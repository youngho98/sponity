<template>

  <div class="p-4 bg-white shadow-lg rounded-2xl max-w-2xl mx-auto my-40">
    <div class="flex flex-row items-start gap-4">
      <img v-if="imgUrl !== ''" :src="imgUrl" class="rounded-lg w-64 h-48" />
      <img v-else src="@/assets/no-image.png" class="rounded-lg w-64 h-48" />
      <div class="flex flex-col justify-evenly w-full h-40">
        <div>
          <p class="text-2xl font-medium text-gray-800 dark:text-white">
            {{ clubStore.clubInfo.clubName }}
          </p>
        </div>
        <div>
          <p class="text-sm font-medium text-gray-800 dark:text-white">
            클럽 프로필 이미지를 변경하세요
          </p>
        </div>
        <div class="w-full p-2 bg-green-100 rounded-lg dark:bg-white">
          <input type="file" @change="handleFileChange" />
        </div>
      </div>
    </div>
    <div class="flex items-center justify-between gap-4 mt-6">
      <button type="button" @click="deleteFile"
        class="w-1/2 px-4 py-2 text-base bg-red-500 border rounded-lg text-white hover:bg-red-700 ">
        delete
      </button>
      <button type="button" @click="uploadFile"
        class="w-1/2 px-4 py-2 text-base text-white bg-green-500 border rounded-lg hover:bg-green-700 ">
        Upload
      </button>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';
import { useClubStore } from '@/stores/club';

const route = useRoute();

const clubStore = useClubStore();

const url = `http://localhost:8080/club-manager/${route.params.clubId}/profile-img`;

const imgUrl = ref('');

const originFile = ref('');

onMounted(() => {
  axios.get(`${url}`, {
    headers: {
      Authorization: sessionStorage.getItem('access-token')
    }
  })
    .then((response) => {
      imgUrl.value = response.data;
      originFile.value = response.data;
    })
    .catch(() => {
      console.log("이미지가 없습니다.");
    })
})

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
    imgUrl.value = response.data;
    originFile.value = response.data;
    console.log('File uploaded successfully:', response.data);
    alert("클럽 프로필 이미지가 변경되었습니다.");
  } catch (error) {
    console.error('Error uploading file:', error);
    alert("클럽 프로필 이미지 변경에 실패했습니다.");
  }
};

// 기존 이미지 삭제
const deleteFile = async function () {
  try {
    const fileName = originFile.value.split("/")[4];
    const response = await axios.delete(`${url}/${fileName}`, {
      headers: {
        Authorization: sessionStorage.getItem('access-token')
      }
    });
    imgUrl.value = '';
    console.log('File deleted successfully', response.data);
    alert("클럽 프로필 이미지가 삭제되었습니다.");
  } catch (error) {
    console.error('Error deleting file:', error);
    alert("클럽 프로필 이미지가 삭제에 실패했습니다.");
  }
}
</script>

<style scoped>
/* Add your styles here */
</style>
