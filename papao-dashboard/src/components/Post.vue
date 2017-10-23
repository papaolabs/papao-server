<template xmlns:v-on="http://www.w3.org/1999/xhtml" xmlns:v-bind="http://www.w3.org/1999/xhtml"
          xmlns:v-if="http://www.w3.org/1999/xhtml">
  <main class="mdl-layout__content">
    <div class="mdl-layout__tab-panel is-active" id="overview">
      <button v-on:click="initPostList">초기화</button>
      <button v-if="index > 0" v-on:click="readPrevPosts">이전</button>
      <button v-if="this.postList.length == this.size" v-on:click="readNextPosts">다음</button>
      <div class="mdl-grid portfolio-max-width">
        <div v-for="post in postList" class="mdl-cell mdl-card mdl-shadow--4dp portfolio-card">
          <div class="mdl-card__media">
            <div class="thumbnail">
              <div class="centered">
                <img class="article-image" v-bind:src="post.imageUrl" border="0" alt="">
              </div>
            </div>
          </div>
          <div class="mdl-card__title">
            <h2 class="mdl-card__title-text">Blog template</h2>
          </div>
          <div class="mdl-card__supporting-text">
            Enim labore aliqua consequat ut quis ad occaecat aliquip incididunt. Sunt nulla eu enim irure enim nostrud
            aliqua consectetur ad consectetur sunt ullamco officia. Ex officia laborum et consequat duis.
          </div>
          <div class="mdl-card__actions mdl-card--border">
            <a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect mdl-button--accent"
               href="portfolio-example01.html" data-upgraded=",MaterialButton,MaterialRipple">Read more<span
              class="mdl-button__ripple-container"><span class="mdl-ripple"></span></span></a>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
  import { mapGetters, mapActions } from 'vuex';

  const PAGE_SIZE = 100;

  export default {
    created() {
      this.setListSize({size: PAGE_SIZE});
      this.setDate({beginDate: '20171023', endDate: '20171023'});
      this.readCurrentPosts();
    },
    name: 'Post',
    methods: {
      ...mapActions([
        'initPostList',
        'setListSize',
        'setDate',
        'readCurrentPosts',
        'readNextPosts',
        'readPrevPosts',
      ]),
    },
    computed: {
      ...mapGetters([
        'index',
        'size',
        'postList',
        'beginDate',
        'endDate',
      ])
    },
  };
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .portfolio-max-width {
    max-width: 900px;
    margin: auto;
  }

  .thumbnail {
    position: relative;
    padding-top: 100%; /* 1:1 ratio */
    overflow: hidden;
  }

  .thumbnail .centered {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    -webkit-transform: translate(50%, 50%);
    -ms-transform: translate(50%, 50%);
    transform: translate(50%, 50%);
  }

  .thumbnail .centered img {
    position: absolute;
    top: 0;
    left: 0;
    max-width: 100%;
    height: auto;
    -webkit-transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
  }
</style>
