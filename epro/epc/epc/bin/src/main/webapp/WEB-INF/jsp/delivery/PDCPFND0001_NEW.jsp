<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title></title>
	<style type="text/css">
		
			html, body {margin:0;padding:0;}
			html, body, div, span, object, iframe, h1, h2, h3, h4, h5, h6, p, a, address, del, em, img, strong, sub, sup, b, i, dl, dt, dd, ol, ul, li,
			fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas, embed, figure, figcaption, footer, header,
			nav, section, time, mark, video {margin:0; padding:0; border:0; font:inherit; vertical-align:baseline;}
			body, h1, h2, h3, h4, h5, h6, div, p, span, table, th, td, input {margin:0;padding:0;font-family: Dotum, "돋움";font-size:12px;color:#666666;letter-spacing:-0.05em;line-height:1.2;}
			img {border:0;}
			table {border-collapse:collapse;}
			a:active {color: #666666;text-decoration: none;}
			a:link, a:hover, a:visited {text-decoration: none;}
			ol, ul, li {list-style:none;}

			#popup {position:relative;width:680px;margin:0 auto;background:url('//simage.lottemart.com/images/pp/popup/holi_deli_top_bg.png') center 14px no-repeat #bbd3dc;padding:20px;}
			#popup_wrap {width:668px;margin:0 auto;padding:5px 0 5px 0;background:#fff;/*background:url('//simage.lottemart.com/images/pp/popup/holi_deli_cont_bg_center.png') center top repeat-y #fff;*/}
			#popup_wrap:after{display:block;position:absolute;top:43px;left:31px;bottom:43px;width:652px;border-left:3px solid #e6eff2;border-right:3px solid #e6eff2;/*background:url('//simage.lottemart.com/images/pp/popup/holi_deli_cont_bg_center.png') center top repeat-y;*/content:"";z-index:1;}
			#popup #top {background:url('//simage.lottemart.com/images/pp/popup/holi_deli_cont_bg_top.png') center top no-repeat;}
			#popup #top img {vertical-align:top;}
			#popup #top h1{padding:57px 0 15px 0;text-align:center;}
			#popup #top p{padding:0 0 30px 0;text-align:center;}

			#popup_cnt {position:relative;min-height:667px;padding:0 29px 40px 29px;background:url('//simage.lottemart.com/images/pp/popup/holi_deli_cont_bg_bottom.png') center bottom no-repeat;z-index:2;}
			.tb_delivery_search {width:610px;margin:0 0 50px 0;border-top:2px solid #333;}
			.tb_delivery_search th {text-align:left;border-bottom:1px solid #dfdfdf;font-size:14px;color:#333;}
			.tb_delivery_search td {text-align:left;border-bottom:1px solid #dfdfdf;}
			.tb_delivery_search td input{width:194px;border:1px solid #ddd;margin:7px 0 7px 0;padding:5px;font-size:16px;color:#333;}
			.tb_delivery_search .deli_tran{}
			.tb_delivery_search .deli_tran_td input{width:508px;}
			.tb_delivery_search .delivery_search_btn{margin:15px 0 0 0;text-align:center;}
			.tb_delivery_search .delivery_search_btn button{display:inline-block;padding:0;margin:0;border:none;min-width:120px;min-height:40px;text-align:center;font-size:16px;color:#fff;background:#333;line-height:2.2;cursor:pointer;}

			.tb_delivery_list {width:610px;border-top:2px solid #333;}
			.tb_delivery_list .tb_delivery_list_warp{}
			.tb_delivery_list th {height:50px;border-bottom:1px solid #dfdfdf;background:url('//simage.lottemart.com/images/pp/popup/paging_bar.gif') no-repeat 0 19px;font-size:14px;color:#333;vertical-align:middle;}
			.tb_delivery_list th:first-child{background:none;}
			.tb_delivery_list td{height:50px;border-bottom:1px solid #dfdfdf;background:url('//simage.lottemart.com/images/pp/popup/paging_bar.gif') no-repeat 0 19px;font-size:14px;color:#969696;vertical-align:middle;text-align:center;}
			.tb_delivery_list td:first-child{background:none;}
			.tb_delivery_list .no_delivery_list{height:99px;font-size:20px;font-weight:bold;text-align:center;line-height:20px;}

			.popup_paging {width:610px;margin:0 auto;padding:40px 0 40px 0;text-align:center;}
			.popup_paging span.bar {background:url('//simage.lottemart.com/images/pp/popup/paging_bar.gif') no-repeat 3px 1px;padding:0 0 0 10px;font-size:11px;}
			.popup_paging span.bar a {color:#666666; padding:0 4px 0 4px;}
			.popup_paging span.bar2 a {font-size:11px; color:#666666; padding:0 4px 0 4px;}
			.popup_paging span img {vertical-align:middle;}

			.popup_notice {margin:5px 0 0 0;font-size:12px;color:#969696;}
			.popup_notice li{padding:10px 0 0 0;}
			.popup_notice_info{}
			.popup_notice_info li{padding-left:5px;}

	</style>
	<script type="text/javascript" src="/js/jquery/jquery-1.5.2.js"></script>
	<script type="text/javascript" src="/js/pp/common.js"></script>
	<script>
		function condCheck(){
			
			if($('#no').val() == "" && $('#no2').val() == "" &&  $('#sc_hodeco_invoice_no').val() == ""){
				alert("배송조회를 위한 정보가 입력되지 않았습니다.");
				return false;
			}
			
			if( $('#no').val() != "" && isNaN(parseInt($('#no').val())) ){
				alert("접수번호는 숫자만 입력하세요.");
				return false;
			}
			
			if( $('#no2').val() != "" && isNaN(parseInt($('#no2').val())) ){
				alert("핸드폰 번호는 숫자만 입력하세요.");
				return false;
			}
			
			if( $('#sc_hodeco_invoice_no').val() != "" && isNaN(parseInt($('#sc_hodeco_invoice_no').val())) ){
				alert("운송장번호는 숫자만 입력하세요.");
				return false;
			}
			
			return true;
		}
		
		function goPage(page){
			if(page == null) page = 1;
			
			$('#currentPage').val(page);
			$('#sFrm').submit();
		}
		
		function doSearchStr(strCd) {
			var targetUrl = "<c:url value='/delivery/selectStr.do'/>?strCd="+strCd;
			Common.centerPopupWindow(targetUrl, 'popupStrDetail', {width: 280, height: 220});
		}
		
		$(document).ready(function(){
			//입력항목에서 엔터 시 조회처리
			$("input").keypress( function( e){
				if (e.which == 13) {
					$('#sFrm').submit();
				}
			});
			
			if ("${accept_no}" != '') {
				$('#sFrm').submit();
			}
			
		});
		
		
		
	</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
	<!-- popup size 700 * 650 -->
	<div id="popup">
		<div id="popup_wrap">
			<div id="top">
				<h1><img src="${lfn:getString('system.cdn.static.path')}/images/pp/popup/holi_deli_top_tit.jpg" alt="명절배송 현황 조회" /></h1>
				<p><img src="${lfn:getString('system.cdn.static.path')}/images/pp/popup/holi_deli_top_txt.jpg" alt="보내시는분 핸드폰 번호 또는 운송장번호를 입력하시고 조회 버튼을 누르세요. 입력 시 '-' 제외하고 입력해주세요."></p>
			</div>
			<div id="popup_cnt">
				<!-- 조회 -->
				<form id="sFrm" name="sFrm" method="post" onsubmit="return condCheck();">
					<input type="hidden" id="currentPage" name="currentPage" value="${sc.currentPage}" />
					<input type="hidden" id="accept_no" name="accept_no" value="${sc.no}"  />
					<div class="tb_delivery_search">
						<table cellspacing="0" cellpadding="0" summary="">
							<caption></caption>
							<colgroup>
								<col width="88px">
								<col width="217px">
								<col width="98px">
								<col width="207px">
							</colgroup>
							<tbody>
								<tr>
									<th scope="col"><label for="no">접수번호</label></th>
									<td><input type="text" id="no" name="no" value="${sc.no}"></td>
									<th scope="col"><label for="no2">휴대전화번호</label></th>
									<td><input type="text" id="no2" name="no2" value="${sc.no2}"></td>
								</tr>
								<tr>
									<th scope="col" colspan="1" class="deli_tran"><label for="sc_hodeco_invoice_no">운송장번호</label></th>
									<td colspan="3" class="deli_tran_td"><input type="text" id="sc_hodeco_invoice_no" name="sc_hodeco_invoice_no" value="${sc.sc_hodeco_invoice_no}"></td>
								</tr>
							</tbody>
						</table>
						<div class="delivery_search_btn"><button type="button" onclick="goPage();">조회</button></div>
					</div>
				</form>
				<!-- //조회 -->

				<!-- 조회 결과 -->
				<div class="tb_delivery_list">
					<div class="tb_delivery_list_warp">
						<table cellspacing="0" cellpadding="0">
							<colgroup>
								<col width="80px">
								<col width="100px">
								<col width="80px">
								<col width="80px">
								<col width="170px">
								<col width="100px">
							</colgroup>
							<tbody>
								<tr>
									<th>배송사</th>
									<th>운송장번호</th>
									<th>보내는 분</th>
									<th>받으실 분</th>
									<th>배송지</th>
									<th>배송상태</th>
								</tr>
								<c:choose>
									<c:when test="${fn:length(acceptList) > 0}">
								 <!-- 조회 결과 있음  -->
										<c:forEach var="data" items="${acceptList}">
											<tr>
												<td>
													<c:choose>
														<%--원격지배송이면--%>
														<c:when test="${data.ACCEPT_DIVN_CD eq '30' or data.GUN_YN eq 'Y'}">
															<a href="javascript:doSearchStr(${data.DELI_STR_CD});">${data.DELI_STR_CD_NM}</a>
														</c:when>
														<c:otherwise>
															${data.HODECO_NM}
														</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
													<%--원격지배송이면--%>
														<c:when test="${data.ACCEPT_DIVN_CD eq '30'}">
															${data.HODECO_INVOICE_NO}
														</c:when>
														<c:otherwise>
															<c:if test="${data.DELI_COMP_CD eq '08'}">
																<a href="#" onclick="Common.centerPopupWindow('http://www.hanjinexpress.hanjin.net/customer/hddcw18.tracking?w_num=${data.HODECO_INVOICE_NO}', '_popupZipCode_', {width : 500, height : 410});">${data.HODECO_INVOICE_NO}</a>
															</c:if>
															<c:if test="${data.DELI_COMP_CD eq '04'}">
																<a href="#" onclick="Common.centerPopupWindow('http://ftr.alps.llogis.com:8260/ftr/tracking.html?${data.HODECO_INVOICE_NO}', '_popupZipCode_', {width : 620, height : 600, scrollBars : 'YES'});">${data.HODECO_INVOICE_NO}</a>
															</c:if>
														</c:otherwise>
													</c:choose>
												</td>
												<%-- <td>${fn:substring(data.SEND_PSN_NM,0,1)}*${fn:substring(data.SEND_PSN_NM,2,5)}</td>
												<td>${fn:substring(data.RECV_PSN_NM,0,1)}*${fn:substring(data.RECV_PSN_NM,2,5)}</td>
												<td class="address">
													${fn:split(data.RECV_PSN_ZIP_ADDR,' ')[0]} ${fn:split(data.RECV_PSN_ZIP_ADDR,' ')[1]} ******
												</td> --%>
												<td>${data.SEND_PSN_NM}</td>
												<td>${data.RECV_PSN_NM}</td>
												<td class="address">${data.RECV_PSN_ZIP_ADDR}</td>
												<td>
													<c:choose>
														<%--러브콜상태에 따라--%>
														<c:when test="${data.LOVECALL_CHK > 0}">
															배송종료
														</c:when>
														<c:when test="${data.DELI_PRGS_STEP_STS_CD_NM eq '배송불가'}">
															배송중
														</c:when>
														<c:otherwise>
															${data.DELI_PRGS_STEP_STS_CD_NM}
														</c:otherwise>
													</c:choose>
												</td>
											</tr>
								<!-- //조회 결과 있음 -->	
										</c:forEach>
									</c:when>
									<c:otherwise>
								<!-- 조회 결과없음 -->
										<c:if test="${fn:trim(sc.sc_hodeco_invoice_no) ne ''}">
											<c:if test="${fn:substring(sc.sc_hodeco_invoice_no,0,2) eq '50'}">
												<script>
													//Common.centerPopupWindow('http://www.hanjinexpress.hanjin.net/customer/hddcw18.tracking?w_num=${sc.sc_hodeco_invoice_no}', '_popupZipCode_', {width : 500, height : 410});
													window.open('http://www.hanjinexpress.hanjin.net/customer/hddcw18.tracking?w_num=${sc.sc_hodeco_invoice_no}', '_popupZipCode_', 'width=500, height=410');
												</script>
											</c:if>
											<c:if test="${fn:substring(sc.sc_hodeco_invoice_no,0,2) eq '40'}">
												<script>
													window.open('http://ftr.alps.llogis.com:8260/ftr/tracking.html?${sc.sc_hodeco_invoice_no}', '_popupZipCode_', 'width=620, height=600');
												</script>
											</c:if>
										</c:if>
										<tr>
											<td colspan="6" class="no_delivery_list">검색 결과가 없습니다.</td>
										</tr>
								<!-- //조회 결과없음 -->	
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
					<!-- paging -->
					<div class="popup_paging">
						<c:set var="lastPage" value="${sc.totalCount / sc.rowsPerPage + ( sc.totalCount % sc.rowsPerPage == 0 ? 0 : 1)}"/>
						<fmt:parseNumber var="lastPage" value="${lastPage}" integerOnly="true" />
						<fmt:parseNumber var="curPage" value="${sc.currentPage}" integerOnly="true" />
						<fmt:parseNumber var="startPageBlock" value="${(curPage-1)/5}" integerOnly="true" />
						<fmt:parseNumber var="startPageBlock" value="${startPageBlock*5+1}" integerOnly="true" />
						<c:if test="${curPage > 1}">
							<span><a href="#" onclick="goPage(1);"><img src="${lfn:getString('system.cdn.static.path')}/images/pp/popup/btn_first.gif" alt="첫페이지" /></a></span>
							<span><a href="#" onclick="goPage(${curPage > 1 ? curPage -1 : 1});"><img src="${lfn:getString('system.cdn.static.path')}/images/pp/popup/btn_prev.gif" alt="이전페이지" /></a></span>
						</c:if>
						<c:forEach var="seq" begin="${startPageBlock}" end="${startPageBlock +4}" varStatus="loopStatus">
							<c:if test="${seq <= lastPage}">
								<span class="bar${loopStatus.first ? '2' : ''}"><a href="#" onclick="goPage(${seq});" style="${seq == curPage ? 'color:#ff003a;font-weight:bold;' : ''}">${seq}</a></span>
							</c:if>
							<c:set var="startPageBlock" value="${startPageBlock+1}"/>
						</c:forEach>
						<c:if test="${curPage < lastPage}">
							<span><a href="#" onclick="goPage(${startPageBlock < lastPage ? startPageBlock : lastPage});"><img src="${lfn:getString('system.cdn.static.path')}/images/pp/popup/btn_next.gif" alt="다음페이지" /></a></span>
							<span><a href="#" onclick="goPage(${lastPage});"><img src="${lfn:getString('system.cdn.static.path')}/images/pp/popup/btn_end.gif" alt="끝페이지" /></a></span>
						</c:if>
					</div>
					<!-- //paging -->
					
					<ul class="popup_notice">
						<li>
							<strong>※ 배송상태 안내</strong>
							<ul class="popup_notice_info">
								<li>가) 접수: 마트에서 배송등록완료 후 택배사로 상품 인계 중이며, 배송시작 전 배송상태입니다.</li>
								<li>나) 배송 중 : 택배사에서 받으시는 분께 상품을 배송 중인 상태입니다. 클릭하시면 세부 배송현황을 확인 가능합니다.</li>
								<li>다) 배송완료 : 받으시는 분께 상품을 배송완료된 상태입니다.</li>
							</ul>
						</li>
						<li>※ 운송장번호를 클릭하시면 세부 배송 현황 조회를 하실 수 있습니다.</li>
						<li>※ 배송관련 문의 사항은 배송의뢰점포로 연락하시면 확인 가능합니다.</li>
						<li>※ 상기 배송정보는 고객님의 사전 동의 하에 작성된 내용입니다.</li>
					</ul>
				</div>
				<c:if test="${close eq 'Y'}">
					<div class="tb_delivery_search" style="border-top:none;margin-bottom:0;">
						<div class="delivery_search_btn"><button type="button" onclick="location.href='http://m.lottemart.com/mobile/holidays/main.do'">닫기</button></div>
					</div>
				</c:if>
		</div>
	</div>
</body>
</html>