//"use strict";

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
	$('.relation-option').on('click', 'button', function(e){
		// 수량관련 버튼 체크
		var optnArea = $('.relation-option');

		if($(this).hasClass("up")) {
			//console.log("수량증가");
			var obj = $(this).closest("div[class^=set-spinner-type").find(".optnCnt");
			var num = $(obj).val() * 1 + 1;

			if(num > 999) {
				return;
			} else {
				$(obj).val(num);
			}
		}

		if($(this).hasClass("down")) {
			//console.log("수량감소");
			var obj = $(this).closest("div[class^=set-spinner-type").find(".optnCnt");
			var num = $(obj).val() * 1 - 1;

			if( $("#FormProdInfo #ONLINE_PROD_TYPE_CD").val() == "04" ) {	// 골라담기
				if(num < 0) return;
			} else if(num < 1) {
				return;
			}

			$(obj).val(num);
		}

		if($(this).hasClass("optnDel")) {
			//console.log("수량삭제");

			if( prodBasicInfo.ONLINE_PROD_TYPE_CD == "04" ) {
				// 골라담기
				if($(this).prop("name") == "optnLoadDel") {
					$(this).parents(".wrap-set-spinner").find("input:text[name=optnLoadQty]").val("0");
				}
			} else {
				if( $("#FormProdInfo #PROD_LINK_KIND_CD").val() == "05" ) {
					//연관상품
					if( $(this).prop("name") == "orderDel" && $(this).closest("li").prop("id").split("|")[0] == $("#FormProdInfo #PROD_CD").val() ) {
						var itemList = $("#fmOptnList li")
						var cnt=0;
						$.each(itemList, function (index, item) {
							if($(item).prop("id")  == $("#FormProdInfo #PROD_CD").val() )
								cnt++;
						});

						if( cnt > 1 ) $(this).closest("li").remove();
						else $("#fmOptnList").find("li").remove();
					} else {
						$(this).closest("li").remove();
					}
				} else {
					if( $(this).prop("name") == "orderDel") {
						$(this).closest("li").remove();
					} else if( $(this).prop("name") == "orderDelDtl" ) {
						$(this).closest(".select-item-relation").remove();
					}
				}

				if( $('.relation-option .option-list').find("li").length < 1 ) {
					// 옵션 selectbox reset
					fnResetOptn();
				}
			}
		}

		if( prodBasicInfo.ONLINE_PROD_TYPE_CD == "04" ) {
			fnChkOptnLoadQty($(this));	// 골라담기 총 수량 체크
		} else {
			fnChkOptnQty($(this));	//수량체크
		}

	});

	// 숫자만 입력가능
	$('.relation-option').on('keyup', '.optnCnt', function(){
		$(this).val($(this).val().replace(/[^0-9]/gi, "")*1);

		if( prodBasicInfo.ONLINE_PROD_TYPE_CD == "04" ) {
			if($(this).val() == "") $(this).val("0");
			fnChkOptnLoadQty($(this));	// 골라담기 총 수량 체크
		} else {
			if($(this).val() == "" || $(this).val() == "0" ) $(this).val("1");
			fnChkOptnQty($(this));	//수량체크
		}
	});

	/*
	 * 골라담기 수량 체크
	 */
	function fnChkOptnLoadQty(obj) {
		var inputs = $(obj).closest("div");
		var optionArea = $(".relation-option");
		var PROD_FLAG = $("#FormProdInfo #PROD_FLAG").val();
		var ORDER_COUNT_LEFT = $("#FormProdInfo #ORDER_COUNT_LEFT").val();
		var setQty = Number($(".relation-option #OPTN_LOAD_SET_QTY").val());
		var optnLoadQty=0, leftQty=0;
		var leftQtyTxt = "";
		var validHolidayCategory = $("#FormProdInfo #validHolidayCategory").val();	

		var itemsProdCd	= $(optionArea).find("input[name=itemsProdCd]").val();
		var itemsCode		= $(optionArea).find("input[name=itemsCode]").val();
		var orderCount		=  Number( $(inputs).find("input[name=optnLoadQty]").val());

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
				$(inputs).siblings("input[name=optnLoadQty]").val(Number($(inputs).siblings("input[name=optnLoadQty]").val())-tempQty);
				$(optionArea).find("input[name=OrderCount]").val(maxOrderAbleQty);
				return false;
			}
	    }

		if( optnLoadQty > maxOrderAbleQty ) {
			if( maxOrderAbleGubun == 2 ){
				if(validHolidayCategory != null && validHolidayCategory !="" && $("#FormProdInfo #CATEGORY_ID").val().substring(0,8) == validHolidayCategory)
				{
					var prod_nm = $("#FormProdInfo #PROD_NM").val();
					var bigbuy = confirm(fnJsMsg(view_messages.error.productOrderQty, minOrderAbleQty,  maxOrderAbleQty)+"\n명절 선물세트 대량구매 문의를 통해 문의하시면, 더 좋은 가격에 구매가 가능합니다.\n대량구매로 이동하시겠습니까?");
					if(bigbuy)
					{
						window.open('/board/holiday/popup/view.do?ProductNM='+prod_nm+'&SITELOC=VS034', 'bosPop', 'top=50, left=200, width=630, height=940, resizable=no, location=no');
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
						window.open('/board/holiday/popup/view.do?ProductNM='+prod_nm+'&SITELOC=VS034', 'bosPop', 'top=50, left=200, width=630, height=940, resizable=no, location=no');
					}
				}else{
					alert( fnJsMsg(view_messages.error.productOrderQty, minOrderAbleQty,  maxOrderAbleQty) );
				}

			}

			$(inputs).find("input[name=optnLoadQty]").val(Number($(inputs).find("input[name=optnLoadQty]").val())-tempQty);
			$(optionArea).find("input[name=OrderCount]").val(maxOrderAbleQty);

			getTotAmt();	// set 가격정보
			return false;
		}else if(validHolidayCategory != null && validHolidayCategory !="" && optnLoadQty>=300 && $("#FormProdInfo #CATEGORY_ID").val().substring(0,8) == validHolidayCategory){
			var prod_nm = $("#FormProdInfo #PROD_NM").val();
			var bigbuy = confirm(fnJsMsg(view_messages.error.productOrderQty, minOrderAbleQty,  maxOrderAbleQty)+"\n명절 선물세트 대량구매 문의를 통해 문의하시면, 더 좋은 가격에 구매가 가능합니다.\n대량구매로 이동하시겠습니까?");
			if(bigbuy)
			{
				window.open('/board/holiday/popup/view.do?ProductNM='+prod_nm+'&SITELOC=VS034', 'bosPop', 'top=50, left=200, width=630, height=940, resizable=no, location=no');
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
			leftQtyTxt = "(상품 <em>"+setComma(leftQty)+"개</em>를 더 선택해주세요.)";
		}

		$("#leftQty").html(leftQtyTxt);

		getTotAmt();			// set 가격정보
		changeCoupon();	// reset 쿠폰혜택리스트
	}

	// 옵션 색상 변경시
	$('.relation-option .option-select').on('selectmenuchange', 'select[name=ItemColor]', function(){
		getItemSize($(this));
	});

	// 옵션 색상 변경시 - 딜/연관 상품
	$('.relation-option .option-select-relation').on('selectmenuchange', 'select[name=ItemColorDtl]', function(){
		//console.log("옵션 색상 변경시 - 딜/연관 상품");
		getItemSize($(this));
	});

	// 옵션 사이즈 조회
	function getItemSize(obj) {
		//console.log("옵션 사이즈 조회");
		var optionArea = $(".relation-option .option-select");
		var selectedValue = $(obj).val();
		var objSize = ($(obj).prop("name") == "ItemColorDtl") ? "ItemSizeDtl" : "ItemSize";
		var itemListSize =($(obj).prop("name") == "ItemColorDtl") ? optnItemList : itemList;

		if( $(obj).prop("name") == "ItemColorDtl" ) {
			optionArea = $(".relation-option .option-select-relation");
		}

		$(optionArea).find("select[name="+objSize+"] option").remove();

		var optnList="<option value=''>선택하세요.</option>";
		$.each(itemListSize, function (index, item) {
			if (item.itemsColor == selectedValue ) {
				var itemsStockText = "", disabledStr="";
				if( (item.itemsAvailableStock < 1 || item.itemsAbsenceYN != "N")  && !previewYNFlag ) {
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
				optnList+="<option value='"+optVal+"'"+disabledStr+">"+ sizeName +"  ("+setComma(item.itemsCurrSalePrc)+" 원"+dealPrice+")"+itemsStockText+"</option>";
			}
		});

		$(optionArea).find("select[name="+objSize+"]").append(optnList);
		$(optionArea).find("select").selectmenu( "refresh" );
	}

	// 옵션선택
	$('.relation-option .option-select').on('selectmenuchange', 'select[name!=ItemColor]', function(){
		if($(this).is("select[name=optnCtpd]")) {
			getSelectedListCtpd($(this));
		} else {
			getSelectedListBasic($(this));
		}
	});

	// 옵션 변경시 - 딜, 연관 상품
	$('.relation-option .option-select-relation').on('selectmenuchange', 'select', function(){
		if ( $(this).prop("id") == "optnAsso" || $(this).prop("id") == "optnDeal" ) {
			getOptionDetailAjax($(this));
		}
		if ( $(this).prop("id") != "ItemColorDtl" && $(this).prop("id") != "optnAsso" && $(this).prop("id") != "optnDeal" ) {
			getSelectedListOptn($(this));
		}
	});

	// 옵션상세 조회
	function getOptionDetailAjax(obj) {
		//console.log("### 옵션상세 조회 시작 ###");
		var optionArea = $(".relation-option .option-select-relation");
		var selValueArr = $(obj).val().split("|");	// [0] : itemsProdCd / [1] : itemsIndex
		var selProdCd = "", selItemIndex = "", optionYn="N";
		var params = {};
		var _LMAppUrl = $("#FormProdInfo #_LMAppUrl").val();

		$(optionArea).find("#optnAreaDtl").empty();

		if( $("#FormProdInfo #OPTION_YN").val() == "Y" && $(".relation-option .option-list").find("li").length < 1) {
			alert("본품을 먼저 선택해주세요.");
			$(optionArea).find("select option:eq(0)").prop("selected", true);
			$(optionArea).find("select").selectmenu( "refresh" );
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
			params["ProductCD"]			= $("#FormProdInfo #PROD_CD").val();
			params["CategoryID"]			= $("#FormProdInfo #CATEGORY_ID").val();
			params["AssoCD"]				= selProdCd;
			params["ProdLinkKindCd"]	= $("#FormProdInfo #PROD_LINK_KIND_CD").val();
			params["previewYN"]	= prodBasicInfo.previewYN;

			fn$ajax("/product/ajax/productOptionDetailAjax.do", params, fnNmGetter().name);
		} else {
			if( $("#FormProdInfo #PROD_LINK_KIND_CD").val()  == "04" || $("#FormProdInfo #PROD_LINK_KIND_CD").val() == "05" ) {
				getSelectedListOptn($(obj));
			} else {
				//getSelectedList($(obj));
			}
		}
	}



	// 가상 장바구니 담기 Basic
	function getSelectedListBasic(obj) {
		//console.log("################## 시작 가상 장바구니 조회 Basic");

		var objNm = $(obj).prop("name");
		var targetOptnArea = $(".relation-option .option-list");
		var selText = $("#"+objNm+" option:selected").text();
		var selValueArr = $("#"+objNm+" option:selected").val().split("|");	// [0] : itemsProdCd / [1] : itemsIndex

		var selProdCd = "", selItemIndex = "";
		var itemsCode= "", itemsOptnDesc="", itemsCurrSalePrc=0, itemsAvailableStock=0,itemsStockYN="", itemsColorNm="", itemsSizeNm="", itemsProdCd="", itemsProdNm	="", itemsIndex="", itemsMinOrdPsbtQty="", itemsSrcmkCd="";
		var itemsMaxPurchasePrc="";

		if(selValueArr.length > 1 && selValueArr != "") {
			selProdCd = selValueArr[0];
			selItemIndex = selValueArr[1];
		} else {
			// 구성품 초기화
			if( $("#FormProdInfo #PROD_LINK_KIND_CD").val() == "06" ) {
				$(".relation-option .option-select").find("select[name=optnCtpd] option:eq(0)").prop("selected", true);
				$(".relation-option .option-select").find("select[name=optnCtpd]").selectmenu( "refresh" );
			}
			return;
		}

		for(var i=0; i<itemList.length; i++) {
			if( itemList[i].itemsIndex == selItemIndex ) {
				itemsCode					= itemList[i].itemsCode;				//단품코드
				itemsOptnDesc			= itemList[i].itemsOptnDesc;		//옵션명
				itemsCurrSalePrc		= itemList[i].itemsCurrSalePrc;		//판매가
				itemsAvailableStock	= itemList[i].itemsAvailableStock;	// 재고
				itemsStockYN				= itemList[i].itemsStockYN;			// 재고관리
				itemsAbsenceYN		= itemList[i].itemsAbsenceYN;		// 품절여부
				itemsColorNm			= itemList[i].itemsColorNm;			// 색상명
				itemsSizeNm				= itemList[i].itemsSizeNm;			// 사이즈명
				itemsProdCd				= itemList[i].itemsProdCd;			// 상품코드
				itemsProdNm			= itemList[i].itemsProdNm;			// 상품명
				itemsIndex					= itemList[i].itemsIndex;				// 인덱스
				itemsMinOrdPsbtQty	=itemList[i].itemsMinOrdPsbtQty;// 최소구매수량
				itemsMaxPurchasePrc	= itemList[i].itemsMaxPurchasePrc;	// 구매예정가
				itemsSrcmkCd = itemList[i].itemsSrcmkCd;			// 판매코드
				break;
			}
		}

		if( itemsAvailableStock < 1 || itemsAbsenceYN != "N" ) return;

		// 선택상품 중복 조회
		var objItemCnt = 0;
		var objItemIndex = $(targetOptnArea).find("li");

		for(var i=0; i<$(objItemIndex).length; i++) {
			if( $(targetOptnArea).find("li:eq("+i+")").prop("id") == itemsProdCd+"|"+itemsIndex ) {
				objItemCnt++;
			}
		}

		if(objItemCnt > 0) {
			alert("이미 선택된 옵션입니다.");
			return;
		}

		var itemProdCd = itemsProdCd;

		if(itemProdCd.indexOf("W") == 0) {
			itemProdCd = itemsSrcmkCd;
			itemsCode = "001";
		}

		if( $(objItemIndex).length == 0 || objItemCnt == 0 ) {
			var itemOptnTitle   =  (itemsOptnDesc == "" )?itemsProdNm:itemsOptnDesc;
			itemOptnTitle += (itemsColorNm != "")?" / "+itemsColorNm:"";
			itemOptnTitle += (itemsSizeNm != "")?" / "+itemsSizeNm:"";

			var selectedList = '<li id="'+itemsProdCd+'|'+itemsIndex+'">';
			selectedList += '	<div class="select-item">';
			selectedList += '		<span class="item-name"><strong>[옵션]</strong> '+itemOptnTitle+'  ('+setComma(itemsCurrSalePrc)+' 원)'+'</span>';
			selectedList += '		<div class="wrap-set-spinner">';
			selectedList += '			<div class="set-spinner-type1-large">';
			selectedList += '				<input type="text" name="OrderCount" title="수량" id="OrderCount" class="number optnCnt" value="'+itemsMinOrdPsbtQty+'" maxlength="3" style="ime-mode:disabled" onkeypress="return isOnlyNumberInput(event, false)" onkeydown="return isOnlyNumberInput(event, false)" />';
			selectedList += '				<input type="hidden" name="itemsProdCd" id="itemsProdCd" value="' + itemProdCd + '"/>';
			selectedList += '				<input type="hidden" name="itemsCode" id="itemsCode" value="'+itemsCode+'"/>';
			if( Number(itemsMaxPurchasePrc) > 0 ) itemsCurrSalePrc = itemsMaxPurchasePrc;
			selectedList += '				<input type="hidden" name="itemsCurrSalePrc" id="itemsCurrSalePrc" value="'+itemsCurrSalePrc+'"/>';
			selectedList += '				<input type="hidden" name="itemsIndex" id="itemsIndex" value="'+itemsIndex+'"/>';
			selectedList += '				<input type="hidden" name="itemsOptnDesc" id="itemsOptnDesc" value="'+itemsOptnDesc+'"/>';
			if( objNm == "IRREGULAR_VARIATION_MEMO" ) {
				selectedList += '				<input type="hidden" name="irregularVariationOptnDesc" id="irregularVariationOptnDesc" value="'+itemsOptnDesc+'"/>';
			}
			selectedList += '				<span class="set-btn">';
			selectedList += '					<button type="button" class="up" title="수량 증가">수량 증가</button>';
			selectedList += '					<button type="button" class="down" title="수량 감소">수량 감소</button>';
			selectedList += '				</span>';
			selectedList += '			</div>';
			selectedList += '			<span class="price">'+setComma(itemsCurrSalePrc*itemsMinOrdPsbtQty)+' 원</span>';
			selectedList += '			<button type="button" name="orderDel" class="btn-tbl optnDel"><em class="blind">삭제</em></button>';
			selectedList += '		</div>';
			selectedList += '	</div>';
			selectedList += '</li>';

			$(targetOptnArea).append(selectedList);
		}

		// 구성품 초기화
		if( $("#FormProdInfo #PROD_LINK_KIND_CD").val() == "06" ) {
			$(".relation-option .option-select").find("select[name=optnCtpd] option:eq(0)").prop("selected", true);
			$(".relation-option .option-select").find("select[name=optnCtpd]").selectmenu( "refresh" );
		}

		getTotAmt();								// set 가격정보
		getDeliveryType(selItemIndex);	// set 배송유형
		changeCoupon();						// reset 쿠폰혜택리스트

		//console.log("################## 끝 가상 장바구니 조회 Basic");
	}

	// 가상 장바구니 담기 구성품
	function getSelectedListCtpd(obj) {
		//console.log("################## 시작 가상 장바구니 조회 구성품");
		var targetOptnArea = $(".relation-option .option-list");

		// 선택된 본품 옵션 조회
		var optnArea = $(".relation-option .option-select");
		var selProdCdMain = "", selItemIndexMain = "";

		if( $("#FormProdInfo #OPTION_YN").val() != "Y") {
			selProdCdMain = $(optnArea).find("#itemsProdCd").val();
			selItemIndexMain = $(optnArea).find("#itemsIndex").val();
			itemsCodeMain =  $(optnArea).find("#itemsCode").val();
			itemsCurrSalePrcMain =  Number($(optnArea).find("#itemsCurrSalePrc").val());
			orderCount = Number($(optnArea).find("input:text[name=OrderCount]").val());
			itemPrc = $(optnArea).find("input:text[name=OrderCount]").parent().parent().find("span.price").text();

			var objItemCnt = 0;
			var objItemLi = $(targetOptnArea).find("li");
			var itemOptnTitle="";

			for(var i=0; i<$(objItemLi).length; i++) {
				if( $(targetOptnArea).find("li:eq("+i+")").prop("id") == selProdCdMain+"|"+selItemIndexMain ) {
					objItemCnt++;
					break;
				}
			}

			if( $(objItemLi).length == 0 || objItemCnt == 0 ) {
				var selectedList = '<li id="'+selProdCdMain+'|'+selItemIndexMain+'">';
				selectedList += '	<div class="select-item">';
				selectedList += '		<span class="item-name"><strong>[본품]</strong></span>';
				selectedList += '		<div class="wrap-set-spinner">';
				selectedList += '			<div class="set-spinner-type1-large">';
				selectedList += '				<input type="text" name="OrderCount" title="수량" id="OrderCount" class="number optnCnt" value="'+setComma(orderCount)+'" maxlength="3" style="ime-mode:disabled" onkeypress="return isOnlyNumberInput(event, false)" onkeydown="return isOnlyNumberInput(event, false)" />';
				selectedList += '				<input type="hidden" name="itemsProdCd" id="itemsProdCd" value="'+selProdCdMain+'"/>';
				selectedList += '				<input type="hidden" name="itemsCode" id="itemsCode" value="'+itemsCodeMain+'"/>';
				selectedList += '				<input type="hidden" name="itemsCurrSalePrc" id="itemsCurrSalePrc" value="'+itemsCurrSalePrcMain+'"/>';
				selectedList += '				<input type="hidden" name="itemsIndex" id="itemsIndex" value="'+selItemIndexMain+'"/>';
				selectedList += '				<span class="set-btn">';
				selectedList += '					<button type="button" class="up" title="수량 증가">수량 증가</button>';
				selectedList += '					<button type="button" class="down" title="수량 감소">수량 감소</button>';
				selectedList += '				</span>';
				selectedList += '			</div>';
				selectedList += '			<span class="price">'+itemPrc+'</span>';
				selectedList += '			<button type="button" name="orderDel" class="btn-tbl optnDel"><em class="blind">삭제</em></button>';
				selectedList += '		</div>';
				selectedList += '	</div>';
				selectedList += '</li>';

			}
		} else {
			var selValueMainArr = $(optnArea).find("select[name!=optnCtpd]:last option:selected").val().split("|");	// [0] : itemsProdCd / [1] : itemsIndex

			selProdCdMain = selValueMainArr[0];
			selItemIndexMain = selValueMainArr[1];
		}

		// 구성품
		var objNm = $(obj).prop("name");
		var selText = $("#"+objNm+" option:selected").text();
		var selValueArr = $("#"+objNm+" option:selected").val().split("|");	// [0] : itemsProdCd / [1] : itemsIndex

		if( selProdCdMain == "" || selItemIndexMain =="") {
			alert("본품을 먼저 선택해주세요.");
			$("#"+objNm+" option:eq(0)").prop("selected", true);
			$("#"+objNm).selectmenu( "refresh" );
			return false;
		}

		var selProdCd = "", selItemIndex = "";
		var itemsCode= "", itemsOptnDesc="", itemsCurrSalePrc="", itemsStockYN="", itemsColorNm="", itemsSizeNm="", itemsProdCd="", itemsProdNm	="", itemsIndex="", itemsMinOrdPsbtQty="";

		if(selValueArr.length > 1 && selValueArr != "") {
			selProdCd = selValueArr[0];
			selItemIndex = selValueArr[1];
		} else {
			return;
		}

		for(var i=0; i<optnProdList.length; i++) {
			if(optnProdList[i].itemsProdCd == selProdCd && optnProdList[i].itemsIndex == selItemIndex ) {
				itemsCode					= optnProdList[i].itemsCode;				//단품코드
				itemsCurrSalePrc		= optnProdList[i].itemsCurrSalePrc;		//판매가
				itemsAvailableStock	= optnProdList[i].itemsAvailableStock;	// 재고
				itemsAbsenceYN		= optnProdList[i].itemsAbsenceYN;		// 품절여부
				itemsStockYN				= optnProdList[i].itemsStockYN;			// 재고관리
				itemsProdCd				= optnProdList[i].itemsProdCd;			// 상품코드
				itemsProdNm			= optnProdList[i].itemsProdNm;			// 상품명
				itemsIndex					= optnProdList[i].itemsIndex;				// 인덱스
				itemsMinOrdPsbtQty	= optnProdList[i].itemsMinOrdPsbtQty;// 최소구매수량
				break;
			}
		}

		if( $(".relation-option .option-select").find("select[name=optnCtpd] option:selected").val() == "")  return;
		if( itemsIndex == "") return;

		if( itemsAvailableStock < 1 || itemsAbsenceYN != "N" ) return;

		// 본품 노출
		if( selectedList != "" ) {
			$(targetOptnArea).append(selectedList);
		}

		var objItemCnt = 0;
		var objItemMain = $(targetOptnArea).find("li[id='"+selProdCdMain+"|"+selItemIndexMain+"']");
		var objItemIndex = $(objItemMain).find(".select-item-relation");

		if( $(objItemMain).length < 1 ) {
			alert( view_messages.select.selectMainProd );
			fnResetOptn();
			return;
		}

		for(var i=0; i<$(objItemIndex).length; i++) {
			if( $(objItemMain).find(".select-item-relation:eq("+i+")").find("input:hidden[name=itemsIndex]").val() == itemsIndex ) {
				objItemCnt++;
				break;
			}
		}

		if(objItemCnt > 0) {
			alert("이미 선택된 옵션입니다.");
			return;
		}

		if( objItemCnt == 0 ) {
		 	var selectedListDtl   = '		<div class="select-item-relation">';
			selectedListDtl += '		<span class="item-name"><sup>&lfloor;</sup> 구성품 : '+itemsProdNm+'  ('+setComma(itemsCurrSalePrc)+' 원)'+'</span>';
			selectedListDtl += '		<div class="wrap-set-spinner">';
			selectedListDtl += '			<div class="set-spinner-type1-large">';
			selectedListDtl += '				<input type="text" name="OrderCount" title="수량" id="OrderCount" class="number optnCnt" value="'+itemsMinOrdPsbtQty+'" maxlength="3" style="ime-mode:disabled" onkeypress="return isOnlyNumberInput(event, false)" />';
			selectedListDtl += '				<input type="hidden" name="itemsProdCd" id="itemsProdCd" value="'+itemsProdCd+'"/>';
			selectedListDtl += '				<input type="hidden" name="itemsCode" id="itemsCode" value="'+itemsCode+'"/>';
			selectedListDtl += '				<input type="hidden" name="itemsCurrSalePrc" id="itemsCurrSalePrc" value="'+itemsCurrSalePrc+'"/>';
			selectedListDtl += '				<input type="hidden" name="itemsIndex" id="itemsIndex" value="'+itemsIndex+'"/>';
			selectedListDtl += '				<span class="set-btn">';
			selectedListDtl += '					<button type="button" class="up" title="수량 증가">수량 증가</button>';
			selectedListDtl += '					<button type="button" class="down" title="수량 감소">수량 감소</button>';
			selectedListDtl += '				</span>';
			selectedListDtl += '			</div>';
			selectedListDtl += '			<span class="price">'+setComma(itemsCurrSalePrc*itemsMinOrdPsbtQty)+' 원</span>';
			selectedListDtl += '			<button type="button" name="orderDelDtl" class="btn-tbl optnDel"><em class="blind">삭제</em></button>';
			selectedListDtl += '		</div>';
			selectedListDtl += '	</div>';

			$(objItemMain).append(selectedListDtl);
		}

		getTotAmt();	// set 가격정보

		//console.log("################## 끝 가상 장바구니 담기 구성품");
	}

	function getSelectedListOptn(obj) {
		//console.log("################## 시작 가상 장바구니 담기 - 딜/연관상품");

		if( $("#FormProdInfo #PROD_LINK_KIND_CD").val() == "05" ) {
			var targetOptnArea = $(".relation-option .option-list");

			// 선택된 본품 옵션 조회
			var optnArea = $(".relation-option .option-select");
			var selProdCdMain = "", selItemIndexMain = "";

			if( $("#FormProdInfo #OPTION_YN").val() != "Y") {
				selProdCdMain = $(optnArea).find("#itemsProdCd").val();
				selProdNmMain = $(optnArea).find("#itemsProdNm").val();
				selItemIndexMain = $(optnArea).find("#itemsIndex").val();
				itemsCodeMain =  $(optnArea).find("#itemsCode").val();
				itemsCurrSalePrcMain =  Number($(optnArea).find("#itemsCurrSalePrc").val());
				orderCount = Number($(optnArea).find("input:text[name=OrderCount]").val());
				itemPrc = $(optnArea).find("input:text[name=OrderCount]").parent().parent().find("span.price").text();

				var objItemCnt = 0;
				var objItemLi = $(targetOptnArea).find("li");
				var itemOptnTitle="";

				for(var i=0; i<$(objItemLi).length; i++) {
					if( $(targetOptnArea).find("li:eq("+i+")").prop("id") == selProdCdMain+"|"+selItemIndexMain ) {
						objItemCnt++;
						break;
					}
				}

				if( $(objItemLi).length == 0 || objItemCnt == 0 ) {
					var selectedList = '<li id="'+selProdCdMain+'|'+selItemIndexMain+'">';
					selectedList += '	<div class="select-item">';
					selectedList += '		<span class="item-name"><strong>[본품]</strong> '+selProdNmMain+'  ('+setComma(itemsCurrSalePrcMain)+' 원)'+'</span>';
					selectedList += '		<div class="wrap-set-spinner">';
					selectedList += '			<div class="set-spinner-type1-large">';
					selectedList += '				<input type="text" name="OrderCount" title="수량" id="OrderCount" class="number optnCnt" value="'+setComma(orderCount)+'" maxlength="3" style="ime-mode:disabled" onkeypress="return isOnlyNumberInput(event, false)" onkeydown="return isOnlyNumberInput(event, false)" />';
					selectedList += '				<input type="hidden" name="itemsProdCd" id="itemsProdCd" value="'+selProdCdMain+'"/>';
					selectedList += '				<input type="hidden" name="itemsCode" id="itemsCode" value="'+itemsCodeMain+'"/>';
					selectedList += '				<input type="hidden" name="itemsCurrSalePrc" id="itemsCurrSalePrc" value="'+itemsCurrSalePrcMain+'"/>';
					selectedList += '				<input type="hidden" name="itemsIndex" id="itemsIndex" value="'+selItemIndexMain+'"/>';
					selectedList += '				<span class="set-btn">';
					selectedList += '					<button type="button" class="up" title="수량 증가">수량 증가</button>';
					selectedList += '					<button type="button" class="down" title="수량 감소">수량 감소</button>';
					selectedList += '				</span>';
					selectedList += '			</div>';
					selectedList += '			<span class="price">'+itemPrc+'</span>';
					selectedList += '			<button type="button" name="orderDel" class="btn-tbl optnDel"><em class="blind">삭제</em></button>';
					selectedList += '		</div>';
					selectedList += '	</div>';
					selectedList += '</li>';

					$(targetOptnArea).append(selectedList);
				}
			} else {
				var selValueMainArr = $(optnArea).find("select[name!=optnCtpd]:last option:selected").val().split("|");	// [0] : itemsProdCd / [1] : itemsIndex

				selProdCdMain = selValueMainArr[0];
				selItemIndexMain = selValueMainArr[1];
			}

		}

		// 연관/딜상품
		var objId = $(obj).prop("id");
		var objNm = (objId.substring(objId.length-3, objId.length) == "Dtl") ? objId.substring(0, objId.length-3) : objId;

		var optionArea = $(".relation-option .option-list");
		var selectedText = $("#"+objId+" option:selected").text();
		var selectedValArr = $("#"+objId+" option:selected").val().split("|");	// [0] : itemsProdCd / [1] : itemsIndex

		if(selectedValArr.length > 1 && selectedValArr != "") {
			selectedProdCd = selectedValArr[0];
			selectedItemIndex = selectedValArr[1];
		} else {
			alert( view_messages.select.selectMainProd );
			fnResetOptn();
			return;
		}

		var itemsCodeMain= "", itemsCurrSalePrcMain="", itemsStockYNMain="", itemsProdCdMain="", itemsProdNmMain	="", itemsIndexMain="";
		var itemsCode= "", itemsOptnDesc="", itemsCurrSalePrc="", itemsAvailableStock="", itemsAbsenceYN="", itemsStockYN="", itemsColorNm="", itemsSizeNm="", itemsProdCd="", itemsProdNm="", itemsIndex="", itemsOptionYn="Y", itemsMinOrdPsbtQty="";
		var itemsMaxPurchasePrc="";
		var itemsStrCd = ""; // 2017.10.12 전희찬 점포코드 추가
		$.each(optnProdList, function (index, optnProd) {
			if ( optnProd.itemsProdCd == selectedProdCd && optnProd.itemsIndex == selectedItemIndex ) {
				// 옵션 없는 단품 연관상품
				itemsCode				= optnProd.itemsCode;
				itemsCurrSalePrc	= optnProd.itemsCurrSalePrc;
				itemsAvailableStock= optnProd.itemsAvailableStock;
				itemsAbsenceYN	= optnProd.itemsAbsenceYN;		// 품절여부
				itemsStockYN			= optnProd.itemsStockYN;
				itemsProdCd			= optnProd.itemsProdCd;
				itemsProdNm		= optnProd.itemsProdNm;
				itemsIndex				= optnProd.itemsIndex;
				itemsOptionYn		= optnProd.itemsOptionYn;		//N
				itemsMinOrdPsbtQty	= optnProd.itemsMinOrdPsbtQty;// 최소구매수량
				itemsMaxPurchasePrc	= optnProd.itemsMaxPurchasePrc;// 구매예정가
				itemsStrCd = optnProd.itemsStrCd;	// 2017.10.12 전희찬 점포코드 추가
				return false;
			}
		});

		if( itemsOptionYn == "Y" ) {
			// 옵션 있는 연관상품
			for(var i=0; i<optnItemList.length; i++) {
				if( optnItemList[i].itemsIndex == selectedItemIndex ) {
					itemsCode				= optnItemList[i].itemsCode;
					itemsCurrSalePrc	= optnItemList[i].itemsCurrSalePrc;
					itemsAvailableStock= optnItemList[i].itemsAvailableStock;	// 재고
					itemsAbsenceYN	= optnItemList[i].itemsAbsenceYN;		// 품절여부
					itemsStockYN			= optnItemList[i].itemsStockYN;
					itemsProdCd			= optnItemList[i].itemsProdCd;
					itemsProdNm		= optnItemList[i].itemsProdNm;

					itemsOptnDesc		= optnItemList[i].itemsOptnDesc;
					itemsColorNm		= optnItemList[i].itemsColorNm;
					itemsSizeNm			= optnItemList[i].itemsSizeNm;
					itemsIndex				= optnItemList[i].itemsIndex;
					itemsMinOrdPsbtQty	= optnItemList[i].itemsMinOrdPsbtQty;// 최소구매수량
					itemsMaxPurchasePrc	= optnItemList[i].itemsMaxPurchasePrc;// 구매예정가

					break;
				}
			}
		}

		if( itemsAvailableStock < 1 || itemsAbsenceYN != "N" ) return;

		var objItemCnt = 0;
		var objItemLi = $(optionArea).find("li");
		var itemOptnTitle="";

		for(var i=0; i<$(objItemLi).length; i++) {
			if( $(optionArea).find("li:eq("+i+")").prop("id") == itemsProdCd +"|"+ itemsIndex ) {
				objItemCnt++;
				break;
			}
		}

		if(objItemCnt > 0) {
			alert("이미 선택된 옵션입니다.");
			return;
		}

		var selectedList = "";
		var prodLinkKindTitle = "선택";

		if( $("#FormProdInfo #PROD_LINK_KIND_CD").val() == "05" ) prodLinkKindTitle="연관상품";
		if( $("#FormProdInfo #PROD_LINK_KIND_CD").val() == "04" ) {
			prodLinkKindTitle+=Number(itemsIndex.replace("index", ""))+1;
		}

		if( $(objItemLi).length == 0 || objItemCnt == 0 ) {
				itemOptnTitle   = itemsProdNm;
				itemOptnTitle += (itemsOptnDesc != "")?" / "+itemsOptnDesc:"";
				itemOptnTitle += (itemsColorNm != "")?" / "+itemsColorNm:"";
				itemOptnTitle += (itemsSizeNm != "")?" / "+itemsSizeNm:"";

				selectedList += '<li id="'+itemsProdCd+'|'+itemsIndex+'">';
				selectedList += '	<div class="select-item">';
				selectedList += '		<span class="item-name"><strong>['+prodLinkKindTitle+']</strong> '+itemOptnTitle+'  ('+setComma(itemsCurrSalePrc)+' 원)'+'</span>';
				selectedList += '		<div class="wrap-set-spinner">';
				selectedList += '			<div class="set-spinner-type1-large">';
				selectedList += '				<input type="text" name="OrderCount" title="수량" id="OrderCount" class="number optnCnt" value="'+itemsMinOrdPsbtQty+'" maxlength="3" style="ime-mode:disabled" onkeypress="return isOnlyNumberInput(event, false)" onkeydown="return isOnlyNumberInput(event, false)" />';
				selectedList += '				<input type="hidden" name="itemsProdCd" id="itemsProdCd" value="'+itemsProdCd+'"/>';
				selectedList += '				<input type="hidden" name="itemsCode" id="itemsCode" value="'+itemsCode+'"/>';
				selectedList += '				<input type="hidden" name="itemsStrCd" id="itemsStrCd" value="'+itemsStrCd+'"/>'; // 2017.10.12 전희찬 점포코드 추가
				if( Number(itemsMaxPurchasePrc) > 0 ) itemsCurrSalePrc = itemsMaxPurchasePrc;
				selectedList += '				<input type="hidden" name="itemsCurrSalePrc" id="itemsCurrSalePrc" value="'+itemsCurrSalePrc+'"/>';
				selectedList += '				<input type="hidden" name="itemsIndex" id="itemsIndex" value="'+itemsIndex+'"/>';
				selectedList += '				<input type="hidden" name="itemsOptnDesc" id="itemsOptnDesc" value="'+itemsOptnDesc+'"/>';
				if( objNm == "IRREGULAR_VARIATION_MEMO" ) {
					selectedList += '				<input type="hidden" name="irregularVariationOptnDesc" id="irregularVariationOptnDesc" value="'+itemsOptnDesc+'"/>';
				}
				selectedList += '				<span class="set-btn">';
				selectedList += '					<button type="button" class="up" title="수량 증가">수량 증가</button>';
				selectedList += '					<button type="button" class="down" title="수량 감소">수량 감소</button>';
				selectedList += '				</span>';
				selectedList += '			</div>';
				selectedList += '			<span class="price">'+setComma(itemsCurrSalePrc*itemsMinOrdPsbtQty)+' 원</span>';
				selectedList += '			<button type="button" name="orderDel" class="btn-tbl optnDel"><em class="blind">삭제</em></button>';
				selectedList += '		</div>';
				selectedList += '	</div>';
				selectedList += '</li>';

				$(optionArea).append(selectedList);
		}

		getTotAmt();	// set 가격정보

		//console.log("################## 끝 가상 장바구니 담기 - 딜/연관상품");
	}




});

