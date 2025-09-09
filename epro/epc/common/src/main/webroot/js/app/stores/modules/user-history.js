import {
    USER_HISTORY_TOYSRUS_LINK
} from "../types-user-history";

const userHistory = {
    getters : {
        [USER_HISTORY_TOYSRUS_LINK] (state, getters , rootState) {
            return rootState.osType === 'ios'
                ? 'http://itunes.apple.com/app/id987435592'
                : 'market://details?id=com.lottemart.lmscp';
        }
    }
};

export default userHistory;