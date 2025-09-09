
/**
 * 문자열 왼쪽,오른쪽 공백을 제거.
 * @return {String} 공백제거된 문자열.
 */
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
}

/**
 * 문자열의 왼쪽 공백을 제거.
 * @return {String} 공백제거된 문자열.
 */
String.prototype.ltrim = function() {
	return this.replace(/^\s+/,"");
}

/**
 * 문자열의 오른쪽 공백을 제거.
 * @return {String} 공백제거된 문자열.
 */
String.prototype.rtrim = function() {
	return this.replace(/\s+$/,"");
}

/**
 * 해당문자열에 특정문자의 포함 여부 반환.
 * @param {String} findStr 찾을문자.
 * @return {Boolean} 특정문자포함여부
 */
String.prototype.contains = function(findStr){
	return this.indexOf(findStr) >= 0;
}

/**
 * 해당문자열의 특정문자(findStr)를 특정문자(newStr)로 전체치환.
 * @param {Object} findStr 찾을문자
 * @param {Object} newStr 대체문자
 * @return {String} 치환된문자열
 */
String.prototype.replaceAll = function(findStr, newStr){
    return this.replace(new RegExp(findStr, "gi"), newStr);
}

/**
 * 해당문자열의 바이트 수를 반환.
 * @return {Number} 문자열의 바이트 수
 */
String.prototype.getBytes = function() {
	var size = 0;
	for(i=0; i<this.length; i++) {
		var temp = this.charAt(i);
		if(escape(temp) == '%0D') continue;
		if(escape(temp).indexOf("%u") != -1) {
			size += 2;
		}else {
			size++;
		}
	}
	return size;
}

/**
 * Common Class
 * 공통으로 사용될 기능들을 구현한 샘플 클래스.
 * 스트링 NULL 체크 / NameSpace / Import
 *
 * @author: 김융규
 */
