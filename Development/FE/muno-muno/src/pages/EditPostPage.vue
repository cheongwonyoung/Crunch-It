<template>
  <div class="edit-post-page">
    <HeaderB title="게시글 수정" @back="onBack" />

    <form @submit.prevent="submitEdit">
      <div class="category-wrapper">
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
          v-model="editTitle"
          id="title"
          placeholder="제목을 입력해주세요"
          required
          class="input-field"
        />
      </div>

      <div class="form-group">
        <textarea
          v-model="editContent"
          id="content"
          rows="8"
          placeholder="내용을 입력해주세요"
          required
          class="input-field"
        ></textarea>
      </div>

      <ButtonA @onClick="submitEdit">수정 완료</ButtonA>
    </form>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import apiClient from '@/axios';
import { useRoute, useRouter } from 'vue-router';
import HeaderB from '@/components/HeaderB.vue';
import ButtonA from '@/components/ButtonA.vue';

export default {
  name: 'EditPostP',
  components: {
    HeaderB,
    ButtonA,
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const postId = route.params.id;

    const categories = ['전체', '지출', '예적금', '펀드', '주식', '채권'];

    const editTitle = ref('');
    const editContent = ref('');
    const category = ref('');

    const fetchPost = async () => {
      try {
        const response = await apiClient.get(`/community/${postId}`);
        editTitle.value = response.data.title;
        editContent.value = response.data.content;
        category.value = response.data.category;
      } catch (error) {
        console.error('Error fetching post:', error);
      }
    };

    const submitEdit = async () => {
      try {
        await apiClient.put(`/community/${postId}/modify`, {
          title: editTitle.value,
          category: category.value,
          content: editContent.value,
        });
        alert('게시글이 수정되었습니다.');
        router.push(`/community/${postId}`);
      } catch (error) {
        console.error('Error updating post:', error);
      }
    };

    const onBack = () => {
      router.back();
    };

    onMounted(() => {
      fetchPost();
    });

    return {
      editTitle,
      editContent,
      category,
      categories,
      submitEdit,
      onBack,
    };
  },
};
</script>

<style scoped>
.edit-post-page {
  padding: 0 20px;
  margin-bottom: 34px;
  background-color: var(--gr100);
  height: 100vh;
  width: 335px;
  overflow-y: auto; /* 내용이 넘칠 경우 스크롤 허용 */
  position: fixed; /* 고정 위치 */
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 10; /* 다른 요소들 위에 표시 */
  font-family: 'Pretendard', sans-serif;
}

.form-group {
  margin-top: 12px;
}

.category-label {
  margin-right: 10px;
  color: var(--gr40);
  font-size: 20px;
  font-weight: 400;
  line-height: 100%;
}

.category-wrapper {
  display: flex;
  align-items: center;
  margin-top: 76px;
}

.select-wrapper {
  display: flex;
  align-items: center;
}

.category-select {
  font-family: 'Pretendard', sans-serif;
  -webkit-appearance: none;
  color: var(--gr20);
  font-size: 20px;
  font-weight: 600;
  line-height: 100%;
  border: none;
  width: auto;
  max-width: 40px;
}

.input-field {
  width: 295px;
  height: 56px;
  flex-shrink: 0;
  border-radius: 12px;
  border: 0.5px solid var(--gr60);
  background: var(--gr100);
  margin: 0;
  padding: 20px;
  color: var(--gr30);
  font-size: 16px;
  font-family: 'Pretendard', sans-serif;
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
