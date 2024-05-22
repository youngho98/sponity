import { ref } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios';
import router from '@/router';
import { useRoute } from 'vue-router';

const URL = "http://localhost:8080/chatgpt";

export const useChatgptStore = defineStore('chatgpt', () => {

  const route = useRoute();

  const searchList = ref();

  const searchInfoA = ref({});
  const searchInfoB = ref({});

  const searchExercisePlace = function (searchInfoA) {
    axios.post(`${URL}/exercise-place`, searchInfoA.value, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then((response) => {
        searchList.value = response.data;
        router.push({ name: 'kakao' })
      })
      .catch(() => {
        alert("server error")
      })
  }

  const searchRestaurant = function (searchInfoB) {
    axios.post(`${URL}/restaurant`, searchInfoB.value, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
      .then((response) => {
        searchList.value = response.data;
        router.push({ name: 'kakao' })
      })
      .catch(() => {
        alert("server error")
      })
  }


  return {
    searchList,
    searchInfoA,
    searchInfoB,
    searchExercisePlace,
    searchRestaurant,
  }
}, {
  persist: {
    storage: sessionStorage
  }
});
