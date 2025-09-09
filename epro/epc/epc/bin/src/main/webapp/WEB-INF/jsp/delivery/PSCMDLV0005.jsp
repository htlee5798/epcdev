
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
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ include file="/common/scm/scmCommon.jsp" %>

<%
DecimalFormat df = new DecimalFormat("00");
Calendar currentCalendar = Calendar.getInstance();

currentCalendar.add(currentCalendar.DATE, -90);
String strYear31   = Integer.toString(currentCalendar.get(Calendar.YEAR));
String strMonth31  = df.format(currentCalendar.get(Calendar.MONTH) + 1);
String strDay31   = df.format(currentCalendar.get(Calendar.DATE));
String strDate31 = strYear31 + strMonth31 + strDay31;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:import url="/common/commonHead.do" />
<!-- delivery/PSCMDLV0005 -->
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
		//openCal('searchForm.endDate');
	});
	
	$('#excel').click(function(){
		downExcel();
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
	var form = document.searchForm;

	var getDiff = getDateDiff(form.startDate.value,form.endDate.value);
	
	var startDate = form.startDate.value.replace( /-/gi, '' );
	var endDate   = form.endDate.value.replace( /-/gi, '' );
		
	if(startDate>endDate){
		alert('시작일자가 종료일자보다 클수 없습니다.');
		return;
	}
	
	<%-- if(<%=strDate31%>>startDate){
		alert('시작일자보다 종료일자가 90일 이상 클수 없습니다.');
		return;
	} --%>
	
	if(getDiff>90){
		alert('시작일자보다 종료일자가 90일 이상 클수 없습니다.');
		return;
	}  
	
	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if (form.vendorId.value == "")
	    {
	        alert('업체선택은 필수입니다.');
	        form.vendorId.focus();
	        return;
	    }
		</c:when>
	</c:choose>
	
	var url = '<c:url value="/delivery/selectPartnerFirmsListStatus.do"/>';
	form.action = url;
	form.submit();
}

/** ********************************************************
 * 협력업체배송리스트 팝업
 ******************************************************** */
function popupPartnerFirmsForm(fromDate, toDate, toDate, delivCode, delivType) {
	var gridObj = document.WG1;
	var targetUrl = '<c:url value="/delivery/selectPartnerFirms.do"/>?';
	targetUrl += 'fromDate=' + fromDate;
	targetUrl += 'toDate=' + toDate;
	targetUrl += 'toDate=' + toDate;
	targetUrl += 'delivCode=' + delivCode;
	targetUrl += 'delivType=' + delivType;
	
	Common.centerPopupWindow(targetUrl, 'PartnerFirms', {width : 800, height : 180});
}


/** ********************************************************
 * 협력업체배송리스트이동
 * deliStatusCd : 배송상태
 * deliTypeCd : 배송형태코드
 ******************************************************** */
function goMove(deliStatusCd, deliTypeCd){
	var form = document.searchForm;
	
	form.deliStatusCode.value = deliStatusCd;
	form.deliTypeCd.value = deliTypeCd;
	
	//var url = '<c:url value="/delivery/selectPartnerFirmsDeliList.do"/>';
	var url = '<c:url value="/delivery/selectPartnerFirmsOrderList.do"/>';
	
	form.action = url;

	form.submit();
}

function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
	$("#vendorId").val(strVendorId);
}

function downExcel(){
	var form = document.searchForm;
	var url = '<c:url value="/delivery/downloadDestructionDocExcel.do"/>';
	form.action = url;

	form.submit();
}

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<c:set var="startDate" value='<%=strDate31%>' />

