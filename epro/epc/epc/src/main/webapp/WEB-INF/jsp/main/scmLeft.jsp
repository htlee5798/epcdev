<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
		rcon.style.width="981px";
		//rcon2.style.width="971px";

		parent.widthFram.cols = "42px,*"; 
		btnOpen.style.display = "block";

		$(window.parent.frames["contentFram"].document).contents().find("#content_wrap").css("width", "981px");
		$(window.parent.frames["contentFram"].document).contents().find("#wrap_menu").css("width", "981px");
		$(window.parent.frames["contentFram"].document).contents().find("#adddivtabdiv").css("width", "981px");
		$(window.parent.frames["contentFram"].document).contents().find("#adddivtab").css("width", "981px");
		$(window.parent.frames["contentFram"].document).contents().find("#adddivtabbody").css("width", "981px");

		$(window.parent.frames["contentFram"].document).contents().find("iframe").each( function( index, elem){
			//$(this).setAttribute("width", "981");
			$(this).contents().find("#content_wrap").css("width", '981px');
			$(this).contents().find("#wrap_menu").css("width", '971px');
		});
	} else {
		lnb.style.display = "block";
		rcon.style.width="100%";
		//rcon2.style.width="813px";
		parent.widthFram.cols = "190px,*";
		$(window.parent.frames["contentFram"].document).contents().find("#content_wrap").css("width", "100%");
		$(window.parent.frames["contentFram"].document).contents().find("#wrap_menu").css("width", "100%");
		$(window.parent.frames["contentFram"].document).contents().find("#adddivtabdiv").css("width", "100%");
		$(window.parent.frames["contentFram"].document).contents().find("#adddivtab").css("width", "100%");
		$(window.parent.frames["contentFram"].document).contents().find("#adddivtabbody").css("width", "100%");

		$(window.parent.frames["contentFram"].document).contents().find("iframe").each( function( index, elem){
			//$(this).setAttribute("width", "833");
			$(this).contents().find("#content_wrap").css("width", '100%');
			$(this).contents().find("#wrap_menu").css("width", '100%');
		});
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
		rcon.style.width="100%";
		//rcon2.style.width="813px";
		parent.document.getElementById("widthFram").cols = "190px,*";  //For Cross-Browser Compatibility(2015.12.22)
		//parent.widthFram.cols = "190px,*"; //Not working in Chrome, only in IE
		$(window.parent.frames["contentFram"].document).contents().find("#content_wrap").css("width", "100%");
		$(window.parent.frames["contentFram"].document).contents().find("#wrap_menu").css("width", "100%");
		$(window.parent.frames["contentFram"].document).contents().find("#adddivtabdiv").css("width", "100%");
		$(window.parent.frames["contentFram"].document).contents().find("#adddivtab").css("width", "100%");
		$(window.parent.frames["contentFram"].document).contents().find("#adddivtabbody").css("width", "100%");

		$(window.parent.frames["contentFram"].document).contents().find("iframe").each( function( index, elem){
			$(this).contents().find("#content_wrap").css("width", '100%');
			$(this).contents().find("#wrap_menu").css("width", '100%');
		});
		btnOpen.style.display = "none";
	}
}

