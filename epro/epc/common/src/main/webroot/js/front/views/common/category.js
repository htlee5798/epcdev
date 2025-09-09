//"use strict";
$(function() {
/*	var $contents = $('#contents');

	$contents.on('click', 'button[id^=chkWish]', function (event) {
		addWishList(this, event, document.location.href);
	}).on('click', 'button[id^=chkBasket]', function (event) {
		addBasket(this, event, document.location.href);
	});*/

	$('.jq-tab > a').click(function (event) {
        var pt = $(this).parent().siblings();
        if($(pt).find('input[type=checkbox][id^=chk_]') != null && $(pt).find('input[type=checkbox][id^=chk_]').length > 0) {
            $(pt).find('input[type=checkbox][id^=chk_]').parent().removeClass('active');
            $(pt).find('input[type=checkbox][id^=chk_]').removeAttr('checked');
        }
        if($(pt).find('input[type=text][id^=orderQty_]') != null && $(pt).find('input[type=text][id^=orderQty_]').length > 0) {
            $(pt).find('input[type=text][id^=orderQty_]').val("1");
        }
    });
});

//셀렉트박스용 카테고리 리스트 조회-o
function goCategoryListAjax(p_categoryId, p_depth
										, p_previewYn, p_previewChCd, p_previewStrCd, p_previewDispDate) {
	var params = {
			'CategoryID' : p_categoryId
			,'Depth' : p_depth
			,'PREVIEWYN' : p_previewYn
			,'PREVIEWCHCD' : p_previewChCd
			,'PREVIEWSTRCD' : p_previewStrCd
			,'PREVIEWDISPDATE' : p_previewDispDate
			};
	//fn$ajax(_LMAppUrl+"/category/ajax/categoryListAjax.do", params, fnNmGetter().name);
	
	$.ajax({
		url: _LMAppUrl+"/category/ajax/categoryListAjax.do",
		data: params
	}).done(function(response) {
		$("#catNavi").html(response);
	});
}
function callBack_$goCategoryListAjax(response) {
	$("#catNavi").html(response);
}

//테이블용 카테고리 리스트 조회-o
function goCategorySubListAjax(p_categoryId, p_leafYn
											, p_previewYn, p_previewChCd, p_previewStrCd, p_previewDispDate) {
	var params = {
			'CategoryID' : p_categoryId
			,'LeafYn' : p_leafYn
			,'PREVIEWYN' : p_previewYn
			,'PREVIEWCHCD' : p_previewChCd
			,'PREVIEWSTRCD' : p_previewStrCd
			,'PREVIEWDISPDATE' : p_previewDispDate
			};
	fn$ajax(_LMAppUrl+"/category/ajax/categorySubListAjax.do", params, fnNmGetter().name);
}
function callBack_$goCategorySubListAjax(response) {
	$("#catSubNavi").html(response);
}

//카테고리 코너정보 조회
function goCategoryCornerListAjax(p_categoryId, p_templateId, p_depth, p_leafYn, p_dispTemplateTypeCd, p_categoryTypeCd, p_returnUrl
												, p_previewYn, p_previewChCd, p_previewStrCd, p_previewDispDate) {
	var params = {
			'CategoryID' : p_categoryId
			,'TemplateID' : p_templateId
			,'Depth' : p_depth
			,'LeafYn' : p_leafYn
			,'DispTemplateTypeCd' : p_dispTemplateTypeCd
			,'CategoryTypeCd' : p_categoryTypeCd
			,'ReturnUrl' : p_returnUrl
			,'PREVIEWYN' : p_previewYn
			,'PREVIEWCHCD' : p_previewChCd
			,'PREVIEWSTRCD' : p_previewStrCd
			,'PREVIEWDISPDATE' : p_previewDispDate
			};
	fn$ajax(_LMAppUrl+"/category/ajax/categoryCornerListAjax.do", params, fnNmGetter().name);
}
function callBack_$goCategoryCornerListAjax(response) {
	$("#cornerList").html(response);
}

//3Depth:상품 조회
function goCategoryProdListMainAjax(p_categoryId, p_leafYn
													, p_previewYn, p_previewChCd, p_previewStrCd, p_previewDispDate) {
	var params = {
			'CategoryID' : p_categoryId
			,'LeafYn' : p_leafYn
			,'PREVIEWYN' : p_previewYn
			,'PREVIEWCHCD' : p_previewChCd
			,'PREVIEWSTRCD' : p_previewStrCd
			,'PREVIEWDISPDATE' : p_previewDispDate
			};
	
	if(typeof rowPerPage !== 'undefined'){
		params.rowPerPage = rowPerPage;
	};
	fn$ajax(_LMAppUrl+"/category/ajax/categoryProdListMainAjax.do", params, fnNmGetter().name);
}
function callBack_$goCategoryProdListMainAjax(response) {
	$("#divProdListMain").html(response);
}

