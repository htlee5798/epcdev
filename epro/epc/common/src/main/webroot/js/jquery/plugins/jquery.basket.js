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

		//선언부
		var $btnBasket = null;
		var defaults = {
			$wrapper : null,
			wrapperClass : '[data-panel="product"]',
			isMobile : $.utils.isMobile(),
			mallDivnCd : '00001'
		};

		//각 장바구니 버튼에 이벤트 할당
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


			//190625 웹접근성 : 원은재
			//장바구니 레이어 팝업이 떴을 경우, 초점이동이 논리적으로 되게끔 수정
			$(v).on( 'keydown', '.btn-basket[data-method="basket"]' , function(e) {
				var $this, $nextArticle;
				if(_wac.isEnterEvent(e)){
					_wac.markWacEnteredAsync($(this));
				}else if(_wac.isTabEvent(e)){
					$this = $(this)

					//장바구니 담기 버튼에서 엔터를 쳐서 팝업이 떴을 경우 : 팝업 안으로 촛점이동
					if(_wac.isWacEntered($this)){
						_wac.preventDefaultAction(e);
						_wac.removeWacEnteredAsync($this);
						_wac.getFocusables($('.layerpop-toast-btm')).first().focus();
					//그 이외의 경우
					}else{
						$nextArticle = $this.closest('.product-article').next();
						if($nextArticle.hasClass('product-article')){
							_wac.preventDefaultAction(e);
							$nextArticle.addClass('hover');
							_wac.getFocusables($nextArticle).first().focus();
						}
					}
				}
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