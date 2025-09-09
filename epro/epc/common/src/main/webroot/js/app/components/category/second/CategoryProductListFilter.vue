<template>
    <div class="wrap-filter">
        <p class="title">전체<i>({{totalCount}})</i></p>
        <sorting-kind-view></sorting-kind-view>
        <a href="#globalSearchLayer"
           @click.stop.prevent="onSearchLayer"
           class="open-filter">상세검색</a>
        <div class="wrap-goods-trans">
            <list-type></list-type>
        </div>
    </div>
</template>

<script>
    import {mapGetters} from 'vuex';
    import {CATEGORY_TOTAL_COUNT} from "../../../stores/types-category";
    import SortingKindView from "../../../share/SortingKindView";
    import ListType from "../../../share/ListType";
    import SearchFilter from "../../../share/SearchFilter";
    import {
        SEARCH_FILTER_OPEN,
        SEARCH_FILTER_TOTAL_COUNT
    } from "../../../stores/types-search-filter";

    export default {
        components: {
            SearchFilter,
            ListType,
            SortingKindView
        },
        name: "CategoryProductListFilter",
        computed : {
            ...mapGetters({
                totalCount : CATEGORY_TOTAL_COUNT
            })
        },
        methods : {
            onSearchLayer () {
                this.$store
                    .dispatch(SEARCH_FILTER_TOTAL_COUNT, this.totalCount)
                    .then(() => {
                        this.$store.dispatch(SEARCH_FILTER_OPEN);
                    });
            }
        }
    }
</script>

<style scoped>

</style>