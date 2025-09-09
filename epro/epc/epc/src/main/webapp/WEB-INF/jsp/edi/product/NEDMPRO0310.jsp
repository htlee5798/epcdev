<%--
	Page Name 	: NEDMPRO0310.jsp
	Description : 행사정보 등록
	Modification Information
	
	  수정일 			  수정자 				수정내용
	---------- 		---------    		-------------------------------------
	2025.03.07 		park jong gyu	 		최초생성
	
--%>
<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<title></title>

<script>
	let srchProdMtAll = "";	//판매코드 검색조건_마트상품
	let srchProdSuper = "";		//판매코드 검색조건_슈퍼상품
	<c:choose>
		<c:when test="${not empty proEventInfo.vkorg}">
			<c:choose>
				<c:when test="${proEventInfo.vkorg eq 'KR02'}">
				srchProdMtAll="Y";
				</c:when>
				<c:when test="${proEventInfo.vkorg eq 'KR04'}">
				srchProdSuper="Y";
				</c:when>
				<c:otherwise>
				srchProdMtAll="Y";
				srchProdSuper="Y";
				</c:otherwise>
			</c:choose>	
		</c:when>
		<c:otherwise>	/*  (default) */
		srchProdMtAll="Y"
		</c:otherwise>
	</c:choose>
	let trCount = 100;
	let stDy = "${proEventInfo.ofsdt}";		// 행사시작일
	let purStDy = "${proEventInfo.prsdt}";	// 발주시작일
	$(document).ready(function() {
		
		//$('.formDiv').hide();	// 서식 아이템 영역
		$('#prodEvntNewYn').val('Y'); // 파트너사 행사 신규여부
		
		// 행사구분 코드
		let reqContyp = "${proEventInfo.reqContyp}";
		let taskGbn ="${taskGbn}";
		
		//$("select[name='werksType']").empty();
		
		// KR02 : 롯데마트
		// 대상점포에 값 넣기
		//$("select[name='werksType']").append(
		//		$("#werksTypeOrd option[value='01'], #werksTypeOrd option[value='02'], #werksTypeOrd option[value='03'], #werksTypeOrd option[value='04']").clone());
		
		// 원매가 행사 공문
		if(reqContyp == '00' || reqContyp == '' && taskGbn != 'pro'){
			$('input[name="docType"]').val('01');
			$("select[name='reqType'], select[name='reqDisc']").empty();
			$("select[name='reqType']").append($("#reqTypeOrd option[value='8001']").clone());	// 행사유형에 원매가인하 값 넣기
			$("select[name='reqDisc']").append($("#reqDiscOrd option[value='99']").clone());	// 할인유형에 원가인하 값 넣기
			//$("select[name='costType']").append($("#costTypeOrd option[value='01']").clone());	// 비용적용방식에 원가인하 값 넣기
			if(taskGbn == 'orgPri') $("select[name='reqContyp']").attr('disabled', true);
			$('.formDiv, .from1').show(); // 서식 아이템 영역 show, 서식 1	번 show
			$('.fromTd, .from2, .from3, .from4, .from5, .from6, .venRateTh').hide(); // 서식 1번 제외한 나머지 서식 hide
		}else if(taskGbn == 'pro'){
			// 판촉 행사 신청
			$('input[name="docType"]').val('03');
			$('.fromTd').show();
			//$('.formDiv').hide();	// 서식 아이템 영역
			$("select[name='reqType'], select[name='reqDisc']").empty();
			$("select[name='reqType']").append($("#reqTypeOrd > option").clone());
			$("select[name='reqType'] option[value='8001'], select[name='reqContyp'] option[value='00']").remove();
			$("select[name='reqDisc']").append($("#reqDiscOrd option[value='01']").clone());
			$("select[name='reqContyp']").attr('disabled', false);
			$('.from2').show(); // 서식 2번 show
			$('.from3, .from4, .from5, .from6').hide();	// 서식 2번 제외한 나머지 서식 hide
		}
		
		// 행사 데이터 세팅~
		if("${proEventInfo}" != ''){
			eventDataInfo();
		}else{
			if( $("select[name='reqContyp']").val() == '00' ){
				$('input[name="hdVenRate"]').val('');
			}else{
				$('input[name="hdVenRate"]').val('50');
			}
			$('input[name="hdVenRate"]').attr('disabled', true); // 파트너사 분담율
		}
		
		// 행사유형 change event!
		$("select[name='reqType']").change(function() {
			
			if(!confirm("모든 항목이 초기화 됩니다.\n진행하시겠습니까?")) return;
			
			$("select[name='costType'], #formTbody, select[name='reqDisc']").empty();	// 비용적용방식, 서식 아이템 row, 할인유형  초기화
			$('.formDiv, .venRateTh, .from1').show();	// 서식 아이템 영역, 서식 → 분담율 show
			$('.venRateText').text('분담율');
			
			// 할인유형에 따른 비용적용방식 적용(서식 3번부터 9번까지)
			if( $(this).val() != '8001' && $(this).val() != '4002' ){
				$("select[name='costType']").append($("#costTypeOrd option[value='01'], #costTypeOrd option[value='02']").clone());
			}
			
			// 원가 인하시 변경금액, 제안납품가, 기존납품가 show !
			//if( $("select[name='costType'] option:selected").val() == '01' ) $('#formTable .from1').show();
			//else $('#formTable .from1').hide(); // 원가 인하가 아니면 hide!
			
			if( $(this).val() == '1001' ||  $(this).val() == '1301' ||  $(this).val() == '1501'){
				// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
				$("select[name='reqDisc']").append($("#reqDiscOrd option[value='01']").clone());
				
				$('.from2').show(); // 서식 2번 show
				$('.from3, .from4, .from5, .from6').hide();	// 서식 2번 제외한 나머지 서식 hide
				$('input[name="docType"]').val('03');
			}else if( $(this).val() == '1002' ){
				// 번들
				$("select[name='reqDisc']").append(
						$("#reqDiscOrd option[value='01'], #reqDiscOrd option[value='03'], #reqDiscOrd option[value='04']").clone());	// 할인유형에 정액판매가,정액할인,정율할인 값 넣기
				$('.from3').show(); // 서식 3번 show
				$('.from2, .from4, .from5, .from6').hide(); // 서식 3번 제외한 나머지 서식 hide
				$('.venRateText').text('상품별 분담율');
				
				if( $("select[name='reqDisc'] option:selected").val() == '01' )	$('input[name="docType"]').val('04');	// 번들 + 정액판매가
				else if( $("select[name='reqDisc'] option:selected").val() == '03' )	$('input[name="docType"]').val('05'); // 번들 + 정액할인
				else if( $("select[name='reqDisc'] option:selected").val() == '04' )	$('input[name="docType"]').val('06'); // 번들 + 정율할인
				
			}else if( $(this).val() == '1003' ){
				// M+N
				$("select[name='reqDisc']").append($("#reqDiscOrd option[value='05']").clone());	// 할인유형에 m+n 값 넣기
				
				$('.from4').show();	// 서식 4번 show
				$('.from2, .from3, .from5, .from6').hide();	// 서식 4번 제외한 나머지 서식 hide
				$('input[name="docType"]').val('07');
			}else if( $(this).val() == '1004' ){
				// 연관
				$("select[name='reqDisc']").append($("#reqDiscOrd option[value='01'], #reqDiscOrd option[value='03']").clone());	// 할인유형에 정액판매가, 정액할인 값 넣기
				$('.from2, .from5').show();	// 서식 2번, 5번 show
				$('.from3, .from4, .from6').hide();	// 서식 2번, 5번 제외한 나머지 서식 hide
				if( $("select[name='reqDisc'] option:selected").val() == '03' )		$('input[name="docType"]').val('08');
				else if( $("select[name='reqDisc'] option:selected").val() == '01' ) $('input[name="docType"]').val('09');
			}else if( $(this).val() == '4002' ){
				// 상품권
				$("select[name='reqDisc']").append($("#reqDiscOrd option[value='41']").clone());	// 할인유형에 상품권금액 값 넣기
				$("select[name='reqDisc']").append($("#reqDiscOrd option[value='42']").clone());	// 할인유형에 사은품금액 값 넣기
				$("select[name='costType']").append($("#costTypeOrd option[value='02']").clone()); 	// 비용적용방식에 사후정산 값 넣기
				$('.from6').show();	// 서식 6번 show
				$('.from1, .from2, .from3, .from4, .from5').hide(); // 서식 6번 제외한 나머지 서식 hide
				if( $("select[name='reqDisc'] option:selected").val() == '41' )		$('input[name="docType"]').val('10');
				else if( $("select[name='reqDisc'] option:selected").val() == '42' ) $('input[name="docType"]').val('11');
			}else{
				//$('.formDiv').hide();	// 서식 아이템 영역
			}
		}); // end 행사유형 change event!
		
		
		// 비용적용방식 change event!
		/*$("select[name='costType']").change(function() {
			$('.from1, .venRateTh').hide();
			
			// 원가 인하시 서식 1번 show
			if( $(this).val() == '01' )	{
				$('.from1').show();
			}else if( $(this).val() == '02' ){
				$('.venRateTh').show();
			}
		}); // end 비용적용방식 change event!*/
		
		// 행사구분 change event!
		$("select[name='reqContyp']").change(function() {
			
			//$("#formTbody").empty();	// 서식 아이템 row 초기화
			
			$.each($("#proEvent").find("input[type='text'][name]"), function(idx, node) {
		        //if( $(node).attr("name") != 'reqOfrcd')	$(node).val("");
		        if( $(node).attr("name") == 'hdVenRate')	$(node).val("");
		    });
			
			if( $(this).val() == '00' ){
				// 원가 인하시
				$('.fromTd').hide();
				$('.formDiv').show();	// 서식 아이템 영역 show
				$("select[name='reqType'], select[name='reqDisc'], select[name='costType']").empty();	// 행사유형, 서식 아이템 row 초기화
				$("select[name='reqType']").append($("#reqTypeOrd option[value='8001']").clone());	// 행사유형에 원매가인하 값 넣기
				$("select[name='reqDisc']").append($("#reqDiscOrd option[value='99']").clone());	// 할인유형에 원가인하 값 넣기
				$("select[name='costType']").append($("#costTypeOrd option[value='01']").clone());	// 비용적용방식에 원가인하 값 넣기
				
				$('.from1').show(); // 서식 1	번 show
				$('.from2, .from3, .from4, .from5, .from6, .venRateTh').hide(); // 서식 1번 제외한 나머지 서식 hide
			}else{
				$('.fromTd').show();
				//$('.formDiv').hide();	// 서식 아이템 영역
				//$("select[name='reqType']").append($("#reqTypeOrd > option").clone());
				//$("select[name='reqDisc']").append($("#reqDiscOrd > option").clone());
				//$("select[name='costType']").append($("#costTypeOrd > option").clone());
				$("select[name='reqType'] option[value='8001'], select[name='reqDisc'] option[value='99']").remove();
				
				$('input[name="hdVenRate"]').attr('disabled', false); // 파트너사 분담율
				
				if( $(this).val() == '01' ){
					$('input[name="hdVenRate"]').val('50');
					$('input[name="hdVenRate"]').attr('disabled', true); // 파트너사 분담율
				}else if( $(this).val() == '03' ){
					$('input[name="hdVenRate"]').val('100');
					$('input[name="hdVenRate"]').attr('disabled', true); // 파트너사 분담율
				}
			}
		}); // end 행사구분 change event!
		
		// 계열사 change event!
		let bfVkorg = "";
		let afVkorg = "";
		$("select[name='vkorg']").on("focus", function(){
			bfVkorg = $.trim($(this).val());
		}).change(function() {
			afVkorg = $.trim($(this).val());
			
			//데이터 있을 때만 체크
			var datalen = $("#formTbody tr").length;
			if(datalen == 0){
				//계열사 구분 변경 시 공통 이벤트처리
				fncChgVkorg();
				return;
			}
			
			//이전선택값 존재하면서 이후선택값이 달라졌을 경우,
			if(bfVkorg != "" && bfVkorg != afVkorg){
				if(!confirm("검색조건 변경 시, 입력한 서식 리스트가 초기화 됩니다.\n진행하시겠습니까?")){
					//취소 시, 이전선택값 재셋팅
					$(this).val(bfVkorg);
					return; 
				}
				
				//계열사 구분 변경 시 공통 이벤트처리
				fncChgVkorg();
				//기존 데이터 초기화
				$("#formTbody").empty();
			}
			
			//$("select[name='werksType']").empty();
						
			/*if( $(this).val() == 'KR02' ){
				// KR02 : 롯데마트
				// 대상점포에 값 넣기
				$("select[name='werksType']").append(
						$("#werksTypeOrd option[value='01'], #werksTypeOrd option[value='02'], #werksTypeOrd option[value='03'], #werksTypeOrd option[value='04']").clone());	
			}else{
				// KR04 : 롯데슈퍼
				$("select[name='werksType']").append(
						$("#werksTypeOrd option[value='11'], #werksTypeOrd option[value='12'], #werksTypeOrd option[value='13'], #werksTypeOrd option[value='14'], #werksTypeOrd option[value='15']").clone());	
			}*/
		}); // end 계열사 change event!
		
		// 할인유형 change event!
		$("select[name='reqDisc']").change(function() {
			let reqType = $("select[name='reqType'] option:selected").val();
			let reqDisc = $(this).val();
			if( reqType == '1002' ){
				if(reqDisc == '01'){
					// 번들 + 정액판매가
					$('.discntTh1').text('행사판매가1(원)');
					$('.discntTh2').text('행사판매가2(원)');
					$('.discntTh3').text('행사판매가3(원)');
					$('input[name="docType"]').val('04');	// 번들 + 정액판매가
				}else if(reqDisc == '03'){
					// 번들 + 정액할인
					$('.discntTh1').text('할인액1(원)');
					$('.discntTh2').text('할인액2(원)');
					$('.discntTh3').text('할인액3(원)');
					$('input[name="docType"]').val('05');	// 번들 + 정액판매가
				}else if(reqDisc == '04'){
					// 번들 + 정율할인
					$('.discntTh1').text('할인율1(%)');
					$('.discntTh2').text('할인율2(%)');
					$('.discntTh3').text('할인율3(%)');
					$('input[name="docType"]').val('06');	// 번들 + 정액판매가
				}
			}else if( reqType == '1004'){
				if(reqDisc == '03')	$('input[name="docType"]').val('08');	/// 연관 + 정액할인
				else	$('input[name="docType"]').val('09');	/// 연관 + 정액판매가
				$.each($("#formTbody tr"), function(idx, item) {
					let reqSalesPrc = $(this).find('input[name=reqSalesPrc]').val().replace(/,/g, ""); 	// 제안 판매가
					let salesPrc = $(this).find('input[name=salesPrc]').val().replace(/,/g, ""); 		// 기존 판매가
					if(reqSalesPrc == '' && salesPrc == '') return;
					if(reqDisc == '03'){
						// 연관 + 정액할인
						$(this).find('.calSalesPrc').text( (reqSalesPrc - salesPrc).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") ); // 변경금액 계산
					}else{
						// 연관 + 정액판매가 01
						$(this).find('.calSalesPrc').text( (reqSalesPrc ).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") ); // 변경금액 계산
					}
				});
			}else if( reqType == '4002'){
				if(reqDisc == '41') $('input[name="docType"]').val('10');	/// 증정상품권/사은품 + 상품권금액 41
				else	$('input[name="docType"]').val('11');	/// 증정상품권/사은품 + 사은품금액 42
			}
		}); // end 할인유형 change event!
		
		// 행사요청 change event!
		$("select[name='reqOfr']").change(function() {
			
		}); // end 행사요청 change event!
		
		// 대상점포 change event!
		$("select[name='werksType']").change(function() {
			
		}); // end 대상점포 change event!
		
		// 제안파트너사 분담율 change event!
		$("input[name='hdVenRate']").change(function() {
			let reqContyp = $("select[name='reqContyp'] option:selected").val();
			let initVal = 'N';	
			if(reqContyp == '02'){
				if( $(this).val() > 50){
					alert('판촉요청 합의서인 경우에는 50%이하만 입력이 가능합니다.');
					initVal = 'Y';
				}
			}else if(reqContyp == '04'){
				if( $(this).val() < 50 || $(this).val() > 100 ){
					alert('판촉요청 시행공문인 경우에는 50~100%만 입력이 가능합니다.');
					initVal = 'Y';
				}
			}else if(reqContyp == '05'){
				if( $(this).val() > 100 ){
					alert('판촉행사 신청서 공개모집인 경우에는 0~100%만 입력이 가능합니다.');
					initVal = 'Y';
				}
			}
			if( initVal == 'Y' ){
				$(this).val('');
				$(this).focus();
				$("#formTbody tr").find('input[name=venRate]').val('');
			}
		}); // end 제안파트너사 분담율 change event!
		
		// 전체 check!
		$("#allCheck").click(function() {
			if($("#allCheck").is(":checked")) $("input[name=chk]").prop("checked", true);
			else $("input[name=chk]").prop("checked", false);
		});
		
		$('#subTxt').keydown(function() {
		    if (event.keyCode == 13) {
	    		event.preventDefault();
	    		btnEvent('theme', this);
		    }
		});
		
		$(document).on("click", "#proEvent img.datepicker", function(){
			let dayInput = $(this).prev("input")[0];
			let objId = dayInput.id;
			let objVal = $.trim(dayInput.value);
			let objName = $.trim($(this).prev().attr('name'));
			
			if( objName == 'ofsdt'){
				let now = new Date( stDy );	// 등록일 기준 날짜
				now.setMonth( now.getMonth() + 1 );
				let year = now.getFullYear(); // 년도
				let month = ('0' + now.getMonth()).slice(-2);  
				let day = ('0' + (now.getDate()+6)).slice(-2);
				let startDay = ('0' + (now.getDate()+1)).slice(-2);
				openCalSetDt('proEvent.'+objId, objVal, "fncCallBackCalendar", stDy, year + '-' + month + '-' + day);
			}else{
				openCalSetDt('proEvent.'+objId, objVal, "fncCallBackCalendar", "", $('input[name="ofsdt"]').val() );
			}
		});
		
	});

	//날짜변경 callback
	function fncCallBackCalendar(tgObj, cbData){
		if(tgObj == undefined || tgObj == null) return;
		
		let tgId = $.trim(tgObj.id);
		if(tgId.startsWith("srch")) return;	//검색조건 내 캘린더일 경우 return
		
		//Row 상태 변경처리
		let tgRow = tgObj.closest("tr");
		if(tgRow == undefined || tgRow == null) return;
		
		//현재 rowStat 
		let currStat = $.trim(tgRow.attr("data-rowStat"));
		
		//기등록된 데이터일 경우
		if(currStat == "R"){
			//수정상태 변경
			tgRow.attr("data-rowStat", "U");
		}
	}
	
	// 행추가, 행삭제
	function rowBtnEvent(gbn){
		// 행추가(Add), 행삭제(Del)
		
		let rowData = {
				'reqType' : $("select[name='reqType'] option:selected").val()
			, 	'costType' : $("select[name='costType'] option:selected").val()
			,	'trCount'	: trCount
		};
		
		// 행추가
		if(gbn == 'Add'){
			
			// checkTdm 이 있을 때만 초기화 실행
			if ( $("#formTbody tr").length > 49 ) {
				alert('행추가는 최대 50줄까지만 가능합니다.');
				return;
			}
			
			$("#formTemplate").tmpl(rowData).appendTo("#formTbody");
			trCount++;
			$('.from1').show();
			// 행사 유형
			if( rowData.reqType == '1001' ||  rowData.reqType == '1301' ||  rowData.reqType == '1501'){
				// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
				$('.from2').show();
				$('.from3, .from4, .from5, .from6').hide();
			}else if( rowData.reqType == '1002' ){
				// 번들
				$('.from3').show();
				$('.from2, .from4, .from5, .from6').hide();
				if( $("select[name='reqDisc'] option:selected").val() == '01' ){
					// 번들 + 정액판매가
					$('.discntTh1').text('행사판매가1(원)');
					$('.discntTh2').text('행사판매가2(원)');
					$('.discntTh3').text('행사판매가3(원)');
				}else if( $("select[name='reqDisc'] option:selected").val() == '03' ){
					// 번들 + 정액할인
					$('.discntTh1').text('할인율1(%)');
					$('.discntTh2').text('할인율2(%)');
					$('.discntTh3').text('할인율3(%)');
				}else if( $("select[name='reqDisc'] option:selected").val() == '04' ){
					// 번들 + 정율할인
					$('.discntTh1').text('할인액1(원)');
					$('.discntTh2').text('할인액2(원)');
					$('.discntTh3').text('할인액3(원)');
				}
			}else if( rowData.reqType == '1003' ){
				// M+N
				$('.from4').show();
				$('.from2, .from3, .from5, .from6').hide();
			}else if( rowData.reqType == '1004' ){
				// 연관
				$('.from2, .from5').show();
				$('.from3, .from4, .from6').hide();
			}else if( rowData.reqType == '4002' ){
				// 상품권
				$('.from6').show();
				$('.from1, .from2, .from3, .from4, .from5').hide();
			}else if( rowData.reqType == '8001' ){
				// 원매가 인하
				$('.from2, .from3, .from4, .from5, .from6, .venRateTh').hide();
			}
			
			//$('.from1, .venRateTh').hide();
			
			// 원가 인하시 서식 1번 show
			//if( rowData.costType == '01' )	$('.from1').show();
			//else if( rowData.costType == '02' ) $('.venRateTh').show();
			// 아이템 분담율 세팅
			if($("select[name='reqContyp'] option:selected").val() == '01' || $("select[name='reqContyp'] option:selected").val() == '03' ) {
				let venRate = $("select[name='reqContyp'] option:selected").val() == '01' ? '50' : '100';
				$('input[name="venRate"]').val(venRate);
				$('input[name="venRate"]').attr('disabled', true); // 파트너사 분담율
			}else{
				$('input[name="venRate"]').val( $('input[name="hdVenRate"]').val() );
			}
			
		}else{
			if( !$('#formTbody tr').find('input:checked').is(':checked') ){
				alert("삭제 할 Row를 먼저 선택해주세요!");
				return;
			}
			
			if(!confirm("<spring:message code='msg.common.confirm.delete'/>")) return;
			// 행삭제
			let msg = '<spring:message code="msg.common.success.delete" />';
			$('#formTbody tr').find('input:checked').each(function(index){
				if( $(this).closest('tr').find('input[name=rowAttri]').val() == 'search' ){
					// server
					let searchInfo = {};
					//$(this).closest('tr').find('input[name=rowAttri]').val('del');
					$(this).closest('tr').find('input , select').map(function() {
						searchInfo[this.name] = $(this).val().replaceAll("/","");
					});
					searchInfo['delYn'] = 'Y';
					$.ajax({
						contentType : 'application/json; charset=utf-8',
						type : 'post',
						dataType : 'json',
						url : '<c:url value="/edi/product/deleteProEventAppItem.json"/>',
						data : JSON.stringify(searchInfo),
						success : function(data) {
							if(data.result == false) msg = '저장에 실패했습니다.\n다시 확인해주세요.';
						}
					});
				}
				$(this).closest('tr').remove();
			});
			alert(msg);
		}
	}
	
	// 행사 데이터 세팅~
	function eventDataInfo(){
		$("select[name='reqContyp']").attr('disabled', true); // 행사구분
		$("select[name='vkorg']").attr('disabled', true); // 계열사
		$('#prodEvntNewYn').val('N'); // 파트너사 행사 신규여부
		$('.formDiv, .from1').show();
		$("select[name='costType'], select[name='reqDisc']").empty();	// 비용적용방식, 할인유형  초기화
		
		// 할인유형에 따른 비용적용방식 적용(서식 3번부터 9번까지)
		if( "${proEventInfo.reqType}" != '8001' && "${proEventInfo.reqType}" != '4002' ){
			$("select[name='costType']").append($("#costTypeOrd option[value='01'], #costTypeOrd option[value='02']").clone());
		}
		
		// 행사 유형
		if( "${proEventInfo.reqType}" == '1001' ||  "${proEventInfo.reqType}" == '1301' ||  "${proEventInfo.reqType}" == '1501'){
			// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
			$("select[name='reqDisc']").append($("#reqDiscOrd option[value='01']").clone());
			$('.from2').show();
			$('.from3, .from4, .from5, .from6').hide();
		}else if( "${proEventInfo.reqType}" == '1002' ){
			// 번들
			$("select[name='reqDisc']").append(
					$("#reqDiscOrd option[value='01'], #reqDiscOrd option[value='03'], #reqDiscOrd option[value='04']").clone());	// 할인유형에 정액판매가,정액할인,정율할인 값 넣기
			$('.from3').show();
			$('.from2, .from4, .from5, .from6').hide();
			$('.venRateText').text('상품별 분담율');
			
			if( "${proEventInfo.reqDisc}" == '01' ){
				// 번들 + 정액판매가
				$('.discntTh1').text('행사판매가1(원)');
				$('.discntTh2').text('행사판매가2(원)');
				$('.discntTh3').text('행사판매가3(원)');
			}else if( "${proEventInfo.reqDisc}" == '03' ){
				// 번들 + 정액할인
				$('.discntTh1').text('할인율1(%)');
				$('.discntTh2').text('할인율2(%)');
				$('.discntTh3').text('할인율3(%)');
			}else if( "${proEventInfo.reqDisc}" == '04' ){
				// 번들 + 정율할인
				$('.discntTh1').text('할인액1(원)');
				$('.discntTh2').text('할인액2(원)');
				$('.discntTh3').text('할인액3(원)');
			}
			
		}else if( "${proEventInfo.reqType}" == '1003' ){
			// M+N
			$("select[name='reqDisc']").append($("#reqDiscOrd option[value='05']").clone());	// 할인유형에 m+n 값 넣기
			$('.from4').show();
			$('.from2, .from3, .from5, .from6').hide();
		}else if( "${proEventInfo.reqType}" == '1004' ){
			$("select[name='reqDisc']").append($("#reqDiscOrd option[value='01'], #reqDiscOrd option[value='03']").clone());	// 할인유형에 정액판매가, 정액할인 값 넣기
			// 연관
			$('.from2, .from5').show();
			$('.from3, .from4, .from6').hide();
		}else if( "${proEventInfo.reqType}" == '4002' ){
			// 상품권
			$("select[name='reqDisc']").append($("#reqDiscOrd option[value='41']").clone());	// 할인유형에 상품권금액 값 넣기
			$("select[name='reqDisc']").append($("#reqDiscOrd option[value='42']").clone());	// 할인유형에 사은품금액 값 넣기
			$("select[name='costType']").append($("#costTypeOrd option[value='02']").clone()); 	// 비용적용방식에 사후정산 값 넣기
			$('.from6').show();
			$('.from1, .from2, .from3, .from4, .from5').hide();
			$.each($("#formTbody tr"), function(idx, item) {
				let ofrStd = $(this).find('input[name="ofrStd"]').val();
				$(this).find('input[name="ofrStd"]').val(setComma(ofrStd));
			});
		}else if( "${proEventInfo.reqType}" == '8001' ){
			// 원매가 인하
			$("select[name='costType']").append($("#costTypeOrd option[value='01']").clone()); 	// 비용적용방식에 원가인하 값 넣기
			$("select[name='reqDisc']").append($("#reqDiscOrd option[value='99']").clone());	// 할인유형에 원가인하 값 넣기
			$('.from1').show();
			$('.from2, .from3, .from4, .from5, .from6, .venRateTh').hide();
		}
		
		//$('.from1, .venRateTh').hide();
		
		// 비용적용방식 ::: 원가 인하시 서식 1번 show
		//if( "${proEventInfo.costType}" == '01' )	$('.from1').show();
		//else if( "${proEventInfo.costType}" == '02' ) $('.venRateTh').show();
		
		$("select[name='vkorg']").val( "${proEventInfo.vkorg}" );			// 계열사
		$("select[name='reqContyp']").val( "${proEventInfo.reqContyp}" );	// 행사구분
		//$("select[name='reqOfr']").val( "${proEventInfo.reqOfr}" );			// 행사요청
		$("select[name='reqType']").val( "${proEventInfo.reqType}" );		// 행사유형
		$("select[name='reqDisc']").val( "${proEventInfo.reqDisc}" );		// 할인유형
		$("select[name='costType']").val( "${proEventInfo.costType}" );		// 비용적용방식
		$("select[name='zdeal']").val( "${proEventInfo.zdeal}" );			// 거래형태
		// 원매가 공문이 아닐경우 행사유형에서 원매가인하 삭제
		if($("select[name='reqContyp'] option:selected").val() != '00'){
			// 합의서 50이거나 100이면 분담율 수정 x
			if($("select[name='reqContyp'] option:selected").val() == '01' || $("select[name='reqContyp'] option:selected").val() == '03' ) {
				$('input[name="venRate"]').attr('disabled', true); // 아이템 파트너사 분담율
				$('input[name="hdVenRate"]').attr('disabled', true); // 파트너사 분담율
			}
			$("select[name='reqType'] option[value='8001']").remove();
		}
			
		// KR04 : 롯데슈퍼
		if($("select[name='vkorg'] option:selected").val() == 'KR04'){
			//$("select[name='werksType']").empty();
			
			//$("select[name='werksType']").append(
			//		$("#werksTypeOrd option[value='11'], #werksTypeOrd option[value='12'], #werksTypeOrd option[value='13'], #werksTypeOrd option[value='14'], #werksTypeOrd option[value='15']").clone());	
		}
		//$("select[name='werksType']").val( "${proEventInfo.werksType}" );	// 대상점포
	}
	
	// 변경금액 계산
	function fncValChk(obj, event){
		
		if( $(obj).val().length <= 0 ) return;
	
		let reqPurPrc = $(obj).closest("tr").find('input[name=reqPurPrc]').val().replace(/,/g, ""); 		// 제안 납품가
		let purPrc = $(obj).closest("tr").find('input[name=purPrc]').val().replace(/,/g, ""); 				// 기존 납품가
		let reqSalesPrc = $(obj).closest("tr").find('input[name=reqSalesPrc]').val().replace(/,/g, ""); 	// 제안 판매가
		let salesPrc = $(obj).closest("tr").find('input[name=salesPrc]').val().replace(/,/g, ""); 			// 기존 판매가
		let reqType = $("select[name='reqType'] option:selected").val();
		
		if( $(obj).attr("name") == 'reqPurPrc' || $(obj).attr("name") == 'purPrc' ){
			// 제안 납품가(원), 기존 납품가(원)
			if( reqPurPrc == '' && purPrc == '' ){
				// 제안 납품가(원), 기존 납품가(원) 둘다 빈칸일경우, 변경금액 원,율 항목 초기화!
				$(obj).closest("tr").find('.calPurPrc').text('');
				$(obj).closest("tr").find('.calComRate').text('');
			}
			
			// 변경금액 계산
			$(obj).closest("tr").find('.calPurPrc').text( (reqPurPrc - purPrc).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") );
			
			// % 계산
			$(obj).closest("tr").find('.calComRate').text( Math.trunc((reqPurPrc - purPrc) / purPrc * 100) );
		}else{
			// 제안 판매가(원), 기존 판매가(원) 둘다 빈칸일경우, 변경금액 원,율 항목 초기화!
			if( reqSalesPrc == '' && salesPrc == '' ){
				$(obj).closest("tr").find('.calSalesPrc').text('');
				$(obj).closest("tr").find('.calSalesRate').text('');
			}
			
			// 연관일경우
			if( reqType == '1004' ){
				if( $("select[name='reqDisc'] option:selected").val() == '03' )	{
					// 변경금액 계산
					$(obj).closest("tr").find('.calSalesPrc').text( (reqSalesPrc - salesPrc).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") );
					
				}else if( $("select[name='reqDisc'] option:selected").val() == '01' ){
					// 변경금액 계산
					$(obj).closest("tr").find('.calSalesPrc').text( (reqSalesPrc ).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") );
				}
			}else{
				// 변경금액 계산
				$(obj).closest("tr").find('.calSalesPrc').text( (reqSalesPrc - salesPrc).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") );
			}
			// % 계산
			$(obj).closest("tr").find('.calSalesRate').text( Math.trunc((reqSalesPrc - salesPrc) / salesPrc * 100) );
		}
	}
	
	// btn event
	function btnEvent(gbn, obj){
		// 업로드 양식 다운로드(excelFrom), 판매바코드 찾기(srcBarcode), 행사 테마 조회(theme), 팀 조회(depCd), 엑셀 업로드(excelUpl)
		if(gbn == 'excelFrom'){
			// 업로드 양식 다운로드
			let  f = document.hiddenForm;
			$("#hiddenForm input[name='reqType']").val( $("select[name='reqType'] option:selected").val() ); 	// 행사유형
			$("#hiddenForm input[name='costType']").val( $("select[name='costType'] option:selected").val() ); 	// 비용적용방식
			$("#hiddenForm input[name='reqDisc']").val( $("select[name='reqDisc'] option:selected").val() ); 	// 할인유형
			f.action = "<c:url value='/edi/product/selectProEventAppExcelFromDown.do' />";
	    	f.submit();
		}else if(gbn == 'srcBarcode'){
			// 판매바코드 찾기
			Common.centerPopupWindow( "<c:url value='/edi/product/selSrcmkCdPopup.do'/>?srcmkCd="+$(obj).closest('tr').find('input[name=ean11]').val() 
					+ "&trNum=" + $(obj).closest('tr').attr('class')
					+ "&srchProdMtAll=" + srchProdMtAll
					+ "&srchProdSuper=" + srchProdSuper
					+ "&srchPurDept=" + $("select[name='vkorg'] option:selected").val()
					, 'popup', {width: 980, height: 700});
			
		}else if(gbn == 'theme'){
			// 행사 테마 조회
			Common.centerPopupWindow( "<c:url value='/edi/product/selectProdEvntThm.do'/>?subTxt=" + $('input[name="subTxt"]').val()
					+ "&vkorg=" + $("select[name='vkorg'] option:selected").val()
					, 'popup', {width: 980, height: 700});
		}else if(gbn == 'depCd'){
			// 팀 조회
			let pData = [];

			let params = "";
			Object.keys(pData).forEach(function(k, i){
			  params = params+(i===0?"?":"&")+k+"="+pData[k];  
			});

			// 팝업 URL 생성
			let targetUrl = "<c:url value='/edi/product/selEcsReceptionistPopup.do'/>" + "?" + params;

			// 팝업 오픈
			Common.centerPopupWindow(targetUrl, 'ecsReceptionistPopup', { width: 980, height: 700 });
		}else if(gbn == 'excelUpl'){
			// 업로드
			let targetUrl = '<c:url value="/edi/comm/commExcelUploadPopupInit.do"/>';
			let excelSysKnd = "310";	//엑셀 업로드 행사제안 구분값
			let repVendorId = "<c:out value='${epcLoginVO.repVendorId}'/>";
			
			Common.centerPopupWindow(targetUrl+"?excelSysKnd="+excelSysKnd+"&entpCd="+repVendorId, 'excelUploadPopup', {width : 730, height : 350});
		}
	}
	
	// btn Server event
	function btnServerEvent(gbn, obj){
		// 저장(sava), 삭제(del), 행사요청(eventReq), 공문서&계약서( ecsIntgr )
		
		let searchInfo = {};
		let itemList = [];
		let url = "";
		$("input[name='reqTypeTxt']").val( $("select[name='reqType'] option:selected").text() );		// 행사유형
		$("input[name='costTypeTxt']").val( $("select[name='costType'] option:selected").text() );		// 비용적용방식
		$("input[name='reqContypTxt']").val( $("select[name='reqContyp'] option:selected").text() );	// 행사구분
		$("input[name='vkorgTxt']").val( $("select[name='vkorg'] option:selected").text() );			// 계열사
		$("input[name='reqDiscTxt']").val( $("select[name='reqDisc'] option:selected").text() );		// 할인유형
		//$("input[name='reqOfrTxt']").val( $("select[name='reqOfr'] option:selected").text() );			// 행사요청
		//$("input[name='werksTypeTxt']").val( $("select[name='werksType'] option:selected").text() );	// 대상점포
		$("input[name='zdealTxt']").val( $("select[name='zdeal'] option:selected").text() );			// 거래형태
		
		$('#proEvent').find('input , select').map(function() {
			if( this.name == 'ofsdt' || this.name == 'ofedt' || this.name == 'prsdt' || this.name == 'predt' )	searchInfo[this.name] = $(this).val().replace(/[^0-9]/g, "");
			else searchInfo[this.name] = $(this).val();
		});
		
		$.each($("#formTbody tr"), function(idx, item) {
			let itemObj = {};
			$.each($(this).find('input , select'), function(idx, item) {
	      		itemObj[this.name] = item.value;
			});
			itemObj['calPurPrc'] = $(this).find('.calPurPrc').text();
			itemObj['calComRate'] = $(this).find('.calComRate').text();
			itemObj['calSalesPrc'] = $(this).find('.calSalesPrc').text();
			itemObj['calSalesRate'] = $(this).find('.calSalesRate').text();
			if( searchInfo.reqType == '1004' ) itemObj['ofrStd'] = $(this).find('.ofrStd').text(); // 연관
			else if( searchInfo.reqType == '4002' ) itemObj['ofrStd'] = $(this).find('input[name="ofrStd"]').val(); // 상품권 
			itemList.push(itemObj);
      	});
		searchInfo['itemList'] = itemList;
		searchInfo['rfcParam'] = JSON.stringify(searchInfo);
		
		//console.log(searchInfo);
		
		if(gbn == 'save'){
			// 저장
			if(!confirm("<spring:message code='msg.srm.alert.tempSave'/>")) return;
			if(!validation(searchInfo))return;
			url = "<c:url value='/edi/product/insertProEventApp.json'/>";
			searchInfo['delYn'] = 'N';
			searchInfo['apprStatus'] = '01'; // 요청대기
		}else if(gbn == 'del'){
			// 삭제
			if(!confirm("<spring:message code='msg.common.confirm.delete'/>")) return;
			url = "<c:url value='/edi/product/deleteProEventApp.json'/>";
			searchInfo['delYn'] = 'Y';
		}else if(gbn == 'eventReq'){
			// 행사요청
			if(!confirm("<spring:message code='msg.common.confirm.appr.req'/>")) return;
			if(!validation(searchInfo))return;
			url = "<c:url value='/edi/product/insertProEventAppRfcCall.json'/>";
			searchInfo['apprStatus'] = '02'; // 요청완료
		}else if(gbn == 'ecsIntgrMt' || gbn == 'ecsIntgrMd' || gbn == 'ecsIntgrCs' ){
			// 공문서&계약서 작성( ecsIntgrMt : 마트, ecsIntgrMd : 슈퍼, ecsIntgrCs : 씨에스유통 )
			if(!confirm("공문서&계약서 작성을 하시겠습니까?")) return;
			
			let reqContyp = $("select[name='reqContyp'] option:selected").val();
			// 공문이면
			if( reqContyp == '00' || reqContyp == '03' || reqContyp == '04' || reqContyp == '05'  ){
				Common.centerPopupWindow( "<c:url value='/edi/product/selectEcsUserId.do'/>?reqOfrcd="+$("input[name='reqOfrcd']").val() 
						+ "&vkorg=" + $("select[name='vkorg'] option:selected").val()
						+ "&taskGbn=" + gbn
						, 'popup', {width: 900, height: 700});
				return;
			}else{
				url = "<c:url value='/edi/product/insertProdEcsIntgr.json'/>";
				searchInfo['apprStatus'] = '05'; // 전자결재진행
				searchInfo['taskGbn'] = gbn;
			}
		}
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			url : url,
			data : JSON.stringify(searchInfo),
			success : function(data) {
				// 공문서 작성
				if(gbn == 'ecsIntgrMt' || gbn == 'ecsIntgrMd' || gbn == 'ecsIntgrCs'){
					if(data.result== true){
						let specs = 
							"height="+ $(window).height() +
							",width=820"+
							",top=20"+
							",left="+($(window).scrollLeft() + ($(document).width() - 1200) / 2) +
							",scrollbars=yes"+
							",resizable=yes";
						
						/*if( $("select[name='vkorg'] option:selected").val() == 'KR04' ){
							window.open(data.url1, "sendPopup1", specs);
							window.open(data.url2, "sendPopup2", specs);
						}else{
							window.open(data.url1, "sendPopup1", specs);
						}*/
						window.open(data.url, "sendPopup", specs);
					}else{
						alert(data.msg);
					}
				}else{
					// 저장(sava), 삭제(del), 행사요청(eventReq)
					alert(data.msg);
					
					if(data.result== true){
						let  f = document.hiddenForm;
						$("#hiddenForm input[name='reqOfrcd']").val( data.reqOfrcd ); // 행사코드
						// 저장, 행사요청, 공문서작성 일시 page reload
						if(gbn == 'save' || gbn == 'eventReq')	f.action = "<c:url value='/edi/product/selectProEventAppDetail.do' />";
						else f.action = "<c:url value='/edi/product/NEDMPRO0300.do' />";
						f.submit();
					}
				}
			}
		});// end ajax
	}
	
	// 검증
	function validation(info){
		let ofsdt = $("input[name=ofsdt]").val().replace(/[^0-9]/g, "");
		let ofedt = $("input[name=ofedt]").val().replace(/[^0-9]/g, "");
		let prsdt = $("input[name=prsdt]").val().replace(/[^0-9]/g, "");
		let predt = $("input[name=predt]").val().replace(/[^0-9]/g, "");
		let reqType = $("select[name='reqType'] option:selected").val();
		let reqDisc = $("select[name='reqDisc'] option:selected").val();
		let purChk = true;
		let salesChk = true;
		let venRateChk = true;
		let ean11Chk = true;
		
		if( $("input[name=subNo]").val() == '' ){
			alert("행사 테마를 선택해주세요!");
			return false;
		}
		
		if( $("input[name=depCd]").val() == '' ){
			alert("팀을 선택해주세요!");
			return false;
		}
		
		if( $("input[name=reqOfrTxt]").val() == '' ){
			alert("행사명을 입력해주세요!");
			$("input[name=reqOfrTxt]").focus();
			return false;
		}

		if( $("input[name=reqPurTxt]").val() == '' ){
			alert("행사목적을 입력해주세요!");
			$("input[name=reqPurTxt]").focus();
			return false;
		}
		
		if($("select[name='reqContyp'] option:selected").val() != '00'){
			if( $("input[name=ofrCost]").val() == '' || $("input[name=ofrCost]").val() == '0'){
				alert("예상판촉비용을 입력해주세요!");
				$("input[name=ofrCost]").focus();
				return false;
			}
		}
		
		//날짜 필수값 체크
		if(ofsdt == "" || ofedt == ""){
			alert("행사기간 <spring:message code='msg.common.fail.nocalendar'/>");
			return false;
		}
		
		//날짜 필수값 체크
		if(prsdt == "" || predt == ""){
			alert("발주기간 <spring:message code='msg.common.fail.nocalendar'/>");
			return false;
		}
		
		//시작일보다 작은지 여부 체크
	    if (ofsdt > ofedt) {
	        alert("행사기간은 <spring:message code='msg.common.fail.calendar'/>");
	        return false;
	    }
		
	    if (prsdt > predt) {
	        alert("발주기간은 <spring:message code='msg.common.fail.calendar'/>");
	        return false;
	    }

		if( $("input[name=reqOfrDesc]").val() == '' ){
			alert("요청사항을 입력해주세요!");
			$("input[name=reqOfrDesc]").focus();
			return false;
		}
		
		if ( !$("#formTbody tr").length > 0) {
			alert('판매바코드를 추가해주세요.');
	    	return false;
		}
		
		let msg = 'S';
	    // 행사가는 정상가 보다 높을 수 없음
	    $.each($("#formTbody tr"), function(idx, item) {
	    	let matnr = $(this).find('input[name=matnr]').val();								// 상품코드
	    	let purPrc = $(this).find('input[name=purPrc]').val().replace(/,/g, "");			// 정상 원가
	    	let reqPurPrc = $(this).find('input[name=reqPurPrc]').val().replace(/,/g, "");		// 행사 원가
	    	let salesPrc = $(this).find('input[name=salesPrc]').val().replace(/,/g, "");		// 정상 판매가
	    	let reqSalesPrc = $(this).find('input[name=reqSalesPrc]').val().replace(/,/g, "");	// 행사 판매가
	    	let venRate = $(this).find('input[name=venRate]').val();	// 분담율
	    	let ean11 = $(this).find('input[name=ean11]').val();		// 판매바코드
	    	
	    	if( matnr == '' ) msg = '판매바코드 및 상품코드는 필수입니다.\n판매바코드를 선택해주세요';
	    	if( parseInt(purPrc) < parseInt(reqPurPrc) ) msg = '행사 원가는 정상 원가보다 높을 수 없습니다.';
	    	if( parseInt(salesPrc) < parseInt(reqSalesPrc) ) msg = '행사 판매가는 정상 판매가보다 높을 수 없습니다.';
	    	if( venRate > 100) msg = '분담율은 100보다 높을 수 없습니다.';
	    	if( ean11 == '') msg = '판매바코드를 선택해주세요.';
	    	// 상품권이 아니면
	    	if( reqType != '4002' ){
	    		if( purPrc == '') msg = '정상 원가를 입력해주세요.';
		    	if( reqPurPrc == '') msg = '행사 원가를 입력해주세요.';
			}
	    	// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501), 연관(1004)
	    	if( reqType == '1001' ||  reqType == '1301' ||  reqType == '1501' || reqType == '1004' ){
	    		if( salesPrc == '') msg = '정상 판매가를 입력해주세요.';
		    	if( reqSalesPrc == '') msg = '행사 판매가를 입력해주세요.';
	    	}
		});
	    if(msg != 'S'){
	    	alert(msg);
	    	return false;
	    }
	    return true;
	}
	
	//ECS 수신 담당자 콜백
	function setEcsReceiverNm(data) {
		if(data == null){
			alert("관리자 정보가 유효하지 않습니다. \n관리자에게 문의하세요.");
			return;
		}
		
		let empNo 		= $.trim(data.empNo); 
		let managerNm	= $.trim(data.managerNm);
		let teamCd		= $.trim(data.teamCd);
		let teamNm		= $.trim(data.teamNm);
		
		if(empNo == "" || empNo == null){
			alert("담당자 사번이 존재하지 않아 수신 담당자 설정이 불가능합니다.");
			return;
		}
		
		if(teamCd == "" || teamCd == null){
			alert("담당자의 팀 정보가 존재하지 않아 수신 담당자 설정이 불가능합니다.");
			return;
		} 

		//수신 담당자 정보 세팅
		$('input[name=empNo]').val(empNo);
		$('input[name=empTxt]').val(managerNm);
		$('input[name=depCd]').val( teamCd );	// 팀코드
		$('input[name=depTxt]').val( teamNm );	// 팀명
		$('input[name=srcDepTxt]').val( managerNm+'['+teamNm+']' );	// 화면 보여지는 팀명
	}
	
	// 판매 바코드 정보 return
	function setSellCd(data){
		if(data == null){
			alert("상품 데이터가 유효하지 않습니다.\n관리자에게 문의하세요.");
			return;
		}

		//callback data
		let trNum = data.trNum;	 //대상 row구분 class
		let srcmkCd = data.srcmkCd; //판매코드
		let prodCd = data.prodCd; //상품코드
		let prodNm = data.prodNm; //상품명
		let l1Cd = data.l1Cd;	 //대분류코드
		let l2Cd = data.l2Cd;	 //중분류코드
		let l3Cd = data.l3Cd;	 //소분류코드
		let l1Nm = data.l1Nm;	 //대분류코드명
		let l2Nm = data.l2Nm;	 //중분류코드명
		let l3Nm = data.l3Nm;	 //소분류코드명
		let orgCost = $.trim(data.orgCost)!=""?$.trim(data.orgCost):"0";	//기존원가
		let orgCostFmt = setComma(orgCost);	//기존원가 금액formatting
		let prodPatFg = data.prodPatFg;	//상품유형구분
		let dispUnit = data.dispUnit;	//표시단위
		let chkVal = true;
		
		$.each($("#formTbody tr"), function(idx, item) {
			if( $(this).find('input[name=matnr]').val() == prodCd ){
				alert('리스트에 상품코드가 존재합니다.');
				chkVal = false;
				return;
			}
		});
		
		if( chkVal ){
			$('.'+ trNum ).find('input[name=ean11]').val(srcmkCd);
			$('.'+ trNum ).find('input[name=matnr]').val(prodCd);
			$('.'+ trNum ).find('input[name=maktx]').val(prodNm);
			$('.'+ trNum ).find('input[name=purPrc]').val(orgCost.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
		}
		
	}
	
	// 행사 테마번호 return
	function setProdEvntThm(data){
		if(data == null){
			alert("행사 테마번호 데이터가 유효하지 않습니다.\n관리자에게 문의하세요.");
			return;
		}
		purStDy = data.purStDy;	// 발주시작일
		
		let now = new Date( data.stDy );	// 등록일 기준 날짜
		now.setMonth( now.getMonth() + 1 );
		let year = now.getFullYear(); // 년도
		let month = ('0' + now.getMonth()).slice(-2);  
		let day = ('0' + (now.getDate()+1)).slice(-2);
		stDy = year + '-' + month + '-' + day;	// 행사시작일
		
		$('input[name=subNo]').val( data.subNo );		// 행사테마번호
		$('input[name=subTxt]').val( data.subTxt );		// 테마명
		$('input[name=ofsdt]').val( stDy );	// 행사시작일
		$('input[name=ofedt]').val( data.enDy );		// 행사종료일
		$('input[name=prsdt]').val( data.purStDy );		// 발주시작일
		$('input[name=predt]').val( data.purEnDy );		// 발주종료일
	}
	
	// 팀코드 return
	function setDepCd(data){
		if(data == null){
			alert("팀코드 데이터가 유효하지 않습니다.\n관리자에게 문의하세요.");
			return;
		}
		
		$('input[name=depCd]').val( data.depCd );		// 팀코드
		$('input[name=depTxt]').val( data.depTxt );	// 팀명
	}
	
	//엑셀 업로드 콜백 데이터 세팅
	function excelUploadcallBack(data){
		let rowData = {
				'reqType' : $("select[name='reqType'] option:selected").val()
			,	'trCount'	: trCount
		};
		let list = data.resultMap.list;
		let msg = data.message;
		$('.from1').show();
		// 기존 데이터 초기화 
		$("#formTbody").empty();
		   
		for (let i = 0; i < list.length; i++) {
			let newRowData = {};
			newRowData.trCount = i;
			newRowData.ean11 = list[i].data1;			// 판매바코드
			newRowData.matnr = list[i].prodCd;			// 상품코드
			newRowData.maktx = list[i].prodNm;			// 상품명
			
			// 행사 유형
			if( rowData.reqType == '1001' ||  rowData.reqType == '1301' ||  rowData.reqType == '1501'){
				// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
				newRowData.salesPrc = (list[i].data5).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");		// 정상 판매가(원)
				newRowData.reqSalesPrc = (list[i].data6).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");	// 행사 판매가(원)
				newRowData.venRate = list[i].data7;			// 분담율
				newRowData.reqItemDesc = list[i].data8;		// 세부사항
				newRowData.zbigo = list[i].data9;			// 비고
				let reqSalesPrc = (newRowData.reqSalesPrc).toString().replace(/,/g, "");	// 행사 판매가(원)
				let salesPrc = (newRowData.salesPrc).toString().replace(/,/g, "");			// 정상 판매가(원)
				newRowData.calSalesPrc = (reqSalesPrc - salesPrc).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","); // 변경금액
				newRowData.calSalesRate = Math.trunc((reqSalesPrc - salesPrc) / salesPrc * 100); // % 계산
			}else if( rowData.reqType == '1002' ){
				// 번들
				newRowData.ofrStd1 = (list[i].data5).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");		// 기준1(해당상품 N개 구매시)
				newRowData.ofrDisc1 = (list[i].data6).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");		// 할인액1
				newRowData.ofrStd2 = (list[i].data7).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");		// 기준2
				newRowData.ofrDisc2 = (list[i].data8).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");		// 할인액2
				newRowData.ofrStd3 = (list[i].data9).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");		// 기준3
				newRowData.ofrDisc3 = (list[i].data10).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");	// 할인액3
				newRowData.venRate = list[i].data11;		// 분담율
				newRowData.reqItemDesc = list[i].data12;	// 세부사항
				newRowData.zbigo = list[i].data13;			// 비고
			}else if( rowData.reqType == '1003' ){
				// M+N
				newRowData.ofrM = list[i].data5;			// M(개)
				newRowData.ofrN = list[i].data6;			// N(개)
				newRowData.venRate = list[i].data7;			// 분담율
				newRowData.reqItemDesc = list[i].data8;		// 세부사항
				newRowData.zbigo = list[i].data9;			// 비고
			}else if( rowData.reqType == '1004' ){
				// 연관
				newRowData.salesPrc = (list[i].data5).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");		// 정상 판매가(원)
				newRowData.reqSalesPrc = (list[i].data6).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");	// 행사 판매가(원)
				newRowData.venRate = list[i].data7;			// 분담율
				newRowData.reqItemDesc = list[i].data8;		// 세부사항
				newRowData.zbigo = list[i].data9;			// 비고
				let reqSalesPrc = (newRowData.reqSalesPrc).toString().replace(/,/g, "");	// 행사 판매가(원)
				let salesPrc = (newRowData.salesPrc).toString().replace(/,/g, "");			// 정상 판매가(원)
				newRowData.calSalesRate = Math.trunc((reqSalesPrc - salesPrc) / salesPrc * 100); // % 계산
				if( $("select[name='reqDisc'] option:selected").val() == '03' )	{
					// 변경금액 계산
					newRowData.calSalesPrc = (reqSalesPrc - salesPrc).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
				}else if( $("select[name='reqDisc'] option:selected").val() == '01' ){
					// 변경금액 계산
					newRowData.calSalesPrc =  (reqSalesPrc).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
				}
			}else if( rowData.reqType == '4002' ){
				// 상품권
				newRowData.ofrStd = (list[i].data3).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");		// 결제금액 기준(동일입력)
				newRowData.giftAmt = (list[i].data4).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");		// 증정/사은금액
				newRowData.venRate = list[i].data5;			// 분담율
				newRowData.reqItemDesc = list[i].data6;		// 세부사항
				newRowData.zbigo = list[i].data7;			// 비고
			}else if( rowData.reqType == '8001' ){
				// 원매가 인하
				newRowData.reqItemDesc = list[i].data5;		// 세부사항
				newRowData.zbigo = list[i].data6;			// 비고
			}
			
			if( rowData.reqType != '4002' ){
				// 상품권이 아니면
				newRowData.purPrc = (list[i].data3).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","); 		// 정상 원가(원)
				newRowData.reqPurPrc = (list[i].data4).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");	// 행사 원가(원)
				let reqPurPrc = (newRowData.reqPurPrc).toString().replace(/,/g, "");	// 정상 원가(원)
				let purPrc = (newRowData.purPrc).toString().replace(/,/g, "");			// 행사 원가(원)
				newRowData.calPurPrc = (reqPurPrc - purPrc).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","); // 변경금액
				newRowData.calComRate = Math.trunc((reqPurPrc - purPrc) / purPrc * 100); // % 계산
			}
			
			if(list[i].stsChk == 'P'){
				// 템플릿에 가져온 데이터 맵핑
				$("#formTemplate").tmpl(newRowData).appendTo("#formTbody");
			}
		}
		
		// 행사 유형
		if( rowData.reqType == '1001' ||  rowData.reqType == '1301' ||  rowData.reqType == '1501'){
			// 가격 할인(1001), 엘포인트할인(1301), 카드할인(1501)
			$('.from2').show();
			$('.from3, .from4, .from5, .from6').hide();
		}else if( rowData.reqType == '1002' ){
			// 번들
			$('.from3').show();
			$('.from2, .from4, .from5, .from6').hide();
			if( $("select[name='reqDisc'] option:selected").val() == '01' ){
				// 번들 + 정액판매가
				$('.discntTh1').text('행사판매가1(원)');
				$('.discntTh2').text('행사판매가2(원)');
				$('.discntTh3').text('행사판매가3(원)');
			}else if( $("select[name='reqDisc'] option:selected").val() == '03' ){
				// 번들 + 정액할인
				$('.discntTh1').text('할인율1(%)');
				$('.discntTh2').text('할인율2(%)');
				$('.discntTh3').text('할인율3(%)');
			}else if( $("select[name='reqDisc'] option:selected").val() == '04' ){
				// 번들 + 정율할인
				$('.discntTh1').text('할인액1(원)');
				$('.discntTh2').text('할인액2(원)');
				$('.discntTh3').text('할인액3(원)');
			}
		}else if( rowData.reqType == '1003' ){
			// M+N
			$('.from4').show();
			$('.from2, .from3, .from5, .from6').hide();
		}else if( rowData.reqType == '1004' ){
			// 연관
			$('.from2, .from5').show();
			$('.from3, .from4, .from6').hide();
		}else if( rowData.reqType == '4002' ){
			// 상품권
			$('.from6').show();
			$('.from1, .from2, .from3, .from4, .from5').hide();
		}else if( rowData.reqType == '8001' ){
			// 원매가 인하
			$('.from2, .from3, .from4, .from5, .from6, .venRateTh').hide();
		}
		
		// 아이템 분담율 세팅
		if($("select[name='reqContyp'] option:selected").val() == '01' || $("select[name='reqContyp'] option:selected").val() == '03' ) {
			let venRate = $("select[name='reqContyp'] option:selected").val() == '01' ? '50' : '100';
			$('input[name="venRate"]').val(venRate);
			$('input[name="venRate"]').attr('disabled', true); // 파트너사 분담율
		}else{
			$('input[name="venRate"]').val( $('input[name="hdVenRate"]').val() );
		}
		
		if( msg != '') alert(msg);
	}
	
	// 분담율 세팅
	function fncsetHdVenRateVal(obj, event){
		$('input[name="venRate"]').val( $('input[name="hdVenRate"]').val() );
		/*$.each($("#formTbody tr"), function(idx, item) {
			$(this).find('input[name=venRate]').val($(obj).val());
		});*/
	}
	
	//계열사 구분 변경 시 공통 이벤트처리
	function fncChgVkorg(){
		var vkorg = $.trim($("select[name='vkorg']").val());
		
		//--- 판매코드 검색조건 변경
		//판매코드검색조건 초기화
		srchProdMtAll = "";
		srchProdSuper = "";
		//데이터 없는 경우, 변환값 바로적용
		switch(vkorg){
			case "KR02":	//마트
				srchProdMtAll = "Y";
				break;
			case "KR04":	//슈퍼
				srchProdSuper = "Y";
				break;
			default:
				srchProdMtAll = "Y";
				srchProdSuper = "Y";
				break;
		}
		
		//--- 마스터정보 일부 초기화
		$('input[name="subNo"], input[name="subTxt"], input[name="ofsdt"], input[name="ofedt"], input[name="prsdt"], input[name="predt"]').val('');
	}
</script>

<!-- 서식 template -->
<script id="formTemplate"  type="text/x-jquery-tmpl">
<tr class="tr\${trCount}"  bgcolor=ffffff>
	<td align="center"><input type="checkbox" name="chk" value="" style="height: 20px;"  /></td>
	<td align="center"><input type="text" name="ean11" value="<c:out value='\${ean11}'/>" style="width: 85%;" disabled /><i class="bi bi-search" style='margin-left: 5px; cursor: pointer;' onclick='btnEvent("srcBarcode", this)' ></i>
	<td align="center"><input type="text" name="maktx" value="<c:out value='\${maktx}'/>" style="width: 99%;" disabled /></td>
	<td align="center" class="from1 calPurPrc"><c:out value='\${calPurPrc}'/></td>
	<td align="center" class="from1 calComRate"><c:out value='\${calComRate}'/></td>
	<td align="center" class="from1"><input type="text" name="purPrc" maxlength="17" value="<c:out value='\${purPrc}'/>" style="width: 99%;" maxlength="17" onkeyup="return fncValChk(this, event)" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');" /></td>	
	<td align="center" class="from1"><input type="text" name="reqPurPrc" maxlength="17" value="<c:out value='\${reqPurPrc}'/>" style="width: 99%;" maxlength="17" onkeyup="return fncValChk(this, event)" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');" /></td>
	<td align="center" class="from5 ofrStd">해당 상품 동시구매시</td>
	<td align="center" class="from2 calSalesPrc"><c:out value='\${calSalesPrc}'/></td>
	<td align="center" class="from2 calSalesRate"><c:out value='\${calSalesRate}'/></td>
	<td align="center" class="from2"><input type="text" name="salesPrc" maxlength="17" value="<c:out value='\${salesPrc}'/>" style="width: 99%;" maxlength="17" onkeyup="return fncValChk(this, event)" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');"/></td>
	<td align="center" class="from2"><input type="text" name="reqSalesPrc" maxlength="17" value="<c:out value='\${reqSalesPrc}'/>" style="width: 99%;" maxlength="17" onkeyup="return fncValChk(this, event)" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');"/></td>
	<td align="center" class="from6"><input type="text" name="ofrStd" maxlength="17" value="<c:out value='\${ofrStd}'/>" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');" /></td>
	<td align="center" class="from6"><input type="text" name="giftAmt" maxlength="17" value="<c:out value='\${giftAmt}'/>" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');" /></td>
	<td align="center" class="from4"><input type="text" name="ofrM" maxlength="2" value="<c:out value='\${ofrM}'/>" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{2}).+/g, '$1');" /></td>
	<td align="center" class="from4"><input type="text" name="ofrN" maxlength="2" value="<c:out value='\${ofrN}'/>" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{2}).+/g, '$1');" /></td>
	<td align="center" class="from3"><input type="text" name="ofrStd1" maxlength="17" value="<c:out value='\${ofrStd1}'/>" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');"/></td>
	<td align="center" class="from3"><input type="text" name="ofrDisc1" maxlength="17" value="<c:out value='\${ofrDisc1}'/>" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');" /></td>
	<td align="center" class="from3"><input type="text" name="ofrStd2" maxlength="17" value="<c:out value='\${ofrStd2}'/>" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');"/></td>
	<td align="center" class="from3"><input type="text" name="ofrDisc2" maxlength="17" value="<c:out value='\${ofrDisc2}'/>" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');" /></td>
	<td align="center" class="from3"><input type="text" name="ofrStd3" maxlength="17" value="<c:out value='\${ofrStd3}'/>" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');"/></td>
	<td align="center" class="from3"><input type="text" name="ofrDisc3" maxlength="17" value="<c:out value='\${ofrDisc3}'/>" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');" /></td>
	<td align="center" class="venRateTh"><input type="text" name="venRate" maxlength="3" value="<c:out value='\${venRate}'/>" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{2}).+/g, '$1');" /></td>
	<td align="center"><input type="text" name="reqItemDesc" maxlength="80" value="<c:out value='\${reqItemDesc}'/>" style="width: 99%;"/></td>
	<td align="center"><input type="text" name="zbigo" maxlength="80" value="<c:out value='\${zbigo}'/>" style="width: 99%;"/></td>
	<input type="hidden" name="rowAttri" value="new"  />
	<input type="hidden" name="matnr" value="<c:out value='\${matnr}'/>"  />
