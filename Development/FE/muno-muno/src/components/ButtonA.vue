<template>
  <button class="buttonA" @click="handleClick">
    <slot></slot>
  </button>
</template>

<script>
export default {
  methods: {
    handleClick() {
      this.$emit('click');
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
  width: 335px;
  height: 56px;
  flex-shrink: 0;
  border: none;
  cursor: pointer;
}
</style>
