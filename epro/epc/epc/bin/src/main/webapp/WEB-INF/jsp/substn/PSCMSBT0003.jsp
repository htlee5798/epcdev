<%--
 /**
  * @Class Name  : PSCMSBT0003.jsp
  * @Description : 정산내역조회  
  * @Modification Information
  * @
  * @  수정일             수정자                   수정내용
  * @ -------  --------    ---------------------------
  * @2015.11.25            skc			신규작성
  * @
  *  @author
  *  @since
  *  @version
  *  @see
  *
  */
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String excelToDate  =DateUtil.formatDate(DateUtil.getToday(),"-");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>


<%@page import="lcn.module.common.util.DateUtil"%>
<% 
	String curYear  = DateUtil.getCurrentYearAsString();
	String curMonth = DateUtil.getCurrentMonthAsString();	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- PSCMSBT0003 -->
<html>
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<%@ include file="/common/scm/scmCommon.jsp" %>
<script type="text/javascript">

$(document).ready(function(){
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "350px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	
	var ibdata = {};
	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
	    {Header:"원주문번호", 	Type:"Text", 			SaveName:"FIRST_ORDER_ID",  	Align:"Center",  	Width:100, Edit:0}
	  , {Header:"회원번호", 		Type:"Text", 			SaveName:"MEMBER_NO",  		Align:"Center", 	Width:120, Edit:0}
	  , {Header:"주문일자", 		Type:"Date", 			SaveName:"ORD_DY",  				Align:"Center", 	Width:100, Edit:0, Format:"yyyy-MM-dd"}
	  , {Header:"분류코드",     	Type:"Text", 			SaveName:"CAT_CD", 					Hidden:true}
	  , {Header:"분류",       		Type:"Text", 			SaveName:"CAT_NM", 				Align:"Center", 	Width:100, Edit:0}
	  , {Header:"상품코드", 		Type:"Text", 			SaveName:"PROD_CD", 	    		Align:"Center", 	Width:100, Edit:0}
	  , {Header:"상품명", 			Type:"Text", 			SaveName:"PROD_NM", 	    		Align:"Center", 	Width:150, Edit:0, Ellipsis:true}
	  , {Header:"공급가액", 		Type:"Int", 				SaveName:"SELL_PRC", 	    		Align:"Right", 		Width:100, Edit:0}
	  , {Header:"세액", 				Type:"Int", 				SaveName:"TAX_AMT", 	    		Align:"Right", 		Width:100, Edit:0}
	  , {Header:"매출금액", 		Type:"Int", 				SaveName:"SALE_AMT", 	    		Align:"Right", 		Width:100, Edit:0}
	  , {Header:"할인금액", 		Type:"Int", 				SaveName:"DC_AMT", 	    		Align:"Right", 		Width:100, Edit:0}
	  , {Header:"수수료율", 		Type:"Int", 				SaveName:"PRFT_RATE", 			Align:"Right", 		Width:100, Edit:0}
	  , {Header:"수수료금액", 	Type:"Int", 				SaveName:"PRFT_AMT", 	    		Align:"Right", 		Width:100, Edit:0}
	  , {Header:"업체분담율", 	Type:"Int", 				SaveName:"COM_SHCH_RT", 	    Align:"Right", 		Width:100, Edit:0}
	  , {Header:"업체분담금액", Type:"Int", 				SaveName:"COM_AMT", 	    	Align:"Right", 		Width:100, Edit:0}
	  , {Header:"자사분담율", 	Type:"Int", 				SaveName:"CORP_SHCH_RT", 	    Hidden:true}
	  , {Header:"자사분담금액", Type:"Int", 				SaveName:"CORP_AMT", 	    	Hidden:true}
	  , {Header:"카드사분담율", Type:"Int", 				SaveName:"CARD_SHCH_RT", 	    Hidden:true}
	  , {Header:"마일리지", 		Type:"Int", 				SaveName:"MILEAGE_AMT", 	    Align:"Right", 	Width:100, Edit:0}
	  , {Header:"배너광고비", 	Type:"Int", 				SaveName:"BANNER_AMT", 	    Align:"Right", 	Width:100, Edit:0}
	  , {Header:"택배비", 			Type:"Int", 				SaveName:"DELIVERY_AMT", 	    Align:"Right", 	Width:100, Edit:0}
	];
	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	
	//input enter 막기
	$("*").keypress(function(e){
        if(e.keyCode==13) return false;
	});
	
	
    $('#search').click(function() {
    	doSearch();
    });
	// EXCEL버튼 클릭
	$('#excel').click(function() {
		downExcel();
	});

});


