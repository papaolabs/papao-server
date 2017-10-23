import * as types from '../mutation-types';
import PostService from '../../service/apispec';

export default {

  getters: {
    index: state => state.index,
    size: state => state.size,
    postList: state => state.postList,
  },
  state: {
    index: 0,
    size: 10,
    postList: [],
  },
  mutations: {
    [types.INIT_POST_LIST] (state) {
      state.postList = [];
      state.index = 0;
      state.size = 10;
    },
    [types.SET_LIST_SIZE] (state, {size}) {
      state.size = size;
    },
    [types.RECEIVE_POST_LIST] (state, {postList}) {
      state.postList = [];
      state.postList = postList;
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
    readCurrentPosts({commit}) {
      PostService.readPosts(postList => {
        commit({
          type: types.RECEIVE_POST_LIST,
          postList: postList,
        })
      }, {
        index: this.state.post.index,
        size: this.state.post.size,
      })
    },
    readNextPosts({commit}) {
      PostService.readPosts(postList => {
        commit({
          type: types.RECEIVE_POST_LIST,
          postList: postList,
        })
      }, {
        index: ++this.state.post.index,
        size: this.state.post.size,
      })
    },
    readPrevPosts({commit}) {
      PostService.readPosts(postList => {
        commit({
          type: types.RECEIVE_POST_LIST,
          postList: postList,
        })
      }, {
        index: --this.state.post.index,
        size: this.state.post.size,
      })
    },
  },
};
