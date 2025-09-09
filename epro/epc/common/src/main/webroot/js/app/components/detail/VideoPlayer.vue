<template>
    <div id="viewMovie"
         class="swiper-no-swiping"
         :class="{active:playing}"
         v-if="isShow">
        <video v-if="videoType ==='youtube'"
               :src="getVideoUrl"
               controls="controls"
               width="100%"
               height="100%"
               id="viewMovieItem">
        </video>
        <iframe v-else-if="videoType ==='etc'"
                width="100%"
                height="100%"
                frameborder="0"
                allowfullscreen
                :src="getVideoUrl" title="동영상"></iframe>
        <button type="button"
                class="icon-close"
                @click.stop.prevent="close"
        >닫기</button>
    </div>
</template>

<script>
    import {mapGetters} from 'vuex';
    import {
        PRODUCT_DETAIL_VIDEO_URL,
        PRODUCT_DETAIL_VIDEO_TYPE,
        PRODUCT_DETAIL_VIDEO_PLAY
    } from "../../stores/types-product-detail";

    export default {
        name: "VideoPlayer",
        computed : {
            ...mapGetters({
                videoUrl : PRODUCT_DETAIL_VIDEO_URL,
                videoType : PRODUCT_DETAIL_VIDEO_TYPE,
                playing : PRODUCT_DETAIL_VIDEO_PLAY
            }),
            isShow () {
                return this.videoUrl !== '';
            },
            getVideoUrl () {
                return this.videoType === 'youtube' ? 'http://api.wecandeo.com/video/default/' + this.videoUrl
                    : this.videoUrl + '?autoplay=1';
            }
        },
        methods : {
            close () {
                this.$store.dispatch(PRODUCT_DETAIL_VIDEO_PLAY, false);
            }
        }
    }
</script>

<style scoped>

</style>