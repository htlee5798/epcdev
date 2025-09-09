<%@include file="../common.jsp"%>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%--
	Class Name : SRMVEN0010.jsp    
	Description : SRM > SRM 정보 > 파트너사정보변경
	Modification Information
	
	  수정일 			  수정자 						수정내용
	---------- 		---------    	-------------------------------------
	2016.07.29 		 AN TAE KYUNG 	최초생성
	
	author   : AN TAE KYUNG
	since    : 2016.07.29
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<script type="text/javascript" src="/js/epc/showModalDialog.js"></script>

<script>
	$(document).ready(function() {
		
		/* 협력업체코드 초기값 SET */
		//if ("<c:out value='${epcLoginSession.repVendorId}'/>" != "") {
		//	$("select[name='srchVenCd']").val("<c:out value='${epcLoginSession.repVendorId}'/>");
		//}
		
		/* 협력업체코드 전체 SET(다국어 지원을 위해서 추가) */
		var defName = "<spring:message code='text.srm.field.venCdAll' />";
		$("select[name='srchVenCd'] option:eq(0)").before("<option value=''>"+defName+"</option>");	// before: eq(0)전에 값 추가
		$("select[name='srchVenCd']").val(0);
		
		
		/* 숫자만 입력 */
		$('.numberic').css('imeMode','disabled').keydown(function(){
			var e = window.event; //윈도우의 event를 잡	는것입니다.
			//입력 허용 키
			if ( (e.keyCode >= 48 && e.keyCode <= 57)				//숫자키
					|| (e.keyCode >= 96 && e.keyCode <= 105)			//키패드 숫자키
					|| (e.keyCode >= 112 && e.keyCode <= 123)       //F1 - F12
					|| e.keyCode == 8           //Backspace
					|| e.keyCode == 9           //Tab
					|| e.keyCode == 16           //Shift
					|| e.keyCode == 17           //Ctrl
					|| e.keyCode == 18           //Alt
					|| e.keyCode == 46          //Delete
					|| e.keyCode == 35          //End
					|| e.keyCode == 36          //Home
					|| e.keyCode == 37          //왼쪽 화살표
					|| e.keyCode == 39 ){       //오른쪽 화살표
				return true;
			} else {
				return false;
			}
		}).blur(function(){
			onlyNum($(this));
		});
		
		doSearch();
	});

	//숫자만 입력 reset
	function onlyNum(obj){
		if( $(obj).val() != null && $(obj).val() != '' ) {
			var tmps = $(obj).val().replace(/[^0-9]/g, '');
			$(obj).val(tmps);
		}
	}

	/* 조회 */
	function doSearch() {
		var searchInfo = {};
		
		// 협력업체(개별)
		searchInfo["srchVenCd"] 		= $("#searchForm select[name='srchVenCd']").val();		// 협력업체
		searchInfo["srchStatus"] 		= $("#searchForm select[name='srchStatus']").val();		// 확정상태
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/ven/selectVendorList.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				//json 으로 호출된 결과값을 화면에 Setting 
				_setTbodyMasterValue(data);
			}
		});
	}
	
	/* _eventSearch() 후처리(data 객체 그리기) */
	function _setTbodyMasterValue(json) {
		var data = json.vendorList;
		
		setTbodyInit("dataListbody"); // dataList 초기화
		
		if (data.length > 0) {
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		} else {
			setTbodyNoResult("dataListbody", 9);
		}
	}
	
	/* 우편번호 찾기 */
	function openZipCodeNew() {
		var cw=730;
		var ch=670;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		
		window.open("<c:url value='/edi/srm/selectJusoPopup.do'/>","zipPopup","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
	}
	
	/* 우편번호 찾기 Return */
	function receiveValue(zipCd, juso) {
		if (zipCd != "") {
			$('input[name=newZip]').val(zipCd);
			$('input[name=newAddr]').val(juso);
		}
	}
	
	// 허용문자체크 메시지
	function setPermitMsg(target) {	
		var permitMsg = "<spring:message code='text.srm.alert.permitCheck' arguments='"+target+"'/>";
		return permitMsg;
	}
	
	// Byte체크 메시지
	function setByteMsg(target, size) {	
		var byteMsg = "<spring:message code='text.srm.alert.byteCheck' arguments='"+target+","+size+"'/>";
		return byteMsg;
	}
	
	/* 일괄적용 */
	function doSave() {
		var dataInfo = {};
		
		var cnt = $("input[name='chkSel']").length;
		
		// 선택된 파트너사가 체크 
		var chkCnt = 0;
		for (var i = 0; i < cnt; i++) {
			if ($("input[name='chkSel']").eq(i).is(":checked")) {
				chkCnt++;
			}
		}
		
		if (chkCnt <= 0) {
			alert("<spring:message code='msg.srm.alert.srmven0010.notChkCnt1' />");<%--선택된 파트너사가 없습니다. --%>
			return;
		}
		
		// 일괄적용할 항목 체크
		var pkgCnt = 0;
		$('input[type=checkbox]').each(function() {
			var id = $(this).attr("id");
			
			// 일괄적용 checkbox의 id는 pkg로 시작함
			if (id.substr(0, 3) == "pkg") {
				if (id != "pkgAll") {
					if ($(this).prop("checked")) {
						pkgCnt++;
					}
				}
			}
		});
		
		if (pkgCnt <= 0) {
			alert("<spring:message code='msg.srm.alert.srmven0010.reqPkgAll' />");<%--일괄적용할 항목을 선택하세요. --%>
			return;
		}
		
		// 저장대상 설정
		var alVenCd = new Array();	// 선택한 협력업체
		var k = 0;
		for (var i = 0; i < cnt; i++) {
			var info = {};
			if ($("input[name='chkSel']").eq(i).is(":checked")) {
				info["venCd"] 	= $("input[name='venCd']").eq(i).val();
				info["seq"] 	= $("input[name='seq']").eq(i).val();
				info["status"] 	= $("input[name='status']").eq(i).val();
				// 상태값(9:임시저장)
				//info["status"] = "9";
				
				// 대표자명
				<%-- if ($("input[name='pkgPresidNm']").is(":checked") || $("input[name='pkgAll']").is(":checked")) {
					
					var target = "<spring:message code='text.srm.field.sellerCeoName'/>";
					
					if (!$.trim($("input[name='newPresidNm']").val())) {
						alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")대표자명을(를) 입력하세요.
						$("input[name='newPresidNm']").focus();
						return;
					}
					
					if (!cal_3byte($("input[name='newPresidNm']").val(), '30', setPermitMsg(target), setByteMsg(target, '30'))) {
						$("input[name='newPresidNm']").focus();
						return;
					}
					
					info["modPresidNm"] = $("input[name='newPresidNm']").val();
					
				} else {
					info["modPresidNm"] = $("input[name='presidNm']").eq(i).val();
				} --%>
				
				// 대표자 이메일
				if ($("input[name='pkgPresidEmail']").is(":checked") || $("input[name='pkgAll']").is(":checked")) {
					
					var target = "<spring:message code='text.srm.field.sellerCeoEmail'/>";
					
					if (!$.trim($("input[name='newPresidEmail']").val())) {
						alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--대표자 이메일을(를) 입력하세요. --%>
						$("input[name='newPresidEmail']").focus();
						return;
					}
					
					if (!cal_3byte($("input[name='newPresidEmail']").val(), '200', setPermitMsg(target), setByteMsg(target, '200'))) {
						$("input[name='newPresidEmail']").focus();
						return;
					}
					
					info["modPresidEmail"] = $("input[name='newPresidEmail']").val();
					
				} else {
					info["modPresidEmail"] = $("input[name='presidEmail']").eq(i).val();
				}
				
				// 담당자명
				if ($("input[name='pkgDutyInf']").is(":checked") || $("input[name='pkgAll']").is(":checked")) {
					
					var target = "<spring:message code='text.srm.field.vMainName'/>";
					
					if (!$.trim($("input[name='newDutyInf']").val())) {
						alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--담당자명을(를) 입력하세요. --%>
						$("input[name='newDutyInf']").focus();
						return;
					}
					
					if (!cal_3byte($("input[name='newDutyInf']").val(), '100', setPermitMsg(target), setByteMsg(target, '100'))) {
						$("input[name='newDutyInf']").focus();
						return;
					}
					
					info["modDutyInf"] = $("input[name='newDutyInf']").val();
					
				} else {
					info["modDutyInf"] = $("input[name='dutyInf']").eq(i).val();
				}
				
				// 대표전화
				if ($("input[name='pkgRepTelNo']").is(":checked") || $("input[name='pkgAll']").is(":checked")) {
					
					var target = "<spring:message code='text.srm.field.vPhone1'/>";
					
					if (!$.trim($("input[name='newRepTelNo']").val())) {
						alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--대표전화을(를) 입력하세요. --%>
						$("input[name='newRepTelNo']").focus();
						return;
					}
					
					if (!cal_3byte($("input[name='newRepTelNo']").val(), '30', setPermitMsg(target), setByteMsg(target, '30'))) {
						$("input[name='newRepTelNo']").focus();
						return;
					}
					
					info["modRepTelNo"] = $("input[name='newRepTelNo']").val();
					
				} else {
					info["modRepTelNo"] = $("input[name='repTelNo']").eq(i).val();
				}
				
				// 담당자 휴대전화
				if ($("input[name='pkgHpNo1']").is(":checked") || $("input[name='pkgAll']").is(":checked")) {
					
					var target = "<spring:message code='text.srm.field.vMobilePhone'/>";
					
					if (!$.trim($("input[name='newHpNo1']").val())) {
						alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--휴대전화을(를) 입력하세요. --%>
						$("input[name='newHpNo1']").focus();
						return;
					}
					
					if (!cal_3byte($("input[name='newHpNo1']").val(), '30', setPermitMsg(target), setByteMsg(target, '30'))) {
						$("input[name='newHpNo1']").focus();
						return;
					}
					
					info["modHpNo1"] = $("input[name='newHpNo1']").val();
					
				} else {
					info["modHpNo1"] = $("input[name='hpNo1']").eq(i).val();
				}
				
				// FAX
				if ($("input[name='pkgFaxNo']").is(":checked") || $("input[name='pkgAll']").is(":checked")) {
					
					var target = "<spring:message code='text.srm.field.faxFhone'/>";
					
					if (!cal_3byte($("input[name='newFaxNo']").val(), '31', setPermitMsg(target), setByteMsg(target, '31'))) {
						$("input[name='newFaxNo']").focus();
						return;
					}
					
					info["modFaxNo"] = $("input[name='newFaxNo']").val();
				} else {
					info["modFaxNo"] = $("input[name='faxNo']").eq(i).val();
				}
				
				// 담당자 이메일
				if ($("input[name='pkgEmail']").is(":checked") || $("input[name='pkgAll']").is(":checked")) {
					
					var target = "<spring:message code='text.srm.field.vEmail'/>";
					
					if (!$.trim($("input[name='newEmail']").val())) {
						alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--이메일을(를) 입력하세요. --%>
						$("input[name='newEmail']").focus();
						return;
					}
					
					if (!cal_3byte($("input[name='newEmail']").val(), '200', setPermitMsg(target), setByteMsg(target, '200'))) {
						$("input[name='newEmail']").focus();
						return;
					}
					
					info["modEmail"] = $("input[name='newEmail']").val();
					
				} else {
					info["modEmail"] = $("input[name='email']").eq(i).val();
				}
				
				
				
				// 주소
				if ($("input[name='pkgAddr']").is(":checked") || $("input[name='pkgAll']").is(":checked")) {
					
					var target = "<spring:message code='text.srm.field.address'/>";
					
					if (!$.trim($("input[name='newZip']").val()) || !$.trim($("input[name='newAddr']").val()) || !$.trim($("input[name='newAddr2']").val())) {
						alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--주소을(를) 입력하세요. --%>
						$("input[name='newZip']").focus();
						return;
					}
					
					if (!cal_3byte($("input[name='newAddr2']").val(), '120', setPermitMsg(target), setByteMsg(target, '120'))) {
						alert("<spring:message code='text.srm.alert.byteCheck' arguments='" + target + ",120'/>");<%--주소은(는) 120 Byte 이상을 초과할 수 없습니다. --%>
						$("input[name='newAddr2']").focus();
						return;
					}
					
					info["modZip"] 		= $("input[name='newZip']").val();
					info["modAddr"] 	= $("input[name='newAddr']").val();
					info["modAddr2"] 	= $("input[name='newAddr2']").val();
					
				} else {
					info["modZip"] 		= $("input[name='zip']").eq(i).val();
					info["modAddr"] 	= $("input[name='addr']").eq(i).val();
					info["modAddr2"] 	= $("input[name='addr2']").eq(i).val();
				}
				
				// 인증정보
				if ($("input[name='pkgZzqcFg']").is(":checked") || $("input[name='pkgAll']").is(":checked")) {
					if ($("input[name='newZzqcFg1']").is(":checked")) {
						info["zzqcFg1"] = "X";
					} else {
						info["zzqcFg1"] = "";
					}
					if ($("input[name='newZzqcFg2']").is(":checked")) {
						info["zzqcFg2"] = "X";
					} else {
						info["zzqcFg2"] = "";
					}
					if ($("input[name='newZzqcFg3']").is(":checked")) {
						info["zzqcFg3"] = "X";
					} else {
						info["zzqcFg3"] = "";
					}
					if ($("input[name='newZzqcFg4']").is(":checked")) {
						info["zzqcFg4"] = "X";
					} else {
						info["zzqcFg4"] = "";
					}
					if ($("input[name='newZzqcFg5']").is(":checked")) {
						info["zzqcFg5"] = "X";
					} else {
						info["zzqcFg5"] = "";
					}
					if ($("input[name='newZzqcFg6']").is(":checked")) {
						info["zzqcFg6"] = "X";
					} else {
						info["zzqcFg6"] = "";
					}
					if ($("input[name='newZzqcFg7']").is(":checked")) {
						info["zzqcFg7"] = "X";
					} else {
						info["zzqcFg7"] = "";
					}
					if ($("input[name='newZzqcFg8']").is(":checked")) {
						info["zzqcFg8"] = "X";
					} else {
						info["zzqcFg8"] = "";
					}
					if ($("input[name='newZzqcFg9']").is(":checked")) {
						info["zzqcFg9"] = "X";
					} else {
						info["zzqcFg9"] = "";
					}
					if ($("input[name='newZzqcFg10']").is(":checked")) {
						info["zzqcFg10"] = "X";
					} else {
						info["zzqcFg10"] = "";
					}
					if ($("input[name='newZzqcFg11']").is(":checked")) {
						info["zzqcFg11"] = "X";
					} else {
						info["zzqcFg11"] = "";
					}
					
					info["zzqcFg12"] = $("input[name='newZzqcFg12']").val();
				} else {
					info["zzqcFg1"] 		= $("input[name='qcFg01']").eq(i).val();
					info["zzqcFg2"] 		= $("input[name='qcFg02']").eq(i).val();
					info["zzqcFg3"] 		= $("input[name='qcFg03']").eq(i).val();
					info["zzqcFg4"] 		= $("input[name='qcFg04']").eq(i).val();
					info["zzqcFg5"] 		= $("input[name='qcFg05']").eq(i).val();
					info["zzqcFg6"] 		= $("input[name='qcFg06']").eq(i).val();
					info["zzqcFg7"] 		= $("input[name='qcFg07']").eq(i).val();
					info["zzqcFg8"] 		= $("input[name='qcFg08']").eq(i).val();
					info["zzqcFg9"] 		= $("input[name='qcFg09']").eq(i).val();
					info["zzqcFg10"] 		= $("input[name='qcFg10']").eq(i).val();
					info["zzqcFg11"] 		= $("input[name='qcFg11']").eq(i).val();
					info["zzqcFg12"] 		= $("input[name='qcFg12']").eq(i).val();
				}
				
				// MD 이베일
				if ($("input[name='pkgMdEmail']").is(":checked") || $("input[name='pkgAll']").is(":checked")) {
					
					var target = "<spring:message code='text.srm.field.mdEmail'/>";
					
					if (!$.trim($("input[name='newMdEmail']").val())) {
						alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--MD이메일을(를) 입력하세요. --%>
						$("input[name='newMdEmail']").focus();
						return;
					}
					
					if (!cal_3byte($("input[name='newMdEmail']").val(), '200', setPermitMsg(target), setByteMsg(target, '200'))) {
						$("input[name='newMdEmail']").focus();
						return;
					}
					
					info["mdEmail"] = $("input[name='newMdEmail']").val();
				} else {
					info["mdEmail"] = $("input[name='newMdEmail']").eq(i).val();
				}
				
				alVenCd.push(info);
			}
		}
		
		dataInfo["alVenCd"] = alVenCd;
		
		if (!confirm("<spring:message code='msg.srm.alert.tempSave' />")) { <%--저장하시겠습니까? --%>
			return;
		}
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/ven/insertVenInfo.json"/>',
			data : JSON.stringify(dataInfo),
			success : function(data) {
				if (data.retMsg == "success") {
					alert("<spring:message code='msg.srm.alert.saveOk' />");<%--저장 되었습니다. --%>
					fnInitComponent();	// 항목 초기화
					doSearch();			// 재조회
				}
			}
		});
	}
	
	/* 초기화 */
	function fnInitComponent() {
		$("#modifyForm").find("input, checkbox, button").each(function() {
			if (this.type == "text") {
				//console.log(this.name);
				$("input[name='" + this.name + "']").val("");
			} else if (this.type == "checkbox") {
				$(this).prop("checked", false);
			}
		});
		
		$("input[name='pkgAll']").prop("checked", false);
		fnAllChecked();
	}
	
	/* 변경요청 */
	function doConfirm() {
		var dataInfo = {};
		
		var cnt = $("input[name='chkSel']").length;
		
		var chkCnt = 0;		// 선택 건수
		var noneCnt = 0;	// 임시저장이 아닌 건수
		var noneEmail = 0;	// MD 이메일 입력되지 않은 건수
		
		var alVenCd 	= new Array();		// 선택한 협력업체
		var alVenCdSeq 	= new Array();		// 선택한 협력업체 + 순번
		
		for (var i = 0; i < cnt; i++) {
			var info = {};
			var info2 = {};
			
			if ($("input[name='chkSel']").eq(i).is(":checked")) {
				info["venCd"] 	= $("input[name='venCd']").eq(i).val();
				info["seq"] 	= $("input[name='seq']").eq(i).val();
				info["status"] 	= "0";		// 0:변경요청, 1:변경완료, 2:반려, 9:임시저장
				
				alVenCd.push(info);
				
				alVenCdSeq.push(info["venCd"] + "" + info["seq"]);
				
				chkCnt++;
				
				// 임시저장이 아닌 건수 확인
				if ($("input[name='status']").eq(i).val() != "9") {
					noneCnt++;
				}
				
				// MD 이메일 입력여부 체크
				if ($("input[name='mdEmail']").eq(i).val() == "") {
					noneEmail++;
				}
			}
		}
		
		// 선택항목 체크
		if (chkCnt <= 0) {
			alert("<spring:message code='msg.srm.alert.srmven0010.notChkCnt2' />");<%--변경요청 할 협력업체를 선택해주세요. --%>
			return;
		}
		
		// 임시저장이 아닌 건수 체크
		if (noneCnt > 0) {
			alert("<spring:message code='msg.srm.alert.srmven0010.notChkCnt3' />");<%--임시저장인 건만 변경요청이 가능합니다. --%>
			return;
		}
		
		// MD 이메일 입력여부 체크
		if (noneEmail > 0) {
			alert("<spring:message code='msg.srm.alert.srmven0010.notChkCnt4' />");<%--변경요청건 중에 MD 이메일을 입력하지 않은 건이 있습니다. --%>
			return;
		}
		
		dataInfo["proxyNm"]		= "MST1250";
		dataInfo["alVenCd"] 	= alVenCd;
		dataInfo["alVenCdSeq"] 	= alVenCdSeq;
		
		if (!confirm("<spring:message code='msg.srm.alert.approval.request' />")) { <%--변경요청 하시겠습니까? --%>
			return;
		}
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/ven/updateVenInfoConfirm.json"/>',
			data : JSON.stringify(dataInfo),
			success : function(data) {
				if (data.retMsg == "success") {
					alert("<spring:message code='msg.common.success.request' />");<%--요청처리가 성공적으로 수행되었습니다. --%>
					doSearch();
				} else if(data.retMsg == "fail_mdEmail") {
					alert("변경요청할 MD이메일 정보가 존재하지않습니다.\n 입력한 MD이메일을 확인해주세요.");
				}
			}
		});
	}
	
	/* 전체선택/취소 */
	function fnAllChecked() {
		$('input[type=checkbox]').each(function() {
			var id = $(this).attr("id");
			
			// 일괄적용 checkbox의 id는 pkg로 시작함
			if (id.substr(0, 3) == "pkg") {
				if ($("input[name='pkgAll']").is(":checked")) {
					$(this).prop("checked", true);
				} else {
					$(this).prop("checked", false);
				}
				
				// 개별선택 클릭 이벤트 호출(입력할 수 있는 componet명을 checkbox와 동일하게 설정)
				if (id == "pkgAddr") {
					fnSingleChecked(id, "btnZip");
				} else {
					fnSingleChecked(id, "new" + id.substr(3));
				}
			}
		});
	}
	
	/* 선택항목에 따른 속성 변경 */
	function fnSingleChecked(objNm, tarNm) {
		
		if (objNm == "pkgZzqcFg") {	// 인증정보 일 경우 해당수만큼 for문
			if ($("input[name='" + objNm + "']").is(":checked")) {
				for (var i = 1; i <= 11; i++) {
					$("input[name='" + tarNm + "" + i + "']").prop("disabled", false);
				}
			} else {
				for (var i = 1; i <= 11; i++) {
					$("input[name='" + tarNm + "" + i + "']").prop("disabled", true);
					$("input[name='" + tarNm + "" + i + "']").prop("checked", false);
				}
			}
		} else if (objNm == "pkgAddr") {
			
			if (!$("input[name='" + objNm + "']").is(":checked")) {
				$("input[name='" + tarNm + "']").prop("disabled", true);
				$("input[name='newAddr2']").prop("disabled", true);
				$("input[name='newZip']").val("");
				$("input[name='newAddr']").val("");
				$("input[name='newAddr2']").val("");
			} else {
				$("input[name='" + tarNm + "']").prop("disabled", false);
				$("input[name='newAddr2']").prop("disabled", false);
			}
			
		} else {	// 인증정보 이외의 항목
			if ($("input[name='" + objNm + "']").is(":checked")) {
				$("input[name='" + tarNm + "']").prop("disabled", false);
			} else {
				$("input[name='" + tarNm + "']").prop("disabled", true);
				$("input[name='" + tarNm + "']").val("");
				$("input[name='pkgAll']").attr("checked", false);
			}
		}
	}
	
	/* 선택한 파트너사 상세정보 팝업 */
	function doPresidPopup(venCd) {
		var site = "<c:url value='/edi/ven/presidInfoPopup.do'/>";
		var style = "dialogWidth: 860px; dialogHeight: 650px; center: no; status: no; scroll: no";
		var varParam = new Object();
		varParam.venCd = venCd;
		varParam.reLoad;
		
		var retVal = window.showModalDialog(site, varParam ,style);
		
		/* IE일 경우에만 로직 설정 */
		if (showModalDialogSupported) {
			// 조회
			doSearch();
		}
	}
	
	/* window.showModalDialog CallBack */
	function showModalDialogCallback(retVal) {
		// 조회
		doSearch();
	}
	
	/* 파트너사 정보 엑셀 다운로드 */
	function doExcel() {
		$("#hiddenForm").attr("action", "<c:url value='/edi/ven/selectExcelDownList.do'/>");
		$("#hiddenForm").submit();
	}
	
