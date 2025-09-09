<template>
    <button type="button"
            class="basket"
            data-method="basket"
            :data-is-sold-out="isSoldOut"
            :data-option-yn="item.optionYn"
            :data-prod-cd="item.code"
            :data-prod-type-cd="item.onlineProdTypeCd"
            :data-min-quantity="minQuantity"
            :data-max-quantity="item.maxOrdPsbtQty"
            :data-category-id="item.category.id"
            :data-prod-title="item.name"
            :data-is-manufacturing-product="manufacturing"
            title="장바구니 담기"
            @click="onAction">
        <i class="icon-common-cartEmp"></i>
    </button>
</template>
<script>
    import * as TYPECODE from 'assets/js/const-online-product-type-code';

    export default {
        name : 'BasketButton',
        props : [
            'item',
            'isSoldOut'
        ],
        computed : {
            minQuantity () {
                return this.item.minOrdPsbtQty <= 0 ? 1 : this.item.minOrdPsbtQty;
            },
            manufacturing () {
                return (this.item.code === TYPECODE.ONLINE_PROD_TYPE_CD_MAKE)
                    || (this.item.code === TYPECODE.ONLINE_PROD_TYPE_CD_INST);
            }
        },
        methods : {
            onAction () {

            }
        }
    }
</script>
<style scoped>
    .loading {
        position: absolute;
        background-color: rgba(0, 0, 0, .3);
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        font: 0/0 a;
    }
    .loading:after {
        content:'';
        position:absolute;
        top:40%;
        left:50%;
        display:block;
        width:3rem;
        height:3rem;
        margin-left:-1.5rem;
        border:.4rem solid #fff;
        border-radius:50%;
        border-top-color:transparent;
        animation:rotateLoop 1s linear infinite;
    }
</style>