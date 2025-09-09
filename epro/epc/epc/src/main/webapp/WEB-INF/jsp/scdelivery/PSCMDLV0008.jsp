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
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style.css" ></link>
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag-dev.js" ></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>

<script type="text/javascript">


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
	}); // end of ready


	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		
		var f = document.searchForm;
		
		var startDate = f.startDate.value.replace( /-/gi, '' );
		var endDate   = f.endDate.value.replace( /-/gi, '' );
		
		if(startDate>endDate){
			alert('시작일자가 종료일자보다 클수 없습니다.');
			return;
		}			
		
		// 범위체크
		var f_date = f.startDate.value;
		var t_date = f.endDate.value;

	  	var from_day = f_date.substring(5,7) + "-" + f_date.substring(8,10)+ "-" + f_date.substring(0,4);
	  	var to_day   = t_date.substring(5,7) + "-" + t_date.substring(8,10)+ "-" + t_date.substring(0,4);
			
	    from_day  = new Date(from_day);
	    to_day    = new Date(to_day);
	    var days  = Math.ceil((to_day-from_day)/24/60/60/1000);
		goPage();
	}

    
	/** ********************************************************
	 * 페이징 처리 함수
	 ******************************************************** */
	function goPage(){
		var form = document.searchForm;
		
		var startDate = form.startDate.value.replace( /-/gi, '' );
		var endDate   = form.endDate.value.replace( /-/gi, '' );
		
		if(startDate>endDate){
			alert('시작일자가 종료일자보다 클수 없습니다.');
			return;
		}	
		
		var url = '<c:url value="/scdelivery/selectScDeliveryList.do"/>';
		form.flag.value = 'serach';
		form.action = url;
		form.submit();
	}	
	
    /** ********************************************************
     * execl
     ******************************************************** */
    function doExcel(){
		var f = document.searchForm;
		
		var startDate = f.startDate.value.replace( /-/gi, '' );
		var endDate   = f.endDate.value.replace( /-/gi, '' );
		
		if(startDate>endDate){
			alert('시작일자가 종료일자보다 클수 없습니다.');
			return;
		}			
		
    	var url = '<c:url value="/scdelivery/selectScDeliveryExcelList.do"/>';
    	f.action = url;
    	f.submit();	
    }	
    
    function popupVendorList()
    {
    	var targetUrl = '<c:url value="/common/selectPartnerFirmsPopup.do"/>';
    	Common.centerPopupWindow(targetUrl, 'popup', {width : 800, height : 550});    		
    }    
    
    
    function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
    	$("#vendorId").val(strVendorId);
    	$("#vendorNm").val(strVendorNm);
    }	    
    
    
    //협력업체 입력 정보 삭제
    function deleteVendorList()
    {
        var form = document.dataForm;
    	$("#vendorId").val('');
    	$("#vendorNm").val('');
    }    
    
    /***********************
     * 주문상세내역 팝업 함수
     ************************/
    function showOrderDetail(orderId) {
    	var targetUrl = '<c:url value="/dcorder/order/PDCPORD0001.do"/>?order_id='+orderId;
    	Common.centerPopupWindow(targetUrl, '_blank', {width : 1200, height : 700, scrollBars : 'YES'});
    }    
    
    
