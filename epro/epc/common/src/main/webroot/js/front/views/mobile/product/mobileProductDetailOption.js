"use strict";

/*
 * Item Info
 */
 var setItem=function(
		 itemsCode
		 , itemsColor
		 , itemsColorNm
		 , itemsSize
		 , itemsSizeNm
		 , itemsOptnDesc
		 , itemsSalePrc
		 , itemsCurrSalePrc
		 , itemsMaxPromotion
		 , itemsSrcmkCd
		 , itemsAbsenceYN
		 , itemsAvailableStock
		 , itemsStockYn
		 , itemsImgQty
		 , itemsDeliType
		 , itemsForgnDeliYn
		 , itemsProdCd
		 , itemsProdNm
		 , itemsOptionYn
		 , itemsIndex
		 , itemsMaxOrdPsbtQty
		 , itemsMinOrdPsbtQty
		 , itemsMaxPurchasePrc
		 , itemsStrCd
		 , itemOptionTitle
		 , itemOptionSubTitle) {
	 this.itemsCode=itemsCode;
	 this.itemsColor=itemsColor;
	 this.itemsColorNm=itemsColorNm;
	 this.itemsSize=itemsSize;
	 this.itemsSizeNm=itemsSizeNm;
	 this.itemsOptnDesc=itemsOptnDesc;
	 this.itemsSalePrc=itemsSalePrc;
	 this.itemsCurrSalePrc=itemsCurrSalePrc;
	 this.itemsMaxPromotion=itemsMaxPromotion;
	 this.itemsSrcmkCd=itemsSrcmkCd;
	 this.itemsAbsenceYN=itemsAbsenceYN;
	 this.itemsAvailableStock=itemsAvailableStock;
	 this.itemsStockYN=itemsStockYn;
	 this.itemsImgQty=itemsImgQty;
	 this.itemsDeliType=itemsDeliType;
	 this.itemsForgnDeliYn=itemsForgnDeliYn;
	 this.itemsProdCd=itemsProdCd;
	 this.itemsProdNm=itemsProdNm;
	 this.itemsOptionYn=itemsOptionYn;
	 this.itemsIndex=itemsIndex;
	 this.itemsMaxOrdPsbtQty=itemsMaxOrdPsbtQty;
	 this.itemsMinOrdPsbtQty=itemsMinOrdPsbtQty;
	 this.itemsMaxPurchasePrc=itemsMaxPurchasePrc;
	 this.itemsStrCd=itemsStrCd;
	 this.itemOptionTitle=itemOptionTitle;
	 this.itemOptionSubTitle=itemOptionSubTitle;
};