var Common = {

	/**
	 * 컨텍스트 패스.
	 */
	contextPath : "",

	/**
	 * 사용자 아이디.
	 */
	memberId : "",

	/**
	 * JSON 호출 결과 코드
	 */
	JSON_CALL_RESULT_CODE : {
		SUCCESS : 1,
		FAIL : 0
	},

	/**
	 * 다이얼로그 타이틀
	 */
	DIALOG_TITLE : {
		NOTICE : '알림',
		ALERT : '경고',
		INFO : '정보',
		CONFIRM : '확인'
	},

	/**
	 * 메세지
	 */
	MESSAGE : {
		BLOCK : '<h4>잠시만 기다려 주세요.</h4>'
	},


	/**
	 * 스트링 널 체크.
	 * @param {String} str
	 */
	isEmpty : function(str){
		if(str == null) return true;
		return !(str.replace(/(^\s*)|(\s*$)/g, ""));
	},

	/**
	 * 네임스페이스 등록.
	 * @param {String} ns 네임스페이스
	 */
	registNamespace : function(ns){
	    var nsParts = ns.split(".");
	    var root = window;

	    for(var i=0; i<nsParts.length; i++){
	        if (typeof root[nsParts[i]] == "undefined") {
				root[nsParts[i]] = new Object();
			}
	        root = root[nsParts[i]];
	    }
	},

	importJS : function(jsFile){
		$.ajax({
				type: "GET",
				cache:false,
				url: "/js/" + jsFile,
				async : false,
				dataType: "script"
			});
	},

	/**
	 * 팝업 윈도우 화면의 중간에 위치.
	 * @param {String} targetUrl	팝업 윈도우의 내용을 구성하기 위한 호출 URL
	 * @param {String} windowName	팝업 윈도우의 이름
	 * @param {Object} properties	팝업 윈도우의 속성(넓이, 높이, x/y좌표)
	 */
	centerPopupWindow : function(targetUrl, windowName, properties) {
		var childWidth = properties.width;
		var childHeight = properties.height;
		var childTop = (screen.height - childHeight) / 2 - 50;    // 아래가 가리는 경향이 있어서 50을 줄임
		var childLeft = (screen.width - childWidth) / 2;
		var popupProps = "width=" + childWidth + ",height=" + childHeight + ", top=" + childTop + ", left=" + childLeft;
		if (properties.scrollBars == "YES") {
			popupProps += ", scrollbars=yes";
		}
		if (properties.resizable == "YES") {
			popupProps += ", resizable=yes";
		}

		var popupWin = window.open(targetUrl, windowName, popupProps);
		popupWin.focus();
	},

	/**
	 * 업로드 하려는 파일의 이름 사이즈 체크.
	 * @param {String} uploadFileName 파일명
	 * @param {String} limitSize
	 */
	checkUploadFileNameSize : function(uploadFileName, limitSize){
    	if(!Common.isEmpty(uploadFileName)){
    		var index = uploadFileName.lastIndexOf("\\");
    		if(index > -1){
    			uploadFileName = uploadFileName.substring(index+1);
    		}

	    	if(uploadFileName.getBytes() > limitSize){
				Common.alertDialog("알림", "파일 명이 너무 길어요.");
				return false;
	    	}

	    	return true;
    	}else{
			return false;
    	}
	},

	/**
	 * toString
	 */
	toString : function(){
		return "Common Object";
	},

	/**
	 * X,Y 좌표의 구글 맵 화면을 띄움.
	 * @param {integer} x GPS X 위치
	 * @param {integer} y GPS Y 위치
	 * @param {String} message 내용
	 */
	viewMap : function(context, x, y, message){
	 	Common.centerPopupWindow(context+'/bbs/locationMap.do?x='+x+"&y="+y+"&message="+encodeURIComponent(message), 'mapPopup', {width : 500, height : 300});
		return false;
	},

	/**
	 * 두 날자 사이의 일수를 반환
	 * @param {String} fromDate 시작일자 (yyyy-mm-dd)
	 * @param {String} toDate 종료일자 (yyyy-mm-dd)
	 * @return {integer} 두 일자 사이의 일수
	 */
	intervalDate : function(fromDate, toDate){
       var FORMAT = "-";

       // FORMAT을 포함한 길이 체크
       if (fromDate.length != 10 || toDate.length != 10)
           return null;

       // FORMAT이 있는지 체크
       if (fromDate.indexOf(FORMAT) < 0 || toDate.indexOf(FORMAT) < 0)
           return null;

       // 년도, 월, 일로 분리
       var start_dt = fromDate.split(FORMAT);
       var end_dt = toDate.split(FORMAT);

       // 월 - 1(자바스크립트는 월이 0부터 시작하기 때문에...)
       // Number()를 이용하여 08, 09월을 10진수로 인식하게 함.
       start_dt[1] = (Number(start_dt[1]) - 1) + "";
       end_dt[1] = (Number(end_dt[1]) - 1) + "";

       var from_dt = new Date(start_dt[0], start_dt[1], start_dt[2]);
       var to_dt = new Date(end_dt[0], end_dt[1], end_dt[2]);

       return (to_dt.getTime() - from_dt.getTime()) / 1000 / 60 / 60 / 24;
   },

	/**
	 * Alert 다이얼로그 띄움.
	 * @param {String} title 타이틀
	 * @param {String} msg 내용
	 */
	alertDialog : function(title, msg, proc){
		var dialogTag = "<div id='jQueryDialog' title=\"" + title + "\">" + msg + "</div>";
		$(dialogTag).dialog({
			modal: true,
			buttons: {
				'확인': function(){
					if (proc != null) {
						eval(proc);
					}
					$(this).dialog('destroy').remove();
				}
			}
		});
	},

	/**
	 * Alert 다이얼로그 띄운 후 정해진 작업을 완료한 후 팝업창을 닫는다.
	 * @param {String} msg 내용
	 * @param {String} procArray 작업 목록 배열
	 */
	alertProcDialog : function(msg, procArray){
		var dialogTag = "<div id='jQueryDialog' title=\""+Common.DIALOG_TITLE.NOTICE+"\">" + msg + "</div>";
		$(dialogTag).dialog({
			modal: true,
			buttons: {
				'확인': function(){
					$(this).dialog('destroy').remove();
					for(var i=0; i<procArray.length; i++){
						eval(procArray[i]);
					}
				}
			}
		});
	},

	/**
	 * Alert 다이얼로그 띄운 후 취소, 확인 버튼을 출력 후 확인 버튼을 클릭할 경우에는 proc를 실행한다.
	 * @param {String} msg 내용
	 * @param {String} proc 작업
	 */
	choiceDialog : function(msg, proc){
		var dialogTag = "<div id='jQueryDialog' title=\""+Common.DIALOG_TITLE.CONFIRM+"\">" + msg + "</div>";
		$(dialogTag).dialog({
			modal: true,
			buttons: {
				'취소': function(){
					$(this).dialog('destroy').remove();
				},
				'확인': function(){
					eval(proc);
					$(this).dialog('destroy').remove();
				}
			}
		});
		return false;
	},

	/**
	 * 다이얼로그 닫기
	 */
	removeDialog : function() {
		$('#jQueryDialog').dialog('destroy').remove();
	},

	/**
	 * @param
	 * @param
	 */
	makeTelTxt : function(object){
		var mdn = object.val();
		var telText1 = mdn.substring(0,3);
		var telText2 = mdn.substring(3,7);
		var telText3 = mdn.substring(7,11);

		return telText1+"-"+ telText2+"-"+telText3;
	},

	checkObj : function checkObj(o,n) {
		if (o.val().length == 0) {
			o.addClass('ui-state-error');
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/> "+ n +" 입력해주세요.",o.val(''));
			return false;
		} else {
			return true;
		}

	},

	/**
	 * jQuery-UI datepicker 부착
	 * searchStartDate, searchEndDate 필요
	 */
	installDatePicker : function() {
		// datepicker regional['ko'] 속성 정의.
		$.datepicker.regional['ko'] = {
			closeText: '닫기',
			prevText: '이전달',
			nextText: '다음달',
			currentText: '오늘',
			monthNames: ['1월','2월','3월','4월','5월','6월', '7월','8월','9월','10월','11월','12월'],
			monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			dayNames: ['일','월','화','수','목','금','토'],
			dayNamesShort: ['일','월','화','수','목','금','토'],
			dayNamesMin: ['일','월','화','수','목','금','토'],
			dateFormat: 'yy-mm-dd',
			firstDay: 0,
			isRTL: false
		};

		// datepicker 설정.
		$.datepicker.setDefaults(
				$.extend(
					{
						showMonthAfterYear: false,
						showButtonPanel: true
					},
					$.datepicker.regional['ko']
				)
		);

		// 날자 검색 시작 일자 함수 및 달력 이미지 삽입
		$(function(){
			$("#searchStartDate").datepicker(
									   		{
										   		showOn: 'button',
									            showAnim: "show",
										   		buttonImage: '/images/cc/admin/ico_cal.gif',
										   		buttonImageOnly: true,
										   		buttonText: '시작일자',
										   		maxDate: new Date(),
												//minDate: "-1m",
												onSelect: function(dateText, inst){
													var tempArr = dateText.split("-");
													var chYear = parseInt(tempArr[0], 10);
													var chMonth = parseInt(tempArr[1], 10);
													var chDay = parseInt(tempArr[2], 10);

													var d = new Date();
													var month = parseInt(d.getMonth())+1;
													var today  = (d.getFullYear() + "-" + (month < 10 ? "0"+month : month) + "-" + (d.getDate() < 10 ? "0"+d.getDate() : d.getDate()));

													intervalDay = Common.intervalDate(dateText, today);

													if(intervalDay == 0){	// 당일
														// 년, 월, 일
														var minDate = new Date(parseInt(d.getFullYear()),parseInt(d.getMonth()),parseInt(d.getDate()));
														$("#searchEndDate").datepicker('option', 'minDate', minDate);
														var maxDate = new Date(parseInt(d.getFullYear()),parseInt(d.getMonth()),parseInt(d.getDate()));
														$("#searchEndDate").datepicker('option', 'maxDate', maxDate);
														$("#searchEndDate").val("");
													}else{
														// 년, 월, 일
														var minDate = new Date(chYear, chMonth-1, chDay);
														$("#searchEndDate").datepicker('option', 'minDate', minDate);

														//var maxDate = new Date(chYear, chMonth-1, chDay+6);
														var maxDate = new Date();
														month = parseInt(maxDate.getMonth())+1;
														var maxDay  = (maxDate.getFullYear() + "-" + (month < 10 ? "0"+month : month) + "-" + (maxDate.getDate() < 10 ? "0"+maxDate.getDate() : maxDate.getDate()));

														// 금일 보다 +7 일이 크면 금일이 최고 크기
														if(Common.intervalDate(maxDay, today) < 0){
															maxDate = new Date(parseInt(d.getFullYear(), 10),parseInt(d.getMonth(), 10),parseInt(d.getDate(), 10));
															$("#searchEndDate").datepicker('option', 'maxDate', maxDate);
															$("#searchEndDate").val("");
														}else{
															$("#searchEndDate").datepicker('option', 'maxDate', maxDate);
															$("#searchEndDate").val("");
														}
													}	// end of if
										   		}		// end of onSelect
									   		}
									   );
		});

		// 날자 검색 종료 일자 함수 및 달력 이미지 삽입
		$(function(){
			$("#searchEndDate").datepicker(
										{
									   		showOn: 'button',
								            showAnim: "show",
											buttonImage: '/images/cc/admin/ico_cal.gif',
											buttonImageOnly: true,
									   		buttonText: '종료일자',
									   		maxDate: new Date()
										}
									);
		});
	}
};


/**
 * 이미지 리사이징
 */
function Resizing(img, vWidth, vHeigh){
    if(vWidth != ""){
        img.width = vWidth;
    }

    if(vHeigh != "") {
        img.height= vHeigh;
    }
}

// 달력 팝업
//function openCal(inputName) {
//	//alert("cc");
//	Common.centerPopupWindow('/html/cc/calendar.html?inputName='+inputName, 'lottemartCalendar', {width:250,height:250,scrollbars:'no'});
//}
//기존 달력 팝업 버튼은 포커스 이동으로 달력이 나오도록 수정
function openCal(inputName) {

	try {
		// split
		var splitParam = inputName.split(".");
		param = splitParam[splitParam.length-1]
	    param = "#"+param;
	    if( $(param).is(":focus") == false) {
	    	$(param).focus(); //input box가 readonly일 경우 포커스를 잘 못찾아 가는 것 같아서 한번 더 실행하게 함.
	    	$(param).focus();
	    }
	}
	catch(e) {
	}
}
// 달력등 화면 로드 시 등록하시기 위해 추가
function openCals(param) {
	param = "#"+param;
	fn_datepicker(param);
}

/*
 * common.js 로드 시 class속성에 day 존재하는 케이스는 달력으로 로드 함
 * 달력수동입력 변경으로 인한 mask, placeholder 값 삭제. 2016-05-26
 * */
