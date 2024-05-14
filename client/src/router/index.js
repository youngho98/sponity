import { createRouter, createWebHistory } from 'vue-router';

import HomeView from '@/views/HomeView.vue';

import RegisterForm from '@/components/user/RegisterForm.vue';
import LoginForm from '@/components/user/LoginForm.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/join',
      name: 'registerForm',
      component: RegisterForm,
    },
    {
      path: '/login',
      name: 'loginForm',
      component: LoginForm,
    },
  ]
})

export default router
