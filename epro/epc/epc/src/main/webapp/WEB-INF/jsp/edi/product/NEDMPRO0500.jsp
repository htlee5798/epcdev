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
<%-- <%@ include file="./CommonProductFunction.jsp" %> --%>
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

let srchProdMart = "";		//판매코드 검색조건_마트상품
let srchProdMaxx = "";		//판매코드 검색조건_MAXX상품
let srchProdSuper = "";		//판매코드 검색조건_슈퍼상품
let srchProdCfc = "";		//판매코드 검색조건_CfC상품
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
// 		var prodPatFg = $.trim($(this).closest("tr").find("input[name='prodPatFg']").val());		//상품유형구분코드
		var freshStdYn = $.trim($(this).closest("tr").find("input[name='freshStdYn']").val());		//신선규격상품구분
		
		//증감액에 따른 원가변경 사유 validation
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
	$("#srchVenCd, #srchNbPbGbn").on("focus", function(){
		bfSrch = $.trim($(this).val());
	}).change(function(){
		afSrch = $.trim($(this).val());

		//판매코드 팝업 구매조직 검색조건 Disabled
		fncSetSrchProdChansDisabledAll();
		
		//데이터 있을 때만 체크
		var datalen = $("#dataListbody tr").length;
		if(datalen == 0){
			//--------------------------------
			//데이터 없는 경우, 변환값 바로적용
			switch(this.id){
				case "srchVenCd":	//협력사코드
					//파트너사 선택 가능 구매조직 활성화
					eventSelChkVenPurDept(afSrch);
					break;
				default:
					break;
			}
			//--------------------------------
			return;
		}
		
// 		if(this.name == "srchTaxFg"){
// 			var srchProdPatFg = $.trim($("#srchProdPatFg").val());	//상품유형
// 			if(srchProdPatFg != "0"){	//신선이 아닐 경우에는 리스트 초기화 안함
// 				return;
// 			}
// 		}

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
					//파트너사 선택 가능 구매조직 활성화
					eventSelChkVenPurDept(afSrch);
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
	
	//판매코드 변경 시,
	$(document).on("change", "#dataListbody input[name='srcmkCd']", function(e){
		//해당 row의 상품정보 관련 data clear
		fncClearProdRowInfo($(this));
	});
	
	//구매조직 체크박스 변경 시
	$(document).on("click", "input[name='srchPurDepts']", function(e){
		//데이터 있을 때만 체크
		var datalen = $("#dataListbody tr").length;
		if(datalen == 0){
			//구매조직별 상품조회 조건 셋팅
			fncSetSrchProdChans();
			return;
		}
		
		var chked = $(this).is(":checked");
		
		if(!confirm("구매조직 변경 시, 입력한 대상상품 리스트가 초기화 됩니다.\n진행하시겠습니까?")){
			//취소 시, 이전선택값 재셋팅
			$(this).prop("checked", !chked);
			return; 
		}
		
		//구매조직별 상품조회 조건 셋팅
		fncSetSrchProdChans();
		
		//대상상품리스트 초기화
		setTbodyInit("dataListbody");
	});
	
	
	//구매조직 영역 disabled
	$("input[name='srchPurDepts']").prop("disabled", true);
});

