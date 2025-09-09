/**
 * @description 롯데마트몰 global function
 * @version 1.0
 * @date 2016.04.01
 */

/**
 * 전역 function 모음
 */
var browserAgent = navigator.userAgent;

var dummyFunc = function() {};

var _isNoMember = window.isNoMember || false,
	_isNoMemLoginType = window.isNoMemLoginType || false;

//고객기본배송지가 없습니다.
/**
 * GNB 영역 관련 함수들
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
		var return_url = encodeURIComponent(window.location.href, "UTF-8");
		var url = "/imember/member/join/memberJoin.do?sid=" + $.utils.config( 'SID_NM_MARTMALL' ) + (!!param ? "&" + param : "") + "&returnurl=" + return_url + "&memberJoinYN=Y";
		if ( location.protocol === "http:" ) {
			url = $.utils.config( 'LMMembersAppUrl' ) + url;
		}
		else {
			url = $.utils.config( 'LMMembersAppSSLUrl' ) + url;
		}

		var popWin = window.open(url, "_blank");
		popWin.focus();
	},
	login: function(sid, returnUrl) {
		var url = "";
		var openerCheck = "";
		
		try {
			openerCheck = opener && opener.location.href;
		} catch ( e ) {}
		
		if ( $.utils.config( 'LMLocalDomain' ) == "true" ) {
			url = $.utils.config( 'LMMembersAppUrl' ) + "/imember/login/login.do?sid=" + sid + "&returnurl=" + encodeURIComponent(returnUrl) + "&mode=DEV";
			// 팝업여부 확인
			
			if(opener && openerCheck.indexOf('lottemart') > -1) {
				url = $.utils.config( 'LMMembersAppUrl' ) +"/imember/login/login.do?sid=" + sid + "&returnurl="+$.utils.config( 'LMMembersAppUrl' ) +"/index.do"+ "&mode=DEV";
				opener.location.href = url;
				self.close();
				return; 
			}

		} else {
			url = $.utils.config( 'LMMembersAppSSLUrl' ) + "/imember/login/login.do?sid=" + sid + "&returnurl=" + encodeURIComponent(returnUrl);
			// 팝업여부 확인  
			if(opener && openerCheck.indexOf('lottemart') > -1) {
				url = $.utils.config( 'LMMembersAppUrl' ) +"/imember/login/login.do?sid=" + sid + "&returnurl="+$.utils.config( 'LMMembersAppUrl' ) +"/index.do";
				opener.location.href = url;
				self.close();
				return; 
			}

		}
		
		try {
			$.removeCookie("onl_cno", {path: "/", domain: ".lottemart.com"});
			$.removeCookie("cust_id", {path: "/", domain: ".lottemart.com"});
		} catch ( e ) {}
		
		location.href = url;
	},
	logout: function(returnUrl, linkUrl) {
		var url;
		if ( location.protocol === "http:" ) {
			url = $.utils.config( 'LMMembersAppUrl' );
		}
		else {
			url = $.utils.config( 'LMMembersAppSSLUrl' );
		}

		if( $.utils.config( 'LMFamilyLoginYn' ) == "Y" ) {
			url += (!linkUrl ? "/imember/login/ssoLogoutPop.do" : linkUrl) + "?sid="+ $.utils.config( 'SID_NM_MARTMALL' ) +"&kind=f&SITELOC=AA005&returnurl=" + encodeURIComponent(returnUrl) + ( $.utils.config( 'LMLocalDomain' ) === "true" ? "&mode=DEV" : "") +"&accessToken=" + $.cookie($.utils.config( 'SID_NM_MARTMALL' ).toLowerCase() + 'Token');
		} else {
			url += (!linkUrl ? "/imember/login/ssoLogoutPop.do" : linkUrl) + "?sid="+ $.utils.config( 'SID_NM_MARTMALL' ) +"&kind=m&SITELOC=AA005&returnurl=" + encodeURIComponent(returnUrl)+ "&mart=Y" + ( $.utils.config( 'LMLocalDomain' ) === "true" ? "&mode=DEV" : "");
		}

		// https에서 로그아웃시 페이지 이동
		if (location.protocol === "https:") {
			location.href = url;
		}
		else {
			$("<iframe>").attr({"src":url}).css({"width":"0px", "height":"0px"}).appendTo("body");
		}
	},
	noMemberLogin: function(returnurl) {
		var url = "/login/noMemLoginSeed.do?returnURL=" + encodeURIComponent(returnurl) + "&SITELOC=AA005";
		var popup = window.open(url, "ssoLoginPop", "toolbar=no,location=no,directories=no,status=no,scrollbars=yes,resizable=yes, menubar=no,width=800px,height=900px,dependent=yes");

		if (popup != null) {
			popup.focus();
		}
	},
	noMemberOrder: function(returnurl) {
		var url = "/login/noMemLoginSeed.do?type=order&returnURL=" + encodeURIComponent(returnurl);
		var popup = window.open(url, "ssoLoginPop", "toolbar=no,location=no,directories=no,status=no,scrollbars=yes,resizable=yes, menubar=no,width=800px,height=700px,dependent=yes");

		if (popup != null) {
			popup.focus();
		}
	},
	b2eLogout: function(returnUrl, linkUrl) {
		var url = $.utils.config( 'LMMembersAppSSLUrl' ) + (!linkUrl ? "/login/ssoLogoutPop.do" : linkUrl) + "?returnURL=" + encodeURIComponent(returnUrl) + "&SITELOC=AA005";
		location.href =  url;
	},
	integrationChangeUser: function(param, returnUrl) {
		var url;
		var popup;

		if ( location.protocol === "http:" ) {
			url = $.utils.config( 'LMMembersAppUrl' );
		}
		else {
			url = $.utils.config( 'LMMembersAppSSLUrl' );
		}

		window.open(url + "/imember/member/form.do?sid=" + $.utils.config( 'SID_NM_MARTMALL' ) +"&returnurl="+encodeURIComponent(returnUrl)+ (!!param ? "&" + param : ""), "MartMember","");
	},
	familyChangeUser: function(param) {
		lpoint.changeInfo();
	},
	isLogin: function(redirectUrl, successFunc, failFunc) {
		var in_url,
			result = true;

		if (location.protocol != "http:") {
			in_url = $.utils.config( 'LMAppSSLUrl' );
		} else {
			in_url = $.utils.config( 'LMAppUrl' );
		}

		$.api.set({
			apiName : 'isLogin',
			async : false,
			data : {
				redirectUrl: redirectUrl
			},
			successCallback : function( data ) {
				result = data.isLogin === "Y";

				if (result) {
					successFunc && successFunc();
				}
			}
		});

		if (!result) {
			if (typeof failFunc !== "undefined") {
				failFunc();
			}
			else {
				global.login( $.utils.config( 'SID_NM_MARTMALL' ), redirectUrl);
			}
		}
		return result;
	},
	isMember: function(successFunc, failFunc) {
		var in_url;
		var result = true;
		if (location.protocol != "http:") {
			in_url = $.utils.config( 'LMAppSSLUrl' );
		}
		else {
			in_url = $.utils.config( 'LMAppUrl' );
		}

		$.api.set({
			apiName : 'isMember',
			async : false,
			successCallback : function( data ) {
				result = data.isMember === "Y";

				successFunc && successFunc();
			}
		});

		if (typeof failFunc !== "undefined" && !result) {
			failFunc();
		}
		return result;
	},
	goHome: function() {
		if(_isNoMember == "true" && _isNoMemLoginType == "002"){
				alert("비회원 주문을 완료하여 로그아웃 합니다. \n롯데마트몰을 이용해 주셔서 감사합니다.");
				global.b2eLogout($.utils.config( 'LMAppUrl' ) + "/index.do?SITELOC=AB001");
		}else{
			location.href = "/index.do?SITELOC=AB001";
		}
	},
	goBasket: function(basketType) {
		// 장바구니 탭 active를 위해 파라미터 수정. msshin
		// basketType : B - 정기배송
		//					 : C - 해외배송
		//					 : 그외 - 일반배송

		//비회원-주문배송조회로그인 경우 진입불가
		if(_isNoMember == "true" && _isNoMemLoginType == "002"){
			if(confirm("비회원 배송 조회는 주문한 내역만 확인 가능합니다.\n장바구니는 회원가입 후 이용할 수 있습니다.\n회원 가입 후 이용하시겠습니까?")){
				global.b2eLogout(_ReqURL);
				global.familyJoin('SITELOC=JA005');
				return;
			}else {
				return;

			}
		}
		var url = $.utils.config( 'LMAppUrl' ) + "/basket/basketList.do";
		if (basketType) {
			url += "?basketType=" + basketType
		}
		if (url.indexOf("?") >= 0) {
			url += "&";
		}
		else {
			url += "?";
		}
		url += "SITELOC=AA007";

		if( global.isLogin( url ) ) {
    		$.api.get({
    			apiName : 'defaultDeliInfo',
    			successCallback : function( resData ) {
    				if( resData.defaultMemberAddrYn === 'Y' ) {
    					location.href = url;
    				}  else {
    					alert( resData.message );
    					RNB && RNB.delivery.openMyDelivery();
    				}
    			}
    		});
    	}
	},
	goMyOrder: function() {
		var url = $.utils.config( 'LMAppUrl' ) + "/mymart/selectMyOrderList.do?SITELOC=AA006";
		if ( $.utils.config( 'Member_yn' ) === "true" || $.utils.config( 'GuestMember_yn' ) ==="true") {
			location.href = url;
		}
		else {
			global.login( $.utils.config( 'SID_NM_MARTMALL' ), url);
		}
	},
	goWish: function() {
		var url = $.utils.config( 'LMAppUrl' ) + "/mymart/selectWishList.do?SITELOC=AE005";
		if ( $.utils.config( 'Member_yn' ) === "true" ) {
			location.href = url;
		}
		else {
			global.login( $.utils.config( 'SID_NM_MARTMALL' ), url);
		}
	},

	// 장바구니 추가
	addBasket: function(params, successFunc, failFunc) {
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

		if( typeof params === 'string' ) {
			params = JSON.parse( params );
		}

		var successCallback = function(data) {
            if(window.fbq){
                // facebook dynamic remarketing 스크립트변경 2019-02-07
            	fbq('track', 'AddToCart', {
                	content_ids: data.basketItem.prodCd,
                	content_type: 'product'
                });
            }
			try {
				if (data) {
					if (typeof RNB != 'undefined') {
						RNB.basket.load();
					}
				}

				if(window.ga !== undefined) {
					var _prodTitle = $( '.basket, .btn-basket' ).filter( '[data-prod-cd="' + params.prodCd + '"]' ).data('prod-title');

					window.ga( 'send', 'event', {
						eventAction: '장바구니 담기',
						eventCategory: 'P | Common | LotteMartMall | ' + $.utils.config('ReqURL'),
						eventLabel : 'CIP | PID | ' + ( _prodTitle !== undefined ? _prodTitle : $.utils.config('pageTitle') )
					});
				}

				GNB_BasketCount();
				var checkedItems = $.cookie('checkedItemTypeA');	// 일반장바구니 쿠키
				checkedItems = (typeof checkedItems =='undefined')?"":checkedItems+",";
				$.cookie('checkedItemTypeA', checkedItems + data.basketItem.bsketNo ,{path:"/",domain:'lottemart.com'})				
			}
			catch ( e ) {
				//
			}
			successFunc && successFunc(data);
		};

		if(_isNoMember == "true" && _isNoMemLoginType == "002"){
			if(confirm("비회원 배송 조회는 주문한 내역만 확인 가능합니다.\n장바구니는 회원가입 후 이용할 수 있습니다.\n회원 가입 후 이용하시겠습니까?")){
				global.b2eLogout(_ReqURL);
				global.familyJoin('SITELOC=JA005');
				return;
			}else {
				return;
			}
		}

		var failCallback = function(xhr, status, error) {
			if (xhr) {
				var data = $.parseJSON(xhr.responseText);
				if (data && data.message) {
					if(data.error == "adult") {
						//19세 이하 접근 오류시 alert 노출 안하고 성인인증 페이지로 이동
					} else {
						if( msg_basket_errors_no_shipping === data.message ) {
							alert( '롯데마트몰의 상품은 고객님의 가까운\n롯데마트 매장에서 배송됩니다.\n상품을 받으실 배송지를 먼저 등록하신 후\n이용해 주세요.' );
							RNB && RNB.delivery.openMyDelivery();
						} else {
							alert(data.message);
						}
					}
					if ( data.returnURL != null && data.returnURL.trim().length > 0 ) {
						location.href = returnURL;
						return;
					}
					else if (data.error == "adult") {
						setAdultReturnUrl();
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

			if( $.utils.config( 'Member_yn') === "false" && $.utils.config( 'GuestMember_yn' ) ==="true" && basketItem.periDeliYn  == "Y" ) {
				validFailFunc("L.POINT 통합회원 또는 롯데마트 회원만 정기배송 신청이 가능합니다. 회원 가입 후, 신청해 주세요");
				return false;
			}

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

			try {
				// 장바구니 추가 시 wiselog 로깅 스크립트 호출
				if (typeof(n_click_logging) != "undefined") {
					$.each(items, function(kk, kObj) {
						var logUrl = $.utils.config( 'LMAppUrl' ) + "/basket/pickup.do?ProdCd=" + kObj.prodCd;
						n_click_logging(logUrl);
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
							RNB && RNB.delivery.openMyDelivery();
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
						} else { /* 찜바구니 에러로 추가 2016.09.24 김상욱 */
							var basketInfo = null;
							$.each(items, function(i, item) {
								basketInfo = findItem(param.basketList, item);
								if (basketInfo != null) {
									//alert('basketInfo.BSKET_NO ' + basketInfo.BSKET_NO);
									$('#divTemp').append(genDomInput("bsketNo", basketInfo.BSKET_NO));
								}
							});
						}
						//$('#divTemp').append(genDomInput("bsketNo", param.bsketNo));
						$('#divTemp').append(genDomInput("basketType", param.basketType));
						$('#divTemp').append(genDomInput("deliTypeCd", param.deliTypeCd));
						$('#divTemp').append(genDomInput("bsketDivnCd", "02"));

						$('#tForm').attr('action', $.utils.config( 'LMAppSSLUrl' ) +'/basket/insertPreOrder.do').submit();
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
			successFunc && successFunc(data);
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
				apiName : 'wishAdds',
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
			successFunc && successFunc(data);
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
			successCallback : successCallback,
			errorCallback : failFunc
		});
	},
	talkCounsel: function() {
		if (confirm("롯데마트몰 톡을 시작하시겠습니까?")) {
			var newWindow = window.open("/mymart/popup/selectMyTalkCommunication.do", "_TALK_COUNSEL", "width=900; height=720; scrollbars=yes; resizable=yes");
		}
	},
	empUpdate: function() {
		/* 임직원 등록 페이지 팝업 */
		var noMember = "롯데그룹 임직원 인증은 L.POINT통합회원만 가능 합니다.\nL.POINT통합회원 계정으로 로그인/회원가입해주세요.";
		var openUrl = "";
		
		if(location.href.indexOf("wwws") > -1){		
			openUrl = "https://wwws.lottemart.com/member/GroupEmpUpdate.do";
		}else{
			openUrl = "https://www.lottemart.com/member/GroupEmpUpdate.do";
		}
		
		$.ajax({
			url: '/member/GroupEmpUpChk.do',
			data: { STA : "PC" },
			method: 'GET',
			success : function(data) {
				var resCode = data.resultCode;
				if(resCode == '01'){
					alert(data.resultMsg);
					global.login("MARTSHOP", location.href);
				}else if(resCode == '02' || resCode == '03' || resCode == '04'){
					alert(data.resultMsg);
				}else if(resCode == '10'){
					window.open(openUrl,'임직원 등록','width=620, height=820, toolbar=no, menubar=no, scrollbars=no, resizable=yes');
				}else{
					alert(noMember);
					global.login("MARTSHOP", location.href);
				}
			}
		});
	}
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

