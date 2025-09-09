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
		$("input.imageUpload").change(function() {
			var fileExtension = getFileExtension($(this).val()).toLowerCase();
			if(fileExtension != "gif" & fileExtension != "jpg" ) {
				alert("gif와 jpg파일만 지원합니다.");
				//resetImage($(this).get(0));
				$(this).val('');
				$(this).prev().val('');
			} else {
				$(this).prev().val($(this).val());
			}
		});
		
		/*
		$("input.imageUpload").change(function() {
			var fileExtension = getFileExtension($(this).val()).toLowerCase();
			//if (fileExtension != "gif" & fileExtension != "jpg") {
			if (fileExtension != "jpg") {				
				alert("<spring:message code='msg.consult.require.fileExtensionJpg'/>");				
			}
		});
		*/
	
	
		/*
		$("input.docUpload").change(function() {
			var fileExtension = getFileExtension($(this).val()).toLowerCase();
			if(fileExtension != "doc" & 
					fileExtension != "xls" & 
					fileExtension != "ppt" ) {
				alert('올바른 파일을 입력하세요!!\n첨부 문서는 아래 문서만 가능합니다.\n(MS워드, 엑셀, 파워포인트)');
				$(this).val('');
				$(this).prev().val('');
			} else {
				$(this).prev().val($(this).val());
			}
		});
		*/
	
		
	});	
	
	function checkStep3Submit() {
	
		var prodArray = "";
		
		$('input:checkbox[name=prodArraySeq]:checked').each( function(){
			//var aaaa=document.getElementById("prodArraySeq"+i).value;
			//var dddd= document.getElementById("bmanArrayNo"+i).value;
			//prodArraySeq
			//alert($(this).val());
			prodArray =  prodArray + $(this).val() +"/" ;
			
		});
		
		document.MyForm.prodArray.value = prodArray;
		//alert(document.MyForm.prodArray.value);
		
	    // var form=document.forms[0];	
	    // var form=document.MyForm[0];	
	          
	    //  alert(form);
	
	    //$("form[name=MyForm]").submit();
	
	          
	    //  alert($("input[name=selectedProduct]:checked").length);
	    // alert($("input[name=selectedProduct]:checked").value);
	    // var selectedLength = $("input[name=selectedProduct]:checked").length;
	    // alert(selectedLength);
	          
		//	var kk = document.getElementById("mmm");
		
		//	alert(kk.value);
	
	 
	
		//alert(form.autionchkedinfo.checked);
	
		
		/**
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
		**/
	
		
		if(check_submit_4()) {
		$("form[name=MyForm]").submit();
			//alert("폼 전송");
		}
		
		
		
	}
	
	
	function img_view(imgurl, imgpath) {
	
	//alert("tt")
	
	  var w = "600";
	  var h = "600";
	  var width = "600";
	  var height = "600";
	  
	  var path = "upload/" + imgurl;
	
	  var openWidth = "650";
	  var openHeight = "600";
	
	
	  if (openWidth > 800)	    openWidth = 800;
	  if (openHeight > 800)	    openHeight = 800;
	        
	  window.open("<c:url value='/edi/consult/step3ImagePopup.do?img='/>" + imgurl + "&imgpath=" + imgpath + "&h=" + h, "imgwin", "width=" + openWidth + ", height=" + openHeight + ", scrollbars=yes, resizable=yes");
	}	
	
	
	function downloadConsultDocumnet(vendorBusinessNo) {
		window.open("<c:url value='/edi/consult/step3DocumnetPopup.do?vendorBusinessNo='/>" + vendorBusinessNo , "docwin", "width=200, height=300, scrollbars=yes, resizable=yes");
	}
	
	/*
	function downloadConsultDocumnetView(vendorBusinessNo) {
		window.open('${ctx}/edi/consult/step3DocumnetDownloadView.do?vendorBusinessNo=' + vendorBusinessNo , 'docwin', 'width=200, height=300, scrollbars=yes, resizable=yes');
	}
	*/
	function check_submit_4()
	{
		var MyForm = document.getElementsByName("MyForm")[0];
		var resultFlag = true;
		var answeredCount1 =0;
		var notAnsweredCount1 = 0;
		var totalCount = 6;
		$("input.field_1").each(function(index) {
			if( $(this).val() == '' || $(this).val().length == 0) {
				notAnsweredCount1++;
			} else {					
				answeredCount1++;
			}	
		});
			
		if( answeredCount1 != totalCount && notAnsweredCount1 != totalCount ) {
			alert("<spring:message code='msg.consult.require.answered1'/>");
			return false;
		}	 
			
	
		var answeredCount2 =0;
		var notAnsweredCount2 = 0;
		$("input.field_2").each(function(index) {
			if( $(this).val() == '' || $(this).val().length == 0) {
				notAnsweredCount2++;
			} else {					
				answeredCount2++;
			}	
		});
			
		if( answeredCount2 != totalCount && notAnsweredCount2 != totalCount ) {
			alert("<spring:message code='msg.consult.require.answered2'/>");
			return false;
		}	 
	
	
		var answeredCount3 =0;
		var notAnsweredCount3 = 0;
		$("input.field_3").each(function(index) {
			if( $(this).val() == '' || $(this).val().length == 0) {
				notAnsweredCount3++;
			} else {					
				answeredCount3++;
			}	
		});
			
		if( answeredCount3 != totalCount  && notAnsweredCount3 != totalCount  ) {
			alert("<spring:message code='msg.consult.require.answered3'/>");
			return false;
		}	 
	
		if( notAnsweredCount1 == 6 && notAnsweredCount2 == 6 && notAnsweredCount3 ) {
			alert("<spring:message code='msg.consult.require.answered3anyone'/>");
			return false;
		}
		var buy_prc_cnt = MyForm.productCost.length;
			
		for(var i=0; i<buy_prc_cnt; i++)
		{
			var productCost = MyForm.productCost[i].value;
			MyForm.productCost[i].value = productCost.replace(/,/g, '');
			
			var productSalePrice = MyForm.productSalePrice[i].value;
			MyForm.productSalePrice[i].value = productSalePrice.replace(/,/g, '');
			
			var monthlyAverage = MyForm.monthlyAverage[i].value;
			MyForm.monthlyAverage[i].value = monthlyAverage.replace(/,/g, '');
		}
		
		return true;
	}	

