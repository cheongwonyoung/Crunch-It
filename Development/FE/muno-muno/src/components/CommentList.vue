<template>
  <div class="comment-list">
    <h4>댓글 {{ comments.length }}개</h4>
    <ul v-if="comments.length > 0" class="comment-list-items">
      <li v-for="(comment, index) in comments" :key="comment.commentId" class="comment-item">
        <div v-if="editIndex === index" class="edit-comment">
          <textarea v-model="editContent" class="edit-textarea"></textarea>
          <button @click="updateComment(comment.commentId, editContent)">저장</button>
          <button @click="cancelEdit">취소</button>
        </div>
        <div v-else class="comment-content">
          <p>{{ comment.content }}</p>
        </div>
        <div class="comment-meta">
          <span class="writer">작성자: {{ comment.writerId }}</span>
          <span class="separator">|</span>
          <span class="date">{{ formatDate(comment.registerDate) }}</span>
        </div>
        <div class="comment-actions">
          <button @click="enableEdit(index, comment.content)">수정</button>
          <button @click="deleteComment(comment.commentId)">삭제</button>
        </div>

        <!-- replyList가 있을 경우 답글 리스트 표시 -->
        <div v-if="comment.replyList && comment.replyList.length" class="reply-list">
          <div v-for="reply in comment.replyList" :key="reply.replyId" class="reply-item">
            <p>{{ reply.content }}</p>
            <small>답글 작성자: {{ reply.writerId }}</small>
          </div>
        </div>
      </li>
    </ul>
    <p v-else>댓글이 없습니다.</p>
  </div>
</template>

<script>
export default {
  name: 'CommentList',
  props: {
    comments: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      editIndex: null,
      editContent: ''
    };
  },
  methods: {
    formatDate(dateArray) {
      if (!dateArray || dateArray.length < 3) return '날짜 없음';
      const [year, month, day] = dateArray;
      return `${year}. ${String(month).padStart(2, '0')}. ${String(day).padStart(2, '0')}`;
    },
    enableEdit(index, content) {
      this.editIndex = index;
      this.editContent = content;
    },
    cancelEdit() {
      this.editIndex = null;
      this.editContent = '';
    },
    async updateComment(commentId, content) {
      try {
        await this.$emit('update-comment', { commentId, content });
        this.editIndex = null;
        this.editContent = '';
      } catch (error) {
        console.error('댓글 수정 중 오류 발생:', error);
      }
    },
    async deleteComment(commentId) {
      try {
        await this.$emit('delete-comment', commentId);
      } catch (error) {
        console.error('댓글 삭제 중 오류 발생:', error);
      }
    }
  }
};
</script>

<style scoped>
.comment-list {
  margin-top: 20px;
  padding: 0 16px;
}

.comment-list-items {
  list-style: none;
  padding-left: 0;
}

.comment-item {
  border-bottom: 1px solid #eee;
  padding: 12px 0;
}

.comment-content p {
  margin: 0;
  font-size: 15px;
  color: #333;
}

.comment-meta {
  margin-top: 5px;
  font-size: 12px;
  color: #999;
  display: flex;
  gap: 10px;
  align-items: center;
}

.writer {
  color: #007bff;
}

.separator {
  color: #ddd;
}

.comment-actions {
  margin-top: 10px;
  display: flex;
  gap: 10px;
}

.comment-actions button {
  font-size: 12px;
  background-color: #007bff;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 4px;
  cursor: pointer;
}

.comment-actions button:hover {
  background-color: #0056b3;
}

.edit-comment {
  margin-bottom: 10px;
}

.edit-textarea {
  width: 100%;
  height: 80px;
  padding: 10px;
  font-size: 14px;
  border-radius: 4px;
  border: 1px solid #ddd;
}

.edit-comment button {
  margin-top: 10px;
  margin-right: 10px;
  padding: 5px 10px;
}

/* 추가된 reply-list 스타일 */
.reply-list {
  margin-top: 10px;
  padding-left: 20px;
  border-left: 2px solid #ddd;
}

.reply-item {
  margin-top: 5px;
}
</style>
