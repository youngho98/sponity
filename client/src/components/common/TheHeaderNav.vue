<template>
  <div>
    <nav class="back-black shadow-2xl w-full h-20 place-content-center fixed" style="z-index: 1; top: 0;">
      <div class="px-8 mx-auto max-w-7xl">
        <div class="flex items-center justify-between h-16">
          <div class="w-full justify-between flex items-center">
            <div class="flex items-center">
              <a class="flex-shrink-0" href="/">
                <img class="w-40 h-20" src="@/assets/sponity-logo.png" alt="sponity" />
              </a>
              <RouterLink :to="{ name: 'searchClubForm' }"
                class="text-gray-50 hover:text-green-500 px-3 py-2 ml-12 rounded-md text-base font-medium">
                클럽검색
              </RouterLink>
              <RouterLink :to="{ name: 'createClubForm' }"
                class="text-gray-50  hover:text-green-500 px-3 py-2 rounded-md text-base font-medium">
                클럽생성
              </RouterLink>
              <RouterLink :to="{ name: 'chatgpt' }"
                class="text-gray-50  hover:text-green-500 px-3 py-2 rounded-md text-base font-medium">
                추천! with <b>ChatGPT</b>
              </RouterLink>
            </div>
            <div class="flex">
              <div class="flex items-center ml-10 space-x-4">
                <template v-if="userStore.loginUser.nickname === ''">
                  <RouterLink :to="{ name: 'loginForm' }"
                    class="text-gray-50 hover:text-green-500 px-3 py-2 rounded-md text-base font-medium">
                    로그인
                  </RouterLink>
                  <RouterLink :to="{ name: 'registerForm' }"
                    class="text-gray-50 hover:text-green-500 px-3 py-2 rounded-md text-base font-medium">
                    회원가입
                  </RouterLink>
                </template>
                <template v-else>
                  <span class="text-green-500 pl-3 py-2 rounded-md text-base font-medium flex items-center">
                    <img v-if="useUserStore().loginUser.profileImg !== ''" :src="useUserStore().loginUser.profileImg" class="mx-3 rounded-lg w-12 h-12" />
                    <img v-else src="@/assets/avatar.png" class="mx-3 rounded-lg w-12 h-12" />
                    {{ userStore.loginUser.nickname }}
                  </span>
                  <span class="text-gray-50 pr-3 py-2 rounded-md text-base font-medium">
                    님 환영합니다.</span>
                  <RouterLink :to="{ name: 'myPage' }"
                    class="text-gray-50 hover:text-green-500 px-3 py-2 rounded-md text-base font-medium">
                    마이페이지
                  </RouterLink>
                  <RouterLink :to="{ name: 'home' }" @click="logout"
                    class="text-gray-50 hover:text-green-500 px-3 py-2 rounded-md text-base font-medium">
                    로그아웃
                  </RouterLink>
                </template>
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user';

const userStore = useUserStore();

const logout = function () {
  userStore.logout();
}
</script>

<style scoped></style>