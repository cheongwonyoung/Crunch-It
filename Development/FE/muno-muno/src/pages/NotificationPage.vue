<template>
  <div class="search">
    <HeaderB title="알림" @back="onBack" />
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
import { useRouter } from 'vue-router';
import HeaderB from '@/components/HeaderB.vue';
import NotificationItem from '@/components/NotificationItem.vue';

export default {
  name: 'NotificationP',
  components: {
    HeaderB,
    NotificationItem,
  },
  setup() {
    const router = useRouter();

    const notifications = ref([
      {
        notificationId: 1,
        nickname: '이득분 무너',
        title: '요즘은 어떤 주식이 좋은가요?',
        subMessage: '안하는게 제일 좋네요...',
        createdAt: [2024, 10, 11, 9, 41],
        type: '댓글',
      },
      {
        notificationId: 2,
        nickname: '이득분 무너',
        title: '요즘은 어떤 주식이 좋은가요?',
        subMessage: '10월달에는 좋았군요... 요즘은 경기가 안좋아서...',
        createdAt: [2024, 10, 11, 9, 38],
        type: '좋아요',
      },
      {
        notificationId: 3,
        nickname: '이득분 무너',
        title: '요즘은 어떤 주식이 좋은가요?',
        subMessage: '어떤 걸 사도 지금이 이득입니다 ㅋㅋㅋㅋ',
        createdAt: [2024, 10, 3, 12, 0],
        type: '댓글',
      },
    ]);

    onMounted(() => {
      subscribeToSSE();
    });

    const subscribeToSSE = () => {
      const token = localStorage.getItem('JwtToken');
      if (!token) {
        console.error('토큰이 없습니다. 로그인해주세요.');
        return;
      }

      const eventSource = new EventSource(
        `http://localhost:8080/notifications/subscribe?token=${token}`
      );

      eventSource.onmessage = function (event) {
        const newNotification = JSON.parse(event.data);
        notifications.value.push({
          notificationId: newNotification.id,
          nickname: newNotification.nickname,
          title: newNotification.title,
          subMessage: newNotification.subMessage,
          createdAt: newNotification.createdAt,
          type: newNotification.type,
        });
      };

      eventSource.onerror = function () {
        eventSource.close();
      };
    };

    // 뒤로 가기 함수
    const onBack = () => {
      router.go(-1);
    };

    return {
      notifications,
      onBack,
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
