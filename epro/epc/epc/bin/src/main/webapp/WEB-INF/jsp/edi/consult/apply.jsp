<%@ page pageEncoding="UTF-8"%>
    <%@ include file="/common/edi/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="./css/base.css" type="text/css">
<title>롯데마트 입점상담</title>


<script language="JavaScript">


$(document).ready(function($){
//	popup_window1();
//	popup_window3();
});


// function popup_window1()
// {   
	

// 	var WindowWidth1 = 1000;
// 	var WindowHeight1 = 600;
// 	var WindowLeft1 = (screen.width - WindowWidth1)/2;
// 	var WindowTop1= (screen.height - WindowHeight1)/2;

	
// 	//var openUrl = "<c:url value='/epc/edi/consult/selectDepartmentDetailPopup.do?teamCd="+teamCd+"&orgCd="+orgCd+"'/>";
// 	var openUrl1 = "<c:url value='/epc/edi/consult/selectAllL1cdDetailPopup.do'/>";
// 	var popTitle1 = "_newCodes1";
// 	NewWin1 = window.open(openUrl1, popTitle1, "titlebar=no, resizable=1, width="+WindowWidth1+", height="+WindowHeight1+", top="+WindowTop1+", left="+WindowLeft1);
// 	NewWin1.focus();
	
// }

// function popup_window3()
// {   
	

// 	var WindowWidth3 = 400;
// 	var WindowHeight3 = 565;
// 	var WindowLeft3 = (screen.width - WindowWidth3)/2;
// 	var WindowTop3= (screen.height - WindowHeight3)/2;

	
// 	//var openUrl = "<c:url value='/epc/edi/consult/selectDepartmentDetailPopup.do?teamCd="+teamCd+"&orgCd="+orgCd+"'/>";
// 	var openUrl3 = "<c:url value='/epc/edi/consult/selectImgPopup.do'/>";
// 	var popTitle3 = "_newCodes3";
// 	NewWin3 = window.open(openUrl3, popTitle3, "titlebar=no, resizable=1, width="+WindowWidth3+", height="+WindowHeight3+", top="+WindowTop3+", left="+WindowLeft3);
// 	NewWin3.focus();
	
// }


function confirm_submit_new(teamCd)
{	
	
	/*
	var update_gb = document.MyForm.update_gb.value;
	
	if(update_gb != "0")
	{
		
		strMsg ="상담신청을 먼저해 주십시요.";         
		popUrl = "pop_message_00.asp?strMsg=" + strMsg;
		openPopup2(popUrl,'ID')   
	
		return;
	}
	
	var bman_no = document.MyForm.bmanno.value;
	var reg_fg = document.MyForm.reg_fg.value;
	*/
//	alert($("input[name=l1Cd]").val(l1Cd));
//	alert("2");
	
	//$("input[name=orgCd]").val(departmentCode);	
//	alert("3");
//	alert(teamCd);
	$("input[name=teamCd]").val(teamCd);	
//	alert("4");
	$("form[name=MyForm]").submit();

}


function check_go()
{	
	var update_gb = document.MyForm.update_gb.value;
	
	if(update_gb == "0")
	{
		
		strMsg ="상담신청을 먼저해 주십시요.";         
		popUrl = "pop_message_00.asp?strMsg=" + strMsg;
		openPopup2(popUrl,'ID')   
	
		return;
	}
	
	var bman_no = document.MyForm.bmanno.value;
	var reg_fg = document.MyForm.reg_fg.value;
	location.href = "consult_insertdata_2.asp";

}

function openPopup2(openUrl, popTitle)
{	
	var WindowWidth = 280;
	var WindowHeight = 166;
	var WindowLeft = (screen.width - WindowWidth)/2;
	var WindowTop= (screen.height - WindowHeight)/2;

	NewWin = window.open(openUrl, popTitle, "titlebar=no, resizable=1, width="+WindowWidth+", height="+WindowHeight+", top="+WindowTop+", left="+WindowLeft);
	NewWin.focus();
}	


