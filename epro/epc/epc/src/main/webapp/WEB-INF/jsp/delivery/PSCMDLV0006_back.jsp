<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag-dev.js" ></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>

<script type="text/javascript">
/** ********************************************************
 * jQeury 초기화
 ******************************************************** */
$(document).ready(function()
{
	
	var bflag = '${searchVO.flag}';

	if ( bflag == "success" )
    {
        $('#resultMsg').text('<spring:message code="msg.common.success.select"/>');
    }
    else if ( bflag == "zero" )
    {
        $('#resultMsg').text('<spring:message code="msg.common.info.nodata"/>');
    }else if (bflag == "save")
    {
    	$('#resultMsg').text('<spring:message code="msg.common.success.save"/>');
    }	
    else
    {
        $('#resultMsg').text('<spring:message code="msg.common.fail.request"/>');
    }
	
	$('#search').click(function() {
		doSearch();
	});

	
	$('#excel').click(function() {
		doExcel();
	});	
	
	$('#sms').click(function() {
		SMSsend();
	});	

	$('#update').click(function() {
		UpdateStatus();
	});	
	
	$('#startDate').click(function() {
		openCal('searchForm.startDate');
	});
	$('#endDate').click(function() {
		openCal('searchForm.endDate');
	});
}); // end of ready





/** ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function doSearch() {
	goPage('1');
}

/** ********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function goPage(pageIndex)
{
	var f = document.searchForm;
	
	var startDate = f.startDate.value.replace( /-/gi, '' );
	var endDate   = f.endDate.value.replace( /-/gi, '' );
	
	if(startDate>endDate){
		alert('시작일자가 종료일자보다 클수 없습니다.');
		return;
	}	
	
	// 범위체크
	var f_date = f.startDate.value;
	var t_date = f.endDate.value;

  	var from_day = f_date.substring(5,7) + "-" + f_date.substring(8,10)+ "-" + f_date.substring(0,4);
  	var to_day   = t_date.substring(5,7) + "-" + t_date.substring(8,10)+ "-" + t_date.substring(0,4);
		
    from_day  = new Date(from_day);
    to_day    = new Date(to_day);
    var days  = Math.ceil((to_day-from_day)/24/60/60/1000);
        
   	if(days > 7){
//    		alert("검색 최대기간인 7이내만 검색 할 수 있습니다.");
//    		return;
   	}
  	
  	if(f.searchType.value == "1") {
  		if(f.searchContent.value.trim() == ""){
  			alert("주문번호를 입력해주세요..!");
  			return;
  		}
  	}
  	
  	if(f.searchType.value == "2") {
  		if(f.searchContent.value.trim() == ""){
  			alert("로그인ID 를 입력해주세요..!");
  			return;
  		}
  	}
  	
  	if(f.searchType.value == "3") {
  		if(f.searchContent.value.trim() == ""){
  			alert("보낸는분의 성함을 입력해주세요..!");
  			return;
  		}
  	}
  	
  	if(f.searchType.value == "4") {
  		if(f.searchContent.value.trim() == ""){
  			alert("받는분의 성함을 입력해주세요..!");
  			return;
  		}
  	}	
	
	var url = '<c:url value="/delivery/selectPartnerFirmsOrderList.do"/>';
	//f.pageIndex.value = pageIndex;
	f.action = url;
	f.submit();
}

/** ********************************************************
 * execl
 ******************************************************** */
function doExcel(){
	var f = document.searchForm;
	
	var startDate = f.startDate.value.replace( /-/gi, '' );
	var endDate   = f.endDate.value.replace( /-/gi, '' );
	
	if(startDate>endDate){
		alert('시작일자가 종료일자보다 클수 없습니다.');
		return;
	}	
	
	// 범위체크
	var f_date = f.startDate.value;
	var t_date = f.endDate.value;

  	var from_day = f_date.substring(5,7) + "-" + f_date.substring(8,10)+ "-" + f_date.substring(0,4);
  	var to_day   = t_date.substring(5,7) + "-" + t_date.substring(8,10)+ "-" + t_date.substring(0,4);
		
    from_day  = new Date(from_day);
    to_day    = new Date(to_day);
    var days  = Math.ceil((to_day-from_day)/24/60/60/1000);
        
   	if(days > 7){
//    		alert("검색 최대기간인 7이내만 검색 할 수 있습니다.");
//    		return;
   	}
  	
  	if(f.searchType.value == "1") {
  		if(f.searchContent.value.trim() == ""){
  			alert("주문번호를 입력해주세요..!");
  			return;
  		}
  	}
  	
  	if(f.searchType.value == "2") {
  		if(f.searchContent.value.trim() == ""){
  			alert("로그인ID 를 입력해주세요..!");
  			return;
  		}
  	}
  	
  	if(f.searchType.value == "3") {
  		if(f.searchContent.value.trim() == ""){
  			alert("보낸는분의 성함을 입력해주세요..!");
  			return;
  		}
  	}
  	
  	if(f.searchType.value == "4") {
  		if(f.searchContent.value.trim() == ""){
  			alert("받는분의 성함을 입력해주세요..!");
  			return;
  		}
  	}	
	
	var url = '<c:url value="/delivery/selectPartnerFirmsOrderListExcel.do"/>';
	//f.pageIndex.value = pageIndex;
	f.action = url;
	f.submit();	
}

