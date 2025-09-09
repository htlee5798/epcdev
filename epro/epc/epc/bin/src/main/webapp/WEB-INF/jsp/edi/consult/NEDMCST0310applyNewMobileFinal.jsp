<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<title><spring:message code='text.consult.field.lotteVendorConsult'/></title>

<script language="javascript">



	function downloadConsultDocumnet(vendorBusinessNo) {
		window.open("<c:url value='/edi/consult/NEDMCST0310step3DocumnetPopup.do?vendorBusinessNo='/>" + vendorBusinessNo, "docwin", "width=200, height=300, scrollbars=yes, resizable=yes");
	}
	
	function downloadConsultDocumnet2(vendorBusinessNo) {
		window.open("<c:url value='/edi/consult/NEDMCST0310step3DocumnetPopupKindCd.do?vendorBusinessNo='/>"+ vendorBusinessNo, "docwin",
				"width=200, height=300, scrollbars=yes, resizable=yes");
	}
	
	function pwdcheck()	{		
		
		var upw = $("#MyForm #password").val();	
		
		if(!/^[a-zA-Z0-9]{8,12}$/.test(upw))
		{
			alert("<spring:message code='msg.consult.require.pwdCheck1' />");		
			return false;
	 	}		
		var chk_num = upw.search(/[0-9]/g);
		var chk_eng = upw.search(/[a-z]/ig);	 
		
		if(chk_num < 0 || chk_eng < 0)
		{
			alert("<spring:message code='msg.consult.require.pwdCheck2' />");				
			return false;
		}
		
		return true;
	}
 
 
	function checkApply_submit(val)
	{	
	
	if(val=="Y"){
		alert("<spring:message code='msg.consult.approval.prosessing' />");	
		return;
	}else if (val=="C"){
		alert("<spring:message code='msg.consult.approval.access' />");	
		return;
	
	}else {			
		
		if(!pwdcheck() ) {
			return;
		}
		
		if($("#MyForm #updateGb").val() != "1")
		{
		
			var cf = confirm("<spring:message code='msg.consult.confirm.req' />");
			if (cf == true)
			{
				$("#MyForm #updateGb").val("1");
				//password
				$("#MyForm").submit();
			}
			else
				return;
			}
		
		else
			alert("<spring:message code='msg.consult.duplicated.req' />");
	}
		
	}
	
	
	function img_view(imgurl, imgpath) 
	{
		  var w = "600";
		  var h = "600";
		  var width = "600";
		  var height = "600";
		  var path = "upload/" + imgurl;
		  var openWidth = "650";
		  var openHeight = "600";

		  if (openWidth > 800)
		    openWidth = 800;
		  if (openHeight > 800)
		    openHeight = 800;
		        
		  window.open("<c:url value='/edi/consult/NEDMCST0310step3ImagePopup.do?img='/>" + imgurl + "&imgpath=" + imgpath + "&h=" + h, "imgwin", "width=" + openWidth + ", height=" + openHeight + ", scrollbars=yes, resizable=yes");
	}	
	
	function img_viewNew(imgurl, imgpath)
	{
		  var w = "600";
		  var h = "600";
		  var width = "600";
		  var height = "600";
		  var path = "upload/" + imgurl;
		  var openWidth = "650";
		  var openHeight = "600";

		  if (openWidth > 800)
		    openWidth = 800;
		  if (openHeight > 800)
		    openHeight = 800;
		        
		  window.open("<c:url value='/edi/consult/NEDMCST0310step3ImagePopupNew.do?img='/>" + imgurl + "&imgpath=" + imgpath + "&h=" + h, "imgwin", "width=" + openWidth + ", height=" + openHeight + ", scrollbars=yes, resizable=yes");
	}	
	
