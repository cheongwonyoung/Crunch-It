<template>
  <div
    class="slider-container"
    @touchstart="handleTouchStart"
    @touchend="handleTouchEnd"
  >
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
        :class="banner.id"
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
      touchStartX: 0, // 터치 시작 지점 X 좌표
      touchEndX: 0, // 터치 종료 지점 X 좌표
      banners: [
        {
          id: 'savings',
          subtitle: '나와 닮은 문어들의 적금',
          title: 'AI 문어의 추천',
          category: '적금',
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
        {
          subtitle: '주식 고수 문어 선정',
          title: '수익률 No.1 주식',
          category: '주식',
        },
      ],
    };
  },
  computed: {
    // `displayBanners`는 양쪽에 하나씩 추가한 배열을 반환하여 무한 슬라이드를 구현합니다.
    displayBanners() {
      return [
        this.banners[this.banners.length - 1], // 마지막 배너를 첫 번째 위치로
        ...this.banners, // 모든 배너
        this.banners[0], // 첫 번째 배너를 마지막 위치로
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
      console.log('Banner clicked:', banner);
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

    // 터치 시작 시 X 좌표 저장
    handleTouchStart(event) {
      this.onUserSlideStart();
      this.touchStartX = event.touches[0].clientX;
    },
    // 터치 종료 시 X 좌표와 비교하여 슬라이드 방향 결정
    handleTouchEnd(event) {
      this.touchEndX = event.changedTouches[0].clientX;

      // 터치 이동 거리가 50px 이상일 때만 슬라이드 이동
      const distance = this.touchStartX - this.touchEndX;
      if (distance > 50) {
        this.nextSlide(); // 다음 슬라이드로 이동
      } else if (distance < -50) {
        this.prevSlide(); // 이전 슬라이드로 이동
      }

      this.onUserSlideEnd();
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

.banner.savings {
  background: linear-gradient(135deg, #328aff 0%, #7e75ff 100%);
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
