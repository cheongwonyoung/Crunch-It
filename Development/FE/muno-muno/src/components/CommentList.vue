<template>
  <div class="comment-list">
    <h4>댓글 {{ comments.length }}개</h4>
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
              <span class="writer">{{ comment.writerId }}</span>

              <div class="settings-menu">
                <i class="fas fa-ellipsis-v" @click="toggleCommentSettingsMenu(index)"></i>
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
                  <span class="writer">{{ reply.writerId }}</span>

                  <div class="settings-menu">
                    <i class="fas fa-ellipsis-v" @click="toggleReplySettingsMenu(reply.replyId)"></i>
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
  //background-color: #f9f9f9;
}

.comment-list-items {
  list-style: none;
  padding-left: 0;
}

.comment-item {
  border-bottom: 1px solid #eee;
  padding: 12px 0;
}

.comment-wrapper {
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 8px;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  font-size: 14px;
  color: #333;
  font-weight: bold;
}

.comment-content p {
  margin: 5px 0;
  font-size: 14px;
  color: #333;
}

.comment-actions {
  margin-top: 10px;
  display: flex;
  gap: 10px;
}

.comment-actions .reply-button {
  font-size: 12px;
  background-color: #f5f5f5;
  color: #555;
  border: none;
  cursor: pointer;
  padding: 5px;
}

.comment-actions .reply-button:hover {
  background-color: #e0e0e0;
}

.likes {
  color: #888;
  font-size: 12px;
  display: flex;
  align-items: center;
}
.comment-content .date {
  display: block;
  font-size: 12px; /* 작은 글씨로 날짜 표시 */
  color: #999;
  margin-top: 5px;
}

/* 답글 리스트 스타일 */
.reply-list {
  margin-top: 10px;
  padding-left: 10px;
  //border-left: 2px solid #ddd;
  list-style:none;
}

.reply-item {
  margin-top: 5px;
  list-style-type: none;
  position: relative;
}

.reply-wrapper {
  background-color: #f5f5f5;
  padding: 10px;
  border-radius: 8px;
}

.reply-header {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #555;
  font-weight: bold;
}

.reply-content {
  margin-top: 5px;
  font-size: 13px;
  color: #666;
}

.reply-content .date {
  display: block;
  font-size: 11px;
  color: #999;
  margin-top: 3px;
}


.reply-actions {
  display: flex;
  gap: 10px;
  margin-top: 5px;
}

.reply-actions .reply-button {
  font-size: 12px;
  background-color: #f5f5f5;
  color: #555;
  border: none;
  cursor: pointer;
  padding: 5px;
}

.reply-actions .reply-button:hover {
  background-color: #e0e0e0;
}
/* 답글 앞에 기호 추가 */
.reply-item:before {
  content: '└'; /* 답글 앞에 기호 추가 */
  position: absolute;
  left: -15px; /* 기호 위치 조정 */
  top: 0;
  color: #ccc; /* 기호 색상 */
}

/* 답글 입력 영역 스타일 */
.reply-input-wrapper {
  margin-top: 10px;
  padding: 10px;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 14px;
  display: flex;
  flex-direction: column;
}

.reply-input textarea {
  width: 80%;
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

.reply-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.submit-reply-btn {
  background-color: #007bff;
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

.reply-input-wrapper .options {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
  font-size: 12px;
  color: #888;
}

.reply-input-wrapper .options span {
  margin-right: 10px;
}

.reply-input-wrapper .options i {
  font-size: 14px;
  color: #888;
  margin-right: 5px;
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
/* 수정 모드 스타일 */
.edit-comment {
  margin-top: 10px;
}

.edit-comment textarea {
  width: 90%;
  padding: 10px;
  border-radius: 8px;
  border: 1px solid #ddd;
  resize: none;
  font-size: 14px;
  margin-bottom: 10px;
}

.edit-actions {
  display: flex;
  gap: 10px;
}

.submit-edit-btn, .cancel-edit-btn {
  padding: 5px 10px;
  font-size: 12px;
  border-radius: 5px;
  border: 1px solid #ddd;
  cursor: pointer;
}

.submit-edit-btn {
  background-color: #007bff;
  color: white;
}

.cancel-edit-btn {
  background-color: #f5f5f5;
}

.submit-edit-btn:hover {
  background-color: #0056b3;
}

.cancel-edit-btn:hover {
  background-color: #e0e0e0;
}


</style>
