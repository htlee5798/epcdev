<template>
    <div class="satisfy-review"
    v-if="isShow">
        <a href="wrapSticky" class="wrap-starpoint" id="btnReviews">
            <em class="starpointhead"></em>
        </a>
        <p class="review-cases">
            (<b><em class="number-point" name="reviewCnthead">0</em>)</b>
        </p>
    </div>
</template>

<script>
    import $ from 'jquery';

    import {
        PRODUCT_DETAIL_REVIEWS_BEST_REIVEW_LIST,
        PRODUCT_DETAIL_REVIEWS_CATCDYN,
        PRODUCT_DETAIL_REVIEWS_CURRENT_PAGE,
        PRODUCT_DETAIL_REVIEWS_IMGURL,
        PRODUCT_DETAIL_REVIEWS_PHOTO_TOTAL_COUNT,
        PRODUCT_DETAIL_REVIEWS_RELOAD_CNT_YN,
        PRODUCT_DETAIL_REVIEWS_REVIEW_LIST,
        PRODUCT_DETAIL_REVIEWS_ROW_PER_PAGE,
        PRODUCT_DETAIL_REVIEWS_TOTAL_COUNT
    } from "../../stores/types-product-detail";

    export default {
        name: "ProductDetailMainHeaderReview",
        computed : {
            isShow () {

            }
        },
        created () {

        }
    }
</script>

<style scoped>
    .starpointhead{
        width:0%
    }
</style>