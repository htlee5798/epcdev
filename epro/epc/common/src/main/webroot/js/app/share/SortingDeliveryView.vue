<template>
    <div class="select">
        <select id="deliveryView"
                name="deliveryType"
                :value="type"
                @change="setParam($event)">
            <option value="">전체배송</option>
            <option v-for="item in data"
                    v-if="isHolidaysCategory === item.isHolidaysCategory"
                    :value="item.value">
                {{item.name}}
            </option>
        </select>
    </div>
</template>
<script>
    import {mapGetters} from 'vuex';
    import {
        ROOT_IS_HOLIDAYS_CATEGORY
    } from "../stores/types-root";
    import {
        CATEGORY_NAME
    } from "../stores/types-category";
    import {
        SORTING_DELIVERY_VIEW_DATA,
        SORTING_DELIVERY_VIEW_TYPE
    } from "../stores/types-sorting-delivery-view";

    const requestUri = location.href.replace(location.origin + '/mobile', '');

    export default {
        name :'SortingDeliveryView',
        computed : {
            ...mapGetters({
                categoryName : CATEGORY_NAME,
                isHolidaysCategory : ROOT_IS_HOLIDAYS_CATEGORY,
                type : SORTING_DELIVERY_VIEW_TYPE,
                data : SORTING_DELIVERY_VIEW_DATA
            })
        },
        methods : {
            setParam (e) {
                this.$router.push({
                    path : this.$route.path,
                    query : $.extend(true, {}, this.$route.query, {
                        page : 1,
                        deliveryView : e.target.value
                    })
                });

                ga('send', 'event', {
                    eventAction: '카테고리 영역 클릭',
                    eventCategory: '#몰구분=Mart #기기=M #분류=카테고리 #페이지명=' + this.categoryName + ' #URL=' + requestUri,
                    eventLabel : '#Depth=3 #콘텐츠=상품보기조건 #선택사항=배송유형 #선택항목=' + this.type
                });
            }
        }
    }
</script>