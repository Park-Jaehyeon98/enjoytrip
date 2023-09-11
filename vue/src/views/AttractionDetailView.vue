<template>
  <div id="detail">
    <attraction-detail :contentId="contentId" id="attraction-detail"></attraction-detail>
    <!-- 댓글 -->
    <attraction-comment :contentId="contentId" id="attraction-comment"></attraction-comment>
  </div>
</template>

<script>
import AttractionDetail from "../components/attraction/detail/AttractionDetail.vue";
import AttractionComment from "../components/attraction/detail/AttractionComment.vue";

import apiInstance from "@/api/http";
// import { mapGetters } from "vuex";

// const itemStore = "itemStore";
// const userStore = "userStore";

const http = apiInstance();

export default {
  components: {
    AttractionDetail, AttractionComment
  },
  data() {
    return {
      contentId: parseInt(this.$route.params.contentId),
    };
  },
  methods : {
    getAttractInfo(){
      console.log(this.$route.params.contentId);
      console.log(this.contentId);
      http.get("/attraction/" + this.contentId).then(({ data }) => {
      console.log("detail:", data);
      this.attraction = data;
      // console.log(this.attraction);
      this.contentType = this.getContentType(data.contentTypeId);
      });
    },
    getLikeNum(){
      http.get(`/attraction/like/${this.contentId}`)
      .then((data)=>{
        this.like = data;
      })
      .catch((result)=>{
        console.log("좋아요 개수 가져오기 결과 : ",result)
      })
    },
    checkIfLiked(){
      let id = this.checkUserInfo.id;
      http.get(`/attraction/like/${this.contentId}/${id}`)
      .then((data)=>{
        if(data){
          this.isLiked = true;
        }else{
          this.isLiked = false;
        }
      })
      .catch((result)=>{
        console.log("현재 사용자가 좋아요 눌렀는지 검사할 수 없음=>", result)
      });
    },
    // 좋아요
    likeAttr() {
      let requestBody = {
        contentId: this.contentId,
        id: this.checkUserInfo.id,
      };
      http.put("/attraction/like", requestBody)
        .then(() => {
          console.log("좋아요 성공");
          this.checkIfLiked(); // Fetch updated like status
          this.getLikeNum(); // Fetch updated like count
        })
        .catch((error) => {
          console.error("좋아요 실패:", error);
        });
    },
    //좋아요 해제
    unlikeAttr() {
      let id = this.checkUserInfo.id;
      http.delete(`/attraction/like/${this.contentId}/${id}`)
        .then(() => {
          console.log("좋아요 해제 성공");
          this.checkIfLiked(); // Fetch updated like status
          this.getLikeNum(); // Fetch updated like count
        })
        .catch((error) => {
          console.error("좋아요 해제 실패:", error);
        });
    },
  },
};
</script>

<style src="@/assets/css/attraction_detail.css">

</style>
