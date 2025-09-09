<template>
    <div>
        <category-product-list-filter></category-product-list-filter>
        <article class="wrap-main">
            <div id="tgtGoodsTrans"
                 name="moProd"
                 :class="listClass">
                <ul class="prditem-groups" id="productList">
                    <li v-if="data.length > 0"
                        v-for="(item, key) in data"
                        data-panel="product">
                        <product-item
                                :key="key"
                                :item="item">
                        </product-item>
                    </li>
                    <li v-if="end"
                        class="list-empty">
                        {{emptyMessage}}
                    </li>
                </ul>
            </div>
            <div v-show="loading"
                 class="more-bar">
                <span class="spinner">잠시만 기다려주세요.</span>
            </div>
        </article>
    </div>
</template>
<script>
    import $ from 'jquery';
    import {mapGetters} from 'vuex';
    import ListFilter from 'share/ListFilter';
    import ProductItem from "share/ProductItem";
    import InfiniteScroll from '../../../../modules/infiniteScroll';
    import {
        ROOT_MAIN_STORE_CODE,
        ROOT_ZIP_SEQ
    } from "../../../stores/types-root";
    import {
        CATEGORY_ID,
        CATEGORY_LOADING,
        CATEGORY_END,
        CATEGORY_LIST_DATA,
        CATEGORY_PAGE,
        CATEGORY_SIZE,
        CATEGORY_TOTAL_COUNT
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
        LIST_TYPE_GET,
        LIST_TYPE_SET
    } from "../../../stores/types-list-type";

    import CategoryProductListFilter from "./CategoryProductListFilter";

    const $window = $(window);

    let infiniteScroll = null;

    export default {
        name: 'CategoryProductList',
        components: {
            CategoryProductListFilter,
            ListFilter,
            ProductItem
        },
        computed : {
            ...mapGetters({
                categoryId : CATEGORY_ID,
                strCd : ROOT_MAIN_STORE_CODE,
                zipSeq : ROOT_ZIP_SEQ,
                kindView : SORTING_KIND_VIEW_TYPE,
                deliveryView : SORTING_DELIVERY_VIEW_TYPE,
                benefitChkList : SORTING_BENEFIT_VIEW_TYPE,
                categoryListType : LIST_TYPE_GET,
                loading : CATEGORY_LOADING,
                end : CATEGORY_END,
                data : CATEGORY_LIST_DATA,
                page : CATEGORY_PAGE,
                size : CATEGORY_SIZE,
                totalCount : CATEGORY_TOTAL_COUNT
            }),
            emptyMessage () {
                if (this.totalCount > 0 && this.end) {
                    return '마지막 상품입니다.';
                } else if (this.totalCount === 0 ) {
                    if(this.benefitChkList !== '') {
                        return '선택하신 조건에 맞는 상품이 없습니다.';
                    } else if (this.totalCount === 0) {
                        return '상품이 없습니다.';
                    }
                }
            },
            listClass () {
                return {
                    'type-list' : this.categoryListType === 'L',
                    'type-gallery' : this.categoryListType === 'G',
                    'type-image' : this.categoryListType === 'I'
                }
            }
        },
        methods: {
            isActive (t) {
                let scrollBottom = $window.scrollTop() + $window.height();
                let offsetTop = $(t).offset().top;

                if(scrollBottom >= offsetTop) {
                    $(t).addClass('active');
                }
            }
        },
        beforeMount () {
            let listType = sessionStorage.getItem('VT');
            if (listType) {
                this.$store.dispatch(LIST_TYPE_SET, listType);
            }
        },
        mounted () {
            let _this = this;
            infiniteScroll = new InfiniteScroll({
                container : $('#productList'),
                callback () {
                    if(_this.loading || _this.end) {
                        return;
                    }

                    _this.$router.push({
                        path: _this.$route.path,
                        query: $.extend(true, {}, _this.$route.query, {
                            page: _this.page,
                            size: _this.size
                        })
                    });
                }
            });

            $window.on('scroll', () => {
                if( infiniteScroll ) {
                    infiniteScroll.setPosition( $window.scrollTop() );
                }
                $('#productList')
                    .find('li')
                    .not('.active')
                    .each((i, v) => {
                        this.isActive(v);
                    });
            });
        },
        updated () {
            $('#productList')
                .find('li')
                .not('.active')
                .each((i, v) => {
                    this.isActive(v);
                });
        }
    };
</script>
<style scoped>
    .prditem-groups li{
        overflow:hidden;
        transition:all .2s ease-out;
        transform:scale(0.9) translateY(60px);
        transform-origin: 50% 0;
        opacity: 0.5;
    }
    .prditem-groups li.active {
        transform:scale(1) translateY(0);
        opacity: 1;
    }
</style>