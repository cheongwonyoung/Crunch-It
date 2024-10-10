import { defineStore } from "pinia";

export const useUserStore = defineStore("userStore", {
    state: () => ({
        userInfo: {
            nickname: null,
            profileUrl: null,
        },
    }),
    actions: {
        setUserInfo(newUserInfo) {
            this.userInfo.nickname = newUserInfo.nickname;
            this.userInfo.profileUrl = newUserInfo.profileUrl;
        },
        clearUserInfo() {
            this.userInfo.nickname = null;
            this.userInfo.profileUrl = null;
        },
    },
});
