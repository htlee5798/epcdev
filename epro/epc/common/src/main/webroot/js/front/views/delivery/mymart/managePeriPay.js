"use strict";

//선택된 정기배송코드
var periDeliId;

//휴대폰 입력 여부 플래그
var cellNumbers = false;

//동의여부 플래그
var agreeAll = false;

function getExistingPeri(exPeriDeliId) {
	$('input[name=periDeliId]').val(exPeriDeliId);

	var form = $('#periManager').get(0);
	form.submit();
}

//정기배송 완전취소
function cxlPeri(){
	var params = {
			periDeliId: $('input[name=periDeliId]').val()
			,exPeriDeliId: $('input[name=periDeliId]').val()
	};
	
	$.getJSON("/mymart/api/cancelPeriAll.do", params)
	.done(function(data) {
		if(jResult(data)){
			alert('삭제되었습니다');
			//$('input[name=periDeliId]').val('');
			//window.location.reload();
			$('input[name=periDeliId]').val("");
			var form = $('#periManager').get(0);
			form.submit();
		}
	});
}


function setCombiner(tarName){
	var target = $('input[name='+tarName+']');
	var value1 = $('#'+tarName+'1').val();
	var value2 = $('#'+tarName+'2').val();
	var value3 = $('#'+tarName+'3').val();
	
	if(tarName == 'email'){
		target.val(
			value1+'@'+
			value2
		);
	}else{
		target.val(
			(value1+'    ').slice(0,4)+
			(value2+'    ').slice(0,4)+
			(value3+'    ').slice(0,4)
		);
	}
	
	return target.val();
}

