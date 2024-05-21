<template>

  <li class="flex flex-row mb-2 border-gray-400 w-1/3">
    <div
      class="transition duration-500 shadow ease-in-out transform hover:-translate-y-1 hover:shadow-lg select-none bg-white dark:bg-gray-800 rounded-md flex flex-1 items-center p-4">
      <div class="flex flex-col items-center justify-center w-12 h-12 mr-4">
        <a href="#" class="relative block">
          <img alt="profil" src="@/assets/avatar.png" class="mx-auto object-cover rounded-full h-12 w-12 " />
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
      <button v-if="userStore.loginUser.userStatus === 3" @click="dropdown" class="flex justify-end w-24 text-right">
        <svg width="12" fill="currentColor" height="12"
          class="text-gray-500 hover:text-gray-800" viewBox="0 0 1792 1792"
          xmlns="http://www.w3.org/2000/svg">
          <path
            d="M1363 877l-742 742q-19 19-45 19t-45-19l-166-166q-19-19-19-45t19-45l531-531-531-531q-19-19-19-45t19-45l166-166q19-19 45-19t45 19l742 742q19 19 19 45t-19 45z">
          </path>
        </svg>
      </button>
    </div>
    <div v-if="manage === true" class="text-center text-xs font-semibold">
        <button type="button" class="mx-2 my-3 text-green-600 hover:text-green-700">클럽장위임</button>
        <button type="button" class="mx-2 my-3 text-red-600 hover:text-red-700">회원삭제</button>
    </div>
  </li>


</template>

<script setup>
import { useUserStore } from '@/stores/user';
import { ref } from 'vue';

const userStore = useUserStore();

const manage = ref(false);

const dropdown = function() {
  manage.value = !(manage.value);
}

const props = defineProps({
  member: Object
})
</script>

<style scoped></style>