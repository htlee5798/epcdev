<template>
    <header id="header"
            class="l-header l-header-sub sprint"
        :class="headerClass">
        <h1 class="title">
            <a :href="lmAppUrlM + '/mobile/main.do?SITELOC=OB002'">
                <img :src="imageSrcForLogo" alt="롯데마트">
            </a>
            <a href="#stroeLocation"
               class="store-location"
               id="btnStoreLocation"
               data-ga-action="상단 퀵바"
               data-ga-category="#몰구분=Mart #기기=M #분류=레이어 #URL="
               data-ga-label="#영역=점포">여기는 <b>{{storeName}}</b> 입니다
            </a>
        </h1>
        <button type="button"
                class="action-category"
                @click.stop.prevent="open">
            <i class="icon-hamburger">카테고리</i>
        </button>
        <a :href="lmAppUrlM +'/mobile/search/Search.do?SITELOC=OB003'"
           class="icon-header-search"
           data-gtm="M048">검색
        </a>
    </header>
</template>
<script>
    import {mapGetters} from 'vuex';
    import {
        HEADER_SUB_TYPES,
        HEADER_SUB_TODAY_DATE
    } from "../../../stores/types-category-header-sub";
    import {
        ROOT_LM_CDN_V3_ROOT_URL,
        ROOT_MAIN_STORE_NAME,
        ROOT_LM_APP_URL_M,
        ROOT_TODAY_DATE,
        ROOT_SHOW_POPUP
    } from "../../../stores/types-root";

    export default {
        name : 'HeaderSub',
        computed : {
            ...mapGetters({
                storeName : ROOT_MAIN_STORE_NAME,
                lmAppUrlM : ROOT_LM_APP_URL_M,
                lmCdnV3RootUrl : ROOT_LM_CDN_V3_ROOT_URL,
                types : HEADER_SUB_TYPES,
                todayDate : ROOT_TODAY_DATE
            }),
            imageSrcForLogo () {
                return this.lmCdnV3RootUrl + this.types.logoImageSrc;
            },
            headerClass () {
                return this.types.headerSubClass;
            }
        },
        methods : {
            open () {
                this.$store.dispatch(ROOT_SHOW_POPUP, true);

                if(window.ga) {
                    ga('send', 'event', {
                        eventAction : "카테고리 클릭",
                        eventCategory : "롯데마트몰 - easy & slow | 1-1 | 햄버거메뉴",
                        eventLabel :"버튼 클릭"
                    });
                }
            }
        },
        created () {
            this.$store.dispatch(HEADER_SUB_TODAY_DATE, this.todayDate);
        }
    }
</script>
<style>
    #header{z-index:52}
</style>