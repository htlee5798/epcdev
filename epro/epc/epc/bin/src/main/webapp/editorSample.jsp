<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>LOTTE MART Back Office System</title>
	<link rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" />
	<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
	<script type="text/javascript" src="/js/epc/common.js"></script>
	<script type="text/javascript" src="/js/wisegrid/WiseGridTag-dev.js" ></script>
	<script type="text/javascript" src="/js/epc/paging.js" ></script>
	<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
	<script language="javascript" type="text/javascript">
		function popupInsertForm() {
			window.open	('./popup_Detail.html','','width=800px,height=350px'); 
		}
		function popupSearch() {
			window.open	('./popup_InsertForm.html','','width=800px,height=350px');
		}
		function popupDetail() {
			window.open	('./popup_SearchPopup.html','','width=800px,height=600px');
		}
	</script>
</head>
<body>

<div id="content_wrap">

<div class="content_scroll">

<div id="wrap_menu">
	<!--	@ 검색조건	-->

	<DIV id="divShowInstall" style="BORDER-RIGHT: 0px; BORDER-TOP: 0px; Z-INDEX: 0; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px; POSITION: absolute">
		<EMBED src="/edit/images/NamoBanner.swf" width=741 height=525 type=application/x-shockwave-flash></EMBED>
	</Div>
	<script language="javascript" src="/edit/NamoWec7.js"></script>

</div>

</div>


	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg">메시지가 디스플레이 되는 곳입니다.</div>
			<div class="notice">최근공지게시판 제목만 노출</div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>시스템관리</li>
					<li class="last">관리자관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>



</div>

</body>
</html>