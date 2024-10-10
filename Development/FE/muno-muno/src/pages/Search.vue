<template>
  <div class="search">
    <HeaderB title="" @back="goBack" />

    <div class="search-content">
      <h1>어떤 상품을 찾으시나요?</h1>
      <div class="search-box">
        <input
          type="text"
          placeholder="검색어를 입력해주세요."
          v-model="searchQuery"
          @keyup.enter="search"
          class="input-text"
        />
        <img
          class="icon-search"
          src="@/assets/search.svg"
          alt="Search Icon"
          @click="search"
        />
      </div>

      <div class="suggestions">
        <h3>추천 검색어</h3>
        <div class="tags">
          <span v-for="tag in suggestedTags" :key="tag" class="tag">{{
            tag
          }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderB from '@/components/HeaderB.vue';

export default {
  name: 'SearchP',
  components: {
    HeaderB,
  },
  data() {
    return {
      searchQuery: '',
      suggestedTags: [
        '#20대상품추천',
        '#이자율높은상품',
        '#예적금',
        '#펀드',
        '#주식',
        '#채권',
        '#금융상품',
        '#사회초년생',
      ],
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    search() {
      if (this.searchQuery.trim() !== '') {
        console.log('Search query:', this.searchQuery);
      } else {
        console.log('검색어를 입력해주세요.');
      }
    },
  },
};
</script>

<style scoped>
.search {
  padding: 0 20px;
}

.search-content {
  margin-top: 12px;
}

h1 {
  margin-top: 79px;
  margin-bottom: 43px;
  color: var(--gr10);
  font-size: 24px;
  font-weight: 500;
  line-height: 100%;
}

.search-box {
  display: flex;
  align-items: center;
  margin-bottom: 24px;
  padding-bottom: 10px;
  border-bottom: 2px solid var(--gr30);
  position: relative;
}

.input-text {
  border: none;
  outline: none;
  flex-grow: 1;
  font-size: 16px;
  padding: 0;
  margin: 0;
  color: var(--gr20);
  height: 18px;
}

.input-text::placeholder {
  color: var(--gr60);
}

.icon-search {
  position: absolute;
  right: 0;
  bottom: 7px;
  width: 24px;
  height: 24px;
  margin-right: 2px;
  cursor: pointer;
}

.suggestions {
  margin-top: 32px;
}

.suggestions h3 {
  color: var(--gr30);
  font-size: 18px;
  font-weight: 600;
  line-height: 100%;
  margin: 40px 0 15px 2px;
}

.tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px 6px;
}

.tag {
  color: var(--p10);
  font-size: 15px;
  font-weight: 500;
  line-height: 100%;
  border-radius: 20px;
  border: 1px solid var(--p10);
  display: inline-flex;
  padding: 8px 16px;
  justify-content: center;
  align-items: center;
  gap: 10px;
}
</style>
