<%--
	Page Name 	: NEDMPRO0103.jsp
	Description : 코리안넷 물류바코드 등록 탭 화면
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.02.18 		SONG MIN KYO 	최초생성
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
	
	<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>
	
	<style type="text/css">
		/* TAB */
		.tabs {height:31px; background:#fff;}
		.tabs ul {width:100%; height:31px;}
		.tabs li {float:left; width:130px; height:29px; background:#fff; border:1px solid #ccd0d9; border-radius:2px 2px 0 0; font-size:12px; color:#666; line-height:30px; text-align:center;}
		.tabs li.on {border-bottom:#e7eaef 1px solid; color:#333; font-weight:bold;}
		.tabs li a {display:block; color:#666;}
		.tabs li.on a {color:#333; font-weight:bold;}
	</style>
		
	<script type="text/javascript" >
	
		var classListCnt	=	"<c:out value='${classListCnt}'/>";		//상품의 등록된 속성 카운트
		
		/* dom이 생성되면 ready method 실행 */
		$(document).ready(function() {		
												
			//-----탭 클릭 이벤트
			$("#prodTabs li").click(function() {
				var id = this.id;
				
				var pgmId = $("#hiddenForm input[name='pgmId']").val();
				
				$("#hiddenForm input[name='pgmId']").val(pgmId);
				
				//기본정보 탭
				if (id == "pro01") {
					$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0100Detail.do'/>");			
					$("#hiddenForm").attr("method", "post").submit();

				//속성입력 탭	
				} else if (id == "pro02") {
					if (pgmId == "") {
						alert("상품의 기본정보를 먼저 등록해주세요.");
						return;
					}
					
					$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0101.do'/>");			
					$("#hiddenForm").attr("method", "post").submit();
					
				//이미지 등록 탭	
				} else if (id == "pro03") {					
					if (pgmId == "") {
						alert("상품의 기본정보를 먼저 등록해주세요.");
						return;
					}
					
					$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0102.do'/>");			
					$("#hiddenForm").attr("method", "post").submit();

				//영양성분 탭
				} else if (id == "pro05") {
					if (pgmId == "") {
						alert("<spring:message code='msg.product.tab.mst'/>");
						return;
					}
	
					$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0104.do'/>");
					$("#hiddenForm").attr("method", "post").submit();
				}
			});
			
			//-----필수 입력항목 검증
			$("input:text.required").blur(function() {					
				validateFormTextFieldKoreanNet($(this));
			});
			
			//-----해당 입력 항목이 값이 있는경우 검증
			$("input:text.requiredIf").blur(function() {
				if( $(this).val().replace(/\s/gi, '') != ""  ) {
					validateFormTextFieldKoreanNet($(this));
				}
			});
			
			//-----VIC 박스수 Value 설정..
			checkedChange($(":radio[name='wUseFg']:checked").val());			
		
			//-----해당상품의 등록된 속성이 있으면 선택된 속성의 물류바코드를 조회한다.				
			if (classListCnt > 0) {
				_eventSearchImsiProdLogiBcd($("#defaultFrm select[name='selVariant']").val());
			} else {
				//----- 최초 페이지 로딩시 등록되어있는 물류바코드 조회 ['000'을 주는 이유는 단일상품일 경우는 속성이 없기 때문에 ]
				_eventSearchImsiProdLogiBcd("000");
			}
			
			//----- 속성 콤보박스 체인지 Event..
			$("#selVariant").change(function (){
				_eventSearchImsiProdLogiBcd($(this).val());
			});
		});
		
		/* 선택된 속성의 물류바코드 조회 */
		function _eventSearchImsiProdLogiBcd(val) {
			
			var paramInfo	=	{};
			
			paramInfo["pgmId"]		=	$("#hiddenForm input[name='pgmId']").val();
			paramInfo["venCd"]		=	$("#hiddenForm input[name='entpCd']").val();			
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
					var prodBasicInfo	=	data.prodBasicInfo;	//상품의 기본정보
					_setTbodyValue(resultVO, prodBasicInfo);						
				}
			});		
		}
		
		/* 조회 데이터 셋팅 */
		function _setTbodyValue(resultVO, prodBasicInfo) {
			
			if (resultVO == null) {
				
				//저장버튼 
				$(".btn").show();
				
				$("#defaultFrm div[id='sorterFgNm']").text("");		//소터구분 설정
				$("#defaultFrm span[id='useText']").text("사용");		//소터구분 설정
				
				$("#defaultFrm input[name='regDate']").val("<c:out value='${today}'/>");		//등록날짜
				$("#defaultFrm input[name='totalBox']").val("0");		//총박스수

				$("#defaultFrm input[name='logiBcd']").val("<c:out value='${param.LOGI_BCD}'/>");
				$("#defaultFrm input[name='logiBoxIpsu']").val("1");								
				$("#defaultFrm input[name='width']").val("<c:out value='${param.WIDTH}'/>");
				$("#defaultFrm input[name='length']").val("<c:out value='${param.LENGTH}'/>");
				$("#defaultFrm input[name='height']").val("<c:out value='${param.HEIGHT}'/>");
				$("#defaultFrm input[name='wg']").val("<c:out value='${param.WG}'/>");				
				$(":radio[name='conveyFg']:radio[value='0']").attr("checked", true);				
				$("#defaultFrm input[name='innerIpsu']").val("");
				$("#defaultFrm input[name='pltLayerQty']").val("");
				$("#defaultFrm input[name='pltHeightQty']").val("");
				$(":radio[name='wUseFg']:radio[value='0']").attr("checked", true);
				$("#defaultFrm input[name='wInnerIpsu']").val("	");			
				$("#defaultFrm input[name='totalBox']").val("");		
				
			}else {
				
				//저장버튼 disabled
				$(".btn").hide();
								
				//--소터구분 설정;
				if (resultVO.sorterFg == "1") {
					$("#defaultFrm div[id='sorterFgNm']").text("SORTER");
				} else {
					$("#defaultFrm div[id='sorterFgNm']").text("non-sorter");
				}
				
				//-----input, radio value Settings...
				$("#defaultFrm input[name='logiBcd']").val(resultVO.logiBcd);
				$("#defaultFrm input[name='logiBoxIpsu']").val(resultVO.logiBoxIpsu);
				$("#defaultFrm input[name='regDate']").val(resultVO.regDate);								
				$("#defaultFrm input[name='width']").val(resultVO.width);
				$("#defaultFrm input[name='length']").val(resultVO.length);
				$("#defaultFrm input[name='height']").val(resultVO.height);
				$("#defaultFrm input[name='wg']").val(resultVO.wg);				
				$(":radio[name='conveyFg']:radio[value='" + resultVO.conveyFg + "']").attr("checked", true);				
				$("#defaultFrm input[name='innerIpsu']").val(resultVO.innerIpsu);
				$("#defaultFrm input[name='pltLayerQty']").val(resultVO.pltLayerQty);
				$("#defaultFrm input[name='pltHeightQty']").val(resultVO.pltHeightQty);
				$(":radio[name='wUseFg']:radio[value='" + resultVO.wUseFg + "']").attr("checked", true);
				$("#defaultFrm input[name='wInnerIpsu']").val(resultVO.wInnerIpsu);			
				$("#defaultFrm input[name='totalBox']").val(resultVO.totalBox);		
					
			}		
			$("#defaultFrm input[name='srcmkCd']").val(prodBasicInfo.srcmkCd);		
		}
				
		/* 숫자입력 체크 */
		function _chkDigit(val) {
			var chars = "0123456789.";
			for (var inx = 0; inx < val.length; inx++) {
		       if (chars.indexOf(val.charAt(inx)) == -1)
		           return false;
		    }
		    return true;
		}
		
		
		
		/* 소터구분 */
		function digit_sorter_check () {
						 		
			var width		=	$("input[name='width']").val().replace(/\s/gi, '');			//가로
			var length		=	$("input[name='length']").val().replace(/\s/gi, '');		//세로
			var height		=	$("input[name='height']").val().replace(/\s/gi, '');		//높이
			var wg			=	$("input[name='wg']").val().replace(/\s/gi, '');			//총중량
			var oldSorter	=	$("input[name='sorterFg']").val().replace(/\s/gi, '');		//default로 설정되어 있는 소터구분값
			
			
			if (!_chkDigit(width)) {
				alert("숫자만 입력할 수 있습니다.");
				$("input[name='width']").val("");
				$("input[name='width']").focus(); 
				return false;
			}
			
			if (!_chkDigit(length)) {
				alert("숫자만 입력할 수 있습니다.");
				$("input[name='length']").val("");
				$("input[name='length']").focus(); 
				return false;
			}
			
			if (!_chkDigit(height)) {
				alert("숫자만 입력할 수 있습니다.");
				$("input[name='height']").val("");
				$("input[name='height']").focus(); 
				return false;
			}
			
			if (!_chkDigit(wg))	{
				alert("숫자만 입력할 수 있습니다.");
				$("input[name='wg']").val("");
				$("input[name='wg']").focus(); 
				return false;
			} 
			
			
			/*
			if (width == "") {
				alert("박스체적 가로를 먼저입력해 주세요.");
				$("input[name='wg']").val("");
				$("input[name='width']").focus(); 
				return false; 	
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
				return false;	
			}	
			
			if (wg == "") {
				alert("박스체적 총중량을 먼저 입력해주세요.");
				$("input[name='wg']").val("");
				$("input[name='height']").focus(); 
				return false;	
			}
			
			if (!_chkDigit(width)) {
				alert("숫자만 입력할 수 있습니다.");
				$("input[name='width']").val("");
				$("input[name='width']").focus(); 
				return false;
			}
			
			if (!_chkDigit(length)) {
				alert("숫자만 입력할 수 있습니다.");
				$("input[name='length']").val("");
				$("input[name='length']").focus(); 
				return false;
			}
			
			if (!_chkDigit(height)) {
				alert("숫자만 입력할 수 있습니다.");
				$("input[name='height']").val("");
				$("input[name='height']").focus(); 
				return false;
			}
			
			if (!_chkDigit(wg))	{
				alert("숫자만 입력할 수 있습니다.");
				$("input[name='wg']").val("");
				$("input[name='wg']").focus(); 
				return false;
			} */
			
			
			
			
			
			
			if (width == "" || length == "" || height == "" || wg == "") {
				alert("가로, 세로, 높이, 총중량은 모두 입력하셔야 됩니다.")
				return false;
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
							alert("소터구분은 sorter입니다.");							
							$("div[id='sorterFgNm']").html("<font color='2f6084'><b>sorter</b></font>");										
						} else {
							$("input[name='sorterFg']").val("0");							
							alert("총중량으로 인하여 소터구분은 non-sorter입니다.");							
							$("div[id='sorterFgNm']").html("<font color='2f6084'><b>non-sorter</b></font>");	
						}
						
					} else {
						$("input[name='sorterFg']").val("0");
						alert("세로 및 높이 길이로 인하여 소터구분은 non-sorter입니다.");						
						$("div[id='sorterFgNm']").html("<font color='2f6084'><b>non-sorter</b></font>");	
					}
					
				} else {
					$("input[name='sorterFg']").val("0");
					alert("가로 길이로 인하여 소터구분은 non-sorter입니다.");					
					$("div[id='sorterFgNm']").html("<font color='2f6084'><b>non-sorter</b></font>");									
				}
			
			//----- 소터에러사유가 없음이 아닐때	
			} else {
				$("input[name='sorterFg']").val("0");
				alert("소터에러사유로 인하여 소터구분은 non-sorter입니다."); 				
				$("div[id='sorterFgNm']").html("<font color='2f6084'><b>non-sorter</b></font>");
			}
			
			return true;
		} 
		
		/* 물류바코드 입력 체크 */
		function digitcheck(obj){
			if(!isNumberDu(obj)) {
				obj.val("");
				alert("<spring:message code='msg.number'/>");
				obj.focus();
				return false;
			}						
			return true;
		}
		
		//
		/* 물류박스 입수 입력체크 */
		function validateFormTextFieldKoreanNet(jqueryTextFieldObj) {			
			if(jqueryTextFieldObj.val() != '') {
				validateTextField(jqueryTextFieldObj);
			}
		}
		
		/* Default 설정 */
		function checkedChange(str) {
			if(str == '0'){				
				$("input[name=wInnerIpsu]").val("");
				$(":radio[name='wUseFg']:radio[value='0']").attr("checked", true);
				
	 		}else{				
	 			$(":radio[name='wUseFg']:radio[value='1']").attr("checked", true);
			}
		}
		
		/* VIC 박스수 체크 */
		function digit_sorter_check_winner(obj) 
		{			
			if(!digitcheck(obj)) return;				
		}
		
		/* 저장 */
		function _eventSave() {
			
			var barCodeFlag	=	$("input[name='barCodeFlag']").val().replace(/\s/gi, '');
			var boxFlag		=	$("input[name='boxFlag']").val().replace(/\s/gi, '');
			var logiBcd		=	$("input[name='logiBcd']").val().replace(/\s/gi, '');
			var logiBoxIpsu	=	$("input[name='logiBoxIpsu']").val().replace(/\s/gi, '');
			
			if (barCodeFlag == "") {
				alert("바코드 검증서는 필수 입니다.");
				return;
			}
			
			if (logiBcd == "") {
				alert("물류바코드를 입력해주세요.");
				$("input[name='logiBcd']").focus();
				return;
			}

			
			if (logiBoxIpsu == "") {
				alert("물류박스 입수를 입력해주세요.");
				$("input[name='logiBoxIpsu']").focus();
				return;
			}
			
			setSorterFlag(); 			//바코드 입력을 위한 값 적용
			
			if(!digit_sorter_check()) {	//물류바코드 validation
				return;
			}; 		
			
			if ($(":radio[name='wUseFg']:checked").val() == "1" && !$("input[name='wInnerIpsu']").val().trim()) {
				alert("VIC 사용일때는 박스 개수를 입력하세요!");
				$("input[name='wInnerIpsu']").val("");
				$("input[name='wInnerIpsu']").focus();
			}
			
			if ($(":radio[name='wUseFg']:checked").val() == "0") {			
				$("input[name='wInnerIpsu']").val("0");
			}
			
			
			var paramInfo = {};
			
			$("#defaultFrm").find("input, radio").each(function() {
				if (this.type == "radio") {
					paramInfo[this.name]	=	$(":radio[name='" + this.name + "']:checked").val();						
				} else {
					if (this.name != "barCodeFlag" && this.name != "boxFlag") {
						paramInfo[this.name]	=	$(this).val().replace(/\s/gi, '');	
					}						
				}				
			});
			
			
			//----- 상품에 등록된 속성이 있을경우 속성의 variant를 담아주고 아닐경우에는 '000'로 준다.
			if (classListCnt > 0) {
				paramInfo["variant"]	=	$("#defaultFrm select[name='selVariant']").val().replace(/\s/gi, '');
			} else {
				paramInfo["variant"]	=	"000";
			}
			
			//----- 물류바코드 등록구분에 따라 확정구분
			if (paramInfo["cfgFg"] == "I") {
				paramInfo["logiCfmFg"]		=	"00";			//확정구분[00, 02:심사중, 09:완료]
			} else {
				paramInfo["logiCfmFg"]		=	"02";			//확정구분[00, 02:심사중, 09:완료]
			}
			
			var prodAttTypFg	=	"<c:out value='${prodDetailVO.prodAttTypFg}'/>";
			
			//단일이면 등록된 상품기본정보의 판매코드를 묶음 상품이면 변형속성 저장 테이블에 있는 속성별 판매코드를
			if ( prodAttTypFg == "00" ) {
				paramInfo["srcmkCd"]	=	"<c:out value='${prodDetailVO.sellCd}'/>";
			} else {
				paramInfo["srcmkCd"]	=	$("#defaultFrm input[name='srcmkCd']").val()
			}
								
			var arrProxyNm				=	["MST0800"];	//RFC Call name[MST0800:신규물류바코드등록및변경 수신]
			paramInfo["arrProxyNm"]		=	arrProxyNm;		//RFC Call NAME....	
			paramInfo["venCd"]			=	$("input[name='entpCd']").val();
			paramInfo["cfgFg"]			=	"I";			//신규등록으로..
			paramInfo["logiCfmFg"]		=	"02";			//심사중으로..
			paramInfo["pgmId"]			=	$("input[name='pgmId']").val();
			
			//console.log(paramInfo);
			
			if (!confirm("저장 하시겠습니까?")) {
				return;
			}
			
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/product/insertImsiProdLogiBcd.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					
					if (data.msg == "SUCCESS") {
						alert("저장 되었습니다.");
						
						if (classListCnt > 0) {
							_eventSearchImsiProdLogiBcd($("#defaultFrm select[name='selVariant']").val());
						} else {
							_eventSearchImsiProdLogiBcd("000");
						}
						
					} else if (data.msg == "DUPL_LOGI_BCD") {
						alert("이미 등록된 물류바코드 입니다 \r\n 물류바코드를 다시 입력해주세요");
						$("input[name='logiBcd']").focus();						
					} else {
						alert("저장에 실패하였습니다.");
					}
					
				}
			});	
			
			
		}		
		
		
		//코리안넷에서 바코드 등록시 사용됨.
		function setSorterFlag() {
			var widthB  = parseInt($("input[name=width]").val());
			var lengthB = parseInt($("input[name=length]").val());
			var heightB = parseInt($("input[name=height]").val());
			var wgB     = parseInt($("input[name=wg]").val());
			var entpCode = $("#entpCode").val();
	
	
			var innerIpsu = $("input[name=innerIpsu]").val();
			var pltLayerQty = $("input[name=pltLayerQty]").val();
			var pltHeightQty = $("input[name=pltHeightQty]").val();
			var totalBox = innerIpsu * pltLayerQty * pltHeightQty;
			
			if($(":radio[name=conveyFg]:checked").val() == "0")	//소터에러사유없음 구분
			{
				if( widthB >= 225 &&  widthB <= 850)
				{	if ( lengthB >= 100 && lengthB <= 600 && heightB >=50 && heightB <=500)
					{	if (wgB >= 0.5 && wgB <= 30 )
						$("input[name=sorterFg]").val("1");
					}
				}
			}
	
	
			if( entpCode =="899999" || entpCode =="899915" || entpCode =="000242" ) {
				var vol_sum = widthB * lengthB * heightB;
				if(vol_sum >=7486500) $("input[name=crsdkFg]").val("1");
			}
	
			$("input[name=totalBox]").val(totalBox);
		}
		
	</script>

