import {
    HEADER_SUB_TYPES,
    HEADER_SUB_TODAY_DATE
} from "../types-category-header-sub";

const headerSub = {
    state : {
        todayDate : '',
        types : {
            defaults : {
                headerSubClass : 'twenty-wrap',
                logoImageSrc:'/images/layout/m-logo-ver3.png'
            },
            thanksgiving : {
                headerSubClass : 'ch-2017-header-02',
                logoImageSrc:'/images/layout/m-logo-ch2.png'
            },
            thanksgivingForOpen : {
                headerSubClass : 'ch-2017-header-01',
                logoImageSrc:'/images/layout/m-logo-ch1.png'
            }
        }
    },
    getters : {
        [HEADER_SUB_TYPES] (state) {
            if (state.todayDate >= '2017091400'
                && state.todayDate <= '2017092608') {
                return state.types.thanksgivingForOpen;
            } else if (state.todayDate >= '2017092609'
                && state.todayDate <= '2017100312') {
                return state.types.thanksgiving;
            } else {
                return state.types.defaults;
            }
        }
    },
    mutations : {
        [HEADER_SUB_TODAY_DATE](state, value) {
            state.todayDate = value;
        }
    },
    actions : {
        [HEADER_SUB_TODAY_DATE]({commit}, value) {
            commit(HEADER_SUB_TODAY_DATE, value);
        }
    }
};

export default headerSub;