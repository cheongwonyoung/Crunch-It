<template>
  <div class="homepage">
    <!-- 헤더 -->
    <div class="header">
      <img src="@/assets/munoLogo.svg" alt="Muno 로고" class="logo" />

      <router-link to="/mypage" class="mypage">
        <img
          src="@/assets/mypage.svg"
          alt="마이페이지 아이콘"
          class="mypage-icon"
        />
      </router-link>
    </div>

    <!-- 지출 금액 섹션 -->
    <section class="expense-section">
      <p class="subtitle">{{ currentMonth }} 지출 금액</p>
      <h1 class="amount">{{ expenseAmount }}원</h1>

      <!-- 그래프 -->
      <div class="graph">
        <div class="bar blue"></div>
        <div class="vs">VS</div>
        <div class="bar white"></div>
      </div>
    </section>

    <!-- 레벨 -->
    <div class="level">
      <img src="@/assets/crown.svg" alt="왕관" class="icon" />
      <p>문어 별로 각자 레벨을 확인해 보세요!</p>
      <img src="@/assets/arrowRight.svg" alt="화살표" class="arrow-icon" />
    </div>

    <!-- 자산분석 카드 -->
    <section class="analysis-section">
      <h3 class="analysis-title">자산분석</h3>
      <p class="analysis-description">
        전달 대비, 소비가 <span class="highlight">증가</span>했어요
      </p>
      <!-- 막대 그래프 -->
      <div class="chart">
        <div class="month">08월</div>
        <div class="month">09월</div>
      </div>
      <router-link to="/recommendation" class="recommend-link">
        슬기로운 자산 관리 상품 추천 받기
      </router-link>
    </section>

    <!-- 환율/증시 카드 -->
    <section class="currency-stock-card">
      <div class="tabs">
        <button
          class="tab"
          :class="{ active: selectedTab === '환율' }"
          @click="selectTab('환율')"
        >
          환율
        </button>
        <button
          class="tab"
          :class="{ active: selectedTab === '증시' }"
          @click="selectTab('증시')"
        >
          증시
        </button>
      </div>
      <div class="currency-stock-content">
        <!-- 환율 정보 -->
        <div v-if="selectedTab === '환율'">
          <p>환율 정보 표시</p>
        </div>
        <!-- 증시 정보 -->
        <div v-else>
          <div class="market-info">
            <div class="market-item">
              <p class="market-title">KOSPI</p>
              <p class="market-value">{{ kospiValue }}</p>
              <p class="market-change" :class="kospiChangeClass">
                {{ kospiChange }}
              </p>
            </div>
            <div class="market-item">
              <p class="market-title">KOSDAQ</p>
              <p class="market-value">{{ kosdaqValue }}</p>
              <p class="market-change" :class="kosdaqChangeClass">
                {{ kosdaqChange }}
              </p>
            </div>
          </div>
          <p class="date-time">{{ currentTime }}</p>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'HomePage',
  data() {
    return {
      currentMonth: '0월',
      expenseAmount: 0,
      selectedTab: '환율',
      kospiValue: '0',
      kospiChange: '0',
      kosdaqValue: '0',
      kosdaqChange: '0',
      currentTime: '0',
    };
  },
  created() {
    this.fetchExpenseData();
  },
  methods: {
    async fetchExpenseData() {
      try {
        const response = await axios.get('https://api.example.com/expenses');
        this.expenseAmount = response.data.amount;
        this.currentMonth = response.data.month;
      } catch (error) {
        console.error('데이터를 가져오는데 실패했습니다:', error);
      }
    },
    selectTab(tab) {
      this.selectedTab = tab;
    },
  },
  computed: {
    kospiChangeClass() {
      return this.kospiChange.startsWith('+') ? 'positive' : 'negative';
    },
    kosdaqChangeClass() {
      return this.kosdaqChange.startsWith('+') ? 'positive' : 'negative';
    },
  },
};
</script>

<style scoped>
/* 전체 페이지 스크롤 숨기기 */
.homepage {
  overflow-y: hidden;
}

