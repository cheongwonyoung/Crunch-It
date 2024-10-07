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
// import SockJS from 'sockjs-client';
// import Stomp from 'webstomp-client';
// import apiClient from "@/axios";

export default {
  data() {
    return {
      stompClient: null,
      messages: [],
      messageContent: '',
      chatRooms: [],         // 채팅방 목록
      currentRoomId: null,   // 현재 참여 중인 채팅방 ID
      currentRoomName: '',   // 현재 참여 중인 채팅방 이름
    };
  },
  methods: {
    connect() {
      // const socket = new window.SockJS('http://192.168.50.108:8080/ws');
      const socket = new window.SockJS('http://localhost:8080/ws');
      console.log(socket);
      this.stompClient = window.Stomp.over(socket);
      this.stompClient.connect({}, this.onConnected, this.onError);
    },
    onConnected() {
      console.log("connected to websocket")
      console.log(this.stompClient.connected)
      this.fetchChatRooms();  // 채팅방 목록 로드
    },
    onError(error) {
      console.error('WebSocket connection error', error);
    },
    fetchChatRooms() {
      this.chatRooms = [
        { id: 1, name: '문어방' },
        { id: 2, name: '거지방' },
        { id: 3, name: '종토방' },
        { id: 4, name: '자유방' }
      ];
    },
    joinRoom(roomId) {
      if (this.stompClient && this.currentRoomId) {
        this.stompClient.unsubscribe('/topic/chat/' + this.currentRoomId);
      }

      const selectedRoom = this.chatRooms.find(room => room.id === roomId);

      this.currentRoomId = roomId;
      this.currentRoomName = selectedRoom ? selectedRoom.name : 'Unknown Room';
      this.messages = [];  // 메시지 초기화

      console.log("roomID = " + this.currentRoomId)

      this.stompClient.subscribe('/topic/chat/' + this.currentRoomId, this.onMessageReceived);
      console.log("subscribe !!! ")
      console.log(this.stompClient)
    },
    onMessageReceived(payload) {
      const message = JSON.parse(payload.body);
      this.messages.push(message);
    },
    sendMessage() {
      if (this.messageContent.trim() !== '' && this.currentRoomId) {
          const message = {
            sender: 'User',
            content: this.messageContent,
            roomId: this.currentRoomId,
          };

          // destination, headers, body 순서로 설정
          this.stompClient.send(`/app/chat/${this.currentRoomId}`, {}, JSON.stringify(message));
          console.log(this.stompClient)
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