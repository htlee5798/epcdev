<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"      %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"   %>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"            %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"      %>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>

<% 
	String prodCd 	   = SecureUtil.stripXSS(request.getParameter("prodCd"));
	String vendorId    = SecureUtil.stripXSS((String)request.getParameter("vendorId"));
	String prdDivnType = SecureUtil.stripXSS((String)request.getAttribute("prdDivnType"));
	String tabNo = "2";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script language="javascript" type="text/javascript">
	$(document).ready(function(){
		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "320px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		
		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"순번", 		Type:"Int", 		SaveName:"num", 			Align:"Center", Width:30,   Height:18, Edit:0}
		  , {Header:"", 			Type:"CheckBox",	SaveName:"CHK", 			Align:"Center", Width:28,   Sort:false} // default=1(수정가능)
		  , {Header:"점포코드", 	Type:"Text", 		SaveName:"strCd", 			Align:"Center", Width:50,  	Edit:0} // 수정불가
		  , {Header:"점포명", 		Type:"Text", 		SaveName:"strNm", 			Align:"Center", Width:80,	Edit:0} // 수정가능
		  , {Header:"단품코드", 	Type:"Text", 		SaveName:"itemCd", 			Align:"Center", Width:50,	Edit:0}
		  , {Header:"발주구분", 	Type:"Text", 		SaveName:"purStopDivnCd",	Align:"Center", Width:50,	Edit:0}
		  , {Header:"가능재고", 	Type:"Int", 		SaveName:"avilStockQty", 	Align:"Center", Width:50,	Edit:0}
		  , {Header:"실재고", 		Type:"Text", 		SaveName:"stockQty", 		Align:"Center", Width:50,	Edit:0}
		  , {Header:"수동품절", 	Type:"CheckBox",	SaveName:"onlineSoutYn", 	Align:"Center", Width:65,	Edit:1}
		  , {Header:"MD품절", 		Type:"Text", 		SaveName:"mdSoutYn", 		Align:"Center", Width:60,	Hidden:true}
		  , {Header:"품절여부", 	Type:"Text", 		SaveName:"soutYn", 			Align:"Center", Width:60,	Edit:0}
		  , {Header:"전시여부", 	Type:"CheckBox",	SaveName:"dispYn", 			Align:"Center", Width:67,	Edit:1}
		  , {Header:"사용여부", 	Type:"Text", 		SaveName:"sellPsbtYn", 		Align:"Center", Width:60,	Edit:0}
		  , {Header:"재고관리", 	Type:"CheckBox",	SaveName:"stkMgrYn", 		Align:"Center", Width:68,	Edit:1}
		  , {Header:"거래형태", 	Type:"Text", 		SaveName:"trdTypeDivnCd", 	Align:"Center", Width:60,	Edit:0}
		  , {Header:"해외배송여부", Type:"CheckBox",	SaveName:"forgnDeliYn", 	Align:"Center", Width:80,	Edit:1}
		  , {Header:"점포픽업여부", Type:"CheckBox",	SaveName:"strPikupYn", 		Align:"Center", Width:90, 	Edit:1}
		  , {Header:"배송옵션", 	Type:"Combo",		SaveName:"deliOptnCd", 		Align:"Center", Width:85, 	Edit:1}
		  , {Header:"전시적용기준일",Type:"Int", 		SaveName:"dispApplyBaseDd", Align:"Center", Width:85, 	Edit:1, EditLen:3}
		  , {Header:"MD최근판매일", Type:"Text", 		SaveName:"mdRecentSellDy", 	Align:"Center", Width:80, 	Edit:1}
		  , {Header:"원가", 		Type:"Int", 		SaveName:"buyPrc", 	 	Hidden:true}
		<% 	if ("03".equals(prdDivnType)) { // 소셜상품인 경우에 매가, 판매가를 수정할 수 있다. %>
		  , {Header:"매가", 		Type:"Int", 	SaveName:"sellPrc", 	Align:"Right", Width:50, Edit:1}
		  , {Header:"판매가", 		Type:"Int", 	SaveName:"currSellPrc", Align:"Right", Width:50, Edit:1}
		<% 	} else { // 수정불가 %>
		  , {Header:"매가", 		Type:"Int", 	SaveName:"sellPrc", 	Align:"Right", 	Width:50, Edit:0}
		  , {Header:"판매가", 		Type:"Int", 	SaveName:"currSellPrc", Align:"Right", Width:50, Edit:0}
		<%	} %>	    
		  , {Header:"인터넷상품코드", 		Type:"Text", 	SaveName:"prodCd", 	Hidden:true}
		];
		
		IBS_InitSheet(mySheet, ibdata);
			
