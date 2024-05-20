import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import router from '@/router';
import { useRoute } from 'vue-router';
import { useClubStore } from '@/stores/club';

const URL = "http://localhost:8080/club-manager";

export const useClubManagerStore = defineStore('clubManager', () => {

  const route = useRoute();

  const create = function (clubInfo) {
    axios.post(`${URL}`, clubInfo.value, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then(() => {
        alert("클럽이 성공적으로 생성되었습니다.");
        router.replace({ name: 'searchClubForm' })
      })
      .catch(() => {
        alert("클럽 생성에 실패했습니다.")
      })
  }

  const change = function (clubInfo) {
    axios.put(`${URL}/${route.params.clubId}`, clubInfo.value, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then(() => {
        alert("클럽이 성공적으로 수정되었습니다.");
        router.replace({ name: 'searchClubForm' })
      })
      .catch(() => {
        alert("클럽 수정에 실패했습니다.")
      })
  }

  const remove = function () {
    axios.delete(`${URL}/${route.params.clubId}`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then(() => {
        alert("클럽이 성공적으로 삭제되었습니다.");
        router.replace({ name: 'searchClubForm' })
      })
      .catch((error) => {
        let num = error.response.data;
        let message = "";
        switch (num) {
          case 1:
            message = "클럽장이 아닙니다.";
            break;
          default:
            message = "server error";
        }
        alert(message);
      })
  }

  return {
    create,
    change,
    remove,
  }
});
