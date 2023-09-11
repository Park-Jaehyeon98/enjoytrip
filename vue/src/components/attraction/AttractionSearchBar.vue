<template>
  <div style="z-index:1">
    <h1>관광지 검색</h1>
    <!-- 시, 도 -->
    <h3>시, 도 선택</h3>
    <b-form-select
      v-model="sidoCode"
      :options="sidos"
      @change="gugunList"
    ></b-form-select>
    <!-- 구, 군 -->
    <h3>시, 구, 군, 선택</h3>
    <b-form-select v-model="gugunCode" :options="guguns"></b-form-select>
    <!-- 관광지 유형 -->
    <h3>관광지 유형 선택</h3>
    <b-form-select
      v-model="contentTypeId"
      :options="contents"
    ></b-form-select>
    <!-- 검색어 -->
    <h3>키워드</h3>
    <b-form-input v-model="title" @keyup.enter="search" placeholder="검색어를 입력하세요"></b-form-input>
    <b-btn variant="outline-primary" @click="search" style="margin-top: 30px; width: 100%;">검색</b-btn>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
const itemStore = "itemStore";
const attractionStore = "attractionStore";

export default {
  name: "AttractionSearchBar",
  data() {
    return {
      sidoCode: 0,
      gugunCode: 0,
      contentTypeId: 0,
      title: "",
    };
  },
  computed: {
    ...mapState(itemStore, ["sidos", "guguns", "contents"]),
    ...mapState(attractionStore, ["attractions"]),
  },
  created() {
    console.log(this.$store);
    // this.$store.commit("CLEAR_SIDO_LIST");
    // this.$store.dispatch("getSido");
    this.CLEAR_SIDO_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(itemStore, ["getSido", "getGugun"]),
    ...mapActions(attractionStore, ["getAttractionList"]),
    ...mapMutations(itemStore, ["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST"]),
    ...mapMutations(attractionStore, ["CLEAR_ATTRACTION_LIST"]),
    gugunList() {
      // console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = 0;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    search() {
      this.CLEAR_ATTRACTION_LIST();
      const obj = {
        // contentId: 0,
        sidoCode: this.sidoCode,
        gugunCode: this.gugunCode,
        contentTypeId: this.contentTypeId,
        title: this.title,
      };
      this.getAttractionList(obj);
      console.log(this.attractions);
    },
  },
};
</script>

<style></style>
