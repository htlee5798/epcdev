<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>LOTTE MART Back Office System</title>
	<link rel="stylesheet" href="/css/style_1024.css" />
	<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
	<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
	<script type="text/javascript">
	function lnbclose() {
		var lnb = document.getElementById("leftArea");
		var btnOpen = document.getElementById("lbtn_open");
		var rcon = parent.contentFram.document.getElementById('content_wrap');
		var rcon2 = parent.contentFram.document.getElementById('wrap_menu');
		var wSize = parent.contentFram.document.getElementById('widthSize');
		
		if (lnb.style.display = 'block'){
				lnb.style.display = "none";
				lnb.style.width = "";
				rcon.style.width="947px";
				if(wSize)wSize.value="947px";
				//rcon2.style.width="927px";
				parent.widthFram.cols = "42px,*"; 
				btnOpen.style.display = "block";
		} else {
				lnb.style.display = "block";
				rcon.style.width="799px";
				//rcon2.style.width="779px";
				parent.widthFram.cols = "190px,*"; 
		}
	}

	function lnbopen() {
		var lnb = document.getElementById("leftArea");
		var btnOpen = document.getElementById("lbtn_open");
		var rcon = parent.contentFram.document.getElementById('content_wrap');
		var rcon2 = parent.contentFram.document.getElementById('wrap_menu');
		var wSize = parent.contentFram.document.getElementById('widthSize');
		if (lnb.style.display = 'none'){
			lnb.style.display = "block";
			lnb.style.width = "170px";
			rcon.style.width="799px";
			if(wSize) wSize.value="799px";
			//rcon2.style.width="779px";
			parent.widthFram.cols = "190px,*"; 
			btnOpen.style.display = "none";
		}
	}

	var submenu = '';

	
	function menuclick(n, leafYn){
		
		// 3depth가 있는 경우만 실행
		if( leafYn == 'N' ){
			if (submenu != n){
				if (submenu != '' ){
					document.getElementById("smenu" + submenu).style.display = 'none';
					document.getElementById("s" + submenu).className = "plus";
				}
				document.getElementById("smenu" + n).style.display = 'block';
				document.getElementById("s" + n).className = "minus";
				submenu = n;
			} else {
				document.getElementById("smenu" + n).style.display = 'none';
				document.getElementById("s" + n).className = "plus";
				submenu = '';
			}
		}
	}

	function fnConsult(url){

		

		// wWidth = 360;
		// wHight = 120;
		// wX = (window.screen.width - wWidth) / 2;
		 //wY = (window.screen.height - wHight) / 2;


		 var ww = window.open(url,"consult",'width=1040px,height=550px,status=yes, scrollbars=yes,resizeable=yes ');
		
		 
		
		}
	
	</script>
</head>

