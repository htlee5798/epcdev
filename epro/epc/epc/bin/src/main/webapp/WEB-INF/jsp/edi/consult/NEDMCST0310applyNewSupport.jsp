<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title><spring:message code='text.consult.field.lotteVendorConsult'/></title>
<script type="text/javascript" src="/js/epc/showModalDialog.js"></script>
<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>

<script language="javascript">

	$(document).ready(function() {	
		
		if("<c:out value='${errMsg}'/>"){
			alert("<c:out value='${errMsg}'/>");
			history.back(-1);
			return false;
		}
		 
		$( "#MyForm input[name='vendorAttachFile']" ).change( function() {
			var checkReturn = true;
			var fileExtension = getFileExtension($(this).val()).toLowerCase();
			var files = $( "#MyForm input[name='vendorAttachFile']" );			// file 명 
			
			if( fileExtension != "ppt" & fileExtension != "pptx" & fileExtension != "pdf" ) {
				
				alert( "<spring:message code='msg.consult.require.fileExtensionPptPptxPdf'/>" );
				checkReturn = false;
				
			} else {
				
				var iSize = document.getElementById(this.name).files[0].size;
				
				if ( iSize > ( 1024 * 1024 ) * 2) {
					iSize = (Math.round((iSize / 1024) * 100) / 100);
					alert("<spring:message code='msg.consult.require.maxSize2m'/>"); /* 첨부가능한 파일의 용량은 2MB 입니다.  */	
					checkReturn = false;
				}
			}
			 
			/* 파일삭제 */
			if(!checkReturn){
				files.replaceWith( files.val('').clone(true)); // 잘못된 확장자 들어올경우 초기화
			}
			
			return checkReturn;
			
		});
			
		
	});
	
	function checkApplySubmit() {			
	
			if( !check_submit() ) {
				return;
			}	
	
		var email1 = $("#MyForm input[name=email1]").val();
		var email2 = $("#MyForm input[name=email2]").val();

		var email =  email1+"@"+email2;
		$("#MyForm input[name=email]").val(email);
	

		
		//-----특이사항 2000byte 초과시 return[최대 공백없이 한글 100자 입력 가능 스크립트에서는 한글을 2byte로 체크하나 DB에서는 3byte로 인식]
		if(!calByteProd( MyForm.content,	2000,	'<spring:message code="text.consult.field.uniqueness"/>',	false)) 		return;
		
		$("#MyForm").submit();		
		
	}

	function openPopup(openUrl, popTitle)
	{	
		winstyle = "dialogWidth=400px; dialogHeight:400px; center:yes; status: no; scroll: yes; help: no"; 
		result = window.showModalDialog(openUrl, window, winstyle);
	}	
	
	function check_submit() 
	{	
		
		if ($("#MyForm input[name='hndNm']").val().replace(/\s/gi, '') == "") 
		{
			/* strMsg ="msg.no.company.name";         
			popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=hndNm";
			openPopup(popUrl,'ERROR');   
		
			return false; */
			alert("상호명을 입력해주세요")
			$("#MyForm input[name='hndNm']").focus();
			return false;
		}
		
		//대표자명
		if ($("#MyForm input[name='ceoNm']").val().replace(/\s/gi, '') == "") 
		{	
			/* strMsg ="msg.ceo.name";
			popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=ceoNm";
			openPopup(popUrl,'ID');   
			return false; */
			alert("대표자명을 입력해주세요");
			$("#MyForm input[name='ceoNm']").focus();
			return false;
		}
	
		if ($("#MyForm input[name='mainProduct']").val().replace(/\s/gi, '') == "") 
		{	
			/* strMsg ="msg.main.product";
			popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=mainProduct";
			openPopup(popUrl,'ID');   
			return false; */
			alert("주력품목(생산품)을 입력해주세요");
			$("#MyForm input[name='mainProduct']").focus();
			return false;
		}
		
		//대표전화번호
		/* if ($("#MyForm input[name='officetel1']").val().length == 0 || $("#MyForm input[name='officetel2']").val().length == 0 || $("#MyForm input[name='officetel3']").val().length == 0)
		{
			var targetObject = "";
			
			if($("#MyForm input[name='officetel1']").val().length == 0){
				targetObject = "officetel1";
			} else if($("#MyForm input[name='officetel2']").val().length == 0){
				targetObject = "officetel2";
			} else if($("#MyForm input[name='officetel3']").val().length == 0){
				targetObject = "officetel3";
			} else {
			}
			
			strMsg ="msg.office.telephone";         
			popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus="+targetObject;
			openPopup(popUrl,'ID');   
		
			return false;
		} */
		
		if ($("#MyForm input[name='officetel1']").val().replace(/\s/gi, '') == "") {
			alert("연락처 입력을 정확히 해주세요.");
			$("#MyForm input[name='officetel1']").focus();
			return false;
		}
		
		if ($("#MyForm input[name='officetel2']").val().replace(/\s/gi, '') == "") {
			alert("연락처 입력을 정확히 해주세요.");
			$("#MyForm input[name='officetel2']").focus();
			return false;
		}
		
		if ($("#MyForm input[name='officetel3']").val().replace(/\s/gi, '') == "") {
			alert("연락처 입력을 정확히 해주세요.");
			$("#MyForm input[name='officetel3']").focus();
			return false;
		}
		
		if ($("#MyForm input[name='email1']").val().replace(/\s/gi, '') == "") {
			alert("이메일 주소를 입력해주세요.");
			$("#MyForm input[name='email1']").focus();
			return false;
		}
			
		if ($("#MyForm input[name='email2']").val().replace(/\s/gi, '') == "") {
			alert("이메일 주소를 입력해주세요.");
			$("#MyForm input[name='email2']").focus();
			return false;
		}
		
		/* if ($("#MyForm input[name='email1']").val().length == 0 || $("#MyForm input[name='email2']").val().length == 0) 
		{
			
			var targetObject = "";
			if ($("#MyForm input[name='email1']").val().length == 0) {
				targetObject = "email1";
			}else if ($("#MyForm input[name='email2']").val().length == 0) {
				targetObject = "email2";
			} else {
			}
			
			strMsg ="msg.email";         
			popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus="+targetObject;
			openPopup(popUrl,'ID');   
		
			return false;
		}	 */

		if ($("#MyForm input[name='yearSaleAmount']").val().replace(/\s/gi, '') == "") 
		{
			
			/* strMsg ="msg.annual.amount";         
			popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=yearSaleAmount";
			openPopup(popUrl,'ID');   
		
			return false; */
			alert("연매출액(전년기준)을 입력해주세요");
			$("#MyForm input[name='yearSaleAmount']").focus();
			return false;
		}
			
		var yearSaleAmount = $("#MyForm input[name='yearSaleAmount']").val().replace(/,/g, '');
		$("#MyForm input[name='yearSaleAmount']").val(yearSaleAmount);
		
		
		if ($("#MyForm input[name='vendorAttachFile']").val().length != 0){
			var fileExtension = getFileExtension($("#MyForm input[name='vendorAttachFile']").val()).toLowerCase();
			if(fileExtension != "ppt" & fileExtension != "pptx" & fileExtension != "pdf" ) {
				alert("<spring:message code='msg.consult.require.fileExtensionPptPptxPdf'/>");
				return false;
			}
		}
		
		
		return true;
	}
	
	
	
	function Nextfocus(lenth,name,a)  
	{
		if (name=="tel1")			
		{
			for( i=0 ; i<a.value.length; i++)
			{
				if(a.value.charAt(i) <"0" || a.value.charAt(i)>"9")
				{
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value="";
					return;
				}
			}
			if(lenth==3)
			{
				document.MyForm.tel2.focus();
			}
		}	
		else if (name=="tel2")
		{
			
			for( i=0 ; i<a.value.length; i++)
			{
				if(a.value.charAt(i) <"0" || a.value.charAt(i)>"9")
				{
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value="";
					return;
				}
			}
			if(lenth==4)
			{
				document.MyForm.tel3.focus();
			}
		}	
		else if (name=="tel3")
		{

			
			for( i=0 ; i<a.value.length; i++)
			{
				if(a.value.charAt(i) <"0" || a.value.charAt(i)>"9")
				{
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value="";
					return;
				}
			}
			if(lenth==4)
			{
				//document.MyForm.fax_no1.focus();
			}
	
	
		}
		else if (name=="officetel1")
		{
			for( i=0 ; i<a.value.length; i++)
			{
				if(a.value.charAt(i) <"0" || a.value.charAt(i)>"9")
				{
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value="";
					return;
				}
			}
			if(lenth==3)
			{
				document.MyForm.officetel2.focus();
			}
		}
		else if (name=="officetel2")
		{
			for( i=0 ; i<a.value.length; i++)
			{
				if(a.value.charAt(i) <"0" || a.value.charAt(i)>"9")
				{
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value="";
					return;
				}
			}
			if(lenth==4)
			{
				document.MyForm.officetel3.focus();
			}
		}
		else if (name=="officetel3")
		{
			for( i=0 ; i<a.value.length; i++)
			{
				if(a.value.charAt(i) <"0" || a.value.charAt(i)>"9")
				{
					alert("<spring:message code='msg.common.error.noNum'/>");
					a.focus();
					a.value="";
					return;
				}
			}
			if(lenth==4)
			{
				//document.MyForm.home_addr.focus();
			}
		}
	}
	
	
	function downloadConsultDocumnet(vendorBusinessNo) {
		window.open("<c:url value='/edi/consult/NEDMCST0310step3DocumnetPopup.do?vendorBusinessNo='/>"+ vendorBusinessNo, "docwin","width=200, height=300, scrollbars=yes, resizable=yes");
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
	
	
	
</script>

</head>

<body>
<form name="MyForm" action="<c:url value='/epc/edi/consult/NEDMCST0310insertApplyConfirmSupport.do'/>" method="post" enctype="multipart/form-data" id="MyForm">
<input type="hidden" name="bmanNo" 		id="bmanNo"     value="<c:out value='${vendorSession.vendor.bmanNo}'/>" />
<input type="hidden" name="gubun" 		id="gubun"      value="<c:out value='${gubun }'/>" />
<input type="hidden" name="prodArray" 	id="prodArray"  value="" />
<input type="hidden" name="email" 		id="email"  	value="" />


<!--<input type="hidden" name="returnPage" value="epc/edi/consult/insertStep3.do" />
--><div id="wrap">
<div id="con_wrap">
<div class="con_area">

		
		
<br><br>


					<div class="s_title">
						<h2><spring:message code='text.consult.field.consultHopeDivision'/></h2>						
					</div>
					
					<div class="tb_form_comm">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<colgroup>
								<col width="140px">
								<col width="300px">
								<col width="130px">
								<col width="*">
							</colgroup>
							<tbody>							
						<tr>						
						<th><p class="check"><spring:message code='text.consult.field.hopeDivision'/></p></th>
						<td>
							<div class="td_p">
								<input class="chk" id="tSubteamCd1" type="radio" name="tSubteamCd" value="00022" <c:if test="${vendor.tSubteamCd eq 00088}"> checked </c:if> <c:if test="${empty vendor.bmanKindCd}"> checked </c:if>  ><label class="label mr20" for="business1"><spring:message code='text.consult.field.ordTeam'/></label>
						   </div>
						</td>									
						<td colspan="2" align="right"><spring:message code='text.consult.field.consultHopeDivisionInfo'/>
						</td>
						</tr>
						</tbody>
					</table>
					</div>
					
					
					<br><br>						
					<div class="s_title">
						<h2><spring:message code='text.consult.field.consultApplicantList'/></h2>							
					</div>					
					<div class="tb_form_comm">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">							
							<colgroup>
								<col width="140px">
								<col width="300px">
								<col width="130px">
								<col width="*">
							</colgroup>
							<tbody>
							<tr>							
								<th width="130px"><p><spring:message code='text.consult.field.bmanNo'/></p></th>
								<td width="300px"><div class="td_p"><span class="t_gray t_12"><input type="text" name="bmanNo" id="bmanNo" class="txt" value="<c:out value='${ vendorSession.vendor.bmanNo }'/>"  readonly style="width:232px;"></span></div>
								</td>
							</tr>

					<tr>
						<th width="130px"><p class="check"><spring:message code='text.consult.field.hndNm'/></p></th>
						<td width="300px">
							<div class="td_p">
								<span class="input_txt"><span><input  maxlength="15"  type="text" class="txt" name="hndNm" id="hndNm" value="<c:out value='${vendor.hndNm}'/>" style="width:232px;" onkeyup="javascript:cal_byte(this, '50', '<spring:message code='text.consult.field.hndNm'/>');"></span></span>
							</div>
						</td>
						<th><p class="check"><spring:message code='text.consult.field.ceoNm'/></p></th>
						<td><div class="td_p"><span class="input_txt"><span><input maxlength="15"  type="text" name="ceoNm" id="ceoNm" class="txt" value="<c:out value='${vendor.ceoNm}'/>" style="width:232px;" onkeyup="javascript:cal_byte(this, '50', '<spring:message code='text.consult.field.ceoNm'/>');"></span></span></div>
						</td>
					</tr>
					<tr>
						<th><p class="check"><spring:message code='text.consult.field.officetel'/></p></th>
						<td>								
							<div class="td_p">
							<span class="input_txt"><span><input type="text" name="officetel1" id="officetel1" class="txt" value="<c:out value='${vendor.officetel1}'/>"  maxlength="3" onKeyUp="Nextfocus(this.value.length,this.name,this);" style="width:54px;"></span></span> -
							<span class="input_txt"><span><input type="text" name="officetel2" id="officetel2" class="txt" value="<c:out value='${vendor.officetel2}'/>"  maxlength="4" onKeyUp="Nextfocus(this.value.length,this.name,this);" style="width:54px;"></span></span> -
							<span class="input_txt"><span><input type="text" name="officetel3" id="officetel3" class="txt" value="<c:out value='${vendor.officetel3}'/>"  maxlength="4" onKeyUp="Nextfocus(this.value.length,this.name,this);" style="width:54px;"></span></span>
							</div>					
						</td>
						<th><p class="check"><spring:message code='text.consult.field.email'/></p></th>
						<td>
							<div class="td_p">
								<span class="input_txt"><span><input type="text" class="txt" name="email1" id="email1" value="<c:out value='${vendor.email1}'/>" style="width:90px;" onkeyup="javascript:cal_byte(this, '22', '<spring:message code="text.consult.field.email"/>');"></span></span> @
								<span class="input_txt"><span><input type="text" class="txt" name="email2" id="email2" value="<c:out value='${vendor.email2}'/>" style="width:110px;" onkeyup="javascript:cal_byte(this, '20', '<spring:message code="text.consult.field.email"/>');" ></span></span>
							</div>
						</td>				
					</tr>
					</tbody>
			</table>
								
								
								
								
								
					

<!-- 기본정보 --><br><br>
			<div class="s_title">
				<h2><spring:message code='text.consult.field.consultProdList'/></h2>
			</div>
<!-- 
			<div class="t_txt_wrap">
				<div class="ex_right"><p>표시는 필수입력사항</p></div>
			</div>
 -->			
			<div class="tb_form_comm">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
			
				<colgroup>
								<col width="140px">
								<col width="300px">
								<col width="130px">
								<col width="*">
							</colgroup>
				
				<tbody>
										
				
					<tr>					
						
							
						<th><p class="check"><spring:message code='text.consult.header.mainProduct'/></p></th>
						<td>
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="mainProduct" id="mainProduct" class="txt" value="<c:out value='${vendor.mainProduct }'/>"  maxlength="20" style="width:156px;"></span></span>
							</p>
						</td>
						<th ><p class="check"><spring:message code='text.consult.header.yearSaleAmount'/></p></th>
						<td >
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="yearSaleAmount" id="yearSaleAmount" class="txt" value="<c:out value='${vendor.yearSaleAmount }'/>" maxlength="7" onkeyup="displayComma(this, this.value);" style="width:76px;"></span></span> <spring:message code='text.consult.field.onehunmillion'/>
							</p>
						</td>
						
													
					</tr>		
					<tr>	
					
						
					</tr>
						
					
					
					
							
		<div class="tb_form_h_common_02">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">			
				<colgroup><col width="130px"><col width="300px"><col width="130px"><col width="*"></colgroup>
				</colgroup>		
				</table>
			</div>

		
			<br><br>
					<div class="s_title mt20">
						<h2><spring:message code='text.consult.field.exhibitResources'/></h2>
					</div>			
					<div class="tb_form_comm">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<colgroup>
							<col width="140px">
								<col width="300px">
								<col width="130px">
								<col width="*">						
						</colgroup>
						<tbody>			
						<tr>					
						
						<th width="140px"><p><spring:message code='text.consult.field.businessIntroduction'/></p></th>									
									<td colspan="3">
										<div class="td_p">
											 								
												<input type="hidden" name="vendorFile" id="vendorFile" value="${vendor.attachFileCode }" /> 	
												<input type="file" name="vendorAttachFile" id="vendorAttachFile" class="docUpload" value="" style="width: 250px; height: 18px;">
											<c:if test="${not empty vendor.attachFileCode }">
											<span style="margin-left: 50px"></span>&nbsp;<a	href="javascript:downloadConsultDocumnet('<c:out value="${vendor.bmanNo}"/>')"><spring:message code='text.consult.field.docDownload'/></a>
											<span style="margin-left: 50px"></span>&nbsp;<a	href="javascript:deleteBusinessIntroduction('<c:out value="${vendor.bmanNo }"/>')"><spring:message code='text.consult.field.docDelete'/></a>
											</c:if>
											<br>
											<font color="red"><spring:message code='text.consult.field.2mppt'/></font>	
											
										</div>
									</td>							
								</tr>
						</tbody>
					</table>
					</div>	
		
		
		
		
		<br><br>
					<div class="s_title mt20">
						<h2><spring:message code='text.consult.field.uniqueness'/></h2>
					</div>			
					<div class="tb_form_comm">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<colgroup>
							<col width="140px">
								<col width="300px">
								<col width="130px">
								<col width="*">						
						</colgroup>
						<tbody>			
						<tr>					
						
						<td colspan="4" rowspan="5">
						<div class="box_etc_input">
							<textarea cols="122" rows="5" name="content" id="content"	style="width: 828px; height: 40px;"><c:out value='${vendor.pecuSubjectContent}'/></textarea>
						</div>
						
						</tr>
					
						</tbody>
					</table>
					</div>
		
		

			

			<!-- button -->
			<div class="btn_c_wrap mt30">
				<span class="btn_red"><span><a href="javascript:checkApplySubmit();"><spring:message code='button.common.confirm'/></a></span></span>
			</div>
			<!--// button -->

		</div>


						
	</div>

</div>



</form>

</body>
</html>