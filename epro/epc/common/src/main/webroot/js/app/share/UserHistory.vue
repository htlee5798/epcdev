<template>
    <aside id="globalNotice">
        <header class="header">
            <h1 class="title">나의 관심 콕!</h1>
            <a class="noticebox"
               :href="this.$store.state.lmAppUrlM + '/mobile/viewPushMsg.do'"
               v-if="isApp">
                <i class="icon-common-alert"> 알림보관함</i>
            </a>
            <button type="button"
                    class="icon-close"
                    @click.prevent="rnbClose">닫기</button>
            <nav class="tabmenu-type1 js-tab">
                <a href="#GNTabItem1" data-gtm="M092">
                    최근 본 상품 <em class="number" id="histprodcnt"></em>
                </a>
                <a href="#GNTabItem2" data-gtm="M092">
                    최근 구매 상품 <em class="number" id="histgetprodcnt"></em>
                </a>
            </nav>
        </header>
        <section id="GNTablItem1"
                class="wrap-inner-scroll tabcontents">
            <ul id="gnbhist1"
                class="gn-notice-list"></ul>
        </section>
        <section id="GNTablItem2"
                class="wrap-inner-scroll tabcontents">
            <ul id="gnbhist2"
                class="gn-notice-list"></ul>
        </section>
        <div class="wrap-rightbanner">
            <a v-if="isSamsung !== 'Y'"
               href="#"
               id="toysrusBanner"
               data-gtm="M093"
               data-title="토이저러스">
                <img :src="this.$store.state.lmCdnV3RootUrl +'/images/layout/m-mainright-banner1.png'" alt="토이저러스 세계 최대 장난감 유아용품 전문점">
            </a>
            <a v-else
               href="#"
               @click.prevent>
                <img :src="this.$store.state.lmCdnV3RootUrl +'/images/layout/m-mainright-banner1.png'" alt="토이저러스 세계 최대 장난감 유아용품 전문점">
            </a>
            <a v-if="isSamsung !== 'Y'"
               :href="toysrusLink" data-gtm="M093" data-title="롯데마트M쿠폰">
                <img :src="this.$store.state.lmCdnV3RootUrl+'/images/layout/m-mainright-banner2.png'" alt="롯데마트 M쿠폰 손안에서 만나는 오프라인 쿠폰북">
            </a>
            <a v-else
               href="#"
               @click.prevent>
                <img :src="this.$store.state.lmCdnV3RootUrl+'/images/layout/m-mainright-banner2.png'" alt="롯데마트 M쿠폰 손안에서 만나는 오프라인 쿠폰북">
            </a>
        </div>
    </aside>
</template>
<script>
    import {mapGetters} from 'vuex';
    import {
        ROOT_IS_APP,
        ROOT_IS_SAMSUNG
    } from "../stores/types-root";
    import {
        USER_HISTORY_TOYSRUS_LINK
    } from "../stores/types-user-history";

    export default {
        name : 'UserHistory',
        computed : {
            ...mapGetters({
                isApp : ROOT_IS_APP,
                toysrusLink : USER_HISTORY_TOYSRUS_LINK,
                isSamsung : ROOT_IS_SAMSUNG
            })
        },
        methods : {
            rnbClose
        }
    }
</script>