<template>
  <div id="my-list">
    <h1>내가 쓴 게시글 / 댓글</h1> 

    <!-- <b-btn @click="$roter.push({name:'myBoardComment'})">게시글</b-btn>
    <b-btn @click="$router.push({name : 'myBoardCommentList'})">게시글 댓글</b-btn>
    <b-btn @click="$router.push({name : 'myAttractionCommentList'})">여행지 댓글</b-btn> -->

    <b-button-group id="button-group">
      <b-btn variant="outline-primary" @click="showBoardList">게시글</b-btn>
      <b-btn variant="outline-primary" @click="showBoardCommentList">게시글 댓글</b-btn>
      <b-btn variant="outline-primary" @click="showAttractionCommentList">여행지 댓글</b-btn>
    </b-button-group>

    <div v-if="currentTab === 'boardList'" class="my-list-list" id="table">
      <div class="row">
          <div class="cell">번호</div>
          <div class="cell">제목</div>
          <div class="cell">조회수</div>
          <div class="cell">작성시간</div>
      </div>
      <my-board-list></my-board-list>
    </div>

    <div v-if="currentTab === 'boardCommentList'" class="my-list-list" id="table">
      <!-- 게시글 댓글 목록 표시 -->
      <div class="row">
        <div class="cell">게시글 번호</div>
        <div class="cell">게시글 제목</div>
        <div class="cell">댓글 번호</div>
        <div class="cell">내용</div>
        <div class="cell">작성시간</div>
      </div>
      <my-board-comment-list></my-board-comment-list>
    </div>

    <div v-if="currentTab === 'attractionCommentList'" class="my-list-list" id="table">
      <!-- 여행지 댓글 목록 표시 -->
      <div class="row">
        <div class="cell">관광지 번호</div>
        <div class="cell">관광지 이름</div>
        <div class="cell">댓글 번호</div>
        <div class="cell">내용</div>
        <div class="cell">작성 시간</div>
      </div>
      <my-attraction-comment-list></my-attraction-comment-list>
    </div>
  </div>
</template>

<script>
import MyBoardList from "./MyBoardList.vue";
import MyBoardCommentList from "./MyBoardCommentList.vue";
import MyAttractionCommentList from "./MyAttractionCommentList.vue";
export default {
  data() {
    return {
      currentTab: 'boardList', // 현재 선택된 탭
    };
  },
  components: {
    MyBoardList, MyBoardCommentList, MyAttractionCommentList
  },
  methods: {
    showBoardList() {
      this.currentTab = 'boardList';
    },
    showBoardCommentList() {
      this.currentTab = 'boardCommentList';
    },
    showAttractionCommentList() {
      this.currentTab = 'attractionCommentList';
    },
  },
};
</script>

<style scoped>
/* 필요한 스타일을 추가하세요 */
h1{
  font-weight: 800;
    font-size: 65px;
    text-align: center;
    display: inline;
    background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    #3287ff50 30%
  );
}
#my-list{
  padding : 60px
}
.my-list-list{
  margin-top: 50px;
}
#button-group{
  margin-top : 50px;
  width: 100%;
}
</style>