<template>
    <div class="wrap-goodsdetails-slider"
         ref="sliderWrapper"
        :class="{active:play}">
        <ul class="swiper-wrapper">
            <li class="swiper-slide"
                v-for="(n, index) in imagesLength"
                :class="{'movie-player':isVideo(index)}">
                <a v-if="!isVideo(index)"
                   :href="link(n)"
                   @click.prevent="openZoomView($event, {})">
                    <img :data-src="imgSrc(n)"
                         class="swiper-lazy"
                         width="640">
                    <div class="swiper-lazy-preloader"></div>
                </a>
                <template v-else-if="isVideo(index)">
                    <img :data-src="imgSrc(n)"
                         class="swiper-lazy"
                         width="640">
                    <div class="swiper-lazy-preloader"></div>
                    <video-player></video-player>
                </template>
            </li>
        </ul>
        <div class="wrap-page"
             :class="{disable:play}">
            <div class="swiper-pagination"
                 v-if="imagesLength > 1">
            </div>
            <goods-zoom-button
                    v-if="productInfo"
                    :options="goodZoomButtonOptions()"></goods-zoom-button>
        </div>
    </div>
</template>

<script>
    import Swiper from 'swiper';
    import GoodsZoomButton from 'share/GoodsZoomButton.vue';
    import {mapGetters} from 'vuex';
    import {
        ROOT_LM_CDN_V3_ROOT_URL,
        ROOT_LM_APP_URL_M,
        ROOT_LM_CDN_DYNAMIC_URL
    } from "../../stores/types-root";
    import {
        PRODUCT_DETAIL_CATEGORY_ID,
        PRODUCT_DETAIL_PRODUCT_INFO,
        PRODUCT_DETAIL_VIDEO_URL,
        PRODUCT_DETAIL_VIDEO_PLAY
    } from "../../stores/types-product-detail";

    import {
        MESSAGES_3G_LTE
    } from "../../assets/js/messages";

    import openZoomView from '../../assets/js/open-zoom-view';
    import VideoPlayer from "./VideoPlayer.vue";

    const REQUEST_URL = location.href.replace( location.origin + '/mobile', '' );

    export default {
        name: "ProductDetailMainHeaderSlider",
        data () {
            return {
                slide : null,
                updateProductInfo : false
            }
        },
        computed : {
            ...mapGetters({
                lmAppUrlM : ROOT_LM_APP_URL_M,
                lmCdnV3RootUrl : ROOT_LM_CDN_V3_ROOT_URL,
                lmCdnDynamicUrl : ROOT_LM_CDN_DYNAMIC_URL,
                categoryId : PRODUCT_DETAIL_CATEGORY_ID,
                productInfo : PRODUCT_DETAIL_PRODUCT_INFO,
                videoUrl : PRODUCT_DETAIL_VIDEO_URL,
                play : PRODUCT_DETAIL_VIDEO_PLAY
            }),
            imagesLength () {
                return Number(!this.productInfo.imgQty ? 1 : this.productInfo.imgQty);
            }
        },
        created () {
            this.$store.subscribe((mutation, state) => {
                if(mutation.type === PRODUCT_DETAIL_PRODUCT_INFO) {
                    this.updateProductInfo = true;
                }
            });
        },
        updated () {
            if(this.slide === null && this.updateProductInfo) {
                let _this = this;
                this.slide = new Swiper(this.$refs.sliderWrapper, {
                    loop : true,
                    lazy: true,
                    preloadImages: false,
                    pagination: {
                        el: '.wrap-goodsdetails-slider .swiper-pagination'
                    },
                    on : {
                        tap (event) {
                            let target = event.target;

                            if (target.classList.contains('movie-player')) {
                                let connection = navigator.connection
                                    || navigator.mozConnection
                                    || navigator.webkitConnection;

                                let permission = false;

                                if(connection && connection.type !== 'wifi') {
                                    if(confirm(MESSAGES_3G_LTE)) {
                                        permission = true;
                                    }
                                } else {
                                    permission = true;
                                }
                                if(permission) {
                                    _this.$store.dispatch(PRODUCT_DETAIL_VIDEO_PLAY, true);
                                    let categoryId = _this.categoryId;
                                    let {productCode} = _this.productInfo;

                                    ga('send', 'event', {
                                        eventCategory: `#기기=M #분류=PDP #페이지명=상품상세정보 #URL=${REQUEST_URL}`,
                                        eventAction: '동영상 콘텐츠 | 동영상 보기',
                                        eventLabel : `#CID=${categoryId} #PID=${productCode}`
                                    });
                                }
                            }
                        }
                    }
                });

                for (let img of this.$refs.sliderWrapper.querySelectorAll('img')) {
                    img.onerror = ({target}) => {
                        let {lmCdnV3RootUrl} = this;
                        target.src = `${lmCdnV3RootUrl}/images/layout/noimg_prod_640x640.jpg`;
                    };
                }
            }
        },
        methods : {
            link (index) {
                if(!this.updateProductInfo) {
                    return;
                }
                let {mdSrcmkCd, imgQty} = this.productInfo;
                let lmAppUrlM = this.lmAppUrlM;
                let categoryId = this.categoryId;

                return `${lmAppUrlM}/mobile/popup/mobileProductOrgImg.do?ProductCD=${mdSrcmkCd}&CategoryID=${categoryId}&qty=${imgQty}&imgIdx=${index}`;
            },
            imgSrc (index) {
                if(!this.updateProductInfo) {
                    return;
                }
                let lmCdnDynamicUrl = this.lmCdnDynamicUrl;
                let {mdSrcmkCd} = this.productInfo;
                let substringMdSrcmkCd = mdSrcmkCd.substring(0, 5);

                return `${lmCdnDynamicUrl}/${substringMdSrcmkCd}/${mdSrcmkCd}_${index+1}_640.jpg`;
            },
            goodZoomButtonOptions () {
                return {
                    link: {
                        href: this.link(1),
                        cls: 'action-view-originalimage'
                    },
                    img: {
                        cls: 'icon-common-originalimage',
                        desc: '이미지 원본 보기'
                    }
                }
            },
            isVideo (index) {
                return this.videoUrl !== '' && index === 0;
            },
            openZoomView
        },
        components : {
            VideoPlayer,
            GoodsZoomButton
        }
    }