/** ********************************************************
 * 1. 협력사팝업
 ******************************************************** */
function popupVendor(){
	var targetUrl = '<c:url value="/vendorPopUp/vendorPopUpView.do"/>';

 	Common.centerPopupWindow(targetUrl, 'vendorPopUp', {width : 592, height : 500});
}
/** ********************************************************
 * 2. 협렵사 정보 세팅
 ******************************************************** */
 function setVendorInto(strVendorId, strVendorNm, strCono) { 
		$("#vendorId").val(strVendorId);
 }

/** ********************************************************
 * 조회
 ******************************************************** */
function doSearch() {
	goPage();
}
function goPage(){
	
	var f = document.dataForm;
	var gridObj = document.WG1;
	
	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if (f.vendorId.value == "")
	    {
	        alert('업체선택은 필수입니다.');
	        f.vendorId.focus();
	        return;
	    }
		</c:when>
	</c:choose>

	//--------------------------------------
	// 정산내역조회 조회
	//--------------------------------------
	var url = '<c:url value="/substn/selectSubStnDeductList.do"/>';
	
	var param = new Object();
	param.mode 	    = "search";
	param.year 	    = $("#asCmbYy").val();
	param.month 	= $("#asCmbMonth").val();
	param.accotYm 	= $('#asCmbYy').val() + $('#asCmbMonth').val();
	param.adjSeq 	= $("#asCmbSeq").val();
	param.catCd 	    = $("#selDaeGoods").val();
	param.catNm 	= $("#selDaeGoods option:selected").val();
	param.vendorId	= $("#vendorId").val();
	
	loadIBSheetData(mySheet, url, null, null, param);
}

//데이터 읽은 직후 이벤트
function mySheet_OnSearchEnd() {
	fSubStnSum();
}

//집계 데이터 가져오기
function fSubStnSum(){
 
	
	var targetUrl = "";

	 $('#year').val($('#asCmbYy').val());
	 $('#month').val($('#asCmbMonth').val());
	 $('#accotYm').val($('#asCmbYy').val() + $('#asCmbMonth').val());
	 $('#adjSeq').val($('#asCmbSeq').val());
	 $('#catCd').val($('#selDaeGoods').val());
	 
	 var formQueryString = $('*', '#dataForm').fieldSerialize();
	targetUrl = '<c:url value="/substn/selectSubStnDeductSumList.do"/>';
	
	$.ajax({
		type: 'POST',
		url: targetUrl,
		data: formQueryString,
		success: function(data) {
			try {
				_setTbodyMasterValue(data);	
				}
			 catch (e) {}
		},
		error: function(e) {
			alert(e);
		}
	});
	/* _eventSearch() 후처리(data  객체 그리기) */
	function _setTbodyMasterValue(json) { 
		var data = json.stats;
		var tempData = [];
		var ORD_AMT = addTxtComma(String(data.ORD_AMT));
		var DC_AMT = addTxtComma(String(data.DC_AMT));
		var SALE_AMT = addTxtComma(String(data.SALE_AMT));
		var IN_AMT = addTxtComma(String(data.IN_AMT));
		var CHARGE_AMT = addTxtComma(String(data.CHARGE_AMT));
		var DEDUCT_AMT = addTxtComma(String(data.DEDUCT_AMT));
		var DELIVERY_AMT = addTxtComma(String(data.DELIVERY_AMT));
		var ADJ_AMT = addTxtComma(String(data.ADJ_AMT));
		$('#ordAmt').text(ORD_AMT);
		$('#dcAmt').text(DC_AMT);
		$('#saleAmt').text(SALE_AMT);
		$('#inAmt').text(IN_AMT);
		$('#chargeAmt').text(CHARGE_AMT);
		$('#deductAmt').text(DEDUCT_AMT);
		$('#deliveryAmt').text(DELIVERY_AMT);
		$('#adjAmt').text(ADJ_AMT);

	}
}

