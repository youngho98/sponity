<template>
  <div class="flex flex-col max-w-2xl bg-white rounded-lg shadow-2xl shadow-gray-500 px-8 py-8 mx-auto mt-60 mb-40">
    <div class="self-center mb-2 text-2xl font-light text-gray-500">
      회원가입
    </div>
    <span class="justify-center text-sm text-center text-gray-500">
      이미 계정이 있나요?
      <RouterLink :to="{ name: 'loginForm' }" class="text-sm text-sky-600 hover:text-sky-700">
        로그인
      </RouterLink>
    </span>
    <div class="p-6 mt-8">
      <form action="#">
        <div class="flex flex-col mb-2">
          <div class=" relative ">
            <p class="text-xs text-gray-500 mt-3 mb-1 mx-2">ID</p>
            <input type="text" v-model="userInfo.userId"
              class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              name="ID" placeholder="ID" />
          </div>
        </div>
        <div class="flex flex-col mb-2">
          <div class=" relative ">
            <p class="text-xs text-gray-500 mt-3 mb-1 mx-2">비밀번호</p>
            <input type="password" v-model="userInfo.password"
              class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              name="Password" placeholder="Password" />
          </div>
        </div>
        <div class="flex flex-col mb-2">
          <div class=" relative ">
            <p class="text-xs text-gray-500 mt-3 mb-1 mx-2">비밀번호 재확인</p>
            <input type="password" v-model="userInfo.passwordCheck"
              class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              name="Password" placeholder="Password Check" />
          </div>
        </div>
        <div class="flex flex-col mb-2">
          <div class=" relative ">
            <p class="text-xs text-gray-500 mt-3 mb-1 mx-2">이름</p>
            <input type="text" v-model="userInfo.userName"
              class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              name="Name" placeholder="Name" />
          </div>
        </div>
        <div class="flex flex-col mb-2">
          <div class=" relative ">
            <p class="text-xs text-gray-500 mt-3 mb-1 mx-2">닉네임</p>
            <input type="text" v-model="userInfo.nickname"
              class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              name="Nickname" placeholder="Nickname" />
          </div>
        </div>
        <div class="flex flex-col mb-2">
          <div class=" relative ">
            <p class="text-xs text-gray-500 mt-3 mb-1 mx-2">이메일</p>
            <input type="email" v-model="userInfo.email"
              class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              name="email" placeholder="Email Address" />
          </div>
        </div>
        <div class="flex flex-col mb-2">
          <p class="text-xs text-gray-500 mt-3 mb-1 mx-2">주소</p>
          <div class=" relative flex">
            <input type="text"
              class="w-5/12 rounded-lg border-transparent appearance-none border border-gray-300 py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              name="phone" readonly :value="userInfo.wideArea" placeholder="도/특별시/광역시" />
            <input type="text"
              class="w-1/3 rounded-lg border-transparent appearance-none border border-gray-300 py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              name="phone" readonly :value="userInfo.detailArea" placeholder="시/군/구" />
            <input type="button" @click="execDaumPostcode" value="주소 입력" :disabled="!isScriptLoaded"
              class="w-1/4 py-2 px-4 cursor-pointer bg-green-500 hover:bg-green-600 focus:ring-green-500 focus:ring-offset-green-200 text-white transition ease-in duration-200 text-center text-sm font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg " />
          </div>
        </div>
        <div class="flex w-full my-4">
          <input type="button" @click="register" value="회원가입"
            class="py-2 px-4 cursor-pointer bg-green-500 hover:bg-green-600 focus:ring-green-500 focus:ring-offset-green-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg " />
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user';
import { ref, onMounted } from 'vue';

const userInfo = ref({
  userId: '',
  password: '',
  passwordCheck: '',
  userName: '',
  nickname: '',
  email: '',
  wideArea: '',
  detailArea: ''
});

const userStore = useUserStore();

const register = function () {
  userStore.register(userInfo);
}

const isScriptLoaded = ref(false);

onMounted(() => {
  loadDaumPostcodeScript();
});

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
        userInfo.value.wideArea = arr[0];
        userInfo.value.detailArea = arr[1];
      }
    }).open();
  } else {
    console.error("Daum Postcode script has not been loaded.");
  }
}
</script>


<style scoped></style>