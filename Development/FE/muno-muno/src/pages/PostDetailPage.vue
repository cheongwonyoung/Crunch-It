<template>
  <div class="post-detail-page">
    <header class="header">
<!--      <h1 class="title">{{ post.title }}</h1>-->
      <!-- Back Icon -->
      <img src="@/assets/arrowLeft.svg" alt="back-icon" @click="goBack" class="back-icon"/>

      <!-- 설정 아이콘 및 드롭다운 메뉴 -->
      <div class="settings-menu">
        <img src="@/assets/dots-vertical.svg" alt="dots-vertical"  @click="toggleSettingsMenu"/>
        <div v-if="showSettingsMenu" class="dropdown-menu">
          <ul>
            <li @click="goToEditPage">수정</li>
            <li @click="deletePost">삭제</li>
          </ul>
        </div>
      </div>
    </header>
    <div class="post-meta">
      <span class="category">{{ post.category }}</span>
      <h1 class="title">{{ post.title }}</h1>
      <div class="user-info">
        <img class="user-avatar" src="https://via.placeholder.com/40" alt="avatar" />
        <div class="user-meta">
          <span class="user">{{ post.writerId }}</span>
          <span class="date">{{ formattedDate }}</span>
        </div>
      </div>
    </div>


<!--    <div class="post-content">-->
<!--      <p>{{ post.content }}</p>-->
<!--    </div>-->

<!--    <div class="post-likes" @click="likePost">-->
<!--      <span :class="likedByUser ? 'liked' : ''">❤️ {{ post.likes }}</span>-->
<!--    </div>-->
    <div class="post-content">
      <p>{{ post.content }}</p>
    </div>

    <div class="post-actions">
      <div class="likes-comments">
        <img src="@/assets/heart-rounded.svg" alt="like" @click="likePost" class="action-icon" />
        <!-- Fix the class binding for likedByUser -->
        <span :class="{ liked: likedByUser }" class="likes-count">공감 {{ post.likes }}</span>
        <img src="@/assets/message-square.svg" alt="comments" class="action-icon" />
        <span class="comment-count">댓글 {{ post.commentsCount }}</span>
      </div>
    </div>


    <!-- 댓글 리스트 및 수정/삭제 기능 추가 -->
    <CommentList :comments="comments" :replies="replies"
                 @update-comment-reply-count="updateCommentReplyCount"
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
    <MessageInput></MessageInput>


    <router-link to="/community" class="back-link">목록</router-link>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import apiClient from '../axios';
