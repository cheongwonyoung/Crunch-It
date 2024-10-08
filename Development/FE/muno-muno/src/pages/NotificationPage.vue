<template>
  <div class="container mx-auto p-4">
    <h1 class="text-xl font-semibold mb-4">알림</h1>
    <div v-for="notification in notifications" :key="notification.id" class="flex items-start mb-4 border-b pb-4">
      <div class="flex-shrink-0">
        <div class="w-10 h-10 bg-blue-200 rounded-full flex items-center justify-center">
          <span class="text-lg">💬</span>
        </div>
      </div>
      <div class="ml-4">
        <p class="font-semibold">{{ notification.title }}</p>
        <p class="text-sm text-gray-600">{{ notification.message }}</p>
        <p class="text-sm text-gray-500">{{ formatTime(notification.time) }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';

export default {
  name:'NotificationP',
  setup() {
    const notifications = ref([]);

    const fetchNotifications = async () => {
      try {
        const response = await axios.get('/notifications');
        notifications.value = response.data;
      } catch (error) {
        console.error('Failed to fetch notifications', error);
      }
    };

    const formatTime = (time) => {
      const date = new Date(time);
      return `${date.getFullYear()}.${date.getMonth() + 1}.${date.getDate()} ${date.getHours()}:${String(date.getMinutes()).padStart(2, '0')}`;
    };

    onMounted(() => {
      fetchNotifications();
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
