<template>
  <div class="message-input">
    <img class="icon-image" src="@/assets/picture.svg" alt="이미지 첨부" />

    <input
      type="text"
      v-model="message"
      placeholder="메시지를 입력하세요..."
      class="input-field"
      @keyup.enter="sendMessage"
    />

    <img
      class="icon-send"
      src="@/assets/send.svg"
      alt="전송"
      @click="sendMessage"
    />
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
        this.$emit('send', this.message);
        this.message = '';
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
  cursor: pointer;
}

.input-field {
  width: 278px;
  height: 40px;
  padding: 0 16px;
  border: none;
  border-radius: 40px;
  background: var(--gr80);
  font-size: 14px;
  outline: none;
  margin-right: 8px;
  margin: 0 10px 0 8px;
  box-sizing: border-box;
}

.icon-send {
  width: 24px;
  height: 24px;
  cursor: pointer;
}
</style>