//유효성 체크
function validate(){
	//2017.03.02 이승남 ORDER_BASKET_TYPE = 10, 11 이 외에 경우 처리
	if(!$("#insuffPprocMethodDiv").attr('id') == undefined){
		if($("#products01Cnt").val() > 0){
			if(!$('input[name=insuffPprocMethodCd]').is(":checked")){
				$("#insuffPprocMethodDiv").attr("tabindex", -1).focus();
				alert('품절상품 처리 방법을 선택해 주세요.'); return false;
			}
		}
	}
	
	if($('input[name=insuffPprocMethodCd]').val() == undefined){
		if($("input[id=coOrderYn]").is(":checked")){
			$(document.infoManager).append($('<input>', {type:'hidden', name:'insuffPprocMethodCd', value:'05'}));
		}else{
			$(document.infoManager).append($('<input>', {type:'hidden', name:'insuffPprocMethodCd', value:'04'}));
		}
	}
	
	$('.cellNo').each(function(){
		if(!this.value) {
			cellNumbers = false;
			$(this).focus();
			return false;
		}
		cellNumbers = true;
	});
	if(!cellNumbers){
		alert('연락처를 입력해 주세요.'); return false;
	}
	
	if($("#cellNo2").val() == ""){
		alert("휴대폰번호 가운데자리를 입력하여 주세요."); 
		$("#cellNo2").focus();
		return false;
	}
	
	if($("#cellNo1").val() == "010" && $.trim($("#cellNo2").val()).length < 4 ){
		alert("휴대폰번호 가운데자리를 4자 이상 입력해야 합니다."); 
		$("#cellNo2").focus();
		return false;
	}

	if($("#cellNo1").val() != "010" && $.trim($("#cellNo2").val()).length < 3 ){
		alert("휴대폰번호 가운데자리를 3자 이상 입력해야 합니다."); 
		$("#cellNo2").focus();
		return false;
	}
	
	if($("#cellNo3").val() == ""){
		alert("휴대폰번호 뒷자리를 입력하여 주세요."); 
		$("#cellNo3").focus();
		return false;
	}
	
	if($.trim($("#cellNo3").val()).length < 4 ){
		alert("휴대폰번호 뒷자리는 4자 이상 입력해야 합니다."); 
		$("#cellNo3").focus();
		return false;
	}
	
	if($("#email1").val() == ""){
		alert("이메일 앞자리를 입력하여 주세요."); 
		$("#email1").focus();
		return false;
	}
	
	if($("#email2").val() == ""){
		alert("이메일 뒷자리를 입력하여 주세요."); 
		$("#email2").focus();
		return false;
	}	
	
	//2017.02.10 이승남
	var email = $("#email1").val()+"@"+$("#email2").val();
	// 이메일 형식 체크
	if (!chkEmail(email)) {
		alert("이메일 주소 형식이 올바르지 않습니다.");
		$("#email1").focus();
		return false;
	}
	
	if($("#recpNm").val() == ""){
		alert("받는 분 이름을 입력해 주시기 바랍니다.");
		$("#recpNm").focus();
		return false;
	}
	
	if($("#recpCellNo2").val() == ""){
		alert("휴대폰번호 가운데자리를 입력하여 주세요."); 
		$("#recpCellNo2").focus();
		return false;
	}
	
	if($("#recpCellNo1").val() == "010" && $.trim($("#recpCellNo2").val()).length < 4 ){
		alert("휴대폰번호 가운데자리를 4자 이상 입력해야 합니다."); 
		$("#recpCellNo2").focus();
		return false;
	}

	if($("#recpCellNo1").val() != "010" && $.trim($("#recpCellNo2").val()).length < 3 ){
		alert("휴대폰번호 가운데자리를 3자 이상 입력해야 합니다."); 
		$("#recpCellNo2").focus();
		return false;
	}
	
	if($("#recpCellNo3").val() == ""){
		alert("휴대폰번호 뒷자리를 입력하여 주세요."); 
		$("#recpCellNo3").focus();
		return false;
	}
	
	if($.trim($("#recpCellNo3").val()).length < 4 ){
		alert("휴대폰번호 뒷자리는 4자 이상 입력해야 합니다."); 
		$("#recpCellNo3").focus();
		return false;
	}
/*
	if($("#recpTelNo2").val() == ""){
		alert("전화번호 가운데자리를 입력하여 주세요."); 
		$("#recpTelNo2").focus();
		return false;
	}
	
	if($.trim($("#recpTelNo2").val()).length < 3 ){
		alert("전화번호 가운데자리를 3자 이상 입력해야 합니다."); 
		$("#recpTelNo2").focus();
		return false;
	}
	
	if($("#recpTelNo3").val() == ""){
		alert("전화번호 뒷자리를 입력하여 주세요."); 
		$("#recpTelNo3").focus();
		return false;
	}
	
	if($.trim($("#recpTelNo3").val()).length < 4 ){
		alert("전화번호 뒷자리는 4자 이상 입력해야 합니다."); 
		$("#recpTelNo3").focus();
		return false;
	}	
*/	
	
	//2017.02.06 이승남
	// 전화번호 필수 X  and  유효성 검사
	if($("#recpTelNo2").val() != "" && $("#recpTelNo3").val() != ""){
		var telNum = $("#recpTelNo1").val()+'-'+$("#recpTelNo2").val()+'-'+$("#recpTelNo3").val();
		if (chkPhoneNumber(telNum) == false) {
			alert("전화번호 형식이 잘못되었습니다. 다시 입력해 주세요.");
			$("#recpTelNo1").focus();
			return false;
		}
	}	
	//2017.02.06 이승남
	if($("#deliRqstSbjtContent").val() == 'default'){
		alert("배송 메시지를 선택해 주시기 바랍니다.");
		return false;
	}else{
		if($("#deliRqstSbjtContent").val() == '01'){
			if($("input[name=deliRqstSbjtContent]").val() == ""){
				$("input[name=deliRqstSbjtContent]").focus();
				alert("배송 메시지 내용을 입력해 주시기 바랍니다.");
				return false;
			}
		}
	}
	
	if(!$("input:radio[name=setlCouponUseYn]").is(":checked")){
		$("#spareDiv").attr("tabindex", -1).focus();
		alert('결제쿠폰자동사용 여부를 선택해 주세요.'); return false;
	}
	
	if(!$("input:radio[name=deliCouponUseYn]").is(":checked")){
		$("#spareDiv").attr("tabindex", -1).focus();
		alert('무료배송쿠폰자동사용 여부를 선택해 주세요.'); return false;
	}
	
	setCombiner('cellNo');	//주문자 휴대
	setCombiner('email');	//주문자 이메일
	setCombiner('recpCellNo');	//받는이 휴대폰
	setCombiner('recpTelNo');	//받는이 전화번호
	
	return true;
}

