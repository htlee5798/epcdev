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
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%
DecimalFormat df = new DecimalFormat("00");
Calendar currentCalendar = Calendar.getInstance();

currentCalendar.add(currentCalendar.DATE, -90);
String strYear31 = Integer.toString(currentCalendar.get(Calendar.YEAR));
String strMonth31 = df.format(currentCalendar.get(Calendar.MONTH) + 1);
String strDay31 = df.format(currentCalendar.get(Calendar.DATE));
String strDate31 = strYear31 + strMonth31 + strDay31;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:import url="/common/commonHead.do" />
<!-- delivery/PSCMDLV0006 -->
<script type="text/javascript">
var alertMsg = '* 확인사항\n① 택배사에 상품을 인계했는지 확인(인계 후 2시간 뒤 입력)\n② 올바른 택배사/송장번호 입력했는지 확인';
$(function() {

	var venDeliStatusCd = $("select[name=venDeliStatusCd]");
	var venDeliStatusCdArr = "";

	for (i = 0; i < venDeliStatusCd.length; i++) {
		if ($("input[name=checkValue]")[i].checked == true) {
			venDeliStatusCdArr += $("select[name=venDeliStatusCd]")[i].value;
			if (i != venDeliStatusCd.length) {
				venDeliStatusCdArr += ':';
			}
		}
	}

	/* 송장 입력 validation */
	$("input[name=hodecoInvoiceNo], input[name=hodecoAddInvoiceNo]").change(function() {
		if ($(this).val().trim() != "") {
			var hodecoId = $(this).attr('id');
			var hodecoValue = $(this).val();
			var onlnDeliTypeCd = $(this).parents("td").children('input[class="onlnDeliTypeCd"]').val();

			if (onlnDeliTypeCd == '01') {
				for (i=0; i<$("input[name=checkValue]").length; i++) {
					if (hodecoId === $("input[name=checkValue]")[i].value) {
						if ($("input[name=checkValue]")[i].disabled != true) {
							$("input[name=checkValue]")[i].checked = true;
						}
					}

					if (hodecoId === $("select[name=hodecoCd]")[i].id) {
						if ($("select[name=hodecoCd]")[i].value == "%") {
							alert(alertMsg);
							return;
						}
						var hodecoCd = $("select[name=hodecoCd]")[i].value;
					}
				}

				deliveryCheck(hodecoCd, hodecoValue);
			}
		}
	});
	
});

/* jQeury 초기화 */
$(document).ready(function() {

	var bflag = '${searchVO.flag}';

	if ( bflag == "success" ) {
		$('#resultMsg').text('<spring:message code="msg.common.success.select"/>');
	} else if ( bflag == "zero" ) {
		$('#resultMsg').text('<spring:message code="msg.common.info.nodata"/>');
	} else if (bflag == "save") {
		$('#resultMsg').text('<spring:message code="msg.common.success.save"/>');
	} else {
		$('#resultMsg').text('<spring:message code="msg.common.fail.request"/>');
	}

	$('#search').click(function() {
		doSearch();
	});

	$('#excel').click(function() {
		doExcel('down');
	});
	<%-- //	$('#print').click(function() {
//		doPrint('down');
//	}); --%>


	$('#excelUp').click(function() {
		doExcel('up');
	});
	
	$('#sms').click(function() {
		SMSsend();
	});
	<%-- 	/* $('#update').click(function() {
		UpdateStatus();
	}); */ --%>


	$('#startDate').click(function() {
		openCal('searchForm.startDate');
	});
	$('#endDate').click(function() {
		openCal('searchForm.endDate');
	});

	$("input[name=sndPrarDy]").click(function() {
		var idx = $(this).attr('id');
		if (idx == '') {
			openCal('searchForm.sndPrarDy');
		} else {
			clickCal(idx);
		}
	});

	function venDeliStatusCdChange(tag) {

		var sId = tag.parent().attr('id');
		if ($("#d_disabled_"+sId).val() == 'false') {
			if ( tag.val() == '02') {
				// 발송불가 일 경우 발송예정일자 CLEAR
				$("#chk2_"+sId).find('>input[name=sndPrarDy]').attr("value", "");
				$("#chk2_"+sId).find('>input[name=sndPrarDy]').attr("disabled", true);
				$("#chk2_"+sId).find('>select[name=dlayUnavlReasonCd]').attr("value", "01");
				$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReason]').attr("disabled", true);
				$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReason]').attr("value", "결품을 등록해 주세요");
				$("#chk_"+sId).find('>input[name=checkValue]').attr("disabled", false);
				$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReasonBtn]').attr("style", "display:inline-block;");
				$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReasonBtn]').attr("value", "등록");

			} else if ( tag.val() == '07') {

				if (tag.siblings("input[name=oriVenDeliStatusCd]").val()  == tag.val()) {
					//조회시 상태가 배송지연인경우 배송지연으로 다시 등록이 불가능 하다.
					//배송지연1회 등록 가능
					$("#chk2_"+sId).find('>input[name=sndPrarDy]').attr("value", tag.siblings("input[name=oriSndPrarDy]").val());
					$("#chk2_"+sId).find('>input[name=sndPrarDy]').attr("disabled", true);
					$("#chk2_"+sId).find('>select[name=dlayUnavlReasonCd]').attr("value", "03");
					$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReason]').attr("disabled", true);
					$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReason]').attr("value", tag.siblings("input[name=oriDlayUnavlDtlReason]").val());
					$("#chk_"+sId).find('>input[name=checkValue]').attr("checked", false);
					$("#chk_"+sId).find('>input[name=checkValue]').attr("disabled", true);
					alert("배송지연이 1회 등록된 배송건 입니다.");
				} else {
					$("#chk2_"+sId).find('>input[name=sndPrarDy]').attr("disabled", false);
					$("#chk2_"+sId).find('>select[name=dlayUnavlReasonCd]').attr("value", "03");
					$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReason]').attr("disabled", false);
					$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReason]').attr("value", "");
					$("#chk_"+sId).find('>input[name=checkValue]').attr("disabled", false);
				}
				$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReasonBtn]').attr("style", "display:none;");
				$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReasonBtn]').attr("value", "조회");
				$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReasonBtn]').attr("onclick", "");
				
			} else {
				if ( tag.val() == '05' ) {
					$("#chk_"+sId).find('>input[name=checkValue]').attr("disabled", true);
					$("#chk_"+sId).find('>input[name=checkValue]').attr("checked", false);
					$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReasonBtn]').attr("style", "display:none;");
					$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReasonBtn]').attr("value", "조회");
				} else {
					$("#chk_"+sId).find('>input[name=checkValue]').attr("disabled", false);
					$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReasonBtn]').attr("style", "display:none;");
					$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReasonBtn]').attr("value", "조회");
					$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReasonBtn]').attr("onclick", "");
				}

				// 발송불가/배송지연이 아닐 경우 기본값으로 세팅
				$("#chk2_"+sId).find('>input[name=sndPrarDy]').attr("value", "");
				$("#chk2_"+sId).find('>select[name=dlayUnavlReasonCd]').attr("value", "");
				$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReason]').attr("value", "");
				$("#chk2_"+sId).find('>input[name=sndPrarDy]').attr("disabled", true);
				$("#chk2_"+sId).find('>select[name=dlayUnavlReasonCd]').attr("disabled", true);
				$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReason]').attr("disabled", true);
				$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReasonBtn]').attr("style", "display:none;");
				$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReasonBtn]').attr("value", "조회");
				$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReasonBtn]').attr("onclick", "");
			}	
		}
	}

	$("select[name=venDeliStatusCd]").change(function(e) {

		var tag = $(this);
		var sId = tag.parent().attr('id');

		//배송지연 선택
		var oriVenDeliStatusCd = tag.siblings("input[name=oriVenDeliStatusCd]").val();
		var ordDy = tag.siblings("input[name=oriOrdDy]").val();
		var oriOrdStsCd = tag.siblings("input[name=oriOrdStsCd]").val();
		var oriBsketTypeCd = tag.siblings("input[name=oriBsketTypeCd]").val();

		//배송지연 선택 + 결제완료 상태 + 명절장바구니 아님
		if (tag.val() == '07' && oriOrdStsCd == '11' && oriBsketTypeCd != '31') {
			if (oriVenDeliStatusCd == tag.val()) {
				//1. 기존 배송지연건 [= 배송지연은 1회 등록 가능]
				$("#chk2_"+sId).find('>input[name=sndPrarDy]').attr("value", tag.siblings("input[name=oriSndPrarDy]").val());
				$("#chk2_"+sId).find('>input[name=sndPrarDy]').attr("disabled", true);
				$("#chk2_"+sId).find('>select[name=dlayUnavlReasonCd]').attr("value", "03");
				$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReason]').attr("disabled", true);
				$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReason]').attr("value", tag.siblings("input[name=oriDlayUnavlDtlReason]").val());
				$("#chk_"+sId).find('>input[name=checkValue]').attr("checked", false);
				$("#chk_"+sId).find('>input[name=checkValue]').attr("disabled", true);
				alert("배송지연이 1회 등록된 배송건 입니다.");
			} else if (oriOrdStsCd =='11') {
				//2. 결제완료일 +2 일 까지는 배송지연이 등록이 가능하다.
				$.ajax({ 
					type : "POST",
					async : false,
					url : "<c:url value='/delivery/selectDeliveryDlayBlockYn.do'/>",
					dataType : "json",
					timeout : 5000,
					cache : true,
					data : {
						"ordDy" :ordDy
					},
					success: function(blockYn) {
						if (blockYn =="N") { // 배송지연 등록가능
							venDeliStatusCdChange(tag);
						} else { // 배송지연 불가
							$("#chk2_"+sId).find('>input[name=sndPrarDy]').attr("value", "");
							$("#chk2_"+sId).find('>input[name=sndPrarDy]').attr("disabled", true);
							$("#chk2_"+sId).find('>select[name=dlayUnavlReasonCd]').attr("value", "03");
							$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReason]').attr("disabled", true);
							$("#chk2_"+sId).find('>input[name=dlayUnavlDtlReason]').attr("value","");
							$("#chk_"+sId).find('>input[name=checkValue]').attr("checked", false);
							$("#chk_"+sId).find('>input[name=checkValue]').attr("disabled", true);
							alert("결제 완료일로 부터 영업일 기준 2일 후에는\n배송지연 신청이 불가능합니다.\n배송상태를 다시 선택해주세요.");
						}
					}
				});
			}
		} else { // 그외
			venDeliStatusCdChange(tag);
		}
	});

	$("span[name='spanCal']").mouseover(function() {
		if ($(this).prev("input[name=sndPrarDy]").prop("disabled")) {
			$(this).css({"cursor":"not-allowed"});
		} else {
			$(this).css({"cursor":"pointer"});
		}
	});

	$(".onlnDeliTypeCd").click(function() {
		if ($(this).val() == '01') {
			$(this).siblings('#guideText').text('*배송송장번호 입력');
			$(this).siblings('#alertText').text('*송장번호를 정확히 입력하지 않을 시 매출확정 불가');
		} else if ($(this).val() == '02') {
			$(this).siblings('#guideText').text('*설치 기사 이름 입력');
			$(this).siblings('#alertText').text('');
		} else if ($(this).val() == '03') {
			$(this).siblings('#guideText').text('*쿠폰번호 입력');
			$(this).siblings('#alertText').text('');
		} else if ($(this).val() == '04') {
			$(this).siblings('#guideText').text('*해피콜or실제배송시작일자 입력');
			$(this).siblings('#alertText').text('');
		}
	});

	//결품 조회, 등록 이벤트
	$("input[name=dlayUnavlDtlReasonBtn]").click(function() {
		var mode =$('#mode').val().trim();
		var tdId = $(this).parent().attr('id').replace("chk2_","");	
		var counselSeq = $("#chk2_"+tdId).find('>input[name=dlayUnavlDtlReason]').val();
		// 부모 TD ID
		var vendorId = $('#vendorId').val();
		var contents = document.getElementById("chk2_"+tdId);
		var DProdCd = $("#chk2_"+tdId).find('>input[name=DProdCd]').val();
		var DProdNm = encodeURI($("#chk2_"+tdId).find('>input[name=DProdNm]').val());
		var DDeliveryId = $("#chk2_"+tdId).find('>input[name=DDeliveryId]').val();
		var DDeliNo= $("#chk2_"+tdId).find('>input[name=DDeliNo]').val();
		var DOrderId= $("#chk2_"+tdId).find('>input[name=DOrderId]').val();
		var targetUrl = '<c:url value="/PSCMDLV0006/counselView.do"/>?mode='+mode+'&vendorId='+vendorId+'&counselSeq='+counselSeq+'&tdId='+tdId +
			'&orderId='+DOrderId+'&deliNo='+DDeliNo; 
		Common.centerPopupWindow(targetUrl, 'pscmdlv000603', {width : 800, height : 350,scrollBars : 'YES'});
	});

}); // end of ready

 var doubleSubmitFlag = false;
