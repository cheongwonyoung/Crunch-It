<template>
  <div class="container mx-auto p-4">
    <h1 class="text-xl font-semibold mb-4">알림</h1>
    <div v-for="notification in notifications" :key="notification.notificationId" class="flex items-start mb-4 border-b pb-4">
      <div class="flex-shrink-0">
        <div class="w-10 h-10 bg-blue-200 rounded-full flex items-center justify-center">
          <span class="text-lg">💬</span>
        </div>
      </div>
      <div class="ml-4">
        <p class="font-semibold">{{ notification.title }}</p>
        <p class="text-sm text-gray-600">{{ notification.message }}</p>
        <p class="text-sm text-gray-500">{{ formatTime(notification.createdAt) }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
// import axios from 'axios';
import apiClient from "@/axios";

export default {
  name: 'NotificationP',
  setup() {
    const notifications = ref([]);

    // 기존 알림 데이터를 가져오는 함수 (Authorization 헤더 추가)
    const fetchNotifications = async () => {
      try {
        const token = localStorage.getItem('JwtToken'); // 로컬스토리지에서 JWT 토큰 가져오기
        if (!token) {
          console.error('토큰이 없습니다. 로그인해주세요.');
          return;
        }

        const response = await apiClient.get('/notifications', {
          headers: {
            Authorization: `${token}` // JWT 토큰을 Authorization 헤더에 포함
          }
        });
        notifications.value = response.data;
      } catch (error) {
        console.error('알림 데이터를 불러오는 데 실패했습니다.', error);
      }
    };

    // 실시간 알림을 구독하는 함수
    const subscribeToSSE = () => {
      const token = localStorage.getItem('JwtToken'); // JWT 토큰을 로컬스토리지에서 가져옴
      if (!token) {
        console.error('토큰이 없습니다. 로그인해주세요.');
        return;
      }

      const payload = JSON.parse(atob(token.split('.')[1])); // JWT 디코딩
      console.log('Decoded Token Payload:', payload);

      // console.log('SSE 구독 요청 시작...',token);

      const eventSource = new EventSource(`http://localhost:8080/notifications/subscribe?token=${token}`);

      eventSource.onmessage = function(event) {
        console.log('New Notification:', event.data);
        // 새로운 알림이 오면 notifications 배열에 추가
        notifications.value.push(JSON.parse(event.data));
      };

      eventSource.onerror = function(error) {
        if (eventSource.readyState === EventSource.CLOSED) {
          console.log('SSE connection closed by server.');
        } else {
          console.error('SSE connection error', error);
          console.log('EventSource readyState:', eventSource.readyState);
          console.log('EventSource URL:', eventSource.url);
        }
        eventSource.close();
      };

    };

    const formatTime = (time) => {
      const date = new Date(time);
      return `${date.getFullYear()}.${date.getMonth() + 1}.${date.getDate()} ${date.getHours()}:${String(date.getMinutes()).padStart(2, '0')}`;
    };

    onMounted(() => {
      fetchNotifications(); // 처음에 알림 데이터 가져오기
      subscribeToSSE(); // SSE 구독 시작
    });

    return {
      notifications,
      formatTime,
    };
  },
};
</script>


<style scoped>
.container {
  max-width: 600px;
}
</style>