//3Depth:상품 조회
function goCategoryProdListAjax( ) {
	if($("#aAll").hasClass("active")) {
		$("#p_searchItem_De_01").attr("disabled", true);
		$("#p_searchItem_De_04").attr("disabled", true);
		$("#p_searchItem_De_02").attr("disabled", true);
		$("#p_searchItem_De_06").attr("disabled", true);
	} else if($("#aStr").hasClass("active")) {
		$("#p_searchItem_De_01").attr("disabled", false);
		$("#p_searchItem_De_04").attr("disabled", true);
		$("#p_searchItem_De_02").attr("disabled", true);
		$("#p_searchItem_De_06").attr("disabled", true);
	} else if($("#aVen").hasClass("active")) {
		$("#p_searchItem_De_01").attr("disabled", true);
		$("#p_searchItem_De_04").attr("disabled", false);
		$("#p_searchItem_De_02").attr("disabled", false);
		$("#p_searchItem_De_06").attr("disabled", true);
	} else if($("#aGift").hasClass("active")) {
		$("#p_searchItem_De_01").attr("disabled", true);
		$("#p_searchItem_De_04").attr("disabled", true);
		$("#p_searchItem_De_02").attr("disabled", true);
		$("#p_searchItem_De_06").attr("disabled", false);
	}
	
	if($("#aList").hasClass("active")) {
		$("#ViewType").val("LIST");
		$("#ReturnUrl").val("category/includes/listTypeProdMoreList");
	} else if($("#aImg").hasClass("active")) {
		$("#ViewType").val("SIMG");
		$("#ReturnUrl").val("category/includes/imgTypeProdMoreList");
	}
	
	fn$ajax_globalStart(true);
	
	var params = $('#productForm').serialize();
	//fn$ajax(_LMAppUrl+"/category/ajax/categoryProdListAjax.do", params, fnNmGetter().name);
	$.ajax({
		url: _LMAppUrl+"/category/ajax/categoryProdListAjax.do",
		data: params
	}).done(function(response) {
		if($("#aList").hasClass("active")) {
			$("#divList").html(response);
			$("#divImg").html("");
		} else if($("#aImg").hasClass("active")) {
			$("#divList").html("");
			$("#divImg").html(response);
		}
	});
}
function callBack_$goCategoryProdListAjax(response) {
	if($("#aList").hasClass("active")) {
		$("#divList").html(response);
		$("#divImg").html("");
	} else if($("#aImg").hasClass("active")) {
		$("#divList").html("");
		$("#divImg").html(response);
	}
}

function goProdListAjax( ) {
	fn$ajax_globalStart(true);
	var params = $('#productForm').serialize();
	fn$ajax(_LMAppUrl+"/category/ajax/categoryProdListAjax.do", params, fnNmGetter().name);
}
function callBack_$goProdListAjax(response) {
	if($("#aList").hasClass("active")) {
		$("#divList").append(response);
		$("#divImg").html("");
	} else if($("#aImg").hasClass("active")) {
		$("#divList").html("");
		$("#divImg").append(response);
	}

//	$(window).off('.disableScroll');
	isScroll = true;
}

/*//1Depth:금주의 인기상품 더보기
function goCategoryTwekPopProdAjax(p_form) {
	var params = $('#'+p_form.name).serialize();
	fn$ajax(_LMAppUrl+"/category/ajax/categoryCornerSetProdListAjax.do", params, fnNmGetter().name);
}
function callBack_$goCategoryTwekPopProdAjax(response) {
	if($("#tab03").hasClass("active")) {
		$("#divProd03").append(response);
		isScroll_twekPop = true;
	} else {
		
	}
}*/
//2Depth:어제 많이 팔린 상품 더보기
function goCategoryDaySellProdAjax(p_form) {
	var params = $('#'+p_form.name).serialize();
	fn$ajax(_LMAppUrl+"/category/ajax/categoryCornerSetProdListAjax.do", params, fnNmGetter().name);
}
function callBack_$goCategoryDaySellProdAjax(response) {
	if($("#tab01").hasClass("active")) {
		$("#divProd01").append(response);
		isScroll_dySell = true;
	} else if($("#tab02").hasClass("active")) {
		$("#divProd02").append(response);
		isScroll_new = true;
	} else {
		
	}
}

//카테고리 선택-o
function goCategory(p_categoryId, p_depth, p_leafYn, type, url) {
	if(type == 'LINK_URL'){
		location.href = url;
		
		return;
	};
	
	if(p_depth == "${CATEGORYINFO.DEPTH}") {
		goCategoryNavi(p_categoryId, p_depth, p_leafYn);
	} else {
		fn$ajax_globalStart(true);
		goCategoryList(p_categoryId);
	}
};

