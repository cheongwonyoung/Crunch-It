<!-- 문어 버튼들의 컴포넌트 -->
<template>
  <div class="octopus-selection">
    <OctopusButton
      v-for="octopus in octopuses"
      :key="octopus.id"
      :name="octopus.name"
      :buttonImage="octopus.buttonImage"
      @select="selectOctopus(octopus)"
    />
  </div>
</template>

<script>
import OctopusButton from '@/components/OctopusButton.vue';

export default {
  name: 'OctopusSelection',
  components: {
    OctopusButton,
  },
  props: {
    octopuses: {
      type: Array,
      required: true,
    },
  },
  methods: {
    selectOctopus(octopus) {
      this.$emit('select-octopus', octopus);
    },
  },
};
</script>

<style scoped>
.octopus-selection {
  display: flex;
  padding: 0 22px 0 163px;
  position: absolute;
  bottom: 212px;
}

.octopus-button {
  background-color: transparent;
  border: none;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0 4px;
}
</style>
