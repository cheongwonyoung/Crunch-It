<template>
  <div class="message-wrapper">
    <div class="message-time-user">{{ time }}</div>
    <div class="message message-user">
      <div class="message-text">{{ message }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MessageUser',
  props: {
    message: {
      type: String,
      required: true,
    },
    time: {
      type: String,
      required: true,
    },
  },
};
</script>

<style scoped>
.message-wrapper {
  display: flex;
  justify-content: flex-end;
  align-items: flex-end;
  margin-bottom: 12px;
  position: relative;
  line-height: 150%;
}

.message-user {
  display: inline-block;
  border-radius: 20px 2px 20px 20px;
  background: var(--p10);
  color: var(--gr100);
  box-shadow: 0px 0px 4px 0px rgba(0, 0, 0, 0.06);
  padding: 8px 14px;
  margin-left: 6px;
}

.message-time-user {
  color: var(--gr50);
  font-size: 11px;
  font-weight: 400;
  line-height: 100%;
  align-self: flex-end;
  margin-bottom: 2px;
}
</style>
