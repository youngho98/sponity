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
        router.replace({ name: 'clubDetail' })
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
        router.replace({ name: 'clubList' })
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

  const changeLeader = function(clubId, userId) {
    axios.patch(`${URL}/${clubId}/member-list/${userId}/leader`, null, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
    .then(() => {
      alert("클럽장이 정상적으로 변경되었습니다.");
      router.replace({ name: 'clubDetail' });
    })
    .catch(() => {
      alert("클럽장 위임에 실패했습니다.");
    })
  }

  const deleteMember = function(clubId, userId) {
    axios.delete(`${URL}/${clubId}/member-list/${userId}`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
    .then(() => {
      alert("회원 삭제에 성공했습니다.");
      router.go(0);
    })
    .catch(() => {
      alert("회원 삭제에 실패했습니다.");
    })
  }

  return {
    create,
    change,
    remove,
    changeLeader,
    deleteMember,
  }
});
