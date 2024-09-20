<template>
  <div class="community-page">
    <header class="header">
      <h1>커뮤니티</h1>
      <div class="search-icon">
        <i class="fas fa-search"></i>
      </div>
    </header>

    <div class="category-tabs">
      <button
          v-for="category in categories"
          :key="category.name"
          :class="{ active: selectedCategory === category.name }"
          @click="selectCategory(category.name)">
        {{ category.name }}
      </button>
    </div>

    <!-- 서버로부터 데이터를 받아와 게시글 표시 -->
    <div class="post-list">
      <div v-for="post in filteredPosts" :key="post.id" class="post-item">
        <div class="post-header">
          <span class="category">{{ post.category }}</span>
          <span class="user">{{ post.user }}</span>
        </div>
        <h3 class="post-title">{{ post.title }}</h3>
        <p class="post-content">{{ post.content }}</p>
        <div class="post-footer">
          <span class="date">{{ post.date }}</span>
          <span class="comments">{{ post.comments }} 댓글</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      categories: [
        { name: '전체' },
        { name: '지출' },
        { name: '예적금' },
        { name: '펀드' },
        { name: '주식' },
        { name: '채권' },
      ],
      selectedCategory: '전체',
      posts: [], // 서버로부터 데이터를 받아와 저장할 배열
    };
  },
  computed: {
    filteredPosts() {
      if (this.selectedCategory === '전체') {
        return this.posts;
      }
      return this.posts.filter(post => post.category === this.selectedCategory);
    },
  },
  methods: {
    selectCategory(category) {
      this.selectedCategory = category;
    },
  },
  mounted() {
    axios.get('http://localhost:8080/')
        .then(response => {
          this.posts = response.data;
          console.log(response.data);
        })
        .catch(error => {
          console.error('서버로부터 데이터를 받아오는 중 오류 발생:', error);
        });
  },
};
</script>

<style scoped>
.community-page {
  padding: 20px;
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 24px;
  margin-bottom: 20px;
}
.category-tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}
.category-tabs button {
  padding: 10px;
  border: none;
  background-color: #f0f0f0;
  border-radius: 20px;
  cursor: pointer;
}
.category-tabs .active {
  background-color: #333;
  color: white;
}
.post-list .post-item {
  margin-bottom: 20px;
  border-bottom: 1px solid #e0e0e0;
  padding-bottom: 10px;
}
.post-header {
  display: flex;
  justify-content: space-between;
  font-size: 14px;
  color: #888;
}
.post-title {
  font-size: 18px;
  margin: 5px 0;
}
.post-content {
  font-size: 16px;
  color: #555;
}
.post-footer {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #aaa;
}
.bottom-nav {
  position: fixed;
  bottom: 0;
  width: 100%;
  display: flex;
  justify-content: space-around;
  padding: 10px 0;
  background-color: #f8f8f8;
}
.bottom-nav .active {
  font-weight: bold;
  color: #000;
}
</style>