$(function() {
	$('#quickmenu-bar .inner-scroll').on('click', 'button', function(e){
		// 수량관련 버튼 체크
		var optnArea = $('#quickmenu-bar .inner-scroll');

		if($(this).hasClass("up")) {
			//console.log("수량증가");
			var obj = $(this).closest(".goodsadded").find(".optnCnt");
			var num = $(obj).val() * 1 + 1;

			if(num > 999) {
				return;
			} else {
				$(obj).val(num);
			}
		}

		if($(this).hasClass("down")) {
			//console.log("수량감소");
			var obj = $(this).closest(".goodsadded").find(".optnCnt");
			var num = $(obj).val() * 1 - 1;

			if( prodBasicInfo.ONLINE_PROD_TYPE_CD == "04" ) {	// 골라담기
				if(num < 0) return;
			} else if(num < 1) {
				return;
			}

			$(obj).val(num);
		}

		if($(this).hasClass("optnDel")) {
			//console.log("수량삭제");
			if( $("#FormProdInfo #PROD_LINK_KIND_CD").val() == "05" ) {
				// 연관상품
				$(this).closest("div[name=virtual-basket]").remove();

				// 본품 선택 없으면 연관상품 모두 삭제
				var cnt = 0;
				$.each($(optnArea).find("div[name=virtual-basket]"), function (index, item) {
					var objIdArr = $(item).prop("id");
					if(objIdArr.split("|")[0] == $("#FormProdInfo #PROD_CD").val() ) {
						cnt++;
					}
				});

				if( cnt < 1 ) {
					$(optnArea).find("div[name=virtual-basket]").remove();
				}
			} else {
				if( $(this).prop("name") == "orderDel") {
					$(this).closest("div[name=virtual-basket]").remove();
				} else if( $(this).prop("name") == "orderDelDtl" ) {
					$(this).closest(".goodsadded").remove();
				}
			}
			fnResetOptn('all', 0);	// 옵션 selectbox reset
		}

		if( prodBasicInfo.ONLINE_PROD_TYPE_CD == "04" ) {
			fnChkOptnLoadQty($(this));	// 골라담기 총 수량 체크
		} else {
			fnChkOptnQty($(this));	//수량체크
		}
	}).on('keyup, blur', '.optnCnt', function(){
		$(this).val($(this).val().replace(/[^0-9]/gi, "")*1);

		if( prodBasicInfo.ONLINE_PROD_TYPE_CD == "04" ) {
			if($(this).val() == "") $(this).val("0");
			fnChkOptnLoadQty();	// 골라담기 총 수량 체크
		} else {
			if($(this).val() == "" || $(this).val() == "0" ) $(this).val("1");
			fnChkOptnQty($(this));	//수량체크
		}
	}).on('keypress, keydown','.optnCnt', function(event){
		return isOnlyNumberInput(event, false);
	}).on('click','.optnCnt', function(){
		$(this).val('');
		return false;
	});

	// 옵션 변경시
	$('#quickmenu-bar .inner-scroll').on('change', 'select[name!=HOPE_DELI_PSBT_DD]', function(){
		var objId = $(this).prop("id");
		var objNm = (objId.substring(objId.length-3, objId.length) == "Dtl") ? objId.substring(0, objId.length-3) : objId;
		var index = $('#quickmenu-bar .inner-scroll select').index($(this));

		if( objNm == "ItemColor" ) {
			// 색상 변경시 사이즈 조회
			getItemSize($(this));
			// 변경시 selectbox 초기화
			//$(this).parents(".option-select").siblings().find("select option:eq(0)").prop("selected", true);
		} else if( objNm == "optnAsso" || objNm == "optnDeal" ) {
			// 연관상품/딜상품 옵션조회
			 $('#quickmenu-bar .inner-scroll').find("#optnAreaDtl").empty();
			getOptionDetailAjax($(this));
		} else {
			// 가상장바구니 담기
			if( objNm == "optnCtpd" ) {
				// 구성품
				fnMakeVirtualBasketList_CTPD($(this));
			}  else if( objId.substring(objId.length-3, objId.length) == "Dtl") {
				// 연관/딜 상품 옵션선택
				fnMakeVirtualBasketList_OPTN($(this));
			}else {
				// 변경시 selectbox 초기화
				fnResetOptn('after', index);

				// 기본 가상 장바구니 남기
				fnMakeVirtualBasketList($(this));
			}
		}
	});

});

	/*
	 * 골라담기 수량 체크
	 */
	function fnChkOptnLoadQty(obj) {
		console.log("mobileProductDetailOption.js 진입!!!!!!!!!!");
		var optionArea = $("#quickmenu-bar .inner-scroll");
		var PROD_FLAG = $("#FormProdInfo #PROD_FLAG").val();
		var ORDER_COUNT_LEFT = $("#FormProdInfo #ORDER_COUNT_LEFT").val();
		var setQty = Number($(optionArea).find("#OPTN_LOAD_SET_QTY").val());
		var optnLoadQty=0, leftQty=0;
		var leftQtyTxt = "";
		var validHolidayCategory = $("#FormProdInfo #validHolidayCategory").val();

		var orderCount		= Number($(obj).siblings("input[name=optnLoadQty]").val());
		var itemsProdCd	= $(optionArea).find("input[name=itemsProdCd]").val();
		var itemsCode		= $(optionArea).find("input[name=itemsCode]").val();

		var getMaxOrderQtyArr		= getMaxOrderQty(itemsProdCd, itemsCode);
		var maxOrderAbleGubun	= getMaxOrderQtyArr[0].stockGb;
		var maxOrderAbleQty		= Number(getMaxOrderQtyArr[0].maxOrderAbleQty) * setQty;
		var minOrderAbleQty			= Number(getMaxOrderQtyArr[0].minOrderAbleQty) * setQty;

		if( orderCount < 0  || null == orderCount) {
			//alert("주문수량이 옳바르지 않습니다.");
			alert( view_messages.fail.orderCntFail );
			return false;
		}

		if( itemsCode == "" ) {
			//alert("상품이 없습니다! (단품코드 미입력!)");
			alert( view_messages.error.noItem );
			return false;
		}

		// 선택된 총 수량 setting
		for(var i=0; i<$(optionArea).find("input[name=optnLoadQty]").length; i++) {
			optnLoadQty += Number($(optionArea).find("input[name=optnLoadQty]:eq("+i+")").val());
		}
		$(optionArea).find("input[name=OrderCount]").val(optnLoadQty);

		// 선택가능 수량
		var tempQty=Number($(optionArea).find("input[name=OrderCount]").val())-maxOrderAbleQty;

		if( PROD_FLAG == "G") {
			var leftCount =Number(ORDER_COUNT_LEFT); //남은수량
			if(leftCount<optnLoadQty){
				alert( view_messages.fail.orderCntOver );
				$(obj).siblings("input[name=optnLoadQty]").val(Number($(obj).siblings("input[name=optnLoadQty]").val())-tempQty);
				$(optionArea).find("input[name=OrderCount]").val(maxOrderAbleQty);
				return false;
			}
	    }

		if( optnLoadQty > maxOrderAbleQty ) {
			if( maxOrderAbleGubun == 2 ){
				if(validHolidayCategory != null && validHolidayCategory !="" && $("#FormProdInfo #CATEGORY_ID").val().substring(0,8) == validHolidayCategory)
				{
					var prod_nm = encodeURIComponent($("#FormProdInfo #PROD_NM").val());
					var bigbuy = confirm(fnJsMsg(view_messages.error.productOrderQty, minOrderAbleQty,  maxOrderAbleQty)+"\n명절 선물세트 대량구매 문의를 통해 문의하시면, 더 좋은 가격에 구매가 가능합니다.\n대량구매로 이동하시겠습니까?");
					if(bigbuy)
					{
						document.location.href= "/mobile/anniv/mobileAnnivGuide.do?guideType=Question&ProductNM="+prod_nm+"&SITELOC=OZ034";
						$('#basket').unbind('click');
						$('#purchase').unbind('click');
					}
				}else{
					alert("고객님 배송점포에 현재 준비된 수량은 "+maxOrderAbleQty+"개입니다.");
				}
			}else{
				if(validHolidayCategory != null && validHolidayCategory !="" && $("#FormProdInfo #CATEGORY_ID").val().substring(0,8) == validHolidayCategory)
				{
					var prod_nm = encodeURIComponent($("#FormProdInfo #PROD_NM").val());
					var bigbuy = confirm(fnJsMsg(view_messages.error.productOrderQty, minOrderAbleQty,  maxOrderAbleQty)+"\n명절 선물세트 대량구매 문의를 통해 문의하시면, 더 좋은 가격에 구매가 가능합니다.\n대량구매로 이동하시겠습니까?");
					if(bigbuy)
					{
						document.location.href= "/mobile/anniv/mobileAnnivGuide.do?guideType=Question&ProductNM="+prod_nm+"&SITELOC=OZ034";
						$('#basket').unbind('click');
						$('#purchase').unbind('click');
					}
				}else{
					alert( fnJsMsg(view_messages.error.productOrderQty, minOrderAbleQty,  maxOrderAbleQty) );
				}

			}

			$(obj).siblings("input[name=optnLoadQty]").val(Number($(obj).siblings("input[name=optnLoadQty]").val())-tempQty);
			$(optionArea).find("input[name=OrderCount]").val(maxOrderAbleQty);

			getTotAmt();	// set 가격정보
			return false;
		}else if(validHolidayCategory != null && validHolidayCategory !="" && optnLoadQty>=300 && $("#FormProdInfo #CATEGORY_ID").val().substring(0,8) == validHolidayCategory){
			var prod_nm = encodeURIComponent($("#FormProdInfo #PROD_NM").val());
			var bigbuy = confirm(fnJsMsg(view_messages.error.productOrderQty, minOrderAbleQty,  maxOrderAbleQty)+"\n명절 선물세트 대량구매 문의를 통해 문의하시면, 더 좋은 가격에 구매가 가능합니다.\n대량구매로 이동하시겠습니까?");
			console.log(33);
			if(bigbuy)
			{
				document.location.href= "/mobile/anniv/mobileAnnivGuide.do?guideType=Question&ProductNM="+prod_nm+"&SITELOC=OZ034";
				$('#basket').unbind('click');
				$('#purchase').unbind('click');
			}
		}

		// 선택된 총 수량 setting
		optnLoadQty=0;
		for(var i=0; i<$(optionArea).find("input[name=optnLoadQty]").length; i++) {
			optnLoadQty += Number($(optionArea).find("input[name=optnLoadQty]:eq("+i+")").val());
		}
		$(optionArea).find("input[name=OrderCount]").val(optnLoadQty);

		if( optnLoadQty % setQty != 0 || optnLoadQty == 0 ) {
			leftQty = setQty - (optnLoadQty % setQty);
			leftQtyTxt = "(상품 <em class='point1'>"+setComma(leftQty)+"개</em>를 더 선택해주세요.)";
		}

		$(optionArea).find("#leftQty").html(leftQtyTxt);

		getTotAmt();			// set 가격정보
		changeCoupon();	// reset 쿠폰혜택리스트
	}

	// 옵션상세 조회
	function getOptionDetailAjax(obj) {
		//console.log("### 연관/딜 옵션상세 조회 시작 ###");
		var optionArea = $("#quickmenu-bar .inner-scroll");
		var selValueArr = $(obj).val().split("|");	// [0] : itemsProdCd / [1] : itemsIndex
		var selProdCd = "", selItemIndex = "", optionYn="N";
		var params = {};
		var objId = $(obj).prop("id");

		// 기존 옵션 삭제
		$(optionArea).find("."+objId).next("select").remove();

		if( $("#FormProdInfo #OPTION_YN").val() == "Y" && $("#quickmenu-bar .inner-scroll div[name=virtual-basket]").length < 1) {
			alert("본품을 먼저 선택해주세요.");
			$(optionArea).find("select option:eq(0)").prop("selected", true);
			return false;
		}

		if(selValueArr.length > 1 && selValueArr != "") {
			selProdCd = selValueArr[0];
			selItemIndex = selValueArr[1];
		} else {
			return;
		}

		$.each(optnProdList, function (index, optnProd) {
			if (optnProd.itemsProdCd == selProdCd && optnProd.itemsIndex == selItemIndex ) {
				optionYn = optnProd.itemsOptionYn;
				return false;
			}
		});

		optnItemList = new Array();

		if( optionYn == "Y" ) {
			// 옵션 조회

			params["ProductCD"]			= $("#FormProdInfo #PROD_CD").val();
			params["CategoryID"]			= $("#FormProdInfo #CATEGORY_ID").val();
			params["AssoCD"]				= selProdCd;
			params["ProdLinkKindCd"]	= $("#FormProdInfo #PROD_LINK_KIND_CD").val();

			$.getJSON(_LMAppUrlM+"/mobile/product/ajax/mobileProductOptionDetailAjax.do", params)
			.done(function(data) {
	    		callBack_$getOptionDetailAjax(data);
			});

		} else {
			// 단품 가상장바구니 담기
			fnMakeVirtualBasketList_OPTN($(obj));
		}
	}

	// 옵션상세조회 결과
	function callBack_$getOptionDetailAjax(data) {

		var optionArea = $("#quickmenu-bar .inner-scroll");

		for (var i in data.OptnItemList) {
			var optnItem = data.OptnItemList[i];

			var OPTN_SALE_PRC = (optnItem.SELL_PRC=="")?0:optnItem.SELL_PRC;
			var OPTN_CURR_SELL_PRC = (optnItem.CURR_SELL_PRC=="")?0:optnItem.CURR_SELL_PRC;
			var OPTN_MAX_PROMOTION = (optnItem.MAX_PROMOTION=="")?0:optnItem.MAX_PROMOTION;
			var OPTN_MAX_PURCHASE_PRC = (optnItem.OPTN_MAX_PURCHASE=="")?0:optnItem.OPTN_MAX_PURCHASE;

			//에누리가 표시여부
			if( optnItem.PRICE_TYP != "Y" ) {
				OPTN_SALE_PRC = OPTN_CURR_SELL_PRC;
			}

			// 구매예정가 셋팅
			if( Number(optnItem.MAX_PURCHASE) < Number(OPTN_CURR_SELL_PRC) && Number(optnItem.MAX_PURCHASE) > 0 ) {
				OPTN_MAX_PURCHASE_PRC = optnItem.MAX_PURCHASE;
			}

			var itemInfoDtl = new setItem(
					optnItem.ITEM_CD,						optnItem.COLOR_CD,					optnItem.COLOR_NM,				optnItem.SZ_CD,						optnItem.SZ_NM,
					optnItem.OPTN_DESC,				OPTN_SALE_PRC,						OPTN_CURR_SELL_PRC,			optnItem.MAX_PROMOTION,								optnItem.MD_SRCMK_CD,
					optnItem.ABSENCE_YN,				optnItem.AVAILABLE_STOCK,		optnItem.STOCK_YN,				'0',										'',
					'',												optnItem.PROD_CD,					optnItem.PROD_NM,				optnItem.OPTION_YN,		'index'+i,
					optnItem.MAX_ORD_PSBT_QTY,	optnItem.MIN_ORD_PSBT_QTY,	OPTN_MAX_PURCHASE_PRC );

				optnItemList[i] = itemInfoDtl;

				// 재고 체크를 위한 배열 추가. getMaxOrderQty()에서 사용
				if( $.grep(optnItemListAll, function(n, i) {
					return ( n.itemsProdCd == itemInfoDtl.itemsProdCd && n.itemsCode == itemInfoDtl.itemsCode );
				}).length < 1 ) {
					optnItemListAll.push(itemInfoDtl);
				}
		}

		var optnItem = data.OptnItemList[0];
		var optionTitle = "";

		if($("#FormProdInfo #PROD_LINK_KIND_CD").val()  == "05") {
			optionTitle = "연관상품 ";
		}

		if( "Y" != optnItem.VARIATION_YN && null != optnItem.NFOML_VARIATION_DESC && "" != optnItem.NFOML_VARIATION_DESC ) {
			//1.비정규 베리에이션
			var optnItemListTemp = new Array();
			var cnt = 0;
			var descArr = (optnItem.NFOML_VARIATION_DESC).replace( /\;$/, '' ).split(";");

			for (var i in descArr) {
				optnItemListTemp[0] = optnItemList[0];
				if(i ==0) {
					optnItemList[0].itemsOptnDesc = descArr[i];
				}else {
					optnItemList[i] = $.extend(true, {}, optnItemListTemp[0])
					optnItemList[i].itemsOptnDesc = descArr[i];
				}

				optnItemList[cnt].itemsIndex = "index"+i;
			}

			// 옵션 생성
			fnMakeOptn("S", optionTitle+"옵션", optnItemList, "IRREGULAR_VARIATION_MEMODtl", "required");
		}

		if( "Y" == optnItem.VARIATION_YN) {
			if( '02' == optnItem.PROD_DIVN_CD || ( '01' == optnItem.PROD_DIVN_CD && optnItem.PROD_CD.substring(0, 1) == "V" ) ){
				//2.원앤원
				// 옵션 생성
				fnMakeOptn("S", optionTitle+"옵션", optnItemList, "ItemDescDtl", "required");
			} else {
				//3.색상/사이즈
				var colorArr = [];
				// 옵션 생성
				fnMakeOptn("S", optionTitle+"옵션", optnItemList, "ItemColorDtl", "required");
			}
		}
		//console.log("################## 끝 옵션상세 조회");
	}

