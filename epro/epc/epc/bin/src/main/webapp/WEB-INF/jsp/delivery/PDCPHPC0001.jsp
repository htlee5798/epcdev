<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ page import="com.lottemart.epc.common.util.EPCUtil" %>
<%@ page import="com.lottemart.common.util.DataMap" %>
<%
DataMap hpclInfo1 = (DataMap)request.getAttribute("hpclInfo");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<script type="text/javascript">
var send_zip_bttn_clicked = false;
var recv_zip_bttn_clicked = false;

$(document).ready(function() {

	if($('#loginSession').val()=='false'){
		alert('<spring:message code="msg.login.necessary"/>');
		//메인 화면으로 이동
		var targetUrl = '<c:url value="/main/intro.do"/>';
		opener.window.open('','_parent'); 
		opener.window.open(targetUrl,'_top');
		self.close();
		return;
	}

	//사후 해피콜 확정인 경우 해피콜 등록 불가능
	if("${hpclInfo.HPCL_RSLT_CD}"=="60"){
		$('#insertHpc').hide();
	}

    // 저장버튼 클릭
    $('#save').click(function() {
    	if("${hpclInfo.INVOICE_PRT_YN}"=="Y") {
    		alert("이미 운송송장이 출력된 상태입니다. \n수정이 불가합니다.");
    		return;
    	} else if("${hpclInfo.ORDER_APPLY_YN}"=="Y") {
        		alert("이미 발주반영된 상태입니다. \n수정이 불가합니다.");
        		return;
    	} else {
	    	doUpdate();
    	}
    });

    // 닫기버튼 클릭
    $('#close').click(function() {
		window.close();
    });

    //더보기버튼클릭
    $('#everyInfo').click(function() {
		$('#hpcList').show();
		$('#everyInfo').hide();
		$('#closeHpcList').show();
    });

    //접기버튼클릭
    $('#closeHpcList').click(function() {
		$('#hpcList').hide();
		$('#everyInfo').show();
		$('#closeHpcList').hide();
    });

    //등록버튼클릭
    $('#insertHpc').click(function() {
    	//배송진행단계상태 : 접수등록완료 에서만 등록 가능
    	if('${hpclInfo.DELI_PRGS_STEP_STS_CD}' == '13' ||
    	   '${hpclInfo.DELI_PRGS_STEP_STS_CD}' == '51' ||
    	   '${hpclInfo.DELI_PRGS_STEP_STS_CD}' == '53' ||
    	   '${hpclInfo.DELI_PRGS_STEP_STS_CD}' == '55' ||
    	   '${hpclInfo.DELI_PRGS_STEP_STS_CD}' == '71' ||
    	   '${hpclInfo.DELI_PRGS_STEP_STS_CD}' == '73'
    	){
	    	$('#insertHpc').hide();
	    	$('#hpcRegi').show();
	    	$('#hpcRegiTable').show();
    	}else{
    		//alert("배송진행단계상태가 \"접수등록완료\"인 경우에만\n해피콜 등록이 가능합니다.");
    		alert("해피콜을 등록 할 수 있는 단계가 아닙니다\n해피콜 등록 가능 상태는 다음과 같습니다\n\n[사전해피콜]배송진행단계상태 : 접수등록완료\n[사후해피콜]배송진행단계상태 : 집하완료, 미집하, 배송출발, 배송완료, 배송불가\n\n※사후 해피콜은 배송완료인 경우에 등록 하셔야 합니다 ");
    	}
    });

    //취소버튼클릭
    $('#closeHpcRegi').click(function() {
    	$('#insertHpc').show();
    	$('#hpcRegi').hide();
    	$('#hpcRegiTable').hide();

    	//전화번호 리셋
    	setPhoneNumber();

    	//이름
    	$('#sendNm').val("${hpclInfo.SEND_PSN_NM}");
    	$('#recvNm').val("${hpclInfo.RECV_PSN_NM}");
    	
    	//주소 리셋
    	<c:if test="${loginId eq 'hpc1' or loginId eq 'hpc2' or loginId eq 'hpc3'  or loginId eq 'hpc4'  or loginId eq 'hpc5' }">
	    	$('#sendPsnZipNo').val("${hpclInfo.SEND_PSN_ZIP_NO}");
	    	$('#sendPsnZipNoSeq').val("${hpclInfo.SEND_PSN_ZIP_NO_SEQ}");
	    	$('#sendPsnZipAddr').val("${hpclInfo.SEND_PSN_ZIP_ADDR}");
	    	$('#sendPsnAddr').val("${hpclInfo.SEND_PSN_ADDR}");
	    </c:if>
    	
    	$('#recvPsnZipNo').val("${hpclInfo.RECV_PSN_ZIP_NO}");
    	$('#recvPsnZipNoSeq').val("${hpclInfo.RECV_PSN_ZIP_NO_SEQ}");
    	$('#recvPsnZipAddr').val("${hpclInfo.RECV_PSN_ZIP_ADDR}");
    	$('#recvPsnAddr').val("${hpclInfo.RECV_PSN_ADDR}");
    	$('#deliStrCd').val("${hpclInfo.DELI_STR_CD}");
    	$('#updateRecv').val("");

    	//해피콜 정보 리셋
    	$("#hpclRsltCd option").each( function(){
    		if( $(this).val() == "${hpclInfo.HPCL_RSLT_CD}") {
    			$(this).attr("selected", true);
    			return;
    		}
    	});
    	$('#hpclContentBox').val("");
    	$('#hpclContent').val("");
    	$('#hpclContent').hide();
    });

    //[접기]버튼 숨김 
    $('#closeHpcList').hide();

	//우편번호 조회
	$('#send_zip_bttn').click(function() {
		send_zip_bttn_clicked = true;
		popupZip();
	});

	$('#recv_zip_bttn').click(function() {
		recv_zip_bttn_clicked = true;
		popupZip();
	});

	//해피콜 메시지
	$(getHpcMessage()).appendTo('#hpclContentBox');
	$('#hpclContent').focus().hide();

	//전화번호 초기화
	setPhoneNumber();

	$(".input").change(function(){
		$('#updateRecv').val("Y");
	});

	//validate
	var detailForm = $('#detailForm');
	$('#hpclRsltCd', detailForm).attr("message","해피콜상태").attr("validate","required:true");
	$('#hpclContentBox', detailForm).attr("message","해피콜메시지").attr("validate","required:true");
	$('#recvPsnZipNo', detailForm).attr("message","우편번호").attr("validate","required:true, maxlength: 7");
	$('#recvPsnZipAddr', detailForm).attr("message","받으실분 주소").attr("validate","required:true, maxlength: 100 ");
	$('#recvPsnAddr', detailForm).attr("message","받으실분 상세주소").attr("validate","required:true, maxlength: 100 ");
	$('#recvNm', detailForm).attr("message","받으실분 이름").attr("validate","required:true, maxlength: 15 ");
	//$('#recvPsnCellNo1', detailForm).attr("message","휴대폰번호 앞자리").attr("validate","required:true, number: true, minlength: 3, maxlength: 4, min: 0");
	//$('#recvPsnCellNo2', detailForm).attr("message","휴대폰번호 중간자리").attr("validate","required:true, number: true, minlength: 3, maxlength: 4, min: 0");
	//$('#recvPsnCellNo3', detailForm).attr("message","휴대폰번호 뒷자리").attr("validate","required:true, number: true, minlength: 3, maxlength: 4, min: 0");
	//$('#recvPsnTelNo1', detailForm).attr("message","전화번호 앞자리").attr("validate","number: true, minlength: 2, maxlength: 4, min: 0");
	//$('#recvPsnTelNo2', detailForm).attr("message","전화번호 중간자리").attr("validate","number: true, minlength: 3, maxlength: 4, min: 0");
	//$('#recvPsnTelNo3', detailForm).attr("message","전화번호 뒷자리").attr("validate","number: true, minlength: 3, maxlength: 4, min: 0");
	<c:if test="${loginId eq 'hpc1' or loginId eq 'hpc2' or loginId eq 'hpc3' or loginId eq 'hpc4' or loginId eq 'hpc5'}">
		$('#sendPsnZipNo', detailForm).attr("message","보내신분우편번호").attr("validate","required:true, maxlength: 7");
		$('#sendPsnZipAddr', detailForm).attr("message","보내신분 주소").attr("validate","required:true, maxlength: 100 ");
		$('#sendPsnAddr', detailForm).attr("message","보내신분 상세주소").attr("validate","required:true, maxlength: 100 ");
		$('#sendNm', detailForm).attr("message","보내신분 이름").attr("validate","required:true, maxlength: 15 ");
		$('#sendPsnCellNo1', detailForm).attr("message","보내신분 휴대폰번호 앞자리").attr("validate","required:true, number: true, minlength: 2, maxlength: 4, min: 0");
		$('#sendPsnCellNo2', detailForm).attr("message","보내신분 휴대폰번호 중간자리").attr("validate","required:true, number: true, minlength: 3, maxlength: 4, min: 0");
		$('#sendPsnCellNo3', detailForm).attr("message","보내신분 휴대폰번호 뒷자리").attr("validate","required:true, number: true, minlength: 3, maxlength: 4, min: 0");
		$('#sendPsnTelNo1', detailForm).attr("message","보내신분 전화번호 앞자리").attr("validate","number: true, minlength: 2, maxlength: 4, min: 0");
		$('#sendPsnTelNo2', detailForm).attr("message","보내신분 전화번호 중간자리").attr("validate","number: true, minlength: 3, maxlength: 4, min: 0");
		$('#sendPsnTelNo3', detailForm).attr("message","보내신분 전화번호 뒷자리").attr("validate","number: true, minlength: 3, maxlength: 4, min: 0");
	</c:if>
	$('#hpclContent', detailForm).attr("message","해피콜메시지").attr("validate","required:true, maxlength: 100");

	detailForm.validate( $.extend({}, validateDefaultOption, {
		submitHandler: function(detailForm) {
			validateDefaultOption.submitHandler(detailForm);
		},
		errorPlacement: function(error, element) {
			error.insertAfter(element); 
		}
	}));

}); // end of ready

