import {
    PRODUCT_ITEM_ADULT_PCCV_YN
} from "../types-product-item";

const productItem = {
    getters : {
        [PRODUCT_ITEM_ADULT_PCCV_YN](state, getters, rootState) {
            return rootState.adultPccvYn;
        }
    }
};

export default productItem;