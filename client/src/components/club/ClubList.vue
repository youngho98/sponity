<template>

  <div class="w-3/4 mx-auto my-40 p-12 bg-white">
    <div class="flex items-end justify-between mb-12 header">
      <div class="title">
        <p class="mb-4 text-4xl font-bold text-gray-800">
          클럽 목록
        </p>
        <p class="text-xl font-light text-gray-400">
          클럽에 가입하고 함께 운동할 친구를 찾아보세요
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
      <ClubItem v-for="club in clubStore.clubList" :key="club.clubId" :club="club"/>
    </div>
  </div>

</template>

<script setup>
import ClubItem from '@/components/club/ClubItem.vue';

import { useClubStore } from '@/stores/club';
import { useUserStore } from '@/stores/user';
import { onMounted } from 'vue';

const userStore = useUserStore();
const clubStore = useClubStore();

onMounted(() => {
  if (userStore.loginUser.showMyClub != 1) {
    clubStore.searchClub(clubStore.searchInfo);
  } else {
    clubStore.showMyClubs();
  }
})
</script>


<style scoped></style>