//카테고리 선택 
function goCategoryNavi(p_categoryId, p_depth, p_leafYn) {
	goCategoryListAjax(p_categoryId, p_depth);
	goCategorySubListAjax(p_categoryId, p_leafYn);
	if(p_depth == 2) {
		goCategoryDaySellNewProdAjax(p_categoryId);
		goCategoryTodayHotAjax(p_categoryId);
		goCategoryRecomPlanAjax(p_categoryId);
		goCategoryTwekPopRecomProdAjax(p_categoryId);
	}
	if(p_depth == 3) {
		goCategoryHotProdAjax(p_categoryId);
		goCategoryDaySellNewRecomProdAjax(p_categoryId);
	}
	if(p_depth == 4) {
		goCategoryProdListAjax();
	}
}

function goCategorySpecial(categoryId, leafYn, isRootCategory) {
	var rootCategoryId = isRootCategory ? categoryId : $('[data-root-category-id].active').data('rootCategoryId');
	var specialURL = "";
	sessionStorage.clear();
	if(!isRootCategory){
		sessionStorage.setItem('sub_category_id', categoryId);
		sessionStorage.setItem('sub_category_leaf_yn', leafYn);		
	};
	
	specialURL = (rootCategoryId == 'C2020042') ? '/special/enterprise/list.do?CategoryID=' : '/special/categorySpecList.do?CategoryID=';
	document.location.href = _LMAppUrl + specialURL + rootCategoryId; 
}

function optionLayer(obj, event) {
	if("Y" == previewYn) {
        alert(fnJsMsg(view_messages.fail.doesNotFnc));        //미리보기에서는 제한되는 기능입니다.
        return;
    } else {
    	var $_obj = $(obj)
    		, _info= '';
    	
    	if($_obj.attr('data-id') && $_obj.attr('data-id') != null){
    		_info = $_obj.attr('data-id');
    	}else if($_obj.attr('id') && $_obj.attr('id') != null){
    		_info = $_obj.attr('id');
    	}
    	
		var prodInfos = _info.split('_'); //id="B(O)_B_${nRow.OPTION_YN}_${nRow.PROD_CD}_${nRow.ONLINE_PROD_TYPE_CD}_${PROD_AREA_IDX }_${nRow.CATEGORY_ID }"
		//정기배송여부
		var periDeliYn = $_obj.attr('periDeli');
		
		if(periDeliYn == undefined){
			periDeliYn = "N";	
		}
		
		if(prodInfos[4] != null && prodInfos[4] != "" 
        		&& ( prodInfos[4] == onlineProdMake || prodInfos[4] == onlineProdInst || prodInfos[4] == onlineProdReserve ) ) {
        	//주문제작상품, 설치상품, 예약상품은 상품상세페이지로 이동
        	alert(fnJsMsg(view_messages.confirm.goProductDetail));		//해당 상품은 상품 상세에서 장바구니에 담으실 수 있습니다.
        	goProductDetail(prodInfos[6], prodInfos[3], 'N'); // categoryId, prodCd, 
        	return;
        }

		if(prodInfos[2] != "Y") {//단일 상품
			var flag = global.isLogin(document.location.href);
    		if(flag) {
    			addBasketProdListOneItem(obj, event, prodInfos[1],  prodInfos[3], prodInfos[5], document.location.href);	//장바구니담기
    		}
    		return;
		}
		//옵션상품
		var p_type = null;
		if(prodInfos[1] == "D") {
			p_type = "order";
		} else {
			p_type = "basket";
		}

		//로그인없이 옵션내용 출력
		if(prodInfos[2]=="Y") {
			var params = {
				'PROD_CD' : prodInfos[3]
				, 'TYPE' : p_type
				, 'PRODTYPECD' : prodInfos[4]
				, 'CATEGORYID' : prodInfos[6]
				, 'PERIDELIYN' : periDeliYn
			};

			$.ajax({
				type: "post",
				url: _LMAppUrl+"/product/ajax/productOptionListAjax.do",
				data: params,
				dataType: "html",
				success: function(data) {
					//console.log(data);
					$("#opt-list").html(data);
					//$("#opt-list").append(data);
					//$("#opt_cart").css({display:'block'});
					layerpopTriggerBtn( obj, event );
				    selectMenu();
				}
			});
		}
    }
}

function addWishProd(obj, event) {
	if("Y" == previewYn) {
        alert(fnJsMsg(view_messages.fail.doesNotFnc));        //미리보기에서는 제한되는 기능입니다.
        return;
    } else {
        event.preventDefault();
        
        /* $(this).toggleClass('cuurent'); */
        var prodInfos = $(obj).attr('id').split('_');      // "wish_"+prodCd+"_"+idx+"_"+categoryId or "wish_"+prodCd+"_"+categoryId
        if(prodInfos != null && prodInfos.length > 3) {
            addProdWishList(obj, event, prodInfos[1], prodInfos[3], 'N', prodInfos[2], document.location.href);
        } else if(prodInfos != null && prodInfos.length > 2) {
            addProdWishList(obj, event, prodInfos[1], prodInfos[2], 'N', '', document.location.href);
        }
    }
}

function maxBenefitLayer(obj, event) {
	event.preventDefault();
	layerpopTriggerBtn( obj, event );
    selectMenu();
}