</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>


	<div id="content_wrap">

		<div class="content_scroll">

			<form name="searchForm" method="post">

				<input type="hidden" name="flag" value="" />
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
								<col width="10%">
								<col width="30%"> 
								<col width="10%">
								<col width="30%">
								<col width="10%">
								<col width="10%">
								</colgroup>
								<tr>
									<th><span class="star">*</span>주문기간</th>
									<td>
										<input type="text" name="startDate" value="${searchVO.startDate}" id="startDate" class="day" readonly style="width: 30%;"/>
										<a href="javascript:openCal('searchForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
									  ~ <input type="text" name="endDate" value="${searchVO.endDate}" id="endDate" class="day" readonly style="width: 30%;"/>
									    <a href="javascript:openCal('searchForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
									</td>
									<th>협력업체코드</th>
									<td>
					                    <input type="hidden" name="vendorId" id="vendorId" class="day" readonly style="width:39%;" />
					                    <input type="text" name="vendorNm" id="vendorNm" class="day" readonly style="width:70%;" />
					                    <a href="javascript:popupVendorList();"  ><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_search.gif" alt="" class="middle" /></a>
					                    <a href="javascript:deleteVendorList();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_eraser.gif" alt="" class="middle" /></a>
									</td>
									<th>배송상태</th>
									<td>
										<select id="" name="venDeliStatusCd" class="select">
											<option value="" >전체</option>
											<c:forEach items="${DE014List}" var="codeList">
											<option value="${codeList.MINOR_CD}" <c:if test="${codeList.MINOR_CD eq searchVO.venDeliStatusCd}">selected="selected"</c:if>>${codeList.CD_NM}</option>								
											</c:forEach>
										</select>
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

							<table class="bbs_list" cellpadding="0" cellspacing="0"border="0">
							<colgroup>
								<col style="width:10%" />
								<col style="width:20%" />
								<col style="width:6%" />
								<col style="width:10%" />
								<col style="width:10%" />
								<col style="width:10%" />
								<col style="width:10%" />
								<col style="width:14%" />
								<col style="width:10%" />
							</colgroup>
							<tr> 
								<th>주문번호</th>
								<th>상품명</th>
								<th>수량</th>
								<th>판매가</th>
								<th>결재금액</th>
								<th>배송상태</th>
								<th>업체코드</th>
								<th>운송장번호</th>
								<th>택배사</th>
							</tr>
<c:if test="${searchVO.flag == 'serach'}">							
	<c:set var="T_ORDER_ID"/>		
	<c:forEach var="list" items="${list}">
		<c:choose>					
		<c:when test="${list.ROWSPAN > 1}">
							<tr class="r1">
			<c:if test="${list.ORDER_ID != T_ORDER_ID}">					
								<td rowspan="${list.ROWSPAN}">
									<a href="javascript:showOrderDetail('${list.ORDER_ID}')">${list.ORDER_ID}</a>
								</td>
			</c:if>					
								<td style='text-align: left'>${list.PROD_NM}</td>
								<td><fmt:formatNumber value="${list.ORD_QTY}" pattern="#,##0" /></td>
								<td style='text-align: right'><fmt:formatNumber value="${list.TOT_SELL_AMT}" pattern="#,##0" /></td>
			<c:if test="${list.ORDER_ID != T_ORDER_ID}">											
								<td rowspan="${list.ROWSPAN}" style='text-align: right'><fmt:formatNumber value="${list.APPLY_TOT_AMT}" pattern="#,##0" /></td>
			</c:if>					
								<td>${list.VEN_DELI_STATUS_NM}</td>
								<td>${list.VEN_CD}</td>
								<td>${list.HODECO_INVOICE_NO}</td>
								<td>${list.HODECO_NM}</td>
							</tr>
		</c:when>
		<c:otherwise>
							<tr class="r1">
								<td>
									<a href="javascript:showOrderDetail('${list.ORDER_ID}')">${list.ORDER_ID}</a>
								</td>
								<td style='text-align: left'>${list.PROD_NM}</td>
								<td><fmt:formatNumber value="${list.ORD_QTY}" pattern="#,##0" /></td>
								<td style='text-align: right'><fmt:formatNumber value="${list.TOT_SELL_AMT}" pattern="#,##0" /></td>
								<td style='text-align: right'><fmt:formatNumber value="${list.APPLY_TOT_AMT}" pattern="#,##0" /></td>
								<td>${list.VEN_DELI_STATUS_NM}</td>
								<td>${list.VEN_CD}</td>
								<td>${list.HODECO_INVOICE_NO}</td>
								<td>${list.HODECO_NM}</td>
							</tr>		
		</c:otherwise>			
		</c:choose>		
		<c:set var="T_ORDER_ID" value="${list.ORDER_ID}"/>
	</c:forEach>						
	
	<c:if test="${empty list}">
							<tr class="r1">
								<td colspan="11"><spring:message code="msg.common.info.nodata"/></td>
							</tr>
	</c:if>	
</c:if>	
	
							</table>
						</div>
						<!-- 2검색내역 // -->
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
						<li class="last">주문목록조회</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->

	</div>

	<!--	@ BODY WRAP  END  	// -->

</body>
</html>