$(document).ready(function(){

	$(".day").each(function(i){
		id = $(".day").eq(i).attr("id");
		openCals(id);
		$("#"+id).inputmask({
			mask: "y-1-2",
			placeholder: "YYYY-MM-DD",
			leapday: "-02-29",
			separator: "-",
			alias: "yyyy/mm/dd"
		});
	});

});

function fn_datepicker(params){
   $(document).on('focus',params,function(){
        $(this).datepicker({
        dateFormat: 'yy-mm-dd',
        prevText: '이전 달',
        nextText: '다음 달',
        monthNames:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        monthNamesShort:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        dayNames: ['일','월','화','수','목','금','토'],
        dayNamesShort: ['일','월','화','수','목','금','토'],
        dayNamesMin: ['일','월','화','수','목','금','토'],
        showMonthAfterYear: true,
        yearSuffix: '년',
        changeMonth: true,
	    changeYear: true,
	    showButtonPanel: true,
        currentText: '오늘 날짜',
        closeText: '닫기'
      });
  });
}
/**
 *  콤보박스 항목 생성
 *  sel         select box object
 *  url         url
 *  params      parameters
 *  curr_value  초기 선택할 값
 **/
//function getCombo(sel, url, params, sync_flag, curr_value)
function getCombo(select, url, params)
{
	// ajax 호출 및 결과 획득
	// jQuery ajax 함수를 사용합니다.
	var obj;
	// ajax 호출
	$.ajax({
	      url: url,
	      data: params,
	      dataType: "json",  // json 외에 text, html 타입도 가능함.
	      success: function(data) {
	    	  getComboCallBack(select, data);
	      }
	});

}

// 콤보박스 항목 생성 ajax 호출 후 처리
function getComboCallBack(select, data) {
	if (select == null) {
		// alert('select 객체가 null입니다.');
		return;
	}
	if (data != null) {
		for (var i = 0; i < data.length; i++) {
			select.options.add(new Option(data[i].TEXT, data[i].VALUE));
		}
	}
}

// 날짜포맷터
// ex) 20110101 --> 2011-01-01
function formatDate(date) {
	var delim = '-';
	if (date == null) return date;

	if (date.length == 8) {
		return date.substring(0, 4) + delim + date.substring(4, 6) + delim + date.substring(6, 8);
	}
	else if (date.length == 6) {
		return date.substring(0, 4) + delim + date.substring(4, 6);
	}
	else {
		return date;
	}

	return date;

}

// 날짜포맷 제거
// ex) 2011-01-01 --> 20110101
function unformatDate(date) {
	if (date == null) return date;

	return date.replaceAll('-', '');
}

//시간포맷터
//ex) 120101 --> 12:01:01
function formatTime(time) {
	var delim = ':';
	if (time == null) return time;

	if (time.length == 6) {
		return time.substring(0, 2) + delim + time.substring(2, 4) + delim + time.substring(4, 6);
	}
	else if (time.length == 4) {
		return time.substring(0, 2) + delim + time.substring(2, 4);
	}
	else {
		return time;
	}

	return time;

}

//시간포맷 제거
//ex) 12:01:01 --> 120101
function unformatTime(time) {
	if (time == null) return time;

	return time.replaceAll('-', '');
}

//우편번호포맷터
// ex) 123456 --> 123-456
function formatZipCode(zipCode){
	if(zipCode == null)
		return zipCode;

	if(zipCode.length == 6){
		return zipCode.substring(0,3) + "-" + zipCode.substring(3,6);
	}
	else{
		return zipCode;
	}
}
//우편번호포맷 제거
function unformatZipCode(zipCode){
	if (zipCode == null) return zipCode;

	return zipCode.replaceAll('-', '');
}

// 오늘날짜조회 yyyy-mm-dd 형태
function getToday() {
	var date = new Date();

	var today = leadingZeros(date.getFullYear(), 4) + '-'
	          + leadingZeros(date.getMonth()+1, 2) + '-'
	          + leadingZeros(date.getDate(), 2);
	return today;
}

// 숫자앞에 지정된 자릿수에 맞도록 0을 채움
function leadingZeros(num, digits) {
	var zero = '';
	var str = num.toString();
	if (str.length < digits) {
		for (var i = 0; i < digits - str.length; i++) {
			zero += '0';
		}
	}
	return zero + str;
}

//3자리 마다 콤마(,) 찍어주는 기능
function formatComma(num)  {
	var num = num.toString();
	var rtn = "";
	var val = "";
	var j = 0;
	x = num.length;

	for(i=x; i>0; i--) {
		if(num.substring(i,i-1) != ",")
			val = num.substring(i, i-1) + val;
	}
	x = val.length;
	for(i=x; i>0; i--) {
		if(j%3 == 0 && j!=0)
			rtn = val.substring(i,i-1)+","+rtn;
		else
			rtn = val.substring(i,i-1)+rtn;

		j++;
	}

	return rtn;
}

// 콤마(,) 지우기
function unformatComma(num)  {
	if (num == null) return num;

	return num.replaceAll(',', '');
}

// nvl
function nvl(str, obj) {
	if (str == null || str == '') {
		return obj;
	}
	return str;
}

// 문자 --> 숫자변환
function getInt(str) {
	try {
		return parseInt(unformatComma(nvl(str, '0')));
	} catch (e) {
		return 0;
	}
}

/*
 * 공지사항 관련 함수
 */
function scrolling(objId,sec1,sec2,speed,height){
	this.objId=objId;
	this.sec1=sec1;
	this.sec2=sec2;
	this.speed=speed;
	this.height=height;
	this.h=0;
	this.div=document.getElementById(this.objId);
	this.htmltxt=this.div.innerHTML;
	this.div.innerHTML=this.htmltxt+this.htmltxt;
	this.div.isover=false;
	this.div.onmouseover=function(){this.isover=true;}
	this.div.onmouseout=function(){this.isover=false;}
	var self=this;
	this.div.scrollTop=0;
	window.setTimeout(function(){self.play()},this.sec1);
}
scrolling.prototype={
	play:function(){
		var self=this;
		if(!this.div.isover){
			this.div.scrollTop+=this.speed;
			if(this.div.scrollTop>this.div.scrollHeight/2){
				this.div.scrollTop=0;
			}else{
				this.h+=this.speed;
				if(this.h>=this.height){
					if(this.h>this.height|| this.div.scrollTop%this.height !=0){
						this.div.scrollTop-=this.h%this.height;
					}
					this.h=0;
					window.setTimeout(function(){self.play()},this.sec1);
					return;
				}
			}
		}
		window.setTimeout(function(){self.play()},this.sec2);
	}
}


//로딩바 보이기
function loadingMask(){
 var childWidth = 128;
 var childHeight = 128;
 var childTop = (document.body.clientHeight - childHeight) / 2;
 var childLeft = (document.body.clientWidth - childWidth) / 2;

 var loadingDiv = $('<span id="loadingLayer" style="position:absolute; left:'+childLeft+'px; top:'+childTop+'px; width:100%; height:100%; z-index:10001"><img id="ajax_load_type_img" src="/js/jquery/res/loading.gif"></span>').appendTo($("body"));
 loadingDiv.show();
 var loadingDivBg = $('<iframe id="loadingLayerBg" frameborder="0" style="filter:alpha(opacity=0);position:absolute; left:0px; top:0px; width:100%; height:100%; z-index:10000"><img id="ajax_load_type_img" src="/js/jquery/res/loading.gif"></iframe>').appendTo($("body"));
 loadingDivBg.show();
}

