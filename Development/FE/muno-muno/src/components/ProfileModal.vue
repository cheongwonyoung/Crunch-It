<template>
    <div class="modal-background" v-if="isVisible">
        <div class="modal-content">
            <div class="action-group">
                <button class="modal-option" @click="triggerFileInput">앨범에서 선택</button>
                <input type="file" ref="fileInput" style="display: none" @change="selectFromAlbum" accept="image/*" />

                <hr class="divider" />
                <button class="modal-option delete" @click="deleteProfilePhoto">프로필 사진 삭제</button>
            </div>
            <div class="close-group">
                <button class="modal-option close" @click="closeModal">닫기</button>
            </div>
        </div>
    </div>
</template>

<script>
    import apiClient from "@/axios";
    export default {
        props: {
            isVisible: {
                type: Boolean,
                required: true,
            },
        },
        methods: {
            triggerFileInput() {
                this.$refs.fileInput.click();
            },
            async selectFromAlbum(event) {
                const file = event.target.files[0];
                if (!file) {
                    alert("파일을 선택하지 않았습니다.");
                    return;
                }

                const formData = new FormData();
                formData.append("file", file); // FormData에 파일 추가
                const res = await apiClient.post("/mypage/uploadProfile", formData, {
                    headers: {
                        "Content-Type": "multipart/form-data",
                    },
                });
                try {
                    console.log(res.data.message);
                    alert("변경되었습니다");
                    this.$emit("close");
                } catch (err) {
                    console.log(err);
                }
            },
            async deleteProfilePhoto() {
                const res = await apiClient.delete("/mypage/deleteProfile");
                try {
                    console.log(res.data.message);
                    alert("프로필 사진이 삭제되었습니다.");
                    this.$emit("close");
                } catch (err) {
                    console.log(err);
                }
            },
            closeModal() {
                this.$emit("close");
            },
        },
    };
</script>

<style scoped>
    .modal-background {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 1000;
    }

    .modal-content {
        width: 335px;
        text-align: center;
        position: fixed;
        bottom: 34px;
        left: 50%;
        transform: translateX(-50%);
        width: calc(100% - 40px);
    }

    .action-group {
        background-color: var(--gr80);
        border-radius: 12px;
        overflow: hidden;
        margin-bottom: 10px;
    }

    .modal-option {
        color: var(--p10);
        width: 100%;
        padding: 20px 0;
        border: none;
        background: none;
        cursor: pointer;
        font-size: 16px;
        font-weight: 400;
        line-height: 100%;
    }

    .delete {
        color: #ff3e41;
    }

    .close {
        color: var(--p10);
        text-align: center;
        font-size: 16px;
        font-weight: 600;
        line-height: 100%;
        border-radius: 12px;
        background: var(--gr100);
        width: 335px;
        height: 56px;
        border: none;
        cursor: pointer;
    }

    .divider {
        border: none;
        margin: 0 0;
        margin: 0;
        border-top: 1px solid var(--gr60);
    }
</style>
