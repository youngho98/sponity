<template>

  <div class="w-2/3 mx-auto mb-40 p-12 bg-white">
    <div class="items-end mb-12 header">
      <div class="title">
        <div class="flex text-center items-center justify-between">
          <div class="text-3xl font-semibold text-gray-800">
            게시판
          </div>
          <RouterLink :to="{ name: 'createBoardForm' }" class="text-right text-sky-600 hover:text-sky-700">게시글 작성</RouterLink>
        </div>
        <p class="text-xl font-light text-gray-400 mt-6">
          {{ clubStore.clubInfo.introduction }}
        </p>
      </div>
      <!-- <div class="text-end">
        <form
          class="flex flex-col justify-center w-3/4 max-w-sm space-y-3 md:flex-row md:w-full md:space-x-3 md:space-y-0">
          <div class=" relative ">
            <input type="text" id="&quot;form-subscribe-Search"
              class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              placeholder="Enter a title" />
          </div>
          <button
            class="flex-shrink-0 px-4 py-2 text-base font-semibold text-white bg-green-600 rounded-lg shadow-md hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-green-500 focus:ring-offset-2 focus:ring-offset-green-200"
            type="submit">
            Search
          </button>
        </form>
      </div> -->
    </div>
    <div class="grid grid-cols-1 gap-12 md:grid-cols-2 xl:grid-cols-3">
      <BoardItem v-for="board in boardStore.boardList" :key="board.boardId" :board="board" />
    </div>
  </div>

</template>

<script setup>
import BoardItem from '@/components/board/BoardItem.vue';

import { useBoardStore } from '@/stores/board';
import { useClubStore } from '@/stores/club';
import { onMounted } from 'vue';

const clubStore = useClubStore();
const boardStore = useBoardStore();

onMounted(() => {
  boardStore.getBoardList(clubStore.clubInfo.clubId);
})
</script>


<style scoped></style>