<%--
	Page Name 	: NEDMPRO0041.jsp
	Description : 임시보관함 상품 상세보기 페이지
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2015.12.08 		SONG MIN KYO 	최초생성
--%>
<%@ include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ include file="./CommonProductFunction.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Cache-Control" content="no-store"/>
<!-- HTTP 1.0 -->
<meta http-equiv="Pragma" content="no-cache"/>
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><spring:message code='msg.product.onOff.title'/></title>
	
	<style type="text/css">
		ul.tabs {
			margin: 0;
			padding: 0;
			float: left;
			list-style: none;
			height: 32px; /*--Set height of tabs--*/
			border-bottom: 1px solid #999;
			border-left: 1px solid #999;
			width: 100%;
		}
		ul.tabs li {
			float: left;
			margin: 0;
			padding: 0;
			height: 31px; /*--Subtract 1px from the height of the unordered list--*/
			line-height: 31px; /*--Vertically aligns the text within the tab--*/
			border: 1px solid #999;
			border-left: none;
			margin-bottom: -1px; /*--Pull the list item down 1px--*/
			overflow: hidden;
			position: relative;
			background: #e0e0e0;
			font-family: Dotum, "돋움";
			font-size: 10px;
			font-weight: bold;
		}
		ul.tabs li a {
			text-decoration: none;
			color: #000;
			display: block;
			font-size: 1.2em;
			padding: 0 20px;
			border: 1px solid #fff; /*--Gives the bevel look with a 1px white border inside the list item--*/
			outline: none;
		}
		ul.tabs li a:hover {
			background: #ccc;
		}
		html ul.tabs li.active, html ul.tabs li.active a:hover  { /*--Makes sure that the active tab does not listen to the hover properties--*/
			background: #fff;
			border-bottom: 1px solid #fff; /*--Makes the active tab look like it's connected with its content--*/
		}
	</style>
		
	<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>
	<script type="text/javascript" src="../../namoCross/js/namo_scripteditor.js"></script>
		
	<script type="text/javascript" >
		
		/* dom이 생성되면 ready method 실행 */
		$(document).ready(function() {					
			//-----기본정보 탭 활성화
			$("ul.tabs li:first").addClass("active").show();
			
			
			//-----hiddenForm에 있는 SESSION의 협력사 타입
			var vendorTypeCd	=	$("#hiddenForm input[name='vendorTypeCd']").val().replace(/\s/gi, '');
			
			//-----협력사 타입이 06이면 신규 온,오프 상품을 등록할수 없다.
			if (vendorTypeCd == "06") {
				alert("<spring:message code='msg.product.onOff.not.permission'/>");
				history.back(-1);
			}
			
			//상품구분(규격, 패션) 선택
			/* $("#defaultForm select[name=prodDivnCd]").change(function() {
				_eventSetupFieldByProductDivnCode($(this).val().replace(/\s/gi, ''));
			}); */
			
			//-----협력업체 선택시 실행.
			$("#defaultForm select[name=entpCd]").change(function() {										
				var errorNode = $(this).prev("div[name=error_msg]").length;
				if( errorNode > 0 ) { 
					$(this).prev().remove();
				}
				
				if($(this).val().replace(/\s/gi, '') != '' ) {
					
					//거래중지된 업체인지 체크(commonProductFunction.jsp에 함수 있음)
					_eventCheckBlackListVendor($(this).val());	
				}
				
				$("[name=prodTypeDivnCd] > option[value='']").attr("selected", "true"); 
			});
			
			//----- 팀 변경시 이벤트
			$("#defaultForm select[name=teamCd]").change(function() {		
				_eventSelectL1List($(this).val().replace(/\s/gi, ''));
			});
			
			//----- 대분류 변경시 이벤트
			$("#defaultForm select[name=l1Cd]").change(function() {
				//_eventSelectL4List($(this).val().replace(/\s/gi, ''));
				commerceChange($(this).val().replace(/\s/gi, ''));
			});
			
			//-----기본값 설정
			$("#protectTagTypeCode").removeClass("required");
			$("#protectTagTypeCode").parent().prev().find("span.star").hide();
			
			//-----필수 콤보박스 값 검증
			$("select.required").change(function() {
				validateSelectBox($(this)); 
			});
			
			//-----필수 입력항목 검증
			$("input:text.required").blur(function() {	
				if( !$(this).attr("readonly"))
				validateFormTextField($(this));
			});
			
			//-----해당 입력 항목이 값이 있는경우 검증
			$("input:text.requiredIf").blur(function() {
				if( $(this).val().replace(/\s/gi, '') != ""  ) {
					validateTextField($(this));
				}
			});
			
			//radio버튼이 선택되었는지 검증.
			$("input:radio.required").click(function() {				
				validateRadioField($(this));
			});
			
			//상품유형 선택시 선택옵션
			$("select[name=prodTypeDivnCd]").change(function () {
				_eventSetupJang($(this).val().replace(/\s/gi, ''));
			});			
						
			//유통일 관리 여부 변경시
			$("#flowDdMgrCd").change(function() {
				setDayForWarehouse($(this).val().replace(/\s/gi, ''));
			});
			
			//온오프 규격일때만 88코드 입력 가능 
			$("input[name=sellCd]").focus(function() {
				check88SellCodeCondition($(this));
			});
			
			//88코드 관련 필드 검증
			$("input[name=sellCode]").blur(function() {
				if($(this).val().reaplce(/\s/gi, '') != "") {
					validateSellCode($(this));
				}
			});
			
			// 도난방지태그 변경시
			// 미사용일 경우, 도난 방지 태그 유형 값은 선택 못하도록 수정
			// AS IS에도 함수는없어 실행되지 않으나 혹시 몰라 복사만 해놓고 주석처리
			/* $("#protectTagDivnCd").change(function() {
				setProtectTagTypeDivn();
			}); */	
			
			//전상법 select 변경시 액션
			$("select[name=productAddSelectTitle]").change(function() {
				if( $(this).val().replace(/\s/gi, '') != "" ) {
					selectBoxProdTemplateDetailList($(this).val().replace(/\s/gi, ''));
				}									
			});
			
			//KC 인증마크 select 변경시 액션
			$("select[name=productCertSelectTitle]").change(function() {
				if( $(this).val().replace(/\s/gi, '') != "" ) {
					selectBoxCertTemplateDetailList($(this).val().replace(/\s/gi, ''));	
				}								
			});	
			
			
			//----- 상품의 상세정보 SeleceBox, RadioButton ValueSetting.[커스텀태그에서는 시큐어 코딩이 불가능 하여 스크립트에서 처리]
			_eventSetTmpProductDetailValue();			
			
		});
		
		/* 대분류 변경시 이벤트 */
		function commerceChange(val){
			//템플릿 셋팅 , 1값은 온오프 등록, 2값은 온라인 등록
			setupLcdDivnCodeProdAdd(val,'1');
			
			setupLcdDivnCodeCertAdd(val,'1');
			
			//세분류 셋팅
			_eventSelectL4List(val);
		}
		
		/* 상품의 상세정보 SeleceBox ValueSetting.[커스텀태그에서는 시큐어 코딩이 불가능 하여 스크립트에서 처리] */
		function _eventSetTmpProductDetailValue() {					
			var prodDivnCd			=	"<c:out value='${tmpProduct.prodDivnCd}'/>";			//상품구분[1:규격, 5:패션]
			var entpCd				=	"<c:out value='${tmpProduct.entpCd}'/>";				//협력업체
			var teamCd				=	"<c:out value='${tmpProduct.teamCd}'/>";				//팀코드
			var l1Cd				=	"<c:out value='${tmpProduct.l1Cd}'/>";					//대분류
			var l3Cd				=	"<c:out value='${tmpProduct.l3Cd}'/>";					//소분류
			var taxatDivnCd			=	"<c:out value='${tmpProduct.taxatDivnCd}'/>";			//면과세구분
			var dpUnitCd			=	"<c:out value='${tmpProduct.dpUnitCd}'/>";				//표시단위
			var matCd				=	"<c:out value='${tmpProduct.matCd}'/>";					//VIC여부
			var purUnitCd			=	"<c:out value='${tmpProduct.purUnitCd}'/>";				//발주단위코드
			var prodTypeDivnCd		=	"<c:out value='${tmpProduct.prodTypeDivnCd}'/>";		//상품유형코드
			var newProdPromoFg		=	"<c:out value='${tmpProduct.newProdPromoFg}'/>";		//신상품장려금구분
			var overPromoFg			=	"<c:out value='${tmpProduct.overPromoFg}'/>";			//성과초과장려금구분
			var flowDdMgrCd			=	"<c:out value='${tmpProduct.flowDdMgrCd}'/>";			//유통일관리여부
			var npurBuyPsbtDivnCd	=	"<c:out value='${tmpProduct.npurBuyPsbtDivnCd}'/>";		//무발주매입가능구분
			var newProdFirstPurDivnCd	=	"<c:out value='${tmpProduct.newProdFirstPurDivnCd}'/>";		//신규상품초도구분
			var mixYn				=	"<c:out value='${tmpProduct.mixYn}'/>";					//혼재여부
			var totInspYn			=	"<c:out value='${tmpProduct.totInspYn}'/>";				//전수검사여부
			var protectTagDivnCd	=	"<c:out value='${tmpProduct.protectTagDivnCd}'/>";		//도난방지태그구분
			var protectTagTypeCd	=	"<c:out value='${tmpProduct.protectTagTypeCd}'/>";		//도난방지태그유형			
			var ctrTypeDivnCd		=	"<c:out value='${tmpProduct.ctrTypeDivnCd}'/>";			//센터유형구분
			var tmprtDivnCd			=	"<c:out value='${tmpProduct.tmprtDivnCd}'/>";			//온도구분
			var homeCd				=	"<c:out value='${tmpProduct.homeCd}'/>";				//원산지
			var sesnDivnCd			=	"<c:out value='${tmpProduct.sesnDivnCd}'/>";			//계절구분
			var ecoYn				=	"<c:out value='${tmpProduct.ecoYn}'/>";					//친환경인증여부
			var ecoNm				=	"<c:out value='${tmpProduct.ecoNm}'/>";					//친환경인증분류명
			var dlvGa				=	"<c:out value='${tmpProduct.dlvGa}'/>";					//차등배송비여부
			var insCo				=	"<c:out value='${tmpProduct.insCo}'/>";					//별도설치비유무
			
						
			//-----SelectBox Value						
			$("#defaultForm select[name='prodDivnCd']").val(prodDivnCd);
			$("#defaultForm select[name='entpCd']").val(entpCd);
			$("#defaultForm select[name='teamCd']").val(teamCd);
			$("#defaultForm select[name='l1Cd']").val(l1Cd);
			$("#defaultForm select[name='l3Cd']").val(l3Cd);
			$("#defaultForm select[name='taxatDivnCd']").val(taxatDivnCd);
			$("#defaultForm select[name='dpUnitCd']").val(dpUnitCd);
			$("#defaultForm select[name='matCd']").val(matCd);
			$("#defaultForm select[name='purUnitCd']").val(purUnitCd);
			$("#defaultForm select[name='prodTypeDivnCd']").val(prodTypeDivnCd);
			$("#defaultForm select[name='flowDdMgrCd']").val(flowDdMgrCd);
			$("#defaultForm select[name='protectTagDivnCd']").val(protectTagDivnCd);
			$("#defaultForm select[name='protectTagTypeCd']").val(protectTagTypeCd);
			$("#defaultForm select[name='ctrTypeDivnCd']").val(ctrTypeDivnCd);
			$("#defaultForm select[name='tmprtDivnCd']").val(tmprtDivnCd);	
			$("#defaultForm select[name='sesnDivnCd']").val(sesnDivnCd);
			$("#defaultForm select[name='ecoNm']").val(ecoNm);
						
			//-----RadioButton Value
			$(":radio[name='newProdPromoFg']:radio[value='" + newProdPromoFg + "']").attr("checked", true);
			$("#defaultform input[name='hidNewProdPromoFg']").val(newProdPromoFg);
			
			$(":radio[name='overPromoFg']:radio[value='" + overPromoFg + "']").attr("checked", true);
			$("#defaultform input[name='hidOverPromoFg']").val(overPromoFg);
			
			$(":radio[name='npurBuyPsbtDivnCd']:radio[value='" + npurBuyPsbtDivnCd + "']").attr("checked", true);
			$("#defaultform input[name='hidNpurBuyPsbtDivnCd']").val(overPromoFg);
			
			$(":radio[name='newProdFirstPurDivnCd']:radio[value='" + newProdFirstPurDivnCd + "']").attr("checked", true);
			$("#defaultform input[name='hidNewProdFirstPurDivnCd']").val(newProdFirstPurDivnCd);
			
			$(":radio[name='mixYn']:radio[value='" + mixYn + "']").attr("checked", true);
			$("#defaultform input[name='hidMixYn']").val(mixYn);
			
			$(":radio[name='totInspYn']:radio[value='" + totInspYn + "']").attr("checked", true);
			$("#defaultform input[name='hidTotInspYn']").val(totInspYn);
			
			$("#:radio[name='ecoYn']:radio[value='" + ecoYn + "']").attr("checked", true);
			$("#defaultform input[name='hidEcoYn']").val(ecoYn);
			
			$("#:radio[name='dlvGa']:radio[value='" + dlvGa + "']").attr("checked", true);
			$("#defaultform input[name='hidDlvGa']").val(dlvGa);
			
			$("#:radio[name='insCo']:radio[value='" + insCo + "']").attr("checked", true);
			$("#defaultform input[name='hidInsCo']").val(insCo);			
		}
	</script>

