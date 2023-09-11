<template>
    <div id="board-detail">
        <div id="header">
            <h1>{{ board.subject }}</h1>
            
            <div id="info-block">
                <span>글쓴이 : {{ board.id }}, 작성일 : {{ board.registerTime }} , 조회수 : {{ board.hit }}</span>
            </div>
        </div>

        <hr>

        <div id="content-block">
            <p>
                {{ board.content }}
            </p>
                
        </div>
        <b-btn variant="outline-primary" v-if="this.userInfo.id == board.id" @click="openModal">수정</b-btn>
        <b-btn variant="outline-primary" v-if="this.userInfo.id == board.id" @click="removeBoard">삭제</b-btn>
        <!-- 댓글영역 -->
        <board-comment :bno="bno"></board-comment>

        <!-- 모달 컴포넌트 -->
        <transition name="fade">
            <BoardEdit v-if="showModal && showModalType === 'editBoard'" @close-modal="closeModal"/>
        </transition>
    </div>
</template>

<script>
import BoardEdit from "./BoardEdit.vue";
import { mapState, mapGetters} from "vuex";
const userStore = "userStore";
const modalStore = "modalStore";

import apiInstance from "@/api/http";
const http = apiInstance();

import BoardComment from "./BoardComment.vue";

export default {
    components : {BoardComment, BoardEdit},
    data(){
        return{
            bno : this.$route.params.bno,
            board : {
                // content : String,
                // hit : Number,
                // id : String,
                // registerTime : String,
                // subject : String
            }
        }
    },
    created(){
        this.getBoard();
    },
    computed : {
        ...mapState(userStore, ["userInfo"]),
        ...mapGetters(modalStore, ["showModal", "showModalType"]),
    },
    methods :{
        removeBoard(){
            http.delete(`/board/${this.bno}`)
            .then(({data})=>{
                console.log(data)
                if(data > 0){ // 게시물 삭제하는 sql실행하면 결과가 반영된 행의 개수를 반환하도록 함
                    this.$router.push("/board")
                }else {
                    alert("삭제 실패");
                }
            })
        },
        getBoard() {
            http.get(`/board/${this.bno}`)
            .then(({data})=>{
                // console.log(data);
                this.board = data;
            })
            console.log(this.userInfo)
        },
        // 모달용 메소드
        openModal() {
            this.$store.commit("modalStore/SET_MODAL_TYPE", "editBoard");
            this.$store.commit("modalStore/SET_SHOW_MODAL", true);
        },
        closeModal() {
            this.$store.commit("modalStore/SET_SHOW_MODAL", false);
            this.getBoard();

        },
    }
};
</script>

<style scoped src="@/assets/css/board_detail.css"></style>
