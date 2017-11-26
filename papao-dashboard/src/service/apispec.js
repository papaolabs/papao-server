import axios from 'axios';

const hostName = 'http://220.230.124.9:9010';

export default {
  readPosts(cb, {beginDate, endDate, index, size}) {
    axios.get(`${hostName}/api/v1/posts/pages`, {
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
  createComment(cb, {postId, text}) {
    let url = `${hostName}/api/v1/posts/${postId}/comments/guest?text=`+text;
    axios.post(url).then((response) => {
      cb(response.data);
    });
  },
  readComments(cb, {postId}) {
    axios.get(`${hostName}/api/v1/posts` + postId + '/comments', {}).then((response) => {
      cb(response.data);
    });
  },
};
