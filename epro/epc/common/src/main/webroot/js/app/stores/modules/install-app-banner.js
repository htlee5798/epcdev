import {
    INSTALL_APP_BANNER_IS_SHOW,
    INSTALL_APP_BANNER_CHANGE_STATE
} from "../types-install-app-banner";

const installAppBanner = {
    state: {
        isViewBanner: true
    },
    getters : {
        [INSTALL_APP_BANNER_IS_SHOW] (state, getters, rootState) {
            return state.isViewBanner
                && rootState.isMobile
                && rootState.isSamsung !== 'Y';
        }
    },
    mutations: {
        [INSTALL_APP_BANNER_CHANGE_STATE] (state, value) {
            state.isViewBanner = value;
        }
    },
    actions: {
        [INSTALL_APP_BANNER_IS_SHOW]({state, commit, rootState}) {
            commit(INSTALL_APP_BANNER_IS_SHOW, rootState);
        },
        [INSTALL_APP_BANNER_CHANGE_STATE] ({commit}, value) {
            commit(INSTALL_APP_BANNER_CHANGE_STATE, value);
        }
    }
};

export default installAppBanner;