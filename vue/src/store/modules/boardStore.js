import apiInstance from "@/api/http";
const http = apiInstance();

const boardStore = {
  namespaced: true,
  state: {
    boardList: [],
  },
  getters: {
    boardListGetter(state) {
      return state.boardList;
    },
  },
  mutations: {
    SET_BOARD_LIST(state, boardList) {
      state.boardList = boardList;
    },
  },
  actions: {
    getBoardList: ({ commit }) => {
      http.get(`/board`).then(({ data }) => {
        // this.boardList = data;
        console.log("data:", data);
        commit("SET_BOARD_LIST", data);
      });
    },
    searchKeyword: ({ commit }, searchCondition, keyword) => {
      http.get(`/board/search?condition=${searchCondition}&keyword=${keyword}`).then(({ data }) => {
        // console.log("list:", this.boardList, "data:", data);
        commit("SET_BOARD_LIST", data);
      });
    },
  },
};

export default boardStore;
