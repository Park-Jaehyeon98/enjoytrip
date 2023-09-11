<template>
  <div id="attraction-view">
    <div id="search-bar">
      <attraction-search-bar></attraction-search-bar>
    </div>
    <attraction-list id="attraction-list"></attraction-list>
  </div>
</template>

<script>
import AttractionSearchBar from "@/components/attraction/AttractionSearchBar.vue";
import AttractionList from "../components/attraction/AttractionList.vue";

export default {
  components: { AttractionSearchBar, AttractionList },
  
};
</script>

<style src="@/assets/css/attraction_view.css">

</style>
