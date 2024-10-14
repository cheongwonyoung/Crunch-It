<template>
  <div class="comment-list">
<!--    <h4>댓글 {{ totalCommentsAndReplies }}개</h4>-->
    <ul v-if="comments.length > 0" class="comment-list-items">
      <li v-for="(comment, index) in comments" :key="comment.commentId" class="comment-item">

        <!-- 댓글 내용 -->
        <div class="comment-wrapper">

          <!-- 수정 중인 상태에서는 textarea 표시 -->
          <div v-if="editIndex === index" class="edit-comment">
            <textarea v-model="editContent" class="edit-textarea"></textarea>
            <div class="edit-actions">
              <button @click="updateComment(comment.commentId, editContent)" class="submit-edit-btn">저장</button>
              <button @click="cancelEdit" class="cancel-edit-btn">취소</button>
            </div>
          </div>

          <!-- 수정 중이 아닐 때 일반 댓글 표시 -->
          <div v-else>
            <div class="comment-header">
<!--              writerId가 아닌 닉네임이 뜨도록 해야함-->
              <span class="writer">{{ comment.nickname }}</span>

              <div class="settings-menu">
                <img src="@/assets/dots-vertical.svg" alt="dots-vertical" @click="toggleCommentSettingsMenu(index)" class="dots-icon" />
                <div v-if="showCommentSettingsMenuIndex === index" class="dropdown-menu">
                  <ul>
                    <li @click="enableEdit(index, comment.content)">수정</li>
                    <li @click="deleteComment(comment.commentId)">삭제</li>
                  </ul>
                </div>
              </div>


            </div>
            <div class="comment-content">
              <p>{{ comment.content }}</p>
              <span class="date">{{ formatDate(comment.registerDate) }}</span>
            </div>
            <div class="comment-actions">
              <button class="reply-button" @click="toggleReplyInput(index)">답글</button>
            </div>
          </div>

        </div>

        <!-- 답글 입력 영역 및 답글 리스트는 기존대로 -->
        <div v-if="showReplyInputIndex === index" class="reply-input">
          <div class="reply-input-wrapper">
            <textarea v-model="replyContent" rows="3"></textarea>
            <div class="reply-footer">
              <button @click="submitReply(comment.commentId)" class="submit-reply-btn">등록</button>
            </div>
          </div>
        </div>

        <div v-if="getRepliesByCommentId(comment.commentId).length" class="reply-list">
          <ul>
            <li v-for="reply in getRepliesByCommentId(comment.commentId)" :key="reply.replyId" class="reply-item">
              <div v-if="editReplyIndex === reply.replyId" class="edit-reply">
                <textarea v-model="editReplyContent" class="edit-textarea"></textarea>
                <div class="edit-actions">
                  <button @click="updateReply(reply.replyId, editReplyContent)" class="submit-edit-btn">저장</button>
                  <button @click="cancelReplyEdit" class="cancel-edit-btn">취소</button>
                </div>
              </div>
              <div v-else class="reply-wrapper">
                <div class="reply-header">
                  <span class="writer">{{ reply.nickname }}</span>

                  <div class="settings-menu">
                    <img src="@/assets/dots-vertical.svg" alt="dots-vertical" @click="toggleReplySettingsMenu(reply.replyId)" class="dots-icon" />
                    <div v-if="showReplySettingsMenuIndex === reply.replyId" class="dropdown-menu">
                      <ul>
                        <li @click="enableReplyEdit(reply.replyId, reply.content)">수정</li>
                        <li @click="deleteReply(reply.replyId)">삭제</li>
                      </ul>
                    </div>
                  </div>

                </div>
                <div class="reply-content">
                  <p>{{ reply.content }}</p>
                  <span class="date">{{ formatDate(reply.registerDate) }}</span>
                </div>
              </div>
            </li>
          </ul>
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
    },
    replies: {
      type: Array,
      required: true
    }
  },
  mounted() {
    // Emit the correct comment and reply count when the component is mounted
    this.emitCommentAndReplyCount();
  },
  watch: {
    comments() {
      // Emit updated counts whenever comments change
      this.emitCommentAndReplyCount();
    },
    replies() {
      // Emit updated counts whenever replies change
      this.emitCommentAndReplyCount();
    }
  },

  data() {
    return {
      editIndex: null, // 수정 중인 댓글 인덱스
      editContent: '', // 수정할 댓글의 내용
      showCommentSettingsMenuIndex: null, // 댓글의 설정 메뉴를 열기 위한 인덱스
      showReplySettingsMenuIndex: null, // 답글의 설정 메뉴를 열기 위한 인덱스
      showReplyInputIndex: null, // 답글 입력창을 열기 위한 인덱스
      replyContent: '', // 답글 내용
      editReplyIndex: null, // 수정 중인 답글 인덱스
      editReplyContent: '', // 수정할 답글의 내용
    };
  },
  methods: {
    emitCommentAndReplyCount() {
      const commentCount = this.comments.length;
      const replyCount = this.comments.reduce((total, comment) => {
        return total + this.getRepliesByCommentId(comment.commentId).length;
      }, 0);
      this.$emit('update-comment-reply-count', { commentCount, replyCount });
    },
    formatDate(dateArray) {
      if (!dateArray || dateArray.length < 3) return '날짜 없음';
      const [year, month, day] = dateArray;
      return `${year}. ${String(month).padStart(2, '0')}. ${String(day).padStart(2, '0')}`;
    },
    enableEdit(index, content) {
      this.editIndex = index;
      this.editContent = content; // 선택한 댓글의 내용을 입력창에 미리 채움
    },
    cancelEdit() {
      this.editIndex = null; // 수정 모드 취소
      this.editContent = ''; // 입력된 수정 내용을 초기화
      this.showSettingsMenuIndex = null;
    },
    enableReplyEdit(replyIndex, replyContent) {
      this.editReplyIndex = replyIndex;
      this.editReplyContent = replyContent;
    },
    cancelReplyEdit() {
      this.editReplyIndex = null;
      this.editReplyContent = '';
    },
    async updateComment(commentId, content) {
      try {
        await this.$emit('update-comment', { commentId, content });
        this.editIndex = null; // 수정 완료 후 수정 모드 종료
        this.editContent = '';
        this.showCommentSettingsMenuIndex = null; // 설정 메뉴 닫기
      } catch (error) {
        console.error('댓글 수정 중 오류 발생:', error);
      }
    },
    toggleCommentSettingsMenu(index) {
      if (this.showCommentSettingsMenuIndex === index) {
        this.showCommentSettingsMenuIndex = null; // 설정 메뉴 닫기
      } else {
        this.showCommentSettingsMenuIndex = index; // 설정 메뉴 열기
      }
    },
    toggleReplySettingsMenu(index) {
      if (this.showReplySettingsMenuIndex === index) {
        this.showReplySettingsMenuIndex = null; // 설정 메뉴 닫기
      } else {
        this.showReplySettingsMenuIndex = index; // 설정 메뉴 열기
      }
    },


    async deleteComment(commentId) {
      try {
        await this.$emit('delete-comment', commentId);
        this.showCommentSettingsMenuIndex = null; // 설정 메뉴 닫기
      } catch (error) {
        console.error('댓글 삭제 중 오류 발생:', error);
      }
    },
    toggleReplyInput(index) {
      if (this.showReplyInputIndex === index) {
        this.showReplyInputIndex = null; // 답글 입력창 닫기
      } else {
        this.showReplyInputIndex = index; // 해당 댓글의 답글 입력창 열기
      }
    },
    async submitReply(commentId) {
      if (!this.replyContent.trim()) return;

      try {
        await this.$emit('submit-reply', { commentId, content: this.replyContent });
        this.replyContent = ''; // 입력창 초기화
        this.showReplyInputIndex = null; // 답글 입력창 닫기
      } catch (error) {
        console.error('답글 등록 중 오류 발생:', error);
      }
    },
    getRepliesByCommentId(commentId) {
      return this.replies.filter(reply => reply.commentId === commentId);
    },
    async updateReply(replyId, content) {
      try {
        await this.$emit('update-reply', { replyId, content });
        this.editReplyIndex = null;
        this.editReplyContent = '';
        this.showReplySettingsMenuIndex = null; // 설정 메뉴 닫기
      } catch (error) {
        console.error('답글 수정 중 오류 발생:', error);
        this.showReplySettingsMenuIndex = null; // 설정 메뉴 닫기
      }
    },
    async deleteReply(replyId) {
      try {
        await this.$emit('delete-reply', replyId);
        this.showReplySettingsMenuIndex = null; // 설정 메뉴 닫기
      } catch (error) {
        console.error('답글 삭제 중 오류 발생:', error);
        this.showReplySettingsMenuIndex = null; // 설정 메뉴 닫기
      }
    },
  }
};
</script>

