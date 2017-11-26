<template xmlns:v-on="http://www.w3.org/1999/xhtml" xmlns:v-bind="http://www.w3.org/1999/xhtml"
          xmlns:v-if="http://www.w3.org/1999/xhtml">
  <div class="mdl-layout__tab-panel is-active" id="overview">
    <div>
      {{this.beginDate}} ~ {{this.endDate}}
    </div>
    <button v-on:click="initPostList">초기화</button>
    <button v-if="index > 0" v-on:click="readPrevPosts">이전</button>
    <button v-if="this.postList.length == this.size" v-on:click="readNextPosts">다음</button>
    <div v-else>검색 결과 끝</div>
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
          <h2 class="mdl-card__title-text">{{post.kindName}}</h2>
          <div class="mdl-card__state">- {{post.state}}</div>
        </div>
        <div class="mdl-card__supporting-text">
            <span v-if="post.gender == 'M'" class="mdl-chip">
            <span class="mdl-chip__text">수컷</span>
          </span>
          <span v-if="post.gender == 'F'" class="mdl-chip">
            <span class="mdl-chip__text">암컷</span>
          </span>
          <span v-if="post.type == '01'" class="mdl-chip">
            <span class="mdl-chip__text">기관</span>
          </span>
          <span v-if="post.type == '02'" class="mdl-chip">
            <span class="mdl-chip__text">실종</span>
          </span>
          <span v-if="post.type == '03'" class="mdl-chip">
            <span class="mdl-chip__text">개인</span>
          </span>
          <span class="mdl-chip">
            <span class="mdl-chip__text">{{post.weight}}kg</span>
          </span>
          <span v-if="post.neuter == 'Y'" class="mdl-chip">
            <span class="mdl-chip__text">중성화</span>
          </span>
          <span v-if="post.neuter == 'N'" class="mdl-chip">
            <span class="mdl-chip__text">중성화X</span>
          </span>
        </div>
        <div class="mdl-card__supporting-text">
          {{dateFormat(post.happenDate)}}
          {{post.happenPlace}}에서 길을 잃었어요
        </div>
        <div class="mdl-card__actions mdl-card--border">
          <router-link :to="{ path: `posts/${post.id}` }"
                       class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect">
            자세히 보기
          </router-link>
        </div>
      </div>
    </div>
    <button id="view-source"
            class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored mdl-shadow--4dp mdl-color--accent">
      <i class="material-icons" role="presentation">search</i>
      <span class="visuallyhidden">Search</span>
      <span class="mdl-button__ripple-container"><span class="mdl-ripple is-animating"
                                                       style="width: 160.392px; height: 160.392px; transform: translate(-50%, -50%) translate(29px, 41px);"></span></span>
    </button>
  </div>
</template>

<script>
  import { mapGetters, mapActions } from 'vuex';

  const PAGE_SIZE = 12;

  export default {
    created() {
      this.setListSize({size: PAGE_SIZE});
      this.setDate({beginDate: '20171024', endDate: '20171024'});
      this.readCurrentPosts();
    },
    name: 'post',
    methods: {
      ...mapActions([
        'initPostList',
        'setListSize',
        'setDate',
        'readCurrentPosts',
        'readNextPosts',
        'readPrevPosts',
      ]),
      dateFormat(source) {
        let year = source.substring(0, 4);
        let month = source.substring(4, 6);
        let day = source.substring(6, 8);

        return year + '년 ' + month + '월 ' + day + '일';
      },
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
  .mdl-card__media {
    background-color: #f5f5f5;
  }

  .mdl-card__supporting-text {
    margin: -20px 0px 0px 0px !important;
    padding: 16px !important;
    width: 90% !important;
  }

  .mdl-card__state {
    margin-left: 5px;
    color: #a5a5a5;
  }

  .mdl-card__actions {
    padding: 4px 0px;
  }

  .mdl-card__title-text {
    font-weight: normal;
    font-size: 20px;
    color: #757575;
  }

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
