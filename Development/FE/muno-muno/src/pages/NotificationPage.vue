<template>
  <div class="search">
    <HeaderB title="알림" @back="goBack" />
  </div>
  <div class="notification-list">
    <NotificationItem
      v-for="notification in notifications"
      :key="notification.notificationId"
      :notification="notification"
    />
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import HeaderB from '@/components/HeaderB.vue';
import NotificationItem from '@/components/NotificationItem.vue';
import apiClient from '@/axios';
import { useRouter } from 'vue-router';

export default {
  name: 'NotificationP',
  components: {
    HeaderB,
    NotificationItem,
  },
  setup() {
    const notifications = ref([]);
    const router = useRouter();

    const goBack = () => {
      router.push('/community');
    };
    onMounted(() => {
      console.log('Component mounted');
      fetchNotifications();
      subscribeToSSE();
    });

    // 기존 알림 데이터를 가져오는 함수 (Authorization 헤더 추가)
    const fetchNotifications = async () => {
      try {
        const token = localStorage.getItem('JwtToken'); // 로컬스토리지에서 JWT 토큰 가져오기
        console.log(token);
        if (!token) {
          console.error('토큰이 없습니다. 로그인해주세요.');
          return;
        }

        const response = await apiClient.get('/notifications', {
          headers: {
            Authorization: `Bearer ${token}`, // JWT 토큰을 Authorization 헤더에 포함
          },
        });
        notifications.value = response.data;
        //console.log(response.data);
      } catch (error) {
        console.error('알림 데이터를 불러오는 데 실패했습니다.', error);
      }
    };

    const subscribeToSSE = () => {
      const token = localStorage.getItem('JwtToken');
      if (!token) {
        console.error('토큰이 없습니다. 로그인해주세요.');
        return;
      }
      //실시간 알림 구독
      const eventSource = new EventSource(
        `http://ec2-3-38-135-108.ap-northeast-2.compute.amazonaws.com:8080/notifications/subscribe?token=${token}`
      );

      eventSource.onmessage = function (event) {
        // const newNotification = JSON.parse(event.data);
        //console.log("new notification ",event.data);

        notifications.value.push(JSON.parse(event.data));
      };

      eventSource.onerror = function (error) {
        console.error('SSE 연결 오류:', error);
        eventSource.close();
      };
    };

    // createdAt 배열 데이터를 yyyy.MM.dd HH:mm 형식으로 변환하는 함수
    // const formatTime = (timeArray) => {
    //   if (Array.isArray(timeArray)) {
    //     const [year, month, day, hour, minute] = timeArray; // 배열에서 값 추출
    //     return `${year}.${String(month).padStart(2, '0')}.${String(day).padStart(2, '0')} ${String(hour).padStart(2, '0')}:${String(minute).padStart(2, '0')}`;
    //   }
    //   return ''; // 배열이 아닌 경우 빈 문자열 반환
    // };

    return {
      notifications,
      goBack,
    };
  },
};
</script>

<style scoped>
.notification-list {
  position: absolute;
  top: 108px;
  left: 0;
  width: 375px;
  height: 704px;
  max-width: 600px;
  padding: 0 20px;
  box-sizing: border-box;
  overflow-y: auto;
}
.notification-item-wrapper {
  border-bottom: 0.5px solid var(--gr70);
}
</style>
