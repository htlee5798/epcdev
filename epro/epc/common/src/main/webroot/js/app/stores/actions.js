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

const actions = {
    [ROOT_LM_CDN_DYNAMIC_URL]({commit}, value) {
        commit(ROOT_LM_CDN_DYNAMIC_URL, value);
    },
    [ROOT_ONLINE_PROD_TYPE_CD_DEAL]({commit}, value) {
        commit(ROOT_ONLINE_PROD_TYPE_CD_DEAL, value);
    },
    [ROOT_TODAY_DATE]({commit}, value) {
        commit(ROOT_TODAY_DATE, value);
    },
    [ROOT_IS_SAMSUNG]({commit}, value) {
        commit(ROOT_IS_SAMSUNG, value);
    },
    [ROOT_HOLIDAYS_CATEGORY_TYPE]({commit}, value) {
        commit(ROOT_HOLIDAYS_CATEGORY_TYPE, value);
    },
    [ROOT_IS_HOLIDAYS_CATEGORY]({commit}, value) {
        commit(ROOT_IS_HOLIDAYS_CATEGORY, value);
    },
    [ROOT_LOGIN_CHECK]({commit}, value) {
        commit(ROOT_LOGIN_CHECK, value);
    },
    [ROOT_MEMBER_NO]({commit}, value) {
        commit(ROOT_MEMBER_NO, value);
    },
    [ROOT_ZIP_SEQ]({commit}, value) {
        commit(ROOT_ZIP_SEQ, value);
    },
    [ROOT_SHOW_POPUP]({commit}, value) {
        commit(ROOT_SHOW_POPUP, value);
    },
    [ROOT_SHOW_FOOTER]({commit}, value) {
        commit(ROOT_SHOW_FOOTER, value);
    },
    [ROOT_SID]({commit}, value) {
        commit(ROOT_SID, value);
    },
    [ROOT_MEMBER_GRADE_CD]({commit}, value) {
        commit(ROOT_MEMBER_GRADE_CD, value);
    },
    [ROOT_MEMBER_GRADE_NAME]({commit}, value) {
        commit(ROOT_MEMBER_GRADE_NAME, value);
    },
    [ROOT_MEMBER_NAME]({commit}, value) {
        commit(ROOT_MEMBER_NAME, value);
    },
    [ROOT_HISTORY_LIST]({commit}, value = []) {
        commit(ROOT_HISTORY_LIST, value);
    }
};

export default actions;