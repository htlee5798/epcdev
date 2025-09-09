<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
/* 폼로드 */
$(document).ready(function($) {		
	$("select[name='entp_cd']").val("<c:out value='${param.searchEntpCd}'/>");	// 협력업체 선택값 세팅
});


	function PopupWindow(pageName) {
		var cw=400;
		var ch=300;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
	}

	/*
	function doSearch() {
		var form = document.forms[0];
		var url = "<c:url value='/edi/usply/NEDMUSP0050UpdateSelect.do'/>";
		var searchStartDate = $("#searchForm input[name='startDate']").val().replaceAll("-", "");
		var searchEndDate = $("#searchForm input[name='endDate']").val().replaceAll("-", "");
		var searchEntpCd = $("#searchForm select[name='entp_cd']").val();
		
		$('#doUpdateSearchForm #searchStartDate').val(searchStartDate);
		$('#doUpdateSearchForm #searchEndDate').val(searchEndDate);
		$('#doUpdateSearchForm #searchEntpCd').val(searchEntpCd);
		
		$('#doUpdateSearchForm').attr('action' , url);
		var form = document.forms[0];
		
		if(dateValid(form)){
		loadingMaskFixPos();
		$('#doUpdateSearchForm').submit();
		}
	}*/

	function doUpdateSearch() {
		var url = "<c:url value='/edi/usply/NEDMUSP0050UpdateSelect.do'/>";
		var searchStartDate = $("#searchForm input[name='srchStartDate']").val();
		var searchEndDate = $("#searchForm input[name='srchEndDate']").val();
		var searchEntpCd = $("#searchForm select[name='entp_cd']").val();
		var searchProductVal = $("#searchForm input[name='productVal']").val();		
						
		
		
	//	alert("searchProductVal:"+searchProductVal);
		
		$('#doUpdateSearchForm input[name="srchStartDate"]').val(searchStartDate);
		$('#doUpdateSearchForm input[name="srchEndDate"]').val(searchEndDate);
		$('#doUpdateSearchForm input[name="searchEntpCd"]').val(searchEntpCd);
		$('#doUpdateSearchForm input[name="searchProductVal"]').val(searchProductVal);
		
		$('#doUpdateSearchForm').attr('action' , url);
		
		if (dateValid()) {
			loadingMaskFixPos();
			$('#doUpdateSearchForm').submit();
		}	
	}
				 
	function uspSwap(idx){
//alert("uspSwap");
		var newHtm1 = "<select name='usp2'>"+
		 // "<option value='11'><spring:message code='epc.usp.reasonFg11'/></option>"+
		 // "<option value='13'><spring:message code='epc.usp.reasonFg13'/></option>"+
		 // "<option value='14'><spring:message code='epc.usp.reasonFg14'/></option>"+
		  
		  "<option value='31'><spring:message code='epc.usp.reasonFg31'/></option>"+		  
		  "</select>";

		var newHtm2 = "<select name='usp2'>"+
		//  "<option value='21'><spring:message code='epc.usp.reasonFg21'/></option>"+
		//  "<option value='23'><spring:message code='epc.usp.reasonFg23'/></option>"+
		//  "<option value='24'><spring:message code='epc.usp.reasonFg24'/></option>"+
		//  "<option value='25'><spring:message code='epc.usp.reasonFg25'/></option>"+
		  
		  "<option value='41'><spring:message code='epc.usp.reasonFg41'/></option>"+
		  "<option value='42'><spring:message code='epc.usp.reasonFg42'/></option>"+
		  "<option value='43'><spring:message code='epc.usp.reasonFg43'/></option>"+
		  "<option value='44'><spring:message code='epc.usp.reasonFg44'/></option>"+
		  "<option value='45'><spring:message code='epc.usp.reasonFg45'/></option>"+
		  "</select>";
		 
		if ($("select[name='usp1']").eq(idx).val() == "1") {
			$("#second_usp" + idx).html(newHtm1);
		} else {
			$("#second_usp" + idx).html(newHtm2);
		}		
	}

	function uspTopSwap(){

		var newHtml1 = "<select name='usp_top2'>"+
			//		  "<option value='11'><spring:message code='epc.usp.reasonFg11'/></option>"+
			//		  "<option value='13'><spring:message code='epc.usp.reasonFg13'/></option>"+
			//		  "<option value='14'><spring:message code='epc.usp.reasonFg14'/></option>"+
				  "<option value='31'><spring:message code='epc.usp.reasonFg31'/></option>"+
					  "</select>";

		var newHtml2 = "<select name='usp_top2'>"+
			//		  "<option value='21'><spring:message code='epc.usp.reasonFg21'/></option>"+
			//		  "<option value='23'><spring:message code='epc.usp.reasonFg23'/></option>"+
			//		  "<option value='24'><spring:message code='epc.usp.reasonFg24'/></option>"+
			//		  "<option value='25'><spring:message code='epc.usp.reasonFg25'/></option>"+
			 "<option value='41'><spring:message code='epc.usp.reasonFg41'/></option>"+
			 "<option value='42'><spring:message code='epc.usp.reasonFg42'/></option>"+
			 "<option value='43'><spring:message code='epc.usp.reasonFg43'/></option>"+
			 "<option value='44'><spring:message code='epc.usp.reasonFg44'/></option>"+
			 "<option value='45'><spring:message code='epc.usp.reasonFg45'/></option>"+
					  "</select>";
					  
		
		var uspTop1	=	$("select[name='usp_top1']").val();
		if (uspTop1 == "1") {			
			$("#usp_top_div").html(newHtml1);			
		}else if (uspTop1 == "2") {
			$("#usp_top_div").html(newHtml2);
		}
	}

	function batchSwap(){
//		alert("batchSwap 일괄적용");
  		var newHtml1 = "<select name='usp2'>"+
  	//	  "<option value='11'><spring:message code='epc.usp.reasonFg11'/></option>"+
  	//	  "<option value='13'><spring:message code='epc.usp.reasonFg13'/></option>"+
  	//	  "<option value='14'><spring:message code='epc.usp.reasonFg14'/></option>"+
  	 "<option value='31'><spring:message code='epc.usp.reasonFg31'/></option>"+
  	 
  		  "</select>";
// alert("newHtml1:"+newHtml1);  
  		  var newHtml2 = "<select name='usp2'>"+
  	//	  "<option value='21'><spring:message code='epc.usp.reasonFg21'/></option>"+
  	//	  "<option value='23'><spring:message code='epc.usp.reasonFg23'/></option>"+
  	//	  "<option value='24'><spring:message code='epc.usp.reasonFg24'/></option>"+
  	//	  "<option value='25'><spring:message code='epc.usp.reasonFg25'/></option>"+
  		  "<option value='41'><spring:message code='epc.usp.reasonFg41'/></option>"+
  		  "<option value='42'><spring:message code='epc.usp.reasonFg42'/></option>"+
  		  "<option value='43'><spring:message code='epc.usp.reasonFg43'/></option>"+
  		  "<option value='44'><spring:message code='epc.usp.reasonFg44'/></option>"+
  		  "<option value='45'><spring:message code='epc.usp.reasonFg45'/></option>"+
  		  "</select>";
// alert("newHtml2:"+newHtml2);  	
	var chkData	=	$("input[name='ckValid']").val();
//alert("chkData:"+chkData);
	
		if(chkData=="empty"){
			alert("<spring:message code='epc.usp.search.noResult'/>");		//조회된 데이터가 없습니다
			return;
		}
		
		var uspTop1	=	$("select[name='usp_top1']").val();
	//	alert("uspTop1:"+uspTop1);
		
		var uspTop2	=	$("select[name='usp_top2']").val();
	//	alert("uspTop2:"+uspTop2);
		
		
		$("#resultList tr").each(function(i) {
			$("select[name='usp1']").eq(i).val(uspTop1);
			
			//$("#second_usp" + i).html("");
			
 			if (uspTop1 == "1") {		
 			//	alert("11111");
 				$("#second_usp" + i).html(newHtml1);
 				$("select[name='usp2']").eq(i).val(uspTop2);				
 			}  else if (uspTop1 == "2") {
  			//	alert("22222");
  				$("#second_usp" + i).html(newHtml2);
  				$("select[name='usp2']").eq(i).val(uspTop2);
 			}
						
		});
		
		
		/* var form = document.forms[0];
		
		
		
		if(form.rowcnt.length){
			if(form.usp_top1.value == "1"){
				for(var i=0;i<form.usp1.length;i++){
					form.usp1[i].value=1;
					document.getElementById("second_usp"+i).innerHTML=newHtm1;
					form.usp2[i].value=form.usp_top2.value;
				}
			}else if(form.usp_top1.value == "2"){
				for(var i=0;i<form.usp1.length;i++){
					form.usp1[i].value=2;
					document.getElementById("second_usp"+i).innerHTML=newHtm2;
					form.usp2[i].value=form.usp_top2.value;
				}
			}
		}else{
			if(form.usp_top1.value == "1"){
					form.usp1.value=1;
					document.getElementById("second_usp0").innerHTML=newHtm1;
					form.usp2.value=form.usp_top2.value;
			}else if(form.usp_top1.value == "2"){
					form.usp1.value=2;
					document.getElementById("second_usp0").innerHTML=newHtm2;
					form.usp2.value=form.usp_top2.value;
			}
		} */

		

	}
	
	function doUpdate(){
		if (!confirm('<spring:message code="msg.common.confirm.save" />')) {
			return;
		}
		
		//서버 날짜 가져오기
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/delivery/NEDMDLY0120ServerDate.json"/>',
			data : '',
			success : function(data) {
				//서버날짜 셋팅
				doUpdateProcess(data.nowDate);
			}
		});
	}

	function doUpdateProcess(nowDate) {

		var chkData	=	$("input[name='ckValid']").val();
		
		if(chkData=="empty"){
			alert("<spring:message code='epc.usp.search.noResult'/>");		//조회된 데이터가 없습니다
			return;
		}
		var updateParam = {};
		var arrPEDMUSP0000VO = new Array();
		$('#resultList tr').each(function(){	
			var PEDMUSP0000VO = {};
			PEDMUSP0000VO["ORD_SLIP_NO"] =$(this).data('ordSlipNo');
			PEDMUSP0000VO["REG_VSEQ"] =$(this).data('regVseq');
			PEDMUSP0000VO["VEN_REASON_FG"] = $(this).find('select[name=usp1]').val();
			PEDMUSP0000VO["VEN_DETAIL_FG"] = $(this).find('select[name=usp2]').val();
			PEDMUSP0000VO["VEN_CFM_FG"] = '1';
			PEDMUSP0000VO["VEN_CFM_DT"] = nowDate;
			arrPEDMUSP0000VO.push(PEDMUSP0000VO);
		});
		//updateParam['updateParam'] = arrPEDMUSP0000VO;
		//console.log(updateParam);
		var dataInfo = {};
		dataInfo["TAB"] = arrPEDMUSP0000VO;
		
		// 공통 RequestCommon
		dataInfo["REQCOMMON"] = getReqCommon(); 
		//console.log(dataInfo);
		rfcCall("PUR0190" , dataInfo);
	}
	
	function rfcCallBack(data){
		//ORD_SLIP_NO PROD_CD
		var TAB = data.result.TAB;
		var updateParam = {};
		var arrPEDMUSP0000VO = new Array();
		if($('#resultList tr.r1').length == 1){
			if(TAB.STATUS != 'S'){
				alert("TAB.ORD_SLIP_NO = " + TAB.ORD_SLIP_NO +",TAB.PROD_CD=" +TAB.PROD_CD + ",STATUS :" + TAB.STATUS);
				return;
			}
			var ORD_SLIP_NO = TAB.ORD_SLIP_NO;
			var REG_VSEQ = TAB.REG_VSEQ;
			
			$('#resultList tr.r1').each(function(){
				var ordSlipNo = $(this).data('ordSlipNo');
				var regVseq = $(this).data('regVseq');
				if(ORD_SLIP_NO == ordSlipNo && REG_VSEQ == regVseq){
					var PEDMUSP0000VO = {};
					PEDMUSP0000VO["ordSlipNo"] =$(this).data('ordSlipNo');
					PEDMUSP0000VO["prodCd"] =$(this).data('prodCd');
					PEDMUSP0000VO["regVseq"] =$(this).data('regVseq');
					PEDMUSP0000VO["venReasonFg"] = $(this).find('select[name=usp1]').val();
					PEDMUSP0000VO["venDetailFg"] = $(this).find('select[name=usp2]').val();
					arrPEDMUSP0000VO.push(PEDMUSP0000VO);
				}
			});
		}else{
			for(var i=0;i<TAB.length;i++){
				var ORD_SLIP_NO = TAB[i].ORD_SLIP_NO;
				var REG_VSEQ = TAB[i].REG_VSEQ;
				if(TAB[i].STATUS != 'S'){
					alert("TAB.ORD_SLIP_NO = " + TAB[i].ORD_SLIP_NO +",TAB.REG_VSEQ=" +TAB[i].REG_VSEQ + ",STATUS :" + TAB[i].STATUS);
					continue;
				}
				
				$('#resultList tr.r1').each(function(){
					var ordSlipNo = $(this).data('ordSlipNo');
					var regVseq = $(this).data('regVseq');
					if(ORD_SLIP_NO == ordSlipNo && REG_VSEQ == regVseq){
						var PEDMUSP0000VO = {};
						PEDMUSP0000VO["ordSlipNo"] =$(this).data('ordSlipNo');
						PEDMUSP0000VO["prodCd"] =$(this).data('prodCd');
						PEDMUSP0000VO["regVseq"] =$(this).data('regVseq');
						PEDMUSP0000VO["venReasonFg"] = $(this).find('select[name=usp1]').val();
						PEDMUSP0000VO["venDetailFg"] = $(this).find('select[name=usp2]').val();
						arrPEDMUSP0000VO.push(PEDMUSP0000VO);
					}
				});
			}
		}
		
		
		
		updateParam['updateParam'] = arrPEDMUSP0000VO;
		$.ajax({
			contentType : 'application/json; charset=utf-8',	
			type: "post",
			dataType: "json",
			url: "<c:url value='/edi/usply/NEDMUSP0050Update.json'/>",
			data: JSON.stringify(updateParam),
			success: function(data) {
				alert("<spring:message code='epc.usp.saveSuccess'/>");
			}
		});
	}



	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}


	function dateValid(){

		var startDate = $("#searchForm input[name='srchStartDate']").val();
		var endDate = $("#searchForm input[name='srchEndDate']").val();
		
		var rangeDate = 0;
		
		if(startDate == "" || endDate == ""  ){
			alert("<spring:message code='msg.common.fail.nocalendar'/>");
			form.startDate.focus();
			return false;
		}


		// startDate, endDate 는 yyyy-mm-dd 형식

	    startDate = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10);
	    endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10);

	   var intStartDate = parseInt(startDate);
	   var intEndDate = parseInt(endDate);
			
		
	    if (intStartDate > intEndDate) {
	        alert("<spring:message code='msg.common.fail.calendar'/>");
	        form.startDate.focus();
	        return false;
	    }

		
	    intStartDate = new Date(startDate.substring(0, 4),startDate.substring(4,6),startDate.substring(6, 8),0,0,0); 
	    endDate = new Date(endDate.substring(0, 4),endDate.substring(4,6),endDate.substring(6, 8),0,0,0); 


	    rangeDate=Date.parse(endDate)-Date.parse(intStartDate);
	    rangeDate=Math.ceil(rangeDate/24/60/60/1000);

		if(rangeDate>15){
		//	alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			alert("<spring:message code='epc.usp.validDate'/>");
			
			form.startDate.focus();
			return false;
		}	
		return true;
		
	}
	
	function productClear(){
		$("#searchForm input[id='productVal']").val("");
	//	$("#searchForm input[id='searchSrcmkVal']").val("");
	}