function prodDetailLink(obj, event) {
	var link = $(obj).closest('article, .product-article').find('.prod-name a').attr('href'); //href로 가져올때
	var onclick = $(obj).closest('article, .product-article').find('.prod-name a').attr('onclick'); //onclick으로 가져올때
	var section = $(obj).find('section').length;
	// location 링크 goProductDetail 로 가져올수 있도록 해야함
	//if (!$(e.target).attr('href')) location.href = link; // href
	//if (!$(e.target).attr('href')) location.href = onclick; // onclick
	if (!$(event.target).attr('href')) {
		if (onclick != null && onclick.length > 0) {
			if(section == '0') {
				//eval(onclick); // onclick
				var onclickArr = onclick.split(';');
				eval(onclickArr[0]); // onclick
			}
		} else if(link != null && link.length > 0) {
			if(section == '0') {
				var linkArr = link.split(';');
				location.href = linkArr[0];
			}
		}
	} else {
		if(section == '0') {
			var linkArr = link.split(';');
			location.href = linkArr[0];
		}
	}
}



//주문수량 $(in_orderQty).val(Number(tmpOrderQty) < Number(minOrderQty)? Number(minOrderQty) : Number(tmpOrderQty)-1);
function calOrderQtyProd(obj, p_prodCd, p_gubun, p_area_idx) {

	var  $_obj = $(obj)
		, prodIdx = ""
		, categoryId = ""
		, minOrderQty = 0
		, maxOrderQty = 0
		, tmpOrderQty = 0 ; //현재수량

	if(p_area_idx != null && p_area_idx != "" && p_area_idx != "undefined" && p_area_idx != undefined) {
		prodIdx = "_" + p_area_idx;
	}

	var dealProdYn = p_prodCd.substring(0,1) == "D" ? "Y" : "N"; 
	
	if($_obj.attr('data-maxQty') && $_obj.attr('data-minQty') && $_obj.attr('data-categoryId')){
		minOrderQty = $_obj.attr('data-minQty');
		maxOrderQty = $_obj.attr('data-maxQty');
		categoryId = $_obj.attr('data-categoryId');
	}else{
		minOrderQty = $('#minQty_'+p_prodCd+prodIdx).val();
		maxOrderQty = $('#maxQty_'+p_prodCd+prodIdx).val();
		categoryId = $("#categoryId_"+p_prodCd+prodIdx).val();
	}
	
	if(dealProdYn == "Y" ) {
		//딜상품 수량입력시, 상품상세페이지로 이동
		alert(fnJsMsg(view_messages.confirm.goProductDetail));		//해당 상품은 상품 상세에서 장바구니에 담으실 수 있습니다.
		goProductDetail(categoryId, p_prodCd, 'N');
		return;
	}

	//tmpOrderQty		= $('#orderQty_'+p_prodCd+prodIdx).val();
	var in_orderQty = "";
	if(p_gubun == "incre" || p_gubun=="decre") {
		if( ($("#aList") != null && $("#aList").length > 0)  && $("#aList").hasClass("active") ) { // 리스트형 보기
			$_obj.parent().siblings().each(function(index) {
				if(("#"+this.id) == ('#orderQty_'+p_prodCd+prodIdx)) {
					tmpOrderQty = $(this).val();
					in_orderQty = this;
				}
			});
		} else { // 이미지형 보기
			$_obj.siblings().each(function(index) {
				if(("#"+this.id) == ('#orderQty_'+p_prodCd+prodIdx)) {
					tmpOrderQty = $(this).val();
					in_orderQty = this;
				}
			});
		}
	} else {
		tmpOrderQty = $_obj.val();
		in_orderQty = obj;
	}
	
	//숫자인지확인
	if(!isOnlyNumber(tmpOrderQty)) {
		alert(fnJsMsg(view_messages.error.orderQtyNumber));	//주문수량은 숫자만 입력 가능합니다.
		if(minOrderQty == 0 || minOrderQty == null || minOrderQty == "" || minOrderQty == "undefined" || minOrderQty == undefined) {
			minOrderQty = 1;
		}
		$(in_orderQty).val(minOrderQty);
		return;
	}
	
	if(p_gubun=="incre") {
		//$('#orderQty_'+p_prodCd+prodIdx).val(Number(tmpOrderQty)+1);
		$(in_orderQty).val(Number(tmpOrderQty)+1);
		
	}else if(p_gubun=="decre") {
		//$('#orderQty_'+p_prodCd+prodIdx).val(Number(tmpOrderQty) < Number(minOrderQty)? Number(minOrderQty) : Number(tmpOrderQty)-1);
		$(in_orderQty).val(Number(tmpOrderQty) < Number(minOrderQty)? Number(minOrderQty) : Number(tmpOrderQty)-1);
	} else {
		//수량 하드수정
	}
	
	//현재수량 재셋팅
	//tmpOrderQty		= $('#orderQty_'+p_prodCd+prodIdx).val();
	tmpOrderQty	= $(in_orderQty).val();
	
	if(Number(tmpOrderQty) < Number(minOrderQty)) {
		alert(fnJsMsg(view_messages.error.productOrderQty, minOrderQty, maxOrderQty));	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
		//$("#orderQty_"+p_prodCd+prodIdx).val(minOrderQty);
		$(in_orderQty).val(minOrderQty);
		return;
	} else if(Number(tmpOrderQty) > Number(maxOrderQty)) {
		alert(fnJsMsg(view_messages.error.productOrderQty, minOrderQty, maxOrderQty));	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
		//$("#orderQty_"+p_prodCd+prodIdx).val(maxOrderQty);
		$(in_orderQty).val(maxOrderQty);
		return;
	}
}

