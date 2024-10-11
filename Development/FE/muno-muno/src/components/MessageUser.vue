<template>
  <div class="message-wrapper">
    <div class="message-time-user">{{ time }}</div>
    <div class="message message-user">
      <!-- 이미지가 있을 경우 이미지 표시 -->
      <div v-if="image">
        <img :src="image" alt="이미지" class="message-image" />
      </div>
      <!-- 텍스트 메시지 -->
      <div v-if="message" class="message-text">{{ message }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MessageUser',
  props: {
    message: {
      type: String,
      default: '', // 메시지가 없는 경우도 허용
    },
    time: {
      type: String,
      required: true,
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
  justify-content: flex-end;
  align-items: flex-end;
  margin-bottom: 12px;
  position: relative;
  line-height: 150%;
}

.message-user {
  display: inline-block;
  border-radius: 20px 2px 20px 20px;
  background: var(--p10);
  color: var(--gr100);
  box-shadow: 0px 0px 4px 0px rgba(0, 0, 0, 0.06);
  padding: 8px 14px;
  margin-left: 6px;
  max-width: 200px;
}

.message-text {
  /* 텍스트 메시지 스타일 */
}

.message-image {
  max-width: 100%;
  border-radius: 8px;
  margin-top: 5px;
}
  
.message-time-user {
  color: var(--gr50);
  font-size: 11px;
  font-weight: 400;
  line-height: 100%;
  align-self: flex-end;
  margin-bottom: 2px;
}
</style>