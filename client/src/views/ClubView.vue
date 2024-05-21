<template>

  <nav class="flex flex-wrap items-center justify-between w-2/3 mx-auto my-5 p-4 bg-green-50 shadow">
    <div class="w-auto lg:order-2 lg:w-1/5 lg:text-center">
      <a class="text-xl font-semibold text-gray-800 font-heading" href="#">
        {{ clubStore.clubInfo.clubName }}
      </a>
    </div>
    <div class="hidden w-full navbar-menu lg:order-1 lg:block lg:w-2/5">
      <RouterLink :to="{ name: 'clubList' }" class="block mt-4 mr-10 text-green-600 lg:inline-block lg:mt-0 hover:text-green-800" href="#">
        클럽 목록
      </RouterLink>
      <a class="block mt-4 mr-10 text-green-600 lg:inline-block lg:mt-0 hover:text-green-800" href="#">
        게시판
      </a>
    </div>
    <div class="hidden w-full navbar-menu lg:order-3 lg:block lg:w-2/5 lg:text-right">
      <RouterLink :to="{ name: 'clubManageForm', params: { clubId: clubStore.clubInfo.clubId } }"
        v-if="userStore.loginUser.userStatus === 3"
        class="block mt-4 mr-10 text-green-600 lg:inline-block lg:mt-0 hover:text-green-800">
        클럽 관리
      </RouterLink>
      <a v-if="userStore.loginUser.userStatus === 1" @click="register"
        class="block mt-4 mr-10 text-green-600 lg:inline-block lg:mt-0 hover:text-green-800" href="#">
        클럽 가입
      </a>
      <a v-if="userStore.loginUser.userStatus === 2" @click="unregister"
        class="block mt-4 mr-10 text-green-600 lg:inline-block lg:mt-0 hover:text-green-800" href="#">
        클럽 탈퇴
      </a>
    </div>
  </nav>

  <RouterView />
</template>

<script setup>
import { useUserStore } from '@/stores/user';
import { useClubStore } from '@/stores/club';

const userStore = useUserStore();
const clubStore = useClubStore();

const register = function () {
  clubStore.register();
}

const unregister = function () {
  clubStore.unregister();
}
</script>

<style scoped></style>