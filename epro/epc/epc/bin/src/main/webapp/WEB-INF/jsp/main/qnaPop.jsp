<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"               %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
		<title>롯데마트 BOS FAQ</title>
		<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
		<link type="text/css" rel="stylesheet" href="/css/epc/edi/scm_popup.css" ></link>
	</head>
	<body>
		<div class="wrap_popup">
			<div class="nav_section">
			<h1><a href="#" class="ico"><span>아이콘</span></a>기능별 FAQ</h1>
				<nav class="nav">
					<span class="column_1 active">상품관리</span>
					<span class="column_2">주문관리</span>
					<span class="column_3">CS관리</span>
					<span class="column_4">정산관리</span>
				</nav>
			</div>
			<div class="cnt_section">
				<div class="column_1 active">
					<h2>- 상품 관리 기능 FAQ</h2>
					<ul>
						<li class="sub_col_1 active">
							<div class="wrap_q">
								<span>Q.</span> 대표상품 코드가 존재하지 않아 상품 등록이 되지 않습니다.
							</div>
							<div class="wrap_a">
								<div class="tit">
									<span>A.</span><strong class="blue">담당MD에게 대표상품코드 생성을 요청</strong>하세요.<br>
									생성완료 時 다음과 같이 생성된 토글박스를 통해 마진 선택이 가능합니다.
								</div>
								<div class="wrap_sub">
									<img src="//simage.lottemart.com/lm2/images/contents/2016/11/sub_1_1.jpg" alt="">
								</div>
							</div>
						</li>
						<li class="sub_col_2">
							<div class="wrap_q">
								<span>Q.</span> 소분류가 없다는 메시지가 뜨며 등록이 되지 않습니다.
							</div>
							<div class="wrap_a">
								<div class="tit">
									<span>A.</span>담당MD를 통해 등록하고자하는 상품 카테고리에 대한<strong class="blue">기본 카테고리 할당을</strong> 요청하세요.
								</div>
							</div>
						</li>
						<li class="sub_col_3">
							<div class="wrap_q">
								<span>Q.</span> 상품등록 時 배송정책 등록시 출고지/반품지 선택이 되지 않습니다.
							</div>
							<div class="wrap_a">
								<div class="tit">
									<span>A.</span>상품 등록시 출고지/반품지 <strong class="blue">선택은 [SCM]시스템관리>업체정보관리>업체주소</strong> 탭에서<br> <strong class="blue">기본출고지/반품지를 추가</strong>하셔야 선택가능 합니다.
								</div>
								<div class="wrap_sub">
									<img src="//simage.lottemart.com/lm2/images/contents/2016/11/sub_1_3.jpg" alt="">
								</div>
							</div>
						</li>
						<li class="sub_col_4">
							<div class="wrap_q">
								<span>Q.</span> 상품 등록 後 전시가 되지 않아 담당MD에게 확인 요청하니 승인요청 상품 조회가 안된다고 합니다.
							</div>
							<div class="wrap_a">
								<div class="tit">
									<span>A.</span>신상품 등록 後 담당MD에게 상품 승인요청으로 넘어가기 위해서는 <strong class="blue">[EDI]상품>신규상품관리>임시보관함</strong> 탭에서 <strong class="blue">확정</strong>을 하셔야 합니다.
								</div>
								<div class="wrap_sub">
									<img src="//simage.lottemart.com/lm2/images/contents/2016/11/sub_1_4.jpg" alt="">
								</div>
							</div>
						</li>
						<li class="sub_col_5">
							<div class="wrap_q">
								<span>Q.</span> 등록된 상품들을 검색하고 싶습니다.
							</div>
							<div class="wrap_a">
								<div class="tit">
									<span>A.</span><strong class="blue">[SCM]상품관리>상품정보관리>인터넷상품관리</strong>탭에서 조회 가능합니다.
								</div>
								<div class="wrap_sub">
									<img src="//simage.lottemart.com/lm2/images/contents/2016/11/sub_1_5.jpg" alt="">
								</div>
							</div>
						</li>
						<li class="sub_col_6">
							<div class="wrap_q">
								<span>Q.</span> 상품 정보(상품명/품절/전시/재고수량)를 일괄변경하고 싶습니다.
							</div>
							<div class="wrap_a">
								<div class="tit">
									<span>A.</span><strong class="blue">[SCM]상품관리>상품정보관리>상품정보관리</strong>에서 변경 가능합니다.<br>상품정보관리 탭에서 조회>변경 원하는 상품 체크>일괄변경 토글박스에서 상품명/판매상태/전시상태/재고수량 中 항목 선택 후 값 입력으로 일괄 변경 가능합니다.
								</div>
								<div class="wrap_sub">
									<img src="//simage.lottemart.com/lm2/images/contents/2016/11/sub_1_6.jpg" alt="">
								</div>
							</div>
						</li>
						<li class="sub_col_7">
							<div class="wrap_q">
								<span>Q.</span> 상품 판매가격을 변경하고 싶습니다.
							</div>
							<div class="wrap_a">
								<div class="tit">
									<span>A.</span><strong class="blue">[SCM]상품관리>온라인전용상품관리>가격변경요청관리</strong>에서 변경요청을 통해 가능합니다.<br> 상품추가>조회>변경 상품체크>추가>변경가격 및 사유 기입>저장>가격변경승인 후 반영
								</div>
								<div class="wrap_sub">
									<img src="//simage.lottemart.com/lm2/images/contents/2016/11/sub_1_7.jpg" alt="">
								</div>
							</div>
						</li>
						<li class="sub_col_8">
							<div class="wrap_q">
								<span>Q.</span> 공지 기능을 사용하고 싶습니다.
							</div>
							<div class="wrap_a">
								<div class="tit">
									<span>A.</span><strong class="blue">[SCM]상품관리>상품정보관리>협력사공지관리</strong> 탭에서 설정 가능합니다.<br>적용대상(상품/카테고리/업체) 선택을 통해 일부혹은 전체 상품에 공지 가능합니다.
								</div>
								<div class="wrap_sub">
									<img src="//simage.lottemart.com/lm2/images/contents/2016/11/sub_1_8.jpg" alt="">
								</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="column_2">
					<h2>- 주문 관리 기능 FAQ</h2>
					<ul>
						<li class="sub_col_1 active">
							<div class="wrap_q">
								<span>Q.</span> 배송비가 무료로 설정되어 있습니다. 배송비 설정을 하고 싶습니다
							</div>
							<div class="wrap_a">
								<div class="tit">
									<span>A.</span><strong class="blue">[SCM]시스템관리>업체정보관리>주문배송비관리 </strong>탭에서 설정 가능합니다.<br>기준최소금액~기준최대금액, 배송비를 설정하여 최대금액미만 구매건에 대해 설정한 배송비가 적용되게 설정 가능합니다.  신상품 등록시 상품별 개별 배송비 설정도 가능합니다. 단, 설정한 배송비는 익일 적용 됩니다.
								</div>
								<div class="wrap_sub">
									<img src="//simage.lottemart.com/lm2/images/contents/2016/11/sub_2_1.jpg" alt="">
								</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="column_3">
					<h2>- CS 관리 기능 FAQ</h2>
					<ul>
						<li class="sub_col_1 active">
							<div class="wrap_q">
								<span>Q.</span> 게시판이 정상적으로 조회가되지 않습니다.
							</div>
							<div class="wrap_a">
								<div class="tit">
									<span>A.</span>현재 시스템은 Chrome에 최적화되어 있습니다. <br>익스플로러 게시판 미작동의 경우는 다음과 같이 설정하시기 바랍니다.
								</div>
								<div class="wrap_sub">
									<ol class="blue">
										<li>1. 익스플로러창 -> 도구 -> 인터넷 옵션 으로 이동</li>
										<li>2. 인터넷옵션의 고급탭을 클릭</li>
										<li>3. 보안문항에<br> <p> SSL 2.0 사용 체크해제<br>SSL 3.0 사용 체크<br>TLS 1.0 사용 체크해제<br>TLS 1.1 사용 체크해제<br>TLS 1.2 사용 체크해제<p></li>
									</ol>
								</div>
							</div>
						</li>
					</ul>
				</div>
				<div class="column_4">
					<h2>- 정산 관리 기능 FAQ</h2>
					<ul>
						<li class="sub_col_1 active">
							<div class="wrap_q">
								<span>Q.</span> 상품을 고객에게 발송했지만 매출 확정이 되지 않습니다.
							</div>
							<div class="wrap_a">
								<div class="tit">
									<span>A.</span><strong class="blue">[SCM]협력업체 배송관리>배송현황></strong> 탭에서 매출확정 가능합니다.<br> 일반상품의 경우 <strong class="blue">택배업체/운송장을 정확하게 기입</strong>하신후 배송상태를 <strong class="blue">배송중으로 전환</strong>하셔야 매출이 확정됩니다. 무형상품/설치상품은 발송일자 혹은 설치일자에 해당 일자 기입 후 배송업체(마트배송)설정, 배송상태 배송중으로 전환 해주시면 확정됩니다.
								</div>
								<div class="wrap_sub">
									<img src="//simage.lottemart.com/lm2/images/contents/2016/11/sub_4_1.jpg" alt="">
								</div>
							</div>
						</li>
						<li class="sub_col_2">
							<div class="wrap_q">
								<span>Q.</span> 매출을 확인하고 싶습니다.
							</div>
							<div class="wrap_a">
								<div class="tit">
									<span>A.</span><strong class="blue">[SCM]정산관리>매출공제현황> </strong>탭에서 확인 가능합니다.<br> 1~10일 → 20일, 11~20일 → 30일, 21~말일 → 익월 10일에 수수료를 제외한 금액이 입금됩니다. 택배비의 경우 월기준 합산하여 3차 시에 입금됩니다.
								</div>
								<div class="wrap_sub">
									<img src="//simage.lottemart.com/lm2/images/contents/2016/11/sub_4_2.jpg" alt="">
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<i class="dim"></i>
	</body>
	<script>
	$(document).ready(function(){
		$('nav.nav span').click(function(){
			var _tabClass = $(this).attr('class').replace(' active','');
			$(this).addClass('active').siblings().removeClass('active');
			$('.cnt_section .' + _tabClass).addClass('active').siblings().removeClass('active');
			$('.sub_col_1').addClass('active').siblings().removeClass('active');
		});
		$('.cnt_section ul .wrap_q').click(function(){
			$(this).parent().addClass('active').siblings().removeClass('active');
		});
		$('.nav_section .ico').click(function(){
			$('html').addClass('popup');
		});
		$('.dim , nav.nav span').click(function(){
			$('html').removeClass('popup');
		});
	});
	</script>
</html>