/*******************************
 * 해피콜 받으실 분 전화번로 set
 *******************************/
function setPhoneNumber(){

	var recvPsnCellNo = "${hpclInfo.RECV_PSN_CELL_NO}";
	var recvPsnTelNo = "${hpclInfo.RECV_PSN_TEL_NO}";

	recvPsnCellNo = recvPsnCellNo.replace(/-/g, "");
	recvPsnTelNo = recvPsnTelNo.replace(/-/g, "");

	$('#recvPsnCellNo1').val(recvPsnCellNo.substring(0,4).trim());
	$('#recvPsnCellNo2').val(recvPsnCellNo.substring(4,8).trim());
	$('#recvPsnCellNo3').val(recvPsnCellNo.substring(8,12).trim());
	$('#recvPsnTelNo1').val(recvPsnTelNo.substring(0,4).trim());
	$('#recvPsnTelNo2').val(recvPsnTelNo.substring(4,8).trim());
	$('#recvPsnTelNo3').val(recvPsnTelNo.substring(8,12).trim());

	<c:if test="${loginId eq 'hpc1' or loginId eq 'hpc2' or loginId eq 'hpc3' or loginId eq 'hpc4' or loginId eq 'hpc5'}">
		var sendPsnCellNo = "${hpclInfo.SEND_PSN_CELL_NO}";
		var sendPsnTelNo = "${hpclInfo.SEND_PSN_TEL_NO}";
		sendPsnCellNo = sendPsnCellNo.replace(/-/g, "");
		sendPsnTelNo = sendPsnTelNo.replace(/-/g, "");
		$('#sendPsnCellNo1').val(sendPsnCellNo.substring(0,4).trim());
		$('#sendPsnCellNo2').val(sendPsnCellNo.substring(4,8).trim());
		$('#sendPsnCellNo3').val(sendPsnCellNo.substring(8,12).trim());
		$('#sendPsnTelNo1').val(sendPsnTelNo.substring(0,4).trim());
		$('#sendPsnTelNo2').val(sendPsnTelNo.substring(4,8).trim());
		$('#sendPsnTelNo3').val(sendPsnTelNo.substring(8,12).trim());
	</c:if>
}

