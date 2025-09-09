import $ from 'jquery';

const getIsWrote = (productCd) =>
    new Promise((resolve) => {
        $.api.get({
            url: `/mobile/scan/products/${productCd}/reviews/check.do`,
            successCallback: function (data) {
                resolve(data);
            }
        });
    });

export default getIsWrote;