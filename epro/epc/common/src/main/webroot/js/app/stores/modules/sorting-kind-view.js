import {
    SORTING_KIND_VIEW_TYPE,
    SORTING_KIND_VIEW_DATA
} from "../types-sorting-kind-view";

const sortingKindView = {
    state : {
        type : 'hot',
        data : [{
            name: '인기상품순',
            value: 'hot'
        }, {
            name: '낮은가격순',
            value: 'low'
        }, {
            name: '높은가격순',
            value: 'high'
        }, {
            name: '신상품순',
            value: 'new'
        }, {
            name: '상품만족도',
            value: 'stfd'
        }]
    },
    getters : {
        [SORTING_KIND_VIEW_TYPE](state) {
            return state.type;
        },
        [SORTING_KIND_VIEW_DATA](state) {
            return state.data;
        }
    },
    mutations : {
        [SORTING_KIND_VIEW_TYPE](state, value) {
            state.type = value === '' ? 'hot' : value;
        },
        [SORTING_KIND_VIEW_DATA](state, value) {
            state.data = value;
        }
    },
    actions : {
        [SORTING_KIND_VIEW_TYPE]({commit}, value = 'hot') {
            commit(SORTING_KIND_VIEW_TYPE, value);
        },
        [SORTING_KIND_VIEW_DATA]({commit}, value) {
            commit(SORTING_KIND_VIEW_DATA, value);
        }
    }
};
export default sortingKindView;