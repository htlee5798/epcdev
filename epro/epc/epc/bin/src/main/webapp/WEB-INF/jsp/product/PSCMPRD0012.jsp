<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ page import="lcn.module.common.util.StringUtil"%>
<%@ page import="com.lottemart.common.util.DataMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
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
<script type="text/javascript" src="/js/wisegrid/WiseGridTag-dev.js" ></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>

<script type="text/javascript">
/** ********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function goSearch(month){
	var form = document.adminForm;
	form.currentMonth.value = month;
	var url = '<c:url value="/product/selectCalendarScheduleList.do"/>';
	form.action = url;
	
	loadingMask();
	form.submit();
}

function doSearch() {
	var form = document.adminForm;
	var url = '<c:url value="/product/selectCalendarScheduleList.do"/>';
	form.action = url;
	
	loadingMask();
	form.submit();
}

function goListView(){
	var form = document.adminForm;
	var url = '<c:url value="/product/selectScheduleMgrView.do"/>';
	form.action = url;
	
	loadingMask();
	form.submit();
}

/** ********************************************************
 * 상세 팝업 함수
 ******************************************************** */
function goScheduleDetailPopup(seq) {
	var url = '<c:url value="/product/selectSchedulePopup.do"/>?pageType=cal&scdlSeqs='+seq;
	Common.centerPopupWindow(url, 'detail', {width : 800, height : 320});
}

/** ********************************************************
 * 등록 팝업 함수
 ******************************************************** */
function goScheduleAddPopup() {
	var targetUrl = '<c:url value="/product/insertSchedulePopupView.do"/>?pageType=cal';
	Common.centerPopupWindow(targetUrl, 'insert', {width : 800, height : 320});
}

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<div id="content_wrap">

<div class="content_scroll">

<form name="adminForm">

<input type="hidden" name="currentMonth" />
<div id="wrap_menu">

	<!--	2 검색내역 	-->
	<div class="wrap_con">
		<!-- list -->
			
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">
				<img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_first.gif" onclick="javascript:goSearch('${beforeYear}')" />
				<img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_prev.gif" onclick="javascript:goSearch('${beforeMonth}')" />
				${fn:substring(currentMonth,0,4)}년 ${fn:substring(currentMonth,4,6)}월
				<img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_next.gif" onclick="javascript:goSearch('${afterMonth}')" />
				<img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_end.gif" onclick="javascript:goSearch('${afterYear}')" />
				</li>
				<li class="btn">
					<a href="javascript:goScheduleAddPopup();" class="btn" ><span>스케쥴추가</span></a>
					<a href="javascript:goListView();" class="btn" ><span>리스트로 보기</span></a>
				</li>
			</ul>
  
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="13%">
				<col width="13%">
				<col width="13%">
				<col width="13%">
				<col width="13%">
				<col width="13%">
				<col width="13%">
				<col width="13%">
			</colgroup>
			<tr>
				<th class="fst"><b><font color="red">일</font></b></th>
				<th><b>월</b></th>
				<th><b>화</b></th>
				<th><b>수</b></th>
				<th><b>목</b></th>
				<th><b>금</b></th>
				<th><b><font color="blue">토</font></b></th>
			</tr>
			<%
				String currentMonth = (String)request.getAttribute("currentMonth");
				List list = (List)request.getAttribute("list");
				int year = Integer.parseInt(currentMonth.substring(0, 4));
				int month = Integer.parseInt(currentMonth.substring(4, 6));
 				GregorianCalendar calendar = new GregorianCalendar(year, (month - 1), 1);  // 년, 월을 구함
				int maxday = calendar.getActualMaximum((calendar.DAY_OF_MONTH));  // 마지막 날을 구함
				int week = (calendar.get(Calendar.DAY_OF_WEEK));  // 주를 구함
				if (week == 1) {
					week = 8;
				}

				if(week != 8) {
					out.println("<tr class='r1'>");
					out.println("<td height='60'></td>");
					for(int i = 1; i < (week - 1); i++) {
						out.println("<td height='60'></td>");
					}
				}
				for (int i = 1;i <= maxday;i++) {
					if (week <= 7) {
						out.println("<td height='60'>");
						out.println(i+"</br>");
						
						// 해당 날짜의 스케쥴 데이터 출력
						for(int j = 0; j < list.size(); j++) {
							String day = currentMonth + StringUtil.leftPad(i+"", 2, "0");
							DataMap map = (DataMap)list.get(j);
							
							if(day.equals(map.getString("RSERV_START_DY"))) {
								out.print(map.getString("RSERV_START_TM").substring(0, 2)+":"+map.getString("RSERV_START_TM").substring(2, 4));
								out.print("~");
								out.println(map.getString("RSERV_END_TM").substring(0, 2)+":"+map.getString("RSERV_END_TM").substring(2, 4));
								out.println("<a href='#' onclick='goScheduleDetailPopup(\""+map.getString("SCDL_SEQS")+"\")'>");
								out.println(map.getString("VENDOR_NM")+"</a></br>");
							}
						}
						
						out.println("</td>");
					} else {
						out.println("</tr>");
						out.println("<tr class='r1'>");
						out.println("<td height='60'>");
						out.println(i+"</br>");

						// 해당 날짜의 스케쥴 데이터 출력
						for(int j = 0; j < list.size(); j++) {
							String day = currentMonth + StringUtil.leftPad(i+"", 2, "0");
							DataMap map = (DataMap)list.get(j);
							
							if(day.equals(map.getString("RSERV_START_DY"))) {
								out.print(map.getString("RSERV_START_TM").substring(0, 2)+":"+map.getString("RSERV_START_TM").substring(2, 4));
								out.print("~");
								out.println(map.getString("RSERV_END_TM").substring(0, 2)+":"+map.getString("RSERV_END_TM").substring(2, 4));
								out.println("<a href='#' onclick='goScheduleDetailPopup(\""+map.getString("SCDL_SEQS")+"\")'>");
								out.println(map.getString("VENDOR_NM")+"</a></br>");
							}
						}
						
						out.println("</td>");
						week = 1; // 다시 월요일부터 시작     
					}
					week++;
				}
				for(int i = 0; i <= 7 - week; i++) {
					out.println("<td height='60'></td>");
				}
			%>
			</tr>
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
					<li>상품관리</li>
					<li class="last">상품이미지촬영스케쥴 달력</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>