function fnChkOptnQty(obj) {
//	console.log("##### fnChkOptnQty() 옵션수량 Validation #####");

	var inputs = $(obj).siblings("input");
	var PROD_FLAG = $("#FormProdInfo #PROD_FLAG").val();
	var ORDER_COUNT_LEFT = $("#FormProdInfo #ORDER_COUNT_LEFT").val();
	var validHolidayCategory = $("#FormProdInfo #validHolidayCategory").val();

	var orderCount		= Number($(inputs).siblings("input[name=OrderCount]").val());
	var currSalePrc		= Number($(inputs).siblings("input:hidden[name=itemsCurrSalePrc]").val());
	var itemsProdCd	= $(inputs).siblings("input:hidden[name=itemsProdCd]").val();
	var itemsCode		= $(inputs).siblings("input:hidden[name=itemsCode]").val();

	if(prodBasicInfo.PROD_CD.indexOf("W") == 0) {
		itemsProdCd = prodBasicInfo.PROD_CD;
	}

	var getMaxOrderQtyArr		= getMaxOrderQty(itemsProdCd, itemsCode);
	var maxOrderAbleGubun	= getMaxOrderQtyArr[0].stockGb;
	var maxOrderAbleQty		= getMaxOrderQtyArr[0].maxOrderAbleQty;
	var minOrderAbleQty			= getMaxOrderQtyArr[0].minOrderAbleQty;

	if( orderCount == 0 || orderCount == "" ) {
//		alert("주문수량이 옳바르지 않습니다.");
		alert( view_messages.fail.orderCntFail );
		return false;
	}

	if( itemsCode == "" ) {
		//alert("상품이 없습니다! (단품코드 미입력!)");
		alert( view_messages.error.noItem );
		return false;
	}

	if( PROD_FLAG == "G") {
		var leftCount =Number(ORDER_COUNT_LEFT); //남은수량
		if(leftCount<orderCount){
			alert( view_messages.fail.orderCntOver );
			$(inputs).siblings("input[name=OrderCount]").val(leftCount);
			return false;
		}
    }

	var rtn_flag=true;
	if( orderCount < minOrderAbleQty ) {
		alert( fnJsMsg(view_messages.error.productOrderQty, minOrderAbleQty,  maxOrderAbleQty) );

		$(inputs).siblings("input[name=OrderCount]").val(minOrderAbleQty);
		orderCount = minOrderAbleQty;
		rtn_flag = false;
	} else if( orderCount > maxOrderAbleQty ) {
		if( maxOrderAbleGubun == 2 ){
			if(validHolidayCategory != null && validHolidayCategory !="" && $("#FormProdInfo #CATEGORY_ID").val().substring(0,8) == validHolidayCategory)
			{
				var prod_nm = $("#FormProdInfo #PROD_NM").val();
				var bigbuy = confirm(fnJsMsg(view_messages.error.productOrderQty, minOrderAbleQty,  maxOrderAbleQty)+"\n명절 선물세트 대량구매 문의를 통해 문의하시면, 더 좋은 가격에 구매가 가능합니다.\n대량구매로 이동하시겠습니까?");
				if(bigbuy)
				{
					document.location.href= "/mobile/anniv/mobileAnnivGuide.do?guideType=Question&ProductNM="+prod_nm+"&SITELOC=OZ034";
					$('#basket').unbind('click');
					$('#purchase').unbind('click');
				}
			}else{
				alert("고객님 배송점포에 현재 준비된 수량은 "+maxOrderAbleQty+"개입니다.");
			}
		}else{
			if(validHolidayCategory != null && validHolidayCategory !="" && $("#FormProdInfo #CATEGORY_ID").val().substring(0,8) == validHolidayCategory)
			{
				var prod_nm = $("#FormProdInfo #PROD_NM").val();
				var bigbuy = confirm(fnJsMsg(view_messages.error.productOrderQty, minOrderAbleQty,  maxOrderAbleQty)+"\n명절 선물세트 대량구매 문의를 통해 문의하시면, 더 좋은 가격에 구매가 가능합니다.\n대량구매로 이동하시겠습니까?");
				if(bigbuy)
				{
					document.location.href= "/mobile/anniv/mobileAnnivGuide.do?guideType=Question&ProductNM="+prod_nm+"&SITELOC=OZ034";
					$('#basket').unbind('click');
					$('#purchase').unbind('click');
				}
			}else{
				alert( fnJsMsg(view_messages.error.productOrderQty, minOrderAbleQty,  maxOrderAbleQty) );
			}
		}

		$(inputs).siblings("input[name=OrderCount]").val(maxOrderAbleQty);
		orderCount = maxOrderAbleQty;
		rtn_flag = false;
	}else if(validHolidayCategory != null && validHolidayCategory !="" && orderCount >= 300 && $("#FormProdInfo #CATEGORY_ID").val().substring(0,8) == validHolidayCategory){
		var prod_nm = $("#FormProdInfo #PROD_NM").val();
		var bigbuy = confirm(fnJsMsg(view_messages.error.productOrderQty, minOrderAbleQty,  maxOrderAbleQty)+"\n명절 선물세트 대량구매 문의를 통해 문의하시면, 더 좋은 가격에 구매가 가능합니다.\n대량구매로 이동하시겠습니까?");
		if(bigbuy)
		{
			document.location.href= "/mobile/anniv/mobileAnnivGuide.do?guideType=Question&ProductNM="+prod_nm+"&SITELOC=OZ034";
			$('#basket').unbind('click');
			$('#purchase').unbind('click');
		}
	}

	var itemPrice = currSalePrc * orderCount;

	if($(inputs).siblings("input[id=OrderCountMain]").length < 1){ //옵션 상품일 경우에만 수량별 가격 변경.
		$(inputs).parent().siblings(".point1").find(".number").text(setComma(itemPrice));
	}

	if($(obj).prop("id") != "mainBtn" && $(obj).prop("id") != "OrderCountMain") {
		fnOptnQtySync($(inputs), "L");
	} else {
		fnOptnQtySync($(inputs), "M");
	}

	getTotAmt();			// set 가격정보
	changeCoupon();	// reset 쿠폰혜택리스트

	return rtn_flag;
}

