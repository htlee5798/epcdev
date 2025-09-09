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
<%@page import="com.lottemart.epc.common.util.SecureUtil" %>

<% 
	String orderId = SecureUtil.stripXSS(request.getParameter("orderId"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag-dev.js" ></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>

<style type="text/css">
		table.bbs_list th.orderItemListHeader 
		{
			background:url('/images/common/layout/bg_list_05.png') repeat-x left top;
			background-color:#5fb1ce;
			color:#ffffff;
			font-weight:bold;
			font-size:11px;
		}
		table.bbs_list th.multiline 
		{
			background:url('');
			background-color:#e7e7e7;
		}
		table td.orderdetail_btn
		{
			width:50pxpx;
		}
</style>
	
<script type="text/javascript">
/** ********************************************************
 * jQeury 초기화
 ******************************************************** */
$(document).ready(function(){
	
}); // end of ready


//상품번호 존재시 상품 정보 팝업.
function productInfo(CategoryID, prod_Cd, strCd, mallDivnCd){
	
	if(strCd == null){
		strCd = "${order.STR_CD}";
	}
	
    if ( prod_Cd != "" ){
       	// MART 상품이면 MART 상품상세로
       	// TRU  상품이면 TRU  상품상세로
       	switch (mallDivnCd)
       	{
       		//MART
       		case "00001" : {Common.centerPopupWindow('http://www.lottemart.com/product/ProductDetail.do?ProductCD='+prod_Cd+'&strCd='+strCd+'&approvalGbn=N&previewYN=Y', '_blank', {width : 980, height : 600, scrollBars : 'YES'}); }     break;
       		//TRU
       		case "00002" : {Common.centerPopupWindow('http://toysrus.lottemart.com/product/ProductDetailAdmin.do?ProductCD='+prod_Cd+'&strCd='+strCd+'&approvalGbn=N', '_blank', {width : 980, height : 600, scrollBars : 'YES'}); } break;
       		
       		default: { 
       			Common.centerPopupWindow('http://www.lottemart.com/product/ProductDetail.do?ProductCD='+prod_Cd+'&strCd='+strCd+'&approvalGbn=N&previewYN=Y', '_blank', {width : 980, height : 600, scrollBars : 'YES'});
       		}
       	}
    }
}

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<form name="dataForm" id="dataForm">

<div id="popup">
	<!--  @title  -->
	<div id="p_title1">
		<h1>구매상품상세</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/cc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
	</div><br/>
	<!--  @title  //-->

	<!-- 팝업내용 -->
	<div class="popup_contents">
		<!-- 회원정보(주문자정보) -->
		<div class="bbs_list">
			<!-- 선택된 주문의 마스트 정보 출력 종료 -->
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="1">
				<colgroup>
					<col width="25%">
					<col width="50%">
					<col width="25%">
				</colgroup>
				<tr>
					<th class="orderItemListHeader">상품순번</th>
					<th class="orderItemListHeader">상품명</th>
					<th class="orderItemListHeader">단가</th>
				</tr>
		<c:if test="${!empty orderItemList}">
			<c:forEach items="${orderItemList}" var="orderItem" varStatus="jstatus" >
				<tr>
					<td>${orderItem.ORDER_ITEM_SEQ}</td>
					<td title="${orderItem.PROD_NM}">
                  			<c:choose>
							<c:when test="${orderItem.DELI_STATUS_CD == '33' || orderItem.DELI_STATUS_CD == '34'}">
								<font color="red">${orderItem.PROD_NM_STR}</font>
	                   		</c:when>
	                   		<c:otherwise>
								${orderItem.PROD_NM_STR}
	                   		</c:otherwise>
                   		</c:choose>
                   		<br/>
                   		<c:if test="${!empty orderItem.VARIATION_NM}">
                   			${orderItem.VARIATION_NM}
                   			<br/>
                  		</c:if>
                  			<!-- <a href="javascript: ;" onClick="javascript:productInfo('${orderItem.CATEGORY_ID}','${orderItem.PROD_CD}','${orderItem.ITEM_STR_CD}','${orderItem.MALL_DIVN_CD}');">${orderItem.PROD_CD}</a>  -->
                  			${orderItem.PROD_CD}
					</td>
					<td>
						<fmt:formatNumber value="${orderItem.CURR_SELL_PRC}" pattern="#,###,###,##0"  />원
					</td>
				</tr>
			</c:forEach> <%-- orderItemList --%>
		</c:if>
			</table>
		</div>
	</div>
</div>
</form>

<form name="form1" id="form1">
<input type="hidden" id="orderId" name="orderId" value="<%=orderId%>" />
</form>

</body>
</html>