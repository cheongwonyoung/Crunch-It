<template>
  <div v-if="show" class="modal-overlay" @click.self="closeModal">
    <div class="modal-content">
      <div v-if="product">
        <!-- 주식일 때 -->
        <div v-if="product.stockCode" class="product-badge">
          {{ product.stockMarket }}
        </div>
        <div v-if="product.stockCode">
          <h2>{{ product.stockName }}</h2>
          <p>주식코드: {{ product.stockCode }}</p>
          <p>평균단가: {{ product.averagePurchasePrice }}원</p>
        </div>

        <!-- 채권일 때 -->
        <div v-else-if="product.bondCode" class="product-badge">
          {{ product.bondName.substring(0, 4) }}
        </div>
        <div v-if="product.bondCode">
          <h2>
            {{ product.bondName.substring(4, product.bondName.length - 6) }}
          </h2>
          <p>등급: AAA</p>
          <p>유형: {{ product.bondName.slice(-3) }}</p>
        </div>

        <!-- 펀드일 때 -->
        <div v-else-if="product.fundCode" class="product-badge">
          {{ product.fundType }}
        </div>
        <div v-if="product.fundCode">
          <h2>{{ product.fundName }}</h2>
          <p>펀드코드: {{ product.fundCode }}</p>
        </div>
      </div>

      <div class="modal-buttons">
        <button @click="closeModal">닫기</button>
        <button>상품 보러 가기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ModalBanner',
  props: {
    show: {
      type: Boolean,
      required: true,
    },
    product: {
      type: Object, // 하나의 product 객체를 받음
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
