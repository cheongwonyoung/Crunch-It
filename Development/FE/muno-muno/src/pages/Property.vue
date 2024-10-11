<!-- Property.vue -->
<template>
  <div class="property-container">
    <div class="sticky-header">
      <div class="property-header">
        <span class="property-header-text">자산분석</span>
      </div>
    </div>

    <div class="scrollable-content">
      <div class="sub-bar-container">
        <PropertySubBar
          :selectedMonth="selectedMonth"
          :selectedYear="selectedYear"
          :lastUpdated="lastUpdated"
          @toggleCalendar="toggleCalendar"
        />
        <PropertyCalendar
          v-if="showCalendar"
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
    return {
      selectedMonth: new Date().getMonth() + 1,
      selectedYear: new Date().getFullYear(),
      lastUpdated: '2024.10.01',
      showCalendar: false,
      chartData: null,
      cardData: null,
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
      try {
        const response = await apiClient.get('/property/statistics', {
          params: {
            year: this.selectedYear,
            month: this.selectedMonth,
          },
        });
        const { currentMonth, previousMonth } = response.data;
        console.log(response);

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
        const level = Math.round((value / totalAssets) * 5) + 1;
        return Math.min(Math.max(level, 1), 6);
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
}

.sticky-header {
  position: sticky;
  top: 0;
  z-index: 1000;
  background-color: var(--gr100);
}

.property-header {
  width: 375px;
  height: 64px;
  flex-shrink: 0;
  display: flex;
  align-items: center;
  background-color: var(--gr100);
  box-sizing: border-box;
  padding-left: 20px;
}

.property-header-text {
  font-size: 22px;
  font-style: normal;
  font-weight: 600;
  line-height: 100%;
  color: var(--gr10);
}

.sub-bar-container {
  position: relative;
  z-index: 1000;
}

.scrollable-content {
  flex: 1;
  overflow-y: auto;
  -webkit-overflow-scrolling: touch;
  position: relative;

  &::-webkit-scrollbar {
    display: none;
  }

  -ms-overflow-style: none;
  scrollbar-width: none;
}

.analysis {
  margin-top: 30px;
  margin-left: 20px;
}

.analysis-text {
  color: var(--gr30);
  font-family: Pretendard;
  font-size: 20px;
  font-style: normal;
  font-weight: 600;
  line-height: 150%;
}
</style>
