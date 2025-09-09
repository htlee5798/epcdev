<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"   uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8"/>
<meta http-equiv="Content-Script-Type" content="text/javascript"/>
<meta http-equiv="Content-Style-Type" content="text/css"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="viewport" content="width=1024"/>
<meta name="keywords" content=""/>
<meta name="description" content=""/>
<%@ include file="prodHead.jsp"%>
<script type="text/javascript">
$(document).ready( function () {
	__BxSliderProdDetail('.prod-detail-info .thumb-slist-remote');
	__BxSliderProdDetail('.prod-detail-info .thumb-slist-movie');
	
	$('.imgStoB').click(function () {
		$("#imgBigSize").attr("src", $(this).attr("src"));
	});
	
	$('.imgStoB').each(function(index){
		if(index == 0){
			$("#imgBigSize").attr("src", $(this).attr("src"));
		}
	});
});
</script>
</head>
<body>
<nav class="skip-menu">
	<a href="#contents">콘텐츠 바로가기</a>
	<a href="#gnb">메뉴 바로가기</a>
</nav>
<article id="document" class="wrapper-category">
	<header id="header">
		<nav class="gnb"></nav>
	</header>
	<hr hidden="hidden">
	<div id="container">
		<article id="contents" style="padding-top:5px;">
			<div class="wrap-in">
				<section class="prod-detail">
					<h1 class="detail-type"><c:out value="${newProdDetailInfo.prodNm}" /></h1>
					<div class="prod-detail-info clear-after">
						<div class="flt-left">
							<div class="wrap-thumb">
								<div class="thumb-bigsize">
									<img id="imgBigSize" src="" alt="<c:out value='${newProdDetailInfo.prodNm}' />" class="thumb">
								</div>
								<nav class="tabmenu jq-tab">
									<a href="#prod-tab1">상품 이미지</a>
									<a href="#prod-tab2" class="hidden">동영상</a>
								</nav>
								<div class="wrap-tab-cont">
									<div class="tab-cont active" id="prod-tab1">
										<h2 class="hidden">상품 이미지</h2>
										<div class="wrap-thumb-slist">
											<ul class="thumb-slist-remote clear-after">
												<c:forEach  items="${onlineImageList}" var="imageFile"  varStatus="index" >
													<li><a href="#"><img src="<c:out value='${imagePath}'/><c:out value='/${subFolderName}'/><c:out value='/${imageFile}'/>"  class="thumb imgStoB"></a></li>
												</c:forEach>
											</ul>
										</div>
									</div>
								</div>
							</div>
							<!-- 고객만족도 -->
							<div class="prod-sfaction">
								<p class="customer">
									<strong class="tit">고객만족도</strong>
									<em class="score">0.0</em>
									<span class="ico-star-type1">
										<em style="width:80%;">10.0 / 10점 만점</em>
									</span>
								</p>
								<p class="comment">
									<strong class="tit">상품평</strong>
									<a href="#tab02"><span class="num-n"><strong>5</strong></span>건</a>
								</p>
							</div>
							<!-- //고객만족도 -->
						</div>
						<div class="flt-right">
							<fieldset>
								<legend>상품 상세 구매</legend>
								<div class="wrap-info">
									<div class="prod-relation-info">
										
										<!-- 판매가 / 최대혜택가격 -->
										<div class="relation-price">
											<!-- 판매가 노출 -->
											<dl>
												<dt>판매가</dt>
												<dd>
													<span class="num-n">
														<c:set var="norProdSalePrc" value="${fn:escapeXml(newProdDetailInfo.norProdSalePrc)}"/>
														<em><fmt:formatNumber value="${norProdSalePrc}" pattern="#,###" /></em>
													</span>
													<span class="won">원</span>
												</dd>
											</dl>
											<!-- //판매가 노출 -->
										</div>
										<!-- //판매가 / 최대혜택가격 -->

										<!-- 할인 프로모션 내역 -->
										<div class="relation-promotion">
											<ul>
												<li>
													<em>할인영역</em>
													할인영역 입니다.
												</li>
											</ul>
										</div>
										<!-- //할인 프로모션 내역 -->

										<!-- 구매혜택 -->
										<div class="relation-notice">
											<dl>
												<dt>구매혜택</dt>
												<dd>
													<em class="txt-wbrown">구매혜택 영역 입니다.</em>
												</dd>
											</dl>
										</div>
										<!-- //구매혜택 -->

										<!-- 카드 할인 프로모션 -->
										<div class="relation-card">
											<dl>
												<dt>청구할인</dt>
												<dd>
													<ul>
														<li>
															청구할인 영역 입니다.
														</li>
													</ul>
												</dd>
											</dl>
											<dl>
												<dt>카드혜택</dt>
												<dd>
													<button type="button" class="btn-tbl layerpop-trigger" data-trigger="layerpop-interest-free">무이자할부안내</button>
													<button type="button" class="btn-tbl layerpop-trigger" data-trigger="layerpop-charge-point">포인트 결제 안내</button>
													<button type="button" class="btn-tbl layerpop-trigger" data-trigger="layerpop-partnership-iscount">롯데마트 제휴카드 10% 청구할인 안내</button>
												</dd>
											</dl>
										</div>
										<!-- //카드 할인 프로모션 -->

										<!-- 적립 프로모션 -->
										<div class="relation-reward">
											<dl>
												<dt>L.POINT 적립</dt>
												<dd>
													<button type="button" class="btn-tbl layerpop-trigger" data-trigger="layerpop-benefits-class">회원등급별 혜택 안내</button>
												</dd>
											</dl>
										</div>
										<!-- //적립 프로모션 -->

										<!-- 각 옵션 선택 영역 -->
										<div class="relation-option">
											
											<dl class="option-select-relation">
												<dt>옵션</dt>
												<dd>
													<div class="select-type2 select-type2-1">
														<select class="selectmenu prod-option-select">
															<option selected>옵션 영역입니다.</option>
														</select>
													</div>
												</dd>
											</dl>
										</div>
										<!-- //각 옵션 선택 영역 -->
									</div>
									<div class="prod-decision-price">
										구매예정금액
										 <span class="num-n">
										 	<c:set var="norProdSalePrc" value="${fn:escapeXml(newProdDetailInfo.norProdSalePrc)}"/>
										 	<strong><fmt:formatNumber value="${norProdSalePrc}" pattern="#,###" /></strong> <span class="won">원</span>
										 </span>
									</div>
									<div class="prod-decision">
										<p class="decision-notice">상기 금액은 결제 금액과 다를 수 있습니다.</p>
										<button type="button" class="btn-large-type1 layerpop-trigger2" data-trigger="layerpop-basket"><i class="ico-basket">장바구니</i></button>
										<button type="button" class="btn-large-type3 layerpop-trigger2" data-trigger="layerpop-purchase"><i class="ico-buynow">바로구매</i></button>
										<button type="button" class="sp-btn-selected layerpop-trigger2" data-trigger="layerpop-wish-list" data-hide-type="auto">찜</button>
									</div>
								</div>
							</fieldset>
						</div>
					</div>
				</section>
			</div>
			<div class="wrap-even"><div class="wrap-in-only">
				<section class="prod-dcomment">
					<!-- 상품정보 -->
					<nav class="fulltab-prod">
						<a href="#tab01" class="active">상품정보</a>
						<a href="#tab04">배송/교환/반품</a>
					</nav>
					
					<section class="wrap-tab-cont" id="tab01">
						<h2 class="hidden">상품정보</h2>
						<div class="prod-dbanner"><c:out value='${newProdDetailInfo.prodDesc }' /></div>
						<div class="tbl-lst-v">
							<table>
								<caption></caption>
								<colgroup>
									<col style="width:25%">
									<col style="width:auto">
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">판매코드</th>
										<td><c:out value="${newProdDetailInfo.sellCd}" /></td>
									</tr>
									<c:forEach  items="${norProdDetailList}" var="norProdDetailInfo"  varStatus="index" >
										<tr>
											<th scope="row"><c:out value="${norProdDetailInfo.COL_NM" /></th>
											<td><c:out value="${norProdDetailInfo.COL_VAL}" /></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						<h3>제품 안전인증정보</h3>
						<div class="tbl-lst-v">
							<table>
								<caption></caption>
								<colgroup>
									<col style="width:25%">
									<col style="width:auto">
								</colgroup>
								<tbody>
									<tr>
										<th scope="row" class="ksc"><img src="<c:out value='${frontUrl}' />/v3/images/layout/img_kcs.gif" alt="KCS인증마크" ><c:out value="${kcProdDetailList[0].INFO_GRP_NM}"/></th>
										<td>
										<c:forEach items="${kcProdDetailList}"  var="certi" >
											<li><c:out value="${certi.COL_NM }"/>&nbsp;&nbsp;<c:out value="${ certi.COL_VAL }"/></li>
										</c:forEach>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</section>
					<!-- //상품정보 -->

					<!-- 배송/교환/반품 -->
					<section class="wrap-tab-cont" id="tab04">
						<h2 class="hidden">배송/교환/반품 250건</h2>
						<h3 class="subsection0">주문에서 배송까지! 믿을 수 있는 롯데마트몰</h3>
						<ol class="prod-dprocess clear-after">
							<li>
								<strong><span>01</span>주문접수</strong>
								<p><span class="txt-wbrown">주문 즉시 인근 롯데마트 매장</span>에<br>주문한 상품 내역이 접수됩니다.</p>
								<img src="<c:out value='${frontUrl}' />/v3/images/layout/img_dprocess01.jpg" alt="주문접수 이미지">
							</li>
							<li>
								<strong><span>02</span>상품선별</strong>
								<p><span class="txt-wbrown">숙련된 장보기 전문가</span>들이 매장에서<br>가장 신선한 상품을 꼼꼼하게 선별합니다.</p>
								<img src="<c:out value='${frontUrl}' />/v3/images/layout/img_dprocess02.jpg" alt="상품선별 이미지">
							</li>
							<li>
								<strong><span>03</span>포장 및 출하</strong>
								<p>안전하고 신선하게 포장하여,<br><span class="txt-wbrown">냉장, 냉동 상태를 그대로 유지</span>하여<br>출하합니다.</p>
								<img src="<c:out value='${frontUrl}' />/v3/images/layout/img_dprocess03.jpg" alt="포장 및 출하 이미지">
							</li>
							<li>
								<strong><span>04</span>광속배송</strong>
								<p><span class="txt-wbrown">당일배송, 일 8회차 배송!</span><br>냉장, 냉동은 기본! 고객님께서 원하시는 시간에 직접 배송해드립니다.</p>
								<img src="<c:out value='${frontUrl}' />/v3/images/layout/img_dprocess04.jpg" alt="광속배송 이미지">
							</li>
						</ol>
						<h3>배송 안내</h3>
						<div class="tbl-lst">
							<table>
								<caption></caption>
								<colgroup>
									<col style="width:15%">
									<col style="width:28.3%">
									<col style="width:28.3%">
									<col style="width:auto">
								</colgroup>
								<thead>
									<tr>
										<th scope="col">구분</th>
										<th scope="col">매장배송</th>
										<th scope="col">매장택배</th>
										<th scope="col">업체택배배송</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>내용</td>
										<td class="subject">거주지 인근 매장에서 직접 배달</td>
										<td class="subject">
											<ul class="bul-circle">
												<li>인근에 롯데마트 매장이 없는 지역</li>
												<li>매장 상품 중 일부 택배운영 상품</li>
											</ul>
										</td>
										<td class="subject">협력업체 및 산지에서 택배발송</td>
									</tr>
									<tr>
										<td>배송시간</td>
										<td class="subject">
											<ul class="bul-circle">
												<li><i class="txt-wbrown">오후 4시 이전 결제 완료 시</i> 당일배송 가능</li>
												<li><i class="txt-wbrown">11:00~21:00 하루 8회 배송</i><br/>(단, 김포점, 동두천점, 대구율하점, 나주점 하루 4회 배송)</li>
											</ul>
										</td>
										<td class="subject" colspan="2">
											<ul class="bul-circle">
												<li>배송기일 : 결제일 다음날부터 3일 이내 발송<br/>(단, 토요일, 일요일, 공휴일은 배송기일에서 제외됩니다.)</li>
												<li>산간 도서 등 일부지역은 배송기일이 추가적으로 소요될 수 있으며,<br/>상품의 재고 상황에 따라 다소 지연될 수 있사오니 이점 양해하여 주시기 바랍니다.</li>
											</ul>
										</td>
									</tr>
									<tr>
										<td>배송지역</td>
										<td class="subject">
											<button type="button" class="btn-form-type3">배송가능지역 매장 보기</button>
										</td>
										<td class="subject">전국배송 가능</td>
										<td class="subject">전국배송 가능</td>
									</tr>
									<tr>
										<td>장보기 대행료</td>
										<td class="subject">
											<strong>결제금액 기준</strong>
											<ul class="bul-circle">
												<li>3만원 미만 : <i class="txt-wbrown">4,000원</i></li>
												<li>3만원 이상 : <i class="txt-wbrown">무료</i></li>
											</ul>
										</td>
										<td class="subject">
											<strong>결제금액 기준</strong>
											<ul class="bul-circle">
												<li>3만원 미만 : <i class="txt-wbrown">4,000원</i></li>
												<li>3만원 이상 : <i class="txt-wbrown">무료</i>
													<br/>(일부 도서산간지역은 추가 배송비가 <br/>발생할 수 있습니다.)
												</li>
											</ul>
										</td>
										<td class="subject">업체별 상이<br/>업체 배송상품 중 배송비는 상품별로<br/> 다를 수 있습니다.<br/>(일부 도서산간지역은 추가배송비가 발생할 수 있습니다.)</td>
									</tr>
								</tbody>
							</table>
						</div>
						<p class="delivery-exception">일부 전자제품의 경우 <span class="txt-wbrown">결제일로부터 3~5일 이내 배송</span>됩니다.(업체마다 배송기간이 상이할 수 있습니다.)</p>
						<h3>교환 및 반품 안내</h3>
						<div class="tbl-lst-v">
							<table>
								<caption></caption>
								<colgroup>
									<col style="width:15%">
									<col style="width:auto">
								</colgroup>
								<tbody>
									<tr>
										<th scope="row">가능한 경우</th>
										<td>
											<ul class="bul-circle">
												<li>상품 등을 실제 받으신 날로부터 <i class="txt-wbrown">7일 이내</i>(단, 신선선물세트는 고객 변심으로 인한 환불이 되지 않습니다.)</li>
												<li>받으신 상품의 내용이 표시된 광고 사항과 다른 경우, 상품들을 받으신 날로부터 3개월 이내 또는 그 사실을 안 날로 또는 알 수 있었던 날로부터 30일 이내</li>
												<li>전자상거래 등에서 소비자 보호에 관한 법률에 규정되어 있는 소비자 청약철회 가능범위에 해당되는 경우</li>
												<li>교환 및 반품 문의는 <strong>고객센터 &#62; 온라인몰 1:1 상담요청 &#62; 회원전용상담(비회원일경우 비회원주문관련상담) &#62; 상담유형을 반품/교환</strong>으로 신청해 주시거나 롯데마트몰 <i class="txt-wbrown">고객센터(1577-2500)</i>로 전화주시기바랍니다.</li>
												<li>고객님의 단순변심에 의한 교환 및 반품의 경우 상품반송에 드는 비용은 고객님께서 부담하셔야 합니다.</li>
											</ul>
										</td>
									</tr>
									<tr>
										<th scope="row">불가능한 경우</th>
										<td>
											<ul class="bul-circle">
												<li>신선선물세트 발송 후 고객님의 <i class="txt-wbrown">단순변심</i>으로 환불 요청하시는 경우</li>
												<li>고객님의 단순변심으로 인한 반품/교환 요청이 상품 등을 받으신 날로부터 <i class="txt-wbrown">7일을 경과한 경우</i></li>
												<li>고객님의 책임있는 사유로 상품 등이 멸실 또는 훼손된 경우</li>
												<li>고객님의 사용 또는 일부 소비에 의하여 상품 등의 가치가 현저히 감소한 경우(예:가공식품, 신선식품)</li>
												<li>시간이 경과되어 재판매가 곤란할 정도로 상품 등의 가치가 상실된 경우(예:가공식품 등)</li>
												<li>배송된 상품이 하자 없음을 확인 후 설치가 완료된 경우(예:가전제품, 가구, 헬스기기 등)</li>
												<li>복제가 가능한 상품 등의 포장을 훼손한 경우(예:도서, DVD, CD 등 복제 가능한 상품)</li>
												<li>포장 훼손으로 인하여 상품의 가치가 상실된 경우(예:식품, 생활용품, 가전제품 및 라벨이나 상품 태그가 손상된 일반상품 등)<br/>
												(단, LCD액정이 부착된 제품의 불량화소에 따른 반품/교환은 각 제조사의 기준에 따릅니다.)</li>
												<li>기타 전자상거래 등에서의 소비자보호에 관련 법률이 정하는 소비자 청약철회 제한에 해당되는 경우 고객변심에 의한 교환, 반품인 경우 상품 반송에 드는 비용은 고객님께서 부담하셔야 합니다.</li>
											</ul>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<h3>품절시 환불 안내</h3>
						<div class="wrap-bul-circle-border">
							<ul class="bul-circle">
								<li>고객님의 주문시점과 매장에서 상품을 준비하는 시간의 차이로 주문하신 상품이 매장 내에 없는 결품 상황이 생길 수 있습니다.</li>
								<li>받으신 상품의 내용이 표시 광고 사항과 다른 경우에는 상품들을 받으신 날로부터 3개월 이내 또는 그 사실을 안 날로 또는 알 수 있었던 날로부터 30일 이내</li>
								<li>전자상거래 등에서의 소비자보호에 관한 법률에 규정되어 있는 소비자 청약철회 가능범위에 해당되는 경우</li>
							</ul>
						</div>
					</section>
					<!-- //배송/교환/반품 -->
				</section>
			</div></div>
		</article>