//2017.01.24 이승남
//정기배송 수정
function updatePeriSchd(val){
	var tempMsg = ""
	if(val == 'delyDay'){
		if(!$("input[name^=deliDay]").is(":checked")){
			alert("배송 받을 요일을 선택해 주세요.");
			return;
		}else{
			tempMsg = "변경한 내용을 저장하시겠습니까?";
		}	
	} else if(val == 'extend'){		
		if(!$("input[name^=addMonth]").is(":checked")){
			alert("연장할 기간을 선택해 주세요.");
			return;
		}else{
			tempMsg = $('span.extend_deli_date').text()+"됩니다.\n연장하시겠습니까?";
		}	
	}else{
		tempMsg = "변경한 내용을 저장하시겠습니까?";
	}
	if(!confirm(tempMsg)){
		return;
	}
	$('#updateKind').val(val);
	var form = $('#periManager').get(0);
	form.action = '/mymart/updatePeri.do';
	form.submit();
}

function updatePeriInfo(){
	//2017.02.06 이승남	
	if(validate()){
		if(!confirm("변경된 내용을 모든 정기배송에 적용합니다. \n변경하시겠습니까?")){
			return;
		}else{
			var form = document.infoManager;//$('#infoManager').get(0);
			form.action = '/mymart/updatePeri.do';
			form.submit();
		}	
	}	
}

function updatePeriPay(){
	if(!$('#cardcoCd').val()){
		$('#cardcoCd').focus();
		alert('신용카드를 선택해 주세요.'); return false;
	}
	
	$('input[allId=ordAgmtAgree]').each(function(){
		if(!this.checked) {
			agreeAll = false;
			$(this).focus();
			return false;
		}
		agreeAll = true;
	});
	if(!agreeAll){
		alert('약관에 동의해 주세요.'); return false;
	}
	
	var form = document.ini;
	var startDy = $('#payManager input[name=fixedSetlMethodNo]').attr('start');
	var endDy = $('#payManager input[name=fixedSetlMethodNo]').attr('end');
	$('#payManager input[name=ini_offer_period]').val(startDy+""+endDy);
	$('#payManager input[name=aprvStartDy]').val(startDy);
	$('#payManager input[name=aprvEndDy]').val(endDy);
	
	$('#payManager input[name=buyertel]').val(setCombiner('cellNo').split(' ').join(''));
	$('#payManager input[name=buyeremail]').val(setCombiner('email').split(' ').join(''));
	
	if(auth(form)){
		form.action = '/mymart/updatePeri.do';
		form.submit();
	}
 }

function emptyForm(form){
	$('input', form).parent().removeClass('active');
	$('input[type=checkbox]', form).check(false);
	$('input[type=radio]', form).check(false);
	$('input[type=text]', form).val('');
	
	$('select option', form).prop('selected', false);
	$('select', form).trigger('change');
	
	$('#ordType1').prop('checked', true).parent().addClass('active');
}

