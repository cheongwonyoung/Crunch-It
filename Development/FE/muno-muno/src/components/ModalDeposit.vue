<template>
  <div class="modal-overlay" v-if="show" @click.self="close">
    <div class="modal-content">
      <div class="product-badge">{{ product?.korCoNm || 'N/A' }}</div>
      <div class="product-title">{{ product?.finPrdtNm || 'N/A' }}</div>
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
      <button @click="close">닫기</button>
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
    close() {
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
  margin-bottom: 8px;
}

.modal-content h2 {
  margin: 0 0 12px 0;
  color: var(--gr30);
  text-align: center;
  font-size: 22px;
  font-weight: 550;
  line-height: 100%;
}

.modal-content p {
  margin: 6px 0;
  color: var(--gr50);
  text-align: center;
  font-size: 14px;
  font-weight: 350;
  line-height: 100%;
}

.modal-buttons {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 26px;
}

.modal-buttons button {
  margin: 0;
  border-radius: 10px;
  border: none;
  cursor: pointer;
  width: 134px;
  height: 48px;
  flex-shrink: 0;
}

.modal-buttons button:first-child {
  font-size: 16px;
  background: var(--gr70);
  color: var(--gr50);
  font-weight: 500;
  line-height: 100%;
}

.modal-buttons button:last-child {
  font-size: 16px;
  background: var(--p10);
  color: var(--gr100);
  font-weight: 500;
  line-height: 100%;
}
</style>
