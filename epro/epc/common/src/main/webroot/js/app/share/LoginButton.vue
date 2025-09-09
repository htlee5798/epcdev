<template>
    <a :href="loginPath"
       @click="onGA"
       :class="{'anchor-gnb-login':type === 'categoryHeader'}">
        <i v-if="type === 'categoryFooter'"
           class="icon-category-relate6">로그인</i>
        로그인
    </a>
</template>

<script>
    import {
        ROOT_LM_APP_SSL_URL_M,
        ROOT_SID
    } from "../stores/types-root";
    import {mapGetters} from 'vuex';
    export default {
        name: "LoginButton",
        data () {
            return {
                gaOptions : {
                    categoryHeader : {
                        eventCategory : '롯데마트몰 - easy & slow | 1-1 | 로그인',
                        eventAction : '카테고리 헤더 클릭',
                        eventLabel : '버튼 클릭'
                    },
                    categoryFooter : {
                        eventCategory : '몰구분=Mart #기기=M #분류=카테고리 #페이지명=롯데마트몰 - easy & slow life',
                        eventAction : '카테고리 영역 클릭',
                        eventLabel : '#Depth=1 #콘텐츠=하단배너 #카테고리명=로그인'
                    }
                }
            }
        },
        props : {
            type : {
                default : 'categoryHeader'
            }
        },
        computed : {
            ...mapGetters({
                lmAppSSLUrlM: ROOT_LM_APP_SSL_URL_M,
                sid: ROOT_SID
            }),
            loginPath() {
                let {lmAppSSLUrlM, sid} = this;
                let returnUrl = location.href;
                return `${lmAppSSLUrlM}/mobile/PMWMMEM0001.do?sid=${sid}&returnurl=${returnUrl}`;
            }
        },
        methods : {
            onGA () {
                if(window.ga) {
                    ga('send', 'event', this.gaOptions[this.type]);
                }
            }
        }
    }
</script>

<style scoped>

</style>