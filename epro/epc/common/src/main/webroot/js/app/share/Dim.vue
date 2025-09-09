<template>
    <transition name="dim">
        <div class="blur"
             v-if="showPopup"
             @click.stop.prevent.capture="close">
        </div>
    </transition>
</template>

<script>
    import {mapGetters} from 'vuex';
    import {
        ROOT_SHOW_POPUP
    } from "../stores/types-root";

    export default {
        name: "dim",
        computed : {
            ...mapGetters({
                showPopup : ROOT_SHOW_POPUP
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
    .blur {
        position:fixed;
        top:0;
        left:0;
        width:100%;
        height:100%;
        background:#000;
        opacity: 0.5;
        z-index:55;
    }
</style>