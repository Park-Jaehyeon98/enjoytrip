import Vue from "vue";
import VueRouter from "vue-router";
// import HomeView from "../views/HomeView.vue";
import store from "@/store";

Vue.use(VueRouter);

// https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["userStore/checkUserInfo"];
  const checkToken = store.getters["userStore/checkToken"];
  let token = sessionStorage.getItem("access-token");
  console.log("로그인 처리 전", checkUserInfo, token);

  if (checkUserInfo != null && token) {
    console.log("토큰 유효성 체크하러 가자!!!!");
    await store.dispatch("userStore/getUserInfo", token);
  }
  if (!checkToken || checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "login" });
    // router.push({ name: "login" });

    // 유저스토어에 로그인 모달을 보여줄 것을 정의
    store.commit("modalStore/SET_SHOW_MODAL", true);
    store.commit("modalStore/SET_MODAL_TYPE", "login");
  } else {
    console.log("로그인 했다!!!!!!!!!!!!!.");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "main",
    component: () => import("../views/MainView.vue"),
  },
  //////////////////////////// attraction ///////////////////
  {
    path: "/attraction",
    name: "attraction",
    component: () => import("../views/AttractionView.vue"),
    // children: [
    //   {
    //   },
    // ],
  },
  {
    path: "/attractionDetail/:contentId",
    name: "attractionDetail",
    beforeEnter: onlyAuthUser,
    component: () => import("../views/AttractionDetailView.vue"),
  },
  // ////////////////board///////////////////////
  {
    path: "/board",
    name: "board",
    component: () => import("../views/BoardView.vue"),
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "boardList",
        component: () => import("../components/board/BoardList.vue"),
      },
      {
        path: "detail/:bno",
        name: "boardDetail",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "board" */ "@/components/board/BoardDetail"),
      },
      {
        path: "write",
        name: "boardWrite",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "board" */ "@/components/board/BoardWrite"),
      },
      {
        path: "edit/:bno",
        name: "boardEdit",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "board" */ "@/components/board/BoardEdit"),
      },
    ],
  },
  ////////////////// user ////////////////////
  {
    path: "/user",
    name: "user",
    component: () => import("../views/UserView.vue"),
    redirect: "/user/login",
    children: [
      {
        path: "regist",
        name: "regist",
        component: () => import("../components/user/UserRegist.vue"),
      },
      {
        path: "login",
        name: "login",
        component: () => import(/* webpackChunkName: "user" */ "../components/user/UserLogin"),
      },
      {
        path: "my",
        name: "my",
        redirect: "/user/my/list",
        beforeEnter: onlyAuthUser,
        component: () => import("../views/MyListView.vue"),
        children: [
          {
            path: "list",
            name: "myList",
            component: () => import("../components/user/mypage/mylist/MyList.vue"),
          },
        ],
      },
      {
        path: "like",
        name: "like",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/user/mypage/UserLikes.vue"),
      },
      {
        path: "userInfoEdit",
        name: "userInfoEdit",
        beforeEnter: onlyAuthUser,
        component: () => import("../components/user/mypage/UserInfoEdit.vue"),
      },
      {
        path: "searchNear/:contentId",
        name: "searchNear",
        beforeEnter: onlyAuthUser,
        component: () => import("@/components/user/mypage/UserLikeSearchNear.vue"),
      },
    ],
  },
  //////////////////// admin //////////////////////
  {
    path: "/admin",
    name: "admin",
    beforeEnter: onlyAuthUser,
    component: () => import("../views/AdminView.vue"),
    redirect: "/admin/userList",
    children: [
      {
        path: "userList",
        name: "userList",
        component: () => import("../components/admin/AdminUserList.vue"),
      },
    ],
  },

  // {
  //   path: "/about",
  //   name: "about",
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () =>
  //     import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
  // },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
