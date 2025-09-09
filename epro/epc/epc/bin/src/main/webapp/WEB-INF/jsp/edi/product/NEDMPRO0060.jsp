<%--
	Page Name 	: NEDMPRO0060.jsp
	Description : 상품일괄등록 LIST 화면
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2015.11.27		SONG MIN KYO 	최초생성
--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<%@ include file="../common.jsp" %>
	<c:import url="/common/commonHead.do" />	
	<%@ include file="/common/scm/scmCommon.jsp" %>
	<%@ include file="./CommonProductFunction.jsp" %>

	<script type="text/javascript" >

		/* dom이 생성되면 ready method 실행 */
		$(document).ready(function() {

			//검색 시작, 종료일 (Parameter....)
			var srchStartDt = 	"<c:out value='${param.srchStartDt}'/>";
			var srchEndDt	=	"<c:out value='${param.srchEndDt}'/>";

			//검색 값이 없을경우 Default 값 설정
			if (srchStartDt.replace(/\s/gi, '') == "") {
				srchStartDt = "<c:out value='${srchStartDt}'/>";
				srchEndDt = "<c:out value='${srchEndDt}'/>";
			}

			//검색 기간 설정
			$("#searchForm input[name='srchStartDt']").val(srchStartDt);
			$("#searchForm input[name='srchEndDt']").val(srchEndDt);

			//----- 팀 변경시 이벤트
			$("#searchForm select[name=teamCd]").change(function() {
				//----- 대, 중, 소분류 초기화
				$("#l1Cd option").not("[value='']").remove();

				_eventSelectL1List($(this).val().replace(/\s/gi, ''));
			});

			initIBSheetGrid();
		});	

		/* 그리드초기화 */
		function initIBSheetGrid(){
			// START of IBSheet Setting
			createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "380px");
			mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
			mySheet.SetDataAutoTrim(false);

			var ibdata = {};
			// SizeMode: 
			ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
			// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
			ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

			// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시

			//20180904 상품키워드 입력 기능 추가
			/*
			ibdata.Cols = [
				{Header:"", 			Type:"CheckBox",	SaveName:"SELECTED", 	 		Align:"Center",   Width:25,    Sort:false}
			  , {Header:"순번",			Type:"Text", 		SaveName:"NUM", 				Align:"Center",   Width:40,   Edit:0}
			  , {Header:"등록일자",		Type:"Text", 		SaveName:"REG_DATE", 			Align:"Center",   Width:80,   Edit:0, Cursor:'pointer', Color:'blue'}
			  , {Header:"상품명",			Type:"Text", 		SaveName:"PROD_NM", 	 		Align:"Left",  		Width:280,   Edit:0, Ellipsis:true}
			  , {Header:"대분류",			Type:"Text", 		SaveName:"L1_NM", 	 			Align:"Center", 	Width:80,   Edit:0, Ellipsis:true}
			  , {Header:"옵션여부",		Type:"Text", 		SaveName:"OPT_YN", 	 			Align:"Center",  	Width:70,   Edit:0}
			  , {Header:"전상법 분류",		Type:"Text", 		SaveName:"NOR_PROD_INFO_GRP_CD", Align:"Center",  	Width:100,   Edit:0}
			  , {Header:"KC인증 분류",		Type:"Text",		SaveName:"KC_INFO_GRP_CD", 	 	Align:"Center", 	Width:100,   Edit:0}
			  , {Header:"원가",			Type:"Int", 		SaveName:"BUY_PRC", 	 		Align:"Right", 		Width:80,   Edit:0}
			  , {Header:"매가",			Type:"Int", 		SaveName:"SELL_PRC", 	 		Align:"Right", 		Width:80,   Edit:0}
			  , {Header:"이익률",			Type:"Text", 		SaveName:"PRFT_RATE", 	 		Align:"Center", 	Width:80,   Edit:0}
			  , {Header:"상세설명\n등록여부",	Type:"Text", 		SaveName:"DESC_YN", 	 		Align:"Center", 	Width:70,   Edit:0}
			  , {Header:"이미지\n등록여부",	Type:"Text", 		SaveName:"IMG_YN", 	 			Align:"Center", 	Width:70,   Edit:0}
			  , {Header:"전상법\n등록여부",	Type:"Text", 		SaveName:"NOR_PROD_YN", 	 	Align:"Center", 	Width:70,   Edit:0}
			  , {Header:"KC인증\n등록여부",	Type:"Text", 		SaveName:"KC_YN", 	 				Align:"Center", 	Width:70,   Edit:0}
			  , {Header:"신규상품코드",		Type:"Text", 		SaveName:"PGM_ID", 	 				Hidden:true}
			  , {Header:"대분류코드",		Type:"Text", 		SaveName:"L1_CD", 	 				Hidden:true}
			];
			*/
			ibdata.Cols = [
							{Header:"",					Type:"CheckBox",SaveName:"SELECTED", 	 			Align:"Center",		Width:25,	Sort:false}
						  , {Header:"순번",				Type:"Text", 	SaveName:"NUM", 					Align:"Center",		Width:40,   Edit:0}
						  , {Header:"등록일자",			Type:"Text", 	SaveName:"REG_DATE", 				Align:"Center",		Width:80,   Edit:0, Cursor:'pointer', Color:'blue'}
						  , {Header:"상품명",				Type:"Text", 	SaveName:"PROD_NM", 	 			Align:"Left",  		Width:280,	Edit:0, Ellipsis:true}
						  , {Header:"대분류",				Type:"Text", 	SaveName:"L1_NM", 	 				Align:"Center", 	Width:80,   Edit:0, Ellipsis:true}
						  , {Header:"옵션여부",			Type:"Text", 	SaveName:"OPT_YN", 	 				Align:"Center",  	Width:70,   Edit:0}
						  , {Header:"전상법 분류",			Type:"Text", 	SaveName:"NOR_PROD_INFO_GRP_CD", 	Align:"Center",  	Width:100,	Edit:0}
						  , {Header:"KC인증 분류",			Type:"Text",	SaveName:"KC_INFO_GRP_CD", 	 		Align:"Center", 	Width:100,	Edit:0}
						  , {Header:"원가",				Type:"Int", 	SaveName:"BUY_PRC", 	 			Align:"Right", 		Width:80,   Edit:0}
						  , {Header:"매가",				Type:"Int", 	SaveName:"SELL_PRC", 	 			Align:"Right", 		Width:80,   Edit:0}
						  , {Header:"이익률",				Type:"Text", 	SaveName:"PRFT_RATE", 	 			Align:"Center", 	Width:80,   Edit:0}
						  , {Header:"상세이미지\n등록여부",	Type:"Text", 	SaveName:"DESC_YN", 	 			Align:"Center", 	Width:70,   Edit:0}
						  , {Header:"대표이미지\n등록여부",	Type:"Text", 	SaveName:"IMG_YN", 	 				Align:"Center", 	Width:70,   Edit:0}
						  , {Header:"전상법\n등록여부",		Type:"Text", 	SaveName:"NOR_PROD_YN", 	 		Align:"Center", 	Width:70,   Edit:0}
						  , {Header:"KC인증\n등록여부",		Type:"Text", 	SaveName:"KC_YN", 	 				Align:"Center", 	Width:70,   Edit:0}
						  , {Header:"상품키워드\n등록여부",	Type:"Text", 	SaveName:"KEYWORD_YN", 	 			Align:"Center",  	Width:70,	Edit:0}
						  , {Header:"신규상품코드",			Type:"Text", 	SaveName:"PGM_ID", 	 									Hidden:true}
						  , {Header:"대분류코드",			Type:"Text", 	SaveName:"L1_CD", 	 								Hidden:true}
						  , {Header:"임직원몰판매가능여부",	Type:"Text", 	SaveName:"STAFF_MALL_SELL_PSBT_YN",	Align:"Center",  	Width:70,	Edit:0}
			];
			//20180904 상품키워드 입력 기능 추가

			IBS_InitSheet(mySheet, ibdata);

			mySheet.SetFrozenCol(1);
			mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
			mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
		}

		/** ********************************************************
		 * 조회 처리 함수
		 ******************************************************** */
		function doSearch() {
			goPage('1');
		}

		/* 조회 버튼 이벤트 */
		function goPage(currentPage){
			var paramInfo	=	{};
			var vendorTypeCd	=	"<c:out value='epcLoginVO.vendorTypeCd'/>";

			if (vendorTypeCd == "06") {
				if ($("#entp_cd").val().replace(/\s/gi, '') == "") {
					alert("<spring:message code='msg.product.common.comp'/>");
					$("#entp_cd").focus();
					return;
				}
			}

			// 날짜 체크
			var srchStartDt = $("#searchForm input[name='srchStartDt']").val().replace(/\s/gi, '');
			var srchEndDt = $("#searchForm input[name='srchEndDt']").val().replace(/\s/gi, '');

			if (srchStartDt == "" || srchEndDt == "") {
				alert("<spring:message code='msg.common.fail.nocalendar'/>");
				$("#searchForm input[name='srchStartDt']").focus();
				return;
			}

			if (parseInt(srchStartDt.replaceAll("-", "")) > parseInt(srchEndDt.replaceAll("-", ""))) {
				alert("<spring:message code='msg.common.fail.calendar'/>");
				$("#searchForm input[name='srchStartDt']").focus();
				return;
			}

			var url = '<c:url value="/edi/product/imsiProductSearch.do"/>';

			loadIBSheetData(mySheet, url, currentPage, '#searchForm', null);
		}

		/* 이 펑션은 협력업체 검색창에서 호출하는 펑션임    */
		function setVendorInto(strVendorId, strVendorNm, strCono) { 
			$("#entp_cd").val(strVendorId);
		}

		/* 선택상품 삭제 */
		function _eventDoDelete() {
			if (!confirm("<spring:message code='msg.common.confirm.delete'/>")) {
				return;
			}

			var paramInfo = {};
			var arrPgmId = new Array();
			var arrOnOffGubun = new Array();
			var iCnt = mySheet.RowCount() + 1;
			var idx  = 0;

			for(var i = 1 ; i < iCnt; i++) {
				var selected = mySheet.GetCellValue(i, "SELECTED");

				if(selected){
					arrPgmId[idx] = mySheet.GetCellValue(i, "PGM_ID");
					arrOnOffGubun[idx] = "1";
					idx++;
				}
			}

			paramInfo["arrPgmId"] = arrPgmId; //상품코드
			paramInfo["arrOnOffGubun"] = arrOnOffGubun; //상품의 온오프 구분

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/deleteImsiProductList.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					if (data.msg == "SUCCESS") {
						alert("<spring:message code='msg.common.success.delete'/>"); //성공
					} else {
						alert("<spring:message code='msg.common.fail.delete'/>"); //실패
					}

					doSearch();
				}
			});
		}

		/* excel */
		function _eventExcel() {
			var tbody1 = $('#dataTable tbody');

			var form = document.hiddenForm;

			var date = $("#searchForm input[name='srchStartDt']").val().replace(/\s/gi, '') + "~" + $("#searchForm input[name='srchEndDt']").val().replace(/\s/gi, '');
			var productDivnName = $("select[name=srchProductDivnCode] option:selected").text();
			var selectedVendor = $("#entp_cd option:selected").text();

			var srchTitle = "<CAPTION>임시보관함<br>";
				srchTitle += "[조회기간 : "+date+"] [상품 구분: "+productDivnName+"] [협력업체 : "+selectedVendor+"]<br>";
				srchTitle += "</CAPTION>"+tbody1.parent().html();

			//console.log(srchTitle);

			$("#hiddenForm input[name='staticTableBodyValue']").val(srchTitle);
			$("#hiddenForm input[name='name']").val("temp");

			$("#hiddenForm").attr("target", "_blank");
			$("#hiddenForm").attr("action", "<c:url value='/edi/comm/NEDPCOM0030.do'/>");
			$("#hiddenForm").attr("method", "post").submit();

			$("#hiddenForm").attr("action", "");
			$("#hiddenForm").attr("target", "_self");
		}

		/* 상품 확정 */
		function _eventDoFix() {
			var paramInfo = {};
			var arrPgmId = new Array(); //상품코드
			var arrOnOffGubun = new Array(); //상품의 온오프 구분
			var selChk = false;
			var iCnt = mySheet.RowCount() + 1;
			var idx  = 	0;

			//20180904 상품키워드 입력 기능 추가
			var arrStaffSellYn = new Array();
			//20180904 상품키워드 입력 기능 추가

			for(var i=1 ; i<iCnt; i++) {
				var selected = mySheet.GetCellValue(i, "SELECTED");

				if(selected){
					var prodNm = mySheet.GetCellValue(i, "PROD_NM"); // 상품명
					var norProdYn = mySheet.GetCellValue(i, "NOR_PROD_YN"); // 전상법 등록 여부
					var kcYn = mySheet.GetCellValue(i, "KC_YN"); // KC인증 등록 여부
					var imgYn = mySheet.GetCellValue(i, "IMG_YN"); // 이미지 등록 여부
					var descYn = mySheet.GetCellValue(i, "DESC_YN"); // 추가설명 등록 여부

					//20180904 상품키워드 입력 기능 추가
					var keywordYn = mySheet.GetCellValue(i, "KEYWORD_YN"); //상품키워드 등록 여부

					arrStaffSellYn[idx] = mySheet.GetCellValue(i, "STAFF_MALL_SELL_PSBT_YN");
					//20180904 상품키워드 입력 기능 추가

					arrPgmId[idx] = mySheet.GetCellValue(i, "PGM_ID");
					arrOnOffGubun[idx] = "1";

					if(norProdYn == "N"){
						alert("선택된 확정 자료 상품중 [No:"+i+" 상품명:" + prodNm + "]  전상법정보를 등록해 주세요. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
						return;
					}

					if(kcYn == "N"){
						alert("선택된 확정 자료 상품중 [No:"+i+" 상품명:" + prodNm + "]  KC인증정보를 등록해 주세요. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
						return;
					}

					if(imgYn == "N"){
						alert("선택된 확정 자료 상품중 [No:"+i+" 상품명:" + prodNm + "]  온라인이미지를 3개 이상 등록해 주세요. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
						return;
					}

					if(descYn == "N"){
						alert("선택된 확정 자료 상품중 [No:"+i+" 상품명:" + prodNm + "]  추가설명을 등록해 주세요. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
						return;
					}

					//20180904 상품키워드 입력 기능 추가
					if(keywordYn == "N") {
						alert("선택된 확정 자료 상품중 [No:"+i+" 상품명:" + prodNm + "]  상품키워드를 등록해 주세요. \n등록일자를 클릭하신 다음 상세보기에서 등록하세요!");
						return;
					}
					//20180904 상품키워드 입력 기능 추가

					selChk = true;
					idx++;
				}
			}

			if(!selChk) {
				alert("<spring:message code='msg.order.fix'/>");
				return;
			}

			paramInfo["arrPgmId"] = arrPgmId; // 상품코드
			paramInfo["arrOnOffGubun"] = arrOnOffGubun;	//상품의 온오프 구분

			//20180904 상품키워드 입력 기능 추가
			paramInfo["arrStaffSellYn"] = arrStaffSellYn;
			//20180904 상품키워드 입력 기능 추가
			
			if (!confirm("확정요청 하시겠습니까?")) {
				return;
			}

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/fixImsiProductList.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					if (data.msg == "SUCCESS") {
						alert("<spring:message code='msg.common.success.request'/>");
					} else {
						alert("<spring:message code='msg.common.fail.request'/>"); //실패	
					}

					doSearch();
				}
			});
		}

		//상품 상세보기
		function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
			if (mySheet.ColSaveName(Col) == 'REG_DATE') {
				var url = "<c:url value='/edi/product/NEDMPRO0030OnlineDetail.do'/>";

				$("#hiddenForm input[name='pgmId']").val(mySheet.GetCellValue(Row, 'PGM_ID'));
				$("#hiddenForm").attr("target", "_self");
				$("#hiddenForm").attr("action", url);
				$("#hiddenForm").attr("method", "post").submit();
			}
		}

		/**********************************************************
		 * 일괄업로드용 양식 다운
		 **********************************************************/
		function exeExcelFile(){
			var form = document.excelForm;
			
			var chkVal = $("#excelOpt option:selected").val();
			var chkTxt = $("#excelOpt option:selected").text();
			var fileName = chkTxt+"_일괄업로드";
			var headerNm = "";

			if(chkVal != "01" && chkVal != "02"){
				var iCnt = mySheet.RowCount() + 1;
				var pgmIdVal = "";

				for(var i=1 ; i<iCnt; i++) {
					var selected = mySheet.GetCellValue(i, "SELECTED");

					if(selected){
						pgmIdVal += ","+mySheet.GetCellValue(i, "PGM_ID");
					}
				}

				if(pgmIdVal == ""){
					alert("다운받을 상품을 선택 하세요.");
					return;
				}else{
					pgmIdVal = pgmIdVal.substring(1,pgmIdVal.length);
				}

				$("#pgmIdVal").val(pgmIdVal);
			}

			if (!confirm('엑셀 양식을 다운로드 하시겠습니까?')) {
				$("#pgmIdVal").val("");
				return;
			}

			$("#fileName").val(encodeURIComponent(fileName));
			$("#optionVal").val(chkVal);

			form.target = "frameForExcel";
			form.action = '<c:url value="/edi/product/batchProductExcelDown.do"/>';
			form.submit();
		}

		function uploadPop(){
			var targetUrl = '<c:url value="/edi/product/NEDMPRO0061.do"/>';
			var heightVal = 350;
			var chkVal = $("#excelOpt option:selected").val();

			if(chkVal == "05" || chkVal == "06"){
				targetUrl = '<c:url value="/edi/product/NEDMPRO0062.do"/>';
				heightVal = 680;
			}	

			if($("#entp_cd").val() == ""){
				alert("업체를 선택 하세요.");
				$("#entp_cd").focus();
				return;
			}

			Common.centerPopupWindow(targetUrl+"?optionVal="+chkVal+"&entpCd="+$("#entp_cd").val(), 'batchProdPop', {width : 730, height : heightVal});	
		}
	</script>
