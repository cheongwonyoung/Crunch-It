<template>
  <section class="level-section">
    <p class="level-text">{{ levelText }}</p>
    <!-- 레벨에 맞는 문어 텍스트 표시 -->
    <p
      class="level-description"
      @click="navigateToProductRecommendation"
      ref="descriptionText"
    >
      문어를 성장시키고 싶다면? >
    </p>

    <img :src="currentOctopus.image" alt="문어 이미지" class="octopus-image" />
    <MunoTooltip class="tooltip" />
  </section>
</template>

<script>
import MunoTooltip from '@/components/MunoTooltip.vue';

export default {
  props: {
    currentLevel: {
      type: Number,
      required: true,
    },
    currentOctopus: {
      type: Object,
      required: true,
    },
    levelText: {
      type: String,
      required: true,
    },
  },
  components: {
    MunoTooltip,
  },
  methods: {
    navigateToProductRecommendation() {
      this.$emit('navigate');
    },
  },
};
</script>

<style scoped>
.level-section {
  padding-top: 91px;
  margin: 0 22px;
}

.level-text {
  color: var(--gray-20);
  font-size: 24px;
  font-weight: 500;
  line-height: 100%;
  margin: 0 0 10px 0;
}

.level-description {
  color: var(--gr50);
  font-size: 14px;
  font-weight: 400;
  line-height: 100%;
  margin: 10px 0 0 0;
  cursor: pointer;
}

.octopus-image {
  width: 374px;
  height: 250px;
  top: 80px;
  position: relative;
  display: block;
  left: 50%;
  transform: translateX(-50%);
}

.tooltip {
  position: absolute;
  top: 232px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 2;
}
</style>
