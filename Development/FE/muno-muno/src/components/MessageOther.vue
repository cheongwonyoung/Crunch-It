<template>
  <div class="message-wrapper">
    <div class="message-profile">
      <img class="message-other-logo" :src="avatar" alt="other" />
      <div class="message-text-label">{{ name }}</div>
    </div>

    <div class="message-content">
      <div class="message-row">
        <div class="message-other">
          <!-- 이미지가 있을 경우 이미지 표시 -->
          <div v-if="image">
            <img :src="image" alt="이미지" class="message-image" />
          </div>
          <!-- 텍스트 메시지 -->
          <div v-else class="message-text">{{ message }}</div>
        </div>
        <div class="message-time">{{ time }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MessageOther',
  props: {
    message: {
      type: String,
      default: '', // 메시지가 없을 수도 있으므로 기본값 설정
    },
    time: {
      type: String,
      required: true,
    },
    name: {
      type: String,
      default: '상대방',
    },
    avatar: {
      type: String,
      default: () => require('@/assets/profile.svg'),
    },
    image: {
      type: String,
      default: null, // 이미지가 없을 수도 있으므로 기본값 설정
    },
  },
};
</script>

<style scoped>
.message-wrapper {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 12px;
  position: relative;
  max-width: 100%;
}

.message-profile {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}

.message-other-logo {
  width: 32px;
  height: 32px;
  margin-right: 8px;
}

.message-text-label {
  color: var(--gr20);
  font-size: 16px;
  font-weight: 500;
  line-height: 100%;
}

.message-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  position: relative;
}

.message-row {
  display: flex;
  align-items: flex-end;
  flex-wrap: wrap;
}

.message-other {
  display: inline-block;
  border-radius: 2px 20px 20px 20px;
  background: var(--gr70);
  color: var(--gr100);
  box-shadow: 0px 0px 4px 0px rgba(0, 0, 0, 0.06);
  padding: 8px 14px;
  margin-right: 6px;
  max-width: 100%;
  word-wrap: break-word;
}

.message-text {
  color: var(--gr20);
  font-size: 16px;
  font-weight: 400;
  line-height: 150%;
}

.message-image {
  max-width: 100%;
  max-height: 200px;
  border-radius: 8px;
  margin-top: 5px;
}

.message-time {
  color: var(--gr50);
  font-size: 11px;
  font-weight: 400;
  line-height: 100%;
  align-self: flex-end;
  margin-bottom: 2px;
  white-space: nowrap;
}
</style>