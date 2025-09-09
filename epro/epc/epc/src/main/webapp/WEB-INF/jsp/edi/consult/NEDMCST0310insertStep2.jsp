<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="/css/base.css" type="text/css">
<title><spring:message code='text.consult.field.lotteVendorConsult'/></title>



<script language="javascript">
		
		$(document).ready(function(){

			<c:if test="${not empty vendor.companyCharCode}">
			var companyCharCode = "<c:out value='${vendor.companyCharCode}'/>";
			$("input[name=chk_attribute]").each(function(index) {

			     var currentCompanyChar = this.value;				
			     if( companyCharCode.indexOf(currentCompanyChar) > -1 ) {
			        $(this).attr("checked", true);
			     }
			});
			</c:if>
			
			<c:if test="${not empty vendor.lotteAffTradeCode}">
			var lotteAffTradeCode = "<c:out value='${vendor.lotteAffTradeCode}'/>";
			$("input[name=chk_company]").each(function(index) {

			     var lotteAffCode = this.value;				
			     if( lotteAffTradeCode.indexOf(lotteAffCode) > -1 ) {
			        $(this).attr("checked", true);
			     }
			});
			</c:if>


			<c:if test="${not empty vendor.factoryType}">
				$('select[name=factoryType]').val("<c:out value='${vendor.factoryType}'/>");
			</c:if>
		



			
		});	

		function checkStep2Submit() {
			
			var companyCharacter = "", lotteAffTradeCode = "";
			
			if( $("input[name=chk_company]:checked").length > 0 ) 
			{
				$("input[name=chk_company]:checked").each(function(index) {
						lotteAffTradeCode += $(this).val()+";";
				});

				$("input[name=lotteAffTradeCode]").val(lotteAffTradeCode);
			}
			
			

			if( $("input[name=chk_attribute]:checked").length > 0 )	{		
				$("input[name=chk_attribute]:checked").each(function(index) {
					companyCharacter += $(this).val()+";";					
				});
				$("input[name=companyCharCode]").val(companyCharacter);
			}

			if( check_submit_3() ) {
				$("form[name=MyForm]").submit();
			}
			
			
		}
		
		
		
		function check_submit_3()
		{	
			
			if($("#MyForm #mainProduct").val().length==0) 
			{
				
				strMsg ="msg.main.product";         
				popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=mainProduct";
				openPopup(popUrl,'ID');   
			
				return false;
			}
			
			if($("#MyForm #yearSaleAmount").val().length==0) 
			{
				
				strMsg ="msg.annual.amount";         
				popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=yearSaleAmount";
				openPopup(popUrl,'ID');   
			
				return false;
			}
				
			if($("#MyForm #monthSaleAmount").val().length==0) 
			{
				
				strMsg ="msg.month.amount";         
				popUrl = "<c:url value='/epc/edi/consult/NEDMCST0310messagePopup.do?strMsg='/>" + strMsg + "&objFocus=monthSaleAmount";
				openPopup(popUrl,'ID');   
			
				return false;
			}
			
			/*
			var other_str_cd_1; // = MyForm.other_str_cd[0].value;
			var other_str_cd_2; // = MyForm.other_str_cd[1].value;
			var other_str_cd_3; // = MyForm.other_str_cd[2].value;
			var other_str_cd_4; // = MyForm.other_str_cd[3].value;
			
			var check_null = 0;
			for(i=0; i<3; i++)
			{		
				alert(MyForm.other_str_cd[].selected.value);
			}
			
			alert(check_null);
			
			
			if((other_str_cd_1 != '') || (other_str_cd_2 != '') || (other_str_cd_3 != '') || (other_str_cd_4 != ''))
			{
				if((other_str_cd_1 = other_str_cd_2) || (other_str_cd_2 = other_str_cd_3) || (other_str_cd_3 = other_str_cd_4) || (other_str_cd_1 = other_str_cd_4))
				{
					alert('같다!! 고치라!');
					return false;
				}
			}	
			*/
			
			
			//돈관련 입력폼에서 ',' 제거
			var capitalAmount  = MyForm.capitalAmount.value.replace(/,/g, '');
			MyForm.capitalAmount.value = capitalAmount;
			
			var yearSaleAmount = MyForm.yearSaleAmount.value.replace(/,/g, '');
			MyForm.yearSaleAmount.value = yearSaleAmount;
			
			var monthSaleAmount = MyForm.monthSaleAmount.value.replace(/,/g, '');
			MyForm.monthSaleAmount.value = monthSaleAmount;
			
			return true;
			//document.MyForm.submit();

		}
		
		
		</script>

