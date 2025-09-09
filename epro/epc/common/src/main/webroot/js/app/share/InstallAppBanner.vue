<template>
    <transition name="slide-fade">
        <div id="settingTopBanner"
             v-if="!isApp && !hasCookie">
            <a href="#"
               @click.stop.prevent="installApp"
               class="appbanner"
               data-gtm="M001">
                <img src="http://image.lottemart.com/v3/images/layout/m-appsettingbanner.png"
                     alt="더욱 편리한 롯데마트몰 APP으로 만나세요">
            </a>
            <button type="button"
                    class="icon-close js-close"
                    @click.stop.prevent="close">닫기
            </button>
        </div>
    </transition>
</template>
<script>
    import $ from 'jquery';
    import installApp from '../assets/js/install-lottemart-app';

    const COOKIE_NAME_VIEW_BAN = 'viewban';
    const COOKIE_VALUE = 'Y';

    export default {
        name : 'InstallAppBanner',
        data () {
            return {
                [COOKIE_NAME_VIEW_BAN]: ''
            }
        },
        computed : {
            hasCookie () {
                return this[COOKIE_NAME_VIEW_BAN];
            },
            isApp () {
                return $.utils.isiOSLotteMartApp()
                    || $.utils.isAndroidLotteMartApp();
            }
        },
        methods : {
            installApp,
            close () {
                let date = new Date();
                let promise = new Promise((resolve, reject) => {
                    $.cookie(COOKIE_NAME_VIEW_BAN, COOKIE_VALUE, {
                        expires: date.setDate(date.getDate() + 1)
                    });

                    resolve();
                });

                promise.then(() => {
                    this[COOKIE_NAME_VIEW_BAN] = true;
                });
            }
        },
        created () {
            this[COOKIE_NAME_VIEW_BAN] = $.cookie(COOKIE_NAME_VIEW_BAN) === COOKIE_VALUE;
        }
    };
</script>
<style scoped>
    .slide-fade-enter-active {
        transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
    }
    .slide-fade-leave-active {
        transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
    }
    .slide-fade-enter, .slide-fade-leave-to {
        transform: translateX(100%);
    }
</style>