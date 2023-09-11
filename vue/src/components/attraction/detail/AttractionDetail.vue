<template>
    <div>
      <div id="header">
        <!-- 제목 -->
        <h1>{{ attraction.title }}</h1>
        
        <!-- 좋아요 -->
        <div id="like-block">
          <span>좋아요 수 : {{like}}</span>
          <img src="@/assets/img/like128.png" alt="" class="likeButton" @click="likeAttr" v-if="!isLiked">
          <img src="@/assets/img/liked128.png" alt="" class="likeButton" @click="unlikeAttr" v-if="isLiked">
        </div>
      </div><br>
      <h2>이 장소는 <b>{{ contentType }}</b>(으)로 즐기기 적합해요!</h2>
      <hr>
      
      <div id="image">
        <b-card :img-src="attraction.firstImage" img-alt="Card image" img-left width="100%">
          <b-card-text style="font-size:17px">
            {{ attraction.overview }}
          </b-card-text>
        </b-card>
      </div>  
      <div id="map-area">
        <attraction-map
          :latitude="attraction.latitude"
          :longitude="attraction.longitude"
        ></attraction-map>
      </div>
    </div>
  </template>
  
  <script>
  import AttractionMap from "./AttractionMap.vue";
  
  import apiInstance from "@/api/http";
  import { mapGetters } from "vuex";
  
  const itemStore = "itemStore";
  const userStore = "userStore";
  
  const http = apiInstance();
  
  export default {
    components: {
      AttractionMap
    },
    props : {
        contentId : Number
    },
    data() {
      return {
        attraction: {},
        contentType: null,
        isLiked : false, // 현재 사용자가 좋아요를 누른 전적이 있는지 검사
        like : null
      };
    },
    computed: {
      ...mapGetters(itemStore, ["getContentType"]),
      ...mapGetters(userStore, ["checkUserInfo"]), // 좋아요 눌렀는지 확인용
      // this.contentType = this.$store.getters.getContentType;
    },
    created() {
      // 관광지 정보 가져오기
      this.getAttractInfo();
      // 좋아요 개수 가져오기
      this.getLikeNum();
      // 현재 사용자가 좋아요를 눌렀는지 검사
      this.checkIfLiked();
    },
    methods : {
      getAttractInfo(){
        //console.log(this.$route.params.contentId);
        //console.log(this.contentId);
        http.get("/attraction/" + this.contentId).then(({ data }) => {
          //console.log("detail:", data);
          this.attraction = data;
          // //console.log(this.attraction);
          this.contentType = this.getContentType(data.contentTypeId);
        });
      },
      getLikeNum(){
        http.get(`/attraction/like/${this.contentId}`)
        .then(({data})=>{
            //console.log("좋아요 수: ",data)
          this.like = data;
        })
        .catch((result)=>{
          console.log("좋아요 개수 가져오기 결과 : ",result)
        })
      },
      checkIfLiked(){
        let id = this.checkUserInfo.id;
        http.get(`/attraction/like/${this.contentId}/${id}`)
        .then(({data})=>{
            //console.log("checkIfLiked: ", data)
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
            //console.log("좋아요 성공");
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
            //console.log("좋아요 해제 성공");
            this.checkIfLiked(); // Fetch updated like status
            this.getLikeNum(); // Fetch updated like count
          })
          .catch((error) => {
            console.error("좋아요 해제 실패:", error);
          });
      },
    },
    watch : {
      isLiked(){
        this.getLikeNum();
      }
    }
  };
  </script>
  
  <style>
    .likeButton{
      cursor : pointer;
      width : 50px;
    }
  </style>
  