function doubleSubmitCheck() {
	if (doubleSubmitFlag) {
		alert("이미 실행중입니다.");
		setTimeout(function doubleSubmitCheck() { doubleSubmitFlag = false;  },4000);
	} else {
		doubleSubmitFlag = true;
	}
}

//orderitem set
function setOrderItem(counselContent, tdId) {	
	$("#chk2_"+tdId).find('>input[name=counselContent]').attr("value", counselContent);
}
<%--
// 결품등록 팝업
/* function goPopUp(mode) {
		var counselSeq = $("input[name=dlayUnavlDtlReason]").parent().attr('id').replace("chk2_","");
		alert(counselSeq)
		//return;
		var vendorId = $('#vendorId').val();
		
		// 부모 TD ID
		var tdId = $("input[name=sndPrarDy]").parent().attr('id');
		var targetUrl = '<c:url value="/PSCMDLV0006/counselView.do"/>?mode='+mode+'&vendorId='+vendorId+'&counselSeq='+counselSeq+'&tdId='+tdId;
		Common.centerPopupWindow(targetUrl, 'pscmdlv000603', {width : 800, height : 350,scrollBars : 'YES'});
} */--%>

function clickCal(idx) {
	$("input[name=sndPrarDy]").each(function(index) {
		if (idx == $(this).attr("id")) {
			openCal('searchForm.sndPrarDy['+index+']');
		}
	});
}

function sndPrarDyOpenCal(calTag) {
	if (!$(calTag).prev("input[name=sndPrarDy]").prop("disabled")) {
		$(calTag).prev("input[name=sndPrarDy]").click();
	}
}

/* 조회 처리 함수 */
function doSearch() {
	goPage('1');
}