<style scoped>
/* 댓글 리스트 스타일 */
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

.comment-wrapper,
.reply-wrapper {
  padding: 10px 0;
  border-radius: 8px;
}

.comment-header,
.reply-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  color: #333;
  margin-bottom: 6px;
}

.writer {
  font-weight: 600; /* Bold for the writer name */
  font-family: 'Pretendard', sans-serif;
  font-size: 14px;
  color: #292D33;
}

.comment-content p,
.reply-content p {
  margin: 5px 0;
  font-family: 'Pretendard', sans-serif;
  font-size: 14px;
  color: #383E47;
  line-height: 1.6;
}

.comment-content .date,
.reply-content .date {
  font-size: 12px;
  color: #909090;
  margin-top: 4px;
}

.comment-actions,
.reply-actions {
  margin-top: 8px;
  display: flex;
  gap: 8px;
}

.comment-actions .reply-button,
.reply-actions .reply-button {
  font-size: 12px;
  color: #007bff;
  border: none;
  background: none;
  cursor: pointer;
}

.comment-actions .reply-button:hover,
.reply-actions .reply-button:hover {
  text-decoration: underline;
}

.reply-input-wrapper {
  margin-top: 10px;
  padding: 10px;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 14px;
}

.reply-input textarea {
  width: 90%;
  padding: 10px;
  border: none;
  resize: none;
  font-size: 14px;
  line-height: 1.5;
  color: #333;
  background-color: #fff;
  border-radius: 5px;
  margin-bottom: 10px;
  box-shadow: inset 0 1px 3px rgba(0,0,0,0.1);
}

