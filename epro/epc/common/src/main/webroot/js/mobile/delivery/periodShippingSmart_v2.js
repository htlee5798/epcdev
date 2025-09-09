(function(root, factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD. Register as an anonymous module.
		define([], factory);
	} else if (typeof module === 'object' && module.exports) {
		// Node. Does not work with strict CommonJS, but
		// only CommonJS-like environments that support module.exports,
		// like Node.
		module.exports = factory();
	} else {
		// Browser globals (root is window)
		root.returnExports = factory();
	}
})(typeof self !== 'undefined' ? self : this, function() {
	var hash = '',
		$container = '',
		$picks = '',
		$tabs = '';

	var _init = function(obj) {
		hash = obj.hash;

		$container = $('[data-pageid="' + hash + '"]');
		$picks = $container.find('#preiodShipping_n');
		$tabs = $container.find('nav.tabmenu-type1');

		$tabs.on('click', 'a', getList);

		$container.find('select[name=priodPackageSelect]').on('change', function() {
			$(this)
				.find('option:selected')
				.attr('selected', 'selected');

			getPackageLoad();
		});

		if ($container.find('.smartList').children().length === 0) {
			startGetList();
		}
	};

	function getList() {
		getSmartMemberNm();
		var _this = $(this);
		_this
			.addClass('active')
			.siblings()
			.removeClass('active');
		$('.tabcontents', $container).removeClass('active');
		var tabValue = $(this).attr('value');
		$('#preiodShipping_' + tabValue, $container).addClass('active');

		//탭이동이 먼저 있는데도 active 상태로 보여지지 않아 setTimeout 설정
		setTimeout(function() {
			//2018.01.19 모바일 app의 경우 web용 로딩바가 보지 않도록 분기처리
			if (!$.utils.isiOSLotteMartApp() && !$.utils.isAndroidLotteMartApp()) {
				//모바일 앱이 아닐경우만 로딩바 표시
				$container.loadingBar();
			} else {
				if (window.LOTTEMARTDID && window.LOTTEMARTDID['isLoading']) {
					window.LOTTEMARTDID.isLoading(true);
				} else {
					schemeLoader.loadScheme({ key: 'lodingStart' });
				}
			}
			if (tabValue == 's') {
				getSmartRecommList();
			} else if (tabValue == 'p') {
				getPackageList();
			} else if (tabValue == 'n') {
				//getNormalList(); // history : 골라담기 추천상품리스트 노출 속도이슈로 기능제거 됨.
				//2018.01.19 모바일 app의 경우 web용 로딩바가 보지 않도록 분기처리
				if (!$.utils.isiOSLotteMartApp() && !$.utils.isAndroidLotteMartApp()) {
					//모바일 앱이 아닐경우만 로딩바 표시
					$container.loadingBar(false);
				} else {
					if (window.LOTTEMARTDID && window.LOTTEMARTDID['isLoading']) {
						window.LOTTEMARTDID.isLoading(false);
					} else {
						schemeLoader.loadScheme({ key: 'lodingEnd' });
					}
				}
				$picks.find('img[data-src]').each(function() {
					var $img = $(this),
						src = $img.data('src');

					$img.attr('src', src);
				});
			}
		}, 0);
	}

	function getPackageLoad() {
		//2018.01.19 모바일 app의 경우 web용 로딩바가 보지 않도록 분기처리
		if (!$.utils.isiOSLotteMartApp() && !$.utils.isAndroidLotteMartApp()) {
			//모바일 앱이 아닐경우만 로딩바 표시
			$container.loadingBar();
		} else {
			if (window.LOTTEMARTDID && window.LOTTEMARTDID['isLoading']) {
				window.LOTTEMARTDID.isLoading(true);
			} else {
				schemeLoader.loadScheme({ key: 'lodingStart' });
			}
		}
		getPackageList();
	}

	function qtyValChk(cnt, val) {
		var itemObj = $('#prodQty_' + cnt);
		var maxQty = parseInt($(itemObj).attr('data-max-qty') || '999');
		var minQty = parseInt($(itemObj).attr('data-min-qty') || '1');
		if (val >= maxQty) {
			alert('상품의 최대구매수량은 ' + maxQty + '개 입니다.');
			$('#prodQty_' + cnt).val(maxQty);
			return;
		}

		if (val == '' || val < minQty) {
			alert('상품의 최소구매수량은 ' + minQty + '개 이상 최대 ' + maxQty + '개 까지만 구매가 가능합니다.');
			$('#prodQty_' + cnt).val(minQty);
			return;
		}
	}

	/**
	 * MemberNm
	 */
	var getSmartMemberNm = function() {
		var $realName = $container.find('.rName');
		var $deliinfos = $container.find('.ldeliinfo');

		$.api.get({
			apiName: 'getMemberValue',
			dataType: 'text',
			successCallback: function(data) {
				var rParam = JSON.parse(data);
				$realName.html(rParam.memNm);

				if (rParam.isMember) {
					if (rParam.deliveryInfo != null) {
						$deliinfos.html(
							'<p class="msg-desc">고객님의 정기배송 예정일은 <em class="number">' +
								rParam.deliveryInfo.HOPE_DELI_DY_NM +
								' </em> 입니다.</p>' +
								'<p class="link-info">' +
								'<a href="/mobile/mypage/managePeri.do" class="btn-common-color1">정기배송 정보 안내</a>' +
								'</p>'
						);
					} else {
						$deliinfos.html('<p class="msg-desc">자주 구매하시는 상품을 정기배송으로 편하게 배송 받으세요.</p>');
					}
				} else {
					$deliinfos.html('<p class="msg-desc">로그인하시면 고객님만의 정기배송 상품을 추천해 드립니다.</p>');
				}
			},
			errorCallback: function(xhr, status, error) {
				//alert('memNmerr : ' + error);
			}
		});
	};

	/**
	 * 카테고리에 할당 된 상품 목록을 조회
	 */
	var getSmartRecommList = function() {
		var $smartList = $container.find('.smartList');

		$.api.get({
			apiName: 'mobilePeriodShippingSmartRecommendList',
			data: {
				deviceBr: 'm'
			},
			successCallback: function(data) {
				var lists = $.render.mobilePeriodShippingSmartRecommendList(data);

				$smartList.html(lists);
				//2018.01.19 모바일 app의 경우 web용 로딩바가 보지 않도록 분기처리
				if (!$.utils.isiOSLotteMartApp() && !$.utils.isAndroidLotteMartApp()) {
					//모바일 앱이 아닐경우만 로딩바 표시
					$container.loadingBar(false);
				} else {
					if (window.LOTTEMARTDID && window.LOTTEMARTDID['isLoading']) {
						window.LOTTEMARTDID.isLoading(false);
					} else {
						schemeLoader.loadScheme({ key: 'lodingEnd' });
					}
				}
			}
		});
	};

	/**
	 * 패키지 목록 조회
	 */
	var getPackageList = function() {
		var $priodPackageList = $container.find('.priodPackageList');

		$.api.get({
			apiName: 'mobilePeriodShippingPackageList',
			data: {
				package_type: $container.find('select[name=priodPackageSelect]').val(),
				page_no: 1,
				page_size: 100
			},
			successCallback: function(data) {
				var lists = $.render.mobilePeriodShippingPackageList(data);

				$priodPackageList.html(lists);
				//2018.01.19 모바일 app의 경우 web용 로딩바가 보지 않도록 분기처리
				if (!$.utils.isiOSLotteMartApp() && !$.utils.isAndroidLotteMartApp()) {
					//모바일 앱이 아닐경우만 로딩바 표시
					$container.loadingBar(false);
				} else {
					if (window.LOTTEMARTDID && window.LOTTEMARTDID['isLoading']) {
						window.LOTTEMARTDID.isLoading(false);
					} else {
						schemeLoader.loadScheme({ key: 'lodingEnd' });
					}
				}
			}
		});
	};
	function startGetList() {
		getPackageList();
	}

	return {
		init: function(obj) {
			_init(obj);
		}
	};
});
