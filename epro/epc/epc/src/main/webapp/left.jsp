<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>LOTTE MART Back Office System</title>
	<link rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" />
	<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
	<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
	<script type="text/javascript">
	function lnbclose() {
		var lnb = document.getElementById("leftArea");
		var btnOpen = document.getElementById("lbtn_open");
		var rcon = parent.contentFram.document.getElementById('content_wrap');
		var rcon2 = parent.contentFram.document.getElementById('wrap_menu');

		if (lnb.style.display = 'block'){
				lnb.style.display = "none";
				lnb.style.width = "";
				rcon.style.width="947px";
				rcon2.style.width="927px";
				parent.widthFram.cols = "42px,*"; 
				btnOpen.style.display = "block";
		} else {
				lnb.style.display = "block";
				rcon.style.width="799px";
				rcon2.style.width="779px";
				parent.widthFram.cols = "190px,*"; 
		}
	}

	function lnbopen() {
		var lnb = document.getElementById("leftArea");
		var btnOpen = document.getElementById("lbtn_open");
		var rcon = parent.contentFram.document.getElementById('content_wrap');
		var rcon2 = parent.contentFram.document.getElementById('wrap_menu');

		if (lnb.style.display = 'none'){
			lnb.style.display = "block";
			lnb.style.width = "170px";
			rcon.style.width="799px";
			rcon2.style.width="779px";
			parent.widthFram.cols = "190px,*"; 
			btnOpen.style.display = "none";
		}
	}

	var submenu = '';

	function menuclick(n, leafYn){
		
		// 3depthê° ìë ê²½ì°ë§ ì¤í
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
	</script>
</head>
<body>
	<!-- left area -->
	<div id="left_wrap">
		<!-- left open -->
		<div id="leftArea">
			<h2>ProtoType</h2>
			<ul>
					<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1,'N');" class="d2"> 코드관리</a>
						<ul id="smenu1" style="display:none;">
							<li><a href="<c:url value="/lottemart-epc/etc/codeMainView.do"/>" onclick="menuclick('11','Y')" class="d2" target="contentFram">코드관리</a></li>
							<li><a href="/lottemart-epc/tapSample.jsp" onclick="menuclick('12','Y')" class="d2" target="contentFram"> tab샘플</a></li>
							<li><a href="/lottemart-epc/editorSample.jsp" onclick="menuclick('13','Y')" class="d2" target="contentFram"> editor샘플</a></li>
						</ul>
					</li>
					<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2,'N');" class="d2"> 제휴관리</a>
						<ul id="smenu2" style="display:none;">
							<li><a href="/lottemart-epc/statistics/selectNaverEdmSummaryList.do?rootAffiliateLinkNo=0015" onclick="menuclick('21','Y')" class="d2" target="contentFram">네이버 지식 쇼핑</a></li>
							<li><a href="/lottemart-epc/statistics/selectNaverEdmSummaryList.do?rootAffiliateLinkNo=0020" onclick="menuclick('22','Y')" class="d2" target="contentFram">네이버 쇼핑캐스트</a></li>
							<li><a href="/lottemart-epc/statistics/selectCoolGramEdmSales.do?rootAffiliateLinkNo=0016" onclick="menuclick('23','Y')" class="d2" target="contentFram">쿨그램</a></li>
							<li><a href="/lottemart-epc/statistics/selectAsianaMileageList.do" onclick="menuclick('24','Y')" class="d2" target="contentFram">아시아나 정산 관리</a></li>
							<li><a href="/lottemart-epc/statistics/selectAsianaBalanceAccountList.do" onclick="menuclick('25','Y')" class="d2" target="contentFram">아시아나 정산대상 관리</a></li>
							<li><a href="/lottemart-epc/promotion/savingMileageList.do" onclick="menuclick('26','Y')" class="d2" target="contentFram">적립마일리지 관리</a></li>
							<li><a href="/lottemart-epc/statistics/selectDaumEdmSalesSummaryList.do?rootAffiliateLinkNo=0018" onclick="menuclick('27','Y')" class="d2" target="contentFram">다음 쇼핑하우</a></li>
						</ul>
					</li>
					<li id="s3" class="depth2 plus"><a href="#" onclick="menuclick(3,'N');" class="d2"> 상품관리</a>
						<ul id="smenu3" style="display:none;">
							<li><a href="/lottemart-epc/product/selectProduct.do" onclick="menuclick('31','Y')" class="d2" target="contentFram">상품목록</a></li>
							<li><a href="/lottemart-epc/product/selectPriceChangeView.do" onclick="menuclick('32','Y')" class="d2" target="contentFram">가격변경요청관리</a></li>
							<li><a href="/lottemart-epc/product/selectScheduleMgrView.do" onclick="menuclick('33','Y')" class="d2" target="contentFram">상품이미지촬영스케쥴관리</a></li>
							<li><a href="/lottemart-epc/product/selectOutOfStockView.do" onclick="menuclick('34','Y')" class="d2" target="contentFram">품절관리</a></li>
						</ul>
					</li>
					
					<li id="s4" class="depth2 plus"><a href="#" onclick="menuclick(4,'N');" class="d2"> 게시판관리</a>
						<ul id="smenu4" style="display:none;">
							<li><a href="/lottemart-epc/board/selectBoardList.do" onclick="menuclick('41','Y')" class="d2" target="contentFram">공지사항관리</a></li>
							<li><a href="/lottemart-epc/board/selectInquireList.do" onclick="menuclick('42','Y')" class="d2" target="contentFram">1:1문의관리</a></li>
							<li><a href="/lottemart-epc/board/selectSuggestionList.do" onclick="menuclick('43','Y')" class="d2" target="contentFram">건의사항관리</a></li>
						</ul>
					</li>
					
					<li id="s5" class="depth2 plus"><a href="#" onclick="menuclick(5,'N');" class="d2"> 배송관리</a>
						<ul id="smenu5" style="display:none;">
