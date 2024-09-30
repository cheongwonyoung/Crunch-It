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
      <span>❤️ {{ post.likes }}</span>
    </div>

    <CommentList :comments="comments" />

    <router-link to="/community" class="back-link">목록</router-link>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRoute } from 'vue-router';
import CommentList from "@/components/CommentList.vue";

export default {
  name: 'PostDetailP',
  components: { CommentList },
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

    const comments = ref([]);

    const fetchPostAndComments = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/community/${postId}`);
        post.value = response.data;

        const commentResponse = await axios.get(`http://localhost:8080/community/${postId}/comments`);
        comments.value = commentResponse.data;
      } catch (error) {
        console.error('Error fetching post and comments:', error);
      }
    };

    const formatDate = (dateArray) => {
      if (!dateArray || dateArray.length < 3) return 'No date';
      const [year, month, day] = dateArray;
      return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
    };

    onMounted(() => {
      fetchPostAndComments();
    });

    return {
      post,
      comments,
      formatDate
    };
  },
};
</script>

<style scoped>
.post-detail-page {
  padding: 16px;
  max-width: 720px;
  margin: 0 auto;
  font-family: 'Helvetica Neue', Arial, sans-serif;
  color: #444;
}

.header {
  border-bottom: 2px solid #eee;
  margin-bottom: 20px;
}

.title {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 5px;
}

.post-meta {
  font-size: 14px;
  color: #999;
  margin-bottom: 20px;
}

.post-content {
  font-size: 16px;
  line-height: 1.6;
  color: #333;
  margin-bottom: 20px;
}

.post-likes {
  font-size: 16px;
  color: #555;
  margin-bottom: 20px;
}

.back-link {
  display: inline-block;
  color: #007BFF;
  font-size: 14px;
  text-decoration: none;
  margin-top: 20px;
}

.back-link:hover {
  text-decoration: underline;
}
</style>
