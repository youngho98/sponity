<script setup>
import { RouterLink, RouterView } from 'vue-router'
import TheHeaderNav from '@/components/common/TheHeaderNav.vue'
import TheFooterNav from '@/components/common/TheFooterNav.vue'
</script>

<template>
  <TheHeaderNav />
  <RouterView />
  <TheFooterNav />
</template>

<style>
.back-black {
  background-color: #171819;
}
</style>
