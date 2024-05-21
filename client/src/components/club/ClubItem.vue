<template>

  <div class="m-auto overflow-hidden rounded-lg shadow-lg cursor-pointer w-80">
    <RouterLink :to="{name: 'clubDetail', params: { clubId: club.clubId }}" class="block w-full h-full">
      <img v-if="club.clubImg !== null" :src="club.clubImg" class="object-cover w-80 h-60" />
      <img v-else src="@/assets/no-image.png" class="object-cover w-80 h-60" />
      <div class="w-full p-4 bg-white">
        <p class="font-medium text-green-500 text-md">
          {{ club.category }}
        </p>
        <p class="mb-2 text-xl font-medium text-gray-800">
          {{ club.clubName }}
        </p>
        <p class="font-light text-gray-400 text-md">
          {{ shortIntro }}
        </p>
        <div class="flex flex-wrap items-center mt-4 justify-starts">
          <div class="text-xs mr-2 py-1.5 px-4 text-gray-600 bg-green-100 rounded-2xl">
            {{ club.wideArea }}
          </div>
          <div class="text-xs mr-2 py-1.5 px-4 text-gray-600 bg-green-100 rounded-2xl">
            {{ club.detailArea }}
          </div>
        </div>
      </div>
    </RouterLink>
  </div>

</template>

<script setup>
import { ref } from 'vue';

const props = defineProps({
  club: Object
})

const shortenWords = (str, length = 20) => {
  let result = '';
  if (str.length > length) {
    result = str.substr(0, length - 2) + '...';
  } else {
    result = str;
  }
  return result;
};

const shortIntro = ref(shortenWords(props.club.introduction));

if (shortIntro.value === "") {
  shortIntro.value = "...";
}
</script>

<style scoped></style>