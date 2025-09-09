<template>
    <div v-if="isMobile"
            id="pagemove-bar">
        <a href="#page-wrapper"
           id="pagemove-to-top">
            <i class="icon-common-footarw">맨위로</i>
        </a>
        <a href="#"
           @click.prevent="back"
           id="pagemove-prev">
            <i class="icon-common-footarw">이전페이지로</i>
        </a>
    </div>
</template>
<script>
    import {mapGetters} from 'vuex';
    import {
        ROOT_IS_MOBILE
    } from "../stores/types-root";

    export default {
        name : 'PageTopButton',
        methods : {
            back () {
                history.back();
            }
        },
        computed : {
            ...mapGetters({
                isMobile : ROOT_IS_MOBILE
            })
        }
    }
</script>