function callBack_$getOptionDetailAjax(response) {

	var optionArea = $(".relation-option .option-select-relation");
	response =$.trim(response);

	if(response !="") {
		$(optionArea).find("#optnAreaDtl").append(response);
	}

	//console.log("################## 끝 옵션상세 조회");
}

//옵션수량 Validation
function fnChkOptnQty(obj) {
	//console.log("##### fnChkOptnQty() 옵션수량 Validation #####");

	var inputs = $(obj).siblings("input");
	var optionArea = $(".relation-option .option-select");
	var PROD_FLAG = $("#FormProdInfo #PROD_FLAG").val();
	var ORDER_COUNT_LEFT = $("#FormProdInfo #ORDER_COUNT_LEFT").val();

	if($(obj).prop("id") != "mainBtn" && $(obj).prop("id") != "OrderCountMain") {
		if( $(obj).prop("id") != "OrderCount") {
			inputs = $(obj).parent().siblings("input");
		}
		optionArea = $(".relation-option .option-list");
	}

	var orderCount		= Number($(inputs).siblings("input:text[name=OrderCount]").val());
	var currSalePrc		= Number($(inputs).siblings("input:hidden[name=itemsCurrSalePrc]").val());
	var itemsProdCd	= $(inputs).siblings("input:hidden[name=itemsProdCd]").val();
	var itemsCode		= $(inputs).siblings("input:hidden[name=itemsCode]").val();
	var validHolidayCategory = $("#FormProdInfo #validHolidayCategory").val();

	if(prodBasicInfo.PROD_CD.indexOf("W") == 0) {
		itemsProdCd = prodBasicInfo.PROD_CD;
	}

	var getMaxOrderQtyArr		= getMaxOrderQty(itemsProdCd, itemsCode);
	var maxOrderAbleGubun	= getMaxOrderQtyArr[0].stockGb;
	var maxOrderAbleQty		= getMaxOrderQtyArr[0].maxOrderAbleQty;
	var minOrderAbleQty			= getMaxOrderQtyArr[0].minOrderAbleQty;

	if( orderCount == 0 || orderCount == "" ) {
		//alert("주문수량이 옳바르지 않습니다.");
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
			$(inputs).siblings("input:text[name=OrderCount]").val(leftCount);
			return false;
		}
    }

	var rtn_flag=true;
	if( orderCount < minOrderAbleQty ) {
		alert( fnJsMsg(view_messages.error.productOrderQty, minOrderAbleQty,  maxOrderAbleQty) );

		$(inputs).siblings("input:text[name=OrderCount]").val(minOrderAbleQty);
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
					window.open('/board/holiday/popup/view.do?ProductNM='+prod_nm+'&SITELOC=VS034', 'bosPop', 'top=50, left=200, width=630, height=940, resizable=no, location=no');
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
					window.open('/board/holiday/popup/view.do?ProductNM='+prod_nm+'&SITELOC=VS034', 'bosPop', 'top=50, left=200, width=630, height=940, resizable=no, location=no');
				}
			}else{
				alert( fnJsMsg(view_messages.error.productOrderQty, minOrderAbleQty,  maxOrderAbleQty) );
			}
		}

		$(inputs).siblings("input:text[name=OrderCount]").val(maxOrderAbleQty);
		orderCount = maxOrderAbleQty;
		rtn_flag = false;
	}else if(validHolidayCategory != null && validHolidayCategory !="" && orderCount>=300 && $("#FormProdInfo #CATEGORY_ID").val().substring(0,8) == validHolidayCategory){
		var prod_nm = $("#FormProdInfo #PROD_NM").val();
		var bigbuy = confirm(fnJsMsg(view_messages.error.productOrderQty, minOrderAbleQty,  maxOrderAbleQty)+"\n명절 선물세트 대량구매 문의를 통해 문의하시면, 더 좋은 가격에 구매가 가능합니다.\n대량구매로 이동하시겠습니까?");
		if(bigbuy)
		{
			window.open('/board/holiday/popup/view.do?ProductNM='+prod_nm+'&SITELOC=VS034', 'bosPop', 'top=50, left=200, width=630, height=940, resizable=no, location=no');
		}
	}

	 var itemPrice = currSalePrc * orderCount;
	 $(inputs).parent().siblings(".price").text(setComma(itemPrice) + " 원");

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
	var orderCount = Number($(inputs).siblings("input:text[name=OrderCount]").val());
	var currSalePrc = Number($(inputs).siblings("input:hidden[name=itemsCurrSalePrc]").val());
	var itemPrice = currSalePrc * orderCount;

	if(gubun == "M") {
		var optionArea = $(".relation-option .option-list");
		var objItemLi = $(optionArea).find("li");

		for(var i=0; i<$(objItemLi).length; i++) {
			if( $(optionArea).find("li:eq("+i+")").prop("id").split("|")[0] == itemsProdCd && $(optionArea).find("li:eq("+i+")").prop("id").split("|")[1] == itemsIndex ) {
				$(optionArea).find("li[id='"+itemsProdCd+"|"+itemsIndex+"'] .select-item").find("input:text[name=OrderCount]").val(orderCount);
				$(optionArea).find("li[id='"+itemsProdCd+"|"+itemsIndex+"'] .select-item").find("span.price").text(setComma(itemPrice)+" 원");
				break;
			}
		}
	} else {
		var optionArea = $(".relation-option .option-select");

		if( $(optionArea).find("input:hidden[name=itemsProdCd]").val() == itemsProdCd && $(optionArea).find("input:hidden[name=itemsIndex]").val() == itemsIndex) {
			$(optionArea).find("input:text[name=OrderCount]").val(orderCount);
			$(optionArea).find("span.price").text(setComma(itemPrice)+" 원");
		}
	}
}