//금액 비교를 위해 String 금액으로 변환 
function parseCost(val) {
	var numVal = '0';
	
	//paremeter 없을 경우 0 return
	if(val == undefined || val == null || val == "") return numVal;
	
	numVal = val.replace(/[^0-9.]/g, '');
	numVal = Math.floor(numVal);
	
	if(isNaN(numVal)){
		numVal = 0;
	}
	return numVal;
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
// 	var srchPurDept = $.trim($("#srchPurDept").val());	//구매조직
	var srchVenCd = $.trim($("#srchVenCd").val());		//파트너사코드
	var srchNbPbGbn = $.trim($("#srchNbPbGbn").val());	//NB,PB 구분
// 	var srchProdPatFg = $.trim($("#srchProdPatFg").val());	//상품유형
// 	var srchTaxFg = $.trim($("#srchTaxFg").val());		//과세유형
	
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
	
	//구매조직 누락
	var purDeptLen = $("#searchForm input[name='srchPurDepts']:checked").not(":disabled").length;	//선택한 구매조직
	if(purDeptLen == 0){
		alert("구매조직을 1개 이상 선택해주세요.");
		return;
	}
	
	//상품유형 누락
// 	if(srchProdPatFg == ""){
// 		alert("상품유형은 필수 선택 항목입니다.");
// 		$("#srchProdPatFg").focus();
// 		return false;
// 	}
	
// 	//상품유형=신선일 경우, 과세유형 필수
// 	if(srchProdPatFg == "0" && srchTaxFg == ""){
// 		alert("신선 상품일 경우, 과세 유형은 필수 선택 항목입니다.");
// 		$("#srchTaxFg").focus();
// 		return false;
// 	}
	
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
	newRowData.contCode = "";
	
	newRowData.chgReqCostDtFmt = $.trim($("#srchChgReqCostDt").val());		//원가변경요청일
	
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
	
	//선택한 행 삭제
	$("#dataListbody tr").find("input[name='cbox']:checked").closest("tr").remove();
	
// 	//DB에서 삭제할 데이터가 있을 경우
// 	if(prodDataArr != null && prodDataArr.length > 0){
// 		saveInfo.prodDataArr = prodDataArr;		//삭제대상리스트
		
		
// 		$.ajax({
// 			contentType : 'application/json; charset=utf-8',
// 			type : 'post',
// 			dataType : 'json',
// 			url : '<c:url value="/edi/product/deleteTpcProdChgCostItem.json"/>',
// 			data : JSON.stringify(saveInfo),
// 			success : function(data) {
// 				var msg = data.msg;
				
// 				if("success" == msg){
// 					alert("삭제되었습니다.");

// 					//선택한 행 삭제
// 					$("#dataListbody tr").find("input[name='cbox']:checked").closest("tr").remove();
// 				}else{
// 					var errMsg = $.trim(data.errMsg) != "" ? data.errMsg : "처리 중 오류가 발생했습니다.";
// 					alert(errMsg);
// 				}
				
// 			}
// 		});
		
// 	}else{
// 		//선택한 행 삭제
// 		$("#dataListbody tr").find("input[name='cbox']:checked").closest("tr").remove();
// 	}
	
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
	var form = document.excelForm;
	
	var chkVal = "500";  //업도르 양식 구분 값 
	var fileName = "원가변경요청_일괄업로드";
	var headerNm = "";
	
	if (!confirm('엑셀 양식을 다운로드 하시겠습니까?')) {
		$("#pgmIdVal").val("");
		return;
	}
	
	$("#fileName").val(encodeURIComponent(fileName));  // 파일명
	$("#optionVal").val(chkVal);  //양식 번호 
	
	form.target = "frameForExcel";
	form.action = '<c:url value="/edi/product/selectTpcProdChgCostDetailExcelDown.do"/>';
	form.submit();
}

//엑셀템플릿 업로드
function eventExcelUpload(){
	var targetUrl = '<c:url value="/edi/comm/commExcelUploadPopupInit.do"/>';
	var heightVal = 350;
	var excelSysKnd = "500";	//엑셀 업로드 원가변경요청 구분값
	
	//valdation
	var purDeptLen = $("#searchForm input[name='srchPurDepts']:checked").not(":disabled").length;	
	if(purDeptLen == 0){
		alert("구매조직은 필수 선택 항목입니다.");
		$("#srchPurDept").focus();
		return;
	}
	
	if($("#srchVenCd").val() == ""){
		alert("파트너사는 필수 선택 항목입니다.");
		$("#srchVenCd").focus();
		return;
	}
	
	if($("#srchNbPbGbn").val() == ""){
		alert("NB/PB 구분은 필수 선택 항목입니다.");
		$("#srchNbPbGbn").focus();
		return;
	}
	
	//구매조직 array
	var purDepts = [];
	$("#searchForm input[name='srchPurDepts']:checked").not(":disabled").each(function(){
		purDepts.push($.trim($(this).val()));
	});
	
	Common.centerPopupWindow(targetUrl+"?excelSysKnd="+excelSysKnd+"&entpCd="+$("#srchVenCd").val()+"&nbPbGbn="+$("#srchNbPbGbn").val()+"&purDepts="+purDepts, 'excelUploadPopup', {width : 730, height : heightVal});

}


