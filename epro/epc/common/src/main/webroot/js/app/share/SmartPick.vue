<template>
    <div v-if="isShow">
        <div id="smartpickup-bar">
            <a href="#"
               @click.prevent="goSmartPick"
               class="icon-pickup-mainbutton">
                스마트픽업
            </a>
        </div>
        <div id="smartpickup-layer" style="display:none">
            <div class="pickup-layer">
                <p class="thumbtitle">바로사고, 바로찾는 쇼핑,</p>
                <p class="conts">
                    <em>롯데마트몰 스마트픽</em>을 이용해보세요!
                </p>
                <div class="link">
                    <a href="#"
                       @click.prevent="goSmartPick">
                        지금바로 픽업하시려면 터치 <i class="icon-more">바로가기</i>
                    </a>
                </div>
                <label class="cookie-bar">
                    <input type="checkbox" @click.prevnet="setCookie('smartPickup');">
                    앞으로 보지 않기
                </label>
            </div>
            <div class="wrap-mask"></div>
        </div>
    </div>
</template>
<script>
    import {mapGetters} from 'vuex';
    import {
        SMART_PICK_IS_SHOW
    } from "../stores/types-smart-pick";

    export default {
        name: 'SmartPick',
        computed : mapGetters({
            isShow: SMART_PICK_IS_SHOW
        }),
        methods : {
            goSmartPick,
            setCookie (type) {
                if(window.fnFloatingCookie) {
                    fnFloatingCookie(type);
                }
            }
        }
    };
</script>
