<%--
	Page Name 	: NEDMPRO0220Pop.jsp
	Description : 임시보관함에서 물류바코드 등록 화면 팝업 JSP
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2015.11.30		SONG MIN KYO 	최초생성
	2016.01.05		SONG MIN KYO	스크립트 수정
--%>
<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	
	<script>		
		
		var classListCnt	=	"<c:out value='${classListCnt}'/>";		//상품의 등록된 속성 카운트
		var gbn 			= 	"<c:out value='${param.gbn}'/>";		//부모페이지가 임시보관함 리스트 페이지 인지 신상품등록현황 조회 페이지인지 구분[임시보관함 : '', 신상품등록현황조회 : 99]
		
		$(document).ready(function() {		
			//----- Default 설정
			checkedChange('0');			
			
			//-----해당상품의 등록된 속성이 있으면 선택된 속성의 물류바코드를 조회한다.				
			if (classListCnt > 0) {
				_eventSearchImsiProdLogiBcd($("#searchForm select[name='selVariant']").val());
			} else {
				//----- 최초 페이지 로딩시 등록되어있는 물류바코드 조회 ['000'을 주는 이유는 단일상품일 경우는 속성이 없기 때문에 ]
				_eventSearchImsiProdLogiBcd("000");
			}
			
			//----- 속성 콤보박스 체인지 Event..
			$("#selVariant").change(function (){
				_eventSearchImsiProdLogiBcd($(this).val());
			});						
		});	
		
		/* Default 설정 */
		function checkedChange(str) {
			if(str == '0'){
				$("input[name=wInnerIpsu]").attr("disabled", true); 
				$("input[name=wInnerIpsu]").removeClass("required").addClass("inputRead");
				$("input[name=wInnerIpsu]").val("");
				//$("input[name=wUseFg]").val("0");
				
	 		}else{
				$("input[name=wInnerIpsu]").attr("disabled", false); 
				$("input[name=wInnerIpsu]").addClass("required").removeClass("inputRead");
				//$("input[name=wUseFg]").val("1");
			}
		}
		
		/* 혼재 여부 Click Event */
		function MIX_NO_READY(name)
		{
			alert("<spring:message code='msg.product.mixnoready.info'/>");
			$(":radio[name='mixProdFg']:radio[value='0']").attr("checked", true);			
		}
		
		/* 물류바코드 입력 체크 */
		function digitcheck(obj){
			if(!isNumberDu(obj)) {
				obj.value="";
				alert("<spring:message code='msg.number'/>");
				obj.focus();
				return false;
			}						
			return true;
		}
		
		/* 소터에러 사유 Radio Click Event */
		function setRadioValue(obj) {			
			digit_sorter_check(obj); 
		}
		
		/* 소터구분 */
		function digit_sorter_check (obj) {
			
			if (obj.name != "conveyFg") {
				if(!digitcheck(obj)) return;	
			}
			 			
			var width		=	$("input[name='width']").val().replace(/\s/gi, '');			//가로
			var length		=	$("input[name='length']").val().replace(/\s/gi, '');		//세로
			var height		=	$("input[name='height']").val().replace(/\s/gi, '');		//높이
			var wg			=	$("input[name='wg']").val().replace(/\s/gi, '');			//총중량
			var oldSorter	=	$("input[name='sorterFg']").val().replace(/\s/gi, '');		//default로 설정되어 있는 소터구분값
			
			if (obj.name == "wg" || obj.name == "conveyFg"){
				if (width == "") {
					alert("박스체적 가로를 먼저입력해 주세요.");
					$("input[name='wg']").val("");
					$("input[name='width']").focus(); 
					return;	
				}
				
				if (length == "") {
					alert("박스체적 세로를 먼저입력해 주세요.");
					$("input[name='wg']").val("");
					$("input[name='length']").focus(); 
					return;	
				}
				
				if (height == "") {
					alert("박스체적 높이를 먼저입력해 주세요.");
					$("input[name='wg']").val("");
					$("input[name='height']").focus(); 
					return;	
				}	
			}  
			
			
			
			if (width == "" || length == "" || height == "" || wg == "") {
				return;
			}
			
			
			var conveyFg	=	$(":radio[name='conveyFg']:checked").val();
			//-----소터에러사유가 없을때만
			if (conveyFg == "0") {
				
				//----- 가로길이가 225이상이고 850이하일때
				if( parseInt(width) >= 225 && parseInt(width) <= 850) {
					
					//----- 세로길이가 100이상이고 600이하 && 높이가 50이상 500이하
					if (parseInt(length) >= 100 && parseInt(length) <= 600 && parseInt(height) >=50 && parseInt(height) <=500) {
						
						//----- 총중량 무게가 0.5kg이상 30kg 이하
						if (parseInt(wg) >= 0.5 && parseInt(wg) <= 30 ) {							
							$("input[name='sorterFg']").val("1");
							
							if (oldSorter != $("input[name='sorterFg']").val() || obj.name == "wg" || obj.name == "conveyFg") {
								alert("소터구분은 sorter입니다.")
							}
							$("div[id='sorterFgNm']").html("<font color='2f6084'><b>sorter</b></font>");										
						} else {
							$("input[name='sorterFg']").val("0");
							
							if (oldSorter != $("input[name='sorterFg']").val() || obj.name == "wg" || obj.name == "conveyFg") {
								alert("총중량으로 인하여 소터구분은 non-sorter입니다.")
							}
							$("div[id='sorterFgNm']").html("<font color='2f6084'><b>non-sorter</b></font>");	
						}
						
					} else {
						$("input[name='sorterFg']").val("0");
						if (oldSorter != $("input[name='sorterFg']").val() || obj.name == "wg" || obj.name == "conveyFg") {
							alert("세로 및 높이 길이로 인하여 소터구분은 non-sorter입니다.")
						}
						$("div[id='sorterFgNm']").html("<font color='2f6084'><b>non-sorter</b></font>");	
					}
					
				} else {
					$("input[name='sorterFg']").val("0");
					if (oldSorter != $("input[name='sorterFg']").val() || obj.name == "wg" || obj.name == "conveyFg") {
						alert("가로 길이로 인하여 소터구분은 non-sorter입니다.")
					}
					$("div[id='sorterFgNm']").html("<font color='2f6084'><b>non-sorter</b></font>");									
				}
			
			//----- 소터에러사유가 없음이 아닐때	
			} else {
				$("input[name='sorterFg']").val("0");
				if(oldSorter != $("input[name='sorterFg']").val() || obj.name == "wg" || obj.name == "conveyFg") alert("소터에러사유로 인하여 소터구분은 non-sorter입니다.");
				$("div[id='sorterFgNm']").html("<font color='2f6084'><b>non-sorter</b></font>");				
			}
		}
		
		/* 팔레트 */
		function digit_pallet(obj) {
			var totalBox = "";
				
			var innerIpsu		=	$("input[name='innerIpsu']").val().replace(/\s/gi, '');		//가로박스
			var pltLayerQty		=	$("input[name='pltLayerQty']").val().replace(/\s/gi, '');	//세로박스
			var pltHeightQty	=	$("input[name='pltHeightQty']").val().replace(/\s/gi, '');	//높이박스
			
			if(!digitcheck(obj)) return;
			
			if (obj.name == "pltHeightQty") {
				if (innerIpsu == "") {
					alert("가로박스수를 먼저입력해 주세요.");
					$("input[name='innerIpsu']").focus(); 
					return;
				}
				
				if(pltLayerQty == "")
				{
					alert("세로박스수를 먼저입력해 주세요.");
					$("input[name='pltLayerQty']").focus(); 
					return;
				}
			}
			
			if (innerIpsu == "" || pltLayerQty == "" || pltHeightQty == "") {
				return;
			}
			
			totalBox =  parseInt(innerIpsu) * parseInt(pltLayerQty) * parseInt(pltHeightQty);
			//alert("총박수스 : " + total_box);
			
			$("input[name='totalBox']").val(totalBox);
			$("span[id='totalBoxNm']").html("<font color='2f6084'><b>"+totalBox+"</b></font>")		
		}
		
		
		/* 저장 */
		function _eventDoSubmit() {
			
			var paramInfo		=	{};
			
			var pgmId			=	$("#searchForm input[name='pgmId']").val().replace(/\s/gi, '');
			var venCd			=	$("#searchForm input[name='venCd']").val().replace(/\s/gi, '');
			var logiBcd			=	$("#searchForm input[name='logiBcd']").val().replace(/\s/gi, '');
			var width			=	$("#searchForm input[name='width']").val().replace(/\s/gi, '');
			var length			=	$("#searchForm input[name='length']").val().replace(/\s/gi, '');
			var height			=	$("#searchForm input[name='height']").val().replace(/\s/gi, '');
			var wg				=	$("#searchForm input[name='wg']").val().replace(/\s/gi, '');
			//var wUseFg			=	$("#searchForm input[name='wUseFg']").val().replace(/\s/gi, '');
			var wUseFg			=	$(":radio[name='wUseFg']:checked").val();
			var wInnerIpsu		=	$("#searchForm input[name='wInnerIpsu']").val().replace(/\s/gi, '');
			var innerIpsu		=	$("#searchForm input[name='innerIpsu']").val().replace(/\s/gi, '');
			var pltLayerQty		=	$("#searchForm input[name='pltLayerQty']").val().replace(/\s/gi, '');
			var pltHeightQty	=	$("#searchForm input[name='pltHeightQty']").val().replace(/\s/gi, '');
			
			//----- 상품코드가 없을경우 return			
			if (pgmId == "") {
				return;
			}
			
			//-----???????????
			var venCdState = false;			
			if (venCd == "899999" || venCd == "899915" || venCd == "000242") {
				venCdState = true
			}
		
			
			if (logiBcd == "") {
				alert("정상적인 물류바코드를 입력하세요!");
				$("#searchForm input[name='logiBcd']").focus();
				return;
			}
			
			if (width == "") {
				alert("박스체적 [가로]를 입력하세요!");
				$("#searchForm input[name='width']").focus();
				return;
			}
			
			if (length == "") {
				alert("박스체적 [세로]를 입력하세요!");
				$("#searchForm input[name='length']").focus();
				return;
			}
			
			if (height == "") {
				alert("박스체적 [높이]를 입력하세요!");
				$("#searchForm input[name='height']").focus();
				return;
			}
			
			if (wg == "") {
				alert("박스체적 [총중량]을 입력하세요!");
				$("#searchForm input[name='wg']").focus();
				return;
			}
			
			//console.log("wUseFg ==" + wUseFg);
			
			if (wUseFg == "1" && (wInnerIpsu == "" || Number(wInnerIpsu) <= 0)) {
				alert("VIC 박스 개수를  입력하세요!");
				$("#searchForm input[name='wInnerIpsu']").focus();
				return;
			}		
			
			//-----파레트가하나라도 입력되었으면 가로,세로,높이 모두입력
			if (innerIpsu != "" || pltLayerQty != "" || pltHeightQty != "") {
				if (innerIpsu == "" || Number(innerIpsu) <= 0) {
					alert("팔레트값을 입력하실경우 [가로박스수]를 입력하세요!");
					$("#searchForm input[name='innerIpsu']").focus();
					return;
				}
				
				if (pltLayerQty == "" || Number(pltLayerQty) <= 0) {
					alert("팔레트값을 입력하실경우 [세로박스수]를 입력하세요!");
					$("#searchForm input[name='pltLayerQty']").focus();
					return;
				}
				
				if (pltHeightQty == "" || Number(pltHeightQty) <= 0) {
					alert("팔레트값을 입력하실경우 [높이박스수]를 입력하세요!");
					$("#searchForm input[name='pltHeightQty']").focus();
					return;
				}
			}
			
			if (venCdState) {
				var vol_sum = width * height * length;
				if(vol_sum >= 7486500) {
					$("#searchForm input[name='crsdkFg']").val("1");
				}	
			}
			
			
			$("#searchForm").find("input, radio").each(function() {
				if (this.type == "radio") {
					paramInfo[this.name]	=	$(":radio[name='" + this.name + "']:checked").val();	
					
					//console.log(this.name + " == " + $(":radio[name='" + this.name + "']:checked").val());
				} else {
					if (this.name != "searchVenCd" && this.name != "searchSrcmkCd") {
						paramInfo[this.name]	=	$(this).val().replace(/\s/gi, '');	
					}						
				}
				
			});
			
			//----- 상품에 등록된 속성이 있을경우 속성의 variant를 담아주고 아닐경우에는 '000'로 준다.
			if (classListCnt > 0) {
				paramInfo["variant"]	=	$("#searchForm select[name='selVariant']").val().replace(/\s/gi, '');
			} else {
				paramInfo["variant"]	=	"000";
			}
			
			//----- 물류바코드 등록구분에 따라 확정구분
			if (paramInfo["cfgFg"] == "I") {
				paramInfo["logiCfmFg"]		=	"00";			//확정구분[00, 02:심사중, 09:완료]
			} else {
				paramInfo["logiCfmFg"]		=	"02";			//확정구분[00, 02:심사중, 09:완료]
			}
								
			var arrProxyNm				=	["MST0800"];	//RFC Call name[MST0800:신규물류바코드등록및변경 수신]
			paramInfo["arrProxyNm"]		=	arrProxyNm;		//RFC Call NAME....	

			//console.log(paramInfo);
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/insertImsiProdLogiBcd.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					//성공이 아닐경우
					if (data.msg == "DUPL_LOGI_BCD") {
						alert("이미 등록된 물류바코드 입니다 \r\n 물류바코드를 다시 입력해주세요");
						$("input[name='logiBcd']").focus();
						return;
					} else if (data.msg == "RFC_FAIL") {
						alert("저장에 실패하였습니다.");
						return;
					} else {
						alert("저장 되었습니다.");
						
						if (gbn == "99") {
							opener._eventSearch();
							self.close();	
						} else {
							//등록한 물류바코드 재조회
							if (classListCnt > 0) {
								_eventSearchImsiProdLogiBcd($("#searchForm select[name='selVariant']").val());
							} else {
								_eventSearchImsiProdLogiBcd("000");
							}
						}
					}
				}
			});	
			
		}
		
		
		/* 선택된 속성의 물류바코드 조회 */
		function _eventSearchImsiProdLogiBcd(val) {
			$("span").empty();
			
			var paramInfo	=	{};
			
			paramInfo["pgmId"]		=	$("#searchForm input[name='pgmId']").val();
			paramInfo["venCd"]		=	$("#searchForm input[name='venCd']").val();			
			paramInfo["variant"]	=	val;
			
			
			//console.log(paramInfo);
		
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/selectImsiProdLogiBcd.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {														
					var resultVO		=	data.resultVO;		//물류바코드 정보
					var prodBasicInfo	=	data.prodBasicInfo;	//상품 기본정보
					_setTbodyValue(resultVO);	
					_setBasicInfo(prodBasicInfo);
				}
			});		
		}
		
		
		/* 조회 데이터 셋팅 */
		function _setTbodyValue(resultVO, prodBasicInfo) {
			if (resultVO == null) {
				
				//저장버튼 
				$(".btn").show();
				
				//----- 바코드 상태 설정				
				$("#spBarcodeSt").text("등록요망");
				
				//----- 사용여부 설정
				$("#spUseFgTxt").text("사용");
				
				//--소터구분 설정
				$("#searchForm div[id='sorterFgNm']").text("");
				
				//----- 총박스수 설정
				$("#totalBoxNm").text("");
				
				$("#searchForm input[name='logiBcd']").val("");
				$("#searchForm input[name='logiBoxIpsu']").val("1");
				$("#searchForm input[name='regDate']").val("<c:out value='${nowDate}'/>");								
				$("#searchForm input[name='width']").val("");
				$("#searchForm input[name='length']").val("");
				$("#searchForm input[name='height']").val("");
				$("#searchForm input[name='wg']").val("");
				$(":radio[name='mixProdFg']:radio[value='0']").attr("checked", true);
				$(":radio[name='conveyFg']:radio[value='0']").attr("checked", true);
				$("#searchForm input[name='innerIpsu']").val("");
				$("#searchForm input[name='pltLayerQty']").val("");
				$("#searchForm input[name='pltHeightQty']").val("");
				$(":radio[name='wUseFg']:radio[value='0']").attr("checked", true);
				$("#searchForm input[name='wInnerIpsu']").val("");
					
				checkedChange("0");
				
			}else {
				
				if (resultVO.logiCfmFg == "03") {
					$(".btn").show();	
				} else {
					//저장버튼 disabled
					$(".btn").hide();
				}
				
				
				//----- 바코드 상태 설정
				//$("#spBarcodeSt").text("등록요망");
				if (resultVO.logiCfmFg == "00" || resultVO.logiCfmFg == "02") {
					$("#spBarcodeSt").text("심사중");	
				} else if (resultVO.logiCfmFg == "03") {
					$("#spBarcodeSt").text("수정요청");
				} else {
					$("#spBarcodeSt").text("완료");
				}
				
				//----- 사용여부 설정
				if (resultVO.useFg == "1") {
					$("#spUseFgTxt").text("사용");	
				} else {
					$("#spUseFgTxt").text("사용안함");
				}
				
				if (resultVO.sorterFg == "1") {
					$("#searchForm div[id='sorterFgNm']").text("SORTER");
				} else {
					$("#searchForm div[id='sorterFgNm']").text("non-sorter");
				}
				
				//----- 총박스수 설정
				$("#totalBoxNm").text(resultVO.totalBox);
				
				
				//-----input, radio value Settings...
				$("#searchForm input[name='logiBcd']").val(resultVO.logiBcd);
				$("#searchForm input[name='logiBoxIpsu']").val(resultVO.logiBoxIpsu);
				$("#searchForm input[name='regDate']").val(resultVO.regDate);								
				$("#searchForm input[name='width']").val(resultVO.width);
				$("#searchForm input[name='length']").val(resultVO.length);
				$("#searchForm input[name='height']").val(resultVO.height);
				$("#searchForm input[name='wg']").val(resultVO.wg);
				$(":radio[name='mixProdFg']:radio[value='" + resultVO.mixProdFg + "']").attr("checked", true);				
				$(":radio[name='conveyFg']:radio[value='" + resultVO.conveyFg + "']").attr("checked", true);				
				$("#searchForm input[name='innerIpsu']").val(resultVO.innerIpsu);
				$("#searchForm input[name='pltLayerQty']").val(resultVO.pltLayerQty);
				$("#searchForm input[name='pltHeightQty']").val(resultVO.pltHeightQty);
				$(":radio[name='wUseFg']:radio[value='" + resultVO.wUseFg + "']").attr("checked", true);
				$("#searchForm input[name='wInnerIpsu']").val(resultVO.wInnerIpsu);
				
				checkedChange(resultVO.wUseFg);
					
			}
			
		}
		
		/* 상품기본정보 셋팅 */
		function _setBasicInfo(prodBasicInfo) {
			
			//-----판매코드
			if (prodBasicInfo.srcmkCd == null || prodBasicInfo.srcmkCd == '') {
				$("#txtSrcmkCd").append("<font color='red'>88코드를 입력하지 않을시 물류 바코드는 자체 발번됩니다.</font> ");	
			} else {
				$("#txtSrcmkCd").text(prodBasicInfo.srcmkCd);	
			}
			
			//-----상품 기본정보
			var html = "";
			$("#txtBasicInfoRegDt").text(prodBasicInfo.regDt);
			$("#txtBasicInfoSrcmkCd").text(prodBasicInfo.srcmkCd);
			$("#txtBasicInfoProdCd").text(prodBasicInfo.prodCd);
			$("#txtBasicInfoProdNm").text(prodBasicInfo.prodNm);
			$("#txtBasicInfoOrdIpsu").text(setComma(prodBasicInfo.ordIpsu));
			$("#prodCd").val(prodBasicInfo.prodCd);
			$("#srcmkCd").val(prodBasicInfo.srcmkCd);
			
		}
		
	</script>


	<style type="text/css">
		input { width:30px; }
	</style>
