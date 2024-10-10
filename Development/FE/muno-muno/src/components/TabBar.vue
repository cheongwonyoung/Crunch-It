<template>
  <nav class="tab-bar">
    <router-link
      to="/"
      class="tab-item"
      :class="{ active: $route.path === '/' }"
    >
      <img
        :src="
          $route.path === '/'
            ? require('@/assets/home-p.svg')
            : require('@/assets/home.svg')
        "
        alt="홈"
      />
      <span>홈</span>
    </router-link>

    <router-link
      to="/product"
      class="tab-item"
      :class="{ active: $route.path === '/product' }"
    >
      <img
        :src="
          $route.path === '/product'
            ? require('@/assets/product-p.svg')
            : require('@/assets/product.svg')
        "
        alt="상품추천"
      />
      <span>상품추천</span>
    </router-link>

    <router-link
      to="/property"
      class="tab-item"
      :class="{ active: $route.path === '/property' }"
    >
      <img
        :src="
          $route.path === '/property'
            ? require('@/assets/property-p.svg')
            : require('@/assets/property.svg')
        "
        alt="자산분석"
      />
      <span>자산분석</span>
    </router-link>

    <router-link
      to="/community"
      class="tab-item"
      :class="{ active: $route.path === '/community' }"
    >
      <img
        :src="
          $route.path === '/community'
            ? require('@/assets/community-p.svg')
            : require('@/assets/community.svg')
        "
        alt="커뮤니티"
      />
      <span>커뮤니티</span>
    </router-link>
    <router-link to="/chat" class="tab-item">
      <img src="@/assets/community.svg" alt="채팅" />
      <span>채팅</span>
    </router-link>
    <img class="bar" src="@/assets/bar.svg" alt="Home Indicator Bar" />
  </nav>
</template>

<script>
export default {
  name: 'TabBar',
};
</script>

<style scoped>
.tab-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 85px;
  background-color: var(--gr100);
  border-top: 1px solid var(--gr70);
  box-shadow: 0px -1px 5px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.tab-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  color: var(--gr60);
  text-decoration: none;
  width: calc(100% / 4);
  padding: 8px 0;
  padding-bottom: 34px;
}

.tab-item img {
  width: 24px;
  height: 24px;
  margin-bottom: 2.4px;
}

.tab-item span {
  font-size: 12px;
  font-weight: 500;
  text-align: center;
  margin-top: 0px;
}

.tab-item.active {
  color: var(--p10);
}

.tab-item.active span {
  color: var(--p10);
}

.bar {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 34px;
  z-index: -1;
  object-fit: contain;
}
</style>
