<%--
	Page Name 	: NEDMPRO0520.jsp
	Description : ESG인증관리
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
<title>ESG 인증관리</title>
<style>
table.sub-table td{padding:5px 3px; word-break:break-word;}
table.sub-table select, table.sub-table input, table.sub-table textarea{max-width:100%;}  
table.sub-table .tdr{text-align:right;}
table.sub-table .tdc{text-align:center;}
</style>

<script type="text/javascript">
const extLimit = "<c:out value='${extLimit}'/>";

$(function(){
	//list datepicker click event 동적 binding
	$(document).on("click", "#dataListbody img.datepicker", function(){
		var dayInput = $(this).prev("input")[0];
		var objId = dayInput.id;
		var objVal = $.trim(dayInput.value);
		
		openCalSetDt("reqDetailForm."+objId, objVal, "fncCallBackCalendar");
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
	
	//파일 객체 변경 시 실행, eventSave사용 (eventSaveFormData 사용XXX)
	$(document).on("change", "#dataListbody input[type=file]", function(){
		var tgRow = $(this).closest("tr");	//대상 row
		tgRow.attr("data-rowStat", "U");	//행상태변경 - 수정
		
		if($(this)[0] == undefined || $(this)[0] == null || $(this)[0].files.length == 0){
			alert("파일 정보 읽기에 실패하였습니다.");
			//파일영역 초기화
			fncClearFile(this);
			return;
		}
		
		var fileNm = $.trim($(this)[0].files[0].name);							//업로드파일명
		var fileExt = fileNm.substr(fileNm.lastIndexOf(".")+1).toLowerCase();	//파일확장자명
		var limitExtArr = $.trim(extLimit).split("|") || [];					//업로드가능 파일확장자
		
		//확장자 체크
		var extLimitTxt = extLimit.replace(/\|/g,",");
		if($.inArray(fileExt, limitExtArr) == -1){
			alert(extLimitTxt + " 파일만 업로드 할수 있습니다.");
			//파일영역 초기화
			fncClearFile(this);
			return;
		}
		
		tgRow.find("span[name=fileIdView]").text(fileNm);
		<%--
		var esgFileId = $.trim(tgRow.find("input[name='esgFileId']").val());//첨부파일아이디
		var prodCd = $.trim(tgRow.find("input[name='prodCd']").val());		//상품코드
		var esgCfmFg = $.trim(tgRow.find("input[name='esgCfmFg']").val());	//ESG인증정보변경 확정상태
		var esgGbn = $.trim(tgRow.find("input[name='esgGbn']").val());		//ESG유형
		var esgAuth = $.trim(tgRow.find("input[name='esgAuth']").val());	//ESG인증유형
		var esgAuthDtl = $.trim(tgRow.find("input[name='esgAuthDtl']").val());	//ESG인증상세유형
		
		//승인대기상태에서 수정불가
		if(esgCfmFg == "0"){
			alert("인증정보 변경요청 승인대기 중입니다.");
			return;
		}
		
		//ESG 유형 누락
		if(esgGbn == ""){
			alert("ESG 유형을 선택해주세요.");
			return;
		}
		
		//ESG 인증유형 누락
		if(esgAuth == ""){
			alert("ESG 인증유형을 선택해주세요.");
			return;
		}
		
		//ESG 인증유형상세 누락
		if(esgAuthDtl == ""){
			alert("ESG 인증상세유형을 선택해주세요.");
			return;
		}
		
		var formData = new FormData();
		formData.append("prodCd", prodCd);			//상품코드
		formData.append("esgFileId", esgFileId);	//첨부파일아이디
		formData.append("esgFile", $(this)[0].files[0]);	//첨부파일
		formData.append("tempYn", "Y");				//임시파일
		formData.append("esgGbn", esgGbn);			//ESG유형
		formData.append("esgAuth", esgAuth);		//ESG인증유형
		formData.append("esgAuthDtl", esgAuthDtl);	//ESG인증상세유형
		
		$.ajax({
			url:'<c:url value="/edi/product/updateProdEsgFileInfo.json"/>',
			contentType : false,
			type:"POST",
			dataType: "JSON",
			data:formData,
			cache: false,
			async:false,
			processData: false,
			success:function(data) {
				var msg = data.msg;
				if(msg == "success"){
					var esgFileId = $.trim(data.esgFileId);
					tgRow.find("input[name='esgFileId']").val(esgFileId);
					tgRow.find("span[name=fileIdView]").text(fileNm);
				}else{
					var errMsg = data.errMsg;
					alert("파일 업로드 중 오류가 발생했습니다.\n"+errMsg);
					//파일영역 초기화
					fncClearFile(this);
					return;
				}
				
			}
		});
		
		--%>
	});
	
	
	//----- 대분류 변경시 이벤트
	$("#searchForm select[name=srchL1Cd]").change(function() {
		var groupCode	=	"";
		var l1Cd = $.trim($(this).val().replace(/\s/gi, ''));

		//----- 중, 소분류 초기화
		$("#srchL2Cd option").not("[value='']").remove();
		$("#srchL3Cd option").not("[value='']").remove();
		
		// 대분류 미선택 시, 중분류 선택불가
		if(l1Cd == ""){
			$("#srchL2Cd").prop("disabled", true);
			$("#srchL3Cd").prop("disabled", true);
			return;
		}
		
		//소분류 disabled
		$("#srchL2Cd").prop("disabled", false);
		$("#srchL3Cd").prop("disabled", true);
		
		commerceChange2(groupCode, l1Cd, "", "", ""); //--20170520 infoGrpCd3 파라미터 추가
	});
	
	//----- 중분류 변경시 이벤트
	$("#searchForm select[name=srchL2Cd]").change(function() {
		var groupCode	=	"";
		var l1Cd = $.trim($("select[name='srchL1Cd'] option:selected").val());
		var l2Cd = $.trim($(this).val());
		
		//----- 소분류 초기화
		$("#srchL3Cd option").not("[value='']").remove();
		
		// 중분류 미선택 시, 소분류 선택불가
		if(l2Cd == ""){
			$("#srchL3Cd").prop("disabled", true);
			return;
		}
		
		
		//소분류 disabled
		$("#srchL3Cd").prop("disabled", false);
		
		//소분류 조회
		_eventSelectSrchL3List(groupCode, l1Cd, l2Cd);
	});
	
	//----- ESG 구분 변경시 이벤트
	$("#searchForm select[name=srchEsgGbn]").change(function() {
		var esgGbn = $.trim($(this).find("option:selected").val())
		
		//----- ESG 유형, 상세유형 초기화
		$("#srchEsgAuth option").not("[value='']").remove();
		$("#srchEsgAuthDtl option").not("[value='']").remove();
		
		// ESG 구분 선택 안했을 경우, 유형 선택불가
		if(esgGbn == ""){
			$("#srchEsgAuth").prop("disabled", true);
			$("#srchEsgAuthDtl").prop("disabled", true);
			return;
		}
		
		//상세유형 disabled
		$("#srchEsgAuth").prop("disabled", false);			
		$("#srchEsgAuthDtl").prop("disabled", true);
		
		//유형 조회
		_eventSrchEsgAuth(esgGbn);
	});
	
	//----- ESG 유형 변경시 이벤트
	$("#searchForm select[name=srchEsgAuth]").change(function() {
		var esgGbn = $.trim($(this).find("option:selected").val())
		var esgAuth = $.trim($(this).find("option:selected").val())
		
		//----- ESG 유형, 상세유형 초기화
		$("#srchEsgAuthDtl option").not("[value='']").remove();
		
		// ESG 유형 선택 안했을 경우, 상세유형 선택불가
		if(esgAuth == ""){
			$("#srchEsgAuthDtl").prop("disabled", true);
			return;
		}
		
		//상세유형 disabled
		$("#srchEsgAuthDtl").prop("disabled", false);
		
		//유형 조회
		_eventSrchEsgAuthDtl(esgGbn, esgAuth);
	});
	
});


/* 대분류 변경시 이벤트 20170518 추가*/
function commerceChange2(groupCode, val, infoGrpCd, infoGrpCd2, infoGrpCd3){
	//-----중분류 셋팅
	_eventSelectSrchL2List(groupCode, val);
}

/* 대분류 변경시 중분류 호출 이벤트 */
function _eventSelectSrchL2List(groupCode, val) {
	var paramInfo = {};

	paramInfo["groupCode"]	=	groupCode;		//팀코드
	paramInfo["l1Cd"]		=	val;			//대븐류

	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		async : false,
		url : '<c:url value="/edi/product/NselectNoTeamL2List.json"/>',
		data : JSON.stringify(paramInfo),
		success : function(data) {
			var resultList	=	data.l2List;
			if (resultList.length > 0) {

				var eleHtml = [];
				for (var i = 0; i < resultList.length; i++) {
					eleHtml[i] = "<option value='"+resultList[i].l2Cd+"'>"+resultList[i].l2Nm+"</option>"+"\n";
				}

				$("#srchL2Cd option").not("[value='']").remove();	//콤보박스 초기화
				$("#srchL2Cd").append(eleHtml.join(''));
			} else {
				$("#srchL2Cd option").not("[value='']").remove();	//콤보박스 초기화
			}
		}
	});
}

