import Vuex from 'vuex';
import Vue from 'vue';

import postModule from './modules/post';
import commentModule from './modules/comment';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    post: postModule,
    comment: commentModule,
  },
});
