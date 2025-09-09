<%--
	Page Name 	: NEDMPRO0320.jsp
	Description : 반품제안 등록 화면 
	Modification Information
	
	  수정일 			  수정자 						수정내용
	---------- 		---------    	-------------------------------------
	2025.03.18  PARK JONG GYU 					최초생성		
--%>
<%@ include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<title></title>
<style type="text/css">
	/* TAB */
.tabs {
	height: 31px;
	background: #fff;
}

.tabs ul {
	width: 100%;
	height: 31px;
}

.tabs li {
	float: left;
	width: 130px;
	height: 29px;
	background: #fff;
	border: 1px solid #ccd0d9;
	border-radius: 2px 2px 0 0;
	font-size: 12px;
	color: #666;
	line-height: 30px;
	text-align: center;
}

.tabs li.on {
	border-bottom: #e7eaef 1px solid;
	color: #333;
	font-weight: bold;
}

.tabs li a {
	display: block;
	color: #666;
}

.tabs li.on a {
	color: #333;
	font-weight: bold;
}
	
.img {
	height: 14px;
}	

</style>
<script type="text/javascript" >
let trCount = 10000;
let rowNum;
let curYear = "${curYear}";
let ssnlProdObj = {
			"lunarNewYear" 	: "${curYear}" +"-01-31"
		,	"newYear" 		: "${newYear}"
		,	"valentineDay" 	: "${curYear}" +"-02-14"
		,	"whiteDay" : "${curYear}" +"-03-14"
		,	"arborDay" : "${curYear}" +"-04-05"
		,	"newSemester" : ""
		,	"chldrDay" : "${curYear}" +"-05-05"
		,	"chuseok" : "${chuseok}"
		,	"hlwnDay" : "${curYear}" +"-10-31"
		,	"pprDay" : "${curYear}" +"-11-11"
		,	"pckldSsn" : "${curYear}" +"-12-31"
		,	"christmas" : "${curYear}" +"-12-25"
		,	"sumerSsn" : "${curYear}" +"-09-30"
		,	"wntrSsn" : "${curYear}" +"-03-31"
}
/* dom이 생성되면 ready method 실행 */
$(document).ready(function() {

	//----- 검색조건 협력업체 Default 설정.
	let srchEntpCd = "<c:out value='${param.srchEntpCd}'/>";  //검색조건 협력업체코드
	let repVendorId = "<c:out value='${epcLoginVO.repVendorId}'/>";  //관리자로 로그인 해서 협력업체 갈아타기 로그인시 협력업체 코드
	if (srchEntpCd.replace(/\s/gi, '') ==  "") {
		$("#searchForm #srchEntpCd").val(repVendorId);
	} else {
		$("#searchForm #srchEntpCd").val(srchEntpCd);
	}
	
	//요청내역 list datepicker click event 동적 binding
	$(document).on("click", "#dataListbody img.datepicker", function(){
		rowNum = $(this).closest('tr').attr('class');
		$(this).closest('tr').find('input[name=rowAttri]').val('mdf');
		
		let dayInput = $(this).prev("input")[0];
		let objId = dayInput.id;
		let objVal = $.trim(dayInput.value);
		let objName = $.trim($(this).prev().attr('name'));
		let rtnResn = $('.'+rowNum).find("select[name='rtnResn'] option:selected").val();		// 반품 사유
		let rtnResnDtl = $('.'+rowNum).find("select[name='rtnResnDtl'] option:selected").val(); // 반품 상세 사유
		
		// 시즌상품별 기준값 → 반품마감일 +30일 세팅
		let now = new Date( objVal );
		now.setMonth( now.getMonth() + 1 );
		let year = now.getFullYear(); // 년도
		let month = ('0' + (now.getMonth() + 1)).slice(-2); // 다음달  
		let day = ('0' + now.getDate()).slice(-2); // 일
		objVal = year + '-' + month + '-' + day; 
		
		if( objName == 'rtnClsDy'){
			if( rtnResn == 'A' ){
				if( rtnResnDtl == '6' )	  openCalSetDt('reqDetailForm.'+objId, objVal, "fncCallBackCalendar", ssnlProdObj.lunarNewYear, objVal);
				else if( rtnResnDtl == '7' ) openCalSetDt('reqDetailForm.'+objId, objVal, "fncCallBackCalendar", ssnlProdObj.newYear, objVal);
				else if( rtnResnDtl == '8' ) openCalSetDt('reqDetailForm.'+objId, objVal, "fncCallBackCalendar", ssnlProdObj.valentineDay, objVal);
				else if( rtnResnDtl == '9' ) openCalSetDt('reqDetailForm.'+objId, objVal, "fncCallBackCalendar", ssnlProdObj.whiteDay, objVal);
				else if( rtnResnDtl == '10' ) openCalSetDt('reqDetailForm.'+objId, objVal, "fncCallBackCalendar", ssnlProdObj.arborDay, objVal);
				else if( rtnResnDtl == '11' ) openCalSetDt('reqDetailForm.'+objId, objVal, "fncCallBackCalendar", ssnlProdObj.newSemester, objVal);
				else if( rtnResnDtl == '12' ) openCalSetDt('reqDetailForm.'+objId, objVal, "fncCallBackCalendar", ssnlProdObj.chldrDay, objVal);
				else if( rtnResnDtl == '13' ) openCalSetDt('reqDetailForm.'+objId, objVal, "fncCallBackCalendar", ssnlProdObj.chuseok, objVal);
				else if( rtnResnDtl == '14' ) openCalSetDt('reqDetailForm.'+objId, objVal, "fncCallBackCalendar", ssnlProdObj.hlwnDay, objVal);
				else if( rtnResnDtl == '15' ) openCalSetDt('reqDetailForm.'+objId, objVal, "fncCallBackCalendar", ssnlProdObj.pprDay, objVal);
				else if( rtnResnDtl == '16' ) openCalSetDt('reqDetailForm.'+objId, objVal, "fncCallBackCalendar", ssnlProdObj.pckldSsn, objVal);
				else if( rtnResnDtl == '17' ) openCalSetDt('reqDetailForm.'+objId, objVal, "fncCallBackCalendar", ssnlProdObj.christmas, objVal);
				else if( rtnResnDtl == '18' ) openCalSetDt('reqDetailForm.'+objId, objVal, "fncCallBackCalendar", ssnlProdObj.sumerSsn, objVal);
				else if( rtnResnDtl == '19' ) openCalSetDt('reqDetailForm.'+objId, objVal, "fncCallBackCalendar", ssnlProdObj.wntrSsn, objVal);
			}else{
				openCalSetDt('reqDetailForm.'+objId, objVal, "fncCallBackCalendar", "${srchFromDt}", "${next2Month}");
			}
		}else{
			openCalSetDt('reqDetailForm.'+objId, objVal, "fncCallBackCalendar", "${srchFromDt}");
		}
		
		 //$(this).closest('tr').find('input[name="rtnClsDy').val().replace(/[^0-9]/g, "");
	});
	
	// 전체 check!
	$("#allCheck").click(function() {
		//if($("#allCheck").is(":checked")) $("input[name=chk]").prop("checked", true);
		//else $("input[name=chk]").prop("checked", false);
		
		$('#dataListbody tr').each(function(index){
			let chk = $(this).closest('tr').find('input[name=chk]').prop('disabled');
			if(!chk){
				if($("#allCheck").is(":checked")){
					$(this).closest('tr').find('input[name=chk]').prop("checked", true);
					$(this).closest('tr').find('input[name=rowAttri]').val('mdf');
				}else{
					$(this).closest('tr').find('input[name=chk]').prop("checked", false);
					$(this).closest('tr').find('input[name=rowAttri]').val('search');
				}
			}
		});
	});
	
	//-- 탭 클릭 이벤트 --------------------
	$("#prodTabs li").click(function() {
		let tabId = this.id;
		
		// 제안 탭
		if (tabId == "pro01") {									
			$('#pro01').addClass("on");
			$('#pro02').removeClass("on");
			$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0320.do'/>");
			$("#hiddenForm").attr("method", "post").submit();
		// 약정서 탭	
		} else if (tabId == "pro02") {
			$('#pro01').removeClass("on");
			$('#pro02').addClass("on");
			$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0330.do'/>");			
			$("#hiddenForm").attr("method", "post").submit(); 
		}
	});
	
	let today = "${srchFromDt}";
	if( today.replace(/[^0-9]/g, "") > ssnlProdObj.wntrSsn.replace(/[^0-9]/g, "") ){
		ssnlProdObj.newSemester = "${curYear}" +"-09-30";
	}else{
		ssnlProdObj.newSemester = "${curYear}" +"-03-31";
	}
	
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
	
	if( $('.'+rowNum).find("select[name='rtnResn'] option:selected").val() != 'A' ){
		//let now = new Date( $('.'+rowNum).find('input[name="rtnRegDy"]').val() );	// 등록일 기준 날짜
		//now.setMonth( now.getMonth() + 2 );
		//let year = now.getFullYear(); // 년도
		//let month = ('0' + (now.getMonth() + 1)).slice(-2); // 다음달  
		//let day = ('0' + now.getDate()).slice(-2); // 일
		//$('.'+rowNum).find('input[name="rtnClsDy"]').val( year + '-' + month + '-' + day ); // 반품 마감일 데이터 세팅
	}else{
		
	}
}

// 반품 사유 change event
function chgEvent(obj){
	
	$(obj).closest('tr').find("select[name='rtnResnDtl']").empty(); // 반품 상세 사유
	$(obj).closest('tr').find('input[name=rowAttri]').val('mdf');
	
	//let now = new Date( "${prevMonth}" );
	let now = new Date( $(obj).closest('tr').find('input[name=rtnRegDy]').val() );
	if( $(obj).val() == 'A' )	{
		$(obj).closest('tr').find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl > option").clone());
		$(obj).closest('tr').find("select[name='rtnResnDtl'] option[value='1'], select[name='rtnResnDtl'] option[value='2'], select[name='rtnResnDtl'] option[value='3'], select[name='rtnResnDtl'] option[value='4'], select[name='rtnResnDtl'] option[value='5']").remove();
		let today = new Date( "${srchFromDt}" );
		today.setHours(0, 0, 0, 0);
		let firstDataChk = 0;
		$.each(ssnlProdObj, function(key, dateStr) {
			let dateObj = new Date(dateStr); // 문자열을 Date 객체로 변환
			dateObj.setHours(0, 0, 0, 0); // 시간 제거
		    if (dateObj < today) {
		    	if( key == 'lunarNewYear' )	$(obj).closest('tr').find("select[name='rtnResnDtl'] option[value='6']").remove();
		    	else if( key == 'newYear' ) $(obj).closest('tr').find("select[name='rtnResnDtl'] option[value='7']").remove();
		    	else if( key == 'valentineDay' ) $(obj).closest('tr').find("select[name='rtnResnDtl'] option[value='8']").remove();
		    	else if( key == 'whiteDay' ) $(obj).closest('tr').find("select[name='rtnResnDtl'] option[value='9']").remove();
		    	else if( key == 'arborDay' ) $(obj).closest('tr').find("select[name='rtnResnDtl'] option[value='10']").remove();
		    	else if( key == 'newSemester' ) $(obj).closest('tr').find("select[name='rtnResnDtl'] option[value='11']").remove();
		    	else if( key == 'chldrDay' ) $(obj).closest('tr').find("select[name='rtnResnDtl'] option[value='12']").remove();
		    	else if( key == 'chuseok' ) $(obj).closest('tr').find("select[name='rtnResnDtl'] option[value='13']").remove();
		    	else if( key == 'hlwnDay' ) $(obj).closest('tr').find("select[name='rtnResnDtl'] option[value='14']").remove();
		    	else if( key == 'pprDay' ) $(obj).closest('tr').find("select[name='rtnResnDtl'] option[value='15']").remove();
		    	else if( key == 'pckldSsn' ) $(obj).closest('tr').find("select[name='rtnResnDtl'] option[value='16']").remove();
		    	else if( key == 'christmas' ) $(obj).closest('tr').find("select[name='rtnResnDtl'] option[value='17']").remove();
		    	else if( key == 'sumerSsn' ) $(obj).closest('tr').find("select[name='rtnResnDtl'] option[value='18']").remove();
		    	else if( key == 'wntrSsn' ) $(obj).closest('tr').find("select[name='rtnResnDtl'] option[value='19']").remove();
			}else{
				if(firstDataChk == 0) firstDataChk = dateObj;
			}
		});
		if(firstDataChk == 0) firstDataChk = today;
		now = new Date( firstDataChk );
	}else if( $(obj).val() == 'B' ) $(obj).closest('tr').find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl option[value='1'], #srcRtnResnDtl option[value='5']").clone());
	else if( $(obj).val() == 'C' ) $(obj).closest('tr').find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl option[value='2']").clone());
	else if( $(obj).val() == 'D' ) $(obj).closest('tr').find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl option[value='3'], #srcRtnResnDtl option[value='4']").clone());
	setRtnClsDy( now, obj);
}

// 반품 상세 사유 change event
function chgRtnResnDtl(obj){
	
	$(obj).closest('tr').find('input[name=rowAttri]').val('mdf');
	
	let now;
	if( $(obj).val() == '6' )		now = new Date( ssnlProdObj.lunarNewYear );
	else if( $(obj).val() == '7' ) now = new Date( ssnlProdObj.newYear );
	else if( $(obj).val() == '8' ) now = new Date( ssnlProdObj.valentineDay );
	else if( $(obj).val() == '9' ) now = new Date( ssnlProdObj.whiteDay );
	else if( $(obj).val() == '10' ) now = new Date( ssnlProdObj.arborDay );
	else if( $(obj).val() == '11' ) now = new Date( ssnlProdObj.newSemester );
	else if( $(obj).val() == '12' ) now = new Date( ssnlProdObj.chldrDay );
	else if( $(obj).val() == '13' ) now = new Date( ssnlProdObj.chuseok );
	else if( $(obj).val() == '14' ) now = new Date( ssnlProdObj.hlwnDay );
	else if( $(obj).val() == '15' ) now = new Date( ssnlProdObj.pprDay );
	else if( $(obj).val() == '16' ) now = new Date( ssnlProdObj.pckldSsn );
	else if( $(obj).val() == '17' ) now = new Date( ssnlProdObj.christmas );
	else if( $(obj).val() == '18' ) now = new Date( ssnlProdObj.sumerSsn );
	else if( $(obj).val() == '19' ) now = new Date( ssnlProdObj.wntrSsn );
	
	setRtnClsDy(now, obj);
}

/* 조회 */
function btnSearch() {
	let searchInfo = {};
	
	$('#searchForm').find('input , select').map(function() {
		searchInfo[this.name] = $(this).val().replaceAll("-","");
	});
	
	//console.log(searchInfo);
	
    $.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		url : '<c:url value="/edi/product/selectProdRtnItemList.json"/>',
		data : JSON.stringify(searchInfo),
		success : function(data) {
			
			//json 으로 호출된 결과값을 화면에 Setting
			_setTbody(data.list);
		}
	});	
    
}

