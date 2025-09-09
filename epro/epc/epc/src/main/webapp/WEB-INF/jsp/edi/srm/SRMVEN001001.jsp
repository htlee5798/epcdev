<%@ page contentType="text/html; charset=UTF-8"%>

<%--
	Class Name : SRMVEN001001.jsp    
	Description : SRM > SRM 정보 > 파트너사 상세정보 팝업
	Modification Information
	
	  수정일 			  수정자 						수정내용
	---------- 		-----------   	-------------------------------------
	2016.08.09 		SHIN SE JIN 	최초생성
	
	author   : SHIN SE JIN
	since    : 2016.08.09
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@include file="../common.jsp"%>
	<%@ include file="/common/edi/ediCommon.jsp"%>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<script type="text/javascript" src="/js/epc/showModalDialogCallee.js"></script>

<script>
	var varParam;
	
	$(document).ready(function() {
		getDialogArguments();
		
		varParam = window.dialogArguments;
		
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
		
		//searchInfo["venCd"]	= '<c:out value="${param.venCd}"/>';		// 협력업체
		searchInfo["venCd"] = varParam.venCd;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/ven/selectSrmVenList.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				//json 으로 호출된 결과값을 화면에 Setting 
				_setTbodyMasterValue(data);
				disabledTrue();	// 입력폼 막기
				resetForm();	// 입력폼 초기화
			}
		});
	}
	
	/* doSearch() 후처리(data 객체 그리기) */
	function _setTbodyMasterValue(data) {
		setTbodyInit("dataListbody"); // dataList 초기화
		
		var cnt = 0;
		if (data.length > 0) {
			for(var i = 0; i < data.length; i++) {
				data[i].rnum = i+1;
				
				if (data[i].status == '9' || data[i].status == '0') {	// 리스트중 상태값이 0:변경요청 이거나 9:임시저장 일경우 카운트 증가
					cnt++;
				}
			}
			
			if (cnt > 0) {				// 상태값이 0이나 9가 있을 경우
				$("#btnNew").hide();	//신규버튼 감추기
			} else {
				$("#btnNew").show();	//신규버튼 보이기
			}
			
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		} else {
			setTbodyNoResult("dataListbody", 8);
		}
	}
	
	/* row select */
	function onSelectRow(rnum) {
		
		resetForm();	//입력폼 초기화
		
		$("#headTbl tr").css("background-color","");					// row background-color 초기화
		$("#headTbl tr:eq("+rnum+")").css("background-color","#efefef");	// row background-color 변경
		
		$("input[name='venCd']").val($("input[name='subvenCd']").eq(rnum-1).val());										// 협력업체
		$("input[name='seq']").val($("input[name='subseq']").eq(rnum-1).val());											// 순번
		//$("input[name='presidNm']").val($("input[name='subpresidNm']").eq(rnum-1).val());								// 대표자명
		$("input[name='presidEmail']").val($("input[name='subpresidEmail']").eq(rnum-1).val());							// 대표자 이메일
		$("input[name='repTelNo']").val($("input[name='subrepTelNo']").eq(rnum-1).val());								// 대표전화
		$("input[name='dutyInf']").val($("input[name='subdutyInf']").eq(rnum-1).val());									// 담당자명
		$("input[name='hpNo1']").val($("input[name='subhpNo1']").eq(rnum-1).val());										// 휴대전화
		$("input[name='faxNo']").val($("input[name='subfaxNo']").eq(rnum-1).val());										// 팩스번호
		$("input[name='email']").val($("input[name='subemail']").eq(rnum-1).val());										// 이메일
		$("input[name='mdEmail']").val($("input[name='submdEmail']").eq(rnum-1).val());									// MD이메일
		$("input[name='zip']").val($("input[name='subzip']").eq(rnum-1).val());											// 우편번호
		$("input[name='addr']").val($("input[name='subaddr']").eq(rnum-1).val());										// 주소
		$("input[name='addr2']").val($("input[name='subaddr2']").eq(rnum-1).val());										// 상세주소
		$("input[name='status']").val($("input[name='substatus']").eq(rnum-1).val());									// 상태값(0:변경요청, 1:변경완료, 2:반려, 9:임시저장)
		if ($("input[name='subzzqcFg1']").eq(rnum-1).val() == "X") $("input[name='zzqcFg1']").prop("checked",true);		// HACCP 
		if ($("input[name='subzzqcFg2']").eq(rnum-1).val() == "X") $("input[name='zzqcFg2']").prop("checked",true);		// FSSC22000
		if ($("input[name='subzzqcFg3']").eq(rnum-1).val() == "X") $("input[name='zzqcFg3']").prop("checked",true);		// ISO22000
		if ($("input[name='subzzqcFg4']").eq(rnum-1).val() == "X") $("input[name='zzqcFg4']").prop("checked",true);		// GMP인증  
		if ($("input[name='subzzqcFg5']").eq(rnum-1).val() == "X") $("input[name='zzqcFg5']").prop("checked",true);		// KS인증    
		if ($("input[name='subzzqcFg6']").eq(rnum-1).val() == "X") $("input[name='zzqcFg6']").prop("checked",true);		// 우수농산물GAP인증
		if ($("input[name='subzzqcFg7']").eq(rnum-1).val() == "X") $("input[name='zzqcFg7']").prop("checked",true);		// 유기가공식품인증         
		if ($("input[name='subzzqcFg8']").eq(rnum-1).val() == "X") $("input[name='zzqcFg8']").prop("checked",true);		// 전통식품품질인증
		if ($("input[name='subzzqcFg9']").eq(rnum-1).val() == "X") $("input[name='zzqcFg9']").prop("checked",true);		// ISO_9001
		if ($("input[name='subzzqcFg10']").eq(rnum-1).val() == "X") $("input[name='zzqcFg10']").prop("checked",true);	// 수산물품질인증
		if ($("input[name='subzzqcFg11']").eq(rnum-1).val() == "X") $("input[name='zzqcFg11']").prop("checked",true);	// PAS_220
		
		if ($("input[name='substatus']").eq(rnum-1).val() == '9') {	// 상태값이 9(임시저장) 일때 입력폼 disabled false, 버튼(변경요청, 저장, 삭제) 보여주기
			disabledFalse()	// 입력폼 풀기
		} else {
			disabledTrue();	// 입력폼 막기
		}
		
	}
	
	/* 입력폼 막기 */
	function disabledTrue() {	// 입력폼 disabled true, 버튼(변경요청, 저장, 삭제) 감추기
		
		$("input[type='text']").prop("disabled", true);		
		$("input[type='checkbox']").prop("disabled", true);
		$("input[type='button']").prop("disabled", true);
		$("#btnRequest").hide();
		$("#btnSave").hide();
		$("#btnDelete").hide();
		
	}
	
	/* 입력폼 풀기 */
	function disabledFalse() {	// 입력폼 disabled false, 버튼(변경요청, 저장, 삭제) 보여주기
		
		$("input[type='text']").prop("disabled", false);
		$("input[type='checkbox']").prop("disabled", false);
		$("input[type='button']").prop("disabled", false);
		$("input[name='zip'], input[name='addr']").prop("disabled", true);
		$("#btnRequest").show();
		$("#btnSave").show();
		$("#btnDelete").show();
		
	}
	
	/* 입력폼 초기화 */
	function resetForm() {
		$("#MyForm").find("input").each(function() {
			
			if (this.type == "text") {
				$("input[name='" + this.name + "']").val("");
				
			} else if (this.type == "checkbox") {
				var id = $(this).attr("id");
				
				if (id.substr(0, 3) == "zzq") {	// zzq로 시작하는 체크박스를 체크를 해제
					$(this).prop("checked", false);
				}
			}
			
		});
	}
	
	/* 신규 */
	function doNew() {
		
		resetForm();																						   		//입력폼 초기화
		$("#headTbl tr").css("background-color","");														   		// row background-color 초기화
		
		$("input[name='venCd']").val($("input[name='subvenCd']").eq(0).val());                                 		// 협력업체                               
		$("input[name='seq']").val($("input[name='subseq']").eq(0).val());                                     		// 순번                                 
		//$("input[name='presidNm']").val($("input[name='subpresidNm']").eq(0).val());                          		// 대표자명                               
		$("input[name='presidEmail']").val($("input[name='subpresidEmail']").eq(0).val());                     		// 대표자 이메일                            
		$("input[name='repTelNo']").val($("input[name='subrepTelNo']").eq(0).val());                           		// 대표전화                               
		$("input[name='dutyInf']").val($("input[name='subdutyInf']").eq(0).val());                             		// 담당자명                               
		$("input[name='hpNo1']").val($("input[name='subhpNo1']").eq(0).val());                                 		// 휴대전화                               
		$("input[name='faxNo']").val($("input[name='subfaxNo']").eq(0).val());                                 		// 팩스번호                               
		$("input[name='email']").val($("input[name='subemail']").eq(0).val());                                 		// 이메일                                
		$("input[name='mdEmail']").val($("input[name='submdEmail']").eq(0).val());                             		// MD이메일                              
		$("input[name='zip']").val($("input[name='subzip']").eq(0).val());                                  		// 우편번호                               
		$("input[name='addr']").val($("input[name='subaddr']").eq(0).val());                                		// 주소                                 
		$("input[name='addr2']").val($("input[name='subaddr2']").eq(0).val());                              		// 상세주소                               
		$("input[name='status']").val($("input[name='substatus']").eq(0).val());                               		// 상태값(0:변경요청, 1:변경완료, 2:반려, 9:임시저장)  
		if ($("input[name='subzzqcFg1']").eq(0).val() == "X") $("input[name='zzqcFg1']").prop("checked",true);		// HACCP                              
		if ($("input[name='subzzqcFg2']").eq(0).val() == "X") $("input[name='zzqcFg2']").prop("checked",true);		// FSSC22000                          
		if ($("input[name='subzzqcFg3']").eq(0).val() == "X") $("input[name='zzqcFg3']").prop("checked",true);		// ISO22000                           
		if ($("input[name='subzzqcFg4']").eq(0).val() == "X") $("input[name='zzqcFg4']").prop("checked",true);		// GMP인증                              
		if ($("input[name='subzzqcFg5']").eq(0).val() == "X") $("input[name='zzqcFg5']").prop("checked",true);		// KS인증                               
		if ($("input[name='subzzqcFg6']").eq(0).val() == "X") $("input[name='zzqcFg6']").prop("checked",true);		// 우수농산물GAP인증                         
		if ($("input[name='subzzqcFg7']").eq(0).val() == "X") $("input[name='zzqcFg7']").prop("checked",true);		// 유기가공식품인증                           
		if ($("input[name='subzzqcFg8']").eq(0).val() == "X") $("input[name='zzqcFg8']").prop("checked",true);		// 전통식품품질인증                           
		if ($("input[name='subzzqcFg9']").eq(0).val() == "X") $("input[name='zzqcFg9']").prop("checked",true);		// ISO_9001                           
		if ($("input[name='subzzqcFg10']").eq(0).val() == "X") $("input[name='zzqcFg10']").prop("checked",true);	// 수산물품질인증                            
		if ($("input[name='subzzqcFg11']").eq(0).val() == "X") $("input[name='zzqcFg11']").prop("checked",true);	// PAS_220
		
		$("input[type='text']").prop("disabled", false);
		$("input[type='checkbox']").prop("disabled", false);
		$("input[type='button']").prop("disabled", false);
		$("input[name='zip'], input[name='addr']").prop("disabled", true);
		$("#btnRequest").hide();	//변경요청 감추기
		$("#btnSave").show();		//저장 보이기
		$("#btnDelete").hide();		//삭제 감추기
		
	}
	
	/* 변경요청 */ 
	function doRequest() {
		
		var dataInfo = {};
		dataInfo["venCd"]	= $("input[name='venCd']").val();
		dataInfo["seq"]		= $("input[name='seq']").val();
		dataInfo["status"]	= '0';
		
		var alVenCd = new Array();
		var alVenCdSeq = new Array();
		
		alVenCd.push(dataInfo);
		alVenCdSeq.push(dataInfo["venCd"] + "" + dataInfo["seq"]);
		
		var data = {};
		
		data["proxyNm"]	= "MST1250";
		data["alVenCd"]	= alVenCd;
		data["alVenCdSeq"]	= alVenCdSeq;
		
		if (!confirm("<spring:message code='msg.common.confirm.appr.req' />")) {<%--변경요청 하시겠습니까? --%>
			return;
		}
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/ven/updateVenInfoConfirm.json"/>',
			data : JSON.stringify(data),
			success : function(data) {
				if (data.retMsg == "success") {
					alert("<spring:message code='msg.common.success.request' />");<%--요청처리가 성공적으로 수행되었습니다. --%>
					doSearch();
				}
			}
		});
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
	
	/* 저장  */
	function doSave() {
		
		var regVar = "^[a-zA-Z0-9!$&*.=^`|~#%'+\/?_{}-]+@([a-zA-Z0-9_-]+[\.])+([a-zA-Z])";	// 이메일 형식
		
		<%-- var target = "<spring:message code='text.srm.field.sellerCeoName'/>";	// 대표자명
		if (!$.trim($('input[name="presidNm"]').val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")대표자명을(를) 입력하세요.
			$('input[name="presidNm"]').focus();
			return;
		} --%>
		
		/* if (!cal_3byte($('input[name="presidNm"]').val(), '30', setPermitMsg(target), setByteMsg(target, '30'))) {
			$('input[name="presidNm"]').focus();
			return;
		} */
		
		target = "<spring:message code='text.srm.field.sellerCeoEmail'/>";	// 대표자 이메일
		if (!$.trim($('input[name="presidEmail"]').val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--대표자 이메일을(를) 입력하세요. --%>
			$('input[name="presidEmail"]').focus();
			return;
		}
		
		if(new RegExp(regVar).test($('input[name="presidEmail"]').val()) == false) {
			alert("<spring:message code='msg.srm.alert.validation.vEmail'/>");<%--이메일 형식이 아닙니다. --%>
			$('input[name="presidEmail"]').focus();
			return;
		}
		
		if (!cal_3byte($('input[name="presidEmail"]').val(), '200', setPermitMsg(target), setByteMsg(target, '200'))) {
			$('input[name="presidEmail"]').focus();
			return;
		}
		
		target = "<spring:message code='text.srm.field.vMainName'/>";	// 담당자명
		if (!$.trim($('input[name="dutyInf"]').val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--담당자명을(를) 입력하세요. --%>
			$('input[name="dutyInf"]').focus();
			return;
		}
		
		if (!cal_3byte($('input[name="dutyInf"]').val(), '100', setPermitMsg(target), setByteMsg(target, '100'))) {
			$('input[name="dutyInf"]').focus();
			return;
		}
		
		target = "<spring:message code='text.srm.field.vPhone1'/>";	// 대표전화
		if (!$.trim($('input[name="repTelNo"]').val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--대표전화을(를) 입력하세요. --%>
			$('input[name="repTelNo"]').focus();
			return;
		}
		
		if (!cal_3byte($('input[name="repTelNo"]').val(), '30', setPermitMsg(target), setByteMsg(target, '30'))) {
			$('input[name="repTelNo"]').focus();
			return;
		}
		
		target = "<spring:message code='text.srm.field.vMobilePhone'/>";	// 휴대전화
		if (!$.trim($('input[name="hpNo1"]').val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--휴대전화을(를) 입력하세요. --%>
			$('input[name="hpNo1"]').focus();
			return;
		}
		
		if (!cal_3byte($('input[name="hpNo1"]').val(), '30', setPermitMsg(target), setByteMsg(target, '30'))) {
			$('input[name="hpNo1"]').focus();
			return;
		}
		
		target = "<spring:message code='text.srm.field.vEmail'/>";	// 이메일
		if (!$.trim($('input[name="email"]').val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--이메일을(를) 입력하세요. --%>
			$('input[name="email"]').focus();
			return;
		}
		
		if(new RegExp(regVar).test($('input[name="email"]').val()) == false) {
			alert("<spring:message code='msg.srm.alert.validation.vEmail'/>");<%--이메일 형식이 아닙니다. --%>
			$('input[name="email"]').focus();
			return;
		}
		
		if (!cal_3byte($('input[name="email"]').val(), '200', setPermitMsg(target), setByteMsg(target, '200'))) {
			$('input[name="email"]').focus();
			return;
		}
		
		target = "<spring:message code='text.srm.field.address'/>";	// 주소	
		if (!$.trim($('input[name="zip"]').val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--주소을(를) 입력하세요. --%>
			$('input[name="zip"]').focus();
			return;
		}
		
		if (!cal_3byte($('input[name="zip"]').val(), '10', setPermitMsg(target), setByteMsg(target, '10'))) {
			$('input[name="zip"]').focus();
			return;
		}
		
		if (!$.trim($('input[name="addr"]').val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--주소을(를) 입력하세요. --%>
			$('input[name="addr"]').focus();
			return;
		}
		
		if (!cal_3byte($('input[name="addr"]').val(), '120', setPermitMsg(target), setByteMsg(target, '120'))) {
			$('input[name="addr"]').focus();
			return;
		}
		
		if (!$.trim($('input[name="addr2"]').val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--주소을(를) 입력하세요. --%>
			$('input[name="addr2"]').focus();
			return;
		}
		
		if (!cal_3byte($('input[name="addr2"]').val(), '120', setPermitMsg(target), setByteMsg(target, '120'))) {
			$('input[name="addr2"]').focus();
			return;
		}
		
		target = "<spring:message code='text.srm.field.mdEmail'/>";	// MD이메일
		if (!$.trim($('input[name="mdEmail"]').val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--MD이메일을(를) 입력하세요. --%>
			$('input[name="mdEmail"]').focus();
			return;
		}
		
		if(new RegExp(regVar).test($('input[name="mdEmail"]').val()) == false) {
			alert("<spring:message code='msg.srm.alert.validation.vEmail'/>");<%--이메일 형식이 아닙니다. --%>
			$('input[name="mdEmail"]').focus();
			return;
		}
		
		if (!cal_3byte($('input[name="mdEmail"]').val(), '200', setPermitMsg(target), setByteMsg(target, '200'))) {
			$('input[name="mdEmail"]').focus();
			return;
		}
		
		var dataInfo = {};
		
		dataInfo["venCd"]			= $('input[name="venCd"]').val();			// 협력업체
		dataInfo["seq"]				= $('input[name="seq"]').val();				// 순번
		dataInfo["status"]			= $('input[name="status"]').val();			// 상태
		dataInfo["modPresidNm"]		= $('input[name="presidNm"]').val();		// 대표자명
		dataInfo["modPresidEmail"]	= $('input[name="presidEmail"]').val();		// 대표자이메일
		dataInfo["modRepTelNo"]		= $('input[name="repTelNo"]').val();		// 대표전화
		dataInfo["modDutyInf"]		= $('input[name="dutyInf"]').val();			// 담당자명
		dataInfo["modHpNo1"]		= $('input[name="hpNo1"]').val();			// 휴대전화
		dataInfo["modFaxNo"]		= $('input[name="faxNo"]').val();			// 팩스
		dataInfo["modEmail"]		= $('input[name="email"]').val();			// 이메일
		dataInfo["mdEmail"]			= $('input[name="mdEmail"]').val();			// MD이메일
		dataInfo["modZip"]			= $('input[name="zip"]').val();				// 우편번호
		dataInfo["modAddr"]			= $('input[name="addr"]').val();			// 주소
		dataInfo["modAddr2"]		= $('input[name="addr2"]').val();			// 상세주소
		
		for (var i = 0; i < $("#MyForm input[type='checkbox']").length; i++) {	// 인증정보
			if ($("#MyForm input[type='checkbox']").eq(i).is(":checked")) {
				var name = $("#MyForm input[type='checkbox']").eq(i).attr("name");
				dataInfo[name] = 'X';
			}
		}
		
		var alVenCd = new Array();	// Array생성
		
		alVenCd.push(dataInfo);		// Array에 넘길정보 넣기
		
		var data = {};
		
		data["alVenCd"] = alVenCd;
		
		if (!confirm("<spring:message code='msg.srm.alert.tempSave' />")) { <%--저장하시겠습니까? --%>
			return;
		}
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/ven/insertVenInfo.json"/>',
			data : JSON.stringify(data),
			success : function(data) {
				if (data.retMsg == "success") {
					alert("<spring:message code='msg.srm.alert.saveOk' />");<%--저장 되었습니다. --%>
					doSearch();
				}
			}
		});
	}
	
	/* 삭제하기 */
	function doDelete() {
		
		var dataInfo = {};
		
		dataInfo["venCd"]	= $('input[name="venCd"]').val();			// 협력업체
		dataInfo["seq"]		= $('input[name="seq"]').val();				// 순번
		
		var alVenCd = new Array();
		
		alVenCd.push(dataInfo);
		
		var data = {};
		
		data["alVenCd"] = alVenCd;
		
		if (!confirm("<spring:message code='msg.srm.alert.confirmDelete' />")) { <%--삭제하시겠습니까? --%>
			return;
		}
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/ven/deleteVenInfo.json"/>',
			data : JSON.stringify(data),
			success : function(data) {
				if (data.retMsg == "success") {
					alert("<spring:message code='msg.srm.alert.deleteOk' />");<%--삭제 되었습니다. --%>
					doSearch();
				}
			}
		});
	}
	
	/* 우편번호 찾기 */
	function openZipCodeNew() {
		var cw=500;
		var ch=450;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		
		window.open("<c:url value='/edi/srm/selectJusoPopup.do'/>","zipPopup","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
	}
	
	/* 주소 찾은 값 넣어주기 */
	function receiveValue(zipCd, juso) {
		$("input[name='zip']").val(zipCd);
		$("input[name='addr']").val(juso);
		$("input[name='addr2']").val("");
	}
	
	/* 상세보기 팝업창 닫기 */
	function popupClose() {
		parent.window.close();
	}
	
	/* 윈도우 종료 시 부모창으로 값 전달(body에 설정) */
	function windowClose() {
		var retVal = new Array();
		//retVal[0] = "OK";
		//retVal[1] = "OK2";
		
		setReturnValue(retVal);
		
		parent.window.returnValue = retVal;
		parent.window.close();
	}
	
</script>

<script id="dataListTemplate" type="text/x-jquery-tmpl">
	<tr style="cursor: pointer;" onclick="onSelectRow('<c:out value="\${rnum}"/>')">

		{%if rnum == '1'%}
			<td align="center"><spring:message code='table.srm.srmven001001.colum.title1'/></td><%--최종 --%>
		{%elif rnum != '1'%}
			<td align="center"><c:out value="\${rnum-1}" /></td>
		{%/if%}

		<td align="center"><c:out value="\${presidNm}"/></td>
		<td align="center"><c:out value="\${presidEmail}"/></td>
		<td align="center"><c:out value="\${dutyInf}"/></td>
		<td align="center"><c:out value="\${hpNo1}"/></td>
		<td align="center"><c:out value="\${email}"/></td>
		<td align="center">
			{%if seq != '99999'%}
				{%if status == '9'%}
					<spring:message code="text.srm.field.noStatus" /><%--임시저장 --%>
				{%elif status == '0'%}
					<spring:message code="text.srm.field.status0" /><%--변경요청 --%>
				{%elif status == '1'%}
					<font color="blue"><spring:message code="text.srm.field.status1" /></font><%--변경완료 --%>
				{%elif status == '2'%}
					<font color="red"><spring:message code="text.srm.field.status2" /></font><%--반려 --%>
				{%/if%}
			{%/if%}
		</td>
		<td align="center">
			{%if seq != '99999'%}
				<c:out value="\${ifDt}"/>
			{%/if%}
			
			<input type="hidden" id="subvenCd" 			name="subvenCd"			value="<c:out value="\${venCd}" />" />
			<input type="hidden" id="subseq" 			name="subseq"			value="<c:out value="\${seq}" />" />
			<input type="hidden" id="subpresidNm" 		name="subpresidNm"		value="<c:out value="\${presidNm}" />" />
			<input type="hidden" id="subpresidEmail" 	name="subpresidEmail"	value="<c:out value="\${presidEmail}" />" />
			<input type="hidden" id="subrepTelNo" 		name="subrepTelNo"		value="<c:out value="\${repTelNo}" />" />
			<input type="hidden" id="subdutyInf" 		name="subdutyInf"		value="<c:out value="\${dutyInf}" />" />
			<input type="hidden" id="subhpNo1" 			name="subhpNo1"			value="<c:out value="\${hpNo1}" />" />
			<input type="hidden" id="subemail" 			name="subemail"			value="<c:out value="\${email}" />" />
			<input type="hidden" id="submdEmail" 		name="submdEmail"		value="<c:out value="\${mdEmail}" />" />
			<input type="hidden" id="subfaxNo" 			name="subfaxNo"			value="<c:out value="\${faxNo}" />" />
			<input type="hidden" id="subzip" 			name="subzip"			value="<c:out value="\${zip}" />" />
			<input type="hidden" id="subaddr" 			name="subaddr"			value="<c:out value="\${addr}" />" />
			<input type="hidden" id="subaddr2" 			name="subaddr2"			value="<c:out value="\${addr2}" />" />
			<input type="hidden" id="subzzqcFg1" 		name="subzzqcFg1"		value="<c:out value="\${zzqcFg1}" />" />
			<input type="hidden" id="subzzqcFg2" 		name="subzzqcFg2"		value="<c:out value="\${zzqcFg2}" />" />
			<input type="hidden" id="subzzqcFg3" 		name="subzzqcFg3"		value="<c:out value="\${zzqcFg3}" />" />
			<input type="hidden" id="subzzqcFg4" 		name="subzzqcFg4"		value="<c:out value="\${zzqcFg4}" />" />
			<input type="hidden" id="subzzqcFg5" 		name="subzzqcFg5"		value="<c:out value="\${zzqcFg5}" />" />
			<input type="hidden" id="subzzqcFg6" 		name="subzzqcFg6"		value="<c:out value="\${zzqcFg6}" />" />
			<input type="hidden" id="subzzqcFg7" 		name="subzzqcFg7"		value="<c:out value="\${zzqcFg7}" />" />
			<input type="hidden" id="subzzqcFg8" 		name="subzzqcFg8"		value="<c:out value="\${zzqcFg8}" />" />
			<input type="hidden" id="subzzqcFg9" 		name="subzzqcFg9"		value="<c:out value="\${zzqcFg9}" />" />
			<input type="hidden" id="subzzqcFg10" 		name="subzzqcFg10"		value="<c:out value="\${zzqcFg10}" />" />
			<input type="hidden" id="subzzqcFg11" 		name="subzzqcFg11"		value="<c:out value="\${zzqcFg11}" />" />
			<input type="hidden" id="subzzqcFg12" 		name="subzzqcFg12"		value="<c:out value="\${zzqcFg12}" />" />
			<input type="hidden" id="substatus" 		name="substatus"		value="<c:out value="\${status}" />" />
			<input type="hidden" id="subregDate" 		name="subregDate"		value="<c:out value="\${regDate}" />" />
			<input type="hidden" id="subregId" 			name="subregId"			value="<c:out value="\${regId}" />" />
			<input type="hidden" id="submodDate" 		name="submodDate"		value="<c:out value="\${modDate}" />" />
			<input type="hidden" id="submodId" 			name="submodId"			value="<c:out value="\${modId}" />" />
			<input type="hidden" id="subifDt" 			name="subifDt"			value="<c:out value="\${ifDt}" />" />
		</td>
	</tr>
</script>

<base target="_self"/>
</head>

<body onbeforeunload="windowClose();">
	<div id="popup" style="width:95%; padding: 20px 5px 5px 20px;">
	    <div id="p_title1">
	        <h1><spring:message code="tab.srm.srmjon0030.tab2" /></h1><%--상세정보 --%>
	        <span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
	    </div>
		<div id="wrap_menu">
			<!---------- 변경정보 이력 List Start  ------------------------------>
			<p style="margin-top: 5px;" />
			<div class="bbs_list">
				<ul class="tit">
					<li class="tit"><spring:message code="text.srm.field.sellerList1" /></li><%--변경정보 이력 List --%>
					<li class="tit" style="color: blue;">(<spring:message code="text.srm.field.sellerList2" />)</li><%--리스트 클릭시 상세내용을 볼 수 있습니다. --%>
					<li class="btn">
						<a href="#" class="btn" id="btnNew" name="btnNew" onclick="doNew();"><span><spring:message code='button.srm.new' /></span></a><%--신규 --%>
						<a href="#" class="btn" id="btnClose" name="btnClose" onclick="popupClose();"><span><spring:message code='button.srm.close' /></span></a><%--닫기 --%>
					</li>
				</ul>
				<div style="width:100%; height:250px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="1" style="border-color:#88a1b6; border-style: solid;" id="headTbl">
						<colgroup>
							<col style="width: 30px;" />
							<col style="width: 80px;" />
							<col style="width: 130px;" />
							<col style="width: 80px;" />
							<col style="width: 80px;" />
							<col style="width: 120px;" />
							<col style="width: 80px;" />
							<col style="width: 140px;" />
						</colgroup>
						<tr>
							<th><spring:message code="text.srm.field.no" /></th><%--No --%>
							<th><spring:message code="text.srm.field.sellerCeoName" /></th><%--대표자명 --%>
							<th><spring:message code="text.srm.field.sellerCeoEmail" /></th><%--대표자이메일 --%>
							<th><spring:message code="text.srm.field.vMainName" /></th><%--담당자명 --%>
							<th><spring:message code="text.srm.field.vMobilePhone" /></th><%--휴대전화 --%>
							<th><spring:message code="text.srm.field.vEmail" /></th><%--이메일 --%>
							<th><spring:message code="text.srm.field.confirmStatus" /></th><%--확정여부 --%>
							<th><spring:message code="text.srm.field.confirmDate" /></th><%--확정일자 --%>
						</tr>
						
						<tbody id="dataListbody" />
						
					</table>
				</div>
			</div>
			<!---------- 변경정보 이력 List End  ------------------------------>
			
			<p style="margin-top: 10px;" />
			<div class="bbs_list">
				<form name="MyForm" id="MyForm" method="post">
					<input type="hidden" id="venCd" name="venCd" />
					<input type="hidden" id="seq" name="seq" />
					<input type="hidden" id="status" name="status" />
							
					<div class="bbs_search">
						<ul class="tit">
							<li class="tit"><spring:message code="tab.srm.srmjon0030.tab2" /></li><%--상세정보 --%>
							
							<li class="btn">
								<a href="#" class="btn" id="btnRequest" name="btnRequest" onclick="doRequest();"><span><spring:message code='button.srm.approval.request' /></span></a><%--변경요청 --%>
								<a href="#" class="btn" id="btnSave" name="btnSave" onclick="doSave();"><span><spring:message code='button.srm.save' /></span></a><%--저장 --%>
								<a href="#" class="btn" id="btnDelete" name="btnDelete" onclick="doDelete();"><span><spring:message code='button.srm.delete' /></span></a><%--삭제 --%>
							</li>
						</ul>
						
						<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
							<colgroup>
								<col style="width:15%" />
								<col style="width:30%" />
								<col style="width:15%" />
								<col style="*" />
							</colgroup>
							
							<tr>
								<%-- <th><label for="presidNm">*<spring:message code="text.srm.field.sellerCeoName" /></label></th>대표자명
								<td>
									<input type="text" id="presidNm" name="presidNm" />
								</td> --%>
								<th><label for="presidEmail">*<spring:message code="text.srm.field.sellerCeoEmail" /></label></th><%--대표자이메일 --%>
								<td colspan="3">
									<input type="text" id="presidEmail" name="presidEmail" style="width: 50%;" />
								</td>
							</tr>
							<tr>
								<th><label for="dutyInf">*<spring:message code="text.srm.field.vMainName" /></label></th><%--담당자명 --%>
								<td>
									<input type="text" id="dutyInf" name="dutyInf" />
								</td>
								<th><label for="repTelNo">*<spring:message code="text.srm.field.vPhone1" /></label></th><%--대표전화 --%>
								<td>
									<input type="text" id="repTelNo" name="repTelNo" class="numberic" />
								</td>
							</tr>
							<tr>
								<th><label for="hpNo1">*<spring:message code="text.srm.field.vMobilePhone" /></label></th><%--휴대전화 --%>
								<td>
									<input type="text" id="hpNo1" name="hpNo1" class="numberic" />
								</td>
								<th><label for="faxNo"><spring:message code="text.srm.field.faxFhone" /></label></th><%--FAX --%>
								<td>
									<input type="text" id="faxNo" name="faxNo" class="numberic" />
								</td>
							</tr>
							<tr>
								<th><label for="email">*<spring:message code="text.srm.field.vEmail" /></label></th><%--이메일 --%>
								<td colspan="3">
									<input type="text" id="email" name="email" style="width: 50%;" />
								</td>
							</tr>
							<tr>
								<th><label for="addr2">*<spring:message code="text.srm.field.address" /></label></th><%--주소 --%>
								<td colspan="3">
									<input type="text" id="zip" name="zip" style="width:50PX; text-align:center;" readonly="readonly" />
									<input type="button" onclick="openZipCodeNew();" value="<spring:message code='button.srm.find'/>" style="cursor:pointer" /><%--찾기 --%>
									<input type="text" id="addr" name="addr" style="width:250px;" readonly="readonly" />
									<input type="text" id="addr2" name="addr2" style="width:200px;" />
								</td>
							</tr>
							<tr>
								<th><label><spring:message code="text.srm.field.srmjon0042.sub.title1" /></label></th><%--인증정보 --%>
								<td colspan="3">
									<input type="checkbox" id="zzqcFg1" name="zzqcFg1" /><spring:message code='checkbox.srm.zzqcFg1'/><%--HACCP --%>
									<input type="checkbox" id="zzqcFg2" name="zzqcFg2" /><spring:message code='checkbox.srm.zzqcFg2'/><%--FSSC22000 --%>
									<input type="checkbox" id="zzqcFg3" name="zzqcFg3" /><spring:message code='checkbox.srm.zzqcFg3'/><%--ISO22000 --%>
									<input type="checkbox" id="zzqcFg4" name="zzqcFg4" /><spring:message code='checkbox.srm.zzqcFg4'/><%--GMP인증 --%>
									<input type="checkbox" id="zzqcFg5" name="zzqcFg5" /><spring:message code='checkbox.srm.zzqcFg5'/><%--KS인증 --%>
									<input type="checkbox" id="zzqcFg6" name="zzqcFg6" /><spring:message code='checkbox.srm.zzqcFg6'/><%--우수농산물GAP인증 --%>
									<br>
									<input type="checkbox" id="zzqcFg7" name="zzqcFg7" /><spring:message code='checkbox.srm.zzqcFg7'/><%--유기가공식품인증 --%>
									<input type="checkbox" id="zzqcFg8" name="zzqcFg8" /><spring:message code='checkbox.srm.zzqcFg8'/><%--전통식품품질인증 --%>
									<input type="checkbox" id="zzqcFg9" name="zzqcFg9" /><spring:message code='checkbox.srm.zzqcFg9'/><%--ISO_9001 --%>
									<input type="checkbox" id="zzqcFg10" name="zzqcFg10" /><spring:message code='checkbox.srm.zzqcFg10'/><%--수산물품질인증 --%>
									<input type="checkbox" id="zzqcFg11" name="zzqcFg11" /><spring:message code='checkbox.srm.zzqcFg11'/><%--PAS_220 --%>
								</td>
							</tr>
							<tr>
								<th><label for="mdEmail">*<spring:message code="text.srm.field.mdEmail" /></label></th><%--MD이메일 --%>
								<td colspan="3">
									<input type="text" id="mdEmail" name="mdEmail" style="width: 50%;" /><br>
									<span style="color:blue;"><spring:message code='text.srm.field.srmven0010Notice1'/></span><%--변경요청시 담당MD에게 E-mail 발송용 --%>
								</td>
							</tr>
						</table>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