.submit-reply-btn {
  background-color: #3E8AFF;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 8px 16px;
  font-size: 14px;
  cursor: pointer;
}

.submit-reply-btn:hover {
  background-color: #0056b3;
}

/* 설정 메뉴 아이콘 */
.settings-menu {
  position: relative;
  display: inline-block;
  cursor: pointer;
}

.settings-menu i {
  font-size: 16px;
  color: rgba(85, 85, 85, 0.78);
}

.dropdown-menu {
  position: absolute;
  top: 20px;
  right: 0;
  width: 50px;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 10;
}

.dropdown-menu ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.dropdown-menu li {
  padding: 10px;
  cursor: pointer;
  font-size: 14px;
  color: #333;
}

.dropdown-menu li:hover {
  background-color: #f0f0f0;
}

.reply-item:before {
  content: none;
  display: none;
}


.reply-item::before,
.reply-item::after {
  content: none;
  display: none;
}

.reply-item {
  padding-left: 0 ;
  border-left: none;
  list-style-type: none ; /* Ensure no bullets or symbols */
}

/* Additional styling for the replies if needed */
.reply-list {
  margin-left: 0; /* Remove any indentation */
  margin-top: 10px;
}

/* Reply content styles */
.reply-content p {
  font-family: 'Pretendard', sans-serif;
  font-size: 14px;
  color: #383E47;
  line-height: 1.6;
}

</style>
