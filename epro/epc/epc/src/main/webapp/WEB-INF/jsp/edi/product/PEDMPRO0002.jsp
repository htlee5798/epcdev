
<%--
- Author(s): 
- Created Date: 2011. 10. 20
- Version : 1.0
- Description : 신규상품 등록 [온오프라인상품]

--%>

<%@include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
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
	<title>신상품 등록</title>
	<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>
	<%@include file="./javascript.jsp" %>
	<script type="text/javascript" >

	if("${epcLoginVO.vendorTypeCd}" == '06' ){
		alert("온,오프 상품은 등록하실수 없습니다.");
	}

	$(function() {

		//협력업체 선택시 실행.
		$("select[name=entpCode]").change(function() {
			
			var errorNode = $(this).prev("div[name=error_msg]").length;
			if( errorNode > 0 ) { 
				$(this).prev().remove();
			}
			
			if( $(this).val() != '' ) 
			checkBlackListVendor();
			$("[name=productTypeDivnCode] > option[value='']").attr("selected", "true");
		});

		//사이즈 분류 값 변경
		$("select[name=sizeCategory]").change(sizeCategorySetup);

		//팀분류
		$("#teamGroupCode").change(selectL1List);

		//대분류 값 변경.
		$("#l1GroupCode").change(commerceChange);		
		
		//필수 콤보박스 값 검증
		$("select.required").change(function() {
		//	alert("콤보");
			validateSelectBox($(this)); 
		});

		//필수 입력항목 검증
		$("input:text.required").blur(function() {
//alert("필수 입력항목 검증");
			//validateTextField($(this));
			if( !$(this).attr("readonly"))
			validateFormTextField($(this));
		});
		

		//해당 입력 항목이 값이 있는경우 검증
		$("input:text.requiredIf").blur(function() {
//alert("해당 입력 항목이 값이 있는경우 검증");
			if( $(this).val() != ''  ) {
				validateTextField($(this));
			}
		});
		

		//radio버튼이 선택되었는지 검증.
		$("input:radio.required").click(function() {
			
			validateRadioField($(this));
		});
		
		

		//상품구분(규격, 패션) 선택
		$("select[name=productDivnCode]").change(setupFieldByProductDivnCode);
		
		//상품유형 선택시 선택옵션
		$("select[name=productTypeDivnCode]").change(setupJang);
		//온라인 대표 상품 코드 변경시 실행
		//$("select[name=onlineProductCode]").change(selectOnlineRepresentProductCode);

		//유통일 관리 여부 변경시
		$("#flowDateManageCode").change(setDayForWarehouse);
		//사이즈 값을 선택했을 경우, 동일한 색상의 동일한 사이즈는 선택할 수 없음. 
		$("select.subsize").change(function() {
			checkSameColorSizePair($(this));
		});		
		$("select.subsize").live("change", function() { checkSameColorSizePair($(this)); });
		
		//색상값 선택 시 사이즈, 88코드값 초기화
		$("select[name=colorCode]").change(function() {
		    $(this).parent().parent().find("select.subsize").val("");
		    $(this).parent().parent().next().find(".subSellCode").val("");
		});

		//온오프 규격일때만 88코드 입력 가능 
		$("input[name=sellCode]").focus(function() {
			check88SellCodeCondition($(this));
		});
		//$("input.subSellCode").live("focus", function() { check88SellCodeCondition($(this)); });
		
		//88코드 관련 필드 검증
		$("input[name=sellCode], input.subSellCode").blur(function() {
			if($(this).val != '') {
				validateSellCode($(this));
			}
		});

		// 도난방지태그 변경시
		// 미사용일 경우, 도난 방지 태그 유형 값은 선택 못하도록 수정
		//$("#protectTagDivnCode").change(setProtectTagTypeDivn);
		
		//기본값 설정
		$("#teamGroupCode").val('00255');
		$("#protectTagTypeCode").removeClass("required");
		$("#protectTagTypeCode").parent().prev().find("span.star").hide();
		
		//전상법 select 변경시 액션
		$("select[name=productAddSelectTitle]").change(function() {
			if( $(this).val() != '' ) 
			//	alert("this");
			//	alert($(this).val());
				selectBoxProdTemplateDetailList($(this).val());
		});
		
		//KC 인증마크 select 변경시 액션
		$("select[name=productCertSelectTitle]").change(function() {
			if( $(this).val() != '' ) 
			//	alert("this");
			//	alert($(this).val());
				selectBoxCertTemplateDetailList($(this).val());
		});		
		
	});

		//상품 등록 폼을 전송하기전에 
		//검증로직을 실행하는 함수.
		//공통 검증 함수인 validateCommon을 실행하고
		//각 필드 별로 필요한 작업 실행. 
		function validateNewProductInfo() {
		
			var validationResult = validateCommon();
			
			//서적 음반일 경우 발행일 필수 입력
			var categoryValue = $("#l1GroupCode").val();
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
			
			var dbDatelength1 = $("input[name=newProdStDy]").val().replace(/-/g, '').replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
			$("input[name=newProdStDy]").val(dbDatelength1);
			var errorLength = $("div[name=error_msg]").length;
			//alert("errorLength : "+errorLength);
			
			if(!validationResult || errorLength > 0	) {
				$("input[name=productDay]").val(originalProductDay);
				alert("필수 속성값들을 모두 입력해주시기 바랍니다.");
				return false;
			} else {
				return true;
			}
		}

		//상품 등록 폼을 전송하기 전에 각 필드의 기본  값 설정. 
		function setupFormFieldDefaultValue() {
			var tempCode = $("#temperatureDivnCode").val();
			$("#tmpDivnCode").val(tempCode);

			var displayTotalQuantity = $("input[name=displayTotalQuantity]").val();
			var displayBaseQuantity = $("input[name=displayBaseQuantity]").val();
			

			if( displayTotalQuantity == '')
			$("input[name=displayTotalQuantity]").val(0);

			if( displayBaseQuantity == '')				
			$("input[name=displayBaseQuantity]").val(0);

			/*
			var maxKeepDay = $("#maxKeepDayCount").val();
			if( maxKeepDay == '' )
			$("#maxKeepDayCount").val(0);
			*/
			
		    var wec = document.Wec;
			$("input[name=productDescription]").val(wec.MIMEValue);
			/*
			if( $(".default0").val() == '') {
				$(".default0").val('0');
			}
			*/
		}

		//상품등록 폼 전송
		function submitProductInfo() {
			alert("2016/4/21부터 신규상품등록  기능 중지를 알려드립니다.");
			return;
			
							
			$("div#content_wrap").block({ message: null ,  showOverlay: false });
						
			 //필드 값 검증.
			if( validateNewProductInfo() ) {

				//유효성 체크 통과
				setupFormFieldDefaultValue();
							
				if($("#entpCode").val() == ""){
					alert('업체선택은 필수입니다.');
					$("#entpCode").focus();
					return;
				}
				
				if(! calByteProd( newProduct.productName      ,50,'상품명',false) ) return;
				if(! calByteProd( newProduct.productShortName ,30,'쇼카드 상품명',false) ) return;	
			
				if(! checkColorSize()) return;

				//장려금 체크
				if(! setupjangCheck()) return;
				
				//전자상거래 validation check
				if(! prodAddValidationCheck()) return;
				
				if(! prodCertValidationCheck()) return;
				
			  
				/////////////////상품설명 validation//////////////
				var wec = document.Wec;
				var trimText = wec.TextValue;
				trimText=trimText.replace(/^\s*/,'').replace(/\s*$/, ''); 

				if(trimText=="" && wec.BodyValue.indexOf('<IMG')=='-1' && wec.BodyValue.indexOf('<img')=='-1' && wec.BodyValue.toUpperCase().indexOf('<IFRAME')=='-1' && wec.BodyValue.toUpperCase().indexOf('<iframe')=='-1'  && wec.BodyValue.toUpperCase().indexOf('<EMBED')=='-1' && wec.BodyValue.toUpperCase().indexOf('<embed')=='-1'){
					alert('상품설명을 필히 입력해주세요. 온라인판매를 위해 정확히 넣어주세요!');
					return;
				}
				///////////////////////////////////////////////
				
				loadingMaskFixPos();
				$("form[name=newProduct]").submit();
			}
			
			 setTimeout(function() {
			 $("div#content_wrap").unblock();
			 hideLoadingMask();
			}, 500);
		}
		
		
		//전자상거래 데이터 셋팅
		function commerceChange(){
			
		
			//템플릿 셋팅 , 1값은 온오프 등록, 2값은 온라인 등록
			setupLcdDivnCodeProdAdd($("#l1GroupCode").val(),'1');
			
			setupLcdDivnCodeCertAdd($("#l1GroupCode").val(),'1');
			
			//세분류 셋팅
			selectL4List();
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
	    }
	</script>

