<%--
- Author(s): lth
- Created Date: 2016. 05. 17
- Version : 1.0
- Description : 주문목록 조회(상품별)
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="lcn.module.common.util.DateUtil"%>
<%
    String excelToDate  =DateUtil.formatDate(DateUtil.getToday(),"-");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<%@ include file="/common/scm/scmCommon.jsp" %>
<!-- order/PSCMORD0004 -->
<script type="text/javascript">
var excelConf;

	/** ********************************************************
	 * jQeury 초기화
	 ******************************************************** */
	$(document).ready(function() {
		$('#search').click(function() {
			doSearch();
		});

		$('#clear').click(function() {
			clearVendorInfo();
		});

		$('#excel').click(function() {
			doExcel();
		});

		$('#startDate').click(function() {
			openCal('searchForm.startDate');
		});
		$('#endDate').click(function() {
			openCal('searchForm.endDate');
		});

		$('#normalBtn').click(function() {
			var frm = document.searchForm;
			frm.strCd.value = '981';
			//alert(frm.strCd.value);
		});
		$('#holiBtn').click(function() {
			var frm = document.searchForm;
			frm.strCd.value = '985';
			alert("명절몰은 전용센터점으로 주문이 나옵니다.");
			//alert(frm.strCd.value);
		});

		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "320px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);

		var ibdata = {};
		// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
		    {Header:"주문번호",		Type:"Text" ,			SaveName:"ORDER_ID", 		        	Align:"Center", Width:100, Edit:0}
		  , {Header:"원주문번호", 		Type:"Text", 			SaveName:"UP_ORDER_ID", 	    		Align:"Center", Width:100, Edit:0}
		  , {Header:"EC주문번호", 		Type:"Text", 			SaveName:"EC_ORDER_ID", 	    		Align:"Center", Width:100, Edit:0}
		  , {Header:"상품번호", 		Type:"Text", 			SaveName:"PROD_CD", 					Align:"Center", Width:100, Edit:1}
		  , {Header:"상품명", 		Type:"Text", 			SaveName:"PROD_NM", 					Align:"Left", 	Width:150, Edit:1}
		  , {Header:"옵션", 			Type:"Text", 			SaveName:"ITEM_OPTION", 				Align:"Left",	Width:100, Edit:0}
		  , {Header:"수량", 			Type:"Int", 			SaveName:"ORD_QTY", 					Align:"Right", 	Width:60,  Edit:0, Format:"#,###,###"}
		  , {Header:"점포", 			Type:"Text", 			SaveName:"STR_NM", 						Align:"Center", Width:100, Edit:1}
		  , {Header:"주문상태", 		Type:"Text", 			SaveName:"ORD_STS_NM",  				Align:"Center", Width:100, Edit:0}
		  , {Header:"매출상태", 		Type:"Text", 			SaveName:"SALE_STS_NM",  				Align:"Center", Width:100, Edit:0}
		  , {Header:"주문자(받는자)", 	Type:"Text", 			SaveName:"CUST_NM",  					Align:"Center", Width:100, Edit:0}
		  , {Header:"주문금액",     	Type:"Int", 			SaveName:"ORDER_ITEM_AMT_SUM", 			Align:"Right", 	Width:80,  Edit:0, Format:"#,###,###"}
		  , {Header:"주문원가",        Type:"Int", 			SaveName:"BUY_PRC", 					Align:"Right", 	Width:80,  Edit:0, Format:"#,###,###"}
		  , {Header:"주문일자", 	    Type:"Text", 			SaveName:"ORD_DY", 	      				Align:"Center", Width:70,  Edit:0}
		  , {Header:"매출일자", 	    Type:"Text", 			SaveName:"SALE_DY", 	      			Align:"Center", Width:70,  Edit:0}
		];

		IBS_InitSheet(mySheet, ibdata);

		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
		//mySheet.FitColWidth();

	}); // end of ready


	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {

		var f = document.searchForm;

		var startDate = f.startDate.value.replace( /-/gi, '' );
		var endDate   = f.endDate.value.replace( /-/gi, '' );

		<c:choose>
			<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
			if ($("#vendorId").val() == "") {
		        alert('업체선택은 필수입니다.');
		        $("#vendorId").focus();
		        return;
		    }
			</c:when>
		</c:choose>

		if(startDate>endDate){
			alert('시작일자가 종료일자보다 클수 없습니다.');
			return;
		}

		goPage('1');
	}

    function goPage(currentPage) {

		var f = document.searchForm;

      	if(f.searchOrderType.value == "1") {
      		if(f.orderId.value.trim() == ""){
      			alert("원주문번호를 입력해주세요..!");
      			return;
      		}
      	}

      	if(f.searchOrderType.value == "2") {
      		if(f.orderId.value.trim() == ""){
      			alert("EC주문번호를 입력해주세요..!");
      			return;
      		}
      	}

        var url = '<c:url value="/order/selectOrderItemList.do"/>';
        var param = new Object();

        param.rowsPerPage = $("#rowsPerPage").val();
    	param.startDate = $("#startDate").val();
    	param.endDate = $("#endDate").val();
    	param.vendorId = $("#vendorId").val();
    	param.orderId = $("#orderId").val();
    	param.custNm = $("#custNm").val(); 
    	param.ordStsCd = $("#ordStsCd").val();
    	param.strCd =  $("#strCd").val();
    	param.setlTypeCd =  $("#setlTypeCd").val();
    	param.saleStsCd = $("#saleStsCd").val();  
    	param.searchType = $("#searchType").val();
    	param.searchOrderType = $("#searchOrderType").val();
    	param.mode = 'search';

    	loadIBSheetData(mySheet, url, currentPage, null, param);

    }

    function doExcel() {

     	if(mySheet.GetTotalRows() == 0){
    		alert('조회할 데이터가 없습니다.');
    		return;
    	}

    	if(confirm('엑셀 다운로드 하시겠습니까?')){

    		var paramInfo = {};
			paramInfo["startDate"] = $("#startDate").val();
			paramInfo["endDate"] = $("#endDate").val();
			paramInfo["vendorId"] = $("#vendorId").val();
			paramInfo["orderId"] = $("#orderId").val();
			paramInfo["custNm"] = $("#custNm").val(); 
			paramInfo["ordStsCd"] = $("#ordStsCd").val();
			paramInfo["strCd"] = $("#strCd").val();
			paramInfo["setlTypeCd"] = $("#setlTypeCd").val();
			paramInfo["saleStsCd"] = $("#saleStsCd").val();  
			paramInfo["searchType"] = $("#searchType").val();
			paramInfo["searchOrderType"] = $("#searchOrderType").val();

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/order/selectCustInfoByGoods.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					var resultList = data.excelPossYn;
					$("#CustInfoByGoods").val(resultList);
				}
			});

			if(parseInt($("#CustInfoByGoods").val()) > 0){
				alert("배송완료 시점부터 3개월이 지난 고객정보 엑셀파일 다운로드 불가");
				return;
			}else{
				var xlsUrl = '<c:url value="/order/selectOrderItemListExcel.do"/>';
				var hideCols = 'APPLY_OCUR_POINT|APPLY_COUPON_AMT';
				var param = new Object();

				param.rowsPerPage 	=  $("#rowsPerPage").val();
		    	param.startDate = $("#startDate").val();
		    	param.endDate = $("#endDate").val();
		    	param.vendorId = $("#vendorId").val();
		    	param.orderId =   $("#orderId").val();
		    	param.custNm =   $("#custNm").val(); 
		    	param.ordStsCd =  $("#ordStsCd").val();
		    	param.strCd =  $("#strCd").val();
		    	param.setlTypeCd =  $("#setlTypeCd").val();
		    	param.saleStsCd =  $("#saleStsCd").val();  
		    	param.searchType =  $("#searchType").val();
		    	param.mode = 'search';
				directExcelDown(mySheet, '주문목록조회(상품명)_' + new Date().yyyymmdd(), xlsUrl, '#searchForm', param, hideCols); // 전체 다운로드
			}
		}
    }

    function excelDown() {
    	$("#rowsPerPage > option:selected").val('65000');
    	doSearch();
    }

	function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
		$("#vendorId").val(strVendorId);
	}

	//데이터 읽은 직후 이벤트
	function mySheet_OnSearchEnd(cd,msg) {

		if(excelConf == true){
			if( RETURN_IBS_OBJ.excelPossYn > 0){
				alert("배송완료 시점부터 3개월이 지난 고객정보 엑셀파일 다운로드 불가");
				excelConf = false;
				return;
			}
// 				var gridObj = document.WG1;
// 				gridObj.ClearExcelInfo();
<%-- 				gridObj.strDefaultExportFileName="주문목록조회(상품별)_<%=excelToDate%>"; --%>
// 				gridObj.ExcelExport('', '', true, true);
// 				hideLoadingMask();
// 				excelConf = false;
// 				$("#rowsPerPage").val('50');
// 				goPage('1');
		}

		//mySheet.FitColWidth();
	}

	function mySheet_OnResize(Width, Height) {
		//변경된 정보에 따라 컬럼들의 너비를 재조정한다.
		//mySheet.FitColWidth();
	}

