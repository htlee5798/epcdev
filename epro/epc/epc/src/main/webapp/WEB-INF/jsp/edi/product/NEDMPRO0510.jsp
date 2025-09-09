<%--
	Page Name 	: NEDMPRO0510.jsp
	Description : 원가변경요청 조회
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2025.03.17		yja				최초생성		
--%>
<%@ include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ include file="./CommonProductFunction.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>원가변경요청 조회</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<style>
	table .tdr{text-align:right;}
	table .tdc{text-align:center;}
</style>

<script type="text/javascript" >
let rowCount = 0;
let maxSelRow = 100;

let srchProdMart = "";		//판매코드 검색조건_마트상품
let srchProdMaxx = "";		//판매코드 검색조건_MAXX상품
let srchProdSuper = "";		//판매코드 검색조건_슈퍼상품
let srchProdCfc = "";		//판매코드 검색조건_CfC상품
$(function(){
	
	//신선상품여부 변경이벤트
	$(document).on("change", "input[name='srchFreshStdYn']", function(){
		//신선일 경우에만 과세유형 필수선택
		var val = $.trim($(this).val());
		
		if(val == "Y"){
			$("#srchTaxFg").parent().prev().find("span.star").css("display", "inline-block");
		}else{
			$("#srchTaxFg").parent().prev().find("span.star").css("display", "none");
		}
		
		$("#srchTaxFg").val("");
	});
	
	let tody = new Date();
	let nextDy = new Date(tody.getFullYear(), tody.getMonth(), tody.getDate() + 1);
	let nextDy_yy = nextDy.getFullYear();
	let nextDy_mm = ("0"+(nextDy.getMonth()+1)).slice(-2);
	let nextDy_dd = ("0"+nextDy.getDate()).slice(-2);
	
	//요청내역 list datepicker click event 동적 binding
	$(document).on("click", "#dataListbody img.datepicker", function(){
		var dayInput = $(this).prev("input")[0];
		var objId = dayInput.id;
		var objVal = $.trim(dayInput.value);
		
		var minDt = nextDy_yy+nextDy_mm+nextDy_dd;
		
		openCalSetDt("reqDetailForm."+objId, objVal, "fncCallBackCalendar", minDt);
	});
	
	$("#srchChgReqCostDt").next("img.datepicker").click(function(){
		var dayInput = $(this).prev("input")[0];
		var objId = dayInput.id;
		var objVal = $.trim(dayInput.value);
		
		var minDt = nextDy_yy+nextDy_mm+nextDy_dd;
		
		openCalSetDt("searchForm."+objId, objVal, "fncCallBackCalendar", minDt);
	});
	
	//변경사유 변경
	$(document).on("change", "#dataListbody select[name='costRsnCd']", function(){
		var row = $(this).closest("tr");
		var rowId = row.index();
		var costRsnCd = $.trim(this.value);		//변경사유
		var freshStdYn = $.trim($(this).closest("tr").find("input[name='freshStdYn']").val());		//신선규격상품구분
		
		//변경원가 입력 안하고 먼저 변경사유 입력시 validation 발생 
	 	var chgReqCost = parseCost($.trim(row.find("input[name='chgReqCost']").val()));
		var orgCost = parseCost($.trim(row.find("input[name='orgCost']").val()));
		
		//원가 금액 validation 상태
		if(!fncChkCostChgRsn(row)) return false;

		//--------------------------------------------------------------------
		//변경상세사유 selectbox 구성
		_setOptCostRsnDtlCd($(this), freshStdYn, costRsnCd);
	});
	
	//변경상세사유 선택
	$(document).on("focus", "#dataListbody select[name='costRsnDtlCd']", function(){
		var costRsnCdObj = $(this).closest("tr").find("select[name='costRsnCd']"); 
		var costRsnCd = $.trim(costRsnCdObj.val());		//변경사유
		
		if($.trim(costRsnCd) == ""){
			alert("변경사유를 먼저 선택해주세요.");
			costRsnCdObj.focus();
			$(this).val("");
			return;
		}
	});
	
	//금액영역 blur comma입력
	$(document).on("blur", ".amt", function(){
		this.value = setComma(this.value);		
	});
	
	//금액입력란 focus 시 comma 제거
	$(document).on("focus", ".amt", function(){
		this.value = this.value.replace(/[^0-9.]/g,"");		
	});
	
	//금액 입력 시, 증감율 계산
	$(document).on("change", "input[name='orgCost'], input[name='chgReqCost']", function(){
		fncCalcIncDecRate(this);
	});
	
	//row data 변경 시, row status 변경
	$(document).on("change", "#dataListbody input, #dataListbody select", function(){
		if(this.name == "cbox") return;
		var currRowStat = $(this).closest("tr").attr("data-rowStat");
		if(currRowStat == "R"){
			$(this).closest("tr").attr("data-rowStat", "U");
		}
	});
	
	//검색조건 변경 시, 그리드 초기화 확인 이벤트
	let bfSrch = "";
	let afSrch = "";
	$("#srchVenCd, #srchNbPbGbn, #srchPurDept, input[name='srchFreshStdYn'], #srchTaxFg").on("focus", function(){
		bfSrch = $.trim($(this).val());
	}).change(function(){
		afSrch = $.trim($(this).val());
		
		//데이터 있을 때만 체크
		var datalen = $("#dataListbody tr").length;
		if(datalen == 0){
			//--------------------------------
			//데이터 없는 경우, 변환값 바로적용
			switch(this.id){
				case "srchVenCd":	//협력사코드
					//구매조직 셋팅
					eventSelChkVenPurDept(afSrch);
					break;
				case "srchPurDept":	//구매조직
					//공문영역 control
					fncDcAreaControl(afSrch);
					//구매 조직 변경 시, 판매코드 팝업 제한조건 셋팅
					fncSetSrchProdChans();
					break;
				default:
					break;
			}
			//--------------------------------
			return;
		}
		
		//이전선택값 존재하면서 이후선택값이 달라졌을 경우,
		if(bfSrch != "" && bfSrch != afSrch){
			if(!confirm("검색조건 변경 시, 입력한 대상상품 리스트가 초기화 됩니다.\n진행하시겠습니까?")){
				//취소 시, 이전선택값 재셋팅
				$(this).val(bfSrch);
				return; 
			}
			
			//--------------------------------
			//데이터 있는 경우, 진행 confirm 확인 후 적용
			switch(this.id){
				case "srchVenCd":	//협력사코드
					//구매조직 셋팅
					eventSelChkVenPurDept(afSrch);
					break;
				case "srchPurDept":	//구매조직
					//공문영역 control
					fncDcAreaControl(afSrch);
					//구매 조직 변경 시, 판매코드 팝업 제한조건 셋팅
					fncSetSrchProdChans();
					break;
				default:
					break;
			}
			//--------------------------------
			
			//대상상품리스트 초기화
			setTbodyInit("dataListbody");
		}
	});
	
	//체크박스 전체선택
	$("#chkAll").unbind().click(function(){
		var all = $(this).is(":checked");
		if(all){
			$("#dataListbody tr").find("input[name='cbox']").prop("checked", true);
		}else{
			$("#dataListbody tr").find("input[name='cbox']").prop("checked", false);
		}
	});	
	
	//row 체크박스 선택
	$(document).on("click","#dataListbody input[name='cbox']", function(){
		var dataLen = $("#dataListbody input[name='cbox']").length;
		var chkedLen = $("#dataListbody input[name='cbox']:checked").length;
		
		if(chkedLen == dataLen){
			$("#chkAll").prop("checked", true);
		}else{
			$("#chkAll").prop("checked", false);
		}
	});
	
	//화면진입시 영역 control -----------------------------
	//공문생성 선택값 disabled
	fncDcAreaControl();

});

//금액 비교를 위해 String 금액으로 변환 
function parseCost(val) {
	return Number(val.replace(/,/g, ''));
}
//공문관련 영역 control
function fncDcAreaControl(purDept){
	var val = $.trim(purDept);
	
	//구매조직 값 없을경우 비활성화
	if(val == ""){
		$("input[name='dcCreGbn']").attr("checked", false);
		$("input[name='dcCreGbn']").attr("disabled", true);
		return;
	}
	
	if(val == "KR04" || val == "KR05"){	//슈퍼 or CS
		//마트선택불가
		$("input[name='dcCreGbn'][value='MT']").attr("disabled", true).attr("checked", false);
		$("input[name='dcCreGbn'][value!='MT']").attr("disabled", false);
		
		//기본 선택
		if(val == "KR04"){			//슈퍼
			$("input[name='dcCreGbn'][value='SP']").attr("checked", true);	
		}else if(val == "KR05"){	//CS
			$("input[name='dcCreGbn'][value='CS']").attr("checked", true);
		}
	}else{								//마트
		//마트만선택가능
		$("input[name='dcCreGbn'][value!='MT']").attr("disabled", true).attr("checked", false);
		$("input[name='dcCreGbn'][value='MT']").attr("disabled", false).attr("checked", true);
	}
}

