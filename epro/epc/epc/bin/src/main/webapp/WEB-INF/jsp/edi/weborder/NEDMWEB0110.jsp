<%--
- Author(s): 
- Created Date: 2015. 12. 10
- Version : 1.0
- Description : 상품별 반품등록 탭화면

--%>
<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script  type="text/javascript" >
	
	$(function() {

		_storeSelectCodeOptionTag();	// PAGE ON LOAD : VEN_CD 별 STORE_CD 설정
		//doSearchReturnList();			// today 등록반품록조회
		
		//버튼 CLICK EVNET ---------------------------------------------------------------
		$('#btnProdSave').unbind().click(null, 	 _eventProdSave);			// 등록하기
		$('#btnProdSelect').unbind().click(null, doSearchReturnList);		// 협력업체 코드별 반품상품조회
		
		//--------------------------------------------------------------------------------
		
		//입력필드 ENTER KEY EVENT --------------------------------------------------------
		$('#prodCd').unbind().keydown(function(e) {	// 상품코드
			switch(e.which) {
	    		case 13 : doSearchReturnList(this); break; // enter
	    		default : return true;
	    	}
	    	e.preventDefault();
	   	});
		
		$('#rrlQty').unbind().keydown(function(e) {	// 수량
			switch(e.which) {
	    		case 13 :  _eventProdSave(this); break; // enter
	    		default : return true;
	    	}
	    	e.preventDefault();
	   	});
	 	//-------------------------------------------------------------------------------
		
	 	// 권역 셀렉트 박스 변경시 점포 코드 변경
		$("#areaCd").change(function () {
			var _majorCD = $("#areaCd").val();
			_storeSelectCodeOptionTag(_majorCD, "#strCd", "전체");
			
		});
	 	
	 	//입력필드 CHANGE  EVENT ---------------------------------------------------------
		$("#entp_cd").change(function () { _storeSelectCodeOptionTag();});  // 업체코드 셀렉트 박스 변경시 점포 코드 변경
		//-------------------------------------------------------------------------------
		
		// 삭제 체크박스 전체 선택 ---------------------------------------------------------
		$('#allCheckStr').click(function(){
			if ($("#allCheckStr").is(":checked")) { 
				$('input:checkbox[id^=strBox]:not(checked)').attr("checked", true); 
			} else { 
				$('input:checkbox[id^=strBox]:checked').attr("checked", false); 
			} 
			
			$('input:checkbox[id^=strBox]:checked').each(function() {
				if($(this).attr("disabled")){
					$(this).attr("checked", false);
				}
			});
		});
		//-------------------------------------------------------------------------------
	 	
	});
	
	
	
	<%--  반품상품 저장  --%>
	function _eventProdSave(){
		
		<%-- 조회조건 검증 ============================= --%>	
		if(!$.trim($('#prodCd').val())){
			alert("<spring:message code='msg.web.alert.a01208' />");	//상품코드를 입력하세요!	
			$("#prodCd").focus();
			return false;
		}  
		
		if(!$.trim($('#prodCd').val()) || $('#prodCd').val().length != 10 ){
			alert("<spring:message code='msg.web.error.strCdMin10' />");	//상품코드 10자리를 정확히 입력하세요!
			$("#prodCd").focus();
			return false;
		}
		
		
		if(!isNumber($('#prodCd').val())) {
			alert("<spring:message code='msg.web.error.valProdCd' />");	//정상적인 상품코드를 입력하세요!
			
			$('#prodCd').val('');
			$('#prodCd').focus();
			return false;
		}
		
		
		if(!$.trim($('#entp_cd').val())){
			alert("<spring:message code='msg.web.error.valEntpCd' />");	//선택된 업체코드가 없습니다.
			$("#entp_cd").focus();
			return false;
		}
		<%-- ------------------------------------------------------------- --%>
		
		//var rrlQty 	= (Number($('#rrlQty').val().replace(/\,/g,"")));
	    //var rrlStkQty = (Number($('#rrlStkQty').val().replace(/\,/g,"")));
	   
		<%-- ----------- ============================= --%>
		var saveInfoListParam = {};
		var saveInfoList = new Array();
// 		if($('#searchForm input[name="rtnQty"]').size() == 0){
// 			alert("수량등록된 상품이 없습니다.");
// 			return false;
// 		}
		
		var flag = 0;
		for(var i=0;i<$('#searchForm input[name="rtnQty"]').size();i++){
			if($('#searchForm input[name="rtnQty"]').eq(i).val()!=""){
				flag = 1;
			}
		}
		if(flag==0){
			alert("<spring:message code='msg.web.alert.a012023' />");	//수량등록된 상품이 없습니다.
			return false;
		}
		
		<%-- 저장 CONFIRM  ============================ --%>
		var confirmMsg = "<spring:message code='msg.web.error.valStrCd' />";	//선택하신 [업체/상품/점포]의 반품을 등록하시겠습니까?
		//if(!$("#strCd").val()) confirmMsg = "[전체점포] 대상으로 반품을 등록하시겠습니까?";
		if (!confirm(confirmMsg)) {
        	return false;
		}
		
		$('#searchForm input[name="rtnQty"]').each(function(){
			if($(this).val() != ''){
				var saveInfo = {};
				saveInfo["prodCd"] 	= $(this).data('prodCd');
				saveInfo["srcmkCd"] 	= $(this).data('srcmkCd');
				saveInfo["prodNm"] 	= $(this).data('prodNm');
				saveInfo["prodStd"] 	= $(this).data('prodStd');
				saveInfo["venCd"] 		= $(this).attr('data-ven-cd'); //$(this).attr('data-ven-cd') $(this).data('venCd')
				saveInfo["venNm"] 		= ' ';
				saveInfo["ordUnit"] 	= $(this).data('ordUnit');
				saveInfo["ordIpsu"] 	= $(this).data('ordIpsu');
				saveInfo["stdBuyPrc"] = $(this).data('buyPrc');
				saveInfo["strCd"]		= $(this).data('strCd');
				saveInfo["strNm"]		= $(this).data('strNm');
				saveInfo["rrlStkQty"]		= $(this).data('rrlStkQty');
				saveInfo["rrlQty"] = $(this).val() // '반품수량';
				saveInfo["rrlTotProdQty"] = $(this).val() // '반품상품수';
				saveInfo["rrlTotPrc"] =  $(this).data('rrlTotPrc');// '반품총금액';
				saveInfo["rrlTotStkQty"] = $(this).data('rrlStkQty');// '반품일재고총수량';
				saveInfoList.push(saveInfo);
			}
		});
		saveInfoListParam['insertParam'] = saveInfoList;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			//url : '${ctx}/edi/product/test.json',
			url : '<c:url value="/edi/weborder/NEDMWEB0110saveReturnProdData.json"/>',
			data : JSON.stringify(saveInfoListParam),
			success : function(data) {
				if(data.state =="0"){
					alert("<spring:message code='msg.web.alert.a01202' />");	//정상적으로 등록되었습니다.	
					$('#btnProdSelect').trigger('click');
				}else{
					alert("<spring:message code='msg.web.alert.a01203' />\n[CODE:"+data.state+"]");		//반품처리중 오류가 발생하였습니다.
				}
					
			}
		});	
		<%-- ----------- ============================= --%>
		
		//setProdViewData(true);	//조회상품정보 초기화
		//doSearchReturnList();	//반품등록 현황 조회
	}
	

	
	<%--조회상품정보 초기화 --%>
	function setProdViewData(prodFocus){
		
		<%--[검색조건 초기화 & Focus ]==================== --%>
		if(prodFocus) {
			$('#prodCd').val(''); <%-- 상품코드(검색조건)--%>
			$("#prodCd").focus(); 
		}
		
		<%--[조회된 상품정보 초기화 ]====================== --%>
		$('#rrlQty').val(''); 			<%-- 수량  	 	--%>
		$('#rrlStkQty').val('');		<%-- 수량  	 	--%>
		
		$('#prodCd_view').text('');		<%-- 상품코드  	 --%>
		$('#srcmkCd_view').text('');	<%-- 판매코드      --%>
		$('#prodNm_view').text('');		<%-- 상품명        --%>
		$('#prodStd_view').text('');	<%-- 상품규격      --%>
		$('#ordIpsu_view').text('');	<%-- 발주입수      --%>


		$('#buyPrc_view').text('');		<%-- 원가        	 --%>
		$('#salePrc_view').text('');	<%-- 매가        	 --%>

	}
	
	/* 조회 */
	function doSearchReturnList(){
		<%-- 조회조건 검증 ============================= --%>	
		if (!$.trim($('#prodCd').val())) {
			alert("<spring:message code='msg.web.alert.a01208' />");	//상품코드를 입력하세요!
			$("#prodCd").focus();
			return false;
		}  
		
		if(!$.trim($('#prodCd').val()) || $('#prodCd').val().length != 10 ){
			alert("<spring:message code='msg.web.error.strCdMin10' />");	//상품코드 10자리를 정확히 입력하세요!
			$("#prodCd").focus();
			return false;
		}
		
		
		if(!isNumber($('#prodCd').val())) {
			alert("<spring:message code='msg.web.error.valProdCd' />");	//정상적인 상품코드를 입력하세요!
			
			$('#prodCd').val('');
			$('#prodCd').focus();
			return false;
		}
		
		
		if(!$.trim($('#entp_cd').val())){
			alert("<spring:message code='msg.web.error.valEntpCd' />");	//선택된 업체코드가 없습니다.
			$("#entp_cd").focus();
			return false;
		}
		<%-- ------------------------------------------------------------- --%>
		
		
		//구현 길스
		var dataInfo = {};
		//20151117
		dataInfo["PROD_CD"] = $('#prodCd').val();
		
		var TAB1 = new Array();
		if ($("#strCd option:selected").val() == '') {
			var selLen = $("#strCd option").size();
			  for (var i = 1; i < selLen; i++) {
				 var info = {};
				//console.log($("#strCd option:eq(" + i + ")").val());
				
				var selVal = $("#strCd option:eq(" + i + ")").val();
				info["STR_CD"] = selVal;
				
				TAB1.push(info); 
			}
		} else {
			var info = {};
			info["STR_CD"] = $("#strCd option:selected").val();
			TAB1.push(info); 
		} 
		
		var TAB2 = new Array();
		if ($("#entp_cd option:selected").val() == '') {
			var selLen = $("#entp_cd option").size();
			  for (var i = 1; i < selLen; i++) {
				 var info = {};
				//console.log($("#entp_cd option:eq(" + i + ")").val());
				
				var selVal = $("#entp_cd option:eq(" + i + ")").val();
				info["VEN_CD"] = selVal;
				
				TAB2.push(info); 
			}  
		} else {
			var info = {};
			info["VEN_CD"] = $("#entp_cd option:selected").val();
			TAB2.push(info); 
		} 
		
		dataInfo["TAB1"] = TAB1;
		dataInfo["TAB2"] = TAB2;
		// 공통 RequestCommon
		dataInfo["REQCOMMON"] = getReqCommon(); 
		
		//rfc펑션 콜후 콜백 펑션 호출 (콜백 펑션 구현rfcCallBack)
		rfcCall("INV0680" , dataInfo);
	}
	
	/* RFC CallBack */
	function rfcCallBack(data){
		 var rows = data.result.RESPCOMMON.ZPOROWS;
		var result = data.result;
		setTbodyInit("dataListbody");
		 if (rows != 0) {
			_setTbodyMasterValue(result.TAB);
		} else {
			setTbodyNoResult("dataListbody", 10);
		} 
	}
	
	function _setTbodyMasterValue(data) { 
		setTbodyInit("dataListbody");	// dataList 초기화
		var totalordIpsu = 0;
		var totalRrlStkQty = 0;
		var tempData = [];
		if(typeof(data.length) == 'undefined'){
			tempData =  data;
			tempData['index'] = 1;
			tempData['PROD_CD'] = data['PROD_CD'].replaceAll(' ','');
			tempData['VEN_CD'] = strLpad(data['VEN_CD'].replaceAll(' ',''),'0',6);
			tempData['STR_CD'] = data['STR_CD'].replaceAll(' ','');
			totalordIpsu += parseInt(data['ORD_IPSU']); // 입수 ORD_IPSU 
			totalRrlStkQty += parseInt(data['RRL_STK_QTY']); // 재고RRL_STK_QTY
			$('#storeCount').text('1');
			$('#prodCount').text('1');
		}else{
			for(var i = 0;i<data.length;i++){
				tempData[i] =  data[i];
				  
				tempData[i]['index'] = i + 1;
				tempData[i]['PROD_CD'] = data[i]['PROD_CD'].replaceAll(' ','');
				tempData[i]['VEN_CD'] = strLpad(data[i]['VEN_CD'].replaceAll(' ',''),'0',6);
				tempData[i]['STR_CD'] = data[i]['STR_CD'].replaceAll(' ','');
				totalordIpsu += parseInt(data[i]['ORD_IPSU']); // 입수 ORD_IPSU 
				totalRrlStkQty += parseInt(data[i]['RRL_STK_QTY']); // 재고RRL_STK_QTY
				
			}
		}
		
		$('#storeCount').text(data.length);
		$('#prodCount').text(data.length);
		$('#totalordIpsu').text(totalordIpsu);
		$('#totalRrlStkQty').text(totalRrlStkQty);
		$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/weborder/NEDMWEB0110selectDayRtnProdList.json"/>',
			data : JSON.stringify(
									{venCd : $('#entp_cd').val() //$('#entp_cd').val() '003999'
									,strCd : $('#areaCd').val()
									,prodCd :$('#prodCd').val()
									}
								),
			success : function(data) {
				var result = data.result;
				 if(result.length > 0){
					for(var i = 0 ; i< result.length;i++){
						var prodCd = result[i]['prodCd'];
						var venCd = result[i]['venCd'];
						var strCd = result[i]['strCd'];
						$('#'+prodCd +venCd +strCd).attr('disabled',true);
						$('#'+prodCd +venCd +strCd).val(result[i]['rrlQty']);
						
					}
				} 
			}
		});	
		
	}
	
	
	
	

	// 단위수량 입력시 EA 와 금액 계산
	function sumOrdInfo(obj){
		// 상품별 합계
		var rtnQty = $(obj).parent().parent().children().children('input[name="rtnQty"]').attr('value');
		
		rtnQty = rtnQty.replaceAll(",","");

		if(!isNumber(rtnQty.replace(/\,/g,"")) || (rtnQty != ""  && !chkQty > 0)) {
			alert("<spring:message code='msg.web.alert.a012012' />");		//	정상적인발주수량(EA)을 입력하세요!
			$(obj).val("");
			$(obj).focus();
			return;	
		}
	}
	
	
	function _eventProdSendCheck(){
		var checkVal = false;
		
		for ( var i = 0; i < ($("#rtnCnt").val()); i++) {
			var cnt = i+1;		
			if( $("#regSts"+cnt).val() == '0' ){
				checkVal = true;
			} 
			//alert($("#regSts"+cnt).val());
		}
		
		if( checkVal == true) {
			_eventProdSend();
		}else{
			alert("<spring:message code='msg.web.alert.a012023' />");		//수량등록된 상품이 없습니다.
			return false;
		}
	}
	
	
	/* 목록 초기화 (합계 ROW, 상품목록List)*/
	function _setTbodyMstInit(){
		$("#datalistStr tr").remove();	//상품목록 TBODY
		
		$("#storeCount"	).text('0');	//점포SUM
		$("#prodCount"	).text('0');	//상품SUM
		$("#qtySum"		).text('0');	//수량SUM
		$("#amtSum"		).text('0');	//금액SUM
		
	}
	
	
	//-----------------------------------------------------------------------
	//숫자만 입력가능하도록
	//-----------------------------------------------------------------------
	//예)
	//html : <input type='text' name='test' onkeypress="onlyNumber();">
	//-----------------------------------------------------------------------
	function onlyNumber(obj)
	{

		if((event.keyCode<48) || (event.keyCode>57))
		{
			event.returnValue=false;
		}else{
			var rrlTotPrc = 0;
			if($(obj).val() > $(obj).data('rrlStkQty')){
				alert("<spring:message code='msg.web.alert.a012018' />");		//	현재고량보다 많습니다.
				$(obj).val('');
				$(obj).closest('tr').find('.rrlTotPrc').text(rrlTotPrc);
			}else{
				rrlTotPrc = $(obj).val() * $(obj).data('buyPrc');
				$(obj).data('rrlTotPrc',rrlTotPrc);
				$(obj).closest('tr').find('.rrlTotPrc').text(rrlTotPrc);
			}
			
		}
	}
	
	//숫자검사
	function isNumber(str) {
		var chars = "0123456789";
		return containsCharsOnly(str, chars);
	}
	//Chars 검사
	function containsCharsOnly(input,chars) {
	   for (var inx = 0; inx < input.length; inx++) {
	       if (chars.indexOf(input.charAt(inx)) == -1)
	           return false;
	    }
	    return true;
	}
	
	/* 권역별 점포코드 세팅 */
	function _storeSelectCodeOptionTag(commonMajorCode, toSelectTagID, firstOptionMessage, finallyMethod) {
		var str = { "majorCD": commonMajorCode };
		
		$.ajaxSetup({
	  		contentType: "application/json; charset=utf-8" 
			});
		$.post(
				"<c:url value='/CommonCodeHelperController/storeSelectList.do'/>",
				JSON.stringify(str),
				function(data){
					var json = _jsonParseCheck(data);
					if(json==null)
					{
						return;
					}
					$(toSelectTagID + " option").remove();
					if(firstOptionMessage == "none" || firstOptionMessage == null || firstOptionMessage == "null")
					{
						$(toSelectTagID).append(json);
					}
					else
					{
						$(toSelectTagID).append("<option value=''>" + firstOptionMessage + "</option>").append(json);
					}
					if(finallyMethod != null)
					{
						finallyMethod();
					}
				},	"text"
			);
	}
	
	/*  JSON 파싱 체크*/
	function _jsonParseCheck(jsonStr){
		//jsonParseCheck(jsonStr);
		
		//alert("jsonStr : " + jsonStr);
		if(jsonStr == null || jsonStr == "") {
			return null;
		}
	
		var json = JSON.parse(jsonStr);
		
		if(json==null){
			return null;
		}
		
		var resultCd = json.__RESULT__;
		if(resultCd==null || resultCd!="NG"){
			return json;
			
		}else{
			var errCd = json.__ERR_CD__;
			var errMsg = json.__ERR_MSG__;
			 
			alert("[ <spring:message code='msg.web.alert.a012019' /> ]\n" + errMsg + ", Code : " + errCd);	//에러 
			return null;
		}
		
	}