/** ********************************************************
 * head 선택 checkBox
 ******************************************************** */
function select_all()
{
	var f = document.searchForm;
	
	if(f.checkValue != null){
	    if (f.allCheck.checked == true) 
	    {
	    	if (f.checkValue != null && f.checkValue.length > 1)
	    	{
	        	for (i=0; i<f.checkValue.length; i++)
	        	{
	        	   if (f.checkValue[i].disabled == false)
	        		f.checkValue[i].checked = true;
	        	}   
	        }
	        else
	        {
	        if (f.checkValue.disabled == false)
	        	f.checkValue.checked = true;
	        }         
	    } 
	    else 
	    {
	    	if (f.checkValue != null && f.checkValue.length > 1)
	    	{
	        	for (i=0; i<f.checkValue.length; i++)
	        	{
	        		f.checkValue[i].checked = false;
	        	}   
	        }
	        else
	        {
	        	f.checkValue.checked = false;
	        }            
	    }
	}	    
}

/** ********************************************************
 * header 배송상태 selectBox
 **********************************************************/
function check_change()
{
    var chk_count = 0;
    var f = document.searchForm;
    var i;
    
    if(f.checkValue != null){
	    if (f.checkValue.length > 1)
	    {
		    for ( i=0; i< f.checkValue.length; i++) {
		      if (f.checkValue[i].checked == true)
		      {
		      	f.venDeliStatusCd[i].value = f.allDelivCodeType.value;
		      	chk_count++;
		      }
		    }
		}
		else
		{
			if (f.checkValue.checked == true)
			{
				f.venDeliStatusCd.value = f.allDelivCodeType.value;
		      	chk_count++;
			}
		}
	    return chk_count;
    }
}



/**********************************************************
 * 저장
 **********************************************************/
function UpdateStatus()
{
	var f = document.searchForm;
	var type = f.saveDeliTypeCd.value;
	
	if(f.checkValue != null && check_selected()>0){
		var value = getSelectedList();
		if(!value) return;
		f.codeList.value = value;
	
// 		if(type=='04' ){
// 			var url = '<c:url value="/delivery/updatePartnerFirmsItem_short.do"/>';
// 		}else{
// 			var url = '<c:url value="/delivery/updatePartnerFirmsItem_holy.do"/>';
// 		}
		var url = '<c:url value="/delivery/updatePartnerFirmsItem.do"/>';
		f.action = url;
		f.submit();
	}else{
		alert("항목을 체크해주세요!!");
	}

}


/**********************************************************
 * 저장시 선택 checkBox 갯수
 **********************************************************/
function check_selected() {
    var chk_count = 0;
    var f = document.searchForm;
    var i;
    if (f.checkValue != null && f.checkValue.length > 1){
	    for ( i=0; i< f.checkValue.length; i++) {
			if (f.checkValue[i].checked == true){
				chk_count++;
			}
	    }
	}else{
		if (f.checkValue.checked == true){
	      	chk_count++;
		}
	}
    return chk_count;
}

/**********************************************************
 * 저장 목록셋팅
 **********************************************************/