</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
	<div id="content_wrap">
		<div class="content_scroll">
			<form name="searchForm"  id="searchForm" method="post">
				<input type="hidden" name="searchUseYn" value="" />
				<input type="hidden" name="excelYn" value="" />
				<input type="hidden" name="CustInfoByGoods"  id="CustInfoByGoods" value="" />
				<div id="wrap_menu">
					<!--	@ 검색조건	-->
					<div class="wrap_search">
						<!-- 01 : search -->
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit">조회조건</li>
								<li class="btn">
									<a href="#" class="btn" id="excel"><span><spring:message code="button.common.excel" /></span></a>
									<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire" /></span></a>
								</li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
								<col width="13%">
								<col width="17%">
								<col width="10%">
								<col width="30%">
								<col width="10%">
								<col width="20%">
								</colgroup>
								<tr>
									<th><span class="star">*</span>조회기간구분</th>
									<td>
										<select id="searchType" name="searchType" class="select">
											<option value="1">주문일자</option>
											<option value="2">매출일자</option>
										</select>
									</td>
									<th><span class="star">*</span>기간</th>
									<td>
										<input type="text" name="startDate" value="${searchVO.startDate}" id="startDate" class="day" readonly style="width: 30%;"/>
										<a href="javascript:openCal('searchForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
									  ~ <input type="text" name="endDate" value="${searchVO.endDate}" id="endDate" class="day" readonly style="width: 30%;"/>
									    <a href="javascript:openCal('searchForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
									</td>
									<th> 일반구분</th>
										<td>
											<div>
												<input type="radio"  id="normalBtn" name="normalholiday" value="0" checked />일반
												<input type="radio"  id="holiBtn" name="normalholiday" value="1" />명절
											</div>
										</td>
								</tr>
								<tr>
									<th><span class="star">*</span> 매출점포</th>
									<td>
										<select id="strCd" name="strCd" class="select">
											<option value="">전체</option>
											<c:forEach items="${strCdList}" var="strCdList">
											<option value="${strCdList.STR_CD}" <c:if test="${searchVO.strCd eq strCdList.STR_CD}">selected="selected"</c:if>>${strCdList.STR_NM}</option>
											</c:forEach>
										</select>
									</td>
									<th> 주문상태</th>
									<td>
										<select id="ordStsCd" name="ordStsCd" class="select">
											<option value="">전체</option>
											<c:forEach items="${ordStsCdList}" var="ordStsCdList">
											<option value="${ordStsCdList.MINOR_CD}">${ordStsCdList.CD_NM}</option>
											</c:forEach>
										</select> 
									</td>
									<th> 결제방법</th>
									<td>
										<select id="setlTypeCd" name="setlTypeCd" class="select">
											<option value="">전체</option>
											<c:forEach items="${setlTypeCdList}" var="setlTypeCdList">
											<option value="${setlTypeCdList.MINOR_CD}">${setlTypeCdList.CD_NM}</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th>매출상태</th>
									<td>
										<select id="saleStsCd" name="saleStsCd" class="select">
											<option value="">전체</option>
											<c:forEach items="${saleStsCdList}" var="saleStsCdList">
											<option value="${saleStsCdList.MINOR_CD}">${saleStsCdList.CD_NM}</option>
											</c:forEach>
										</select>
									</td>
									<th>주문자명</th>
									<td><input type="text" id="custNm" name="custNm" value="" class="input" maxlength="20"/></td>
									<th>
										<select id="searchOrderType" name="searchOrderType" class="select">
											<option value="%" <c:if test="${'%' eq searchVO.searchOrderType}">selected="selected"</c:if>>선택</option>
											<option value="1" <c:if test="${'1' eq searchVO.searchOrderType}">selected="selected"</c:if>>원주문번호</option>
											<option value="2" <c:if test="${'2' eq searchVO.searchOrderType}">selected="selected"</c:if>>EC주문번호</option>
										</select>
									</th>
									<td><input type="text" id="orderId" name="orderId" value="" class="input" maxlength="30"/></td>
								</tr>
								<tr>	
									<th>협력업체코드</th>
									<td colspan="2">
										<c:choose>
											<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
												<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
												<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
											</c:when>
											<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
												<select name="vendorId" id="vendorId" class="select">
												<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
							                        <option value="${venArr}" <c:if test="${venArr eq vendorId || (not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId ) }">selected</c:if>>${venArr}</option>
												</c:forEach>
												</select>
											</c:when>
										</c:choose>
									</td>
									<td colspan="3"></td>
								</tr>
							</table>
						</div>
						<!-- 1검색조건 // -->
					</div>
					<!--	2 검색내역 	-->
					<div class="wrap_con">
						<!-- list -->
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">조회내역</li>
							</ul>
							<table class="bbs_list" cellpadding="0" cellspacing="0"
								border="0">
								<tr>
									<%-- <td><script type="text/javascript">initWiseGrid("WG1", "100%", "320");	</script></td> --%>
									<td><div id="ibsheet1"></div></td><!-- IBSheet 위치 -->
								</tr>
							</table>
						</div>
						<!-- 2검색내역 // -->
					</div>
					<!-- 페이징 DIV -->
					<div id="pagingDiv" class="pagingbox1" style="width: 100%;">
						<script>
							setLMPaging("0", "0", "0", "goPage", "pagingDiv");
						</script>
					</div>
				</div>
			</form>
		</div>
		<!-- footer -->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="location">
					<ul>
						<li>홈</li>
						<li>주문관리</li>
						<li class="last">주문목록조회(상품별)</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
	<!--	@ BODY WRAP  END  	// -->
</body>
</html>