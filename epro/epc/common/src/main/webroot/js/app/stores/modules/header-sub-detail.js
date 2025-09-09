import {
    PRODUCT_DETAIL_QRCOD
} from "../types-product-detail";

const headerSubDetail = {
    state : {
        qrcode : '0'
    },
    getters : {
        [PRODUCT_DETAIL_QRCOD](state) {
            return state.qrcode;
        }
    },
    mutations : {
        [PRODUCT_DETAIL_QRCOD](state, value) {
            state.qrcode = value;
        }
    },
    actions : {
        [PRODUCT_DETAIL_QRCOD]({commit}, value) {
            commit(PRODUCT_DETAIL_QRCOD, value);
        }
    }
};

export default headerSubDetail;