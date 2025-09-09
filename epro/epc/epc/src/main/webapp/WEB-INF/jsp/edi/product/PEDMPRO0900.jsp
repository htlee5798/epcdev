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
		//팀분류 체인지 이벤트
		$("#teamGroupCode").change(function (){
			//data list 초기화
			$("#dataListbody tr").remove();
			
			_eventClearTeamCd("team", $(this).val());
		});
		
		//대분류 체인지 이벤트
		$("#l1GroupCode").change(function (){
			//datalist 초기화
			$("#dataListbody tr").remove();
			
			_eventClearTeamCd("L1CD", $(this).val());
		});
		
		//세분류 체인지 이벤트
		$("#l4GroupCode").change(function (){
			//data list 초기화
			$("#dataListbody tr").remove();
			
			// 동적분석속성 입력 html 초기화
			$("#grpAttrTbl #dynamicGroupAttrTr").remove();
			
			// sap 소분류 콤보박스 구성 초기화
			$("#sapL3CdCompboList option").not("[value='']").remove();	//SAP 소분류 콤보박스 초기화
											
			_eventInputSapL3Cd($(this).val());
		});
		
		// SAP 소분류 콤보박스 체인지 이벤트
		$("#sapL3CdCompboList").change(function() {					
			_eventCallSapL3GrpAttr($(this).val().replace(/\s/gi, ''));		// 선택된 SAP 소분류에 따른 그룹분석속성 리스트 조회
		});	
		
		//속성입력 div 숨김
		$("#wrap_attrMenu").attr("style", "display:none");		
		
		//체크박스 전체선택 true, false
		$("#chkAll").click(function() {
			//alert("전체선택");
			if ($("#chkAll").prop("checked")) {
				$("input[type=checkbox]").prop("checked", true);
			}else {
				$("input[type=checkbox]").prop("checked", false);
			}
		});
		
		var teamCd	=	"<c:out value='${param.teamCd}'/>";
		var l1Cd	=	"<c:out value='${param.l1Cd}'/>";
		var l4Cd	=	"<c:out value='${param.srchL4Cd}'/>";
		
		if (teamCd.replace(/\s/gi, '') != "") {
			$("select[name='teamCode']").val(teamCd);
			
			//대분류 콤보박스 호출 함수
			_eventSelectL1ListProductBatch(teamCd);
			
			$("select[name='l1Cd']").val(l1Cd);
			
			// 세분류 콤보박스 호출
			_eventSelectL4ListProductBatch(l1Cd);
			
			$("select[name='l4Cd']").val(l4Cd);
			
			_eventInputSapL3Cd(l4Cd);
			
			//조회 함수 호출
			doSearch();
		}
		
	});
	
	/* sap 소분류에 따른 그룹분석속성리스트 Data 구성 */
	function _eventSetsapL3GrpAttrList(json) {
		
		var sapL3GrpAttrListData	=	json.sapL3GrpAttrList;
		var sapL3GrpAttrComboList	=	json.sapL3GrpAttrComboList;		
		var kyukeoklist				=	json.kyekeokList;
		
		if (sapL3GrpAttrListData.length > 0) {
			
			// 그룹분석속성 동적 html 초기화
			$("#grpAttrTbl tr:eq(1)").remove();
						
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
				//if (sapL3GrpAttrListData[i].attTypCd != "50" && sapL3GrpAttrListData[i].attTypCd != "51" && sapL3GrpAttrListData[i].attTypCd != "60") {
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
				//}				
			}
								
			$("#grpAttrTbl").append(trHtml.join(''));						
			$("#grpAttrTbl").show();			
		} else {
			// 그룹분석속성 동적 html 초기화
			$("#grpAttrTbl #dynamicGroupAttrTr").remove();
		}
	}
	
	/* 선택된 SAP 소분류에 따른 그룹분석속성 리스트 , 그룹분석속성의 콤보리스트 조회 */
	function _eventCallSapL3GrpAttr(val) {
		
		var paramInfo = {};
		
		paramInfo["newProductCode"]	=	"";		// 상품코드
		paramInfo["sellCode"]		=	"";				// 판매코드
		paramInfo["srchL4Cd"]		=	$("#l4GroupCode option:selected").val().replace(/\s/gi, '');	// 세분류		
		paramInfo["sapL3Cd"]		=	val;															// sap 소분류 코드
		paramInfo["majorCd"]		=	"PRD17";														// 규격단위 콤보리스트를 조회하기 위한 Parameter
		
		//return
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			//url : '<c:url value="/edi/product/selectSapL3CdAttrListBatch.json"/>',					// 입력된 값 디폴트로 콤보박스에 박아줄 경우 해당 url 사용
			url : '<c:url value="/edi/product/selectSapL3CdAttrList.json"/>',							// 입력된 값 디폴트로 콤보박스에 안넣어주고 사용할떄 
			data : JSON.stringify(paramInfo),
			success : function(data) {	
				
				// sap 소분류에 따른 그룹분석속성리스트 Data
				_eventSetsapL3GrpAttrList(data);
			}
		});					
	}
	
	/* SAP 소분류 콤보박스 구성 */
	function _eventSetSapL3CdComboList(json) {
		var data = json.sapL3CdComboList;
		
		$("#sapL3CdCompboSpTxt").text("소분류");
		
		if (data.length > 0) {
			
			var eleHtml = [];
			for (var i = 0; i < data.length; i++) {		
				eleHtml[i] = "<option value='"+data[i].sapL3Cd+"'>"+data[i].sapL3Nm+"</option>"+"\n";				
			}
						
			$("select[name='sapL3CdCompboList']").append(eleHtml.join(''));										// 기존상품세분류
			
		} 
	}
	
	/* 세분류에 매핑되어 있는 SAP_L3_CD 소분류 콤보박스 구성 조회 */
	function _eventCallAjaxGrpAttrList(val) {
		var paramInfo = {};
				
		paramInfo["srchL4Cd"]		=	val;								// 기존상품세분류
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectSapMapAttrList.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {	
				
				// SAP 소분류 콤보박스 구성
				_eventSetSapL3CdComboList(data);
			}
		});		
	}
	
	/* 세분류 체인지 이벤트 */
	function _eventInputSapL3Cd(val) {		
		if (val.replace(/\s/gi, '') == "") {			
			$("#wrap_attrMenu").attr("style", "display:none");			//선택된 세분류가 없을경우 속성입력 div display:none	
		} else {
			$("#wrap_attrMenu").attr("style", "display:block");			//선택된 세분류가 있을경우 속성입력 div display:block
		}		
		
		//세분류에 매핑되어 있는 SAP_L3_CD 소분류 콤보박스 구성조회
		_eventCallAjaxGrpAttrList(val);
	}
	
	/* 대분류 콤보박스 구성 */
	function _eventSelectL1ListProductBatch(val) {
		var paramInfo	=	{};

		paramInfo["teamCode"]	=	val;
		paramInfo["l1Cd"]		=	"";			
		
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
						eleHtml[i] = "<option value='"+resultList[i].teamCode+"'>"+resultList[i].teamName+"</option>"+"\n";				
					}
					
									
					$("#l1GroupCode").append(eleHtml.join(''));						
				}				
			    				
			}
		});
	}
	
	/* 세분류 콤보구성 박스 */
	function _eventSelectL4ListProductBatch(val) {
		var paramInfo	=	{};

		paramInfo["teamCode"]	=	$("#teamGroupCode option:selected").val();
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
						eleHtml[i] = "<option value='"+resultList[i].categoryCode+"'>"+resultList[i].categoryName+"</option>"+"\n";				
					}
					
									
					$("#l4GroupCode").append(eleHtml.join(''));						
				}				
			    				
			}
		});
	}
	
	/* _eventClearTeamCd */
	function _eventClearTeamCd(gubun, val) {
		// 팀 콤보박스 변경시
		if (gubun.replace(/\s/gi, '') == "team") {
			//대분류 콤보박스 초기화	
			$("#l1GroupCode option").not("[value='']").remove();	
			
			//세분류 콤보박스 초기화
			$("#l4GroupCode option").not("[value='']").remove();
						
			//대분류 콤보박스 호출 함수
			_eventSelectL1ListProductBatch(val);
			
		// 대분류 콤보박스 변경시	
		} else {
			//세분류 콤보박스 초기화
			$("#l4GroupCode option").not("[value='']").remove();
			
			// 세분류 콤보박스 호출
			_eventSelectL4ListProductBatch(val);						
		}
		
		// sap 소분류 초기화
		$("#sapL3CdCompboList option").not("[value='']").remove();	//SAP 소분류 콤보박스 초기화
		
		// sap 소분류 초기화에 따른 동적 분석속성입력 tr remove
		$("#grpAttrTbl #dynamicGroupAttrTr").remove();
		
	}
	
	/* 조회 */
	function doSearch() {
		var paramInfo	=	{};
		var venderTypeCd	=	"<c:out value='${epcLoginVO.vendorTypeCd}'/>";
		
		if (venderTypeCd.replace(/\s/gi, '') == "06") {
			alert("업체 선택은 필수 입니다.");
			$("#entp_cd").focus();
			return;
		}
		
		var teamCode = $("select[name='teamCode']").val().replace(/\s/gi, '');
		var l1Cd = $("select[name='l1Cd']").val().replace(/\s/gi, '');
		var l4Cd = $("select[name='l4Cd']").val().replace(/\s/gi, '');
		
		if (teamCode == "" || l1Cd == "" || l4Cd == "") {
			alert("필수 검색조건을 입력하지 않았습니다.");
			return;
		}
		
		paramInfo["entpCode"]	=	$("#searchForm #entp_cd").val().replace(/\s/gi, '');	//협력업체코드
		paramInfo["teamCode"]	=	teamCode;		//팀코드
		paramInfo["l1Cd"]		=	l1Cd;			//대분류
		paramInfo["l4Cd"]		=	l4Cd;			//세분류
		paramInfo["pageIndex"]	=	"1";			//페이지 번호
		
		//조회 함수 호출
		_eventCall(paramInfo);				
	}
	
	/* 조회 ajax 호출 */	
	function _eventCall(paramInfo) {
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
	
	/* _eventCall() 후처리(data 마스터코드 객체 그리기) */
	function _setTbodyMasterValue(json) {
		var data = json.resultList;
		
		setTbodyInit("dataListbody"); // dataList 초기화
		
		if (data != null && data.length > 0) {
			for (var i=0; i< data.length; i++) {
				data[i].count = i+1;		
				
				if (data[i].sapL3Cd == null || data[i].sapL3Cd == "null") {
					data[i].sapL3Cd = "";
				}
				
			}
		}
		
		if (data.length > 0) {
			
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");		
			//$("#paging-ul").html(json.contents);
		} else {		
			setTbodyNoResult("dataListbody",6);			
			//$("#paging-ul").html("");
		}
	}

	
	function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
		$("#entp_cd").val(strVendorId);
	}
	
	
	/* 상품 수정 페이지 이동 */
	function goDetailView (regDate, sellCode, newProductCode, l4Cd, productName, venCd, sapL3Cd) {
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
		form.sapL3Cd.value = sapL3Cd;
		form.pageGbn.value = "BATCH";
		form.teamCd.value = $("select[name='teamCode']").val().replace(/\s/gi, '');
		form.l1Cd.value = $("select[name='l1Cd']").val().replace(/\s/gi, '');
		form.pageGbn.value = "BATCH";
		
		//alert($("#entp_cd").val());
		
		form.action = "<c:url value='/edi/product/updateWholeProduct.do'/>";
		form.submit();
		
	}
	
	
	/* 일괄저장 */
	function btnSaveProductGroupAttr() {
		var paramInfo	=	{};
		var chkCnt = $("input[name=chkAttId]:checked").length;					// 체크된 카운트			
		var arrGrpAttr		=	new Array();									// 그룹분석속성분류코드
		var arrGrpAttrTyp	=	new Array();									// 그룹분석속성분류코드 타입[10:combo, 20:table column, 30:freeForm digit, 40:freeForm Text, 50:Brand Popup, 51:Maker Popup]
		
		if ($("select[name='sapL3CdCompboList']").val().replace(/\s/gi, '') == "") {
			alert("소분류가 선택되지 않았습니다");
			$("select[name='sapL3CdCompboList']").focus();
			return;			
		}
		
		if (chkCnt <= 0) {
			alert("조회된 상품이 없거나 선택된 상품이 없습니다.");
			return;
		}
		
		var GrpAttrLen	=	$("#grpAttrTbl #dynamicGroupAttrTr").length;
		
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
		paramInfo["srchL4Cd"]		=	$("select[name='l4Cd']").val().replace(/\s/gi, '');					//기존세분류
		paramInfo["sapL3Cd"]		=	$("select[name='sapL3CdCompboList']").val().replace(/\s/gi, '');	//SAP 소분류
		paramInfo["arrGrpAttr"]		=	arrGrpAttr;															// 그룹분석속성 붑류코드
		paramInfo["arrGrpAttrTyp"]	=	arrGrpAttrTyp														// 그룹분석속성 붑류코드 타입
		paramInfo["arrVal"]			=	attArrVal;															// 그룹분석속성 붑류값
		paramInfo["entpCd"]			=	$("#entp_cd").val().replace(/\s/gi, '');							// 협력업체코드
		
		// 저장 확인 메세지 
		if (!confirm("선택된 소분류의 입력되지 않은 그룹분석속성들은 저장되지 않으며 선택한 제품들은 일괄로 적용됩니다. 저장하시겠습니까?")) {
			return;
		}	
		
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
				} else {
					alert("저장도중 오류가 발생하였습니다");
				}
				
				// 목록 재조회
				 doSearch();						
			}
		});
	}
