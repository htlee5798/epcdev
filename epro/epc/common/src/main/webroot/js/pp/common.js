
/**
 * 문자열 왼쪽,오른쪽 공백을 제거.
 * 
 * @return {String} 공백제거된 문자열.
 */
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
}

/**
 * 문자열의 왼쪽 공백을 제거.
 * 
 * @return {String} 공백제거된 문자열.
 */
String.prototype.ltrim = function() {
	return this.replace(/^\s+/,"");
}

/**
 * 문자열의 오른쪽 공백을 제거.
 * 
 * @return {String} 공백제거된 문자열.
 */
String.prototype.rtrim = function() {
	return this.replace(/\s+$/,"");
}

/**
 * 해당문자열에 특정문자의 포함 여부 반환.
 * 
 * @param {String}
 *            findStr 찾을문자.
 * @return {Boolean} 특정문자포함여부
 */
String.prototype.contains = function(findStr){
	return this.indexOf(findStr) >= 0;
}

/**
 * 해당문자열의 특정문자(findStr)를 특정문자(newStr)로 전체치환.
 * 
 * @param {Object}
 *            findStr 찾을문자
 * @param {Object}
 *            newStr 대체문자
 * @return {String} 치환된문자열
 */
String.prototype.replaceAll = function(findStr, newStr){
    return this.replace(new RegExp(findStr, "gi"), newStr);
}

