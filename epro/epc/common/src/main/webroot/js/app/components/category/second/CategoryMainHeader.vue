<template>
    <div class="category-subdepth-header">
        <h1>{{categoryName}}</h1>
        <a v-if="isHolydaysCategory && holidaysCategoryType === 'Brand'"
           href="/mobile/holidays/brand/main.do" class="link-prev" data-gtm="M007_2">
            <i class="icon-common-prev02">이전</i>
        </a>
        <a v-else-if="isHolydaysCategory && holidaysCategoryType !== 'Brand'"
           href="/mobile/holidays/main.do" class="link-prev" data-gtm="M007_2">
            <i class="icon-common-prev02">이전</i>
        </a>
        <a v-else :href="'/mobile/cate/firstCategoryProductList.do?CategoryID='+firstCategoryId" class="link-prev" data-gtm="M007_2">
            <i class="icon-common-prev02">이전</i>
        </a>
    </div>
</template>
<script>
    import {mapGetters} from 'vuex';
    import {
        ROOT_IS_HOLIDAYS_CATEGORY,
        ROOT_HOLIDAYS_CATEGORY_TYPE
    } from "../../../stores/types-root";
    import {
        CATEGORY_ID,
        CATEGORY_NAME
    } from "../../../stores/types-category";

    export default {
        name: 'CategoryMainHeader',
        computed: {
            ...mapGetters({
                categoryId : CATEGORY_ID,
                categoryName : CATEGORY_NAME,
                isHolidaysCategory : ROOT_IS_HOLIDAYS_CATEGORY,
                holidaysCategoryType : ROOT_HOLIDAYS_CATEGORY_TYPE
            }),
            firstCategoryId() {
                return this.categoryId.substring(0, 8);
            }
        }
    };
</script>