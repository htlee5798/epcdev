<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%-- <%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> --%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>

<!-- 신규 공통 css 및 js 파일 INCLUDE -->
<c:import url="/common/commonHead.do" />
<%@ include file="/common/scm/scmCommon.jsp" %>

<script type="text/javascript">
var rtnProdCdList = new Array();
var rtnCullSellPrcList = new Array();

/** ********************************************************
 * jQeury 초기화
 ******************************************************** */
$(document).ready(function(){
	$('#search').click(function() {
		doSearch();
	});

	$('#startDate').click(function() {
		openCal('searchForm.startDate');
		});
	$('#endDate').click(function() {
		openCal('searchForm.endDate');
		});	

});
/** ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function doSearch() {
	goPage('1');
}

/** ********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function goPage(pageIndex) {

	var f = document.searchForm;

	var getDiff = getDateDiff(f.startDate.value,f.endDate.value);

	var startDate = f.startDate.value.replace( /-/gi, '' );
	var endDate   = f.endDate.value.replace( /-/gi, '' );

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
	
	if(startDate>endDate){
		alert('시작일자가 종료일자보다 클수 없습니다.');
		return;
	}

	if(getDiff>90){
		alert('시작일자보다 종료일자가 90일 이상 클수 없습니다.');
		return;
	}
	
	if(f.searchType.value == "1") {
		if(f.searchContent.value.trim() == ""){
			alert("주문번호를 입력해주세요..!");
			return;
		}
	}
	
	if(f.searchType.value == "2") {
		if(f.searchContent.value.trim() == ""){
			alert("로그인ID 를 입력해주세요..!");
			return;
		}
	}
	
	if(f.searchType.value == "3") {
		if(f.searchContent.value.trim() == ""){
			alert("보낸는분의 성함을 입력해주세요..!");
			return;
		}
	}
	
	if(f.searchType.value == "4") {
		if(f.searchContent.value.trim() == ""){
			alert("받는분의 성함을 입력해주세요..!");
			return;
		}
	}
	
	if(f.searchType.value == "7") {
		if(f.searchContent.value.trim() == ""){
			alert("원주문번호를 입력해주세요..!");
			return;
		}
	}
	
	var url = '<c:url value="/delivery/selectDeliHistList.do"/>';
	//f.pageIndex.value = pageIndex;
	f.action = url;
	f.submit();
}



<%-- $(document).ready(function(){
	$('#startDate').click(function() {
		openCal('popupForm.startDate');
	});
	$('#endDate').click(function() {
		openCal('popupForm.endDate');
	});
}); // end of ready
 --%>

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<div id="content_wrap">

<div class="content_scroll">

<form name="searchForm">
<input type="hidden" name="parentFormName" />
<div id="wrap_menu">

	<!--	@ 검색조건	-->
	<div class="wrap_search">
    <!--  @title  -->
     <div id="p_title1">
		<h1>배송지변경이력</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
		<!-- 01 : search -->
		<div class="bbs_search">
			<ul class="tit">
				<li class="tit">조회조건</li>
				<li class="btn">
					<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
					<a href="#" class="btn" onclick="self.close();"><span><spring:message code="button.common.close"/></span></a>
				</li>
			</ul>
			<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="9%">
				<col width="12%">
				<col width="8%">
				<col width="25%">
				<col width="9%">
				<col width="18%">
			</colgroup>
					
			<tr>
				<th><span class="star">*</span> 일자검색조건</th>
				<td>
					<select name="dateGbn" class="select">
   						<option value="01">주문일</option>
   						<option value="02">수정일</option>
					</select>
				</td>
				<th>
				<span class="star">*</span> 조회일자</th>
				<td>
				<input type="text" name="startDate" id="startDate" class="day" readonly style="width:30%;" value="${searchVO.startDate}" /><a href="javascript:openCal('searchForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
					~
				<input type="text" name="endDate" id="endDate" class="day" readonly style="width:30%;" value="${searchVO.endDate}" /><a href="javascript:openCal('searchForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>															
				</td>
				<th>
					<select name="searchType" class="select">
								<option value="%" <c:if test="${'%' eq searchVO.searchType}">selected="selected"</c:if>>선택</option>
								<option value="1" <c:if test="${'1' eq searchVO.searchType}">selected="selected"</c:if>>주문번호</option>
								<option value="3" <c:if test="${'3' eq searchVO.searchType}">selected="selected"</c:if>>보내는분</option>
								<option value="4" <c:if test="${'4' eq searchVO.searchType}">selected="selected"</c:if>>받는분</option>
								<option value="5" <c:if test="${'5' eq searchVO.searchType}">selected="selected"</c:if>>상품코드</option>
								<option value="6" <c:if test="${'6' eq searchVO.searchType}">selected="selected"</c:if>>판매코드</option>
								<option value="7" <c:if test="${'7' eq searchVO.searchType}">selected="selected"</c:if>>원주문번호</option>
					</select>
				</th>
				<td class="text">
					<input type="text" name="searchContent" value="${searchVO.searchContent}"/>
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
				<li class="tit">조회내역</li>
			</ul>
		</div>
		<!-- 2검색내역 // -->
		<div style="overflow-y:scroll;width:790px;height:380px;">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
                        <col style="width:90px" />
                        <col style="width:90px" />
                        <col style="width:90px" />
                        <col style="width:140px" />
                        <col style="width:100px" />
                        <col style="width:90px" />
                        <col style="width:90px" />
                        <col style="width:100px" />
                        <col style="width:100px" />
                        <%-- <col style="width:70px" /> --%>
                        <%-- <col style="width:80px" /> --%>
                        <%-- <col style="width:150px" /> --%>
                        <%-- <col style="width:70px" /> --%>
                        <%-- <col style="width:70px" /> --%>
					</colgroup>
					<tr> 
						<th rowspan="3">주문번호<br/><font color='red'>(원주문번호)</font></th>
						<th>상품코드</th>
						<th>판매코드</th>
						<th colspan="3">상품명</th>
						<th>규격</th>
						<th>주문수량</th>
						<th>보내는분</th>
					</tr>
					<tr>
						<th>주문일</th>
						<th>주문시간</th>
						<th colspan="3">기존배송지</th>
						<th>집전화</th>
						<th>H.P</th>
						<th>받는분</th>
					</tr>
					<tr>
						<th>수정일</th>
						<th>수정시간</th>
						<th colspan="3">신규배송지</th>
						<th>집전화</th>
						<th>H.P</th>
						<th>받는분</th>
					</tr>
		<c:set var="T_ORDER_ID"/>
		<c:set var="T_DELIVERY_ID"/>
		<c:set var="listCnt" value="${fn:length(list)}"/>
		
		<c:forEach var="list" items="${list}" varStatus="status">
			<c:if test="${T_ORDER_ID != list.ORDER_ID || T_DELIVERY_ID != list.DELIVERY_ID}">  
               		<tr>
						<td rowspan="3" style="background: ${divColor}"><c:out value="${list.ORDER_ID}"/><br/><font color='red'><c:out value="${list.FIRST_ORDER_ID}"/></font></td>
						<td>${list.PROD_CD}</td>
						<td>${list.MD_SRCMK_CD}</td>
						<td colspan="3">${list.PROD_NM}</td>
						<td>${list.PROD_STANDARD_NM}</td>
						<td>${list.ORD_QTY}</td>
						<td>${list.CUST_NM} </td>
					</tr>
					<tr>
						<td>${list.ORD_DY}</td>
						<td>${list.ORD_TM}</th>
						<td colspan="3"><c:out value="${list.PRE_ADDR_1_NM}"/><c:out value="${list.PRE_ADDR_2_NM}"/></td>
						<td><c:out value="${list.PRE_TEL_NO}"/></td>
						<td>
						 <c:out value="${list.PRE_CELL_NO}"/> 
						<%-- <fmt:formatNumber value="${list.PRE_CELL_NO}" pattern="### ########" /> --%>
						</td>
						<td>${list.PRE_CUST_NM}</td>
					</tr>
					<tr>
						<td>${list.REG_DATE}</td>
						<td>${list.REG_TM}</td>
						<td colspan="3"><c:out value="${list.CHG_ADDR_1_NM}"/><c:out value="${list.CHG_ADDR_2_NM}"/></td>
						<td><c:out value="${list.CHG_TEL_NO}"/></td>
						<td><c:out value="${list.CHG_CELL_NO}"/></td>
						<td>${list.CHG_CUST_NM}</td>
					</tr>
					<tr class="r1">
						<td height="1" colspan="15" style="color: blue;" ></td>
					</tr>
		</c:if>
			<c:set var="T_ORDER_ID" value="${list.ORDER_ID}"/>
			<c:set var="T_DELIVERY_ID" value="${list.DELIVERY_ID}"/>
		</c:forEach>
			</table>
			</div>
	</div>
	
	<!-- 페이징 DIV -->
	<!-- <div id="pagingDiv" class="pagingbox1" style="width: 100%;">
		<script> setLMPaging("0", "0", "0","goPage","pagingDiv")</script>
	</div> -->
 
</div>
</form>
</div>
	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg">${resultMsg}</div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>배송리스트</li>
					<li class="last">배송지변경이력</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->

</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>