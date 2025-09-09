
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
       
       if ( v_date.indexOf('-') > -1) {
    	   OPERATOR = "-";
    	   v_date = v_date.substring(1, v_date.lenght);
       }
       else if ( v_date.indexOf('+') > -1) {
    	   OPERATOR = "+";
    	   v_date = v_date.substring(1, v_date.lenght);
       }

       // FORMAT을 포함한 길이 체크 및 FORMAT이 있는지 체크
       if (thisDate.length != 10 || thisDate.indexOf(FORMAT) < 0)
           return null;

       // 년도, 월, 일로 분리
       var this_dt = thisDate.split(FORMAT);

       // 월 - 1(자바스크립트는 월이 0부터 시작하기 때문에...)
       // Number()를 이용하여 08, 09월을 10진수로 인식하게 함.
       this_dt[1] = (Number(this_dt[1]) - 1) + "";

       var new_dt = new Date(this_dt[0], this_dt[1], eval(this_dt[2] + OPERATOR +  v_date));
       
       var yy = new_dt.getYear();
       var mm = (new_dt.getMonth()+1) < 10 ? "0"+(new_dt.getMonth()+1) : new_dt.getMonth()+1 ;
       var dd = new_dt.getDate() < 10 ? "0"+new_dt.getDate() : new_dt.getDate();
       
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

