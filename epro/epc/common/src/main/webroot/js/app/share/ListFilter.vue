<template>
    <div>
        <div class="lie-table goods-spot">
            <div class="left">
                <p>전체 <em class="number">{{totalCount}}</em>개</p>
            </div>
            <div class="right">
                <list-type></list-type>
            </div>
        </div>
        <div class="lie-table goods-sorting">
            <sorting-kind-view></sorting-kind-view>
            <sorting-delivery-view></sorting-delivery-view>
            <sorting-benefit></sorting-benefit>
        </div>
    </div>
</template>
<script>
    import {mapGetters} from 'vuex';
    import SortingKindView from 'share/SortingKindView';
    import SortingDeliveryView from 'share/SortingDeliveryView';
    import SortingBenefit from 'share/SortingBenefit';
    import {
        CATEGORY_LIST_TYPE
    } from "../stores/types-category";
    import ListType from "./ListType";

    export default {
        name : 'ListFilter',
        data : function () {
            return {
                gtmNumber : 'M036'
            }
        },
        components : {
            ListType,
            SortingKindView,
            SortingDeliveryView,
            SortingBenefit,
        },
        props : [
            'totalCount'
        ],
        computed : {
            ...mapGetters({
                categoryListType : CATEGORY_LIST_TYPE
            }),
            setListType : function () {
                switch(this.categoryListType) {
                    case 'L':
                        return 'type-gallery';
                        break;
                    case 'G' :
                        return 'type-image';
                        break;
                    default :
                        return 'type-list';
                        break;
                }
            }
        },
        methods : {
            changeListType : function () {
                if(this.categoryListType === 'L') {
                    this.$store.dispatch(CATEGORY_LIST_TYPE, 'G');
                } else if(this.categoryListType === 'G') {
                    this.$store.dispatch(CATEGORY_LIST_TYPE, 'I');
                } else {
                    this.$store.dispatch(CATEGORY_LIST_TYPE, 'L');
                }
            }
        }
    }
</script>