// 로딩바 감추기
function hideLoadingMask(){
 $('#loadingLayer').remove();
 $('#loadingLayerBg').remove();
}

/**
 * 현재년도부터 -5년까지의 콤보 생성
 *
 * @param obj
 * @param year
 */
function dp_makeYearCombo(obj, year) {
	if ( obj == null ) return;
    for( var i = year; i > year-5; i-- ) {
        $('<option></option>')
        .attr('value', i)
        .text(i)
        .appendTo(obj);
    }
}

//배열 값 중복제거
function unique(list) {
  var result = [];
  $.each(list, function(i, e) {
    if ($.inArray(e, result) == -1) result.push(e);
  });
  return result;
}

/**
 * 월 콤보 생성
 *
 * @param obj
 * @param month
 */
function dp_makeMonthCombo(obj, month) {
	if ( obj == null ) return;
	var strVal = "0";
    for( var i = 1; i <= 12; i++ ) {
    	if ( i < 10 ) strVal = "0" + i;
    	else strVal = "" + i;
    	if ( month == i ) {
	    	$('<option></option>', {
	    		value: strVal,
	    		text: i,
	    		selected: 'selected'
	    	}).appendTo(obj);
    	} else {
    		$('<option></option>', {
	    		value: strVal,
	    		text: i
	    	}).appendTo(obj);
    	}
    }
}

var ItemBox = {

		/*********************************
		* 01. 아이템가져오기
		**********************************/
		getItems : function(){
			var frm = document.delivItemForm;
			var items = new Array();
			var index = -1;

			//-------------------------
			// 상품개수 = 1
			if (frm.ordQty.length == undefined) {
				index++;
				items[index] = new Array();
				items[index] = {
						 deliTypeCd  	: frm.itemDeliTypeCd.value
						,strVendorId 	: frm.strVendorId.value
						,deliveryId  	: frm.deliveryId.value
						,orderItemSeq  	: frm.orderItemSeq.value
						,changeQty  	: frm.changeQty.value
						,reasonCd  		: frm.reasonCd.value
						,reDeliStatusCd : frm.reDeliStatusCd.value
						,itemSaleStsCd	: frm.saleStsCd.value
						,deliStatusCd	: frm.deliStatusCd.value
						,strCd			: frm.itemStrCd.value
				};
			}
			//--------------------------
			// 상품개수 > 1
			else{
				for ( var i=0 ; i<frm.ordItemDivnCd.length ; i++) {

					index++;
					items[index] = new Array();
					items[index] = {
							 deliTypeCd  	: frm.itemDeliTypeCd[i].value
							,strVendorId 	: frm.strVendorId[i].value
							,deliveryId  	: frm.deliveryId[i].value
							,orderItemSeq  	: frm.orderItemSeq[i].value
							,changeQty  	: frm.changeQty[i].value
							,reasonCd  		: frm.reasonCd[i].value
							,reDeliStatusCd : frm.reDeliStatusCd[i].value
							,itemSaleStsCd	: frm.saleStsCd[i].value
							,deliStatusCd	: frm.deliStatusCd[i].value
							,strCd			: frm.itemStrCd[i].value
					};
				}
			}
			if(index == -1 ) {
				alert("체크한 상품이 없습니다.");
			}
			return items;

		}
		/*********************************
		* 02. 아이템가져오기(체크한 것만)
		* 	@ex	var items = ItemBox.getCheckedItems();
		**********************************/
		, getCheckedItems : function(alertMode){
			var frm = document.delivItemForm;
			var items = new Array();
			var index = -1;

			//-------------------------
			// 상품개수 = 1
			if (frm.ordQty.length == undefined) {
				// 체크한 상품만 실행
 				if ( frm.ordItemDivnCd.value == "00" && frm.prodFlag.checked == true ) {
// 				if (frm.prodFlag.checked == true ) {
//					alert("frm.itemDeliTypeCd.value:"+frm.itemDeliTypeCd.value);
					index++;
					items[index] = new Array();
					items[index] = {
							 deliTypeCd  	: frm.itemDeliTypeCd.value
							,strVendorId 	: frm.strVendorId.value
							,deliveryId  	: frm.deliveryId.value
							,orderItemSeq  	: frm.orderItemSeq.value
							,changeQty  	: frm.changeQty.value
							,reasonCd  		: frm.reasonCd.value
							,reDeliStatusCd : frm.reDeliStatusCd.value
							,itemSaleStsCd	: frm.saleStsCd.value
							,deliStatusCd	: frm.deliStatusCd.value
							,strCd			: frm.itemStrCd.value
					};
				}
			}
			//--------------------------
			// 상품개수 > 1
			else{
				for ( var i=0 ; i<frm.ordItemDivnCd.length ; i++) {

					//test
//					alert("common.js "+i+":"+frm.orderItemSeq[i].value);
//					alert("ordItemDivnCd:"+frm.ordItemDivnCd[i].value);
//					alert("checked:"+frm.prodFlag[i].checked);

					// 체크한 상품만 실행
					if ( frm.ordItemDivnCd[i].value == "00" && frm.prodFlag[i].checked == true ) {
// 					if (frm.prodFlag[i].checked == true ) {
// 						alert("frm.itemDeliTypeCd["+i+"].value:"+frm.itemDeliTypeCd[i].value);
						index++;
						items[index] = new Array();
						items[index] = {
								 deliTypeCd  	: frm.itemDeliTypeCd[i].value
								,strVendorId 	: frm.strVendorId[i].value
								,deliveryId  	: frm.deliveryId[i].value
								,orderItemSeq  	: frm.orderItemSeq[i].value
								,changeQty  	: frm.changeQty[i].value
								,reasonCd  		: frm.reasonCd[i].value
								,reDeliStatusCd : frm.reDeliStatusCd[i].value
								,itemSaleStsCd	: frm.saleStsCd[i].value
								,deliStatusCd	: frm.deliStatusCd[i].value
								,strCd			: frm.itemStrCd[i].value
						};
					}
				}
			}
			if(index == -1 && alertMode != "OFF") {
				alert("체크한 상품이 없습니다.1");
			}
			return items;
		}
		/*********************************************************************************
		* 03. items 를 위한 indexOf 함수
		*    @param items, key, value
		*    @return 지정한 key 와 value가 일치하는 items의 첫번째 index 를 리턴
		*                                    일치하는 것이 없으면 -1 을 리턴
        *    @ex     items : [0]  bsketTypeCd: 10 deliTypeCd:12
        *                    [1]  bsketTypeCd: 11 deliTypeCd:01
        *                    [2]  bsketTypeCd: 21 deliTypeCd:05
        *            var result = ItemBox(items, "deliTypeCd", "05");
        *            result is 2
		**********************************************************************************/
		, indexOf : function(items, key, value){
			var length = items.length;
			var i;
			//--------------------------------------------
			// key == "deliTypeCd"
			if(key == "deliTypeCd"){
				for(i=0; i<length; i++){
					if(items[i].deliTypeCd == value){
						return i;
					}
				}
			}
			//--------------------------------------------
			// key == "strVendorId"
			if(key == "strVendorId"){
				for(i=0; i<length; i++){
					if(items[i].strVendorId == value){
						return i;
					}
				}
			}
			//--------------------------------------------
			// key == "deliveryId"
			if(key == "deliveryId"){
				for(i=0; i<length; i++){
					if(items[i].deliveryId == value){
						return i;
					}
				}
			}
			//--------------------------------------------
			// key == "itemSaleStsCd"
			if(key == "itemSaleStsCd"){
				for(i=0; i<length; i++){
					if(items[i].itemSaleStsCd == value){
						return i;
					}
				}
			}
			//--------------------------------------------
			// key == "deliStatusCd"
			if(key == "deliStatusCd"){
				for(i=0; i<length; i++){
					if(items[i].deliStatusCd == value){
						return i;
					}
				}
			}
			//--------------------------------------------
			// key == "strCd"
			if(key == "strCd"){
				for(i=0; i<length; i++){
					if(items[i].strCd == value){
						return i;
					}
				}
			}
			return -1;
		}
		/*********************************************************************************
		 * 04. items 생성기
		 * 		key 한개짜리 items 자료구조를 만든다.
		 * 	@param data, key
		 * 	@return items
		 *  @ex		ItemBox.genItems(subFrm.itemDeliTypeCd, "deliTypeCd");
		 */
		, genItems : function(data, key){

			var length = data.length;
			var items = new Array();

			//--------------------------------------------
			// key == "deliTypeCd"
			if(key == "deliTypeCd"){
				if (length == undefined) {
					items[0] = new Array();
					items[0] = {
							deliTypeCd : data.value
					}
				}
				else{
					for (var i = 0; i < length; i++) {
						items[i] = new Array();
						items[i] = {
								deliTypeCd : data[i].value
						};
					}
				}
			}

			return items;
		}
		/*********************************************************************************
		 * 05. items show
		 * 		key 값에 해당하는 value 들을 alert 으로 출력한다.
		 * 	@param data, key
		 * 	@return
		 *  @ex		ItemBox.show(items, "deliTypeCd");
		 */
		, show : function(items, key){
			var length = items.length;
			var i;
			if(length == 0){
				alert("is empty");
				return;
			}

			//--------------------------------------------
			// key == "deliTypeCd"
			if(key == "deliTypeCd"){
				for(i=0; i<length; i++){
					alert(items[i].deliTypeCd);
				}
			}
			//--------------------------------------------
			// key == "strVendorId"
			if(key == "strVendorId"){
				for(i=0; i<length; i++){
					alert(items[i].strVendorId);
				}
			}
			//--------------------------------------------
			// key == "deliveryId"
			if(key == "deliveryId"){
				for(i=0; i<length; i++){
					alert(items[i].deliveryId);
				}
			}
			//--------------------------------------------
			// key == "orderItemSeq"
			if(key == "orderItemSeq"){
				for(i=0; i<length; i++){
					alert(items[i].orderItemSeq);
				}
			}
			//--------------------------------------------
			// key == "changeQty"
			if(key == "changeQty"){
				for(i=0; i<length; i++){
					alert(items[i].changeQty);
				}
			}
			//--------------------------------------------
			// key == "reasonCd"
			if(key == "reasonCd"){
				for(i=0; i<length; i++){
					alert(items[i].reasonCd);
				}
			}
			//--------------------------------------------
			// key == "reDeliStatusCd"
			if(key == "reDeliStatusCd"){
				for(i=0; i<length; i++){
					alert(items[i].reDeliStatusCd);
				}
			}
			//--------------------------------------------
			// key == "itemSaleStsCd"
			if(key == "itemSaleStsCd"){
				for(i=0; i<length; i++){
					alert(items[i].itemSaleStsCd);
				}
			}
			//--------------------------------------------
			// key == "deliStatusCd"
			if(key == "deliStatusCd"){
				for(i=0; i<length; i++){
					alert(items[i].deliStatusCd);
				}
			}
			//--------------------------------------------
			// key == "strCd"
			if(key == "strCd"){
				for(i=0; i<length; i++){
					alert(items[i].strCd);
				}
			}
		}

		/*********************************************************************************
		 * 06. isUnique
		 * 		key 값에 해당하는 value 둘이 중복을 제거 하고 한 가지만 있는지 체크
		 * 	@param data, key
		 * 	@return boolean
		 *  @ex		ItemBox.isUnique(items, "deliveryId");
		 */
		, isUnique : function(items, key){
			var length = items.length;
			var i;
			if(length == 0){
				alert("is empty");
				return false;
			}

			var list = [];

			//--------------------------------------------
			// key == "deliveryId"
			if(key == "deliveryId"){
				for(i=0; i<length; i++){
					list.push(items[i].deliveryId);
				}
			}
			//--------------------------------------------
			// key == "deliStatusCd"
			if(key == "deliStatusCd"){
				for(i=0; i<length; i++){
					list.push(items[i].deliStatusCd);
				}
			}
			//--------------------------------------------
			// key == "strCd"
			if(key == "strCd"){
				for(i=0; i<length; i++){
					list.push(items[i].strCd);
				}
			}

			//--------------------------------------------
			// return
			if(list.length == 0){
				alert("Invalid key or value is empty");
				return false;
			}

			list = unique(list);
			if(list.length == 1 ){
				return true;
			}

			return false;
		}
		, isCenter : function(items){
			var length = items.length;
			var i;
			if(length == 0){
				alert("is empty");
				return;
			}

			for(i=0; i<length; i++){
				if(checkCenter(items[i].strCd)){
					return true;
				}
			}
			return false;
		}
};