/*******************************
 * 해피콜 메시지 List
 *******************************/
function getHpcMessage(){
	var message = '';

	message = message +
			'<option value="해피콜완료">해피콜완료</option>'+
			'<option value="해피콜완료(주소수정)">해피콜완료(주소수정)</option>'+
			'<option value="부재">부재</option>'+
			'<option value="장기부재">장기부재</option>'+
			'<option value="회사주소(미확정)">회사주소(미확정)</option>'+
			'<option value="결번">결번</option>'+
			'<option value="타인">타인</option>'+
			'<option value="로밍">로밍</option>'+
			'<option value="전원꺼짐">전원꺼짐</option>'+
			'<option value="착신정지">착신정지</option>'+
			'<option value="수취거절">수취거절</option>'+
			'<option value="수하인콜진행요청">수하인콜진행요청</option>'+
			'<option value="송하인택배요청건">송하인택배요청건</option>'+
			'<option value="점포택배요청건">점포택배요청건</option>'+
			'<option value="0">기타(텍스트입력)</option>';

	return message;
}

/*******************************
 * 우편번호 조회
 *******************************/
function popupZip(){
 	Common.centerPopupWindow('<c:url value="/delivery/post.do"/>', 'post_no', {width : 500, height : 410});
}

/*******************************
 * 우편번호 붙여넣기
 *******************************/
function pasteZip(obj) {
	if (send_zip_bttn_clicked) {
		$('#sendPsnZipNo').val(obj.POST_NO);
		$('#sendPsnZipAddr').val(obj.POST_ADDR);
		$('#sendPsnAddr').val(obj.DTL_ADDR);
		$('#sendPsnZipNoSeq').val(obj.SEQC);

		send_zip_bttn_clicked = false;
	} else if (recv_zip_bttn_clicked) {
		$('#recvPsnZipNo').val(obj.POST_NO);
		$('#recvPsnZipAddr').val(obj.POST_ADDR);
		$('#recvPsnAddr').val(obj.DTL_ADDR);
		$('#recvPsnZipNoSeq').val(obj.SEQC);
		$('#deliStrCd').val(obj.ONLINE_STR_CD);

		recv_zip_bttn_clicked = false;
	}

}

/*******************************
 * 해피콜 결과 저장
 *******************************/
