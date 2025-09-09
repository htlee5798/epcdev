<%--
	Page Name 	: NEDMIGG0010.jsp
	Description : 임가공 출고 관리 페이지 
	Modification Information
	
	수정일		수정자          		수정내용
	----------	-----------		---------------------------
 	2018.11.20	SHIN SE JIN		최초생성
--%>

<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr class="r1">

		<input type="hidden" id="budat" name="budat" value="<c:out value="\${BUDAT }"/>" />
		<input type="hidden" id="werks" name="werks" value="<c:out value="\${WERKS }"/>" />
		<input type="hidden" id="eblnr" name="eblnr" value="<c:out value="\${EBLNR }"/>" />
		<input type="hidden" id="giSeq" name="giSeq" value="<c:out value="\${GI_SEQ }"/>" />
		<input type="hidden" id="giMatnr" name="giMatnr" value="<c:out value="\${GI_MATNR }"/>" />
		<input type="hidden" id="grMatnr" name="grMatnr" value="<c:out value="\${GR_MATNR }"/>" />
		<input type="hidden" id="remainQty" name="remainQty" value="<c:out value="\${REMAIN_QTY }"/>" />

		<td align="center"><input type="checkbox" id="check" name="check" /></td>
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
		<td align="right"><input type="text" id="useNewQty" name="useNewQty" onkeypress='return isNumberKey(event)' style="width:50px;" /></td>
		<td align="right"><input type="text" id="lossNewQty" name="lossNewQty" onkeypress='return isNumberKey(event)' style="width:50px;" /></td>
		<td align="center"><c:out value="\${GR_MATNR }"/></td>
		<td align="left"><c:out value="\${GR_MAKTX }"/></td>
		<td align="center"><c:out value="\${GR_MEINS }"/></td>
		<td align="right"><input type="text" id="grNewQty" name="grNewQty" onkeypress='return isNumberKey(event)' style="width:50px;" /></td>
		<td align="center">
			<form name="splyDwForm_<c:out value="\${index }"/>"	id="splyDwForm_<c:out value="\${index }"/>" method="post" action="#">
				<input type="text" class="day" id="splyDw" name="splyDw" onkeydown='return onlyNumber(event)' readonly maxlength="10" style="width:80px;" />
				<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('splyDwForm_<c:out value="\${index }"/>.splyDw');" style="cursor:hand;" />
			</form>
		</td>
		<td align="left"><input type="text" id="memo_<c:out value="\${index }"/>" name="memo_<c:out value="\${index }"/>" onkeyup="checkByte(this, 200, memo_<c:out value="\${index }"/>)" style="width:190px;" /></td>
	</tr>
</script>			
	
