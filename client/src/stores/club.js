import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import router from '@/router';

const URL = "http://localhost:8080/club";

export const useClubStore = defineStore('club', () => {
  
  const search = function(clubInfo) {
    console.log(clubInfo.value);
    axios.post(`${URL}/search`, clubInfo.value, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
    .then(() => {
      alert("검색에 성공했습니다.");
    })
    .catch(() => {
      alert("검색에 실패했습니다.")
    })
  }

  return {
    search,
  }
});