// IE <= 8 에서 array의 indexOf를 사용하기 위해 선언
if (!Array.indexOf) {
	Array.prototype.indexOf = function(obj, start) {
		for (var i = (start || 0); i < this.length; i++) {
			if (this[i] == obj) {
				return i;
			}
		}
	}
}

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


/**
 * FRONT 페이지 생성
 * @param currPages - 현재 페이지
 * @param totalRecords - 전체 페이지 수
 * @param rowPerPages - 최대 페이지 번호가 표시되는 수
 * @param url - 페이지 번호를 클릭할 경우 이동하기 위한 URI, A tag의 HREF 속성값
 */
var paginator = function(currPage, totalRecords, rowPerPages, url, maxLinks) {
	if (typeof(url) === "undefined") {
		url = "#";
	}
	var factory = {
			createLink: function(page, text, className) {
				if (typeof(text) === "undefined") {
					text = page + "";
				}
				var html = "<a href=\"" +
						url.replace("##", page) +
						"\" title=\"" +
						page +
						" 페이지로 이동\"";
				if (typeof(className) != "undefined") {
					html += " class=\"" +
							className +
							"\"";
				}
				html += ">" +
						text +
						"</a>";
				return html;
			},
	};

	factory.render = function() {
		maxLinks = maxLinks||10;
		var html = "";
		var totalPages = Math.ceil(totalRecords/rowPerPages);
		var lastPage = currPage === totalPages;
		var pageStart = Math.max((Math.ceil(currPage/maxLinks)-1)*maxLinks + 1, 1);
		var pageEnd = pageStart + maxLinks - 1;

		if (pageEnd > totalPages) {
			pageEnd = totalPages;
		}

		html += "<div class=\"paging\">";

		if (pageStart > 1) {
			if ((pageStart - maxLinks) > 1) {
				html += factory.createLink(1, "처음 페이지로", "page-first");
			}
			html += factory.createLink(pageStart - 1, "이전 페이지로", "page-prev");
		}

		html += "<span class=\"pagelist\">";

		for (var i = pageStart; i <= pageEnd; i++ ) {
			if ( i == currPage ) {
				html += "<strong title=\"현재 페이지\">" + i + "</strong>";
			}
			else {
				html += factory.createLink(i);
			}
		}

		html += "</span>";

		if ((pageEnd + 1) < totalPages) {
			html += factory.createLink(pageEnd + 1, "다음 페이지", "page-next");
			if (!lastPage) {
				html += factory.createLink(totalPages, "마지막 페이지", "page-last");
			}
		}

		html += "</div>";
		return html;
	};

	return factory.render();
};

