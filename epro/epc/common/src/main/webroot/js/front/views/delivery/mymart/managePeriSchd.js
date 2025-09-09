"use strict";

function callBack_$fnGetDiscountList(response){
    var jsonData = eval( "(" + response + ")" );
    for (var i = 0; i < jsonData.length; i++) {
    	var param = jsonData[i];
        $("#dcList").append('<li>'+param.DC_TYP_NM+'<em class="txt-red flt-right">'+setComma(param.DC_AMT)+'</em></li>');
    }
}

//선택된 정기배송코드
var periDeliId;

function fnSoutAlert(){
	alert("품절된 상품입니다.");
}

//정기배송 완전취소
function cxlPeri(){
	var params = {
			periDeliId: $('input[name=periDeliId]').val()
			,exPeriDeliId: $('input[name=periDeliId]').val()
	};

	$.getJSON("/mymart/api/cancelPeriAll.do", params)
	.done(function(data) {
		if(jResult(data)){
			alert('삭제되었습니다');
			//$('input[name=periDeliId]').val('');
			//window.location.reload();
			$('input[name=periDeliId]').val("");
			var form = $('#periManager').get(0);
			form.submit();
		}
	});
}

//2017.01.24 이승남
//정기배송 수정
function updatePeri(val){
	var tempMsg = ""
	if(val == 'delyDay'){
		if(!$("input[name^=deliDay]").is(":checked")){
			alert("배송 받을 요일을 선택해 주세요.");
			return;
		}else{
			tempMsg = "변경한 내용을 저장하시겠습니까?";
		}
	} else if(val == 'extend'){
		if(!$("input[name^=addMonth]").is(":checked")){
			alert("연장할 기간을 선택해 주세요.");
			return;
		}else{
			tempMsg = $('span.extend_deli_date').text()+"됩니다.\n연장하시겠습니까?";
		}
	}else{
		tempMsg = "변경한 내용을 저장하시겠습니까?";
	}
	if(!confirm(tempMsg)){
		return;
	}
	$('#updateKind').val(val);
	var form = $('#periManager').get(0);
	form.action = '/mymart/updatePeri.do';
	form.submit();
}

function  rePlacePeriSchd(){
	var form = $('#periManager').get(0);
	//쿠폰 적용 후 리로드
	$('#flag').val('Y');
	form.submit();
}

//레이어팝업 닫기
function closeLayer(obj){
	$(obj).parents('section.layer-bottom').remove();
	$('#layer_pop1').removeClass('active');
	$('#layer_pop2').removeClass('active');
	$('#layer_pop3').removeClass('active');
	$('#layer_pop4').removeClass('active');
}

function getExistingPeri(exPeriDeliId) {
	$('input[name=periDeliId]').val(exPeriDeliId);

	var form = $('#periManager').get(0);

	//정기배송 신청 정보 선택시
	$('#periDeliDegNoNew').val('');
	$('#flag').val('N');
	form.submit();
}

function setMonth(month){
	var start_date_str = $('#deli_start_dy').val();
	var start_yyyy = parseInt(start_date_str.substring(0,4));
	var start_mm = parseInt(start_date_str.substring(4,6));
	var start_dd = parseInt(start_date_str.substring(6,8));

	//ex)1월 29일 신청시, 연장 기간이 08월 00일로 끝나는 오류 발생하여 수정
	//var start_date = new Date(start_yyyy, start_mm, start_dd, 0, 0, 0);
	//var extMonth = parseInt($('#use_month').val())+parseInt(month);
	//start_date.setMonth(start_date.getMonth()+extMonth);
	var start_date_str2 = start_mm +"/"+start_dd+"/"+start_yyyy;
	var start_date = new Date(start_date_str2);
	var extMonth = parseInt($('#use_month').val())+parseInt(month);
	start_date.setMonth((start_date.getMonth()+1)+extMonth);

	var ext_yyyy = ""+start_date.getFullYear();
	var tmpmm = start_date.getMonth();
	if(tmpmm == 0){
		tmpmm = 12;
	}
	var ext_mm = ("0"+tmpmm).slice(-2);
	var ext_dd = ("0"+(start_date.getDate()-1)).slice(-2);
	$('#useMonth').val(extMonth);

	//2017.02.07 이승남_LM25767
	//var dispStr = start_yyyy+'.'+start_mm+'.'+start_dd+' ~ '+ext_yyyy+'.'+ext_mm+'.'+ext_dd+"까지 연장";
	var dispStr = start_yyyy+'.'+start_date_str.substring(4,6)+'.'+start_date_str.substring(6,8)+' ~ '+ext_yyyy+'.'+ext_mm+'.'+ext_dd+"까지 연장";
	$('span.extend_deli_date').html(dispStr);
	$('#useMonthNmV').val(dispStr);
}

function moveCal(ym){

	//2017.02.01 이승남 - 정기배송 관리_스케쥴 관리_이전, 다음달 로딩 시 위치 달력으로
	$("#positionVal").val($(document).scrollTop());

	var periodStart = $("#periodStart").val();
	periodStart = periodStart.substr(0,4) + periodStart.substr(5,2);
	var calYm = ym.substr(0,4) + ym.substr(5,2) ;
	if(calYm < periodStart){
		return;
	}

	var form = $('#periManager').get(0);
	form.reset();

	$('input[name=yyyymm]').val(ym);
	form.submit();

}

function cancelPeriSchd(periDeliId, periDeliDegNo){
	var deliCxlYn = $("#deliCnclYn").val();
	var deliMsg1;
	var deliMsg2;

	if(deliCxlYn == 'Y'){
		deliMsg1 = '이번 정기배송 상품 전체를 다시 받으시겠습니까?';
		deliMsg2 = '다시 받습니다';
	}else{
		deliMsg1 = '이번 정기배송 상품 전체를 취소하시겠습니까?';
		deliMsg2 = '취소 되었습니다';
	}

	if(!confirm(deliMsg1)) return;

	var params = {
			periDeliId: periDeliId,
			periDeliDegNo: periDeliDegNo,
			deliCnclYn: deliCxlYn=='Y'?'N':'Y'
	};

	$.getJSON("/mymart/api/cancelPeriSchd.do", params)
	.done(function(data) {
		if(jResult(data)){
			alert(deliMsg2);
			periDetail(params.periDeliId, params.periDeliDegNo);
		}
	});
}

function changeMdCmplDc(reCalc) {
	var cmplDc = 0;

	$("[name^=cmplDc]:radio:checked").each(function (idx) {
		$this = $(this);
		cmplDc += Number($this.val());

    	if (Number($this.val()) > 0) {
    		$('tr[name="trCmpl_' + $this.attr('CMPL_MST_ID') + '_' + $this.attr('GRPNG') + '"]').hide();
    		//realMdCmplDcCnt++;  <%-- 2016.03.24 김종현 삭제 --%>
    	}
    	else {
    		$('tr[name="trCmpl_' + $this.attr('CMPL_MST_ID') + '_'  + $this.attr('GRPNG') + '"]').show();
    	}
	});

	$('#cmplDc').val(cmplDc);
	//$('#cmplDcStr span[name=dcCmplAmt]').html(util.currency(cmplDc)); <%-- 2016.03.23 김종현 삭제 --%>

	if (reCalc) {
		fnRecalcPromotion('Trade');
	}
	else {
		calc.all(false);
	}
}

function setPromotion(mdCmplList){
    var $cmplTbody = $('#cmplTbody'); // 사은품 선택 영역
    var cmplList = "";
    var $cmplTrSelect = $('#cmplDcTbody'); // 할인/증정 선택형 영역
    var cmplDcList = "";
    var realMdCmpl01Cnt = 0;
    var realMdCmplList = new Array();

	var multiSelectCnt = 0;
    if (mdCmplList != null && mdCmplList.length > 0) {
    	var mdCmplMstId = "";
    	var mdGrpng = "";
    	var addRow = true;
		var cmplNm = "";
    	var cmplTableView = false;
    	var cmplDcTableView = false;
    	$("#cmplTbody").empty();
    	$.each(mdCmplList, function(i, mdCmpl) {
	    	// 할인/증정 일경우 skip
    		if (mdCmpl.OFFER_TY == '4005' && mdCmpl.DSCNT_AMT != null && Number(mdCmpl.DSCNT_AMT) > 0) {
		    	return;
    		}

    		cmplTableView = true;
    		if (mdCmpl.PAY_COND_FG == '01') {
    			realAllCardDc += 1;
    		}

			if (mdCmpl.GIFT_TY == '02' || mdCmpl.GIFT_TY == '06' ) { // 상품권
				cmplNm = "마일리지: " + util.currency(mdCmpl.GIFT_PRICE) + " 점";
			}
			else {
				cmplNm = mdCmpl.CMPL_NM;
			}

    		addRow = (mdGrpng != mdCmpl.GRPNG);
    		if (addRow) {
	    		$("#cmplTbody").append("<tr></tr>");
			}
			var tr = $("#cmplTbody tr:last");

    		$(tr).attr({
		    	name: "trCmpl_" + mdCmpl.CMPL_MST_ID + '_' + mdCmpl.GRPNG,
    		})
    		.append("<th scope=\"row\"><span class=\"require\"><i>필수항목</i></span>"+ mdCmpl.CMPL_MST_NM + "</th>")
    		.append("<td></td>");
			$(tr).find("td").append("<div class=\"select-type1 w-type80\"></div>");
			$(tr).find("td > div").append("<label for=\"\">" + cmplNm + "</label>")
			.append("<select name=\"mdCmplGoods\"><option value=''>선택하세요</option></select>");
		    $(tr).find("td > div > select").append('<option '
					+ ' value="' + mdCmpl.CMPL_MST_ID + $(mdCmpl.PROD_CD).nvl('_') + $(mdCmpl.ITEM_CD).nvl('_')
					+ '" CMPL_MST_ID="' + mdCmpl.CMPL_MST_ID
					+ '" GIFT_TY="' + mdCmpl.GIFT_TY
					+ '" PROD_CD="' + $(mdCmpl.PROD_CD).nvl('')
					+ '" ITEM_CD="' + $(mdCmpl.ITEM_CD).nvl('')
					+ '" GRPNG="' + $(mdCmpl.GRPNG).nvl('') + '" >'
					+ cmplNm + '</option>');
		    mdGrpng = mdCmpl.GRPNG;
    	});
		if (cmplTableView) {
			$('#cmplInfo').removeClass('hide');
		}
		else {
			$('#cmplInfo').addClass('hide');
		}
		mdGrpng = "";

		$("#cmplDcTbody").empty();
    	$.each(mdCmplList, function(i, mdCmpl) {
	    	// 할인/증정
    		if (mdCmpl.OFFER_TY == '4005' && mdCmpl.DSCNT_AMT != null && Number(mdCmpl.DSCNT_AMT) > 0) {

	    		cmplDcTableView = true;

	    		if (mdCmpl.PAY_COND_FG == '01') {
	    			realAllCardDc += 1;
	    		}

    			if (mdCmpl.GIFT_TY == '02' || mdCmpl.GIFT_TY == '06' ) { // 상품권
    				cmplNm = "마일리지: " + util.currency(mdCmpl.GIFT_PRICE) + " 점";
    			}
    			else {
    				cmplNm = mdCmpl.CMPL_NM;
    			}

    			var tr = $("<tr></tr>");
	    		$(tr).attr({
			    	name: "trCmpl_" + mdCmpl.CMPL_MST_ID + '_' + mdCmpl.GRPNG,
	    		})
	    		.append("<th scope=\"row\"><span class=\"require\"><i>필수항목</i></span>"+ mdCmpl.CMPL_MST_NM + "</th>")
	    		.append("<td></td>");
    			$(tr).find("td").append("<ul class=\"radio-list-h\"></ul>");
    			$(tr).find("td > ul").append("<li><span class=\"radio-data\"></span><label for=\"\">할인 적용</label><span class=\"txt-wbrown\">(" + util.currency(Number(cmplDcAmt)) + " 원 할인)" + "</span><li>");
    			$(tr).find("td > ul li:first .radio-data").append('<input type="radio" id="' + mdCmplMstId + '_' + mdGrpng + '_dc" name="cmplDc_' + mdCmplMstId + '_' + mdGrpng + '" value="' + cmplDcAmt + '" checked="checked" '
				+ 'PROD_TRADE_DIVN="Trade" '
				+ 'PROD_CD="" '
				+ 'ITEM_CD="" '
				+ 'PRE_ORDER_ITEM_SEQ="" '
				+ 'PAY_COND_FG="' + mdCmpl.PAY_COND_FG + '" '
				+ 'CARDCO_CD="' + mdCmpl.CARDCO_CD + '" '
				+ 'BOS_EVT_KIND="1" '
				+ 'PROMO_CL_TYP_CD="27" '
				+ 'PROMO_CL_MST_NO="' + mdCmpl.EVT_MST_SEQ + '" '
				+ 'PROMO_CL_SEQ="' + mdCmpl.EVT_SEQ + '" '
				+ 'EVNT_APPLY_QTY="1" '
				+ 'EVNT_APPLY_AMT="' + cmplDcAmt + '" '
				+ 'PROMO_KIND_CD="01" '
				+ 'PRVS_TO_NO="" '
				+ 'SCP_STR_CD="" '
				+ 'DC_EVT_CONTENT="' + mdCmpl.DC_EVT_CONTENT + '" '
				+ 'REP_COUPON_ID="" '
				+ 'PROD_SELL_QTY="' + $(mdCmpl.SELL_CNT).nvl('0') + '" '
				+ 'OFFER_TY="4005" '
				+ 'MD_EVT_CD="' + mdCmpl.MD_EVT_CD + '" '
				+ 'CMPL_MST_ID="' + mdCmplMstId + '" '
				+ 'GRPNG="' + mdGrpng + '" '
				+ 'onClick="javascript:changeMdCmplDc(true);" '
				+ '/>');
    			$(tr).find("td > ul").append("<li><span class=\"radio-data\"></span><label for=\"\">증정</label><span class=\"txt-wbrown\">("
	    				+ util.currency(Number(cmplDcAmt))
	    				+ ' P)'
	    				+ '</span><li>');
				$(tr).find("td > ul li:last .radio-data").append('<input type="radio" id="' + mdCmplMstId + '_' + mdGrpng + '_cmpl" name="cmplDc_' + mdCmplMstId + '_' + mdGrpng + '" value="0" '
	    				+'CMPL_MST_ID="' + mdCmplMstId + '" '
	    				+'GRPNG="' + mdGrpng + '" '
	    				+'onClick="javascript:changeMdCmplDc(true);" ');
	    		$("#cmplDcTbody").append($(tr));
            	$('#cmplDc').val(Number($('#cmplDc').val()) + Number(cmplDcAmt));
    		}
    	});
		if (cmplDcTableView) {
			$('#cmplDcInfo').removeClass('hide');
		}
		else {
			$('#cmplDcInfo').addClass('hide');
		}

    	var mdCmplMstId = "";
    	var mdGrpng = "";
    	var firstChecked = "";
    	var lastCmplCnt = 0;
		var cmplNm = "";

    	for (var idx = 0; idx < mdCmplList.length; idx++) {
    		var mdCmpl = mdCmplList[idx];
    		var addCmplDc = false;

    		if (mdCmpl.PAY_COND_FG == '01') {
    			realAllCardDc += 1;
    		}

    		if (idx == 0) { // 처음이면
    			addCmplDc = true;

    			var displayStyle = "";
    			if (mdCmpl.CMPL_CNT == 1) {
    				displayStyle = ' display:none;';
    			}
    			else {
    				displayStyle = ' display:;';
    				multiSelectCnt++;
    			}

    			cmplList += '<tr name="trCmpl_' + mdCmpl.CMPL_MST_ID + '_' + mdCmpl.GRPNG + '"><th scope="row"><span class="require"><i>필수항목</i></span>'
    				+ mdCmpl.CMPL_MST_NM
    				+ '</th><td><select name="mdCmplGoods"' + displayStyle +'">';
    			firstChecked = 'selected="selected"';
    			realMdCmpl01Cnt++;
    		}
    		else {
    			if (mdCmplMstId != mdCmpl.CMPL_MST_ID || mdGrpng != mdCmpl.GRPNG) {
	    			addCmplDc = true;

	    			var displayStyle = "";
	    			var displaySpan = "";
	    			if (mdCmpl.CMPL_CNT == 1) {
	    				displayStyle = ' display:none;';
	    			}
	    			else {
	    				displayStyle = ' display:;';
	    				multiSelectCnt++;
	    			}

			    	if (lastCmplCnt == 1) {
						displaySpan = cmplNm;
			    	}

    				cmplList += '</select>' + displaySpan + '</td></tr>'
	    				+ '<tr name="trCmpl_' + mdCmpl.CMPL_MST_ID + '_' + mdCmpl.GRPNG + '"><th scope="row"><span class="require"><i>필수항목</i></span>'
	    				+ mdCmpl.CMPL_MST_NM
    					+ '</th><td><select name="mdCmplGoods"' + displayStyle +'">';
	    			firstChecked = 'selected="selected"';
	    			realMdCmpl01Cnt++;
    			}
    		}

			if (mdCmpl.GIFT_TY == '02' || mdCmpl.GIFT_TY == '06' ) { // 상품권
				cmplNm = "마일리지: " + util.currency(mdCmpl.GIFT_PRICE) + " 점";
			}
			else {
				cmplNm = mdCmpl.CMPL_NM;
			}

			cmplList += '<option ' + firstChecked
				+ ' value="' + mdCmpl.CMPL_MST_ID + $(mdCmpl.PROD_CD).nvl('_') + $(mdCmpl.ITEM_CD).nvl('_')
				+ '" CMPL_MST_ID="' + mdCmpl.CMPL_MST_ID
				+ '" GIFT_TY="' + mdCmpl.GIFT_TY
				+ '" PROD_CD="' + $(mdCmpl.PROD_CD).nvl('')
				+ '" ITEM_CD="' + $(mdCmpl.ITEM_CD).nvl('')
				+ '" GRPNG="' + $(mdCmpl.GRPNG).nvl('') + '" >'
				+ cmplNm + '</option>';

			firstChecked = "";
			mdCmplMstId = mdCmpl.CMPL_MST_ID;
			mdGrpng = mdCmpl.GRPNG;

			lastCmplCnt = mdCmpl.CMPL_CNT

			// 할인/증정 선택형
			var cmplDcAmt = mdCmpl.DSCNT_AMT;
			if (addCmplDc && mdCmpl.OFFER_TY == '4005' && cmplDcAmt != null && Number(cmplDcAmt) > 0) {

    			cmplDcList += '<tr>'
    				+'<th scope="row"><span class="require"><i>필수항목</i></span>'
    				+mdCmpl.CMPL_MST_NM
    				+'</th>'
    				+'<td>'
    				+'<ul class="radio-list-h">'
    				+'<li>'
    				+ '<span class="radio-data active">'
    				+ '<input type="radio" id="' + mdCmplMstId + '_' + mdGrpng + '_dc" name="cmplDc_' + mdCmplMstId + '_' + mdGrpng + '" value="' + cmplDcAmt + '" checked="checked" ';
    				+ 'PROD_TRADE_DIVN="Trade" '
    				+ 'PROD_CD="" '
					+ 'ITEM_CD="" '
					+ 'PRE_ORDER_ITEM_SEQ="" '
					+ 'PAY_COND_FG="' + mdCmpl.PAY_COND_FG + '" '
					+ 'CARDCO_CD="' + mdCmpl.CARDCO_CD + '" '
					+ 'BOS_EVT_KIND="1" '
					+ 'PROMO_CL_TYP_CD="27" '
					+ 'PROMO_CL_MST_NO="' + mdCmpl.EVT_MST_SEQ + '" '
					+ 'PROMO_CL_SEQ="' + mdCmpl.EVT_SEQ + '" '
					+ 'EVNT_APPLY_QTY="1" '
					+ 'EVNT_APPLY_AMT="' + cmplDcAmt + '" '
					+ 'PROMO_KIND_CD="01" '
					+ 'PRVS_TO_NO="" '
					+ 'SCP_STR_CD="" '
					+ 'DC_EVT_CONTENT="' + mdCmpl.DC_EVT_CONTENT + '" '
					+ 'REP_COUPON_ID="" '
					+ 'PROD_SELL_QTY="' + $(mdCmpl.SELL_CNT).nvl('0') + '" '
					+ 'OFFER_TY="4005" '
					+ 'MD_EVT_CD="' + mdCmpl.MD_EVT_CD + '" '
					+ 'CMPL_MST_ID="' + mdCmplMstId + '" '
					+ 'GRPNG="' + mdGrpng + '" '
					+ 'onClick="javascript:changeMdCmplDc(true);" '
    				+ '/>'
    				+'</span>'
    				+'<label for="">할인 적용</label>'
    				+'<span class="txt-wbrown">('
    				+util.currency(Number(cmplDcAmt))
    				+ ' 원 할인)'
    				+'</span>'
    				+'</li>'
    				+'<li>'
    				+'<span class="radio-data">'
    				+'<input type="radio" id="' + mdCmplMstId + '_' + mdGrpng + '_cmpl" name="cmplDc_' + mdCmplMstId + '_' + mdGrpng + '" value="0" '
    				+'CMPL_MST_ID="' + mdCmplMstId + '" '
    				+'GRPNG="' + mdGrpng + '" '
    				+'onClick="javascript:changeMdCmplDc(true);" '
    				+'/>'
    				+'</span>'
    				+'<label for="">'
    				+'증정'
    				+'</label>'
    				+'<span class="txt-wbrown">('
    				+cmplNm
    				+')'
    				+'</span>'
    				+'</li>'
    				+'</ul>'
    				+'</td>'
    				+'</tr>'
    				;

            	$('#cmplDc').val(Number($('#cmplDc').val()) + Number(cmplDcAmt));
            	realMdCmplDcCnt++;
			}
    	}

    	var displaySpan = "";
    	if (lastCmplCnt == 1) {
			displaySpan = cmplNm;
    	}

		cmplList += '</select>' + displaySpan + '</td></tr>';
    }
}

var exItemSeq = "";
function setOption(current, itemSeq, prodCd, itemCd, optionYn){
	//alert(optionYn);
	exItemSeq = itemSeq;
	if(optionYn == "Y"){
		layerpopTriggerBtn( current, event );
		setPeriOptionLayer(prodCd, itemCd);
	}else{
		confirmOption(itemSeq, prodCd);
	}
}

var exProdCd = "";
function setPeriOptionLayer(prodCd, itemCd){
	var optionHtml = "";
	var params = {
			prodCd : prodCd
		  , itemCd : itemCd
	};
	exProdCd = prodCd;

	$.getJSON("/delivery/api/optionInfo.do", params)
	.done(function(data) {
		if (!!data.list) {
			optionHtml += "<option value=''>선택하세요</option>";
			$.each(data.list, function(){
				if(this.OPTION_CD == params.itemCd){
					$('#option_select').siblings('label').html(this.OPTION_NM);
					optionHtml += "<option value='" + this.OPTION_CD + "' selected='selected'>" + this.OPTION_NM + "</option>";
				}else{
					optionHtml += "<option value='" + this.OPTION_CD + "'>" + this.OPTION_NM + "</option>";
				}
			});
			$("#option_select").html(optionHtml);
			$('#optItemCount').val(Number($('#ordQty' + prodCd).val()));
			$('#maxOtQty').val(Number($('#maxQty_' + prodCd).val()));
			$('#minOtQty').val(Number($('#minQty_' + prodCd).val()));
		}
	});
}

function useCoupon(periDeliId, periDeliDegNo, setlCouponNo){
	var totPrice = $("#totRealPrc").val();
	var totPrice2 = Number($("#totRealPrc").val()) - Number($("#totRealDlvPrc").val());
	//var totSumSellPrc = Number(totSumSellPrcN) + Number(totSumSellPrcV) + Number(totSumSellPrcM) + Number(totSumSellPrcA);
	//var totSumDlvPrc = Number(totDlvPrcN) + Number(totDlvPrcV) + Number(totDlvPrcM) + Number(totDlvPrcA);
	//totPrice = totPrice.replace("원","");
	//totPrice = totPrice.replace(/,/gi, "");
	if(setlCouponNo == "null" && $("#repCouponId").val() != ""){
		setlCouponNo = $("#repCouponId").val();
	}
	window.open("/mymart/popup/periCoupon.do?periDeliId="+periDeliId+"&periDeliDegNo="+periDeliDegNo+"&setlCouponNo="+setlCouponNo+"&totPrice="+totPrice+"&totPrice2="+totPrice2, "periCouponPop", "width=1050px, height=965px, scrollbars=yes");
}

function getFormatDate(date){
	var year = date.getFullYear();
	var month = (1 + date.getMonth());
	var day = date.getDate();

	month = month >= 10 ? month : '0' + month;
	day = day >= 10 ? day : '0' + day;

	return year + '' + month + '' + day;
}

