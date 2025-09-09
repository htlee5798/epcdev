(function ($, window, document) {
	'use strict';

	$.templates('templateMoveMallLayer',
		'<h2 class="title"><i class="catchphrase">{{:together.desc}}</i>{{:together.title}}</h2>' +
		'<div class="wrapper-scroll">' +
		'	<nav class="list-move-mall">' +
		'	{{for together.malls}}' +
		'		<a href="{{:url}}" class="item TG_MALLNAV_{{:#index+1}}" data-gate-url="{{:ssoGateUrl}}">' +
		'			<img src="{{:img}}" alt="{{:title}}">' +
		'			<span>{{:title}}</span><em>{{:desc}}</em>' +
		'		</a>' +
		'	{{/for}}' +
		'	</nav>' +
		'	<h3 class="sub-title TG_MALLNAV_OTHER_TOGGLE">{{:others.title}}</h3>' +
		'	<nav class="list-move-mall-sub">' +
		'	{{for others.malls}}' +
		'		<a href="{{:linkUrl}}" target="_blank" class="item TG_MALLNAV_OTHER_{{:#index+1}}"' +
		'			data-android-scheme="{{:androidScheme}}"' +
		'			data-android-store-url="{{:androidStoreUrl}}"' +
		'			data-ios-exe="{{:iosAppExecUrl}}"' +
		'			data-ios-store-url="{{:iosStoreUrl}}"' +
		'			data-launch-app="{{:launchAppYn}}"' +
		'			data-link-url="{{:linkUrl}}"' +
		'		>' +
		'			<img src="{{:img}}" alt="{{:title}}"><span>{{:title}}</span>' +
		'		</a>' +
		'	{{/for}}' +
		'	</nav>' +
		'</div>'
	);

	$.fn.moveMallLayer = function (options) {
		var opts = $.extend({
			dataJSONUrl: '/together/moveDataUrlConnToJsonString.do', // 자사 서버 몰 이동 JSON 경로
			openBtnSelector: '#moveMallOpen', // 투게더 몰 네비게이션 오픈 버튼 Selector
			errorMsgMallData: '앗, 데이터를 불러오지 못했어요.\n잠시 후 다시 시도해 주세요.', // 몰 데이터 연동 실패 시 노출 Alert 문구
			appLowerVerMSG: '앱을 최신 버전으로 업데이트 하시면 더 많은 쇼핑 경험을 하실 수 있습니다.', // 투게더가 적용되지 않는 자사 앱에서 몰 이동 구좌 터치 시 Alert 문구
			toyAppMSG: '롯데마트몰 APP을 설치하시면 이용 가능 합니다.' // 투게더가 적용되지 않는 토이앱에서 몰 이동 구좌 터치 시 Alert 문구
		}, options);

		var self = this,
			$self = $(self),
			$html = $('html'),
			$body = $('body'),
			_scrollTop = 0,
			$btnTGMallOpen = $(opts.openBtnSelector),
			$btnClose = $self.find('icon-close'),
			$container,
			$wrapScroll,
			$btnMore,
			$linkMoveMall,
			$linkMoveMallSub,
			iOSChkFlag = navigator.userAgent.match(/iP(hone|od|ad)/);

		var APPSCHEME_TG_OPENLAYER = 'togetherApp://openlayer'; // 투게더 웹뷰에서 몰 이동 네비게이션 호출 앱 스킴 (투게더 웹뷰에서는 앱용 몰 네비게이션 노출)

		function init() {
			$btnTGMallOpen.on('click', function(e) {
				e.preventDefault();
				if (togetherObj.isTogether) {
					callAppScheme(APPSCHEME_TG_OPENLAYER);
				} else {
					if (togetherObj.isMyApp) {
						alert(opts.appLowerVerMSG);
						return false;
					}else if(togetherObj.isToyApp){
						alert(opts.toyAppMSG);
						return false;
					}else {
						layerOpen();
					}
				}
			});

			$btnClose.on('click.tg', function() {
				layerClose();
			});
		}
		function layerOpen() {
			_scrollTop = $(window).scrollTop();
			$body.css({'top': '-' + _scrollTop + 'px', 'height': $(window).height() + _scrollTop + 'px'});
			$html.addClass('layer-masking-mall');
			$self.addClass('active');

			if (!!$container) return;

			var mallData = sessionStorage.getItem('jsonMoveMallLayer');
			if (!mallData) {
				getJsonDataAsync();
				return;
			}
			$self.prepend(parseHTML(JSON.parse(mallData)));

			addEvent();
		}
		function layerClose() {
			$self.removeClass('active');
			$body.css({'top': '0', 'height': 'auto'});
			$html.removeClass('layer-masking-mall');
			if ($container.find('.sub-title').length) $container.find('.sub-title').removeClass('active');

			if (_scrollTop > 0) $(window).scrollTop(_scrollTop);
		}
		function getJsonDataAsync() {
			$.get(opts.dataJSONUrl)
				.done(function(data) {
					sessionStorage.setItem('jsonMoveMallLayer', JSON.stringify(data));
					layerOpen();
				}).fail(function() {
					layerClose();
					alert(opts.errorMsgMallData);
				})
		}
		function parseHTML(data) {
			var $docFrag = document.createDocumentFragment();
			var $div = document.createElement('div');
			$div.classList.add('move-mall-layer');
			$div.innerHTML = $.render['templateMoveMallLayer'](data);
			$docFrag.appendChild($div);
			return $docFrag;
		}
		function callAppScheme(appScheme) {
			if (iOSChkFlag) {
				var appFrame = document.createElement('iframe');
				appFrame.style.visibility = 'hidden';
				appFrame.style.display = 'none';
				appFrame.src = appScheme;

				document.querySelector('body').appendChild(appFrame);
			} else {
				window.location.href = appScheme;
			}
		}
		function addEvent() {
			$container = $self.find('.move-mall-layer');
			$wrapScroll = $container.find('.wrapper-scroll');
			$btnMore = $container.find('.sub-title');
			$linkMoveMall = $container.find('.list-move-mall .item');
			$linkMoveMallSub = $container.find('.list-move-mall-sub .item');

			var _appendQty = ($linkMoveMallSub.length > 10) ? 10 : $linkMoveMallSub.length,
				$appendQtyStr = $('<i>' + _appendQty + '</i>');
			$btnMore.append($appendQtyStr);

			$container.on('click', function(e) {
				e.stopPropagation();
			});

			$self.find('.mask').on('touchmove', function(e) {
				e.preventDefault();
			});
			if (iOSChkFlag) {
				var posY;
				$wrapScroll.on('touchstart', function(e) {
					posY = e.touches[0].pageY;
				}).on('touchmove', function(e) {
					if (e.touches.length > 1 || (e.scale && e.scale !== 1)) return;
					var flagPosDown = (e.touches[0].pageY - posY > 0),
						scrollTop = $wrapScroll.scrollTop(),
						flagScrollEnd = (scrollTop > ($wrapScroll.prop('scrollHeight') - $wrapScroll.outerHeight()));

					if (e.cancelable && ((flagPosDown && scrollTop < 1) || (!flagPosDown && flagScrollEnd))) e.preventDefault();
				});
			}

			$self.on('click', function(e) {
				if ($container.has(e.target).length == 0) layerClose();
			});

			$btnMore.on('click', function() {
				$btnMore.toggleClass('active');

				if ($btnMore.hasClass('active')) {
					$wrapScroll.animate({scrollTop: $wrapScroll.height()}, 300);
				}
			});

			$wrapScroll.on('scroll.tg', function() {
				var flagScrolling = $wrapScroll.scrollTop() > 0 && !$wrapScroll.hasClass('scrolling');
				$container.toggleClass('scrolling', flagScrolling);
			});

			$linkMoveMall.on('click.tg', function(e) {
				e.preventDefault();
				var _flagLink = $(this).attr('href').indexOf('lottemart.com') > 0;
				(_flagLink) ? tgGoTargetUrl(this) : tgGoTargetUrl(this, true);
				layerClose();
			});

			$linkMoveMallSub.on('click.tg', function() { // 더 다양한 앱 (앱 또는 웹 단순 링크 연결)
				var $target = $(this),
					androidExecUrl = $target.attr('data-android-scheme'), // 안드로이드 스토어 URL
					androidStoreUrl = $target.attr('data-android-store-url'), // 안드로이드 스토어 URL
					iosAppExecUrl = $target.attr('data-ios-exe'),
					iosStoreUrl = $target.attr('data-ios-store-url'),
					launchAppYn = $target.attr('data-launch-app'),
					linkUrl = $target.attr('data-link-url');

				if (launchAppYn == 'Y') { // 앱으로 연결
					if (iOSChkFlag) { // iOS
						var chkAt = +new Date;

						var appChkTimer = setTimeout(function () {
							if (+new Date - chkAt < 2000) {
								location.href = iosStoreUrl;
							}
						}, 1500);

						callAppScheme(iosAppExecUrl);
					} else { // Android
						if (androidExecUrl && /^intent:\/\//i.test(androidExecUrl)) {
							location.href = androidExecUrl;
						} else {
							callAppScheme(androidStoreUrl);
						}
					}
				} else { // 웹으로 연결
					window.open(linkUrl); // 새창
				}
				layerClose();
				return false;
			});
		};

		init();

		return self;
	};
})(jQuery, window, document);



