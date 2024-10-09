<template>
  <div>
    <ButtonA @click="handleButtonClick">지금 연동할래요</ButtonA>
  </div>
</template>

<script>
import ButtonA from '@/components/ButtonA.vue';

export default {
  components: {
    ButtonA,
  },
  methods: {
    handleButtonClick() {
      // 버튼 클릭 시 동작할 함수
      console.log('ButtonA가 클릭되었습니다.');
    },
  },
};
</script>
