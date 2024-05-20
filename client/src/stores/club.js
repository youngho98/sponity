import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import router from '@/router';
import { useUserStore } from './user';
import { useRoute } from 'vue-router';

const URL = "http://localhost:8080/club";

export const useClubStore = defineStore('club', () => {

  const route = useRoute();

  const clubList = ref([]);

  const clubInfo = ref({});

  const search = function (clubInfo) {
    axios.post(`${URL}/search`, clubInfo.value, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then((response) => {
        clubList.value = response.data;
        router.push({ name: 'searchClubResult' });
      })
      .catch(() => {
        alert("검색에 실패했습니다.")
      })
  }

  const getClubInfo = function (clubId) {
    axios.get(`${URL}/${clubId}`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then((response) => {
        clubInfo.value = response.data;
        useUserStore().loginUser.userStatus = response.data.userStatus;
        useUserStore().loginUser.isLike = response.data.isLike;
      })
      .catch(() => {
        alert("클럽 조회에 실패했습니다.");
      })
  }

  const like = function() {
    axios.get(`${URL}/${route.params.clubId}/like`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
    .then(() => {
      useUserStore().loginUser.isLike = 1;
      router.go(0);
    })
    .catch(() => {
      alert("server error");
    })
  }

  const unlike = function() {
    axios.delete(`${URL}/${route.params.clubId}/like`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
    .then(() => {
      useUserStore().loginUser.isLike = 0;
      router.go(0);
    })
    .catch(() => {
      alert("server error");
    })
  }

  return {
    clubList,
    clubInfo,
    search,
    getClubInfo,
    like,
    unlike,
  }
}, {
  persist: {
    storage: sessionStorage
  }
});
