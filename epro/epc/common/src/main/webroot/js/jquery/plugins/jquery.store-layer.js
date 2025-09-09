(function ($, window, document, undefined) {
	'use strict';

	$.templates( 'mobileStoreLocationLayer',
	'<article class="laypopup-storelocation {{:loginCheck}} {{:isNoMemLoginType}} {{:~className(className)}}">' +
		'<p class="mystore-message">' +
		'{{:deliveryNotice}}' +
		'</p>' +
		'<div class="button-group">' +
			'<a href="javascript:global.storeView(\'{{:stdistMallYn}}\')" class="check-delivery-time"' +
				'data-ga-action="상단 퀵바"' +
				'data-ga-category="#몰구분=Mart #기기=M #분류=레이어 #페이지명=핫콕 #URL="'+
				'data-ga-label="#영역=점포>배송시간확인">배송시간 확인</a>'+
			'<a href="javascript:global.isLogin(' + "'{{:~getLMAppUrlM()}}/mobile/popup/selectMyDeliveryList.do?returnURL="+encodeURIComponent(location.href)+"'" + ', function() { window.location.href='+ "'{{:~getLMAppUrlM()}}/mobile/popup/selectMyDeliveryList.do?returnURL="+encodeURIComponent(location.href)+"'"+'})" class="address-change"'+
				'data-ga-action="상단 퀵바"' +
				'data-ga-category="#몰구분=Mart #기기=M #분류=레이어 #페이지명=핫콕 #URL="'+
				'data-ga-label="#영역=점포>기본 배송지 변경">기본 배송지 변경</a>'+
		'</div>'+
		'<button type="button" class="icon-close">닫기</button>' +
	'</article>'
	);

	$.templates( 'mobileStoreLocationLayerEmpty',
	'<article class="laypopup-storelocation {{:loginCheck}} {{:isNoMemLoginType}} {{:~className(className)}}">' +
		'<p class="mystore-message">해당 점의 매장 배송이 마감됐습니다.<br>기본 배송지를 변경하시겠습니까?</p>' +
		'<div class="button-group">' +
			'<a href="javascript:global.isLogin(' + "'{{:~getLMAppUrlM()}}/mobile/popup/selectMyDeliveryList.do?returnURL="+encodeURIComponent(location.href)+"'" + ', function() { window.location.href='+ "'{{:~getLMAppUrlM()}}/mobile/popup/selectMyDeliveryList.do?returnURL="+encodeURIComponent(location.href)+"'"+'})" class="address-change">기본 배송지 변경</a>'+
		'</div>'+
		'<button type="button" class="icon-close">닫기</button>' +
	'</article>'
	);

	$.fn.storeLayer = function (options) {
		var defaults = {
				templateName: 'mobileStoreLocationLayer',
				myDefaultDelivery: '',
				mainStoreCode: '',
				mainStoreName: '',
				stdistMallYn: 'Y'
			},
			config = $.extend(true, defaults, options || {}),
			$body = $('body'),
			$layer = null,
			$this = this,
			$mask = $('<div class="mask"></div>'),
			$pageMoveBar = $('#pagemove-bar'),
			$smartPickUpBar = $('#smartpickup-bar');

		function setDateFormat(date, split) {
			date = date.trim();
			if (date.length < 8 || date == undefined ) return "";
			return date.substring(0, 4) + split + date.substring(4, 6) + split + date.substring(6, 8);
		}

		function getDeliveryTimeInfo() {
			$.getJSON("/mobile/product/ajax/mobileDeliveryTimeInfoAjax.do", {
				'deliSpFg_SD': 'Y'
			})
			.done(function(data) {
				var deliveryNotice = "";

				if( data != "" ) {
					if (data.status !== "success") {
						config.templateName = 'mobileStoreLocationLayerEmpty';
					} else {
						var deliveryTimeInfo = data.deliveryTimeInfo;
						var delivDate = setDateFormat(deliveryTimeInfo.DELIV_DATE, '-').trim();				// 배송일자
						var deliStartTm = deliveryTimeInfo.DELI_PRAR_START_TM.replace(":", "").trim();	// 배송시작시간
						var ordCloseTm = deliveryTimeInfo.ORD_CLOSE_TM.replace(":", "").trim();			// 주문마감시간

						var nowDt = data.nowDt.replace(":", "").trim();										// 현재시간
						var after2hDt = data.after2hDt.replace(":", "").trim();			// 현재시간 + 2 시간
						var after1dDt = setDateFormat(data.after1dDt, '-').trim();	// 다음날

						//김포 허브앤스포크 작업 - 시간 출력하는 부분 수정 - 분 부분을 0으로 나오게끔 수정
						//주석 처리 var deliStart = deliStartTm.substring(0,2)+":"+deliStartTm.substring(2);
						var deliStart = deliStartTm.substring(0,2)+":"+deliStartTm.substring(2,3)+"0";

						if( delivDate == nowDt.substring(0, 10).trim() ) {
							if( nowDt.substring(10).trim() < ordCloseTm && deliStartTm < after2hDt.substring(10).trim() ) {
								// nothing!
								deliveryNotice = 'default';
							} else {
								deliveryNotice = '<br>오늘 <i class="delivery-timer"></i>' + deliStart + '부터 배송 가능합니다.';
							}
						}  else if ( delivDate == after1dDt ) {
							deliveryNotice = '<br>내일 <i class="delivery-timer"></i>' + deliStart + '부터 배송 가능합니다.';
						} else {
							deliveryNotice = 'default';
						}
					}
				}

				open({deliveryNotice: deliveryNotice});
			});
		}

		function open(obj) {
			var storeName = '';
			if (config.myDefaultDelivery !== "" && config.myDefaultDelivery !== null) {
				if (!config.myDefaultDelivery[0].STDIST_MALL_STDIST_YN) {
					storeName = '택배배송점';
				} else {
					storeName = config.myDefaultDelivery[0].STR_NM;
				}
			} else {
				if (config.mainStoreCode === '802') {
					storeName = '택배배송점';
				} else {
					storeName = config.mainStoreName;
				}
			}

			if (obj.deliveryNotice === 'default') {
				obj.deliveryNotice = '나의 <b>기본배송지</b>를 기준으로<br><em>'+ storeName +'</em> 상품을 보고 계십니다.';
			} else {
				var temp = obj.deliveryNotice;
				obj.deliveryNotice = '지금 주문하면 <em>' + storeName + '</em>에서 ' + temp;
			}

			$('.laypopup-storelocation').remove();

			var templateHtml = $.render[config.templateName]({
				className: '',
				deliveryNotice: obj.deliveryNotice,
				stdistMallYn: config.stdistMallYn
			});

			$layer = $(templateHtml).appendTo('body');
			$body.addClass('layer-popup-active').append($mask);

			$pageMoveBar.addClass('hidden');
			$smartPickUpBar.addClass('hidden');
			$this.addClass('active');

			$layer.css({'display': 'block', 'top': '45px'});

			$layer
				.on('click', '[data-ga-action]', window.gaAction || gaAction)
				.on('click', '.icon-close', function () {
					remove();
					return false;
				})
				.on('click', 'a', function(){
					remove();
					window.location.href = $(this).attr('href');
					return false;
				});

			$mask.on('click touchmove', function () {
				remove();
				return false;
			});

			schemeLoader.loadScheme({key: 'hideBar'});
		}

		function remove() {
			if ($.cookie('firstStoreLayer')) {
				$.removeCookie('firstStoreLayer', {domain: '.lottemart.com', path: '/'});
			}

			$layer.remove();
			$mask.remove();
			$pageMoveBar.removeClass('hidden');
			$smartPickUpBar.removeClass('hidden');
			$body.removeClass('layer-popup-active');
			$this.removeClass('active');

			$layer = null;
			$('.laypopup-storelocation').remove();

			var _schemeKey = 'showBar';
			if ( $.utils.isiOSLotteMartApp() || $.utils.isAndroidLotteMartApp() ) {
				var checkedAppVersion = $.utils.isAndroid() ? '11.08' : '10.36';
				if ( !(parseFloat(checkedAppVersion) > parseFloat($.utils.getAppVersion())) ) _schemeKey = 'showBarStore';
			}
			schemeLoader.loadScheme({key: _schemeKey});
		}

		function gaAction() {
			var $this = $(this),
				data = $this.data(),
				requestUri = location.href.replace(location.origin + '/mobile', '');

			if (data.gaCategory && data.gaCategory.indexOf('#URL=') !== -1) {
				data.gaCategory = data.gaCategory + requestUri;
			}

			ga('send', 'event', {
				eventCategory: data.gaCategory,
				eventAction: data.gaAction,
				eventLabel: data.gaLabel
			});
		}

		return this.each(function (i, v) {
			$(v).on('click', function (e) {
				e.preventDefault();
				if (!$(this).hasClass('active')) {
					getDeliveryTimeInfo();
				} else {
					if ($layer) {
						remove();
					}
				}
				if (window.gaAction) {
					window.gaAction.apply(this);
				} else {
					gaAction.apply(this);
				}
				return false;
			});
		});
	};
})(jQuery, window, document);