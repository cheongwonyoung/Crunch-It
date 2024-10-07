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
      <div v-for="message in recvMessages" :key="message.id">
        <strong>[받음]{{ message.sender }}:</strong> {{ message.content }}
      </div>
      <div v-for="message in sentMessages" :key="message.id">
              <strong>[보냄]{{ message.sender }}:</strong> {{ message.content }}
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
      recvMessages: [],
      sentMessages: [],
      messageContent: '',
      chatRooms: [],         // 채팅방 목록
      currentRoomId: null,   // 현재 참여 중인 채팅방 ID
      currentRoomName: '',   // 현재 참여 중인 채팅방 이름
      currentSubScription: null
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
      console.log("STOMP Connected:", this.stompClient.connected);
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
        // 이전 구독 해제
      if (this.currentSubscription) {
          this.currentSubscription.unsubscribe();
          console.log("Unsubscribed from previous room");
      }

            const selectedRoom = this.chatRooms.find(room => room.id === roomId);

      this.currentRoomId = roomId;
      this.currentRoomName = selectedRoom ? selectedRoom.name : 'Unknown Room';
      this.recvMessages = [];  // 메시지 초기화

      // 새로운 구독
      this.currentSubscription = this.stompClient.subscribe(`/topic/chat/${this.currentRoomId}`, this.onMessageReceived);
      console.log("Subscribed to new room:", this.currentRoomId);
    },
    onMessageReceived(payload) {
      const message = JSON.parse(payload.body);
      this.recvMessages.push(message);

    // 수신된 메시지가 본인(sender가 'User')의 메시지인지 확인
  if (message.sender !== 'User') {
    this.messages.push(message);  // 본인의 메시지가 아닌 경우에만 추가
  }
    },
    sendMessage() {
      if (this.messageContent.trim() !== '' && this.currentRoomId) {
          const message = {
            sender: 'User',
            content: this.messageContent,
            roomId: this.currentRoomId,
          };
          try{
            
            // destination, headers, body 순서로 설정
              this.stompClient.send(`/topic/chat/${this.currentRoomId}`, {}, JSON.stringify(message));
              this.sentMessages.push(message);  // 보낸 메시지 배열에 추가
              console.log("Message sent:", message);
          }catch(errer){
            console.log("error 발생")
          }
    
      // STOMP 클라이언트 객체 상태 확인
      console.log("STOMP Client Status after send:", this.stompClient.subscriptions);
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