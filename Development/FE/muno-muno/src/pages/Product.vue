<template>
  <div class="product-recommendation">
    <HeaderQ title="상품추천" />

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
    <div class="product-list" v-if="selectedCategory === '주식'">
      <div>
        <StockProductItem
          v-for="(stockRank, index) in stockRankList"
          :key="index"
          @click="openStockModal(index)"
          :product="stockRank"
        />
      </div>
    </div>

    <div class="product-list" v-else>
      <div v-if="loading" class="loading">로딩 중...</div>
      <div v-else-if="products.length === 0" class="no-products">
        해당 상품이 없습니다
      </div>
      <div v-else>
        <!-- 리스트가 계속 렌더링되도록 유지 -->
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
    <ModalStock
      v-if="showStockModal && selectedStockList"
      :show="showStockModal"
      :product="selectedStock"
      :productList="selectedStockList"
      @close="closeStockModal"
    />

    <!-- 배너 모달 -->
    <ModalBanner
      v-if="showBannerModal"
      :show="showBannerModal"
      :product="selectedBannerProduct"
      @close="showBannerModal = false"
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
import StockProductItem from '@/components/StockProductItem.vue';
import ModalStock from '@/components/ModalStock.vue';
import apiClient from '@/axios';
import { mapState } from 'pinia';
import { useUserStore } from '@/stores/userStore';

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
    ModalStock,
    ModalBanner,
    StockProductItem,
  },
  computed: {
    ...mapState(useUserStore, ['userInfo']),
  },
  data() {
    return {
      selectedCategory: '예금',
      showModal: false,
      selectedProduct: null,
      selectedBannerProduct: null,
      showBannerModal: false,
      showStockModal: false,
      loading: false, // 로딩 상태 추가
      isBannerClicked: false, // 배너가 클릭되었는지 여부 추가
      categories: ['예금', '적금', '펀드', '채권', '주식'],
      products: [], // 선택된 카테고리의 상품들
      savingProducts: [], // 적금 리스트를 미리 저장
      recommendationData: null, // 추천 데이터를 미리 저장
      stockRankList: [
        {
          title: '한국투자증권',
          content: '상(하)반기 당기 순이익 주식 TOP3',
        },
        {
          title: '한국투자증권',
          content: '시가총액이 높은 주식 TOP3',
        },
        {
          title: '한국투자증권',
          content: '결산 배당금 TOP 3',
        },
      ],
      selectedStock: {},
      selectedStockList: [],
      stockProduct: {
        profit: [],
        amount: [],
        dividend: [],
      },
    };
  },
  methods: {
    async fetchStockProducts() {
      this.loading = true; // API 요청 전 로딩 시작
      const profitRes = await apiClient.get('/recommend/profitrank');
      const amountRes = await apiClient.get('/recommend/amountrank');
      const dividendRes = await apiClient.get('/recommend/dividendrank');
      try {
        this.stockProduct.profit = profitRes?.data.slice(0, 3) || [];
        this.stockProduct.amount = amountRes?.data.slice(0, 3) || [];
        this.stockProduct.dividend = dividendRes?.data.slice(0, 3) || [];
      } catch (err) {
        console.log(err);
      } finally {
        this.loading = false;
      }
    },
    async fetchSavingProducts() {
      try {
        const savingResponse = await apiClient.get('/recommend/saving');
        this.savingProducts = savingResponse?.data?.savingList || [];
      } catch (error) {
        console.error('적금 데이터를 미리 불러오는 중 오류 발생:', error);
      }
    },
    async fetchRecommendationData() {
      try {
        const recommendationResponse = await apiClient.post(
          'http://127.0.0.1:5000/recommendation/product',
          {
            birth: this.birth,
            gender: this.gender,
            salary: this.salary,
          }
        );
        this.recommendationData = recommendationResponse.data;
      } catch (error) {
        console.error('추천 데이터를 미리 불러오는 중 오류 발생:', error);
      }
    },
    async fetchCategoryProducts(category) {
      let apiUrl = '';
      switch (category) {
        case '예금':
          apiUrl = '/recommend/deposit';
          break;
        case '적금':
          apiUrl = '/recommend/saving';
          break;
        case '펀드':
          apiUrl = '/recommend/fund';
          break;
        case '채권':
          apiUrl = '/recommend/bond';
          break;
        default:
          apiUrl = '';
      }

      if (apiUrl) {
        this.loading = true; // API 요청 전 로딩 시작
        try {
          const response = await apiClient.get(apiUrl);
          console.log(response.data);
          switch (category) {
            case '예금':
              this.products = response?.data?.depositList || [];
              // 정렬 로직 추가
              // 정렬: 1순위 - '국민은행', 2순위 - sixMonthOption의 intrRate2 내림차순
              this.products.sort((a, b) => {
                // '국민은행' 우선
                if (a.korCoNm === '국민은행' && b.korCoNm !== '국민은행')
                  return -1;
                if (b.korCoNm === '국민은행' && a.korCoNm !== '국민은행')
                  return 1;

                // sixMonthOption의 intrRate2 내림차순 정렬
                const aIntrRate2 = a.sixMonthOption?.intrRate2 || 0;
                const bIntrRate2 = b.sixMonthOption?.intrRate2 || 0;
                return bIntrRate2 - aIntrRate2;
              });
              break;
            case '적금':
              this.products = response?.data?.savingList || [];
              // 정렬 로직 추가
              // '국민은행' 우선 정렬 후, '국민은행' 내에서 12개월 옵션 intrRate2로 정렬하고, 없으면 6개월 옵션 기준
              this.products.sort((a, b) => {
                // '국민은행' 우선
                if (a.korCoNm === '국민은행' && b.korCoNm !== '국민은행')
                  return -1;
                if (b.korCoNm === '국민은행' && a.korCoNm !== '국민은행')
                  return 1;

                // 둘 다 '국민은행'이거나 둘 다 아니면 intrRate2 기준으로 정렬
                const aIntrRate2 =
                  a.yearOption?.['12']?.intrRate2 !== undefined
                    ? a.yearOption['12'].intrRate2
                    : a.sixMonthOption?.intrRate2 !== undefined
                    ? a.sixMonthOption.intrRate2
                    : -Infinity;
                const bIntrRate2 =
                  b.yearOption?.['12']?.intrRate2 !== undefined
                    ? b.yearOption['12'].intrRate2
                    : b.sixMonthOption?.intrRate2 !== undefined
                    ? b.sixMonthOption.intrRate2
                    : -Infinity;

                return bIntrRate2 - aIntrRate2;
              });
              break;
            case '펀드':
              this.products = response?.data || [];
              break;
            case '채권':
              this.products = response?.data || [];
              break;
          }
        } catch (error) {
          console.error('API 요청 실패:', error);
          this.products = []; // 요청 실패 시 빈 배열로 설정
        } finally {
          this.loading = false; // 로딩 종료
        }
      }
    },
    selectCategory(category) {
      this.isBannerClicked = false; // 카테고리 선택 시 배너 클릭 상태 초기화
      this.selectedCategory = category;
      if (category === '주식') {
        this.fetchStockProducts();
      } else {
        this.fetchCategoryProducts(category); // 카테고리 선택 시 해당 API 호출
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
    openStockModal(index) {
      switch (index) {
        case 0:
          this.selectedStockList = this.stockProduct.profit;
          this.selectedStock = this.stockRankList[0];
          break;
        case 1:
          this.selectedStockList = this.stockProduct.amount;
          this.selectedStock = this.stockRankList[1];
          break;
        case 2:
          this.selectedStockList = this.stockProduct.dividend;
          this.selectedStock = this.stockRankList[2];
          break;
      }
      this.showStockModal = true;
    },
    closeStockModal() {
      this.showStockModal = false;
      this.selectedStock = null;
      this.selectedStockList = [];
    },
    async openModalFromBanner(banner) {
      this.isBannerClicked = true;
      if (banner.category === '적금') {
        try {
          // 적금 리스트가 미리 로드된 데이터를 사용
          if (this.savingProducts.length === 0) {
            await this.fetchSavingProducts(); // 적금 리스트가 없는 경우 새로 불러오기
          }

          // 미리 로드된 추천 데이터 사용
          let { CODE, probability } = this.recommendationData[0];
          CODE = String(CODE).padStart(12, '0');

          const matchedProduct = this.savingProducts.find((product) => {
            const cleanFinPrdtCd = String(product.finPrdtCd).replace(
              /[^0-9]/g,
              ''
            );
            return cleanFinPrdtCd === CODE;
          });

          if (matchedProduct) {
            this.selectedBannerProduct = {
              ...matchedProduct,
              savingCode: matchedProduct.finPrdtCd,
              savingTitle: matchedProduct.finPrdtNm,
              savingBadge: matchedProduct.korCoNm,
              probability: `${parseFloat(probability).toFixed(2)}%`,
            };
            this.showBannerModal = true;
          } else {
            console.warn('일치하는 적금 상품을 찾을 수 없습니다.');
          }
        } catch (error) {
          console.error('적금 배너 API 요청 실패:', error);
        }
      } else {
        let apiUrl = '';
        switch (banner.category) {
          case '주식':
            apiUrl = '/recommendation/top-stocks';
            break;
          case '펀드':
            apiUrl = '/recommendation/top-funds';
            break;
          case '채권':
            apiUrl = '/recommendation/top-bonds';
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
          }
        } catch (error) {
          console.error('배너 API 요청 실패:', error);
        }
      }
    },
    closeBannerModal() {
      this.showBannerModal = false;
      this.selectedBannerProduct = null;
    },
  },
  async mounted() {
    this.fetchCategoryProducts(this.selectedCategory); // 페이지 로드 시 기본 카테고리('예금') API 호출
    this.birth = this.userInfo.birth;
    this.gender = this.userInfo.gender;
    this.salary = this.userInfo.salary;
    this.fetchSavingProducts(); // 적금 데이터를 미리 불러오기
    await this.fetchRecommendationData(); // 추천 데이터를 미리 불러오기
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
  font-family: 'Pretendard', sans-serif;
}

.category-buttons {
  padding-left: 20px;
  display: flex;
  font-family: 'Pretendard', sans-serif;
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

.loading {
  font-size: 16px;
  color: var(--gr60);
  text-align: center;
}
</style>
