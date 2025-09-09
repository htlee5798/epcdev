<template>
    <span class="starpoint-layer" id="btnReviews">
        <em class="starpoint-cover" :style="styleObject"></em>
    </span>
</template>

<script>
    export default {
        name : 'StarPoint',
        props : [
            'point'
        ],
        computed : {
            styleObject () {
                return {
                    width : (this.point * 10) + '%'
                }
            }
        }
    }
</script>