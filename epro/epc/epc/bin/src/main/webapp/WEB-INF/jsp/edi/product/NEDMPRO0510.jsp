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
<style>
	table .tdr{text-align:right;}
	table .tdc{text-align:center;}
</style>


<script type="text/javascript" >
let rowCount = 0;
$(function(){
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
});


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

//조회
function eventSearch(){
	//검색조건 확인
	if(!fncValidate()) return;
	
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
		url : '<c:url value="/edi/product/selectTpcProdChgCostDetailView.json"/>',
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
	}else{
		//datalist 초기화
		setTbodyInit("dataListbody");
	}
	
}

//$(obj).closest("tr")
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
	var purDept = $.trim(tgObj.find("input[name='purDept']").val());	//구매조직
	
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
	alert("준비 중 입니다.");
	return;
}

//판매코드 찾기 검색조건 팝업 OPEN
function fncOpenSrchPopSrcmkCd(obj){
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
	var dispUnit = json.dispUnit;	//표시단위

	//검색조건
	if(trNum == "search"){
		$("#searchForm #srchSrcmkCd").val(srcmkCd);
	}
}

//등록화면으로 이동
function fncMoveToWrt(obj){
	var tgObj = $(obj).closest("tr");
	var srchPurDept = $.trim(tgObj.find("input[name='purDept']").val());		//구매조직
	var srchNbPbGbn = $.trim(tgObj.find("input[name='nbPbGbn']").val());		//NB,PB구분
	var srchVenCd = $.trim(tgObj.find("input[name='venCd']").val());			//협력사코드
	
	$("#hiddenForm input[name='srchPurDept']").val(srchPurDept);
	$("#hiddenForm input[name='srchNbPbGbn']").val(srchNbPbGbn);
	$("#hiddenForm input[name='srchVenCd']").val(srchVenCd);
	
	var f = document.hiddenForm;
	f.action = "<c:url value='/edi/product/NEDMPRO0500.do'/>";
	f.submit();
}
</script>