var ChkJob = {


		returnReceipt : function(handleType, deliStatusCd, saleStsCd, isCenter){

//			alert("handleType:"+handleType + " deliStatusCd:"+ deliStatusCd + " saleStsCd:"+saleStsCd+" isCenter:"+isCenter);

			//-----------------------------------
			// 처리유형: 반품접수
			// 배송상태: 상차완료, 상차완료확인, 매장보관중(당일), 배송완료, 패킹완료&매출완료
			//-----------------------------------
			if(handleType == "3"){
				if(deliStatusCd == "43" || deliStatusCd == "45" || deliStatusCd == "46" || deliStatusCd == "51" ||  (deliStatusCd == "36" && saleStsCd =="01")){

					return true;
				}
				else if(deliStatusCd == "52"){
					alert("배송실패는 회수없는 반품접수를 이용해주세요.");
					return false;
				}
			}

			//-----------------------------------
			// 처리유형: 회수없는 반품접수
			// 배송상태: 상차완료, 상차완료확인, 매장보관중(당일), 배송완료, 배송실패, 패킹완료&매출완료
			//-----------------------------------
			else if(handleType == "12"){
//				20160928 김윤정 요청
				if(!isCenter && deliStatusCd == "52"){
					// 20170125 전용센터 아닌 경우 일반 점포는 배송실패 시에만 처리 가능 하도록 수정.
					return true;
				}
				// 전용센터, 일반점포 근거리배송 , 픽업시에 회수없는 반품 가능하도록 수정
				/*else if(!isCenter){
					alert("전용센터 상품이 아닐 경우 회수없는 반품접수를 할수 없습니다.");
					return false;
				}*/

				if(deliStatusCd == "43" || deliStatusCd == "45" || deliStatusCd == "46" || deliStatusCd == "51" || deliStatusCd == "52" || (deliStatusCd == "36" && saleStsCd =="01")){
					return true;
				}
				else{
					alert("배송상태가 상차완료/배송완료/배송실패이 아닐 경우 회수없는 반품접수를 할수 없습니다.");
					return false;
				}
			}
			//-----------------------------------
			// 처리유형: 결품발생 반품접수
			// 배송상태: 결품발생
			//-----------------------------------
			else if(handleType == "13"){

				var frm = document.delivItemForm;
//				alert("redeliExists:"+frm.redeliExists.value);
				//재배송이고 결품발생이면..
				if(deliStatusCd == "33" && saleStsCd == "01"){
					return true;
				}
				else{
					alert("재배송결품이 아닐 경우 결품발생 반품접수를 할수 없습니다.");
					return false;
				}
			}
			else{
				alert("배송상태가 상차완료 또는 배송완료가 아닐경우 반품접수를 할수 없습니다.");
				return false;
			}
		}

/*
		,deliStatus : function(handleType, job, deliStatusCd){

//			alert("handleType:"+handleType + " job:"+ job + " deliStatusCd:"+deliStatusCd);

			//-----------------------------------
			//처리유형: 반품접수 / Job:반품접수
			//		상차완료, 배송완료
			//-----------------------------------
			if(handleType == "3" && job == "returnReceipt"){
				if(deliStatusCd == "43" || deliStatusCd == "51"){
					return true;
				}
				else{
					return false;
				}
			}

			//-----------------------------------
			//처리유형: 미회수 반품접수 / Job:반품접수
			//		상차완료, 배송완료, 결품발생, 배송실패
			//-----------------------------------
			if(handleType == "12" && job == "returnReceipt"){
				if(deliStatusCd == "43" || deliStatusCd == "51" || deliStatusCd == "33" || deliStatusCd == "52"){
					return true;
				}
				else{
					return false;
				}
			}

			return true;
		}
*/
};

