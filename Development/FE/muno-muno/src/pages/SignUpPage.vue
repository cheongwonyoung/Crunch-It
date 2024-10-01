<template lang="">
  <div class="container">
    <!-- <HeaderComponent title="회원가입" @close="handleClose" /> -->
    <HeaderB title="회원가입" @back="handleClose" />
  </div>
</template>
<script>
import apiClient from '@/axios';
// import HeaderComponent from "@/components/HeaderComponent.vue";
import HeaderB from '@/components/HeaderB.vue';
export default {
  components: {
    // HeaderComponent,
    HeaderB,
  },
  data() {},
  methods: {
    handleClose() {
      this.$router.push({ name: 'Login' });
    },
    signUp() {
      apiClient;
    },
  },
};
</script>
<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  box-sizing: border-box;
}
</style>
