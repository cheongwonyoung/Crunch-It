<template>
    <div class="homepage">
        <!-- Status Bar -->
        <div class="status-bar">
            <img src="@/assets/statusBar.svg" alt="Status Bar" class="status-bar-svg" />
        </div>

        <!-- 헤더 -->
        <div class="header">
            <img src="@/assets/munoLogo.svg" alt="Muno 로고" class="logo" />

            <router-link to="/mypage" class="mypage">
                <img src="@/assets/mypage.svg" alt="마이페이지 아이콘" class="mypage-icon" />
            </router-link>
        </div>
        <!-- 문어 레벨 섹션 -->
        <OctopusLevel
            :currentLevel="octopusesLevel[0]"
            :currentOctopus="currentOctopus"
            :levelText="levelText"
            @navigate="navigateToProductRecommendation" />

        <!-- 문어 선택 섹션 -->
        <OctopusSelection :octopuses="octopuses" @select-octopus="selectOctopus" />

        <!-- 지출 금액 섹션 -->
        <ExpenseContainer :expenseAmount="expenseAmount" />

        <!-- TabBar 컴포넌트 사용 -->
        <TabBar />
    </div>
</template>

<script>
    import ExpenseContainer from "@/components/ExpenseContainer.vue";
    import OctopusLevel from "@/components/OctopusLevel.vue";
    import OctopusSelection from "@/components/OctopusSelection.vue";
    import TabBar from "@/components/TabBar.vue";
    import apiClient from "@/axios";
    export default {
        name: "HomeP",
        components: {
            ExpenseContainer,
            OctopusLevel,
            OctopusSelection,
            TabBar,
        },
        data() {
            return {
                expenseAmount: 100000,
                test: 0,
                levelText: "",
                currentOctopus: {},
                octopusesLevel: [1, 1, 1, 1], //예적금, 주식, 펀드, 채권 순
                octopuses: [
                    {
                        id: 1,
                        name: "예적금",
                        levelImages: [
                            require("@/assets/octopus_a1.svg"),
                            require("@/assets/octopus_a2.svg"),
                            require("@/assets/octopus_a3.svg"),
                        ],
                        buttonImage: require("@/assets/button_a.svg"),
                    },
                    {
                        id: 2,
                        name: "주식",
                        levelImages: [
                            require("@/assets/octopus_b1.svg"),
                            require("@/assets/octopus_b2.svg"),
                            require("@/assets/octopus_b3.svg"),
                        ],
                        buttonImage: require("@/assets/button_b.svg"),
                    },
                    {
                        id: 3,
                        name: "펀드",
                        levelImages: [
                            require("@/assets/octopus_c1.svg"),
                            require("@/assets/octopus_c2.svg"),
                            require("@/assets/octopus_c3.svg"),
                        ],
                        buttonImage: require("@/assets/button_c.svg"),
                    },
                    {
                        id: 4,
                        name: "채권",
                        levelImages: [
                            require("@/assets/octopus_d1.svg"),
                            require("@/assets/octopus_d2.svg"),
                            require("@/assets/octopus_d3.svg"),
                        ],
                        buttonImage: require("@/assets/button_d.svg"),
                    },
                ],
            };
        },
        methods: {
            selectOctopus(octopus) {
                const octopusIndex = octopus.id - 1; // 배열 인덱스는 0부터 시작
                const octopusLevel = this.octopusesLevel[octopusIndex] || 1;
                this.currentOctopus = {
                    name: octopus.name,
                    image: octopus.levelImages[octopusLevel - 1],
                };
                this.levelText = `Lv.${octopusLevel} ${octopus.name}`;
            },
            navigateToProductRecommendation() {
                this.$router.push("/product");
            },
            fetchLevelFromBackend() {
                apiClient
                    .get("/home/getUserOctopus")
                    .then((res) => {
                        const userOctopus = res.data.userOctopus;
                        if (userOctopus) {
                            this.octopusesLevel[0] = userOctopus.savingOctopusLevel;
                            this.octopusesLevel[1] = userOctopus.stockOctopusLevel;
                            this.octopusesLevel[2] = userOctopus.fundOctopusLevel;
                            this.octopusesLevel[3] = userOctopus.bondOctopusLevel;
                        }
                        this.setInitialOctopus();
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            },
            setInitialOctopus() {
                const initialOctopus = this.octopuses[0];
                this.selectOctopus(initialOctopus);
            },
            fetchMonthlyOutcome() {
                apiClient
                    .get("/home/getMonthlyOutcome")
                    .then((res) => {
                        const amount = res.data.total_monthly_outcome;
                        this.expenseAmount = amount ? amount : 0;
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            },
        },
        mounted() {
            this.setInitialOctopus();
            this.fetchLevelFromBackend();
            this.fetchMonthlyOutcome();
        },
    };
</script>

<style scoped>
    .homepage {
        background-color: var(--p70);
        overflow: hidden;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        height: 570px;
    }

    .status-bar {
        background-color: var(--p70);
        height: 44px;
        display: flex;
        justify-content: center;
        align-items: center;
        position: fixed;
        top: 0;
        width: 100%;
        z-index: 1000;
    }

    .status-bar-svg {
        width: 375px;
        height: 44px;
    }

    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 22px;
        height: 65px;
        background-color: var(--p70);
        position: fixed;
        top: 44px;
        left: 0;
        width: 100%;
        z-index: 1000;
        box-sizing: border-box;
    }

    .logo {
        width: auto;
        height: 17px;
        margin: 0;
    }

    .mypage-icon {
        width: 24px;
        height: 24px;
    }
</style>
