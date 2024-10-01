<template>
  <div class="tooltip">
    <div class="tooltip-text">{{ currentMessage }}</div>
    <div class="tooltip-tail">
      <img src="@/assets/tail.svg" alt="말풍선 꼬리" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'MunoTooltip',
  data() {
    return {
      currentMessageIndex: 0,
      messages: ['상품을 가입해볼까?', '지출을 줄여봐!', '나를 성장시켜줘!'],
    };
  },
  computed: {
    currentMessage() {
      return this.messages[this.currentMessageIndex];
    },
  },
  mounted() {
    this.startMessageRotation();
  },
  methods: {
    startMessageRotation() {
      this.messageInterval = setInterval(() => {
        this.currentMessageIndex =
          (this.currentMessageIndex + 1) % this.messages.length;
      }, 3000); // 3초마다 메시지 변경
    },
  },
  beforeUnmount() {
    clearInterval(this.messageInterval); // 컴포넌트 파괴 시 setInterval 제거
  },
};
</script>

<style scoped>
.tooltip {
  position: absolute;
  display: inline-flex;
  flex-direction: column;
  align-items: center;
}

.tooltip-text {
  padding: 6px 8px;
  background-color: var(--gr30);
  border-radius: 8px;
  color: var(--gr100);
  text-align: center;
  font-size: 13px;
  font-weight: 400;
  line-height: 100%;
  letter-spacing: -0.13px;
}

.tooltip-tail {
  margin-top: -8.5px;
}
</style>
