<template>
    <div class="wrap-sharelist">
        <a v-for="service in services"
           :data-service="service.id"
           href="javascript:;"
            @click.stop.prevent="onShare($event, service.id)">
            <i :class="service.iconClass"></i>
            {{service.name}}
        </a>
    </div>
</template>
<script>
    import ShareHandler from 'assets/js/share-handler';
    import Clipboard from 'clipboard';
    import {
        MESSAGE_URL_COPY_FAIL,
        MESSAGE_URL_COPY_SUCCESS
    } from "../assets/js/messages";

    const shareHandler = new ShareHandler();
    let clipboard = null;

    export default {
        name: "ShareButton",
        data () {
            return {
                services : [{
                    id : 'kakaotalk',
                    name : '카카오톡',
                    iconClass : 'icon-share-kakao'
                },{
                    id : 'facebook',
                    name : '페이스북',
                    iconClass : 'icon-share-facebook'
                },{
                    id : 'twitter',
                    name : '트위터',
                    iconClass : 'icon-share-twiter'
                },{
                    id : 'sms',
                    name : 'SMS',
                    iconClass : 'icon-share-sms'
                },{
                    id : 'kakaostory',
                    name : '카카오스토리',
                    iconClass : 'icon-share-kakaostory'
                },{
                    id : 'band',
                    name : '밴드',
                    iconClass : 'icon-share-band'
                },{
                    id : 'line',
                    name : '라인',
                    iconClass : 'icon-share-line'
                },{
                    id : 'linkcopy',
                    name : 'URL 복사',
                    iconClass : 'icon-share-url'
                }]
            }
        },
        mounted () {
            if(Clipboard.isSupported()) {
                let title = $('meta[property="og:title"]').attr("content");

                clipboard = new Clipboard(this.$el.querySelector('[data-service="linkcopy"]'), {
                    text() {
                        return `[${title}] ${location.href}`;
                    }
                });

                clipboard.on('success', () => {
                    alert(MESSAGE_URL_COPY_SUCCESS);
                });
                clipboard.on('error', () => {
                    alert(MESSAGE_URL_COPY_FAIL);
                });
            } else {
                this.$el
                    .querySelector('[data-service="linkcopy"]')
                    .classList
                    .add('hide');
            }

            if($.utils.config('onlinemallApp')){
                let appVersion = $.utils.getAppVersion();
                let appLastVersion1 = $.utils.isAndroid() ? "10.80" : "10.14";

                let appIsLast = $.utils.checkAppVersion(appVersion, appLastVersion1);
                if (!appIsLast) {
                    this.$el
                        .querySelector("[data-service=kakaostory]")
                        .classList
                        .add('hide');
                    this.$el
                        .querySelector("[data-service=band]")
                        .classList
                        .add('hide');
                    this.$el
                        .querySelector("[data-service=line]")
                        .classList
                        .add('hide');
                }

                if (!schemeLoader.isSupport('sendSMS')) {
                    this.$el
                        .querySelector("[data-service=sms]")
                        .classList
                        .add('hide');
                }

                if ($.utils.isIOS()) {
                    let excludeTwitterVersion = $.utils.checkAppVersion(appVersion, '10.22');
                    if (!excludeTwitterVersion) {
                        this.$el
                            .querySelector("[data-service=twitter]")
                            .classList
                            .add('hide');
                    }
                }
            }
        },
        methods : {
            onShare (event, id) {
                if(id !== 'linkcopy') {
                    shareHandler.clickHandler($(event.currentTarget));
                }
            }
        }
    }
</script>

<style scoped>
    .hide{display:none}
</style>