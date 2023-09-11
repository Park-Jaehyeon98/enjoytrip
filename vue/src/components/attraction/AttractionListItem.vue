<template>
  <div @click="goDetail" class="row">
    <div class="cell">{{ attraction.contentId }}</div>
    <div class="cell">
      <b>{{ attraction.title }}</b>
    </div>
    <div class="cell">
      {{ attraction.like }}
    </div>
  </div>
</template>

<script>
export default {
  props: {
    attraction: Object,
  },
  methods: {
    goDetail() {
      this.$router.push({
        name: "attractionDetail",
        params: { contentId: this.attraction.contentId },
      });
    },
  },
  created() {
    // console.log("this attraction", this.attraction);
  },
};
</script>

<style></style>