function periDetail(periDeliId, periDeliDegNo, flag){

	//특정 회차에 쿠폰 적용 후 페이지 리로드 될때 회차 유지를 위함
	if(flag == 'Y'){
		$("#flag").val("Y");
	}

	var xmlHttp;

	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		xmlHttp = new ActiveXObject('Msxml2.XMLHTTP');
	} else {
		return;
	}

	xmlHttp.open('HEAD', window.location.href.toString(), false);
	xmlHttp.setRequestHeader("Content-Type", "text/html");
	xmlHttp.send('');

	var serverDateTmp = xmlHttp.getResponseHeader("Date");
	var currentDateClass = new Date(serverDateTmp);

	var serverDate = getFormatDate(currentDateClass);

	/* 검증  alert
	if($("#flag").val() == 'Y'){
		alert("회차선택");
	}else{
		alert("배송선택");
	}*/

	var nowPeriDeliId = $("#nowPeriDeliId").val();
	var nowPeriDeliDegNo = $("#nowPeriDeliDegNo").val();

	$("#periInfo").html("");
	$("#storeProdList").html("");
	$("#onlineProdList").html("");
	$("#outrangeProdList").html("");
	$("#addItemList").html("");

	$("#storeItemBox").addClass("hide");
	$("#onlineItemBox").addClass("hide");
	$("#outrangeItemBox").addClass("hide");
	$("#addItemBox").addClass("hide");
	$("#totalProdSum").addClass("hide");
	if(!periDeliId||!periDeliDegNo){
		return;
	}

	var params = {
			periDeliId: periDeliId,
			periDeliDegNo: periDeliDegNo
	};

	//캘린더에서 선택한 회차를 쿠폰 적용시 reload될때 해당 회차를 유지하기 위해 추가
	//특정 회차에 쿠폰 적용 후 페이지 리로드 될때 회차 유지를 위함 flag = Y
	if(($("#periDeliDegNoNew").val() != null && $("#periDeliDegNoNew").val() != "") && $("#flag").val() == 'Y'){
		//처음 화면에 진입시 null 이후 periDeliDegNo으로 셋팅
		$("#periDeliDegNo").val(periDeliDegNo);

		if($("#periDeliDegNoNew").val() != periDeliDegNo){//회차 변경이 일어난 경우
			$("#periDeliDegNoNew").val(periDeliDegNo);
		}

			var params = {
					periDeliId: periDeliId,
					periDeliDegNo: $("#periDeliDegNoNew").val()
			};



	}else{
	var params = {
			periDeliId: periDeliId,
			periDeliDegNo: periDeliDegNo
	};
  }

	$.ajaxSetup({async: false});

	var today = new Date();

	$.getJSON("/mymart/api/getPeriScheduleInfo.do", params)
		.done(function(data) {
			if(jResult(data)){

                //---- 정기배송 목록의 합계 정보 -- 시작 -- By. 신성훈
                var sellPriceTotal  = data.sellPriceTotal; //전체 상품 금액
                var totalDcAmt      = data.totalDcAmt    ; //전체 할인금액
                var totalDeliverAmt = (1 * data.deliverAmtSum) - (1 * data.delAmt) ; //전체 배송비 = 배송비 합계 - 배송비쿠폰 할인 금액
                var payAmt          = data.payAmt        ; //총 결제예정금액
                //---- 정기배송 목록의 합계 정보 -- 끝 -- By. 신성훈

                //---------- 종류별 판매가 소계 정보 -- 시작 -- By. 신성훈
                var unitTotalAmt1 = 0; //
                var unitTotalAmt2 = 0; //매장 배송
                var unitTotalAmt3 = 0; //업체 택배
                var unitTotalAmt4 = 0; //매장 택배
                var unitTotalAmt5 = 0; //이번만 추가 상품
                //---------- 종류별 판매가 소계 정보 -- 끝 -- By. 신성훈

                var unitTotalDiscount1 = 0;
                var unitTotalDiscount2 = 0;
                var unitTotalDiscount3 = 0;
                var unitTotalDiscount4 = 0;
                var unitTotalDiscount5 = 0;


                //데이타 호출 후 현재 회차를 맞춤
                //특정 회차에 쿠폰 적용 후 페이지 리로드 될때 회차 유지를 위함
                if($("#flag").val() == 'Y'){
            	$("#nowPeriDeliDegNo").val(periDeliDegNo);
            	$("#periDeliDegNoNew").val(periDeliDegNo);
            	$("#periDeliDegNo").val(periDeliDegNo);
                }

				//$("#deliCnclYn").val(data.schdInfo.DELI_CNCL_YN);
                $("#deliCnclYn").val(data.schdInfo.TOTAL_DELI_CNCL_YN);
				var periInfoHtml = "";

				periInfoHtml += "<section class='reguldel-area'> ";
				periInfoHtml += "	<h3 class='gap-end-ttype4'>정기배송 정보 안내</h3> ";
				periInfoHtml += "	<div class='tbl-lst-v'> ";
				periInfoHtml += "		<table> ";
				periInfoHtml += "			<caption>정기배송 정보 안내</caption> ";
				periInfoHtml += "			<colgroup> ";
				periInfoHtml += "				<col style='width:10%;'/> ";
				periInfoHtml += "				<col style='width:35%;'/> ";
				periInfoHtml += "				<col style='width:10%;'/> ";
				periInfoHtml += "				<col style='width:35%;'/> ";
				periInfoHtml += "			</colgroup> ";
				periInfoHtml += "			<tbody> ";
				periInfoHtml += "				<tr> ";
				periInfoHtml += "					<th scope='row'>정기배송일</th> ";
				periInfoHtml += "					<td colspan='3'> ";
				periInfoHtml += "						<strong>"+ data.schdInfo.DELI_DATE + " " + data.schdInfo.PERI_DELI_DEG_NO +"회차 정기배송</strong> ";

				//특정 회차에 쿠폰 적용 후 페이지 리로드 될때 회차 유지를 위함
				if($("#flag").val() != 'Y'){
				$("input[name=periDeliDegNo]").val(data.schdInfo.PERI_DELI_DEG_NO);
				}

				periInfoHtml += "						<span class='delivery-info'>["+  data.schdInfo.DELI_TXT+"]</span> ";
				var deliDate = data.schdInfo.DELI_DATE.substr(0,4) + "" + data.schdInfo.DELI_DATE.substr(5,2) + "" + (data.schdInfo.DELI_DATE.substr(8,2) - 1);

				if(serverDate * 1 < deliDate * 1) {
					periInfoHtml += "						<button type='button' class='btn-form-type2 flt-right' title='새 창 열림' onclick='javascript:cancelPeriSchd(\""+data.schdInfo.PERI_DELI_ID+"\",\""+data.schdInfo.PERI_DELI_DEG_NO+"\");'>"+ data.schdInfo.DELI_CNCL_TXT +"</button> ";
				}
				periInfoHtml += "					</td> ";
				periInfoHtml += "				</tr> ";

				var isCouponEnabled = false;

				if(params.periDeliDegNo == data.schdInfo.MIN_DEG_NO) isCouponEnabled = true;

				//if(nowPeriDeliId == periDeliId && nowPeriDeliDegNo == periDeliDegNo){//수정 By 신성훈
				if(data.schdInfo.ORDER_ID == null && isCouponEnabled){
					periInfoHtml += "				<tr> ";
					periInfoHtml += "					<th scope='row'>결제쿠폰</th> ";

					//------------------------ 결제쿠폰 칸 -- 시작 ----------------------------------
					//--- 결제쿠폰 칸 수정하기 -- 시작 --By 신성훈 --
                    if (data.selectedSetlCpn) {
                    	periInfoHtml += "    <td>" + data.selectedSetlCpn.COUPON_NM ;
                    	$("#repCouponId").val(data.payCouponList[0].REP_COUPON_ID);
                    } else {
                        if (data.schdInfo && data.schdInfo.SETL_COUPON_USE_YN && data.schdInfo.SETL_COUPON_USE_YN == 'Y') {
                            periInfoHtml += "    <td>쿠폰없음";
                        } else {
                        	periInfoHtml += "    <td>적용하지 않음";
                        }
                    }
					//--- 결제쿠폰 칸 수정하기 -- 끝 --By 신성훈 --

					/*  //--- 주석처리함  --By 신성훈 --
					if(data.schdInfo.DELI_CNCL_YN == 'Y'){
						periInfoHtml += "					<td>적용하지 않음";
					}else{
						if($("#payCouponListLength").val() != "" && $("#payCouponListLength").val() != data.payCouponList.length){
							alert("상품변경으로 인하여 적용예정인 쿠폰이 변경되었습니다.");
						}

						//---- 수정 시작  By. 신성훈 -----
						var payCouponListCount = 0;
						if (data.payCouponList) {
							$("#payCouponListLength").val(data.payCouponList.length);
							payCouponListCount = data.payCouponList.length;
						}
						//---- 수정 끝  By. 신성훈 -----


						//--- 주석처리함  --By 신성훈 --
						if (payCouponListCount == 0) { //수정 By. 신성훈
					    	periInfoHtml += "					<td>쿠폰없음";
					    }else{
					    	if(data.schdInfo.COUPON_NM != null){
					    		var couponSelChk = 0;
					    		if(data.payCouponList != "" && data.payCouponList != null){
					    			for(var x=0;x<data.payCouponList.length;x++){
					    				if(data.payCouponList[x].REP_COUPON_ID == data.schdInfo.SETL_COUPON_NO){
					    					couponSelChk++;
					    				}
					    			}
					    		}
					    		if(couponSelChk == 0){
					    			alert("상품변경으로 인하여 적용예정인 쿠폰이 변경되었습니다.");
					    			var params = {
					    					periDeliId: periDeliId,
					    					periDeliDegNo: periDeliDegNo,
					    					setlCouponNo: ""
					    					};

					    			$.getJSON("/mymart/popup/updateperiCoupon.do", params)
					    			.done(function(data) {
					    				if(data.result == "true"){
					    				}
					    			});

					    			periInfoHtml += "					<td>"+ data.payCouponList[0].COUPON_NM +" ";
					    			$("#repCouponId").val(data.payCouponList[0].REP_COUPON_ID);
					    		}else{
						    		periInfoHtml += "					<td>"+ data.schdInfo.COUPON_NM +" ";
					    		}
					    	}else if(data.schdInfo.COUPON_NM == null &&  data.schdInfo.SETL_COUPON_NO == "000000000000000"){
					    		periInfoHtml += "					<td>적용하지 않음 ";
					    	}else{
						    	periInfoHtml += "					<td>"+ data.payCouponList[0].COUPON_NM +" ";
						    	$("#repCouponId").val(data.payCouponList[0].REP_COUPON_ID);
					    	}
					    }
					}
				    */


					//------------ 결제쿠폰 칸 에서 조회버튼 -----------
					if (data.schdInfo.DELI_CNCL_YN != 'Y') {
						var setlCpnNo = '000000000000';
						if(data.selectedSetlCpn != null) setlCpnNo = data.selectedSetlCpn.REP_COUPON_ID;
						periInfoHtml += "						<span><button type='button' title='새 창 열림' onclick='useCoupon(\""+data.schdInfo.PERI_DELI_ID+"\", \""+data.schdInfo.PERI_DELI_DEG_NO+"\", \""+setlCpnNo+"\");' class='btn-form flt-right'>조회</button></span> ";
					}

					periInfoHtml += "					</td> ";
					//------------------------ 결제쿠폰 칸 -- 끝 ----------------------------------


					//------------------------ 임직원할인 또는 마일리지 칸 ----------------------------------
					if(data.schdInfo.STAFF_DC_YN == 'Y'){
						periInfoHtml += "					<th scope='row'>임직원할인</th> ";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							periInfoHtml += "					<td>적용하지 않음</td>";
						}else{
							periInfoHtml += "					<td>자동적용</td> ";
						}
					}else{
						periInfoHtml += "					<th scope='row'>마일리지</th> ";
						periInfoHtml += "					<td id='milgUse'>"+ data.schdInfo.MILG_USE +"</td> ";
					}
					periInfoHtml += "				</tr> ";
					periInfoHtml += "				<tr> ";

					//---------------------------- 배송비쿠폰 칸 ------------------------------------
					periInfoHtml += "					<th scope='row'>배송비쿠폰</th> ";

					//--- '배송비 쿠폰' 칸 수정하기 -- 시작 --By 신성훈 --
                    if (data.selectedDelCpn) {
                    	periInfoHtml += "    <td>" + data.selectedDelCpn.COUPON_NM + "</td> ";
                    } else {
                        if (data.schdInfo && data.schdInfo.DELI_COUPON_USE_YN && data.schdInfo.DELI_COUPON_USE_YN == 'Y') {
                            periInfoHtml += "    <td>쿠폰없음</td>";
		                } else {
						    periInfoHtml += "    <td>적용하지 않음</td>";
		                }
                    }
					//--- '배송비 쿠폰' 칸 수정하기 -- 끝 --By 신성훈 --

                    /*  //--- 주석처리함  --By 신성훈 --
					if (data.schdInfo.DELI_CNCL_YN == 'Y') {
						periInfoHtml += "					<td id='dlvCoupon'>적용하지 않음</td>";
					} else {

						// ----- 수정 시작 -- By.신성훈 ---------------------
						if (data.deliveryCouponList && data.deliveryCouponList.length >= 1) {
							periInfoHtml += "					<td id='dlvCoupon'>"+ data.deliveryCouponList[0].COUPON_NM +"</td> ";
						} else {
							periInfoHtml += "					<td>쿠폰없음";
						}
						// ----- 수정 끝 -- By.신성훈 ---------------------
					    //if(data.deliveryCouponList.length == 0){
					    //	periInfoHtml += "					<td>쿠폰없음";
					    //}else{
					    //	periInfoHtml += "					<td id='dlvCoupon'>"+ data.deliveryCouponList[0].COUPON_NM +"</td> ";
					    //}
					}
					*/

					//---------------------------- 예치금 칸 ------------------------------------
					periInfoHtml += "					<th scope='row'>예치금</th> ";
					periInfoHtml += "					<td id='dpamtUse'>"+ data.schdInfo.DPAMT_USE +"</td> ";
					periInfoHtml += "				</tr> ";
				//} //수정 By 신성훈

				} // 주문번호 없을시

				periInfoHtml += "			</tbody> ";
				periInfoHtml += "		</table> ";
				if(isCouponEnabled){
					periInfoHtml += "		<p class='tbl-foot'> ";
					periInfoHtml += "			<ul class='bul-circle'> ";
					periInfoHtml += "				<li>쿠폰 및 보조결제수단은 결제시점에 자동 적용되며 결제예정금액은 적용 전 가격이므로, 쇼핑에 참고해 주세요</li> ";
					periInfoHtml += "				<li>쿠폰 및 보조결제수단 변경은 <span class='txt-wbrown'>마이롯데 &gt; 정기배송관리페이지</span>에서 변경 가능합니다.</li> ";
					periInfoHtml += "				<li>상품 취소 시, 적용 예정 쿠폰이 변경될 수 있습니다</li> ";
					periInfoHtml += "			</ul> ";
					periInfoHtml += "		</p> ";
				}
				periInfoHtml += "	</div> ";
				periInfoHtml += "</section> ";
				if(data.schdInfo.ORDER_ID != null){

				}else{
					periInfoHtml += "<div class='shiprefd-area-type3'>";
					periInfoHtml += "	<div class='del-schedule'>";
					if(data.schdInfo.DELI_CNCL_YN == 'Y'){
						periInfoHtml += "		<strong>배송예정상품 : </strong> <span>0개</span>";
					}else{
						periInfoHtml += "		<strong>배송예정상품 : </strong> <span id='prd_cnt'>"+ numberWithCommas(data.schdInfo.PROD_CNT) +"개</span>";
					}
					periInfoHtml += "	</div>";
					periInfoHtml += "	<div class='pay-schedule'>";
					//if(data.schdInfo.DPST_AMT != null){
					//	periInfoHtml += "		<strong>결제금액 : </strong> <span>";
					//}else{
						periInfoHtml += "		<strong>결제예정금액 : </strong> <span>";
					//}

					if(data.schdInfo.SETL_METHOD_NM != null){
						periInfoHtml += "		["+ data.schdInfo.SETL_METHOD_NM +"]";
					}
					if(data.schdInfo.DELI_CNCL_YN == 'Y'){
						periInfoHtml += "		<strong id='totPrice' class='n-black'>"+ numberWithCommas(payAmt) +"원</strong></span>"; //수정 By. 신성훈
					}else{
						periInfoHtml += "		<strong id='totPrice' class='n-black'>"+ numberWithCommas(payAmt) +"원</strong>("+ data.schdInfo.PAY_DATE +" / 오전 10시 결제 예정)</span>"; //수정 By. 신성훈
					}
					periInfoHtml += "	</div>";
					periInfoHtml += "</div>";
					periInfoHtml += "<div class='wrap-set-complex-align gap-mid-pt2'><div class='set-complex-align'>";
					periInfoHtml += "	<div class='a-left'>";
					periInfoHtml += "		<i class='icon-noticinfo'>알림</i><span class='txt-wbrown desc'>상품 가격은 현재 구매 가능 금액이며, 정기배송 자동결제 시점에는 상품 금액이 변동될 수 있으니 쇼핑에 참고해 주세요</span>";
					periInfoHtml += "	</div></div>";
					periInfoHtml += "</div>				";
				}

				$("#periInfo").html(periInfoHtml);

				var attentionHtml = "";

				if(data.schdInfo.ORDER_ID != null){

					attentionHtml +="<li>정기배송 상품은 자동결제 시점 기준 가격으로 결제금액이 변동될 수 있습니다.</li>";
					attentionHtml +="<li>매장배송의 경우, 주문시 선택하신 시간에 배송이 진행되며, 도로사정이나 악천후 등으로 배송이 지연될 수 있습니다. 이점 양해바랍니다.</li>";
					attentionHtml +="<li>업체 택배상품을 주문하신 경우 상품별로 배송될 수 있습니다.</li>";
					$("#attention").html(attentionHtml);

					$("#itemAdd").hide();
					 jQuery.ajax({
						    type: "POST",
							url:"/mymart/selectPeriOrderDetail.do",
							dataType: "html",
							data: "orderId="+data.schdInfo.ORDER_ID,
							async:false,
							cache: false,
							success: function(html){
								$("#orderListDiv").hide();
								$("#orderListDiv").empty().append(html);

								initOrderDetail();

								$("#orderListDiv").show();
							},
							error: function(xhr,  Status, error) {
								//alert('sendRequest error : ' + xhr.status + " ( " + error + " ) " );
							},
							complete: function(xhr, textStatus) {
								//alert('complete! status : ' + xhr.status);
							}
						});

					 	 var prodList = data.prodList;
                         var resultObject = data.resultObject; //할인정보 가져오기 // By 신성훈
                         var ListDisCount = data.ListDisCount; //할인정보 가져오기 // By 신성훈
                         var discountAmt1 = 0                ; //할인정보 가져오기 // By 신성훈
					 	 var cCnt = 0;
					 	 var cancelItemListHtml = "";
						 for(var i=0; i<prodList.length; i++){
							 if(prodList[i].ITEM_CNCL_YN == 'Y'){
								cCnt++;
								if(prodList[i].SOUT_YN == 'Y'){
									totSellPrcA = totSellPrcA + 0;
								}else{
									totSellPrcA = totSellPrcA + prodList[i].TOT_PRC;
								}

								discntAmt =  prodList[i].DISCNT_AMT;
								if(discntAmt == undefined){
									discntAmt = 0;
								}
								if(prodList[i].SOUT_YN == 'Y'){
									totCurrSellPrcA = totCurrSellPrcA + 0 - (discntAmt * prodList[i].ORD_QTY);
									totProductDcA = totSellPrcA - totCurrSellPrcA;
								}else{
									totCurrSellPrcA = totCurrSellPrcA + prodList[i].TOT_CURR_PRC - (discntAmt * prodList[i].ORD_QTY);
									totProductDcA = totSellPrcA - totCurrSellPrcA;
								}

								cancelItemListHtml += "<li>";

                                //---- li 태그 밖에서  li 태그 안으로 위치만 바꿈 -- 시작 --  By 신성훈
								cancelItemListHtml += "    <input type='hidden' id='prodCd_"         + prodList[i].PROD_CD + "' name='prodCd' value='"         + prodList[i].PROD_CD          + "' />                        ";
								cancelItemListHtml += "    <input type='hidden' id='itemCd_"         + prodList[i].PROD_CD + "' name='itemCd' value='"         + prodList[i].ITEM_CD          + "' />                        ";
								cancelItemListHtml += "    <input type='hidden' id='categoryId_"     + prodList[i].PROD_CD + "' name='categoryId' value='"     + prodList[i].CATEGORY_ID      + "' />            ";
								cancelItemListHtml += "    <input type='hidden' id='overseaYn_"      + prodList[i].PROD_CD + "' name='overseaYn' value='"      + prodList[i].OVERSEA_YN       + "' />               ";
								cancelItemListHtml += "    <input type='hidden' id='nfomlVariation_" + prodList[i].PROD_CD + "' name='nfomlVariation' value='" + prodList[i].NFOML_VARIATION  + "' /> ";
								cancelItemListHtml += "    <input type='hidden' id='minQty_"         + prodList[i].PROD_CD + "' name='minQty' value='"         + prodList[i].MIN_ORD_PSBT_QTY + "' />               ";
								cancelItemListHtml += "    <input type='hidden' id='maxQty_"         + prodList[i].PROD_CD + "' name='maxQty' value='"         + prodList[i].MAX_ORD_PSBT_QTY + "' />               ";
                                //---- li 태그 밖에서  li 태그 안으로 위치만 바꿈 -- 끝   --  By 신성훈

								cancelItemListHtml += "	<article>";
								//cancelItemListHtml += "		<span class='check-data'>";
								//cancelItemListHtml += "			<input type='checkbox' class='PERI_DELI_PROD_SEQ' id='checkbox_"+ prodList[i].PERI_DELI_PROD_SEQ+"_1' name='checkbox_"+ prodList[i].PERI_DELI_PROD_SEQ+"' value='' title='상품선택'>";
								//cancelItemListHtml += "		</span>";
								//cancelItemListHtml += "		<label for='prod-item1'></label>";
								cancelItemListHtml += "		<div class='wrap-thumb'>";

								if(prodList[i].SOUT_YN == 'Y'){
									cancelItemListHtml += "<span class='prod-tem-sold'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_tem_sold_208x208.png' alt='일시품절'></span>";
								}
								 if(prodList[i].ITEM_CNCL_YN == 'Y'){
									cancelItemListHtml += "<span class='prod-skip'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_skip_208x208.png' alt='이번만 취소'></span>";
									var tmpPrdCnt = $("#prd_cnt").text().replace("개","");
									$("#prd_cnt").text(tmpPrdCnt - 1 + "개")
								}
								cancelItemListHtml += "			<a href='#' onclick='goProductDetailView(\""+prodList[i].CATEGORY_ID+"\",\""+prodList[i].PROD_CD+"\")'><img src='"+_LMCdnDynamicUrl+"/" + prodList[i].MD_SRCMK_CD.substring(0,5) +"/"+ prodList[i].MD_SRCMK_CD +"_1_80.jpg' onerror='javascript:showNoImage(this,80,80)' alt='"+ prodList[i].PROD_NM + "' class='thumb'></a>";
								cancelItemListHtml += "		</div>";
								cancelItemListHtml += "		<p class='prod-bene'>";
								if(prodList[i].DELI_TYPE.substring(0,2) == '01'){
									cancelItemListHtml += "<i class='icon-band-delivery1'>매장배송</i>";
								}else if(prodList[i].DELI_TYPE.substring(0,2) == '02'){
									cancelItemListHtml += "<i class='icon-band-delivery2'>매장택배</i>";
								}else if(prodList[i].DELI_TYPE.substring(0,2) == '04'){
									cancelItemListHtml += "<i class='icon-band-delivery4'>업체택배</i>";
								}else if(prodList[i].DELI_TYPE.substring(0,2) == '06'){
									cancelItemListHtml += "<i class='icon-band-delivery5'>명절배송</i>";
								}else if(prodList[i].DELI_TYPE.substring(0,2) == '07'){
									cancelItemListHtml += "<i class='icon-band-delivery3'>매장픽업</i>";
								}
								if(prodList[i].ICON_10 == '1'){
									cancelItemListHtml += "<i class='icon-tag-freeship'>무료배송</i>";
								}
								cancelItemListHtml += "		</p>";
								cancelItemListHtml += "		<div class='wrap-info clear-after'>";
								cancelItemListHtml += "			<strong class='prod-name'>"+ prodList[i].PROD_NM +"</strong>";

								if(prodList[i].ITEM_CNCL_YN != 'Y'){
									cancelItemListHtml += "			<ul class='prod-item-detail'>";
									if(prodList[i].OPTION_NM != null){
										cancelItemListHtml += "				<li>옵션 : "+ prodList[i].OPTION_NM +"</li>";
									}
									cancelItemListHtml += "			</ul>";
								}

								cancelItemListHtml += "			<div class='prod-num'>";
								if(data.schdInfo.DELI_CNCL_YN == 'Y' || prodList[i].SELL_PSBT_YN == 'N' || prodList[i].SOUT_YN == 'Y' || prodList[i].ITEM_CNCL_YN == 'Y' ){ // 수정 By 신성훈 -- prodList[i].SELL_PSBT_YN == 'Y'
									cancelItemListHtml += "				<p class='set-spinner-type1-large' disabled='disabled'>";
									cancelItemListHtml += "					<input type='text' class='ORD_QTY' id='ordQty' name='ordQty"+prodList[i].PROD_CD+"' value='"+ prodList[i].ORD_QTY +"' maxlength='3' title='수량' disabled='disabled'>";
									cancelItemListHtml += "					<span class='set-btn'>";
									cancelItemListHtml += "						<button type='button' class='up' MAX_ORD_PSBT_QTY='"+ prodList[i].MAX_ORD_PSBT_QTY +"' disabled='disabled'>수량 증가</button>";
									cancelItemListHtml += "						<button type='button' class='down' MIN_ORD_PSBT_QTY='"+ prodList[i].MIN_ORD_PSBT_QTY +"' disabled='disabled'>수량 감소</button>";
									cancelItemListHtml += "					</span>";
									cancelItemListHtml += "				</p>";
								}else{
									cancelItemListHtml += "				<p class='set-spinner-type1-large'>";
									cancelItemListHtml += "					<input type='text' class='ORD_QTY' id='ordQty"+prodList[i].PROD_CD+"' name='ordQty' value='"+ prodList[i].ORD_QTY +"' maxlength='3' data-max-qty='"+ prodList[i].MAX_ORD_PSBT_QTY +"' data-min-qty='"+ prodList[i].MIN_ORD_PSBT_QTY +"' style='ime-mode:disabled' onKeyPress='return isOnlyNumberInput(event, false);' onblur='qtyValChk(\""+prodList[i].PROD_CD+"\",this.value);' title='수량'>";
									cancelItemListHtml += "					<span class='set-btn'>";
									cancelItemListHtml += "						<button type='button' class='up' MAX_ORD_PSBT_QTY='"+ prodList[i].MAX_ORD_PSBT_QTY +"'>수량 증가</button>";
									cancelItemListHtml += "						<button type='button' class='down' MIN_ORD_PSBT_QTY='"+ prodList[i].MIN_ORD_PSBT_QTY +"'>수량 감소</button>";
									cancelItemListHtml += "					</span>";
									cancelItemListHtml += "				</p>";
								}
								cancelItemListHtml += "				<p class='prod-price'>";
								if(data.schdInfo.DELI_CNCL_YN == 'Y' || prodList[i].SELL_PSBT_YN == 'N' || prodList[i].SOUT_YN == 'Y' || prodList[i].ITEM_CNCL_YN == 'Y' ) { // 수정 By 신성훈 -- prodList[i].SELL_PSBT_YN == 'Y'
									cancelItemListHtml += "					<strong class='txt-red a-right'>0원</strong>";
								}else{
                                    // --------- 할인금액 작성하기 - 시작 ------------- By. 신성훈
                                    discountAmt1 = 0;
                                    if (ListDisCount) {
                                        for (var k=0; k < ListDisCount.length; k++) {
                                            if (ListDisCount[k].PROD_CD == prodList[i].PROD_CD && ListDisCount[k].ITEM_CD == prodList[i].ITEM_CD) {
                                                discountAmt1 = ListDisCount[k].UNIT_DISCOUNT_AMT ;
                                            }
                                        }
                                    } else {
                                    	discountAmt1 = 0;
                                    }
									//--------- 할인금액 작성하기 - 끝 ------------- By. 신성훈

									cancelItemListHtml += "					<strong class='txt-red a-right'>"+ numberWithCommas((prodList[i].CURR_SELL_PRC * prodList[i].ORD_QTY) - (discntAmt * prodList[i].ORD_QTY) - discountAmt1) +"원</strong>"; //수정 By. 신성훈
								}

								//--------- 할인정보 보여주기 여부 -------------- //추가 By.신성훈
								var layerPopupDisplay1 = "N";
                                if (resultObject && resultObject.Product && resultObject.Product.length > 0) {
									for (var j=0; j < resultObject.Product.length; j++) {
										if (resultObject.Product[j].PROD_CD == prodList[i].PROD_CD && resultObject.Product[j].ITEM_CD == prodList[i].ITEM_CD) {
											layerPopupDisplay1 = "Y";
										}
									}
                                } else {
                                	layerPopupDisplay1 = "N";
                                }
								//--------- 할인정보 보여주기 여부  - 끝 -------------- //추가 By.신성훈

								if (layerPopupDisplay1 == "Y") { // ------- 수정 -- 시작 -- By. 신성훈
									cancelItemListHtml += "					<a class='category-unroll layerpop-trigger2' data-trigger='layerpop-dclist_"+prodList[i].PROD_CD + "' id='openDC1_" + prodList[i].PROD_CD + "' onclick='layerpopTriggerBtn(this,event);return false;' title='레이어팝업 열림'><span class='hidden'>레이어팝업 열기</span></a>";   //By 신성훈
									cancelItemListHtml += "<script type='text/template' data-attach='layerpop-dclist_"+prodList[i].PROD_CD+"'>";
									cancelItemListHtml += "	<section class='layerpop-type2 laypopup-locate-type6 layerpop-target layer-bottom'>";
									cancelItemListHtml += "		<div class='container'>";
									cancelItemListHtml += "			<h3 class='tit-h'>할인 내역</h3>";
									cancelItemListHtml += "				<div class='contents'>";
									cancelItemListHtml += "					<ul class='bul-circle' id='dcList'>";

									//--- 수정 시작 ---  By.신성훈
									if (resultObject && resultObject.Product && resultObject.Product.length > 0) {  //if(prodList[i].DC_DETAIL.length > 0){
										for (var j=0; j < resultObject.Product.length; j++) {
											if (resultObject.Product[j].PROD_CD == prodList[i].PROD_CD && resultObject.Product[j].ITEM_CD == prodList[i].ITEM_CD) { //추가 By. 신성훈
												var dcName = "";
												var dcAmt  = 0;

												if (resultObject.Product[j].PROMO_KIND_CD == "02") {
													if (resultObject.Product[j].PROMO_CL_TYP_CD == '04') {
														dcName = "즉시상품할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '10') {
														dcName = "온라인상품할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '05') {
														dcName = "상품DM 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '06') {
														dcName = "N 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '08') {
														dcName = "고객상품할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '11') {
														dcName = "ONE 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '12') {
														dcName = "다둥이클럽할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '29') {
														dcName = "정기배송상품할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '09') {
														dcName = "카드상품할인";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '21') {
														dcName = "M쿠폰상품할인";
													}
												}
												if (resultObject.Product[j].PROMO_KIND_CD == '01') {
												    if (resultObject.Product[j].PROMO_CL_TYP_CD == '01') {
												    	dcName = "살수록더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '02') {
														dcName = "함께더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '07') {
														dcName = "L.POINT 할인";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '14') {
														dcName = "살수록더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '17') {
														dcName = "다둥이 살수록더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '18') {
														dcName = "다둥이 살수록더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '25') {
														dcName = "온라인상품할인";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '26') {
														dcName = "기타상품할인";
													}
												}

												//if (resultObject.Product[j].DC_UNIT_CD == '01' || resultObject.Product[j].DC_UNIT_CD == '03') {
													dcAmt = parseInt(nvl(resultObject.Product[j].EVNT_APPLY_AMT,0)) * parseInt(nvl(resultObject.Product[j].EVNT_APPLY_QTY,0));
												//} else if (resultObject.Product[j].DC_UNIT_CD == '02') {
												//	dcAmt = resultObject.Product[j].DC_EVT_CONTENT;
												//}

												if (dcName != "") {
													//if (resultObject.Product[j].DC_UNIT_CD == '01' || resultObject.Product[j].DC_UNIT_CD == '03') {
														cancelItemListHtml += "<li>" + dcName  + "<em class='txt-red flt-right'>" + numberWithCommas(dcAmt) + "원</em></li>";
													//} else if (resultObject.Product[j].DC_UNIT_CD == '02') {
													//	cancelItemListHtml += "<li>" + dcName + "<em class='txt-red flt-right'>" + dcAmt + "%</em></li>";
													//}
												}
											}
										}
									}
									//--- 수정 끝 ---  By.신성훈

                                    //---- 임직원 할인 추가 -- 시작 ----------- By.신성훈
                                    if (resultObject && resultObject.Staff) {
	                                    if (resultObject.Staff.length > 0) {
	                                        for (var j=0; j < resultObject.Staff.length; j++) {
	                                            if (resultObject.Staff[j].PROD_CD == prodList[i].PROD_CD && resultObject.Staff[j].ITEM_CD == prodList[i].ITEM_CD) {
	                                            	cancelItemListHtml += "<li>임직원할인<em class='txt-red flt-right'>" + numberWithCommas( (1 * resultObject.Staff[j].EVNT_APPLY_AMT) * (1 * resultObject.Staff[j].EVNT_APPLY_QTY)) + "원</em></li>";
	                                            }
	                                        }
	                                    }
                                    }
                                    //---- 임직원 할인 추가 -- 끝 ----------- By.신성훈

									cancelItemListHtml += "					</ul>";
									cancelItemListHtml += "				</div>";
									cancelItemListHtml += "			</div>";
									cancelItemListHtml += "		<a href='javascript:;' class='btn-ico-close' title='레이어팝업 닫기'><i>할인 내역</i></a>";
									cancelItemListHtml += "	</section>";
									cancelItemListHtml += "</script>";
								}
								cancelItemListHtml += "				</p>";
								cancelItemListHtml += "			</div>";
								if(data.schdInfo.DELI_CNCL_YN != 'Y'){
									if(prodList[i].ITEM_CNCL_YN == 'Y'){
									}else{
										if(prodList[i].OPTION_YN != 'Y'){
											cancelItemListHtml += "      <button title='변경' class='btn-form layerpop-trigger2' id='managePeri_" + "cancel" + "_" + prodList[i].PROD_CD + "' onclick='managePeriCart.add(this);' type='button'  data-category-id='" + prodList[i].CATEGORY_ID+ "' data-prod-cd='" + prodList[i].PROD_CD + "' data-prod-type-cd='"+ prodList[i].ONLINE_PROD_TYPE_CD +"' data-option-yn='"+prodList[i].OPTION_YN+"' data-gubun='"+prodList[i].GUBUN+"' data-item-cd='"+prodList[i].ITEM_CD+"'  data-str-cd='" + prodList[i].STR_CD+ "'  data-zip-seq='" + prodList[i].RECP_ZIP_SEQ+ "'  data-peri-deli-prod-seq='" + prodList[i].PERI_DELI_PROD_SEQ + "'  data-min-qty='" + prodList[i].MIN_ORD_PSBT_QTY + "' data-max-qty='" + prodList[i].MAX_ORD_PSBT_QTY + "' data-btn-obj-id='managePeri_" + "cancel" + "_" + prodList[i].PROD_CD + "' data-call-back-func='addPeriBasketCallback'>변경</button>";
										}else{
											cancelItemListHtml += "      <button title='변경' class='btn-form layerpop-trigger2' id='managePeri_" + "cancel" + "_" + prodList[i].PROD_CD + "' onclick='managePeriCart.add(this);' type='button'  data-category-id='" + prodList[i].CATEGORY_ID+ "' data-prod-cd='" + prodList[i].PROD_CD + "' data-prod-type-cd='"+ prodList[i].ONLINE_PROD_TYPE_CD +"' data-option-yn='"+prodList[i].OPTION_YN+"' data-gubun='"+prodList[i].GUBUN+"' data-item-cd='"+prodList[i].ITEM_CD+"'  data-str-cd='" + prodList[i].STR_CD+ "'  data-zip-seq='" + prodList[i].RECP_ZIP_SEQ+ "'  data-peri-deli-prod-seq='" + prodList[i].PERI_DELI_PROD_SEQ + "'  data-min-qty='" + prodList[i].MIN_ORD_PSBT_QTY + "' data-max-qty='" + prodList[i].MAX_ORD_PSBT_QTY + "' data-btn-obj-id='managePeri_" + "cancel" + "_" + prodList[i].PROD_CD + "' data-call-back-func='addPeriBasketCallback' disabled>변경</button>";
										}

										cancelItemListHtml += "		<button type='button' class='btn-form-type2' onclick='deleteChkItem(\""+ prodList[i].PERI_DELI_PROD_SEQ +"\");'>삭제</button>";
									}
								}
								cancelItemListHtml += "		</div>";
								cancelItemListHtml += "	</article>";
								cancelItemListHtml += "</li>";
							}
						 }
						 $("#cancelItemList").html(cancelItemListHtml);
						 if(cancelItemListHtml != ""){
							 $("#CprodCnt").text(cCnt);
							 $("#cancelItemBox").removeClass("hide");
						 }else{
							 $("#cancelItemBox").addClass("hide");
						 }
				}else{
					$("#orderListDiv").empty();

					attentionHtml +="<li>정기배송 장바구니에 담긴 매장상품 결제예정 금액이 30,000원 이상인 경우 배송비 무료, 30,000원 미만인 경우 배송비 2,500 부과됩니다. (일부 초근거리 지역에 계신 분들께는 배송비 추가혜택이 있을 수 있습니다.)<br>단, 택배상품의 배송비는 배송업체마다 적용 기준이 다르며, 동일업체일 경우 묶음배송으로 1회만 부가됩니다.</li>";
					attentionHtml +="<li>정기배송 상품은 자동결제 시점 기준 가격으로 결제금액이 변동될 수 있습니다.</li>";
					attentionHtml +="<li>정기배송 정보 수정 및 취소는 마이롯데 &gt; 정기배송관리에서 변경 가능합니다.</li>";
					attentionHtml +="<li>카드 분실 및 한도초과, 통신 지연 등으로 최초 자동결제(배송일 하루 전 오전 10시)가 실패된 경우, 고객센터에서 전화를 드리며, 2차 자동 결제 시점(13시)까지 재결제가 일어나지 않는 경우, 자동취소 처리됩니다.</li>";
					attentionHtml +="<li>자세한 사항은 1577-2500번 고객센터로 문의주세요.</li>";
					$("#attention").html(attentionHtml);

					var discntAmt = 0;
					var sumTotPrice = 0;
					var sumTotDcPrice = 0;
					var sumTotDlvPrice = 0;


					var prodList = data.prodList;
                    var resultObject   = data.resultObject; //할인정보 가져오기 // By 신성훈
                    var ListDisCount   = data.ListDisCount; //할인정보 가져오기 // By 신성훈
                    var discountAmt2   = 0; //할인금액 By 신성훈
                    var discountAmt3   = 0; //할인금액 By 신성훈
                    var discountAmt4   = 0; //할인금액 By 신성훈
                    var discountAmt5   = 0; //할인금액 By 신성훈
					var storeProdListHtml = ""; 				// 매장 배송 상품
					var onlineProdListHtml = "";				// 업체 택배 상품
					var outrangeProdListHtml = "";			// 매장 택배 상품(특정점)
					var addItemListHtml = "";				    // 이번만 추가 상품

					if(prodList.length > 0){
						var nCnt = 0;
						var totSellPrcN = 0;
						var totCurrSellPrcN = 0;
						var totProductDcN = 0;
						var totDlvPrcN = 0;
						var deliCost01 = data.deliCost01;
						if(deliCost01 == undefined){
							deliCost01 = 0;
						}
						var totDlvPrcN = totDlvPrcN + Number(deliCost01);

						var vCnt = 0;
						var totSellPrcV = 0;
						var totCurrSellPrcV = 0;
						var totProductDcV = 0;
						var totDlvPrcV = 0;
						var deliCost04 = data.deliCost04;
						if(deliCost04 == undefined){
							deliCost04 = 0;
						}
						var totDlvPrcV = totDlvPrcV + Number(deliCost04);

						var mCnt = 0;
						var totSellPrcM = 0;
						var totCurrSellPrcM = 0;
						var totProductDcM = 0;
						var totDlvPrcM = 0;
						var deliCost02 = data.deliCost02;
						if(deliCost02 == undefined){
							deliCost02 = 0;
						}
						var totDlvPrcM = totDlvPrcM + Number(deliCost02);

						var aCnt = 0;
						var totSellPrcA = 0;
						var totCurrSellPrcA = 0;
						var totProductDcA = 0;
						var totDlvPrcA = 0;
						//var totDlvPrcA = totDlvPrcA + Number(data.deliCost02);

						for(var i=0; i<prodList.length; i++){
							// ----------------------------------------------------------------------
							// -------------------- 매장 배송 상품 ----------------------------------
							// ----------------------------------------------------------------------
							if(prodList[i].BASKET_TYPE == '11' && prodList[i].ITEM_OTA_YN != 'Y'){
								nCnt++;
								if(prodList[i].ITEM_CNCL_YN != 'Y'){
									totSellPrcN = totSellPrcN + prodList[i].TOT_PRC;
									discntAmt =  prodList[i].DISCNT_AMT;
									if(discntAmt == undefined){
										discntAmt = 0;
									}
									totCurrSellPrcN = totCurrSellPrcN + prodList[i].TOT_CURR_PRC - (discntAmt * prodList[i].ORD_QTY);
									totProductDcN = totSellPrcN - totCurrSellPrcN;
								}

								storeProdListHtml += "<li>";

                                //---- li 태그 밖에서  li 태그 안으로 위치만 바꿈 -- 시작 --  By 신성훈
								storeProdListHtml += "    <input type='hidden' id='prodCd_"         + prodList[i].PROD_CD + "' name='prodCd' value='"         + prodList[i].PROD_CD          + "' />                        ";
								storeProdListHtml += "    <input type='hidden' id='itemCd_"         + prodList[i].PROD_CD + "' name='itemCd' value='"         + prodList[i].ITEM_CD          + "' />                        ";
								storeProdListHtml += "    <input type='hidden' id='categoryId_"     + prodList[i].PROD_CD + "' name='categoryId' value='"     + prodList[i].CATEGORY_ID      + "' />            ";
								storeProdListHtml += "    <input type='hidden' id='overseaYn_"      + prodList[i].PROD_CD + "' name='overseaYn' value='"      + prodList[i].OVERSEA_YN       + "' />               ";
								storeProdListHtml += "    <input type='hidden' id='nfomlVariation_" + prodList[i].PROD_CD + "' name='nfomlVariation' value='" + prodList[i].NFOML_VARIATION  + "' /> ";
								storeProdListHtml += "    <input type='hidden' id='minQty_"         + prodList[i].PROD_CD + "' name='minQty' value='"         + prodList[i].MIN_ORD_PSBT_QTY + "' />               ";
								storeProdListHtml += "    <input type='hidden' id='maxQty_"         + prodList[i].PROD_CD + "' name='maxQty' value='"         + prodList[i].MAX_ORD_PSBT_QTY + "' />               ";
                                //---- li 태그 밖에서  li 태그 안으로 위치만 바꿈 -- 끝 --  By 신성훈

								storeProdListHtml += "	<article>";
								storeProdListHtml += "		<div class='wrap-thumb'>";

								if(prodList[i].SELL_PSBT_YN == 'N' || prodList[i].PERI_DELI_YN == 'N'){
									storeProdListHtml += "<span class='prod-nofixe-del'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_nofixe_del_208x208.png' alt='정기배송 불가'></span>";
								}
								if(prodList[i].SOUT_YN == 'Y'){
									storeProdListHtml += "<span class='prod-tem-sold'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_tem_sold_208x208.png' alt='일시품절'></span>";
								}
								if(data.schdInfo.DELI_CNCL_YN != 'Y' && prodList[i].ITEM_CNCL_YN == 'Y'){
									storeProdListHtml += "<span class='prod-skip'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_skip_208x208.png' alt='이번만 취소'></span>";
									var tmpPrdCnt = $("#prd_cnt").text().replace("개","");
									$("#prd_cnt").text(tmpPrdCnt - 1 + "개")
								}
								if(data.schdInfo.DELI_CNCL_YN == 'Y'){
									storeProdListHtml += "<span class='prod-skip'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_skip_208x208.png' alt='이번만 취소'></span>";
									var tmpPrdCnt = $("#prd_cnt").text().replace("개","");
									$("#prd_cnt").text(tmpPrdCnt - 1 + "개")
								}
								storeProdListHtml += "			<a href='javascript:;' onclick='goProductDetailView(\""+prodList[i].CATEGORY_ID+"\",\""+prodList[i].PROD_CD+"\")'><img src='"+_LMCdnDynamicUrl+"/"+ prodList[i].MD_SRCMK_CD.substring(0,5) +"/"+ prodList[i].MD_SRCMK_CD +"_1_80.jpg' onerror='javascript:showNoImage(this,80,80)' alt='"+ prodList[i].PROD_NM + "' class='thumb'></a>";
								storeProdListHtml += "		</div>";
								storeProdListHtml += "		<div class='wrap-info'>";
								storeProdListHtml += "			<a href='javascript:;' onclick='goProductDetailView(\""+prodList[i].CATEGORY_ID+"\",\""+prodList[i].PROD_CD+"\")'><strong class='prod-name'>"+ prodList[i].PROD_NM +"</strong></a>";


								if(prodList[i].ITEM_CNCL_YN != 'Y'){
									storeProdListHtml += "			<ul class='prod-item-detail'>";
									if(prodList[i].OPTION_NM != null){
										storeProdListHtml += "				<li>옵션 : "+ prodList[i].OPTION_NM +"</li>";
									}

									if (prodList[i].PEST_TYPE_NM != null) {
										var strArray = prodList[i].PEST_TYPE_NM.split('SERVICE_FRONT');
										storeProdListHtml += "				<li>증정품: " + strArray[1] + "</li>";
									}
									storeProdListHtml += "			</ul>";
								}




								storeProdListHtml += "			<div class='prod-num'>";
								if(data.schdInfo.DELI_CNCL_YN == 'Y' || prodList[i].SELL_PSBT_YN == 'N' || prodList[i].SOUT_YN == 'Y' || prodList[i].ITEM_CNCL_YN == 'Y' ){ // 수정 By 신성훈 -- prodList[i].SELL_PSBT_YN == 'Y'
									storeProdListHtml += "				<p class='set-spinner-type1-large' disabled='disabled'>";
									storeProdListHtml += "					<input type='text' class='ORD_QTY' id='ordQty"+prodList[i].PROD_CD+"' name='ordQty' value='"+ prodList[i].ORD_QTY +"' maxlength='3' title='수량' disabled='disabled'>";
									storeProdListHtml += "					<span class='set-btn'>";
									storeProdListHtml += "						<button type='button' class='up' MAX_ORD_PSBT_QTY='"+ prodList[i].MAX_ORD_PSBT_QTY +"' disabled='disabled'>수량 증가</button>";
									storeProdListHtml += "						<button type='button' class='down' MIN_ORD_PSBT_QTY='"+ prodList[i].MIN_ORD_PSBT_QTY +"' disabled='disabled'>수량 감소</button>";
									storeProdListHtml += "					</span>";
									storeProdListHtml += "				</p>";
								}else{
									storeProdListHtml += "				<p class='set-spinner-type1-large'>";
									storeProdListHtml += "					<input type='text' class='ORD_QTY' id='ordQty"+prodList[i].PROD_CD+"' name='ordQty' value='"+ prodList[i].ORD_QTY +"' maxlength='3' data-max-qty='"+ prodList[i].MAX_ORD_PSBT_QTY +"' data-min-qty='"+ prodList[i].MIN_ORD_PSBT_QTY +"' style='ime-mode:disabled' onKeyPress='return isOnlyNumberInput(event, false);' onblur='qtyValChk(\""+prodList[i].PROD_CD+"\",this.value);' title='수량'>";
									storeProdListHtml += "					<span class='set-btn'>";
									storeProdListHtml += "						<button type='button' class='up' MAX_ORD_PSBT_QTY='"+ prodList[i].MAX_ORD_PSBT_QTY +"'>수량 증가</button>";
									storeProdListHtml += "						<button type='button' class='down' MIN_ORD_PSBT_QTY='"+ prodList[i].MIN_ORD_PSBT_QTY +"'>수량 감소</button>";
									storeProdListHtml += "					</span>";
									storeProdListHtml += "				</p>";
								}
								storeProdListHtml += "				<p class='prod-price'>";

								if(data.schdInfo.DELI_CNCL_YN == 'Y' || prodList[i].SELL_PSBT_YN == 'N' || prodList[i].SOUT_YN == 'Y' || prodList[i].ITEM_CNCL_YN == 'Y' ){ // 수정 By 신성훈 -- prodList[i].SELL_PSBT_YN == 'Y'
									storeProdListHtml += "					<strong class='txt-red a-right'>0원</strong>";
								}else{
	                                //--------------- 종류별 판매가 소계를 계산하기 -- 시작 -------- By 신성훈 -----------
	                                unitTotalAmt2 = unitTotalAmt2 + (prodList[i].CURR_SELL_PRC * prodList[i].ORD_QTY) - (discntAmt * prodList[i].ORD_QTY) ;
	                                //--------------- 종류별 판매가 소계를 계산하기 -- 끝   -------- By 신성훈 -----------

	                                //------ 할인금액 작성하기 -- 시작 --  By. 신성훈
									discountAmt2 = 0;

                                    if (ListDisCount) {
                                        for (var k=0; k < ListDisCount.length; k++) {
                                            if (ListDisCount[k].PROD_CD == prodList[i].PROD_CD && ListDisCount[k].ITEM_CD == prodList[i].ITEM_CD) {
                                            	discountAmt2 = ListDisCount[k].UNIT_DISCOUNT_AMT ;
                                            	unitTotalDiscount2 = unitTotalDiscount2 + discountAmt2;
                                            }
                                        }
                                    } else {
                                    	discountAmt2 = 0;
                                    }
                                    //------ 할인금액 작성하기 -- 끝 --  By. 신성훈

									storeProdListHtml += "					<strong class='txt-red a-right'>"+ numberWithCommas((prodList[i].CURR_SELL_PRC * prodList[i].ORD_QTY) - (discntAmt * prodList[i].ORD_QTY) - discountAmt2) +"원</strong>";  // By 신성훈
								}

								//--- 할인정보 보여주기 여부 -- 시작 -- By. 신성훈
								var layerPopupDisplay2 = "N";
								if (resultObject && resultObject.Product && resultObject.Product.length > 0) {
									for (var j=0; j < resultObject.Product.length; j++) {
										if (resultObject.Product[j].PROD_CD == prodList[i].PROD_CD && resultObject.Product[j].ITEM_CD == prodList[i].ITEM_CD) {
											layerPopupDisplay2 = "Y";
										}
									}
								} else {
									layerPopupDisplay2 = "N";
								}
								//--- 할인정보 보여주기 여부 -- 끝 -- By. 신성훈

								if (layerPopupDisplay2 == "Y") {  // ------- 수정 -- 시작 -- By. 신성훈
									storeProdListHtml += "					<a class='category-unroll layerpop-trigger2' data-trigger='layerpop-dclist_"+prodList[i].PROD_CD + "' id='openDC2_" + prodList[i].PROD_CD + "' onclick='layerpopTriggerBtn(this,event);return false;' title='레이어팝업 열림'><span class='hidden'>레이어팝업 열기</span></a>";  //수정 By. 신성훈
									storeProdListHtml += "<script type='text/template' data-attach='layerpop-dclist_"+prodList[i].PROD_CD+"'>";
									storeProdListHtml += "	<section class='layerpop-type2 laypopup-locate-type6 layerpop-target layer-bottom'>";
									storeProdListHtml += "		<div class='container'>";
									storeProdListHtml += "			<h3 class='tit-h'>할인 내역</h3>";
									storeProdListHtml += "				<div class='contents'>";
									storeProdListHtml += "					<ul class='bul-circle' id='dcList'>";

									//----------- 수정 시작 -- By 신성훈 ------------
									if (resultObject && resultObject.Product && resultObject.Product.length > 0) {
										for (var j=0; j < resultObject.Product.length; j++) {
											if (resultObject.Product[j].PROD_CD == prodList[i].PROD_CD && resultObject.Product[j].ITEM_CD == prodList[i].ITEM_CD) { //추가 By. 신성훈
												var dcName = "";
												var dcAmt  = 0 ;

												if (resultObject.Product[j].PROMO_KIND_CD == "02") {
													if (resultObject.Product[j].PROMO_CL_TYP_CD == '04') {
														dcName = "즉시상품할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '10') {
														dcName = "온라인상품할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '05') {
														dcName = "상품DM 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '06') {
														dcName = "N 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '08') {
														dcName = "고객상품할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '11') {
														dcName = "ONE 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '12') {
														dcName = "다둥이클럽할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '29') {
														dcName = "정기배송상품할인 쿠폰";
                                                    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '09') {
                                                        dcName = "카드상품할인";
                                                    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '21') {
                                                        dcName = "M쿠폰상품할인";
                                                    }
												}
												if (resultObject.Product[j].PROMO_KIND_CD == '01') {
												    if (resultObject.Product[j].PROMO_CL_TYP_CD == '01') {
												    	dcName = "살수록더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '02') {
														dcName = "함께더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '07') {
														dcName = "L.POINT 할인";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '14') {
														dcName = "살수록더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '17') {
														dcName = "다둥이 살수록더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '18') {
														dcName = "다둥이 살수록더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '25') {
														dcName = "온라인상품할인";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '26') {
														dcName = "기타상품할인";
													}
												}

												//if (resultObject.Product[j].DC_UNIT_CD == '01' || resultObject.Product[j].DC_UNIT_CD == '03') {
													dcAmt = parseInt(nvl(resultObject.Product[j].EVNT_APPLY_AMT, 0)) * parseInt(nvl(resultObject.Product[j].EVNT_APPLY_QTY, 0));
												//} else if (resultObject.Product[j].DC_UNIT_CD == '02') {
												//	dcAmt = resultObject.Product[j].DC_EVT_CONTENT;
												//}

												if (dcName != "") {
													//if (resultObject.Product[j].DC_UNIT_CD == '01' || resultObject.Product[j].DC_UNIT_CD == '03') {
														storeProdListHtml += "<li>" + dcName + "<em class='txt-red flt-right'>" + numberWithCommas(dcAmt) +  "원</em></li>";
													//} else if (resultObject.Product[j].DC_UNIT_CD == '02') {
														//storeProdListHtml += "<li>" + dcName + "<em class='txt-red flt-right'>" + dcAmt + "%</em></li>";
													//}
												}
											}
										}
									}
									//----------- 수정 끝 -- By 신성훈 ------------

                                    //----------- 임직원 할인 -- 시작 --- By.신성훈
                                    if (resultObject && resultObject.Staff) {
	                                    if (resultObject.Staff.length > 0) {
	                                        for (var j=0; j < resultObject.Staff.length; j++) {
	                                            if (resultObject.Staff[j].PROD_CD == prodList[i].PROD_CD && resultObject.Staff[j].ITEM_CD == prodList[i].ITEM_CD) {
	                                                storeProdListHtml += "<li>임직원할인<em class='txt-red flt-right'>" + numberWithCommas( (1 * resultObject.Staff[j].EVNT_APPLY_AMT) * (1 * resultObject.Staff[j].EVNT_APPLY_QTY)) + "원</em></li>";
	                                            }
	                                        }
	                                    }
                                    }
                                    //----------- 임직원 할인 -- 끝 --- By.신성훈

									storeProdListHtml += "					</ul>";
									storeProdListHtml += "				</div>";
									storeProdListHtml += "			</div>";
									storeProdListHtml += "		<a href='javascript:;' class='btn-ico-close' title='레이어팝업 닫기'><i>할인 내역</i></a>";
									storeProdListHtml += "	</section>";
									storeProdListHtml += "</script>";

								}
								storeProdListHtml += "				</p>";
								storeProdListHtml += "			</div>";
								if(data.schdInfo.DELI_CNCL_YN != 'Y'){
									if(prodList[i].ITEM_CNCL_YN == 'Y'){
										storeProdListHtml += "			<button type='button' class='btn-form-type3' onclick='cancelItem(\""+prodList[i].PERI_DELI_PROD_SEQ+"\", \""+'N'+"\");' >배송받기</button>";
									}else{

										if(prodList[i].PERI_DELI_YN == 'N'){
										storeProdListHtml += "이 상품은 더 이상 정기배송이 불가능합니다";
										}else{
											storeProdListHtml += "			<button type='button' class='btn-form-type2' onclick='cancelItem(\""+prodList[i].PERI_DELI_PROD_SEQ+"\", \""+'Y'+"\");'>이번만 취소</button>";
										 //storeProdListHtml += "			<button type='button' class='btn-form layerpop-trigger2' data-trigger='layerpop-basket' onclick='setOption(this,\""+prodList[i].PERI_DELI_PROD_SEQ+"\",\""+prodList[i].PROD_CD+"\",\""+prodList[i].ITEM_CD+"\",\""+prodList[i].OPTION_YN+"\");' title='레이어팝업 열림'>변경</button>";
											if(prodList[i].OPTION_YN != 'Y'){
											storeProdListHtml += "          <button title='변경' class='btn-form layerpop-trigger2' id='managePeri_" + "store" + "_" + prodList[i].PROD_CD + "' onclick='managePeriCart.add(this);' type='button'  data-category-id='" + prodList[i].CATEGORY_ID+ "' data-prod-cd='" + prodList[i].PROD_CD + "' data-prod-type-cd='"+ prodList[i].ONLINE_PROD_TYPE_CD +"' data-option-yn='"+prodList[i].OPTION_YN+"' data-gubun='"+prodList[i].GUBUN+"' data-item-cd='"+prodList[i].ITEM_CD+"'  data-str-cd='" + prodList[i].STR_CD+ "'  data-zip-seq='" + prodList[i].RECP_ZIP_SEQ+ "'  data-peri-deli-prod-seq='" + prodList[i].PERI_DELI_PROD_SEQ + "'  data-min-qty='" + prodList[i].MIN_ORD_PSBT_QTY + "' data-max-qty='" + prodList[i].MAX_ORD_PSBT_QTY + "' data-btn-obj-id='managePeri_" + "store" + "_" + prodList[i].PROD_CD + "' data-call-back-func='addPeriBasketCallback'>변경</button>";
											}else{
											storeProdListHtml += "          <button title='변경' class='btn-form layerpop-trigger2' id='managePeri_" + "store" + "_" + prodList[i].PROD_CD + "' onclick='managePeriCart.add(this);' type='button'  data-category-id='" + prodList[i].CATEGORY_ID+ "' data-prod-cd='" + prodList[i].PROD_CD + "' data-prod-type-cd='"+ prodList[i].ONLINE_PROD_TYPE_CD +"' data-option-yn='"+prodList[i].OPTION_YN+"' data-gubun='"+prodList[i].GUBUN+"' data-item-cd='"+prodList[i].ITEM_CD+"'  data-str-cd='" + prodList[i].STR_CD+ "'  data-zip-seq='" + prodList[i].RECP_ZIP_SEQ+ "'  data-peri-deli-prod-seq='" + prodList[i].PERI_DELI_PROD_SEQ + "'  data-min-qty='" + prodList[i].MIN_ORD_PSBT_QTY + "' data-max-qty='" + prodList[i].MAX_ORD_PSBT_QTY + "' data-btn-obj-id='managePeri_" + "store" + "_" + prodList[i].PROD_CD + "' data-call-back-func='addPeriBasketCallback' disabled>변경</button>";
											}
										}

									}
								}
								storeProdListHtml += "		</div>";
								storeProdListHtml += "	</article>";
								storeProdListHtml += "</li>";
							}

							//-----------------------------------------------------------------------------
							//--------------------- 업체 택배 상품 ----------------------------------------
							//-----------------------------------------------------------------------------
							if(prodList[i].BASKET_TYPE == '21' && prodList[i].ITEM_OTA_YN != 'Y'){
								vCnt++;
								if(prodList[i].ITEM_CNCL_YN != 'Y'){
									totSellPrcV = totSellPrcV + prodList[i].TOT_PRC;
									discntAmt =  prodList[i].DISCNT_AMT;
									if(discntAmt == undefined){
										discntAmt = 0;
									}
									totCurrSellPrcV = totCurrSellPrcV + prodList[i].TOT_CURR_PRC - (discntAmt * prodList[i].ORD_QTY);
									totProductDcV = totSellPrcV - totCurrSellPrcV;
								}

								onlineProdListHtml += "<li>";

								//---- li 태그 밖에서  li 태그 안으로 위치만 바꿈 -- 시작 --  By 신성훈
								onlineProdListHtml += "    <input type='hidden' id='prodCd_"         + prodList[i].PROD_CD + "' name='prodCd' value='"         + prodList[i].PROD_CD          + "' />                        ";
								onlineProdListHtml += "    <input type='hidden' id='itemCd_"         + prodList[i].PROD_CD + "' name='itemCd' value='"         + prodList[i].ITEM_CD          + "' />                        ";
								onlineProdListHtml += "    <input type='hidden' id='categoryId_"     + prodList[i].PROD_CD + "' name='categoryId' value='"     + prodList[i].CATEGORY_ID      + "' />            ";
								onlineProdListHtml += "    <input type='hidden' id='overseaYn_"      + prodList[i].PROD_CD + "' name='overseaYn' value='"      + prodList[i].OVERSEA_YN       + "' />               ";
								onlineProdListHtml += "    <input type='hidden' id='nfomlVariation_" + prodList[i].PROD_CD + "' name='nfomlVariation' value='" + prodList[i].NFOML_VARIATION  + "' /> ";
								onlineProdListHtml += "    <input type='hidden' id='minQty_"         + prodList[i].PROD_CD + "' name='minQty' value='"         + prodList[i].MIN_ORD_PSBT_QTY + "' />               ";
								onlineProdListHtml += "    <input type='hidden' id='maxQty_"         + prodList[i].PROD_CD + "' name='maxQty' value='"         + prodList[i].MAX_ORD_PSBT_QTY + "' />               ";
								//---- li 태그 밖에서  li 태그 안으로 위치만 바꿈 -- 끝 --  By 신성훈

								onlineProdListHtml += "	<article>";
								onlineProdListHtml += "		<div class='wrap-thumb'>";

								if(prodList[i].SELL_PSBT_YN == 'N'|| prodList[i].PERI_DELI_YN  == 'N'){
									onlineProdListHtml += "<span class='prod-nofixe-del'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_nofixe_del_208x208.png' alt='정기배송 불가'></span>";
								}
								if(prodList[i].SOUT_YN == 'Y'){
									onlineProdListHtml += "<span class='prod-tem-sold'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_tem_sold_208x208.png' alt='일시품절'></span>";
								}
								if(prodList[i].ITEM_CNCL_YN == 'Y'){
									onlineProdListHtml += "<span class='prod-skip'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_skip_208x208.png' alt='이번만 취소'></span>";
									var tmpPrdCnt = $("#prd_cnt").text().replace("개","");
									$("#prd_cnt").text(tmpPrdCnt - 1 + "개")
								}
								if(data.schdInfo.DELI_CNCL_YN == 'Y'){
									onlineProdListHtml += "<span class='prod-skip'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_skip_208x208.png' alt='이번만 취소'></span>";
									var tmpPrdCnt = $("#prd_cnt").text().replace("개","");
									$("#prd_cnt").text(tmpPrdCnt - 1 + "개")
								}
								onlineProdListHtml += "			<a href='javascript:;' onclick='goProductDetailView(\""+prodList[i].CATEGORY_ID+"\",\""+prodList[i].PROD_CD+"\")'><img src='"+_LMCdnDynamicUrl+"/" + prodList[i].MD_SRCMK_CD.substring(0,5) +"/"+ prodList[i].MD_SRCMK_CD +"_1_80.jpg' onerror='javascript:showNoImage(this,80,80)' alt='"+ prodList[i].PROD_NM + "' class='thumb'></a>";
								onlineProdListHtml += "		</div>";
								onlineProdListHtml += "		<div class='wrap-info'>";
								onlineProdListHtml += "			<a href='javascript:;' onclick='goProductDetailView(\""+prodList[i].CATEGORY_ID+"\",\""+prodList[i].PROD_CD+"\")'><strong class='prod-name'>"+ prodList[i].PROD_NM +"</strong></a>";

								if(prodList[i].ITEM_CNCL_YN != 'Y'){
									onlineProdListHtml += "			<ul class='prod-item-detail'>";
									if(prodList[i].OPTION_NM != null){
										onlineProdListHtml += "				<li>옵션 : "+ prodList[i].OPTION_NM +"</li>";
									}

									if (prodList[i].PEST_TYPE_NM != null) {
										var strArray = prodList[i].PEST_TYPE_NM.split('SERVICE_FRONT');
										onlineProdListHtml += "				<li>증정품: " + strArray[1] + "</li>";
									}

									onlineProdListHtml += "			</ul>";
								}

								onlineProdListHtml += "			<div class='prod-num'>";
								if(data.schdInfo.DELI_CNCL_YN == 'Y' || prodList[i].SELL_PSBT_YN == 'N' || prodList[i].SOUT_YN == 'Y' || prodList[i].ITEM_CNCL_YN == 'Y' ){ // 수정 By 신성훈 -- prodList[i].SELL_PSBT_YN == 'Y'
									onlineProdListHtml += "				<p class='set-spinner-type1-large' disabled='disabled'>";
									onlineProdListHtml += "					<input type='text' class='ORD_QTY' id='ordQty"+prodList[i].PROD_CD+"' name='ordQty' value='"+ prodList[i].ORD_QTY +"' maxlength='3' title='수량' disabled='disabled'>";
									onlineProdListHtml += "					<span class='set-btn'>";
									onlineProdListHtml += "						<button type='button' class='up' MAX_ORD_PSBT_QTY='"+ prodList[i].MAX_ORD_PSBT_QTY +"' disabled='disabled'>수량 증가</button>";
									onlineProdListHtml += "						<button type='button' class='down' MIN_ORD_PSBT_QTY='"+ prodList[i].MIN_ORD_PSBT_QTY +"' disabled='disabled'>수량 감소</button>";
									onlineProdListHtml += "					</span>";
									onlineProdListHtml += "				</p>";
								}else{
									onlineProdListHtml += "				<p class='set-spinner-type1-large'>";
									onlineProdListHtml += "					<input type='text' class='ORD_QTY' id='ordQty"+prodList[i].PROD_CD+"' name='ordQty' value='"+ prodList[i].ORD_QTY +"' maxlength='3' data-max-qty='"+ prodList[i].MAX_ORD_PSBT_QTY +"' data-min-qty='"+ prodList[i].MIN_ORD_PSBT_QTY +"' style='ime-mode:disabled' onKeyPress='return isOnlyNumberInput(event, false);' onblur='qtyValChk(\""+prodList[i].PROD_CD+"\",this.value);' title='수량'>";
									onlineProdListHtml += "					<span class='set-btn'>";
									onlineProdListHtml += "						<button type='button' class='up' MAX_ORD_PSBT_QTY='"+ prodList[i].MAX_ORD_PSBT_QTY +"'>수량 증가</button>";
									onlineProdListHtml += "						<button type='button' class='down' MIN_ORD_PSBT_QTY='"+ prodList[i].MIN_ORD_PSBT_QTY +"'>수량 감소</button>";
									onlineProdListHtml += "					</span>";
									onlineProdListHtml += "				</p>";
								}
								onlineProdListHtml += "				<p class='prod-price'>";
								if(data.schdInfo.DELI_CNCL_YN == 'Y' || prodList[i].SELL_PSBT_YN == 'N' || prodList[i].SOUT_YN == 'Y' || prodList[i].ITEM_CNCL_YN == 'Y' ){  // 수정 By 신성훈 -- prodList[i].SELL_PSBT_YN == 'Y'
									onlineProdListHtml += "					<strong class='txt-red a-right'>0원</strong>";
								}else{
	                                //--------------- 종류별 판매가 소계를 계산하기 -- 시작 -------- By 신성훈 -----------
	                                unitTotalAmt3 = unitTotalAmt3 + (prodList[i].CURR_SELL_PRC * prodList[i].ORD_QTY) - (discntAmt * prodList[i].ORD_QTY) ;
	                                //--------------- 종류별 판매가 소계를 계산하기 -- 끝   -------- By 신성훈 -----------

                                    // ------ 할인금액 작성하기 -- 시작 ---  By. 신성훈
                                    discountAmt3 = 0;

                                    if (ListDisCount) {
                                        for (var k=0; k < ListDisCount.length; k++) {
                                            if (ListDisCount[k].PROD_CD == prodList[i].PROD_CD && ListDisCount[k].ITEM_CD == prodList[i].ITEM_CD) {
                                            	discountAmt3 = ListDisCount[k].UNIT_DISCOUNT_AMT ;
                                            	unitTotalDiscount3 = unitTotalDiscount3 + discountAmt3;
                                            }
                                        }
                                    } else {
                                    	discountAmt3 = 0;
                                    }
                                    // ------ 할인금액 작성하기 -- 끝 ---  By. 신성훈

									onlineProdListHtml += "					<strong class='txt-red a-right'>"+ numberWithCommas((prodList[i].CURR_SELL_PRC * prodList[i].ORD_QTY) - (discntAmt * prodList[i].ORD_QTY) - discountAmt3) +"원</strong>";  //수정 By. 신성훈
								}

								//----- 할인정보 보여주기 여부 -- 시작 --- By. 신성훈
								var layerPopupDisplay3 = "N";
								if (resultObject && resultObject.Product && resultObject.Product.length > 0) {
									for (var j=0; j < resultObject.Product.length; j++) {
										if (resultObject.Product[j].PROD_CD == prodList[i].PROD_CD && resultObject.Product[j].ITEM_CD == prodList[i].ITEM_CD) {
											layerPopupDisplay3 = "Y";
										}
									}
								} else {
									layerPopupDisplay3 = "N";
								}
								//----- 할인정보 보여주기 여부 -- 끝 --- By. 신성훈

								if (layerPopupDisplay3 == "Y") { // ------- 수정 -- -- By. 신성훈
									onlineProdListHtml += "					<a class='category-unroll layerpop-trigger2' data-trigger='layerpop-dclist_"+prodList[i].PROD_CD + "' id='openDC3_" + prodList[i].PROD_CD + "' onclick='layerpopTriggerBtn(this,event);return false;' title='레이어팝업 열림'><span class='hidden'>레이어팝업 열기</span></a>";   //By 신성훈
									onlineProdListHtml += "<script type='text/template' data-attach='layerpop-dclist_"+prodList[i].PROD_CD+"'>";
									onlineProdListHtml += "	<section class='layerpop-type2 laypopup-locate-type6 layerpop-target layer-bottom'>";
									onlineProdListHtml += "		<div class='container'>";
									onlineProdListHtml += "			<h3 class='tit-h'>할인 내역</h3>";
									onlineProdListHtml += "				<div class='contents'>";
									onlineProdListHtml += "					<ul class='bul-circle' id='dcList'>";

									//------------ 수정 시작 --- By 신성훈
									if (resultObject && resultObject.Product && resultObject.Product.length > 0) {
										for (var j=0; j < resultObject.Product.length; j++){
											if (resultObject.Product[j].PROD_CD == prodList[i].PROD_CD && resultObject.Product[j].ITEM_CD == prodList[i].ITEM_CD) { //추가 By.신성훈

												var dcName = "";
												var dcAmt = 0;
												if (resultObject.Product[j].PROMO_KIND_CD == "02") {
													if (resultObject.Product[j].PROMO_CL_TYP_CD == '04') {
														dcName = "즉시상품할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '10') {
														dcName = "온라인상품할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '05') {
														dcName = "상품DM 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '06') {
														dcName = "N 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '08') {
														dcName = "고객상품할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '11') {
														dcName = "ONE 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '12') {
														dcName = "다둥이클럽할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '29') {
														dcName = "정기배송상품할인 쿠폰";
                                                    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '09') {
                                                        dcName = "카드상품할인";
                                                    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '21') {
                                                        dcName = "M쿠폰상품할인";
                                                    }
												}
												if (resultObject.Product[j].PROMO_KIND_CD == '01'){
												    if (resultObject.Product[j].PROMO_CL_TYP_CD == '01'){
												    	dcName = "살수록더싸게";
												    } else if(resultObject.Product[j].PROMO_CL_TYP_CD == '02'){
														dcName = "함께더싸게";
												    } else if(resultObject.Product[j].PROMO_CL_TYP_CD == '07'){
														dcName = "L.POINT 할인";
												    } else if(resultObject.Product[j].PROMO_CL_TYP_CD == '14'){
														dcName = "살수록더싸게";
												    } else if(resultObject.Product[j].PROMO_CL_TYP_CD == '17'){
														dcName = "다둥이 살수록더싸게";
												    } else if(resultObject.Product[j].PROMO_CL_TYP_CD == '18'){
														dcName = "다둥이 살수록더싸게";
												    } else if(resultObject.Product[j].PROMO_CL_TYP_CD == '25'){
														dcName = "온라인상품할인";
												    } else if(resultObject.Product[j].PROMO_CL_TYP_CD == '26'){
														dcName = "기타상품할인";
													}
												}

												//if (resultObject.Product[j].DC_UNIT_CD == '01' || resultObject.Product[j].DC_UNIT_CD == '03'){
													dcAmt = parseInt(nvl(resultObject.Product[j].EVNT_APPLY_AMT, 0)) * parseInt(nvl(resultObject.Product[j].EVNT_APPLY_QTY, 0));
												//} else if (resultObject.Product[j].DC_UNIT_CD == '02'){
												//	dcAmt = resultObject.Product[j].DC_EVT_CONTENT;
												//}

												if (dcName != ""){
													//if (resultObject.Product[j].DC_UNIT_CD == '01' || resultObject.Product[j].DC_UNIT_CD == '03'){
														onlineProdListHtml += "<li>" + dcName  + "<em class='txt-red flt-right'>" + numberWithCommas(dcAmt) + "원</em></li>";
													//} else if (resultObject.Product[j].DC_UNIT_CD == '02'){
													//	onlineProdListHtml += "<li>" + dcName + "<em class='txt-red flt-right'>" + dcAmt + "%</em></li>";
													//}
												}
										    }//if
										}//for
									}//if
									//------------ 수정 끝 --- By 신성훈

                                    //------------ 임직원 할인 시작 --- By.신성훈
                                    if (resultObject && resultObject.Staff) {
	                                    if (resultObject.Staff.length > 0) {
	                                        for (var j=0; j < resultObject.Staff.length; j++) {
	                                            if (resultObject.Staff[j].PROD_CD == prodList[i].PROD_CD && resultObject.Staff[j].ITEM_CD == prodList[i].ITEM_CD) {
	                                            	onlineProdListHtml += "<li>임직원할인<em class='txt-red flt-right'>" + numberWithCommas( (1 * resultObject.Staff[j].EVNT_APPLY_AMT) * (1 * resultObject.Staff[j].EVNT_APPLY_QTY)) + "원</em></li>";
	                                            }
	                                        }
	                                    }
                                    }
                                    //------------ 임직원 할인 끝 --- By.신성훈

									onlineProdListHtml += "					</ul>";
									onlineProdListHtml += "				</div>";
									onlineProdListHtml += "			</div>";
									onlineProdListHtml += "		<a href='javascript:;' class='btn-ico-close' title='레이어팝업 닫기'><i>할인 내역</i></a>";
									onlineProdListHtml += "	</section>";
									onlineProdListHtml += "</script>";

								}
								onlineProdListHtml += "				</p>";
								onlineProdListHtml += "			</div>";
								if(data.schdInfo.DELI_CNCL_YN != 'Y'){
									if(prodList[i].ITEM_CNCL_YN == 'Y'){
										onlineProdListHtml += "			<button type='button' class='btn-form-type3' onclick='cancelItem(\""+prodList[i].PERI_DELI_PROD_SEQ+"\", \""+'N'+"\");' >배송받기</button>";
									}else{
										onlineProdListHtml += "			<button type='button' class='btn-form-type2' onclick='cancelItem(\""+prodList[i].PERI_DELI_PROD_SEQ+"\", \""+'Y'+"\");'>이번만 취소</button>";
								     //onlineProdListHtml += "			<button type='button' class='btn-form layerpop-trigger2' data-trigger='layerpop-basket' onclick='setOption(this,\""+prodList[i].PERI_DELI_PROD_SEQ+"\",\""+prodList[i].PROD_CD+"\",\""+prodList[i].ITEM_CD+"\",\""+prodList[i].OPTION_YN+"\");' title='레이어팝업 열림'>변경</button>";
										if(prodList[i].OPTION_YN != 'Y'){
										onlineProdListHtml += "         <button title='변경' class='btn-form layerpop-trigger2' id='managePeri_" + "online" + "_" + prodList[i].PROD_CD + "' onclick='managePeriCart.add(this);' type='button'  data-category-id='" + prodList[i].CATEGORY_ID+ "' data-prod-cd='" + prodList[i].PROD_CD + "' data-prod-type-cd='"+ prodList[i].ONLINE_PROD_TYPE_CD +"' data-option-yn='"+prodList[i].OPTION_YN+"' data-gubun='"+prodList[i].GUBUN+"' data-item-cd='"+prodList[i].ITEM_CD+"'  data-str-cd='" + prodList[i].STR_CD+ "'  data-zip-seq='" + prodList[i].RECP_ZIP_SEQ+ "'  data-peri-deli-prod-seq='" + prodList[i].PERI_DELI_PROD_SEQ + "'  data-min-qty='" + prodList[i].MIN_ORD_PSBT_QTY + "' data-max-qty='" + prodList[i].MAX_ORD_PSBT_QTY + "' data-btn-obj-id='managePeri_" + "online" + "_" + prodList[i].PROD_CD + "' data-call-back-func='addPeriBasketCallback'>변경</button>";
										}else{
										onlineProdListHtml += "         <button title='변경' class='btn-form layerpop-trigger2' id='managePeri_" + "online" + "_" + prodList[i].PROD_CD + "' onclick='managePeriCart.add(this);' type='button'  data-category-id='" + prodList[i].CATEGORY_ID+ "' data-prod-cd='" + prodList[i].PROD_CD + "' data-prod-type-cd='"+ prodList[i].ONLINE_PROD_TYPE_CD +"' data-option-yn='"+prodList[i].OPTION_YN+"' data-gubun='"+prodList[i].GUBUN+"' data-item-cd='"+prodList[i].ITEM_CD+"'  data-str-cd='" + prodList[i].STR_CD+ "'  data-zip-seq='" + prodList[i].RECP_ZIP_SEQ+ "'  data-peri-deli-prod-seq='" + prodList[i].PERI_DELI_PROD_SEQ + "'  data-min-qty='" + prodList[i].MIN_ORD_PSBT_QTY + "' data-max-qty='" + prodList[i].MAX_ORD_PSBT_QTY + "' data-btn-obj-id='managePeri_" + "online" + "_" + prodList[i].PROD_CD + "' data-call-back-func='addPeriBasketCallback' disabled>변경</button>";
										}
									}
								}
								onlineProdListHtml += "		</div>";
								onlineProdListHtml += "	</article>";
								onlineProdListHtml += "</li>";
							}

							//--------------------------------------------------------------------------
							//-------------------- 매장 택배 상품 --------------------------------------
							//--------------------------------------------------------------------------
							if(prodList[i].BASKET_TYPE == '13' && prodList[i].ITEM_OTA_YN != 'Y'){
								mCnt++;
								if(prodList[i].ITEM_CNCL_YN != 'Y'){
									totSellPrcM = totSellPrcM + prodList[i].TOT_PRC;
									discntAmt =  prodList[i].DISCNT_AMT;
									if(discntAmt == undefined){
										discntAmt = 0;
									}
									totCurrSellPrcM = totCurrSellPrcM + prodList[i].TOT_CURR_PRC - (discntAmt * prodList[i].ORD_QTY);
									totProductDcM = totSellPrcM - totCurrSellPrcM;
								}

								outrangeProdListHtml += "<li>";

                                //---- li 태그 밖에서  li 태그 안으로 위치만 바꿈 -- 시작 --  By 신성훈
								outrangeProdListHtml += "    <input type='hidden' id='prodCd_"         + prodList[i].PROD_CD + "' name='prodCd' value='"         + prodList[i].PROD_CD          + "' />                        ";
								outrangeProdListHtml += "    <input type='hidden' id='itemCd_"         + prodList[i].PROD_CD + "' name='itemCd' value='"         + prodList[i].ITEM_CD          + "' />                        ";
								outrangeProdListHtml += "    <input type='hidden' id='categoryId_"     + prodList[i].PROD_CD + "' name='categoryId' value='"     + prodList[i].CATEGORY_ID      + "' />            ";
								outrangeProdListHtml += "    <input type='hidden' id='overseaYn_"      + prodList[i].PROD_CD + "' name='overseaYn' value='"      + prodList[i].OVERSEA_YN       + "' />               ";
								outrangeProdListHtml += "    <input type='hidden' id='nfomlVariation_" + prodList[i].PROD_CD + "' name='nfomlVariation' value='" + prodList[i].NFOML_VARIATION  + "' /> ";
								outrangeProdListHtml += "    <input type='hidden' id='minQty_"         + prodList[i].PROD_CD + "' name='minQty' value='"         + prodList[i].MIN_ORD_PSBT_QTY + "' />               ";
								outrangeProdListHtml += "    <input type='hidden' id='maxQty_"         + prodList[i].PROD_CD + "' name='maxQty' value='"         + prodList[i].MAX_ORD_PSBT_QTY + "' />               ";
                                //---- li 태그 밖에서  li 태그 안으로 위치만 바꿈 -- 끝 --  By 신성훈

								outrangeProdListHtml += "	<article>";
								outrangeProdListHtml += "		<div class='wrap-thumb'>";

								if(prodList[i].SELL_PSBT_YN == 'N'|| prodList[i].PERI_DELI_YN  == 'N'){
									outrangeProdListHtml += "<span class='prod-nofixe-del'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_nofixe_del_208x208.png' alt='정기배송 불가'></span>";
								}
								if(prodList[i].SOUT_YN == 'Y'){
									outrangeProdListHtml += "<span class='prod-tem-sold'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_tem_sold_208x208.png' alt='일시품절'></span>";
								}
								if(prodList[i].ITEM_CNCL_YN == 'Y'){
									outrangeProdListHtml += "<span class='prod-skip'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_skip_208x208.png' alt='이번만 취소'></span>";
									var tmpPrdCnt = $("#prd_cnt").text().replace("개","");
									$("#prd_cnt").text(tmpPrdCnt - 1 + "개")
								}
								if(data.schdInfo.DELI_CNCL_YN == 'Y'){
									outrangeProdListHtml += "<span class='prod-skip'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_skip_208x208.png' alt='이번만 취소'></span>";
									var tmpPrdCnt = $("#prd_cnt").text().replace("개","");
									$("#prd_cnt").text(tmpPrdCnt - 1 + "개")
								}
								outrangeProdListHtml += "			<a href='javascript:;' onclick='goProductDetailView(\""+prodList[i].CATEGORY_ID+"\",\""+prodList[i].PROD_CD+"\")'><img src='"+_LMCdnDynamicUrl+"/" + prodList[i].MD_SRCMK_CD.substring(0,5) +"/"+ prodList[i].MD_SRCMK_CD +"_1_80.jpg' onerror='javascript:showNoImage(this,80,80)' alt='"+ prodList[i].PROD_NM + "' class='thumb'></a>";
								outrangeProdListHtml += "		</div>";
								outrangeProdListHtml += "		<div class='wrap-info'>";
								outrangeProdListHtml += "			<a href='javascript:;' onclick='goProductDetailView(\""+prodList[i].CATEGORY_ID+"\",\""+prodList[i].PROD_CD+"\")'><strong class='prod-name'>"+ prodList[i].PROD_NM +"</strong></a>";

								if(prodList[i].ITEM_CNCL_YN != 'Y'){
									outrangeProdListHtml += "			<ul class='prod-item-detail'>";
									if(prodList[i].OPTION_NM != null){
										outrangeProdListHtml += "				<li>옵션 : "+ prodList[i].OPTION_NM +"</li>";
									}

									if (prodList[i].PEST_TYPE_NM != null) {
										var strArray = prodList[i].PEST_TYPE_NM.split('SERVICE_FRONT');
										outrangeProdListHtml += "				<li>증정품: " + strArray[1] + "</li>";
									}

									outrangeProdListHtml += "			</ul>";
								}

								outrangeProdListHtml += "			<div class='prod-num'>";
								if(data.schdInfo.DELI_CNCL_YN == 'Y' || prodList[i].SELL_PSBT_YN == 'N' || prodList[i].SOUT_YN == 'Y' || prodList[i].ITEM_CNCL_YN == 'Y' ){ // 수정 By 신성훈 -- prodList[i].SELL_PSBT_YN == 'Y'
									outrangeProdListHtml += "				<p class='set-spinner-type1-large' disabled='disabled'>";
									outrangeProdListHtml += "					<input type='text' class='ORD_QTY' id='ordQty"+prodList[i].PROD_CD+"' name='ordQty' value='"+ prodList[i].ORD_QTY +"' maxlength='3' title='수량' disabled='disabled'>";
									outrangeProdListHtml += "					<span class='set-btn'>";
									outrangeProdListHtml += "						<button type='button' class='up' MAX_ORD_PSBT_QTY='"+ prodList[i].MAX_ORD_PSBT_QTY +"' disabled='disabled'>수량 증가</button>";
									outrangeProdListHtml += "						<button type='button' class='down' MIN_ORD_PSBT_QTY='"+ prodList[i].MIN_ORD_PSBT_QTY +"' disabled='disabled'>수량 감소</button>";
									outrangeProdListHtml += "					</span>";
									outrangeProdListHtml += "				</p>";
								}else{
									outrangeProdListHtml += "				<p class='set-spinner-type1-large'>";
									outrangeProdListHtml += "					<input type='text' class='ORD_QTY' id='ordQty"+prodList[i].PROD_CD+"' name='ordQty' value='"+ prodList[i].ORD_QTY +"' maxlength='3' data-max-qty='"+ prodList[i].MAX_ORD_PSBT_QTY +"' data-min-qty='"+ prodList[i].MIN_ORD_PSBT_QTY +"' style='ime-mode:disabled' onKeyPress='return isOnlyNumberInput(event, false);' onblur='qtyValChk(\""+prodList[i].PROD_CD+"\",this.value);' title='수량'>";
									outrangeProdListHtml += "					<span class='set-btn'>";
									outrangeProdListHtml += "						<button type='button' class='up' MAX_ORD_PSBT_QTY='"+ prodList[i].MAX_ORD_PSBT_QTY +"'>수량 증가</button>";
									outrangeProdListHtml += "						<button type='button' class='down' MIN_ORD_PSBT_QTY='"+ prodList[i].MIN_ORD_PSBT_QTY +"'>수량 감소</button>";
									outrangeProdListHtml += "					</span>";
									outrangeProdListHtml += "				</p>";
								}
								outrangeProdListHtml += "				<p class='prod-price'>";
								if(data.schdInfo.DELI_CNCL_YN == 'Y' || prodList[i].SELL_PSBT_YN == 'N' || prodList[i].SOUT_YN == 'Y' || prodList[i].ITEM_CNCL_YN== 'Y' ){ // 수정 By 신성훈 -- prodList[i].SELL_PSBT_YN == 'Y'
									outrangeProdListHtml += "					<strong class='txt-red a-right'>0원</strong>";
								}else{
	                                //--------------- 종류별 판매가 소계를 계산하기 -- 시작 -------- By 신성훈 -----------
	                                unitTotalAmt4 = unitTotalAmt4 + (prodList[i].CURR_SELL_PRC * prodList[i].ORD_QTY) - (discntAmt * prodList[i].ORD_QTY) ;
	                                //--------------- 종류별 판매가 소계를 계산하기 -- 끝   -------- By 신성훈 -----------

                                    // ---------- 할인금액 작성하기 -- 시작 ---- By. 신성훈
                                    discountAmt4 = 0;

                                    if (ListDisCount) {
                                        for (var k=0; k < ListDisCount.length; k++) {
                                            if (ListDisCount[k].PROD_CD == prodList[i].PROD_CD && ListDisCount[k].ITEM_CD == prodList[i].ITEM_CD) {
                                            	discountAmt4 = ListDisCount[k].UNIT_DISCOUNT_AMT ;
                                            	unitTotalDiscount4 = unitTotalDiscount4 + discountAmt4;
                                            }
                                        }
                                    } else {
                                    	discountAmt4 = 0;
                                    }
                                    // ---------- 할인금액 작성하기 -- 끝 ---- By. 신성훈

									outrangeProdListHtml += "					<strong class='txt-red a-right'>"+ numberWithCommas((prodList[i].CURR_SELL_PRC * prodList[i].ORD_QTY) - (discntAmt * prodList[i].ORD_QTY) - discountAmt4) +"원</strong>"; // By 신성훈
								}

								//--------- 할인정보 보여주기 여부 -- 시작 ---- By. 신성훈
								var layerPopupDisplay4 = "N";
								if (resultObject && resultObject.Product && resultObject.Product.length > 0) {
									for (var j=0; j < resultObject.Product.length; j++) {
										if (resultObject.Product[j].PROD_CD == prodList[i].PROD_CD && resultObject.Product[j].ITEM_CD == prodList[i].ITEM_CD) {
											layerPopupDisplay4 = "Y";
										}
									}
								} else {
									layerPopupDisplay4 = "N";
								}
								//--------- 할인정보 보여주기 여부 -- 끝 ---- By. 신성훈

								if (layerPopupDisplay4 == "Y") {  // ------- 수정 --    -- By. 신성훈
									outrangeProdListHtml += "					<a class='category-unroll layerpop-trigger2' data-trigger='layerpop-dclist_"+prodList[i].PROD_CD + "' id='openDC4_" + prodList[i].PROD_CD + "' onclick='layerpopTriggerBtn(this,event);return false;' title='레이어팝업 열림'><span class='hidden'>레이어팝업 열기</span></a>";  //By 신성훈

									outrangeProdListHtml += "<script type='text/template' data-attach='layerpop-dclist_"+prodList[i].PROD_CD+"'>";
									outrangeProdListHtml += "	<section class='layerpop-type2 laypopup-locate-type6 layerpop-target layer-bottom'>";
									outrangeProdListHtml += "		<div class='container'>";
									outrangeProdListHtml += "			<h3 class='tit-h'>할인 내역</h3>";
									outrangeProdListHtml += "				<div class='contents'>";
									outrangeProdListHtml += "					<ul class='bul-circle' id='dcList'>";

									//----------- 수정 시작 -- By. 신성훈-----------
									if (resultObject && resultObject.Product && resultObject.Product.length > 0){
										for (var j=0; j < resultObject.Product.length; j++) {
                                            if (resultObject.Product[j].PROD_CD == prodList[i].PROD_CD && resultObject.Product[j].ITEM_CD == prodList[i].ITEM_CD) { //추가 By.신성훈
												var dcName = "";
												var dcAmt  = 0;

												if (resultObject.Product[j].PROMO_KIND_CD == "02") {
													if (resultObject.Product[j].PROMO_CL_TYP_CD == '04') {
														dcName = "즉시상품할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '10') {
														dcName = "온라인상품할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '05') {
														dcName = "상품DM 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '06') {
														dcName = "N 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '08') {
														dcName = "고객상품할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '11') {
														dcName = "ONE 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '12') {
														dcName = "다둥이클럽할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '29') {
														dcName = "정기배송상품할인 쿠폰";
                                                    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '09') {
                                                        dcName = "카드상품할인";
                                                    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '21') {
                                                        dcName = "M쿠폰상품할인";
                                                    }
												}
												if (resultObject.Product[j].PROMO_KIND_CD == '01') {
												    if (resultObject.Product[j].PROMO_CL_TYP_CD == '01') {
												    	dcName = "살수록더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '02') {
														dcName = "함께더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '07') {
														dcName = "L.POINT 할인";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '14') {
														dcName = "살수록더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '17') {
														dcName = "다둥이 살수록더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '18') {
														dcName = "다둥이 살수록더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '25') {
														dcName = "온라인상품할인";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '26') {
														dcName = "기타상품할인";
													}
												}

												//if (resultObject.Product[j].DC_UNIT_CD == '01' || resultObject.Product[j].DC_UNIT_CD == '03') {
													dcAmt = parseInt(nvl(resultObject.Product[j].EVNT_APPLY_AMT, 0)) * parseInt(nvl(resultObject.Product[j].EVNT_APPLY_QTY, 0));
												//} else if (resultObject.Product[j].DC_UNIT_CD == '02') {
												//	dcAmt = resultObject.Product[j].DC_EVT_CONTENT;
												//}

												if (dcName != "") {
													//if (resultObject.Product[j].DC_UNIT_CD == '01' || resultObject.Product[j].DC_UNIT_CD == '03') {
														outrangeProdListHtml += "<li>" + dcName  + "<em class='txt-red flt-right'>" + numberWithCommas(dcAmt) + "원</em></li>";
													//} else if (resultObject.Product[j].DC_UNIT_CD == '02'){
													//	outrangeProdListHtml += "<li>" + dcName + "<em class='txt-red flt-right'>" + dcAmt + "%</em></li>";
													//}
												}
                                            }
										}
									}
									//----------- 수정 끝 -- By. 신성훈-----------

                                    //----------- 임직원 할인 ---- 시작 -- By.신성훈
                                    if (resultObject && resultObject.Staff) {
	                                    if (resultObject.Staff.length > 0) {
	                                        for (var j=0; j < resultObject.Staff.length; j++) {
	                                            if (resultObject.Staff[j].PROD_CD == prodList[i].PROD_CD && resultObject.Staff[j].ITEM_CD == prodList[i].ITEM_CD) {
	                                            	outrangeProdListHtml += "<li>임직원할인<em class='txt-red flt-right'>" + numberWithCommas( (1 * resultObject.Staff[j].EVNT_APPLY_AMT) * (1 * resultObject.Staff[j].EVNT_APPLY_QTY)) + "원</em></li>";
	                                            }
	                                        }
	                                    }
                                    }
                                    //----------- 임직원 할인 ---- 시작 -- By.신성훈

									outrangeProdListHtml += "					</ul>";
									outrangeProdListHtml += "				</div>";
									outrangeProdListHtml += "			</div>";
									outrangeProdListHtml += "		<a href='javascript:;' class='btn-ico-close' title='레이어팝업 닫기'><i>할인 내역</i></a>";
									outrangeProdListHtml += "	</section>";
									outrangeProdListHtml += "</script>";

								}
								outrangeProdListHtml += "				</p>";
								outrangeProdListHtml += "			</div>";
								if(data.schdInfo.DELI_CNCL_YN != 'Y'){
									if(prodList[i].ITEM_CNCL_YN == 'Y'){
										outrangeProdListHtml += "			<button type='button' class='btn-form-type3' onclick='cancelItem(\""+prodList[i].PERI_DELI_PROD_SEQ+"\", \""+'N'+"\");' >배송받기</button>";
									}else{
									    if(prodList[i].PERI_DELI_YN == 'N'){
										outrangeProdListHtml += "이 상품은 더 이상 정기배송이 불가능합니다";
										}else{
										   outrangeProdListHtml += "			<button type='button' class='btn-form-type2' onclick='cancelItem(\""+prodList[i].PERI_DELI_PROD_SEQ+"\", \""+'Y'+"\");'>이번만 취소</button>";
									     //outrangeProdListHtml += "			<button type='button' class='btn-form layerpop-trigger2' data-trigger='layerpop-basket' onclick='setOption(this,\""+prodList[i].PERI_DELI_PROD_SEQ+"\",\""+prodList[i].PROD_CD+"\",\""+prodList[i].ITEM_CD+"\",\""+prodList[i].OPTION_YN+"\");' title='레이어팝업 열림'>변경</button>";
										   if(prodList[i].OPTION_YN != 'Y'){
										   outrangeProdListHtml += "        <button title='변경' class='btn-form layerpop-trigger2' id='managePeri_" + "out" + "_" + prodList[i].PROD_CD + "' onclick='managePeriCart.add(this);' type='button'  data-category-id='" + prodList[i].CATEGORY_ID+ "' data-prod-cd='" + prodList[i].PROD_CD + "' data-prod-type-cd='"+ prodList[i].ONLINE_PROD_TYPE_CD +"' data-option-yn='"+prodList[i].OPTION_YN+"' data-gubun='"+prodList[i].GUBUN+"' data-item-cd='"+prodList[i].ITEM_CD+"'  data-str-cd='" + prodList[i].STR_CD+ "'  data-zip-seq='" + prodList[i].RECP_ZIP_SEQ+ "'  data-peri-deli-prod-seq='" + prodList[i].PERI_DELI_PROD_SEQ + "'  data-min-qty='" + prodList[i].MIN_ORD_PSBT_QTY + "' data-max-qty='" + prodList[i].MAX_ORD_PSBT_QTY + "' data-btn-obj-id='managePeri_" + "out" + "_" + prodList[i].PROD_CD + "' data-call-back-func='addPeriBasketCallback'>변경</button>";
										   }else{
										   outrangeProdListHtml += "        <button title='변경' class='btn-form layerpop-trigger2' id='managePeri_" + "out" + "_" + prodList[i].PROD_CD + "' onclick='managePeriCart.add(this);' type='button'  data-category-id='" + prodList[i].CATEGORY_ID+ "' data-prod-cd='" + prodList[i].PROD_CD + "' data-prod-type-cd='"+ prodList[i].ONLINE_PROD_TYPE_CD +"' data-option-yn='"+prodList[i].OPTION_YN+"' data-gubun='"+prodList[i].GUBUN+"' data-item-cd='"+prodList[i].ITEM_CD+"'  data-str-cd='" + prodList[i].STR_CD+ "'  data-zip-seq='" + prodList[i].RECP_ZIP_SEQ+ "'  data-peri-deli-prod-seq='" + prodList[i].PERI_DELI_PROD_SEQ + "'  data-min-qty='" + prodList[i].MIN_ORD_PSBT_QTY + "' data-max-qty='" + prodList[i].MAX_ORD_PSBT_QTY + "' data-btn-obj-id='managePeri_" + "out" + "_" + prodList[i].PROD_CD + "' data-call-back-func='addPeriBasketCallback' disabled>변경</button>";
										   }
										}
									}
								}
								outrangeProdListHtml += "		</div>";
								outrangeProdListHtml += "	</article>";
								outrangeProdListHtml += "</li>";
							}

							//-----------------------------------------------------------------------------------------------
							//-------------------------------- 이번만 추가 상품 ---------------------------------------------
							//-----------------------------------------------------------------------------------------------
							if(prodList[i].ITEM_OTA_YN == 'Y'){
								aCnt++;
								if(prodList[i].SOUT_YN == 'Y'){
									totSellPrcA = totSellPrcA + 0;
								}else{
									totSellPrcA = totSellPrcA + prodList[i].TOT_PRC;
								}

								discntAmt =  prodList[i].DISCNT_AMT;
								if(discntAmt == undefined){
									discntAmt = 0;
								}

								if(prodList[i].SOUT_YN == 'Y'){
									totCurrSellPrcA = totCurrSellPrcA + 0 - (discntAmt * prodList[i].ORD_QTY);
									totProductDcA = totSellPrcA - totCurrSellPrcA;
								}else{
									totCurrSellPrcA = totCurrSellPrcA + prodList[i].TOT_CURR_PRC - (discntAmt * prodList[i].ORD_QTY);
									totProductDcA = totSellPrcA - totCurrSellPrcA;
								}

								var totSellPrcNA = 0;
								if(prodList[i].DELI_TYPE.substring(0,2) == '01'){
									totSellPrcNA = totSellPrcN + totSellPrcA;
								}

								addItemListHtml += "<li>";

                                //---- li 태그 밖에서  li 태그 안으로 위치만 바꿈 -- 시작 --  By 신성훈
								addItemListHtml += "    <input type='hidden' id='prodCd_"         + prodList[i].PROD_CD + "' name='prodCd' value='"         + prodList[i].PROD_CD          + "' />                        ";
								addItemListHtml += "    <input type='hidden' id='itemCd_"         + prodList[i].PROD_CD + "' name='itemCd' value='"         + prodList[i].ITEM_CD          + "' />                        ";
								addItemListHtml += "    <input type='hidden' id='categoryId_"     + prodList[i].PROD_CD + "' name='categoryId' value='"     + prodList[i].CATEGORY_ID      + "' />            ";
								addItemListHtml += "    <input type='hidden' id='overseaYn_"      + prodList[i].PROD_CD + "' name='overseaYn' value='"      + prodList[i].OVERSEA_YN       + "' />               ";
								addItemListHtml += "    <input type='hidden' id='nfomlVariation_" + prodList[i].PROD_CD + "' name='nfomlVariation' value='" + prodList[i].NFOML_VARIATION  + "' /> ";
								addItemListHtml += "    <input type='hidden' id='minQty_"         + prodList[i].PROD_CD + "' name='minQty' value='"         + prodList[i].MIN_ORD_PSBT_QTY + "' />               ";
								addItemListHtml += "    <input type='hidden' id='maxQty_"         + prodList[i].PROD_CD + "' name='maxQty' value='"         + prodList[i].MAX_ORD_PSBT_QTY + "' />               ";
                                //---- li 태그 밖에서  li 태그 안으로 위치만 바꿈 -- 끝 --  By 신성훈

								addItemListHtml += "	<article>";
								addItemListHtml += "		<span class='check-data "+prodList[i].PERI_DELI_PROD_SEQ +"' id='chk_"+prodList[i].PERI_DELI_PROD_SEQ+"' name='delChkbox' value='" + prodList[i].PERI_DELI_PROD_SEQ + "'>";
								addItemListHtml += "			<input type='checkbox' class='PERI_DELI_PROD_SEQ' id='checkbox_"+ prodList[i].PERI_DELI_PROD_SEQ+"_1' name='checkbox_"+ prodList[i].PERI_DELI_PROD_SEQ+"' value='' title='상품선택' onclick='javascript:oneCheck("+ prodList[i].PERI_DELI_PROD_SEQ +");'>";
								addItemListHtml += "		</span>";
								addItemListHtml += "		<label for='prod-item1'></label>";
								addItemListHtml += "		<div class='wrap-thumb'>";

								if(prodList[i].SOUT_YN == 'Y'){
									addItemListHtml += "<span class='prod-tem-sold'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_tem_sold_208x208.png' alt='일시품절'></span>";
								}
								if(data.schdInfo.DELI_CNCL_YN == 'Y'){
									addItemListHtml += "<span class='prod-skip'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_skip_208x208.png' alt='이번만 취소'></span>";
									var tmpPrdCnt = $("#prd_cnt").text().replace("개","");
									$("#prd_cnt").text(tmpPrdCnt - 1 + "개")
								}
								addItemListHtml += "			<a href='javascript:;' onclick='goProductDetailView(\""+prodList[i].CATEGORY_ID+"\",\""+prodList[i].PROD_CD+"\")'><img src='"+_LMCdnDynamicUrl+"/" + prodList[i].MD_SRCMK_CD.substring(0,5) +"/"+ prodList[i].MD_SRCMK_CD +"_1_80.jpg' onerror='javascript:showNoImage(this,80,80)' alt='"+ prodList[i].PROD_NM + "' class='thumb'></a>";
								addItemListHtml += "		</div>";
								addItemListHtml += "		<p class='prod-bene'>";
								if(prodList[i].DELI_TYPE.substring(0,2) == '01'){
									addItemListHtml += "<span class='icon-tag-delivery1'>매장배송</span>";
								}else if(prodList[i].DELI_TYPE.substring(0,2) == '02'){
									addItemListHtml += "<span class='icon-tag-delivery2'>매장택배</span>";
								}else if(prodList[i].DELI_TYPE.substring(0,2) == '04'){
									addItemListHtml += "<span class='icon-tag-delivery4'>업체택배</span>";
								}else if(prodList[i].DELI_TYPE.substring(0,2) == '06'){
									addItemListHtml += "<span class='icon-tag-delivery5'>명절배송</span>";
								}else if(prodList[i].DELI_TYPE.substring(0,2) == '07'){
									addItemListHtml += "<span class='icon-tag-delivery3'>매장픽업</span>";
								}
								if(prodList[i].ICON_10 == '1'){
									addItemListHtml += "<i class='icon-tag-freeship'>무료배송</i>";
								}
								addItemListHtml += "		</p>";
								addItemListHtml += "		<div class='wrap-info'>";
								addItemListHtml += "			<a href='javascript:;' onclick='goProductDetailView(\""+prodList[i].CATEGORY_ID+"\",\""+prodList[i].PROD_CD+"\")'><strong class='prod-name'>"+ prodList[i].PROD_NM +"</strong></a>";

								if(prodList[i].ITEM_CNCL_YN != 'Y'){
									addItemListHtml += "			<ul class='prod-item-detail'>";
									if(prodList[i].OPTION_NM != null){
										addItemListHtml += "				<li>옵션 : "+ prodList[i].OPTION_NM +"</li>";
									}

									if (prodList[i].PEST_TYPE_NM != null) {
										var strArray = prodList[i].PEST_TYPE_NM.split('SERVICE_FRONT');
										addItemListHtml += "				<li>증정품: " + strArray[1] + "</li>";
									}

									addItemListHtml += "			</ul>";
								}

								addItemListHtml += "			<div class='prod-num'>";
								if(data.schdInfo.DELI_CNCL_YN == 'Y' || prodList[i].SELL_PSBT_YN == 'N' || prodList[i].SOUT_YN == 'Y' || prodList[i].ITEM_CNCL_YN == 'Y' ){ // 수정 By 신성훈 -- prodList[i].SELL_PSBT_YN == 'Y'
									addItemListHtml += "				<p class='set-spinner-type1-large' disabled='disabled'>";
									addItemListHtml += "					<input type='text' class='ORD_QTY' id='ordQty' name='ordQty"+prodList[i].PROD_CD+"' value='"+ prodList[i].ORD_QTY +"' maxlength='3' title='수량' disabled='disabled'>";
									addItemListHtml += "					<span class='set-btn'>";
									addItemListHtml += "						<button type='button' class='up' MAX_ORD_PSBT_QTY='"+ prodList[i].MAX_ORD_PSBT_QTY +"' disabled='disabled'>수량 증가</button>";
									addItemListHtml += "						<button type='button' class='down' MIN_ORD_PSBT_QTY='"+ prodList[i].MIN_ORD_PSBT_QTY +"' disabled='disabled'>수량 감소</button>";
									addItemListHtml += "					</span>";
									addItemListHtml += "				</p>";
								}else{
									addItemListHtml += "				<p class='set-spinner-type1-large'>";
									addItemListHtml += "					<input type='text' class='ORD_QTY' id='ordQty"+prodList[i].PROD_CD+"' name='ordQty' value='"+ prodList[i].ORD_QTY +"' maxlength='3' data-max-qty='"+ prodList[i].MAX_ORD_PSBT_QTY +"' data-min-qty='"+ prodList[i].MIN_ORD_PSBT_QTY +"' style='ime-mode:disabled' onKeyPress='return isOnlyNumberInput(event, false);' onblur='qtyValChk(\""+prodList[i].PROD_CD+"\",this.value);' title='수량'>";
									addItemListHtml += "					<span class='set-btn'>";
									addItemListHtml += "						<button type='button' class='up' MAX_ORD_PSBT_QTY='"+ prodList[i].MAX_ORD_PSBT_QTY +"'>수량 증가</button>";
									addItemListHtml += "						<button type='button' class='down' MIN_ORD_PSBT_QTY='"+ prodList[i].MIN_ORD_PSBT_QTY +"'>수량 감소</button>";
									addItemListHtml += "					</span>";
									addItemListHtml += "				</p>";
								}
								addItemListHtml += "				<p class='prod-price'>";
								if(data.schdInfo.DELI_CNCL_YN == 'Y' || prodList[i].SELL_PSBT_YN == 'N' || prodList[i].SOUT_YN == 'Y' || prodList[i].ITEM_CNCL_YN == 'Y' ){ // 수정 By 신성훈 -- prodList[i].SELL_PSBT_YN == 'Y'
									addItemListHtml += "					<strong class='txt-red a-right'>0원</strong>";
								}else{
	                                //--------------- 종류별 판매가 소계를 계산하기 -- 시작 -------- By 신성훈 -----------
	                                unitTotalAmt5 = unitTotalAmt5 + (prodList[i].CURR_SELL_PRC * prodList[i].ORD_QTY) - (discntAmt * prodList[i].ORD_QTY) ;
	                                //--------------- 종류별 판매가 소계를 계산하기 -- 끝   -------- By 신성훈 -----------

                                    // ----------- 할인금액 작성하기 -- 시작 ----  By. 신성훈
                                    discountAmt5 = 0;

                                    if (ListDisCount) {
                                        for (var k=0; k < ListDisCount.length; k++) {
                                            if (ListDisCount[k].PROD_CD == prodList[i].PROD_CD && ListDisCount[k].ITEM_CD == prodList[i].ITEM_CD) {
                                            	discountAmt5 = ListDisCount[k].UNIT_DISCOUNT_AMT ;
                                            	unitTotalDiscount5 = unitTotalDiscount5 + discountAmt5;
                                            }
                                        }
                                    } else {
                                    	discountAmt5 = 0;
                                    }
                                    // ----------- 할인금액 작성하기 -- 끝----  By. 신성훈

									addItemListHtml += "					<strong class='txt-red a-right'>"+ numberWithCommas((prodList[i].CURR_SELL_PRC * prodList[i].ORD_QTY) - (discntAmt * prodList[i].ORD_QTY) - discountAmt5) +"원</strong>"; // By 신성훈
								}

								//---------- 할인정보 보여주기 여부  -- 시작 ----  By. 신성훈
								var layerPopupDisplay5 = "N";
								if (resultObject && resultObject.Product && resultObject.Product.length > 0) {
									for (var j=0; j < resultObject.Product.length; j++) {
										if (resultObject.Product[j].PROD_CD == prodList[i].PROD_CD && resultObject.Product[j].ITEM_CD == prodList[i].ITEM_CD) {
											layerPopupDisplay5 = "Y";
										}
									}
								} else {
									layerPopupDisplay5 = "N";
								}
								//---------- 할인정보 보여주기 여부  -- 시작 ----  By. 신성훈

								if (layerPopupDisplay5 == "Y") { // ------- 수정 --- By. 신성훈
									addItemListHtml += "					<a class='category-unroll layerpop-trigger2' data-trigger='layerpop-dclist_"+prodList[i].PROD_CD + "' id='openDC5_" + prodList[i].PROD_CD + "' onclick='layerpopTriggerBtn(this,event);return false;' title='레이어팝업 열림'><span class='hidden'>레이어팝업 열기</span></a>";  //By 신성훈
									addItemListHtml += "<script type='text/template' data-attach='layerpop-dclist_"+prodList[i].PROD_CD+"'>";
									addItemListHtml += "	<section class='layerpop-type2 laypopup-locate-type6 layerpop-target layer-bottom'>";
									addItemListHtml += "		<div class='container'>";
									addItemListHtml += "			<h3 class='tit-h'>할인 내역</h3>";
									addItemListHtml += "				<div class='contents'>";
									addItemListHtml += "					<ul class='bul-circle' id='dcList'>";

									// ----------------- 수정 시작 -- By. 신성훈 ----------
                                    if (resultObject && resultObject.Product && resultObject.Product.length > 0) {
                                        for (var j=0; j < resultObject.Product.length; j++) {
                                            if (resultObject.Product[j].PROD_CD == prodList[i].PROD_CD && resultObject.Product[j].ITEM_CD == prodList[i].ITEM_CD) { //추가 By.신성훈
												var dcName = "";
												var dcAmt  = 0;

												if (resultObject.Product[j].PROMO_KIND_CD == "02") {
													if (resultObject.Product[j].PROMO_CL_TYP_CD == '04') {
														dcName = "즉시상품할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '10') {
														dcName = "온라인상품할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '05') {
														dcName = "상품DM 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '06') {
														dcName = "N 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '08') {
														dcName = "고객상품할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '11') {
														dcName = "ONE 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '12') {
														dcName = "다둥이클럽할인 쿠폰";
													} else if (resultObject.Product[j].PROMO_CL_TYP_CD == '29') {
														dcName = "정기배송상품할인 쿠폰";
                                                    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '09') {
                                                        dcName = "카드상품할인";
                                                    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '21') {
                                                        dcName = "M쿠폰상품할인";
                                                    }
												}
												if (resultObject.Product[j].PROMO_KIND_CD == '01'){
												    if (resultObject.Product[j].PROMO_CL_TYP_CD == '01') {
												    	dcName = "살수록더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '02') {
														dcName = "함께더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '07') {
														dcName = "L.POINT 할인";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '14') {
														dcName = "살수록더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '17') {
														dcName = "다둥이 살수록더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '18') {
														dcName = "다둥이 살수록더싸게";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '25') {
														dcName = "온라인상품할인";
												    } else if (resultObject.Product[j].PROMO_CL_TYP_CD == '26') {
														dcName = "기타상품할인";
													}
												}

												//if (resultObject.Product[j].DC_UNIT_CD == '01' || resultObject.Product[j].DC_UNIT_CD == '03') {
													dcAmt = parseInt(nvl(resultObject.Product[j].EVNT_APPLY_AMT, 0)) * parseInt(nvl(resultObject.Product[j].EVNT_APPLY_QTY, 0));
												//} else if (resultObject.Product[j].DC_UNIT_CD == '02'){
												//	dcAmt = resultObject.Product[j].DC_EVT_CONTENT;
												//}

												if (dcName != "") {
													//if (resultObject.Product[j].DC_UNIT_CD == '01' || resultObject.Product[j].DC_UNIT_CD == '03') {
														addItemListHtml += "<li>" + dcName  + "<em class='txt-red flt-right'>" + numberWithCommas(dcAmt) + "원</em></li>";
													//} else if (resultObject.Product[j].DC_UNIT_CD == '02'){
													//	addItemListHtml += "<li>" + dcName + "<em class='txt-red flt-right'>" + dcAmt + "%</em></li>";
													//}
												}
                                            }//if
										}//for
									}//if
									// ----------------- 수정 끝 -- By. 신성훈 ----------

                                    //--------------- 임직원 할인 --- 시작 --- By.신성훈
                                    if (resultObject && resultObject.Staff) {
	                                    if (resultObject.Staff.length > 0) {
	                                        for (var j=0; j < resultObject.Staff.length; j++) {
	                                            if (resultObject.Staff[j].PROD_CD == prodList[i].PROD_CD && resultObject.Staff[j].ITEM_CD == prodList[i].ITEM_CD) {
	                                            	addItemListHtml += "<li>임직원할인<em class='txt-red flt-right'>" + numberWithCommas( (1 * resultObject.Staff[j].EVNT_APPLY_AMT) * (1 * resultObject.Staff[j].EVNT_APPLY_QTY)) + "원</em></li>";
	                                            }
	                                        }
	                                    }
                                    }
                                    //--------------- 임직원 할인 --- 끝 --- By.신성훈

									addItemListHtml += "					</ul>";
									addItemListHtml += "				</div>";
									addItemListHtml += "			</div>";
									addItemListHtml += "		<a href='javascript:;' class='btn-ico-close' title='레이어팝업 닫기'><i>할인 내역</i></a>";
									addItemListHtml += "	</section>";
									addItemListHtml += "</script>";
								}
								addItemListHtml += "				</p>";
								addItemListHtml += "			</div>";
								if(data.schdInfo.DELI_CNCL_YN != 'Y'){
									if(prodList[i].ITEM_CNCL_YN == 'Y'){
									}else{
									  //addItemListHtml += "		<button type='button' class='btn-form layerpop-trigger2' data-trigger='layerpop-basket' onclick='setOption(this,\""+prodList[i].PERI_DELI_PROD_SEQ+"\",\""+prodList[i].PROD_CD+"\",\""+prodList[i].ITEM_CD+"\",\""+prodList[i].OPTION_YN+"\");' title='레이어팝업 열림'>변경</button>";
										addItemListHtml += "      <button title='변경' class='btn-form layerpop-trigger2' id='managePeri_" + "add" + "_" + prodList[i].PROD_CD + "' onclick='managePeriCart.add(this);' type='button'  data-category-id='" + prodList[i].CATEGORY_ID+ "' data-prod-cd='" + prodList[i].PROD_CD + "' data-prod-type-cd='"+ prodList[i].ONLINE_PROD_TYPE_CD +"' data-option-yn='"+prodList[i].OPTION_YN+"' data-gubun='"+prodList[i].GUBUN+"' data-item-cd='"+prodList[i].ITEM_CD+"'  data-str-cd='" + prodList[i].STR_CD+ "'  data-zip-seq='" + prodList[i].RECP_ZIP_SEQ+ "'  data-peri-deli-prod-seq='" + prodList[i].PERI_DELI_PROD_SEQ + "'  data-min-qty='" + prodList[i].MIN_ORD_PSBT_QTY + "' data-max-qty='" + prodList[i].MAX_ORD_PSBT_QTY + "' data-btn-obj-id='managePeri_" + "add" + "_" + prodList[i].PROD_CD + "' data-call-back-func='addPeriBasketCallback'>변경</button>";
										addItemListHtml += "		<button type='button' class='btn-form-type2' onclick='deleteChkItem(\""+ prodList[i].PERI_DELI_PROD_SEQ +"\");'>삭제</button>";
									}
								}
								addItemListHtml += "		</div>";
								addItemListHtml += "	</article>";
								addItemListHtml += "</li>";
							}

						}

					if(Number(totSellPrcN) >= 30000 || totSellPrcNA >= 30000){
						totDlvPrcN = 0;
					}
					var totSumSellPrcN	 = totCurrSellPrcN + totDlvPrcN;
					$("#storeProdList").html(storeProdListHtml);
					if(storeProdListHtml != ""){
						$("#NprodCnt").text(nCnt);
						$("#storeItemBox").removeClass("hide");
						var storeProdSumHtml = "";
						storeProdSumHtml += "				</ul>";
						storeProdSumHtml += "			</div>";
						storeProdSumHtml += "		</div>";
						storeProdSumHtml += "		<div class='wrap-paysum'><div class='paysum'>";
						storeProdSumHtml += "			<ul>";
						storeProdSumHtml += "				<li>";
						storeProdSumHtml += "					<dl>";
						storeProdSumHtml += "						<dt>판매가</dt>";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							storeProdSumHtml += "						<dd>0<span class='won'>원</span></dd>";
						}else{
							storeProdSumHtml += "						<dd>"+ numberWithCommas(unitTotalAmt2) +"<span class='won'>원</span></dd>"; // 수정 By. 신성훈
						}
						storeProdSumHtml += "					</dl>";
						storeProdSumHtml += "				</li>";
						storeProdSumHtml += "				<li>";
						storeProdSumHtml += "					<dl>";
						storeProdSumHtml += "						<dt>할인금액</dt>";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							storeProdSumHtml += "						<dd class='minus-w'>0<span class='won'>원</span></dd>";
						}else{
							storeProdSumHtml += "						<dd class='minus-w'>"+ numberWithCommas(unitTotalDiscount2) +"<span class='won'>원</span></dd>"; // 수정 By. 신성훈
						}
						storeProdSumHtml += "					</dl>";
						storeProdSumHtml += "				</li>";
						storeProdSumHtml += "				<li>";
						storeProdSumHtml += "					<dl>";
						storeProdSumHtml += "						<dt>배송비</dt>";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							storeProdSumHtml += "						<dd class='plus-w'>0<span class='won'>원</span></dd>";
						}else{
							storeProdSumHtml += "						<dd class='plus-w'>"+ numberWithCommas(totDlvPrcN) +"<span class='won'>원</span></dd>";
						}
						storeProdSumHtml += "					</dl>";
						storeProdSumHtml += "				</li>";
						storeProdSumHtml += "				<li>";
						storeProdSumHtml += "					<dl>";
						storeProdSumHtml += "						<dt>결제예정금액</dt>";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							storeProdSumHtml += "						<dd class='equal-w'>0<span class='won'>원</span></dd>";
						}else{
							storeProdSumHtml += "						<dd class='equal-w'>"+ numberWithCommas(unitTotalAmt2 - unitTotalDiscount2 + totDlvPrcN) +"<span class='won'>원</span></dd>"; // 수정 By. 신성훈
						}
						storeProdSumHtml += "					</dl>";
						storeProdSumHtml += "				</li>";
						storeProdSumHtml += "			</ul>";
						storeProdSumHtml += "		</div></div>";
						storeProdSumHtml += "	</div>";
						storeProdSumHtml += "</section>";

						$("#storeProdSum").html(storeProdSumHtml);
					}

					var totSumSellPrcV	 = totCurrSellPrcV + totDlvPrcV;
					$("#onlineProdList").html(onlineProdListHtml);
					if(onlineProdListHtml != ""){
						$("#VprodCnt").text(vCnt);
						$("#onlineItemBox").removeClass("hide");
						var onlineProdSumHtml = "";
						onlineProdSumHtml += "				</ul>";
						onlineProdSumHtml += "			</div>";
						onlineProdSumHtml += "		</div>";
						onlineProdSumHtml += "		<div class='wrap-paysum'><div class='paysum'>";
						onlineProdSumHtml += "			<ul>";
						onlineProdSumHtml += "				<li>";
						onlineProdSumHtml += "					<dl>";
						onlineProdSumHtml += "						<dt>판매가</dt>";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							onlineProdSumHtml += "						<dd>0<span class='won'>원</span></dd>";
						}else{
							onlineProdSumHtml += "						<dd>"+ numberWithCommas(unitTotalAmt3) +"<span class='won'>원</span></dd>"; // 수정 By. 신성훈
						}
						onlineProdSumHtml += "					</dl>";
						onlineProdSumHtml += "				</li>";
						onlineProdSumHtml += "				<li>";
						onlineProdSumHtml += "					<dl>";
						onlineProdSumHtml += "						<dt>할인금액</dt>";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							onlineProdSumHtml += "						<dd class='minus-w'>0<span class='won'>원</span></dd>";
						}else{
							onlineProdSumHtml += "						<dd class='minus-w'>"+ numberWithCommas(unitTotalDiscount3) +"<span class='won'>원</span></dd>"; // 수정 By. 신성훈
						}
						onlineProdSumHtml += "					</dl>";
						onlineProdSumHtml += "				</li>";
						onlineProdSumHtml += "				<li>";
						onlineProdSumHtml += "					<dl>";
						onlineProdSumHtml += "						<dt>배송비</dt>";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							onlineProdSumHtml += "						<dd class='plus-w'>0<span class='won'>원</span></dd>";
						}else{
							onlineProdSumHtml += "						<dd class='plus-w'>"+ numberWithCommas(totDlvPrcV) +"<span class='won'>원</span></dd>";
						}
						onlineProdSumHtml += "					</dl>";
						onlineProdSumHtml += "				</li>";
						onlineProdSumHtml += "				<li>";
						onlineProdSumHtml += "					<dl>";
						onlineProdSumHtml += "						<dt>결제예정금액</dt>";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							onlineProdSumHtml += "						<dd class='equal-w'>0<span class='won'>원</span></dd>";
						}else{
							onlineProdSumHtml += "						<dd class='equal-w'>"+ numberWithCommas(unitTotalAmt3 - unitTotalDiscount3 + totDlvPrcV) +"<span class='won'>원</span></dd>"; // 수정 By. 신성훈
						}
						onlineProdSumHtml += "					</dl>";
						onlineProdSumHtml += "				</li>";
						onlineProdSumHtml += "			</ul>";
						onlineProdSumHtml += "		</div></div>";
						onlineProdSumHtml += "	</div>";
						onlineProdSumHtml += "</section>";

						$("#onlineProdSum").html(onlineProdSumHtml);
					}

					if(Number(totSellPrcM) >= 30000){
						totDlvPrcM = 0;
					}
					var totSumSellPrcM	 = totCurrSellPrcM + totDlvPrcM;
					$("#outrangeProdList").html(outrangeProdListHtml);
					if(outrangeProdListHtml != ""){
						$("#MprodCnt").text(mCnt);
						$("#outrangeItemBox").removeClass("hide");
						var outrangeProdSumHtml = "";
						outrangeProdSumHtml += "				</ul>";
						outrangeProdSumHtml += "			</div>";
						outrangeProdSumHtml += "		</div>";
						outrangeProdSumHtml += "		<div class='wrap-paysum'><div class='paysum'>";
						outrangeProdSumHtml += "			<ul>";
						outrangeProdSumHtml += "				<li>";
						outrangeProdSumHtml += "					<dl>";
						outrangeProdSumHtml += "						<dt>판매가</dt>";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							outrangeProdSumHtml += "						<dd>0<span class='won'>원</span></dd>";
						}else{
							outrangeProdSumHtml += "						<dd>"+ numberWithCommas(unitTotalAmt4) +"<span class='won'>원</span></dd>"; // 수정 By. 신성훈
						}
						outrangeProdSumHtml += "					</dl>";
						outrangeProdSumHtml += "				</li>";
						outrangeProdSumHtml += "				<li>";
						outrangeProdSumHtml += "					<dl>";
						outrangeProdSumHtml += "						<dt>할인금액</dt>";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							outrangeProdSumHtml += "						<dd class='minus-w'>0<span class='won'>원</span></dd>";
						}else{
							outrangeProdSumHtml += "						<dd class='minus-w'>"+ numberWithCommas(unitTotalDiscount4) +"<span class='won'>원</span></dd>"; // 수정 By. 신성훈
						}
						outrangeProdSumHtml += "					</dl>";
						outrangeProdSumHtml += "				</li>";
						outrangeProdSumHtml += "				<li>";
						outrangeProdSumHtml += "					<dl>";
						outrangeProdSumHtml += "						<dt>배송비</dt>";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							outrangeProdSumHtml += "						<dd class='plus-w'>0<span class='won'>원</span></dd>";
						}else{
							outrangeProdSumHtml += "						<dd class='plus-w'>"+ numberWithCommas(totDlvPrcM) +"<span class='won'>원</span></dd>";
						}
						outrangeProdSumHtml += "					</dl>";
						outrangeProdSumHtml += "				</li>";
						outrangeProdSumHtml += "				<li>";
						outrangeProdSumHtml += "					<dl>";
						outrangeProdSumHtml += "						<dt>결제예정금액</dt>";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							outrangeProdSumHtml += "						<dd class='equal-w'>0<span class='won'>원</span></dd>";
						}else{
							outrangeProdSumHtml += "						<dd class='equal-w'>"+ numberWithCommas(unitTotalAmt4 - unitTotalDiscount4 + totDlvPrcM) +"<span class='won'>원</span></dd>"; // 수정 By. 신성훈
						}
						outrangeProdSumHtml += "					</dl>";
						outrangeProdSumHtml += "				</li>";
						outrangeProdSumHtml += "			</ul>";
						outrangeProdSumHtml += "		</div></div>";
						outrangeProdSumHtml += "	</div>";
						outrangeProdSumHtml += "</section>";

						$("#outrangeProdSum").html(outrangeProdSumHtml);
					}

					var totSumSellPrcA	 = totCurrSellPrcA + totDlvPrcA;
					$("#addItemList").html(addItemListHtml);
					if(addItemListHtml != ""){
						$("#AprodCnt").text(aCnt);
						$("#addItemBox").removeClass("hide");
						var addItemProdSumHtml = "";
						addItemProdSumHtml += "				</ul>";
						addItemProdSumHtml += "			</div>";
						addItemProdSumHtml += "		</div>";
						addItemProdSumHtml += "		<div class='wrap-paysum'><div class='paysum'>";
						addItemProdSumHtml += "			<ul>";
						addItemProdSumHtml += "				<li>";
						addItemProdSumHtml += "					<dl>";
						addItemProdSumHtml += "						<dt>판매가</dt>";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							addItemProdSumHtml += "						<dd>0<span class='won'>원</span></dd>";
						}else{
							addItemProdSumHtml += "						<dd>"+ numberWithCommas(unitTotalAmt5) +"<span class='won'>원</span></dd>"; // 수정 By. 신성훈
						}
						addItemProdSumHtml += "					</dl>";
						addItemProdSumHtml += "				</li>";
						addItemProdSumHtml += "				<li>";
						addItemProdSumHtml += "					<dl>";
						addItemProdSumHtml += "						<dt>할인금액</dt>";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							addItemProdSumHtml += "						<dd class='minus-w'>0<span class='won'>원</span></dd>";
						}else{
							addItemProdSumHtml += "						<dd class='minus-w'>"+ numberWithCommas(unitTotalDiscount5) +"<span class='won'>원</span></dd>"; // 수정 By. 신성훈
						}
						addItemProdSumHtml += "					</dl>";
						addItemProdSumHtml += "				</li>";
						addItemProdSumHtml += "				<li>";
						addItemProdSumHtml += "					<dl>";
						addItemProdSumHtml += "						<dt>배송비</dt>";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							addItemProdSumHtml += "						<dd class='plus-w'>0<span class='won'>원</span></dd>";
						}else{
							addItemProdSumHtml += "						<dd class='plus-w'>"+ numberWithCommas(totDlvPrcA) +"<span class='won'>원</span></dd>";
						}
						addItemProdSumHtml += "					</dl>";
						addItemProdSumHtml += "				</li>";
						addItemProdSumHtml += "				<li>";
						addItemProdSumHtml += "					<dl>";
						addItemProdSumHtml += "						<dt>결제예정금액</dt>";
						if(data.schdInfo.DELI_CNCL_YN == 'Y'){
							addItemProdSumHtml += "						<dd class='equal-w'>0<span class='won'>원</span></dd>";
						}else{
							addItemProdSumHtml += "						<dd class='equal-w'>"+ numberWithCommas(unitTotalAmt5 - unitTotalDiscount5 + totDlvPrcA) +"<span class='won'>원</span></dd>"; // 수정 By. 신성훈
						}
						addItemProdSumHtml += "					</dl>";
						addItemProdSumHtml += "				</li>";
						addItemProdSumHtml += "			</ul>";
						addItemProdSumHtml += "		</div></div>";
						addItemProdSumHtml += "	</div>";
						addItemProdSumHtml += "</section>";

						$("#addItemProdSum").html(addItemProdSumHtml);
					}

					var totSumSellPrc = Number(totSumSellPrcN) + Number(totSumSellPrcV) + Number(totSumSellPrcM) + Number(totSumSellPrcA);
					var totSumDlvPrc = Number(totDlvPrcN) + Number(totDlvPrcV) + Number(totDlvPrcM) + Number(totDlvPrcA);


					var totalSumHtml = "";

					totalSumHtml += "<ul>";
					totalSumHtml += "	<li>";
					totalSumHtml += "		<dl>";
					totalSumHtml += "			<dt>전체 상품금액</dt>";
					if(data.schdInfo.DELI_CNCL_YN == 'Y'){
						totalSumHtml += "			<dd>0<span class='won'>원</span></dd>";
					}else{
						totalSumHtml += "			<dd>"+ numberWithCommas(sellPriceTotal); +"<span class='won'>원</span></dd>";  // -- 수정 By 신성훈
					}
					totalSumHtml += "		</dl>";
					totalSumHtml += "	</li>";
					totalSumHtml += "	<li>";
					totalSumHtml += "		<dl>";
					totalSumHtml += "			<dt>전체 할인금액</dt>";
					if(data.schdInfo.DELI_CNCL_YN == 'Y'){
						totalSumHtml += "			<dd class='minus-w'>0<span class='won'>원</span></dd>";
					}else{
						totalSumHtml += "			<dd class='minus-w'>"+ numberWithCommas(totalDcAmt) +"<span class='won'>원</span></dd>"; // -- 수정 By 신성훈
					}
					totalSumHtml += "		</dl>";
					totalSumHtml += "	</li>";
					totalSumHtml += "	<li>";
					totalSumHtml += "		<dl>";
					totalSumHtml += "			<dt>전체 배송비</dt>";
					if(data.schdInfo.DELI_CNCL_YN == 'Y'){
						totalSumHtml += "			<dd class='plus-w'>0<span class='won'>원</span></dd>";
					}else{
						totalSumHtml += "			<dd class='plus-w'>" + numberWithCommas(totalDeliverAmt) + "<span class='won'>원</span></dd>"; // -- 수정 By 신성훈
					}
					totalSumHtml += "		</dl>";
					totalSumHtml += "	</li>";
					totalSumHtml += "	<li>";
					totalSumHtml += "		<dl>";
					totalSumHtml += "			<dt>총 결제예정금액</dt>";
					if(data.schdInfo.DELI_CNCL_YN == 'Y'){
						totalSumHtml += "			<dd class='equal'>0<span class='won'>원</span></dd>";
					}else{
						//totalSumHtml += "			<dd class='equal'>"+ numberWithCommas(payAmt) +"<span class='won'>원</span></dd>";  // -- 수정 By 신성훈
						totalSumHtml += "			<dd class='equal'>"+ numberWithCommas(totSumSellPrc - totalDcAmt) +"<span class='won'>원</span></dd>";  // -- 수정 By 신성훈
					}

					//var totSumSellPrc = Number(totSumSellPrcN) + Number(totSumSellPrcV) + Number(totSumSellPrcM) + Number(totSumSellPrcA);
					//var totSumDlvPrc = Number(totDlvPrcN) + Number(totDlvPrcV) + Number(totDlvPrcM) + Number(totDlvPrcA);
					totalSumHtml += "	<input type='hidden' id='totRealPrc' name='totRealPrc' value='"+totSumSellPrc+"'/>";
					totalSumHtml += "	<input type='hidden' id='totRealDlvPrc' name='totRealDlvPrc' value='"+totSumDlvPrc+"'/>";
					totalSumHtml += "		</dl>";
					totalSumHtml += "	</li>";
					totalSumHtml += "</ul>";
					$("#totalProdSum").removeClass("hide");
					$("#totalProdSum").html(totalSumHtml);

					//var totSumSellPrc = Number(totSumSellPrcN) + Number(totSumSellPrcV) + Number(totSumSellPrcM) + Number(totSumSellPrcA);
					if(data.schdInfo.DELI_CNCL_YN == 'Y'){
						$("#totPrice").text("0원");
					}else{
						$("#totPrice").text(numberWithCommas(payAmt)+"원");  //수정 By.신성훈
					}

					var couPonUseAmt = 0;
					var freeDeliUseAmt = 0;
					if(data.schdInfo.SETL_COUPON_USE_YN == 'Y'){
						if(data.schdInfo.COUPON_AMT != null){
							couPonUseAmt = Number(data.schdInfo.COUPON_AMT);
						}else{
							if(data.payCouponList != "" && data.payCouponList != null){
								couPonUseAmt = Number(data.payCouponList[0].COUPON_DC_AMT);
							}else{
								couPonUseAmt = 0;
							}
						}
					}

					if(totSumDlvPrc > 0){
						if(data.schdInfo.DELI_COUPON_USE_YN == 'Y'){
							if(data.deliveryCouponList != "" && data.deliveryCouponList != null){
								if(data.deliveryCouponList[0].COUPON_DC_AMT != null && data.deliveryCouponList[0].COUPON_DC_AMT != 0){
									freeDeliUseAmt = data.deliveryCouponList[0].COUPON_DC_AMT;
								}
							}else{
								if(data.schdInfo.DELI_CNCL_YN == 'Y'){
									$("#dlvCoupon").text("적용하지 않음");
								}else{
									$("#dlvCoupon").text("쿠폰없음");
								}
							}
						}
					}else{
						$("#dlvCoupon").text("적용하지 않음");
					}

	//				totSumSellPrc = Number(totSumSellPrc) -Number(couPonUseAmt) - Number(freeDeliUseAmt);

					var milgUseC = 0;
					var dpamtUseC = 0;
					var milgUse = 0;
					var dpamtUse = 0;
					if(data.schdInfo.MILG_USE != "마일리지 없음" && data.schdInfo.MILG_USE != "적용하지 않음"){
						milgUse = data.schdInfo.MILG_USE;
						milgUse = milgUse.replace("P","");
						milgUse = milgUse.replace(/,/gi, "");
					}else{
						milgUse = 0;
					}
					if(data.schdInfo.DPAMT_USE != "예치금 없음" && data.schdInfo.DPAMT_USE != "적용하지 않음"){
						dpamtUse = data.schdInfo.DPAMT_USE;
						dpamtUse = dpamtUse.replace("원","");
						dpamtUse = dpamtUse.replace(/,/gi, "");
					}else{
						dpamtUse = 0;
					}

					if(data.schdInfo.STAFF_DC_YN == 'Y'){
						if(Number(dpamtUse) > Number(totSumSellPrc)){
							dpamtUseC = Number(totSumSellPrc);
						}else{
							dpamtUseC = Number(dpamtUse);
						}
					}else{
						if(Number(milgUse) > Number(totSumSellPrc)){
							milgUseC = Number(totSumSellPrc);
							dpamtUseC = 0;
						}
						if(Number(milgUse) > 0 &&  Number(milgUse) <= Number(totSumSellPrc) && Number(dpamtUse) <= 0){
							milgUseC =  Number(milgUse);
						}else if(Number(milgUse) > 0 &&  Number(milgUse) <= Number(totSumSellPrc) && Number(dpamtUse) > 0){
							milgUseC =  Number(milgUse);
							var  totPrNorm2 = Number(totSumSellPrc) - Number(milgUseC);
							if(Number(dpamtUse) > Number(totPrNorm2)){
								dpamtUseC = Number(totPrNorm2);
							}else{
								dpamtUseC = Number(dpamtUse);
							}
						}
					}

					if(data.schdInfo.DELI_CNCL_YN == 'Y'){
						$("#milgUse").text("적용하지 않음");
						$("#dpamtUse").text("적용하지 않음");
					}else{
						if(data.schdInfo.MILG_USE != "마일리지 없음" && data.schdInfo.MILG_USE != "적용하지 않음"){
							$("#milgUse").text(numberWithCommas(milgUseC)+"P");
						}else{
							$("#milgUse").text(data.schdInfo.MILG_USE);
						}

						if(data.schdInfo.DPAMT_USE != "예치금 없음" && data.schdInfo.DPAMT_USE != "적용하지 않음"){
							$("#dpamtUse").text(numberWithCommas(dpamtUseC)+"원");
						}else{
							$("#dpamtUse").text(data.schdInfo.DPAMT_USE);
						}

						//$("#totPrice").text(numberWithCommas(Number(totSumSellPrc) - Number(milgUseC) - Number(dpamtUseC)) + "원");
						//if(data.schdInfo.DPST_AMT != null){
						//	$("#totPrice").text(numberWithCommas(Number(data.schdInfo.DPST_AMT)) + "원");
						//}else{
						    $("#totPrice").text(numberWithCommas(payAmt) + "원");  //수정 By.신성훈
						//}
					}

					if(data.schdInfo.DELI_CNCL_YN == 'Y'){
						$("#itemAdd").hide();
					}else{
						if(nowPeriDeliId == periDeliId && nowPeriDeliDegNo == periDeliDegNo){
							$("#itemAdd").show();
						}else{
							$("#itemAdd").hide();
						}
					}
					}
				}


				//------ 레이어팝업을 보여주는 삼각아이콘을 보여줄 지 판단하기 -- 시작 --By. 신성훈
				var prodList = data.prodList;
			    if (prodList.length > 0) {
			        for (var i=0; i < prodList.length; i++) {
			            //1) 이번만 취소
			            var ulContent1 = $("#openDC1_" + prodList[i].PROD_CD).next().text();
			            var liPos1     = ulContent1.indexOf("li") ;

			            if (liPos1 == -1) {
			                $("#openDC1_" + prodList[i].PROD_CD).hide();
			            } else {
			            	$("#openDC1_" + prodList[i].PROD_CD).show();
			            }

			            //2) 매장배송
			            var ulContent2 = $("#openDC2_" + prodList[i].PROD_CD).next().text();
			            var liPos2     = ulContent2.indexOf("li") ;

			            if (liPos2 == -1) {
			                $("#openDC2_" + prodList[i].PROD_CD).hide();
			            } else {
			            	$("#openDC2_" + prodList[i].PROD_CD).show();
			            }

			            //3) 업체 택배 = 택배배송
			            var ulContent3 = $("#openDC3_" + prodList[i].PROD_CD).next().text();
			            var liPos3     = ulContent3.indexOf("li") ;

			            if (liPos3 == -1) {
			                $("#openDC3_" + prodList[i].PROD_CD).hide();
			            } else {
			            	$("#openDC3_" + prodList[i].PROD_CD).show();
			            }

			            //4) 매장 택배 = 특정점 배송
			            var ulContent4 = $("#openDC4_" + prodList[i].PROD_CD).next().text();
			            var liPos4     = ulContent4.indexOf("li") ;

			            if (liPos4 == -1) {
			                $("#openDC4_" + prodList[i].PROD_CD).hide();
			            } else {
			            	$("#openDC4_" + prodList[i].PROD_CD).show();
			            }

			            //5) 이번만 추가
			            var ulContent5 = $("#openDC5_" + prodList[i].PROD_CD).next().text();
			            var liPos5     = ulContent5.indexOf("li") ;

			            if (liPos5 == -1) {
			                $("#openDC5_" + prodList[i].PROD_CD).hide();
			            } else {
			            	$("#openDC5_" + prodList[i].PROD_CD).show();
			            }
			        }
			    }//------ 레이어팝업을 보여주는 삼각아이콘을 보여줄 지 판단하기 -- 끝 --By. 신성훈

			    if(data.schdInfo.DELI_CNCL_YN != 'Y'){
			    	if(data.setlCpnChanged == 'Y') alert("상품변경으로 인하여 적용예정인 쿠폰이 변경되었습니다.");
			    }

			}//if (jResult(data)) {
		});//.done(function(data) {

	 $.ajaxSetup({async: true});

} // function periDetail()

