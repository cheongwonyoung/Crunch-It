<template>
  <div class="post-detail-page">
    <header class="header">
      <h1 class="title">{{ post.title }}</h1>

      <!-- 설정 아이콘 및 드롭다운 메뉴 -->
      <div class="settings-menu">
        <i class="fas fa-ellipsis-v" @click="toggleSettingsMenu"></i>
        <div v-if="showSettingsMenu" class="dropdown-menu">
          <ul>
            <li @click="goToEditPage">수정</li>
            <li @click="deletePost">삭제</li>
          </ul>
        </div>
      </div>

      <div class="post-meta">
        <span class="category">{{ post.category }}</span> |
        <span class="user">{{ post.writerId }}</span> |
        <span class="date">{{ formattedDate }}</span>
      </div>
    </header>

    <div class="post-content">
      <p>{{ post.content }}</p>
    </div>

    <div class="post-likes">
      <span>❤️ {{ post.likes }}</span>
    </div>

    <!-- 댓글 리스트 및 수정/삭제 기능 추가 -->
    <CommentList :comments="comments" @update-comment="handleUpdateComment" @delete-comment="handleDeleteComment" />

    <!-- 댓글 입력 -->
    <div class="comment-input">
      <textarea
          v-model="newComment"
          placeholder="댓글을 입력하세요..."
          rows="3"
      ></textarea>
      <button @click="submitComment" class="submit-btn">댓글 등록</button>
    </div>

    <router-link to="/community" class="back-link">목록</router-link>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import CommentList from "@/components/CommentList.vue";

export default {
  name: 'PostDetailP',
  components: { CommentList },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const postId = route.params.id;
    const API_URL = `http://localhost:8080/community/${postId}`; // API 경로 상수화

    const post = ref({
      title: '',
      content: '',
      category: '',
      writerId: '',
      registerDate: '',
      modifyDate: '',
      likes: 0,
    });

    const comments = ref([]);
    const newComment = ref('');
    const showSettingsMenu = ref(false);

    // 날짜 포맷팅 함수 직접 포함
    const formatDate = (dateArray) => {
      if (!dateArray || dateArray.length < 3) return 'No date';
      const [year, month, day] = dateArray;
      return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
    };

    const formattedDate = computed(() =>
        post.value.modifyDate ? formatDate(post.value.modifyDate) : formatDate(post.value.registerDate)
    );

    const fetchData = async (url, callback) => {
      try {
        const response = await axios.get(url);
        callback(response.data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    const fetchPostAndComments = () => {
      fetchData(API_URL, (data) => (post.value = data));
      fetchData(`${API_URL}/comments`, (data) => (comments.value = data));
    };

    const toggleSettingsMenu = () => {
      showSettingsMenu.value = !showSettingsMenu.value;
    };

    const goToEditPage = () => {
      router.push({ name: 'EditPost', params: { id: postId } });
    };

    const deletePost = async () => {
      if (confirm("정말 삭제하시겠습니까?")) {
        try {
          await axios.delete(`${API_URL}/delete`);
          alert('게시글이 삭제되었습니다.');
          router.push('/community');
        } catch (error) {
          console.error('Error deleting post:', error);
        }
      }
    };

    const submitComment = async () => {
      if (!newComment.value.trim()) return;

      try {
        await axios.post(`${API_URL}/comments/create`, {
          content: newComment.value,
          writerId: 1001, // 실제 로그인된 사용자 ID 사용
          boardId: postId
        });
        newComment.value = '';
        fetchPostAndComments();
      } catch (error) {
        console.error('Error submitting comment:', error);
      }
    };

    const handleUpdateComment = async ({ commentId, content }) => {
      try {
        await axios.put(`${API_URL}/comments/modify/${commentId}`, { content });
        fetchPostAndComments();
      } catch (error) {
        console.error('Error updating comment:', error);
      }
    };

    const handleDeleteComment = async (commentId) => {
      if (confirm("정말 이 댓글을 삭제하시겠습니까?")) {
        try {
          await axios.delete(`${API_URL}/comments/delete/${commentId}`);
          fetchPostAndComments();
        } catch (error) {
          console.error('Error deleting comment:', error);
        }
      }
    };

    onMounted(fetchPostAndComments);

    return {
      post,
      comments,
      newComment,
      showSettingsMenu,
      formattedDate,
      submitComment,
      toggleSettingsMenu,
      goToEditPage,
      deletePost,
      handleUpdateComment,
      handleDeleteComment
    };
  },
};
</script>
<style scoped>
.post-detail-page {
  padding: 16px;
  max-width: 700px;
  margin: 0 auto;
  font-family: 'Helvetica Neue', Arial, sans-serif;
  color: #444;
}

.header {
  border-bottom: 2px solid #eee;
  margin-bottom: 20px;
  padding:5px;
  position: relative;
}

.title {
  font-size: 25px;
  font-weight: bold;
  margin-bottom: 5px;
}

.settings-menu {
  position: absolute;
  top: 0;
  right: 0;
  cursor: pointer;
}

.settings-menu .dropdown-menu {
  position: absolute;
  top: 20px;
  left: 0;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.settings-menu .dropdown-menu ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.settings-menu .dropdown-menu li {
  padding: 10px;
  cursor: pointer;
  font-size: 14px;
  display: inline-block; /* inline-block으로 수정하여 세로로 나열되지 않도록 */
  writing-mode: horizontal-tb; /* 가로 텍스트 유지 */
  white-space: nowrap; /* 줄바꿈 없이 가로로 출력 */
  font-family: Arial, sans-serif; /* 기본 폰트 설정 */
}

.settings-menu .dropdown-menu li:hover {
  background-color: #f0f0f0;
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

.comment-input {
  display: flex;
  flex-direction: column;
  margin-top: 20px;
  padding:10px;
}

.comment-input textarea {
  //width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 14px;
  resize: none;
  margin-bottom: 10px;
}

.submit-btn {
  align-self: flex-end;
  padding: 8px 16px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}

.submit-btn:hover {
  background-color: #0056b3;
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
