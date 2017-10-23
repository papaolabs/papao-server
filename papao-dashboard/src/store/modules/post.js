import * as types from '../mutation-types';
import PostService from '../../service/apispec'

export default {

  getters: {
    postList: state => state.postList,
  },
  state: {
    postList: [],
  },
  mutations: {
    [types.RECEIVE_POST_LIST] (state, {postList}) {
      state.postList = postList;
    },
    [types.INIT_POST_LIST] (state) {
      state.postList = [];
    },
  },
  actions: {
    initPostList({commit}) {
      commit({
        type: types.INIT_POST_LIST,
      });
    },
    readPosts({commit}, params) {
      PostService.readPosts(postList => {
        commit({
          type: types.RECEIVE_POST_LIST,
          postList: postList,
        }, params);
      });
    },
  },
};
