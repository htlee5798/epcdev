<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"http://www.w3.org/TR/html4/loose.dtd">
<%-- common JS --%>

<!--link rel="stylesheet" href="http://www.lottemart.com/js/ewm/css/front/testPc_popup_@2016.css" type="text/css"-->
<link rel="stylesheet" href="//simage.lottemart.com/css/front/popup@201607.css" type="text/css">

<script type="text/javascript">

$(window).load(function() {
	 
	  //resize 
	  window.resizeTo( 670, 900);
	 
}); 



$(document).ready(function() {
	fnSubmit();
});

function fnSubmit(){
	var hodecoCd = '${hodecoCd}';
	var hodecoInvoiceNo = '${hodecoInvoiceNo}';
	var actionUrl = "";
	var $form1 = $("#form1");
	
	
	//우체국택배
	//if(hodecoCd=='02'){ 
	//	actionUrl = "http://service.epost.go.kr/trace.RetrieveRegiPrclDeliv.postal?sid1="+hodecoInvoiceNo;		
	//삼성택배	
	//}else if(hodecoCd=='03'){
	//	actionUrl = "http://nexs.cjgls.com/web/service02_01.jsp?slipno="+hodecoInvoiceNo;
	//현대택배
	//}else if(hodecoCd=='04'){
	//	actionUrl = "http://www.hlc.co.kr/hydex/jsp/tracking/trackingViewCus.jsp?InvNo="+hodecoInvoiceNo;
	//로젠택배
	//}else if(hodecoCd=='05'){	
	//	actionUrl = "http://www.ilogen.com/iLOGEN.Web.New/TRACE/TraceNoView.aspx?gubun=slipno&slipno="+hodecoInvoiceNo;
	//아주택배	
	//}else 
    if(hodecoCd=='06'){	
		actionUrl = "http://www.ajulogis.co.kr/common/asp/search_history_proc.asp?sheetno="+hodecoInvoiceNo;
    }//KGB택배
	//}else if(hodecoCd=='07'){	
	//	actionUrl = "http://www.kgbls.co.kr/trace/default.asp?sendno="+hodecoInvoiceNo;
	//한진택배
	//}else if(hodecoCd=='08'){	
	//	actionUrl = "http://www.hanjinexpress.hanjin.net/customer/plsql/hddcw07.result?wbl_num="+hodecoInvoiceNo;
	//한진택배 해외배송(롯데마트 제휴)
	//}else 
	if(hodecoCd=='08F'){	
		actionUrl = "http://www.hanjin.co.kr/Delivery_html/lotte/inquiry.jsp?wbl_num="+hodecoInvoiceNo;
	}//대신택배
	//}else if(hodecoCd=='09'){
	//	actionUrl = "http://www.daesinlogistics.co.kr/daesin/jsp/d_freight_chase/d_general_process.jsp?billno1="+hodecoInvoiceNo.substring(0,4)+"&billno2="+hodecoInvoiceNo.substring(4,7)+"&billno3="+hodecoInvoiceNo.substring(7,13);
	//대한통운택배
	//}else if(hodecoCd=='10'){
	//	actionUrl = "https://www.doortodoor.co.kr/parcel/doortodoor.do?fsp_action=PARC_ACT_002&fsp_cmd=retrieveInvNoACT&invc_no="+hodecoInvoiceNo;
	//CJ HTH택배
	//}else if(hodecoCd=='11'){
	//	actionUrl = "http://nexs.cjgls.com/web/service02_01.jsp?slipno="+hodecoInvoiceNo;
	//옐로우캡택배
	//}else if(hodecoCd=='12'){
	//	actionUrl = "http://www.yellowcap.co.kr/custom/inquiry_result.asp?INVOICE_NO="+hodecoInvoiceNo;
	//KT로지스택배
	//}else 
	if(hodecoCd=='13'){
		actionUrl = "http://218.153.4.42/customer/cus_trace_02.asp?invc_no="+hodecoInvoiceNo+"&searchMethod="+"I";
	}//일양택배	
	//}else if(hodecoCd=='14'){
	//	actionUrl = "http://www.ilyanglogis.com/functionality/tracking_result.asp?hawb_no="+hodecoInvoiceNo;
	//CJ GLS택배
	//}else if(hodecoCd=='15'){
	//	actionUrl = "http://nexs.cjgls.com/web/service02_01.jsp?slipno="+hodecoInvoiceNo;
	//하나로 택배
	//}else 
	if(hodecoCd=='16'){
		actionUrl = "http://www.hanarologis.com/branch/chase/listbody.html?a_gb=center&a_cd=4&a_item=0&fr_slipno="+hodecoInvoiceNo;
	//동부택배	
	}//else if(hodecoCd=='17'){
	//	actionUrl = "http://www.dongbups.com/newHtml/delivery/dvsearch_View.jsp?item_no="+hodecoInvoiceNo;
	//천일택배	
	//}else if(hodecoCd=='18'){
	//	actionUrl = "http://www.cyber1001.co.kr/HTrace/HTrace.jsp?transNo="+hodecoInvoiceNo;
	//}			

	if(actionUrl != ""){
	    $form1.attr("action", actionUrl).submit();
	}
}
</script>


<!-- 2016.01.25 -->
<body id="popup">
<form id="form1" name="form1" method="post"></form>
	<div id="pop_wrap" class="pop_w_620 delivery_wrap">
		<form name="#" method="#" action="#">
			<fieldset>
				<!-- legend>배송조회</legend> -->
				<h1 class="de-sprite-title">배송조회</h1>
				<!-- ul class="de-sprite-shipping">
					<li>주문완료</li>
					<li>배송준비중</li>
					<li class="current">배송중</li>
					<li>배송완료</li>
				</ul> -->
				<div class="info">
					<h2>배송정보</h2>
					<table summary="배송정보 입니다. 각 항목으로는 배송업체, 송장번호, 발송인, 수령인이 있습니다.">
						
						<colgroup>
							<col width="20%" />
							<col width="*" />
						</colgroup>
						<tbody>
							<tr>
								<th scope="row">배송업체</th>
								<td>${tCodeNm}</td>
							</tr>
							<tr>
								<th scope="row">송장번호</th>
								<td>${invoiceNo}</td>
							</tr>
							<tr>
								<th scope="row">발송인</th>
								<td>${senderName}</td>
							</tr>
							<tr>
								<th scope="row">수령인</th>
								<td>${receiverName}</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="state">
					<h2>배송상태</h2>
					<div class="inner">
						<!-- s_box -->
						
						<c:forEach items="${trackingDetails}" var="nRow" begin="0"  step="1" varStatus="status">
						
							<div class="s_box">
								<span class="date">${nRow.timeString}</span>
								<p>[${nRow.where}] ${nRow.kind}</p>
																
								<c:set var="manName" value="" />
								
								<c:if test="${nRow.manName ne '' }">
								    <c:set var="manName" value="배송기사 : ${nRow.manName}" />
								</c:if>
								
								<c:if test="${nRow.telno2 ne '' }">
								<span class="driver"> ${manName}<em>${nRow.telno2}</em></span>
								</c:if>
								
							</div>
						
						</c:forEach>
						<!-- // s_box -->

					</div>
				</div>
				<div id="pop_contents">
					<span class="btn_red btn_r_s">
						<span><a href="#none" onclick="window.close();">확인</a></span>
					</span>
					<span class="btn_white btn_w_s">
						<span><a href="#none" onclick="window.close();">취소</a></span>
					</span>
				</div>
			</fieldset>
		</form>
	</div>
</body>
<!-- // 2016.01.25 -->
</html>
