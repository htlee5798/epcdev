/**
 * 
 */
(function( $, window, document, undefined ) {
	'use strict';
	
	$.fn.inputOrdQtyRange = function(options){
		return this.each(function() {
			return inputOrdQtyRange(this, options);
		});
	};
	
	function inputOrdQtyRange(element, options) {
		var $qtyElem = null,
			defaults = {
				val : 1,
				max : 0,
				min : 0,
				plusBtnCss :'sp-plus',
				minusBtnCss: 'sp-minus'
			};
		
		init();
		
		function init() {
			if($(element).is('[data-is-range]')){
				return;
			}
			
			$.extend( true, defaults, options || {} );

			$('<button type="button" class="'+ defaults.minusBtnCss +'" title="수량 감소"><i class="ico-minus">감소</i></button> ').appendTo(element).off('click.range').on('click.range', minusEv);
			$('<button type="button" class="'+ defaults.plusBtnCss +'" title="수량 증가"><i class="ico-plus">증가</i></button> ').appendTo(element).off('click.range').on('click.range', plusEv);

			$(element).attr('data-is-range', 'true');

			$qtyElem = $(element).find('input[type="text"]').on('keypress keydown', isOnlyNumberInput).on('change', validOrderQty);
			
			if($qtyElem.is('[max]')) {
				defaults.max = $qtyElem.attr('max');
			}
			if($qtyElem.is('[min]')) {
				defaults.min = $qtyElem.attr('min');
			}
		}
		
		function plusEv() {
			var tmpqty = parseInt($qtyElem.val(), 10);
		
			$qtyElem.val(tmpqty + 1);
			validOrderQty();
		}
		
		function minusEv() {
			var tmpqty = parseInt($qtyElem.val(), 10);
		
			$qtyElem.val(tmpqty < defaults.min ? defaults.min : tmpqty - 1);
			validOrderQty();
		}
		
		function validOrderQty() {
			var minQty = parseInt(defaults.min, 10),
				maxQty = parseInt(defaults.max, 10),
				orderQty = parseInt($qtyElem.val(), 10);
			
			if(!isOnlyNumber(orderQty)) {
				alert(fnJsMsg(view_messages.error.orderQtyNumber));	//주문수량은 숫자만 입력 가능합니다.
				var minQty = ( defaults.min == undefined || defaults.min == 0 || defaults.min == null ) ? 1 : defaults.min;
				$qtyElem.val(minQty);
				return;
			}
			if(orderQty < minQty) {
				alert(fnJsMsg(view_messages.error.productOrderQty, minQty, maxQty));	//해당상품은 최소{0}개 이상 최대{5}개 까지만 구매가 가능합니다.
				$qtyElem.val(minQty);
				return;
			} else if(orderQty > maxQty) {
				alert(fnJsMsg(view_messages.error.productOrderQty, minQty, maxQty));	//해당상품은 최소{0}개 이상 최대{5}개 까지만 구매가 가능합니다.
				$qtyElem.val(maxQty);
				return;
			}
		}
	}
})( jQuery, window, document );