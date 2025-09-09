
<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>
<%--
	Page Name 	: SRMRST0020.jsp
	Description : 입점상담 결과확인 > 진행현황
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.06		AN TAE KYUNG	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title><spring:message code='text.srm.field.resultConsult1' /></title><%--입점상담 결과 --%>

<style type="text/css">
.sc_orange {background:#f3753a;}
</style>

<script language="JavaScript">
	/* 화면 초기화 */
	$(document).ready(function() {

		var vMobilePhone = '<c:out value="${resultVO.vMobilePhone }" />';	// 휴대전화
		var irsNo = '<c:out value="${resultVO.irsNo }" />';					// 사업자등록번호

		/* 전화번호,사업자번호 형식으로 바꾸기(국내업체일 경우에만) */
		if ('<c:out value="${srmLoginSession.shipperType}"/>' == '1') {
			vMobilePhone = phone_format(vMobilePhone);
			irsNo = irsNo_format(irsNo);
		}

		$("#vMobilePhone").html(vMobilePhone);
		$("#irsNo").html(irsNo);

		/* 상담신청 리스트 조회 */
		goPage("1");
	});

	/* 전화번호 형식으로 바꾸기 */
	function phone_format(num){
		return num.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
	}

	/* 사업자번호 형식으로 바꾸기 */
	function irsNo_format(num){
		return num.replace(/([0-9]{3})([0-9]{2})([0-9]{5})/,"$1-$2-$3");
	}

	/* 상담신청 리스트 조회 */
	function goPage(pageIndex) {
		var searchInfo = {};

		searchInfo["pageIndex"] = pageIndex;

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/srm/selectStatusList.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(json) {
				_setTbodyMasterValue(json);
			}
		});
	}

	/* List Set */
	function _setTbodyMasterValue(json){
		var data = json.list;
		setTbodyInit("dataListbody");	// dataList 초기화
		if (data.length > 0) {
			$.each(data, function(i) {
				data[i].rnum = i+1;

				if (i > 0) {
					data[i].preSeq = data[i - 1].seq;
				} else {
					data[i].preSeq = "";
				}
            });

			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
			$("#paging").html(json.contents);
		} else {
			setTbodyNoResult("dataListbody", 12);
			$("#paging").html();
		}
	}

	/* 선택한 입점신청 내역 삭제 */
	function fnDelConfirm() {
		var chkIdx = 0;
		var seqList = new Array();
		var len = $("#MyForm input[name='selChk']").length;
		for (var i = 0; i < len; i++) {
			if ($("input[name='selChk']").eq(i).is(":checked")) {
				var seq = $("#MyForm input[name='seq']").eq(i).val();
				seqList.push(seq);
				chkIdx++;
			}
		}

		if (chkIdx == 0) {
			alert("<spring:message code='msg.srm.alert.reqSelChk' />");<%--선택된 상담신청 항목이 없습니다. --%>
			return;
		}

		if (!confirm("<spring:message code='msg.srm.alert.confirmDelete' />")) { <%--삭제하시겠습니까? --%>
            return;
        }

		var searchInfo = {};

		searchInfo["seqList"] 			= seqList;	// seq
		//console.log(searchInfo);
		//return;

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'text',
			async : false,
			url : '<c:url value="/edi/srm/deleteCounselInfo.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				if (data == "logOut") {
					alert("<spring:message code='msg.srm.alert.logOut' />");<%--로그아웃 되었습니다. --%>
					location.href = "<c:url value='/edi/srm/SRMRST0020.do' />";
				} else {
					alert("<spring:message code='msg.srm.alert.deleteOk' />");<%--삭제 되었습니다. --%>
					goPage();						// 상담신청 리스트 조회
				}

			},
		});
	}

	/* MD거절 사유 팝업 */
	function RejectReasonInfo(houseCode, sellerCode, seq) {
		$("#frmHidden input[name='seq']").val(seq);
		var cw = 720;
		var ch = 500;
		var url = "<c:url value='/edi/srm/selectRejectReasonInfoPopup.do' />";
		PopupWindow(cw, ch, url);
	}

	/* 상담조회 팝업 */
	function CounselInfo(houseCode, sellerCode, seq) {
		$("#frmHidden input[name='seq']").val(seq);
		var cw = 720;
		var ch = 670;
		var url = "<c:url value='/edi/srm/selectCompCounselInfoPopup.do' />";
		PopupWindow(cw, ch, url);
	}

	/* 품평회 팝업 */
	function FairInfo(houseCode, sellerCode, seq) {
		$("#frmHidden input[name='seq']").val(seq);
		var cw = 720;
		var ch = 670;
		var url = "<c:url value='/edi/srm/selectCompFairInfoPopup.do' />";
		PopupWindow(cw, ch, url);
	}

	/* 이행보증증권 팝업 */
	function InsInfo(houseCode, sellerCode, seq) {
		$("#frmHidden input[name='seq']").val(seq);
		var cw = 720;
		var ch = 670;
		var url = "<c:url value='/edi/srm/selectCompInsInfoPopup.do' />";
		PopupWindow(cw, ch, url);
	}

	/*시행조치 팝업*/
	function doImpPopup(evNo, seq) {
		$("#frmHidden input[name='evNo']").val(evNo);
		$("#frmHidden input[name='seq']").val(seq);
		var cw = 720;
		var ch = 730;
		var url = "<c:url value='/edi/srm/selectCompSiteVisitCover3Popup.do' />";
		var sw = screen.availWidth;
		var sh = screen.availHeight;
		var px = Math.round((sw-cw)/2);
		var py = Math.round((sh-ch)/2);

		var win = window.open("", "popup", "left=" + px + ",top=" + py + ",width=" + cw + ",height=" + ch + ",toolbar=no,menubar=no,status=no,resizable=no,scrollbars=yes");

		$("#frmHidden").attr("action", url);
		$("#frmHidden").attr("target", "popup");
		$("#frmHidden").submit();
		$("#frmHidden").attr("target", "_self");

		//윈도우 닫기 이벤트 확인
//		var interval = window.setInterval(function() {
//			try {
//				if (win == null || win.closed) {
//					window.clearInterval(interval);
//					selectData(); //callback
//				}
//			}
//			catch (e) {
//			}
//		}, 1000);
	}

	//품질경영평가 기관 정보 팝업
	function doEvlCompPopup(houseCode, visitSeq, evalNoResult, evalSellerCode){
		$("#frmHidden input[name='seq']").val(visitSeq);
		$("#frmHidden input[name='evNo']").val(evalNoResult);
		$("#frmHidden input[name='evalSellerCode']").val(evalSellerCode);
		var cw = 700;
		var ch = 400;
		var url = "<c:url value='/edi/srm/selectCompSiteVisitCompPopup.do' />";
		PopupWindow(cw, ch, url);
	}
	
	//업체가 품질경영평가 기관을 선택하기 위한 팝업
	function doSelectCompListPopup(houseCode, Seq, sellerCode){
		$("#hiddenForm input[name='reqSeq']").val(Seq);
		$("#hiddenForm input[name='sellerCode']").val(sellerCode);	
		$("#hiddenForm input[name='inOutKind']").val('02');		
		var cw = 700;
		var ch = 400;
		var url = "<c:url value='/edi/srm/selectCompSiteVisitCompListPopup.do' />";
				
		var sw = screen.availWidth;
		var sh = screen.availHeight;
		var px = Math.round((sw-cw)/2);
		var py = Math.round((sh-ch)/2);

		window.open("", "popup", "left=" + px + ",top=" + py + ",width=" + cw + ",height=" + ch + ",toolbar=no,menubar=no,status=no,resizable=no,scrollbars=no");

		$("#hiddenForm").attr("action", url);
		$("#hiddenForm").attr("target", "popup");
		$("#hiddenForm").submit();
		$("#hiddenForm").attr("target", "_self");
	}
	

	/* 팝업창 */
 	function PopupWindow(cw, ch, url) {
		var sw = screen.availWidth;
		var sh = screen.availHeight;
		var px = Math.round((sw-cw)/2);
		var py = Math.round((sh-ch)/2);

		window.open("", "popup", "left=" + px + ",top=" + py + ",width=" + cw + ",height=" + ch + ",toolbar=no,menubar=no,status=no,resizable=no,scrollbars=no");

		$("#frmHidden").attr("action", url);
		$("#frmHidden").attr("target", "popup");
		$("#frmHidden").submit();
		$("#frmHidden").attr("target", "_self");
	}

 	/* 업체 정보확인 */
	function hiddenCompInfo(houseCode, sellerCode, reqSeq) {
		$("#hiddenForm input[name='houseCode']").val(houseCode);
		$("#hiddenForm input[name='sellerCode']").val(sellerCode);
		$("#hiddenForm input[name='reqSeq']").val(reqSeq);
		var cw = 800;
		var ch = 700;
		var sw = screen.availWidth;
		var sh = screen.availHeight;
		var px = Math.round((sw-cw)/2);
		var py = Math.round((sh-ch)/2);

		window.open("", "popup", "left=" + px + ",top=" + py + ",width=" + cw + ",height=" + ch + ",toolbar=no,menubar=no,status=no,resizable=no,scrollbars=yes");
		<c:if test="${srmLoginSession.shipperType eq '1'}">
			$("#hiddenForm").attr("action", "<c:url value='/edi/srm/selectHiddenCompInfoPopup.do' />");
		</c:if>
		<c:if test="${srmLoginSession.shipperType eq '2'}">
			$("#hiddenForm").attr("action", "<c:url value='/edi/srm/selectGlobalHiddenCompInfoPopup.do' />");
		</c:if>

		$("#hiddenForm").attr("target", "popup");
		$("#hiddenForm").submit();
		$("#hiddenForm").attr("target", "_self");
	}


	//취소(임시저장으로 되돌림)
	function doCancel(){
		var chkIdx = 0;
		var seqList = new Array();
		var len = $("#MyForm input[name='selChk']").length;
		for (var i = 0; i < len; i++) {
			if ($("input[name='selChk']").eq(i).is(":checked")) {
				var seq = $("#MyForm input[name='seq']").eq(i).val();
				seqList.push(seq);
				chkIdx++;
			}
		}

		if (chkIdx == 0) {
			alert("<spring:message code='msg.srm.alert.reqSelChk' />");<%--선택된 상담신청 항목이 없습니다. --%>
			return;
		}

		if (!confirm("<spring:message code='msg.srm.alert.srmrst0020.cancel' />")) { <%--선택한 신청건은 임시저장 상태로 돌아갑니다.\\n취소하시겠습니까? --%>
			return;
		}

		var searchInfo = {};

		searchInfo["seqList"] 			= seqList;	// seq

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'text',
			async : false,
			url : '<c:url value="/edi/srm/updateCounselInfoCancel.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				alert("<spring:message code='msg.srm.alert.vendorConsultReqCancelConplete' />");<%--입점상담신청 취소가 정상적으로 처리 되었습니다. --%>
				goPage("1");						// 상담신청 리스트 조회
			},
		});
	}

