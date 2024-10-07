<template>
  <div>
    <!-- 헤더 -->
    <div class="property-header">
      <span class="property-header-text">자산분석</span>
    </div>

    <!-- 중간 부분 (달력 아이콘, 월 선택, 업데이트 날짜) -->
    <div class="mid-section">
      <div class="left-section">
        <img
          src="@/assets/calendar.svg"
          alt="Calendar Icon"
          class="calendar-icon"
        />
        <span class="month-text">{{ selectedMonth }}월</span>
        <img
          src="@/assets/toggle.svg"
          alt="Toggle Icon"
          class="toggle-icon"
          @click="toggleCalendar"
        />
      </div>
      <div class="right-section">
        <span class="update-text">업데이트: {{ lastUpdated }}</span>
      </div>
    </div>

    <!-- 달력 컴포넌트 (토글바 클릭 시 나오는 달력) -->
    <Calendar v-if="showCalendar" @monthSelected="updateMonth" />

    <!-- 자산 분석 그래프 -->
    <PropertyChart />
  </div>
</template>

<script>
import PropertyChart from '@/components/PropertyChart.vue';

export default {
  name: 'PropertyP', // 다중 단어로 변경
  components: { PropertyChart },
};
</script>

<style scoped>
/* Header 스타일 */
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

/* 자산분석 헤더 텍스트 스타일 */
.property-header-text {
  font-size: 22px;
  font-style: normal;
  font-weight: 600;
  line-height: 100%; /* 22px */
  color: var(--gr10);
}
</style>
