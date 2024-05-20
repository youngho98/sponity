import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import router from '@/router';

const URL = "http://localhost:8080/club-manager";

export const useClubManagerStore = defineStore('clubManager', () => {

  const create = function (clubInfo) {
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

  const change = function (clubInfo) {
    axios.put(`${URL}/${clubInfo.value.clubId}`, clubInfo.value, {
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

  const remove = function () {
    axios.delete(`${URL}/${clubInfo.value.clubId}`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then(() => {
        alert("클럽이 성공적으로 삭제되었습니다.");
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
