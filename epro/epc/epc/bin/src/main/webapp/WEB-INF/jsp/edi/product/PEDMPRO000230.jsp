
<%--
- Author(s): 
- Created Date: 2011. 10. 20
- Version : 1.0
- Description : 신상품등록  [  온라인전용, 소셜상품  등록페이지 ]

-- Modified by EDSK 2015.11.26
-- 차세대 MD 대응으로 인한 변경사항 수정

--%>
<%@include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ include file="./CommonProductFunction.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Cache-Control" content="no-store"/>
<!-- HTTP 1.0 -->
<meta http-equiv="Pragma" content="no-cache"/>
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>신상품 등록</title>
	<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>
<%@include file="./javascript.jsp"%>
<script type="text/javascript" >

	$(function() {
	    
		$('#md_vali_sellCode').val('1111111111111'); // 기획/복함 상품이나 바코드 없는 상품
 	    
		$('#md_vali_sellCode').focus(function(){
	    	$('#md_vali_sellCode').val('');
	    }); 
	 	
	 	
		if ("${epcLoginVO.vendorTypeCd}" == '06' ){
			
			if ("${epcLoginVO.repVendorId}" != ''){
				checkBlackListVendor1 ("${epcLoginVO.repVendorId}");
			}
		} else {
			
			// 협력업체 선택시 실행.
			if ("${epcLoginVO.repVendorId}" != '') {
				checkBlackListVendor4Online();
			}
		}
		
		$("select[name=entpCode]").change(function() {
			var errorNode = $(this).prev("div[name=error_msg]").length;
			if( errorNode > 0 ) { 
				$(this).prev().remove();
			}
			
			if( $(this).val() != '' ){
				checkBlackListVendor4Online();
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

		// 상품구분(규격, 패션) 선택
		$("select[name=productDivnCode]").change(setupFieldByProductDivnCode);
	
		// 온라인 대표 상품 코드 변경시 실행
		$("select[name=onlineProductCode]").change(selectOnlineRepresentProductCode2);

		// 온라인전용/소셜상품 선택시 실행
		$("input[name=onOffDivnCode]").click(setOnOffDivnCode);

		//88코드 관련 필드 검증
 		//$("input[name=sellCode], input.subSellCode").blur(function() {
		//	if($(this).val != '') {
		//		validateSellCode($(this));
		//	}
		//});
		
		// 기본값 설정
		$("#teamGroupCode").val('00232');
		$("#protectTagTypeCode").removeClass("required");
		$("#protectTagTypeCode").parent().prev().find("span.star").hide();
		
	
		// 소분류 선택 변경 시 
		$("select[id=l4GroupCode]").change(function() {
			$("#l4GroupCode").val($(this).val());
			
			commerceChange($(this).val());		// 전상법 조회
			certChange($(this).val()); 			// KC인증마크 조회
		});
		
		// 전상법 select 변경시 액션
		$("select[name=productAddSelectTitle]").change(function() {
			if( $(this).val() != '' ) 
			      selectBoxProdTemplateDetailList($(this).val());
		});
		
		// KC 인증마크 select 변경시 액션
		$("select[name=productCertSelectTitle]").change(function() {
			
			if( $(this).val() != '' ) 
				selectBoxCertTemplateDetailList($(this).val());
		});		
		
		//----- 계절구분 년도 현재년도로 default
		var nowYear	=	"<c:out value='${nowYear}'/>";			
		$("select[name='sesnYearCd']").val(nowYear);
		
		_eventSelectSesnList($("select[name='sesnYearCd']").val().replace(/\s/gi, ''));			
		
		
		//-----계절년도 체인지 이벤트
		$("select[name='sesnYearCd']").change(function() {
			_eventSelectSesnList($(this).val().replace(/\s/gi, ''));
		});	
	});
	
		/*
			상품 등록 폼을 전송하기전에 검증로직을 실행하는 함수.
			공통 검증 함수인 validateCommon을 실행하고 각 필드 별로 필요한 작업 실행.
	    */
		function validateNewProductInfo() {
			//2016.05.18 위수탁 거래유형 검증 추가
			if ($("#tradeType").val() != '6') {
				alert("위수탁 협력업체로만 상품등록이 가능합니다");
			     return false;
			}
			var validationResult = validateCommon();
			
			// 서적 음반일 경우 발행일 필수 입력
			var categoryValue = $("#l1GroupCode").val();
			
			
			// 2015.11.26 by kmlee 서적 음반인 경우 카테고리가 "40"인지 확인이 필요함.
			if( categoryValue == "40" ) {
				if ( $("input[name=productDay]").val() == '' || 
						$("input[name=productDay]").val().length == 0 )
				{
					showErrorMessage($("input[name=productDay]"));
				}	
				else 
				{
					
					deleteErrorMessageIfExist($("input[name=productDay]"));
				}
			} else {
				deleteErrorMessageIfExist($("input[name=productDay]"));
			}

			var originalProductDay = $("input[name=productDay]").val();
			var dbDatelength = $("input[name=productDay]").val().replace(/-/g, '').replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
			if( dbDatelength != '' || dbDatelength != null )
			$("input[name=productDay]").val(dbDatelength);
			
			var errorLength = $("div[name=error_msg]").length;			
			if(!validationResult ||
					errorLength > 0	) {
				$("input[name=productDay]").val(originalProductDay);
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
			
		    var wec = document.Wec;
			$("input[name=productDescription]").val(wec.MIMEValue);

			
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
				
				if (!prodAddValidationCheck ()) return;
				
				if (!prodCertValidationCheck ()) return;
				
				/////////////////상품설명 validation//////////////
				var wec = document.Wec;
				var trimText = wec.TextValue;
				trimText=trimText.replace(/^\s*/,'').replace(/\s*$/, ''); 

				if(trimText=="" && wec.BodyValue.indexOf('<IMG')=='-1' && wec.BodyValue.indexOf('<img')=='-1' && wec.BodyValue.toUpperCase().indexOf('<IFRAME')=='-1' && wec.BodyValue.toUpperCase().indexOf('<iframe')=='-1'  && wec.BodyValue.toUpperCase().indexOf('<EMBED')=='-1' && wec.BodyValue.toUpperCase().indexOf('<embed')=='-1'){
					alert('상품설명을 필히 입력해주세요. 없으면 임의로 넣어주세요!!');
					return;
				}
				///////////////////////////////////////////////
		
				if(index > 0){
					if($("#optnDesc"+index).val() == "" || $("#rservStkQty"+index).val() == ""){
		  	    		alert("단품 정보를 정확하게 입력하세요");
		  	    		return;
		  	    	}
		
					if($("#optnDesc"+index).val() != "" && $("#optnDesc"+index).val().indexOf(';') > -1){
		  	    		alert(" ';' 을 사용할수 없습니다.");
		  	    		return;
		  	    	}
				}
				
				$("#taxatDivCode").attr("disabled",false);
				
				loadingMaskFixPos();
				$("form[name=newProduct]").submit();
			}
			
			setTimeout(function() {
			 $("div#content_wrap").unblock();
			 hideLoadingMask();
			}, 500);
		}
	
		//카테고리 팝업
	    function doPopupCategory()
	    {
	        var targetUrl = '${ctx}/edi/product/onlineDisplayCategory.do';//01:상품
	        Common.centerPopupWindow(targetUrl, 'epcCategoryPopup', {width : 460, height : 455});
	    }
	 
	    // 카테고리 검색창으로 부터 받은 카테고리 정보 입력
	    function setCategoryInto(asCategoryId, asCategoryNm) // 이 펑션은 카테고리 검색창에서 호출하는 펑션임
	    {
		    // 카테고리를 입력하라는 에러메세지가 있는 경우, 에러 메세지 삭제
	    	deleteErrorMessageIfExist($("input[name=onlineDisplayCategoryCode]"));
	    	
	        var form = document.newProduct;
	        form.asCategoryId.value = asCategoryId;
	        form.asCategoryNm.value = asCategoryNm;
	        
	        selectL4ListForOnline(asCategoryId);
	    }
	 
	    // 카테고리 입력 정보 삭제
	    function doDdeleteCategory()
	    {
	        var form = document.newProduct;
	        form.asCategoryId.value = "";
	        form.asCategoryNm.value = "";
	    }
	    
	    // 전자상거래 데이터 셋팅
		function commerceChange(val){
			//템플릿 셋팅 , 1값은 온오프 등록, 2값은 온라인 등록
			setupLcdDivnCodeProdAdd(val,'2');
		}
	  
		// KC인증마크 데이터 셋팅
		function certChange(val){
			//템플릿 셋팅 , 1값은 온오프 등록, 2값은 온라인 등록
			setupLcdDivnCodeCertAdd(val,'2');
		}

		// ImageSplitter Popup에서 데이터가 전해진다.
	    function addSplitImage(activeSquareMimeValue)
	    {
			var wec = document.Wec;
			var bodyTag = wec.BodyValue;
			wec.MIMEValue = activeSquareMimeValue;
			wec.BodyValue = bodyTag + decodeURI(wec.BodyValue); // base64에서 한글 입력하면 깨진다.
	    }


		 // 팝업으로 ImageSplitter를 띄운다.
	    function doImageSplitterView()
	    {
			window.open("/edit/splitter/ImageSplitter.html", "ImageSplitter", "width="+screen.width+", height="+screen.height+", toolbar=no, menubar=no, resizeable=true, fullscreen=true");
	    }

	    function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
	    	$("#entpCode").val(strVendorId);
	    	
	    	checkBlackListVendor1(strVendorId);
	    }
	</script>

</head>

<body>
	<div id="content_wrap">
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="newProduct" method="post" action="${ctx}/edi/product/PEDMPRO0002.do" enctype="multipart/form-data">
		<input type="hidden" name="blackListVendorFlag" value="n" />
		<input type="hidden" name="l1GroupCode" id="l1GroupCode" value="" />
		<input type="hidden" name="prodAddMasterCd" id="prodAddMasterCd" value="" />		
		<input type="hidden" name="prodAddCd" id="prodAddCd" value="" />
		<input type="hidden" name="prodAddNm" id="prodAddNm" value="" />
		<input type="hidden" name="prodCertMasterCd" id="prodCertMasterCd" value="" />
		<input type="hidden" name="prodCertCd" id="prodCertCd" value="" />
		<input type="hidden" name="prodCertNm" id="prodCertNm" value="" />		
		<input type="hidden" name="prodAddSelectCount" id="prodAddSelectCount" value="" />
		<input type="hidden" name="prodAddL1CategoryId" id="prodAddL1CategoryId" value="" />
		<input type="hidden" name="itemRow" id="itemRow" value="0" />
		<input type="hidden" name="tradeType" id="tradeType" value="" />
		
		<!-- input type="hidden" name="newProductCode" value="${newProductCode }" / -->
		<input type="hidden" name="officialOrder.newProductGeneratedDivnCode" value="NEW_EDI" />
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">신규상품등록 - * 표시는 필수 입력항목입니다.</li>
						<li class="btn">
							<a href="#" class="btn" onclick="submitProductInfo();"><span><spring:message code="button.common.save"/></span></a>
						</li>
					</ul>
				</div>	
				<!-- 01 : search -->
				<div class="bbs_list" style="margin-top:5px">
					<ul class="tit">
						<li class="tit">기본정보</li>												
					</ul>
					<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
					
					<!--  이하 hidden 필드는 사용 유무, 용도 정의해야함.  -->
					<input type="hidden" name="eventSellPrice" value="0"/>
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:17%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> 온/오프 상품구분</th>
						<td><div>
						<c:if test="${empty param.divnCode}">
							<input type="hidden" name="onOffDivnCode" value="1" /><b>온라인 전용상품</b> &nbsp; &nbsp;
						</c:if>
						<c:if test="${not empty param.divnCode}">
							<input type="hidden" name="onOffDivnCode" value="2" /><b>소셜상품</b> &nbsp; &nbsp;
						</c:if>
						

							</div>
						</td>
			           <th><span class="star">*</span>판매(88)/내부</th>
						<td >
							<input type="text" name="sellCode"  maxlength="13" style="width:150px;" class="sell88Code" maxlength="13" readonly/>
						</td>
					</tr>
					
					
					<tr>
						<th><span class="star">*</span> 협력업체</th>
						<td>
							<c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<input type="text" name="entpCode" id="entpCode" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<c:if test="${not empty param.entpCode}">
											<html:codeTag4Online objId="entpCode" objName="entpCode" width="150px;" selectParam="<c:out escapeXml='false' value='${param.entpCode}'/>" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
										</c:if>
										<c:if test="${ empty param.entpCode}">
											<html:codeTag4Online objId="entpCode" objName="entpCode" width="150px;" selectParam="${epcLoginVO.repVendorId}" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
										</c:if>
									</c:when>
							</c:choose>
						</td>
						<th><span class="star">*</span> 상품속성 </th>
						<td>
							<select name="productDivnCode" class="required" style="width:150px;">
								<option value="1" >규격</option>
								<c:if test="${empty param.divnCode}">
									<option value="5">패션</option>
								</c:if>
							</select>
						</td>
					</tr>
					<tr>
						
						
						<th><span class="star">*</span> 카테고리</th>
						<td >
							<div>
							<input type="text" name="onlineDisplayCategoryCode" id="asCategoryId" class="day required" readonly style="width:39%;" />
		                    <input type="text" name="onlineDisplayCategoryName" id="asCategoryNm" class="day required" readonly style="width:39%;" />
		                    <a href="javascript:doPopupCategory();"  ><img src="/images/epc/btn/bt_search.gif" alt="" class="middle" /></a>
		                    </div>
						</td>
						<th><span class="star">*</span> 소분류 </th>  <!-- 2015.11.27 by kmlee 세분류를 소분류로 변경 -->
						<td>
							<select id="l4GroupCode" name="officialOrder.subGroupCode"> 
								<option value=''></option>
							</select>
						</td>
					</tr>
					
					<tr>
						
						<th><span class="star">*</span> 정상매가</th>
						<td >
							<input type="text" name="normalProductSalePrice" class="required number" maxlength="8" style="width:150px;" />
						</td>
						
						<th><span class="star">*</span> 면과세구분</th>
						<td>
							<html:codeTag attr="class=\"required\"" objId="taxatDivCode" objName="taxatDivCode" parentCode="PRD06" width="150px;" comType="SELECT" formName="form" defName="선택"  />
						</td>
					</tr>
					
					<tr>
						<th>표시단위</th>
						<td>
							<html:codeTag   objId="displayUnitCode" objName="displayUnitCode" parentCode="PRD17" width="150px;" comType="SELECT" formName="form" defName="선택"  />
						</td>	
						
						<th>표시기준수량</th>
						<td>
							 <input type="text" name="displayBaseQuantity" maxlength="5" style="width:150px;" class="requiredIf number default0"/>
						</td>	
						
					</tr>
					
					<tr>
						<th>상품규격</th>
						<td>
							<input type="text" name="productStandardName" maxlength="7" style="width:150px;" />
						</td>
						
						
						<th>표시총량</th>
						<td><input type="hidden" name="sellPrice"   />
							 <input type="text" name="displayTotalQuantity" maxlength="5"  class="requiredIf number default0" style="width:150px;" />
						</td>	
						
					</tr>
					
					
					<tr>
						
						<th><span class="star">*</span> 온라인 대표상품코드</th>
						<td colspan="3">
							<div id="onlineRepresentProductTD"></div>
							<select name="onlineProductCode" class="required" style="width:400px;">
								<option />
							</select>
						</td>
					</tr>
					
					<tr name="onLineProductNameRow" >
						<th><span class="star">*</span> 온라인 대표상품명</th>
						<td>
							<input type="text" name="onlineProductName" class="required" maxlength="80" style="width:150px;" readonly/>
						</td>	
						<th>상품사이즈</th>
						<td>
							<div name="productSize">
								가로 <input type="text" name="productHorizontalLength" style="width:40px" class="requiredIf number default0 size"  maxlength="4"/>mm&nbsp;
								세로 <input type="text" name="productVerticalLength"   style="width:40px" class="requiredIf number default0 size"  maxlength="4"/>mm&nbsp; 
								높이 <input type="text" name="productHeight"           style="width:40px" class="requiredIf number default0 size"  maxlength="4"/>mm
							</div>  
						</td>
					</tr>
					
				
				    <tr>				
				    <th><span class="star" >*</span>판매(88)/내부 <br>유효성검사용</th>				
					<td colspan="3">
					<font color=blue>※ 단품구성 상품만 기재 필요, 기획/복합 상품이나 바코드 없는 상품은 "1111111111111"로 기재하십시오.
					<br>※ 롯데마트 매장상품 중복 여부 체크용 바코드(88) 기재란, 숫자만 입력 가능!
					</b></font>
					<br>
							<input type="text" name="md_vali_sellCode"   id="md_vali_sellCode"  style="width:150px;"   class="required number range digit13"  maxlength="13"/>
				    </td>
					</tr>
					
					<tr>
						<th><span class="star">*</span> 상품명</th>
						<td colspan=3>
							<input type="text" name="productName" class="required"  value="${newProduct.productName }" style="width:390px;" maxlength="50" />
						</td>	
					</tr>
					
					
					<tr>
						<th><span class="star">*</span> 이익률(%)</th>
						<td 
							<c:if test="${not empty param.divnCode}">
								colspan="3"
							</c:if>
						>	<input type="hidden" name="normalProductCost" />
							<input type="text"   name="profitRate"  class="required rate"  maxlength="5" style="width:150px;" readonly/>
						</td>
						
						<c:if test="${empty param.divnCode}">
							<th>발행일</th>
							<td>
								 <input type="text" maxlength="8" class="requiredIf"  name="productDay" style="width:80px;" readonly/>
								 <img src="/images/bos/layout/btn_cal.gif" class="middle" onClick="openCal('newProduct.productDay');" style="cursor:hand;" />
							</td>
						</c:if>						
					</tr>
					
					<c:if test="${not empty param.divnCode}">
					<tr>
						
						<th>발행일</th>
						<td >
							 <input type="text" maxlength="8" class="requiredIf"  name="productDay" style="width:80px;" readonly/>
							 <img src="/images/bos/layout/btn_cal.gif" class="middle" onClick="openCal('newProduct.productDay');" style="cursor:hand;" />
						</td>
						
						<th><span class="star">*</span> 배송구분</th>
						<td>
							
<html:codeTag attr="class=\"required\"" objId="socialProductDeliveryCode" objName="socialProductDeliveryCode" dataType="TET" parentCode="PRD33" width="150px;" comType="SELECT" formName="form" defName="선택"  />
							
						</td>
					</tr>
					</c:if>

					
					<tr>
					<th>가격 발급구분 코드</th>
					<td>
					<c:if test="${empty param.divnCode}">
							<input type="radio" name="priceIssueDivnCode" value="0" />비규격
							<input type="radio" name="priceIssueDivnCode" value="1" />규격
							
					</c:if>
						<c:if test="${not empty param.divnCode}">
						<input type="radio" name="priceIssueDivnCode" value="9" checked/>임직원제외						
						</c:if>
						
						</td>
													
						<th>수량/중량구분</th>
						<td ><div>
							<input type="radio" name="officialOrder.quantityWeightDivnCode" value="0" />수량
							<input type="radio" name="officialOrder.quantityWeightDivnCode" value="1" />중량
							</div>
						</td>
					</tr>
					
					<tr>
						<th><span class="star">*</span> 계절구분</th>
						<td colspan=3>							
							<div>
							<select id="sesnYearCd"	  name="sesnYearCd"  class="required">
								<option value="">선택</option>
								<c:forEach items="${seasonYearList}" var="list" varStatus="index" >
									<option value="${list.yearCd}">${list.yearNm}</option> 
								</c:forEach>
							</select>
							<select id="sesnDivnCd"	   name="sesnDivnCd"	class="required">
								<option value="">선택</option>											
							</select>
							</div>							
						</td>
					</tr>
					
					<tr>
						<th>모델명</th>
						<td>
							 <input type="text" maxlength="30" class="requiredIf" id="modelName" name="officialOrder.modelName" style="width:150px;" />							 
						</td>
						<th><span class="star">*</span> 원산지</th>
						<td>
							<html:codeTag attr="class=\"required\"" objId="productCountryCode" objName="productCountryCode" parentCode="PRD16" width="150px;" comType="SELECT" formName="form" defName="선택"  />
						</td>
					</tr>
					
					<tr>
						<th>브랜드</th>
						<td>
							 <input type="text" maxlength="30" class="requiredIf" id="brandName" name="officialOrder.brandName" style="width:150px;" />
							 <input type="hidden" id="brandCode" name="officialOrder.brandCode" /> <a href="javascript:openBrandPopup();" class="btn" ><span><spring:message code="button.common.choice"/></span></a>
						</td>	
						<th>메이커</th>
						<td >
							 <input type="text" maxlength="30" class="requiredIf"  id="makerName" name="officialOrder.makerName" style="width:150px;" />
							 <input type="hidden" name="officialOrder.makerCode" id="makerCode"  /> <a href="javascript:openMakerPopup();" class="btn" ><span><spring:message code="button.common.choice"/></span></a>
						</td>
					</tr>
					
					<tr>
						
						<th>친환경인증여부</th>
						<td>
							<input type="radio" class="onOffField"  name="officialOrder.ecoYn" value="0"/>N
							<input type="radio"  class="onOffField" name="officialOrder.ecoYn" value="1"/>Y	
						</td>
						<th>친환경인증분류명</th>
						<td>
							<html:codeTag   objId="ecoNm" objName="officialOrder.ecoNm" parentCode="PRD08" width="150px;" comType="SELECT" formName="form" defName="선택"  />
						</td>
						
					
					</tr>
				
					<tr>
						<th>차등배송비여부</th>
						<td>
							<input type="radio" class="onOffField"  name="officialOrder.dlvgaYn" value="0"/>N
							<input type="radio" class="onOffField" name="officialOrder.dlvgaYn" value="1"/>Y	
						</td>
			
							
						<th>별도설치비유무</th>
						<td>
							<input type="radio" class="onOffField"  name="officialOrder.inscoYn" value="0"/>N
							<input type="radio" class="onOffField" name="officialOrder.inscoYn" value="1"/>Y	
						</td>
					</tr>
			 	
					<tr>
						<th>차등배송비내용</th>
						<td colspan="3">
							 <input type="text" id="dlvDt" name="officialOrder.dlvDt" style="width:90%;" maxlength="50"/>
						</td>	
					</tr>
					
					<tr>
						<th><span class="star">*</span> 최소주문가능량</th>
						<td>
							 <input type="text" maxlength="5"  name="officialOrder.minimumOrderQuantity" style="width:150px;" class="required number range minimum"/>
						</td>	
						<th><span class="star">*</span> 최대주문가능량</th>
						<td >
							 <input type="text"  maxlength="5" name="officialOrder.maximumOrderQuantity"  style="width:150px;" class="required number range maximum" />
						</td>
					</tr>
					
				</table>
				
			</div>
			
			<div class="bbs_list" style="margin-top:5px">
				
				<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:15%" />
						<col style="*" />
					</colgroup>
					<tr>	
						<th>단품정보<a href="javascript:fnItemAdd('new')" id="addItem" class="btn" ><span>추가</span></a></th>
						<td id="itemTd">	
							
						</td>
					</tr>
				</table>	
			</div>
				
			<div>		
					<ul name="productAddTemplateTitle" class="tit mt10" style="display:none">
							
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">전자 상거래</li> 
								<li class="tit" id="productAddSelectBox" style="display:none">
									<html:codeTag objId="productAddSelectTitle" objName="productAddSelectTitle" width="150px;" comType="SELECT" formName="form" defName="선택"  />
								</li>
								
							  	<a href="//simage.lottemart.com/lim/static_root/images/epc/Ecom_Manual_v1_0.ppt" class="btn" id="excel"><span>전자상거래법 메뉴얼</span></a>
						
							
							</ul>
						 	 
						</div>
						
						</div>
					</ul>
				
					<div name="productAddTemplateData" class="bbs_search"  style="display:none">
						<table  name="data_List" class="bbs_search" cellpadding="0" cellspacing="0" border="0">
							<colgroup>
								<col style="width:35px;"/>
								<col style="width:65px;"/>
							</colgroup>
							
						</table>
					</div>
				</div>
				
				<div>		
					<ul name="productCertTemplateTitle" class="tit mt10" style="display:none">
							
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit">KC 인증마크</li> 
								<li class="tit" id="productCertSelectBox" style="display:none">
									<html:codeTag objId="productCertSelectTitle" objName="productCertSelectTitle" width="150px;" comType="SELECT" formName="form" defName="선택"  />
								</li>
							</ul>
						 	 
						</div>
						
						</div>
					</ul>
				
					<div name="productCertTemplateData" class="bbs_search"  style="display:none">
						<table  name="cert_List" class="bbs_search" cellpadding="0" cellspacing="0" border="0">
							<colgroup>
								<col style="width:35px;"/>
								<col style="width:65px;"/>
							</colgroup>
							
						</table>
					</div>
				</div>				
				
				
				<!-- 상품 설명 시작 -->
				<div class="bbs_list" style="margin-top:5px">
					<ul class="tit">					
						<li class="tit">*상품설명 : 온라인에서 사용될 설명이며 <font color='red'><b>필수</b></font>사항입니다.</li>
						<li class="btn">
							<a href="javascript:doImageSplitterView();" class="btn" ><span>이미지추가</span></a>
						</li>															 
					</ul>
					<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">		
						<tr>
							<td>
							 	 <DIV id="divShowInstall" style="BORDER-RIGHT: 0px; BORDER-TOP: 0px; Z-INDEX: 0; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px; POSITION: absolute">
									<EMBED src="/edit/images/NamoBanner.swf" width=100% height=525 type=application/x-shockwave-flash></EMBED>
								</Div>
								<script language="javascript" src="/edit/NamoWec7.js"></script>
								 <input type="hidden" name="productDescription" />
							</td>	
						</tr>
					</table>
				</div>	
				<!-- 상품 설명 끝 -->	
			</div>
						
		</div>
		</form>
		
	</div>

	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>상품</li>
					<li>신규상품관리</li>
					<li class="last">신규상품등록</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>

</html>