/**
 * 공통 Utilities
 */
var utils = {
	// Number 타입의 문자/숫자를 소수점 및 단위포인트를 갖춘 문자열로 전달한다.
	formatNumber: function(val, partSize, minorDigit) {
		var re = '\\d(?=(\\d{' + (partSize || 3) + '})+' + (minorDigit > 0 ? '\\D' : '$') + ')',
	        num = val.toFixed(Math.max(0, ~~minorDigit));
		var c = ".";
		var s = ",";

	    return (c ? num.replace('.', c) : num).replace(new RegExp(re, 'g'), '$&' + (s || ','));
	},

	removeArrayByGrep: function(arr, grepFunc) {
		if (arr != null) {
			var grepArr = $.grep(arr, grepFunc);
			if (grepArr != null) {
				var pos = -1;
				for(var i = 0 ; i < grepArr.length ; i++ ) {
					pos = arr.indexOf(grepArr[i]);
					if (pos >= 0) {
						arr.splice(pos, 1);
					}
				}
			}
		}
		return arr;
	},

	getItemImagePath: function(prodCd, width, scheme) {
		var dirName = prodCd.substring(0,5).trim();
		return scheme + "/images/prodimg/" + dirName + "/" + prodCd + "_1_" + width + ".jpg";
	}
};

