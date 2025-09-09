import headerSub from './header-sub';
import productItem from './product-item';
import sortingKindView from './sorting-kind-view';
import sortingDeliveryView from './sorting-delivery-view';
import sortingBenefitView from './sorting-benefit-view';
import listType from './list-type';
import searchFilter from './search-filter';
import {
    CATEGORY_SUB_DATA,
    CATEGORY_ID,
    CATEGORY_NAME,
    CATEGORY_TOTAL_COUNT,
    CATEGORY_END,
    CATEGORY_LIST_DATA,
    CATEGORY_LIST_UPDATE,
    CATEGORY_LOADING,
    CATEGORY_PAGE,
    CATEGORY_SIZE
} from "../types-category";
import {
    ROOT_SHOW_FOOTER
} from "../types-root";

const categoryList = {
    state: {
        categoryId: '',
        categoryName: '',
        categorySub: [],
        totalCount: 0,
        data: [],
        loading: false,
        end: false,
        page: 1,
        size: 30
    },
    getters: {
        [CATEGORY_SUB_DATA](state) {
            return state.categorySub;
        },
        [CATEGORY_NAME](state) {
            return state.categoryName;
        },
        [CATEGORY_ID](state) {
            return state.categoryId;
        },
        [CATEGORY_TOTAL_COUNT](state) {
            return state.totalCount;
        },
        [CATEGORY_END](state) {
            return state.end;
        },
        [CATEGORY_LIST_DATA](state) {
            return state.data;
        },
        [CATEGORY_LOADING](state) {
            return state.loading;
        },
        [CATEGORY_PAGE](state) {
            return state.page;
        },
        [CATEGORY_SIZE](state) {
            return state.size;
        }
    },
    mutations: {
        [CATEGORY_SUB_DATA](state, value) {
            state.categorySub = value;
        },
        [CATEGORY_NAME](state, value) {
            state.categoryName = value;
        },
        [CATEGORY_ID](state, value) {
            state.categoryId = value;
        },
        [CATEGORY_LIST_DATA](state, value) {
            if (value === null) {
                state.data = [];
            } else if (state.page === 1) {
                state.data = value;
            } else {
                state.data.push(...value);
            }
        },
        [CATEGORY_TOTAL_COUNT](state, value) {
            state.totalCount = value;
        },
        [CATEGORY_END](state, value) {
            state.end = value;
        },
        [CATEGORY_LOADING](state, value) {
            state.loading = value;
        },
        [CATEGORY_PAGE](state, value) {
            state.page = Number(value);
        },
        [CATEGORY_SIZE](state, value) {
            state.size = value;
        }
    },
    actions: {
        [CATEGORY_SUB_DATA]({commit}, value) {
            commit(CATEGORY_SUB_DATA, value);
        },
        [CATEGORY_NAME]({commit}, value) {
            commit(CATEGORY_NAME, value);
        },
        [CATEGORY_ID]({commit}, value) {
            commit(CATEGORY_ID, value);
        },
        [CATEGORY_LIST_DATA]({dispatch, commit}, {products = [], count = 0}) {
            commit(CATEGORY_LIST_DATA, products);
            commit(CATEGORY_TOTAL_COUNT, count);
        },
        [CATEGORY_LIST_UPDATE]({dispatch, getters, commit}, value) {
            dispatch(CATEGORY_LIST_DATA, value.data)
                .then(() => {
                    let value = false;
                    if (getters[CATEGORY_TOTAL_COUNT] > 0) {
                        if (getters[CATEGORY_PAGE] * getters[CATEGORY_SIZE] >= getters[CATEGORY_TOTAL_COUNT]) {
                            value = true;
                        } else {
                            value = false;
                        }
                    } else {
                        value = true;
                    }

                    dispatch(CATEGORY_END, value)
                        .then(() => {
                            dispatch(ROOT_SHOW_FOOTER, value);
                        });
                });
        },
        [CATEGORY_LOADING]({commit}, value) {
            commit(CATEGORY_LOADING, value);
        },
        [CATEGORY_PAGE]({commit}, value = 1) {
            commit(CATEGORY_PAGE, value);
        },
        [CATEGORY_SIZE]({commit}, value = 30) {
            commit(CATEGORY_SIZE, value);
        },
        [CATEGORY_END]({commit}, value) {
            commit(CATEGORY_END, value);
        }
    },
    modules: {
        headerSub,
        productItem,
        sortingKindView,
        sortingDeliveryView,
        sortingBenefitView,
        listType,
        searchFilter
    }
};

export default categoryList;