</tr>

</script>
</head>
<body>
	<div id="content_wrap">
		<div>
			<div id="wrap_menu">
				<div class="wrap_search">
					<!-- 01 : search -->
					<div class="bbs_search">
						<ul class="tit">
							<li class="btn">
								<c:if test="${ empty proEventInfo.apprStatus || proEventInfo.apprStatus == '01' }">
									<a href="#" class="btn" onclick="btnServerEvent('save');"><span>저장</span></a>
								</c:if>
								<c:if test="${ proEventInfo.apprStatus == '01' }">
									<a href="#" class="btn" onclick="btnServerEvent('del');"><span>삭제</span></a>
									<a href="#" class="btn" onclick="btnServerEvent('eventReq');"><span>행사요청</span></a>
								</c:if>
								<c:if test="${ (proEventInfo.apprStatus == '04' || proEventInfo.apprStatus == '44' || proEventInfo.apprStatus == '98' || proEventInfo.apprStatus == '99') && proEventInfo.vkorg == 'KR02' }">
									<a href="#" class="btn" onclick="btnServerEvent('ecsIntgrMt');"><span>공문서&계약서 작성</span></a>
								</c:if>
								<c:if test="${ (proEventInfo.apprStatus == '04' || proEventInfo.apprStatus == '44' || proEventInfo.apprStatus == '98' || proEventInfo.apprStatus == '99') && proEventInfo.vkorg == 'KR04' }">
									<a href="#" class="btn" onclick="btnServerEvent('ecsIntgrMd');"><span>롯데슈퍼 공문서&계약서 작성</span></a>
									<a href="#" class="btn" onclick="btnServerEvent('ecsIntgrCs');"><span>씨에스유통 공문서&계약서 작성</span></a>
								</c:if>
							</li>
						</ul>
					</div>
					<form name="proEvent" id="proEvent">
						<input type="hidden" name="docType" id="docType" value="<c:out value='${proEventInfo.docType}'/>"/>
						<div class="bbs_list">
							<ul class="tit">
								<li class="tit"><spring:message
										code="msg.product.onOff.default.notice" /></li>
							</ul>
							<table class="bbs_grid3" cellpadding="0" cellspacing="0"
								border="0">
								<colgroup>
									<col style="width: 15%" />
									<col style="width: 30%" />
									<col style="width: 15%" />
									<col style="width: 30%" />
								</colgroup>
								<tr>
									<th><b><span class="star">*</span> 계열사</b></th>
									<td colspan="3">
										<select id="vkorg" name="vkorg" class="required" style="width:150px;">
											<option value="KR02" >롯데마트</option>
											<option value="KR04" >롯데슈퍼</option>
										</select>
										<input type="hidden" name="vkorgTxt" id="vkorgTxt"  />
									</td>
								</tr>
								<tr>
									<th><b><span class="star">*</span> 행사 테마 번호</b></th>
									<td>
										<input type="hidden" name="subNo" id="subNo" value="<c:out value='${proEventInfo.subNo}'/>" disabled/>
										<input type="text" name="subTxt" id="subTxt" value="<c:out value='${proEventInfo.subTxt}'/>" style="width: 85%;" disabled/>
										<i class="bi bi-search" style='margin-left: 5px; cursor: pointer;' onclick='btnEvent("theme", this)' ></i>
									</td>
									<th><b> 파트너사 행사코드</b></th>
									<td>
										<input type="text" name="reqOfrcd" id="reqOfrcd" style="width: 150px;" value="<c:out value='${proEventInfo.reqOfrcd}'/>" disabled />
										<input type="hidden" name="prodEvntNewYn" id="prodEvntNewYn"  />
									</td>
								</tr>
								<tr>
									<th><b><span class="star">*</span> 행사구분</b></th>
									<td>
										<html:codeTag objId="reqContyp" objName="reqContyp"  width="150px;"  parentCode="EVCAT"  comType="SELECT" orderSeqYn="Y"  formName="form"/>
										<!-- <select id="reqContyp" name="reqContyp" class="required" style="width:150px;">
											<option value="00" >원매가행사공문</option>
											<option value="01" >판촉요청 합의서(50:50)</option>
											<option value="02" >판촉요청 합의서(자율분담)</option>
											<option value="03" >판촉행사 시행공문(100:0)</option>
											<option value="04" >판촉행사 시행공문(자율분담)</option>
											<option value="05" >판촉행사 신청서_공개모집</option>
										</select> -->
										<input type="hidden" name="reqContypTxt" id="reqContypTxt"  />
									</td>
									<th><b><span class="star">*</span> 팀</b></th>
									<td>
										<input type="hidden" name="depCd" id="depCd" value="<c:out value='${proEventInfo.depCd}'/>" disabled/>
										<input type="hidden" name="depTxt" id="depTxt" value="<c:out value='${proEventInfo.depTxt}'/>" disabled/>
										<input type="text" name="srcDepTxt" id="srcDepTxt" value="<c:out value='${proEventInfo.srcDepTxt}'/>" style="width: 85%;" disabled/>
										<i class="bi bi-search" style='margin-left: 5px; cursor: pointer;' onclick='btnEvent("depCd", this)' ></i>
										<input type="hidden" id="empNo" name="empNo" value="<c:out value='${proEventInfo.empNo}'/>"/>
										<input type="hidden" id="empTxt" name="empTxt" value="<c:out value='${proEventInfo.empTxt}'/>"/>
									</td>
									<!-- <th><b>대상점포</b></th>
									<td>
										<select id="werksType" name="werksType" class="required" style="width:150px;">
											<option value="01" >마트 전점(마트+맥스)</option>
											<option value="02" >마트 전점(마트)</option>
											<option value="03" >맥스 전점(맥스)</option>
											<option value="04" >이그로서리(온라인)</option>
											<option value="11" >슈퍼 전점(직영+FC+CS+하모니)</option>
											<option value="12" >직영 전점(직영+CS)</option>
											<option value="13" >가맹 전점(FC+하모니)</option>
											<option value="14" >가맹 FC(FC)</option>
											<option value="15" >가맹 하모니(하모니)</option>
											<option value="99" >기타</option>
										</select>
										<select id="werksTypeOrd" name="werksTypeOrd" class="required" style="display: none;">
											<option value="01" >마트 전점(마트+맥스)</option>
											<option value="02" >마트 전점(마트)</option>
											<option value="03" >맥스 전점(맥스)</option>
											<option value="04" >이그로서리(온라인)</option>
											<option value="11" >슈퍼 전점(직영+FC+CS+하모니)</option>
											<option value="12" >직영 전점(직영+CS)</option>
											<option value="13" >가맹 전점(FC+하모니)</option>
											<option value="14" >가맹 FC(FC)</option>
											<option value="15" >가맹 하모니(하모니)</option>
											<option value="99" >기타</option>
										</select>
										<input type="hidden" name="werksTypeTxt" id="werksTypeTxt"  />
									</td> -->
									<!-- <th class = "fromTd"><b><span class="star">*</span> 행사요청</b></th>
									<td class = "fromTd">
										<select id="reqOfr" name="reqOfr" class="required" style="width:150px;">
											<option value="01" >파트너사요청</option>
											<option value="02" >공개모집참여</option>
										</select>
										<input type="hidden" name="reqOfrTxt" id="reqOfrTxt"  />
									</td> -->
								</tr>
								<tr>
									<th><b><span class="star">*</span> 행사명</b></th>
									<td>
										<input type="text" name="reqOfrTxt" id="reqOfrTxt" maxlength="20" style="width: 98%;" value="<c:out value='${proEventInfo.reqOfrTxt}'/>"  />
									</td>
									<th><b><span class="star">*</span> 행사목적</b></th>
									<td>
										<input type="text" name="reqPurTxt" id="reqPurTxt" maxlength="20" style="width: 98%;" value="<c:out value='${proEventInfo.reqPurTxt}'/>"  />
									</td>
								</tr>
								<tr class = "fromTd">
									<th><b><span class="star">*</span> 거래형태</b></th>
									<td>
										<select id="zdeal" name="zdeal" class="required" style="width:150px;">
											<option value="01" >직매입</option>
										</select>
										<input type="hidden" name="zdealTxt" id="zdealTxt"  />
									</td>
									<th><b><span class="star">*</span> 행사유형</b></th>
									<td>
										<html:codeTag objId="reqType" objName="reqType"  width="150px;"  parentCode="REQFG"  comType="SELECT" orderSeqYn="Y"  formName="form"/>
										<html:codeTag objId="reqTypeOrd" objName="reqTypeOrd"  width="150px;"  parentCode="REQFG"  display="none"  comType="SELECT" orderSeqYn="Y"  formName="form"  />
										<!-- <select id="reqType" name="reqType" class="required" style="width:150px;">
											<option value="8001" >원매가인하</option>
											<option value="1001" >가격할인</option>
											<option value="1002" >번들</option>
											<option value="1003" >M+N</option>
											<option value="1004" >연관</option>
											<option value="1301" >엘포인트할인</option>
											<option value="1501" >카드할인</option>
											<option value="4002" >증정상품권/사은품</option>
										</select>
										<select id="reqTypeOrd" name="reqTypeOrd" class="required" style="display: none;">
											<option value="8001" >원매가인하</option>
											<option value="1001" >가격할인</option>
											<option value="1002" >번들</option>
											<option value="1003" >M+N</option>
											<option value="1004" >연관</option>
											<option value="1301" >엘포인트할인</option>
											<option value="1501" >카드할인</option>
											<option value="4002" >증정상품권/사은품</option>
										</select> -->
										<input type="hidden" name="reqTypeTxt" id="reqTypeTxt"  />
									</td>
								</tr>
								<tr class = "fromTd">
									<th><b><span class="star">*</span> 할인유형</b></th>
									<td>
										<html:codeTag objId="reqDisc" objName="reqDisc" width="150px;"  parentCode="REQDC"  comType="SELECT" orderSeqYn="Y"  formName="form"/>
										<html:codeTag objId="reqDiscOrd" objName="reqDiscOrd"  width="150px;"  parentCode="REQDC"  display="none"  comType="SELECT" orderSeqYn="Y"  formName="form"/>
										<!-- <select id="reqDisc" name="reqDisc" class="required" style="width:150px;">
											<option value="01" >정액판매가</option>
											<option value="03" >정액할인</option>
											<option value="04" >정율할인</option>
											<option value="05" >M+N</option>
											<option value="41" >상품권금액</option>
											<option value="42" >사은품금액</option>
											<option value="99" >원가인하</option>
										</select>
										<select id="reqDiscOrd" name="reqDiscOrd" class="required" style="display: none;">
											<option value="01" >정액판매가</option>
											<option value="03" >정액할인</option>
											<option value="04" >정율할인</option>
											<option value="05" >M+N</option>
											<option value="41" >상품권금액</option>
											<option value="42" >사은품금액</option>
											<option value="99" >원가인하</option>
										</select> -->
										<input type="hidden" name="reqDiscTxt" id="reqDiscTxt"  />
									</td>
									<th><b><span class="star">*</span> 비용적용방식</b></th>
									<td>
										<html:codeTag objId="costType" objName="costType" width="150px;"  parentCode="CSTFG"  comType="SELECT" orderSeqYn="Y"  formName="form"/>
										<html:codeTag objId="costTypeOrd" objName="costTypeOrd" width="150px;"  parentCode="CSTFG" display="none" comType="SELECT" orderSeqYn="Y"  formName="form"/>
										<!-- <select id="costType" name="costType" class="required" style="width:150px;">
											<option value="01" >원가인하</option>
											<option value="02" >사후 정산</option>
											<option value="04" >수수료율 인하</option>  주석임
										</select>
										<select id="costTypeOrd" name="costTypeOrd" class="required" style="display: none;">
											<option value="01" >원가인하</option>
											<option value="02" >사후 정산</option>
											<option value="04" >수수료율 인하</option> 주석임 
										</select> -->
										<input type="hidden" name="costTypeTxt" id="costTypeTxt"  />
									</td>
								</tr>
								<tr class = "fromTd">
									<th><b><span class="star">*</span> 제안파트너사 분담율</b></th>
									<td>
										<input type="text" name="hdVenRate" id="hdVenRate" style="width: 98%;" value="<c:out value='${proEventInfo.hdVenRate}'/>" maxlength="3" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1');" onkeyup="fncsetHdVenRateVal(this, event)" />
									</td>
									<th><b><span class="star">*</span> 예상판촉비용</b></th>
									<td>
										<input type="text" name="ofrCost" id="ofrCost" maxlength="17" style="width: 98%;" value="<c:out value='${proEventInfo.ofrCost}'/>" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');" />
									</td>
								</tr>
								<tr>
									<th><b><span class="star">*</span> 행사기간</b></th>
									<td>
										<input type="text" class="day" name="ofsdt" id="ofsdt" style="width:80px;" value="<c:out value='${proEventInfo.ofsdt}'/>" disabled> <img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor:hand;" />
										~
										<input type="text" class="day" name="ofedt" id="ofedt" style="width:80px;" value="<c:out value='${proEventInfo.ofedt}'/>" disabled> <!-- <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('proEvent.ofedt');"  style="cursor:hand;" /> -->
									</td>
									<th><b><span class="star">*</span> 발주기간</b></th>
									<td>
										<input type="text" class="day" name="prsdt" id="prsdt" style="width:80px;" value="<c:out value='${proEventInfo.prsdt}'/>" disabled> <img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor:hand;" />
										~
										<input type="text" class="day" name="predt" id="predt" style="width:80px;" value="<c:out value='${proEventInfo.predt}'/>" disabled> <!-- <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('proEvent.predt');"  style="cursor:hand;" /> -->
									</td>
								</tr>
								<!-- <tr class = "fromTd">
									<th><b><span class="star">*</span> 기타점포</b></th>
									<td>
										<input type="text" name="werksEtcTxt" id="werksEtcTxt" style="width: 150px;" value="<c:out value='${proEventInfo.werksEtcTxt}'/>" />
									</td>
								</tr>
								 -->
								<tr>
									<th><b><span class="star">*</span> 요청사항</b></th>
									<td colspan="3">
										<input type="text" name="reqOfrDesc" id="reqOfrDesc" maxlength="150" style="width: 80%;" value="<c:out value='${proEventInfo.reqOfrDesc}'/>" />
										<br/><font color="red">※ 특정점만 행사 시 해당 점포명 기재</font>
									</td>
								</tr>
							</table>
						</div>
						<div class="wrap_con formDiv">
							<!-- list -->
							<div class="bbs_list">
							
								<ul class="tit">
									<li class="tit">서식</li>
									<li class="btn">
										<c:if test="${ empty proEventInfo.apprStatus || proEventInfo.apprStatus == '01' }">
											<a href="#" class="btn" onclick="rowBtnEvent('Add');"><span>행추가</span></a>
											<a href="#" class="btn" onclick="rowBtnEvent('Del');"><span>행삭제</span></a>
											<a href="#" class="btn" onclick="btnEvent('excelFrom');"><span>업로드 양식</span></a>
											<a href="#" class="btn" onclick="btnEvent('excelUpl');"><span><spring:message code="button.rep.product.excelUpload"/></span></a>
										</c:if>
									</li>
								</ul>
								<div style="width:100%; height:446px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
									<table id="formTable" cellpadding="1" cellspacing="1" border="0" width="1300" bgcolor=efefef>
										<colgroup>
											<col width="2%"/>
											<col width="10%"/>
											<col width="10%"/>
											<col class="from1" width="5%"/>
											<col class="from1" width="5%"/>
											<col class="from1" width="5%"/>
											<col class="from1" width="5%"/>
											<col class="from5" width="10%"/>
											<col class="from2" width="5%"/>
											<col class="from2" width="5%"/>
											<col class="from2" width="5%"/>
											<col class="from2" width="5%"/>
											<col class="from6" width="5%"/>
											<col class="from6" width="5%"/>
											<col class="from4" width="5%"/>
											<col class="from4" width="5%"/>
											<col class="from3" width="5%"/>
											<col class="from3" width="5%"/>
											<col class="from3" width="5%"/>
											<col class="from3" width="5%"/>
											<col class="from3" width="5%"/>
											<col class="from3" width="5%"/>
											<col class="venRateTh" width="5%"/>
											<col width="10%"/>
											<col width="10%"/>
										</colgroup>
										<tr bgcolor="#e4e4e4">
											<th><input type="checkbox" id="allCheck" name="chk"></th>
											<th>판매바코드(13)</th>
											<th>상품명</th>
											<th class="from1">변경금액(원)</th>
											<th class="from1">변경금액율(%)</th>
											<th class="from1">정상 원가(원)</th>
											<th class="from1">행사 원가(원)</th>
											<th class="from5">행사기준</th>
											<th class="from2">변경금액(원)</th>
											<th class="from2">변경금액율(%)</th>
											<th class="from2">정상 판매가(원)</th>
											<th class="from2">행사 판매가(원)</th>
											<th class="from6">결제금액 기준(동일입력)</th>
											<th class="from6">증정/사은금액</th>
											<th class="from4">M(개)</th>
											<th class="from4">N(개)</th>
											<th class="from3">기준1(해당상품 N개 구매시)</th>
											<th class="from3 discntTh1">할인액1(원)</th>
											<th class="from3">기준2</th>
											<th class="from3 discntTh2">할인액2(원)</th>
											<th class="from3">기준3</th>
											<th class="from3 discntTh3">할인액3(원)</th>
											<th class="venRateTh venRateText">분담율</th>
											<th>세부사항</th>
											<th>비고</th>
										</tr>
		
										<tbody id="formTbody">
											<c:if test="${not empty proEventList }">
												<c:forEach items="${proEventList}" var="list" varStatus="index" >
													<tr class="tr${index.count}"  bgcolor=ffffff>
														<td align="center"><input type="checkbox" name="chk" value="" style="height: 20px;"  /></td>
														<td align="center"><input type="text" name="ean11" value="${list.ean11}" style="width: 99%;" disabled/></td>
														<td align="center"><input type="text" name="maktx" value="${list.maktx}" style="width: 99%;" disabled/></td>
														<td align="center" class="from1 calPurPrc">${list.calPurPrc}</td>
														<td align="center" class="from1 calComRate">${list.calComRate}</td>
														
														<td align="center" class="from1"><input type="text" name="purPrc" maxlength="17" value="${list.purPrc}" style="width: 99%;" maxlength="17" onkeyup="return fncValChk(this, event)" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');" /></td>
														<td align="center" class="from1"><input type="text" name="reqPurPrc" maxlength="17" value="${list.reqPurPrc}" style="width: 99%;" maxlength="17" onkeyup="return fncValChk(this, event)" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');" /></td>
														<td align="center" class="from5 ofrStd">${list.ofrStd}</td>
														<td align="center" class="from2 calSalesPrc">${list.calSalesPrc}</td>
														<td align="center" class="from2 calSalesRate">${list.calSalesRate}</td>
														<td align="center" class="from2"><input type="text" name="salesPrc" maxlength="17" value="${list.salesPrc}" style="width: 99%;" maxlength="17" onkeyup="return fncValChk(this, event)" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');"/></td>
														<td align="center" class="from2"><input type="text" name="reqSalesPrc" maxlength="17" value="${list.reqSalesPrc}" style="width: 99%;" maxlength="17" onkeyup="return fncValChk(this, event)" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');"/></td>
														<td align="center" class="from6"><input type="text" name="ofrStd" maxlength="17" value="${list.ofrStd}" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');" /></td>
														<td align="center" class="from6"><input type="text" name="giftAmt" maxlength="17" value="${list.giftAmt}" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');" /></td>
														<td align="center" class="from4"><input type="text" name="ofrM" maxlength="2" value="${list.ofrM}" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{2}).+/g, '$1');" /></td>
														<td align="center" class="from4"><input type="text" name="ofrN" maxlength="2" value="${list.ofrN}" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{2}).+/g, '$1');" /></td>
														<td align="center" class="from3"><input type="text" name="ofrStd1" maxlength="17" value="${list.ofrStd1}" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');"/></td>
														<td align="center" class="from3"><input type="text" name="ofrDisc1" maxlength="17" value="${list.ofrDisc1}" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');" /></td>
														<td align="center" class="from3"><input type="text" name="ofrStd2" maxlength="17" value="${list.ofrStd2}" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');"/></td>
														<td align="center" class="from3"><input type="text" name="ofrDisc2" maxlength="17" value="${list.ofrDisc2}" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');" /></td>
														<td align="center" class="from3"><input type="text" name="ofrStd3" maxlength="17" value="${list.ofrStd3}" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');"/></td>
														<td align="center" class="from3"><input type="text" name="ofrDisc3" maxlength="17" value="${list.ofrDisc3}" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{0}).+/g, '$1').replace(/\B(?=(\d{3})+(?!\d))/g, ',');" /></td>
														<td align="center" class="venRateTh"><input type="text" name="venRate" maxlength="3" value="${list.venRate}" style="width: 99%;" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1').replace(/(\.\d{2}).+/g, '$1');" /></td>
														<td align="center"><input type="text" name="reqItemDesc" maxlength="80" value="${list.reqItemDesc}" style="width: 99%;"/></td>
														<td align="center"><input type="text" name="zbigo" maxlength="80" value="${list.zbigo}" style="width: 99%;"/></td>
														<input type="hidden" name="rowAttri" value="search" />
														<input type="hidden" name="reqOfrcd" value="${list.reqOfrcd}" />
														<input type="hidden" name="matnr" value="${list.matnr}" />
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div id="footer">
					<div id="footbox">
						<div class="msg" id="resultMsg"></div>
						<div class="notice"></div>
						<div class="location">
							<ul>
								<li>홈</li>
								<li>상품</li>
								<li>상품현황관리</li>
								<li class="last">행사정보 등록</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form name="hiddenForm" id="hiddenForm">
		<input type="hidden" id="reqOfrcd" 			name="reqOfrcd"  value="${proEventInfo.reqOfrcd}"	/> 	    	    	
		<input type="hidden" id="reqType" 			name="reqType"  value="${proEventInfo.reqType}"	/> 	    	    	
		<input type="hidden" id="costType" 			name="costType"  value="${proEventInfo.costType}"	/> 	    	    	
		<input type="hidden" id="reqDisc" 			name="reqDisc"  value="${proEventInfo.reqDisc}"	/> 	    	    	
	</form>
</body>
</html>