//날짜변경 callback
function fncCallBackCalendar(tgObj, cbData){
	if(tgObj == undefined || tgObj == null) return;
	
	let tgId = $.trim(tgObj.id);
	if(tgId.startsWith("srch")) return;	//검색조건 내 캘린더일 경우 return
	
	//Row 상태 변경처리
	let tgRow = tgObj.closest("tr");
	if(tgRow == undefined || tgRow == null) return;
	
	//현재 rowStat 
	var currStat = $.trim(tgRow.attr("data-rowStat"));
	
	//기등록된 데이터일 경우
	if(currStat == "R"){
		//수정상태 변경
		tgRow.attr("data-rowStat", "U");
	}
}

//검색조건 확인
function fncValidate(){
	var srchPurDept = $.trim($("#srchPurDept").val());	//구매조직
	var srchVenCd = $.trim($("#srchVenCd").val());		//파트너사코드
	var srchNbPbGbn = $.trim($("#srchNbPbGbn").val());	//NB,PB 구분
	var srchFreshStdYn = $.trim($("input[name='srchFreshStdYn']:checked").val());	//신선상품구분
// 	var srchProdPatFg = $.trim($("#srchProdPatFg").val());	//상품유형
	var srchTaxFg = $.trim($("#srchTaxFg").val());		//과세유형
	
	//파트너사코드 누락
	if(srchVenCd == ""){
		alert("파트너사는 필수 선택 항목입니다.");
		$("#srchVenCd").focus();
		return false;
	}
	
	//구매조직 누락
	if(srchPurDept == ""){
		alert("구매조직은 필수 선택 항목입니다.");
		$("#srchPurDept").focus();
		return false;
	}
	
	//NB,PB구분 누락
	if(srchNbPbGbn == ""){
		alert("NB/PB 구분은 필수 선택 항목입니다.");
		$("#srchNbPbGbn").focus();
		return false;
	}
	
	//신선상품여부 누락
	if(srchFreshStdYn == ""){
		alert("신선상품여부는 필수 선택 항목입니다.");
		$("input[name='srchFreshStdYn']").eq(0).focus();
		return false;
	}
	
// 	//상품유형 누락
// 	if(srchProdPatFg == ""){
// 		alert("상품유형은 필수 선택 항목입니다.");
// 		$("#srchProdPatFg").focus();
// 		return false;
// 	}
	
	//신선일 경우, 과세유형 필수
	if(srchFreshStdYn == "Y" && srchTaxFg == ""){
		alert("신선 상품일 경우, 과세 유형은 필수 선택 항목입니다.");
		$("#srchTaxFg").focus();
		return false;
	}
	
	
	return true;
}

//조회
function eventSearch(){
	//검색조건 확인
	if(!fncValidate()) return;
	
	var searchInfo = {};
	$("#searchForm").find("input, select").each(function(){
		if(this.name != null && this.name != ""){
			if(this.type == "radio"){
				if($(this).is(":checked")){
					searchInfo[this.name] = $.trim($(this).val());
				}
			}else{
				if($(this).hasClass("day")){
					searchInfo[this.name] = $.trim($(this).val()).replace(/\D/g,"");
				}else{
					searchInfo[this.name] = $.trim($(this).val());
				}
			}
		}
	});
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/selectTpcProdChgCostDetailView.json"/>',
		data : JSON.stringify(searchInfo),
		success : function(data) {
			//datalist 초기화
			setTbodyInit("dataListbody");
			//체크박스 전체선택 초기화
			$("#chkAll").prop("checked", false);
			
			//footer Setting 
			if(data.itemList.length > 0){
				// 조회 성공시 
				$('#resultMsg').text('<spring:message code="msg.common.success.select"/>');
			}else{
				//조회된 건수 없음
				$('#resultMsg').text('<spring:message code="msg.common.info.nodata"/>');
			}
			
			//데이터 셋팅
			fncSetData(data);
		},
		error : function(request, status, error, jqXHR) {
			// 요청처리를 실패하였습니다.
			$('#resultMsg').text('msg.common.fail.request');
		} 
	});
}

//datalist setting
function fncSetData(json){
	var itemList = json.itemList;
	
	//아이템정보
	if(itemList != null && itemList.length > 0){
		//row index setting
		for(var i=0; i<itemList.length; i++){
			itemList[i].count = ++rowCount;
			itemList[i].orgCostFmt = ($.trim(itemList[i].orgCost) != "")?setComma(itemList[i].orgCost):"";
			itemList[i].chgReqCostFmt = ($.trim(itemList[i].chgReqCost) != "")?setComma(itemList[i].chgReqCost):"";
		}
		
		//datalist setting
		$("#dataListTemplate").tmpl(itemList).appendTo("#dataListbody");
		
		//변경상세사유 셋팅
		for(var j = 0; j < itemList.length; j++){
			var prodPatFg = itemList[j].prodPatFg;		//상품유형구분코드
			var costRsnCd = itemList[j].costRsnCd;		//변경사유코드
			var costRsnDtlCd = itemList[j].costRsnDtlCd;//변경상세사유코드
			var count = itemList[j].count;				//row index
			var dcNum = itemList[j].dcNum;				
			
			if(dcNum != null && dcNum != ""){
				$("#costRsnCd" + count).prop("disabled", true);
				$("#costRsnDtlCd" + count).prop("disabled", true);
			}
			
			//요청사유 
			if(itemList[j].costRsnCd != null){
				$("#costRsnCd" + count).val(itemList[j].costRsnCd);
			}
			
			//요청상세사유 obj
			var rsnDtlObj = $("#dataListbody tr.r"+count).find("select[name='costRsnDtlCd']");
			//요청상세사유 select option 구성
			_setOptCostRsnDtlCd(rsnDtlObj, prodPatFg, costRsnCd);
			
			//요청상세사유 값 setting
			$("#dataListbody tr.r"+count).find("select[name='costRsnDtlCd']").val(costRsnDtlCd);
			
			//요청상세사유
			if(itemList[j].costRsnDtlCd != null){
				$("#costRsnDtlCd" + count).val(itemList[j].costRsnDtlCd);
			}
		}
		
		
	}else{
		//datalist 초기화
		setTbodyInit("dataListbody");
	}
	
}

//복사
function eventCopy(obj){
	var tgObj = $(obj).closest("tr");
	
	var saveInfo = {};
	var reqNo = tgObj.find("input[name='reqNo']").val();				//요청번호
	var srcmkCd = tgObj.find("input[name='srcmkCd']").val();			//판매코드
	var prodCd = tgObj.find("input[name='prodCd']").val();				//상품코드
	var seq = tgObj.find("input[name='seq']").val();					//순번
	var prStat = $.trim(tgObj.find("input[name='prStat']").val());		//진행상태
	var rnum = $.trim(tgObj.find("span[name='rnum']").text());			//행번호
	var dcNum = $.trim(tgObj.find("input[name='dcNum']").val());		//계약번호
	var purDept = $.trim($("#srchPurDept").val());						//구매조직
	
	//진행상태확인
	if("03" != prStat){
		alert("반려된 건만 요청정보 복사가 가능합니다.");
		return;
	}
	
	saveInfo.reqNo = reqNo;           //요청번호
	saveInfo.srcmkCd = srcmkCd;       //판매코드
	saveInfo.prodCd = prodCd;         //상품코드
	saveInfo.seq = seq;               //순번
	saveInfo.purDept = purDept;       //구매조직
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/insertCopyTpcProdChgCost.json"/>',
		data : JSON.stringify(saveInfo),
		success : function(data) {
			var msg = data.msg;
			
			if("success" == msg){
				alert("복사되었습니다.");
				eventSearch();
			}else{
				var errMsg = $.trim(data.errMsg) != "" ? data.errMsg : "처리 중 오류가 발생했습니다.";
				alert(errMsg);
			}
			
		}
	});
}


//엑셀다운로드
function eventExcelDown(){
	var form = document.searchForm;
	
	//검색조건 확인
	if(!fncValidate()) return;
	
	form.action = "<c:url value='/edi/product/selectTpcProdChgCostDetailExcelDownload.do' />";
	form.submit();
}

