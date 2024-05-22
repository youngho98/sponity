import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import router from '@/router';
import { useUserStore } from './user';
import { useRoute } from 'vue-router';

const URL = "http://localhost:8080/club";

export const useBoardStore = defineStore('board', () => {

  const boardList = ref([]);

  const boardInfo = ref({});

  const getBoardList = function(clubId) {
    axios.get(`${URL}/${clubId}/board`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token'),
        'Content-Type': 'text/plain',
      }
    })
      .then((response) => {
        console.log(response.data);
        boardList.value = response.data;
      })
      .catch(() => {
        alert("게시판 로딩에 실패했습니다.")
      })
  }

  const getBoardInfo = function(clubId, boardId) {
    axios.get(`${URL}/${clubId}/board/${boardId}`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
    .then((response) => {
      console.log(response.data);
      boardInfo.value = response.data;
    })
    .catch(() => {
      alert("게시글 로딩에 실패했습니다.")
    })
  }

  const removeBoard = function(clubId, boardId) {
    axios.delete(`${URL}/${clubId}/board/${boardId}`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
    .then(() => {
      alert("게시글이 삭제되었습니다.");
      router.replace({name: 'boardList'});
    })
    .catch(() => {
      alert("게시글 삭제에 실패했습니다.")
    })
  }

  const createReview = function(clubId, boardId, reviewContent) {
    axios.post(`${URL}/${clubId}/board/${boardId}`, reviewContent.value, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
    .then(() => {
      alert("댓글이 등록되었습니다.");
    })
    .catch(() => {
      alert("댓글 등록에 실패했습니다.");
    })
  }

  const modifyReview = function(clubId, boardId, reviewId, reviewContent) {
    axios.put(`${URL}/${clubId}/board/${boardId}/${reviewId}`, reviewContent.value, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
    .then(() => {
      alert("댓글이 수정되었습니다.");
    })
    .catch(() => {
      alert("댓글 수정에 실패했습니다.");
    })
  }

  const deleteReview = function(clubId, boardId, reviewId) {
    axios.delete(`${URL}/${clubId}/board/${boardId}/${reviewId}`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
    .then(() => {
      alert("댓글이 삭제되었습니다.");
    })
    .catch(() => {
      alert("댓글 삭제에 실패했습니다.");
    })
  }

  return {
    boardList,
    boardInfo,
    getBoardList,
    getBoardInfo,
    removeBoard,
    createReview,
    modifyReview,
    deleteReview,
  }
});
