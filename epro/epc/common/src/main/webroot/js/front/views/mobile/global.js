"use strict";
/**
 * @description 롯데마트몰 global function
 * @version 1.0
 * @date 2016.04.01
 */

var browserAgent = navigator.userAgent;

var dummyFunc = function() {};

var __GuestMember_yn = window._GuestMember_yn || false,
	__GuestMember_type = window._GuestMember_type || false;

/**
 * 장바구니 / 바로구매 / 찜 관련 함수등
 */
var global = {
	// Browser 분류
	Browser: {
		ie : /msie/i.test(browserAgent) || /trident/i.test(browserAgent) || /edge/i.test(browserAgent),
		opera: /mozilla/i.test(browserAgent) && /applewebkit/i.test(browserAgent) && /chrome/i.test(browserAgent) && /safari/i.test(browserAgent) && /opr/i.test(browserAgent),
		safari: /safari/i.test(browserAgent) && /applewebkit/i.test(browserAgent) && !/chrome/i.test(browserAgent),
		chrome: /webkit/i.test(browserAgent)  && /chrome/i.test(browserAgent) && !/edge/i.test(browserAgent),
		firefox: /mozilla/i.test(browserAgent) && /firefox/i.test(browserAgent),
		info: function() {
			var t, m = browserAgent.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || [];
			if (/trident/i.test(m[1])) {
				t = /\brv[ :]+(\d+)/g.exec(browserAgent) || [];
				return {
					name: "IE",
					version: t[1] || ''
				}
			}
			if ( m[1] === "chrome") {
				t = browserAgent.match(/\bOPR\/(\d+)/);
				if (t != null ) {
					return {
						name: 'Opera',
						version: t[1]
					}
				}
			}
			m = m[2] ? [m[1], m[2]]: [navigator.appName, navigator.appVersion, '-?'];
			if ( (t = browserAgent.match(/version\/(\d+)/i)) != null ) {
				m.splice(1, 1, t[1]);
			}
			
			return {
				name: m[0],
				version: m[1]
			};
		},
		version: ""
	},
	familyJoin: function(param) {
		var sid = "MMARTSHOP";
		var family_url = (window._LMMembersAppSSLUrl || $.utils.config('LMMembersAppSSLUrl')) + "/imember/member/join/memberJoin.do";
		var return_url = encodeURIComponent(window.location.href, "UTF-8");

		family_url += ("?sid=" + sid + "&returnurl=" + return_url + "&memberJoinYN=Y");
		window.open(family_url, "family");
		
	},
	autoLogin: function() {
		function isEmpty(str){
		    if(typeof str == "undefined" || str == null || str == "")
		        return true;
		    else
		        return false;
		}
		
		if(!isEmpty($.cookie('renewalToken'))){
			$.removeCookie('renewalToken', {domain: '.lottemart.com', path: '/'});
		}
		
		var tokenName = 'MMARTSHOP'.toLowerCase() + 'Token';
		
		if(location.href.indexOf("ssoGate=Y") <= -1){
			if(!isEmpty($.cookie('autoLogin')) && $.cookie('autoLogin') == 'Y' && isEmpty($.cookie(tokenName))
					&& !isEmpty($.cookie('renewalTkn')) && !isEmpty($.cookie('autoLgnRgDtti'))
					|| !isEmpty($.cookie('autoLogin')) && $.cookie('autoLogin') == 'Y' 
					&& !isEmpty($.cookie('isExpiration')) && $.cookie('isExpiration') == 'Y'
					&& !isEmpty($.cookie('renewalTkn')) && !isEmpty($.cookie('autoLgnRgDtti'))
			){
				var loadingHtml = '<div class="gnb-category" style="left:0;right:0;background-color:transparent"><div class="loading"></div></div>';
				$('body').append(loadingHtml);
				
				lpoint.autoLogin({
					rnwTkn: $.cookie( 'renewalTkn' ),
					autoLgnRgDtti : $.cookie( 'autoLgnRgDtti' ),
					callback: function (response) {
						if (response.rspC === '00') {
							$.cookie('ssoToken', response.ssoTkn, {expires : 90, path: '/', domain: '.lottemart.com'});
							$.cookie(tokenName, response.acesTkn, {expires : 90, path: '/', domain: '.lottemart.com'});
							$.cookie('renewalTkn', response.rnwTkn, {expires : 90, path: '/', domain: '.lottemart.com'});
							$.cookie('isExpiration', "N", {expires : 90, path: '/', domain: '.lottemart.com'});
							
							var _userAgent = navigator.userAgent;
							if(_userAgent.toUpperCase().indexOf("LOTTEMART-APP-SHOPPING-IOS") > -1 || _userAgent.toUpperCase().indexOf("LOTTEMART-APP-SHOPPING-ANDROID") > -1) {
								var loginScheme = 'togetherApp://login/?ssoTkn=' + encodeURIComponent(response.ssoTkn);
								if($.cookie('isAutoLogin')) {
									loginScheme += '&auto=Y';
								}
								
								var appFrame = document.createElement('iframe');
								appFrame.style.visibility = "hidden";
								appFrame.style.display = "none";
								appFrame.src = loginScheme;
	
								document.getElementsByTagName("body")[0].appendChild(appFrame);
							}
							location.href = location.href;
							return false;
						}
						$.removeCookie('ssoToken', {domain: '.lottemart.com', path: '/'});
						$.removeCookie(tokenName, {domain: '.lottemart.com', path: '/'});
						$.removeCookie('renewalTkn', {domain: '.lottemart.com', path: '/'});
						$.removeCookie('autoLgnRgDtti', {domain: '.lottemart.com', path: '/'});
						$.removeCookie('isExpiration', {domain: '.lottemart.com', path: '/'});
						location.href = location.href;
					}
				});
				
			}	
		}
	},
	login: function(sid, returnUrl) {
		var url;
		url = (window._LMAppSSLUrlM || $.utils.config('LMAppSSLUrlM')) + "/mobile/PMWMMEM0001.do?sid=" + sid + "&returnurl=" + encodeURIComponent(returnUrl);
	
		document.location.href=url;
	},
	loginWithCategoryId : function(sid) {
		var returnUrl = location.origin + '/mobile/corners.do';
		if($.cookie && $.cookie('__categoryId')) {
			returnUrl = returnUrl + '?returnCategoryId=' + $.cookie('__categoryId');
		} else if(utils && utils['cookie'] && utils.cookie.get('__categoryId')) {
			returnUrl = returnUrl + '?returnCategoryId=' + utils.cookie.get('__categoryId');
		}

		var returnurl = getReturnUrl();
		document.location.href = (window._LMAppSSLUrlM || $.utils.config('LMAppSSLUrlM')) + "/mobile/PMWMMEM0001.do?sid=" + sid + "&returnurl=" + encodeURIComponent(returnurl);
	},
	isLogin: function(redirectUrl, successFunc, failFunc) {
		var param = {
			redirectUrl: redirectUrl
		};
		var in_url;
		var result = true;
		if (location.protocol != "http:") {
			in_url = (window._LMAppSSLUrl || $.utils.config('LMAppSSLUrl'));
		}
		else {
			in_url = (window._LMAppUrl || $.utils.config('LMAppUrl'));
		}
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
				global.login($.utils.config( 'SID_NM_MMARTMALL' ), redirectUrl );
			}
		}
		return result;
	},
	isMember: function(successFunc, failFunc) {
		var in_url;
		var result = true;
		if (location.protocol != "http:") {
			in_url = (window._LMAppSSLUrlM || $.utils.config('LMAppSSLUrlM'));
		}
		else {
			in_url = (window._LMAppUrlM || $.utils.config('LMAppUrlM'));
		}
		
		$.api.set({
			apiName : 'isMember',
			async : false,
			successCallback : function( data ) {
				result = data.isMember === "Y";
				if (result) {
					if (typeof(successFunc) != "undefined") {
						successFunc();
					}
				}
			}
		});
		
		if (typeof(failFunc) != "undefined" && !result) {
			failFunc();
		}
		return result;
	},
	goHome: function() {
		location.href = "/mobile/main.do";
	},
	goBasket: function() {
		if(__GuestMember_yn == 'true' && __GuestMember_type == "002"){
   			if(confirm("비회원 배송 조회는 주문한 내역만 확인 가능합니다.\n장바구니는 회원가입 후 이용할 수 있습니다.\n회원 가입 후 이용하시겠습니까?")){
   				goLogout();
   				global.familyJoin();
   				return;
			}else {
				return;
			}
   		}
		
		var url = (window._LMAppUrlM || $.utils.config('LMAppUrlM')) + "/mobile/mypage/PMWMMAR0003.do";
		if ( global.isLogin( location.href ) ) {
			$.api.get({
    			apiName : 'defaultDeliInfo',
    			successCallback : function( resData ) {
    				if( resData.defaultMemberAddrYn === 'Y' ) {
    					location.href = url;
    				}  else {
    					alert( resData.message );
    					location.href = $.utils.config( 'LMAppUrlM' ) + '/mobile/popup/selectMemberDeliveryForm.do';
    				}	
    			}
    		});
		}
	},
	addBasket: function(params, successFunc, failFunc) {
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
				// facebook dynamic remarketing 스크립트변경 2019-02-07
            	fbq('track', 'AddToCart', {
                	content_ids: data.basketItem.prodCd,
                	content_type: 'product'
                });
			}
			
			if (typeof(successFunc) != "undefined" && $.isFunction(successFunc)) {
				successFunc(data);

				var _prodTitle = $( '.basket' ).filter( '[data-prod-cd="' + data.basketItem.prodCd[0] + '"]' ).data('prod-title');

				ga('send', 'event', {
					eventAction: '장바구니 담기',
					eventCategory: 'M | Common | LotteMartMall | ' + location.pathname,
					eventLabel : 'CIP | PID | ' + _prodTitle
				});
				var checkedItems = $.cookie('checkedItemTypeA');	// 일반장바구니 쿠키
				checkedItems = (typeof checkedItems =='undefined')?"":checkedItems+",";
				$.cookie('checkedItemTypeA', checkedItems + data.basketItem.bsketNo ,{path:"/",domain:'lottemart.com'});			
			}
		};
		
		if(__GuestMember_yn == 'true' && __GuestMember_type == "002"){
   			if(confirm("비회원 배송 조회는 주문한 내역만 확인 가능합니다.\n장바구니는 회원가입 후 이용할 수 있습니다.\n회원 가입 후 이용하시겠습니까?")){
   				goLogout();
   				global.familyJoin();
   				return;
			}else {
				return;
			}
   		}
		
		var failCallback = function(xhr, status, error) {
			if (xhr) {
				var data = $.parseJSON(xhr.responseText);
				if (data && data.message) {
					if( msg_basket_errors_no_shipping === data.message ) {
						alert( '롯데마트몰의 상품은 고객님의 가까운\n롯데마트 매장에서 배송됩니다.\n상품을 받으실 배송지를 먼저 등록하신 후\n이용해 주세요.' );
						location.href = $.utils.config( 'LMAppUrlM' ) + '/mobile/popup/selectMemberDeliveryForm.do';
					}else if(data.error == "adult"){
						//	alert(data.message);
						 goProductDetailMobile(
			                        params.categoryId,
			                        params.prodCd,
			                        'N',
			                        '',
			                        '',
			                        ''
			                    );
					} else {							
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
		
		global.isLogin(document.location.href, function() {
			$.api.set({
				apiName : 'basketAdd',
				data : dataParams,
				successCallback : successCallback,
				errorCallback : failCallback
			});
			// 장바구니 추가 시 wiselog 로깅 스크립트 호출
			try {
				if (typeof(n_click_logging) != "undefined") {
					$.each(items, function(kk, kObj) {
						n_click_logging((window._LMAppUrlM || $.utils.config('LMAppUrlM')) + '/basket/pickup.do?ProdCd=' + kObj.prodCd);
					});
				}
			}
			catch ( e ) {
				//
			}
		});
	},
	addDirectBasket: function(params, failFunc) {
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
					if( msg_basket_errors_no_shipping === data.message ) {
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
		
		global.isLogin(null, function() {
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

						$('#tForm').attr('action',(window._LMAppUrlM || $.utils.config('LMAppUrlM'))+'/mobile/basket/insertPreOrder.do').submit();
					}//if
				},
				errorCallback : failCallback
			});
		});
	},
	addWish: function(wishItems, successFunc, failFunc) {
		var successCallback = function(data) {
			if(window.fbq){
				fbq('track', 'AddToWishlist');
			}
			if (data) {
				if (typeof(RNB) != "undefined") {
					RNB.wish.load();
				}
			}
			
			if (typeof(successFunc) != "undefined" && $.isFunction(successFunc)) {
				successFunc(data);
			}
		};
		
		var failCallback = function(xhr, status, error) {
			if (xhr) {
				var data = $.parseJSON(xhr.responseText);
				if (data && data.message) {
					alert(data.message);
					if ( data.redirectUrl != null && data.redirectUrl.trim().length > 0 ) {
						location.href = redirectUrl;
						return;
					}
				}
			}
		};
		
		if (typeof(failFunc) != "undefined" && $.isFunction(failFunc)) {
			failCallback = failFunc;
		}
		
		var validFailFunc = function(msg) {
			var validErrorXhr = {
				responseText: "{\"message\":\"" + msg + "\"}",
				status: "400"
			};
			failCallback(validErrorXhr, "validation error");
		};
		var defaultData = {
			categoryId: null,
			prodCd: null,
			forgnDelyplYn: "N"
		};
		
		var params = {
			categoryIds: [],
			prodCds: [],
			forgnDelyplYns: []
		};
		
		var validItem = function(item) {
			if (item.categoryId == null || $.trim(item.categoryId) === "" ) {
				// 카테고리 validation error
				validFailFunc("카테고리가 확인되지 않아 찜하기가 취소 되었습니다.");
				return false;
			}

			if (item.prodCd == null || $.trim(item.prodCd) === "" ) {
				// 상품코드 validation error
				validFailFunc("선택한 상품이 없어 찜하기가 취소 되었습니다.");
				return false;
			}
			
			return true;
		};
		if ($.isArray(wishItems)) {
			var item = null;
			var valid = true;
			console.log("wishItems = ", wishItems);
			$.each(wishItems, function(i, o) {
				item = $.extend({}, defaultData, o);
				if (!validItem(item)) {
					return (valid = false);
				}
				params.categoryIds.push(item.categoryId);
				params.prodCds.push(item.prodCd);
				params.forgnDelyplYns.push(item.forgnDelyplYn);
			});
			if (!valid) {
				return;
			}
		}
		else {
			var item = $.extend({}, defaultData, wishItems);
			if (!validItem(item)) {
				return;
			}

			params.categoryIds.push(item.categoryId);
			params.prodCds.push(item.prodCd);
			params.forgnDelyplYns.push(item.forgnDelyplYn);
		}
		
		global.isLogin(null, function() {
			$.api.set({
				apiName: 'wishAdds',
				traditional : true,
				data : params,
				successCallback : successCallback,
				errorCallback : failCallback
			});
		});
	},
	deleteWish: function(wishItems, successFunc, failFunc) {
		var successCallback = function(data) {
			if (typeof(RNB) != "undefined") {
				RNB.wish.load();
			}
			
			if (typeof(successFunc) != "undefined" && $.isFunction(successFunc)) {
				successFunc(data);
			}
		};
		
		var failCallback = function(xhr, status, error) {
			if (xhr) {
				var data = $.parseJSON(xhr.responseText);
				if (data && data.message) {
					alert(data.message);
					if ( data.redirectUrl != null && data.redirectUrl.trim().length > 0 ) {
						location.href = redirectUrl;
						return;
					}
				}
			}
		};
		
		if (typeof(failFunc) != "undefined" && $.isFunction(failFunc)) {
			failCallback = failFunc;
		}
		
		var validFailFunc = function(msg) {
			var validErrorXhr = {
				responseText: "{\"message\":\"" + msg + "\"}",
				status: "400"
			};
			failCallback(validErrorXhr, "validation error");
		};
		
		var defaultData = {
				prodCd: null
			};
		
		var params = {
			prodCds: []
		};
		
		var validItem = function(item) {
			if (item.prodCd == null || $.trim(item.prodCd) === "" ) {
				// 상품코드 validation error
				validFailFunc("선택한 상품이 없어 찜하기가 취소 되었습니다.");
				return false;
			}
			
			return true;
		};
		
		if ($.isArray(wishItems)) {
			var item = null;
			var valid = true;
			
			$.each(wishItems, function(i, o) {
				item = $.extend({}, defaultData, o);

				if (!validItem(item)) {
					return (valid = false);
				}
				params.prodCds.push(item.prodCd);
			});
			if (!valid) {
				return;
			}
		}
		else {
			var item = $.extend({}, defaultData, wishItems);

			if (!validItem(item)) {
				return;
			}

			params.prodCds.push(item.prodCd);
		}
		
		global.isLogin(null, function() {
			$.api.set({
				apiName : 'wishDelete',
				traditional : true,
				data : params,
				successCallback : successCallback,
				failCallback : failCallback
			});
		});
	},
	addHistory: function(data, successFunc, failFunc) {
		var defaultData = {
			categoryId: null,
			contensNo: null,
			prodCd: null,
			histTypeCd: null
		};
		data = $.extend({}, defaultData, data);
		
		var successCallback = function(data) {
			if (typeof(RNB) != "undefined") {
				RNB.history.load();
			}
			
			if (typeof(successFunc) != "undefined" && $.isFunction(successFunc)) {
				successFunc(data);;
			}
		};
		
		if (typeof(failFunc) === "undefined" || !$.isFunction(failFunc)) {
			failFunc = function(xhr, status, error) {
				// ignore
			};
		}
		
		$.api.set({
			apiName : 'myHistoryAdd',
			data : data,
			success : successCallback,
			errorCallback : failFunc
		});
	},
	empUpdate: function() {
		/* 임직원 등록 페이지 팝업 */
		var noMember = "롯데그룹 임직원 인증은 L.POINT통합회원만 가능 합니다.\nL.POINT통합회원 계정으로 로그인/회원가입해주세요.";
		var openUrl = "";
		
		if(location.href.indexOf("ms.") > -1){		
			openUrl = "https://ms.lottemart.com/member/GroupEmpUpdate.do?STA=MO";
		}else{
			openUrl = "https://m.lottemart.com/member/GroupEmpUpdate.do?STA=MO";
		}
		
		$.ajax({
			url: '/member/GroupEmpUpChk.do',
			data: { STA : "MO" },
			method: 'GET',
			success : function(data) {
				var resCode = data.resultCode;
				if(resCode == '01'){
					alert(data.resultMsg);
					global.login("MMARTSHOP", location.href);
				}else if(resCode == '02' || resCode == '03' || resCode == '04'){
					alert(data.resultMsg);
				}else if(resCode == '10'){
					//window.open(openUrl,'임직원 등록','width=620, height=820, toolbar=no, menubar=no, scrollbars=no, resizable=yes');
					location.href = openUrl;
				}else{
					alert(noMember);
					global.login("MMARTSHOP", location.href);
				}
			}
		});
	},
	
	corpUpdate: function(select) {
		/* 기업회원 등록 페이지 팝업 */
		var noMember = "기업회원 인증은 L.POINT통합회원만 가능 합니다.\nL.POINT통합회원 계정으로 로그인/회원가입해주세요.";
		var openUrl = "";
		
		if(location.href.indexOf("ms.") > -1){		
			openUrl = "https://ms.lottemart.com/member/CorpUpdate.do?SELECT="+select;
		}else{
			openUrl = "https://m.lottemart.com/member/CorpUpdate.do?SELECT="+select;
		}
		
		$.ajax({
			url: '/member/CorpUpChk.do',
			data: { STA : "MO" },
			method: 'GET',
			success : function(data) {
				var resCode = data.resultCode;
				if(resCode == '01'){
					alert(data.resultMsg);
					global.login("MMARTSHOP", location.href);
				}else if(resCode == '02' || resCode == '03' || resCode == '04'){
					alert(data.resultMsg);
				}else if(resCode == '10'){
					location.href = openUrl;
				}else{
					alert(noMember);
					global.login("MMARTSHOP", location.href);
				}
			}
		});
	},
	isDeliRegistYn: function() {
		$.api.get({
			apiName : 'defaultDeliInfo',
			successCallback : function( resData ) {
				if( resData.defaultMemberAddrYn === 'N' ) {
					alert( resData.message );
					location.href = $.utils.config( 'LMAppUrlM' ) + '/mobile/popup/selectMemberDeliveryForm.do';
				}	
			}
		});
	},
	goPeriBasket: function() {
		/* 정기배송 장바구니로 이동 */
		if(__GuestMember_yn == 'true' && __GuestMember_type == "002"){
   			if(confirm("비회원 배송 조회는 주문한 내역만 확인 가능합니다.\n장바구니는 회원가입 후 이용할 수 있습니다.\n회원 가입 후 이용하시겠습니까?")){
   				goLogout();
   				global.familyJoin();
   				return;
			}else {
				return;
			}
   		}
		
		var url = (window._LMAppUrlM || $.utils.config('LMAppUrlM')) + "/mobile/mypage/PMWMMAR0003.do?basketType=B";
		if ( global.isLogin( location.href ) ) {
			$.api.get({
    			apiName : 'defaultDeliInfo',
    			successCallback : function( resData ) {
    				if( resData.defaultMemberAddrYn === 'Y' ) {
    					location.href = url;
    				}  else {
    					alert( resData.message );
    					location.href = $.utils.config( 'LMAppUrlM' ) + '/mobile/popup/selectMemberDeliveryForm.do';
    				}	
    			}
    		});
		}
	},
};

global.Browser.version = global.Browser.info().version;

try {	
	if ($.browser == null || $.browser.msie) {
		$.browser = {
				msie: global.Browser.ie,
				version: global.Browser.version
		};
	}
} catch (e) {}

/**
 * event의 preventDefault(), stopPropagation() 호출
 */
var eventPropagationWrapper = function(e, func) {
	if (e && e.preventDefault != null && typeof(e.preventDefault) === "function") {
		e.preventDefault();
	}
	func();
	if (e && e.stopPropagation != null && typeof(e.stopPropagation) === "function") {
		e.stopPropagation();
	}
};

/**
 * 숫자 및 문자를 해당자릿수만큼 채움
 * @param val - 값
 * @param len - 자릿수
 * @param z - 채워질 문자
 */
var zeroFill = function(val, len, z) {
	z = z || '0';
	val = val + '';
	return val.length >= len ? val : new Array(len - val.length + 1).join(z) + val;
};

