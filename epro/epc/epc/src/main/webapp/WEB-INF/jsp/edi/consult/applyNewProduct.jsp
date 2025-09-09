<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>롯데마트 입점상담</title>

 
<script language="JavaScript">

		$(document).ready(function(){
			<c:if test="${not empty param.orgCd}">
				//not empty
			
			</c:if>

			<c:if test="${empty param.orgCd}">
				//empty
				$('select[name=orgCd]').val('${vendor.orgCd}');
			</c:if>
			
			<c:if test="${not empty vendor.corpnDivnCd}">
				$('select[name=corpnDivnCd]').val('${vendor.corpnDivnCd}');
			</c:if>
			
			<c:if test="${not empty vendor.l1Cd}">
				$('select[name=L1Cd]').val('${vendor.l1Cd}');
			</c:if>
			
			var l1CdText = $("select[name=L1Cd] option:selected").text();
			if(l1CdText==''){
				$('select[name=L1Cd]').val('');
			}
			
			<c:if test="${not empty vendor.cell1}">
				$('select[name=cell1]').val('${vendor.cell1}');
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
			if(l1Cd==''){
		
				alert("분류 카테고리가 입력이 안되었습니다.");
				return;
				
			}
			//document.MyForm.tel2
			
		

			 
			$("form[name=MyForm]").submit();
		
			
		}
		
		
		function check_submit_1() 
		{	
			var MyForm = document.getElementsByName("MyForm")[0];
			
			
			
			
			if(MyForm.hndNm.value.length==0)
			{
				
				strMsg ="msg.no.company.name";         
				popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=hndNm";
				openPopup(popUrl,'에러');   
			
				return false;
			}
			if(MyForm.ceoNm.value.length==0)
			{
				
				strMsg ="msg.ceo.name";
				popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=ceoNm";
				openPopup(popUrl,'ID');   
				return false;
			}
			if(MyForm.businessKind.value.length==0)
			{
				
				strMsg ="msg.businessKind";         
				popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=businessKind";
				openPopup(popUrl,'ID');   
			
				return false;
			}		
			if(MyForm.businessType.value.length==0)
			{
				
				strMsg ="msg.businessType";         
				popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=businessType";
				openPopup(popUrl,'ID');   
			
				return false;
			}
			
			
			
	
			
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
			
			
			
			
			return true;
		}


		function validate(form){
	
			if(form.officetel2.value.length <3 ){
				alert("<spring:message code='msg.common.error.length'/>");
				form.officetel2.focus();
				return false;
			}

			if(form.officetel3.value.length <4 ){
				alert("<spring:message code='msg.common.error.length'/>");
				form.officetel1.focus();
				return false;
			}
			
			if(form.fax1.value != ""){
				if(form.fax2.value.length <3 ){
					alert("<spring:message code='msg.common.error.length'/>");
					form.fax2.focus();
					return false;
				}

				if(form.fax3.value.length <4 ){
					alert("<spring:message code='msg.common.error.length'/>");
					form.fax3.focus();
					return false;
				}
			}
			
	
			
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
					document.MyForm.corpnRsdtNo2.focus();
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
					document.MyForm.cell3.focus();
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


		function PopupWindow(pageName) {
			var cw=480;
			var ch=480;
			var sw=screen.availWidth;
			var sh=screen.availHeight;
			var px=Math.round((sw-cw)/2);
			var py=Math.round((sh-ch)/2);
			window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
		}



	
		function downloadConsultDocumnet(vendorBusinessNo) {
			window.open('${ctx}/edi/consult/step3DocumnetPopup.do?vendorBusinessNo=' + vendorBusinessNo , 'docwin', 'width=200, height=300, scrollbars=yes, resizable=yes');
		}
		
		function check_submit_1() 
		{	
			var MyForm = document.getElementsByName("MyForm")[0];
			
			if(MyForm.hndNm.value.length==0)
			{
				
				strMsg ="msg.no.company.name";         
				popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=hndNm";
				openPopup(popUrl,'에러');   
			
				return false;
			}
			if(MyForm.ceoNm.value.length==0)
			{
				
				strMsg ="msg.ceo.name";
				popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=ceoNm";
				openPopup(popUrl,'ID');   
				return false;
			}
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
			return true;
		}

		
		
		
		
		function checkApplySubmit() 
		{
			
			var form = document.MyForm;
			
			if( !check_submit_1() ) {
				return;
			}
			
	alert("폼 전송시작");			
	
	
	var email1 = $("input[name=email1]").val();
	var email2 = $("input[name=email2]").val();

	var email =  email1+"@"+email2;
	$("input[name=email]").val(email);
	
	
	
	
			if(check_submit_4()) {
				$("form[name=MyForm]").submit();
					//alert("폼 전송");
				}
	
			/* $("form[name=MyForm]").submit(); */

		}
		
		function check_submit_4()
		{ alert('2');
			var MyForm = document.getElementsByName("MyForm")[0];
			var resultFlag = true;
			var answeredCount1 =0;
			var notAnsweredCount1 = 0;
			var totalCount = 2;
			$("input.field_1").each(function(index) {
				 alert($(this).val());
				if( $(this).val() == '' ||
						$(this).val().length == 0) {
					notAnsweredCount1++;
				} else {					
					answeredCount1++;
				}	
			});
				
			if( answeredCount1 != totalCount && 
					notAnsweredCount1 != totalCount ) {
				alert("첫번째 항목을 모두 입력해주시기 바랍니다.");
				return false;
			}	 
				

			var answeredCount2 =0;
			var notAnsweredCount2 = 0;
			$("input.field_2").each(function(index) {
				 alert($(this).val());
				if( $(this).val() == '' ||	$(this).val().length == 0) {
					notAnsweredCount2++;
				} else {					
					answeredCount2++;
				}	
			});
				
			alert("answeredCount2:"+answeredCount2+" totalCount:"+totalCount);
			
			if( answeredCount2 != totalCount && 
					notAnsweredCount2 != totalCount ) {
				alert("두번째 항목을 모두 입력해주시기 바랍니다.");
				return false;
			}	 


			var answeredCount3 =0;
			var notAnsweredCount3 = 0;
			$("input.field_3").each(function(index) {
				if( $(this).val() == '' ||	$(this).val().length == 0) {
					notAnsweredCount3++;
				} else {					
					answeredCount3++;
				}	
			});
			
			alert("answeredCount3:"+answeredCount3+" totalCount:"+totalCount);
			
			if( answeredCount3 != totalCount  && notAnsweredCount3 != totalCount  ) {
				alert("세번째 항목을 모두 입력해주시기 바랍니다.");
				return false;
			}	 

			if( notAnsweredCount1 == 2 && notAnsweredCount2 == 2 && notAnsweredCount3 == 2 ) {
				alert("세 항목중 적어도 하나는 입력해야 합니다.");
				return false;
			}
// 			var buy_prc_cnt = MyForm.productCost.length;
				
// 			for(var i=0; i<buy_prc_cnt; i++)
// 			{
// 				var productCost = MyForm.productCost[i].value;
// 				MyForm.productCost[i].value = productCost.replace(/,/g, '');
				
// 				var productSalePrice = MyForm.productSalePrice[i].value;
// 				MyForm.productSalePrice[i].value = productSalePrice.replace(/,/g, '');
				
// 				var monthlyAverage = MyForm.monthlyAverage[i].value;
// 				MyForm.monthlyAverage[i].value = monthlyAverage.replace(/,/g, '');
// 			}
			
			return true;
		}	

		
		
		function img_view(imgurl, imgpath) {
			
			
			alert(imgurl);
			alert(imgpath);
			//alert("tt")

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
			        
			  window.open('${ctx}/edi/consult/step3ImagePopup.do?img=' + imgurl + '&imgpath=' + imgpath + '&h=' + h, 'imgwin', 'width=' + openWidth + ', height=' + openHeight + ', scrollbars=yes, resizable=yes');
			}	
		
		
		
		
		
		
		
		
</script>


</head>

<body>

<form name="MyForm" action="${ctx }/epc/edi/consult/insertApplyConfirm.do" method="post" enctype="multipart/form-data">

<input type="hidden" name="regDivnCd" value="${vendorSession.vendor.regDivnCd }" />
<input type="hidden" name="chgStatusCd" value="${vendorSession.vendor.chgStatusCd }" />
<input type="hidden" name="corpnRsdtNo" value="${vendor.corpnRsdtNo}" />
<input type="hidden" name="passwd" value="${vendorSession.vendor.passwd}" />

<input type="hidden" name="email" value="" />
<input type="hidden" name="zipNo" value="" />

<div id="wrap">

	<div id="con_wrap">
		

		<div class="con_area">

			

			<div class="step_wrap">
			
			</div>

			<!-- 기본정보 -->
			<div class="s_title">
				<h2>기본정보</h2>
			</div>

			<div class="t_txt_wrap">
				<div class="ex_right"><p>표시는 필수입력사항</p></div>
			</div>
			<div class="tb_form_comm">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption>기본정보 입력 화면</caption>
				<colgroup><col width="130px"><col width="300px"><col width="130px"><col width="*"></colgroup>
				<tbody>
					<tr>
						<th width="130px"><p class="check">상호명</p></th>
						<td width="300px">
							<div class="td_p">
								<span class="input_txt"><span><input  maxlength="15"  type="text" class="txt" name="hndNm" value="${vendor.hndNm}" style="width:232px;" onkeyup="javascript:cal_byte(this, '50', '상호명');"></span></span>
							</div>
						</td>
						<th width="130px"><p>사업자등록번호</p></th>
							<td width="300px"><div class="td_p"><span class="t_gray t_12"><input type="text" name="bmanNo" class="txt" value="${ vendorSession.vendor.bmanNo }"  ID="Text1" readonly style="width:232px;"></span></div></td>
					</tr>
					
					<tr>
						<th><p class="check">분류</p></th>
						<td>
							<div class="td_p">
														<select class="select" name="L1Cd" >
															<option value="">선택</option>
                       									 	<c:forEach items="${consultTeamList}" var="consultTeamList" begin="0">
                            									<option value="${consultTeamList.L1_CD}">${consultTeamList.teamNm}</option>
                        								 	</c:forEach>
                        								 </select>
								<input type="hidden" name="teamCd" value="${TeamcdData.TEAM_CD }"/>
							</div>
						</td>
				
						<th><p class="check">대표자명</p></th>
						<td><div class="td_p"><span class="input_txt"><span><input maxlength="15"  type="text" name="ceoNm" class="txt" value="${vendor.ceoNm}" style="width:232px;" onkeyup="javascript:cal_byte(this, '50', '대표자명');"></span></span></div></td>
						
					</tr>
					<tr>
					
						<th><p class="check">대표전화번호</p></th>
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
						<tr>
						<th><p class="check">주력품목(상품명)</p></th>
						<td><div class="td_p">						
						<span class="input_txt"><span><input type="text" name="mainProduct" class="txt" value="${vendor.mainProduct }"  maxlength="20" style="width:106px;"></span></span>
						</div>
					</td>
						</tr>
		
		
		
		</tr>
					
		

				<c:if test="${fn:length(vendorProductList) > 0 }"> 
					<c:forEach  var="vendorProduct" items="${vendorProductList}" varStatus="status">
					
					<tr>
					
						<td class="t_center num" rowspan="1"><span class="t_12">${vendorProduct.seq }<br>
						<c:if test="${ not empty vendorProduct.attachFileCode }">
							<a href="javascript:img_view('${vendorProduct.attachFileCode}', '${attachFileFolder}')" class="btn">이미지 보기</a>
						</c:if>
						</span></td>
						<input type="hidden" name="seq" value="${vendorProduct.seq }" ID="Hidden1">
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" name="productName" class="txt field_${vendorProduct.seq}" value="${vendorProduct.productName }" maxlength="20" style="width:130px;"></span></span>
						</td>		
					
						<td class="t_center no_b_line">
							<input type="hidden" name="uploadFile${status.count }" class="field_${vendorProduct.seq}" value="${vendorProduct.attachFileCode }"/>
							<input type="file"   name="attachFile${status.count }"  class="imageUpload" value=""  style="width:190px; height:18px;" >

						</td>
					</tr>
				
					
					</c:forEach>
				</c:if>
				
				
				
				<c:if test="${fn:length(vendorProductList) == 0 }"> 
					<tr>
						<td class="t_center num" rowspan="2"><span class="t_12">1</span></td>
						<input type="hidden" name="seq" value="1" ID="Hidden1">
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" name="productName" class="txt field_1" value="" maxlength="20" style="width:130px;"></span></span>
						</td>
					
						<td class="t_center no_b_line"> 
							<input type="hidden" name="uploadFile1" class="field_1" value=""/>
							<input type="file" name="attachFile1"  value="" class="imageUpload"  style="width:190px; height:18px;" >
					   </td>
					</tr>
				
				</c:if>
				
				<c:if test="${fn:length(vendorProductList) == 0   || fn:length(vendorProductList) == 1   }"> 
					<tr>
						<td class="t_center num" rowspan="2"><span class="t_12">2</span></td>
						<input type="hidden" name="seq" value="2" ID="Hidden1">
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" name="productName" class="txt field_2" value="" maxlength="20" style="width:130px;"></span></span>
						</td>
					
						<td class="t_center no_b_line"> 
						<input type="hidden" name="uploadFile2" value="" class="field_2" />
						<input type="file" name="attachFile2"  value="" class="imageUpload"  style="width:190px; height:18px;" >
					   </td>
					</tr>
				
				</c:if>	
					
				<c:if test="${ fn:length(vendorProductList) == 0 ||	fn:length(vendorProductList) == 1  || fn:length(vendorProductList) == 2 }">	
					<tr>
						<td class="t_center num" rowspan="2"><span class="t_12">3</span></td>
						<input type="hidden" name="seq" value="3" ID="Hidden1">
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" name="productName" class="txt field_3" value="" maxlength="20" style="width:130px;"></span></span>
						</td>
					
						<td class="t_center no_b_line"> 
							<input type="hidden" name="uploadFile3" class="field_3" value=""/>
							<input type="file" name="attachFile3"  value=""  class="imageUpload"    style="width:190px; height:18px;" >
					   </td>
					</tr>
				
				</c:if>
		
		<!-- 
		
				<c:if test="${fn:length(vendorProductList) > 0 }"> 
					<c:forEach  var="vendorProduct" items="${vendorProductList}" varStatus="status">	
					
					<tr>
					
					<th><p>${vendorProduct.seq }. 상품명...</th>		
					<td colspan="4">
						
						</span>
						<input type="hidden" name="seq" value="${vendorProduct.seq }" ID="Hidden1">						
							<span class="input_txt"><span><input type="text" name="productName" class="txt field_${vendorProduct.seq}" value="${vendorProduct.productName }" maxlength="20" style="width:130px;"></span></span>
											
							<input type="text"   name="uploadFile${status.count }" class="field_${vendorProduct.seq}" value="${vendorProduct.attachFileCode }"/>
							<input type="file"   name="attachFile${status.count }"  class="imageUpload" value=""  style="width:190px; height:18px;" >
						<c:if test="${ not empty vendorProduct.attachFileCode }">
							<a href="javascript:img_view('${vendorProduct.attachFileCode}', '${attachFileFolder}')" class="btn">이미지 보기 </a>
						
							
						</c:if>
						</td>
					</tr>	
					</c:forEach>
				</c:if>
				
				
				<c:if test="${fn:length(vendorProductList) == 0 }"> 
					<tr>
						<th><p class="check">1. 상품명</th>						
						<input type="hidden" name="seq" value="1" ID="Hidden1">
						<td>
							<span class="input_txt"><span><input type="text" name="productName" class="txt field_1" value="" maxlength="20" style="width:130px;"></span></span>
						</td>
						<td colspan="2"> 
							<input type="hidden" name="uploadFile1" class="field_1" value=""/>
							<input type="file" name="attachFile1"  value="" class="imageUpload"  style="width:190px; height:18px;" >
					   </td>
					</tr>				
				</c:if>
				
				<c:if test="${fn:length(vendorProductList) == 0   ||
									fn:length(vendorProductList) == 1   }"> 
					<tr>
					<th><p>2. 상품명</p></th>					
						<input type="hidden" name="seq" value="2" ID="Hidden1">
						<td>
							<span class="input_txt"><span><input type="text" name="productName" class="txt field_2" value="" maxlength="20" style="width:130px;"></span></span>
						</td>						
						<td colspan="2"> 
						<input type="hidden" name="uploadFile2" value="" class="field_2" />
						<input type="file" name="attachFile2"  value="" class="imageUpload"  style="width:190px; height:18px;" >
					  
					  
					  
					  
					  
					  
					  
					  
					   </td>
					</tr>				
				</c:if>	
					
				<c:if test="${ fn:length(vendorProductList) == 0 ||
					fn:length(vendorProductList) == 1  || fn:length(vendorProductList) == 2 }">	
					<tr>
						<th><p>3. 상품명</p></th>							
						<input type="hidden" name="seq" value="3" ID="Hidden1">
						<td>
							<span class="input_txt"><span><input type="text" name="productName" class="txt field_3" value="" maxlength="20" style="width:130px;"></span></span>
						</td>					
						<td colspan="2"> 
							<input type="hidden" name="uploadFile3" class="field_3" value=""/>
							<input type="file" name="attachFile3"  value=""  class="imageUpload"    style="width:190px; height:18px;" >
					   </td>
					</tr>				
				</c:if>
		
		 -->
		
		
		
		
			
			<tr>					
				<th><p class="check">첨부문서</p></th>		
				<td colspan="3">		
				<input type="hidden" name="vendorFile" value="${vendor.attachFileCode }"/> 
				<input type="file" name="vendorAttachFile" class="docUpload" value=""  style="width:200px;  height:18px;">111
				<c:if test="${not empty vendor.attachFileCode }"><span style="margin-left: 50px">${vendor.attachFileCode}</span>&nbsp;<a href="javascript:downloadConsultDocumnet('${vendor.bmanNo}')">다운로드</a>					
				</c:if>
				<ul class="list_star">
					<li>
						<font color="blue">
							<b>사업소개서 및 기타 참고서류</b>
						</font>
						<font color="red">
							(반드시 2M이하 용량 / PPT, PPTX, PDF파일로 올려주세요)
						</font>
					</li>
				</ul>
			</td>
						
			</tr>		
				
		
						
			
					
					
					
				</tbody>
			</table>
			</div>
			<!-- //기본정보 -->

			

			<ul class="list_star">
				<li>대표이메일을 등록하시면 결과를 이메일로 받아보실수 있습니다. 미 등록時 결과정보는 해당 페이지에서 확인하실수 있습니다.</li>
				<li>Vendor Pool을 통한 상담신청은 Vendor Pool의 기본정보를 사용합니다. 해당 정보를 수정할 수 있습니다.</li>
				<li>E-mail에서 hanmail.net 은 메일의 수신이 안될 경우가 있으니 다른 E-mail을 사용하시기 바랍니다. 해당 E-mail로 상담정보가 발송됩니다.</li>
			</ul>

			<!-- button -->
			<div class="btn_c_wrap mt25">
				<span class="btn_red"><span><a href="javascript:checkApplySubmit();"><spring:message code='button.common.confirm'/></a></span></span>
		</div>
			<!--// button -->

		</div>

		

	</div>

</div>

</form>
</body>
</html>