/* 중분류 변경시 소분류 호출 이벤트 */
function _eventSelectSrchL3List(groupCode, l1Cd, l2Cd) {
	var paramInfo = {};

	paramInfo["groupCode"]	=	groupCode;		//팀코드
	paramInfo["l1Cd"]		=	$.trim(l1Cd);	//대분류
	paramInfo["l2Cd"]		=	$.trim(l2Cd);	//소분류

	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		async : false,
		url : '<c:url value="/edi/product/selectNgetNoTeamL3List.json"/>',
		data : JSON.stringify(paramInfo),
		success : function(data) {
			var resultList	=	data.l3List;
			if (resultList.length > 0) {

				var eleHtml = [];
				for (var i = 0; i < resultList.length; i++) {
					eleHtml[i] = "<option value='"+resultList[i].l3Cd+"'>"+resultList[i].l3Nm+"</option>"+"\n";
				}
				
				$("#srchL3Cd").append(eleHtml.join(''));
			}
		}
	});
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
	
	//변경이후 행 체크
	let cbox = tgRow.find("input[name='cbox']")[0];
	if(cbox){
		$(cbox).prop("checked", true);
	}
}

//판매코드 찾기 검색조건 팝업 OPEN
function fncOpenSrchPopSrcmkCd(){
	var targetUrl = "<c:url value='/edi/product/selSrcmkCdPopup.do'/>" + "?trNum=search";
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

	//검색조건
	if(trNum == "search"){
		$("#searchForm #srchSrcmkCd").val(srcmkCd);
	}
}

