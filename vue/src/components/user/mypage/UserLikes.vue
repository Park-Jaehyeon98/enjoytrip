<template>
  <div id="likes">
    <h1>내가 좋아하는 관광지 목록</h1>

    <b-card-group deck id="like-card-group">
      <div v-for="like in pagedLikes" :key="like.contentId">
        <b-card v-if="like.firstImage==''" :title="like.title" :img-src='require("../../../assets/img/howthere.png")'>
          <b-btn variant="outline-primary" @click="$router.push({name : 'attractionDetail', params : {contentId : like.contentId}})">관광지 상세정보</b-btn><br>
          <b-btn variant="outline-primary" @click="$router.push({name : 'searchNear', params : {contentId:like.contentId}})">숙박업소 찾기</b-btn>
        </b-card>
        <b-card v-else :title="like.title" :img-src=" like.firstImage ">
            <b-btn variant="outline-primary" @click="$router.push({name : 'attractionDetail', params : {contentId : like.contentId}})">관광지 상세정보</b-btn><br>
            <b-btn variant="outline-primary" @click="$router.push({name : 'searchNear', params : {contentId:like.contentId}})">숙박업소 찾기</b-btn>
        </b-card>
      </div>
    </b-card-group>

    <!-- 페이징 컴포넌트 -->
    <page-component
        :displayedPageNumbers="displayedPageNumbers"
        :currentPage="currentPage"
        @go-to-previous-page="goToPreviousPage"
        @go-to-next-page="goToNextPage"
        @go-to-page="goToPage"
        >
    </page-component>
    <!-- 페이징 컴포넌트 끝 -->
    <!-- <router-view></router-view> -->
  </div>
</template>

<script>

import PageComponent from "@/components/PageComponent.vue";


import { mapState} from "vuex";
const userStore = "userStore";

import apiInstance from "@/api/http";
const http = apiInstance();

export default {
  components: {
    PageComponent
    },
    data(){
        return {
            likes: [],
            currentPage: 1,    // 현재 페이지
            pageSize: 8,       // 한 페이지에 나타날 관광지 수
            displayedPageNumbers: [] // 페이징 네비게이션에 나올 버튼들
        }
    },
    computed: {
        ...mapState(userStore, ["userInfo"]),
         /* paging 시작 */
    totalPages() {
        return Math.ceil(this.likes.length / this.pageSize);
    },
    pagedLikes() { // attractions를 paging해서 나타내는 메소드. 
        // 페이징하는 컴포넌트의 data에 따라 이름을 바꿔 써야 함
        const startIndex = (this.currentPage - 1) * this.pageSize;
        const endIndex = startIndex + this.pageSize;
        return this.likes.slice(startIndex, endIndex);
      },
  },
    created(){
        let id = this.$store.getters["userStore/checkUserInfo"].id;
        http.get(`/attraction/like?userId=${id}`)
        .then(({data})=>{
            console.log("userlikes", data);
            this.likes = data;
        }),
        // 페이징 시작
    this.currentPage = 1; // 초기에 현재 페이지 값 설정
    this.updateDisplayedPageNumbers(); // 페이지 네비게이션에 숫자 버튼들 생성
    // 페이징 끝
    },
    methods: {
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
    likes: { // 페이징용
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

<style scoped>
  .active {
    background-color: #007bff;
    color: #fff;
  }
  #likes {
    padding : 60px
  }
  h1{
    /* margin-top: 50px; */
    font-weight: 800;
    font-size: 65px;
    text-align: center;
    display: inline-block;
    background: linear-gradient(
      180deg,
      rgba(255, 255, 255, 0) 70%,
      #3287ff50 30%
    );
  }
  #like-card-group{
    margin-top: 50px;
    width : 100%;
    max-width: 100%;
    margin-left: auto;
    margin-right: auto;
  }

  #like-card-group img{
    width : 400px;
    height: 200px;
  }
  .card{
    /* margin-left : auto;
    margin-right: auto; */
    margin-bottom: 30px;
  }
  .card-title{
    font-weight: 800;
    text-align: center;
  }
  .card-body button{
    margin-bottom : 10px;
    width : 100%
  }
</style>