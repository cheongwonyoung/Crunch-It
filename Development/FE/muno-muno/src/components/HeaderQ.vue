<template>
  <div class="header">
    <div class="header-text">{{ title }}</div>
    <img class="search" src="@/assets/search.svg" @click="goToSearch" />
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      required: true, // default 값 제거
    },
  },
  methods: {
    goToSearch() {
      this.$router.push('/search');
    },
  },
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 25px 0 20px;
  height: 65px;

  position: fixed;
  /* top: 44px; */
  left: 0;
  width: 100%;
  z-index: 1000;
  box-sizing: border-box;
  font-size: 22px;
  background-color: var(--gr100);
}

.header-text {
  color: var(--gr10);
  font-size: 22px;
  font-weight: 600;
  line-height: 100%;
}

.search {
  width: 24px;
  height: 24px;
  cursor: pointer;
}
</style>
