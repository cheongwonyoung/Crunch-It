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
          },
          {
            id: 2,
            bank: 'KB국민',
            title: 'KB스타퀴즈왕적금',
            joinMethods: '인터넷, 스마트폰',
            interestType: '복리',
            sixMonthRate: '1.5%',
            twelveMonthRate: '1.8%',
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
