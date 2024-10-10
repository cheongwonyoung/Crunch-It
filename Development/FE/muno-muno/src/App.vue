<template>
  <div id="app">
    <StatusBar />
    <div class="content">
      <router-view></router-view>
    </div>
    <TabBar v-if="showTabBar" />
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
  data() {
    return {
      hideRoutes: [
        '/login',
        '/signup',
        '/mypage',
        '/mypageedit',
        '/search',
        '/message',
        '/mydata',
        '/incomesetting',
        '/limitsetting',
      ],
    };
  },
  computed: {
    showTabBar() {
      // this.$route.path가 hideRoutes 배열에 포함된 경로로 시작하면 TabBar를 숨깁니다.
      return !this.hideRoutes.some((route) =>
        this.$route.path.startsWith(route)
      );
    },
  },
};
</script>

<style>
body,
html {
  font-family: 'Pretendard', sans-serif;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: auto;
  scrollbar-width: none;
  -ms-overflow-style: none;
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
  --gr60: #c4cad4;
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
  --p70: #ebf3ff;
  --p80: #f0f6ff;
}

.content {
  padding-top: 44px;
  height: calc(100% - 86px - 44px);
  overflow-y: auto;
}

.content::-webkit-scrollbar,
html::-webkit-scrollbar,
body::-webkit-scrollbar {
  width: 0;
  display: none;
}
</style>
