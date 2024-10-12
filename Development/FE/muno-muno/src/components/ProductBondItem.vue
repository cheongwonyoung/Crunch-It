<template>
  <div class="product-item" @click="selectProduct">
    <div class="product-badge">{{ product?.issuerName || '발행자 없음' }}</div>
    <div class="product-title">
      {{ product?.productName?.slice(4) || '상품명 없음' }}
    </div>
  </div>
</template>

<script>
export default {
  name: 'BondItem',
  props: {
    product: {
      type: Object,
      default: () => ({}), // 빈 객체를 기본값으로 설정하여 undefined 방지
    },
  },
  emits: ['select'],
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
