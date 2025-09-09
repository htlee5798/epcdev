<template>
    <header id="header">
        <h1 class="title"
            :class="{disable:play}">
            <a :href="domainPath + '/mobile/main.do'">
                <img :src="imagePath + '/images/layout/m-logo.png'" alt="롯데마트몰">
            </a>
        </h1>
        <a href="#" class="action-prev"
           @click.stop.prevent="onPrevPage"
           data-ga-action="페이지 콘텐츠 클릭"
           :class="{disable:play}"
           :data-ga-category="gaCategory"
           :data-ga-label="gaLabel('뒤로가기')">
            <i class="icon-common-prev">이전</i>
        </a>
        <a class="action-search"
           data-gtm="M046"
           :class="{disable:play}"
           :href="domainPath + '/mobile/search/Search.do'">
            <i class="icon-common-search">검색</i>
        </a>
        <a href="#" class="action-cart"
           @click.stop.prevent="onGoToBasket"
           data-ga-action="페이지 콘텐츠 클릭"
           :class="{disable:play}"
           :data-ga-category="gaCategory"
           :data-ga-label="gaLabel('장바구니')">
            <i class="icon-common-basket">장바구니</i>
            <em class="number" id="prodDetailBasketCnt">0</em>
        </a>
    </header>
</template>

<script>
    import {mapGetters} from 'vuex';
    import {
        ROOT_LM_APP_URL_M,
        ROOT_LM_CDN_V3_ROOT_URL
    } from "../../stores/types-root";
    import {
        PRODUCT_DETAIL_QRCOD,
        PRODUCT_DETATL_PRODUCT_CODE,
        PRODUCT_DETAIL_PRODUCT_INFO,
        PRODUCT_DETAIL_CATEGORY_ID,
        PRODUCT_DETAIL_ANNIVERSARY_TITLE,
        PRODUCT_DETAIL_MEMBER_YN,
        PRODUCT_DETAIL_GUEST_MEMBER_YN,
        PRODUCT_DETAIL_GUEST_MEMBER_TYPE,
        PRODUCT_DETAIL_VIDEO_PLAY
    } from "../../stores/types-product-detail";

    import {
        MESSAGES_GUESTMEMBER,
        MESSAGES_LOGOUT
    } from "../../assets/js/messages";

    import {
        AUTO_LOGIN,
        AUTO_LOGIN_ID,
        AUTO_LOGIN_MEMBER_ID,
        IS_AUTO_LOGIN,
        ATLGDATE
    } from "../../assets/js/const-cookie-name";

    const gaDataSetCategory = ({anniversaryTitle = '', productName = ''}) =>
        `#몰구분=Mart #기기=M #분류=GNB #페이지명=${anniversaryTitle}${productName} #URL=`;
    const gaDataSetLabel = ({categoryId = '', productCode = '', content=''}) =>
        `#CID=${categoryId} #PID=${productCode} #영역=상단 퀵바  #콘텐츠=${content}`;


    const removeLoginCookies = (autoCookie) => {
        if(autoCookie === 'Y') {
            let domain = window.location.hostname;
            $.removeCookie(AUTO_LOGIN_ID);
            $.removeCookie(AUTO_LOGIN);
            $.removeCookie(ATLGDATE);
            $.removeCookie(IS_AUTO_LOGIN, {domain : domain, path : '/'});
            $.removeCookie(AUTO_LOGIN_MEMBER_ID, {domain : domain, path : '/'});
        }
    };

    const ssoUrls = (
        {
            autoCookie,
            domainPath,
            lpointServiceYn,
            lmServerType
        }
    ) => {
        const protocal = lmServerType === 'dev' ? 'http' : 'https';
        const isMart = autoCookie === 'Y' && lpointServiceYn !=='Y' ? 'mart=Y' : '';
        const userAgent = navigator.userAgent;
        const outRetURL = userAgent.indexOf('TOYSRUS.IPHONE.SHOPPING') > 1
        || userAgent.indexOf('TOYSRUS.ANDROID.SHOPPING') > 1
            ? 'http://m.toysrus.lottemart.com/mobile/main.do'
            : domainPath + '/mobile/main.do';


        return `${protocal}://www.lottemart.com/imember/login/ssoLogoutPop.do?${isMart}&sid=MMARTSHOP&returnurl=${outRetURL}`;
    };

    export default {
        name: "HeaderSub",
        computed : {
            ...mapGetters({
                domainPath : ROOT_LM_APP_URL_M,
                imagePath : ROOT_LM_CDN_V3_ROOT_URL,
                play : PRODUCT_DETAIL_VIDEO_PLAY
            }),
            gaCategory () {
                return gaDataSetCategory({
                    anniversaryTitle : this.$store.getters[PRODUCT_DETAIL_ANNIVERSARY_TITLE],
                    productName : this.$store.getters[PRODUCT_DETAIL_PRODUCT_INFO].productName
                });
            }
        },
        methods : {
            onPrevPage () {
                if(this.$store.getters[PRODUCT_DETAIL_QRCOD] === 1) {
                    location.href = this.domainPath() + '//mobile/main.do';
                } else {
                    history.back();
                }
            },
            onGoToBasket () {
                const autoCookie = $.cookie(AUTO_LOGIN);
                if(this.$store.getters[PRODUCT_DETAIL_GUEST_MEMBER_YN] === 'true'
                    &&  this.$store.getters[PRODUCT_DETAIL_GUEST_MEMBER_TYPE] === "002") {
                    if(confirm(MESSAGES_GUESTMEMBER)) {
                        if(this.$store.getters[PRODUCT_DETAIL_MEMBER_YN] === 'true') {
                            if (!confirm(MESSAGES_LOGOUT)) {
                                return;
                            }
                        }
                        //goLogout
                        removeLoginCookies(autoCookie);
                        location.replace(ssoUrls());

                    } else {
                        return;
                    }
                }
            },
            gaLabel (content) {
                return gaDataSetLabel({
                    categoryId : this.$store.getters[PRODUCT_DETAIL_CATEGORY_ID],
                    productCode : this.$store.getters[PRODUCT_DETATL_PRODUCT_CODE],
                    content
                })
            }
        }
    }
</script>
<style scoped>
    #header .title.disable,
    #header .action-search.disable,
    #header .action-cart.disable,
    #header .action-prev.disable{
        dislplay:none
    }
</style>