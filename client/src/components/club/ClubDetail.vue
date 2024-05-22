<template>

  <div class="flex w-2/3 mx-auto justify-between p-4">
    <div class="text-gray-400">
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
  <img v-if="clubStore.clubInfo.clubImg !== null" :src="clubStore.clubInfo.clubImg" class="w-2/3 mx-auto rounded" >
  <img v-else src="@/assets/banner.png" class="mx-auto" />

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