//판매코드 찾기 검색조건 팝업 OPEN
function fncOpenSrchPopSrcmkCd(obj){
	var pData = [];
	pData.trNum = "search";
	pData.entpCd = $.trim($("#srchVenCd").val());			//선택한 파트너사 코드 있을 경우, 해당 파트너사의 판매코드만 조회
	pData.srcmkCd = $.trim($("#srchSrcmkCd").val());		//입력한 판매코드 있을 경우 셋팅
// 	pData.prodPatFg = $.trim($("#srchProdPatFg").val());	//상품패턴 1-규격상품만 원가변경요청가능
	pData.srchChgOrgCostYn = "Y";							//원가변경 가능 대상 상품만 조회
	pData.srchProdMart	= srchProdMart;		//마트
	pData.srchProdMaxx	= srchProdMaxx;		//맥스
	pData.srchProdSuper = srchProdSuper;	//슈퍼
	pData.srchProdCfc	= srchProdCfc;		//cfc
	
	var params = "";
	Object.keys(pData).forEach(function(k, i){
	  params = params+(i===0?"?":"&")+k+"="+pData[k];  
	});
	
	var targetUrl = "<c:url value='/edi/product/selSrcmkCdPopup.do'/>"+params;
	Common.centerPopupWindow(targetUrl, 'sellCdPopup', {width: 980, height: 700});
}


//판매코드 찾기 팝업 callback
function setSellCd(json){
	if(json == null){
		alert("상품 데이터가 유효하지 않습니다.\n관리자에게 문의하세요.");
		return;
	}
	
	//callback data
	var trNum = json.trNum;	 //대상 row구분 class
	var srcmkCd = json.srcmkCd; //판매코드
	var prodCd = json.prodCd; //상품코드
	var prodNm = json.prodNm; //상품명
	var l1Cd = json.l1Cd;	 //대분류코드
	var l2Cd = json.l2Cd;	 //중분류코드
	var l3Cd = json.l3Cd;	 //소분류코드
	var l1Nm = json.l1Nm;	 //대분류코드명
	var l2Nm = json.l2Nm;	 //중분류코드명
	var l3Nm = json.l3Nm;	 //소분류코드명
	var orgCost = $.trim(json.orgCost)!=""?$.trim(json.orgCost):"0";	//기존원가
	var orgCostFmt = setComma(orgCost);	//기존원가 금액formatting
	var prodPatFg = json.prodPatFg;	//상품유형구분
	var ordUnit = json.ordUnit;	//기본단위

	//검색조건
	if(trNum == "search"){
		$("#searchForm #srchSrcmkCd").val(srcmkCd);
	}
}

<%--
//등록화면으로 이동
function fncMoveToWrt(obj){
	var tgObj = $(obj).closest("tr");
	var srchPurDept = $.trim(tgObj.find("input[name='purDept']").val());		//구매조직
	var srchNbPbGbn = $.trim(tgObj.find("input[name='nbPbGbn']").val());		//NB,PB구분
	var srchVenCd = $.trim(tgObj.find("input[name='venCd']").val());			//협력사코드
	
	var srchProdPatFg = $.trim(tgObj.find("input[name='prodPatFg']").val());	//상품유형
	var srchTaxFg = $.trim(tgObj.find("input[name='taxFg']").val());		//과세유형
	
	
	$("#hiddenForm input[name='srchPurDept']").val(srchPurDept);
	$("#hiddenForm input[name='srchNbPbGbn']").val(srchNbPbGbn);
	$("#hiddenForm input[name='srchVenCd']").val(srchVenCd);
	
	$("#hiddenForm input[name='srchProdPatFg']").val(srchProdPatFg);
	$("#hiddenForm input[name='srchTaxFg']").val(srchTaxFg);
	
	
	var f = document.hiddenForm;
	f.action = "<c:url value='/edi/product/NEDMPRO0500.do'/>";
	f.submit();
}
--%>

//변경상세사유 option 셋팅
function _setOptCostRsnDtlCd(obj, freshStdYn, costRsnCd){
	//변경 상세사유 option clear
	$(obj).closest("tr").find("select[name='costRsnDtlCd']").find("option[value!='']").remove();
	
	var optionList = ${optionList};
	
	
	var optHtml = "";			//변경상세사유 select option html
	
	if($.trim(freshStdYn).toUpperCase() == "Y"){		//신선규격상품일경우
		switch(costRsnCd){
		case "01":	//인상
			for(var i =0; i<optionList.length; i++){
				if(optionList[i].LET_4_REF == "1"){
					optHtml += "<option value="+optionList[i].CODE_ID+">"+optionList[i].CODE_NAME+"</option>\n";
				}
			}			
			break;
		case "02":
			for(var i =0; i<optionList.length; i++){
				if(optionList[i].LET_4_REF == "2"){
					optHtml += "<option value="+optionList[i].CODE_ID+">"+optionList[i].CODE_NAME+"</option>\n";
				}
			}		
			break;
		default: break;
		}
	}else{						//상품유형 - 일반규격
		switch(costRsnCd){
			case "01":	//인상
				for(var i =0; i<optionList.length; i++){
					if(optionList[i].LET_1_REF == "1"){
						optHtml += "<option value="+optionList[i].CODE_ID+">"+optionList[i].CODE_NAME+"</option>\n";
					}
				}
				break;
			case "02":	//인하
				for(var i =0; i<optionList.length; i++){
					if(optionList[i].LET_1_REF == "2"){
						optHtml += "<option value="+optionList[i].CODE_ID+">"+optionList[i].CODE_NAME+"</option>\n";
					}
				}
				break;
				default:break;
		}	
	}
	//변경상세사유 option setting
	 $(obj).closest("tr").find("select[name='costRsnDtlCd']").append(optHtml);
}

//저장
function eventSave(){
	//validation check
	if(!fncValidate()) return;
	
	var datalen = $("#dataListbody tr[data-rowStat='I'], #dataListbody tr[data-rowStat='U']").length;
	
	if(datalen == 0){
		alert("변경된 정보가 없습니다.");
		return;
	}
	
	var saveInfo = {};
	var prodDataArr = [];
	
	var purDept = $.trim($("#srchPurDept").val());	//구매조직
	var venCd = $.trim($("#srchVenCd").val());		//파트너사코드
	var nbPbGbn = $.trim($("#srchNbPbGbn").val());	//nb,pb구분
	var freshStdYn = $.trim($("input[name='srchFreshStdYn']:checked").val());	//신선상품구분
	var taxFg = $.trim($("#srchTaxFg").val());		//과세유형
	
	var flag = true;
	
	$("#dataListbody tr[data-rowStat='I'], #dataListbody tr[data-rowStat='U']").each(function(){
		var row = $(this).closest("tr");
		
		var prodPatFg = $(this).find("input[name='prodPatFg']").val();				//상품유형코드
		var srcmkCd = $(this).find("input[name='srcmkCd']").val();					//판매코드
		var prodCd = $.trim($(this).find("input[name='prodCd']").val());			//상품코드
		var costRsnCd = $(this).find("select[name='costRsnCd']").val();				//변경사유
		var costRsnDtlCd = $(this).find("select[name='costRsnDtlCd']").val();		//변경상세사유
		var chgReqCostDt = $(this).find("input[name='chgReqCostDt']").val().replace(/\D/g,"");	//변경시작일자
		var prStat = $.trim($(this).find("input[name='prStat']").val())				//진행상태
		var rnum = $.trim($(this).find("span[name='rnum']").text());				//행번호
		var dcNum = $.trim($(this).find("input[name='dcNum']").val());				//공문번호
		var contCode = $.trim($(this).find("input[name='contCode']").val());		//공문연계번호
		
		//연계 공문번호 존재 시, 저장불가
		if(contCode != ""){
			alert("공문생성 이후 데이터 수정이 불가능합니다.\n행번호:"+rnum);	
			flag = false;
			return false;
		}
		
		//진행상태확인
		if("" != prStat && "00" != prStat){
			alert("승인대기중이거나 이미 승인/반려 상태입니다.\n저장이 불가능합니다.\n행번호:"+rnum);
			flag = false;
			return false;
		}
		
		if(prodCd == ""){
			alert("상품코드 정보가 없습니다.");
			flag=false;
			return false;
		}

		if(srcmkCd == ""){
			alert("판매코드 정보가 없습니다.");
			flag=false;
			return false;
		}
		
		if(costRsnCd == ""){
			alert("변경사유는 필수 입력입니다.");
			$(this).find("select[name='costRsnCd']").focus();
			flag=false;
			return false;
		}
		
		if(costRsnDtlCd == ""){
			alert("변경상세사유는 필수 입력입니다.");
			$(this).find("select[name='costRsnDtlCd']").focus();
			flag=false;
			return false;
		}
		
		if(chgReqCostDt == ""){
			alert("변경시작일자는 필수 입력입니다.");
			$(this).find("input[name='chgReqCostDt']").focus();
			flag=false;
			return false;
		}
		
		//원가 인상 인하 validation
		if(!fncChkCostChgRsn(row)){
			flag = false;
			return false;
		}
		
		//원가변경요청일이 과거일자임
		if(!fncChkChgReqCostDt($(this).find("input[name='chgReqCostDt']"))){
			flag=false;
			return false;
		}
		
		var prodData = {};
		$(this).find("input, select").not(".notInc").map(function(){
			if(this.name != undefined && this.name != null && this.name != ""){
				if($(this).hasClass("day") || $(this).hasClass("amt")){
					prodData[this.name] = $.trim($(this).val()).replace(/\D/g,"");
				}else{
					prodData[this.name] = $.trim($(this).val());
				}
			}
		});
		
		if(prodData != null){
			prodDataArr.push(prodData);
		}
	});
	
	if(!flag) return;
	
	//저장대상상품 없을 경우
	if(prodDataArr == null || prodDataArr.length == 0){
		alert("저장 가능한 대상상품 리스트가 존재하지 않습니다.\n공문이 생성 이후 수정이 불가능합니다.");
		return;
	}
	
	saveInfo.prodDataArr = prodDataArr;	//대상상품 list
	saveInfo.purDept = purDept;			//구매조직
	saveInfo.venCd = venCd;				//파트너사코드
	saveInfo.nbPbGbn = nbPbGbn;			//NB,PB구분
	saveInfo.freshStdYn = freshStdYn;	//신선유무
	saveInfo.taxFg = taxFg;				//과세유형
	
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/updateTpcProdChgCostInfo.json"/>',
		data : JSON.stringify(saveInfo),
		success : function(data) {
			var msg = data.msg;
			
			if("success" == msg){
				alert("저장되었습니다.");
				eventSearch();
			}else{
				var errMsg = $.trim(data.errMsg) != "" ? data.errMsg : "처리 중 오류가 발생했습니다.";
				alert(errMsg);
			}
			
		}
	});
	
	
}

