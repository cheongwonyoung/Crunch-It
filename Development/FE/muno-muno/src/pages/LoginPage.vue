<template>
    <div class="container">
        <img src="@/assets/logo.svg" alt="muno logo" class="logo" />
        <input type="email" id="id-input" placeholder="아이디 입력" v-model="email" />
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
    import { useUserStore } from "@/stores/userStore";
    import { mapActions } from "pinia";
    export default {
        data() {
            return {
                email: "",
                password: "",
            };
        },
        methods: {
            ...mapActions(useUserStore, ["setUserInfo"]),
            async login() {
                if (this.email === "" || this.password === "") {
                    return;
                }
                try {
                    const res = await apiClient.post("/auth/login", {
                        email: this.email,
                        password: this.password,
                    });
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
                    } else {
                        console.error("Authorization 헤더가 존재하지 않거나 잘못된 형식입니다.");
                    }
                } catch (error) {
                    console.log(error);
                }
                try {
                    const res = await apiClient.get("/mypage/userInfo");
                    const nickname = res.data.data.nickname;
                    const profileUrl = res.data.data.profile_url;
                    this.setUserInfo({ nickname: nickname, profileUrl: profileUrl });
                } catch (error) {
                    console.log(error);
                }
                this.$router.push("/");
            },
        },
    };
</script>

<style>
    .container {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 100vh;
        box-sizing: border-box;
    }

    .logo {
        height: 23px;
        margin-bottom: 60px;
    }

    input {
        width: 335px;
        height: 56px;
        box-sizing: border-box;
        background: var(--gr100);
        border: 0.5px solid var(--gr60);
        border-radius: 12px;
        padding: 0 0 0 16px;
        font-size: 16px;
        color: var(--gr20);
        margin-bottom: 12px;
    }

    ::placeholder {
        color: #d6dae0;
        font-weight: 500;
        font-size: 16px;
    }

    .button {
        width: 335px;
        height: 56px;
        background: var(--p10);
        border-radius: 12px;
        border: none;
        cursor: pointer;
        font-weight: 600;
        font-size: 16px;
        color: var(--gr100);
        display: flex;
        justify-content: center;
        align-items: center;
        margin-bottom: 20px;
    }

    .link-container {
        display: flex;
        justify-content: space-between;
        width: 209px;
        font-weight: 500;
        font-size: 13px;
        color: #8892a0;
        margin-bottom: 130px;
    }

    .router-link {
        text-decoration: none;
        color: var(--gr60);
    }

    .router-link:hover {
        color: var(--p10);
    }

    .social-login-container {
        display: flex;
        justify-content: space-around;
        width: 210px;
    }

    .sns-login {
        font-weight: 500;
        font-size: 12px;
        color: #8892a0;
    }

    .login-img {
        width: 50px;
        height: 50px;
        border-radius: 50%;
    }

    .sns-login-container {
        display: flex;
        align-items: center;
        width: 100%;
        margin-bottom: 20px;
    }

    .separator {
        flex: 1;
        height: 1px;
        background-color: #d6dae0;
        margin: 0 10px;
    }
</style>
