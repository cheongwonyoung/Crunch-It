<template lang="">
    <div class="container">
        <!-- <HeaderComponent title="회원가입" @close="handleClose" /> -->
        <headerX title="회원가입" @close="handleClose" />
        <div class="input-container" style="margin-top: 80px">
            <div class="input-title">이메일</div>
            <input type="email" class="input-long" placeholder="이메일 입력" v-model="email" />
        </div>
        <div class="input-container">
            <div class="input-title">이메일 인증</div>
            <div class="input-flex">
                <input type="email" class="input-short" placeholder="인증번호 입력" v-model="email" />
                <button type="button" class="certification-btn">인증번호</button>
            </div>
        </div>
        <div class="input-container">
            <div class="input-title">비밀번호</div>
            <input type="password" class="input-long" placeholder="비밀번호 입력" v-model="email" />
            <input type="password" class="input-long" placeholder="비밀번호 확인" v-model="email" />
        </div>
        <div class="input-container">
            <div class="input-title">닉네임</div>
            <input type="text" class="input-long" placeholder="닉네임 입력" v-model="email" />
        </div>
        <div class="button-container">
            <button type="button" class="signup-btn">가입하기</button>
        </div>
    </div>
</template>
<script>
    import apiClient from "@/axios";
    import headerX from "@/components/HeaderX.vue";
    export default {
        components: {
            headerX,
        },
        data() {},
        methods: {
            handleClose() {
                this.$router.push({ name: "Login" });
            },
            signUp() {
                apiClient;
            },
        },
    };
</script>
<style scoped>
    .container {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: flex-start;
        height: 100vh;
        box-sizing: border-box;
    }
    .input-container {
        width: 335px;
        flex-shrink: 0;
        margin-bottom: 20px;
    }
    .input-title {
        color: var(--gray-40, #616b79);
        font-family: Pretendard;
        font-size: 14px;
        font-style: normal;
        font-weight: 600;
        line-height: 100%; /* 14px */
        margin-bottom: 8px;
    }

    .input-long {
        width: 335px;
        height: 56px;
        box-sizing: border-box;
        background: #ffffff;
        border: 0.5px solid #d6dae0;
        border-radius: 12px;
        padding: 0 0 0 20px; /* 텍스트 입력 시 패딩 추가 */
        font-family: "Pretendard", sans-serif;
        font-size: 16px;
        color: #000000; /* 입력 텍스트 색상 */
        margin-bottom: 12px; /* 입력 필드 간 간격 추가 */
    }

    .certification-btn {
        width: 93px;
        height: 56px;
        background: #3e8aff;
        border-radius: 12px;
        border: none; /* 버튼 기본 테두리 제거 */
        cursor: pointer; /* 마우스를 올렸을 때 포인터 모양 변경 */
        font-family: "Pretendard", sans-serif;
        font-style: normal;
        font-weight: 500px;
        font-size: 16px;
        color: #ffffff;
        display: flex;
        justify-content: center; /* 수평 중앙 정렬 */
        align-items: center; /* 수직 중앙 정렬 */
        text-align: center;
        margin-bottom: 12px; /* 입력 필드 간 간격 추가 */
    }

    .signup-btn {
        width: 335px;
        height: 56px;
        background: #3e8aff;
        border-radius: 12px;
        border: none; /* 버튼 기본 테두리 제거 */
        cursor: pointer; /* 마우스를 올렸을 때 포인터 모양 변경 */
        font-family: "Pretendard", sans-serif;
        font-style: normal;
        font-weight: 600;
        font-size: 16px;
        color: #ffffff;
        display: flex;
        justify-content: center; /* 수평 중앙 정렬 */
        align-items: center; /* 수직 중앙 정렬 */
        text-align: center;
        margin-bottom: 20px; /* 버튼 아래 간격 추가 */
    }

    .signup-btn:disabled {
        width: 335px;
        height: 56px;
        background: #cce0ff;
        border-radius: 12px;
        border: none; /* 버튼 기본 테두리 제거 */
        cursor: pointer; /* 마우스를 올렸을 때 포인터 모양 변경 */
        font-family: "Pretendard", sans-serif;
        font-style: normal;
        font-weight: 600;
        font-size: 16px;
        color: #ffffff;
        display: flex;
        justify-content: center; /* 수평 중앙 정렬 */
        align-items: center; /* 수직 중앙 정렬 */
        text-align: center;
        margin-bottom: 20px; /* 버튼 아래 간격 추가 */
    }

    .input-short {
        width: 232px;
        height: 56px;
        flex-shrink: 0;
        border-radius: 12px;
        border: 0.5px solid var(--gray-60, #d6dae0);
        background: var(--gray-100, #fff);
        padding: 0 0 0 20px; /* 텍스트 입력 시 패딩 추가 */
        font-family: "Pretendard", sans-serif;
        font-size: 16px;
        color: #000000; /* 입력 텍스트 색상 */
        margin-bottom: 12px; /* 입력 필드 간 간격 추가 */
    }

    .input-flex {
        display: flex;
        align-items: center; /* 세로 정렬 */
        justify-content: space-between; /* 양 끝으로 요소 배치 */
    }
    .button-container {
        margin-top: 30px;
    }
</style>
