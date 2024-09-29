<template>
  <div class="post-detail-page">
    <header class="header">
      <h1>{{ post.title }}</h1>
      <div class="post-meta">
        <span class="category">카테고리: {{ post.category }}</span>
        <span class="user">작성자: {{ post.writerId }}</span>
        <span class="date">작성일: {{ formatDate(post.registerDate) }}</span>
        <span class="modified-date" v-if="post.modifyDate && post.modifyDate.length > 0">수정일: {{ formatDate(post.modifyDate) }}</span>
      </div>
    </header>

    <div class="post-content">
      <p>{{ post.content }}</p>
    </div>

    <div class="post-likes">
      <span>좋아요: {{ post.likes }}</span>
    </div>

    <router-link to="/community" class="back-link">← 목록</router-link>
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
      if (!dateArray || dateArray.length < 6) return '날짜 없음'; // 날짜 배열이 올바른지 확인
      const [year, month, day, hour] = dateArray;
      return `${year}년 ${month}월 ${day}일 ${hour}시`;
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
}

.header {
  margin-bottom: 20px;
}

.post-meta {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  font-size: 14px;
  color: #888;
  margin-bottom: 10px;
}

.post-content {
  font-size: 16px;
  margin-bottom: 20px;
}

.post-likes {
  font-size: 14px;
  color: #444;
  margin-bottom: 20px;
}

.back-link {
  display: block;
  margin-top: 20px;
  color: #007BFF;
  text-decoration: none;
}

.back-link:hover {
  text-decoration: underline;
}

.modified-date {
  color: #444;
}
</style>
