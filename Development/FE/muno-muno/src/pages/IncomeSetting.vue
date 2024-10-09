<template>
  <div class="income-setting-page">
    <h2>한 달 수익 설정</h2>
    <input
      type="number"
      v-model.number="monthlyIncome"
      placeholder="한 달 수익을 입력하세요"
    />
    <button @click="handleSetIncome">설정</button>
  </div>
</template>

<script>
export default {
  name: 'IncomeSetting',
  data() {
    return {
      monthlyIncome: null,
    };
  },
  methods: {
    handleSetIncome() {
      if (this.monthlyIncome) {
        alert(`한 달 수익이 설정되었습니다: ${this.monthlyIncome}원`);
      } else {
        alert('수익을 입력해주세요.');
      }
    },
  },
};
</script>

<style scoped>
.income-setting-page {
  text-align: center;
  padding: 20px;
}

input {
  width: 300px;
  margin-bottom: 10px;
  padding: 8px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
</style>
