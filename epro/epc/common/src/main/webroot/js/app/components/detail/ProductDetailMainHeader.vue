<template>
    <header class="details-goodsspot">
        <product-detail-main-header-slider></product-detail-main-header-slider>
        <product-detail-main-header-title></product-detail-main-header-title>
    </header>
</template>

<script>
    import ProductDetailMainHeaderSlider from "./ProductDetailMainHeaderSlider";
    import ProductDetailMainHeaderTitle from "./product-detail-main-header-title";

    export default {
        name: "ProductDetailMainHeader",
        components : {
            ProductDetailMainHeaderTitle,
            ProductDetailMainHeaderSlider
        }
    };
</script>

<style scoped>

</style>