<!-- 최대혜택가 안내 data-attach 값은 버튼의 data-target 속성 값과 같은 값. -->
<script type="text/template" data-attach="tooltip-tbl-help">
	<section class="layerpop-toast-top layerpop-target layer-bottom">
		<div class="container">
			<div class="contents">
				<span class="txt-wbrown">최대혜택가란</span> 고객님께서<br>
				해당 상품에 대해 받을 수 있는 혜택을<br>
				모두 적용한 가격입니다.<br>
				구매 예정 금액과 상이할 수 있습니다.
			</div>
		</div>
	</section>
</script>
<!-- 무이자할부안내 / data-attach 값은 버튼의 data-target 속성 값과 같은 값. -->
<script type="text/template" data-attach="layerpop-interest-free">
	<section class="layerpop-type3 layerpop-target layer-bottom"><!-- layerpop-target / 버튼 아래에 위치하는 내용. layer-bottom-->
		<div class="container">
			<h1 class="tit-h">무이자할부안내</h1>
			<div class="contents">
				<div class="txtimginfo-w first">
					<ul>
						<li class="imginfo w100"><img src="<c:out value='${frontUrl}'/>/v3/images/temp/temp_card3.jpg" alt="" /></li>
						<li  class="txtinfo">
							<ul class="bul-circle">
								<li>5만원 이상 최대 3개월 무이자 할부</li>
								<li>10만원 이상 최대 5개월 무이자 할부</li>
								<li>20만원 이상 최대 12개월 무이자 할부</li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="txtimginfo-w">
					<ul>
						<li class="imginfo w100"><img src="<c:out value='${frontUrl}'/>/v3/images/temp/temp_card4.jpg" alt="" /></li>
						<li  class="txtinfo">
							<ul class="bul-circle">
								<li>5만원 이상 최대 3개월 무이자 할부</li>
								<li>10만원 이상 최대 5개월 무이자 할부</li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="txtimginfo-w">
					<ul>
						<li class="imginfo w100"><img src="<c:out value='${frontUrl}'/>/v3/images/temp/temp_card5.jpg" alt="" /></li>
						<li  class="txtinfo">
							<ul class="bul-circle">
								<li>5만원 이상 최대 3개월 무이자 할부</li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="txtimginfo-w">
					<ul>
						<li class="imginfo w100"><img src="<c:out value='${frontUrl}'/>/v3/images/temp/temp_card6.jpg" alt="" /></li>
						<li  class="txtinfo">
							<ul class="bul-circle">
								<li>5만원 이상 최대 3개월 무이자 할부</li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="txtimginfo-w">
					<ul>
						<li class="imginfo w100"><img src="<c:out value='${frontUrl}'/>/v3/images/temp/temp_card7.jpg" alt="" /></li>
						<li  class="txtinfo">
							<ul class="bul-circle">
								<li>5만원 이상 최대 3개월 무이자 할부</li>
								<li>10만원 이상 최대 5개월 무이자 할부</li>
								<li>20만원 이상 최대 12개월 무이자 할부</li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<a href="javascript:;" class="btn-ico-close"><i>팝업 닫기</i></a>
	</section>
