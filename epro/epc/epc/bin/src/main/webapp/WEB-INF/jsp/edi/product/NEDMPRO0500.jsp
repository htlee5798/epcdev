<%--
	Page Name 	: NEDMPRO0500.jsp
	Description : 원가변경요청 등록
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
<title>원가변경요청 등록</title>
<style>
table.sub-table td{padding:5px 3px; word-break:break-word;}
table.sub-table select, table.sub-table input, table.sub-table textarea{max-width:100%;}  
table.sub-table .tdr{text-align:right;}
table.sub-table .tdc{text-align:center;}
</style>

<script type="text/javascript" >
let rowCount = 0;
let maxSelRow = 100;
$(function(){
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
		
// 		openCalSetDt("reqDetailForm."+objId, objVal);
		openCalSetDt("reqDetailForm."+objId, objVal, "fncCallBackCalendar", minDt);
	});
	
	//변경사유 변경
	$(document).on("change", "#dataListbody select[name='costRsnCd']", function(){
		var costRsnCd = $.trim(this.value);		//변경사유
		var prodPatFg = $.trim($(this).closest("tr").find("input[name='prodPatFg']").val());		//상품유형구분코드
		//변경상세사유 selectbox 구성
		_setOptCostRsnDtlCd($(this), prodPatFg, costRsnCd);
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
	$("#srchVenCd, #srchPurDept, #srchNbPbGbn, #srchProdPatFg, #srchTaxFg").on("focus", function(){
		bfSrch = $.trim($(this).val());
	}).change(function(){
		afSrch = $.trim($(this).val());
		
		//데이터 있을 때만 체크
		var datalen = $("#dataListbody tr").length;
		if(datalen == 0) return;
		
// 		if(this.name == "srchTaxFg"){
// 			var srchProdPatFg = $.trim($("#srchProdPatFg").val());	//상품유형
// 			if(srchProdPatFg != "0"){	//신선이 아닐 경우에는 리스트 초기화 안함
// 				return;
// 			}
// 		}
		
		//이전선택값 존재하면서 이후선택값이 달라졌을 경우,
		if(bfSrch != "" && bfSrch != afSrch){
			if(!confirm("검색조건 변경 시, 입력 및 수정하신 대상상품 리스트가 초기화 됩니다.\n진행하시겠습니까?")){
				//취소 시, 이전선택값 재셋팅
				$(this).val(bfSrch);
				return; 
			}
			
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
	
	//상품유형 변경 이벤트
	$(document).on("change", "#srchProdPatFg", function(){
		//상품유형=신선(0)일 경우에만 과세유형 필수선택
		var val = $.trim($(this).val());
		
		if(val == "0"){
			$("#srchTaxFg").parent().prev().find("span.star").css("display", "inline-block");
		}else{
			$("#srchTaxFg").parent().prev().find("span.star").css("display", "none");
		}
		
		$("#srchTaxFg").val("");
	});
	
	<%-- 원가변경요청 조회 메뉴에서 넘어오는 경우, 필수 검색조건 모두 있을 시, 즉시 조회 --%>
	<c:if test='${not empty paramVo}'>
		var srchPurDept = "<c:out value='${paramVo.srchPurDept}'/>";		//구매조직
		var srchNbPbGbn = "<c:out value='${paramVo.srchNbPbGbn}'/>";		//NB,PB구분
		var srchVenCd = "<c:out value='${paramVo.srchVenCd}'/>";			//파트너사
		
		if(srchPurDept != "" && srchNbPbGbn != "" && srchVenCd != ""){
			$("#srchPurDept").val(srchPurDept);
			$("#srchNbPbGbn").val(srchNbPbGbn);
			$("#srchVenCd").val(srchVenCd);
			
			eventSearch();
		}
	
	</c:if>
	
});

//날짜변경 callback
function fncCallBackCalendar(tgId, cbData){
	if(tgId == null || tgId == undefined || tgId == "") return;	//반환 target 없을 경우 return
	if(tgId.startsWith("srch")) return;	//검색조건 내 캘린더일 경우 return
	
	var tgObj = $(eval(tgId)).closest("tr");
	
	if(tgObj == null || tgObj == undefined) return;
	
	//현재 rowStat
	var currStat = $.trim(tgObj.attr("data-rowStat"));
	
	//기등록된 데이터일 경우
	if(currStat == "R"){
		//수정상태 변경
		tgObj.attr("data-rowStat", "U");
	}
}

//검색조건 확인
function fncValidate(){
	var srchPurDept = $.trim($("#srchPurDept").val());	//구매조직
	var srchVenCd = $.trim($("#srchVenCd").val());		//파트너사코드
	var srchNbPbGbn = $.trim($("#srchNbPbGbn").val());	//NB,PB 구분
	var srchProdPatFg = $.trim($("#srchProdPatFg").val());	//상품유형
	var srchTaxFg = $.trim($("#srchTaxFg").val());		//과세유형
	
	//구매조직 누락
	if(srchPurDept == ""){
		alert("구매조직은 필수 선택 항목입니다.");
		$("#srchPurDept").focus();
		return false;
	}
	
	//파트너사코드 누락
	if(srchVenCd == ""){
		alert("파트너사는 필수 선택 항목입니다.");
		$("#srchVenCd").focus();
		return false;
	}
	
	//NB,PB구분 누락
	if(srchNbPbGbn == ""){
		alert("NB/PB 구분은 필수 선택 항목입니다.");
		$("#srchNbPbGbn").focus();
		return false;
	}
	
	//상품유형 누락
	if(srchProdPatFg == ""){
		alert("상품유형은 필수 선택 항목입니다.");
		$("#srchProdPatFg").focus();
		return false;
	}
	
	//상품유형=신선일 경우, 과세유형 필수
	if(srchProdPatFg == "0" && srchTaxFg == ""){
		alert("신선 상품일 경우, 과세 유형은 필수 선택 항목입니다.");
		$("#srchTaxFg").focus();
		return false;
	}
	
	return true;
}

//행추가
function fncAddRow(){
	//validation check
	if(!fncValidate()) return;
	
	var newRowData = {};
	newRowData.reqNo = "";
	newRowData.rowStat = "I";		//행상태
	newRowData.costRsnCd = "";		//변경사유
	newRowData.rnum = $("#dataListbody tr").length+1;
	newRowData.count = ++rowCount;	//row index
	newRowData.dcNum = "";
	newRowData.dcStat = "";
	
	//행추가
	$("#dataListTemplate").tmpl(newRowData).appendTo("#dataListbody");
	
	$("#chkAll").prop("checked", false);
}	

//행삭제
function fncDelRow(){
	var tgObj = $("#dataListbody tr").find("input[name='cbox']:checked");
	if(tgObj.length == 0){
		alert("삭제할 행을 선택해주세요.");
		return;
	}
	
	//U, R인것만 ajax
	var saveInfo = {};
	var prodDataArr = [];	//DB삭제대상 data
	
	var flag = true;
	tgObj.closest("tr[data-rowStat='R'], tr[data-rowStat='U']").each(function(){
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
	
	//DB에서 삭제할 데이터가 있을 경우
	if(prodDataArr != null && prodDataArr.length > 0){
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
				}else{
					var errMsg = $.trim(data.errMsg) != "" ? data.errMsg : "처리 중 오류가 발생했습니다.";
					alert(errMsg);
				}
				
			}
		});
		
	}else{
		//선택한 행 삭제
		$("#dataListbody tr").find("input[name='cbox']:checked").closest("tr").remove();
	}
	
	//순번재정렬
	fncSetRnum();
}

//순번재정렬
function fncSetRnum(){
	$("#dataListbody tr").each(function (index) {
        $(this).find("td:eq(1)").text(index + 1); // 1부터 다시 설정
    });
}

//엑셀템플릿 다운로드
function eventExcelTmplDown(){
	fncNotyet();
}

//엑셀템플릿 업로드
function eventExcelUpload(){
	fncNotyet();
}

//미완
function fncNotyet(){
	alert("준비 중입니다.");
	return false;
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

//판매코드 찾기 검색조건 팝업 OPEN
function fncOpenSrchPopSrcmkCd(){
	var pData = [];
	pData.trNum = "search";
	pData.srcmkCd = $.trim($("#srchSrcmkCd").val());
	pData.prodPatFg = $.trim($("#srchProdPatFg").val());
	pData.taxFg = $.trim($("#srchTaxFg").val());
	
	var params = "";
	Object.keys(pData).forEach(function(k, i){
	  params = params+(i===0?"?":"&")+k+"="+pData[k];  
	});
	
	var targetUrl = "<c:url value='/edi/product/selSrcmkCdPopup.do'/>"+params;
	Common.centerPopupWindow(targetUrl, 'sellCdPopup', {width: 980, height: 700});
}

//판매코드 찾기 팝업 Open
function fncOpenPopSrcmkCd(obj){
	var pData = [];
	pData.trNum = $(obj).closest('tr').attr('class');
	pData.srcmkCd = $.trim($(obj).closest('td').find("input[name='srcmkCd']").val());
	pData.prodCd = $.trim($(obj).closest('tr').find("input[name='prodCd']").val());
	pData.prodPatFg = $.trim($("#srchProdPatFg").val());
	pData.taxFg = $.trim($("#srchTaxFg").val());
	
	var params = "";
	Object.keys(pData).forEach(function(k, i){
	  params = params+(i===0?"?":"&")+k+"="+pData[k];  
	});
	
	var targetUrl = "<c:url value='/edi/product/selSrcmkCdPopup.do'/>"+params;
	Common.centerPopupWindow(targetUrl, 'sellCdPopup', {width: 980, height: 700});
}

//판매코드 찾기 팝업 callback
function setSellCd(data){
	if(data == null){
		alert("상품 데이터가 유효하지 않습니다.\n관리자에게 문의하세요.");
		return;
	}

	//검색조건
	if(data.trNum && data.trNum == "search"){
		$("#searchForm #srchSrcmkCd").val(data.srcmkCd);
	}else{
	//검색조건 외
		//등록가능상태 확인 후 setting
		if(!eventCheckSelAvailProd(data)) return;
	}
	
}

//선택가능상품 확인
function eventCheckSelAvailProd(json){
	if(json == null){
		alert("판매코드 선택정보가 존재하지 않습니다.");
		return false;
	}
	
	var srcmkCd = $.trim(json.srcmkCd);		//판매코드
	var prodCd = $.trim(json.prodCd);		//상품코드
	var purDept = $.trim($("#srchPurDept").val());	//구매조직코드  
	var trNum = $.trim(json.trNum);			//선택한 행
	
	if(purDept == ""){
		alert("구매조직 정보가 존재하지 않습니다.");
		return;
	}
	
	//list에서 중복확인
	var flag = true;
	$("#dataListbody input[name='srcmkCd'][value='"+srcmkCd+"'][value!='']").each(function(){
		//동일 tr이면 체크 x
		if($(this).closest("tr").hasClass(trNum)) return;
		
		var gridSrcmkCd = $.trim($(this).closest("tr").find("input[name='srcmkCd']").val());	//현재 grid 판매코드
		var gridProdCd = $.trim($(this).closest("tr").find("input[name='prodCd']").val());		//현재 grid 상품코드
		var rnum = $.trim($(this).closest("tr").find("span[name='rnum']").text());
		
		if(gridProdCd == prodCd && gridSrcmkCd == srcmkCd){
			alert("이미 리스트에 추가된 상품코드/판매코드입니다.\n등록된 행:"+rnum);
			flag = false;
			return false;
		}
	});
	
	if(!flag) return false;
	
	var searchInfo = {};
	searchInfo.srcmkCd = srcmkCd;
	searchInfo.prodCd = prodCd;
	searchInfo.purDept = purDept;
	
	//기등록된 데이터 체크
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/selectCheckProdChgCostSelOkStatus.json"/>',
		data : JSON.stringify(searchInfo),
		success : function(data) {
			var msg = data.msg;
			if("success" == msg){
				//판매바코드 callback data setting
				fncSetSaleBarcodeData(json);
			}else{
				var errMsg = (data.errMsg != null && data.errMsg != "")?data.errMsg:"선택이 불가능한 판매코드입니다.";
				alert(errMsg);
				return false;
			}
		}
	});
	
	return true;
}