//기존 재고량을 무시하고 재고FUNCTION 에서 조회하여 가져온다.
function getMaxOrderQty(itemsProdCd, itemsCd) {
	var stockYN = "Y";
	var availableStock = 0;
	var MAX_ORDER_ABLE_QTY = 0; //구매할수 있는 최대 수량
	var MIN_ORDER_ABLE_QTY = 0; //구매할수 있는 최소 수량
	var itemExistYn = "N";

	for(var i=0; i<itemList.length; i++) {
		if( itemList[i].itemsProdCd == itemsProdCd && itemList[i].itemsCode == itemsCd ) {
			availableStock				= itemList[i].itemsAvailableStock; // 재고수량(남은 재고 수량)
			stockYN							= itemList[i].itemsStockYN; // 재고관리여부 Y자동,N수동
			MAX_ORDER_ABLE_QTY	= itemList[i].itemsMaxOrdPsbtQty;
			MIN_ORDER_ABLE_QTY	= itemList[i].itemsMinOrdPsbtQty;
			itemExistYn 		= "Y";
			break;
		}
	}

	if(itemExistYn == "N" ) {
		for(var i=0; i<optnProdList.length; i++) {
			if( optnProdList[i].itemsProdCd == itemsProdCd && optnProdList[i].itemsCode == itemsCd ) {
				availableStock				= optnProdList[i].itemsAvailableStock; // 재고수량(남은 재고 수량)
				stockYN							= optnProdList[i].itemsStockYN; // 재고관리여부 Y자동,N수동
				MAX_ORDER_ABLE_QTY	= optnProdList[i].itemsMaxOrdPsbtQty;
				MIN_ORDER_ABLE_QTY	= optnProdList[i].itemsMinOrdPsbtQty;
				itemExistYn 					= "Y";
				break;
			}
		}
	}

	if(itemExistYn == "N" ) {
		for(var i=0; i<optnItemListAll.length; i++) {
			if( optnItemListAll[i].itemsProdCd == itemsProdCd && optnItemListAll[i].itemsCode == itemsCd ) {
				availableStock				= optnItemListAll[i].itemsAvailableStock; // 재고수량(남은 재고 수량)
				stockYN							= optnItemListAll[i].itemsStockYN; // 재고관리여부 Y자동,N수동
				MAX_ORDER_ABLE_QTY	= optnItemListAll[i].itemsMaxOrdPsbtQty;
				MIN_ORDER_ABLE_QTY	= optnItemListAll[i].itemsMinOrdPsbtQty;
				break;
			}
		}
	}

	var arry = [];

	if (stockYN == "Y"){
		//최대값보다 재고량이 많다면 최대값을 넘긴다  아니면 남은 재고량을 넘긴다.
		if (Number(availableStock) > Number(MAX_ORDER_ABLE_QTY)){	//정상
			arry.push({
				"maxOrderAbleQty" : MAX_ORDER_ABLE_QTY,
				"minOrderAbleQty" : MIN_ORDER_ABLE_QTY,
				"stockGb" : "1"
			})
		} else {
			//재고부족으로 남은재고량만 넘김
			arry.push({
				"maxOrderAbleQty" : availableStock,
				"minOrderAbleQty" : MIN_ORDER_ABLE_QTY,
				"stockGb" : "2"
			})
		}
	}else{
		//재고관리 안함
		arry.push({
				"maxOrderAbleQty" : MAX_ORDER_ABLE_QTY,
				"minOrderAbleQty" : MIN_ORDER_ABLE_QTY,
				"stockGb" : "3"
			})
	}
	return arry;
}

function fnOptnQtySync(inputs, gubun) {

	var itemsProdCd = $(inputs).siblings("input:hidden[name=itemsProdCd]").val();
	var itemsIndex = $(inputs).siblings("input:hidden[name=itemsIndex]").val();
	var orderCount = Number($(inputs).siblings("input[name=OrderCount]").val());
	var currSalePrc = Number($(inputs).siblings("input:hidden[name=itemsCurrSalePrc]").val());
	var itemPrice = currSalePrc * orderCount;

	if(gubun == "M") {
		var optionArea = $(".relation-option .option-list");
		var objItemLi = $(optionArea).find("li");

		for(var i=0; i<$(objItemLi).length; i++) {
			if( $(optionArea).find("li:eq("+i+")").prop("id") == itemsProdCd && $(optionArea).find("li:eq("+i+")").prop("title") == itemsIndex ) {
				$(optionArea).find("li[id='"+itemsProdCd+"'][title='"+itemsIndex+"'] .select-item").find("input[name=OrderCount]").val(orderCount);
				$(optionArea).find("li[id='"+itemsProdCd+"'][title='"+itemsIndex+"'] .select-item").find("span.price").text(setComma(itemPrice)+"원");
				break;
			}
		}
	} else {
		var optionArea = $(".relation-option .option-select");

		if( $(optionArea).find("input:hidden[name=itemsProdCd]").val() == itemsProdCd && $(optionArea).find("input:hidden[name=itemsIndex]").val() == itemsIndex) {
			$(optionArea).find("input[name=OrderCount]").val(orderCount);
			$(optionArea).find("span.price").text(setComma(itemPrice)+"원");
		}
	}
}

/*
 * 옵션 초기화
 * gb :  all-전체, after-index 이후
 */
function fnResetOptn(gb, index) {
//	console.log("옵션초기화");
	var optionArea = $("#quickmenu-bar .inner-scroll");

	if( gb == 'all' ) {
		// 첫번째 select부터 초기화
		$(optionArea).find("select:eq(0)").find("option:eq(0)").prop("selected", true);
		// 옵션영역 삭제
		$(optionArea).find("#optnAreaDtl").empty();
	}
	// 선택된 select 이후부터 초기화
	$(optionArea).find("select:gt("+index+")").find("option:eq(0)").prop("selected", true);

	if( $(optionArea).find("select").prop("name") == "ItemSize" ) {
		$(optionArea).find("select").find("option:gt(0)").remove();
	}

	getTotAmt();	// set 가격정보
}

