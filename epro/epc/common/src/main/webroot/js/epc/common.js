
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
 * 원하는 bytes만큼 substring 
 */
String.prototype.substrBytes = function(maxbytes){
	var str = this;
	var len = 0;
	for (var i=0; i<str.length; i++){
		var temp = str.charCodeAt(i);
		if(escape(temp) == '%0D') continue;
		len += (temp>128?2:1);
		if(len > maxbytes) return str.substring(0,i);
	}
	
	return str;
}

/**
 * rpad
 * @param totLen
 * @param repl
 * @returns {String}
 */
String.prototype.rpad = function(totLen, repl) {
	var added = '';
	var len = totLen - this.length;
	for (var i = 0; i < len; ++i) {
		added += repl;
	}
	return this+added;
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

		var popupWin = window.open(targetUrl, windowName, popupProps);
		popupWin.focus();
		return popupWin;
	},
	
	/**
	 * 레이어 팝업 화면의 중간에 위치.
	 * @param {String} targetUrl	팝업 윈도우의 내용을 구성하기 위한 호출 URL
	 * @param {String} windowName	팝업 윈도우의 이름
	 * @param {Object} properties	팝업 윈도우의 속성
	 * width : 넓이
	 * height : 높이
	 * scrollBarX : X축 스크롤바 보임
	 * scrollBarY : Y축 스크롤바 보임
	 */	
	centerPopupLayer : function(targetUrl, windowName, properties) {
		
		var loadingDiv = $("body", top.document);
		
		loadingDiv
			.ajaxStart(function(){
				loadingDiv.mask("Waiting...");
			})
			.ajaxStop(function(){
				loadingDiv.unmask();
		});

		if(top != self){
			top.Common.centerPopupLayer(targetUrl, windowName, properties);
			return;
		}
		
		var childWidth = properties.width;
		var childHeight = properties.height;
		
		var title = properties.title;
		var dialogId = windowName;
		var dialogUrl = targetUrl;
		
		var dobj = $("#" + dialogId);
		if (dobj.length == 0) {
			dobj = $("<div>").attr("id", dialogId).css("vertical-align", "middle").appendTo("body");
		}
		
		if(properties.scrollBarX != null)
			dobj.css("overflow-x", "auto");
		else
			dobj.css("overflow-x", "hidden");
		
		if(properties.scrollBarY != null){
			dobj.css("overflow-y", "auto");
			childWidth += 20;	//스크롤바가 생긴만큼 늘려주고
		}else
			dobj.css("overflow-y", "hidden");
		
		$.ajax({
			url 	: dialogUrl,
			dataType: 'html',
			target 	: dialogId,
			success : function(data){
					dobj.html(data);
						dobj.dialog({
							width		: childWidth,			
							height		: childHeight,
							//draggable	: false,
							resizable	: false,
							modal		: true,
							title		: title,
							bgiframe	: true,
							close		: function(){
											if(properties.close) properties.close();
											dobj.remove();
										}
						});
					}
		});
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
   
   /********************************************************************
    * 기본날짜에 interval day 만큼 계산하여 새로운 날짜를 반환하는 함수
    * @param thisDate 기본 날짜
    * @param v_date interval 날짜(+,- 포함, default는 + )
    * @returns
    *********************************************************************/
   addDate : function(thisDate, v_date){
		var FORMAT = "-";
		var OPERATOR = "+";

		if (v_date.indexOf("-") > -1) {
			OPERATOR = "-";
			v_date = v_date.substring(1, v_date.length);
		} else if (v_date.indexOf('+') > -1) {
			OPERATOR = "+";
			v_date = v_date.substring(1, v_date.length);
		}

		// FORMAT을 포함한 길이 체크 및 FORMAT이 있는지 체크
		if (thisDate.length != 10 || thisDate.indexOf(FORMAT) < 0)
			return null;

		// 년도, 월, 일로 분리
		var this_dt = thisDate.split(FORMAT);

		// 월 - 1(자바스크립트는 월이 0부터 시작하기 때문에...)
		// Number()를 이용하여 08, 09월을 10진수로 인식하게 함.
		this_dt[1] = (Number(this_dt[1]) - 1) + "";
		var new_dt = new Date(this_dt[0], this_dt[1], eval(this_dt[2]
				+ OPERATOR + v_date));

		var yy = new_dt.getFullYear();
		var mm = (new_dt.getMonth() + 1) < 10 ? "0" + (new_dt.getMonth() + 1)
				: new_dt.getMonth() + 1;
		var dd = new_dt.getDate() < 10 ? "0" + new_dt.getDate() : new_dt
				.getDate();

		return yy + FORMAT + mm + FORMAT + dd;
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
										   		buttonImage: '/images/epc/admin/ico_cal.gif', 
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
											buttonImage: '/images/epc/admin/ico_cal.gif', 
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

/**
 * 달력 팝업
 */
function openCal(inputName) {
	Common.centerPopupWindow('/html/epc/calendar.html?inputName='+inputName, 'lottemartCalendar', {width:250,height:250,scrollbars:'no'});
}
/**
 * 달력 팝업 선택날짜 있을때
 */
function openCalSetDt(inputName, inputDate, cbFunc, minDt, maxDt) {
	var selDate = $.trim(inputDate).replace(/\D/g,"");
	
	Common.centerPopupWindow('/html/epc/calendar.html?inputName='+inputName+'&inputDate='+selDate+'&callbackFunc='+cbFunc+'&minDt='+minDt+'&maxDt='+maxDt, 'lottemartCalendar', {width:250,height:250,scrollbars:'no'});
	
	/*if(cbFunc){
		Common.centerPopupWindow('/html/epc/calendar.html?inputName='+inputName+'&inputDate='+selDate+'&callbackFunc='+cbFunc, 'lottemartCalendar', {width:250,height:250,scrollbars:'no'});	
	}else{
		Common.centerPopupWindow('/html/epc/calendar.html?inputName='+inputName+'&inputDate='+selDate, 'lottemartCalendar', {width:250,height:250,scrollbars:'no'});
	}*/
	
}
/**
 * 달력 팝업(callback 함수명 포함)
 */
function openCalWithCallback(inputName, callbackFuncName) {
	Common.centerPopupWindow('/html/epc/calendar.html?inputName='+inputName+'&callbackFunc='+callbackFuncName, 'lottemartCalendar', {width:250,height:250,scrollbars:'no'});
}

/**
 * 정수체크
 */
function isNumber(str){
	var tempstr ="0123456789";
	if(str == "") return false;
	
    for (i=0; i<str.length;i++){
		if (tempstr.indexOf(str.substring(i, i+1)) == -1){
			return false;
			break;
		}
	  }
	return true;
	//return /^\d+$/.test(str)
}

/**********************************************************
 * 시작일자 및 종료일자 유효성 체크
 ******************************************************** */
function checkFeday( startDt, endDt){
 
	var arrStartDt = new Array();
	arrStartDt[0] = startDt.substring(0,4);
	arrStartDt[1] = startDt.substring(4,6);
	arrStartDt[2] = startDt.substring(6,9);
	
	var arrEndDt = new Array();
	arrEndDt[0] = endDt.substring(0,4);
	arrEndDt[1] = endDt.substring(4,6);
	arrEndDt[2] = endDt.substring(6,9);
	
	arrStartDt[1] = (Number(arrStartDt[1]) - 1) + "";
	arrEndDt[1]   = (Number(arrEndDt[1]) - 1) + "";
	
	var sDate = new Date(arrStartDt[0], arrStartDt[1], arrStartDt[2]);
	var eDate = new Date(arrEndDt[0], arrEndDt[1], arrEndDt[2]);
	
	if ( sDate > eDate) {
		return false;
	}
	return true;
}

/** ********************************************************
 * 콤보박스 reset
 ******************************************************** */
function comboReset( obj) {
	var len = obj.length;
	var i;
	for (i=len-1; i>0; i--) {
		obj.options[i] = null;
	}
}

/** ********************************************************
 * ajax 조회 후 콤보박스의 option부분을 생성한다.
 ******************************************************** */
//전시관리>표준분류카테고리할당 대/중/소/세분류 콤보박스
function comboCall( obj, data, all) {
	
	//리셋
	comboReset( obj);

	if ( data != null && data.length > 0 ) {
		
		for( var i=0; i<data.length; i++) {
			if ( i == 0) {
				if(all == 'ALL'){
					obj.options[i] = new Option( "전체", "");
				}else{
					obj.options[i] = new Option( "선택", "");
				}
			}
			obj.options[i+1] = new Option( data[i].name, data[i].code);
		}
	}
}

//전시관리>사이트카테고리>등록팝업>연동사이트영역콤보
function comboCall2( obj, data, all) {
	
	//리셋
	comboReset( obj);
	
	if ( data != null && data.length > 0 ) {
		
		for( var i=0; i<data.length; i++) {
			if ( i == 0) {
				if(all == 'ALL'){
					obj.options[i] = new Option( "전체", "");
				}else{
					obj.options[i] = new Option( "선택", "");
				}
			}
			obj.options[i+1] = new Option( data[i].CD_NM, data[i].LET_2_REF);
		}
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

/*
 * 로딩중 표시
 */
function loadingMask()
{
	var childWidth  = 128;
	var childHeight = 128;
	var childTop    = (document.body.clientHeight - childHeight) / 2;
	var childLeft   = (document.body.clientWidth  - childWidth)  / 2;

	var loadingDiv = $('<span id="loadingLayer" style="position:absolute; left:'+childLeft+'px; top:'+childTop+'px; width:100%; height:100%; z-index:10001"><img id="ajax_load_type_img" src="/js/jquery/res/loading.gif"></span>').appendTo($("body"));
	loadingDiv.show();
	var loadingDivBg = $('<iframe id="loadingLayerBg" frameborder="0" style="filter:alpha(opacity=0);position:absolute; left:0px; top:0px; width:100%; height:100%; z-index:10000"><img id="ajax_load_type_img" src="/js/jquery/res/loading.gif"></iframe>').appendTo($("body"));
	loadingDivBg.show();
}

/*
 * 로딩바 감추기
 */
function hideLoadingMask()
{
	$('#loadingLayer').remove();
	$('#loadingLayerBg').remove();
}




/**************************************************
 * 2015.11.02 이후 신규로 추가한 함수(EPC)
 **************************************************/

Date.prototype.yyyymmdd = function() {
	var yyyy = this.getFullYear().toString();
	var mm = (this.getMonth() + 1).toString();
	var dd = this.getDate().toString();

	return yyyy + (mm[1] ? mm : '0' + mm[0]) + (dd[1] ? dd : '0' + dd[0]);
}

/* data table 초기화 */
function setTbodyInit(listTable){
	$("#"+listTable+" tr").remove();
}

/*data table 결과없음 처리*/
function setTbodyNoResult(listTable,cols, msg){
	if(!cols) cols=0;
	if(!msg) msg = "조회결과가 없습니다.";
	$("#"+listTable).append('<tr><td colspan="'+cols+'" class="tdm" align=center height=30>'+msg+'</span></td></tr>');
}

/*숫자에 콤마 추가히기(금액단위)*/
function setComma(n) {
	var reg = /(^[+-]?\d+)(\d{3})/;
	n += "";
	while (reg.test(n))
		n = n.replace(reg, "$1" + "," + "$2");
	return n;
}

/* 선택된 점포 배열로 넘기기(구분자로 잘라서 배열로 저장) */
function storeValArrList(storeVal) {
	var storeList = new Array();
	
	var storeArr = storeVal.split("-");
	for (var i = 0; i < storeArr.length; i++) {
		if (storeArr[i] != "") {
			storeList.push(storeArr[i]);
		}
	}
	
	return storeList;
}

/**
 * 문자열 오른쪽 길이만큼 채움
 * @param str
 * @param repl
 * @param len
 * @returns
 */
function strRpad(str, repl, len) {
	var added = "";
	
	if (str == null || str == "") {
		return str;
	}
	
	var strLen = parseInt(len) - str.length;
	if (strLen <= 0) {
		return str;
	}
	
	for (var i = 0; i < parseInt(strLen); i++) {
		added += repl;
	}
	
	return str + added;
}

/**
 * 문자열 오른쪽 길이만큼 채움
 * @param str
 * @param repl
 * @param len
 * @returns
 */
function strLpad(str, repl, len) {
	var added = "";
	
	if (str == null || str == "") {
		return str;
	}
	
	var strLen = parseInt(len) - str.length;
	if (strLen <= 0) {
		return str;
	}
	
	for (var i = 0; i < parseInt(strLen); i++) {
		added += repl;
	}
	
	return added + str;
}

/**
 * RFC 호출 시 공통으로 가져가는 RequstCommon
 * @returns {___anonymous20116_20117}
 */
function getReqCommon () {
	var reqInfo = {};
	
	reqInfo["ZPOSOURCE"] = "";
	reqInfo["ZPOTARGET"] = "";
	reqInfo["ZPONUMS"] = "";
	reqInfo["ZPOROWS"] = "";
	reqInfo["ZPODATE"] = "";
	reqInfo["ZPOTIME"] = "";
	
	return reqInfo;
}

/**
 * PageContext 가져오기
 * @returns
 */
function getContextPath() {
	var hostIndex = location.href.indexOf( location.host ) + location.host.length;
	return location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );
}

/**
 * 날짜포맷지정
 * @param str
 */
function strToDateFormat(str, fm) {
	var str = str.trim();
	
	if (str == "") {
		return "";
	}
	
	if (fm == "" || fm == null) {
		fm = "-";
	}
	
	if (str.length == 8) {
		return str.substring(0, 4) + fm + str.substring(4, 6) + fm + str.substring(6, 8);
		
	} else {
		return str;
	}
}

/**
 *	입력값에 숫또는 . 자만 있는지 체크
 *	(번호 입력란 체크.
 *	 금액입력란은 isNumComma를 사용해야 합니다.)
 */
function newIsNumberDu(input) {
    var chars = "0123456789.";
    return newHasCharsOnly(input,chars);
}

/**
 * 입력값이 특정 문자(chars)만으로 되어있는지 체크
 * 특정 문자만 허용하려 할 때 사용
 * ex) if (!hasCharsOnly(form.blood,"ABO")) {
 *         alert("혈액형 필드에는 A,B,O 문자만 사용할 수 있습니다.");
 *     }
 */
function newHasCharsOnly(input,chars) {
    for (var inx = 0; inx < input.val().length; inx++) {
       if (chars.indexOf(input.val().charAt(inx)) == -1)
           return false;
    }
    return true;
}

//** 라이센스 설정
var ibleaders;
ibleaders = ibleaders || {};
ibleaders = {
	// 개발
	license : "W2FtSztPKCRwbDc1YjJxbn9SMVxtHykodWR4ImMxYCF3NQQyXXMVaWl2OXY6LTtyfzZyFSMEYwt2fDJ+Lm4oMnlnY3NOdEUmWm8yN2Q2"
    	
    // 운영 - 운영에 올릴 때 이걸 풀고 위에 것을 막으세요.
    //license: "W2FtSztPKC5wbzcxYjJxbn9NNREsCHUne20iIGk5LSN3dF96FndSOnx2d2wtLH4rPDdyV3kAZwJweCM+bXpwI39vISIMJ1wuRStveXtpOnNoL2JgKxx9FXtJejtpPGEmfWU8bSt5Um8JIgEsamg/dGkudWMqZDYedEd7Emo4YCN6ZD16KHgqJAItDzRMN3old3M1N2IsdyBhD3VIKAhvOjkibGczZC1iJzQYPEUpSSd3cyw+e3c8bX5qJEgjQTNBJjhlfzVmM2k+IXk3FHRcPlVtMjR6N2A+dW8iL3JLOE47H34haC0qdiU9fnExbUsvU3AaNGEqfy1tLm9+aCt4Wy4ae082ezd2JSNlZzZtL2pAJUh7VjVnOHVsKDFlLHMjfw9gWHYUe2k8MmVnKGIyfyAvBThGKFIvbChreH4xZz9maT9LOFw2XjojbXt1PH5pOyFhcFI/Qjpdaik1ZSt6KmwveH1pSDpPIxR6ZzV/NXY/I2pocCsENEtkHzV+MmE5dW8pMnYubVAxXz0AMyk1ciA9YGc3cA=="
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
			
			if (typeof(data) == 'string') {
				alert('세션을 만료되었습니다.');
			} else if (data.result) {
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

//IBSheet 그리드데이터 엑셀 데이터 다운로드
function excelDataDown(mSheet, fileNm, hideCol) {
	mSheet.SetWaitImageVisible(1); // 처리중 로딩바가 보이게 한다.

	var downCols = "";
	if (hideCol) downCols  = makeHiddenSkipCol(mSheet, hideCol);

	mSheet.Down2Excel({'FileName':fileNm, Merge:1, Multipart:0, HiddenColumn:true, DownCols:downCols});
	mSheet.SetWaitImageVisible(0); // 처리중 로딩바가 안보이게 한다.
}

//IBSheet 엑셀양식 다운로드
function excelFormDown(mSheet, fileName, headerRows, hideCol) {
	var hdRow = '0';
	for (var i=1; i<headerRows; i++) {
		hdRow += '|' + i;
	}

	var downCols = "";
	if (hideCol) downCols  = makeHiddenSkipCol(mSheet, hideCol);
	mSheet.SetWaitImageVisible(1); // 처리중 로딩바가 보이게 한다.
	mSheet.Down2Excel({"FileName":fileName, Merge:1, Multipart:0, DownRows:hdRow, DownCols:downCols});
	mSheet.SetWaitImageVisible(0); // 처리중 로딩바가 안보이게 한다.
}

//감춰진 컬럼이나, Seq,Status,DelCheck 타입의 컬럼은 제외
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

//페이징 없이 서버에서 전체데이터 엑셀 다운로드(검색조건 적용)
function directExcelDown(mSheet, fileName, url, formId, param, hideCol) {

	var mParam = new Object();
	mParam.Multipart = 0;
	mParam.Merge 	 = 1;
	mParam.URL 		 = url;
	mParam.FileName  = fileName;
	mParam.DownCols  = makeHiddenSkipCol(mSheet, hideCol);

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
	
	mSheet.SetWaitImageVisible(1); // 처리중 로딩바가 보이게 한다.
	mSheet.DirectDown2Excel(mParam);
	mSheet.SetWaitImageVisible(0); // 처리중 로딩바가 안보이게 한다.
	
	hideLoadingMask();
}

// UUID 생성
function getUUID() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
	return v.toString(16);
	});
}

// 2016.08.24 2차에 적용
//20160601 김학수 추가 [ 배열에서 indexOf() 함수가 적용되지 않는경우 추가 ( ie 8 이하 )]
if ( ! Array.prototype.indexOf ){ 
 Array.prototype.indexOf = function( item , index ){ 
     var index = ( index ) ? parseInt( index , 10 ) : 0; 
     if ( index < 0 ) index = this.length + index; 
     for ( index; index < this.length; index++ ){  if ( this[ index ] === item ) return index;  } 
     return -1; 
 }; 
}

//날짜 차이 계산 함수
//date1 : 기준 날짜(YYYY-MM-DD), date2 : 대상 날짜(YYYY-MM-DD)
function getDateDiff(date1,date2)
{
var arrDate1 = date1.split("-");
var getDate1 = new Date(parseInt(arrDate1[0],10),parseInt(arrDate1[1],10),parseInt(arrDate1[2],10));
var arrDate2 = date2.split("-");
var getDate2 = new Date(parseInt(arrDate2[0],10),parseInt(arrDate2[1],10),parseInt(arrDate2[2],10));

var getDiffTime = getDate2.getTime() - getDate1.getTime();

return Math.floor(getDiffTime / (1000 * 60 * 60 * 24));
}

/**
 * 점포 검색 팝업
 */
function openStr(mode, id, isUnusedAllStoreCode) {
	var context = getContextPath();
	id = id || '';
	isUnusedAllStoreCode = isUnusedAllStoreCode || false;
	
	Common.centerPopupWindow(context + '/product/PSCMPRD0060.do?mode=' + mode+ '&id=' + id + '&isUnusedAllStoreCode=' + isUnusedAllStoreCode, '점포검색', {
		width : 1000,
		height : 700,
		scrollBars : 'YES'
	});
}

/**
 * 상품 검색 팝업 ( 온오프구분, 묶음 제외 )
 */
function openProductOnOffNotDeal(mode, onOffYn) {
	var context = getContextPath();
	// onOffYn : 01 = 온오프('Y') , 02,03 = 온라인('N')
	Common.centerPopupWindow(context + '/product/repProdCd/selectProdCdPop.do?mode=' + mode+ '&onOffYn=' + onOffYn + '&dealYn=N', '상품검색', {
		width : 800,
		height : 550,
		scrollBars : 'no'
	}); 
}

//array에서 중복값 포함된 부분만 추출
function getMatchedArr(tgArr, compareArr){
	const setCompareArr = new Set(compareArr);
	return tgArr.filter(item => setCompareArr.has(item));
}

//array에서 중복값이 없는 부분만 추출
function getUnMatchedArr(tgArr, compareArr){
	const setCompareArr = new Set(compareArr);
	return tgArr.filter(item => !setCompareArr.has(item));
}

