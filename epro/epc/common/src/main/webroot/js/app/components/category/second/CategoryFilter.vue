<template>
    <div class="category-subdepth category-subdepth3">
        <div class="category-subdepth-inner">
            <a v-for="subCate in categoryFilterData"
               href="#"
               @click="search($event)"
               data-gtm="M004_2"
               :data-categoryid="subCate.categoryId"
               :class="{active : subCate.categoryId === categoryId}">
                <span>{{subCate.categoryName}}</span>
            </a>
        </div>
    </div>
</template>
<script>
    import {mapGetters} from 'vuex';
    import {
        CATEGORY_ID,
        CATEGORY_SIZE,
        CATEGORY_SUB_DATA
    } from "../../../stores/types-category";

    export default {
        name : 'CategoryFilter',
        computed : {
            ...mapGetters({
                categoryId : CATEGORY_ID,
                categoryFilterData : CATEGORY_SUB_DATA,
                size : CATEGORY_SIZE
            })
        },
        methods : {
            search (e) {
                e.preventDefault();
                this.$router.push({
                    path : this.$route.path,
                    query : $.extend(true, {}, this.$route.query, {
                        CategoryID : e.currentTarget.dataset.categoryid,
                        page : 1,
                        size : this.size,
                        kindView : '',
                        deliveryView : '',
                        benefitChkList : ''
                    })
                });
            }
        }
    }
</script>