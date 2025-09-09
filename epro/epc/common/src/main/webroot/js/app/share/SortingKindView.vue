<template>
    <div class="select">
        <select id="kindView"
                name="kindView"
                v-if="categoryId !== 'C004'"
                :value="type"
                @change="setParam($event)">
            <option v-for="item in data"
                    :value="item.value">
                {{item.name}}
            </option>
        </select>
    </div>
</template>
<script>
    import {mapGetters} from 'vuex';
    import {
        CATEGORY_ID,
        CATEGORY_NAME
    } from "../stores/types-category";
    import {
        SORTING_KIND_VIEW_DATA,
        SORTING_KIND_VIEW_TYPE
    } from "../stores/types-sorting-kind-view";

    const requestUri = location.href.replace(location.origin + '/mobile', '');

    export default {
        name: 'SortingKindView',
        computed : {
            ...mapGetters({
                categoryId : CATEGORY_ID,
                categoryName : CATEGORY_NAME,
                type : SORTING_KIND_VIEW_TYPE,
                data : SORTING_KIND_VIEW_DATA
            })
        },
        methods : {
            setParam (e) {
                this.$router.push({
                    path : this.$route.path,
                    query : $.extend(true, {}, this.$route.query, {
                        page : 1,
                        kindView : e.target.value
                    })
                });

                ga('send', 'event', {
                    eventAction: '카테고리 영역 클릭',
                    eventCategory: '#몰구분=Mart #기기=M #분류=카테고리 #페이지명=' + this.categoryName + ' #URL=' + requestUri,
                    eventLabel : '#Depth=3 #콘텐츠=상품보기조건 #선택사항=정렬옵션 #선택항목=' + this.type
                });
            }
        }
    };
</script>