function doUpdate() {
	var confirmMsg = '';

	if($('#detailForm').validate().form()){

		<c:if test="${loginId eq 'hpc1' or loginId eq 'hpc2' or loginId eq 'hpc3' or loginId eq 'hpc4' or loginId eq 'hpc5'}">
			//전화번호를 입력하려면 다하고, 하지 않으며면 다 하지 않도록...
			if($('#sendPsnTelNo1').val()=='' || $('#sendPsnTelNo2').val()=='' || $('#sendPsnTelNo3').val()==''){
				if(!($('#sendPsnTelNo1').val()=='' && $('#sendPsnTelNo2').val()=='' && $('#sendPsnTelNo3').val()=='')){
					alert("보내신분 전화번호를 세칸 모두 입력해주세요.");
					$('#sendPsnTelNo1').focus();
					return ;
				}
			}
		</c:if>

		if($('#recvPsnTelNo1').val()=='' || $('#recvPsnTelNo2').val()=='' || $('#recvPsnTelNo3').val()==''){
			if(!($('#recvPsnTelNo1').val()=='' && $('#recvPsnTelNo2').val()=='' && $('#recvPsnTelNo3').val()=='')){
				alert("받으실분 전화번호를 세칸 모두 입력해주세요.");
				$('#recvPsnTelNo1').focus();
				return ;
			}
		}

		// 사전 확정후에는 주소 못고침
		confirmMsg = '<spring:message code="msg.common.confirm.save"/>';
		if($('#updateRecv').val()=="Y"){	//받으실 분 정보를 수정한 경우
			if("${hpclInfo.HPCL_RSLT_CD}"=="30" || "${hpclInfo.HPCL_RSLT_CD}"=="40" || "${hpclInfo.HPCL_RSLT_CD}"=="50"){
				confirmMsg = "확정된 데이터 입니다. 정보를 수정하시겠습니까?";
			}
		}

		var date = new Date();
		var yyyy = date.getFullYear().toString();
		var mm = (date.getMonth() + 1).toString().length < 2 ? "0"+ (date.getMonth() + 1) : (date.getMonth() + 1).toString();
		var dd = date.getDate().toString().length < 2 ? "0" + date.getDate() : date.getDate().toString();
		var hours = date.getHours().toString().length < 2 ? "0" + date.getHours() : date.getHours().toString();	// 시간
		var minutes = date.getMinutes().toString().length < 2 ? "0" + date.getMinutes() : date.getMinutes().toString();	// 분

		var today = yyyy + mm + dd;
		var todayHM = today + hours + minutes;
		var deliOrderDy = $('#deliOrderDy').val().replace(/-/gi, "");
		var orgDeliOrderDy = $('#orgDeliOrderDy').val().replace(/-/gi, "");
		var fedayMallProdDivnCd = $('#fedayMallProdDivnCd').val();

		//이미 등록된 출고일 변경 시 체크 
		if (orgDeliOrderDy != '') {
			//명절냉동은 D-day 12시 50분까지 수정가능 (12시 -> 12시 50분으로 변경 2021.12.21)
			var limitDate = orgDeliOrderDy + "1250";

			if (fedayMallProdDivnCd == '4') {//명절냉장배송
				//명절냉장배송은 D-1 일 12시 50분까지 수정 가능 (12시 -> 12시 50분으로 변경 2021.12.21)
				limitDate = addDate(orgDeliOrderDy, -1)+"1250";
			}

			//냉동은 출고일자 12시50분 까지 수정가능, 냉장은 출고일자-1일 12시 50분 까지 수정가능 (12시 -> 12시 50분으로 변경 2021.12.21)
			if (limitDate < todayHM) {
				alert("출고일을 수정할 수 없습니다.\n*냉동은 최종 출고일 마감시간은 출고일 D일 12시 50분\n*냉장은 최종 출고일 마감시간은 출고일 D-1일 12시 50분");
				$('#deliOrderDy').focus();
				return ;
			}
		}

		//출고일자 입력했을 경우 출고일 등록 가능일자 체크(2021.07.27 추가)
		if (deliOrderDy != '') {
			//명절 냉동, 냉장만 출고일자 등록가능(명절냉장배송-4, 명절냉동배송-9)
			if (fedayMallProdDivnCd != '4' && fedayMallProdDivnCd != '9') {
				alert("유형이 냉장/냉동인 경우만 출고일 등록 가능합니다.");
				$('#deliOrderDy').focus();
				return ;
			}

			//명절냉동은 D-day 12시 50분까지 수정가능 (12시 -> 12시 50분으로 변경 2021.12.21)
			var limitDate = deliOrderDy + "1250";
			
			if (fedayMallProdDivnCd == '4') {//명절냉장배송
				//명절냉장배송은 D-1 일 12시 50분까지 수정 가능(12시 -> 12시 50분으로 변경 2021.12.21)
				limitDate = addDate(deliOrderDy, -1)+"1250";
			}

			//냉동은 출고일자 12시 50분까지 수정가능, 냉장은 출고일자-1일 12시 50분까지 수정가능(12시 -> 12시 50분으로 변경 2021.12.21)
			if (limitDate < todayHM) {
				alert("출고일을 등록할 수 없습니다.\n*냉동은 최종 출고일 마감시간은 출고일 D일 12시 50분\n*냉장은 최종 출고일 마감시간은 출고일 D-1일 12시 50분");
				$('#deliOrderDy').focus();
				return ;
			}
		}

		if(confirm(confirmMsg)){

			//전화번호를 디비에 적합한 형식으로 수정
			assemblePhoneNumber();
			$.ajax({
				url: '<c:url value="/delivery/updateHappyCall.do"/>',
				type : "post",
				data: $('#detailForm').serialize()+"&recvPsnZipAddr="+$("#recvPsnZipAddr").val()+"&sendPsnZipAddr="+$("#sendPsnZipAddr").val(),
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				error: function(){
					alert('<spring:message code="msg.common.fail.update"/>');	//수정실패
				},
				success: function(json) {
					if(json>0){
						$('#updateRecv').val("");
						$('#closeHpcRegi').click();

						//재조회
						var invoiceNo = "${invoiceNo}";
						var targetUrl = '<c:url value="/delivery/PDCPHPC0001.do"/>?invoiceNo=' + invoiceNo;
						Common.centerPopupWindow(targetUrl, 'win', {width : 800, height : 600, scrollBars:"YES"});

						if(!opener.closed){
							if(opener.document.hpclForm){
								opener.doSearch(); //해피콜목록 조회 화면 refresh
							}
						}

					}else if(json == -1){ //로그인 권한 종료
						alert('<spring:message code="msg.login.necessary"/>');
						//메인 화면으로 이동
						var targetUrl = '<c:url value="/main/intro.do"/>';
						opener.window.open(targetUrl,'_top');
						self.close();
						return;
					}else{
						alert('<spring:message code="msg.common.fail.update"/>');	//수정실패
					}
				}
			});
		}
	}
}

