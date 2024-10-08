<template>
  <div class="message-input">
    <!-- 이미지 첨부 아이콘 -->
    <img class="picture" src="@/assets/picture.svg" alt="이미지 첨부" />

    <!-- 메시지 입력창 -->
    <input
      type="text"
      v-model="message"
      placeholder="메시지를 입력하세요..."
      class="input-field"
    />

    <!-- 메시지 전송 아이콘 -->
    <img class="send" src="@/assets/send.svg" alt="전송" @click="sendMessage" />
  </div>
</template>

<script>
export default {
  data() {
    return {
      message: '',
    };
  },
  methods: {
    sendMessage() {
      if (this.message.trim()) {
        // 메시지 전송 로직 추가
        console.log('Message Sent:', this.message);
        this.message = ''; // 메시지 전송 후 입력창 초기화
      }
    },
  },
};
</script>

<style scoped>
.message-input {
  border-radius: 12px 12px 0px 0px;
  border-top: 0.5px solid var(--gr60);
  background: var(--gr100);
  box-shadow: 0px 0px 20px 0px rgba(62, 68, 78, 0.1);
  display: flex;
  align-items: center;
  padding: 8px 20px 42px 13px;
  width: 375px;
  box-sizing: border-box;
}

.icon-image {
  width: 24px;
  height: 24px;
  margin-right: 8px;
  cursor: pointer;
}

.input-field {
  width: 276px;
  height: 40px;
  padding: 0 16px;
  border: none;
  border-radius: 40px;
  background: var(--gr80);
  font-size: 14px;
  outline: none;
  margin-right: 8px;
  margin: 0 10px 0 8px;
}

.icon-send {
  width: 24px;
  height: 24px;
  cursor: pointer;
  margin-left: 20px;
}
</style>
