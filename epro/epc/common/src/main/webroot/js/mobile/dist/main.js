(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory();
	} else {
		root.fnJsMsg = factory();
	}
}( window, function() {
	var _fnJsMsg = function( fl ) {
		var arg= arguments;
		 if(arg.length==0) return '';
		 if(arg.length==1) return arg[0];
		 
		 var fn = function(w, g) {
		  if(isNaN(g)) return '';
		  var idx = parseInt(g)+1;
		  if(idx >= arg.length) return '';
		  return arg[parseInt(g)+1];
		 };
		 return arg[0].replace(/\{([0-9]*)\}/g, fn);
	};
	
	return _fnJsMsg;
}));

(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory();
	} else {
		root.logout = factory();
	}
}( window, function() {
	var _logout = function( returnURL ) {
		location.href = (window._LMMembersAppSSLUrlM || $.utils.config('LMMembersAppSSLUrlM')) +"/login/logout.do?returnURL="+ returnURL;
	};
	
	return _logout;
}));
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.goProductDetailMobile = factory();
	}
}( window, function() {
	var _goProductDetailMobile = function( cateId,prodCd,popupYn,socialSeq,siteLoc,smartOfferClickUrl ) {
		var dpCode = "";
		if(typeof(smartOfferClickUrl) != "undefined" && smartOfferClickUrl != "") {
			// 스마트오퍼를 통한 상품을 클릭 시 logging 을 위해 다음을 호출하고, 결과는 받을 필요 없다. itemSetId, scnId
			try {
				var codeList = ["dpId", "itemSetId", "scnId"];
				var clickParams = smartOfferClickUrl.substring(smartOfferClickUrl.indexOf("?") + 1).split("&");
				var curParams;
				for (var i = 0 ; i < codeList.length ; i++ ) {
					curParams = $.grep(clickParams, function(obj) {
						return obj.indexOf(codeList[i] + "=") >= 0;
					});
					if (curParams != null && curParams.length > 0) {
						if (codeList[i] === "dpId") {
							dpCode += ("&" + "dp=" + curParams[0].split("=")[1]);
						}
						else {
							dpCode += ("&" + curParams[0]);
						}
					}
				}
				$.get(smartOfferClickUrl);
			}
			catch (e) {}
		}

		document.location.href = $.utils.config( 'LMAppUrlM' ) + "/mobile/cate/PMWMCAT0004_New.do?CategoryID="+cateId+"&ProductCD="+prodCd+"&socialSeq="+socialSeq +"&SITELOC=" + siteLoc+  (dpCode != "" ? dpCode:"");
	};
	
	return _goProductDetailMobile;
}));
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.goLogin = factory();
	}
}( window, function() {
	var _goLogin = function( sid, returnURL ) {
		var url = (window._LMAppUrlM || $.utils.config('LMAppUrlM')) + '/mobile/PMWMMEM0001.do?sid=' + sid + '&returnurl=' + returnURL;
		
		if(window._LMLocalDomain || $.utils.config('LMLocalDomain') == 'true') {
			url += '&mode=DEV';
		}
		window.location = url;
	};
	
	return _goLogin;
}));
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ) );
	} else {
		root.showNoImage = factory( jQuery );
	}
}( window, function( $ ) {
	var _showNoImage = function( obj, width, height ) {
		if(typeof obj.src == "undefined"){
			return "";
		}

		if ( obj.src.indexOf("noimg_prod") == -1 ) {
			var width = width || $(obj).attr("width");
			var height = height || width || $(obj).attr("height");
			
			$(obj).attr("src", ( $.utils.config( 'LMCdnV3RootUrl' ) || window._LMCdnV3RootUrl ) +"/images/layout/noimg_prod_"+ width +"x"+ height +".jpg");
		}
	};
	
	return _showNoImage;
}));
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery', 'jquery.cookie'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery'), require('jquery.cookie') );
	} else {
		root.getReturnUrl = factory(jQuery, jQuery.cookie);
	}
}( window, function($, cookie) {
	var _getReturnUrl = function(button) {
		var returnUrl = location.origin + location.pathname + location.search;
		var returnCategoryId = cookie('__categoryId');
		
		if(returnCategoryId && location.search.indexOf('returnCategoryId') < 0) {
			returnUrl += ((location['search']) ? '&' : '?') + "returnCategoryId=" + returnCategoryId;
		}
		return returnUrl;
	};
	
	return _getReturnUrl;
}));
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery', 'schemeLoader'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery'), require('schemeLoader') );
	} else {
		root.lnbShow = factory(jQuery, root.schemeLoader);
	}
}( window, function($, schemeLoader) {
	var _lnbShow = function(button) {
		var page = $('#page-wrapper'),
			tgtCls = 'globalCategory';

		page.addClass(tgtCls);
	    schemeLoader.loadScheme({key: 'lnbOpen'});
	    schemeLoader.loadScheme({key: 'hideBar'});
	};
	
	return _lnbShow;
}));
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

