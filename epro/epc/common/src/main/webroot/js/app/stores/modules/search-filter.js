import {
    SEARCH_FILTER_IS_OPEN,
    SEARCH_FILTER_CLOSE,
    SEARCH_FILTER_OPEN,
    SEARCH_FILTER_TOTAL_COUNT,
    SEARCH_FILTER_GTM_BUTTON_CLOSE,
    SEARCH_FILTER_GTM_BUTTON_RESET,
    SEARCH_FILTER_GTM_BUTTON_APPLY,
    SEARCH_FILTER_DATA,
    SEARCH_FILTER_ACCORDION_MENU,
    SEARCH_FILTER_RESET
} from "../types-search-filter";

const searchFilter = {
    state: {
        isOpen : false,
        totalCount : 0,
        gtmButtonClose : 'M133',
        gtmButtonReset : 'M132',
        gtmButtonApply : 'M137',
        data : [{
            title :'배송',
            isActive : false,
            items : [{
                gtmNumber:'M134',
                title : 'DELIVERY',
                name : '매장배송',
                type : 'deliveryView',
                value : '01'
            },{
                gtmNumber:'M134',
                title : 'DELIVERY',
                name : '택배배송',
                type : 'deliveryView',
                value : '02'
            },{
                gtmNumber:'M134',
                title : 'DELIVERY',
                name : '업체택배',
                type : 'deliveryView',
                value : '04'
            }]
        }, {
            title : '혜택',
            isActive : false,
            items : [{
                gtmNumber:'M134',
                title : 'BENEFIT',
                name : '증정',
                type : 'benefitChkList',
                value : '03,04'
            },{
                gtmNumber:'M134',
                title : 'BENEFIT',
                name : '1+1',
                type : 'benefitChkList',
                value : '02,05,07,09'
            },{
                gtmNumber:'M134',
                title : 'BENEFIT',
                name : 'L.POINT 혜택',
                type : 'benefitChkList',
                value : '01'
            },{
                gtmNumber:'M134',
                title : 'BENEFIT',
                name : '카드할인',
                type : 'benefitChkList',
                value : '10'
            },{
                gtmNumber:'M134',
                title : 'BENEFIT',
                name : '살수록 더 싸게',
                type : 'benefitChkList',
                value : '06'
            },{
                gtmNumber:'M134',
                title : 'BENEFIT',
                name : '즉시할인',
                type : 'benefitChkList',
                value : '08,11,12'
            }]
        }]
    },
    getters: {
        [SEARCH_FILTER_IS_OPEN](state) {
            return state.isOpen;
        },
        [SEARCH_FILTER_TOTAL_COUNT](state) {
            return state.totalCount;
        },
        [SEARCH_FILTER_GTM_BUTTON_CLOSE](state) {
            return state.gtmButtonClose;
        },
        [SEARCH_FILTER_GTM_BUTTON_RESET](state) {
            return state.gtmNumberForButtonReset;
        },
        [SEARCH_FILTER_GTM_BUTTON_APPLY](state) {
            return state.gtmNumberForButtonReset;
        },
        [SEARCH_FILTER_DATA](state) {
            return state.data;
        }
    },
    mutations: {
        [SEARCH_FILTER_OPEN](state) {
            state.isOpen = true;
        },
        [SEARCH_FILTER_CLOSE](state) {
            state.isOpen = false;
        },
        [SEARCH_FILTER_TOTAL_COUNT](state, value) {
            state.totalCount = value;
        },
        [SEARCH_FILTER_GTM_BUTTON_CLOSE](state, value) {
            state.gtmButtonClose = value;
        },
        [SEARCH_FILTER_GTM_BUTTON_RESET](state, value) {
            state.gtmNumberForButtonReset = value;
        },
        [SEARCH_FILTER_GTM_BUTTON_APPLY](state, value) {
            state.gtmNumberForButtonReset = value;
        },
        [SEARCH_FILTER_DATA](state, value) {
            state.data = value;
        },
        [SEARCH_FILTER_ACCORDION_MENU](state, value) {
            state.data.forEach((v, index) => {
                if(index === value) {
                    v.isActive = !v.isActive;
                } else {
                    v.isActive = false;
                }
            });
        }
    },
    actions: {
        [SEARCH_FILTER_OPEN]({commit}) {
            commit(SEARCH_FILTER_OPEN);
        },
        [SEARCH_FILTER_CLOSE]({commit}) {
            commit(SEARCH_FILTER_CLOSE);
        },
        [SEARCH_FILTER_TOTAL_COUNT]({commit}, value = 0) {
            commit(SEARCH_FILTER_TOTAL_COUNT, value);
        },
        [SEARCH_FILTER_GTM_BUTTON_CLOSE]({commit}, value) {
            commit(SEARCH_FILTER_GTM_BUTTON_CLOSE, value);
        },
        [SEARCH_FILTER_GTM_BUTTON_RESET]({commit}, value) {
            commit(SEARCH_FILTER_GTM_BUTTON_RESET, value);
        },
        [SEARCH_FILTER_GTM_BUTTON_APPLY]({commit}, value) {
            commit(SEARCH_FILTER_GTM_BUTTON_APPLY, value);
        },
        [SEARCH_FILTER_DATA]({commit}, value = []) {
            commit(SEARCH_FILTER_DATA, value);
        },
        [SEARCH_FILTER_ACCORDION_MENU]({commit}, value) {
            commit(SEARCH_FILTER_ACCORDION_MENU, value);
        }
    }
};

export default searchFilter;