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
    [types.CREATE_COMMENT]() {
      console.log('success');
    },
    [types.SET_POST_ID](state, {postId}) {
      state.postId = postId;
    },
  },
  actions: {
    createComment({commit}, {text}) {
      commentService.createComment(commit({
        type: types.CREATE_COMMENT,
      }), {
        postId: this.state.comment.postId,
        text: text,
      })
    },
    setPostId({commit}, {postId}) {
      console.log(postId);
      commit({
        type: types.SET_POST_ID,
        postId: postId,
      })
    },
  },
};
