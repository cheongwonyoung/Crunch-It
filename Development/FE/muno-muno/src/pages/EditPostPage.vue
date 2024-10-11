<template>
  <div class="edit-post-page">
    <h1>게시글 수정</h1>
    <form @submit.prevent="submitEdit">
      <div class="form-group">
        <label for="title">제목</label>
        <input type="text" id="title" v-model="editTitle" />
      </div>
      <div class="form-group">
        <label for="category">카테고리</label>
        <select v-model="category" id="category" required>
          <option disabled value="">카테고리를 선택하세요</option>
          <option v-for="cat in categories" :key="cat" :value="cat">
            {{ cat }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label for="content">내용</label>
        <textarea id="content" v-model="editContent" rows="6"></textarea>
      </div>
      <button type="submit" class="submit-btn">수정 완료</button>
    </form>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import apiClient from '../axios';
import { useRoute, useRouter } from 'vue-router';

export default {
  name: 'EditPostP',
  setup() {
    const route = useRoute();
    const router = useRouter();
    const postId = route.params.id;

    const categories = ['지출', '예적금', '펀드', '주식', '채권'];

    const editTitle = ref('');
    const editContent = ref('');
    const category = ref(''); // 카테고리 선택

    // 게시글 정보 가져오기
    const fetchPost = async () => {
      try {
        const response = await apiClient.get(
          `http://localhost:8080/community/${postId}`
        );
        editTitle.value = response.data.title;
        editContent.value = response.data.content;
        category.value = response.data.category; // 카테고리 초기화
      } catch (error) {
        console.error('Error fetching post:', error);
      }
    };

    // 게시글 수정하기
    const submitEdit = async () => {
      try {
        await apiClient.put(
          `http://localhost:8080/community/${postId}/modify`,
          {
            title: editTitle.value,
            category: category.value, // 카테고리 포함
            content: editContent.value,
          }
        );
        alert('게시글이 수정되었습니다.');
        router.push(`/community/${postId}`);
      } catch (error) {
        console.error('Error updating post:', error);
      }
    };

    onMounted(() => {
      fetchPost(); // 초기 게시글 정보 가져오기
    });

    return {
      editTitle,
      editContent,
      category, // 추가
      categories, // 카테고리 리스트
      submitEdit,
    };
  },
};
</script>

<style scoped>
.edit-post-page {
  max-width: 720px;
  margin: 0 auto;
  padding: 20px;
  font-family: 'Helvetica Neue', Arial, sans-serif;
}

h1 {
  font-size: 24px;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  font-size: 14px;
  margin-bottom: 5px;
}

input,
textarea,
select {
  width: 100%;
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.submit-btn {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.submit-btn:hover {
  background-color: #0056b3;
}
</style>