//장바구니 담기(딜상품이미지의 장바구니)-슬라이드
function dealProdAddBasketPopOption(p_onlineProdTypeCd, p_optionYn, p_categoryId, p_prodCd) {
	if("Y" == previewYn) {
        alert(fnJsMsg(view_messages.fail.doesNotFnc));        //미리보기에서는 제한되는 기능입니다.
        return;
    } else {
    	var flag = global.isLogin(document.location.href);
    	if(flag) {
    		goPopupProdOption(p_prodCd, 'basket', p_onlineProdTypeCd, p_categoryId);
    	}
    }
}

//장바구니 담기(상품이미지의 장바구니)-슬라이드
function prodAddBasketPopOption(p_onlineProdTypeCd, p_optionYn, p_categoryId, p_prodCd, p_minQty, p_maxQty){
	if("Y" == previewYn) {
        alert(fnJsMsg(view_messages.fail.doesNotFnc));        //미리보기에서는 제한되는 기능입니다.
        return;
    } else {
        if(p_optionYn=="Y"){
        	var flag = global.isLogin(document.location.href);
        	if(flag) {
        		goPopupProdOption(p_prodCd, 'basket', p_onlineProdTypeCd, p_categoryId);
        	}
        }else{
            //옵션없는경우 바로 장바구니담기
            addBasketProdListOneItem(null, null, 'B', p_prodCd, '', document.location.href);		//addBasketProdListOneItem(obj, event, p_gubun, p_prodCd, p_area_idx, p_url) 
        }
    }
}

function addBasket(obj, event){
	if("Y" == previewYn) {
		alert(fnJsMsg(view_messages.fail.doesNotFnc));		//미리보기에서는 제한되는 기능입니다.
		return;
	}
	
	var $selectedObj = null, 
		$checkBoxesObj = null;
		$checkedBoxesObj = null;
	
	// Tab 있는 페이지의 경우, tab 객체 선택.
	$(".prod-list").closest('.active').each(function(index, i) {
		var activeTab = $(i).attr('id');
		if(activeTab != null && activeTab.length > 0) {
			$selectedObj = $(i); 
		}
	});
	
	if($selectedObj != null && $selectedObj.length > 0){
		$checkBoxesObj = $selectedObj.find('input[type=checkbox][id^=chk_]');
		$checkedBoxesObj = $selectedObj.find('input[type=checkbox][id^=chk_]:checked');
	}else{
		$checkBoxesObj = $('input[type=checkbox][id^=chk_]');
		$checkedBoxesObj = $('input[type=checkbox][id^=chk_]:checked');
	}
	
	if($checkBoxesObj.length==0){
		alert(fnJsMsg(view_messages.fail.addCartNoProd));	//장바구니에 담을 수 있는 상품이 없습니다.
		return;
	}
	if($checkedBoxesObj.length==0){
		alert(fnJsMsg(view_messages.error.notSelected));	//선택된 항목이 없습니다. (상품을 1개 이상 선택해 주세요.)
		return;
	}
	
	cart.adds(obj, $checkedBoxesObj);
	return false;
}