//		mySheet.SetExtendLastCol(1);
//		mySheet.SetRowHidden(0, 1); // 0번째(헤더) row를 숨긴다.
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
//		mySheet.SetFocusAfterProcess(0);
		mySheet.SetComboOpenMode(1); // 원클릭으로 ComboBox Open
		mySheet.FitColWidth(); // 화면에 딱 맞게 조정.
		
		//input enter 막기
		$("*").keypress(function(e){
	        if(e.keyCode==13) return false;
		});
		//점포 초기 선택값
		$("#strGubun").val("01");
		chStrGubun("01");
	});

	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		goPage('1');
	}

	// 셀값 변경 시..
	function mySheet_OnChange(Row, Col, Value, OldValue) {
		if (Row == 0) return;
		if (Col < 2) return;

		if (Value != OldValue) {
			mySheet.SetCellValue(Row, "CHK", true);
			mySheet.SetCellBackColor(Row, Col, '#F6E5E2');
		}
	}
	
	function goPage(currentPage) {

		setStrCd();

		var url = '<c:url value="/product/prdPriceSearch.do"/>';

		var param = new Object();
		param.strGubun = $('#strGubun').val();
		param.prodCd = '<%= prodCd %>';
		param.strCd = $('#strCd').val();
		
		if ($('#stockCheck').is(':checked')) {
			param.stockYn = 'Y';
		} else {
			param.stockYn = 'N';
		}
		
		if ($('#avilStockCheck').is(':checked')) {
			param.avilStockYn = 'Y';
		} else {
			param.avilStockYn = 'N';
		}
		param.mode = 'search';
			
		loadIBSheetData(mySheet, url, currentPage, null, param);
	}
	
	// 데이터 읽은 직후 이벤트
	function mySheet_OnSearchEnd() {
		//
		var codeArr = new Array();
		var nameArr = new Array();
		<c:forEach items="${commonCodeList}" var="codeInfo" begin="0" varStatus="stat">
			codeArr.push('${codeInfo.minorCd}');
			nameArr.push('${codeInfo.cdNm}');
		</c:forEach>
		
		var cInfo = {ComboCode:codeArr.join("|"), ComboText:nameArr.join("|")};
	 	var rowCount = mySheet.RowCount() + 1;
		for (var i=1; i<rowCount; i++) {
			mySheet.CellComboItem(i, 'deliOptnCd', cInfo);
		}
	}
	
	//리스트 수정
	function doUpdate(){
		if(!checkUpdateData()) {
			return;
		}

		if( confirm("선택된 가격정보를 수정 하시겠습니까?") ){
			var param = new Object();
			param.mode = "update";
			param.prdDivnType = '${prdDivnType}';
			param.mProdCd = '<%= prodCd %>';
			param.vendorId = '<%= vendorId %>';
			param.logDate = $('#logDate').val();
			
		    var sUrl = '<c:url value="/product/prdPriceListUpdate.do"/>';

		    mySheet.DoSave(sUrl, {Param:$.param(param), Col:1, Quest:false, Sync:2});
		}
	}

	//Code: 서버에서 저장한 코드, Msg: 서버에서 저장한 메세지, StCode: Http통신코드(200=정상), StMsg:HTTP메세지(OK)
	function mySheet_OnSaveEnd(Code, Msg, StCode, StMsg) {
		alert(Msg);
		if (Code == 1) doSearch();
	}
	
	// 저장 전에 값들 체크
	function checkUpdateData() {

		var chkIndex = 0;
		var cntDeliOptnCd = 0;
		var chkSpecStr = "";
		var logChar = "";   //로그를 담아두기 위한 변수 
		$('#logDate').val(''); //로그를 위한 변수
		var strGubun = $("#strGubun").val();
		
	 	var rowCount = mySheet.RowCount() + 1;
		for (var i=1; i<rowCount; i++) {
			
			if (mySheet.GetCellValue(i, "forgnDeliYn") == "1") {//해외배송가능일 경우, 배송옵션이 일반점포가 아니면 에러임
				if (mySheet.GetCellValue(i, "deliOptnCd") != '0001') {
					alert("해외배송가능인 경우에, 배송옵션은 '일반매장'으로 설정하셔야 합니다.");
					return false;
				}
			}
			
			if (mySheet.GetCellValue(i, "deliOptnCd") == '0003') {
				if (strGubun != '') {
					alert("'특정점'을 설정하려면 점포를 전체로 조회해야 합니다.");
		
					$('#strGubun').val('');
					goPage('1');
					return false;
				}
			
				if (chkSpecStr == "") {
					chkSpecStr = mySheet.GetCellValue(i, "strCd");
					cntDeliOptnCd = 1;
				} else {
					if (chkSpecStr != mySheet.GetCellValue(i, "strCd")) {
						alert("배송옵션의 '특정점'은 한개 까지만 선택이 가능합니다.");
						return false;
					}
				}
			}
			
			if (mySheet.GetCellValue(i, "CHK") == 1) {
				var chkIndex = i + 1;

			<%  if ("03".equals(prdDivnType)) {//소셜상품인 경우에 판매가를 수정할 수 있다.%>
					if (Common.isEmpty($.trim(mySheet.GetCellValue(i, "sellPrc")))) {
						alert(chkIndex + "번째 줄 매가가 입력되지 않았습니다.");
						return false;
					}
					if (!byteCheck2(chkIndex + '번째 줄 매가', mySheet.GetCellValue(i, "sellPrc"), 10)) {
						return false;
					}
					if (Common.isEmpty($.trim(mySheet.GetCellValue(i, "currSellPrc")))) {
						alert(chkIndex + "번째 줄 판매가가 입력되지 않았습니다.");
						return false;
					}
					if (!byteCheck2(chkIndex + '번째 줄 판매가',mySheet.GetCellValue(i, "currSellPrc"), 10)) {
						return false;
					}
			<%  } %>
				
				if (Common.isEmpty($.trim(mySheet.GetCellValue(i, "deliOptnCd")))) {
					alert(chkIndex + "번째 줄 배송옵션이 선택되지 않았습니다.");
					return false;
				}

				if (Common.isEmpty($.trim(mySheet.GetCellValue(i, "dispApplyBaseDd")))) {
					alert(chkIndex + "번째 줄 전시적용기준일이 입력되지 않았습니다.");
					return false;
				}
				if (!byteCheck2(chkIndex + '번째 줄 전시적용기준일', mySheet.GetCellValue(i, "dispApplyBaseDd"), 3)) {
					return false;
				}

				if (Common.isEmpty($.trim(mySheet.GetCellValue(i, "mdRecentSellDy")))) {
					alert(chkIndex + "번째 줄 MD최근판매일이 입력되지 않았습니다.");
					return false;
				}
				if (!byteCheck2(chkIndex + '번째 줄 MD최근판매일', mySheet.GetCellValue(i, "mdRecentSellDy"), 8)) {
					return false;
				}
			}
		}
		
		// 데이터 문제에 대한 현업의 요청. 로그를 위한 변수임
		for (var i=1; i<rowCount; i++) {                                      
			if (mySheet.GetCellValue(i, "CHK") == 1) {
				logChar += "[" + mySheet.GetCellValue(i, "strCd") + "/" +  mySheet.GetCellValue(i, "onlineSoutYn") + "/" +  mySheet.GetCellValue(i, "dispYn") + "/" +  mySheet.GetCellValue(i, "stkMgrYn") + "]";
			}
		}
		$('#logDate').val(logChar);
		
		return true;
	}

	function setStrCd() {
		if(!$("#strGubun").val()) {
			$("#strCd").val($("#strCd00").val());
		} else if($("#strGubun").val()=="01") {
			$("#strCd").val($("#strCd01").val());
		} else if($("#strGubun").val()=="02") {
			$("#strCd").val($("#strCd02").val());
		}
	}

	// 점포분류 콤보값 변경시
	function chStrGubun(v) {
		$("#strCd00").hide();
		$("#strCd00").val("");
		$("#strCd01").hide();
		$("#strCd01").val("");
		$("#strCd02").hide();
		$("#strCd02").val("");

		if(v == '') {
			$("#strCd00").show();
		} else if(v == '01') {
			$("#strCd01").show();
		} else if(v == '02') {
			$("#strCd02").show();
		}
	}
	
	function onlyNumber() {
		if ((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105) || event.keyCode == 8){
			
		}else{
			alert("숫자만 입력해 주세요");			
			}
		event.returnValue=false;
	}

	// 일괄적용
	function setDispApplyBaseDd() {

		if (confirm("입력된 전시적용기준일을 일괄적용 하시겠습니까?")) {
			
			//값 트림, 크기 채크(바이트)
			$('#txtDispApplyBaseDd').val($.trim($('#txtDispApplyBaseDd').val()));
			if (!byteCheck('전시적용기준일 일괄적용 값',$('#txtDispApplyBaseDd').val(),3,$('#txtDispApplyBaseDd'))) {
				return;
			}
			//값 입력 유무 채크
			if (Common.isEmpty($.trim($('#txtDispApplyBaseDd').val()))) {
				alert('<spring:message code="msg.common.error.required" arguments="전시적용기준일 일괄적용 값"/>');
				$('#txtDispApplyBaseDd').focus();
				return;
			}			
			
			var dispApplyBaseDd = $("#txtDispApplyBaseDd").val();		
		 	var rowCount = mySheet.RowCount() + 1;
			for (var i=1; i<rowCount; i++) {
				mySheet.SetCellValue(i, "dispApplyBaseDd", dispApplyBaseDd);
			}
		}
	}
