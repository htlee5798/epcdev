<template>
    <div>
        <nav v-if="isMobile"
             id="quickmenu-bar" class="enable">
            <div class="group-ir">
                <div class="image">
                    <img :src="lmCdnV3RootUrl+'/images/layout/m-quickmenu-bar-ver2.png'" alt="">
                </div>
                <div class="wrap-inner">
                    <div class="inner">
                        <a v-for="menu in menus"
                           :href="menu.link ? lmAppUrlM+menu.link : '#'"
                           :id="menu.id ? menu.id : ''"
                           :class="menu.styleClass ? menu.styleClass : ''"
                           @click.prevent="appBarMenu(menu.type)">
                            {{menu.name}}
                        </a>
                    </div>
                </div>
            </div>
        </nav>
        <user-history></user-history>
        <page-top-button></page-top-button>
        <smart-pick></smart-pick>
    </div>
</template>
<script>
    import {mapGetters} from 'vuex';
    import UserHistory from "./UserHistory.vue";
    import PageTopButton from './PageTopButton.vue';
    import SmartPick from './SmartPick.vue';
    import {
        ROOT_LM_APP_URL_M,
        ROOT_LM_CDN_V3_ROOT_URL
    } from "../stores/types-root";
    import {
        APP_BAR_MENU,
        APP_BAR_IS_MOBILE_WEB
    } from "../stores/types-app-bar";

    export default {
        name: 'AppBar',
        components: {
            UserHistory,
            PageTopButton,
            SmartPick
        },
        methods : {
            appBarMenu
        },
        computed : mapGetters({
            menus : APP_BAR_MENU,
            isMobile : APP_BAR_IS_MOBILE_WEB,
            lmAppUrlM : ROOT_LM_APP_URL_M,
            lmCdnV3RootUrl : ROOT_LM_CDN_V3_ROOT_URL
        })
    };
</script>