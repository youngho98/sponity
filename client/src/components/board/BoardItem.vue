<template>

  <div class="m-auto overflow-hidden rounded-lg shadow-lg cursor-pointer w-80">
    <RouterLink :to="{name: 'boardDetail', params: { boardId: board.boardId }}" class="block w-full h-full">
      <img v-if="board.img1 !== null" :src="board.img1" class="object-cover w-80 h-60" />
      <img v-else src="@/assets/no-image.png" class="object-cover w-80 h-60" />
      <div class="w-full p-4 bg-white">
        <p class="mb-2 text-xl font-medium text-gray-800">
          {{ board.title }}
        </p>
        <p class="text-gray-500 text-md">
          {{ board.nickname }}
        </p>
        <div class="flex justify-between text-sm">
          <div>
            {{ board.regDate }} 작성됨
          </div>
          <div>
            {{ board.viewCnt }}회 조회
          </div>
        </div>
      </div>
    </RouterLink>
  </div>

</template>

<script setup>
const props = defineProps({
  board: Object
})
</script>

<style scoped></style>