</script>
<!-- 포인트 결제 안내 / data-attach 값은 버튼의 data-target 속성 값과 같은 값. -->
<script type="text/template" data-attach="layerpop-charge-point">
	<section class="layerpop-type3 layerpop-target layer-bottom"><!-- layerpop-target / 버튼 아래에 위치하는 내용. layer-bottom -->
		<div class="container">
			<h1 class="tit-h">포인트 결제 안내</h1>
			<div class="contents">
				<div class="txtimginfo-w first">
					<ul>
						<li class="imginfo w100"><img src="<c:out value='${frontUrl}'/>/v3/images/temp/temp_card3.jpg" alt="" /></li>
						<li  class="txtinfo">
							<ul class="bul-circle">
								<li>10포인트 이상 보유 시 사용</li>
								<li>회원등급에 따라 0.1%~1% 적립</li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="txtimginfo-w">
					<ul>
						<li class="imginfo w100"><img src="<c:out value='${frontUrl}'/>/v3/images/temp/temp_card4.jpg" alt="" /></li>
						<li  class="txtinfo">
							<ul class="bul-circle">
								<li>10포인트 이상 보유 시 사용<br />(구매금액의 최대 20% TOP포인트 사용 가능)</li>
								<li>구매금액의 0.1% TOP포인트로 적립</li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="txtimginfo-w">
					<ul>
						<li class="imginfo w100"><img src="<c:out value='${frontUrl}'/>/v3/images/temp/temp_card5.jpg" alt="" /></li>
						<li  class="txtinfo">
							<ul class="bul-circle">
								<li>1포인트 이상 보유 시 사용</li>
								<li>결제금액의 5%, 1회 최대 5,000포인트 사용</li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="txtimginfo-w">
					<ul>
						<li class="imginfo w100"><img src="<c:out value='${frontUrl}'/>/v3/images/temp/temp_card6.jpg" alt="" /></li>
						<li  class="txtinfo">
							<ul class="bul-circle">
								<li>1포인트 이상 보유 시 사용</li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="txtimginfo-w">
					<ul>
						<li class="imginfo w100"><img src="<c:out value='${frontUrl}'/>/v3/images/temp/temp_card7.jpg" alt="" /></li>
						<li  class="txtinfo">
							<ul class="bul-circle">
								<li>1포인트 이상 보유 시 사용</li>
								<li>결제금액의 0.2% KB카드 포인트리로 재적립<br />(단, KB 비씨카드 제외)</li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<a href="javascript:;" class="btn-ico-close"><i>팝업 닫기</i></a>
	</section>
