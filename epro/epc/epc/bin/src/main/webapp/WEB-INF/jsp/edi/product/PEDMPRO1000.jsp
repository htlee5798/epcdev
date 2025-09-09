<%-- 
- Author(s): 
- Created Date: 2011. 10. 20
- Version : 1.0
- Description : 상품등록현황

--%>
<%@include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@include file="./javascript.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>

	$(document).ready(function() {	
		
		$("#entp_cd").change(function () {
			
			//datalist 초기화
			$("#dataListbody tr").remove();
			
			// 동적분석속성 입력 html 초기화
			$("#grpAttrTbl tr").remove();
			
			//대분류 콤보박스 초기화
			$("#l1GroupCode option").not("[value='']").remove();
			
			//세분류 콤보박스 초기화
			$("#l4GroupCode option").not("[value='']").remove();
			
			//그릅소분류 콤보박스 초기화
			$("#srchGrpCd option").not("[value='']").remove();
			
			if ($(this).val().replace(/\s/gi, '') != "") {
				//대분류 콤보박스 조회 구성
				_eventSelectL1ListProductBatch();	
			}
			
			
		});
		
		
		//대분류 콤보박스 조회 구성
		_eventSelectL1ListProductBatch();
		
		//대분류 체인지 이벤트
		$("#l1GroupCode").change(function (){
			//datalist 초기화
			$("#dataListbody tr").remove();
			
			// 동적분석속성 입력 html 초기화
			$("#grpAttrTbl tr").remove();
			
			//그룹소분류 초기화			
			$("#srchGrpCd option").not("[value='']").remove();
			
			//세분류 조회
			_eventClearTeamCd("L1CD", $(this).val());						
		});
		
		// 세분류 콤보박스 체인지 이벤트
		$("#l4GroupCode").change(function() {					
			
			//data list 초기화
			$("#dataListbody tr").remove();
			
			// 동적분석속성 입력 html 초기화
			$("#grpAttrTbl tr").remove();	
			
			_eventClearGrpCd("L3CD", $(this).val());
			
		});	
		
		//체크박스 전체선택 true, false
		$("#chkAll").click(function() {
			//alert("전체선택");
			if ($("#chkAll").prop("checked")) {
				$("input[type=checkbox]").prop("checked", true);
			}else {
				$("input[type=checkbox]").prop("checked", false);
			}
		});
		
		var l1Cd			=	"<c:out value='${param.l1Cd}'/>";
		//var l4Cd			=	"<c:out value='${param.srchL4Cd}'/>";
		var l4Cd			=	"<c:out value='${param.l4Cd}'/>";
		var pageIdx			=	"<c:out value='${pageIdx}'/>";
		//var srchCompleteGbn	=	"<c:out value='${param.srchCompleteGbn}'/>"
		var srchProdGbn		=	"<c:out value='${param.srchProdGbn}'/>";
		var recordCnt		=	"<c:out value='${param.recordCnt}'/>";
		var srchGrpCd		=	"<c:out value='${param.srchGrpCd}'/>";
				
		if (recordCnt != "") {
			$("select[name='recordCnt']").val(recordCnt);
		}
		
		/* if (srchCompleteGbn.replace(/\s/gi, '') != "") {
			$("select[name='srchCompleteGbn']").val(srchCompleteGbn);
		} */
		
		if (srchProdGbn.replace(/\s/gi, '') == "") {
			$("select[name='srchProdGbn']").val(srchProdGbn);
		}
		
		if (l1Cd.replace(/\s/gi, '') != "") {
			
			//대분류 콤보박스 호출 함수
			_eventSelectL1ListProductBatch();			
			$("select[name='l1Cd']").val(l1Cd);
			
			// 세분류 콤보박스 호출
			_eventClearTeamCd("L1CD", l1Cd);					
			$("select[name='l4Cd']").val(l4Cd);
						
			// 그룹소분류 콤보박스 호출
			_eventSelectSrchGrpCd(l4Cd);
			$("select[name='srchGrpCd']").val(srchGrpCd);
			
			//data list 초기화
			$("#dataListbody tr").remove();
			
			// 동적분석속성 입력 html 초기화
			$("#grpAttrTbl tr").remove();			
			

			//그룹분석속성 리스트 조회 구성
			//_eventCallSapL3GrpAttr($("select[name='l4Cd']").val().replace(/\s/gi, ''));		// 선택된  세분류에 따른 그룹분석속성 리스트 조회
			
			//조회 함수 호출
			goPage(pageIdx);
		} 
		
		//판매코드 정렬
		$("#spSrcmkCd").click(function (){
			var srcmkOrder	=	"";
			if ($(this).text() == "▲") {
				$(this).text("▼");
				srcmkOrder = "srcmkCdAsc";
			} else {
				$(this).text("▲");
				srcmkOrder = "srcmkCdDesc";
			}
			$("input[name='orderGbn']").val(srcmkOrder);
			goPage("1");
		});
		
		//상품코드 정렬
		$("#spProdCd").click(function (){
			var prodCdOrder	=	"";
			if ($(this).text() == "▲") {
				$(this).text("▼");
				prodCdOrder = "prodCdAsc";
			} else {
				$(this).text("▲");
				prodCdOrder = "prodCdDesc";
			}
			$("input[name='orderGbn']").val(prodCdOrder);
			goPage("1");
		});
		
		//상품명 정렬
		$("#spProdNm").click(function (){
			var prodNmOrder	=	"";
			if ($(this).text() == "▲") {
				$(this).text("▼");
				prodNmOrder = "prodNmAsc";
			} else {
				$(this).text("▲");
				prodNmOrder = "prodNmDesc";
			}
			$("input[name='orderGbn']").val(prodNmOrder);
			goPage("1");
		});
		
	});
	
	/* sap 소분류에 따른 그룹분석속성리스트 Data 구성 */
	function _eventSetsapL3GrpAttrList(json) {
		
		var sapL3GrpAttrListData	=	json.sapL3GrpAttrList;
		var sapL3GrpAttrComboList	=	json.sapL3GrpAttrComboList;		
		var kyukeoklist				=	json.kyekeokList;
		
		if (sapL3GrpAttrListData.length > 0) {
			
			// 그룹분석속성 동적 html 초기화
			//$("#grpAttrTbl tr:eq(1)").remove();
			$("#grpAttrTbl tr").remove();
						
			var trHtml = [];		// sap L3_CD 값에 따른 그룹분석속성 리스트를 html로
			for (var i = 0; i < sapL3GrpAttrListData.length; i++) {
				
				if (sapL3GrpAttrListData[i].val == null || sapL3GrpAttrListData[i].val.length <= 0) {
					sapL3GrpAttrListData[i].val = "";	
				}
				
				if (sapL3GrpAttrListData[i].attValNm == null || sapL3GrpAttrListData[i].attValNm.length <= 0) {
					sapL3GrpAttrListData[i].attValNm = "";	
				}
				
				//그룹분석속성분류코드 타입[10:combo, 20:table column, 30:freeForm digit, 40:freeForm Text, 50:Brand Popup, 51:Maker Popup]
				// 50, 51, 60 타입은 콤보박스로 갈수도 있기 때문에 현재 입력받지 않는다고 하여 if문 처리
				// 20 타입은 아직 정의되지 않았음 정의 되면 추가해줘야 됨 
				if (sapL3GrpAttrListData[i].attTypCd != "50" && sapL3GrpAttrListData[i].attTypCd != "51" && sapL3GrpAttrListData[i].attTypCd != "60") {
					trHtml[i] =  "<tr id='dynamicGroupAttrTr'>";
					trHtml[i] += "	<th>" + sapL3GrpAttrListData[i].attNm + "</th>";
					trHtml[i] += "		<td colspan='3'>";
					
					// 타입에 따른 입력형태 구성 [콤보일경우]
					if (sapL3GrpAttrListData[i].attTypCd == "10") {					
						trHtml[i] += "		<select name='arrCombo10'		id='arrCombo10'>";
						trHtml[i] += "			<option value=''>선택</option>";	
						
						// sap L3_Cd의 그룹분석속성의 콤보리스트 구성
						for (var x = 0; x < sapL3GrpAttrComboList.length; x++) {
							if (sapL3GrpAttrListData[i].attId == sapL3GrpAttrComboList[x].attId) {
								
								if (sapL3GrpAttrListData[i].val == sapL3GrpAttrComboList[x].attValId) {
									trHtml[i] += "			<option value='" + sapL3GrpAttrComboList[x].attValId + "'	selected>" + sapL3GrpAttrComboList[x].attValNm + "</option>";	
								} else {
									trHtml[i] += "			<option value='" + sapL3GrpAttrComboList[x].attValId + "'>" + sapL3GrpAttrComboList[x].attValNm + "</option>";
								}								
								
							}
						}						
						trHtml[i] += "		</select>";
					
					// 브랜드팝업	
					} else if (sapL3GrpAttrListData[i].attTypCd == "50") {
						trHtml[i] += "<input type='text' 	id='brandName' name='brandName' style='width:150px;background-color:#F6F6F6;' 	readonly='readonly'		disabled='disabled'		value='" + sapL3GrpAttrListData[i].attValNm + "'/>";
						trHtml[i] += "<input type='hidden'  id='brandCode' name='brandCode' value='" + sapL3GrpAttrListData[i].val + "'/>";
						trHtml[i] += "<a href='javascript:openBrandPopup();' class='btn'><span><spring:message code='button.common.choice'/></span></a>";
						
					// 메이커 팝업	
					} else if (sapL3GrpAttrListData[i].attTypCd == "51") {
						trHtml[i] += "<input type='text' 	id='makerName' name='makerName' style='width:150px;background-color:#F6F6F6;'  	readonly='readonly'		disabled='disabled'		value='" + sapL3GrpAttrListData[i].attValNm + "'/>";
						trHtml[i] += "<input type='hidden'  id='makerCode' name='makerCode' value='" + sapL3GrpAttrListData[i].val + "'/>";
						trHtml[i] += "<a href='javascript:openMakerPopup();' class='btn'><span><spring:message code='button.common.choice'/></span></a>";
						
					} else if (sapL3GrpAttrListData[i].attTypCd == "60") {
						
						// 그룹분석속성 타입이 60이면서 해당 속성의 입력된 값이 잇을 경우
						if (sapL3GrpAttrListData[i].val != null && sapL3GrpAttrListData[i].val != "") {
							trHtml[i] += "		<input type='text'	name='freeTxt60'			id='freeTxt60'				maxlength='8'		style='ime-mode:disabled'	onkeypress='NewOnlyNumber(event);'	value='" + sapL3GrpAttrListData[i].val.substring(0, parseInt(sapL3GrpAttrListData[i].val.indexOf("|"))) + "'			/>";
							trHtml[i] += "		<select   			name='displayUnitCode'		id='displayUnitCode' 		 >";
							trHtml[i] += "			<option value=''>선택</option>";
							
							for (var xz = 0; xz < kyukeoklist.length; xz++) {
								if (sapL3GrpAttrListData[i].val.substring(parseInt(sapL3GrpAttrListData[i].val.indexOf("|") + 1), sapL3GrpAttrListData[i].val.length) == kyukeoklist[xz].codeId) {
									trHtml[i] += "			<option value='" + kyukeoklist[xz].codeId + "'	selected>" + kyukeoklist[xz].codeNm + "</option>";
								} else {
									trHtml[i] += "			<option value='" + kyukeoklist[xz].codeId + "'>" + kyukeoklist[xz].codeNm + "</option>";
								}
																
							}						
							trHtml[i] += "		</select>&nbsp;<font color='red'>(숫자만 입력가능)</font>";
						
						// 그룹분석속성 타입이 60이면서 해당 속성의 입력된 값이 없을 경우	
						} else {
							
							trHtml[i] += "		<input type='text'	name='freeTxt60'			id='freeTxt60'				maxlength='8'		style='ime-mode:disabled'	onkeypress='NewOnlyNumber(event);'	value='" + sapL3GrpAttrListData[i].val + "'			/>";
							trHtml[i] += "		<select   			name='displayUnitCode'		id='displayUnitCode' 		 >";
							trHtml[i] += "			<option value=''>선택</option>";
							
							for (var xz = 0; xz < kyukeoklist.length; xz++) {								
								trHtml[i] += "			<option value='" + kyukeoklist[xz].codeId + "'>" + kyukeoklist[xz].codeNm + "</option>";																
							}						
							trHtml[i] += "		</select>&nbsp;<font color='red'>(숫자만 입력가능)</font>";
						}
																		
					} else if (sapL3GrpAttrListData[i].attTypCd == "30") {						
						trHtml[i] += "		<input type='text' 	maxlength='25' 	id='freeTxt30' 		name='freetxt30' 	style='width:150px;ime-mode:disabled' onkeypress='NewOnlyNumber(event);'	value='" + sapL3GrpAttrListData[i].val + "'	/>&nbsp;<font color='red'>(숫자만 입력가능)</font>";												
						
					} else if (sapL3GrpAttrListData[i].attTypCd == "40") {						
						trHtml[i] += "		<input type='text' 	maxlength='25'	 id='freeTxt40' 	name='freetxt40' 	style='width:150px;'	value='" + sapL3GrpAttrListData[i].val + "' />	";													
					}

					trHtml[i] += "		<input type='hidden'	name='attId'		id='attId'		value='" + sapL3GrpAttrListData[i].attId + 	"'/>		";
					trHtml[i] += "		<input type='hidden'	name='attTyp'		id='attTyp'		value='" + sapL3GrpAttrListData[i].attTypCd+ "'/>		";
					trHtml[i] += "	</td>";
					trHtml[i] += "</tr>";	
				}				
			}
								
			$("#grpAttrTbl").append(trHtml.join(''));						
			$("#grpAttrTbl").show();			
		} else {
			// 그룹분석속성 동적 html 초기화
			$("#grpAttrTbl tr").remove();
		}
		
		
	}
	
	/* 선택된 SAP 소분류에 따른 그룹분석속성 리스트 , 그룹분석속성의 콤보리스트 조회 */
	function _eventCallSapL3GrpAttr(val) {

		var paramInfo = {};
		
		paramInfo["newProductCode"]	=	"";				// 상품코드
		paramInfo["sellCode"]		=	"";				// 판매코드
		paramInfo["srchL4Cd"]		=	$("#l4GroupCode option:selected").val().replace(/\s/gi, '');	// 세분류			
		paramInfo["majorCd"]		=	"PRD17";														// 규격단위 콤보리스트를 조회하기 위한 Parameter
		paramInfo["entpCd"]			=	$("#searchForm #entp_cd").val().replace(/\s/gi, '');			// 선택한 협렵업체코드
		paramInfo["l1Cd"]			=	$("select[name='l1Cd']").val().replace(/\s/gi, '');				// 선택한 대분류
		
		return
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			//url : '<c:url value="/edi/product/selectSapL3CdAttrList.json"/>',							// 입력된 값 디폴트로 콤보박스에 안넣어주고 사용할떄
			//url : '<c:url value="/edi/product/selectProductGrpAttrList.json"/>',
			url : '<c:url value="/edi/product/selectProductBatchGrpAttrList.json"/>',			
			data : JSON.stringify(paramInfo),
			success : function(data) {	
				
				// sap 소분류에 따른 그룹분석속성리스트 Data
				_eventSetsapL3GrpAttrList(data);
			}
		});					
	}
	
	/* 대분류 콤보박스 구성 */
	function _eventSelectL1ListProductBatch() {
		
		//대분류 콤보박스 초기화
		$("#l1GroupCode option").not("[value='']").remove();
		
		var paramInfo	=	{};

		paramInfo["entpCode"]	=	$("#searchForm #entp_cd").val().replace(/\s/gi, '');
		
		//console.log(paramInfo);
		//return;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectWholeProductAttrBatchTeamCombo.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				
				var resultList	=	data.resultList;
				var options		=	"";
				if (resultList.length > 0) {
					
					var eleHtml = [];
					for (var i = 0; i < resultList.length; i++) {		
						eleHtml[i] = "<option value='"+resultList[i].l1Cd+"'>"+resultList[i].l1Nm+"</option>"+"\n";				
					}
														
					$("#l1GroupCode").append(eleHtml.join(''));						
				}				
			    				
			}
		});
	}
	
	/* 세분류 콤보구성 박스 */
	function _eventSelectL4ListProductBatch(val) {
		var paramInfo	=	{};

		paramInfo["entpCode"]	=	$("#searchForm #entp_cd").val().replace(/\s/gi, '');	
		paramInfo["l1Cd"]		=	val;		
		
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectProductBatchL4CdList.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				
				var resultList	=	data.resultList;
				var options		=	"";
				if (resultList.length > 0) {
					
					var eleHtml = [];
					for (var i = 0; i < resultList.length; i++) {		
						eleHtml[i] = "<option value='"+resultList[i].l4Cd+"'>"+resultList[i].l4Nm+"</option>"+"\n";				
					}
														
					$("#l4GroupCode").append(eleHtml.join(''));						
				}				
			    				
			}
		});
	}
	
	/* _eventClearTeamCd */
	function _eventClearTeamCd(gubun, val) {
		
		//세분류 콤보박스 초기화
		$("#l4GroupCode option").not("[value='']").remove();
			
		// 세분류 콤보박스 호출
		_eventSelectL4ListProductBatch(val);										
	}
	
	/* 검색조건 그룹소분류 콤보박스 구성  */
	function _eventClearGrpCd(gubun, val) {
		
		//그룹소분류 콤보박스 초기화
		$("#srchGrpCd option").not("[value='']").remove();
			
		//그룹소분류 콤보박스 호출
		_eventSelectSrchGrpCd(val);										
	}
	
	/* 검색조건 그룹소분류 ajax 호출*/
	function _eventSelectSrchGrpCd(val) {
		var paramInfo	=	{};

		paramInfo["entpCode"]	=	$("#searchForm #entp_cd").val().replace(/\s/gi, '');	
		paramInfo["sapL3Cd"]	=	val;		
		
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectSrchGrpCd.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				
				var resultList	=	data.resultList;
				var options		=	"";
				if (resultList.length > 0) {
					
					var eleHtml = [];
					for (var i = 0; i < resultList.length; i++) {		
						eleHtml[i] = "<option value='"+resultList[i].grpCd+"'>"+resultList[i].grpCdNm+"</option>"+"\n";				
					}
														
					$("#srchGrpCd").append(eleHtml.join(''));						
				}				
			    				
			}
		});
	}
		
	
	/* 조회 */
	function doSearch() {						
		goPage("1");				
	}
	
	/* 조회 후처리(data 마스터코드 객체 그리기) */
	function _setTbodyMasterValue(json) {
		var data 		=	json.resultList;
		
		setTbodyInit("dataListbody"); // dataList 초기화
		
		$("input[name='pageIdx']").val(json.pageIdx);
				
		if (data.length > 0) {					
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");		
			$("#paging-ul").html(json.contents);
		} else {		
			setTbodyNoResult("dataListbody",5);			
			$("#paging-ul").html("");
		}
		
		_eventSetsapL3GrpAttrList(json);				
	}

	
	function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
		$("#entp_cd").val(strVendorId);
	}
	
	
	/* 상품 수정 페이지 이동 */
	function goDetailView (regDate, sellCode, newProductCode, l4Cd, productName, venCd, sapL3Cd, grpCd) {
		var form	=	document.hiddenForm;
		
		$("#hiddenForm	#regDate").val(regDate);
		$("#hiddenForm	#sellCode").val(sellCode);
		$("#hiddenForm	#newProductCode").val(newProductCode);
		$("#hiddenForm	#srchL4Cd").val(l4Cd);		
		form.regDate.value = regDate;
		form.sellCode.value = sellCode;
		form.newProductCode.value = newProductCode;
		form.srchL4Cd.value = l4Cd;
		form.productName.value = productName;
		form.entpCd.value = venCd;
		form.pageGbn.value = "BATCH";
		form.l1Cd.value = $("select[name='l1Cd']").val().replace(/\s/gi, '');
		form.pageIdx.value = $("input[name='pageIdx']").val();		
		form.recordCnt.value = $("select[name='recordCnt']").val();										//한페이지에 보여질 게시물 건수
		form.sapL3Cd.value = sapL3Cd;
		form.l4Cd.value = $("select[name='l4Cd']").val().replace(/\s/gi, '');
		form.grpCd.value = grpCd;
		form.srchProdGbn.value = $("select[name='srchProdGbn']").val().replace(/\s/gi, '');				//상품구분[1:Hyper, 2:Vic, 3:공통]
		form.srchGrpCd.value = $("select[name='srchGrpCd']").val().replace(/\s/gi, '');
		
		//form.srchCompleteGbn.value = $("select[name='srchCompleteGbn']").val().replace(/\s/gi, '');	2016.03.07 이후 제외 확정구분
		
		if ($("#searchForm #entp_cd").val().replace(/\s/gi, '') == "") {
			form.entpCode.value = "all";
		} else {
			form.entpCode.value = $("#searchForm #entp_cd").val().replace(/\s/gi, '');
		}
		
		//alert($("#entp_cd").val());
		
		form.action = "<c:url value='/edi/product/updateWholeProduct.do'/>";
		form.submit();
		
	}
	
	
	/* 일괄저장 */
	function btnSaveProductGroupAttr() {
		
		//븐석속성 입력 TR Length
		var attrTrLen	=	$("#grpAttrTbl tr").length;
		if (attrTrLen <= 0) {
			alert("조회된 그룹분석속성이 없습니다");
			return;
		}
		
		var paramInfo	=	{};
		var chkCnt = $("input[name=chkAttId]:checked").length;					// 체크된 카운트			
		var arrGrpAttr		=	new Array();									// 그룹분석속성분류코드
		var arrGrpAttrTyp	=	new Array();									// 그룹분석속성분류코드 타입[10:combo, 20:table column, 30:freeForm digit, 40:freeForm Text, 50:Brand Popup, 51:Maker Popup]		
		
		if (chkCnt <= 0) {
			alert("조회된 상품이 없거나 선택된 상품이 없습니다.");
			return;
		}
		
		var GrpAttrLen	=	$("#grpAttrTbl tr").length;
		
		for (var i = 0; i < GrpAttrLen; i++) {			
			var info	=	{};			
			arrGrpAttr[i] 		= $("#grpAttrTbl input[name='attId']").eq(i).val();
			arrGrpAttrTyp[i]	= $("#grpAttrTbl input[name='attTyp']").eq(i).val();							
		}
					
		var attArrVal	=	"";
		
		// 테이블의 input, select 요소를 모두 찾아 값을 추출한다.
		$("#grpAttrTbl").find("input, select").each(function() {
			if (this.name != "attTyp" && this.name != "attId" && this.name != "sapL3CdCompboList" && this.name != "brandCode" && this.name != "makerCode") {

				if (this.name == "freeTxt60") {
					
					//if ($(this).val().replace(/\s/gi, '') != "") {
						if ( $(this).val().replace(/\s/gi, "") == "" && $("#displayUnitCode").val().replace(/\s/gi, "") == "" ) {
							attArrVal += ",";
						} else {
							attArrVal += $(this).val() + "|" + $("#displayUnitCode").val() + ",";	
						}
							
					//}
				} else if (this.name == "brandName") {					
					// 선택된 브랜드 코드만 얻어온다.					
					attArrVal += $("#grpAttrTbl input[name='brandCode']").val().replace(/\s/gi, '') + ",";										
				} else if (this.name == "makerName") {
					// 선택된 메이커 코드만 얻어온다.					
					attArrVal += $("#grpAttrTbl input[name='makerCode']").val().replace(/\s/gi, '') + ",";					
				} else {
					
					if (this.name != "displayUnitCode") {
						// Value값들을 ',' 구분자로 문자열로 완성
						attArrVal += $(this).val() + ",";	
					}
						
				}
			}										
		});
		
		//완성한 문자열을 배열로 구성한다.
		var attArrVal	=	attArrVal.substr(0, (attArrVal.length -1)).split(",");
		
		// 그룹분석속성분류 코드가 60일때 값을 배열로 값을 따로 추출한다.
		var arr60	=	new Array();
		var txt60 = "";
		for (var i = 0; i < attArrVal.length; i++) {

			if (attArrVal[i].replace(/\s/gi, '') != "") {

				if (attArrVal[i].indexOf("|") >= 0) {
					arr60 = attArrVal[i].split("|");
				}
			}
		}
		
		// 단위규격은 같이 입력되게 유도
		for (var i = 0; i < arr60.length; i++) {
			if (arr60[i].replace(/\s/gi, '') == "") {
				alert("단위 규격 입력형태가 올바르지 않습니다.\r\n수량 혹은 단위 규격을 선택해주세요");
				return;
			}
		}						
		
		
		/* for (var i = 0; i < attArrVal.length; i++) {
			if (attArrVal[i].replace(/\s/gi, '') == "") {
				alert("속성값은 반드시 입력하셔야 하며, 속성의 값이 없을경우는 '해당없음' 으로 반드시 선택해주세요.");
				return;
			}
		} */
		
		var arrProdCd		=	new Array();
		var preArrProdCd	=	new Array();
		var arrSrcmkCd		=	new Array();
		var arrSapL3Cd		=	new Array();
		var arrL4Cd			=	new Array();
		var idx 			= 	0;
		var prodIdx			=	0;
		
		for (var i = 0; i < $("input[name=chkAttId]").length; i++) {
			if ($("input[name=chkAttId]").eq(i).is(":checked")) {
				
				if ($("input[name='cfmFg']").eq(i).val() == "1") {
					alert("이미 확정처리된 제품이 포함되어있습니다.\r\n확정된 제품은 확정 취소를 하신 후 수정 하실수 있습니다");
					return;
				}
				
				arrProdCd[idx]	=	$("#searchForm input[name='newProductCode']").eq(i).val();								
				arrSrcmkCd[idx]	=	$("#searchForm input[name='sellCode']").eq(i).val();
				arrSapL3Cd[idx] =  $("#searchForm input[name='sapL3Cd']").eq(i).val();				
				arrL4Cd[idx] 	=  $("#searchForm input[name='l4Cd']").eq(i).val();
				
				idx++;
			}
		}
		
		var uniqueNames = [];

		$.each(arrProdCd, function(i, el){
			if($.inArray(el, uniqueNames) === -1) uniqueNames.push(el);
		});
		
		//console.log("uniqNames ==" + uniqueNames);
		
		
		paramInfo["arrSrcmkCd"]		=	arrSrcmkCd;															//판매코드
		paramInfo["arrProdCd"]		=	uniqueNames;														//상품코드
		paramInfo["arrSapL3Cd"]		=	arrSapL3Cd;															// SAP 소분류
		paramInfo["arrL4Cd"]		=	arrL4Cd;															// SAP 소분류
		paramInfo["l1Cd"]			=	$("select[name='l1Cd']").val().replace(/\s/gi, '');					// SAP 대분류
		paramInfo["srchL4Cd"]		=	$("select[name='l4Cd']").val().replace(/\s/gi, '');					// SAP 소분류
		paramInfo["arrGrpAttr"]		=	arrGrpAttr;															// 그룹분석속성 붑류코드
		paramInfo["arrGrpAttrTyp"]	=	arrGrpAttrTyp;														// 그룹분석속성 붑류코드 타입
		paramInfo["arrVal"]			=	attArrVal;															// 그룹분석속성 붑류값
		paramInfo["entpCd"]			=	$("#entp_cd").val().replace(/\s/gi, '');							// 협력업체코드
		
		
		
		
		
		// 저장 확인 메세지 
		if (!confirm("입력되지 않은 분석속성들은 저장되지 않으며 선택한 상품들은 일괄로 적용됩니다. 저장하시겠습니까?")) {
			return;
		}	
		
		//console.log(paramInfo);
		//return;
		
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/insertGroupAttrBatch.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				//alert(data.msg);
				if (data.msg == "SUCCESS") {
					alert("저장 되었습니다");	
					
					//datalist 초기화
					$("#dataListbody tr").remove();
					
					// 동적분석속성 입력 html 초기화
					$("#grpAttrTbl tr").remove();					
					
				} else {
					alert("저장도중 오류가 발생하였습니다");
				}
				
				// 목록 재조회
				 goPage($("input[name='pageIdx']").val());						
			}
		});
	}
	
	
	/* 일괄삭제 */
	function btnDelProductGroupAttr() {
		
		//븐석속성 입력 TR Length
		var attrTrLen	=	$("#grpAttrTbl tr").length;
		if (attrTrLen <= 0) {
			alert("조회된 그룹분석속성이 없습니다");
			return;
		}
		
		var paramInfo	=	{};
		var chkCnt = $("input[name=chkAttId]:checked").length;					// 체크된 카운트					
		
		if (chkCnt <= 0) {
			alert("조회된 상품이 없거나 선택된 상품이 없습니다.");
			return;
		}
		
		var arrProdCd	=	new Array();
		var arrSrcmkCd	=	new Array();
		var arrSapL3Cd	=	new Array();
		var arrL4Cd		=	new Array();
		var idx 		= 	0;
		
		for (var i = 0; i < $("input[name=chkAttId]").length; i++) {
			if ($("input[name=chkAttId]").eq(i).is(":checked")) {
				
				if ($("input[name='cfmFg']").eq(i).val() == "1") {
					alert("이미 확정처리된 제품이 포함되어있습니다.\r\n확정된 제품은 확정 취소를 하신 후 수정 하실수 있습니다");
					return;
				}
				arrSrcmkCd[idx]	=	$("#searchForm input[name='sellCode']").eq(i).val();
				arrProdCd[idx]	=	$("#searchForm input[name='newProductCode']").eq(i).val();				
				arrSapL3Cd[idx]	=	$("#searchForm input[name='arrSapL3Cd']").eq(i).val();
				arrL4Cd[idx]	=	$("#searchForm input[name='l4Cd']").eq(i).val();
				
				idx++;
			}
		}
		
		paramInfo["arrSrcmkCd"]		=	arrSrcmkCd;															//판매코드
		paramInfo["arrProdCd"]		=	arrProdCd;															//상품코드
		paramInfo["arrSapL3Cd"]		=	arrSapL3Cd;															//SAP 소분류 코드
		paramInfo["arrL4Cd"]		=	arrL4Cd;															//SAP 소분류 코드
		paramInfo["srchL4Cd"]		=	$("select[name='l4Cd']").val().replace(/\s/gi, '');					//기존세분류
		
		
		
		// 저장 확인 메세지 
		if (!confirm("선택된 상품들의 입력되어있는 분석속성들이 일괄 삭제 됩니다. 삭제 하시겠습니까?")) {
			return;
		}
		
		//console.log(paramInfo);
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/delGroupAttrBatch.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				//alert(data.msg);
				if (data.msg == "SUCCESS") {
					alert("저장 되었습니다");	
					
					//datalist 초기화
					$("#dataListbody tr").remove();
					
					// 동적분석속성 입력 html 초기화
					$("#grpAttrTbl tr").remove();	
				} else {
					alert("저장도중 오류가 발생하였습니다");
				}
				
				// 목록 재조회
				goPage($("input[name='pageIdx']").val());						
			}
		});
	}
	
	
	/** ********************************************************
	 * 페이징 처리 함수
	 ******************************************************** */
	function goPage(pageIndex){
		
		$("#chkAll").prop("checked", false);
		
		// 동적분석속성 입력 html 초기화
		$("#grpAttrTbl tr").remove();	
		
		var paramInfo	=	{};
		var venderTypeCd	=	"<c:out value='${epcLoginVO.vendorTypeCd}'/>";
		
		/* if (venderTypeCd.replace(/\s/gi, '') == "06") {
			alert("업체 선택은 필수 입니다.");
			$("#entp_cd").focus();
			return;
		} */
		
		if ($("#searchForm #entp_cd").val().replace(/\s/gi, '') == "") {
			alert("업체선택은 필수 입니다.");
			$("#searchForm #entp_cd").focus();
			return;
		}
		
		var l1Cd 		= $("select[name='l1Cd']").val().replace(/\s/gi, '');
		var l4Cd 		= $("select[name='l4Cd']").val().replace(/\s/gi, '');
		var srchGrpCd 	= $("select[name='srchGrpCd']").val().replace(/\s/gi, '');
		
		if ( l1Cd == "" || l4Cd == "" || srchGrpCd == "") {
			alert("필수 검색조건을 입력하지 않았습니다.");
			return;
		}
		
		paramInfo["entpCode"]		=	$("#searchForm #entp_cd").val().replace(/\s/gi, '');						//협력업체코드
		paramInfo["l1Cd"]			=	l1Cd;																		//대분류
		//paramInfo["l4Cd"]			=	l4Cd;																		//세분류(SAP L3_CD 코드리스트로 변경됨에 따라 현재 사용안함 다시 원래 세분류로 변경되면 사용하면 됨)
		paramInfo["sapL3Cd"]		=	l4Cd;																		//화면 상단의 대분류와 소분류가 SAP 으로 변경된 코드체계로 가져오므로 추가 
		paramInfo["pageIndex"]		=	pageIndex;																	//페이지 번호
		paramInfo["majorCd"]		=	"PRD17";
		paramInfo["recordCnt"]		=	$("select[name='recordCnt']").val();										//한페이지에 보여질 게시물 건수
		paramInfo["srchProdGbn"]	=	$("select[name='srchProdGbn']").val();										//상품구분 검색조건
		paramInfo["srchGrpCd"]		=	$("select[name='srchGrpCd']").val();										//그룹소분류 검색조건
		
		//paramInfo["srchCompleteGbn"]	=	$("select[name='srchCompleteGbn']").val().replace(/\s/gi, '');		//확정여부	2016.03.07 이후 제외
		
		//정렬기준 default는 상품코드 역순
		if ($("input[name='orderGbn']").val().replace(/\s/gi, '') == "") {
			paramInfo["orderGbn"]	=	"srcmkCdAsc";
		} else {
			paramInfo["orderGbn"]	=	$("input[name='orderGbn']").val().replace(/\s/gi, '');	
		}
		
		//console.log(paramInfo);
		
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectWholeProductAttrBatchList.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				_setTbodyMasterValue(data);
			}
		});
	}
	
	/* 일괄완료 */
	function btnComplete(gbn) {
		
		//븐석속성 입력 TR Length
		var attrTrLen	=	$("#grpAttrTbl tr").length;
		if (attrTrLen <= 0) {
			alert("조회된 그룹분석속성이 없습니다");
			return;
		}
		
		var paramInfo	=	{};
		var	cfmFg = "0";														// 완료처리여부['' or 0:미완료, 1:완료]
		var chkCnt = $("input[name=chkAttId]:checked").length;					// 체크된 카운트					
		
		if (chkCnt <= 0) {
			alert("조회된 상품이 없거나 선택된 상품이 없습니다.");
			return;
		}	
		
		if (gbn == "complete") {
			
			if (!confirm("선택하신 제품들의 속성들이 확정처리 되며, 수정은 확정취소 후 가능합니다.\r\n확정처리 하시겠습니까?")) {
				return;			
			}
			
			cfmFg = "1";			
		} else {
			if (!confirm("선택하신 제품들의 확정처리가 취소 되며, 수정이 가능합니다.\r\n취소처리 하시겠습니까?")) {
				return;			
			}
		}	
		
		var arrProdCd	=	new Array();
		var arrSrcmkCd	=	new Array();
		var idx 		= 	0;
		
		for (var i = 0; i < $("input[name=chkAttId]").length; i++) {
			if ($("input[name=chkAttId]").eq(i).is(":checked")) {
				arrSrcmkCd[idx]	=	$("#searchForm input[name='sellCode']").eq(i).val();
				arrProdCd[idx]	=	$("#searchForm input[name='newProductCode']").eq(i).val();
				
				idx++;
			}
		}
				
		paramInfo["arrSrcmkCd"]		=	arrSrcmkCd;															//판매코드
		paramInfo["arrProdCd"]		=	arrProdCd;															//상품코드		
		paramInfo["cfmFg"]			=	cfmFg;
				
		//console.log(paramInfo);
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/updateCompleteGroupAttrBatch.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				//alert(data.msg);
				if (data.msg == "SUCCESS") {
					alert("저장 되었습니다.");				
					
					//datalist 초기화
					$("#dataListbody tr").remove();
					
					// 동적분석속성 입력 html 초기화
					$("#grpAttrTbl tr").remove();	
				} else {
					alert("저장도중 오류가 발생하였습니다");
				}
				
				// 목록 재조회
				goPage($("input[name='pageIdx']").val());						
			}
		});
		
	}
		
