<template>

  <div class="flex flex-col max-w-2xl bg-white rounded-lg shadow-2xl shadow-gray-500 px-8 py-8 mx-auto my-10">
    <div class="p-6">
      <div class="self-center mb-2 text-base font-light text-gray-500">
        운동 후 식사하러 갈 맛집 추천 받기!
      </div>
      <form action="#">
        <div class="flex flex-col mb-2">
          <p class="text-xs text-gray-500 mt-3 mb-1 mx-2">현재 어디에 있나요?</p>
          <div class=" relative flex">
            <input type="text"
              class="w-5/12 rounded-lg border-transparent appearance-none border border-gray-300 py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              readonly :value="searchInfoB.wideArea" placeholder="도/특별시/광역시" />
            <input type="text"
              class="w-1/3 rounded-lg border-transparent appearance-none border border-gray-300 py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              readonly :value="searchInfoB.detailArea" placeholder="시/군/구" />
            <input type="button" @click="execDaumPostcode" value="주소 입력" :disabled="!isScriptLoaded"
              class="w-1/4 py-2 px-4 cursor-pointer bg-green-500 hover:bg-green-600 focus:ring-green-500 focus:ring-offset-green-200 text-white transition ease-in duration-200 text-center text-sm font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg " />
          </div>
        </div>
        <div class="flex flex-col mb-2">
          <div class=" relative ">
            <p class="text-xs text-gray-500 mt-3 mb-1 mx-2">어떤 음식을 먹고 싶나요?</p>
            <input type="text" v-model="searchInfoB.category"
              class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              placeholder="먹고 싶은 음식을 입력하세요." />
          </div>
        </div>
        <div class="flex w-full my-4">
          <input type="button" @click="searchRestaurant" value="검색하기"
            class="py-2 px-4 cursor-pointer bg-green-500 hover:bg-green-600 focus:ring-green-500 focus:ring-offset-green-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg " />
        </div>
      </form>
    </div>
  </div>

</template>

<script setup>
import router from '@/router';
import { useChatgptStore } from '@/stores/chatgpt';
import { useUserStore } from '@/stores/user';
import { ref, onMounted } from 'vue';

const searchInfoB = ref({
  wideArea: '',
  detailArea: '',
  category: '',
});

const userStore = useUserStore();
const chatgptStore = useChatgptStore();

const searchRestaurant = function () {
  if (userStore.loginUser.nickname === '') {
    alert("로그인이 필요합니다.");
    router.push({name: 'loginForm'});
    return;
  }
  chatgptStore.searchRestaurant(searchInfoB);
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
        searchInfoB.value.wideArea = arr[0];
        searchInfoB.value.detailArea = arr[1];
      }
    }).open();
  } else {
    console.error("Daum Postcode script has not been loaded.");
  }
}
</script>

<style scoped></style>