</script>
<style scoped>
    /* Preloader */
    .swiper-lazy-preloader {
        width: 42px;
        height: 42px;
        position: absolute;
        left: 50%;
        top: 50%;
        margin-left: -21px;
        margin-top: -21px;
        z-index: 10;
        -webkit-transform-origin: 50%;
        -ms-transform-origin: 50%;
        transform-origin: 50%;
        -webkit-animation: swiper-preloader-spin 1s steps(12, end) infinite;
        animation: swiper-preloader-spin 1s steps(12, end) infinite;
    }
    .swiper-lazy-preloader:after {
        display: block;
        content: '';
        width: 100%;
        height: 100%;
        background-image: url("data:image/svg+xml;charset=utf-8,%3Csvg%20viewBox%3D'0%200%20120%20120'%20xmlns%3D'http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg'%20xmlns%3Axlink%3D'http%3A%2F%2Fwww.w3.org%2F1999%2Fxlink'%3E%3Cdefs%3E%3Cline%20id%3D'l'%20x1%3D'60'%20x2%3D'60'%20y1%3D'7'%20y2%3D'27'%20stroke%3D'%236c6c6c'%20stroke-width%3D'11'%20stroke-linecap%3D'round'%2F%3E%3C%2Fdefs%3E%3Cg%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.27'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.27'%20transform%3D'rotate(30%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.27'%20transform%3D'rotate(60%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.27'%20transform%3D'rotate(90%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.27'%20transform%3D'rotate(120%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.27'%20transform%3D'rotate(150%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.37'%20transform%3D'rotate(180%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.46'%20transform%3D'rotate(210%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.56'%20transform%3D'rotate(240%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.66'%20transform%3D'rotate(270%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.75'%20transform%3D'rotate(300%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.85'%20transform%3D'rotate(330%2060%2C60)'%2F%3E%3C%2Fg%3E%3C%2Fsvg%3E");
        background-position: 50%;
        background-size: 100%;
        background-repeat: no-repeat;
    }
    .swiper-lazy-preloader-white:after {
        background-image: url("data:image/svg+xml;charset=utf-8,%3Csvg%20viewBox%3D'0%200%20120%20120'%20xmlns%3D'http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg'%20xmlns%3Axlink%3D'http%3A%2F%2Fwww.w3.org%2F1999%2Fxlink'%3E%3Cdefs%3E%3Cline%20id%3D'l'%20x1%3D'60'%20x2%3D'60'%20y1%3D'7'%20y2%3D'27'%20stroke%3D'%23fff'%20stroke-width%3D'11'%20stroke-linecap%3D'round'%2F%3E%3C%2Fdefs%3E%3Cg%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.27'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.27'%20transform%3D'rotate(30%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.27'%20transform%3D'rotate(60%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.27'%20transform%3D'rotate(90%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.27'%20transform%3D'rotate(120%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.27'%20transform%3D'rotate(150%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.37'%20transform%3D'rotate(180%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.46'%20transform%3D'rotate(210%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.56'%20transform%3D'rotate(240%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.66'%20transform%3D'rotate(270%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.75'%20transform%3D'rotate(300%2060%2C60)'%2F%3E%3Cuse%20xlink%3Ahref%3D'%23l'%20opacity%3D'.85'%20transform%3D'rotate(330%2060%2C60)'%2F%3E%3C%2Fg%3E%3C%2Fsvg%3E");
    }
    @-webkit-keyframes swiper-preloader-spin {
        100% {
            -webkit-transform: rotate(360deg);
            transform: rotate(360deg);
        }
    }
    @keyframes swiper-preloader-spin {
        100% {
            -webkit-transform: rotate(360deg);
            transform: rotate(360deg);
        }
    }
    .wrap-goodsdetails-slider.active{
        z-index:51
    }
    .wrap-goodsdetails-slider .wrap-page.disable{
        display:none
    }
</style>