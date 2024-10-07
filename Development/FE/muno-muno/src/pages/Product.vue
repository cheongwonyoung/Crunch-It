<template>
  <div class="product-recommendation">
    <HeaderQ title="상품추천" @search="handleSearch" />

    <div class="popular-section">
      <h2>가장 인기있는 상품</h2>
    </div>

    <BannerSlider />

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
import axios from 'axios';

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
      allProducts: {
        // UI 확인을 위한 임의 데이터
        예금: [
          {
            id: 1,
            bank: 'KB국민',
            title: '국민 입출금 통장',
            joinMethods: '영업점, 인터넷, 스마트폰',
            interestType: '단리',
            sixMonthRate: '1%',
            twelveMonthRate: '1.3%',
            category: '적금',
          },
          {
            id: 2,
            bank: 'KB국민',
            title: 'KB스타퀴즈왕적금',
            joinMethods: '인터넷, 스마트폰',
            interestType: '복리',
            sixMonthRate: '1.5%',
            twelveMonthRate: '1.8%',
            category: '적금',
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
    async fetchProducts() {
      try {
        const response = await axios.get('백엔드_엔드포인트');
        this.allProducts = response.data;
        this.updateProducts();
      } catch (error) {
        console.error('Error fetching products:', error);
      }
    },
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
    this.fetchProducts();
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