<!-- 							<li><a href="/lottemart-epc/delivery/viewVenStatus.do" onclick="menuclick('51','Y')" class="d2" target="contentFram">협력업체배송현황</a></li> -->
							<li><a href="/lottemart-epc/delivery/selectPartnerFirmsStatus.do" onclick="menuclick('51','Y')" class="d2" target="contentFram">협력업체배송현황</a></li>
							<li><a href="/lottemart-epc/delivery/selectPartnerFirmsOrderList.do" onclick="menuclick('52','Y')" class="d2" target="contentFram">협력업체배송리스트</a></li>
						</ul>
					</li>							
					
					<li id="s6" class="depth2 plus"><a href="#" onclick="menuclick(6,'N');" class="d2"> 정산관리</a>
						<ul id="smenu6" style="display:none;">
							<li><a href="/lottemart-epc/calculation/selectDeliveryCostsCalculateList.do" onclick="menuclick('61','Y')" class="d2" target="contentFram">택배비정산목록</a></li>
						</ul>
					</li>							
					
					<li id="s7" class="depth2 plus"><a href="#" onclick="menuclick(7,'N');" class="d2"> 시스템관리</a>
						<ul id="smenu7" style="display:none;">
							<li><a href="/lottemart-epc/system/selectDeliveryChargePolicy.do" onclick="menuclick('71','Y')" class="d2" target="contentFram">배송시정책</a></li>
							<li><a href="/lottemart-epc/system/selectPersonInCharge.do" onclick="menuclick('72','Y')" class="d2" target="contentFram">담당자관리</a></li>
						</ul>
					</li>							
					
					<li id="s8" class="depth2 plus"><a href="#" onclick="menuclick(8,'N');" class="d2"> 주문관리</a>
						<ul id="smenu8" style="display:none;">
							<li><a href="/lottemart-epc/order/viewOrderList.do" onclick="menuclick('81','Y')" class="d2" target="contentFram">주문목록조회</a></li>
							<li><a href="/lottemart-epc/order/viewProductSaleSum.do" onclick="menuclick('82','Y')" class="d2" target="contentFram">상품별매출현황</a></li>
						</ul>
					</li>						
					
					
			</ul>
			<div id="lbtn_close"><a href="#" onclick="lnbclose();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_lnb_close.gif" alt="" /></a></div>
		</div>
		<div id="lbtn_open" style="display:none;"><a href="#" onclick="lnbopen();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_lnb_open.gif" alt="" /></a></div>
	</div>			

</div>
</body>
</html>