/* List Data 셋팅 */
function _setTbody(json) {
	let data = json;
	setTbodyInit("dataListbody"); // dataList 초기화
	
	let today = new Date( "${srchFromDt}" );
	today.setHours(0, 0, 0, 0);
	let firstDataChk = 0;
	
   	if (json.length > 0) {
        // 데이터에 count 추가 (인덱스 역할)
        for (let i = 0; i < data.length; i++) {
            //json[i].count = i + 1; // 1부터 시작하도록 설정
            data[i].rowAttri = 'search';
            data[i].trCount = i;
        }
        
        $("#dataListTemplate").tmpl(data).appendTo("#dataListbody");
		//$("select[name='teamCd']").append($("#srcTeamCd > option").clone());
		$("select[name='rtnResnPlce']").append($("#srcRtnResnPlce > option").clone());
		$("select[name='rtnResn']").append($("#srcRtnResn > option").clone());
		//$("select[name='rtnResnDtl']").append($("#srcRtnResnDtl > option").clone());
		//$("select[name='rtnResnDtl'] option[value='15'], select[name='rtnResnDtl'] option[value='16'], select[name='rtnResnDtl'] option[value='17'], select[name='rtnResnDtl'] option[value='18']").remove();
		//$("select[name='teamCd'] option:contains('선택')").remove();
		
		for (let i = 0; i < json.length; i++) {
			$(".tr"+data[i].trCount).find("select[name='rtnResnDtl']").empty(); // 반품 상세 사유
			
			if( data[i].rtnResn == 'A' ){
				$(".tr"+data[i].trCount).find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl > option").clone());
				$(".tr"+data[i].trCount).find("select[name='rtnResnDtl'] option[value='1'], select[name='rtnResnDtl'] option[value='2'], select[name='rtnResnDtl'] option[value='3'], select[name='rtnResnDtl'] option[value='4'], select[name='rtnResnDtl'] option[value='5']").remove();
			}else if( data[i].rtnResn == 'B' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl option[value='1'], #srcRtnResnDtl option[value='5']").clone());
			else if( data[i].rtnResn == 'C' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl option[value='2']").clone());
			else if( data[i].rtnResn == 'D' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl option[value='3'], #srcRtnResnDtl option[value='4']").clone());
			
			$(".tr"+data[i].trCount).find("select[name='teamCd']").val( data[i].teamCd );
			$(".tr"+data[i].trCount).find("select[name='rtnResnPlce']").val( data[i].rtnResnPlce );
			$(".tr"+data[i].trCount).find("select[name='rtnResn']").val( data[i].rtnResn );
			$(".tr"+data[i].trCount).find("select[name='rtnResnDtl']").val( data[i].rtnResnDtl );
			$(".tr"+data[i].trCount).find("select[name='bukrs']").val( data[i].bukrs );
			$(".tr"+data[i].trCount).find("select[name='bukrs']").attr('disabled', true);
			
			if(data[i].prdtStatus != '0'){
				$(".tr"+data[i].trCount).find("select, input").attr('disabled', true);
			}
			
			if(data[i].prodTypFg == '2'){
				$(".tr"+data[i].trCount).find("select[name='rtnResn'] option[value='A']").remove();
			}
			
			$.each(ssnlProdObj, function(key, dateStr) {
				let dateObj = new Date(dateStr); // 문자열을 Date 객체로 변환
				dateObj.setHours(0, 0, 0, 0); // 시간 제거
			    if (dateObj < today) {
			    	if( key == 'lunarNewYear' )	$(".tr"+data[i].trCount).find("select[name='rtnResnDtl'] option[value='6']").remove();
			    	else if( key == 'newYear' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl'] option[value='7']").remove();
			    	else if( key == 'valentineDay' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl'] option[value='8']").remove();
			    	else if( key == 'whiteDay' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl'] option[value='9']").remove();
			    	else if( key == 'arborDay' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl'] option[value='10']").remove();
			    	else if( key == 'newSemester' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl'] option[value='11']").remove();
			    	else if( key == 'chldrDay' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl'] option[value='12']").remove();
			    	else if( key == 'chuseok' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl'] option[value='13']").remove();
			    	else if( key == 'hlwnDay' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl'] option[value='14']").remove();
			    	else if( key == 'pprDay' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl'] option[value='15']").remove();
			    	else if( key == 'pckldSsn' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl'] option[value='16']").remove();
			    	else if( key == 'christmas' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl'] option[value='17']").remove();
			    	else if( key == 'sumerSsn' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl'] option[value='18']").remove();
			    	else if( key == 'wntrSsn' ) $(".tr"+data[i].trCount).find("select[name='rtnResnDtl'] option[value='19']").remove();
				}
			});
        }
		
   	}else{
   		setTbodyNoResult("dataListbody", 11);
   	}
}


/* 이 펑션은 협력업체 검색창에서 호출하는 펑션임    */
function setVendorInto(strVendorId, strVendorNm, strCono) {
	$("#srchEntpCd").val(strVendorId);
}


//행추가, 행삭제
function rowBtnEvent(gbn){
	// 행추가(Add), 행삭제(Del)
	
	// 행추가
	if(gbn == 'Add'){
		
		if( $("select[name='srcVenCd'] option:selected").val() == ''){
			alert('파트너사을 선택해주세요!');
			return;
		}
		
		if( $("select[name='srcTeamCd'] option:selected").val() == ''){
			alert('팀을 선택해주세요!');
			return;
		}
		
		let checkTdm = $("#dataListbody tr").find(".tdm").length > 0;

		// checkTdm 이 있을 때만 초기화 실행
		if (checkTdm) {
			setTbodyInit("dataListbody"); // dataList 초기화
		}
		
		let rowData = {
					'rowAttri' 	: 	'new'
				,	'prodRtnNo'	:	''
				,	'teamCd'	:	$("select[name='srcTeamCd'] option:selected").val()
				,	'teamNm'	:	$("select[name='srcTeamCd'] option:selected").text()
				,	'srcmkCd'	:	''
				,	'prodCd'	:	''
				,	'rtnRegDy'	:	"${srchFromDt}"
				,	'rtnClsDy'	:	''
				,	'reqMdDy'	:	''
				,	'prdtStatusNm'	:	'등록대기'
				,	'prdtStatus'	:	'0'
				,	'rtnResn'	:	''
				,	'rtnResnDtl'	:	''
				,	'rtnResnPlce'	:	''
				,	'venCd'			:	$("select[name='srcVenCd'] option:selected").val()
				,	'trCount'		:	trCount
		};
		$("#dataListTemplate").tmpl(rowData).appendTo("#dataListbody");
		//$(".tr"+trCount).find("select[name='teamCd']").append($("#srcTeamCd > option").clone());
		//$(".tr"+trCount).find("select[name='teamCd'] option:contains('선택')").remove();
		$(".tr"+trCount).find("select[name='rtnResnPlce']").append($("#srcRtnResnPlce > option").clone());
		$(".tr"+trCount).find("select[name='rtnResn']").append($("#srcRtnResn > option").clone());
		$(".tr"+trCount).find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl > option").clone());
		$(".tr"+trCount).find("select[name='rtnResnDtl'] option[value='1'], select[name='rtnResnDtl'] option[value='2'], select[name='rtnResnDtl'] option[value='3'], select[name='rtnResnDtl'] option[value='4'], select[name='rtnResnDtl'] option[value='5']").remove();
		
		let today = new Date( "${srchFromDt}" );
		today.setHours(0, 0, 0, 0);
		let firstDataChk = 0;
		$.each(ssnlProdObj, function(key, dateStr) {
			let dateObj = new Date(dateStr); // 문자열을 Date 객체로 변환
			dateObj.setHours(0, 0, 0, 0); // 시간 제거
		    if (dateObj < today) {
		    	if( key == 'lunarNewYear' )	$(".tr"+trCount).find("select[name='rtnResnDtl'] option[value='6']").remove();
		    	else if( key == 'newYear' ) $(".tr"+trCount).find("select[name='rtnResnDtl'] option[value='7']").remove();
		    	else if( key == 'valentineDay' ) $(".tr"+trCount).find("select[name='rtnResnDtl'] option[value='8']").remove();
		    	else if( key == 'whiteDay' ) $(".tr"+trCount).find("select[name='rtnResnDtl'] option[value='9']").remove();
		    	else if( key == 'arborDay' ) $(".tr"+trCount).find("select[name='rtnResnDtl'] option[value='10']").remove();
		    	else if( key == 'newSemester' ) $(".tr"+trCount).find("select[name='rtnResnDtl'] option[value='11']").remove();
		    	else if( key == 'chldrDay' ) $(".tr"+trCount).find("select[name='rtnResnDtl'] option[value='12']").remove();
		    	else if( key == 'chuseok' ) $(".tr"+trCount).find("select[name='rtnResnDtl'] option[value='13']").remove();
		    	else if( key == 'hlwnDay' ) $(".tr"+trCount).find("select[name='rtnResnDtl'] option[value='14']").remove();
		    	else if( key == 'pprDay' ) $(".tr"+trCount).find("select[name='rtnResnDtl'] option[value='15']").remove();
		    	else if( key == 'pckldSsn' ) $(".tr"+trCount).find("select[name='rtnResnDtl'] option[value='16']").remove();
		    	else if( key == 'christmas' ) $(".tr"+trCount).find("select[name='rtnResnDtl'] option[value='17']").remove();
		    	else if( key == 'sumerSsn' ) $(".tr"+trCount).find("select[name='rtnResnDtl'] option[value='18']").remove();
		    	else if( key == 'wntrSsn' ) $(".tr"+trCount).find("select[name='rtnResnDtl'] option[value='19']").remove();
			}else{
				if(firstDataChk == 0) firstDataChk = dateObj;
			}
		});
		if(firstDataChk == 0) firstDataChk = today;
		let now = new Date( firstDataChk );
		setRtnClsDy(now, $(".tr"+trCount).find("input[name='rtnClsDy']"));
		trCount++;
	}else{
		if( !$('#dataListbody tr').find('input:checked').is(':checked') ){
			alert("삭제 할 Row를 먼저 선택해주세요!");
			return;
		}
		
		if(!confirm("<spring:message code='msg.common.confirm.delete'/>")) return;
		// 행삭제
		let msg = '<spring:message code="msg.common.success.delete" />';
		$('#dataListbody tr').find('input:checked').each(function(index){
			if( $(this).closest('tr').find('input[name=rowAttri]').val() == 'search' ){
				// server
				let searchInfo = {};
				$(this).closest('tr').find('input , select').map(function() {
					if( this.name == 'rtnRegDy' || this.name == 'rtnClsDy' || this.name == 'reqMdDy' )	searchInfo[this.name] = $(this).val().replace(/[^0-9]/g, "");
					else searchInfo[this.name] = $(this).val();
				});
				searchInfo['delYn'] = 'Y';
				searchInfo['rtnClsDy'] = $(this).closest('tr').find('input[name="rtnClsDy').val().replace(/[^0-9]/g, "");
				
				$.ajax({
					contentType : 'application/json; charset=utf-8',
					type : 'post',
					dataType : 'json',
					url : '<c:url value="/edi/product/deleteProdRtnItem.json"/>',
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

//판매코드 팝업
function _sellCdPopUp(){
	
	Common.centerPopupWindow( "<c:url value='/edi/product/selSrcmkCdPopup.do'/>?ean11="+$("#searchForm").find('input[name=srcmkCd]').val() 
			+ "&trNum=src"
			, 'popup', {width: 800, height: 550});
}


// btn event
function btnEvent(gbn, obj){
	// 저장(sava), 업로드 양식 다운로드(excelFrom), 업로드(excelUpl), 판매바코드 찾기(srcBarcode), MD협의요청(mdReq)
	
	let searchInfo = {};
	let itemList = [];
	
	$('#searchForm').find('input , select').map(function() {
		if( this.name == 'rtnRegDy' || this.name == 'rtnClsDy' || this.name == 'reqMdDy' )	searchInfo[this.name] = $(this).val().replace(/[^0-9]/g, "");
		else searchInfo[this.name] = $(this).val();
	});
	
	let checkTdm = $("#dataListbody tr").find(".tdm").length > 0;
	if (!checkTdm) {
		if(gbn == 'mdReq'){
			// MD협의요청 일경우 체크한것만.
			$('#dataListbody tr').find('input:checked').each(function(index){
				var itemObj = {};
				$(this).closest('tr').find('input , select').map(function() {
					if( this.name == 'rtnRegDy' || this.name == 'rtnClsDy' || this.name == 'reqMdDy' )	itemObj[this.name] = $(this).val().replace(/[^0-9]/g, "");
					else itemObj[this.name] = $(this).val();
				});
				itemList.push(itemObj);
			});
		}else{
			$.each($("#dataListbody tr"), function(idx, item) {
				var itemObj = {};
				if( $(this).find('input[name=rowAttri]').val() != 'search' ){
					$.each($(this).find('input , select'), function(idx, item) {
				     	if( this.name == 'rtnRegDy' || this.name == 'rtnClsDy' || this.name == 'reqMdDy' )	itemObj[this.name] = item.value.replace(/[^0-9]/g, "");
				     	else itemObj[this.name] = item.value;
					});
					itemObj['rtnClsDy'] = $(this).find('input[name="rtnClsDy').val().replace(/[^0-9]/g, "");
					itemList.push(itemObj);
				}
		    });
		}
	}
	
	searchInfo['itemList'] = itemList;
	searchInfo['rfcParam'] = JSON.stringify(searchInfo);
	
	//console.log(searchInfo);
	
	if(gbn == 'excelFrom'){
		// 업로드 양식 다운로드
		let  f = document.hiddenForm;
		f.action = "<c:url value='/edi/product/selectProdRtnItemExcelFromDown.do' />";
    	f.submit();
	}else if(gbn == 'excelUpl'){
		// 업로드
		
		let targetUrl = '<c:url value="/edi/comm/commExcelUploadPopupInit.do"/>';
		let heightVal = 350;
		let excelSysKnd = "320";	//엑셀 업로드 반품 구분값
		let repVendorId = "<c:out value='${epcLoginVO.repVendorId}'/>";
		
		Common.centerPopupWindow(targetUrl+"?excelSysKnd="+excelSysKnd+"&entpCd="+repVendorId, 'excelUploadPopup', {width : 730, height : heightVal});
		
	}else if(gbn == 'srcBarcode'){
		// 판매바코드 찾기
		$(obj).closest('tr').find('input[name=rowAttri]').val('mdf');
		Common.centerPopupWindow( "<c:url value='/edi/product/selSrcmkCdPopup.do'/>?srcmkCd="+$(obj).closest('tr').find('input[name=srcmkCd]').val() 
				+ "&trNum=" + $(obj).closest('tr').attr('class')
				+ "&entpCd=" + $(obj).closest('tr').find('input[name=venCd]').val()
				+ "&teamCd=" + $(obj).closest('tr').find('input[name=teamCd]').val()
				, 'popup', {width: 800, height: 550});
	}else{ // 저장(sava), MD협의요청(mdReq)
		
		let dataList = searchInfo.itemList;
		for(let i = 0; i < dataList.length; i++ ){
			
			if( dataList[i].prodCd == '' ){
				alert('판매코드를 선택해주세요!');
				return false;
			}
			
			if(gbn == 'mdReq'){
				if( dataList[i].prdtStatus != '0' || dataList[i].prodRtnNo == '' ){
					alert('진행상태가 "파트너사등록"만 MD 협의요청을 할 수 있습니다');
					return false;
				}	
			}
		}
		
		// 저장
		if(gbn == 'save'){
			
			if( itemList.length <= 0){
				alert('변경된 내용이 없습니다');
				return;
			}
			
			if(!confirm("<spring:message code='msg.srm.alert.tempSave'/>")) return;
			if(!validation(searchInfo))return;
			url = "<c:url value='/edi/product/insertProdRtnItem.json'/>";
			searchInfo['delYn'] = 'N';
		}else if(gbn == 'mdReq'){
			// MD협의요청
			
			if( !$('#dataListbody tr').find('input:checked').is(':checked') ){
				alert("MD 합의요청을 진행하려는 Row를 먼저 선택해주세요!");
				return;
			}
			
			if(!confirm("<spring:message code='msg.common.confirm.appr.req'/>")) return;
			url = "<c:url value='/edi/product/insertProdRtnItemRfcCall.json'/>";
			searchInfo['prdtStatus'] = '1'; // 요청완료
		}
		
		//console.log(searchInfo);
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			url : url,
			data : JSON.stringify(searchInfo),
			success : function(data) {
				if(data.result == false) alert('저장에 실패했습니다.\n다시 확인해주세요.');
				else alert(data.msg);
				$("select[name='srcTeamCd'] option:eq(0)").attr("selected", true);
				$("select[name='srcVenCd'] option:eq(0)").attr("selected", true);
				btnSearch('1');
			}
		});
	}
}

//검증
function validation(info){
	/*let srchFromDt = $("input[name=srchFromDt]").val().replace(/[^0-9]/g, "");
	let srchEndDt = $("input[name=srchEndDt]").val().replace(/[^0-9]/g, "");
	
	//날짜 필수값 체크
	if(srchFromDt == "" || srchEndDt == ""){
		alert("등록기간 <spring:message code='msg.common.fail.nocalendar'/>");
		return false;
	}
	
	//시작일보다 작은지 여부 체크
    if (srchFromDt > srchEndDt) {
        alert("등록기간은 <spring:message code='msg.common.fail.calendar'/>");
        return false;
    }*/
	
	let dataList = info.itemList;
	
	for(let i = 0; i < dataList.length; i++ ){
		if( dataList[i].rtnRegDy == ""){
			alert('반품요청일자 필수입니다.');
			return false;
		}
	}
    return true;
}


//판매 바코드 정보 return
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
	let prodPatFg = data.prodPatFg;	//상품패턴구분
	let prodTypFg = data.prodTypFg;	//상품유형구분
	let dispUnit = data.dispUnit;	//표시단위
	let today = "${srchFromDt}";
	
	let chkVal = true;
	let msg = '';
	$.each($("#dataListbody tr"), function(idx, item) {
		let venCd = $(this).find('input[name=venCd]').val();					// 리스트의 파트너사코드
		let rowVenCd = $('.'+ trNum ).find('input[name=venCd]').val();			// row의 파트너사코드
		let rowProdCd = $(this).find('input[name=prodCd]').val();				// 리스트의 상품코드
		let rowRtnClsDy = $(this).find('input[name=rtnClsDy]').val();			// 리스트의 반품마감일
		let prdtStatus = $(this).find('input[name=prdtStatus]').val();			// 진행상태
		let bukrs = $(this).find("select[name='bukrs'] option:selected").val(); // 리스트의 계열사
		let rowBukrs = $('.'+ trNum ).find("select[name='bukrs'] option:selected").val(); // row의 계열사
		
		switch(prdtStatus){
			case '0':
				if( (bukrs == rowBukrs && venCd == rowVenCd && rowProdCd == prodCd) ){ //&& (today.replace(/[^0-9]/g, "") >= rowRtnClsDy.replace(/[^0-9]/g, "")) ){
					if($('.'+ trNum ).find('input[name=rowAttri]').val() == 'mdf'){
						msg = '파트너사코드 : ' + venCd + ' 상품코드 : ' + rowProdCd + '\n리스트에 판매코드가 존재합니다.';
						chkVal = false;
					}
				}
			break;
			case '3':
				break;
			default:
			if( (bukrs == rowBukrs && venCd == rowVenCd && rowProdCd == prodCd) && (today.replace(/[^0-9]/g, "") <= rowRtnClsDy.replace(/[^0-9]/g, "")) ){
				if( today.replace(/[^0-9]/g, "") <= rowRtnClsDy.replace(/[^0-9]/g, "") ){
					msg = '파트너사코드 : ' + venCd + ' 상품코드 : ' + rowProdCd + '의 반품마감일이 지나지않았습니다.';
					chkVal = false;
				}
			}
		}
	});
	
	// 데이터 체크 통과하면..
	if( chkVal ){
		// 조회조건 - 판매코드
		if(trNum == 'src'){
			$('#searchForm input[name=srcmkCd]').val(srcmkCd);
			$('#searchForm input[name=srcmkNm]').val(prodNm);
		}else{
			// 리스트 판매코드
			$('.'+ trNum ).find('input[name=srcmkCd]').val(srcmkCd);
			$('.'+ trNum ).find('input[name=prodCd]').val(prodCd);
			$('.'+ trNum ).find('input[name=prodNm]').val(prodNm);
		}
		// 반품사유, 반품상세사유 초기화
		$('.'+ trNum ).find("select[name='rtnResn'], select[name='rtnResnDtl']").empty();
		$('.'+ trNum ).find("select[name='rtnResn']").append($("#srcRtnResn > option").clone());
		// PB유형구분값이 2면
		if(prodTypFg == '2'){
			// 시즌상품 삭제
			$('.'+ trNum ).find("select[name='rtnResn'] option[value='A']").remove();
			$('.'+ trNum ).find("select[name='rtnResnDtl'] option[value='1']").remove();
			$('.'+ trNum ).find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl option[value='1'], #srcRtnResnDtl option[value='5']").clone());
		}else{
			$('.'+ trNum ).find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl > option").clone());
			$('.'+ trNum ).find("select[name='rtnResnDtl'] option[value='1'], select[name='rtnResnDtl'] option[value='2'], select[name='rtnResnDtl'] option[value='3'], select[name='rtnResnDtl'] option[value='4'], select[name='rtnResnDtl'] option[value='5']").remove();
		}
	}else alert(msg);
	
	
}

//row change
function chgRowAttri(obj){
	 $(obj).closest('tr').find('input[name=rowAttri]').val('mdf');
}

function setRtnClsDy(now, obj){
	now.setMonth( now.getMonth() + 1 );
	let year = now.getFullYear(); // 년도
	let month = ('0' + (now.getMonth() + 1)).slice(-2); // 다음달  
	let day = ('0' + now.getDate()).slice(-2); // 일
	$(obj).closest('tr').find("input[name='rtnClsDy']").val( year + '-' + month + '-' + day ); // 반품 마감일 데이터 세팅
}

//엑셀 업로드 콜백 데이터 세팅
function excelUploadcallBack(data){
	let rowData = {
			'reqType' : $("select[name='reqType'] option:selected").val()
		,	'trCount'	: trCount
	};
	let list = data.resultMap.list;
	let msg = data.message;
	
	// 기존 데이터 초기화 
	$("#dataListbody").empty();
	
	for (let i = 0; i < list.length; i++) {
		let newRowData = {};
		newRowData.trCount = i;
		newRowData.prodRtnNo = "";					// 반품요청번호
		newRowData.rtnRegDy = list[i].data1;		// 등록일자
		newRowData.venCd = list[i].data3;			// 파트너사코드
		newRowData.teamCd = list[i].data4;			// 팀코드
		newRowData.teamNm = list[i].teamNm;			// 팀명
		newRowData.srcmkCd = list[i].data5;			// 판매코드
		newRowData.prodCd = list[i].data6;			// 상품코드
		newRowData.prodNm = list[i].prodNm;			// 상품명
		newRowData.rtnClsDy = list[i].data7;		// 반품마감일
		newRowData.prdtStatus = '0';				// 진행상태
		newRowData.prdtStatusNm = '등록대기';			// 진행상태명
		
		if(list[i].stsChk == 'P'){
			newRowData.prodCd = list[i].prodCd;
			// 템플릿에 가져온 데이터 맵핑
			$("#dataListTemplate").tmpl(newRowData).appendTo("#dataListbody");
			
			let rtnResnPlceSize = $(".tr"+newRowData.trCount).find("select[name='rtnResnPlce'] option").size();
			let rtnResnSize = $(".tr"+newRowData.trCount).find("select[name='rtnResn'] option").size();
			if(rtnResnPlceSize == 0) $(".tr"+newRowData.trCount).find("select[name='rtnResnPlce']").append($("#srcRtnResnPlce > option").clone());
			if(rtnResnSize == 0) $(".tr"+newRowData.trCount).find("select[name='rtnResn']").append($("#srcRtnResn > option").clone());
		}
		
		$(".tr"+newRowData.trCount).find("select[name='rtnResnDtl']").empty(); // 반품 상세 사유
		
		if( list[i].data8 == 'A' ){
			$(".tr"+newRowData.trCount).find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl > option").clone());
			$(".tr"+newRowData.trCount).find("select[name='rtnResnDtl'] option[value='1'], select[name='rtnResnDtl'] option[value='2'], select[name='rtnResnDtl'] option[value='3'], select[name='rtnResnDtl'] option[value='4'], select[name='rtnResnDtl'] option[value='5']").remove();
		}else if( list[i].data8 == 'B' ) $(".tr"+newRowData.trCount).find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl option[value='1'], #srcRtnResnDtl option[value='5']").clone());
		else if( list[i].data8 == 'C' ) $(".tr"+newRowData.trCount).find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl option[value='2']").clone());
		else if( list[i].data8 == 'D' ) $(".tr"+newRowData.trCount).find("select[name='rtnResnDtl']").append($("#srcRtnResnDtl option[value='3'], #srcRtnResnDtl option[value='4']").clone());
		
		$(".tr"+newRowData.trCount).find("select[name='teamCd']").val( list[i].data4 );
		$(".tr"+newRowData.trCount).find("select[name='rtnResn']").val( list[i].data8 );
		$(".tr"+newRowData.trCount).find("select[name='rtnResnDtl']").val( list[i].data9 );
		$(".tr"+newRowData.trCount).find("select[name='rtnResnPlce']").val( list[i].data10 );
		$(".tr"+newRowData.trCount).find("select[name='bukrs']").val( list[i].data2 );
		
		if(list[i].prodTypFg == '2'){
			$(".tr"+newRowData.trCount).find("select[name='rtnResn'] option[value='A']").remove();
		}
	}
	if( msg != '') alert(msg);
}



</script>
<!-- DATA LIST -->
<script id="dataListTemplate" type="text/x-jquery-tmpl">
<tr class="tr\${trCount}"  bgcolor=ffffff>
	<td align="center">
		<input type="checkbox" name="chk" value="" style="height: 20px;" />
	</td>
	<td align="center">
		<input type="text" name="rtnRegDy" id="rtnRegDy\${trCount}"  value="<c:out value="\${rtnRegDy}"/>" style="width: 80px;" disabled/>
		{%if prdtStatus == "0"%}
			<img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor: pointer;" />				
		{%/if%}
	</td>
	<td align="center">
		<select id="bukrs" name="bukrs" class="required" style="width:130px;" onchange="chgRowAttri(this)">
			<option value="KR02" >롯데마트슈퍼(통합)</option>
			<option value="KR03" >CS유통</option>
			<option value="KR99" >완전가맹</option>
		</select>
	</td>
	<td align="center">
		<c:out value="\${venCd}"/>
	</td>
	<td align="center">
		<c:out value="\${teamNm}"/>
	</td>
	<td align="center">
		<input type="text" name="srcmkCd" value="\${srcmkCd}" style="width: 85%;" disabled/>
		{%if prdtStatus == "0"%}
			<i class="bi bi-search" style='margin-left: 5px; cursor: pointer;' onclick='btnEvent("srcBarcode", this)' onchange="chgRowAttri(this)"></i>				
		{%/if%}		
	</td>
	<td align="center">
		<input type="text" name="prodNm" value="\${prodNm}" style="width: 99%;" disabled onchange="chgRowAttri(this)"/>
	</td>
	<td align="center" class="rtnClsDy">
		<input type="text" name="rtnClsDy" id="rtnClsDy\${trCount}"  value="<c:out value="\${rtnClsDy}"/>" style="width: 80px;" disabled/>
		{%if prdtStatus == "0"%}
			<img src="/images/epc/layout/btn_cal.gif" class="middle datepicker" style="cursor: pointer;" />				
		{%/if%}
	</td>
	<td align="center">
		<c:out value="\${reqMdDy}"/>
	</td>
	<td align="center">
		<c:out value="\${prdtStatusNm}"/>
	</td>
	<td align="center">
		<select id="rtnResn" name="rtnResn" class="required" style="width:130px;" onchange="javascript:chgEvent(this);" />
	</td>
	<td align="center">
		<select id="rtnResnDtl" name="rtnResnDtl" class="required" style="width:130px;" onchange="javascript:chgRtnResnDtl(this);" />
	</td>
	<td align="center">
		<select id="rtnResnPlce" name="rtnResnPlce" class="required" style="width:130px;" onchange="chgRowAttri(this)" />
	</td>
	<input type="hidden" name="rowAttri" value="\${rowAttri}"  />
	<input type="hidden" name="prodCd" value="\${prodCd}"  />
	<input type="hidden" name="venCd" value="\${venCd}"  />
	<input type="hidden" name="teamCd" value="\${teamCd}"  />
	<input type="hidden" name="prdtStatus" value="\${prdtStatus}"  />
	<input type="hidden" name="prodRtnNo" value="\${prodRtnNo}"  />
	<input type="hidden" name="prodTypFg" value="\${prodTypFg}"  />
</tr>
</script>
</head>
<body>
	<div id="content_wrap">
		<!-- tab 구성---------------------------------------------------------------->
		<div id="prodTabs" class="tabs" style="padding-top: 10px;">
			<ul>
				<li id="pro01" style="cursor: pointer;" class="on">제안</li>	<!-- 제안 -->
				<li id="pro02" style="cursor: pointer;">약정서</li>	<!-- 약정서 -->
			</ul>
		</div>
		<!-- tab 구성---------------------------------------------------------------->
		<div>
			<form name="searchForm" id="searchForm">
				<div id="wrap_menu">
					<div class="wrap_search">
						<div class="bbs_search">
							<ul class="tit">
								<li class="tit">검색조건</li>
								<li class="btn">
									<a href="#" class="btn" onclick="btnSearch('1')"><span>조회</span></a>
									<a href="#" class="btn" onclick="btnEvent('save')"><span>저장</span></a>																	
								</li>
							</ul>
							<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
								<colgroup>
									<col style="width:15%" />
									<col style="width:30%" />
									<col style="width:15%" />
									<col style="*" />
								</colgroup>
								<tr>
									<th>파트너사</th>
									<td>
										<%-- <c:if test="${epcLoginVO.vendorTypeCd eq '06'}">
											<input type="text" name="srchEntpCd" id="srchEntpCd" readonly="readonly" readonly="readonly" style="width:40%;" />
											<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
										</c:if>
										<c:if test="${epcLoginVO.vendorTypeCd ne '06'}">
											<html:codeTag objId="srchEntpCd" objName="srchEntpCd" width="150px;"  dataType="CP" comType="SELECT" formName="form" defName="전체" />
										</c:if> --%>
										<html:codeTag objId="srcVenCd" objName="srcVenCd" width="150px;" dataType="CP" comType="SELECT" defName="선택" formName="form"/>
									</td>
									<th>등록기간</th>
									<td>
										<c:if test="${empty param.srchFromDt}">
											<input type="text" class="day" name="srchFromDt" id="srchFromDt" style="width:80px;" value="<c:out value='${srchFromDt}'/>"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchFromDt');" style="cursor:hand;" />
											~
											<input type="text" class="day" name="srchEndDt" id="srchEndDt" style="width:80px;" value="<c:out value='${srchEndDt}'/>"> 	<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDt');"  style="cursor:hand;" />
										</c:if>
										<c:if test="${not empty param.srchFromDt}">
											<input type="text" class="day" name="srchFromDt" id="srchFromDt" style="width:80px;" value="<c:out value='${param.srchFromDt}'/>"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchFromDt');" style="cursor:hand;"/>
											~
											<input type="text" class="day" name="srchEndDt" id="srchEndDt" style="width:80px;" value="<c:out value='${param.srchEndDt}'/>"> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.srchEndDt');" style="cursor:hand;" />
										</c:if>
									</td>
								</tr>
								<tr>
									<th>팀</th>
									<td>
										<select id="srcTeamCd" name="srcTeamCd" class="required" style="width: 150px;">
											<option value="">
												<spring:message code="button.common.choice" /></option>
												<c:forEach items="${teamList}" var="teamList" varStatus="index">
													<option value="${teamList.teamCd}">${teamList.teamNm}</option>
												</c:forEach>
										</select>
									</td>
									<th>판매코드</th>
									<td>
										<input type="hidden" name="srcmkCd" id="srcmkCd"  style="width:40%;" placeholder="판매코드" />
										<input type="text" name="srcmkNm" id="srcmkNm"  style="width:80%;" placeholder="판매코드" disabled/>
										<i class="bi bi-search" style='margin-left: 5px; cursor: pointer;' onclick='_sellCdPopUp("srcBarcode", this)' ></i>
									</td>
								</tr>
								<tr>
									<th>진행상태</th>
									<td>
										<div style="float:left; id="srchIngStatus">
											<select name="srcStatus" id="srcStatus">
												<option value="ALL">전체</option>
												<option value="1">파트너사등록</option>
												<option value="2">MD 협의요청</option>
												<option value="3">MD 승인</option>
												<option value="4">MD 반려</option>
											</select>
										</div>
									</td>
									<th></th>
									<td>
										<!-- 히든!!! -->
										<html:codeTag objId="srcRtnResnPlce" objName="srcRtnResnPlce"  width="150px;"  parentCode="RTPLC"  display="none"  comType="SELECT" orderSeqYn="Y"  formName="form"  />
										<html:codeTag objId="srcRtnResn" objName="srcRtnResn"  width="150px;"  parentCode="RTRSN"  display="none"  comType="SELECT" orderSeqYn="Y"  formName="form"  />
										<html:codeTag objId="srcRtnResnDtl" objName="srcRtnResnDtl"  width="150px;"  parentCode="RTRSD"  display="none"  comType="SELECT" orderSeqYn="Y"  formName="form"  />
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</form>
			<form name="reqDetailForm" id="reqDetailForm">
				<div class="wrap_con">
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit">검색내역 </li>
							<li class="btn">
								<a href="#" class="btn" onclick="rowBtnEvent('Add');"><span>행추가</span></a>
								<a href="#" class="btn" onclick="rowBtnEvent('Del');"><span>행삭제</span></a>	
								<a href="#" class="btn" onclick="btnEvent('excelFrom');"><span>업로드 양식</span></a>		
								<a href="#" class="btn" onclick="btnEvent('excelUpl');"><span>업로드</span></a>		
								<a href="#" class="btn" onclick="btnEvent('mdReq')"><span>MD협의요청</span></a>		
							</li>
						</ul>
						<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
							<table id="dataTable" cellpadding="1" cellspacing="1" border="0" width=1530 bgcolor=efefef>
								<colgroup>
									<col style="width:2%"/>
									<col style="width:7%"/>
									<col style="width:7%"/>
									<col style="width:7%"/>
									<col style="width:10%"/>
									<col style="width:10%"/>
									<col style="width:10%"/>
									<col style="width:7%"/>
									<col style="width:7%"/>
									<col style="width:5%"/>
									<col style="width:5%"/>
									<col style="width:5%"/>
									<col style="width:5%"/>
								</colgroup>
								<tr bgcolor="#e4e4e4">
									<th><input type="checkbox" id="allCheck" name="chk"></th>
									<th>반품요청일자</th>
									<th>계열사</th>
									<th>파트너사코드</th>
									<th>팀명</th>
									<th>판매코드</th>
									<th>상품명</th>
									<th>반품마감일</th>
									<th>MD협의요청일</th>
									<th>진행상태</th>
									<th>반품사유</th>
									<th>반품상세사유</th>
									<th>반품장소</th>
								</tr>
								<tbody id="dataListbody" />
							</table>
						</div>
					</div>
				</div>
			</form>
				
			<div id="footer">
				<div id="footbox">
					<div class="msg" id="resultMsg"></div>
					<div class="notice"></div>
					<div class="location">
						<ul>
							<li>홈</li>
							<li>상품</li>
							<li>상품현황관리</li>
							<li class="last">반품 제안 등록</li>
						</ul>
					</div>
				</div>
			</div>
				
		</div>
	</div>
	<form name="hiddenForm" id="hiddenForm">
		<input type="hidden" id="prodRtnNo" 	name="prodRtnNo"  value=""	/> 	    	    	
		<!-- <input type="hidden" id="seq" 			name="seq"  value=""	/> --> 	    	    	
	</form>
</body>
</html>
