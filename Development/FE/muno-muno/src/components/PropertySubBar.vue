<template>
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
        @click="$emit('toggleCalendar')"
      />
    </div>
    <div class="right-section">
      <span class="update-text">업데이트: {{ lastUpdated }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PropertySubBar',
  props: {
    selectedMonth: {
      type: Number,
      required: true,
    },
    selectedYear: {
      type: Number,
      required: true,
    },
    lastUpdated: {
      type: String,
      required: true,
    },
  },
};
</script>

<style scoped>
.mid-section {
  position: relative;
  width: 375px;
  height: 50px;
  background-color: var(--gr100);
  box-sizing: border-box;
}

.left-section {
  position: absolute;
  top: 6px;
  left: 20px;
  display: flex;
  align-items: center;
}

.calendar-icon {
  width: 28px;
  height: 28px;
}

.month-text {
  margin-left: 4px;
  color: var(--gr20);
  font-size: 22px;
  font-style: normal;
  font-weight: 600;
  line-height: 100%;
}

.toggle-icon {
  width: 20px;
  height: 20px;
  flex-shrink: 0;
  margin-left: 4px;
  cursor: pointer;
}

.right-section {
  position: absolute;
  top: 19px;
  right: 24px;
}

.update-text {
  color: var(--gr50);
  text-align: right;
  font-size: 12px;
  font-style: normal;
  font-weight: 400;
  line-height: 100%;
}
</style>