/*
 * 장바구니 담기1(옵션 없는 상품)
*/
function addBasketProdListOneItem(obj, event, p_gubun, p_prodCd, p_area_idx, p_url) {
	// 장바구니 담기 성공 레이어 노출
	if("Y" == previewYn) {
		alert(fnJsMsg(view_messages.fail.doesNotFnc));		//미리보기에서는 제한되는 기능입니다.
		return;
	} else {
		var flag = global.isLogin(p_url);
		
		if(!flag){
			return;
		}
		var $_obj = $(obj)
			, $_orderQty = null
			, tmpOrdedQty = 1
			, prodIdx = ''
			, prodCd = ''
			, categoryId = ''
			, orderQty = ''
			, minQty = ''
			, maxQty = '';
		
		if(p_area_idx != null && p_area_idx != "" && p_area_idx != "undefined" && p_area_idx != undefined) {
			prodIdx = "_" + p_area_idx;
		}
		
		if($_obj.attr('data-prodCd') && $_obj.attr('data-categoryId')){
			prodCd = $_obj.attr('data-prodCd');
			categoryId = $_obj.attr('data-categoryId');
			minQty = $_obj.attr('data-minQty');
			maxQty = $_obj.attr('data-maxQty');
			
			if($_obj.attr('data-orderQty') !== undefined){
				orderQty = $_obj.attr('data-orderQty');
			}
		}else{
			prodCd = $("#prodCd_"+p_prodCd+prodIdx).val();
			categoryId = $("#categoryId_"+p_prodCd+prodIdx).val();
			minQty = $("#minQty_"+p_prodCd+prodIdx).val();
			maxQty = $("#maxQty_"+p_prodCd+prodIdx).val();
		}
		
		if( orderQty == ''){
			$_orderQty = $("#orderQty_"+p_prodCd+prodIdx);
			orderQty = $_orderQty.val();
		}
		
		if(prodIdx != "") {
			/*if($(obj).parent().siblings('img') != null && $(obj).parent().siblings('img').length > 0) {
				//슬라이드형이라서 주문수량이 없는 경우, 주문수량을 1로 셋팅한다.
				tmpOrdedQty = 1;
			} else {*/
				tmpOrdedQty = orderQty;
				/*}*/
		} else if(prodIdx == "") {
			if( $_orderQty != null && $_orderQty.length == 0)  {
				tmpOrdedQty = minQty;
			}
		}
		var minOrderQty = minQty;	//최소구매수량
		var maxOrderQty = maxQty;	//현재 구매할수 있는 수량
		
		//01.수량 체크
		if(isOnlyNumber(tmpOrdedQty) == false) {
			alert(fnJsMsg(view_messages.error.orderQtyNumber));	//주문수량은 숫자만 입력 가능합니다.
			return;
		} else if(Number(tmpOrdedQty) < Number(minOrderQty)){
			alert(fnJsMsg(view_messages.error.productOrderQty, minOrderQty, maxOrderQty));	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
			$_orderQty.val(minOrderQty);
			return;
		} else if(Number(tmpOrdedQty) > Number(maxOrderQty)){
			alert(fnJsMsg(view_messages.error.productOrderQty, minOrderQty, maxOrderQty));	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
			$_orderQty.val(maxOrderQty);
			return;
		}

		var periDeliYnValue = "N";
		if(p_gubun == "P") {
			periDeliYnValue = "Y";
		}
		
		var basketItems=[];
		basketItems.push({
			prodCd: prodCd,				// 상품코드
			itemCd: "001",				// 단품코드
			bsketQty: Number(tmpOrdedQty),	// 주문수량
			categoryId: categoryId,			// 카테고리ID
			nfomlVariation: null,			// 옵션명, 골라담기의 경우 옵션명:수량
			overseaYn: 'N',					// 해외배송여부
			prodCouponId: null,				// 즉시할인쿠폰ID
			oneCouponId: null,				// ONE 쿠폰ID
			cmsCouponId: null,				// CMS 쿠폰ID
			markCouponId: null,				// 마케팅제휴쿠폰ID
			periDeliYn: periDeliYnValue		// 정기배송여부
		});

		if(p_gubun == "B" || p_gubun == "P") {
			/*if (!confirm(fnJsMsg(view_messages.confirm.addCart))) {	//선택한 상품을 장바구니에 담으시겠습니까?
				return;
			}*/
			//fnAddBasketItem();	//as-is
			global.addBasket(basketItems, function(data) {
				//alert(fnJsMsg(view_messages.result.addedCart));		//선택하신 상품을 장바구니에 담았습니다.
				// 장바구니 담기 성공 레이어 노출
				layerpopTriggerBtn(obj, event);
			});
		} else if(p_gubun == "D" ) {
			//fnDirectOrderItem();
			global.addDirectBasket(basketItems);
		}
	}
}
//버튼 클릭시 버튼의 이벤트에 따라 분류
var layerpopTriggerCompleteBtn = function(eventE, event) {
	
	var target_ele = '.layerpop-target';
	var layerComplete = $(target_ele).find('.layerpop-complete');
	contentAttach(layerComplete);
	$(eventE).removeClass('active').next(target_ele).remove();
	$(eventE).parent().prepend(layerComplete.next());
	$('html').unbind('click');
	var topValue = $(target_ele).outerHeight(true);
	var marginValue = $(target_ele).outerWidth(true)/2;
	$(eventE).siblings(target_ele).css({top:(-topValue) + 'px', 'margin-left':(-marginValue) + 'px', 'left':'50%'});
	$(eventE).siblings(target_ele).show();
	//$(eventE).siblings(target_ele).show().css({top:(topValue) + 'px'+leftValue+widthValue});
};
//상품리스트 스크립트 E=================
//상품 개별 찜/찜취소하기
function addProdWishList(obj, event, p_prodCd, p_categoryId, p_forgnDelyplYn, p_area_idx, p_url) {
	if("Y" == previewYn) {
		alert(fnJsMsg(view_messages.fail.doesNotFnc));		//미리보기에서는 제한되는 기능입니다.
		return;
	} else {
		if("${const:getString('NO_MEMBER_NO')}" == "${_FrontSessionEntity.member_no}"){
		      alert(fnJsMsg(view_messages.fail.noMember));		//비회원은 찜하기가 안됩니다.
		      return;
		}

		var flag = global.isLogin(p_url);
		
		if(flag) {
			var prodIdx = "";
			var prodIdx2 = "";
			if(p_area_idx != null && p_area_idx != "" && p_area_idx != "undefined" && p_area_idx != undefined) {
				prodIdx = "_" + p_area_idx;
				prodIdx2 = "-" + p_area_idx;
			}
			var cateId = "";
			if(p_categoryId != null && p_categoryId != "" && p_categoryId != "undefined" && p_categoryId != undefined) {
				cateId = "_" +p_categoryId;
			}
			var wishYn = "Y";		// 'Y':찜, 'N':찜취소
			var defaultDatas = {};
			if(p_prodCd != "") {
				defaultDatas = {
						categoryId:p_categoryId
		                ,prodCd: p_prodCd            // 상품코드
		                ,forgnDelyplYn: p_forgnDelyplYn           // 해외배송여부
					};
				if($("#aList") != null && $("#aList").length > 0) {
					if($("#aList").hasClass("active")) {
						if($("#wish_"+p_prodCd+prodIdx+cateId).html() == "찜") {
			    			wishYn = "Y";
			    		} else {
			    			wishYn = "N";
			    		}
					} else {
						if($("#wish_"+p_prodCd+prodIdx+cateId).hasClass("active")) {
							wishYn = "N";
						} else {
							wishYn = "Y";
						}
					}
				} else {
					if($("#wish_"+p_prodCd+prodIdx+cateId).hasClass("active")) {
						wishYn = "N";
					} else {
						wishYn = "Y";
					}
				}			
			} else {
				
			}
			var classNm = (wishYn=="Y" ? "selected":"selected-cancel");
			if(wishYn == "N") {
				defaultDatas = {
		                prodCd: p_prodCd            // 상품코드
					};
				global.deleteWish(defaultDatas, function(data) {
					if(data == "") {
						data = {message : "찜하기가 취소되었습니다"};
					}
					wishResult(obj, event, p_prodCd, p_categoryId, p_area_idx, classNm, data);
	            },
	            function(xhr, status, error) {
	            	if(xhr.status == "200" && xhr.statusText == "OK") {
						data = {message : "찜하기가 취소되었습니다"};
					}
					wishResult(obj, event, p_prodCd, p_categoryId, p_area_idx, classNm, data);
	            }
				);
			} else {
				global.addWish(defaultDatas, function(data) {
					wishResult(obj, event, p_prodCd, p_categoryId, p_area_idx, classNm, data);
				});
			}
		}
	}
}