//엑셀 업로드 콜백 데이터 세팅
function excelUploadcallBack(data){
	var list = data.resultMap.list;
	
	//list 초기화
	$("#dataListbody").empty();
	
	//데이터리스트 없을 경우 return
	if(list == undefined || list == null || list.length == 0) return;
	
	//row number
	let rnum = 0;
	
	for (var i = 0; i < list.length; i++) {
		var newRowData = {};
		//
		//validation 추가할거면 이 부분에서 해야함 ---
		//
		var chkDuplRnum = $.trim(list[i].chkDuplRnum);		//중복데이터 여부
		
		//중복된 상품일 경우, 첫 번째 데이터만 업로드처리
		if("1" != chkDuplRnum) continue;
		
		
		//공통부 setting
		newRowData.rnum		= ++rnum;		//행번호
		newRowData.reqNo	= "";			//요청번호
		newRowData.rowStat	= "I";			//행상태
		newRowData.count	= ++rowCount;	//row index
		newRowData.dcStat	= "";			//공문발송상태
		newRowData.contCode	= "";			//공문연계번호
		
		//data setting
		newRowData.srcmkCd	= $.trim(list[i].data1) || "";		//판매코드
		newRowData.prodCd	= $.trim(list[i].prodCd) || "";		//상품코드
		newRowData.prodNm	= $.trim(list[i].prodNm) || "";		//상품명
		newRowData.ordUnit	= $.trim(list[i].ordUnit) || "";	//기본단위
		
		//금액 셋팅 (소수점 제거 & comma setting)
		newRowData.chgReqCostFmt = ($.trim(list[i].data4) != "")?setComma(Math.floor(list[i].data2)):"";	//변경요청금액
		newRowData.orgCostFmt = ($.trim(list[i].orgCost) != "")?setComma(Math.floor(list[i].orgCost)):"";	//원가금액
		newRowData.incDecRate = ($.trim(list[i].incDecRate) != "")?$.trim(list[i].incDecRate).toFixed(2):"";//증감율
		
		newRowData.costRsnCd = $.trim(list[i].data3) || ""; 	//변경사유
        newRowData.costRsnDtlCd = $.trim(list[i].data4) || ""; 	//변경상세사유
		newRowData.cmt		= $.trim(list[i].data6) || "";		//비고
		newRowData.chgReqCostDtFmt = formatToYyyyMmDd($.trim(list[i].data5)) || "";	//변경시작일시
		
		newRowData.l3Cd = $.trim(list[i].l3Cd) || ""; //소분류코드
		newRowData.l3Nm = $.trim(list[i].l3Nm) || ""; //소분류명 
		newRowData.l2Cd = $.trim(list[i].l2Cd) || ""; //중분류코드
		newRowData.l2Nm = $.trim(list[i].l2Nm) || ""; //중분류명 
		newRowData.l1Cd = $.trim(list[i].l1Cd) || ""; //대분류코드
		newRowData.l1Nm = $.trim(list[i].l1Nm) || ""; //대분류명 
		
		newRowData.freshStdYn = $.trim(list[i].freshStdYn) || "";	//신선규격상품구분
		newRowData.prodPatFg = $.trim(list[i].prodPatFg) || ""; 	//상품유형
		
		//data append
		$("#dataListTemplate").tmpl(newRowData).appendTo("#dataListbody");
		
		//변경사유, 상세사유 selectbox 구성 -------------------------------
		var lastRow = $("#dataListbody tr:last"); 								// 방금 append된 마지막 tr
		var costRsnCdSel = lastRow.find("select[name='costRsnCd']");			//변경사유 selectbox
		var costRsnDtlCdSel = lastRow.find("select[name='costRsnDtlCd']");		//변경상세사유 selectbox
		//변경사유 값 SETTING
		costRsnCdSel.val(newRowData.costRsnCd);
		//변경상세사유 option list setting
		_setOptCostRsnDtlCd(costRsnCdSel[0], newRowData.freshStdYn, newRowData.costRsnCd);
		//변경상세사유 값 setting
		costRsnDtlCdSel.val(newRowData.costRsnDtlCd);
	}
}

