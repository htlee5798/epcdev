"use strict";

//정기배송일 설정 플래그
var periPeriod = false;

//휴대폰 입력 여부 플래그
var cellNumbers = false;

//동의여부 플래그
var agreeAll = false;

function goBasket() {
	window.location.href = '/basket/basketList.do?basketType=B';
}

//정기결제 기간
var aprvStartDy;
var aprvEndDy;


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
}

//유효성 체크
function validate(){
	
  	if($("#custNm").val() == ""){
		alert("보내는 분 이름을 입력해 주시기 바랍니다.");
		$("#custNm").focus();
		return false;
	}
	
	if(!periPeriod){
		$("#monthDiv").attr("tabindex", -1).focus();
		alert('정기배송일 설정을 완료해 주세요.'); 
		return false;
	}
	
	
	if($('input[name=hopeDeliTm]:checked').val() == undefined){
		//주문 시 배송시간 없으면 default check
		//$('input[name=hopeDeliTm][value=1]').check(true);
	}
	
  if($("#products01Cnt").val() > 0){
		if($('input[name^=hopeDeliTm]:checked').length < 1){
			$("#monthDiv").attr("tabindex", -1).focus();
			alert('매장상품 배송시간 설정을 완료해 주세요.'); 
			return false;
		}
	}
  
  if($("#basketTypeB").val() ==  '11' || $("#basketTypeB").val() ==  '10' ){	
	  
	  if(!$('input[name=insuffPprocMethodCd]').is(":checked")){
			$("#insuffPprocMethodDiv").attr("tabindex", -1).focus();
			alert('품절상품 처리 방법을 선택해 주세요.'); 
			return false;
		}	
	}
  

	
/*	$('.cellNo').each(function(){
		if(!this.value) {
			cellNumbers = false;
			$(this).focus();
			return false;
		}
		cellNumbers = true;
	});
	if(!cellNumbers){
		alert('주문자 정보의 휴대폰번호를 입력해 주세요.'); return false;
	}*/
	
	if($("#cellNo2").val() == ""){
		alert("주문자 정보의 휴대폰번호 가운데자리를 입력하여 주세요."); 
		$("#cellNo2").focus();
		return false;
	}
	
	if($("#cellNo1").val() == "010" && $.trim($("#cellNo2").val()).length < 3 ){
		alert("주문자 정보의 휴대폰번호 가운데자리를 3자 이상 입력해야 합니다."); 
		$("#cellNo2").focus();
		return false;
	}

	if($("#cellNo1").val() != "010" && $.trim($("#cellNo2").val()).length < 3 ){
		alert("주문자 정보의 휴대폰번호 가운데자리를 3자 이상 입력해야 합니다."); 
		$("#cellNo2").focus();
		return false;
	}
	
	if($("#cellNo3").val() == ""){
		alert("주문자 정보의 휴대폰번호 뒷자리를 입력하여 주세요."); 
		$("#cellNo3").focus();
		return false;
	}
	
	if($.trim($("#cellNo3").val()).length < 4 ){
		alert("주문자 정보의 휴대폰번호 뒷자리는 4자 이상 입력해야 합니다."); 
		$("#cellNo3").focus();
		return false;
	}
	
	// 2017.01.24 이승남
	var celNo = $("#cellNo1").val()+'-'+$("#cellNo2").val()+'-'+$("#cellNo3").val();
	if (chkMobileNumber(celNo) == false) {
		alert("주문자 정보의 휴대폰번호 형식이 잘못되었습니다.\n 다시 입력해 주세요.");
		$("#celNo3").focus();
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
	//2017.01.24 이승남
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

	//2017.01.25 이승남 fn_checkKorean
	if (containsChars($("#recpNm").val(), "!,*&^%$#@~;?")){
		alert("받는 분 이름은 한글,영문,숫자만 입력하실 수 있습니다.");
		$("#recpNm").focus();
		return false;
	}
	//-------------------------------------------
	if($("#recpCellNo2").val() == ""){
		alert("배송지 정보의 휴대폰번호 가운데자리를 입력하여 주세요."); 
		$("#recpCellNo2").focus();
		return false;
	}
	
	if($("#recpCellNo1").val() == "010" && $.trim($("#recpCellNo2").val()).length < 3 ){
		alert("배송지 정보의 휴대폰번호 가운데자리를 3자 이상 입력해야 합니다."); 
		$("#recpCellNo2").focus();
		return false;
	}

	if($("#recpCellNo1").val() != "010" && $.trim($("#recpCellNo2").val()).length < 3 ){
		alert("배송지 정보의 휴대폰번호 가운데자리를 3자 이상 입력해야 합니다."); 
		$("#recpCellNo2").focus();
		return false;
	}
	
	if($("#recpCellNo3").val() == ""){
		alert("배송지 정보의 휴대폰번호 뒷자리를 입력하여 주세요."); 
		$("#recpCellNo3").focus();
		return false;
	}
	
	if($.trim($("#recpCellNo3").val()).length < 4 ){
		alert("배송지 정보의 휴대폰번호 뒷자리는 4자 이상 입력해야 합니다."); 
		$("#recpCellNo3").focus();
		return false;
	}
	
	// 2017.01.24 이승남
	var telNum = $("#recpCellNo1").val()+'-'+$("#recpCellNo2").val()+'-'+$("#recpCellNo3").val();
	if (chkMobileNumber(telNum) == false) {
		alert("배송지 정보의 휴대폰번호 형식이 잘못되었습니다.\n 다시 입력해 주세요.");
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
	//2017.01.24 이승남
	// 전화번호 필수 X  and  유효성 검사
	if($("#recpTelNo2").val() != "" && $("#recpTelNo3").val() != ""){
		var telNum = $("#recpTelNo1").val()+'-'+$("#recpTelNo2").val()+'-'+$("#recpTelNo3").val();
		if (chkPhoneNumber(telNum) == false) {
			alert("전화번호 형식이 잘못되었습니다. 다시 입력해 주세요.");
			$("#recpTelNo1").focus();
			return false;
		}
	}            //2017.02.06 이승남
	$("[name=deliRqstSbjtContent]").on("change keyup", function(event) {
		var count = $('[name=deliRqstSbjtContent]').val().length;
		$('#deliRqstSbjtContentCnt').html("");
		$('#deliRqstSbjtContentCnt').html(count + " / 30 글자");        		
	});
	$("[name=giftSbjtContent]").on("change keyup", function(event) {
		var count = $('[name=giftSbjtContent]').val().length;
		$('#giftMsgCnt').html("");
		$('#giftMsgCnt').html(count + " / 30 글자");
	});
	//2017.02.06 이승남
	if($("#deliRqstSbjtContent").val() == 'default'){
		alert("배송 메시지를 선택해 주시기 바랍니다.");
		$("#deliRqstSbjtContent").focus();
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
	
	/*if(!$("input:radio[name=deliCouponUseYn]").is(":checked")){
		$("#spareDiv").attr("tabindex", -1).focus();
		alert('무료배송쿠폰자동사용 여부를 선택해 주세요.'); return false;
	}*/
	
	if($('a.category-unroll-type2').length == 0 || $('a.category-unroll-type2').hasClass('active')){
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
	}

	
	var agent = navigator.userAgent.toLowerCase();
	if ( (navigator.appName == 'Netscape' && navigator.userAgent.search('Trident') != -1) || (agent.indexOf("msie") != -1) ) {
	}else{
		alert("Internet Explorer에서만 정기배송 신청이 가능합니다.");
		return false;
	}
	
	setCombiner('cellNo');	//주문자 휴대
	setCombiner('email');	//주문자 이메일
	setCombiner('recpCellNo');	//받는이 휴대폰
	setCombiner('recpTelNo');	//받는이 전화번호

	return true;
}

//실행
function openPayment(){
	var form = document.ini;
	var buyername = $('input[name=custNm]').val();
	var buyertel = $('input[name=cellNo]').val().split(' ').join('');
	var buyeremail = $('input[name=email]').val();
	var productList = $('input[name=productList]').val();
	var price = $('input[name=price]').val();
	var goodname;
	var size = $('input[name=productList]').length;
	
	if(productList.length == 1){
		goodname = productList.val();
	}
	if(productList.length > 1){
		goodname = productList + '외 '+(size-1)+'건';
	}
	$('input[name=buyername]', form).val(buyername);
	$('input[name=buyertel]', form).val(buyertel);
	$('input[name=buyeremail]', form).val(buyeremail);
	$('input[name=goodname]', form).val(goodname);
	$('input[name=price]', form).val(price);
	$('input[name=ini_offer_period]', form).val(aprvStartDy+""+aprvEndDy);
	$('input[name=aprvStartDy]', form).val(aprvStartDy);
	$('input[name=aprvEndDy]', form).val(aprvEndDy);
	
	//결제 시도
	if(auth(form)){
		var params = {
				paymethod : $('input[name=paymethod]', form).val(),
				goodname : $('input[name=goodname]', form).val(),
				buyername : $('input[name=buyername]', form).val(),
				buyertel : $('input[name=buyertel]', form).val(),
				buyeremail : $('input[name=buyeremail]', form).val(),
				encrypted : $('input[name=encrypted]', form).val(),
				sessionkey : $('input[name=sessionkey]', form).val(),
		};
		
		$.getJSON("/peribasket/orderPayAuth.do", params)
			.done(function(data) {
				if (jResult(data)){
					if($('a.category-unroll-type2').length == 0){
						$(form).append($('<input>', {type:'hidden', name:'fixedSetlMethodNo', value:data.fixedSetlMethodNo}));
					}else{
						$('input[name=fixedSetlMethodNo]', form).val(data.fixedSetlMethodNo);
					}
					form.action = '/peribasket/orderDone.do';
					form.submit();
				}
			});
	}
}


function goSubmit(){  
	if($("#products01Cnt").val() > 0){
	}else{
		if($('input[name=insuffPprocMethodCd]').val() == undefined){
			if($("input[id=coOrderYn]").is(":checked")){
				$(document.ini).append($('<input>', {type:'hidden', name:'insuffPprocMethodCd', value:'05'}));
			}else{
				$(document.ini).append($('<input>', {type:'hidden', name:'insuffPprocMethodCd', value:'04'}));
			}
		}
	}
	var diverse = $('input[name=ordType]:checked').val();

	if(diverse ==  undefined){diverse = "new"}
	if(diverse == 'new'){
		if(validate()){
			if($('input[name=fixedSetlMethodNo]').length > 0){
				if($('input[name=cardCd]').val() != $("#cardcoCd").val()){
					openPayment();
				}else{
					document.ini.action="/peribasket/orderDone.do"
					document.ini.submit();
				}
			}else{
				openPayment();
			}
		}
	}else if(diverse == 'old'){
		//기존 신청 번호 세팅
		var activePeriOrderNo = $('input[name=exPeriDeliId]').val();
		if(!activePeriOrderNo){
			alert('이용 중인 정기배송 건을 선택하세요.');
			return;
		}
		if(confirm('이용중인 정기배송 주문건과 함께 받으시겠습니까?')){
		$('#basketAdd input[name=exPeriDeliId]').val(activePeriOrderNo);
		$("#basketAdd").append($("#prodList").html());
		$('*', '#oldTypeDiv').prop('disabled', false);	
		$('#basketAdd').get(0).submit();
		}
	}
}

//정기 배송 세팅 메시지
function createPeriNotice(){
	var today = new Date();
	var todayStr = today.getFullYear()+'.'+('0'+(today.getMonth()+1)).slice(-2)+'.'+('0'+today.getDate()).slice(-2);
	var targetDay = "";
	var targetDayStr = "";
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
				//	if(firstDeliDay < tmpDeliDay){
				if(comp > 2){
							firstDeliDay = new Date(firstDeliDay.setDate(firstDeliDay.getDate()));
						}else{
							firstDeliDay = new Date(firstDeliDay.setDate(firstDeliDay.getDate()+7));
						}
				//	}

				firstDeliDay = new Date(firstDeliDay.setDate(firstDeliDay.getDate()+comp));
				deliStartDy = firstDeliDay.getFullYear()+('0'+(firstDeliDay.getMonth()+1)).slice(-2)+('0'+firstDeliDay.getDate()).slice(-2);
				firstDeliDayStr = firstDeliDay.getFullYear()+'.'+('0'+(firstDeliDay.getMonth()+1)).slice(-2)+'.'+('0'+firstDeliDay.getDate()).slice(-2)+' ('+$(this).attr('txt')+')';
			}
			
			if(comp>2){
			stdComp = comp;	//더 최근 날짜를 쓰기 위해 차이를 저장
			}
			//요일 세팅
			if(!!totalDays) totalDays = totalDays + ','; 
			totalDays = totalDays + $(this).attr('txt');
		});
		targetDay = new Date(firstDeliDay.setMonth(firstDeliDay.getMonth()+parseInt($('input[name=useMonth]:checked').val())));
		//targetDay.setDate(targetDay.getDate() - 1);
		targetDayStr = targetDay.getFullYear()+'.'+('0'+(targetDay.getMonth()+1)).slice(-2)+'.'+('0'+targetDay.getDate()).slice(-2);
		
		$('#period').html(firstDeliDayStr + ' ~ ' + targetDayStr + ' ('+$('input[name=useMonth]:checked').val()+'개월)');
		
		$('#interval').html($('select[name=deliTermCd] option:selected').text() + ' / ' + totalDays+'요일 배송');

		if($('input[name=hopeDeliTm]:checked').attr('txt') != undefined){
			$("#firstDayTimeNm").html("<strong>첫 정기배송 가능일 : <strong class='txt-wbrown firstDay'>"+firstDeliDayStr+"</strong> / 매장상품 배송시간 : <strong class='txt-wbrown' id='firstTime'>"+ $('input[name=hopeDeliTm]:checked').attr('txt') +"</strong></strong>");
		}else{		
			$("#firstDayTimeNm").html("<strong>첫 정기배송 가능일 : <strong class='txt-wbrown firstDay'>"+firstDeliDayStr+"</strong></strong>");
		}
		$('.firstDay').html(firstDeliDayStr);
		//$('#firstTime').html($('input[name=hopeDeliTm]:checked').attr('txt'));
		$("#firstDayNm").show();
		$('#deliStartDy').val(deliStartDy);
	}else{
		$('.firstDay').html("");
		$("#firstDayNm").hide();
	}
	

	
	//결제 기간 세팅
	
	aprvStartDy = todayStr.split('.').join('');
	aprvEndDy = targetDayStr.split('.').join('');
}

