<template>
  <div class="write-post-page">
    <h1 class="page-title">글쓰기</h1>
    <form @submit.prevent="submitPost">
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
        <textarea v-model="content" id="content" rows="8" required></textarea>
      </div>

      <div class="form-actions">
        <button type="submit" class="submit-button">확인</button>
        <button type="button" class="cancel-button" @click="cancelPost">취소</button>
      </div>
    </form>
  </div>
</template>

<script>
import { ref } from 'vue';
import apiClient from '@/axios';
import { useRouter } from 'vue-router';

export default {
  name: 'WritePostP',
  setup() {
    const title = ref('');
    const category = ref('');
    const content = ref('');
    const router = useRouter();

    const categories = ['지출', '예적금', '펀드', '주식', '채권'];

    const token=localStorage.getItem('JwtToken');
    let userId=null;
    //let nickname='';

    //decode
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

        userId = decodedToken?.user_id || decodedToken?.userId;
        //nickname=decodedToken?.nickname;
        //console.log("decoded Token", decodedToken);

      } catch (error) {
        console.error('Error decoding token manually:', error);
      }
    } else {
      console.error('No JWT token found in localStorage');
    }

    const submitPost = async () => {
      const postData = {
        title: title.value,
        category: category.value,
        content: content.value,
        writerId: userId,
      };
      console.log(postData);

      try {
        await apiClient.post('http://localhost:8080/community/create', postData);
        router.push('/community');
      } catch (error) {
        console.error('게시글 작성 중 오류 발생:', error);
      }
    };

    const cancelPost = () => {
      if (confirm('작성한 내용을 취소하시겠습니까?')) {
        router.push('/community');
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
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  font-family: Arial, sans-serif;
}

.page-title {
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
  color: #333;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
  color: #555;
}

input[type="text"],
select,
textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
  color: #333;
  box-sizing: border-box; /* 모든 요소의 크기를 일관되게 유지 */
}

textarea {
  resize: vertical;
}

input:focus,
select:focus,
textarea:focus {
  outline: none;
  border-color: #007BFF;
}

.form-actions {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 20px;
}

button {
  padding: 10px 40px;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

.submit-button {
  background-color: #007BFF;
  color: white;
}

.submit-button:hover {
  background-color: #0056b3;
}

.cancel-button {
  background-color: #D6DAE0;
  color: black;
}

.cancel-button:hover {
  background-color: #bdc0c6;
}
</style>
