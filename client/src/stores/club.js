import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import router from '@/router';

const URL = "http://localhost:8080/club";

export const useClubStore = defineStore('club', () => {

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
      })
      .catch(() => {
        alert("클럽 조회에 실패했습니다.");
      })
  }

  return {
    clubList,
    clubInfo,
    search,
    getClubInfo,
  }
}, {
  persist: {
    storage: sessionStorage
  }
});
