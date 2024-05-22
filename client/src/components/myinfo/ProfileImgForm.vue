<template>

  <div class="p-4 bg-white shadow-2xl rounded-2xl max-w-xl mx-auto my-80">
    <div class="flex flex-row items-start gap-4">
      <img v-if="imgUrl !== ''" :src="imgUrl" class="rounded-lg w-40 h-40" />
      <img v-else src="@/assets/avatar.png" class="rounded-lg w-40 h-40" />
      <div class="flex flex-col justify-evenly w-full h-40">
        <div>
          <p class="text-2xl font-medium text-gray-800 dark:text-white">
            {{ userStore.loginUser.nickname }} 님
          </p>
        </div>
        <div>
          <p class="text-sm font-medium text-gray-800 dark:text-white">
            프로필 이미지를 변경하세요
          </p>
        </div>
        <div class="w-full p-2 bg-green-100 rounded-lg dark:bg-white">
          <input type="file" @change="handleFileChange" />
        </div>
      </div>
    </div>
    <div class="flex items-center justify-between gap-4 mt-6">
      <button type="button" @click="deleteFile"
        class="w-1/2 px-4 py-2 text-base bg-red-500 border rounded-lg text-white font-semibold hover:bg-red-600 ">
        이미지 삭제
      </button>
      <button type="button" @click="uploadFile"
        class="w-1/2 px-4 py-2 text-base text-white bg-green-500 border rounded-lg font-semibold hover:bg-green-600 ">
        이미지 업로드
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useUserStore } from '@/stores/user';

const userStore = useUserStore();

const url = "http://localhost:8080/my-page/profile-img";
const imgUrl = ref(userStore.loginUser.profileImg);

// 파일 업로드
const file = ref(null);

const handleFileChange = (event) => {
  file.value = event.target.files[0];
  imgUrl.value = URL.createObjectURL(event.target.files[0]);
};

const uploadFile = async () => {
  if (!file.value) {
    console.error('No file selected');
    return;
  }

  try {
    const formData = new FormData();
    formData.append('img', file.value);

    const response = await axios.post(`${url}`, formData, {
      headers: {
        Authorization: sessionStorage.getItem('access-token'),
        'Content-Type': 'multipart/form-data'
      }
    });
    userStore.loginUser.profileImg = response.data;
    console.log('File uploaded successfully:', response.data);
  } catch (error) {
    console.error('Error uploading file:', error);
  }
};

// 기존 이미지 삭제
const fileName = userStore.loginUser.profileImg.split("/")[4];

const deleteFile = async function () {
  try {
    const response = await axios.delete(`${url}/${fileName}`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    });
    userStore.loginUser.profileImg = '';
    imgUrl.value = '';
    console.log('File deleted successfully', response.data);
  } catch (error) {
    console.error('Error deleting file:', error);
  }
}
</script>

<style scoped>
/* Add your styles here */
</style>
