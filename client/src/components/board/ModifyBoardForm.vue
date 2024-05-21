<template>
  <div class="flex flex-col max-w-3xl bg-white rounded-lg shadow shadow-gray-500 px-8 py-8 mx-auto my-24">
    <div class="self-center mb-2 text-2xl font-light text-gray-500">
      게시글 수정
    </div>
    <div class="p-6 mt-8">
      <form action="#">
        <div class="flex flex-col mb-2">
          <div class="relative">
            <p class="text-xs text-gray-500 mt-3 mx-3">Title</p>
            <input type="text" v-model="boardInfo.title"
              class="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              placeholder="제목을 입력하세요." />
          </div>
        </div>
        <div class="flex flex-col mb-2">
          <div class="relative">
            <p class="text-xs text-gray-500 mt-3 mx-3">Content</p>
            <textarea v-model="boardInfo.content"
              class="rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full h-32 py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
              placeholder="내용을 입력하세요." />
          </div>
        </div>
        <div class="flex flex-col mb-2">
          <div class="relative">
            <p class="text-xs text-gray-500 mt-3 mx-3">Image</p>
            <div class="flex justify-between">
              <div class="w-1/3 p-2">
                <input type="file" @change="handleFileChange1" />
              </div>
              <div class="w-1/3 p-2">
                <input type="file" @change="handleFileChange2" />
              </div>
              <div class="w-1/3 p-2">
                <input type="file" @change="handleFileChange3" />
              </div>
            </div>
          </div>
        </div>
        <div class="flex w-full my-4">
          <input type="button" @click="uploadFile" value="Save"
            class="py-2 px-4 bg-green-600 hover:bg-green-700 focus:ring-green-500 focus:ring-offset-green-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2 rounded-lg" />
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
import { useBoardStore } from '@/stores/board';
import router from '@/router';

const route = useRoute();

const boardInfo = ref({
  title: '',
  content: '',
});

const boardStore = useBoardStore();

onMounted(() => {
  boardStore.getBoardInfo(route.params.clubId, route.params.boardId);
  boardInfo.value.title = boardStore.boardInfo.title;
  boardInfo.value.content = boardStore.boardInfo.content;
})

// 파일 등록
const file1 = ref(null);
const file2 = ref(null);
const file3 = ref(null);

const handleFileChange1 = (event) => {
  file1.value = event.target.files[0];
};

const handleFileChange2 = (event) => {
  file2.value = event.target.files[0];
};

const handleFileChange3 = (event) => {
  file3.value = event.target.files[0];
};

const uploadFile = async () => {
  const formData = new FormData();
  formData.append('title', boardInfo.value.title);
  formData.append('content', boardInfo.value.content);
  if (file1.value) formData.append('img1', file1.value);
  if (file2.value) formData.append('img2', file2.value);
  if (file3.value) formData.append('img3', file3.value);

  try {
    const response = await axios.put(`http://localhost:8080/club/${route.params.clubId}/board/${route.params.boardId}`, formData, {
      headers: {
        Authorization: sessionStorage.getItem('access-token'),
        'Content-Type': 'multipart/form-data'
      }
    });
    console.log('File uploaded successfully:', response.data);
    alert("게시글이 수정되었습니다.");
    router.replace({name: 'boardDetail'});
  } catch (error) {
    let num = error.response.data;
    let message = "";
    switch (num) {
      case 1:
        message = "제목을 입력하세요.";
        break;
      case 2:
        message = "내용을 입력하세요.";
        break;
      case 3:
        message = "AWS s3 server error.";
        break;
      default:
        message = "server error";
    }
    alert(message);
    console.error('Error uploading file:', error);
  }
};
</script>

<style scoped></style>
