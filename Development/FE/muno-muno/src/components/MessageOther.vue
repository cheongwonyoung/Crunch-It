<template>
  <div class="message-wrapper">
    <div class="message-profile">
      <img class="message-other-logo" :src="avatar" alt="other" />
      <div class="message-text-label">{{ name }}</div>
    </div>

    <div class="message-content">
      <div class="message-row">
        <div class="message-other">
          <div class="message-text">{{ message }}</div>
        </div>
        <div class="message-time">{{ time }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MessageOther',
  props: {
    message: {
      type: String,
      required: true,
    },
    time: {
      type: String,
      required: true,
    },
    name: {
      type: String,
      default: '상대방',
    },
    avatar: {
      type: String,
      default: 'https://via.placeholder.com/32',
    },
  },
};
</script>

<style scoped>
.message-wrapper {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 12px;
  position: relative;
  max-width: 100%;
}

.message-profile {
  display: flex;
  align-items: center;
  margin-bottom: 8px;
}

.message-other-logo {
  width: 32px;
  height: 32px;
  margin-right: 8px;
}

.message-text-label {
  color: var(--gr20);
  font-size: 16px;
  font-weight: 500;
  line-height: 100%;
}

.message-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  position: relative;
}

.message-row {
  display: flex;
  align-items: flex-end;
  flex-wrap: wrap;
}

.message-other {
  display: inline-block;
  border-radius: 2px 20px 20px 20px;
  background: var(--gr70);
  color: var(--gr100);
  box-shadow: 0px 0px 4px 0px rgba(0, 0, 0, 0.06);
  padding: 8px 14px;
  margin-right: 6px;
  max-width: 100%;
  word-wrap: break-word;
}

.message-text {
  color: var(--gr20);
  font-size: 16px;
  font-weight: 400;
  line-height: 150%;
}

.message-time {
  color: var(--gr50);
  font-size: 11px;
  font-weight: 400;
  line-height: 100%;
  align-self: flex-end;
  margin-bottom: 2px;
  white-space: nowrap;
}
</style>
