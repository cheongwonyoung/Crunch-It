<template>
    <div class="container" v-if="!authcodeChecked || !authcodeSended || !findPasswordFlag">
        <headerB title="비밀번호 찾기" @back="handleClose" />
        <div class="input-container" style="margin-top: 80px">
            <div class="input-title">이메일 인증</div>
            <div class="input-flex">
                <input
                    type="email"
                    class="input-short"
                    placeholder="이메일 입력"
                    v-model="email"
                    @blur="emailCheck"
                    :disabled="isEmailInputDisabled" />
                <button
                    type="button"
                    class="certification-btn"
                    @click="startTimer"
                    :disabled="emailError || email === ''">
                    {{ buttonText }}
                </button>
            </div>
            <div class="input-flex">
                <input
                    type="text"
                    class="input-short"
                    placeholder="인증번호 입력"
                    v-model="authcode"
                    :disabled="!authcodeSended" />
                <button
                    v-if="authcodeSended"
                    type="button"
                    class="certification-btn"
                    @click="checkAuthCode"
                    :disabled="authCheckBtnDisabled">
                    인증 확인
                </button>
            </div>
            <span v-if="emailError && email" class="error-text">존재하지 않는 이메일입니다.</span>
        </div>
        <buttonA :disabled="isDisabled" @onClick="findPassword">비밀번호 찾기</buttonA>
    </div>
    <div class="container" v-else>
        <headerB title="새로운 비밀번호 입력" @back="handleClose" />
        <div class="input-container" style="margin-top: 80px">
            <div class="input-title">새로운 비밀번호</div>
            <input type="password" class="input-long" placeholder="비밀번호 입력" v-model="password" />
            <input type="password" class="input-long" placeholder="비밀번호 확인" v-model="passwordCheck" />
            <span v-if="passwordError" class="error-text">비밀번호가 일치하지 않습니다.</span>
        </div>
        <buttonA :disabled="isPasswordDisabled" @onClick="changePassword">비밀번호 변경</buttonA>
    </div>
</template>
<script>
    import headerB from "@/components/HeaderB.vue";
    import buttonA from "@/components/ButtonA.vue";
    import apiClient from "@/axios";
    export default {
        components: {
            headerB,
            buttonA,
        },
        data() {
            return {
                email: "",
                authcode: "",
                emailError: true,
                authcodeSended: false,
                authcodeChecked: false,
                isTimerRunning: false,
                findPasswordFlag: false,
                password: "",
                passwordCheck: "",
            };
        },
        computed: {
            isDisabled() {
                return !this.authcodeChecked;
            },
            isPasswordDisabled() {
                return !this.password || !this.passwordCheck || this.passwordError;
            },
            isEmailInputDisabled() {
                return this.authcodeSended;
            },
            buttonText() {
                return this.isTimerRunning ? `${Math.floor(this.timeLeft / 60)}:${this.timeLeft % 60}` : "인증번호";
            },
            authSendBtnDisabled() {
                return this.emailError;
            },
            authCheckBtnDisabled() {
                return this.authcodeChecked;
            },
            passwordError() {
                // 비밀번호와 비밀번호 확인이 일치하는지 확인
                return this.password !== "" && this.passwordCheck !== "" && this.password !== this.passwordCheck;
            },
        },
        methods: {
            changePassword() {
                apiClient
                    .post("/auth/changePassword", {
                        email: this.email,
                        password: this.password,
                    })
                    .then((res) => {
                        if (res.data.code == 200) {
                            alert("비밀번호가 변경되었습니다.");
                            this.$router.push("/login");
                        }
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            },
            findPassword() {
                this.findPasswordFlag = true;
            },
            handleClose() {
                this.$router.push("/login");
            },
            getAuthCode() {
                apiClient
                    .post("/auth/sendAuthCode", {
                        email: this.email,
                        isForSignUp: false,
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            },
            checkAuthCode() {
                const params = {
                    email: this.email,
                    authCode: this.authcode,
                };
                apiClient
                    .get("/auth/checkAuthCode", { params })
                    .then((res) => {
                        if (res.status == 200) {
                            this.authcodeChecked = true;
                        }
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            },
            emailCheck() {
                const params = { email: this.email };
                apiClient
                    .get("/auth/checkEmail", {
                        params,
                    })
                    .then((res) => {
                        this.emailError = res.data["code"] != 422;
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            },
            async startTimer() {
                const params = { email: this.email };
                try {
                    const res = await apiClient.get("/auth/checkEmail", { params });
                    this.emailError = res.data["code"] != 422;

                    // API 요청이 완료된 후 실행할 코드
                } catch (error) {
                    console.error("API 요청 중 오류 발생:", error);
                }
                if (this.emailError) return;
                if (!this.isTimerRunning) {
                    this.isTimerRunning = true;
                    this.timeLeft = 180;
                    this.authcodeSended = true;
                    this.getAuthCode();
                    const timer = setInterval(() => {
                        if (this.timeLeft > 0) {
                            this.timeLeft--;
                        } else {
                            clearInterval(timer);
                            this.isTimerRunning = false;
                        }
                    }, 1000);
                }
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
        margin-bottom: 10px; /* 입력 필드 간 간격 추가 */
    }

    .input-flex {
        display: flex;
        align-items: center; /* 세로 정렬 */
        justify-content: space-between; /* 양 끝으로 요소 배치 */
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
    .certification-btn:disabled {
        width: 93px;
        height: 56px;
        background: #cce0ff;
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
    .input-container {
        width: 335px;
        flex-shrink: 0;
        margin-bottom: 20px;
        position: relative;
    }
    .error-text {
        color: #f00;
        padding-left: 5px;
        font-family: Pretendard;
        font-style: normal;
        font-weight: 400;
        line-height: 100%; /* 12px */
        font-size: 12px;
        position: absolute; /* 위치를 절대적으로 설정하여 요소가 레이아웃을 밀지 않음 */
        top: 90%; /* 입력 필드 바로 아래에 위치 */
        left: 0;
        margin-top: 10px;
    }
    .correct-text {
        color: rgb(8, 0, 255);
        padding-left: 5px;
        font-family: Pretendard;
        font-style: normal;
        font-weight: 400;
        line-height: 100%; /* 12px */
        font-size: 12px;
        position: absolute; /* 위치를 절대적으로 설정하여 요소가 레이아웃을 밀지 않음 */
        top: 90%; /* 입력 필드 바로 아래에 위치 */
        left: 0;
        margin-top: 10px;
    }
</style>
