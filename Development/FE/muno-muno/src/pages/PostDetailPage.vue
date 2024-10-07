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

    <div class="post-likes" @click="likePost">
      <span>❤️ {{ post.likes }}</span>
    </div>


    <!-- 댓글 리스트 및 수정/삭제 기능 추가 -->
    <CommentList :comments="comments" :replies="replies"
                 @update-comment="handleUpdateComment"
                 @delete-comment="handleDeleteComment"
                 @submit-reply="submitReply"
                 @update-reply="handleUpdateReply"
                 @delete-reply="handleDeleteReply"/>

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
  components: {CommentList},
  setup() {
    const route = useRoute();
    const router = useRouter();
    const postId = route.params.id;
    const API_URL = `http://localhost:8080/community/${postId}`; // API 경로 상수화
    const REPLY_API_URL = `http://localhost:8080/api/replies`; // 답글 API 경로 상수화
    const COMMENT_API_URL = `http://localhost:8080/api/comments`; // 댓글 API 경로 상수화
    const LIKE_API_URL = `http://localhost:8080/api/likes`; // 답글 API 경로 상수화

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
    const replies = ref([]); // 모든 답글 데이터를 저장
    const newComment = ref('');
    const showSettingsMenu = ref(false);

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
      fetchData(`${COMMENT_API_URL}/board/${postId}`, (data) => (comments.value = data));
      fetchData(`${REPLY_API_URL}`, (data) => (replies.value = data)); // 모든 답글 데이터를 가져옴
    };

    const toggleSettingsMenu = () => {
      showSettingsMenu.value = !showSettingsMenu.value;
    };

    const goToEditPage = () => {
      router.push({name: 'EditPost', params: {id: postId}});
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
        await axios.post(`${COMMENT_API_URL}/create/${postId}`, {
          content: newComment.value,
          writerId: 1, // 실제 로그인된 사용자 ID 사용해야함
          boardId: postId
        });
        newComment.value = '';
        fetchPostAndComments();
      } catch (error) {
        console.error('Error submitting comment:', error.response ? error.response.data : error.message);
      }
    };

    const handleUpdateComment = async ({commentId, content}) => {
      try {
        await axios.put(`${COMMENT_API_URL}/modify/${commentId}`, {content});
        fetchPostAndComments();
      } catch (error) {
        console.error('Error updating comment:', error);
      }
    };

    const handleDeleteComment = async (commentId) => {
      if (confirm("정말 이 댓글을 삭제하시겠습니까?")) {
        try {
          await axios.delete(`${COMMENT_API_URL}/delete/${commentId}`);
          fetchPostAndComments();
        } catch (error) {
          console.error('Error deleting comment:', error);
        }
      }
    };
    const submitReply = async ({ commentId, content }) => {
      try {
        const response = await axios.post(`${REPLY_API_URL}/${commentId}`, {
          writerId: 1, // 로그인된 사용자 ID로 수정해야
          content: content,
          commentId:commentId
        });
        replies.value.push(response.data); // 새로 등록한 답글을 즉시 화면에 반영
        fetchPostAndComments();
      } catch (error) {
        console.error('Error submitting reply:', error);
      }
    };
    const handleUpdateReply = async ({ replyId, content }) => {
      try {
        await axios.put(`${REPLY_API_URL}/modify/${replyId}`, { content });
        fetchPostAndComments(); // 데이터를 다시 불러와서 갱신
      } catch (error) {
        console.error('답글 수정 중 오류 발생:', error);
      }
    };

    const handleDeleteReply = async (replyId) => {
      if (confirm('정말 이 답글을 삭제하시겠습니까?')) {
        try {
          await axios.delete(`${REPLY_API_URL}/delete/${replyId}`);
          fetchPostAndComments();
        } catch (error) {
          console.error('답글 삭제 중 오류 발생:', error);
        }
      }
    };
    const likePost = async () => {
      try {
        await axios.post(`${LIKE_API_URL}`,{
          writerId: post.value.writerId,  // 글의 데이터를 가져올 때 설정된 writer_id로
          boardId:postId,
          userId: 1, // 로그인된 사용자 ID로 수정해야
        });
        post.value.likes += 1; // Increment the likes in the frontend for immediate feedback
      } catch (error) {
        console.error('Error liking the post:', error);
      }
    };

    onMounted(fetchPostAndComments);

    return {
      post,
      comments,
      replies, // replies 데이터를 추가로 반환
      newComment,
      showSettingsMenu,
      formattedDate,
      submitReply,
      submitComment,
      toggleSettingsMenu,
      goToEditPage,
      deletePost,
      handleUpdateComment,
      handleDeleteComment,
      handleUpdateReply,
      handleDeleteReply,
      likePost
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
  padding: 5px;
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
  padding: 10px;
}

.comment-input textarea {
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
