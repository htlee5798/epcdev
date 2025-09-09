<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

	function popupVendorList()
	{
		var targetUrl = '<c:url value="/common/selectPartnerSmallPopup.do"/>';
		Common.centerPopupWindow(targetUrl, 'popup', {width : 400, height : 550});
	}

	/**
	 * 점포 Popup
	 */
	function storePopUp(pageName) {
		var url = "<c:url value='/edi/comm/PEDPCOM0001.do'/>";
		
		var cw=400;
		var ch=440;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		
		window.open(url,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
	}
	
	/**
	 * 상품 Popup
	 */
	function productPopUp() {
		var url = "<c:url value='/edi/comm/PEDPCOM0002.do'/>";
		
		var cw=400;
		var ch=440;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		
		window.open(url,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
	}
	

	/**
	 * RFC Controller 호출
	 * 작업 완료 후 rfcCallBack 함수 호출
	 * @param proxyNm
	 * @param paramInfo
	 */
	function rfcCall(proxyNm, param) {
		if (proxyNm == "") {
			//alert("PROXY명을 확인해주세요.");
			return;
		}
		
		var requestParam = {};
		requestParam["param"] 	= JSON.stringify(param);
		requestParam["proxyNm"] = proxyNm;
		//console.log(requestParam);
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/comm/rfcCall.json" />',
			data : JSON.stringify(requestParam),
			success : function(data) {
				rfcCallBack(data);
			}
		});
	}
	
	/*  */
	function fnDateValid(startDate, endDate, diffDay) {
		var rangeDate = 0;

		if (startDate == "" || endDate == "") {
			alert("<spring:message code='msg.common.fail.nocalendar'/>");
			//$("#startDate").focus();
			return false;
		}

		startDate 	= startDate.replaceAll("-", "");
		endDate 	= endDate.replaceAll("-", "");
		//console.log(startDate + "::" + endDate);
		
		var intStartDate 	= parseInt(startDate);
		var intEndDate 		= parseInt(endDate);

		if (intStartDate > intEndDate) {
			alert("<spring:message code='msg.common.fail.calendar'/>");
			//$("#startDate").focus();
			return false;
		}

		intStartDate 	= new Date(startDate.substring(0, 4), startDate.substring(4, 6), startDate.substring(6, 8), 0, 0, 0);
		intEndDate 		= new Date(endDate.substring(0, 4), endDate.substring(4, 6), endDate.substring(6, 8), 0, 0, 0);
		
		rangeDate = Date.parse(intEndDate) - Date.parse(intStartDate);
		rangeDate = Math.ceil(rangeDate / 24 / 60 / 60 / 1000);

		if (rangeDate > parseInt(diffDay)) {
			if (diffDay == "15") {
				alert("<spring:message code='msg.common.fail.rangecalendar_15'/>");
			} else {
				alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			}
			
			//$("#startDate").focus();
			return false;
		}

		return true;
	}
	
	/* 숫자여부 체크 */
	function fnOnlyNumber(e) {
		var keyCode = (window.netscape) ? e.which : e.keyCode;
		if (!(keyCode == 0 || keyCode == 8 || (keyCode >= 48 && keyCode <= 57 ))) {
			if (window.netscape) {
				e.preventDefault();
			} else {
				if(event.preventDefault){
		            event.preventDefault();
		        } else {
		            event.returnValue = false;
		        }
			}
		}
	}
	
</script>