</head>



<body>
<form name="MyForm" id="MyForm" action="<c:url value='/epc/edi/consult/NEDMCST0310insertStep2.do'/>" method="post">

<input type="hidden" name="companyCharCode" value="<c:out value='${vendor.companyCharCode}'/>"/>
<input type="hidden" name="lotteAffTradeCode" value="<c:out value='${vendor.lotteAffTradeCode}'/>" />
<input type="hidden" name="bmanNo" value="<c:out value='${vendorSession.vendor.bmanNo}'/>" />

<div id="wrap">

	<div id="con_wrap">
		

		<div class="con_area">

		
			<div class="step_wrap">
			
			<c:if test="${ vendorSession.vendor.chgStatusCd == '0' }"> 
				<ol>
					<li><a href="#" onclick="javascript:go_step('1');"><img src="/images/epc/edi/consult/step-base-form.gif" alt="<spring:message code='text.consult.field.step-base-form'/>"></a></li>
					<li class="on"><a href="#"><img src="/images/epc/edi/consult/step-sales-form-on.gif" alt="<spring:message code='text.consult.field.step-sales-form'/>"></a></li>
					<li><img src="/images/epc/edi/consult/step-product-form.gif" alt="<spring:message code='text.consult.field.step-product-form'/>"></a></li>
					<li><img src="/images/epc/edi/consult/step-counsel-form.gif" alt="<spring:message code='text.consult.field.step-counsel-form'/>"></a></li>
				</ol>
			</c:if>
			
			<c:if test="${ vendorSession.vendor.chgStatusCd != '0' }"> 
				<ol>
					<li><a href="#" onclick="javascript:go_step('1');"><img src="/images/epc/edi/consult/step-base-form.gif" alt="<spring:message code='text.consult.field.step-base-form'/>"></a></li>
					<li class="on"><a href="#"><img src="/images/epc/edi/consult/step-sales-form-on.gif" alt="<spring:message code='text.consult.field.step-sales-form'/>"></a></li>
					<li><a href="#" onclick="javascript:go_step('4');"><img src="/images/epc/edi/consult/step-product-form.gif" alt="<spring:message code='text.consult.field.step-product-form'/>"></a></li>
					<li><a href="#" onclick="javascript:go_step('5');"><img src="/images/epc/edi/consult/step-counsel-form.gif" alt="<spring:message code='text.consult.field.step-counsel-form'/>"></a></li>
				</ol>
			</c:if>
			</div>

			<!-- title -->
			<div class="s_title">
				<h2><spring:message code='text.consult.field.salesInfo'/></h2>
			</div>

			<div class="t_txt_wrap">
				<div class="ex_right"><p><spring:message code='text.consult.field.reqSymbol'/></p></div>
			</div>
			<div class="tb_form_comm">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption><spring:message code='text.consult.field.step-sales-form'/></caption>
				<colgroup><col width="130px"><col width="156px"><col width="130px"><col width="156px"><col width="130px"><col width="*"></colgroup>
				<tbody>
					<tr>
						<th width="130px"><p class="check"><spring:message code='text.consult.field.mainProduct'/></p></th>
						<td width="156px">
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="mainProduct" id="mainProduct" class="txt" value="<c:out value='${vendor.mainProduct }'/>"  maxlength="20" style="width:106px;"></span></span>
							</p>
						</td>
						<th width="130px"><p class="check"><spring:message code='text.consult.field.yearSaleAmount'/></p></th>
						<td width="156px">
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="yearSaleAmount" id="yearSaleAmount" class="txt" value="<c:out value='${vendor.yearSaleAmount }'/>" maxlength="7" onkeyup="displayComma(this, this.value);" style="width:76px;"></span></span> <spring:message code='text.consult.field.1million'/>
							</p>
						</td>
						<th width="130px"><p class="check"><spring:message code='text.consult.field.monthSaleAmount'/></p></th>
						<td width="*">
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="monthSaleAmount" id="monthSaleAmount" class="txt" value="<c:out value='${vendor.monthSaleAmount }'/>" maxlength="7" onkeyup="displayComma(this, this.value);" style="width:76px;"></span></span> <spring:message code='text.consult.field.1million'/>
							</p>
						</td>
					</tr>
					<tr>
						<th><p><spring:message code='text.consult.field.foundationYear'/></p></th>
						<td>
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="foundationYear" id="foundationYear" class="txt" value="<c:out value='${vendor.foundationYear }'/>" maxlength="4" onblur="len_chk(this)" style="width:76px;" /></span></span> <spring:message code='text.consult.field.year'/>
							</p>
						</td>
						<th><p><spring:message code='text.consult.field.capitalAmount'/></p></th>
						<td>
							<p class="td_p">
								<span class="input_txt"><span><input type="text"  name="capitalAmount" id="capitalAmount" class="txt" value="<c:out value='${vendor.capitalAmount }'/>" maxlength="7" onkeyup="displayComma(this, this.value);" style="width:76px;"></span></span> <spring:message code='text.consult.field.1million'/>
							</p>
						</td>
						<th><p><spring:message code='text.consult.field.factoryType'/></p></th>
						<td>
							<div class="td_p">
								<!-- select -->
								<select id="Select4" name="factoryType" style="width:129px;">
									<option selected value="1"><spring:message code='text.consult.field.factoryType1'/></option>
									<option value="2"><spring:message code='text.consult.field.factoryType2'/></option>
									<option value="3"><spring:message code='text.consult.field.factoryType3'/></option>
								</select>
								<!--// select -->
							</div>
						</td>
					</tr>
					<tr>
						<th><p><spring:message code='text.consult.field.chkAttribute'/></p></th>
						<td colspan="5">
							<div class="td_p clearfloat">
								<div class="fl_left">
									<input id="company1" type="checkbox" name="chk_attribute" value="jejo" class="chk" checked><label class="label mr15" for="company1"><spring:message code='text.consult.field.chkAttributeJejo'/></label>
									<input id="company2" type="checkbox" name="chk_attribute" value="jicsuip" class="chk"><label class="label mr15" for="company2"><spring:message code='text.consult.field.chkAttributeJicsuip'/></label>
									<input id="company3" type="checkbox" name="chk_attribute" value="chongpan" class="chk"><label class="label mr15" for="company3"><spring:message code='text.consult.field.chkAttributeChongpan'/></label>
									<input id="company4" type="checkbox" name="chk_attribute" value="broker" class="chk"><label class="label mr15" for="company4"><spring:message code='text.consult.field.chkAttributeBroker'/></label>
									<input id="company5" type="checkbox" name="chk_attribute" value="sanji" class="chk"><label class="label mr15" for="company5"><spring:message code='text.consult.field.chkAttributeSanji'/></label>
									<input id="company6" type="checkbox" name="chk_attribute" value="imdae" class="chk"><label class="label" for="company6"><spring:message code='text.consult.field.chkAttributeImdae'/></label>
								</div>
								<div class="fl_right mr10"><spring:message code='text.consult.field.chkAttributeEtc'/> <span class="input_txt ml5"><span><input type="text" name="companyDescription" id="companyDescription" class="txt" value="<c:out value='${vendor.companyDescription }'/>"  maxlength="40" style="width:141px;"></span></span></div>
							</div>
						</td>
					</tr>
				</tbody>
				</table>
			</div>

			<!-- title -->
			<div class="s_title mt20">
				<h2><spring:message code='text.consult.field.companyDescription'/></h2>
			</div>
			<div class="tb_form_comm">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption><spring:message code='text.consult.field.companyDescription'/></caption>
				<colgroup><col width="130px"><col width="156px"><col width="130px"><col width="156px"><col width="130px"><col width="156px"></colgroup>
				<tbody>
					<tr>
						<th width="130px"><p><spring:message code='text.consult.field.smIndustryEntpNm'/></p></th>
						<td width="156px" colspan="5">
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="smIndustryEntpNm" id="smIndustryEntpNm" class="txt" value="<c:out value='${vendor.smIndustryEntpNm }'/>"  maxlength="60" style="width:522px;"></span></span>
							</p>
						</td>
					</tr>
					<tr>
						<th width="130px"><p><spring:message code='text.consult.field.smIndustryEntpProdNm'/></p></th>
						<td width="156px" colspan="3">
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="smIndustryEntpProdNm" id="smIndustryEntpProdNm" class="txt" value="<c:out value='${vendor.smIndustryEntpProdNm }'/>" maxlength="40" style="width:396px;"></span></span>
							</p>
						</td>
						<th width="130px"><p ><spring:message code='text.consult.field.smIndustryEntpAmt'/></p></th>
						<td width="156px">
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="smIndustryEntpAmt" id="smIndustryEntpAmt" class="txt" value="<c:out value='${vendor.smIndustryEntpAmt }'/>" maxlength="9" onkeyup="displayComma(this, this.value);" style="width:76px;"></span></span> <spring:message code='text.consult.field.1million'/>
							</p>
						</td>
					</tr>
				</tbody>
				</table>
			</div>
				<!-- <colgroup><col width="157px"><col width="91px"><col width="211px"><col width="97px"><col width="97px"><col width="*"></colgroup>
				<thead>
					<tr>
						<th width="157px" class="line">업체명</th>
						<th width="91px" class="line">입점 점포수</th>
						<th width="211px" class="line">평균 M/G</th>
						<th width="97px" class="line">물류비</th>
						<th width="97px" class="line">장려금</th>
						<th width="*">거래형태</th>
					</tr>
				</thead> -->
				
				<%-- <tbody>
				
				
				<c:if test="${fn:length(saleList) == 0}">
					<tr>
						<td class="t_left">
							<div class="td_p">
								<select id="Select5" name="other_str_cd" style="width:129px;">
									<option value="">전체</option>
									<c:forEach  var="ediCommonCode" items="${otherStoreList}">
										<option value='${ediCommonCode.orgCode}' <c:if test="${ ediCommonCode.orgCode == sale.otherStoreCode }"> selected </c:if> >${ediCommonCode.orgName}</option>
									</c:forEach>															
								</select>
							</div>
						</td>
						<td class="t_center">
							<span class="input_txt"><span><input type="text" name="ent_str_cnt"  maxlength="4" class="txt" value=""  onkeyup="return inputNumOnly(this);" style="width:40px;"></span></span>
						</td>
						<td class="t_center">
							정상 <span class="input_txt"><span><input type="text" name="mg1" class="txt" value="" style="width:20px;"  onkeyup="return inputNumOnly(this);" ></span></span> % /
							행사 <span class="input_txt"><span><input type="text" name="mg2" class="txt" maxlength="3" value="" style="width:20px;"  onkeyup="return inputNumOnly(this);" ></span></span> %
						</td>
						<td class="t_center">
							<span class="input_txt"><span><input type="text" name="pcost" class="txt" value=""  maxlength="3" style="width:30px;" onkeyup="return inputNumOnly(this);"></span></span> %
						</td>
						<td class="t_center">
							<span class="input_txt"><span><input type="text" name="subAmount" class="txt" value=""  maxlength="3" style="width:30px;" onkeyup="return inputNumOnly(this);"></span></span> %
						</td>
						<td class="t_center">
							<span class="input_txt"><span><input type="text" name="trd_typ_fg" class="txt" value=""  maxlength="30" style="width:158px;"></span></span>
						</td>
					</tr>
					
					
					
					
					
					
					<tr>
						<td class="t_left">
							<div class="td_p">
								<select id="Select5" name="other_str_cd" style="width:129px;">
									<option value="">전체</option>
									<c:forEach  var="ediCommonCode" items="${otherStoreList}">
										<option value='${ediCommonCode.orgCode}'
											<c:if test="${ ediCommonCode.orgCode == sale.otherStoreCode }">
												selected
											</c:if>
									   >${ediCommonCode.orgName}</option>
									</c:forEach>															
								</select>
							</div>
						</td>
						<td class="t_center">
							<span class="input_txt"><span><input type="text" name="ent_str_cnt"  maxlength="4" class="txt" value=""  onkeyup="return inputNumOnly(this);" style="width:40px;"></span></span>
						</td>
						<td class="t_center">
							정상 <span class="input_txt"><span><input type="text" name="mg1" class="txt" value="" style="width:20px;"  onkeyup="return inputNumOnly(this);" ></span></span> % /
							행사 <span class="input_txt"><span><input type="text" name="mg2" class="txt" maxlength="3" value="" style="width:20px;"  onkeyup="return inputNumOnly(this);" ></span></span> %
						</td>
						<td class="t_center">
							<span class="input_txt"><span><input type="text" name="pcost" class="txt" value=""  maxlength="3" style="width:30px;" onkeyup="return inputNumOnly(this);"></span></span> %
						</td>
						<td class="t_center">
							<span class="input_txt"><span><input type="text" name="subAmount" class="txt" value=""  maxlength="3" style="width:30px;" onkeyup="return inputNumOnly(this);"></span></span> %
						</td>
						<td class="t_center">
							<span class="input_txt"><span><input type="text" name="trd_typ_fg" class="txt" value=""  maxlength="30" style="width:158px;"></span></span>
						</td>
					</tr>
					<tr>
						<td class="t_left">
							<div class="td_p">
								<select id="Select5" name="other_str_cd" style="width:129px;">
									<option value="">전체</option>
									<c:forEach  var="ediCommonCode" items="${otherStoreList}">
										<option value='${ediCommonCode.orgCode}'
											<c:if test="${ ediCommonCode.orgCode == sale.otherStoreCode }">
												selected
											</c:if>
									   >${ediCommonCode.orgName}</option>
									</c:forEach>															
								</select>
							</div>
						</td>
						<td class="t_center">
							<span class="input_txt"><span><input type="text" name="ent_str_cnt"  maxlength="4" class="txt" value=""  onkeyup="return inputNumOnly(this);" style="width:40px;"></span></span>
						</td>
						<td class="t_center">
							정상 <span class="input_txt"><span><input type="text" name="mg1" class="txt" value="" style="width:20px;"  onkeyup="return inputNumOnly(this);" ></span></span> % /
							행사 <span class="input_txt"><span><input type="text" name="mg2" class="txt" maxlength="3" value="" style="width:20px;"  onkeyup="return inputNumOnly(this);" ></span></span> %
						</td>
						<td class="t_center">
							<span class="input_txt"><span><input type="text" name="pcost" class="txt" value=""  maxlength="3" style="width:30px;" onkeyup="return inputNumOnly(this);"></span></span> %
						</td>
						<td class="t_center">
							<span class="input_txt"><span><input type="text" name="subAmount" class="txt" value=""  maxlength="3" style="width:30px;" onkeyup="return inputNumOnly(this);"></span></span> %
						</td>
						<td class="t_center">
							<span class="input_txt"><span><input type="text" name="trd_typ_fg" class="txt" value=""  maxlength="30" style="width:158px;"></span></span>
						</td>
					</tr>
					</c:if>
					
					<c:if test="${not empty saleList }">
						<c:set var="listNum"  value="0" />
						<c:forEach  var="sale" items="${saleList}" >
						<tr>
							<td class="t_left">
								<div class="td_p">
									<select id="Select5" name="other_str_cd" style="width:129px;">
										<option value="">전체</option>
										<c:forEach  var="ediCommonCode" items="${otherStoreList}">
											<option value='${ediCommonCode.orgCode}'
												<c:if test="${ ediCommonCode.orgCode == sale.otherStoreCode }">
													selected
												</c:if>
										   >${ediCommonCode.orgName}</option>
										</c:forEach>															
									</select>
								</div>
							</td>
							<td class="t_center">
								<span class="input_txt"><span><input type="text" name="ent_str_cnt"  maxlength="4" class="txt" value="${sale.enteredStoreCount }"  onkeyup="return inputNumOnly(this);" style="width:40px;"></span></span>
							</td>
							<td class="t_center">
								정상 <span class="input_txt"><span><input type="text" name="mg1" class="txt" value="${sale.marginRate1 }" style="width:20px;"  onkeyup="return inputNumOnly(this);" ></span></span> % /
								행사 <span class="input_txt"><span><input type="text" name="mg2" class="txt" maxlength="3" value="${sale.marginRate2 }" style="width:20px;"  onkeyup="return inputNumOnly(this);" ></span></span> %
							</td>
							<td class="t_center">
								<span class="input_txt"><span><input type="text" name="pcost" class="txt" value="${sale.pcost }"  maxlength="3" style="width:30px;" onkeyup="return inputNumOnly(this);"></span></span> %
							</td>		
							<td class="t_center">
								<span class="input_txt"><span><input type="text" name="subAmount" class="txt" value="${sale.subAmount }"  maxlength="3" style="width:30px;" onkeyup="return inputNumOnly(this);"></span></span> %
							</td>
							<td class="t_center">
								<span class="input_txt"><span><input type="text" name="trd_typ_fg" class="txt" value="${sale.tradeTypeContent }"  maxlength="30" style="width:158px;"></span></span>
							</td>
						</tr>
						<c:set var="listNum" value="${listNum + 1 }" />
						</c:forEach>
						
						<c:if test="${listNum eq 1 }">
							<tr>
								<td class="t_left">
									<div class="td_p">
										<select id="Select5" name="other_str_cd" style="width:129px;">
											<option value="">전체</option>
											<c:forEach  var="ediCommonCode" items="${otherStoreList}">
												<option value='${ediCommonCode.orgCode}'
													<c:if test="${ ediCommonCode.orgCode == sale.otherStoreCode }">
														selected
													</c:if>
											   >${ediCommonCode.orgName}</option>
											</c:forEach>															
										</select>
									</div>
								</td>
								<td class="t_center">
									<span class="input_txt"><span><input type="text" name="ent_str_cnt"  maxlength="4" class="txt" value=""  onkeyup="return inputNumOnly(this);" style="width:40px;"></span></span>
								</td>
								<td class="t_center">
									정상 <span class="input_txt"><span><input type="text" name="mg1" class="txt" value="" style="width:20px;"  onkeyup="return inputNumOnly(this);" ></span></span> % /
									행사 <span class="input_txt"><span><input type="text" name="mg2" class="txt" maxlength="3" value="" style="width:20px;"  onkeyup="return inputNumOnly(this);" ></span></span> %
								</td>
								<td class="t_center">
									<span class="input_txt"><span><input type="text" name="pcost" class="txt" value=""  maxlength="3" style="width:30px;" onkeyup="return inputNumOnly(this);"></span></span> %
								</td>
								<td class="t_center">
									<span class="input_txt"><span><input type="text" name="subAmount" class="txt" value=""  maxlength="3" style="width:30px;" onkeyup="return inputNumOnly(this);"></span></span> %
								</td>
								<td class="t_center">
									<span class="input_txt"><span><input type="text" name="trd_typ_fg" class="txt" value=""  maxlength="30" style="width:158px;"></span></span>
								</td>
							</tr>
							<tr>
								<td class="t_left">
									<div class="td_p">
										<select id="Select5" name="other_str_cd" style="width:129px;">
											<option value="">전체</option>
											<c:forEach  var="ediCommonCode" items="${otherStoreList}">
												<option value='${ediCommonCode.orgCode}'
													<c:if test="${ ediCommonCode.orgCode == sale.otherStoreCode }">
														selected
													</c:if>
											   >${ediCommonCode.orgName}</option>
											</c:forEach>															
										</select>
									</div>
								</td>
								<td class="t_center">
									<span class="input_txt"><span><input type="text" name="ent_str_cnt"  maxlength="4" class="txt" value=""  onkeyup="return inputNumOnly(this);" style="width:40px;"></span></span>
								</td>
								<td class="t_center">
									정상 <span class="input_txt"><span><input type="text" name="mg1" class="txt" value="" style="width:20px;"  onkeyup="return inputNumOnly(this);" ></span></span> % /
									행사 <span class="input_txt"><span><input type="text" name="mg2" class="txt" maxlength="3" value="" style="width:20px;"  onkeyup="return inputNumOnly(this);" ></span></span> %
								</td>
								<td class="t_center">
									<span class="input_txt"><span><input type="text" name="pcost" class="txt" value=""  maxlength="3" style="width:30px;" onkeyup="return inputNumOnly(this);"></span></span> %
								</td>
								<td class="t_center">
									<span class="input_txt"><span><input type="text" name="subAmount" class="txt" value=""  maxlength="3" style="width:30px;" onkeyup="return inputNumOnly(this);"></span></span> %
								</td>
								<td class="t_center">
									<span class="input_txt"><span><input type="text" name="trd_typ_fg" class="txt" value=""  maxlength="30" style="width:158px;"></span></span>
								</td>
							</tr>
						</c:if>
						
						<c:if test="${listNum eq 2 }">
							<tr>
								<td class="t_left">
									<div class="td_p">
										<select id="Select5" name="other_str_cd" style="width:129px;">
											<option value="">전체</option>
											<c:forEach  var="ediCommonCode" items="${otherStoreList}">
												<option value='${ediCommonCode.orgCode}'
													<c:if test="${ ediCommonCode.orgCode == sale.otherStoreCode }">
														selected
													</c:if>
											   >${ediCommonCode.orgName}</option>
											</c:forEach>															
										</select>
									</div>
								</td>
								<td class="t_center">
									<span class="input_txt"><span><input type="text" name="ent_str_cnt"  maxlength="4" class="txt" value=""  onkeyup="return inputNumOnly(this);" style="width:40px;"></span></span>
								</td>
								<td class="t_center">
									정상 <span class="input_txt"><span><input type="text" name="mg1" class="txt" value="" style="width:20px;"  onkeyup="return inputNumOnly(this);" ></span></span> % /
									행사 <span class="input_txt"><span><input type="text" name="mg2" class="txt" maxlength="3" value="" style="width:20px;"  onkeyup="return inputNumOnly(this);" ></span></span> %
								</td>
								<td class="t_center">
									<span class="input_txt"><span><input type="text" name="pcost" class="txt" value=""  maxlength="3" style="width:30px;" onkeyup="return inputNumOnly(this);"></span></span> %
								</td>
								<td class="t_center">
									<span class="input_txt"><span><input type="text" name="subAmount" class="txt" value=""  maxlength="3" style="width:30px;" onkeyup="return inputNumOnly(this);"></span></span> %
								</td>
								<td class="t_center">
									<span class="input_txt"><span><input type="text" name="trd_typ_fg" class="txt" value=""  maxlength="30" style="width:158px;"></span></span>
								</td>
							</tr>
						</c:if>
						
					</c:if>
					
				</tbody> --%>
				
				
				
			
				
				
				
				
				
				<!-- </table>
			</div> -->
			<%-- <div class="tb_form_comm_btm">
				<ul class="clearfloat">
					<li class="tit"><p class="t_block">롯데계열거래여부</p></li>
					<li class="con">
						<div class="fl_left mt2">
							<input id="company1" type="checkbox" name="chk_company" value="dept" class="chk"><label class="label mr15" for="company1">롯데백화점</label>
							<input id="company2" type="checkbox" name="chk_company" value="super" class="chk"><label class="label mr15" for="company2">롯데슈퍼</label>
							<input id="company3" type="checkbox" name="chk_company" value="k7" class="chk"><label class="label" for="company3">K-7</label>
						</div>
						<div class="fl_right">기타 <span class="input_txt ml8"><span><input type="text" name="lotteAffTradeContent" class="txt" value="${vendor.lotteAffTradeContent }" maxlength="40"  style="width:397px;"></span></span></div>
					</li>
				</ul>
			</div> --%>

			<ul class="list_star">
				<li><spring:message code='text.consult.field.step2Info1'/></li>
				<!-- <li>평균 M/G는 상품별 마진율이 아니라 전체 마진을 의미하며 정상일때와 행사일때의 마진율을 입력하시면 됩니다.</li>
				<li>거래형태는 직매입, 특정, 임대 또는 단기 행사등을 입력하시면 됩니다.</li> -->
			</ul>

			<!-- button -->
			<div class="btn_c_wrap mt30">
				<span class="btn_red"><span><a href="#" onclick="checkStep2Submit();"><spring:message code='button.common.confirm'/></a></span></span>
			</div>
			<!--// button -->

		</div>

		
	</div>

</div>

<input type="hidden" name="bman_no" id="bman_no">	
</form>
</body>
</html>