<script>

	/* 임가공 출고요청 정보 조회 RFC Call */
	function doSearch() {
		
		var searchInfo = {};
		
		searchInfo["I_FR_DATE"]	=	$("#searchForm input[name='srchFromDate']").val().replaceAll("-", "");	// 시작일자                                       
		searchInfo["I_TO_DATE"]	=	$("#searchForm input[name='srchToDate']").val().replaceAll("-", "");	// 종료일자
		searchInfo["I_LIFNR"]	=	$("#searchForm select[name='srchEntpCode']").val();						// 파트너사 코드
		searchInfo["I_SPLY_FG"]	=	null;																		// 납품완료 지시자(촐고화면:""/입고화면:"X")
		// 공통 RequestCommon
		searchInfo["REQCOMMON"] =	getReqCommon();
		
		if(dateValid()){
			// 임가공 입고정보 조회 (콜백 rfcCallBack)
			rfcCall("INV0990" , searchInfo);
		}
	}
	
	/* RFC CallBack */
	function rfcCallBack(data) {
		
		setTbodyInit("dataListbody");	// dataList 초기화
		
		var result = data.result;
		if(result.TAB != null && result.E_MSGTYP == "S") {
			_setTbodyMasterValue(result.TAB);
		} else {
			setTbodyNoResult("dataListbody", 21);	
		}
	}
	
	/* 임가공 출고요청 정보 리스트 출력 */
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
		}
		
		$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
	}

	/* 조회 Validation */
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
			$("#srchFromDate").focus();
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
	
	/* 입고 */
	function doSave() {
		
		if(dupleReq) return;	// 중복요청 방지
		
		var checkCnt = $("input[name='check']:checked").length;
		var dataList = [];
		var errCnt = 0;
		
		if (checkCnt > 0) {
			$("input[name='check']:checkbox").each(function(index) {
				var dataInfo = {};
				if ($(this).is(":checked")) {
					$("#dataListbody tr").eq(index).find("input").map(function() {
						
						if(this.name == "check")return;
						
						if(this.name == "splyDw") {
							dataInfo[this.name] = $(this).val().replaceAll("-", "");
						
						} else if(this.name == "useNewQty" || this.name == "lossNewQty" || this.name == "grNewQty") {
							dataInfo[this.name] = parseFloat($(this).val());
							
						} else if(this.name.indexOf("memo") > -1) {
							dataInfo["memo"] = $(this).val();
							
						} else {
							dataInfo[this.name] = $(this).val();
						}
						
					});
					
					dataList.push(dataInfo);	// 입고정보 List추가
				}
			});
			
			if(!dateValid2(dataList))return;
			
			if(!confirm("<spring:message code='msg.igg.save.confirm'/>"))return;
			
			dupleReq = true;	// 중복요청 방지
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/imgagong/imgagongGrDataSave.json"/>',
				data : JSON.stringify({'dataList' : dataList}),
				success : function(data) {
					if(data.resultCd == "success") {
						
						// 20190628 전산정보팀 이상구 추가. EPC 상에서 인터페이스 성공처리로 리턴 받았지만 
						// SAP에서 오류난 경우에 대해서 처리를 위해 아래 로직 추가. 
						if(!(typeof data.keys == "undefined")){ 
							// 오류난 키값이 있는경우 메세지를 출력해 준다.
							var err_msg = "";						
							err_msg = "총 " + data.keys.length + "건 입고처리 중 오류가 발생하였습니다.\n" ;
							
							data.keys.forEach(function(v,i){
								console.log(v + ":" + i);
								err_msg = err_msg + " - " +data.eMessages[i] + "\n" ;	// 해당 오류에 대한 에러를 표기해 준다. 					
							});
							
							// 에러 매세지를 출력해 준다.	
							alert(err_msg);
							
						}else{
							alert("<spring:message code='msg.igg.save.success'/>"); // 입고가 정상적으로 처리 되었습니다.
						} 
						
					} else {
						alert("<spring:message code='msg.igg.save.error'/>"); // 입고중 오류가 발생하였습니다.
					}
					
					doSearch();	// 출고 요청현황 조회
				}
			});
			
			dupleReq = false;	// 중복요청 방지 해제
			
		} else {
			alert("<spring:message code='msg.igg.save.validation1'/>");
			return;
		}
		
	}
	
	/* 입고 Validation */
	function dateValid2(dataList) {
		
		for(var index = 0; index < dataList.length; index++) {
			
			var dataInfo = dataList[index];
			
			// 사용수량 체크
			if($.trim(dataInfo['useNewQty']) == "") {
				alert("<spring:message code='msg.igg.save.validation2'/>");
				return false;
			}
			
			// 수율수량 체크
			if($.trim(dataInfo['lossNewQty']) == "") {
				alert("<spring:message code='msg.igg.save.validation3'/>");
				return false;
			}
			
			// 사용수량과 수율수량 합 체크(합이 재고수량보다 많을 수 없음) 
			if(parseFloat(dataInfo['useNewQty']) + parseFloat(dataInfo['lossNewQty']) > parseFloat(dataInfo['remainQty'])) {
				alert("<spring:message code='msg.igg.save.validation4'/>");
				return false;
			}
			
			// 입고수량 체크
			if($.trim(dataInfo['lossNewQty']) == "" || parseInt(dataInfo['grNewQty']) == 0) {
				alert("<spring:message code='msg.igg.save.validation5'/>");
				return false;
			}
			
			// 납품예정일 체크
			if($.trim(dataInfo['splyDw']) == "") {
				alert("<spring:message code='msg.igg.save.validation6'/>");
				return false;
			}
			
			var nowDate = '<c:out value="${paramMap.nowDate}"/>';	// YYYY-MM-DD
			var splyDw = dataInfo["splyDw"];
			
			var intNowDate 	= parseInt(nowDate.replaceAll("-", ""));
			var intSplyDw	= parseInt(splyDw);
			
			// 납품예정일 미래일자 체크
			if (intNowDate >= intSplyDw) {
				alert("<spring:message code='msg.igg.save.validation7'/>");
				return false;
			}
			
		}
		return true;
	}
	
	/* 전체체크 */
	function eventCheckAll() {
		
		var checkCnt = $("input[name='check']").length;
		
		if (checkCnt <= 0) {
			return;
		}
		
		if ($("input[name='checkAll']").is(":checked")) {
			$("input[name='check']:checkbox").each(function() {
				$(this).attr("checked", true);
			});
		} else {
			$("input[name='check']:checkbox").each(function() {
				$(this).attr("checked", false);
			});
		}
		
	}
	
	/* 숫자만입력 */
	function onlyNumber(event){
		event = event || window.event;
		var keyID = (event.which) ? event.which : event.keyCode;
		if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ) 
			return;
		else
			return false;
	}
	
	/* 소수입력(3자리)*/
	function isNumberKey(event) {

		event = event || window.event;
		var keyID = (event.which) ? event.which : event.keyCode;
		
        if (keyID != 46 && keyID > 31 && (keyID < 48 || keyID > 57))
            return false;

        // Textbox value       
        var _value = event.srcElement.value;       

        // 소수점(.)이 두번 이상 나오지 못하게
        var _pattern0 = /^\d*[.]\d*$/; // 현재 value값에 소수점(.) 이 있으면 . 입력불가
        if (_pattern0.test(_value)) {
            if (keyID == 46) {
                return false;
            }
        }

        // 소수점 둘째자리까지만 입력가능
        var _pattern2 = /^\d*[.]\d{3}$/; // 현재 value값이 소수점 셋째짜리 숫자이면 더이상 입력 불가
        if (_pattern2.test(_value)) {
            return false;
        }
        
        return true;
    }

	
	/* 바이트수 체크 */
	function checkByte(input, max) {

		var str = input.value; 
		var strLength = str.length;		// 전체길이
		
		//변수초기화
		var inputByte = 0;				// 한글일 경우 3, 그 외 글자는 1을 더함
		var charLength = 0;				// substring하기 위해 사용
		var oneChar = "";				// 한글자씩 검사
		var strTemp = "";				// 글자수를 초과하면 제한한 글자전까지만 보여줌.
		
		for(var i=0; i< strLength; i++) {
			oneChar = str.charAt(i);	// 한 글자 추출
			if (escape(oneChar).length > 4) { 
				inputByte +=3;			// 한글이면 3를 더한다
			} else {
				inputByte++;			// 한글이 아니면 1을 다한다
			}
		    
			if (inputByte <= max) {
				charLength = i + 1;
			}
		}
		
		// 글자수 초과
		if (inputByte > max) {
			alert( max + "바이트를 초과 입력할수 없습니다.");
			strTemp = str.substr(0, charLength);
			input.value = strTemp;

			// 표시될 글자수를 다시 센다.
			var tempByte = 0;
			for(var i=0; i< strTemp.length; i++) {
				var tempChar = strTemp.charAt(i);
				if (escape(tempChar).length > 4) { 
					tempByte +=3;
				} else {
					tempByte++;
				}
			}
			inputByte = tempByte;
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
									<a href="#" class="btn" onclick="doSave();"><span><spring:message code="btn.igg.save.imgagongSave"/></span></a>
									<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
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
							<li class="tit"><spring:message	code="text.igg.field.result" /></li>
						</ul>
						<div class="datagrade_scroll">
							<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="headTbl">
								<colgroup>
									<col style="width:20px;" /><!--  -->
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
									<col style="width:55px;" /><!-- 기존 사용수량 -->
									<col style="width:55px;" /><!-- 기존 수율수량 -->
									<col style="width:55px;" /><!-- 잔여재고 수량 -->
									<col style="width:60px;" /><!-- 사용수량 -->
									<col style="width:60px;" /><!-- 수율수량 -->
									<col style="width:90px;" /><!-- 완제품 코드 -->
									<col style="width:150px;" /><!-- 완제품명 -->
									<col style="width:40px;" /><!-- 단위 -->
									<col style="width:60px;" /><!-- 입고수량 -->
									<col style="width:120px;" /><!-- 납품예정일 -->
									<col style="width:200px;" /><!-- 메모 -->
									<col  />
								</colgroup>
								<%-- <spring:message	code="epc.igg.header.srcmkCd" /> --%>
								<tr>
									<th><input type="checkbox" id="checkAll" name="checkAll" onclick="eventCheckAll();" /></th>
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
									<th><spring:message	code="epc.igg.header.useQty" /></th>
									<th><spring:message	code="epc.igg.header.lossQty" /></th>
									<th><spring:message	code="epc.igg.header.remainQty" /></th>
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
							</table>
							<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="listTbl">
								<colgroup>
									<col style="width:20px;" /><!--  -->
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
									<col style="width:55px;" /><!-- 기존 사용수량 -->
									<col style="width:55px;" /><!-- 기존 수율수량 -->
									<col style="width:55px;" /><!-- 잔여재고 수량 -->
									<col style="width:60px;" /><!-- 사용수량 -->
									<col style="width:60px;" /><!-- 수율수량 -->
									<col style="width:90px;" /><!-- 완제품 코드 -->
									<col style="width:150px;" /><!-- 완제품명 -->
									<col style="width:40px;" /><!-- 단위 -->
									<col style="width:60px;" /><!-- 입고수량 -->
									<col style="width:120px;" /><!-- 납품예정일 -->
									<col style="width:200px;" /><!-- 메모 -->
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
						<li><spring:message	code="epc.igg.menu.lvl3_1" /></li>
						<li class="last"><spring:message code="epc.igg.menu.ctrCdDetail_1" /></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
</body>
</html>