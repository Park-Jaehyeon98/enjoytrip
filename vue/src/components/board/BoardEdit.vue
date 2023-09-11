<!-- 이 vue는 BoardWrite에서 가져옴 -->
<template>
  <div>
    <div class="modal-overlay">
      <div class="modal-window  fade-transition">
          <!-- 모달 닫기 버튼 -->
          <button class="close-button" @click="closeModal">X</button>

          <h1>게시물 수정</h1>    

          <div class="input-wrapper">
            <label for="title">제목:</label>
            <input type="text" name="title" id="title" v-model="board.subject" placeholder="제목" />
          </div>

          <div class="input-wrapper">
            <label for="content">내용:</label>
            <textarea name="content" id="content" v-model="board.content" placeholder="내용"></textarea>
          </div>

          <button @click="editBoard">글 등록</button>
        </div>
    </div>
  </div>
</template>

<script>
// import { mapState } from "vuex";
// const userStore = "userStore"
import apiInstance from "@/api/http";
const http = apiInstance();
export default {
  name : "BoardEdit",
  data() {
    return {
        bno : this.$route.params.bno,
        board : {
        }
    }
  },
//   computed : {
//       ...mapState(userStore, ["userInfo"]),
//   },
  created(){
    // console.log("user info", this.userInfo);
    http.get(`/board/${this.bno}`)
    .then(({data})=>{
        console.log("게시물 수정에서 글 가져오기 : ", data)
        this.board = data;
    })
  },
  methods: {
    editBoard() {
      http.put('/board', this.board).then(({ data }) => {
        console.log("board edit 성공 : ", data);
        if (data) {
          // this.$router.push({name : 'boardDetail', params : {bno : this.bno}});
          this.closeModal();
        } else {
          alert("글 수정 실패");
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