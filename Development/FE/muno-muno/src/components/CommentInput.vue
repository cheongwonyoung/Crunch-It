<template>
  <div class="message-input">
    <!-- 메시지 입력 필드 -->
    <input
        type="text"
        v-model="localNewComment"
        placeholder="댓글을 입력하세요..."
        class="input-field"
        @keyup.enter="onSubmitComment"
    />
    <!-- 메시지 전송 아이콘 -->
    <img
        class="icon-send"
        src="@/assets/send.svg"
        alt="전송"
        @click="onSubmitComment"
    />
  </div>
</template>

<script>
export default {
  props: {
    newComment: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      localNewComment: this.newComment // newComment를 로컬로 복사
    };
  },
  methods: {
    onSubmitComment() {
      if (this.localNewComment.trim()) {
        this.$emit('submitComment', this.localNewComment); // 부모에게 이벤트 전달
        this.localNewComment = ''; // 입력창 초기화
      }
    }
  },
  watch: {
    newComment(newVal) {
      this.localNewComment = newVal; // 부모의 값이 변경되면 로컬 변수도 업데이트
    }
  }
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

.input-field {
  width: 278px;
  height: 40px;
  padding: 0 16px;
  border: none;
  border-radius: 40px;
  background: var(--gr80);
  font-size: 14px;
  outline: none;
  margin: 0 10px 0 8px;
  box-sizing: border-box;
}

.icon-send {
  width: 24px;
  height: 24px;
  cursor: pointer;
}
</style>