</script>
<!-- 롯데마트 제휴카드 10% 청구할인 안내 / data-attach 값은 버튼의 data-target 속성 값과 같은 값. -->
<script type="text/template" data-attach="layerpop-partnership-iscount">
	<section class="layerpop-type3 layerpop-target layer-bottom"><!-- layerpop-target / 버튼 아래에 위치하는 내용. layer-bottom -->
		<div class="container">
			<h1 class="tit-h">롯데마트 제휴카드 10% 청구할인 안내</h1>
			<div class="contents">
				<div class="txtimginfo first">
					<ul>
						<li class="imginfo"><img src="<c:out value='${frontUrl}'/>/v3/images/temp/temp_card1.jpg" alt="" /></li>
						<li  class="txtinfo">
							<span class="tit">롯데마트 롯데카드</span>
							<ul class="bul-circle">r
								<li>평일에는 온라인, 주말에는 매장에서 <em class="txt-wbrown">10% 청구할인</em></li>
								<li>롯데 계열사 <em class="txt-wbrown">특별할인</em></li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="txtimginfo">
					<ul>
						<li class="imginfo"><img src="<c:out value='${frontUrl}'/>/v3/images/temp/temp_card2.jpg" alt="" /></li>
						<li  class="txtinfo">
							<span class="tit">롯데마트 KB국민카드(신용/체크)</span>
							<ul class="bul-circle">
								<li>평일에는 온라인, 주말에는 매장에서 <em class="txt-wbrown">10% 청구할인</em></li>
								<li>커피, 영화, 교통, 놀이공원 등 <em class="txt-wbrown">추가할인</em></li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="set-btn">
					<button type="button" class="btn-form">자세히 보기</button>
				</div>
			</div>
		</div>
		<a href="javascript:;" class="btn-ico-close"><i>팝업 닫기</i></a>
	</section>