function resetSchd(){
	$('#periInfo').empty();
	$('#storeItemBox').addClass('hide');
	$('#onlineItemBox').addClass('hide');
	$('#outrangeItemBox').addClass('hide');
}

function cancelItem(itemSeq, cancelYn){
	var deliMsg1;
	var deliMsg2;

	if(cancelYn == 'Y'){
		deliMsg1 = '해당 상품을 이번만 취소하시겠습니까?';
		deliMsg2 = '취소 처리되었습니다.';
	}else{
		deliMsg1 = '해당 상품을 받으시겠습니까?';
		deliMsg2 = '상품 받기로 처리되었습니다';
	}

	if(!confirm(deliMsg1)){
		return;
	}

	var params = {
			periDeliId : $('input[name=periDeliId]').val(),
			periDeliDegNo : $('input[name=periDeliDegNo]').val(),
			periDeliProdSeq : itemSeq,
			itemCnclYn : cancelYn
	};

	$.getJSON("/mymart/api/cancelPeriSchdItem.do", params)
		.done(function(data) {
			if(jResult(data)){
				alert(deliMsg2);
				periDetail(params.periDeliId, params.periDeliDegNo);
			}
		});
}

function updateCmpl(obj){
	var params = {
			periDeliId : $('input[name=periDeliId]').val(),
			periDeliDegNo : $('input[name=periDeliDegNo]').val(),
			cmplMstId : $(obj).attr('CMPL_MST_ID'),
			grpng : $(obj).attr('GRPNG'),
			cmplType : $(obj).attr('CMPL_TYPE'),
			prodCd : $(obj).attr('PROD_CD'),
			itemCd : $(obj).attr('ITEM_CD'),
	};

	$.getJSON("/mymart/api/updateCmpl.do", params)
		.done(function(data) {
			if(jResult(data)){
				alert('저장되었습니다.');
			}
		});
}