</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td style="text-align: center;">
			{%if status == "A01"%}
				<input type="checkbox" id="selChk" name="selChk" />
			{%else%}
				<input type="checkbox" disabled="disabled" id="selChk" name="selChk" />
			{%/if%}
		</td>
		<td style="text-align: center;"><c:out value="\${channelCodeNm}"/></td>

		<td class="cell_yellow" style="text-align: center; cursor: pointer;" onClick="hiddenCompInfo('<c:out value='\${houseCode}'/>','<c:out value='\${sellerCode}'/>','<c:out value='\${seq}'/>')" >
			<c:out value="\${catLv1CodeNm}"/>
		</td>

		<td style="text-align: center;"><c:out value="\${requestDate}"/></td>
		<td style="text-align: center;"><c:out value="\${receiptDate}"/></td>
		<td style="text-align: center;"><c:out value="\${userName}"/></td>
		<td style="text-align: center;"><c:out value="\${ownerMd}"/></td>

		<%---------- MD접수 ----------%>
		{%if processStatus != null%}
			<td class="click" style="text-align:center;">
				{%if processStatus == 'B01'%}	<%-- 해당MD접수 --%>
					<div class="status_circle sc_yellow" data-tooltip-text="<c:out value="\${processStatusNm}"/>" />
				{%elif processStatus == 'B03'%}	<%-- MD거절 --%>
					<div class="status_circle sc_pink" data-tooltip-text="<c:out value="\${processStatusNm}"/>" onClick="RejectReasonInfo('<c:out value='\${houseCode}'/>','<c:out value='\${sellerCode}'/>','<c:out value='\${seq}'/>')" />
				{%else%}
					<div class="status_circle sc_green" data-tooltip-text="<spring:message code="text.srm.tooltip.md.finish"/>" />
				{%/if%}
			</td>
		{%else%}
			<td class="click" style="text-align: center;"></td>
		{%/if%}

		{%if channelCode == '99' %}
			<td class="status_not"></td>
		{%elif evalAStatus != null%}
			<td class="click" style="text-align:center;">
				{%if evalAStatus == 'C01'%}		<%-- 상담요청 --%>
					<div class="status_circle sc_gray" data-tooltip-text="<c:out value="\${evalAStatusNm}"/>" onClick="CounselInfo('<c:out value='\${houseCode}'/>','<c:out value='\${sellerCode}'/>','<c:out value='\${seq}'/>')" />
				{%elif evalAStatus == 'C02'%}	<%-- 입점거절 --%>
					<div class="status_circle sc_pink" data-tooltip-text="<c:out value="\${evalAStatusNm}"/>" onClick="CounselInfo('<c:out value='\${houseCode}'/>','<c:out value='\${sellerCode}'/>','<c:out value='\${seq}'/>')" />
				{%elif evalAStatus == 'C03'%}	<%-- 상담완료 --%>
					<div class="status_circle sc_green" data-tooltip-text="<c:out value="\${evalAStatusNm}"/>" onClick="CounselInfo('<c:out value='\${houseCode}'/>','<c:out value='\${sellerCode}'/>','<c:out value='\${seq}'/>')" />
				{%/if%}
			</td>

			<%-- <td style="text-align: center; cursor: pointer;" onClick="CounselInfo('<c:out value='\${houseCode}'/>','<c:out value='\${sellerCode}'/>','<c:out value='\${seq}'/>')">
				<div class="p_circle circle_orange" data-tooltip-text="<spring:message code="text.srm.search.field.status.option1"/>" />
			</td> --%>
		{%else%}
			<td class="click" style="text-align: center;"></td>
		{%/if%}

		{%if channelCode == '06' || channelCode == '99' %}
			<td class="status_not"></td>
		{%elif evalBStatus != null%}
			{%if evalBStatus != 'D01' && evalBStatus != 'D02' && evalBStatus != 'D03' %}
				<td class="click" style="text-align:center;">
					{%if evalBStatus == 'D04'%}		<%-- 품평회요청 --%>
						<div class="status_circle sc_gray" data-tooltip-text="<c:out value="\${evalBStatusNm}"/>" onClick="FairInfo('<c:out value='\${houseCode}'/>','<c:out value='\${sellerCode}'/>','<c:out value='\${seq}'/>')" />
					{%elif evalBStatus == 'D05'%}	<%-- 입점거절(품평회) --%>
						<div class="status_circle sc_pink" data-tooltip-text="<c:out value="\${evalBStatusNm}"/>" onClick="FairInfo('<c:out value='\${houseCode}'/>','<c:out value='\${sellerCode}'/>','<c:out value='\${seq}'/>')" />
					{%elif evalBStatus == 'D06'%}	<%-- 품평회완료 --%>
						<div class="status_circle sc_green" data-tooltip-text="<c:out value="\${evalBStatusNm}"/>" onClick="FairInfo('<c:out value='\${houseCode}'/>','<c:out value='\${sellerCode}'/>','<c:out value='\${seq}'/>')" />
					{%/if%}
				</td>
				<%-- <td style="text-align: center; cursor: pointer;" onClick="FairInfo('<c:out value='\${houseCode}'/>','<c:out value='\${sellerCode}'/>','<c:out value='\${seq}'/>')">
					<c:out value="\${evalBStatusNm}"/>
				</td> --%>
			{%else%}
				<td class="click" style="text-align: center;">
					{%if evalBStatus == 'D01'%}		<%-- 품평회 SKIP 요청 --%>
						<div class="status_circle sc_blue" data-tooltip-text="<c:out value="\${evalBStatusNm}"/>" />
					{%elif evalBStatus == 'D02'%}	<%-- 품평회 SKIP 반려 --%>
						<div class="status_circle sc_pink" data-tooltip-text="<c:out value="\${evalBStatusNm}"/>" />
					{%elif evalBStatus == 'D03'%}	<%-- 품평회 SKIP 확정 --%>
						<div class="status_circle sc_green" data-tooltip-text="<c:out value="\${evalBStatusNm}"/>" />
					{%/if%}
				</td>
			{%/if%}
		{%else%}
			<td class="click" style="text-align: center;"></td>
		{%/if%}

		<%-- {%if channelCode == '01' || channelCode == '02' || channelCode == '99' %}
			<td class="status_not"></td>
		{%elif evalCStatus != null%}
			<td class="click" style="text-align:center;">
				{%if evalCStatus == 'F01'%}		이행보증보험가입요청
					<div class="status_circle sc_gray" data-tooltip-text="<c:out value="\${evalCStatusNm}"/>" onClick="InsInfo('<c:out value='\${houseCode}'/>','<c:out value='\${sellerCode}'/>','<c:out value='\${seq}'/>')" />
				{%elif evalCStatus == 'F02'%}	이행보증보험가입등록
					<div class="status_circle sc_yellow" data-tooltip-text="<c:out value="\${evalCStatusNm}"/>" onClick="InsInfo('<c:out value='\${houseCode}'/>','<c:out value='\${sellerCode}'/>','<c:out value='\${seq}'/>')" />
				{%elif evalCStatus == 'F03' || evalCStatus == 'F04'%}	이행보증보험확인완료(경리) || 이행보증보험확인완료(MD)
					<div class="status_circle sc_green" data-tooltip-text="<c:out value="\${evalCStatusNm}"/>" onClick="InsInfo('<c:out value='\${houseCode}'/>','<c:out value='\${sellerCode}'/>','<c:out value='\${seq}'/>')" />
				{%/if%}
			</td>

			<td style="text-align: center; cursor: pointer;" onClick="InsInfo('<c:out value='\${houseCode}'/>','<c:out value='\${sellerCode}'/>','<c:out value='\${seq}'/>')">
				<c:out value="\${evalCStatusNm}"/>
			</td>
		{%else%}
			<td class="click" style="text-align: center;"></td>
		{%/if%}
	 --%>

		{%if channelCode =='06' || channelCode == '99' %}
			<td class="status_not"></td>
		{%elif evalDStatus != null%}
			{%if evalDStatus != 'G01' && evalDStatus != 'G02' && evalDStatus != 'G03' %}
				<td class="click" style="text-align: center;">
					{%if evalDStatus == 'G04'%}		<%-- 품질경영평가요청 --%>
						<div class="status_circle sc_gray" data-tooltip-text="<c:out value="\${evalDStatusNm}"/>" onClick="javascript:doEvlCompPopup('<c:out value='\${houseCode}'/>','<c:out value='\${visitSeq}'/>','<c:out value='\${evalNoResult}'/>','<c:out value='\${evalSellerCode}'/>')" />
					{%elif evalDStatus == 'G05'%}	<%-- 품질경영평가재요청 --%>
						<div class="status_circle sc_darkgray" data-tooltip-text="<c:out value="\${evalDStatusNm}"/>" onClick="javascript:doEvlCompPopup('<c:out value='\${houseCode}'/>','<c:out value='\${visitSeq}'/>','<c:out value='\${evalNoResult}'/>','<c:out value='\${evalSellerCode}'/>')" />
					{%elif evalDStatus == 'G06'%}	<%-- 품질경영평가요청확인 --%>
						<div class="status_circle sc_yellow" data-tooltip-text="<c:out value="\${evalDStatusNm}"/>" onClick="javascript:doEvlCompPopup('<c:out value='\${houseCode}'/>','<c:out value='\${visitSeq}'/>','<c:out value='\${evalNoResult}'/>','<c:out value='\${evalSellerCode}'/>')" />
					{%elif evalDStatus == 'G07'%}	<%-- 기관평가완료 --%>
						<div class="status_circle sc_yellow" data-tooltip-text="<c:out value="\${evalDStatusNm}"/>" onClick="javascript:doEvlCompPopup('<c:out value='\${houseCode}'/>','<c:out value='\${visitSeq}'/>','<c:out value='\${evalNoResult}'/>','<c:out value='\${evalSellerCode}'/>')" />
					{%elif evalDStatus == 'G08'%}	<%-- 입점거절 --%>
						<div class="status_circle sc_pink" data-tooltip-text="<c:out value="\${evalDStatusNm}"/>" onClick="javascript:doEvlCompPopup('<c:out value='\${houseCode}'/>','<c:out value='\${visitSeq}'/>','<c:out value='\${evalNoResult}'/>','<c:out value='\${evalSellerCode}'/>')" />
					{%elif evalDStatus == 'G09'%}	<%-- 품질경영평가완료 --%>
						<div class="status_circle sc_green" data-tooltip-text="<c:out value="\${evalDStatusNm}"/>" onClick="javascript:doEvlCompPopup('<c:out value='\${houseCode}'/>','<c:out value='\${visitSeq}'/>','<c:out value='\${evalNoResult}'/>','<c:out value='\${evalSellerCode}'/>')" />
					{%elif evalDStatus == 'G11'%}	<%-- 평가업체 확인 요청 --%>
						<div class="status_circle sc_orange" data-tooltip-text="<c:out value="\${evalDStatusNm}"/>" onClick="javascript:doSelectCompListPopup('<c:out value='\${houseCode}'/>','<c:out value='\${seq}'/>','<c:out value='\${sellerCode}'/>','G11')" />
					{%elif evalDStatus == 'G12'%}	<%-- 평가업체 확인  --%>
						<div class="status_circle sc_orange" data-tooltip-text="<c:out value="\${evalDStatusNm}"/>" onClick="javascript:doSelectCompListPopup('<c:out value='\${houseCode}'/>','<c:out value='\${seq}'/>','<c:out value='\${sellerCode}'/>', 'G12')" />				
									
					{%/if%}
					
				</td>

				<%-- <td style="text-align: center; cursor: pointer;" onClick="javascript:doEvlCompPopup('<c:out value='\${houseCode}'/>','<c:out value='\${visitSeq}'/>','<c:out value='\${evalNoResult}'/>','<c:out value='\${evalSellerCode}'/>')">
					<c:out value="\${evalDStatusNm}"/>
				</td> --%>
			{%else%}
				<td class="click" style="text-align: center;">
					{%if evalDStatus == 'G01'%}		<%-- 품질경영평가 SKIP 요청 --%>
						<div class="status_circle sc_blue" data-tooltip-text="<c:out value="\${evalDStatusNm}"/>" />
					{%elif evalDStatus == 'G02'%}	<%-- 품질경영평가 SKIP 반려 --%>
						<div class="status_circle sc_pinkn" data-tooltip-text="<c:out value="\${evalDStatusNm}"/>" />
					{%elif evalDStatus == 'G03'%}	<%-- 품질경영평가 SKIP 확정 --%>
						<div class="status_circle sc_green" data-tooltip-text="<c:out value="\${evalDStatusNm}"/>" />
					{%/if%}
				</td>

				<%-- <td style="text-align: center;"><c:out value="\${evalDStatusNm}"/></td> --%>
			{%/if%}
		{%else%}
			<td class="click" style="text-align: center;"></td>
		{%/if%}

		{%if totImpCnt != 0 && (evalDStatus == 'G05' || evalDStatus == 'G07' || evalDStatus == 'G09')%}
			<td class="click" style="text-align: center; cursor: pointer;" onClick="javascript:doImpPopup('<c:out value='\${evalNoResult}'/>','<c:out value='\${visitSeq}'/>')">
				(<c:out value="\${impCnt}"/>/<c:out value="\${totImpCnt}"/>)
			</td>
		{%else%}
			<td class="click" style="text-align: center;"></td>
		{%/if%}

		{%if evalDStatus == 'G05'%}		<%-- 품질경영평가재요청 --%>
			<td style="text-align: center;">
				<c:out value="\${evalDStatusNm}" />
			</td>
		{%else%}
			{%if processStatus == 'C02' || processStatus == 'E01' || processStatus == 'D05' || processStatus == 'H04'%}
				<td style="color :red; text-align: center;">
					<c:out value="\${processStatusNm}" />
				</td>
			{%elif processStatus == 'Z01'%}
				<td style="color :blue; text-align: center;">
					<c:out value="\${processStatusNm}" />
				</td>
			{%else%}
				<td style="text-align: center;">
					<c:out value="\${processStatusNm}" />
				</td>
			{%/if%}
		{%/if%}
		<input type="hidden" id="seq" name="seq" value="<c:out value="\${seq}" />" />
	</tr>
