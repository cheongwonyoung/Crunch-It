<template>
  <div class="message-page">
    <!-- 상단 헤더 -->
    <HeaderB :title="currentRoomName" @back="goBack" />

    <!-- 메시지 컨테이너 -->
    <div class="message-container" ref="messageContainer">
      <div v-for="message in messages" :key="message.id">
        <!-- 사용자 메시지 -->
        <MessageUser
          v-if="message.sender === nickname"
          :message="message.content"
          :time="message.time"
          :image="message.image"
        />
        <!-- 상대방 메시지 -->
        <MessageBot
          v-else
          :message="message.content"
          :time="message.time"
          :name="message.sender"
          :avatar="require('@/assets/profile.svg')"
          :image="message.image"
        />
      </div>
    </div>

    <!-- 메시지 입력창 컴포넌트 -->
    <MessageInput @send="handleSendMessage" @sendImage="handleSendImage" />
  </div>
</template>

<script>
import HeaderB from '@/components/HeaderB.vue';
import MessageUser from '@/components/MessageUser.vue';
import MessageBot from '@/components/MessageOther.vue';
import MessageInput from '@/components/MessageInput.vue';
import { useUserStore } from '@/stores/userStore';
import { mapState } from 'pinia';

function decodeJwt(token) {
  if (!token) return null;
  const base64Url = token.split('.')[1];
  const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  const jsonPayload = decodeURIComponent(
    atob(base64)
      .split('')
      .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
      .join('')
  );
  return JSON.parse(jsonPayload);
}

export default {
  name: 'MessageP',
  components: {
    HeaderB,
    MessageUser,
    MessageBot,
    MessageInput,
  },
  computed: {
    ...mapState(useUserStore, ['userInfo']),
  },
  data() {
    return {
      stompClient: null,
      messages: [],
      currentRoomId: 2,
      currentRoomName: '거지방',
      user: null,
      nickname: '',
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    handleSendMessage(messageContent) {
      if (messageContent.trim() !== '') {
        const message = {
          sender: this.nickname,
          content: messageContent,
          time: new Date().toLocaleTimeString('ko-KR', {
            hour: '2-digit',
            minute: '2-digit',
          }),
          roomId: this.currentRoomId,
          image: null, // 압축된 Base64 인코딩 이미지
          profile_image: null, 
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
    handleSendImage(file) {
      const MAX_WIDTH = 800; // 최대 너비
      const MAX_HEIGHT = 800; // 최대 높이
      const QUALITY = 0.7; // JPEG 압축 품질 (0에서 1 사이)

      const reader = new FileReader();
      reader.onload = (event) => {
        const img = new Image();
        img.src = event.target.result;

        img.onload = () => {
          const canvas = document.createElement("canvas");
          let width = img.width;
          let height = img.height;

          // 너비와 높이 조정
          if (width > MAX_WIDTH || height > MAX_HEIGHT) {
            if (width > height) {
              height = Math.round((height * MAX_WIDTH) / width);
              width = MAX_WIDTH;
            } else {
              width = Math.round((width * MAX_HEIGHT) / height);
              height = MAX_HEIGHT;
            }
          }

          // Canvas에 이미지 그리기 및 압축
          canvas.width = width;
          canvas.height = height;
          const ctx = canvas.getContext("2d");
          ctx.drawImage(img, 0, 0, width, height);

          // Base64 인코딩된 이미지 생성
          const base64Image = canvas.toDataURL("image/jpeg", QUALITY);

          // WebSocket을 통해 이미지 전송
          const message = {
            sender: this.nickname,
            content: '', // 이미지 전송 메시지의 경우 텍스트 비우기
            time: new Date().toLocaleTimeString('ko-KR', {
              hour: '2-digit',
              minute: '2-digit',
            }),
            roomId: this.currentRoomId,
            image: base64Image, // 압축된 Base64 인코딩 이미지
          };

          this.stompClient.send(
            `/topic/chat/${this.currentRoomId}`,
            {},
            JSON.stringify(message)
          );
          this.messages.push(message);
          this.scrollToBottom();
        };
      };
      reader.readAsDataURL(file);
    },
    connect() {
      const socket = new window.SockJS('http://localhost:8080/ws');
      this.stompClient = window.Stomp.over(socket);
      this.stompClient.connect({}, this.onConnected, this.onError);
    },
    onConnected() {
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
      if (message.sender === this.nickname) return;
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
    const userStore = useUserStore();
    const token = localStorage.getItem('JwtToken');
    this.user = decodeJwt(token);

    console.log("!!! "+ userStore.userInfo.nickname)
    this.connect();
  },
  beforeUnmount() {
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