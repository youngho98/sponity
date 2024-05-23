<template>
  <div class="my-40">
    <div class="flex mx-auto">
      <div class="mx-auto mb-2 text-2xl font-semibold text-gray-500">
        ChatGPT에게 추천 받기
      </div>
    </div>
    <ChatgptSearchFormA />
    <ChatgptSearchFormB />
  </div>
</template>

<script setup>
import ChatgptSearchFormA from '@/components/chatgpt/ChatgptSearchFormA.vue';
import ChatgptSearchFormB from '@/components/chatgpt/ChatgptSearchFormB.vue';
</script>

<style scoped></style>