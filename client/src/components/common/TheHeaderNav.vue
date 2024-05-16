<template>
  <div>
    <nav class="bg-white shadow shadow-gray-500 h-24 place-content-center">
      <div class="px-8 mx-auto max-w-7xl">
        <div class="flex items-center justify-between h-16">
          <div class="w-full justify-between flex items-center">
            <div class="flex">
              <a class="flex-shrink-0" href="/">
                <img class="w-40 h-12" src="@/assets/sponity-logo.png" alt="sponity" />
              </a>
              <RouterLink to="/"
                class="text-green-500  hover:text-green-800 px-3 py-2 ml-12 rounded-md text-xl font-medium">
                Home
              </RouterLink>
              <RouterLink class="text-green-500  hover:text-green-800 px-3 py-2 rounded-md text-xl font-medium">
                Club
              </RouterLink>
            </div>
            <div class="flex">
              <div class="flex items-baseline ml-10 space-x-4">
                <template v-if="store.loginUserNickname === ''">
                  <RouterLink :to="{ name: 'loginForm' }"
                    class="text-green-500  hover:text-green-800 px-3 py-2 rounded-md text-xl font-medium">
                    로그인
                  </RouterLink>
                  <RouterLink :to="{ name: 'registerForm' }"
                    class="text-green-500  hover:text-green-800 px-3 py-2 rounded-md text-xl font-medium">
                    회원가입
                  </RouterLink>
                </template>
                <template v-else>
                  <span class="text-green-800 pl-3 py-2 rounded-md text-xl font-medium">
                    {{ store.loginUserNickname }}
                  </span>
                  <span class="text-green-500 pr-3 py-2 rounded-md text-xl font-medium">
                    님 환영합니다.</span>
                  <RouterLink :to="{ name: 'home' }" @click="logout"
                    class="text-green-500  hover:text-green-800 px-3 py-2 rounded-md text-xl font-medium">
                    로그아웃
                  </RouterLink>
                </template>
                <!-- <RouterLink :to="{ name: 'home' }" @click="test"
                  class="text-green-500  hover:text-green-800 px-3 py-2 rounded-md text-xl font-medium">
                  Test
                </RouterLink> -->
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
import axios from 'axios';

const store = useUserStore();

const logout = function () {
  store.logout();
}

// const test = function () {
//   axios.get("http://localhost:8080/test", {
//     headers: {
//       Authorization: sessionStorage.getItem('access-token')
//     }
//   })
//     .then(() => {
//       alert("success");
//     })
//     .catch(() => {
//       alert("fail");
//     })
// }
</script>

<style scoped></style>