</head>

<body>
	<div id="content_wrap">
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="newProduct" method="post" action="${ctx}/edi/product/PEDMPRO0002.do" enctype="multipart/form-data">
		<input type="hidden" name="blackListVendorFlag" value="n" />
		<input type="hidden" name="prodAddMasterCd" id="prodAddMasterCd" value="" />
		<input type="hidden" name="prodAddCd" id="prodAddCd" value="" />
		<input type="hidden" name="prodAddNm" id="prodAddNm" value="" />
		<input type="hidden" name="prodCertMasterCd" id="prodCertMasterCd" value="" />
		<input type="hidden" name="prodCertCd" id="prodCertCd" value="" />
		<input type="hidden" name="prodCertNm" id="prodCertNm" value="" />		
		<input type="hidden" name="prodAddSelectCount" id="prodAddSelectCount" value="" />
		<!-- 이동빈 추가 -->
		<input type="hidden" name="tradeType" id="tradeType" value="" />
		
		
		<!-- input type="hidden" name="newProductCode" value="${newProductCode }" / -->
		<input type="hidden" name="officialOrder.newProductGeneratedDivnCode" value="EDI" />
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">신규상품등록 : * 표시는 필수 입력항목입니다.</li>
					 	<li class="btn"><font color="blue">* 2016/4/21부터 신규상품등록  기능 중지를 알려드립니다.</font>
					 
					 	<c:choose>
									<c:when test="${paramMap.vendorTypeCd != '06'}">
										<%-- <a href="#" class="btn" onclick="submitProductInfo();"><span><spring:message code="button.common.save"/></span></a> --%>
									</c:when>
						</c:choose>
						</li>
					</ul>
				</div>	
				<!-- 01 : search -->
				<font color='white'><b>PEDMPRO0002.jsp</b></font>
				
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">기본정보</li>
					</ul>
					<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
					
					<!--  이하 hidden 필드는 사용 유무, 용도 정의해야함.  -->
					<input type="hidden" name="eventSellPrice" value="0"/>
					<input type="hidden" name="imgNcnt"  value="${tmpProduct.imgNcnt}" />
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:17%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> 온/오프 상품구분</th>
						
						
						<td colspan=3>
							<div>
								<input type="hidden" name="onOffDivnCode" value="0" /><b>온오프겸용 상품</b>
							</div>
						</td>
						
					</tr>
					<tr>
						<th><span class="star">*</span> 상품속성 </th>
						<td>
							<select name="productDivnCode" class="required" style="width:150px;">
								<option value="1">규격
								<option value="5">패션
							</select>
						</td>
						<th><span class="star">*</span> 협력업체</th>
						<td>
							<c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<input type="text" name="entpCode" id="entpCode" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									<c:when test="${paramMap.vendorTypeCd != '06'}">
										<c:if test="${not empty param.entpCode}">
											<html:codeTag objId="entpCode" objName="entpCode" width="150px;" selectParam="<c:out value='${param.entpCode}' />" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
										</c:if>
										<c:if test="${ empty param.entpCode}">
											<html:codeTag objId="entpCode" objName="entpCode" width="150px;" selectParam="<c:out value='${param.entpCode}' />" dataType="CP" comType="SELECT" formName="form" defName="전체"  />
										</c:if>
									</c:when>
							</c:choose>
						</td>
					</tr>
					<tr>
						
						<th><span class="star">*</span> 팀</th>
						<td>
							<select id="teamGroupCode" name="officialOrder.teamCode" class="required" style="width:150px;">
								<c:forEach items="${teamList}" var="team" varStatus="index" >
									<option value="${team.teamCode}">${team.teamName}</option> 
								</c:forEach>
							</select>
						</td>
						<th><span class="star">*</span> 대분류</th>
						<td >
							<select id="l1GroupCode" name="officialOrder.lGroupCode" class="required" style="width:150px;">
								<c:forEach items="${l1GroupList}" var="l1Group" varStatus="indexL1" >
									<c:if test="${indexL1.index == 0 }">
										<option value=all>전체</option>
									</c:if>
									<option value="${l1Group.teamCode}">${l1Group.teamName}</option> 
								</c:forEach>
							</select>
						</td>
					</tr>
					
					
					
					<tr>
						
						<th><span class="star" style="display:none">*</span>판매(88)/내부</th>
						<td >
							<input type="text" name="sellCode"  maxlength="13" style="width:150px;" class="sell88Code" maxlength="13"/>
						</td>
						<th> 세분류</th>
						<td >
							<select id="l4GroupCode" name="officialOrder.subGroupCode">
								<option value=''></option>
							</select>
						</td>
					</tr>
					
					<tr>
						<th>상품규격</th>
						<td>
							<input type="text" name="productStandardName" maxlength="7" style="width:150px;" />
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
						
						<th><span class="star"></span>표시기준수량</th>
						<td>
							 <input type="text" name="displayBaseQuantity" maxlength="5" style="width:150px;" class="requiredIf number default0"/>
						</td>	
						
					</tr>
					
					<tr>
						
						<th><span class="star">*</span> 상품사이즈</th>
						<td colspan="3">
							<div name="productSize">
								가로 <input type="text" name="productHorizontalLength" style="width:40px" class="required number default0 size"  maxlength="4"/>mm&nbsp;
								세로 <input type="text" name="productVerticalLength" style="width:40px"   class="required number default0 size"  maxlength="4"/>mm&nbsp; 
								높이 <input type="text" name="productHeight" style="width:40px"           class="required number default0 size"  maxlength="4"/>mm
							</div>  
						</td>
						<!-- th><span class="star">*</span> 판매가</th>
						<td >
							<input type="text" name="sellPrice" class="required number" maxlength="8" style="width:150px;" />
						</td -->
					</tr>
					
					
					 
					<tr>
						<th><span class="star">*</span> 상품명</th>
						<td colspan=3>
							<input type="text" name="productName" class="required" value="${newProduct.productName }" style="width:390px;" maxlength="50" />
						</td>	
					</tr>
					<tr>	
						<th><span class="star">*</span> 쇼카드상품명</th>
						<td colspan=3>
							<input type="text" name="productShortName" class="required" style="width:390px;" maxlength="30" />
						</td>
					</tr>
					
					<tr>
					<th><span class="star"></span>표시총량</th>
						<td>
							 <input type="text" name="displayTotalQuantity" maxlength="5"  class="requiredIf number default0" style="width:150px;" />
							 <!-- <input type="text" name="displayTotalQuantity" maxlength="5"  class="required number" style="width:150px;" /> -->
						</td>
						
						
						<th><span class="star">*</span> 정상매가</th>
						<td >
							<input type="text" name="normalProductSalePrice" class="required number range price" maxlength="8" style="width:150px;" />
						</td>
							
					</tr>
					
					<tr>
						<th><span class="star" style="display:none"> *</span> 이익률(%)</th>
						<td >
							<input type="text" name="profitRate"  class="requiredIf rate"  maxlength="5" style="width:150px;" readonly/>
						</td>
						
						<th><span class="star">*</span> 정상원가(vat 제외)</th>
						<td >
							<input type="text" name="normalProductCost" class="required number range cost" maxlength="9" style="width:150px;" />
						</td>	
					</tr>
					
					<tr>
						<th colspan='4'><b>일반상품은 마트전용으로 선택 후  VIC 매가/이익률/원가를  바로 위와  같이 동일하게 기입하세요!<b></th>
					</tr>
					
					
					
			<!-- 이동빈시작 -->		
					
					
			<tr>
						<th><span class="star">*</span> VIC 여부 </th>
						<td>
							<select name="matCd" class="required" style="width:150px;" defName="선택" >
								<option value="">선택
								<option value="1">마트 전용
								<option value="2">마트+VIC마켓 겸용
								<option value="3">VIC마켓 전용
							</select>
						
						
						<th><span class="star">*</span> VIC 정상매가</th>
						<td >
							<input type="text" name="vicnormalProductSalePrice" class="required number vicrange vicprice" maxlength="8" style="width:150px;" />
						</td>
						
					</tr>
					
					<tr>
						<th><span class="star" style="display:none"> *</span> VIC 이익률(%)</th>
						<td >
							<input type="text" name="vicprofitRate"  class="requiredIf vicrate"  maxlength="5" style="width:150px;" readonly/>
						</td>
						
						<th><span class="star">*</span> VIC 정상원가(vat 제외)</th>
						<td >
							<input type="text" name="vicnormalProductCost" class="required number vicrange viccost" maxlength="9" style="width:150px;" />
						</td>	
					</tr>
					
					<tr>		
					
					
					
					<!-- 이동빈끝 -->		
					
					
						
						<th><span class="star">*</span> 발주입수</th>
						<td >
							<input type="text" maxlength="7" name="officialOrder.publishIncrementQuantity" style="width:150px;" class="onOffField required number"/>
						</td>
						<th><span class="star">*</span> 발주단위</th>
						<td >
							<!-- input type="text" name="officialOrder.publishedUnitCode" / -->
							<html:codeTag attr="class=\"onOffField required\"" objId="publishedUnitCode" objName="officialOrder.publishedUnitCode" parentCode="CSA01" width="150px;" comType="SELECT" formName="form" defName="선택"  />
						</td>
					</tr>
					</table>
				</div>
				<!-- 1검색조건 // -->
				<table cellspacing=0 cellpadding="0" border="0" ><tr><td height=5></td></tr></table>
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">추가정보</li>
					</ul>
					<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:17%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> 상품유형 </th>
						<td >
							<html:codeTag attr="class=\"onOffField required\"" objId="productTypeDivnCode" objName="productTypeDivnCode" parentCode="PRD09" width="150px;" comType="SELECT" formName="form" defName="선택"  />
						</td>
						
						<th> 신상품입점장려금적용</th>
						<td>
						<div class="jangoption">							
							<input type="radio"  class="onOffField" name='newProdPromoFg' value="0" checked>미적용
							<input type="radio"  class="onOffField" name='newProdPromoFg' value="1">적용
							<input type="hidden" name="officialOrder.newProdPromoFg" id="newProdPromoFg"/>
						</div>	
						<div class="nojangoption" style="display:none">사용안함</div>											
						</td>
					<tr>
						<th> 신상품 출시일자</th>
						<td>
							<div class="jangoption">
								 <input type="text" maxlength="8" class="requiredIf"  name="newProdStDy" style="width:80px;" readonly/>
								 <img src="/images/bos/layout/btn_cal.gif" class="middle" onClick="openCal('newProduct.newProdStDy');" style="cursor:hand;" /><br>(신상품입점장려금 적용시 필수)
						   </div>	
						   <div class="nojangoption" style="display:none">사용안함</div>
						</td>					
						<th> 성과초과장려금 적용구분</th>
						<td>
							<div class="jangoption">								
								<input type="radio"  class="onOffField" name='overPromoFg' value="0" checked>미적용
								<input type="radio" class="onOffField" name='overPromoFg' value="1">적용
								<input type="hidden" name="officialOrder.overPromoFg" id="overPromoFg"/>
							</div>	
							<div class="nojangoption" style="display:none">사용안함</div>							
						</td>					
					</tr>	
					</tr>
					<tr>
					<th>유통일 관리여부</th>
						<td><input type="hidden" id="maxKeepDayCount" name="officialOrder.maxKeepDayCount" value="0" />
							 <html:codeTag  attr="class=\"onOffField\"" objId="flowDateManageCode" objName="officialOrder.flowDateManageCode" parentCode="PRD29" width="150px;" comType="SELECT" formName="form" defName="선택"  />
						 </td>
						<th> 입고가능일</th>
						<td><div class="flowDate">
							<input type="text"  class="requiredIf number flowDate"  maxlength="2" name='storeWarehouseDay' />일
							</div>
							<div class="noFlowDate" style="display:none">사용안함</div>
						</td>
						
					</tr>
					<tr>
						<th><span class="star">*</span> 무발주 매입구분</th>
						<td><div>
							<input type="radio"  class="onOffField required" name='noPublishedBuyPossibleDivnCode' value="0">미적용
							<input type="radio" class="onOffField required" name='noPublishedBuyPossibleDivnCode' value="1">적용
							<input type="hidden" name="officialOrder.noPublishedBuyPossibleDivnCode" id="noPublishedBuyPossibleDivnCode"/>
							</div>
						</td>
						<!-- 
						<th><span class="star">*</span> 최대보관일수</th>
						<td>
							 <input type="hidden" id="maxKeepDayCount" name="officialOrder.maxKeepDayCount" value="0" />
						</td>
						 -->
						<th> 출고가능일</th>
						<td><div class="flowDate">
							 <input type="text" name="pickupWarehouseDay" maxlength="2"  class="requiredIf number flowDate" />일
							 </div>
							 <div class="noFlowDate" style="display:none">사용안함</div>
						</td>
					</tr>
					
					<tr>
						<th><span class="star">*</span> 신규상품 초도구분</th>
						<td>
						<div>
						<input type="radio" class="onOffField required" name="newProductFirstPublishedDivnCode" value="0" checked/>미적용
						<input type="hidden" name="officialOrder.newProductFirstPublishedDivnCode" id="newProductFirstPublishedDivnCode" value="0"/>
						</div>
						</td>
						<th><span class="star">*</span> 혼재여부</th>
						<td ><div>
							<input type="radio" class="onOffField required"  name="mixYn" value="0"/>단일상품
							<input type="radio" class="onOffField required"  name="mixYn" value="1"/>혼재상품
							<input type="hidden" name="officialOrder.mixYn" id="mixYn"/>
							</div>
						</td>
					</tr>
					
					<tr>
						<th>전수검사여부</th>
						<td>
							<input type="radio" class="onOffField"  name="officialOrder.totalInspectYn"  value="0" checked />미대상
							<input type="radio"  class="onOffField" name="officialOrder.totalInspectYn" value="1"/>대상	
						</td>
						<th>발행일</th>
						<td >
							 <input type="text" maxlength="8" class="requiredIf"  name="productDay" style="width:80px;" readonly/>
							 <img src="/images/bos/layout/btn_cal.gif" class="middle" onClick="openCal('newProduct.productDay');" style="cursor:hand;" />
						</td>
					</tr>
					
					<tr>
						<th><span class="star">*</span> 도난방지TAG사용</th>
						<td>
							<html:codeTag  attr="class=\"onOffField required\"" objId="protectTagDivnCode" objName="officialOrder.protectTagDivnCode" parentCode="ALL14" width="150px;" comType="SELECT" formName="form"   />
						</td>
						<th><span class="star">*</span> 도난방지 TAG유형구분</th>
						<td >
							<html:codeTag  attr="class=\"onOffField required\"" objId="protectTagTypeCode" objName="officialOrder.protectTagTypeCode" parentCode="PRD01" width="150px;" comType="SELECT" formName="form"   />
						</td>
					</tr>
					
					<tr>
						<th><span class="star">*</span> 센터유형</th>
						<td>
							<html:codeTag attr="class=\"onOffField required\"" objId="centerDivnCode" objName="officialOrder.centerDivnCode" parentCode="PRD12" width="150px;" comType="SELECT" formName="form" defName="선택"  />  
						</td>
						<th><span class="star">*</span> 온도구분</th>
						<td >
							<html:codeTag attr="class=\"onOffField required\"" objId="temperatureDivnCode" objName="temperatureDivnCode" parentCode="PRD30" width="150px;" comType="SELECT" formName="form" defName="선택"  />
							<input type="hidden" name="officialOrder.temperatureDivnCode"  id="tmpDivnCode"/>
						</td>
					</tr>
					<!-- 
					<tr>
					<th>가격 발급구분 코드</th>
						<td>
							<input type="radio" name="priceIssueDivnCode" value="1" />신선 규격상품
							<input type="radio" name="priceIssueDivnCode" value="9" />직원에누리제외상품
						</td>
													
						<th>수량/중량구분</th>
						<td ><div>
							<input type="radio" name="officialOrder.quantityWeightDivnCode" value="0" />수량
							<input type="radio" name="officialOrder.quantityWeightDivnCode" value="1" />중량
							</div>
						</td>
					</tr>
					 -->
					<tr>
					
						<th>모델명</th>
						<td>
							 <input type="text" maxlength="30" class="requiredIf" id="modelName" name="officialOrder.modelName" style="width:150px;" />
							 
						</td>
						<th><span class="star">*</span> 원산지</th>
						<td>
							<html:codeTag attr="class=\"required\"" objId="productCountryCode" objName="productCountryCode" parentCode="PRD16" width="150px;" comType="SELECT" formName="form" defName="선택"  />
						</td>
					<!--
						<th>
							<li  id="seasonAddSelectBoxtitle" name="seasonAddSelectBoxtitle" style="display:none">
								<span class="star">*</span> 계절구분
							</li>
						</th>
										
						<td>	
							<li  id="seasonAddSelectBox" style="display:none">
								 <html:codeTag attr="class=\"onOffField\""  objId="seasonDivnCode"  objName="officialOrder.seasonDivnCode"     parentCode="PRD03" width="150px;" comType="SELECT" formName="form" defName="선택"  />							
							</li>
						</td>	
					-->	
						
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
						<th>ISP 상세내용</th>
						<td colspan="3">
							 <input type="text" id="ispDetailDescription" name="officialOrder.ispDetailDescription" style="width:90%;" maxlength="50"/>
						</td>	
					</tr>							
				</table>
			</div>
			<div>		
				<ul name="colorSizeTitle" class="tit mt10" style="display:none">
						
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit">색상/사이즈<span style="margin-left:20px"> 88코드는 온오프 겸용 상품 입력시에만 입력하실 수 있습니다.</span></li>
						</ul>
					</div>
						
					<div class="bbs_search">
						<ul class="tit">
							<li class="tit">
							Size선택 : 
							<select name="sizeCategory">
								<option value="">선택</option>
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
					<!-- 01 : search -->
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
							<th rowspan="2">Size 구분 </th>
							<th rowspan="2">삭제</th>
							-->
							<th colspan="5">SIZE </th>
							<th rowspan="2">삭제</th>
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
									<option value="">선택</option> 
									<c:forEach items="${colorList}" var="color" varStatus="index" >
										<option value="${color.categoryCode}">${color.categoryName}</option> 
									</c:forEach>
								</select>
							</td>
							
							<td>
								<select class="subsize" name="size1">
									<option value=''>선택</option>
									
								</select>
							</td>
							<td>
								<select class="subsize" name="size2" >
									<option value='' >선택</option>
									
								</select>
							</td>
							<td>
								<select class="subsize" name="size3">
									<option value=''>선택</option>
									
								</select>
							</td>
							<td>
								<select class="subsize" name="size4">
									<option value=''>선택</option>
									
								</select>
							</td>
							<td>
								<select class="subsize"  name="size5">
									<option value=''>선택</option>
								
								</select>
								</td>
							<td rowspan=2 width=60>
								<a href="javascript:delColorSize(1)" class="btn"><span><spring:message code="button.common.delete"/></span></a>
							</td>
						</tr>
						
						<tr id="sellCodeRow_1" name="sellCodeRow">
							<td>
								<!-- input type="radio" name="sellFlag" value="on"/>
								판매 -->&nbsp;&nbsp;88코드
							</td>
							
							<td>
								<input type="text" class="subSellCode" name="sellCode1" size="15" maxlength="13"/>
							</td>
							<td>
								<input type="text" class="subSellCode" name="sellCode2" size="15" maxlength="13" />
							</td>
							<td>
								<input type="text" class="subSellCode" name="sellCode3" size="15" maxlength="13" />
							</td>
							<td>
								<input type="text" class="subSellCode" name="sellCode4" size="15" maxlength="13" />
							</td>
							<td>
								<input type="text" class="subSellCode" name="sellCode5" size="15" maxlength="13" />
							</td>
							<!-- td>삭제</td -->
						</tr>
					
						</table>
					</div>
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
				
				<table cellspacing=0 cellpadding="0" border="0" ><tr><td height=5></td></tr></table>
					<div class="bbs_list">
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
			
				<!-- 1검색조건 // -->
				
				
			</div>
			
			
		</div>
		</form>
		<form name="sizeForm" id="sizeForm" method="post" action="${ctx}/edi/product/sizeByCategory.do">
			<input type="hidden" name="sizeCategoryCode" />
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
