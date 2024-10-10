<template>
    <div class="limit-setting-page">
        <HeaderB title="" @back="goBack" />
        <div class="content">
            <img src="@/assets/step3.svg" alt="Step 3" class="step-icon" />
            <h1>한 달 지출 한도를<br />설정해 주세요</h1>
            <div class="spending-limit">
                <span class="limit-text">한 달 지출 한도</span>
                <div class="amount">{{ formattedLimit }}원</div>
            </div>
            <input
                type="range"
                v-model="limit"
                :max="income"
                step="1000"
                class="slider"
                ref="slider"
                @input="updateSliderBackground" />
            <div class="slider-labels">
                <span>0%</span>
                <span>50%</span>
                <span>100%</span>
            </div>
            <div class="info-box">
                <img src="@/assets/pig.svg" alt="Pig Icon" class="pig-icon" />
                <span class="info-text">지출 한도를 정해 예산을 체계적으로 관리하세요</span>
            </div>
        </div>
        <ButtonA @click="handleStart" class="start-button"> 시작하기 </ButtonA>
    </div>
</template>

<script>
    import ButtonA from "@/components/ButtonA.vue";
    import HeaderB from "@/components/HeaderB.vue";
    import apiClient from "@/axios";

    export default {
        components: {
            HeaderB,
            ButtonA,
        },
        data() {
            return {
                income: 500000,
                limit: 0,
            };
        },
        computed: {
            formattedLimit() {
                return this.limit.toLocaleString();
            },
        },
        methods: {
            async handleStart() {
                console.log("Saving limit:", this.limit);

                const res = await apiClient.put("/onboarding/updateOnBoarding", {
                    salary: this.income,
                    outcome_limit: this.limit,
                    md_user_id: 1, // 하드코딩
                });
                try {
                    if (res.data.result !== "success") {
                        alert("오류가 발생했습니다.");
                        this.$router.push("/login");
                    }
                } catch (err) {
                    console.log(err);
                    this.$router.push("/login");
                }
                this.$router.push("/");
            },
            goBack() {
                this.$router.push("/incomesetting");
            },
            updateSliderBackground() {
                const percentage = (this.limit / this.income) * 100;
                const slider = this.$refs.slider;
                if (slider) {
                    slider.style.background = `linear-gradient(to right, var(--p10) ${percentage}%, var(--gr70) ${percentage}%)`;
                }
            },
        },
        mounted() {
            this.income = Number(this.$route.query.income) || this.income;
            this.updateSliderBackground();
        },
    };
</script>

<style scoped>
    .limit-setting-page {
        top: 88px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        padding: 0 20px;
        text-align: left;
        position: relative;
    }

    .content {
        padding: 0;
        margin: 0;
    }

    .step-icon {
        display: block;
        margin-bottom: 20px;
        width: auto;
        height: auto;
    }

    h1 {
        color: var(--gr30);
        font-size: 26px;
        font-weight: 600;
        line-height: 150%;
        margin: 20px 0;
    }

    .spending-limit {
        text-align: center;
        margin: 40px 0;
    }

    .limit-text {
        color: var(--gr40);
        font-size: 16px;
        font-weight: 400;
        line-height: 100%;
    }

    .spending-limit .amount {
        color: var(--gr20);
        font-size: 30px;
        font-weight: 600;
        line-height: 100%;
        margin-top: 8px;
    }

    .slider {
        -webkit-appearance: none;
        appearance: none;
        width: 325px;
        height: 8px;
        border-radius: 6px;
        background: var(--gr70);
        margin: 20px auto;
        display: block;
    }

    .slider::-webkit-slider-thumb {
        -webkit-appearance: none;
        appearance: none;
        background: var(--gr100);
        border-radius: 50%;
        box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.28);
        cursor: pointer;
        width: 32px;
        height: 32px;
    }

    .slider-labels {
        display: flex;
        justify-content: space-between;
        color: var(--gr40);
        font-size: 12px;
        font-weight: 400;
    }

    .info-box {
        display: flex;
        align-items: center;
        background-color: var(--gr80);
        border-radius: 8px;
        padding: 11px 15px;
        margin-top: 30px;
    }

    .info-text {
        color: var(--gr40);
        font-size: 14px;
        font-weight: 500;
    }

    .pig-icon {
        width: 24px;
        height: 24px;
        margin-right: 8px;
    }
</style>
