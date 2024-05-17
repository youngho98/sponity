import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import router from '@/router';

const URL = "http://localhost:8080";

export const useProfileStore = defineStore('profile', () => {
  const loginUser = ref({
    userId: '',
    userName: '',
    nickname: '',
    email: '',
    wideArea: '',
    detailArea: '',
  });

  const getUserInfo = function() {
    axios.get(`${URL}/my-page`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
    .then((response) => {
      loginUser.value.userId = response.data.userId;
      loginUser.value.userName = response.data.userName;
      loginUser.value.nickname = response.data.nickname;
      loginUser.value.email = response.data.email;
      loginUser.value.wideArea = response.data.wideArea;
      loginUser.value.detailArea = response.data.detailArea;
    })
    .catch(() => {
      alert("다시 로그인 해주세요.");
      router.replace({ name: 'loginForm' });
    });
  }

  const modifyProfile = function() {
    axios.put(`${URL}/modify-profile`, loginUser.value, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
    .then(() => {
      alert("성공적으로 회원정보가 변경되었습니다.");
    })
    .catch(() => {
      alert("정보 변경 실패");
    })
  }

  return {
    loginUser,
    getUserInfo,
    modifyProfile,
  }
});