//변경원가 , 원가 비교 validation 
/*
function priceValidChk(row){
	var chgReqCost = parseCost($.trim(row.find("input[name='chgReqCost']").val()));		// 변경원가
	var orgCost = parseCost($.trim(row.find("input[name='orgCost']").val()));			// 원가
	var costRsnCd = $.trim(row.find("select[name='costRsnCd']").val());		// 변경 사유
	var costRsnDtlCd = $.trim(row.find("select[name='costRsnDtlCd']").val());	// 변경 상세사유
	
	//변경원가 미입력	
	if(chgReqCost == null || chgReqCost == ""){
		alert("변경원가를 입력해주세요.");
		row.find("select[name='costRsnCd']").val("");
		row.find("select[name='costRsnDtlCd']").val("");
		row.find("input[name='chgReqCost']").focus();
		return false;
	}
	
	
	//변경원가가 기존원가보다 높을때는 인상  변경원가가 기존원가보다 낮을때는 인하만 선택되게 
	if(chgReqCost > orgCost){
		if(costRsnCd != "01"){ //01 인상  02 인하
			alert("변경원가가 기존원가보다 큽니다.\n올바른 변경사유를 선택해주세요.");
			row.find("select[name='costRsnCd']").val("");
			row.find("select[name='costRsnDtlCd']").val("");
			row.find("select[name='costRsnCd']").focus();
			return false;
		}  
	}
	
	if(chgReqCost < orgCost){
		if(costRsnCd != "02"){ //01 인상  02 인하
			alert("변경원가가 기존원가보다 작습니다.\n올바른 변경사유를 선택해주세요.");
			row.find("select[name='costRsnCd']").val("");
			row.find("select[name='costRsnDtlCd']").val("");
			row.find("select[name='costRsnCd']").focus();
			return false;
		}
	}
	
	return true;
}
*/

//증감여부에 따른 변경사유 validation
function fncChkCostChgRsn(trObj){
	if(trObj == undefined || trObj == null) return false;
	
	var chgReqCost = parseCost($.trim(trObj.find("input[name='chgReqCost']").val()));	//변경원가
	var orgCost = parseCost($.trim(trObj.find("input[name='orgCost']").val()));			//기존원가
	var costRsnCd = $.trim(trObj.find("select[name='costRsnCd']").val());		// 변경 사유
	var costRsnDtlCd = $.trim(trObj.find("select[name='costRsnDtlCd']").val());	// 변경 상세사유
	
	//변경원가 미입력
	if(chgReqCost == null || chgReqCost == "" || Number(chgReqCost) == 0){
		alert("변경원가 입력이후 변경사유 선택이 가능합니다.\n변경원가는 0원이상 입력해주세요.");
		trObj.find("select[name='costRsnCd']").val("");	// 선택 초기화
		trObj.find("input[name='chgReqCost']").focus();
		return false;
	}
	
	//변경원가 == 기존원가
	if(orgCost == chgReqCost){
		alert("변경원가가 기존원가와 동일합니다.\n변경원가를 다시 입력해주세요.");
		trObj.find("select[name='costRsnCd']").val("");	// 선택 초기화
		trObj.find("input[name='chgReqCost']").focus();
		return false;
	}
	
	//변경원가가 기존원가보다 높을때는 인상  변경원가가 기존원가보다 낮을때는 인하만 선택되게 ----
	if(chgReqCost > orgCost){
		if(costRsnCd != "01"){ //01 인상  02 인하
			alert("변경원가가 기존원가보다 큽니다.\n올바른 변경사유를 선택해주세요.");
			trObj.find("select[name='costRsnCd']").val("");	// 선택 초기화
			trObj.find("select[name='costRsnCd']").focus();	// focus
			return false;
		}  
	}
	
	if(chgReqCost < orgCost){
		if(costRsnCd != "02"){ //01 인상  02 인하
			alert("변경원가가 기존원가보다 작습니다.\n올바른 변경사유를 선택해주세요.");
			trObj.find("select[name='costRsnCd']").val("");	// 선택 초기화
			trObj.find("select[name='costRsnCd']").focus();	// focus
			return false;
		}
	}

	return true;		
}


//삭제
function eventDel(){
	var tgObj = $("#dataListbody tr").find("input[name='cbox']:checked");
	if(tgObj.length == 0){
		alert("삭제할 행을 선택해주세요.");
		return;
	}
	
	var saveInfo = {};
	var prodDataArr = [];	//DB삭제대상 data
	
	var flag = true;
	tgObj.closest("tr").each(function(){
		var delInfo = {};
		
		var reqNo = $(this).find("input[name='reqNo']").val();				//요청번호
		var srcmkCd = $(this).find("input[name='srcmkCd']").val();			//판매코드
		var prodCd = $(this).find("input[name='prodCd']").val();			//상품코드
		var seq = $(this).find("input[name='seq']").val();					//순번
		var prStat = $.trim($(this).find("input[name='prStat']").val());	//진행상태
		var rnum = $.trim($(this).find("span[name='rnum']").text());		//행번호
		
		if("" != prStat && "00" != prStat){
			alert("승인대기중이거나 이미 승인/반려 상태입니다.\n삭제가 불가능합니다.\n행번호:"+rnum);
			flag = false;
			return false;
		}
		
		if(reqNo != "" && srcmkCd != "" && prodCd != "" && seq != ""){
			delInfo.reqNo = reqNo;
			delInfo.srcmkCd = srcmkCd;
			delInfo.prodCd = prodCd;
			delInfo.seq = seq;
			
			prodDataArr.push(delInfo);
		}
	});
	
	if(!flag) return;
	
	if(!confirm("삭제하시겠습니까?")) return;
	
	saveInfo.prodDataArr = prodDataArr;		//삭제대상리스트
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/deleteTpcProdChgCostItem.json"/>',
		data : JSON.stringify(saveInfo),
		success : function(data) {
			var msg = data.msg;
			
			if("success" == msg){
				alert("삭제되었습니다.");

				//선택한 행 삭제
				$("#dataListbody tr").find("input[name='cbox']:checked").closest("tr").remove();
				
				//순번재정렬
				fncSetRnum();
			}else{
				var errMsg = $.trim(data.errMsg) != "" ? data.errMsg : "처리 중 오류가 발생했습니다.";
				alert(errMsg);
				return;
			}
		}
	});
		
}

