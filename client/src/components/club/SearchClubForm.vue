<template>
  <div class="flex flex-col max-w-3xl bg-white rounded-lg shadow shadow-gray-500 px-8 py-8 mx-auto my-24">
    <div class="self-center mb-2 text-2xl font-light text-gray-500">
      Search Clubs
    </div>
    <div class="p-6 mt-8">
      <form action="#">
        <p class="text-xs text-gray-500 mt-3 mx-3">Category</p>
        <div class="flex flex-wrap text-center mb-2">
          <div v-for="(item, index) in categoryList" :key="index"
            class="flex items-center border border-gray-200 rounded px-3 mx-3 my-2">
            <input type="radio" :id="item.key" v-model="searchInfo.category" :value="item.value"
              class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 focus:ring-1" />
            <label :for="item.key" class="w-full py-4 ms-2 text-sm font-medium text-gray-900 ">{{
              item.value }}</label>
          </div>
        </div>
        <div class="flex flex-col mb-2">
          <p class="text-xs text-gray-500 mt-3 mx-3">Club Area</p>
          <div class=" relative flex">
            <input type="text"
              class="w-5/12 rounded-lg border-transparent appearance-none border border-gray-300 py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              name="phone" readonly :value="searchInfo.wideArea" placeholder="도/특별시/광역시" />
            <input type="text"
              class="w-1/3 rounded-lg border-transparent appearance-none border border-gray-300 py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              name="phone" readonly :value="searchInfo.detailArea" placeholder="시/군/구" />
            <input type="button" @click="execDaumPostcode" value="주소 입력" :disabled="!isScriptLoaded"
              class="w-1/4 py-2 px-4  bg-green-600 hover:bg-green-700 focus:ring-green-500 focus:ring-offset-green-200 text-white transition ease-in duration-200 text-center text-sm font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg " />
          </div>
        </div>
        <div class="flex flex-col mb-2">
          <div class=" relative ">
            <p class="text-xs text-gray-500 mt-3 mx-3">Club Name</p>
            <input type="text" v-model="searchInfo.keyword"
              class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              placeholder="Club Name" />
          </div>
        </div>
        <div class="flex w-full my-4">
          <input type="button" @click="insertSearch" value="Search"
            class="py-2 px-4  bg-green-600 hover:bg-green-700 focus:ring-green-500 focus:ring-offset-green-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg " />
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import router from '@/router';
import { useClubStore } from '@/stores/club';
import { useUserStore } from '@/stores/user';
import { ref, onMounted } from 'vue';

const searchInfo = ref({
  category: '',
  wideArea: '',
  detailArea: '',
  keyword: '',
});

const userStore = useUserStore();
const clubStore = useClubStore();

const insertSearch = function () {
  userStore.loginUser.showMyClub = 0;
  clubStore.insertSearch(searchInfo);
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
        searchInfo.value.wideArea = arr[0];
        searchInfo.value.detailArea = arr[1];
      }
    }).open();
  } else {
    console.error("Daum Postcode script has not been loaded.");
  }
}

const categoryList = ref([
  {
    key: "00",
    value: "축구 ⚽️",
  },
  {
    key: "01",
    value: "농구 🏀",
  },
  {
    key: "02",
    value: "야구 ⚾️",
  },
  {
    key: "03",
    value: "족구 🏐",
  },
  {
    key: "04",
    value: "배드민턴 🏸",
  },
  {
    key: "05",
    value: "탁구 🏓",
  },
  {
    key: "06",
    value: "러닝 🏃‍♂️",
  },
  {
    key: "07",
    value: "골프 ⛳️",
  },
  {
    key: "08",
    value: "등산 🏔️",
  },
  {
    key: "09",
    value: "클라이밍 🧗‍♀️",
  },
  {
    key: "10",
    value: "볼링 🎳",
  },
  {
    key: "11",
    value: "당구 🎱",
  },
  {
    key: "12",
    value: "테니스 🎾",
  },
  {
    key: "13",
    value: "자전거 🚴‍♂️",
  },
]);
</script>


<style scoped></style>