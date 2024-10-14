<template>
  <div class="product-item" @click="selectProduct">
    <div class="product-badge">{{ product?.korCoNm || '금융사 없음' }}</div>
    <div class="product-title">{{ product?.finPrdtNm || '상품명 없음' }}</div>
  </div>
</template>

<script>
export default {
  name: 'SavingItem',
  props: {
    product: {
      type: Object,
      default: () => ({}), // 기본값 설정
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
.product-item {
  width: 300px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  background: var(--gr100);
  border-radius: 10px;
  border: 0.5px solid var(--gr60);
  margin: 6px 20px;
}

.product-badge {
  padding: 4px 8px;
  border-radius: 10px;
  background: var(--p70);
  color: var(--p10);
  font-size: 10px;
  font-weight: 600;
}

.product-title {
  color: var(--gr10);
  font-size: 18px;
  font-weight: 600;
  margin-top: 10px;
}
</style>
