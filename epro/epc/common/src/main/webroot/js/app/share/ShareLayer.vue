<template>
    <transition name="share">
        <article class="layerpopup-share active"
                 v-if="isOpen">
            <header class="header">
                <h3 class="title">공유하기</h3>
                <button @click.prevent="close"
                        type="button"
                        class="icon-close
                        js-close">닫기</button>
            </header>
            <share-button></share-button>
        </article>
    </transition>
</template>

<script>
    import {mapGetters} from 'vuex';
    import {
        ROOT_SHOW_POPUP
    } from "../stores/types-root";
    import ShareButton from "./ShareButton";

    export default {
        components: {ShareButton},
        name: "ShareLayer",
        created () {
            $(window)
                .on('resize', () => {
                this.close();
            });
        },
        updated () {
            $(this.$el).css({
                top: $(window).height() / 2
                - (this.$el.offsetHeight / 2
                    - (document.querySelector('#settingTopBanner').length === 0
                        ? 0 : document.querySelector('#settingTopBanner').offsetHeight)
                )
            });
        },
        computed : {
            ...mapGetters({
                isOpen : ROOT_SHOW_POPUP
            })
        },
        methods : {
            close () {
                this.$store.dispatch(ROOT_SHOW_POPUP, false);
            }
        }
    }
</script>

<style scoped>
    .share-enter {
        transform: translateY(100%);
        -webkit-transform: translateY(100%);
    }
    .share-enter-to{
        transform: translateY(0%);
        -webkit-transform: translateY(0%);
    }
    .share-enter-active{
        transition : all .2s ease-in-out;
    }
    .share-leave {
        transform: translateY(0%);
        -webkit-transform: translateY(0%);
    }
    .share-leave-to{
        transform: translateY(100%);
        -webkit-transform: translateY(100%);
    }
    .share-leave-active{
        transition : all .2s ease-in-out;
    }
    .layerpopup-share{
        z-index:56
    }
</style>