</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:<c:out value='${param.widthSize }'/></c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm"		id="searchForm" 	method="post">
				
		<%-- 공통 마스터 Parameter 값 --%>
		<input type="hidden" 	id="venCd"  		name="venCd" 	 	value="<c:out value='${param.venCd}'/>"			/> 
		<input type="hidden" 	id="l1Cd"   		name="l1Cd" 	 	value="<c:out value='${param.l1Cd}'/>" 			/>
		<input type="hidden" 	id="pgmId"			name="pgmId"   		value="<c:out value='${param.pgmId}'/>"			/>
		<input type="hidden" 	id="crsdkFg"		name="crsdkFg" 		value="0">
		<input type="hidden"	id="cfgFg"			name="cfgFg"		value="<c:out value='${param.cfgFg}'/>"			/>	
		<input type="hidden" 	id="prodCd"			name="prodCd" 														/>
		<input type="hidden"	id="srcmkCd"		name="srcmkCd"														/>
		
		<div id="wrap_menu">
		
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">
							물류바코드 등록
							<c:if test="${classListCnt > 0 }">
								<font color="red">(※ 묶음 상품일 경우 속성별로 물류바코드를 등록해주세요.)</font>
							</c:if>
						</li>
						<li class="btn">
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:15%" />										
						<col style="width:20%" />
						<col style="*" />
					</colgroup>
					<tr>
						
						<th>* 협력업체</th>
						<c:if test="${classListCnt <= 0}">
							<td colspan="3">
								<%-- <input type="text" id="searchVenCd" name="searchVenCd" readonly='true' value="<c:out value='${param.venCd}'/>" style="width:50px"/></a> --%>
								<c:out value='${param.venCd}'/>
							</td>
						</c:if>
						
						<c:if test="${classListCnt > 0}">
							<td>								
								<c:out value='${param.venCd}'/>
							</td>
						</c:if>	
						
						
						
						
						<!--등록된 속성이 있을때만 속성선택 박스를 보여준다. -->						
						<c:if test="${classListCnt > 0 }">							
							<th>* 속성</th>
							<td>
								<select name="selVariant"	id="selVariant">
								
									<!-- 신상품 등록현황에서 물류바코드 등록할떄 -->
									<c:if test="${param.gbn eq '99'}">
										<option value="<c:out value='${param.variant}'/>"><c:out value="${varAttNm}"/></option>
									</c:if>
									
									<!-- 임시보관함 리스트에서 물류바코드 등록할떄 -->
									<c:if test="${param.gbn ne '99'}">
										<c:forEach	items="${classList}"	var="list"	varStatus="index">
											<option value="<c:out value='${list.variant}'/>"><c:out value="${list.attNm}"/></option>
										</c:forEach>
									</c:if>									
								</select>
							</td>							
						</c:if>		
						
												
					</tr>
						<th>* 판매(88)/내부 </th>
						<td colspan="3">							
							<span id="txtSrcmkCd"/>									
						</td>
					<tr>
						
					</tr>	
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
			<!--	2 검색내역 	-->
			<div class="wrap_con">
			
			
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">검색내역 </li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0"	id="basicInfoTbl"	name="basicInfoTbl">
					<colgroup>
						<col style="width:100px;"  />
						<col style="width:100px;"  />
						<col style="width:100px;"  />
						<col  />
						<col style="width:100px;"  />
					</colgroup>
					<tr>
						<th>등록일자</th>
						<th>판매(88)코드</th>
						<th>상품코드</th>
						<th>상품명</th>
						<th>입수</th>
					</tr>
					<tbody	id="basicInfoTbody"/>					
						<tr class="r1">
							<td align="center"><span id="txtBasicInfoRegDt"/></td>
							<td align="center"><span id="txtBasicInfoSrcmkCd"/></td>
							<td align="center"><span id="txtBasicInfoProdCd"/></td>
							<td style="padding-left:5px;"><span id="txtBasicInfoProdNm"/></td>
							<td style="padding-right:5px;"align="right"><span id="txtBasicInfoOrdIpsu"/><%-- <fmt:formatNumber value="${tmpBarcodeList.ORD_IPSU}"	 type="number" currencySymbol="" />&nbsp; --%></td>
						</tr>					
					</table>
					
				</div>
			</div>
			
			
			<!-- 물류 바코드 자료 필드 시작 -->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
												
					<ul class="tit" >
						<li class="btn" style="float:right">						
							<div><a href="javascript:_eventDoSubmit();" class="btn" style="width:50px;text-align:center">저장</a></div>
						</li>
					</ul>
				
					<table cellpadding="0" cellspacing="0" border="0" 	width="100%"	>
					    <tr>
							<td>															
								<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">	
								<tr>
									<th colspan="6"><b>&nbsp;물&nbsp;&nbsp; 류&nbsp;&nbsp; 바&nbsp;&nbsp; 코&nbsp;&nbsp; 드&nbsp;&nbsp;  정&nbsp;&nbsp; 보&nbsp;&nbsp;</b></th>
									<th width=100>바코드 상태</th>
									<td width=100 align=center>
										<c:if test="${param.logiCfmFg eq '00'}">
											&nbsp;
											<b><font color="blue"><span id="spBarcodeSt">등록요망</span></font></b>
										</c:if>
										
										<c:if test="${param.logiCfmFg eq '02'}">
											&nbsp;
											<b><font color="blue"><span id="spBarcodeSt">심사중</span></font></b>
										</c:if>
										
										<c:if test="${param.logiCfmFg eq '03'}">
											&nbsp;
											<b><font color="blue"><span id="spBarcodeSt">수정요청</span></font></b>
										</c:if>
										
									</td>
								</tr>
								<tr>
									<th align="center"  width=100>물류바코드</th>
									<td align="left" width=100><input type="text" name="logiBcd"	id="logiBcd" 	style="width: 100px;" maxlength="14" onblur="digitcheck(this)" /></td>
									<th align="center" width=100>물류박스 입수</td>
									<td align="center" width=100><input type="text" class="inputReadOnly" name="logiBoxIpsu"	id="logiBoxIpsu" 	value="1" 	style="width: 80px;" 	readOnly  /></td>
									<th align="center"  width=80>등록날짜</th>
									<td align="center"><input type="text" class="inputReadOnly" name="regDate" 	id="regDate"		value="<c:out value='${nowDate}' />"  style="width: 60px;" readOnly  /></td>
									<th align="center">사용여부</th>
									<td align="center">
										&nbsp; 
										<span id="spUseFgTxt"></span>
										<input type="hidden" name="useFg" value="1">
									</td>
								</tr>
								<tr >
									<th align="center">박스체적 </th>
									<td align="left" style="padding-left:20px" colspan="5">
										
										<font color="red">
											※ 정확한 숫자를 입력하셔야 납품이 가능합니다.
											예)30cm=300mm,1,000g=1kg 
										</font>
										<br>
										가로 : <input type="text" name="width"	id="width"  maxlength="4" style="width:40px;" onblur="digit_sorter_check(this)"  value="" /> mm&nbsp; &nbsp;
										세로 : <input type="text" name="length" 	id="length"	maxlength="4" style="width:40px;" onblur="digit_sorter_check(this)"  value="" /> mm&nbsp; &nbsp;
										높이 : <input type="text" name="height" 	id="height"	maxlength="4" style="width:40px;" onblur="digit_sorter_check(this)"  value="" /> mm&nbsp; &nbsp;
										총중량 : <input type="text" name="wg"   	id="wg"		maxlength="4" style="width:40px;" onblur="digit_sorter_check(this)"  value="" /> kg&nbsp; &nbsp;
										
										
									</td>
									<th>혼재여부</th>
									<td align="center">
										<input type="radio"  name="mixProdFg"	id="mixProdFg" 	value="0" checked >비혼재<br> 
										<input type="radio"  name="mixProdFg" 	id="mixProdFg"	value="1"  onclick="MIX_NO_READY('mixProdFg_new');" >혼재
									</td>
								</tr>
								<tr>
									<th align="center">소터에러사유</th>
									<td style="padding-left:15px" colspan="5">
										<input type="radio" name="conveyFg" 	id="conveyFg"	onClick="setRadioValue(this)" value="0" checked />없음 &nbsp;
										<input type="radio" name="conveyFg" 	id="conveyFg"	onClick="setRadioValue(this)" value="1" />파손가능상품 &nbsp;
										<input type="radio" name="conveyFg" 	id="conveyFg"	onClick="setRadioValue(this)" value="2" />비닐포장제품 &nbsp;
									</td>
									
									<th align="center">소터구분 </th>
									<td align="center"><input type="hidden" name="sorterFg" value="0"><div id=sorterFgNm></div></td>
								</tr>
								<tr>
									<th align="center">팔레트</th>
									<td style="padding-left:20px" colspan="5">
										가로박스수 <input type="text" 	name="innerIpsu" 		id="innerIpsu"		value=""  maxlength="4" onblur="digit_pallet(this)"	style="width:50px;" />&nbsp;&nbsp;&nbsp;
										세로박스수 <input type="text"	name="pltLayerQty" 		id="pltLayerQty"	value=""  maxlength="4" onblur="digit_pallet(this)"	style="width:50px;" />&nbsp;&nbsp;&nbsp;
									        높이박스수 <input type="text" 	name="pltHeightQty" 	id="pltHeightQty"	value=""  maxlength="4" onblur="digit_pallet(this)"	style="width:50px;" />
									</td>
									<th align="center">총박스수</th>
									<td align="center">
										<span id=totalBoxNm></span>
										<input type ="hidden" name="totalBox"	id="totalBox"/>
									</td>
								</tr>
									
								<tr>
									<th align="center">VIC 취급여부</tj>
									<td style="padding-left:20px" >									
										<input type="radio" name="wUseFg"	id="wUseFg" 	value="0"  onclick="javascript:checkedChange('0');" checked/>미취급
										<input type="radio" name="wUseFg" 	id="wUseFg"		value="1"  onclick="javascript:checkedChange('1');" />취급
									<td>
									<th align="center" width=100>VIC 박스 수</td>
									
									<td>
									 	<input type="text" name="wInnerIpsu"	id="wInnerIpsu" 	  maxlength="4" 	onblur="digit_sorter_check(this)"	style="width:50px;" />&nbsp;&nbsp;&nbsp;
										<div class="fl_left"></div>
									</td>
								</tr>		
								</table>								
							</td>
						</tr>
					</table>											
				</div>
			</div>
			<!-- 물류 바코드 자료 필드 끝 -->
		</div>
		</form>
	</div>
</div>

</body>
</html>
