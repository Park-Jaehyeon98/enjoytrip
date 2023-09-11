import jwtDecode from "jwt-decode";
import router from "@/router";
import { login, findById, tokenRegeneration, logout } from "@/api/user";

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
    isValidToken: false,
    showModal: false,
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
    checkToken: function (state) {
      return state.isValidToken;
    },
    checkLoginState: function (state) {
      return state.isLogin;
    },
    showModal: function (state) {
      return state.showModal;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
      // Vue.set(state.isLogin, isLogin);
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_IS_VALID_TOKEN: (state, isValidToken) => {
      state.isValidToken = isValidToken;
    },
    SET_USER_INFO: (state, userInfo) => {
      // state.isLogin = true;
      state.userInfo = userInfo;
      console.log("userStore - setuserinfo - userinfo:", state.userInfo);
    },
    SET_SHOW_LOGIN_MODAL: (state, showModal) => {
      state.showModal = showModal;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["access-token"];
            let refreshToken = data["refresh-token"];
            console.log("login success token created!!!! >> ", accessToken, refreshToken);
            commit("SET_IS_LOGIN", true);
            console.log("SET_IS_LOGIN 실행");
            commit("SET_IS_LOGIN_ERROR", false);
            console.log("SET_IS_LOGIN_ERROR 실행");
            commit("SET_IS_VALID_TOKEN", true);
            console.log("SET_IS_VALID_TOKEN 실행");
            sessionStorage.setItem("access-token", accessToken);
            console.log("access-token 실행");
            sessionStorage.setItem("refresh-token", refreshToken);
            console.log("refresh-token 실행");
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getUserInfo({ commit, dispatch }, token) {
      console.log("getUserInfo token:", token);
      let decodeToken = jwtDecode(token);
      console.log("getUserInfo decodeToken:", decodeToken);

      await findById(
        decodeToken.userid,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_USER_INFO", data.userInfo);
            console.log("3. getUserInfo data >> ", data);
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        async (error) => {
          console.log(
            "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
            error.response.status
          );
          commit("SET_IS_VALID_TOKEN", false);
          await dispatch("tokenRegeneration");
        }
      );
    },
    async tokenRegeneration({ commit, state }) {
      console.log("토큰 재발급 >> 기존 토큰 정보 : {}", sessionStorage.getItem("access-token"));
      await tokenRegeneration(
        JSON.stringify(state.userInfo),
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["access-token"];
            console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
            sessionStorage.setItem("access-token", accessToken);
            commit("SET_IS_VALID_TOKEN", true);
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === 401) {
            console.log("갱신 실패");
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await logout(
              state.userInfo.userid,
              ({ data }) => {
                if (data.message === "success") {
                  console.log("리프레시 토큰 제거 성공");
                } else {
                  console.log("리프레시 토큰 제거 실패");
                }
                alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
                commit("SET_IS_LOGIN", false);
                commit("SET_USER_INFO", null);
                commit("SET_IS_VALID_TOKEN", false);
                router.push({ name: "login" });
              },
              (error) => {
                console.log(error);
                commit("SET_IS_LOGIN", false);
                commit("SET_USER_INFO", null);
              }
            );
          }
        }
      );
    },
    /* 원래 버전 */
    async userLogout({ state, commit }, userid) {
      await logout(
        userid,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_IS_LOGIN", false);
            console.log("userLogout - isLogin :", state.isLogin);
            console.log("SET_IS_LOGIN false로 변경");
            // console.log("data message : ", data.message);
            commit("SET_USER_INFO", null);
            commit("SET_IS_VALID_TOKEN", false);
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // async userLogout({ commit }, userid) {
    //   return new Promise((resolve, reject) => {
    //     logout(
    //       userid,
    //       ({ data }) => {
    //         if (data.message === "success") {
    //           commit("SET_IS_LOGIN", false);
    //           commit("SET_USER_INFO", null);
    //           commit("SET_IS_VALID_TOKEN", false);
    //           resolve(); // Resolve the promise when logout is successful
    //         } else {
    //           reject(); // Reject the promise when logout fails
    //         }
    //       },
    //       (error) => {
    //         console.log(error);
    //         reject(); // Reject the promise if an error occurs
    //       }
    //     );
    //   });
    // },
  },
};

export default userStore;