<div id="content_wrap">

	<div class="content_scroll">
	
	<form name="searchForm" method="post">
	
		<input type="hidden" name="deliStatusCode" value=""/>
		<input type="hidden" name="deliTypeCd" value=""/>
		<input type="hidden" name="saleStsCd" value="11"/>
		<input type="hidden" name="dateGbn" value="1"/>
		
		<div id="wrap_menu">
		
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">조회조건</li>
						<li class="btn">
							<a href="javascript:doSearch();" class="btn" ><span><spring:message code="button.common.inquire"/></span></a>
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
						<th><span class="star">*</span> 주문일자</th>
						<td>						
							<input type="text" name="startDate" id="startDate" class="day" readonly style="width:33%;" value="${searchVO.startDate}" /><a href="javascript:openCal('searchForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
							<%--<input type="text" name="startDate" id="startDate" class="day" readonly style="width:33%;" value="<c:out value="${fn:substring(startDate,0,4)}-${fn:substring(startDate,4,6)}-${fn:substring(startDate,6,8)}" />" /><a href="javascript:openCal('searchForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>--%> 
							~
							<input type="text" name="endDate" id="endDate" class="day" readonly style="width:33%;" value="${searchVO.endDate}" /><a href="javascript:openCal('searchForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
						</td>
						<th>협력업체코드</th>
						<td>
							<c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<c:if test="${not empty vendorId}">
											<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="${vendorId}" style="width:40%;"/>
										</c:if>	
										<c:if test="${empty vendorId}">
											<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
										</c:if>	
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<select name="vendorId" id="vendorId" class="select">
											<option value="">전체</option>
										<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
										
											<c:if test="${not empty vendorId}">
												<option value="${venArr}" <c:if test="${venArr eq vendorId}">selected</c:if>>${venArr}</option>
											</c:if>	
											<c:if test="${empty vendorId}">
												<option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
											</c:if>	
					                        
										</c:forEach>
										</select>
									</c:when>
						</c:choose>
						</td>
						<td></td>
						<th></th>
						<td></td>
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
						<li class="btn">
							<a href="#" class="" id="excel"><span>배송지 정보 확인 파기서 <font style="color: red;"> (현재 총 누적: ${ totalOrdCnt})</font></span></a>
						</li>
					</ul>
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:10%" />
						<col style="width:9%" />
						<col style="width:9%" />
						<col style="width:9%" />
						<col style="width:9%" />
						<col style="width:9%" />
						<col style="width:9%" />
						<col style="width:9%" />
						<col style="width:9%" />
						<col style="width:9%" />
						<col style="width:9%" />
					</colgroup>
					<tr> 
						<th>구분</th>
						<th>미확인</th>
						<th>발송불가</th>
						<th>발송예정</th>
						<th>배송중</th>
						<th>배송지연</th>
						<th>배송완료</th>
						<th>발송취소</th>
						<th>반품회수확인</th>
						<th>반품회수완료</th>
						<th>교환지시</th>
					</tr>
					<c:forEach var="result" items="${list}">
					<tr>
						<td><c:out value="${result.VD_TEXT }"/></td>
						<td style='text-align: right'><font color="blue"><a href="javascript:goMove('01', '<c:out value="${result.DELI_TYPE_CD }"/>')">
							<fmt:formatNumber value="${result.VD_01_SUM}" pattern="#,##0" /></font>
						</a>&nbsp;&nbsp;</td>
						<td style='text-align: right'><font color="blue"><a href="javascript:goMove('02', '<c:out value="${result.DELI_TYPE_CD }"/>')">
							<fmt:formatNumber value="${result.VD_02_SUM}" pattern="#,##0" /></font>
						</a>&nbsp;&nbsp;</td>
						<td style='text-align: right'><font color="blue"><a href="javascript:goMove('03', '<c:out value="${result.DELI_TYPE_CD }"/>')">
							<fmt:formatNumber value="${result.VD_03_SUM}" pattern="#,##0" /></font>
						</a>&nbsp;&nbsp;</td>
						<td style='text-align: right'><font color="blue"><a href="javascript:goMove('09', '<c:out value="${result.DELI_TYPE_CD }"/>')">
							<fmt:formatNumber value="${result.VD_09_SUM}" pattern="#,##0" /></font>
						</a>&nbsp;&nbsp;</td>						
						<td style='text-align: right'><font color="blue"><a href="javascript:goMove('07', '<c:out value="${result.DELI_TYPE_CD }"/>')">
							<fmt:formatNumber value="${result.VD_10_SUM}" pattern="#,##0" /></font>
						</a>&nbsp;&nbsp;</td>
						<td style='text-align: right'><font color="blue"><a href="javascript:goMove('05', '<c:out value="${result.DELI_TYPE_CD }"/>')">
							<fmt:formatNumber value="${result.VD_05_SUM}" pattern="#,##0" /></font>
						</a>&nbsp;&nbsp;</td>
						<td style='text-align: right'><fmt:formatNumber value="${result.VD_04_SUM}" pattern="#,##0" /></font>&nbsp;&nbsp;&nbsp;</td>
						<td style='text-align: right'><fmt:formatNumber value="${result.VD_06_SUM}" pattern="#,##0" />&nbsp;&nbsp;&nbsp;</td>
						<td style='text-align: right'><fmt:formatNumber value="${result.VD_07_SUM}" pattern="#,##0" /></font>&nbsp;&nbsp;&nbsp;</td>
						<td style='text-align: right'><fmt:formatNumber value="${result.VD_08_SUM}" pattern="#,##0" /></font>&nbsp;&nbsp;&nbsp;</td>
					</tr>
					</c:forEach>
					<c:if test="${not empty map}">
					<tr>
						<td><c:out value="${map.VD_TEXT }"/></td>
						<td style='text-align: right'><fmt:formatNumber value="${map.VD_01_SUM}" pattern="#,##0" />&nbsp;&nbsp;&nbsp;</td>
						<td style='text-align: right'><fmt:formatNumber value="${map.VD_02_SUM}" pattern="#,##0" />&nbsp;&nbsp;&nbsp;</td>
						<td style='text-align: right'><fmt:formatNumber value="${map.VD_03_SUM}" pattern="#,##0" />&nbsp;&nbsp;&nbsp;</td>
						<td style='text-align: right'><fmt:formatNumber value="${map.VD_09_SUM}" pattern="#,##0" />&nbsp;&nbsp;&nbsp;</td>
						<td style='text-align: right'><fmt:formatNumber value="${map.VD_10_SUM}" pattern="#,##0" />&nbsp;&nbsp;&nbsp;</td>
						<td style='text-align: right'><fmt:formatNumber value="${map.VD_05_SUM}" pattern="#,##0" />&nbsp;&nbsp;&nbsp;</td>
						<td style='text-align: right'><fmt:formatNumber value="${map.VD_04_SUM}" pattern="#,##0" />&nbsp;&nbsp;&nbsp;</td>
						<td style='text-align: right'><fmt:formatNumber value="${map.VD_06_SUM}" pattern="#,##0" />&nbsp;&nbsp;&nbsp;</td>
						<td style='text-align: right'><fmt:formatNumber value="${map.VD_07_SUM}" pattern="#,##0" />&nbsp;&nbsp;&nbsp;</td>
						<td style='text-align: right'><fmt:formatNumber value="${map.VD_08_SUM}" pattern="#,##0" />&nbsp;&nbsp;&nbsp;</td>					
					</tr>			
					</c:if>			
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
					<li class="last">협력업체배송현황</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->

</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>