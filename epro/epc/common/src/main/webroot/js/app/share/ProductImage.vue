<template>
    <div class="product-img">
        <img v-if="item.adlYn === 'Y' && adultPccvYn !== 'N'"
             :src="$store.state.lmCdnV3RootUrl + '/images/layout/m-goods-adult.png'"
             alt="성인인증이 필요한 상품입니다."/>
        <span v-else-if="isSoldOut"
              class="prod-sout">
                <img :src="$store.state.lmCdnV3RootUrl + '/images/layout/img_soldout_500x500.png'"
                     alt="품절"
                     class="thumb">
            </span>
        <img :src="getProductImagePath"
             style="width:500px"
             @error="onError"
             :alt="item.name"/>
    </div>
</template>
<script>
    import {mapGetters} from 'vuex';
    import getItemImagePath from 'assets/js/get-item-image-path';
    import {
        ROOT_LM_CDN_DYNAMIC_URL
    } from "../stores/types-root";
    import {
        PRODUCT_ITEM_ADULT_PCCV_YN
    } from "../stores/types-product-item";

    export default{
        name : 'ProductImage',
        props : [
            'item',
            'isSoldOut'
        ],
        computed : {
            ...mapGetters({
                adultPccvYn : PRODUCT_ITEM_ADULT_PCCV_YN
            }),
            getProductImagePath () {
                return getItemImagePath({
                    mdSrcmkCd : this.item.mdSrcmkCd,
                    sizeCode : '500',
                    seq : '1',
                    path : this.$store.getters[ROOT_LM_CDN_DYNAMIC_URL]
                });
            }
        },
        methods : {
            onError (e) {
                showNoImage(e.target, 500, 500);
            }
        }
    }
</script>