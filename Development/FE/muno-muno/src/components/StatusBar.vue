<template>
  <div class="status-bar">
    <img src="@/assets/statusBar.svg" alt="Status Bar" />
  </div>
</template>

<script>
export default {
  name: 'StatusBar',
};
</script>

<style scoped>
.status-bar {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 44px;
  background-color: white;
  z-index: 1000;
}
</style>
