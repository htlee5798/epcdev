import {
    APP_BAR_MENU,
    APP_BAR_IS_MOBILE_WEB
} from "../types-app-bar";

const appBar = {
    state : {
        menus: [{
            name: '홈',
            link: '/mobile/main.do?SITELOC=OC001',
            type: 'home'
        }, {
            name: '카테고리',
            type : 'category'
        }, {
            name : '장바구니',
            styleClass : 'notice-cart',
            link : '/mobile/mypage/PMWMMAR0003.do?SITELOC=OC003',
            type : 'basket'
        }, {
            name : '최근 구매 상품',
            link : '#globalNotice',
            type : 'history'
        }, {
            id : 'myMartPop',
            name : '마이롯데',
            link : '#globalMyMart',
            type : 'mymall'
        }]
    },
    getters : {
        [APP_BAR_MENU] (state) {
            return state.menus;
        },
        [APP_BAR_IS_MOBILE_WEB] (state, getters, rootState) {
            return rootState.isMobile && !rootState.isApp;
        }
    }
};

export default appBar;