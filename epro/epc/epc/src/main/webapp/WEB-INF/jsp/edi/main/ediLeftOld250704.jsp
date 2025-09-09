<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
				
				<c:choose>
					<c:when test='${not empty pgm.isFull and pgm.isFull eq "Y"}'>
					rcon.style.width="99%";
					if(wSize)wSize.value="99%";
					</c:when>
					<c:otherwise>
					rcon.style.width="981px";
					if(wSize)wSize.value="981px";
					</c:otherwise>
				</c:choose>
				//rcon2.style.width="927px";
				parent.widthFram.cols = "42px,*";
				btnOpen.style.display = "block";
			} else {
				lnb.style.display = "block";
				rcon.style.width="833px";
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

			if (rcon){

				parent.widthFram.cols = "190px,*";
				lnb.style.display = "block";
				lnb.style.width = "170px";
				
				<c:choose>
					<c:when test='${not empty pgm.isFull and pgm.isFull eq "Y"}'>
					rcon.style.width="99%";
					if(wSize) wSize.value="99%";
					</c:when>
					<c:otherwise>
					rcon.style.width="833px";
					if(wSize) wSize.value="833px";
					</c:otherwise>
				</c:choose>
				
				//rcon2.style.width="779px";

				btnOpen.style.display = "none";
			}
		}



		var submenu = '';
		function menuclick(n){
//alert("n:"+n);
//alert("submenu:"+submenu);

			var className = '';
			if (submenu != n){
				if( submenu != '' ) {
					className = jQuery('#s' + submenu).attr('class');
//alert("nn:"+n);

					if(className.indexOf("minus") != -1) {
						className = className.replace('minus', 'plus');
					}
					jQuery('#s' + submenu).attr('class', className);

					if( jQuery('#smenu' + submenu).length ) {
						jQuery('#smenu' + submenu).hide();

						// 하위메뉴 class 초기화
						jQuery('#smenu' + submenu).find('>li').each(function() {
							jQuery(this).attr('class', '');
						});
					}
				}

				className = jQuery('#s' + n).attr('class');
				if(className.indexOf("plus") != -1) {
					className = className.replace('plus', 'minus');
				}
				jQuery('#s' + n).attr('class', className);

				jQuery('.depth2').each(function() {
					jQuery(this).find('>a').attr('class', 'd2');
				});

				jQuery('#s' + n).find('>a').attr('class', 'd2_on');

				if( jQuery('#smenu' + n).length ) {
					if( jQuery('#smenu' + n).attr('clickEvent') != 'Y' ) {
						jQuery('#smenu' + n).find('>li').bind('click', function() {
							var nowObj = jQuery(this);
							jQuery('#smenu' + n).find('>li').each(function() {
								if( jQuery(this).get(0) == jQuery(nowObj).get(0) ) {
									jQuery(this).attr('class', 'on');
								} else {
									jQuery(this).attr('class', '');
								}
							});
						});
						jQuery('#smenu' + n).attr('clickEvent', 'Y');
					}
					jQuery('#smenu' + n).show();
				}

				submenu = n;
			} else {
				className = jQuery('#s' + n).attr('class');
				if(className.indexOf("minus") != -1) {
					className = className.replace('minus', 'plus');
				}
				jQuery('#s' + n).attr('class', className);
				jQuery('#s' + n).find('>a').attr('class', 'd2');

				if( jQuery('#smenu' + n).length ) {
					jQuery('#smenu' + n).hide();

					jQuery('#smenu' + n).find('>li').each(function() {
						jQuery(this).attr('class', '');
					});
				}

				submenu = '';
			}
		}


		function fnConsult(url)
		{
			// wWidth = 360;
			// wHight = 120;
			// wX = (window.screen.width - wWidth) / 2;
			//wY = (window.screen.height - wHight) / 2;


			var ww = window.open(url,"consult",'width=1000px,height=550px, scrollbars=yes ');
		}

		function initMenu()
		{

			<c:choose>
			<c:when test="${empty pgm.pgm_sub }">
			menuclick(1,'N');
			</c:when>
			<c:otherwise>
			menuclick(${pgm.pgm_sub},'N');
			</c:otherwise>
			</c:choose>


			parent.widthFram.cols = "190px,*";

		}
	</script>
</head>

