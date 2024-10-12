<template>
  <div class="start-modal" v-if="isVisible">
    <div class="start-modal-overlay" @click="closeModal"></div>
    <div class="start-modal-content">
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
            다시 안 보기
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
  z-index: 2000;
}

.start-modal-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
}

.start-modal-content {
  padding: 28px 28px 24px;
  width: 334px;
  height: auto;
  box-sizing: border-box;
  border-radius: 12px;
  max-width: 500px;
  background: var(--gr100);
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  text-align: center;
  z-index: 10000;
}

.modal-image {
  width: 100px;
  height: auto;
  margin-top: 24px;
}

.content {
  padding: 0;
}

h1 {
  color: var(--gr30);
  font-size: 22px;
  font-weight: 550;
  line-height: 1.5;
}

.description {
  color: var(--gr50);
  font-size: 15px;
  line-height: 1.5;
  margin-bottom: 30px;
  font-weight: 400;
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
