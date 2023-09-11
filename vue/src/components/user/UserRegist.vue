<template>
  <div class="modal-overlay">
    <div class="modal-window">
      <!-- 모달 닫기 버튼 -->
      <button class="close-button" @click="closeModal">X</button>
      <h1>회원가입</h1>
      <div class="input-wrapper">
        <div>
          id : <input type="text" v-model="user.id">
        </div>
        <div>
          pw : <input type="password" v-model="user.pw">
        </div>
        <div>
          name : <input type="text" v-model="user.name">
        </div>
        <div>
          email : <input type="email" v-model="user.email">
        </div>
      </div>
      
      <div class="button-wrapper">
        <button @click="moveToLogin">로그인</button>
        <button @click="regist">회원가입</button>
      </div>
    </div>
  </div>
</template>

<script>
import apiInstance from "@/api/http";
const http = apiInstance();
export default {
  name : "UserRegist",
  data() {
    return {
      user: {
        id: "",
        pw: "",
        name: "",
        email: "",
      }
    }
  },
  mounted() {
    document.addEventListener('keydown', this.handleKeyDown);
  },
  beforeUnmount() {
    document.removeEventListener('keydown', this.handleKeyDown);
  },
  methods: {
    regist() {
      if(this.id=="" && this.pw==""){
        alert("아이디와 비밀번호 입력은 필수입니다.");
        return;
      }
      http.post('/user/regist', this.user).then(({ data }) => {
        console.log("post 성공 data : ", data);
        if (data) {
          // this.$router.push("/user/login");
          this.moveToLogin();
        } else {
          alert("회원가입 실패");
        }
      }).catch(() => {
        console.log("post 실패");
      })
    },
    closeModal() {
      this.$emit("close-modal")
    },
    moveToLogin(){
      this.$emit("switch-to-login");
    },
    handleKeyDown(event) {
      if (event.key === 'Escape') {
        this.closeModal();
      }
    },
  }
};
</script>

<style></style>
