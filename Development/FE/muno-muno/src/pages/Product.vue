<template>
  <div class="product-recommendation">
    <HeaderQ title="상품추천" @search="handleSearch" />

    <div class="popular-section">
      <h2>가장 인기있는 상품</h2>
    </div>

    <BannerSlider @banner-click="openModal" />

    <div class="categories">
      <div class="category-buttons">
        <Category
          v-for="category in categories"
          :key="category"
          :category="category"
          :isActive="selectedCategory === category"
          @category-selected="selectCategory"
        />
      </div>
      <div class="base-underline"></div>
    </div>

    <div class="product-list">
      <div v-if="products.length === 0" class="no-products">
        해당 상품이 없습니다
      </div>
      <div v-else>
        <ProductItem
          v-for="product in products"
          :key="product.id"
          :product="product"
          @click="openModal(product)"
        />
      </div>
    </div>

    <!-- ProductModal -->
    <ProductModal
      v-if="selectedProduct"
      :show="showModal"
      :product="selectedProduct"
      @close="closeModal"
    />
  </div>
</template>

<script>
import HeaderQ from '@/components/HeaderQ.vue';
import ProductItem from '@/components/ProductItem.vue';
import BannerSlider from '@/components/BannerSlider.vue';
import Category from '@/components/Category.vue';
import ProductModal from '@/components/Modal.vue';

export default {
  name: 'ProductP',
  components: {
    HeaderQ,
    ProductItem,
    BannerSlider,
    Category,
    ProductModal,
  },
  data() {
    return {
      selectedCategory: '예금',
      showModal: false,
      selectedProduct: null,
      categories: ['예금', '적금', '펀드', '주식', '채권'],
      products: [],
      // 초기 UI 확인 차 임의 데이터. 추후 삭제 바람.
      allProducts: {
        예금: [
          {
            id: 1,
            bank: 'KB국민',
            title: '국민 입출금 통장',
            joinMethods: '영업점, 인터넷, 스마트폰',
            interestType: '단리',
            sixMonthRate: '1%',
            twelveMonthRate: '1.3%',
            category: '예금',
          },
          {
            id: 2,
            bank: 'KB국민',
            title: 'KB스타퀴즈왕적금',
            joinMethods: '인터넷, 스마트폰',
            interestType: '복리',
            sixMonthRate: '1.5%',
            twelveMonthRate: '1.8%',
            category: '예금',
          },
          {
            id: 3,
            bank: 'KB국민',
            title: 'KB Star 정기예금',
            joinMethods: '인터넷, 스마트폰',
            interestType: '복리',
            sixMonthRate: '2.5%',
            twelveMonthRate: '1.8%',
            category: '예금',
          },
          {
            id: 4,
            bank: 'KB국민',
            title: 'KB국민UP정기예금',
            joinMethods: '인터넷, 스마트폰',
            interestType: '복리',
            sixMonthRate: '2.90%%',
            twelveMonthRate: '1.8%',
            category: '예금',
          },
          {
            id: 5,
            bank: 'KB국민',
            title: '국민수퍼정기예금(개인)',
            joinMethods: '인터넷, 스마트폰',
            interestType: '복리',
            sixMonthRate: '2.5%',
            twelveMonthRate: '1.8%',
            category: '예금',
          },
        ],
        펀드: [
          {
            id: 3,
            bank: '신한',
            title: '신한 글로벌펀드',
            fundProduct: '글로벌 주식형 펀드',
            category: '펀드',
          },
        ],
        채권: [
          {
            id: 4,
            bank: '우리',
            title: '우리채권',
            bondRating: 'AAA',
            couponRate: '2.0%',
            maturityDate: '2026-12-31',
            interestPaymentDate: '매년 12월 31일',
            category: '채권',
          },
        ],
        주식: [
          {
            id: 5,
            bank: '삼성',
            title: '삼성전자',
            marketCapWeight: '15%',
            category: '주식',
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
      this.products = this.allProducts[this.selectedCategory] || [];
    },
    handleSearch() {
      console.log('Search clicked');
    },
    openModal(product) {
      this.selectedProduct = product;
      this.showModal = true;
    },
    closeModal() {
      this.showModal = false;
      this.selectedProduct = null;
    },
  },
  mounted() {
    this.updateProducts();
  },
};
</script>

<style scoped>
.popular-section {
  margin-top: 78px;
}

.popular-section h2 {
  color: var(--gr30);
  font-size: 20px;
  font-weight: 600;
  line-height: 100%;
  padding-left: 20px;
}

.categories {
  text-align: center;
  font-size: 16px;
  font-weight: 600;
  line-height: 150%;
  margin-bottom: 14px;
  position: relative;
}

.category-buttons {
  padding-left: 20px;
  display: flex;
}

.base-underline {
  height: 0.5px;
  background: var(--gr70);
  position: absolute;
  width: 100%;
}

.product-list {
  font-size: 16px;
  color: var(--gr60);
  text-align: center;
}
</style>
