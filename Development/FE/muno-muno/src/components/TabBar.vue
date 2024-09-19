<template>
  <nav class="tab-bar">
    <router-link to="/" class="tab-item">
      <img src="@/assets/home.svg" alt="홈" />
      <span>홈</span>
    </router-link>
    <router-link to="/product" class="tab-item">
      <img src="@/assets/product.svg" alt="상품추천" />
      <span>상품추천</span>
    </router-link>
    <router-link to="/property" class="tab-item">
      <img src="@/assets/property.svg" alt="자산관리" />
      <span>자산관리</span>
    </router-link>
    <router-link to="/community" class="tab-item">
      <img src="@/assets/community.svg" alt="커뮤니티" />
      <span>커뮤니티</span>
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
  height: 86px;
  background-color: var(--gray-1000, #fff);
  border: 0.5px solid var(--gray-700, #eaecef);
  box-shadow: 0px 0px 20px 0px rgba(62, 68, 78, 0.1);
  z-index: 1000;
  padding: 0;
}

.tab-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  color: #caced4;
  flex-grow: 1;
  text-decoration: none;
  z-index: 10;
}

.tab-item img {
  width: 24px;
  height: 24px;
}

.tab-item span {
  font-size: 12px;
  font-weight: 500;
  text-align: center;
  margin-top: 5px;
}

.bar {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 34px;
  z-index: 5;
  object-fit: contain;
}
</style>
