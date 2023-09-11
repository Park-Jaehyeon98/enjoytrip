<template>
  <div>
    <div id="banner" ref="banner">
      <!-- 코멘트 -->
      <transition name="banner-slide-left">
        <div v-if="showBanner" id="bannerComment" class="bannerComment">
          저기<br>어때
        </div>
      </transition>
      <transition name="banner-slide-right">
        <div v-if="showBanner" id="bannerComment2" class="bannerComment">
          저기어때에서<br>관광지 확인하고<br>
          나에게 딱 알맞는<br>숙박업소 찾아보자!
        </div>
      </transition>
      <button class="banner-button" @click="scrollToNextSection">
        <b-iconstack class="banner-button" font-scale="5" rotate="90">
        <b-icon stacked animation="cylon" icon="chevron-right" shift-h="-4" variant="light"></b-icon>
        <b-icon stacked animation="cylon" icon="chevron-right" shift-h="0" variant="light"></b-icon>
      </b-iconstack>
      </button>
    </div>

    <!-- second section -->
    <div class="second-section">
      <h1>✨ 원하는 활동을 즐겨보세요! ✨</h1>
      <div id="second-section-row">
        <div class="card" @click="$router.push('/attraction')">
          <h2>관광지 찾기</h2>
          <img src="https://images.unsplash.com/photo-1501785888041-af3ef285b470?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" alt="관광지" class="avatar">
          <h4>전국 각지의 관광지를 찾아보아요</h4>
        </div>
        <div class="card" @click="$router.push('/user/like')">
          <h2>머물곳 찾기</h2>
          <img src="https://images.unsplash.com/photo-1455587734955-081b22074882?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" alt="숙박업소" class="avatar">
          <h4>내가 찜한 관광지 근처의<br> 숙박업소는?</h4>
        </div>
        <div class="card" @click="$router.push('/board')">
          <h2>커뮤니티</h2>
          <img src="https://images.unsplash.com/photo-1507297230445-ff678f10b524?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80" alt="커뮤니티" class="avatar">
          <h4>커뮤니티에서 소통해요</h4>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showBanner: false,
      bannerHeight : null,
    };
  },
  methods: {
    scrollToNextSection() {
      window.scrollTo({
        // top: window.pageYOffset + this.bannerHeight + 56,
        top: this.bannerHeight + 56,
        behavior: "smooth"
      });
    }
  },
  mounted() {
    // Delay the initial animation to allow component rendering
    setTimeout(() => {
      this.showBanner = true;
    }, 10);
    // 클라이언트에 액세스하기 전에 배너 요소가 렌더링됨.
    // data 가 업데이트 되고 난 직후, UI가 갱신될 때 Vue가 DOM을 찾지 못하는 경우가 발생
    this.$nextTick(() => {
      this.bannerHeight = this.$refs.banner.clientHeight;
    });
  }
};
</script>

<style scoped src="@/assets/css/main_view.css">
</style>
