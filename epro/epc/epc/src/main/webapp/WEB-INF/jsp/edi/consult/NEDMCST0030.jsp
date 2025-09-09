<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>

function doForward(){
	var form = document.forms[0];

	form.action ="<c:url value='/edi/consult/NEDMCST0031select.do'/>";
 	form.submit();
}
</script>
 
</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post">
		<input type="hidden" name="sele_bman" value="all"/>
		<div id="wrap_menu">
		
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">알리미 서비스</li>
						<li class="btn">
							<a href="#" class="btn" id="infoReg" onclick="javascript:doForward();"><span>서비스 신청</span></a>
						</li>
					</ul>
				</div>
			</div>
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit"></li>
					</ul>
					
					<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:200px;"  />
						<col  />
					</colgroup>
					<tr>
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_sms_img_1.gif" alt="알리미 서비스란"></h1>
							</div>
						</td>
						<td>
							<font size="2">
							  발주정보 및 기타 거래가 롯데마트와 협력업체 사이에 발생할 경우 요약 정보를 협력업체 담당자에게 
							<br>
							 문자 메시지와 E-mail을 발송해 주는 서비스 입니다.
							</font>
						</td>
					</tr>
					</table>
					
					<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:200px;"  />
						<col  />
					</colgroup>
					<tr>
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_sms_img_2.gif" alt="서비스 내용"></h1>
							</div>
						</td>
						<td>
							<font size="2">
							▶ SMS <br>
							지정 시각에 발주 내역 정보를 서비스 신청자의 핸드폰으로 전송합니다. <br>
									&nbsp;&nbsp;&nbsp;예) "000님 통상발주를 확인하시기 바랍니다. - 롯데마트 VC" <br>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"000님 긴급발주 정보를 확인하시기 바랍니다. - 롯데마트 VC"  <br>
							▶ E-mail <br>
							매일 업체 요약정보와 발주서 정보를 정해진 시각에 서비스 신청자의 메일로 전송삽니다.
							</font>
						</td>
					</tr>
					</table>
					
					<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:200px;"  />
						<col  />
					</colgroup>
					<tr>
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_sms_img_6.gif" alt="서비스 신청/해지/수정"></h1>
							</div>
						</td>
						<td>
							<font size="2">
							  하단의 서비스 신청 버튼을 누르면 해당 서비스에 대한 신청이 이루어 집니다.
							<br>
							 서비스의 해지 또한 하단의 해지 버튼을 이용하시면 됩니다.
							<br>
							핸드폰 번호 또는 E-mail 주소를 변경하고자 할 경우에는 정보수정 버튼을 이용하면 정보수정이 
							<br>가능합니다.
							</font>
						</td>
					</tr>
					</table>
					
					<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:200px;"  />
						<col  />
					</colgroup>
					<tr>
						<td align="center">
							<div class="con_title">
								<h1><img src="/images/epc/edi/consult/comm_sms_img_3.gif" alt="서비스 이용료"></h1>
							</div>
						</td>
						<td>
						<font size="2">
						<b>본 서비스는 현재 무료로 제공됩니다.</b>
						</font>
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
					<li class="last">알리미 서비스</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

</body>
</html>
