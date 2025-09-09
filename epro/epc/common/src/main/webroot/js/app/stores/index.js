import $ from 'jquery';
import Vue from 'vue';
import Vuex from 'vuex';

import installAppBanner from './modules/install-app-banner';
import appBar from './modules/app-bar';
import userHistory from './modules/user-history';
import smartPick from './modules/smart-pick';

import mutations from './mutations';
import actions from './actions';
import {
    ROOT_ADULT_PCCV_YN,
    ROOT_ONLINE_PROD_TYPE_CD_DEAL,
    ROOT_LOGIN_CHECK,
    ROOT_MEMBER_NO,
    ROOT_HOLIDAYS_CATEGORY_TYPE,
    ROOT_MAIN_STORE_CODE,
    ROOT_MAIN_STORE_NAME,
    ROOT_ZIP_SEQ,
    ROOT_TODAY_DATE,
    ROOT_IS_SAMSUNG,
    ROOT_IS_HOLIDAYS_CATEGORY,
    ROOT_IS_MOBILE,
    ROOT_IS_APP,
    ROOT_LM_APP_URL_M,
    ROOT_LM_APP_SSL_URL_M,
    ROOT_LM_MEMBER_APP_SSL_URL_M,
    ROOT_LM_CDN_V3_ROOT_URL,
    ROOT_LM_CDN_DYNAMIC_URL,
    ROOT_LM_CDN_STATIC_ROOT_URL,
    ROOT_SHOW_POPUP,
    ROOT_SHOW_FOOTER,
    ROOT_GUEST_MEMBER_NAME,
    ROOT_SID,
    ROOT_ONLINE_MALL_APP,
    ROOT_MEMBER_GRADE_CD,
    ROOT_MEMBER_GRADE_NAME,
    ROOT_MEMBER_NAME,
    ROOT_HISTORY_LIST,
    ROOT_MEMBER_YN,
    ROOT_LPOINT_SERVICE_YN,
    ROOT_LM_SERVER_TYPE
} from "./types-root";

const STATIC_CONFIG = $.utils.config('all');

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        todayDate: '',
        onlineProdTypeCdDeal: '',
        zipSeq: '',
        holidaysCategoryType: '',
        isSamsung: '',
        isHolidaysCategory: '',
        memberNo: '',
        memberGradeCd :'',
        memberGradeName :'',
        memberName : '',
        sid : '',
        showFooter : true,
        showPopup: false,
        historyList : [],
        lmCdnDynamicUrl: STATIC_CONFIG.LMCdnDynamicUrl,
        lmCdnStaticRootUrl : STATIC_CONFIG.LMCdnStaticRootUrl,
        loginCheck: STATIC_CONFIG.loginCheck,
        lmAppUrlM: STATIC_CONFIG.LMAppUrlM,
        lmAppSSLUrlM : STATIC_CONFIG.LMAppSSLUrlM,
        lmCdnStaticUrl: STATIC_CONFIG.LMCdnStaticUrl,
        lmCdnV3RootUrl: STATIC_CONFIG.LMCdnV3RootUrl,
        lmMembersAppSSLUrl : STATIC_CONFIG.LMMembersAppSSLUrl,
        adultPccvYn: STATIC_CONFIG.LMAdultPccvYn,
        mainStoreCode: STATIC_CONFIG.main_store_code,
        mainStoreName: STATIC_CONFIG.main_store_name,
        memberYn : STATIC_CONFIG.Member_yn,
        guestMemberName : STATIC_CONFIG.GuestMemberName,
        onlinemallApp : STATIC_CONFIG.onlinemallApp,
        lpointServiceYn : STATIC_CONFIG.LPOINT_SERVICE_YN,
        lmServiceType : STATIC_CONFIG.LMServerType,
        isMobile: $.utils.isMobile(),
        isApp: $.utils.isiOSLotteMartApp() || $.utils.isAndroidLotteMartApp(),
        osType: $.utils.isIOS() ? 'ios' : ($.utils.isAndroid() ? 'android' : '')
    },
    getters: {
        [ROOT_ADULT_PCCV_YN](state) {
            return state.adultPccvYn;
        },
        [ROOT_ONLINE_PROD_TYPE_CD_DEAL](state) {
            return state.onlineProdTypeCdDeal;
        },
        [ROOT_LOGIN_CHECK](state) {
            return state.loginCheck === 'true';
        },
        [ROOT_MEMBER_NO](state) {
            return state.memberNo === 'true';
        },
        [ROOT_HOLIDAYS_CATEGORY_TYPE](state) {
            return state.holidaysCategoryType;
        },
        [ROOT_MAIN_STORE_CODE](state) {
            return state.mainStoreCode;
        },
        [ROOT_MAIN_STORE_NAME](state) {
            return state.mainStoreName;
        },
        [ROOT_ZIP_SEQ](state) {
            return state.zipSeq;
        },
        [ROOT_TODAY_DATE](state) {
            return state.todayDate;
        },
        [ROOT_IS_SAMSUNG](state) {
            return state.isSamsung;
        },
        [ROOT_IS_HOLIDAYS_CATEGORY](state) {
            return state.isHolidaysCategory;
        },
        [ROOT_IS_MOBILE](state) {
            return state.isMobile;
        },
        [ROOT_IS_APP](state) {
            return state.isApp;
        },
        [ROOT_LM_APP_URL_M](state) {
            return state.lmAppUrlM;
        },
        [ROOT_LM_APP_SSL_URL_M](state) {
            return state.lmAppSSLUrlM;
        },
        [ROOT_LM_CDN_V3_ROOT_URL](state) {
            return state.lmCdnV3RootUrl;
        },
        [ROOT_LM_CDN_DYNAMIC_URL](state) {
            return state.lmCdnDynamicUrl;
        },
        [ROOT_LM_CDN_STATIC_ROOT_URL](state) {
            return state.lmCdnStaticRootUrl;
        },
        [ROOT_SHOW_POPUP](state) {
            return state.showPopup;
        },
        [ROOT_SHOW_FOOTER](state) {
            return state.showFooter;
        },
        [ROOT_GUEST_MEMBER_NAME](state) {
            return state.guestMemberName;
        },
        [ROOT_SID](state) {
            return state.sid;
        },
        [ROOT_LM_MEMBER_APP_SSL_URL_M](state) {
            return state.lmMembersAppSSLUrl;
        },
        [ROOT_ONLINE_MALL_APP](state) {
            return state.onlinemallApp;
        },
        [ROOT_MEMBER_GRADE_CD](state) {
            return state.memberGradeCd;
        },
        [ROOT_MEMBER_GRADE_NAME](state) {
            return state.memberGradeName;
        },
        [ROOT_HISTORY_LIST](state) {
            return state.historyList;
        },
        [ROOT_MEMBER_YN](state) {
            return state.memberYn === 'true';
        },
        [ROOT_LPOINT_SERVICE_YN](state) {
            return state.lpointServiceYn;
        },
        [ROOT_LM_SERVER_TYPE](state) {
            return state.lmServiceType;
        },
        [ROOT_MEMBER_NAME](state) {
            return state.memberName;
        }
    },
    modules: {
        installAppBanner,
        appBar,
        userHistory,
        smartPick,
    },
    mutations,
    actions
});

export default store;