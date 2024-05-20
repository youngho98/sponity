<template>

  <section class="text-gray-600 body-font w-3/5 mx-auto my-5">
    <div class="container mx-auto flex px-5 py-24 flex-row items-center">
      <div class="max-w-lg w-full mb-0">
        <img class="object-cover object-center rounded" style="width: 36rem; height: 27rem;"
          src="@/assets/no-image.png">
      </div>
      <div class="flex-grow w-1/2 pl-24 flex flex-col items-start text-left">
        <p class="title-font text-xl mb-2 font-medium text-green-600">{{ clubStore.clubInfo.category }}</p>
        <h1 class="title-font text-3xl mb-4 font-medium text-gray-900">{{ clubStore.clubInfo.clubName }}</h1>
        <p class="title-font text-md mb-4 font-medium text-gray-900">{{ clubStore.clubInfo.wideArea }} {{
          clubStore.clubInfo.detailArea }}</p>
        <p class="mb-8 text-sm leading-relaxed">{{ clubStore.clubInfo.introduction }}</p>

        <div class="flex justify-center">
          <button
            class="ml-4 inline-flex text-gray-700 bg-gray-100 border-0 py-2 px-6 focus:outline-none hover:bg-gray-200 rounded text-lg">❤️
            3</button>
          <button
            class="ml-4 inline-flex text-gray-700 bg-gray-100 border-0 py-2 px-6 focus:outline-none hover:bg-gray-200 rounded text-lg">👤
            3</button>
        </div>
      </div>
    </div>
  </section>

</template>

<script setup>
import { useClubStore } from '@/stores/club';
import { onMounted } from 'vue';
import { useRoute } from 'vue-router';

const clubStore = useClubStore();
const route = useRoute();

onMounted(() => {
  clubStore.getClubInfo(route.params.clubId);
})
</script>

<style scoped></style>