function getSelectedList() {
    var itemlist = "";
    var f = document.searchForm;
    if (f.checkValue.length > 1)
    {
	    for (i=0; i< f.checkValue.length; i++) 
	    {
	      if (f.checkValue[i].checked == true)
	      {
	      	if( f.venDeliStatusCd[i].value == "05")
	      	  {
	      	  		if(f.hodecoInvoiceNo[i].value == "")
		      		{
		      		    alert("발송완료는  배송송장번호를 필수로 입력하시기 바랍니다.");
		      		    return false;  
		      		}
		      		else if(f.hodecoCd[i].value =="%")
		      		{
		      		    alert("배송회사를 지정해주시기바랍니다.");
	      		        return false;
		      		}
	      	  }
	      	  else
	      	  { 
					if(f.hodecoInvoiceNo[i].value != "" && f.hodecoCd[i].value =="%")
					{
		      		    alert("배송송장번호를 입력하신경우 배송회사를 지정해주시기바랍니다.");
	      		        return false;						
					}	      		  
	      		  
					if(f.hodecoCd[i].value !="%" && f.hodecoInvoiceNo[i].value == "")
					{
		      		    alert(" 배송회사를 지정하신경우 배송송장번호를 입력하시기 바랍니다.");
		      		    return false;  						
					}
					
		      	    if(f.hodecoInvoiceNo[i].value == "")
	      		    {
      		           f.hodecoInvoiceNo[i].value = "X"; 
      		        }
     	      }
	        if (itemlist == "")
	          itemlist += f.checkValue[i].value + ":" + f.venDeliStatusCd[i].value + ":" + f.hodecoCd[i].value + ":" + f.hodecoInvoiceNo[i].value + ":" + f.delivery_id[i].value + ":" + f.deliNo[i].value + ":" + f.deliStatusCd[i].value + ":" + f.venCd[i].value;
	        else
	          itemlist += ","+ f.checkValue[i].value + ":" + f.venDeliStatusCd[i].value + ":" + f.hodecoCd[i].value + ":" + f.hodecoInvoiceNo[i].value + ":" + f.delivery_id[i].value + ":" + f.deliNo[i].value + ":" + f.deliStatusCd[i].value + ":" + f.venCd[i].value;
			}
	      
	      
	    }  					
	}
    
    else
    	
	{
	      if (f.checkValue.checked == true)
	      {
	      	if( f.venDeliStatusCd.value == "05")
	      	{
	      		if(f.hodecoInvoiceNo.value == "")
	      		  {
	      		    alert("발송완료는  배송송장번호를 필수로 입력하시기 바랍니다..");
	      		    return false;  
	      		  }
	      		  else if(f.hodecoCd.value =="%")
	      		  {
	      			alert("배송회사를 지정해주시기바랍니다.");
      		        return false;
	      		  }
	      	 }
	      	 else
	      	 { 
  		          if(f.hodecoInvoiceNo.value == "")
  		          {
		                f.hodecoInvoiceNo.value = "X"; 
             	  }
             }	      
		      itemlist += f.checkValue.value + ":" + f.venDeliStatusCd.value + ":" + f.hodecoCd.value + ":" + f.hodecoInvoiceNo.value + ":" + f.delivery_id.value + ":" + f.deliNo.value + ":" + f.deliStatusCd.value + ":" + f.venCd.value;
		  }
	}
	return itemlist;
}


function SMSsend()
{
	var f = document.searchForm;

	if(f.deliStatusCode.value != "05"){
		alert("배송상태가 발송완료인 주문만 SMS를 발송할 수 있습니다.\n\r상단 조회조건중 배송상태를'발송완료'로 선택하여 조회하세요.");
		return;
	}
	
	if(confirm("배송SMS를  발송하시겠습니까?")){
		var url = '<c:url value="/delivery/insertSendSMS.do"/>';
		f.target = "_self";
		f.action = url;
  		f.submit();
	}
}	



/**********************************************************
 * 배송추적
 **********************************************************/
