<template>

  <div class="container flex flex-col items-center justify-center w-3/5 mx-auto">
    <div class="w-full px-4 py-5 mb-2 bg-white border rounded-md shadow sm:px-6 dark:bg-gray-800">
      <h3 class="text-lg font-medium leading-6 text-gray-900 dark:text-white">
        User database
      </h3>
      <p class="max-w-2xl mt-1 text-sm text-gray-500 dark:text-gray-200">
        Details and informations about user.
      </p>
    </div>
    <ul class="flex flex-wrap">
      <MemberItem v-for="member in memberList" :key="member.userId" :member="member"/>
    </ul>
  </div>

</template>

<script setup>
import { onMounted, ref } from 'vue';
import MemberItem from '@/components/club/MemberItem.vue';
import axios from 'axios';
import { useRoute } from 'vue-router';

const memberList = ref([]);
const route = useRoute();

onMounted(() => {
  getMemberList();
});

const getMemberList = function() {
  axios.get(`http://localhost:8080/club/${route.params.clubId}/member-list`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    })
    .then((response) => {
      memberList = response.data;
    })
    .catch(() => {
      alert("회원 조회에 실패했습니다.");
    })
}
</script>

<style scoped></style>