</script>

<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr class="r1">
		<td align="center">
			{%if status == '0'%}
				<input type="checkbox" id="chkSel" name="chkSel" disabled="disabled">
			{%else%}
				<input type="checkbox" id="chkSel" name="chkSel">
			{%/if%}
			
		</td>

		<td align="center" style="cursor:pointer;" onclick="doPresidPopup('<c:out value="\${venCd}"/>')"><b><c:out value="\${venCd}"/></b></td>
		
		{%if status == '0' || status == '9'%}
			<td align="center"><c:out value="\${modPresidNm}"/></td>
			<td align="left"><c:out value="\${modPresidEmail}"/></td>
			<td align="center"><c:out value="\${modDutyInf}"/></td>
			<td align="center"><c:out value="\${modHpNo1}"/></td>
			<td align="left"><c:out value="\${modEmail}"/></td>
		{%else%}
			<td align="center"><c:out value="\${presidNm}"/></td>
			<td align="left"><c:out value="\${presidEmail}"/></td>
			<td align="center"><c:out value="\${dutyInf}"/></td>
			<td align="center"><c:out value="\${hpNo1}"/></td>
			<td align="left"><c:out value="\${email}"/></td>
		{%/if%}
		
		<td align="center">
			{%if status == '9'%}
				<spring:message code='button.weborder.temporary.save' />	<%-- 임시저장 --%>
			{%elif status == '0'%}
				<spring:message code='text.srm.field.status0' />	<%-- 변경요청 --%>
			{%elif status == '1'%}
				<font color="blue"><spring:message code='text.srm.field.status1' /></font>	<%-- 변경완료 --%>
			{%elif status == '2'%}
				<font color="red"><spring:message code='button.common.return' /></font>	<%-- 반려 --%>
			{%/if%}
		</td>
		<td align="center">
			<c:out value="\${ifDt}"/>
			
			<input type="hidden" id="venCd" 		name="venCd" 			value="<c:out value="\${venCd}" />" 		/>
			<input type="hidden" id="seq" 			name="seq" 				value="<c:out value="\${seq}" />" 			/>
			<input type="hidden" id="status" 		name="status" 			value="<c:out value="\${status}" />" 		/>
			
			{%if status == '0' || status == '9'%}
				<input type="hidden" id="presidNm" 		name="presidNm" 		value="<c:out value="\${modPresidNm}" />" 		/>
				<input type="hidden" id="presidEmail" 	name="presidEmail" 		value="<c:out value="\${modPresidEmail}" />" 	/>
				<input type="hidden" id="repTelNo" 		name="repTelNo" 		value="<c:out value="\${modRepTelNo}" />" 		/>
				<input type="hidden" id="dutyInf" 		name="dutyInf" 			value="<c:out value="\${modDutyInf}" />" 		/>
				<input type="hidden" id="hpNo1" 		name="hpNo1" 			value="<c:out value="\${modHpNo1}" />" 			/>
				<input type="hidden" id="email" 		name="email" 			value="<c:out value="\${modEmail}" />" 			/>
				<input type="hidden" id="faxNo" 		name="faxNo" 			value="<c:out value="\${modFaxNo}" />" 			/>
				<input type="hidden" id="zip" 			name="zip" 				value="<c:out value="\${modZip}" />" 			/>
				<input type="hidden" id="addr" 			name="addr" 			value="<c:out value="\${modAddr}" />" 			/>
				<input type="hidden" id="addr2" 		name="addr2" 			value="<c:out value="\${modAddr2}" />" 			/>
				<input type="hidden" id="qcFg01" 		name="qcFg01" 			value="<c:out value="\${zzqcFg01}" />" 			/>
				<input type="hidden" id="qcFg02" 		name="qcFg02" 			value="<c:out value="\${zzqcFg02}" />" 			/>
				<input type="hidden" id="qcFg03" 		name="qcFg03" 			value="<c:out value="\${zzqcFg03}" />" 			/>
				<input type="hidden" id="qcFg04" 		name="qcFg04" 			value="<c:out value="\${zzqcFg04}" />" 			/>
				<input type="hidden" id="qcFg05" 		name="qcFg05" 			value="<c:out value="\${zzqcFg05}" />" 			/>
				<input type="hidden" id="qcFg06" 		name="qcFg06" 			value="<c:out value="\${zzqcFg06}" />" 			/>
				<input type="hidden" id="qcFg07" 		name="qcFg07" 			value="<c:out value="\${zzqcFg07}" />" 			/>
				<input type="hidden" id="qcFg08" 		name="qcFg08" 			value="<c:out value="\${zzqcFg08}" />" 			/>
				<input type="hidden" id="qcFg09" 		name="qcFg09" 			value="<c:out value="\${zzqcFg09}" />" 			/>
				<input type="hidden" id="qcFg10" 		name="qcFg10" 			value="<c:out value="\${zzqcFg10}" />" 			/>
				<input type="hidden" id="qcFg11" 		name="qcFg11" 			value="<c:out value="\${zzqcFg11}" />" 			/>
				<input type="hidden" id="qcFg12" 		name="qcFg12" 			value="<c:out value="\${zzqcFg12}" />" 			/>
				<input type="hidden" id="mdEmail" 		name="mdEmail" 			value="<c:out value="\${mdEmail}" />" 			/>
			{%else%}
				<input type="hidden" id="presidNm" 		name="presidNm" 		value="<c:out value="\${presidNm}" />" 		/>
				<input type="hidden" id="presidEmail" 	name="presidEmail" 		value="<c:out value="\${presidEmail}" />" 	/>
				<input type="hidden" id="repTelNo" 		name="repTelNo" 		value="<c:out value="\${repTelNo}" />" 		/>
				<input type="hidden" id="dutyInf" 		name="dutyInf" 			value="<c:out value="\${dutyInf}" />" 		/>
				<input type="hidden" id="hpNo1" 		name="hpNo1" 			value="<c:out value="\${hpNo1}" />" 		/>
				<input type="hidden" id="email" 		name="email" 			value="<c:out value="\${email}" />" 		/>
				<input type="hidden" id="faxNo" 		name="faxNo" 			value="<c:out value="\${faxNo}" />" 		/>
				<input type="hidden" id="zip" 			name="zip" 				value="<c:out value="\${zip}" />" 			/>
				<input type="hidden" id="addr" 			name="addr" 			value="<c:out value="\${addr}" />" 			/>
				<input type="hidden" id="addr2" 		name="addr2" 			value="<c:out value="\${addr2}" />" 		/>
				<input type="hidden" id="qcFg01" 		name="qcFg01" 			value="<c:out value="\${qcFg01}" />" 		/>
				<input type="hidden" id="qcFg02" 		name="qcFg02" 			value="<c:out value="\${qcFg02}" />" 		/>
				<input type="hidden" id="qcFg03" 		name="qcFg03" 			value="<c:out value="\${qcFg03}" />" 		/>
				<input type="hidden" id="qcFg04" 		name="qcFg04" 			value="<c:out value="\${qcFg04}" />" 		/>
				<input type="hidden" id="qcFg05" 		name="qcFg05" 			value="<c:out value="\${qcFg05}" />" 		/>
				<input type="hidden" id="qcFg06" 		name="qcFg06" 			value="<c:out value="\${qcFg06}" />" 		/>
				<input type="hidden" id="qcFg07" 		name="qcFg07" 			value="<c:out value="\${qcFg07}" />" 		/>
				<input type="hidden" id="qcFg08" 		name="qcFg08" 			value="<c:out value="\${qcFg08}" />" 		/>
				<input type="hidden" id="qcFg09" 		name="qcFg09" 			value="<c:out value="\${qcFg09}" />" 		/>
				<input type="hidden" id="qcFg10" 		name="qcFg10" 			value="<c:out value="\${qcFg10}" />" 		/>
				<input type="hidden" id="qcFg11" 		name="qcFg11" 			value="<c:out value="\${qcFg11}" />" 		/>
				<input type="hidden" id="qcFg12" 		name="qcFg12" 			value="<c:out value="\${qcFg12}" />" 		/>
				<input type="hidden" id="mdEmail" 		name="mdEmail" 			value="" 									/>
			{%/if%}
		</td>
	</tr>
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->

