import {
    PRODUCT_DETAIL_VIDEO_PLAY
} from "../types-product-detail";

const productDetailVideoPlayer = {
    state : {
        play : false
    },
    getters : {
        [PRODUCT_DETAIL_VIDEO_PLAY] (state) {
            return state.play;
        }
    },
    mutations : {
        [PRODUCT_DETAIL_VIDEO_PLAY] (state, value) {
            state.play = value;
        }
    },
    actions : {
        [PRODUCT_DETAIL_VIDEO_PLAY] ({commit}, value) {
            commit(PRODUCT_DETAIL_VIDEO_PLAY,value);
        }
    }
};

export default productDetailVideoPlayer;