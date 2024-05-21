import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import router from '@/router';
import { useUserStore } from './user';
import { useRoute } from 'vue-router';

const URL = "http://localhost:8080/club";

export const useBoardStore = defineStore('board', () => {

  const boardList = ref([]);

  const clubInfo = ref({});

  const getBoardList = function (clubId) {
    axios.get(`${URL}/${clubId}/board`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then((response) => {
        boardList.value = response.data;
      })
      .catch(() => {
        alert("게시판 로딩에 실패했습니다.")
      })
  }

  return {
    boardList,
    getBoardList,
  }
});