function chkAll(flag){
	$('#addItemList').find('input[type=checkbox]').check(flag);
}

function oneCheck(periSeq){
	var cls = $('#chk_'+periSeq).attr('class');
	var status = cls.match("active");

	if (status) {
		document.getElementById("chk_"+periSeq).className = "check-data " + periSeq;
	} else {
		document.getElementById("chk_"+periSeq).className = "check-data " + periSeq + " active";
	}
}

function deleteChkItem(itemSeq){
	var params = {
			periDeliId : $('input[name=periDeliId]').val(),
			periDeliDegNo : $('input[name=periDeliDegNo]').val(),
	};

	if(!params.periDeliDegNo){
		alert('정기배송 스케쥴이 선택되지 않았습니다.');
		return;
	}
	if(!itemSeq || !confirm('삭제하시겠습니까?')){
		return;
	}
	if(itemSeq=='all'){
		$.ajaxSetup({async:false});

		var tmpClass;
		var tmpSeq = [];
		var delChkSeq;
		var cnt = 0;

	    $("[name='delChkbox']").each(function (i) {

	    	tmpClass = $(this).attr('class');

	    	if(tmpClass.match("active")){
	    		tmpSeq = $(this).attr('id').split("_");
	    		delChkSeq = tmpSeq[1];
	    		params.periDeliProdSeq = delChkSeq;
	    		$.getJSON("/mymart/api/removeOneTimeItem.do", params)
				.done(function(data) {
				});
	    		cnt++;
	    	}
	    });

		$.ajaxSetup({async:true});
		alert("삭제되었습니다.");
		periDetail(params.periDeliId, params.periDeliDegNo);
	}else{
		params.periDeliProdSeq = itemSeq;
		$.getJSON("/mymart/api/removeOneTimeItem.do", params)
			.done(function(data) {
				if(jResult(data)){
					alert("삭제되었습니다.");
					periDetail(params.periDeliId, params.periDeliDegNo);
				}
			});
	}
}

