<template>
  <div class="slider-container">
    <div
      class="banner-slider"
      ref="slider"
      :style="{
        transform: `translateX(${-currentSlide * 345}px)`,
        transition: isAnimating
          ? 'transform 0.5s cubic-bezier(0.42, 0, 0.58, 1)'
          : 'none',
      }"
      @transitionend="onTransitionEnd"
    >
      <div
        v-for="(banner, index) in displayBanners"
        :key="index"
        class="banner"
        @click="onBannerClick(banner)"
      >
        <div class="banner-content">
          <p>{{ banner.subtitle }}</p>
          <h3>{{ banner.title }}</h3>
        </div>

        <div class="octopus-image">
          <img :src="getOctopusImage(banner.category)" alt="문어상품 이미지" />
        </div>
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
      banners: [
        {
          subtitle: '주식 고수 문어 선정',
          title: '수익률 No.1 주식',
          category: '주식',
        },
        {
          subtitle: '문어들이 사랑하는 펀드',
          title: '수익률 No.1 펀드',
          category: '펀드',
          fundProduct: '글로벌 주식형 펀드',
        },
        {
          subtitle: '문어들의 안전자산',
          title: '수익률 No.1 채권',
          category: '채권',
        },
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
          return require('@/assets/octopusProduct_1.png');
        case '적금':
          return require('@/assets/octopusProduct_2.png');
        case '펀드':
          return require('@/assets/octopusProduct_3.png');
        case '주식':
          return require('@/assets/octopusProduct_4.png');
        case '채권':
          return require('@/assets/octopusProduct_5.png');
      }
    },
    onBannerClick(banner) {
      this.$emit('banner-click', banner);
    },
    startAutoSlide() {
      this.autoSlideInterval = setInterval(() => {
        if (!this.isUserInteracting) {
          this.nextSlide();
        }
      }, 3500); // 3.5초 간격으로 슬라이드 전환
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
  transition: transform 3s cubic-bezier(0.6, -0.28, 0.74, 0.05);
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
  cursor: pointer;
  overflow: hidden;
}

.banner-content {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.banner-content p {
  color: var(--gr100);
  font-size: 14px;
  font-weight: 500;
  margin: 4px 0 6px 2px;
}

.banner-content h3 {
  color: var(--gr100);
  font-size: 22px;
  font-weight: 600;
  margin: 0;
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

@keyframes popUpAnimation {
  0% {
    transform: translateY(-2);
  }
  50% {
    transform: translateY(-9.5px); /* 위로 10px 올라감 */
  }
  100% {
    transform: translateY(-2);
  }
}

.octopus-image {
  position: relative; /* 문어 이미지가 배너 안에서 움직이도록 위치 지정 */
  transform: translateY(10px); /* 문어 이미지를 10px 아래로 이동 */
}

.octopus-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  animation: popUpAnimation 1.7s ease-in-out infinite;
}
</style>
