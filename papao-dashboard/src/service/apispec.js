import axios from 'axios';

const hostName = 'http://localhost:8080';

export default {
  readPosts(cb, {beginDate, endDate, index, size}) {
    axios.get(`${hostName}/api/v1/posts`, {
      params: {
        beginDate: beginDate,
        endDate: endDate,
        index: index,
        size: size,
      }
    }).then((response) => {
      cb(response.data);
    });
  },
  readPost(cb, {postId}) {
    axios.get(`${hostName}/api/v1/posts/${postId}`).then((response) => {
      cb(response.data);
    });
  },
  createComment(cb, {text, postId}) {
    axios.post(`${hostName}/api/v1/posts/comments/${postId}`, {
      text: text,
    }).then((response) => {
      cb(response.data);
    });
  },
  readComments(cb, {postId}) {
    axios.get(`${hostName}/api/v1/posts` + postId + '/comments', {}).then((response) => {
      cb(response.data);
    });
  },
};