/*
 * 옵션 초기화
 */
function fnResetOptn() {
	var optionArea =  $('.relation-option .option-select, .relation-option .option-select-relation');

	$(optionArea).find("input:text[name=OrderCount]").val("1");

	$(optionArea).find("#optnAreaDtl").empty();

	$.each($(optionArea).find("select"), function (index, item) {
		$(item).find("option:eq(0)").prop("selected", true);

		if( $(item).prop("name") == "ItemSize" || $(item).prop("name") == "ItemSizeDtl" ) {
			var itemNm = $(item).prop("name");
			$(item).find("option:gt(0)").remove();
		}

		$(item).selectmenu( "refresh" );
	});

	fnChkOptnQty("input:text[name=OrderCount]");

}

// 쿠폰혜택리스트 Reset
function changeCoupon() {
	var optionArea = $(".relation-option .option-list");
	var promoArea = $("#promoDesc", ".prod-detail-info .relation-promotion");
	var ONLINE_PROD_TYPE_CD = prodBasicInfo.ONLINE_PROD_TYPE_CD;
	var totalOrderCount = 0;

	if($(promoArea).length < 1 ) return;

	if( ONLINE_PROD_TYPE_CD == "04" ) {
		// console.log("골라담기");
		var optnLoadQty = 0;
		var setQty = Number($("#OPTN_LOAD_SET_QTY").val());

		for(i=0; i<$("input:text[name=optnLoadQty]").length; i++) {
			optnLoadQty += Number($("input:text[name=optnLoadQty]:eq("+i+")").val());
		}
		totalOrderCount	= optnLoadQty / setQty;

	} else {
		if( $(optionArea).find("li").length > 0 ) {
			//console.log("옵션상품");

			for( var i=0; i < $(optionArea).find("li").length; i++) {
				var obj = $(optionArea).find("li:eq("+i+")");
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
				$(promoArea).find("#"+event.promoType).html("<em>"+event.title+"</em>"+event.desc);
			} else {
				$(promoArea).find("#"+event.promoType).html("<em>"+event.title+"</em>"+limitCnt+"개당 "+setComma(event.gubunFlag)+event.gubunFlag_icon+" 할인가능");
			}
		}
	});
}