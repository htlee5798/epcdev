(function ($){
	/* <div class="spinner-input">
	 *   <input type="text" id="orderQty_${nRow.PROD_CD}" name="orderQty_${nRow.PROD_CD}" value="${inOrderQty}"
	 *      max="${nRow.MAX_ORD_PSBT_QTY}"
	 *      max="${nRow.MAX_ORD_PSBT_QTY}"
	 *      min="${nRow.MIN_ORD_PSBT_QTY}"
	 *      data-category-id="${nRow.CATEGORY_ID}"
	 *      data-prod-cd="${nRow.PROD_CD}"
	 *      maxlength="3" title="수량">
	 * </div>
	 */

	function inputOrdQtyRange(element, options) {	
		var $qtyElem = null,
			$productPanel = null,
			defaults = {
				val : 1,
				max : 0,
				min : 0,
				plusBtnCss :'sp-plus',
				minusBtnCss: 'sp-minus'
			};

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
			// 옵션상품은 옵션레이어 오픈 후 레이어에서 메시지 처리(jquery.deal-option-layer-improved.js)
			if($(this).data().optionYn == undefined || $(this).data().optionYn == 'N'){
				var tmpqty = parseInt($qtyElem.val(), 10);

				$qtyElem.val(tmpqty + 1);
				validOrderQty();
			}
		}

		function minusEv() {
			// 옵션상품은 옵션레이어 오픈 후 레이어에서 메시지 처리(jquery.deal-option-layer-improved.js)
			if($(this).data().optionYn == undefined || $(this).data().optionYn == 'N'){
				var tmpqty = parseInt($qtyElem.val(), 10);

				$qtyElem.val(tmpqty < defaults.min ? defaults.min : tmpqty - 1);
				validOrderQty();
			}
		}
		
		function validHolidayCategory() {
			var validHolidayCategoryId = "";
			$.ajax({
				type: "GET",
				url: _LMAppUrl+"/holidays/ajax/getValidCateId.do",
				dataType: "json",
				async : false,
				success: function(data){
					validHolidayCategoryId = (data == null) ? "" : data;
				},
				error:function(data){
				}
			});
			console.log(validHolidayCategoryId);
			return validHolidayCategoryId;
		}

		function validOrderQty() {
			var minQty = parseInt(defaults.min, 10),
				maxQty = parseInt(defaults.max, 10),
				orderQty = parseInt($qtyElem.val(), 10);
			var validHolidayCategoryId= validHolidayCategory();

			$productPanel = $qtyElem.closest('[data-panel=product]');

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
				if(validHolidayCategoryId != null && validHolidayCategoryId != "" && $qtyElem.attr('data-category-id').substring(0,8) == validHolidayCategoryId)
				{
					var prod_nm = $productPanel.find('.thumb-link').text();
					var bigbuy = confirm(fnJsMsg(view_messages.error.productOrderQty, minQty, maxQty)+"\n명절 선물세트 대량구매 문의를 통해 문의하시면, 더 좋은 가격에 구매가 가능합니다.\n대량구매로 이동하시겠습니까?");
					if(bigbuy)
					{
						window.open('/board/holiday/popup/view.do?ProductNM='+prod_nm+'&SITELOC=VS034', 'bosPop', 'top=50, left=200, width=630, height=940, resizable=no, location=no');
					}
				}else{
					alert(fnJsMsg(view_messages.error.productOrderQty, minQty, maxQty));	//해당상품은 최소{0}개 이상 최대{5}개 까지만 구매가 가능합니다.
				}

				$qtyElem.val(maxQty);
				return;
			} else if(validHolidayCategoryId != null && validHolidayCategoryId != "" && orderQty>=300 && $qtyElem.attr('data-category-id').substring(0,8) == validHolidayCategoryId){
				var prod_nm = $productPanel.find('.thumb-link').text();
				var bigbuy = confirm(fnJsMsg(view_messages.error.productOrderQty, minQty, maxQty)+"\n명절 선물세트 대량구매 문의를 통해 문의하시면, 더 좋은 가격에 구매가 가능합니다.\n대량구매로 이동하시겠습니까?");
				if(bigbuy)
				{
					window.open('/board/holiday/popup/view.do?ProductNM='+prod_nm+'&SITELOC=VS034', 'bosPop', 'top=50, left=200, width=630, height=940, resizable=no, location=no');
				}
			}
		}

		init();
	}
	$.fn.inputOrdQtyRange = function(options){
		return this.each(function() {
			return inputOrdQtyRange(this, options);
		});
	};


	$(function() {
		$('.spinner-input').inputOrdQtyRange();
	});
})(jQuery);