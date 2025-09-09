<template>
    <main id="document" role="main">
        <category-main-header></category-main-header>
        <category-filter></category-filter>
        <category-product-list></category-product-list>
    </main>
</template>
<script>
    import CategoryMainHeader from './CategoryMainHeader.vue';
    import CategoryFilter from './CategoryFilter.vue';
    import CategoryProductList from './CategoryProductList.vue';

    export default {
        name : 'CategoryBody',
        components : {
            CategoryMainHeader,
            CategoryFilter,
            CategoryProductList
        }
    }
</script>