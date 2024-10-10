<template>
  <div v-if="show" class="modal-overlay" @click.self="closeModal">
    <div class="modal-content">
      <div class="product-badge">{{ product.bank }}</div>
      <h2>{{ product.title }}</h2>

      <!-- 예금 및 적금 정보 -->
      <div v-if="product.category === '예금' || product.category === '적금'">
        <p>가입 방법: {{ product.joinMethods }}</p>
        <p>금리 유형: {{ product.interestType }}</p>
        <p>6개월 저축 금리: {{ product.sixMonthRate }}</p>
        <p>12개월 저축 금리: {{ product.twelveMonthRate }}</p>
      </div>

      <!-- 펀드 정보 -->
      <div v-else-if="product.category === '펀드'">
        <p>펀드 상품: {{ product.fundProduct }}</p>
      </div>

      <!-- 채권 정보 -->
      <div v-else-if="product.category === '채권'">
        <p>등급: {{ product.bondRating }}</p>
        <p>표면 금리: {{ product.couponRate }}</p>
        <p>만기일: {{ product.maturityDate }}</p>
        <p>이자 지급일: {{ product.interestPaymentDate }}</p>
      </div>

      <!-- 주식 정보 -->
      <div v-else-if="product.category === '주식'">
        <p>시가 총액 비중: {{ product.marketCapWeight }}</p>
      </div>

      <div class="modal-buttons">
        <button @click="closeModal">닫기</button>
        <button>상품보러 가기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProductModal',
  props: {
    show: {
      type: Boolean,
      required: true,
    },
    product: {
      type: Object,
      required: true,
    },
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
