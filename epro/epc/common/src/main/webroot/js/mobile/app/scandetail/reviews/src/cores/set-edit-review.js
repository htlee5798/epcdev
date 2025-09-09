import $ from 'jquery';
import getMobilePath from './get-mobile-path';

const setEditReview = (
    {
        id,
        productCd
    }
    ) => {
    return new Promise((resolve) => {
        $.api.set({
            url: getMobilePath({
                url: `/mobile/scan/products/${productCd}/reviews/${id}/check/member.do`
            }),
            dataType: 'json',
            successCallback: function (data) {
                resolve(data);
            }
        });
    });
};

export default setEditReview;