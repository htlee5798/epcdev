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

<script language="JavaScript">
function ckResult(){
	
	<c:if test="${not empty emsResult }">
		alert("신문고 등록이 완료되었습니다.");
		window.close();
	</c:if>
}

function doSubmit(){
	var form = document.forms[0];

	if(form.reg_name.value==''){
		alert("이름을 입력하세요.");
		form.reg_name.focus();
		return;
	}
	if(form.reg_email.value==''){
		alert("E-mail을 입력하세요.");
		form.reg_email.focus();
		return;
	}
	if(form.title.value==''){
		alert("제목을 입력하세요.");
		form.title.focus();
		return;
	}
	if(form.coment.value==''){
		alert("내용을 입력하세요.");
		form.coment.focus();
		return;
	}
	
	form.action  = "<c:url value='/edi/consult/PEDPCST001101Insert.do'/>";
	form.submit();
}


</script>

</head>




<body onload="ckResult()">

			
<form id="form1" name="form1" method="post" action="#">

<input type="hidden" name="v_name" value="이동빈"/>
<input type="hidden" name="v_email" value="kachi79@lottemart.com"/>

<div id="popup">
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
				<a href="javascript:doSubmit();"  class="btn"><span>저장</span></a>
				<a href="javascript:window.close();"  class="btn"><span><spring:message code="button.common.close"/></span></a>
			</li>
		</ul>
	</div>
	
	<div class="wrap_con">
		<div class="bbs_list">
			<table width=750px class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				
<!-- (상세내역부) -->
	<tr>
		<td width=750px>
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td bgcolor=#ffffff >
					<img src="/images/epc/edi/ems/cyber_st_4.gif"  />
				</td>
			</tr>
			<tr>
				<td bgcolor=#ffffff >
					 &nbsp;&nbsp;사이버 신고센터를 찾아주셔서 감사합니다. 사이버 신고센터는 롯데마트 직원은 물론 협력회사 임직원 및 동료사원과 같이 롯데마트와 관계된 모든 분들이 이용하실 수 있는 공간입니다. 
					접수된 내용은 롯데마트 경영개선팀(구. 감사실)으로 직접 연결되어 신속하고 공정하게 처리되며 특히, 신고자의 비밀은 철저히 보장하여 신고자에게 불이익이 없도록 최선을 다할 것입니다.
					단, 신고내용이 부정확하거나 허위인 경우에는 처리되지 않을 수도 있으니 사실에 입각하여 구체적으로 작성바랍니다.
				</td>
			</tr>
			</table>
			<br>
			
			<table width=750px cellpadding=1 cellspacing=1 border=0 bgcolor="#CECCCD">
			<tr>
				<td height=25 width=100 bgcolor=#FFFFCC align=center>신청일
				</td>
				<td bgcolor=#ffffff >
					&nbsp;<input style="width:400px;" type=bgcolortext name=regDate value="${paramMap.startDate }" readonly style="width:380px;">
				</td>
			</tr>
			<tr>
				<td height=25 width=100 bgcolor=#FFFFCC align=center>이름</td>
				<td bgcolor=#ffffff>
					&nbsp;<input style="width:400px;" type=text name=reg_name value="${buyList.VEN_NM }" style="width:120px">
				</td>
			</tr>
			<tr>
				<td height=25 width=100 bgcolor=#FFFFCC align=center>E-mail</td>
				<td bgcolor=#ffffff>
					&nbsp;<input style="width:400px;" type=text name="reg_email"  style="backgroundcolor:#ffffff;width:380px;">
				</td>
			</tr>
			<tr>
				<td height=25 width=100 bgcolor=#FFFFCC align=center>제목</td>
				<td bgcolor=#ffffff>
					
					&nbsp;<input style="width:400px;" type=text name=title  style="backgroundcolor:#ffffff;width:380px;">
				</td>
			</tr>
			<tr>
				<td height=200 width=100 bgcolor=#FFFFCC align=center>내용</td>
				<td bgcolor=#ffffff>
					&nbsp;<textarea   style="width:400px;height:100%;"  name="coment" onkeyup="javascript:cal_byte(this, '4000', '내용');" ></textarea>
			</tr>
				
			</table>
		</td>
	</tr>
	
	<tr>
		<td colspan=2 width=100% bgcolor=#ffffff align=center>
		</td>
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
