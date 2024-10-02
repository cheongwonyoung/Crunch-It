<template>
    <div class="container">
        <!-- <HeaderComponent title="회원가입" @close="handleClose" /> -->
        <headerX title="회원가입" @close="handleClose" />
        <div class="input-container" style="margin-top: 80px">
            <div class="input-title">닉네임</div>
            <input type="text" class="input-long" placeholder="닉네임 입력" v-model="nickname" @blur="nicknameCheck" />
            <span v-if="nicknameError" class="error-text2">이미 사용중인 닉네임입니다.</span>
            <span v-if="!nicknameError && nickname !== ''" class="correct-text2">사용 가능한 닉네임입니다.</span>
        </div>
        <div class="input-container">
            <div class="input-title">이메일 인증</div>
            <div class="input-flex">
                <input type="email" class="input-short" placeholder="이메일 입력" v-model="email" @blur="emailCheck" />
                <button
                    type="button"
                    class="certification-btn"
                    @click="startTimer"
                    :disabled="emailError || email === ''">
                    {{ buttonText }}
                </button>
            </div>
            <input
                type="number"
                class="input-long"
                placeholder="인증번호 입력"
                v-model="authcode"
                :disabled="!authcodeSended" />
            <span v-if="emailError" class="error-text">이미 사용중인 이메일입니다.</span>
            <span v-if="!emailError && email !== ''" class="correct-text">사용 가능한 이메일입니다.</span>
        </div>
        <div class="input-container">
            <div class="input-title">비밀번호</div>
            <input type="password" class="input-long" placeholder="비밀번호 입력" v-model="password" />
            <input type="password" class="input-long" placeholder="비밀번호 확인" v-model="passwordCheck" />
            <span v-if="passwordError" class="error-text">비밀번호가 일치하지 않습니다.</span>
        </div>

        <div class="input-container">
            <div class="input-title">휴대폰 번호</div>
            <input
                type="text"
                class="input-long"
                placeholder="010-1234-5678"
                v-model="formattedPhoneNum"
                @input="formatPhoneNumber"
                maxlength="13" />
        </div>
        <div class="input-container">
            <div class="input-title">생년월일</div>
            <input type="date" v-model="selectedDate" placeholder="" class="input-short" />
        </div>
        <div class="button-container">
            <button type="button" class="signup-btn" :disabled="isDisabled" @click="signUp">가입하기</button>
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
        data() {
            return {
                email: "",
                authcode: "",
                password: "",
                passwordCheck: "",
                nickname: "",
                phoneNum: "",
                selectedDate: "",
                emailError: false,
                nicknameError: false,
                authcodeSended: false,
                isTimerRunning: false,
                timeLeft: 180,
            };
        },
        computed: {
            authBtnDisabled() {
                return this.emailError;
            },
            isDisabled() {
                // 모든 필드가 채워져 있는지 확인
                return (
                    this.email === "" ||
                    this.authcode === "" ||
                    this.password === "" ||
                    this.passwordCheck === "" ||
                    this.nickname === "" ||
                    this.phoneNum === "" ||
                    this.selectedDate === "" ||
                    this.passwordError || // 비밀번호 확인 오류가 있는 경우 비활성화
                    this.emailError ||
                    this.nicknameError
                );
            },
            passwordError() {
                // 비밀번호와 비밀번호 확인이 일치하는지 확인
                return this.password !== "" && this.passwordCheck !== "" && this.password !== this.passwordCheck;
            },
            formattedPhoneNum: {
                get() {
                    return this.formatAsPhone(this.phoneNum);
                },
                set(value) {
                    this.phoneNum = value.replace(/[^0-9]/g, "").slice(0, 11);
                },
            },
            buttonText() {
                return this.isTimerRunning ? `${Math.floor(this.timeLeft / 60)}:${this.timeLeft % 60}` : "인증번호";
            },
        },
        methods: {
            handleClose() {
                this.$router.push({ name: "Login" });
            },
            getAuthCode() {
                this.authcodeSended = true;
            },
            signUp() {
                apiClient;
            },
            formatPhoneNumber() {
                this.phoneNum = this.phoneNum.replace(/[^0-9]/g, ""); // 숫자만 남김
            },
            formatAsPhone(value) {
                if (value.length <= 3) return value; // 3자리까지는 그대로
                if (value.length <= 7) return `${value.slice(0, 3)}-${value.slice(3)}`; // 000-0000
                return `${value.slice(0, 3)}-${value.slice(3, 7)}-${value.slice(7, 11)}`; // 000-0000-0000
            },
            emailCheck() {
                const params = { email: this.email };
                apiClient
                    .get("/auth/checkEmail", {
                        params,
                    })
                    .then((res) => {
                        this.emailError = res.data["code"] == 422;
                    });
            },
            nicknameCheck() {
                const params = { nickname: this.nickname };
                apiClient
                    .get("/auth/checkNickname", {
                        params,
                    })
                    .then((res) => {
                        this.nicknameError = res.data["code"] == 422;
                    });
            },
            startTimer() {
                if (!this.isTimerRunning) {
                    this.isTimerRunning = true;
                    this.getAuthCode();
                    this.timeLeft = 180;
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
    .input-container {
        width: 335px;
        flex-shrink: 0;
        margin-bottom: 20px;
        position: relative;
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
    .error-text2 {
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
        margin-top: 5px;
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

    .correct-text2 {
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
        margin-top: 5px;
    }
</style>
