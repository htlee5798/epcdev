<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>


</head>




<body>

			
<form id="form1" name="form1" method="post" action="#">


<div id="popup">
    <!------------------------------------------------------------------ -->
    <!--    title -->
    <!------------------------------------------------------------------ -->
    <div id="p_title1">
        <h1>신문고</h1>
        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
    </div>
    <br>
	<div class="popup_contents">

	<div class="bbs_search">
		<ul class="tit">
			<li class="tit">사이버 감사 신고센터</li>
			<li class="btn">
				<a href="javascript:window.close();"  class="btn"><span><spring:message code="button.common.close"/></span></a>
			</li>
		</ul>
	</div>
	
	<div class="wrap_con">
		<div class="bbs_list">
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td bgcolor=#ffffff >
					<img src="/images/epc/edi/ems/cyber_st_1.gif"  />
				</td>
			</tr>
			<tr>
				<td bgcolor=#ffffff >
					 &nbsp;&nbsp;롯데마트 경영개선팀에서는 윤리경영이념에 입각한 상시 감사활동으로 깨끗하고 투명한 조직 풍토를 조성하기 위해 노력하고 있습니다. 저희 경영개선팀에서는 업무의 공정성/투명성 제고, 비합리적인 제도 개선을 통한 효율 증대, 
					 사규정 준수 및 근무자세 확립에 역점을 두고 감사업무를 수행하고 있습니다. 협력회사 여러분께서
					  평소 업무 수행중 경험하신 비윤리적인 사안이나 제반 문제점에 대한 건의사항이 있으시면 주저 마시고 신고하여 주시기 바랍니다. 
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td bgcolor=#ffffff >
					<img src="/images/epc/edi/ems/cyber_st_2.gif"  />
				</td>
			</tr>
			<tr>
				<td bgcolor=#ffffff >
					- 전화 : 02) 2145 - 8031~6 FAX : 02) 2145 - 8592 
				</td>
			</tr>
			<tr>
				<td bgcolor=#ffffff >
					- 우편 : 서울시 송파구 신천동 7-18번지 롯데캐슬플자자 7층 롯데마트 경영개선 1팀
				</td>
			</tr>
			<tr>
				<td bgcolor=#ffffff >
					- E-MAIL : martinspect@lottemart.com
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td bgcolor=#ffffff >
					<img src="/images/epc/edi/ems/cyber_st_3.gif"  />
				</td>
			</tr>
			<tr>
				<td bgcolor=#ffffff > 
					- 업무 수행시 발생한 부조리 및 부당한 대우 사항
				</td>
			</tr>
			<tr>
				<td bgcolor=#ffffff > 
					- 임직원의 금품 및 향응 접대 요구나 제공 사실
				</td>
			</tr>
			<tr>
				<td bgcolor=#ffffff > 
					- 임직원의 직권을 남용한 부당한 행위
				</td>
			</tr>
			<tr>
				<td bgcolor=#ffffff > 
					- 기타 불합리하거나 개선해야할 업무 및 제도
				</td>
			</tr>
			</table>
			<br><br>
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td align="center"><a href="<c:url value="/edi/consult/PEDPCST001101.do"/>"><img src="/images/epc/edi/ems/cyber_assessment_b.gif"  /></a></td>
			</tr>
			</table>
		</div>
	</div>
    </div><!-- class popup_contents// -->
    
    <br/>


</div><!-- id popup// -->
	

</form>	
				
</body>
</html>
