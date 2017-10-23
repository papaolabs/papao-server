import * as types from '../mutation-types';
import PostService from '../../service/apispec'

export default {

  getters: {
    index: state => state.index,
    size: state => state.size,
    postList: state => state.postList,
  },
  state: {
    index: 0,
    size: 20,
    postList: [],
  },
  mutations: {
    [types.INIT_POST_LIST] (state) {
      state.postList = [];
    },
    [types.SET_LIST_SIZE] (state, {size}) {
      state.size = size;
    },
    [types.NEXT_POST_LIST] (state, {postList}) {
      state.postList = postList;
      state.index++;
    },
    [types.PREV_POST_LIST] (state, {postList}) {
      state.postList = postList;
      if (state >= 0) {
        state.index--;
      }
    },
  },
  actions: {
    initPostList({commit}) {
      commit({
        type: types.INIT_POST_LIST,
      });
    },
    setListSize({commit}, {size}) {
      commit({
        type: types.SET_LIST_SIZE,
        size: size,
      })
    },
    readPosts({commit}, params) {
      PostService.readPosts(postList => {
        commit({
          type: types.NEXT_POST_LIST,
          postList: postList,
        })
      }, {
        index: this.state.post.index,
        size: this.state.post.size,
      })
    },
  },
};
