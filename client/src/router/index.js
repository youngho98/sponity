import { createRouter, createWebHistory } from 'vue-router';

import HomeView from '@/views/HomeView.vue';
import MyPageView from '@/views/MyPageView.vue';
import ClubManagerView from '@/views/ClubManagerView.vue';
import ClubView from '@/views/ClubView.vue';

import RegisterForm from '@/components/user/RegisterForm.vue';
import LoginForm from '@/components/user/LoginForm.vue';
import FindIdForm from '@/components/user/FindIdForm.vue';
import ResetPwForm from '@/components/user/ResetPwForm.vue';

import MyPage from '@/components/myinfo/MyPage.vue';
import WithdrawPage from '@/components/myinfo/WithdrawPage.vue';
import ProfileImgForm from '@/components/myinfo/ProfileImgForm.vue';

import CreateClubForm from '@/components/clubmanager/CreateClubForm.vue';

import SearchClubForm from '@/components/club/SearchClubForm.vue';
import SearchClubResult from '@/components/club/SearchClubResult.vue';
import ClubDetail from '@/components/club/ClubDetail.vue';


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
    {
      path: '/find-id',
      name: 'findIdForm',
      component: FindIdForm,
    },
    {
      path: '/reset-pw',
      name: 'resetPwForm',
      component: ResetPwForm,
    },
    {
      path: '/my-page',
      name: 'myPageView',
      component: MyPageView,
      children: [
        {
          path: '',
          name: 'myPage',
          component: MyPage,
        },
        {
          path: 'withdraw',
          name: 'withdrawPage',
          component: WithdrawPage,
        },
        {
          path: 'profile-img',
          name: 'profileImgForm',
          component: ProfileImgForm,
        },
      ],
    },
    {
      path: '/club-manager',
      name: 'clubManagerView',
      component: ClubManagerView,
      children: [
        {
          path: '',
          name: 'createClubForm',
          component: CreateClubForm,
        },
      ],
    },
    {
      path: '/club',
      name: 'clubView',
      component: ClubView,
      children: [
        {
          path: 'search',
          name: 'searchClubForm',
          component: SearchClubForm,
        },
        {
          path: '',
          name: 'searchClubResult',
          component: SearchClubResult,
        },
        {
          path: ':clubid',
          name: 'clubDetail',
          component: ClubDetail,
        },
      ],
    },
  ]
})

export default router