//var confirmOption = function(obj) {
//	var params = jParam(obj);
//	$.getJSON("/mymart/api/cancelPeriSchdItem.do", params)
//	.done(function(data) {
//		if(jResult(data)){
//			alert('변경되었습니다.');
//			periDetail(params.periDeliId, params.periDeliDegNo);
//		}
//	});
//};

function confirmOption(itemSeq, prodCd){
	var params = {
			itemCd : $("#itemCd_"+prodCd).val(),
		    ordQty :	$('#ordQty'+prodCd).val(),
			periDeliId : $('input[name=periDeliId]').val(),
			periDeliDegNo : $('input[name=periDeliDegNo]').val(),
			periDeliProdSeq : itemSeq
	};

	$.getJSON("/mymart/api/cancelPeriSchdItem.do", params)
	.done(function(data) {
		if(!!data && eval(data.result)){
			alert('변경되었습니다.');
			periDetail(params.periDeliId, params.periDeliDegNo);
		}
	});
}

function createNextDaily(){
	$('#nextPeriDeliDay').html("");
	var today = new Date();
	//var todayStr = today.getFullYear()+'.'+('0'+(today.getMonth()+1)).slice(-2)+'.'+('0'+today.getDate()).slice(-2);
	//var targetDay = new Date(today.setMonth(today.getMonth()+parseInt($('input[name=useMonth]:checked').val())));
	//				targetDay.setDate(targetDay.getDate() - 1);
	//var targetDayStr = targetDay.getFullYear()+'.'+('0'+(targetDay.getMonth()+1)).slice(-2)+'.'+('0'+targetDay.getDate()).slice(-2);
	var firstDeliDay;
	var tmpDeliDay;
	var deliStartDy;
	var firstDeliDayStr;
	var totalDays = '';

	var stdComp = 99;//비교 기준
	if($('input[name^=deliDay]:checked').length > 0){
		$('input[name^=deliDay]:checked').each(function(){
			var comp = $(this).attr('day')-today.getDay();
			if(comp < 3) comp+=7;//비교대상 요일이 3일 이전일 경우 7일 앞으로 보정해준다
			if(comp < stdComp){//오늘부터 3일 이후에 해당하는 첫 요일을 세팅
				firstDeliDay = new Date();
				//tmpDeliDay = new Date("09/30/2016");
				//if(firstDeliDay < tmpDeliDay){
					if(comp > 2){
						firstDeliDay = new Date(firstDeliDay.setDate(firstDeliDay.getDate()));
					}else{
						firstDeliDay = new Date(firstDeliDay.setDate(firstDeliDay.getDate()+7));
					}
				//}

				firstDeliDay = new Date(firstDeliDay.setDate(firstDeliDay.getDate()+comp));
				deliStartDy = firstDeliDay.getFullYear()+('0'+(firstDeliDay.getMonth()+1)).slice(-2)+('0'+firstDeliDay.getDate()).slice(-2);
				firstDeliDayStr = firstDeliDay.getFullYear()+'.'+('0'+(firstDeliDay.getMonth()+1)).slice(-2)+'.'+('0'+firstDeliDay.getDate()).slice(-2)+' ('+$(this).attr('txt')+')';
			}

			if(comp>2){
				stdComp = comp;	//더 최근 날짜를 쓰기 위해 차이를 저장
			}

		});
		$('#nextPeriDeliDay').html("다음 정기배송일 : <strong class='txt-wbrown'>"+ firstDeliDayStr+"</strong>");
		$('#deliStartDy').val(deliStartDy);
	}
}