//정기배송 세팅 완료시 안내
function showPeriNotice(){
	createPeriNotice();	//메시지 작성
	if(!!$('input[name=useMonth]:checked').val() &&
			//!!$('input[name=hopeDeliTm]:checked').val() &&
			!!$('select[name=deliTermCd]').val() &&
			$('input[name^=deliDay]:checked').length > 0){
		$('#settingOff').hide();
		$('#settingOn').show();
		periPeriod = true;
	}else{
		$('#settingOn').hide();
		$('#settingOff').show();
		periPeriod = false;
	}
}

function emptyForm(form){
	$('input', form).parent().removeClass('active');
	$('input[type=checkbox]', form).check('reset');
	$('input[type=radio]', form).check('reset');
	$('input[type=text]', form).val('');
	
	$('select option', form).prop('selected', false);
	$('select', form).trigger('change');
	
	$('#settingOn').hide();
	$('#settingOff').show();
	periPeriod = false;
	
	$('#ordType1').prop('checked', true).parent().addClass('active');
}

//기존 정기배송 로딩
function getExistingPeri(exPeriDeliId, exOrderBsketType) {
	$('input[name=exPeriDeliId]').val(exPeriDeliId);
	emptyForm($('#basketListForm').get(0));
	
	if(!exPeriDeliId){
		return;
	}
	var params = {
			exPeriDeliId: exPeriDeliId
	};	
	
	$.getJSON("/peribasket/api/existingPeriOrder.do", params)
		.done(function(data) {
			if (!!data) {
				$('*', '#basketListForm').prop('disabled', false);
				if(data.USE_MONTH == "3" || data.USE_MONTH == "6" || data.USE_MONTH == "12" ){
					$('input[name=useMonth][value='+data.USE_MONTH+']').check(true);
				}else{
					$('input[name=useMonth][value=12]').check(true);
				}
				
				$('#deliTermSelect option[value='+data.DELI_TERM_CD+']').select();
				if(data.SUN_DELI_YN == 'Y') $('#deliDay1Yn').check(true);
				if(data.MON_DELI_YN == 'Y') $('#deliDay2Yn').check(true);
				if(data.TUE_DELI_YN == 'Y') $('#deliDay3Yn').check(true);
				if(data.WED_DELI_YN == 'Y') $('#deliDay4Yn').check(true);
				if(data.THU_DELI_YN == 'Y') $('#deliDay5Yn').check(true);
				if(data.FRI_DELI_YN == 'Y') $('#deliDay6Yn').check(true);
				if(data.SAT_DELI_YN == 'Y') $('#deliDay7Yn').check(true);
				$('input[name=hopeDeliTm][value='+data.HOPE_DELI_TM+']').check(true);
				showPeriNotice();
				$('#period').html(data.PERIOD_START + ' ~ ' + data.PERIOD_END + ' ('+data.USE_MONTH+'개월)');
				
				
				if(data.INSUFF_PPROC_METHOD_CD != null){//기 업체 배송일 경우 null값으로 넘어와 법인 value값으로 셋팅되는것을 막기 위해 추가
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
				}
				
					
					//기 업체 배송에 매장 배송 함께 받기의 경우 품절(부부취소 default check)
					if(data.INSUFF_PPROC_METHOD_CD == null && data.ORDER_BASKET_TYPE == "21"){
						$('input[name=insuffPprocMethodCd][value=04]').check(true);
					}else{
					$('input[name=insuffPprocMethodCd][value='+data.INSUFF_PPROC_METHOD_CD+']').check(true);
					}
					

				if((data.ORDER_BASKET_TYPE == "11" || data.ORDER_BASKET_TYPE == "10") || ($("#basketTypeB").val() == "11" || $("#basketTypeB").val() == "10")){ 
					$("#insuffPprocMethodH").show();
					$("#insuffPprocMethodDiv").show();
					$("#coOrderYnDiv").show();
					$("#coOrderYnUl").show();
					$("#giftSbjtTr").show();
				}else{
					if($("#products01Cnt").val() > 0){
						$("#insuffPprocMethodH").show();
						$("#insuffPprocMethodDiv").show();
						$("#coOrderYnDiv").show();
						$("#coOrderYnUl").show();
					}else{
						$("#insuffPprocMethodH").hide();
						$("#insuffPprocMethodDiv").hide();
						$("#coOrderYnDiv").hide();
						$("#coOrderYnUl").hide();
					}
					$("#giftSbjtTr").hide();
				}
				//$('#custNm').html(data.CUST_NM);
				$('input[name=custNm]').val(data.CUST_NM);
				var cellNo1 = data.CELL_NO.substring(0,4).trim();
				var cellNo2 = data.CELL_NO.substring(4,8).trim();
				var cellNo3 = data.CELL_NO.substring(8,12).trim();
				$('#cellNo1 option[value='+cellNo1+']').select();
				$('#cellNo2').val(cellNo2);
				$('#cellNo3').val(cellNo3);
				var email1 = data.EMAIL.split('@')[0];
				var email2 = data.EMAIL.split('@')[1];
				var email3 = $('#email3 option[value="'+email2+'"]');
				$('#email1').val(email1);
				$('#email2').val(email2);
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
				if (data.RECP_TEL_NO != null && data.RECP_TEL_NO != ""){
					var recpTelNo1 = data.RECP_TEL_NO.substring(0,4).trim();
					var recpTelNo2 = data.RECP_TEL_NO.substring(4,8).trim();
					var recpTelNo3 = data.RECP_TEL_NO.substring(8,12).trim();
					$('#recpTelNo1 option[value='+recpTelNo1+']').select();
					$('#recpTelNo2').val(recpTelNo2);
					$('#recpTelNo3').val(recpTelNo3);
				}
				
				
				$('#addr').html('('+data.RECP_ZIP_CD+')'+' '+data.RECP_ADDR_1_NM+' '+data.RECP_ADDR_2_NM);
				var deliRqstSbjtContent = $('#deliRqstSbjtContent option:contains("'+data.DELI_RQST_SBJT_CONTENT+'")');
				
				if(!!deliRqstSbjtContent){//Revision 70296에서 바뀐거 원복
					//if(deliRqstSbjtContent.val() == '03'){
					if(deliRqstSbjtContent.val() != '01' && deliRqstSbjtContent.val() != undefined){
					deliRqstSbjtContent.select();
					}else{
						$('#deliRqstSbjtContent option:contains("직접입력")').select();
					}
					//$('input[name=SdeliRqstSbjtContent]').val(deliRqstSbjtContent.text());
					$('input[name=deliRqstSbjtContent]').val(data.DELI_RQST_SBJT_CONTENT);
				}else{
					$('div.deliRqstSbjtArea').show();
				}
				$('input[name=giftSbjtContent]').val(data.GIFT_SBJT_CONTENT);
				if(!!data.ORD_AMT_DISP_YN) $('#ordAmtDispYn').check();
				$('input[name=setlCouponUseYn][value='+data.SETL_COUPON_USE_YN+']').check(true);
				$('input[name=deliCouponUseYn][value='+data.DELI_COUPON_USE_YN+']').check(true);
				if(data.MILG_USE_YN == 'Y') $('#milgUseYn').check(true);
				if(data.DPAMT_USE_YN == 'Y') $('#dpamtUseYn').check(true);
				if(data.STAFF_DC_YN == 'Y') $('#staffDcYn').check(true);
				$("#fixedSetlMethodNo").val(data.FIXED_SETL_METHOD_NO);
				$("#cardcoCd").val(data.CARDCO_CD);
				//var fixedSetlMethodNo = $('#fixedSetlMethodNo option[value="'+data.FIXED_SETL_METHOD_NO+'"]');
				//if(!!fixedSetlMethodNo) fixedSetlMethodNo.select();
				if(data.ORD_AGMT_AGREE_YN == 'Y') $('#ordAgmtAgree').click();
				
				//$('*', '#basketListForm').prop('disabled', true);
				$('*', '#oldTypeDiv').prop('disabled', true);
				$('*', '#spareDiv').prop('disabled', true);
				$('*', '#fixedSetlMethodSec1').prop('disabled', true);
				$('*', '#fixedSetlMethodSec2').prop('disabled', true);
				
				$('.btnTgt', '#basketListForm').prop('disabled', false);
				
				// 신규 신청하기 라디오 버튼 활성화
				//$('#ordType2').attr('disabled', false);
				$('input[name=ordType]', '#basketListForm').prop('disabled', false);
				$('#allProdView').prop('disabled', false);
				
				//기존 배송에 근거리 배송이 있는지 확인
				//(울릉도 같이 자사택배로 권역 외 등을 걸러내기 위함)
				//기존 근거리 배송이 있거나 현재 매장배송이 있는경우 배송시간을 보여줌(김포 권역 외는 근거리 배송 및 매장배송 타입에 해당하지 않음)
				if(data.PRO_01 > 0 || $("#products01Cnt").val() > 0){
					$("#deliTimeTr").show();
					$("#giftSbjtContent_div1").css('display','block');
					$("#giftSbjtContent_div2").css('display','block');
				}else{
					$("#deliTimeTr").hide();
					$("#giftSbjtContent_div1").css('display','none');
					$("#giftSbjtContent_div2").css('display','none');			
				}
				
				
				//(기존신청)매장 배송시간 임의지정
				if(!$("input:radio[name='hopeDeliTm']").is(":checked")){
					$('input[name=hopeDeliTm][value=1]').check(true);
					$('input[name=hopeDeliTmOld]').val("1");
				}

				//(기존신청)품절상품 처리방법 임의지정
				if(!$("input:radio[name='insuffPprocMethodCd']").is(":checked") && $("#products01Cnt").val() > 0){
					$('input[name=insuffPprocMethodCd][value=04]').check(true);
					$('input[name=insuffPprocMethodCd][value=05]').check(true);
				}

				//(기존신청)주문서 금액 표기 (기신청, 신규신청 상관없이 매장 배송이 들어 있으면 주문금액표기 노출, 품절처리방법과 동일)
				if((data.ORDER_BASKET_TYPE == "11" || data.ORDER_BASKET_TYPE == "10") || ($("#basketTypeB").val() == "11" || $("#basketTypeB").val() == "10")){
				//if(data.ORDER_BASKET_TYPE == "11" || data.ORDER_BASKET_TYPE == "10"){
					$("#ordAmtDispYn_a").css('display','block'); 
					$("#ordAmtDispYn_b").css('display','block');
					$("#ordAmtDispYn_c").css('display','none'); 
					if(data.ORD_AMT_DISP_YN == 'Y'){
						$('#ordAmtDispYn').check(true);
						$("#ordAmtDispYn_a").addClass("active");
					}
				}else{
					$("#ordAmtDispYn_a").css('display','none'); 
					$("#ordAmtDispYn_b").css('display','none');
					$("#ordAmtDispYn_c").css('display','block'); 
					
				}
				
				// 선택된 옵션은 리스트에서 제거
				$("a[name=selId]").removeClass("disabled");
				$("#selId_"+data.PERI_ID).addClass("disabled");
			}
		});
}

