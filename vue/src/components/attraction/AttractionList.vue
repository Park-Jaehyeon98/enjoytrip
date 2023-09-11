<template>
  <div>
    <b-button-group size="sm">
      <b-button pill variant="outline-secondary" @click="sortList('num')">번호 순 정렬</b-button>
      <b-button  pill variant="outline-secondary" @click="sortList('like')">좋아요 순 정렬</b-button>
    </b-button-group>

    <div id="table">
      <div class="row">
        <div class="cell">번호</div>
        <div class="cell">이름</div>
        <div class="cell">좋아요 수</div>
      </div>
      <attraction-list-item
        v-for="(attraction, index) in pagedAttractions"
        :key="index"
        :attraction="attraction"
      ></attraction-list-item>
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
</template>

<script>
import AttractionListItem from "../attraction/AttractionListItem.vue";
import PageComponent from "../PageComponent.vue";
import { mapState, mapActions, mapMutations } from "vuex";
const attractionStore = "attractionStore";
export default {
  components: { AttractionListItem, PageComponent },
  data() {
    return{
      sortState : false, // false: 숫자로 정렬, true : 좋아요로 정렬
      // 페이징용 data 시작
      currentPage: 1,    // 현재 페이지
      pageSize: 10,       // 한 페이지에 나타날 관광지 수
      displayedPageNumbers: [] // 페이징 네비게이션에 나올 버튼들
      // 페이징용 끝
    }
  },
  created() {
    console.log("attraction list:", this.attractions);
    this.clearAttractions(); // list 초기화
    // 페이징 시작
    this.currentPage = 1; // 초기에 현재 페이지 값 설정
    this.updateDisplayedPageNumbers(); // 페이지 네비게이션에 숫자 버튼들 생성
    // 페이징 끝
  },
  computed: {
    ...mapState(attractionStore, ["attractions"]),
    /* paging 시작 */
    totalPages() {
      return Math.ceil(this.attractions.length / this.pageSize);
    },
    pagedAttractions() { // attractions를 paging해서 나타내는 메소드. 
      // 페이징하는 컴포넌트의 data에 따라 이름을 바꿔 써야 함
      const startIndex = (this.currentPage - 1) * this.pageSize;
      const endIndex = startIndex + this.pageSize;
      return this.attractions.slice(startIndex, endIndex);
    }
    // 페이징 끝
  },
  watch: {
    attractions: { // 페이징용
      handler() {
        this.currentPage = 1
        this.updateDisplayedPageNumbers();
      },
      immediate: true // 어트랙션에 변경사항이 없어도 watcher가 생성될 때 즉시 호출함.
      // => 컴포넌트가 생성될 때도 updateDisplayedPageNumbers가 처음에 호출됨.
    }
  },
  methods : {
    ...mapActions(attractionStore, ["clearAttractions"]),
    ...mapMutations(attractionStore, {
      sortByNum : "SORT_BY_NUM",
      sortByLike : "SORT_BY_LIKE"
    }),
    sortList(op){
      if(op == "num"){
        if(this.sortState){ // 좋아요로 정렬돼있을때만 숫자정렬
          this.sortByNum();
          this.sortState = false;
        }
      }else{
        if(!this.sortState){
          this.sortByLike();
          this.sortState = true
        }
      }
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
  }
};
</script>

<style>
  .active {
    background-color: #007bff;
    color: #fff;
  }
</style>

