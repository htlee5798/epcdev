;(function(){

	"use strict"

	var defaults = {
		data : {
			prodCd : '',
			categoryId : '',
			minQty : '',
			maxQty : '',
			itemCd : '',
			gubun : '', // B: 장바구니담기, P:정기배송담기, D: 바로구매
			optionYn : '',
			prodTypeCd : '',
			orderQty : '',
			periDeliYn : 'N'
		}
		, validator : null
		, addBasketCallback : null
		, goBuynowCallback : null
	};

	var Cart = function(params){
//		data-prod-cd="${nRow.PROD_CD}"
//		data-category-id="${nRow.CATEGORY_ID}"
//		data-max-qty="${nRow.MAX_ORD_PSBT_QTY}"
//		data-min-qty="${nRow.MIN_ORD_PSBT_QTY}"
//		data-item-cd="${nRow.ITEM_CD}"
//		data-gubun="B"
//		data-option-yn="${optionValue}"
//		data-prod-type-cd="${nRow.ONLINE_PROD_TYPE_CD}"
//		data-peri-deli-yn="Y"
//		id="basketBtn_${nRow.PROD_CD}_${PROD_AREA_IDX}"

		var _Cart = this
			, _target = null;

		function init() {
			$.extend(true, defaults, params || {} );
		}

		_Cart.add = function(obj){
			var $this = $(obj);
			_target = obj;
			
			$.extend(true, defaults.data, $this.data());

			var data = defaults.data;			
			
			if(null != defaults.validator && !defaults.validator(data)) return;

			if(data.optionYn === 'Y'){//옵션상품인 경우
				getOption(_target);
			}else{//단일상품인 경우
				var basketItems = [];
				basketItems = setBasket(data, basketItems, $this);
				
				if(data.gubun === 'D'){
					goBuyNow(basketItems);
				}else{
					addBasket(basketItems);
				}
			}
		};

		_Cart.adds = function(obj, targetObj){
			var basketItems=[];
			_target = obj;

			targetObj.each(function(index){
				var $this = $(this),  //this element
					$basketBtn = $("#basketBtn_"+ $this.data('product-code'));

				$.extend( true, defaults.data , $basketBtn.data() );

				var data = defaults.data;
				basketItems = setBasket(data, basketItems, $this);
			});

			addBasket(basketItems);
		};

		function setBasket(data, basketItems, obj){
			var $orderQty = null
				, orderQty = data.orderQty;

			if( orderQty == undefined || orderQty == ''){ //수량선택 버튼이 있는 경우,
				$orderQty = obj.closest('.product-article, tr').find("[name=orderQty_" + data.prodCd+"]");
				orderQty = $orderQty.length == 0 ? data.minQty : $orderQty.val();
			}

			if(!validOrderQty(data, $orderQty, orderQty)){
				return;
			}

			basketItems.push({
				prodCd: data.prodCd,			// 상품코드
				itemCd: data.itemCd == '' ? '001' : data.itemCd,
				bsketQty: Number(orderQty),		// 주문수량
				categoryId: data.categoryId,	// 카테고리ID
				nfomlVariation: null,			// 옵션명, 골라담기의 경우 옵션명:수량
				overseaYn: 'N',					// 해외배송여부
				prodCouponId: null,				// 즉시할인쿠폰ID
				oneCouponId: null,				// ONE 쿠폰ID
				cmsCouponId: null,				// CMS 쿠폰ID
				markCouponId: null,				// 마케팅제휴쿠폰ID
				periDeliYn: data.gubun === 'P' ? 'Y' : 'N' // 정기배송여부
			});

			return basketItems;
		}

		function validOrderQty(data, orderQtyObj, orderQty){
			var minQty = parseInt(data.minQty, 10),
				maxQty = parseInt(data.maxQty, 10);
			orderQty = parseInt(orderQty, 10);

			if(!isOnlyNumber(orderQty)) {
				alert(fnJsMsg(view_messages.error.orderQtyNumber));	//주문수량은 숫자만 입력 가능합니다.
				return;
			} else if(orderQty < minQty){
				alert(fnJsMsg(view_messages.error.productOrderQty, minQty, maxQty));	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
				$(orderQtyObj).val(minQty);
				return;
			} else if(orderQty > maxQty){
				alert(fnJsMsg(view_messages.error.productOrderQty, minQty, maxQty));	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
				$(orderQtyObj).val(maxQty);
				return;
			}

			return true;
		}

		function addBasket(params){
			global.addBasket(params, function(data) {
				defaults.addBasketCallback(_target);
			});
		}

		function goBuyNow(params){
			global.addDirectBasket(params);
		}

		function getOption(obj){
			var url = document.location.href;
			//옵션정보 가져오기
			$.ajax({
				type: "GET",
				cache: false,
				url: _LMAppUrl+"/product/ajax/productOptionListAjax.do",
				data: {
					'PROD_CD' : defaults.data.prodCd
					, 'TYPE' : defaults.data.gubun === 'D' ? 'order' : 'basket'
					, 'PRODTYPECD' : defaults.data.prodTypeCd
					, 'CATEGORYID' : defaults.data.categoryId
					, 'PERIDELIYN' : defaults.data.periDeliYn
				},
				dataType: "html",
				success: function(data) {
					//render
//					$("#opt-list").html(data);
//					layerpopTriggerBtn(obj);
//					selectMenu();

					$(obj).basketOption({
						tmp: data,
						after: selectMenu
					});
				}
			});
		}
		init();
	}

	window.Cart = Cart;
})();

function basketOptionsLayer(target, after) {
	var $results = $(target).closest('.product-article'),
		$basketResult = $($('#basket-result').html()).appendTo('body'),
		_top = $(target).offset().top - $basketResult.outerHeight(true) - 12,
		_left = $(target).offset().left - ($basketResult.outerWidth(true) / 2) + ($(target).outerWidth(true) / 2);

	$results.addClass('active');
	
	//2017.02.16 이승남 정기배송 담기_ 클릭 시 레이어팝업 변경
	var obj = $(target);
	if("Y" == obj.attr("data-peri-deli-yn")){
		$('div.contents').find('strong').html("정기배송 장바구니에 상품이 담겼습니다.");
		$('div.set-btn').find('.btn-form-type1').attr('onclick', "global.goBasket('B');return false;");
	}

	$basketResult.css({
		display: 'block',
		top: _top,
		left: _left
	});

	if(after) {
		after();
	}

	//TODO : planb - eventFunc 함수 회피용 (eventFunc삭제후 삭제 필요)
	//basketResult.after('<i style="position:fixed;top:0;right:0;bottom:0;left:0;z-index:11;" />');

	$(document).on('click.basket', function(e) {
		if(e.target == target || $(e.target).closest($basketResult).length > 0) {
			return;
		}

		//TODO : planb - eventFunc 함수 회피용 (eventFunc삭제후 삭제 필요)
		//basketResult.next('i').remove();
		$basketResult.remove();
		$results.removeClass('active');

		$(document).off('click.basket');
	});
	//layerpopTriggerBtn(target);
}