</head>

<body>
	<div id="content_wrap">
		<div>				
			<div id="wrap_menu">			
				<!-- 상단 신규상품등록 안내문구와 저장버튼 출력 부분 -------------------------------------------------->
				<div class="wrap_search">									
					<div class="bbs_search">

						<!-- tab 구성---------------------------------------------------------------->
						<ul class="tabs">
						    <li><a href="#tab1"	class="on">기본정보</a></li>
						    <li><a href="#tab2">상품속성</a></li>
						    <li><a href="#tab3">이미지</a></li>
						</ul>
						<!-- tab 구성---------------------------------------------------------------->
					
						<ul class="tit">
							<li class="tit">* 신규상품수정 : <spring:message code="msg.product.onOff.register.notice"/></li>
							<li class="btn">															
								<a href="#" class="btn" onclick="submitProductInfo();"><span><spring:message code="button.common.save" /></span></a>
								<a href="#" class="btn" onclick="copyProductInfo();"><span>복사</span></a>															
							</li>
						</ul>
					</div>
					<!-- 상단 신규상품등록 안내문구와 저장버튼 출력 부분  끝-------------------------------------------------->
					
					
					<!-- 상품정보 입력란 기본정보 시작 --------------------------------------------------------------->
					<form name="defaultForm"		id="defaultForm">		
					
						<input type="hidden" 	name="prodAddMasterCd" 					id="prodAddMasterCd" 	/>
						<input type="hidden" 	name="prodCertMasterCd" 				id="prodCertMasterCd" 	/>												
						<input type="hidden" 	name="tradeType" 						id="tradeType" 			/>
								
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit"><spring:message code="msg.product.onOff.default.notice"/></li>								
							</ul>
							
							
							<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col style="width:15%" />
									<col style="width:30%" />
									<col style="width:17%" />
									<col style="*" />
								</colgroup>
								
								<tr>
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.gubun"/></th>
									<td colspan=3>
										<div>
											<input type="hidden" name="onOffDivnCd"	id="onOffDivnCd" 		value="0" 	/><b><spring:message code="msg.product.onOff.default.onOffDivnCode"/></b>
										</div>
									</td>							
								</tr>
								
								<tr>
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.itemProperty"/></th>
									<td>
										<select name="prodDivnCd"	id="prodDivnCd"	 class="required" 	style="width:150px;">
											<option value="1"><spring:message code="msg.product.onOff.default.item"/></option>
											<option value="5"><spring:message code="msg.product.onOff.default.fashionItem"/></option>
										</select>
									</td>
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.vendor"/></th>
									<td>																													
										<html:codeTag objId="entpCd" objName="entpCd" width="150px;" dataType="CP" comType="SELECT" formName="form" defName="선택"  />																				
									</td>
								</tr>
														
								<tr>							
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.team"/></th>
									<td>
										<select id="teamCd" name="teamCd" class="required" style="width:150px;">
											<option value="">선택</option>
											<c:forEach items="${teamList}" var="teamList" varStatus="index" >
												<option value="<c:out value='${teamList.teamCd}' />"><c:out value="${teamList.teamNm}" /></option> 
											</c:forEach>
										</select>
									</td>
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.class"/></th>
									<td >
										<select id="l1Cd" name="l1Cd" class="required" style="width:150px;">	
											<option value="">선택</option>
											<c:forEach items="${l1GroupList}"	var="l1List"	varStatus="index">
												<option value="<c:out value='${l1List.l1Cd}'/>"><c:out value="${l1List.l1Nm}"/></option>
											</c:forEach>		
																				
										</select>
									</td>
								</tr>
								
								<tr>						
									<th><span class="star" style="display:none">*</span><spring:message code="msg.product.onOff.default.sellCode"/></th>
									<td>
										<input type="text" name="sellCd" 		id="sellCd"		 maxlength="13" style="width:150px;" 	class="sell88Code" 		maxlength="13"		value="<c:out value='${tmpProduct.sellCd}'/>"/>
									</td>
									<th><spring:message code="msg.product.onOff.default.detailClass"/></th>
									<td >
										<select id="l3Cd" name="l3Cd">
											<option value='선택'></option>
											<c:forEach items="${l3GroupList}"		var="l3List"	varStatus="index">
												<option value="<c:out value='${l3List.l3Cd}'/>"><c:out value="${l3List.l3Nm}"/></option>
											</c:forEach>
										</select>
									</td>
								</tr>
								
								<tr>
									<th><spring:message code="msg.product.onOff.default.itemStandard"/></th>
									<td>
										<input type="text" name="prodStandardNm"	id="prodStandardNm"		 maxlength="7" style="width:150px;" 	value="<c:out value='${tmpProduct.prodStandardNm}'/>"/>
									</td>
									
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.dutyfree"/></th>
									<td>
										<html:codeTag attr="class=\"required\"" objId="taxatDivnCd" objName="taxatDivnCd" parentCode="PRD06" width="150px;" comType="SELECT" formName="form" defName="<spring:message code='button.common.choice'/>"  />
									</td>							
								</tr>
								
								<tr>
									<th><spring:message code="msg.product.onOff.default.displayUnit"/></th>
									<td>
										<html:codeTag   objId="dpUnitCd" objName="dpUnitCd" parentCode="PRD17" width="150px;" comType="SELECT" formName="form" defName="<spring:message code='button.common.choice'/>"  />
									</td>								
									<th><span class="star"></span><spring:message code="msg.product.onOff.default.displayStandardQuantity"/></th>
									<td>
										 <input type="text" name="dpBaseQty"	id="dpBaseQty"	maxlength="5" style="width:150px;" class="requiredIf number default0"	value="<c:out value='${dpBaseQty}'/>"/>
									</td>								
								</tr>
								
								<tr>						
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.itemSize"/></th>
									<td colspan="3">
										<div name="productSize">
											<spring:message code="msg.product.onOff.default.width"/> 	<input type="text" name="prodHrznLeng" 		id="prodHrznLeng"		style="width:40px" 		class="required number default0 size"  maxlength="4"	value="<c:out value='${prodHrznLeng}'/>"/>mm&nbsp;
											<spring:message code="msg.product.onOff.default.length"/> 	<input type="text" name="prodVtclLeng" 		id="prodVtclLeng"		style="width:40px"   	class="required number default0 size"  maxlength="4"	value="<c:out value='${prodVtclLeng}'/>"/>mm&nbsp; 
											<spring:message code="msg.product.onOff.default.height"/> 	<input type="text" name="prodHigt" 			id="prodHigt"			style="width:40px"      class="required number default0 size"  maxlength="4"	value="<c:out value='${prodHigt}'/>"/>mm
										</div>  
									</td>
									<!-- th><span class="star">*</span> <spring:message code="msg.product.onOff.default.sellPrice"/></th>
									<td >
										<input type="text" name="sellPrice"		id="sellPrice"	 class="required number" maxlength="8" style="width:150px;" />
									</td -->
								</tr>
								
								<tr>
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.itemNm"/></th>
									<td colspan=3>
										<input type="text" name="prodNm"	id="prodNm" 	class="required"  style="width:390px;" maxlength="50" 	value="<c:out value='${prodNm}'/>"/>
									</td>	
								</tr>
								
								<tr>	
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.shortNm"/></th>
									<td colspan=3>
										<input type="text" name="prodShortNm" 	id="prodShortNm""	class="required" style="width:390px;" maxlength="30" 	value="<c:out value='${prodShortNm}'/>"/>
									</td>
								</tr>
								
								<tr>
									<th><span class="star"><spring:message code="msg.product.onOff.default.dispTotQty"/></span></th>
									<td>
										 <input type="text" name="dispTotQty"		id="dispTotQty" 	maxlength="5"  class="requiredIf number default0" style="width:150px;" 	value="<c:out value='${dispTotQty}'/>"/>
									</td>
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.normalPrice"/></th>
									<td >
										<input type="text" name="norProdSalePrc"	id="norProdSalePrc"	 class="required number range price" maxlength="8" style="width:150px;" 	value="<c:out value='${norProdSalePrc}'/>"/>
									</td>								
								</tr>
								
								<tr>
									<th><span class="star" style="display:none"> *</span><spring:message code="msg.product.onOff.default.prftRate"/></th>
									<td >
										<input type="text" name="prftRate"		id="prftRate"  	class="requiredIf rate"  maxlength="5" style="width:150px;"		value="<c:out value='${prftRate}'/>" 	readonly/>
									</td>
									
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.norProdPcost"/></th>
									<td >
										<input type="text" name="norProdPcost"		id="norProdPcost" 		class="required number range cost" maxlength="9" style="width:150px;" 	value="<c:out value='${norProdPcost}'/>"/>
									</td>	
								</tr>
								
								<tr>
									<th colspan='4'><b><spring:message code="msg.product.onOff.default.normalItemTxt"/><b></th>
								</tr>	
								
								<tr>
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.vicChk"/></th>
									<td>
										<select name="matCd"	id="matCd" 	class="required" style="width:150px;">
											<option value=""><spring:message code="button.common.choice"/>
											<option value="1"><spring:message code="msg.product.onOff.default.exclusiveItem"/>
											<option value="2"><spring:message code="msg.product.onOff.default.exclusiveItemVIC"/>
											<option value="3"><spring:message code="msg.product.onOff.default.exclusiveVIC"/>
										</select>
									<th><span class="star">*</span> <spring:message code="msg.product.onOff.default.vicNormalItemPrice"/></th>
									<td>
										<input type="text" name="wnorProdSalePrc"		id="wnorProdSalePrc" 	class="required number vicrange vicprice" maxlength="8" style="width:150px;" 	value="<c:out value='${wnorProdSalePrc}'/>"/>
									</td>							
								</tr>
								
								<tr>
									<th><span class="star" style="display:none"> *</span><spring:message code="msg.product.onOff.default.wprftRate"/></th>
									<td>
										<input type="text" name="wprftRate"		id="wprftRate"  class="requiredIf vicrate"  maxlength="5" style="width:150px;" readonly		value="<c:out value='${wprftRate}'/>"/>
									</td>							
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.vicNormalCost"/></th>
									<td>
										<input type="text" name="wnorProdPcost"		id="wnorProdPcost"	 class="required number vicrange viccost" maxlength="9" style="width:150px;" 	value="<c:out value='${wnorProdPcost}'/>"/>
									</td>	
								</tr>
								
								<tr>												
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.purInrcntQty"/></th>
									<td >
										<input type="text" maxlength="7" name="purInrcntQty"	id="purInrcntQty" 	style="width:150px;" class="onOffField required number"		value="<c:out value='${purInrcntQty}'/>"/>
									</td>
									<th><span class="star">*</span> <spring:message code="msg.product.onOff.default.purUnitCd"/></th>
									<td>
										<html:codeTag attr="class=\"onOffField required\"" objId="purUnitCd" objName="purUnitCd" parentCode="CSA01" width="150px;" comType="SELECT" formName="form" defName="<spring:message code='button.common.choice'/>"  />
									</td>
								</tr>											
							</table>
						</div>	
						<!-- 상품정보 입력란 기본정보 끝 --------------------------------------------------------------->
					
						<table cellspacing=0 cellpadding="0" border="0" ><tr><td height=5></td></tr></table>
						
						<!-- 추가정보 입력란 시작 -------------------------------------------------------------------->
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit"><spring:message code="msg.product.onOff.default.AdditionalInfo"/></li>
							</ul>
							
							<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col style="width:15%" />
									<col style="width:30%" />
									<col style="width:17%" />
									<col style="*" />
								</colgroup>
								
								<tr>
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.itemType"/> </th>
									<td>
										<html:codeTag attr="class=\"onOffField required\"" objId="prodTypeDivnCd" objName="prodTypeDivnCd" parentCode="PRD09" width="150px;" comType="SELECT" formName="form" defName="<spring:message code='button.common.choice'/>"  />
									</td>						
									<th><spring:message code="msg.product.onOff.default.newItemincentiveApply"/></th>
									<td>
										<div class="jangoption">							
											<input type="radio"  class="onOffField" name='newProdPromoFg'		id="newProdPromoFg"		value="0" checked>	<spring:message code="msg.product.onOff.default.notApply"/>
											<input type="radio"  class="onOffField" name='newProdPromoFg' 		id="newProdPromoFg"		value="1">			<spring:message code="msg.product.onOff.default.apply"/>
											<input type="hidden" 					name="hidNewProdPromoFg" 	id="hidNewProdPromoFg	"/>
										</div>	
										<div class="nojangoption" style="display:none"><spring:message code="msg.product.onOff.default.notUse"/></div>											
									</td>
								</tr>
								
								<tr>
									<th><spring:message code="msg.product.onOff.default.releaseDt"/></th>
									<td>
										<div class="jangoption">
											 <input type="text" maxlength="8" class="requiredIf"  name="newProdStDy" 	id="newProdStDy"			style="width:80px;" readonly	value="<c:out value='${newProdStDy}'/>"/>
											 <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('newProduct.newProdStDy');" 	style="cursor:hand;" /><br><spring:message code="msg.product.onOff.default.whenIncentive"/>
									   </div>	
									   <div class="nojangoption" style="display:none"><spring:message code="msg.product.onOff.default.notUse"/></div>
									</td>					
									<th><spring:message code="msg.product.onOff.default.exceIncentive"/></th>
									<td>
										<div class="jangoption">								
											<input type="radio"  	name='overPromoFg'		id="overPromoFg"	class="onOffField"  value="0" checked><spring:message code="msg.product.onOff.default.notApply"/>
											<input type="radio" 	name='overPromoFg'		id="overPromoFg"	class="onOffField"  value="1"><spring:message code="msg.product.onOff.default.apply"/>
											<input type="hidden" 	name="hidOverPromoFg" 	id="hidOverPromoFg"/>
										</div>	
										<div class="nojangoption" style="display:none"><spring:message code="msg.product.onOff.default.notUse"/></div>							
									</td>					
								</tr>
								
								<tr>
									<th><spring:message code="msg.product.onOff.default.expireDt"/></th>
										<td><input type="hidden" id="maxKeepDayCount" name="maxKeepDayCount" value="0" />
											 <html:codeTag  attr="class=\"onOffField\"" objId="flowDdMgrCd" objName="flowDdMgrCd" parentCode="PRD29" width="150px;" comType="SELECT" formName="form" defName="<spring:message code='button.common.choice'/>" />
										 </td>
										<th><spring:message code="msg.product.onOff.default.possibleDt"/></th>
										<td>
											<div class="flowDate">
												<input type="text"  class="requiredIf number flowDate"  maxlength="2" name='stgePsbtDd'	id="stgePsbtDd"		value="<c:out value='${stgePsbtDd}'/>" />일
											</div>
											<div class="noFlowDate" style="display:none"><spring:message code="msg.product.onOff.default.notUse"/></div>
										</td>								
								</tr>	
								
								<tr>
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.mubaljoo"/></th>
									<td><div>
										<input type="radio"  	name='npurBuyPsbtDivnCd'		id="npurBuyPsbtDivnCd"	class="onOffField required"  value="0"><spring:message code="msg.product.onOff.default.notApply"/>
										<input type="radio" 	name='npurBuyPsbtDivnCd'		id="npurBuyPsbtDivnCd"	class="onOffField required"  value="1"><spring:message code="msg.product.onOff.default.apply"/>
										<input type="hidden" 	name="hidNpurBuyPsbtDivnCd" 	id="hidNpurBuyPsbtDivnCd"/>
										</div>
									</td>
									<!-- 
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.maxKeepDt"/></th>
									<td>
										 <input type="hidden" id="maxKeepDayCount" name="officialOrder.maxKeepDayCount" value="0" />
									</td>
									 -->
									<th><spring:message code="msg.product.onOff.default.releasePossibleDt"/></th>
									<td><div class="flowDate">
										 <input type="text" name="pickPsbtDd" 	id="pickPsbtDd"		maxlength="2"  class="requiredIf number flowDate" 	value="<c:out value='${pickPsbtDd}'/>"/>일
										 </div>
										 <div class="noFlowDate" style="display:none"><spring:message code="msg.product.onOff.default.notUse"/></div>
									</td>
								</tr>
		
								<tr>
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.newItemGbn"/></th>
									<td>
									<div>
									<input type="radio" 	name="newProdFirstPurDivnCd"		id="newProdFirstPurDivnCd"	class="onOffField required" 		 value="0" checked/><spring:message code="msg.product.onOff.default.notApply"/>
									<input type="hidden" 	name="hidNewProdFirstPurDivnCd" 	id="hidNewProdFirstPurDivnCd" value="0"/>
									</div>
									</td>
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.honjaeGbn"/></th>
									<td ><div>
										<input type="radio" 	name="mixYn"	id="mixYn"		class="onOffField required"   value="0"/><spring:message code="msg.product.onOff.default.danItem"/>
										<input type="radio" 	name="mixYn"	id="mixYn"		class="onOffField required"   value="1"/><spring:message code="msg.product.onOff.default.honJaeItem"/>
										<input type="hidden" 	name="hidMixYn" id="hidMixYn"/>
										</div>
									</td>							
								</tr>
								
								<tr>
									<th><spring:message code="msg.product.onOff.default.confirmGbn"/></th>
									<td>
										<input type="radio" 	class="onOffField"  name="totInspYn" 	id="totInspYn" 		value="0" checked /><spring:message code="msg.product.onOff.default.notTarget"/>
										<input type="radio"  	class="onOffField" 	name="totInspYn" 	id="totInspYn"		value="1"/><spring:message code="msg.product.onOff.default.target"/>
										<input type="hidden"	name="hidTotInspYn"		id="hidTotInspTn"		/>	
									</td>
									<th><spring:message code="msg.product.onOff.default.balhangDt"/></th>
									<td >
										 <input type="text" maxlength="8" class="requiredIf"  name="productDay"		id="productDay"	 	style="width:80px;" 	readonly/>
										 <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('newProduct.productDay');" style="cursor:hand;" />
									</td>
								</tr>
								
								<tr>
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.safeTagUseGbn"/></th>
									<td>
										<html:codeTag  attr="class=\"onOffField required\"" objId="protectTagDivnCd" objName="protectTagDivnCd" 	parentCode="ALL14" width="150px;" comType="SELECT" formName="form"   />
									</td>
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.safeTagTypeGbn"/></th>
									<td >
										<html:codeTag  attr="class=\"onOffField required\"" objId="protectTagTypeCd" objName="protectTagTypeCd" parentCode="PRD01" width="150px;" comType="SELECT" formName="form"   />
									</td>
								</tr>
								
								<tr>
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.centerType"/></th>
									<td>
										<html:codeTag attr="class=\"onOffField required\"" objId="ctrTypeDivnCd" objName="ctrTypeDivnCd" parentCode="PRD12" width="150px;" comType="SELECT" formName="form" defName="<spring:message code='button.common.choice'/>"  />  
									</td>
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.temperature"/></th>
									<td >
										<html:codeTag attr="class=\"onOffField required\"" objId="tmprtDivnCd" objName="tmprtDivnCd" parentCode="PRD30" width="150px;" comType="SELECT" formName="form" defName="<spring:message code='button.common.choice'/>"  />
									</td>
								</tr>
								
								<%-- <tr>
									<th><spring:message code="msg.product.onOff.default.priceGbnCd"/></th>
									<td>
										<input type="radio" name="priceIssueDivnCode"	id="priceIssueDivnCode" value="1" /><spring:message code='msg.product.onOff.default.newKyekyuktem'/>
										<input type="radio" name="priceIssueDivnCode" 	id="priceIssueDivnCode"	value="9" /><spring:message code='msg.product.onOff.default.extceptMemberItem'/>
									</td>
																
									<th><spring:message code="msg.product.onOff.default.quantityGbn"/></th>
									<td ><div>
										<input type="radio" name="quantityWeightDivnCode"	id="quantityWeightDivnCode" 	value="0" /><spring:message code="msg.product.onOff.default.radioQuantity"/>
										<input type="radio" name="quantityWeightDivnCode" 	id="quantityWeightDivnCode"		value="1" /><spring:message code="msg.product.onOff.default.radioWeight"/>
										</div>
									</td>
								</tr> --%>
								
								<tr>					
									<th><spring:message code="msg.product.onOff.default.modelNm"/></th>
									<td>
										 <input type="text" maxlength="30" class="requiredIf" id="modelNm" name="id="modelNm"" style="width:150px;" 	value="<c:out value='${tmpProduct.modelNm}'/>"/>
										 
									</td>
									<th><span class="star">*</span><spring:message code="msg.product.onOff.default.country"/></th>
									<td>
										<html:codeTag attr="class=\"required\"" objId="homeCd" objName="homeCd" parentCode="PRD16" width="150px;" comType="SELECT" formName="form" defName="<spring:message code='button.common.choice'/>"  />
									</td>	
								</tr>					
									<th>
										<li  id="seasonAddSelectBoxtitle" name="seasonAddSelectBoxtitle">
											<span class="star">*</span><spring:message code="msg.product.onOff.seasonGbn"/>
										</li>
									</th>										
									<td colspan="3">	
										<li  id="seasonAddSelectBox"	name="seasonAddSelectBox">
											<select id="sesnDivnCd"		name="sesnDivnCd">
												<option value="">선택</option>
												<c:forEach items="${seasonList}" var="seson" varStatus="index" >
													<option value="<c:out value='${seson.styleCd}' />"><c:out value="${seson.styleNm}" /></option> 
												</c:forEach>
											</select>							
										</li>
									</td>									
								</tr>
								
								<tr>
									<th><spring:message code="msg.product.onOff.default.brand"/></th>
									<td>
										 <input type="text" maxlength="30" class="requiredIf" id="brandName" name="brandName" style="width:150px;" 		value="<c:out value='${tmpProduct.brandNm}'/>"/>
										 <input type="hidden" id="brandCode" name="brandCode" 	value="<c:out value='${tmpProduct.brandCd}'/>"/> 
										 <a href="javascript:openBrandPopup();" class="btn" ><span><spring:message code="button.common.choice"/></span></a>
									</td>	
									<th><spring:message code="msg.product.onOff.default.maker"/></th>
									<td >
										 <input type="text" maxlength="30" class="requiredIf"  id="makerName" name="makerName" style="width:150px;" 	value="<c:out value='${tmpProduct.makerNm}'/>"/>
										 <input type="hidden" name="makerCode" id="makerCode"  value="<c:out value='${tmpProduct.makerCd}'/>"/> <a href="javascript:openMakerPopup();" class="btn" ><span><spring:message code="button.common.choice"/></span></a>
									</td>
								</tr>
								
								<tr>						
									<th><spring:message code="msg.product.onOff.default.environment"/></th>
									<td>
										<input type="radio" 	class="onOffField"  name="ecoYn"	id="ecoYn" 		value="0"/>N
										<input type="radio"  	class="onOffField" 	name="ecoYn" 	id="ecoYn"		value="1"/>Y
										<input type="hidden"  	name="hidEcoYn" 	id="hidEcoYn"	/>	
									</td>
									<th><spring:message code="msg.product.onOff.default.ecoNm"/></th>
									<td>
										<html:codeTag   objId="ecoNm" objName="ecoNm" parentCode="PRD08" width="150px;" comType="SELECT" formName="form" defName="선택"  />
									</td>
								</tr>
								
								<tr>
									<th><spring:message code="msg.product.onOff.default.dlvGa"/></th>
									<td>
										<input type="radio" class="onOffField"  name="dlvGa"	id="dlvGa"	 	value="0"/>N
										<input type="radio" class="onOffField" 	name="dlvGa" 	id="dlvGa"		value="1"/>Y	
										<input type="hidden"	name="hidDlvGa"		id="hidDlvGa"	/>
									</td>
									<th><spring:message code="msg.product.onOff.default.insCo"/></th>
									<td>
										<input type="radio" class="onOffField"  name="insCo"	id="insCo" 	value="0"/>N
										<input type="radio" class="onOffField" 	name="insCo" 	id="insCo"	value="1"/>Y
										<input type="hidden"	name="hidInsCo"		id="hidInsCo"	/>	
									</td>
								</tr>
								
								<tr>
									<th><spring:message code="msg.product.onOff.default.dlvDt"/></th>
									<td colspan="3">
										 <input type="text" id="dlvDt" name="dlvDt" style="width:90%;" maxlength="50"	value="<c:out value='${tmpProduct.dlvDt}'/>"/>
									</td>	
								</tr>	
								
								<tr>
									<th><spring:message code="msg.product.onOff.default.ispDetailDescription"/></th>
									<td colspan="3">
										 <input type="text" id="ispDtlDesc" name="ispDtlDesc" style="width:90%;" maxlength="50"		value="<c:out value='${tmpProduct.ispDtlDesc}'/>"/>
									</td>	
								</tr>							
							</table>
						</div>
						<!-- 추가정보 입력란 끝 -------------------------------------------------------------------->		
					
						<!-- 패션상품 color/size 입력란 시작 -------------------------------------------------------->
						<%-- <div>
							<ul name="colorSizeTitle" class="tit mt10" style="display:none">
								<div class="bbs_list">
									<ul class="tit">
										<li class="tit"><spring:message code="msg.product.onOff.default.fashionSizeColor"/><span style="margin-left:20px"><spring:message code="msg.product.onOff.default.fashion88Code"/></span></li>
									</ul>
								</div>
								
								<div class="bbs_search">
									<ul class="tit">
										<li class="tit">
											<spring:message code="msg.product.onOff.default.choiceSize"/> 
											<select name="sizeCategory"		id="sizeCategory">
												<option value=""><spring:message code="button.common.choice"/></option>
												<c:forEach items="${sizeCategoryList}" var="sizeCategory" varStatus="index" >
													<option value="${sizeCategory.categoryCode}">${sizeCategory.categoryName}</option> 
												</c:forEach>
											</select>
										</li>
										<li class="btn">
											<a href="javaScript:addColorSize()" class="btn"><span><spring:message code="button.product.color.app"/></span></a>
										</li>
									</ul>
								</div>
							</ul>
							
							<div name="colorSizeData" class="bbs_search"  style="display:none">
								<table  name="color_size" class="bbs_search" cellpadding="0" cellspacing="0" border="0">
									<colgroup>
										<col />
										<!-- 
										<col />
										 -->
										<col />
										<col />
										<col />
										<col />
										<col />
										<col style="width:70px;"  />								
									</colgroup>
									
									<tr>
										<th rowspan="2">Color </th>
										<!-- 
										<th rowspan="2"><spring:message code="msg.product.onOff.default.sizeGbn"/> </th>
										<th rowspan="2"><spring:message code="msg.product.onOff.default.sizeDel"/></th>
										-->
										<th colspan="5">SIZE </th>
										<th rowspan="2"><spring:message code="msg.product.onOff.default.sizeDel"/></th>
									</tr>
									
									<tr>
										<!-- 
										<th> </th>
										 -->
										<th>Size1 </th>
										<th>Size2 </th>
										<th>Size3 </th>
										<th>Size4 </th>
										<th>Size5 </th>
									</tr>
									
									<tr id="colorSizeRow_1" name="colorSizeRow">
										<td>
											<select name="colorCode">
												<option value=""><spring:message code="button.common.choice"/></option> 
												<c:forEach items="${colorList}" var="color" varStatus="index" >
													<option value="${color.categoryCode}">${color.categoryName}</option> 
												</c:forEach>
											</select>
										</td>
										
										<td>
											<select class="subsize" name="size1"	id="size1">
												<option value=''><spring:message code="button.common.choice"/></option>										
											</select>
										</td>
										<td>
											<select class="subsize" name="size2" 	id="size2">
												<option value='' ><spring:message code="button.common.choice"/></option>
												
											</select>
										</td>
										<td>
											<select class="subsize" name="size3"	id="size3">
												<option value=''><spring:message code="button.common.choice"/></option>
												
											</select>
										</td>
										<td>
											<select class="subsize" name="size4"	id="size4">
												<option value=''><spring:message code="button.common.choice"/></option>
												
											</select>
										</td>
										<td>
											<select class="subsize"  name="size5"	id="size5">
												<option value=''><spring:message code="button.common.choice"/></option>
											
											</select>
											</td>
										<td rowspan=2 width=60>
											<a href="javascript:delColorSize(1)" class="btn"><span><spring:message code="button.common.delete"/></span></a>
										</td>
									</tr>
									
									<tr id="sellCodeRow_1" name="sellCodeRow">
										<td>
											&nbsp;&nbsp;88 CODE
										</td>
										
										<td>
											<input type="text" class="subSellCode" name="sellCode1"		id="sellCode1" 	size="15" maxlength="13"/>
										</td>
										<td>
											<input type="text" class="subSellCode" name="sellCode2" 	id="sellCode1"	size="15" maxlength="13" />
										</td>
										<td>
											<input type="text" class="subSellCode" name="sellCode3" 	id="sellCode3"	size="15" maxlength="13" />
										</td>
										<td>
											<input type="text" class="subSellCode" name="sellCode4" 	id="sellCode4"	size="15" maxlength="13" />
										</td>
										<td>
											<input type="text" class="subSellCode" name="sellCode5" 	id="sellCode5"	size="15" maxlength="13" />
										</td>
										<!-- td><spring:message code="button.common.delete"/></td -->
									</tr>							
								</table>
							</div>					
						</div> --%>
						<!-- 패션상품 color/size 입력란 끝 -------------------------------------------------------->
					
						<!-- 전자상거래 시작 --------------------------------------------------------------------->
						<div>
							<ul name="productAddTemplateTitle"	id="productAddTemplateTitle" class="tit mt10" style="display:none">
								<div class="bbs_list">
									<ul class="tit">
										<li class="tit"><spring:message code="msg.product.onOff.default.eletDeal"/></li> 
										<li class="tit" id="productAddSelectBox"	name="productAddSelectBox" 	style="display:none">
											<html:codeTag objId="productAddSelectTitle" objName="productAddSelectTitle" width="150px;" comType="SELECT" formName="form" defName="<spring:message code='button.common.choice'/>"  />
										</li>
										
									  	<a href="//simage.lottemart.com/lim/static_root/images/epc/Ecom_Manual_v1_0.ppt" class="btn" id="excel"><span><spring:message code="msg.product.onOff.default.eletDealManual"/></span></a>
									</ul>
								</div>
							</ul>
						</div>	
						
						<div name="productAddTemplateData" class="bbs_search"  style="display:none">
							<table  name="data_List" class="bbs_search" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col style="width:35px;"/>
									<col style="width:65px;"/>
								</colgroup>							
							</table>
						</div>						
						<!-- 전자상거래 끝 --------------------------------------------------------------------->		
					
						<!-- KC 인증마크 시작 --------------------------------------------------------------------->
						<div>		
							<ul name="productCertTemplateTitle"	id="productCertTemplateTitle" class="tit mt10" style="display:none">						
								<div class="bbs_list">
									<ul class="tit">
										<li class="tit"><spring:message code="msg.product.onOff.default.KCmark"/></li> 
										<li class="tit" id="productCertSelectBox"	name="productCertSelectBox" 	style="display:none">
											<html:codeTag objId="productCertSelectTitle" objName="productCertSelectTitle" width="150px;" comType="SELECT" formName="form" defName="선택"  />
										</li>
									</ul>						 	 
								</div>
							</ul>	
						</div>
						
						<div name="productCertTemplateData" class="bbs_search"  style="display:none">
							<table  name="cert_List" class="bbs_search" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col style="width:35px;"/>
									<col style="width:65px;"/>
								</colgroup>								
							</table>
						</div>
						<!-- KC 인증마크 종료 --------------------------------------------------------------------->
					
						<table cellspacing=0 cellpadding="0" border="0" ><tr><td height=5></td></tr></table>
					
						<!-- editor 시작 ----------------------------------------------------------------------->
						<div class="bbs_list">
							<ul class="tit">
									<li class="tit">*상품설명 : 온라인에서 사용될 설명이며 <font color='red'><b>필수</b></font>사항입니다.</li>

									<!-- <li class="btn">
										<a href="javascript:doImageSplitterView();" class="btn" ><span>이미지추가</span></a>
									</li> -->	
							</ul>
							<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">		
								<tr>
									<td>
									 	<!--  <DIV id="divShowInstall" style="BORDER-RIGHT: 0px; BORDER-TOP: 0px; Z-INDEX: 0; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px; POSITION: absolute">
											<EMBED src="/edit/images/NamoBanner.swf" width=100% height=525 type=application/x-shockwave-flash></EMBED>
										</Div> -->
										<!-- <script language="javascript" src="/edit/NamoWec7.js"></script> -->
										 <input type="hidden" name="productDescription"	id="productDescription" />
										 
										 <textarea id="pe_agt" name="pe_agt" alt="initText" title="initText" Style="width:730px;height:450px;font-size:10pt"><p>Welcome to <span style="font-weight:bold;">CrossEditor 3.0</span> sample page</p></textarea>
										<script type="text/javascript" language="javascript">
											var CrossEditor = new NamoSE('pe_agt');
											CrossEditor.params.Width = "100%";
											CrossEditor.params.UserLang = "auto";
											CrossEditor.params.FullScreen = false;
											CrossEditor.EditorStart();
											/* function OnInitCompleted(e){
												e.editorTarget.SetBodyValue(document.getElementById("pe_agt").value);
												} */
										</script>										 
									</td>	
								</tr>
							</table>
						</div>	
						<!-- editor 끝 ----------------------------------------------------------------------->
					</form>
				</div>				
			</div>
		</div>
		
		
		<!-- footer 시작 -------------------------------------------------------------------------------------->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="notice"></div>
				<div class="location">
					<ul>
						<li><spring:message code="msg.product.onOff.default.footerHome"/></li>
						<li><spring:message code="msg.product.onOff.default.footerItem"/></li>
						<li><spring:message code="msg.product.onOff.default.footerctrlNewItem"/></li>
						<li class="last"><spring:message code="msg.product.onOff.default.footerRegNewItem"/></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer 끝 -------------------------------------------------------------------------------------->
	</div>
		
	<!-- hidden Form -->
	<form name="hiddenForm"	id="hiddenForm">	
		<input type="hidden"	name="vendorTypeCd"						id="vendorTypeCd"						value="<c:out value='${epcLoginVO.vendorTypeCd}'/>"			/>	
	</form>	
</body>
</html>
