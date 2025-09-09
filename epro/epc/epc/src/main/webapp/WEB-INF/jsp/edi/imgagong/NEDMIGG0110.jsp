<%--
	Page Name 	: NEDMIGG0110.jsp
	Description : 임가공 입고 관리 페이지 
	Modification Information
	
	수정일		수정자          		수정내용
	----------	-----------		---------------------------
 	2018.11.22	SHIN SE JIN		최초생성
--%>

<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<!-- 입고정보 헤더 -->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr style="display: table-row; vertical-align: inherit; background-color: #e0f8f7;">
		<td align="center"><c:out value="\${NO }"/></td>
		<td align="center" id="detailBtn_<c:out value="\${index }"/>">
			<a href="#" onclick="doSearchDetail('<c:out value="\${EBLNR }"/>','<c:out value="\${GI_SEQ }"/>','<c:out value="\${index }"/>');">
				<img alt="" src="/images/bb/layout/lnb_plus.gif">
			</a>
		</td>
		<td align="center"><c:out value="\${BUDAT_FMT }"/></td>
		<td align="center"><c:out value="\${DCWER }"/></td>
		<td align="left"><c:out value="\${DCNAM }"/></td>
		<td align="center"><c:out value="\${WERKS }"/></td>
		<td align="left"><c:out value="\${NAME1 }"/></td>
		<td align="center"><c:out value="\${EBLNR }"/></td>
		<td align="center"><c:out value="\${GI_SEQ }"/></td>
		<td align="center"><c:out value="\${GI_MATNR }"/></td>
		<td align="left"><c:out value="\${GI_MAKTX }"/></td>
		<td align="center"><c:out value="\${GI_MEINS }"/></td>
		<td align="right"><c:out value="\${GI_QTY }"/></td>
		<td align="right"><c:out value="\${USE_QTY }"/></td>
		<td align="right"><c:out value="\${LOSS_QTY }"/></td>
		<td align="right"><c:out value="\${REMAIN_QTY }"/></td>
		<td align="center"><c:out value="\${GR_MATNR }"/></td>
		<td align="left"><c:out value="\${GR_MAKTX }"/></td>
		<td align="center"><c:out value="\${GR_MEINS }"/></td>
		<td align="right"><c:out value="\${GR_QTY }"/></td>
		<td align="center"><c:out value="\${MESSAGE }"/></td>
	</tr>
	<tr id="detailRow_<c:out value="\${index }"/>"  style="display: none">
		<td>
		<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="headTbl">
			<colgroup>
				<col style="width:25px;" />
				<col style="width:30px;" />
				<col style="width:40px;" />
				<col style="width:90px;" />
				<col style="width:50px;" />
				<col style="width:90px;" />
				<col style="width:90px;" />
				<col style="width:50px;" />
				<col style="width:90px;" />
				<col style="width:150px;" />
				<col style="width:40px;" />
				<col style="width:50px;" />
				<col style="width:50px;" />
				<col style="width:90px;" />
				<col style="width:210px;" />
				<col style="width:40px;" />
				<col style="width:60px;" />
				<col style="width:120px;" />
				<col style="width:300px;" />
				<col  />
			</colgroup>
			<tr>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<th><spring:message	code="epc.igg.header.stat" /></th>
				<th><spring:message	code="epc.igg.header.budat" /></th>
				<th><spring:message	code="epc.igg.header.strCd" /></th>
				<th><spring:message	code="epc.igg.header.strNm" /></th>
				<th><spring:message	code="epc.igg.header.eblnr" /></th>
				<th>SEQ</th>
				<th><spring:message	code="epc.igg.header.giMatnr" /></th>
				<th><spring:message	code="epc.igg.header.giMaktx" /></th>
				<th><spring:message	code="epc.igg.header.meins" /></th>
				<th><spring:message	code="epc.igg.header.useNewQty" /></th>
				<th><spring:message	code="epc.igg.header.lossNewQty" /></th>
				<th><spring:message	code="epc.igg.header.grMatnr" /></th>
				<th><spring:message	code="epc.igg.header.grMaktx" /></th>
				<th><spring:message	code="epc.igg.header.meins" /></th>
				<th><spring:message	code="epc.igg.header.grQty" /></th>
				<th><spring:message	code="epc.igg.header.splyDw" /></th>
				<th><spring:message	code="epc.igg.header.memo" /></th>
				<th>&nbsp;</th>
			</tr>
			<tbody id="detailTbody_<c:out value="\${index }"/>" />
		</table>
		</td>
	</tr>
