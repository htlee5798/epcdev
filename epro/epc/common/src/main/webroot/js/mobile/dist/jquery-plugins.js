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