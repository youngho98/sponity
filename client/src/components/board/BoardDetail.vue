<template>

  <section class="text-gray-600 body-font overflow-hidden">
    <div class="container py-12 mx-auto">
      <div class="w-3/5 mx-auto flex flex-wrap">
        <div class="flex flex-row-reverse ml-auto">
          <button @click="removeBoard" class="mx-2 text-sky-600 hover:text-sky-700">삭제</button>
          <RouterLink :to="{ name: 'modifyBoardForm', params: { boardId: boardStore.boardInfo.boardId } }"
            class="mx-2 text-sky-600 hover:text-sky-700">
            수정</RouterLink>
        </div>
        <div class="w-full pb-6 my-0">
          <h1 class="text-gray-900 text-3xl title-font font-medium mb-4">{{ boardStore.boardInfo.title }}</h1>
          <h2 class="text-sm title-font text-gray-500 tracking-widest">{{ boardStore.boardInfo.nickname }}</h2>
          <p class="leading-relaxed mb-4 mt-10">{{ boardStore.boardInfo.content }}</p>
          <div class="flex border-t border-gray-200 py-2">
            <span class="text-gray-500">조회수</span>
            <span class="ml-auto text-gray-900">{{ boardStore.boardInfo.viewCnt }}회</span>
          </div>
          <div class="flex border-t border-gray-200 py-2">
            <span class="text-gray-500">작성일</span>
            <span class="ml-auto text-gray-900">{{ boardStore.boardInfo.regDate }}</span>
          </div>
        </div>
        <div class="flex flex-wrap">
          <img v-if="boardStore.boardInfo.img1 !== null" :src="boardStore.boardInfo.img1"
            class="w-72 h-54 mx-1 object-cover object-center rounded">
          <img v-else src="@/assets/no-image.png" class="w-72 h-54 mx-1 object-cover object-center rounded">
          <img v-if="boardStore.boardInfo.img2 !== null" :src="boardStore.boardInfo.img2"
            class="w-72 h-54 mx-1 object-cover object-center rounded">
          <img v-else src="@/assets/no-image.png" class="w-72 h-54 mx-1 object-cover object-center rounded">
          <img v-if="boardStore.boardInfo.img3 !== null" :src="boardStore.boardInfo.img3"
            class="w-72 h-54 mx-1 object-cover object-center rounded">
          <img v-else src="@/assets/no-image.png" class="w-72 h-54 mx-1 object-cover object-center rounded">
        </div>

        <div class="w-full py-6 my-0">
          <div class="flex">
            <h3 class="font-semibold my-3 w-11/12">댓글 목록</h3>
            <button @click="createOn" class="text-sky-600 hover:text-sky-700 my-3 ml-auto mr-2">작성</button>
          </div>
          <ReviewItem v-for="review in boardStore.boardInfo.reviewList" :key="review.reviewId" :review="review" />
        </div>

      </div>
    </div>
  </section>

  <div v-if="create" class="my-center-form bg-white shadow-2xl w-1/2 px-12 py-12 rounded-2xl border border-gray-500">
    <div class="flex">
      <h3 class="font-semibold my-3 w-5/6">댓글 등록</h3>
      <button @click="createReview" class="text-sky-600 hover:text-sky-700 my-3 ml-auto mr-2">등록</button>
      <button @click="createOff" class="text-sky-600 hover:text-sky-700 my-3 ml-auto mr-2">취소</button>
    </div>
    <div class="flex border-t border-gray-600 py-2">
      <span class="text-gray-500 w-1/6">{{ userStore.loginUser.nickname }}</span>
      <textarea v-model="reviewContent.content"
        class="rounded-lg border-transparent flex-1 appearance-none border border-gray-400 w-full h-12 py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent" />
    </div>
  </div>

</template>

<script setup>
import ReviewItem from '@/components/board/ReviewItem.vue';

import { useBoardStore } from '@/stores/board';
import { useUserStore } from '@/stores/user';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const boardStore = useBoardStore();
const userStore = useUserStore();

onMounted(() => {
  boardStore.getBoardInfo(route.params.clubId, route.params.boardId);
})

const removeBoard = function () {
  boardStore.removeBoard(route.params.clubId, route.params.boardId);
}

const reviewContent = ref({
  content: ''
});

const create = ref(false);
const createOn = function () {
  create.value = true;
}
const createOff = function () {
  create.value = false;
}
const createReview = function () {
  boardStore.createReview(route.params.clubId, route.params.boardId, reviewContent);
}
</script>

<style scoped>
.my-center-form {
  position: fixed;
  top: 40%;
  margin: 0 auto;
  left: 0;
  right: 0;
}
</style>