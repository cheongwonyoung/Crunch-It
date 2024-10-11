<!-- PropertyCardContainer.vue -->
<template>
  <div class="property-cards-container">
    <div class="cards">
      <PropertyCard
        class="titles"
        title="자산"
        :amount="cardData.asset.current"
        :previousAmount="cardData.asset.previous"
        type="asset"
      />
      <PropertyCard
        class="titles"
        title="소비"
        :amount="cardData.expense.current"
        :previousAmount="cardData.expense.previous"
        type="expense"
      />
    </div>
    <div class="cards">
      <PropertyCard
        class="titles"
        title="투자"
        :amount="cardData.investment.current"
        :previousAmount="cardData.investment.previous"
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
  props: {
    cardData: {
      type: Object,
      required: true,
    },
  },
  methods: {
    goToProductPage() {
      this.$router.push('/product');
    },
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
