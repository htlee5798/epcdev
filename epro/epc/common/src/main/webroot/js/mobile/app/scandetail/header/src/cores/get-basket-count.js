import $ from 'jquery';

const getBasketCount = (callback) => {
	if ($.utils.config('Login_yn') === 'Y') {
		$.getJSON("/basket/api/count.do")
			.done(function (data) {
				if (data) {
					if (data.count > 0) {
						if (callback) {
							callback(data.count);
						} else {
							document.querySelector('#basketCountData').dataset.qty = data.count;
						}
					}
				}
			});
	}
};

export default getBasketCount;