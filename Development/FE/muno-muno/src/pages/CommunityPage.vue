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
          @click="selectCategory(category.name)"
      >
        {{ category.name }}
      </button>
    </div>

    <!-- 서버로부터 데이터를 받아와 게시글 표시 -->
    <div class="post-list">
      <div
          v-for="post in filteredPosts"
          :key="post.boardId"
          :v-if="post && post.boardId"
          class="post-item"
          @click="goToDetail(post.boardId)"
      >
        <div class="post-header">
          <span class="category">{{ post.category }}</span>
          <span class="user">{{ post.writerId }}</span>
        </div>
        <h3 class="post-title">{{ post.title }}</h3>
        <p class="post-content">{{ post.content }}</p>
        <div class="post-footer">
          <span class="date">{{ post.date }}</span>
          <span class="comments">{{ post.comments }} 댓글</span>
        </div>
      </div>
    </div>

    <!-- 하단 + 버튼 -->
    <button class="floating-button" @click="goToWritePage">+</button>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

export default {
  name: 'CommunityPage',
  setup() {
    const categories = ref([
      { name: '전체' },
      { name: '지출' },
      { name: '예적금' },
      { name: '펀드' },
      { name: '주식' },
      { name: '채권' }
    ]);

    const selectedCategory = ref('전체');
    const posts = ref([]);
    const router = useRouter();

    const fetchPosts = async () => {
      try {
        const response = await axios.get('http://localhost:8080/community');
        posts.value = response.data;
      } catch (error) {
        console.error('서버로부터 데이터를 받아오는 중 오류 발생:', error);
      }
    };

    const filteredPosts = computed(() => {
      if (selectedCategory.value === '전체') {
        return posts.value;
      }
      return posts.value.filter((post) => post.category === selectedCategory.value);
    });

    const selectCategory = (category) => {
      selectedCategory.value = category;
    };

    // 클릭 시 상세 페이지로 이동
    const goToDetail = (board_id) => {
      router.push({ name: 'PostDetail', params: { id: board_id }}); // 라우트 이동
    };

    // 글쓰기 페이지로 이동
    const goToWritePage = () => {
      router.push({ name: 'WritePost' }); // WritePostPage.vue로 이동
    };

    onMounted(() => {
      fetchPosts();
    });

    return {
      categories,
      selectedCategory,
      filteredPosts,
      selectCategory,
      goToDetail,
      goToWritePage
    };
  }
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
  cursor: pointer; /* 클릭할 수 있음을 나타내는 포인터 커서 */
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

.floating-button {
  position: fixed;
  bottom: 100px; /* TabBar 위에 위치하도록 하단 위치 조정 */
  right: 20px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 50%;
  width: 60px;
  height: 60px;
  font-size: 36px;
  line-height: 60px;
  text-align: center;
  cursor: pointer;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  z-index: 1100; /* TabBar보다 높은 z-index 설정 */
}

.floating-button:hover {
  background-color: #0056b3;
}
</style>