//엑셀에서 넘어온 날짜  포맷
function formatToYyyyMmDd(dateStr) {
    if (!dateStr) return "";

    // 숫자만 남김
    var cleanDate = dateStr.replace(/[^0-9]/g, "");

    if (cleanDate.length === 8) {
        // yyyymmdd -> yyyy-mm-dd
        return cleanDate.replace(/(\d{4})(\d{2})(\d{2})/, "$1-$2-$3");
    } else if (cleanDate.length === 6) {
        // yymmdd -> 20yy-mm-dd (단순 예시)
        return cleanDate.replace(/(\d{2})(\d{2})(\d{2})/, "20$1-$2-$3");
    } else {
        return ""; // 처리할 수 없는 형식
    }
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
	pData.entpCd = $.trim($("#srchVenCd").val());			//선택한 파트너사 코드 있을 경우, 해당 파트너사의 판매코드만 조회
	pData.srcmkCd = $.trim($("#srchSrcmkCd").val());		//입력한 판매코드 있을 경우 셋팅
// 	pData.prodPatFg = $.trim($("#srchProdPatFg").val());	//상품패턴 1-규격상품만 원가변경요청가능
	pData.srchChgOrgCostYn = "Y";							//원가변경 가능 대상 상품만 조회
	pData.taxFg = $.trim($("#srchTaxFg").val());
	
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

//판매코드 찾기 팝업 Open
function fncOpenPopSrcmkCd(obj){
	var pData = [];
	pData.trNum = $(obj).closest('tr').attr('class');
	pData.entpCd = $.trim($("#srchVenCd").val());			//선택한 파트너사 코드 있을 경우, 해당 파트너사의 판매코드만 조회
	pData.srcmkCd = $.trim($(obj).closest('td').find("input[name='srcmkCd']").val());
// 	pData.prodCd = $.trim($(obj).closest('tr').find("input[name='prodCd']").val());
// 	pData.prodPatFg = $.trim($("#srchProdPatFg").val());		//상품패턴 1-규격상품만 원가변경요청가능
	pData.srchChgOrgCostYn = "Y";							//원가변경 가능 대상 상품만 조회
	pData.taxFg = $.trim($("#srchTaxFg").val());
	pData.srchPurDept = $("#searchForm input[name='srchPurDepts']:checked").eq(0).val();		//선택한 첫번째 구매조직 - 원가조회기준
	
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
function setSellCd(data){
	if(data == null){
		alert("상품 데이터가 유효하지 않습니다.\n관리자에게 문의하세요.");
		return;
	}
	
	var srcmkCd = $.trim(data.srcmkCd); 
	
	if(srcmkCd == ""){
		alert("판매코드가 존재하지 않는 상품은 선택이 불가능합니다.");
		return;
	}

	//검색조건
	if(data.trNum && data.trNum == "search"){
		$("#searchForm #srchSrcmkCd").val(srcmkCd);
	}else{

		
		
	//검색조건 외
		//등록가능상태 확인 후 setting
		eventCheckSelAvailProd(data);
	}
	
}

//선택가능상품 확인
function eventCheckSelAvailProd(json){
	if(json == null){
		alert("판매코드 선택정보가 존재하지 않습니다.");
		return false;
	}
	
	var srcmkCd = $.trim(json.srcmkCd);				//판매코드
	var prodCd = $.trim(json.prodCd);				//상품코드
// 	var purDept = $.trim($("#srchPurDept").val());	//구매조직코드  
	var trNum = $.trim(json.trNum);					//선택한 행
	
	var purDeptLen = $("#searchForm input[name='srchPurDepts']:checked").length;	
	if(purDeptLen == 0){
		alert("구매조직을 1개 이상 선택해주세요.");
		return;
	}
	//구매조직 array
	var purDepts = [];
	$("#searchForm input[name='srchPurDepts']:checked").each(function(){
		purDepts.push($.trim($(this).val()));
	});
	
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
	searchInfo.purDepts = purDepts;
// 	searchInfo.purDept = purDept;
	
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
	var ordUnit = json.ordUnit;		//기본단위
	var freshStdYn = $.trim(json.freshStdYn)!=""?$.trim(json.freshStdYn).toUpperCase():"N";	//신선규격상품여부
	
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
	tgRow.find("input[name='ordUnit']").val(ordUnit);		//기본단위
	tgRow.find("input[name='freshStdYn']").val(freshStdYn);	//신선규격상품여부
	
	
	//text setting
	tgRow.find("span[name='l1Nm']").text(l1Nm);			//대분류명
	tgRow.find("span[name='l2Nm']").text(l2Nm);			//중분류명
	tgRow.find("span[name='l3Nm']").text(l3Nm);			//소분류명
	tgRow.find("span[name='prodNm']").text(prodNm);		//상품명
	
//	tgRow.find("select[name='costRsnCd']").change();
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
	var purDeptLen = $("#searchForm input[name='srchPurDepts']:checked").length;	
	if(purDeptLen == 0){
		alert("구매조직을 1개 이상 선택해주세요.");
		return;
	}
	
	//구매조직 array
	var purDepts = [];
	$("#searchForm input[name='srchPurDepts']:checked").each(function(){
		purDepts.push($.trim($(this).val()));
	});
	
// 	var purDept = $.trim($("#srchPurDept").val());	//구매조직
	var venCd = $.trim($("#srchVenCd").val());		//파트너사코드
	var nbPbGbn = $.trim($("#srchNbPbGbn").val());	//nb,pb구분
	
	var flag = true;
	
	$("#dataListbody tr").each(function(){
		var prodPatFg = $(this).find("input[name='prodPatFg']").val();				//상품유형코드
		var srcmkCd = $(this).find("input[name='srcmkCd']").val();					//판매코드
		var prodCd = $.trim($(this).find("input[name='prodCd']").val());			//상품코드
		var costRsnCd = $(this).find("select[name='costRsnCd']").val();				//변경사유
		var costRsnDtlCd = $(this).find("select[name='costRsnDtlCd']").val();		//변경상세사유
		var chgReqCostDt = $(this).find("input[name='chgReqCostDt']").val().replace(/\D/g,"");	//변경시작일자
		var prStat = $.trim($(this).find("input[name='prStat']").val())				//진행상태
		var rnum = $.trim($(this).find("span[name='rnum']").text());				//행번호
		var dcNum = $.trim($(this).find("input[name='dcNum']").val());				//공문번호
		
		if(dcNum != "") return;
		
		if("" != prStat){
			alert("이미 등록된 요청정보입니다.\n등록된 원가변경요청 정보는 <원가변경요청 조회>메뉴에서 수정하실 수 있습니다.");
			flag = false;
			return false;
		}
		
// 		if("" != prStat && "00" != prStat){
// 			alert("승인대기중이거나 이미 승인/반려 상태입니다.\n저장이 불가능합니다.\n행번호:"+rnum);
// 			flag = false;
// 			return false;
// 		}
		if(prodCd == ""){
			alert("상품코드 정보가 없습니다.");
			flag=false;
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
		
		//변경금액에 따른 변경 사유 validation
		if(!fncChkCostChgRsn($(this))){
			flag=false;
			return false;
		}
		
		//원가변경요청일 validation
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
// 		alert("저장 가능한 대상상품 리스트가 존재하지 않습니다.\n공문이 생성된 건은 수정이 불가능합니다.");
		alert("저장 가능한 대상상품 리스트가 존재하지 않습니다.");
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
	saveInfo.purDepts = purDepts;		//구매조직 array
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
				//대상상품리스트 초기화
				setTbodyInit("dataListbody");
				
// 				eventSearch();
			}else{
				var errMsg = $.trim(data.errMsg) != "" ? data.errMsg : "처리 중 오류가 발생했습니다.";
				alert(errMsg);
			}
			
		}
	});
}

<%--
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
			
			//footer 설정 
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
			var costRsnCd = itemList[j].costRsnCd;		//변경사유코드
			var prodPatFg = itemList[j].prodPatFg;		//상품유형구분코드
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
--%>

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

<%--
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
	
//판매코드 변경 시, 선택한 상품관련 정보 삭제
function fncClearProdRowInfo(obj){
	if(obj == null || obj == undefined) return;
	
	var tgObj = $(obj).closest("tr");
	
	tgObj.find("input[name='ordUnit']").val("");
	tgObj.find("input[name='freshStdYn']").val("");
	tgObj.find("input[name='prodCd']").val("");
	tgObj.find("input[name='l1Cd']").val("");
	tgObj.find("input[name='l2Cd']").val("");
	tgObj.find("input[name='l3Cd']").val("");
	tgObj.find("input[name='orgCost']").val("");
	
	tgObj.find("span[name='l1Nm']").text("");
	tgObj.find("span[name='l2Nm']").text("");
	tgObj.find("span[name='l3Nm']").text("");
	tgObj.find("span[name='prodNm']").text("");
	tgObj.find("select[name='costRsnDtlCd'] option[value!='']").remove();
}

//업체코드 별 계열사 & 구매조직 체크
function eventSelChkVenPurDept(venCd){
	if($.trim(venCd) == ""){
		//구매조직 영역 disabled
		$("input[name='srchPurDepts']").prop("disabled", true);
		$("input[name='srchPurDepts']").prop("checked", false);
		
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
	var venPurDepts = json;		//활성화 구매조직
	
	//구매조직 영역 disabled
	$("input[name='srchPurDepts']").prop("disabled", true);
	
	//업체 선택 가능 구매조직이 없을 경우, return 
	if(venPurDepts == undefined || venPurDepts == null || venPurDepts.length == 0){
		$("input[name='srchPurDepts']").prop("checked", false);
		return;
	}
	
	//선택가능한 구매조직만 활성화
	for(var i=0; i<json.length; i++){
		$("input[name='srchPurDepts'][value='"+$.trim(json[i].CODE_ID)+"']").prop("disabled", false);
	}
	
	//비활성화된 구매조직 체크해제
	$("input[name='srchPurDepts']:disabled").prop("checked", false);
	
	//체크한 구매조직 없을 경우, default setting
	var chkedLen = $("input[name='srchPurDepts']:checked").not(":disabled").length;
	if(chkedLen == 0){
		$("input[name='srchPurDepts']").not(":disabled").eq(0).prop("checked", true);
	}
	
	//구매조직별 상품조회 조건 셋팅
	fncSetSrchProdChans();
}

//구매 조직 변경 시, 판매코드 팝업 제한조건 셋팅
function fncSetSrchProdChans(){
	const purDepts = $("input[name='srchPurDepts']:checked").not(":disabled").map(function(){return $(this).val();}).get();
	if(purDepts == null || purDepts.length == 0) return;
	
	//제한조건 초기화
	srchProdMart = "";		//판매코드 검색조건_마트상품
	srchProdMaxx = "";		//판매코드 검색조건_MAXX상품
	srchProdSuper = "";		//판매코드 검색조건_슈퍼상품
	srchProdCfc = "";		//판매코드 검색조건_CfC상품

	//제한조건 재설정
	$.each(purDepts, function(i,val){
		switch(val){
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
	});
}

//판매코드 팝업 구매조직 검색조건 Disabled
function fncSetSrchProdChansDisabledAll(){
	srchProdMart	= "Y";		//판매코드 검색조건_마트상품
	srchProdMaxx	= "Y";		//판매코드 검색조건_MAXX상품
	srchProdSuper	= "Y";		//판매코드 검색조건_슈퍼상품
	srchProdCfc 	= "Y";		//판매코드 검색조건_CfC상품
}

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
</script>

<!-- 요청내역 datalist template -->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r\${count}" bgcolor="#ffffff" data-rowStat="<c:out value='\${rowStat}'/>">
	<td class="chkGbn tdc" style="padding:0px">
		<input type="checkbox" id="cbox\${count}" name="cbox" class="notInc"/>
		<input type="hidden" name="reqNo" 		value="<c:out value='\${reqNo}'/>"/>
		<input type="hidden" name="seq" 		value="<c:out value='\${seq}'/>"/>
		<input type="hidden" name="prodPatFg" 	value="<c:out value='\${prodPatFg}'/>"/>
		<input type="hidden" name="ordUnit" 	value="<c:out value='\${ordUnit}'/>"/>
		<input type="hidden" name="prStat"		value="<c:out value='\${prStat}'/>"/>
		<input type="hidden" name="freshStdYn"	value="<c:out value='\${freshStdYn}'/>"/>
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
		{%if contCode == null || contCode == "" %}
		<input type="text" class="day" id="chgReqCostDt\${count}" name="chgReqCostDt" style="width:90px;" value="<c:out value='\${chgReqCostDtFmt}'/>" disabled> <img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor:hand;" />
		{%else%}
		<input type="text" class="day" id="chgReqCostDt\${count}" name="chgReqCostDt" style="width:90px;" value="<c:out value='\${chgReqCostDtFmt}'/>" disabled>
		{%/if%}
	</td>
	<td class="tdc">
		<input type="text" id="orgCost\${count}" name="orgCost" value="<c:out value='\${orgCostFmt}'/>" style="text-align:right;width:100%;" class="amt" disabled/>
	</td>
	<td class="tdc">
		{%if contCode == null || contCode == "" %}
		<input type="text" id="chgReqCost\${count}" name="chgReqCost" value="<c:out value='\${chgReqCostFmt}'/>" style="text-align:right;width:100%;" class="amt" oninput="this.value=this.value.replace(/\D/g,'')" maxlength="9" />
		{%else%}
		<input type="text" id="chgReqCost\${count}" name="chgReqCost" value="<c:out value='\${chgReqCostFmt}'/>" style="text-align:right;width:100%;" class="amt" oninput="this.value=this.value.replace(/\D/g,'')" maxlength="9" disabled/>
		{%/if%}
	</td>
	<td class="tdc">
		<input type="text" id="incDecRate\${count}" name="incDecRate" value="<c:out value='\${incDecRate}'/>" style="text-align:right;width:80%;" class="notInc" disabled/> %
	</td>
	<td class="tdc">
		{%if contCode == null || contCode == "" %}
		<html:codeTag objId="costRsnCd\${count}" objName="costRsnCd" width="150px;"  parentCode="CORSN"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form"/>
		{%else%}
		<html:codeTag objId="costRsnCd\${count}" objName="costRsnCd" width="150px;"  parentCode="CORSN"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form"/>
		{%/if%}
	</td>
	<td class="tdc">
		{%if contCode == null || contCode == "" %}
		<html:codeTag objId="costRsnDtlCd\${count}" objName="costRsnDtlCd" width="150px;"  parentCode="CORSD"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form"/>
		{%else%}
		<html:codeTag objId="costRsnDtlCd\${count}" objName="costRsnDtlCd" width="150px;"  parentCode="CORSD"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form"/>
		{%/if%}
	</td>
	<td class="tdc">
		{%if contCode == null || contCode == "" %}
		<input type="text" id="cmt\${count}" name="cmt" value="<c:out value='\${cmt}'/>" maxlength="200" style="width:100%"/>
		{%else%}
		<input type="text" id="cmt\${count}" name="cmt" value="<c:out value='\${cmt}'/>" maxlength="200" style="width:100%" disabled/>
		{%/if%}
	</td>
	<%--
	<td class="tdc">
		<span name="dcNum"><c:out value='\${dcNum}'/></span>
		<input type="hidden" name="dcNum" 		value="<c:out value='\${dcNum}'/>"/>
		<input type="hidden" name="dcStat"		value="<c:out value='\${dcStat}'/>"/>
		<input type="hidden" name="contCode"	value="<c:out value='\${contCode}'/>"/>
	</td>
	--%>
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
<%-- 							<a href="javascript:void(0);" class="btn" onclick="eventSearch('Y')"><span><spring:message code="button.common.inquire"/></span></a> --%>
						</li>
					</ul>
					<!-- 검색조건 start -->
					<form id="searchForm" name="searchForm">
					<input type="hidden" id="srchProdPatFg" name="srchProdPatFg" value="1"/>	<%-- 상품패턴구분=규격만 원가변경요청가능 --%>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col width="12%"/>
							<col width="20%"/>
							<col width="12%"/>
							<col width="15%"/>
							<col width="12%"/>
							<col width="25%"/>
						</colgroup>
						<tbody>
							<tr>
								<th><span class="star">*</span> 파트너사</th>
								<td>
									<html:codeTag objId="srchVenCd" objName="srchVenCd" width="150px;" dataType="CP" comType="SELECT" defName="선택" formName="form"/>
								</td>
								<th><span class="star">*</span> NB/PB</th>
								<td>
									<html:codeTag objId="srchNbPbGbn" objName="srchNbPbGbn" width="100px;"  parentCode="NBPB"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form"/>
								</td>
								<th>판매코드</th>
								<td>
									<input type="text" name="srchSrcmkCd" id="srchSrcmkCd" style="width:100px;"/>
									<a href="javascript:void(0);" class="btn" onclick="fncOpenSrchPopSrcmkCd()"><span>찾기</span></a>
								</td>
							</tr>
							<%--<tr>
								 
								<th><span class="star">*</span> 상품유형</th>
								<td>
									<html:codeTag objId="srchProdPatFg" objName="srchProdPatFg" width="150px;"  parentCode="PATFG"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form"/>
								</td>
								<th><span class="star" style="display:none;">*</span> 과세유형</th>
								<td>
									<html:codeTag objId="srchTaxFg" objName="srchTaxFg" width="150px;"  parentCode="TAXFG"  comType="SELECT" dataType="NTCPCD" orderSeqYn="Y" defName="선택" formName="form"/>
								</td>
								<th>변경시작일자</th>
								<td><input type="text" class="day" id="srchChgReqCostDt" name="srchChgReqCostDt" style="width:80px;" value="<c:out value='${srchFromDt}'/>"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchChgReqCostDt');" style="cursor:hand;" /></td>
							</tr>--%>
							<tr>
								<th><span class="star">*</span> 구매조직</th>
								<td colspan="3">
									<html:codeTag objId="srchPurDepts" objName="srchPurDepts" width="150px;"  parentCode="PURDE"  comType="CHECKBOX" dataType="NTCPCD" orderSeqYn="Y" selectParam="" formName="form" subNmUseYn="Y" notIn="Y" subCodeList02="XX"/>
								</td>
								<th>변경시작일자</th>
								<td>
									<input type="text" class="day" id="srchChgReqCostDt" name="srchChgReqCostDt" style="width:80px;" value="<c:out value='${srchFromDt}'/>">
									<img src="/images/epc/layout/btn_cal.gif" class="middle datepicker"  style="cursor:hand;" />
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
<!-- 									<a href="javascript:void(0);" class="btn" onclick="fncCreDcDoc()"><span>공문생성</span></a> -->
<!-- 									<a href="javascript:void(0);" class="btn" onclick="eventSendReq()"><span>MD협의요청</span></a> -->
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
<!-- 										<col width="8%">	 -->
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
<!-- 											<th>공문번호</th> -->
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
								<li class="last">원가변경요청등록</li>
							</ul>
						</div>
					</div>
				</div>
				<!-- footer //-->
			</div>
		</div>
	</div>
	<!--  -->
	<form id="hiddenForm" name="hiddenForm">
		<input type="hidden" id="" name=""/>
	</form>
	
	<form name="excelForm" id="excelForm">
		<iframe name="frameForExcel" style="display:none;"></iframe>
		<input type="hidden" name="fileName" id="fileName"/>
		<input type="hidden" name="optionVal" id="optionVal"/>
		<input type="hidden" name="pgmIdVal" id="pgmIdVal"/>
		<input type="hidden" name="hiddenExcelTmpCd" id="hiddenExcelTmpCd"/>
		<input type="hidden" name="hiddenSrchPurDept" id="hiddenSrchPurDept"/>
		<input type="hidden" name="hiddenSrchVenCd" id="hiddenSrchVenCd"/>
		<input type="hidden" name="hiddenSrchNbPbGbn" id="hiddenSrchNbPbGbn"/>
	</form>
	
</body>
</html>