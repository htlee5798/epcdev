$(function() {
	RNB.init();

	var $RNBBasketArea = $("#RNB_BasketArea"),
		$RNBViewBasket = $("#RNB_View_Basket"),
		$noLoginMenu = $("#noLoginMenu"),
		$RNBLogin = $("#RNB_Login"),
		$rnbPersonalBar = $( '#rnbPersonalBar' ),
		$toggleBtn = $rnbPersonalBar.find('.menu-toggle');
	
	$toggleBtn.triggerHandler('click');

	//페이지 새로 그린 경우 텝 미작동..
	$RNBBasketArea.on("click", "strong[data-tab-id]", function(){
		var $this = $( this ),
			basketActiveTabId =  $this.attr("data-tab-id");

		$this
			.addClass("active")
			.siblings("strong[data-tab-id]")
			.removeClass("active");

		$RNBViewBasket
			.find(".pay-info .txt-amount." + basketActiveTabId )
			.show()
			.siblings(".txt-amount")
			.hide();

		RNB.config.basketActiveTabId = basketActiveTabId;

		return false;
	});

	//비로그인시
	if( _Login_yn === 'N' ) {
		$noLoginMenu
			.find(".my-basket, .my-history, .my-coupon, .my-wish, .link-talk, .icon-change")
			.on("click", function(e) {
				e.preventDefault();
				alert("회원 전용 서비스 입니다. 회원 로그인을 해주세요.");
				$RNBLogin.triggerHandler('click');
		});
	}
});

