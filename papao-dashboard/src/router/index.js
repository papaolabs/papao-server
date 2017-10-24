import Vue from 'vue';
import Router from 'vue-router';
import post from '@/components/post';
import postDetail from '@/components/post-detail';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/posts',
      name: 'home',
      component: post,
    },
    {
      path: '/posts/:postId',
      component: postDetail,
    },
  ],
});