</head>
<body>
	<div id="content_wrap">
		<div>
			<form name="searchForm" id="searchForm">
				<div id="wrap_menu">
					<div class="wrap_search">
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit">검색조건</li>
								<li class="btn">
									<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
									<a href="#" class="btn" onclick="_eventExcel();"><span><spring:message code="button.common.excel"/></span></a>
									<a href="#" class="btn" onclick="_eventDoFix();"><span><spring:message code="button.common.confirmation"/></span></a>
									<a href="#" class="btn" onclick="_eventDoDelete();"><span><spring:message code="button.common.delete"/></span></a>
								</li>
							</ul>

							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col style="width:15%" />
									<col style="width:31%" />
									<col style="width:15%" />
									<col style="*" />
								</colgroup>
								<tr>
									<th>상품구분 </th>
									<td>
										<select name="srchProductDivnCode" id="srchProductDivnCode">
											<option value="">전체</option>
											<option value="1">규격</option>
											<option value="5">패션</option>
										</select>
									</td>
									<th>협력업체 코드</th>
									<td>
										<c:choose>
												<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
													<c:if test="${not empty param.entp_cd}">
													<input type="text" name="entp_cd" id="entp_cd" readonly="readonly" readonly="readonly" value="<c:out escapeXml='false' value='${param.entp_cd}'/>" style="width:40%;"/>
													</c:if>
													<c:if test="${empty param.entp_cd}">
													<input type="text" name="entp_cd" id="entp_cd" readonly="readonly" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
													</c:if>
													<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
												</c:when>

												<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
													<c:if test="${not empty param.entp_cd}">
														<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="<c:out escapeXml='false' value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" formName="form"  />
													</c:if>
													<c:if test="${ empty param.entp_cd}">
														<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="${epcLoginVO.repVendorId}" dataType="CP" comType="SELECT" formName="form"  />
													</c:if>
												</c:when>
										</c:choose>
									</td>
								</tr>
								<tr>
									<th><span class="star">*</span> 조회기간 </th>
									<td>
										<input type="text" class="day" name="srchStartDt" id="srchStartDt" style="width:80px;"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDt');" style="cursor:hand;" />
										~
										<input type="text" class="day" name="srchEndDt" id="srchEndDt" style="width:80px;"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDt');"  style="cursor:hand;" />
									</td>
									<th>대분류</th>
									<td>
										<select id="teamCd" name="teamCd" style="width:140px;">
											<option value="">전체</option>
											<c:forEach items="${teamList}" var="teamList" varStatus="index" >
												<option value="${teamList.teamCd}">${teamList.teamNm}</option> 
											</c:forEach>
										</select>
										<select id="l1Cd" name="l1Cd" style="width:140px;">
										</select>
									</td>
								</tr>
							</table>

							<table  cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
								<tr>
									<td colspan="4" bgcolor=ffffff>
										<!-- 20180904 상품키워드 입력 기능 추가 -->
										<!--
										<strong>&nbsp;<font color="red">※ 상품일괄 등록 설명</font></strong><br/>
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;1. 기본정보 등록 양식(상품등록(옵션)) 먼저 다운 후 등록</font></strong><br/>
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;2. 기본정보 조회 체크박스 선택 후 나머지 항목 양식 다운(전상법,KC인증,상품상세,상품이미지)</font></strong><br/>
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;3. 상품등록(옵션有) 양식 단품정보 등록시 기본정보의 기준순번에 한에 적용 됨</font></strong><br/>
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;4. 이미지등록(상품상세,상품이미지) 시 적용할 이미지 파일은 zip 압축으로 업로드</font></strong><br/>
										-->
										<strong>&nbsp;<font color="red">※ 상품일괄 등록 설명</font></strong><br/>
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;1. 기본정보 등록 양식(상품등록(옵션)) 먼저 다운 후 등록</font></strong><br/>
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;2. 상품키워드 등록시 기본정보의 기준순번에 한에 적용 됨</font></strong><br/>
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;3. 기본정보 1개당 상품키워드 3개 이상 필수 입력</font></strong><br/>	<!-- 20190326 추가  -->
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;4. 기본정보 조회 체크박스 선택 후 나머지 항목 양식 다운(전상법,KC인증,상세이미지,대표이미지)</font></strong><br/>
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;5. 상품등록(옵션有) 양식 단품정보 등록시 기본정보의 기준순번에 한에 적용 됨</font></strong><br/>
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;6-1. 상품등록(옵션無) 양식 단품속성 등록시 기본정보의 기준순번에 한에 적용 됨</font></strong><br/>
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;6-2. 상품등록(옵션有) 양식 단품속성 등록시 기본정보의 기준순번&단품정보의 단품코드에 한에 적용 됨</font></strong><br/>
										<strong>&nbsp;<font color="red">&nbsp;&nbsp;&nbsp;7. 이미지등록(상세이미지,대표이미지) 시 적용할 이미지 파일은 zip 압축으로 업로드</font></strong><br/>
										<!-- 20180904 상품키워드 입력 기능 추가 -->
									</td>
								</tr>
							</table>
						</div>
					</div>

					<div class="wrap_con">
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">검색결과</li>
								<li class="btn">
									<select id="excelOpt" name="excelOpt">
											<option value="01" selected="selected">상품등록(옵션無)</option>
											<option value="02" >상품등록(옵션有)</option>
											<option value="03" >전상법</option>
											<option value="04" >KC인증</option>
											<option value="05" >상세이미지</option>
											<option value="06" >대표이미지</option>
										</select>
										<a href="#" class="btn" onclick="exeExcelFile();"><span>양식다운</span></a>
									<a href="#" class="btn" onclick="uploadPop();"><span>일괄등록</span></a>
				                </li>
							</ul>

							<table id="dataTable" class="bbs_list" cellpadding="0" cellspacing="0" border="0">
								<tr>
				 					<td><div id="ibsheet1"></div></td>
								</tr>
							</table>
						</div>
					</div>

					<!-- 페이징 DIV -->
					<div id="pagingDiv" class="pagingbox1" style="width: 100%;">
						<script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script>
					</div>

				</div>
			</form>
		</div>
	</div>

	<!-- hiddenForm ------------------------------------------------------>
	<form name="hiddenForm" id="hiddenForm">
		<input type="hidden" name="pgmId" id="pgmId" />
		<input type="hidden" name="staticTableBodyValue" id="staticTableBodyValue" />
		<input type="hidden" name="name" id="name" />
		<input type="hidden" name="mode" id="mode" value="modify" />
		<input type="hidden" name="onoffDivnCd" id="onoffDivnCd" />
		<input type="hidden" name="cfgFg" id="cfgFg" value="I" /><!-- 물류바코드 등록/수정 구분자 -->
		<input type="hidden" name="logiCfmFg" id="logiCfmFg" /><!-- 물류바코드 등록 상태[00:등록, 02:심사, 03:반려, 09:완료] -->
		<input type="hidden" name="gbn" id="gbn" value="" /><!-- 임시보관함['']에서 등록하는지 신상품등록현황[99]에서 등록하는지 구분자 -->
		<input type="hidden" name="venCd" id="venCd" /><!-- 물류바코드 등록 팝업화면에 parameter로 넘기기 위해 사용 -->
		<input type="hidden" name="l1Cd" id="l1Cd" /><!-- 물류바코드 등록 팝업화면에 parameter로 넘기기 위해 사용 -->
	</form>

	<form name="excelForm" id="excelForm">
		<iframe name="frameForExcel" style="display:none;"></iframe>
		<input type="hidden" name="fileName" id="fileName"/>
		<input type="hidden" name="optionVal" id="optionVal"/>
		<input type="hidden" name="pgmIdVal" id="pgmIdVal"/>
	</form>

</body>
</html>
