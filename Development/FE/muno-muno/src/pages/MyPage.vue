<template>
  <div class="mypage">
    <HeaderB title="마이페이지" @back="goBack" />

    <!-- 프로필 섹션 -->
    <div class="profile-section">
      <div class="profile-image-container">
        <img
          :src="profileUrl ? profileUrl : require('@/assets/profile.svg')"
          alt="프로필 이미지"
          class="profile-image"
        />
        <button class="edit-icon" @click="showProfileModal">
          <img src="@/assets/edit.svg" alt="프로필 수정" />
        </button>
      </div>

      <!-- 사용자 정보 섹션 -->
      <div class="user-info">
        <div class="user-detail">
          <span class="user-q">닉네임</span>
          <span class="user-a">{{
            user.nickname || '닉네임을 등록해주세요'
          }}</span>
        </div>
        <div class="user-detail">
          <span class="user-q">생년월일</span>
          <span class="user-a">{{
            user.birthDate || '생년월일을 등록해주세요'
          }}</span>
        </div>
        <div class="user-detail">
          <span class="user-q">휴대폰 번호</span>
          <span class="user-a">{{
            user.phoneNumber || '휴대폰 번호를 등록해주세요'
          }}</span>
        </div>
        <div class="user-detail">
          <span class="user-q">이메일 주소</span>
          <span class="user-a">{{
            user.email || '이메일 주소를 등록해주세요'
          }}</span>
        </div>
      </div>
    </div>

    <!-- 프로필 수정 버튼 -->
    <ButtonA class="profile-button" @click="goToEditPage">프로필 수정</ButtonA>

    <!-- 프로필 모달 컴포넌트 -->
    <ProfileModal
      v-if="isProfileModalVisible"
      @close="isProfileModalVisible = false"
      :isVisible="isProfileModalVisible"
    />
  </div>
</template>

<script>
import HeaderB from '@/components/HeaderB.vue';
import ButtonA from '@/components/ButtonA.vue';
import { useUserStore } from '@/stores/userStore';
import { mapActions, mapState } from 'pinia';
import apiClient from '@/axios';
import ProfileModal from '@/components/ProfileModal.vue';

export default {
  name: 'MyPage',
  components: {
    HeaderB,
    ButtonA,
    ProfileModal,
  },
  computed: {
    ...mapState(useUserStore, ['userInfo']),
  },
  data() {
    return {
      user: {
        nickname: '',
        birthDate: '',
        phoneNumber: '',
        email: '',
      },
      profileUrl: '',
      isProfileModalVisible: false,
    };
  },
  methods: {
    ...mapActions(useUserStore, ['setUserInfo']),
    goBack() {
      this.$router.push('/');
    },
    // 프로필 수정 페이지로 이동
    goToEditPage() {
      this.$router.push({
        name: 'MyPageEdit',
      });
    },
    fetchUserProfile() {
      apiClient
        .get('/mypage/userInfo')
        .then((response) => {
          const data = response.data.data;
          this.user.email = data.email;
          this.user.birthDate = data.birth;
          this.user.nickname = data.nickname;
          this.user.phoneNumber = data.phone_number;
          this.profileUrl = data.profile_url;
          this.setUserInfo({
            nickname: data.nickname,
            profileUrl: data.profileUrl,
          });
        })
        .catch((error) => {
          console.error('Error fetching user profile:', error);
        });
    },
    showProfileModal() {
      this.isProfileModalVisible = true;
    },
  },
  mounted() {
    this.fetchUserProfile();
  },
};
</script>

<style scoped>
.mypage {
  padding: 0 25px;
  background-color: var(--gr100);
  height: 100vh;
}

.profile-section {
  margin-top: 84px;
}

.profile-image-container {
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}

.profile-image {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  margin-bottom: 40px;
}

.edit-icon {
  position: absolute;
  bottom: 0;
  right: 0;
  background-color: var(--gr100);
  border: none;
  cursor: pointer;
}

.user-info {
  margin-top: 20px;
}

.user-detail {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 0;
}

.user-q {
  color: var(--gr20);
  font-size: 16px;
  font-weight: 600;
}

.user-a {
  color: var(--gr50);
  font-size: 16px;
  font-weight: 400;
  text-align: right;
}
</style>
