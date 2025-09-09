<%--
 /**
  * @Class Name  : PSCMSBT0001.jsp
  * @Description : 정산지불현황
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
<!-- PBOMSBT0002 -->
<html>
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<%@ include file="/common/scm/scmCommon.jsp" %>
<script type="text/javascript">

$(document).ready(function(){
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "380px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	
	var ibdata = {};
	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:0, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
	    {Header:"순번", 							Type:"Text", 			SaveName:"NUM",							Align:"Center", 	Width:50, Edit:0}
	  , {Header:"상위업체코드", 			Type:"Text", 			SaveName:"SALE_VENDOR_ID",  		Align:"Center",  	Width:100, Edit:0}
	  , {Header:"상위업체명", 				Type:"Text", 			SaveName:"SALE_VENDOR_NM",  	Align:"Center", 	Width:120, Edit:0}
	  , {Header:"하위업체코드", 			Type:"Text", 			SaveName:"VENDOR_ID",  				Align:"Center", 	Width:100, Edit:0}
	  , {Header:"하위업체명",     			Type:"Text", 			SaveName:"VENDOR_NM", 			Align:"Center", 	Width:120, Edit:0}
	  , {Header:"주문번호",       				Type:"Text", 			SaveName:"ORDER_ID", 					Align:"Center", 	Width:100, Edit:0}
	  , {Header:"원주문번호", 				Type:"Text", 			SaveName:"FIRST_ORDER_ID", 	    Align:"Center", 	Width:100, Edit:0}
	  , {Header:"인터넷상품코드", 			Type:"Text", 			SaveName:"PROD_CD", 	    			Align:"Center", 	Width:100, Edit:0}
	  , {Header:"상품명", 						Type:"Text", 			SaveName:"PROD_NM", 	    			Align:"Center", 	Width:150, Edit:0, Ellipsis:true}
	  , {Header:"결재코드", 					Type:"Text", 			SaveName:"SETL_TYPE_CD", 	    	Hidden:true}
	  , {Header:"결재방법", 					Type:"Text", 			SaveName:"SETL_TYPE_NM", 	    	Align:"Center", 	Width:100, Edit:0}
	  , {Header:"쿠폰유형코드", 			Type:"Text", 			SaveName:"COUPON_TYPE_CD", 	    Hidden:true}
	  , {Header:"쿠폰유형", 					Type:"Text", 			SaveName:"COUPON_TYPE_NM", 	Hidden:true}
	  , {Header:"주문상태코드", 			Type:"Text", 			SaveName:"ORD_STS_CD", 	    		Hidden:true}
	  , {Header:"주문상태", 					Type:"Text", 			SaveName:"ORD_STS_NM", 	    	Align:"Center", 	Width:100, Edit:0}
	  , {Header:"매출상태코드", 			Type:"Text", 			SaveName:"SALE_STS_CD", 	    		Hidden:true}
	  , {Header:"매출상태", 					Type:"Text", 			SaveName:"SALE_STS_NM", 	    	Align:"Center", 	Width:100, Edit:0}
	  , {Header:"결재수단별지불금액", 	Type:"Int", 				SaveName:"PAY_AMT", 	    			Align:"Right", 		Width:120, Edit:0}
	  , {Header:"위수탁상품결재금액", 	Type:"Int", 				SaveName:"BUY_PRC", 	    			Align:"Right", 		Width:120, Edit:0}
	  , {Header:"주문일자", 					Type:"Date", 			SaveName:"ORD_DY", 	    			Align:"Center", 	Width:100, Edit:0, Format:"yyyy-MM-dd"}
	  , {Header:"매출일자", 					Type:"Date", 			SaveName:"CONF_DATE", 	    		Align:"Center", 	Width:100, Edit:0, Format:"yyyy-MM-dd"}
	];
	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetFrozenCol(3);
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

//데이터 읽은 직후 이벤트
function mySheet_OnSearchEnd() {
	var rowCount  = mySheet.RowCount()+1;
	var value;
	var value2;
	var i;
	
	for(i=1; i<rowCount; i++){
		value = mySheet.GetCellValue(i,"ORDER_ID");
		
		for(j=i + 1 ; j<rowCount; j++){
			value2 = mySheet.GetCellValue(j,"ORDER_ID");
			
			if(value == value2){
				mySheet.SetMergeCell(i,18,(j-i)+1,1);
			}
		}
	}
}

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
function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
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
	// 업체별 매출공제 조회
	//--------------------------------------
	var url = '<c:url value="/substn/selectPaySubStnList.do"/>';
	
	var param = new Object();
	param.mode 	    = "search";
	param.year 	    = $("#asCmbYy").val();
	param.month 	= $("#asCmbMonth").val();
	param.accotYm 	= $('#asCmbYy').val() + $('#asCmbMonth').val();
	param.adjSeq 	= $("#asCmbSeq").val();
	param.catCd 	    = $("#selDaeGoods").val();
	param.catNm 	= $("#selDaeGoods option:selected").val();
	param.setlTypeCd	= $("#setlTypeCd").val();
	param.ordStsCd	= $("#ordStsCd").val();
	param.saleStsCd	= $("#saleStsCd").val();
	param.vendorId	= f.vendorId.value;
	param.prodCd	= $("#prodCd").val();
	
	loadIBSheetData(mySheet, url, null, null, param);
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
		var f = document.dataForm;
		var xlsUrl = '<c:url value="/substn/selectPaySubStnListExcel.do"/>';
		
		var param = new Object();
		param.mode 	    = "search";
		param.year 	    = $("#asCmbYy").val();
		param.month 	= $("#asCmbMonth").val();
		param.accotYm 	= $('#asCmbYy').val() + $('#asCmbMonth').val();
		param.adjSeq 	= $("#asCmbSeq").val();
		param.catCd 	    = $("#selDaeGoods").val();
		param.catNm 	= $("#selDaeGoods option:selected").val();
		param.setlTypeCd	= $("#setlTypeCd").val();
		param.ordStsCd	= $("#ordStsCd").val();
		param.saleStsCd	= $("#saleStsCd").val();
		param.vendorId	= f.vendorId.value;
		param.prodCd	= $("#prodCd").val();
		
		directExcelDown(mySheet, '정산지불현황_' + new Date().yyyymmdd(), xlsUrl, null, param, null); // 전체 다운로드 
	}    
}
</script>
</head>

<body>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form name="dataForm" id="dataForm">
<div id="content_wrap">
<div class="content_scroll"> 
	<div id="wrap_menu">
		<!--	@ 검색조건	-->
		<div class="wrap_search">
			<!-- 01 : search -->
			<div class="bbs_search">
			
				<ul class="tit">
					<li class="tit">정산지불현황</li>
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
					<col width="15%">
					<col width="10%">
					<col width="15%">
					<col width="10%">
					<col width="20%">

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
							
						<th>분류</th>
						<td>
							<select style="width:90%" class="select" name="selDaeGoods" id="selDaeGoods">
								<option value="">전체</option>
								<c:forEach items="${daeCdList}" var="code" begin="0">
	 								<option value="${code.code }" ${code.code == daeCd ? "selected='selected'" : "" }>${code.name }</option>
	 							</c:forEach>
							</select>
						</td>	
						<th>결재수단</th>
						<td colspan="3">
							<select style="width:95%"  name="setlTypeCd" id="setlTypeCd" class="select">
								<option value="">전체</option>
								<c:forEach items="${codeListOR008}" var="code" begin="0">
		 							<option value="${code.CD }">${code.NM }</option>
		 						</c:forEach>
							</select>
						</td>	

					</tr>
						<th>주문상태</th>
						<td>
							<select style="width:90%" class="select" name="ordStsCd" id="ordStsCd"><!--  style="width:150px;" -->
								<option value="">전체</option>
								<c:forEach items="${ordCdList}" var="ordInfo" varStatus="status">
									<option value="${ordInfo.MINOR_CD}">${ordInfo.CD_NM}</option>
								</c:forEach>
							</select>
						</td>
						<th>매출상태</th>
						<td>
							<select style="width:90%" class="select" name="saleStsCd" id="saleStsCd" ><!--  style="width:150px;" -->
								<option value="">전체</option>
								<c:forEach items="${saleCdList}" var="saleInfo" varStatus="status">
									<option value="${saleInfo.MINOR_CD}">${saleInfo.CD_NM}</option>
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

						<th class="fst">상품코드</th>
						<td>
							<input type="text" name="prodCd" id="prodCd" style="width:90%;height:12px;border:1px solid #ccc;font-size:11px;line-height:12px;" size="10" value="" class="inputRead">							
						</td>
					<tr>
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
					<li class="tit" style="float:left;width:10%;"><spring:message code="text.common.title.searchResult"/></li>
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
				<li class="last">정산지불현황</li>
			</ul>
		</div>
	</div>
</div>
<!-- footer //-->
	
	
</div>	
</form>


</body>
</html>