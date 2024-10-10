<template>
  <button class="buttonA" @click="handleClick">
    <slot></slot>
  </button>
</template>

<script>
export default {
  methods: {
    handleClick() {
      this.$emit('onClick');
    },
  },
};
</script>

<style scoped>
.buttonA {
  color: var(--gr100);
  text-align: center;
  font-size: 16px;
  font-weight: 500;
  line-height: 100%;
  border-radius: 12px;
  background: var(--p10);
  height: 56px;
  flex-shrink: 0;
  border: none;
  cursor: pointer;

  position: fixed;
  bottom: 34px;
  left: 50%;
  transform: translateX(-50%);
  width: calc(100% - 40px);
}

.buttonA:disabled {
  color: var(--gr100);
  text-align: center;
  font-size: 16px;
  font-weight: 500;
  line-height: 100%;
  border-radius: 12px;
  background: var(--p60);
  width: 335px;
  height: 56px;
  flex-shrink: 0;
  border: none;
  cursor: pointer;

  position: fixed;
  bottom: 34px;
  left: 50%;
  transform: translateX(-50%);
  width: calc(100% - 40px);
}
</style>
