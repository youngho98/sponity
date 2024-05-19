import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import router from '@/router';

const URL = "http://localhost:8080/club";

export const useClubStore = defineStore('club', () => {
  
  const clubList = ref([]);

  const search = function(clubInfo) {
    axios.post(`${URL}/search`, clubInfo.value, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
    .then((response) => {
      clubList.value = response.data;
      console.log(response.data);
      router.push({ name: 'searchClubResult' });
    })
    .catch(() => {
      alert("검색에 실패했습니다.")
    })
  }

  return {
    clubList,
    search,
  }
}, {
  persist: {
    storage: sessionStorage
  }
});
