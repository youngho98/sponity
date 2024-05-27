<template>

  <div>
    <div class="flex border-t border-gray-600 py-2">
      <span class="text-gray-500 w-1/6">{{ review.nickname }}</span>
      <span class="text-gray-900 w-5/6">{{ review.content }}</span>
    </div>
    <div class="flex border-t border-gray-200 py-2">
      <span class="text-gray-500 w-1/6">작성일</span>
      <span class="text-gray-900 w-3/4">{{ review.regDate }}</span>
      <button v-if="userStore.loginUser.nickname === review.nickname" @click="modifyOn"
        class="text-sky-600 hover:text-sky-700 mx-auto">수정</button>
      <button v-else style="visibility: hidden;"
        class="text-sky-600 hover:text-sky-700 mx-auto">수정</button>
      <button v-if="userStore.loginUser.nickname === review.nickname || userStore.loginUser.userStatus === 3"
        @click="deleteReview" class="text-sky-600 hover:text-sky-700 mx-auto">삭제</button>
    </div>
  </div>

  <div v-if="modify" class="my-center-form bg-white shadow-2xl w-1/2 px-12 py-12 rounded-2xl border border-gray-500">
    <div class="flex">
      <h3 class="font-semibold my-3 w-5/6">댓글 수정</h3>
      <button @click="modifyReview" class="text-sky-600 hover:text-sky-700 my-3 ml-auto mr-2">수정</button>
      <button @click="modifyOff" class="text-sky-600 hover:text-sky-700 my-3 ml-auto mr-2">취소</button>
    </div>
    <div class="flex border-t border-gray-600 py-2">
      <span class="text-gray-500 w-1/6">{{ userStore.loginUser.nickname }}</span>
      <textarea v-model="reviewContent.content"
        class="rounded-lg border-transparent flex-1 appearance-none border border-gray-400 w-full h-12 py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent" />
    </div>
  </div>

</template>

<script setup>
import { useBoardStore } from '@/stores/board';
import { useUserStore } from '@/stores/user';
import axios from 'axios';
import { ref } from 'vue';
import { useRoute } from 'vue-router';

const userStore = useUserStore();
const boardStore = useBoardStore();
const route = useRoute();

const reviewContent = ref({
  content: ''
});

const props = defineProps({
  review: Object
})

const modify = ref(false);
const modifyOn = function () {
  reviewContent.value.content = props.review.content;
  modify.value = true;
}
const modifyOff = function () {
  modify.value = false;
}
const modifyReview = function () {
  boardStore.modifyReview(route.params.clubId, route.params.boardId, props.review.reviewId, reviewContent);
}

const deleteReview = function () {
  boardStore.deleteReview(route.params.clubId, route.params.boardId, props.review.reviewId);
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