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

      <PropertyChart />

      <div class="analysis">
        <span class="analysis-text"
          >전달대비 자산변동을<br />한눈에 보여드려요</span
        >
      </div>

      <PropertyCardContainer />
    </div>
  </div>
</template>

<script>
import PropertyChart from '@/components/PropertyChart.vue';
import PropertySubBar from '@/components/PropertySubBar.vue';
import PropertyCalendar from '@/components/PropertyCalendar.vue';
import PropertyCardContainer from '@/components/PropertyCardContainer.vue';

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
    },
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
