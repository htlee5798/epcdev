import $ from 'jquery';

const getBasketCount = () => {
    if($.utils.config('Login_yn') === 'Y') {
        $.getJSON("/basket/api/count.do")
            .done(function (data) {
                if (data) {
                    if (data.count > 0) {
                        document.querySelector('#basketCountData').dataset.qty = data.count;
                    }
                }
            });
    }
};

export default getBasketCount;