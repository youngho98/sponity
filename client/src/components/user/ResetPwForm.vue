<template>

  <div class="flex flex-col w-full max-w-md px-8 py-8 bg-white rounded-lg shadow-2xl shadow-gray-500 mx-auto my-40">
    <div class="self-center mb-6 text-xl font-light text-gray-500 sm:text-2xl dark:text-white">
      비밀번호 찾기
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
            <input type="text" v-model="userInfo.userId"
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
                  d="M1792 710v794q0 66-47 113t-113 47h-1472q-66 0-113-47t-47-113v-794q44 49 101 87 362 246 497 345 57 42 92.5 65.5t94.5 48 110 24.5h2q51 0 110-24.5t94.5-48 92.5-65.5q170-123 498-345 57-39 100-87zm0-294q0 79-49 151t-122 123q-376 261-468 325-10 7-42.5 30.5t-54 38-52 32.5-57.5 27-50 9h-2q-23 0-50-9t-57.5-27-52-32.5-54-38-42.5-30.5q-91-64-262-182.5t-205-142.5q-62-42-117-115.5t-55-136.5q0-78 41.5-130t118.5-52h1472q65 0 112.5 47t47.5 113z">
                </path>
              </svg>
            </span>
            <input type="text" v-model="userInfo.email"
              class=" rounded-r-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              placeholder="Your email address" />
          </div>
        </div>
        <div class="flex items-center mb-6 -mt-4">
          <div class="flex ml-auto">
            <RouterLink :to="{ name: 'findIdForm' }"
              class="inline-flex text-sm font-thin text-gray-500 hover:text-gray-700">
              아이디를 모르시나요?
            </RouterLink>
          </div>
        </div>
        <div class="flex w-full">
          <input type="button" @click="resetPw" value="제출"
            class="py-2 px-4 cursor-pointer bg-green-500 hover:bg-green-600 focus:ring-green-500 focus:ring-offset-green-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg " />
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

const userInfo = ref({
  userId: '',
  email: ''
});

const userStore = useUserStore();

const resetPw = function () {
  userStore.resetPw(userInfo);
}
</script>

<style scoped></style>