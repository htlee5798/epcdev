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
;(function( $, window, document, undefined ) {
	'use strict';

	var $window = $(window),
		$document = $(document)

	var BenefitLayer = function(element, option) {
		this.$body = null,
		this.element = $(element);
		this.$mask;
		this.defaults = {
			className: 'benefit-layer',
			styles: '',
			templateName : 'benefitLayer',
			after: null,
			body:'body',
			mask:false
		},
		this.$layer = null;

		$.extend(true, this.defaults, option || {});

		if(this.defaults.body instanceof jQuery){
			this.$body = this.defaults.body;
		}else{
			this.$body = $(this.defaults.body);
		}

		this.defaults.data = parseBenefitData( this.element.data( 'benefit' ) );
		this.init();
		return this;
	};

	BenefitLayer.prototype = {
		init: function() {
			var _this = this;

			$window.on( 'resize', function() {
				_this.close();
			});
		},
		set: function() {
			var _this = this;

			if( this.$body.find( '.benefit-layer' ).not( _this.$layer ).length > 0 ) {
				this.$body.find( '[data-benefit]' ).not( _this.element ).each(function( i, v ) {
					var $v = $( v );

					if( $v.data( 'benefitlayer' ) ) {
						$v.data( 'benefitlayer' ).close();
					}
				});
			}

			if( _this.$layer !== null ) {
				_this.close();

				return _this;
			}
			_this.element.addClass( 'active' );

			$document.off('click.close').on('click.close', function(e) {
				var _target = e.target;
				if(_target === _this.element[0] || $(_target).closest( _this.$layer).length > 0) {
					return;
				}
				_this.close();
			});

			if(_this.defaults.mask){
				this.$mask = $('<div class="mask"></div>');
				this.$mask.on('touchmove', $.proxy( _this.maskTouchMove, _this));
				this.$mask.on('click',  $.proxy(_this.close, _this));
				this.$mask.appendTo(this.$body);
			}

			_this.$layer = $( $.render[ _this.defaults.templateName ](_this.defaults) ).appendTo(this.$body);

			if( _this.defaults.styles !== '' ) {
				_this.$layer.css( _this.defaults.styles );
			} else {
				_this.$layer
				.css({
					display: 'block',
					top: _this.element.offset().top + _this.element.height() + 5 - this.$body.offset().top,
					left: function() {
						var _left = Math.max(_this.element.offset().left + _this.element.outerWidth() - _this.$layer.outerWidth(true), 0);

						return _left;
					}
				});
			}

			_this.$layer
				.find('.js-close')
				.on('click.close', $.proxy(_this.close, _this));

		},
		maskTouchMove: function(e) {
			this.close();
		}
		,close: function(e) {
			var _this = this;
			if( _this.$layer !== null ) {
				_this.element.removeClass( 'active' );
				_this.$layer.remove();
				_this.$layer = null;
				$document.off('click.close');
				if(_this.defaults.mask){
					_this.$mask.off('click');
					_this.$mask.remove();
					_this.$mask = null;
				}
			}
		}
	};

	/*
	 * data split : data-benefit="온라인상품할인!123원@카드상품할인!5%@"
	 * */
	function parseBenefitData(data) {
		var val = null,
			arr = [];

		data = data.split('@');

		for(var i in data) {
			if(!data[i]) {
				return arr;
			}

			val = data[i].split('!');

			arr.push({
				'title': val[0],
				'value': $.utils.comma( val[1] )
			});
		}
	}

	$.fn.benefitLayer = function(option) {
		return this.each(function( i, v ) {
			var $v = $( v );

			if( !$v.data( 'benefitlayer' ) ) {
				$v.data( 'benefitlayer' , new BenefitLayer( v, option) );
				$v.data( 'benefitlayer' ).set();
			} else {
				$v.data( 'benefitlayer' ).set();
			}
		});
	};

	$window.on('unload', function() {

		$document.find( '[data-benefit]' ).each(function( i, v ) {
			var $v = $( v );

			if( $v.data( 'benefitlayer' ) ) {
				$v.data( 'benefitlayer' ).close();
			}
		});

	});

})( jQuery, window, document );
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
// Copyright (C) 2011-2012 Software Languages Lab, Vrije Universiteit Brussel
// This code is dual-licensed under both the Apache License and the MPL

// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/* Version: MPL 1.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is a shim for the ES-Harmony reflection module
 *
 * The Initial Developer of the Original Code is
 * Tom Van Cutsem, Vrije Universiteit Brussel.
 * Portions created by the Initial Developer are Copyright (C) 2011-2012
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *
 */

 // ----------------------------------------------------------------------------

 // This file is a polyfill for the upcoming ECMAScript Reflect API,
 // including support for Proxies. See the draft specification at:
 // http://wiki.ecmascript.org/doku.php?id=harmony:reflect_api
 // http://wiki.ecmascript.org/doku.php?id=harmony:direct_proxies

 // For an implementation of the Handler API, see handlers.js, which implements:
 // http://wiki.ecmascript.org/doku.php?id=harmony:virtual_object_api

 // This implementation supersedes the earlier polyfill at:
 // code.google.com/p/es-lab/source/browse/trunk/src/proxies/DirectProxies.js

 // This code was tested on tracemonkey / Firefox 12
//  (and should run fine on older Firefox versions starting with FF4)
 // The code also works correctly on
 //   v8 --harmony_proxies --harmony_weakmaps (v3.6.5.1)

 // Language Dependencies:
 //  - ECMAScript 5/strict
 //  - "old" (i.e. non-direct) Harmony Proxies
 //  - Harmony WeakMaps
 // Patches:
 //  - Object.{freeze,seal,preventExtensions}
 //  - Object.{isFrozen,isSealed,isExtensible}
 //  - Object.getPrototypeOf
 //  - Object.keys
 //  - Object.prototype.valueOf
 //  - Object.prototype.isPrototypeOf
 //  - Object.prototype.toString
 //  - Object.prototype.hasOwnProperty
 //  - Object.getOwnPropertyDescriptor
 //  - Object.defineProperty
 //  - Object.defineProperties
 //  - Object.getOwnPropertyNames
 //  - Object.getOwnPropertySymbols
 //  - Object.getPrototypeOf
 //  - Object.setPrototypeOf
 //  - Object.assign
 //  - Function.prototype.toString
 //  - Date.prototype.toString
 //  - Array.isArray
 //  - Array.prototype.concat
 //  - Proxy
 // Adds new globals:
 //  - Reflect

 // Direct proxies can be created via Proxy(target, handler)

 // ----------------------------------------------------------------------------

