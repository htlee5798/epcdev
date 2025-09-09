import {
    PRODUCT_DETAIL_REVIEWS_CATCDYN,
    PRODUCT_DETAIL_REVIEWS_IMGURL,
    PRODUCT_DETAIL_REVIEWS_BEST_REIVEW_LIST,
    PRODUCT_DETAIL_REVIEWS_CURRENT_PAGE,
    PRODUCT_DETAIL_REVIEWS_PHOTO_TOTAL_COUNT,
    PRODUCT_DETAIL_REVIEWS_RELOAD_CNT_YN,
    PRODUCT_DETAIL_REVIEWS_REVIEW_LIST,
    PRODUCT_DETAIL_REVIEWS_ROW_PER_PAGE,
    PRODUCT_DETAIL_REVIEWS_TOTAL_COUNT
} from "../types-product-detail";

const productDetailReview = {
    state : {
        catCdYn : '',
        imgUrl : '',
        bestReviewList : [],
        currentPage : 1,
        photoTotalCount : 0,
        reloadCntYN : 'N',
        reviewList : [],
        rowPerPage : 10,
        totalCount : 0
    },
    getters : {
        [PRODUCT_DETAIL_REVIEWS_CATCDYN](state) {
            return state.catCdYn;
        },
        [PRODUCT_DETAIL_REVIEWS_IMGURL](state) {
            return state.imgUrl;
        },
        [PRODUCT_DETAIL_REVIEWS_BEST_REIVEW_LIST](state){
            return state.bestReviewList;
        },
        [PRODUCT_DETAIL_REVIEWS_CURRENT_PAGE](state) {
            return state.currentPage;
        },
        [PRODUCT_DETAIL_REVIEWS_PHOTO_TOTAL_COUNT](state) {
            return state.photoTotalCount;
        },
        [PRODUCT_DETAIL_REVIEWS_RELOAD_CNT_YN](state) {
            return state.reloadCntYN;
        },
        [PRODUCT_DETAIL_REVIEWS_REVIEW_LIST](state) {
            return state.reviewList;
        },
        [PRODUCT_DETAIL_REVIEWS_ROW_PER_PAGE](state) {
            return state.rowPerPage;
        },
        [PRODUCT_DETAIL_REVIEWS_TOTAL_COUNT](state) {
            return state.totalCount;
        }
    },
    mutations : {
        [PRODUCT_DETAIL_REVIEWS_CATCDYN](state, value) {
            state.catCdYn = value;
        },
        [PRODUCT_DETAIL_REVIEWS_IMGURL](state, value) {
            state.imgUrl = value;
        },
        [PRODUCT_DETAIL_REVIEWS_BEST_REIVEW_LIST](state, value) {
            state.bestReviewList = value;
        },
        [PRODUCT_DETAIL_REVIEWS_CURRENT_PAGE](state, value) {
            state.currentPage = value;
        },
        [PRODUCT_DETAIL_REVIEWS_PHOTO_TOTAL_COUNT](state, value){
            state.photoTotalCount = value;
        },
        [PRODUCT_DETAIL_REVIEWS_RELOAD_CNT_YN](state, value){
            state.reloadCntYN = value;
        },
        [PRODUCT_DETAIL_REVIEWS_ROW_PER_PAGE](state, value) {
            state.rowPerPage = value;
        },
        [PRODUCT_DETAIL_REVIEWS_TOTAL_COUNT](state, value) {
            state.totalCount = value;
        }
    },
    actions : {
        [PRODUCT_DETAIL_REVIEWS_CATCDYN]({commit}, value) {
            commit(PRODUCT_DETAIL_REVIEWS_CATCDYN, value);
        },
        [PRODUCT_DETAIL_REVIEWS_IMGURL]({commit}, value) {
            commit(PRODUCT_DETAIL_REVIEWS_IMGURL, value);
        },
        [PRODUCT_DETAIL_REVIEWS_BEST_REIVEW_LIST]({commit}, value) {
            commit(PRODUCT_DETAIL_REVIEWS_BEST_REIVEW_LIST, value);
        },
        [PRODUCT_DETAIL_REVIEWS_CURRENT_PAGE]({commit}, value) {
            commit(PRODUCT_DETAIL_REVIEWS_CURRENT_PAGE, value);
        },
        [PRODUCT_DETAIL_REVIEWS_PHOTO_TOTAL_COUNT]({commit}, value) {
            commit(PRODUCT_DETAIL_REVIEWS_PHOTO_TOTAL_COUNT, value);
        },
        [PRODUCT_DETAIL_REVIEWS_RELOAD_CNT_YN]({commit}, value) {
            commit(PRODUCT_DETAIL_REVIEWS_RELOAD_CNT_YN, value);
        },
        [PRODUCT_DETAIL_REVIEWS_ROW_PER_PAGE]({commit}, value) {
            commit(PRODUCT_DETAIL_REVIEWS_ROW_PER_PAGE, value);
        },
        [PRODUCT_DETAIL_REVIEWS_TOTAL_COUNT]({commit}, value) {
            commit(PRODUCT_DETAIL_REVIEWS_TOTAL_COUNT, value);
        }
    }
};

export default productDetailReview;