/*
 * 옵션 생성
 * fnMakeOptn(optionType, optionTitle, itemList, selectId, required)
 * optionType : I-input, S-select
 * optionTitle : 옵션타이틀
 * itemList : 옵션리스트
 * selectNm : Select Object ID
 * required : 필수여부. 필수="required", 필수아님=""
*/
function fnMakeOptn(optionType, optionTitle, itemOptionList, selectId, required) {
//	console.log("##### Start fnMakeOptn()");
	var optionArea = $("#quickmenu-bar .inner-scroll");
	var minQty = $("#FormProdInfo #MIN_ORD_PSBT_QTY").val();
	var optnStr="";
	var btnAbsenceYN= "N";	// 품절여부
	var selectNm = (selectId.substring(selectId.length-3, selectId.length) == "Dtl") ? selectId.substring(0, selectId.length-3) : selectId;

	if( optionType == "I" ) {
		// 옵션없는 단품
		var i=0;
		var itemsCode			= itemOptionList[i].itemsCode;			//단품코드
		var itemsCurrSalePrc	= itemOptionList[i].itemsCurrSalePrc;	//판매가
		var itemsProdCd		= itemOptionList[i].itemsProdCd;			//인터넷상품코드
		var itemsProdNm		= itemOptionList[i].itemsProdNm;		//인터넷상품명
		var itemsIndex			= itemOptionList[i].itemsIndex;			//Index
		var itemsMinOrdPsbtQty	= itemOptionList[i].itemsMinOrdPsbtQty;			// 최소구매수량
		var itemsMaxPurchasePrc	= itemList[i].itemsMaxPurchasePrc;	//구매예정가

		optnStr += '	<div class="option-select mainItem" name="virtual-basket" id="'+itemsProdCd+'|'+itemsIndex+'">';
		optnStr += '		<div class="goodsadded">';
		optnStr += '			<p class="prod-nm hidden">본품 : '+itemsProdNm+'</p>';
		optnStr += '			<div class="wrap-spinner">';
		optnStr += '				<div class="spinner">';
		optnStr += '					<button type="button" class="minus down"><i class="icon-sum-minus">감소</i></button>';
		optnStr += '					<input type="tel" name="OrderCount" id="OrderCountMain" title="수량" class="optnCnt" value="'+itemsMinOrdPsbtQty+'" maxlength="3" style="ime-mode:disabled" />';
		optnStr += '					<input type="hidden" name="itemsProdCd" id="itemsProdCd" value="'+itemsProdCd+'"/>';
		optnStr += '					<input type="hidden" name="itemsCode" id="itemsCode" value="'+itemsCode+'"/>';
		// 구매예정가
		if( Number(itemsMaxPurchasePrc) > 0 ) itemsCurrSalePrc = itemsMaxPurchasePrc;
		optnStr += '					<input type="hidden" name="itemsCurrSalePrc" id="itemsCurrSalePrc" value="'+itemsCurrSalePrc+'"/>';
		optnStr += '					<button type="button" class="plus up"><i class="icon-sum-plus">추가</i></button>';
		optnStr += '				</div>';
		optnStr += '				<p class="point1"><em class="number">'+setComma(itemsCurrSalePrc*itemsMinOrdPsbtQty)+'</em>원</p>';
		optnStr += '			</div>';
		optnStr += '		</div>';
		optnStr += '	</div>';

		$(optionArea).append(optnStr);

		if( itemOptionList[i].itemsAvailableStock < 1 || itemOptionList[i].itemsAbsenceYN != "N" ) btnAbsenceYN = "Y";

	} else if( optionType == "S" ) {
		// 옵션
		var totalStockCnt=0;
		var optnList="";
		var optnTitle = "선택하세요";
		var optionTitle = itemList[0].itemOptionTitle == "" ? '옵션' : itemList[0].itemOptionTitle;
		if( selectNm == "ItemColor") optnTitle = optionTitle;
		if( selectNm == "optnDeal")  optnTitle = "상품 선택";
		if( selectNm == "optnCtpd")  optnTitle = "(선택) 구성품 추가";
		if( selectNm == "optnAsso")  optnTitle = "(선택) 연관상품 추가";

		if( selectNm != "ItemSize") {
			// 옵션-사이즈가 아닐경우만 영역셋팅
			if(selectId == 'optnDeal'){ // 딜 상품일 경우
				optnStr = '	<div class="option-select '+selectId+'">';
				if(optionTitle != "") {
					optnStr += '		<p class="title"><i class="icon-common-optcheck"></i> 선택</p>';
				}
				optnStr += '	    <div class="wrap-select-layer">';
				optnStr += '	        <div id="titleArea">';
				optnStr += '	        <div class="select required"><button type="button" class="select-btn">상품을 선택하세요</button></div>';
				optnStr += '	        </div>';
				optnStr += '	            <div class="select-layer">';
				optnStr += '		            <ul id='+selectId+'>';
				optnStr += '		            </ul>';
				optnStr += '	            </div>';
				optnStr += '	        </div>';
				optnStr += '	    </div>';
				optnStr += '	</div>';
			}else{ // 딜 상품이 아닐 경우
				optnStr = '	<div class="option-select '+selectId+'">';
				if(optionTitle != "") {
					optnStr += '		<p class="title"><i class="icon-common-optcheck"></i> '+optionTitle+'</p>';
				}

				optnStr +=  '	<div class="select '+required+'">';
				optnStr += '		<select name="'+selectNm+'" id="'+selectId+'" '+required+'>';
				optnStr += '		<option value="">'+optnTitle+'</option>';
				optnStr += '		</select>';
				optnStr += '	</div>';
				optnStr += '</div>';
			}

			if(selectId.substring(selectId.length-3, selectId.length) == "Dtl") {
				$(optionArea).find("#optnAreaDtl").append(optnStr);
			} else {
				//$(optionArea).find("div[name=virtual-basket]:first").before(optnStr);
				$(optionArea).append(optnStr);
			}
			if((selectNm == "optnCtpd" || selectNm == "optnAsso" || selectNm == "optnDeal") && $(optionArea).find(".mainItem").length > 0 ) {
				// 본품단품+구성품/연관/딜 상품 일 경우
				$(optionArea).find("."+selectId).insertBefore($(optionArea).find(".mainItem"));
				$(optionArea).find(".prod-nm").removeClass("hidden");
			}

			if(selectId =="optnAsso" || selectId =="optnDeal") {
			// 연관/딜 상품 옵션 영역 set
				$(optionArea).find("."+selectId).append("<div style='margin-top:8px;' id='optnAreaDtl'></div>");
			}

		} else {
			optnStr =  '<div class="select '+required+'">';
			optnStr += '<select name="'+selectNm+'" id="'+selectId+'" '+required+'>';
			optnStr += '<option value="">'+optnTitle+'</option>';
			optnStr += '</select>';
			optnStr += '</div>';
			$(optionArea).find(".option-select").find("."+selectId).append(optnStr);
		}

		if( selectNm == "ItemColor" ) {
			var colorArr = [];
			var dealPrice = "";

			$.each(itemOptionList, function (index, item) {
				if(item.itemsProdCd.indexOf("W") == 0 && Number(item.itemsMaxPromotion) < Number(item.itemsCurrSalePrc) && Number(item.itemsMaxPromotion) > 0) {
					dealPrice = " &nbsp;&nbsp;(최저가 "+setComma(item.itemsMaxPromotion)+" 원)";
				}

				colorArr.push({"colorCd": item.itemsColor, "colorNm": item.itemsColorNm, "dealPrice": dealPrice});
				totalStockCnt += item.itemsAvailableStock;
			});

			var colorRsult = [];
			var totalColorCnt=0;
			$.each(colorArr, function (index, color) {
				if ($.grep(colorRsult, function(e) {
					return e.colorCd === color.colorCd;
				}).length == 0) {
					colorRsult.push(color);
					optnList+="<option value='"+color.colorCd+"'>"+color.colorNm + color.dealPrice + "</option>";
					totalColorCnt++;
				}
			});

			//TODO: 20170523 값이 없는 단일색상컬러 예외처리
			try {
				if( totalColorCnt == 1 ) {
					var tmpColor = colorRsult[0];
					var tmpColorCd = tmpColor.colorCd;
					var tmpColorNm = tmpColor.colorNm;

					if( tmpColorNm == null || tmpColorNm.replace( /(\s*)/g, '') == '' ) {
						tmpColorCd = 'ONE COLOR';
						tmpColorNm = 'ONE COLOR';

						if( itemList != null && itemList.length > 0 ) {
							$.each(itemList, function(xidx) {
								itemList[xidx].itemsColor = tmpColorCd;
								itemList[xidx].itemsColorNm = tmpColorNm;
							});
						}
						if( itemOptionList != null && itemOptionList.length > 0 ) {
							$.each(itemOptionList, function(xidx) {
								itemOptionList[xidx].itemsColor = tmpColorCd;
								itemOptionList[xidx].itemsColorNm = tmpColorNm;
							});
						}

						optnList ="<option value='"+tmpColorCd+"'>"+tmpColorNm+"</option>";
					}
				}
			} catch(e1) {
			}

		} else {
			for(var i=0; i< itemOptionList.length; i++) {
				var itemsStockText="", itemsOptnNm="", disabledStr="";

				if(selectNm == "optnCtpd" || selectNm =="optnAsso" || selectNm == "optnDeal") {
					itemsOptnNm = itemOptionList[i].itemsProdNm;
					if (itemOptionList[i].itemsOptionYn == "N" && (itemOptionList[i].itemsAvailableStock < 1 || itemOptionList[i].itemsAbsenceYN != "N" )) {
						itemsStockText = " - 품절";
						disabledStr = "disabled='disabled'";
					}
				} else {
					itemsOptnNm = itemOptionList[i].itemsProdCd.indexOf("W") == 0 ? itemOptionList[i].itemsColorNm : itemOptionList[i].itemsOptnDesc;
					if( itemOptionList[i].itemsAvailableStock < 1 || itemOptionList[i].itemsAbsenceYN != "N") {
						itemsStockText = " - 품절";
						disabledStr = "disabled='disabled'";
					}
				}
				totalStockCnt += itemOptionList[i].itemsAvailableStock;

				var optVal = itemOptionList[i].itemsProdCd+"|"+itemOptionList[i].itemsIndex;
				var dealTxt = "", dealPrice = "";

				if(selectNm == "optnDeal") {
					dealTxt = "[선택"+(i+1)+"] ";
					if( Number(itemOptionList[i].itemsMaxPromotion) < Number(itemOptionList[i].itemsCurrSalePrc) && Number(itemOptionList[i].itemsMaxPromotion) > 0) {
						dealPrice = " &nbsp;&nbsp;최저가 "+setComma(itemOptionList[i].itemsMaxPromotion)+" 원";
					}
				}
				if(selectId == 'optnDeal'){ // 딜 상품일 경우
					var temps_curr               = itemOptionList[i].itemsCurrSalePrc;
					var temps_maxPro          = itemOptionList[i].itemsMaxPromotion;
					var tempsProdCd           = itemOptionList[i].itemsProdCd;
					var tempsAbsenceYN     = itemOptionList[i].itemsAbsenceYN;
					var tempsAvailableStock = itemOptionList[i].itemsAvailableStock;
					var tempStrCd 				= itemOptionList[i].itemsStrCd;		// 2017.10.19 전희찬 추가 점포코드

					optnList += '				<li ' + (itemsStockText == '' ? '' : 'class="soldout"') + '>';
					optnList += '				    <div class="wrap-conts">';
					optnList += '				        <input class="textdealTxt" type="hidden" id="listData' +i+'" value="'+dealTxt+'"/>';
					optnList += '				        <input class="testOptnNm" type="hidden" id="listData' +i+'" value="'+itemsOptnNm+'"/>';
					optnList += '				        <input class="testIndex" type="hidden" id="listData' +i+'" value="'+'index'+i+'"/>';
					optnList += '				        <p class="title">';
					optnList += '				        	<em class="point2">'+dealTxt+'</em> '+itemsOptnNm + itemsStockText;
					optnList += '				        </p>'
					optnList += '				        <p class="price">';
					optnList += '				        <input class="testProdCd" type="hidden" id="listData' +i+'" value="'+tempsProdCd+'"/>';
					optnList += '				        <input class="testStrCd" type="hidden" id="listData' +i+'" value="'+tempStrCd+'"/>';	// 2017.10.19 전희찬 추가 점포코드
					if(Number(temps_maxPro) < Number(temps_curr) && Number(temps_maxPro)>0){
						optnList += '				            <span class="sellprice">판매가 <em class="number">'+setComma(temps_curr)+'</em>원</span>';
					    optnList += '				            <em class="fullbene">최저가 <i class="number-point">'+setComma(temps_maxPro)+'</i>원</em>';
					}else{
						optnList += '				            <em class="fullbene">판매가 <i class="number-point">'+setComma(temps_curr)+'</i>원</em>';
					}
					optnList += '				        </p>';
					optnList += '				    </div>';
					optnList += '				</li>';
				}else{ // 딜 상품이 아닐 경우
					optnList += "				<option value='"+optVal+"'"+disabledStr+">"+dealTxt+itemsOptnNm+"  ("+setComma(itemOptionList[i].itemsCurrSalePrc)+"원"+dealPrice+")"+itemsStockText+"</option>";
				}
			}
		}
		$(optionArea).find(".option-select").find("#"+selectId).append(optnList);

		if( selectNm == "ItemColor") {
			var optionSubTitle = itemList[0].itemOptionSubTitle == "" || itemList[0].itemOptionSubTitle == "색상" ? '사이즈' : itemList[0].itemOptionSubTitle;
			optnStr = '<div class="select '+required+'">';
			optnStr += '<select name="ItemSize" id="'+(selectNm==selectId?'ItemSize':'ItemSizeDtl')+'" '+required+'>';
			optnStr += '<option value="">' + optionSubTitle + '</option>';
			optnStr += '</select>';
			optnStr += '</div>';

			$(optionArea).find(".option-select."+selectId).append(optnStr);
		}
		if( totalStockCnt < 1 ) btnAbsenceYN = "Y";
	}

	// 품절버튼노출체크 - 본품만 해당
	fnBtnAbsence(btnAbsenceYN);

}