function isAdmin(loginId){

	var isAdmin = false;

	switch (loginId) {
		case "ccagent999" 	:  isAdmin = true; break; //시스템관리자
		case "ccagent273" 	:  isAdmin = true; break; //송화영
		case "ccagent414" 	:  isAdmin = true; break; //문창호
		case "ccagent252" 	:  isAdmin = true; break; //이혜임
		case "online" 		:  isAdmin = true; break; //개발용
		default : isAdmin = false;
	}
	if(isAdmin){
		alert("관리자권한입니다. 사용에 신중해주세요.");
		return true;
	}

	return false;
}

function isSuperAdmin(loginId){

	var isAdmin = false;

	switch (loginId) {
		case "ccagent999" 	:  isAdmin = true; break; //시스템관리자
		case "online" 		:  isAdmin = true; break; //개발용
		default : isAdmin = false;
	}
	if(isAdmin){
		alert("관리자권한입니다. 사용에 신중해주세요.");
		return true;
	}

	return false;
}

//라이센스 설정
/*
var ibleaders;
ibleaders = ibleaders || {};
ibleaders = {
    license: "W2FtSztPKCNzZTYxaDJxbiwPcEg7RSl2ODBgZShnOmA9NRF4A3MVaWl2OXY6LTtyfytjHGkTYwN3ZXk+bDFmaT4hIzcIYA8kE2o3NmQwZCB0"
};
*/

var domain = window.location.hostname;
var pref = domain.split('.')[0];

//운영
if (pref == 'limadmin'){
	lKey = 'W2FtSztPKC5wbzcxYjJxbn9NNREsCHUne20iIGk5LSN3dF96FndSOnx2d2wtLH4rPDdyV3kAZwJweCM+bXpwI39vISIMJ1wuRStveXtpOnNoL2JgKxx9FXtJejtpPGEmfWU8bSt5Um8JIgEsamg/dGkudWMqZDYedEd7Emo4YCN6ZD16KHgqJAItDzRMN3old3M1N2IsdyBhD3VIKAhvOjkibGczZC1iJzQYPEUpSSd3cyw+e3c8bX5qJEgjQTNBJjhlfzVmM2k+IXk3FHRcPlVtMjR6N2A+dW8iL3JLOE47H34haC0qdiU9fnExbUsvU3AaNGEqfy1tLm9+aCt4Wy4ae082ezd2JSNlZzZtL2pAJUh7VjVnOHVsKDFlLHMjfw9gWHYUe2k8MmVnKGIyfyAvBThGKFIvbChreH4xZz9maT9LOFw2XjojbXt1PH5pOyFhcFI/Qjpdaik1ZSt6KmwveH1pSDpPIxR6ZzV/NXY/I2pocCsENEtkHzV+MmE5dW8pMnYubVAxXz0AMyk1ciA9YGc3cA==';
	//https http공용 라이센스로 변경.
	//lKey = 'W2FtSztPKC5zbTc0YTJxbn9NNREsCHUne20iIGk5LSN3dF96FndSOnwsd3A5f3s7azV+GiMGdA54aSMlbjgsNHR1JzEdeFw7VS9zcTx4en0pOD51NBhkBGBROzkuP2wqdG09K2hsVyERKlpuLCp6LSo1ZCwzcyMHfl8hE297ait6aTA4bi4vf1AhVHMRensjcHM1N34xdyB+FGhDLVM9NmJ8PigpfzlkIDQYPFk0SSdoaDE1fixuYSUseQUiQDdBJjh5YjVmLHIjKnxsRngHYAcjKC9uNmQ+dW8+MnJLJ1UmFHt6OiFxM3hwf3M1bUsvT20aNH4xYiZodT1yM3UqFjQBb00yezd2OT5lZyl2MmFFfhp3DXI8am1tNy1jNm0hZBJ0RjBNKGkseCIgbikseygqGiZEM1Mmcm4tMGMyez5rKGUNZQ4pWSQ9eWMvZjdoISp8bEZ4B2AHIygvbjJkIWhpPjNzVzlQOwpuJXQlbTs/OWFzKnFWKVRwHjd/KmpnMy5vfmgreF8qGntOLWcxbCc8fno9MnQkAi0Qe0g0YCZ0bDYx';
}else{
	//2024-05-09 개발 서버 라이센스 키 발급 - 1년마다 갱신하여야 함(업체 : 아이비리더스)
	lKey = 'W2FtSztPKCNybDc2YjJxbn9NNREsCHUne20iIGk5LTV8c0ZsFmoUeWlzOS4layg+e2J8Fm5cch10YTIldTtuaHV1Oi4YaR59A3AoP35wPWxpNn9iaA17AHlAICFvPSt1dGI5L2AiFXINcQE8enE4a2hwPW8oYmweaUo4GXchay45PzU7bi9kMQc4TChWPH5iKiEpMmEzbSFkDmlGMFIzdCcneW4sIWstPTAROUY2UyZycjAwYy1gI2B3PkMnBm4TOj1nYC9nNmgiL2FtSDpCO0BlLXE8eHo6fGogMGhKPU8nEWZ7NGM0aD82ejZsP1d2Dj0AciJkPXIzLmhuaXUwD3VfJx1zeit4PXk5KShoOj0FMg5zC2c5czYpaW9+djF3PlQoE3JTKHw8fD4oKThsMjxuWWkaYglhKTAtYCNmOmUjOWFWIkgsG31weHs7OW0lP28hIhFtB2AAMylhJmE7ZDQ6YC5pQyMVfFl7OG8yKzNibT0ncDcXd086QG89YTF6LjYoLX0rNgJnBH9GbjpnbGN/MDhiNnQ/Bn9EaRBrMHcvNGosfzhpZjhcdUUxTil0ODpjejZkLH4oMF9gCyhXL3FvMSpjLWYkf2ggViJILBt9cHhiMmUsdD8pZm1MOVw7VXVzbSgrfyp1aCEvdFc7VDoVZXovay8zeHBldDc/QStUbho0YDc=';
	//작년꺼
	//lKey = 'W2FtSztPKCZxbTc3YjJxbn9NNREsCHUne20iIGk5LTV8c0ZsFmoUeWlzOS4layg+e2J8Fm5cch10YTIldTtuaHV1Oi4YaR59A3AoP35wPWxpNn9iaA17AHlAICFvPSt1dGI5L2AiFXINcQE8enE4a2hwPW8oYmweaUo4GXchay45PzU7bi9kMQc4TChWPH5iKiEpMmEzbSFkDmlGMFIzdCcneW4sIWstPTAROUY2UyZycjAwYy1gI2B3PkMnBm4TOj1nYC9nNmgiL2FtSDpCO0BlLXE8eHo6fGogMGhKPU8nEWZ7NGM0aD82ejZsP1d2Dj0AciJkPXIzLmhuaXUwD3VfJx1zeit4PXk5KShoOj0FMg5zC2c5czYpaW9+djF3PlQoE3JTKHw8fD4oKThsMjxuWWkaYglhKTAtYCNmOmUjOWFWIkgsG31weHs7OW0lP28hIhFtB2AAMylhJmE7ZDQ6YC5pQyMVfFl7OG8yKzNibT0ncDcXd086QG89YTF6LjYoLX0rNgJnBH9GbjpnbGN/MDhiNnQ/Bn9EaRBrMHcvNGosfzhpZjhcdUUxTil0ODpjejZkLH4oMF9gCyhXL3FvMSpjLWYkf2ggViJILBt9cHhiMmUsdD8pZm1MOVw7VXVzbSgrfyp1aCEvdFc7VDoVZXovay8zeHBldDc/TCtUbh00YDc=';
}
var ibleaders;
ibleaders = ibleaders || {};
ibleaders = {
	license : lKey
};