function gotoProdZoom(prodCd, categoryId, adlYn){
	goProdZoom(categoryId, prodCd, '001', 'N', adlYn,'00001');
}

function prodDetailLink(obj, event) {
	var onclick = $(obj).closest('article').find('.prod-name a').attr('onclick'); //onclick으로 가져올때
	var section = $(obj).find('section').length;
	// location 링크 goProductDetail 로 가져올수 있도록 해야함
	if (!$(event.target).attr('href')) {
		if (onclick != null && onclick.length > 0) {
			if(section == '0'){
				var onclickArr = onclick.split(';');
				eval(onclickArr[0]); // onclick
			}
		}
	}
}


function otaList(pageNum){
	var params = {
			'currentPage' : pageNum,
			'CategoryId' : 'C2060008', //'C2060003',
			'SubCategoryId' : '',
			'mkdpSeq' : '000000000001', //'000000000011',
			'viewType' : 'L',
			'detailYN' : 'Y',
			'dispCd' : '',
			'divnSeq' : '',
			'periBasketType' : $('#periBasketType').val(),
			'strCd' : $('#strCd').val(),
			'zipSeq' : $('#zipSeq').val()
	};
	$("#currentPage").val(params.currentPage);

	if($("div[id=otaProd_"+params.currentPage+"]").html() ==  undefined && $('#itemAddList').length > 0){
		$.getJSON("/mymart/api/periOtaList.do", params)
		.done(function(data) {
			 if ( data != null &&  data.list!= undefined && data.list.length > 0) {
				 if (pageNum == 1 && Math.ceil(data.list[0].TOTAL_COUNT/4) == 1) {
					 $('.bx-prev').attr('class', 'bx-prev disabled');
					 $('.bx-next').attr('class', 'bx-next disabled');
				 } else if (pageNum == 1) {
					 $('.bx-prev').attr('class', 'bx-prev disabled');
				 } else if (pageNum == Math.ceil(data.list[0].TOTAL_COUNT/4)) {
					 $('.bx-prev').attr('class', 'bx-prev');
					 $('.bx-next').attr('class', 'bx-next disabled');
				 } else if(pageNum != 1 && pageNum != Math.ceil(data.list[0].TOTAL_COUNT/4)){
					 $('.bx-prev').attr('class', 'bx-prev');
					 $('.bx-next').attr('class', 'bx-next');
				 }

				 var productList = data.list;
				 var otaProductHtml = "";
				 $("#totAll").val(productList[0].TOTAL_COUNT);
				 otaProductHtml += "<div id='otaProd_"+params.currentPage+"' class='prod-list'>";
				 for (var i = 0 ; i < productList.length ; i++ ) {
					 otaProductHtml += "<div class='product-article'>";
					 if(productList[i].OPTION_YN == 'Y' && productList[i].OPTION_NM != null && productList[i].OPTION_NM != ""){
						 otaProductHtml += "	<span class='prod-option' >";
						 otaProductHtml += "		<param name='optionYn' value='"+productList[i].OPTION_YN+"' class='OPTION_YN'/>";
						 otaProductHtml += "		<param name='prodCd' value='"+productList[i].PROD_CD+"' class='PROD_CD'/>";
						 otaProductHtml += "		<param name='itemCd' value='"+productList[i].ITEM_CD+"' class='ITEM_CD' />";
						 otaProductHtml += "		<param name='optionNm' value='"+productList[i].OPTION_NM+"' class='OPTION_NM' />";
						 otaProductHtml += "		<param name='ordQty' value='1' class='ORD_QTY'  MIN_ORD_PSBT_QTY='"+productList[i].MIN_ORD_PSBT_QTY+"' MAX_ORD_PSBT_QTY='"+productList[i].MAX_ORD_PSBT_QTY+"' />";
						 otaProductHtml += "		<button type='button' class='layerpop-trigger2 changeItem' OPTION_YN='"+productList[i].OPTION_YN+"' data-trigger='layerpop-basket' title='옵션선택'>옵션</button>";
						 otaProductHtml += "	</span>";
					 }
					 otaProductHtml += "<div class='wrap-thumb'>";
					 otaProductHtml += "<a href='javascript:;' onclick='goProductDetailView(\""+productList[i].CATEGORY_ID+"\",\""+productList[i].PROD_CD+"\"); return false;'>";
				 	if(productList[i].SOUT_YN_REAL == 'Y'){
				 		otaProductHtml += "		<span class='prod-sout'><img src='"+ _LMCdnV3RootUrl +"/images/layout/img_soldout_208x208.png' alt='품절'></span>";
				 	}
				 	otaProductHtml += "		<img src='"+_LMCdnDynamicUrl+"/"+ productList[i].MD_SRCMK_CD.substring(0,5) +"/"+productList[i].MD_SRCMK_CD +"_1_208.jpg' onerror='javascript:showNoImage(this,204,204)'  alt='"+productList[i].PROD_NM+"'>";
				 	otaProductHtml += "</a>";
				 	otaProductHtml += "		<span class='wrap-tag'>";

					var promoProdIcon = [];
					if(productList[i].PROMO_PROD_ICON != null){
						promoProdIcon = productList[i].PROMO_PROD_ICON.split("|");
					}

					if(promoProdIcon.length > 0){
						for(var k=0;k<promoProdIcon.length;k++){
							if(promoProdIcon[k] != ""){
								var prodIcon = promoProdIcon[k].split(":");
								var orderNo = 	prodIcon[0];
								var iconType = 	prodIcon[1];
								var iconNm = 	prodIcon[2];
								var iconDesc = 	prodIcon[3];
								var iconExtra = 	prodIcon[4];
								var iconType_sub = iconType.substr(0,1);

								if(k < 1){
									if(orderNo == "04"){
										otaProductHtml += "			<span><img src='"+ _LMCdnStaticUrl +"/images/front/common/flag-s-"+ iconType +".gif' alt='온라인단독'></span>";
									}else if(iconType == 'won'){
										otaProductHtml += "			<span class='icon-goods-"+ iconType +"'><em class='money'>"+ numberWithCommas(iconDesc) +"</em></span>";
									}else if(iconType == "discount"){
										otaProductHtml += "			<span class='icon-goods-"+ iconType +"'><em class='number'>"+ iconDesc +"</em></span>";
									}else if(iconType == "bundle"){
										otaProductHtml += "			<span class='icon-goods-"+ iconType +"'><em class='plus'>"+ iconDesc +"</em></span>";
									}else{
										if(iconExtra.indexOf("-1") > -1){
											otaProductHtml += "<i class='icon-goods-type"+ iconExtra +"'><em class='number'>"+ iconDesc +"</em></i>";
										}else if(iconExtra.indexOf("-2") > -1){
											otaProductHtml += "<i class='icon-goods-type"+ iconExtra +"'><em class='money'>"+numberWithCommas( iconDesc )+"</em></i>";
										}else{
											if(iconType_sub =="t"){
											otaProductHtml += "<i class='icon-goods-"+ iconType +"'>"+ iconNm +"</i>";
											}else{
												otaProductHtml += "<i class='icon-goods-type"+ iconType +"'>"+ iconNm +"</i>";
											}
										}
									}
								}
							}
						}
					}
					otaProductHtml += "		</span>";
					otaProductHtml += "		<span class='wrap-band'>";
					if(productList[i].DELI_TYPE.substr(0,2) == '01'){
				 		otaProductHtml += "			<i class='icon-band-delivery1' >매장배송</i>";
				 	}
				 	if(productList[i].DELI_TYPE.substr(0,2) == '02'){
				 		otaProductHtml += "			<i class='icon-band-delivery2' >매장택배</i>";
				 	}
				 	if(productList[i].DELI_TYPE.substr(0,2) == '04'){
				 		otaProductHtml += "			<i class='icon-band-delivery4' >업체택배</i>";
				 	}
				 	if(productList[i].ICON_10 == '1'){
				 		otaProductHtml += "			<i class='icon-band-delivery6'>무료배송</i>";
				 	}
				 	otaProductHtml += "		</span>";

				 	otaProductHtml += "		<span class='prod-link'>";
				 	var wishActive = "";
				 	if(productList[i].WISH_CNT > 0){
				 		wishActive = " active";
				 	}

				 	otaProductHtml += "			<a href='javascript:;' class='dibs " + wishActive + "' data-button-name='btnAddWish' data-category-id='" + productList[i].CATEGORY_ID + "' data-product-code='" + productList[i].PROD_CD + "'><span class='hidden'>찜하기</span></a>";
				 	otaProductHtml += "			<a href='javascript:;' onclick='gotoProdZoom(\""+productList[i].PROD_CD+"\",\""+productList[i].CATEGORY_ID+"\",\""+productList[i].ADL_YN+"\");return false;' class='blank' title='새 창 열림'><span class='hidden'>새창 보기</span></a>";
				 	otaProductHtml += "		</span>";
				 	otaProductHtml += "</div>";
				 	otaProductHtml += "<div class='wrap-info'>";
				 	otaProductHtml += "		<p class='prod-name'><strong><a href='javascript:;' onclick='goProductDetailView(\""+productList[i].CATEGORY_ID+"\",\""+productList[i].PROD_CD+"\"); return false;'>"+productList[i].PROD_NM+"</a></strong></p>";
				 	if(productList[i].CURR_SELL_PRC != productList[i].PROM_MAX_VAL){
					 	otaProductHtml += "		<p class='prod-price'>판매가 <strong class='price-strike-type2'><span>"+ numberWithCommas(productList[i].CURR_SELL_PRC) +"<i></i></span><span class='won'>원</span></strong></p>";
				 	}
				 	otaProductHtml += "		<p class='price-max'>";
				 	if(productList[i].CURR_SELL_PRC != productList[i].PROM_MAX_VAL){
				 		otaProductHtml += "최저가";
				 	}else{
				 		otaProductHtml += "판매가";
				 	}
				 	otaProductHtml += "<span class='num-n'><em>"+ numberWithCommas(productList[i].PROM_MAX_VAL) +"</em></span>원";
				 	if(productList[i].CURR_SELL_PRC != productList[i].PROM_MAX_VAL){
					 	otaProductHtml += "<button type='button' class='layerpop-trigger2' data-trigger='layerpop-maxbenefit_"+productList[i].PROD_CD+"' onclick='layerpopTriggerBtn( this, event );'>최저가 내역</button>";
					 	otaProductHtml += "<script type='text/template' data-attach='layerpop-maxbenefit_"+productList[i].PROD_CD+"'>";
					 	otaProductHtml += "<section class='layerpop-type2 laypopup-locate-type1 layerpop-target layer-bottom'>";
					 	otaProductHtml += "		<div class='container'>";
					 	otaProductHtml += "			<h3 class='tit-h'>할인 내역</h3>";
					 	otaProductHtml += "			<div class='contents'>";
					 	otaProductHtml += "				<ul class='bul-circle' id='dcList'>";
						var promMaxValList = "";
						if(productList[i].PROM_MAX_VAL_LIST != null){
							promMaxValList = productList[i].PROM_MAX_VAL_LIST.split("@");
						}
						if(promMaxValList.length >0){
							for(var x=0;x<promMaxValList.length;x++){
	                            if(promMaxValList[x] != ""){
	                            	otaProductHtml += "<li>";
	                                var promMaxValListArr = promMaxValList[x].split("!");
	                                for(var j=0;j<promMaxValListArr.length;j++){
	                                	if(j==0) otaProductHtml += promMaxValListArr[j];
	                                	else {
	                                		otaProductHtml += '<em class="txt-red flt-right">' + promMaxValListArr[j] + '</em>';
	                                	}
	                                }
	                                otaProductHtml += "           </li>";
	                            }
							}
						}

						otaProductHtml += "				</ul>";
					 	otaProductHtml += "			</div>";
					 	otaProductHtml += "		</div>";
					 	otaProductHtml += "		<a href='javascript:;' class='btn-ico-close' title='레이어팝업 닫기'><i>할인 내역</i></a>";
					 	otaProductHtml += "</section>";
					 	otaProductHtml += "</script>";
				 	}
				 	otaProductHtml += "		</p>";
				 	otaProductHtml += "		<div class='wrap-spinner'>";
				 	otaProductHtml += "			<param name='periDeliId' ref='input[name=periDeliId]' />";
				 	otaProductHtml += "			<param name='periDeliDegNo' ref='input[name=periDeliDegNo]' />";
				 	otaProductHtml += "			<param name='prodCd' value='"+productList[i].PROD_CD+"' />";
				 	otaProductHtml += "			<param name='prodNm' value='"+productList[i].PROD_NM+"' />";
				 	otaProductHtml += "			<param name='itemCd' value='"+productList[i].ITEM_CD+"' />";
				 	otaProductHtml += "			<param name='ordQty' value='' class='ORD_QTY'  MIN_ORD_PSBT_QTY='"+productList[i].MIN_ORD_PSBT_QTY+"' MAX_ORD_PSBT_QTY='"+productList[i].MAX_ORD_PSBT_QTY+"'  />";
				 	otaProductHtml += "			<param name='nfomlVariation' value='"+productList[i].NFORL_VARIATION+"' /> ";
				 	otaProductHtml += "			<param name='ordQty' value='1'  />";
				 	otaProductHtml += "			<param name='extraQty' value='0'  />";
				 	otaProductHtml += "			<param name='categoryId' value='"+productList[i].CATEGORY_ID+"' />";
				 	otaProductHtml += "			<param name='deliType' value='"+productList[i].DELI_TYPE.substr(0,2)+"'/>";
				 	if(productList[i].SOUT_YN_REAL == 'Y'){
				 		otaProductHtml += "			<button type='button' class='btn-type4 addThis' onclick='javascript:fnSoutAlert();'>이번만 추가</button>";
				 	} else {
				 		var addThisZipSeq  = $('input[name=zipSeq]').val()
  				 	  otaProductHtml += "      <button title='변경' class='btn-type4 addThis' id='managePeri_" + "addThis" + "_" + productList[i].PROD_CD + "' onclick='managePeriCart.add(this);' type='button'  data-category-id='" + productList[i].CATEGORY_ID+ "' data-prod-cd='" + productList[i].PROD_CD + "' data-prod-type-cd='"+ productList[i].ONLINE_PROD_TYPE_CD +"' data-option-yn='"+productList[i].OPTION_YN+"' data-gubun='N' data-item-cd='"+productList[i].ITEM_CD+"'  data-str-cd='" + productList[i].STR_CD+ "'  data-zip-seq='" + addThisZipSeq + "'  data-peri-deli-prod-seq='0'  data-min-qty='" + productList[i].MIN_ORD_PSBT_QTY + "' data-max-qty='" + productList[i].MAX_ORD_PSBT_QTY + "' data-btn-obj-id='managePeri_" + "addThis" + "_" + productList[i].PROD_CD + "' data-call-back-func='addPeriAddThisBasketCallback'>이번만 추가</button>";

				 	}
				 	otaProductHtml += "		</div>";
				 	otaProductHtml += "</div>";
				 	otaProductHtml += "</div>";
				 }
				 otaProductHtml += "</div>";

				 $("#itemAddList").append(otaProductHtml);
			} else {
				$("#itemAdd").css("display", "none");
			}
		});
	} else {
		if (pageNum == 1 && Math.ceil($("#totAll").val()/4) == 1) {
			 $('.bx-prev').attr('class', 'bx-prev disabled');
			 $('.bx-next').attr('class', 'bx-next disabled');
		 } else if (pageNum == 1) {
			 $('.bx-prev').attr('class', 'bx-prev disabled');
		 } else if (pageNum == Math.ceil($("#totAll").val()/4)) {
			 $('.bx-prev').attr('class', 'bx-prev');
			 $('.bx-next').attr('class', 'bx-next disabled');
		 } else if(pageNum != 1 && pageNum != Math.ceil($("#totAll").val()/4)){
			 $('.bx-prev').attr('class', 'bx-prev');
			 $('.bx-next').attr('class', 'bx-next');
		 }
	}
}

