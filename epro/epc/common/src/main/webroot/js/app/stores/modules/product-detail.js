import headerSubDetail from './header-sub-detail';
import productDetailVideoPlayer from './product-detail-video-player';
import productDetailReview from './product-detail-reviews';

import {
    PRODUCT_DETAIL_PRODUCT_INFO,
    PRODUCT_DETATL_PRODUCT_CODE,
    PRODUCT_DETAIL_CATEGORY_ID,
    PRODUCT_DETAIL_ANNIVERSARY_TITLE,
    PRODUCT_DETAIL_LM_SERVER_TYPE,
    PRODUCT_DETAIL_MEMBER_YN,
    PRODUCT_DETAIL_LPOINT_SERVICE_YN,
    PRODUCT_DETAIL_GUEST_MEMBER_TYPE,
    PRODUCT_DETAIL_GUEST_MEMBER_YN,
    PRODUCT_DETAIL_VIDEO_URL,
    PRODUCT_DETAIL_VIDEO_TYPE,
    PRODUCT_DETAIL_LIST
} from "../types-product-detail";

const productDetail = {
    state: {
        productInfo: {},
        productCode: '',
        categoryId: '',
        anniversaryTitle: '',
        memberYn: $.utils.config('Member_yn'),
        lpointServiceYn: $.utils.config('LPOINT_SERVICE_YN'),
        lmServerType: $.utils.config('LMServerType'),
        guestMemberYn: $.utils.config('GuestMember_yn'),
        guestMemberType: $.utils.config('GuestMember_type'),
        videoUrl: '',
        videoType: '',
        list: []
    },
    getters: {
        [PRODUCT_DETAIL_PRODUCT_INFO](state) {
            return state.productInfo;
        },
        [PRODUCT_DETATL_PRODUCT_CODE](state) {
            return state.productCode;
        },
        [PRODUCT_DETAIL_CATEGORY_ID](state) {
            return state.categoryId;
        },
        [PRODUCT_DETAIL_ANNIVERSARY_TITLE](state) {
            return state.anniversaryTitle;
        },
        [PRODUCT_DETAIL_LM_SERVER_TYPE](state) {
            return state.lmServerType;
        },
        [PRODUCT_DETAIL_MEMBER_YN](state) {
            return state.memberYn;
        },
        [PRODUCT_DETAIL_LPOINT_SERVICE_YN](state) {
            return state.lpointServiceYn;
        },
        [PRODUCT_DETAIL_GUEST_MEMBER_TYPE](state) {
            return state.guestMemberType;
        },
        [PRODUCT_DETAIL_GUEST_MEMBER_YN](state) {
            return state.guestMemberYn;
        },
        [PRODUCT_DETAIL_VIDEO_URL](state) {
            return state.videoUrl;
        },
        [PRODUCT_DETAIL_VIDEO_TYPE](state) {
            return state.videoType;
        },
        [PRODUCT_DETAIL_LIST](state) {
            return state.list;
        }
    },
    mutations: {
        [PRODUCT_DETAIL_PRODUCT_INFO](state, value) {
            state.productInfo = value;
        },
        [PRODUCT_DETATL_PRODUCT_CODE](state, value) {
            state.productCode = value;
        },
        [PRODUCT_DETAIL_CATEGORY_ID](state, value) {
            state.categoryId = value;
        },
        [PRODUCT_DETAIL_ANNIVERSARY_TITLE](state, value) {
            state.anniversaryTitle = value;
        },
        [PRODUCT_DETAIL_LM_SERVER_TYPE](state, value) {
            state.lmServerType = value;
        },
        [PRODUCT_DETAIL_MEMBER_YN](state, value) {
            state.memberYn = value;
        },
        [PRODUCT_DETAIL_LPOINT_SERVICE_YN](state, value) {
            state.lpointServiceYn = value;
        },
        [PRODUCT_DETAIL_GUEST_MEMBER_TYPE](state, value) {
            state.guestMemberType = value;
        },
        [PRODUCT_DETAIL_GUEST_MEMBER_YN](state, value) {
            state.guestMemberYn = value;
        },
        [PRODUCT_DETAIL_VIDEO_URL](state, value) {
            state.videoUrl = value;
        },
        [PRODUCT_DETAIL_VIDEO_TYPE](state, value) {
            state.videoType = value;
        },
        [PRODUCT_DETAIL_LIST](state, value) {
            state.list = value;
        }
    },
    actions: {
        [PRODUCT_DETAIL_PRODUCT_INFO]({commit}, value) {
            commit(PRODUCT_DETAIL_PRODUCT_INFO, value);
        },
        [PRODUCT_DETATL_PRODUCT_CODE]({commit}, value) {
            commit(PRODUCT_DETATL_PRODUCT_CODE, value);
        },
        [PRODUCT_DETAIL_CATEGORY_ID]({commit}, value) {
            commit(PRODUCT_DETAIL_CATEGORY_ID, value);
        },
        [PRODUCT_DETAIL_ANNIVERSARY_TITLE]({commit}, value) {
            commit(PRODUCT_DETAIL_ANNIVERSARY_TITLE, value);
        },
        [PRODUCT_DETAIL_LM_SERVER_TYPE]({commit}, value) {
            commit(PRODUCT_DETAIL_LM_SERVER_TYPE, value);
        },
        [PRODUCT_DETAIL_MEMBER_YN]({commit}, value) {
            commit(PRODUCT_DETAIL_MEMBER_YN, value);
        },
        [PRODUCT_DETAIL_LPOINT_SERVICE_YN]({commit}, value) {
            commit(PRODUCT_DETAIL_LPOINT_SERVICE_YN, value);
        },
        [PRODUCT_DETAIL_GUEST_MEMBER_TYPE]({commit}, value) {
            commit(PRODUCT_DETAIL_GUEST_MEMBER_TYPE, value);
        },
        [PRODUCT_DETAIL_GUEST_MEMBER_YN]({commit}, value) {
            commit(PRODUCT_DETAIL_GUEST_MEMBER_YN, value);
        },
        [PRODUCT_DETAIL_VIDEO_URL]({commit}, value) {
            commit(PRODUCT_DETAIL_VIDEO_URL, value);
        },
        [PRODUCT_DETAIL_VIDEO_TYPE]({commit}, value) {
            commit(PRODUCT_DETAIL_VIDEO_TYPE, value);
        },
        [PRODUCT_DETAIL_LIST]({commit}, value) {
            commit(PRODUCT_DETAIL_LIST, value);
        }
    },
    modules: {
        headerSubDetail,
        productDetailVideoPlayer,
        productDetailReview
    }
};
export default productDetail;