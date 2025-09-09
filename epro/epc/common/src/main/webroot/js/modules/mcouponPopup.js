(function(root, factory) {
	'use strict';

	if (typeof define === 'function' && define.amd) {
		define(factory);
	} else if (typeof exports !== 'undefined') {
		module.exports = factory();
	} else {
		root.McouponAppDown = factory();
	}
})(window, function() {
	'use strict';

	$.templates('mcouponLayer',
		'<div class="layer-mask">' +
		'	<div class="layer-mcoupon">' +
		'		<button type="button" class="btn-mcoupon-benefit">앱에서 혜택 받기</button>' +
		'		<div class="close-area">' +
		'			<button type="button" class="btn-close-sevendays">7일 동안 보지 않기</button>' +
		'			<button type="button" class="btn-close">닫기</button>' +
		'		</div>' +
		'	</div>' +
		'</div>'
	);

	var $html = $('html');

	function parseHTML() {
		var $docFrag = document.createDocumentFragment();
		var $layer = document.createElement('div');
		$layer.classList.add('mcoupon-down-recommend');
		$layer.innerHTML = $.render['mcouponLayer']();
		$docFrag.appendChild($layer);
		return $docFrag;
	}

	function init() {
		var _temp = parseHTML();
		schemeLoader.loadScheme({key: 'hideBar'});
		$('body').append(_temp);
		$html.addClass('layer-masking-mall');
		addEvent();
	}

	function addEvent() {
		var $popupLayer = $('.mcoupon-down-recommend');
		var $btnMcouponApp = $popupLayer.find('.btn-mcoupon-benefit');
		var $sevenDayClose = $popupLayer.find('.btn-close-sevendays');
		var $dayClose = $popupLayer.find('.btn-close');

		function popupClose() {
			$popupLayer.remove();
			$html.removeClass('layer-masking-mall');
			schemeLoader.loadScheme({key: 'showBar'});
		}

		$btnMcouponApp.on('click', function(e) {
			e.preventDefault();
			if($.utils.isIOS()){
				window.location.href = 'http://itunes.apple.com/app/id987435592';
			} else if($.utils.isAndroid()){
				window.location.href = 'market://details?id=com.lottemart.lmscp';
			}
		});

		$sevenDayClose.on('click', function() {
			setCookie('ncookie', 'done', 7);
			popupClose();
		});

		$dayClose.on('click', function() {
			popupClose();
		});

		function setCookie(name, value, expiredays) {
			var todayDate = new Date();
			todayDate.setDate(todayDate.getDate() + expiredays);
			document.cookie = name + '=' + escape(value) + '; path=/; expires=' + todayDate.toGMTString() + ';';
		}
	}

	return {
		open: function() {
			var cookiedata = document.cookie;
			if (cookiedata.indexOf('ncookie=done') < 0) {
				init();
			}
		}
	};
});