//기존 정기배송 로딩
function showExistingPeriInfo() {
	emptyForm($('#periManager').get(0));
	
	if(!periDeliId){
		return;
	}
	var params = {
			exPeriDeliId: periDeliId
	};
	
	$.getJSON("/peribasket/api/existingPeriOrder.do", params)
		.done(function(data) {
			if (!!data) {
				console.log("data : " + JSON.stringify(data));
				if(data.INSUFF_PPROC_METHOD_CD != '02'&&
				   data.INSUFF_PPROC_METHOD_CD != '04'&&
				   data.INSUFF_PPROC_METHOD_CD != '03'){
					//$('#coOrderYn').click();
					$('#insuffPprocMethodCd1').val('11');
					$('#insuffPprocMethodCd2').val('05');
					$('#insuffPprocMethodCd3').val('12');		
					
					$('#coOrderYn').prop("checked", true);
					$("#coOrderYnSpan").addClass("active");
				
				}else{
					
					$('#coOrderYn').prop("checked", false);
					$("#coOrderYnSpan").addClass("");
					
				}
				
				$('input[name=insuffPprocMethodCd][value='+data.INSUFF_PPROC_METHOD_CD+']').check(true);
				
				$('#custNm').html(data.CUST_NM);
				var cellNo1 = data.CELL_NO.substring(0,4).trim();
				var cellNo2 = data.CELL_NO.substring(4,8).trim();
				var cellNo3 = data.CELL_NO.substring(8,12).trim();
				$('#cellNo1 option[value='+cellNo1+']').select();
				$('#cellNo2').val(cellNo2);
				$('#cellNo3').val(cellNo3);
				var email1 = data.EMAIL.split('@')[0];
				var email2 = data.EMAIL.split('@')[1];
				//var email3 = $('#email3 option[value="'+email2+'"]');
				$('#email1').val(email1);
				$('#email2').val(email2);
				if(email2 != ""){
					$("#email3").val(email2).prop("selected", "selected");
				}
				//if(!email3) $('#email2').val(email2);
				//else email3.select();
				$('#delyplNm').html(data.DELYPL_NM);
				$('input[name=recpNm]').val(data.RECP_NM);
				if(data.RECP_CELL_NO != null && data.RECP_CELL_NO != ""){
					var recpCellNo1 = data.RECP_CELL_NO.substring(0,4).trim();
					var recpCellNo2 = data.RECP_CELL_NO.substring(4,8).trim();
					var recpCellNo3 = data.RECP_CELL_NO.substring(8,12).trim();
					$('#recpCellNo1 option[value='+recpCellNo1+']').select();
					$('#recpCellNo2').val(recpCellNo2);
					$('#recpCellNo3').val(recpCellNo3);
				}
				if(data.RECP_TEL_NO != null && data.RECP_TEL_NO != ""){
					var recpTelNo1 = data.RECP_TEL_NO.substring(0,4).trim();
					var recpTelNo2 = data.RECP_TEL_NO.substring(4,8).trim();
					var recpTelNo3 = data.RECP_TEL_NO.substring(8,12).trim();
					//$('#recpTelNo1 option[value='+recpTelNo1+']').select();
					$('#recpTelNo1').val(recpTelNo1);
					$('#recpTelNo2').val(recpTelNo2);
					$('#recpTelNo3').val(recpTelNo3);
				}
				$('#addr').html('('+data.RECP_ZIP_CD+')'+' '+data.RECP_ADDR_1_NM+' '+data.RECP_ADDR_2_NM);
				var deliRqstSbjtContent = $('#deliRqstSbjtContent option:contains("'+data.DELI_RQST_SBJT_CONTENT+'")');
				if(!!deliRqstSbjtContent){
					deliRqstSbjtContent.select();
					//$('input[name=deliRqstSbjtContent]').val(deliRqstSbjtContent.text());
					$('input[name=deliRqstSbjtContent]').val(data.DELI_RQST_SBJT_CONTENT);
				}else{
					$('div.deliRqstSbjtArea').show();
				}
				$('input[name=giftSbjtContent]').val(data.GIFT_SBJT_CONTENT);
				$('input.countIn').trigger('keyup');
				
				$('input[name=setlCouponUseYn][value='+data.SETL_COUPON_USE_YN+']').check(true);
				$('input[name=deliCouponUseYn][value='+data.DELI_COUPON_USE_YN+']').check(true);
				if(data.ORD_AMT_DISP_YN == 'Y') $('#ordAmtDispYn').check(true);
				if(data.MILG_USE_YN == 'Y') $('#milgUseYn').check(true);
				if(data.DPAMT_USE_YN == 'Y') $('#dpamtUseYn').check(true);
				if(data.STAFF_DC_YN == 'Y') $('#staffDcYn').check(true);
			}
		});
}

//구매동의 올체크
var checkOrdAgree = function(obj){
	var flag = obj.checked;
	if($(obj).hasClass('checkAll')){
		var chkSet = $('input[allId='+$(obj).attr('id')+']');
		chkSet.each(function(){
			$(this).check(flag);
		});
	}else{
		var chkSet = $('input[allId='+$(obj).attr('allId')+']');
		var notFlagLen = chkSet.map(function(){ //체크되지 않은거 확인
			if(this.checked == false) return this;
		}).length;
		
		if(notFlagLen > 0) $('#'+$(obj).attr('allId')).check(false);
		else $('#'+$(obj).attr('allId')).check(true);
	}
};

