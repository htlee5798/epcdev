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
				rcon.style.width="981px";
				if(wSize)wSize.value="981px";
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
			rcon.style.width="833px";
			if(wSize) wSize.value="833px";
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

<body  onLoad="initMenu();">
	<!-- left area -->
	<div id="left_wrap">
		<!-- left open -->
		<div id="leftArea">
				<c:choose>
					<c:when test="${pgm.pgm_code eq 'ORD'}">
						<h2>발주정보</h2>
						<ul>
						
							<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2"> 기간정보</a>
								<ul id="smenu1" style="display:none;">
									<li><a href="<c:url value="/edi/order/PEDMORD0001.do"/>" target="contentFram">상품별</a></li>
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
							</li>
							
							
						</ul>
					</c:when>
					
					<c:when test="${pgm.pgm_code eq 'BUY'}">
					<h2>매입정보</h2>
					<ul>
					
						<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2"> 기간별 매입정보</a>
							<ul id="smenu1" style="display:none;">
								<li><a href="<c:url value="/edi/buy/PEDMBUY0001.do"/>" target="contentFram">일자별</a></li>
								<li><a href="<c:url value="/edi/buy/PEDMBUY0002.do"/>" target="contentFram">점포별</a></li>
								<li><a href="<c:url value="/edi/buy/PEDMBUY0003.do"/>" target="contentFram">상품별</a></li>
								<li><a href="<c:url value="/edi/buy/PEDMBUY0004.do"/>" target="contentFram">전표별</a></li>
								<li><a href="<c:url value="/edi/buy/PEDMBUY0005.do"/>" target="contentFram">전표상세별</a></li>
								<li><a href="<c:url value="/edi/buy/PEDMBUY0006.do"/>" target="contentFram">점포상품별</a></li>
								<li><a href="<c:url value="/edi/buy/PEDMBUY0007.do"/>" target="contentFram">점포별 가매입</a></li>
								<li><a href="<c:url value="/edi/buy/PEDMBUY0008.do"/>" target="contentFram">증정품확정</a></li>
							</ul>
						</li>
						<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> 입고거부상품</a>
							<ul id="smenu2" style="display:none;">
								<li><a href="<c:url value="/edi/buy/PEDMBUY0021.do"/>"' target="contentFram">센터입고거부상품</a></li>
							</ul>
						</li>
					</ul>
				</c:when>
				
				<c:when test="${pgm.pgm_code eq 'USP'}">
					<h2>미납정보</h2>
					<ul>
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
					</ul>
				</c:when>
					
				<c:when test="${pgm.pgm_code eq 'SAL'}">	
					<h2>매출정보</h2>
					<ul>
						<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">기간별 매출정보</a>
							<ul id="smenu1" style="display:none;">
								<li><a href="<c:url value="/edi/sale/PEDMSAL0001.do"/>" target="contentFram">일자별</a></li>
								<li><a href="<c:url value="/edi/sale/PEDMSAL0002.do"/>" target="contentFram">점포별</a></li>
								<li><a href="<c:url value="/edi/sale/PEDMSAL0003.do"/>" target="contentFram">상품별</a></li>
								<li><a href="<c:url value="/edi/sale/PEDMSAL0004.do"/>" target="contentFram">상품상세</a></li>
							</ul>
						</li>
					</ul>
				</c:when>
					
				<c:when test="${pgm.pgm_code eq 'INV'}">
					<h2>재고정보</h2>
					<ul>
						<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">기간별 재고정보</a>
							<ul id="smenu1" style="display:none;">
								<li><a href="<c:url value="/edi/inventory/PEDMINV0001.do"/>" target="contentFram">현재고(점포) </a></li>
								<li><a href="<c:url value="/edi/inventory/PEDMINV0002.do"/>" target="contentFram">현재고(상품) </a></li>
								<li><a href="<c:url value="/edi/inventory/PEDMINV0003.do"/>" target="contentFram">센터 점출입</a></li>
								<li><a href="<c:url value="/edi/inventory/PEDMINV0004.do"/>" target="contentFram">센터 점출입 상세 </a></li>
							</ul>
						</li>
						<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> 불량상품 조치</a>
							<ul id="smenu2" style="display:none;">
								<li><a href="<c:url value="/edi/inventory/PEDMINV0021.do"/>" target="contentFram">불량상품내역</a></li>
							</ul>
						</li>
					</ul>
				</c:when>
				
				<c:when test="${pgm.pgm_code eq 'PAY'}">	
					<h2>결산정보</h2>
					<ul>
						<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">기간별 결산정보</a>
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
						</li>
						<!-- 
						<li id="s3" class="depth2 plus"><a href="#" onclick="menuclick(3,'N');" class="d2"> 재무팀 전송</a>
							<ul id="smenu3" style="display:none;">
								<li><a href='<c:url value="/edi/payment/PEDMPAY00301.do"/>' target="contentFram">대금결제전송</a></li>
								<li><a href='<c:url value="/edi/payment/PEDMPAY00303.do"/>' target="contentFram">SMS/EMS관리</a></li>
							</ul>
						</li>
						 -->
					</ul>	
				</c:when>
				
				<c:when test="${pgm.pgm_code eq 'DLY'}">
					<h2>배달정보</h2>
					<ul>
						<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">기간별 배달정보</a>
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
						</li>
					</ul>
				</c:when>
				
				
				
				
				
				<c:when test="${pgm.pgm_code eq 'PRO'}">
					<h2>상품정보</h2>
					<ul>
						<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">신규상품관리</a>
							<ul id="smenu1" style="display:none;">
							
							<!--	<li><a href='<c:url value="/edi/product/PEDMPRO0001.do"/>'  target="contentFram">신규상품등록현황조회</a></li>	-->
							<!--	<li><a href='<c:url value="/edi/product/PEDMPRO0002.do"/>'  target="contentFram">온오프 상품등록 (일반)</a></li>-->
							<!--	<li><a href='<c:url value="/edi/product/PEDMPRO000230.do"/>'  target="contentFram"> 온라인전용 상품등록</a></li>-->
							<!-- <li><a href='<c:url value="/edi/product/PEDMPRO000230.do?divnCode=social"/>'  target="contentFram">소셜 상품등록</a></li> -->
							<!--	<li><a href='<c:url value="/edi/product/PEDMPRO0003.do"/>'  target="contentFram">임시보관함</a></li> -->	
							<!-- <li><a href='<c:url value="/edi/product/PEDMPRO0004.do"/>' target="contentFram">상품일괄등록</a></li>  -->	
								
							</ul>
						</li>
						<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> 상품현황관리</a>
							<ul id="smenu2" style="display:none;">
								
							<!--	<li><a href='<c:url value="/edi/product/PEDMPRO0007.do"/>' target="contentFram">상품등록 현황</a></li> -->	
							<!-- 	<li><a href='<c:url value="/edi/product/PEDMPRO0009.do"/>' target="contentFram">점포별상품등록 현황</a></li> -->
							
							<!--	<li><a href='<c:url value="/edi/product/PEDMPRO0008.do"/>' target="contentFram">이미지사이즈 관리</a></li>-->
								
						<!--
						 		<li><a href='<c:url value="/edi/product/PEDMPRO0099.do"/>' target="contentFram">상품 분석속성관리</a></li>-->
						<!--		<li><a href='<c:url value="/edi/product/PEDMPRO0900.do"/>' target="contentFram">상품 분석속성관리(일괄)</a></li> -->	
						
							</ul>
						</li>
						<li id="s3" class="depth2 plus"><a href="#" onclick="menuclick(3,'N');" class="d2"> 물류바코드관리 </a>
							<ul id="smenu3" style="display:none;">
								<li><a href='<c:url value="/edi/product/PEDMPRO0005.do"/>'  target="contentFram">물류바코드현황</a></li>
								<!--<li><a href='<c:url value="/edi/product/PEDMPRO0006.do"/>'  target="contentFram">물류바코드등록</a></li>  -->
								<!--<li><a href="javascript:alert('물류바코드 등록은 기존 EDI 시스템에서 등록부탁드립니다.');"  target="contentFram">물류바코드등록</a></li>-->
							</ul>
						</li>
						
					</ul>
				</c:when>
				
				
				
				
				
				
					
				<c:when test="${pgm.pgm_code eq 'CST'}">
					<h2>협업정보</h2>
						<ul>
							<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2">협업정보</a>
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
							</li>
							<li id="s4" class="depth2 plus"><a href="#" onclick="menuclick(4,'N');" class="d2"> 업체정보</a>
								<ul id="smenu4" style="display:none;">
									<li><a href='<c:url value="/edi/consult/PEDMCST0099.do"/>' target="contentFram">신주소관리</a></li>
								</ul>
							</li>
							
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
						<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2"> 발주등록</a>
							<ul id="smenu1" style="display:none;">
								<li><a href="<c:url value="/edi/weborder/PEDMWEB0001.do"/>" target="contentFram">점포별 발주등록</a></li>
								<li><a href="<c:url value="/edi/weborder/PEDMWEB0002.do"/>" target="contentFram">상품별 발주등록</a></li>
								<li><a href="<c:url value="/edi/weborder/PEDMWEB0003.do"/>" target="contentFram">발주일괄등록</a></li>
								<li><a href="<c:url value="/edi/weborder/PEDMWEB0004.do"/>" target="contentFram">발주 전체 현황</a></li>
							</ul>
						</li>
						
						<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> 반품등록</a>
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
								<li><a href="<c:url value="/edi/weborder/PEDMWEB0010.do"/>" target="contentFram">발주 승인관리</a></li>
							    <li><a href="<c:url value="/edi/weborder/PEDMWEB0011.do"/>" target="contentFram">발주 전체 현황</a></li>
								 <li><a href="<c:url value="/edi/weborder/PEDMWEB0016.do"/>" target="contentFram">반품 전체  현황</a></li>							    
								<li><a href="<c:url value="/edi/weborder/PEDMWEB0020.do"/>" target="contentFram">Mder 협력업체 설정</a></li> 
						
							</ul>
						</li>
						</c:if>
						
						
					</ul>
				</c:when>
					
			</c:choose>		
			<div id="lbtn_close"><a href="#" onclick="lnbclose();"><img src="/images/epc/layout/btn_lnb_close.gif" alt="" /></a></div>
		</div>
		<div id="lbtn_open" style="display:none;"><a href="#" onclick="lnbopen();"><img src="/images/epc/layout/btn_lnb_open.gif" alt="" /></a></div>
	</div>			


</body>
</html>