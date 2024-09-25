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

html,
body {
  width: 375px;
  height: 812px;
  margin: 0;
  padding: 0;
  overflow: hidden;
}

#app {
  width: 100%;
  height: 100%;
}

:root {
  --gr10: #1f2226;
  --gr20: #292d33;
  --gr30: #383e47;
  --gr40: #616b79;
  --gr50: #8892a0;
  --gr60: #d6dae0;
  --gr70: #edeff2;
  --gr80: #f5f6f7;
  --gr90: #fbfbfc;
  --gr100: #ffffff;
  --p10: #3e8aff;
  --p20: #5398ff;
  --p30: #97c0fe;
  --p40: #99c1ff;
  --p50: #b2d1ff;
  --p60: #b2d1ff;
  --p70: #cce0ff;
  --p80: #ebf3ff;
  --p90: #f0f6ff;
}
</style>
