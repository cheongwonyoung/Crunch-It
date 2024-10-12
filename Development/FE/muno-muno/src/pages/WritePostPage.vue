<template>
  <div class="write-post-page">
    <HeaderB title="게시글 작성" @back="onBack" />

    <form @submit.prevent="submitPost">
      <div class="form-group category-wrapper">
        <label for="category" class="category-label">카테고리</label>
        <div class="select-wrapper">
          <select
            v-model="category"
            id="category"
            required
            class="category-select"
          >
            <option disabled value="">카테고리를 선택하세요</option>
            <option v-for="cat in categories" :key="cat" :value="cat">
              {{ cat }}
            </option>
          </select>
          <img
            src="@/assets/chevronDown.svg"
            alt="Dropdown Icon"
            class="dropdown-icon"
          />
        </div>
      </div>

      <div class="form-group">
        <input
          type="text"
          v-model="title"
          id="title"
          placeholder="제목을 입력해주세요"
          required
          class="input-field"
        />
      </div>

      <div class="form-group">
        <textarea
          v-model="content"
          id="content"
          rows="8"
          placeholder="내용을 입력해주세요"
          required
          class="input-field"
        ></textarea>
      </div>

      <ButtonA type="submit">등록하기</ButtonA>
    </form>
  </div>
</template>

<script>
import { ref } from 'vue';
import apiClient from '@/axios';
import { useRouter } from 'vue-router';
import HeaderB from '@/components/HeaderB.vue';
import ButtonA from '@/components/ButtonA.vue';

export default {
  name: 'WritePostP',
  components: {
    HeaderB,
    ButtonA,
  },
  setup() {
    const title = ref('');
    const category = ref('전체');
    const content = ref('');
    const router = useRouter();

    const categories = ['전체', '지출', '예적금', '펀드', '주식', '채권'];

    const token = localStorage.getItem('JwtToken');
    let userId = null;

    if (token) {
      try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(
          atob(base64)
            .split('')
            .map((c) => `%${('00' + c.charCodeAt(0).toString(16)).slice(-2)}`)
            .join('')
        );
        const decodedToken = JSON.parse(jsonPayload);

        if (!isNaN(decodedToken.user_id)) {
          userId = parseInt(decodedToken.user_id, 10);
        } else {
          console.error('Invalid user_id format:', decodedToken.user_id);
        }

        userId = decodedToken?.user_id || decodedToken?.userId;
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
        await apiClient.post('/community/create', postData);
        router.push('/community');
      } catch (error) {
        console.error('게시글 작성 중 오류 발생:', error);
      }
    };

    const onBack = () => {
      router.back();
    };

    return {
      title,
      category,
      content,
      categories,
      submitPost,
      onBack,
    };
  },
};
</script>

<style scoped>
.write-post-page {
  position: absolute;
  top: 108px;
  left: 0;
  width: 100%;
  max-width: 600px;
  padding: 0 20px;
  box-sizing: border-box;
  overflow-y: auto;
}

.form-group {
  margin-top: 12px;
}

.category-label {
  margin: 0 10px 0 2px;
  color: var(--gr40);
  font-size: 20px;
  font-weight: 400;
  line-height: 100%;
}

.category-wrapper {
  display: flex;
  align-items: center;
  margin-top: 12px;
}

.select-wrapper {
  display: flex;
  align-items: center;
}

.category-select {
  -webkit-appearance: none;
  color: var(--gr20);
  font-size: 20px;
  font-weight: 600;
  line-height: 100%;
  border: none;
  width: auto;
  max-width: 60px;
}

.input-field {
  width: 335px;
  height: 56px;
  flex-shrink: 0;
  border-radius: 12px;
  border: 0.5px solid var(--gr60);
  background: var(--gr100);
  margin: 0;
  padding: 20px;
  color: var(--gr30);
  font-size: 16px;
}

textarea.input-field {
  width: 335px;
  height: 306px;
  box-sizing: border-box;
  padding: 20px;
  color: var(--gr30);
  font-size: 16px;
}

.input-field::placeholder,
textarea.input-field::placeholder {
  color: var(--gr60);
}

.input-field:focus,
textarea.input-field:focus {
  color: var(--gr20);
  font-size: 16px;
}
</style>
