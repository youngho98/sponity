<template>

  <div class="w-96 p-4 mx-auto my-80 bg-white shadow-2xl rounded-2xl">
    <div class="w-full h-full text-center">
      <div class="flex flex-col justify-between h-full">
        <svg width="40" height="40" class="w-12 h-12 m-auto mt-4 text-green-500" fill="currentColor"
          viewBox="0 0 1792 1792" xmlns="http://www.w3.org/2000/svg">
          <path
            d="M704 1376v-704q0-14-9-23t-23-9h-64q-14 0-23 9t-9 23v704q0 14 9 23t23 9h64q14 0 23-9t9-23zm256 0v-704q0-14-9-23t-23-9h-64q-14 0-23 9t-9 23v704q0 14 9 23t23 9h64q14 0 23-9t9-23zm256 0v-704q0-14-9-23t-23-9h-64q-14 0-23 9t-9 23v704q0 14 9 23t23 9h64q14 0 23-9t9-23zm-544-992h448l-48-117q-7-9-17-11h-317q-10 2-17 11zm928 32v64q0 14-9 23t-23 9h-96v948q0 83-47 143.5t-113 60.5h-832q-66 0-113-58.5t-47-141.5v-952h-96q-14 0-23-9t-9-23v-64q0-14 9-23t23-9h309l70-167q15-37 54-63t79-26h320q40 0 79 26t54 63l70 167h309q14 0 23 9t9 23z">
          </path>
        </svg>
        <p class="mt-4 text-xl font-bold text-gray-800">
          계정 삭제
        </p>
        <p class="px-6 py-2 text-xs text-gray-600">
          정말로 계정을 삭제하시겠습니까? 다시 복구할 수 없습니다.
        </p>
        <p class="px-6 py-4 text-l text-gray-600">
          비밀번호를 입력하세요
        </p>
        <input type="password" v-model="password.curPw"
          class="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent" />
        <div class="flex items-center justify-between w-full gap-4 mt-8">
          <input type="button" value="회원 탈퇴" @click="withdraw"
            class="py-2 px-4 cursor-pointer bg-red-500 hover:bg-red-600 focus:ring-red-500 focus:ring-offset-red-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg " />
          <input type="button" value="취소" @click="cancel"
            class="py-2 px-4 cursor-pointer bg-white hover:bg-gray-100 focus:ring-green-500 focus:ring-offset-green-200 text-green-500 w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg " />
        </div>
      </div>
    </div>
  </div>

</template>

<script setup>
import router from '@/router';
import { useProfileStore } from '@/stores/profile';
import { ref } from 'vue';

const password = ref({
  curPw: ''
});

const profileStore = useProfileStore();

const withdraw = function() {
  profileStore.withdraw(password);
}

const cancel = function() {
  router.replace({name: 'myPage'});
}
</script>

<style scoped></style>