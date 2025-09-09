<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.lottemart.common.util.DataMap"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	List<DataMap> popupList = (List<DataMap>)request.getAttribute("popupList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/epc/main.css" type="text/css" />
<script type="text/javascript" language="javascript" src="/js/jquery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" language="javascript" src="/js/jquery/libs/jquery.min.js"></script>
<script type="text/javascript" language="javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" language="javascript" src="/js/epc/jquery-ui.min.js"></script>
<style type="text/css">
#layerPopup0{overflow:hidden; height:auto; display:block; border-radius:3px; width:680px; z-index:100; position:absolute; top:10%; left:11%;}
#layerPopup0 h4{background:#659fd5; font-size:20px; height:24px;  padding:10px 0 10px 15px ; color:#FFF; font-weight:normal;}
#layerPopup0 h4 a.close{float:right; padding-right:8px; color:#FFF; font-family:verdana}
#layerPopup0:hover{cursor:move;}
#layerPopup1{overflow:hidden; height:auto; display:block; border-radius:3px; width:680px; z-index:100; position:absolute; top:10%; right:11%;}
#layerPopup1 h4{background:#659fd5; font-size:20px; height:24px;  padding:10px 0 10px 15px ; color:#FFF; font-weight:normal;}
#layerPopup1 h4 a.close{float:right; padding-right:8px; color:#FFF; font-family:verdana}
#layerPopup1:hover{cursor:move;}
#layerPopup2{overflow:hidden; height:auto; display:block; border-radius:3px; width:720px; z-index:100; position:absolute; top:20%; right:18%;}
#layerPopup2 h4{background:#659fd5; font-size:20px; height:24px;  padding:10px 0 10px 15px ; color:#FFF; font-weight:normal;}
#layerPopup2 h4 a.close{float:right; padding-right:8px; color:#FFF; font-family:verdana}
#layerPopup2:hover{cursor:move;}
.popupCont0{display:block; background: #FFF; border:2px solid #659fd5; padding:15px 20px; height:300px; }
.popupCont0 li{height:100px; width:100%; display:inline-block; margin:0; font-size:20px; line-height:25px; }
.popupCont1{display:block; background: #FFF; border:2px solid #659fd5; padding:15px 20px; height:260px; }
.popupCont1 li{height:100px; width:100%; display:inline-block; margin:0; font-size:20px; line-height:25px; }
.popupCont2{display:block; background: #FFF; border:2px solid #659fd5; padding:15px 20px; height:450px; }
.popupCont2 li{height:100px; width:100%; display:inline-block; margin:0; font-size:20px; line-height:25px; }
.personalInfoPolicy{float:left; padding-left: 15px;}
</style>
<title>롯데마트 파트너 시스템</title>
<script type="text/javascript">
var now = new Date(); 
var year = now.getFullYear(); 
var month = (now.getMonth()+1); //월은 0부터시작
var date = now.getDate(); 
var YEAR=Number(year*10000);
var MONTH=Number(month*100);
var DATE=Number(date*1);
var today=YEAR+MONTH+DATE;

(function() {
	/*Use Object Detection to detect IE6*/
	var m = document.uniqueID /*IE*/
	&& document.compatMode /*>=IE6*/
	&& !window.XMLHttpRequest /*<=IE6*/
	&& document.execCommand ;

	try{
		if (!!m) {
			m("BackgroundImageCache", false, true) /* = IE6 only */
		}
	}catch(oh) {};
})();


$(document).ready(function($) {

	/*if ("${T}"!=null && "${AD_01}"!=0 || "${AD_02}"!=0) {
		alert('배송 미확인건: '+"${AD_01}"+'건'+'\n'+'배송 예정건: '+"${AD_02}"+'건'+'이 있습니다.');
	}*/

	/*layer pupup 추가시 해당 ID값 참조하여 마우스 드래그 기능*/
	for(var i = 0; i < 3; i++) {
		var layer = document.getElementById("layerPopup" + i);
		if (layer != null && layer != '') {
			$("#layerPopup" + i).draggable();
		}
	}

	//위수탁 업체 로그인 시 미처리 문의건 확인
	if ("${CHK_01}" != 0) {
		var today = new Date();
		var yyyy = today.getFullYear();
		var mm = today.getMonth() + 1;
		var dd = today.getDate();

		if (mm < 10) { mm = '0' + mm; }
		if (dd < 10) { dd = '0' + dd; }

		var strDate = yyyy + "-" + mm + "-01";
		var endDate = yyyy + "-" + mm + "-" + dd;
		alert('                       ※ 미처리 문의건 : ' + "${CHK_01}" + '건\n                  [' + strDate + ' ~ ' + endDate +']\n→ 확인방법 : SCM - 게시판관리 - 게시판관리 - 상품Q&A게시판');
	}

	//업체 주문배송비관리
	if ("${CHK_02}" == 'N') {
		alert('★필독/주의사항★  \n [배송비/배송정보 미설정 파트너사입니다] \n 배송비,반품배송비 정보 등록은 [SCM→시스템관리→업체정보관리] 탭에서 설정 가능합니다. \n ※※※ [주문배송비관리] 미설정 시, 묶음배송이 안되오니 필수 등록 해주시기 바랍니다.');
	}

	<%--var checkCookieHC11;
	checkCookieHC11=getCookie("popcookie201401140");  // 쿠키네임 지정
	if (checkCookieHC11 != 'popcookie201401140' ) {
		if 	( 20161123<=today && today<=20161215) {
			popup_windowFornt201401140();
		}
	}

	//20160912 이동빈
	var checkCookieHC2;//2014년 명절 윤리실첨 캠페인
	checkCookieHC2=getCookie("popcookie20140825");  // 쿠키네임 지정
	if (checkCookieHC2 != 'popcookie20140825' ) {
		if ( 20160911<=today && today<=20161029) {
			popup_windowFront20150205();
		}
	}

	checkCookieHC12=getCookie("popcookie20151203");  // 쿠키네임 지정
	if (checkCookieHC12 != 'popcookie20151203' ) {
		//	popup_windowFront20151203();
	}

	// 2016 02 11 팝업 수정
	var checkCookieHC13;
	checkCookieHC13 = getCookie("popcookie20160119");
	if (checkCookieHC13 != 'popcookie20160119' ) {
		if ( 20160906<=today && today<=20160912) {
			popup_windowFront20160119();
		}
	}

	// 2017 02 16  팝업 수정
	var checkCookieHC16;
	checkCookieHC16 = getCookie("popcookie20170216");
	if (checkCookieHC16 != 'popcookie20170216' ) {
		if ( 20170125<=today && today<=20170306) {
			popup_windowFront20170216();
		}
	}

	// 2017 03 13  팝업 수정
	var checkCookieHC17;
	checkCookieHC17 = getCookie("popcookie20170313");
	if (checkCookieHC17 != 'popcookie20170313' ) {
		if ( 20170313<=today && today<=20170531) {
			//popup_windowFront20170313();
		}
	}--%>

	var layerPopup0;
	layerPopup0=getCookie("layerPopup0");  // 쿠키네임 지정
	if (layerPopup0 == 'done' ) {
		document.all.layerPopup0.style.visibility="visible";
	}

	var layerPopup1;
	layerPopup1=getCookie("layerPopup1");  // 쿠키네임 지정
	if (layerPopup1 == 'done' ) {
		document.all.layerPopup1.style.visibility="visible";
	}

	var layerPopup2;
	layerPopup2=getCookie("layerPopup2");  // 쿠키네임 지정
	if (layerPopup2 == 'done' ) {
		document.all.layerPopup2.style.visibility="hidden";
	}

	/*********************************
	* G-fair 기간 체크
	*********************************/
	//gfairCheck();

	/*********************************
	* 이미지 리사이즈 변경 공지 기간 체크
	*********************************/
	//imgNoticCheck();

	/*********************************
	* 로그인 시, 메인화면에 공지사항 팝업 바로 노출
	*********************************/
	popup_windowFront20151203();
	popup_windowFront20170313();
	popup_window();

	//수수료율 변경요청 팝업 삭제
	//popupOpenAddress();
	//layerPopUp();

});
<%--
function popup_windowFront20151203() {
	var WindowWidth12 = 480;
	var WindowHeight12 = 220;
	var WindowLeft12 = (screen.width - WindowWidth12)/3;
	var WindowTop12= (screen.height - WindowHeight12)/2;
	var openUrl12 = "<c:url value='/epc/edi/consult/selectFront0012ImgPopup.do'/>";
	var popTitle12 = "_newCodes12";
	NewWin12 = window.open(openUrl12, popTitle12, "titlebar=no, resizable=0, width="+WindowWidth12+", height="+WindowHeight12+", top="+WindowTop12+", left="+WindowLeft12);
	NewWin12.focus();
}

function popup_windowFront20170313() {
	var WindowWidth17 = 520;
	var WindowHeight17 = 675;
	var WindowLeft17 = (screen.width - WindowWidth17)/3;
	var WindowTop17= (screen.height - WindowHeight17)/2;
	var openUrl17 = "<c:url value='/epc/edi/consult/selectFront0017ImgPopup.do'/>";
	var popTitle17 = "_newCodes17";
	NewWin17 = window.open(openUrl17, popTitle17, "titlebar=no, resizable=0, scrollbars=yes, width="+WindowWidth17+", height="+WindowHeight17+", top="+WindowTop17+", left="+WindowLeft17);
	NewWin17.focus();
}
--%>
<%--
function popup_windowFornt201305280() {
	var WindowWidth8 = 410;
	var WindowHeight8 = 575;
	var WindowLeft8 = (screen.width - WindowWidth8)/4;
	var WindowTop8= (screen.height - WindowHeight8)/2;
	var openUrl8 = "<c:url value='/epc/edi/consult/selectFront0008ImgPopup.do'/>";
	var popTitle8 = "_newCodes8";
	NewWin8 = window.open(openUrl8, popTitle8, "titlebar=no, resizable=1, width="+WindowWidth8+", height="+WindowHeight8+", top="+WindowTop8+", left="+WindowLeft8);
	NewWin8.focus();
}

function popup_windowFornt201308280() {
	var WindowWidth9 = 400;
	var WindowHeight9 = 575;
	var WindowLeft9 = (screen.width - WindowWidth9)/3;
	var WindowTop9= (screen.height - WindowHeight9)/2;
	var openUrl9 = "<c:url value='/epc/edi/consult/selectFront0009ImgPopup.do'/>";
	var popTitle9 = "_newCodes9";
	NewWin9 = window.open(openUrl9, popTitle9, "titlebar=no, resizable=1, width="+WindowWidth9+", height="+WindowHeight9+", top="+WindowTop9+", left="+WindowLeft9);
	NewWin9.focus();
}

function popup_windowFornt201309290() {
	var WindowWidth10 = 460;
	var WindowHeight10 = 575;
	var WindowLeft10 = (screen.width - WindowWidth10)/3;
	var WindowTop10= (screen.height - WindowHeight10)/2;
	var openUrl10 = "<c:url value='/epc/edi/consult/selectFront0010ImgPopup.do'/>";
	var popTitle10 = "_newCodes10";
	NewWin10 = window.open(openUrl10, popTitle10, "titlebar=no, resizable=1, width="+WindowWidth10+", height="+WindowHeight10+", top="+WindowTop10+", left="+WindowLeft10);
	NewWin10.focus();
}

 function popup_windowFornt201401140() {
	var WindowWidth11 = 470;
	var WindowHeight11 = 650;
	var WindowLeft11 = (screen.width - WindowWidth11)/3;
	var WindowTop11= (screen.height - WindowHeight11)/2;
	var openUrl11 = "<c:url value='/epc/edi/consult/selectFront0011ImgPopup.do'/>";
	var popTitle11 = "_newCodes11";
	NewWin11 = window.open(openUrl11, popTitle11, "titlebar=no, resizable=1, width="+WindowWidth11+", height="+WindowHeight11+", top="+WindowTop11+", left="+WindowLeft11);
	NewWin11.focus();
} 

//이동빈 20160912

function popup_windowFront20150205() {
	var WindowWidth = 450;
	var WindowHeight = 650;
	var WindowLeft = (screen.width - WindowWidth)/3;
	var WindowTop= (screen.height - WindowHeight)/2;
	var openUrl = "<c:url value='/epc/edi/consult/NEDMCST0310selectFront0002ImgPopup.do'/>";
	var popTitle = "_newCodes2";
	NewWin2 = window.open(openUrl, popTitle, "titlebar=no, resizable=1, width="+WindowWidth+", height="+WindowHeight+", top="+WindowTop+", left="+WindowLeft);
	//NewWin2.focus(); 
}

function popup_windowFront20160119() {
	var WindowWidth13 = 480;
	var WindowHeight13 = 726;
	var WindowLeft13 = (screen.width - WindowWidth13)/3;
	var WindowTop13= (screen.height - WindowHeight13)/2;
	var openUrl13 = "<c:url value='/epc/edi/consult/selectFront0013ImgPopup.do'/>";
	var popTitle13 = "_newCodes13";
	NewWin13 = window.open(openUrl13, popTitle13, "titlebar=no, resizable=0, width="+WindowWidth13+", height="+WindowHeight13+", top="+WindowTop13+", left="+WindowLeft13);
	NewWin13.focus();
}

function popup_windowFront20170216() {
	var WindowWidth16 = 500;
	var WindowHeight16 = 675;
	var WindowLeft16 = (screen.width - WindowWidth16)/3;
	var WindowTop16= (screen.height - WindowHeight16)/2;
	var openUrl16 = "<c:url value='/epc/edi/consult/selectFront0016ImgPopup.do'/>";
	var popTitle16 = "_newCodes16";
	NewWin16 = window.open(openUrl16, popTitle16, "titlebar=no, resizable=0, scrollbars=yes, width="+WindowWidth16+", height="+WindowHeight16+", top="+WindowTop16+", left="+WindowLeft16);
	NewWin16.focus();
}

function popup_windowFront20191125() {
	var WindowWidth18 = 470;
	var WindowHeight18 = 563;
	var WindowLeft18 = (screen.width - WindowWidth18)/3;
	var WindowTop18= (screen.height - WindowHeight18)/2;
	var openUrl18 = "<c:url value='/epc/edi/consult/selectFront0018ImgPopup.do'/>";
	var popTitle18 = "_newCodes18";
	NewWin18 = window.open(openUrl18, popTitle18, "titlebar=no, resizable=0, scrollbars=yes, width="+WindowWidth18+", height="+WindowHeight18+", top="+WindowTop18+", left="+WindowLeft18);
	NewWin18.focus();
}

function popup_windowFront20200306() {
	var WindowWidth19 = 690;
	var WindowHeight19 = 830;
	var WindowLeft19 = (screen.width - WindowWidth19)/3;
	var WindowTop19= (screen.height - WindowHeight19)/2 - 60;
	var openUrl19 = "<c:url value='/epc/edi/consult/selectFront0019ImgPopup.do'/>";
	var popTitle19 = "_newCodes19";
	NewWin19 = window.open(openUrl19, popTitle19, "titlebar=no, resizable=0, scrollbars=yes, width="+WindowWidth19+", height="+WindowHeight19+", top="+WindowTop19+", left="+WindowLeft19);
	NewWin19.focus();
}

function popup_windowFront20200311() {
	var WindowWidth20 = 690;
	var WindowHeight20 = 830;
	var WindowLeft20 = (screen.width - WindowWidth20)/3 - 100;
	var WindowTop20= (screen.height - WindowHeight20)/2 + 70;
	var openUrl20 = "<c:url value='/epc/edi/consult/selectFront0020ImgPopup.do'/>";
	var popTitle20 = "_newCodes20";
	NewWin20 = window.open(openUrl20, popTitle20, "titlebar=no, resizable=0, scrollbars=yes, width="+WindowWidth20+", height="+WindowHeight20+", top="+WindowTop20+", left="+WindowLeft20);
	NewWin20.focus();
}

function popup_windowFront20200319() {
	var WindowWidth21 = 570;
	var WindowHeight21 = 590;
	var WindowLeft21 = (screen.width - WindowWidth21)/3 - 100;
	var WindowTop21= (screen.height - WindowHeight21)/2 + 70;
	var openUrl21 = "<c:url value='/epc/edi/consult/selectFront0021ImgPopup.do'/>";
	var popTitle21 = "_newCodes21";
	NewWin21 = window.open(openUrl21, popTitle21, "titlebar=no, resizable=0, scrollbars=yes, width="+WindowWidth21+", height="+WindowHeight21+", top="+WindowTop21+", left="+WindowLeft21);
	NewWin21.focus();
}--%>

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

	if (checkCookie != 'noOpen' ) {
		//var url ='<c:url value="/main/popUpintro.do"/>?boardSeq=<%=dataMap.getString("BOARD_SEQ")%>';  /* 팝업창 주소 */
		//centerPopupWindowTmp(url, 'epcPopupBoard'+<%=i%>, {width:800, height:500 , scrollBars : 'YES'},<%=i%>);

		var board_seq = "<%=dataMap.getString("BOARD_SEQ")%>";
		centerPopupWindowTmp2(board_seq, 'epcPopupBoard'+<%=i%>, {width:800, height:500 , scrollBars : 'YES'},<%=i%>);
	}
<%
}
%>
}

function centerPopupWindowTmp(targetUrl, windowName, properties,counts) {
	var childWidth = properties.width;
	var childHeight = properties.height;
	var childTop = (screen.height - childHeight) / 2 - 50;
	var childLeft = ((screen.width - childWidth) / 2)+50*counts;
	var popupProps = "width=" + childWidth + ",height=" + childHeight + ", top=" + childTop + ", left=" + childLeft;

	if (properties.scrollBars == "YES") {
		popupProps += ", scrollbars=yes";
	}

	window.open(targetUrl, windowName, popupProps);
}

function centerPopupWindowTmp2(board_seq, windowName, properties,counts) {
	var childWidth = properties.width;
	var childHeight = properties.height;
	var childTop = (screen.height - childHeight) / 2 - 50;
	var childLeft = ((screen.width - childWidth) / 2)+50*counts;
	var popupProps = "width=" + childWidth + ",height=" + childHeight + ", top=" + childTop + ", left=" + childLeft;

	if (properties.scrollBars == "YES") {
		popupProps += ", scrollbars=yes";
	}

	window.open('', windowName, popupProps);
	document.mForm.target = windowName;
	document.mForm.method = "POST";
	document.mForm.boardSeq.value = board_seq;
	document.mForm.action = '<c:url value="/main/popUpintro.do"/>';
	document.mForm.submit();
}

function getCookie(name) {
	var arg = name + "=";
	var alen = arg.length;
	var clen = document.cookie.length;
	var i = 0;

	while (i < clen) {
		var j = i + alen;
		if (document.cookie.substring(i, j) == arg)
			return getCookieVal(j);
		i = document.cookie.indexOf(" ", i) + 1;
		if (i == 0) break;
	}
	return false;
}

function getCookieVal(offset) {
	var endstr = document.cookie.indexOf (";", offset);
	if (endstr == -1) endstr = document.cookie.length;
	return unescape(document.cookie.substring(offset, endstr));
}

function goForm(gubn) {
	var form = document.mForm;
	var url;

	if ( gubn == 'S' ) {
		url = '<c:url value="/main/viewScmMain.do"/>';
	} else if ( gubn == 'V' ) {
		url = '<c:url value="/main/viewScmMain.do"/>';
		form.vndInfo.value = "V";
	} else if ( gubn == 'E' ) {
		url = '<c:url value="/edi/main/viewEdiMain.do"/>';
	} else if ( gubn == 'Q' ) {
		url = '<c:url value="/edi/main/viewEtcMain.do"/>?gubn='+gubn;
		Common.centerPopupWindow(url, 'QNA', {width : 1500, height : 630,scrollBars : 'YES'});
		return;
	} else if ( gubn == 'T' ) {
		url = '<c:url value="/edi/main/viewEtcMain.do"/>?gubn='+gubn;
		Common.centerPopupWindow(url, 'TEL', {width : 1100, height : 750,scrollBars : 'YES'});
		return;
	} else if ( gubn == 'A' ) {
		popup_windowFront20151203();
		popup_windowFront20170313();
		popup_window();
		return;
	} else if ( gubn == 'B' ) {
		url = '<c:url value="http://www.gfair.or.kr/main.do"/>';
		Common.centerPopupWindow(url, 'G-FAIR KOREA', {width : 1500, height : 630,scrollBars : 'YES'});
		return;
	} else {
		alert('링크가 올바르지 않습니다.');
		return;
	}

	form.action=url;
	form.submit();
}

function goLink(gubn) {
	var form = document.mForm;
	var url;

	if (gubn == '0') {
		url = '<c:url value="/edi/inventory/PEDMINV0021Baner.do"/>';
	} else if (gubn == '1') {
		url = '<c:url value="/edi/buy/PEDMBUY0021Baner.do"/>';
	} else if (gubn == '2') {
		url = '<c:url value="/edi/product/PEDMPRO0008Baner.do"/>';
	} else if (gubn == '3') {
		url = '<c:url value="/edi/product/PEDMPRO0001Baner.do"/>';
	} else {
		return;
	}

	form.method = "get";
	form.action = url;
	form.submit();
}

function PopupWindow(pageName) {
	var cw = 800;
	var ch = 600;
	var sw = screen.availWidth;
	var sh = screen.availHeight;
	var px = Math.round((sw-cw)/2);
	var py = Math.round((sh-ch)/2);
	window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
}

function cyberPopup() {
	//PopupWindow("<c:url value='/edi/consult/PEDPCST0011.do'/>");
	var url ='http://company.lottemart.com/bc/lmt/PbcmLmt0005/main.do?menuCd=BM090208'; /* 팝업창 주소 */
	Common.centerPopupWindow(url, 'cyberpopup', {width : 1200, height : 600, scrollBars : 'YES', resizable : 'YES'});
}

/**********************************************************
 * 뉴스 & 공지사항 상세보기 
 ******************************************************** */
 function doPopupDetail(board_seq) {
	var url ='<c:url value="/board/selectDetailPopup.do"/>?boardSeq='+board_seq; /* 팝업창 주소 */
	Common.centerPopupWindow(url, 'epcDetailPopup', {width : 600, height : 400, scrollBars : 'YES'});
}

/** ********************************************************
 * 더보기 팝업링크
 ******************************************************** */
 function doMorePopup() {
	var url ='<c:url value="/board/selectBoardList.do"/>'; /* 팝업창 주소 */
	Common.centerPopupWindow(url, 'epcSearchPopup', {width : 790, height : 590});
}

 /** ********************************************************
  * g-fair 배너기간 체크
  ******************************************************** */
<%--function gfairCheck() {
	if ( today>=20170921 && today<=20171020) {
		$("#gfair").show();
	}
}--%>

function imgNoticCheck() {
	<%--if ( today>=20200101 && today<=20200331) {
		popupOpen();
	}

	var checkCookieHC18;
	checkCookieHC18 = getCookie("popcookie20191125");
	if (checkCookieHC18 != 'popcookie20191125' ) {
		if ( today >= 20191125 && today<=20200331) {
			popup_windowFront20191125();
		}
	}
	
	var checkCookieHC19;
	checkCookieHC19 = getCookie("popcookie20200306");
	if (checkCookieHC19 != 'popcookie20200306' ) {
		if ( today >= 20200301 && today<=20200430) {
			popup_windowFront20200306();
		}
	}

	var checkCookieHC20;
	checkCookieHC20 = getCookie("popcookie20200311");
	if (checkCookieHC20 != 'popcookie20200311' ) {
		if ( today >= 20200311 && today<=20200430) {
			popup_windowFront20200311();
		}
	}

	var checkCookieHC21;
	checkCookieHC21 = getCookie("popcookie20200319");
	if (checkCookieHC21 != 'popcookie20200319' ) {
		if ( today >= 20200319 && today<=20200531) {
			popup_windowFront20200319();
		}
	}--%>

}

function closeByLayer(id) {
	var chkId = document.getElementById("chk" + id);
	if (chkId.checked) {
		setCookie("layerPopup" + id, "done", 1);
	}
	document.getElementById("layerPopup" + id).style.visibility = "hidden";
	document.getElementById("chk" + id).checked = false;
}

function setCookie( name, value, expiredays ) {
	var todayDate = new Date();
	todayDate.setDate( todayDate.getDate() + expiredays );
	document.cookie = name + '=' + escape( value ) + '; path=/; expires=' + todayDate.toGMTString() + ';'
}

function popupOpen() {
	if (document.all.layerPopup0.style.visibility == "hidden") {
		document.all.layerPopup0.style.visibility = "visible";
		return false;
	} else {
		document.all.layerPopup0.style.visibility = "hidden";
		return false;
	}

	var $layerPopupObj = $('#layerPopup0');
	var left = ( $(window).scrollLeft() + ($(window).width() - $layerPopupObj.width()) / 2 );
	var top = ( $(window).scrollTop() + ($(window).height() - $layerPopupObj.height()) / 2 );
	$layerPopupObj.css({'left':left,'top':top, 'position':'absolute'});
	$('body').css('position','relative').append($layerPopupObj);
}

function popupOpenAddress() {
  if (document.all.layerPopup1.style.visibility == "hidden") {
		document.all.layerPopup1.style.visibility = "visible";
		return false;
	} else {
		document.all.layerPopup1.style.visibility = "hidden";
		return false;
	}

	var $layerPopupObj = $('#layerPopup1');
	var left = ( $(window).scrollLeft() + ($(window).width() - $layerPopupObj.width()) / 2 );
	var top = ( $(window).scrollTop() + ($(window).height() - $layerPopupObj.height()) / 2 );
	$layerPopupObj.css({'left':left,'top':top, 'position':'absolute'});
	$('body').css('position','relative').append($layerPopupObj);
}

function layerPopUp() {
  if (document.all.layerPopup2.style.visibility == "visible") {
		document.all.layerPopup2.style.visibility = "hidden";
		return false;
	}/* else {
		document.all.layerPopup2.style.visibility = "hidden";
		return false;
	} */

	var $layerPopupObj = $('#layerPopup2');
	var left = ( $(window).scrollLeft() + ($(window).width() - $layerPopupObj.width()) / 2 );
	var top = ( $(window).scrollTop() + ($(window).height() - $layerPopupObj.height()) / 2 );
	$layerPopupObj.css({'left':left,'top':top, 'position':'absolute'});
	$('body').css('position','relative').append($layerPopupObj);
}

function personalInfoPolicy() {
	var url ='<c:url value="/main/personalInfoPolicy.do"/>';
	Common.centerPopupWindow(url, 'personalInfoPolicy', {width : 1030, height : 700, scrollBars : 'YES'});
}
</script>
</head>
<body>
<form name="mForm" method="get">
<input type="hidden" name="boardSeq" id="boardSeq" value="" />
<input type="hidden" name="vndInfo" id="vndInfo" value="" />
<div id="wrap">
	<div id="con_wrap">

		<div class="con_area">
			<div id="contents">
				<div id="head">
					<h1><img src="${lfn:getString('system.cdn.static.path')}/images/epc/h1-logo-lottemart.gif" alt="행복드림-롯데마트" /></h1>
				</div>
				<!-- 롯데마트 협력회사 서비스 -->
				<div class="intro">
					<img src="${lfn:getString('system.cdn.static.path')}/images/epc/tit-main-svc.gif" alt="롯데마트 협력회사 서비스 Collaboration & Value Creation" />
				</div>

				<!-- 공지사항 & 행사안내 list 최대 5줄 출력 -->
				<div class="notic">

					<h3>공지사항 &amp; 행사안내</h3>
					<ul>
						<c:forEach var="result" items="${list}" varStatus="status">
							<li><a href="javascript:doPopupDetail('${result.BOARD_SEQ}')">${result.REG_DATE}&nbsp;&nbsp;&nbsp;${result.TITLE}</a></li>
						</c:forEach>
					</ul>
					<span class="more"><a href="javascript:doMorePopup()"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/btn-more.gif" alt="더보기" /></a></span>
				</div>

				<!-- 체크리스트 출력 최대 4줄 출력 -->
				<%-- <div class="chk_list">
					<h3>확인해주세요</h3>
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<caption>항목에 따른 건수</caption>
						<colgroup><col width="216px"><col width="*"></colgroup>
						<thead>
							<tr>
								<th width="216px">항목</th>
								<th width="*">건수</th>
							</tr>
						</thead>
						<tbody>
						<%
							String titles[] = new String[] {"불량상품", "입고거부 상품", "POG상품(이미지 등록 반려)", "삼진아웃제 차수"};
							int i = 0;
							
							for (i = 0; i < 4; i++) {
								%>
								
								<tr>
									<td><p class="t_left">&nbsp;</p></td>
									<td>&nbsp;</td>
								</tr>
								
								<%
							}
						%>
						<c:forEach var="result" items="${list}" varStatus="status">
							<tr>
								<td><p class="t_left"><a href="javascript:goLink('<%=i%>')"><%=titles[i]%></a></p></td>
								<td>${result}</td>
							</tr>
						<%
							i++;
						%>
						</c:forEach>
						</tbody>
					</table>
				</div> --%>
				<!-- 배너 -->
				<div class="bnr_area">
					<a href="http://www.safebill.co.kr/etax/index.jsp" target="_blank"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/banner/bnr-main-invoice.gif" alt="세금 계산서" /></a>
					<a href="http://winwin.lottemart.com/main/main.do" target="_blank"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/banner/bnr-main-growing.gif" alt="동반 성장" /></a>
					<a href="http://www.lcn.co.kr/" target="_blank"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/banner/bnr-main-lcn.gif" alt="LCN" /></a>
					<a href="http://ecs.lcn.co.kr/" target="_blank"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/banner/bnr-main-econtract.gif" alt="전자 계약" /></a>
					<a href="http://b2b.lottemart.com/" target="_blank"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/banner/bnr-main-b2bmall.gif" alt="롯데마트 B2B" /></a>
				</div>
			</div>

			<div id="quick_area">
				<div class="login_user">
<c:choose>
	<c:when test="${not empty epcLoginVO.adminId}">
				<p class="user"><strong>${epcLoginVO.adminId}</strong>님 환영합니다.</p>
				<p class="compy_num"><strong>사업자번호</strong>
				<select name="conoArr" style="width:90px;border:1px solid #ccc;font-size:11px;line-height:19px;">
				<c:forEach var="cono" items="${epcLoginVO.cono}" varStatus="i">
					<option value="${cono}">${cono}</option>
				</c:forEach>
				</select>
				</p>
	</c:when>
	<c:otherwise>
				<p class="user"><strong>${epcLoginVO.loginNm}</strong>님 환영합니다.</p>
				<p class="compy_num"><strong>사업자번호</strong>
				<select name="conoArr" style="width:90px;border:1px solid #ccc;font-size:11px;line-height:19px;">
				<c:forEach var="cono" items="${epcLoginVO.cono}" varStatus="i">
					<option value="${cono}">${cono}</option>
				</c:forEach>
				</select>
				</p>
	</c:otherwise>
</c:choose>
				</div>
				<div class="menu_bnr">
					<div>
						<ul>
							<li><a href="javascript:goLink('3');">신상품 등록</a></li>
							<!-- <li><a href="javascript:cyberPopup();">준법경영상담실</a></li> -->
						</ul>
					</div>
				</div>
				<!-- button -->
				<ul class="advice_area">
					<li onClick="goForm('S');"><p>SCM</p></li>
					<li onClick="goForm('E');"><p>EDI</p></li>
					<li onClick="goForm('Q');"><p>기능별 Q&A</p></li>
					<!-- <li onClick="goForm('T');"><p>팀별 전화번호</p></li> -->
					<li onClick="goForm('A');"><p>공지사항</p></li>
				</ul>
				<!-- // button -->
				<!-- 2017.09.21 2017.10.20 g-fair 배너 // -->
				<ul>
					<li id="gfair" name="gfair" onClick="goForm('B');" style="display:none"><p><img src="${lfn:getString('system.cdn.static.path')}/images/epc/banner/bnr-g-fair.gif" alt="G-Fair" style="cursor:pointer;" /></p></li>
				</ul>
				<!-- // 2017.09.21 2017.10.20 g-fair 배너 -->
			</div>
		</div>

		<div id="footer">
			<p class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/footer/footer-logo-mlottemart.gif" alt="행복드림-롯데마트" /></p>
			<p class="address"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/footer/footer-copyright.gif" alt="copyright&copy; LOTTEMART.COM. ALL RIGHTS RESERVED." /></p>
			<p class="personalInfoPolicy"><strong><a href="javascript:personalInfoPolicy();">개인정보처리방침</a></strong></p>
		</div>

	</div>
</div>

<div id="layerPopup0" style="position:absolute; visibility:hidden;">
	<h4>공지사항<a href="#" class="close" onClick="javascript:popupOpen()">X</a> </h4>
		<ul class="popupCont0">
			<li>
			<strong>
                             안녕하십니까. 파트너사 여러분<br />
              '20년 롯데마트 판매장려금 결정 조건 공지 드립니다.<br /><br />
               ○ 공지 내용<br />
              &nbsp;&nbsp;- 판매장려금 계약 사항<br />
              &nbsp;&nbsp;- 판매장려금 종류<br /><br />
              ○ 확인 방법<br />
              &nbsp;&nbsp;- 뉴스&공지사항 '20년 롯데마트 판매장려금 결정 조건 안내드립니다.'<br />
              &nbsp;&nbsp;&nbsp; <font color="red">(첨부문서 확인)</font><br /><br />
              <input type="checkbox" name="chk0" id="chk0" onclick="closeByLayer(0)" />
			  <font color="blue">하루 동안 이 창을 열지 않습니다.</font>
            </strong>
            </li>
        </ul>
</div>

<!-- 레이어 팝업 공지 : 출고지/반품지 필수등록 안내 -->
<div id="layerPopup1" style="position:absolute; visibility:hidden;">
	<h4>공지사항<a href="#" class="close" onClick="javascript:popupOpenAddress()">X</a> </h4>
		<ul class="popupCont1">
			<li>
			<strong>
			신규 상품 등록시의 필수값 변경사항 공지 드립니다.<br /><br />
			향후 기본출고지 및 기본반품지 정보가 없는 파트너사의 경우,<br /> 
			신규 상품 등록이 불가하오니<br />
			기본출고지 및 기본반품지 정보를 필수로 입력하여 주시기를 바랍니다.<br /><br />
			&nbsp;&nbsp;- 적용 일시 : 1/14(화)<br />
			&nbsp;&nbsp;- 입력 경로 : SCM>시스템관리>업체정보관리>업체주소 입력란<br /><br />
			<input type="checkbox" name="chk1" id="chk1" onclick="closeByLayer(1)" />
 			<font color="blue">하루 동안 이 창을 열지 않습니다.</font>
			</strong>
		</li>
	</ul>
</div>

<!-- 레이어 팝업 공지 : 수수료율 변경요청 안내 -->
<%--<div id="layerPopup2" style="position:absolute; visibility:hidden;">
	<h4>공지사항<a href="#" class="close" onClick="javascript:layerPopUp()">X</a> </h4>
		<ul class="popupCont2">
			<li>
			<strong>
			안녕하십니까. 파트너사 여러분<br /><br />
			롯데마트 온라인몰「수수료율 변경요청」에 대한 공지 드립니다.<br /> 
			(위수탁 업체에 한함)<br /><br />
			  ○ 공지 내용<br />
			  &nbsp;&nbsp;- 온라인몰「수수료율 변경요청」메뉴 신규 개발 안내<br />
			  &nbsp;&nbsp;- 해당 메뉴 매뉴얼 확인 요청<br /><br />
			  ○ 확인 방법<br />
			  &nbsp;&nbsp;- 뉴스&공지사항 內 '온라인몰「수수료율 변경요청」매뉴얼 안내드립니다.'<br />
			  &nbsp;&nbsp;&nbsp; <font color="red">(첨부문서 확인)</font><br /><br />
			<input type="checkbox" name="chk2" id="chk2" onclick="closeByLayer(2)" />
			 <font color="blue">하루 동안 이 창을 열지 않습니다.</font>
			</strong>
		</li>
	</ul>
</div>--%>
<!-- 레이어 팝업 공지 : 2차 인증 안내 -->
<c:if test="${codeList[0].LET_2_REF eq 'Y'}">
<div id="layerPopup2" style="position:absolute; visibility:visible;">
	<h4>공지사항<a href="#" class="close" onClick="javascript:layerPopUp()">X</a></h4>
		<ul class="popupCont2">
			<li>
			<strong>
			<font color="red">[필독]</font> 롯데마트몰 파트너사 시스템 2차 휴대폰 인증 도입 안내
			</strong><br /><br />
			보안강화 지침에 따라 파트너사 시스템 로그인 시 '2차 휴대폰 인증 단계'가 <b>2021년 8월 31일(화)</b> 부로 추가되었습니다.<br /><br />
			휴대폰 번호는 <b>'SCM > 시스템관리 > 업체정보관리 > 담당자정보'</b>에서 추가 등록 및 수정 하실 수 있습니다.<br /><br />
			등록된 휴대폰 번호가 없거나 불일치하여 정상적으로 SMS 인증을 받지 못한 경우 로그인이 불가합니다.<br /><br />
			아직 추가가 안된 담당자가 있다면 등록하여 서비스 이용에 불편함이 없으시길 바랍니다. <br /><br />
			<b> - <a href="#" onClick="javascript:goForm('V');" style="color:blue;">휴대폰 번호 확인/등록  바로가기 →</a></b><br /><br />
			<input type="checkbox" name="chk2" id="chk2" onclick="closeByLayer(2)" />
			<font size="2px">하루 동안 이 창을 열지 않습니다.</font>
		</li>
	</ul>
</div>
</c:if>
<script type="text/javascript" language="javascript" src="/js/front/front_common.js"></script>
</form>
</body>
</html>