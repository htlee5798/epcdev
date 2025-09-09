<template>
    <div class="wrap-title">
        <h1 class="title">
            {{productInfo.productName}}
        </h1>
        <p v-for="item in list"
           v-if="isCountry(item)"
                class="country">
            {{item.colNm}} : {{item.colVal}}
        </p>
        <div class="wrap-btn">
            <button @click.prevent="open($event)"
                    class="icon-common-share"
                    type="button">공유하기</button>
        </div>
    </div>
</template>

<script>
    import {mapGetters} from 'vuex';
    import {
        ROOT_SHOW_POPUP
    } from "../../stores/types-root";
    import {
        PRODUCT_DETAIL_CATEGORY_ID,
        PRODUCT_DETATL_PRODUCT_CODE,
        PRODUCT_DETAIL_PRODUCT_INFO,
        PRODUCT_DETAIL_LIST
    } from "../../stores/types-product-detail";
    import ShareLayer from "../../share/ShareLayer";

    const REQUEST_URL = location.href.replace( location.origin + '/mobile', '' );

    export default {
        components: {
            ShareLayer
        },
        name: "ProductDetailMainHeaderTitle",
        data () {
            return {
                isUpdatedInfo : false,
                isUpdatedList : false
            }
        },
        computed : {
            ...mapGetters({
                categoryId : PRODUCT_DETAIL_CATEGORY_ID,
                productCode : PRODUCT_DETATL_PRODUCT_CODE,
                productInfo : PRODUCT_DETAIL_PRODUCT_INFO,
                list : PRODUCT_DETAIL_LIST
            })
        },
        created () {
            this.$store.subscribe((mutation, state) => {
                if (mutation.type === PRODUCT_DETAIL_PRODUCT_INFO) {
                    this.isUpdatedInfo = true;
                } else if (mutation.type === PRODUCT_DETAIL_LIST) {
                    this.isUpdatedList = true;
                }
            });
        },
        methods : {
            isCountry (item) {
                if(!this.isUpdateList) {
                    return false;
                }
                return (item.infoGrpCd === 'EC019'
                || item.infoGrpCd === 'EC020'
                || item.infoGrpCd === 'EC021')
                && item.infoColCd === '00003'
            },
            sendToGA () {
                let categoryId = this.categoryId;
                let productCode = this.productCode;

                ga('send', 'event', {
                   eventCategory : `#몰구분=Mart #기기=M #분류=상품상세정보 #페이지명=상품상세 #URL=${REQUEST_URL}`,
                   eventAction : '상품 상세 탐색',
                   eventLabel : `#CID=${categoryId} #PID=${productCode} #세부액션=공유하기`
                });
            },
            open ($event) {
                this.$store.dispatch(ROOT_SHOW_POPUP, true);
                this.sendToGA();
            }
        }
    }
</script>
<style scoped>
</style>