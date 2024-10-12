<template>
  <div class="modal-overlay" v-if="show" @click.self="close">
    <div class="modal-content">
      <div class="product-badge">
        {{ product?.issuerName || '발행자 정보 없음' }}
      </div>
      <div class="product-title">
        {{
          product?.productName ? product.productName.slice(4) : '상품 이름 없음'
        }}
      </div>
      <p>등급: {{ product?.creditRating || 'AAA' }}</p>
      <p>표면 금리: {{ product?.surfaceRate || '정보 없음' }}%</p>
      <p>
        만기일:
        {{
          product?.maturityDate
            ? product.maturityDate.split(' ')[0]
            : '만기일 없음'
        }}
      </p>
      <p>이자 지급일: {{ product?.interestPaymentCycle || '지급일 없음' }}</p>
      <button @click="close">닫기</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BondModal',
  props: {
    show: Boolean,
    product: {
      type: Object,
      default: () => ({}), // product가 없을 경우 빈 객체로 기본값 설정
    },
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
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 10px;
  width: 300px;
}

.product-badge {
  background-color: #007aff;
  color: white;
  padding: 5px;
  border-radius: 5px;
}

.product-title {
  margin-top: 10px;
  font-weight: bold;
}
</style>
