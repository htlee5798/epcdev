<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="/css/base.css" type="text/css">
<title>롯데마트 입점상담</title>



<script language="javascript">
		<!--
		$(document).ready(function(){

			<c:if test="${not empty vendor.companyCharCode}">
			var companyCharCode = "${vendor.companyCharCode}";
			$("input[name=chk_attribute]").each(function(index) {

			     var currentCompanyChar = this.value;				
			     if( companyCharCode.indexOf(currentCompanyChar) > -1 ) {
			        $(this).attr("checked", true);
			     }
			});
			</c:if>
			
			<c:if test="${not empty vendor.lotteAffTradeCode}">
			var lotteAffTradeCode = "${vendor.lotteAffTradeCode}";
			$("input[name=chk_company]").each(function(index) {

			     var lotteAffCode = this.value;				
			     if( lotteAffTradeCode.indexOf(lotteAffCode) > -1 ) {
			        $(this).attr("checked", true);
			     }
			});
			</c:if>


			<c:if test="${not empty vendor.factoryType}">
				$('select[name=factoryType]').val('${vendor.factoryType}');
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
			var MyForm = document.getElementsByName("MyForm")[0];
			
			if(MyForm.mainProduct.value.length==0)
			{
				
				strMsg ="msg.main.product";         
				popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=mainProduct";
				openPopup(popUrl,'ID');   
			
				return false;
			}
			
			if(MyForm.yearSaleAmount.value.length==0)
			{
				
				strMsg ="msg.annual.amount";         
				popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=yearSaleAmount";
				openPopup(popUrl,'ID');   
			
				return false;
			}
				
			if(MyForm.monthSaleAmount.value.length==0)
			{
				
				strMsg ="msg.month.amount";         
				popUrl = "${ctx }/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=monthSaleAmount";
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
		
		//-->
		</script>

</head>



<body>
<form name="MyForm" action="${ctx }/epc/edi/consult/insertStep2.do" method="post">

<input type="hidden" name="companyCharCode" value="${vendor.companyCharCode}"/>
<input type="hidden" name="lotteAffTradeCode" value="${vendor.lotteAffTradeCode}" />
<input type="hidden" name="bmanNo" value="${vendorSession.vendor.bmanNo}" />

<div id="wrap">

	<div id="con_wrap">
		

		<div class="con_area">

		
			<div class="step_wrap">
			
			<c:if test="${ vendorSession.vendor.chgStatusCd == '0' }"> 
				<ol>
					<li><a href="#" onclick="javascript:go_step('1');"><img src="/images/epc/edi/consult/step-base-form.gif" alt="기본정보 입력"></a></li>
					<li class="on"><a href="#"><img src="/images/epc/edi/consult/step-sales-form-on.gif" alt="매출정보 입력"></a></li>
					<li><img src="/images/epc/edi/consult/step-product-form.gif" alt="상품정보 입력"></a></li>
					<li><img src="/images/epc/edi/consult/step-counsel-form.gif" alt="상담신청"></a></li>
				</ol>
			</c:if>
			
			<c:if test="${ vendorSession.vendor.chgStatusCd != '0' }"> 
				<ol>
					<li><a href="#" onclick="javascript:go_step('1');"><img src="/images/epc/edi/consult/step-base-form.gif" alt="기본정보 입력"></a></li>
					<li class="on"><a href="#"><img src="/images/epc/edi/consult/step-sales-form-on.gif" alt="매출정보 입력"></a></li>
					<li><a href="#" onclick="javascript:go_step('4');"><img src="/images/epc/edi/consult/step-product-form.gif" alt="상품정보 입력"></a></li>
					<li><a href="#" onclick="javascript:go_step('5');"><img src="/images/epc/edi/consult/step-counsel-form.gif" alt="상담신청"></a></li>
				</ol>
			</c:if>
			</div>

			<!-- title -->
			<div class="s_title">
				<h2>매출정보</h2>
			</div>

			<div class="t_txt_wrap">
				<div class="ex_right"><p>표시는 필수입력사항</p></div>
			</div>
			<div class="tb_form_comm">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption>매출정보 입력 화면</caption>
				<colgroup><col width="130px"><col width="156px"><col width="130px"><col width="156px"><col width="130px"><col width="*"></colgroup>
				<tbody>
					<tr>
						<th width="130px"><p class="check">주력생산품</p></th>
						<td width="156px">
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="mainProduct" class="txt" value="${vendor.mainProduct }"  maxlength="20" style="width:106px;"></span></span>
							</p>
						</td>
						<th width="130px"><p class="check">연매출액</p></th>
						<td width="156px">
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="yearSaleAmount" class="txt" value="${vendor.yearSaleAmount }" maxlength="7" onkeyup="displayComma(this, this.value);" style="width:76px;"></span></span> 백만
							</p>
						</td>
						<th width="130px"><p class="check">월평균매출액</p></th>
						<td width="*">
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="monthSaleAmount" class="txt" value="${vendor.monthSaleAmount }" maxlength="7" onkeyup="displayComma(this, this.value);" style="width:76px;"></span></span> 백만
							</p>
						</td>
					</tr>
					<tr>
						<th><p>설립년도</p></th>
						<td>
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="foundationYear" class="txt" value="${vendor.foundationYear }" maxlength="4" onblur="len_chk(this)" style="width:76px;" /></span></span> 년도
							</p>
						</td>
						<th><p>자본금</p></th>
						<td>
							<p class="td_p">
								<span class="input_txt"><span><input type="text"  name="capitalAmount" class="txt" value="${vendor.capitalAmount }" maxlength="7" onkeyup="displayComma(this, this.value);" style="width:76px;"></span></span> 백만
							</p>
						</td>
						<th><p>공장유무</p></th>
						<td>
							<div class="td_p">
								<!-- select -->
								<select id="Select4" name="factoryType" style="width:129px;">
									<option selected value="1">직영</option>
									<option value="2">하청</option>
									<option value="3">기타</option>
								</select>
								<!--// select -->
							</div>
						</td>
					</tr>
					<tr>
						<th><p>회사 성격</p></th>
						<td colspan="5">
							<div class="td_p clearfloat">
								<div class="fl_left">
									<input id="company1" type="checkbox" name="chk_attribute" value="jejo" class="chk" checked><label class="label mr15" for="company1">제조</label>
									<input id="company2" type="checkbox" name="chk_attribute" value="jicsuip" class="chk"><label class="label mr15" for="company2">직수입</label>
									<input id="company3" type="checkbox" name="chk_attribute" value="chongpan" class="chk"><label class="label mr15" for="company3">총판/대리점</label>
									<input id="company4" type="checkbox" name="chk_attribute" value="broker" class="chk"><label class="label mr15" for="company4">벤더</label>
									<input id="company5" type="checkbox" name="chk_attribute" value="sanji" class="chk"><label class="label mr15" for="company5">산지</label>
									<input id="company6" type="checkbox" name="chk_attribute" value="imdae" class="chk"><label class="label" for="company6">임대수수료</label>
								</div>
								<div class="fl_right mr10">기타 <span class="input_txt ml5"><span><input type="text" name="companyDescription" class="txt" value="${vendor.companyDescription }"  maxlength="40" style="width:141px;"></span></span></div>
							</div>
						</td>
					</tr>
				</tbody>
				</table>
			</div>

			<!-- title -->
			<div class="s_title mt20">
				<h2>동업계 입점현황</h2>
			</div>
			<div class="tb_form_comm">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption>동업계 입점현황</caption>
				<colgroup><col width="130px"><col width="156px"><col width="130px"><col width="156px"><col width="130px"><col width="156px"></colgroup>
				<tbody>
					<tr>
						<th width="130px"><p>유통사명</p></th>
						<td width="156px" colspan="5">
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="smIndustryEntpNm" class="txt" value="${vendor.smIndustryEntpNm }"  maxlength="60" style="width:522px;"></span></span>
							</p>
						</td>
					</tr>
					<tr>
						<th width="130px"><p>납품상품</p></th>
						<td width="156px" colspan="3">
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="smIndustryEntpProdNm" class="txt" value="${vendor.smIndustryEntpProdNm }" maxlength="40" style="width:396px;"></span></span>
							</p>
						</td>
						<th width="130px"><p >월평균납품액</p></th>
						<td width="156px">
							<p class="td_p">
								<span class="input_txt"><span><input type="text" name="smIndustryEntpAmt" class="txt" value="${vendor.smIndustryEntpAmt }" maxlength="9" onkeyup="displayComma(this, this.value);" style="width:76px;"></span></span> 백만
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
				<li>업체 매출 상세정보를 입력하세요. 동업계 입점현황은 상담 판단의 중요자료입니다. 정확히 입력해 주시기 바랍니다.</li>
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

<input type="hidden" name="bman_no">	
</form>
</body>
</html>