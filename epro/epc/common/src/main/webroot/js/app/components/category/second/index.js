import $ from 'jquery';
import Vue from 'vue';
import {mapGetters} from 'vuex';
import VueRouter from 'vue-router';
import InstallAppBanner from 'share/InstallAppBanner';
import HeaderSub from './HeaderSub';
import CategoryBody from './CategoryBody';
import FooterApp from 'share/FooterApp';
import AppBar from 'share/AppBar';
import SearchFilter from 'share/SearchFilter';
import CategoryMenu from 'share/CategoryMenu';
import store from 'stores/index';
import categoryList from 'stores/modules/category-list';
import {
    ROOT_ONLINE_PROD_TYPE_CD_DEAL,
    ROOT_TODAY_DATE,
    ROOT_IS_SAMSUNG,
    ROOT_HOLIDAYS_CATEGORY_TYPE,
    ROOT_IS_HOLIDAYS_CATEGORY,
    ROOT_MEMBER_NO,
    ROOT_ZIP_SEQ,
    ROOT_MAIN_STORE_CODE,
    ROOT_SID,
    ROOT_MEMBER_GRADE_CD,
    ROOT_MEMBER_GRADE_NAME,
    ROOT_MEMBER_NAME,
    ROOT_HISTORY_LIST,
    ROOT_SHOW_POPUP
} from "../../../stores/types-root";
import {
    CATEGORY_ID,
    CATEGORY_NAME,
    CATEGORY_PAGE,
    CATEGORY_SIZE,
    CATEGORY_LOADING,
    CATEGORY_LIST_UPDATE,
} from "../../../stores/types-category";
import {
    SORTING_KIND_VIEW_TYPE
} from "../../../stores/types-sorting-kind-view";
import {
    SORTING_DELIVERY_VIEW_TYPE
} from "../../../stores/types-sorting-delivery-view";
import {
    SORTING_BENEFIT_VIEW_TYPE
} from "../../../stores/types-sorting-benefit-view";
import {
    SEARCH_FILTER_IS_OPEN
} from "../../../stores/types-search-filter";

store.registerModule('categoryList', categoryList);

Vue.use(VueRouter);

Vue.component('install-app-banner', InstallAppBanner);
Vue.component('header-sub', HeaderSub);
Vue.component('footer-app', FooterApp);
Vue.component('app-bar', AppBar);
Vue.component('search-filter', SearchFilter);
Vue.component('category-menu', CategoryMenu);

const router = new VueRouter({
    mode: 'history',
    routes: [{
        path: '/mobile/cate/v2/productList.do',
        component: CategoryBody,

    }]
});

const App = new Vue({
    el: '#page-wrapper',
    router,
    store,
    beforeMount() {
        let settingStaticData = new Promise((resolve) => {
            this.$store.dispatch(ROOT_TODAY_DATE, this.$el.dataset.todayDate);
            this.$store.dispatch(ROOT_ONLINE_PROD_TYPE_CD_DEAL, this.$el.dataset.onlineProdTypeCdDeal);
            this.$store.dispatch(ROOT_ZIP_SEQ, this.$el.dataset.zipSeq);
            this.$store.dispatch(ROOT_IS_SAMSUNG, this.$el.dataset.isSamsung);
            this.$store.dispatch(ROOT_HOLIDAYS_CATEGORY_TYPE, this.$el.dataset.holidaysCategoryType);
            this.$store.dispatch(ROOT_IS_HOLIDAYS_CATEGORY, this.$el.dataset.isHolidaysCategory);
            this.$store.dispatch(ROOT_MEMBER_NO, this.$el.dataset.memberNo);
            this.$store.dispatch(CATEGORY_NAME, this.$el.dataset.categoryName);
            this.$store.dispatch(ROOT_SID, this.$el.dataset.sid);
            this.$store.dispatch(ROOT_MEMBER_GRADE_CD, this.$el.dataset.memberGradeCd);
            this.$store.dispatch(ROOT_MEMBER_GRADE_NAME, this.$el.dataset.memberGradeName);
            this.$store.dispatch(ROOT_MEMBER_NAME, this.$el.dataset.memberName);
            this.$store.dispatch(ROOT_HISTORY_LIST, this.$el.dataset.historyList);
            resolve();
        });
        settingStaticData.then(() => {
            this.fetchData();
        });
    },
    computed: {
        ...mapGetters({
            strCd: ROOT_MAIN_STORE_CODE,
            zipSeq: ROOT_ZIP_SEQ,
            page: CATEGORY_PAGE,
            size: CATEGORY_SIZE,
            sortingKindViewType: SORTING_KIND_VIEW_TYPE,
            sortingDeliveryViewType: SORTING_DELIVERY_VIEW_TYPE,
            sortingBenefitViewType: SORTING_BENEFIT_VIEW_TYPE,
            isOpenSearchFilter: SEARCH_FILTER_IS_OPEN,
            showPopup: ROOT_SHOW_POPUP
        })
    },
    watch: {
        '$route': 'fetchData'
    },
    methods: {
        fetchData() {
            let _this = this;
            this.$store.dispatch(CATEGORY_LOADING, true);
            this.$store.dispatch(CATEGORY_ID, this.$route.query.CategoryID);
            this.$store.dispatch(SORTING_KIND_VIEW_TYPE, this.$route.query.kindView);
            this.$store.dispatch(SORTING_DELIVERY_VIEW_TYPE, this.$route.query.deliveryView);
            this.$store.dispatch(SORTING_BENEFIT_VIEW_TYPE, this.$route.query.benefitChkList);
            this.$store.dispatch(CATEGORY_PAGE, this.$route.query.page);
            this.$store.dispatch(CATEGORY_SIZE, this.$route.query.size);

            $.api.get({
                url: '/categories/' + this.$route.query.CategoryID + '/products.do',
                isDim: false,
                data: {
                    strCd: this.strCd,
                    zipSeq: this.zipSeq,
                    orderBy: this.$route.query.kindView,
                    deliType: this.$route.query.deliveryView,
                    iconType: this.$route.query.benefitChkList,
                    'pagination.page': this.$route.query.page,
                    'pagination.size': this.$route.query.size
                },
                successCallback(res) {
                    _this.setCategoryList(res);
                }
            });
        },
        setCategoryList(data) {
            if (data.error) {
                alert(data.error.message);
                this.$store.dispatch(CATEGORY_LOADING, false);
                return;
            }
            this.$store.dispatch(CATEGORY_LIST_UPDATE, data)
                .then(() => {
                    this.$store.dispatch(CATEGORY_PAGE, this.page + 1);
                    this.$store.dispatch(CATEGORY_LOADING, false);
                });
        }
    }
});

window.App = App;