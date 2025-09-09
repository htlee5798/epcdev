<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%--@page import="java.util.Date"--%>
<%@page import="java.util.List"%>
<%@page import="com.lottemart.common.util.DataMap"%>
<%--@ page import="lcn.module.common.util.DateUtil" --%>
<%@ page import="com.lcnjf.util.DateUtil" %>
<%
List<DataMap> popupList = (List<DataMap>)request.getAttribute("popupList");
//boolean isOld = true;
//String toDate   = DateUtil.addDay(DateUtil.getToday(), 0);
//String nextDate = DateUtil.addDay(DateUtil.getToday(), 1);
//String returnDeliNextDate = DateUtil.addDay(DateUtil.getToday(), 1);

//int returnDeliToDate = Integer.parseInt(toDate);
//if (returnDeliToDate < 20200428) {
//	returnDeliNextDate ="20200428";
//}

//Date today = new Date();
//boolean isLcn = DateUtil.greaterThan(today, "2021-10-27 20:00:00", "yyyy-MM-dd HH:mm:ss"); // 2021-09-30 20:00:00 부터 로그인URL을 LCN으로 변경
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/epc/main.css" type="text/css">
<style>.personalInfoPolicy{float:left; padding-left: 15px;}</style>
<script type="text/javascript" language="javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" language="javascript" src="/js/jquery/jquery-1.6.2.min.js"></script>
<title>롯데마트 입점상담</title>

<script type="text/javascript">
(function(){ 
    /*Use Object Detection to detect IE6*/
    var m = document.uniqueID /*IE*/
    && document.compatMode /*>=IE6*/
    && !window.XMLHttpRequest /*<=IE6*/
    && document.execCommand ;

    try{
        if(!!m){
            m("BackgroundImageCache", false, true) /* = IE6 only */
        }
    }catch(oh){};
})();

function goForm(gubn) {
	var form = document.mForm;
	var url = "${lfn:getString('login.sdomain.url')}";

	if( gubn == "A") {
		url += "/common/viewEpcAdmLoginForm.do";
	} else if( gubn == "C" ) {
		url = '<c:url value="/common/epcVendorLogin.do"/>';
	} else if( gubn == "S" ) {
		url = '<c:url value="/index.jsp"/>';
	} else if( gubn == "L" ) {
	<%-- if (isLcn) { --%>
		url = "${lfn:getString('epc.lcn.login.url')}";
	<%-- } else { --%>
	<%-- url = "${lfn:getString('epc.lcn.login.old.url')}"; --%>
	<%-- } --%>
	} else if( gubn == "H" ) {
		url += "/common/viewHappyLoginForm.do";
	} else if( gubn == "J" ) {
		url += "/common/viewAllianceLoginForm.do";
	}

	form.action=url;
	form.submit();
}

	/** ********************************************************
	 * 최초페이지 오픈시 팝업 공지
	 ******************************************************** */
function popup_window() {
	var checkCookie;
<% 
	for ( int i = 0; i < popupList.size(); i++ ) {
		DataMap dataMap = (DataMap)popupList.get(i);
%>
	checkCookie=getCookie("board_<%=dataMap.getString("BOARD_SEQ")%>");  // 쿠키네임 지정

	if(checkCookie != "noOpen" ) {
		var url ='<c:url value="/main/popUpintro.do"/>?boardSeq=<%=dataMap.getString("BOARD_SEQ")%>';  /* 팝업창 주소 */
		centerPopupWindowTmp(url, 'epcPopupBoard'+<%=i%>, {width:600, height:300 , scrollBars : 'YES'},<%=i%>);
	}
<%
	}
%>
}

function centerPopupWindowTmp(targetUrl, windowName, properties, counts) {
	var childWidth = properties.width;
	var childHeight = properties.height;
	var childTop = (screen.height - childHeight) / 2 - 50; <%-- 아래가 가리는 경향이 있어서 50을 줄임 --%>
	var childLeft = ((screen.width - childWidth) / 2) + 50 * counts;
	var popupProps = "width=" + childWidth + ",height=" + childHeight + ", top=" + childTop + ", left=" + childLeft;
	if (properties.scrollBars == "YES") {
		popupProps += ", scrollbars=yes";
	}

	window.open(targetUrl, windowName, popupProps);
}

/** ********************************************************
* 더보기 팝업링크
******************************************************** */
function doMorePopup() {
	var url ='<c:url value="/board/selectBoardList.do"/>'; /* 팝업창 주소 */
	Common.centerPopupWindow(url, 'epcSearchPopup', {width : 790, height : 590});
}

/** ********************************************************
 * 상세보기 팝업링크
 ******************************************************** */
function doPopupDetail(board_seq) {
	var url = '<c:url value="/board/selectDetailPopup.do"/>?boardSeq='+board_seq; /* 팝업창 주소 */
	Common.centerPopupWindow(url, 'epcDetailPopup', {width : 600, height : 400, scrollBars : 'YES'});
}

function goConsult() {
	var url = '<c:url value="/epc/edi/consult/NEDMCST0310login.do"/>'; /* 팝업창 주소 */
	Common.centerPopupWindow(url, 'ediConsult', {width : 913, height : 768,  scrollBars :"YES"});
}