//import axios from 'axios';
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
    const REPLY_API_URL = `http://localhost:8080/apiClient/replies`; // 답글 API 경로 상수화
    const COMMENT_API_URL = `http://localhost:8080/apiClient/comments`; // 댓글 API 경로 상수화
    const LIKE_API_URL = `http://localhost:8080/apiClient/likes`; // 답글 API 경로 상수화

    const post = ref({
      title: '',
      content: '',
      category: '',
      writerId: '',
      registerDate: '',
      modifyDate: '',
      likes: 0,
      commentsCount:0,
    });

    const comments = ref([]);
    const replies = ref([]); // 모든 답글 데이터를 저장
    const newComment = ref('');
    const showSettingsMenu = ref(false);
    const goBack = () => {
      router.push('/community');
    };

    const updateCommentReplyCount = ({ commentCount, replyCount }) => {
      post.value.commentsCount = commentCount + replyCount; // Sum of comments and replies
    };

    const token=localStorage.getItem('JwtToken');
    let userId=null;
    let nickname='';

    //decode
    if (token) {
      try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(
            atob(base64)
                .split('')
                .map(c => `%${('00' + c.charCodeAt(0).toString(16)).slice(-2)}`)
                .join('')
        );
        const decodedToken = JSON.parse(jsonPayload);

        // user_id가 숫자인지 확인하고, 문자열일 경우 정수로 변환
        if (!isNaN(decodedToken.user_id)) {
          userId = parseInt(decodedToken.user_id, 10);
        } else {
          console.error('Invalid user_id format:', decodedToken.user_id);
        }

        // userId = decodedToken?.user_id || decodedToken?.userId;
        nickname=decodedToken?.nickname;
        console.log("decoded Token", decodedToken);

      } catch (error) {
        console.error('Error decoding token manually:', error);
      }
    } else {
      console.error('No JWT token found in localStorage');
    }

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
        const response = await apiClient.get(url);
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
          await apiClient.delete(`${API_URL}/delete`);
          alert('게시글이 삭제되었습니다.');
          router.push('/community');
        } catch (error) {
          console.error('Error deleting post:', error);
        }
      } else {
        showSettingsMenu.value=false;
      }
    };

    const submitComment = async () => {
      if (!newComment.value.trim()) return;

      try {
        const payload = {
          content: newComment.value,
          writerId: userId,
          boardId: postId,
        };
        console.log(payload);

        await apiClient.post(`${COMMENT_API_URL}/create/${postId}`, payload);

        newComment.value = '';
        fetchPostAndComments();
      } catch (error) {
        console.error('Error submitting comment:', error.response ? error.response.data : error.message);
      }
    };


    const handleUpdateComment = async ({commentId, content}) => {
      try {
        await apiClient.put(`${COMMENT_API_URL}/modify/${commentId}`, {content});
        fetchPostAndComments();
      } catch (error) {
        console.error('Error updating comment:', error);
      }
    };

    const handleDeleteComment = async (commentId) => {
      if (confirm("정말 이 댓글을 삭제하시겠습니까?")) {
        try {
          await apiClient.delete(`${COMMENT_API_URL}/delete/${commentId}`);
          fetchPostAndComments();
        } catch (error) {
          console.error('Error deleting comment:', error);
        }
      }
    };

    const submitReply = async ({ commentId, content }) => {
      try {
        const response = await apiClient.post(`${REPLY_API_URL}/${commentId}`, {
          writerId: userId, // 로그인된 사용자 ID로 수정해야
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
        await apiClient.put(`${REPLY_API_URL}/modify/${replyId}`, { content });
        fetchPostAndComments(); // 데이터를 다시 불러와서 갱신
      } catch (error) {
        console.error('답글 수정 중 오류 발생:', error);
      }
    };

    const handleDeleteReply = async (replyId) => {
      if (confirm('정말 이 답글을 삭제하시겠습니까?')) {
        try {
          await apiClient.delete(`${REPLY_API_URL}/delete/${replyId}`);
          fetchPostAndComments();
        } catch (error) {
          console.error('답글 삭제 중 오류 발생:', error);
        }
      }
    };
    const likedByUser = ref(false);

    const likePost = async () => {
      try {
        const payload = {
          boardId: postId,
          writerId: post.value.writerId,
          userId,
        };

        if(likedByUser.value){
          await apiClient.post(`${LIKE_API_URL}`,payload);
          likedByUser.value=false;
          post.value.likes-=1;
        } else {
          const response=await apiClient.post(`${LIKE_API_URL}`, payload);
          likedByUser.value=response.data;
          post.value.likes+=1;
        }
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
      likePost,
      nickname,
      goBack,
      updateCommentReplyCount,
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
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 5px 0;
}

.back-icon {
  cursor: pointer;
  width: 24px;
  height: 24px;
}

.settings-menu {
  cursor: pointer;
  position: relative;
}

.settings-menu .dropdown-menu {
  position: absolute;
  top: 24px;
  right: 0;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 100;
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
  writing-mode: horizontal-tb;
  white-space: nowrap;
  font-family: Arial, sans-serif;
}

.settings-menu .dropdown-menu li:hover {
  background-color: #f0f0f0;
}

.post-meta {
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
}

.category {
  font-size: 14px;
  font-weight: 500;
  color: #383E47;
  margin-bottom: 5px;
}

.title {
  font-size: 20px;
  font-weight: 600;
  color: #292D33;
  line-height: 1.5;
  margin: 8px 0;
}

.user-info {
  display: flex;
  align-items: center;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 8px;
}
.user-meta {
  display: flex;
  flex-direction: column; /* Stack the user and date vertically */
}

.user {
  font-size: 14px;
  color: #383E47;
  margin-right: 8px;
  margin-bottom: 2px; /* Add a small space between the user and date */
}

.date {
  font-size: 12px;
  color: #909090;
}

.post-content {
  font-size: 16px;
  line-height: 1.6;
  color: #333;
  //margin-bottom: 20px;
  margin-top: 10px; /* Add some space above and below the post content */
  padding: 10px 0; /* Add padding to make the borders stand out */
  border-top: 1px solid #ddd; /* Light gray line above the content */
  border-bottom: 1px solid #ddd; /* Light gray line below the content */
  white-space: pre-wrap; /* Ensure new lines are rendered as in the post */

}

.post-likes {
  font-size: 16px;
  color: #555;
  margin-bottom: 20px;
}

.liked {
  color: red;
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

.post-actions {
  display: flex;
  align-items: center;
  justify-content: space-between; /* Space between likes and comments */
  padding: 10px 0; /* Add padding to give some space */
  border-bottom: 6px solid #F5F6F7; /* Divider line below the actions */
  margin-bottom: 16px; /* Space below the post actions */

}

.likes-comments {
  display: flex;
  align-items: center;
  font-family: 'Pretendard', sans-serif;
  font-size: 14px;
  font-weight: 400;
  line-height: 14px;
  text-align: left;
  gap: 6px; /* Adjust the gap between icons and text */
}

.action-icon {
  width: 16px;
  height: 18px;
  cursor: pointer;
}

.likes-count {
  margin-right: 10px; /* Increased margin between heart icon and "공감" text */
  color:#383E47;
}

.comment-count {
  font-family: 'Pretendard', sans-serif;
  font-size: 14px;
  font-weight: 400;
  line-height: 14px;
  color:#383E47;
}

.liked:hover,
.comment-count:hover {
  text-decoration: underline;
}

.liked {
  color: red; /* Example for liked color, adjust as necessary */
}


</style>