<body onLoad="initMenu();">
<!-- left area -->
<div id="left_wrap">
	<!-- left open -->
	<div id="leftArea">
		<c:choose>
			<c:when test="${pgm.pgm_code eq 'ORD'}">
				<h2>발주 상세 Data</h2>
				<ul>

						<%-- <li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2"> 기간정보</a>
								<ul id="smenu1" style="display:none;">
									<li><a href="<c:url value="/edi/order/NEDMORD0010.do"/>" target="contentFram">상품별</a></li>
									<li><a href="<c:url value="/edi/order/PEDMORD0002.do"/>" target="contentFram">전표별</a></li>
									<li><a href="<c:url value="/edi/order/PEDMORD0003.do"/>" target="contentFram">전표상세</a></li>
									<li><a href="<c:url value="/edi/order/PEDMORD0004.do"/>" target="contentFram">점포별</a></li>
									<li><a href="<c:url value="/edi/order/PEDMORD0005.do"/>" target="contentFram">PDC전표상세</a></li>
								</ul>
							</li>


							<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> 주문응답서</a>
								<ul id="smenu2" style="display:none;">
									<li><a href="<c:url value="/edi/order/PEDMORD0021.do"/>" target="contentFram">납품가능정보</a></li>
									<li><a href="<c:url value="/edi/order/PEDMORD0022.do"/>" target="contentFram">신선매입정보변경</a></li>
								</ul>
							</li> --%>


					<li id="s1" class="depth2 plus">
						<a href="#" onclick="menuclick(1,'N');" class="d2"> 기간정보</a>
						<ul id="smenu1" style="display: none;">
							<li><a href="<c:url value="/edi/order/NEDMORD0010.do"/>"
								   target="contentFram">상품별</a></li>
							<li><a href="<c:url value="/edi/order/NEDMORD0020.do"/>"
								   target="contentFram">전표별</a></li>
							<li><a href="<c:url value="/edi/order/NEDMORD0030.do"/>"
								   target="contentFram">전표상세</a></li>
							<li><a href="<c:url value="/edi/order/NEDMORD0040.do"/>"
								   target="contentFram">점포별</a></li>
							<li><a href="<c:url value="/edi/order/NEDMORD0050.do"/>"
								   target="contentFram">PDC전표상세</a></li>
						</ul>
					</li>
					<font color="red">&nbsp;※ 발주의뢰서는 LCN사이트 內 확인요망</font>

						<%-- <li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> 주문응답서</a>
								<ul id="smenu2" style="display:none;">
									<li><a href="<c:url value="/edi/order/NEDMORD0110.do"/>" target="contentFram">납품가능정보</a></li>
									<li><a href="<c:url value="/edi/order/NEDMORD0120.do"/>" target="contentFram">신선매입정보변경</a></li>
								</ul>
							</li> --%>

				</ul>
			</c:when>

			<c:when test="${pgm.pgm_code eq 'BUY'}">
				<h2>매입정보</h2>
				<ul>

					<li id="s1" class="depth2 plus"><a href="#"
													   onclick="menuclick(1,'N');" class="d2"> 기간별 매입정보</a>
						<ul id="smenu1" style="display: none;">
							<li><a href="<c:url value="/edi/buy/NEDMBUY0010.do"/>"
								   target="contentFram">일자별</a></li>
							<li><a href="<c:url value="/edi/buy/NEDMBUY0020.do"/>"
								   target="contentFram">점포별</a></li>
							<li><a href="<c:url value="/edi/buy/NEDMBUY0030.do"/>"
								   target="contentFram">상품별</a></li>
							<li><a href="<c:url value="/edi/buy/NEDMBUY0040.do"/>"
								   target="contentFram">전표별</a></li>
							<li><a href="<c:url value="/edi/buy/NEDMBUY0050.do"/>"
								   target="contentFram">전표상세별</a></li>
							<li><a href="<c:url value="/edi/buy/NEDMBUY0060.do"/>"
								   target="contentFram">점포상품별</a></li>
								<%-- <li><a href="<c:url value="/edi/buy/PEDMBUY0007.do"/>" target="contentFram">점포별 가매입</a></li> --%>
							<li><a href="<c:url value="/edi/buy/NEDMBUY0080.do"/>"
								   target="contentFram">증정품확정</a></li>
						</ul></li>
					<li id="s2" class="depth2 plus"><a href="#"
													   onclick="menuclick(2,'N');" class="d2"> 입고거부상품</a>
						<ul id="smenu2" style="display: none;">
							<li><a href="<c:url value="/edi/buy/NEDMBUY0110.do"/>"
								' target="contentFram">센터입고거부상품</a></li>
						</ul></li>
				</ul>
			</c:when>

			<c:when test="${pgm.pgm_code eq 'USP'}">
				<h2>미납정보</h2>
				<%-- <ul>
                    <li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2"> 기간별 미납정보</a>
                        <ul id="smenu1" style="display:none;">
                            <li><a href="<c:url value="/edi/usply/PEDMUSP0001.do"/>" target="contentFram">일자별</a></li>
                            <li><a href="<c:url value="/edi/usply/PEDMUSP0002.do"/>" target="contentFram">점포별</a></li>
                            <li><a href="<c:url value="/edi/usply/PEDMUSP0003.do"/>" target="contentFram">상품별</a></li>
                            <li><a href="<c:url value="/edi/usply/PEDMUSP0004.do"/>" target="contentFram">상품상세</a></li>
                            <li><a href="<c:url value="/edi/usply/PEDMUSP0005.do"/>" target="contentFram">미납사유입력&조회</a></li>
                            <li><a href="<c:url value="/edi/usply/PEDMUSP0006.do"/>" target="contentFram">패널티확정</a></li>
                        </ul>
                    </li>
                </ul> --%>
				<ul>
					<li id="s1" class="depth2 plus"><a href="#"
													   onclick="menuclick(1,'N');" class="d2"> 기간별 미납정보</a>
						<ul id="smenu1" style="display: none;">
							<li><a href="<c:url value="/edi/usply/NEDMUSP0010.do"/>"
								   target="contentFram">일자별</a></li>
							<li><a href="<c:url value="/edi/usply/NEDMUSP0020.do"/>"
								   target="contentFram">점포별</a></li>
							<li><a href="<c:url value="/edi/usply/NEDMUSP0030.do"/>"
								   target="contentFram">상품별</a></li>
							<li><a href="<c:url value="/edi/usply/NEDMUSP0040.do"/>"
								   target="contentFram">상품상세</a></li>
							<li><a href="<c:url value="/edi/usply/NEDMUSP0050.do"/>"
								   target="contentFram">미납사유입력&조회</a></li>
							<li><a href="<c:url value="/edi/usply/NEDMUSP0060.do"/>"
								   target="contentFram">패널티확정</a></li>
						</ul></li>
				</ul>
			</c:when>

			<c:when test="${pgm.pgm_code eq 'SAL'}">
				<h2>매출정보</h2>
				<ul>
						<%-- <li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">기간별 매출정보</a>
							<ul id="smenu1" style="display:none;">
								<li><a href="<c:url value="/edi/sale/PEDMSAL0001.do"/>" target="contentFram">일자별</a></li>
								<li><a href="<c:url value="/edi/sale/PEDMSAL0002.do"/>" target="contentFram">점포별</a></li>
								<li><a href="<c:url value="/edi/sale/PEDMSAL0003.do"/>" target="contentFram">상품별</a></li>
								<li><a href="<c:url value="/edi/sale/PEDMSAL0004.do"/>" target="contentFram">상품상세</a></li>
							</ul>
						</li> --%>
					<li id="s1" class="depth2 plus"><a href="#"
													   onclick="menuclick(1,'N');" class="d2"> 기간별 매출정보</a>
						<ul id="smenu1" style="display: none;">
							<li><a href="<c:url value="/edi/sale/NEDMSAL0010.do" />"
								   target="contentFram">일자별</a></li>
							<li><a href="<c:url value="/edi/sale/NEDMSAL0020.do" />"
								   target="contentFram">점포별</a></li>
							<li><a href="<c:url value="/edi/sale/NEDMSAL0030.do" />"
								   target="contentFram">상품별</a></li>
							<li><a href="<c:url value="/edi/sale/NEDMSAL0040.do" />"
								   target="contentFram">상품상세</a></li>
						</ul></li>
				</ul>
			</c:when>

			<c:when test="${pgm.pgm_code eq 'INV'}">
				<h2>재고정보</h2>
				<ul>
					<li id="s1" class="depth2 plus"><a href="#"
													   onclick="menuclick(1,'N');" class="d2">기간별 재고정보</a>
						<ul id="smenu1" style="display: none;">
							<li><a
									href="<c:url value="/edi/inventory/NEDMINV0010.do"/>"
									target="contentFram">현재고(점포) </a></li>
							<li><a
									href="<c:url value="/edi/inventory/NEDMINV0020.do"/>"
									target="contentFram">현재고(상품) </a></li>
							<li><a
									href="<c:url value="/edi/inventory/NEDMINV0030.do"/>"
									target="contentFram">센터 점출입</a></li>
							<li><a
									href="<c:url value="/edi/inventory/NEDMINV0040.do"/>"
									target="contentFram">센터 점출입 상세 </a></li>
						</ul></li>
					<li id="s2" class="depth2 plus"><a href="#"
													   onclick="menuclick(2,'N');" class="d2"> 불량상품 조치</a>
						<ul id="smenu2" style="display: none;">
							<li><a
									href="<c:url value="/edi/inventory/NEDMINV0110.do"/>"
									target="contentFram">불량상품내역</a></li>
								<%-- <li><a href="<c:url value="/edi/inventory/PEDMINV0021.do"/>" target="contentFram">O불량상품내역</a></li> --%>

						</ul></li>
				</ul>
			</c:when>

			<c:when test="${pgm.pgm_code eq 'PAY'}">
				<h2>결산정보</h2>
				<ul>
						<%-- <li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">기간별 결산정보</a>
							<ul id="smenu1" style="display:none;">

								<li><a href="<c:url value="/edi/payment/PEDMPAY0001.do"/>" target="contentFram">사업자 등록번호 </a></li>
