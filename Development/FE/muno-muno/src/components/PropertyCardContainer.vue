<template>
  <div class="property-cards-container">
    <!-- 첫 번째 행: 자산과 소비 카드 -->
    <div class="cards">
      <PropertyCard
        class="titles"
        title="자산"
        :amount="asset"
        :previousAmount="previousAsset"
        type="asset"
      />
      <PropertyCard
        class="titles"
        title="소비"
        :amount="expense"
        :previousAmount="previousExpense"
        type="expense"
      />
    </div>
    <!-- 두 번째 행: 투자와 AI 추천 상품 카드 -->
    <div class="cards">
      <PropertyCard
        class="titles"
        title="투자"
        :amount="investment"
        :previousAmount="previousInvestment"
        type="investment"
      />
      <PropertyCard
        class="titles"
        title="AI 추천 상품"
        type="ai"
        @click="goToProductPage"
      />
    </div>
  </div>
</template>

<script>
import PropertyCard from './PropertyCard.vue';

export default {
  name: 'PropertyCardContainer',
  components: {
    PropertyCard,
  },
  // 컴포넌트의 데이터 정의
  data() {
    return {
      asset: 0,
      previousAsset: 0,
      expense: 0,
      previousExpense: 0,
      investment: 0,
      previousInvestment: 0,
    };
  },
  methods: {
    // AI 추천 상품 페이지로 이동하는 메서드
    goToProductPage() {
      this.$router.push('/product');
    },
    // 데이터를 가져오는 메서드
    async fetchData() {
      try {
        // 테스트 데이터
        this.asset = 1300700;
        this.previousAsset = 1200000;
        this.expense = 500000;
        this.previousExpense = 550000;
        this.investment = 200000;
        this.previousInvestment = 150000;
        // 실제 API 호출로 대체해야 합니다
        // const response = await fetch('/api/financial-data');
        // const data = await response.json();

        // this.asset = data.asset;
        // this.previousAsset = data.previousAsset;
        // this.expense = data.expense;
        // this.previousExpense = data.previousExpense;
        // this.investment = data.investment;
        // this.previousInvestment = data.previousInvestment;
      } catch (error) {
        console.error('데이터를 가져오는데 실패했습니다:', error);
        // 에러 처리 로직 추가
      }
    },
  },
  // 컴포넌트가 마운트될 때 데이터를 가져옴
  mounted() {
    this.fetchData();
  },
};
</script>

<style scoped>
/* 카드 컨테이너의 전체 스타일 */
.property-cards-container {
  margin: 20px;
  display: flex;
  flex-direction: column;
  gap: 13px;
}

/* 각 카드 행의 스타일 */
.cards {
  display: flex;
  justify-content: space-between;
}

/* 카드 제목의 스타일 */
.titles {
  color: var(--gr20);
  font-family: Pretendard;
  font-size: 16px;
  font-style: normal;
  font-weight: 600;
  line-height: 135%;
}
</style>
