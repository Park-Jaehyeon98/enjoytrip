<template>
  <div id="board-header">
    <h1>게시물</h1>
      <div class="input-wrapper">
        <b-form-select v-model="searchCondition" :options="options">
        </b-form-select>
        <b-form-input
          v-model="keyword"
          placeholder="검색어.."
          @keypress.enter="sendKeyword"
        >
        </b-form-input>
        <b-btn variant="outline-primary" @click="sendKeyword">검색</b-btn>
      </div>
      
    <!-- <router-view></router-view> -->
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchCondition: "",
      options: ["제목", `제목+내용`, "작성자"],
      keyword: "",
    };
  },
  methods: {
    sendKeyword() {
      console.log("send keyword", this.searchCondition, this.keyword);
      this.$emit("search", this.searchCondition, this.keyword);
    },
  },
};
</script>

<style scoped>
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
    margin-bottom: 50px;
  }
</style>
