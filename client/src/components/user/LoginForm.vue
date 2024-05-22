<template>

  <div class="flex flex-col w-full max-w-md px-8 py-8 bg-white rounded-lg shadow-2xl shadow-gray-500 mx-auto my-40">
    <div class="self-center mb-6 text-xl font-light text-gray-500 sm:text-2xl dark:text-white">
      로그인
    </div>
    <div class="mt-8">
      <form action="#" autoComplete="off">
        <div class="flex flex-col mb-2">
          <div class="flex relative ">
            <span
              class="rounded-l-md inline-flex  items-center px-3 border-t bg-white border-l border-b  border-gray-300 text-gray-500 shadow-sm text-sm">
              <svg width="15" height="15" fill="currentColor" viewBox="0 0 36 36" xmlns="http://www.w3.org/2000/svg">
                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                <g id="SVGRepo_iconCarrier">
                  <path fill="#6B7280" d="M36 32a4 4 0 0 1-4 4H4a4 4 0 0 1-4-4V4a4 4 0 0 1 4-4h28a4 4 0 0 1 4 4v28z">
                  </path>
                  <path
                    d="M5.717 9.156c0-1.55.992-2.418 2.325-2.418s2.325.868 2.325 2.418v17.611c0 1.551-.992 2.418-2.325 2.418s-2.325-.867-2.325-2.418V9.156zm7.44.156c0-1.427.992-2.388 2.387-2.388h5.148c6.945 0 10.914 4.465 10.914 11.348C31.605 24.783 27.389 29 21.001 29h-5.395c-1.023 0-2.449-.559-2.449-2.325V9.312zm4.65 15.409h3.132c4 0 5.828-2.945 5.828-6.666c0-3.969-1.859-6.852-6.139-6.852h-2.822v13.518z"
                    fill="#FFF"></path>
                </g>
              </svg>
            </span>
            <input type="text" v-model="loginInfo.userId"
              class=" rounded-r-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              placeholder="Your ID" />
          </div>
        </div>
        <div class="flex flex-col mb-6">
          <div class="flex relative ">
            <span
              class="rounded-l-md inline-flex  items-center px-3 border-t bg-white border-l border-b  border-gray-300 text-gray-500 shadow-sm text-sm">
              <svg width="15" height="15" fill="currentColor" viewBox="0 0 1792 1792"
                xmlns="http://www.w3.org/2000/svg">
                <path
                  d="M1376 768q40 0 68 28t28 68v576q0 40-28 68t-68 28h-960q-40 0-68-28t-28-68v-576q0-40 28-68t68-28h32v-320q0-185 131.5-316.5t316.5-131.5 316.5 131.5 131.5 316.5q0 26-19 45t-45 19h-64q-26 0-45-19t-19-45q0-106-75-181t-181-75-181 75-75 181v320h736z">
                </path>
              </svg>
            </span>
            <input type="password" v-model="loginInfo.password"
              class=" rounded-r-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              placeholder="Your password" />
          </div>
        </div>
        <div class="flex items-center mb-6 -mt-4">
          <div class="flex ml-auto">
            <RouterLink :to="{ name: 'findIdForm' }"
              class="inline-flex text-sm font-thin text-gray-500 hover:text-gray-700">
              ID/비밀번호를 잊어버리셨나요?
            </RouterLink>
          </div>
        </div>
        <div class="flex w-full">
          <input type="button" @click="login" value="로그인"
            class="py-2 px-4  bg-green-500 hover:bg-green-600 focus:ring-green-500 focus:ring-offset-green-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2 cursor-pointer rounded-lg " />
        </div>
      </form>
    </div>
    <div class="flex items-center justify-center mt-6">
      <RouterLink :to="{ name: 'registerForm' }"
        class="inline-flex items-center text-xs font-thin text-center text-gray-500 hover:text-gray-700">
        <span class="ml-2">
          혹시 계정이 없으신가요?
        </span>
      </RouterLink>
    </div>
  </div>

</template>

<script setup>
import { useUserStore } from '@/stores/user';
import { ref } from 'vue';
import { RouterLink } from 'vue-router';

const loginInfo = ref({
  userId: '',
  password: ''
});

const userStore = useUserStore();

const login = function () {
  userStore.login(loginInfo);
}
</script>

<style scoped></style>