var RNB = {
	config : {
		currentTime : new Date().getTime(),
		basketActiveTabId : "tab01",
		hodevMallStrYn : "N"
	},
	init: function() {
		this.bindEvent();

		if ( _Login_yn === 'N' ) {
			personalBarToggle();
			return;
		}
		//속도 개선
		$.utils.deferredAction(
			this.coupon.load,
			this.delivery.load,
			this.basket.load,
			this.history.load
		);
	},
	load: function() {
		var that = RNB;
		that.delivery.load();
		that.basket.load();
		that.coupon.load();
		that.history.load();
		that.bindEvent();
	},
	bindEvent: function() {
		var _rnb = this,
			$rnbPersonalBar = $( '#rnbPersonalBar' );
			
		$rnbPersonalBar.on("click", ".link-cart", function(e) {
			_rnb.config.basketActiveTabId === 'tab01'
				? global.goBasket()
				: global.goBasket('B');

			return false;
		}).on( 'click', ".link-talk", function(e) {
			if( _Login_yn === 'N' ) {
				global.isLogin( null, function() {
					global.talkCounsel();
				});
			} else {
				global.talkCounsel();
			}
			return false;
		});

		personalBarToggle();
	},

	delivery: {
		load: function(d) {
			var _delivery = RNB.delivery;
			if ( _Login_yn === 'Y' ) {
				if(d) {
					_delivery.render(d);
					return;
				}

				$.api.get({
					apiName : 'myDelivery',
					data : {
						v : RNB.config.currentTime
					},
					successCallback : function( data ) {
						_delivery.render(data);
					}
				});
			}
		},
		render: function(data) {
			var _delivery = RNB.delivery;

			var $rnbPersonalBar = $( '#rnbPersonalBar' ),
				$deliveryElement = $rnbPersonalBar.find( '.delivery-info' );

			_delivery.bindEvent();
			
			if (data != null) {
				RNB.config.hodevMallStrYn = data.storeInfo.HODEV_MALL_STR_YN;
				
				// 택배배송 점
				if (data.stdistMallStrNm != null && data.stdistMallStrNm.STR_NM == "택배배송점") {
					return;
				}
				
				/* 해당 이슈 2018.01.03 이종휘 담당요청으로 인한 주석 유지
				if ( data.storeInfo && data.storeInfo.HODEV_MALL_STR_YN === "Y") {
					$rnbPersonalBar.find(".hodev_str").show();
				} else 
				*/
				if (data.stdistMallStrNm != null && data.stdistMallStrNm.STR_NM == "택배배송점") {
					$rnbPersonalBar.find(".deliv_str").show();
				} else {
					$rnbPersonalBar.find(".normal_str").show();
				}

				if (data.hopeDeliDay) {
					var month = data.hopeDeliDay.DELIV_DATE.substring(4,6),
						day = data.hopeDeliDay.DELIV_DATE.substring(6,8),
						//김포 허브앤스포크 작업 - 시간 출력하는 부분 수정 - 분 부분을 0으로 나오게끔 수정
						//주석 처리 time = data.hopeDeliDay.DELI_PRAR_START_TM;
						time = data.hopeDeliDay.DELI_PRAR_START_TM.substring(0,2) + ":" + data.hopeDeliDay.DELI_PRAR_START_TM.substring(3,4) + "0";
					// 배송일
					$deliveryElement
						.find( ".time-txt" )
						.html( month + "." + day + ". " + time + "부터" );
				}
				// 배송지명
				if( data.storeInfo && data.storeInfo.STR_NM ) {
					var strNm = $deliveryElement.find( ".info-tit" ).eq(0).text().indexOf('픽업') > 0 
					|| $deliveryElement.find( ".info-tit" ).eq(0).text().indexOf('드라이브') > 0 
					|| $deliveryElement.find( ".info-tit" ).eq(0).text().indexOf('렌터카') > 0? $deliveryElement.find( ".info-tit" ).eq(0).text() : data.storeInfo.STR_NM;
					$deliveryElement
						.find( ".info-tit" )
						.html( strNm );
				}
			} else {
				$rnbPersonalBar.find(".empty_str").show();
				alert("기본 배송지를 등록해 주세요.");
				RNB.delivery.openMyDelivery();
			}
			if(data.expressYn != null){
				if(data.expressYn == 'Y'){
					$('#expressBanner').show();
				}
			}
		},
		bindEvent: function() {
			// 배송시간 보기
			$("#guide-delivery").on("click", function(e) {
				if( RNB.config.hodevMallStrYn == "Y" )  {
					alert("고객님의 현재 배송지는 전용센터 택배 배송지입니다.\n택배상품은 결제일 다음날부터 2~3일 이내 발송됩니다.");
				} else {
					RNB.delivery.openDeliveryTime();
				}
				

				return false;
			});
			// 배송지변경
			$("#delivery-change").on("click", function(e) {
				RNB.delivery.openMyDelivery();

				return false;
			});
		},
		openDeliveryTime: function() {
			openwindow( '/quickmenu/popup/deliverytime.do?SITELOC=AE001' , "POP_DELIVERYTIME", 920, 820, "yes");
		},
		openMyDelivery: function(param) {
			var popupMyDeliveryUrl = '/mymart/popup/selectMyDeliveryList.do?SITELOC=AE002';
			if(typeof param !== 'undefined'){
				popupMyDeliveryUrl += param;
			}
			openwindow( popupMyDeliveryUrl, "POP_MY_DELIVERY", 770, 720, "yes");
		}
	},
	basket: {
		data: {
			normalBasket: {
				itemList: [],
				totalAmt: 0,
				totalDcAmt: 0,
				totalOrderAmt: 0
			},
			periDeliBasket: {
				itemList: [],
				totalAmt: 0,
				totalDcAmt: 0,
				totalOrderAmt: 0
			},
			defaultDelivery: {} ,
			highwayStrYn: {} ,
			isSmartPickUp: {} ,
			zipSeq: {} ,
			pickUpYn: {}
			
		},
		load: function(d) {
			var _basket = RNB.basket;

			if ( _Login_yn === "Y") {
				if(d) {
					_basket
						.setRenderData(d)
						.render();
					return;
				}

				$.api.get({
					apiName : 'basketList',
					data : {
						v : RNB.config.currentTime
					},
					successCallback : function( data ) {
						_basket
							.setRenderData(data)
							.render();
					}
				});
			}
			else {
				_basket.render();
			}
		},
		setRenderData: function(d) {
			var _basket = RNB.basket;

			_basket.data.normalBasket.itemList = d.arrMartNormal || [];
			_basket.data.periDeliBasket.itemList = d.arrMartPeriDeli || [];
			_basket.data.defaultDelivery = d.mDefaultMemberAddr;
			_basket.data.highwayStrYn = d.highwayStrYn;
			_basket.data.isSmartPickUp = d.isSmartPickUp;
			_basket.data.zipSeq = d.zipSeq;
			_basket.data.pickUpYn = d.pickUpYn;
			
			return _basket;
		},
		calculate: function() {
			var _basket = RNB.basket;
			// 일반 장바구니
			_basket.data.normalBasket.totalAmt = 0;
			_basket.data.normalBasket.totalDcAmt = 0;
			_basket.data.normalBasket.totalOrderAmt = 0;
			_basket.data.normalBasket.totalCount = 0;

			$.each( _basket.data.normalBasket.itemList, function(i, item) {
				if ($.trim(item.CTPD_ITEM_YN) == "" || item.CTPD_ITEM_YN == "N") {
					_basket.data.normalBasket.totalAmt += ( parseInt( item.SELL_PRC, 10 ) * parseInt( item.BSKET_QTY, 10 ));
					_basket.data.normalBasket.totalDcAmt += (( parseInt( item.SELL_PRC, 10 ) - parseInt( item.PROMO_MAX_VAL, 10 )) * parseInt( item.BSKET_QTY, 10 ) );
					_basket.data.normalBasket.totalCount ++;
				}
			});

			_basket.data.normalBasket.totalOrderAmt = _basket.data.normalBasket.totalAmt - _basket.data.normalBasket.totalDcAmt;

			// 정기배송 장바구니
			_basket.data.periDeliBasket.totalAmt = 0;
			_basket.data.periDeliBasket.totalDcAmt = 0;
			_basket.data.periDeliBasket.totalOrderAmt = 0;
			_basket.data.periDeliBasket.totalCount = 0;

			$.each( _basket.data.periDeliBasket.itemList, function(i, item) {
				if ($.trim(item.CTPD_ITEM_YN) == "" || item.CTPD_ITEM_YN == "N") {
					_basket.data.periDeliBasket.totalAmt += ( parseInt( item.SELL_PRC, 10 ) * parseInt( item.BSKET_QTY, 10 ) );
					_basket.data.periDeliBasket.totalDcAmt += (( parseInt( item.SELL_PRC, 10 ) - parseInt( item.PROMO_MAX_VAL, 10 )) * parseInt( item.BSKET_QTY, 10 ) );
					_basket.data.periDeliBasket.totalCount ++;
				}
			});
			_basket.data.periDeliBasket.totalOrderAmt = _basket.data.periDeliBasket.totalAmt - _basket.data.periDeliBasket.totalDcAmt;

			return _basket;
		},
		render: function() {
			var _basket = RNB.basket;

			_basket.calculate();

			_basket.data.activeClass = RNB.config.basketActiveTabId;

			var html = $.render.rnbBasket( _basket.data );

			var $rnbTotalBasketAmt = $( '#RNB_Total_BasketAmt' ),
				$rnbTotalBasketCount = $("#RNB_Total_BasketCount"),
				$textAmount = $rnbTotalBasketAmt.find( '.txt-amount' ),
				$rnbBasketArea = $( '#RNB_BasketArea' ),
				$rnbViewBasket = $( '#RNB_View_Basket' ),
				$gnbBasket = $( '#GNB_Basket' );

			var totalOrderAmt = utils.formatNumber(_basket.data.normalBasket.totalOrderAmt),
				totalCount = utils.formatNumber(_basket.data.normalBasket.totalCount),
				periDeliTotalOrderAmt = utils.formatNumber(_basket.data.periDeliBasket.totalOrderAmt);

			$rnbTotalBasketAmt.html( totalOrderAmt );
			$rnbTotalBasketCount.html( totalCount );	// + that.data.periDeliBasket.totalCount

			$rnbBasketArea.html(html);

			// 일반 장바구니, 정기배송 장바구니 합계금액
			$rnbViewBasket
				.find( '.txt-amount.tab01' )
				.text( totalOrderAmt );

			$rnbViewBasket
				.find( '.txt-amount.tab02' )
				.text( periDeliTotalOrderAmt );

			if( RNB.config.basketActiveTabId !== "" ) {
				$rnbBasketArea
					.find( '[data-tab-id="' + RNB.config.basketActiveTabId + '"]' )
					.triggerHandler('click');
			}
			//header 의 장바구니에도 수량 표시
			$gnbBasket
				.find( "> em" )
				.text( totalCount );
		},
		addBasket: function(item) {
			var _basket = RNB.basket;

			global.addBasket( item, function(data) {
				alert("선택한 상품을 장바구니에 추가했습니다.");
				_basket.load();
			}, _basket.errorCallback);
		},
		removeBasket: function(basketNo) {
			var _basket = RNB.basket;

			var $rnbViewBasket = $( '#RNB_View_Basket' );

			if ( _Login_yn=== "Y" ) {
				$.api.set({
					apiName : 'basketRemove',
					data : {
						basketNo : basketNo
					},
					successCallback : function( data ) {
						utils.removeArrayByGrep(_basket.data.normalBasket.itemList, function(basket) {
							return basket.BSKET_NO === basketNo;
						});
						utils.removeArrayByGrep(_basket.data.periDeliBasket.itemList, function(basket) {
							return basket.BSKET_NO === basketNo;
						});

						if ( $rnbViewBasket.find( '> .rnb-layerpop' ).length > 0 ) {
							var $target = $("#RNB_Basket_" + basketNo );

							$target.fadeOut({
								complete: function() {
									var basketTab = $target.closest(".tab-cont").attr("id"),
										$basketTab = $( '#' + basketTab ),
										itemCount = $basketTab.find(".list-item").length;

									$target.remove();

									if ( itemCount <= 0 ) {
										$basketTab
											.find( ".no-data-result" )
											.show();
									}

									$rnbViewBasket
										.find( "a[href='#" + basketTab + "']")
										.find("em")
										.html( utils.formatNumber( itemCount ) );


									var $tabCont = $rnbViewBasket.find( "strong[data-tab-id].active");

									// Aiden 장바구니 삭제 todo
									var basketData = _basket.data.normalBasket;
									if (basketTab != "RNBcartTab01") {
										basketData = _basket.data.periDeliBasket;
									}

									$rnbViewBasket
										.find(".pay-info .txt-amount." + $tabCont.attr("data-tab-id") )
										.html( utils.formatNumber( basketData.totalOrderAmt ) );
								}
							});
						}

						_basket.render();
					},
					errorCallback : _basket.errorCallback
				});
			}
		},
		errorCallback: function(xhr, status, error) {
			if (xhr) {
				var data = $.parseJSON(xhr.responseText);
				if (data && data.message && xhr.status != 401) {
					alert(data.message);
					if ( data.redirectUrl != null && data.redirectUrl.trim().length > 0 ) {
						location.href = redirectUrl;
						return;
					}
				}
			}
		}
	},
	coupon: {
		load: function(d) {
			var _coupon = RNB.coupon;

			if ( _Member_yn === "true" ) {
				if(d) {
					_coupon.render(d);
					return;
				}

				$.api.get({
					apiName : 'myCoupon',
					data : {
						v : RNB.config.currentTime
					},
					successCallback : function( data ) {
						if (data) {
							_coupon.render(data);
						}
					}
				});
			}
			else {
				_coupon.render({});
			}
		},
		render: function(data) {
			var $rnbCouponArea = $("#RNB_CouponArea"),
				$rnbCouponCount = $("#RNB_CouponCount");

			var _coupon = RNB.coupon;
			if (data == null) {
				data = {
					couponList: []
				}
			}

			var couponInfo = {
				totalCount: (data.couponList ? data.couponList.length : 0),
				list: data.couponList || [],
				expireCount: 0,
				expireList: []
			};

			var today = new Date();

			today.setDate(today.getDate() + 3);

			var dy = zeroFill(today.getFullYear(), 4) + zeroFill(today.getMonth()+1, 2) + zeroFill(today.getDate(), 2);

			$.each(data.couponList, function(i, o) {
				o.USE_END_DY_FMT = o.USE_END_DY.substring(0, 4) + "." + o.USE_END_DY.substring(4, 6) + "." + o.USE_END_DY.substring(6, 8);
				if (o.USE_END_DY <= dy) {
					couponInfo.expireCount++;
					couponInfo.expireList.push(o);
				}
			});

			var html = $.render.rnbCoupon(couponInfo);

			$rnbCouponArea.html(html);
			$rnbCouponCount.html( utils.formatNumber( couponInfo.totalCount ) );
		}
	},
	wish: { //사용하지 안음
		data: [],
		load: function(d) {},
		render: function() {},
		removeWish: function(prodCd) {},
		bindEvent: function() {}
	},
	history: {
		data: [],
		load: function(d) {
			var _history = RNB.history;

			if ( _Member_yn === "true") {
				if(d) {
					_history.data = d;
					_history.render();
					return;
				}

				$.api.get({
					apiName : 'myHistory',
					data : {
						v : RNB.config.currentTime
					},
					successCallback : function( data ) {
						if (data) {
							_history.data = data;
							_history.render();
						}
					}
				});
			}
			else {
				_history.render({});
			}
			historyControl();
		},
		render: function() {
			var _history = RNB.history;

			var $rnbQuickHistoryArea = $("#RNB_QuickHistoryArea"),
				$rnbHistoryArea = $("#RNB_HistoryArea"),
				$rnbHistoryCount = $('#RNB_HistoryCount');

			var historyData = {
				totalCount: (_history.data.historyList ? _history.data.historyList.length : 0),
				history: _history.data.historyList || []
			};

			var html = $.render.rnbHistory(historyData);

			$rnbQuickHistoryArea.html($.render.rnbQuickHistory(historyData));
			$rnbHistoryArea.html(html);
			$rnbHistoryCount.html(utils.formatNumber(historyData.totalCount));

			historyControl();
		},
		add: function(data, option) {
			var _history = RNB.history;

			var defaultData = {
				histTypeCd: "",
				prodCd: "",
				categoryId: "",
				contentsNo: ""
			};

			var defaultOption = {
				success: null,
				fail: null
			};

			data = $.extend({}, defaultData, data);
			option = $.extend({}, defaultOption, option);

			$.api.set({
				apiName : 'myHistoryAdd',
				data : data,
				successCallback : function( data ){
					if (data) {
						_history.load();
					}
					if (typeof(option.success) != "undefined") {
						if ($.isFunction(option.success)) {
							option.success(data);
						}
					}
				},
				errorCallback : function(xhr, status, error) {
					if (typeof(option.fail) != "undefined") {
						if ($.isFunction(option.fail)) {
							option.success(xhr, status, error);
						}
					}
				}
			});
		},
		removeHistory: function(histSeq) {
			var _history = RNB.history;

			$.api.set({
				apiName : 'myHistoryRemove',
				data : {
					histSeq: histSeq
				},
				successCallback : function( data ) {
					var list = $.grep(_history.data.historyList, function(o) {
						return o.histSeq === histSeq;
					});

					if ( list != null) {
						var pos = 0;
						for (var i = 0 ; i < list.length ; i++ ) {
							pos = _history.data.historyList.indexOf(list[i]);
							if (pos > -1) {
								_history.data.historyList.splice(pos, 1);
							}
						}

						_history.render();

						if ($("#RNB_View_History > .rnb-layerpop").length > 0) {
							$("#RNB_History_" + histSeq).fadeOut({
								complete: function() {
									$("#RNB_History_" + histSeq).remove();
									var itemCount = $("#RNB_View_History .rnb-list [id^='RNB_History_']").length;
									if (itemCount <= 0) {
										$("#RNB_View_History .no-data-result").show();
									}
									$("#RNB_View_History .rnb-layer-header span.num").html(utils.formatNumber(itemCount));
								}
							});
						}
						$("#RNB_QuickHistory_" + histSeq).remove();
						historyControl();
					}
				}
			});
		},
		initSlider: function(listEle) {
			var OPT = {
				mode : 'fade',
				pager : false,
				auto: (isIE) ? false : true,
				autoHover: true,
				speed: 0,
				onLoadSlider: function(activeIdx) {
					var slider = this;
					_wac.bx.setAccessibility(slider, OPT.autoHover);
				},
			};
			var that = $(listEle).bxSlider(OPT);
			return that;
		}
	},
	
	goHistoryProdDetail: function(categoryId,prodCd) {
		if(confirm("상품 상세에서 옵션 선택 후 담으실 수 있습니다.")){
			document.location.href = _LMAppUrl + "/product/ProductDetail.do?ProductCD=" + prodCd + "&CategoryID=" + categoryId;
		}
	}
};