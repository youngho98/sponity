<template>
    <div>
      <div>
        ChatGPT에게 추천 받기
      </div>
      <div class="p-6 mt-8">
        <div>운동할 장소 추천 받기!</div>
        <form action="#">
          <div>
            <div>
              <p>wideArea</p>
              <input type="text" v-model="searchInfoA.wideArea" placeholder="도/특별시/광역시" />

              <p>detailArea</p>
              <input type="text" v-model="searchInfoA.detailArea" placeholder="시/군/구" />

              <p>category(운동)</p>
              <input type="text" v-model="searchInfoA.category" placeholder="하고 싶은 운동을 입력하세요." />
            </div>
          </div>
          <div>
            <input type="button" @click="searchExercisePlace" value="ChatGPT에게 추천 받기!" />
          </div>
        </form>
      </div>
      <div class="p-6 mt-8">
        <div>운동 후 식사하러 갈 맛집 추천 받기!</div>
        <form action="#">
          <div>
            <div>
              <p>wideArea</p>
              <input type="text" v-model="searchInfoB.wideArea" placeholder="도/특별시/광역시" />

              <p>detailArea</p>
              <input type="text" v-model="searchInfoB.detailArea" placeholder="시/군/구" />

              <p>category(음식)</p>
              <input type="text" v-model="searchInfoB.category" placeholder="먹고 싶은 음식을 입력하세요." />
            </div>
          </div>
          <div>
            <input type="button" @click="searchRestaurant" value="ChatGPT에게 추천 받기!" />
          </div>
        </form>
      </div>
    </div>
  </template>
  

  <script setup>
  import { useChatgptStore } from '@/stores/chatgpt';
  import { ref } from 'vue';
  
  const searchInfoA = ref({
    wideArea: '',
    detailArea: '',
    category: '',
  });

  const searchInfoB = ref({
    wideArea: '',
    detailArea: '',
    category: '',
  });
  
  const chatgptStore = useChatgptStore();
  
  const searchExercisePlace = function () {
    chatgptStore.searchExercisePlace(searchInfoA);
  }
  
  const searchRestaurant = function () {
    chatgptStore.searchRestaurant(searchInfoB);
  }
  </script>
  
  
  <style scoped></style>