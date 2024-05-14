<template>
  <div>
    <nav class="bg-white shadow shadow-gray-500 h-24 place-content-center">
      <div class="px-8 mx-auto max-w-7xl">
        <div class="flex items-center justify-between h-16">
          <div class="w-full justify-between flex items-center">
            <a class="flex-shrink-0" href="/">
              <img class="w-40 h-12" src="@/assets/sponity-logo.png" alt="sponity" />
            </a>
            <div class="block">
              <div class="flex items-baseline ml-10 space-x-4">
                <RouterLink to="/"
                  class="text-green-500  hover:text-green-800 px-3 py-2 rounded-md text-xl font-medium">
                  Home
                </RouterLink>
                <RouterLink class="text-green-500  hover:text-green-800 px-3 py-2 rounded-md text-xl font-medium">
                  Club
                </RouterLink>
                <RouterLink :to="{name: 'loginForm'}"
                  class="text-green-500  hover:text-green-800 px-3 py-2 rounded-md text-xl font-medium">
                  Login
                </RouterLink>
                <RouterLink :to="{name: 'registerForm'}"
                  class="text-green-500  hover:text-green-800 px-3 py-2 rounded-md text-xl font-medium">
                  SignUp
                </RouterLink>
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>
  </div>
</template>

<script setup>

</script>

<style scoped></style>