// 옵션 사이즈 조회
function getItemSize(obj) {
	//console.log("옵션 사이즈 조회");
	var optionArea = $("#quickmenu-bar .inner-scroll");
	var selectedValue = $(obj).val();
	var objSize = ($(obj).prop("id") == "ItemColorDtl") ? "ItemSizeDtl" : "ItemSize";
	var itemListSize =($(obj).prop("id") == "ItemColorDtl") ? optnItemList : itemList;

	$(optionArea).find("select[id="+objSize+"] option").remove();

	var selIndex = 0;
	var optionSubTitle = itemList[0].itemOptionSubTitle == "" || itemList[0].itemOptionSubTitle == "색상" ? '사이즈' : itemList[0].itemOptionSubTitle;
	var optnList="<option value=''>" + optionSubTitle + "</option>";
	$.each(itemListSize, function (index, item) {
		if (item.itemsColor == selectedValue ) {
			var itemsStockText = "", disabledStr="";
			if( item.itemsAvailableStock < 1 || item.itemsAbsenceYN != "N" ) {
				itemsStockText = " - 품절";
				disabledStr = "disabled='disabled'";
			}

			var optVal = item.itemsProdCd+"|"+item.itemsIndex;
			var dealPrice = "";
			if((objSize == "ItemSizeDtl" && prodBasicInfo.ONLINE_PROD_TYPE_CD == "05")
					|| objSize == "ItemSize" && prodBasicInfo.ONLINE_PROD_TYPE_CD == "11") {

				if( Number(item.itemsMaxPromotion) < Number(item.itemsCurrSalePrc) && Number(item.itemsMaxPromotion) > 0) {
					dealPrice = " &nbsp;&nbsp;최저가 "+setComma(item.itemsMaxPromotion)+" 원";
				}
			}

			var sizeName = item.itemsSizeNm || 'ONE SIZE';
			optnList+="<option value='"+optVal+"'"+disabledStr+">" + sizeName +"  ("+setComma(item.itemsCurrSalePrc)+"원"+dealPrice+")"+itemsStockText+"</option>";
		}
	});

	$(optionArea).find("select[id="+objSize+"]").append(optnList);

	// 패션 옵션 상품 품절시 미품절 상품 자동 선택. 모바일_배포리스트_20160811 운영 소스 Merge
	// 2016.09.22 주석처리. merge 후 선택된 상품이 자동 선택되면 가상장바구니에도 상품이 자동으로 담겨야하나 처리되어있지 않음.
/*	var selIndex = 0;

	var optnList="<option value=''>사이즈</option>";
	$.each(itemListSize, function (index, item) {
		if (item.itemsColor == selectedValue ) {
			var itemsStockText = "";
			if( item.itemsAvailableStock < 1 || item.itemsAbsenceYN != "N" ) {
				itemsStockText = " - 품절";
			} else {
				if(selIndex == 0)  {
					selIndex = index + 1;
				}
			}

			var optVal = item.itemsProdCd+"|"+item.itemsIndex;
			optnList+="<option value='"+optVal+"'>"+item.itemsSizeNm+"  ("+setComma(item.itemsCurrSalePrc)+"원)"+itemsStockText+"</option>";
		}
	});

	$(optionArea).find("select[id="+objSize+"]").append(optnList);
	$(optionArea).find("select[id="+objSize+"]").find("option:eq("+selIndex+")").prop("selected", true);*/

}


/*
 * 구매버튼 정보 Set
 */
function fnBtnAbsence(btnAbsenceYN) {
	// 재고가 0인 경우 버튼 제어
	if( btnAbsenceYN == "Y" ) {
		$("#quickmenu-bar .btnBuy").remove();
		$("#quickmenu-bar .btnBuy-default").remove();
		$("#quickmenu-bar .btnForgn").remove();
		$("#quickmenu-bar .btnPeri").remove();
		$("#quickmenu-bar .btnSoldOut").show();
		$("#quickmenu-bar .btnSoldOut-default").show();
	}
}

/*
 * 가상장바구니 생성 - 기본
*/
function fnMakeVirtualBasketList(obj){
//	console.log("################## 시작 가상 장바구니 생성");
	var optionArea = $("#quickmenu-bar .inner-scroll");
	var objId = $(obj).prop("id");
	var objNm = (objId.substring(objId.length-3, objId.length) == "Dtl") ? objId.substring(0, objId.length-3) : objId;
	var selValueArr = $("#"+objId+" option:selected").val().split("|");	// [0] : itemsProdCd / [1] : itemsIndex
	var PROD_LINK_KIND_CD = $("#FormProdInfo #PROD_LINK_KIND_CD").val();

	var selProdCd = "", selItemIndex = "";
	if(selValueArr.length > 1 && selValueArr != "") {
		selProdCd = selValueArr[0];
		selItemIndex = selValueArr[1];
	}

	var virtualBasket="";
	for (var i in itemList) {
		var item = itemList[i];
		if( item.itemsProdCd == selProdCd && item.itemsIndex == selItemIndex ) {
			var main_objId = selProdCd+"|"+selItemIndex;
			virtualBasket=getVirtualBasket(obj, item, main_objId, "basic","");
			break;
		}
	}

	$(optionArea).last().append(virtualBasket);

	getTotAmt();			// set 가격정보
	changeCoupon();	// reset 쿠폰혜택리스트
	//console.log("################## 끝 가상 장바구니 생성");
}

/*
 * 가상장바구니 생성 - 구성품
*/
function fnMakeVirtualBasketList_CTPD(obj){
	//console.log("################## 시작 가상 장바구니 생성 - 구성품");
	var optionArea = $("#quickmenu-bar .inner-scroll");

	//선택된 본품 조회
	var main_selValueArr,main_selProdCd="", main_selItemIndex="";

	// 선택된 구성품
	var objId = $(obj).prop("id");
	var objNm = (objId.substring(objId.length-3, objId.length) == "Dtl") ? objId.substring(0, objId.length-3) : objId;
	var selValueArr = $("#"+objId+" option:selected").val().split("|");	// [0] : itemsProdCd / [1] : itemsIndex

	if( $("#FormProdInfo #OPTION_YN").val() != "Y") {
		// 본품단품
		main_selValueArr = $(optionArea).find(".mainItem").prop("id").split("|");
	} else {
		// 본품옵션
		main_selValueArr = $(optionArea).find("select[name!="+objNm+"]:last option:selected").val().split("|");	// [0] : itemsProdCd / [1] : itemsIndex
	}

	if(main_selValueArr.length > 1 && main_selValueArr != "") {
		main_selProdCd = main_selValueArr[0];
		main_selItemIndex = main_selValueArr[1];
	}

	// 본품 선택 체크
	if( main_selProdCd == "" || main_selItemIndex =="") {
		alert( view_messages.select.selectMainProd );
		$("#"+objNm+" option:eq(0)").prop("selected", true);
		return false;
	}

	var selProdCd = "", selItemIndex = "";
	if(selValueArr.length > 1 && selValueArr != "") {
		selProdCd = selValueArr[0];
		selItemIndex = selValueArr[1];
	} else {
		return;
	}

	var virtualBasket="";
	for (var i in optnProdList) {
		var item = optnProdList[i];
		if( item.itemsProdCd == selProdCd && item.itemsIndex == selItemIndex ) {
			var main_objId=main_selProdCd+"|"+main_selItemIndex;
			virtualBasket=getVirtualBasket(obj, item, main_objId, "optnCtpd","");
			break;
		}
	}

	// 선택된 구성품이 노출될 본품상품 위치
	var ctpdArea = $(optionArea).find("div[id='"+main_selProdCd+"|"+main_selItemIndex+"']");
	$(ctpdArea).append(virtualBasket);

	getTotAmt();	// set 가격정보

	//console.log("################## 끝 가상 장바구니 생성 - 구성품");
}

