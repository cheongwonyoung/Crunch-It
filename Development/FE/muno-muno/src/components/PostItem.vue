<template>
  <div class="post-item" @click="handleClick">
    <div class="post-header">
      <span class="category">{{ post.category }}</span>
      <span class="user">{{ post.nickname }}</span>
    </div>
    <h3 class="post-title">{{ post.title }}</h3>
    <p class="post-content">{{ post.content }}</p>
    <div class="post-footer">
      <span class="date">{{ formattedDate }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PostItem',
  props: {
    post: {
      type: Object,
      required: true,
    },
    onClick: {
      type: Function,
      required: true,
    },
  },
  computed: {
    formattedDate() {
      const dateArray = this.post.modifyDate || this.post.registerDate;
      if (!dateArray || dateArray.length < 3) return '날짜 없음';
      const [year, month, day] = dateArray;
      return `${year}. ${String(month).padStart(2, '0')}. ${String(
        day
      ).padStart(2, '0')}`;
    },
  },
  methods: {
    handleClick() {
      this.onClick(this.post.boardId);
    },
  },
};
</script>

<style scoped>
.post-item {
  padding: 18px 0;
  border-bottom: 0.5px solid #edeff2;
  background-color: #fff;
  cursor: pointer;
  transition: transform 0.2s ease;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.post-item:hover {
  transform: scale(1.02);
}

.post-title {
  align-self: stretch;
  color: var(--gr20);
  font-size: 18px;
  font-weight: 600;
  line-height: 140%;
  letter-spacing: -0.36px;
  margin: 8px 0 0 0;
}

.category {
  font-weight: 600;
  color: var(--p10);
  font-size: 12px;
  font-weight: 600;
  line-height: 130%;
  letter-spacing: -0.24px;
  margin-right: 6px;
}

.user {
  color: var(--gr60);
  font-size: 12px;
  font-weight: 500;
  line-height: 130%;
  letter-spacing: -0.24px;
}

.post-content {
  width: 100%;               /* 컨테이너의 너비 */
  height: calc(1.5em * 3);    /* 3줄 높이 제한 */
  line-height: 1.5em;         /* 한 줄의 높이를 지정 */
  overflow: hidden;           /* 넘치는 텍스트 숨기기 */
  text-overflow: ellipsis;    /* 넘치는 텍스트가 생기면 "..."로 표시 */
  display: -webkit-box;       /* Flexbox 유사 레이아웃 */
  -webkit-line-clamp: 3;      /* 3줄로 제한 */
  -webkit-box-orient: vertical; /* 수직 방향 레이아웃 설정 */
  white-space: normal;        /* 텍스트 줄바꿈 허용 */
}

.post-footer {
  color: var(--gr60);
  font-size: 12px;
  font-weight: 500;
  line-height: 130%;
  letter-spacing: -0.24px;
}
</style>
