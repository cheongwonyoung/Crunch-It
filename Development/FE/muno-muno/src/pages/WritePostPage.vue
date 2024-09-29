<template>
  <div class="write-post-page">
    <h1>글쓰기</h1>
    <form @submit.prevent="submitPost"> <!-- 바로 서버로 전송 -->
      <div class="form-group">
        <label for="title">제목</label>
        <input type="text" v-model="title" id="title" required />
      </div>

      <div class="form-group">
        <label for="category">카테고리</label>
        <select v-model="category" id="category" required>
          <option disabled value="">카테고리를 선택하세요</option>
          <option v-for="cat in categories" :key="cat" :value="cat">{{ cat }}</option>
        </select>
      </div>

      <div class="form-group">
        <label for="content">내용</label>
        <textarea v-model="content" id="content" rows="10" required></textarea>
      </div>

      <div class="form-actions">
        <button type="submit">확인</button> <!-- DB로 전송하는 버튼 -->
        <button type="button" @click="cancelPost">취소</button> <!-- 취소 버튼 -->
      </div>
    </form>
  </div>
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';

export default {
  name: 'WritePostP',
  setup() {
    const title = ref('');
    const category = ref('');
    const content = ref('');
    const router = useRouter();

    const categories = ['지출', '예적금', '펀드', '주식', '채권'];

    // 사용자가 글을 최종적으로 제출하는 함수
    const submitPost = async () => {
      const postData = {
        title: title.value,
        category: category.value,
        content: content.value,
        writerId: 1, // 임시 작성자 ID (로그인 시스템이 있다면 동적으로 설정 가능)
      };

      try {
        await axios.post('http://localhost:8080/community/create', postData); // 서버로 데이터 전송
        router.push('/community'); // 게시글 작성 후 목록으로 이동
      } catch (error) {
        console.error('게시글 작성 중 오류 발생:', error);
      }
    };

    // 작성 취소 함수
    const cancelPost = () => {
      if (confirm('작성한 내용을 취소하시겠습니까?')) {
        router.push('/community'); // 커뮤니티 페이지로 이동
      }
    };

    return {
      title,
      category,
      content,
      categories,
      submitPost,
      cancelPost
    };
  },
};
</script>

<style scoped>
.write-post-page {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
}

input[type="text"],
select,
textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

button {
  padding: 10px 20px;
  background-color: #007BFF;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

.form-actions {
  display: flex;
  gap: 10px;
  margin-top: 20px;
}

button[type="button"] {
  background-color:#D6DAE0 ; /* 취소 버튼 색상 */
}

button[type="button"]:hover {
  background-color: #bdc0c6;
}
</style>
