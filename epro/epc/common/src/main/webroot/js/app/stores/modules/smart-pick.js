import {
    SMART_PICK_IS_SHOW
} from "../types-smart-pick";

const smartPick = {
    getters : {
        [SMART_PICK_IS_SHOW](state, getters, rootState) {
            return rootState.isMobile
                && location.href.indexOf('corners.do') !== -1;
        }
    }
};

export default smartPick;