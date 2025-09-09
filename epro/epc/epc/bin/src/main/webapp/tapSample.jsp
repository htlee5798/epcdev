<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>LOTTE MART Back Office System</title>
	<link rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" />
	<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
	<script type="text/javascript" src="/js/epc/common.js"></script>
	<script type="text/javascript" src="/js/wisegrid/WiseGridTag-dev.js" ></script>
	<script type="text/javascript" src="/js/epc/paging.js" ></script>
	<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
	<script language="javascript" type="text/javascript">
		function popupInsertForm() {
			window.open	('./popup_Detail.html','','width=800px,height=350px'); 
		}
		function popupSearch() {
			window.open	('./popup_InsertForm.html','','width=800px,height=350px');
		}
		function popupDetail() {
			window.open	('./popup_SearchPopup.html','','width=800px,height=600px');
		}
	</script>
</head>
<body>

<div id="content_wrap">

<div class="content_scroll">

<div id="wrap_menu">
	<!--	@ 검색조건	-->
	<div class="wrap_search">
		<!-- 01 : search -->
		<div class="bbs_search">
			<ul class="tit">
				<li class="tit">검색조건</li>
				<li class="btn">
					<!-- button -->
					<!-- 비활성화때 -->
					<span class="btn"><span>신규</span></span>
					<!-- 기본형 -->
					<a href="#" class="btn" onclick="popupInsertForm();"><span>신규</span></a>
					<!-- input 사용시 -->
					<span class="btn"><input type="button" value="검색" style="width:42px;"></span>
					<!--// button -->
				</li>
			</ul>
			<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="10%">
				<col width="25%">
				<col width="10%">
				<col width="15%">
				<col width="10%">
				<col width="20%">
			</colgroup>
			<tr>
				<th><span class="star">*</span> 변경일자</th>
				<td>
					<input type="text" class="day" style="width:33%;" /> <a href="#"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
					~
					<input type="text" class="day" style="width:33%;" /> <a href="#"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
				</td>
				<th><span class="star">*</span> 성별</th>
				<td>
					<input type="Radio" Checked />남
					<input type="Radio"/>여
				</td>
				<th>점포코드</th>
				<td>
					<select class="select">
						<option>선택</option>
						<option>선택</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>상품명</th>
				<td class="text">텍스트인 경우 </td>
				<th>성별</th>
				<td >
					<input type="Radio" Checked />남
					<input type="Radio"/>여 <a href="#" class="btn1"><span>삭제</span></a>
				</td>
				<th></th>
				<td></td>
			</tr>
			<tr>
				<th>상품명</th>
				<td class="text">텍스트인 경우</td>
				<th>성별</th>
				<td class="check">
					<input type="Radio" Checked />남
					<input type="Radio" />여
				</td>
				<th></th>
				<td></td>
			</tr>
			</table>
		</div>
		<!-- 1검색조건 // -->
	</div>

	<!-- tab -->
	<div class="tab mt20">
		<ul>
			<li class="on"><a href="#">탭이름1</a></li>
			<li><a href="#">탭이름2</a></li>
			<li><a href="#">탭이름3</a></li>
			<li><a href="#">탭이름4</a></li>
			<li><a href="#">탭이름2</a></li>
			<li><a href="#">탭이름3</a></li>
		</ul>
	</div>
	<!--// tab -->

	<!--	2 검색내역 	-->
	<div class="wrap_con">
		<!-- list -->
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">검색내역</li>
				<li class="btn">
					<a href="#" class="btn" onclick="popupSearch();"><span>상품추가</span></a>
					<a href="#" class="btn"><span>삭제</span></a>
				</li>
			</ul>

			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="5%">
				<col width="3%">
				<col width="13%">
				<col width="13%">
				<col width="13%">
				<col width="13%">
				<col width="13%">
			</colgroup>
			<tr>
				<th rowspan="3" class="fst">순번</th>
				<th rowspan="3"><input type="CheckBox" class="check" /></th>
				<th colspan="3">상품명</th>
				<th>총액</th>
				<th>상품/주문</th>
				<th>OK CashBag 적립</th>
			</tr>
			<tr>
				<th>수량</th>
				<th>단가</th>
				<th>적립금 적립</th>
				<th>신세계 포인트 적립</th>
				<th>프로모션</th>
				<th>프로모션</th>
			</tr>
			<tr>
				<th>상품쿠폰</th>
				<th>배송지</th>
				<th>다중배송</th>
				<th>프로모션</th>
				<th>프로모션</th>
				<th>프로모션</th>
			</tr>
			<tr class="r1">
				<td rowspan="3" class="fst">1</td>
				<td rowspan="3"><input type="CheckBox" class="check" /></td>
				<td colspan="3"><a href="#" onclick="popupDetail();">닭날개</a></td>
				<td>20,000</td>
				<td>NO</td>
				<td>적립</td>
			</tr>
			<tr>
				<td>1</td>
				<td>20,000</td>
				<td>0</td>
				<td>600</td>
				<td>적당히 사세요</td>
				<td>열심히</td>
			</tr>
			<tr>
				<td>-</td>
				<td>인천시 남구 학익동</td>
				<td>NO</td>
				<td>2007-10-12</td>
				<td>30,000</td>
				<td>30,000</td>
			</tr>
			<tr class="r1">
				<td rowspan="3" class="fst">1</td>
				<td rowspan="3"><input type="CheckBox" class="check" /></td>
				<td colspan="3"><a href="#" onclick="popupDetail();">닭날개</a></td>
				<td>20,000</td>
				<td>NO</td>
				<td>적립</td>
			</tr>
			<tr>
				<td>1</td>
				<td>20,000</td>
				<td>0</td>
				<td>600</td>
				<td>적당히 사세요</td>
				<td>열심히</td>
			</tr>
			<tr>
				<td>-</td>
				<td>인천시 남구 학익동</td>
				<td>NO</td>
				<td>2007-10-12</td>
				<td>30,000</td>
				<td>30,000</td>
			</tr>
			<tr class="r1">
				<td rowspan="3" class="fst">1</td>
				<td rowspan="3"><input type="CheckBox" class="check" /></td>
				<td colspan="3"><a href="#" onclick="popupDetail();">닭날개</a></td>
				<td>20,000</td>
				<td>NO</td>
				<td>적립</td>
			</tr>
			<tr>
				<td>1</td>
				<td>20,000</td>
				<td>0</td>
				<td>600</td>
				<td>적당히 사세요</td>
				<td>열심히</td>
			</tr>
			<tr>
				<td>-</td>
				<td>인천시 남구 학익동</td>
				<td>NO</td>
				<td>2007-10-12</td>
				<td>30,000</td>
				<td>30,000</td>
			</tr>
			<tr class="r1">
				<td rowspan="3" class="fst">1</td>
				<td rowspan="3"><input type="CheckBox" class="check" /></td>
				<td colspan="3"><a href="#" onclick="popupDetail();">닭날개</a></td>
				<td>20,000</td>
				<td>NO</td>
				<td>적립</td>
			</tr>
			<tr>
				<td>1</td>
				<td>20,000</td>
				<td>0</td>
				<td>600</td>
				<td>적당히 사세요</td>
				<td>열심히</td>
			</tr>
			<tr>
				<td>-</td>
				<td>인천시 남구 학익동</td>
				<td>NO</td>
				<td>2007-10-12</td>
				<td>30,000</td>
				<td>30,000</td>
			</tr>
			<tr class="r1">
				<td rowspan="3" class="fst">1</td>
				<td rowspan="3"><input type="CheckBox" class="check" /></td>
				<td colspan="3"><a href="#" onclick="popupDetail();">닭날개</a></td>
				<td>20,000</td>
				<td>NO</td>
				<td>적립</td>
			</tr>
			<tr>
				<td>1</td>
				<td>20,000</td>
				<td>0</td>
				<td>600</td>
				<td>적당히 사세요</td>
				<td>열심히</td>
			</tr>
			<tr>
				<td>-</td>
				<td>인천시 남구 학익동</td>
				<td>NO</td>
				<td>2007-10-12</td>
				<td>30,000</td>
				<td>30,000</td>
			</tr>
			<tr class="r1">
				<td rowspan="3" class="fst">1</td>
				<td rowspan="3"><input type="CheckBox" class="check" /></td>
				<td colspan="3"><a href="#" onclick="popupDetail();">닭날개</a></td>
				<td>20,000</td>
				<td>NO</td>
				<td>적립</td>
			</tr>
			<tr>
				<td>1</td>
				<td>20,000</td>
				<td>0</td>
				<td>600</td>
				<td>적당히 사세요</td>
				<td>열심히</td>
			</tr>
			<tr>
				<td>-</td>
				<td>인천시 남구 학익동</td>
				<td>NO</td>
				<td>2007-10-12</td>
				<td>30,000</td>
				<td>30,000</td>
			</tr>
			</table>
		</div>
	</div>
	<!--	2 검색내역 // -->
	<!-- paging -->
	<div class="pagingbox">
		<p class="total">[총건수 <span>10</span>/24235]</p>

		<div class="paging">
			<span class="fst"><a href="#"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_first.gif" alt="첫페이지" class="middle" /></a></span>
			<span class="pre"><a href="#"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_prev.gif" alt="이전페이지" class="middle" /></a></span>

			<span class="bar2"><a href="#">1</a></span>
			<span class="bar"><a href="#" >2</a></span>
			<span class="bar"><a href="#">3</a></span>
			<span class="bar"><a href="#">4</a></span>
			<span class="bar"><a href="#">5</a></span>
			<span class="bar"><a href="#">6</a></span>
			<span class="bar"><a href="#">7</a></span>
			<span class="bar"><a href="#">8</a></span>
			<span class="bar"><a href="#">9</a></span>
			<span class="bar"><a href="#">10</a></span>

			<span class="nxt"><a href="#"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_next.gif" alt="다음페이지" class="middle" /></a></span>
			<span class="end"><a href="#"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_end.gif" alt="끝페이지" class="middle" /></a></span>
		</div>
	</div>
	<!-- paging // -->

</div>

</div>


	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg">메시지가 디스플레이 되는 곳입니다.</div>
			<div class="notice">최근공지게시판 제목만 노출</div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>시스템관리</li>
					<li class="last">관리자관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>



</div>

</body>
</html>