//111031 수정
var submenu = '';
function menuclick(n){
	var className = '';
	if (submenu != n){
		if( submenu != '' ) {
			className = jQuery('#s' + submenu).attr('class');

			if( jQuery('#smenu' + submenu).length ) {
				jQuery('#smenu' + submenu).hide();

				if(className.indexOf("minus") != -1) {
					className = className.replace('minus', 'plus');
				}
				jQuery('#s' + submenu).attr('class', className);

				// 하위메뉴 class 초기화
				jQuery('#smenu' + submenu).find('>li').each(function() {
					jQuery(this).attr('class', '');
				});
			}else{//2013.04.23 tree bug 수정 _ 1
				if(className.indexOf("plus") != -1) {
					className = className.replace('plus', 'minus');
				}
				jQuery('#s' + submenu).attr('class', className);
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

function clickTabMenu( url, menuNm) {
	window.parent.frames["contentFram"].clickTabMenu( url, menuNm);
}
</script>
</head>
<body>
	<!-- left area -->
	<div id="left_wrap">
		<!-- left open -->
		<div id="leftArea">
<c:choose>
	<c:when test="${menuGb eq '1'}"><h2>상품관리</h2></c:when>
	<c:when test="${menuGb eq '2'}"><h2>배송관리</h2></c:when>
	<c:when test="${menuGb eq '3'}"><h2>게시판관리</h2>	</c:when>
	<c:when test="${menuGb eq '4'}"><h2>정산관리</h2></c:when>
	<c:when test="${menuGb eq '5'}"><h2>주문관리</h2></c:when>
	<c:when test="${menuGb eq '6'}"><h2>시스템관리</h2></c:when>
	<c:when test="${menuGb eq '7'}"><h2>통계</h2></c:when>
	<c:when test="${menuGb eq '8'}"><h2>기획전관리</h2></c:when>
</c:choose>
			<ul>
<c:choose>
	<c:when test="${menuGb eq '1'}">
				<li id="s1" class="depth2 plus"><a href='#' onclick="menuclick(1);" class="d2">상품정보관리</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/product/selectProduct.do"/>' , '인터넷상품관리');">인터넷상품관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/product/selectOutOfStockView.do"/>' , '상품정보관리');">상품정보관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/product/repProduct.do"/>' , '대표상품코드관리');">대표상품코드관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/product/prodChgHist.do"/>' , '상품정보수정요청');">상품정보수정요청</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/product/prodElecCommView.do"/>' , '전상법관리');">전상법관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/product/selPoint.do"/>' , '셀링포인트관리');">셀링포인트관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/product/VendorMgr.do"/>' , '협력사공지관리');">협력사공지관리</a></li>
					</ul>
				</li>
				<li id="s2" class="depth2 plus"><a href='#' onclick="menuclick(2);" class="d2">온라인전용상품관리</a>
					<ul id="smenu2" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/product/selectPriceChangeView.do"/>' , '가격변경요청관리');">가격변경요청관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/product/selectComponentView.do"/>' , '추가구성품관리');">추가구성품관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/product/selectGiftView.do"/>' , '증정품관리');">증정품관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/product/selectItemPriceChangeView.do"/>' , '수수료율 변경요청');">수수료율 변경요청</a></li>
					</ul>
				</li>
				<li id="s3" class="depth2 plus"><a href='#' onclick="menuclick(3);" class="d2">스케쥴관리</a>
					<ul id="smenu3" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/product/selectScheduleMgrView.do"/>' , '이미지촬영스케쥴관리');">이미지촬영스케쥴관리</a></li>
					</ul>
				</li>
	</c:when>
	<c:when test="${menuGb eq '8'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2">기획전관리</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/exhibition/basicExhibition.do"/>' , '기획전등록');">기획전등록</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/exhibition/exhibitionMng.do"/>' , '기획전관리');">기획전관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/exhibition/exhibitionMngInt.do"/>' , '통합기획전관리');">통합기획전관리</a></li>
					</ul>
				</li>
	</c:when>
	<c:when test="${menuGb eq '2'}">

		<c:choose>
			<c:when test="${epcLoginVO.happyGubn}">
				<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2);" class="d2">해피콜</a>
					<ul id="smenu2" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/delivery/PDCMHPC0001.do"/>' , '해피콜관리');">해피콜관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/delivery/PDCMHPC000101.do"/>' , '해피콜실적집계');">해피콜실적집계</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/delivery/PDCMHPC000102.do"/>' , '출고일 조회');">출고일 조회</a></li>
					</ul>
				</li>
			</c:when>
			<c:otherwise>
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2">협력업체 배송관리</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/delivery/selectPartnerFirmsListStatus.do"/>' , '배송현황');">배송현황</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/delivery/selectPartnerFirmsOrderList.do"/>' , '배송리스트');">배송리스트</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/delivery/viewPartnerNoFirmsOrderList.do"/>' , '미확정 배송리스트');">미확정 배송리스트</a></li>
						<!--li><a href='<c:url value="/delivery/selectPartherReturnStatusList.do"/>' target="contentFram">교환/반품현황</a></li-->
						<!-- 2016.03.22 by kmlee[게시판관리][고객센터 문의관리]로 통합하기로 협의 with 조수란 책임 -->
						<!--li><a href="javascript:clickTabMenu('<c:url value="/board/selectCallCenterList.do"/>' , '콜센터배송문의등록');">콜센터배송문의등록</a></li-->														
						<!-- 2017.12.11 by mz_khs 페널티 대상상품 현황, 발송일 준수 현황 -->
						<%-- 2017.12.22 페널티 관련 보류 
							p.5 페널티 대상상품 현황
							 - 메뉴 노출여부
							   ▶ 패널티 대상상품 현황 장표 자체 불필요

							p.6 발송일 준수현황
							 - 페널티 항목 제외 후 제공여부
							   ▶ 패널티 항목 제외 처리
						<li><a href="javascript:clickTabMenu('<c:url value="/delivery/pscmdlv0010/init.do"/>' , '페널티 대상상품 현황');">페널티 대상상품 현황</a></li> --%>
						<li><a href="javascript:clickTabMenu('<c:url value="/delivery/pscmdlv0011/init.do"/>' , '발송일 준수 현황');">발송일 준수 현황</a></li>
						
					</ul>
				</li>
				<c:if test="${not empty epcLoginVO.adminId}">
				<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2);" class="d2">해피콜</a>
					<ul id="smenu2" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/delivery/PDCMHPC0001.do"/>' , '해피콜관리');">해피콜관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/delivery/PDCMHPC000101.do"/>' , '해피콜실적집계');">해피콜실적집계</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/delivery/PDCMHPC000102.do"/>' , '출고일 조회');">출고일 조회</a></li>
					</ul>
				</li>
				</c:if>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${menuGb eq '3'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 게시판관리</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/board/selectBoardList.do"/>' , '공지사항관리');">공지사항관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/board/selectProductList.do"/>' , '상품평게시판');">상품평게시판</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/board/selectSuggestionList.do"/>' , '업체문의관리');">업체문의관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/board/selectCcQnaList.do"/>' , '고객센터문의관리');">고객센터문의관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/board/selectQnaList.do"/>' , '상품Q&A게시판');">상품Q&A게시판</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/board/ecReviewList.do"/>' , '롯데ON 상품평 관리');">롯데ON 상품평 관리</a></li>
					</ul>
				</li>
				<%-- <li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2);" class="d2"> 고객의 소리</a>
					<ul id="smenu2" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/board/selectInquireList.do"/>' , '콜센터1:1문의');">콜센터1:1문의</a></li>
					</ul>
				</li> --%>
	</c:when>
	<c:when test="${menuGb eq '4'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 정산관리</a>
					<ul id="smenu1" style="display:none;">
					    <%-- 2016.03.22 by kmlee 아래 배송료정산과 내용이 동일하고, 쿼리 수정이 필요한 상태로 우선 닫아두기로 협의 with 최현수 책임 --%>
						<%--<li><a href="javascript:clickTabMenu('<c:url value="/calculation/selectDeliveryCostsCalculateList.do"/>' , '택배비정산목록');">택배비정산목록</a></li>--%>
						<li><a href="javascript:clickTabMenu('<c:url value="/calculation/selectDeliverySettleCostsCalculateList.do"/>' , '배송료정산목록');">배송료정산목록</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/substn/viewSubStnForm.do"/>' , '매출공제현황');">매출공제현황</a></li>
						<%--<li><a href="javascript:clickTabMenu('<c:url value="/substn/viewPaySubStnForm.do"/>' , '정산지불현황');">정산지불현황</a></li>--%>
						<%--<li><a href="javascript:clickTabMenu('<c:url value="/substn/viewSubStnAdjForm.do"/>' , '정산내역조회');">정산내역조회</a></li>--%>
					</ul>
				</li>
	</c:when>
	<c:when test="${menuGb eq '5'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 주문관리</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/order/viewOrderList.do"/>' , '주문목록조회');">주문목록조회</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/order/viewOrderItemList.do"/>' , '주문목록조회(상품별)');">주문목록조회(상품별)</a></li>
						<%-- <li><a href="javascript:clickTabMenu('<c:url value="/order/viewSocialOrderList.do"/>' , '소셜주문목록조회');">소셜주문목록조회</a></li> --%>
						<li><a href="javascript:clickTabMenu('<c:url value="/order/viewProductSaleSum.do"/>' , '상품별매출현황');">상품별매출현황</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/delivery/viewPartnerReturnList.do"/>', '취소/반품/교환주문');">취소/반품/교환주문</a></li>	
					</ul>
				</li>
				<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2);" class="d2"> 매출정보</a>
					<ul id="smenu2" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/order/salesInfobyDate.do"/>' , '일자별');">일자별</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/order/salesInfobyStore.do"/>' , '점포별');">점포별</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/order/viewSaleProductList.do"/>', '상품별');">상품별</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/order/viewSaleDetailList.do"/>' , '상품상세');">상품상세</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/order/selectCSRList.do"/>' , '카테고리별판매순위');">카테고리별판매순위</a></li>
					</ul>
				</li>
	</c:when>
	<c:when test="${menuGb eq '6'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 시스템관리</a>
					<ul id="smenu1" style="display:none;">
						<c:choose>
						<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
							<li><a href="javascript:clickTabMenu('<c:url value="/system/selectPartner.do"/>' , '협력사관리');">협력사관리</a></li>
						</c:when>
						</c:choose>
						<!--li><a href="javascript:clickTabMenu('<c:url value="/system/selectDeliveryChargePolicy.do"/>' , '정책관리');">정책관리</a></li-->
						<!--li><a href="javascript:clickTabMenu('<c:url value="/system/selectPersonInCharge.do"/>', '담당자관리');">담당자관리</a></li-->
						<li><a href="javascript:clickTabMenu('<c:url value="/system/selectVendorInfoMgr.do"/>', '업체정보관리');">업체정보관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/system/selectEcsTem.do"/>', '전상법템플릿관리');">전상법템플릿관리</a></li>
					</ul>
				</li>
	</c:when>
	<c:when test="${menuGb eq '7'}">
		<c:choose>
			<c:when test="${epcLoginVO.allianceGubn}">
				<c:choose>
					<c:when test="${epcLoginVO.adminTypeCd eq '0200'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 네이버지식쇼핑</a>
					<ul id="smenu1" style="display:none;">
						 <li><a href="javascript:clickTabMenu('<c:url value="/statistics/selectNaverEdmSummaryList.do?rootAffiliateLinkNo=0015"/>' , '네이버지식쇼핑');">네이버지식쇼핑</a></li>
					</ul>
				</li>
					</c:when>

					<c:when test="${epcLoginVO.adminTypeCd eq '0210'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 네이버쇼핑캐스트</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/selectNaverEdmSummaryList.do?rootAffiliateLinkNo=0020"/>' , '네이버쇼핑캐스트');">네이버쇼핑캐스트</a></li>
					</ul> 
				</li>
					</c:when>
					<%-- < c:when test="${epcLoginVO.adminTypeCd eq '0400'}">
					<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 아시아나</a>
						<ul id="smenu1" style="display:none;">
							<li><a href="javascript:clickTabMenu('<c:url value="/statistics/selectAsianaMileageList.do"/>' , '아시아나 정산 관리');">아시아나 정산 관리</a></li>
							<li><a href="javascript:clickTabMenu('<c:url value="/statistics/selectAsianaBalanceAccountList.do"/>' , '아시아나 정산대상 관리');">아시아나 정산대상 관리</a></li>
						</ul>
					</li>
					< /c:when> --%>
					<c:when test="${epcLoginVO.adminTypeCd eq '0100'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 다음 쇼핑하우</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/selectDaumEdmSalesSummaryList.do?rootAffiliateLinkNo=0018"/>' , '다음 쇼핑하우');">다음 쇼핑하우</a></li>
					</ul>
				</li>
					</c:when>
					<c:when test="${epcLoginVO.adminTypeCd eq '0500'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 쿨그램</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/selectCoolGramEdmSalesList.do?rootAffiliateLinkNo=0016"/>' , '쿨그램');">쿨그램</a></li>
					</ul>
				</li>
					</c:when>
					<c:when test="${epcLoginVO.adminTypeCd eq '0700'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 레시피기여매출</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/selectRecipeContributionEdmList.do?rootAffiliateLinkNo=0018"/>' , '레시피기여매출');">레시피기여매출</a></li>
					</ul>
				</li>
					</c:when>
					<c:when test="${epcLoginVO.adminTypeCd eq '0800'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 롯데</a>
				<ul id="smenu1" style="display:none;">
					<li><a href="javascript:clickTabMenu('<c:url value="/statistics/selectLotteCardMallCalList.do"/>' , '롯데카드몰정산관리');">롯데카드몰정산관리</a></li>
					<li><a href="javascript:clickTabMenu('<c:url value="/statistics/selectLotteCardMallObjectCalList.do"/>' , '롯데카드몰정산대상관리');">롯데카드몰정산대상관리</a></li>
				</ul>
				</li>
					</c:when>
					<c:when test="${epcLoginVO.adminTypeCd eq '0103'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 에누리</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/selectEnuriSummaryList.do?rootAffiliateLinkNo=0103"/>' , '에누리');">에누리</a></li>
					</ul>
				</li>
					</c:when>
					<c:when test="${epcLoginVO.adminTypeCd eq '0104'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 쿠차</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/coochaList.do?rootAffiliateLinkNo=0104"/>' , '쿠차');">쿠차</a></li>
					</ul>
				</li>
					</c:when>
					<c:when test="${epcLoginVO.adminTypeCd eq '0125'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> KTShop</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/selectKtShopList.do?rootAffiliateLinkNo=0125"/>' , 'KTShop');">KTShop</a></li>
					</ul>
				</li>
					</c:when>
					<c:when test="${epcLoginVO.adminTypeCd eq '0001'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 크로스픽업</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/crossPickUpList.do?"/>' , '크로스픽업');">크로스픽업</a></li>
					</ul>
				</li>
					</c:when>
					<c:when test="${epcLoginVO.adminTypeCd eq '0002'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 롯데렌터카</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/rentCarPickUp.do?extStrCd=004"/>' , '롯데렌터카');">롯데렌터카</a></li>
					</ul>
				</li>
					</c:when>
					<c:when test="${epcLoginVO.adminTypeCd eq '0007'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 주유소픽업</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/gasStationPickUp.do?extStrCd=007"/>' , '주유소픽업');">주유소픽업</a></li>
					</ul>
				</li>
					</c:when>
					<c:when test="${epcLoginVO.adminTypeCd eq '0008'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 세븐일레븐픽업</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/rentCarPickUp.do?extStrCd=008"/>' , '세븐일레븐픽업');">세븐일레븐픽업</a></li>
					</ul>
				</li>
					</c:when>
					<c:when test="${epcLoginVO.adminTypeCd eq '0300'}">
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 위메프 CPS</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/weMakePriceEdmSummaryList.do?rootAffiliateLinkNo=0156"/>' , '위메프 CPS');">위메프 CPS</a></li>
					</ul>
				</li>
					</c:when>
				</c:choose>
			</c:when>
			<c:otherwise>
				<li id="s1" class="depth2 plus"><a href="#" onclick="menuclick(1);" class="d2"> 네이버지식쇼핑</a>
					<ul id="smenu1" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/naverEdmSummaryList.do?rootAffiliateLinkNo=0015"/>' , '네이버지식쇼핑');">네이버지식쇼핑</a></li>
					</ul>
				</li>
				<li id="s2" class="depth2 plus"><a href="#" onclick="menuclick(2);" class="d2"> 네이버쇼핑캐스트</a>
					<ul id="smenu2" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/naverEdmSummaryList.do?rootAffiliateLinkNo=0020"/>' , '네이버쇼핑캐스트');">네이버쇼핑캐스트</a></li>
					</ul>
				</li>
				<li id="s3" class="depth2 plus"><a href="#" onclick="menuclick(3);" class="d2"> 쿨그램</a>
					<ul id="smenu3" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/coolGramEdmSalesList.do?rootAffiliateLinkNo=0016"/>' , '쿨그램');">쿨그램</a></li>
					</ul>
				</li>
				<%-- <li id="s4" class="depth2 plus"><a href="#" onclick="menuclick(4);" class="d2"> 아시아나</a>
					<ul id="smenu4" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/asianaMileageList.do"/>' , '아시아나 정산 관리');">아시아나 정산 관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/asianaBalanceAccountList.do"/>' , '아시아나 정산대상 관리');">아시아나 정산대상 관리</a></li>
					</ul>
				</li> --%>
				<li id="s5" class="depth2 plus"><a href="#" onclick="menuclick(5);" class="d2">롯데</a>
					<ul id="smenu5" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/lotteCardMallCalList.do"/>' , '롯데카드몰정산관리');">롯데카드몰정산관리</a></li>
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/lotteCardMallObjectCalList.do"/>' , '롯데카드몰정산대상관리');">롯데카드몰정산대상관리</a></li>
					</ul>
				</li>
				<li id="s6" class="depth2 plus"><a href="#" onclick="menuclick(6);" class="d2"> 레시피기여매출</a>
					<ul id="smenu6" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/recipeContributionEdmList.do?rootAffiliateLinkNo=0018"/>' , '레시피기여매출');">레시피기여매출</a></li>
					</ul>
				</li>
				<li id="s7" class="depth2 plus"><a href="#" onclick="menuclick(7);" class="d2"> 다음 쇼핑하우</a>
					<ul id="smenu7" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/daumEdmSalesSummaryList.do?rootAffiliateLinkNo=0018"/>' , '다음 쇼핑하우');">다음 쇼핑하우</a></li>
					</ul>
				</li>
				<li id="s8" class="depth2 plus"><a href="#" onclick="menuclick(8);" class="d2"> 에누리</a>
					<ul id="smenu8" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/enuriSummaryList.do?rootAffiliateLinkNo=0103"/>' , '에누리');">에누리</a></li>
					</ul>
				</li>
				<li id="s9" class="depth2 plus"><a href="#" onclick="menuclick(9);" class="d2"> 쿠차</a>
					<ul id="smenu9" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/coochaList.do?rootAffiliateLinkNo=0104"/>' , '쿠차');">쿠차</a></li>
					</ul>
				</li>
				<li id="s10" class="depth2 plus"><a href="#" onclick="menuclick(10);" class="d2"> KTShop</a>
					<ul id="smenu10" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/ktShopList.do?rootAffiliateLinkNo=0125"/>' , 'KTShop');">KTShop</a></li>
					</ul>
				</li>
				<li id="s11" class="depth2 plus"><a href="#" onclick="menuclick(11);" class="d2"> 크로스픽업</a>
					<ul id="smenu11" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/crossPickUpList.do?"/>' , '크로스픽업');">크로스픽업</a></li>
					</ul>
				</li>
				<li id="s12" class="depth2 plus"><a href="#" onclick="menuclick(12);" class="d2"> 롯데렌터카</a>
					<ul id="smenu12" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/rentCarPickUp.do?extStrCd=004"/>' , '롯데렌터카');">롯데렌터카</a></li>
					</ul>
				</li>

				<li id="s13" class="depth2 plus"><a href="#" onclick="menuclick(13);" class="d2"> 주유소픽업</a>
					<ul id="smenu13" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/gasStationPickUp.do?extStrCd=007"/>' , '주유소픽업');">주유소픽업</a></li>
					</ul>
				</li>
				<li id="s14" class="depth2 plus"><a href="#" onclick="menuclick(14);" class="d2"> 세븐일레븐픽업</a>
					<ul id="smenu14" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/rentCarPickUp.do?extStrCd=008"/>' , '세븐일레븐픽업');">세븐일레븐픽업</a></li>
					</ul>
				</li>
				<li id="s15" class="depth2 plus"><a href="#" onclick="menuclick(15);" class="d2"> 위메프 CPS</a>
					<ul id="smenu15" style="display:none;">
						<li><a href="javascript:clickTabMenu('<c:url value="/statistics/weMakePriceEdmSummaryList.do?rootAffiliateLinkNo=0156"/>' , '위메프 CPS');">위메프 CPS</a></li>
					</ul>
				</li>
			</c:otherwise>
		</c:choose>
	</c:when>
</c:choose>
			</ul>
			<div id="lbtn_close"><a href="#" onclick="lnbclose();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_lnb_close.gif" alt="" /></a></div>
		</div>
		<div id="lbtn_open" style="display:none;"><a href="#" onclick="lnbopen();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_lnb_open.gif" alt="" /></a></div>
	</div>
</div>
</body>
</html>