</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr class="r1"  bgcolor=ffffff>
		<td align="center">			
			<input type="checkBox"	name="chkAttId"				id="chkAttId"			value="Y"				/>			
			<input type="hidden"	name="sellCode"				id="sellCode"			value="<c:out value='\${sellCode}'/>"		/>
			<input type="hidden"	name="newProductCode"		id="newProductCode"		value="<c:out value='\${newProductCode}'/>"	/>								
		</td>
		<td align="center"><c:out value='\${count}'/></td>
		<td align="center"><c:out value='\${regDate}'/></td>
		<td align="left">&nbsp;<c:out value='\${sellCode}'/></td>
		<td align="center"><c:out value='\${newProductCode}'/></td>
		<td><a href="#" onclick="goDetailView('<c:out value="\${regDate}"/>',	'<c:out value="\${sellCode}"/>',	'<c:out value="\${newProductCode}"/>', 		'<c:out value="\${l4Cd}"/>', 	'<c:out value="\${productName}"/>',		'<c:out value="\${venCd}"/>',	'<c:out value="\${sapL3Cd}"/>');" 	id="goDetailLnk"	name="goDetailLnk"><c:out value='\${productName}'/></a></td>									
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
		<input type="hidden" name="pageIndex" value="${param.pageIndex}" />
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
						</colgroup>
						<tr>							 
							<th><span class="star">*</span> 협력업체</th>
							<td>
								<c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<c:if test="${not empty param.entp_cd}">
										<input type="text" name="entp_cd" id="entp_cd" readonly="readonly" value="${param.entp_cd}" style="width:40%;"/>
										</c:if>
										<c:if test="${empty param.entp_cd}">
											<input type="text" name="entp_cd" id="entp_cd" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
										</c:if>
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<c:if test="${not empty param.entp_cd}">
											<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="${param.entp_cd}" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
										</c:if>
										<c:if test="${ empty param.entp_cd}">
											<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="${epcLoginVO.repVendorId}" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
										</c:if>
									</c:when>
								</c:choose>
							</td>							
							<th><span class="star">*</span> 팀</th>
							<td>
								<select id="teamGroupCode" name="teamCode" class="required" style="width:100px;">
									<option value="">선택</option>
									<c:forEach items="${teamList}" var="team" varStatus="index" >
										<option value="${team.teamCode}"	>${team.teamName}</option> 
									</c:forEach>
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
							
							<th><span class="star">*</span> 세분류</th>
							<td >
								<select id="l4GroupCode" name="l4Cd">
									<option value="">선택</option>
								</select>
							</td>													
						</tr>
					</table>
					<table  cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
						<tr>
							<td colspan="6" bgcolor=ffffff>
								&nbsp;※업체별로 상품 코드가 많을 경우 수십초의 시간이 걸릴 수 있습니다.								
							</td>
						</tr>
					</table>
					
					
					
					<div id="wrap_attrMenu">
						<div class="wrap_search">
							<div class="bbs_list">
								<ul class="tit">
									<li class="tit">속성 입력</li>		
									<li class="btn">
										<a href="#" class="btn" onclick="btnSaveProductGroupAttr();">일괄저장</a>
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
							 		
							 		<tr>						
										<th>
											<span id="sapL3CdCompboSpTxt"	style="color:red">소분류</span>
										</th>
										<td colspan="3">
											<select name="sapL3CdCompboList"	id="sapL3CdCompboList">
												<option value="">선택</option>
											</select>
										</td>																
									</tr>	
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
					
					<div style="width:100%; height:446px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
					<table  cellpadding="1" cellspacing="1" border="0" width=1330 bgcolor=efefef>
					<colgroup>
						<col width="50px"/>
						<col width="100px"/>
						<col width="100px"/>
						<col width="100px"/>
						<col width="100px"/>
						<col width="200px" />
						<col />																
					</colgroup>
					<tr bgcolor="#e4e4e4">
						<th><input type="checkBox"	name="chkAll"	id="chkAll"/></th>
						<th>No.</th>
						<th>등록일자</th>
						<th>판매(88)코드</th>
						<th>상품코드</th>
						<th>상품명</th>						
						<th>입력속성</th>
					</tr>
					
					<tbody id="dataListbody" />
					
					<%-- <c:if test="${fn:length(wholeProductList) > 0 }">
						<c:set var="row_cnt"  value="1" />
						<c:forEach items="${wholeProductList}" var="barcode" varStatus="index" >
							<tr class="r1"  bgcolor=ffffff> <!-- totalProductCount: ${totalProductCount} param.pageIndex : ${param.pageIndex}  -->
							<!-- searchParam.recordCountPerPage : ${searchParam.recordCountPerPage} -->
								<td align="center">
									<input type="checkBox"	name="chkAttId"		id="chkAttId"	value="Y"/>
								
									<input type="hidden"	name="sellCode"				id="sellCode"			value="<c:out value='${barcode.sellCode}'/>"/>
									<input type="hidden"	name="newProductCode"		id="newProductCode"		value="<c:out value='${barcode.newProductCode}'/>"/>								
								</td>
								<td align="center">${ totalProductCount - ( param.pageIndex -1 ) * paginationInfo.recordCountPerPage - index.index }</td>
								<td align="center">${barcode.regDate}</td>
								<td align="left">&nbsp;${barcode.sellCode }</td>
								<td align="center">${barcode.newProductCode}</td>
								<td><a href="#" onclick="goDetailView('${barcode.regDate}',	'${barcode.sellCode}',	'${barcode.newProductCode}',	'${barcode.l4Cd}', 	'${barcode.productName}', '${barcode.venCd}', '${barcode.sapL3Cd}');" 	id="goDetailLnk"	name="goDetailLnk">${barcode.productName}</a></td>									
							</tr>
							<c:set var="row_cnt" value="${row_cnt+1 }" />
						</c:forEach>
						<!-- c:forEach begin="${row_cnt}" end ="14" -->
					      <!-- tr class="r1"  bgcolor=ffffff>
						    <td>&nbsp;</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr -->	
					   <!-- /c:forEach -->
					</c:if> --%>
					<%-- <c:if test="${fn:length(wholeProductList) == 0 }">
						<tr class="r1"  bgcolor=ffffff><td colspan="15" align=center>Data가 없습니다.</td></tr>
					</c:if> --%>
					</table>
					</div>
					
					<%-- <div id="paging">
						<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="goPage" />
					</div> --%>										
					
				</div>
			</div>
		</div>
		</form>
		<iframe name="frameForDownload" style="width:0%;height:0%;"></iframe> 
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
		<input type="hidden"	name="sapL3Cd"				id="sapL3Cd"		/>
		<input type="hidden"	name="pageGbn"				id="pageGbn"		/>
		<input type="hidden"	name="teamCd"				id="teamCd"			/>
		<input type="hidden"	name="l1Cd"					id="l1Cd"			/>
		
		
		<!-- 문자열로 담아서 넘어갈 배열 -->
		<input type="hidden"	name="arrSrcmkCd"			id="arrSrcmkCd"		/>
		<input type="hidden"	name="arrProdCd"			id="arrProdCd"		/>		
	</form>
	
</div>
</body>
</html>
