<template lang="">
    <div class="container">
        <input type="email" id="id-input" placeholder="이메일 입력" v-model="email" />
        <input type="password" id="password-input" placeholder="비밀번호 입력" v-model="password" />

        <button class="button" @click="login">로그인</button>

        <div class="link-container">
            <router-link class="router-link" to="/findPassword">아이디 / 비밀번호 찾기</router-link>
            <span>|</span>
            <router-link class="router-link" to="/signup">회원가입</router-link>
        </div>
        <div class="sns-login-container">
            <div class="separator"></div>
            <span class="sns-login">SNS 계정으로 로그인</span>
            <div class="separator"></div>
        </div>
        <div class="social-login-container">
            <img class="login-img" src="@/assets/kakao.svg" />
            <img class="login-img" src="@/assets/naver.svg" />
            <img class="login-img" src="@/assets/apple.svg" />
        </div>
    </div>
</template>
<script>
    import apiClient from "@/axios";
    export default {
        data() {
            return {
                email: "",
                password: "",
            };
        },
        methods: {
            login() {
                if (this.email === "" || this.password === "") {
                    return;
                }
                apiClient
                    .post("/auth/login", {
                        email: this.email,
                        password: this.password,
                    })
                    .then((res) => {
                        if (res.status != 200) {
                            console.log("Server Error");
                            return;
                        }
                        const authHeader = res.headers["authorization"];
                        console.log(authHeader);
                        if (authHeader && authHeader.startsWith("Bearer ")) {
                            const token = authHeader.substring(7); // 'Bearer ' 이후의 문자열을 추출
                            console.log("JWT Token:", token);
                            localStorage.setItem("JwtToken", token);
                            this.$router.push("/");
                        } else {
                            console.error("Authorization 헤더가 존재하지 않거나 잘못된 형식입니다.");
                        }
                    })
                    .catch((error) => {
                        console.log(error);
                    });
            },
        },
    };
</script>
<style>
    /* 공통 설정 */
    /* 공통 설정 */
    /* 전체 컨테이너 */
    .container {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 100vh;
        box-sizing: border-box;
    }

    /* Input fields */
    input {
        width: 335px;
        height: 56px;
        box-sizing: border-box;
        background: #ffffff;
        border: 0.5px solid #d6dae0;
        border-radius: 12px;
        padding: 0 0 0 16px; /* 텍스트 입력 시 패딩 추가 */
        font-family: "Pretendard", sans-serif;
        font-size: 16px;
        color: #000000; /* 입력 텍스트 색상 */
        margin-bottom: 12px; /* 입력 필드 간 간격 추가 */
    }

    /* Placeholder 스타일 */
    ::placeholder {
        color: #d6dae0; /* Placeholder 색상 */
        font-weight: 500;
        font-size: 16px;
    }

    /* Button */
    .button {
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

    /* 아이디 / 비밀번호 찾기 | 회원가입 */
    .link-container {
        display: flex;
        justify-content: space-between; /* 각 링크 사이에 공간 배분 */
        width: 209px;
        font-family: "Pretendard", sans-serif;
        font-style: normal;
        font-weight: 500;
        font-size: 13px;
        color: #8892a0;
        margin-bottom: 130px; /* 링크 아래 간격 추가 */
    }

    /* 링크 스타일 */
    .router-link {
        text-decoration: none; /* 밑줄 제거 */
        color: #8892a0; /* 링크 색상 */
    }

    .router-link:hover {
        color: #3e8aff; /* 마우스를 올렸을 때 링크 색상 */
    }

    /* 소셜 로그인 아이콘 컨테이너 */
    .social-login-container {
        display: flex;
        justify-content: space-around; /* 각 아이콘 사이에 간격 추가 */
        width: 210px;
    }

    .sns-login {
        font-family: "Pretendard", sans-serif;
        font-style: normal;
        font-weight: 500;
        font-size: 12px;
        text-align: center;

        color: #8892a0;
    }

    .login-img {
        width: 50px;
        height: 50px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 20 10px;
    }
    .sns-login-container {
        display: flex;
        align-items: center; /* 수직 중앙 정렬 */
        width: 100%; /* 전체 너비 사용 */
        margin-bottom: 20px; /* 아래 간격 추가 */
    }

    /* 구분선 */
    .separator {
        flex: 1; /* 남은 공간을 차지하도록 설정 */
        height: 1px; /* 구분선 두께 */
        background-color: #d6dae0; /* 구분선 색상 */
        margin: 0 10px; /* 구분선과 텍스트 사이의 간격 */
    }
</style>
