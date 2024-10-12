<!-- PropertyChart.vue -->
<template>
  <div class="chart-container">
    <div class="chart-box">
      <svg :width="width" :height="height" viewBox="0 0 140 150">
        <defs>
          <radialGradient
            id="skyBlueGradient"
            cx="50%"
            cy="50%"
            r="50%"
            fx="50%"
            fy="50%"
          >
            <stop offset="0%" stop-color="#C1DAFF" stop-opacity="0.3" />
            <stop offset="30%" stop-color="var(--p60)" stop-opacity="0.7" />
            <stop offset="70%" stop-color="var(--p30)" stop-opacity="0.7" />
            <stop offset="80%" stop-color="var(--p20)" stop-opacity="0.5" />
            <stop offset="100%" stop-color="var(--p10)" stop-opacity="0.5" />
          </radialGradient>

          <mask id="previousLevelMask">
            <g v-for="(section, index) in previousSections" :key="index">
              <path
                v-for="(level, levelIndex) in 6"
                :key="`${index}-${levelIndex}`"
                :d="calculatePath(index, levelIndex, 1.02)"
                :fill="levelIndex < section.level ? 'white' : 'black'"
              />
            </g>
          </mask>

          <mask id="currentLevelMask">
            <g v-for="(section, index) in sections" :key="index">
              <path
                v-for="(level, levelIndex) in 6"
                :key="`${index}-${levelIndex}`"
                :d="calculatePath(index, levelIndex, 1.02)"
                :fill="levelIndex < section.level ? 'white' : 'black'"
              />
            </g>
          </mask>
        </defs>

        <g v-for="levelIndex in 6" :key="`grid-${levelIndex}`">
          <circle
            cx="70"
            cy="70"
            :r="levelIndex * 10"
            fill="none"
            :stroke="levelIndex === maxLevel ? 'var(--gr50)' : 'var(--gr60)'"
            stroke-width="0.5"
            stroke-dasharray="1,1"
          />
        </g>

        <g v-for="(section, index) in sections" :key="`lines-${index}`">
          <path
            :d="calculateLinePath(index)"
            fill="none"
            stroke="var(--gr60)"
            stroke-width="0.5"
            stroke-dasharray="1,1"
          />
        </g>

        <circle
          cx="70"
          cy="70"
          r="60"
          fill="var(--gr60)"
          fill-opacity="0.3"
          mask="url(#previousLevelMask)"
        />

        <circle
          cx="70"
          cy="70"
          r="60"
          fill="url(#skyBlueGradient)"
          mask="url(#currentLevelMask)"
        />

        <g v-for="(label, index) in labels" :key="`label-${index}`">
          <text
            :x="calculateLabelPosition(index).x"
            :y="calculateLabelPosition(index).y"
            text-anchor="middle"
            font-size="8"
            :fill="
              sections[index].level === maxLevel ? '#000000' : 'var(--gr50)'
            "
            font-weight="bold"
          >
            {{ label }}
          </text>
        </g>
      </svg>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    width: {
      type: Number,
      default: 300,
    },
    height: {
      type: Number,
      default: 250,
    },
    chartData: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      labels: ['예적금', '주식', '펀드', '채권', '현금자산'],
    };
  },
  computed: {
    levels() {
      return [
        this.chartData.current.savings,
        this.chartData.current.stock,
        this.chartData.current.fund,
        this.chartData.current.bond,
        this.chartData.current.cash,
      ];
    },
    previousLevels() {
      return [
        this.chartData.previous.savings,
        this.chartData.previous.stock,
        this.chartData.previous.fund,
        this.chartData.previous.bond,
        this.chartData.previous.cash,
      ];
    },
    sections() {
      return this.levels.map((level) => ({ level }));
    },
    previousSections() {
      return this.previousLevels.map((level) => ({ level }));
    },
    maxLevel() {
      return Math.max(...this.levels);
    },
  },
  methods: {
    calculatePath(sectionIndex, levelIndex, scale = 1) {
      const startAngle = sectionIndex * (360 / 5);
      const endAngle = (sectionIndex + 1) * (360 / 5);
      const innerRadius = levelIndex * 10 * scale;
      const outerRadius = (levelIndex + 1) * 10 * scale;

      const startRadians = (startAngle - 90) * (Math.PI / 180);
      const endRadians = (endAngle - 90) * (Math.PI / 180);

      const startX = 70 + innerRadius * Math.cos(startRadians);
      const startY = 70 + innerRadius * Math.sin(startRadians);
      const endX = 70 + outerRadius * Math.cos(endRadians);
      const endY = 70 + outerRadius * Math.sin(endRadians);

      const largeArcFlag = endAngle - startAngle <= 180 ? 0 : 1;

      return `
        M ${startX} ${startY}
        A ${innerRadius} ${innerRadius} 0 ${largeArcFlag} 1 ${
        70 + innerRadius * Math.cos(endRadians)
      } ${70 + innerRadius * Math.sin(endRadians)}
        L ${endX} ${endY}
        A ${outerRadius} ${outerRadius} 0 ${largeArcFlag} 0 ${
        70 + outerRadius * Math.cos(startRadians)
      } ${70 + outerRadius * Math.sin(startRadians)}
        Z
      `;
    },
    calculateLinePath(sectionIndex) {
      const angle = sectionIndex * (360 / 5) - 90;
      const radius = 60;
      const radians = angle * (Math.PI / 180);
      const x = 70 + radius * Math.cos(radians);
      const y = 70 + radius * Math.sin(radians);
      return `M 70 70 L ${x} ${y}`;
    },
    calculateLabelPosition(index) {
      let angle = (index * 72 - 54) * (Math.PI / 180);
      let radius = 76;

      return {
        x: 70 + radius * Math.cos(angle),
        y: 70 + radius * Math.sin(angle),
      };
    },
  },
};
</script>

<style scoped>
.chart-container {
  width: 100%;
  display: flex;
  justify-content: center;
}

.chart-box {
  width: 335px;
  height: 320px;
  flex-shrink: 0;
  border-radius: 14px;
  border: 1px solid var(--gr70);
  background: var(--gr100);
  box-shadow: 0px 0px 6px 0px rgba(0, 0, 0, 0.04);
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
