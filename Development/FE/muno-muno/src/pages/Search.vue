<template>
  <div class="search">
    <HeaderB title="" @back="goBack" />

    <div class="search-content">
      <h1>어떤 상품을 찾으시나요?</h1>

      <!-- 검색창 -->
      <div class="search-box">
        <input
          type="text"
          placeholder="검색어를 입력해주세요."
          v-model="searchQuery"
          @keyup.enter="search"
          class="input-text"
        />
        <img
          class="icon-search"
          src="@/assets/search.svg"
          alt="Search Icon"
          @click="search"
        />
      </div>

      <!-- 경고 메시지 (검색창 바로 아래로 이동) -->
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

      <!-- 카테고리 제목 추가 -->
      <h2 class="category-title">카테고리</h2>

      <!-- 카테고리 태그 버튼 -->
      <div class="category-tags">
        <span
          v-for="category in categories"
          :key="category"
          :class="{ selected: selectedCategory === category }"
          @click="selectCategory(category)"
          class="tag-btn"
        >
          {{ '#' + category }}
        </span>
      </div>

      <!-- 로딩 스피너 -->
      <div v-if="loading" class="loading-spinner">
        <div class="spinner"></div>
      </div>

      <!-- 필터링된 상품 리스트 -->
      <div
        v-if="!loading && searched && filteredProducts.length > 0"
        class="product-list"
      >
        <component
          v-for="product in filteredProducts"
          :key="product.finPrdtNm || product.fndNm || product.productName"
          :is="getProductItemComponent()"
          :product="product"
        />
      </div>

      <div
        v-else-if="!loading && searched && filteredProducts.length === 0"
        class="no-result"
      >
        <h3>검색 결과가 없습니다.</h3>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderB from '@/components/HeaderB.vue';
import ProductDepositItem from '@/components/ProductDepositItem.vue';
import ProductSavingItem from '@/components/ProductSavingItem.vue';
import ProductFundItem from '@/components/ProductFundItem.vue';
import ProductBondItem from '@/components/ProductBondItem.vue';
import apiClient from '@/axios';

