<template>
  <div class="start-modal" v-if="isVisible">
    <div class="modal-overlay" @click="closeModal"></div>
    <div class="modal-content">
      <img src="@/assets/profile.svg" alt="Illustration" class="modal-image" />
      <div class="content">
        <h1>쉽고 재미있는 자산 관리!</h1>
        <p class="description">
          매달 펼쳐지는 자산 문어와 악당 문어의 대결!<br />
          자산 문어와 악당 문어의 대결에 도전하세요.<br />
          이번 달 챔피언이 될 준비가 되셨나요? 🐙
        </p>

        <div class="modal-buttons">
          <button @click="doNotShowAgain" class="secondary-button">
            다시는 안 보기
          </button>
          <button @click="closeModal" class="close-button">닫기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'StartModal',
  props: {
    isVisible: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    closeModal() {
      this.$emit('close');
    },
    doNotShowAgain() {
      localStorage.setItem('startModalShown', 'true');
      this.closeModal();
    },
  },
};
</script>

<style scoped>
.start-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(0, 0, 0, 0.7);
  z-index: 9999;
}

.modal-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
}
.modal-content {
  position: relative;
  background: var(--gr100);
  border-radius: 12px;
  padding: 0;
  width: 334px;
  max-width: none;
  text-align: center;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  z-index: 10000;
  margin: 20px;
}

.modal-image {
  width: 100px;
  height: auto;
  margin-top: 24px;
}

.content {
  padding: 0 24px 28px;
}

h1 {
  color: var(--gr30);
  font-size: 22px;
  font-weight: 550;
  line-height: 150%;
}

.description {
  color: var(--gr40);
  font-size: 15px;
  line-height: 150%;
  margin-bottom: 30px;
}

.modal-buttons {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  gap: 10px;
  margin-top: 26px;
}

.modal-buttons button {
  border-radius: 10px;
  border: none;
  cursor: pointer;
  width: 134px;
  height: 48px;
  font-size: 16px;
  font-weight: 500;
  line-height: 1.2;
}

.secondary-button {
  background: var(--gr70);
  color: var(--gr50);
}

.close-button {
  background: var(--p10);
  color: var(--gr100);
}
</style>
