<template>
  <div class="post-detail-page">
    <header class="header">
      <h1 class="title">{{ post.title }}</h1>
      <div class="post-meta">
        <span class="category">{{ post.category }}</span> |
        <span class="user">{{ post.writerId }}</span> |
        <span class="date">{{ post.modifyDate ? formatDate(post.modifyDate) : formatDate(post.registerDate) }}</span>
      </div>
    </header>

    <div class="post-content">
      <p>{{ post.content }}</p>
    </div>

    <div class="post-likes">
      <span>❤️ 좋아요 {{ post.likes }}</span>
    </div>

    <router-link to="/community" class="back-link">← 목록으로 돌아가기</router-link>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';

export default {
  name: 'PostDetailP',
  setup() {
    const route = useRoute();
    const postId = route.params.id;

    const post = ref({
      title: '',
      content: '',
      category: '',
      writerId: '',
      registerDate: '',
      modifyDate: '',
      likes: 0,
      comments: []
    });

    const fetchPost = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/community/${postId}`);
        post.value = response.data;
      } catch (error) {
        console.error('게시글을 가져오는 중 오류 발생:', error);
      }
    };

    const formatDate = (dateArray) => {
      if (!dateArray || dateArray.length < 3) return '날짜 없음';
      const [year, month, day] = dateArray;
      return `${year}. ${String(month).padStart(2, '0')}. ${String(day).padStart(2, '0')}`;
    };

    onMounted(() => {
      fetchPost();
    });

    return {
      post,
      formatDate
    };
  },
};
</script>

<style scoped>
.post-detail-page {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
  font-family: Arial, sans-serif;
  color: #333;
  //background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.header {
  border-bottom: 1px solid #ddd;
  margin-bottom: 15px;
  padding-bottom: 10px;
}

.title {
  font-size: 24px;
  margin: 0 0 10px 0;
}

.post-meta {
  font-size: 12px;
  color: #666;
}

.post-content {
  font-size: 16px;
  line-height: 1.8;
  min-height: 20px;
  padding:10px 0;
  margin-bottom: 20px;
}

.post-likes {
  font-size: 14px;
  color: #666;
  margin-bottom: 20px;
}

.back-link {
  display: inline-block;
  color: #007BFF;
  text-decoration: none;
  margin-top: 20px;
  font-size: 14px;
}

.back-link:hover {
  text-decoration: underline;
}
</style>
