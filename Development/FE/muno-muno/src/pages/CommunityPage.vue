<template>
  <div class="community-page">
    <!-- HeaderX 컴포넌트를 사용 -->
    <HeaderX title="커뮤니티" :icons="headerIcons" />

    <!-- 채팅 메시지 섹션 -->
    <div class="chat-header">
      <span>💬 문어봐도돼요?</span>
    </div>

    <!-- 커뮤니티 카테고리 섹션 -->
    <div class="community-categories">
      <div
        v-for="category in communityCategories"
        :key="category.name"
        class="category-item"
      >
        <img :src="category.imgSrc" :alt="category.name" class="category-img" />
        <span class="category-name">{{ category.name }}</span>
      </div>
    </div>

    <!-- CategoryP 컴포넌트를 사용한 카테고리 렌더링 -->
    <div class="category-tabs">
      <CategoryP
        v-for="category in categories"
        :key="category.name"
        :category="category.name"
        :isActive="selectedCategory === category.name"
        @category-selected="selectCategory"
      />
    </div>

    <!-- 서버로부터 데이터를 받아와 게시글 표시 -->
    <div class="post-list">
      <PostItem
        v-for="post in filteredPosts"
        :key="post.boardId"
        :post="post"
        :onClick="goToDetail"
      />
    </div>

    <!-- 이미지 버튼 -->
    <img
      src="@/assets/plus.svg"
      alt="Plus Icon"
      class="floating-button"
      @click="goToWritePage"
    />
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import apiClient from '../axios';
import { useRouter } from 'vue-router';
import CategoryP from '../components/Category.vue';
import HeaderX from '../components/HeaderX.vue';
import PostItem from '../components/PostItem.vue';

export default {
  name: 'CommunityPage',
  components: {
    CategoryP,
    HeaderX,
    PostItem,
  },
  setup() {
    const categories = ref([
      { name: '전체' },
      { name: '지출' },
      { name: '예적금' },
      { name: '펀드' },
      { name: '주식' },
      { name: '채권' },
    ]);

    const communityCategories = ref([
      { name: '문어방', imgSrc: require('@/assets/muno_room.svg') },
      { name: '거지방', imgSrc: require('@/assets/geoji_room.svg') },
      { name: '종토방', imgSrc: require('@/assets/jongto_room.svg') },
      { name: '자유방', imgSrc: require('@/assets/free_room.svg') },
    ]);

    const selectedCategory = ref('전체');
    const posts = ref([]);
    const router = useRouter();

    const headerIcons = ref([
      {
        src: require('@/assets/notification.svg'),
        alt: 'Notification Icon',
        onClick: () => goToNotifications(),
      },
    ]);

    const fetchPosts = async () => {
      try {
        const response = await apiClient.get('http://localhost:8080/community');
        posts.value = response.data;
      } catch (error) {
        console.error('서버로부터 데이터를 받아오는 중 오류 발생:', error);
      }
    };

    const filteredPosts = computed(() => {
      return posts.value.filter((post) => {
        if (!post || !post.boardId) {
          return false;
        }
        if (selectedCategory.value === '전체') {
          return true;
        }
        return post.category === selectedCategory.value;
      });
    });

    const selectCategory = (category) => {
      selectedCategory.value = category;
    };

    const goToDetail = (boardId) => {
      router.push({ name: 'PostDetail', params: { id: boardId } });
    };

    const goToWritePage = () => {
      router.push({ name: 'WritePost' });
    };

    const goToNotifications = () => {
      router.push({ name: 'Notification' });
    };

    onMounted(() => {
      fetchPosts();
    });

    return {
      categories,
      communityCategories,
      selectedCategory,
      filteredPosts,
      selectCategory,
      goToDetail,
      goToWritePage,
      goToNotifications,
      headerIcons,
    };
  },
};
</script>

<style scoped>
.community-page {
  padding: 0 20px;
  position: absolute;
  top: 112px;
  width: 375px;
  height: 812px;
  box-sizing: border-box;
}

.chat-header {
  display: flex;
  align-items: center;
  margin-bottom: 18px;
  color: var(--gr30);
  font-size: 20px;
  font-weight: 600;
  line-height: 100%;
}

.community-categories {
  display: flex;
  gap: 16px;
  margin-bottom: 20px;
  overflow-x: auto;
  scrollbar-width: none;
}

.community-categories::-webkit-scrollbar {
  display: none;
}

.category-item {
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.category-img {
  margin-bottom: 6px;
}

.category-name {
  color: var(--gr30);
  text-align: center;
  font-size: 14px;
  font-weight: 500;
  line-height: 150%;
}

.category-tabs {
  display: flex;
  justify-content: flex-start;
  overflow-x: auto;
  white-space: nowrap;
  scrollbar-width: none;
  border-bottom: 0.5px solid var(--gr70);
}

.category-tabs::-webkit-scrollbar {
  display: none;
}

.floating-button {
  position: fixed;
  bottom: 106px;
  right: 20px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  z-index: 1100;
  transition: transform 0.3s ease;
}

.floating-button:hover {
  transform: scale(1.05);
}
</style>
