<template>
    <a :href="options.link.href"
       :class="options.link.cls"
       @click.prevent="openZoomView($event, {})"
       data-ga-action="상품 상세 탐색"
       data-ga-category="#몰구분=Mart #기기=M #분류=상품상세정보 #페이지명=상품상세 #URL="
       :data-ga-label="'#CID='+categoryId+'#PID='+productInfo.mdSrcmkCd+'#세부액션=상품확대보기'">
        <i :class="options.img.cls">
            {{options.img.desc}}
        </i>
    </a>
</template>

<script>
    import openZoomView from '../assets/js/open-zoom-view';

    import {mapGetters} from 'vuex';
    import {
        PRODUCT_DETAIL_CATEGORY_ID,
        PRODUCT_DETAIL_PRODUCT_INFO
    } from "../stores/types-product-detail";

    export default {
        name: "GoodsZoomButton",
        props : [
            'options'
        ],
        computed : {
            ...mapGetters({
                categoryId : PRODUCT_DETAIL_CATEGORY_ID,
                productInfo : PRODUCT_DETAIL_PRODUCT_INFO
            })
        },
        methods : {
            openZoomView
        }
    }
</script>

<style scoped>

</style>