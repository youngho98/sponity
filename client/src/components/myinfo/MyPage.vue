<template>
  <section class="h-screen bg-white mt-52 mb-40">
    <form class="container max-w-2xl mx-auto shadow-2xl w-3/4">
      <div class="p-4 border-t-2 bg-gray-100/5 border-b">
        <div class="flex justify-center max-w-sm mx-auto md:w-full md:mx-0">
          <div class="mx-auto flex items-center space-x-4">
            <img v-if="useUserStore().loginUser.profileImg !== ''" :src="useUserStore().loginUser.profileImg" class="rounded-lg w-16 h-16" />
            <img v-else src="@/assets/avatar.png" class="rounded-lg w-16 h-16" />
            <h1 class="text-gray-600 ml-3 font-bold">
              {{ profileStore.loginUser.userId }} 님의 유저 정보
            </h1>
            <RouterLink :to="{ name: 'profileImgForm' }" class="block text-sky-600 hover:text-sky-700"> 이미지 변경 </RouterLink>
          </div>
        </div>
      </div>
      <div class="space-y-6 bg-white">
        <div class="items-center w-full p-4 space-y-0 text-gray-500 inline-flex">
          <h2 class="max-w-sm mx-auto w-1/3">
            개인 정보
          </h2>
          <div class="max-w-sm mx-auto space-y-5 w-2/3">
            <div>
              <div class=" relative flex items-center">
                <h3 class="mx-5 text-sm">이름&nbsp;&nbsp;&nbsp;&nbsp;</h3>
                <input type="text" v-model="profileStore.loginUser.userName"
                  class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
                  :placeholder="profileStore.loginUser.userName" />
              </div>
            </div>
            <div>
              <div class=" relative flex items-center">
                <h3 class="mx-5 text-sm">닉네임</h3>
                <input type="text" v-model="profileStore.loginUser.nickname"
                  class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
                  :placeholder="profileStore.loginUser.nickname" />
              </div>
            </div>
            <div>
              <div class=" relative flex items-center">
                <h3 class="mx-5 text-sm">이메일</h3>
                <input type="text" v-model="profileStore.loginUser.email"
                  class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
                  :placeholder="profileStore.loginUser.email" />
              </div>
            </div>
          </div>
        </div>
        <hr />
        <div class="items-center w-full p-8 space-y-0 text-gray-500 inline-flex">
          <h2 class="max-w-sm mx-auto w-4/12">
            지역 변경
          </h2>
          <div class="max-w-sm mx-auto space-y-5 w-5/12 pl-9 inline-flex">
            <div class=" relative ">
              <input type="text" v-model="profileStore.loginUser.wideArea"
                class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
                :placeholder="profileStore.loginUser.wideArea" />
              <input type="text" v-model="profileStore.loginUser.detailArea"
                class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
                :placeholder="profileStore.loginUser.detailArea" />
            </div>
          </div>
          <div class="text-center w-3/12 pl-6">
            <input type="button" value="변경" @click="execDaumPostcode" :disabled="!isScriptLoaded"
              class="py-2 px-4 cursor-pointer bg-green-500 hover:bg-green-600 focus:ring-green-500 focus:ring-offset-green-200 text-white w-full transition ease-in duration-200 text-center text-sm font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg " />
          </div>
        </div>
        <hr />
        <div class="items-center w-full p-8 space-y-0 text-gray-500 inline-flex">
          <h2 class="max-w-sm mx-auto w-4/12">
            비밀번호 변경
          </h2>
          <div class="max-w-sm mx-auto space-y-5 w-5/12 pl-9 inline-flex">
            <div class=" relative ">
              <p class="text-xs text-gray-500 mt-3 mb-1 mx-2">현재 비밀번호</p>
              <input type="password" v-model="password.curPw"
                class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
                placeholder="Current Password" />
              <p class="text-xs text-gray-500 mt-3 mb-1 mx-2">새 비밀번호</p>
              <input type="password" v-model="password.newPw"
                class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
                placeholder="New Password" />
              <p class="text-xs text-gray-500 mt-3 mb-1 mx-2">비밀번호 재확인</p>
              <input type="password" v-model="password.newPwCheck"
                class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
                placeholder="New Password Check" />
            </div>
          </div>
          <div class="text-center w-3/12 pl-6">
            <input type="button" value="변경" @click="changePw"
              class="py-2 px-4 cursor-pointer bg-green-500 hover:bg-green-600 focus:ring-green-500 focus:ring-offset-green-200 text-white w-full transition ease-in duration-200 text-center text-sm font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg " />
          </div>
        </div>
        <hr />

        <div class="flex justify-between px-4 pb-4 text-gray-500">
          <input type="button" value="회원 탈퇴" @click="withdraw"
            class="py-2 px-4 w-1/4 cursor-pointer bg-red-500 hover:bg-red-600 focus:ring-red-500 focus:ring-offset-red-200 text-white transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg " />
          <input type="button" value="나의 클럽" @click="showMyClubs"
            class="py-2 px-4 w-1/4 cursor-pointer bg-sky-500 hover:bg-sky-600 focus:ring-sky-500 focus:ring-offset-sky-200 text-white transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg " />
          <input type="button" value="저장" @click="modifyProfile"
            class="py-2 px-4 w-1/4 cursor-pointer bg-green-500 hover:bg-green-600 focus:ring-green-500 focus:ring-offset-green-200 text-white transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg " />
        </div>
      </div>
    </form>
  </section>
</template>

<script setup>
import router from '@/router';
import { useProfileStore } from '@/stores/profile';
import { useUserStore } from '@/stores/user';
import { onMounted, ref } from 'vue';

const userStore = useUserStore();
const profileStore = useProfileStore();

const isScriptLoaded = ref(false);

onMounted(() => {
  profileStore.getUserInfo();
  loadDaumPostcodeScript();
})

function loadDaumPostcodeScript() {
  const script = document.createElement('script');
  script.src = '//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
  script.onload = () => {
    isScriptLoaded.value = true;
  };
  document.head.appendChild(script);
}

function execDaumPostcode() {
  if (window.daum && window.daum.Postcode) {
    new daum.Postcode({
      oncomplete: (data) => {
        // Handling logic after postcode search completion
        const address = data.roadAddress;
        const arr = address.split(" ");
        profileStore.loginUser.wideArea = arr[0];
        profileStore.loginUser.detailArea = arr[1];
      }
    }).open();
  } else {
    console.error("Daum Postcode script has not been loaded.");
  }
}

const modifyProfile = function () {
  profileStore.modifyProfile();
}

const password = ref({
  curPw: '',
  newPw: '',
  newPwCheck: '',
})

const changePw = function () {
  profileStore.changePw(password);
}

const withdraw = function () {
  router.push({ name: 'withdrawPage' });
}

const showMyClubs = function() {
  userStore.loginUser.showMyClub = 1;
  router.push({ name: 'clubList' });
}
</script>

<style scoped></style>