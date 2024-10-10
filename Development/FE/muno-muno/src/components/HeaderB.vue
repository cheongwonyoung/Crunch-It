<template>
  <div class="header">
    <img class="back-icon" src="@/assets/arrowLeft.svg" @click="onBack" />
    <div class="header-text">{{ title }}</div>
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
    onBack() {
      this.$emit('back');
    },
  },
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding: 0 18px;
  height: 65px;

  position: fixed;
  top: 44px;
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

.back-icon {
  width: 24px;
  height: 24px;
  cursor: pointer;
  margin-right: 6px;
}
</style>
