<template>
  <div class="flex items-start mb-2 notification-item-wrapper">
    <!-- 알림 유형에 따라 아이콘 변경 -->
    <div class="icon-container">
      <img
        :src="
          notification.type === '댓글'
            ? require('@/assets/c_notification.svg')
            : require('@/assets/like.svg')
        "
        alt="알림 아이콘"
        class="notification-icon"
      />
    </div>
    <!-- 알림 내용 -->
    <div class="a">
      <p class="b">
        <span class="nickname">{{ notification.nickname }}</span>
        님이 <span class="title">{{ notification.title }}</span> 글에
        {{ notification.type }}을 남겼습니다.
      </p>
      <p class="subMessage">{{ notification.subMessage }}</p>
      <p class="formattedTime">{{ formattedTime }}</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'NotificationItem',
  props: {
    notification: {
      type: Object,
      required: true,
    },
  },
  mounted() {
    console.log("NotificationItem mounted with notification:", this.notification);
  },
  computed: {
    // createdAt 배열 데이터를 2분 전, 3시간 전 등의 형식으로 변환
    formattedTime() {
      const timeArray = this.notification.createdAt;
      if (Array.isArray(timeArray)) {
        const createdDate = new Date(
          timeArray[0],
          timeArray[1] - 1,
          timeArray[2],
          timeArray[3],
          timeArray[4]
        );
        const now = new Date();
        const diffInMinutes = Math.floor((now - createdDate) / 60000);

        if (diffInMinutes < 60) {
          return `${diffInMinutes}분 전`;
        } else if (diffInMinutes < 1440) {
          return `${Math.floor(diffInMinutes / 60)}시간 전`;
        } else {
          return `${timeArray[0]}.${String(timeArray[1]).padStart(
            2,
            '0'
          )}.${String(timeArray[2]).padStart(2, '0')}`;
        }
      }
      return '';
    },
  },
};
</script>

<style scoped>
.notification-item-wrapper {
  display: flex;
  align-items: start;
}

.notification-item {
  border-bottom: 1px solid var(--gr70);
}

.b {
  color: var(--gr20);
  font-size: 14px;
  font-weight: 400;
  line-height: 150%;
  letter-spacing: -0.28px;
  margin: 22px 0 4px 0;
}

.nickname {
  font-weight: 600;
}

.title {
  font-weight: 600;
}

p.subMessage {
  color: var(--gr40);
  font-size: 14px;
  font-weight: 400;
  line-height: 150%;
  letter-spacing: -0.28px;
  margin: 4px 0;
}

p.formattedTime {
  color: var(--gr60);
  font-size: 12px;
  font-weight: 500;
  line-height: 130%;
  letter-spacing: -0.24px;
  margin: 4px 0 20px 0;
}

.notification-icon {
  display: block;
  margin: 22px 8px 0 0;
  width: 24px;
  height: 24px;
}
</style>