</script>
</head>

<body>

<form name="dataForm" id="dataForm">
	<input type="hidden" id="strCd"       name="strCd" />
	<input type="hidden" id="prdDivnType" name="prdDivnType" value="<%=prdDivnType%>"/>
	<input type="hidden" id="logDate" name="">
<div id="content_wrap"   style="width:990px; height:200px;">

	<div id="wrap_menu" style="width:990px;">
		<!--	@ 검색조건	-->

		<!-- 상품 상세보기 하단 탭 -->
		<c:set var="tabNo" value="<%=tabNo%>" />      
		<c:import url="/common/productDetailTab.do" charEncoding="euc-kr" >
			<c:param name="tabNo" value="${tabNo}" />
			<c:param name="prodCd" value="<%=prodCd%>" />
		</c:import>
		
		<div class="wrap_con">
			<!-- list -->
			<div class="bbs_list">
				<ul class="tit">
					<li class="tit" style="padding:3px 0 0 13px;">가격정보&nbsp;&nbsp;
			
					<select name="strGubun" id="strGubun" onchange="chStrGubun(this.value)">
						<option value="">전체</option>
						<option value="01">온라인점포</option>
						<option value="02">명절점포</option>
					</select>

					<select name="strCd00" id="strCd00">
						<option value="">전체</option>
					</select>
					<select name="strCd01" id="strCd01" style="display:none">
						<option value="">전체</option>
							<c:forEach items="${storeList}" var="code" begin="0">
								<c:if test="${code.strGubun == '01'}" >
									<option value="${code.strCd }">${code.strNm }</option>
								</c:if>
							</c:forEach>
					</select>
					<select name="strCd02" id="strCd02" style="display:none">
						<option value="">전체</option>
							<c:forEach items="${storeList}" var="code" begin="0">
								<c:if test="${code.strGubun == '02'}" >
									<option value="${code.strCd }">${code.strNm }</option>
								</c:if>
							</c:forEach>
					</select>
					&nbsp;&nbsp; &nbsp;
					가능재고&nbsp;:&nbsp;0
					<input type="checkbox" name="avilStockCheck" id="avilStockCheck"/>
					&nbsp;&nbsp; &nbsp;
					실재고&nbsp;:&nbsp;0
					<input type="checkbox" name="stockCheck" id="stockCheck"/>
					&nbsp;&nbsp; &nbsp;
					전시적용기준일
					<input class="text" type="text" name="txtDispApplyBaseDd" id="txtDispApplyBaseDd" onkeyup="onlyNumber()" style="ime-mode:disabled;width:30px">
					</li>
					<li class="btn">
						<a href="javascript:setDispApplyBaseDd();" class="btn" ><span>일괄적용</span></a>
						<a href="javascript:doSearch();" class="btn" ><span>조회</span></a>
						<a href="javascript:doUpdate();" class="btn" ><span>저장</span></a>
					</li>
				</ul>
	
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td><div id="ibsheet1"></div></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>	
</form>

<form name="form1" id="form1">
	<input type="hidden" id="prodCd"   name="prodCd"   value="<%=prodCd%>"  />
	<input type="hidden" id="vendorId" name="vendorId" value="<%=vendorId%>"/>
</form>

</body>
</html>