<!-- 2012.07.18 임재유 - 조충환K 요청으로 삭제 -->
								<li><a href="<c:url value="/edi/payment/PEDMPAY0002.do"/>" target="contentFram">대금결제정보 </a></li>

<!-- 2012.07.18 임재유 - 조충환K 요청으로 삭제 -->
								<li><a href="<c:url value="/edi/payment/PEDMPAY0003.do"/>" target="contentFram">점포별 대금결제</a></li>

								<li><a href="<c:url value="/edi/payment/PEDMPAY0004.do"/>" target="contentFram">세금계산서 정보 </a></li>
								<li><a href="<c:url value="/edi/payment/PEDMPAY0005.do"/>" target="contentFram">물류비 정보 </a></li>
<!-- 2012.07.18 임재유 - 조충환K 요청으로 삭제 -->
 								<li><a href="<c:url value="/edi/payment/PEDMPAY00061.do"/>" target="contentFram">신 판매장려금 정보 </a></li>
 								<li><a href="<c:url value="/edi/payment/PEDMPAY0006.do"/>" target="contentFram">구 판매장려금 정보</a></li>

							</ul>
						</li>
						<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> 거래실적정보</a>
							<ul id="smenu2" style="display:none;">

								<li><a href="<c:url value="/edi/payment/PEDMPAY0021.do"/>" target="contentFram">거래실적조회</a></li>
								<li><a href="<c:url value="/edi/payment/PEDMPAY0022.do"/>" target="contentFram">점포별거래실적</a></li>