</script>

<!-- 입고정보 상세내역 -->
<script id="dataListTemplate2" type="text/x-jquery-tmpl">
	<tr style="display: table-row; vertical-align: inherit; background-color: #f8ece0;">
		<td align="center"></td>
		<td align="center">
			<input type="hidden" id="budat" name="budat" value="<c:out value="\${BUDAT }"/>" />
			<input type="hidden" id="werks" name="werks" value="<c:out value="\${WERKS }"/>" />
			<input type="hidden" id="eblnr" name="eblnr" value="<c:out value="\${EBLNR }"/>" />
			<input type="hidden" id="giSeq" name="giSeq" value="<c:out value="\${GI_SEQ }"/>" />
			<input type="hidden" id="grSeq" name="grSeq" value="<c:out value="\${GR_SEQ }"/>" />			
			<input type="hidden" id="giMatnr" name="giMatnr" value="<c:out value="\${GI_MATNR }"/>" />
			<input type="hidden" id="useNewQty" name="useNewQty" value="<c:out value="\${USE_QTY }"/>" />
			<input type="hidden" id="lossNewQty" name="lossNewQty" value="<c:out value="\${LOSS_QTY }"/>" />
			<input type="hidden" id="grMatnr" name="grMatnr" value="<c:out value="\${GR_MATNR }"/>" />
			<input type="hidden" id="splyDw" name="splyDw" value="<c:out value="\${SPLY_DW }"/>" />
			<input type="hidden" id="memo" name="memo" value="<c:out value="\${MEMO }"/>" />
			
			{%if DEL_STAT == 'Y' %}
				<input type="radio" id="radioBtn" name="radioBtn" />
			{%else%}
				<input type="radio" id="radioBtn" name="radioBtn" disabled />
			{%/if%}
		</td>
		<td align="center"><c:out value="\${DEL_STAT }"/></td>
		<td align="center"><c:out value="\${BUDAT_FMT }"/></td>
		<td align="center"><c:out value="\${WERKS }"/></td>
		<td align="left"><c:out value="\${NAME1 }"/></td>
		<td align="center"><c:out value="\${EBLNR }"/></td>
		<td align="center"><c:out value="\${GR_SEQ }"/></td>
		<td align="center"><c:out value="\${GI_MATNR }"/></td>
		<td align="left"><c:out value="\${GI_MAKTX }"/></td>
		<td align="center"><c:out value="\${GI_MEINS }"/></td>
		<td align="right"><c:out value="\${USE_QTY }"/></td>
		<td align="right"><c:out value="\${LOSS_QTY }"/></td>
		<td align="center"><c:out value="\${GR_MATNR }"/></td>
		<td align="left"><c:out value="\${GR_MAKTX }"/></td>
		<td align="center"><c:out value="\${GR_MEINS }"/></td>
		<td align="right"><c:out value="\${GR_QTY }"/></td>
		<td align="center"><c:out value="\${SPLY_DW_FMT }"/></td>
		<td align="left" colspan="3"><c:out value="\${MEMO }"/></td>
	</tr>
</script>
	
