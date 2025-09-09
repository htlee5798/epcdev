<template>
    <button type="button"
            id="btnGoodsTrans"
            :class="setListType"
            :data-gtm="gtmNumber"
            @click.stop.prevent="changeListType">상품보기변경</button>
</template>

<script>
    import {mapGetters} from 'vuex';
    import {
        LIST_TYPE_SET,
        LIST_TYPE_GET
    } from "../stores/types-list-type";

    export default {
        name: "ListType",
        computed : {
            setListType : function () {
                switch(this.$store.getters[LIST_TYPE_GET]) {
                    case 'L':
                        return 'type-list';
                        break;
                    case 'G' :
                        return 'type-gallery';
                        break;
                    default :
                        return 'type-image';
                        break;
                }
            },
            ...mapGetters({
                listType : LIST_TYPE_GET
            })
        },
        methods : {
            changeListType : function () {
                if(this.listType === 'L') {
                    this.$store.dispatch(LIST_TYPE_SET, 'G');
                } else if(this.listType === 'G') {
                    this.$store.dispatch(LIST_TYPE_SET, 'I');
                } else {
                    this.$store.dispatch(LIST_TYPE_SET, 'L');
                }
            }
        }
    }
</script>

<style scoped>

</style>