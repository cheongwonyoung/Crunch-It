<template>
  <div id="app">
    <router-view></router-view>
    <TabBar />
  </div>
</template>

<script>
import TabBar from './components/TabBar.vue';

export default {
  components: {
    TabBar,
  },
};
</script>

<style>
body {
  font-family: 'Pretendard', sans-serif;
}
</style>