function delivComSearch(hodecoCd, hodecoInvoiceNo){
	
	//우체국택배
	if(hodecoCd=='02'){ 
		var url = "http://service.epost.go.kr/trace.RetrieveRegiPrclDeliv.postal?sid1="+hodecoInvoiceNo;
		window.open(url,"kocnss");
	//삼성택배	
	}else if(hodecoCd=='03'){
		var url="http://nexs.cjgls.com/web/service02_01.jsp?slipno="+hodecoInvoiceNo;
		window.open(url,"kocnss");
	//현대택배
	}else if(hodecoCd=='04'){
		var url = 'http://www.hlc.co.kr/hydex/jsp/tracking/trackingViewCus.jsp?InvNo='+hodecoInvoiceNo;
		window.open(url,"kocnss");
	//로젠택배
	}else if(hodecoCd=='05'){	
		var url="http://www.ilogen.com/iLOGEN.Web.New/TRACE/TraceNoView.aspx?gubun=slipno&slipno="+hodecoInvoiceNo;
		window.open(url,"kocnss");
	//아주택배	
	}else if(hodecoCd=='06'){	
		var url="http://www.ajulogis.co.kr/common/asp/search_history_proc.asp?sheetno="+hodecoInvoiceNo;
		window.open(url,"kocnss");
	//KGB택배
	}else if(hodecoCd=='07'){	
		var url="http://www.kgbls.co.kr/trace/default.asp?sendno="+hodecoInvoiceNo;
		window.open(url,"kocnss");
	//한진택배
	}else if(hodecoCd=='08'){	
		var url="http://www.hanjinexpress.hanjin.net/customer/plsql/hddcw07.result?wbl_num="+hodecoInvoiceNo;
		window.open(url,"kocnss");
	//대신택배
	}else if(hodecoCd=='09'){
		var billno1 = String(hodecoInvoiceNo).substring(0,4); 
		var billno2 = String(hodecoInvoiceNo).substring(4,7); 
		var billno3 = String(hodecoInvoiceNo).substring(7,13);
		var url="http://www.daesinlogistics.co.kr/daesin/jsp/d_freight_chase/d_general_process.jsp?billno1="+billno1+"&billno2="+billno2+"&billno3="+billno3;
		window.open(url,"kocnss");
	//대한통운택배
	}else if(hodecoCd=='10'){
	  	var url="https://www.doortodoor.co.kr/parcel/doortodoor.do?fsp_action=PARC_ACT_002&fsp_cmd=retrieveInvNoACT&invc_no="+hodecoInvoiceNo;
		window.open(url,"kocnss");
	//CJ HTH택배
	}else if(hodecoCd=='11'){
		var url="http://nexs.cjgls.com/web/service02_01.jsp?slipno="+hodecoInvoiceNo;
		window.open(url,"kocnss");
	//옐로우캡택배
	}else if(hodecoCd=='12'){
		var url="http://www.yellowcap.co.kr/custom/inquiry_result.asp?INVOICE_NO="+hodecoInvoiceNo;
		window.open(url,"kocnss");
	//KT로지스택배
	}else if(hodecoCd=='13'){
	   	var url="http://218.153.4.42/customer/cus_trace_02.asp?invc_no="+hodecoInvoiceNo+"&searchMethod="+"I";
		window.open(url,"kocnss");
	//일양택배	
	}else if(hodecoCd=='14'){
		var url="http://www.ilyanglogis.com/functionality/tracking_result.asp?hawb_no="+hodecoInvoiceNo;
		window.open(url,"kocnss");
	//CJ GLS택배
	}else if(hodecoCd=='15'){
		var url="http://nexs.cjgls.com/web/service02_01.jspurl"+hodecoInvoiceNo;
		window.open(url,"kocnss");
	//하나로 택배
	}	else if(hodecoCd=='16'){
		var url="http://www.hanarologis.com/branch/chase/listbody.html?a_gb=center&a_cd=4&a_item=0&fr_slipno="+hodecoInvoiceNo;
		window.open(url,"kocnss");
	//동부택배	
	}else if(hodecoCd=='17'){
		var url="http://www.dongbuexpress.co.kr/Html/Delivery/DeliveryCheckView.jsp?item_no="+hodecoInvoiceNo;
		window.open(url,"kocnss");
	//천일택배	
	}else if(hodecoCd=='18'){
		var url="http://www.cyber1001.co.kr/HTrace/HTrace.jsp?transNo="+hodecoInvoiceNo;
		window.open(url,"kocnss");
	}			
	
}


/**********************************************************
 * 협력업체검색팝업
 **********************************************************/
function vendorPopUp()
{
 	var targetUrl = '<c:url value=""/>';
	Common.centerPopupWindow(targetUrl, 'popup', {width : 800, height : 550});
}

/**********************************************************
 * 택배사코드팝업
 **********************************************************/
function deliCodePopUp()
{
 	var targetUrl = '<c:url value="/common/selectDeliCodePopup.do"/>';
	Common.centerPopupWindow(targetUrl, 'popup', {width : 400, height : 650, scrollBars : "YES"});
}

/**********************************************************
 * 협력업체 검색창으로 부터 받은 협력업체 정보 입력
 **********************************************************/
function setVendorInto(strVendorId, strVendorNm) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임
	$("#strVendorId").val(strVendorId);
	$("#strVendorNm").val(strVendorNm);	   
}


/**********************************************************
 * 송장업로드
 **********************************************************/
function excelUpload(){
 	var targetUrl = '<c:url value="/common/selectPartnerInvoiceNoPopup.do"/>';
	Common.centerPopupWindow(targetUrl, 'popup', {width : 800, height : 150});	
}		