//공문생성
function eventCreDcDoc() {
	var tgObj= $("#dataListbody tr").find("input[name='cbox']:checked");
	
	//선택된 row 없음
	if(tgObj.length == 0){
		alert("공문 생성을 위한 대상상품을 1개 이상 선택해주세요.\n(최대 "+maxSelRow+"건 선택가능)");
		return;
	}
	//최대 선택가능 수 초과
	if(tgObj.length > maxSelRow){
		alert("최대 "+maxSelRow+"건까지 공문 일괄생성이 가능합니다.\n현재 선택 건수:"+tgObj.length);
		return;
	}
	
	var purDept = $.trim($("#srchPurDept").val());	//구매조직
	var venCd = $.trim($("#srchVenCd").val());		//파트너사코드
	var nbPbGbn = $.trim($("#srchNbPbGbn").val());	//nb,pb구분
	var freshStdYn = $.trim($("input[name='srchFreshStdYn']:checked").val());	//신선상품구분
	var taxFg = $.trim($("#srchTaxFg").val());		//과세유형
	var dcCreGbn = $.trim($("input[name='dcCreGbn']:checked").val());		//공문생성구분(수신처)
	var ecsWrtId = $.trim($("#ecsWrtId").val());	//ECS 작성자아이디
	var dcGrpCreYn = "N";	//그룹요청건의 공문을 생성하는 것인지 체크(N:현재구매조직, Y:그룹요청구매조직)
	
	//수신담당자 정보
	var ecsReceiver 		= $.trim($("#ecsReceiver").val());			//수신담당자명
	var ecsReceiverEmpNo	= $.trim($("#ecsReceiverEmpNo").val());		//수신담당자 사번
	var ecsReceiverTeamCd	= $.trim($("#ecsReceiverTeamCd").val());	//수신담당자 소속팀코드
	
	//공문수신처 미선택
	if(dcCreGbn == ""){
		alert("공문 수신처를 선택해주세요.");
		return;
	}
	
	//공문생성 시, ECS 아이디 필수 입력
	if(ecsWrtId == ""){
		alert("ECS 아이디를 입력해주세요.");
		$("#ecsWrtId").focus();
		return;
	}
	
	//공문생성 시, 수신담당자 선택 필수
	if(ecsReceiverEmpNo == "" && ecsReceiverTeamCd == ""){
		alert("ECS 수신 담당자를 선택해주세요.");
		$("#ecsReceiver").focus();
		return;
	}
	
	//수신담당자 사번 없음
	if(ecsReceiverEmpNo == ""){
		alert("ECS 수신 담당자 사번을 확인할 수 없습니다.\n수신 담당자를 다시 지정해주세요.");
		$("#ecsReceiver").focus();
		return;
	}
	
	//수신담당자 소속팀 없음
	if(ecsReceiverTeamCd == ""){
		alert("ECS 수신 담당자 소속팀을 확인할 수 없습니다.\n수신 담당자를 다시 지정해주세요.");
		$("#ecsReceiver").focus();
		return;
	}
	
	//그룹요청건의 공문을 생성하는 것인지 체크
	switch(purDept){
		case "KR04":		//슈퍼
			dcGrpCreYn = (dcCreGbn == "SP")?"N":"Y";
			break;
		case "KR05":		//CS
			dcGrpCreYn = (dcCreGbn == "CS")?"N":"Y";
			break;
		default:			//마트 - 해당없음
			break;
	}
	
	
	//검색조건 체크 (공통 필수 data)
	if(!fncValidate()) return;
	
	var saveInfo = {};
	var prodDataArr = [];	//공문생성대상
	
	var flag = true;
	
	tgObj.closest("tr").each(function(){
		var row = $(this).closest("tr");
		var prodData = {};
		
		var rowStat = $(this).attr("data-rowStat");							//행상태
		var reqNo = $(this).find("input[name='reqNo']").val();				//요청번호
		var srcmkCd = $(this).find("input[name='srcmkCd']").val();			//판매코드
		var prodCd = $(this).find("input[name='prodCd']").val();			//상품코드
		var seq = $(this).find("input[name='seq']").val();					//순번
		var prStat = $.trim($(this).find("input[name='prStat']").val());	//진행상태
		var rnum = $.trim($(this).find("span[name='rnum']").text());		//행번호
		var dcNum = $.trim($(this).find("input[name='dcNum']").val());		//공문번호
		var dcStat = $.trim($(this).find("input[name='dcStat']").val());	//공문진행상태
		var contCode = $.trim($(this).find("input[name='contCode']").val());	//공문연계번호
		var dcCreAllYn = $.trim($(this).find("input[name='dcCreAllYn']").val());//모든공문생성여부
		
		var costRsnCd = $(this).find("select[name='costRsnCd']").val();				//변경사유
		var costRsnDtlCd = $(this).find("select[name='costRsnDtlCd']").val();		//변경상세사유
		
		//변경원가 입력 안하고 먼저 변경사유 입력시 validation 발생 
		var chgReqCost = parseCost($.trim($(this).find("input[name='chgReqCost']").val()));	//변경원가
		var orgCost = parseCost($.trim($(this).find("input[name='orgCost']").val()));		//원가
		
		//행 등록, 수정상태 확인
		if(rowStat != "R"){
			alert("변경된 데이터가 존재합니다.\n저장을 먼저 진행해주세요.\n행번호:"+rnum);
			flag = false;
			return false;
		}
		
		//진행상태확인
		if("" != prStat && "00" != prStat){
			alert("승인대기중이거나 이미 승인/반려 상태입니다.\nMD협의요청이 불가능합니다.\n행번호:"+rnum);
			flag = false;
			return false;
		}
		
		//원가 인상 인하 validation
		if(!fncChkCostChgRsn(row)){
			flag = false;
			return false;
		}
		
		//원가변경요청일이 과거일자임
		if(!fncChkChgReqCostDt($(this).find("input[name='chgReqCostDt']"))){
			flag=false;
			return false;
		}
		
		//이미 생성된 공문 존재
		if(dcGrpCreYn=="Y"){	//그룹화된 다른 구매조직의 공문생성시
			if(dcCreAllYn == "Y"){	//모든 공문이 생성돼있을 경우에만 return
				alert("이미 모든 공문이 생성되었습니다.\n행번호:"+rnum);
				flag = false;
				return false;
			}
		}else{					//현재 구매조직의 공문생성 시
			if((dcNum != "" || contCode != "")){
				alert("이미 생성된 공문이 존재합니다.\n행번호:"+rnum);
				flag = false;
				return false;
			}
		}
		
		prodData.reqNo = reqNo;           //요청번호
		prodData.srcmkCd = srcmkCd;       //판매코드
		prodData.prodCd = prodCd;         //상품코드
		prodData.seq = seq;               //순번
		
		prodDataArr.push(prodData);
	});
	
	if(!flag) return;
	
	saveInfo.purDept = purDept;			//구매조직
// 	saveInfo.prodPatFg = prodPatFg;		//상품유형구분
	saveInfo.freshStdYn = freshStdYn;	//신선규격구분
	saveInfo.taxFg = taxFg;				//면과세구분
	saveInfo.dcCreGbn = dcCreGbn;		//공문생성구분
	saveInfo.ecsWrtId = ecsWrtId;		//ECS 작성자아이디
	saveInfo.prodDataArr = prodDataArr;	//대상상품 list
	saveInfo.ecsReceiverEmpNo = ecsReceiverEmpNo;	//수신담당자사번
	saveInfo.ecsReceiverTeamCd = ecsReceiverTeamCd;	//수심담당자팀코드
	
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/insertCreDcDocProChgCost.json"/>',
		data : JSON.stringify(saveInfo),
		success : function(data) {
			var msg = data.msg;
			
			if("success" != msg){
				var errCode = $.trim(data.errCode);
				var errMsg = $.trim(data.errMsg);
				var alertMessage = "";
				
				switch(errCode){
				case "ERR01" :
					alertMessage = (errMsg != "")?errMsg:"ECS 응답이 없습니다.";
					break;
				case "ERR02" :
					alertMessage = (errMsg != "")?errMsg:"ECS 응답결과가 없습니다.";
					break;
				case "ERR03" :
					alertMessage = (errMsg != "")?errMsg:"ECS URL 추출에 실패하였습니다.";
					break;
				default :
					alertMessage = "처리 중 오류가 발생했습니다.\n"+errMsg;
					break;
				}
				
				alert(alertMessage);
				return;
			}
			
			var ecsUrl = data.url;
			Common.centerPopupWindow(ecsUrl, 'ECS', {width: 1280, height: 700});
		}
	});
	
}

