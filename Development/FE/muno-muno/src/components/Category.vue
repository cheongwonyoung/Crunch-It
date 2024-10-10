<template>
  <button
    :class="{ active: isActive }"
    @click="$emit('category-selected', category)"
  >
    {{ category }}

    <span v-if="isActive" class="underline"></span>
  </button>
</template>

<script>
export default {
  name: 'CategoryP',
  props: {
    category: {
      type: String,
      required: true,
    },
    isActive: {
      type: Boolean,
      default: false,
    },
  },
};
</script>

<style scoped>
button {
  padding: 10px 14px;
  border: none;
  background: none;
  font-size: 16px;
  cursor: pointer;
  color: var(--gr50);
  font-style: normal;
  font-weight: 500;
  line-height: 100%;
  position: relative;
  margin-right: 10px;
}

button:last-child {
  margin-right: 0;
}

button.active {
  color: var(--gr20);
  text-align: center;
  font-size: 16px;
  font-weight: 600;
  line-height: 100%;
}

.underline {
  content: '';
  display: block;
  height: 2px;
  background-color: var(--gr20);
  position: absolute;
  bottom: 0px;
  left: 0;
  right: 0;
  margin: 0 auto;
  width: 100%;
}
</style>