function Lpad(str, len) {
 	str = str + "";
 	
	while(str.length < len) {
  		str = "0" + str;
 	}
 	
 	return str;
}

//엑셀다운
function downExcel()
{
	if(confirm('엑셀 다운로드 하시겠습니까?')){
		var xlsUrl = '<c:url value="/substn/selectSubStnDeductListExcel.do"/>';
		
		var param = new Object();
		param.mode 	    = "search";
		param.year 	    = $("#asCmbYy").val();
		param.month 	= $("#asCmbMonth").val();
		param.accotYm 	= $('#asCmbYy').val() + $('#asCmbMonth').val();
		param.adjSeq 	= $("#asCmbSeq").val();
		param.catCd 	= $("#selDaeGoods").val();
		param.catNm 	= $("#selDaeGoods option:selected").val();
		param.vendorId	= $("#vendorId").val();
		
		directExcelDown(mySheet, '정산내역조회_<%=excelToDate%>', xlsUrl, null, param, null); // 전체 다운로드 
	}    
}
</script>
</head>

<body>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form name="dataForm" id="dataForm">

	
<input type="hidden" id="year" name="year" value="" />
<input type="hidden" id="month" name="month" value="" />
<input type="hidden" id="accotYm" name="accotYm" value="" />
<input type="hidden" id="adjSeq" name="adjSeq" value="" />
<input type="hidden" id="catCd" name="catCd" value="" />

