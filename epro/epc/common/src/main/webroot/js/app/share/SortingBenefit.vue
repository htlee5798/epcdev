<template>
    <div class="benefit">
        <button type="button"
                class="benefit-btn"
                :class="{'active' : isActive}"
                @click.prevent="onActive($event)">혜택별</button>
        <div class="wrap-sorting">
            <button type="button"
                    class="btn-chk-all"
                    @click.prevent="onSelectedAll($event, true)">전체 선택</button>
            <div class="sorting-check" id="divBenefit">
                <label v-for="(item, index) in data">
                    <input type="checkbox"
                           :id="'chkBenefit' + item.value"
                           :value="item.value"
                           :checked="isChecked(item.value)"/>
                    <em>{{item.name}}</em>
                </label>
            </div>
            <div class="wrap-btn">
                <button type="button"
                        class="btn-chk-refresh"
                        data-gtm="M038"
                        @click.prevent="onSelectedAll($event, false)">
                    <i class="icon-common-refresh"></i> 초기화
                </button>
                <button type="button"
                        class="btn-common-color4"
                        data-gtm="M037"
                        @click.prevent="onSubmit">
                    혜택 필터 적용
                </button>
            </div>
        </div>
    </div>
</template>
<script>
    import {mapGetters} from 'vuex';
    import {
        SORTING_BENEFIT_VIEW_TYPE,
        SORTING_BENEFIT_VIEW_DATA
    } from "../stores/types-sorting-benefit-view";
    import {
        CATEGORY_BENEFIT_CHK_LIST,
        CATEGORY_BENEFIT_CHK_LIST_DATA
    } from "../stores/types-category";

    export default {
        name : 'SortingBenefit',
        data : function () {
            return {
                isActive : false
            }
        },
        computed : {
            ...mapGetters({
                type : SORTING_BENEFIT_VIEW_TYPE,
                data : SORTING_BENEFIT_VIEW_DATA
            })
        },
        methods : {
            isChecked (value) {
                let checkLists = this.type.split(',');

                return checkLists.includes(value) ? 'checked' : '';
            },
            onActive (e) {
                this.isActive = !this.isActive;
            },
            onSelectedAll (e, isChecked) {
                let $listWrap = document.querySelector('#divBenefit');
                let $checkBoxs = $listWrap.querySelectorAll('input[type="checkbox"]');

                for(let $checkBox of $checkBoxs) {
                    $checkBox.checked = isChecked;
                }
            },
            onSubmit () {
                let $listWrap = document.querySelector('#divBenefit');
                let $checkBoxs = $listWrap.querySelectorAll('input[type="checkbox"]');
                let returnValue = [];

                for(let $checkBox of $checkBoxs) {
                    if($checkBox.checked) {
                        returnValue.push($checkBox.value);
                    }
                }

                this.$router.push({
                    path : this.$route.path,
                    query : $.extend(true, {}, this.$route.query, {
                        page : 1,
                        benefitChkList : returnValue.join(',')
                    })
                });

                this.isActive = false;
            }
        }
    }
</script>