function qtyValChk(cnt, val){
	var itemObj = $("#ordQty" + cnt);
	var maxQty = parseInt($(itemObj).attr("data-max-qty") || "999");
	var minQty = parseInt($(itemObj).attr("data-min-qty") || "1");
	if(val >= maxQty){
		alert("상품의 최대구매수량은 " + maxQty + "개 입니다.");
		$("#ordQty"+cnt).val(maxQty);
		return;
	}
	if(val == "" || val < minQty){
		alert("상품의 최소구매수량은 " + minQty + "개 입니다.");
		$("#ordQty"+cnt).val(minQty);
		return;
	}
}

function bxNext(){
	var totpageCnt = eval($("#totAll").val())/4;
	//alert(totpageCnt + "/" + $("#totAll").val() + "/" + $("#currentPage").val());

	if(totpageCnt > eval($("#currentPage").val())){
		var curPage = eval($("#currentPage").val()) + 1;
		otaList(curPage);
		$("div[id^=otaProd]").hide();
		$("div[id=otaProd_"+curPage+"]").show();
	}
}

function bxPrev(){
	var totpageCnt = eval($("#totAll").val())/4;
	if($("#currentPage").val() > 1){
		var curPage = eval($("#currentPage").val()) - 1;
		$("#currentPage").val(curPage)
		$("div[id^=otaProd]").hide();
		$("div[id=otaProd_"+curPage+"]").show();

		if (curPage == 1) {
			if(curPage != totpageCnt/4){
				 $('.bx-prev').attr('class', 'bx-prev disabled');
				 $('.bx-next').attr('class', 'bx-next');
			 } else {
				 $('.bx-prev').attr('class', 'bx-prev disabled');
			 }
		}
	}
}