/* 페이징 처리 함수 */
function goPage(pageIndex) {

	doubleSubmitCheck()
	/* if (doubleSubmitCheck()) {
		return;
	}*/
	var f = document.searchForm;
	var getDiff = getDateDiff(f.startDate.value,f.endDate.value);
	var startDate = f.startDate.value.replace( /-/gi, '' );
	var endDate = f.endDate.value.replace( /-/gi, '' );

	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if (f.vendorId.value == "") {
			alert('업체선택은 필수입니다.');
			f.vendorId.focus();
			return;
		}
		</c:when>
	</c:choose>

	if (startDate>endDate) {
		alert('시작일자가 종료일자보다 클수 없습니다.');
		return;
	}

	if (getDiff>90) {
		alert('시작일자보다 종료일자가 90일 이상 클수 없습니다.');
		return;
	}

	// 범위체크
	var f_date = f.startDate.value;
	var t_date = f.endDate.value;

	var from_day = f_date.substring(5,7) + "-" + f_date.substring(8,10)+ "-" + f_date.substring(0,4);
	var to_day = t_date.substring(5,7) + "-" + t_date.substring(8,10)+ "-" + t_date.substring(0,4);

	from_day = new Date(from_day);
	to_day = new Date(to_day);
	var days = Math.ceil((to_day-from_day)/24/60/60/1000);

	//if (days > 7) {
//		alert("검색 최대기간인 7이내만 검색 할 수 있습니다.");
//		return;
	//}

	if (f.searchType.value == "1") {
		if (f.searchContent.value.trim() == "") {
			alert("주문번호를 입력해주세요..!");
			return;
		}
	}

	if (f.searchType.value == "2") {
		if (f.searchContent.value.trim() == "") {
			alert("로그인ID 를 입력해주세요..!");
			return;
		}
	}

	if (f.searchType.value == "3") {
		if (f.searchContent.value.trim() == "") {
			alert("보낸는분의 성함을 입력해주세요..!");
			return;
		}
	}

	if (f.searchType.value == "4") {
		if (f.searchContent.value.trim() == "") {
			alert("받는분의 성함을 입력해주세요..!");
			return;
		}
	}

	if (f.searchType.value == "7") {
		if (f.searchContent.value.trim() == "") {
			alert("원주문번호를 입력해주세요..!");
			return;
		}
	}

	if (f.searchType.value == "8") {
		if (f.searchContent.value.trim() == "") {
			alert("EC주문번호를 입력해주세요..!");
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
function doExcel(gubun) {
	doubleSubmitCheck();
	var f = document.searchForm;

	var startDate = f.startDate.value.replace( /-/gi, '' );
	var endDate = f.endDate.value.replace( /-/gi, '' );

	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if (f.vendorId.value == "") {
			alert('업체선택은 필수입니다.');
			form.vendorId.focus();
			return;
		}
		</c:when>
	</c:choose>

	if (startDate>endDate) {
		alert('시작일자가 종료일자보다 클수 없습니다.');
		return;
	}	

	// 범위체크
	var f_date = f.startDate.value;
	var t_date = f.endDate.value;

	var from_day = f_date.substring(5,7) + "-" + f_date.substring(8,10)+ "-" + f_date.substring(0,4);
	var to_day = t_date.substring(5,7) + "-" + t_date.substring(8,10)+ "-" + t_date.substring(0,4);

	from_day = new Date(from_day);
	to_day = new Date(to_day);
	var days = Math.ceil((to_day-from_day)/24/60/60/1000);

	//if (days > 7) {
//		alert("검색 최대기간인 7이내만 검색 할 수 있습니다.");
//		return;
	//}

	if (f.searchType.value == "1") {
		if (f.searchContent.value.trim() == "") {
			alert("주문번호를 입력해주세요..!");
			return;
		}
	}

	if (f.searchType.value == "2") {
		if (f.searchContent.value.trim() == "") {
			alert("로그인ID 를 입력해주세요..!");
			return;
		}
	}

	if (f.searchType.value == "3") {
		if (f.searchContent.value.trim() == "") {
			alert("보낸는분의 성함을 입력해주세요..!");
			return;
		}
	}

	if (f.searchType.value == "4") {
		if (f.searchContent.value.trim() == "") {
			alert("받는분의 성함을 입력해주세요..!");
			return;
		}
	}

	if (f.searchType.value == "8") {
		if (f.searchContent.value.trim() == "") {
			alert("EC주문번호를 입력해주세요..!");
			return;
		}
	}

	var excelYn;
	$.ajax({
		type : "POST",
		async : false,
		url : "<c:url value='/delivery/selectCustInfo.do'/>",
		dataType : "json",
		timeout : 5000,
		cache : true,
		data : $('#searchForm').serialize(),
		success: function(data) {
			excelYn = data;
		}
	});

	if (excelYn == 'X') {
		alert('배송완료 시점부터 3개월이 지난 고객정보 엑셀파일 다운로드 불가');
		return; 
	}

	if (gubun == 'up') {
		var url = '<c:url value="/delivery/selectPartnerFirmsOrderListExcelForm.do"/>';
	} else {
		var url = '<c:url value="/delivery/selectPartnerFirmsOrderListExcel.do"/>';
	}
	//f.pageIndex.value = pageIndex;
	f.action = url;
	f.submit();
}

/* head 선택 checkBox */
function select_all() {
	var f = document.searchForm;

	if (f.checkValue != null) {
		if (f.allCheck.checked == true) {
			if (f.checkValue != null && f.checkValue.length > 1) {
				for (i=0; i<f.checkValue.length; i++) {
					if (f.checkValue[i].disabled == false)
						f.checkValue[i].checked = true;
				}
			} else {
			if (f.checkValue.disabled == false)
				f.checkValue.checked = true;
			}
		} else {
			if (f.checkValue != null && f.checkValue.length > 1) {
				for (i=0; i<f.checkValue.length; i++) {
					f.checkValue[i].checked = false;
				}
			} else {
				f.checkValue.checked = false;
			}
		}
	}
}

/* header 배송상태 selectBox */
function check_change(obj) {

	var chk_count = 0;
	var f = document.searchForm;
	var i;

	if (f.checkValue != null) {
		if (f.checkValue.length > 1) {
			for (i = 0; i < f.checkValue.length; i++) {
			  	if (f.checkValue[i].checked == true) {
					chk_count++;
					if (obj.name == 'allDelivCodeType') {
						if (f.HvenDeliStatusCd[i].value == '01' || f.HvenDeliStatusCd[i].value == '03') {
							f.venDeliStatusCd[i].value = f.allDelivCodeType.value;
						}
					} else if (obj.name == 'allHodecoCd') {
						f.hodecoCd[i].value = f.allHodecoCd.value;
					}
				}
			}
		} else {
			if (f.checkValue.checked == true) {
				chk_count++;
				if (obj.name == 'allDelivCodeType') {
					if (f.HvenDeliStatusCd.value == '01' || f.HvenDeliStatusCd.value == '03') {
						f.venDeliStatusCd.value = f.allDelivCodeType.value;
					}
				} else if (obj.name == 'allHodecoCd') {
					f.hodecoCd.value = f.allHodecoCd.value;
				}
			}
		}
		return chk_count;
	}
}

/* 저장 */
function UpdateStatus() {

	/* if (doubleSubmitCheck()) {
		return;
	} */
	var f = document.searchForm;
	var type = f.saveDeliTypeCd.value;

	if (f.checkValue != null && check_selected() > 0) {
		var value = getSelectedList();
		if (!value) return;
		f.codeList.value = value;

//		if (type=='04' ) {
//			var url = '<c:url value="/delivery/updatePartnerFirmsItem_short.do"/>';
//		} else {
//			var url = '<c:url value="/delivery/updatePartnerFirmsItem_holy.do"/>';
//		}

		var url = '<c:url value="/delivery/updatePartnerFirmsItem.do"/>';
		f.action = url;
		f.submit();
	} else {
		alert("항목을 체크해주세요!!");
	}
}

/* 저장시 선택 checkBox 갯수 */
function check_selected() {
	var chk_count = 0;
	var f = document.searchForm;
	var i;
	if (f.checkValue != null && f.checkValue.length > 1) {
		for ( i=0; i< f.checkValue.length; i++) {
			if (f.checkValue[i].checked == true) {
				chk_count++;
			}
		}
	} else {
		if (f.checkValue.checked == true) {
			chk_count++;
		}
	}
	return chk_count;
}

/* 저장 목록셋팅 */
function getSelectedList() {

	/* 초기 송장 정보 셋팅 */
	var chkHodecoList = new Array()  //택배사 코드
	, chkHodecoNoList = new Array()  //송장번호
	, chkHodecoAddNoList = new Array()  //추가송장번호
	, chkDeliStatusList = new Array()  //배송 상태
	, invoiceNo = ""
	, addInvoiceNo = "";

	<c:set var="T_ORDER_CHK" />
	<c:set var="T_DELIVERY_CHK" />

	<c:forEach items="${list}" var="list" varStatus="status">
		<c:choose>
			<c:when test="${list.ROWSPAN1<2}">
				chkHodecoList.push("${list.HODECO_CD}");
				chkHodecoNoList.push('<c:out value="${list.HODECO_INVOICE_NO}"/>');
				chkHodecoAddNoList.push('<c:out value="${list.HODECO_ADD_INVOICE_NO}"/>');
				chkDeliStatusList.push("${list.VEN_DELI_STATUS_CD}");
			</c:when>
			<c:otherwise>
				<c:if test="${T_ORDER_CHK != list.ORDER_ID || T_DELIVERY_CHK != list.DELIVERY_ID}">
					chkHodecoList.push("${list.HODECO_CD}");
					chkHodecoNoList.push('<c:out value="${list.HODECO_INVOICE_NO}"/>');
					chkHodecoAddNoList.push('<c:out value="${list.HODECO_ADD_INVOICE_NO}"/>');
					chkDeliStatusList.push("${list.VEN_DELI_STATUS_CD}");
				</c:if>
			</c:otherwise>
		</c:choose>

		<c:set var="T_ORDER_CHK" value="${list.ORDER_ID}"/>
		<c:set var="T_DELIVERY_CHK" value="${list.DELIVERY_ID}"/>
	</c:forEach>
	/* 초기 송장 정보 셋팅 /end */

	var itemlist = "";
	var f = document.searchForm;
	if (f.checkValue.length > 1) {
		for (i = 0; i < f.checkValue.length; i++) {
			if (f.checkValue[i].checked == true) {
				if (f.venDeliStatusCd[i].value =="01") {
					alert("배송상태를 선택하세요.");
					return false;
				}
				if (f.venDeliStatusCd[i].value =="02" && f.counselContent[i].value == "") {
					alert("결품을 등록하세요.");
					return false;
				}

				if (f.venDeliStatusCd[i].value == "05" || f.venDeliStatusCd[i].value == "09") {
					if ($('td[name="HonlnDeliTypeCd"]').eq(i).children('input[class="onlnDeliTypeCd"]:checked').val() == '01') {

						if (f.hodecoInvoiceNo[i].value == "") {
							alert("배송송장번호를 필수로 입력하시기 바랍니다.");
							return false;
						}

						if (f.hodecoInvoiceNo[i].value == f.hodecoAddInvoiceNo[i].value) {
							alert("하나의 주문에 같은 송장번호를 저장할 수 없습니다.");
							return;
						}

						if (f.hodecoInvoiceNo[i].value.length < 8) {
							alert("배송송장번호를 8자리 이상 입력하시기 바랍니다.");
							return false;
						}

						var isNum = isNumberReg(f.hodecoInvoiceNo[i].value);
						if (!isNum) {
							alert('배송송장번호를 숫자만 입력하시기 바랍니다.');
							return false;
						}
					}

					if ($('td[name="HonlnDeliTypeCd"]').eq(i).children('input[class="onlnDeliTypeCd"]:checked').val() == '02') {
						if (f.hodecoInvoiceNo[i].value == "") {
							alert("설치 기사 이름을 필수로 입력하시기 바랍니다.");
							return false;
						}
					}

					if ($('td[name="HonlnDeliTypeCd"]').eq(i).children('input[class="onlnDeliTypeCd"]:checked').val() == '') {
						alert("온라인배송유형을 선택해 주시기바랍니다.");
						return false
					}

					if (f.hodecoCd[i].value =="%") {
						alert("배송회사를 지정해주시기바랍니다.");
						return false;
					}

					if ($('td[name="HonlnDeliTypeCd"]').eq(i).children('input[class="onlnDeliTypeCd"]:checked').val() == '01') {
					
						if ((chkDeliStatusList[i] =='03' || chkDeliStatusList[i] =='01') && f.venDeliStatusCd[i].value == '09') {
							var noChk = deliveryCheck(f.hodecoCd[i].value, f.hodecoInvoiceNo[i].value);
							if (!noChk) {
								//alert("배송중")
								return false;
							}
						}

						/* 배송 상태, 택배사, 송장번호, 추가송장번호의 변동이 있다면 아래 로직을 실행 */
						if (f.venDeliStatusCd[i].value != chkDeliStatusList[i] || chkHodecoNoList[i] != f.hodecoInvoiceNo[i].value || 
								chkHodecoAddNoList[i] != f.hodecoAddInvoiceNo[i].value || chkHodecoList[i] != f.hodecoCd[i].value) {
							if (chkHodecoNoList[i] != f.hodecoInvoiceNo[i].value && chkHodecoAddNoList[i] != f.hodecoAddInvoiceNo[i].value) {
								invoiceNo = f.hodecoInvoiceNo[i].value;
								addInvoiceNo = f.hodecoAddInvoiceNo[i].value;
							} else if (chkHodecoNoList[i] != f.hodecoInvoiceNo[i].value) {
								invoiceNo = f.hodecoInvoiceNo[i].value;
							} else if (chkHodecoAddNoList[i] != f.hodecoAddInvoiceNo[i].value) {
								invoiceNo = f.hodecoAddInvoiceNo[i].value;
							} else {
								if (f.hodecoInvoiceNo[i].value != "" && f.hodecoAddInvoiceNo[i].value != "") {
									invoiceNo = f.hodecoInvoiceNo[i].value;
									addInvoiceNo = f.hodecoAddInvoiceNo[i].value;
								} else if (f.hodecoInvoiceNo[i].value != "") {
									invoiceNo = f.hodecoInvoiceNo[i].value;
								} else if (f.hodecoAddInvoiceNo[i].value != "") {
									invoiceNo = f.hodecoAddInvoiceNo[i].value;
								} else {
									invoiceNo = f.hodecoInvoiceNo[i].value;
								}
							}

							var noChk = hadecoNoChk(f.hodecoCd[i].value , invoiceNo, addInvoiceNo);
							if (!noChk) {
								alert("중복된 송장번호 입니다.");
								return false;
							}
						}
						
					}

				}

				if (f.venDeliStatusCd[i].value =="07") {
					if (f.sndPrarDy[i].value =="") {
						alert("발송예정일자를 등록하시기 바랍니다.");
						return false;
					}

					if (f.sndPrarDy[i].value <= "${searchVO.endDate}") {
						alert("발송예정일자는 현재일 이후로 등록하시기 바랍니다.");
						return false;
					}
				}

				if (f.venDeliStatusCd[i].value =="02" || f.venDeliStatusCd[i].value =="07") {
					if (f.dlayUnavlReasonCd[i].value =="") {
						alert("지연/불가사유를 선택하시기 바랍니다.");
						return false;
					}	
					if (f.dlayUnavlDtlReason[i].value == "") {
						alert("지연/불가상세 사유를 등록하시기 바랍니다.");
						return false;
					}
				}

				if (f.hodecoCd[i].value !="%") {
					if (f.hodecoInvoiceNo[i].value == "") {
						alert("배송회사를 선택하였으나 송장번호가 없읍니다.");
						return false;  
					}
				}

				if (f.hodecoInvoiceNo[i].value != "") {
					if (f.hodecoCd[i].value =="%") {
						alert("배송회사를 지정해주시기바랍니다.");
						return false;
					}
				}

				if (itemlist == "") {
					itemlist += f.checkValue[i].value + ":" + f.venDeliStatusCd[i].value + ":" + 
								f.hodecoCd[i].value + ":" + f.hodecoInvoiceNo[i].value + ":" + 
								f.delivery_id[i].value + ":" + f.deliNo[i].value + ":" + 
								f.deliStatusCd[i].value + ":" + f.venCd[i].value + ":" +
								f.hodecoAddInvoiceNo[i].value + ":" + f.sndPrarDy[i].value + ":" + 
								f.dlayUnavlReasonCd[i].value + ":" + f.dlayUnavlDtlReason[i].value + ":" +
								$('td[name="HonlnDeliTypeCd"]').eq(i).children('input[class="onlnDeliTypeCd"]:checked').val()+":"+
								f.counselContent[i].value + ":" + 
								f.onlineProdTypeCd[i].value + ":" + f.orgOnlnDeliTypeCd[i].value + ":itemlist1"; /* 20181016 테스트후 삭제*/
				} else {
					itemlist += "■"+ f.checkValue[i].value + ":" + f.venDeliStatusCd[i].value + ":" + 
									f.hodecoCd[i].value + ":" + f.hodecoInvoiceNo[i].value + ":" + 
									f.delivery_id[i].value + ":" + f.deliNo[i].value + ":" + 
									f.deliStatusCd[i].value + ":" + f.venCd[i].value + ":" +
									f.hodecoAddInvoiceNo[i].value + ":" + f.sndPrarDy[i].value + ":" + 
									f.dlayUnavlReasonCd[i].value + ":" + f.dlayUnavlDtlReason[i].value + ":" +
									$('td[name="HonlnDeliTypeCd"]').eq(i).children('input[class="onlnDeliTypeCd"]:checked').val()+":"+
									f.counselContent[i].value + ":" +
									f.onlineProdTypeCd[i].value + ":" + f.orgOnlnDeliTypeCd[i].value + ":itemlist2"; /* 20181016 테스트후 삭제*/
				}
			}
		}

	} else {
		if (f.checkValue.checked == true) {
			if (f.venDeliStatusCd.value =="01") {
				alert("배송상태를 선택하세요.");
				return false;
			}
			if (f.venDeliStatusCd.value =="02" && f.counselContent.value == "") {
				alert("결품을 등록하세요.");
				return false;
			}

			if (f.venDeliStatusCd.value =="05" || f.venDeliStatusCd.value =="09") {
				if ($('td[name="HonlnDeliTypeCd"]').children('input[class="onlnDeliTypeCd"]:checked').val() == '01') {

					if (f.hodecoInvoiceNo.value == "") {
						alert("배송송장번호를 필수로 입력하시기 바랍니다.");
						return false;
					}

					if (f.hodecoInvoiceNo.value == f.hodecoAddInvoiceNo.value) {
						alert("하나의 주문에 같은 송장번호를 저장할 수 없습니다.");
						return;
					}

					if (f.hodecoInvoiceNo.value.length < 8) {
						alert("배송송장번호를 8자리 이상 입력하시기 바랍니다.");
						return false;
					}

					var isNum = isNumberReg(f.hodecoInvoiceNo.value);
					if (!isNum) {
						alert('배송송장번호를 숫자만 입력하시기 바랍니다.');
						return false;
					}
				}

				if ($('td[name="HonlnDeliTypeCd"]').children('input[class="onlnDeliTypeCd"]:checked').val() == '02') {
					if (f.hodecoInvoiceNo.value == "") {
						alert("설치 기사 이름을 필수로 입력하시기 바랍니다.");
						return false;  
					}
				}

				if ($('td[name="HonlnDeliTypeCd"]').children('input[class="onlnDeliTypeCd"]:checked').val() == '') {
					alert("온라인배송유형을 선택해 주시기바랍니다.");
					return false;
				}

				if (f.hodecoCd.value == "%") {
					alert("배송회사를 지정해주시기바랍니다.");
					return false;
				}

				if ($('td[name="HonlnDeliTypeCd"]').children('input[class="onlnDeliTypeCd"]:checked').val() == '01') {

					if ((chkDeliStatusList[0] =='03' || chkDeliStatusList[0] =='01') && f.venDeliStatusCd.value == '09') {
						var noChk = deliveryCheck(f.hodecoCd.value, f.hodecoInvoiceNo.value);
						if (!noChk) {
							return false;
						}
					}
					if (f.venDeliStatusCd.value != chkDeliStatusList || chkHodecoNoList != f.hodecoInvoiceNo.value || 
						chkHodecoAddNoList != f.hodecoAddInvoiceNo.value || chkHodecoList != f.hodecoCd.value) {
						if (chkHodecoNoList != f.hodecoInvoiceNo.value && chkHodecoAddNoList != f.hodecoAddInvoiceNo.value) {
							invoiceNo = f.hodecoInvoiceNo.value;
							addInvoiceNo = f.hodecoAddInvoiceNo.value;
						} else if (chkHodecoNoList != f.hodecoInvoiceNo.value) {
							invoiceNo = f.hodecoInvoiceNo.value;
						} else if (chkHodecoAddNoList != f.hodecoAddInvoiceNo.value) {
							invoiceNo = f.hodecoAddInvoiceNo.value;
						} else {
							if (f.hodecoInvoiceNo.value != "" && f.hodecoAddInvoiceNo.value != "") {
								invoiceNo = f.hodecoInvoiceNo.value;
								addInvoiceNo = f.hodecoAddInvoiceNo.value;
							} else if (f.hodecoInvoiceNo.value != "") {
								invoiceNo = f.hodecoInvoiceNo.value;
							} else if (f.hodecoAddInvoiceNo.value != "") {
								invoiceNo = f.hodecoAddInvoiceNo.value;
							} else {
								invoiceNo = f.hodecoInvoiceNo.value;
							}
						}
						var noChk = hadecoNoChk(f.hodecoCd.value, invoiceNo, addInvoiceNo);
						if (!noChk) {
							alert("중복된 송장번호 입니다.");
							return false;
						}
					}
				}
			}

			if (f.venDeliStatusCd.value =="07") {
				if (f.sndPrarDy.value =="") {
					alert("발송예정일자를 등록하시기 바랍니다.");
					return false;
				}
				if (f.sndPrarDy.value <= "${searchVO.endDate}") {
					alert("발송예정일자는 현재일 이후로 등록하시기 바랍니다.");
					return false;
				}
			}

			if (f.venDeliStatusCd.value =="02" || f.venDeliStatusCd.value =="07") {
				if (f.dlayUnavlReasonCd.value =="") {
					alert("지연/불가사유를 선택하시기 바랍니다.");
					return false;
				}
				if (f.dlayUnavlDtlReason.value == "") {
					alert("지연/불가상세 사유를 등록하시기 바랍니다.");
					return false;
				}
			}

			if (f.hodecoCd.value !="%") {
				if (f.hodecoInvoiceNo.value == "") {
					alert("배송회사를 선택하였으나 송장번호가 없습니다.");
					return false;
				}
			}

			if (f.hodecoInvoiceNo.value != "") {
				if (f.hodecoCd.value =="%") {
					alert("배송회사를 지정해주시기바랍니다.");
					return false;
				}
			}

			itemlist += f.checkValue.value + ":" + f.venDeliStatusCd.value + ":" + 
						f.hodecoCd.value + ":" + f.hodecoInvoiceNo.value + ":" + 
						f.delivery_id.value + ":" + f.deliNo.value + ":" + 
						f.deliStatusCd.value + ":" + f.venCd.value + ":" +
						f.hodecoAddInvoiceNo.value + ":" + f.sndPrarDy.value + ":" + 
						f.dlayUnavlReasonCd.value + ":" + f.dlayUnavlDtlReason.value + ":" +
						$('td[name="HonlnDeliTypeCd"]').children('input[class="onlnDeliTypeCd"]:checked').val()+":"+
						f.counselContent.value + ":" + 
						f.onlineProdTypeCd.value + ":" + f.orgOnlnDeliTypeCd.value + ":itemlist3"; /* 20181016 테스트후 삭제*/
		}
	}
	return itemlist;
}

/*************************************************
 * 이미 등록된 송장인지 체크
 *************************************************/
function hadecoNoChk(hodecoCd, invoiceNo, addInvoiceNo) {

	var params = {"hodecoCd" : hodecoCd, "invoiceNo" : invoiceNo, "addInvoiceNo" : addInvoiceNo};
	var result;

	$.ajax({
		 type : "POST"
		, url :  "<c:url value='/PSCMDLV0006/hodecoNoChk.do'/>"
		, async : false
		, data : params
		, success : function(data) {
			if (data == "Y") {
				result = false;
				if (addInvoiceNo == "") {
				// alert(alertMsg);
				} else {
				// alert(alertMsg);
				}
			} else {
				result = true;
			}
		}
		, error : function(data) {
			console.log(data);
		}
	});

	return result;
}

function deliveryCheck(hodecoCd, hodecoValue) {

	var params = {"hodecoCd" : hodecoCd, "hodecoInvoiceNo" : hodecoValue};
	var result;

	$.ajax({
	  type : "POST"
	, url : "<c:url value='/PSCMDLV0006/deliveryCheck.do'/>"
	, async : false
	, data : params
	, success : function(data) {
		if (data == "Y") {
			alert(alertMsg);
			result = false;
		} else {
			result = true;
		}
	}
	, error : function(data) {
		console.log(data);
	}
	});

	return result;
}

function SMSsend() {

	var f = document.searchForm;

//	if (f.deliStatusCode.value != "05") {
//		alert("배송상태가 발송완료인 주문만 SMS를 발송할 수 있습니다.\n\r상단 조회조건중 배송상태를'발송완료'로 선택하여 조회하세요.");
//		return;
//	}

	if (f.deliStatusCode.value != "09") {
		alert("배송상태가 배송중인 주문만 SMS를 발송할 수 있습니다.\n\r상단 조회조건중 배송상태를'배송중'으로 선택하여 조회하세요.");
		return;
	}

	if (confirm("배송SMS를  발송하시겠습니까?")) {
		var url = '<c:url value="/delivery/insertSendSMS.do"/>';
		f.target = "_self";
		f.action = url;
		f.submit();
	}
}

function isNumberReg(value) {
	var num_regx=/^[0-9]*$/;
	var val = value.replaceAll('-','');
	if (num_regx.test(val) == false) { return false; }
	return true;
}

/* 배송추적 */
function delivComSearch(hodecoCd, hodecoInvoiceNo) {

	var targetUrl = '<c:url value="/PSCMDLV0006/selectDeliveryStatus.do"/>?hodecoCd='+ hodecoCd + '&hodecoInvoiceNo=' + hodecoInvoiceNo;
	Common.centerPopupWindow(targetUrl, '', {
		width : 650,
		height : 900,
		scrollBars : 'YES'
	});
}

/* 협력업체검색팝업 */
function vendorPopUp() {
	var targetUrl = '<c:url value=""/>';
	Common.centerPopupWindow(targetUrl, 'popup', {width : 800, height : 550});
}

/* 배송지 변경이력 */
function deliHistPopUp() {
	var targetUrl = '<c:url value="/delivery/selectDeliHistPopup.do"/>';
	Common.centerPopupWindow(targetUrl, 'popup', {width : 800, height : 650, scrollBars : "YES"});
}

/* 택배사코드팝업 */
function deliCodePopUp() {
	var targetUrl = '<c:url value="/common/selectDeliCodePopup.do"/>';
	Common.centerPopupWindow(targetUrl, 'popup', {width : 400, height : 650, scrollBars : "YES"});
}

function setVendorInto(strVendorId, strVendorNm, strCono) { // 협력업체 검색창에서 호출
	$("#vendorId").val(strVendorId);
}

/* 송장업로드 */
function excelUpload() {
	var targetUrl = '<c:url value="/common/selectPartnerInvoiceNoPopup.do"/>';
	Common.centerPopupWindow(targetUrl, 'popup', {width : 1500, height : 600});
}

function chk_Hangul(str) {
	for(var i = 0; i < str.length; i++) {
		oneChar = str.charAt(i); // 한 글자 추출
		if (escape(oneChar).length <= 4) { // 한글은 escape로 변환하면 6자
			return true;
		}
	}
	return false;
}

function check_inputByte(obj, maxByte) {
	var maxByte = maxByte;
	var str = obj.value; // 입력한 문자
	var totalByte = 0; 
	var n = 0;

	for(var i = 0; i < str.length; i++) {
		var oneChar = str.charAt(i);
		if (escape(oneChar).length>4) {
			totalByte += 2; //한글
		} else {
			totalByte++; // 영문,숫자,특수문자
		}
		if (totalByte <= maxByte) {
			n = i+1; 
		}
	}

	// 150바이트 초과시 텍스트 자르기
	if (totalByte > maxByte) {
		alert('지연/불가 상세사유의 입력값이 150byte를 초과하였습니다.');
		var str2 = str.substr(0,n);
		obj.value = str2;
	}
}

</script>
</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<c:set var="startDate" value='<%=strDate31%>' />
<div id="content_wrap">

	<div class="content_scroll">

	<form id="searchForm" name="searchForm" method="post">
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
							<%-- <c:if test="${'04' eq searchVO.deliTypeCd}"><a href="#" class="btn" id="sms"><span><spring:message code="button.common.smsSend"/></span></a></c:if> --%>
							<a href="javascript:doExcel('up');" class="btn" ><span>업로드양식</span></a>
							<a href="javascript:doExcel('down');" class="btn" ><span><spring:message code="button.common.excel" /></span></a>							
							<a href="javascript:SMSsend();" class="btn" ><span><spring:message code="button.common.smsSend"/></span></a>
							<a href="javascript:doSearch();" class="btn" ><span><spring:message code="button.common.inquire"/></span></a>
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
								<%-- <option value="3" <c:if test="${'3' eq searchVO.dateGbn}">selected="selected"</c:if>>발송예정일</option> --%>
							</select>
						</th>
						<td>
							<input type="text" name="startDate" id="startDate" class="day" readonly style="width:33%;" value="${searchVO.startDate}" /><a href="javascript:openCal('searchForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
							<%--<input type="text" name="startDate" id="startDate" class="day" readonly style="width:33%;" value="<c:out value="${fn:substring(startDate,0,4)}-${fn:substring(startDate,4,6)}-${fn:substring(startDate,6,8)}" />" /><a href="javascript:openCal('searchForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>--%> 
							~
							<input type="text" name="endDate" id="endDate" class="day" readonly style="width:33%;" value="${searchVO.endDate}" /><a href="javascript:openCal('searchForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
						</td>
						<th>
							<select name="searchType" class="select">
								<option value="%" <c:if test="${'%' eq searchVO.searchType}">selected="selected"</c:if>>선택</option>
								<option value="1" <c:if test="${'1' eq searchVO.searchType}">selected="selected"</c:if>>주문번호</option>
								<option value="3" <c:if test="${'3' eq searchVO.searchType}">selected="selected"</c:if>>보내는분</option>
								<option value="4" <c:if test="${'4' eq searchVO.searchType}">selected="selected"</c:if>>받는분</option>
								<option value="5" <c:if test="${'5' eq searchVO.searchType}">selected="selected"</c:if>>상품코드</option>
								<option value="6" <c:if test="${'6' eq searchVO.searchType}">selected="selected"</c:if>>판매코드</option>
								<option value="7" <c:if test="${'7' eq searchVO.searchType}">selected="selected"</c:if>>원주문번호</option>
								<option value="8" <c:if test="${'8' eq searchVO.searchType}">selected="selected"</c:if>>EC주문번호</option>
							</select>
						</th>
						<td class="text"><input type="text" name="searchContent" value="${searchVO.searchContent}"/></td>
						<!-- <th>협력업체코드</th> -->
						<%-- <td class="text"><input type="text" id="strVendorNm" name="strVendorNm" value="${searchVO.strVendorNm}" onDblClick="javascript:vendorPopUp();" readonly/></td> --%>
						<%-- <input type="hidden" id="strVendorId" name="strVendorId" value="${searchVO.strVendorId}"> --%>
						<th>협력업체코드</th>
						<td>
							<c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<c:if test="${not empty vendorId}">
											<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="${vendorId}" style="width:40%;"/>
										</c:if>
										<c:if test="${empty vendorId}">
											<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
										</c:if>
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<select name="vendorId" id="vendorId" class="select">
											<option value="">전체</option>
										<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
											<c:if test="${not empty vendorId}">
												<option value="${venArr}" <c:if test="${venArr eq vendorId}">selected</c:if>>${venArr}</option>
											</c:if>
											<c:if test="${empty vendorId}">
												<option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
											</c:if>
										</c:forEach>
										</select>
									</c:when>
						</c:choose>
						</td>
					</tr>
					<tr>
						<th>배송구분</th>
						<td>
							<select name="deliTypeCd" class="select">
								<option value="04" <c:if test="${'04' eq searchVO.deliTypeCd}">selected="selected"</c:if>>일반배송</option>
								<option value="06" <c:if test="${'06' eq searchVO.deliTypeCd}">selected="selected"</c:if>>명절배송</option>
								<option value="%" selected="selected"<c:if test="${'%' eq searchVO.deliTypeCd}">selected="selected"</c:if>>전체</option>
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
				<font color="red"><b>1. 일반/예약 상품</br>① 미발송 →  배송예정으로 수기 전환 (확인 후 임의 고객취소 방지)</br>② 배송예정 → 배송중 수기 전환 (올바른 택배사/송장번호 입력 필수)</br>
				③ 배송예정 또는 배송중 단계에서 택배사/송장번호가 올바르다면 배송완료 단계로 전환 및 매출 확정</br>
				<font color="blue"><b>
				※ 합배송, 중복송장인 경우에는 발송예정으로 배송상태를 변경하신 후, 택배사와 송장번호를 입력하시면 매출확정 처리 됩니다.</br>
				※ 일반상품에 한하여 운송장 오등록, 직접배송 등 배송추적이 불가능한 경우, 배송중 상태에서 26일 경과 시 (27일차) 자동 배송완료 처리 됩니다. (주문제작/예약/설치/직배송/무형상품 및 명절주문 제외)
				</b></font></br>
				2. 설치/직배송/무형</br>① 미발송 → 배송예정/배송중 수기 전환</br>② 배송중 단계에서 자동 Batch에 의한 매출확정 </b></font></br>
				<%-- <font color="red"><b>* 올바른 택배사/송장번호 입력 시, 매출 확정 가능</br>① 송장번호 입력(발송예정단계) ② 상품 택배기사 인계  ③ 올바른 택배사/송장번호 입력시 배송중 단계로 자동 처리</br>
				※ 별도 발송예정 → 배송중 단계는 자동 변경 처리 가능하며, 매출 확정은 상품 고객 인수 시점에 반영됩니다. </b></font> --%>
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">##[주의] 발송 완료된 주문은 수정할 수 없습니다!! ## </li>
						<li class="btn">
							<a href="javascript: deliHistPopUp();" class="btn" ><span>배송지변경이력</span></a>
							<a href="javascript: deliCodePopUp();" class="btn" ><span>택배사코드</span></a>
							<a href="javascript: excelUpload();" class="btn" ><span>송장업로드</span></a>
							<a href="javascript:UpdateStatus();"  id="updStat" class="btn" ><span><spring:message code="button.common.save"/></span></a>
						</li>
					</ul>
					<div style="overflow-y:scroll;width:100%;height:500px;">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:80px" />
						<col style="width:130px" />
						<col style="width:90px" />
						<col style="width:120px" />
						<col style="width:110px" />
						<col style="width:110px" />
						<col style="width:110px" />
						<col style="width:100px" />
						<col style="width:100px" />
						<col style="width:150px" />
						<col style="width:150px" />
						<col style="width:120px" />
						<col style="width:120px" />
						<col style="width:280px" />
						<col style="width:50px" />
						<%--<col style="width:70px" />
						<col style="width:80px" />
						<col style="width:150px" />
						<col style="width:70px" />
						<col style="width:70px" /> --%>
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
						<th>보내는분(H.P)</th>
						<th colspan="3">(우편번호)주소</th>
						<th>개인통관번호</th>
						<th colspan="2">메모</th>
					</tr>
					<tr>
						<th rowspan="2">주문번호<br/><font color='red'>(원주문번호)</font><br/><font color='blue'>(EC주문번호)</font></th>
						<th>상품코드</th>
						<th>판매코드</th>
						<th colspan="3">상품명</th>
						<th>상품가격</th>
						<th>희망발송일</th>
						<th rowspan="2">배송상태
							<select name="allDelivCodeType" class="select"  OnChange="check_change(this);">
								<c:forEach items="${DE014List}" var="codeList">
								<option value="${codeList.MINOR_CD}">${codeList.CD_NM}</option>
								</c:forEach>
							</select>
						</th>
						<th>발송예정일자</th>
						<th>지연/불가사유</th>
						<th rowspan="2">배송추적</th>
						<th rowspan="2">택배사<br/>
							<select name="allHodecoCd" class="input_text" style="width:90%;" OnChange="check_change(this);">
								<option>미확인</option>
								<c:forEach items="${DE011List}" var="codeList">
								<option value="${codeList.MINOR_CD}">${codeList.CD_NM}</option>
								</c:forEach>
							</select>
						</th>
						<th>송장번호<%-- (송장번호임시생성<input name="tempHodecoInvoiceNo" type="checkbox" onclick="check_change(this);"/>) --%></th>
						<th>선택<input type="checkbox" name="allCheck" onClick="select_all()"></th>
					</tr>
					<tr>
						<th>업체명</th>
						<th>주문상태</th>
						<th colspan="2">옵션</th>
						<th colspan="1">규격</th>
						<th>주문수량</th>
						<th>발송완료일</th>
						<th COLSPAN="2">지연/불가상세사유</th>
						<th>추가송장번호</th>
						<th>SMS</th>
					</tr>
		<c:set var="T_ORDER_ID"/>
		<c:set var="T_DELIVERY_ID"/>
		<c:set var="listCnt" value="${fn:length(list)}"/>
		<c:forEach var="list" items="${list}" varStatus="status">
		<input type="hidden" id="HvenDeliStatusCd"  name="HvenDeliStatusCd" value="${list.VEN_DELI_STATUS_CD}"/>
			<c:if test="${T_ORDER_ID != list.ORDER_ID || T_DELIVERY_ID != list.DELIVERY_ID}">
					<tr class="r1">
						<td height="1" colspan="15" style="color: blue;">
							<input type="hidden" name="deliNo" value="${list.DELI_NO}"/>
							<%-- <input type="hidden" name="invoiceSeq" value="${list.INVOICE_SEQ}"/> --%>
							<input type="hidden" name="venCd" value="${list.VEN_CD}"/>
							<input type="hidden" name="deliStatusCd" value="${list.DELI_STATUS_CD}"/>
							<input type="hidden" name=onlineProdTypeCd value="${list.ONLINE_PROD_TYPE_CD}">
							<input type="hidden" name=orgOnlnDeliTypeCd value="${list.ORG_ONLN_DELI_TYPE_CD}">
						</td>
					</tr>
				<c:choose>
					<c:when test="${list.ORD_STS_CD == '20' || list.ORD_STS_CD == '21' || list.ORD_STS_CD == '22' || list.ORD_STS_CD == '30' || list.ORD_STS_CD == '31' 
								|| list.ORD_STS_CD == '32' || list.ORD_STS_CD == '40' || list.ORD_STS_CD == '41' || list.ORD_STS_CD == '42' || list.ORD_STS_CD == '43'}">
							<c:set var="divColor" value="#FF2424"></c:set>
					</c:when>
					 <c:when test="${list.VEN_DELI_STATUS_CD eq '01'  }">
							<c:set var="divColor" value="#B2CCFF"></c:set>
					 </c:when>
					<c:otherwise>
							<c:set var="divColor" value="url('/images/common/layout/bg_list_03.gif') repeat-x left top"></c:set>
					</c:otherwise>
				</c:choose>
					<tr>
						<td style="background: ${divColor}"><c:out value="${list.DELIVERY_ID}"/></td>
						<td style="background: ${divColor}">${list.STR_NM}</td>
						<td style="background: ${divColor}"><fmt:parseDate value="${list.ORD_DY}" var="dateFmt" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd" /></td>
						<td style="background: ${divColor}"><c:out value="${list.ORD_TM}"/></td>
						<td style="background: ${divColor}"><fmt:formatNumber value="${list.DELIV_AMT}" pattern="#,##0" /></td>
						<td style="background: ${divColor}"><c:out value="${list.RECP_PSN_TEL_NO}"/></td>
						<td style="background: ${divColor}"><c:out value="${list.RECP_PSN_CELL_NO}"/></td>
						<td style="background: ${divColor}"><c:out value="${list.TO_PSN_NM}"/></td>
						<td style="background: ${divColor}"><c:out value="${list.FROM_PSN_NM}(${list.CUST_CELL_NO})"/></td>
						<td style="background: ${divColor}" colspan="3">(<c:out value="${list.RECP_PSN_POST_NO}"/>)<c:out value="${list.ADDR}"/></td>
					<%-- <c:if test="${T_ORDER_ID != list.ORDER_ID || T_DELIVERY_ID != list.DELIVERY_ID}"> --%>
						<td style="background: ${divColor}" rowspan="1"><c:out value="${list.CURS_CLRN_NUM}"/></td>
					<%-- </c:if> --%>
						<td style="background: ${divColor}" colspan="2"><c:out value="${list.DELI_MSG}"/></td>
					</tr>
			</c:if>
			<c:set var="tr_cnt" value="${list.DELI_NO}"/>
			<c:choose>
				<c:when test="${list.ROWSPAN1<2}">
					<tr>
						<td rowspan="2" style="background: ${divColor}"><c:out value="${list.ORDER_ID}"/><br/><font color='red'><c:out value="${list.FIRST_ORDER_ID}"/></font><br/><font color='blue'><c:out value="${list.EC_ORDER_ID}"/></font></td>
						<td style="background: ${divColor}"><c:out value="${list.PROD_CD}"/></td>
						<td style="background: ${divColor}"><c:out value="${list.MD_SRCMK_CD}"/></td>
						<c:if test="${(list.OX == 'O')}">
						<td style="background: ${divColor}" colspan="3" ><c:out value="${list.PROD_NM}"/></td>
							</c:if>
						<c:if test="${(list.OX == 'X')}">
						<td style="background: ${divColor}" colspan="3"  bgcolor="yellow"><font color='red'><c:out value="${list.PROD_NM}"/></font></td>
						</c:if>
						<td style="background: ${divColor}"><fmt:formatNumber value="${list.TOT_SELL_AMT}" pattern="#,##0" /></td>
						<td style="background: ${divColor}"><c:out value="${list.ENTP_DELI_PRAR_DY}"/></td>
						<td style="background: ${divColor}" rowspan="2" id="${tr_cnt}" >
							<input type="hidden" name="oriVenDeliStatusCd" value="${list.VEN_DELI_STATUS_CD}"/>
							<input type="hidden" name="oriDlayUnavlDtlReason" value="${list.DLAY_UNAVL_DTL_REASON }"/>
							<input type="hidden" name="oriSndPrarDy" value="${list.SND_PRAR_DY}"/>
							<input type="hidden" name="oriOrdStsCd" value="${list.ORD_STS_CD}"/>
							<input type="hidden" name="oriOrdDy" value="${list.ORD_DY}"/>
							<input type="hidden" name="oriBsketTypeCd" value="${list.BSKET_TYPE_CD}"/>
							<select name="venDeliStatusCd" class="input_text" <c:if test="${(list.HODECO_INVOICE_NO != '' && list.VEN_DELI_STATUS_CD eq '05')|| (list.HODECO_INVOICE_NO != '' && list.VEN_DELI_STATUS_CD eq '09') || list.VEN_DELI_STATUS_CD eq '02' || list.ORD_STS_CD eq '20' || list.ORD_STS_CD eq '21'}">disabled="disabled"</c:if>>
								<c:forEach items="${DE014List}" var="codeList">
								<option value="${codeList.MINOR_CD}" <c:if test="${list.VEN_DELI_STATUS_CD eq codeList.MINOR_CD}">selected="selected"</c:if>>${codeList.CD_NM}</option>
								</c:forEach>
							</select>
						</td>
						<!-- 20160530 수정 -->
						<td style="background: ${divColor}" colspan="2" rowspan="<c:out value="${list.ROWSPAN2}"/>" id="chk2_${tr_cnt}">
							<!-- 발송예정일자 -->
							<c:if test="${ listCnt == 1}">
								<input type="text" name="sndPrarDy" id="" readonly style="width:40%; vertical-align: middle;" disabled="disabled" value="${list.SND_PRAR_DY}" />
								<span name="spanCal" onclick="javascript:sndPrarDyOpenCal(this);"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></span>
							</c:if>
							<c:if test="${ listCnt > 1 && listCnt != list.ROWSPAN1}">
								<input type="text" name="sndPrarDy" id="${ status.index }" readonly style="width:40%; vertical-align: middle;" disabled="disabled" value="${list.SND_PRAR_DY}" />
								<span name="spanCal" onclick="javascript:sndPrarDyOpenCal(this);"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></span>
							</c:if>
							&nbsp;&nbsp;
							<!-- 사유코드 --> 
							<select name="dlayUnavlReasonCd" disabled="disabled" class="input_text">
								<option value="">선택</option>
								<c:forEach items="${DE017List}" var="codeList">
								<option value="${codeList.MINOR_CD}" <c:if test="${list.DLAY_UNAVL_REASON_CD eq codeList.MINOR_CD}">selected="selected"</c:if>>${codeList.CD_NM}</option>								
								</c:forEach>
							</select>
							<br/><br/>
							<!-- 지연/불가상세사유 -->
								<input type="text" name="dlayUnavlDtlReason" onkeyup="check_inputByte(this,150)" style="width:70%;" disabled="disabled" value="<c:if test="${list.VEN_DELI_STATUS_CD == '02' || list.VEN_DELI_STATUS_CD == '07'}">${list.DLAY_UNAVL_DTL_REASON }</c:if>"/>
								<input type="hidden" name="DProdCd" value="<c:out value="${list.PROD_CD}"/>"/>
								<input type="hidden" name="DProdNm" value="<c:out value="${list.PROD_NM}"/>"/>
								<input type="hidden" name="DDeliveryId" value="<c:out value="${list.DELIVERY_ID}"/>"/>
								<input type="hidden" name="DDeliNo" value="${list.DELI_NO}"/>
								<input type="hidden" name="DOrderId" value="${list.ORDER_ID}"/>
								<input type="button" id= "dlayUnavlDtlReasonBtn" name="dlayUnavlDtlReasonBtn"  value=" <c:if test="${list.VEN_DELI_STATUS_CD == '02'}">조회 </c:if>"  onclick="<c:if test="${list.VEN_DELI_STATUS_CD == '02'}"></c:if>"  <c:if test="${list.VEN_DELI_STATUS_CD != '02'}">style="display: none;"</c:if>  <c:if test="${list.VEN_DELI_STATUS_CD == '02'}">style="display:inline-block;"</c:if>/>	
								<input type="hidden"  id="counselContent" name="counselContent"  value=""/>
								<input type="hidden"  id="mode" name="mode" value="<c:if test="${list.VEN_DELI_STATUS_CD == '02'}">search</c:if><c:if test="${list.VEN_DELI_STATUS_CD != '02'}">insert</c:if>" />
						</td>
						<td style="background: ${divColor}" rowspan="<c:out value="${list.ROWSPAN2}"/>" >
							<c:if test="${list.HODECO_INVOICE_NO != ''}">
								<c:if test="${list.HODECO_CD != '01' && list.HODECO_CD != '%' && list.VEN_DELI_STATUS_CD == '05'}">
									<a href="#" onClick="javascript:delivComSearch('<c:out value="${list.HODECO_CD}"/>','<c:out value="${list.HODECO_INVOICE_NO}"/>');">배송추적</a>
								</c:if>
							</c:if>
						</td>
						<td style="background: ${divColor}" rowspan="2">
							<select name="hodecoCd" id="<c:out value="${list.ORDER_ID}"/>:<c:out value="${list.ORDER_ITEM_SEQ}"/>" class="input_text" style="width:90%;" >
								<option value="%" <c:if test="${'%' eq codeList.MINOR_CD}">selected="selected"</c:if>>미지정</option>
								<c:forEach items="${DE011List}" var="codeList">
								<option value="${codeList.MINOR_CD}" <c:if test="${list.HODECO_CD eq codeList.MINOR_CD}">selected="selected"</c:if>>${codeList.CD_NM}</option>
								</c:forEach>
							</select>
						</td>
						<!-- 20160530 수정 -->
						<td style="background: ${divColor}" rowspan="2" id="HonlnDeliTypeCd" name="HonlnDeliTypeCd">
							<!-- 2016-11-30 최성웅 해당상품의 배송타입만 노출 -->
							<c:if test="${list.ONLN_DELI_TYPE_CD eq '01'}">
								<input type="radio" class="onlnDeliTypeCd" name="onlnDeliTypeCd_${status.count}" value="01" ${list.ONLN_DELI_TYPE_CD eq '01' || list.ONLN_DELI_TYPE_CD == null  ? 'checked' : '' }> 택배상품
							</c:if>
							<c:if test="${list.ONLN_DELI_TYPE_CD eq '02'}">
								<input type="radio" class="onlnDeliTypeCd" name="onlnDeliTypeCd_${status.count}" value="02" ${list.ONLN_DELI_TYPE_CD eq '02' ? 'checked' : '' }> 설치상품
							</c:if>
							<!-- 2016-11-22 GWSG 최성웅 무형상품 추가 -->
							<c:if test="${list.ONLN_DELI_TYPE_CD eq '03'}">
								<input type="radio" class="onlnDeliTypeCd" name="onlnDeliTypeCd_${status.count}" value="03" ${list.ONLN_DELI_TYPE_CD eq '03' ? 'checked' : '' }> 무형상품
							</c:if>
							<!-- 2018-05  홍진옥 직배송상품 추가 -->
							<c:if test="${list.ONLN_DELI_TYPE_CD eq '04'}">
								<input type="radio" class="onlnDeliTypeCd" name="onlnDeliTypeCd_${status.count}" value="04" ${list.ONLN_DELI_TYPE_CD eq '04' ? 'checked' : '' }> 직배송상품
							</c:if>
							<br/>
							<input type="text" name="hodecoInvoiceNo" id="<c:out value="${list.ORDER_ID}"/>:<c:out value="${list.ORDER_ITEM_SEQ}"/>" value="<c:out value="${list.HODECO_INVOICE_NO}"/>" onKeyPress="if ((event.keyCode&lt;48)||(event.keyCode&gt;57)) event.returnValue=false;" maxlength="20">
							<br/> 
							<!-- 2016-11-22 GWSG 최성웅 무형상품 입력시 쿠폰 번호 입력 수정 -->
							<font id="guideText" name='guideText' color="red">${list.ONLN_DELI_TYPE_CD eq '01' || list.ONLN_DELI_TYPE_CD == null ? '*배송송장번호 입력' : (list.ONLN_DELI_TYPE_CD eq '02' ? '*설치 기사 이름 입력' : (list.ONLN_DELI_TYPE_CD eq '03' ? '*쿠폰번호 입력' : (list.ONLN_DELI_TYPE_CD eq '04' ? '*해피콜or실제배송시작일자 입력' : '')) )}</font>
							<br/>
							<input type="text" name="hodecoAddInvoiceNo" id="<c:out value="${list.ORDER_ID}"/>:<c:out value="${list.ORDER_ITEM_SEQ}"/>" value="<c:out value="${list.HODECO_ADD_INVOICE_NO}"/>" onKeyPress="if ((event.keyCode&lt;48)||(event.keyCode&gt;57)) event.returnValue=false;" maxlength="200" style="margin-top: 8px;">
						</td>
						<!-- ------------- -->
						<td style="background: ${divColor}" id="chk_${tr_cnt}">
							<c:choose>
							<c:when test="${(list.HODECO_INVOICE_NO!='' && list.VEN_DELI_STATUS_CD eq '05')|| list.ORD_STS_CD eq '20' || list.ORD_STS_CD eq '21'}">
								<input type="hidden" name="d_disabled_${tr_cnt}" id="d_disabled_${tr_cnt}" value="true"/>
							</c:when>
							<c:otherwise>
								<input type="hidden" name="d_disabled_${tr_cnt}" id="d_disabled_${tr_cnt}" value="false"/>
							</c:otherwise>
							</c:choose>
							<input type="checkbox" name="checkValue"  value="<c:out value="${list.ORDER_ID}"/>:<c:out value="${list.ORDER_ITEM_SEQ}"/>"<c:if test="${(list.HODECO_INVOICE_NO!='' && list.VEN_DELI_STATUS_CD eq '05')|| list.VEN_DELI_STATUS_CD eq '02' || list.VEN_DELI_STATUS_CD eq '07' || list.ORD_STS_CD eq '20' || list.ORD_STS_CD eq '21'}">disabled="disabled"</c:if>>
							<input type="hidden" name="d_order_id" value="<c:out value="${list.ORDER_ID}"/>">
							<input type="hidden" name="d_item_seq" value="<c:out value="${list.ORDER_ITEM_SEQ}"/>">
							<input type="hidden" name="delivery_id" value="<c:out value="${list.DELIVERY_ID}"/>">
							<input type="hidden" name="d_hodecoInvoiceNo" value="<c:out value="${list.HODECO_INVOICE_NO}"/> ">
						</td>
					</tr>
					<tr>
						<td style="background: ${divColor}"><c:out value="${list.VENDOR_NM}"/></td>
						<td style="background: ${divColor}"><c:out value="${list.ORD_STS_NM}"/></td>
						<td style="background: ${divColor}" colspan="2"><c:out value="${list.ITEM_OPTION}"/></td>
						<td style="background: ${divColor}"><c:out value="${list.PROD_STANDARD_NM}"/></td>
						<td style="background: ${divColor}"><fmt:formatNumber value="${list.ORD_QTY}" pattern="#,##0" /></td>
						<td style="background: ${divColor}"><c:out  value="${list.DELI_FNSH_DATE}"/></td>
						<td style="background: ${divColor}"><font color="blue"><c:out value="${list.SMS_SEND_YN}" /></font></td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
				<c:if test="${T_ORDER_ID != list.ORDER_ID || T_DELIVERY_ID != list.DELIVERY_ID}">
						<td style="background: ${divColor}" rowspan="<c:out value="${list.ROWSPAN2}"/>"><c:out value="${list.ORDER_ID}"/><br/><font color='red'><c:out value="${list.FIRST_ORDER_ID}"/></font></td>
				</c:if>
						<td style="background: ${divColor}"><c:out value="${list.PROD_CD}"/></td>
						<td style="background: ${divColor}"><c:out value="${list.MD_SRCMK_CD}"/></td>
				<c:if test="${(list.OX == 'O')}">
						<td style="background: ${divColor}" colspan="3"><c:out value="${list.PROD_NM}"/></td>
				</c:if>
				<c:if test="${(list.OX == 'X')}">
						<td style="background: ${divColor}" colspan="3"><font color='red'><c:out value="${list.PROD_NM}"/></font></td>
				</c:if>
						<td style="background: ${divColor}"><fmt:formatNumber value="${list.TOT_SELL_AMT}" pattern="#,##0" /></td>
						<td style="background: ${divColor}"><c:out value="${list.ENTP_DELI_PRAR_DY}"/></td>
				<c:if test="${T_ORDER_ID != list.ORDER_ID || T_DELIVERY_ID != list.DELIVERY_ID}">	
						<td style="background: ${divColor}" rowspan="<c:out value="${list.ROWSPAN2}"/>" id="${tr_cnt}">
							<input type="hidden" name="oriVenDeliStatusCd" value="${list.VEN_DELI_STATUS_CD}"/>
							<input type="hidden" name="oriDlayUnavlDtlReason" value="${list.DLAY_UNAVL_DTL_REASON }"/>
							<input type="hidden" name="oriSndPrarDy" value="${list.SND_PRAR_DY}"/>
							<input type="hidden" name="oriOrdStsCd" value="${list.ORD_STS_CD}"/>
							<input type="hidden" name="oriOrdDy" value="${list.ORD_DY}"/>
							<input type="hidden" name="oriBsketTypeCd" value="${list.BSKET_TYPE_CD}"/>
							<select name="venDeliStatusCd" class="input_text" <c:if test="${(list.HODECO_INVOICE_NO != '' && list.VEN_DELI_STATUS_CD eq '05') || (list.HODECO_INVOICE_NO != '' && list.VEN_DELI_STATUS_CD eq '09') || list.VEN_DELI_STATUS_CD eq '02' || list.ORD_STS_CD eq '20' || list.ORD_STS_CD eq '21'}">disabled="disabled"</c:if>>
							<c:forEach items="${DE014List}" var="codeList">
								<option value="${codeList.MINOR_CD}" <c:if test="${list.VEN_DELI_STATUS_CD eq codeList.MINOR_CD}">selected="selected"</c:if>>${codeList.CD_NM}</option>
							</c:forEach>
							</select>
						</td>
						<!-- 20160530 추가 -->
						<td style="background: ${divColor}" colspan="2" rowspan="<c:out value="${list.ROWSPAN2}"/>" id="chk2_${tr_cnt}">
							<c:if test="${ listCnt == list.ROWSPAN1}">
								<input type="text" name="sndPrarDy" id="" readonly style="width:40%; vertical-align: middle;" disabled="disabled" value="${list.SND_PRAR_DY}" />
								<span name="spanCal" onclick="javascript:sndPrarDyOpenCal(this);"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></span>
							</c:if>
							<c:if test="${ listCnt > 1 && listCnt != list.ROWSPAN1}">
								<input type="text" name="sndPrarDy" id="${ status.index }" readonly style="width:40%; vertical-align: middle;" disabled="disabled" value="${list.SND_PRAR_DY}" />
								<span name="spanCal" onclick="javascript:sndPrarDyOpenCal(this);"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></span>
							</c:if>
							&nbsp;&nbsp;
							<select name="dlayUnavlReasonCd" disabled="disabled" class="input_text">
								<option value="">선택</option>
								<c:forEach items="${DE017List}" var="codeList">
								<option value="${codeList.MINOR_CD}" <c:if test="${list.DLAY_UNAVL_REASON_CD eq codeList.MINOR_CD}">selected="selected"</c:if>>${codeList.CD_NM}</option>								
								</c:forEach>
							</select>
							<br/><br/>
							<input type="text" name="dlayUnavlDtlReason" onkeyup="check_inputByte(this,150)" style="width:70%;" disabled="disabled" value="<c:if test="${list.VEN_DELI_STATUS_CD == '02' || list.VEN_DELI_STATUS_CD == '07'}">${list.DLAY_UNAVL_DTL_REASON }</c:if>"/>
							<input type="hidden" name="DProdCd" value="<c:out value="${list.PROD_CD}"/>"/>
							<input type="hidden" name="DProdNm" value="<c:out value="${list.PROD_NM}"/>"/>
							<input type="hidden" name="DDeliveryId" value="<c:out value="${list.DELIVERY_ID}"/>"/>
							<input type="hidden" name="DDeliNo" value="${list.DELI_NO}"/>
							<input type="hidden" name="DOrderId" value="${list.ORDER_ID}"/>
							<input type="button" id= "dlayUnavlDtlReasonBtn" name="dlayUnavlDtlReasonBtn"  value=" <c:if test="${list.VEN_DELI_STATUS_CD == '02'}">조회 </c:if>"  onclick="<c:if test="${list.VEN_DELI_STATUS_CD == '02'}">  </c:if>"  <c:if test="${list.VEN_DELI_STATUS_CD != '02'}">style="display: none;"</c:if>  <c:if test="${list.VEN_DELI_STATUS_CD == '02'}">style="display:inline-block;"</c:if>/>
							<input type="hidden"  id="counselContent" name="counselContent"  value=""/>
							<input type="hidden"  id="mode" name="mode" value="<c:if test="${list.VEN_DELI_STATUS_CD == '02'}">search</c:if><c:if test="${list.VEN_DELI_STATUS_CD != '02'}">insert</c:if>" />
						</td>
				</c:if>
				<c:if test="${T_ORDER_ID != list.ORDER_ID || T_DELIVERY_ID != list.DELIVERY_ID}">
						<td style="background: ${divColor}" rowspan="<c:out value="${list.ROWSPAN2}"/>">
					<c:if test="${list.HODECO_INVOICE_NO != ''}">
						<c:if test="${list.HODECO_CD != '01' && list.HODECO_CD != '%' && list.VEN_DELI_STATUS_CD == '05'}">
							<a href="#" onClick="javascript:delivComSearch('<c:out value="${list.HODECO_CD}"/>','<c:out value="${list.HODECO_INVOICE_NO}"/>');">배송추적</a>
						</c:if>
					</c:if>
						</td>
				</c:if>
				<c:if test="${T_ORDER_ID != list.ORDER_ID || T_DELIVERY_ID != list.DELIVERY_ID}">
						<td style="background: ${divColor}" rowspan="<c:out value="${list.ROWSPAN2}"/>">
							<select name="hodecoCd" id="<c:out value="${list.ORDER_ID}"/>:<c:out value="${list.ORDER_ITEM_SEQ}"/>" class="input_text" style="width:90%;" >
								<option value="%" <c:if test="${'%' eq codeList.MINOR_CD}">selected="selected"</c:if>>미지정</option>
								<c:forEach items="${DE011List}" var="codeList">
								<option value="${codeList.MINOR_CD}" <c:if test="${list.HODECO_CD eq codeList.MINOR_CD}">selected="selected"</c:if>>${codeList.CD_NM}</option>
								</c:forEach>
							</select>
						</td>
						<!-- 20160530 수정 -->
						<td style="background: ${divColor}" id="HonlnDeliTypeCd" name="HonlnDeliTypeCd" rowspan="<c:out value="${list.ROWSPAN2}"/>">
							<!-- 2016-11-30 최성웅 해당상품의 배송타입만 노출 -->
							<c:if test="${list.ONLN_DELI_TYPE_CD eq '01'}">	
								<input type="radio" class="onlnDeliTypeCd" name="onlnDeliTypeCd_${status.count}" value="01" ${list.ONLN_DELI_TYPE_CD eq '01' || list.ONLN_DELI_TYPE_CD == null  ? 'checked' : '' }> 택배상품
							</c:if>
							<c:if test="${list.ONLN_DELI_TYPE_CD eq '02'}">	
								<input type="radio" class="onlnDeliTypeCd" name="onlnDeliTypeCd_${status.count}" value="02" ${list.ONLN_DELI_TYPE_CD eq '02' ? 'checked' : '' }> 설치상품
							</c:if>
							<!-- 2016-11-22 GWSG 최성웅 무형상품 추가 -->
							<c:if test="${list.ONLN_DELI_TYPE_CD eq '03'}">	
								<input type="radio" class="onlnDeliTypeCd" name="onlnDeliTypeCd_${status.count}" value="03" ${list.ONLN_DELI_TYPE_CD eq '03' ? 'checked' : '' }> 무형상품
							</c:if>
							<!-- 2018-05  홍진옥 직배송상품 추가 -->
							<c:if test="${list.ONLN_DELI_TYPE_CD eq '04'}">
								<input type="radio" class="onlnDeliTypeCd" name="onlnDeliTypeCd_${status.count}" value="04" ${list.ONLN_DELI_TYPE_CD eq '04' ? 'checked' : '' }> 직배송상품
							</c:if>
							<br/>
							<input type="text" name="hodecoInvoiceNo" id="<c:out value="${list.ORDER_ID}"/>:<c:out value="${list.ORDER_ITEM_SEQ}"/>" value="<c:out value="${list.HODECO_INVOICE_NO}"/>" onKeyPress="if ((event.keyCode&lt;48)||(event.keyCode&gt;57)) event.returnValue=false;" maxlength="20" >
							<br/>
							<!-- 2016-11-22 GWSG 최성웅 무형상품 입력시 쿠폰 번호 입력 수정 -->
							<font id="guideText" name='guideText' color="red">${list.ONLN_DELI_TYPE_CD eq '01' || list.ONLN_DELI_TYPE_CD == null ? '*배송송장번호 입력' : (list.ONLN_DELI_TYPE_CD eq '02' ? '*설치 기사 이름 입력' : (list.ONLN_DELI_TYPE_CD eq '03' ? '*쿠폰번호 입력' : (list.ONLN_DELI_TYPE_CD eq '04' ? '*해피콜or실제배송시작일자 입력' : '')) )}</font>
							<br/>
							<input type="text" name="hodecoAddInvoiceNo" id="<c:out value="${list.ORDER_ID}"/>:<c:out value="${list.ORDER_ITEM_SEQ}"/>" value="<c:out value="${list.HODECO_ADD_INVOICE_NO}"/>" onKeyPress="if ((event.keyCode&lt;48)||(event.keyCode&gt;57)) event.returnValue=false;" maxlength="200" style="margin-top: 8px;">
						</td>
						<td style="background: ${divColor}" id="chk_${tr_cnt}" rowspan="<c:out value="${list.ROWSPAN2}"/>">
							<c:choose>
							<c:when test="${(list.HODECO_INVOICE_NO!='' && list.VEN_DELI_STATUS_CD eq '05')|| list.ORD_STS_CD eq '20' || list.ORD_STS_CD eq '21'}">
								<input type="hidden" name="d_disabled_${tr_cnt}" id="d_disabled_${tr_cnt}" value="true"/>							
							</c:when>
							<c:otherwise>
								<input type="hidden" name="d_disabled_${tr_cnt}" id="d_disabled_${tr_cnt}" value="false"/>
							</c:otherwise>
							</c:choose>
							<input type="checkbox" name="checkValue"  value="<c:out value="${list.ORDER_ID}"/>:<c:out value="${list.ORDER_ITEM_SEQ}"/>"<c:if test="${(list.HODECO_INVOICE_NO!='' && list.VEN_DELI_STATUS_CD eq '05')|| list.VEN_DELI_STATUS_CD eq '02' || list.VEN_DELI_STATUS_CD eq '07' || list.ORD_STS_CD eq '20' || list.ORD_STS_CD eq '21'}">disabled="disabled"</c:if>>
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
						<td style="background: ${divColor}"><c:out value="${list.VENDOR_NM}"/></td>
						<td style="background: ${divColor}"><c:out value="${list.ORD_STS_NM}"/></td>
						<td style="background: ${divColor}" colspan="2"><c:out value="${list.ITEM_OPTION}"/></td>
						<td style="background: ${divColor}" ><c:out value="${list.PROD_STANDARD_NM}"/></td>
						<td style="background: ${divColor}"><fmt:formatNumber value="${list.ORD_QTY}" pattern="#,##0" /></td>
						<td style="background: ${divColor}"><c:out  value="${list.DELI_FNSH_DATE}"/></td>
						<%-- <td><font color="blue"><c:out value="${list.SMS_SEND_YN}" /></font></td> --%>
					</tr>
				</c:otherwise>
			</c:choose>
			<c:set var="T_ORDER_ID" value="${list.ORDER_ID}"/>
			<c:set var="T_DELIVERY_ID" value="${list.DELIVERY_ID}"/>
		</c:forEach>
						<tr class="r1">
							<td height="1" colspan="15" style="color: blue;" ></td>
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