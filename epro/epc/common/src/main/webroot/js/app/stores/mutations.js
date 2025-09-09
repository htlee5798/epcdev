import {
    ROOT_LM_CDN_DYNAMIC_URL,
    ROOT_ONLINE_PROD_TYPE_CD_DEAL,
    ROOT_TODAY_DATE,
    ROOT_IS_SAMSUNG,
    ROOT_HOLIDAYS_CATEGORY_TYPE,
    ROOT_IS_HOLIDAYS_CATEGORY,
    ROOT_LOGIN_CHECK,
    ROOT_MEMBER_NO,
    ROOT_ZIP_SEQ,
    ROOT_SHOW_POPUP,
    ROOT_SHOW_FOOTER,
    ROOT_SID,
    ROOT_MEMBER_GRADE_CD,
    ROOT_MEMBER_GRADE_NAME,
    ROOT_MEMBER_NAME,
    ROOT_HISTORY_LIST
} from "./types-root";

const mutations = {
    [ROOT_LM_CDN_DYNAMIC_URL](state, value) {
        state.lmCdnDynamicUrl = value;
    },
    [ROOT_ONLINE_PROD_TYPE_CD_DEAL](state, value) {
        state.onlineProdTypeCdDeal = value;
    },
    [ROOT_TODAY_DATE](state, value) {
        state.todayDate = value;
    },
    [ROOT_IS_SAMSUNG] (state, value) {
        state.isSamsung = value;
    },
    [ROOT_HOLIDAYS_CATEGORY_TYPE] (state, value) {
        state.holidaysCategoryType = value;
    },
    [ROOT_IS_HOLIDAYS_CATEGORY] (state, value) {
        state.isHolidaysCategory = (value === 'true' || value === 'True');
    },
    [ROOT_LOGIN_CHECK] (state, value) {
        state.loginCheck = value;
    },
    [ROOT_MEMBER_NO] (state, value) {
        state.memberNo = value;
    },
    [ROOT_ZIP_SEQ] (state, value) {
        state.zipSeq = value;
    },
    [ROOT_SHOW_POPUP] (state, value) {
        state.showPopup = value;
    },
    [ROOT_SHOW_FOOTER] (state, value) {
        state.showFooter = value;
    },
    [ROOT_SID](state, value) {
        state.sid = value === '' ? 'MMARTSHOP' : value;
    },
    [ROOT_MEMBER_GRADE_CD](state, value) {
        state.memberGradeCd = value;
    },
    [ROOT_MEMBER_GRADE_NAME](state, value) {
        state.memberGradeName = value;
    },
    [ROOT_MEMBER_NAME](state, value) {
        state.memberName = value;
    },
    [ROOT_HISTORY_LIST](state, value) {
        state.historyList = value;
    }
};

export default mutations;