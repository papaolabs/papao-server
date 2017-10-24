<template>
  <header class="mdl-layout__header mdl-layout__header--scroll mdl-color--primary">
    <div class="mdl-layout--large-screen-only mdl-layout__header-row">
    </div>
    <div class="mdl-layout--large-screen-only mdl-layout__header-row">
      <h3>Name &amp; Title</h3>
    </div>
    <div class="mdl-layout--large-screen-only mdl-layout__header-row">
    </div>
    <div class="mdl-layout__tab-bar mdl-js-ripple-effect mdl-color--primary-dark">
      <a href="#overview" class="mdl-layout__tab is-active">Menu1</a>
      <a href="#features" class="mdl-layout__tab">Menu2</a>
      <a href="#features" class="mdl-layout__tab">Menu3</a>
      <a href="#features" class="mdl-layout__tab">Menu4</a>
      <a href="#features" class="mdl-layout__tab">Menu5</a>
      <button
        class="mdl-button mdl-js-button mdl-button--fab mdl-js-ripple-effect mdl-button--colored mdl-shadow--4dp mdl-color--accent"
        id="add">
        <i class="material-icons" role="presentation">add</i>
        <span class="visuallyhidden">Add</span>
      </button>
    </div>
  </header>
</template>


<script>
  export default {
    name: 'header',
  };
</script>

<style scoped>

</style>
