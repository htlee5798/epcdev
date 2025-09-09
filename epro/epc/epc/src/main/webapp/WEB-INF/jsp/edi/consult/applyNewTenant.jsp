<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>롯데마트 입점상담</title>


<script language="javascript">
		
		function checkApplySubmit() {			
			
 			if( !check_submit() ) {
 				return;
 			}
			

			var email1 = $("input[name=email1]").val();
			var email2 = $("input[name=email2]").val();

			var email =  email1+"@"+email2;
			$("input[name=email]").val(email);
			

			$("form[name=MyForm]").submit();
		
			
		}

		function openPopup(openUrl, popTitle)
		{	
			winstyle = "dialogWidth=400px; dialogHeight:400px; center:yes; status: no; scroll: yes; help: no"; 
			result = window.showModalDialog(openUrl, window, winstyle);
		}	

		
		function check_submit() 
		{	
			var MyForm = document.getElementsByName("MyForm")[0];
			
			if(MyForm.hndNm.value.length==0)
			{
				strMsg ="msg.no.company.name";         
				popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=hndNm";
				openPopup(popUrl,'에러');   
			
				return false;
			}
			
			//대표자명
			if(MyForm.ceoNm.value.length==0)
			{	
				strMsg ="msg.ceo.name";
				popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=ceoNm";
				openPopup(popUrl,'ID');   
				return false;
			}
		
			//대표전화번호
			if((MyForm.officetel1.value.length==0) || (MyForm.officetel2.value.length==0) || (MyForm.officetel3.value.length==0))
			{
				var targetObject = "";
				
				if(MyForm.officetel1.value.length==0) {
					targetObject = "officetel1";
				}
				else if(MyForm.officetel2.value.length==0) {
					targetObject = "officetel2";
				}
				
				else if(MyForm.officetel3.value.length==0) {
					targetObject = "officetel3";
				}
				else {}
				
				strMsg ="msg.office.telephone";         
				popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus="+targetObject;
				openPopup(popUrl,'ID');   
			
				return false;
			}
			
			if(MyForm.email1.value.length==0 || MyForm.email2.value.length==0 )
			{
				
				var targetObject = "";
				if(MyForm.email1.value.length==0) {
					targetObject = "email1";
				}
				
				else if(MyForm.email2.value.length==0) {
					targetObject = "email2";
				}
				else{}
				
				strMsg ="msg.email";         
				popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus="+targetObject;
				openPopup(popUrl,'ID');   
			
				return false;
			}
			//주력품목(생산품)
			if(MyForm.mainProduct.value.length==0)
			{	
				strMsg ="msg.main.product";         
				popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=mainProduct";
				openPopup(popUrl,'ID');   
			
				return false;
			}			
			//현재운영매장수
			if(MyForm.asisMystorecnt.value.length==0)
			{	
				strMsg ="msg.asisMystorecnt";
				popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=asisMystorecnt";
				openPopup(popUrl,'ID');   
				return false;
			}
			//입점희망점포
			if(MyForm.wishStore.value.length==0)
			{	
				strMsg ="msg.wishstore";
				popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=wishStore";
				openPopup(popUrl,'ID');   
				return false;
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
			window.open(
					'${ctx}/edi/consult/step3DocumnetPopup.do?vendorBusinessNo='
							+ vendorBusinessNo, 'docwin',
					'width=200, height=300, scrollbars=yes, resizable=yes');
		}
	</script>

</head>

<body>
<form name="MyForm" action="${ctx }/epc/edi/consult/insertApplyConfirmTenant.do" method="post" enctype="multipart/form-data" ID="Form1">
<input type="hidden" name="bmanNo" value="${vendorSession.vendor.bmanNo}" />
<input type="hidden" id="gubun" name="gubun" value="${gubun }" />
<input type="hidden" id=prodArray name="prodArray" value="" />
<input type="hidden" name="email" value="" />


<!--<input type="hidden" name="returnPage" value="epc/edi/consult/insertStep3.do" />
--><div id="wrap">
<div id="con_wrap">
<div class="con_area">

		
		
<br><br>




<div class="s_title">
						<h2>상담신청 희망부서</h2>						
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
						<th><p class="check">희망부서</p></th>
						<td>
					<div class="td_p">
								<input class="chk" id="tSubteamCd1" type="radio" name="tSubteamCd" value="15502" 
									<c:if test="${vendor.tSubteamCd eq 15502}"> checked </c:if> checked
									
								<label class="label mr20" for="business1">식품테넌트</label>
								<input class="chk" id="tSubteamCd2" type="radio" name="tSubteamCd" value="00088"  
									<c:if test="${vendor.tSubteamCd eq 00088}"> checked </c:if> ><label class="label" for="business2">비식품테넌트</label>
							</div>
					</td>								
						<td colspan="2" align="right">(상담희망부서 관련 문의처 : 02)2145-8000)
						</td>
						</tr>
						</tbody>
					</table>
					</div>
					
					
					
		<br><br>	
			<div class="s_title">
				<h2>상담신청인 내역</h2>
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
						<th width="130px"><p>사업자등록번호</p></th>
						<td width="300px"><div class="td_p"><span class="t_gray t_12"><input type="text" name="bmanNo" class="txt" value="${ vendorSession.vendor.bmanNo }"  ID="Text1" readonly style="width:232px;"></span></div>
						</td>
					</tr>
							
					<tr>
						<th width="130px"><p class="check">상호명</p></th>
						<td width="300px">
							<div class="td_p">
								<span class="input_txt"><span><input  maxlength="150"  type="text" class="txt" name="hndNm" value="${vendor.hndNm}" style="width:232px;" onkeyup="javascript:cal_byte(this, '50', '상호명');"></span></span>
							</div>
						</td>
						
						<th><p class="check">대표자명</p></th>
						<td><div class="td_p"><span class="input_txt"><span><input maxlength="15"  type="text" name="ceoNm" class="txt" value="${vendor.ceoNm}" style="width:232px;" onkeyup="javascript:cal_byte(this, '50', '대표자명');"></span></span></div></td>
					</tr>
					<tr>
						<th><p class="check">연락 받으실 번호</p></th>
						<td>
							<div class="td_p">
								<span class="input_txt"><span><input type="text" name="officetel1" class="txt" value="${vendor.officetel1}"  maxlength="3" onKeyUp="Nextfocus(this.value.length,this.name,this);" style="width:54px;"></span></span> -
								<span class="input_txt"><span><input type="text" name="officetel2" class="txt" value="${vendor.officetel2}"  maxlength="4" onKeyUp="Nextfocus(this.value.length,this.name,this);" style="width:54px;"></span></span> -
								<span class="input_txt"><span><input type="text" name="officetel3" class="txt" value="${vendor.officetel3}"  maxlength="4" onKeyUp="Nextfocus(this.value.length,this.name,this);" style="width:54px;"></span></span>
							</div>
						</td>		
						<th><p class="check">E-mail</p></th>
						<td>
							<div class="td_p">
								<span class="input_txt"><span><input type="text" class="txt" name="email1" value="${vendor.email1}" style="width:90px;"></span></span> @
								<span class="input_txt"><span><input type="text" class="txt" name="email2" value="${vendor.email2}" style="width:110px;"></span></span>
							</div>
						</td>										
					</tr>			
				
					
					</tbody>
					</table>
								
								
								<br><br>	
			<div class="s_title">
				<h2>상담신청 내역</h2>
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
						<th><p class="check">주력품목(생산품)</p></th>
						<td>
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="mainProduct" class="txt" value="${vendor.mainProduct }"  maxlength="20" style="width:106px;"></span></span>
							</p>
						</td>
					
					
						<th><p class="check">현재 운영 매장 수</p></th>
						<td>
						<p class="td_p">
						<span class="input_txt"><span><input type="text" name="asisMystorecnt" class="txt" value="${vendor.asisMystorecnt }" maxlength="9" onkeyup="displayComma(this, this.value);" style="width:76px;"></span></span> 개
						</p>
						</td>	
						
					
				
				
						</td>	
					</tr>
					<tr>
					
					<th><p class="check">입점희망점포</p></th>
						<td colspan="3">
							<div class="td_p">
							<span class="input_txt">
							<span>
							<input maxlength="100"  type="text" name="wishStore" class="txt" value="${vendor.wishStore}" style="width:400px;" 	onkeyup="javascript:cal_byte(this, '70', '입점희망점포');">
							</span>
							</span>
							</div>
						</td>
					</tr>
					</tbody>
					</table>					
		
<br><br>
					<div class="s_title mt20">
						<h2>별첨 자료</h2>
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
						
						<th width="140px"><p>사업소개서</p></th>									
									<td colspan="3">
										<div class="td_p">
											 								
												<input type="hidden" name="vendorFile" value="${vendor.attachFileCode }" /> 	
												<input type="file" name="vendorAttachFile" class="docUpload" value="" style="width: 250px; height: 18px;">
											<c:if test="${not empty vendor.attachFileCode }">
											<span style="margin-left: 50px"></span>&nbsp;<a	href="javascript:downloadConsultDocumnet('${vendor.bmanNo}')">문서다운로드</a>
											</c:if>
											<br>
											<font color="red">첨부문서는 2M이하 용량 / PPT, PPTX, PDF 파일로 올려주세요.</font>	
											
										</div>
									</td>							
								</tr>
						</tbody>
					</table>
					</div>	
		
		
		
		
		<br><br>
					<div class="s_title mt20">
						<h2>특이사항</h2>
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
							<textarea cols="122" rows="5" name="content"	style="width: 828px; height: 40px;">${vendor.pecuSubjectContent}</textarea>
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