//2017.01.24 이승남
//이메일 주소 유효성 검사.
function chkEmail(email) {
	var regExp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	return regExp.test(email);
}

// 휴대폰번호 유효성 검사.
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
function changeDeliLoc(){
  //if(confirm('장바구니로 돌아갑니다.\n계속 하시겠습니까?')){
	if(confirm('배송지를 변경하시려면 장바구니로 돌아가야 합니다.\n장바구니로 이동하시겠습니까?')){	
		goBasket();	
	}
}

function changeAmtArea() {
	
	var idx = 0;
	
	$("a[name^=openDC]").each(function() {
		idx = $("a[name^=openDC]").index(this);
		var subId = $("a[name^=openDC]").eq(idx).attr('subid');

		var sellAmt = $("a[name^=openDC]").eq(idx).attr('sellAmt');
		var dcamtWithoutStaffDc = $("a[name^=openDC]").eq(idx).attr('discountAmt');
		var staffDcAmt = $("a[name^=openDC]").eq(idx).attr('staffDcAmt');
		
		var discountAmt = 0;		
		var buyAmt = 0;
		
		if($('#staffDcYn').is(':checked')) {
			discountAmt = Number(dcamtWithoutStaffDc)+Number(staffDcAmt);		
		} else {
			discountAmt = dcamtWithoutStaffDc;			
		}	
		
		buyAmt = Number(sellAmt) - Number(discountAmt);
		
		$("#discountAmt_" + subId).text(numberWithCommas(discountAmt));
		$("#buyAmt_" + subId).text(numberWithCommas(buyAmt) + '원');		
		
		if(discountAmt > 0) $("a[name^=openDC]").eq(idx).show();
		else $("a[name^=openDC]").eq(idx).hide(); 
	});
}	