function createNextDaily(){
	$('#nextPeriDeliDay').html("");
	var today = new Date();
	//var todayStr = today.getFullYear()+'.'+('0'+(today.getMonth()+1)).slice(-2)+'.'+('0'+today.getDate()).slice(-2);
	//var targetDay = new Date(today.setMonth(today.getMonth()+parseInt($('input[name=useMonth]:checked').val())));
	//				targetDay.setDate(targetDay.getDate() - 1);
	//var targetDayStr = targetDay.getFullYear()+'.'+('0'+(targetDay.getMonth()+1)).slice(-2)+'.'+('0'+targetDay.getDate()).slice(-2);
	var firstDeliDay;
	var tmpDeliDay;
	var deliStartDy;
	var firstDeliDayStr;
	var totalDays = '';

	var stdComp = 99;//비교 기준
	if($('input[name^=deliDay]:checked').length > 0){
		$('input[name^=deliDay]:checked').each(function(){
			var comp = $(this).attr('day')-today.getDay();
			if(comp < 3) comp+=7;//비교대상 요일이 3일 이전일 경우 7일 앞으로 보정해준다
			if(comp < stdComp){//오늘부터 3일 이후에 해당하는 첫 요일을 세팅
				firstDeliDay = new Date();
				//tmpDeliDay = new Date("09/30/2016");
				//if(firstDeliDay < tmpDeliDay){
					if(comp > 2){
						firstDeliDay = new Date(firstDeliDay.setDate(firstDeliDay.getDate()));
					}else{
						firstDeliDay = new Date(firstDeliDay.setDate(firstDeliDay.getDate()+7));
					}
				//}

				firstDeliDay = new Date(firstDeliDay.setDate(firstDeliDay.getDate()+comp));
				deliStartDy = firstDeliDay.getFullYear()+('0'+(firstDeliDay.getMonth()+1)).slice(-2)+('0'+firstDeliDay.getDate()).slice(-2);
				firstDeliDayStr = firstDeliDay.getFullYear()+'.'+('0'+(firstDeliDay.getMonth()+1)).slice(-2)+'.'+('0'+firstDeliDay.getDate()).slice(-2)+' ('+$(this).attr('txt')+')';
			}
			
			if(comp>2){
				stdComp = comp;	//더 최근 날짜를 쓰기 위해 차이를 저장
			}
			
		});	
		$('#nextPeriDeliDay').html(firstDeliDayStr);
		$('#deliStartDy').val(deliStartDy);
	}
}

function setMonth(month){
	var start_date_str = $('#deli_start_dy').val();
	var start_yyyy = parseInt(start_date_str.substring(0,4)); 
	var start_mm = parseInt(start_date_str.substring(4,6));
	var start_dd = parseInt(start_date_str.substring(6,8));
	
	//ex)1월 29일 신청시, 연장 기간이 08월 00일로 끝나는 오류 발생하여 수정
	//var start_date = new Date(start_yyyy, start_mm, start_dd, 0, 0, 0);
	//var extMonth = parseInt($('#use_month').val())+parseInt(month);
	//start_date.setMonth(start_date.getMonth()+extMonth);
	var start_date_str2 = start_mm +"/"+start_dd+"/"+start_yyyy;
	var start_date = new Date(start_date_str2);
	var extMonth = parseInt($('#use_month').val())+parseInt(month);
	start_date.setMonth((start_date.getMonth()+1)+extMonth);
	
	var ext_yyyy = ""+start_date.getFullYear();
	var tmpmm = start_date.getMonth();
	if(tmpmm == 0){
		tmpmm = 12;	
	}
	var ext_mm = ("0"+tmpmm).slice(-2);
	var ext_dd = ("0"+(start_date.getDate()-1)).slice(-2);
	
	$('#useMonth').val(extMonth);
	
	//var dispStr = start_yyyy+'.'+start_mm+'.'+start_dd+' ~ '+ext_yyyy+'.'+ext_mm+'.'+ext_dd+"까지 연장";
	var dispStr = start_yyyy+'.'+start_date_str.substring(4,6)+'.'+start_date_str.substring(6,8)+' ~ '+ext_yyyy+'.'+ext_mm+'.'+ext_dd+"까지 연장";
	$('span.extend_deli_date').html(dispStr);
	$('#useMonthNmV').val(dispStr);
}

