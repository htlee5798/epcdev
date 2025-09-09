<%--
- Author(s): projectBOS32
- Created Date: 2016. 04. 27
- Version : 1.0
- Description : 추가구성품 등록/수정 폼
--%>

<%@include file="../edi/common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ include file="../edi/product/CommonProductFunction.jsp" %>
<%@include file="../edi/product/javascript.jsp" %>

<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- 공통 css 및 js 파일 -->

<meta http-equiv="Cache-Control" content="no-store"/>
<!-- HTTP 1.0 -->
<meta http-equiv="Pragma" content="no-cache"/>
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>추가구성품등록</title>
	
	<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>
	<script type="text/javascript" src="../../../namoCross/js/namo_scripteditor.js"></script>
	
	<script type="text/javascript" >
	var vndId = "";
	
	$(document).ready(function(){
		
		//-----상품등록이후 상품 기본정도 등록 완료 메세지 띄운다.
		var mode = "<c:out value='${param.mode}'/>";
		if (mode == "save") {
			alert("추가구성품 등록 :  등록이 완료 되었습니다. \n 상세페이지는 EDI > 상품 > 임시보관함 에서 확인 가능합니다.");	
			window.close();
		} else if(mode == "update") {
			alert("수정 되었습니다.");
		}
		
		if("${newProdDetailInfo}" == ''){ 
			vndId = "${epcLoginVO.repVendorId}";
			
			if ("${epcLoginVO.vendorTypeCd}" == '06' ){
				
				if ("${epcLoginVO.repVendorId}" != ''){
					checkBlackListVendor1 ("${epcLoginVO.repVendorId}");
				}
			} else {
				
				// 협력업체 선택시 실행.
				if ("${epcLoginVO.repVendorId}" != '') {
					checkBlackListVendor();
				}
			}
		}else{
			vndId = "${newProdDetailInfo.entpCd}";
		}
	 	
		//업체코드별 거래형태 조회(selectBox mapping)
		trdTypFgSel();
		
		$("select[name=entpCode]").change(function() {
			var errorNode = $(this).prev("div[name=error_msg]").length;
			if( errorNode > 0 ) { 
				$(this).prev().remove();
			}
			
			if( $(this).val() != '' ){
				checkBlackListVendor();
			}
		});
		
		// 필수 콤보박스 값 검증
		$("select.required").change(function() {
			validateSelectBox($(this)); 
		});
		
		// 필수 입력항목 검증
		$("input:text.required").blur(function() {
			//validateTextField($(this));
			validateFormTextField($(this));
		});
		
		// 해당 입력 항목이 값이 있는경우 검증
		$("input:text.requiredIf").blur(function() {
			if($(this).val() != '') {
				validateTextField($(this));
			}
		});
		
		// radio버튼이 선택되었는지 검증.
		$("input:radio.required").click(function() {
			validateRadioField($(this));
		});

		// 온라인 대표 상품 코드 변경시 실행
		$("select[name=onlineProductCode]").change(selectOnlineRepresentProductCode2);

		// 온라인전용/소셜상품 선택시 실행
		$("input[name=onOffDivnCode]").click(setOnOffDivnCode);
		
		
		//모든 input에 대해서 특수문자 경고표시하기
		$("input").keyup(function(){
				
			var value = $(this).val();
			var arr_char = new Array();

			arr_char.push("'");
			arr_char.push("\"");
			arr_char.push("<");
			arr_char.push(">");
			arr_char.push(";");
				
				
			for(var i=0 ; i<arr_char.length ; i++)
			{
				if(value.indexOf(arr_char[i]) != -1)
				{
					alert("[<, >, ', /, ;] 특수문자는 사용하실 수 없습니다.");
					value = value.substr(0, value.indexOf(arr_char[i]));

					$(this).val(value);				
				}
			}
		});
		
		/*********************************** 여기서 부턴 상품등록이후 상품 상세정보 설정에 사용됩니다.*************************************************/
		
		//----- 상품의 상세정보 SeleceBox, RadioButton ValueSetting.[커스텀태그에서는 시큐어 코딩이 불가능 하여 스크립트에서 처리]
		if ('<c:out value="${newProdDetailInfo.pgmId}" />' != "") {
			//-----SeleceBox, RadioButton ValueSetting..
			_eventSetnewProdDetailInfoDetailValue();
			
		}		
		/*****************************************************************************************************************************/
		
	});
	
	
	/* 상품등록 이후 상세정보 SelectBox, RadioButton Value Settings..... */
	function _eventSetnewProdDetailInfoDetailValue() {
		var taxatDivnCd		=	"<c:out value='${newProdDetailInfo.taxatDivnCd}'/>";		//면과세구분
		var onlineRepProdCd	=	"<c:out value='${newProdDetailInfo.onlineRepProdCd}'/>";	//온라인 대표상품코드
		var homeCd			=	"<c:out value='${newProdDetailInfo.homeCd}'/>"				//원산지
		var norProdSaleCurr		=	"<c:out value='${newProdDetailInfo.norProdSaleCurr}'/>";		//정산매가단위
			
		//-----SelectBox Settings....
		$("select[name='taxatDivCode']").val(taxatDivnCd);
		$("select[name='taxatDivCode']").attr("disabled",true);
		$("select[name='onlineProductCode']").val(onlineRepProdCd);
		$("select[name='productCountryCode']").val(homeCd);
		$("select[name='norProdSaleCurr']").val(norProdSaleCurr);
	}
	
	
	/*
		상품 등록 폼을 전송하기전에 검증로직을 실행하는 함수.
		공통 검증 함수인 validateCommon을 실행하고 각 필드 별로 필요한 작업 실행.
    */
	function validateNewProductInfo() {

		var validationResult = validateCommon();
		
		var errorLength = $("div[name=error_msg]").length;
		
		if(!validationResult ||
				errorLength > 0	) {
			
			alert("필수 속성값들을 모두 입력해주시기 바랍니다.");
			return false;
		} else {
			return true;
		}
	}

	// 상품 등록 폼을 전송하기 전에 각 필드의 기본  값 설정. 
	function setupFormFieldDefaultValue() {
		var tempCode = $("#temperatureDivnCode").val();
		$("#tmpDivnCode").val(tempCode);

		var normalProductSalePrice = $("input[name=normalProductSalePrice]").val();
		$("input[name=sellPrice]").val(normalProductSalePrice);


		var displayTotalQuantity = $("input[name=displayTotalQuantity]").val();
		var displayBaseQuantity = $("input[name=displayBaseQuantity]").val();
		

		if( displayTotalQuantity == '')
		$("input[name=displayTotalQuantity]").val(0);

		if( displayBaseQuantity == '')				
		$("input[name=displayBaseQuantity]").val(0);

	}

	//상품등록 폼 전송
	function submitProductInfo() {
		 $("div#content_wrap").block({ message: null ,  showOverlay: false });
		var index = $("#itemRow").val();	

		 //필드 값 검증.
		if( validateNewProductInfo() ) {

			//유효성 체크 통과
			setupFormFieldDefaultValue();
			
			if($("#entpCode").val() == ""){
				alert('업체선택은 필수입니다.');
				$("#entpCode").focus();
				return;
			}
			
			if (!calByteProd (newProduct.productName, 80, '상품명', false) ) return;		// 상품명 byte체크
			
			var flagVar = 0;
			
			$("form[name=newProduct] input:file").each(function() {
				if( $(this).val() == '') {
					flagVar ++;
				}
			});
			 
			if(flagVar == 1 && "${onlineImageList}" == "") {
				alert("업로드할 대표이미지를 선택해주세요.");
				return;
			}
			
			$("#taxatDivCode").attr("disabled",false);
			
			loadingMaskFixPos();
			
			var actUrl = "<c:url value='/product/saveComponent.do'/>";
			
			if ('<c:out value="${newProdDetailInfo.pgmId}" />' != "") {
				actUrl = "<c:url value='/product/modifyComponent.do'/>";
			}
			
			$("#newProduct").attr("action",actUrl);
			$("#newProduct").attr("method", "post").submit();
		}
		
		setTimeout(function() {
		 $("div#content_wrap").unblock();
		 hideLoadingMask();
		}, 500);
	}


    function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
    	$("#entpCode").val(strVendorId);
    	
    	checkBlackListVendor1(strVendorId);
    }
    
    function trdTypFgSel(){
		$.getJSON("${ctx}/edi/product/trdTypFgSel.do",null, trdTypFgSelRst);
	}
	
	function trdTypFgSelRst(data){
		for(var i=0; i<data.length; i++){
			for(var j=0; j<$("select[name=entpCode] option").size(); j++){
				if(data[i].VENDOR_ID == $("select[name=entpCode] option:eq("+j+")").val()){
					var tdrTypFgNm = data[i].TRD_TYP_FG_NM;
					
					if(tdrTypFgNm == null || tdrTypFgNm == "null" || tdrTypFgNm == ""){
						tdrTypFgNm = ""
					}else{
						tdrTypFgNm = "("+data[i].TRD_TYP_FG_NM+")";
					}
		
					$("select[name=entpCode] option:eq("+j+")").replaceWith("<option value='"+data[i].VENDOR_ID+"'>"+data[i].VENDOR_ID+tdrTypFgNm+"</option>");
				}
			}
		}
		
		$("select[name=entpCode]").val(vndId);
	}
	</script>