<!-- 2012.07.18 임재유 - 조충환K 요청으로 삭제 -->
								<li><a href="<c:url value="/edi/payment/PEDMPAY0023.do"/>" target="contentFram">잔액조회</a></li>
							<li><a href="<c:url value="/edi/payment/PEDMPAY0024.do"/>" target="contentFram">패밀리론</a></li>
							</ul>
						</li> --%>
					<!--
						<li id="s3" class="depth2 plus"><a href="#" onclick="menuclick(3,'N');" class="d2"> 재무팀 전송</a>
							<ul id="smenu3" style="display:none;">
								<li><a href='<c:url value="/edi/payment/PEDMPAY00301.do"/>' target="contentFram">대금결제전송</a></li>
								<li><a href='<c:url value="/edi/payment/PEDMPAY00303.do"/>' target="contentFram">SMS/EMS관리</a></li>
							</ul>
						</li>
						 -->
					<li id="s1" class="depth2 plus"><a href="#"
													   onclick="menuclick(1,'N');" class="d2"> 기간별 결산정보</a>
						<ul id="smenu1" style="display: none;">
							<li><a href="<c:url value="/edi/payment/NEDMPAY0010.do"/>"
								   target="contentFram">사업자 등록번호 </a></li>
							<li><a href="<c:url value="/edi/payment/NEDMPAY0020.do"/>"
								   target="contentFram">대금결제정보 </a></li>
							<li><a href="<c:url value="/edi/payment/NEDMPAY0030.do"/>"
								   target="contentFram">점포별 대금결제</a></li>
							<li><a href="<c:url value="/edi/payment/NEDMPAY0040.do"/>"
								   target="contentFram">세금계산서 정보 </a></li>
							<li><a href="<c:url value="/edi/payment/NEDMPAY0050.do"/>"
								   target="contentFram">물류비 정보 </a></li>
							<li><a href="<c:url value="/edi/payment/NEDMPAY0061.do"/>"
								   target="contentFram">신 판매장려금 정보 </a></li>
								<%--  								<li><a href="<c:url value="/edi/payment/NEDMPAY0060.do"/>" target="contentFram">구 판매장려금 정보</a></li> --%>

						</ul></li>
					<li id="s2" class="depth2 plus"><a href="#"
													   onclick="menuclick(2,'N');" class="d2"> 거래실적정보</a>
						<ul id="smenu2" style="display: none;">

							<li><a href="<c:url value="/edi/payment/NEDMPAY0110.do"/>"
								   target="contentFram">거래실적조회</a></li>
							<li><a href="<c:url value="/edi/payment/NEDMPAY0120.do"/>"
								   target="contentFram">점포별거래실적</a></li>
							<li><a href="<c:url value="/edi/payment/NEDMPAY0130.do"/>"
								   target="contentFram">잔액조회</a></li>
							<li><a href="<c:url value="/edi/payment/NEDMPAY0140.do"/>"
								   target="contentFram">패밀리론</a></li>
						</ul></li>
				</ul>
			</c:when>

			<c:when test="${pgm.pgm_code eq 'DLY'}">
				<h2>배달정보</h2>
				<ul>
						<%-- <li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">기간별 배달정보</a>
							<ul id="smenu1" style="display:none;">
								<li><a href="<c:url value="/edi/delivery/PEDMDLY0001.do"/>" target="contentFram">현황표</a></li>
								<li><a href="<c:url value="/edi/delivery/PEDMDLY0002.do"/>" target="contentFram">접수확인</a></li>
								<li><a href="<c:url value="/edi/delivery/PEDMDLY0003.do"/>" target="contentFram">완료등록</a></li>
							</ul>
						</li>
						<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> TOY배달정보</a>
							<ul id="smenu2" style="display:none;">
								<li><a href="<c:url value="/edi/delivery/PEDMDLY0021.do"/>" target="contentFram">현황표</a></li>
								<li><a href="<c:url value="/edi/delivery/PEDMDLY0022.do"/>" target="contentFram">접수확인</a></li>
								<li><a href="<c:url value="/edi/delivery/PEDMDLY0023.do"/>" target="contentFram">완료등록</a></li>
							</ul>
						</li> --%>
					<li id="s1" class="depth2 plus"><a href="#"
													   onclick="menuclick(1,'N');" class="d2"> TOY배달정보</a>
						<ul id="smenu1" style="display: none;">
							<li><a href="<c:url value="/edi/delivery/NEDMDLY0110.do"/>"
								   target="contentFram">현황표</a></li>
							<li><a href="<c:url value="/edi/delivery/NEDMDLY0120.do"/>"
								   target="contentFram">접수확인</a></li>
							<li><a href="<c:url value="/edi/delivery/NEDMDLY0130.do"/>"
								   target="contentFram">완료등록</a></li>
						</ul></li>
				</ul>
			</c:when>





			<c:when test="${pgm.pgm_code eq 'PRO'}">
				<h2>상품정보</h2>
				<ul>
					<li id="s1" class="depth2 plus"><a href="#"
													   onclick="menuclick(1,'N');" class="d2">신규상품관리</a>
						<ul id="smenu1" style="display: none;">
								<%-- <li><a href='<c:url value="/edi/product/PEDMPRO0001.do"/>'  target="contentFram">신규상품등록현황조회</a></li> --%>
							<li><a href='<c:url value="/edi/product/NEDMPRO0010.do"/>'
								   target="contentFram">신규상품등록현황조회</a></li>
								<%-- <li><a href='<c:url value="/edi/product/PEDMPRO0002.do"/>'  target="contentFram">온오프 상품등록 (일반)</a></li> --%>
							<li><a href='<c:url value="/edi/product/NEDMPRO0020.do"/>'
								   target="contentFram"><font color="blue">온오프</font>상품등록(일반)</a></li>
								   