export default {
  name: 'SearchP',
  components: {
    HeaderB,
    ProductDepositItem,
    ProductSavingItem,
    ProductFundItem,
    ProductBondItem,
  },
  data() {
    return {
      searchQuery: '', // 검색어
      selectedCategory: null, // 선택된 카테고리
      categories: ['예금', '적금', '펀드', '채권'], // 카테고리 목록
      products: [], // 모든 카테고리의 상품 데이터
      filteredProducts: [], // 필터링된 상품 데이터
      searched: false, // 검색 여부 확인
      errorMessage: '', // 에러 메시지 상태
      loading: false, // 로딩 상태
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    async selectCategory(category) {
      this.loading = true; // 로딩 시작
      if (this.selectedCategory === category) {
        this.selectedCategory = null;
        this.filteredProducts = [];
        this.loading = false; // 로딩 종료
        return;
      }

      this.selectedCategory = category;
      await this.fetchProductsByCategory(category);
      this.loading = false; // 로딩 종료
      this.search();
    },

    async fetchProductsByCategory(category) {
      try {
        let response;
        switch (category) {
          case '예금':
            response = await apiClient.get('/recommend/deposit');
            this.products = response.data.depositList || [];
            break;
          case '적금':
            response = await apiClient.get('/recommend/saving');
            this.products = response.data.savingList || [];
            break;
          case '펀드':
            response = await apiClient.get('/recommend/fund');
            this.products = response.data || [];
            break;
          case '채권':
            response = await apiClient.get('/recommend/bond');
            this.products = response.data || [];
            break;
        }
        this.filteredProducts = [...this.products];
      } catch (error) {
        this.errorMessage = '상품 데이터를 가져오는 중 오류가 발생했습니다.';
      }
    },
    search() {
      this.errorMessage = ''; // 검색 시작 시 에러 메시지 초기화
      if (this.searchQuery.trim().length < 2) {
        this.errorMessage = '두 글자 이상 입력해 주세요.';
        return;
      }
      if (!this.selectedCategory) {
        this.errorMessage = '카테고리를 선택해주세요.';
        return;
      }
      this.searched = true;

      const searchTermLower = this.searchQuery
        .toLowerCase()
        .replace(/\s+/g, '');

      this.filteredProducts = this.products.filter((product) => {
        let searchFields = [];
        switch (this.selectedCategory) {
          case '예금':
          case '적금':
            searchFields = [product.finPrdtNm];
            break;
          case '펀드':
            searchFields = [product.fndNm, product.fndTp];
            break;
          case '채권':
            searchFields = [product.productName];
            break;
        }

        return searchFields.some(
          (field) =>
            field &&
            field.toLowerCase().replace(/\s+/g, '').includes(searchTermLower)
        );
      });

      if (this.filteredProducts.length === 0) {
        this.errorMessage = '';
      }
    },
    getProductItemComponent() {
      switch (this.selectedCategory) {
        case '예금':
          return 'ProductDepositItem';
        case '적금':
          return 'ProductSavingItem';
        case '펀드':
          return 'ProductFundItem';
        case '채권':
          return 'ProductBondItem';
        default:
          return 'ProductDepositItem';
      }
    },
  },
};
</script>

<style scoped>
/* 폰트를 Pretendard로 변경 */
body,
input,
button,
h1,
h2,
h3,
p {
  font-family: 'Pretendard', sans-serif;
}

.search {
  padding: 0 20px;
}

.search-content {
  margin-top: 12px;
}

h1 {
  margin-top: 79px;
  margin-bottom: 43px;
  color: var(--gr10);
  font-size: 24px;
  font-weight: 500;
  line-height: 100%;
}

.search-box {
  display: flex;
  align-items: center;
  margin-bottom: 24px;
  padding-bottom: 10px;
  border-bottom: 2px solid var(--gr30);
  position: relative;
}

.input-text {
  border: none;
  outline: none;
  flex-grow: 1;
  font-size: 16px;
  padding: 0;
  margin: 0;
  color: var(--gr20);
  height: 18px;
  caret-color: transparent;
}

.input-text:focus {
  caret-color: var(--gr20); /* 포커스 시 커서 색상 표시 */
}

.input-text::placeholder {
  color: var(--gr60);
}

.icon-search {
  position: absolute;
  right: 25px;
  bottom: 7px;
  width: 24px;
  height: 24px;
  margin-right: 2px;
  cursor: pointer;
}

.error-message {
  color: red;
  font-size: 14px;
  margin-top: 200px;
  position: absolute; /* 절대 위치로 고정 */
  top: 30px; /* 검색창 바로 아래에 위치하도록 조정 */
  left: 20px; /* 왼쪽으로 20 띄워줌 */
  width: calc(100% - 40px); /* 좌우 여백을 제외한 전체 너비 */
}

/* 카테고리 제목 */
.category-title {
  margin-top: 42px; /* 카테고리 위에 추가 */
  font-size: 18px;
  font-weight: 600;
  color: var(--gr10);
}

/* 태그 버튼 스타일 */
.category-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 18px;
}

.tag-btn {
  display: inline-block;
  background-color: transparent;
  border: 1px solid var(--p10);
  color: var(--p10);
  border-radius: 20px;
  padding: 6px 6px;
  cursor: pointer;
  font-size: 15px;
  transition: background-color 0.3s, color 0.3s;
}

.tag-btn.selected {
  background-color: var(--p10);
  color: white;
}

.product-list {
  display: flex;
  flex-direction: column; /* 세로로 정렬 */
  justify-content: flex-start; /* 위쪽부터 시작 */
  align-items: center; /* 가로 방향 중앙 정렬 */
  margin-top: 20px;
}

.product-item {
  width: 300px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  background: var(--gr100);
  border-radius: 10px;
  border: 0.5px solid var(--gr60);
  box-shadow: 0 0 6px rgba(0, 0, 0, 0.04);
  margin: 10px 0; /* 아이템 간 간격을 위해 위아래 마진 추가 */
}

.no-result {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 180px; /* 필요한 경우 높이를 조정하세요 */
  color: var(--gr50); /* 원하는 색상 적용 */
  font-weight: normal;
  text-align: center;
}
h3 {
  font-weight: 600;
}

/* 로딩 스피너 스타일 */
.loading-spinner {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 180px; /* 스피너 영역 높이 */
}

.spinner {
  width: 20px;
  height: 20px;
  border: 5px solid rgba(0, 0, 0, 0.1);
  border-top-color: #3e8aff; /* 스피너의 색상 */
  border-radius: 50%;
  animation: spin 1s ease infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}
</style>
