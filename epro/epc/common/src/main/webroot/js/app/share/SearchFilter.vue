<template>
    <div>
        <aside id="globalSearchLayer">
            <header class="header">
                <h1 class="title">상세검색</h1>
                <span class="search-desc">
                    전체 <em class="number">{{totalCount}}</em>개</span>
                <button type="button"
                        data-id="btnreset"
                        class="btn-common-color4"
                        :data-gtm="gtmButtonReset"
                        @click.stop.prevent="onReset">초기화</button>
                <button type="button"
                        data-id="btnclose"
                        class="icon-close"
                        :data-gtm="gtmButtonClose"
                        @click.stop.prevent="onClose">닫기</button>
            </header>
            <section class="wrap-inner-scroll">
                <dl class="shipping-faq-list">
                    <template v-if="data.length > 0"
                              v-for="(d, index) in data">
                        <dt :class="{active:d.isActive}"
                            @click="onAccordion(index)">{{d.title}}</dt>
                        <dd>
                            <ul class="ps-list">
                                <li v-if="d.items.length > 0"
                                    v-for="item in d.items">
                                    <label>
                                        <input type="checkbox"
                                               class="chk-form"
                                               :data-gtm="item.gtmNumber"
                                               :data-title="item.title"
                                               :data-value="item.name"
                                               :name="item.type"
                                               :value="item.value"
                                               :checked="isChecked(item.type, item.value)">
                                        <span>{{item.name}}</span>
                                    </label>
                                </li>
                            </ul>
                        </dd>
                    </template>
                </dl>
            </section>
            <div class="searchfilter-select-apply">
                <button type="button"
                        class="btn-common-color6 fullw"
                        data-id="btnsearch"
                        :data-gtm="gtmButtonApply"
                        @click.stop.prevent="onSearch">
                    선택적용
                </button>
            </div>
        </aside>
        <div class="global-mask"
             style="display:none"
             @click.stop.prevent="onClose"></div>
    </div>
</template>

<script>
    import {mapGetters} from 'vuex';
    import {
        SEARCH_FILTER_CLOSE,
        SEARCH_FILTER_OPEN,
        SEARCH_FILTER_GTM_BUTTON_RESET,
        SEARCH_FILTER_GTM_BUTTON_CLOSE,
        SEARCH_FILTER_TOTAL_COUNT,
        SEARCH_FILTER_IS_OPEN,
        SEARCH_FILTER_DATA,
        SEARCH_FILTER_GTM_BUTTON_APPLY,
        SEARCH_FILTER_ACCORDION_MENU
    } from "../stores/types-search-filter";

    export default {
        name: "SearchFilter",
        props: [
            'el'
        ],
        data() {
            return {
                activeClass: 'globalSearchLayer'
            };
        },
        computed: {
            ...mapGetters({
                totalCount: SEARCH_FILTER_TOTAL_COUNT,
                gtmButtonClose: SEARCH_FILTER_GTM_BUTTON_CLOSE,
                gtmButtonApply: SEARCH_FILTER_GTM_BUTTON_APPLY,
                gtmButtonReset: SEARCH_FILTER_GTM_BUTTON_RESET,
                data: SEARCH_FILTER_DATA,
                isOpenSearchFilter: SEARCH_FILTER_IS_OPEN
            })
        },
        created() {
            this.$store.subscribe((mutation) => {
                if(mutation.type === SEARCH_FILTER_OPEN) {
                    schemeLoader.loadScheme({key: 'hideBar'});
                }

                if(mutation.type === SEARCH_FILTER_CLOSE) {
                    schemeLoader.loadScheme({key: 'showBar'});
                }
            });
        },
        mounted() {
            let _this = this;

            $(this.$el).on('webkitTransitionEnd transitionend', () => {
                if ($(_this.el).hasClass(_this.activeClass)) {
                    $(_this.$el)
                        .find('.global-mask')
                        .fadeIn(200);
                    $('html, body').addClass('mask');
                } else {
                    $(_this.$el)
                        .find('.global-mask')
                        .fadeOut(200);
                    $('html, body').removeClass('mask');
                }
            });
        },
        methods: {
            onReset() {
                $(this.$el)
                    .find(':checkbox:checked')
                    .prop('checked', false);
            },
            onSearch() {
                let obj = {};
                let searchObject = {};
                let _this = this;

                $(this.$el)
                    .find(':checkbox')
                    .each(function () {
                        var $el = $(this);
                        var name = $el.attr('name');
                        var value = $el.is(':checked') ? $el.val() : '';

                        if (obj[name]) {
                            obj[name].push(value)
                        } else {
                            obj[name] = [value];
                        }
                    });

                $.each(obj, (i, v) => {
                    if (_this.$route.query.hasOwnProperty(i)) {
                        searchObject[i] = v.filter(function (v) {
                            return v !== '';
                        }).join(',');
                    }
                });

                this.$router.push({
                    path: this.$route.path,
                    query: $.extend(true, {}, this.$route.query, searchObject, {
                        page: 1
                    })
                });

                this.onClose();
            },
            onClose() {
                this.$store.dispatch(SEARCH_FILTER_CLOSE);
            },
            onAccordion(index) {
                this.$store.dispatch(SEARCH_FILTER_ACCORDION_MENU, index);
            },
            isChecked(type, value) {
                let returnValue = '';
                if (this.$route.query[type]
                    && this.$route.query[type].indexOf(value) !== -1) {
                    returnValue = 'checked';
                }

                return returnValue;
            }
        }
    };
</script>

<style scoped>

</style>