// targetUrl에 따른 해당 몰 SSO Gate URL 체크
function tgCheckUrl(targetUrl) {
	var url = new URL(targetUrl);
	var ssoGateInfo = {
		'ellotte.com': 'https://m.members.ellotte.com/members-mo/login/sso/ssoGate',
		'lottemart.com': 'https://m.lottemart.com/mobile/sso/ssoGate.do',
		'lottesuper.co.kr': 'https://m.lottesuper.co.kr/handler/sso/SSOGate-Start',
		'e-himart.co.kr': 'https://msecure.e-himart.co.kr/app/sso/ssoGate',
		'lotteimall.com': 'https://securem.lotteimall.com/sso/ssoGate.lotte',
		'lohbs.co.kr': 'https://m.lohbs.co.kr/login/sso/ssoGate',
		'lotte.com': {
			'on': 'https://m.lotte.com/on/sso/ssoGate.do',
			'lottecom': 'https://m.lotte.com/sso/ssoGate.do'
		}
	};
	var ssoGateUrl = '';

	for (key in ssoGateInfo) {
		if (url.host.match(key)) {
			if (key == 'lotte.com') { // !롯데닷컴 URL로 브랜드 페이지를 운영하는 이슈로 lotte.com에 대해서만 별도 처리
				if (url.pathname.match(/^\/on\//)) { // 브랜드 ON 페이지
					ssoGateUrl = ssoGateInfo['lotte.com']['on'];
				} else { // 롯데닷컴
					ssoGateUrl = ssoGateInfo['lotte.com']['lottecom'];
				}
			} else { // 그외
				ssoGateUrl = ssoGateInfo[key];
			}
			break;
		}
	}
	return ssoGateUrl;
}

// 링크에 대한 투게더 이동 처리
function tgGoTargetUrl(target, gateLoading) {
	var ssoTknVal = $.cookie('ssoToken'),
		$target = $(target),
		targetUrl = $target.attr('href'),
		paramsObj = {},
		autoLoginFlag = !!$.cookie('isAutoLogin'),
		ownLoginFlag = !!$.cookie('lmarttoken'),
		ssoGateUrl = '';

	if (!targetUrl) return false;
	if (!togetherObj.isTogether && togetherObj.isMyApp) {
		alert('앱을 최신 버전으로 업데이트 하시면 더 많은 쇼핑 경험을 하실 수 있습니다.');
		return false;
	}
	if (togetherObj.isTogether) {
		location.href = targetUrl;
		return false;
	}

	ssoGateUrl = tgCheckUrl(targetUrl);
	if (!ssoGateUrl) {
		location.href = targetUrl;
	} else {
		if (!!gateLoading) paramsObj.gate = 'Y';
		paramsObj.targetUrl = targetUrl;
		// paramsObj.AFFILIATE_ID = '01760001';

		if (autoLoginFlag) paramsObj.auto = 'Y';

		if (!!ssoTknVal) {
			paramsObj.ssoTkn = ssoTknVal;
		} else if (ownLoginFlag) {
			paramsObj.ownLogin = 'Y';
		}

		ssoGateUrl += (ssoGateUrl.indexOf('?') > -1 ? '&' : '?') + $.param(paramsObj);
		location.href = ssoGateUrl;
	}

	return false;
}