function wishResult(obj, event, p_prodCd, p_categoryId, p_area_idx, classNm, data) {
	var msg = data.message;
    var templateStr = $.templates("#wish-prod");
    var resultLayer = templateStr.render({
        "msg": msg,
        "classNm": classNm
    });
    $("#wish-prod").text(resultLayer);
	
    layerpopTriggerBtn( $(obj), event );
    
    if($("#aList") != null && $("#aList").length > 0) {
    	if($("#aList").hasClass("active")) {
    		if($("#wish_"+p_prodCd+"_"+p_area_idx+"_"+p_categoryId).html() == "찜") {
				$("button[id^=wish_"+p_prodCd+"]").html("찜취소");
			} else {
				$("button[id^=wish_"+p_prodCd+"]").html("찜");
			}
    	} else {
    		if(classNm == "selected") {
    		    	$("a[id^=wish_"+p_prodCd+"]").addClass("active");
    		    } else {
    		    	$("a[id^=wish_"+p_prodCd+"]").removeClass("active");
    		    }
    	}
    } else {
    	if(classNm == "selected") {
	    	$("a[id^=wish_"+p_prodCd+"]").addClass("active");
	    } else {
	    	$("a[id^=wish_"+p_prodCd+"]").removeClass("active");
	    }
    }

}
 
