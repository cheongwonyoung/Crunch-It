<template>
  <div class="onboarding-container">
    <HeaderX title="" @close="handleClose" />
    <div class="content">
      <img src="@/assets/step1.svg" alt="Step 1" class="step-icon" />
      <h1>
        muno에서<br /><span class="highlight">마이데이터</span>를 연동해보세요.
      </h1>
      <p>
        연동한 마이데이터로 나의 자산을<br />쉽고 빠르게 분석하여 관리할 수
        있어요.
      </p>
    </div>
    <ButtonA @click="handleButtonClick" class="connect-button">
      지금 연동할래요
    </ButtonA>
  </div>
</template>

<script>
import ButtonA from '@/components/ButtonA.vue';
import HeaderX from '@/components/HeaderX.vue';

export default {
  components: {
    ButtonA,
    HeaderX,
  },
  methods: {
    async handleButtonClick() {
      try {
        // 임의의 데이터를 사용하여 테스트
        const myData = {
          userId: 1,
          balance: 100000,
          assets: [
            { type: 'savings', amount: 50000 },
            { type: 'investments', amount: 50000 },
          ],
        };

        // 테스트용 데이터 출력
        console.log('Received MyData (Mock):', myData);
        // 페이지 이동
        this.$router.push('/incomesetting');
      } catch (error) {
        console.error('Error fetching MyData:', error);
        alert('마이데이터를 불러오는 데 실패했습니다. 다시 시도해 주세요.');
      }
    },
    handleClose() {
      this.$router.push('/login'); // SignUpPage로 이동
    },
  },
};
</script>

<style scoped>
.onboarding-container {
  top: 88px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 0 20px;
  text-align: left;
  position: relative;
}

.step-icon {
  display: block;
  margin: 0 0 20px 0;
  width: auto;
  height: auto;
}

.content {
  padding: 0;
  margin: 0;
}

h1 {
  color: var(--gr10);
  font-size: 26px;
  font-weight: 400;
  line-height: 39px;
  margin: 20px 0px;
}

.highlight {
  color: var(--gr10);
  font-weight: 600;
}

p {
  color: var(--gr50);
  font-size: 14px;
  font-weight: 400;
  line-height: 21px;
}
</style>
