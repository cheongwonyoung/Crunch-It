<template>
  <!-- 차트 박스를 감싸는 컨테이너. 크기와 테두리를 정의 -->
  <div class="chart-box">
    <!-- SVG 정의, width와 height는 외부에서 받아옴, viewBox로 좌표계를 설정 -->
    <svg :width="width" :height="height" viewBox="0 0 140 140">
      <defs>
        <!-- 방사형 그래디언트 정의, 하늘색에서 점점 짙어지는 색상 변화를 설정 -->
        <radialGradient
          id="skyBlueGradient"
          cx="50%"
          cy="50%"
          r="50%"
          fx="50%"
          fy="50%"
        >
          <!-- 중심에서부터 바깥쪽으로 색상이 변화하는 방식 정의 -->
          <stop offset="0%" stop-color="#C1DAFF" stop-opacity="0.9" />
          <stop offset="70%" stop-color="var(--p10)" stop-opacity="0.7" />
          <stop offset="100%" stop-color="var(--p10)" stop-opacity="0.5" />
        </radialGradient>

        <!-- 마스크 정의 (전달 기준 데이터에 사용됨) -->
        <mask id="previousLevelMask">
          <!-- 이전 달 데이터용 섹션 그리기 -->
          <g v-for="(section, index) in previousSections" :key="index">
            <path
              v-for="(level, levelIndex) in 6"
              :key="`${index}-${levelIndex}`"
              :d="calculatePath(index, levelIndex, 1.02)"
              :fill="levelIndex < section.level ? 'white' : 'black'"
            />
          </g>
        </mask>

        <!-- 현재 달 기준 데이터용 마스크 -->
        <mask id="currentLevelMask">
          <!-- 현재 달 데이터용 섹션 그리기 -->
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

      <!-- 점선 그리드 그리기: 차트의 각 레벨을 구분하는 원형 라인 -->
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

      <!-- 방사형 차트의 기준선을 표시: 차트의 각 섹션 경계선 -->
      <g v-for="(section, index) in sections" :key="`lines-${index}`">
        <path
          :d="calculateLinePath(index)"
          fill="none"
          stroke="var(--gr60)"
          stroke-width="0.5"
          stroke-dasharray="1,1"
        />
      </g>

      <!-- 전달 기준 데이터 (회색 부분) -->
      <circle
        cx="70"
        cy="70"
        r="60"
        fill="var(--gr70)"
        fill-opacity="0.3"
        mask="url(#previousLevelMask)"
      />

      <!-- 이번 달 기준 데이터 (하늘색 부분) -->
      <circle
        cx="70"
        cy="70"
        r="60"
        fill="url(#skyBlueGradient)"
        mask="url(#currentLevelMask)"
      />

      <!-- 차트 라벨 (예적금, 주식 등 항목) -->
      <g v-for="(label, index) in labels" :key="`label-${index}`">
        <text
          :x="calculateLabelPosition(index).x"
          :y="calculateLabelPosition(index).y"
          text-anchor="middle"
          font-size="6"
          :fill="sections[index].level === maxLevel ? '#000000' : 'var(--gr50)'"
          font-weight="bold"
        >
          <!-- 라벨 표시 -->
          {{ label }}
        </text>
      </g>
    </svg>
  </div>
</template>

<script>
import { computed } from 'vue';

export default {
  props: {
    width: {
      type: Number,
      default: 300, // 기본 폭 300px
    },
    height: {
      type: Number,
      default: 250, // 기본 높이 250px
    },
    levels: {
      type: Array,
      default: () => [5, 4, 1, 5, 2], // 이번 달 데이터 (하늘색): 예적금, 주식, 펀드, 채권, 현금자산
      validator: (value) =>
        value.length === 5 && value.every((v) => v >= 0 && v <= 6),
    },
    previousLevels: {
      type: Array,
      default: () => [1.5, 6, 4.8, 1.5, 6], // 전달 데이터 (회색): 예적금, 주식, 펀드, 채권, 현금자산
      validator: (value) =>
        value.length === 5 && value.every((v) => v >= 0 && v <= 6),
    },
  },
  setup(props) {
    // 이번 달 데이터와 전달 데이터 계산
    const sections = computed(() => {
      return props.levels.map((level) => ({ level }));
    });

    const previousSections = computed(() => {
      return props.previousLevels.map((level) => ({ level }));
    });

    const maxLevel = computed(() => Math.max(...props.levels)); // 가장 높은 레벨 값 계산

    const labels = ['예적금', '주식', '펀드', '채권', '현금자산'];

    // 차트 섹션 경계를 계산하여 방사형으로 배치
    const calculatePath = (sectionIndex, levelIndex, scale = 1) => {
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
    };

    // 방사형 차트의 선 계산
    const calculateLinePath = (sectionIndex) => {
      const angle = sectionIndex * (360 / 5) - 90;
      const radius = 60;
      const radians = angle * (Math.PI / 180);
      const x = 70 + radius * Math.cos(radians);
      const y = 70 + radius * Math.sin(radians);
      return `M 70 70 L ${x} ${y}`;
    };

    // 라벨의 위치 계산: 차트 바깥쪽에 라벨 배치
    const calculateLabelPosition = (index) => {
      let angle = (index * 72 - 54) * (Math.PI / 180);
      let radius = 76;

      //   // 펀드 라벨 (index 2) 특별 처리
      //   if (index === 3) {
      //     radius = 76;
      //     angle = (index * 72 - 52) * (Math.PI / 180);
      //   }

      return {
        x: 70 + radius * Math.cos(angle),
        y: 70 + radius * Math.sin(angle),
      };
    };

    return {
      sections,
      previousSections,
      labels,
      maxLevel,
      calculatePath,
      calculateLinePath,
      calculateLabelPosition,
    };
  },
};
</script>

<style scoped>
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
