<template>
    <div class="mypageedit">
        <HeaderB title="마이페이지 수정" @back="goBack" />

        <!-- 사용자 정보 입력 필드 -->
        <div class="input-section">
            <label>닉네임</label>
            <div class="input-container">
                <input type="text" v-model="editableUser.nickname" :placeholder="user.nickname" @blur="nicknameCheck" />
                <span v-if="nicknameError" class="error-text2">이미 사용중인 닉네임입니다.</span>
                <span v-if="!nicknameError && editableUser.nickname !== ''" class="correct-text2"
                    >사용 가능한 닉네임입니다.</span
                >
            </div>

            <label>생년월일</label>
            <input
                type="text"
                v-model="editableUser.birthDate"
                :placeholder="user.birthDate"
                maxlength="10"
                @input="formatBirthDate" />

            <label>휴대폰 번호</label>
            <input
                type="text"
                v-model="editableUser.phoneNumber"
                :placeholder="user.phoneNumber"
                maxlength="13"
                @input="formatPhoneNumber" />

            <label>이메일 주소</label>
            <input type="email" v-model="editableUser.email" :placeholder="user.email" disabled="true" />
        </div>

        <!-- 수정 완료 버튼 -->
        <ButtonA class="save-button" :disabled="!isFormValid" @onClick="saveChanges"> 수정 완료 </ButtonA>
    </div>
</template>

<script>
    import HeaderB from "@/components/HeaderB.vue";
    import ButtonA from "@/components/ButtonA.vue";
    import apiClient from "@/axios";

    export default {
        name: "MyPageEdit",
        components: {
            HeaderB,
            ButtonA,
        },
        data() {
            return {
                editableUser: {
                    email: "",
                    nickname: "",
                    phoneNumber: "",
                    birthDate: "",
                }, // props로 받은 user를 data로 복사
                user: {
                    email: "",
                    nickname: "",
                    phoneNumber: "",
                    birthDate: "",
                },
                nicknameError: false,
            };
        },
        created() {
            this.fetchUserProfile();
        },
        computed: {
            isFormValid() {
                if (this.nicknameError) return false;
                if (this.editableUser.nickname && !this.nicknameError) return true;
                // 모든 필드가 입력되었을 경우에만 버튼 활성화
                return this.editableUser.birthDate || this.editableUser.phoneNumber;
            },
        },
        methods: {
            goBack() {
                this.$router.push("/mypage");
            },
            fetchUserProfile() {
                apiClient
                    .get("/mypage/userInfo")
                    .then((response) => {
                        const data = response.data.data;
                        this.user.email = data.email;
                        this.user.birthDate = data.birth;
                        this.user.nickname = data.nickname;
                        this.user.phoneNumber = data.phone_number;
                        this.profileUrl = data.profile_url;
                        console.log(response.data);
                    })
                    .catch((error) => {
                        console.error("Error fetching user profile:", error);
                    });
            },
            saveChanges() {
                // 백엔드로 수정된 데이터 전송
                const payload = {
                    nickname: this.editableUser.nickname == "" ? this.user.nickname : this.editableUser.nickname,
                    birth: this.editableUser.birthDate == "" ? this.user.birthDate : this.editableUser.birthDate,
                    phone_number:
                        this.editableUser.phoneNumber == "" ? this.user.phoneNumber : this.editableUser.phoneNumber,
                };
                console.log(payload);
                apiClient
                    .put("/mypage/updateUserInfo", payload)
                    .then(() => {
                        alert("프로필이 수정되었습니다.");
                        this.goBack();
                    })
                    .catch((error) => {
                        console.error("Error updating profile:", error);
                    });
            },
            nicknameCheck() {
                const params = { nickname: this.editableUser.nickname };
                apiClient
                    .get("/auth/checkNickname", {
                        params,
                    })
                    .then((res) => {
                        this.nicknameError = res.data["code"] == 422;
                    });
            },
            formatPhoneNumber() {
                // 입력 형식: 000-0000-0000
                let phoneNumber = this.editableUser.phoneNumber.replace(/[^0-9]/g, ""); // 숫자만 남기기
                if (phoneNumber.length > 3 && phoneNumber.length <= 7) {
                    phoneNumber = `${phoneNumber.slice(0, 3)}-${phoneNumber.slice(3)}`;
                } else if (phoneNumber.length > 7) {
                    phoneNumber = `${phoneNumber.slice(0, 3)}-${phoneNumber.slice(3, 7)}-${phoneNumber.slice(7, 11)}`;
                }
                this.editableUser.phoneNumber = phoneNumber; // 포맷된 전화번호로 업데이트
            },
            formatBirthDate() {
                // 입력 형식: 00000-00-00
                let birthDate = this.editableUser.birthDate.replace(/[^0-9]/g, ""); // 숫자만 남기기
                if (birthDate.length > 4 && birthDate.length <= 6) {
                    birthDate = `${birthDate.slice(0, 4)}-${birthDate.slice(4)}`;
                } else if (birthDate.length > 6) {
                    birthDate = `${birthDate.slice(0, 4)}-${birthDate.slice(4, 6)}-${birthDate.slice(6, 8)}`;
                }
                this.editableUser.birthDate = birthDate; // 포맷된 생년월일로 업데이트
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

    .error-text2 {
        color: #f00;
        padding-left: 5px;
        font-family: Pretendard;
        font-style: normal;
        font-weight: 400;
        line-height: 100%; /* 12px */
        font-size: 12px;
        position: absolute; /* 위치를 절대적으로 설정하여 요소가 레이아웃을 밀지 않음 */
        top: 80%; /* 입력 필드 바로 아래에 위치 */
        left: 0;
        margin-top: 2px;
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
        top: 80%; /* 입력 필드 바로 아래에 위치 */
        left: 0;
        margin-top: 2px;
    }

    .input-container {
        width: 335px;
        flex-shrink: 0;
        position: relative;
    }
</style>
