<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMMNT002002.jsp
	Description : 대표자 SRM 모니터링 > 상세팝업 > 비고팝업
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.22		LEE HYOUNG TAK	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@include file="./SRMCommon.jsp" %>

	<title><spring:message code='text.srm.field.remark'/></title><%--spring:message : 비고--%>
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
	<!-- popup wrap -->
	<div id="popup_wrap_full">
		<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

		<div class="tit_btns">
			<h2 class="tit_star"><spring:message code='text.srm.field.remark'/></h2>	<%-- 대분류 조회--%>
		</div>

		<table class="tbl_st1 form_style">
			<colgroup>
				<col width="*" />
			</colgroup>
			<tbody>
			<tr>
				<td>
					<pre style="white-space: pre-wrap;"><c:out value="${data.remark2}"/></pre>
				</td>
			</tr>
			</tbody>
		</table><!-- END 정보 입력폼 -->
		<p class="align-c mt10"><input type="button" id="" name="" value="<spring:message code='button.srm.close'/>" class="btn_normal btn_blue" onclick="func_ok();" /><%--spring:message : 닫기--%></p>
	</div><!-- END popup wrap -->

</body>
</html>