</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<div id="content_wrap">

	<div class="content_scroll">
	
	<form name="searchForm" method="post">
		
		<input type="hidden" name="codeList">
		<input type="hidden" name="saveDeliTypeCd" value="${searchVO.deliTypeCd}">
		<input type="hidden" name="strCd" value="${searchVO.strCd}">
		
		<div id="wrap_menu">
		
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">조회조건</li>
						<li class="btn">
<%-- 							<c:if test="${'04' eq searchVO.deliTypeCd}"><a href="#" class="btn" id="sms"><span><spring:message code="button.common.smsSend"/></span></a></c:if> --%>
							<a href="#" class="btn" id="excel"><span>엑셀</span></a>
							<a href="#" class="btn" id="sms"><span><spring:message code="button.common.smsSend"/></span></a>
							<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
						</li>  
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col width="10%">
						<col width="30%">
						<col width="10%">
						<col width="20%">
						<col width="10%">
						<col width="20%">
					</colgroup>
					<tr>
						<th>
							<select name="dateGbn" class="select" >
								<option value="1" <c:if test="${'1' eq searchVO.dateGbn }">selected="selected"</c:if>>주문일</option>
								<option value="2" <c:if test="${'2' eq searchVO.dateGbn}">selected="selected"</c:if>>발송완료일</option>
								<option value="3" <c:if test="${'3' eq searchVO.dateGbn}">selected="selected"</c:if>>배송희망일</option>
<%-- 								<option value="3" <c:if test="${'3' eq searchVO.dateGbn}">selected="selected"</c:if>>발송예정일</option> --%>
							</select>
						</th>
						<td>
							<input type="text" name="startDate" id="startDate" class="day" readonly style="width:33%;" value="${searchVO.startDate}" /><a href="javascript:openCal('searchForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
							~
							<input type="text" name="endDate" id="endDate" class="day" readonly style="width:33%;" value="${searchVO.endDate}" /><a href="javascript:openCal('searchForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
						</td>
						<th>
							<select name="searchType" class="select">
								<option value="%" <c:if test="${'%' eq searchVO.searchType}">selected="selected"</c:if>>선택</option>
								<option value="1" <c:if test="${'1' eq searchVO.searchType}">selected="selected"</c:if>>주문번호</option>
			 					<option value="2" <c:if test="${'2' eq searchVO.searchType}">selected="selected"</c:if>>로그인ID</option>
								<option value="3" <c:if test="${'3' eq searchVO.searchType}">selected="selected"</c:if>>보내는분</option>
								<option value="4" <c:if test="${'4' eq searchVO.searchType}">selected="selected"</c:if>>받는분</option>
							</select>						
						</th>
						<td class="text"><input type="text" name="searchContent" value="${searchVO.searchContent}"/></td>
						
						
<!-- 						<th>협력업체코드</th> -->
<%-- 						<td class="text"><input type="text" id="strVendorNm" name="strVendorNm" value="${searchVO.strVendorNm}" onDblClick="javascript:vendorPopUp();" readonly/></td> --%>
<%-- 						<input type="hidden" id="strVendorId" name="strVendorId" value="${searchVO.strVendorId}"> --%>
						<th>협력업체코드</th>
						<td>
							<select id="vendorId" name="vendorId" class="select">
								<option value="">전체</option>
								<c:forEach items="${epcLoginVO.vendorId}" var="venArr">
								<option value="${venArr}" <c:if test="${venArr eq vendorId}">selected="selected"</c:if>>${venArr}</option>
								</c:forEach>
							</select>
						</td>						
					</tr>
					<tr>
						<th>배송구분</th>
						<td>
							<select name="deliTypeCd" class="select">
								<option value="04" <c:if test="${'04' eq searchVO.deliTypeCd}">selected="selected"</c:if>>일반배송</option>
								<option value="06" <c:if test="${'06' eq searchVO.deliTypeCd}">selected="selected"</c:if>>명절배송</option>
							</select>								
						</td>
						<th>주문상태</th>
						<td>
							<select name="saleStsCd" class="select">
								<option value="%" selected="selected">전체</option>
								<c:forEach items="${OR002List}" var="codeList">
								<option value="${codeList.MINOR_CD}" <c:if test="${codeList.MINOR_CD eq searchVO.saleStsCd}">selected="selected"</c:if>>${codeList.CD_NM}</option>								
								</c:forEach>								
							</select>								
						</td>
						<th>배송상태</th>
						<td>
							<select name="deliStatusCode" class="select">
								<option value="%" <c:if test="${codeList.MINOR_CD eq searchVO.deliStatusCode}">selected="selected"</c:if>>전체</option>
								<c:forEach items="${DE014List}" var="codeList">
								<option value="${codeList.MINOR_CD}" <c:if test="${codeList.MINOR_CD eq searchVO.deliStatusCode}">selected="selected"</c:if>>${codeList.CD_NM}</option>								
								</c:forEach>
							</select>							
						</td>
					</tr>
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
						<li class="tit">##[주의] 발송 완료된 주문은 수정할 수 없습니다!! ## </li>
						<li class="btn">
							<a href="javascript: deliCodePopUp();" class="btn" ><span>택배사코드</span></a>
							<a href="javascript: excelUpload();" class="btn" ><span>송장업로드</span></a>
							<a href="#" class="btn" id="update"><span><spring:message code="button.common.save"/></span></a>
						</li>						
					</ul>
					<div style="overflow-y:scroll;width:790px;height:350px;">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
                        <col style="width:100px" />
                        <col style="width:100px" />
                        <col style="width:100px" />
                        <col style="width:100px" />
                        <col style="width:110px" />
                        <col style="width:110px" />
                        <col style="width:110px" />
                        <col style="width:100px" />
                        <col style="width:100px" />
                        <col style="width:100px" />
                        <col style="width:200px" />
                        <col style="width:300px" />
                        <col style="width:50px" />
                        
                        
