<template>
  <div class="edit-post-page">
    <HeaderB title="게시글 수정" @back="onBack" />

    <form @submit.prevent="submitEdit">
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
        const response = await apiClient.get(
          `http://localhost:8080/community/${postId}`
        );
        editTitle.value = response.data.title;
        editContent.value = response.data.content;
        category.value = response.data.category;
      } catch (error) {
        console.error('Error fetching post:', error);
      }
    };

    const submitEdit = async () => {
      try {
        await apiClient.put(
          `http://localhost:8080/community/${postId}/modify`,
          {
            title: editTitle.value,
            category: category.value,
            content: editContent.value,
          }
        );
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
  margin-right: 10px;
  color: var(--gr40);
  font-size: 20px;
  font-weight: 400;
  line-height: 100%;
}

.category-wrapper {
  display: flex;
  align-items: center;
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