//MD협의요청
function eventSendReq(){
	var tgObj = $("#dataListbody tr").find("input[name='cbox']:checked");
	if(tgObj.length == 0){
		alert("MD 협의요청 대상 상품을 선택해주세요.");
		return;
	}
	
	var saveInfo = {};
	var prodDataArr = [];	//협의요청대상
	
	var flag = true;
	tgObj.closest("tr").each(function(){
		var prodData = {};
		
		var rowStat = $(this).attr("data-rowStat");							//행상태
		var reqNo = $(this).find("input[name='reqNo']").val();				//요청번호
		var srcmkCd = $(this).find("input[name='srcmkCd']").val();			//판매코드
		var prodCd = $(this).find("input[name='prodCd']").val();			//상품코드
		var seq = $(this).find("input[name='seq']").val();					//순번
		var prStat = $.trim($(this).find("input[name='prStat']").val());	//진행상태
		var rnum = $.trim($(this).find("span[name='rnum']").text());		//행번호
		var dcNum = $.trim($(this).find("input[name='dcNum']").val());		//계약번호
		var dcStat = $.trim($(this).find("input[name='dcStat']").val());	//공문진행상태
		var contCode = $.trim($(this).find("input[name='contCode']").val());	//공문연계번호
		var grpId = $.trim($(this).find("input[name='grpId']").val()); 			//요청그룹아이디
		var dcCreAllYn = $.trim($(this).find("input[name='dcCreAllYn']").val()); //모든공문생성여부
		var dcCreSendYn = $.trim($(this).find("input[name='dcCreSendYn']").val()); //모든공문발송여부
		
		//행 등록, 수정상태 확인
		if(rowStat != "R"){
			alert("변경된 데이터가 존재합니다.\n저장을 먼저 진행해주세요.\n행번호:"+rnum);
			flag = false;
			return false;
		}
		
		//진행상태확인
		if("" != prStat && "00" != prStat){
			alert("승인대기중이거나 이미 승인/반려 상태입니다.\nMD협의요청이 불가능합니다.\n행번호:"+rnum);
			flag = false;
			return false;
		}
		
		//공문미생성
		if(""==dcNum || "N" == dcCreAllYn){
			if(grpId != ""){
				alert("모든 공문이 생성 되지 않았습니다.\n공문발송 이후 MD협의요청이 가능합니다.\n행번호:"+rnum);
			}else{
				alert("공문이 생성 되지 않았습니다.\n공문발송 이후 MD협의요청이 가능합니다.\n행번호:"+rnum);
			}
			flag = false;
			return false;
		}
		
		//공문미발송
		if("N" == dcCreSendYn){
			if(grpId != ""){
				alert("발송되지 않은 공문이 존재합니다.\n공문발송 이후 MD협의요청이 가능합니다.\n행번호:"+rnum);
			}else{
				alert("공문이 발송 되지 않았습니다.\n공문발송 이후 MD협의요청이 가능합니다.\n행번호:"+rnum);
			}
			flag = false;
			return false;
		}
		
		prodData.reqNo = reqNo;           //요청번호
		prodData.srcmkCd = srcmkCd;       //판매코드
		prodData.prodCd = prodCd;         //상품코드
		prodData.seq = seq;               //순번
		
		prodDataArr.push(prodData);
	});
	
	if(!flag) return;
	
	if(!confirm("선택하신 "+tgObj.length+"건에 대해 MD협의요청 하시겠습니까?")) return;
	
	saveInfo.prodDataArr = prodDataArr;	//대상상품 list
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/insertReqMdProChgCost.json"/>',
		data : JSON.stringify(saveInfo),
		success : function(data) {
			var msg = data.msg;
			
			if("success" == msg){
				var succMsg = "요청되었습니다.";
				if($.trim(data.resultMsg) != "") succMsg += "\n"+$.trim(data.resultMsg);
				alert(succMsg);
				eventSearch();
			}else{
				var errMsg = $.trim(data.errMsg) != "" ? data.errMsg : "처리 중 오류가 발생했습니다.";
				alert(errMsg);
			}
			
		}
	});
	
}


//증감율계산
function fncCalcIncDecRate(obj){
	var tgRow = $(obj).closest("tr");
	var orgCost = tgRow.find("input[name='orgCost']").val().replace(/\D/g, "");			//기존원가
	var chgReqCost = tgRow.find("input[name='chgReqCost']").val().replace(/\D/g, "");	//변경원가
	
	if($.trim(orgCost) == "" || $.trim(chgReqCost) == ""){
		tgRow.find("input[name='incDecRate']").val(0);
		return;
	}
	
	orgCost = (orgCost == "")? 0:Number(orgCost);
	chgReqCost = (chgReqCost == "")? 0:Number(chgReqCost);
	
	//기존원가가 0이면 증감율 미표시
	if(orgCost == 0){
		tgRow.find("input[name='incDecRate']").val("");
	}else{
		//증감율 계산
		var rate = Number(chgReqCost-orgCost)*100/Number(orgCost);
		tgRow.find("input[name='incDecRate']").val(rate.toFixed(2));
	}
}


//원가변경요청일 체크
function fncChkChgReqCostDt(obj){
	var date = (obj != undefined && obj != null)? $.trim($(obj).val()).replace(/\D/g,"") : "";
	var rnum = $.trim($(obj).closest("tr").find("span[name='rnum']").text());		//행번호
	
	if(date.length != 8){
		alert("원가변경요청일을 올바르게 입력해주세요.\n행번호:"+rnum);
		$(obj).focus();
		return false;
	}
	
	var yy = date.substring(0,4);//연
	var mm = date.substring(4,6);//월
	var dd = date.substring(6,8);//일
	
	var selDate = new Date(yy, mm-1, dd);
	var today = new Date();
	
	if(selDate <= today){
		alert("원가변경 요청일은 다음날부터 선택 가능합니다.\n행번호:"+rnum);
		$(obj).val("");
		$(obj).focus();
		return false;
	}
	
	return true;
}

//순번재정렬
function fncSetRnum(){
	$("#dataListbody tr").each(function (index) {
        $(this).find("td:eq(1)").text(index + 1); // 1부터 다시 설정
    });
}

//ECS 수신 담당자 조회
function fncOpenPopEcsRcvMngr() {
	var pData = [];

	var params = "";
	Object.keys(pData).forEach(function(k, i){
	  params = params+(i===0?"?":"&")+k+"="+pData[k];  
	});

	// 팝업 URL 생성
	var targetUrl = "<c:url value='/edi/product/selEcsReceptionistPopup.do'/>" + "?" + params;

	// 팝업 오픈
	Common.centerPopupWindow(targetUrl, 'ecsReceptionistPopup', { width: 980, height: 700 });
}

//ECS 수신 담당자 콜백
function setEcsReceiverNm(data) {
	if(data == null){
		alert("관리자 정보가 유효하지 않습니다. \n관리자에게 문의하세요.");
		return;
	}
	
	var empNo 		= $.trim(data.empNo); 
	var managerNm	= $.trim(data.managerNm);
	var teamCd		= $.trim(data.teamCd);
	var teamNm		= $.trim(data.teamNm);
	
	if(empNo == "" || empNo == null){
		alert("담당자 사번이 존재하지 않아 수신 담당자 설정이 불가능합니다.");
		return;
	}
	
	if(teamCd == "" || teamCd == null){
		alert("담당자의 팀 정보가 존재하지 않아 수신 담당자 설정이 불가능합니다.");
		return;
	} 

	//수신 담당자 정보 세팅
	$("#ecsReceiver").val(managerNm);
	$("#ecsReceiverEmpNo").val(empNo);
	$("#ecsReceiverTeamCd").val(teamCd);
	$("#ecsReceiverTeamNm").val(teamNm);
}

//업체코드 별 계열사 & 구매조직 체크
function eventSelChkVenPurDept(venCd){
	if($.trim(venCd) == ""){
		//선택가능 option list 전체 삭제
		$("#srchPurDept").find("option[value!='']").remove();
		//공문영역 초기화
		fncDcAreaControl("");
		
		//제한조건 초기화
		srchProdMart = "";		//판매코드 검색조건_마트상품
		srchProdMaxx = "";		//판매코드 검색조건_MAXX상품
		srchProdSuper = "";		//판매코드 검색조건_슈퍼상품
		srchProdCfc = "";		//판매코드 검색조건_CfC상품
		return;
	}
	
	<%-- 업무에서 사용하는 구매조직 필터링 --%>
	const codeSrchInfo = {
		"notIn" : "Y"
		, "subExtentList02" : ["XX"]
		, "subNmUseYn" : "Y"
		, "orderSeqYn" : "Y"
	};
	
	var paramInfo	=	{};
	paramInfo["venCd"] = $.trim(venCd);			//업체코드
	paramInfo["codeSrchInfo"] = codeSrchInfo;	//업무별 구매조직 조회조건 SETTING
	

	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		async : false,
		url : '<c:url value="/edi/product/selectVendorPurDeptsWorkUsable.json"/>',
		data : JSON.stringify(paramInfo),
		success : function(data) {
			//협력사별 구매조직 셋팅 
			fncCtrlVenPurDeptinfo(data);
		}
	});
}