function deleteBusinessIntroduction(bmanNo) {
		
		if(confirm("<spring:message code='msg.common.confirm.delete'/>")){
			var str = {"bmanNo" 		: bmanNo };
					
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : "<c:url value='/edi/consult/NEDMCST0310deleteBusinessIntroduction.do'/>",
				data : JSON.stringify(str),
				success : function(data) {	
					if(data>0){
						alert("<spring:message code='msg.common.success.delete'/>");
						location.reload();
					}else{
						
					}
				}
			});			
		}
	}
	
	function deleteImg(bmanNo, seq) {
		
		if(confirm("<spring:message code='msg.common.confirm.delete'/>")){
						
			var str = { 
					"bmanNo" 		: bmanNo, 
					"seq" 		: seq
			};
			console.log(str);		
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : "<c:url value='/edi/consult/NEDMCST0310deleteImg.do'/>",
				data : JSON.stringify(str),
				success : function(data) {	
					if(data>0){
						alert("<spring:message code='msg.common.success.delete'/>");
						location.reload();
					}else{
						
					}
				}
			});			
		}
	}
	
	function deleteConsultDocumnet(vendorBusinessNo) {
		
		if(confirm("<spring:message code='msg.common.confirm.delete'/>")){
			var str = {"bmanNo" 		: vendorBusinessNo };
					
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : "<c:url value='/edi/consult/NEDMCST0310step3DocumnetDeletePopupKindCd.do'/>",
				data : JSON.stringify(str),
				success : function(data) {	
					if(data>0){
						alert("<spring:message code='msg.common.success.delete'/>");
						location.reload();
					}else{
						
					}
				}
			});			
		}
	}
	
	
	
	
</script>
</head>
<body>

