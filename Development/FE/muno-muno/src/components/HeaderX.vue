<template>
  <div class="header">
    <div class="header-text">{{ title }}</div>
    <div class="header-icons">
      <img
        v-for="icon in resolvedIcons"
        :key="icon.alt"
        :src="icon.src"
        :alt="icon.alt"
        class="icon"
        @click="icon.onClick"
      />
    </div>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      required: true,
      default: '제목',
    },
    icons: {
      type: Array,
      default: () => [],
    },
  },
  methods: {
    onClose() {
      this.$emit('close');
    },
  },
  computed: {
    resolvedIcons() {
      // 아이콘이 없으면 기본 아이콘으로 설정
      if (this.icons.length === 0) {
        return [
          {
            src: require('@/assets/x.svg'),
            alt: 'Default Icon',
            onClick: () => {
              this.onClose();
            },
          },
        ];
      }
      return this.icons;
    },
  },
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 25px 0 22px;
  height: 65px;
  background-color: var(--gr100);
  position: fixed;
  top: 0px;
  left: 0;
  width: 100%;
  z-index: 1000;
  box-sizing: border-box;
  font-size: 22px;
}

.header-text {
  font-size: 22px;
  font-weight: 600;
  line-height: 100%;
}

.header-icons {
  display: flex;
  gap: 10px;
}

.icon {
  width: 24px;
  height: 24px;
  cursor: pointer;
}
</style>
