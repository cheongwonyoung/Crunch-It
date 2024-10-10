<template>
  <div class="modal-background" v-if="isVisible">
    <div class="modal-content">
      <div class="action-group">
        <button class="modal-option" @click="selectFromAlbum">
          앨범에서 선택
        </button>
        <hr class="divider" />
        <button class="modal-option delete" @click="deleteProfilePhoto">
          프로필 사진 삭제
        </button>
      </div>
      <div class="close-group">
        <button class="modal-option close" @click="closeModal">닫기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    isVisible: {
      type: Boolean,
      required: true,
    },
  },
  methods: {
    selectFromAlbum() {
      alert('앨범에서 사진을 선택합니다.');
    },
    deleteProfilePhoto() {
      alert('프로필 사진이 삭제되었습니다.');
    },
    closeModal() {
      this.$emit('close');
    },
  },
};
</script>

<style scoped>
.modal-background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  width: 335px;
  text-align: center;
  position: fixed;
  bottom: 34px;
  left: 50%;
  transform: translateX(-50%);
  width: calc(100% - 40px);
}

.action-group {
  background-color: var(--gr80);
  border-radius: 12px;
  overflow: hidden;
  margin-bottom: 10px;
}

.modal-option {
  color: var(--p10);
  width: 100%;
  padding: 20px 0;
  border: none;
  background: none;
  cursor: pointer;
  font-size: 16px;
  font-weight: 400;
}

.delete {
  color: #ff3e41;
}

.close {
  color: var(--p10);
  text-align: center;
  font-size: 16px;
  font-weight: 600;
  line-height: 100%;
  border-radius: 12px;
  background: var(--gr100);
  width: 335px;
  height: 56px;
  border: none;
  cursor: pointer;
}

.divider {
  border: none;
  margin: 0;
  border-top: 1px solid var(--gr60);
}
</style>
