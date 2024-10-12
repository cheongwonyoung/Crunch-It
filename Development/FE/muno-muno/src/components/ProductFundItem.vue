<template>
  <div class="product-item" @click="selectProduct">
    <div class="product-badge">국민은행</div>
    <!-- product.fndNm이 null인 경우를 대비한 처리 -->
    <div class="product-title">
      {{ product?.fndNm ? product.fndNm.split('(')[0] : '상품명 없음' }}
    </div>
  </div>
</template>

<script>
export default {
  name: 'FundItem',
  props: {
    product: {
      type: Object,
      required: true,
    },
  },
  emits: ['select'], // emits 옵션 명시적으로 추가
  methods: {
    selectProduct() {
      this.$emit('select', this.product); // select 이벤트 방출
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
