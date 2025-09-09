(function(root, factory) {
	'use strict';

	if (typeof define === 'function' && define.amd) {
		define(['jquery', 'lnbShow', 'getReturnUrl'], factory);
	} else if (typeof exports !== 'undefined') {
		module.exports = factory(require('jquery'), require('lnbShow'), require('getReturnUrl'));
	} else {
		root.lnbOpen = factory(jQuery, root.lnbShow, root.getReturnUrl);
	}
})(window, function($, lnbShow, getReturnUrl) {
	var _lnbOpen = function(button) {
		var $lnb = $('aside#globalCategory'),
			swiper = $('#allCategoryMenu').length > 0 ? $('#allCategoryMenu')[0].swiper : undefined;

		sessionStorage.setItem('openLnbTop', $(window).scrollTop());

		lnbShow();

		var isLoading = false;

		$lnb.on('webkitTransitionEnd transitionend', function() {
			var $this = $(this);
			if (isLoading) {
				return;
			}
			isLoading = true;

			if ($this.find('.wrap-inner-scroll').length <= 0) {
				var returnUrl = getReturnUrl();
				$.api.get({
					apiName: 'mobileGnbMenu',
					data: {
						ICPASS: 'Y',
						returnUrl: returnUrl
					},
					dataType: 'html',
					successCallback: function(response) {
						$this.html(response);

						swiper = $('#allCategoryMenu')[0].swiper;
					}
				});
			}

			$('html, body').addClass('masking');
		});
	};

	return _lnbOpen;
});