(function(global){ // function-as-module pattern
"use strict";

// === Direct Proxies: Invariant Enforcement ===

// Direct proxies build on non-direct proxies by automatically wrapping
// all user-defined proxy handlers in a Validator handler that checks and
// enforces ES5 invariants.

// A direct proxy is a proxy for an existing object called the target object.

// A Validator handler is a wrapper for a target proxy handler H.
// The Validator forwards all operations to H, but additionally
// performs a number of integrity checks on the results of some traps,
// to make sure H does not violate the ES5 invariants w.r.t. non-configurable
// properties and non-extensible, sealed or frozen objects.

// For each property that H exposes as own, non-configurable
// (e.g. by returning a descriptor from a call to getOwnPropertyDescriptor)
// the Validator handler defines those properties on the target object.
// When the proxy becomes non-extensible, also configurable own properties
// are checked against the target.
// We will call properties that are defined on the target object
// "fixed properties".

// We will name fixed non-configurable properties "sealed properties".
// We will name fixed non-configurable non-writable properties "frozen
// properties".

// The Validator handler upholds the following invariants w.r.t. non-configurability:
// - getOwnPropertyDescriptor cannot report sealed properties as non-existent
// - getOwnPropertyDescriptor cannot report incompatible changes to the
//   attributes of a sealed property (e.g. reporting a non-configurable
//   property as configurable, or reporting a non-configurable, non-writable
//   property as writable)
// - getPropertyDescriptor cannot report sealed properties as non-existent
// - getPropertyDescriptor cannot report incompatible changes to the
//   attributes of a sealed property. It _can_ report incompatible changes
//   to the attributes of non-own, inherited properties.
// - defineProperty cannot make incompatible changes to the attributes of
//   sealed properties
// - deleteProperty cannot report a successful deletion of a sealed property
// - hasOwn cannot report a sealed property as non-existent
// - has cannot report a sealed property as non-existent
// - get cannot report inconsistent values for frozen data
//   properties, and must report undefined for sealed accessors with an
//   undefined getter
// - set cannot report a successful assignment for frozen data
//   properties or sealed accessors with an undefined setter.
// - get{Own}PropertyNames lists all sealed properties of the target.
// - keys lists all enumerable sealed properties of the target.
// - enumerate lists all enumerable sealed properties of the target.
// - if a property of a non-extensible proxy is reported as non-existent,
//   then it must forever be reported as non-existent. This applies to
//   own and inherited properties and is enforced in the
//   deleteProperty, get{Own}PropertyDescriptor, has{Own},
//   get{Own}PropertyNames, keys and enumerate traps

// Violation of any of these invariants by H will result in TypeError being
// thrown.

// Additionally, once Object.preventExtensions, Object.seal or Object.freeze
// is invoked on the proxy, the set of own property names for the proxy is
// fixed. Any property name that is not fixed is called a 'new' property.

// The Validator upholds the following invariants regarding extensibility:
// - getOwnPropertyDescriptor cannot report new properties as existent
//   (it must report them as non-existent by returning undefined)
// - defineProperty cannot successfully add a new property (it must reject)
// - getOwnPropertyNames cannot list new properties
// - hasOwn cannot report true for new properties (it must report false)
// - keys cannot list new properties

// Invariants currently not enforced:
// - getOwnPropertyNames lists only own property names
// - keys lists only enumerable own property names
// Both traps may list more property names than are actually defined on the
// target.

// Invariants with regard to inheritance are currently not enforced.
// - a non-configurable potentially inherited property on a proxy with
//   non-mutable ancestry cannot be reported as non-existent
// (An object with non-mutable ancestry is a non-extensible object whose
// [[Prototype]] is either null or an object with non-mutable ancestry.)

// Changes in Handler API compared to previous harmony:proxies, see:
// http://wiki.ecmascript.org/doku.php?id=strawman:direct_proxies
// http://wiki.ecmascript.org/doku.php?id=harmony:direct_proxies

// ----------------------------------------------------------------------------

// ---- WeakMap polyfill ----

// TODO: find a proper WeakMap polyfill

// define an empty WeakMap so that at least the Reflect module code
// will work in the absence of WeakMaps. Proxy emulation depends on
// actual WeakMaps, so will not work with this little shim.
if (typeof WeakMap === "undefined") {
  global.WeakMap = function(){};
  global.WeakMap.prototype = {
    get: function(k) { return undefined; },
    set: function(k,v) { throw new Error("WeakMap not supported"); }
  };
}

// ---- Normalization functions for property descriptors ----

function isStandardAttribute(name) {
  return /^(get|set|value|writable|enumerable|configurable)$/.test(name);
}

// Adapted from ES5 section 8.10.5
function toPropertyDescriptor(obj) {
  if (Object(obj) !== obj) {
    throw new TypeError("property descriptor should be an Object, given: "+
                        obj);
  }
  var desc = {};
  if ('enumerable' in obj) { desc.enumerable = !!obj.enumerable; }
  if ('configurable' in obj) { desc.configurable = !!obj.configurable; }
  if ('value' in obj) { desc.value = obj.value; }
  if ('writable' in obj) { desc.writable = !!obj.writable; }
  if ('get' in obj) {
    var getter = obj.get;
    if (getter !== undefined && typeof getter !== "function") {
      throw new TypeError("property descriptor 'get' attribute must be "+
                          "callable or undefined, given: "+getter);
    }
    desc.get = getter;
  }
  if ('set' in obj) {
    var setter = obj.set;
    if (setter !== undefined && typeof setter !== "function") {
      throw new TypeError("property descriptor 'set' attribute must be "+
                          "callable or undefined, given: "+setter);
    }
    desc.set = setter;
  }
  if ('get' in desc || 'set' in desc) {
    if ('value' in desc || 'writable' in desc) {
      throw new TypeError("property descriptor cannot be both a data and an "+
                          "accessor descriptor: "+obj);
    }
  }
  return desc;
}

function isAccessorDescriptor(desc) {
  if (desc === undefined) return false;
  return ('get' in desc || 'set' in desc);
}
function isDataDescriptor(desc) {
  if (desc === undefined) return false;
  return ('value' in desc || 'writable' in desc);
}
function isGenericDescriptor(desc) {
  if (desc === undefined) return false;
  return !isAccessorDescriptor(desc) && !isDataDescriptor(desc);
}

function toCompletePropertyDescriptor(desc) {
  var internalDesc = toPropertyDescriptor(desc);
  if (isGenericDescriptor(internalDesc) || isDataDescriptor(internalDesc)) {
    if (!('value' in internalDesc)) { internalDesc.value = undefined; }
    if (!('writable' in internalDesc)) { internalDesc.writable = false; }
  } else {
    if (!('get' in internalDesc)) { internalDesc.get = undefined; }
    if (!('set' in internalDesc)) { internalDesc.set = undefined; }
  }
  if (!('enumerable' in internalDesc)) { internalDesc.enumerable = false; }
  if (!('configurable' in internalDesc)) { internalDesc.configurable = false; }
  return internalDesc;
}

function isEmptyDescriptor(desc) {
  return !('get' in desc) &&
         !('set' in desc) &&
         !('value' in desc) &&
         !('writable' in desc) &&
         !('enumerable' in desc) &&
         !('configurable' in desc);
}

function isEquivalentDescriptor(desc1, desc2) {
  return sameValue(desc1.get, desc2.get) &&
         sameValue(desc1.set, desc2.set) &&
         sameValue(desc1.value, desc2.value) &&
         sameValue(desc1.writable, desc2.writable) &&
         sameValue(desc1.enumerable, desc2.enumerable) &&
         sameValue(desc1.configurable, desc2.configurable);
}

// copied from http://wiki.ecmascript.org/doku.php?id=harmony:egal
function sameValue(x, y) {
  if (x === y) {
    // 0 === -0, but they are not identical
    return x !== 0 || 1 / x === 1 / y;
  }

  // NaN !== NaN, but they are identical.
  // NaNs are the only non-reflexive value, i.e., if x !== x,
  // then x is a NaN.
  // isNaN is broken: it converts its argument to number, so
  // isNaN("foo") => true
  return x !== x && y !== y;
}

/**
 * Returns a fresh property descriptor that is guaranteed
 * to be complete (i.e. contain all the standard attributes).
 * Additionally, any non-standard enumerable properties of
 * attributes are copied over to the fresh descriptor.
 *
 * If attributes is undefined, returns undefined.
 *
 * See also: http://wiki.ecmascript.org/doku.php?id=harmony:proxies_semantics
 */
function normalizeAndCompletePropertyDescriptor(attributes) {
  if (attributes === undefined) { return undefined; }
  var desc = toCompletePropertyDescriptor(attributes);
  // Note: no need to call FromPropertyDescriptor(desc), as we represent
  // "internal" property descriptors as proper Objects from the start
  for (var name in attributes) {
    if (!isStandardAttribute(name)) {
      Object.defineProperty(desc, name,
        { value: attributes[name],
          writable: true,
          enumerable: true,
          configurable: true });
    }
  }
  return desc;
}

/**
 * Returns a fresh property descriptor whose standard
 * attributes are guaranteed to be data properties of the right type.
 * Additionally, any non-standard enumerable properties of
 * attributes are copied over to the fresh descriptor.
 *
 * If attributes is undefined, will throw a TypeError.
 *
 * See also: http://wiki.ecmascript.org/doku.php?id=harmony:proxies_semantics
 */
function normalizePropertyDescriptor(attributes) {
  var desc = toPropertyDescriptor(attributes);
  // Note: no need to call FromGenericPropertyDescriptor(desc), as we represent
  // "internal" property descriptors as proper Objects from the start
  for (var name in attributes) {
    if (!isStandardAttribute(name)) {
      Object.defineProperty(desc, name,
        { value: attributes[name],
          writable: true,
          enumerable: true,
          configurable: true });
    }
  }
  return desc;
}

// store a reference to the real ES5 primitives before patching them later
var prim_preventExtensions =        Object.preventExtensions,
    prim_seal =                     Object.seal,
    prim_freeze =                   Object.freeze,
    prim_isExtensible =             Object.isExtensible,
    prim_isSealed =                 Object.isSealed,
    prim_isFrozen =                 Object.isFrozen,
    prim_getPrototypeOf =           Object.getPrototypeOf,
    prim_getOwnPropertyDescriptor = Object.getOwnPropertyDescriptor,
    prim_defineProperty =           Object.defineProperty,
    prim_defineProperties =         Object.defineProperties,
    prim_keys =                     Object.keys,
    prim_getOwnPropertyNames =      Object.getOwnPropertyNames,
    prim_getOwnPropertySymbols =    Object.getOwnPropertySymbols,
    prim_assign =                   Object.assign,
    prim_isArray =                  Array.isArray,
    prim_concat =                   Array.prototype.concat,
    prim_isPrototypeOf =            Object.prototype.isPrototypeOf,
    prim_hasOwnProperty =           Object.prototype.hasOwnProperty;

// these will point to the patched versions of the respective methods on
// Object. They are used within this module as the "intrinsic" bindings
// of these methods (i.e. the "original" bindings as defined in the spec)
var Object_isFrozen,
    Object_isSealed,
    Object_isExtensible,
    Object_getPrototypeOf,
    Object_getOwnPropertyNames;

/**
 * A property 'name' is fixed if it is an own property of the target.
 */
function isFixed(name, target) {
  return ({}).hasOwnProperty.call(target, name);
}
function isSealed(name, target) {
  var desc = Object.getOwnPropertyDescriptor(target, name);
  if (desc === undefined) { return false; }
  return desc.configurable === false;
}
function isSealedDesc(desc) {
  return desc !== undefined && desc.configurable === false;
}

/**
 * Performs all validation that Object.defineProperty performs,
 * without actually defining the property. Returns a boolean
 * indicating whether validation succeeded.
 *
 * Implementation transliterated from ES5.1 section 8.12.9
 */
function isCompatibleDescriptor(extensible, current, desc) {
  if (current === undefined && extensible === false) {
    return false;
  }
  if (current === undefined && extensible === true) {
    return true;
  }
  if (isEmptyDescriptor(desc)) {
    return true;
  }
  if (isEquivalentDescriptor(current, desc)) {
    return true;
  }
  if (current.configurable === false) {
    if (desc.configurable === true) {
      return false;
    }
    if ('enumerable' in desc && desc.enumerable !== current.enumerable) {
      return false;
    }
  }
  if (isGenericDescriptor(desc)) {
    return true;
  }
  if (isDataDescriptor(current) !== isDataDescriptor(desc)) {
    if (current.configurable === false) {
      return false;
    }
    return true;
  }
  if (isDataDescriptor(current) && isDataDescriptor(desc)) {
    if (current.configurable === false) {
      if (current.writable === false && desc.writable === true) {
        return false;
      }
      if (current.writable === false) {
        if ('value' in desc && !sameValue(desc.value, current.value)) {
          return false;
        }
      }
    }
    return true;
  }
  if (isAccessorDescriptor(current) && isAccessorDescriptor(desc)) {
    if (current.configurable === false) {
      if ('set' in desc && !sameValue(desc.set, current.set)) {
        return false;
      }
      if ('get' in desc && !sameValue(desc.get, current.get)) {
        return false;
      }
    }
  }
  return true;
}

// ES6 7.3.11 SetIntegrityLevel
// level is one of "sealed" or "frozen"
function setIntegrityLevel(target, level) {
  var ownProps = Object_getOwnPropertyNames(target);
  var pendingException = undefined;
  if (level === "sealed") {
    var l = +ownProps.length;
    var k;
    for (var i = 0; i < l; i++) {
      k = String(ownProps[i]);
      try {
        Object.defineProperty(target, k, { configurable: false });
      } catch (e) {
        if (pendingException === undefined) {
          pendingException = e;
        }
      }
    }
  } else {
    // level === "frozen"
    var l = +ownProps.length;
    var k;
    for (var i = 0; i < l; i++) {
      k = String(ownProps[i]);
      try {
        var currentDesc = Object.getOwnPropertyDescriptor(target, k);
        if (currentDesc !== undefined) {
          var desc;
          if (isAccessorDescriptor(currentDesc)) {
            desc = { configurable: false }
          } else {
            desc = { configurable: false, writable: false }
          }
          Object.defineProperty(target, k, desc);
        }        
      } catch (e) {
        if (pendingException === undefined) {
          pendingException = e;
        }
      }
    }
  }
  if (pendingException !== undefined) {
    throw pendingException;
  }
  return Reflect.preventExtensions(target);
}

// ES6 7.3.12 TestIntegrityLevel
// level is one of "sealed" or "frozen"
function testIntegrityLevel(target, level) {
  var isExtensible = Object_isExtensible(target);
  if (isExtensible) return false;
  
  var ownProps = Object_getOwnPropertyNames(target);
  var pendingException = undefined;
  var configurable = false;
  var writable = false;
  
  var l = +ownProps.length;
  var k;
  var currentDesc;
  for (var i = 0; i < l; i++) {
    k = String(ownProps[i]);
    try {
      currentDesc = Object.getOwnPropertyDescriptor(target, k);
      configurable = configurable || currentDesc.configurable;
      if (isDataDescriptor(currentDesc)) {
        writable = writable || currentDesc.writable;
      }
    } catch (e) {
      if (pendingException === undefined) {
        pendingException = e;
        configurable = true;
      }
    }
  }
  if (pendingException !== undefined) {
    throw pendingException;
  }
  if (level === "frozen" && writable === true) {
    return false;
  }
  if (configurable === true) {
    return false;
  }
  return true;
}

// ---- The Validator handler wrapper around user handlers ----

/**
 * @param target the object wrapped by this proxy.
 * As long as the proxy is extensible, only non-configurable properties
 * are checked against the target. Once the proxy becomes non-extensible,
 * invariants w.r.t. non-extensibility are also enforced.
 *
 * @param handler the handler of the direct proxy. The object emulated by
 * this handler is validated against the target object of the direct proxy.
 * Any violations that the handler makes against the invariants
 * of the target will cause a TypeError to be thrown.
 *
 * Both target and handler must be proper Objects at initialization time.
 */
function Validator(target, handler) {
  // for non-revokable proxies, these are const references
  // for revokable proxies, on revocation:
  // - this.target is set to null
  // - this.handler is set to a handler that throws on all traps
  this.target  = target;
  this.handler = handler;
}

Validator.prototype = {

  /**
   * If getTrap returns undefined, the caller should perform the
   * default forwarding behavior.
   * If getTrap returns normally otherwise, the return value
   * will be a callable trap function. When calling the trap function,
   * the caller is responsible for binding its |this| to |this.handler|.
   */
  getTrap: function(trapName) {
    var trap = this.handler[trapName];
    if (trap === undefined) {
      // the trap was not defined,
      // perform the default forwarding behavior
      return undefined;
    }

    if (typeof trap !== "function") {
      throw new TypeError(trapName + " trap is not callable: "+trap);
    }

    return trap;
  },

  // === fundamental traps ===

  /**
   * If name denotes a fixed property, check:
   *   - whether targetHandler reports it as existent
   *   - whether the returned descriptor is compatible with the fixed property
   * If the proxy is non-extensible, check:
   *   - whether name is not a new property
   * Additionally, the returned descriptor is normalized and completed.
   */
  getOwnPropertyDescriptor: function(name) {
    "use strict";

    var trap = this.getTrap("getOwnPropertyDescriptor");
    if (trap === undefined) {
      return Reflect.getOwnPropertyDescriptor(this.target, name);
    }

    name = String(name);
    var desc = trap.call(this.handler, this.target, name);
    desc = normalizeAndCompletePropertyDescriptor(desc);

    var targetDesc = Object.getOwnPropertyDescriptor(this.target, name);
    var extensible = Object.isExtensible(this.target);

    if (desc === undefined) {
      if (isSealedDesc(targetDesc)) {
        throw new TypeError("cannot report non-configurable property '"+name+
                            "' as non-existent");
      }
      if (!extensible && targetDesc !== undefined) {
          // if handler is allowed to return undefined, we cannot guarantee
          // that it will not return a descriptor for this property later.
          // Once a property has been reported as non-existent on a non-extensible
          // object, it should forever be reported as non-existent
          throw new TypeError("cannot report existing own property '"+name+
                              "' as non-existent on a non-extensible object");
      }
      return undefined;
    }

    // at this point, we know (desc !== undefined), i.e.
    // targetHandler reports 'name' as an existing property

    // Note: we could collapse the following two if-tests into a single
    // test. Separating out the cases to improve error reporting.

    if (!extensible) {
      if (targetDesc === undefined) {
        throw new TypeError("cannot report a new own property '"+
                            name + "' on a non-extensible object");
      }
    }

    if (name !== undefined) {
      if (!isCompatibleDescriptor(extensible, targetDesc, desc)) {
        throw new TypeError("cannot report incompatible property descriptor "+
                            "for property '"+name+"'");
      }
    }
    
    if (desc.configurable === false) {
      if (targetDesc === undefined || targetDesc.configurable === true) {
        // if the property is configurable or non-existent on the target,
        // but is reported as a non-configurable property, it may later be
        // reported as configurable or non-existent, which violates the
        // invariant that if the property might change or disappear, the
        // configurable attribute must be true.
        throw new TypeError(
          "cannot report a non-configurable descriptor " +
          "for configurable or non-existent property '" + name + "'");
      }
      if ('writable' in desc && desc.writable === false) {
        if (targetDesc.writable === true) {
          // if the property is non-configurable, writable on the target,
          // but is reported as non-configurable, non-writable, it may later
          // be reported as non-configurable, writable again, which violates
          // the invariant that a non-configurable, non-writable property
          // may not change state.
          throw new TypeError(
            "cannot report non-configurable, writable property '" + name +
            "' as non-configurable, non-writable");
        }
      }
    }

    return desc;
  },

  /**
   * In the direct proxies design with refactored prototype climbing,
   * this trap is deprecated. For proxies-as-prototypes, instead
   * of calling this trap, the get, set, has or enumerate traps are
   * called instead.
   *
   * In this implementation, we "abuse" getPropertyDescriptor to
   * support trapping the get or set traps for proxies-as-prototypes.
   * We do this by returning a getter/setter pair that invokes
   * the corresponding traps.
   *
   * While this hack works for inherited property access, it has some
   * quirks:
   *
   * In Firefox, this trap is only called after a prior invocation
   * of the 'has' trap has returned true. Hence, expect the following
   * behavior:
   * <code>
   * var child = Object.create(Proxy(target, handler));
   * child[name] // triggers handler.has(target, name)
   * // if that returns true, triggers handler.get(target, name, child)
   * </code>
   *
   * On v8, the 'in' operator, when applied to an object that inherits
   * from a proxy, will call getPropertyDescriptor and walk the proto-chain.
   * That calls the below getPropertyDescriptor trap on the proxy. The
   * result of the 'in'-operator is then determined by whether this trap
   * returns undefined or a property descriptor object. That is why
   * we first explicitly trigger the 'has' trap to determine whether
   * the property exists.
   *
   * This has the side-effect that when enumerating properties on
   * an object that inherits from a proxy in v8, only properties
   * for which 'has' returns true are returned:
   *
   * <code>
   * var child = Object.create(Proxy(target, handler));
   * for (var prop in child) {
   *   // only enumerates prop if (prop in child) returns true
   * }
   * </code>
   */
  getPropertyDescriptor: function(name) {
    var handler = this;

    if (!handler.has(name)) return undefined;

    return {
      get: function() {
        return handler.get(this, name);
      },
      set: function(val) {
        if (handler.set(this, name, val)) {
          return val;
        } else {
          throw new TypeError("failed assignment to "+name);
        }
      },
      enumerable: true,
      configurable: true
    };
  },

  /**
   * If name denotes a fixed property, check for incompatible changes.
   * If the proxy is non-extensible, check that new properties are rejected.
   */
  defineProperty: function(name, desc) {
    // TODO(tvcutsem): the current tracemonkey implementation of proxies
    // auto-completes 'desc', which is not correct. 'desc' should be
    // normalized, but not completed. Consider:
    // Object.defineProperty(proxy, 'foo', {enumerable:false})
    // This trap will receive desc =
    //  {value:undefined,writable:false,enumerable:false,configurable:false}
    // This will also set all other attributes to their default value,
    // which is unexpected and different from [[DefineOwnProperty]].
    // Bug filed: https://bugzilla.mozilla.org/show_bug.cgi?id=601329

    var trap = this.getTrap("defineProperty");
    if (trap === undefined) {
      // default forwarding behavior
      return Reflect.defineProperty(this.target, name, desc);
    }

    name = String(name);
    var descObj = normalizePropertyDescriptor(desc);
    var success = trap.call(this.handler, this.target, name, descObj);
    success = !!success; // coerce to Boolean

    if (success === true) {

      var targetDesc = Object.getOwnPropertyDescriptor(this.target, name);
      var extensible = Object.isExtensible(this.target);

      // Note: we could collapse the following two if-tests into a single
      // test. Separating out the cases to improve error reporting.

      if (!extensible) {
        if (targetDesc === undefined) {
          throw new TypeError("cannot successfully add a new property '"+
                              name + "' to a non-extensible object");
        }
      }

      if (targetDesc !== undefined) {
        if (!isCompatibleDescriptor(extensible, targetDesc, desc)) {
          throw new TypeError("cannot define incompatible property "+
                              "descriptor for property '"+name+"'");
        }
        if (isDataDescriptor(targetDesc) &&
            targetDesc.configurable === false &&
            targetDesc.writable === true) {
            if (desc.configurable === false && desc.writable === false) {
              // if the property is non-configurable, writable on the target
              // but was successfully reported to be updated to
              // non-configurable, non-writable, it can later be reported
              // again as non-configurable, writable, which violates
              // the invariant that non-configurable, non-writable properties
              // cannot change state
              throw new TypeError(
                "cannot successfully define non-configurable, writable " +
                " property '" + name + "' as non-configurable, non-writable");
            }
          }
      }

      if (desc.configurable === false && !isSealedDesc(targetDesc)) {
        // if the property is configurable or non-existent on the target,
        // but is successfully being redefined as a non-configurable property,
        // it may later be reported as configurable or non-existent, which violates
        // the invariant that if the property might change or disappear, the
        // configurable attribute must be true.
        throw new TypeError(
          "cannot successfully define a non-configurable " +
          "descriptor for configurable or non-existent property '" +
          name + "'");
      }

    }

    return success;
  },

  /**
   * On success, check whether the target object is indeed non-extensible.
   */
  preventExtensions: function() {
    var trap = this.getTrap("preventExtensions");
    if (trap === undefined) {
      // default forwarding behavior
      return Reflect.preventExtensions(this.target);
    }

    var success = trap.call(this.handler, this.target);
    success = !!success; // coerce to Boolean
    if (success) {
      if (Object_isExtensible(this.target)) {
        throw new TypeError("can't report extensible object as non-extensible: "+
                            this.target);
      }
    }
    return success;
  },

  /**
   * If name denotes a sealed property, check whether handler rejects.
   */
  delete: function(name) {
    "use strict";
    var trap = this.getTrap("deleteProperty");
    if (trap === undefined) {
      // default forwarding behavior
      return Reflect.deleteProperty(this.target, name);
    }

    name = String(name);
    var res = trap.call(this.handler, this.target, name);
    res = !!res; // coerce to Boolean

    var targetDesc;
    if (res === true) {
      targetDesc = Object.getOwnPropertyDescriptor(this.target, name);
      if (targetDesc !== undefined && targetDesc.configurable === false) {
        throw new TypeError("property '" + name + "' is non-configurable "+
                            "and can't be deleted");
      }
      if (targetDesc !== undefined && !Object_isExtensible(this.target)) {
        // if the property still exists on a non-extensible target but
        // is reported as successfully deleted, it may later be reported
        // as present, which violates the invariant that an own property,
        // deleted from a non-extensible object cannot reappear.
        throw new TypeError(
          "cannot successfully delete existing property '" + name +
          "' on a non-extensible object");
      }
    }

    return res;
  },

  /**
   * The getOwnPropertyNames trap was replaced by the ownKeys trap,
   * which now also returns an array (of strings or symbols) and
   * which performs the same rigorous invariant checks as getOwnPropertyNames
   *
   * See issue #48 on how this trap can still get invoked by external libs
   * that don't use the patched Object.getOwnPropertyNames function.
   */
  getOwnPropertyNames: function() {
    // Note: removed deprecation warning to avoid dependency on 'console'
    // (and on node, should anyway use util.deprecate). Deprecation warnings
    // can also be annoying when they are outside of the user's control, e.g.
    // when an external library calls unpatched Object.getOwnPropertyNames.
    // Since there is a clean fallback to `ownKeys`, the fact that the
    // deprecated method is still called is mostly harmless anyway.
    // See also issues #65 and #66.
    // console.warn("getOwnPropertyNames trap is deprecated. Use ownKeys instead");
    return this.ownKeys();
  },

  /**
   * Checks whether the trap result does not contain any new properties
   * if the proxy is non-extensible.
   *
   * Any own non-configurable properties of the target that are not included
   * in the trap result give rise to a TypeError. As such, we check whether the
   * returned result contains at least all sealed properties of the target
   * object.
   *
   * Additionally, the trap result is normalized.
   * Instead of returning the trap result directly:
   *  - create and return a fresh Array,
   *  - of which each element is coerced to a String
   *
   * This trap is called a.o. by Reflect.ownKeys, Object.getOwnPropertyNames
   * and Object.keys (the latter filters out only the enumerable own properties).
   */
  ownKeys: function() {
    var trap = this.getTrap("ownKeys");
    if (trap === undefined) {
      // default forwarding behavior
      return Reflect.ownKeys(this.target);
    }

    var trapResult = trap.call(this.handler, this.target);

    // propNames is used as a set of strings
    var propNames = Object.create(null);
    var numProps = +trapResult.length;
    var result = new Array(numProps);

    for (var i = 0; i < numProps; i++) {
      var s = String(trapResult[i]);
      if (!Object.isExtensible(this.target) && !isFixed(s, this.target)) {
        // non-extensible proxies don't tolerate new own property names
        throw new TypeError("ownKeys trap cannot list a new "+
                            "property '"+s+"' on a non-extensible object");
      }

      propNames[s] = true;
      result[i] = s;
    }

    var ownProps = Object_getOwnPropertyNames(this.target);
    var target = this.target;
    ownProps.forEach(function (ownProp) {
      if (!propNames[ownProp]) {
        if (isSealed(ownProp, target)) {
          throw new TypeError("ownKeys trap failed to include "+
                              "non-configurable property '"+ownProp+"'");
        }
        if (!Object.isExtensible(target) &&
            isFixed(ownProp, target)) {
            // if handler is allowed to report ownProp as non-existent,
            // we cannot guarantee that it will never later report it as
            // existent. Once a property has been reported as non-existent
            // on a non-extensible object, it should forever be reported as
            // non-existent
            throw new TypeError("ownKeys trap cannot report existing own property '"+
                                ownProp+"' as non-existent on a non-extensible object");
        }
      }
    });

    return result;
  },

  /**
   * Checks whether the trap result is consistent with the state of the
   * wrapped target.
   */
  isExtensible: function() {
    var trap = this.getTrap("isExtensible");
    if (trap === undefined) {
      // default forwarding behavior
      return Reflect.isExtensible(this.target);
    }

    var result = trap.call(this.handler, this.target);
    result = !!result; // coerce to Boolean
    var state = Object_isExtensible(this.target);
    if (result !== state) {
      if (result) {
        throw new TypeError("cannot report non-extensible object as extensible: "+
                             this.target);
      } else {
        throw new TypeError("cannot report extensible object as non-extensible: "+
                             this.target);
      }
    }
    return state;
  },

  /**
   * Check whether the trap result corresponds to the target's [[Prototype]]
   */
  getPrototypeOf: function() {
    var trap = this.getTrap("getPrototypeOf");
    if (trap === undefined) {
      // default forwarding behavior
      return Reflect.getPrototypeOf(this.target);
    }

    var allegedProto = trap.call(this.handler, this.target);

    if (!Object_isExtensible(this.target)) {
      var actualProto = Object_getPrototypeOf(this.target);
      if (!sameValue(allegedProto, actualProto)) {
        throw new TypeError("prototype value does not match: " + this.target);
      }
    }

    return allegedProto;
  },

  /**
   * If target is non-extensible and setPrototypeOf trap returns true,
   * check whether the trap result corresponds to the target's [[Prototype]]
   */
  setPrototypeOf: function(newProto) {
    var trap = this.getTrap("setPrototypeOf");
    if (trap === undefined) {
      // default forwarding behavior
      return Reflect.setPrototypeOf(this.target, newProto);
    }

    var success = trap.call(this.handler, this.target, newProto);

    success = !!success;
    if (success && !Object_isExtensible(this.target)) {
      var actualProto = Object_getPrototypeOf(this.target);
      if (!sameValue(newProto, actualProto)) {
        throw new TypeError("prototype value does not match: " + this.target);
      }
    }

    return success;
  },

  /**
   * In the direct proxies design with refactored prototype climbing,
   * this trap is deprecated. For proxies-as-prototypes, for-in will
   * call the enumerate() trap. If that trap is not defined, the
   * operation is forwarded to the target, no more fallback on this
   * fundamental trap.
   */
  getPropertyNames: function() {
    throw new TypeError("getPropertyNames trap is deprecated");
  },

  // === derived traps ===

  /**
   * If name denotes a fixed property, check whether the trap returns true.
   */
  has: function(name) {
    var trap = this.getTrap("has");
    if (trap === undefined) {
      // default forwarding behavior
      return Reflect.has(this.target, name);
    }

    name = String(name);
    var res = trap.call(this.handler, this.target, name);
    res = !!res; // coerce to Boolean

    if (res === false) {
      if (isSealed(name, this.target)) {
        throw new TypeError("cannot report existing non-configurable own "+
                            "property '"+ name + "' as a non-existent "+
                            "property");
      }
      if (!Object.isExtensible(this.target) &&
          isFixed(name, this.target)) {
          // if handler is allowed to return false, we cannot guarantee
          // that it will not return true for this property later.
          // Once a property has been reported as non-existent on a non-extensible
          // object, it should forever be reported as non-existent
          throw new TypeError("cannot report existing own property '"+name+
                              "' as non-existent on a non-extensible object");
      }
    }

    // if res === true, we don't need to check for extensibility
    // even for a non-extensible proxy that has no own name property,
    // the property may have been inherited

    return res;
  },

  /**
   * If name denotes a fixed non-configurable, non-writable data property,
   * check its return value against the previously asserted value of the
   * fixed property.
   */
  get: function(receiver, name) {

    // experimental support for invoke() trap on platforms that
    // support __noSuchMethod__
    /*
    if (name === '__noSuchMethod__') {
      var handler = this;
      return function(name, args) {
        return handler.invoke(receiver, name, args);
      }
    }
    */

    var trap = this.getTrap("get");
    if (trap === undefined) {
      // default forwarding behavior
      return Reflect.get(this.target, name, receiver);
    }

    name = String(name);
    var res = trap.call(this.handler, this.target, name, receiver);

    var fixedDesc = Object.getOwnPropertyDescriptor(this.target, name);
    // check consistency of the returned value
    if (fixedDesc !== undefined) { // getting an existing property
      if (isDataDescriptor(fixedDesc) &&
          fixedDesc.configurable === false &&
          fixedDesc.writable === false) { // own frozen data property
        if (!sameValue(res, fixedDesc.value)) {
          throw new TypeError("cannot report inconsistent value for "+
                              "non-writable, non-configurable property '"+
                              name+"'");
        }
      } else { // it's an accessor property
        if (isAccessorDescriptor(fixedDesc) &&
            fixedDesc.configurable === false &&
            fixedDesc.get === undefined) {
          if (res !== undefined) {
            throw new TypeError("must report undefined for non-configurable "+
                                "accessor property '"+name+"' without getter");
          }
        }
      }
    }

    return res;
  },

  /**
   * If name denotes a fixed non-configurable, non-writable data property,
   * check that the trap rejects the assignment.
   */
  set: function(receiver, name, val) {
    var trap = this.getTrap("set");
    if (trap === undefined) {
      // default forwarding behavior
      return Reflect.set(this.target, name, val, receiver);
    }

    name = String(name);
    var res = trap.call(this.handler, this.target, name, val, receiver);
    res = !!res; // coerce to Boolean

    // if success is reported, check whether property is truly assignable
    if (res === true) {
      var fixedDesc = Object.getOwnPropertyDescriptor(this.target, name);
      if (fixedDesc !== undefined) { // setting an existing property
        if (isDataDescriptor(fixedDesc) &&
            fixedDesc.configurable === false &&
            fixedDesc.writable === false) {
          if (!sameValue(val, fixedDesc.value)) {
            throw new TypeError("cannot successfully assign to a "+
                                "non-writable, non-configurable property '"+
                                name+"'");
          }
        } else {
          if (isAccessorDescriptor(fixedDesc) &&
              fixedDesc.configurable === false && // non-configurable
              fixedDesc.set === undefined) {      // accessor with undefined setter
            throw new TypeError("setting a property '"+name+"' that has "+
                                " only a getter");
          }
        }
      }
    }

    return res;
  },

  /**
   * Any own enumerable non-configurable properties of the target that are not
   * included in the trap result give rise to a TypeError. As such, we check
   * whether the returned result contains at least all sealed enumerable properties
   * of the target object.
   *
   * The trap should return an iterator.
   *
   * However, as implementations of pre-direct proxies still expect enumerate
   * to return an array of strings, we convert the iterator into an array.
   */
  enumerate: function() {
    var trap = this.getTrap("enumerate");
    if (trap === undefined) {
      // default forwarding behavior
      var trapResult = Reflect.enumerate(this.target);
      var result = [];
      var nxt = trapResult.next();
      while (!nxt.done) {
        result.push(String(nxt.value));
        nxt = trapResult.next();
      }
      return result;
    }

    var trapResult = trap.call(this.handler, this.target);
    
    if (trapResult === null ||
        trapResult === undefined ||
        trapResult.next === undefined) {
      throw new TypeError("enumerate trap should return an iterator, got: "+
                          trapResult);    
    }
    
    // propNames is used as a set of strings
    var propNames = Object.create(null);
    
    // var numProps = +trapResult.length;
    var result = []; // new Array(numProps);
    
    // trapResult is supposed to be an iterator
    // drain iterator to array as current implementations still expect
    // enumerate to return an array of strings
    var nxt = trapResult.next();
    
    while (!nxt.done) {
      var s = String(nxt.value);
      if (propNames[s]) {
        throw new TypeError("enumerate trap cannot list a "+
                            "duplicate property '"+s+"'");
      }
      propNames[s] = true;
      result.push(s);
      nxt = trapResult.next();
    }
    
    /*for (var i = 0; i < numProps; i++) {
      var s = String(trapResult[i]);
      if (propNames[s]) {
        throw new TypeError("enumerate trap cannot list a "+
                            "duplicate property '"+s+"'");
      }

      propNames[s] = true;
      result[i] = s;
    } */

    var ownEnumerableProps = Object.keys(this.target);
    var target = this.target;
    ownEnumerableProps.forEach(function (ownEnumerableProp) {
      if (!propNames[ownEnumerableProp]) {
        if (isSealed(ownEnumerableProp, target)) {
          throw new TypeError("enumerate trap failed to include "+
                              "non-configurable enumerable property '"+
                              ownEnumerableProp+"'");
        }
        if (!Object.isExtensible(target) &&
            isFixed(ownEnumerableProp, target)) {
            // if handler is allowed not to report ownEnumerableProp as an own
            // property, we cannot guarantee that it will never report it as
            // an own property later. Once a property has been reported as
            // non-existent on a non-extensible object, it should forever be
            // reported as non-existent
            throw new TypeError("cannot report existing own property '"+
                                ownEnumerableProp+"' as non-existent on a "+
                                "non-extensible object");
        }
      }
    });

    return result;
  },

  /**
   * The iterate trap is deprecated by the enumerate trap.
   */
  iterate: Validator.prototype.enumerate,

  /**
   * Any own non-configurable properties of the target that are not included
   * in the trap result give rise to a TypeError. As such, we check whether the
   * returned result contains at least all sealed properties of the target
   * object.
   *
   * The trap result is normalized.
   * The trap result is not returned directly. Instead:
   *  - create and return a fresh Array,
   *  - of which each element is coerced to String,
   *  - which does not contain duplicates
   *
   * FIXME: keys trap is deprecated
   */
  /*
  keys: function() {
    var trap = this.getTrap("keys");
    if (trap === undefined) {
      // default forwarding behavior
      return Reflect.keys(this.target);
    }

    var trapResult = trap.call(this.handler, this.target);

    // propNames is used as a set of strings
    var propNames = Object.create(null);
    var numProps = +trapResult.length;
    var result = new Array(numProps);

    for (var i = 0; i < numProps; i++) {
     var s = String(trapResult[i]);
     if (propNames[s]) {
       throw new TypeError("keys trap cannot list a "+
                           "duplicate property '"+s+"'");
     }
     if (!Object.isExtensible(this.target) && !isFixed(s, this.target)) {
       // non-extensible proxies don't tolerate new own property names
       throw new TypeError("keys trap cannot list a new "+
                           "property '"+s+"' on a non-extensible object");
     }

     propNames[s] = true;
     result[i] = s;
    }

    var ownEnumerableProps = Object.keys(this.target);
    var target = this.target;
    ownEnumerableProps.forEach(function (ownEnumerableProp) {
      if (!propNames[ownEnumerableProp]) {
        if (isSealed(ownEnumerableProp, target)) {
          throw new TypeError("keys trap failed to include "+
                              "non-configurable enumerable property '"+
                              ownEnumerableProp+"'");
        }
        if (!Object.isExtensible(target) &&
            isFixed(ownEnumerableProp, target)) {
            // if handler is allowed not to report ownEnumerableProp as an own
            // property, we cannot guarantee that it will never report it as
            // an own property later. Once a property has been reported as
            // non-existent on a non-extensible object, it should forever be
            // reported as non-existent
            throw new TypeError("cannot report existing own property '"+
                                ownEnumerableProp+"' as non-existent on a "+
                                "non-extensible object");
        }
      }
    });

    return result;
  },
  */
  
  /**
   * New trap that reifies [[Call]].
   * If the target is a function, then a call to
   *   proxy(...args)
   * Triggers this trap
   */
  apply: function(target, thisBinding, args) {
    var trap = this.getTrap("apply");
    if (trap === undefined) {
      return Reflect.apply(target, thisBinding, args);
    }

    if (typeof this.target === "function") {
      return trap.call(this.handler, target, thisBinding, args);
    } else {
      throw new TypeError("apply: "+ target + " is not a function");
    }
  },

  /**
   * New trap that reifies [[Construct]].
   * If the target is a function, then a call to
   *   new proxy(...args)
   * Triggers this trap
   */
  construct: function(target, args, newTarget) {
    var trap = this.getTrap("construct");
    if (trap === undefined) {
      return Reflect.construct(target, args, newTarget);
    }

    if (typeof target !== "function") {
      throw new TypeError("new: "+ target + " is not a function");
    }

    if (newTarget === undefined) {
      newTarget = target;
    } else {
      if (typeof newTarget !== "function") {
        throw new TypeError("new: "+ newTarget + " is not a function");
      }      
    }
    return trap.call(this.handler, target, args, newTarget);
  }
};

// ---- end of the Validator handler wrapper handler ----

// In what follows, a 'direct proxy' is a proxy
// whose handler is a Validator. Such proxies can be made non-extensible,
// sealed or frozen without losing the ability to trap.

// maps direct proxies to their Validator handlers
var directProxies = new WeakMap();

// patch Object.{preventExtensions,seal,freeze} so that
// they recognize fixable proxies and act accordingly
Object.preventExtensions = function(subject) {
  var vhandler = directProxies.get(subject);
  if (vhandler !== undefined) {
    if (vhandler.preventExtensions()) {
      return subject;
    } else {
      throw new TypeError("preventExtensions on "+subject+" rejected");
    }
  } else {
    return prim_preventExtensions(subject);
  }
};
Object.seal = function(subject) {
  setIntegrityLevel(subject, "sealed");
  return subject;
};
Object.freeze = function(subject) {
  setIntegrityLevel(subject, "frozen");
  return subject;
};
Object.isExtensible = Object_isExtensible = function(subject) {
  var vHandler = directProxies.get(subject);
  if (vHandler !== undefined) {
    return vHandler.isExtensible();
  } else {
    return prim_isExtensible(subject);
  }
};
Object.isSealed = Object_isSealed = function(subject) {
  return testIntegrityLevel(subject, "sealed");
};
Object.isFrozen = Object_isFrozen = function(subject) {
  return testIntegrityLevel(subject, "frozen");
};
Object.getPrototypeOf = Object_getPrototypeOf = function(subject) {
  var vHandler = directProxies.get(subject);
  if (vHandler !== undefined) {
    return vHandler.getPrototypeOf();
  } else {
    return prim_getPrototypeOf(subject);
  }
};

// patch Object.getOwnPropertyDescriptor to directly call
// the Validator.prototype.getOwnPropertyDescriptor trap
// This is to circumvent an assertion in the built-in Proxy
// trapping mechanism of v8, which disallows that trap to
// return non-configurable property descriptors (as per the
// old Proxy design)
Object.getOwnPropertyDescriptor = function(subject, name) {
  var vhandler = directProxies.get(subject);
  if (vhandler !== undefined) {
    return vhandler.getOwnPropertyDescriptor(name);
  } else {
    return prim_getOwnPropertyDescriptor(subject, name);
  }
};

// patch Object.defineProperty to directly call
// the Validator.prototype.defineProperty trap
// This is to circumvent two issues with the built-in
// trap mechanism:
// 1) the current tracemonkey implementation of proxies
// auto-completes 'desc', which is not correct. 'desc' should be
// normalized, but not completed. Consider:
// Object.defineProperty(proxy, 'foo', {enumerable:false})
// This trap will receive desc =
//  {value:undefined,writable:false,enumerable:false,configurable:false}
// This will also set all other attributes to their default value,
// which is unexpected and different from [[DefineOwnProperty]].
// Bug filed: https://bugzilla.mozilla.org/show_bug.cgi?id=601329
// 2) the current spidermonkey implementation does not
// throw an exception when this trap returns 'false', but instead silently
// ignores the operation (this is regardless of strict-mode)
// 2a) v8 does throw an exception for this case, but includes the rather
//     unhelpful error message:
// 'Proxy handler #<Object> returned false from 'defineProperty' trap'
Object.defineProperty = function(subject, name, desc) {
  var vhandler = directProxies.get(subject);
  if (vhandler !== undefined) {
    var normalizedDesc = normalizePropertyDescriptor(desc);
    var success = vhandler.defineProperty(name, normalizedDesc);
    if (success === false) {
      throw new TypeError("can't redefine property '"+name+"'");
    }
    return subject;
  } else {
    return prim_defineProperty(subject, name, desc);
  }
};

Object.defineProperties = function(subject, descs) {
  var vhandler = directProxies.get(subject);
  if (vhandler !== undefined) {
    var names = Object.keys(descs);
    for (var i = 0; i < names.length; i++) {
      var name = names[i];
      var normalizedDesc = normalizePropertyDescriptor(descs[name]);
      var success = vhandler.defineProperty(name, normalizedDesc);
      if (success === false) {
        throw new TypeError("can't redefine property '"+name+"'");
      }
    }
    return subject;
  } else {
    return prim_defineProperties(subject, descs);
  }
};

Object.keys = function(subject) {
  var vHandler = directProxies.get(subject);
  if (vHandler !== undefined) {
    var ownKeys = vHandler.ownKeys();
    var result = [];
    for (var i = 0; i < ownKeys.length; i++) {
      var k = String(ownKeys[i]);
      var desc = Object.getOwnPropertyDescriptor(subject, k);
      if (desc !== undefined && desc.enumerable === true) {
        result.push(k);
      }
    }
    return result;
  } else {
    return prim_keys(subject);
  }
}

Object.getOwnPropertyNames = Object_getOwnPropertyNames = function(subject) {
  var vHandler = directProxies.get(subject);
  if (vHandler !== undefined) {
    return vHandler.ownKeys();
  } else {
    return prim_getOwnPropertyNames(subject);
  }
}

// fixes issue #71 (Calling Object.getOwnPropertySymbols() on a Proxy
// throws an error)
if (prim_getOwnPropertySymbols !== undefined) {
  Object.getOwnPropertySymbols = function(subject) {
    var vHandler = directProxies.get(subject);
    if (vHandler !== undefined) {
      // as this shim does not support symbols, a Proxy never advertises
      // any symbol-valued own properties
      return [];
    } else {
      return prim_getOwnPropertySymbols(subject);
    }
  };
}

// fixes issue #72 ('Illegal access' error when using Object.assign)
// Object.assign polyfill based on a polyfill posted on MDN: 
// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/\
//  Global_Objects/Object/assign
// Note that this polyfill does not support Symbols, but this Proxy Shim
// does not support Symbols anyway.
if (prim_assign !== undefined) {
  Object.assign = function (target) {
    
    // check if any argument is a proxy object
    var noProxies = true;
    for (var i = 0; i < arguments.length; i++) {
      var vHandler = directProxies.get(arguments[i]);
      if (vHandler !== undefined) {
        noProxies = false;
        break;
      }
    }
    if (noProxies) {
      // not a single argument is a proxy, perform built-in algorithm
      return prim_assign.apply(Object, arguments);
    }
    
    // there is at least one proxy argument, use the polyfill
    
    if (target === undefined || target === null) {
      throw new TypeError('Cannot convert undefined or null to object');
    }

    var output = Object(target);
    for (var index = 1; index < arguments.length; index++) {
      var source = arguments[index];
      if (source !== undefined && source !== null) {
        for (var nextKey in source) {
          if (source.hasOwnProperty(nextKey)) {
            output[nextKey] = source[nextKey];
          }
        }
      }
    }
    return output;
  };
}

// returns whether an argument is a reference to an object,
// which is legal as a WeakMap key.
function isObject(arg) {
  var type = typeof arg;
  return (type === 'object' && arg !== null) || (type === 'function');
};

// a wrapper for WeakMap.get which returns the undefined value
// for keys that are not objects (in which case the underlying
// WeakMap would have thrown a TypeError).
function safeWeakMapGet(map, key) {
  return isObject(key) ? map.get(key) : undefined;
};

// returns a new function of zero arguments that recursively
// unwraps any proxies specified as the |this|-value.
// The primitive is assumed to be a zero-argument method
// that uses its |this|-binding.
function makeUnwrapping0ArgMethod(primitive) {
  return function builtin() {
    var vHandler = safeWeakMapGet(directProxies, this);
    if (vHandler !== undefined) {
      return builtin.call(vHandler.target);
    } else {
      return primitive.call(this);
    }
  }
};

// returns a new function of 1 arguments that recursively
// unwraps any proxies specified as the |this|-value.
// The primitive is assumed to be a 1-argument method
// that uses its |this|-binding.
function makeUnwrapping1ArgMethod(primitive) {
  return function builtin(arg) {
    var vHandler = safeWeakMapGet(directProxies, this);
    if (vHandler !== undefined) {
      return builtin.call(vHandler.target, arg);
    } else {
      return primitive.call(this, arg);
    }
  }
};

Object.prototype.valueOf =
  makeUnwrapping0ArgMethod(Object.prototype.valueOf);
Object.prototype.toString =
  makeUnwrapping0ArgMethod(Object.prototype.toString);
Function.prototype.toString =
  makeUnwrapping0ArgMethod(Function.prototype.toString);
Date.prototype.toString =
  makeUnwrapping0ArgMethod(Date.prototype.toString);

Object.prototype.isPrototypeOf = function builtin(arg) {
  // bugfix thanks to Bill Mark:
  // built-in isPrototypeOf does not unwrap proxies used
  // as arguments. So, we implement the builtin ourselves,
  // based on the ECMAScript 6 spec. Our encoding will
  // make sure that if a proxy is used as an argument,
  // its getPrototypeOf trap will be called.
  while (true) {
    var vHandler2 = safeWeakMapGet(directProxies, arg);
    if (vHandler2 !== undefined) {
      arg = vHandler2.getPrototypeOf();
      if (arg === null) {
        return false;
      } else if (sameValue(arg, this)) {
        return true;
      }
    } else {
      return prim_isPrototypeOf.call(this, arg);
    }
  }
};

Array.isArray = function(subject) {
  var vHandler = safeWeakMapGet(directProxies, subject);
  if (vHandler !== undefined) {
    return Array.isArray(vHandler.target);
  } else {
    return prim_isArray(subject);
  }
};

function isProxyArray(arg) {
  var vHandler = safeWeakMapGet(directProxies, arg);
  if (vHandler !== undefined) {
    return Array.isArray(vHandler.target);
  }
  return false;
}

// Array.prototype.concat internally tests whether one of its
// arguments is an Array, by checking whether [[Class]] == "Array"
// As such, it will fail to recognize proxies-for-arrays as arrays.
// We patch Array.prototype.concat so that it "unwraps" proxies-for-arrays
// by making a copy. This will trigger the exact same sequence of
// traps on the proxy-for-array as if we would not have unwrapped it.
// See <https://github.com/tvcutsem/harmony-reflect/issues/19> for more.
Array.prototype.concat = function(/*...args*/) {
  var length;
  for (var i = 0; i < arguments.length; i++) {
    if (isProxyArray(arguments[i])) {
      length = arguments[i].length;
      arguments[i] = Array.prototype.slice.call(arguments[i], 0, length);
    }
  }
  return prim_concat.apply(this, arguments);
};

// setPrototypeOf support on platforms that support __proto__

var prim_setPrototypeOf = Object.setPrototypeOf;

// patch and extract original __proto__ setter
var __proto__setter = (function() {
  var protoDesc = prim_getOwnPropertyDescriptor(Object.prototype,'__proto__');
  if (protoDesc === undefined ||
      typeof protoDesc.set !== "function") {
    return function() {
      throw new TypeError("setPrototypeOf not supported on this platform");
    }
  }

  // see if we can actually mutate a prototype with the generic setter
  // (e.g. Chrome v28 doesn't allow setting __proto__ via the generic setter)
  try {
    protoDesc.set.call({},{});
  } catch (e) {
    return function() {
      throw new TypeError("setPrototypeOf not supported on this platform");
    }
  }

  prim_defineProperty(Object.prototype, '__proto__', {
    set: function(newProto) {
      return Object.setPrototypeOf(this, Object(newProto));
    }
  });

  return protoDesc.set;
}());

Object.setPrototypeOf = function(target, newProto) {
  var handler = directProxies.get(target);
  if (handler !== undefined) {
    if (handler.setPrototypeOf(newProto)) {
      return target;
    } else {
      throw new TypeError("proxy rejected prototype mutation");
    }
  } else {
    if (!Object_isExtensible(target)) {
      throw new TypeError("can't set prototype on non-extensible object: " +
                          target);
    }
    if (prim_setPrototypeOf)
      return prim_setPrototypeOf(target, newProto);

    if (Object(newProto) !== newProto || newProto === null) {
      throw new TypeError("Object prototype may only be an Object or null: " +
                         newProto);
      // throw new TypeError("prototype must be an object or null")
    }
    __proto__setter.call(target, newProto);
    return target;
  }
}

Object.prototype.hasOwnProperty = function(name) {
  var handler = safeWeakMapGet(directProxies, this);
  if (handler !== undefined) {
    var desc = handler.getOwnPropertyDescriptor(name);
    return desc !== undefined;
  } else {
    return prim_hasOwnProperty.call(this, name);
  }
}

// ============= Reflection module =============
// see http://wiki.ecmascript.org/doku.php?id=harmony:reflect_api

var Reflect = global.Reflect = {
  getOwnPropertyDescriptor: function(target, name) {
    return Object.getOwnPropertyDescriptor(target, name);
  },
  defineProperty: function(target, name, desc) {

    // if target is a proxy, invoke its "defineProperty" trap
    var handler = directProxies.get(target);
    if (handler !== undefined) {
      return handler.defineProperty(target, name, desc);
    }

    // Implementation transliterated from [[DefineOwnProperty]]
    // see ES5.1 section 8.12.9
    // this is the _exact same algorithm_ as the isCompatibleDescriptor
    // algorithm defined above, except that at every place it
    // returns true, this algorithm actually does define the property.
    var current = Object.getOwnPropertyDescriptor(target, name);
    var extensible = Object.isExtensible(target);
    if (current === undefined && extensible === false) {
      return false;
    }
    if (current === undefined && extensible === true) {
      Object.defineProperty(target, name, desc); // should never fail
      return true;
    }
    if (isEmptyDescriptor(desc)) {
      return true;
    }
    if (isEquivalentDescriptor(current, desc)) {
      return true;
    }
    if (current.configurable === false) {
      if (desc.configurable === true) {
        return false;
      }
      if ('enumerable' in desc && desc.enumerable !== current.enumerable) {
        return false;
      }
    }
    if (isGenericDescriptor(desc)) {
      // no further validation necessary
    } else if (isDataDescriptor(current) !== isDataDescriptor(desc)) {
      if (current.configurable === false) {
        return false;
      }
    } else if (isDataDescriptor(current) && isDataDescriptor(desc)) {
      if (current.configurable === false) {
        if (current.writable === false && desc.writable === true) {
          return false;
        }
        if (current.writable === false) {
          if ('value' in desc && !sameValue(desc.value, current.value)) {
            return false;
          }
        }
      }
    } else if (isAccessorDescriptor(current) && isAccessorDescriptor(desc)) {
      if (current.configurable === false) {
        if ('set' in desc && !sameValue(desc.set, current.set)) {
          return false;
        }
        if ('get' in desc && !sameValue(desc.get, current.get)) {
          return false;
        }
      }
    }
    Object.defineProperty(target, name, desc); // should never fail
    return true;
  },
  deleteProperty: function(target, name) {
    var handler = directProxies.get(target);
    if (handler !== undefined) {
      return handler.delete(name);
    }
    
    var desc = Object.getOwnPropertyDescriptor(target, name);
    if (desc === undefined) {
      return true;
    }
    if (desc.configurable === true) {
      delete target[name];
      return true;
    }
    return false;    
  },
  getPrototypeOf: function(target) {
    return Object.getPrototypeOf(target);
  },
  setPrototypeOf: function(target, newProto) {
    
    var handler = directProxies.get(target);
    if (handler !== undefined) {
      return handler.setPrototypeOf(newProto);
    }
    
    if (Object(newProto) !== newProto || newProto === null) {
      throw new TypeError("Object prototype may only be an Object or null: " +
                         newProto);
    }
    
    if (!Object_isExtensible(target)) {
      return false;
    }
    
    var current = Object.getPrototypeOf(target);
    if (sameValue(current, newProto)) {
      return true;
    }
    
    if (prim_setPrototypeOf) {
      try {
        prim_setPrototypeOf(target, newProto);
        return true;
      } catch (e) {
        return false;
      }
    }

    __proto__setter.call(target, newProto);
    return true;
  },
  preventExtensions: function(target) {
    var handler = directProxies.get(target);
    if (handler !== undefined) {
      return handler.preventExtensions();
    }
    prim_preventExtensions(target);
    return true;
  },
  isExtensible: function(target) {
    return Object.isExtensible(target);
  },
  has: function(target, name) {
    return name in target;
  },
  get: function(target, name, receiver) {
    receiver = receiver || target;

    // if target is a proxy, invoke its "get" trap
    var handler = directProxies.get(target);
    if (handler !== undefined) {
      return handler.get(receiver, name);
    }

    var desc = Object.getOwnPropertyDescriptor(target, name);
    if (desc === undefined) {
      var proto = Object.getPrototypeOf(target);
      if (proto === null) {
        return undefined;
      }
      return Reflect.get(proto, name, receiver);
    }
    if (isDataDescriptor(desc)) {
      return desc.value;
    }
    var getter = desc.get;
    if (getter === undefined) {
      return undefined;
    }
    return desc.get.call(receiver);
  },
  // Reflect.set implementation based on latest version of [[SetP]] at
  // http://wiki.ecmascript.org/doku.php?id=harmony:proto_climbing_refactoring
  set: function(target, name, value, receiver) {
    receiver = receiver || target;

    // if target is a proxy, invoke its "set" trap
    var handler = directProxies.get(target);
    if (handler !== undefined) {
      return handler.set(receiver, name, value);
    }

    // first, check whether target has a non-writable property
    // shadowing name on receiver
    var ownDesc = Object.getOwnPropertyDescriptor(target, name);

    if (ownDesc === undefined) {
      // name is not defined in target, search target's prototype
      var proto = Object.getPrototypeOf(target);

      if (proto !== null) {
        // continue the search in target's prototype
        return Reflect.set(proto, name, value, receiver);
      }

      // Rev16 change. Cf. https://bugs.ecmascript.org/show_bug.cgi?id=1549
      // target was the last prototype, now we know that 'name' is not shadowed
      // by an existing (accessor or data) property, so we can add the property
      // to the initial receiver object
      // (this branch will intentionally fall through to the code below)
      ownDesc =
        { value: undefined,
          writable: true,
          enumerable: true,
          configurable: true };
    }

    // we now know that ownDesc !== undefined
    if (isAccessorDescriptor(ownDesc)) {
      var setter = ownDesc.set;
      if (setter === undefined) return false;
      setter.call(receiver, value); // assumes Function.prototype.call
      return true;
    }
    // otherwise, isDataDescriptor(ownDesc) must be true
    if (ownDesc.writable === false) return false;
    // we found an existing writable data property on the prototype chain.
    // Now update or add the data property on the receiver, depending on
    // whether the receiver already defines the property or not.
    var existingDesc = Object.getOwnPropertyDescriptor(receiver, name);
    if (existingDesc !== undefined) {
      var updateDesc =
        { value: value,
          // FIXME: it should not be necessary to describe the following
          // attributes. Added to circumvent a bug in tracemonkey:
          // https://bugzilla.mozilla.org/show_bug.cgi?id=601329
          writable:     existingDesc.writable,
          enumerable:   existingDesc.enumerable,
          configurable: existingDesc.configurable };
      Object.defineProperty(receiver, name, updateDesc);
      return true;
    } else {
      if (!Object.isExtensible(receiver)) return false;
      var newDesc =
        { value: value,
          writable: true,
          enumerable: true,
          configurable: true };
      Object.defineProperty(receiver, name, newDesc);
      return true;
    }
  },
  /*invoke: function(target, name, args, receiver) {
    receiver = receiver || target;

    var handler = directProxies.get(target);
    if (handler !== undefined) {
      return handler.invoke(receiver, name, args);
    }

    var fun = Reflect.get(target, name, receiver);
    return Function.prototype.apply.call(fun, receiver, args);
  },*/
  enumerate: function(target) {
    var handler = directProxies.get(target);
    var result;
    if (handler !== undefined) {
      // handler.enumerate should return an iterator directly, but the
      // iterator gets converted to an array for backward-compat reasons,
      // so we must re-iterate over the array
      result = handler.enumerate(handler.target);
    } else {
      result = [];
      for (var name in target) { result.push(name); };      
    }
    var l = +result.length;
    var idx = 0;
    return {
      next: function() {
        if (idx === l) return { done: true };
        return { done: false, value: result[idx++] };
      }
    };
  },
  // imperfect ownKeys implementation: in ES6, should also include
  // symbol-keyed properties.
  ownKeys: function(target) {
    return Object_getOwnPropertyNames(target);
  },
  apply: function(target, receiver, args) {
    // target.apply(receiver, args)
    return Function.prototype.apply.call(target, receiver, args);
  },
  construct: function(target, args, newTarget) {
    // return new target(...args);

    // if target is a proxy, invoke its "construct" trap
    var handler = directProxies.get(target);
    if (handler !== undefined) {
      return handler.construct(handler.target, args, newTarget);
    }
    
    if (typeof target !== "function") {
      throw new TypeError("target is not a function: " + target);
    }
    if (newTarget === undefined) {
      newTarget = target;
    } else {
      if (typeof newTarget !== "function") {
        throw new TypeError("newTarget is not a function: " + target);
      }      
    }

    return new (Function.prototype.bind.apply(newTarget, [null].concat(args)));
  }
};

// feature-test whether the Proxy global exists, with
// the harmony-era Proxy.create API
if (typeof Proxy !== "undefined" &&
    typeof Proxy.create !== "undefined") {

  var primCreate = Proxy.create,
      primCreateFunction = Proxy.createFunction;

  var revokedHandler = primCreate({
    get: function() { throw new TypeError("proxy is revoked"); }
  });

  global.Proxy = function(target, handler) {
    // check that target is an Object
    if (Object(target) !== target) {
      throw new TypeError("Proxy target must be an Object, given "+target);
    }
    // check that handler is an Object
    if (Object(handler) !== handler) {
      throw new TypeError("Proxy handler must be an Object, given "+handler);
    }

    var vHandler = new Validator(target, handler);
    var proxy;
    if (typeof target === "function") {
      proxy = primCreateFunction(vHandler,
        // call trap
        function() {
          var args = Array.prototype.slice.call(arguments);
          return vHandler.apply(target, this, args);
        },
        // construct trap
        function() {
          var args = Array.prototype.slice.call(arguments);
          return vHandler.construct(target, args);
        });
    } else {
      proxy = primCreate(vHandler, Object.getPrototypeOf(target));
    }
    directProxies.set(proxy, vHandler);
    return proxy;
  };

  global.Proxy.revocable = function(target, handler) {
    var proxy = new Proxy(target, handler);
    var revoke = function() {
      var vHandler = directProxies.get(proxy);
      if (vHandler !== null) {
        vHandler.target  = null;
        vHandler.handler = revokedHandler;
      }
      return undefined;
    };
    return {proxy: proxy, revoke: revoke};
  }
  
  // add the old Proxy.create and Proxy.createFunction methods
  // so old code that still depends on the harmony-era Proxy object
  // is not broken. Also ensures that multiple versions of this
  // library should load fine
  global.Proxy.create = primCreate;
  global.Proxy.createFunction = primCreateFunction;

} else {
  // Proxy global not defined, or old API not available
  if (typeof Proxy === "undefined") {
    // Proxy global not defined, add a Proxy function stub
    global.Proxy = function(_target, _handler) {
      throw new Error("proxies not supported on this platform. On v8/node/iojs, make sure to pass the --harmony_proxies flag");
    };
  }
  // Proxy global defined but old API not available
  // presumably Proxy global already supports new API, leave untouched
}

// for node.js modules, export every property in the Reflect object
// as part of the module interface
if (typeof exports !== 'undefined') {
  Object.keys(Reflect).forEach(function (key) {
    exports[key] = Reflect[key];
  });
}

// function-as-module pattern
}(typeof exports !== 'undefined' ? global : this));
(function (root, factory) {
    "use strict";

    if (typeof define === 'function' && define.amd) {
        define(['jquery'],factory);
    } else if (typeof exports !== 'undefined') {
        module.exports = factory(require('jquery'))
    } else {
        root.$.fn.viewTypeChange = factory(jQuery);
    }
}(window, function ($) {
    var _viewTypeChange = function (options) {
        "use strict";

        var Module = function (element, opts) {
            var _this = this;

            this._element = element;
            this.defaults = proxy();
            this.changeView = _changeView;

            function proxy () {
                try {
                    return new Proxy({
                        el: '#tgtGoodsTrans',
                        viewType: '',
                        isSupportBrowser : true
                    }, {
                        set: function (target, key, value, receiver) {
                            if (key === 'viewType') {
                                _changeView(value);
                                sessionStorage.setItem('VT', value);
                            }
                            return Reflect.set(target, key, value, receiver);
                        },
                        get: function (target, key, receiver) {
                            return Reflect.get(target, key, receiver);
                        }
                    });
                } catch (e) {
                    return {
                        el: '#tgtGoodsTrans',
                        viewType: '',
                        isSupportBrowser : false
                    };
                }
            }

            init();

            function init() {
                $.extend(true, _this.defaults, opts || {});
            }

            function _changeView(type) {
                switch (type) {
                    case 'L':
                        $(_this.defaults.el).attr('class', 'type-list');
                        $(element).attr('class', 'type-list');
                        break;
                    case 'G' :
                        $(_this.defaults.el).attr('class', 'type-gallery');
                        $(element).attr('class', 'type-gallery');
                        break;
                    default :
                        $(_this.defaults.el).attr('class', 'type-image');
                        $(element).attr('class', 'type-image');
                        break;
                }
            }
        };

        Module.prototype.onClick = function() {
            var _this = this;
            var $target = $(_this._element);

            if($target.hasClass('type-list')) {
                this.defaults.viewType = 'G';
            } else if($target.hasClass('type-gallery')) {
                this.defaults.viewType = 'I';
            } else {
                this.defaults.viewType = 'L';
            }

            if(!this.defaults.isSupportBrowser) {
                this.changeView(this.defaults.viewType);
            }
        };

        Module.prototype.onChange = function (viewType) {
            this.defaults.viewType = viewType;

            if(!this.defaults.isSupportBrowser) {
                this.changeView(this.defaults.viewType);
            }
        };

        return this.each(function (i, v) {
            $(v).data('viewTypeChange', new Module(v, options));
        });
    };

    return _viewTypeChange;
}));
$.fn.searchFilter = function (options) {
    var promise = null;
    var SearchFilter = function (opts) {
    	var defaults;
    	if(opts.categoryId && opts.categoryId.substring(0,4) === 'C005'){		//명절몰
        	defaults = {
	            target: '#page-wrapper',
	            activeClass: 'globalSearchLayer',
	            templateName: 'mobileSearchFilter',
	            gtmNumberForButtonReset: options.gtmNumberForButtonReset,
	            gtmNumberForButtonClose: options.gtmNumberForButtonClose,
	            deliveryList: [{
	                gtmNumber: options.gtmNumber,
	                title: 'DELIVERY',
	                name: '명절매장배송',
	                type: 'deliveryView',
	                value: '06[!@!]00'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'DELIVERY',
	                name: '명절택배배송',
	                type: 'deliveryView',
	                value: '06[!@!]01,06[!@!]05'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'DELIVERY',
	                name: '명절업체배송',
	                type: 'deliveryView',
	                value: '06[!@!]03'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'DELIVERY',
	                name: '명절냉장배송',
	                type: 'deliveryView',
	                value: '06[!@!]02'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'DELIVERY',
	                name: '명절냉동택배',
	                type: 'deliveryView',
	                value: '06[!@!]04'
	            }],
	            benefitList: [{
	                gtmNumber: options.gtmNumber,
	                title: 'BENEFIT',
	                name: '덤증정',
	                type: 'benefitChkList',
	                value: '06'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'BENEFIT',
	                name: '1+1',
	                type: 'benefitChkList',
	                value: '04'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'BENEFIT',
	                name: '카드할인',
	                type: 'benefitChkList',
	                value: '15'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'BENEFIT',
	                name: 'L.POINT 혜택',
	                type: 'benefitChkList',
	                value: '13'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'BENEFIT',
	                name: '상품권증정',
	                type: 'benefitChkList',
	                value: '09'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'BENEFIT',
	                name: '즉시할인',
	                type: 'benefitChkList',
	                value: '16'
	            }],
	            searchObject: {},
	            callback: null
	        };
    	} else {
        	defaults = {
    	            target: '#page-wrapper',
    	            activeClass: 'globalSearchLayer',
    	            templateName: 'mobileSearchFilter',
    	            gtmNumberForButtonReset: options.gtmNumberForButtonReset,
    	            gtmNumberForButtonClose: options.gtmNumberForButtonClose,
    	            deliveryList: [{
    	                gtmNumber: options.gtmNumber,
    	                title: 'DELIVERY',
    	                name: '매장배송',
    	                type: 'deliveryView',
    	                value: '01,01[!@!]03'
    	            }, {
    	                gtmNumber: options.gtmNumber,
    	                title: 'DELIVERY',
    	                name: '택배배송',
    	                type: 'deliveryView',
    	                value: '02'
    	            }, {
    	                gtmNumber: options.gtmNumber,
    	                title: 'DELIVERY',
    	                name: '업체택배',
    	                type: 'deliveryView',
    	                value: '04'
    	            }, {
    	                gtmNumber: options.gtmNumber,
    	                title: 'DELIVERY',
    	                name: '바로배송',
    	                type: 'deliveryView',
    	                value: 'express'
    	            }],
    	            benefitList: [{
    	                gtmNumber: options.gtmNumber,
    	                title: 'BENEFIT',
    	                name: '증정',
    	                type: 'benefitChkList',
    	                value: '03,04'
    	            }, {
    	                gtmNumber: options.gtmNumber,
    	                title: 'BENEFIT',
    	                name: '1+1',
    	                type: 'benefitChkList',
    	                value: '02,05,07,09'
    	            }, {
    	                gtmNumber: options.gtmNumber,
    	                title: 'BENEFIT',
    	                name: 'L.POINT 혜택',
    	                type: 'benefitChkList',
    	                value: '01'
    	            }, {
    	                gtmNumber: options.gtmNumber,
    	                title: 'BENEFIT',
    	                name: '카드할인',
    	                type: 'benefitChkList',
    	                value: '10'
    	            }, {
    	                gtmNumber: options.gtmNumber,
    	                title: 'BENEFIT',
    	                name: '살수록 더 싸게',
    	                type: 'benefitChkList',
    	                value: '06'
    	            }, {
    	                gtmNumber: options.gtmNumber,
    	                title: 'BENEFIT',
    	                name: '즉시할인',
    	                type: 'benefitChkList',
    	                value: '08,11,12'
    	            }],
    	            searchObject: {},
    	            callback: null
    	        };
    	}
    	
        var _this = this;

        var params = location.search.replace('?', '').split('&');

        for(var i = 0, len = params.length; i < len; i++) {
            var param = params[i].split('=');
            defaults.searchObject[param[0]] = param[1];
        }

        this.$layer = null;

        this.config = $.extend(true, defaults, options);

        this.render();
    };

    SearchFilter.prototype.reset = function () {
        this.$layer
            .find(':checkbox:checked')
            .prop('checked', false);
    };

    SearchFilter.prototype.search = function () {
        var obj = {};
        var _this = this;
        this.$layer
            .find(':checkbox')
            .each(function () {
                var $el = $(this);
                var name = $el.attr('name');
                var value = $el.is(':checked') ? $el.val() : '';

                if(obj[name]) {
                    obj[name].push(value)
                } else {
                    obj[name] = [value];
                }
            });

        $.each(obj, function (i, v) {
            _this.config.searchObject[i] = v.filter(function (v) {
                return v !== '';
            }).join(',');
        });

        if(_this.config.callback !== null) {
            _this.config.callback(_this.config.searchObject);
        } else {
            location.search = decodeURIComponent($.param(_this.config.searchObject));
        }

    };

    SearchFilter.prototype.toggle = function () {
        if(!this.$layer) {
            return;
        }
        if($(this.config.target).hasClass(this.config.activeClass)) {
            schemeLoader.loadScheme({key: 'showBar'});
        } else {
            schemeLoader.loadScheme({key: 'hideBar'});
        }
        $(this.config.target).toggleClass(this.config.activeClass);
    };

    SearchFilter.prototype.setOptions = function (obj) {
        $.extend(true, this.config, obj);
        this.render();
    };

    SearchFilter.prototype.render = function () {
        var _this = this;

        if(this.$layer !== null) {
            this.$layer.remove();
        }

        this.$layer = $($.render[this.config.templateName](this.config))
            .appendTo($(this.config.target));

        this.$layer
            .find(':checkbox')
            .each(function (i, v) {
                var $v = $(v),
                    value = $v.val(),
                    type = $v.attr('name');

                if(_this.config.searchObject.hasOwnProperty(type)
                && _this.config.searchObject[type].indexOf(value) !== -1) {
                    $v.prop('checked', true);
                }
            });

        this.$layer
            .promise()
            .then(function () {
                _this.$layer
                    .on('click', '[data-id="btnreset"]', function () {
                        _this.reset();
                        return false;
                    })
                    .on('click', '[data-id="btnclose"]', function () {
                        _this.toggle();
                        return false;
                    })
                    .on('click', '[data-id="btnaccordion"]', function () {
                        $(this)
                            .toggleClass('active')
                            .siblings()
                            .removeClass('active');
                        return false;
                    })
                    .on('click', '[data-id="btnsearch"]', function () {
                        if(window.Promise) {
                            promise = new Promise(function (resolve) {
                                _this.toggle();

                                setTimeout(function () {
                                    resolve();
                                }, 300);
                            });
                            promise.then(function () {
                                _this.search();
                            });
                        } else {
                            _this.toggle();
                            _this.search();
                        }
                        return false;
                    })
                    .on('webkitTransitionEnd transitionend', function () {
                        if ($(_this.config.target).hasClass(_this.config.activeClass)) {
                            _this
                                .$layer
                                .next()
                                .fadeIn(200);

                            $('html, body').addClass('mask');
                        } else {
                            _this
                                .$layer
                                .next()
                                .fadeOut(200);

                            $('html, body').removeClass('mask');
                        }
                    })
                    .next()
                    .on('click', function () {
                        _this.toggle();
                        return false;
                    });
            });
    };

    return this.each(function (i, v) {
        var $el = $(v);

        if(!$el.data('searchFilter')) {
            $el.data('searchFilter', new SearchFilter(options));
        }

        $el.on('click', function () {

            $(this).data('searchFilter').toggle();
            return false;
        });
    });
};
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
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory();
	} else {
		root.genDomInput = factory();
	}
}( window, function() {
	var _genDomInput = function( elemName, elemValue ) {
		var input = document.createElement("input");
		
		input.setAttribute("type", "hidden");
		input.setAttribute("name", elemName);
		input.setAttribute("id", elemName);
		input.setAttribute("value", elemValue);
		
		return input;
	};
	
	return _genDomInput;
}));
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
		define(factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory();
	} else {
		root.fnNmGetter = factory();
	}
}( window, function() {
	var _fnNmGetter = function( caller ) {
		var f = arguments.callee.caller,
			pat = /^function\s+([a-zA-Z0-9_]+)\s*\(/i,
			func = new Object();
		
	    if(caller) {	    	
	    	f = f.caller;
	    }
	    
	    pat.exec(f);
	    func.name = RegExp.$1;
	    
	    return func;
	};
	
	return _fnNmGetter;
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
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.co = root.co || {};
		root.co.ajax = factory(jQuery);
	}
}( window, function($) {
	var _coAjax = function( url, params, fnCallback, type, syn ) {
		var fnSuccess = typeof fnCallback === 'function' ? fnCallback : fnCallback.success;
		var jsonParams = JSON.stringify(params);
		jsonParams = encodeURIComponent(jsonParams);
		if (!type) type = 'POST';
		if (!syn) syn = true;
		$.ajax({
			async: syn,
			type: type,
			cache: true,
			url: url,
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
				'X-Requested-With': 'XMLHttpRequest'
			},
			data: params,
			success: function(data) {
				if(data && fnSuccess){
					fnSuccess(data);
				}

			},
			error: function(data, status, headers) {
				if(data && fnCallback.error){
					fnCallback.error(data, status, headers);
				}
			}
		});
	};
	
	return _coAjax;
}));
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.goProductDetail = factory(jQuery);
	}
}( window, function($) {
	var _goProductDetail = function( cateId,prodCd,popupYn,socialSeq,smartOfferClickUrl ) {
		var dpCode = "";
		if(typeof(smartOfferClickUrl) != "undefined" && smartOfferClickUrl != "") {
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
		window.location.href = $.utils.config( 'LMAppUrlM' ) + "/mobile/cate/PMWMCAT0004_New.do?CategoryID="+cateId+"&ProductCD="+prodCd+"&socialSeq="+socialSeq + (dpCode != "" ? dpCode:"");
	};
	
	return _goProductDetail;
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
		define(['jquery', 'goLogin'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery'), require('goLogin') );
	} else {
		root.isLogin = factory( jQuery, root.goLogin );
	}
}( window, function( $, goLogin ) {
	var _isLogin = function(redirectUrl, event) {
		params = {
				'redirectUrl' : redirectUrl
		};

		var bLogin;

		var url = (window._LMAppUrlM || $.utils.config('LMAppUrlM')) +"/mobile/login/islogin.do";

		$.ajax({
	        type       : "POST" ,
	        url        : url,
	        //url        : _LMAppSSLUrl+"/member/islogin.do" ,
	        data       : params ,
	        async      : false ,
	        dataType   : "text" ,
	        timeOut    : (9 * 1000) ,
	        success    : function(response){
	         	var jsonData = eval( "(" + response + ")" );
	         	bLogin = ( jsonData.isLogin == "Y" );
	        } ,
			cache      : false ,
	        error      : callSysErr
	    });

		if ( !bLogin ) {
			goLogin("MMARTSHOP", redirectUrl);
			//return false;
		}

		return bLogin;
	};
	
	return _isLogin;
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
		module.exports = factory( require( 'jquery' ) );
	} else {
		root.global = root.global || {};
		root.global.addHistory = factory( jQuery );
	}
}( window, function( $ ) {
	var _addHistory = function(data, successFunc, failFunc) {
		var defaultData = {
				categoryId: null,
				contensNo: null,
				prodCd: null,
				histTypeCd: null
			};
			data = $.extend({}, defaultData, data);
			
			var successCallback = function(data) {
				if (typeof(successFunc) != "undefined" && $.isFunction(successFunc)) {
					successFunc(data);;
				}
			};
			
			if (typeof(failFunc) === "undefined" || !$.isFunction(failFunc)) {
				failFunc = function(xhr, status, error) {
				};
			}
			
			$.api.set({
				apiName : 'myHistoryAdd',
				data : data,
				success : successCallback,
				errorCallback : failFunc
			});
	};
	
	return _addHistory;
}));
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