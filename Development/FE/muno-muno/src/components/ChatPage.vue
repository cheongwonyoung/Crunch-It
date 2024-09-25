<template>
  <div>
    <h2>Chat Application</h2>
    
    <!-- 채팅방 목록 -->
    <div>
      <h3>Available Chat Rooms</h3>
      <ul>
        <li v-for="room in chatRooms" :key="room.id">
          <button @click="joinRoom(room.id)">{{ room.name }}</button>
        </li>
      </ul>

      <!-- 채팅방 개설 -->
      <input v-model="newRoomName" placeholder="New room name..." />
      <button @click="createRoom">Create Room</button>
    </div>

    <!-- 채팅방 참여 후 채팅 기능 -->
    <div v-if="currentRoomId">
      <h3>Room: {{ currentRoomName }}</h3>
      <div v-for="message in messages" :key="message.id">
        <strong>{{ message.sender }}:</strong> {{ message.content }}
      </div>

      <input v-model="messageContent" @keyup.enter="sendMessage" placeholder="Type your message..." />
      <button @click="sendMessage">Send</button>
    </div>
  </div>
</template>

<script>
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';
import axios from 'axios';

export default {
  data() {
    return {
      stompClient: null,
      messages: [],
      messageContent: '',
      chatRooms: [],         // 채팅방 목록
      currentRoomId: null,   // 현재 참여 중인 채팅방 ID
      currentRoomName: '',   // 현재 참여 중인 채팅방 이름
      newRoomName: '',       // 새 채팅방 이름
    };
  },
  methods: {
    connect() {
      const socket = new SockJS('/ws');
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect({}, this.onConnected, this.onError);
    },
    onConnected() {
      this.fetchChatRooms();  // 채팅방 목록 로드
    },
    onError(error) {
      console.error('WebSocket connection error', error);
    },
    fetchChatRooms() {
      // 서버에서 채팅방 목록을 가져옵니다.
      axios.get('/api/chatrooms')
        .then(response => {
          this.chatRooms = response.data;
        })
        .catch(error => {
          console.error('Error fetching chat rooms:', error);
        });
    },
    createRoom() {
      // 새 채팅방 생성
      if (this.newRoomName.trim() !== '') {
        axios.post('/api/chatrooms', { name: this.newRoomName })
          .then(response => {
            this.chatRooms.push(response.data);
            this.newRoomName = ''; // 입력 필드 초기화
          })
          .catch(error => {
            console.error('Error creating chat room:', error);
          });
      }
    },
    joinRoom(roomId) {
      // 기존 구독 취소 후 새로운 방에 참여
      if (this.stompClient && this.currentRoomId) {
        this.stompClient.unsubscribe('/topic/chat/' + this.currentRoomId);
      }

      // 새로운 채팅방에 참여
      const selectedRoom = this.chatRooms.find(room => room.id === roomId);
      this.currentRoomId = roomId;
      this.currentRoomName = selectedRoom ? selectedRoom.name : 'Unknown Room';
      this.messages = [];  // 메시지 초기화

      this.stompClient.subscribe('/topic/chat/' + this.currentRoomId, this.onMessageReceived);
    },
    onMessageReceived(payload) {
      const message = JSON.parse(payload.body);
      this.messages.push(message);
    },
    sendMessage() {
      if (this.messageContent.trim() !== '' && this.currentRoomId) {
        const message = {
          sender: 'User', // 사용자명
          content: this.messageContent,
          roomId: this.currentRoomId,
        };
        this.stompClient.send('/app/chat/' + this.currentRoomId, JSON.stringify(message), {});
        this.messageContent = ''; // 메시지 전송 후 초기화
      }
    },
  },
  mounted() {
    this.connect();  // 컴포넌트가 로드될 때 WebSocket 연결
  },
};
</script>

<style scoped>
/* 채팅 스타일 정의 */
div {
  margin: 20px;
}

input {
  padding: 10px;
  width: 80%;
  margin-right: 10px;
}

button {
  padding: 10px 15px;
}

ul {
  list-style: none;
  padding: 0;
}

li {
  margin-bottom: 10px;
}
</style>