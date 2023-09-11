import apiInstance from "@/api/http";
const http = apiInstance();

const itemStore = {
  namespaced: true,
  state: {
    sidos: [{ value: 0, text: "선택하세요" }],
    guguns: [{ value: 0, text: "선택하세요" }],
    // 12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점
    contents: [
      { value: 0, text: "선택하세요" },
      { value: 12, text: "관광지" },
      { value: 14, text: "문화시설" },
      { value: 15, text: "축제공연행사" },
      { value: 25, text: "여행코스" },
      { value: 28, text: "레포츠" },
      // { value: 32, text: "숙박" },
      { value: 38, text: "쇼핑" },
      { value: 39, text: "음식점" },
    ],
    selectedSido: 0,
    selectedGugun: 0,
    selectedContentType: 0,
  },
  getters: {
    getContentType: (state) => (value) => {
      let contentType = state.contents.find((content) => content.value == value);
      return contentType.text;
    },
  },
  mutations: {
    SET_SIDO_LIST(state, sidos) {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    CLEAR_SIDO_LIST(state) {
      state.sidos = [{ value: 0, text: "선택하세요" }];
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [{ value: 0, text: "선택하세요" }];
    },
  },
  actions: {
    getSido({ commit }) {
      http
        .get(`/attraction/sido`)
        .then(({ data }) => {
          console.log("action get Sido", data);
          commit("SET_SIDO_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getGugun({ commit }, sidoCode) {
      // const params = { sidoCode: sidoCode, sidoName: null };
      // console.log("params:", params);
      http
        .get(`/attraction/sido/${sidoCode}`)
        .then(({ data }) => {
          // console.log(commit, response);
          commit("SET_GUGUN_LIST", data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  modules: {},
};

export default itemStore;