function openDetail(TEAM_CD)
{
	//alert(L1_cd);
	//alert(TEAM_CD);
	
	var WindowWidth = 400;
	var WindowHeight = 366;
	var WindowLeft = (screen.width - WindowWidth)/2;
	var WindowTop= (screen.height - WindowHeight)/2;

	
	
	//var openUrl = "<c:url value='/epc/edi/consult/selectDepartmentDetailPopup.do?teamCd="+teamCd+"&orgCd="+orgCd+"'/>";
	var openUrl = "<c:url value='/epc/edi/consult/selectL1cdDetailPopup.do?L1_CD="+L1_cd+"'/>";
	var popTitle = "_newCodes";
	NewWin = window.open(openUrl, popTitle, "titlebar=no, resizable=1, width="+WindowWidth+", height="+WindowHeight+", top="+WindowTop+", left="+WindowLeft);
	NewWin.focus();
	
}
//-->
</script>

</head>

<body>

<form name="MyForm" ID="Form1" action="${ctx }/epc/edi/consult/insertStep1.do" method="post">
<input type="hidden" name="orgCd" />
<input type="hidden" name="l1Cd" />
<input type="hidden" name="teamCd" />
<div id="wrap">

	<div id="con_wrap">
		

		<div class="con_area">

			<!-- title -->
			<div class="con_title">
				<h1><img src="/images/epc/edi/consult/h1-advice-apply-list.gif" alt="상담신청"></h1>
			</div>
			<!--// title -->

			<div class="product-list clearfloat">
				<div class="fl_left mr10">
					<div class="tb_h_common_02">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
						
						<colgroup><col width="82px"><col width="277px"><col width="66px"><col width="*"></colgroup>
						<thead>
							<tr>
								<th width="82px" class="line">팀명</th>
								<th width="277px" class="line">주요상품군</th>
								<th width="*">신청</th>
							</tr>
						</thead>
						<tbody>
						<c:set var="oldTeamCd"  value="" />
						<c:set var="subCnt"     value="0" />
						<%--<c:set var="setCnt"     value="${fn:length(departmentList)/2}" />  --%>
						<c:set var="setCnt"     value="14" />
						<c:forEach items="${consultcategroyList}" var="consultcategroyList" varStatus="index" >
							
							 <c:if test="${ setCnt > index.count }">
							 	<tr>
									<td class="team_name t_center">
									${consultcategroyList.TEAM_NM }
									</td>
								<%-- <td class="t_center"><a href="javascript:openDetail('${consultcategroyList.TEAM_CD}')" >${consultcategroyList.MAIN_PROD}</a></td> --%>
									<td class="t_center">${consultcategroyList.MAIN_PROD}</td>
									<td class="t_center"><span class="btn_white btn_w_td_ls"><span class="btn_td_w_01"><a href="javascript:confirm_submit_new('${consultcategroyList.TEAM_CD}');">신청</a></span></span></td>
								</tr>
						 	<c:set var="oldTeamCd" value="${consultcategroyList.TEAM_CD }" />
						<!-- 	<c:set var="subCnt"    value="${subCnt+1 }" />
							-->
							 </c:if>
						</c:forEach>
						 
						</tbody>
						</table>
					</div>
				</div>
				
				<div class="fl_left">
					<!-- td에 2줄이상인경우 상하 여백을 맞춰주기위한 class인 cell_pd3을 넣어준다 -->
					<div class="tb_h_common_02">
						 					<table cellpadding="0" cellspacing="0" border="0" width="100%">
											
											<colgroup><col width="82px"><col width="277px"><col width="66px"><col width="*"></colgroup>
											<thead>
												<tr>
													<th width="82px" class="line">팀명</th>
													<th width="277px" class="line">주요상품군</th>
													<th width="*">신청</th>
												</tr>
											</thead>
											<tbody>
											<c:set var="oldTeamCd"  value="" />
											
											<c:forEach items="${consultcategroyList}" var="consultcategroyList" varStatus="index" >
											
												 <c:if test="${  index.count > subCnt }">
												 	<tr>
														<td class="team_name t_center">${consultcategroyList.TEAM_NM }</td>
														<td class="t_center">${consultcategroyList.MAIN_PROD}</td>
														<td class="t_center"><span class="btn_white btn_w_td_ls"><span class="btn_td_w_01"><a href="javascript:confirm_submit_new('${consultcategroyList.TEAM_CD}');">신청</a></span></span></td>
													</tr>
													<c:set var="oldTeamCd" value="${consultcategroyList.TEAM_CD }" />
												</c:if>
											</c:forEach>
											 
											</tbody>
											</table>
										
					</div>
				</div>
			</div>
			
			
			
			

		</div>

		
	</div>

</div>

</form>

</body>
</html>