// 테마, 헤더높이 설정
var Ibs = {	// Theme Setting
	ThemeCode : 'BL', // 'GM3',
	ThemeName : 'Blue', // 'Main3'
	HeaderHeight : 20
};

var RETURN_IBS_OBJ; // Global Variable
//IBSheet Data Display
function loadIBSheetData(sheetName, targetUrl, curPage, formId, param) {

	// loading bar start...
	loadingMask();

	var rowsPerPage = $("#rowsPerPage").val();

	if (formId) {
		param = $('*', formId).fieldSerialize();
		param += "&currentPage=" + curPage;
	} else {
		param.currentPage = curPage;
	}

	$.ajax({
		type: 'POST',
		url: targetUrl,
		data: param,
		success: function(data) {
			if (data.result) {
				RETURN_IBS_OBJ = data;
				sheetName.LoadSearchData(data.ibsList);
				var len = (data.ibsList) ? eval(data.ibsList).length : 0;
				if (len == 0) {
					$('#resultMsg').text('해당 자료가 없습니다.');
				} else {
					$('#resultMsg').text('정상적으로 조회되었습니다.');
				}

				if (rowsPerPage) {
					sheetName.SetPageCount(rowsPerPage);
				}

				if (curPage && data.pageIndex) {
					setLMPaging(data.totalCount, rowsPerPage, data.pageIndex, 'goPage', 'pagingDiv');
				}

			} else {
				alert(data.errMsg);
				$('#resultMsg').text('조회 중 에러가 발생했습니다.');
			}
			// loading bar end.
			hideLoadingMask();
		},
		error: function(e) {
			// loading bar end.
			$('#resultMsg').text('요청처리를 실패했습니다.');
			hideLoadingMask();
			alert(e);
		}
	});
}

function loadIBSheetDataCnt(sheetName, targetUrl, curPage, formId, param) {

	// loading bar start...
	loadingMask();

	var rowsPerPage = $("#rowsPerPage").val();

	if (formId) {
		param = $('*', formId).fieldSerialize();
		param += "&currentPage=" + curPage;
	} else {
		param.currentPage = curPage;
	}

	$.ajax({
		type: 'POST',
		url: targetUrl,
		data: param,
		success: function(data) {
			if (data.result) {
				RETURN_IBS_OBJ = data;
				sheetName.LoadSearchData(data.ibsList);
				var len = (data.ibsList) ? eval(data.ibsList).length : 0;
				if (len == 0) {
					$('#resultMsg').text('해당 자료가 없습니다.');
				} else {
					$('#resultMsg').text('정상적으로 조회되었습니다.');
				}

				if (rowsPerPage) {
					sheetName.SetPageCount(rowsPerPage);
				}

				if (curPage && data.pageIndex) {
					setLMPaging(data.totalCount, rowsPerPage, data.pageIndex, 'goPage', 'pagingDiv');
				}

				$('li.totalCnt').html("<b>[조회건수  <font color='red'>"+ len + "</font> 건]</b>");
			} else {
				alert(data.errMsg);
				$('#resultMsg').text('조회 중 에러가 발생했습니다.');
			}
			// loading bar end.
			hideLoadingMask();

		},
		error: function(e) {
			// loading bar end.
			$('#resultMsg').text('요청처리를 실패했습니다.');
			hideLoadingMask();
			alert(e);
		}
	});
}

// IBSheet 그리드데이터 엑셀 데이터 다운로드
function excelDataDown(mySheet, fileNm, hideCol) {
	mySheet.SetWaitImageVisible(1); // 처리중 로딩바가 보이게 한다.

	var downCols = "";
	if (hideCol) downCols  = makeHiddenSkipCol(mySheet, hideCol);

	mySheet.Down2Excel({'FileName':fileNm, Merge:1, Multipart:0, HiddenColumn:true, DownCols:downCols});
//	mySheet.Down2Excel({'FileName':fileNm, Merge:1, Multipart:0, HiddenColumn:true});
	mySheet.SetWaitImageVisible(0); // 처리중 로딩바가 안보이게 한다.
}

// IBSheet 엑셀양식 다운로드
function excelFormDown(mySheet, fileName, headerRows, hideCol) {
	var hdRow = '0';
	for (var i=1; i<headerRows; i++) {
		hdRow += '|' + i;
	}

	var downCols = "";
	if (hideCol) downCols  = makeHiddenSkipCol(mySheet, hideCol);
	mySheet.SetWaitImageVisible(1); // 처리중 로딩바가 보이게 한다.
	mySheet.Down2Excel({"FileName":fileName, Merge:1, Multipart:0, DownRows:hdRow, DownCols:downCols});
	mySheet.SetWaitImageVisible(0); // 처리중 로딩바가 안보이게 한다.
}

// 감춰진 컬럼이나, Seq,Status,DelCheck 타입의 컬럼은 제외
function makeHiddenSkipCol(sObj, hideCol) {
	var hCols = "";
	if (hideCol) hCols = hideCol.split("|");
	var lc = sObj.LastCol();
	var colsArr = new Array();
	for (var i=0; i<=lc; i++) {
		var colType = sObj.GetCellProperty(0, i, "Type");
		var colName = sObj.GetCellProperty(0, i, "SaveName"); // SaveName
		// Hidden이 아니면 넣는다.
		if (0 == sObj.GetColHidden(i) && colType != "Seq" && colType != "Status" && colType != "DelCheck") {
			if (hideCol && hCols.indexOf(colName) < 0) {
				colsArr.push(i);
			}
		}
	}
	return colsArr.join("|");
}