//상품목록 상,하단- 찜하기(선택된 상품 찜하기)
function addWishList(obj, event, _url) {
	if("Y" == previewYn) {
        alert(fnJsMsg(view_messages.fail.doesNotFnc));        //미리보기에서는 제한되는 기능입니다.
        return;
    } else {
    	var selectedObj = null;
		$(".prod-list").closest('.active').each(function(index, i) {
			var activeTab = $(i).attr('id');
    		if(activeTab != null && activeTab.length > 0) {
				selectedObj = $(i); 
			}
		});
		
		if(selectedObj != null && selectedObj.length > 0) {
			if(selectedObj.find('input[type=checkbox][id^=chk_]').length==0){
				alert(fnJsMsg(view_messages.fail.addWishNoProd));	//찜바구니에 담을 수 있는 상품이 없습니다.
				return;
			}
			if(selectedObj.find('input[type=checkbox][id^=chk_]:checked').length==0){
				alert(fnJsMsg(view_messages.error.notSelected));		//선택된 항목이 없습니다. (상품을 1개 이상 선택해 주세요.)
				return;
			}
		} else {
			if($('input[type=checkbox][id^=chk_]').length==0){
				alert(fnJsMsg(view_messages.fail.addWishNoProd));	//찜바구니에 담을 수 있는 상품이 없습니다.
				return;
			}
			if($('input[type=checkbox][id^=chk_]:checked').length==0){
				alert(fnJsMsg(view_messages.error.notSelected));		//선택된 항목이 없습니다. (상품을 1개 이상 선택해 주세요.)
				return;
			}
		}
    	
    	var flag = global.isLogin(_url);
    	if(flag) {

    		var params = [];

    	    $('.compare-check').find('input[type=checkbox][id^=chk_]:checked').each(function(index){
    			var prodInfos = ($('.compare-check').find("#"+this.id).val()).split(';');
    			params.push({
    				prodCd: prodInfos[0],
    				forgnDelyplYn:"N",
    				categoryId:prodInfos[2]
    			});
    		});
    		
    		var classNm = "selected";
    		
    		global.addWish(params, function(data) {
				var templateStr = $.templates("#wish-prod");
		        var resultLayer = templateStr.render({
		            "msg": data.message,
		            "classNm": classNm
		        });
		        $("#wish-prod").text(resultLayer);
		        
		        layerpopTriggerBtn( $(obj), event );
		        
		        $.each(params, function(i, value) {
		        	 if($("#aList") != null && $("#aList").length > 0) {
		        		 if($("#aList").hasClass("active")) {
		        			 $("button[id^=wish_"+value.prodCd+"]").html("찜취소");
		        		 } else {
		        			 $("a[id^=wish_"+value.prodCd+"_").addClass("active");
		        		 }
		 	        } else {
			        	$("a[id^=wish_"+value.prodCd+"_").addClass("active");
		 	        }
	    		});
			});
    	}
    }
}

//상품 찜/찜취소하기
function fnProdAddWishList(prodCds, forgnDelyplYns, categoryIds, wishYn){
	var returnValue = "";
	var params = {};
    params["prodCds"] = [];
    params["forgnDelyplYns"] = [];
    params["categoryIds"] = [];

    $.each(prodCds, function(i, value){
        params["prodCds"].push( value );
    });
    $.each(forgnDelyplYns, function(i, value){
        params["forgnDelyplYns"].push( value );
    });
    $.each(categoryIds, function(i, value){
        params["categoryIds"].push( value );
    });
    var _url = "";
    if(wishYn == "Y") {
    	_url = _LMAppSSLUrl+"/mymart/ajax/insertWishList.do";
    } else {
    	_url = _LMAppSSLUrl+"/mymart/ajax/deleteProdWishList.do";
    }
	
	$.ajax({
        type       : "POST" ,
        url        :  _url ,
        data       : params ,
        async      : false ,
        dataType   : "text" ,
        success    : function(response){
        	var jsonData = eval( "(" + response + ")" );
      	  	var result = jsonData[0]; 
      	  	
         	returnValue = result.ERR_NO+":" +result.SHOW_MSG;
        } ,
		cache      : true ,
		error : function(request,status,error){
			//alert("code:"+request.status+"error:"+error+"\n"+"message:"+request.responseText+"\n");
			returnValue =  request.status;
		}
    });
	
	return returnValue;
}

//상품목록 상,하단 - 비교하기
function goCompare() {
	if("Y" == previewYn) {
        alert(fnJsMsg(view_messages.fail.doesNotFnc));        //미리보기에서는 제한되는 기능입니다.
        return;
    } else {
        //01.수량 체크
    	var chkCnt = $('.compare-check').find('input[type=checkbox][id^=chk_]:checked').length;
        if($('.compare-check').find('input[type=checkbox][id^=chk_]').length==0){
            alert(fnJsMsg(view_messages.fail.compareNoProd));   //비교하기 할 수 있는 상품이 없습니다.
            return;
        }
            
        if($('.compare-check').find('input[type=checkbox][id^=chk_]:checked').length==0){
            alert(fnJsMsg(view_messages.error.notSelected));     //선택된 항목이 없습니다. (상품을 1개 이상 선택해 주세요.)
            return;
        }
    
        //2,3개만 가능
        var maxCount =  "";
        if($('.compare-check').find('input[type=checkbox][id^=chk_]:checked').length < 2
                || $('.compare-check').find('input[type=checkbox][id^=chk_]:checked').length > 3
        ){ 
            alert(fnJsMsg(view_messages.fail.compareProdCount));    //비교하기는 2개 이상 3개 이하만 가능합니다.
            return;
            }
    
        //02.PARAM 셋팅
        var prods       = "";   //비교할 상품들   
        $('.compare-check').find('input[type=checkbox][id^=chk_]:checked').each(function(index){
        	var prodInfos = ($('.compare-check').find("#"+this.id).val()).split(';');    		
            prods += (index==0) ? prodInfos[0] : "|^|"+prodInfos[0];
        });
    
        //03.전송     
        goProductCompare(prods);
    }
}