<template>
  <div>
    <div class="modal-overlay">
      <div class="modal-window  fade-transition">
        <!-- 모달 닫기 버튼 -->
        <button class="close-button" @click="closeModal">X</button>
        <h1>로그인</h1>

        <!-- 아이디 -->
        <div class="input-wrapper">
          <label for="id">아이디:</label>
          <input type="text" name="id" id="id" v-model="user.id" />
        </div>
        <!-- 비밀번호 -->
        <div class="input-wrapper">
          <label for="pw">비밀번호:</label>
          <input type="password" name="pw" id="pw" v-model="user.pw" />
        </div>
        <!-- 로그인과 회원가입 -->
        <div class="button-wrapper">
          <button @click="confirm">로그인</button>
          <button @click="moveToRegist">회원가입</button>
        </div>
      </div>
    </div>
</div>

</template>

<script>
import { mapState, mapActions } from "vuex";

const userStore = "userStore";
export default {
  name : "UserLogin",
  data() {
    return {
      user: {
        id: "",
        pw: "",
        email: "",
        name: "",
      },
    };
  },
  mounted() {
    document.addEventListener('keydown', this.handleKeyDown);
  },
  beforeUnmount() {
    document.removeEventListener('keydown', this.handleKeyDown);
  },
  computed: {
    ...mapState(userStore, ["isLogin", "isLoginError", "userInfo", "nextPath"]),
  },
  methods: {
    ...mapActions(userStore, ["userConfirm", "getUserInfo"]),
    async confirm() {
      console.log("confirm 실행");
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      console.log("1. confirm() token >> " + token);
      if (this.isLogin) {
        await this.getUserInfo(token);
        console.log("4. confirm() userInfo :: ", this.userInfo);
        // this.$router.push({ name : "main" });
        this.closeModal()
      } else {
        alert("로그인 실패! 아이디 또는 패스워드를 확인해주세요.");
      }
    },
    // 모달용 메소드
    closeModal() {
      this.$emit("close-modal")
    },
    moveToRegist(){
      this.$emit('switch-to-regist');
    },
    handleKeyDown(event) {
      if (event.key === 'Escape') {
        this.closeModal();
      }
    },
  },
};
</script>

<style scoped>

</style>