</head>

<body>

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:<c:out value="${param.widthSize }"/></c:if>>
		<div>
			<div id="wrap_menu">
				<!---------- 검색 Start  ------------------------------>
				<div class="wrap_con">
					<form id="searchForm" name="searchForm" method="post" action="#">
					<input type="hidden" name="vendor" value="<c:out value="${ven}" />">
					<div class="wrap_search">
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit"><spring:message code='epc.ord.searchCondition' /></li><%--검색조건 --%>
								<li class="btn">
									<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire" /></span></a>	<!-- 조회 -->
									<%-- <a href="#" class="btn" onclick="doExcel();"><span><spring:message code="button.common.excel" /></span></a> --%>	<!-- 엑셀 -->
									<%-- <a href="#" class="btn" onclick="javascript:printText();"><span><spring:message code="button.common.text" /></span></a> --%>	<!-- 텍스트 -->
								</li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
								<colgroup id="tbHead1">
									<col style="width: 15%" />
									<col style="width: 30%" />
									<col style="width: 10%" />
									<col style="" />
								</colgroup>
								<tr id="tbHead2">
									<th>
										<spring:message code='table.srm.srmven0030.colum.title4' /><%--파트너사코드 --%>
									</th>
									<td>
										<html:codeTag objId="srchVenCd" objName="srchVenCd" width="150px;" dataType="CP" comType="SELECT" formName="form" />
									</td>
									<th>
										<spring:message code='table.srm.srmven0010.colum.title1' /><%--확정상태 --%>
									</th>
									<td>
									<select id="srchStatus" name="srchStatus">
											<option value=""><spring:message code='text.srm.field.venCdAll' /></option><%--전체 --%>
											<option value="9"><spring:message code='button.srm.tempSave' 	/></option><%--임시저장 --%>
											<option value="0"><spring:message code='text.srm.field.status0' /></option><%--변경요청 --%>
											<option value="1"><spring:message code='text.srm.field.status1' /></option><%--변경완료 --%>
											<option value="2"><spring:message code='text.srm.field.status2' /></option><%--반려 --%>
										</select>
									</td>
								</tr>
							</table>													
						</div>
					</div>
					</form>
				</div>
				<!---------- 검색 End  ------------------------------>
				
				
				<!---------- 파트너사 정보 Start  ------------------------------>
				<p style="margin-top: 5px;" />
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit"><spring:message code='text.srm.field.srmevl003002.title' /></li><%--파트너사 정보 --%>
						
						<li class="btn">
							<a href="#" class="btn" onclick="doConfirm();"><span><spring:message code="button.srm.approval.request" /></span></a><%-- 변경요청 --%>
						</li>
					</ul>
					
					<div style="width:100%; height:250px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="headTbl">
							<colgroup>
								<col style="width: 40px;" />
								<col style="width: 70px;" />
								<col style="width: 80px;" />
								<col style="width: 150px;" />
								<col style="width: 80px;" />
								<col style="width: 80px;" />
								<col width="*" />
								<col style="width: 70px;" />
								<col style="width: 80px;" />
							</colgroup>
							<tr>
								<th><spring:message code='text.srm.field.select' /></th><%--선택 --%>
								<th><spring:message code='text.srm.field.venCompany' /></th><%--협력업체 --%>
								<th><spring:message code='text.srm.field.sellerCeoName' /></th><%--대표자명 --%>
								<th><spring:message code='text.srm.field.sellerCeoEmail' /></th><%--대표자이메일 --%>
								<th><spring:message code='text.srm.field.vMainName' /></th><%--담당자명 --%>
								<th><spring:message code='text.srm.field.vMobilePhone' /></th><%--휴대전화 --%>
								<th><spring:message code='text.srm.field.vEmail' /></th><%--이메일 --%>
								<th><spring:message code='text.srm.field.confirmStatus' /></th><%--확정여부 --%>
								<th><spring:message code='text.srm.field.confirmDate' /></th><%--확정일자 --%>
							</tr>
							
							<tbody id="dataListbody" />
							
						</table>
					</div>
				</div>
				<!---------- 파트너사 정보 End  ------------------------------>
				
				
				<!---------- 일괄적용 Start  ------------------------------>
				<p style="margin-top: 10px;" />
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit"><spring:message code='table.srm.srmven0010.colum.title2' /></li><%--일괄적용 항목 선택(수정할 협력업체를 선택 후 일괄적용 체크) --%>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col style="width: 100%" />
						</colgroup>
						<tr>
							<td>
								<input type="checkbox" id="pkgAll" name="pkgAll" onClick="fnAllChecked();"><spring:message code='text.srm.field.venCdAll' /><%--전체 --%>
								<%-- &nbsp;<input type="checkbox" id="pkgPresidNm" name="pkgPresidNm" onClick="fnSingleChecked(this.name, 'newPresidNm');"><spring:message code='text.srm.field.sellerCeoName' /> --%><%--대표자명 --%>
								&nbsp;<input type="checkbox" id="pkgPresidEmail" name="pkgPresidEmail" onClick="fnSingleChecked(this.name, 'newPresidEmail');"><spring:message code='text.srm.field.sellerCeoEmail' /><%--대표자 이메일 --%>
								&nbsp;<input type="checkbox" id="pkgRepTelNo" name="pkgRepTelNo" onClick="fnSingleChecked(this.name, 'newRepTelNo');"><spring:message code='text.srm.field.vPhone1' /><%--대표전화 --%>
								&nbsp;<input type="checkbox" id="pkgDutyInf" name="pkgDutyInf" onClick="fnSingleChecked(this.name, 'newDutyInf');"><spring:message code='text.srm.field.vMainName' /><%--담당자명 --%>
								&nbsp;<input type="checkbox" id="pkgHpNo1" name="pkgHpNo1" onClick="fnSingleChecked(this.name, 'newHpNo1');"><spring:message code='text.srm.field.vMobilePhone' /><%--휴대전화 --%>
								&nbsp;<input type="checkbox" id="pkgEmail" name="pkgEmail" onClick="fnSingleChecked(this.name, 'newEmail');"><spring:message code='text.srm.field.vEmail' /><%--이메일 --%>
								&nbsp;<input type="checkbox" id="pkgFaxNo" name="pkgFaxNo" onClick="fnSingleChecked(this.name, 'newFaxNo');"><spring:message code='text.srm.field.faxFhone' /><%--FAX --%>
								&nbsp;<input type="checkbox" id="pkgAddr" name="pkgAddr" onClick="fnSingleChecked(this.name, 'btnZip');"><spring:message code='text.srm.field.address' /><%--주소 --%>
								&nbsp;<input type="checkbox" id="pkgZzqcFg" name="pkgZzqcFg" onClick="fnSingleChecked(this.name, 'newZzqcFg');"><spring:message code='text.srm.field.certiInfo1' /><%--인증정보 --%>
								&nbsp;<input type="checkbox" id="pkgMdEmail" name="pkgMdEmail" onClick="fnSingleChecked(this.name, 'newMdEmail');"><spring:message code='text.srm.field.mdEmail' /><%--MD이메일 --%>
							</td>
						</tr>
						<tr>
							<td>
								<form name="modifyForm" id="modifyForm">		
				
								<div class="bbs_search">
									<ul class="tit">
										<li class="tit"><spring:message code='table.srm.srmven0010.colum.title3' /></li><%--정보변경 --%>
										
										<li class="btn">
											<a href="#" class="btn" onclick="doSave();"><span><spring:message code="button.common.setall" /></span></a><%-- 일괄적용 --%>
										</li>
									</ul>
									
									
									<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
										<colgroup>
											<col style="width:15%" />
											<col style="width:30%" />
											<col style="width:15%" />
											<col width="*" />
										</colgroup>
										
										<!-- <tr>
											<th>사업자 등록번호</th>
											<td>
												<spin id="spBmanNo" />
											</td>
											<th>상호명</th>
											<td>
												<spin id="spVenNm" />
											</td>
										</tr> -->
										<tr>
											<%-- <th><label for="newPresidNm">*<spring:message code='text.srm.field.sellerCeoName' /></label></th>대표자명
											<td>
												<input type="text" id="newPresidNm" name="newPresidNm" maxlength="30" disabled="disabled" />
											</td> --%>
											<th><label for="newPresidEmail">*<spring:message code='text.srm.field.sellerCeoEmail' /></label></th><%--대표자 이메일 --%>
											<td colspan="3">
												<input type="text" id="newPresidEmail" name="newPresidEmail" style="width: 50%;" disabled="disabled" />
											</td>
										</tr>
										<tr>
											<th><label for="newDutyInf">*<spring:message code='text.srm.field.vMainName' /></label></th><%--담당자명 --%>
											<td>
												<input type="text" id="newDutyInf" name="newDutyInf" disabled="disabled" />
											</td>
											<th><label for="newRepTelNo">*<spring:message code='text.srm.field.vPhone1' /></label></th><%--대표전화 --%>
											<td>
												<input type="text" id="newRepTelNo" name="newRepTelNo" class="numberic" disabled="disabled" />
											</td>
										</tr>
										<tr>
											<th><label for="newHpNo1">*<spring:message code='text.srm.field.vMobilePhone' /></label></th><%--휴대전화 --%>
											<td>
												<input type="text" id="newHpNo1" name="newHpNo1" class="numberic" disabled="disabled" />
											</td>
											<th><label for="newFaxNo"><spring:message code='text.srm.field.faxFhone' /></label></th><%--FAX --%>
											<td>
												<input type="text" id="newFaxNo" name="newFaxNo" class="numberic" disabled="disabled" />
											</td>
										</tr>
										<tr>
											<th><label for="newEmail">*<spring:message code='text.srm.field.vEmail' /></label></th><%--이메일 --%>
											<td colspan="3">
												<input type="text" id="newEmail" name="newEmail" style="width: 50%;" disabled="disabled" />
											</td>
										</tr>
										<tr>
											<th><label for="newAddr2">*<spring:message code='text.srm.field.address' /></label></th><%--주소 --%>
											<td colspan="3">
												<input type="text" id="newZip" name="newZip" style="width:50PX; text-align:center;" disabled="disabled" />
												<input type="button" id="btnZip" name="btnZip" onclick="javascript:openZipCodeNew();" value="<spring:message code='button.srm.find'/>" style="cursor:pointer" disabled="disabled" />
												<input type="text" id="newAddr" name="newAddr" style="width:250px;" disabled="disabled" />
												<input type="text" id="newAddr2" name="newAddr2" style="width:200px;" disabled="diasbled" />
											</td>
										</tr>
										<tr>
											<th><label><spring:message code='text.srm.field.certiInfo1' /></label></th><%--인증정보 --%>
											<td colspan="3">
												<input type="checkbox" id="newZzqcFg1" name="newZzqcFg1" disabled="disabled" /><spring:message code='checkbox.srm.zzqcFg1' /><%--HACCP --%> 
												<input type="checkbox" id="newZzqcFg2" name="newZzqcFg2" disabled="disabled" /><spring:message code='checkbox.srm.zzqcFg2' /><%--FSSC22000 --%> 
												<input type="checkbox" id="newZzqcFg3" name="newZzqcFg3" disabled="disabled" /><spring:message code='checkbox.srm.zzqcFg3' /><%--ISO22000 --%> 
												<input type="checkbox" id="newZzqcFg4" name="newZzqcFg4" disabled="disabled" /><spring:message code='checkbox.srm.zzqcFg4' /><%--GMP인증 --%> 
												<input type="checkbox" id="newZzqcFg5" name="newZzqcFg5" disabled="disabled" /><spring:message code='checkbox.srm.zzqcFg5' /><%--KS인증 --%> 
												
												<input type="checkbox" id="newZzqcFg6" name="newZzqcFg6" disabled="disabled" /><spring:message code='checkbox.srm.zzqcFg6' /><%--우수농산물GAP인증 --%> 
												<br>
												<input type="checkbox" id="newZzqcFg7" name="newZzqcFg7" disabled="disabled" /><spring:message code='checkbox.srm.zzqcFg7' /><%--유기가공식품인증 --%> 
												<input type="checkbox" id="newZzqcFg8" name="newZzqcFg8" disabled="disabled" /><spring:message code='checkbox.srm.zzqcFg8' /><%--전통식품품질인증 --%> 
												<input type="checkbox" id="newZzqcFg9" name="newZzqcFg9" disabled="disabled" /><spring:message code='checkbox.srm.zzqcFg9' /><%--ISO_9001 --%> 
												<input type="checkbox" id="newZzqcFg10" name="newZzqcFg10" disabled="disabled" /><spring:message code='checkbox.srm.zzqcFg10' /><%--수산물품질인증 --%> 
											
												<input type="checkbox" id="newZzqcFg11" name="newZzqcFg11" disabled="disabled" /><spring:message code='checkbox.srm.zzqcFg11' /><%--PAS_220 --%> 
												<!-- <br>
												기타품질인증텍스트 <input type="text" id="zzqcFg12" name="zzqcFg12" style="width: 50%;" /> -->
											</td>
										</tr>
										<tr>
											<th><label for="newMdEmail"><spring:message code='text.srm.field.mdEmail' /></label></th><%--MD이메일 --%>
											<td colspan="3">
												<input type="text" id="newMdEmail" name="newMdEmail" style="width: 50%;" disabled="disabled" /><br>
												<span style="color:blue;"><spring:message code='text.srm.field.srmven0010Notice1'/></span><%--변경요청시 담당MD에게 E-mail 발송용 --%>
											</td>
										</tr>
									</table>
								</div>
								</form>
								<p style="margin-bottom: 5px;" />
							</td>
						</tr>
					</table>
					
				</div>
				<!---------- 일괄적용 End  ------------------------------>
				
			</div>
		</div>
		
		<form id="hiddenForm" name="hiddenForm" method="post">
			<input type="hidden" id="venCd" name="venCd">
		</form>

		<!-- footer -->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="notice"></div>
				<div class="location">
					<ul>
						<li><spring:message code='table.srm.srmven0010.colum.title4' /></li><%--홈 --%>
						<li><spring:message code='table.srm.srmven0010.colum.title5' /></li><%--SRM정보 --%>
						<li><spring:message code='table.srm.srmven0010.colum.title6' /></li><%--정보관리 --%>
						<li class="last"><spring:message code='table.srm.srmven0010.colum.title7' /></li><%--파트너사 정보관리 --%>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
</body>
</html>