</script>
<!-- 회원등급별 혜택 안내 / data-attach 값은 버튼의 data-target 속성 값과 같은 값. -->
<script type="text/template" data-attach="layerpop-benefits-class">
	<section class="layerpop-type5 layerpop-target layer-bottom"><!-- layerpop-target / 버튼 아래에 위치하는 내용. layer-bottom -->
		<div class="container">
			<h1 class="tit-h">회원등급별 혜택 안내</h1>
			<div class="contents">
				<div class="tbl-lst-grade">
					<table>
						<caption></caption>
						<colgroup>
							<col style="width:30%"/>
							<col style="width:auto"/>
							<col style="width:auto"/>
							<col style="width:auto"/>
							<col style="width:auto"/>
						</colgroup>
						<tbody>
						<tr>
							<th scope="row">회원등급</th>
							<td><img src="<c:out value='${frontUrl}'/>/v3/images/layout/img_grade.jpg" alt="VIP"></td>
							<td><img src="<c:out value='${frontUrl}'/>/v3/images/layout/img_grade2.jpg" alt="최우수"></td>
							<td><img src="<c:out value='${frontUrl}'/>/v3/images/layout/img_grade3.jpg" alt="우수"></td>
							<td><img src="<c:out value='${frontUrl}'/>/v3/images/layout/img_grade4.jpg" alt="일반"></td>
						  </tr>
						<tr>
							<th scope="row">L.POINT 적립율</th>
							<td><em class="txt-wbrown">0.5%</em></td>
							<td colspan="3"><em class="txt-wbrown">0.1%</em></td>
						  </tr>
						</tbody>
					</table>
					<p class="tit">등급 산정 기준(2016년 1월 1일부 변경 시행)</p>
					<ul class="bul-circle">
						<li>산정 기간 : 직전 2개월 기준 매 홀수 월 변경(단, 직전월 말일 제외)</li>
						<li>산정 기준 : 각 등급별 구매 금액/횟수에 따라 자동화, 롯데마트 매장/온라인 구매 실적 합산</li>
						<li>자세한 내용은 <em class="txt-wbrown">등급 혜택 안내페이지</em> 참조</li>
					</ul>
				</div>
			</div>
		</div>
		<a href="javascript:;" class="btn-ico-close"><i>팝업 닫기</i></a>
	</section>