<!-- 요청내역 datalist template -->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r\${count}" bgcolor="#ffffff" data-rowStat="<c:out value='\${rowStat}'/>">
	<td class="tdc">
		<span name="rnum"><c:out value='\${rnum}'/></span>
		<input type="hidden" name="reqNo" 		value="<c:out value='\${reqNo}'/>"/>
		<input type="hidden" name="seq" 		value="<c:out value='\${seq}'/>"/>
		<input type="hidden" name="prodPatFg" 	value="<c:out value='\${prodPatFg}'/>"/>
		<input type="hidden" name="dispUnit" 	value="<c:out value='\${dispUnit}'/>"/>
		<input type="hidden" name="prStat"		value="<c:out value='\${prStat}'/>"/>
		<input type="hidden" name="venCd"		value="<c:out value='\${venCd}'/>"/>
	</td>
	<td class="tdc">
		{%if prStat == "03"%}
		<a href="javascript:void(0);" class="btn" onclick="eventCopy(this)"><span>복사</span></a>
		{%/if%}
	</td>
	<td class="tdc">
		<span name="prStatNm">
			{%if prStat == "" || prStat == "00" %}
			<a href="javascript:void(0);" onclick="fncMoveToWrt(this)"><c:out value='\${prStatNm}'/></a>
			{%else%}
			<c:out value='\${prStatNm}'/>			
			{%/if%}
		</span>
		<input type="hidden" name="prStat" 		value="<c:out value='\${prStat}'/>"/>
	</td>
	<td class="tdc">
		<span name="dcNum"><c:out value='\${dcNum}'/></span>
		<input type="hidden" name="dcNum" 		value="<c:out value='\${dcNum}'/>"/>
	</td>
	<td class="tdc">
		<span name="prodCd"><c:out value='\${prodCd}'/></span>
		<input type="hidden" name="prodCd" 		value="<c:out value='\${prodCd}'/>"/>
	</td>
	<td class="tdc">
		<span name="srcmkCd"><c:out value='\${srcmkCd}'/></span>
		<input type="hidden" name="srcmkCd" 		value="<c:out value='\${srcmkCd}'/>"/>
	</td>
	<td class="tdc">
		<span name="purDeptNm"><c:out value='\${purDeptNm}'/></span>
		<input type="hidden" name="purDept" 		value="<c:out value='\${purDept}'/>"/>
	</td>
	<td class="tdc">
		<span name="nbPbGbnNm"><c:out value='\${nbPbGbnNm}'/></span>
		<input type="hidden" name="nbPbGbn" 		value="<c:out value='\${nbPbGbn}'/>"/>
	</td>
	<td class="tdc">
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
		<span name="chgReqCostDt"><c:out value='\${chgReqCostDtFmt}'/></span>
		<input type="hidden" name="chgReqCostDt" 		value="<c:out value='\${chgReqCostDt}'/>"/>
	</td>
	<td class="tdr">
		<span name="orgCostFmt"><c:out value='\${orgCostFmt}'/></span>
	</td>
	<td class="tdr">
		<span name="chgReqCost"><c:out value='\${chgReqCostFmt}'/></span>
	</td>
	<td class="tdr">
		<%--
		{%if incDecRate > 0 %}
		<span name="incDecRate" style="font-weight:bold;color:red"> ▲
		{%elif incDecRate < 0 %}
		<span name="incDecRate" style="font-weight:bold;color:blue"> ▼
		{%else%}
		<span name="incDecRate">
		{%/if%}
		--%>
		<span name="incDecRate"><c:out value='\${incDecRate}'/> %</span>
	</td>
	<td class="tdc">
		<span name="costRsnCdNm"><c:out value='\${costRsnCdNm}'/></span>
	</td>
	<td class="tdc">
		<span name="costRsnDtlCdNm"><c:out value='\${costRsnDtlCdNm}'/></span>
	</td>
	<td>
		<span name="cmt"><c:out value='\${cmt}'/></span>
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
							<a href="javascript:void(0);" class="btn" onclick="eventSearch()"><span><spring:message code="button.common.inquire"/></span></a>
							<a href="javascript:void(0);" class="btn" onclick="eventExcelDown()"><span>Excel 다운로드</span></a>
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
										<option value="">전체</option>
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
										<option value="">전체</option>
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
								<td ><input type="text" class="day" id="srchChgReqCostDt" name="srchChgReqCostDt" style="width:80px;" value="<c:out value='${srchFromDt}'/>"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchChgReqCostDt');" style="cursor:hand;" /></td>
							</tr>
							<tr>
								<th>판매코드</th>
								<td>
									<input type="text" name="srchSrcmkCd" id="srchSrcmkCd" style="width:100px;"/>
									<a href="javascript:void(0);" class="btn" onclick="fncOpenSrchPopSrcmkCd()"><span>찾기</span></a>
								</td>
								<th>상태</th>
								<td colspan="3">
									<select id="srchPrStat" name="srchPrStat">
										<option value="">전체</option>
										<option value="00">파트너사등록</option>
										<option value="01">승인대기</option>
										<option value="02">승인</option>
										<option value="03">반려</option>
									</select>
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
								<li class="btn"></li>
							</ul>
							<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll; white-space:nowrap;">
								<form id="reqDetailForm" name="reqDetailForm">
								<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=1800px; bgcolor="efefef" style="table-layout:fixed;" class="sub-table">
									<colgroup>
										<col width="5%">	
										<col width="5%">
										<col width="8%">
										<col width="8%">	
										<col width="8%">	
										<col width="10%">	
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
										<col width="5%">	
										<col width="8%">	
										<col width="15%">	
									</colgroup>
									<thead>
										<tr bgcolor="#e4e4e4">
											<th>No</th>
											<th>복사</th>
											<th>진행상태</th>
											<th>공문번호</th>
											<th>상품코드</th>
											<th>판매코드</th>
											<th>구매조직</th>
											<th>NB/PB</th>
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
			</div>
		</div>
	</div>
	<!--  -->
	<form id="hiddenForm" name="hiddenForm" method="post">
		<input type="hidden" name="srchPurDept"/>
		<input type="hidden" name="srchNbPbGbn"/>
		<input type="hidden" name="srchVenCd"/>
	</form>
	
</body>
</html>