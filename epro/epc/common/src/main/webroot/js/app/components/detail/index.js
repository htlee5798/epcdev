import Vue from 'vue';
import InstallAppBanner from 'share/InstallAppBanner';
import HeaderSub from './HeaderSub.vue';
import ProductDetailMainHeader from './ProductDetailMainHeader';
import ShareLayer from 'share/ShareLayer';
import Dim from 'share/Dim';
import store from 'stores/index';

import blast from 'assets/js/blast';

import productDetail from 'stores/modules/product-detail';
import {
    PRODUCT_DETAIL_QRCOD,
    PRODUCT_DETATL_PRODUCT_CODE,
    PRODUCT_DETAIL_CATEGORY_ID,
    PRODUCT_DETAIL_ANNIVERSARY_TITLE,
    PRODUCT_DETAIL_VIDEO_URL,
    PRODUCT_DETAIL_VIDEO_TYPE,
    PRODUCT_DETAIL_REVIEWS_CATCDYN
} from "../../stores/types-product-detail";

Vue.component('install-app-banner', InstallAppBanner);
Vue.component('header-sub', HeaderSub);
Vue.component('product-detail-main-header', ProductDetailMainHeader);
Vue.component('share-layer', ShareLayer);
Vue.component('dim', Dim);

store.registerModule('productDetail', productDetail);

const App = new Vue({
    el: '#page-wrapper',
    store,
    beforeMount() {
        this.$store.dispatch(PRODUCT_DETAIL_QRCOD, this.$el.dataset.qrcode);
        this.$store.dispatch(PRODUCT_DETATL_PRODUCT_CODE, this.$el.dataset.productCode);
        this.$store.dispatch(PRODUCT_DETAIL_CATEGORY_ID, this.$el.dataset.categoryId);
        this.$store.dispatch(PRODUCT_DETAIL_ANNIVERSARY_TITLE, this.$el.dataset.anniversaryTitle);
        this.$store.dispatch(PRODUCT_DETAIL_VIDEO_URL, this.$el.dataset.videoUrl);
        this.$store.dispatch(PRODUCT_DETAIL_VIDEO_TYPE, this.$el.dataset.videoType);
        this.$store.dispatch(PRODUCT_DETAIL_REVIEWS_CATCDYN, this.$el.dataset.catCdYn);

        document.addEventListener('click', (event) => {
            blast(event);
        });
    }
});

window.App = App;
