<template>
  <section class="expense-section">
    <div class="expense-container">
      <div class="expense-box">
        <!-- 지출금액 -->
        <p class="subtitle">지출금액</p>
        <!-- 금액 -->
        <h1 class="amount">{{ formattedExpenseAmount }}원</h1>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  props: {
    expenseAmount: {
      type: Number,
      required: true,
    },
  },
  computed: {
    formattedExpenseAmount() {
      return this.expenseAmount.toLocaleString(); // 숫자를 100,000 형식으로 변환
    },
  },
};
</script>

<style scoped>
.expense-section {
  background-color: var(--p10);
  position: fixed;
  bottom: 86px;
  left: 0;
  width: 100%;
  height: 112px;
  border-radius: 20px 20px 0 0;
  display: flex;
  justify-content: center;
}

.expense-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.expense-box {
  width: calc(100% - 40px);
  max-width: 335px;
  height: 64px;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.2);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  box-sizing: border-box;
  margin: 24px 0;
  border: 0.8px solid rgba(255, 255, 255, 0.1);
}

.subtitle {
  color: var(--gr100);
  font-size: 16px;
  font-weight: 400;
}

.amount {
  color: var(--gr100);
  font-size: 24px;
  font-weight: 400;
  text-align: right;
}
</style>
