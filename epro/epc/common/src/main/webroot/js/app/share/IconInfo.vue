<template>
    <div class="badge">
        <div class="type01"
             v-if="isFreeDelivery()">
            {{PRODUCT_ICON_NM_10}}
        </div>
        <div class="type02"
             v-if="promotions.length > 0"
             v-for="icon in promotions" v-html="getHtml(icon)">
        </div>
        <div class="type02">카드청구할인</div>
        <div class="type01"
             v-if="isShowDelivery">
            {{deliveryIcons.name}}
        </div>
        <div class="promotion"
             v-if="isGiftMall">
            <i :class="deliveryIcons.css">
                {{deliveryIcons.name}}
            </i>
        </div>
    </div>
</template>
<script>
    import $ from 'jquery';
    import {
        ONLINE_PROD_TYPE_CD_DEAL,
        ONLINE_PROD_TYPE_CD_ETC,
        ONLINE_PROD_TYPE_CD_OVERSEA
    } from 'assets/js/const-online-product-type-code';
    import {
        PRODUCT_ICON_NM_10
    } from 'assets/js/const-product-icon-name';
    import {
        BDL_DELI_TYPE_CD_STORE,
        BDL_DELI_TYPE_CD_PARCEL,
        BDL_DELI_TYPE_CD_OVERSEA,
        BDL_DELI_TYPE_CD_SMS,
        BDL_DELI_TYPE_CD_VENDOR,
        DELI_TYPE_GIFT_RESERVER,
        DELI_TYPE_GIFT_NORMAL,
        DELI_TYPE_GIFT_SPEC,
        DELI_TYPE_GIFT_VENDOR,
        DELI_TYPE_GIFT_COLD,
        DELI_TYPE_GIFT_FREEZE,
        DELI_TYPE_INTEGRATED_GIFTMALL,
        DELI_TYPE_RESERVE,
        DELI_TYPE_MART_DELI,
        DELI_TYPE_NORMAL_PICKUP,
        DELI_TYPE_INST_PICKUP,
        DELI_TYPE_VENDOR_DELI
    } from 'assets/js/const-delivery-type';
    import {
        STORE,
        STORE_PARCEL,
        OVERSEA,
        SMS,
        VENDOR_PARCEL,
        HOLIDAY_STORE,
        HOLIDAY_STORE_PARCEL,
        HOLIDAY_VENDOR_PARCE,
        HOLIDAY_COLD_STORAGE,
        HOLIDAY_REFRIGERATION,
        STORE_PICK_UP
    } from 'assets/js/const-delivery-icon';

    const PROMOTION_DELIMITERS = '|';
    const ICON_DELIMITERS = ':';
    const BDL_DELIVERTY_TYPE_CDOES = {
        [BDL_DELI_TYPE_CD_STORE] : STORE,
        [BDL_DELI_TYPE_CD_PARCEL] : STORE_PARCEL,
        [BDL_DELI_TYPE_CD_OVERSEA] : OVERSEA,
        [BDL_DELI_TYPE_CD_SMS] : SMS,
        [BDL_DELI_TYPE_CD_VENDOR] : VENDOR_PARCEL
    };
    const HOLIDAY_DELIVERY_TYPE_CODES = {
        [DELI_TYPE_GIFT_RESERVER] : HOLIDAY_STORE,
        [DELI_TYPE_GIFT_NORMAL] : HOLIDAY_STORE_PARCEL,
        [DELI_TYPE_GIFT_SPEC] : HOLIDAY_STORE_PARCEL,
        [DELI_TYPE_GIFT_VENDOR] : HOLIDAY_VENDOR_PARCE,
        [DELI_TYPE_GIFT_COLD] : HOLIDAY_COLD_STORAGE,
        [DELI_TYPE_GIFT_FREEZE] : HOLIDAY_REFRIGERATION
    };
    const HTML_PROMOTIONS = {
        'won' (value) {
            let price = $.utils.comma(value);
            return `<em class="won">${price}</em> 할인`;
        },
        'discount' (value) {
            return `<em class="number">${value}</em> 할인`;
        },
        'bundle' (value) {
            return `<em class="plus">${value}</em>`;
        },
        'type6-1' (value) {
            return`<em class="number">${value}</em> 다둥이`;
        },
        'type6-2' (value) {
            let price = $.utils.comma(value);
            return `<em class="won">${price}</em> 다둥이`;
        },
        'type4-1' (value) {
            return `<em class="number">${value}</em> L.POINT`;
        },
        'type4-2' (value) {
            let price = $.utils.comma(value);
            return `<em class="won">${price}</em> L.POINT`;
        }
    };

    export default {
        props : [
            'item',
            'isDeal'
        ],
        created : function () {
        },
        computed : {
            isShowDelivery () {
                return !this.isDeal
                    && (this.item.deliType.indexOf(DELI_TYPE_RESERVE) === 0
                        || this.item.deliType.indexOf(DELI_TYPE_VENDOR_DELI) === 0
                        || this.item.deliType.indexOf(DELI_TYPE_MART_DELI) === 0
                        || (this.item.onlineTypeCd
                            && this.item.onlineTypeCd.indexOf(ONLINE_PROD_TYPE_CD_ETC)));
            },
            isGiftMall () {
                return !this.isDeal
                    && this.item.deliType.indexOf(DELI_TYPE_INTEGRATED_GIFTMALL) !== -1;
            },
            promotions ()  {
                let promotionProductIcon = this.item.promoProdIcon;
                let returnValue = [];

                if(!promotionProductIcon) {
                    return '';
                }

                let promotions = promotionProductIcon.split(PROMOTION_DELIMITERS);

                for(let i = 0, len = promotions.length; i < len; i++) {
                    if(promotions[i] !== '') {
                        let icons = promotions[i].split(ICON_DELIMITERS);

                        returnValue.push({
                            orderNo: icons[0],
                            iconType: icons[4].indexOf('-') !== -1 ? 'type' + icons[4] : icons[1],
                            iconNm: icons[2],
                            iconDesc: icons[3]
                        });
                    }
                }

                return returnValue;
            },
            deliveryIcons () {
                let {onlineProdTypeCd = '', deliType = ''} = this.item;

                if(ONLINE_PROD_TYPE_CD_DEAL === onlineProdTypeCd) {
                    return BDL_DELIVERTY_TYPE_CDOES[deliType];
                } else if(ONLINE_PROD_TYPE_CD_ETC === onlineProdTypeCd) {
                    return SMS;
                } else if(deliType.indexOf(DELI_TYPE_INTEGRATED_GIFTMALL) === 0) {
                    return HOLIDAY_DELIVERY_TYPE_CODES[deliType];
                } else if(ONLINE_PROD_TYPE_CD_OVERSEA === onlineProdTypeCd) {
                    return OVERSEA;
                } else if(deliType.indexOf(DELI_TYPE_RESERVE) === 0) {
                    return STORE;
                } else if(deliType === DELI_TYPE_MART_DELI) {
                    return STORE_PARCEL;
                } else if(deliType === DELI_TYPE_NORMAL_PICKUP
                    || deliType === DELI_TYPE_INST_PICKUP) {
                    return STORE_PICK_UP;
                } else if(deliType === DELI_TYPE_VENDOR_DELI) {
                    return VENDOR_PARCEL;
                }
            }
        },
        methods : {
            isFreeDelivery () {
                return this.item.freeDelivery === 'Y'
                    && (this.item.onlineProdTypeCd
                        && this.item.onlineProdTypeCd !== ONLINE_PROD_TYPE_CD_ETC);
            },
            getHtml (obj) {
                if(obj.orderNo === '04') {
                    return obj.iconDesc;
                } else if(HTML_PROMOTIONS.hasOwnProperty(obj.iconType)) {
                    return HTML_PROMOTIONS[obj.iconType](obj.iconDesc);
                } else if(obj.iconType !== 'type12') {
                    return obj.iconNm;
                }
            }
        }
    }
</script>