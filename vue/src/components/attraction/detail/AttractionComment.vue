<template>
  <div>
    <div class="comment-area">
      <h2 class="comment-heading">댓글</h2>
      <div class="comment-writing">
        <input type="text" v-model="commentContent" class="comment-input comment" placeholder="댓글 작성">
        <b-btn variant="outline-primary" @click="registerComment()">입력</b-btn>
      </div>
      <hr>
      <div v-for="comment in pagedcomments" :key="comment.acno" class="comment">
        작성자 ID : <span class="comment-id">{{ comment.id }}</span><br>
        <!-- 댓글 내용 -->
        <input type="text" v-model="comment.content" :readonly="!comment.editable" class="comment-input">
        <!-- 댓글 수정 확인 버튼 -->
        <b-btn variant="outline-danger" v-show="comment.editable" @click="editComment(comment.acno, comment.content)">확인</b-btn>
        <b-btn variant="outline-primary" v-if="userInfo && userInfo.id == comment.id && !comment.editable" @click="toggleEdit(comment)">수정</b-btn>
        <b-btn variant="outline-danger" v-if="userInfo && userInfo.id == comment.id" @click="deleteComment(comment.acno)">삭제</b-btn>
      </div>
      <!-- 페이징 컴포넌트 -->
        <page-component
        :displayedPageNumbers="displayedPageNumbers"
        :currentPage="currentPage"
        :totalPages = "totalPages"
        @go-to-previous-page="goToPreviousPage"
        @go-to-next-page="goToNextPage"
        @go-to-page="goToPage"
        >
        </page-component>
        <!-- 페이징 컴포넌트 끝 -->
      </div>
  </div>
</template>
    
    <script>
  //   import CommentItem from "@/components/item/CommentItem.vue"
  import PageComponent from "@/components/PageComponent.vue";
    import { mapState } from "vuex";
    const userStore = "userStore"
    
    import apiInstance from "@/api/http";
    const http = apiInstance();
    export default {
        components:{
              // CommentItem
              PageComponent
          },
        props : {
            "contentId" : Number,
        },
        data(){
            return {
                comments : [],
                commentContent: "",
                currentPage: 1,    // 현재 페이지
                pageSize: 10,       // 한 페이지에 나타날 관광지 수
                displayedPageNumbers: [] // 페이징 네비게이션에 나올 버튼들
            }
        },
        computed : {
            ...mapState(userStore, ["userInfo"]),
           /* paging 시작 */
    totalPages() {
        return Math.ceil(this.comments.length / this.pageSize);
    },
    pagedcomments() { // attractions를 paging해서 나타내는 메소드. 
        // 페이징하는 컴포넌트의 data에 따라 이름을 바꿔 써야 함
        const startIndex = (this.currentPage - 1) * this.pageSize;
        const endIndex = startIndex + this.pageSize;
        return this.comments.slice(startIndex, endIndex);
      },
        },
        created(){
            this.getComments();
            console.log(this.contentId);
            this.currentPage = 1; // 초기에 현재 페이지 값 설정
            this.updateDisplayedPageNumbers(); // 페이지 네비게이션에 숫자 버튼들 생성
        },
        methods: {
            getComments(){
                console.log("start getComments:")
                http.get(`/attraction/comment/${this.contentId}`)
                .then(({data})=>{
                    console.log("댓글들:", data)
                    this.comments = data.map(comment => ({
                      ...comment,
                      editable: false // Initialize the editable state for each comment
                    }));
                })
            },
            registerComment(){
                console.log("start register")
                http.post(`/attraction/comment`, {
                    "id" : this.userInfo.id,
                    "content" : this.commentContent,
                    "contentId" : this.contentId,
                })
                .then(({data})=>{
                    if(data){
                        this.getComments();
                        console.log(data)
                    }
                })
                .catch(()=>{
                    console.log("실패")
                })
            },
            deleteComment(acno){
                http.delete(`/attraction/comment/${acno}`)
                .then(({data})=>{
                    if(data){
                        alert("삭제 성공")
                        this.getComments();
                    } else{
                        console.log("댓글 삭제 실패")
                    }
                })
            },
            editComment(acno, content){
                http.put(`/attraction/comment`, {
                    "content" : content,
                    "acno" : acno
                })
                .then(({data})=>{
                    if(data){
                        // alert("수정 성공")
                        this.getComments();
                    } else{
                        console.log("댓글 수정 실패")
                    }
                })
            },
            toggleEdit(comment) {
              // Reset the editable state for all comments
              this.comments.forEach(c => (c.editable = false));
              comment.editable = true; // Set the clicked comment as editable
            },
         // 페이징용 메소드 시작
    goToPage(pageNumber) {
      this.currentPage = pageNumber;
      this.updateDisplayedPageNumbers();
      
    },
    goToNextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.updateDisplayedPageNumbers();
      }
    },
    goToPreviousPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.updateDisplayedPageNumbers();
      }
    },
    // 네비게이션에 표시될 버튼 범위와 개수 지정
    updateDisplayedPageNumbers() {
      const range = 5;  // 버튼 개수
      const totalPages = this.totalPages;
      const currentPage = this.currentPage;

      let startPage = 1;
      let endPage = Math.min(totalPages, range);

      if (currentPage > range) {
        startPage = Math.floor((currentPage - 1) / range) * range + 1;
        endPage = Math.min(startPage + range - 1, totalPages);
      }

      this.displayedPageNumbers = [];
      for (let i = startPage; i <= endPage; i++) {
        this.displayedPageNumbers.push(i);
      }
    }
    // 페이징용 메소드 끝
  },
        
        watch: {
    comments: { // 페이징용
      handler() {
        this.currentPage = 1
        this.updateDisplayedPageNumbers();
      },
      immediate: true // 어트랙션에 변경사항이 없어도 watcher가 생성될 때 즉시 호출함.
      // => 컴포넌트가 생성될 때도 updateDisplayedPageNumbers가 처음에 호출됨.
    }
  },
    }
    </script>
    
    <style>
    .active {
      background-color: #007bff;
      color: #fff;
    }
  </style>
    