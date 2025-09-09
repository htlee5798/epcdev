<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code='text.consult.field.lotteVendorConsult'/></title>

 
<script language="JavaScript">

		$(document).ready(function(){
			<c:if test="${not empty param.orgCd}">
				$("select[name=orgCd]").val("<c:out value='${param.orgCd}'/>");
			</c:if>

			<c:if test="${empty param.orgCd}">
				$("select[name=orgCd]").val("<c:out value='${vendor.orgCd}'/>");
			</c:if>
			
			<c:if test="${not empty vendor.corpnDivnCd}">
				$("select[name=corpnDivnCd]").val("<c:out value='${vendor.corpnDivnCd}'/>");
			</c:if>
			
			<c:if test="${not empty vendor.l1Cd}">
				$("select[name=L1Cd]").val("<c:out value='${vendor.l1Cd}'/>");
			</c:if>
			
			var l1CdText = $("select[name=L1Cd] option:selected").text();
			
			if(l1CdText==""){
				$("select[name=L1Cd]").val("");
			}
			
			<c:if test="${not empty vendor.cell1}">
				$("select[name=cell1]").val("<c:out value='${vendor.cell1}'/>");
			</c:if>

		});	

		function submitStep1Form() {
			var form = document.MyForm;
			
			
			if( !check_submit_1() ) {
				return;
			}
			
			if(!validate(form)){
				return;
			}
			//부서코드가 null값으로 입력되었는데 확인 할 경우 check
			//alert($("select[name=L1Cd]").val());
			var l1Cd = $("select[name=L1Cd]").val();
			if(l1Cd==""){
		
				alert("<spring:message code='msg.consult.require.L1Cd'/>");
				
				//location.href = "${ctx }/epc/edi/consult/selectConsultCategory.do";
				return;
				
			}
			//document.MyForm.tel2
			
			var corpnRsdtNo1 = $("input[name=corpnRsdtNo1]").val();
			var corpnRsdtNo2 = $("input[name=corpnRsdtNo2]").val();

			var corpnRsdtNo = corpnRsdtNo1+corpnRsdtNo2;
			$("input[name=corpnRsdtNo]").val(corpnRsdtNo);

			var email1 = $("input[name=email1]").val();
			var email2 = $("input[name=email2]").val();

			var email =  email1+"@"+email2;
			$("input[name=email]").val(email);

			
			var zipNo1 = $("input[name=zipNo1]").val();
			var zipNo2 = $("input[name=zipNo2]").val();

			var zipNo =  zipNo1+zipNo2;
			$("input[name=zipNo]").val(zipNo);

			 //$("input[name=supplyAddr1]").val("aaaaa");
			 //$("input[name=supplyAddr2]").val("bbbbb");
			 
			$("form[name=MyForm]").submit();
		
			
		}
		
		
		function check_submit_1() 
		{	
			//var MyForm = document.getElementsByName("MyForm")[0];
			
			if($("#MyForm #hndNm").val().length==0)
			{
				
				strMsg ="msg.no.company.name";         
				popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=hndNm";
				
				
				openPopup(popUrl,"ERROR");   
			
				return false;
			}
			if($("#MyForm #ceoNm").val().length==0)
			{
				
				strMsg ="msg.ceo.name";
				popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=ceoNm";
				openPopup(popUrl,"ID");   
				return false;
			}
			if($("#MyForm #businessKind").val().length==0)
			{
				
				strMsg ="msg.businessKind";         
				popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=businessKind";
				openPopup(popUrl,"ID");   
			
				return false;
			}		
			if($("#MyForm #businessType").val().length==0)
			{
				
				strMsg ="msg.businessType";         
				popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=businessType";
				openPopup(popUrl,"ID");   
			
				return false;
			}
			
			
			
			/* if(MyForm.corpnDivnCd.value=="1"){
				if(MyForm.corpnRsdtNo1.value.length < 6 || MyForm.corpnRsdtNo2.value.length < 7)
				{
					if( MyForm.corpnRsdtNo1.value.length < 6 ) {
						targetObject = "corpnRsdtNo1";
					}
					
					if( MyForm.corpnRsdtNo1.value.length == 6 && MyForm.corpnRsdtNo2.value.length < 7 ) {
						targetObject = "corpnRsdtNo2";
					}
					
					strMsg ="msg.company.rsdt.no";         
					popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus="+targetObject;
					openPopup(popUrl,"ID");   
				
					return false;
				}
			} */
			
			/*if(MyForm.corp_nm.value.length==0)
			{
				
				strMsg ="대표브랜드을 입력해 주십시요 \nex)칠성사이다, 월드콘";         
				popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.corp_nm"
				openPopup(popUrl,"ID")   
			
				return false;
			}*/		
			
			
			if($("#MyForm #officetel1").val().length==0 || $("#MyForm #officetel2").val().length==0 || $("#MyForm #officetel3").val().length==0)
			{

				var targetObject = "";
				
				if($("#MyForm #officetel1").val().length==0) {
					targetObject = "officetel1";
				}
				else if($("#MyForm #officetel2").val().length==0) {
					targetObject = "officetel2";
				}
				
				else if($("#MyForm #officetel3").val().length==0) {
					targetObject = "officetel3";
				}
				else {}
				
				strMsg ="msg.office.telephone";         
				popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus="+targetObject;
				openPopup(popUrl,"ID");   
			
				return false;
			}
			
			if($("#MyForm #supplyAddr1").val().length==0)
			{			
				strMsg ="msg.address.find";       
				popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg+ "&objFocus=supplyAddr1";
				openPopup(popUrl,"ID")
			
				return false;
			}	
			
			if($("#MyForm #supplyAddr2").val().length==0)
			{			
				strMsg ="msg.address.text";         
				popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=supplyAddr2";
				openPopup(popUrl,"ID");
			
				return false;
			}	
			
			/*
			if((MyForm.fax1.value.length==0) || (MyForm.fax2.value.length==0) || (MyForm.fax3.value.length==0))
			{
				var targetObject = "";
				if(MyForm.fax1.value.length==0) {
					targetObject = "fax1";
				}
				else if(MyForm.fax2.value.length==0) {
					targetObject = "fax2";
				}
				else if(MyForm.fax3.value.length==0) {
					targetObject = "fax3";
				}
				else{}
				
				strMsg ="msg.fax.number";         
				popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus="+targetObject;
				openPopup(popUrl,"ID");   
			
				return false;
			}	
			*/
			
			
				
			/* if(MyForm.utakmanNm.value.length==0)
			{
				
				strMsg ="msg.duty.man";         
				popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=utakmanNm";
				openPopup(popUrl,"ID");   
			
				return false;
			}	 */
			/* if((MyForm.cell1.value.length==0) || (MyForm.cell2.value.length==0) || (MyForm.cell3.value.length==0))
			{

				var targetObject = "";
				
				if(MyForm.cell1.value.length==0) {
					targetObject = "cell1";
				}
				
				else if(MyForm.cell2.value.length==0) {
					targetObject = "cell2";
				}
				
				else if(MyForm.cell3.value.length==0) {
					targetObject = "cell3";
				}
				else {}
				
				
				strMsg ="msg.cellphone";         
				popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus="+targetObject;
				openPopup(popUrl,"ID");   
			
				return false;
			} */
			
			/*
			if((MyForm.tel1.value.length==0) || (MyForm.tel2.value.length==0) || (MyForm.tel3.value.length==0))
			{

				var targetObject = "";
				
				if(MyForm.tel1.value.length==0) {
					targetObject = "tel1";
				}
				else if(MyForm.tel2.value.length==0) {
					targetObject = "tel2";
				}
				
				else if(MyForm.tel3.value.length==0) {
					targetObject = "tel3";
				}
				else{}
				
				strMsg ="msg.telephone";         
				popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus="+targetObject;
				openPopup(popUrl,"ID");   
			
				return false;
			}
			*/
			
			if($("#MyForm #email1").val().length==0 || $("#MyForm #email2").val().length==0)
			{
				
				var targetObject = "";
				if($("#MyForm #email1").val().length==0) {
					targetObject = "email1";
				}
				
				else if($("#MyForm #email2").val().length==0) {
					targetObject = "email2";
				}
				else{}
				
				strMsg ="msg.email";         
				popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus="+targetObject;
				openPopup(popUrl,"ID");   
			
				return false;
			}	
			
			
			
			
			/*
			if(document.MyForm.supply_addr1.disabled)
				document.MyForm.supply_addr1.disabled = false;
			
			if(document.MyForm.supply_addr2.disabled)
				document.MyForm.supply_addr2.disabled = false;
			
			document.MyForm.bman_no_2.value = removeChar(document.MyForm.bman_no_2.value.toString(), "-");
			document.MyForm.ZipCode.value = removeChar(document.MyForm.ZipCode.value.toString(), "-");
			document.MyForm.submit();
			*/
			return true;
		}


		function validate(){
	
			if($("#MyForm #officetel2").val().length < 3 ){
				alert("<spring:message code='msg.common.error.length'/>");
				form.officetel2.focus();
				return false;
			}

			if($("#MyForm #officetel3").val().length < 4 ){
				alert("<spring:message code='msg.common.error.length'/>");
				form.officetel1.focus();
				return false;
			}
			
			if($("#MyForm #fax1").val().length > 0 ){
				if($("#MyForm #fax2").val().length < 3 ){
					alert("<spring:message code='msg.common.error.length'/>");
					$("#MyForm #fax2").focus();
					return false;
				}

				if($("#MyForm #fax3").val().length < 4 ){
					alert("<spring:message code='msg.common.error.length'/>");
					$("#MyForm #fax3").focus();
					return false;
				}
			}
			
			/* if(form.tel1.value != ""){
				if(form.tel2.value.length <3 ){
					alert("<spring:message code='msg.common.error.length'/>");
					form.tel2.focus();
					return false;
				}
	
				if(form.tel3.value.length <4 ){
					alert("<spring:message code='msg.common.error.length'/>");
					form.tel3.focus();
					return false;
				}
			} */
			
			return true;
		} 
		

		
		function Nextfocus(lenth,name,a)  
		{
			if (name=="corpnRsdtNo1")
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
				if(lenth==6)
				{
					$("#MyForm #corpnRsdtNo2").focus();
				}
			}	
			else if (name=="corpnRsdtNo2")
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
				if(lenth == 7)
				{
					//document.MyForm.aff_fg.focus();//계열사 구분
				}
			}	
			
		
			else if (name=="tel1")
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
					$("#MyForm #tel2").focus();
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
					$("#MyForm #tel3").focus();
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
			else if (name=="fax1")
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
					$("#MyForm #fax2").focus();
				}
			}	
			else if (name=="fax2")
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
					$("#MyForm #fax3").focus();
				}
			}
			else if (name=="fax3")
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
				/*if(lenth==4)
				{
					document.MyForm.aff_fg.focus();
				}*/
			}
					
			
			else if (name=="cell2")
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
					$("#MyForm #cell3").focus();
				}
			}
			else if (name=="cell3")
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
					//document.MyForm.email.focus();
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
					$("#MyForm #officetel2").focus();
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
					$("#MyForm #officetel3").focus();
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


		function PopupWindow(pageName) {
			var cw=480;
			var ch=480;
			var sw=screen.availWidth;
			var sh=screen.availHeight;
			var px=Math.round((sw-cw)/2);
			var py=Math.round((sh-ch)/2);
			window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
		}



		//우편번호 조회 함수
		function openZipCode(){
			PopupWindow('<c:url value="/edi/zip/PEDPZIP0001List.do"/>');
		}
		//도로명  조회 함수
		function openZipCodeNew(){
			PopupWindow('<c:url value="/edi/zip/PEDPZIP0001ListNew.do"/>');
		}
		

