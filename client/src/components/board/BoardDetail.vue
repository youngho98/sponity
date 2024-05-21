<template>
  <section class="text-gray-600 body-font overflow-hidden">
    <div class="container py-12 mx-auto">
      <div class="w-3/5 mx-auto flex flex-wrap">
        <div class="flex flex-row-reverse ml-auto">
          <a href="#" @click="removeBoard" class="mx-2">삭제</a>
          <RouterLink :to="{ name: 'modifyBoardForm', params: { boardId: boardStore.boardInfo.boardId }}" class="mx-2">수정</RouterLink>
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
          <img alt="ecommerce" class="w-72 h-54 mx-1 object-cover object-center rounded" src="@/assets/no-image.png">
          <img alt="ecommerce" class="w-72 h-54 mx-1 object-cover object-center rounded" src="@/assets/no-image.png">
          <img alt="ecommerce" class="w-72 h-54 mx-1 object-cover object-center rounded" src="@/assets/no-image.png">
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { useBoardStore } from '@/stores/board';
import { onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const boardStore = useBoardStore();

onMounted(() => {
  boardStore.getBoardInfo(route.params.clubId, route.params.boardId);
})

const removeBoard = function() {
  boardStore.removeBoard(route.params.clubId, route.params.boardId);
}
</script>

<style scoped></style>