//판매바코드 callback data setting
function fncSetSaleBarcodeData(json){
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
	var dispUnit = json.dispUnit;	//표시단위
	
	//data setting
	//대상 row
	var tgRow = $("#dataListbody tr."+trNum);
	
	//data setting
	tgRow.find("input[name='prodPatFg']").val(prodPatFg);	//상품유형코드
	tgRow.find("input[name='prodCd']").val(prodCd);			//상품코드
	tgRow.find("input[name='srcmkCd']").val(srcmkCd);		//판매코드
	tgRow.find("input[name='l1Cd']").val(l1Cd);				//대분류
	tgRow.find("input[name='l2Cd']").val(l2Cd);				//중분류
	tgRow.find("input[name='l3Cd']").val(l3Cd);				//소분류
	tgRow.find("input[name='orgCost']").val(orgCostFmt);	//기존원가
	tgRow.find("input[name='dispUnit']").val(dispUnit);		//표시단위
	
	//text setting
	tgRow.find("span[name='l1Nm']").text(l1Nm);			//대분류명
	tgRow.find("span[name='l2Nm']").text(l2Nm);			//중분류명
	tgRow.find("span[name='l3Nm']").text(l3Nm);			//소분류명
	tgRow.find("span[name='prodNm']").text(prodNm);		//상품명
	
	tgRow.find("select[name='costRsnCd']").change();
}

