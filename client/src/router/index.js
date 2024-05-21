import { createRouter, createWebHistory } from 'vue-router';

import HomeView from '@/views/HomeView.vue';
import MyPageView from '@/views/MyPageView.vue';
import ClubManagerView from '@/views/ClubManagerView.vue';
import ClubPageView from '@/views/ClubPageView.vue';
import ClubView from '@/views/ClubView.vue';

import RegisterForm from '@/components/user/RegisterForm.vue';
import LoginForm from '@/components/user/LoginForm.vue';
import FindIdForm from '@/components/user/FindIdForm.vue';
import ResetPwForm from '@/components/user/ResetPwForm.vue';

import MyPage from '@/components/myinfo/MyPage.vue';
import WithdrawPage from '@/components/myinfo/WithdrawPage.vue';
import ProfileImgForm from '@/components/myinfo/ProfileImgForm.vue';

import CreateClubForm from '@/components/clubmanager/CreateClubForm.vue';
import ClubManageForm from '@/components/clubmanager/ClubManageForm.vue';
import ClubImgForm from '@/components/clubmanager/ClubImgForm.vue';

import SearchClubForm from '@/components/club/SearchClubForm.vue';
import ClubList from '@/components/club/ClubList.vue';
import ClubDetail from '@/components/club/ClubDetail.vue';
import MemberList from '@/components/club/MemberList.vue';

import BoardList from '@/components/board/BoardList.vue';
import BoardDetail from '@/components/board/BoardDetail.vue';
import CreateBoardForm from '@/components/board/CreateBoardForm.vue';
import ModifyBoardForm from '@/components/board/ModifyBoardForm.vue';


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
      name: 'clubPageView',
      component: ClubPageView,
      children: [
        {
          path: 'search',
          name: 'searchClubForm',
          component: SearchClubForm,
        },
        {
          path: '',
          name: 'clubList',
          component: ClubList,
        },
        {
          path: ':clubId',
          name: 'clubView',
          component: ClubView,
          children: [
            {
              path: '',
              name: 'clubDetail',
              component: ClubDetail,
            },
            {
              path: 'member-list',
              name: 'memberList',
              component: MemberList,
            },
            {
              path: 'manage',
              name: 'clubManageForm',
              component: ClubManageForm,
            },
            {
              path: 'profile-img',
              name: 'clubImgForm',
              component: ClubImgForm,
            },
            {
              path: 'board',
              name: 'boardList',
              component: BoardList,
            },
            {
              path: 'board/create',
              name: 'createBoardForm',
              component: CreateBoardForm,
            },
            {
              path: 'board/:boardId',
              name: 'boardDetail',
              component: BoardDetail,
            },
            {
              path: 'board/:boardId/modify',
              name: 'modifyBoardForm',
              component: ModifyBoardForm,
            },
          ],
        },
      ],
    },
  ]
})

export default router