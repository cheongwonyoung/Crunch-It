<template>
  <div class="income-setting-page">
    <HeaderB title="" @back="goBack" />
    <div class="content">
      <img src="@/assets/step2.svg" alt="Step 2" class="step-icon" />
      <h1>한 달 수익을<br />설정해 주세요</h1>
      <input
        type="number"
        v-model.number="income"
        placeholder="한 달 수익 입력"
        class="input-field"
      />
    </div>
    <ButtonA @click="handleSetIncome" class="connect-button"> 다음 </ButtonA>
  </div>
</template>

<script>
import ButtonA from '@/components/ButtonA.vue';
import HeaderB from '@/components/HeaderB.vue';

export default {
  components: {
    HeaderB,
    ButtonA,
  },
  data() {
    return {
      income: 500000, // 임의의 기본 값
    };
  },
  methods: {
    handleSetIncome() {
      if (this.income) {
        // 다음 페이지로 임의의 값 전달
        this.$router.push({
          path: '/limitsetting',
          query: { income: this.income },
        });
      } else {
        alert('수익을 입력해 주세요.');
      }
    },
    goBack() {
      this.$router.push('/mydata');
    },
  },
};
</script>

<style scoped>
.income-setting-page {
  top: 88px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 0 20px;
  text-align: left;
  position: relative;
}

.content {
  padding: 0;
  margin: 0;
}

.step-icon {
  display: block;
  margin-bottom: 20px;
  width: auto;
  height: auto;
}

h1 {
  color: var(--gr30);
  font-size: 26px;
  font-weight: 600;
  line-height: 150%;
  margin: 20px 0;
}

.input-field {
  width: 100%;
  max-width: 335px;
  height: 56px;
  border-radius: 12px;
  border: 0.5px solid var(--gr60);
  padding-left: 20px;
  font-size: 16px;
  font-weight: 500;
  margin-top: 20px;
}
</style>