/*
 * 가상장바구니 생성 - 연관/ 딜상품
*/
function fnMakeVirtualBasketList_OPTN(obj){
	//console.log("################## 시작 가상 장바구니 생성 - 연관/딜상품");
	var optionArea = $("#quickmenu-bar .inner-scroll");

	// 선택된 연관/딜 상품
	var objId = $(obj).prop("id");
	var objNm = (objId.substring(objId.length-3, objId.length) == "Dtl") ? objId.substring(0, objId.length-3) : objId;
	var selValueArr = $("#"+objId+" option:selected").val().split("|");	// [0] : itemsProdCd / [1] : itemsIndex
	var prod_gb = "optnDeal";

	if( $("#FormProdInfo #PROD_LINK_KIND_CD").val() == "05" ) {
		// 연관상품 - 가상장바구니 선택된 본품 조회
		var main_prodCnt = 0;
		$.each($(optionArea).find("div[name=virtual-basket]"), function (index, basketItem) {
			if($("#FormProdInfo #PROD_CD").val() == $(basketItem).prop("id").split("|")[0])
				main_prodCnt++;
		});

		// 본품 선택 체크
		if( main_prodCnt <  1 ) {
			alert( view_messages.select.selectMainProd );
			fnResetOptn('all', 0);
			return;
		}

		// 상품구분-연관상품
		prod_gb = "optnAsso";
	}

	// 연관/딜 상품 선택 체크
	var selProdCd = "", selItemIndex = "";
	if(selValueArr.length > 1 && selValueArr != "") {
		selProdCd = selValueArr[0];
		selItemIndex = selValueArr[1];
	} else {
		return;
	}

	var itemList = optnProdList;	// 연관/딜 상품리스트
	if(objId.substring(objId.length-3, objId.length) == "Dtl") {
		//연관/딜 옵션리스트
		itemList = optnItemList;
	}

	var virtualBasket="";
	for (var i in itemList) {
		var item = itemList[i];
		if( item.itemsProdCd == selProdCd && item.itemsIndex == selItemIndex ) {
			var main_objId=selProdCd+"|"+selItemIndex;
			virtualBasket=getVirtualBasket(obj, item, main_objId, prod_gb,"");
			break;
		}
	}

	$(optionArea).append(virtualBasket);

	getTotAmt();	// set 가격정보

	//console.log("################## 끝 가상 장바구니 생성 - 연관/딜 상품");
}

function getVirtualBasket(obj, item, main_objId, prod_gb, dealYn) {
	//console.log("################## getVirtualBasket()");
	var objId = "";
	if(dealYn == "Y"){
		objId = "optnDeal"
	}else{
		objId = $(obj).prop("id");
	}
	var optionArea = $("#quickmenu-bar .inner-scroll");
	var objNm = (objId.substring(objId.length-3, objId.length) == "Dtl") ? objId.substring(0, objId.length-3) : objId;

	var vbLen = $(optionArea).find("div[name=virtual-basket]").length;
	var vbCnt=0, vbSubCnt=0;

	// 구성품/연관 상품 본품 선택 체크
	if(objId == "optnCtpd" || objId == "optnAsso") {
		if(vbLen < 1) {
			alert( view_messages.select.selectMainProd );
			$("#"+objId+" option:eq(0)").prop("selected", true);
			return;
		}
	}

	// 재고/품절 check
	if( item.itemsAvailableStock < 1 || item.itemsAbsenceYN != "N" ) return;

	// 선택상품 중복 조회
	for(var i=0; i<vbLen; i++) {
		var findObj = "div[name=virtual-basket]:eq("+i+") ";
		var findObjId = $(optionArea).find(findObj).prop("id");

		if( findObjId == main_objId ) {
			vbCnt++;

			if(objId == "optnCtpd") {
				// 구성품 중복조회
				var vbCtpdLen = $(optionArea).find(findObj+".subadded").length;
				for(var j=0; j<vbCtpdLen; j++) {
					var findCtpdObj = "div[name=virtual-basket]:eq("+i+") ";
					if( $(optionArea).find(findCtpdObj+".subadded:eq("+j+") #itemsProdCd").val() == item.itemsProdCd && $(optionArea).find(findCtpdObj+".subadded:eq("+j+") #itemsIndex").val() == item.itemsIndex ) {
						vbSubCnt++;
					}
				}
				if(vbSubCnt > 0) {
					alert("이미 선택된 추가구성품입니다.");
					return;
				}
			}
		}
	}

	if( objNm != "optnCtpd" ) {
		if(vbCnt > 0) {
			alert("이미 선택된 옵션입니다.");
			return;
		}
	}

	var optnTitle = "옵션 : ";
	var optnClass="", optnDel="";
	if( objNm == "optnAsso" || prod_gb == "optnAsso")  optnTitle = "연관상품 : ";
	if( objNm == "optnCtpd") {optnTitle = "추가구성품 : "; optnClass = "subadded";optnDel = "Dtl";}
	if( objNm == "optnDeal") {optnTitle="[선택"+(Number(item.itemsIndex.replace("index", ""))+1)+"] ";}

	var itemOptnTitle = "";
	if(prod_gb == "optnAsso" || prod_gb == "optnDeal" ) {
		itemOptnTitle += item.itemsProdNm;
		itemOptnTitle += (item.itemsOptnDesc != "" && item.itemsOptnDesc != null)?" / "+item.itemsOptnDesc:"";
	} else {
		itemOptnTitle += (item.itemsOptnDesc == "" || item.itemsOptnDesc == null )?item.itemsProdNm:item.itemsOptnDesc;
	}
	itemOptnTitle += (item.itemsColorNm != "" && item.itemsColorNm != null)?" / "+item.itemsColorNm:"";
	itemOptnTitle += (item.itemsSizeNm != "" && item.itemsSizeNm != null)?" / "+item.itemsSizeNm:"";

	var optnStr="";
	var itemProdCd = item.itemsProdCd;
	var itemItemCd = item.itemsCode;

	if(item.itemsProdCd.indexOf("W") == 0) {
		itemProdCd = item.itemsSrcmkCd;
		itemItemCd = "001";
	}

	if( objNm != "optnCtpd")
		{ optnStr += '	<div class="option-select" name="virtual-basket" id="'+item.itemsProdCd+'|'+item.itemsIndex+'">'; }
	optnStr += '		<div class="goodsadded '+optnClass+'">';
	optnStr += '			<p>'+optnTitle+itemOptnTitle+'  ('+setComma(item.itemsCurrSalePrc)+'원)'+'</p>';
	optnStr += '			<div class="wrap-spinner">';
	optnStr += '				<div class="spinner">';
	optnStr += '					<button type="button" class="minus down"><i class="icon-sum-minus">감소</i></button>';
	optnStr += '					<input type="tel" name="OrderCount" title="수량" id="OrderCount" class="optnCnt" value="'+item.itemsMinOrdPsbtQty+'" maxlength="3" style="ime-mode:disabled" />';
	optnStr += '					<input type="hidden" name="itemsProdCd" id="itemsProdCd" value="' + itemProdCd + '"/>';
	optnStr += '					<input type="hidden" name="itemsCode" id="itemsCode" value="'+itemItemCd+'"/>';
	optnStr += '					<input type="hidden" name="itemsStrCd" id="itemsStrCd" value="'+item.itemsStrCd+'"/>';		// 2017.10.19 전희찬 추가 점포코드
	// 구매예정가
	var itemsCurrSalePrc=item.itemsCurrSalePrc;
	if( Number(item.itemsMaxPurchasePrc) > 0 ) itemsCurrSalePrc = item.itemsMaxPurchasePrc;
	optnStr += '					<input type="hidden" name="itemsCurrSalePrc" id="itemsCurrSalePrc" value="'+itemsCurrSalePrc+'"/>';
	optnStr += '					<input type="hidden" name="itemsIndex" id="itemsIndex" value="'+item.itemsIndex+'"/>';
	optnStr += '					<input type="hidden" name="itemsOptnDesc" id="itemsOptnDesc" value="'+item.itemsOptnDesc+'"/>';
	if( objNm == "IRREGULAR_VARIATION_MEMO" ) {
		optnStr += '				<input type="hidden" name="irregularVariationOptnDesc" id="irregularVariationOptnDesc" value="'+item.itemsOptnDesc+'"/>';
	}
	optnStr += '					<button type="button" class="plus up"><i class="icon-sum-plus">추가</i></button>';
	optnStr += '				</div>';
	optnStr += '				<p class="point1"><em class="number">'+setComma(itemsCurrSalePrc * item.itemsMinOrdPsbtQty)+'</em>원</p>';
	optnStr += '			</div>';
	optnStr += '			<button type="button" name="orderDel'+optnDel+'" class="icon-common-delete optnDel">삭제</button>';
	optnStr += '		</div>';
	if( objNm != "optnCtpd")
		{ optnStr += '	</div>'; }

	return optnStr;
}

