import $ from 'jquery';
import getMobilePath from './get-mobile-path';

const setCheckPassword = (
    {
        id,
        productCd,
        password
    }
    ) => {

    return new Promise((resolve) => {
        $.api.set({
            url: getMobilePath({
                url: `/mobile/scan/products/${productCd}/reviews/${id}/check/password.do`
            }),
            data: {
                userPasswd: password
            },
            dataType: 'json',
            successCallback: function (data) {
                resolve(data);
            }
        })
    });
};

export default setCheckPassword;