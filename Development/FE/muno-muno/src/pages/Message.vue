<template>
  <div class="message-page">
    <!-- 상단 헤더 -->
    <HeaderB title="거지방" @back="goBack" />

    <!-- 메시지 컨테이너 -->
    <div class="message-container">
      <!-- 사용자 메시지 -->
      <MessageUser message="오늘 점심 컵라면 먹음" time="오후 11:58" />

      <!-- 상대방 메시지 -->
      <MessageBot
        message="저는 오늘 집밥 먹었는데 님 부자임?"
        time="오후 11:58"
        name="이자왕 무너"
        avatar="https://via.placeholder.com/32"
      />

      <!-- 사용자 메시지 -->
      <MessageUser message="님 거지임?" time="오후 11:58" />
    </div>

    <!-- 메시지 입력창 컴포넌트 -->
    <MessageInput @send="handleSendMessage" />
  </div>
</template>

<script>
import HeaderB from '@/components/HeaderB.vue';
import MessageUser from '@/components/MessageUser.vue';
import MessageBot from '@/components/MessageOther.vue';
import MessageInput from '@/components/MessageInput.vue';

export default {
  name: 'MessageP',
  components: {
    HeaderB,
    MessageUser,
    MessageBot,
    MessageInput,
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    handleSendMessage(message) {
      console.log('Message sent:', message);
    },
    handleResize() {
      if (window.innerHeight < 700) {
        this.keyboardVisible = true; // 키보드가 나타났을 때
        this.$refs.messageContainer.style.paddingBottom = '300px'; // 키보드 높이만큼 패딩 추가
      } else {
        this.keyboardVisible = false; // 키보드가 사라졌을 때
        this.$refs.messageContainer.style.paddingBottom = '0px';
      }
    },
    adjustForKeyboard(isVisible) {
      if (isVisible) {
        this.keyboardVisible = true;
        window.scrollTo(0, document.body.scrollHeight); // 키보드가 열리면 자동으로 스크롤 다운
      } else {
        this.keyboardVisible = false;
      }
    },
  },
  mounted() {
    window.addEventListener('resize', this.handleResize);
  },
  beforeUnmount() {
    // 'beforeDestroy' 대신 'beforeUnmount' 사용
    window.removeEventListener('resize', this.handleResize);
  },
};
</script>

<style scoped>
.message-page {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.message-container {
  flex-grow: 1;
  padding: 10px 16px;
  padding-top: 84px;
  overflow-y: auto;
}

.message-input {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background-color: white;
}
</style>
