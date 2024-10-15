<template>
  <div class="message-input">
    <img class="icon-image" src="@/assets/picture.svg" alt="이미지 첨부" />
    <textarea
      ref="commentInput"
      v-model="newComment"
      placeholder="댓글을 입력하세요..."
      class="input-field"
      @input="autoScroll"
      @keyup.enter.prevent="submitComment"
    ></textarea>
    <img
      class="icon-send"
      src="@/assets/send.svg"
      alt="전송"
      @click="submitComment"
    />
  </div>
</template>

<script>
import apiClient from '@/axios';

export default {
  props: {
    postId: {
      type: String,
      required: true,
    },
    userId: {
      type: Number,
      required: true,
    },
    nickname: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      newComment: '',
    };
  },
  methods: {
    async submitComment() {
      if (!this.newComment.trim()) return;

      try {
        const payload = {
          content: this.newComment,
          writerId: this.userId,
          boardId: this.postId,
          nickname: this.nickname,
        };

        await apiClient.post(
          `/apiClient/comments/create/${this.postId}`,
          payload
        );

        this.newComment = '';
        this.$emit('comment-submitted');
      } catch (error) {
        console.error(
          'Error submitting comment:',
          error.response ? error.response.data : error.message
        );
      }
    },
    autoScroll() {
      this.$nextTick(() => {
        const textarea = this.$refs.commentInput;
        textarea.scrollTop = textarea.scrollHeight;
      });
    },
  },
};
</script>

<style scoped>
.message-input {
  border-radius: 12px 12px 0px 0px;
  border-top: 0.5px solid var(--gr60);
  background: var(--gr100);
  box-shadow: 0px 0px 20px 0px rgba(62, 68, 78, 0.1);
  display: flex;
  align-items: center;
  padding: 8px 20px 42px 13px;
  width: 375px;
  box-sizing: border-box;
}

.icon-image {
  width: 24px;
  height: 24px;
  cursor: pointer;
}

.input-field {
  width: 278px;
  height: 40px;
  padding: 10px 16px;
  border: none;
  border-radius: 40px;
  background: var(--gr80);
  font-size: 14px;
  outline: none;
  margin: 0 10px 0 8px;
  box-sizing: border-box;
  resize: none;
  overflow-y: auto;
  line-height: 20px;
  scrollbar-width: none;
  -ms-overflow-style: none;
  font-family: 'Pretendard', sans-serif;
  flex-grow: 1; /* Allows the textarea to take up available space */
}

.input-field::placeholder {
  color: #b0b0b0;
}

.input-field::-webkit-scrollbar {
  display: none;
}

.icon-send {
  width: 24px;
  height: 24px;
  cursor: pointer;
}
</style>