(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery', 'schemeLoader'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery'), require('schemeLoader') );
	} else {
		root.lnbClose = factory(jQuery, root.schemeLoader);
	}
}( window, function($, schemeLoader) {
	var _lnbClose = function() {
		var wrapper = $('html, body'),
			page = $('#page-wrapper'),
			tgtCls = 'globalCategory',
			top = sessionStorage.getItem( 'openLnbTop' );
		
		page.removeClass(tgtCls);
		wrapper.removeClass('masking');
		
		$('.trigger-category-submenu').removeClass('trigger-category-submenu');
		$('li.active').removeClass('active');
		
		$('.wrap-inner-scroll').scrollTop(0);
		
		$( 'html, body' ).scrollTop( top ? top : 0 );
		
		setTimeout(function() {
	        schemeLoader.loadScheme({key: 'lnbClosed'});
	        schemeLoader.loadScheme({key: 'showBar'});
		}, 300);
	};
	
	return _lnbClose;
}));
(function( root, factory ) {
	'use strict';

	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory();
	} else {
		root.ban_close = factory();
	}
}( window, function() {
	var _ban_close = function() {
		var d = new Date();
		d.setDate(d.getDate() + 1); //1일 뒤 이 시간
		var expires = "expires="+d.toGMTString();
		document.cookie = "viewban=Y;" + expires;
		if (document.querySelector('#settingTopBanner')) document.querySelector('#settingTopBanner').style.display = 'none';
	};

	return _ban_close;
}));
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.areaTooltipLayerPopup = factory( jQuery );
	}
}( window, function($) {
	var _areaTooltipLayerPopup = function(obj, elem, lTop, openArea) {
		var openEl ;
		if(openEl instanceof jQuery){
			openEl = openArea;
		}else{
			if(!openArea){
				openArea = 'body';
			}
			openEl = $(openArea);
		}
		
	    var $el = openEl.find('[data-layer='+elem+']');

	    obj.addClass('active');
	    
	    openEl.addClass('layer-popup-active').append($el).append('<div class="mask"/>');
	    $el.css('top', lTop);
	    $el.fadeIn(200);

	    openEl.find('.mask, .js-close').click(function(){
	        $el.hide();
	        obj.removeClass('active');
	        openEl.find('.mask').remove();
	        openEl.removeClass('layer-popup-active');
	    });
	};
	
	return _areaTooltipLayerPopup;
}));
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ) );
	} else {
		root.setAdultReturnUrl = factory( jQuery );
	}
}( window, function( $ ) {
	function _setAdultReturnUrl() {
		var urlAdult = location.href;
	
		return location.href = $.utils.config( 'LMAppUrl' ) + "/product/loginBlank.do?urlAdult="+ encodeURIComponent(urlAdult);
	}
	
	return _setAdultReturnUrl;
}));
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.global = root.global || {};
		root.global.familyJoin = factory(jQuery);
	}
}( window, function($) {
	var _familyJoin = function(param) {
		var url = "/imember/member/join/memberJoin.do?sid=" + $.utils.config( 'SID_NM_MARTMALL' ) + (!!param ? "&" + param : "") + "&memberJoinYN=Y";
		if ( location.protocol === "http:" ) {
			url = $.utils.config( 'LMMembersAppUrl' ) + url;
		}
		else {
			url = $.utils.config( 'LMMembersAppSSLUrl' ) + url;
		}
		
		var popWin = window.open(url, "_blank");
		popWin.focus();
	};
	
	return _familyJoin;
}));
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.global = root.global || {};
		root.global.login = factory(jQuery);
	}
}( window, function($) {
	var _login = function(sid, returnUrl) {
		var url;
		url = (window._LMAppSSLUrlM || $.utils.config('LMAppSSLUrlM')) + "/mobile/PMWMMEM0001.do?sid=" + sid + "&returnurl=" + encodeURIComponent(returnUrl);
	
		window.location.href=url;
	};
	
	return _login;
}));
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery', 'login'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( requrie('jquery'), require( 'login' ) );
	} else {
		root.global = root.global || {};
		root.global.isLogin = factory(jQuery, root.global.login);
	}
}( window, function($, login) {
	var _isLogin = function(redirectUrl, successFunc, failFunc) {
		var param = {
			redirectUrl: redirectUrl
		};
		var result = true;

		$.api.set({
			apiName : 'isLogin',
			async : false,
			data : {
				redirectUrl : redirectUrl
			},
			successCallback : function( data ) {
				result = data.isLogin === "Y";
				if (result) {
					if (typeof(successFunc) != "undefined") {
						successFunc();
					}
				}
			}
		});
		
		if (!result) {
			if (typeof(failFunc) != "undefined") {
				failFunc();
			}
			else {
				login($.utils.config( 'SID_NM_MMARTMALL' ), redirectUrl );
			}
		}
		return result;
	};
	
	return _isLogin;
}));
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define([
		        'jquery', 
		        'goLogout',
		        'familyJoin',
		        'isLogin'
		        ], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( 
				require( 'jquery' ),
				require( 'goLogout' ),
				require( 'familyJoin'),
				require( 'isLogin' ) 
		);
	} else {
		root.global = root.global || {};
		root.global.addBasket = factory( jQuery, root.goLogout, root.global.familyJoin, root.global.isLogin );
	}
}( window, function( $, goLogout, familyJoin, isLogin ) {
	var _addBasket = function(params, successFunc, failFunc) {
		var defaultItem = {
				prodCd: "",			// 상품코드
				itemCd: "001",			// 단품코드
				bsketQty: 1,			// 주문수량
				categoryId: "",		// 카테고리ID
				nfomlVariation: "",	// 옵션명, 골라담기의 경우 옵션명:수량
				overseaYn: 'N',			// 해외배송여부
				prodCouponId: "",		// 즉시할인쿠폰ID
				oneCouponId: "",		// ONE 쿠폰ID
				cmsCouponId: "",		// CMS 쿠폰ID
				markCouponId: "",		// 마케팅제휴쿠폰ID
				periDeliYn: 'N',		// 정기배송여부
				mstProdCd: "",		// 딜상품의 대표상품코드
				saveYn: "N",			// 계속담기 여부
				ctpdItemYn: "N",		// 구성품 여부
				ctpdProdCd: "",		// 구성품 원 상품코드
				ctpdItemCd: "",		// 구성품 원 단품코드
				ordReqMsg: "",		// 구매요청사항
				hopeDeliDy: ""		// 희망배송일
			};
			var items = [];
			var basketItem = null;
			var validResult = true;
			var successCallback = function(data) {
				if(window.fbq){
					// facebook dynamic remarketing 스크립트변경 2019-02-08
	            	fbq('track', 'AddToCart', {
	            		content_ids: data.basketItem.prodCd,
	                	content_type: 'product'
	                });
				}
				
				if (typeof(successFunc) != "undefined" && $.isFunction(successFunc)) {
					successFunc(data);

					var _prodTitle = $( '.basket' ).filter( '[data-prod-cd="' + data.basketItem.prodCd[0] + '"]' ).data('prod-title');

					window.ga('send', 'event', {
						eventAction: '장바구니 담기',
						eventCategory: 'M | Common | LotteMartMall | ' + location.pathname,
						eventLabel : 'CIP | PID | ' + _prodTitle
					});
				}
			};
			
			if( $.utils.config( 'GuestMember_yn' ) == 'true' && $.utils.config( 'GuestMember_type' ) == "002"){
	   			if(confirm("비회원 배송 조회는 주문한 내역만 확인 가능합니다.\n장바구니는 회원가입 후 이용할 수 있습니다.\n회원 가입 후 이용하시겠습니까?")){
	   				goLogout();
	   				familyJoin();
	   				return;
				}else {
					return;
				}
	   		}
			
			var failCallback = function(xhr, status, error) {
				if (xhr) {
					var data = $.parseJSON(xhr.responseText);
					if (data && data.message) {
						if( window.msg_basket_errors_no_shipping === data.message ) {
							alert( '롯데마트몰의 상품은 고객님의 가까운\n롯데마트 매장에서 배송됩니다.\n상품을 받으실 배송지를 먼저 등록하신 후\n이용해 주세요.' );
							location.href = $.utils.config( 'LMAppUrlM' ) + '/mobile/popup/selectMemberDeliveryForm.do';
						}else if(data.error == "adult"){
							 goProductDetailMobile(
				                        params.categoryId,
				                        params.prodCd,
				                        'N',
				                        '',
				                        '',
				                        ''
				                    );
						}else {							
							alert(data.message);
						}
						if ( data.redirectUrl != null && data.redirectUrl.trim().length > 0 ) {
							location.href = redirectUrl;
							return;
						}
					}
				}
			};
			var validFailFunc = function(msg) {
				validResult = false;
				var validErrorXhr = {
					responseText: "{\"message\":\"" + msg + "\"}",
					status: "400"
				};
				
				failCallback(validErrorXhr, "validation error");
			};

			var search = location.search;
			if (search == null || $.trim(search) === "" ) {
				search = location.href.substring(location.href.indexOf("?"));
			}

			var argItems = params;
			if (!$.isArray(params)) {
				argItems = [];
				argItems.push(params);
			}
			
			$.each(argItems, function(i, item) {
				basketItem = $.extend({}, defaultItem, item);
				
				if (basketItem.prodCd == null || $.trim(basketItem.prodCd) === "" ) {
					// 상품코드 validation error
					validFailFunc("선택한 상품이 없습니다.");
					return false;
				}
				if (basketItem.itemCd == null || $.trim(basketItem.itemCd) === "" ) {
					// 단품코드 validation error
					validFailFunc("선택한 옵션 상품이 없습니다.");
					return false;
				}
				if (basketItem.bsketQty == null || parseInt(basketItem.bsketQty) <= 0 ) {
					// 상품수량 validation error
					validFailFunc("수량을 입력해 주시기 바랍니다.");
					return false;
				}
				if (basketItem.categoryId == null || $.trim(basketItem.categoryId) === "") {
					if (search !== "" ){
						search = search.substring(1);
						var p = search.split("&");
						$.each(p, function(j, o) {
							var v = o.split("=");
							if (v[0].toUpperCase() === "CATEGORYID") {
								basketItem.categoryId = v[1];
								return false;
							}
						});
					}
				}
				items.push(basketItem);
			});
			
			
			if (!validResult) {
				return;
			}
			
			if (typeof(failFunc) != "undefined" && $.isFunction(failFunc)) {
				failCallback = failFunc;
			}
			
			var dataParams = "";
			
			$.each(items, function(i, o) {
				dataParams += ("&" + $.param(o));
			});
			if (dataParams.length > 0) {
				dataParams = dataParams.substring(1);
			}
			
			isLogin(document.location.href, function() {
				$.api.set({
					apiName : 'basketAdd',
					data : dataParams,
					successCallback : successCallback,
					errorCallback : failCallback
				});
				// 장바구니 추가 시 wiselog 로깅 스크립트 호출
				try {
					if (typeof(window.n_click_logging) != "undefined") {
						$.each(items, function(kk, kObj) {
							window.n_click_logging((window._LMAppUrlM || $.utils.config('LMAppUrlM')) + '/basket/pickup.do?ProdCd=' + kObj.prodCd);
						});
					}
				}
				catch ( e ) {
					//
				}
			});
	};
	
	return _addBasket;
}));
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define([
		        'jquery', 
		        'goLogout',
		        'setAdultReturnUrl',
		        'isLogin'
		        ], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( 
				require( 'jquery' ),
				require( 'goLogout' ),
				require( 'setAdultReturnUrl' ),
				require( 'isLogin' ) 
		);
	} else {
		root.global = root.global || {};
		root.global.addDirectBasket = factory( jQuery, root.goLogout, root.setAdultReturnUrl, root.global.isLogin );
	}
}( window, function( $, goLogout, setAdultReturnUrl, isLogin ) {
    var genDomInput = function( elemName, elemValue ) {
        var input = document.createElement("input");

        input.setAttribute("type", "hidden");
        input.setAttribute("name", elemName);
        input.setAttribute("id", elemName);
        input.setAttribute("value", elemValue);

        return input;
    };
	var _addDirectBasket = function(params, failFunc) {
		var defaultItem = {
				bsketNo: "",		// 장바구니번호(비워둘것)
				prodCd: "",			// 상품코드
				itemCd: "001",			// 단품코드
				bsketQty: 1,			// 주문수량
				categoryId: "",		// 카테고리ID
				nfomlVariation: "",	// 옵션명, 골라담기의 경우 옵션명:수량
				overseaYn: 'N',			// 해외배송여부
				prodCouponId: "",		// 즉시할인쿠폰ID
				oneCouponId: "",		// ONE 쿠폰ID
				cmsCouponId: "",		// CMS 쿠폰ID
				markCouponId: "",		// 마케팅제휴쿠폰ID
				periDeliYn: 'N',		// 정기배송여부
				mstProdCd: "",		// 딜상품의 대표상품코드
				saveYn: "N",			// 계속담기 여부
				ctpdItemYn: "N",		// 구성품 여부
				ctpdProdCd: "",		// 구성품 원 상품코드
				ctpdItemCd: "",		// 구성품 원 단품코드
				ordReqMsg: "",		// 구매요청사항
				hopeDeliDy: ""		// 희망배송일
			};
			var items = [];
			var basketItem = null;
			var validResult = true;
			
			var failCallback = function(xhr, status, error) {
				if (xhr) {
					var data = $.parseJSON(xhr.responseText);
					if (data && data.message) {
						if( window.msg_basket_errors_no_shipping === data.message ) {
							if( confirm( '롯데마트몰의 상품은 고객님의 가까운\n롯데마트 매장에서 배송됩니다.\n상품을 받으실 배송지를 먼저 등록하신 후\n이용해 주세요.' ) ) {
								location.href = $.utils.config( 'LMAppUrlM' ) + '/mobile/popup/selectMemberDeliveryForm.do';
								
							}
						} else {
							alert(data.message);
							if ( data.redirectUrl != null && data.redirectUrl.trim().length > 0 ) {
								location.href = redirectUrl;
								return;
							}
						}
					}
				}
			};
			
			if (typeof(failFunc) != "undefined" && $.isFunction(failFunc)) {
				failCallback = failFunc;
			}
			
			var validFailFunc = function(msg) {
				validResult = false;
				var validErrorXhr = {
					responseText: "{\"message\":\"" + msg.replace(/\n/g, '\\n') + "\"}",
					status: "400"
				};
				failCallback(validErrorXhr, "validation error");
			};

			var search = location.search;
			if (search == null || $.trim(search) === "" ) {
				search = location.href.substring(location.href.indexOf("?"));
			}

			var argItems = params;
			if (!$.isArray(params)) {
				argItems = [];
				argItems.push(params);
			}
			
			$.each(argItems, function(i, item) {
				basketItem = $.extend({}, defaultItem, item);
				
				if (basketItem.prodCd == null || $.trim(basketItem.prodCd) === "" ) {
					// 상품코드 validation error
					validFailFunc("선택한 상품이 없습니다.");
					return false;
				}
				if (basketItem.itemCd == null || $.trim(basketItem.itemCd) === "" ) {
					// 단품코드 validation error
					validFailFunc("선택한 상품이 없습니다.");
					return false;
				}
				if (basketItem.bsketQty == null || parseInt(basketItem.bsketQty) <= 0 ) {
					// 상품수량 validation error
					validFailFunc("수량을 입력해 주시기 바랍니다.");
					return false;
				}
				if (basketItem.categoryId == null || $.trim(basketItem.categoryId) === "") {
					if (search !== "" ){
						search = search.substring(1);
						var p = search.split("&");
						$.each(p, function(j, o) {
							var v = o.split("=");
							if (v[0].toUpperCase() === "CATEGORYID") {
								basketItem.categoryId = v[1];
								return false;
							}
						});
					}
				}
				items.push(basketItem);
			});
			
			if (!validResult) {
				return;
			}
			
			var dataParams = "";
			
			$.each(items, function(i, o) {
				dataParams += ("&" + $.param(o));
			});
			if (dataParams.length > 0) {
				dataParams = dataParams.substring(1);
			}
			
			isLogin(null, function() {
				$.api.set({
					apiName : 'basketAddDirect',
					data : dataParams,
					successCallback : function( data ) {
						var param = data[0];

						if(param.ERR_NO != "0"){
							if(param.ERR_NO =="8"){
								setAdultReturnUrl();
								return;
							}else{
								//alert(param.ERR_MSG);
								var msg = param.ERR_MSG.replace(/\n/g, '\\n');
								validFailFunc(msg);
								return;
							}
						}//if
						
						var findItem = function(list, item) {
							var objArr = $.grep(list, function(o) {
								return o.PROD_CD == item.prodCd && o.ITEM_CD == item.itemCd;
							});
							if (objArr != null && objArr.length > 0) {
								return objArr[0];
							}
							return null;
						};

						if(param.ERR_NO == "0"){
							if ($("#tForm input[name=prodCd]").length < 1 ) {
								var basketInfo = null;
								$.each(items, function(i, item) {
									basketInfo = findItem(param.basketList, item);
									if (basketInfo != null) {
										$('#divTemp').append(genDomInput("bsketNo", basketInfo.BSKET_NO));
										$('#divTemp').append(genDomInput("prodCd", item.prodCd));
										$('#divTemp').append(genDomInput("itemCd", item.itemCd));
										$('#divTemp').append(genDomInput("bsketQty", item.bsketQty));
										$('#divTemp').append(genDomInput("categoryId", item.categoryId));
										$('#divTemp').append(genDomInput("nfomlVariation", item.nfomlVariation));
										$('#divTemp').append(genDomInput("overseaYn", item.overseaYn));
										$('#divTemp').append(genDomInput("prodCouponId", item.prodCouponId));
										$('#divTemp').append(genDomInput("oneCouponId", item.oneCouponId));
										$('#divTemp').append(genDomInput("cmsCouponId", item.cmsCouponId));
										$('#divTemp').append(genDomInput("markCouponId", item.markCouponId));
										$('#divTemp').append(genDomInput("periDeliYn", item.periDeliYn));
										$('#divTemp').append(genDomInput("mstProdCd", item.mstProdCd));
										$('#divTemp').append(genDomInput("saveYn", item.saveYn));
										$('#divTemp').append(genDomInput("ctpdItemYn", item.ctpdItemYn));
										$('#divTemp').append(genDomInput("ctpdProdCd", item.ctpdProdCd));
										$('#divTemp').append(genDomInput("ctpdItemCd", item.ctpdItemCd));
										$('#divTemp').append(genDomInput("ordReqMsg", item.ordReqMsg));
										$('#divTemp').append(genDomInput("hopeDeliDy", item.hopeDeliDy));
									}
								});
							}
							//$('#divTemp').append(genDomInput("bsketNo", param.bsketNo));
							$('#divTemp').append(genDomInput("basketType", param.basketType));
							$('#divTemp').append(genDomInput("deliTypeCd", param.deliTypeCd));
							$('#divTemp').append(genDomInput("bsketDivnCd", "02"));

							$('#tForm').attr('action', $.utils.config( 'LMAppUrlM' )+'/mobile/basket/insertPreOrder.do').submit();
						}//if
					},
					errorCallback : failCallback
				});
			});
	};
	
	return _addDirectBasket;
}));
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.global = root.global || {};
		root.global.storeView = factory(jQuery, root.global.storeView);
	}
}( window, function($) {
	var _rtn = function(stdistMallYn) {
		if( stdistMallYn == "N" ) {
			alert("고객님의 현재 배송지는 전용센터 택배 배송지입니다.\n택배상품은 결제일 다음날부터 2~3일 이내 발송됩니다.");
		} else {
			var url;
			url = (window._LMAppSSLUrlM || $.utils.config('LMAppSSLUrlM')) + "/mobile/popup/deliverytime.do";
		
			window.location.href = url;
		}
	};
	
	return _rtn;
}));
(function( $, window, document, undefined ) {
	'use strict';
	
	var pageDomain = {
			index : '메인',
			category: '카테고리',
			todayhot : '오늘HOT콕',
			plan : '기획전',
			best : '베스트',
			delivery : '정기배송',
			event : '이벤트&쿠폰',
			trend : '트렌드',
			recipe : '요리왕 장보고',
			product : '상품 상세',
			special : '전문관',
			mymart : '마이롯데'
		},
		urlRegex = /(?:http[s]*)\:\/\/(?:[^.]+)\.lottemart\.com\/([^/\n.]*)/,
		urlExec;
	
	(function init() {
		$(setLogLabel);
	})();
	
	function setLogLabel() {
		//if($.utils.config('ReqURL') == '') return false;
		urlExec = urlRegex.exec( location.href );
		
		var _pageTitle = ($.utils.config('pageTitle') === undefined) ? '' : $.utils.config('pageTitle');

		//set pageDomain
		if(urlExec !== null) {
            $.utils.config('pageDomain', pageDomain[urlExec[1]] || urlExec[1]);
        }
	}
	

	$.eventLog = {
		ga: function(log) {
			if( ga ) {
				ga( 'send', 'event', log );
				//console.log(log);
			}
		}
	};
	
})( jQuery, window, document );
(function($){
	
	$.deparam = $.deparam || function(queryString){
		if(queryString === undefined) {
			queryString = window.location.search;
		}

		var parameters = {};
		if(queryString != null && queryString != '') {
			if(queryString.indexOf('?') == 0) {
				queryString =  queryString.slice(1);
			}
			
			var keyValuePairs = queryString.split('&');
			for(var i = 0; i < keyValuePairs.length; i++) {
				var keyValuePair = keyValuePairs[i].split('=');
				
				parameters[keyValuePair[0]] = keyValuePair[1];
			}
		}
		
		return parameters;
	};
	
})(jQuery);
(function($, window, document, undefined) {
	'use strict';
	
	$.fn.moveToEnterpriseMemberForm = function(options) {
		var $this = $(this);
		
		if(validate(options)) {
			location.href = '/mobile/enterprise/members/form.do';
		}
	};
	
	function validate(options) {
		if(!options.isLpointMember) {
			alert('기업회원 등록은 L.POINT 통합회원만 이용가능합니다. 통합회원으로 로그인 후 이용해주세요.')
			return false;
		}
		
		var isValid = false;
		$.ajax({
			url : '/api/enterprise/members/detail.do',
			async : false,
			success : function(res) {
				if(res.enterpriseMember && res.enterpriseMember.authDate) {
					if(confirm('이미 롯데마트 기업회원 등록이 완료되었습니다. 수정을 원하시면 확인 버튼을 눌러주세요.')) {
						isValid = true;	
					}
				} else {
					isValid = true;
				}
			}
		});
		
		return isValid;
	}
})( jQuery, window, document );
(function( $, window, document, undefined ) {
	'use strict';
	/**
	 * See (http://jquery.com/).
	 * @name jQuery
	 * @class
	 * See the jQuery Library  (http://jquery.com/) for full details.  This just
	 * documents the function and classes that are added to jQuery by this plug-in.
	 */

	/**
	 * See (http://jquery.com/)
	 * @name fn
	 * @class
	 * See the jQuery Library  (http://jquery.com/) for full details.  This just
	 * documents the function and classes that are added to jQuery by this plug-in.
	 * @memberOf jQuery
	 */

	/**
	 * <h5>loadingBar</h3>
	 * <p>서버와 통신 시( ajax ) loading 이미지 노출</p>
	 * @class loadingBar
	 * @memberOf jQuery.fn
	 * @param{Boolean} argument - 로딩 여부
	 * @example <caption>JS - loading</caption>
	 * <script type="text/javascript">
	 * 	//default - true
	 *	//show loading
	 * 	$( 'body' ).loadingBar();
	 * </script>
	 * @example <caption>JS - unloading</caption>
	 * <script type="text/javascript">
	 * 	//close loading
	 * 	$( 'body' ).loadingBar( false );
	 * </script>
	 *
	 */
	$.fn.loadingBar = function( isShow ) {
		var $this = this,
			isApp = $.utils ? ($.utils.isiOSLotteMartApp() || $.utils.isAndroidLotteMartApp()) : false,
			loadFunction = isShow === undefined || isShow ? open : close,
			html = $.render.loadingBarForMobile();

		var wrapLoadingBarElement = $this[0].querySelector( '.wrapLoadingBar' );

		loadFunction();

		return this;

		function open() {
			if(isApp) {
                if (window.LOTTEMARTDID && window.LOTTEMARTDID['isLoading']) {
                    window.LOTTEMARTDID.isLoading(true);
                } else {
                    schemeLoader.loadScheme({key: 'lodingStart'});
                }
			} else {
				if( wrapLoadingBarElement === null ) {
					$this.append( html );

					wrapLoadingBarElement = $this[0].querySelector( '.wrapLoadingBar' );
				}

				wrapLoadingBarElement.classList.add( 'pageLoading' );
				wrapLoadingBarElement.style.display = 'block';
			}
		}

		function close() {
			if(isApp) {
                if (window.LOTTEMARTDID && window.LOTTEMARTDID['isLoading']) {
                    window.LOTTEMARTDID.isLoading(false);
                } else {
                    schemeLoader.loadScheme({key: 'lodingEnd'});
                }
			} else {
				if( wrapLoadingBarElement === null ) {
					return;
				}

				wrapLoadingBarElement.classList.remove( 'pageLoading' );
				wrapLoadingBarElement.style.display = 'none';
			}
		}
	};
})( jQuery, window, document );
(function( $, window, document, undefined ) {
	'use strict';
	/**
	 * See (http://jquery.com/).
	 * @name jQuery
	 * @class 
	 * See the jQuery Library  (http://jquery.com/) for full details.  This just
	 * documents the function and classes that are added to jQuery by this plug-in.
	 */

	/**
	 * See (http://jquery.com/)
	 * @name fn
	 * @class 
	 * See the jQuery Library  (http://jquery.com/) for full details.  This just
	 * documents the function and classes that are added to jQuery by this plug-in.
	 * @memberOf jQuery
	 */
	
	/**
	 * <h5>moreBar</h3>
	 * <p>리스트 더보기 시 more 이미지 노출</p>
	 * @class moreBar
	 * @memberOf jQuery.fn
	 * @param{Boolean} argument - 로딩 여부
	 * @example <caption>JS - more</caption>
	 * <script type="text/javascript">
	 * 	//default - true
	 *	//show more
	 * 	$( '.list' ).moreBar();
	 * </script>
	 * @example <caption>JS - unloading</caption>
	 * <script type="text/javascript">
	 * 	//close more
	 * 	$( '.list' ).moreBar( false );
	 * </script>
	 * 
	 */
	$.fn.moreBar = function( isShow ) {
		var $this = this,
			loadFunction = isShow === undefined || isShow ? open : close,
			html = '<div class="more-bar"><span class="spinner">잠시만 기다려주세요.</span></div>';

		loadFunction();
		
		return this;
		
		function open() {
			if( $this.find( '.more-bar' ).length === 0 ) {
				$this.append( html );
			}

			$this.find( '.more-bar' ).show();
		}
		
		function close() {
			$this.find( '.more-bar' ).hide();
		}
	};

})( jQuery, window, document );
//jquery.utils, jquery.product-option-layer, jquery.deal-option-layer, jquery.pick-option-layer
(function( factory ) {
	'user strict';

	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ) );
	} else {
		factory( jQuery );
	}
}(function( $ ) {
	'use strict';

	$.fn.basket = function( options ) {
		var $btnBasket = null;

		var defaults = {
			$wrapper : null,
			wrapperClass : '[data-panel="product"]',
			isMobile : $.utils.isMobile(),
			mallDivnCd : '00001'
		};

		return this.each(function( i, v ) {
			$(v).on( 'click', '[data-method="basket"], [data-method="buy"]' , function() {
				$btnBasket = $( this );

				var data = $btnBasket.data();

				if( data.periodDeliveryYn === 'Y' ) {
					if( $.utils.config( 'Member_yn' ) === 'false' && $.utils.config( 'GuestMember_yn' ) === 'true') {
						alert("L.POINT 통합회원 또는 롯데마트 회원만 정기배송 신청이 가능합니다. 회원 가입 후, 신청해 주세요");
						return false;
					}
				}

				var config = $.extend( true, {}, defaults, options, data );

				config.$wrapper = $btnBasket.closest( config.wrapperClass );

				if(config.isSoldOut){
					alert( '이 상품은 품절되었습니다.' );
				} else if(config.isManufacturingProduct){
					alert( '설치상품, 주문제작상품은 상품 상세에서 담으실 수 있습니다.' );
					if( config.isMobile ) {
						_goProductDetailMobile( config.categoryId, config.prodCd, 'N', '', '', config.mallDivnCd, '' );
					} else {
						_goProductDetail( config.categoryId, config.prodCd, 'N', '', '' , config.mallDivnCd, '');
					}
				} else {
					switch( config.method ) {
						case 'buy' :
							execBuy( config );
							break;
						default :
							execBasket( config );
							break;
					}
				}
				return false;
			});
		});

		function showOptionLayer( options ){
			switch( options.prodTypeCd ) {
				case '04' :
					$btnBasket.pickOptionLayer( options );
					break;
				case '05' :
					$btnBasket.dealOptionLayer( options );
					break;
				default :
					$btnBasket.productOptionLayer( options );
					break;
			}
		};

		function execBasket( options ) {
			if( options.optionYn === 'Y' ) {
				options.successCallBack = function( opts ) {
					callbackAddBasket( opts );
				};
				showOptionLayer( options );
				if(!$.utils.isMobile()){
					_wac.basketTabHandler.tabEvent($btnBasket);
				}
			} else {
				callbackAddBasket( _getParams( options ) );
			}
		}

		function execBuy( options ) {
			if( options.optionYn === 'Y' ) {
				options.successCallBack = function( opts ) {
					callbackAddDirectBasket( opts );
				};
				showOptionLayer( options );
			} else {
				callbackAddDirectBasket(  _getParams( options ) );
			}
		}

		function callbackAddDirectBasket( options ) {
			global.addDirectBasket( options.params );
		}

		function callbackAddBasket( options ) {
			global.addBasket( options.params , function( data ) {
				var basketNo = data.basketItem.bsketNo ? data.basketItem.bsketNo[0] : '',
					totalSellAmt = data.basketItem.totSellAmt ? data.basketItem.totSellAmt : '',
					prodNm = data.basketItem.prodNm ? data.basketItem.prodNm[0] : '',
					currSellPrc = data.basketItem.currSellPrc ? data.basketItem.currSellPrc : '',
					basketQty = data.basketItem.bsketQty ? data.basketItem.bsketQty[0] : '',
					categoryId = data.basketItem.categoryId ? data.basketItem.categoryId[0] : '',
					/* 2018.01.15 adssom 1.0 주석 처리
					adId = options.isMobile ? 598 : 600;
					*/
					adId = 'C03';

				if( options.isMobile ) {
					basketCount();
                    schemeLoader.loadScheme({key: 'basketCountUpdate'});
					alert("장바구니에 담겼습니다.");

					$( options.target || 'body' )
						.off('click.layer')
						.removeClass('layer-popup-active')
						.find(options.layerClass)
						.each(function() {
							$(this).remove();
							$('.mask').remove();
						});
				} else {
					$( options.target || 'body' ).completeAddBasket({
						$el : $btnBasket,
						layerClass : options.layerClass,
						contents : '<strong>' + ( options.periodDeliveryYn === 'Y' ? '정기배송 장바구니에 상품이 담겼습니다.' : '장바구니에 상품이 담겼습니다.' ) + '</strong><br>지금 확인하시겠습니까?' +
						'<div class="set-btn">' +
							'<button type="button" class="btn-form-type1" onclick="global.goBasket();return false;">확인</button>'+
							'<button type="button" class="btn-form-type2 btn-close" onclick="fadeContHide( this, event );">취소</button>'+
						'</div>'
					});
					if(!$.utils.isMobile()){
						_wac.basketTabHandler.tabEvent($btnBasket);
					}
				}
				if( window._seedConversionAnalysis ) {
					_seedConversionAnalysis(adId, basketNo, totalSellAmt , prodNm, currSellPrc , basketQty, categoryId );
				}
			});
			if(!$.utils.isMobile()){
				_wac.basketTabHandler.tabEvent($btnBasket);
			}
		}

		function _getParams( options ) {
			var $wrapper = options.$wrapper,
				$orderQty = $wrapper.find( '[name^="orderQty"]' );

			options.params = {
				prodCd: options.prodCd,			// 상품코드
				itemCd: '001',			// 단품코드
				bsketQty: $orderQty.val(),			// 주문수량
				categoryId: options.categoryId,		// 카테고리ID
				nfomlVariation: '',	// 옵션명, 골라담기의 경우 옵션명:수량
				periDeliYn: options.periodDeliveryYn		// 정기배송여부
			};

			return options;
		}

		function _goProductDetailMobile(cateId,prodCd,popupYn,socialSeq,siteLoc,mallDivnCd,smartOfferClickUrl){
			var dpCode = "";
			if(typeof(smartOfferClickUrl) != "undefined" && smartOfferClickUrl != "") {
				$.api.get({
					url : smartOfferClickUrl,
					dataType : 'jsonp'
				});

				dpCode = $.utils.getParamFoWiseLog( smartOfferClickUrl );
			}
			if( mallDivnCd === '00002' ) {
				if( cateId == undefined || cateId == "" || prodCd == undefined || prodCd == "" ){
					alert('잘롯된 상품정보 입니다.');
			        return false;
			    }

				location.href = "http://m.toysrus.lottemart.com/mobile/cate/ProductDetail.do?CategoryID="+cateId+"&ProductCD="+prodCd;
			} else {
				location.href = $.utils.config( 'LMAppUrlM' ) + "/mobile/cate/PMWMCAT0004_New.do?CategoryID="+cateId+"&ProductCD="+prodCd+"&socialSeq="+socialSeq +"&SITELOC=" + siteLoc+  (dpCode != "" ? dpCode:"");
			}
		 }

		function _goProductDetail(cateId,prodCd,popupYn,socialSeq,koostYn,mallDivnCd,smartOfferClickUrl){
			var popYn=popupYn;
			var koost_Yn=koostYn;
			var socialSeqVal="";
			var mallDivn = mallDivnCd;
			var url = $.utils.config( 'LMAppUrl' );
			if(popupYn==""){
				popYn="N";
			}
			if(koost_Yn == undefined || koost_Yn == null|| koost_Yn==""){
				koost_Yn="N";
			}

			//온라인몰 = 00001
			if(mallDivn === undefined)
				url = $.utils.config( 'LMAppUrl' );
			else{
				if(mallDivn !== "00001") {
					url = $.utils.config( 'LMTruAppUrl' );
				} else {
					url = $.utils.config( 'LMAppUrl' );
				}
			}

			if(prodCd=="" || prodCd==null){
				//alert("상품코드가 존재하지 않습니다.");
				alert( msg_product_error_noPro);
				if(popYn=="Y"){
					self.close();
				}
				return;
			}
			if(socialSeq == undefined || socialSeq == null|| socialSeq==""){
				socialSeqVal="";
			}else{
				socialSeqVal=socialSeq;
			}
			var dpCode = "";
			if(typeof(smartOfferClickUrl) != "undefined" && smartOfferClickUrl != "") {
				$.api.get({
					url : smartOfferClickUrl,
					dataType : 'jsonp'
				});

				dpCode = $.utils.getParamFoWiseLog( smartOfferClickUrl );
			}

			if(popYn=="Y"){
				opener.document.location.href = url+"/product/ProductDetail.do?ProductCD="+prodCd+"&CategoryID="+cateId+"&socialSeq="+socialSeqVal+"&koostYn="+koost_Yn + (dpCode != "" ? dpCode:"");
				self.close();
			}else {
				document.location.href = url+"/product/ProductDetail.do?ProductCD="+prodCd+"&CategoryID="+cateId+"&socialSeq="+socialSeqVal+"&koostYn="+koost_Yn + (dpCode != "" ? dpCode:"");
			}
		  }

		//장바구니 수량 조회
		function basketCount() {
			$.getJSON("/basket/api/count.do")
			.done(function(data) {
				if (data) {
					if( data.count > 0 ) {
						$('.action-basket').attr('data-qty', data.count);
					}
				}
			});
		}
	};


}));
(function( $, window, document, undefined ) {
	'use strict';
	
	$.fn.productOptionLayer = function( options ) {
		var defaults = {
			title : '옵션을 선택하세요',
			layerClass : '.layerpopup-type1',
			btnPlusClass : '.plus',
			btnMinusClass : '.minus',
			btnCloseClass : '.close',
			optionFieldClass : '#optionField',
			wrapperQuantityClass : '.spinner',
			quantityClass : '[name="quantity"]',
			subOptionFieldClass : '#subOptionField',
			target : 'body',
			templateName : 'mobileBasketOptionForDefault',
			marginTop : 4,
			periodDeliveryYn : 'N'
		},
		$this = this,
		config = $.extend( {}, defaults, options || {});
		
		var $target = $( config.target ),
			$layer = null;
		
		(function init() {
			config = $.extend( config, $this.data() );
			
			removeAllLayer();
			render();
		})();
		
		//bind event
		function bindEvent() {
			$layer.on( 'click', config.btnCloseClass, function() {
				close();
				
				return false;
			}).on( 'click', config.btnPlusClass, function() {
				quantityPlus( this );
				
				return false;
			}).on( 'click', config.btnMinusClass, function() {
				quantityMinus( this );
				
				return false;
			}).on( 'change', config.quantityClass, function() {
				validateQuantity( $( this ) );
			}).on( 'change', config.optionFieldClass + ' select', function() {
				reset();
				onChangeOptionSelect( this );
				
			}).on( 'change', config.subOptionFieldClass + ' select', function() {
				onChangeSubOptionSelect( this );
			});
			
			$target.on( 'click.layer', function( e ) {
				var $this = $( e.target );
				
				if( $this.closest( config.layerClass ).length === 0 ) {
					removeAllLayer();
				}
			});

            $('div.mask').on('click', function () {
                if ($(config.layerClass).length > 0) {
                    removeAllLayer();
                }
            });

			$layer.find( 'form' ).submit(function() {
				var formData = $( this ).serializeArray(),
					itemCodes = [],
					nfomVariation = '',
					quantity = 0;
				
				var $quantity = $layer.find( config.quantityClass );
				
				if( !validate( formData ) ) {
					var itemCodes = formData.filter(function( v ) {
						if( v.name === 'quantity' ) {
							quantity = v.value;
						}
						return v.name === 'itemCode';
					});
					
					if( $quantity.data( 'originalValue' ) && $quantity.data( 'originalValue' ) !== quantity ) {
						validateQuantity( $quantity );
						return false;
					}
					
					config.params = {
						prodCd: config.prodCd,			// 상품코드
						itemCd: config.variation === 'Y' ? itemCodes[ itemCodes.length - 1 ].value : '001',			// 단품코드
						bsketQty: quantity,			// 주문수량
						categoryId: config.categoryId,		// 카테고리ID
						nfomlVariation: config.variation === 'Y' ? '' : itemCodes[ 0 ].value + ':' + quantity,	// 옵션명, 골라담기의 경우 옵션명:수량
						periDeliYn: config.periodDeliveryYn		// 정기배송여부
					};
					
					config.successCallBack && config.successCallBack( config );
				}
				
				return false;
			});
		}
		
		function onChangeOptionSelect( target ) {
			var $target = $( target ).find( ':selected' ),
				$wrapperQuantityClass = $( config.wrapperQuantityClass ),
				value = $target.val(),
				variation = $target.data( 'variation' ),
				selectData = config.options.filter( function( v, i ) {
					return v.value === value;
				});
			
			if( selectData.length === 0 ) {
				$wrapperQuantityClass.find( 'button, input' ).prop( 'disabled', true );
				return false;
			}
			
			config.variation = variation;
			
			setQuantityAttribute( selectData );
			
			var optionData = selectData[ 0 ].subOptions;
				
			if( optionData && optionData.length > 0 ) {
				$( config.subOptionFieldClass ).removeClass( 'hidden' );
				
				renderOption({
					targetClass : config.subOptionFieldClass,
					productCode : optionData.productCode,
					optionData : optionData
				});
			} else {
				$wrapperQuantityClass.find( 'button, input' ).prop( 'disabled', false );
			}
		}
		
		function onChangeSubOptionSelect( target ) {
			var $target = $( target ).find( ':selected' ),
				value = $target.val(),
				$wrapperQuantityClass = $( config.wrapperQuantityClass );
			
			if( value === '' ) {
				$wrapperQuantityClass.find( 'button, input' ).prop( 'disabled', true );
			} else {
				$wrapperQuantityClass.find( 'button, input' ).prop( 'disabled', false );
			}
		}
		
		function quantityPlus( target ) {
			var $wrapper = $( target ).closest( config.wrapperQuantityClass ),
				$inputField = $wrapper.find( config.quantityClass ),
				max = $inputField.data( 'maxQuantity' ),
				quantity = parseInt( $inputField.val(), 10 ) + 1;
			
			quantity = quantity > max ? max : quantity;
			
			$inputField.val( quantity );
		}
		
		function quantityMinus( target ) {
			var $wrapper = $( target ).closest( config.wrapperQuantityClass ),
				$inputField = $wrapper.find( config.quantityClass ),
				min = $inputField.data( 'minQuantity' ),
				quantity = parseInt( $inputField.val(), 10 ) - 1;
			
			quantity = quantity < min ? min : quantity;
			
			$inputField.val( quantity );
		}
		
		function removeAllLayer() {
			$target.find( config.layerClass ).each(function() {
				$( this ).remove();
				$( '.mask' ).remove();
			});
			
			$target.off( 'click.layer' ).removeClass( 'layer-popup-active' );
		}
		
		function render() {
			$.api.get({
				apiName : 'basketOptions',
				data : {
					'PROD_CD' : config.prodCd,
					'CATEGORYID' : config.categoryId,
					'PERIDELIYN' : config.periodDeliveryYn
				},
				successCallback : function( productData ) {
					config.options = productData;
	
					var layerTemplate = $.render[ config.templateName ]( config );
					
					open( layerTemplate );
				}
			});
		}
		
		function renderOption( obj ) {
			var optionData = obj.optionData;
			
			if( optionData ) { 
				for( var i = 0, len = optionData.length; i < len; i++ ) {
					if( optionData[ i ].subOptions ) {
						optionData[ i ].isSoldOut = false;
					}
				}
			}
			var selectBoxTemplate = $.render[ obj.templateName || 'mobileSelectBox' ]( obj );
			
			$layer.find( obj.targetClass ).html( selectBoxTemplate );
		}
		
		function renderSubOptionSelect( obj ) {
			var subOptionTemplate = $.render.selectBoxForSubOptions( obj );
			
			 $layer.find( config.subOptionFieldClass ).append( subOptionTemplate );
		}
		
		function open( layerTemplate ) {
			var prodCd = config.prodCd + '';
			
			$target.append( layerTemplate )
				.promise()
				.done(function() {
					$layer = $target.find( config.layerClass );
					
					$target.addClass( 'layer-popup-active' );
					
					var options = {
						targetClass : config.optionFieldClass,
						templateName : 'mobileSelectBox',
						productCode : config.prodCd,
						optionData : config.options
					};

					$( config.optionFieldClass ).removeClass( 'hidden' );
					
					renderOption( options );
					
					setPosition();
					bindEvent();
				});
		}
		
		function close() {
			$layer.remove();
			$( '.mask' ).remove();
			$target.removeClass( 'layer-popup-active' );
		}
		
		function setPosition() {
			var offset = $this.offset(),
				top = offset.top + $this.outerHeight( true ) + config.marginTop;
			
			$layer.css({
				top : top,
				display : 'block'
			});
		}
		
		function reset() {
			$( config.subOptionFieldClass ).addClass( 'hidden' ).html( '' );
			$( config.quantityClass )
				.attr( 'name', 'quantity' )
				.data({
					'minQuantity': config.minQuantity,
					'maxQuantity' : config.maxQuantity
				})
				.val( config.minQuantity );
			
			$( config.wrapperQuantityClass ).find( 'button, input' ).prop( 'disabled', true );
		}
		
		function setQuantityAttribute( data ) {
			var $quantity = $layer.find( config.quantityClass ),
				minQuantity = data[ 0 ].minQuantity,
				maxQuantity = data[ 0 ].maxQuantity,
				value = data[ 0 ].value;
			
			$quantity
				.data({
					'minQuantity': minQuantity,
					'maxQuantity' : maxQuantity
				})
				.val( minQuantity );
		}
		
		function validate( formData ) {
			var isError = false;
			
			formData.some(function( data ) {
				if( data.value === '' ) {
					alert( '옵션을 선택해주세요.' );
					isError = true;
				}
				
				return isError;
			});
			
			return isError;
		}
		
		function validateQuantity( $el ) {
			var quantity = $el.val(),
				min = $el.data( 'minQuantity' ),
				max = $el.data( 'maxQuantity' );
			
			if( quantity > max ) {
				alert( fnJsMsg('주문수량은 {0}개 이상 {1}이하 가능합니다.', min, max ) );
				$el.data( 'originalValue', quantity ).val( max );
				
			} else if( quantity < min ) {
				alert( fnJsMsg('주문수량은 {0}개 이상 {1}이하 가능합니다.', min, max ) );
				$el.data( 'originalValue', quantity ).val( min );
				
			} else if( !isOnlyNumber( quantity ) ) {
				alert( '주문수량은 숫자만 입력 가능합니다.' );
				$el.data('originalValue', quantity).val( min );
				
			} else {
				$el.data( 'originalValue', quantity );
			}
		}
		
		function isOnlyNumber(v) {
		    var reg = /^(\s|\d)+$/;
		    return reg.test(v);
		}
		
		return this;
	};
	
})( jQuery, window, document );
(function( $, window, document, undefined ) {
	'use strict';
	
	$.fn.dealOptionLayer = function( options ) {
		var defaults = {
			title : '옵션을 선택하세요',
			layerClass : '.layerpopup-type1',
			btnPlusClass : '.plus',
			btnMinusClass : '.minus',
			btnCloseClass : '.close',
			itemFieldClass : '#itemField',
			optionFieldClass : '#optionField',
			wrapperQuantityClass : '.spinner',
			quantityClass : '[name="quantity"]',
			subOptionFieldClass : '#subOptionField',
			target : 'body',
			templateName : 'mobileBasketOptionForDefault',
			marginTop : 4,
			periodDeliveryYn : 'N'
		},
		$this = this,
		config = $.extend( {}, defaults, options || {});
		
		var $target = $( config.target ),
			$layer = null;
		
		(function init() {
			config = $.extend( config, $this.data() );
			
			config.mstProdCd = config.prodCd;
			
			removeAllLayer();
			render();
		})();
		
		//bind event
		function bindEvent() {
			$layer.on( 'click', config.btnCloseClass, function() {
				close();
				
				return false;
			}).on( 'click', config.btnPlusClass, function() {
				quantityPlus( this );
				
				return false;
			}).on( 'click', config.btnMinusClass, function() {
				quantityMinus( this );
				
				return false;
			}).on( 'change', config.quantityClass, function() {
				validateQuantity( $( this ) );
				
			}).on( 'change', config.itemFieldClass + ' select', function() {
				resetAll();
				onChangeItemSelect( this );
				
			}).on( 'change', config.optionFieldClass + ' select', function() {
				reset();
				onChangeOptionSelect( this );
				
			}).on( 'change', config.subOptionFieldClass + ' select', function() {
				onChangeSubOptionSelect( this );
			});

            $target.on('click', function (e) {
                var $this = $(e.target);

                if ($this.closest(config.layerClass).length === 0) {
                    removeAllLayer();
                }
            });

            $('div.mask').on('click', function () {
				if ($(config.layerClass).length > 0) {
                    removeAllLayer();
				}
            });
			
			$layer.find( 'form' ).submit(function() {
				var formData = $( this ).serializeArray(),
					itemCodes = [],
					quantity = 0,
					itemCode = '001',
					nfomlVariation = '';
				
				var $quantity = $layer.find( config.quantityClass );
				
				if( !validate( formData ) ) {
					var itemCodes = formData.filter(function( v ) {
						if( v.name === 'quantity' ) {
							quantity = v.value;
						}
						return v.name === 'itemCode';
					});
					
					if( $quantity.data( 'originalValue' ) && $quantity.data( 'originalValue' ) !== quantity ) {
						validateQuantity( $quantity );
						return false;
					}
					
					if( itemCodes.length > 0 ) {
						if( config.variation === 'Y' ) {
							itemCode = itemCodes[ itemCodes.length - 1 ].value;
							nfomlVariation = '';
						} else {
							itemCode = '001';
							nfomlVariation = itemCodes[ itemCodes.length - 1 ].value + ':' + quantity;
						}
					}
					
					config.params = {
						prodCd: config.prodCd,			// 상품코드
						itemCd: itemCode,
						bsketQty: quantity,			// 주문수량
						categoryId: config.categoryId,		// 카테고리ID
						periDeliYn: config.periodDeliveryYn,		// 정기배송여부
						nfomlVariation: nfomlVariation,	// 옵션명, 골라담기의 경우 옵션명:수량
						mstProdCd : config.mstProdCd
					};
					
					config.successCallBack && config.successCallBack( config );
				}

				return false;
			});
		}
		
		function onChangeItemSelect( target ) {
			var $target = $( target ).find( ':selected' ),
				variation = $target.data( 'variation' ),
				hasOption = $target.data( 'optionYn' ),
				productCode = $target.val(),
				selectData = null;
			
			config.prodCd = productCode;
			
			if( productCode === '' ) {
				return false;
			}
			
			if( hasOption === false ) {
				selectData = config.items.filter( function( v, i ) {
					return v.productCode === productCode;
				});
				setQuantityAttribute( selectData );
				$( config.wrapperQuantityClass ).find( 'button, input' ).prop( 'disabled', false );
				
				return false;
			}
			
			$.api.get({
				apiName : 'basketOptions',
				data : {
					'PROD_CD' : productCode,
					'CATEGORYID' : config.categoryId, 
					'PERIDELIYN' : 'N'
				},
				successCallback : function( productData ) {
					config.options = productData; 
					
					var prodCd = config.prodCd + '';
					
					renderOption({
						targetClass : config.optionFieldClass,
						productCode : productCode,
						optionData : config.options
					});
					

					$( config.optionFieldClass ).removeClass( 'hidden' );
				}
			});
		}
		
		function onChangeOptionSelect( target ) {
			var $target = $( target ).find( ':selected' ),
				value = $target.val(),
				$wrapperQuantityClass = $( config.wrapperQuantityClass ),
				variation = $target.data( 'variation' ),
				selectData = config.options.filter( function( v, i ) {
					return v.value === value;
				});
			
			config.variation = variation;
			
			if( selectData.length === 0 ) {
				$wrapperQuantityClass.find( 'button, input' ).prop( 'disabled', true );
				return false;
			}
			
			setQuantityAttribute( selectData );
			
			var optionData = selectData[ 0 ].subOptions;
			
			if( optionData ) {
				$( config.subOptionFieldClass ).removeClass( 'hidden' );
				
				renderOption({
					targetClass : config.subOptionFieldClass,
					productCode : optionData.productCode,
					optionData : optionData
				});
			} else {
				$wrapperQuantityClass.find( 'button, input' ).prop( 'disabled', false );
			}
		}
		
		function onChangeSubOptionSelect( target ) {
			var $target = $( target ),
				value = $target.val(),
				$wrapperQuantityClass = $( config.wrapperQuantityClass );
			
			if( value === '' ) {
				$wrapperQuantityClass.find( 'button, input' ).prop( 'disabled', true );
			} else {
				$wrapperQuantityClass.find( 'button, input' ).prop( 'disabled', false );
			}
		}
		
		function quantityPlus( target ) {
			var $wrapper = $( target ).closest( config.wrapperQuantityClass ),
				$inputField = $wrapper.find( config.quantityClass ),
				max = $inputField.data( 'maxQuantity' ),
				quantity = parseInt( $inputField.val(), 10 ) + 1;
			
			quantity = quantity > max ? max : quantity;
			
			$inputField.val( quantity );
		}
		
		function quantityMinus( target ) {
			var $wrapper = $( target ).closest( config.wrapperQuantityClass ),
				$inputField = $wrapper.find( config.quantityClass ),
				min = $inputField.data( 'minQuantity' ),
				quantity = parseInt( $inputField.val(), 10 ) - 1;
			
			quantity = quantity < min ? min : quantity;
			
			$inputField.val( quantity );
		}
		
		function removeAllLayer() {
			$target.find( config.layerClass ).each(function() {
				$( this ).remove();
				$( '.mask' ).remove();
			});
			
			$target.off( 'click.layer' ).removeClass( 'layer-popup-active' );
		}
		
		function render() {
			$.api.get({
				apiName : 'dealOptions',
				data : {
					'PROD_CD' : config.prodCd,
					'CATEGORYID' : config.categoryId,
					'PERIDELIYN' : 'N'
				},
				successCallback : function( productData ) {
					config.items = productData; 
	
					var layerTemplate = $.render[ config.templateName ]( config );
					
					open( layerTemplate );
				}
			});
		}
		
		function renderOption( obj ) {
			var optionData = obj.optionData;
			
			if( optionData ) { 
				for( var i = 0, len = optionData.length; i < len; i++ ) {
					if( optionData[ i ].subOptions ) {
						optionData[ i ].isSoldOut = false;
					}
				}
			}
			
			var selectBoxTemplate = $.render[ obj.templateName || 'mobileSelectBox' ]( obj );
			
			$layer.find( obj.targetClass ).html( selectBoxTemplate );
		}
		
		function renderSubOptionSelect( obj ) {
			var subOptionTemplate = $.render.selectBoxForSubOptions( obj );
			
			 $layer.find( config.subOptionFieldClass ).append( subOptionTemplate );
		}
		
		function open( layerTemplate ) {
			$target.append( layerTemplate )
				.promise()
				.done(function() {
					$layer = $target.find( config.layerClass );
					
					$target.addClass( 'layer-popup-active' );
					
					var options = {
						targetClass : config.itemFieldClass,
						templateName : 'mobileSelectBox',
						productCode : config.prodCd,
						optionData : config.items
					};
					
					$( config.itemFieldClass ).removeClass( 'hidden' );
					
					renderOption( options );
					
					setPosition();
					bindEvent();
				});
		}
		
		function close() {
			$layer.remove();
			$( '.mask' ).remove();
			$target.removeClass( 'layer-popup-active' );
		}
		
		function setPosition() {
			var offset = $this.offset(),
				top = offset.top + $this.outerHeight( true ) + config.marginTop;
		
			$layer.css({
				top : top,
				display : 'block'
			});
		}

		function setQuantityAttribute( data ) {
			var $quantity = $layer.find( config.quantityClass ),
				minQuantity = data[ 0 ].minQuantity,
				maxQuantity = data[ 0 ].maxQuantity;
			
			$quantity
				.data({
					'minQuantity': minQuantity,
					'maxQuantity' : maxQuantity
				})
				.val( minQuantity );
		}
		
		function validate( formData ) {
			var isError = false;
			
			formData.some(function( data ) {
				if( data.value === '' ) {
					alert( '옵션을 선택해주세요.' );
					isError = true;
				}
				
				return isError;
			});
			
			return isError;
		}
		
		function validateQuantity( $el ) {
			var quantity = $el.val(),
				min = $el.data( 'minQuantity' ),
				max = $el.data( 'maxQuantity' );
			
			if( quantity > max ) {
				alert( fnJsMsg('주문수량은 {0}개 이상 {1}이하 가능합니다.', min, max ) );
				$el.data( 'originalValue', quantity ).val( max );
				
			} else if( quantity < min ) {
				alert( fnJsMsg('주문수량은 {0}개 이상 {1}이하 가능합니다.', min, max ) );
				$el.data( 'originalValue', quantity ).val( min );
				
			} else if( !isOnlyNumber( quantity ) ) {
				alert( '주문수량은 숫자만 입력 가능합니다.' );
				$el.data('originalValue', quantity).val( min );
				
			} else {
				$el.data( 'originalValue', quantity );
			}
		}
		
		function resetAll() {
			$( config.optionFieldClass ).addClass( 'hidden' ).html( '' );
			$( config.subOptionFieldClass ).addClass( 'hidden' ).html( '' );
			$( config.quantityClass )
				.attr( 'name', 'quantity' )
				.data({
					'minQuantity': 1,
					'maxQuantity' : 1
				})
				.val( 1 );
			$( config.wrapperQuantityClass ).find( 'button, input' ).prop( 'disabled', true );
		}

		function reset() {
			$( config.subOptionFieldClass ).addClass( 'hidden' ).html( '' );
			$( config.quantityClass )
				.attr( 'name', 'quantity' )
				.data({
					'minQuantity': 1,
					'maxQuantity' : 1
				})
				.val( 1 );
			$( config.wrapperQuantityClass ).find( 'button, input' ).prop( 'disabled', true );
		}
		
		function isOnlyNumber(v) {
		    var reg = /^(\s|\d)+$/;
		    return reg.test(v);
		}
		
		return this;
	};
	
})( jQuery, window, document );
(function($, window, document, undefined) {
	'use strict';

	$.fn.pickOptionLayer = function(options) {
		var defaults = {
			title : '옵션을 선택하세요',
			layerClass : '.layerpopup-type1',
			btnPlusClass : '.plus',
			btnMinusClass : '.minus',
			btnCloseClass : '.close',
			wrapperQuantityClass : '.spinner',
			quantityClass : '.quantity',
			target : 'body',
			templateName : 'mobileBasketOptionForPick',
			marginTop : 4,
			periodDeliveryYn : 'N'
		}, 
		$this = this, 
		config = $.extend({}, defaults, options || {});

		var $target = $(config.target), 
			$layer = null;

		(function init() {
			config = $.extend(config, $this.data());

			removeAllLayer();
			render();
		})();

		// bind event
		function bindEvent() {
			$layer.on('click', config.btnCloseClass, function() {
				close();

				return false;
			}).on('click', config.btnPlusClass, function() {
				quantityPlus(this);

				return false;
			}).on('click', config.btnMinusClass, function() {
				quantityMinus(this);

				return false;
			}).on('change', config.quantityClass, function() {
				var $this = $(this), quantity = $this.val(), min = $this
						.data('minQuantity'), max = $this
						.data('maxQuantity');
	
				if (quantity > max) {
					alert(fnJsMsg('주문수량은 {0}개 이상 {1}이하 가능합니다.', min, max));
					$this.val(max);
				}
	
				if (quantity < min) {
					alert(fnJsMsg('주문수량은 {0}개 이상 {1}이하 가능합니다.', min, max));
					$this.val(min);
				}
	
				if (!isOnlyNumber(quantity)) {
					alert('주문수량은 숫자만 입력 가능합니다.');
					$this.val(min);
				}
	
				checkedUnit();
			});

			$target.on('click', function(e) {
				var $this = $(e.target);

				if ($this.closest(config.layerClass).length === 0) {
					removeAllLayer();
				}
			});

            $('div.mask').on('click', function () {
                if ($(config.layerClass).length > 0) {
                    removeAllLayer();
                }
            });

			// TODO
			$layer
				.find('form')
				.submit(function() {
					var formData = $(this).serializeArray(), nformVariation = '';

					setTotalQuantity(formData);

					if (!validate()) {
						for (var i = 0, len = formData.length; i < len; i++) {
							var data = formData[i];

							if (nformVariation === '') {
								nformVariation = data.name + ':' + data.value;
							} else {
								nformVariation = nformVariation + ';' + data.name + ':' + data.value;
							}
						}

						config.params = {
							prodCd : config.prodCd, // 상품코드
							itemCd : '001', // 단품코드
							bsketQty : config.totalQuantity / config.unit, // 주문수량
							categoryId : config.categoryId, // 카테고리ID
							nfomlVariation : nformVariation, // 옵션명,
							periDeliYn : config.periodDeliveryYn
						// 정기배송여부
						};

						config.successCallBack && config.successCallBack( config );
					}
					return false;
				});
		}
		// TODO
		function validate() {
			var isError = false, remain = config.totalQuantity % config.unit;

			if (config.totalQuantity % config.unit !== 0) {
				isError = true;

				alert('상품' + setComma(config.unit - remain) + '개를 더 선택해주세요.');
			}

			return isError;
		}

		function checkedUnit() {
			var $form = $layer.find('form'), $error = $layer.find('.error'), formData = $form.serializeArray();

			setTotalQuantity(formData);

			var remain = config.totalQuantity % config.unit;

			if (config.totalQuantity % config.unit !== 0) {
				$error.removeClass('hidden').html('(상품 <em class="point1">'+ $.utils.comma(config.unit - remain)+ '개</em>를 더 선택해주세요.)');
			} else {
				$error.addClass('hidden');
			}
		}

		function setTotalQuantity(formData) {
			var totalQuantity = 0;

			for (var i = 0, len = formData.length; i < len; i++) {
				var data = formData[i];

				totalQuantity = totalQuantity + parseInt(data.value, 10);
			}

			config.totalQuantity = totalQuantity;
		}

		function quantityPlus(target) {
			var $wrapper = $(target).closest(config.wrapperQuantityClass), 
				$inputField = $wrapper.find(config.quantityClass), 
				max = $inputField.data('maxQuantity'), 
				quantity = parseInt($inputField.val(), 10) + 1;

			quantity = quantity > max ? max : quantity;

			$inputField.val(quantity);

			checkedUnit();
		}

		function quantityMinus(target) {
			var $wrapper = $(target).closest(config.wrapperQuantityClass), 
				$inputField = $wrapper.find(config.quantityClass), 
				min = $inputField.data('minQuantity'), 
				quantity = parseInt($inputField.val(), 10) - 1;

			quantity = quantity < min ? min : quantity;

			$inputField.val(quantity);

			checkedUnit();
		}

		function removeAllLayer() {
			$target.find(config.layerClass).each(function() {
				$(this).remove();
				$('.mask').remove();
			});
			$target.off('click.layer').removeClass('layer-popup-active');
		}

		function render() {
			$.api.get({
				apiName : 'basketOptions',
				data : {
					'PROD_CD' : config.prodCd,
					'CATEGORYID' : config.categoryId,
					'PERIDELIYN' : 'N'
				},
				successCallback : function(productData) {
					config.options = productData;
					config.unit = config.options[0].unit;

					var layerTemplate = $.render[config.templateName](config);

					open(layerTemplate);
				}
			});
		}

		function open(layerTemplate) {
			$target.append(layerTemplate).promise().done(function() {
				$layer = $target.find(config.layerClass);

				$target.addClass('layer-popup-active');

				setPosition();
				bindEvent();
			});
		}

		function close() {
			$layer.remove();
			$('.mask').remove();
			$target.removeClass('layer-popup-active');
		}

		function setPosition() {
			var offset = $this.offset(), top = offset.top + $this.outerHeight(true) + config.marginTop;

			$layer.css({
				top : top,
				display : 'block'
			});
		}

		function isOnlyNumber(v) {
			var reg = /^(\s|\d)+$/;
			return reg.test(v);
		}
		
		return this;
	};

})(jQuery, window, document);
(function ($, window, document, undefined) {
    'use strict';

    $.appInstallLayer = function (options) {
        var defaults = {
                closeListener: function () {},
                excludedAffiliates: {
                    name: ['위메프','위메프'],
                    id: ['01560001','01560002']
                }
            },
            $appLayer = null,
            config = $.extend(true, {}, defaults, options);

        init();

        function hasCookie() {
            return !!$.cookie('appInstallInduction');
        }

        function isApp() {
            return $.utils.config('onlinemallApp');
        }

        function isToysrus() {
            return /toysrus.(android|iphone).shopping/.test(window.navigator.userAgent);
        }

        function isQrcode() {
            return !!(parseInt(config.qrcode, 10) && config.dlink);
        }

        function getQueryParameters(str) {
            return (str || document.location.search)
                .replace(/(^\?)/, '')
                .split("&")
                .map(function (n) {
                    return n = n.split("="), this[n[0].toLowerCase()] = n[1], this
                }.bind({}))[0];
        }

        function notContainExcludedAffiliatesID() {
            var queryParams = getQueryParameters();
            return config.excludedAffiliates.id.indexOf(queryParams.affiliate_id) === -1;
        }

        function init() {
            if (isApp() || isToysrus()) {
                return;
            }

            if (hasCookie() && !isQrcode()) {
                config.closeListener();
                return;
            }

            if(userAgent.indexOf("lottemart-app-shopping-did")==-1) {
	            if (isQrcode()) {
	                // $($.render["appInstallInductionLayer2"](config)).appendTo("body");
	            } else {
	                if (notContainExcludedAffiliatesID()) {
	                    // $($.render["appInstallInductionLayer"]()).appendTo("body");
	                }
	            }
            }

            $appLayer = $(".wrap-appsetlayer");
            $appLayer.on("touchmove scroll", function () {
                return false;
            });
            $appLayer.find(".js-close").on("click", function () {
                appInstallLayerClose("web");
            });
            $appLayer.find(".conts a").on("click", function () {
                appInstallLayerClose("app");
            });
        }

        function bakeCookie() {
            $.cookie('appInstallInduction', 'appInstallInductionClose', {
                expires: 5,
                domain: '.lottemart.com',
                path: '/'
            });
        }

        function appInstallLayerClose(resultType) {
            if (resultType === "app" && !isQrcode()) {
                openAppInstall();

                bakeCookie();
            } else if (resultType === "web" && !isQrcode()) {
                bakeCookie();
            }

            $appLayer.fadeOut(300, function () {
                $(this).remove();
            });

            config.closeListener();
        }

        function openAppInstall() {
            var url;
            if ($.utils.isAndroid()) {
                url = $.utils.config("appMarketAndroid");
            } else if ($.utils.isIOS()) {
                url = $.utils.config("appMarketIOS");
            }
            window.open(url);
        }
    };
})(jQuery, window, document);
;(function( $, window, document, undefined ) {
	'use strict';

	var $body = $('body');
	var enScrollTop = 0;
	var beScrollTop = 0;
	var scrollThreshold = 150;

	$.templates('mobileVideoLayerInner',
	'<div class="wrap-movie-kok-inner" style="top:{{:posTop}}">'+
		'{{if ~isYoutubleUrl(videoUrl)}}' +
			'<iframe id="kokVideo" style="height:{{:videoHeight}}" frameborder="0" allowfullscreen src="{{:videoUrl}}&amp;enablejsapi=1&amp;origin=http:%2F%2Fm.lottemart.com" title="동영상"></iframe>' +
		'{{else}}' +
			'<video id="kokVideo" style="height:{{:videoHeight}}" src="{{:videoUrl}}" controls="controls"></video>' +
		'{{/if}}' +

		'<button name="btnClose" type="button" class="close">닫기</button>' +
	'</div>'
	);

	function movieLayerRemove(tDelay) {
		var $tMovieLayer = $body.find('.wrap-movie-kok-inner');
		var $prodWrap = $('.product-wrap');
		$tMovieLayer.fadeOut(tDelay);
		if ($prodWrap.length > 0) $prodWrap.removeClass('video-layer');
		setTimeout(function() {
			$tMovieLayer.remove();
		}, tDelay + 99);
	}

	$(window).on('scroll', function() {
		enScrollTop = window.scrollY;
		if (Math.abs(enScrollTop - beScrollTop) < scrollThreshold) return false;
		movieLayerRemove(400);
		beScrollTop = enScrollTop;
	});

	var VideoLayer = function($el, options) {
		var $elWrap = $el.closest('.product-wrap');
		var _flagElWrap = ($elWrap.length > 0);

		this.config = {
			videoUrl : $el.data('video'),
			templateId : 'mobileVideoLayerInner',
			posTop : (_flagElWrap) ? $elWrap.offset().top + 'px' : 0,
			videoHeight : (_flagElWrap) ? $elWrap.height() : '20rem'
		};

		$.extend(this.config, options);
		if (_flagElWrap) $elWrap.addClass('video-layer');

		this.init();
		return this;
	};

	VideoLayer.prototype = {
		init: function() {
			this.render();
			this.bindEvent();
		},

		render : function() {
			var layer = $.render[this.config.templateId](this.config);

			this.$layer = $(layer).appendTo($body);
		},

		bindEvent : function() {
			// 닫기
			this.$layer.on('click', '[name=btnClose]', function(){
				movieLayerRemove(400);
			});
		}
	};

	$.fn.videoLayer = function(options) {
		return new VideoLayer($(this), options);
	};
})(jQuery, window, document);
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
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.callAppScheme = factory();
	}
}( window, function() {
	var _callAppScheme = function() {
		var appActionID = "appExecuteFrame";
		var actionFrame = document.createElement("IFRAME");

		actionFrame.id = appActionID;
		actionFrame.name = actionFrame.id;
		actionFrame.width = 0;
		actionFrame.height = 0;
		actionFrame.src = scheme;

		if(isAppVal == true){
			window.location.href = scheme ;
		}
	};
	
	return _callAppScheme;
}));