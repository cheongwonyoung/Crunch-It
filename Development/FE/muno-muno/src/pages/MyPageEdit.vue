<template>
  <div class="mypageedit">
    <HeaderB title="마이페이지 수정" @back="goBack" />

    <!-- 사용자 정보 입력 필드 -->
    <div class="input-section">
      <label>닉네임</label>
      <input
        type="text"
        v-model="editableUser.nickname"
        placeholder="닉네임을 입력해주세요"
      />

      <label>생년월일</label>
      <input
        type="number"
        v-model="editableUser.birthDate"
        placeholder="YYYY.MM.DD"
      />

      <label>휴대폰 번호</label>
      <input
        type="text"
        v-model="editableUser.phoneNumber"
        placeholder="010-1234-5678"
      />

      <label>이메일 주소</label>
      <input
        type="email"
        v-model="editableUser.email"
        placeholder="example@domain.com"
      />
    </div>

    <!-- 수정 완료 버튼 -->
    <ButtonA class="save-button" :disabled="!isFormValid" @click="saveChanges">
      수정 완료
    </ButtonA>
  </div>
</template>

<script>
import HeaderB from '@/components/HeaderB.vue';
import ButtonA from '@/components/ButtonA.vue';
import axios from 'axios';

export default {
  name: 'MyPageEdit',
  components: {
    HeaderB,
    ButtonA,
  },
  props: {
    user: {
      type: Object,
      default: () => ({
        nickname: '',
        birthDate: '',
        phoneNumber: '',
        email: '',
      }),
    },
  },
  data() {
    return {
      editableUser: { ...this.user }, // props로 받은 user를 data로 복사
    };
  },
  computed: {
    isFormValid() {
      // 모든 필드가 입력되었을 경우에만 버튼 활성화
      return (
        this.editableUser.nickname &&
        this.editableUser.birthDate &&
        this.editableUser.phoneNumber &&
        this.editableUser.email
      );
    },
  },
  methods: {
    goBack() {
      this.$router.push('/mypage');
    },
    saveChanges() {
      // 백엔드로 수정된 데이터 전송
      axios
        .post('/api/user/profile/update', this.editableUser)
        .then(() => {
          alert('프로필이 수정되었습니다.');
          this.goBack();
        })
        .catch((error) => {
          console.error('Error updating profile:', error);
        });
    },
  },
};
</script>

<style scoped>
.mypageedit {
  padding: 0 20px;
  background-color: var(--gr100);
  height: 100vh;
}

.input-section {
  margin-top: 84px;
}

label {
  color: var(--gr40);
  font-size: 14px;
  font-weight: 500;
  line-height: 100%;
  margin-left: 2px;
}

input {
  width: 100%;
  height: 56px;
  margin: 8px 0 28px 0;
  padding: 0 20px;
  border-radius: 12px;
  border: 1px solid var(--gr60);
  font-size: 16px;
  background-color: var(--gr90);
}

input::placeholder {
  color: var(--gr50);
  font-size: 16px;
  font-weight: 400;
  line-height: 100%;
}

.save-button {
  position: fixed;
  bottom: 34px;
  left: 50%;
  transform: translateX(-50%);
  width: calc(100% - 40px);
}
</style>
