<template>
  <div id="nav">
    <!-- As a link -->
    <b-navbar toggleable="sm" variant="transparent">
      <b-navbar-nav>
        <img id="logo" src="@/assets/img/howthere.png" style="width:100px;" @click="$router.push({path : '/'})">
      </b-navbar-nav>
      <b-navbar-nav class="ml-auto mr-auto">
        <b-nav-text v-if="userInfo"
          >안녕하세요 {{ userInfo.name }}님</b-nav-text
        >
      </b-navbar-nav>

      <b-navbar-toggle target="nav-text-collapse"></b-navbar-toggle>
      <b-collapse id="nav-text-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item @click="$router.push('/attraction')">관광지</b-nav-item>
          <b-nav-item @click="$router.push('/board')">게시글</b-nav-item>
          <!-- 회원은 마이페이지 -->
          <b-nav-item-dropdown text="마이페이지" right v-if="isLogin && userInfo && userInfo.id !== 'admin'">
            <b-dropdown-item @click="$router.push({ name : 'like' })">좋아요 목록</b-dropdown-item>
            <b-dropdown-item @click="$router.push({ name: 'my' })">게시글/댓글</b-dropdown-item>
            <b-dropdown-item @click="openModal(), switchToEditModal()">수정/삭제</b-dropdown-item>
          </b-nav-item-dropdown>

          <!-- 관리자는 회원정보 -->
          <b-nav-item @click="goAdmin" v-if="isLogin && userInfo && userInfo.id === 'admin'">관리자</b-nav-item>
          
          <b-nav-item @click="logout" v-if="userInfo">로그아웃</b-nav-item>
          <!-- <b-nav-item v-if="!userInfo"
            ><b-link :to="{ name: 'user' }">로그인</b-link></b-nav-item
          > -->
          <b-nav-item v-if="!userInfo" @click="openModal(), switchToLoginModal()">로그인</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>

    <!-- 모달 컴포넌트 -->
    <transition name="fade">
      <UserLogin v-if="showModal && showModalType === 'login'" @switch-to-regist="switchToRegistModal" @close-modal="closeModal"/>
    </transition>
    <transition name="fade">
      <UserRegist v-if="showModal && showModalType === 'regist'" @switch-to-login="switchToLoginModal" @close-modal="closeModal" />    
    </transition>
    <transition name="fade">
      <UserInfoEdit v-if="showModal && showModalType === 'infoEdit'" @switch-to-login="switchToLoginModal" @close-modal="closeModal" />
    </transition>
  </div>
</template>

<script>
import UserLogin from "@/components/user/UserLogin.vue";
import UserRegist from "@/components/user/UserRegist.vue";
import UserInfoEdit from "@/components/user/mypage/UserInfoEdit.vue"

import { mapState, mapActions, mapGetters } from "vuex";
const userStore = "userStore";
const modalStore = "modalStore";
export default {
  name: "NavHeader",
  components : {
    UserLogin, UserRegist, UserInfoEdit
  },
  data(){
    return {
    }
  },
  computed: {
    ...mapState(userStore, ["isLogin", "isLoginError", "userInfo"]),
    ...mapGetters(userStore, ["checkLoginState", "checkUserInfo"]),
    ...mapGetters(modalStore, ["showModal", "showModalType"]),
  },
  created() {
    // console.log("header created userinfo: ",this.userInfo)
  },
  methods: {
    ...mapActions(userStore, ["userLogout"]),
    logout() {
      // console.log("111userInfo:", this.userInfo)
      // console.log("userinfo id : ", this.userInfo.id);
      if(this.checkUserInfo && this.checkUserInfo.id)
        this.userLogout(this.checkUserInfo.id)
      this.$store.commit("userStore/SET_IS_LOGIN", false);
      alert("로그아웃 성공");
      this.$router.push(this.$route.path).catch(()=>{})
      // console.log("11isLogin", this.checkLoginState);
      // console.log("11isLogin", this.isLogin)
    },
    goAdmin(){
      this.$router.push({name : 'userList'})
      .catch(()=>{})
    },
    // 모달용 메소드
    switchToRegistModal() {
      this.$store.commit("modalStore/SET_MODAL_TYPE", "regist");
    },
    switchToLoginModal() {
      this.$store.commit("modalStore/SET_MODAL_TYPE", "login");
    },
    switchToEditModal(){
      this.$store.commit("modalStore/SET_MODAL_TYPE", "infoEdit");
    },
    openModal() {
      this.$store.commit("modalStore/SET_SHOW_MODAL", true);
    },
    closeModal() {
      this.$store.commit("modalStore/SET_SHOW_MODAL", false);
    },
  },
};
</script>

<style scoped lang="scss">
@import "../assets/css/app_vars.scss";
#logo:hover{
  cursor: pointer;
}
#nav {
  /* background-color: white; */
  box-shadow: 0 0 15px 1px rgba(0, 0, 0, 0.116);
  // position: absolute;
  // z-index: 1000;
  width: 100%;
  .nav-item {
    color: $blue;
  }
  z-index: 10000;
}

#nav-text-collapse {
  flex-grow: unset;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

</style>