</head>

<body>
	
	<div id="content_wrap">
		<div>				
			<div id="wrap_menu">			
				<!-- 상단 신규상품등록 안내문구와 저장버튼 출력 부분 -------------------------------------------------->
				<div class="wrap_search">
					
					<!-- tab 구성---------------------------------------------------------------->
						<div id="prodTabs" class="tabs" style="padding-top:10px;">
						<ul>
							<li id="pro01" style="cursor: pointer;">기본정보</li>
							<li id="pro02" style="cursor: pointer;">상품속성</li>
							<li id="pro03" style="cursor: pointer;">이미지</li>
							
							<!-- 단일 상품일 경우 -->														
							<c:if test="${prodDetailVO.prodAttTypFg eq '00'}">
								<!-- 추가정보 입력란의 혼재여부가 단일일 경우에만 물류바코드 탭 disply 표기 -->
								<c:if test="${prodDetailVO.mixYn eq '0'}">
									<li id="pro04" style="cursor: pointer;">물류바코드</li>
								</c:if>
							</c:if>
							
							<!-- 묶음 상품일 경우 -->
							<c:if test="${prodDetailVO.prodAttTypFg eq '01'}">
								<c:if test="${prodDetailVO.inputVarAttCnt > 0}">
									<li id="pro04" style="cursor: pointer;">물류바코드</li>
								</c:if>
							</c:if>		
							
							<li id="pro05" style="cursor: pointer;">영양성분</li>										
						</ul>
					</div>
					<!-- tab 구성---------------------------------------------------------------->
					
					
					<div class="bbs_list" style="margin-top:5px">
						<ul class="tit">
							<li class="tit">물류 바코드 정보</li>
							<li class="btn">																
								<a href="#" class="btn" onclick="_eventSave();"><span><spring:message code="button.common.save" /></span></a>															 																														
							</li>
						</ul>
						
						<form	name="defaultFrm"	id="defaultFrm">
						
							<input type="hidden"	name="srcmkCd"		id="srcmkCd"	/>
						
							<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="1">
							
								<!--등록된 속성이 있을때만 속성선택 박스를 보여준다. -->													
								<c:if test="${classListCnt > 0 }">			
									<tr>
										<th>* 속성</th>
										<td colspan="3">
											<select name="selVariant"	id="selVariant">											
												<c:forEach	items="${classList}"	var="list"	varStatus="index">
													<option value="<c:out value='${list.variant}'/>"><c:out value="${list.attNm}"/></option>
												</c:forEach>					
											</select>
										</td>
									</tr>				
																
								</c:if>		
							
								<tr>
									<th><span class="star">*</span>바코드 검증서</th>
									<td>
										 <input type="text" class="required" id="barCodeFlag" name="barCodeFlag"	id="barCodeFlag" 	value="<c:out value='${param.bcd_fg}'/>" style="width:150px;" maxlength="80" readonly/>
									</td>	
								
									<th>물류 바코드 검증서</th>
									<td>
										 <input type="text"  id="boxFlag" name="boxFlag" value="<c:out value='${param.BOX_FG}'/>" style="width:150px;" maxlength="80" readonly/>
									</td>	
								</tr>
								<tr>
									<th><span class="star">*</span>물류바코드</th>
									<td>
										 <input type="text" name="logiBcd"	id="logiBcd" value="<c:out value='${param.LOGI_BCD}'/>" style="width: 100px;"  maxlength="14" />
									</td>	
								
									<th><span class="star">*</span>물류박스 입수</th>
									<td>
										<input type="text" name="logiBoxIpsu"	id="logiBoxIpsu"	class="required number"  value="1" style="width: 80px;" maxlength="2"  />
									</td>	
								</tr>
								<tr>
									<th><span class="star">*</span>등록날짜</th>
									<td>
										 <input type="text" class="inputReadOnly" readOnly name="regDate"	id="regDate"	 value="<c:out value='${today}'/>"  style="width: 80px;"  />
									</td>	
								
									<th><span class="star">*</span>사용여부</th>
									<td><span id="useText">사용</span>
										<input type="hidden" name="useFg" 	id="useFg"		value="1">
									</td>	
								</tr>
				
							 <tr>	
								<th align="center">소터구분</th>
								<td align="center"><div id=sorterFgNm></div>
									<input type="hidden" name="sorterFg" value="0">
									<input type="hidden" name="crsdkFg" value="0">
								</td>
								<th>혼재여부</th>
								<td align="center">
									<input type="radio" name="mixProdFg" value="0"  checked/>비혼재<br>
								</td>
							</tr>
							<tr >
								<th align="center">박스체적 </th>
								<td align="left" style="padding-left:20px" colspan="3">
									
									<div style="color:blue;">
										※ 정확한 숫자를 입력하셔야 납품이 가능합니다.
										예)30cm=300mm,1,000g=1kg (총중량은 소수점 둘째자리까지 입력가능합니다.)
									</div>
									<div>
									가로 :   <input type="text" name="width"   style="width:40px;"  maxlength="4"  value="<c:out value='${param.WIDTH}'/>" onblur="digit_sorter_check()"/> mm&nbsp; &nbsp;
									세로 :   <input type="text" name="length"  style="width:40px;"  maxlength="4"  value="<c:out value='${param.LENGTH}'/>" onblur="digit_sorter_check()"/> mm&nbsp; &nbsp;
									높이 :   <input type="text" name="height"  style="width:40px;"  maxlength="4"  value="<c:out value='${param.HEIGHT}'/>" onblur="digit_sorter_check()"/> mm&nbsp; &nbsp;
									총중량 : <input type="text" name="wg"      style="width:40px;"  maxlength="4"  value="<c:out value='${param.WG}'/>" onblur="digit_sorter_check()"/> kg&nbsp; &nbsp;
									</div>
								</td>
							</tr>
							<tr>
								<th align="center">소터에러사유</th>
								<td style="padding-left:15px" colspan="3">								
									<div>								
										<input type="radio" name="conveyFg" 	id="conveyFg"	onClick="digit_sorter_check()" value="0" checked />없음 &nbsp;
										<input type="radio" name="conveyFg" 	id="conveyFg"	onClick="digit_sorter_check()" value="1" />파손가능상품 &nbsp;
										<input type="radio" name="conveyFg" 	id="conveyFg"	onClick="digit_sorter_check()" value="2" />비닐포장제품 &nbsp;									
									</div>
								</td>
							</tr>
					
							<tr>
								<th align="center">팔레트</th> 
								<td style="padding-left:20px" colspan="3">
									<div>
										가로박스수 <input type="text" name="innerIpsu"		id="innerIpsu" 				class="requiredIf number" 	value="" 	style="width:50px;" />&nbsp;&nbsp;&nbsp;
										세로박스수 <input type="text" name="pltLayerQty" 	id="pltLayerQty"			class="requiredIf number" 	value="" 	style="width:50px;" />&nbsp;&nbsp;&nbsp;
									       높이박스수 <input type="text" name="pltHeightQty" 	id="pltHeightQty"			class="requiredIf number"   value="" 	style="width:50px;" />&nbsp;&nbsp;&nbsp;
									       총 박스수 <span id="totalBox"></span><input type ="text" name="totalBox" value=""	readonly="readonly"	style="width:50px;"/>
								    </div>							 
								</td>							
							</tr>
							<tr>
								<th align="center">VIC 사용여부</th>
								<td style="padding-left:20px" >	
									<div>
										<input type="radio" name="wUseFg" 	id="wUseFg"		value="0"  onclick="javascript:checkedChange('0');" checked/>미사용
										<input type="radio" name="wUseFg" 	id="wUseFg"		value="1"  onclick="javascript:checkedChange('1');" />사용									
									</div>
									<th ><span class="star"></span>VIC 박스 수</th>										
									<td>								
										<input type="text" name="wInnerIpsu" 	id="wInnerIpsu"	   		maxlength="4"   onblur="digit_sorter_check_winner(this)"	 style="width:50px;" />					
									</td>
							</tr>			
						</table>
					</form>	
				</div>

											
				</div>				
			</div>
		</div>
		
		
		
	</div>
		
	<!-- 탭 이동을 위한 hiddenForm -->
	<form name="hiddenForm"	id="hiddenForm">	
		<input type="hidden"	name="pgmId"				id="pgmId"				value="<c:out value="${prodDetailVO.pgmId}" />"			/>
		<input type="hidden"	name="entpCd"				id="entpCd"				value="<c:out value="${prodDetailVO.entpCd}" />"		/>
		<input type="hidden"	name="l1Cd" 		    id="l1Cd" 		    value="<c:out value="${prodDetailVO.l1Cd}" />" 		    />
		<input type="hidden"  name="l3Cd"         id="l3Cd"         value="<c:out value="${prodDetailVO.l3Cd}" />"        />
		<input type="hidden"	name="chkVariant"		id="chkVariant"		value=""												/>
		<input type="hidden"	name="mode"					id="mode"				  value="<c:out value='${param.mode}'/>"					/>
		
		<input type="hidden"	name="srcmk_cd"			id="srcmk_cd"			value="<c:out value='${param.srcmk_cd}'/>"					/>	<!-- 코리안넷에서 넘어온 판매코드 -->
		<input type="hidden"	name="bcd_fg"				id="bcd_fg"				value="<c:out value='${param.bcd_fg }'/>"					/>	<!-- 코리안넷 바코드번호 -->
		<input type="hidden"	name="BOX_FG"				id="BOX_FG"				value="<c:out value='${param.BOX_FG}'/>"					/>	<!-- 코리안넷 박스바코드번호 -->
		<input type="hidden"	name="LOGI_BCD"			id="LOGI_BCD"			value="<c:out value='${param.LOGI_BCD}'/>"					/>	<!-- 코리안넷 박스GTIN -->		
		<input type="hidden"	name="WG"					  id="WG"					  value="<c:out value='${param.WG}'/>"						/>	<!-- 코리안넷 박스 중량 -->
		<input type="hidden"	name="WIDTH"				id="WIDTH"				value="<c:out value='${param.WIDTH}'/>"						/>	<!-- 코리안넷 박스 가로 -->
		<input type="hidden"	name="LENGTH"				id="LENGTH"				value="<c:out value='${param.LENGTH}'/>"					/>	<!-- 코리안넷 박스 세로 -->
		<input type="hidden"	name="HEIGHT"				id="HEIGHT"				value="<c:out value='${param.HEIGHT}'/>"					/>	<!-- 코리안넷 박스 높이 -->
		<input type="hidden"	name="TOTAL_IPSU"		id="TOTAL_IPSU"		value="<c:out value='${param.TOTAL_IPSU}'/>"				/>	<!-- 코리안넷 수량 -->
		<input type="hidden"	name="bman_no"			id="bman_no"			value="<c:out value='${param.bman_no}'/>"					/>	<!-- 코리안넷에서 넘어온 사업자등록번호 -->
	</form>
	
						
</body>
</html>