</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr class="r1"  bgcolor=ffffff>
	<td align="center">					
			<input type="checkBox"	name="chkAttId"				id="chkAttId"			value="Y"											/>			
			<input type="hidden"	name="sellCode"				id="sellCode"			value="<c:out value='\${sellCode}'/>"				/>
			<input type="hidden"	name="newProductCode"		id="newProductCode"		value="<c:out value='\${newProductCode}'/>"			/>								
			<input type="hidden"	name="cfmFg"				id="cfmFg"				value="<c:out value='\${cfmFg}'/>"					/>
			<input type="hidden"	name="sapL3Cd"				id="sapL3Cd"			value="<c:out value='\${sapL3Cd}'/>"				/>
			<input type="hidden"	name="l4Cd"					id="l4Cd"				value="<c:out value='\${l4Cd}'/>"					/>
	</td>
	<td  align="center">			
		<c:out value="\${hvFg}"/>									
	</td>
	<td align="center"><c:out value="\${newProductCode}"/></td>			
	<td align="center">&nbsp;<c:out value='\${sellCode}'/></td>
	<td><a href="#" onclick="goDetailView('<c:out value="\${regDate}"/>',	'<c:out value="\${sellCode}"/>',	'<c:out value="\${newProductCode}"/>', 		'<c:out value="\${l4Cd}"/>', 	'<c:out value="\${productName}"/>',		'<c:out value="\${venCd}"/>',	'<c:out value="\${sapL3Cd}"/>', 	'<c:out value="\${grpCd}"/>');" 	id="goDetailLnk"	name="goDetailLnk"><c:out value='\${productName}'/></a></td>
	<td align="center">
		
		<font color="blue"><strong><c:out value='\${inputCnt}'/></strong></font> /
		<font color="red"><strong><c:out value='\${x00816Cnt}'/></strong></font> /
  		<strong><c:out value="\${totCnt}" /></strong> 
	</td>		

	<td align="left">			
		<span class='ellipsis' title='<c:out value="\${attrValTxt}" />'><c:out value="\${attrValTxt}" />
	</td>	
