import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import router from '@/router';

const URL = "http://localhost:8080";

export const useUserStore = defineStore('user', () => {
  const loginUser = ref({
    nickname: '',
    profileImg: '',
    userStatus: '',
    isLike: '',
    showMyClub: 0,
  });

  const register = function (userInfo) {
    if (userInfo.value.userId === '' || userInfo.value.password === '' || userInfo.value.passwordCheck === '' || userInfo.value.userName === '' || userInfo.value.nickname === '' || userInfo.value.email === '' || userInfo.value.wideArea === '' || userInfo.value.detailArea === '') {
      alert("빈 칸을 전부 채워야 합니다.");
      return;
    }

    if (userInfo.value.password !== userInfo.value.passwordCheck) {
      alert("비밀번호 확인이 일치하지 않습니다.");
      return;
    }

    axios.post(`${URL}/join`, userInfo.value)
      .then(() => {
        router.replace({ name: 'loginForm' });
      })
      .catch((error) => {
        let num = error.response.data;
        let message = "";
        switch (num) {
          case 1:
            message = "중복된 ID입니다.";
            break;
          case 2:
            message = "중복된 닉네임입니다.";
            break;
          case 3:
            message = "중복된 이메일입니다.";
            break;
          case 4:
            message = "ID는 알파벳 대소문자와 숫자로만 이루어져 있으면서 4 ~ 14자 사이여야 합니다.";
            break;
          case 5:
            message = "비밀번호는 알파벳 대소문자, 숫자, 특수문자를 각각 적어도 하나 이상 포함하면서 8 ~ 20자 사이여야 합니다.";
            break;
          default:
            message = "server error";
        }
        alert(message);
      });
  }

  const login = function (loginInfo) {
    axios.post(`${URL}/login`, loginInfo.value)
      .then((response) => {
        // 세션에 access-token 저장
        sessionStorage.setItem('access-token', response.headers.authorization);

        const rawToken = response.headers.authorization.split(' ');
        const token = rawToken[1].split('.');

        // Base64 디코딩 함수
        const decodeBase64 = (str) => {
          const bytes = Uint8Array.from(atob(str), c => c.charCodeAt(0));
          return new TextDecoder().decode(bytes);
        };

        // base64 디코딩 오류를 방지하기 위해 '-'를 '+'로, '_'를 '/'로 치환
        token[1] = token[1].replaceAll('-', '+');
        token[1] = token[1].replaceAll('_', '/');
        // payload 부분 디코딩
        const payload = decodeBase64(token[1]);
        const payloadObj = JSON.parse(payload);

        // 닉네임 가져오기
        let nickname = payloadObj.nickname;
        loginUser.value.nickname = nickname;
        // 이미지 가져오기
        let profileImg = payloadObj.profileImgUrl;
        if (profileImg !== undefined) {
          loginUser.value.profileImg = profileImg;
        } else {
          loginUser.value.profileImg = '';
        }

        router.replace({ name: 'home' });
        setTimeout(() => {
          router.go(0);
        }, 100);
      })
      .catch(() => {
        alert("없는 회원이거나 잘못된 비밀번호입니다.");
      });
  }

  const findId = function (userInfo) {
    axios.post(`${URL}/find-id`, userInfo.value)
      .then((response) => {
        alert(`회원님의 아이디는 ${response.data} 입니다.`);
      })
      .catch(() => {
        alert("찾는 아이디가 없습니다.")
      })
  }

  const resetPw = function (userInfo) {
    axios.post(`${URL}/reset-pw`, userInfo.value)
      .then(() => {
        alert(`이메일로 임시비밀번호를 전송하였습니다. 이메일을 확인해주세요!`);
      })
      .catch((error) => {
        let num = error.response.data;
        let message = "";
        switch (num) {
          case 1:
            message = "회원가입 내역이 없습니다.";
            break;
          case 2:
            message = "가입된 ID이지만, 이메일이 불일치합니다.";
            break;
          default:
            message = "server error";
        }
        alert(message);
      })
  }

  const logout = function () {
    sessionStorage.removeItem('access-token');
    loginUser.value.nickname = '';
    loginUser.value.profileImg = '';
    router.replace({ name: 'home' });
  }

  const changeNickname = function(nickname) {
    loginUser.value.nickname = nickname;
  }

  return {
    loginUser,
    register,
    login,
    findId,
    resetPw,
    logout,
    changeNickname,
  }
}, {
  persist: {
    storage: sessionStorage
  }
});