//업체코드 별 사용가능 계열사 & 구매조직   
function fncCtrlVenPurDeptinfo(json){
	let selPurDept = $.trim($("#srchPurDept").val());		//이전선택 구매조직
	
	//선택가능 option list 전체 삭제
	$("#srchPurDept").find("option[value!='']").remove();
	//공문영역 초기화
	fncDcAreaControl("");
	
	//구매조직 select box option 구성
	let selBoxOpts = "";
	if(json != null){
		for(var i=0; i<json.length; i++){
			selBoxOpts += "<option value='"+$.trim(json[i].CODE_ID)+"'>"+$.trim(json[i].CODE_NAME)+"</option>\n";
		}
		
		//option setting
		$("#srchPurDept").append(selBoxOpts);
		
		<%-- 
			이전선택값이 있으면 그대로 셋팅해준다
			(selectbox라서 옵션에 없으면 빈값 자동으로 셋팅됨!) 
		--%>
		if(selPurDept != ""){
			$("#srchPurDept").val(selPurDept);
		}
		
		//현재 선택된 구매조직에 따른 공문영역 control
		let newPurDept = $.trim($("#srchPurDept").val());		//변경 후 구매조직 선택값
		fncDcAreaControl(newPurDept);	//공문영역 자동셋팅
		
		//첫번째 옵션 자동 선택
// 		var fstOpt = $.trim($("#srchPurDept").find("option[value!='']").eq(0).val());
// 		$("#srchPurDept").val(fstOpt);
// 		fncDcAreaControl(fstOpt);	//공문영역 자동셋팅
	}
	
	//구매조직별 상품조회 조건 셋팅
	fncSetSrchProdChans();
}


//구매 조직 변경 시, 판매코드 팝업 제한조건 셋팅
function fncSetSrchProdChans(){
	let srchPurDept = $.trim($("#srchPurDept").val());
	
	//제한조건 초기화
	srchProdMart = "";		//판매코드 검색조건_마트상품
	srchProdMaxx = "";		//판매코드 검색조건_MAXX상품
	srchProdSuper = "";		//판매코드 검색조건_슈퍼상품
	srchProdCfc = "";		//판매코드 검색조건_CfC상품

	//제한조건 재설정
	switch(srchPurDept){
		case "KR02":	//마트
			srchProdMart = "Y";
			break;
		case "KR03":	//MAXX
			srchProdMaxx = "Y";
			break;
		case "KR04":	//슈퍼
		case "KR05":	//CS
			srchProdSuper = "Y";
			break;
		case "KR09":	//CFC
			srchProdCfc = "Y";
			break;
		default:
			//판매코드 팝업 구매조직 검색조건 Disabled
			fncSetSrchProdChansDisabledAll();
			break;
	}
}

//판매코드 팝업 구매조직 검색조건 Disabled
function fncSetSrchProdChansDisabledAll(){
	srchProdMart	= "Y";		//판매코드 검색조건_마트상품
	srchProdMaxx	= "Y";		//판매코드 검색조건_MAXX상품
	srchProdSuper	= "Y";		//판매코드 검색조건_슈퍼상품
	srchProdCfc 	= "Y";		//판매코드 검색조건_CfC상품
}
</script>


<!-- 요청내역 datalist template -->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r\${count}" bgcolor="#ffffff" data-rowStat="<c:out value='\${rowStat}'/>">
	<td class="chkGbn tdc" style="padding:0px">
		{%if prStat == "00"%}
		<input type="checkbox" id="cbox\${count}" name="cbox" class="notInc"/>
		{%/if%}
		<input type="hidden" name="reqNo" 		value="<c:out value='\${reqNo}'/>"/>
		<input type="hidden" name="seq" 		value="<c:out value='\${seq}'/>"/>
		<input type="hidden" name="prodPatFg" 	value="<c:out value='\${prodPatFg}'/>"/>
		<input type="hidden" name="ordUnit" 	value="<c:out value='\${ordUnit}'/>"/>
		<input type="hidden" name="prStat"		value="<c:out value='\${prStat}'/>"/>
		<input type="hidden" name="venCd"		value="<c:out value='\${venCd}'/>"/>
		<input type="hidden" name="freshStdYn"	value="<c:out value='\${freshStdYn}'/>"/>
		<input type="hidden" name="grpId"		value="<c:out value='\${grpId}'/>"/>
		<input type="hidden" name="dcCreAllYn"	value="<c:out value='\${dcCreAllYn}'/>"/>
		<input type="hidden" name="editAvailYn"	value="<c:out value='\${editAvailYn}'/>"/>
	</td>
	<td class="tdc"><span name="rnum"><c:out value='\${rnum}'/></span></td>
	<td class="tdc">
		{%if prStat == "03"%}
		<a href="javascript:void(0);" class="btn" onclick="eventCopy(this)"><span>복사</span></a>
		{%/if%}
	</td>
	<td class="tdc">
		<span name="prStatNm"><c:out value='\${prStatNm}'/></span>
		<input type="hidden" name="prStat" 		value="<c:out value='\${prStat}'/>"/>
	</td>
	<td class="tdc">
		{%if (dcNum == null || dcNum == "") && (contCode != null && contCode != "")%}
			<span name="dcNum" style="color:#0058e1;font-weight:700;">[수신대기]</span>
		{%else%}
			<span name="dcNum"><c:out value='\${dcNum}'/></span>
		{%/if%}
		
		<input type="hidden" name="dcNum" 		value="<c:out value='\${dcNum}'/>"/>
		<input type="hidden" name="contCode" 	value="<c:out value='\${contCode}'/>"/>
	</td>
	<td class="tdc">
		<span name="prodCd"><c:out value='\${prodCd}'/></span>
		<input type="hidden" name="prodCd" 		value="<c:out value='\${prodCd}'/>"/>
	</td>
	<td class="tdc">
		<span name="srcmkCd"><c:out value='\${srcmkCd}'/></span>
		<input type="hidden" name="srcmkCd" 		value="<c:out value='\${srcmkCd}'/>"/>
	</td>
	<td>
		<input type="hidden" id="l1Cd\${count}" name="l1Cd" value="<c:out value='\${l1Cd}'/>"/>
		<span name="l1Nm"><c:out value='\${l1Nm}'/></span>
	</td>
	<td>
		<input type="hidden" id="l2Cd\${count}" name="l2Cd" value="<c:out value='\${l2Cd}'/>"/>
		<span name="l2Nm"><c:out value='\${l2Nm}'/></span>
	</td>
	<td>
		<input type="hidden" id="l3Cd\${count}" name="l3Cd" value="<c:out value='\${l3Cd}'/>"/>
		<span name="l3Nm"><c:out value='\${l3Nm}'/></span>
	</td>
	<td><span name="prodNm"><c:out value='\${prodNm}'/></span></td>
	<td class="tdc">
		{%if (dcNum == null || dcNum == "") && editAvailYn == "Y" %}
		<input type="text" class="day" id="chgReqCostDt\${count}" name="chgReqCostDt" style="width:90px;" value="<c:out value='\${chgReqCostDtFmt}'/>" disabled> <img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor:hand;" />
		{%else%}
		<input type="text" class="day" id="chgReqCostDt\${count}" name="chgReqCostDt" style="width:90px;" value="<c:out value='\${chgReqCostDtFmt}'/>" disabled>
		{%/if%}
	</td>
	<td class="tdr">
		<input type="text" id="orgCost\${count}" name="orgCost" value="<c:out value='\${orgCostFmt}'/>" style="text-align:right;width:100%;" class="amt" disabled/>
	</td>
	<td class="tdr">
		{%if (dcNum == null || dcNum == "") && editAvailYn == "Y" %}
		<input type="text" id="chgReqCost\${count}" name="chgReqCost" value="<c:out value='\${chgReqCostFmt}'/>" style="text-align:right;width:100%;" class="amt" oninput="this.value=this.value.replace(/\D/g,'')" maxlength="9"/>
		{%else%}
		<input type="text" id="chgReqCost\${count}" name="chgReqCost" value="<c:out value='\${chgReqCostFmt}'/>" style="text-align:right;width:100%;" class="amt" oninput="this.value=this.value.replace(/\D/g,'')" maxlength="9" disabled/>
		{%/if%}
	</td>
	<td class="tdr">
		<input type="text" id="incDecRate\${count}" name="incDecRate" value="<c:out value='\${incDecRate}'/>" style="text-align:right;width:80%;" class="notInc" disabled/> %
	</td>
	<td class="tdc">
		{%if (dcNum == null || dcNum == "") && editAvailYn == "Y" %}
		<html:codeTag objId="costRsnCd\${count}" objName="costRsnCd" width="100px;"  parentCode="CORSN"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form"/>
		{%else%}
		<html:codeTag objId="costRsnCd\${count}" objName="costRsnCd" width="100px;"  parentCode="CORSN"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form" disabled="disabled"/>
		{%/if%}
	</td>
	<td class="tdc">
		{%if (dcNum == null || dcNum == "") && editAvailYn == "Y" %}
		<html:codeTag objId="costRsnDtlCd\${count}" objName="costRsnDtlCd" width="100px;"  parentCode="CORSD"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form"/>
		{%else%}
		<html:codeTag objId="costRsnDtlCd\${count}" objName="costRsnDtlCd" width="100px;"  parentCode="CORSD"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form" disabled="disabled"/>
		{%/if%}
	</td>
	<td>
		{%if (dcNum == null || dcNum == "") && editAvailYn == "Y" %}
		<input type="text" id="cmt\${count}" name="cmt" value="<c:out value='\${cmt}'/>" style="width:100%" maxlength="200"/>
		{%else%}
		<input type="text" id="cmt\${count}" name="cmt" value="<c:out value='\${cmt}'/>" style="width:100%" maxlength="200" disabled/>
		{%/if%}
	</td>
