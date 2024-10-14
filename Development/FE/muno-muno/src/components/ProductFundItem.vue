<template>
  <div class="product-item" @click="selectProduct">
    <div class="product-header">
      <div class="product-badge">국민은행</div>
    </div>
    <div class="product-title">
      <span>{{
        product?.fndNm ? product.fndNm.split('(')[0] : '상품명 없음'
      }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FundItem',
  props: {
    product: {
      type: Object,
      default: () => ({}),
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

.product-header {
  width: 100%;
  display: flex;
  justify-content: flex-start;
  margin-bottom: 10px;
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
  width: 100%;
  text-align: left;
}

.product-title span {
  display: inline-block;
  word-break: keep-all;
  overflow-wrap: break-word;
  max-width: 100%;
  line-height: 1.3;
}
</style>