</tr>
</script>

</head>

<body>
	<div id="content_wrap">
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm"	id="searchForm" method="post" action="#">
		<input type="hidden" id="entpCode" name="entpCode" value="${param.entpCode}" />
		<input type="hidden" name="pageIndex" value="${param.pageIdx}" />
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">전체상품현황</li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						<input type="hidden" id="storeVal" name="storeVal"  value="${param.storeVal }" />
						<input type="hidden" id="productVal" name="productVal" />
						
						<colgroup>
							<col style="width:10%;" />
							<col style="width:20%;" />
							<col style="width:15%;;" />
							<col style="width:20%;" />
							<col style="width:10%;" />
							<col style="width:20%;" />													
						</colgroup>
						<tr>							 
							<th><span class="star">*</span> 협력업체</th>
							<td >
								<c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<c:if test="${not empty param.entpCode}">
										<input type="text" name="entp_cd" id="entp_cd" readonly="readonly" value="${param.entpCode}" style="width:40%;"/>
										</c:if>
										<c:if test="${empty param.entpCode}">
											<input type="text" name="entp_cd" id="entp_cd" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
										</c:if>
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<c:if test="${not empty param.entpCode}">
											<%-- <html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="${param.entpCode}" dataType="CP" comType="SELECT" formName="form" defName="전체"  /> --%>
											
											<c:if test="${param.entpCode ne 'all'}">
												<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="${param.entpCode}" dataType="CP" comType="SELECT" formName="form" defName="전체"  />	
											</c:if>
											
											<c:if test="${param.entpCode eq 'all'}">
												<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;"  dataType="CP" comType="SELECT" formName="form" defName="전체"  />	
											</c:if>
											
											
										</c:if>
										<c:if test="${ empty param.entpCode}">
											<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="${epcLoginVO.repVendorId}" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
										</c:if>
									</c:when>
								</c:choose>
							</td>							
							<%-- <th><span class="star">*</span> 팀</th>
							<td>
								<select id="teamGroupCode" name="teamCode" class="required" style="width:100px;">
									<option value="">선택</option>
									<c:forEach items="${teamList}" var="team" varStatus="index" >
										<option value="${team.teamCode}"	>${team.teamName}</option> 
									</c:forEach>
								</select>
							</td> --%>
							
							
							<!-- 2016.03.07 이후로 제외 -->							
							<!-- <th>확정여부</th>
							<td colspan="3">
								<select id="srchCompleteGbn" name="srchCompleteGbn">
									<option value="">전체</option>
									<option value="1">확정</option>
									<option value="0">미확정</option>
								</select>
							</td> -->
							
							<th>상품구분</th>
							<td>
								<select id="srchProdGbn" name="srchProdGbn">
									<option value="">전체</option>
									<option value="1">Hyper</option>
									<option value="2">Vic</option>
								</select>
							</td>
							
							<th> 게시물 건수</th>
							<td>
								<select id="recordCnt" name="recordCnt">
									<option value="20">20</option>
									<option value="50">50</option>
									<option value="100">100</option>
								</select>
							</td>		
																												
						</tr>	
						<tr>	
							<th><span class="star">*</span> 대분류</th>
							<td >
								<select id="l1GroupCode" name="l1Cd" class="required" style="width:150px;">
									<option value="">선택</option>									
								</select>
							</td>
							<th><span class="star">*</span> 소분류</th>
							<td>
								<select id="l4GroupCode" name="l4Cd">
									<option value="">선택</option>
								</select>
							</td>
							
							<th><span class="star">*</span> 그룹 소분류</th>
							<td>
								<select id="srchGrpCd" name="srchGrpCd">
									<option value="">선택</option>
								</select>
							</td>
						</tr>
						
					</table>
					<table  cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
						<tr>
							<td colspan="6" bgcolor=ffffff>
								<strong>&nbsp;<font color="red">※업체별로 상품 코드가 많을 경우 수십초의 시간이 걸릴 수 있습니다.</font></strong><br/>
								<strong>&nbsp;<font color="red">※해당상품들의 분석속성이 없을경우 해당상품들은 조회되지 않습니다.</font></strong><br/>								
							</td>
						</tr>
					</table>
					
					
					
					<div id="wrap_attrMenu">
						<div class="wrap_search">
							<div class="bbs_list">
								<ul class="tit">
									<li class="tit">속성 입력</li>		
									<li class="btn">										
										<a href="#" class="btn" onclick="btnSaveProductGroupAttr();">저장</a>
										<a href="#" class="btn" onclick="btnDelProductGroupAttr();">삭제</a>
										<!-- 2016.03.07 이후 확정 제외 -->
										<!-- <a href="#" class="btn" onclick="btnComplete('complete');">확정</a>
										<a href="#" class="btn" onclick="btnComplete('cancel');">확정취소</a> -->
									</li>				
								</ul>
								
								
							 	<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0"		id="grpAttrTbl"		>
							 		<colgroup	id="grpAttrTblColGrp">
							 			<col style="width:10%;" />
										<col style="width:20%;" />
										<col style="width:15%;;" />
										<col style="width:20%;" />	
							 		</colgroup>							 		
							 		<!-- -------------------------------------------------------------------------->
							 		
							 		
							 		<!-- <tr id='dynamicGroupAttrTr'>
							 			<td>12123</td>
							 			<td>12123</td>
							 			<td>12123</td>
							 		</tr>
							 		
							 		<tr id='dynamicGroupAttrTr'>
							 			<td>12123</td>
							 			<td>12123</td>
							 			<td>12123</td>
							 		</tr>
							 		
							 		<tr id='dynamicGroupAttrTr'>
							 			<td>12123</td>
							 			<td>12123</td>
							 			<td>12123</td>
							 		</tr> -->
							 		
							 	</table>
								
									
														
							</div>
						</div>
					</div>
					
					
					
				</div>
				
				
				<!-- 1검색조건 // -->
			</div>
			<!--	2 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
				
					<ul class="tit">
						<li class="tit">검색내역</li>						
					</ul>
					
					<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;">
					<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=1388 bgcolor=efefef>
					<colgroup>				
						<col style="width:50px"/>		
						<col style="width:100px"/>
						<col style="width:100px"/>
						<col style="width:200px"/>
						<col style="width:200px"/>
						<col  />						
					</colgroup>
					<tr bgcolor="#e4e4e4">
						<th><input type="checkBox"	name="chkAll"	id="chkAll"/></th>
						<!-- <th>완료여부</th> -->						
						<th>상품구분</th>
						<th id="thProdCd">상품코드			<span id="spProdCd"		style="cursor:pointer">▲</span></th>							
						<th id="thSrcmkCd">판매(88)코드	<span id="spSrcmkCd"	style="cursor:pointer">▲</span></th>						
						<th id="thProdNm">상품명			<span id="spProdNm"		style="cursor:pointer">▲</span></th>			
						<th>입력속성 / 해당없음 / 총 갯수</th>
						<th>속성 값</th>						
					</tr>
					
					<tbody id="dataListbody" />
					
					</table>
					</div>
					
					
					<!-- Pagging Start ---------->			
					<div id="paging_div">
				        <ul class="paging_align" id="paging-ul" style="width: 400px"></ul>
					</div>
					<!-- Pagging End ---------->							
					
				</div>
			</div>
		</div>
		</form>		
	</div>


	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>상품</li>
					<li>상품현황관리</li>
					<li class="last">기존상품수정</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
	
	<form	name="hiddenForm"	name="hiddenForm"		method="post">
		<input type="hidden"	name="regDate"				id="regDate"		/>
		<input type="hidden"	name="sellCode"				id="sellCode"		/>
		<input type="hidden"	name="newProductCode"		id="newProductCode"	/>
		<input type="hidden"	name="srchL4Cd"				id="srchL4Cd"		/>
		<input type="hidden"	name="productName"			id="productName"	/>
		<input type="hidden"	name="entpCd"				id="entpCd"			/>
		<input type="hidden"	name="pageGbn"				id="pageGbn"		/>
		<input type="hidden"	name="l1Cd"					id="l1Cd"			/>
		<input type="hidden"	name="pageIdx"				id="pageIdx"		value="${pageIdx}"/>
		<input type="hidden"	name="orderGbn"				id="orderGbn"		/>
		<input type="hidden"	name="srchCompleteGbn"		id="srchCompleteGbn"		/>
		<input type="hidden"	name="entpCode"				id="entpCode"		/>
		<input type="hidden"	name="recordCnt"			id="recordCnt"		/>
		<input type="hidden"	name="sapL3Cd"				id="sapL3Cd"		/>
		<input type="hidden"	name="l4Cd"					id="l4Cd"			/>
		<input type="hidden"	name="grpCd"				id="grpCd"			/>
		<input type="hidden"	name="srchProdGbn"			id="srchProdGbn"	/>
		<input type="hidden"	name="srchGrpCd"			id="srchGrpCd"		/>
		
		
		
		<!-- 문자열로 담아서 넘어갈 배열 -->
		<input type="hidden"	name="arrSrcmkCd"			id="arrSrcmkCd"		/>
		<input type="hidden"	name="arrProdCd"			id="arrProdCd"		/>		
	</form>
	
</div>
</body>
</html>
