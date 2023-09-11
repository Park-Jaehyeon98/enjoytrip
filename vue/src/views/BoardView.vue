<template>
  <div>
    <!-- <transition :name="transitionName"> -->
    <transition>
      <router-view :key="$route.fullPath"></router-view>
    </transition>
  </div>
</template>

<script>
export default {
  name: "BoardView",
  data(){
    return{
      transitionName : 'slid-left'
    }
  },
  // beforeRouteUpdate (to, from, next) {
  //   const toDepth = to.path.split('/').length
  //   const fromDepth = from.path.split('/').length
  //   this.transitionName = toDepth < fromDepth ? 'slide-right' : 'slide-left'
  //   next()
  // },
};
</script>

<style>
.fade-enter-active, .fade-leave-active {
  transition: opacity .2s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}
 
</style>