<%--                         <col style="width:70px" /> --%>
<%--                         <col style="width:80px" /> --%>
<%--                         <col style="width:150px" /> --%>
<%--                         <col style="width:70px" /> --%>
<%--                         <col style="width:70px" /> --%>
					</colgroup>
					<tr> 
						<th>배송지순번</th>
						<th>점포</th>
						<th>주문일자</th>
						<th>주문시간</th>
						<th>배송비</th>
						<th>집전화</th>
						<th>H.P</th>
						<th>받는분</th>
						<th>보내는분</th>
						<th colspan="2">(우편번호)주소</th>
						<th colspan="2">메모</th>
					</tr>
					<tr>
						<th rowspan="2">주문번호</th>
						<th rowspan="2">업체명</th>
						<th rowspan="2">주문상태</th>
						<th colspan="3">상품명</th>
						<th>상품가격</th>
						<th>예약배송일</th>
						<th rowspan="2">배송상태
							<select name="allDelivCodeType" class="select"  OnChange="check_change();">
								<c:forEach items="${DE014List}" var="codeList">
								<option value="${codeList.MINOR_CD}">${codeList.CD_NM}</option>								
								</c:forEach>
							</select>		            						
						</th>
						<th rowspan="2">배송추적</th>
						<th rowspan="2">택배사</th>
						<th rowspan="2">송장번호</th>
						<th>선택<input type="checkbox" name="allCheck" onClick="select_all()"></th>
					</tr>
					<tr>
						<th colspan="3">옵션</th>
						<th>주문수량</th>
						<th>발송완료일</th>
						<th>SMS</th>
					</tr>
		<c:set var="T_ORDER_ID"/>		
		<c:set var="T_DELIVERY_ID"/>
		<c:forEach var="list" items="${list}">
			<c:if test="${T_ORDER_ID != list.ORDER_ID || T_DELIVERY_ID != list.DELIVERY_ID}">							
					<tr class="r1">
						<td height="1" colspan="13" style="color: blue;" >
							<input type="hidden" name="deliNo" value="${list.DELI_NO}"/>
