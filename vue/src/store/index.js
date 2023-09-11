import Vue from "vue";
import Vuex from "vuex";
// import http from "@/util/http";
import createPersistedState from "vuex-persistedstate";
import boardStore from "@/store/modules/boardStore";
import attractionStore from "@/store/modules/attractionStore";
import userStore from "@/store/modules/userStore";
import itemStore from "@/store/modules/item/itemStore";
import modalStore from "@/store/modules/modalStore";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    boardStore,
    attractionStore,
    itemStore,
    userStore,
    modalStore,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
    }),
  ],
});