//쿠폰혜택리스트 Reset
function changeCoupon() {
	var optionArea = $("#quickmenu-bar .inner-scroll");
	var promoArea = $("#prodInfoTable", ".board-goods-details");
	var ONLINE_PROD_TYPE_CD = prodBasicInfo.ONLINE_PROD_TYPE_CD;
	var totalOrderCount = 0;

	if($(promoArea).length < 1 ) return;

	if( ONLINE_PROD_TYPE_CD == "04" ) {
		// console.log("골라담기");
		var optnLoadQty = 0;
		var setQty = Number($("#OPTN_LOAD_SET_QTY").val());

		for(i=0; i<$("input[name=optnLoadQty]").length; i++) {
			optnLoadQty += Number($("input[name=optnLoadQty]:eq("+i+")").val());
		}
		totalOrderCount	= optnLoadQty / setQty;

	} else {
		if( $(optionArea).find("div[name=virtual-basket]").length > 0 ) {
			//console.log("옵션상품");
			for( var i=0; i < $(optionArea).find("div[name=virtual-basket]").length; i++) {
				var obj = $(optionArea).find("div[name=virtual-basket]:eq("+i+")");
				var orderCount = $(obj).find("input[name=OrderCount]");

				if( orderCount.length < 1 ) totalOrderCount+= 0;
				else totalOrderCount += Number(orderCount.val());
			}
		} else {
			//console.log("단품");
			var optionArea = $(".relation-option .option-select");
			var orderCount = $(optionArea).find("input[name=OrderCount]");

			if( orderCount.length < 1 ) totalOrderCount+= 0;
			else totalOrderCount	= Number(orderCount.val());
		}
	}

	$.each(eventArr, function(index, event) {
		// 즉석쿠폰 || CMS쿠폰 || ONE쿠폰 || 다둥이쿠폰
		if( event.promoType == "C04" || event.promoType == "C06" || event.promoType == "C11" || event.promoType == "C12" ) {
			var limitCnt = (event.promoType == "C04")?event.extInfo:"1";

			if( totalOrderCount < Number(limitCnt) ) {
				$(promoArea).find("#"+event.promoType).html('<th scope="row"><p class="list">'+event.title+'</p></th><td>'+event.desc+'</td>');
			} else {
				$(promoArea).find("#"+event.promoType).html('<th scope="row"><p class="list">'+event.title+'</p></th><td>'+limitCnt+'개당 '+setComma(event.gubunFlag)+event.gubunFlag_icon+' 할인가능</td>');
			}
		}
	});
}
//옵션 변경시 (딜 상품일 경우)
function fn_ajaxOption(vars){
	var optionArea = $("#quickmenu-bar .inner-scroll #titleArea");
    var textdealTxt = vars.find('.textdealTxt').attr('value');      //선택 순서
    var testOptnNm = vars.find('.testOptnNm').attr('value');  //선택 상품명
    var testProdCd = vars.find('.testProdCd').attr('value');     //선택 상품코드
    var testIndex = vars.find('.testIndex').attr('value');     //선택 상품코드

	$(optionArea).html("");
	var asTxt = '<div class="select">';
	asTxt += ' 	       <button type="button" class="select-btn">';
	asTxt += ' 	           <em class="point2">'+textdealTxt+'</em> '+testOptnNm;
    asTxt += '         </button>';
    asTxt += '    </div>';
	$(optionArea).html(asTxt);
	$('#quickmenu-bar .inner-scroll').find("#optnAreaDtl").empty();
	getOptionDetailAjax_Deal( testProdCd, testIndex );
}

//옵션상세 조회(딜 상품일 경우)
function getOptionDetailAjax_Deal(testProdCd, testIndex) {
	var optionArea = $("#quickmenu-bar .inner-scroll");
	//var selValueArr = $(obj).val().split("|");	// [0] : itemsProdCd / [1] : itemsIndex
	var selProdCd = "", selItemIndex = "", optionYn="N";
	var params = {};
	var objId = ""; //$(obj).prop("id");
	objId = "optnDeal";
	//$(optionArea).find("."+objId).next("select").remove();

	if( $("#FormProdInfo #OPTION_YN").val() == "Y" && $("#quickmenu-bar .inner-scroll div[name=virtual-basket]").length < 1) {
		alert("본품을 먼저 선택해주세요.");
		//$(optionArea).find("select option:eq(0)").prop("selected", true);
		return false;
	}
	if(testProdCd !="" && testIndex != ""){
		selProdCd     = testProdCd;
		selItemIndex  = testIndex;
	}else{
		return;
	}
	$.each(optnProdList, function (index, optnProd) {
		if (optnProd.itemsProdCd == selProdCd && optnProd.itemsIndex == selItemIndex ) {
			optionYn = optnProd.itemsOptionYn;
			return false;
		}
	});
	optnItemList = new Array();

	if( optionYn == "Y" ) { // 옵션이 있을 경우 옵션 조회 실시
		params["ProductCD"]			= $("#FormProdInfo #PROD_CD").val();
		params["CategoryID"]			= $("#FormProdInfo #CATEGORY_ID").val();
		params["AssoCD"]				= selProdCd;
		params["ProdLinkKindCd"]	= $("#FormProdInfo #PROD_LINK_KIND_CD").val();
		$.getJSON(_LMAppUrlM+"/mobile/product/ajax/mobileProductOptionDetailAjax.do", params)
		.done(function(data) {
    		callBack_$getOptionDetailAjax(data);
		});
	}else{
		// 단품 가상장바구니 담기(딜 상품 전용)
		fnMakeVirtualBasketList_OPTN_DEAL(selProdCd,selItemIndex);
	}
}
function fnMakeVirtualBasketList_OPTN_DEAL(testProdCd, testIndex){
	//console.log("################## 시작 가상 장바구니 생성 - 연관/딜상품");
	var optionArea = $("#quickmenu-bar .inner-scroll");
	// 선택된 연관/딜 상품
	//var objId = $(obj).prop("id");
	var objId = ""; //$(obj).prop("id");
	objId = "optnDeal";
    var objNm = (objId.substring(objId.length-3, objId.length) == "Dtl") ? objId.substring(0, objId.length-3) : objId;
	//var selValueArr = $("#"+objId+" option:selected").val().split("|");	// [0] : itemsProdCd / [1] : itemsIndex
	var prod_gb = "optnDeal";
    var selProdCd = "";
    var selItemIndex = "";

	/*if( $("#FormProdInfo #PROD_LINK_KIND_CD").val() == "05" ) {
		// 연관상품 - 가상장바구니 선택된 본품 조회
		var main_prodCnt = 0;
		$.each($(optionArea).find("div[name=virtual-basket]"), function (index, basketItem) {
			if($("#FormProdInfo #PROD_CD").val() == $(basketItem).prop("id").split("|")[0])
				main_prodCnt++;
		});
		// 본품 선택 체크
		if( main_prodCnt <  1 ) {
			alert( view_messages.select.selectMainProd );
			fnResetOptn('all', 0);
			return;
		}
		// 상품구분-연관상품
		prod_gb = "optnAsso";
	}*/
	// 연관/딜 상품 선택 체크
	var selProdCd = "", selItemIndex = "";
	if(testProdCd !="" && testIndex != ""){
		selProdCd     = testProdCd;
		selItemIndex  = testIndex;
	}else{
		return;
	}

	var itemList = optnProdList;	// 연관/딜 상품리스트
	if(objId.substring(objId.length-3, objId.length) == "Dtl") {
		itemList = optnItemList;
	}

	var virtualBasket="";
	for (var i in itemList) {
		var item = itemList[i];
		if( item.itemsProdCd == selProdCd && item.itemsIndex == selItemIndex ) {
			var main_objId=selProdCd+"|"+selItemIndex;
			virtualBasket=getVirtualBasket("", item, main_objId, prod_gb,"Y");
			break;
		}
	}
	$(optionArea).append(virtualBasket);
	getTotAmt();	// set 가격정보
}
$('#quickmenu-bar .inner-scroll #optnDeal ').on('changezzzzz', function(){
	var objId = $(this).prop("id");
	var objNm = (objId.substring(objId.length-3, objId.length) == "Dtl") ? objId.substring(0, objId.length-3) : objId;
	var index = $('#quickmenu-bar .inner-scroll select').index($(this));

	if( objNm == "ItemColor" ) {
		// 색상 변경시 사이즈 조회
		getItemSize($(this));
	} else if( objNm == "optnAsso" || objNm == "optnDeal" ) {
		// 연관상품/딜상품 옵션조회
		 $('#quickmenu-bar .inner-scroll').find("#optnAreaDtl").empty();
		getOptionDetailAjax($(this));
	} else {
		// 가상장바구니 담기
		if( objNm == "optnCtpd" ) {
			// 구성품
			fnMakeVirtualBasketList_CTPD($(this));
		}  else if( objId.substring(objId.length-3, objId.length) == "Dtl") {
			// 연관/딜 상품 옵션선택
			fnMakeVirtualBasketList_OPTN($(this));
		}else {
			// 변경시 selectbox 초기화
			fnResetOptn('after', index);
			// 기본 가상 장바구니 남기
			fnMakeVirtualBasketList($(this));
		}
	}
});

$(".btn-option-cart").on('click',function(){
	if($('#quickmenu-bar').attr('class') != 'active'){ toggleQuickMenuBar(); }
	
	$('.select-btn').click();
	$('#optnDeal li').eq($(this).data('idx')).trigger('click');
});