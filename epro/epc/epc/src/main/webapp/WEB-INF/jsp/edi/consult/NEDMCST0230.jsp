<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><spring:message code='text.consult.field.lotteVendorConsult' /></title>

<script language="JavaScript">

	$(document).ready(function($) {
		$('#dataListbody tr').live('click', function(e) {
			
			var detailDataInfo = {};
			
			var obj = $(this).children().last();	// last-td		
			
			detailDataInfo['bcustNm'] = $(obj).children("input[name='bcustNm']").val();
			detailDataInfo['bhpNo'] = $(obj).children("input[name='bhpNo']").val();
			detailDataInfo['bzip'] = $(obj).children("input[name='bzip']").val();
			detailDataInfo['bnewAddrFg'] = $(obj).children("input[name='bnewAddrFg']").val();
			detailDataInfo['baddr1'] = $(obj).children("input[name='baddr1']").val();
			detailDataInfo['baddr2'] = $(obj).children("input[name='baddr2']").val();
			detailDataInfo['bnewAddr1'] = $(obj).children("input[name='bnewAddr1']").val();
			detailDataInfo['bnewAddr2'] = $(obj).children("input[name='bnewAddr2']").val();
			detailDataInfo['bnewAddr3'] = $(obj).children("input[name='bnewAddr3']").val();
			detailDataInfo['bnewAddr4'] = $(obj).children("input[name='bnewAddr4']").val();
			detailDataInfo['bslipNo'] = $(obj).children("input[name='bslipNo']").val();
			detailDataInfo['bregDy'] = $(obj).children("input[name='bregDy']").val();
			detailDataInfo['bean11'] = $(obj).children("input[name='bean11']").val();
			detailDataInfo['bprodNm'] = $(obj).children("input[name='bprodNm']").val();
			detailDataInfo['breqCmt'] = $(obj).children("input[name='breqCmt']").val();
			detailDataInfo['bprocStat'] = $(obj).children("input[name='bprocStat']").val();
			detailDataInfo['brepairScheDt'] = $(obj).children("input[name='brepairScheDt']").val();
			detailDataInfo['brepairCompDt'] = $(obj).children("input[name='brepairCompDt']").val();
			detailDataInfo['btransCompDt'] = $(obj).children("input[name='btransCompDt']").val();
			
			if ($(obj).children("input[name='bslipNo']").val() != "" && $(obj).children("input[name='bslipNo']").val() != null) {
				doDetailView(detailDataInfo);
			} else {
				return false;
			} 
		});
	});
	
	/* 상세정보 */
	function doDetailView(detailDataInfo){
		
		$("span[id='spCustNm']").text(detailDataInfo['bcustNm']);
		$("span[id='spHpNo']").text(toPhoneFormat(detailDataInfo['bhpNo']));
		
		if(detailDataInfo['bnewAddrFg'] == '0'){
			/* $("input[name='newAddrFg']").prop('checked', false); */
			$("span[id='spZipAddress']").text("(" + detailDataInfo['bzip'] + ") " + detailDataInfo['baddr1'] + " " + detailDataInfo['baddr2']);
		}else{	//detailDataInfo['bnewAddrFg'] == '1'
			/* $("input[name='newAddrFg']").prop('checked', true); */
			$("span[id='spZipAddress']").text("(" + detailDataInfo['bzip'] + ") " + detailDataInfo['bnewAddr1'] + " " + detailDataInfo['bnewAddr2']+ " " + detailDataInfo['bnewAddr3'] + " " + detailDataInfo['bnewAddr4']);
		}
		
		//$("span[id='spSlipNo']").text(detailDataInfo['bslipNo']);		// 접수번호
		$("span[id='spRegDy']").text(detailDataInfo['bregDy']);			// 접수일자
		
		$("span[id='spEan11']").text(detailDataInfo['bean11']);			// 판매코드
		$("span[id='spProdNm']").text(detailDataInfo['bprodNm']);		// 상품명
		
		$("input[name='slipNo']").val(detailDataInfo['bslipNo']);
		//$("input[name='regDy']").val(detailDataInfo['bregDy']);
		$("input[name='ean11']").val(detailDataInfo['bean11']);
		$("input[name='prodNm']").val(detailDataInfo['bprodNm']);
		$("textarea[name='reqCmt']").val(detailDataInfo['breqCmt']);
		$("select[name='procStat']").val(detailDataInfo['bprocStat']);
		$("input[name='repairScheDt']").val(detailDataInfo['brepairScheDt']);
		$("input[name='repairCompDt']").val(detailDataInfo['brepairCompDt']);
		$("input[name='transCompDt']").val(detailDataInfo['btransCompDt']);
	}
	
	function doSearch(){
		goPage('1');
	}
	
	
	function goPage(pageIndex) {
		var searchInfo = {};
		
		// 조회기간(From)
		searchInfo["srchStartDate"] 	= $("#searchForm input[name='srchStartDate']").val();
		// 조회기간(To)
		searchInfo["srchEndDate"] 		= $("#searchForm input[name='srchEndDate']").val();
		// 협력업체(개별)
		searchInfo["srchEntpCd"] 		= $("#searchForm select[name='srchEntpCd']").val();
		// 진행상태
		searchInfo["srchProcStat"] 		= $("#searchForm select[name='srchProcStat']").val();
		// page
		searchInfo["pageIndex"] 		= pageIndex;					
		
		if(dateValid()){
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				//url : '${ctx}/edi/product/test.json',
				url : '<c:url value="/edi/consult/NEDMCST0230Select.json" />',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					$("#pageIndex").val(pageIndex);
					//json 으로 호출된 결과값을 화면에 Setting 
					_setTbodyMasterValue(data);
				}
			});	
		}
		
		
		
	}
	
	
	/* _eventSearch() 후처리(data  객체 그리기) */
	function _setTbodyMasterValue(json) { 
		
		var data = json.asList;
		setTbodyInit("dataListbody");	// dataList 초기화
		
		if (data.length > 0) {
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody"); // setComma(n)
			$('#dataListbody tr').css('cursor', 'pointer');
			$("#paging-ul").html(json.contents);
			
		} else { 
			setTbodyNoResult("dataListbody", 8);
			$("#paging-ul").html("");
		}
	}
	


	function dateValid(){

		var startDate 	= $("#searchForm input[name='srchStartDate']").val();
		var endDate 	= $("#searchForm input[name='srchEndDate']").val();
		
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

	    
		if(rangeDate>30){
			alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			form.startDate.focus();
			return false;
		}
	    
		return true;
		
	}
	
	
	function _eventSave(){
		var slipNo = $("input[name='slipNo']").val();
		
		if (slipNo != "" && slipNo != null) {
			
			if($("select[name='procStat']").val() == "3"){//업체입고
				if($("input[name='repairScheDt']").val() == "" || $("input[name='repairScheDt']").val() == null){
					alert("<spring:message code='epc.cst.alert.msg24' />");
					return;
				}
			}else if($("select[name='procStat']").val() == "4"){//수리완료
				if($("input[name='repairCompDt']").val() == "" || $("input[name='repairCompDt']").val() == null){
					alert("<spring:message code='epc.cst.alert.msg25' />");
					return;
				}
			}else if($("select[name='procStat']").val() == "5"){//고객인도
				if($("input[name='transCompDt']").val() == "" || $("input[name='transCompDt']").val() == null){
					alert("<spring:message code='epc.cst.alert.msg26' />");
					return;
				}
			}else if($("select[name='procStat']").val() == "6"){//점포입고
				if($("input[name='repairCompDt']").val() == "" || $("input[name='repairCompDt']").val() == null){
					alert("<spring:message code='epc.cst.alert.msg27' />");
					return;
				}
			}
			
			
			
			if(confirm("<spring:message code='msg.common.confirm.save' />")){
				
				var dataInfo = {};
				
				dataInfo["SLIP_NO"] = slipNo				
				dataInfo["PROC_STAT"] = $("select[name='procStat']").val();
				dataInfo["AS_REQ_DY"] = $("input[name='repairScheDt']").val().replaceAll("-","");
				dataInfo["AS_FIN_DY"] = $("input[name='repairCompDt']").val().replaceAll("-","");
				dataInfo["AS_DELI_DY"] = $("input[name='transCompDt']").val().replaceAll("-","");
				//console.log(dataInfo);
				
				rfcCall("CUS0400" , dataInfo);
			}			
			
		}else{
			alert("<spring:message code='epc.cst.alert.msg23' />");
			return;
		}
	}
	
	
	function rfcCallBack(data){
		var result = data.result;
		
		if(result.RESPCOMMON.ZPOSTAT == 'S'){
			
			var dataInfo = {};
			
			dataInfo["slipNo"] = $("input[name='slipNo']").val();				
			dataInfo["procStat"] = $("select[name='procStat']").val();
			dataInfo["repairScheDt"] = $("input[name='repairScheDt']").val().replaceAll("-","");
			dataInfo["repairCompDt"] = $("input[name='repairCompDt']").val().replaceAll("-","");
			dataInfo["transCompDt"] = $("input[name='transCompDt']").val().replaceAll("-","");
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				//url : '${ctx}/edi/product/test.json',
				url : '<c:url value="/edi/consult/NEDMCST0230Update.json" />',
				data : JSON.stringify(dataInfo),
				success : function(data) {
					if(data.cnt > 0)	alert('<spring:message code="msg.common.success.save" />');
					else				alert('<spring:message code="msg.common.fail.save" />');
				}
			});	
			
			//location.reload();
		}else{
			alert('<spring:message code="msg.common.fail.save" />');
		}
		
		
	}
	
	function toPhoneFormat(str) {
		 var phoneNo = "";
		 var number = str.replaceAll("-", "");
		 
		 if (number.length < 2) {
		  return;
		 }
		 /*
		 if (number.substring(0, 2) == "02") {
		 	if (number.length == 2) {
		   		phoneNo = number + "-";
		  	} else if (number.length == 5) {
		   		phoneNo += number.substring(0, 2) + "-" + "***" + "-";
		  	} else if (number.length >  9) {
		   		phoneNo += number.substring(0, 2) + "-" + "****" + "-" + number.substring(6, 10);
		  	} else if (number.length == 9) {
		   		phoneNo += number.substring(0,2) + "-" + "***" + "-" + number.substring(5, 10);
		  	} else {
		   		phoneNo = str;
		  	}
		 } else {
		  	if (number.length == 3) {
		   		phoneNo = number + "-";
		  	} else if (number.length ==  6) {
		   		phoneNo += number.substring(0, 3) + "-" + "***" + "-";
		  	} else if (number.length >  10) {
		   		phoneNo += number.substring(0, 3) + "-" + "****" + "-" + number.substring(7);
		  	} else if (number.length == 10) {
		   		phoneNo += number.substring(0, 3) + "-" + "***" + "-" + number.substring(6);
		  	} else {
		   		phoneNo = str;
		  	}
		 }
		 */
		 
		 if (number.substring(0, 2) == "02") {
			 	if (number.length == 2) {
			   		phoneNo = number + "-";
			  	} else if (number.length == 5) {
			   		phoneNo += number.substring(0, 2) + "-" + number.substring(2, 5) + "-";
			  	} else if (number.length >  9) {
			   		phoneNo += number.substring(0, 2) + "-" + number.substring(2, 6) + "-" + number.substring(6, 10);
			  	} else if (number.length == 9) {
			   		phoneNo += number.substring(0,2) + "-" + number.substring(2, 5) + "-" + number.substring(5, 10);
			  	} else {
			   		phoneNo = str;
			  	}
			 } else {
			  	if (number.length == 3) {
			   		phoneNo = number + "-";
			  	} else if (number.length ==  6) {
			   		phoneNo += number.substring(0, 3) + "-" + number.substring(2, 5) + "-";
			  	} else if (number.length >  10) {
			   		phoneNo += number.substring(0, 3) + "-" + number.substring(2, 6) + "-" + number.substring(7);
			  	} else if (number.length == 10) {
			   		phoneNo += number.substring(0, 3) + "-" + number.substring(2, 5) + "-" + number.substring(6);
			  	} else {
			   		phoneNo = str;
			  	}
			 }
		 
		 return phoneNo;
	}
	
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="r1">
	<td align="center"><c:out value="\${rnum}"/></td>
	<td align="center"><c:out value="\${slipNo}"/></td>
	<td align="center"><c:out value="\${custNm}"/></td>
	<td align="center"><c:out value="\${regDy}"/></td>
	<td align="center"><c:out value="\${ean11}"/></td>
	<td><c:out value="\${prodNm}"/></td>
	<td align="center"><c:out value="\${lifnr}"/></td>
	<td align="center">
		<c:out value="\${procStatNm}"/>
		<input type="hidden" name="bcustNm" id="bcustNm" value="<c:out value="\${custNm}" />" />
		<input type="hidden" name="bhpNo" id="bhpNo" value="<c:out value="\${hpNo}" />" />
		<input type="hidden" name="bnewAddrFg" id="bnewAddrFg" value="<c:out value="\${newAddrFg}" />" />
		<input type="hidden" name="bzip" id="bzip" value="<c:out value="\${zip}" />" />
		<input type="hidden" name="baddr1" id="baddr1" value="<c:out value="\${addr1}" />" />
		<input type="hidden" name="baddr2" id="baddr2" value="<c:out value="\${addr2}" />" />
		<input type="hidden" name="bnewAddr1" id="bnewAddr1" value="<c:out value="\${newAddr1}" />" />
		<input type="hidden" name="bnewAddr2" id="bnewAddr2" value="<c:out value="\${newAddr2}" />" />
		<input type="hidden" name="bnewAddr3" id="bnewAddr3" value="<c:out value="\${newAddr3}" />" />
		<input type="hidden" name="bnewAddr4" id="bnewAddr4" value="<c:out value="\${newAddr4}" />" />
		<input type="hidden" name="bslipNo" id="bslipNo" value="<c:out value="\${slipNo}" />" />
		<input type="hidden" name="bregDy" id="bregDy" value="<c:out value="\${regDy}" />" />
		<input type="hidden" name="bean11" id=bean11" value="<c:out value="\${ean11}" />" />
		<input type="hidden" name="bprodNm" id="bprodNm" value="<c:out value="\${prodNm}" />" />
		<input type="hidden" name="breqCmt" id="breqCmt" value="<c:out value="\${reqCmt}" />" />
		<input type="hidden" name="bprocStat" id="bprocStat" value="<c:out value="\${procStat}" />" />
		<input type="hidden" name="brepairScheDt" id="brepairScheDt" value="<c:out value="\${repairScheDt}" />" />
		<input type="hidden" name="brepairCompDt" id="brepairCompDt" value="<c:out value="\${repairCompDt}" />" />
		<input type="hidden" name="btransCompDt" id="btransCompDt" value="<c:out value="\${transCompDt}" />" />
	</td>