<!-- 								   
							<li><a
									href='<c:url value="/edi/product/PEDMPRO000230.do"/>'
									target="contentFram"><font color="red">온라인상품등록(일반)</font></a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0050.do"/>'
								   target="contentFram"><font color="red">온라인상품등록(딜)</font></a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0060.do"/>'
								   target="contentFram"><font color="red">온라인상품등록(일괄)</font></a></li>
							<li><a href='<c:url value="/edi/product/PEDMPRO00070.do"/>'
								   target="contentFram"><font color="red">일괄등록 카테고리조회</font></a></li>
 -->
							<li><a href='<c:url value="/edi/product/NEDMPRO0063.do"/>'
								   target="contentFram">EC 카테고리조회</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0064.do"/>'
								   target="contentFram">EC 카테고리별 속성 조회</a></li>
							<!-- font color=#8B008B -->
							<li><a href='<c:url value="/edi/product/NEDMPRO0080.do"/>'
								   target="contentFram">마트분석속성 조회</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0090.do"/>'
								   target="contentFram">마트분석속성 등록</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0190.do"/>'
								   target="contentFram">영양성분 조회</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0200.do"/>'
								   target="contentFram">영양성분 등록</a></li>
							<!-- <li><a href='<c:url value="/edi/product/PEDMPRO000230.do?divnCode=social"/>'  target="contentFram">소셜 상품등록</a></li> -->
								<%-- <li><a href='<c:url value="/edi/product/PEDMPRO0003.do"/>'  target="contentFram">임시보관함</a></li> --%>
							<li><a href='<c:url value="/edi/product/NEDMPRO0040.do"/>'
								   target="contentFram">임시보관함</a></li>
							<!-- <li><a href='<c:url value="/edi/product/PEDMPRO0004.do"/>' target="contentFram">상품일괄등록</a></li>  -->

						</ul></li>
					<li id="s2" class="depth2 plus"><a href="#"
													   onclick="menuclick(2,'N');" class="d2"> 상품현황관리</a>
						<ul id="smenu2" style="display: none;">
							<!-- AS-IS -->
								<%-- <li><a href='<c:url value="/edi/product/PEDMPRO0007.do"/>' target="contentFram">상품등록 현황</a></li> --%>
								<%-- <li><a href='<c:url value="/edi/product/PEDMPRO0009.do"/>' target="contentFram">점포별상품등록 현황</a></li> --%>
								<%-- <li><a href='<c:url value="/edi/product/PEDMPRO0008.do"/>' target="contentFram">이미지사이즈 관리</a></li> --%>

							<!-- TO-BE -->
							<li><a href='<c:url value="/edi/product/NEDMPRO0110.do"/>'
								   target="contentFram">상품등록 현황</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0120.do"/>'
								   target="contentFram">점포별상품등록 현황</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0130.do"/>'
								   target="contentFram">이미지사이즈&중량 관리</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0140.do"/>'
								   target="contentFram">PLC등급 조회</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0150.do"/>'
								   target="contentFram">PB상품 재고관리</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0170.do"/>'
								   target="contentFram">상품단종/임시발주중단</a></li>
							<%-- <li><a href='<c:url value="/edi/product/NEDMPRO0180.do"/>'
								   target="contentFram">PB상품성적서 관리</a></li> --%>
						</ul>
					</li>
						<%-- <li id="s3" class="depth2 plus"><a href="#" onclick="menuclick(3,'N');" class="d2"> 물류바코드관리 </a>
							<ul id="smenu3" style="display:none;">
								<li><a href='<c:url value="/edi/product/PEDMPRO0005.do"/>'  target="contentFram">물류바코드현황</a></li>
								<li><a href='<c:url value="/edi/product/PEDMPRO0006.do"/>'  target="contentFram">물류바코드등록</a></li>
								<!--<li><a href="javascript:alert('물류바코드 등록은 기존 EDI 시스템에서 등록부탁드립니다.');"  target="contentFram">물류바코드등록</a></li>-->
							</ul>
						</li> --%>

					<li id="s3" class="depth2 plus"><a href="#"
													   onclick="menuclick(3,'N');" class="d2"> 물류바코드관리 </a>
						<ul id="smenu3" style="display: none;">
							<li><a href='<c:url value="/edi/product/NEDMPRO0210.do"/>'
								   target="contentFram">물류바코드현황</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0220.do"/>'
								   target="contentFram">물류바코드등록</a></li>
							<!--<li><a href="javascript:alert('물류바코드 등록은 기존 EDI 시스템에서 등록부탁드립니다.');"  target="contentFram">물류바코드등록</a></li>-->
						</ul></li>
						
					<li id="s4" class="depth2 plus"><a href="#" onclick="menuclick(4,'N');" class="d2"> 차세대_신규 </a>
						<ul id="smenu4" style="display: none;">
							<li><a href='<c:url value="/edi/product/NEDMPRO0500.do"/>'
								   target="contentFram">원가변경요청 등록</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0510.do"/>'
								   target="contentFram">원가변경요청 수정/조회</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0520.do"/>'
								   target="contentFram">ESG 인증관리</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0400.do"/>'
								   target="contentFram">신상품입점제안등록</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0410.do"/>'
								   target="contentFram">신상품입점제안현황</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0300.do"/>'
								   target="contentFram">행사정보 등록내역 조회</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0320.do"/>'
								   target="contentFram">반품 제안 등록</a></li>
						</ul>
					</li>
				</ul>
			</c:when>
			
			<c:when test="${pgm.pgm_code eq  'SYS'}">
				<h2>시스템관리</h2>
				<ul>
					<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2"> 시스템관리 </a>
						<ul id="smenu1" style="display: none;">
							<li><a href='<c:url value="/edi/product/commonMgrCode.do"/>'
								   target="contentFram">코드관리</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0600.do"/>'
								   target="contentFram">i/f테스트</a></li>
						</ul>
					</li>
				</ul>
			</c:when>







			<c:when test="${pgm.pgm_code eq 'CST'}">
				<h2>협업정보</h2>
				<ul>
						<%-- <li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">협업정보</a>
								<ul id="smenu1" style="display:none;">
									<li><a href='<c:url value="/edi/consult/PEDMCST0007.do"/>' target="contentFram">LOAN 론</a></li>
									<li><a href='<c:url value="/edi/consult/PEDMCST0008.do"/>' target="contentFram">거래절차</a></li>
									<li><a href='<c:url value="/edi/consult/PEDMCST0005.do"/>' target="contentFram">알리미 서비스</a></li>
								</ul>
							</li>
							<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> 발주중단 관리</a>
								<ul id="smenu2" style="display:none;">
									<li><a href='<c:url value="/edi/consult/PEDMCST0009.do"/>' target="contentFram">발주중단 등록</a></li>
									<li><a href='<c:url value="/edi/consult/PEDMCST0010.do"/>' target="contentFram">발주중단 결과조회</a></li>
								</ul>
							</li>
							<li id="s3" class="depth2 plus"><a href="#" onclick="menuclick(3,'N');" class="d2"> 견적서 관리</a>
								<ul id="smenu3" style="display:none;">
									<li><a href='<c:url value="/edi/consult/PEDMCST0003.do"/>' target="contentFram">견적서 등록</a></li>
									<li><a href='<c:url value="/edi/consult/PEDMCST0004.do"/>' target="contentFram">견적문서 조회</a></li>
								</ul>
							</li> --%>
					<li id="s1" class="depth2 plus"><a href="#"
													   onclick="menuclick(1,'N');" class="d2"> 협업정보</a>
						<ul id="smenu1" style="display: none;">
							<li><a href='<c:url value="/edi/consult/NEDMCST0010.do"/>'
								   target="contentFram">LOAN 론</a></li>
							<li><a href='<c:url value="/edi/consult/NEDMCST0020.do"/>'
								   target="contentFram">거래절차</a></li>
							<li><a href='<c:url value="/edi/consult/NEDMCST0030.do"/>'
								   target="contentFram">알리미 서비스</a></li>
						</ul></li>
						<%-- <li id="s2" class="depth2 plus"><a href="#"
							onclick="menuclick(2,'N');" class="d2"> 견적서 관리</a>
							<ul id="smenu2" style="display: none;">
								<li><a href='<c:url value="/edi/consult/NEDMCST0210.do"/>'
									target="contentFram">견적서 등록</a></li>
								<li><a href='<c:url value="/edi/consult/NEDMCST0220.do"/>'
									target="contentFram">견적문서 조회</a></li>
							</ul></li> --%>
					<li id="s3" class="depth2 plus"><a href="#"
													   onclick="menuclick(3,'N');" class="d2"> AS 접수관리</a>
						<ul id="smenu3" style="display: none;">
							<li><a href='<c:url value="/edi/consult/NEDMCST0230.do"/>'
								   target="contentFram">AS 접수관리</a></li>
						</ul></li>

					<!-- 이동빈 -->
					<!--
								<li id="s4" class="depth2 plus"><a href="#" onclick="menuclick(4,'N');" class="d2"> 업체정보</a>
									<ul id="smenu4" style="display:none;">
										<li><a href='<c:url value="/edi/consult/PEDMCST0012.do"/>' target="contentFram">신상정보 조회 & 변경</a></li>
									</ul>
								</li>
							-->
				</ul>
			</c:when>

			<c:when test="${pgm.pgm_code eq 'WOD'}">
				<h2>상품정보</h2>
				<ul>
					<li id="s1" class="depth2 plus"><a href="#"
													   onclick="menuclick(1,'N');" class="d2"> 발주등록</a>
						<ul id="smenu1" style="display: none;">
							<li><a href="<c:url value="/edi/weborder/NEDMWEB0010.do"/>"
								   target="contentFram">점포별 발주등록</a></li>
							<li><a href="<c:url value="/edi/weborder/NEDMWEB0020.do"/>"
								   target="contentFram">상품별 발주등록</a></li>
							<li><a href="<c:url value="/edi/weborder/NEDMWEB0030.do"/>"
								   target="contentFram">발주일괄등록</a></li>
							<li><a href="<c:url value="/edi/weborder/NEDMWEB0040.do"/>"
								   target="contentFram">발주 전체 현황</a></li>
						</ul></li>

						<%-- <li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> 반품등록</a>
							<ul id="smenu2" style="display:none;">
								<li><a href="<c:url value="/edi/weborder/PEDMWEB0005.do"/>" target="contentFram">점포별 반품등록</a></li>
								<li><a href="<c:url value="/edi/weborder/PEDMWEB0006.do"/>" target="contentFram">상품별 반품등록</a></li>
								<li><a href="<c:url value="/edi/weborder/PEDMWEB00051.do"/>" target="contentFram">반품 일괄 등록</a></li>

								<li><a href="<c:url value="/edi/weborder/PEDMWEB00053.do"/>" target="contentFram">반품 전체 현황</a></li>
							</ul>
						</li>

						<c:if test="${epcLoginVO.adminId == 'kachi79'}">
						<li id="s3" class="depth2 plus"><a href="#" onclick="menuclick(3,'N');" class="d2"> MDer</a>
							<ul id="smenu3" style="display:none;">
								<li><a href="<c:url value="/edi/weborder/NEDMWEB0210.do"/>" target="contentFram">발주 승인관리</a></li>
							    <li><a href="<c:url value="/edi/weborder/NEDMWEB0220.do"/>" target="contentFram">발주 전체 현황</a></li>
								 <li><a href="<c:url value="/edi/weborder/PEDMWEB0016.do"/>" target="contentFram">반품 전체  현황</a></li>
								<li><a href="<c:url value="/edi/weborder/NEDMWEB0240.do"/>" target="contentFram">Mder 협력업체 설정</a></li>

							</ul>
						</li>
						</c:if> --%>
					<li id="s2" class="depth2 plus"><a href="#"
													   onclick="menuclick(2,'N');" class="d2"> 반품등록</a>
						<ul id="smenu2" style="display: none;">
							<li><a href="<c:url value="/edi/weborder/NEDMWEB0110.do"/>"
								   target="contentFram">상품별 반품등록</a></li>
							<li><a href="<c:url value="/edi/weborder/NEDMWEB0120.do"/>"
								   target="contentFram">임시보관함</a></li>
							<li><a href="<c:url value="/edi/weborder/NEDMWEB0130.do"/>"
								   target="contentFram">반품 일괄 등록</a></li>

							<li><a href="<c:url value="/edi/weborder/NEDMWEB0140.do"/>"
								   target="contentFram">반품 전체 현황</a></li>
						</ul></li>
					<c:if test="${epcLoginVO.adminId == 'kachi79'}">
						<%-- <li id="s3" class="depth2 plus"><a href="#" onclick="menuclick(3,'N');" class="d2"> MDer</a>
                        <ul id="smenu3" style="display:none;">
                            <li><a href="<c:url value="/edi/weborder/NEDMWEB0210.do"/>" target="contentFram">발주 승인관리</a></li>
                            <li><a href="<c:url value="/edi/weborder/NEDMWEB0220.do"/>" target="contentFram">발주 전체 현황</a></li>
                            <li><a href="<c:url value="/edi/weborder/NEDMWEB0230.do"/>" target="contentFram">반품 전체  현황</a></li>
                            <li><a href="<c:url value="/edi/weborder/NEDMWEB0240.do"/>" target="contentFram">Mder 협력업체 설정</a></li>

                        </ul>
                    </li> --%>
					</c:if>

				</ul>
			</c:when>

			<c:when test="${pgm.pgm_code eq 'IGG'}">
				<h2>임가공</h2>
				<ul>
					<li id="s1" class="depth2 plus"><a href="#"
													   onclick="menuclick(1,'N');" class="d2">임가공 출고</a>
						<ul id="smenu1" style="display: none;">
							<li><a href="<c:url value="/edi/imgagong/NEDMIGG0010.do"/>"
								   target="contentFram">임가공 출고 관리</a></li>
						</ul>
					</li>

					<li id="s2" class="depth2 plus"><a href="#"
													   onclick="menuclick(2,'N');" class="d2">임가공 입고</a>
						<ul id="smenu2" style="display: none;">
							<li><a href="<c:url value="/edi/imgagong/NEDMIGG0110.do"/>"
								   target="contentFram">임가공 입고 관리</a></li>
						</ul>
					</li>
				</ul>
			</c:when>
			
			<c:when test="${pgm.pgm_code eq 'MAIN'}">
				<h2>Dashboard</h2>
				<ul>
					<li id="s1" class="depth2 ">
						<a href="<c:url value='/edi/main/mainDashBoard.do'/>" target="contentFram">통합 메인대시보드</a>
<!-- 						<a href="#" onclick="menuclick(1,'N');" class="d2">통합 매인대시보드</a> -->
				   </li>
				  
				</ul>
			</c:when>
		</c:choose>
		<div id="lbtn_close">
			<a href="#" onclick="lnbclose();"><img
					src="/images/epc/layout/btn_lnb_close.gif" alt="" /></a>
		</div>
	</div>
	<div id="lbtn_open" style="display: none;">
		<a href="#" onclick="lnbopen();"><img
				src="/images/epc/layout/btn_lnb_open.gif" alt="" /></a>
	</div>
</div>


</body>
</html>