//2017.02.06 이승남
function keyPressEnt2(val){
	re = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
	var temp=$("#"+val).val();
	if(re.test(temp)){
		alert("한글, 영문, 숫자만 입력할 수 있습니다.");
		$("#"+val).val(temp.replace(re,""));
	}
}	

function keyPressEnt(val){
	re = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
	var temp=$("#"+val).val();
	if(re.test(temp)){
		alert("특수 문자는 입력할 수 없습니다.");
		$("#"+val).val(temp.replace(re,""));
	}
}

//2017.01.24 이승남
//이메일 주소 유효성 검사.
function chkEmail(email) {
	var regExp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	return regExp.test(email);
}

//휴대폰번호 유효성 검사.
function chkMobileNumber(number) {
	var regExp = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;
	if (regExp.test(number) == false) {
		return false;
	}
	return true;
}
//전화번호 유효성 검사.
function chkPhoneNumber(number) {
	var regExp = /^\d{2,4}-\d{3,4}-\d{4}$/;
	if (regExp.test(number) == false) {
		return false;
	}
	return true;
}
//특수문자 포함 유효성 검사.
function containsChars(input, chars){
  for (var i=0; i < input.length; i++){
        if (chars.indexOf(input.charAt(i)) != -1){
               return true;
        }
  }
  return false;
}
//----------------------------

function periAddrChg(){
    window.open(_LMAppUrl+"/mymart/popup/selectPeriDeliveryForm.do?periDeliId="+periDeliId+"&products01Cnt="+$("#products01Cnt").val(), "PopMyMartDeliveryAddrForm", "width=800,height=700,toolbar=no,location=no,directories=no,status=no,scrollbars=no,resizable=no,menubar=no, dependent=yes");
}

