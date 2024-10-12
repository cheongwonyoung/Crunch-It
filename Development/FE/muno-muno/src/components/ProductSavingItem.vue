<template>
  <div class="product-item" @click="selectProduct">
    <div class="product-badge">{{ product.korCoNm }}</div>
    <div class="product-title">{{ product.finPrdtNm }}</div>
  </div>
</template>

<script>
export default {
  name: 'SavingItem',
  props: {
    product: {
      type: Object,
      required: true,
    },
  },
  methods: {
    selectProduct() {
      this.$emit('select', this.product);
    },
  },
};
</script>

<style scoped>
.product-container {
  display: flex;
  justify-content: center;
  width: 100%;
  margin: 0 auto;
}

.product-item {
  width: 300px;
  padding: 20px 18px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  background: var(--gr100);
  border-radius: 10px;
  border: 0.5px solid var(--gr60);
  box-shadow: 0px 0px 6px 0px rgba(0, 0, 0, 0.04);
  margin: 6px 20px;
}

.product-badge {
  padding: 4px 8px;
  border-radius: 10px;
  color: var(--p10);
  font-size: 10px;
  font-weight: 600;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  background: var(--p70);
}

.product-title {
  color: var(--gr10);
  font-size: 18px;
  font-weight: 600;
  margin: 10px 0 0 2px;
}
</style>
