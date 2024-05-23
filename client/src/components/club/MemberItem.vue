<template>

  <div class="mx-6 mt-5 mb-1">
    <div
      class="transition duration-500 shadow ease-in-out transform hover:-translate-y-1 hover:shadow-lg select-none bg-white dark:bg-gray-800 rounded-md flex flex-1 items-center p-4">
      <div class="flex flex-col items-center justify-center w-12 h-8 mr-4">
        <a href="#" class="relative block">
          <img v-if="member.profileImg !== null" :src="member.profileImg" class="mx-auto object-cover rounded-full h-12 w-12">
          <img v-else src="@/assets/avatar.png" class="mx-auto object-cover rounded-full h-12 w-12" />
        </a>
      </div>
      <div class="flex-1 pl-1">
        <div class="font-medium">
          {{ member.nickname }}
        </div>
        <div class="text-xs text-gray-600">
          {{ member.email }}
        </div>
      </div>
      <button v-if="userStore.loginUser.userStatus === 3 && userStore.loginUser.nickname !== member.nickname" @click="dropdown" class="flex justify-end w-24 text-right">
        <svg width="12" fill="currentColor" height="12" class="text-gray-500 hover:text-gray-800"
          viewBox="0 0 1792 1792" xmlns="http://www.w3.org/2000/svg">
          <path
            d="M1363 877l-742 742q-19 19-45 19t-45-19l-166-166q-19-19-19-45t19-45l531-531-531-531q-19-19-19-45t19-45l166-166q19-19 45-19t45 19l742 742q19 19 19 45t-19 45z">
          </path>
        </svg>
      </button>
      <button v-else @click="dropdown" class="flex justify-end w-24 text-right" style="visibility: hidden;">
        <svg width="12" fill="currentColor" height="12" class="text-gray-500 hover:text-gray-800"
          viewBox="0 0 1792 1792" xmlns="http://www.w3.org/2000/svg">
          <path
            d="M1363 877l-742 742q-19 19-45 19t-45-19l-166-166q-19-19-19-45t19-45l531-531-531-531q-19-19-19-45t19-45l166-166q19-19 45-19t45 19l742 742q19 19 19 45t-19 45z">
          </path>
        </svg>
      </button>
    </div>
    <div v-if="manage === true" class="flex text-center text-xs font-semibold justify-center">
      <div>
        <button type="button" @click="changeLeader"
          class="text-green-600 hover:text-green-700 hover:bg-gray-200 mx-3 px-2 py-2 w-20 border border-gray-100 rounded">클럽장
          위임</button>
      </div>
      <div>
        <button type="button" @click="deleteMember"
          class="text-red-600 hover:text-red-700 hover:bg-gray-200 mx-3 px-2 py-2 w-20 border border-gray-100 rounded">회원
          삭제</button>
      </div>
    </div>
  </div>

</template>

<script setup>
import { useClubManagerStore } from '@/stores/clubManager';
import { useUserStore } from '@/stores/user';
import { ref } from 'vue';
import { useRoute } from 'vue-router';

const userStore = useUserStore();
const clubManagerStore = useClubManagerStore();
const route = useRoute();

const manage = ref(false);

const dropdown = function () {
  manage.value = !(manage.value);
}

const props = defineProps({
  member: Object
})

const changeLeader = function () {
  clubManagerStore.changeLeader(route.params.clubId, props.member.userId);
}

const deleteMember = function() {
  clubManagerStore.deleteMember(route.params.clubId, props.member.userId);
}
</script>

<style scoped></style>