/* 입점상담 */
function goConsult2() {
	var url ='<c:url value="/edi/srm/SRMSTP0010.do"/>'; /* 팝업창 주소 */
	window.open(url, "JON");
	//Common.centerPopupWindow(url, 'ediConsult2', {width : 913, height : 768,  scrollBars :"YES"});
}

	/* 품질경영평가 팝업 */
function goConsult3() {
	var url ='<c:url value="/edi/evl/SRMEVL0010.do"/>'; /* 팝업창 주소 */
	window.open(url, "EVL");
	//Common.centerPopupWindow(url, 'ediConsult3', {width : 913, height : 768,  scrollBars :"YES"});
}

/* 비윤리/불공정거래 신고 팝업 */
function cyberPopup() {
  //PopupWindow("<c:url value='/edi/consult/PEDPCST0011.do'/>");
  var url ='http://company.lottemart.com/abide/compliance.asp'; /* 팝업창 주소 */
  Common.centerPopupWindow(url, 'cyberpopup', {width : 1200, height : 600, scrollBars : 'YES', resizable : 'YES'});
}

function getCookie(name) {
	var arg = name + "=";
	var alen = arg.length;
	var clen = document.cookie.length;
	var i = 0;
	while (i < clen) { //while open

		var j = i + alen;
		if (document.cookie.substring(i, j) == arg)
			return getCookieVal(j);
		i = document.cookie.indexOf(" ", i) + 1;
		if (i == 0) break;
	} //while close
	return false;
}

function getCookieVal(offset) {
	var endstr = document.cookie.indexOf (";", offset);
	if (endstr == -1) endstr = document.cookie.length;
	return unescape(document.cookie.substring(offset, endstr));
}

function personalInfoPolicy() {
	var url ='<c:url value="/main/personalInfoPolicy.do"/>';
	Common.centerPopupWindow(url, 'personalInfoPolicy', {width : 1030, height : 700, scrollBars : 'YES'});
}

</script>
</head>
<!--  <body onload="popup_window()">-->
<body>
<form name="mForm">
<div id="wrap">
	<div id="con_wrap">

		<div class="con_area">
			<div id="contents">
				<div id="head">
					<h1><img src="${lfn:getString('system.cdn.static.path')}/images/epc/h1-logo-lottemart.gif" alt="행복드림-롯데마트"></h1>
				</div>
				<!-- 롯데마트 협력회사 서비스 -->
				<div class="intro">
					<img src="${lfn:getString('system.cdn.static.path')}/images/epc/tit-main-svc.gif" alt="롯데마트 협력회사 서비스 Collaboration & Value Creation">
				</div>
				<!-- 뉴스 & 공지사항 list 최대 5줄 출력 -->
				<div class="notic">
					<h3>뉴스 &amp; 공지사항</h3>
					<ul>
<c:forEach var="result" items="${list}" varStatus="status">
						<li><a href="javascript:doPopupDetail('${result.BOARD_SEQ}')">${result.REG_DATE}&nbsp;&nbsp;&nbsp;${result.TITLE}</a></li>
</c:forEach>
					</ul>
					<span class="more"><a href="javascript:doMorePopup()"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/btn-more.gif" alt="더보기"></a></span>
				</div>
				<!-- 배너 -->
				<div class="bnr_area">
					<a href="http://www.safebill.co.kr/etax/index.jsp" target="_blank"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/banner/bnr-main-invoice.gif" alt="세금 계산서"></a>
					<a href="http://winwin.lottemart.com/main/main.do" target="_blank"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/banner/bnr-main-growing.gif" alt="동반 성장"></a>
					<a href="http://www.lcn.co.kr/" target="_blank"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/banner/bnr-main-lcn.gif" alt="LCN"></a>
				</div>
			</div>
			<div id="quick_area">
				<!-- login -->
				<div class="login_box">
					<ul>
						<c:if test="${codeList[0].LET_1_REF eq 'Y'}">
							<li onClick="goForm('L');"><p class="red">협력사 로그인</p></li>
						</c:if>
						<c:if test="${codeList[0].LET_2_REF eq 'Y'}">
							<li onClick="goForm('H');"><p class="wred">명절해피콜</p></li>
						</c:if>
						<c:if test="${codeList[0].LET_3_REF eq 'Y'}">
							<li onClick="goForm('J');"><p class="wred">제휴사 로그인</p></li>
						</c:if>
					</ul>
				</div>
				<!-- // login -->
				<!-- button -->
				<ul class="advice_area">
					<!-- <li onClick="goConsult();"><p>입점상담 신청(지원)</p></li> -->
					<li onClick="goConsult2();"><p>입점상담 신청</p></li>
					<li onClick="goConsult3();"><p>품질경영평가</p></li>
					<li onClick="cyberPopup();"><p>비윤리/불공정거래 신고</p></li>
				</ul>
				<!-- // button -->
			</div>
		</div>
		<div id="footer">
			<p class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/footer/footer-logo-mlottemart.gif" alt="행복드림-롯데마트"></p>
			<p class="address"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/footer/footer-copyright.gif" alt="copyright&copy; LOTTEMART.COM. ALL RIGHTS RESERVED."></p>
			<p class="personalInfoPolicy"><strong><a href="javascript:personalInfoPolicy();">개인정보처리방침</a></strong></p>
		</div>
	</div>
</div>
<script type="text/javascript" language="javascript" src="/js/front/front_common.js"></script>
</form>
</body>
</html>