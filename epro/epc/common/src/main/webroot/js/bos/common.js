var first = Number(location.href.indexOf(location.host)) - 2;
var urls = location.href.replace("//" + location.host + "/", "");
var context = "/" + urls.substring(first, urls.indexOf("/"));
/**
 * 문자열 왼쪽,오른쪽 공백을 제거.
 * 
 * @return {String} 공백제거된 문자열.
 */
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g, "");
}

/**
 * 해당문자열에 특정문자의 포함 여부 반환.
 * 
 * @param {String}
 *            findStr 찾을문자.
 * @return {Boolean} 특정문자포함여부
 */
String.prototype.contains = function(findStr) {
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
String.prototype.replaceAll = function(findStr, newStr) {
	return this.replace(new RegExp(findStr, "gi"), newStr);
}

/**
 * 해당문자열의 바이트 수를 반환.
 * 
 * @return {Number} 문자열의 바이트 수
 */
String.prototype.getBytes = function() {
	var size = 0;
	for (i = 0; i < this.length; i++) {
		var temp = this.charAt(i);
		if (escape(temp) == '%0D')
			continue;
		if (escape(temp).indexOf("%u") != -1) {
			size += 2;
		} else {
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
	return this + added;
}

/**
 * 
 * @see numberFormat
 * @Method Name : common.js
 * @since : 2012. 4. 10.
 * @author : jyLim
 * @version :
 * @Locaton :
 * @Description : #,### 포맷
 * @param
 * @return String
 * @throws
 * 
 */
String.prototype.numberFormat = function() {
	var strNum = this;
	var result = '';
	for (var i = 0; i < strNum.length; i++) {
		if (i && (strNum.length - i) % 3 == 0)
			result += ',';
		result += strNum.charAt(i);
	}
	return result;
}

/*
 * Number Help Function (extend Number Class using prototype)
 */
Number.prototype.numberFormat = function() {
	var strNum = this.toString();
	return strNum.numberFormat();
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
	isEmpty : function(str) {
		if (str == null)
			return true;
		return !(str.replace(/(^\s*)|(\s*$)/g, ""));
	},

	/**
	 * 네임스페이스 등록.
	 * 
	 * @param {String}
	 *            ns 네임스페이스
	 */
	registNamespace : function(ns) {
		var nsParts = ns.split(".");
		var root = window;

		for (var i = 0; i < nsParts.length; i++) {
			if (typeof root[nsParts[i]] == "undefined") {
				root[nsParts[i]] = new Object();
			}
			root = root[nsParts[i]];
		}
	},

	importJS : function(jsFile) {
		$.ajax({
			type : "GET",
			cache : false,
			url : "/js/" + jsFile,
			async : false,
			dataType : "script"
		});
	},
	
	/**
	 * 팝업 윈도우 화면의 상단에 위치.
	 * 
	 * @param {String}
	 *            targetUrl 팝업 윈도우의 내용을 구성하기 위한 호출 URL
	 * @param {String}
	 *            windowName 팝업 윈도우의 이름
	 * @param {Object}
	 *            properties 팝업 윈도우의 속성(넓이, 높이, x/y좌표)
	 */
	topPopupWindow : function(targetUrl, windowName, properties) {
		var childWidth = properties.width;
		var childHeight = properties.height;
		var childTop = 20; 
		var childLeft = (screen.width - childWidth) / 2;
		var popupProps = "width=" + childWidth + ",height=" + childHeight
				+ ", top=" + childTop + ", left=" + childLeft;
		if (properties.scrollBars == "YES") {
			popupProps += ", scrollbars=yes";
		}

		var popupWin = window.open(targetUrl, windowName, popupProps);
		popupWin.focus();
		return popupWin;
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
		var childTop = (screen.height - childHeight) / 2 - 50; // 아래가 가리는 경향이
																// 있어서 50을 줄임
		var childLeft = (screen.width - childWidth) / 2;
		var popupProps = "width=" + childWidth + ",height=" + childHeight
				+ ", top=" + childTop + ", left=" + childLeft;
		if (properties.scrollBars == "YES") {
			popupProps += ", scrollbars=yes";
		}

		var popupWin = window.open(targetUrl, windowName, popupProps);
		popupWin.focus();
		return popupWin;
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

		var loadingDiv = $("body", top.document);

		loadingDiv.ajaxStart(function() {
			loadingDiv.mask("Waiting...");
		}).ajaxStop(function() {
			loadingDiv.unmask();
		});

		if (top != self) {
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
			dobj = $("<div>").attr("id", dialogId).css("vertical-align",
					"middle").appendTo("body");
		}

		if (properties.scrollBarX != null)
			dobj.css("overflow-x", "auto");
		else
			dobj.css("overflow-x", "hidden");

		if (properties.scrollBarY != null) {
			dobj.css("overflow-y", "auto");
			childWidth += 20; // 스크롤바가 생긴만큼 늘려주고
		} else
			dobj.css("overflow-y", "hidden");

		$.ajax({
			url : dialogUrl,
			dataType : 'html',
			target : dialogId,
			success : function(data) {
				dobj.html(data);
				dobj.dialog({
					width : childWidth,
					height : childHeight,
					// draggable : false,
					resizable : false,
					modal : true,
					title : title,
					bgiframe : true,
					close : function() {
						if (properties.close)
							properties.close();
						dobj.remove();
					}
				});
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
	checkUploadFileNameSize : function(uploadFileName, limitSize) {
		if (!Common.isEmpty(uploadFileName)) {
			var index = uploadFileName.lastIndexOf("\\");
			if (index > -1) {
				uploadFileName = uploadFileName.substring(index + 1);
			}

			if (uploadFileName.getBytes() > limitSize) {
				Common.alertDialog("알림", "파일 명이 너무 길어요.");
				return false;
			}

			return true;
		} else {
			return false;
		}
	},

	/**
	 * toString
	 */
	toString : function() {
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
	viewMap : function(context, x, y, message) {
		Common.centerPopupWindow(context + '/bbs/locationMap.do?x=' + x + "&y="
				+ y + "&message=" + encodeURIComponent(message), 'mapPopup', {
			width : 500,
			height : 300
		});
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
	intervalDate : function(fromDate, toDate) {
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
	addDate : function(thisDate, v_date) {
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

	getToday : function() {
		var FORMAT = "-";
		var date = new Date();
		var yyyy = date.getFullYear().toString();
		var mm = (date.getMonth() + 1).toString().length < 2 ? "0"
				+ (date.getMonth() + 1) : (date.getMonth() + 1).toString();
		var dd = date.getDate().toString().length < 2 ? "0" + date.getDate()
				: date.getDate().toString();

		return yyyy + FORMAT + mm + FORMAT + dd;
	},

	formatDate : function(thisDate, format) {

		// 날짜 길이와 FORMAT이 있는지 체크
		if (thisDate.length != 8 || !isNumber(thisDate))
			return null;

		var yyyy = thisDate.substr(0, 4);
		var mm = thisDate.substr(4, 2);
		var dd = thisDate.substr(6, 2);

		return yyyy + format + mm + format + dd;
	},

	/**
	 * Alert 다이얼로그 띄움.
	 * 
	 * @param {String}
	 *            title 타이틀
	 * @param {String}
	 *            msg 내용
	 */
	alertDialog : function(title, msg, proc) {
		var dialogTag = "<div id='jQueryDialog' title=\"" + title + "\">" + msg
				+ "</div>";
		$(dialogTag).dialog({
			modal : true,
			buttons : {
				'확인' : function() {
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
	 * 
	 * @param {String}
	 *            msg 내용
	 * @param {String}
	 *            procArray 작업 목록 배열
	 */
	alertProcDialog : function(msg, procArray) {
		var dialogTag = "<div id='jQueryDialog' title=\""
				+ Common.DIALOG_TITLE.NOTICE + "\">" + msg + "</div>";
		$(dialogTag).dialog({
			modal : true,
			buttons : {
				'확인' : function() {
					$(this).dialog('destroy').remove();
					for (var i = 0; i < procArray.length; i++) {
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
	choiceDialog : function(msg, proc) {
		var dialogTag = "<div id='jQueryDialog' title=\""
				+ Common.DIALOG_TITLE.CONFIRM + "\">" + msg + "</div>";
		$(dialogTag).dialog({
			modal : true,
			buttons : {
				'취소' : function() {
					$(this).dialog('destroy').remove();
				},
				'확인' : function() {
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
	makeTelTxt : function(object) {
		var mdn = object.val();
		var telText1 = mdn.substring(0, 3);
		var telText2 = mdn.substring(3, 7);
		var telText3 = mdn.substring(7, 11);

		return telText1 + "-" + telText2 + "-" + telText3;
	},

	checkObj : function checkObj(o, n) {
		if (o.val().length == 0) {
			o.addClass('ui-state-error');
			Common.alertDialog(Common.DIALOG_TITLE.NOTICE, "<br/><br/> " + n
					+ " 입력해주세요.", o.val(''));
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
			closeText : '닫기',
			prevText : '이전달',
			nextText : '다음달',
			currentText : '오늘',
			monthNames : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월',
					'9월', '10월', '11월', '12월' ],
			monthNamesShort : [ '1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월',
					'9월', '10월', '11월', '12월' ],
			dayNames : [ '일', '월', '화', '수', '목', '금', '토' ],
			dayNamesShort : [ '일', '월', '화', '수', '목', '금', '토' ],
			dayNamesMin : [ '일', '월', '화', '수', '목', '금', '토' ],
			dateFormat : 'yy-mm-dd',
			firstDay : 0,
			isRTL : false
		};

		// datepicker 설정.
		$.datepicker.setDefaults($.extend({
			showMonthAfterYear : false,
			showButtonPanel : true
		}, $.datepicker.regional['ko']));

		// 날자 검색 시작 일자 함수 및 달력 이미지 삽입
		$(function() {
			$("#searchStartDate")
					.datepicker(
							{
								showOn : 'button',
								showAnim : "show",
								buttonImage : '/images/bos/admin/ico_cal.gif',
								buttonImageOnly : true,
								buttonText : '시작일자',
								maxDate : new Date(),
								// minDate: "-1m",
								onSelect : function(dateText, inst) {
									var tempArr = dateText.split("-");
									var chYear = parseInt(tempArr[0], 10);
									var chMonth = parseInt(tempArr[1], 10);
									var chDay = parseInt(tempArr[2], 10);

									var d = new Date();
									var month = parseInt(d.getMonth()) + 1;
									var today = (d.getFullYear()
											+ "-"
											+ (month < 10 ? "0" + month : month)
											+ "-" + (d.getDate() < 10 ? "0"
											+ d.getDate() : d.getDate()));

									intervalDay = Common.intervalDate(dateText,
											today);

									if (intervalDay == 0) { // 당일
										// 년, 월, 일
										var minDate = new Date(parseInt(d
												.getFullYear()), parseInt(d
												.getMonth()), parseInt(d
												.getDate()));
										$("#searchEndDate").datepicker(
												'option', 'minDate', minDate);
										var maxDate = new Date(parseInt(d
												.getFullYear()), parseInt(d
												.getMonth()), parseInt(d
												.getDate()));
										$("#searchEndDate").datepicker(
												'option', 'maxDate', maxDate);
										$("#searchEndDate").val("");
									} else {
										// 년, 월, 일
										var minDate = new Date(chYear,
												chMonth - 1, chDay);
										$("#searchEndDate").datepicker(
												'option', 'minDate', minDate);

										// var maxDate = new Date(chYear,
										// chMonth-1, chDay+6);
										var maxDate = new Date();
										month = parseInt(maxDate.getMonth()) + 1;
										var maxDay = (maxDate.getFullYear()
												+ "-"
												+ (month < 10 ? "0" + month
														: month) + "-" + (maxDate
												.getDate() < 10 ? "0"
												+ maxDate.getDate() : maxDate
												.getDate()));

										// 금일 보다 +7 일이 크면 금일이 최고 크기
										if (Common.intervalDate(maxDay, today) < 0) {
											maxDate = new Date(parseInt(d
													.getFullYear(), 10),
													parseInt(d.getMonth(), 10),
													parseInt(d.getDate(), 10));
											$("#searchEndDate").datepicker(
													'option', 'maxDate',
													maxDate);
											$("#searchEndDate").val("");
										} else {
											$("#searchEndDate").datepicker(
													'option', 'maxDate',
													maxDate);
											$("#searchEndDate").val("");
										}
									} // end of if
								} // end of onSelect
							});
		});

		// 날자 검색 종료 일자 함수 및 달력 이미지 삽입
		$(function() {
			$("#searchEndDate").datepicker({
				showOn : 'button',
				showAnim : "show",
				buttonImage : '/images/bos/admin/ico_cal.gif',
				buttonImageOnly : true,
				buttonText : '종료일자',
				maxDate : new Date()
			});
		});
	}
};

/**
 * 이미지 리사이징
 */
function Resizing(img, vWidth, vHeigh) {
	if (vWidth != "") {
		img.width = vWidth;
	}

	if (vHeigh != "") {
		img.height = vHeigh;
	}
}

/**
 * 상품 검색 팝업
 */
function openProduct(mode, aprvYn, periDeli) {
	if (typeof aprvYn == "undefined")
		aprvYn = 'ALL';
	if (typeof periDeli == "undefined")
		periDeli = 'N';
	Common.centerPopupWindow(context + '/product/PBOMPRD0061.do?mode=' + mode
			+ '&aprvYn=' + aprvYn + '&periDeli=' + periDeli, '상품검색', {
		width : 800,
		height : 550,
		scrollBars : 'no'
	});
}

/**
 * 상품 검색 팝업 ( 다른성향 )
 */
function openProductOnOff(mode, onOffYn) {
	// onOffYn : 01 = 온오프('Y') , 02,03 = 온라인('N')
	if (typeof onOffYn == "undefined")
		onOffYn = 'N';
	Common.centerPopupWindow(context + '/product/PBOMPRD0061.do?mode=' + mode
			+ '&onOffYn=' + onOffYn, '상품검색', {
		width : 800,
		height : 550,
		scrollBars : 'no'
	});
}
/**
 * 상품 검색 팝업 ( 묶음, 추가구성 제외 )
 */
function openProductNotDealCtpd(mode) {
	Common.centerPopupWindow(context + '/product/PBOMPRD0061.do?mode=' + mode
			+ '&dealCtpdYn=N', '상품검색', {
		width : 800,
		height : 550,
		scrollBars : 'no'
	});
}
/**
 * 상품 검색 팝업 ( 묶음 제외 )
 */
function openProductNotDeal(mode) {
	Common.centerPopupWindow(context + '/product/PBOMPRD0061.do?mode=' + mode
			+ '&dealYn=N', '상품검색', {
		width : 800,
		height : 550,
		scrollBars : 'no'
	});
}
/**
 * 상품 검색 팝업 ( 온오프구분, 묶음 제외 )
 */
function openProductOnOffNotDeal(mode, onOffYn) {
	// onOffYn : 01 = 온오프('Y') , 02,03 = 온라인('N')
	Common.centerPopupWindow(context + '/product/PBOMPRD0061.do?mode=' + mode
			+ '&onOffYn=' + onOffYn + '&dealYn=N', '상품검색', {
		width : 800,
		height : 550,
		scrollBars : 'no'
	});
}
/**
 * 상품 검색 팝업 ( 추가구성 제외 )
 */
function openProductNotCtpd(mode) {
	Common.centerPopupWindow(context + '/product/PBOMPRD0061.do?mode=' + mode
			+ '&ctpdYn=N', '상품검색', {
		width : 800,
		height : 550,
		scrollBars : 'no'
	});
}
/**
 * 상품 검색 팝업 ( 온오프구분, 추가구성 제외 )
 */
function openProductOnOffNotCtpd(mode, onOffYn) {
	// onOffYn : 01 = 온오프('Y') , 02,03 = 온라인('N')
	Common.centerPopupWindow(context + '/product/PBOMPRD0061.do?mode=' + mode
			+ '&onOffYn=' + onOffYn + '&ctpdYn=N', '상품검색', {
		width : 800,
		height : 550,
		scrollBars : 'no'
	});
}
/**
 * 점포 검색 팝업
 */
function openStr(mode, id, isUnusedAllStoreCode, onlineYn,fedayYn) {
	id = id || '';
	isUnusedAllStoreCode = isUnusedAllStoreCode || false;
	
	//온라인 점포에서 찾기  디폴트 N (2021.07.06 추가)
	if (typeof onlineYn == "undefined")
		onlineYn = "N";
		
    //명절몰 점포에서 찾기  디폴트 N (2022.10.11 추가)
	if (typeof fedayYn == "undefined")
		fedayYn = "N";
	
	Common.centerPopupWindow(context + '/product/PBOMPRD0060.do?mode=' + mode+ '&id=' + id 
			+ '&isUnusedAllStoreCode=' + isUnusedAllStoreCode +'&onlineYn='+onlineYn+'&fedayYn='+fedayYn, '점포검색', {
		width : 1000,
		height : 700,
		scrollBars : 'YES'
	});
}
/**
 * 템플릿 검색 팝업
 */
function openTemplate(mode, categoryCd) {
	if (typeof categoryCd == "undefined")
		categoryCd = "";
	Common.centerPopupWindow(context + '/newTemplate/PBOMTEM0200.do?mode='
			+ mode + '&categoryTypeCd=' + categoryCd, '템플릿검색', {
		width : 1000,
		height : 550,
		scrollBars : 'no'
	});
}
/**
 * 달력 팝업
 */
// function openCal(inputName) {
// Common.centerPopupWindow('/html/bos/calendar.html?inputName='+inputName,
// 'lottemartCalendar', {width:250,height:250,scrollbars:'no'});
// }
// 기존 달력 팝업 버튼은 포커스 이동으로 달력이 나오도록 수정
function openCal(inputName) {

	try {
		// split
		var splitParam = inputName.split(".");
		param = splitParam[splitParam.length - 1]
		param = "#" + param;
		if ($(param).is(":focus") == false) {
			$(param).focus(); // input box가 readonly일 경우 포커스를 잘 못찾아 가는 것 같아서
								// 한번 더 실행하게 함.
			$(param).focus();
		}
	} catch (e) {
	}
}
// 달력등 화면 로드 시 등록하시기 위해 추가
function openCals(param) {
	param = "#" + param;
	fn_datepicker(param);
}

/*
 * common.js 로드 시 class속성에 day 존재하는 케이스는 달력으로 로드 함 달력수동입력 변경으로 인한 mask,
 * placeholder 값 삭제. 2016-05-23
 */
$(document).ready(function() {

	$(".day").each(function(i) {
		id = $(".day").eq(i).attr("id");
		openCals(id);
		$("#" + id).inputmask({
			mask : "y-1-2",
			placeholder : "YYYY-MM-DD",
			leapday : "-02-29",
			separator : "-",
			alias : "yyyy/mm/dd"
		});
	});

});

function fn_datepicker(params) {
	$(document).on(
			'focus',
			params,
			function() {
				$(this)
						.datepicker(
								{
									dateFormat : 'yy-mm-dd',
									prevText : '이전 달',
									nextText : '다음 달',
									monthNames : [ '1월', '2월', '3월', '4월',
											'5월', '6월', '7월', '8월', '9월',
											'10월', '11월', '12월' ],
									monthNamesShort : [ '1월', '2월', '3월', '4월',
											'5월', '6월', '7월', '8월', '9월',
											'10월', '11월', '12월' ],
									dayNames : [ '일', '월', '화', '수', '목', '금',
											'토' ],
									dayNamesShort : [ '일', '월', '화', '수', '목',
											'금', '토' ],
									dayNamesMin : [ '일', '월', '화', '수', '목',
											'금', '토' ],
									showMonthAfterYear : true,
									yearSuffix : '년',
									changeMonth : true,
									changeYear : true,
									showMonthAfterYear : true,
									showButtonPanel : true,
									currentText : '오늘 날짜',
									closeText : '닫기'
								});
			});
}

/**
 * 달력 팝업(callback 함수명 포함)
 */
function openCalWithCallback(inputName, callbackFuncName) {
	Common.centerPopupWindow('/html/bos/calendar.html?inputName=' + inputName
			+ '&callbackFunc=' + callbackFuncName, 'lottemartCalendar', {
		width : 250,
		height : 250,
		scrollBars : 'no'
	});
}

/**
 * 정수체크
 */
function isNumber(str) {
	var tempstr = "0123456789";
	if (str == "")
		return false;

	for (i = 0; i < str.length; i++) {
		if (tempstr.indexOf(str.substring(i, i + 1)) == -1) {
			return false;
			break;
		}
	}
	return true;
	// return /^\d+$/.test(str)
}

/*******************************************************************************
 * 시작일자 및 종료일자 유효성 체크
 ******************************************************************************/
function checkFeday(startDt, endDt) {

	var arrStartDt = new Array();
	arrStartDt[0] = startDt.substring(0, 4);
	arrStartDt[1] = startDt.substring(4, 6);
	arrStartDt[2] = startDt.substring(6, 9);

	var arrEndDt = new Array();
	arrEndDt[0] = endDt.substring(0, 4);
	arrEndDt[1] = endDt.substring(4, 6);
	arrEndDt[2] = endDt.substring(6, 9);

	arrStartDt[1] = (Number(arrStartDt[1]) - 1) + "";
	arrEndDt[1] = (Number(arrEndDt[1]) - 1) + "";

	var sDate = new Date(arrStartDt[0], arrStartDt[1], arrStartDt[2]);
	var eDate = new Date(arrEndDt[0], arrEndDt[1], arrEndDt[2]);

	if (sDate > eDate) {
		return false;
	}
	return true;
}

/*******************************************************************************
 * 콤보박스 reset
 ******************************************************************************/
function comboReset(obj) {
	var len = obj.length;
	var i;
	for (i = len - 1; i > 0; i--) {
		obj.options[i] = null;
	}
}

/*******************************************************************************
 * ajax 조회 후 콤보박스의 option부분을 생성한다.
 ******************************************************************************/
// 전시관리>표준분류카테고리할당 대/중/소/세분류 콤보박스
function comboCall(obj, data, all) {

	// 리셋
	comboReset(obj);

	if (data != null && data.length > 0) {

		for (var i = 0; i < data.length; i++) {
			if (i == 0) {
				if (all == 'ALL') {
					obj.options[i] = new Option("전체", "");
				} else {
					obj.options[i] = new Option("선택", "");
				}
			}
			obj.options[i + 1] = new Option(data[i].name, data[i].code);
		}
	}
}

// 전시관리>사이트카테고리>등록팝업>연동사이트영역콤보
function comboCall2(obj, data, all) {

	// 리셋
	comboReset(obj);

	if (data != null && data.length > 0) {

		for (var i = 0; i < data.length; i++) {
			if (i == 0) {
				if (all == 'ALL') {
					obj.options[i] = new Option("전체", "");
				} else {
					obj.options[i] = new Option("선택", "");
				}
			}
			obj.options[i + 1] = new Option(data[i].CD_NM, data[i].LET_2_REF);
		}
	}
}
/*
 * 공지사항 관련 함수
 */
function scrolling(objId, sec1, sec2, speed, height) {
	this.objId = objId;
	this.sec1 = sec1;
	this.sec2 = sec2;
	this.speed = speed;
	this.height = height;
	this.h = 0;
	this.div = document.getElementById(this.objId);
	this.htmltxt = this.div.innerHTML;
	this.div.innerHTML = this.htmltxt + this.htmltxt;
	this.div.isover = false;
	this.div.onmouseover = function() {
		this.isover = true;
	}
	this.div.onmouseout = function() {
		this.isover = false;
	}
	var self = this;
	this.div.scrollTop = 0;
	window.setTimeout(function() {
		self.play()
	}, this.sec1);
}
scrolling.prototype = {
	play : function() {
		var self = this;
		if (!this.div.isover) {
			this.div.scrollTop += this.speed;
			if (this.div.scrollTop > this.div.scrollHeight / 2) {
				this.div.scrollTop = 0;
			} else {
				this.h += this.speed;
				if (this.h >= this.height) {
					if (this.h > this.height
							|| this.div.scrollTop % this.height != 0) {
						this.div.scrollTop -= this.h % this.height;
					}
					this.h = 0;
					window.setTimeout(function() {
						self.play()
					}, this.sec1);
					return;
				}
			}
		}
		window.setTimeout(function() {
			self.play()
		}, this.sec2);
	}
}

/*
 * 로딩중 표시
 */
function loadingMask() {
	var childWidth = 128;
	var childHeight = 128;
	var childTop = (document.body.clientHeight - childHeight) / 2;
	var childLeft = (document.body.clientWidth - childWidth) / 2;

	var loadingDiv = $(
			'<span id="loadingLayer" style="position:absolute; left:'
					+ childLeft
					+ 'px; top:'
					+ childTop
					+ 'px; width:100%; height:100%; z-index:10001"><img id="ajax_load_type_img" src="/js/jquery/res/loading.gif"></span>')
			.appendTo($("body"));
	loadingDiv.show();
	var loadingDivBg = $(
			'<iframe id="loadingLayerBg" frameborder="0" style="filter:alpha(opacity=0);position:absolute; left:0px; top:0px; width:100%; height:100%; z-index:10000"><img id="ajax_load_type_img" src="/js/jquery/res/loading.gif"></iframe>')
			.appendTo($("body"));
	loadingDivBg.show();
}

/*
 * 로딩바 감추기
 */
function hideLoadingMask() {
	$('#loadingLayer').remove();
	$('#loadingLayerBg').remove();
}

/**
 * 
 * @see roundN
 * @Method Name : common.js
 * @since : 2012. 2. 22.
 * @author : jyLim
 * @version :
 * @Locaton :
 * @Description : 반올림 함수
 * @param n(숫자)
 *            digigts (자릿수)
 * @return any
 * @throws
 * 
 */
function roundN(n, digits) {
	if (digits >= 0)
		return parseFloat(n.toFixed(digits)); // 소수부 반올림

	digits = Math.pow(10, digits); // 정수부 반올림
	var t = Math.round(n * digits) / digits;

	return parseFloat(t.toFixed(0));
}

/**
 * 
 * @see getGridColumnKeyList
 * @Method Name : common.js
 * @since : 2012. 2. 22.
 * @author : jyLim
 * @version : 1.0
 * @Locaton :
 * @Description : 와이즈그리드 컬럼키들을 문자열로 만들어 반환한다. ex)
 *              TOT_ORD_CNT,INSUFF_ORD_CNT,INSUFF_ORD_YUL
 * @param gridObj(와이즈그리드
 *            객체) sIndex(시작인덱스) endIndex(끝인덱스) outOfIndexList(제외할 인덱스 리스트)
 * @return String
 * @throws
 * 
 */
function getGridColumnKeyList(gridObj, sIndex, eIndex, outOfIndexList) {
	var keyList = new Array();

	// alert(typeof outOfIndexList + ":" + outOfIndexList);

	if (outOfIndexList == null) {
		for (var i = sIndex; i <= eIndex; i++)
			keyList.push(gridObj.GetColHdKey(i));

	}

	else {
		for (var i = sIndex; i <= eIndex; i++) {
			if (outOfIndexList.indexOf(i) == -1)
				keyList.push(gridObj.GetColHdKey(i));
		}
	}

	keyList.join(',');

	// alert(typeof keyList + ":"+keyList+":"+keyList.length);

	return keyList.toString();
}

/**
 * 
 * @see getRateInSurmmary
 * @Method Name : common.js
 * @since : 2012. 3. 2.
 * @author : jyLim
 * @version :
 * @Locaton :
 * @Description :
 * @param
 * @return any
 * @throws
 * 
 */
function getRateInSurmmary(gridObj, strSummaryBarKey, dividend_colNm,
		divisor_colNm, mergeIndex) {

	// 피제수
	var dividend = gridObj.GetSummaryBarValue(strSummaryBarKey, dividend_colNm,
			mergeIndex, false);

	dividend = parseFloat(dividend);
	if (dividend == 0)
		return "0.0";

	// 제수
	var divisor = gridObj.GetSummaryBarValue(strSummaryBarKey, divisor_colNm,
			mergeIndex, false);
	divisor = parseFloat(divisor);
	if (divisor == 0)
		return "0.0";

	// alert(typeof divisor +":"+ divisor);

	// 피제수 / 제수 * 100
	var rate = dividend / divisor * 100;

	// 소수점 2자리 반올림
	return roundN(rate, 2);

}

function getAverageSurmmary(gridObj, strSummaryBarKey, dividend_colNm,
		mergeIndex, roundCnt) {

	// 피제수
	var dividend = gridObj.GetSummaryBarValue(strSummaryBarKey, dividend_colNm,
			mergeIndex, false);

	dividend = parseFloat(dividend);
	if (dividend == 0)
		return "0.0";

	// 제수
	var divisor = gridObj.GetRowCount();
	divisor = parseFloat(divisor);
	if (divisor == 0)
		return "0.0";

	var rate = dividend / divisor;

	// 소수점 roundCnt 자리 반올림
	return roundN(rate, roundCnt);

}

/**
 * LPAD
 * 
 * @param inStr
 * @param totalLen
 * @param strReplace
 * @return
 */
function lpad(inStr, totalLen, strReplace) {

	var strAdd = "";
	var diffLen = totalLen - inStr.length;

	for (var i = 0; i < diffLen; ++i) {
		strAdd += strReplace;
	}
	return strAdd + inStr;
}

function getDateFormatted(yyyymmdd, FORMAT) {
	return yyyymmdd.substring(0, 4) + FORMAT + yyyymmdd.substring(4, 6)
			+ FORMAT + yyyymmdd.substring(6, 8);
}

// ///////////////////////////////////////////////////////////////////////////////////////
// 여기서부터 김남갑이 작성 /////////////////////////////////////////////////////////////

// 대,중,소분류 선택시 해당 콤보박스 데이터 가져오기
// form name이 반드시 'dataForm' 이어야 함.
function categoryChange(gubun, lUrl, mUrl) {

	var formQueryString = $('*', '#dataForm').fieldSerialize();
	var targetUrl = "";

	// 대,중,소 어떤 콤보박스가 전체를 선택하면 하위 콤보박스 리셋
	if (document.dataForm.selDaeGoods.value == "") {
		comboReset(document.dataForm.selJungGoods);
		if(typeof document.dataForm.selSoGoods != 'undefined'){
			if (mUrl)
				comboReset(document.dataForm.selSoGoods);
		}

	}

	if (document.dataForm.selJungGoods.value == "") {
		if(typeof document.dataForm.selSoGoods != 'undefined') {
			if (mUrl)
				comboReset(document.dataForm.selSoGoods);
		}
	}

	if (gubun == "dae") {
		targetUrl = lUrl;
		comboReset(document.dataForm.selJungGoods);
		if(typeof document.dataForm.selSoGoods != 'undefined'){
			if (mUrl)
				comboReset(document.dataForm.selSoGoods);
		}
	} else if (gubun == "jung") {
		targetUrl = mUrl;
		if(typeof document.dataForm.selSoGoods != 'undefined'){
			if (mUrl)
				comboReset(document.dataForm.selSoGoods);
		}
	}

	$.ajax({
		type : 'POST',
		url : targetUrl,
		data : formQueryString,
		success : function(data) {
			try {
				if (data.comboNm == "jung") {
					comboCall(document.dataForm.selJungGoods, data.jungCdList,
							'ALL');
				} else if (data.comboNm == "so") {
					comboCall(document.dataForm.selSoGoods, data.soCdList,
							'ALL');
				}
			} catch (e) {
			}
		},
		error : function(e) {
			alert(e);
		}
	});
}

// 숫자 3자리마다 컴마 찍어주기
function setComma(orgNum) {
	return orgNum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// IBSheet 부분 ===================================================

/*
 * $.extend($.fn, {
 * 
 * ajaxFile : function(options) { var config = { type: 'POST', dataType: 'json',
 * timeout: 0, error: function(request, status, error) { alert("status: " +
 * status + ", error: " + error); } }; $.extend(config, options);
 * this.ajaxSubmit(config); },
 * 
 * enter : function(func) { $(this).bind('keypress', function(e) { if (e.keyCode ==
 * 13) { func.apply(this, [e]); e.preventDefault(); // do for IE dialog close
 * bug. } }); return this; } }); // eof $.fn extends
 */

// ** 라이센스 설정
var domain = window.location.hostname;
var pref = domain.split('.')[0];
// 마이그레이션서버 라이센스적용
if (pref == 'limadmin')
	///2018-04-18 https 프로토콜이 적용된 운영 라이센스 키
	///lKey = 'W2FtSztPKC5ybTc6YTJxbn9NNREsCHUne20iIGk5LSN3dF96FndSOnx2d2wtLH4rPDdyV3kAZwJweCM+bXpwI39vISIMJ1wuRStveXtpOnNoL2JgKxx9FXtJejtpPGEmfWU8bSt5Um8JIgEsamg/dGkudWMqZDYedEd7Emo4YCN6ZD16KHgqJAItDzJLKGcibG4yMX42cSN/D2BYdhR7aTwyY2YoZjd/IS4ZJkM1TDppZilwOX97I2B2Ik0jQTJdOzhlYC57OGxlcy9sVjJBJUFsMytnMGc+cmkhLmlDIxV8WXtkMX82cz4/ZHAqd0oqTnEULCRtLDhtYDYzaTJ2RCgfZ1I3ZzVtODd7IW8+L2ZbYA41VzRhOHJsKSxqKDd6LA9wSDRNNmgkJmRhKGUyfj0gAXwfe1Igd20tNn8xYT9haT5WN1hyB2kjfWs0ZTNoIzVhdlI4QjtAZS1xPHhlbHdqJDF+SDlT';
	//lKey = 'W2FtSztPKC5zbTc0YTJxbn9NNREsCHUne20iIGk5LSN3dF96FndSOnwsd3A5f3s7azV+GiMGdA54aSMlbjgsNHR1JzEdeFw7VS9zcTx4en0pOD51NBhkBGBROzkuP2wqdG09K2hsVyERKlpuLCp6LSo1ZCwzcyMHfl8hE297ait6aTA4bi4vf1AhVHMRensjcHM1N34xdyB+FGhDLVM9NmJ8PigpfzlkIDQYPFk0SSdoaDE1fixuYSUseQUiQDdBJjh5YjVmLHIjKnxsRngHYAcjKC9uNmQ+dW8+MnJLJ1UmFHt6OiFxM3hwf3M1bUsvT20aNH4xYiZodT1yM3UqFjQBb00yezd2OT5lZyl2MmFFfhp3DXI8am1tNy1jNm0hZBJ0RjBNKGkseCIgbikseygqGiZEM1Mmcm4tMGMyez5rKGUNZQ4pWSQ9eWMvZjdoISp8bEZ4B2AHIygvbjJkIWhpPjNzVzlQOwpuJXQlbTs/OWFzKnFWKVRwHjd/KmpnMy5vfmgreF8qGntOLWcxbCc8fno9MnQkAi0Qe0g0YCZ0bDYx';
	//2021 11 24. nhj.
	lKey = 'W2FtSztPKC5wbzcxYjJxbn9NNREsCHUne20iIGk5LSN3dF96FndSOnx2d2wtLH4rPDdyV3kAZwJweCM+bXpwI39vISIMJ1wuRStveXtpOnNoL2JgKxx9FXtJejtpPGEmfWU8bSt5Um8JIgEsamg/dGkudWMqZDYedEd7Emo4YCN6ZD16KHgqJAItDzRMN3old3M1N2IsdyBhD3VIKAhvOjkibGczZC1iJzQYPEUpSSd3cyw+e3c8bX5qJEgjQTNBJjhlfzVmM2k+IXk3FHRcPlVtMjR6N2A+dW8iL3JLOE47H34haC0qdiU9fnExbUsvU3AaNGEqfy1tLm9+aCt4Wy4ae082ezd2JSNlZzZtL2pAJUh7VjVnOHVsKDFlLHMjfw9gWHYUe2k8MmVnKGIyfyAvBThGKFIvbChreH4xZz9maT9LOFw2XjojbXt1PH5pOyFhcFI/Qjpdaik1ZSt6KmwveH1pSDpPIxR6ZzV/NXY/I2pocCsENEtkHzV+MmE5dW8pMnYubVAxXz0AMyk1ciA9YGc3cA==';
else if (pref == 'bosmig')
	lKey = 'W2FtSztPKCNybjY7YTJxbn9NNREsCHUne20iIGk5LTZ7d1hnFShSLzYod2wtLH4rPDdyV3kAZwJweCM+bXplL3xoPCFTJ0lkGytveWEyZmxwaykxcEg5Uw==';
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
var Ibs = { // Theme Setting
	ThemeCode : 'BL', // 'GM3',
	ThemeName : 'Blue', // 'Main3'
	HeaderHeight : 20
};

var RETURN_IBS_OBJ; // Global Variable
// IBSheet Data Display
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
		type : 'POST',
		url : targetUrl,
		data : param,
		success : function(data) {
			if (typeof (data) == 'string') {
				alert('세션이 종료되었습니다.');
				top.location.href = "/bos/common/login/sessionOutView.do";
			} else if (data.result) {
				RETURN_IBS_OBJ = data;
				sheetName.LoadSearchData(data.ibsList);
				var len = (data.ibsList) ? eval(data.ibsList).length : 0;
				if (len == 0) {
					$('#resultMsg').text('해당 자료가 없습니다.');
				} else {
					$('#resultMsg').text('정상적으로 조회되었습니다.');
				}

				if( typeof data.rowsPerPage  != "undefined" ){
					$("#rowsPerPage").val(data.rowsPerPage);
					rowsPerPage = $("#rowsPerPage").val();
				}
				
				if (rowsPerPage) {
					sheetName.SetPageCount(rowsPerPage);
				}

				if (curPage && data.pageIndex && data.gubun) {
					setLMPaging(data.totalCount, rowsPerPage, data.pageIndex,
							'goPage', 'pagingDiv', data.gubun);
				} else if (curPage && data.pageIndex) {
					setLMPaging(data.totalCount, rowsPerPage, data.pageIndex,
							'goPage', 'pagingDiv');
				}

			} else {
				alert(data.errMsg);
				$('#resultMsg').text('조회 중 에러가 발생했습니다.');
			}
			// loading bar end.
			hideLoadingMask();
		},
		error : function(e) {
			// loading bar end.
			$('#resultMsg').text('요청처리를 실패했습니다.');
			hideLoadingMask();
			alert(e);
		}
	});
}

// IBSheet Data Display (기존의 IBSheet에 조회결과를 추가하여 표시)
function loadIBSheetDataAppend(sheetName, targetUrl, curPage, formId, param) {

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
		type : 'POST',
		url : targetUrl,
		data : param,
		success : function(data) {
			if (typeof (data) == 'string') {
				alert('세션이 종료되었습니다.');
				top.location.href = "/bos/common/login/sessionOutView.do";
				
			} else if (data.result) {
				RETURN_IBS_OBJ = data;
				sheetName.LoadSearchData(data.ibsList, {
					Append : 1
				});
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
					setLMPaging(data.totalCount, rowsPerPage, data.pageIndex,
							'goPage', 'pagingDiv');
				}

			} else {
				alert(data.errMsg);
				$('#resultMsg').text('조회 중 에러가 발생했습니다.');
			}
			// loading bar end.
			hideLoadingMask();
		},
		error : function(e) {
			// loading bar end.
			$('#resultMsg').text('요청처리를 실패했습니다.');
			hideLoadingMask();
			alert(e);
		}
	});
}

// IBSheet 그리드데이터 엑셀 데이터 다운로드
function excelDataDown(mSheet, fileNm, hideCol) {
	mSheet.SetWaitImageVisible(1); // 처리중 로딩바가 보이게 한다.

	var downCols = "";
	if (hideCol)
		downCols = makeHiddenSkipCol(mSheet, hideCol);

	mSheet.Down2Excel({
		'FileName' : fileNm,
		Merge : 1,
		Multipart : 0,
		HiddenColumn : true,
		DownCols : downCols
	});
	mSheet.SetWaitImageVisible(0); // 처리중 로딩바가 안보이게 한다.
}

// IBSheet 엑셀양식 다운로드
function excelFormDown(mSheet, fileName, headerRows, hideCol) {
	var hdRow = '0';
	for (var i = 1; i < headerRows; i++) {
		hdRow += '|' + i;
	}

	var downCols = "";
	if (hideCol)
		downCols = makeHiddenSkipCol(mSheet, hideCol);
	mSheet.SetWaitImageVisible(1); // 처리중 로딩바가 보이게 한다.
	mSheet.Down2Excel({
		"FileName" : fileName,
		Merge : 1,
		Multipart : 0,
		DownRows : hdRow,
		DownCols : downCols
	});
	mSheet.SetWaitImageVisible(0); // 처리중 로딩바가 안보이게 한다.
}

// 감춰진 컬럼이나, Seq,Status,DelCheck 타입의 컬럼은 제외
function makeHiddenSkipCol(sObj, hideCol) {
	var hCols = "";
	if (hideCol)
		hCols = hideCol.split("|");
	var lc = sObj.LastCol();
	var colsArr = new Array();
	for (var i = 0; i <= lc; i++) {
		var colType = sObj.GetCellProperty(0, i, "Type");
		var colName = sObj.GetCellProperty(0, i, "SaveName"); // SaveName
		// Hidden이 아니면 넣는다.
		if (0 == sObj.GetColHidden(i) && colType != "Seq"
				&& colType != "Status" && colType != "DelCheck") {
			if (hideCol && hCols.indexOf(colName) < 0) {
				colsArr.push(i);
			}
		}
	}
	return colsArr.join("|");
}

// 페이징 없이 서버에서 전체데이터 엑셀 다운로드(검색조건 적용)
function directExcelDown(mSheet, fileName, url, formId, param, hideCol) {

	var mParam = new Object();
	mParam.Multipart = 0;
	mParam.Merge = 1;
	mParam.URL = url;
	mParam.FileName = fileName;
	mParam.DownCols = makeHiddenSkipCol(mSheet, hideCol);

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

function directExcelDownByPost(mSheet, fileName, url, formId, param, hideCol) {

	var mParam = new Object();
	mParam.Multipart = 0;
	mParam.ExtendParamMethod = 'POST';
	mParam.Merge = 1;
	mParam.URL = url;
	mParam.FileName = fileName;
	mParam.DownCols = makeHiddenSkipCol(mSheet, hideCol);

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
		var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
}

/*
 * checkDate2로 대체됨. 2016.06.03
 */
// function checkDate(obj){
// var checkDataStr = document.getElementById(obj).value.replaceAll("-","");
//	
// if(!isNaN(checkDataStr)){
// var startDt = $('#startDt').val().replaceAll("-","");
// var endDt = $('#endDt').val().replaceAll("-","");
// if( startDt > endDt ) alert("종료일자가 시작일자 보다 이전입니다. 확인 해 주세요.");
// }
// }
/*
 * Method Name : 검색조건 dateformat 유형2 달력수동입력 변경으로 인한 함수추가. obj, obj2 : 날짜비교대상
 * gubun : START, END
 */
function checkDate2(obj, obj2, gubun) {
	var checkDataStr = document.getElementById(obj).value.replaceAll("-", "");
	var startDt = "";
	var endDt = "";

	if (!isNaN(checkDataStr)) {
		if (gubun == "START") {
			startDt = checkDataStr;
			endDt = document.getElementById(obj2).value.replaceAll("-", "");
		} else {
			startDt = document.getElementById(obj2).value.replaceAll("-", "");
			endDt = checkDataStr;
		}

		if (startDt.length == 8 && endDt.length == 8) {
			if (startDt > endDt)
				alert("종료일자가 시작일자 보다 이전입니다. 확인 해 주세요.");
		}
	}
}

/*
 * 첨부파일에 대한 확장자 값을 리턴
 */
function fn_getExtension(ext) {
	var jbSplit = ext.split('.');

	var i = jbSplit.length;
	if (i > 0) {
		return jbSplit[i - 1];
	}
	return "";
}

/*
 * 첨부파일에 대한 확장자 값이 jpg 이면 true 아니면 false 를 리턴
 */
function fn_checkExtension(ext) {

	var tem = fn_getExtension(ext);

	if (tem.toLowerCase() == 'jpg') {
		return true;
	}
	return false;
}

/*
 * 첨부파일에 대한 확장자 값이 jpg,gif,png 이면 true 아니면 false 를 리턴
 */
function fn_checkExtension2(ext) {

	var tem = fn_getExtension(ext);

	if (tem.toLowerCase() == 'jpg' || tem.toLowerCase() == 'gif'
			|| tem.toLowerCase() == 'png') {
		return true;
	}
	return false;
}

function fn_productDetail(mallDivnCd, prodCd) {
	var targetUrl = "";

	// 몰구분코드가 '00002'(토이저러스몰)인 경우
	if (mallDivnCd == "00002") {
		targetUrl = "http://toysrus.lottemart.com/product/ProductDetailAdmin.do?ProductCD="
				+ prodCd + "&strCd=307&approvalGbn=N";
	}
	// 몰구분코드가 '00001'(롯데마트몰)인 경우
	else {
		targetUrl = "http://www.lottemart.com/product/ProductDetail.do?ProductCD="
				+ prodCd + "&strCd=307&approvalGbn=N&previewYN=Y";
	}

	Common.centerPopupWindow(targetUrl, 'productView', {
		width : 970,
		height : 650,
		scrollBars : 'YES'
	});
}

// 이미지 상세 팝업
function imageDetailPrevView(prodCd) {
	var targetUrl = context + '/product/imageDetailPrevView.do?prodCd='
			+ prodCd;
	Common.centerPopupWindow(targetUrl, 'insDealProductView', {
		width : 505,
		height : 540,
		scrollBars : 'NO'
	});
}

// URL 체크 로직
function ValidUrl(str) {
	if (/http:\/\/[A-Za-z0-9\.-]{3,}\.[A-Za-z]{2,3}/.test(str)) {
		return true;
	} else if (/https:\/\/[A-Za-z0-9\.-]{3,}\.[A-Za-z]{2,3}/.test(str)) {
		return true;
	} else	{
		return false;
	}
	
	return false;
}

function calsDates(startDate, endDate) {
	var strDate1 = startDate;
	var strDate2 = endDate;
	var arr1 = strDate1.split('-');
	var arr2 = strDate2.split('-');
	var dat1 = new Date(arr1[0], arr1[1], arr1[2]);
	var dat2 = new Date(arr2[0], arr2[1], arr2[2]);

	var diff = dat2 - dat1;
	var currDay = 24 * 60 * 60 * 1000;// 시 * 분 * 초 * 밀리세컨
	var currMonth = currDay * 30;// 월 만듬
	var currYear = currMonth * 12; // 년 만듬

	var resultDataObj = new Object();
	resultDataObj.years = parseInt(diff / currYear)
	resultDataObj.months = parseInt(diff / currMonth)
	resultDataObj.days = parseInt(diff / currDay)
	return resultDataObj;
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