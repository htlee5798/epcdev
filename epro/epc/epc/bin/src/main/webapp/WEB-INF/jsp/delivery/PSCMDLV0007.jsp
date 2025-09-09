<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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

<script type="text/javascript">
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
}); // end of ready

/** ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function doSearch() {
	
	goPage('1');
}

/** ********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function goPage(pageIndex){
	hideLoadingMask();
	var form = document.searchForm;
	
	var startDate = form.startDate.value.replace( /-/gi, '' );
	var endDate   = form.endDate.value.replace( /-/gi, '' );
	
	if(startDate>endDate){
		alert('시작일자가 종료일자보다 클수 없습니다.');
		return;
	}	
	
	var url = '<c:url value="/delivery/selectPartherReturnStatusList.do"/>';
	form.action = url;
	form.submit();
	loadingMask();
}

//로딩바 보이기
function loadingMask(){
	var childWidth = 128;
	var childHeight = 128;
	var childTop = (document.body.clientHeight - childHeight) / 2;
	var childLeft = (document.body.clientWidth - childWidth) / 2;
	var loadingDiv = $('<span id="loadingLayer" style="position:absolute; left:'+childLeft+'px; top:'+childTop+'px; width:100%; height:100%; z-index:10001"><img id="ajax_load_type_img" src="/js/jquery/res/loading.gif"></span>').appendTo($("body"));
	loadingDiv.show();
	
	var loadingDivBg = $('<iframe id="loadingLayerBg" frameborder="0" style="filter:alpha(opacity=0);position:absolute; left:0px; top:0px; width:100%; height:100%; z-index:10000"><img id="ajax_load_type_img" src="/js/jquery/res/loading.gif"></iframe>').appendTo($("body"));
	loadingDivBg.show();
} 

// 로딩바 감추기
function hideLoadingMask(){
	$('#loadingLayer').remove();
	$('#loadingLayerBg').remove();
}
</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<div id="content_wrap">

	<div class="content_scroll">
	
	<form name="searchForm" method="post">

		<div id="wrap_menu">
		
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">조회조건</li>
						<li class="btn">
							<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col width="10%">
						<col width="30%">
						<col width="10%">
						<col width="15%">
						<col width="10%">
						<col width="15%">
					</colgroup>
					<tr>
						<th><span class="star">*</span> 접수일자</th>
						<td>
							<input type="text" name="startDate" id="startDate" class="day" readonly style="width:33%;" value="${searchVO.startDate}" /><a href="javascript:openCal('searchForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
							~
							<input type="text" name="endDate" id="endDate" class="day" readonly style="width:33%;" value="${searchVO.endDate}" /><a href="javascript:openCal('searchForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
						</td>
						<th>협력업체코드</th>
						<td>
							<select id="vendorId" name="vendorId" class="select">
								<option value="">전체</option>
								<c:forEach items="${epcLoginVO.vendorId}" var="venArr">
								<option value="${venArr}" <c:if test="${venArr eq vendorId}">selected="selected"</c:if>>${venArr}</option>
								</c:forEach>
							</select>
						</td>
						<td></td>
						<th></th>
						<td></td>
					</tr>
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
				
				
			<!--	1 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
				
					<ul class="tit">
						<li class="tit">합계내역</li>
					</ul>
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
<%-- 						<col style="width:8%" /> --%>
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:10%" />
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:8%" />
					</colgroup>
					<tr> 
						<th rowspan=2>배송업체수</th>
						<th rowspan=2>배송건수</th>
						<th colspan=10>상태</th>
					</tr>					
					<tr> 
						<th>반품대기</th>
						<th>반품지시</th>
						<th>반품회수<br/>확인</th>
						<th>반품회수<br/>실패</th>
						<th>반품취소</th>
						<th>교환대기</th>
						<th>교환지시</th>
						<th>교환회수<br/>확인</th>
						<th>교환회수<br/>실패</th>
						<th>교환취소</th>						
					</tr>
					<tr>
						<td>${map.VENDOR_COUNT}</td>
						<td>${map.CNT}</td>
						<td><fmt:formatNumber value="${map.DELI_STATUS_61}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${map.DELI_STATUS_62}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${map.DELI_STATUS_66}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${map.DELI_STATUS_67}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${map.DELI_STATUS_65}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${map.DELI_STATUS_71}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${map.DELI_STATUS_72}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${map.DELI_STATUS_77}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${map.DELI_STATUS_76}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${map.DELI_STATUS_75}" pattern="#,##0" /></td>
					</tr>

					</table>
				</div>
		
			</div>
			<!-- 2검색내역 // -->				
				
				
			<!--	2 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
				
					<ul class="tit">
						<li class="tit">조회내역</li>
					</ul>
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:8%" />
						<col style="width:8%" />
					</colgroup>
					<tr> 
						<th rowspan=2>업체코드</th>
						<th rowspan=2>업체배송건수</th>
						<th colspan=10>통합</th>
					</tr>					
					<tr> 
						<th>반품대기</th>
						<th>반품지시</th>
						<th>반품회수<br/>확인</th>
						<th>반품회수<br/>실패</th>
						<th>반품취소</th>
						<th>교환대기</th>
						<th>교환지시</th>
						<th>교환회수<br/>확인</th>
						<th>교환회수<br/>실패</th>
						<th>교환취소</th>						
					</tr>
					<c:forEach var="list" items="${list}">
					<tr>
						<td>${list.VENDOR_ID}</td>
						<td><fmt:formatNumber value="${list.CNT}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${list.DELI_STATUS_61}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${list.DELI_STATUS_62}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${list.DELI_STATUS_66}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${list.DELI_STATUS_67}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${list.DELI_STATUS_65}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${list.DELI_STATUS_71}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${list.DELI_STATUS_72}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${list.DELI_STATUS_77}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${list.DELI_STATUS_76}" pattern="#,##0" /></td>
						<td><fmt:formatNumber value="${list.DELI_STATUS_75}" pattern="#,##0" /></td>
					</tr>
					</c:forEach>

					</table>
				</div>
		
			</div>
			<!-- 2검색내역 // -->
		
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
					<li>배송관리</li>
					<li class="last">반품/교환목록</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->

</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>