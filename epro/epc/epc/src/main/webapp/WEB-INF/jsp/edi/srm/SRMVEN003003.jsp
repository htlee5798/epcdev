<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMMNT002003.jsp
	Description : 대표자 SRM 모니터링 > 상세팝업 > 특이사항팝업
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.22		LEE HYOUNG TAK	최초생성
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@include file="./SRMCommon.jsp" %>

	<title><spring:message code="text.srm.field.impReqRemark"/></title>
	<link href='/css/epc/edi/srm/srm.css' type="text/css" rel="stylesheet">

	<script language="JavaScript">
		/* 화면 초기화 */
		$(document).ready(function() {
		});


		//닫기
		function func_ok() {
			window.close();
		}
	</script>

</head>


<body>

<form name="MyForm"  id="MyForm" method="post">
	<div class="con_area" style="width:400px;">
		<div id="p_title1">
			<span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
		</div>
		<p style="margin-top: 10px;" />
		<div class="div_tit_dot">
			<div class="div_tit">
				<tt><spring:message code="text.srm.field.impReqRemark"/></tt>
			</div>
		</div>

		<div class="tbl_default">
			<table>
				<colgroup>
					<col width="*" />
				</colgroup>
				<tbody>
				<tr>
					<td>
						<pre style="white-space: pre-wrap;"><c:out value="${data.remark1}"/></pre>
					</td>
				</tr>
				</tbody>
			</table>
		</div>

		<div class="div_btn">
			<input type="button" id="" name="" value="<spring:message code='button.srm.close'/>" class="btn_normal btn_blue" onclick="func_ok();" /><%--spring:message : 닫기--%>
		</div>
	</div>

</form>

</body>
</html>