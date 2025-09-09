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