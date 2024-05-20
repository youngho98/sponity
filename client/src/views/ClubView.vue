<template>

  <nav class="flex flex-wrap items-center justify-between w-2/3 mx-auto my-5 p-4 bg-green-50 shadow">
    <div class="w-auto lg:order-2 lg:w-1/5 lg:text-center">
      <a class="text-xl font-semibold text-gray-800 font-heading" href="#">
        {{ clubStore.clubInfo.clubName }}
      </a>
    </div>
    <div class="hidden w-full navbar-menu lg:order-1 lg:block lg:w-2/5">
      <a class="block mt-4 mr-10 text-green-600 lg:inline-block lg:mt-0 hover:text-green-800" href="#">
        Notice
      </a>
      <a class="block mt-4 mr-10 text-green-600 lg:inline-block lg:mt-0 hover:text-green-800" href="#">
        Board
      </a>
    </div>
    <div class="hidden w-full navbar-menu lg:order-3 lg:block lg:w-2/5 lg:text-right">
      <RouterLink :to="{ name: 'clubManageForm', params: { clubId: clubStore.clubInfo.clubId } }" class="block mt-4 mr-10 text-green-600 lg:inline-block lg:mt-0 hover:text-green-800" href="#">
        Manage
      </RouterLink>
      <a class="block mt-4 mr-10 text-green-600 lg:inline-block lg:mt-0 hover:text-green-800" href="#">
        Register
      </a>
    </div>
  </nav>

  <RouterView />
</template>

<script setup>
import { useClubStore } from '@/stores/club';
import { useRoute } from 'vue-router';

const clubStore = useClubStore();

const route = useRoute();
</script>

<style scoped></style>