//$('button.addThis').on('click', function(event){

function addThis(val){
	var params = jParam(val);
	if(!params.periDeliDegNo){
		alert('정기배송 스케쥴이 선택되지 않았습니다.');
		return;
	}

	if($("#NprodCnt").text() <= 0 && $("#VprodCnt").text() > 0){
		if(params.deliType == "01"){
			alert("매장상품 배송시간은 10:00~13:00 입니다.");
		}
	}

	if($("#MprodCnt").text() > 0){
		if(params.deliType != "02"){
			alert("특정점 배송상품만 함께 받기가 가능합니다.");
			return;
		}
	}else{
		if(params.deliType == "02"){
			alert("특정점 배송상품이 아닌 상품만 함께 받기가 가능합니다.");
			return;
		}
	}

	var optionYn = $(this).parents(document).find('param.OPTION_YN').val();
	var optionNm = $(this).parents(document).find('param.OPTION_NM').val();

	// 옵션이 있으면
	if(optionYn == 'Y' && (optionNm == null || optionNm == '') ){
		alert("옵션이 선택되지 않았습니다.");
		return;
	}


	$.getJSON("/mymart/api/addOneTimeItem.do", params)
		.done(function(data) {
			if(jResult(data)){
				alert("상품이 추가되었습니다.");
				periDetail(params.periDeliId, params.periDeliDegNo);
			}
		});
}
//});

//page on load
$(function() {
	//선택된 정기배송코드 세팅
	var periDeliId = $('input[name=periDeliId]').val();
	var periDeliDegNo = $('input[name=periDeliDegNo]').val();

	//기존 건 선택 처리
	if(periDeliId != ""){
		$('div.img-select-type2 > a').html($('a[periDeliId='+periDeliId+']').html());
	}

	otaList(1);

	// 메인페이지로
	$("#main_link").click(function(e) {
		window.location.href = "/delivery/main.do";
	});

	//기존 건 클릭
	$('ul.periAddr > li > a').on("click", function(){
		getExistingPeri($(this).attr('periDeliId'));
	});

	$("#nowPeriDeliId").val(periDeliId);
	$("#nowPeriDeliDegNo").val(periDeliDegNo);
	periDetail(periDeliId, periDeliDegNo);

	//상품 취소
	$('ul.list-frame').on("click", 'button.cxlItem', function(){
		if($(this).hasClass('skipThis')){ //배송받기
			cancelItem(this, 'N');
		}
		if($(this).hasClass('normal')){ //이번만 취소
			cancelItem(this, 'Y');
		}
		resetSchd();
	});

	$(document).on('click', '#userguide_link', function(){
		window.open("/delivery/popup/user_guide.do", "DeliveryUserGuidePopup", "width=920px, height=720px");
	});

	$(document).on('click', '#faq_link', function(){
		location.href = "/happycenter/faqMain.do";
	});

	$(document).on('click', '#main_link', function(){
		location.href = "/delivery/main.do";
	});

	$(document).on('click', 'button.optionConfirm1', function(){
		$("#itemCd_"+exProdCd).val($('#option_select option:selected').val());
		$('#ordQty'+exProdCd).val($("#optItemCount").val());
		confirmOption(exItemSeq , exProdCd);
	});


	//수량UP
	$(document).on("click", 'button.sp-plus1', function(){
		var basketQty = $('#optItemCount');
		var ordQty = parseInt(basketQty.val());
		var limit = parseInt($("#maxOtQty").val());

		if(ordQty < limit){
			ordQty++;
		}else{
			alert("해당 상품 수량은 최대 "+ limit +" 개까지 가능합니다.");
			ordQty = limit;
		}
		basketQty.val(ordQty);
	});

	//수량DOWN
	$(document).on("click", 'button.sp-minus1', function(){
		var basketQty = $('#optItemCount');
		var ordQty = parseInt(basketQty.val());
		var limit = parseInt($("#minOtQty").val());

		if(ordQty > limit){
			ordQty--;
		}else{
			alert("해당 상품 수량은 최소 "+ limit +" 개 이상부터 가능합니다.");
			ordQty = limit;
		}
		basketQty.val(ordQty);
	});

	$(document).on('change', '.select-type1 select', function (event) {
		var select_name = $(this).children('option:selected').text();
		$(this).siblings('label').text(select_name);
	});

	$(document).on('click', 'button.rmvItem', function(){
		var itemSeq = $(this).attr('PERI_DELI_PROD_SEQ');
		deleteChkItem(itemSeq);
	});

	$(document).on('click', 'select#deliveryCouponSelect, select#payCouponSelect', function(){
		if($(this).attr('id') == 'deliveryCouponSelect') $('select#payCouponSelect').val('');
		if($(this).attr('id') == 'payCouponSelect') $('select#deliveryCouponSelect').val('');
		//alert('쿠폰 적용 필드 길이가 부족해요');
	});

	$(document).on('change', '#deliTermSelect', function(){
		$("#dailychk").val("Y");
		if($('#deliTermSelect option:selected').val() == "30"){
			$(".closeday").hide();
			$("input[name^=deliDay]").parent().addClass("active");
			$("input[name^=deliDay]").prop("checked",true);
		}else{
			$(".closeday").show();
			$("input[name^=deliDay]").parent().removeClass("active");
			$("input[name^=deliDay]").prop("checked",false);
		}
		createNextDaily();
	});

	$(document).on('click', 'input[name^=deliDay]', function(){
		$("#dailychk").val("Y");
		if($('#deliTermSelect option:selected').val() == "30"){
			alert("상품주기를 매일로 선택하셨습니다. \n상품주기를 먼저 변경해주세요.");
			$("input[name^=deliDay]").parent().addClass("active");
			$("input[name^=deliDay]").prop("checked",true);
			return;
		}
		createNextDaily();
	});


	$('#optionTable').on('click', 'input[type=radio], input[type=checkbox]', function (event) {
		$(this).check(this.checked);
	});

	$('ul.list-frame').on("click", 'button.up', function(){
		var basketQty = $(this).parent().siblings('input.ORD_QTY');
		var ordQty = parseInt(basketQty.val());
		var limit = parseInt($(this).attr('MAX_ORD_PSBT_QTY'));
		if(ordQty < limit){
			ordQty++;
		}else{
			alert("해당 상품 수량은 최대 "+ limit +" 개까지 가능합니다.");
			ordQty = limit;
		}
		basketQty.val(ordQty);
		//$(this).parents('article').find('param.ORD_QTY').val(ordQty);
	});

	$('ul.list-frame').on("click", 'button.down', function(){
		var basketQty = $(this).parent().siblings('input.ORD_QTY');
		var ordQty = parseInt(basketQty.val());
		var limit = parseInt($(this).attr('MIN_ORD_PSBT_QTY'));

		if(ordQty > limit){
			ordQty--;
		}else{
			alert("해당 상품 수량은 최소 "+ limit +" 개 이상부터 가능합니다.");
			ordQty = limit;
		}
		basketQty.val(ordQty);
		//(this).parents('article').find('param.ORD_QTY').val(ordQty);
	});

/*	$('button.addThis').on('click', function(event){
		var params = jParam(this);
		if(!params.periDeliDegNo){
			alert('정기배송 스케쥴이 선택되지 않았습니다.');
			return;
		}

		if($("#NprodCnt").text() <= 0 && $("#VprodCnt").text() > 0){
			if(params.deliType == "01"){
				alert("매장상품 배송시간은 10:00~13:00 입니다.");
			}
		}

		if($("#MprodCnt").text() > 0){
			if(params.deliType != "02"){
				alert("특정점 배송상품만 함께 받기가 가능합니다.");
				return;
			}
		}

		var optionYn = $(this).parents(document).find('param.OPTION_YN').val();
		var optionNm = $(this).parents(document).find('param.OPTION_NM').val();

		// 옵션이 있으면
		if(optionYn == 'Y' && (optionNm == null || optionNm == '') ){
			alert("옵션이 선택되지 않았습니다.");
			return;
		}


		$.getJSON("/mymart/api/addOneTimeItem.do", params)
			.done(function(data) {
				if(jResult(data)){
					alert("상품이 추가되었습니다.");
					periDetail(params.periDeliId, params.periDeliDegNo);
				}
			});
	});*/
});


function addPeriBasketCallback(obj, event){

	var params = {
			itemCd : $(obj).data("periBasketItem").itemCd,
		    ordQty : $(obj).data("periBasketItem").bsketQty,
			periDeliId : $('input[name=periDeliId]').val(),
			periDeliDegNo : $('input[name=periDeliDegNo]').val(),
			periDeliProdSeq : $(obj).data("periBasketItem").periDeliProdSeq,
			nfomlVariation : ''
	};

	if($(obj).data("periBasketItem").nfomlVariation != null && $(obj).data("periBasketItem").nfomlVariation != '' &&
	   $(obj).data("periBasketItem").nfomlVariation != "undefined"){
		params.nfomlVariation = $(obj).data("periBasketItem").nfomlVariation;
	}

	$.ajax({
		type: "POST",
		cache: false,
		url: "/mymart/api/cancelPeriSchdItem.do",
		data: params,
		dataType: "json",
		success: function(data) {
			if(!!data && eval(data.result)){
				alert('변경되었습니다.');
				periDetail(params.periDeliId, params.periDeliDegNo);
			}
		}
	});

	$("#opt_peri_cart").remove();

}

function addPeriAddThisBasketCallback(obj, event){

	var params = {
			prodCd : $(obj).data("periBasketItem").prodCd,
			itemCd : $(obj).data("periBasketItem").itemCd,
		    ordQty : $(obj).data("periBasketItem").bsketQty,
			periDeliId : $('input[name=periDeliId]').val(),
			periDeliDegNo : $('input[name=periDeliDegNo]').val(),
			periDeliProdSeq : $(obj).data("periBasketItem").periDeliProdSeq,
			nfomlVariation : '',
			categoryId : $(obj).data("periBasketItem").categoryId,
			extraQty : "0"
	};
	if($(obj).data("periBasketItem").nfomlVariation != null && $(obj).data("periBasketItem").nfomlVariation != '' &&
	   $(obj).data("periBasketItem").nfomlVariation != "undefined"){
		params.nfomlVariation = $(obj).data("periBasketItem").nfomlVariation;
	}
	var btnObjId = $(obj).data("periBasketItem").btnObjId;;
	var deliType = $("#" + btnObjId).parents("DIV").find('param[name=deliType]').val();


	if(!params.periDeliDegNo){
		alert('정기배송 스케쥴이 선택되지 않았습니다.');
		return;
	}

	if($("#NprodCnt").text() <= 0 && $("#VprodCnt").text() > 0){
		if(deliType == "01"){
			alert("매장상품 배송시간은 10:00~13:00 입니다.");
		}
	}

	if($("#MprodCnt").text() > 0){
		if(deliType != "02"){
			alert("특정점 배송상품만 함께 받기가 가능합니다.");
			return;
		}
	}else{
		if(deliType == "02"){
			alert("특정점 배송상품이 아닌 상품만 함께 받기가 가능합니다.");
			return;
		}
	}

	if(confirm("해당 상품을 추가하시겠습니까?")){
		$.ajax({
			type: "POST",
			cache: false,
			url: "/mymart/api/addOneTimeItem.do",
			data: params,
			dataType: "json",
			success: function(data) {
				if(!!data && eval(data.result)){
					alert('변경되었습니다. ');
					periDetail(params.periDeliId, params.periDeliDegNo);
				}
			}
		});

		$("#opt_peri_cart").remove();
	}
}