//조회
function eventSearch(){
	
	var srchVenCd = $.trim($("#srchVenCd").val());		//검색조건_협력업체
	
	if(srchVenCd == ""){
		alert("협력업체는 필수 선택항목입니다.");
		$("#srchVenCd").focus();
		return;
	}
	
	var srchCnt = 0;
	var searchInfo = {};
	$("#searchForm").find("input, select").each(function(){
		if(this.name != null && this.name != ""){
			if(this.type == "checkbox" && $(this).hasClass("chkYn")){
				searchInfo[this.name] = $(this).is(":checked")?"Y":"N";				
			}else if($(this).hasClass("day")){
				searchInfo[this.name] = $.trim($(this).val()).replace(/\D/g,"");
			}else{
				searchInfo[this.name] = $.trim($(this).val());
			}
			
			//인증서종료대상/파트너사 검색조건 외 입력했는지 체크
			if(this.name != "srchExpTgYn" && this.name != "srchVenCd" && !$(this).hasClass("chkYn") && $.trim($(this).val()) != ""){
				srchCnt ++;
			}
		}
	});
	
	//설정된 검색조건이 있는지 여부
	searchInfo.srchYn = (srchCnt > 0)?"Y":"N";
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/selectProdEsgList.json"/>',
		data : JSON.stringify(searchInfo),
		success : function(data) {
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

//list 데이터 셋팅
function fncSetData(json){
	var itemList = json.itemList;
	//datalist 초기화
	setTbodyInit("dataListbody");
	
	//아이템정보
	if(itemList != null && itemList.length > 0){
		//row index setting
		for(var i=0; i<itemList.length; i++){
			itemList[i].count = itemList[i].rnum;
		}
		
		//datalist setting
		$("#dataListTemplate").tmpl(itemList).appendTo("#dataListbody");
	}else{
		//데이터리스트 없음 처리
		setTbodyNoResult("dataListbody", 14);
	}
}

//변경요청 FormData
function eventSaveFormData(){
	var tgObj= $("#dataListbody tr").find("input[name='cbox']:checked");
	
	//선택된 row 없음
	if(tgObj.length == 0){
		alert("변경요청 건을 1개 이상 선택해주세요.");
		return;
	}
	
	if(!confirm("저장하시겠습니까?")) return;
	
	var formData = new FormData();
	
	var saveInfo = {};
	var prodDataArr = [];	//요청대상
	var flag = true;
	tgObj.closest("tr").each(function(i,e){
		var prodData = {};
		
		var prodCd = $.trim($(this).find("input[name='prodCd']").val());			//상품코드
		var esgGbn = $.trim($(this).find("input[name='esgGbn']").val());			//ESG 유형코드
		var esgAuth = $.trim($(this).find("input[name='esgAuth']").val());			//ESG 인증유형
		var esgAuthDtl = $.trim($(this).find("input[name='esgAuthDtl']").val());	//ESG 인증유형상세
		var esgToDt	= $.trim($(this).find("input[name='esgToDt']").val());			//인증종료일(Before)
		var esgAfFrDt = $.trim($(this).find("input[name='esgAfFrDt']").val());		//인증시작일(After)
		var esgAfToDt = $.trim($(this).find("input[name='esgAfToDt']").val());		//인증종료일(After)
		var esgCfmFg = $.trim($(this).find("input[name='esgCfmFg']").val());		//ESG인증정보변경 확정상태
		
		//상품코드없음
		if(prodCd == ""){
			alert("상품코드가 존재하지 않아 수정/저장이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 유형 없음
		if(esgGbn == ""){
			alert("ESG 유형이 존재하지 않아 수정/저장이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 인증유형이 없음
		if(esgAuth == ""){
			alert("ESG 인증유형이 존재하지 않아 수정/저장이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 인증상세유형이 없음
		if(esgAuthDtl == ""){
			alert("ESG 인증상세유형이 존재하지 않아 수정/저장이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}

		//승인요청상태일 경우 수정불가
		if(esgCfmFg == "0"){
			alert("ESG 인증정보 변경 승인 대기 중인 건은 수정/저장이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		
		//ESG 인증요청일자 체크
		if(!fncChkEsgDate($(this))){
			flag=false;
			return false;
		};
		
		//data 생성
		$(this).find("input, select").not(".notInc").map(function(){
			if(this.name != undefined && this.name != null && this.name != ""){
				if(this.type == "file" && $(this)[0].files.length > 0){
					prodData[this.name] = $(this)[0].files[0];
				}else if($(this).hasClass("day") || $(this).hasClass("amt")){
					prodData[this.name] = $.trim($(this).val()).replace(/\D/g,"");
				}else{
					prodData[this.name] = $.trim($(this).val());
				}
				
				//formdata setting
				<%-- 파일 Object 없을 때 formdata 생성하면 getter에서 null exception 발생함 --%>
				if(this.type != "file" || (this.type == "file" && $(this)[0].files.length > 0)){
					formData.append("prodDataArr["+i+"]."+this.name, prodData[this.name]);
				}
			}
		});
// 		if(prodData != null){
// 			prodDataArr.push(prodData);
// 		}
	});
	
	if(!flag) return;
// 	saveInfo.prodDataArr = prodDataArr;		//대상상품 list
	
	$.ajax({
		url:'<c:url value="/edi/product/updateProdEsgInfoWithFile.json"/>',
		contentType : false,
		type:"POST",
		dataType: "JSON",
		data:formData,
		cache: false,
		async:false,
		processData: false,
		success:function(result) {
			var msg = result.msg;
			
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


//변경요청(file저장 X)
function eventSave(){
	var tgObj= $("#dataListbody tr").find("input[name='cbox']:checked");
	
	//선택된 row 없음
	if(tgObj.length == 0){
		alert("변경요청 건을 1개 이상 선택해주세요.");
		return;
	}
	
	if(!confirm("저장하시겠습니까?")) return;
	
	var saveInfo = {};
	var prodDataArr = [];	//요청대상
	var flag = true;
	tgObj.closest("tr").each(function(i,e){
		var prodData = {};
		
		var prodCd = $.trim($(this).find("input[name='prodCd']").val());			//상품코드
		var esgGbn = $.trim($(this).find("input[name='esgGbn']").val());			//ESG 유형코드
		var esgAuth = $.trim($(this).find("input[name='esgAuth']").val());			//ESG 인증유형
		var esgAuthDtl = $.trim($(this).find("input[name='esgAuthDtl']").val());	//ESG 인증유형상세
		var esgToDt	= $.trim($(this).find("input[name='esgToDt']").val());			//인증종료일(Before)
		var esgAfFrDt = $.trim($(this).find("input[name='esgAfFrDt']").val());		//인증시작일(After)
		var esgAfToDt = $.trim($(this).find("input[name='esgAfToDt']").val());		//인증종료일(After)
		var esgCfmFg = $.trim($(this).find("input[name='esgCfmFg']").val());		//ESG인증정보변경 확정상태
		
		//상품코드없음
		if(prodCd == ""){
			alert("상품코드가 존재하지 않아 수정/저장이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 유형 없음
		if(esgGbn == ""){
			alert("ESG 유형이 존재하지 않아 수정/저장이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 인증유형이 없음
		if(esgAuth == ""){
			alert("ESG 인증유형이 존재하지 않아 수정/저장이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 인증상세유형이 없음
		if(esgAuthDtl == ""){
			alert("ESG 인증상세유형이 존재하지 않아 수정/저장이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		
		//승인요청상태일 경우 수정불가
		if(esgCfmFg == "0"){
			alert("ESG 인증정보 변경 승인 대기 중인 건은 수정/저장이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		
		//ESG 인증요청일자 체크
		if(!fncChkEsgDate($(this))){
			flag=false;
			return false;
		};
		
		$(this).find("input[type!=file], select").not(".notInc").map(function(){
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
	saveInfo.prodDataArr = prodDataArr;		//대상상품 list
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/updateProdEsgInfo.json"/>',
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

//ESG 인증요청일자 체크
function fncChkEsgDate(obj){
	var esgToDt = $.trim($(obj).find("input[name='esgToDt']").val()).replace(/\D/g, "");		//인증종료일(Before)
	var esgAfFrDt = $.trim($(obj).find("input[name='esgAfFrDt']").val()).replace(/\D/g, "");	//인증시작일(After)
	var esgAfToDt = $.trim($(obj).find("input[name='esgAfToDt']").val()).replace(/\D/g, "");	//인증종료일(After)
	var esgAuth = $.trim($(obj).find("input[name='esgAuth']").val());					//ESG인증유형
	var esgDtFg = $.trim($(obj).find("input[name='esgDtFg']").val());					//인증기간 필수입력여부
	
	var rnum = $(obj).find("input[name='rnum']").val();
	
	//인증기간 필수 아닐 경우, 날짜 미입력 시 체크하지 않음(필수X)
	if(esgDtFg == "" && esgAfFrDt == "" && esgAfToDt == ""){
		return true;
	}
	
// 	if(esgToDt == "" || esgToDt.length != 8){
// 		alert("인증종료일(Before) 정보가 존재하지 않습니다.");
// 		return false;
// 	}
	
	if(esgAfFrDt == "" || esgAfFrDt.length != 8){
		alert("인증시작일(After)을 올바르게 입력해주세요.");
		$(obj).find("input[name='esgAfFrDt']").focus();
		return false;
	}
	
	if(esgAfToDt == "" || esgAfToDt.length != 8){
		alert("인증종료일(After)을 올바르게 입력해주세요.");
		$(obj).find("input[name='esgAfToDt']").focus();
		return false;
	}
	
	var esgTo_yy = esgToDt.substring(0,4);	//인증종료일(Before) 연	
	var esgTo_mm = esgToDt.substring(4,6);	//인증종료일(Before) 월	
	var esgTo_dd = esgToDt.substring(6,8);	//인증종료일(Before) 일	
	var esgAfFr_yy = esgAfFrDt.substring(0,4);	//인증시작일(After) 연	
	var esgAfFr_mm = esgAfFrDt.substring(4,6);	//인증시작일(After) 월	
	var esgAfFr_dd = esgAfFrDt.substring(6,8);	//인증시작일(After) 일	
	var esgAfTo_yy = esgAfToDt.substring(0,4);	//인증종료일(After) 연	
	var esgAfTo_mm = esgAfToDt.substring(4,6);	//인증종료일(After) 월	
	var esgAfTo_dd = esgAfToDt.substring(6,8);	//인증종료일(After) 일	
	
	var esgToDate 	= new Date(esgTo_yy, esgTo_mm-1, esgTo_dd);			//인증종료일(Before) Date
	var esgAfFrDate = new Date(esgAfFr_yy, esgAfFr_mm-1, esgAfFr_dd);	//인증시작일(After) Date
	var esgAfToDate	= new Date(esgAfTo_yy, esgAfTo_mm-1, esgAfTo_dd);	//인증종료일(After) Date
	
	//인증종료일(Before) 있을 때에만 체크
	//인증시작일(After)은 인증종료일(Before) 이후여야 함
	if(esgToDt != "" && esgToDt.length == 8){
		if(esgToDate >= esgAfFrDate){
			alert("인증시작일(After)는 인증종료일(Before) 이후여야 합니다.");
			$(obj).find("input[name='esgAfFrDt']").val("");
			$(obj).find("input[name='esgAfFrDt']").focus();
			return false;
		}
	}
	
	//인증종료일(After)은 인증시작일(After) 이후여야 함
	if(esgAfFrDate >= esgAfToDate){
		alert("인증종료일(After)는 인증시작일(After) 이후여야 합니다.");
		$(obj).find("input[name='esgAfToDt']").val("");
		$(obj).find("input[name='esgAfToDt']").focus();
		return false;
	}
	
	return true;
}

//변경요청전송
function eventSend(){
	var tgObj= $("#dataListbody tr").find("input[name='cbox']:checked");
	
	//선택된 row 없음
	if(tgObj.length == 0){
		alert("변경요청 건을 1개 이상 선택해주세요.");
		return;
	}
	
	if(!confirm("변경요청 하시겠습니까?")) return;
	
	var saveInfo = {};
	var prodDataArr = [];	//요청대상
	var flag = true;
	tgObj.closest("tr").each(function(i,e){
		var prodData = {};
		
		var rowStat = $.trim($(this).attr("data-rowStat"));							//행상태
		var prodCd = $.trim($(this).find("input[name='prodCd']").val());			//상품코드
		var esgGbn = $.trim($(this).find("input[name='esgGbn']").val());			//ESG 유형코드
		var esgAuth = $.trim($(this).find("input[name='esgAuth']").val());			//ESG 인증유형
		var esgAuthDtl = $.trim($(this).find("input[name='esgAuthDtl']").val());	//ESG 인증유형상세
		var esgToDt	= $.trim($(this).find("input[name='esgToDt']").val());			//인증종료일(Before)
		var esgAfFrDt = $.trim($(this).find("input[name='esgAfFrDt']").val());		//인증시작일(After)
		var esgAfToDt = $.trim($(this).find("input[name='esgAfToDt']").val());		//인증종료일(After)
		var esgDtFg = $.trim($(this).find("input[name='esgDtFg']").val());			//인증기간 필수입력여부
		var esgCfmFg = $.trim($(this).find("input[name='esgCfmFg']").val());		//ESG인증정보변경 확정상태
		var delEsgYn = $.trim($(this).find("input[name='delEsgYn']").val());		//삭제된 정보
		var isTodyCfmOk = $.trim($(this).find("input[name='isTodyCfmOk']").val());	//당일 승인된 건
		
		//삭제된 정보는 요청불가
		if(delEsgYn == "Y"){
			alert("삭제된 항목에 대한 요청은 불가합니다.\n저장을 먼저 진행해주세요.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		
		//당일 승인된 건은 당일 요청 불가
		if(isTodyCfmOk == "Y"){
			alert("금일 승인처리된 항목입니다.\n다음날부터 요청이 가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		
		//행상태확인
		if(rowStat != "R"){
			alert("수정된 항목이 존재합니다.\n저장을 먼저 진행해주세요.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//상품코드없음
		if(prodCd == ""){
			alert("상품코드가 존재하지 않아 요청이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 유형 없음
		if(esgGbn == ""){
			alert("ESG 유형이 존재하지 않아 요청이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 인증유형이 없음
		if(esgAuth == ""){
			alert("ESG 인증유형이 존재하지 않아 요청이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//ESG 인증상세유형이 없음
		if(esgAuthDtl == ""){
			alert("ESG 인증상세유형이 존재하지 않아 요청이 불가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//승인요청상태일 경우 재요청불가
		if(esgCfmFg == "0"){
			alert("ESG 인증정보 변경 승인대기중입니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		//임시저장 상태에서만 요청가능
		if(esgCfmFg != "9"){
			alert("ESG 인증정보 저장 후 요청이 가능합니다.\n행번호:"+Number(i+1));
			flag = false;
			return false;
		}
		
		//ESG 인증요청일자 체크
		if(!fncChkEsgDate($(this))){
			flag=false;
			return false;
		};
		
		$(this).find("input[type!=file], select").not(".notInc").map(function(){
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
	
	saveInfo.prodDataArr = prodDataArr;		//대상상품 list
	
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/updateProdEsgSendProxy.json"/>',
		data : JSON.stringify(saveInfo),
		success : function(data) {
			var msg = data.msg;
			
			if("success" == msg){
				alert("변경요청되었습니다.");
				eventSearch();
			}else{
				var errMsg = $.trim(data.errMsg) != "" ? data.errMsg : "처리 중 오류가 발생했습니다.";
				alert(errMsg);
			}
			
		}
	});
	
}

//input clear
function fncClearDataListInput(objId){
	if(objId == undefined || objId == null|| objId == "") return;
	$("#"+objId).val("");	//값초기화
	$("#"+objId).closest("tr").attr("data-rowStat", "U");		//수정상태변경
}

// file upload
function fncFileUpload(obj, inputName) {
	$(obj).closest('tr').find('input[name="'+inputName+'"]').trigger("click");
}

// file download
function fncFileDown(esgFileId){
	$('#fileForm input[name=atchFileId]').val(esgFileId);
	$('#fileForm').attr("action", '<c:url value="/edi/product/esgFileDown.do"/>')
	$('#fileForm').submit();
}
 
// file 영역 clear
function fncClearFile(obj){
	//기본 setting mesage
	var defHtml = '<span name="fileIdView">'+extLimit+' Only</span>';
	
	$(obj).closest("tr").find("span[name='fileIdView']").remove();
	$(obj).closest("tr").find("span[name='fileIdView']").closest("a").remove();
	$(obj).after(defHtml);
	$(obj).val("");
}

//ESG중분류(유형) 조회
function _eventSrchEsgAuth(esgGbn) {
	var paramInfo = {};
	paramInfo["srchEsgGbn"] = $.trim(esgGbn);	//ESG구분

	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		async : false,
		url : '<c:url value="/edi/product/selectEsgMstlList.json"/>',
		data : JSON.stringify(paramInfo),
		success : function(data) {
			if(data != null && data.length > 0){
				var eleHtml = [];
				for (var i = 0; i < data.length; i++) {
					eleHtml[i] = "<option value='"+data[i].mLvCdn+"'>"+data[i].mLvNmn+"</option>"+"\n";
				}
				
				$("#srchEsgAuth").append(eleHtml.join(''));
			}			
		}
	});
}

//ESG소분류(상세유형) 조회
function _eventSrchEsgAuthDtl(esgGbn, esgAuth) {
	var paramInfo = {};
	paramInfo["srchEsgGbn"] = $.trim(esgGbn);	//ESG구분
	paramInfo["srchEsgAuth"] = $.trim(esgAuth);	//ESG유형

	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		async : false,
		url : '<c:url value="/edi/product/selectEsgMstSList.json"/>',
		data : JSON.stringify(paramInfo),
		success : function(data) {
			if(data != null && data.length > 0){
				var eleHtml = [];
				for (var i = 0; i < data.length; i++) {
					eleHtml[i] = "<option value='"+data[i].sLvCdn+"'>"+data[i].sLvNmn+"</option>"+"\n";
				}
				
				$("#srchEsgAuthDtl").append(eleHtml.join(''));
			}			
		}
	});
}
</script>

<!-- 인증정보 list datalist template -->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
{%if delEsgYn == "Y"%}
<tr class="r\${count}" bgcolor="#f6f6f6" data-rowStat="<c:out value='\${rowStat}'/>">
{%else%}
<tr class="r\${count}" bgcolor="#ffffff" data-rowStat="<c:out value='\${rowStat}'/>">
{%/if%}
	<td class="tdc">
		{%if esgCfmFg != "0" && delEsgYn != "Y" && isTodyCfmOk != "Y" %}	<%-- 승인대기중이 아님 and 삭제되지 않은 정보 and 오늘승인된건이 아닌것만 체크박스표시 --%>
		<input type="checkbox" id="cbox\${count}" name="cbox" class="notInc"/>
		{%/if%}
		<input type="hidden" name="rnum" 	class="notInc" value="<c:out value='\${rnum}'/>"/>
		<input type="hidden" name="esgDtFg" value="<c:out value='\${esgDtFg}'/>"/>
		<input type="hidden" name="esgCfmFg" value="<c:out value='\${esgCfmFg}'/>"/>
		<input type="hidden" name="esgEarth" value="<c:out value='\${esgEarth}'/>"/>
		<input type="hidden" name="delEsgYn" value="<c:out value='\${delEsgYn}'/>"/>
		<input type="hidden" name="isTodyCfmOk" value="<c:out value='\${isTodyCfmOk}'/>"/>
	</td>
	<td class="tdc">
		{%if delEsgYn == "Y"%}		<%-- 삭제된 건 --%>
			<font color="red">삭제</font>
		{%elif esgCfmFg != "" && esgCfmFg != "1" %}	<%-- 상태값이 있으면서, 승인되지 않은 건 --%>
			<font><c:out value='\${esgCfmFgNm}'/></font>
		{%elif isTodyCfmOk == "Y" %}	<%-- 오늘 승인된 건 --%>
			<font color="blue"><c:out value='\${esgCfmFgNm}'/></font>
		{%else%} <%-- 그 외 (상태값 없거나, 오늘 승인되지 않은 건) --%>
			<font>-</font>
		{%/if%}
	</td>
	<td class="tdc">
		<c:out value='\${prodCd}'/>
		<input type="hidden" name="prodCd" value="<c:out value='\${prodCd}'/>"/>
	</td>
	<td class="tdc">
		<c:out value='\${srcmkCd}'/>
		<input type="hidden" name="srcmkCd" value="<c:out value='\${srcmkCd}'/>"/>
	</td>
	<td><c:out value='\${prodNm}'/></td>
	<td><c:out value='\${prodStd}'/></td>
	<td>
		<c:out value='\${esgGbn}'/>. <c:out value='\${esgGbnNm}'/>
		<input type="hidden" name="esgGbn" value="<c:out value='\${esgGbn}'/>"/>
	</td>
	<td>
		<c:out value='\${esgAuth}'/>. <c:out value='\${esgAuthNm}'/>
		<input type="hidden" name="esgAuth" value="<c:out value='\${esgAuth}'/>"/>		
	</td>
	<td>
		<c:out value='\${esgAuthDtl}'/>. <c:out value='\${esgAuthDtlNm}'/>
		<input type="hidden" name="esgAuthDtl" value="<c:out value='\${esgAuthDtl}'/>"/>
	</td>
	<td class="tdc">
		<c:out value='\${esgFrDtFmt}'/>
		<input type="hidden" name="esgFrDt" value="<c:out value='\${esgFrDt}'/>"/>
	</td>
	<td class="tdc">
		<c:out value='\${esgToDtFmt}'/>
		<input type="hidden" name="esgToDt" value="<c:out value='\${esgToDt}'/>"/>
	</td>
	{%if delEsgYn != "Y"%}	<%-- 상품 ESG 정보삭제 되지 않은 건 --%>
	<td class="tdc">
		{%if esgCfmFg == "0" || isTodyCfmOk == "Y"%}	<%-- 승인대기 상태 or 금일 승인된 건일 경우, 요청 불가 --%>
		<c:out value='\${esgAfFrDtFmt}'/>
		{%else%}
		<input type="text" class="day" id="esgAfFrDt\${count}" name="esgAfFrDt" style="width:80px;" value="<c:out value='\${esgAfFrDtFmt}'/>" disabled> <img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor:hand;" />
		<a href="javascript:void(0);" class="btn" onclick="fncClearDataListInput('esgAfFrDt\${count}')"><span>Clear</span></a>
		{%/if%}
	</td>
	<td class="tdc">
		{%if esgCfmFg == "0" || isTodyCfmOk == "Y"%}
		<c:out value='\${esgAfToDtFmt}'/>
		{%else%}
		<input type="text" class="day" id="esgAfToDt\${count}" name="esgAfToDt" style="width:80px;" value="<c:out value='\${esgAfToDtFmt}'/>" disabled> <img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor:hand;" />
		<a href="javascript:void(0);" class="btn" onclick="fncClearDataListInput('esgAfToDt\${count}')"><span>Clear</span></a>
		{%/if%}
	</td>
	<td class="tdc">
		{%if esgCfmFg == "0" || isTodyCfmOk == "Y"%}
		<c:out value="\${orgFileNm}" default="-"/>
		{%else%}
		<input type="file" id="esgFile\${count}" name="esgFile" value="" style="display:none"/>
			{%if esgFileId != '' && esgFileId != null %}
			<a href="javascript:void(0);" onclick="fncFileDown('\${esgFileId}', '\${filePath}');">
				<span name="fileIdView" style = "color: blue;">\${orgFileNm}</span>
			</a>
			{%else%}
			<span name="fileIdView"><c:out value='\${extLimit}'/> Only</span>
			{%/if%}
			<a href="javascript:void(0);" class="btn" onclick="fncFileUpload(this, 'esgFile')"><span>찾아보기</span></a>
		{%/if%}
		<input type="hidden" name="esgFileId" value="<c:out value='\${esgFileId}'/>"/>
	</td>
	{%else%}		<%-- 상품 ESG 정보삭제된건 --%>
	<td class="tdc" colspan="3">
		<font>삭제된 인증정보입니다.</font>
	</td>
	{%/if%}
</tr>
</script>
</head>
<body>
<div id="content_wrap">
	<div id="wrap_menu">
		<div class="wrap_search">
			<div class="bbs_search">
				<ul class="tit">
					<li class="tit">ESG인증관리</li>
					<li class="btn">
						<a href="javascript:void(0);" class="btn" onclick="eventSearch()"><span><spring:message code="button.common.inquire"/></span></a>
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
							<th><span class="star">*</span> 협력업체</th>
							<td>
								<html:codeTag objId="srchVenCd" objName="srchVenCd" width="150px;" dataType="CP" comType="SELECT" defName="선택" formName="form"/>
							</td>
							<th>인증서 종료대상</th>
							<td>
								<input type="checkbox" class="chkYn" id="srchExpTgYn" name="srchExpTgYn"/>
							</td>
							<th>삭제제외</th>
							<td>
								<input type="checkbox" class="chkYn" id="srchWithDel" name="srchWithDel" checked/>
							</td>
						</tr>
						<tr>
							<th>대분류</th>
							<td>
								<html:codeTag objId="srchL1Cd" objName="srchL1Cd" dataType="ORG" comType="SELECT" defName="선택" formName="form"/>
							</td>
							<th>중분류</th>
							<td>
								<select id="srchL2Cd" name="srchL2Cd" style="max-width:90%" disabled>
									<option value=""><spring:message code="button.common.choice" /></option>
								</select>
							</td>
							<th>소분류</th>
							<td>
								<select id="srchL3Cd" name="srchL3Cd" style="max-width:90%" disabled>
									<option value=""><spring:message code="button.common.choice" /></option>
								</select>
							</td>
						</tr>
						<tr>
							<th>구분</th>
							<td>
								<select id="srchEsgGbn" name="srchEsgGbn">
									<option value=""><spring:message code="button.common.choice" /></option>
									<c:if test="${not empty esgMstL }">
										<c:forEach items="${esgMstL}" var="list" varStatus="index" >
											<option value="<c:out value='${list.lLvCdn}'/>"><c:out value='${list.lLvNmn}'/></option>
										</c:forEach>
									</c:if>
								</select>
							</td>
							<th>유형</th>
							<td>
								<select id="srchEsgAuth" name="srchEsgAuth" style="max-width:90%" disabled>
									<option value=""><spring:message code="button.common.choice" /></option>
								</select>
							</td>
							<th>상세유형</th>
							<td>
								<select id="srchEsgAuthDtl" name="srchEsgAuthDtl" style="max-width:90%" disabled>
									<option value=""><spring:message code="button.common.choice" /></option>
								</select>
							</td>
						</tr>
						<tr>
							<th>판매코드</th>
							<td>
								<input type="text" name="srchSrcmkCd" id="srchSrcmkCd" style="width:100px;"/>
								<a href="javascript:void(0);" class="btn" onclick="fncOpenSrchPopSrcmkCd()"><span>찾기</span></a>
							</td>
							<th>인증기간</th>
							<td colspan="3">
								<input type="text" class="day" name="srchStartDt" id="srchStartDt" style="width:80px;"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDt');" style="cursor:hand;" />
								~
								<input type="text" class="day" name="srchEndDt" id="srchEndDt" style="width:80px;"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDt');"  style="cursor:hand;" />
							</td>
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
								<a href="javascript:void(0);" class="btn" onclick="eventSaveFormData()"><span>저장</span></a>
								<a href="javascript:void(0);" class="btn" onclick="eventSend()"><span>변경요청</span></a>
							</li>
						</ul>
						<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll; white-space:nowrap;">
							<form id="reqDetailForm" name="reqDetailForm" method="post">
							<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=1800px; bgcolor="efefef" style="table-layout:fixed;" class="sub-table">
								<colgroup>
									<col width="2%">	
									<col width="4%">	
									<col width="5%">	
									<col width="5%">	
									<col width="8%">	
									<col width="4%">	
									<col width="5%">	
									<col width="5%">	
									<col width="8%">	
									<col width="5%">	
									<col width="5%">	
									<col width="8%">	
									<col width="8%">	
									<col width="12%">	
								</colgroup>
								<thead>
									<tr bgcolor="#e4e4e4">
										<th rowspan="2"><input type="checkbox" id="chkAll"/></th>
										<th rowspan="2">상태</th>
										<th rowspan="2">상품코드</th>
										<th rowspan="2">판매코드</th>
										<th rowspan="2">운영상품명</th>
										<th rowspan="2">규격</th>
										<th rowspan="2">유형코드/유형</th>
										<th rowspan="2">인증유형코드/인증유형</th>
										<th rowspan="2">인증상세유형코드/인증상세유형</th>
										<th colspan="2">Before</th>
										<th colspan="2">After</th>
										<th rowspan="2">인증서첨부</th>
									</tr>
									<tr>
										<th>인증시작일</th>
										<th>인증종료일</th>
										<th>인증시작일</th>
										<th>인증종료일</th>
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
							<li class="last">ESG 인증관리</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- footer //-->
		</div>
	</div>
</div>
<form id="hiddenForm" name="hiddenForm" method="post" enctype="multipart/form-data">

</form>

<form id="fileForm" name="fileForm" method="post">
	<input type="hidden" id="atchFileId" name="atchFileId"/>
</form>
</body>
</html>