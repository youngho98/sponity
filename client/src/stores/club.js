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

  const searchInfo = ref({
    category: '',
    wideArea: '',
    detailArea: '',
    keyword: '',
  });

  const insertSearch = function (searchForm) {
    searchInfo.value.category = searchForm.value.category;
    searchInfo.value.wideArea = searchForm.value.wideArea;
    searchInfo.value.detailArea = searchForm.value.detailArea;
    searchInfo.value.keyword = searchForm.value.keyword;
    router.push({ name: 'clubList' });
  }

  const searchClub = function (searchForm) {
    axios.post(`${URL}/search`, searchForm, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then((response) => {
        clubList.value = response.data;
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

  const like = function () {
    axios.post(`${URL}/${route.params.clubId}/like`, null, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then(() => {
        router.go(0);
      })
      .catch(() => {
        alert("server error");
      })
  }

  const unlike = function () {
    axios.delete(`${URL}/${route.params.clubId}/like`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then(() => {
        router.go(0);
      })
      .catch(() => {
        alert("server error");
      })
  }

  const register = function () {
    axios.post(`${URL}/${route.params.clubId}`, null, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then(() => {
        router.go(0);
      })
      .catch((error) => {
        let num = error.response.data;
        let message = "";
        switch (num) {
          case 1:
            message = "이미 가입된 회원입니다.";
            break;
          default:
            message = "server error";
        }
        alert(message);
      })
  }

  const unregister = function () {
    axios.delete(`${URL}/${route.params.clubId}`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then(() => {
        router.go(0);
      })
      .catch((error) => {
        let num = error.response.data;
        let message = "";
        switch (num) {
          case 1:
            message = "가입된 회원이 아닙니다.";
            break;
          default:
            message = "server error";
        }
        alert(message);
      })
  }

  return {
    clubList,
    clubInfo,
    searchInfo,
    insertSearch,
    searchClub,
    getClubInfo,
    like,
    unlike,
    register,
    unregister,
  }
}, {
  persist: {
    storage: sessionStorage
  }
});
