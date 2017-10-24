<template>
  <div>
    {{this.selectedPost}}
    <div>
      <commentInput v-bind:id="this.$route.params.postId"></commentInput>
    </div>
  </div>
</template>

<script>
  import { mapGetters, mapActions } from 'vuex';
  import commentInput from './comment-input';

  export default {
    created() {
      this.readPost({postId: this.$route.params.postId});
    },
    components: {
      commentInput: commentInput,
    },
    name: 'post-detail',
    methods: {
      ...mapActions([
        'readPost',
      ]),
    },
    computed: {
      ...mapGetters([
        'selectedPost',
      ]),
    },
  };
</script>

<style scoped>

</style>
