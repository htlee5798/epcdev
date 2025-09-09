<%@page pageEncoding="utf-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html lang="ko">
<head>
	<%@ include file="/common/edi/meta.jsp" %>
	<title><decorator:title/> | <fmt:message key="title.supply.consult"/></title>
	<link href='/css/epc/edi/consult/style.css' type="text/css" rel="stylesheet">
	<script language="JavaScript" src='/js/jquery/jquery-1.6.1.js'></script>
	<script type="text/javascript" language="javascript" src='/js/jquery/jquery-1.6.4.min.js'></script>
	<script language="JavaScript" src='/js/epc/edi/consult/etc.js'></script>
	<script language="JavaScript" src='/js/epc/edi/consult/common.js'></script>
	<link href='/css/epc/edi/consult/popup.css' type="text/css" rel="stylesheet">
	<link href='/css/epc/edi/consult/base.css' type="text/css" rel="stylesheet">
	
	
	<script language="JavaScript">
	<!--	
		function check_go()
		{	
			var update_gb = document.MyForm.update_gb.value;
			if(update_gb == "0")
			{
				
				strMsg ="상담신청을 먼저해 주십시요.";         
				popUrl = "pop_message_00.asp?strMsg=" + strMsg;
				openPopup2(popUrl,'ID');   
			
				return;
			}
			var bman_no = document.MyForm.bmanno.value;
			location.href = "consult_modifydata.asp?org_cd=" + org_cd + "&ddd=" + reg_fg + "&bmanno=" + bman_no;
		}
		
		function openinfo(bman_no){
			url = "pop_duty_info.asp?bman_no=" + bman_no;
			window.open(url,"zip","toolbar=no,menubar=no,resizable=no,scrollbars=yes,width=720,height=660");
		}	
	//-->
	</script>
	

	<decorator:head />
</head>
<body>
	<!-- Header -->
		<jsp:include page="/common/edi/consult/header.jsp"/>
	<!-- //Header -->

	<div id="con_wrap">
	<div class="con_title">
				<h1><img src='/images/epc/edi/consult/h1-detail-info-input.gif' alt="상담 상세정보 입력"/></h1>
	</div>
	</div>
	<!-- Body -->
		<!-- c:set var="currentMenu" scope="request" --><!-- decorator:getProperty property="meta.menu" / --><!-- /c:set -->
		<decorator:body />
	<!-- //Body -->

	<!-- Footer -->
		<jsp:include page="/common/edi/consult/footer.jsp"/>
	<!-- //Footer -->
</body>
</html>
