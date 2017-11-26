<template xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label"> 
    <input v-on:keyup.enter="createComment" class="mdl-textfield__input" type="text"
           id="comment-text"> 
    <label class="mdl-textfield__label" for="comment-text">내용</label> 
  </div>
</template>

<script>
  import { mapGetters, mapActions } from 'vuex';

  export default {
    created() {
      this.setPostId({postId: this.id});
    },
    props: [
      'id',
    ],
    name: 'commentInput',
    methods: {
      ...mapActions([
        'createComment',
        'setPostId',
      ]),
    },
    computed: {
      ...mapGetters([
        'postId',
      ]),
    },
  };
</script>

<style scoped>

</style>