//감춰진 컬럼이나, Seq,Status,DelCheck 타입의 컬럼은 제외
function makeHiddenSkipShowAddCol(sObj, hideCol, showCol) {

	var hCols = "";
	if (hideCol) hCols = hideCol.split("|");
	if (showCol) sCols = showCol.split("|");
	var lc = sObj.LastCol();
	var colsArr = new Array();
	for (var i=0; i<=lc; i++) {
		var colType = sObj.GetCellProperty(0, i, "Type");
		var colName = sObj.GetCellProperty(0, i, "SaveName"); // SaveName
		// Hidden이 아니면 넣는다.
		if (0 == sObj.GetColHidden(i) && colType != "Seq" && colType != "Status" && colType != "DelCheck") {
			if (hideCol && hCols.indexOf(colName) < 0) {
				colsArr.push(i);
			}
		}
		// Hidden이여도 포함해주어야 할 칼럼은 넣는다.
		if (1 == sObj.GetColHidden(i) && colType != "Seq" && colType != "Status" && colType != "DelCheck") {
			if (showCol && sCols.indexOf(colName) == 0) {
				colsArr.push(i);
			}
		}
	}
	return colsArr.join("|");
}

// 페이징 없이 서버에서 전체데이터 엑셀 다운로드(검색조건 적용)
function directExcelDown(mySheet, fileName, url, formId, param, hideCol) {

	var mParam = new Object();
	mParam.Multipart = 0;
	mParam.Merge 	 = 1;
	mParam.URL 		 = url;
	mParam.FileName  = fileName;
	mParam.DownCols  = makeHiddenSkipCol(mySheet, hideCol);

	if (formId) { // form id가 들어올 때..
		var epram = $('*', formId).fieldSerialize();
		epram = epram.replace('rowsPerPage', 'oldRowsPerPage');
		epram = epram.replace('currentPage', 'oldCurrentPage'); // 안전장치
		epram += '&currentPage=1&rowsPerPage=65000';
		mParam.ExtendParam = epram;
	} else { // param이 들어올 때..
		param.currentPage = 1;
		param.rowsPerPage = 65000;
		mParam.ExtendParam = $.param(param);
	}

	mySheet.SetWaitImageVisible(1); // 처리중 로딩바가 보이게 한다.
	mySheet.DirectDown2Excel(mParam);
	mySheet.SetWaitImageVisible(0); // 처리중 로딩바가 안보이게 한다.
}

//페이징 없이 서버에서 전체데이터 엑셀 다운로드(검색조건 적용)
function directExcelDown2(mySheet, fileName, url, formId, param, hideCol, showCol) {

	var mParam = new Object();
	mParam.Multipart = 0;
	mParam.Merge 	 = 1;
	mParam.URL 		 = url;
	mParam.FileName  = fileName;
	mParam.DownCols  = makeHiddenSkipShowAddCol(mySheet, hideCol, showCol);

	if (formId) { // form id가 들어올 때..
		var epram = $('*', formId).fieldSerialize();
		epram = epram.replace('rowsPerPage', 'oldRowsPerPage');
		epram = epram.replace('currentPage', 'oldCurrentPage'); // 안전장치
		epram += '&currentPage=1&rowsPerPage=65000';
		mParam.ExtendParam = epram;
	} else { // param이 들어올 때..
		param.currentPage = 1;
		param.rowsPerPage = 65000;
		mParam.ExtendParam = $.param(param);
	}

	mySheet.SetWaitImageVisible(1); // 처리중 로딩바가 보이게 한다.
	mySheet.DirectDown2Excel(mParam);
	mySheet.SetWaitImageVisible(0); // 처리중 로딩바가 안보이게 한다.
}

/*
 * Method Name : 검색조건 dateformat 유형2
 * 달력수동입력 변경으로 인한 함수추가.
 * obj, obj2 : 날짜비교대상
 * gubun : START, END
 * */
function checkDate2(obj, obj2, gubun){
	var checkDataStr = document.getElementById(obj).value.replaceAll("-","");
	var startDt = "";
	var endDt = "";

	if(!isNaN(checkDataStr)){
		if(gubun == "START"){
			startDt = checkDataStr;
			endDt = document.getElementById(obj2).value.replaceAll("-","");
		}
		else{
			startDt = document.getElementById(obj2).value.replaceAll("-","");
			endDt = checkDataStr;
		}

		if(startDt.length == 8 && endDt.length == 8){
			if( startDt > endDt ) alert("종료일자가 시작일자 보다 이전입니다. 확인 해 주세요.");
		}
	}

}

/**
 * 아이디형태의 값검증
 */
function checkIdType(in_str){
	var pattern = new RegExp('[^ㄱ-ㅎ가-힣a-zA-Z0-9]', 'i');
	if (pattern.exec(in_str) != null) {
		//alert("한글, 영문, 숫자만 가능합니다.");
		return false;
	}else{
		return true;
	}
}

/**
 * 한글만 가능
 */
function checkHangulType(in_str){
	var pattern = new RegExp('[^ㄱ-ㅎ가-힣]', 'i');
	if (pattern.exec(in_str) != null) {
		//alert("한글 가능합니다.");
		return false;
	}else{
		return true;
	}
}

/**
 * 특수문자 존재여부
 */
function checkSpecialType(in_str) {
	var m_Sp = /[$\\@\\\#%\^\&\*\(\)\[\]\+\_\{\}\'\~\=\|\<\>]/;
	var m_val  = in_str;

	if(m_val.length == 0)
		return true;

	var strLen = m_val.length;
	var m_char = m_val.charAt((strLen) - 1);
	if(m_char.search(m_Sp) != -1)
		return false;

}

/**
 * 문자열의 Byte 수를 리턴하는 함수
 */
function checkMaxLength(str) {

	if (str == null || str.length == 0) {
      return 0;
    }
    var size = 0;

    for (var i = 0; i < str.length; i++) {
      size += charByteSize(str.charAt(i));
    }
    return size;
}

function charByteSize(ch) {
    if (ch == null || ch.length == 0) {
      return 0;
    }

    var charCode = ch.charCodeAt(0);

    if (charCode <= 0x00007F) {
      return 1;
    } else if (charCode <= 0x0007FF) {
      return 2;
    } else if (charCode <= 0x00FFFF) {
      return 3;
    } else {
      return 4;
    }
}

//김연민 - 사용안함
//function phone_format(num){
//	return num.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
//}


var isNN = (navigator.appName.indexOf("Netscape")!=-1);
function autoTab(input,len, e){
	var keyCode = (isNN) ? e.which : e.keyCode;
	var filter = (isNN) ? [0,8,9] : [0,8,9,16,17,18,37,38,39,40,46];

	if(input.value.length >= len && !containsElement(filter,keyCode)) {
		input.value = input.value.slice(0, len);
		input.form[(getIndex(input)+1) % input.form.length].focus();
	}

	function containsElement(arr, ele) {
		var found = false, index = 0;
		while(!found && index < arr.length)
			if(arr[index] == ele)
				found = true;
			else
				index++;
		return found;
	}

	function getIndex(input) {
		var index = -1, i = 0, found = false;
		while (i < input.form.length && index == -1)
			if (input.form[i] == input)index = i;
			else i++;
		return index;
	}
	return true;
}

function num_only(fl){

 t = fl.value ;

	for(i=0;i<t.length;i++)
		if (t.charAt(i)<'0' || t.charAt(i)>'9') {
			alert("숫자만 입력해주세요.") ;
			 fl.value="";
			 fl.focus() ;
			 return false ;
		}
}

String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 숫자, - 만 가능
 */
function checkPhoneNumberType(in_str){
	var pattern = new RegExp('[^0-9\-]', 'i');
	if (pattern.exec(in_str) != null) {
		return false;
	}else{
		return true;
	}
}

//마우스 드래그, 우클릭 방지
/*
$(document).ready(function(){
$(document).bind("contextmenu", function(e) {
return false;
});
});
$(document).bind('selectstart',function() {return false;});
$(document).bind('dragstart',function(){return false;});
*/