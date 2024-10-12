<template>
  <div class="product-recommendation">
    <HeaderQ title="상품추천" @search="handleSearch" />

    <div class="popular-section">
      <h2>가장 인기있는 상품</h2>
    </div>

    <BannerSlider @banner-click="openModalFromBanner" />

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
        <component
          v-for="product in products"
          :is="getProductItemComponent()"
          :key="product.id"
          :product="product"
          @select="openModal(product)"
        />
      </div>
    </div>

    <!-- 상품 모달 -->
    <component
      v-if="showModal && selectedProduct"
      :is="getModalComponent()"
      :product="selectedProduct"
      :show="showModal"
      @close="closeModal"
    />

    <!-- 배너 모달 -->
    <ModalBanner
      v-if="showBannerModal && selectedBannerProduct"
      :product="selectedBannerProduct"
      :show="showBannerModal"
      @close="closeBannerModal"
    />
  </div>
</template>

<script>
import HeaderQ from '@/components/HeaderQ.vue';
import BannerSlider from '@/components/BannerSlider.vue';
import Category from '@/components/Category.vue';
import ProductBondItem from '@/components/ProductBondItem.vue';
import ProductDepositItem from '@/components/ProductDepositItem.vue';
import ProductFundItem from '@/components/ProductFundItem.vue';
import ProductSavingItem from '@/components/ProductSavingItem.vue';
import ModalBond from '@/components/ModalBond.vue';
import ModalDeposit from '@/components/ModalDeposit.vue';
import ModalFund from '@/components/ModalFund.vue';
import ModalSaving from '@/components/ModalSaving.vue';
import ModalBanner from '@/components/ModalBanner.vue';
import apiClient from '@/axios';

export default {
  name: 'ProductP',
  components: {
    HeaderQ,
    BannerSlider,
    Category,
    ProductBondItem,
    ProductDepositItem,
    ProductFundItem,
    ProductSavingItem,
    ModalBond,
    ModalDeposit,
    ModalFund,
    ModalSaving,
    ModalBanner,
  },
  data() {
    return {
      selectedCategory: '예금',
      showModal: false,
      selectedProduct: null,
      selectedBannerProduct: null,
      showBannerModal: false,
      categories: ['예금', '적금', '펀드', '주식', '채권'],
      products: [], // 선택된 카테고리의 상품들
    };
  },
  methods: {
    async fetchCategoryProducts(category) {
      let apiUrl = '';
      switch (category) {
        case '예금':
          apiUrl = 'http://localhost:8080/recommend/deposit';
          break;
        case '적금':
          apiUrl = 'http://localhost:8080/recommend/saving';
          break;
        case '펀드':
          apiUrl = 'http://localhost:8080/recommend/fund';
          break;
        case '채권':
          apiUrl = 'http://localhost:8080/recommend/bond';
          break;
        default:
          apiUrl = '';
      }

      if (apiUrl) {
        try {
          const response = await apiClient.get(apiUrl);
          switch (category) {
            case '예금':
              this.products = response?.data?.depositList || [];
              break;
            case '적금':
              this.products = response?.data?.savingList || [];
              break;
            case '펀드':
              this.products = response?.data || [];
              break;
            case '채권':
              this.products = response?.data || [];
              break;
          }
          if (this.products.length === 0) {
            console.warn('해당 카테고리에 상품이 없습니다.');
          }
        } catch (error) {
          console.error('API 요청 실패:', error);
          this.products = []; // 요청 실패 시 빈 배열로 설정
        }
      }
    },
    selectCategory(category) {
      this.selectedCategory = category;
      this.fetchCategoryProducts(category); // 카테고리 선택 시 해당 API 호출
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
    getModalComponent() {
      switch (this.selectedCategory) {
        case '예금':
          return 'ModalDeposit';
        case '적금':
          return 'ModalSaving';
        case '펀드':
          return 'ModalFund';
        case '채권':
          return 'ModalBond';
        default:
          return 'ModalDeposit';
      }
    },
    openModal(product) {
      if (product) {
        this.selectedProduct = product;
        this.showModal = true;
      } else {
        console.warn('선택된 상품이 없습니다.');
      }
    },
    closeModal() {
      this.showModal = false;
      this.selectedProduct = null;
    },
    async openModalFromBanner(banner) {
      let apiUrl = '';
      switch (banner.category) {
        case '주식':
          apiUrl = 'http://localhost:8080/recommendation/top-stocks';
          break;
        case '펀드':
          apiUrl = 'http://localhost:8080/recommendation/top-funds';
          break;
        case '채권':
          apiUrl = 'http://localhost:8080/recommendation/top-bonds';
          break;
        default:
          return;
      }

      try {
        const response = await apiClient.get(apiUrl);
        if (response.data && response.data.length > 0) {
          this.selectedBannerProduct = response.data[0];
          this.showBannerModal = true;
        } else {
          console.warn('배너 데이터가 없습니다.');
          this.selectedBannerProduct = null;
        }
      } catch (error) {
        console.error('배너 API 요청 실패:', error);
        this.selectedBannerProduct = null;
      }
    },
    closeBannerModal() {
      this.showBannerModal = false;
      this.selectedBannerProduct = null;
    },
  },
  mounted() {
    this.fetchCategoryProducts(this.selectedCategory); // 페이지 로드 시 기본 카테고리('예금') API 호출
  },
};
</script>

<style scoped>
.product-recommendation {
  overflow-x: hidden;
}

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
