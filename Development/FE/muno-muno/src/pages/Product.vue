<template>
  <div class="product-recommendation">
    <div class="status-bar"></div>
    <div class="header">
      <h1>상품추천</h1>
      <div class="search-icon">
        <img src="@/assets/search.svg" alt="Search" />
      </div>
    </div>
    <div class="banner">
      <div class="banner-content">
        <h2>웃는 문어들의 선택</h2>
        <p>쏠편한 입출금통장</p>
      </div>
    </div>
    <div class="categories">
      <button
        :class="{ active: selectedCategory === '예금' }"
        @click="selectCategory('예금')"
      >
        예금
      </button>
      <button
        :class="{ active: selectedCategory === '적금' }"
        @click="selectCategory('적금')"
      >
        적금
      </button>
      <button
        :class="{ active: selectedCategory === '펀드' }"
        @click="selectCategory('펀드')"
      >
        펀드
      </button>
      <button
        :class="{ active: selectedCategory === '주식' }"
        @click="selectCategory('주식')"
      >
        주식
      </button>
      <button
        :class="{ active: selectedCategory === '채권' }"
        @click="selectCategory('채권')"
      >
        채권
      </button>
    </div>
    <div class="product-list">
      <div v-for="product in products" :key="product.id" class="product-item">
        <div class="product-badge">{{ product.bank }}</div>
        <h2 class="product-title">{{ product.title }}</h2>
        <p class="product-description">{{ product.description }}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProductP',
  data() {
    return {
      selectedCategory: '예금',
      products: [],
      allProducts: {
        예금: [
          {
            id: 1,
            bank: '신한',
            title: '쏠편한 입출금통장',
            description: '#모바일이체도 #신한CD,ATM출금도 #수수료완전면제',
          },
          {
            id: 2,
            bank: 'KB국민',
            title: '국민 입출금 통장',
            description: '#국민 통장 #이자율',
          },
        ],
        적금: [
          {
            id: 3,
            bank: '신한',
            title: '신한 적금 상품',
            description: '#고금리 #안전한 투자',
          },
          {
            id: 4,
            bank: '우리',
            title: '우리 적금 상품',
            description: '#안정성 #고수익',
          },
        ],
        펀드: [
          {
            id: 5,
            bank: 'NH투자증권',
            title: 'NH 펀드',
            description: '#장기투자 #고수익',
          },
          {
            id: 6,
            bank: '미래에셋',
            title: '미래에셋 펀드',
            description: '#분산투자 #안정성',
          },
        ],
        주식: [
          {
            id: 7,
            bank: '삼성증권',
            title: '삼성전자',
            description: '#코스피 #대형주',
          },
          {
            id: 8,
            bank: '키움증권',
            title: '키움증권 주식',
            description: '#성장주 #안정성',
          },
        ],
        채권: [
          {
            id: 9,
            bank: '신한카드',
            title: '신한카드 채권',
            description: '#안정성 #고정수익',
          },
          {
            id: 10,
            bank: 'KB국민카드',
            title: 'KB 국민카드 채권',
            description: '#안정적 투자',
          },
        ],
      },
    };
  },
  methods: {
    selectCategory(category) {
      this.selectedCategory = category;
      this.updateProducts();
    },
    updateProducts() {
      // 선택된 카테고리에 맞는 상품 리스트를 업데이트
      this.products = this.allProducts[this.selectedCategory] || [];
    },
  },
  mounted() {
    // 페이지가 로드되었을 때 기본 상품을 업데이트
    this.updateProducts();
  },
};
</script>

<style scoped>
.status-bar {
  width: 375px;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 375px;
  height: 64px;
  flex-shrink: 0;
}

.header h1 {
  font-size: 24px;
  font-weight: bold;
}

.search-icon img {
  width: 24px;
  height: 24px;
}

.banner {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 335px;
  height: 114px;
  border-radius: 14px;
  background: #292d33;
  margin: 16px auto;
}

.banner-content h2 {
  color: #ffffff;
  font-size: 14px;
  margin-left: 15px;
  margin-bottom: 8px;
}

.banner-content p {
  color: #ffffff;
  font-size: 18px;
  font-weight: bold;

  margin-left: 15px;
}

.categories {
  color: var(--gray-20, #292d33);
  text-align: center;
  font-family: Pretendard;
  font-size: 16px;
  font-style: normal;
  font-weight: 600;
  line-height: 150%; /* 24px */
}

.categories button {
  padding: 8px 16px;
  border: none;
  background: none;
  font-size: 16px;
  cursor: pointer;
  color: #5f6368;
}

.categories .active {
  color: #1f1f1f;
  border-bottom: 2px solid #1f1f1f;
}

.product-list {
  margin-top: 16px;
}

.product-item {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 6px;
  padding: 18px;
  border-radius: 10px;
  border: 0.5px solid #d6dae0;
  background: #fff;
  margin-bottom: 12px;
  box-shadow: 0px 0px 6px rgba(0, 0, 0, 0.04);
}

.product-badge {
  display: inline-flex;
  padding: 4px 8px;
  border-radius: 9px;
  background: #ebf3ff;
  color: #1f1f1f;
}

.product-title {
  font-size: 18px;
  font-weight: bold;
  margin: 8px 0;
}

.product-description {
  font-size: 14px;
  color: #5f6368;
}
</style>
