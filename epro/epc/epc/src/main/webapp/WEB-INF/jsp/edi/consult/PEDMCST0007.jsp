<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
</script>
 
</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post">
		<div id="wrap_menu">
		
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">Loan</li>
					</ul>
				</div>
			</div>
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit"></li>
					</ul>
					
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<tr class="r1">
						<td>
							&nbsp;&nbsp;&nbsp;&nbsp;<font color="red"><b>[협력업체 자금지원제도의 목적]</b></font><br>
							&nbsp;&nbsp;롯데마트는 협력업체와 상호 신뢰를 바탕으로 지속적인 전략적 동반관계로 성장하기 위하여,<br>
							&nbsp;&nbsp;중소기업은행과 업무 제휴를 통하여 협력업체에게 원활한 자금지원을 제공하고자 합니다.<br><br>
							&nbsp;&nbsp;&nbsp;&nbsp;<div class="con_title">
								<h1><a href='<c:url value="/edi/consult/PEDMCST000701.do"/>'><img src="/images/epc/edi/consult/collaboration_a_2_re.gif" ></a></h1>
							</div><br>
							&nbsp;&nbsp;&nbsp;&nbsp;<div class="con_title">
								<h1><a href='<c:url value="/edi/consult/PEDMCST000702.do"/>'><img src="/images/epc/edi/consult/netloan_fnq_n.gif" ></a></h1>
							</div><br><br>
							&nbsp;&nbsp;<font color="blue"><b>『 롯데마트 자금지원 신청 (은행대출건)』</b></font><br>
							&nbsp;※ 자세한 사항은 상단「협력업체 자금지원제도」를 클릭하여 확인하세요<br>
							&nbsp;ㆍ실적방식 네트워크론 (’05년 3월~) : 마이너스 통장 개설 <br>
							&nbsp;ㆍ플러스 네트워크론 (’06년 8월~) : 발주서 방식<br>
							&nbsp;&nbsp;&nbsp;롯데마트 패밀리론(’07년 9월~) : 납품액 방식 <br>
							&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">→ 플러스 네트워크론 추천신청 시 패밀리론 동시 추천됩니다.</font><br> 
						</td>
					</tr>
					</table>
				</div>
			</div>
					
		</div>
		</form>
	</div>


	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>협업</li>
					<li>협업정보</li>
					<li class="last">Loan</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

</body>
</html>