</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if> >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post" action="#">
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" >
		
		<input type="hidden" id="productVal" 		name="productVal" 		value="<c:out value="${param.searchProductVal }" />" />
		<%-- <input type="hidden" id="searchProductVal"	name="searchProductVal"	value="<c:out value="${param.searchProductVal }" />" /> --%>
		
		
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.usp.search.condition'/></li>
						<li class="btn">
							<a href="#" class="btn" onclick="doUpdateSearch();"><span><spring:message code='epc.usp.createSelect'/></span></a>
							<a href="#" class="btn" onclick="doUpdate();"><span><spring:message code="button.common.save"/></span></a>
	
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
		
		
					<input type="hidden" id="forward_hour" name="forwardUsp1">
					<input type="hidden" id="forward_hour" name="forwardUsp2">
					<input type="hidden" id="forward_hour" name="forwardProd_cd">
					<input type="hidden" id="forward_hour" name="forwardOrd_slip_no">
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> <spring:message code='epc.usp.search.searchPeriod'/> </th>
						<td colspan="2">
							<input type="text" class="day" id="srchStartDate" name="srchStartDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value="${param.srchStartDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="srchEndDate" name="srchEndDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="<c:out value="${param.srchEndDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');"  style="cursor:hand;" />
						</td>
						<th><spring:message code='epc.usp.search.entpCd'/></th>
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form" defName="전체" />
						
						</td>
					</tr>
					<tr>	
						<th><spring:message code='epc.usp.header.venReasonNmOri'/></th>
						<td colspan="2">
							1 : <select name="usp_top1" onchange="javsscript:uspTopSwap();">
								
								<option value="1"><spring:message code='epc.usp.reasonFg1'/></option>
								<option value="2"><spring:message code='epc.usp.reasonFg2'/></option>
							</select> &nbsp;&nbsp;
							2 :<span id="usp_top_div">
								<select name="usp_top2">
						<!--		<option value="11"><spring:message code='epc.usp.reasonFg11'/></option>
									<option value="13"><spring:message code='epc.usp.reasonFg13'/></option>
									<option value="14"><spring:message code='epc.usp.reasonFg14'/></option>
						 -->
						 <option value="31"><spring:message code='epc.usp.reasonFg31'/></option>
								</select>
							</span>&nbsp;&nbsp;
							<a href="#" class="btn" onclick="batchSwap();"><span><spring:message code='epc.usp.createBulk'/></span></a>
						</td>						
							
						
						<th><spring:message code='epc.usp.search.prodCd'/></th>
						<td >
							<input type="Radio"  name="prodCdType" <c:if test="${empty param.searchProductVal }"> Checked</c:if>  onclick="javascript:productClear();"/><spring:message code='epc.usp.search.allProd'/>   
							<input type="Radio"  name="prodCdType" <c:if test="${not empty param.searchProductVal }"> Checked</c:if> onclick="javascript:productPopUp();"/><spring:message code='epc.usp.search.prodSelect'/>
						</td>
										
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
						<li class="tit"><spring:message code='epc.usp.search.resultSearchtxt'/></li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col style="width:70px;" />
							<col style="width:100px;" />
						
							<col style="width:150px;" />
					<!--		<col style="width:40px;" />
							<col style="width:40px;" />
					-->		<col style="width:70px;" />
							<col style="width:60px;" />
							<col style="width:60px;" />
							<col style="width:80px;" />
							<col style="width:140px;" />
							<col />
						</colgroup>
						<tr>
						<!-- 	<th><spring:message code='epc.usp.header.prodCdSrcmkCd'/></th> -->
						<th><spring:message code='epc.usp.header.splyDy'/></th>
						<th><spring:message code='epc.usp.header.prodCdSrcmkCd'/></th> 
							<th><spring:message code='epc.usp.header.prodNm'/></th>
					<!--		<th><spring:message code='epc.usp.header.ordIpsu'/></th>
							<th><spring:message code='epc.usp.header.unit'/></th>
					-->		<th><spring:message code='epc.usp.header.strNm'/></th>
							<th><spring:message code='epc.usp.header.usplyQty'/></th>
							<th><spring:message code='epc.usp.header.usplyBuyAmt'/></th>
							<th><spring:message code='epc.usp.header.venReasonNm'/></th>
							<th><spring:message code='epc.usp.header.venDetailNm'/></th>
							<th><spring:message code='epc.usp.header.confirmYN'/></th>
						</tr>
					</table>
					<div class="datagrade_scroll">
						<table id="resultList" class="bbs_list" cellpadding="0" cellspacing="0" border="0">
							<colgroup>
								<col style="width:70px;" />
								<col style="width:100px;" />
								<col style="width:150px;" />
							<!--	<col style="width:40px;" />
								<col style="width:40px;" />
							-->	<col style="width:70px;" />
								<col style="width:60px;" />
								<col style="width:60px;" />
								<col style="width:80px;" />
								<col style="width:140px;" />
								<col  />
							</colgroup>
							<c:if test="${not empty usplyList }">
								<input type="hidden" name="ckValid" value="exist"/>
								<c:set var="idx"  value="0" />
								<c:forEach items="${usplyList}" var="list" varStatus="index" >
									<input type="hidden" name="rowcnt"/>
									<tr class="r1" data-ord-slip-no = "<c:out value="${list.ordSlipNo }" />" data-prod-cd = "<c:out value="${list.prodCd }" />"  data-reg-vseq = "<c:out value="${list.regVseq }" />">
									
									<td align="center"><c:out value="${list.splyDy }" /></td>
									
									<td align="center"><c:out value="${list.prodCd }" /><br>/<c:out value="${list.srcmkCd }" /></td>
									
										<td align="left"><c:out value="${list.prodNm }" /></td>
						<!--				<td align="right"><fmt:formatNumber value="${list.ordIpsu }" type="number" currencySymbol="" />&nbsp;</td>
										<td align="center"><c:out value="${list.ordUnit }" /></td>
						-->				<td align="center"><c:out value="${list.strNm }" /></td>
										<td align="right"><fmt:formatNumber value="${list.usplyQty}" type="number" currencySymbol="" />&nbsp;</td>
										<td align="right"><fmt:formatNumber value="${list.usplyBuyAmt}" type="number" currencySymbol="" />&nbsp;</td>
										<c:if test="${list.venReasonFg eq null }">
										<td align="center">
											<select name="usp1" onchange="javsscript:uspSwap('<c:out value="${idx }" />');">
												<option value="1"><spring:message code='epc.usp.reasonFg1'/></option>
												<option value="2"><spring:message code='epc.usp.reasonFg2'/></option>
											</select>
										</td>
										<td align="center">
											<div id="second_usp<c:out value="${idx }" />">
												<select name="usp2">
												<!--<option value="11"><spring:message code='epc.usp.reasonFg11'/></option>
													<option value="13"><spring:message code='epc.usp.reasonFg13'/></option>
													<option value="14"><spring:message code='epc.usp.reasonFg14'/></option>
												 -->
												 <option value="31"><spring:message code='epc.usp.reasonFg31'/></option>
												</select>
											</div>
										</td>
										</c:if>
										
										<c:if test="${list.venReasonFg eq 1 }">
										
										<td align="center">
											<select name="usp1" onchange="javsscript:uspSwap('<c:out value="${idx }" />');">
												<option value="1" selected><spring:message code='epc.usp.reasonFg1'/></option>
												<option value="2"><spring:message code='epc.usp.reasonFg2'/></option>
											</select>
										</td>
										<td align="center">
											<div id="second_usp${idx }">
												<select name="usp2">
											<!--	<option value="11" <c:if test="${list.venDetailFg eq 11}">selected</c:if> ><spring:message code='epc.usp.reasonFg11'/></option>
													<option value="13" <c:if test="${list.venDetailFg eq 13}">selected</c:if> ><spring:message code='epc.usp.reasonFg13'/></option>
													<option value="14" <c:if test="${list.venDetailFg eq 14}">selected</c:if> ><spring:message code='epc.usp.reasonFg14'/></option>
											-->
											<option value="31" <c:if test="${list.venDetailFg eq 31}">selected</c:if> ><spring:message code='epc.usp.reasonFg31'/></option>
												</select>
											</div>
										</td>
										</c:if>
										
										<c:if test="${list.venReasonFg eq 2 }">
										<td align="center">
											<select name="usp1" onchange="javsscript:uspSwap('<c:out value="${idx }" />');">
												<option value="1" ><spring:message code='epc.usp.reasonFg1'/></option>
												<option value="2" selected><spring:message code='epc.usp.reasonFg2'/></option>
											</select>
										</td>
										<td align="center">
											<div id="second_usp<c:out value="${idx }" />">
												<select name="usp2">
												<!--  	<option value='21' <c:if test="${list.venDetailFg eq 21}">selected</c:if> ><spring:message code='epc.usp.reasonFg21'/></option>
													<option value='23' <c:if test="${list.venDetailFg eq 23}">selected</c:if> ><spring:message code='epc.usp.reasonFg23'/></option>
													<option value='24' <c:if test="${list.venDetailFg eq 24}">selected</c:if> ><spring:message code='epc.usp.reasonFg24'/></option>
													<option value='25' <c:if test="${list.venDetailFg eq 25}">selected</c:if> ><spring:message code='epc.usp.reasonFg25'/></option>
												-->
												<option value='41' <c:if test="${list.venDetailFg eq 41}">selected</c:if> ><spring:message code='epc.usp.reasonFg41'/></option>
												<option value='42' <c:if test="${list.venDetailFg eq 42}">selected</c:if> ><spring:message code='epc.usp.reasonFg42'/></option>
												<option value='43' <c:if test="${list.venDetailFg eq 43}">selected</c:if> ><spring:message code='epc.usp.reasonFg43'/></option>
												<option value='44' <c:if test="${list.venDetailFg eq 44}">selected</c:if> ><spring:message code='epc.usp.reasonFg44'/></option>
												<option value='45' <c:if test="${list.venDetailFg eq 45}">selected</c:if> ><spring:message code='epc.usp.reasonFg45'/></option>
												</select>
											</div>
										</td>
										</c:if>
										<td></td>
									</tr>
									<c:set var="idx" value="${idx + 1 }" />
								</c:forEach>
							</c:if>
							<c:if test="${empty usplyList }">
								<input type="hidden" name="ckValid" value="empty"/>
								<tr><td colspan="11" align=center><spring:message code='epc.usp.search.noResult'/></td></tr>
							</c:if>
						</table>
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>
<form id="doUpdateSearchForm" name="doUpdateSearchForm" method="post" action="#">
	<input type = "hidden" id ="srchStartDate" name = "srchStartDate" />
	<input type = "hidden" id ="srchEndDate" name = "srchEndDate" />
	<input type = "hidden" id ="searchEntpCd" name = "searchEntpCd" /> 
	<input type = "hidden" id ="searchProductVal" name = "searchProductVal" /> 	
	
</form>

	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li><spring:message code='epc.usp.home'/></li>
					<li><spring:message code='epc.usp.usplyInfo'/></li>
					<li><spring:message code='epc.usp.dateInfo'/></li>
					<li class="last"><spring:message code='epc.usp.venReasonInputAndInquiries'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
</body>
</html>