function addDate(pYyyymmdd, pAddVal) {
	var yyyy;
	var mm;
	var dd;
	var cDate;
	var coDate;
	var cYear, cMonth, cDay;

	yyyy = pYyyymmdd.substr(0, 4);
	mm = pYyyymmdd.substr(4, 2);
	dd = pYyyymmdd.substr(6, 2);

	dd = (dd*1) + (pAddVal * 1);

	cDate = new Date(yyyy, mm-1, dd);
	cYear = cDate.getFullYear();
	cMonth = cDate.getMonth() + 1;
	cDay = cDate.getDate();

	cMonth = cMonth < 10 ? "0" + cMonth : cMonth;
	cDay = cDay < 10 ? "0" + cDay : cDay;

	return  "" + cYear + "" + cMonth + "" + cDay ;
}

/****************************************************
 * 전화번호들을 디비에 저장할 형식에 맞추어 조립한다.
 *****************************************************/
function assemblePhoneNumber(){
	var recvPsnCellNo = $('#recvPsnCellNo1').val().rpad(4,' ') + $('#recvPsnCellNo2').val().rpad(4,' ') + $('#recvPsnCellNo3').val().rpad(4,' ');
	$('#recvPsnCellNo').val(recvPsnCellNo);
	var recvPsnTelNo = $('#recvPsnTelNo1').val().rpad(4,' ') + $('#recvPsnTelNo2').val().rpad(4,' ') + $('#recvPsnTelNo3').val().rpad(4,' ');
	$('#recvPsnTelNo').val(recvPsnTelNo);
	<c:if test="${loginId eq 'hpc1' or loginId eq 'hpc2' or loginId eq 'hpc3' or loginId eq 'hpc4' or loginId eq 'hpc5'}">
		var sendPsnCellNo = $('#sendPsnCellNo1').val().rpad(4,' ') + $('#sendPsnCellNo2').val().rpad(4,' ') + $('#sendPsnCellNo3').val().rpad(4,' ');
		$('#sendPsnCellNo').val(sendPsnCellNo);
		var sendPsnTelNo = $('#sendPsnTelNo1').val().rpad(4,' ') + $('#sendPsnTelNo2').val().rpad(4,' ') + $('#sendPsnTelNo3').val().rpad(4,' ');
		$('#sendPsnTelNo').val(sendPsnTelNo);
	</c:if>
}

/*************************
 * 배송메시지 선택
 ************************** */
function changeDeliMsg(val) {
	if (val == '0') {
		$('#hpclContent').fadeIn().val('').focus();
	}
	else {
		$('#hpclContent').val(val).fadeOut();
	}
}

/** ***********************
 * 따옴표는 입력 불가능
 ************************** */
function checkQuotation(){
	if(event.keyCode=="34"){
		event.returnValue=false;
	}
}

//출고일삭제
function doDdelete() {
	var form = document.detailForm;
    form.deliOrderDy.value = "";
 }

