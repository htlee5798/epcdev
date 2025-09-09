import {
    SORTING_BENEFIT_VIEW_DATA,
    SORTING_BENEFIT_VIEW_TYPE
} from "../types-sorting-benefit-view";

const sortingBenefitView = {
    state: {
        type: '',
        data: [{
            name: 'L.POINT혜택',
            value: '01'
        }, {
            name: '다둥이 혜택',
            value: '02'
        }, {
            name: '사은품 증정',
            value: '03'
        }, {
            name: '상품권 증정',
            value: '04'
        }, {
            name: '덤',
            value: '05'
        }, {
            name: '살수록 더 싸게',
            value: '06'
        }, {
            name: '같이 더 싸게',
            value: '07'
        }, {
            name: 'M쿠폰',
            value: '08'
        }, {
            name: 'M+N',
            value: '09'
        }, {
            name: '카드할인',
            value: '10'
        }, {
            name: '즉시할인',
            value: '11'
        }, {
            name: '즉시할인 쿠폰',
            value: '12'
        }]
    },
    getters: {
        [SORTING_BENEFIT_VIEW_DATA](state) {
            return state.data;
        },
        [SORTING_BENEFIT_VIEW_TYPE](state) {
            return state.type;
        }
    },
    mutations: {
        [SORTING_BENEFIT_VIEW_DATA](state, value) {
            state.data = value;
        },
        [SORTING_BENEFIT_VIEW_TYPE](state, value) {
            state.type = value;
        }
    },
    actions: {
        [SORTING_BENEFIT_VIEW_DATA]({commit}, value) {
            commit(SORTING_BENEFIT_VIEW_DATA, value);
        },
        [SORTING_BENEFIT_VIEW_TYPE]({commit}, value = '') {
            commit(SORTING_BENEFIT_VIEW_TYPE, value);
        }
    }
};

export default sortingBenefitView;