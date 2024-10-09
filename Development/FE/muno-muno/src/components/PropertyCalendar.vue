<template>
  <div class="calendar-popup">
    <div class="calendar-header">
      <button class="nav-button" @click="changeYear(-1)">&lt;</button>
      <span class="year-display">{{ currentYear }}</span>
      <button class="nav-button" @click="changeYear(1)">&gt;</button>
    </div>
    <div class="month-grid">
      <button
        v-for="month in 12"
        :key="month"
        class="month-button"
        :class="{
          selected: month === selectedMonth && currentYear === selectedYear,
          hover: month === hoverMonth,
        }"
        @click="selectMonth(month)"
        @mouseenter="hoverMonth = month"
        @mouseleave="hoverMonth = null"
      >
        {{ month }}월
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PropertyCalendar',
  props: {
    isVisible: Boolean,
    selectedMonth: Number,
    selectedYear: Number,
  },
  data() {
    return {
      currentYear: this.selectedYear,
      hoverMonth: null,
    };
  },
  methods: {
    changeYear(delta) {
      this.currentYear += delta;
    },
    selectMonth(month) {
      this.$emit('monthSelected', { year: this.currentYear, month });
    },
  },
};
</script>

<style scoped>
.calendar-popup {
  position: absolute;
  top: 100%;
  left: 20px;
  z-index: 1000;
  width: 300px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  padding: 16px;
}

.calendar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.nav-button {
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
}

.year-display {
  font-size: 18px;
  font-weight: bold;
}

.month-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 8px;
}

.month-button {
  padding: 10px;
  background: none;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
}

.month-button.hover {
  background-color: #e8f5e9;
}

.month-button.selected {
  background-color: var(--p10);
  color: white;
  border-color: var(--p10);
}
</style>