</tr>
</script>

<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->

</head>

<body>
<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:<c:out value="${param.widthSize }" /></c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id = "searchForm" name="searchForm" method="post" action="#" >
		
		<input type="hidden" id="widthSize" name="widthSize" value="<c:out value="${param.widthSize }" />" /> 
		<input type="hidden" id="pageIndex" name="pageIndex" value="<c:out value="${param.pageIndex}" />" />
		
		<div id="wrap_menu">
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.cst.as'/></li>
						<li class="btn">
							<a href="#" class="btn" onclick="javascript:doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="width:20%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> <spring:message code='epc.cst.search.regDy'/> </th>
						<td>
							<input type="text" class="day" id="srchStartDate" name="srchStartDate"  maxlength="10" readonly value="<c:out value="${paramMap.srchStartDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchStartDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="srchEndDate" name="srchEndDate"  maxlength="10" readonly value="<c:out value="${paramMap.srchEndDate}" />" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDate');"  style="cursor:hand;" />
						</td>
							
						<th><spring:message code='epc.cst.search.entpCd2'/></th>
						<td>
							<html:codeTag objId="srchEntpCd" objName="srchEntpCd" width="75px;" dataType="CP" comType="SELECT" formName="form" defName="전체" />
						</td>
						<th><spring:message code='epc.cst.search.procStat'/></th>
						<td>
							<html:codeTag objId="srchProcStat" objName="srchProcStat" comType="SELECT" formName="form" parentCode="AS001" defName="전체"></html:codeTag>
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
						<li class="tit"><spring:message code='epc.cst.search.asList'/></li>
					</ul>
					<div style="width:100%; height:200px; overflow-y:scroll; overflow-x:scroll;">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="addTable">
							<colgroup>
								<col style="width:30px;" />
								<col style="width:40px;" />
								<col style="width:30px;" />
								<col style="width:30px;" />
								<col style="width:40px;" />
								<col style="width:100px;" />
								<col style="width:30px;" />
								<col style="width:30px;" />
							</colgroup>
							<tr>
								<th><spring:message code='epc.cst.header.asCode1'/></th>
								<th><spring:message code='epc.cst.header.asCode2'/></th>
								<th><spring:message code='epc.cst.header.asCode3'/></th>
								<th><spring:message code='epc.cst.header.asCode4'/></th>
								<th><spring:message code='epc.cst.header.asCode5'/></th>
								<th><spring:message code='epc.cst.header.asCode6'/></th>
								<th><spring:message code='epc.cst.header.asCode7'/></th>
								<th><spring:message code='epc.cst.header.asCode8'/></th>
							</tr>
							<!-- Data List Body Start ------------------------------------------------------------------------------>
							<tbody id="dataListbody" />
							<!-- Data List Body End   ------------------------------------------------------------------------------>
							</table>
					</div>
				</div>
				
				<!-- Pagging Start ---------->			
				<div id="paging_div">
			        <ul class="paging_align" id="paging-ul" style="width: 400px"></ul>
				</div>
				<!-- Pagging End ---------->
				
				<br />
				
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.cst.text.info1'/></li>
					</ul>
					<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col style="width:15%" />
							<col style="width:30%" />
							<col style="width:15%" />
							<col style="width:*" />
						</colgroup>
						<tr>
							<th><spring:message code="epc.cst.header.asCode9"/></th>
							<td>
								<span id="spCustNm" />
								<!-- <input type="text" name="custNm" id="custNm" maxlength="13" style="width:150px;" readonly="readonly"/> -->
							</td>
							<th><spring:message code="epc.cst.header.asCode10"/></th>
							<td>
								<span id="spHpNo" />
								<!-- <input type="text" name="hpNo" id="hpNo" maxlength="13" style="width:150px;" readonly="readonly"/> -->
							</td>
						</tr>
						<tr>
							<%-- <th><spring:message code="epc.cst.header.asCode11"/></th>
							<td>
								<input type="checkbox" name="newAddrFg" id="newAddrFg" disabled="disabled"/>
							</td> --%>
							<th><spring:message code="epc.cst.header.asCode12"/></th>
							<td colspan="3">
								<span id="spZipAddress" />
								<!-- <input type="text" name="zip" id="zip" maxlength="30" style="width:150px;" maxlength="30" readonly="readonly"/><br />
								<input type="text" style="margin-top:5px;width:450px;" name="addr" id="addr" maxlength="100" readonly="readonly"/> -->
							</td>
						</tr> 
					</table>
				</div>
				
				<br />
				
				<div class="bbs_list">
				
					<ul class="tit">
						<li class="tit"><spring:message code='epc.cst.text.info2'/></li>
						<li class="btn"><a href="#" class="btn" onclick="_eventSave();"><span><spring:message code="button.common.save" /></span></a></li>
					</ul>
					
					<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col style="width:15%" />
							<col style="width:30%" />
							<col style="width:15%" />
							<col style="width:30%" />
						</colgroup>
						<tr>
							<th><spring:message code="epc.cst.header.asCode13"/></th>
							<td>
								<span id="spSlipNo" />
								<input type="text" name="slipNo" id="slipNo" maxlength="12" style="width:150px;border:none"	readonly="readonly"	/>
							</td>
							<th><spring:message code="epc.cst.header.asCode14"/></th>
							<td>
								<span id="spRegDy" />
								<!-- <input type="text" name="regDy" id="regDy" maxlength="13" style="width:150px;" readonly="readonly"/> -->
							</td>
						</tr>
						<tr>
							<th><spring:message code="epc.cst.header.asCode15"/></th>
							<td colspan="3">
								<span id="spEan11" />
								<!-- <input type="text" name="ean11" id="ean11" maxlength="12" style="width:150px;" readonly="readonly"/> -->
							</td>
						</tr>
						<tr>
							<th><spring:message code="epc.cst.header.asCode16"/></th>
							<td colspan="3">
								<span id="spProdNm" />
								<!-- <input type="text" name="prodNm" id="prodNm" maxlength="20" style="width:200px;" readonly="readonly"/> -->
							</td>
						</tr>
						<tr>
							<th><spring:message code="epc.cst.header.asCode17"/></th>
							<td colspan="3">
								<textarea name="reqCmt" id="reqCmt" cols="100" rows="5" readonly="readonly" style="background-color: #cccccc;">
								</textarea>
							</td>
						</tr>
						<tr>
							<th><spring:message code="epc.cst.header.asCode18"/></th>
							<td colspan="3">
								<html:codeTag objId="procStat" objName="procStat" comType="SELECT" formName="form" selectParam="<c:out value='${param.procStat}'/>" parentCode="AS001" defName="전체"></html:codeTag>
							</td>
						</tr>
						<tr>
							<th><spring:message code="epc.cst.header.asCode19"/></th>
							<td>
								<input type="text" class="day" id="repairScheDt" name="repairScheDt" maxlength="10" readonly style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.repairScheDt');" style="cursor:hand;" />
							</td>
							<th><spring:message code="epc.cst.header.asCode20"/></th>
							<td>
								<input type="text" class="day" id="repairCompDt" name="repairCompDt" maxlength="10" readonly style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.repairCompDt');" style="cursor:hand;" />
							</td>
						</tr>
						<tr>
							<th><spring:message code="epc.cst.header.asCode21"/></th>
							<td colspan="3">
								<input type="text" class="day" id="transCompDt" name="transCompDt" maxlength="10" readonly style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.transCompDt');" style="cursor:hand;" />
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
				<li><spring:message code='epc.cst.home'/></li>
				<li><spring:message code='epc.cst.cola'/></li>
				<li class="last"><spring:message code='epc.cst.as'/></li>
			</ul>
		</div>
	</div>
</div>
<!-- footer //-->
</div>

</body>
</html>