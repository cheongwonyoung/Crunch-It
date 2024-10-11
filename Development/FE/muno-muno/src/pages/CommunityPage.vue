<template>
  <div class="community-page">
    <header class="header">
<!--      <Category category="커뮤니티"></Category>-->
      <h1 class="community-title">커뮤니티</h1>


      <!-- 알림 아이콘 추가 -->
      <div class="icons">
        <button @click="goToNotifications" class="notification-icon">
          <img src="@/assets/notification.svg" alt="Notification Icon" class="icon-svg" />
        </button>
<!--        <div class="search-icon">-->
<!--          <i class="fas fa-search"></i>-->
<!--        </div>-->
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
          <span class="date">{{ post.modifyDate ? formatDate(post.modifyDate) : formatDate(post.registerDate) }}</span>
        </div>
      </div>
    </div>

    <!-- 하단 + 버튼 -->
    <button class="floating-button" @click="goToWritePage">
      <img src="@/assets/plus.svg" alt="Plus Icon" />
    </button>

  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import apiClient from '../axios';
import { useRouter } from 'vue-router';
// import Category from '../components/Category.vue'

export default {
  name: 'CommunityPage',
  // components: {Category},
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
        const response = await apiClient.get('http://localhost:8080/community');
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
    const goToDetail = (boardId) => {
      router.push({ name: 'PostDetail', params: { id: boardId }}); // 라우트 이동
    };

    // 글쓰기 페이지로 이동
    const goToWritePage = () => {
      router.push({ name: 'WritePost' }); // WritePostPage.vue로 이동
    };

    // 알림 페이지로 이동
    const goToNotifications = () => {
      router.push({ name: 'Notification' }); // NotificationPage로 이동
    };

    // 날짜 포맷 함수
    const formatDate = (dateArray) => {
      if (!dateArray || dateArray.length < 3) return '날짜 없음'; // 날짜 배열이 올바른지 확인
      const [year, month, day] = dateArray;
      return `${year}. ${String(month).padStart(2, '0')}. ${String(day).padStart(2, '0')}`; // "2024. 10. 03" 형식으로 변환
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
      goToWritePage,
      goToNotifications, // 알림 페이지로 이동 함수 반환
      formatDate
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

/* 커뮤니티 텍스트 스타일 */
.community-title {
  font-family: 'Pretendard', sans-serif;
  font-size: 22px;
  font-weight: 600;
  line-height: 22px;
  text-align: left;
  width: 77px;
  height: 22px;
  margin: 0;
  opacity: 1; /* 요청에 따라 opacity 수정 */
}

.icons {
  display: flex;
  gap: 10px;
  align-items: center;
}

.notification-icon {
  border: none;
  background: none;
  cursor: pointer;
}

.icon-svg {
  width: 24px;
  height: 24px;
}

.category-tabs {
  display: flex;
  justify-content: flex-start;
  //margin-bottom: 20px;
  border-bottom: 2px solid #e0e0e0;
  overflow-x: auto; /* 넘칠 경우 가로 스크롤 활성화 */
  white-space: nowrap; /* 버튼이 줄바꿈 없이 한 줄로 유지되도록 설정 */

  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none;  /* Internet Explorer and Edge */
}

.category-tabs::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera */
}

.category-tabs button {
  padding: 10px 15px;
  border: none;
  background: none;
  color: #555;
  cursor: pointer;
  border-bottom: 2px solid transparent;
  transition: color 0.3s, border-bottom 0.3s;
  font-family: 'Pretendard', sans-serif;
  font-size: 16px;
  font-weight: 600;
  line-height: 24px;
  text-align: center;
}

.category-tabs button:hover {
  color: #333;
}

.category-tabs .active {
  color: #000;
  font-weight: bold;
  border-bottom: 2px solid #333; /* Highlight the active category */
  background-color: #f0f0f0;
  //padding: 10px 15px;
}

.post-list {
  margin-top: 10px;
}

.post-list .post-item {
  //margin-bottom: 20px;
  //border: 1px solid #e0e0e0;
  padding: 15px;
  //border-radius: 10px;
  //box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  border-bottom: 0.5px solid #EDEFF2;
  background-color: #fff;
  cursor: pointer;
  transition: transform 0.2s ease;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.post-list .post-item:hover {
  transform: scale(1.02);
}

.post-header {
  display: flex;
  align-items: center; /* Align category and user vertically centered */
  gap: 5px; /* Add a small gap between the category and writer */
  font-size: 12px;
  color: #2973E4; /* Apply color to the category */
  width: 100%;
}

.category {
  color: #2973E4; /* Color for category */
  font-weight: 600; /* Make the category slightly bolder */
}

.user {
  color: #888; /* Make the user ID a lighter color */
  font-weight: 400; /* Normal weight for the user ID */
}

.post-title {
  font-size: 18px;
  margin: 10px 0 0;
  font-weight: bold;
  color: #333;
  width: 100%;
}

.post-content {
  font-family: 'Pretendard', sans-serif;
  font-size: 14px;
  font-weight: 400;
  line-height: 21px;
  letter-spacing: -0.02em;
  text-align: left;
  color: #555;
  margin-bottom: 10px;
  width: 100%;

  /* CSS for truncating the text */
  display: -webkit-box;
  -webkit-line-clamp: 3; /* Limits the content to 3 lines */
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis; /* Adds "..." at the end of the content */
}


.post-footer {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #aaa;
  width: 100%;
}



.floating-button {
  position: fixed;
  bottom: 100px; /* Adjust this as needed for spacing from the bottom */
  right: 20px; /* Adjust this as needed for spacing from the right */
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background: linear-gradient(110.4deg, #3E8AFF -8.05%, #7E75FF 109.17%);
  display: flex;
  align-items: center;
  justify-content: center;
  //box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
  cursor: pointer;
  z-index: 1100;
  opacity: 1; /* Setting opacity to 1 to make it visible */
  transition: transform 0.3s ease;
  border: none; /* Remove any border */
  outline: none; /* Remove the focus outline */
  box-shadow: none; /* Remove any shadow */
}

.floating-button:hover {
  transform: scale(1.05); /* Slightly enlarge the button on hover */
}

.floating-button img {
  width: 24px; /* Set the size of the plus icon */
  height: 24px;
  object-fit: contain;
}


</style>
