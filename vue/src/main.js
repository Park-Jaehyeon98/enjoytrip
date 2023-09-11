import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "@/api/vueBootstrap.js";
import * as VueGoogleMaps from "vue2-google-maps";

Vue.use(VueGoogleMaps, {
  load: {
    key: "AIzaSyBwEdKqeZ9A1d12Cg07hxcMjZCQMOzFJk0",
    libraries: "places",
    region: "KR",
  },
  installComponents: true,
});

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
