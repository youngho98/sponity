<template>
  <section class="h-screen bg-white my-24">
    <form class="container max-w-2xl mx-auto shadow-md w-3/4">
      <div class="p-4 border-t-2 border-green-400 rounded-lg bg-gray-100/5 ">
        <div class="max-w-sm mx-auto md:w-full md:mx-0">
          <div class="inline-flex items-center space-x-4">
            <!-- <a href="#" class="relative block">
              <img alt="profil" src="/images/person/1.jpg" class="mx-auto object-cover rounded-full h-16 w-16 " />
            </a> -->
            <h1 class="text-gray-600 ml-3 font-bold">
              {{ store.loginUserNickname }}
            </h1>
          </div>
        </div>
      </div>
      <div class="space-y-6 bg-white">
        <div class="items-center w-full p-4 space-y-0 text-gray-500 inline-flex">
          <h2 class="max-w-sm mx-auto w-1/3">
            Personal info
          </h2>
          <div class="max-w-sm mx-auto space-y-5 w-2/3">
            <div>
              <div class=" relative flex items-center">
                <h3 class="mx-5 text-sm">Name</h3>
                <input type="text"
                  class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
                  placeholder="Name" />
              </div>
            </div>
            <div>
              <div class=" relative flex items-center">
                <h3 class="mx-5 text-sm">Nickname</h3>
                <input type="text"
                  class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
                  placeholder="Nickname" />
              </div>
            </div>
            <div>
              <div class=" relative flex items-center">
                <h3 class="mx-5 text-sm">Email</h3>
                <input type="text"
                  class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
                  placeholder="Email" />
              </div>
            </div>
          </div>
        </div>
        <hr />
        <div class="items-center w-full p-8 space-y-0 text-gray-500 inline-flex">
          <h2 class="max-w-sm mx-auto w-4/12">
            Change Area
          </h2>
          <div class="max-w-sm mx-auto space-y-5 w-5/12 pl-9 inline-flex">
            <div class=" relative ">
              <input type="text"
                class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
                placeholder="Wide Area" />
              <input type="text"
                class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
                placeholder="Detail Area" />
            </div>
          </div>
          <div class="text-center w-3/12 pl-6">
            <button type="button"
              class="py-2 px-4  bg-green-600 hover:bg-green-700 focus:ring-green-500 focus:ring-offset-green-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg ">
              Change
            </button>
          </div>
        </div>
        <hr />
        <div class="items-center w-full p-8 space-y-0 text-gray-500 inline-flex">
          <h2 class="max-w-sm mx-auto w-4/12">
            Change password
          </h2>
          <div class="max-w-sm mx-auto space-y-5 w-5/12 pl-9 inline-flex">
            <div class=" relative ">
              <input type="text"
                class=" rounded-lg border-transparent flex-1 appearance-none border border-gray-300 w-full py-2 px-4 bg-white text-gray-700 placeholder-gray-400 shadow-sm text-base focus:outline-none focus:ring-2 focus:ring-green-600 focus:border-transparent"
                placeholder="Password" />
            </div>
          </div>
          <div class="text-center w-3/12 pl-6">
            <button type="button"
              class="py-2 px-4  bg-pink-600 hover:bg-pink-700 focus:ring-pink-500 focus:ring-offset-pink-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg ">
              Change
            </button>
          </div>
        </div>
        <hr />
        <div class="px-4 pb-4 ml-auto text-gray-500 w-1/3">
          <button type="submit"
            class="py-2 px-4  bg-blue-600 hover:bg-blue-700 focus:ring-blue-500 focus:ring-offset-blue-200 text-white w-full transition ease-in duration-200 text-center text-base font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-offset-2  rounded-lg ">
            Save
          </button>
        </div>
      </div>
    </form>
  </section>
  <RouterView />
</template>

<script setup>
import { useUserStore } from '@/stores/user';
import { onMounted } from 'vue';

const store = useUserStore();

onMounted(() => {
  const userInfo = store.getUserInfo(store.loginUserNickname);
})
</script>

<style scoped></style>