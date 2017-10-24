import * as types from '../mutation-types';
import commetService from '../../service/apispec'

export default {

  getters: {
    commentList: state => state.commentList,
  },
  state: {
    postList: [],
  },
  mutations: {
    [types.RECEIVE_COMMENT_LIST] (state, {commentList}) {
      state.commentList = commentList;
    },
  },
  actions: {
    createComment({commit}, params) {
      commetService.createComment(commentList => {
        commit({
          type: types.RECEIVE_COMMENT_LIST,
          commentList: commentList,
        }, params);
      });
    },
  },
};