/* 헤더 */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 22px;
  height: 65px;
  background-color: var(--p70);
  position: fixed;
  top: 44px;
  left: 0;
  width: 100%;
  z-index: 1000;
}

.mypage-icon {
  width: 24px;
  height: 24px;
}

/* 아래 콘텐츠가 헤더 아래로 배치되도록 여백 추가 */
.expense-section {
  padding: 16px 20px;
  background-color: var(--p70);
  margin-top: 65px;
}

.logo {
  height: 17px;
}

.mypage {
  height: 21px;
}

/* 지출 금액 */
.expense-section {
  padding: 16px 20px;
  background-color: var(--p70);
}

.subtitle {
  font-size: 20px;
  color: var(--gr10);
  margin: 0 0 6px 4px;
}

.amount {
  color: var(--gr10);
  font-size: 38px;
  font-weight: 500;
  line-height: 100%;
  margin: 0 0 24px 2px;
}

/* 그래프 */
.graph {
  display: flex;
  align-items: center;
  justify-content: center;
}

.bar {
  width: 110px;
  height: 110px;
  border-radius: 10px;
}

.blue {
  background-color: #5398ff;
}

.white {
  background-color: var(--gr100);
  border: 1px solid #e0e0e0;
}

.vs {
  margin: 0 13px;
  font-size: 16px;
  font-weight: 600;
  color: var(--gr50);
}

/* 레벨 */
.level {
  background-color: var(--gr80);
  padding: 11px 16px;
  border: 0.5px solid var(--gr60);
  border-radius: 10px;
  display: flex;
  align-items: center;
  margin: 25px 20px 10px;
}

.icon {
  width: 24px;
  height: 24px;
  margin-right: 8px;
}

.arrow-icon {
  width: 20px;
  height: 20px;
}

.level p {
  font-size: 14px;
  color: var(--gr20);
  margin: 0;
  flex-grow: 1;
}

/* 자산분석 카드 */
.analysis-section {
  background-color: var(--gr100);
  border: 0.5px solid var(--gr60);
  border-radius: 10px;
  padding: 30px 22px;
  margin: 20px;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.05);
}

.analysis-title {
  color: var(--p10);
  font-size: 14px;
  font-weight: 600;
  margin: 0 0 8px;
}

.analysis-description {
  color: var(--gr20);
  font-size: 22px;
  font-weight: 500;
  margin: 8px 0 40px;
}

.highlight {
  color: var(--p10);
  font-weight: 500;
}

.chart {
  display: flex;
  justify-content: space-between;
  margin-bottom: 13px;
}

.month {
  font-size: 12px;
  color: var(--gr40);
}

.recommend-link {
  display: block;
  font-size: 13px;
  text-align: center;
  color: var(--gr20);
  text-decoration: none;
  padding-top: 13px;
  border-top: 1px solid var(--gr80);
}

/* 환율,증시 카드 */
.currency-stock-card {
  background-color: var(--gr100);
  border: 0.5px solid var(--gr60);
  border-radius: 10px;
  padding: 20px;
  margin: 20px;
  box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.05);
}

.tabs {
  display: flex;
  justify-content: center;
  margin-bottom: 10px;
}

.tab {
  background-color: var(--gr70);
  border: none;
  padding: 10px 20px;
  font-size: 14px;
  color: var(--gr40);
  border-radius: 20px;
  cursor: pointer;
  margin: 0 5px;
  transition: background-color 0.2s ease;
}

.tab.active {
  background-color: var(--gr100);
  color: var(--gr10);
  font-weight: bold;
}

.currency-stock-content {
  text-align: center;
}

.market-info {
  display: flex;
  justify-content: space-between;
  padding: 0 20px;
}

.market-item {
  text-align: center;
}

.market-title {
  font-size: 14px;
  color: var(--gr50);
}

.market-value {
  font-size: 22px;
  font-weight: 600;
  color: var(--gr10);
}

.market-change {
  font-size: 12px;
  margin-top: 5px;
}

.positive {
  /* 상승일 때 */
  color: #f23f3f;
}

.negative {
  /* 하락일 때 */
  color: blue;
}

.date-time {
  margin-top: 10px;
  font-size: 12px;
  color: var(--gr40);
}
</style>
