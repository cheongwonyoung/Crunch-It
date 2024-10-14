<template>
  <div class="modal-overlay" v-if="show" @click.self="close">
    <div class="modal-content">
      <div class="product-badge">{{ product?.korCoNm || 'N/A' }}</div>
      <div class="product-title">
        <span>{{ product?.finPrdtNm || 'N/A' }}</span>
      </div>
      <p>가입 방법: {{ product?.joinWay || 'N/A' }}</p>
      <p v-if="product?.sixMonthOption">
        금리 유형: {{ product.sixMonthOption.intrRateTypeNm || 'N/A' }}
      </p>
      <p v-if="product?.sixMonthOption">
        6개월 저축 금리: {{ product.sixMonthOption.intrRate || 'N/A' }}% (우대
        {{ product.sixMonthOption.intrRate2 || 'N/A' }}%)
      </p>
      <p v-if="product?.yearOption">
        12개월 저축 금리: {{ product.yearOption.intrRate || 'N/A' }}% (우대
        {{ product.yearOption.intrRate2 || 'N/A' }}%)
      </p>
      <div class="modal-buttons">
        <button @click="closeModal">닫기</button>
        <button>상품 보러 가기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'DepositModal',
  props: {
    show: Boolean,
    product: Object,
  },
  methods: {
    closeModal() {
      this.$emit('close');
    },
  },
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  padding: 28px 28px 24px 28px;
  width: 334px;
  height: auto;
  box-sizing: border-box;
  border-radius: 12px;
  max-width: 500px;
  background: var(--gr100);
  box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 0.1);
  text-align: center;
  font-family: 'Pretendard', sans-serif;
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
  margin-bottom: 12px;
}

.product-title {
  margin: 0 0 16px 0;
  color: var(--gr30);
  font-size: 22px;
  font-weight: 550;
}

.product-title span {
  display: inline-block;
  word-break: keep-all;
  overflow-wrap: break-word;
  line-height: 1.3;
  max-width: 100%;
}

.modal-content p {
  margin: 8px 0;
  color: var(--gr50);
  font-size: 14px;
  font-weight: 350;
  line-height: 1.4;
}

.modal-buttons {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 26px;
  font-family: 'Pretendard', sans-serif;
}

.modal-buttons button {
  margin: 0;
  border-radius: 10px;
  border: none;
  cursor: pointer;
  width: 134px;
  height: 48px;
  flex-shrink: 0;
  font-size: 16px;
  font-weight: 500;
  font-family: 'Pretendard', sans-serif;
}

.modal-buttons button:first-child {
  background: var(--gr70);
  color: var(--gr50);
}

.modal-buttons button:last-child {
  background: var(--p10);
  color: var(--gr100);
}
</style>
