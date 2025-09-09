import $ from 'jquery';

const getDeliveryTimeInfo = (
        deliSpFg_SD,
        deliSpFg_SP,
        deliSpFg_CP
    ) => {

    let promise = new Promise((resolve, reject) => {
        $.getJSON("/mobile/product/ajax/mobileDeliveryTimeInfoAjax.do", {
            'deliSpFg_SD': deliSpFg_SD,
            'deliSpFg_SP': deliSpFg_SP,
            'deliSpFg_CP': deliSpFg_CP
        }).done(function (data) {
            resolve(data);
        }).fail(function (data) {
            reject(data);
        });
    });

    return promise;
};

export default getDeliveryTimeInfo;