</script>


</head>

<body>

<form name="MyForm" id="MyForm" action="<c:url value='/epc/edi/consult/NEDMCST0310insertStep1.do'/>" method="post">

<input type="hidden" name="regDivnCd" id="regDivnCd" value="<c:out value='${vendorSession.vendor.regDivnCd }'/>" />
<input type="hidden" name="chgStatusCd" id="chgStatusCd" value="<c:out value='${vendorSession.vendor.chgStatusCd }'/>" />
<input type="hidden" name="corpnRsdtNo" id="corpnRsdtNo" value="<c:out value='${vendor.corpnRsdtNo}'/>" />
<input type="hidden" name="passwd" id="passwd" value="<c:out value='${vendorSession.vendor.passwd}'/>" />

<input type="hidden" name="email" id="email" value="" />
<input type="hidden" name="zipNo" id="zipNo" value="" />

<div id="wrap">

	<div id="con_wrap">
		

		<div class="con_area">

			

			<div class="step_wrap">
				<ol>
				   <li class="on"><a href="#"><img src="/images/epc/edi/consult/step-base-form-on.gif" alt="<spring:message code='text.consult.field.step-base-form'/>"></a></li>
					<li><img src="/images/epc/edi/consult/step-sales-form.gif" alt="<spring:message code='text.consult.field.step-sales-form'/>"></a></li>
					<li><img src="/images/epc/edi/consult/step-product-form.gif" alt="<spring:message code='text.consult.field.step-product-form'/>"></a></li>
					<li><img src="/images/epc/edi/consult/step-counsel-form.gif" alt="<spring:message code='text.consult.field.step-counsel-form'/>"></a></li>
					
				<!-- 	<li><a href="#" onclick="javascript:go_step('3');"><img src="/images/epc/edi/consult/step-sales-form.gif" alt="매출정보 입력"></a></li>
					<li><a href="#" onclick="javascript:go_step('4');"><img src="/images/epc/edi/consult/step-product-form.gif" alt="상품정보 입력"></a></li>
					<li><a href="#" onclick="javascript:go_step('5');"><img src="/images/epc/edi/consult/step-counsel-form.gif" alt="상담신청"></a></li> -->
					
				</ol>
			</div>

			<!-- 기본정보 -->
			<div class="s_title">
				<h2><spring:message code='text.consult.field.baseInfo'/></h2>
			</div>

			<div class="t_txt_wrap">
				<div class="ex_right"><p><spring:message code='text.consult.field.reqSymbol'/></p></div>
			</div>
			<div class="tb_form_comm">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption><spring:message code='text.consult.field.basicInformation'/></caption>
				<colgroup><col width="130px"><col width="300px"><col width="130px"><col width="*"></colgroup>
				<tbody>
					<tr>
						<th width="130px"><p class="check"><spring:message code='text.consult.field.corporationPlot'/></p></th>
						<td width="300px">
							<div class="td_p">
								<!-- select -->
								<select id="Select5" name="corpnDivnCd" style="width:124px;">
									<option selected value="1"><spring:message code='text.consult.field.corporation'/></option>
									<option value="2"><spring:message code='text.consult.field.individual'/></option>
									<option value="3"><spring:message code='text.consult.field.Non-business'/></option>
								</select>
								<!--// select -->
							</div>
						</td>
						<th width="130px"><p><spring:message code='text.consult.field.bmanNo'/></p></th>
							<td width="300px"><div class="td_p"><span class="t_gray t_12"><input type="text" name="bmanNo" id="bmanNo" class="txt" value="<c:out value='${ vendorSession.vendor.bmanNo }'/>"   readonly style="width:232px;"></span></div></td>
					</tr>
					<tr>
						<th><p class="check"><spring:message code='text.consult.field.bmanKindCd'/></p></th>
						<td>
							<div class="td_p">
								<input class="chk" id="business1" type="radio" name="bmanKindCd" value="2" <c:if test="${vendor.bmanKindCd eq 2}"> checked </c:if> <c:if test="${empty vendor.bmanKindCd}"> checked </c:if>  ><label class="label mr20" for="business1"><spring:message code='text.consult.field.bmanKindCd2'/></label>
								<input class="chk" id="business2" type="radio" name="bmanKindCd" value="1"  <c:if test="${vendor.bmanKindCd eq 1}"> checked </c:if> ><label class="label" for="business2"><spring:message code='text.consult.field.bmanKindCd1'/></label>
							</div>
						</td>
						<%-- <th><p class="check">계열사구분</p></th>
						<td>
							<div class="td_p">
								<input class="chk" id="affiliate1" type="radio" name="affYn" value="Y" <c:if test="${vendor.affYn eq 'Y'}"> checked</c:if> <c:if test="${empty vendor.affYn}"> checked </c:if>>     <label class="label mr20" for="affiliate1">Yes</label>
								<input class="chk" id="affiliate2" type="radio" name="affYn" value="N" <c:if test="${vendor.affYn eq 'N'}"> checked </c:if>><label class="label" for="affiliate2">No</label>
							</div>
						</td> --%>
						<th><p class="check"><spring:message code='text.consult.field.hndNm'/></p></th>
						<td>
							<div class="td_p">
								<span class="input_txt"><span><input  maxlength="15"  type="text" class="txt" name="hndNm" id="hndNm" value="<c:out value='${vendor.hndNm}'/>" style="width:232px;" onkeyup="javascript:cal_byte(this, '50', '<spring:message code="text.consult.field.hndNm"/>');"></span></span>
							</div>
						</td>
					</tr>
					<tr>
						<th><p class="check"><spring:message code='text.consult.field.businessKind'/></p></th>
						<td><div class="td_p"><span class="input_txt"><span><input maxlength="15"  type="text" name="businessKind" id="businessKind" class="txt" value="<c:out value='${vendor.businessKind }'/>" style="width:232px;" onkeyup="javascript:cal_byte(this, '50', '<spring:message code="text.consult.field.businessKind"/>');"></span></span></div></td>
						<th><p class="check"><spring:message code='text.consult.field.businessType'/></p></th>
						<td><div class="td_p"><span class="input_txt"><span><input maxlength="15"  type="text" name="businessType" id="businessType" class="txt" value="<c:out value='${vendor.businessType }'/>" style="width:232px;" onkeyup="javascript:cal_byte(this, '50', '<spring:message code="text.consult.field.businessType"/>');"></span></span></div></td>
					</tr>
					<tr>
						<th><p class="check"><spring:message code='text.consult.field.classification'/></p></th>
						<td>
							<div class="td_p">
								${TeamcdData.TEAM_NM } / <select class="select" name="L1Cd">
															<option value="">선택</option>
                       									 	<c:forEach items="${L1cdData}" var="L1cdData" begin="0">
                            									<option value="${L1cdData.L1_CD}">${L1cdData.L1_NM}</option>
                        								 	</c:forEach>
                        								 </select>
								<input type="hidden" name="teamCd" value="${TeamcdData.TEAM_CD }"/>
							</div>
						</td>
						<th><p><spring:message code='text.consult.field.corpnNm'/></p></th>
			<td><div class="td_p"><span class="input_txt"><span><input type="text" name="corpnNm" id="corpnNm" class="txt" value="<c:out value='${vendor.corpnNm}'/>" style="width:232px;" onkeyup="javascript:cal_byte(this, '100', '<spring:message code="text.consult.field.corpnNm"/>');"></span></span></div></td>
					</tr>
					<tr>
						<%-- <th><p>법인번호</p></th>
						<td>
							<div class="td_p">
								<span class="input_txt"><span><input type="text" name="corpnRsdtNo1" class="txt" value="${vendor.corpnRsdtNo1 }" maxlength="6" onKeyUp="Nextfocus(this.value.length,this.name,this);" style="width:99px;"></span></span> -
								<span class="input_txt"><span><input type="text" name="corpnRsdtNo2" class="txt" value="${vendor.corpnRsdtNo2}"  maxlength="7" onKeyUp="Nextfocus(this.value.length,this.name,this);" style="width:99px;"></span></span>
							</div>
						</td> --%>
						<th><p class="check"><spring:message code='text.consult.field.ceoNm'/></p></th>
						<td><div class="td_p"><span class="input_txt"><span><input maxlength="15"  type="text" name="ceoNm" id="ceoNm" class="txt" value="<c:out value='${vendor.ceoNm}'/>" style="width:232px;" onkeyup="javascript:cal_byte(this, '50', '<spring:message code="text.consult.field.ceoNm"/>');"></span></span></div></td>
						<th><p class="check"><spring:message code='text.consult.field.corpnTel'/></p></th>
						<td>
							<div class="td_p">
								<span class="input_txt"><span><input type="text" name="officetel1" id="officetel1" class="txt" value="<c:out value='${vendor.officetel1}'/>"  maxlength="3" onKeyUp="Nextfocus(this.value.length,this.name,this);" style="width:54px;"></span></span> -
								<span class="input_txt"><span><input type="text" name="officetel2" id="officetel2" class="txt" value="<c:out value='${vendor.officetel2}'/>"  maxlength="4" onKeyUp="Nextfocus(this.value.length,this.name,this);" style="width:54px;"></span></span> -
								<span class="input_txt"><span><input type="text" name="officetel3" id="officetel3" class="txt" value="<c:out value='${vendor.officetel3}'/>"  maxlength="4" onKeyUp="Nextfocus(this.value.length,this.name,this);" style="width:54px;"></span></span>
							</div>
						</td>
					</tr>
					<tr>
						<th><p class="check"><spring:message code='text.consult.field.corpnEmail'/></p></th>
						<td>
							<div class="td_p">
								<span class="input_txt"><span><input type="text" class="txt" name="email1" id="email1" value="<c:out value='${vendor.email1}'/>" style="width:90px;"></span></span> @
								<span class="input_txt"><span><input type="text" class="txt" name="email2" id="email2" value="<c:out value='${vendor.email2}'/>" style="width:110px;"></span></span>
								<!--
								<select id="" name="" style="width:150px;">
									<option value="">선택하세요</option>
								</select>
								-->
							</div>
						</td>
						<th><p>FAX </p></th>
						<td>
							<div class="td_p">
								<span class="input_txt"><span><input type="text" name="fax1"  id="fax1"  maxlength="3" class="txt" value="<c:out value='${vendor.fax1}'/>" onKeyUp="Nextfocus(this.value.length,this.name,this);" style="width:54px;"></span></span> -
								<span class="input_txt"><span><input type="text" name="fax2"  id="fax2"  maxlength="4" class="txt" value="<c:out value='${vendor.fax2}'/>" onKeyUp="Nextfocus(this.value.length,this.name,this);" style="width:54px;"></span></span> -
								<span class="input_txt"><span><input type="text" name="fax3"  id="fax3"  maxlength="4" class="txt" value="<c:out value='${vendor.fax3}'/>" onKeyUp="Nextfocus(this.value.length,this.name,this);" style="width:54px;"></span></span>
							</div>
						</td>
					</tr>
					<tr>
						<th rowspan="2"><p class="check"><spring:message code='text.consult.field.addr'/></p></th>
						<td>
							<div class="td_p">
								<span class="input_txt"><span><input type="text" name="zipNo1" id="zipNo1" class="txt" value="<c:out value='${vendor.zipNo1}'/>" style="width:55px;" readonly></span></span> -
								<span class="input_txt"><span><input type="text" name="zipNo2" id="zipNo2" class="txt" value="<c:out value='${vendor.zipNo2}'/>" style="width:55px;" readonly></span></span>
								
								<span class="btn_gray btn_g_td_ls"><span class="t_malgun"><a href="javascript:openZipCodeNew();"><spring:message code='button.common.zipcode'/></a></span></span>
								
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<div class="td_p">
								<span class="input_txt"><span><input type="text" name="supplyAddr1" id="supplyAddr1" class="txt" value="<c:out value='${vendor.supplyAddr1 }'/>" style="width:300px;" title="<spring:message code='text.consult.field.searchZip'/>" maxlength="10" readonly ></span></span>
								<span class="input_txt"><span><input type="text" name="supplyAddr2" id="supplyAddr2" class="txt" value="<c:out value='${vendor.supplyAddr2 }'/>" maxlength="20"  style="width:221px;" ></span></span>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			</div>
			<!-- //기본정보 -->

			<!-- 담당자 정보 -->
			<%-- <div class="s_title mt20">
				<h2>담당자 정보</h2>
			</div>

			<div class="tb_form_comm">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption>담당자 정보 입력 화면</caption>
				<colgroup><col width="130px"><col width="300px"><col width="130px"><col width="*"></colgroup>
				<tbody>
					<tr>
						<th><p class="check">이름</p></th>
						<td>
							<div class="td_p">
								<span class="input_txt"><span><input type="text" name="utakmanNm" class="txt" value="${vendor.utakmanNm}" style="width:232px;"></span></span>
							</div>
						</td>
						<th><p></p></th>
						<td><div class="td_p"></div></td>
					</tr>
					<tr>
						<th><p class="check">핸드폰</p></th>
						<td>
							<div class="td_p">
								<select id="cell1" name="cell1" style="width:78px;">
									<option value="010">010</option>
									<option value="011">011</option>
									<option value="016">016</option>
									<option value="017">017</option>
									<option value="019">019</option>
									<option value="007">070</option>									
								</select> -
								
								<!--<span class="input_txt"><span><input type="text" name="cell1" class="txt" value="${vendor.cell1}" maxlength="3" style="width:52px;" onKeyUp="Nextfocus(this.value.length,this.name,this);"></span></span> -
								-->
								<span class="input_txt"><span><input type="text" name="cell2" class="txt" value="${vendor.cell2}" maxlength="4" style="width:52px;" onKeyUp="Nextfocus(this.value.length,this.name,this);"></span></span> -
								<span class="input_txt"><span><input type="text" name="cell3" class="txt" value="${vendor.cell3}" maxlength="4" style="width:52px;" onKeyUp="Nextfocus(this.value.length,this.name,this);"></span></span>
							</div>
						</td>
						<th><p>사무실</p></th>
						<td>
							<div class="td_p">
								<span class="input_txt"><span><input type="text" name="tel1" value="${vendor.tel1}" class="txt"  style="width:54px;" maxlength="3" onKeyUp="Nextfocus(this.value.length,this.name,this);"></span></span> -
								<span class="input_txt"><span><input type="text" name="tel2" value="${vendor.tel2}" class="txt"  style="width:54px;" maxlength="4" onKeyUp="Nextfocus(this.value.length,this.name,this);"></span></span> -
								<span class="input_txt"><span><input type="text" name="tel3" value="${vendor.tel3}"  class="txt"  style="width:54px;" maxlength="4" onKeyUp="Nextfocus(this.value.length,this.name,this);"></span></span>
							</div>
						</td>
					</tr>
					<tr>
						<th><p class="check">E-mail</p></th>
						<td colspan="3">
							<div class="td_p">
								<span class="input_txt"><span><input type="text" class="txt" name="email1" value="${vendor.email1}" style="width:130px;"></span></span> @
								<span class="input_txt"><span><input type="text" class="txt" name="email2" value="${vendor.email2}" style="width:130px;"></span></span>
								<!--
								<select id="" name="" style="width:150px;">
									<option value="">선택하세요</option>
								</select>
								-->
							</div>
						</td>
					</tr>
					<tr>
						<th><p>홈페이지</p></th>
						<td colspan="3">
							<div class="td_p">
								<span class="t_12 t_gray">http://</span>
								<span class="input_txt"><span><input type="text" name="homePageAddress" class="txt" value="${vendor.homePageAddress }" maxlength="200" style="width:416px;"></span></span>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			</div> --%>
			<!-- //담당자 정보 -->

			<ul class="list_star">
				<li><spring:message code='text.consult.field.step1Info1'/></li>
				<li><spring:message code='text.consult.field.step1Info2'/></li>
				<li><spring:message code='text.consult.field.step1Info3'/></li>
			</ul>

			<!-- button -->
			<div class="btn_c_wrap mt25">
				<span class="btn_red"><span><a href="#" onclick="submitStep1Form();"><spring:message code='button.common.confirm'/></a></span></span>
			</div>
			<!--// button -->

		</div>

		

	</div>

</div>

</form>
</body>
</html>

