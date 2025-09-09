(function( $, window, document, undefined ) {
	'use strict';

	$.fn.productOptionLayer = function( options ) {
		var defaults = {
			title : '옵션을 선택하세요',
			itemClass : '.product-article',
			layerClass : '.layerpop-type3',
			btnPlusClass : '.sp-plus',
			btnMinusClass : '.sp-minus',
			btnCloseClass : '#btnClose',
			optionFieldClass : '#optionField',
			wrapperQuantityClass : '.wrapper-quantity',
			quantityClass : '.quantity',
			subOptionFieldClass : '#subOptionField',
			target : 'body',
			templateName : 'basketOptionForDefault',
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
			}).on( 'keydown', config.btnCloseClass, function(e) {
				if(_wac.isEnterEvent(e) || _wac.isTabEvent(e)){
					close();
					$item.addClass('active')
					$item.find('.layerpop-trigger2').focus();
				}
				return false;
			}).on( 'click', config.btnPlusClass, function() {
				quantityPlus( this );

				return false;
			}).on( 'click', config.btnMinusClass, function() {
				quantityMinus( this );

				return false;
			}).on( 'change', config.quantityClass, function() {
				validateQuantity( $(this) );
			}).on( 'change', config.optionFieldClass + ' select', function() {
				reset();

				setLabel( this );
				onChangeOptionSelect( this );

			}).on( 'change', config.subOptionFieldClass + ' select', function() {
				setLabel( this );
				onChangeSubOptionSelect( this );
			});

			$target.on( 'click.layer' , function( e ) {
				var $this = $( e.target );

				if( $this.closest( config.layerClass ).length === 0 ) {
					removeAllLayer();
					close();
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
					label : '',
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
			var selectBoxTemplate = $.render[ obj.templateName || 'selectBox' ]( obj );

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

					var options = {
						targetClass : config.optionFieldClass,
						templateName : 'selectBox',
						label : prodCd.indexOf( 'V' ) !== -1 ? '선택' : '옵션선택',
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
			$item.removeClass( 'active hover' );
			if( $layer !== null ) {
				$layer.remove();
			}
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

		function setLabel( target ) {
			var $target = $( target ),
				$label = $target.prev();

			$label.html( $target.find( ':selected' ).text() );
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
				if(config.categoryId.substring(0,8) == "C0050018")
				{
					var prod_nm = config.prodTitle;
					var bigbuy = confirm(fnJsMsg(view_messages.error.productOrderQty, min, max )+"\n명절 선물세트 대량구매 문의를 통해 문의하시면, 더 좋은 가격에 구매가 가능합니다.\n대량구매로 이동하시겠습니까?");
					if(bigbuy)
					{
						window.open('/board/holiday/popup/view.do?ProductNM='+prod_nm+'&SITELOC=VS034', 'bosPop', 'top=50, left=200, width=630, height=940, resizable=no, location=no');
					}
				}else{
					alert( fnJsMsg(view_messages.error.productOrderQty, min, max ) );
				}
				$el.data( 'originalValue', quantity ).val( max );

			} else if( quantity < min ) {
				alert( fnJsMsg(view_messages.error.productOrderQty, min, max ) );
				$el.data( 'originalValue', quantity ).val( min );

			} else if( !isOnlyNumber( quantity ) ) {
				alert( '주문수량은 숫자만 입력 가능합니다.' );
				$el.data('originalValue', quantity).val( min );

			} else {
				if(quantity>=300 && config.categoryId.substring(0,8) == "C0050018"){
					var prod_nm = config.prodTitle;
					var bigbuy = confirm(fnJsMsg(view_messages.error.productOrderQty, min, max )+"\n명절 선물세트 대량구매 문의를 통해 문의하시면, 더 좋은 가격에 구매가 가능합니다.\n대량구매로 이동하시겠습니까?");
					if(bigbuy)
					{
						window.open('/board/holiday/popup/view.do?ProductNM='+prod_nm+'&SITELOC=VS034', 'bosPop', 'top=50, left=200, width=630, height=940, resizable=no, location=no');
					}
				}
				$el.data( 'originalValue', quantity );
			}
		}

		return this;
	};

})( jQuery, window, document );