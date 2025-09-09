<template>
    <div class="product-wrap">
        <product-image
                :item="item"
                :is-sold-out="isSoldOut"
                :lm-cdn-v3-root-url="lmCdnV3RootUrl"
                :adult-pccv-yn="adultPccvYn"></product-image>
        <div class="product-info">
            <a href="#"
               class="name"
            @click.prevent="onProductDetail($event, item)"
            data-gtm="M033"
            :data-category-id="item.category.id"
            :data-prod-cd="item.code">
                {{item.name}}
            </a>
            <div class="price-wrap">
                <div class="price">
                    <div class="number last">
                        <span>판매가</span>
                        <em>{{currencyFormat(item.currSellPrc)}}</em>
                        <i>원</i>
                        <i v-if="isDeal">~</i>
                    </div>
                </div>
                <div class="number last"
                     v-if="item.currSellPrc > item.promoMaxVal">
                    <span>혜택가</span>
                    <em class="sale">{{currencyFormat(item.promoMaxVal)}}</em>
                    <i>원</i>
                    <i v-if="isDeal">~</i>
                </div>
            </div>
            <icon-info
                    :item="item"
                    :is-deal="isDeal">
            </icon-info>
            <div class="review-wrap">
                <div class="review"
                     v-if="item.recommPoint > 6 && item.recommCount > 0">
                    <star-point :point="item.recommPoint"></star-point>
                    <span class="review-cases">
                        (<em>{{currencyFormat(item.recommCount)}}</em>)
                    </span>
                </div>
                <div class="cartInput">
                    <basket-button
                            :is-sold-out="isSoldOut"
                            :item="item"
                            :disabled="isSoldOut"
                            :class="{disabled:isSoldOut}">
                    </basket-button>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import {mapGetters} from 'vuex';
    import IconInfo from './IconInfo';
    import StarPoint from "./StarPoint";
    import BasketButton from "./BasketButton";
    import ProductImage from "./ProductImage";
    import {
        ROOT_ADULT_PCCV_YN,
        ROOT_LM_CDN_V3_ROOT_URL,
        ROOT_LM_CDN_DYNAMIC_URL,
        ROOT_ONLINE_PROD_TYPE_CD_DEAL
    } from "../stores/types-root";
    import {
        CATEGORY_BENEFIT_CHK_LIST
    } from "../stores/types-category";

    export default {
        name : 'ProductItem',
        props : [
            'item'
        ],
        components : {
            ProductImage,
            BasketButton,
            StarPoint,
            IconInfo
        },
        data () {
            return {
                isLastList : false,
                currentPage : 1
            }
        },
        computed : {
            ...mapGetters({
                adultPccvYn : ROOT_ADULT_PCCV_YN,
                benefitChkList : CATEGORY_BENEFIT_CHK_LIST,
                lmCdnV3RootUrl : ROOT_LM_CDN_V3_ROOT_URL,
                lmCdnDynamicUrl : ROOT_LM_CDN_DYNAMIC_URL,
                onlineProdTypeCdDeal : ROOT_ONLINE_PROD_TYPE_CD_DEAL
            }),
            isSoldOut () {
                return this.item.soutYn === 'Y'
                    && !(this.onlineProdTypeCdDeal === this.item.onlineProdTypeCd);
            },
            isDeal () {
                return this.item.onlineProdTypeCd === this.onlineProdTypeCdDeal;
            }
        },
        mounted () {
            $(this.$el)
                .on('transitionend -webkit-transitionend', '.mat-layer',function() {
                    $(this).remove();
                });
        },
        methods : {
            currencyFormat (price) {
                return $.utils.comma(price);
            },
            onProductDetail (event, obj) {
                let promise = new Promise((resolve, reject) => {
                    this.onAction(event, resolve);
                });

                promise.then(() => {
                    goProductDetailMobile(
                        obj.category.id,
                        obj.code,
                        'N',
                        '',
                        '',
                        obj.CLICK_URL || ''
                    );
                });
            },
            onAction (event, resolve) {
                let $obj = $(event.currentTarget).closest(this.$el);
                let posX = event.pageX - $obj.offset().left;
                let posY = event.pageY - $obj.offset().top;

                if ($obj.find('.mat-layer').length > 0) return;

                $obj.append('<i class="mat-layer" />');

                setTimeout(function() {
                    $obj.find('.mat-layer')
                        .css({'top': posY, 'left': posX})
                        .addClass('active');

                    resolve();
                }, 10);
            }
        }
    }

    function isEmpty(value) {
        return value === null || value === '' || value === undefined;
    }
</script>
<style scoped>
    .mat-layer {
        position:absolute;
        width:0;
        height:0;
        z-index:2;
        border-radius:50%;
        transition:all .2s linear;
        background-color:rgba(0,0,0,.3);
        transform:translate(-50%,-50%)
    }
    .mat-layer.active {
        width:50rem;
        height:50rem;
        background-color:rgba(0,0,0,0)
    }
    .disabled{
        opacity: 0.7;
    }
</style>