</script>	

<style>
.dot_web0001 span{display:block;overflow:hidden;width:100px;height:18px;white-space:nowrap;text-overflow:ellipsis;-o-text-overow: ellipsis;-moz-binding:url(js/ellipsis.xml#ellipsis)undefinedundefinedundefined}
.dot_web0002 span{display:block;overflow:hidden;width:110px;height:18px;white-space:nowrap;text-overflow:ellipsis;-o-text-overow: ellipsis;-moz-binding:url(js/ellipsis.xml#ellipsis)undefinedundefinedundefined}

/* 오류 데이터 라인 + 글씨 Color 회색*/
.through_tr { font-style:italic; text-decoration:line-through; color:gray }

</style>
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr bgcolor="ffffff">
	<td align="center"><c:out value="\${index}" /></td>
	<td align="center">
		<span title="null" style="color: blue;">
			{%if parseInt(RRL_STK_QTY) == 0 %}
				<font color="red"><spring:message code='text.web.field.t01201' /></font>
			{%else%}
				<spring:message code='text.web.field.t01202' />
			{%/if%}
		</span>
	</td>
	<td class="dot_web0001"><span title="[<c:out value="\${STR_CD}" />]<c:out value="\${STR_NM}" />">[<c:out value="\${STR_CD}" />]<c:out value="\${STR_NM}" /></span></td>
	<td align="center"><c:out value="\${PROD_CD}" /></td>
	<td align="center"><c:out value="\${SRCMK_CD}" /></td>
	<td class="dot_web0002"><span title="<c:out value="\${PROD_NM}" />"><c:out value="\${PROD_NM}" /></span></td>
	<td align="center"><c:out value="\${ORD_IPSU}" /></td>
	<td align="right" style="color: blue; font-weight: bolder;"><c:out value="\${parseInt(RRL_STK_QTY)}" /></td>
	<td align="right">
		{%if parseInt(RRL_STK_QTY) == 0 %}
			<input 
			id="<c:out value="\${PROD_CD}" /><c:out value="\${VEN_CD}" /><c:out value="\${STR_CD}" />"
			type="text" name="rtnQty" maxlength="10" value="" readonly
			style="text-align: right; width: 60px;" 
			data-prod-cd = "<c:out value="\${PROD_CD }" />"
			data-srcmk-cd ="<c:out value="\${SRCMK_CD }" />"
			data-prod-nm = "<c:out value="\${PROD_NM }" />"
			data-prod-std ="<c:out value="\${PROD_STD }" />"
			data-ven-cd = "<c:out value="\${VEN_CD }" />"
			data-ord-unit = "<c:out value="\${ORD_UNIT }" />"
			data-ord-ipsu = "<c:out value="\${ORD_IPSU }" />"
			data-buy-prc = "<c:out value="\${BUY_PRC }" />"
			data-str-cd = "<c:out value="\${STR_CD }" />" 
			data-str-nm = "<c:out value="\${STR_NM }" />" 
			data-rrl-stk-qty="<c:out value="\${parseInt(RRL_STK_QTY) }" />"
			">
		{%else%}
			 <input 
			id="<c:out value="\${PROD_CD}" /><c:out value="\${VEN_CD}" /><c:out value="\${STR_CD}" />"
			type="text" name="rtnQty" maxlength="10" value=""
			style="text-align: right; width: 60px;" 
			data-prod-cd = "<c:out value="\${PROD_CD }" />"
			data-srcmk-cd ="<c:out value="\${SRCMK_CD }" />"
			data-prod-nm = "<c:out value="\${PROD_NM }" />"
			data-prod-std ="<c:out value="\${PROD_STD }" />"
			data-ven-cd = "<c:out value="\${VEN_CD }" />"
			data-ord-unit = "<c:out value="\${ORD_UNIT }" />"
			data-ord-ipsu = "<c:out value="\${ORD_IPSU }" />"
			data-buy-prc = "<c:out value="\${BUY_PRC }" />"
			data-str-cd = "<c:out value="\${STR_CD }" />" 
			data-str-nm = "<c:out value="\${STR_NM }" />" 
			data-rrl-stk-qty="<c:out value="\${parseInt(RRL_STK_QTY) }" />"
			onkeyup="onlyNumber(this);">
		{%/if%}
	</td>
	<td class="rrlTotPrc" align="right" style="color: red;">0</td>
</tr>
</script>
</head>
<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post" action="#">
		<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />" > 
		<div id="wrap_menu">
		
		<!--	@ 검색조건	-->
			
			<div class="bbs_search">
				<ul class="tit">
					<li class="tit"><spring:message code='text.web.field.selectRtnProd'/></li>
					<li class="btn"><span style="font-weight: normal;  color: #414fbb; text-align: right;"><span><img src="/images/epc/btn/icon_04.png" alt="Notice" /></span> <spring:message code='text.web.field.srchInit01101'/></span>
					<span style="font-weight: normal; color: #414fbb; margin-left: 25px;"><img src="/images/epc/btn/icon_04.png" alt="Notice" />&nbsp; <spring:message code='text.web.field.srchInit01102'/> <span style="color: red;"><spring:message code="msg.weborder.vendor.send.time.rtn"/> </span><spring:message code='text.web.field.srchInit01103'/></span></li>
				</ul>
				<table class="bbs_search" >
					<colgroup>
						<col style="width:15%" />
						<col style="width:15%" />
						<col style="width:10%" />
						<col style="width:15%" />
						<col style="width:10%" />
						<col style="width:10%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><spring:message code='text.web.field.srchProd'/></th>
						<td colspan=7  style="border-bottom-color: #ffffff;">
							 <table>
								<tr>
									<td><b><spring:message code='text.web.field.entpCd'/></b></td>
									<td><html:codeTag objId="entp_cd" objName="entp_cd" width="100px;" formName="form" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" /></td>
									<!--<td><b>점포</b> : <select name="strCd" id="strCd" style="width: 130px;"></select></td>-->
									<td><b><spring:message code='text.web.field.areaCd'/></b></td>
									<td>
										<html:codeTag objId="areaCd" objName="areaCd" width="75px;" formName="form" dataType="AREA" comType="select"  defName="전체"  />
										<select name="strCd" id="strCd" style="width:120px">
											<option value=""><spring:message code='text.web.field.searchALL'/></option>
										</select>
									</td>
									<td><b>* <spring:message code='text.web.field.prodCd'/></b></td>
									<td><input type="text" id="prodCd" name="prodCd" class="required"  style="width:100px;" maxlength="10" /></td>
									<td><a href="#" class="btn" id="btnProdSelect" ><span><spring:message code='text.web.field.search'/></span></a></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				</div>
				<div class="bbs_search" style="margin-top: 2px;">	
				<table class="bbs_search" >
					<colgroup>
						<col style="width:15%" />
						<col style="*" />
					</colgroup>
				</table>
			</div>
			
			
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit"><spring:message code='text.web.field.addList'/> </li>
						<li class="btn">
							<a href="#" class="btn" id="btnProdSave"><span><spring:message code='button.web.createQty'/></span></a>
						</li>
					</ul>
			
					<table id="dataTable" cellpadding="1" cellspacing="1" border="0" bgcolor=efefef width="100%">
                        <colgroup>
							<col width="31px">
							<col width="55px">
							<col width="110px">
							<col width="80px">
							<col width="90px">
							<col width="*">
							<col width="55px">
							<!-- <col width="60px"/>  -->
							<!--	<col width="50px"/> -->
							<col width="60px">
							<col width="60px">
							<col width="100px">
							<col width="18px">
						</colgroup>
						<thead>
                        	<tr bgcolor="#e4e4e4" align=center> 
                          		<th>No.</th>
                          		<th><spring:message code='epc.web.header.status'/></th>
                          		<th><spring:message code='epc.web.header.strNm'/></th>
                          		<th><spring:message code='text.web.field.prodCd'/></th>
                          		<th><spring:message code='epc.web.header.buyCd'/></th>
                          		<th><spring:message code='epc.web.header.prdNm'/></th>
                          		<th><spring:message code='epc.web.header.available'/></th>
                          		<th><spring:message code='epc.web.header.h01101'/></th>
                          		<th><spring:message code='epc.web.header.h01102'/></th>
                          		<th><spring:message code='epc.web.header.h01103'/></th>
                          		<th rowspan="2"></th>
                        	</tr>
                      		<tr bgcolor="87CEFA"> 
                        		<td colspan="2" bgcolor="#929292"><span style="width:100%; font-weight: bold; text-align: center; color: #ffffff;"><spring:message code='epc.web.header.sum'/></span></td>
                          		<td><spring:message code='epc.web.header.strCnt'/></td>
                          		<td colspan="3"><spring:message code='epc.web.header.prodCnt'/></td>
                          		<td id="totalordIpsu" style="text-align: center;">-</td> 
                          		<td id="totalRrlStkQty" style="text-align: center;">-</td>
                          		<td style="text-align: right;"><span  style="font-weight: bold; font-size: 1.1em;"></span></td>
                          		<td style="text-align: right;"><span  style="font-weight: bold; font-size: 1.1em;"></span></td>
                          	</tr>
                        </thead>
                        	 <tr> 
		                     	<td colspan=12>   
		                        	<div id="_dataList1" style="background-color:#FFFFFF; margin: 0; padding: 0; height:346px; overflow-y: scroll; overflow-x: hidden">
		                        		<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=100% bgcolor="#EFEFEF">
		                     			<colgroup>
											<col width="30px">
											<col width="55px">
											<col width="110px">
											<col width="80px">
											<col width="90px">
											<col width="*">
											<col width="55px">
											<col width="60px">
											<col width="60px">
											<col width="100px">
										</colgroup>
										<!-- Data List Body Start ------------------------------------------------------------------------------>
										<tbody id="dataListbody" >
										</tbody>
										<!-- Data List Body End   ------------------------------------------------------------------------------>
		                     			
		                     			</table>
		                     		</div>
		                     	</td>
		                    </tr>
					</table>
					
				</div>
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
					<li><spring:message code='epc.web.menu.lvl1'/></li>
					<li><spring:message code='epc.web.menu.lvl2'/></li>
					<li><spring:message code='epc.web.menu.returnCreate'/></li>
					<li class="last"><spring:message code='epc.web.menu.perProd'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
