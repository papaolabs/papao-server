<template>

</template>

<script>
  export default {
    name: 'CommentForm',
    created() {
    },
    methods: {
      ...mapActions([
        'createComment'
      ]),
    },
    computed: {
/*      ...mapGetters([
        'commentList'
      ])*/
    },
  };
</script>

<style scoped>

</style>
