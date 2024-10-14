<!-- Property.vue -->
<template>
  <div class="property-container">
    <div class="fixed-header">
      <div class="property-header">
        <span class="property-header-text">자산분석</span>
      </div>
    </div>

    <div v-if="isLoading" class="loading-container">
      <div class="loading-spinner"></div>
      <p>분석 중...</p>
    </div>

    <div v-else class="scrollable-content">
      <div class="sub-bar-container">
        <PropertySubBar
          :selectedMonth="selectedMonth"
          :selectedYear="selectedYear"
          :lastUpdated="lastUpdated"
          @toggleCalendar="toggleCalendar"
        />
      </div>
      <div class="calendar-wrapper" v-if="showCalendar">
        <PropertyCalendar
          :selectedMonth="selectedMonth"
          :selectedYear="selectedYear"
          @monthSelected="updateMonth"
        />
      </div>

      <PropertyChart v-if="chartData" :chartData="chartData" />

      <div class="analysis">
        <span class="analysis-text"
          >전달대비 자산변동을<br />한눈에 보여드려요</span
        >
      </div>

      <PropertyCardContainer v-if="cardData" :cardData="cardData" />
    </div>
  </div>
</template>

<script>
import PropertyChart from '@/components/PropertyChart.vue';
import PropertySubBar from '@/components/PropertySubBar.vue';
import PropertyCalendar from '@/components/PropertyCalendar.vue';
import PropertyCardContainer from '@/components/PropertyCardContainer.vue';
import apiClient from '@/axios';

export default {
  name: 'PropertyP',
  components: {
    PropertyChart,
    PropertySubBar,
    PropertyCalendar,
    PropertyCardContainer,
  },
  data() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');

    return {
      selectedMonth: today.getMonth() + 1,
      selectedYear: year,
      lastUpdated: `${year}.${month}.${day}`,
      showCalendar: false,
      chartData: null,
      cardData: null,
      isLoading: false,
    };
  },
  methods: {
    toggleCalendar() {
      this.showCalendar = !this.showCalendar;
    },
    updateMonth({ year, month }) {
      this.selectedYear = year;
      this.selectedMonth = month;
      this.showCalendar = false;
      this.fetchPropertyData();
    },
    async fetchPropertyData() {
      this.isLoading = true;
      try {
        const response = await apiClient.get('/property/statistics', {
          params: {
            year: this.selectedYear,
            month: this.selectedMonth,
          },
        });
        const { currentMonth, previousMonth } = response.data;
        console.log(response.data);

        if (currentMonth && previousMonth) {
          this.chartData = {
            current: this.calculateChartLevels(currentMonth),
            previous: this.calculateChartLevels(previousMonth),
          };

          this.cardData = {
            asset: {
              current: currentMonth.totalAccountBalance,
              previous: previousMonth.totalAccountBalance,
            },
            expense: {
              current: currentMonth.totalOutcome,
              previous: previousMonth.totalOutcome,
            },
            investment: {
              current:
                currentMonth.stockProfitAmount +
                currentMonth.fundProfitAmount +
                currentMonth.bondProfitAmount,
              previous:
                previousMonth.stockProfitAmount +
                previousMonth.fundProfitAmount +
                previousMonth.bondProfitAmount,
            },
          };
        } else {
          console.error('Invalid data received from the server');
          this.chartData = null;
          this.cardData = null;
        }
      } catch (error) {
        console.error('Error fetching property data:', error);
        this.chartData = null;
        this.cardData = null;
      } finally {
        this.isLoading = false;
      }
    },
    calculateChartLevels(data) {
      const totalAssets =
        data.savingsAmount +
        data.stockInvestAmount +
        data.fundInvestAmount +
        data.bondInvestAmount +
        data.totalAccountBalance;

      const calculateLevel = (value) => {
        if (value === 0) return 0;
        const percentage = (value / totalAssets) * 100;
        if (percentage < 5) return 1;
        if (percentage < 10) return 2;
        if (percentage < 20) return 3;
        if (percentage < 30) return 4;
        if (percentage < 40) return 5;
        return 6;
      };

      return {
        savings: calculateLevel(data.savingsAmount),
        stock: calculateLevel(data.stockInvestAmount),
        fund: calculateLevel(data.fundInvestAmount),
        bond: calculateLevel(data.bondInvestAmount),
        cash: calculateLevel(data.totalAccountBalance),
      };
    },
  },
  watch: {
    selectedMonth() {
      this.fetchPropertyData();
    },
    selectedYear() {
      this.fetchPropertyData();
    },
  },
  mounted() {
    console.log('Component mounted');
    this.fetchPropertyData();
  },
};
</script>

<style scoped>
.property-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: var(--gr100);
}

.fixed-header {
  position: fixed;
  top: 44px; /* StatusBar 높이 */
  left: 0;
  right: 0;
  z-index: 1000;
  background-color: var(--gr100);
}

.property-header {
  width: 100%;
  height: 64px;
  display: flex;
  align-items: center;
  padding-left: 20px;
  background-color: var(--gr100);
}

.property-header-text {
  font-size: 22px;
  font-weight: 600;
  line-height: 100%;
  color: var(--gr10);
}

.scrollable-content {
  flex: 1;
  overflow-y: auto;
  -webkit-overflow-scrolling: touch;
  scrollbar-width: none;
  padding-top: 64px; /* 헤더(64px) 높이 */
}

.scrollable-content::-webkit-scrollbar {
  display: none;
}

.sub-bar-container {
  position: sticky;
  background-color: var(--gr100);
  z-index: 999;
}

.calendar-wrapper {
  position: sticky;
  top: 50px;
  background-color: var(--gr100);
  z-index: 998;
}

.analysis {
  margin-top: 30px;
  margin-left: 20px;
}

.analysis-text {
  color: var(--gr30);
  font-family: Pretendard, sans-serif;
  font-size: 20px;
  font-weight: 600;
  line-height: 150%;
}

.loading-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  font-size: 18px;
  color: var(--gr30);
}

.loading-spinner {
  border: 4px solid var(--gr70);
  border-top: 4px solid var(--p50);
  border-radius: 50%;
  width: 40px;
  height: 40px;
  animation: spin 1s linear infinite;
  margin-bottom: 20px;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>
