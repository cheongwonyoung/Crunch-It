import { defineStore } from "pinia";

export const useUserStore = defineStore("userStore", {
    state: () => ({
        userInfo: {
            nickname: null,
            profileUrl: null,
            birth: null,
            gender: null,
            salary: null,
        },
    }),
    actions: {
        setUserInfo(newUserInfo) {
            this.userInfo.nickname = newUserInfo.nickname;
            this.userInfo.profileUrl = newUserInfo.profileUrl;
            this.userInfo.birth = newUserInfo.birth;
            this.userInfo.gender = newUserInfo.gender;
            this.userInfo.salary = newUserInfo.salary;
        },
        clearUserInfo() {
            this.userInfo.nickname = null;
            this.userInfo.profileUrl = null;
            this.userInfo.birth = null;
            this.userInfo.gender = null;
            this.userInfo.salary = null;
        },
    },
    persist: {
        enabled: true,
        strategies: [
            {
                key: "user",
                storage: localStorage, // 또는 sessionStorage
            },
        ],
    },
});
