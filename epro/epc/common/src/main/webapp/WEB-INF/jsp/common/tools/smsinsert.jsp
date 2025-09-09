<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
 /**
  * @Class Name : sms.jsp
  * @Description : sms insert test
  * @Modification Information
  * @
  * @  수정일         수정자                   수정내용
  * @ -------    --------    ---------------------------
  * @ 2011.09.05    고성훈          최초 생성
  *
  *  @author 공통서비스 AA 고성훈
  *  @since 2011.09.05
  *  @version 1.0
  *  @see
  *  
  */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:set var="registerFlag" value="${empty LtsmsVO.SERIALNO ? '등록' : '수정'}"/>
<title>Sample <c:out value="${registerFlag}"/> </title>

<!--For Commons Validator Client Side-->
<script type="text/javascript" src="<c:url value='/validator.do'/>"></script>
<validator:javascript formName="ltsmsVO" staticJavascript="false" xhtml="true" cdata="false"/>
<!--For Commons Validator Server-Side Validation -->
<script type="text/javascript">
<!-- 
/* SMS 등록 function */
function fn_save() {	
	frm = document.detailForm;
	if(!validateLtsmsVO(frm)){
        return;
    }else{
    	frm.action = "<c:url value="${registerFlag == '등록' ? '/sample/sms/insert.do' : '/sample/sms/update.do'}"/>";
        frm.submit();
    }
}
	// Server-Side Validation
function fn_save_ServerSide() {	
	frm = document.detailForm;
    	frm.action = "<c:url value="${registerFlag == '등록' ? '/sample/sms/insert.do' : '/sample/sms/update.do'}"/>";
        frm.submit();
}
-->
</script>
 
</head>
<body style="text-align:center; margin:0 auto; display:inline; padding-top:100px;">

<form:form commandName="ltsmsVO" name="detailForm">
<div>
	<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#D3E2EC" bordercolordark="#FFFFFF" style="BORDER-TOP:#C2D0DB 2px solid; BORDER-LEFT:#ffffff 1px solid; BORDER-RIGHT:#ffffff 1px solid; BORDER-BOTTOM:#C2D0DB 1px solid; border-collapse: collapse;">
		<colgroup>
			<col width="150">
			<col width="">
		</colgroup>
		<c:if test="${registerFlag == '수정'}">
		<tr>
			<td class="tbtd_caption">발송 ID</td>
			<td class="tbtd_content">
				<form:input path="SERIALNO" cssClass="essentiality" maxlength="15" readonly="true" />
			</td>			
		</tr>
		</c:if>
		<tr>
			<td>발송자 명</td>
			<td>
				<form:input path="USER_NM" maxlength="30"/>
				&nbsp;<form:errors path="USER_NM" />
			</td>
		</tr>
		<tr>
			<td>내용</td>
			<td>
				<form:textarea path="DATA" rows="5" cols="58" />
			</td>
		</tr>
		<tr>
			<td>받는사람 번호</td>
			<td>
				<form:input path="DESTCALLNO" maxlength="10"/>
			</td>
		</tr>
		<tr>
			<td>보내는사람 번호</td>
			<td>
				<form:input path="CALLBACKNO" maxlength="10"/>
				
			</td>
		</tr>
	</table>
	<div>
		<ul>
			<li><a href="javascript:fn_save_ServerSide();"><c:out value='${registerFlag}'/></a></li>
			<c:if test="${registerFlag == '수정'}">
			<li><a href="javascript:fn_delete();">삭제</a></li>
			</c:if>
			<li><a href="javascript:document.detailForm.reset();">Reset</a></li></ul>
	</div>
</div>
</form:form>
<% if (request.getAttribute("message") != null) { %>
<script>
	alert('<%= request.getAttribute("message") %>');
</script>
<% } %>
</body>
</html>