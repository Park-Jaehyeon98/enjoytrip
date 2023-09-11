b<template>
  <div id="board-main">
    <board-header @search="search"></board-header>
    <div style="text-align: left;">
      <b-btn variant="primary" @click="openModal()">글작성</b-btn>
    </div>
    <div id="table">
      <div class="row">
        <div class="cell">글번호</div>
        <div class="cell">제목</div>
        <div class="cell">작성자</div>
        <div class="cell">조회수</div>
        <div class="cell">작성일</div>
      </div>
      <div class="row" v-for="(board, index) in pagedBoards" :key="index">
        <div class="cell">{{ board.bno }}</div>
        <div class="cell"><b-link :to="{name : 'boardDetail', params:{bno : board.bno}}">{{ board.subject }}</b-link></div>
        <div class="cell">{{board.id}}</div>
        <div class="cell">{{ board.hit }}</div>
        <div class="cell">{{ board.registerTime }}</div>
      </div>
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

    <!-- 모달 컴포넌트 -->
    <transition name="fade">
      <BoardWrite v-if="showModal && showModalType === 'write'" @close-modal="closeModal"/>
    </transition>

    
  </div>
</template>

<script>
import BoardHeader from "./BoardHeader.vue";
import BoardWrite from "./BoardWrite.vue";
import PageComponent from "@/components/PageComponent.vue";
import { mapState, mapActions, mapGetters } from "vuex";
const boardStore = "boardStore";
const userStore = "userStore";
const modalStore = "modalStore";

import apiInstance from "@/api/http";
const http = apiInstance();

export default {
  name: "BoardList",
  data() {
    return {
      boards: [],
      currentPage: 1,    // 현재 페이지
      pageSize: 10,       // 한 페이지에 나타날 관광지 수
      displayedPageNumbers: [] // 페이징 네비게이션에 나올 버튼들
    };
  },
  computed: {
    ...mapState(boardStore, ["boardList"]),
    ...mapState(userStore, ["userInfo"]),
    ...mapGetters(boardStore, ["boardListGetter"]),
    ...mapGetters(modalStore, ["showModal", "showModalType"]),

    totalPages() {
        return Math.ceil(this.boards.length / this.pageSize);
    },
    pagedBoards() { // attractions를 paging해서 나타내는 메소드. 
        // 페이징하는 컴포넌트의 data에 따라 이름을 바꿔 써야 함
        const startIndex = (this.currentPage - 1) * this.pageSize;
        const endIndex = startIndex + this.pageSize;
        return this.boards.slice(startIndex, endIndex);
      },
  },
  components: {
    BoardHeader, BoardWrite, PageComponent
  },
  created() {
    /* vuex 버전 */
    // this.getBoardList();
    // // getters
    // console.log(this.boardListGetter)
    // this.list = this.boardListGetter

    /* 그냥 버전 */
    this.getBoards();
  },
  methods: {
    ...mapActions(boardStore, ["getBoardList", "searchKeyword"]),
    
    //* vuex버전 */
    // search(searchCondition, keyword) {
    //   // this.searchKeyword(searchCondition, searchKeyword);
    //   // this.list = this.boardList;
    //     // console.log("list:", this.boardList, "data:", data);
    //     // commit("SET_BOARD_LIST", data);
    //   });
    //   this.$router.push("/board").catch(() => {})
    // },
    getBoards() {
      http.get(`/board`).then(({ data }) => {
        // this.boardList = data;
        console.log("data:", data);
        // commit("SET_BOARD_LIST", data);
        this.boards = data;
        console.log(this.boards);
      });
    },
    search(searchCondition, keyword) {
      http.get(`/board/search?condition=${searchCondition}&keyword=${keyword}`).then(({ data }) => {
        this.boards = data;
        console.log(this.boards);
      });
      // this.$router.push("/board").catch(() => {})
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
    },
    // 모달용
    openModal() { 
      this.$store.commit("modalStore/SET_MODAL_TYPE", "write");
      this.$store.commit("modalStore/SET_SHOW_MODAL", true);
    },
    closeModal() {
      this.$store.commit("modalStore/SET_SHOW_MODAL", false);
      this.getBoards();
    },
  },
  watch: {
    boards: { // 페이징용
      handler() {
        this.currentPage = 1
        this.updateDisplayedPageNumbers();
      },
      immediate: true // 어트랙션에 변경사항이 없어도 watcher가 생성될 때 즉시 호출함.
      // => 컴포넌트가 생성될 때도 updateDisplayedPageNumbers가 처음에 호출됨.
    }
  },
};
</script>

<style>
  .active {
    background-color: #007bff;
    color: #fff;
  }
  #board-main{
    padding : 60px
  }
  #table{
    margin-top: 50px;
    /* min-height: 100vh; */
  }
  #write-btn{
    position: relative;
    left : calc(100vw - 120px - 42px);
  }
</style>
