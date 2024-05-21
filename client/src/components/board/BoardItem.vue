<template>

  <div class="m-auto overflow-hidden rounded-lg shadow-lg cursor-pointer w-80">
    <!-- <RouterLink :to="{name: 'clubDetail', params: { clubId: club.clubId }}" class="block w-full h-full"> -->
      <img v-if="board.img_1 !== null" :src="board.img_1" class="object-cover w-80 h-60" />
      <img v-else src="@/assets/no-image.png" class="object-cover w-80 h-60" />
      <div class="w-full p-4 bg-white">
        <p class="mb-2 text-xl font-medium text-gray-800">
          {{ board.title }}
        </p>
        <p class="font-light text-gray-400 text-md">
          {{ shortContent }}
        </p>
        <p class="text-gray-800 text-sm">
          {{ board.userId }}
        </p>
        <div class="flex justify-between">
          <div>
            {{ board.regDate }} 작성됨
          </div>
          <div>
            {{ board.viewCnt }}회 조회
          </div>
        </div>
        <!-- <div class="flex flex-wrap items-center mt-4 justify-starts">
          <div class="text-xs mr-2 py-1.5 px-4 text-gray-600 bg-gray-100 rounded-2xl">
            {{ board.viewCnt }}회 조회
          </div>
          <div class="text-xs mr-2 py-1.5 px-4 text-gray-600 bg-gray-100 rounded-2xl">
            {{ board.regDate }} 작성됨
          </div>
        </div> -->
      </div>
    <!-- </RouterLink> -->
  </div>

</template>

<script setup>
import { ref } from 'vue';

const props = defineProps({
  board: Object
})

const shortenWords = (str, length = 20) => {
  let result = '';
  if (str.length > length) {
    result = str.substr(0, length - 2) + '...';
  } else {
    result = str;
  }
  return result;
};

const shortContent = ref(shortenWords(props.board.content));

if (shortContent.value === "") {
  shortContent.value = "...";
}
</script>

<style scoped></style>