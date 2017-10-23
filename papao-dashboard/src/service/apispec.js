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
  createComment(cb, params) {
    axios.post(`${hostName}/api/v1/posts` + postId + '/comments', {
      userId: params.userId,
      userName: params.userName,
      text: params.text,
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
