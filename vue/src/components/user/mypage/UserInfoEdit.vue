<template>
  <div class="modal-overlay">
    <div class="modal-window">
      <!-- 모달 닫기 버튼 -->
      <button class="close-button" @click="closeModal">X</button>
      <h1>회원 정보<br> 수정 / 삭제</h1>

      <div class="input-wrapper">
        아이디 : <input type="text" :value="userInfo.id" readonly/>
      </div>

      <div class="input-wrapper">
        이름 : <input type="text" v-model="newName"> <br>
        이메일 : <input type="text" v-model="newEmail"><br>
        현재 비밀번호 : <input type="password" v-model="pw"><br>
        바꿀 비밀번호 : <input type="password" v-model="newPw"><br>
      </div>

      <div class="button-wrapper">
        <button @click="infoEdit">회원 정보 수정</button>
        <button @click="userdelete">회원 탈퇴</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions} from "vuex";
const userStore = "userStore";
import apiInstance from "@/api/http";
const http = apiInstance();
export default {
  name: "UserinfoEdit",
  data() {
    return {
      newName: "",
      newEmail: "",
      pw: "",
      newPw: "",
    }
  },
  created() {
    this.newName = this.userInfo.name;
    this.newEmail = this.userInfo.email;
  },
  mounted() {
    document.addEventListener('keydown', this.handleKeyDown);
  },
  beforeUnmount() {
    document.removeEventListener('keydown', this.handleKeyDown);
  },
  computed:{
    ...mapState(userStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(userStore, ["userLogout"]),
    infoEdit() {
      if (this.newName == "") {
        this.newName = this.userInfo.name;
      }
      if (this.newEmail == "") {
        this.newEmail = this.userInfo.email;
      }
      if (this.pw != this.newPw) {
        http.put(`/user/update`, {
          "id": this.userInfo.id,
          "name": this.newName,
          "email": this.newEmail,
          "pw": this.pw,
          "newPw": this.newPw,
        }).then(({data}) => {
          console.log("data : ", data)
          if (!data) {
            this.userLogout(this.userInfo.id);
            alert("회원 정보 수정 성공");
            // this.$router.push("/user/login");
            this.moveToLogin();
          } else {
            alert("회원 정보 수정 실패");
          }
        }).catch(() => {
          console.log("회원 정보 수정 실패")
        })
      } else {
        alert("비밀번호를 제대로 입력해주세요.");
      }
    },
    userdelete() {
      console.log("userdelete 실행 : ", this.userInfo.id);
      this.userLogout(this.userInfo.id);
      http.delete(`/user/delete/${this.userInfo.id}`).then(({ data }) => {
        if (!data) {
          alert("회원 탈퇴 성공");
          // this.$router.push("/user/login");
          this.moveToLogin();
        } else {
          alert("회원 탈퇴 실패")
        }
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
}
</script>

<style></style>
