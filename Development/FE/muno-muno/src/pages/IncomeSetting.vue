<template>
  <div class="income-setting-page">
    <HeaderB title="" @back="goBack" />
    <div class="content">
      <img src="@/assets/step2.svg" alt="Step 2" class="step-icon" />
      <h1>한 달 수익을<br />설정해 주세요</h1>
      <input
        type="text"
        v-model="formattedIncome"
        @input="updateIncome"
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
  computed: {
    formattedIncome: {
      get() {
        return this.formatNumber(this.income);
      },
      set(value) {
        this.income = this.parseNumber(value);
      },
    },
  },
  methods: {
    handleSetIncome() {
      if (this.income) {
        // 숫자 값이 제대로 전달되는지 확인하기 위해 콘솔에 로그 출력
        console.log('전달되는 income 값:', this.income);

        // 다음 페이지로 숫자 값 전달
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
    formatNumber(value) {
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    },
    parseNumber(value) {
      return parseInt(value.replace(/,/g, ''), 10) || 0;
    },
    updateIncome(event) {
      let value = event.target.value.replace(/[^\d]/g, '');
      this.income = value ? parseInt(value, 10) : 0;
    },
  },
};
</script>

<style scoped>
.income-setting-page {
  display: flex;
  flex-direction: column;
  padding: 0px 20px;
  background-color: var(--gr100);
  height: 100vh;
  position: fixed; /* 화면에 고정 */
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: hidden; /* 스크롤 막기 */
}

.content {
  display: flex;
  flex-direction: column; /* 요소들을 세로로 나열 */
  margin-top: 64px;
}

.step-icon {
  display: block;
  margin: 0 0 20px 0;
  width: 102px;
  height: 26px;
  margin-top: 20px;
}

h1 {
  color: var(--gr30);
  font-size: 26px;
  font-weight: 600;
  line-height: 150%;
  margin: 0 0 20px 0; /* 위쪽 여백 제거, 아래쪽 여백만 유지 */
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
  box-sizing: border-box;
}
</style>
