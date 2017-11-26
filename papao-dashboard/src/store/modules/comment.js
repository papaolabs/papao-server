import * as types from '../mutation-types';
import commentService from '../../service/apispec'

export default {

  getters: {
    postId: state => state.postId,
    commentList: state => state.commentList,
  },
  state: {
    postId: -1,
    postList: [],
  },
  mutations: {
    [types.CREATE_COMMENT](state, {comment}) {
      console.log(comment);
    },
    [types.SET_POST_ID](state, {postId}) {
      state.postId = postId;
    },
  },
  actions: {
    createComment({commit}, {text}) {
      commentService.createComment(comment => {
        commit({
          type: types.CREATE_COMMENT,
          comment: comment,
        })
      }, {
        postId: this.state.comment.postId,
        text: 'abcd',
      })
    },
    setPostId({commit}, {postId}) {
      commit({
        type: types.SET_POST_ID,
        postId: postId,
      })
    },
  },
};
