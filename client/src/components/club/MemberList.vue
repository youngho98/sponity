<template>

  <div class="container flex flex-col items-center justify-center w-3/5 mx-auto mt-20 mb-96">
    <div class="w-full px-4 py-4 mb-2 bg-white border rounded-md shadow sm:px-6 dark:bg-gray-800">
      <h3 class="text-xl font-medium leading-6 text-gray-900 dark:text-white">
        멤버 목록
      </h3>
      <p class="max-w-2xl mt-1 text-sm text-gray-500 dark:text-gray-200">
        클럽에 가입한 멤버들의 목록과 세부 정보
      </p>
    </div>
    <div class="grid grid-cols-2">
      <MemberItem v-for="member in memberList" :key="member.userId" :member="member"/>
    </div>
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
      memberList.value = response.data;
    })
    .catch(() => {
      alert("회원 조회에 실패했습니다.");
    })
}
</script>

<style scoped></style>