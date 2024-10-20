<template>
  <div v-if="show" class="modal-overlay" @click.self="closeModal">
    <div class="modal-content">
      <!-- 금융 상품 배지 -->
      <div class="product-badge">{{ product.issuerName }}</div>

      <!-- 금융 상품 제목 -->
      <div class="product-title">
        <span>{{ product.productName.slice(4) }}</span>
      </div>

      <!-- 금융 상품 정보 -->
      <p>등급: {{ product.creditRating || 'AAA' }}</p>
      <p>표면 금리: {{ product.surfaceRate }}%</p>
      <p>만기일: {{ product.maturityDate.split(' ')[0] }}</p>
      <p>이자 지급일: {{ product.interestPaymentCycle }}</p>

      <!-- 모달 버튼 -->
      <div class="modal-buttons">
        <button @click="closeModal">닫기</button>
        <button>상품 보러 가기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BondModal',
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
  background: rgba(0, 0, 0, 0.7); /* 수정 */
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
  margin-bottom: 8px;
}

.product-title {
  margin: 0 0 12px 0;
  color: var(--gr30);
  text-align: center;
  font-size: 22px;
  font-weight: 550;
  line-height: 100%;
}

.product-title span {
  display: inline-block;
  word-break: keep-all;
  overflow-wrap: break-word;
  line-height: 1.3;
  max-width: 100%;
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
  line-height: 100%;
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