/**
 * 해당문자열의 바이트 수를 반환.
 * 
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
 * 
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
 * Common Class 공통으로 사용될 기능들을 구현한 샘플 클래스. 스트링 NULL 체크 / NameSpace / Import
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
	 * 
	 * @param {String}
	 *            str
	 */
	isEmpty : function(str){
		if(str == null) return true;
		return !(str.replace(/(^\s*)|(\s*$)/g, ""));
	},

	/**
	 * 네임스페이스 등록.
	 * 
	 * @param {String}
	 *            ns 네임스페이스
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
	 * 
	 * @param {String}
	 *            targetUrl 팝업 윈도우의 내용을 구성하기 위한 호출 URL
	 * @param {String}
	 *            windowName 팝업 윈도우의 이름
	 * @param {Object}
	 *            properties 팝업 윈도우의 속성(넓이, 높이, x/y좌표)
	 */	
	centerPopupWindow : function(targetUrl, windowName, properties) {
		var childWidth = properties.width;
		var childHeight = properties.height;
		var childTop = (screen.height - childHeight) / 2 - 50;    // 아래가 가리는
																	// 경향이 있어서
																	// 50을 줄임
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
	 * 
	 * @param {String}
	 *            targetUrl 팝업 윈도우의 내용을 구성하기 위한 호출 URL
	 * @param {String}
	 *            windowName 팝업 윈도우의 이름
	 * @param {Object}
	 *            properties 팝업 윈도우의 속성 width : 넓이 height : 높이 scrollBarX : X축
	 *            스크롤바 보임 scrollBarY : Y축 스크롤바 보임
	 */	
	centerPopupLayer : function(targetUrl, windowName, properties) {
		
//		if(top != self){
//			top.Common.centerPopupLayer(targetUrl, windowName, properties);
//			return;
//		}
		
		var loadingDiv = $("body", top.document);
		loadingDiv
		.ajaxStart(function(){
			loadingDiv.mask("Waiting...");
		})
		.ajaxStop(function(){
			loadingDiv.unmask();
		});
		
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
			childWidth += 20;	// 스크롤바가 생긴만큼 늘려주고
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
					// draggable : false,
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
	 * 레이어 팝업 화면의 중간에 위치.
	 * 
	 * @param {String}
	 *            targetUrl 팝업 윈도우의 내용을 구성하기 위한 호출 URL
	 * @param {String}
	 *            windowName 팝업 윈도우의 이름
	 * @param {Object}
	 *            properties 팝업 윈도우의 속성 width : 넓이 height : 높이 scrollBarX : X축
	 *            스크롤바 보임 scrollBarY : Y축 스크롤바 보임
	 */	
	mobileLayer : function(targetUrl, windowName, properties) {

		var childWidth = properties.width;
		var childHeight = properties.height;
		
		var title = properties.title;
		var dialogId = windowName;
		var dialogUrl = targetUrl;
		
		var dobj = $("#" + dialogId);
		if (dobj.length == 0) {
			dobj = $("<div>").attr("id", dialogId).css("vertical-align", "middle").appendTo("body");
		}
				
		$.maskAjax({
			url 	: dialogUrl,
			dataType: 'html',
			target 	: dialogId,
			success : function(data){
				var json = {};
				try{
					json = eval ('('+data+')');
				}catch(e){
					json = {resultCode:"0"};
				}
				
				if(json.resultCode == -1){
					alert(json.resultMsg);
				}else{
					dobj.html(data).dialog({
						width		: childWidth,			
						height		: childHeight,
						draggable	: false,
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
				
			}
		});
	},
	
	/**
	 * 업로드 하려는 파일의 이름 사이즈 체크.
	 * 
	 * @param {String}
	 *            uploadFileName 파일명
	 * @param {String}
	 *            limitSize
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
	 * 
	 * @param {integer}
	 *            x GPS X 위치
	 * @param {integer}
	 *            y GPS Y 위치
	 * @param {String}
	 *            message 내용
	 */		
	viewMap : function(context, x, y, message){
	 	Common.centerPopupWindow(context+'/bbs/locationMap.do?x='+x+"&y="+y+"&message="+encodeURIComponent(message), 'mapPopup', {width : 500, height : 300});
		return false;
	},
	
	/**
	 * 두 날자 사이의 일수를 반환
	 * 
	 * @param {String}
	 *            fromDate 시작일자 (yyyy-mm-dd)
	 * @param {String}
	 *            toDate 종료일자 (yyyy-mm-dd)
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
   
   /***************************************************************************
	 * 기본날짜에 interval day 만큼 계산하여 새로운 날짜를 반환하는 함수
	 * 
	 * @param thisDate
	 *            기본 날짜
	 * @param v_date
	 *            interval 날짜(+,- 포함, default는 + )
	 * @returns
	 **************************************************************************/
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
	 * 
	 * @param {String}
	 *            title 타이틀
	 * @param {String}
	 *            msg 내용
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
	 * Alert 다이얼로그 띄움. 2초후 사라짐
	 * 
	 * @param {String}
	 *            title 타이틀
	 * @param {String}
	 *            msg 내용
	 * @param {String}
	 *            focus_id 포커스 아이디
	 */	
	showMessageRemove : function(msg, focus_id, proc){
		if(msg != undefined){
			var dialogTag = "<div id='jQueryDialogMessage' title='알림' class='font25'><div class='font25_prod'>" + msg + "</div></div>";
			$(dialogTag).dialog({
				modal		: true,
				draggable	: false,
				resizable	: false,
				width 		: 500,
				close: function(){
					if(proc != undefined){
						eval(proc+"();");
					}
					$("#"+focus_id).focus();
				}
			});	
			setTimeout("showMessageClose('"+focus_id+"','"+proc+"');",3000);
		}else{
			showMessageClose(focus_id, proc);
		}
	},
	showMessageRemoveNoModal : function(msg, focus_id, proc){
		var dialogTag = "<div id='jQueryDialogMessage' title='알림' class='font25'><div class='font25_prod'>" + msg + "</div></div>";
		$(dialogTag).dialog({
			modal		: false,
			draggable	: false,
			resizable	: false,
			width 		: 500,
			close: function(){
				if(proc != undefined){
					eval(proc+"();");
				}
				$("#"+focus_id).focus();
			}
		});	
		setTimeout("showMessageClose('"+focus_id+"','"+proc+"');",3000);
	},
	/**
	 * 사이즈를 조절해서 보여준다
	 * 
	 * @param title
	 * @param msg
	 * @param proc
	 */
	showDialog : function(title, msg, c_width, c_height){
		var dialogTag = "<div id='jQueryDialog' title=\"" + title + "\">" + msg + "</div>";
		if(c_width == undefined) c_width = 530;
		if(c_height == undefined) c_height = 550;
		
		$(dialogTag).dialog({
			modal		: true,
			draggable	: false,
			resizable	: false,
			width 		: c_width,
			height 		: c_height,
			buttons: {
				'확인': function(){
					$(this).dialog('destroy').remove();
				}
			}
		
		});	// setTimeout("$(this).dialog('destroy').remove();",2000);
	},
	
	/**
	 * 사이즈를 조절해서 보여준다
	 * 
	 * @param title
	 * @param msg
	 * @param proc
	 */
	showDialogRemove : function(title, msg, c_width, c_height){
		var dialogTag = "<div id='jQueryDialogMessage' title=\"" + title + "\">" + msg + "</div>";
		if(c_width == undefined) c_width = 530;
		if(c_height == undefined) c_height = 550;
		
		$(dialogTag).dialog({
			modal		: true,
			draggable	: false,
			resizable	: false,
			width 		: c_width,
			height 		: c_height,
			buttons: {
				'확인': function(){
					$(this).dialog('destroy').remove();
				}
			}
		});
		setTimeout("showMessageClose('temp','undefined');",3000);
	},
	
	/**
	 * Alert 다이얼로그 띄운 후 정해진 작업을 완료한 후 팝업창을 닫는다.
	 * 
	 * @param {String}
	 *            msg 내용
	 * @param {String}
	 *            procArray 작업 목록 배열
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
	 * 
	 * @param {String}
	 *            msg 내용
	 * @param {String}
	 *            proc 작업
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
	 * jQuery-UI datepicker 부착 searchStartDate, searchEndDate 필요
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
										   		buttonImage: '/images/pp/admin/ico_cal.gif', 
										   		buttonImageOnly: true,
										   		buttonText: '시작일자',
										   		maxDate: new Date(),
												// minDate: "-1m",
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

														// var maxDate = new
														// Date(chYear,
														// chMonth-1, chDay+6);
														var maxDate = new Date();
														month = parseInt(maxDate.getMonth())+1;
														var maxDay  = (maxDate.getFullYear() + "-" + (month < 10 ? "0"+month : month) + "-" + (maxDate.getDate() < 10 ? "0"+maxDate.getDate() : maxDate.getDate()));

														// 금일 보다 +7 일이 크면 금일이 최고
														// 크기
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
											buttonImage: '/images/pp/admin/ico_cal.gif', 
											buttonImageOnly: true,
									   		buttonText: '종료일자',
									   		maxDate: new Date()
										}
									);
		});
	}
};

// 2초후 사라지는 레이어 닫기
function showMessageClose(focus_id, proc){
	if(proc != "undefined"){
		eval(proc+"();");
	}
	$('#jQueryDialogMessage').remove();
	$("#"+focus_id).focus();
}
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
//function openCal(inputName) {
//	Common.centerPopupWindow('/html/pp/calendar.html?inputName='+inputName, 'lottemartCalendar', {width:250,height:250,scrollbars:'no'});
//}
//기존 달력 팝업 버튼은 포커스 이동으로 달력이 나오도록 수정
function openCal(inputName) {
	
	try {
		// split 
		var splitParam = inputName.split(".");
		param = splitParam[splitParam.length-1]
	    param = "#"+param;
	    if( $(param).is(":focus") == false) {
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
// common.js 로드 시 class속성에 day 존재하는 케이스는 달력으로 로드 함
$(document).ready(function(){
	
	$(".day").each(function(i){
		id = $(".day").eq(i).attr("id");
		openCals(id);
		$("#"+id).inputmask({
			mask: "y-1-2", 
			//placeholder: "YYYY-MM-DD", 
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
 * 달력 팝업(callback 함수명 포함)
 */
function openCalWithCallback(inputName, callbackFuncName) {
	Common.centerPopupWindow('/html/pp/calendar.html?inputName='+inputName+'&callbackFunc='+callbackFuncName, 'lottemartCalendar', {width:250,height:250,scrollbars:'no'});
}

/**
 * 정수체크
 */
function isNumber(str){
	var tempstr ="0123456789";
	if(str == "") return false;
	
    for ( var i=0; i<str.length;i++){
		if (tempstr.indexOf(str.substring(i, i+1)) == -1){
			return false;
			break;
		}
	  }
	return true;
	// return /^\d+$/.test(str)
}

/**
 * Form 을 json 으로 serialize함 (Serialize Form to json) 예) 호출 :
 * $('#frm').serializeJSON(); 결과 : { "fieldName1": "data1", "fieldName2":
 * "data2", "fieldName3": "data3"}
 */
$.fn.serializeJSON = function() { 
    var o = {};
    var a = this.serializeArray(); 
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) { 
                o[this.name] = [o[this.name]];
            } 
            o[this.name].push(this.value || ''); 
        } else { 
            o[this.name] = this.value || ''; 
        } 
    }); 
    return o; 
}; 

/**
 * javascript load
 */
function importJS(src) {
	var js = $("<script>").attr({type: "text/javascript", src: src});
	$("head").append(js);
}

// ---------------------- DP(배송계획) 추가 -----------------------//
var BONSA_STRCD 			= '999'; 			// 본사 점포코드
var DP_CONTEXT_PATH 		= '';
var DP_EDITABLE_CELL_COLOR 	= '218|254|227'; 	//수정가능 항목 BgColor
var DP_CHANGED_CELL_COLOR 	= '246|229|226'; 	//수정가능한 항목 수정시에 색 변경 표시
var DP_VALID_ERROR_COLOR 	= '97|178|206'; 	//validation error cell color
var DP_SMS_TIME_GAP         = 20;               //도착예정SMS 발송시 예정시각 전 후 계산에 반영

function dp_setContextPath(path) {
	DP_CONTEXT_PATH = path;
}

function dp_getContextPath() {
	return DP_CONTEXT_PATH;
}

/**
 * WiseGrid의 체크박스 선택여부 확인 조건) 체크박스 칼럼명 : SELECTED
 */
function dp_isRowSelected(gridObj) {
	var result = false;
	var rowCount = gridObj.GetRowCount();
	for (var i = 0; i < rowCount; i++) {
		if (gridObj.GetCellValue('SELECTED', i) == 1) {
			result = true;
			break;
		}
	}
	if (!result) {
		alert('선택한 항목이 없습니다.');
	}
	return result;
}

/**
 * Grid 내 수정가능 항목 BgColor 변경
 */
function dp_changeColCellBgColor(gridObj, strAry) {	
	if ( $.isArray(strAry) && strAry.length > 0 ) {
		for ( var i = 0; i < strAry.length; i++ ) {
			gridObj.SetColCellBgColor(strAry[i], DP_EDITABLE_CELL_COLOR);
		}
	}	
}

/**
 * showModalDialog open
 * 
 * @param {String}
 *            targetUrl 팝업 윈도우의 내용을 구성하기 위한 호출 URL
 * @param {String}
 *            windowName 팝업 윈도우의 이름
 * @param {Object}
 *            properties 팝업 윈도우의 속성(넓이, 높이, x/y좌표)
 */
function dp_modalOpen(targetUrl, windowName, properties) {
	var childWidth 	= properties.width;
	var childHeight = properties.height;
	var childTop 	= (screen.height - childHeight) / 2 - 50;    // 아래가 가리는
																	// 경향이 있어서
																	// 50을 줄임
	var childLeft 	= (screen.width - childWidth) / 2;
	var popupProps 	= "dialogWidth:" + childWidth + "px;dialogHeight:" + childHeight + "px;dialogTop:" + childTop + "px;dialogLeft:" + childLeft+"px;status:0";	

	return window.showModalDialog(targetUrl, windowName, popupProps);
}

/**
 * 엑셀 다운로드
 * 
 * @param gridObj
 */
function dp_excelDownload(gridObj) {
	if(gridObj.GetRowCount() == 0){
		alert('조회된 자료가 없습니다');
		return;
	}
	gridObj.ClearExcelInfo();
	gridObj.ExcelExport('', '', true, true);
}

/**
 * JSON 타입을 받아 콤보생성
 * 
 * @param obj
 *            콤보구성될 대상 jQuery Object
 * @param json
 *            option 태그를 구성할 데이터(CODE,NAME을 가져야 한다)
 * @param gbn
 *            (0:없음,1:전체,2:선택)
 */
function dp_makeComboWithJson(obj, json, gbn) {
	if ( obj == null ) return;
	if ( gbn == 0 ) {
		// do nothing
	} else if ( gbn == 1 ) {
		obj.html('<option value="">전체</option>');
	} else {
		obj.html('<option value="">선택</option>');	
	}
	if ( json == null ) return;
    for( var data, i = -1; data = json[++i]; ) {    	
		$('<option></option>')
        .attr('value', data.CODE)
        .text(data.NAME)
        .appendTo(obj);
    }
}

/**
 * dp_makeComboWithJson + 선택표시
 */
function dp_makeComboWithJsonValue(obj, json, gbn, strVal) {
	if ( obj == null ) return;
	if ( gbn == 0 ) {
		// do nothing
	} else if ( gbn == 1 ) {
		obj.html('<option value="">전체</option>');
	} else {
		obj.html('<option value="">선택</option>');	
	}
	if ( json == null ) return;
    for( var data, i = -1; data = json[++i]; ) {
    	if ( data == strVal ) {
	    	$('<option></option>')
	        .attr('value', data.CODE)
	        .attr('selected', true)
	        .text(data.NAME)
	        .appendTo(obj);
    	} else {
    		$('<option></option>')
	        .attr('value', data.CODE)
	        .text(data.NAME)
	        .appendTo(obj);
    	}
    }
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

/**
 * 날짜타입(yyyy-MM-dd) 으로 변환
 * 
 * @param str
 * @returns {String}
 */
function dp_makeDateStr(str) {
	var result = "";	 
	if ( str != null ) {
		var sdate = str.trim().replaceAll('-','');
		if ( sdate.length == 8 ) {
			result = sdate.substring(0,4)+'-'+
					 sdate.substring(4,6)+'-'+
					 sdate.substring(6,8);
		} else {
			result = sdate;
		}
	}
	return result;
}

/**
 * 그리드 조회 후 페이징 처리
 * 
 * @param GridObj
 */
function dp_paging(GridObj) {    
    var totalCnt    = "0";
    var rowsPerPage = "0";
    var curPage     = "0";
    try {
        totalCnt    = GridObj.GetParam("totalCount");
        rowsPerPage = GridObj.GetParam("rowsPerPage");
        curPage     = GridObj.GetParam("currentPage");
    } catch (e) {}
        
    setLMPaging(totalCnt,rowsPerPage,curPage,'goPage','pagingDiv');
}

/**
 * 우편번호 팝업
 */
function dp_popupZip(){
	var targetUrl = dp_getContextPath() + "/hocommon/post/list.do";	
	Common.centerPopupWindow(targetUrl, 'post_no', {width : 500, height : 410});
}

/**
 * 조회조건 점포코드관련 기본 세팅
 * 
 * @param login_str_cd
 */
function dp_initStoreInput(login_str_cd, login_str_nm) {
	$("#str_cd").val(login_str_cd);
	$("#str_nm").val(login_str_nm);
	$("#str_nm").prop("readonly", true);
	
	if (login_str_cd != BONSA_STRCD) { // 999:전점(본사직원)
	    $("#str_cd").prop("readonly", true);
	} else {
	    $("#str_cd").focus();
	}
	$('#str_cd').numeric();
}

/**
 * 점포코드 직접 입력시 점포명 자동 선택
 */
function dp_selectStoreCombo() {
	var strCd = $("#str_cd_txt").val();	
	$("#str_cd option").each( function(){
		if( $(this).val() == strCd) {
			$(this).attr("selected", true);
			return;
		}		
	});
}

/**
 * 코드 직접 입력시 해당하는 값을 자동 선택(그리드 입력용)
 * 
 * @param htmlObj -
 *            select컨트롤 (주로 select컨트롤의 id에 option을 붙여서 사용, 예:str_cd option)
 * @param gridObj -
 *            그리드
 * @param setColumn -
 *            해당값을 출력할 컬럼명
 * @param rowIndex -
 *            몇 번째 row이냐?
 * @param getColumn -
 *            값을 읽어들일 컬럼명
 */
function dp_selectCombo_for_Grid(htmlObj, gridObj, setColumn, rowIndex, getColumn) {
	var inputCd = gridObj.GetCellValue(getColumn, rowIndex);
	var resultFlag = false;
	
	if (htmlObj == null) return;

	htmlObj.each( function() {
		if( $(this).val() == inputCd) {
			gridObj.SetCellValue(setColumn, rowIndex, $(this).text());
			resultFlag = true;
		}
	});
	return resultFlag;
}

/**
 * 점포콤보 선택시 점포코드값 셋팅 함수
 */
function dp_setSaleStrCdTxt() {
	if ( $("#str_cd").val() == '') {
		$("#str_cd_txt").val("");
	} else {
		$("#str_cd_txt").val($("#str_cd").val());
	}
}

/**
 * 점포선택콤보 변경과 동시에 배송차수 콤보 재구성
 * 
 * @param gubun (0:없음,1:전체,2:선택)
 */
function dp_selectStoreComboAndDeliSeqncCombo(gubun) {
	var strCd = $("#str_cd_txt").val();	
	$("#str_cd option").each( function(){
		if( $(this).val() == strCd) {
			$(this).attr("selected", true);
			dp_getDeliSeqncCombo(gubun);
			return;
		}		
	});
}

/**
 * 점포코드 변경시 배송차수 콤보 재구성 사용하기 위해서는 jsp 에서
 * dp_setContextPath('${pageContext.request.contextPath}') call 후 이용.
 * 
 * @param gubun (0:없음,1:전체,2:선택)
 */
function dp_getDeliSeqncCombo(gubun) {
    var str_cd = $('#str_cd').val().trim();
    if (str_cd == '') {
    	dp_makeComboWithJson($('#deli_seqnc'), null, gubun);
    	return;
    }
    $.post(
    		dp_getContextPath() + "/dpcommon/multiSelect.do",
            'gubun=deliseqnc&str_cd='+str_cd,
            function(json){
                dp_makeComboWithJson($('#deli_seqnc'), json, gubun);
            },
            "json"
    );
}

/**
 * 노선번호 콤보 구성
 * @param gubun (0:없음,1:전체,2:선택)
 */
function dp_getRouteNoCombo(gubun) {
    var str_cd = $('#str_cd').val().trim();
    var deli_dy = $('#deli_dy').val().replaceAll("-","");    
    var deli_seqnc = $('#deli_seqnc').val();
    if (str_cd == '' || deli_dy == '' || deli_seqnc == '') {
    	dp_makeComboWithJson($('#route_no'), null, gubun);
    	return;
    }
    $.post(
    		dp_getContextPath() + "/dpcommon/multiSelect.do",
            'gubun=routeno&str_cd='+str_cd+'&deli_dy='+deli_dy+'&deli_seqnc='+deli_seqnc,
            function(json){
                dp_makeComboWithJson($('#route_no'), json, gubun);
            },
            "json"
    );
}

/**
 * 기사명 선택시 차량번호 세팅 함수
 */
function dp_setVhclNoTxt() {
	var driverNm = $("#selDriver option:selected").text();
	$("#selVhclNo option").each( function(){
		if( $(this).val() == driverNm) {
			$(this).attr("selected", true);
			$("#vhcl_no_txt").val($("#selVhclNo option:selected").text());
			return;
		}		
	});
}

/**
 * 점포 선택시 기사리스트 리셋
 */
function dp_resetDriverList(gubun) {
	var strCd = $("#str_cd_txt").val();	
	$("#str_cd option").each( function(){
		if( $(this).val() == strCd) {
			$(this).attr("selected",true);
			dp_setDriverListCombo(gubun);
			return;
		}		
	});
}

/**
 * 점포 선택시 기사리스트 리셋
 */
function dp_setDriverListCombo(gubun) {
    var str_cd = $('#str_cd option:selected').val().trim();
    $.post(
    		dp_getContextPath() + "/dpcommon/multiSelect.do",
            'gubun=driver&strCd=' + str_cd,
            function(json){
                dp_makeComboWithJson($('#selDriver'),json,gubun);
            },
            "json"
    );
}

/**
 * 휴대폰번호 - 붙여 표시
 */
function dp_makeCellNoTxt(mdn) {
	if ( mdn == null || mdn.trim().length == 0 ) {
		return "";
	}
	mdn = mdn.trim();
	var ilen = mdn.length;
	
	var telText1 = "";
	var telText2 = "";
	var telText3 = "";
	
	if ( ilen == 10 || ilen == 11 ) {
		telText1 = mdn.substring(0,3);
		telText2 = mdn.substring(3,ilen-4);
		telText3 = mdn.substring(ilen-4,ilen);	 
	} else {
		return mdn;
	}

	return telText1+"-"+ telText2+"-"+telText3;
}

function dp_driverInfoStr(drvNm, cellNo) {
	var drvInfo = "";
	if ( drvNm == null || drvNm.trim() == '' ) {
		drvInfo = "<b>기사가 지정되지 않았습니다.</b> &nbsp;&nbsp;";
	} else {	
		drvInfo = "<b>기사명 : " + drvNm + " &nbsp; 연락처 : " + dp_makeCellNoTxt(cellNo) + "</b> &nbsp;&nbsp";
	}
	return drvInfo;
}

// ---------------------- DP(배송계획) 추가 -----------------------//

// ajax 처리할때 mask 표현
$.extend({
	defaultAjaxOption : {
		type		: 'POST',
		data 		: '',
		dataType	: 'json',
		onsubmit	: true
	},
	maskAjax	: function (o) {
		
		var loadingDiv = $('<span style="position:absolute; left:0px; top:'+$(document).scrollTop()+'px; width:100%; height:100%; z-index:10000"></span>').appendTo($("body"));

		loadingDiv
			.ajaxStart(function(){				
				loadingDiv.mask("Waiting...");
			})
			.ajaxStop(function(){
				loadingDiv.unmask();
				loadingDiv.remove();
		});
		
		return $.ajax(o);
	}
});

// dcmoblie에서 사용될 view
function ajaxView(data){
	if(data.resultCode == -1){
		Common.showMessageRemove(data.resultMsg);
	}else if(data.resultCode == 0){
		$("body").html(data);
	}else if(data.resultCode == 1){
		if(data.resultMsg != undefined){
			Common.showMessageRemove(data.resultMsg);
		}
	}else{
		if(data.length < 1024){
			Common.showMessageRemove(data);
		}else{
			$("body").html(data);
		}
	}
}

// 엔터키
function enterKey(fnc){
	var ieKey = window.event.keyCode;
	
	if ( ieKey != 13 ){
		return;
	}
	
	eval(fnc+"();");
}

/**
 * hidden text 폼 요소를 클리어 합니다.
 * 
 * @param form
 * @param clearReadonly
 *            true이면 읽기만 가능한 요소도 클리어 합니다
 * @return
 */
function clearForm(form) {
	var	tform;
	
	if (form == null) {
		return;
	}
	
	if (typeof(form) == "string") {
		tform	= $("form[name=" + form +"]");
	} else {
		tform	= $(form);
	}
	$(":input", tform).each(function() {
		switch (this.type) {
			case "hidden":
			case "text":
				if ($(this).attr("clear")!="no") {
					this.value	= "";
				}
				break;
			default :
				// alert(this.type);
		}
	});
}

/**
 * 택배사별 화물추적 팝업
 * 
 * @param kocn_1
 * @param num
 */
function popupHodeco(kocn_1, num) {
	// 우체국택배
	if(kocn_1=='02'){ 
		var kocn_url = "http://service.epost.go.kr/trace.RetrieveRegiPrclDeliv.postal?sid1="+num;
		window.open(kocn_url,"kocnss");
	// 삼성택배
	}else if(kocn_1=='03'){
		var kocn_url="http://nexs.cjgls.com/web/service02_01.jsp?slipno="+num;
		window.open(kocn_url,"kocnss");
	// 현대택배
	}else if(kocn_1=='04'){
		var url = "http://www.hydex.net/ehydex/jsp/home/distribution/tracking/tracingView.jsp?InvNo="+num;
		window.open(url, "kocnss");
	// 로젠택배
	}else if(kocn_1=='05'){	
		var kocn_url="http://www.ilogen.com/iLOGEN.Web.New/TRACE/TraceNoView.aspx?gubun=slipno&slipno="+num;
		window.open(kocn_url,"kocnss");
	// 아주택배
	}else if(kocn_1=='06'){	
		var kocn_url="http://www.ajulogis.co.kr/common/asp/search_history_proc.asp?sheetno="+num;
		window.open(kocn_url,"kocnss");
	// KGB택배
	}else if(kocn_1=='07'){	
		var kocn_url="http://www.kgbls.co.kr/trace/default.asp?sendno="+num;
		window.open(kocn_url,"kocnss");
	// 한진택배
	}else if(kocn_1=='08'){	
		var kocn_url="http://www.hanjinexpress.hanjin.net/customer/plsql/hddcw07.result?wbl_num="+num;
		window.open(kocn_url,"kocnss");
	// 대신택배
	}else if(kocn_1=='09'){
		var billno1 = String(num).substring(0,4); 
		var billno2 = String(num).substring(4,7); 
		var billno3 = String(num).substring(7,13);
		var kocn_url="http://www.daesinlogistics.co.kr/daesin/jsp/d_freight_chase/d_general_process.jsp?billno1="+billno1+"&billno2="+billno2+"&billno3="+billno3;
		window.open(kocn_url,"kocnss");
	// 대한통운택배
	}else if(kocn_1=='10'){
	  	var kocn_url="https://www.doortodoor.co.kr/parcel/doortodoor.do?fsp_action=PARC_ACT_002&fsp_cmd=retrieveInvNoACT&invc_no="+num;
		window.open(kocn_url,"kocnss");
	// CJ HTH택배
	}else if(kocn_1=='11'){
		var kocn_url="http://nexs.cjgls.com/web/service02_01.jsp?slipno="+num;
		window.open(kocn_url,"kocnss");
	// 옐로우캡택배
	}else if(kocn_1=='12'){
		var kocn_url="http://www.yellowcap.co.kr/custom/inquiry_result.asp?INVOICE_NO="+num;
		window.open(kocn_url,"kocnss");
	// KT로지스택배
	}else if(kocn_1=='13'){
	   	var kocn_url="http://218.153.4.42/customer/cus_trace_02.asp?invc_no="+num+"&searchMethod="+"I";
		window.open(kocn_url,"kocnss");
	// 일양택배
	}else if(kocn_1=='14'){
		var kocn_url="http://www.ilyanglogis.com/functionality/tracking_result.asp?hawb_no="+num;
		window.open(kocn_url,"kocnss");
	// CJ GLS택배
	}else if(kocn_1=='15'){
		var kocn_url="http://nexs.cjgls.com/web/service02_01.jsp?slipno="+num;
		window.open(kocn_url,"kocnss");
	// 하나로 택배
	}else if(kocn_1=='16'){
		var kocn_url="http://www.hanarologis.com/branch/chase/listbody.html?a_gb=center&a_cd=4&a_item=0&fr_slipno="+num;
		window.open(kocn_url,"kocnss");
	// 동부택배
	}else if(kocn_1=='17'){
		var kocn_url="http://www.kglogis.co.kr/delivery/delivery_result.jsp?item_no="+num;
		window.open(kocn_url,"kocnss");
	// 천일택배
	}else if(kocn_1=='18'){
		var kocn_url="http://www.cyber1001.co.kr/HTrace/HTrace.jsp?transNo="+num;
		window.open(kocn_url,"kocnss");
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
};

/*
 * 로딩중 표시
 */
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
function hideLoadingMask(){
	$('#loadingLayer').remove();
	$('#loadingLayerBg').remove();
}

//금액 3자리마다 콤마
function addTxtComma(formnum){
	
    num1 = formnum.length;

    FirstNum = formnum.substr(0,1);
    FirstNum2 = formnum.substr(1,num1);

    if(FirstNum == "0"){ 
            return "0";
    return FirstNum2;
            formnum = FirstNum2;
    }
    loop = /^\$|,/g;
    formnum = formnum.replace(loop, "");	
    var fieldnum = '' + formnum;
    if(isNaN(fieldnum)){
        alert("숫자만 입력하실 수 있습니다.");
        
        return "";
    }else{
        var comma = new RegExp('([0-9])([0-9][0-9][0-9][,.])');
        var data = fieldnum.split('.');
        data[0] += '.';
        do {
            data[0] = data[0].replace(comma, '$1,$2');
        } while (comma.test(data[0]));

        if (data.length > 1){
            return data.join('');
        }else{
            return data[0].split('.')[0];
        }
    }
}
/**
 * 입력값에서 콤마를 없앤다.
 */
function removeComma(input) {
    return input.replace(/,/gi,"");
}

//순수 숫자 체크 INPUT박스(IE, FF)
function isOnlyNumberInput(e, decimal) {
    
	var key;
    var keychar;

    if (window.event) {
       // IE에서 이벤트를 확인하기 위한 설정
        key = window.event.keyCode;
    } else if (e) {
      // FireFox에서 이벤트를 확인하기 위한 설정
        key = e.which;
    } else {
        return true;
    }

    keychar = String.fromCharCode(key);
    if ((key == null) || (key == 0) || (key == 8) || (key == 9) || (key == 13)
            || (key == 27)) {
        return true;
    } else if ((("0123456789").indexOf(keychar) > -1)) {
        return true;
    } else if (decimal && (keychar == ".")) {
        return true;
    } else
        return false;
}

//라이센스 설정
/*
var domain = window.location.hostname;
var pref = domain.split('.')[0];
var ibleaders;
ibleaders = ibleaders || {};
ibleaders = {
    license: "W2FtSztPKCNzZTYxaDJxbiwPcEg7RSl2ODBgZShnOmA9NRF4A3MVaWl2OXY6LTtyfytjHGkTYwN3ZXk+bDFmaT4hIzcIYA8kE2o3NmQwZCB0"
};
*/

var domain = window.location.hostname;
var pref = domain.split('.')[0];

if (pref == 'limadmin')
	//운영. 20211124 적용. nhj.
	lKey = 'W2FtSztPKC5wbzcxYjJxbn9NNREsCHUne20iIGk5LSN3dF96FndSOnx2d2wtLH4rPDdyV3kAZwJweCM+bXpwI39vISIMJ1wuRStveXtpOnNoL2JgKxx9FXtJejtpPGEmfWU8bSt5Um8JIgEsamg/dGkudWMqZDYedEd7Emo4YCN6ZD16KHgqJAItDzRMN3old3M1N2IsdyBhD3VIKAhvOjkibGczZC1iJzQYPEUpSSd3cyw+e3c8bX5qJEgjQTNBJjhlfzVmM2k+IXk3FHRcPlVtMjR6N2A+dW8iL3JLOE47H34haC0qdiU9fnExbUsvU3AaNGEqfy1tLm9+aCt4Wy4ae082ezd2JSNlZzZtL2pAJUh7VjVnOHVsKDFlLHMjfw9gWHYUe2k8MmVnKGIyfyAvBThGKFIvbChreH4xZz9maT9LOFw2XjojbXt1PH5pOyFhcFI/Qjpdaik1ZSt6KmwveH1pSDpPIxR6ZzV/NXY/I2pocCsENEtkHzV+MmE5dW8pMnYubVAxXz0AMyk1ciA9YGc3cA==';
else
 	//2024-05-09 개발 서버 라이센스 키 발급 - 1년마다 갱신하여야 함(업체 : 아이비리더스)
	lKey = 'W2FtSztPKCNybDc2YjJxbn9NNREsCHUne20iIGk5LTV8c0ZsFmoUeWlzOS4layg+e2J8Fm5cch10YTIldTtuaHV1Oi4YaR59A3AoP35wPWxpNn9iaA17AHlAICFvPSt1dGI5L2AiFXINcQE8enE4a2hwPW8oYmweaUo4GXchay45PzU7bi9kMQc4TChWPH5iKiEpMmEzbSFkDmlGMFIzdCcneW4sIWstPTAROUY2UyZycjAwYy1gI2B3PkMnBm4TOj1nYC9nNmgiL2FtSDpCO0BlLXE8eHo6fGogMGhKPU8nEWZ7NGM0aD82ejZsP1d2Dj0AciJkPXIzLmhuaXUwD3VfJx1zeit4PXk5KShoOj0FMg5zC2c5czYpaW9+djF3PlQoE3JTKHw8fD4oKThsMjxuWWkaYglhKTAtYCNmOmUjOWFWIkgsG31weHs7OW0lP28hIhFtB2AAMylhJmE7ZDQ6YC5pQyMVfFl7OG8yKzNibT0ncDcXd086QG89YTF6LjYoLX0rNgJnBH9GbjpnbGN/MDhiNnQ/Bn9EaRBrMHcvNGosfzhpZjhcdUUxTil0ODpjejZkLH4oMF9gCyhXL3FvMSpjLWYkf2ggViJILBt9cHhiMmUsdD8pZm1MOVw7VXVzbSgrfyp1aCEvdFc7VDoVZXovay8zeHBldDc/QStUbho0YDc=';
	//작년꺼
	//lKey = 'W2FtSztPKCZxbTc3YjJxbn9NNREsCHUne20iIGk5LTV8c0ZsFmoUeWlzOS4layg+e2J8Fm5cch10YTIldTtuaHV1Oi4YaR59A3AoP35wPWxpNn9iaA17AHlAICFvPSt1dGI5L2AiFXINcQE8enE4a2hwPW8oYmweaUo4GXchay45PzU7bi9kMQc4TChWPH5iKiEpMmEzbSFkDmlGMFIzdCcneW4sIWstPTAROUY2UyZycjAwYy1gI2B3PkMnBm4TOj1nYC9nNmgiL2FtSDpCO0BlLXE8eHo6fGogMGhKPU8nEWZ7NGM0aD82ejZsP1d2Dj0AciJkPXIzLmhuaXUwD3VfJx1zeit4PXk5KShoOj0FMg5zC2c5czYpaW9+djF3PlQoE3JTKHw8fD4oKThsMjxuWWkaYglhKTAtYCNmOmUjOWFWIkgsG31weHs7OW0lP28hIhFtB2AAMylhJmE7ZDQ6YC5pQyMVfFl7OG8yKzNibT0ncDcXd086QG89YTF6LjYoLX0rNgJnBH9GbjpnbGN/MDhiNnQ/Bn9EaRBrMHcvNGosfzhpZjhcdUUxTil0ODpjejZkLH4oMF9gCyhXL3FvMSpjLWYkf2ggViJILBt9cHhiMmUsdD8pZm1MOVw7VXVzbSgrfyp1aCEvdFc7VDoVZXovay8zeHBldDc/TCtUbh00YDc=';
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
function loadIBSheetData(sheetName, targetUrl, curPage, formId, param, gubun) {

	// loading bar start...
	loadingMask();

	var rowsPerPage = $("#rowsPerPage").val();
	if(gubun == undefined) gubun = "0";
	
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
					setLMPaging(data.totalCount, rowsPerPage, data.pageIndex, 'goPage', 'pagingDiv', gubun);
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

//마우스 드래그, 우클릭 방지
$(document).ready(function(){
$(document).bind("contextmenu", function(e) {
return false;
});
/*$(document).bind('selectstart',function(e) {return false;}); 
$(document).bind('dragstart',function(){return false;}); */
});