//2017.02.02 이승남
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

$(function() {
	if($("#products01Cnt").val() > 0){
	//if('${param.baskettype }' == '11' || '${param.baskettype}' =='10'){
		$("#deliTimeTr").show();
	}else{
		$("#deliTimeTr").hide();
	}
	
	//기존/신규 라디오 컨트롤
	
	$('input[name=ordType]').on('click', function(){
		if(this.value == 'old')	{
			//2017.02.02 이승남 - 기존 신청건과 함께 받기 클릭 시 임직원 할인 체크 불가능
			$('#staffDcYn').prop('disabled', true);
			
			emptyForm($('#basketListForm').get(0));
			$('.ordTypeNew').hide();
			$('.ordTypeOld').show();
			if($("#products01Cnt").val() > 0){
		   //if('${param.baskettype }' == '11' || '${param.baskettype}' =='10'){
			$("#deliTimeTr").show();
			}
		}
		if(this.value == 'new')	{
			//2017.02.02 이승남 - 기존 신청건과 함께 받기 클릭 시 임직원 할인 체크 불가능 풀기
			$('#staffDcYn').prop('disabled', false);
			
			//location.reload();		
			$('*', '#basketListForm').prop('disabled', false);
			var form = $('#basketListForm').get(0);
			form.submit();
		}
	});
	
	if($("#chkOrdType").val() == "old"){
		$('input[name=ordType][value=old]').click();
	}
	
	//기존 신청 기본 세팅
	if($('ul.periAddr li').length == 1){
		//기존 건 없을 경우 disable
		//$('#ordType1').prop('disabled', true);
		$(".wrap-set-complex-align").hide();
		$("#oldperiNoti").hide();
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

	//구매동의 체크 이벤트
	$('input[id^=ordAgmtAgree]').on('click', function(){
		checkOrdAgree(this);
	});
	
	//정기배송 세팅이벤트1
	$('input[name=useMonth]').on('click', function(){
		showPeriNotice();
	});
	
	//정기배송 세팅이벤트2
	$('input[name^=deliDay]').on('click', function(){
		if($('#deliTermSelect option:selected').val() == "30"){
			alert("상품주기를 매일로 선택하셨습니다. \n상품주기를 먼저 변경해주세요.");
			$("input[name^=deliDay]").parent().addClass("active");
			$("input[name^=deliDay]").prop("checked",true);
			return;
		}
		
		//	LM-22683: 20170119.김대홍
		if($('#deliDay1Yn').is(":checked") && $('#deliDay2Yn').is(":checked") && $('#deliDay3Yn').is(":checked")
			&& $('#deliDay4Yn').is(":checked") && $('#deliDay5Yn').is(":checked") 
			&& $('#deliDay6Yn').is(":checked") && $('#deliDay7Yn').is(":checked")) {
			
			
			
			$('#deliTermSelect option').each( function( i, v ) {
				var $option = $( v ),
					value = $option.val();
				
				
				if( value === '30' ){
					$option.prop( 'selected', true ).closest( 'div' ).find( 'label' ).html( $option.text() );
				} else {
					$option.prop( 'selected', false );
				}
			});
		}
		showPeriNotice();
	});

	//정기배송 세팅이벤트3
	$('select[name=deliTermCd]').on('change', function(){
		if($('#deliTermSelect option:selected').val() == "30"){
			$("#closedeliday").hide();
			$("input[name^=deliDay]").parent().addClass("active");
			$("input[name^=deliDay]").prop("checked",true);
		}else{
			$("#closedeliday").show();
			$("input[name^=deliDay]").parent().removeClass("active");
			$("input[name^=deliDay]").prop("checked",false);
		}
		
		showPeriNotice();
	});

	//정기배송 세팅이벤트4
	$('input[name=hopeDeliTm]').on('click', function(){
		showPeriNotice();
	});
	
	//임직원 마일리지 체크
	$('#staffDcYn').change(function(){
		if(this.checked) $('#milgUseYn').check(false);
		
		changeAmtArea();
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
	
	$('#btn-del').on('click', function(){
		$('input[name=deliRqstSbjtContent]').val('');;
	});
	
	//메시지 문자 카운트
/*	$('input.countIn').on('keyup', function(){
		var tarId = $(this).attr('name')+'Cnt';
		$('#'+tarId).text(strCount(this.value));
	});
	*/
	//배송요청 선택 이벤트
	$('#deliRqstSbjtContent').on('change', function(){
		//01인데 변경시 null로 들어오는 경우가 있음-추후 수정 필요
		if($(this).val() == '01' || $(this).val() == null){ //배송요청 직접입력
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
	
	//전화번호 이메일 기본 세팅
	$('select.defaultTarget').each(function(){
		$('option:eq(0)', this).trigger('change');
	});
	
	//이메일 도메인 클릭 이벤트
	$('#email3').on('change', function(){
		$('#email2').val(this.value);
	});
	
	/**법인 체크 시
	 * 공통코드 OR015 중 일부 사용
	 * 일반 주문 시 대체 02, 취소 04, 예치금적립 03
	 * 법인 주문 시 대체 11, 취소가 없으므로 05로 대신함, 예치금적립 12  
	 */
	$('#coOrderYn').on('click', function(){
		
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
	
	$('#insuffPprocMethodCd2').on('click', function(){
		if($("input:checkbox[id='coOrderYn']").is(":checked")){ 
			alert("법인 주문일 경우 부분취소는 재결제로 처리되며 고객센터를 통해 안내됩니다.");
		}
	});
	
	
	$('ul.periAddr > li > a').on("click", function(event){
		var exOrderBsketType = $(this).attr('orderBsketType');
		getExistingPeri($(this).attr('periDeliId'), exOrderBsketType);
	});
	
	jCellMerge('prodList', 'deliType');
	
	//결제 관련 이벤트
	$(document).on('focus', focus_control());
	enable_click();
	
	//2017.02.02 휴대폰 번호,전화번호 자동 포커스
	$(function() {
		$("#cellNo2").keyup (function () {
			re = /[~!@\#$%^&*\()\-=+']/gi;
			var temp=$("#cellNo2").val();
			if(re.test(temp)){ 
				$("#cellNo2").val(temp.replace(re,""));
			}				
			var charLimit = $(this).attr("maxlength");
			if (this.value.length >= charLimit) {									
			$('#cellNo3').focus();
			return false;
			}				
		});
	});
	$(function() {
		$("#recpCellNo2").keyup (function () {
			re = /[~!@\#$%^&*\()\-=+']/gi;
			var temp=$("#recpCellNo2").val();
			if(re.test(temp)){ 
				$("#recpCellNo2").val(temp.replace(re,""));
			}	
			var charLimit = $(this).attr("maxlength");
			if (this.value.length >= charLimit) {									
			$('#recpCellNo3').focus();
			return false;
			}
		});
	});	
	$(function() {
		$("#recpTelNo2").keyup (function () {
			re = /[~!@\#$%^&*\()\-=+']/gi;
			var temp=$("#recpTelNo2").val();
			if(re.test(temp)){ 
				$("#recpTelNo2").val(temp.replace(re,""));
			}	
			var charLimit = $(this).attr("maxlength");
			if (this.value.length >= charLimit) {									
			$('#recpTelNo3').focus();
			return false;
			}
		});
	});	
//-----------------------------------------------------------------------------
	
});
