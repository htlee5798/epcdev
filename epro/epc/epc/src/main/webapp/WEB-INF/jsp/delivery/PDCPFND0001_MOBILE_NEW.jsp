<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>명절배송 현황조회</title>
	<style type="text/css">
		html, body {margin:0;padding:0;}
		html, body, div, span, object, iframe, h1, h2, h3, h4, h5, h6, p, a, address, del, em, img, strong, sub, sup, b, i, dl, dt, dd, ol, ul, li,
		fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas, embed, figure, figcaption, footer, header,
		nav, section, time, mark, video {margin:0; padding:0; border:0; font:inherit; vertical-align:baseline;}
		body, h1, h2, h3, h4, h5, h6, div, p, span, table, th, td, input {margin:0;padding:0;/* font-family: Dotum, "돋움"; */font-size:13px;color:#666666;letter-spacing:-0.05em;line-height:1.2;}
		img {border:0;}
		table {border-collapse:collapse;min-width: 100%;}/* 2018-10-01 수정 */
		a:active {color: #666666;text-decoration: none;}
		a:link, a:hover, a:visited {text-decoration: none;}
		ol, ul, li {list-style:none;}

		@font-face {
		font-family: 'Noto Sans';
		src: local("Noto Sans Regular"), url("http://simage.lottemart.com/v3/fonts/NotoSans-Regular.woff2") format("woff2"), url("http://simage.lottemart.com/v3/fonts/NotoSans-Regular.woff") format("woff"); 
		}

		html, body{width: 100%;height: 100%;font-family: 'Noto Sans', sans-serif;}
		div{-webkit-box-sizing: border-box;-moz-box-sizing: border-box;box-sizing: border-box;}
		
		.popup{
			height: 100%;
			overflow: auto;
			border-width: 6px;
			border-style: solid;
			-webkit-border-image: linear-gradient(to bottom right, #d0d891, #f3d5a4, #ecbc9e, #c1b4bf, #c7ca90, #f3d5a8, #d5abd5, #b8adb7, #d87f7f);
			-moz-border-image: linear-gradient(to bottom right, #d0d891, #f3d5a4, #ecbc9e, #c1b4bf, #c7ca90, #f3d5a8, #d5abd5, #b8adb7, #d87f7f);
			-o-border-image: linear-gradient(to bottom right, #d0d891, #f3d5a4, #ecbc9e, #c1b4bf, #c7ca90, #f3d5a8, #d5abd5, #b8adb7, #d87f7f);
			border-image:linear-gradient(to bottom right, #d0d891, #f3d5a4, #ecbc9e, #c1b4bf, #c7ca90, #f3d5a8, #d5abd5, #b8adb7, #d87f7f);
			border-image-slice: 1;
		}
		.popup_wrap{position: relative; margin: 0 auto 5px;padding-bottom: 20px;width: calc(100% - 36px);min-height: calc(100% - 10px);border-bottom: 3px solid #e6eff2;}
		.popup_wrap:before{position: absolute;left: -15px;bottom: -3px;width: 18px;height: 18px;content: "";background: url("http://simage.lottemart.com/images/pp/popup/holi_deli_cont_bg_bottom.png") 0 0 no-repeat;}
		.popup_wrap:after{position: absolute;right: -15px;bottom: -3px;width: 18px;height: 18px;content: "";background: url("http://simage.lottemart.com/images/pp/popup/holi_deli_cont_bg_bottom.png") right 0 no-repeat;}
		.popup_wrap .top{position: relative;margin-top: 5px;border-top: 3px solid #e6eff2;}
		.popup_wrap .top:before{position: absolute;left: -15px;top:-3px;width: 18px;height: 18px;content: "";background: url("http://simage.lottemart.com/images/pp/popup/holi_deli_cont_bg_top.png") 0 0 no-repeat;}
		.popup_wrap .top:after{position: absolute;right: -15px;top:-3px;width: 18px;height: 18px;content: "";background: url("http://simage.lottemart.com/images/pp/popup/holi_deli_cont_bg_top.png") right 0 no-repeat;}
		.popup_wrap h1{padding-top: 25px;	padding-bottom: 15px;font-weight: 600;font-size: 24px;text-align: center;color: #000;}
		
		.popup_wrap h1+p{text-align: center;color: #aaa;font-size: 14px;line-height: 1.5em;-ms-word-break: keep-all;word-break: keep-all;}
		.tb_delivery_search{margin-top: 15px;border-top: 2px solid #333;}
		.tb_delivery_search table{width: 100%;}
		.tb_delivery_search tr{width: 100%;}
		.tb_delivery_search tr:after{content: "";display: block;clear: both;}
		.tb_delivery_search th{height: 35px;float: left;padding: 8px 5px 0;width: 100px;font-size: 13px;text-align: left;border-bottom: 1px solid #dfdfdf;-webkit-box-sizing: border-box;-moz-box-sizing: border-box;box-sizing: border-box;}
		.tb_delivery_search td{height: 35px;float: left;padding: 5px 5px 0;width: calc(100% - 100px);border-bottom: 1px solid #dfdfdf;-webkit-box-sizing: border-box;-moz-box-sizing: border-box;box-sizing: border-box;}
		.tb_delivery_search input{vertical-align: top;padding: 0 5px;width: 100%;height: 25px;border: 1px solid #ddd;font-size: 14px;-webkit-box-sizing: border-box;-moz-box-sizing: border-box;box-sizing: border-box;}
		.delivery_search_btn{margin-top: 15px;margin-bottom: 30px;text-align: center;}
		.delivery_search_btn button{display: inline-block;padding: 7px 30px;border: none;font-size: 13px;color: #fff;background: #333;cursor: pointer;}

		.tb_delivery_list_warp{width: 100%;height: auto;overflow-x: auto;border-top: 2px solid #333;}
		.tb_delivery_list_warp th{position: relative;white-space: nowrap;padding: 10px;border-bottom: 1px solid #dfdfdf}
		.tb_delivery_list_warp th:before{position: absolute;left: 0;top: 50%;margin-top: -5px;content: "";width: 1px;height: 10px;background-color: #dfdfdf;}
		.tb_delivery_list_warp th:first-child:before{background-color: transparent;}
		.tb_delivery_list_warp td{position: relative;padding: 10px;white-space: nowrap;text-align: center;border-bottom: 1px solid #dfdfdf;}
		.tb_delivery_list_warp td:before{position: absolute;left: 0;top: 50%;margin-top: -5px;content: "";width: 1px;height: 10px;background-color: #dfdfdf;}
		.tb_delivery_list_warp td:first-child:before{background-color: transparent;}
		.tb_delivery_list_warp .no_delivery_list{padding: 25px;text-align: center;}

		.popup_notice{margin-top: 30px;}
		.popup_notice li{line-height: 1.5em;font-size: 12px;color: #969696;}
		.popup_notice_info{padding-left: 10px;}

		.popup_cnt:before{position: absolute;left: -15px;top: 15px;content: "";width: 3px;height: calc(100% - 30px);background-color: #e6eff2;}
		.popup_cnt:after{position: absolute;right: -15px;top: 15px;content: "";width: 3px;height: calc(100% - 30px);background-color: #e6eff2;}

		.popup_paging {padding:30px 0 10px;text-align:center;}
		.popup_paging span {position: relative;}
		.popup_paging span.bar:before{position: absolute;left: 0;top: 50%;content: "";margin-top: -5px;width: 1px;height: 10px;background-color: #dedede;}
		.popup_paging span a {display: inline-block;padding: 0 5px;color:#666; font-size:12px;line-height: 1.5em;vertical-align: middle;}
		.popup_paging span.bar a{padding:0 10px;}
		.popup_paging span.bar2 a{padding:0 10px;}
	</style>
	
	
	<c:set var="agent" value='${Agent}' />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
	
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

<body>
	<!-- popup size 700 * 650 -->
	<div class="popup" id="popup">
		<div class="popup_wrap" id="popup_wrap">
		     
			<div class="top" id="top">
				<h1>명절배송 현황 조회</h1>
				<p>보내시는분 핸드폰 번호 또는 운송장번호를 입력하시고 조회 버튼을 누르세요. 입력 시 '-' 제외하고 입력해주세요.</p>
			</div>
			<div class="popup_cnt" id="popup_cnt">
				<!-- 조회 -->
				<form id="sFrm" name="sFrm" method="post" onsubmit="return condCheck();">
				<input type="hidden" id="currentPage" name="currentPage" value="${sc.currentPage}" />
				<input type="hidden" id="accept_no" name="accept_no" value="${sc.no}"  />
				<div class="tb_delivery_search">
					<table cellspacing="0" cellpadding="0" summary="">
						<caption></caption>
						
						<tbody>
							<tr>
								<th scope="col"><label for="no">접수번호</label></th>
								<td><input type="text" id="no" name="no" value="${sc.no}"></td> <!-- input id, name 변경 가능 -->
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
							
							<tbody>
								<tr>
									<th>배송사</th>
									<th>운송장번호</th>
									<th>보내는 분</th>
									<th>받으실 분</th>
									<th>배송지</th>
									<th>배송상태</th>
								</tr>
								
								<!-- 조회 결과없음 -->
						<c:if test="${fn:length(acceptList) == 0}">
							<c:if test="${fn:trim(sc.sc_hodeco_invoice_no) ne ''}">
								<c:if test="${fn:substring(sc.sc_hodeco_invoice_no,0,2) eq '50'}">
									<script>
										//Common.centerPopupWindow('http://www.hanjinexpress.hanjin.net/customer/hddcw18.tracking?w_num=${sc.sc_hodeco_invoice_no}', '_popupZipCode_', {width : 500, height : 410});
										//window.open('http://www.hanjinexpress.hanjin.net/customer/hddcw18.tracking?w_num=${sc.sc_hodeco_invoice_no}', '_popupZipCode_', 'width=500, height=410');
										location.href="http://m.hanjin.co.kr/?q=${sc.sc_hodeco_invoice_no}";
									</script>
								</c:if>
								<c:if test="${fn:substring(sc.sc_hodeco_invoice_no,0,2) eq '40'}">
									<script>
										location.href="http://ftr.alps.llogis.com:8260/ftr/tracking.html?${sc.sc_hodeco_invoice_no}";
									</script>
								</c:if>
							</c:if>
								<tr>
									<td colspan="6" class="no_delivery_list">검색 결과가 없습니다.</td>
								</tr>
						</c:if>
								<!-- //조회 결과없음 -->	

								<!-- 조회결과 있음 -->
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
												<a href="http://m.hanjin.co.kr/?q=${data.HODECO_INVOICE_NO}">${data.HODECO_INVOICE_NO}</a>
											</c:if>
											<c:if test="${data.DELI_COMP_CD eq '04'}">
												<a href="http://ftr.alps.llogis.com:8260/ftr/tracking.html?${data.HODECO_INVOICE_NO}">${data.HODECO_INVOICE_NO}</a>
											</c:if>
										</c:otherwise>
									</c:choose>
									</td>
									<%-- <td>${fn:substring(data.SEND_PSN_NM,0,1)}*${fn:substring(data.SEND_PSN_NM,2,5)}</td>
									<td>${fn:substring(data.RECV_PSN_NM,0,1)}*${fn:substring(data.RECV_PSN_NM,2,5)}</td>
									<td>${fn:split(data.RECV_PSN_ZIP_ADDR,' ')[0]} ${fn:split(data.RECV_PSN_ZIP_ADDR,' ')[1]} ******</td> --%>
									<td>${data.SEND_PSN_NM}</td>
									<td>${data.RECV_PSN_NM}</td>
									<td>${data.RECV_PSN_ZIP_ADDR}</td> 
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
							</c:forEach>	
								<!-- //조회결과 있음 -->	
									
								
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
	</div>
</body>
</html>