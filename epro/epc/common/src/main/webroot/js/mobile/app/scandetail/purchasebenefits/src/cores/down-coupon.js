import $ from 'jquery';

const downCoupon = (coponIds, dcCpbookCds, callback) => {
    let isLogin = window.global.isLogin(window.location.href);

    if(isLogin) {
        let promise = new Promise((resolve) => {
            $.post({
                url: '/api/coupon/multiIssued.do',
                data: {
                    repCouponIds: coponIds.join(','),
                    dcCpbookCds: dcCpbookCds.join(',')
                },
                dataType: 'json',
                success: function (data) {
                    alert(data.rtnMsg)
                    resolve(data);
                }
            });
        });

        return promise;
    }
};

export default downCoupon;