//page on load
$(function() {
	

	//선택된 정기배송코드 세팅
	periDeliId = $('input[name=periDeliId]', '#periManager').val();
	
	//기존 건 선택 처리
	if(periDeliId != ""){
		$('div.img-select-type2 > a').html($('a[periDeliId='+periDeliId+']').html());
	}
	
	//기존 건 클릭
	$('ul.periAddr > li > a').on("click", function(){
		getExistingPeri($(this).attr('periDeliId'));
	});
	
	$(document).on('click.f', '.layerpop-trigger2', function (event) {
		 layerpopTriggerBtn( this, event );
	});
	
	$(document).on('change', '.select-type1 select', function (event) {
		var select_name = $(this).children('option:selected').text();
		$(this).siblings('label').text(select_name);
	});
	
	$('#optionTable').on('click', 'input[type=radio], input[type=checkbox]', function (event) {
		$(this).check(this.checked);
	});
	
	$(document).on('change', '#deliTermSelect', function(){
		if($('#deliTermSelect option:selected').val() == "30"){
			$(".closeday").hide();
			$("input[name^=deliDay]").parent().addClass("active");
			$("input[name^=deliDay]").prop("checked",true);
		}else{
			$(".closeday").show();
			$("input[name^=deliDay]").parent().removeClass("active");
			$("input[name^=deliDay]").prop("checked",false);
		}
		createNextDaily();
	});
	
	$(document).on('click', 'input[name^=deliDay]', function(){
		if($('#deliTermSelect option:selected').val() == "30"){
			alert("상품주기를 매일로 선택하셨습니다. \n상품주기를 먼저 변경해주세요.");
			$("input[name^=deliDay]").parent().addClass("active");
			$("input[name^=deliDay]").prop("checked",true);
			return;
		}
		createNextDaily();
	});	
	
	//메시지 문자 카운트
	$('input.countIn').on('keyup', function(){
		var tarId = $(this).attr('name')+'Cnt';
		$('#'+tarId).text(strCount(this.value));
	});

	//배송요청 선택 이벤트
	$('#deliRqstSbjtContent').on('change', function(){
		if($(this).val() == '01'){ //배송요청 직접입력
			//2017.02.06 이승남			
			$("#deliRqstSbjtContentCnt").html("0 / 30 글자");
			$('input[name=deliRqstSbjtContent]').val('');
			$('div.deliRqstSbjtArea').show();
		}else{
			$('div.deliRqstSbjtArea').hide();
			$('input[name=deliRqstSbjtContent]').val($('option:selected', this).text());
			$('input[name=deliRqstSbjtContent]').trigger('keyup');
		}
	});
	
	$('#btn-del').on('click', function(){
		$('input[name=deliRqstSbjtContent]').val('');;
	});
	
	//이메일 도메인 클릭 이벤트
	$('#email3').on('change', function(){
		$('#email2').val(this.value);
	});
	
	//임직원 마일리지 체크
	$('#staffDcYn').on('click', function(){
		if(this.checked) $('#milgUseYn').check(false);
	});
	//2017.02.07 이승남_LM25496
	$('#milgUseYn').on('click', function(){
		//if(this.checked) $('#staffDcYn').check(false);				
		if(this.checked){
			if($('#staffDcYn').is(':checked')){
				alert("임직원 할인 적용 시, 마일리지는 적용되지 않습니다. ");
				$('#milgUseYn').check(false);
			}
		}		
	});
	
	/**법인 체크 시
	 * 공통코드 OR015 중 일부 사용
	 * 일반 주문 시 대체 02, 취소 04, 예치금적립 03
	 * 법인 주문 시 대체 11, 취소가 없으므로 05로 대신함, 예치금적립 12  
	 */
	$('#coOrderYn').on('click', function(){
		//2017.02.07 이승남_LM25576
		if(!$("input:radio[id='insuffPprocMethodCd1']").is(":checked") && !$("input:radio[id='insuffPprocMethodCd2']").is(":checked") && !$("input:radio[id='insuffPprocMethodCd3']").is(":checked")){
			
			alert("품절상품 처리 방법을 먼저 선택해 주세요");
			$("input:checkbox[id='coOrderYn']").check(false);
			
		}else{
			if(this.checked){
				if($("input:radio[id='insuffPprocMethodCd2']").is(":checked")){ 
					if (confirm("법인 주문일 경우 부분취소는 재결제로 처리되며 고객센터를 통해 안내됩니다.")){
						$("input:checkbox[id='coOrderYn']").check(true);
						$('#insuffPprocMethodCd1').val('11');
						$('#insuffPprocMethodCd2').val('05');
						$('#insuffPprocMethodCd3').val('12');
					}else{
						$("input:checkbox[id='coOrderYn']").check(false);
						$('#insuffPprocMethodCd1').val('02');
						$('#insuffPprocMethodCd2').val('04');
						$('#insuffPprocMethodCd3').val('03');
					}
				}else{
						$('#insuffPprocMethodCd1').val('11');
						$('#insuffPprocMethodCd2').val('05');
						$('#insuffPprocMethodCd3').val('12');
				}
			}else{
				$('#insuffPprocMethodCd1').val('02');
				$('#insuffPprocMethodCd2').val('04');
				$('#insuffPprocMethodCd3').val('03');
			}
		}
		
	});
	
	//구매동의 체크 이벤트
	$('input[id^=ordAgmtAgree]').on('click', function(){
		checkOrdAgree(this);
	});
	
	//전화번호 이메일 기본 세팅
	$('select.defaultTarget').each(function(){
		$(this).trigger('change');
	});
	
	$('.cardChange').on('click', function(){
		if($(this).hasClass('active')) $('input', '#cardChange').prop('disabled', false);
		else $('input', '#cardChange').prop('disabled', true);
	});
	showExistingPeriInfo();
	
	//2017.01.23 이승남
	$("#btnCancel").on('click',function(){
		if(!confirm("변경된 내용을 저장하지 않고 취소하시겠습니까?")){
			return;
		}else{
			window.location.reload();
		}
	});	
});
