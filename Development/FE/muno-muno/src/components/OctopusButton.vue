<template>
  <button class="octopus-button" @click="selectOctopus">
    <div class="octopus-box">
      <img :src="buttonImage" :alt="name" class="octopus-icon" />
    </div>
    <p class="octopus-text">{{ name }}</p>
  </button>
</template>

<script>
export default {
  name: 'OctopusButton',
  props: {
    name: {
      type: String,
      required: true,
    },
    buttonImage: {
      type: String,
      required: true,
    },
  },
  methods: {
    selectOctopus() {
      this.$emit('select');
    },
  },
};
</script>

<style scoped>
.octopus-button {
  background-color: transparent;
  border: none;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.octopus-box {
  width: 40px;
  height: 40px;
  flex-shrink: 0;
  background-color: var(--gr100);
  border-radius: 6px;
  display: flex;
  justify-content: center;
  align-items: center;
  filter: drop-shadow(0px 0px 2px rgba(0, 0, 0, 0.08));
}

.octopus-icon {
  width: 38px;
  height: 38px;
  object-fit: cover;
}

.octopus-text {
  color: var(--gr50);
  text-align: center;
  font-size: 11px;
  font-weight: 600;
  line-height: 100%;
  margin: 6px 0 0 0;
}
</style>