//저장
function eventSave(){
	var datalen = $("#dataListbody tr").length;
	
	if(datalen == 0){
		alert("저장할 대상상품 리스트를 입력하세요.");
		return;
	}
	
	var saveInfo = {};
	var prodDataArr = [];
	
	//valdation
	var purDept = $.trim($("#srchPurDept").val());	//구매조직
	var venCd = $.trim($("#srchVenCd").val());		//파트너사코드
	var nbPbGbn = $.trim($("#srchNbPbGbn").val());	//nb,pb구분
	
	var flag = true;
	
	$("#dataListbody tr").each(function(){
		var prodPatFg = $(this).find("input[name='prodPatFg']").val();				//상품유형코드
		var srcmkCd = $(this).find("input[name='srcmkCd']").val();					//판매코드
		var costRsnCd = $(this).find("select[name='costRsnCd']").val();				//변경사유
		var costRsnDtlCd = $(this).find("select[name='costRsnDtlCd']").val();		//변경상세사유
		var chgReqCostDt = $(this).find("input[name='chgReqCostDt']").val().replace(/\D/g,"");	//변경시작일자
		var prStat = $.trim($(this).find("input[name='prStat']").val())				//진행상태
		var rnum = $.trim($(this).find("span[name='rnum']").text());				//행번호
		var dcNum = $.trim($(this).find("input[name='dcNum']").val());				//공문번호
		
		if(dcNum != "") return;
		
		if("" != prStat && "00" != prStat){
			alert("승인대기중이거나 이미 승인/반려 상태입니다.\n저장이 불가능합니다.\n행번호:"+rnum);
			flag = false;
			return false;
		}

		if(srcmkCd == ""){
			alert("판매코드는 필수 입력입니다.");
			$(this).find("input[name='srcmkCd']").focus();
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
		alert("저장 가능한 대상상품 리스트가 존재하지 않습니다.\n공문이 생성된 건은 수정이 불가능합니다.");
		return;
	}
	
	//대상상품 list 구성
// 	$("#dataListbody tr").each(function(){
// 		var prodData = {};
		
// 		$(this).find("input, select").not(".notInc").map(function(){
// 			if(this.name != undefined && this.name != null && this.name != ""){
// 				if($(this).hasClass("day") || $(this).hasClass("amt")){
// 					prodData[this.name] = $.trim($(this).val()).replace(/\D/g,"");
// 				}else{
// 					prodData[this.name] = $.trim($(this).val());
// 				}
// 			}
// 		});
		
// 		if(prodData != null){
// 			prodDataArr.push(prodData);
// 		}
// 	});
	
	saveInfo.prodDataArr = prodDataArr;	//대상상품 list
	saveInfo.purDept = purDept;			//구매조직
	saveInfo.venCd = venCd;				//파트너사코드
	saveInfo.nbPbGbn = nbPbGbn;			//NB,PB구분
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/insertTpcProdChgCostInfo.json"/>',
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

//조회
function eventSearch(chkYn){
	//검색조건 확인
	if(!fncValidate()) return;

	if($.trim(chkYn) == "Y"){
		//수정, 등록한 row가 있을 경우
		var len = $("#dataListbody tr[data-rowStat='U'], #dataListbody tr[data-rowStat='I']").length;
		if(len > 0){
			if(!confirm("조회 시, 입력 중인 대상상품 리스트가 초기화됩니다.\n진행하시겠습니까?")) return;
		}
	}
	
	var searchInfo = {};
	$("#searchForm").find("input, select").each(function(){
		if(this.name != null && this.name != ""){
			if($(this).hasClass("day")){
				searchInfo[this.name] = $.trim($(this).val()).replace(/\D/g,"");
			}else{
				searchInfo[this.name] = $.trim($(this).val());
			}
		}
	});
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/selectTpcProdChgCostDetail.json"/>',
		data : JSON.stringify(searchInfo),
		success : function(data) {
			//datalist 초기화
			setTbodyInit("dataListbody");
			//체크박스 전체선택 초기화
			$("#chkAll").prop("checked", false);
			//데이터 셋팅
			fncSetData(data);
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
			var costRsnCd = itemList[j].costRsnCd;		//변경사유코드
			var prodPatFg = itemList[j].prodPatFg;		//상품유형구분코드
			var costRsnDtlCd = itemList[j].costRsnDtlCd;//변경상세사유코드
			var count = itemList[j].count;				//row index
			
			//요청상세사유 obj
			var rsnDtlObj = $("#dataListbody tr.r"+count).find("select[name='costRsnDtlCd']");
			//요청상세사유 select option 구성
			_setOptCostRsnDtlCd(rsnDtlObj, prodPatFg, costRsnCd);
			
			//요청상세사유 값 setting
			$("#dataListbody tr.r"+count).find("select[name='costRsnDtlCd']").val(costRsnDtlCd);
		}
	}else{
		//datalist 초기화
		setTbodyInit("dataListbody");
	}
	
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

//공문생성
let dcCreProdDataArr=[];	//공문생성대상 list
function fncCreDcDoc(){
	dcCreProdDataArr = [];
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
	var prodPatFg = $.trim($("#srchProdPatFg").val());	//상품유형구분
	var taxFg = $.trim($("#taxFg").val());		//면과세구분
	var dcCreGbn = "";		//공문생성구분
	
	//검색조건 체크 (공통 필수 data)
	if(!fncValidate()) return;
	
	var prodDataArr = [];	//공문생성대상
	
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
		var dcNum = $.trim($(this).find("input[name='dcNum']").val());		//공문번호
		var dcStat = $.trim($(this).find("input[name='dcStat']").val());	//공문진행상태
		
		//행 등록, 수정상태 확인
		if(rowStat != "R"){
			alert("신규 추가 또는 변경된 데이터가 존재합니다.\n저장을 먼저 진행해주세요.\n행번호:"+rnum);
			flag = false;
			return false;
		}
		
		//진행상태확인
		if("" != prStat && "00" != prStat){
			alert("승인대기중이거나 이미 승인/반려 상태입니다.\nMD협의요청이 불가능합니다.\n행번호:"+rnum);
			flag = false;
			return false;
		}
		
		//원가변경요청data 등록되지않음
		if(reqNo == "" || srcmkCd == "" || prodCd == "" || seq == ""){
			alert("저장을 먼저 진행해주세요.\n행번호:"+rnum);
			flag = false;
			return false;
		}
		
		//원가변경요청일이 과거일자임
		if(!fncChkChgReqCostDt($(this).find("input[name='chgReqCostDt']"))){
			flag=false;
			return false;
		}
		
		//이미 생성된 공문 존재
// 		if(dcNum != "" && dcStat != "" && dcStat != "10"){
		if(dcNum != ""){
			alert("이미 생성된 공문이 존재합니다.\n행번호:"+rnum);
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
	
	dcCreProdDataArr = prodDataArr;	//대상상품 list	
	
	//////////////////////////////////
	var pData = [];
	pData.purDept = purDept; 		//구매조직
	pData.selLen = tgObj.length;	//공문생성 대상 개수
	
	var params = "";
	Object.keys(pData).forEach(function(k, i){
	  params = params+(i===0?"?":"&")+k+"="+pData[k];  
	});
	
	var targetUrl = "<c:url value='/edi/product/insCommDcDocCrePopup.do'/>"+params;
	Common.centerPopupWindow(targetUrl, 'dcDocCrePopup', {width: 480, height: 180});
}

//공문생성을 위한 ECS 정보 Callback
function fncCbDcDocCreBefore(json){
	if(json == null){
		alert("공문 생성을 위한 기본정보가 모두 입력되지 않았습니다.");
		return;
	}
	
	var ecsWrtId = $.trim(json.ecsWrtId);		//ECS 작성자아이디
	var dcCreGbn = $.trim(json.dcCreGbn);		//공문생성구분(수신처)
	
	if(ecsWrtId == "" || dcCreGbn == ""){
		alert("공문 생성을 위한 기본정보가 모두 입력되지 않았습니다.");
		return;
	}
	
	var saveInfo = {};
	
	var purDept = $.trim($("#srchPurDept").val());		//구매조직
	var venCd = $.trim($("#srchVenCd").val());			//파트너사코드
	var nbPbGbn = $.trim($("#srchNbPbGbn").val());		//nb,pb구분
	var prodPatFg = $.trim($("#srchProdPatFg").val());	//상품유형구분
	var taxFg = $.trim($("#taxFg").val());				//면과세구분
	
	saveInfo.purDept = purDept;		//구매조직
	saveInfo.prodPatFg = prodPatFg;	//상품유형구분 - 규격
	saveInfo.taxFg = taxFg;			//면과세구분
	saveInfo.dcCreGbn = dcCreGbn;	//공문생성구분(슈퍼일땜나 사용)
	saveInfo.ecsWrtId = ecsWrtId;	//ECS 작성자아이디
	saveInfo.prodDataArr = dcCreProdDataArr;	//대상상품 list
	
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

//공문생성 old(마트or슈퍼 공문으로 생성됨)
function fncCreDcDoc_OLD(){
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
	var prodPatFg = $.trim($("#srchProdPatFg").val());	//상품유형구분
	var taxFg = $.trim($("#taxFg").val());		//면과세구분
	var dcCreGbn = "";		//공문생성구분
	
	
	//검색조건 체크 (공통 필수 data)
	if(!fncValidate()) return;
	
	var saveInfo = {};
	var prodDataArr = [];	//공문생성대상
	
	//TODO_JIA :::: 추후 공통부분 처리 필요
	saveInfo.purDept = purDept;		//구매조직
	saveInfo.prodPatFg = prodPatFg;	//상품유형구분 - 규격
	saveInfo.taxFg = taxFg;			//면과세구분
// 	saveInfo.dcCreGbn = "MT";		//공문생성구분
	/////////////////	
	
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
		
		//행 등록, 수정상태 확인
		if(rowStat != "R"){
			alert("신규 추가 또는 변경된 데이터가 존재합니다.\n저장을 먼저 진행해주세요.\n행번호:"+rnum);
			flag = false;
			return false;
		}
		
		//진행상태확인
		if("" != prStat && "00" != prStat){
			alert("승인대기중이거나 이미 승인/반려 상태입니다.\nMD협의요청이 불가능합니다.\n행번호:"+rnum);
			flag = false;
			return false;
		}
		
		//원가변경요청data 등록되지않음
		if(reqNo == "" || srcmkCd == "" || prodCd == "" || seq == ""){
			alert("저장을 먼저 진행해주세요.\n행번호:"+rnum);
			flag = false;
			return false;
		}
		
		//원가변경요청일이 과거일자임
		if(!fncChkChgReqCostDt($(this).find("input[name='chgReqCostDt']"))){
			flag=false;
			return false;
		}
		
		//이미 생성된 공문 존재
// 		if(dcNum != "" && dcStat != "" && dcStat != "10"){
		if(dcNum != ""){
			alert("이미 생성된 공문이 존재합니다.\n행번호:"+rnum);
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
	
	if(!confirm("선택하신 "+tgObj.length+"건에 대해 공문을 생성 하시겠습니까?")) return;
	
	saveInfo.prodDataArr = prodDataArr;	//대상상품 list
	
	
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
					alertMessage = "ECS 응답이 없습니다.";
					break;
				case "ERR02" :
					alertMessage = "ECS 응답결과가 없습니다.";
					break;
				case "ERR03" :
					alertMessage = "ECS URL 추출에 실패하였습니다.";
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

//변경상세사유 option 셋팅
function _setOptCostRsnDtlCd(obj, prodPatFg, costRsnCd){
	//변경 상세사유 option clear
	$(obj).closest("tr").find("select[name='costRsnDtlCd']").find("option[value!='']").remove();
	
	var optHtml = "";			//변경상세사유 select option html
	
	if(prodPatFg == "0"){		//상품유형 - 신선비규격
		switch(costRsnCd){
		case "01":	//인상
			optHtml += "<option value='01'>원부자재상승</option>\n";			
			break;
		case "02":
			optHtml += "<option value='02'>원부자재하락</option>\n";		
			break;
		default: break;
		}
	}else{						//상품유형 - 일반
		switch(costRsnCd){
			case "01":	//인상
				optHtml += "<option value='01'>원부자재상승</option>\n";
				optHtml += "<option value='03'>경비상승</option>";
				break;
			case "02":	//인하
				optHtml += "<option value='02'>원부자재하락</option>\n";
				optHtml += "<option value='04'>생산비 절감</option>\n";
				optHtml += "<option value='05'>판매량 저하</option>\n";
				break;
				default:break;
		}	
	}
	//변경상세사유 option setting
	$(obj).closest("tr").find("select[name='costRsnDtlCd']").append(optHtml);
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
		
		//행 등록, 수정상태 확인
		if(rowStat != "R"){
			alert("신규 추가 또는 변경된 데이터가 존재합니다.\n저장을 먼저 진행해주세요.\n행번호:"+rnum);
			flag = false;
			return false;
		}
		
		//진행상태확인
		if("" != prStat && "00" != prStat){
			alert("승인대기중이거나 이미 승인/반려 상태입니다.\nMD협의요청이 불가능합니다.\n행번호:"+rnum);
			flag = false;
			return false;
		}
		
		//원가변경요청data 등록되지않음
		if(reqNo == "" || srcmkCd == "" || prodCd == "" || seq == ""){
			alert("저장을 먼저 진행해주세요.\n행번호:"+rnum);
			flag = false;
			return false;
		}
		
		//공문미생성
// 		if(""==dcNum && (dcStat == "" || dcStat == "10")){
		if(""==dcNum){
			alert("공문이 생성되지 않았습니다.\n공문발송 이후 MD협의요청이 불가능합니다.\n행번호:"+rnum);
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
				alert("요청되었습니다.");
				eventSearch();
			}else{
				var errMsg = $.trim(data.errMsg) != "" ? data.errMsg : "처리 중 오류가 발생했습니다.";
				alert(errMsg);
			}
			
		}
	});
}
</script>

<!-- 요청내역 datalist template -->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r\${count}" bgcolor="#ffffff" data-rowStat="<c:out value='\${rowStat}'/>">
	<td class="chkGbn tdc" style="padding:0px">
		<input type="checkbox" id="cbox\${count}" name="cbox" class="notInc"/>
		<input type="hidden" name="reqNo" 		value="<c:out value='\${reqNo}'/>"/>
		<input type="hidden" name="seq" 		value="<c:out value='\${seq}'/>"/>
		<input type="hidden" name="prodPatFg" 	value="<c:out value='\${prodPatFg}'/>"/>
		<input type="hidden" name="dispUnit" 	value="<c:out value='\${dispUnit}'/>"/>
		<input type="hidden" name="prStat"		value="<c:out value='\${prStat}'/>"/>
	</td>
	<td class="tdc"><span name="rnum"><c:out value='\${rnum}'/></span></td>
	<td class="tdc">
		<input type="text" name="prodCd" value="<c:out value='\${prodCd}'/>" disabled/>
	</td>
	<td class="tdc">
		{%if reqNo != null && reqNo != ""%}
		<input type="text" id="srcmkCd\${count}" name="srcmkCd" value="<c:out value='\${srcmkCd}'/>" oninput="this.value=this.value.replace(/\D/g, '')" disabled/>
		{%else%}
		<input type="text" id="srcmkCd\${count}" name="srcmkCd" value="<c:out value='\${srcmkCd}'/>" style="width:100px" oninput="this.value=this.value.replace(/\D/g, '')"/>
		<a href="javascript:void(0);" class="btn" onclick="fncOpenPopSrcmkCd(this)"><span>찾기</span></a>
		{%/if%}
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
		{%if dcNum == null || dcNum == "" %}
		<input type="text" class="day" id="chgReqCostDt\${count}" name="chgReqCostDt" style="width:90px;" value="<c:out value='\${chgReqCostDtFmt}'/>" disabled> <img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor:hand;" />
		{%else%}
		<input type="text" class="day" id="chgReqCostDt\${count}" name="chgReqCostDt" style="width:90px;" value="<c:out value='\${chgReqCostDtFmt}'/>" disabled>
		{%/if%}
	</td>
	<td class="tdc">
		<input type="text" id="orgCost\${count}" name="orgCost" value="<c:out value='\${orgCostFmt}'/>" style="text-align:right;width:100%;" class="amt" disabled/>
	</td>
	<td class="tdc">
		{%if dcNum == null || dcNum == "" %}
		<input type="text" id="chgReqCost\${count}" name="chgReqCost" value="<c:out value='\${chgReqCostFmt}'/>" style="text-align:right;width:100%;" class="amt" oninput="this.value=this.value.replace(/\D/g,'')"/>
		{%else%}
		<input type="text" id="chgReqCost\${count}" name="chgReqCost" value="<c:out value='\${chgReqCostFmt}'/>" style="text-align:right;width:100%;" class="amt" oninput="this.value=this.value.replace(/\D/g,'')" disabled/>
		{%/if%}
	</td>
	<td class="tdc">
		<input type="text" id="incDecRate\${count}" name="incDecRate" value="<c:out value='\${incDecRate}'/>" style="text-align:right;width:80%;" class="notInc" disabled/> %
	</td>
	<td class="tdc">
		{%if dcNum == null || dcNum == "" %}
		<select id="costRsnCd\${count}" name="costRsnCd">
			<option value="">선택</option>
			<option value="01" \${costRsnCd == '01'?'selected':''}>인상</option>
			<option value="02" \${costRsnCd == '02'?'selected':''}>인하</option>
		</select>
		{%else%}
		<select id="costRsnCd\${count}" name="costRsnCd" disabled>
			<option value="">선택</option>
			<option value="01" \${costRsnCd == '01'?'selected':''}>인상</option>
			<option value="02" \${costRsnCd == '02'?'selected':''}>인하</option>
		</select>
		{%/if%}
	</td>
	<td class="tdc">
		{%if dcNum == null || dcNum == "" %}
		<select id="costRsnDtlCd\${count}" name="costRsnDtlCd">
			<option value="">선택</option>
		</select>
		{%else%}
		<select id="costRsnDtlCd\${count}" name="costRsnDtlCd" disabled>
			<option value="">선택</option>
		</select>
		{%/if%}
	</td>
	<td class="tdc">
		{%if dcNum == null || dcNum == "" %}
		<input type="text" id="cmt\${count}" name="cmt" value="<c:out value='\${cmt}'/>" style="width:100%"/>
		{%else%}
		<input type="text" id="cmt\${count}" name="cmt" value="<c:out value='\${cmt}'/>" style="width:100%" disabled/>
		{%/if%}
	</td>
	<td class="tdc">
		<span name="dcNum"><c:out value='\${dcNum}'/></span>
		<%--
		{%if dcStat != null && dcStat != "" && dcStat != "10"%}
		<span name="dcNum"><c:out value='\${dcNum}'/></span>
		{%else%}
		<span name="dcNum">-</span>
		{%/if%}
		--%>
		<input type="hidden" name="dcNum" 		value="<c:out value='\${dcNum}'/>"/>
		<input type="hidden" name="dcStat"		value="<c:out value='\${dcStat}'/>"/>
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
						<li class="tit">원가변경요청</li>
						<li class="btn">
							<a href="javascript:void(0);" class="btn" onclick="eventSearch('Y')"><span><spring:message code="button.common.inquire"/></span></a>
						</li>
					</ul>
					<!-- 검색조건 start -->
					<form id="searchForm" name="searchForm">
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
								<th><span class="star">*</span> 구매조직</th>
								<td>
									<select id="srchPurDept" name="srchPurDept">
										<option value="">선택</option>
										<option value="KR02">롯데마트</option>
										<option value="KR03">MAXX</option>
										<option value="KR04">롯데슈퍼</option>
										<option value="KR09">오카도</option>
									</select>
								</td>
								<th><span class="star">*</span> 파트너사</th>
								<td>
									<html:codeTag objId="srchVenCd" objName="srchVenCd" width="150px;" dataType="CP" comType="SELECT" defName="선택" formName="form"/>
								</td>
								<th><span class="star">*</span> NB/PB</th>
								<td>
									<select id="srchNbPbGbn" name="srchNbPbGbn">
										<option value="">선택</option>
										<option value="01">NB</option>
										<option value="02">PB</option>
									</select>
								</td>
							</tr>
							<tr>
								<th><span class="star">*</span> 상품유형</th>
								<td>
									<select id="srchProdPatFg" name="srchProdPatFg">
										<option value="">선택</option>
										<option value="0">신선비규격</option>
										<option value="1">규격</option>
										<option value="9">패션</option>
									</select>
								</td>
								<th><span class="star" style="display:none;">*</span> 과세유형</th>
								<td>
									<select id="srchTaxFg" name="srchTaxFg">
										<option value="">선택</option>
										<option value="0">면세</option>
										<option value="1">과세</option>
									</select>
								</td>
								<th>변경시작일자</th>
								<td><input type="text" class="day" id="srchChgReqCostDt" name="srchChgReqCostDt" style="width:80px;" value="<c:out value='${srchFromDt}'/>"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchChgReqCostDt');" style="cursor:hand;" /></td>
							</tr>
							<tr>
								<th>판매코드</th>
								<td colspan="5">
									<input type="text" name="srchSrcmkCd" id="srchSrcmkCd" style="width:100px;"/>
									<a href="javascript:void(0);" class="btn" onclick="fncOpenSrchPopSrcmkCd()"><span>찾기</span></a>
								</td>
								<%-- 
								<th>상태</th>
								<td>
									<select id="srchPrStat" name="srchPrStat">
										<option value="">전체</option>
										<option value="00">작성중</option>
										<option value="01">진행</option>
										<option value="02">승인</option>
										<option value="03">반려</option>
									</select>
								</td>
								--%>
							</tr>
						</tbody>
					</table>
					</form>
					<!-- ./검색조건 end -->
					<!-- 검색내역 start -->
					<div class="wrap_con">
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">대상상품리스트</li>
								<li class="btn">
									<a href="javascript:void(0);" class="btn" onclick="eventExcelTmplDown()"><span>Excel 양식 다운로드</span></a>
									<a href="javascript:void(0);" class="btn" onclick="eventExcelUpload()"><span>Excel 업로드</span></a>
									<a href="javascript:void(0);" class="btn" onclick="fncAddRow()"><span>행추가</span></a>
									<a href="javascript:void(0);" class="btn" onclick="fncDelRow()"><span>행삭제</span></a>
									<a href="javascript:void(0);" class="btn" onclick="eventSave()"><span>저장</span></a>
									<a href="javascript:void(0);" class="btn" onclick="fncCreDcDoc()"><span>공문생성</span></a>
									<a href="javascript:void(0);" class="btn" onclick="eventSendReq()"><span>MD협의요청</span></a>
								</li>
							</ul>
							<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll; white-space:nowrap;">
								<form id="reqDetailForm" name="reqDetailForm">
								<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=1800px; bgcolor="efefef" style="table-layout:fixed;" class="sub-table">
									<colgroup>
										<col width="2%">	
										<col width="2%">	
										<col width="8%">	
										<col width="11%">	
										<col width="8%">	
										<col width="8%">	
										<col width="8%">	
										<col width="10%">	
										<col width="10%">	
										<col width="8%">	
										<col width="8%">	
										<col width="8%">	
										<col width="5%">	
										<col width="8%">	
										<col width="15%">	
										<col width="8%">	
									</colgroup>
									<thead>
										<tr bgcolor="#e4e4e4">
											<th><input type="checkbox" id="chkAll"/></th>
											<th>No</th>
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
											<th>공문번호</th>
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
			</div>
		</div>
	</div>
	<!--  -->
	<form id="hiddenForm" name="hiddenForm">
		<input type="hidden" id="" name=""/>
	</form>
	
</body>
</html>