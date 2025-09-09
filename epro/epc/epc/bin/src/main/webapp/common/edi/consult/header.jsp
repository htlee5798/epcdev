<%@page pageEncoding="utf-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>

<script language="javascript">

/*
function go_step(page)
{
	var form = document.getElementById("TopForm");

	if(form.gubunHearder.value == "Y"){
		alert("<spring:message code='msg.supply.consult.prosessing'/>");
		
		return;
		
	}else{
		switch(page)
		{
			case "1":
				location.href="<c:url value='/epc/edi/consult/insertStep1.do'/>";
				break;
			case "2":
				location.href="<c:url value='consult_insertdata_2.asp?step=2'/>";
				break;
			case "3":
				location.href="<c:url value='/epc/edi/consult/insertStep2.do'/>";
				break;
			case "4":
				location.href="<c:url value='/epc/edi/consult/insertStep3.do'/>";
				break;
			case "5":
				location.href="<c:url value='/epc/edi/consult/insertStep4.do'/>";
				break;
		}
		
	}
	
}
*/
function go_page(type)
{

	
	


	var form = document.getElementById("TopForm");
	
	
	
	if(form.gubunHearder.value == "C"){
		
		if (type == "1")
		{	
		location.href = '<c:url value="/epc/edi/consult/NEDMCST0310loginHttpsNew.do"/>';
			
		}else if (type == "3" && document.TopForm.businessNo.value != ""){
			location.href = '<c:url value="/epc/edi/consult/NEDMCST0310loginHttpsNewResult.do"/>';
		}		
	}else{

		//상담신청
		if (type == "1")
		{	

			if(form.gubunHearder.value == "Y"){
				location.href = '<c:url value="/epc/edi/consult/NEDMCST0310loginHttpsNew.do"/>';
			}else{
 				//처음 사용자인 경우
 				if (document.TopForm.businessNo.value != "" && document.TopForm.update_gb.value == "0")
 					location.href = '<c:url value="/epc/edi/consult/NEDMCST0310loginHttpsNew.do"/>';
 					
 				//상담정보가 있는데 상담 신청 버튼을 클릭한 경우
 				else if (document.TopForm.businessNo.value != "" && document.TopForm.update_gb.value != "0")
 					location.href = '<c:url value="/epc/edi/consult/NEDMCST0310loginHttpsNew.do"/>';
				
				if (document.TopForm.businessNo.value != "")
					location.href = '<c:url value="/epc/edi/consult/NEDMCST0310loginHttpsNew.do"/>';
				
				//세션이 없는 경우 로그인 페이지로 이동한다. 	
				else
					location.href = '<c:url value="/epc/edi/consult/NEDMCST0310loginHttpsNew.do"/>';
					
			}
			

		//결과확인
		}else if(type =="3"){
			if(form.gubunHearder.value == "Y"){
				location.href = '<c:url value="/epc/edi/consult/NEDMCST0310loginHttpsNewResult.do"/>';
				
			}else{
				//상담정보가 있는 경우			
				if (document.TopForm.businessNo.value != "" )
					location.href = '<c:url value="/epc/edi/consult/NEDMCST0310loginHttpsNewResult.do"/>';

				//상담 정보가 없는 경우
				else if (document.TopForm.businessNo.value != "" && document.TopForm.update_gb.value == "0")
					alert("<spring:message code='msg.consult.apply'/>");

				//세션이 없는 경우
				else
				
				location.href = '<c:url value="/epc/edi/consult/NEDMCST0310loginHttpsNewResult.do"/>';

			
					
				
			}

		//상담정보 변경
		}else {  
			/*
			if(form.gubunHearder.value == "Y"){
				alert("<spring:message code='msg.supply.consult.prosessing'/>");
				location.href = '<c:url value="/epc/edi/consult/insertStep4.do"/>';
			}else{
				//상담정보가 있는 경우			
				if (document.TopForm.businessNo.value != "" && document.TopForm.update_gb.value != "0")
					location.href = '<c:url value="/epc/edi/consult/insertStep1.do"/>';
	
				//상담 정보가 없는 경우
				else if (document.TopForm.businessNo.value != "" && document.TopForm.update_gb.value == "0")
					alert("<spring:message code='msg.consult.apply'/>");
	
				//세션이 없는 경우
				else
					alert("<spring:message code='msg.login.necessary'/>");
				
			}	
			*/	
		}

	}
		
}

// function doLogout()
// {
// 	var form = document.form;
// 	var url = '<c:url value="/common/epcLogoutConsult.do"/>';
// 	form.action=url;
// 	form.submit();
// }




</script>

	
	<div id="con_wrap">
	
		<div id="head">
			<h1><img src='/images/epc/edi/consult/h1-logo-lottemart.gif' alt="행복드림-롯데마트"></h1>
		<!-- 	<a href="#"><img src="/images/epc/layout/header_btn_logout.gif" onClick="doLogout();" alt="로그아웃" /></a>
	 -->
	
			<div class="gnb">
				<ul>
					<li><div style="width: 210px;" class="first"><p class="tit_li"><a href="<c:url value='/epc/edi/consult/NEDMCST0311.do'/>"><img src="/images/epc/edi/consult/gnb-menu-01.gif" alt="상담절차"></a></p></div></li>
					 
					 <li><div style="width: 210px;"><p class="tit_li"><A href="javascript:go_page('1');" target="_self"><img src="/images/epc/edi/consult/gnb-menu-02.gif" alt="상담신청"></a></p></div></li>
				<%-- 	
					<li><div><p class="tit_li"><A href="${ctx }/epc/edi/consult/NEDMCST0310loginHttpsNew.do" target="_self"><img src="/images/epc/edi/consult/gnb-menu-02.gif" alt="상담신청..."></a></p></div></li>
					 --%>
					
				<!-- 	<li><div><p class="tit_li"><A href="javascript:go_page('2');"  target="_self"><img src="/images/epc/edi/consult/gnb-menu-03.gif" alt="상담정보변경"></a></p></div></li>
				 -->
				  	<li><div style="width: 210px;"><p class="tit_li"><A href="javascript:go_page('3');" ><img src="/images/epc/edi/consult/gnb-menu-04.gif" alt="결과확인"></a></p></div></li>
				
				 	<li><div style="width: 210px;"><p class="tit_li"><A href="<c:url value='/epc/edi/consult/NEDMCST0313.do'/>" target="_self"><img src="/images/epc/edi/consult/gnb-menu-05.gif" alt="FAQ"></a></p></div></li>
				</ul>
			</div>
		</div>
	
	</div>
	
	
<form id="TopForm" name="TopForm" method="post" action="<c:url value='/epc/edi/consult/checkVendorBusinessNo3.do'/>">	
<!--
${gubunBlock}
${vendorSession.vendor.bmanNo}
${vendorSession.vendor.passwd}
	 --->
	
	 
	<input type="hidden" name="businessNo" value="${vendorSession.vendor.bmanNo }">
	<input type="hidden" name="password" value="${vendorSession.vendor.passwd }">
	<input type="hidden" name="update_gb" value="${vendorSession.vendor.chgStatusCd}">
	<input type="hidden" name="gubunHearder" value="${gubunBlock}">

	
</form>		