<template>
  <div class="search">
    <HeaderB title="" @back="goBack" />
  </div>
</template>

<script>
import HeaderB from '@/components/HeaderB.vue';
// import axios from 'axios';

export default {
  name: 'SearchP',
  components: {
    HeaderB,
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>