</script>
<!-- 장바구니 / data-attach 값은 버튼의 data-target 속성 값과 같은 값. -->
<script type="text/template" data-attach="layerpop-basket">
	<section class="layerpop-toast-btm layerpop-target"><!-- layerpop-target -->
		<div class="container">
			<div class="contents">
				<strong>장바구니에 상품이 담겼습니다.</strong><br>지금 확인하시겠습니까?
				<div class="set-btn">
					<button type="button" class="btn-form-type1">확인</button>
					<button type="button" class="btn-form-type2">취소</button>
				</div>
			</div>
		</div>
	</section>
</script>
<!-- 바로구매 / data-attach 값은 버튼의 data-target 속성 값과 같은 값. -->
<script type="text/template" data-attach="layerpop-purchase">
	<section class="layerpop-toast-btm layerpop-target"><!-- layerpop-target -->
		<div class="container">
			<div class="contents">
				<strong>사은품의 재고가<br>부족하여 지급되지 않을 수 있습니다.</strong><br>장바구니에 담으시겠습니까?
				<div class="set-btn">
					<button type="button" class="btn-form-type1">확인</button>
					<button type="button" class="btn-form-type2">취소</button>
				</div>
			</div>
		</div>
	</section>
</script>
<!-- 찜하기 / data-attach 값은 버튼의 data-target 속성 값과 같은 값. -->
<script type="text/template" data-attach="layerpop-wish-list">
	<section class="layerpop-toast-btm-type1 layerpop-target"><!-- layerpop-target -->
		<div class="container">
			<div class="contents">
				<span class="selected">찜하였습니다.</span>
				<!-- <span class="selected-cancel">찜하기가 취소되었습니다.</span> -->
			</div>
		</div>
	</section>
</script>

<!-- 유투브 동영상 -->
<script type="text/template" data-attach="layerpop-youtube">
<section class="layerpop-youtubetype1 layerpop-target"><!-- layerpop-target -->
	<div class="container">
		<div class="contents">
			<iframe width="716" height="402" frameborder="0" src="http://www.youtube.com/embed/wTFBjioVgg4?wmode=transparent" title=""></iframe>
		</div>
		<a href="javascript:;" class="btn-ico-close"><i>팝업 닫기</i></a>
	</div>
</section>
</script>

</div>
</article>
</body>
</html>