//radio, checkbox element 의 checked, disabled 적용
jQuery.fn.extend({
	propChecked: function(mode) {
		if (mode) {
			$("[name='" + $(this).attr("name") + "']:checked").prop("checked", false).removeAttr("checked");
		}
		return this.each(function(i, obj) {
			if (mode) {
				$(obj).attr("checked", "checked");
			}
			else {
				$(obj).removeAttr("checked");
			}
			$(obj).prop("checked", mode);
			if ($(obj).parent().is(".check-data") || $(obj).parent().is(".radio-data")) {
				if (mode) {
					$(obj).parent().addClass("active");
				}
				else {
					$(obj).parent().removeClass("active");
				}
			}
		});
	},
	propDisabled: function(mode) {
		return this.each(function(i, obj) {
			if (mode) {
				$(obj).attr("disabled", mode);
			}
			else {
				$(obj).removeAttr("disabled");
			}
			$(obj).prop("disabled", mode);
			if ($(obj).parent().is(".check-data") || $(obj).parent().is(".radio-data")) {
				if (mode) {
					$(obj).parent().addClass("disabled");
				}
				else {
					$(obj).parent().removeClass("disabled");
				}
				if ($(obj).is(":checked")) {
					$(obj).parent().addClass("active");
				}
				else {
					$(obj).parent().removeClass("active");
				}
			}
		});
	}
});