<% String code = request.getParameter("pgm_code") == null ? "ORD" :request.getParameter("pgm_code"); %>
<body>
	<!-- left area -->
	<div id="left_wrap">
		<!-- left open -->
		<div id="leftArea">
				<% if(code.equals("ORD")) { %>
					<h2>발주정보</h2>
					<ul>
						<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2"> 기간정보</a>
							<ul id="smenu1" style="display:none;">
								<li><a href="/edi/order/PEDMORD0001.do" target="contentFram">상품별</a></li>
								<li><a href="/edi/order/PEDMORD0002.do" target="contentFram">전표별</a></li>
								<li><a href="/edi/order/PEDMORD0003.do" target="contentFram">전표상세</a></li>
								<li><a href="/edi/order/PEDMORD0004.do" target="contentFram">점포별</a></li>
								<li><a href="/edi/order/PEDMORD0005.do" target="contentFram">PDC전표상세</a></li>
							</ul>
						</li>
						<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> 주문응답서</a>
							<ul id="smenu2" style="display:none;">
								<li><a href="/edi/order/PEDMORD0021.do" target="contentFram">납품가능정보</a></li>
								<li><a href="/edi/order/PEDMORD0022.do" target="contentFram">신선매입정보변경</a></li>
							</ul>
						</li>
						<li id="s3" class="depth2 plus"><a href="#" onclick="menuclick(3,'N');" class="d2"> SAMPLE</a>
							<ul id="smenu3" style="display:none;">
								<li><a href="javascript:fnConsult('/edi/sample/PEDMSAM001.do');" >sample01</a></li>
								<li><a href="javascript:fnConsult('/epc/edi/consult/login.do');" >sample02</a></li>
							</ul>
						</li>
					</ul>
				<%} else if(code.equals("BUY")) {%>
					<h2>매입정보</h2>
					<ul>
						<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2"> 기간별 매입정보</a>
							<ul id="smenu1" style="display:none;">
								<li><a href="/edi/buy/NEDMBUY0010.do" target="contentFram">일자별</a></li>
								<li><a href="/edi/buy/NEDMBUY0020.do" target="contentFram">점포별</a></li>
								<li><a href="/edi/buy/NEDMBUY0030.do" target="contentFram">상품별</a></li>
								<li><a href="/edi/buy/NEDMBUY0040.do" target="contentFram">전표별</a></li>
								<li><a href="/edi/buy/NEDMBUY0050.do" target="contentFram">전표상세별</a></li>
								<li><a href="/edi/buy/NEDMBUY0060.do" target="contentFram">점포상품별</a></li>
								<li><a href="/edi/buy/PEDMBUY0007.do" target="contentFram">점포별 가매입</a></li>
								<li><a href="/edi/buy/PEDMBUY0008.do" target="contentFram">증정품확정</a></li>
							</ul>
						</li>
						<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> 입고거부상품</a>
							<ul id="smenu2" style="display:none;">
								<li><a href="/edi/buy/PEDMBUY0021.do"' target="contentFram">센터입고거부상품</a></li>
							</ul>
						</li>
					</ul>
				<%} else if(code.equals("USP")) {%>
					<h2>미납정보</h2>
					<ul>
						<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2"> 기간별 미납정보</a>
							<ul id="smenu1" style="display:none;">
								<li><a href="/edi/usply/PEDMUSP0001.do" target="contentFram">일자별</a></li>
								<li><a href="/edi/usply/PEDMUSP0002.do" target="contentFram">점포별</a></li>
								<li><a href="/edi/usply/PEDMUSP0003.do" target="contentFram">상품별</a></li>
								<li><a href="/edi/usply/PEDMUSP0004.do" target="contentFram">상품상세</a></li>
								<li><a href="/edi/usply/PEDMUSP0005.do" target="contentFram">미납사유입력&조회</a></li>
								<li><a href="/edi/usply/PEDMUSP0006.do" target="contentFram">패널티확정</a></li>
							</ul>
						</li>
					</ul>
				<%} else if(code.equals("SAL")) {%>
					<h2>매출정보</h2>
					<ul>
						<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">기간별 매출정보</a>
							<ul id="smenu1" style="display:none;">
								<li><a href="/edi/sale/PEDMSAL0001.do" target="contentFram">일자별</a></li>
								<li><a href="/edi/sale/PEDMSAL0002.do" target="contentFram">점포별</a></li>
								<li><a href="/edi/sale/PEDMSAL0003.do" target="contentFram">상품별</a></li>
								<li><a href="/edi/sale/PEDMSAL0004.do" target="contentFram">상품상세</a></li>
							</ul>
						</li>
					</ul>
				<%} else if(code.equals("INV")) {%>
					<h2>재고정보</h2>
					<ul>
						<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">기간별 재고정보</a>
							<ul id="smenu1" style="display:none;">
								<li><a href="/edi/inventory/NEDMINV0010.do" target="contentFram">현재고(점포) </a></li>
								<li><a href="/edi/inventory/NEDMINV0020.do" target="contentFram">현재고(상품) </a></li>
								<li><a href="/edi/inventory/NEDMINV0030.do" target="contentFram">센터 점출입</a></li>
								<li><a href="/edi/inventory/NEDMINV0040.do" target="contentFram">센터 점출입 상세 </a></li>
							</ul>
						</li>
						<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> 불량상품 조치</a>
							<ul id="smenu2" style="display:none;">
								<li><a href="/edi/inventory/PEDMINV0021.do" target="contentFram">불량상품내역</a></li>
							</ul>
						</li>
					</ul>
				<%} else if(code.equals("PAY")) {%>
					<h2>결산정보</h2>
					<ul>
						<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">기간별 결산정보</a>
							<ul id="smenu1" style="display:none;">
								<li><a href="/edi/payment/PEDMPAY0001.do" target="contentFram">사업자 등록번호 </a></li>
								<li><a href="/edi/payment/PEDMPAY0002.do" target="contentFram">대금결제정보 </a></li>
								<li><a href="/edi/payment/PEDMPAY0003.do" target="contentFram">점포별 대금결제</a></li>
								<li><a href="/edi/payment/PEDMPAY0004.do" target="contentFram">세금계산서 정보 </a></li>
								<li><a href="/edi/payment/PEDMPAY0005.do" target="contentFram">물류비 정보 </a></li>
								<li><a href="/edi/payment/PEDMPAY0006.do" target="contentFram">판매장려금 정보 </a></li>
							</ul>
						</li>
						<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> 거래실적정보</a>
							<ul id="smenu2" style="display:none;">
								<li><a href="/edi/payment/PEDMPAY0001.do" target="contentFram">거래실적조회</a></li>
								<li><a href="/edi/payment/PEDMPAY0002.do" target="contentFram">점포별거래실적</a></li>
								<li><a href="/edi/payment/PEDMPAY0003.do" target="contentFram">잔액조회</a></li>
								<li><a href="/edi/payment/PEDMPAY0004.do" target="contentFram">패밀리론</a></li>
							</ul>
						</li>
						<li id="s3" class="depth2 plus"><a href="#" onclick="menuclick(3,'N');" class="d2"> 재무팀 전송</a>
							<ul id="smenu3" style="display:none;">
								<li><a href='<c:url value="/edi/payment/PEDMPAY00301.do"/>' target="contentFram">대금결제전송</a></li>
								<li><a href='<c:url value="/edi/payment/PEDMPAY00302.do"/>' target="contentFram">물류비전송</a></li>
								<li><a href='<c:url value="/edi/payment/PEDMPAY00303.do"/>' target="contentFram">SMS/EMS관리</a></li>
							</ul>
						</li>
					</ul>	
				<%} else if(code.equals("DLY")) {%>
					<h2>배달정보</h2>
					<ul>
						<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">기간별 배달정보</a>
							<ul id="smenu1" style="display:none;">
								<li><a href="/edi/delivery/PEDMDLY0001.do" target="contentFram">현황표</a></li>
								<li><a href="/edi/delivery/PEDMDLY0002.do" target="contentFram">접수확인</a></li>
								<li><a href="/edi/delivery/PEDMDLY0003.do" target="contentFram">완료등록</a></li>
							</ul>
						</li>
						<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> TOY배달정보</a>
							<ul id="smenu2" style="display:none;">
								<li><a href="/edi/delivery/PEDMDLY0021.do" target="contentFram">현황표</a></li>
								<li><a href="/edi/delivery/PEDMDLY0022.do" target="contentFram">접수확인</a></li>
								<li><a href="/edi/delivery/PEDMDLY0023.do" target="contentFram">완료등록</a></li>
							</ul>
						</li>
					</ul>
				<%} else if(code.equals("PRO")) {%>
					<h2>상품정보</h2>
					<ul>
						<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">신규상품관리</a>
							<ul id="smenu1" style="display:none;">
								<li><a href='<c:url value="/edi/product/PEDMPRO0001.do"/>'  target="contentFram">신규상품등록현황조회</a></li>
								<li><a href='<c:url value="/edi/product/PEDMPRO0002.do"/>'  target="contentFram">신규상품등록</a></li>
								<li><a href='<c:url value="/edi/product/PEDMPRO0003.do"/>'  target="contentFram">신규상품임시보관함</a></li>
								<li><a href='<c:url value="/edi/product/PEDMPRO0004.do"/>' target="contentFram">상품일괄등록</a></li>
								<li><a href='<c:url value="/edi/product/PEDMPRO0005.do"/>'  target="contentFram">물류바코드현황</a></li>
								<li><a href='<c:url value="/edi/product/PEDMPRO0006.do"/>'  target="contentFram">물류바코드등록</a></li>
							</ul>
						</li>
						<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> 상품현황관리</a>
							<ul id="smenu2" style="display:none;">
								<li><a href='<c:url value="/edi/product/PEDMPRO0007.do"/>' target="contentFram">상품등록 현황</a></li>
								<li><a href='<c:url value="/edi/product/PEDMPRO0008.do"/>' target="contentFram">이미지사이즈 관리</a></li>
							</ul>
						</li>
					</ul>
					<%} else if(code.equals("CST")) {%>
					<h2>협업정보</h2>
					<ul>
						<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">협업정보</a>
							<ul id="smenu1" style="display:none;">
								<li><a href='<c:url value="/edi/consult/PEDMCST00101.do"/>' target="mainFrame">LOAN</a></li>
								<li><a href='<c:url value="/edi/consult/PEDMCST00201.do"/>' target="mainFrame">발주중단 등록</a></li>
								<li><a href='<c:url value="/edi/consult/PEDMCST00202.do"/>' target="mainFrame">발주중단 결과조회</a></li>
								<li><a href='<c:url value="/edi/consult/PEDMCST00301.do"/>' target="mainFrame">판매장려금/수수료 결정</a></li>
								<li><a href='<c:url value="/edi/consult/PEDMCST00302.do"/>' target="mainFrame">협력회사 선정 및 운용</a></li>
								<li><a href='<c:url value="/edi/consult/PEDMCST00401.do"/>' target="mainFrame">견적서 등록</a></li>
								<li><a href='<c:url value="/edi/consult/PEDMCST00402.do"/>' target="mainFrame">견적문서 조회</a></li>
							</ul>
						</li>
						
					</ul>
				<%}else{}%>
			<div id="lbtn_close"><a href="#" onclick="lnbclose();"><img src="/images/epc/layout/btn_lnb_close.gif" alt="" /></a></div>
		</div>
		<div id="lbtn_open" style="display:none;"><a href="#" onclick="lnbopen();"><img src="/images/epc/layout/btn_lnb_open.gif" alt="" /></a></div>
	</div>			


</body>
</html>