</script>
</head>
<body>
<form name="detailForm" id="detailForm" method="post" action="<c:url value="/delivery/updateHappyCall.do"/>">
<div id="popup">
    <!--  @title  -->
    <div id="p_title1">
    	<h1>해피콜등록</h1>
    	<span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/pp/popup/logo_pop.gif" alt="LOTTE MART" /></span>  
    </div>

	<div id="process1">
	    <ul>
		    <li>홈</li>
			<li>배송관리</li>
			<li class="last">해피콜등록</li>
		</ul>
	</div>

	<div class="popup_contents">
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">배송정보</li>
				<li class="btn"><a href="#" class="btn" id="close"><span><spring:message code="button.common.close"/></span></a></li>
			</ul>
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col style="width:13%" />
				<col style="width:20%" />
				<col style="width:13%" />
				<col style="width:20%" />
				<col style="width:13%" />
				<col style="width:21%" />
			</colgroup>
			<tr>
				<th>매출점포</th>
				<td><c:out value="${hpclInfo.SALE_STR_NM}"/></td>
				<th>배송점포</th>
				<td colspan="3"><c:out value="${hpclInfo.DELI_STR_NM}"/></td>
			</tr>
			<tr>
				<th>접수일자</th>
				<td><c:out value="${hpclInfo.ACCEPT_DATE}"/></td>
				<th>접수번호</th>
				<td><c:out value="${hpclInfo.ACCEPT_NO}"/></td>
				<th>운송장출력일시</th>
				<td><b><c:out value="${hpclInfo.INVOICE_PRT_DATE}"/></b></td>
			</tr>
			<tr>
				<th>운송장번호</th>
				<td>
				<c:choose>
					<c:when test="${fn:substring(hpclInfo.HODECO_INVOICE_NO, 0, 2) eq '50'}">
						<a href="#" onclick="Common.centerPopupWindow('http://www.hanjinexpress.hanjin.net/customer/hddcw18.tracking?w_num=${hpclInfo.HODECO_INVOICE_NO}', '_popupZipCode_', {width : 620, height : 600});" style="text-decoration:underline;">
						 ${hpclInfo.HODECO_INVOICE_NO}</a>
					</c:when>
					<c:when test="${fn:substring(hpclInfo.HODECO_INVOICE_NO, 0, 2) eq '40'}">
						<a href="#" onclick="Common.centerPopupWindow('http://ftr.alps.llogis.com:8260/ftr/tracking.html?${hpclInfo.HODECO_INVOICE_NO}', '_popupZipCode_', {width : 620, height : 600});" style="text-decoration:underline;">
						 ${hpclInfo.HODECO_INVOICE_NO}</a>
					</c:when>
					<c:otherwise>
						<c:out value="${hpclInfo.HODECO_INVOICE_NO}"/>
					</c:otherwise>
				</c:choose>
				</td>
				<th>배송진행단계</th>
				<td><c:out value="${hpclInfo.DELI_PRGS_STEP_NM}"/></td>
				<th>배송진행단계상태</th>
				<td><c:out value="${hpclInfo.DELI_PRGS_STEP_STS_NM}"/></td>
			</tr>
			<tr>
				<th>배송메세지</th>
				<td colspan="8"><c:out value="${hpclInfo.DELI_MSG}"/></td>
			</tr>
			</table>

			<ul class="tit">
				<li class="tit">보내신분</li>
			</ul>
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col style="width:13%" />
				<col style="width:20%" />
				<col style="width:13%" />
				<col style="width:20%" />
				<col style="width:13%" />
				<col style="width:20%" />
			</colgroup>
			<tr>
				<th>이름</th>
				<td><c:out value="${hpclInfo.SEND_PSN_NM}"/></td>
				<th>휴대폰번호</th>
				<td><%=EPCUtil.phone(hpclInfo1.getString("SEND_PSN_CELL_NO").replaceAll("-", "").replaceAll(" ", ""))%><%--<c:out value="${EPCUtil.phone(hpclInfo.SEND_PSN_CELL_NO)}"/>--%></td>
				<th>전화번호</th>
				<td><%=EPCUtil.phone(hpclInfo1.getString("SEND_PSN_TEL_NO").replaceAll("-", "").replaceAll(" ", ""))%><%--<c:out value="${EPCUtil.phone(hpclInfo.SEND_PSN_TEL_NO)}"/>--%></td>
			</tr>
			<tr>
				<th>주소</th>
				<td colspan="5">
					<c:out value="${hpclInfo.SEND_PSN_ZIP_NO}"/>&nbsp;
					<c:out value="${hpclInfo.SEND_PSN_ZIP_ADDR}"/>&nbsp;
					<c:out value="${hpclInfo.SEND_PSN_ADDR}"/>
				</td>
			</tr>
			</table>
			<ul class="tit">
				<li class="tit">받으실분</li>
			</ul>
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col style="width:13%" />
				<col style="width:20%" />
				<col style="width:13%" />
				<col style="width:20%" />
				<col style="width:13%" />
				<col style="width:20%" />
			</colgroup>
			<tr>
				<th>이름</th>
				<td><c:out value="${hpclInfo.RECV_PSN_NM}"/></td>
				<th>휴대폰번호</th>
				<td><%=EPCUtil.phone(hpclInfo1.getString("RECV_PSN_CELL_NO").replaceAll("-", "").replaceAll(" ", ""))%><%--<c:out value="${EPCUtil.phone(hpclInfo.RECV_PSN_CELL_NO)}"/>--%></td>
				<th>전화번호</th>
				<td><%=EPCUtil.phone(hpclInfo1.getString("RECV_PSN_TEL_NO").replaceAll("-", "").replaceAll(" ", ""))%><%--<c:out value="${EPCUtil.phone(hpclInfo.RECV_PSN_TEL_NO)}"/>--%></td>
			</tr>
			<tr>
				<th>주소</th>
				<td colspan="5">
					<c:out value="${hpclInfo.RECV_PSN_ZIP_NO}"/>&nbsp;
					<c:out value="${hpclInfo.RECV_PSN_ZIP_ADDR}"/>&nbsp;
					<c:out value="${hpclInfo.RECV_PSN_ADDR}"/>
				</td>
			</tr>
			</table>

			<ul class="tit">
				<li class="tit">상품정보</li>
			</ul>
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col style="width:13%" />
				<col style="width:20%" />
				<col style="width:13%" />
				<col style="width:20%" />
				<col style="width:13%" />
				<col style="width:20%" />
			</colgroup>
			<c:forEach items="${hpclProdInfo}" var="hpclProdInfo">
				<tr>
					<th>상품명</th>
					<td><c:out value="${hpclProdInfo.PROD_NM}"/></td>
					<th>유형</th>
					<td>
						<c:out value="${hpclInfo.FEDAY_MALL_PROD_DIVN_NM}"/>
						<input type="hidden" name="fedayMallProdDivnCd" id="fedayMallProdDivnCd" value="${hpclInfo.FEDAY_MALL_PROD_DIVN_CD}" />
					</td>
					<th>상품수량</th>
					<td><c:out value="${hpclProdInfo.DELI_QTY}"/></td>
				</tr>
			</c:forEach>
			</table>

			<ul class="tit" id="hpcRegi"  style="display:none">
				<li class="tit">해피콜등록 </li>
				<li class="btn">
					<a href="#" class="btn" id="save"><span><spring:message code="button.common.save"/></span></a>
					<a href="#" class="btn" id="closeHpcRegi" ><span><spring:message code="button.common.cancel"/></span></a>
				</li>
			</ul>
			<table class="bbs_grid2" id="hpcRegiTable" cellpadding="0" cellspacing="0" border="0" style="display:none">
			<colgroup>
				<col style="width:13%" />
				<col style="width:20%" />
				<col style="width:13%" />
				<col style="width:20%" />
				<col style="width:13%" />
				<col style="width:20%" />
			</colgroup>

			<input type="hidden" id="invoiceNo" name="invoiceNo" value="${invoiceNo}"/> 
			<tr>
				<th><font color="red">*</font> 해피콜상태</th>
				<td>
					<select id="hpclRsltCd" name=hpclRsltCd style="width:90%">
						<option value="">선택</option>
						<c:choose>
							<c:when test="${loginId eq 'hpc1' or loginId eq 'hpc2' or loginId eq 'hpc3' or loginId eq 'hpc4' or loginId eq 'hpc5'}">
								<c:forEach items="${hpcStatus}" var="hpcStatus">
									<option value="${hpcStatus.MINOR_CD}" <c:if test="${hpcStatus.MINOR_CD==hpclInfo.HPCL_RSLT_CD}">selected</c:if>>${hpcStatus.CD_NM}</option>
								</c:forEach>
							</c:when>
							<c:otherwise>
									<option value="20">사전 해피콜 미확정</option>
							</c:otherwise>
						</c:choose>
					</select>
				</td>
				<th><font color="red">*</font> 해피콜메시지</th>
				<td colspan="3">
					<select id="hpclContentBox" name=hpclContentBox onchange="changeDeliMsg(this.value)" style="width:95%">
						<option value="">선택</option>
					</select><br>
					<input id="hpclContent" name="hpclContent" value="" maxlength="100" style="width:95%" onkeypress="checkQuotation();"/>
				</td>
			</tr>
			<!--  보내신분 수정 -->
			<c:if test="${loginId eq 'hpc1' or loginId eq 'hpc2' or loginId eq 'hpc3' or loginId eq 'hpc4' or loginId eq 'hpc5'}">
				<tr>
					<th><font color="red">*</font> 보내신분 이름</th>
					<td>
						<input class="input" id="sendNm" name="sendNm" value="${hpclInfo.SEND_PSN_NM}" style="width:92%" maxlength="15" onkeypress="checkQuotation();" />
					</td>
					<th><font color="red">*</font> 휴대폰번호</th>
					<td>
						<input type="text" class="input" id="sendPsnCellNo1" name="sendPsnCellNo1" style="width:25%" maxlength='4' /> 
						<input type="text" class="input" id="sendPsnCellNo2" name="sendPsnCellNo2" style="width:25%" maxlength='4' /> 
						<input type="text" class="input" id="sendPsnCellNo3" name="sendPsnCellNo3" style="width:25%" maxlength='4' /> 
						<input type="hidden" id="sendPsnCellNo" name="sendPsnCellNo"/>
					</td>
					<th>전화번호</th>
					<td>
						<input type="text" class="input" id="sendPsnTelNo1" name="sendPsnTelNo1" style="width:25%" maxlength="4" /> 
						<input type="text" class="input" id="sendPsnTelNo2" name="sendPsnTelNo2" style="width:25%" maxlength="4" /> 
						<input type="text" class="input" id="sendPsnTelNo3" name="sendPsnTelNo3" style="width:25%" maxlength="4" /> 
						<input type="hidden" id="sendPsnTelNo" name="sendPsnTelNo"/>
					</td>
				</tr>
				<tr>
					<th rowspan="2"><font color="red">*</font> 보내신 분 주소</th>
					<td>
						<input type="text" class="input" id="sendPsnZipNo" name="sendPsnZipNo" value="${hpclInfo.SEND_PSN_ZIP_NO}"style="width:60%" readonly/>&nbsp;
						<a href="#" class="btn" id="send_zip_bttn"><span><spring:message code="button.common.inquire"/></span></a>
					</td>
					<td colspan="4">
						<input type="text" class="input" id="sendPsnZipAddr" name="sendPsnZipAddr" value="${hpclInfo.SEND_PSN_ZIP_ADDR}" disabled="disabled" maxlength="100" style="width:96%" onkeypress="checkQuotation();" readonly/>
						<input type="hidden" id="sendPsnZipNoSeq" name="sendPsnZipNoSeq" value="${hpclInfo.SEND_PSN_ZIP_NO_SEQ}"/>
					</td>
				</tr>
				<tr>
					<td colspan="5">
						<input type="text" class="input" id="sendPsnAddr" name="sendPsnAddr" value="${hpclInfo.SEND_PSN_ADDR}" maxlength="100" style="width:97%" onkeypress="checkQuotation();" />
					</td>
				</tr>
			</c:if>
			<!-- 받으실분 수정 -->
			<tr>
				<th><font color="red">*</font> 받으실분 이름</th>
				<td>
					<input class="input" id="recvNm" name="recvNm" value="${hpclInfo.RECV_PSN_NM}" style="width:92%" maxlength="15" onkeypress="checkQuotation();"/>
				</td>
				<th><font color="red">*</font> 휴대폰번호</th>
				<td>
					<input type="text" class="input" id="recvPsnCellNo1" name="recvPsnCellNo1" style="width:25%" maxlength='4'/>
					<input type="text" class="input" id="recvPsnCellNo2" name="recvPsnCellNo2" style="width:25%" maxlength='4'/>
					<input type="text" class="input" id="recvPsnCellNo3" name="recvPsnCellNo3" style="width:25%" maxlength='4'/>
					<input type="hidden" id="recvPsnCellNo" name="recvPsnCellNo"/>
				</td>
				<th>전화번호</th>
				<td>
					<input type="text" class="input" id="recvPsnTelNo1" name="recvPsnTelNo1" style="width:25%" maxlength="4"/>
					<input type="text" class="input" id="recvPsnTelNo2" name="recvPsnTelNo2" style="width:25%" maxlength="4"/>
					<input type="text" class="input" id="recvPsnTelNo3" name="recvPsnTelNo3" style="width:25%" maxlength="4"/>
					<input type="hidden" id="recvPsnTelNo" name="recvPsnTelNo"/>
				</td>
			</tr>
			<tr>
				<th rowspan="2"><font color="red">*</font> 받으실 분 주소</th>
				<td>
					<input type="text" class="input" id="recvPsnZipNo" name="recvPsnZipNo" value="${hpclInfo.RECV_PSN_ZIP_NO}"style="width:60%" readonly/>&nbsp;
					<a href="#" class="btn" id="recv_zip_bttn"><span><spring:message code="button.common.inquire"/></span></a>
				</td>
				<td colspan="4">
					<input type="text" class="input" id="recvPsnZipAddr" name="recvPsnZipAddr" value="${hpclInfo.RECV_PSN_ZIP_ADDR}" disabled="disabled" maxlength="100" style="width:96%" onkeypress="checkQuotation();"/>
					<input type="hidden" id="recvPsnZipNoSeq" name="recvPsnZipNoSeq" value="${hpclInfo.RECV_PSN_ZIP_NO_SEQ}"/>
					<input type="hidden" id="deliStrCd" name="deliStrCd" value="${hpclInfo.DELI_STR_CD}"/>
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<input type="text" class="input" id="recvPsnAddr" name="recvPsnAddr" value="${hpclInfo.RECV_PSN_ADDR}" maxlength="100" style="width:97%" onkeypress="checkQuotation();"/>
					<input type="hidden" id="updateRecv" name="updateRecv"/> 
				</td>
			</tr>
			<tr>
				<th>배송메세지</th>
				<td colspan="3">
					<input type="text" class="input" id="deliMsg" name="deliMsg" value="${hpclInfo.DELI_MSG}" maxlength="100" style="width:97%" onkeypress="checkQuotation();"/>
				</td>
				<th>출고일</th>
				<td>
					<input type="hidden" name="orgDeliOrderDy" id="orgDeliOrderDy" value="${hpclInfo.DELI_ORDER_DY}" />
					<input type="text" name="deliOrderDy" id="deliOrderDy" value="${hpclInfo.DELI_ORDER_DY}" class="day" style="width:60%" readonly="readonly"/>
					<a href="javascript:openCal('detailForm.deliOrderDy')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>  
					<a href="javascript:doDdelete();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_eraser.gif" alt="" class="middle" /></a> 
				</td>
			</tr>
			</table>

			<ul class="tit">
				<li class="tit">해피콜정보</li>
				<li class="btn">
					<a href="#" class="btn" id="closeHpcList"><span> 접기 </span></a>
					<a href="#" class="btn" id="everyInfo" ><span>더보기</span></a>
					<a href="#" class="btn" id="insertHpc"><span><spring:message code="button.common.create"/></span></a>
				</li>
			</ul> 
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col style="width:13%" />
				<col style="width:20%" />
				<col style="width:13%" />
				<col style="width:20%" />
				<col style="width:13%" />
				<col style="width:20%" />
			</colgroup>
			<tr>
				<th>해피콜상태</th>
				<td><c:out value="${hpclInfo.HPCL_RSLT_NM}"/></td>
				<th>최종해피콜일시</th>
				<td><c:out value="${endHpc.MOD_DATE}"/></td>
				<th>상담원ID</th>
				<td><c:out value="${endHpc.CNSR_NM}"/></td>
			</tr>
			<tr>
				<th>해피콜메시지</th>
				<td id="hpclContentInfo" ><c:out value="${endHpc.HPCL_CONTENT}"/></td>
				<th>출고일</th>
				<td colspan="3"><c:out value="${hpclInfo.DELI_ORDER_DY}"/></td>
			</tr>
			</table>
			<table id="hpcList" class="bbs_grid2" cellpadding="0" cellspacing="0" border="0" style="display:none">
			<colgroup>
				<col style="width:13%" />
				<col style="width:20%" />
				<col style="width:13%" />
				<col style="width:20%" />
				<col style="width:13%" />
				<col style="width:20%" />
			</colgroup>
			<c:choose>
				<c:when test="${fn:length(hpcInfoList) > 1 }">
					<c:forEach items="${hpcInfoList}" var="hpc" begin="1">
				 		<tr>
							<th>해피콜상태</th>
							<td id="hpclRsltNmList"><c:out value="${hpc.HPCL_RSLT_NM}"/></td>
							<th>해피콜일시</th>
							<td id="modDateList"><c:out value="${hpc.MOD_DATE}"/></td>
							<th>상담원ID</th>
							<td id="cnsrNmList"><c:out value="${hpc.CNSR_NM}"/></td>
						</tr>
						<tr>
							<th>해피콜메시지</th>
							<td colspan='5' id="hpclContentList"><c:out value="${hpc.HPCL_CONTENT}"/></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr><th colspan="6" style="text-align: center;">마지막 리스트입니다.</th></tr>
				</c:otherwise>
			</c:choose>
			</table>
		</div>
	</div>
	<input type="hidden" id="loginSession" name="loginSession" value="${loginSession}"/>
</div><br>
</form>
</body>
</html>