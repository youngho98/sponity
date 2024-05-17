import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import router from '@/router';

const URL = "http://localhost:8080/club-manager";

export const useClubManagerStore = defineStore('clubManager', () => {
  
  const create = function(clubInfo) {
    console.log(clubInfo.value);
    axios.post(`${URL}`, clubInfo.value, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
    .then(() => {
      alert("클럽이 성공적으로 생성되었습니다.");
    })
    .catch(() => {
      alert("클럽 생성에 실패했습니다.")
    })
  }

  return {
    create,
  }
});