<form name="MyForm" action="<c:url value='/epc/edi/consult/NEDMCST0310insertStepApplyFinal.do'/>" method="post" id="MyForm">
		
	<div id="wrap">
		<div id="con_wrap">
			<div class="con_area">
				<!-- 기본정보 -->
					
				
				<div class="s_title mt20">
					<h2><spring:message code='text.consult.field.consultHopeDivision'/></h2>
				</div>
		
				<div class="tb_v_common">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">						
						<colgroup>
							<col width="130px">
							<col width="300px">
							<col width="130px">
							<col width="300px">
						</colgroup>
						<tbody>
							<tr>
								<th><p><spring:message code='text.consult.field.bmanNo'/></p></th>
								<td colspan="3"><p class="td_p"><c:out value='${ vendorSession.vendor.bmanNo }'/></p></td>
							</tr>
							
							<tr>
								<th><p><spring:message code='text.consult.field.hopeDivision'/></p></th>
								<td><p class="td_p"><c:out value='${vendor.teamNm }'/></p></td>
								<th><p><spring:message code='text.consult.field.mainCat'/></p></th>
								<td><p class="td_p"><c:out value='${vendor.l1Nm }'/></p></td>
							</tr>
								
						</tbody>
					</table>
				</div>
				
				<div class="s_title mt20">
					<h2><spring:message code='text.consult.field.consultApplicantList'/></h2>
				</div>
				
				<div class="tb_v_common">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">						
						<colgroup>
							<col width="130px">
							<col width="300px">
							<col width="130px">
							<col width="300px">
						</colgroup>
						<tbody>		
							<tr>	
								<th><p><spring:message code='text.consult.field.hndNm'/></p></th>
								<td><p class="td_p"><c:out value='${vendor.hndNm}'/></p></td>
								<th><p><spring:message code='text.consult.field.ceoNm'/></p></th>
								<td><p class="td_p"><c:out value='${vendor.ceoNm}'/></p></td>
							</tr>
							
							<tr>
								<th><p><spring:message code='text.consult.field.officetel'/></p></th>
								<td><p class="td_p"><c:out value='${vendor.officetel1}'/>-<c:out value='${vendor.officetel2}'/>-<c:out value='${vendor.officetel3}'/></p></td>
								<th><p><spring:message code='text.consult.field.email'/></p></th>
								<td><p class="td_p"><c:out value='${vendor.email}'/></p></td>
							</tr>								
												
						</tbody>
					</table>
				</div>
	
				<div class="s_title mt20">
					<h2><spring:message code='text.consult.field.consultProdList'/></h2>
				</div>
		
				<div class="tb_v_common">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">						
						<colgroup>
							<col width="130px">
							<col width="300px">
							<col width="130px">
							<col width="300px">
						</colgroup>
						<tbody>		
							<tr>
								<th rowspan="3"><p><spring:message code='text.consult.field.productName'/></p></th>
								
									<c:if test="${fn:length(vendorProductList) == 0 }">
									
									</c:if>
									
									<c:if test="${fn:length(vendorProductList) > 0 }">
										<c:forEach var="vendorProduct" items="${vendorProductList}"	varStatus="status">
										
											<td ><p class="td_p"><c:out value='${vendorProduct.productName }'/></p></td>
											<td colspan="2">
												<c:if	test="${not empty vendorProduct.attachFileCode }">
													<a	href="javascript:img_view('<c:out value="${vendorProduct.attachFileCode}"/>', '<c:out value="${attachFileFolder}"/>')" class="btn">
											 		<spring:message code='text.consult.field.viewImg'/></a>
											 		<c:if test="${gubunBlock =='N'}"> 
											 			<span style="margin-left: 20px"></span>&nbsp;<a	href="javascript:deleteImg('<c:out value="${vendor.bmanNo }"/>','<c:out value="${vendorProduct.seq }"/>')"><spring:message code='text.consult.field.docDelete'/></a>
											 		</c:if>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</c:if>							
						</tbody>
					</table>
				</div>
				
				<div class="s_title mt20">
					<h2><spring:message code='text.consult.field.exhibitResources'/></h2>
				</div>
				
				<div class="tb_v_common">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">						
						<colgroup>
							<col width="130px">
							<col width="300px">
							<col width="130px">
							<col width="300px">
						</colgroup>
						
						<tbody>	
							<tr>							
								<th><p><spring:message code='text.consult.field.businessIntroduction'/></p></th>
								<td colspan="3">
									<c:if test="${not empty vendor.attachFileCode }"><span style="margin-left: 50px"><c:out value='${vendor.attachFileCode}'/></span>&nbsp;
										<a href="javascript:downloadConsultDocumnet('<c:out value="${vendor.bmanNo}"/>')"><spring:message code='text.consult.field.download'/></a>
										<c:if test="${gubunBlock =='N'}"> 
											<span style="margin-left: 50px"></span>&nbsp;<a	href="javascript:deleteBusinessIntroduction('<c:out value="${vendor.bmanNo }"/>')"><spring:message code='text.consult.field.docDelete'/></a>
										</c:if>
									</c:if>	
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<div class="s_title mt20">
					<h2><spring:message code='text.consult.field.uniqueness'/></h2>
				</div>
				
				<div class="tb_v_common">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">						
						<colgroup>
							<col width="130px">
							<col width="300px">
							<col width="130px">
							<col width="300px">
						</colgroup>
						<tbody>			
						
							<tr>
								<div class="box_etc_input">
									<textarea cols="122" rows="5" name="content" readOnly	style="width: 828px; height: 40px;"><c:out value='${vendor.pecuSubjectContent}'/></textarea>
								</div>						
							</tr>														
						</tbody>
					</table>
				</div>
				
				<!-- 결격사유 자가진단 -->
				<div class="s_title mt20">
					<h2><spring:message code='text.consult.field.selfDiagnosis'/>(<spring:message code='text.consult.field.consultKind1'/>)</h2>
				</div>
				
				<div class="tb_h_common_02">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<caption><spring:message code='text.consult.field.selfDiagnosis'/></caption>
						<colgroup>
							<col width="100px">
							<col width="450px">
							<col width="100px">
							<col width="*">
						</colgroup>
						<thead>
							<tr>
								<th rowspan="2"><spring:message code='text.consult.header.bunho'/></th>
								<th rowspan="2"><spring:message code='text.consult.header.disqualification'/></th>
								<th colspan="2"><spring:message code='text.consult.header.evaluation'/></th>
							</tr>
							<tr>
								<th>YES</th>
								<th>NO</th>	
							</tr>
						</thead>
						<tbody>											
							<c:if test="${not empty answerList}">
								<c:forEach items="${answerList}" var="list" varStatus="status">
									<tr class="line_dot">
										<td class="t_center"><c:out value='${list.seq+1}'/></td>
										<td class="t_left"><p><c:out value='${list.queryDesc}'/></p></td>
										<c:choose>
											<c:when test="${list.colVal eq 'Y'}">
												<td align="center"><c:out value='${list.colVal}'/></td>
												<td align="center"></td>
											</c:when>

											<c:otherwise>
												<td align="center"></td>
												<td align="center"><c:out value='${list.colVal}'/></td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>
				
				<div class="s_title mt20"></div>
				
				<div class="tb_v_common">
					<table cellpadding="0" cellspacing="0" border="0" width="100%">						
						<colgroup>
							<col width="130px">
							<col width="300px">
							<col width="130px">
							<col width="300px">
						</colgroup>
						<tbody>	
							<tr>							 
								<th><p><spring:message code='text.consult.field.fieUpload'/></p></th>
								<td colspan="3">
									<c:if test="${not empty vendor.atchFileKindCd }"><span style="margin-left: 20px"><c:out value='${vendor.atchFileKindCd}'/></span>&nbsp;
										<a href="javascript:downloadConsultDocumnet2('<c:out value="${vendor.bmanNo}"/>')"><spring:message code='text.consult.field.download'/></a>
										<%-- <span style="margin-left: 20px"></span>&nbsp;<a href="javascript:deleteConsultDocumnet('<c:out value="${vendor.bmanNo}"/>')"><spring:message code='text.consult.field.docDelete'/></a> --%>					
									</c:if>	
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			
				<div class="s_title mt20"></div>
				
				<c:if test="${gubunBlock =='N'}">
					<div class="tb_v_common">
						<table cellpadding="0" cellspacing="0" border="1" width="100%">
							<colgroup>
								<col width="130px">
								<col width="150px">
								<col width="130px">
								<col width="300px">
							</colgroup>
							<thead>
								<tr>
									<th><p><spring:message code='text.consult.header.password'/></p></th>
									<td>
										<span class="input_txt" style="margin-left: 20px"><span>
										<input type="password" name="password"  id="password" maxlength="12" class="txt"	style="width: 150px;"></span></span>
									</td>
									<td></td>
									<td colspan="2" >
										<p class="td_p"><spring:message code='text.consult.header.passwordInfo1'/></p>
									</td>
								</tr>
						</table>
					</div>
				</c:if>
				
				<div class="s_title mt20">
					<h3><font color="red"><spring:message code='text.consult.header.passwordInfo2'/></font></h3>
					<h3><font color="red"><spring:message code='text.consult.header.passwordInfo3'/></font></h3>
				</div>
				
				<!-- button -->
				<div class="btn_c_wrap mt30">
					<span class="btn_white"><span>
						<a href="javascript:checkApply_submit('<c:out value="${gubunBlock }"/>');"><spring:message code='button.consult.apply' /></a></span></span>
				</div>
				<!--// button -->
	
			</div>
		</div>
	</div>


		<input type="hidden" name="updateGb" id="updateGb"	value="" > 
		<input type="hidden" name="teamNm"   id="teamNm" 	value="<c:out value='${vendor.teamNm }'/>" >
		<input type="hidden" name="gubunIn"  id="gubunIn" 	value="<c:out value='${gubunBlock }'/>" />
	</form>
</body>
</html>