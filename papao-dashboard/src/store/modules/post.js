import * as types from '../mutation-types';
import postService from '../../service/apispec';

export default {

  getters: {
    index: state => state.index,
    size: state => state.size,
    beginDate: state => state.beginDate,
    endDate: state => state.endDate,
    postList: state => state.postList,
    selectedPost: state => state.selectedPost,
  },
  state: {
    index: 0,
    size: 10,
    beginDate: '',
    endDate: '',
    postList: [],
    selectedPost: {id: -1},
  },
  mutations: {
    [types.INIT_POST_LIST](state) {
      state.postList = [];
      state.index = 0;
      state.size = 10;
      state.beginDate = '';
      state.endDate = '';
    },
    [types.SET_LIST_SIZE](state, {size}) {
      state.size = size;
    },
    [types.SET_DATE](state, {beginDate, endDate}) {
      state.beginDate = beginDate;
      state.endDate = endDate;
    },
    [types.RECEIVE_POST_LIST](state, {postList}) {
      state.postList = postList;
    },
    [types.RECEIVE_POST](state, {post}) {
      state.selectedPost = post;
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
    setDate({commit}, {beginDate, endDate}) {
      commit({
        type: types.SET_DATE,
        beginDate: beginDate,
        endDate: endDate,
      })
    },
    readPost({commit}, {postId}) {
      postService.readPost(post => {
        commit({
          type: types.RECEIVE_POST,
          post: post,
        })
      }, {
        postId: postId,
      })
    },
    readCurrentPosts({commit}) {
      postService.readPosts(postList => {
        commit({
          type: types.RECEIVE_POST_LIST,
          postList: postList,
        })
      }, {
        beginDate: this.state.post.beginDate,
        endDate: this.state.post.endDate,
        index: this.state.post.index,
        size: this.state.post.size,
      })
    },
    readNextPosts({commit}) {
      postService.readPosts(postList => {
        commit({
          type: types.RECEIVE_POST_LIST,
          postList: postList,
        })
      }, {
        beginDate: this.state.post.beginDate,
        endDate: this.state.post.endDate,
        index: ++this.state.post.index,
        size: this.state.post.size,
      })
    },
    readPrevPosts({commit}) {
      postService.readPosts(postList => {
        commit({
          type: types.RECEIVE_POST_LIST,
          postList: postList,
        })
      }, {
        beginDate: this.state.post.beginDate,
        endDate: this.state.post.endDate,
        index: --this.state.post.index,
        size: this.state.post.size,
      })
    },
  },
};
