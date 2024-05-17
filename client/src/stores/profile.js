import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import router from '@/router';
import { useUserStore } from '@/stores/user';

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

  const getUserInfo = function () {
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

  const modifyProfile = function () {
    axios.put(`${URL}/my-page/modify-profile`, loginUser.value, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then(() => {
        alert("성공적으로 회원정보가 변경되었습니다.");
        useUserStore().changeNickname(loginUser.value.nickname);
      })
      .catch((error) => {
        let num = error.response.data;
        let message = "";
        switch (num) {
          case 1:
            message = "중복된 닉네임입니다.";
            break;
          case 2:
            message = "중복된 이메일입니다.";
            break;
          default:
            message = "server error";
        }
        alert(message);
      })
  }

  const changePw = function (password) {
    if (password.value.newPw !== password.value.newPwCheck) {
      alert("비밀번호 확인이 일치하지 않습니다.");
      return;
    }
    axios.patch(`${URL}/my-page/modify-pw`, password.value, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then(() => {
        alert("성공적으로 비밀번호가 변경되었습니다.");
      })
      .catch((error) => {
        let num = error.response.data;
        let message = "";
        switch (num) {
          case 1:
            message = "비밀번호가 틀렸습니다.";
            break;
          case 2:
            message = "비밀번호는 알파벳 대소문자, 숫자, 특수문자를 각각 적어도 하나 이상 포함하면서 8 ~ 20자 사이여야 합니다.";
            break;
          default:
            message = "server error";
        }
        alert(message);
      })
  }

  const withdraw = function (password) {
    axios.patch(`${URL}/my-page/withdraw`, password.value, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then(() => {
        alert("성공적으로 탈퇴되었습니다.");
        useUserStore().logout();
        router.replace({ name: 'home' });
      })
      .catch((error) => {
        let num = error.response.data;
        let message = "";
        switch (num) {
          case 1:
            message = "비밀번호가 틀렸습니다.";
            break;
          default:
            message = "server error";
        }
        alert(message);
      })
  }

  return {
    loginUser,
    getUserInfo,
    modifyProfile,
    changePw,
    withdraw,
  }
});
