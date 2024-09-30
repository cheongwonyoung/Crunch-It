<template>
  <div class="comment-list">
    <h4>댓글 {{ comments.length }}개</h4>
    <ul v-if="comments.length > 0">
      <li v-for="comment in comments" :key="comment.commentId" class="comment-item">
        <p>{{ comment.content }}</p>
        <div class="comment-meta">
          <span>작성자: {{ comment.writerId }}</span> |
          <span>좋아요: {{ comment.likes }}</span> |
          <span>{{ formatDate(comment.registerDate) }}</span>
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
}

.comment-item {
  border-bottom: 1px solid #ddd;
  padding: 10px 0;
}

.comment-meta {
  font-size: 12px;
  color: #666;
}
</style>
