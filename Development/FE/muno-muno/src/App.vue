<template>
  <div id="app">
    <StatusBar />
    <div class="content">
      <router-view></router-view>
    </div>
    <TabBar />
  </div>
</template>

<script>
import TabBar from './components/TabBar.vue';
import StatusBar from './components/StatusBar.vue';

export default {
  components: {
    TabBar,
    StatusBar,
  },
};
</script>

<style>
body {
  font-family: 'Pretendard', sans-serif;
}

html,
body {
  width: 100%;
  height: 100%;
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
  --gr70: #ebf3ff;
  --gr80: #f5f6f7;
  --gr90: #fbfbfc;
  --gr100: #ffffff;
  --p10: #3e8aff;
  --p20: #5398ff;
  --p30: #97c0fe;
  --p40: #99c1ff;
  --p50: #b2d1ff;
  --p60: #b2d1ff;
  --p70: #ebf3ff;
  --p80: #f0f6ff;
  --gd10: linear-gradient(
    90deg,
    hsla(216, 76%, 100%, 1),
    hsla(216, 60%, 100%, 1)
  );
}

/* content 부분 스크롤 발생 설정 */
.content {
  padding-top: 44px;
  height: calc(100% - 86px - 44px);
  overflow-y: auto;
}

/* 스크롤바 숨김 처리 */
.content::-webkit-scrollbar {
  width: 0;
  display: none;
}

html,
body {
  scrollbar-width: none;
  -ms-overflow-style: none;
}

html::-webkit-scrollbar,
body::-webkit-scrollbar {
  display: none;
}
</style>
