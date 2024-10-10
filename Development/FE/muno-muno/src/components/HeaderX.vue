<template>
  <div class="header">
    <div class="header-text">{{ title }}</div>
    <img class="close-icon" src="@/assets/x.svg" @click="onClose" />
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      required: true,
      default: '제목',
    },
  },
  methods: {
    onClose() {
      this.$emit('close');
    },
  },
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 22px;
  height: 65px;
  background-color: var(--gr100);
  position: fixed;
  top: 44px;
  left: 0;
  width: 100%;
  z-index: 1000;
  box-sizing: border-box;
  font-size: 22px;
}

.header-text {
  font-size: 22px;
  font-weight: 600;
}

.close-icon {
  width: 24px;
  height: 24px;
  cursor: pointer;
}
</style>
