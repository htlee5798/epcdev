<template>
    <a :href="joinPath"
       @click.stop.prevent="onJoin(joinPath)"
       class="anchor-gnb-login"
       data-ga-action="카테고리 헤더 클릭"
       data-ga-category="롯데마트몰 - easy & slow | 1-2 | 회원가입"
       data-ga-label="버튼 클릭">
        회원가입
    </a>
</template>
<script>
    import $ from 'jquery';
    import {mapGetters} from 'vuex';
    import {
        ROOT_LM_APP_SSL_URL_M,
        ROOT_SID
    } from "../stores/types-root";

    export default {
        name: "JoinButton",
        computed : {
            ...mapGetters({
                lmAppSSLUrlM: ROOT_LM_APP_SSL_URL_M,
                sid: ROOT_SID
            }),
            joinPath() {
                let {lmAppSSLUrlM, sid} = this;
                let returnUrl = location.href;
                return `${lmAppSSLUrlM}/imember/member/join/memberJoin.do?sid=${sid}&returnurl=${returnUrl}&memberJoinYN=Y`;
            }
        },
        methods : {
            onJoin(url) {
                if(!this.isApp) {
                    return location.href = url;
                }
                let currentAppVersion = $.utils.getAppVersion();
                let standardAppVersion = $.utils.isAndroid() ? '10.83' : '10.15';

                if ($.utils.checkAppVersion(currentAppVersion, standardAppVersion)) {
                    location.href = url;
                } else {
                    if (this.online) {
                        if (window.LOTTEMART && window.LOTTEMART.openPopup) {
                            window.LOTTEMART.openPopup(url);
                        } else {
                            let isCall = schemeLoader.loadScheme({
                                key: 'openPopup',
                                url: url
                            });
                            if (!isCall) {
                                window.open(url, 'family');
                            }
                        }
                    } else {
                        window.open(url, 'family');
                    }
                }

                //TUNE (회원가입 클릭)
                tuneLoader.loadScheme('registration_attempt');
            }
        }
    }
</script>
<style scoped></style>