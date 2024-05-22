<template>

  <div class="flex flex-col w-full max-w-md px-8 py-8 bg-white rounded-lg shadow-2xl shadow-gray-500 mx-auto my-80">
    <div class="self-center mb-6 text-xl font-light text-gray-500 sm:text-2xl dark:text-white">
      아이디 찾기
    </div>
    <div class="mt-8">
      <form action="#" autoComplete="off">
        <div class="flex flex-col mb-2">
          <div class="flex relative ">
            <span
              class="rounded-l-md inline-flex  items-center px-3 border-t bg-white border-l border-b  border-gray-300 text-gray-500 shadow-sm text-sm">
              <svg width="15" height="15" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                <g id="SVGRepo_iconCarrier">
                  <path
                    d="M12 1C8.96243 1 6.5 3.46243 6.5 6.5C6.5 9.53757 8.96243 12 12 12C15.0376 12 17.5 9.53757 17.5 6.5C17.5 3.46243 15.0376 1 12 1Z"
                    fill="#6B7280"></path>
                  <path
                    d="M7 14C4.23858 14 2 16.2386 2 19V22C2 22.5523 2.44772 23 3 23H21C21.5523 23 22 22.5523 22 22V19C22 16.2386 19.7614 14 17 14H7Z"
                    fill="#6B7280"></path>
                </g>
              </svg>
            </span>
            <input type="text" v-model="userInfo.userName"
              class=" rounded-r-lg flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              placeholder="Your name" />
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
            <RouterLink :to="{ name: 'resetPwForm' }"
              class="inline-flex text-sm font-thin text-gray-500 hover:text-gray-700">
              비밀번호를 잊어버리셨나요?
            </RouterLink>
          </div>
        </div>
        <div class="flex w-full">
          <input type="button" @click="findId" value="제출"
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
  userName: '',
  email: ''
});

const userStore = useUserStore();

const findId = function () {
  userStore.findId(userInfo);
}
</script>

<style scoped></style>