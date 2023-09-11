<template>
  <div id="app">
    <!-- start nav -->
    <nav-header></nav-header>
    <div id="main">
      <router-view :key="$route.fullPath"></router-view>
    </div>
    <!-- footer -->
    <div id="footer">
      <p>(주)저기어때컴퍼니</p>
      <p>대표 : 대전 5반 10팀</p>
      <p>주소 | 대전광역시 유성구 동서대로 98-39 삼성화재 유성연수원</p>
      <p>(주) 저기어때컴퍼니는 통신판매중개자로서 통신판매의 당사자가 아니며, 상품의 예약, 이용 및 환불 등과 관련한 의무와 책임은 각 판매자에게 있습니다.</p>
      <hr>
      <b-icon-facebook /> | 
      <b-icon-instagram />
    </div>
  </div>
</template>

<script>
import NavHeader from "./components/NavHeader.vue";
export default {
  name: "App",
  components: {
    NavHeader,
  },
};
</script>

<style lang="scss">
@import '@/assets/css/app.scss';
@font-face {
  font-family: "Pretendard-Regular";
  src: url("https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff")
    format("woff");
  font-weight: 400, 800;
  font-style: normal;
}
#app {
  font-family: Pretendard-Regular;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #333d4b;
  // margin-top: 60px;
  min-height: 100vh;
  background-color: $body-bg;
}
#main {
  // margin-top: 2rem;
  // position: relative;
  // top: 56px;
  // height : 100%
}
#footer{
  width : 100%;
  // margin-left: auto;
  // margin-right: auto;
  padding : 50px 30px 0 30px;
  color: rgb(171, 171, 171);
  letter-spacing: 1px;
  border-top: 1px solid rgb(223, 223, 223);
  // z-index: 200;
  // position: relative;
}
</style>
