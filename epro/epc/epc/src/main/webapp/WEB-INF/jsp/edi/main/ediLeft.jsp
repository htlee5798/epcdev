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

			var className = '';
			if (submenu != n){
				if( submenu != '' ) {
					className = jQuery('#s' + submenu).attr('class');
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
			<c:when test="${pgm.pgm_code eq 'PRO'}">
				<h2>상품정보</h2>
				<ul>
					<li id="s1" class="depth2 plus"><a href="#"
													   onclick="menuclick(1,'N');" class="d2">신규상품관리</a>
						<ul id="smenu1" style="display: none;">
							<li><a href='<c:url value="/edi/product/NEDMPRO0010.do"/>'
								   target="contentFram">신규상품등록현황조회</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0020.do"/>'
								   target="contentFram"><font color="blue">온오프</font>상품등록(일반)</a></li>
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
							<li><a href='<c:url value="/edi/product/NEDMPRO0040.do"/>'
								   target="contentFram">임시보관함</a></li>
						</ul></li>
					<li id="s2" class="depth2 plus"><a href="#"
													   onclick="menuclick(2,'N');" class="d2"> 상품현황관리</a>
						<ul id="smenu2" style="display: none;">
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
						</ul>
					</li>
					<li id="s3" class="depth2 plus"><a href="#"
													   onclick="menuclick(3,'N');" class="d2"> 물류바코드관리 </a>
						<ul id="smenu3" style="display: none;">
							<li><a href='<c:url value="/edi/product/NEDMPRO0210.do"/>'
								   target="contentFram">물류바코드현황</a></li>
							<li><a href='<c:url value="/edi/product/NEDMPRO0220.do"/>'
								   target="contentFram">물류바코드등록</a></li>
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
							<li><a href='<c:url value="/edi/product/NEDMPRO0530.do"/>'
								   target="contentFram">채널확장</a></li>
						</ul>
					</li>
				</ul>
			</c:when>
			
			<c:when test="${pgm.pgm_code eq  'SYS'}">
				<h2>시스템관리</h2>
				<ul>
					<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2"> 시스템관리 </a>
						<ul id="smenu1" style="display: none;">
							<li><a href='<c:url value="/mngr/edi/product/commonMgrCode.do"/>'
								   target="contentFram">코드관리</a></li>
							<li><a href='<c:url value="/mngr/edi/product/NEDMPRO0600.do"/>'
								   target="contentFram">i/f테스트</a></li>
						</ul>
					</li>
				</ul>
			</c:when>
			
			<c:when test="${pgm.pgm_code eq 'MAIN'}">
				<h2>Dashboard</h2>
				<ul>
					<li id="s1" class="depth2 ">
						<a href="<c:url value='/edi/main/mainDashBoard.do'/>" target="contentFram">통합 메인대시보드</a>
				   </li>
				  
				</ul>
			</c:when>
		</c:choose>
		<div id="lbtn_close">
			<a href="#" onclick="lnbclose();"><img src="/images/epc/layout/btn_lnb_close.gif" alt="" /></a>
		</div>
	</div>
	<div id="lbtn_open" style="display: none;">
		<a href="#" onclick="lnbopen();"><img src="/images/epc/layout/btn_lnb_open.gif" alt="" /></a>
	</div>
</div>


</body>
</html>
