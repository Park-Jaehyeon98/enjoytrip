<template>
  <div class="modal-overlay">
    <div class="modal-window  fade-transition">
      <!-- 모달 닫기 버튼 -->
      <button class="close-button" @click="closeModal">X</button>
      <h2>글 작성</h2>

      <div class="input-wrapper">
          <label for="title">제목:</label>
          <input type="text" name="title" id="title" v-model="board.subject" placeholder="제목" />
      </div>

      <div class="input-wrapper">
          <label for="content">내용:</label>
          <textarea name="content" id="content" v-model="board.content" placeholder="내용"></textarea>
      </div>
      <div>
        <button variant="primary" @click="writeBoard">글 등록</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import apiInstance from "@/api/http";
const userStore = "userStore"
const http = apiInstance();
export default {
  name : "BoardWrite",
  data() {
      return {
          board: {
              subject: "",
              id: "",
              content: "",
          }
    }
  },
  computed : {
      ...mapState(userStore, ["userInfo"]),
  },
  created(){
    console.log("user info", this.userInfo);
  },
  mounted() {
    document.addEventListener('keydown', this.handleKeyDown);
  },
  beforeUnmount() {
    document.removeEventListener('keydown', this.handleKeyDown);
  },
  methods: {
    writeBoard() {
      this.board.id = this.userInfo.id; // Set the board's id property from userInfo
      http.post('/board/write', this.board).then(({ data }) => {
        console.log("board write post 성공 : ", data);
        if (data) {
          // this.$router.push("/board")
          this.closeModal();
        } else {
          alert("글 등록 실패");
        }
      })
    },
    // 모달용 메소드
    closeModal() {
      this.$emit("close-modal")
    },
    handleKeyDown(event) {
      if (event.key === 'Escape') {
        this.closeModal();
      }
    },
  },  
}
</script>

<style>

</style>