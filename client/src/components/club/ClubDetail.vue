<template>

  <div class="flex w-2/3 mx-auto justify-between p-4">
    <div class="font-semibold">
      {{ clubStore.clubInfo.introduction }}
    </div>
    <div>
      <button @click="unlike" v-if="userStore.loginUser.isLike === 1"
        class="ml-4 inline-flex text-gray-700 bg-gray-100 border-0 py-2 px-6 focus:outline-none hover:bg-gray-200 rounded text-lg">❤️
        {{ clubStore.clubInfo.likeNum }}</button>
      <button @click="like" v-else
        class="ml-4 inline-flex text-gray-700 bg-gray-100 border-0 py-2 px-6 focus:outline-none hover:bg-gray-200 rounded text-lg">🤍
        {{ clubStore.clubInfo.likeNum }}</button>
      <button @click="$router.push({ name: 'memberList' })"
        class="ml-4 inline-flex text-gray-700 bg-gray-100 border-0 py-2 px-6 focus:outline-none hover:bg-gray-200 rounded text-lg">👤
        {{ clubStore.clubInfo.memberNum }}</button>
    </div>
  </div>
  <img class="w-2/3 mx-auto rounded" src="@/assets/no-image.png">
  <img src="@/assets/banner.png" class="mx-auto" />

  <!-- <section class="text-gray-600 body-font w-3/5 mx-auto my-5">
    <div class="container mx-auto flex px-5 py-24 flex-row items-center">
      <div class="max-w-lg w-full mb-0">
        <img class="object-cover object-center rounded" src="@/assets/no-image.png">
      </div>
      <div class="flex-grow w-1/2 pl-24 flex flex-col items-start text-left">
        <p class="title-font text-xl mb-2 font-medium text-green-600">{{ clubStore.clubInfo.category }}</p>
        <h1 class="title-font text-3xl mb-4 font-medium text-gray-900">{{ clubStore.clubInfo.clubName }}</h1>
        <p class="title-font text-md mb-4 font-medium text-gray-900">{{ clubStore.clubInfo.wideArea }} {{
          clubStore.clubInfo.detailArea }}</p>
        <p class="mb-8 text-sm leading-relaxed">{{ clubStore.clubInfo.introduction }}</p>

        <div class="flex justify-between">
          <button @click="unlike" v-if="userStore.loginUser.isLike === 1"
            class="ml-4 inline-flex text-gray-700 bg-gray-100 border-0 py-2 px-6 focus:outline-none hover:bg-gray-200 rounded text-lg">❤️
            {{ clubStore.clubInfo.likeNum }}</button>
          <button @click="like" v-else
            class="ml-4 inline-flex text-gray-700 bg-gray-100 border-0 py-2 px-6 focus:outline-none hover:bg-gray-200 rounded text-lg">🤍
            {{ clubStore.clubInfo.likeNum }}</button>
          <button @click="$router.push({ name: 'memberList' })"
            class="ml-4 inline-flex text-gray-700 bg-gray-100 border-0 py-2 px-6 focus:outline-none hover:bg-gray-200 rounded text-lg">👤
            {{ clubStore.clubInfo.memberNum }}</button>
        </div>
      </div>
    </div>
  </section> -->

</template>

<script setup>
import { useClubStore } from '@/stores/club';
import { useUserStore } from '@/stores/user';
import { onMounted } from 'vue';
import { useRoute } from 'vue-router';

const userStore = useUserStore();
const clubStore = useClubStore();
const route = useRoute();

onMounted(() => {
  clubStore.getClubInfo(route.params.clubId);
})

const like = function () {
  clubStore.like();
}

const unlike = function () {
  clubStore.unlike();
}
</script>

<style scoped></style>