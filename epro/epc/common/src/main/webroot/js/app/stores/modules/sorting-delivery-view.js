import {
    SORTING_DELIVERY_VIEW_DATA,
    SORTING_DELIVERY_VIEW_TYPE
} from "../types-sorting-delivery-view";

const sortingDeliveryView = {
    state: {
        type: '',
        data: [{
            name: '명절매장배송',
            value: 'HOLIDAY_STORE',
            isHolidaysCategory: true
        }, {
            name: '명절택배배송',
            value: 'HOLIDAY_STORE_PARCEL',
            isHolidaysCategory: true
        }, {
            name: '명절냉장배송',
            value: 'HOLIDAY_COLD_STORAGE',
            isHolidaysCategory: true
        }, {
            name: '명절냉동배송',
            value: 'HOLIDAY_REFRIGERATION',
            isHolidaysCategory: true
        }, {
            name: '매장배송',
            value: 'shop',
            isHolidaysCategory: false
        }, {
            name: '택배배송',
            value: 'parcel',
            isHolidaysCategory: false
        }]
    },
    getters: {
        [SORTING_DELIVERY_VIEW_DATA](state) {
            return state.data;
        },
        [SORTING_DELIVERY_VIEW_TYPE](state) {
            return state.type;
        }
    },
    mutations : {
        [SORTING_DELIVERY_VIEW_DATA](state, value) {
            state.data = value;
        },
        [SORTING_DELIVERY_VIEW_TYPE](state, value) {
            state.type = value;
        }
    },
    actions : {
        [SORTING_DELIVERY_VIEW_DATA]({commit}, value) {
            commit(SORTING_DELIVERY_VIEW_DATA, value);
        },
        [SORTING_DELIVERY_VIEW_TYPE]({commit}, value) {
            commit(SORTING_DELIVERY_VIEW_TYPE, value);
        }
    }
};

export default sortingDeliveryView;