<div id="content_wrap">
<div class="content_scroll"> 
	<div id="wrap_menu">
		<!--	@ 검색조건	-->
		<div class="wrap_search">
			<!-- 01 : search -->
			<div class="bbs_search">
			
				<ul class="tit">
					<li class="tit">정산내역조회</li>
					<li class="btn">
						<a href="#" class="btn" id="excel"><span><spring:message code="button.common.excel" /></span></a>
						<a href="#" class="btn" id="search"><span><spring:message code="button.common.search"/></span></a> 
					</li>
				</ul>
				<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="10%">
					<col width="20%">
					<col width="10%">
					<col width="20%">
					<col width="10%">
					<col width="15%">
					<col width="10%">
					<col width="15%">

				</colgroup>
				<tr>
						<th><span class="star">* </span>회계년월</th>
						<td>
							<select name="asCmbYy" id="asCmbYy" style="width:50%"  class="select">
								<script>
									var year;
									for(i=2015;i<=2020;i++)
									{
										year = i;
										if(year == <%=curYear%>)
											document.write("<option value="+year+" selected>"+year+"년</option>");
										else
											document.write("<option value="+year+">"+year+"년</option>");
									}	
								</script>	
							</select>
							<select name="asCmbMonth" id="asCmbMonth" style="width:40%">
								<script>
									var month;
									for(i=1;i<=12;i++)
									{
										month = i;
										if(month == <%=curMonth%>)
											document.write("<option value="+Lpad(month,2)+" selected>"+month+"월</option>");
										else
											document.write("<option value="+Lpad(month,2)+">"+month+"월</option>");
									}	
								</script>  						
							</select>							
							
						</td>	
						<th><span class="star">* </span>회차</th>
						<td>
							<select name="asCmbSeq" id="asCmbSeq" style="width:90%">
	    						<option value="1">1차 (1일~10일)</option>
	    						<option value="2">2차 (11일~20일)</option>
	    						<option value="3">3차 (21~월말)</option>
							</select>			
						</td>		
						<th><span class="star">* </span>대분류</th>
						<td>
							<select style="width:90%" class="select" name="selDaeGoods" id="selDaeGoods" onChange="javascript:fChange('dae');"><!--  style="width:150px;" -->
								<option value="">선택</option>
								<c:forEach items="${daeCdList}" var="code" begin="0">
	 								<option value="${code.code }" ${code.code == daeCd ? "selected='selected'" : "" }>${code.name }</option>
	 							</c:forEach>
							</select>
						</td>		
						<th><span class="star">* </span>협력업체</th>
							<td>
								<c:choose>
										<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
											<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
											<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
										</c:when>
										<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
											<select class="select" name="vendorId" id="vendorId" style="width:90%">
											
			                            	<option value="" <c:if test="${param.vendorId == ''}">selected</c:if>>선택</option>
											<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
						                        <option value="${venArr}" <c:if test="${venArr eq param.vendorId || (not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId) }">selected</c:if>>${venArr}</option>
						                    </c:forEach>
											</select>
										</c:when>
									</c:choose>
							</td>
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
					<ul class="tit"">
						<li class="tit" style="float:left;width:15%;">정산내역집계</li>
					</ul>
				</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					  <colgroup>
						<col style="width:10%" />
						<col style="width:10%" />
						<col style="width:10%" />
						<col style="width:10%" />
						<col style="width:10%" />
						<col style="width:10%" />
						<col style="width:10%" />
						<col style="width:10%" />
					  </colgroup>
					  <tr>
						<th>총매출액(A)</th>
						<td id="ordAmt" style='text-align: center'><fmt:formatNumber value="" pattern="#,###,###,###,###" /></td>
						<th>에누리액(B)</th>
						<td id="dcAmt" style='text-align: center'><fmt:formatNumber value="" pattern="#,###,###,###,###" /></td>
						<th>순매출액(C=A+B)</th>
						<td id="saleAmt" style='text-align: center'><fmt:formatNumber value="" pattern="#,###,###,###,###" /></td>
						<th>매입액(D)</th>
						<td id="inAmt" style='text-align: center'><fmt:formatNumber value="" pattern="#,###,###,###,###" /></td>
					  </tr>
					  <tr>
						<th>수수료금액(E=A-D)</th>
						<td id="chargeAmt" style='text-align: center'><fmt:formatNumber value="" pattern="#,###,###,###,###" /></td>
						<th>공제금액(F)</th>
						<td id="deductAmt" style='text-align: center'><fmt:formatNumber value="" pattern="#,###,###,###,###" /></td>
						<th>배송비(G)</th>
						<td id="deliveryAmt" style='text-align: center'><fmt:formatNumber value="" pattern="#,###,###,###,###" /></td>
						<th>정산금액(H=D+F+G)</th>
						<td id="adjAmt" style='text-align: center'><fmt:formatNumber value="" pattern="#,###,###,###,###" /></td>
					  </tr>
					</table>
			</div>
	
		</div>
		<!--	2-2상세검색내역 	-->
		<div class="wrap_con">
			<!-- list -->
			<div class="bbs_list">
				<ul class="tit">
					<ul class="tit"">
						<li class="tit" style="float:left;width:20%;">정산내역 상세내역</li>
					</ul>
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
<!-- footer -->
<div id="footer">
	<div id="footbox">
		<div class="msg" id="resultMsg"></div>
		<!--  홈 -->
		<div class="location">
			<ul>
				<li>홈</li>
				<li>정산관리</li>
				<li class="last">정산내역조회</li>
			</ul>
		</div>
	</div>
</div>
<!-- footer //-->
	
</div>	
</form>


</body>
</html>