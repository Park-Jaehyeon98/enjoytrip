import apiInstance from "@/api/http";
const http = apiInstance();

const AttractionStore = {
  namespaced: true,
  state: {
    attractions: [],
  },
  mutations: {
    CLEAR_ATTRACTION_LIST(state) {
      state.attractions = [];
    },
    SET_ATTRACTION_LIST(state, attractions) {
      attractions.forEach((attraction) => {
        state.attractions.push(attraction);
      });
    },
    SORT_BY_NUM(state) {
      state.attractions.sort(function (a, b) {
        let num1 = a.contentId;
        let num2 = b.contentId;
        return num1 - num2; // 오름차순 정렬
      });
    },
    SORT_BY_LIKE(state) {
      state.attractions.sort(function (a, b) {
        let num1 = a.like;
        let num2 = b.like;
        return num2 - num1; // 내림차순 정렬
      });
    },
  },
  actions: {
    clearAttractions({ commit }) {
      commit("CLEAR_ATTRACTION_LIST");
    },
    getAttractionList({ commit }, searchObj) {
      console.log(searchObj);
      commit("CLEAR_ATTRACTION_LIST");
      let paramUrl = `?sidoCode=${searchObj.sidoCode}&gugunCode=${searchObj.gugunCode}&contentTypeId=${searchObj.contentTypeId}&title=${searchObj.title}`;
      http
        // .get("/attraction", searchObj)
        .get("/attraction" + paramUrl)
        .then(({ data }) => {
          // console.log(commit, response);
          commit("SET_ATTRACTION_LIST", data);
          console.log("getAttractionList:", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};

export default AttractionStore;
