<%--
- Author(s): 
- Created Date: 2015. 12. 10
- Version : 1.0
- Description : 임시보관함

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
	
	<script type="text/javascript"	src="/js/epc/showModalDialogCallee.js"></script>
	
<script  type="text/javascript" >
	
	$(function() {
		
		_storeSelectCodeOptionTag();	// PAGE ON LOAD : VEN_CD 별 STORE_CD 설정
		//doSearchReturnList();			// today 등록반품록조회
		
		//버튼 CLICK EVNET ---------------------------------------------------------------
		$('#btnProdSave').unbind().click(null, 	 _eventProdSave);			// 수량등록 업데이트
		$('#btnProdSend').unbind().click(null, 	 _eventProdSendCheck);			// 반품요청 RFC 요청
		$('#btnProdDelete').unbind().click(null, _eventProdDelete);			// 반품상품삭제 임시보관함 삭제
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
			_storeSelectCodeOptionTag(_majorCD, "#strCd", "<spring:message code='text.web.field.searchALL' />");
			
		});
	 	
	 	//입력필드 CHANGE  EVENT ---------------------------------------------------------
		$("#entp_cd").change(function () { _storeSelectCodeOptionTag();});  // 업체코드 셀렉트 박스 변경시 점포 코드 변경
		//-------------------------------------------------------------------------------
		
		// 삭제 체크박스 전체 선택 ---------------------------------------------------------
		$('#allCheckStr').click(function(){
			if($(this).is(':checked')){
				$('#searchForm input[name=strBox]').attr('checked',true);
			}else{
				$('#searchForm input[name=strBox]').attr('checked',false);
			}
		});
		//-------------------------------------------------------------------------------
	 	
	});
	
	
	
	/* 수량등록 */
	function _eventProdSave() {
		
		if ($('#searchForm input[name="strBox"]:checked').length == 0) {
			alert("<spring:message code='msg.web.alert.a01201' />");
			return false;
		}
		
		//-----선택된 반품상품의 재고량보다 입력수량이 많을 경우 return
		var chkLen	=	$('#searchForm input[name="strBox"]').length;
		for (var i = 0; i < chkLen; i++) {
			if ($("input[name='strBox']").eq(i).is(":checked")) {				
				if ($("input[name='rtnQty']").eq(i).val() > $("input[name='hidRrlStkQty']").eq(i).val() ) {
					alert("반품수량이 재고수량보다 많은 상품이 존재합니다.");
					return;
				}					
			}			
		}
		
		<%-- 저장 CONFIRM  ============================ --%>
		if (!confirm("<spring:message code='msg.web.error.prodSave' />")) {
        	return false;
		}
		<%-- ----------- ============================= --%>
		
		var saveInfoListParam = {};
		var saveInfoList = new Array();
		
		
		
		$('#searchForm input[name="strBox"]:checked').each(function() {
			var $dataRow = $(this).closest('tr').find('input[name=rtnQty]');
			
			var saveInfo = {};
			saveInfo["rrlReqNo"] 	= $dataRow.attr('data-rrl-req-no');
			saveInfo["stdBuyPrc"] 	= $dataRow.attr('data-buy-prc');
			saveInfo["rrlQty"]		= $dataRow.val();
			
			var rrlTotPrc = $dataRow.attr('data-buy-prc') * $dataRow.val();
			saveInfo["rrlTotPrc"] 	= rrlTotPrc;
			//console.log(saveInfo)
			
			saveInfoList.push(saveInfo);
		});
		
		saveInfoListParam['insertParam'] = saveInfoList;
		//return;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/weborder/NEDMWEB0120updateReturnProdData.json"/>',
			data : JSON.stringify(saveInfoListParam),
			success : function(data) {
				if(data.msgCd == "0") {
					alert("<spring:message code='msg.web.alert.a01202' />");
					$('#btnProdSelect').trigger('click');
				}/*  else {
					alert("<spring:message code='msg.web.alert.a01203' />\n[CODE:"+data.state+"]");
				
				} */
			}
		});
		<%-- ----------- ============================= --%>
	}
	
	<%-- 반품등록 삭제 --%>
	function _eventProdDelete() {
		
		if ($('#searchForm input[name="strBox"]:checked').length == 0) {
			alert("<spring:message code='msg.web.alert.a01201' />");
			return false;
		}
		
		
		
		<%-- 저장 CONFIRM  ============================ --%>
		if (!confirm("<spring:message code='msg.web.error.prodDel' />")) {
			return false;
		}
		<%-- ----------- ============================= --%>
		
		var saveInfoListParam = {};
		var saveInfoList = new Array();
		
		
		$('#searchForm input[name="strBox"]:checked').each(function() {
			var $dataRow = $(this).closest('tr').find('input[name=rtnQty]');
			
			var saveInfo = {};
			saveInfo["rrlReqNo"] 	= $dataRow.attr('data-rrl-req-no');
			saveInfo["stdBuyPrc"] 	= $dataRow.attr('data-buy-prc');
			saveInfo["rrlQty"] 		= $dataRow.val();
			
			var rrlTotPrc = $dataRow.attr('data-buy-prc') * $dataRow.val();
			saveInfo["rrlTotPrc"] = rrlTotPrc;
			//console.log(saveInfo)
			
			saveInfoList.push(saveInfo);
		});
		
		saveInfoListParam['insertParam'] = saveInfoList;
		//return;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			//url : '${ctx}/edi/product/test.json',
			url : '<c:url value="/edi/weborder/NEDMWEB0120deleteReturnProdData.json"/>',
			data : JSON.stringify(saveInfoListParam),
			success : function(data) {
				if (data.msgCd == "0") {
					alert("<spring:message code='msg.web.alert.a01206' />");
					$('#btnProdSelect').trigger('click');
				}/* else{
					alert("<spring:message code='msg.web.alert.a01207' />\n[CODE:"+data.state+"]");
				} */
			}
		});	
	}
	
	<%-- 조회상품정보 초기화 --%>
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
	function doSearchReturnList() {
		if (!$.trim($('#entp_cd').val())) {
			alert("<spring:message code='msg.web.alert.a012011' />");
			$("#entp_cd").focus();
			return false;
		}
		
		var searchInfo = {};
		searchInfo["venCd"] 	= $('#entp_cd').val();
		searchInfo["areaCd"] 	= $('#areaCd').val();
		searchInfo["strCd"] 	= $("#strCd").val();
		//console.log(searchInfo);
		//return;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/weborder/NEDMWEB0120selectDayRtnProdList.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				//json 으로 호출된 결과값을 화면에 Setting 
				_setTbodyMasterValue(data);
			}
		});
	}
	
	/* _eventSearch() 후처리(data  객체 그리기) */
	function _setTbodyMasterValue(json) { 
		//console.log(json);
		var data = json.result;
			
		setTbodyInit("dataListbody");	// dataList 초기화
		
		if (data.length > 0) {
			for (var i = 0; i < data.length; i++) {
				data[i].count = i + 1;
			}
			
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		} else { 
			setTbodyNoResult("dataListbody", 9);
		}
	}
	
	/* RFC 호출 CallBack */
	function rfcCallBack(data) {
		var rows = data.result.RESPCOMMON.ZPOROWS;
		var result = data.result;
		//console.log(result);
	}
	
	// 단위수량 입력시 EA 와 금액 계산
	function sumOrdInfo(obj){
		// 상품별 합계
		var rtnQty = $(obj).parent().parent().children().children('input[name="rtnQty"]').attr('value');
		
		rtnQty = rtnQty.replaceAll(",","");

		if(!isNumber(rtnQty.replace(/\,/g,"")) || (rtnQty != ""  && !chkQty > 0)) {
			alert("<spring:message code='msg.web.alert.a012012' />");
			$(obj).val("");
			$(obj).focus();
			return;	
		}
	}
	
	/* 반품요청 */
	function _eventProdSendCheck(){
		if($('#searchForm input[name="strBox"]:checked').length == 0 ){
			alert("<spring:message code='msg.web.alert.a012013' />");
			return false;
		}
		_eventProdSend();
	}
	
	/* RFC 호출 */
	function _eventProdSend(){
		<%-- 저장 CONFIRM  ============================ --%>
		var confirmMsg = "<spring:message code='msg.web.alert.a012014' />";
		//if(!$("#strCd").val()) confirmMsg = "[전체점포] 대상으로 반품을 등록하시겠습니까?";
		if (!confirm(confirmMsg)) {
        	return false;
		}
		<%-- ----------- ============================= --%>
		var saveInfoListParam = {};
		var saveInfoList = new Array();
		if($('#searchForm input[name="strBox"]:checked').length == 0 ){
			alert('<spring:message code='msg.web.alert.a012015' />');
			return false;
		}
		$('#searchForm input[name="strBox"]:checked').each(function(){
			var $dataRow = $(this).closest('tr').find('input[name=rtnQty]');
			
			var saveInfo = {};
			saveInfo["rrlReqNo"] 	= $dataRow.attr('data-rrl-req-no');
			saveInfo["stdBuyPrc"] 	= $dataRow.attr('data-buy-prc');
			saveInfo["rrlQty"] 	= $dataRow.val();
			var rrlTotPrc = $dataRow.attr('data-buy-prc') * $dataRow.val();
			saveInfo["rrlTotPrc"] 	= rrlTotPrc;
			
			saveInfoList.push(saveInfo);
				
		});
		saveInfoListParam['insertParam'] = saveInfoList;
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			//url : '${ctx}/edi/product/test.json',
			url : '<c:url value="/edi/weborder/NEDMWEB0120sendReturn.json"/>',
			data : JSON.stringify(saveInfoListParam),
			success : function(data) {
				if (data.msgCd == "0") {
					alert("<spring:message code='msg.web.alert.a012016' />");
					$('#btnProdSelect').trigger('click');
				}/* else{
					alert("<spring:message code='msg.web.alert.a012017' />\n[CODE:"+data.state+"]");
				} */
			}
		});	
	}
	function _eventProdSendAsis(){
		//구현 길스
		var dataInfo = {};
		//20151117
		
		var TAB = new Array();
		$('#searchForm input[name="strBox"]:checked').each(function(){
			var $dataRow = $(this).closest('tr').find('input[name=rtnQty]');
			var saveInfo = {};
			saveInfo['RTN_DY'] = $dataRow.attr('data-rrl-dy');
			saveInfo['STR_CD'] = $dataRow.attr('data-str-cd');
			saveInfo['PROD_CD'] = $dataRow.attr('data-prod-cd');
			saveInfo['VEN_CD'] = $dataRow.attr('data-ven-cd');
			saveInfo['RTN_QTY'] = $dataRow.val();
			
			TAB.push(saveInfo);
				
		});
		
		
		
		
		dataInfo["TAB"] = TAB;
		// 공통 RequestCommon
		dataInfo["REQCOMMON"] = getReqCommon(); 
		//console.log(dataInfo);
		//rfc펑션 콜후 콜백 펑션 호출 (콜백 펑션 구현rfcCallBack)
		rfcCall("INV0690" , dataInfo);
		//
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
			//alert('obj.val=' + $(obj).val() + ',rrlStkQty=' +$(obj).data('rrlStkQty') )
			if($(obj).val() > $(obj).data('rrlStkQty')){
				alert("<spring:message code='msg.web.alert.a012018' />");
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
			
			alert("[ <spring:message code='msg.web.alert.a012019' /> ]\n" + errMsg + ", Code : " + errCd);
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
		<td align="center"><c:out value="\${count}" /></td>
		<td align='center'>
			{%if regStsCd != 1 %}
				<input type='checkbox' name='strBox' >
			{%/if%}
		</td>

		<td align="center">
			<span title="null" style="color: blue;">
				{%if regStsCd == 1 %}
					<spring:message code='text.web.field.t01201' />
				{%else%}
					<spring:message code='text.web.field.t01202' />
				{%/if%}
			</span>
		</td>
		<td class="dot_web0001"><span title="[<c:out value="\${strCd}" />]<c:out value="\${strNm}" />">[<c:out value="\${strCd}" />]<c:out value="\${strNm}" /></span></td>
		<td align="center"><c:out value="\${prodCd}" /></td>
		<td align="center"><c:out value="\${srcmkCd}" /></td>
		<td class="dot_web0002"><span title="<c:out value="\${prodNm}" />"><c:out value="\${prodNm}" /></span></td>
		<td align="center"><c:out value="\${ordIpsu}" /></td>
		<td align="right" style="color: blue; font-weight: bolder;">
			<c:out value="\${parseInt(rrlStkQty)}" />
			<input type="hidden"	id="hidRrlStkQty"	name="hidRrlStkQty"		value="<c:out value='\${parseInt(rrlStkQty)}' />"	/>
		</td>
		<td align="right">
			{%if regStsCd == 1 %}
				<c:out value="\${parseInt(rrlQty) }" />
			{%else%}
				<input 
					id="<c:out value="\${prodCd}" /><c:out value="\${venCd}" /><c:out value="\${strCd}" />"
					type="text" name="rtnQty" maxlength="10" value="<c:out value="\${parseInt(rrlQty) }" />"
					style="text-align: right; width: 60px;"  
					data-rrl-req-no = "<c:out value="\${rrlReqNo }" />"
					data-rrl-dy = "<c:out value="\${rrlDy }" />"
					data-prod-cd = "<c:out value="\${prodCd }" />"
					data-srcmk-cd ="<c:out value="\${srcmkCd }" />"
					data-prod-nm = "<c:out value="\${prodNm }" />"
					data-prod-std ="<c:out value="\${prodStd }" />"
					data-ven-cd = "<c:out value="\${venCd }" />"
					data-ord-unit = "<c:out value="\${ordUnit }" />"
					data-ord-ipsu = "<c:out value="\${ordIpsu }" />"
					data-buy-prc = "<c:out value="\${stdBuyPrc }" />"
					data-str-cd = "<c:out value="\${strCd }" />" 
					data-str-nm = "<c:out value="\${strNm }" />" 
					data-rrl-stk-qty="<c:out value="\${parseInt(rrlStkQty) }" />"
					onkeyup="onlyNumber(this);">
			{%/if%}
		</td>
		<td class="rrlTotPrc" align="right" style="color: red;"><c:out value="\${parseInt(stdBuyPrc * rrlQty) }" /> </td>
	</tr>
</script>
</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post" action="#">
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" > 
		<div id="wrap_menu">
		
		<!--	@ 검색조건	-->
			
			<div class="bbs_search">
				<ul class="tit">
					<li class="tit"><spring:message code='text.web.field.selectRtnProd' /></li>
					<li class="btn"><span style="font-weight: normal;  color: #414fbb; text-align: right;"><span><img src="/images/epc/btn/icon_04.png" alt="Notice" /></span> <spring:message code='text.web.field.srchInit01101' /></span>
					<span style="font-weight: normal; color: #414fbb; margin-left: 25px;"><img src="/images/epc/btn/icon_04.png" alt="Notice" />&nbsp; <spring:message code='text.web.field.srchInit01102' /> <span style="color: red;"><spring:message code="msg.weborder.vendor.send.time.rtn"/> </span><spring:message code='text.web.field.srchInit01103' /></span></li>
				</ul>
				<table class="bbs_search" >
					<colgroup>
						<col style="width:15%" />
						<col style="width:15%" />
						<col style="width:10%" />
						<col style="width:15%" />
						<col style="width:10%" />
						<!-- <col style="width:10%" /> -->
						<!-- <col style="width:10%" /> -->
						<col style="*" />
					</colgroup>
					<tr>
						<th>검색조건</th>
						<td colspan=7  style="border-bottom-color: #ffffff;">
							 <table>
								<tr>
									<td><b><spring:message code='text.web.field.entpCd' /></b></td>
									<td>
										<c:if test="${epcLoginVO ne null}">
											<html:codeTag objId="entp_cd" objName="entp_cd" width="100px;" formName="form" selectParam="<c:out value='${epcLoginVO.repVendorId}'/>" dataType="CP" comType="SELECT" />
										</c:if>
										
										<c:if test="${epcLoginVO eq null}">
											<html:codeTag objId="entp_cd" objName="entp_cd" width="100px;" formName="form" selectParam="<c:out value='${param.entp_cd}'/>" dataType="CP" comType="SELECT" />
										</c:if>
										
									</td>
									<!--<td><b>점포</b> : <select name="strCd" id="strCd" style="width: 130px;"></select></td>-->
									<td><b><spring:message code='text.web.field.areaCd' /></b></td>
									<td>
										<html:codeTag objId="areaCd" objName="areaCd" width="75px;" formName="form" dataType="AREA" comType="select"  defName="전체"  />
										<select name="strCd" id="strCd" style="width:120px">
											<option value=""><spring:message code='text.web.field.searchALL' /></option>
										</select>
									</td>
									<%-- <td><b><spring:message code='text.web.field.prodCd' /></b></td> --%>
									<!-- <td><input type="text" id="prodCd" name="prodCd" class="required"  style="width:100px;" maxlength="10" /></td> -->
									<td><a href="#" class="btn" id="btnProdSelect" ><span><spring:message code='text.web.field.search' /></span></a></td>
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
						<li class="tit"><spring:message code='text.web.field.addList' /> </li>
						<li class="btn">
							<a href="#" class="btn" id="btnProdSave"><span><spring:message code='button.web.createQty' /></span></a>
							<a href="#" class="btn" id="btnProdSend"><span><spring:message code='button.web.returnReq' /></span></a>
							<a href="#" class="btn" id="btnProdDelete"><span><spring:message code='button.web.delete' /></span></a>
						</li>
					</ul>
			
					<table id="dataTable" cellpadding="1" cellspacing="1" border="0" bgcolor=efefef width="100%">
                        <colgroup>
							<col width="31px">
							<col width="50px"/>
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
                          		<th><spring:message code='epc.web.header.selected' /><input type="checkbox" id="allCheckStr" name="allCheckStr"></th>
                          		<th><spring:message code='epc.web.header.status' /></th>
                          		<th><spring:message code='epc.web.header.strNm' /></th>
                          		<th><spring:message code='text.web.field.prodCd' /></th>
                          		<th><spring:message code='epc.web.header.buyCd' /></th>
                          		<th><spring:message code='text.web.field.prodNm' /></th>
                          		<th><spring:message code='epc.web.header.available' /></th>
                          		<th><spring:message code='epc.web.header.h01101' /></th>
                          		<th><spring:message code='epc.web.header.h01102' /></th>
                          		<th><spring:message code='epc.web.header.h01103' /></th>
                          		<th rowspan="2"></th>
                        	</tr>
                      		<tr bgcolor="87CEFA"> 
                        		<td colspan="3" bgcolor="#929292"><span style="width:100%; font-weight: bold; text-align: center; color: #ffffff;">합 계</span></td>
                          		<td><spring:message code='epc.web.header.strCnt' /></td>
                          		<td colspan="3"><spring:message code='epc.web.header.prodCnt' /></td>
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
											<col width="50px"/>
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
					<li><spring:message code='epc.web.menu.lvl1' /></li>
					<li><spring:message code='epc.web.menu.lvl2' /></li>
					<li><spring:message code='epc.web.menu.returnCreate' /></li>
					<li class="last"><spring:message code='epc.web.menu.perProd' /></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