<script>

	/* 임가공 입고정보 조회(헤더) RFC Call */
	function doSearchHeader() {
		
		var searchInfo = {};
		
		searchInfo["I_FR_DATE"]	=	$("#searchForm input[name='srchFromDate']").val().replaceAll("-", "");	// 시작일자                                       
		searchInfo["I_TO_DATE"]	=	$("#searchForm input[name='srchToDate']").val().replaceAll("-", "");	// 종료일자
		searchInfo["I_LIFNR"]	=	$("#searchForm select[name='srchEntpCode']").val();						// 파트너사 코드
		searchInfo["I_SPLY_FG"]	=	"X";																	// 납품완료 지시자(촐고화면:""/입고화면:"X")
		// 공통 RequestCommon
		searchInfo["REQCOMMON"] =	getReqCommon();
		
		if(dateValid()){
			
			var requestParam = {};
			requestParam["param"] 	= JSON.stringify(searchInfo);
			requestParam["proxyNm"] = "INV0990";
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/comm/rfcCall.json" />',
				data : JSON.stringify(requestParam),
				success : function(data) {
					rfcHaederCallBack(data);
				}
			});
			
		}
	}
	
	/* RFC CallBack */
	function rfcHaederCallBack(data) {
		
		setTbodyInit("dataListbody");	// dataList 초기화
		
		var result = data.result;
		if(result.TAB != null && result.E_MSGTYP == "S") {
			_setTbodyMasterValue(result.TAB);
		} else {
			setTbodyNoResult("dataListbody", 19);	
		}
	}
	
	/* 임가공 입고정보(헤더) 리스트 출력 */
	function _setTbodyMasterValue(data) {

		if (data.length > 0) {
			
			for(var i = 0; i < data.length; i++) {
				var budat = data[i]['BUDAT'].toString();					// 전기일자 '-' 삽입
				data[i]['BUDAT_FMT'] = budat.substring(0, 4) + "-" + budat.substring(4, 6) + "-" + budat.substring(6, 8);
				/* 
				data[i]['GI_QTY'] = Math.ceil(data[i]['GI_QTY']);			// 출고수량 반올림
				data[i]['USE_QTY'] = Math.ceil(data[i]['USE_QTY']);			// 기존 사용수량 반올림
				data[i]['LOSS_QTY'] = Math.ceil(data[i]['LOSS_QTY']);		// 기존 수율수량 반올림
				data[i]['REMAIN_QTY'] = Math.ceil(data[i]['REMAIN_QTY']);	// 잔여재고 수량 반올림
				data[i]['GR_QTY'] = Math.ceil(data[i]['GR_QTY']);			// 입고 수량 반올림
				 */
				data[i]['index'] = i;
				data[i]['NO'] = i+1;
			}
			
			
		} else {
			
			var budat = data['BUDAT'].toString();					// 전기일자 '-' 삽입
			data['BUDAT_FMT'] = budat.substring(0, 4) + "-" + budat.substring(4, 6) + "-" + budat.substring(6, 8);
			/* 
			data['GI_QTY'] = Math.ceil(data['GI_QTY']);				// 출고수량 반올림
			data['USE_QTY'] = Math.ceil(data['USE_QTY']);			// 기존 사용수량 반올림
			data['LOSS_QTY'] = Math.ceil(data['LOSS_QTY']);			// 기존 수율수량 반올림
			data['REMAIN_QTY'] = Math.ceil(data['REMAIN_QTY']);		// 잔여재고 수량 반올림
			data['GR_QTY'] = Math.ceil(data['GR_QTY']);				// 입고 수량 반올림
			 */
			data['index'] = 0;
			data['NO'] = 1;
			
		}

		$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
			
		/*	
		} else {
			setTbodyNoResult("dataListbody", 19);
		}
		*/
	}
	
	/* 임가공 입고정보 조회(상세내역) RFC Call */
	var detailRowIndex = "";
	function doSearchDetail(eblnr, giSeq, rowIndex) {
		
		// 필수 값 체크
		if(eblnr == "" || giSeq == "" || rowIndex == "") return;
		
		var searchInfo = {};
		
		detailRowIndex = rowIndex;						// 상세내역 rowIndex
		searchInfo["I_EBLNR"]		=	eblnr;			// 관리번호                                       
		searchInfo["I_GI_SEQ"]		=	giSeq;			// SEQ
		searchInfo["REQCOMMON"]		=	getReqCommon();	// 공통 RequestCommon
		
		// 입고정보 상세내역 조회 (콜백 rfcCallBack)
		rfcCall("INV1010" , searchInfo);
		
		var detailBtn = '<a href="#" onclick="doDetailHide(\''+eblnr+'\',\''+giSeq+'\',\''+rowIndex+'\');">'
					  + '<img alt="" src="/images/bb/layout/lnb_minus.gif"></a>';
	
		$("#detailBtn_"+rowIndex).empty();
		$("#detailBtn_"+rowIndex).html(detailBtn);
	}
	
	/* RFC CallBack */
	function rfcCallBack(data){
		var result = data.result;
		if(result.TAB != null && result.E_MSGTYP == "S") {
			_setTbodySubValue(data.result.TAB);

		} else {
			setTbodyNoResult("detailTbody_"+detailRowIndex, 18);
			$("#detailRow_"+detailRowIndex).show();							// 상세내역Row 보이기
		}
	}
	
	/* 임가공 입고정보(상세내역) 리스트 출력  */
	function _setTbodySubValue(data) {
		
		var nowDate = '<c:out value="${paramMap.nowDate}"/>';	// YYYY-MM-DD
		var intNowDate 	= parseInt(nowDate.replaceAll("-", ""));
		
		if (data.length > 0) {
			
			for(var i = 0; i < data.length; i++) {
				
				var budat = data[i]['BUDAT'].toString();					// 전기일자 '-' 삽입
				var splyDw = data[i]['SPLY_DW'].toString();					// 납품예정일자 '-' 삽입
				
				data[i]['BUDAT_FMT'] = budat.substring(0, 4) + "-" + budat.substring(4, 6) + "-" + budat.substring(6, 8);
				data[i]['SPLY_DW_FMT'] = splyDw.substring(0, 4) + "-" + splyDw.substring(4, 6) + "-" + splyDw.substring(6, 8);
				/* 
				data[i]['GI_QTY'] = Math.ceil(data[i]['GI_QTY']);			// 출고수량 반올림
				data[i]['USE_QTY'] = Math.ceil(data[i]['USE_QTY']);			// 사용수량 반올림
				data[i]['LOSS_QTY'] = Math.ceil(data[i]['LOSS_QTY']);		// 수율수량 반올림
				data[i]['GR_QTY'] = Math.ceil(data[i]['GR_QTY']);			// 입고 수량 반올림
				 */
				data[i]['headerIndex'] = detailRowIndex;					// 헤더 Index
				
				var intSplyDw	= parseInt(splyDw);
				
				/*// 납품예정일 미래일자 체크(미래일자인 경우 삭제불가)
				if (intNowDate >= intSplyDw) {
					data[i]['DEL_STAT'] = "N";
				} else {
					data[i]['DEL_STAT'] = "Y";
				}*/
				data[i]['DEL_STAT']=data[i]['FLAG'];	// ECO 테이블 칼럼 FLAG에 따른 삭제유무
			}
			
		} else {

			var budat = data['BUDAT'].toString();				// 전기일자 '-' 삽입
			var splyDw = data['SPLY_DW'].toString();			// 납품예정일자 '-' 삽입
			
			data['BUDAT_FMT'] = budat.substring(0, 4) + "-" + budat.substring(4, 6) + "-" + budat.substring(6, 8);
			data['SPLY_DW_FMT'] = splyDw.substring(0, 4) + "-" + splyDw.substring(4, 6) + "-" + splyDw.substring(6, 8);
			/* 
			data['GI_QTY'] = Math.ceil(data['GI_QTY']);			// 출고수량 반올림
			data['USE_QTY'] = Math.ceil(data['USE_QTY']);		// 사용수량 반올림
			data['LOSS_QTY'] = Math.ceil(data['LOSS_QTY']);		// 수율수량 반올림
			data['GR_QTY'] = Math.ceil(data['GR_QTY']);			// 입고 수량 반올림
			 */
			data['headerIndex'] = detailRowIndex;				// 헤더 Index
			
			var intSplyDw	= parseInt(splyDw);
			
			/*// 납품예정일 미래일자 체크(미래일자인 경우 삭제불가)
			if (intNowDate >= intSplyDw) {
				data['DEL_STAT'] = "N";
			} else {
				data['DEL_STAT'] = "Y";
			}*/
			data['DEL_STAT']=data['FLAG'];		// ECO 테이블 칼럼 FLAG에 따른 삭제유무
		}
		
		$("#detailRow_"+detailRowIndex).show();							// 상세내역Row 보이기
		$("#dataListTemplate2").tmpl(data).appendTo("#detailTbody_"+detailRowIndex);
		detailRowIndex = "";											// index 초기화
	}
	
	/* 임가공 입고정보(상세내역) 리스트 숨기기  */
	function doDetailHide(eblnr, giSeq, rowIndex) {
		var detailBtn = '<a href="#" onclick="doSearchDetail(\''+eblnr+'\',\''+giSeq+'\',\''+rowIndex+'\');">'
		  + '<img alt="" src="/images/bb/layout/lnb_plus.gif"></a>';

		$("#detailBtn_"+rowIndex).empty();
		$("#detailBtn_"+rowIndex).html(detailBtn);
		
		$("#detailTbody_"+rowIndex).empty();	// 상세내역 삭제
		$("#detailRow_"+rowIndex).hide();						// 상세내역Row 숨기기
	}

	/* 숫자만 입력 */
	function onlyNumber(event){
		event = event || window.event;
		var keyID = (event.which) ? event.which : event.keyCode;
		if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
			return;
		else
			return false;
	}

	/* 임가공 입고정보 조회(헤더) Validation */
	function dateValid() {
		
		var srchFromDate	=	$("#searchForm input[name='srchFromDate']").val();	// 시작일자
		var srchToDate		=	$("#searchForm input[name='srchToDate']").val();	// 종료일자
		
		var rangeDate = 0;

		if (srchFromDate == "" || srchToDate == "") {
			alert("<spring:message code='msg.common.fail.nocalendar'/>");	// 일자가 입력되지않았습니다.
			$("#srchFromDate").focus();
			return false;
		}

		// srchFromDate, srchToDate 는 yyyy-mm-dd 형식
		srchFromDate	= srchFromDate.substring(0, 4) + srchFromDate.substring(5, 7) + srchFromDate.substring(8, 10);
		srchToDate		= srchToDate.substring(0, 4) + srchToDate.substring(5, 7) + srchToDate.substring(8, 10);
		
		var intStartDate 	= parseInt(srchFromDate);
		var intEndDate 		= parseInt(srchToDate);

		if (intStartDate > intEndDate) {
			alert("<spring:message code='msg.common.fail.calendar'/>");
			return false;
		}

		intStartDate 	= new Date(srchFromDate.substring(0, 4), srchFromDate.substring(4, 6), srchFromDate.substring(6, 8), 0, 0, 0);
		intEndDate 		= new Date(srchToDate.substring(0, 4), srchToDate.substring(4, 6), srchToDate.substring(6, 8), 0, 0, 0);

		rangeDate = Date.parse(intEndDate) - Date.parse(intStartDate);
		rangeDate = Math.ceil(rangeDate / 24 / 60 / 60 / 1000);

		if (rangeDate > 30) {
			alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			$("#srchFromDate").focus();
			return false;
		}

		return true;
	}
	
	// 중복요청 전역변수
	var dupleReq = false;
	
	/* 삭제 */
	function doDelete() {
		
		if(dupleReq) return;	// 중복요청 방지
		
		var checkCnt = $("#dataListbody tr input[type=radio]:checked").length;
		
		if (checkCnt > 0) {
			
			var dataList = [];
			
			$("#dataListbody tr input[type=radio]").each(function(index) {
				var dataInfo = {};
				if ($(this).is(":checked")) {
					$(this).parent().find("input").map(function() {
						if(this.type == "radio")return;
						dataInfo[this.name] = $(this).val();
					});
					dataList.push(dataInfo);	// 입고정보 List추가
				}
			});

			if(!confirm("<spring:message code='msg.igg.delete.confirm'/>"))return;
			
			dupleReq = true;	// 중복요청 방지
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/imgagong/imgagongGrDataDelete.json"/>',
				data : JSON.stringify({'dataList' : dataList}),
				success : function(data) {
					if(data.resultCd == "success") {
						
						// SAP에서 오류난 경우에 대해서 처리를 위해 아래 로직 추가. 
						if(!(typeof data.keys == "undefined")){ 
							// 오류난 키값이 있는경우 메세지를 출력해 준다.
							var err_msg = "";						
							err_msg = "총 " + data.keys.length + "건 처리 중 오류가 발생하였습니다.\n" ;
							
							data.keys.forEach(function(v,i){
								console.log(v + ":" + i);
								err_msg = err_msg + " - " +data.eMessages[i] + "\n" ;	// 해당 오류에 대한 에러를 표기해 준다. 					
							});
							
							// 에러 매세지를 출력해 준다.	
							alert(err_msg);
							
						}else{
							alert("<spring:message code='msg.igg.delete.success'/>");
						} 
						
						
						
					} else {
						alert("<spring:message code='msg.igg.delete.error'/>");
					}
					
					doSearchHeader();	// 입고처리 현황 조회
				}
			});
			
			dupleReq = false;	// 중복요청 방지 해제
			
		} else {
			alert("<spring:message code='msg.igg.delete.validation1'/>");
			return;
		}
		
	}