</head>

<body>
<div id="content_wrap">
	<!--	@ BODY WRAP START 	-->
	<form name="newProduct"  id="newProduct" method="post" enctype="multipart/form-data">
	<input type="hidden" name="blackListVendorFlag"		id="blackListVendorFlag" 	value="n" />	
	<input type="hidden" name="newProductCode" 			id="newProductCode"		value="<c:out escapeXml='false' value='${param.pgmId }'/> "	/>	
	
	<input type="hidden" name="tradeTypeCode"			id="tradeTypeCode" 			value="1" />
	<input type="hidden" name="productImageId"			id="productImageId"			value="${newProdDetailInfo.prodImgId}" />
	<input type="hidden" name="eventSellPrice"			id="eventSellPrice"			value="${newProdDetailInfo.evtProdSellPrc}" />
	<input type="hidden" name="imgNcnt"					id="imgNcnt"				value="${newProdDetailInfo.imgNcnt}" />
	<input type="hidden" name="onlineDisplayCategoryCode"  id="onlineDisplayCategoryCode" value="${newProdDetailInfo.categoryId}" />
	<input type="hidden" name="ctpdYn"					id="ctpdYn"				value="Y" />
	<input type="hidden" 	name="sellCode"			value="<c:out value='${newProdDetailInfo.sellCd }'/>"   />
	<input type="hidden" name="productDivnCode" value="1" />
	
	<input type="hidden" name="officialOrder.newProductGeneratedDivnCode" value="EDI" />
	<div id="wrap_menu">
		<!--	@ 검색조건	-->
		<div class="wrap_search">
			
			<!-- 01 : search -->
			<div class="bbs_list" >
				<ul class="tit">
					<li class="tit">추가구성품등록</li>
					<li class="btn">
						<a href="#" class="btn" onclick="submitProductInfo();"><span><spring:message code="button.common.save"/></span></a>
					</li>
				</ul>
				<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
				
					<colgroup>
						<col style="width:15%" />
						<col style="width:35%" />
						<col style="width:15%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> 온/오프 상품구분</th>
						<td>
							<input type="hidden" name="onOffDivnCode" value="1" /><b>온라인 전용상품(추가 구성품)</b>
						</td>
			           <th><span class="star">*</span> 면과세구분</th>
						<td>
							<html:codeTag attr="class=\"required\"" objId="taxatDivCode" objName="taxatDivCode" parentCode="PRD06" width="150px;" comType="SELECT" formName="form" defName="선택"  />
						</td>
					</tr>
					
					<tr>
						<th><span class="star">*</span> 협력업체</th>
						<td>
							<c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<c:if test="${not empty newProdDetailInfo.entpCd}">
											<input type="text" name="entpCode" id="entpCode" readonly="readonly" readonly="readonly" value="${newProdDetailInfo.entpCd}" style="width:40%;"/>
										</c:if>
										<c:if test="${empty newProdDetailInfo.entpCd}">
											<input type="text" name="entpCode" id="entpCode" readonly="readonly" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
										</c:if>										
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<c:if test="${not empty newProdDetailInfo.entpCd}">
											<html:codeTag objId="entpCode" objName="entpCode" width="150px;" selectParam="${newProdDetailInfo.entpCd}" dataType="CP" comType="SELECT" formName="form"  />
										</c:if>
										
										<c:if test="${ empty newProdDetailInfo.entpCd}">
											<html:codeTag objId="entpCode" objName="entpCode" width="150px;" selectParam="${epcLoginVO.repVendorId}" dataType="CP" comType="SELECT" formName="form"  />
										</c:if>
									</c:when>
							</c:choose>
						</td>
						<th><span class="star">*</span> 원산지</th>
						<td>
							<html:codeTag attr="class=\"required\"" objId="productCountryCode" objName="productCountryCode" parentCode="PRD16" width="150px;" comType="SELECT" formName="form" defName="선택"  />
						</td>
					</tr>
			
					<tr>
						<th><span class="star">*</span> 정상매가</th>
						<td>
							<input type="text" name="normalProductSalePrice"	value="<c:out value='${newProdDetailInfo.norProdSalePrc }'/>" class="required number" maxlength="8" style="width:95px;" onkeydown='onlyNumber(event)'/>
							<input type="hidden" 	name="sellPrice"			value="<c:out value='${newProdDetailInfo.evtProdSellPrc }'/>"   />
							<html:codeTag   objId="norProdSaleCurr" 	objName="norProdSaleCurr"		parentCode="PRD40" 	comType="SELECT" 	formName="form" 	selectParam="KRW" attr="class=\"onOffField required\""/>							
						</td>
						<th>협력사 내부 상품코드</th>
						<td>
							<input type="text"  name="entpInProdCd"  id="entpInProdCd"  style="width:150px;" value="${newProdDetailInfo.entpInProdCd}"/>
						</td>
					</tr>
			
					<tr>
						<th><span class="star">*</span> 온라인 대표상품코드</th>
						<td >
							<div id="onlineRepresentProductTD"></div>
							<select name="onlineProductCode" class="required" style="width:98%;">
								<option value=""><c:if test="${not empty onlineRepresentProductList}">선택</c:if></option>
								<c:forEach items="${onlineRepresentProductList}" var="online"  >
									<option value="${online.onlineProductCode}">
									${online.profitRate}% /
									${online.onlineProductCode} / 
									${online.sellCode} / 
									${online.onlineProductName} /
									${online.l4CodeName} /
									${online.taxatDivCode}
									</option> 
								</c:forEach>
							</select>							
						</td>
						<th><span class="star">*</span> 온라인 대표상품명</th>
						<td>						
							<input type="text" name="onlineProductName" class="required" maxlength="80" style="width:150px;"	value="<c:out value='${newProdDetailInfo.onlineProdNm}'/>" readonly/>							
						</td>	
					</tr>
					
					<tr>
						<th><span class="star">*</span> 이익률(%)</th>
						<td colspan="3">	
							<input type="hidden" name="normalProductCost"					value="<c:out value='${newProdDetailInfo.norProdPcost}'/>" />
							<input type="text"   name="profitRate"  class="required rate"	value="<c:out value='${newProdDetailInfo.prftRate}'/>" 	maxlength="5" style="width:150px;" readonly/>	
						</td>			
					</tr>
					
					<tr>
						<th><span class="star">*</span> 최소주문가능량</th>
						<td>
							 <input type="text" maxlength="5"  name="officialOrder.minimumOrderQuantity" 	value="${newProdDetailInfo.minOrdPsbtQty}"	style="width:150px;" class="required number range minimum" onkeydown='onlyNumber(event)'/>
						</td>	
						<th><span class="star">*</span> 최대주문가능량</th>
						<td >
							 <input type="text"  maxlength="5" name="officialOrder.maximumOrderQuantity"	value="<c:out value='${newProdDetailInfo.maxOrdPsbtQty}'/>"style="width:150px;" class="required number range maximum" onkeydown='onlyNumber(event)'/>
						</td>
					</tr>
					
					<tr>
						<th> 상품명</th>
						<td>
							<input type="text" name="productName"  value="${newProdDetailInfo.prodNm}" style="width:91%;" maxlength="50" />
						</td>
						<th>브랜드</th>
						<td>
							 <input type="text" maxlength="30" class="requiredIf" id="brandName" name="officialOrder.brandName" 	value="${newProdDetailInfo.brandName}"	style="width:150px;" />
							 <input type="hidden" id="brandCode" name="officialOrder.brandCode"	value="${newProdDetailInfo.brandCode}" /> <a href="javascript:openBrandPopup();" class="btn" ><span><spring:message code="button.common.choice"/></span></a>							
						</td>	
					</tr>
					
					<tr>
						<th>모델명</th>
						<td>
							 <input type="text" maxlength="30" class="requiredIf" id="modelName" name="officialOrder.modelName" value="${newProdDetailInfo.modelNm}"style="width:150px;" />
						</td>
						<th>메이커</th>
						<td >
							 <input type="text" maxlength="30" class="requiredIf"  id="makerName" name="officialOrder.makerName"	value="<c:out value='${newProdDetailInfo.makerName}'/>" style="width:150px;" />
							 <input type="hidden" name="officialOrder.makerCode" id="makerCode" value="${newProdDetailInfo.makerCode}" /> <a href="javascript:openMakerPopup();" class="btn" ><span><spring:message code="button.common.choice"/></span></a>
						</td>
					</tr>
				</table>
			</div>	
			<br/>
			<!-- 온라인 대표이미지 등록 시작------------------------------------------------------------------------------------------------------------------------------------------->
			<div class="bbs_search">
				<ul class="tit">
					<li class="tit"><a name="onlineImageAnchor">*온라인 필수 대표이미지</a> </li>
				</ul>
			</div>
				
			<input type="hidden" name="pgmId"					id="pgmId"				value="<c:out value='${param.pgmId}'/>" />
			<input type="hidden" name="prodDivnCd" 			id="prodDivnCd"			 	/>
			<input type="hidden" name="onOffDivnCd" 		id="onOffDivnCd"		value="<c:out value='${newProdDetailInfo.onoffDivnCd}'/>" 	/>
			<input type="hidden" name="uploadFieldCount" 	id="uploadFieldCount" 		value="1"/>						
		
			<div class="bbs_search">
				<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td colspan=6 height=40><b>*주의사항</b> : 500x500크기로 등록해 주시기 바랍니다. </td>
					</tr>
					<colgroup>
						<col  />
						<col />
						<col  />
						<col />
						<col />
						<col  />
					</colgroup>
					<tr>
						<th><span>1.</span> 500 x 500</th>
						<th>250 x 250</th>
						<th>208 x 208</th>
						<th>120 x 120</th>
						<th>90 x 90</th>
						<th>80 x 80</th>
					</tr>
					<c:forEach  items="${onlineImageList}"	var="imageFile" varStatus="index" >
						<tr id="image_row_${index.count}"   name="image_row">
							<td align="center">																
								<img width="95px" src='${imagePath}/${subFolderName }/${imageFile }_500.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_500">
							</td>
							<td align="center">
								<img width="95px" src='${imagePath}/${subFolderName }/${imageFile }_250.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_250">
							</td>							
							<td align="center" >
								<img width="95px" src='${imagePath}/${subFolderName }/${imageFile }_208.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_208">
							</td>
							<td align="center">
								<img  src='${imagePath}/${subFolderName }/${imageFile }_120.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_120">
							</td>
							<td align="center">
								<img  src='${imagePath}/${subFolderName }/${imageFile }_90.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_90">
							</td>							
							<td align="center" >
								<img  src='${imagePath}/${subFolderName }/${imageFile }_80.jpg?currentSecond=${currentSecond}' name="ImgPOG1f" id="image_1_80">
							</td>
						</tr>
						
						<tr id="submit_row_${index.count}" name="submit_row">
							<td colspan="6" align="right" style="padding-right:40px">									
								<input type="file" name="${imageFile }" />							
							</td>
						</tr>								
					</c:forEach>
					
					
					
					<!-- 온라인 대표이미지 리스트가 없을경우 -->
					<c:if test="${empty onlineImageList }">
						<tr name="image_row">
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_500">
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_250">
							</td>						
							<td align="center" >
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_208">
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_120">
							</td>
							<td align="center">
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_90">
							</td>						
							<td align="center" >
								<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_80">
							</td>
						</tr>
						<tr name="submit_row">
							<td colspan="6" align="right" style="padding-right:40px">										
								<input type="file" name="front" />										
							</td>
						</tr>
					</c:if>							
				</table>	
			</div>
			
			<textarea id="template" style="display:none;">				
				<td align="center">
					<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_500">
				</td>
				<td align="center">
					<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_250">
				</td>						
				<td align="center" >
					<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_208">
				</td>
				<td align="center">
					<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_120">
				</td>
				<td align="center">
					<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_90">
				</td>						
				<td align="center" >
					<img width="95px" src="/images/epc/edi/no_photo.gif" name="ImgPOG1f" id="image_1_80">
				</td>			
			</textarea>		
			<!-- 온라인 대표이미지 등록 끝------------------------------------------------------------------------------------------------------------------------------------------->
		</div>
	</form>
</div>

</body>
</html>