</script>

</head>

<body>

<form name="MyForm" id="MyForm" action="<c:url value='/epc/edi/consult/NEDMCST0310insertStep3.do'/>" method="post" enctype="multipart/form-data" >
<input type="hidden" name="bmanNo" id="bmanNo" value="<c:out value='${vendorSession.vendor.bmanNo}'/>" />

<input type="hidden" name="gubun" id="gubun"  value="<c:out value='${gubun }'/>" />
<input type="hidden" name="prodArray" id=prodArray value="" />

<!--<input type="hidden" name="returnPage" value="epc/edi/consult/insertStep3.do" />-->

<div id="wrap">

	<div id="con_wrap">

		<div class="con_area">
		
			<c:if test="${ vendorSession.vendor.chgStatusCd == '0' }">
				<div class="step_wrap">
				<ol>
					<li><a href="#" onclick="javascript:go_step('1');"><img src="/images/epc/edi/consult/step-base-form.gif" alt="<spring:message code='text.consult.field.step-base-form'/>"></a></li>
					<li><a href="#" onclick="javascript:go_step('3');"><img src="/images/epc/edi/consult/step-sales-form.gif" alt="<spring:message code='text.consult.field.step-sales-form'/>"></a></li>
					<li class="on"><a herf="#"><img src="/images/epc/edi/consult/step-product-form-on.gif" alt="<spring:message code='text.consult.field.step-product-form'/>"></a></li>
					<li><img src="/images/epc/edi/consult/step-counsel-form.gif" alt="<spring:message code='text.consult.field.step-counsel-form'/>"></li>
				</ol>
				</div>
			</c:if>	
		
			<c:if test="${ vendorSession.vendor.chgStatusCd != '0' }"> 	
			<div class="step_wrap">
				<ol>
					<li><a href="#" onclick="javascript:go_step('1');"><img src="/images/epc/edi/consult/step-base-form.gif" alt="<spring:message code='text.consult.field.step-base-form'/>"></a></li>
					<li><a href="#" onclick="javascript:go_step('3');"><img src="/images/epc/edi/consult/step-sales-form.gif" alt="<spring:message code='text.consult.field.step-sales-form'/>"></a></li>
					<li class="on"><a herf="#"><img src="/images/epc/edi/consult/step-product-form-on.gif" alt="<spring:message code='text.consult.field.step-product-form'/>"></a></li>
					<li><a href="#" onclick="javascript:go_step('5');"><img src="/images/epc/edi/consult/step-counsel-form.gif" alt="<spring:message code='text.consult.field.step-counsel-form'/>"></a></li>
				</ol>
			</div>
			</c:if>
			

			<!-- title -->
			<div class="s_title mt20">
				<h2><spring:message code='text.consult.field.mainProductInfo'/></h2>
			</div>

			<div class="t_txt_wrap">
				<div class="ex_right"><p><spring:message code='text.consult.field.reqSymbol'/></p></div>
			</div>
			
			<div class="tb_form_h_common_02">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption><spring:message code='text.consult.field.mainProductInfo'/></caption>
				<colgroup><col width="68px"><col width="171px"><col width="135px"><col width="135px"><col width="135px"><col width="*"></colgroup>
				<thead>
					<tr>
						<th width="68px" class="line"><spring:message code='text.consult.header.bunho'/></th>
						<th width="171px" class="line"><img src="/images/epc/edi/consult/bul-tb-required.gif" class="mr5"><spring:message code='text.consult.field.productName'/></th>
						<!-- <th width="135px" class="line"><img src="/images/epc/edi/consult/bul-tb-required.gif" class="mr5">상품원가</th> -->
						<th width="135px" class="line"><img src="/images/epc/edi/consult/bul-tb-required.gif" class="mr5"><spring:message code='text.consult.header.productCost'/></th>
						<!-- <th width="135px" class="line"><img src="/images/epc/edi/consult/bul-tb-required.gif" class="mr5">상품매가</th> -->
						<th width="135px" class="line"><img src="/images/epc/edi/consult/bul-tb-required.gif" class="mr5"><spring:message code='text.consult.header.productSalePrice'/></th>
						<!-- <th width="135px" class="line"><img src="/images/epc/edi/consult/bul-tb-required.gif" class="mr5">월 평균매출</th> -->
						<th width="135px" class="line"><img src="/images/epc/edi/consult/bul-tb-required.gif" class="mr5"><spring:message code='text.consult.header.monthlyAverage'/></th>
						<th width="*"><img src="/images/epc/edi/consult/bul-tb-required.gif" class="mr5"><spring:message code='text.consult.field.insertImg'/>
							<ul class="list_star">
								<li>
									<font color="red">
										<spring:message code='text.consult.field.docUploadInfo'/>
									</font>
								</li>
							</ul>
						</th>
						
						
					</tr>
				</thead>
				<tbody>
				
				<c:if test="${fn:length(vendorProductList) > 0 }"> 
					<c:forEach  var="vendorProduct" items="${vendorProductList}" varStatus="status">
					
					<tr>
						<td class="t_center num" rowspan="2"><span class="t_12"><c:out value='${vendorProduct.seq }'/><br>
						<c:if test="${ not empty vendorProduct.attachFileCode }">
							<a href="javascript:img_view('<c:out value="${vendorProduct.attachFileCode}"/>', '<c:out value="${attachFileFolder}"/>')" class="btn"><spring:message code='text.consult.field.viewImg'/></a>
						</c:if>
						</span></td>
						<input type="hidden" name="seq" value="<c:out value='${vendorProduct.seq }'/>" ID="Hidden1">
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" class="txt field_<c:out value='${vendorProduct.seq}'/>" name="productName" id="productName"  value="<c:out value='${vendorProduct.productName }'/>" maxlength="20" style="width:130px;"></span></span>
						</td>
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" class="txt field_<c:out value='${vendorProduct.seq}'/>" name="productCost" id="productCost" value="<c:out value='${vendorProduct.productCost }'/>" onkeyup="displayComma(this, this.value);" maxlength="14" style="width:94px;"></span></span>
						</td>
						<td  class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" class="txt field_<c:out value='${vendorProduct.seq}'/>" name="productSalePrice" id="productSalePrice" value="<c:out value='${vendorProduct.productSalePrice }'/>" onkeyup="displayComma(this, this.value);" maxlength="14" style="width:94px;"></span></span>
						</td>
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" class="txt field_<c:out value='${vendorProduct.seq}'/>" name="monthlyAverage" id="monthlyAverage" value="<c:out value='${vendorProduct.monthlyAverage }'/>" onkeyup="displayComma(this, this.value);" maxlength="14" style="width:94px;"></span></span>
						</td>
						<td class="t_center no_b_line">
							<input type="hidden" name="uploadFile<c:out value='${status.count }'/>" id="uploadFile<c:out value='${status.count }'/>" class="field_<c:out value='${vendorProduct.seq}'/>" value="<c:out value='${vendorProduct.attachFileCode }'/>"/>
							<input type="file"   name="attachFile<c:out value='${status.count }'/>" id="attachFile<c:out value='${status.count }'/>" class="imageUpload" value=""  style="width:190px; height:18px;" >

						</td>
					</tr>
					<tr class="pr_info">
						<td colspan="5" class="t_left">
							<span class="t_12 mr10"><spring:message code='text.consult.field.productDesc'/></span><span class="input_txt"><span><input type="text" class="txt field_<c:out value='${vendorProduct.seq}'/>" name="productDescription" id="productDescription" value="<c:out value='${vendorProduct.productDescription }'/>" style="width:697px;"></span></span>
						</td>
					</tr>
					
					</c:forEach>
				</c:if>
				
				
				<c:if test="${fn:length(vendorProductList) == 0 }"> 
					<tr>
						<td class="t_center num" rowspan="2"><span class="t_12">1</span></td>
						<input type="hidden" name="seq" value="1" ID="Hidden1">
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" class="txt field_1" name="productName" id="productName" value="" maxlength="20" style="width:130px;"></span></span>
						</td>
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" class="txt field_1" name="productCost" id="productCost" value="" onkeyup="displayComma(this, this.value);" maxlength="14" style="width:94px;"></span></span>
						</td>
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" class="txt field_1" name="productSalePrice" id="productSalePrice" value="" onkeyup="displayComma(this, this.value);" maxlength="14" style="width:94px;"></span></span>
						</td>
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" class="txt field_1" name="monthlyAverage" id="monthlyAverage" value="" onkeyup="displayComma(this, this.value);" maxlength="14" style="width:94px;"></span></span>
						</td>
						<td class="t_center no_b_line"> 
							<input type="hidden" name="uploadFile1" class="field_1" value=""/>
							<input type="file" name="attachFile1"  value="" class="imageUpload"  style="width:190px; height:18px;" >
					   </td>
					</tr>
					<tr class="pr_info">
						<td colspan="5" class="t_left">
							<span class="t_12 mr10"><spring:message code='text.consult.field.productDesc'/></span><span class="input_txt"><span><input type="text" class="txt field_1" value="" name="productDescription" style="width:697px;"></span></span>
						</td>
					</tr>
				</c:if>
				
				<c:if test="${fn:length(vendorProductList) == 0 || fn:length(vendorProductList) == 1   }"> 
					<tr>
						<td class="t_center num" rowspan="2"><span class="t_12">2</span></td>
						<input type="hidden" name="seq" value="2" ID="Hidden1">
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" name="productName" class="txt field_2" value="" maxlength="20" style="width:130px;"></span></span>
						</td>
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" class="txt field_2" name="productCost" value="" onkeyup="displayComma(this, this.value);" maxlength="14" style="width:94px;"></span></span>
						</td>
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" class="txt field_2" name="productSalePrice" value="" onkeyup="displayComma(this, this.value);" maxlength="14" style="width:94px;"></span></span>
						</td>
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" class="txt field_2" name="monthlyAverage" value="" onkeyup="displayComma(this, this.value);" maxlength="14" style="width:94px;"></span></span>
						</td>
						<td class="t_center no_b_line"> 
						<input type="hidden" name="uploadFile2" value="" class="field_2" />
						<input type="file" name="attachFile2"  value="" class="imageUpload"  style="width:190px; height:18px;" >
					   </td>
					</tr>
					<tr class="pr_info">
						<td colspan="5" class="t_left">
							<span class="t_12 mr10"><spring:message code='text.consult.field.productDesc'/>  </span><span class="input_txt"><span><input type="text" class="txt field_2" value="" name="productDescription" style="width:697px;"></span></span>
						</td>
					</tr>
				</c:if>	
					
				<c:if test="${ fn:length(vendorProductList) == 0 || fn:length(vendorProductList) == 1 || fn:length(vendorProductList) == 2 }">	
					<tr>
						<td class="t_center num" rowspan="2"><span class="t_12">3</span></td>
						<input type="hidden" name="seq" value="3" ID="Hidden1">
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" name="productName" class="txt field_3" value="" maxlength="20" style="width:130px;"></span></span>
						</td>
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" class="txt field_3" name="productCost" value="" onkeyup="displayComma(this, this.value);" maxlength="14" style="width:94px;"></span></span>
						</td>
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" class="txt field_3" name="productSalePrice" value="" onkeyup="displayComma(this, this.value);" maxlength="14" style="width:94px;"></span></span>
						</td>
						<td class="t_center no_b_line">
							<span class="input_txt"><span><input type="text" class="txt field_3" name="monthlyAverage" value="" onkeyup="displayComma(this, this.value);" maxlength="14" style="width:94px;"></span></span>
						</td>
						<td class="t_center no_b_line"> 
							<input type="hidden" name="uploadFile3" class="field_3" value=""/>
							<input type="file" name="attachFile3"  value=""  class="imageUpload"    style="width:190px; height:18px;" >
					   </td>
					</tr>
					<tr class="pr_info">
						<td colspan="5" class="t_left">
							<span class="t_12 mr10"><spring:message code='text.consult.field.productDesc'/></span><span class="input_txt"><span><input type="text" class="txt field_3" value="" name="productDescription" style="width:697px;"></span></span>
						</td>
					</tr>
				</c:if>
				</tbody>
				</table>
			</div>

			<!-- 
			<ul class="list_star">
				<li>
					<font color="blue">
					해당 업체의 주력상품 정보를 입력해 주시기 바랍니다. 이미지 파일 용량은 1MB 이하, 첨부파일 용량은 2MB 이하로 해주시기 바랍니다. (총 용량 5MB 이하)<br>
					</font>
					<font color="red">(이미지파일은 JPG, 첨부파일은 PPT,PPTX)</font>
				</li>
			</ul> 
			-->

			<!-- 특이사항 -->
			<div class="s_title mt20">
				<h2><spring:message code='text.consult.field.uniqueness'/></h2>
			</div>
			<div class="box_etc_input"><textarea cols="122" rows="5" name="content" style="width:828px; height:40px;"><c:out value='${vendor.pecuSubjectContent}'/></textarea></div>
			<p class="add_file">
				<span class="mr15"><spring:message code='text.consult.field.docUpload'/></span>
				<input type="hidden" name="vendorFile" value="<c:out value='${vendor.attachFileCode }'/>"/> 
				<input type="file" name="vendorAttachFile" class="docUpload" value=""  style="width:335px;  height:18px;">
				<c:if test="${not empty vendor.attachFileCode }">
					<span style="margin-left: 50px"><c:out value='${vendor.attachFileCode}'/></span>&nbsp;<a href="javascript:downloadConsultDocumnet('<c:out value="${vendor.bmanNo}"/>')"><spring:message code='text.consult.field.download'/></a>					
				</c:if>
				<ul class="list_star">
					<li>
						<font color="blue">
							<b><spring:message code='text.consult.field.step3Info1'/></b>
						</font>
						<font color="red">
							<spring:message code='text.consult.field.step3Info2'/>
						</font>
					</li>
				</ul>
			</p>
			
			<!-- button -->
			<div class="btn_c_wrap mt30">
				<span class="btn_red"><span><a href="javascript:checkStep3Submit();"><spring:message code='button.common.confirm'/></a></span></span>
			</div>
			<!--// button -->

		</div>
						
	</div>

</div>



</form>

</body>
</html>