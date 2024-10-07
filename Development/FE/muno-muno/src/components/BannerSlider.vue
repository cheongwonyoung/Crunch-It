<template>
  <div class="slider-container">
    <div
      class="banner-slider"
      ref="slider"
      :style="{
        transform: `translateX(${-currentSlide * 345}px)`,
        transition: isAnimating ? 'transform 0.5s ease-in-out' : 'none',
      }"
      @transitionend="onTransitionEnd"
    >
      <div
        v-for="(banner, index) in displayBanners"
        :key="index"
        class="banner"
      >
        <div class="banner-content">
          <h3>{{ banner.title }}</h3>
          <p>{{ banner.subtitle }}</p>
        </div>

        <div class="octopus-image">
          <img :src="getOctopusImage(banner.category)" alt="문어상품 이미지" />
        </div>

        <div class="banner-indicator">{{ getIndicator(index) }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BannerSlider',
  data() {
    return {
      currentSlide: 1,
      isAnimating: true,
      autoSlideInterval: null,
      isUserInteracting: false,
      // 백엔드에서 받은 상품 리스트 예시
      banners: [
        {
          title: '웃는 문어들의 선택',
          subtitle: '쏠편한 입출금통장',
          category: '예금',
        },
        { title: '문어들의 펀드', subtitle: '성장하는 펀드', category: '펀드' },
        { title: '문어들의 적금', subtitle: '안전한 적금', category: '적금' },
        { title: '문어들의 주식', subtitle: '주식 투자', category: '주식' },
        { title: '문어들의 채권', subtitle: '안정적인 채권', category: '채권' },
      ],
    };
  },
  computed: {
    displayBanners() {
      return [
        this.banners[this.banners.length - 1],
        ...this.banners,
        this.banners[0],
      ];
    },
  },
  methods: {
    getOctopusImage(category) {
      switch (category) {
        case '예금':
          return require('@/assets/octopusProduct_1.svg');
        case '적금':
          return require('@/assets/octopusProduct_2.svg');
        case '펀드':
          return require('@/assets/octopusProduct_3.svg');
        case '주식':
          return require('@/assets/octopusProduct_4.svg');
        case '채권':
          return require('@/assets/octopusProduct_5.svg');
      }
    },
    startAutoSlide() {
      this.autoSlideInterval = setInterval(() => {
        if (!this.isUserInteracting) {
          this.nextSlide();
        }
      }, 4000);
    },
    stopAutoSlide() {
      clearInterval(this.autoSlideInterval);
    },
    nextSlide() {
      this.isAnimating = true;
      this.currentSlide++;
    },
    prevSlide() {
      this.isAnimating = true;
      this.currentSlide--;
    },
    onTransitionEnd() {
      const totalSlides = this.banners.length;
      if (this.currentSlide === 0) {
        this.currentSlide = totalSlides;
        this.isAnimating = false;
      }
      if (this.currentSlide === totalSlides + 1) {
        this.currentSlide = 1;
        this.isAnimating = false;
      }
    },
    getIndicator(index) {
      if (index === 0 || index === this.banners.length + 1) return '';
      return `${index}/${this.banners.length}`;
    },
    onUserSlideStart() {
      this.isUserInteracting = true;
      this.stopAutoSlide();
    },
    onUserSlideEnd() {
      this.isUserInteracting = false;
      this.startAutoSlide();
    },
  },
  mounted() {
    this.startAutoSlide();
  },
  beforeUnmount() {
    this.stopAutoSlide();
  },
};
</script>

<style scoped>
.slider-container {
  width: 335px;
  height: 114px;
  margin: 16px auto;
}

.banner-slider {
  display: flex;
  transition: transform 0.5s ease-in-out;
}

.banner {
  flex-shrink: 0;
  width: 335px;
  height: 114px;
  border-radius: 14px;
  background: var(--gr20);
  margin-right: 10px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-left: 25px;
  box-sizing: border-box;
}

.banner-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.banner-content h3 {
  color: var(--gr100);
  font-size: 14px;
  font-weight: 500;
  margin: 4px 0 6px 2px;
}

.banner-content p {
  color: var(--gr100);
  font-size: 22px;
  font-weight: 600;
  margin: 0;
}

.banner-indicator {
  color: var(--gr80);
  font-size: 12px;
  font-weight: 600;
  position: absolute;
  top: 10px;
  right: 20px;
}

.octopus-image {
  width: 117px;
  height: 87px;
  flex-shrink: 0;
  display: flex;
  justify-content: center;
  align-self: flex-end;
  box-sizing: border-box;
  margin-right: 16px;
}

.octopus-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
