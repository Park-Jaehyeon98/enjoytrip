<template>
    <div class="pagination">
        <button @click="goToPreviousPage" :disabled="currentPage === 1">이전</button>

        <button
        v-for="pageNumber in displayedPageNumbers"
        :key="pageNumber"
        @click="goToPage(pageNumber)"
        :class="{ active: pageNumber === currentPage }"
        >
        {{ pageNumber }}
        </button>

        <button @click="goToNextPage" :disabled="currentPage === totalPages">다음</button>
  </div>
</template>

<script>
export default {
    props : ["displayedPageNumbers", "currentPage", "totalPages"],
    methods : {
        goToPreviousPage(){
            this.$emit("go-to-previous-page");
        },
        goToNextPage(){
            this.$emit("go-to-next-page")
        },
        goToPage(pageNumber){
            this.$emit("go-to-page", pageNumber);
        }
    
    }
}
</script>

<style lang="scss">

</style>