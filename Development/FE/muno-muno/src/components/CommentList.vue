<template>
  <div class="comment-list">
    <h4>댓글 {{ comments.length }}개</h4>
    <ul v-if="comments.length > 0" class="comment-list-items">
      <li v-for="comment in comments" :key="comment.commentId" class="comment-item">
        <div class="comment-content">
          <p>{{ comment.content }}</p>
        </div>
        <div class="comment-meta">
          <span class="writer">작성자: {{ comment.writerId }}</span>
          <span class="separator">|</span>
          <span class="date">{{ formatDate(comment.registerDate) }}</span>
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
  methods: {
    formatDate(dateArray) {
      if (!dateArray || dateArray.length < 3) return '날짜 없음';
      const [year, month, day] = dateArray;
      return `${year}. ${String(month).padStart(2, '0')}. ${String(day).padStart(2, '0')}`;
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
  list-style: none; /* 목록의 기본 점 제거 */
  padding-left: 0;  /* 왼쪽 기본 여백 제거 */
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
</style>
