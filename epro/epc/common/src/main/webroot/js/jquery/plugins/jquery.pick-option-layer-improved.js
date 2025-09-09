(function( $, window, document, undefined ) {
	'use strict';
	
	$.fn.pickOptionLayer = function( options ) {
		var defaults = {
			title : '옵션을 선택하세요',
			itemClass : 'product-article',
			layerClass : '.layerpop-type3',
			btnPlusClass : '.sp-plus',
			btnMinusClass : '.sp-minus',
			btnCloseClass : '#btnClose',
			wrapperQuantityClass : '.wrapper-quantity',
			quantityClass : '.quantity',
			target : 'body',
			templateName : 'basketOptionForPick',
			marginTop : 4,
			periodDeliveryYn : 'N'
		},
		$this = this,
		config = $.extend( {}, defaults, options || {});
		
		var $target = $( config.target ),
			$layer = null,
			$item = $this.closest( config.itemClass );
		
		(function init() {
			
			config = $.extend( config, $this.data() );
			
			removeAllLayer();
			render();
			
			$item.addClass( 'active hover' );
			
			$( window ).on( 'resize', function() {
				removeAllLayer();
				close();
			});
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
				var $this = $( this ),
					quantity = $this.val(),
					min = $this.data( 'minQuantity' ),
					max = $this.data( 'maxQuantity' );

				if( quantity > max ) {
					alert( fnJsMsg(view_messages.error.productOrderQty, min, max ) );
					$this.val( max );
				}
				
				if( quantity < min ) {
					alert( fnJsMsg(view_messages.error.productOrderQty, min, max ) );
					$this.val( min );
				}
				
				if( !isOnlyNumber( quantity ) ) {
					alert( '주문수량은 숫자만 입력 가능합니다.' );
					$this.val( min );
				}
			});
			
			$target.on( 'click' , function( e ) {
				var $this = $( e.target );
				
				if( $this.closest( config.layerClass ).length === 0 ) {
					removeAllLayer();
					close();
				}
			});
			//TODO
			$layer.find( 'form' ).submit(function() {
				var formData = $( this ).serializeArray(),
					nformVariation = '';
				
				setTotalQuantity( formData );
				
				if( !validate() ){
					for( var i = 0, len = formData.length; i < len; i++ ) {
						var data = formData[ i ];
						
						if( nformVariation === '' ) {
							nformVariation =  data.name + ':' + data.value;
						} else {
							nformVariation =  nformVariation + ';' + data.name + ':' + data.value;
						}
					}
					
					config.params = {
						prodCd: config.prodCd,			// 상품코드
						itemCd: '001',			// 단품코드
						bsketQty: config.totalQuantity / config.unit,			// 주문수량
						categoryId: config.categoryId,		// 카테고리ID
						nfomlVariation: nformVariation,	// 옵션명, 골라담기의 경우 옵션명:수량
						periDeliYn: config.periodDeliYn		// 정기배송여부
					};
					
					config.successCallBack && config.successCallBack( config );
				}
				
				return false;
			});
		}
		//TODO
		function validate() {
			var isError = false,
				remain = config.totalQuantity % config.unit;
			
			if( config.totalQuantity % config.unit !== 0 ) {
				isError = true;
				
				alert( '상품' + setComma( config.unit - remain ) + '개를 더 선택해주세요.' );
			}
			
			return isError;
		}
		
		function setTotalQuantity( formData ) {
			var totalQuantity = 0;
			
			for( var i = 0, len = formData.length; i < len; i++ ) {
				var data = formData[ i ];
				
				totalQuantity = totalQuantity + parseInt( data.value , 10 );
			}
			
			config.totalQuantity = totalQuantity;
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
			});
			//success 토스트 팝업
			$( '.layerpop-toast-btm' ).remove();
		}
		
		function render() {
			$.api.get({
				apiName : 'basketOptions',
				data : {
					'PROD_CD' : config.prodCd,
					'CATEGORYID' : config.categoryId,
					'PERIDELIYN' : 'N'
				},
				successCallback : function( productData ) {
					config.options = productData;
					config.unit = config.options[ 0 ].unit;
	
					var layerTemplate = $.render[ config.templateName ]( config );
					
					open( layerTemplate );
				}
			});
		}
		
		function open( layerTemplate ) {
			$target.append( layerTemplate )
				.promise()
				.done(function() {
					$layer = $target.find( config.layerClass );
					
					setPosition();
					bindEvent();
				});
		}
		
		function close() {
			$item.removeClass( 'active hover' );
			$layer.remove();
			$target.off( 'click.layer' );
		}
		
		function setPosition() {
			var offset = $this.offset(),
				top = offset.top + $this.outerHeight( true ) + config.marginTop,
				left = offset.left,
				layerWidth = $layer.width(),
				wrapperWidth = 1030,
				wrapperLeft = ( $( document ).width() - wrapperWidth ) / 2,
				maxLeftForLayer  = 0,
				maxLeftForWrapper = 0;
			
			if( left + layerWidth >= wrapperLeft + wrapperWidth ) {
				maxLeftForLayer = left + layerWidth;
				maxLeftForWrapper = wrapperLeft + wrapperWidth;
				left = left - ( maxLeftForLayer - maxLeftForWrapper );
			} 
			
			$layer.css({
				top : top,
				left : left
			});
		}
		
		return this;
	};
	
})( jQuery, window, document );