</tr>
</script>

</head>
<body>
	<div id="content_wrap">
		<div id="wrap_menu">
			<div class="wrap_search">
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">원가변경요청내역 조회</li>
						<li class="btn">
							<a href="javascript:void(0);" class="btn" onclick="eventExcelDown()"><span>Excel 다운로드</span></a>
							<a href="javascript:void(0);" class="btn" onclick="eventSearch()"><span><spring:message code="button.common.inquire"/></span></a>
						</li>
					</ul>
					<!-- 검색조건 start -->
					<form id="searchForm" name="searchForm">
<%-- 					<input type="hidden" id="srchProdPatFg" name="srchProdPatFg" value="1"/>	 --%>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col width="12%"/>
							<col width="20%"/>
							<col width="12%"/>
							<col width="25%"/>
							<col width="12%"/>
							<col width="15%"/>
						</colgroup>
						<tbody>
							<tr>
								<th><span class="star">*</span> 파트너사</th>
								<td>
									<html:codeTag objId="srchVenCd" objName="srchVenCd" width="150px;" dataType="CP" comType="SELECT" defName="선택" formName="form"/>
								</td>
								<th><span class="star">*</span> 구매조직</th>
								<td>
									<html:codeTag objId="srchPurDept" objName="srchPurDept" width="150px;"  parentCode="PURDE"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form" subNmUseYn="Y"/>
								</td>
								<th>진행상태</th>
								<td>
									<html:codeTag objId="srchPrStat" objName="srchPrStat" width="100px;"  parentCode="PR01"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="전체" formName="form"/>
								</td>
							</tr>
							<tr>
								<th><span class="star">*</span> NB/PB</th>
								<td>
									<html:codeTag objId="srchNbPbGbn" objName="srchNbPbGbn" width="100px;"  parentCode="NBPB"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form"/>
								</td>
								<th><span class="star">*</span> 신선상품여부</th>
								<td>
									<input type="radio" id="srchFreshStdYn_N" name="srchFreshStdYn" value="N" checked/><label for="srchFreshStdYn_N">일반</label>
									<input type="radio" id="srchFreshStdYn_Y" name="srchFreshStdYn" value="Y"/><label for="srchFreshStdYn_Y">신선</label>
								</td>
								<th><span class="star" style="display:none;">*</span> 과세유형</th>
								<td>
									<html:codeTag objId="srchTaxFg" objName="srchTaxFg" width="100px;"  parentCode="TAXFG"  comType="SELECT"  dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form"/>
								</td>
							</tr>
							<tr>
								<th>판매코드</th>
								<td>
									<input type="text" name="srchSrcmkCd" id="srchSrcmkCd" style="width:100px;"/>
									<a href="javascript:void(0);" class="btn" onclick="fncOpenSrchPopSrcmkCd()"><span>찾기</span></a>
								</td>
								<th>변경시작일자</th>
								<td colspan="3">
									<input type="text" class="day" id="srchChgReqCostDt" name="srchChgReqCostDt" style="width:80px;" value="<c:out value='${srchFromDt}'/>">
									<img src="/images/epc/layout/btn_cal.gif" class="middle datepicker"  style="cursor:hand;" />
								</td>
							</tr>
						</tbody>
					</table>
					</form>
					<table cellpadding="0" cellspacing="1" border="0" width="100%" bgcolor="efefef">
						<tbody><tr>
							<td colspan="6" bgcolor="ffffff">
								&nbsp;※ 공문생성 이후, 공문번호 수신은 10~15초 가량 소요될 수 있습니다.
								<br/>
								<font color="red">
								&nbsp;※ 구매조직이 슈퍼 또는 CS일 경우, 데이터 수정/삭제 시 일괄 생성된 데이터에도 함께 반영됩니다.
								</font>
							</td>
						</tr>
					</tbody></table>
					<!-- ./검색조건 end -->
					<!-- 검색내역 start -->
					<div class="wrap_con">
						<div class="bbs_list">
							<ul class="tit" style="color:#ffffff;">
								<li class="tit">대상상품리스트</li>
								<li class="btn" style="text-align: right;">
									<a href="javascript:void(0);" class="btn" onclick="eventSave()"><span>저장</span></a>
									<a href="javascript:void(0);" class="btn" onclick="eventDel()"><span>삭제</span></a>
									<a href="javascript:void(0);" class="btn" onclick="eventSendReq()"><span>MD협의요청</span></a>
								</li>
							</ul>
							<ul class="tit" style="height:35px; color:#ffffff; background-color:#72828f;">
								<li class="btn"  style="text-align: right;">
									<div style="color: #ffffff;margin-top: 2px; display: inline;">
										공문생성 : 
										<html:codeTag objId="dcCreGbn" objName="dcCreGbn" parentCode="MSTGB"  comType="RADIO" dataType="NTCPCD" orderSeqYn="Y" formName="form"/>
										<input type="text" id="ecsWrtId" name="ecsWrtId" value="" placeholder="ECS 계정입력"/>
										<input type="text" id="ecsReceiver" name="ecsReceiver" value="" placeholder="ECS 수신담당자 선택" style="border: 1px solid #ffffff; background: #efefef;" disabled/>
										<input type="hidden" id="ecsReceiverEmpNo" name="ecsReceiverEmpNo" value=""/>
										<input type="hidden" id="ecsReceiverTeamCd" name="ecsReceiverTeamCd" value=""/>
										<input type="hidden" id="ecsReceiverTeamNm" name="ecsReceiverTeamNm" value=""/>
										<i class="bi bi-search" style='margin-right: 10px; cursor: pointer;' onclick='fncOpenPopEcsRcvMngr()' title="ECS 수신담당자 찾기"></i>
										<a href="javascript:void(0);" class="btn" onclick="eventCreDcDoc()"><span>공문생성</span></a>
									</div>
								</li>
							</ul>
							<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll; white-space:nowrap;">
								<form id="reqDetailForm" name="reqDetailForm">
								<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=2400px; bgcolor="efefef" style="table-layout:fixed;" class="sub-table">
									<colgroup>
										<col width="2%">	
										<col width="2%">	
										<col width="5%">
										<col width="5%">
										<col width="5%">	
										<col width="5%">	
										<col width="8%">	
										<col width="8%">	
										<col width="8%">	
										<col width="8%">	
										<col width="10%">	
										<col width="8%">	
										<col width="8%">	
										<col width="8%">	
										<col width="8%">	
										<col width="8%">	
										<col width="8%">	
										<col width="15%">	
									</colgroup>
									<thead>
										<tr bgcolor="#e4e4e4">
											<th><input type="checkbox" id="chkAll"/></th>
											<th>No</th>
											<th>복사</th>
											<th>진행상태</th>
											<th>공문번호</th>
											<th>상품코드</th>
											<th>판매코드</th>
											<th>대분류</th>
											<th>중분류</th>
											<th>소분류</th>
											<th>상품명</th>
											<th>원가변경<br/>요청일</th>
											<th>기존원가</th>
											<th>변경원가</th>
											<th>증감율</th>
											<th>변경사유</th>
											<th>상세사유</th>
											<th>비고</th>
										</tr>
									</thead>
									<tbody id="dataListbody" />
								</table>
								</form>
							</div>
						</div>
					</div>
					<!-- ./검색내역 end -->
				</div>
				
				<!-- footer -->
				<div id="footer">
					<div id="footbox">
						<div class="msg" id="resultMsg"></div>
						<div class="location">
							<ul>
								<li>상품</li>
								<li>차세대_신규</li>
								<li class="last">원가변경요청조회</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- footer //-->
			</div>
		</div>
	</div>
	<!--  -->
	<form id="hiddenForm" name="hiddenForm" method="post">
		<input type="hidden" name="srchPurDept"/>
		<input type="hidden" name="srchNbPbGbn"/>
		<input type="hidden" name="srchVenCd"/>
		<input type="hidden" name="srchProdPatFg"/>
		<input type="hidden" name="srchTaxFg"/>
	</form>
	
</body>
</html>