</script>

</head>


<body>
	<!--Container-->
	<div id="container">
		<!-- Sub Wrap -->
		<div class="inner sub_wrap">
			<!-- 서브상단 -->
			<div class="sub_top">
				<h2 class="tit_page"><spring:message code='text.srm.field.resultConsult1' /></h2><%--입점상담 결과 --%>
				<p class="page_path"><%-- <a href="<c:url value="/edi/srm/SRMJONMain.do" />">HOME</a> --%>HOME <span><spring:message code='text.srm.field.resultConsult1' /></span><%--입정상담 결과 --%> <span><spring:message code='text.srm.field.resultConsult2' /></span></p><%--입정상담 결과 확인 --%>
			</div><!-- END 서브상단 -->

			<h3 class="tit_star"><spring:message code='text.srm.field.resultConsult2' /></h3><%--입점상담 결과 확인 --%>

			<table class="tbl_st1">
				<colgroup>
					<col style="width:15%;">
					<col style="width:35%;">
					<col style="width:15%;">
					<col style="width:35%;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th><spring:message code='text.srm.field.sellerNameLoc' /></th><%--사업자명 --%>
						<td><c:out value="${resultVO.sellerNameLoc}"/></td>
						<th><spring:message code='text.srm.field.irsNo' /></th><%--사업자등록번호 --%>
						<td id="irsNo"></td>
					</tr>
					<tr>
						<th><spring:message code='text.srm.field.sellerCeoName' /></th><%--대표자명 --%>
						<td><c:out value="${resultVO.sellerCeoName }"/></td>
						<th><spring:message code='text.srm.field.vMainName' /></th><%--담당자명 --%>
						<td><c:out value="${resultVO.vMainName }"/></td>
					</tr>
					<tr>
						<c:if test="${srmLoginSession.shipperType eq '1'}">
						<th><spring:message code='text.srm.field.vMobilePhone' /></th><%--전화번호 --%>
						<td id="vMobilePhone"></td>
						</c:if>
						<th><spring:message code='text.srm.field.vEmail' /></th><%--E-Mail --%>
						<td <c:if test="${srmLoginSession.shipperType eq '2'}">colspan="3" </c:if>><c:out value="${resultVO.vEmail }"/></td>
					</tr>
					<tr>
						<th><spring:message code='text.srm.field.address' /></th><%--주소 --%>
						<td colspan="3">
							<c:if test="${srmLoginSession.shipperType eq '1'}">
								(<c:out value="${resultVO.zipcode }"/>)
								<c:out value="${resultVO.address1 }"/>
								<c:out value="${resultVO.address2 }"/>
							</c:if>
							<c:if test="${srmLoginSession.shipperType eq '2'}">
								<c:out value="${resultVO.address1 }"/>
							</c:if>
						</td>

					</tr>
					<c:if test="${srmLoginSession.shipperType eq '1'}">
					<tr>
						<th><spring:message code='text.srm.field.industryType' /></th><%--업종 --%>
						<td><c:out value="${resultVO.industryType }"/></td>
						<th><spring:message code='text.srm.field.businessType' /></th><%--업태 --%>
						<td><c:out value="${resultVO.businessType }"/></td>
					</tr>
					</c:if>
				</tbody>
			</table>

			<!-- 상단 버튼이 있을 경우 타이틀 -->
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code='text.srm.field.resultConsult3' /> <em>(<spring:message code="text.srm.field.resultConsult4" />)</em></h3><%--입점상담 내역별 상태 조회 --%>
				<div class="right_btns">
					<button type="button" class="btn_normal" onclick="goPage(1);"><spring:message code='button.srm.search2'/></button><%--조회 --%>
					<button type="button" class="btn_normal btn_blue" onclick="doCancel();"><spring:message code='button.srm.srmrst0020.cancel'/></button><%--신청취소 --%>
					<button type="button" class="btn_normal btn_red" onclick="fnDelConfirm();"><spring:message code='button.srm.srmrst0020.delete'/></button><%--신청삭제 --%>
				</div>
			</div><!-- END 상단 버튼이 있을 경우 타이틀 -->

			<!-- 테이블이 넓을 경우 스크롤 됨. / 테이블 너비는 inline style로 조절 / 기본너비(css) 100%로 설정되어있음. -->
			<div class="tbl_scroll">
			<form name="MyForm"  id="MyForm" method="post">
				<table class="tbl_st1" style="width:1400px;">
					<colgroup>
						<col style="width:35px;">
						<col style="width:120px;">
						<col style="width:100px;">
						<col style="width:90px;">
						<col style="width:90px;">
						<col style="width:70px;">
						<col style="width:70px;">
						<col style="width:85px;">
						<col style="width:85px;">
						<col style="width:85px;">
						<col style="width:85px;">
						<col style="width:85px;">
						<!-- <col style="width:60px;"> -->
						<col style="width:140px;">
					</colgroup>
					<thead>
						<tr>
							<th rowspan="2"><spring:message code='text.srm.field.select' /></th><%--선택 --%>
							<th rowspan="2"><spring:message code='text.srm.field.channelCode' /></th><%--채널 --%>
							<th rowspan="2"><spring:message code='text.srm.field.catLv1Code2' /></th><%--분류 --%>
							<th rowspan="2"><spring:message code='text.srm.field.receiptDate2' /></th><%--상담신청일 --%>
							<th rowspan="2"><spring:message code='text.srm.field.requestDate' /></th><%--접수일자 --%>
							<th rowspan="2"><spring:message code='text.srm.field.vMainName' /></th><%--담당자명 --%>
							<th rowspan="2"><spring:message code='text.srm.field.ownerMd2' /></th><%--담당MD --%>
							<th colspan="4"><spring:message code='text.srm.field.status' /></th><%--진행상태 --%>
							<th rowspan="2"><spring:message code='text.srm.field.imp' /></th><%--시정조치 --%>
							<th rowspan="2"><spring:message code='text.srm.field.processStatusNm' /></th><%--최종결과 --%>
						</tr>
						<tr>
							<th><spring:message code='text.srm.field.rejectReasonNm' /></th><%--담당MD접수 --%>
							<th><spring:message code='text.srm.field.evalAStatusNm' /></th><%--상담요청 --%>
							<th><spring:message code='text.srm.field.evalBStatusNm' /></th><%--품평회 --%>
							<%-- <th><spring:message code='text.srm.field.evalCStatusNm' /></th>이행보증보험 --%>
							<th><spring:message code='text.srm.field.evalDStatusNm' /></th><%--품질경영평가 --%>
						</tr>
					</thead>
					<tbody class="align-c" id="dataListbody"></tbody>
				</table>
			</form>
			</div><!-- END 테이블이 넓을 경우 스크롤 -->
			<br>
			<!-- 범주 영역 start --------------------------->
			<div align="right">
				<div class="status_circle sc_gray"		style="cursor: default;"></div><span style="vertical-align: top;">&nbsp;<spring:message code='text.srm.field.srmrst0020.pcircle1'/></span>	<%--요청 --%> &nbsp;
				<div class="status_circle sc_yellow"	style="cursor: default;"></div><span style="vertical-align: top;">&nbsp;<spring:message code='text.srm.field.srmrst0020.pcircle3'/></span>	<%--요청확인, 기관평가완료 --%> &nbsp;
				<div class="status_circle sc_blue"		style="cursor: default;"></div><span style="vertical-align: top;">&nbsp;<spring:message code='text.srm.field.srmrst0020.pcircle6'/></span>	<%--SKIP요청 --%>
				<div class="status_circle sc_pink"		style="cursor: default;"></div><span style="vertical-align: top;">&nbsp;<spring:message code='text.srm.field.srmrst0020.pcircle4'/></span>	<%--거절, SKIP반려 --%> &nbsp;
				<div class="status_circle sc_green"  	style="cursor: default;"></div><span style="vertical-align: top;">&nbsp;<spring:message code='text.srm.field.srmrst0020.pcircle5'/></span>	<%--완료, SKIP확정 --%> &nbsp;
				<div class="status_circle sc_darkgray"  style="cursor: default;"></div><span style="vertical-align: top;">&nbsp;<spring:message code='text.srm.field.srmrst0020.pcircle2'/></span>	<%--품질경영평가재요청 --%> &nbsp;
				<div class="status_circle sc_orange"  	style="cursor: default;"></div><span style="vertical-align: top;">&nbsp;<spring:message code='text.srm.field.srmrst0020.pcircle7'/></span>	<%--업체확인요청 --%> &nbsp;
			</div>
			<!-- 범주 영역 end --------------------------->

			<!-- Paging 영역 start --------------------------->
			<div id="pages">
				<div id="paging" class="board-pager mt30"></div>
			</div>
			<!-- Paging 영역 end --------------------------->

		</div><!-- END Sub Wrap -->
	</div><!--END Container-->

	<form id="frmHidden" name="frmHidden" method="post">
		<input type="hidden" id="evNo"	name="evNo"	/>
		<input type="hidden" id="seq"	name="seq"	/>
		<input type="hidden" id="evalSellerCode"	name="evalSellerCode"	/>
	</form>

	<form id="hiddenForm" name="hiddenForm" method="post">
		<input type="hidden" id="houseCode" name="houseCode" />
		<input type="hidden" id="sellerCode" name="sellerCode" />
		<input type="hidden" id="reqSeq" name="reqSeq" />
		<input type="hidden" id="inOutKind" name="inOutKind" />
	</form>	
	
</body>
</html>