</script>

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:<c:out value='${param.widthSize }'/></c:if> >
		<div>
			<!--	@ BODY WRAP START 	-->
			<form name="searchForm"	id="searchForm" method="post" action="#">
				<div id="wrap_menu">
					<div class="wrap_search">
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit"><spring:message	code="text.igg.field.searchCondition" /></li>
								<li class="btn">
									<a href="#" class="btn" onclick="doDelete();"><span><spring:message code="btn.igg.save.imgagongDelete"/></span></a>
									<a href="#" class="btn" onclick="doSearchHeader();"><span><spring:message code="button.common.inquire"/></span></a>
								</li>
							</ul>
							
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col style="width:20%" />
									<col style="width:30%" />
									<col style="width:20%" />
									<col style="*" />
								</colgroup>
								<tr>
									<th><span class="star">*</span> <spring:message code="text.igg.field.budat"/></th>
									<td>
										<input type="text" class="day" id="srchFromDate" name="srchFromDate" onkeydown='return onlyNumber(event)' maxlength="10" readonly value="<c:out value='${paramMap.firstDate}'/>" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchFromDate');" style="cursor:hand;" />
										~
										<input type="text" class="day" id="srchToDate" name="srchToDate" onkeydown='return onlyNumber(event)' maxlength="10" readonly value="<c:out value='${paramMap.nowDate}'/>" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchToDate');" style="cursor:hand;" />
									</td>
									<th><span class="star">*</span> <spring:message	code="text.igg.field.venCd" /> </th>
									<td>
										<html:codeTag objId="srchEntpCode" objName="srchEntpCode" width="150px;" selectParam="<c:out value='${paramMap.repVendorId}'/>" dataType="CP" comType="SELECT" formName="form" />
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</form>
			<div id="wrap_menu">
				<div class="wrap_con">
					<!-- list -->
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit"><spring:message	code="text.igg.field.result" />&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">※ 익스플로러 : 도구 - 인터넷옵션 - 호환성보기설정 - lottemart.com 제거 해주세요.</font></li>
						</ul>
						<div class="datagrade_scroll">
							<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="headTbl">
								<colgroup>
									<col style="width:25px;" /><!-- NO -->
									<col style="width:30px;" /><!-- +/- -->
									<col style="width:90px;" /><!-- 전기일자 -->
 									<col style="width:50px;" /><!-- 납품센터 -->
									<col style="width:90px;" /><!-- 납품센터명 -->
									<col style="width:50px;" /><!-- 점포 -->
									<col style="width:90px;" /><!-- 점포명 -->
									<col style="width:90px;" /><!-- 관리번호 -->
									<col style="width:50px;" /><!-- SEQ -->
									<col style="width:90px;" /><!-- 원재료 코드 -->
									<col style="width:150px;" /><!-- 원재료명 -->
									<col style="width:40px;" /><!-- 단위 -->
									<col style="width:55px;" /><!-- 출고수량 -->
									<col style="width:55px;" /><!-- 사용수량 -->
									<col style="width:55px;" /><!-- 수율수량 -->
									<col style="width:55px;" /><!-- 잔여재고 수량 -->
									<col style="width:90px;" /><!-- 완제품 코드 -->
									<col style="width:150px;" /><!-- 완제품명 -->
									<col style="width:40px;" /><!-- 단위 -->
									<col style="width:60px;" /><!-- 입고수량 -->
									<col style="width:260px;" /><!-- 메모 -->
									<col  />
								</colgroup>
								<tr>
									<th>NO</th>
									<th>&nbsp;</th>
									<th><spring:message	code="epc.igg.header.budat" /></th>
									<th><spring:message	code="epc.igg.header.dcwer" /></th>
									<th><spring:message	code="epc.igg.header.dcnam" /></th>
									<th><spring:message	code="epc.igg.header.strCd" /></th>
									<th><spring:message	code="epc.igg.header.strNm" /></th>
									<th><spring:message	code="epc.igg.header.eblnr" /></th>
									<th>SEQ</th>
									<th><spring:message	code="epc.igg.header.giMatnr" /></th>
									<th><spring:message	code="epc.igg.header.giMaktx" /></th>
									<th><spring:message	code="epc.igg.header.meins" /></th>
									<th><spring:message	code="epc.igg.header.giQty" /></th>
									<th><spring:message	code="epc.igg.header.useNewQty" /></th>
									<th><spring:message	code="epc.igg.header.lossNewQty" /></th>
									<th><spring:message	code="epc.igg.header.remainQty" /></th>
									<th><spring:message	code="epc.igg.header.grMatnr" /></th>
									<th><spring:message	code="epc.igg.header.grMaktx" /></th>
									<th><spring:message	code="epc.igg.header.meins" /></th>
									<th><spring:message	code="epc.igg.header.grQty" /></th>
									<th><spring:message	code="epc.igg.header.memo" /></th>
									<th>&nbsp;</th>
								</tr>
							</table>
							<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="listTbl">
								<colgroup>
									<col style="width:25px;" /><!-- NO -->
									<col style="width:30px;" /><!-- +/- -->
									<col style="width:90px;" /><!-- 전기일자 -->
									<col style="width:50px;" /><!-- 납품센터 -->
									<col style="width:90px;" /><!-- 납품센터명 -->
									<col style="width:50px;" /><!-- 점포 -->
									<col style="width:90px;" /><!-- 점포명 -->
									<col style="width:90px;" /><!-- 관리번호 -->
									<col style="width:50px;" /><!-- SEQ -->
									<col style="width:90px;" /><!-- 원재료 코드 -->
									<col style="width:150px;" /><!-- 원재료명 -->
									<col style="width:40px;" /><!-- 단위 -->
									<col style="width:55px;" /><!-- 출고수량 -->
									<col style="width:55px;" /><!-- 사용수량 -->
									<col style="width:55px;" /><!-- 수율수량 -->
									<col style="width:55px;" /><!-- 잔여재고 수량 -->
									<col style="width:90px;" /><!-- 완제품 코드 -->
									<col style="width:150px;" /><!-- 완제품명 -->
									<col style="width:40px;" /><!-- 단위 -->
									<col style="width:60px;" /><!-- 입고수량 -->
									<col style="width:260px;" /><!-- 메모 -->
									<col  />
								</colgroup>
								<!-- Data List Body Start ------------------------------------------------------------------------------>
								<tbody id="dataListbody" />
								<!-- Data List Body End   ------------------------------------------------------------------------------>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- footer -->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="notice"></div>
				<div class="location">
					<ul>
						<li><spring:message	code="epc.igg.menu.lvl1" /></li>
						<li><spring:message	code="epc.igg.menu.lvl2" /></li>
						<li><spring:message	code="epc.igg.menu.lvl3_2" /></li>
						<li class="last"><spring:message code="epc.igg.menu.ctrCdDetail_2" /></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
</body>
</html>