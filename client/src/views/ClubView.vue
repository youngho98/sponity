<template>

  <nav class="flex flex-wrap items-center justify-between font-semibold w-2/3 mx-auto mt-40 p-4 border-b bg-white">
    <div class="w-1/5 order-2 text-center">
      <RouterLink :to="{ name: 'clubDetail' }" class="text-3xl font-bold text-gray-800 font-heading">
        {{ clubStore.clubInfo.clubName }}
      </RouterLink>
    </div>
    <div class="w-2/5 navbar-menu order-1 block">
      <RouterLink :to="{ name: 'clubList' }"
        class="innline-block mt-0 mr-10 font-semibold text-green-600 hover:text-green-800" href="#">
        클럽 목록
      </RouterLink>
      <RouterLink v-if="userStore.loginUser.userStatus !== 1" :to="{ name: 'boardList' }"
        class="innline-block mt-0 mr-10 font-semibold text-green-600 hover:text-green-800" href="#">
        게시판
      </RouterLink>
    </div>
    <div class="block w-2/5 navbar-menu order-3 text-right">
      <RouterLink :to="{ name: 'clubManageForm', params: { clubId: clubStore.clubInfo.clubId } }"
        v-if="userStore.loginUser.userStatus === 3"
        class="inline-block mt-0 ml-10 font-semibold text-green-600 hover:text-green-800">
        클럽 관리
      </RouterLink>
      <a v-if="userStore.loginUser.userStatus === 1" @click="register"
        class="inline-block mt-0 ml-10 font-semibold text-green-600 hover:text-green-800" href="#">
        클럽 가입
      </a>
      <a v-if="userStore.loginUser.userStatus === 2" @click="unregister"
        class="inline-block mt-0 ml-10 font-semibold text-green-600 hover:text-green-800" href="#">
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