<%-- 							<input type="hidden" name="invoiceSeq" value="${list.INVOICE_SEQ}"/>	 --%>
							<input type="hidden" name="venCd" value="${list.VEN_CD}"/>
							<input type="hidden" name="deliStatusCd" value="${list.DELI_STATUS_CD}"/>
						</td>
					</tr>  			
					
					<tr> 
						<td><c:out value="${list.DELIVERY_ID}"/></td>
						<td>${list.STR_NM}</td>
						<td><c:out value="${list.ORD_DY}"/></td>
						<td><c:out value="${list.ORD_TM}"/></td>
						<td><fmt:formatNumber value="${list.DELIV_AMT}" pattern="#,##0" /></td>
						<td><c:out value="${list.RECP_PSN_TEL_NO}"/></td>
						<td><c:out value="${list.RECP_PSN_CELL_NO}"/></td>
						<td><c:out value="${list.TO_PSN_NM}"/></td>
						<td><c:out value="${list.FROM_PSN_NM}"/></td>
						<td colspan="2">(<c:out value="${list.RECP_PSN_POST_NO}"/>)<c:out value="${list.ADDR}"/></td>
						<td colspan="2"><c:out value="${list.DELI_MSG}"/></td>					
					</tr>
			</c:if>					
					
			<c:choose>			
				<c:when test="${list.ROWSPAN1<2}">					
					<tr>
						<td rowspan="2"><c:out value="${list.ORDER_ID}"/></td>
						<td rowspan="2"><c:out value="${list.VENDOR_NM}"/></td>
						<td rowspan="2"><c:out value="${list.ORD_STS_NM}"/></td>
						<td colspan="3"><c:out value="${list.PROD_NM}"/></td>
						<td><fmt:formatNumber value="${list.TOT_SELL_AMT}" pattern="#,##0" /></td>
						<td><c:out value="${list.ENTP_DELI_PRAR_DY}"/></td>
						<td rowspan="2">
							<select name="venDeliStatusCd" class="input_text">
								<c:forEach items="${DE014List}" var="codeList">
								<option value="${codeList.MINOR_CD}" <c:if test="${list.VEN_DELI_STATUS_CD eq codeList.MINOR_CD}">selected="selected"</c:if>>${codeList.CD_NM}</option>								
								</c:forEach>							
							</select>            						
						</td>
						<td rowspan="<c:out value="${list.ROWSPAN2}"/>">
			<c:if test="${list.HODECO_INVOICE_NO != ''}">
				<c:if test="${list.HODECO_CD != '01' && list.HODECO_CD != '%' && list.VEN_DELI_STATUS_CD == '05'}">
							<a href="#" onClick="javascript:delivComSearch('<c:out value="${list.HODECO_CD}"/>','<c:out value="${list.HODECO_INVOICE_NO}"/>');">배송추적</a>
				</c:if>
			</c:if>
						</td>
						<td rowspan="2">
							<select name="hodecoCd" class="input_text">
								<option value="%" <c:if test="${'%' eq codeList.MINOR_CD}">selected="selected"</c:if>>미지정</option>
								<c:forEach items="${DE011List}" var="codeList">
								<option value="${codeList.MINOR_CD}" <c:if test="${list.HODECO_CD eq codeList.MINOR_CD}">selected="selected"</c:if>>${codeList.CD_NM}</option>
								</c:forEach>
							</select>
						</td>
						<td rowspan="2"><input type="text" name="hodecoInvoiceNo" value="<c:out value="${list.HODECO_INVOICE_NO}"/>" onKeyPress="if ((event.keyCode&lt;48)||(event.keyCode&gt;57)) event.returnValue=false;" maxlength="20" ></td>
						<td>
							<input type="checkbox" name="checkValue"  value="<c:out value="${list.ORDER_ID}"/>:<c:out value="${list.ORDER_ITEM_SEQ}"/>"<c:if test="${(list.HODECO_INVOICE_NO!='' && list.VEN_DELI_STATUS_CD eq '05')|| list.ORD_STS_CD eq '20' || list.ORD_STS_CD eq '21'}">disabled="disabled"</c:if>>
							<input type="hidden" name="d_order_id" value="<c:out value="${list.ORDER_ID}"/>">
							<input type="hidden" name="d_item_seq" value="<c:out value="${list.ORDER_ITEM_SEQ}"/>">
							<input type="hidden" name="delivery_id" value="<c:out value="${list.DELIVERY_ID}"/>">
							<input type="hidden" name="d_hodecoInvoiceNo" value="<c:out value="${list.HODECO_INVOICE_NO}"/>">
						</td>					
					</tr>	
					<tr >
						<td colspan="3"><c:out value="${list.ITEM_OPTION}"/></td>
						<td><fmt:formatNumber value="${list.ORD_QTY}" pattern="#,##0" /></td>
						<td><c:out  value="${list.DELI_FNSH_DATE}"/></td>
						<td><font color="blue"><c:out value="${list.SMS_SEND_YN}" /></font></td>
					</tr>		
								
				</c:when>
				
				
				
				<c:otherwise>
					<tr>
				<c:if test="${T_ORDER_ID != list.ORDER_ID || T_DELIVERY_ID != list.DELIVERY_ID}">	
						<td rowspan="<c:out value="${list.ROWSPAN2}"/>"><c:out value="${list.ORDER_ID}"/></td>
				</c:if>		
						<td rowspan="2"><c:out value="${list.VENDOR_NM}"/></td>
						<td rowspan="2"><c:out value="${list.ORD_STS_NM}"/></td>
						<td colspan="3"><c:out value="${list.PROD_NM}"/></td>
						<td><fmt:formatNumber value="${list.TOT_SELL_AMT}" pattern="#,##0" /></td>
						<td><c:out value="${list.ENTP_DELI_PRAR_DY}"/></td>
				<c:if test="${T_ORDER_ID != list.ORDER_ID || T_DELIVERY_ID != list.DELIVERY_ID}">	
						<td rowspan="<c:out value="${list.ROWSPAN2}"/>">
							<select name="venDeliStatusCd" class="input_text">
								<c:forEach items="${DE014List}" var="codeList">
								<option value="${codeList.MINOR_CD}" <c:if test="${list.VEN_DELI_STATUS_CD eq codeList.MINOR_CD}">selected="selected"</c:if>>${codeList.CD_NM}</option>								
								</c:forEach>							
							</select>            						
						</td>
				</c:if>	
				
				<c:if test="${T_ORDER_ID != list.ORDER_ID || T_DELIVERY_ID != list.DELIVERY_ID}">
						<td rowspan="<c:out value="${list.ROWSPAN2}"/>">
					<c:if test="${list.HODECO_INVOICE_NO != ''}">
						<c:if test="${list.HODECO_CD != '01' && list.HODECO_CD != '%' && list.VEN_DELI_STATUS_CD == '05'}">
							<a href="#" onClick="javascript:delivComSearch('<c:out value="${list.HODECO_CD}"/>','<c:out value="${list.HODECO_INVOICE_NO}"/>');">배송추적</a>
						</c:if>
					</c:if>
						</td>
				</c:if>
				
				<c:if test="${T_ORDER_ID != list.ORDER_ID || T_DELIVERY_ID != list.DELIVERY_ID}">		
						<td rowspan="<c:out value="${list.ROWSPAN2}"/>">
							<select name="hodecoCd" class="input_text">
								<option value="%" <c:if test="${'%' eq codeList.MINOR_CD}">selected="selected"</c:if>>미지정</option>
								<c:forEach items="${DE011List}" var="codeList">
								<option value="${codeList.MINOR_CD}" <c:if test="${list.HODECO_CD eq codeList.MINOR_CD}">selected="selected"</c:if>>${codeList.CD_NM}</option>
								</c:forEach>
							</select>
						</td>

						<td rowspan="<c:out value="${list.ROWSPAN2}"/>"><input type="text" name="hodecoInvoiceNo" value="<c:out value="${list.HODECO_INVOICE_NO}"/>" onKeyPress="if ((event.keyCode&lt;48)||(event.keyCode&gt;57)) event.returnValue=false;" maxlength="20" ></td>

						<td rowspan="<c:out value="${list.ROWSPAN2}"/>">
							<input type="checkbox" name="checkValue"  value="<c:out value="${list.ORDER_ID}"/>:<c:out value="${list.ORDER_ITEM_SEQ}"/>"<c:if test="${(list.HODECO_INVOICE_NO!='' && list.VEN_DELI_STATUS_CD eq '05')|| list.ORD_STS_CD eq '20' || list.ORD_STS_CD eq '21'}">disabled="disabled"</c:if>>
							<input type="hidden" name="d_order_id" value="<c:out value="${list.ORDER_ID}"/>">
							<input type="hidden" name="d_item_seq" value="<c:out value="${list.ORDER_ITEM_SEQ}"/>">
							<input type="hidden" name="delivery_id" value="<c:out value="${list.DELIVERY_ID}"/>">
							<input type="hidden" name="cross_waybill_no" value="<c:out value="${list.HODECO_INVOICE_NO}"/>">
							<br/><br/>
							<font color="blue"><c:out value="${list.SMS_SEND_YN}" /></font>
						</td>
				</c:if> 				
					</tr>	
					<tr>
						<td colspan="3"><c:out value="${list.ITEM_OPTION}"/></td>
						<td><fmt:formatNumber value="${list.ORD_QTY}" pattern="#,##0" /></td>
						<td><c:out  value="${list.DELI_FNSH_DATE}"/></td>
<%-- 						<td><font color="blue"><c:out value="${list.SMS_SEND_YN}" /></font></td> --%>
					</tr>						
				</c:otherwise>								
			</c:choose>		
			<c:set var="T_ORDER_ID" value="${list.ORDER_ID}"/>
			<c:set var="T_DELIVERY_ID" value="${list.DELIVERY_ID}"/>
		</c:forEach>
						<tr class="r1">
							<td height="1" colspan="13" style="color: blue;" ></td>
						</tr>  			
					</table>
					</div>
				</div>
		
			</div>
			<!-- 2검색내역 // -->

		</div>
	</form>
	</div>
	
	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>배송관리</li>
					<li class="last">협력업체배송리스트</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->

</div>
<form name="doUpdate" action="post">

</form>
<!--	@ BODY WRAP  END  	// -->
</body>
</html>