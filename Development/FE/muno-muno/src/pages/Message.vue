<template>
  <div class="message-page">
    <!-- 상단 헤더 -->
    <HeaderB :title="currentRoomName" @back="goBack" />

    <!-- 메시지 컨테이너 -->
    <div class="message-container" ref="messageContainer">
      <div v-for="message in messages" :key="message.id">
        <!-- 사용자 메시지 -->
        <MessageUser
          v-if="message.sender === 'User'"
          :message="message.content"
          :time="message.time"
        />
        <!-- 상대방 메시지 -->
        <MessageBot
          v-else
          :message="message.content"
          :time="message.time"
          :name="message.sender"
          :avatar="require('@/assets/profile.svg')"
        />
      </div>
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
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

export default {
  name: 'MessageP',
  components: {
    HeaderB,
    MessageUser,
    MessageBot,
    MessageInput,
  },
  data() {
    return {
      stompClient: null,
      // 임의로 추가한 메시지 데이터
      messages: [
        {
          id: 1,
          sender: 'User',
          content: '안녕하세요!',
          time: '오후 10:00',
        },
        {
          id: 2,
          sender: 'Bot',
          content: '안녕하세요! 반갑습니다.',
          time: '오후 10:01',
        },
      ],
      currentRoomId: 2,
      currentRoomName: '거지방', // 백엔드에서 값을 받으면 여기에 표시되도록 함
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    handleSendMessage(messageContent) {
      if (messageContent.trim() !== '') {
        const message = {
          sender: 'User',
          content: messageContent,
          time: new Date().toLocaleTimeString('ko-KR', {
            hour: '2-digit',
            minute: '2-digit',
          }), // 한글 형식 시간
          roomId: this.currentRoomId,
        };
        this.stompClient.send(
          `/topic/chat/${this.currentRoomId}`,
          {},
          JSON.stringify(message)
        );
        this.messages.push(message);
        this.scrollToBottom();
      }
    },
    connect() {
      const socket = new SockJS('http://localhost:8080/ws');
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect({}, this.onConnected, this.onError);
    },
    onConnected() {
      // 백엔드에서 방 이름 값을 받아서 currentRoomName에 설정
      this.stompClient.subscribe(
        `/topic/chat/${this.currentRoomId}`,
        this.onMessageReceived
      );
      console.log('Connected to room:', this.currentRoomName);
    },
    onError(error) {
      console.error('WebSocket connection error:', error);
    },
    onMessageReceived(payload) {
      const message = JSON.parse(payload.body);
      this.messages.push({
        ...message,
        time: new Date().toLocaleTimeString('ko-KR', {
          hour: '2-digit',
          minute: '2-digit',
        }),
      });
      this.scrollToBottom();
    },
    scrollToBottom() {
      this.$nextTick(() => {
        const container = this.$refs.messageContainer;
        container.scrollTop = container.scrollHeight;
      });
    },
  },
  mounted() {
    this.connect();
    window.addEventListener('resize', this.handleResize);
  },
  beforeUnmount() {
    window.removeEventListener('resize', this.handleResize);
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
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
  position: fixed;
  width: 339px;
  height: 614px;
  margin-top: 64px;
  padding-right: 18px;
  padding-left: 18px;
  overflow-y: auto;
  word-wrap: break-word;
}

.message-container div {
  max-width: 100%;
}

.message-input {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background-color: var(--gr100);
}
</style>
