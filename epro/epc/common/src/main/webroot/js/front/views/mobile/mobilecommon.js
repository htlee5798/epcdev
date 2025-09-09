// COMMON ------------------------------------------------------------------------------ START //
var co = {};
/**
 * Ajax 공통 함수
 * @param url 요청 url
 * @param params 요청 파라미터
 * @param fnCallback callback함수
 * @param type 전송 type
 */

var _SERVER_BASE_URL_ = 'http://127.0.0.1/';

co.ajax = function(url, params, fnCallback, type, syn){
	var fnSuccess = typeof fnCallback === 'function' ? fnCallback : fnCallback.success;
	var jsonParams = JSON.stringify(params);
	jsonParams = encodeURIComponent(jsonParams);
	if (!type) type = 'POST';
	if (!syn) syn = true;
	$.ajax({
		async: syn,
		type: type,
		cache: true,
		url: url,
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
			'X-Requested-With': 'XMLHttpRequest'
		},
		data: params,
		success: function(data) {
			if(data && fnSuccess){
				fnSuccess(data);
			}

		},
		error: function(data, status, headers) {
			if(data && fnCallback.error){
				fnCallback.error(data, status, headers);
			}
		}
	});
};
co.include = function(url, params, fnCallback, type){
	var fnSuccess = typeof fnCallback === 'function' ? fnCallback : fnCallback.success;
	if (!type) type = 'POST';
	$.ajax({
		type: type,
		url: _SERVER_BASE_URL_ + url,
		data: params,
		success:function(data) {
			if(data && fnSuccess){
				fnSuccess(data);
			}
		},
		error: function(data, status, headers) {
			if(data && fnCallback.error){
				fnCallback.error(data, status, headers);
			}
		}
	});
};
co.cookie = {
	set : function(c_name,value, endDate){
		var expdate;
		if(endDate) {
			expdate = new Date();
			expdate.setDate(expdate.getDate() + endDate);
		} else {
			expdate = new Date();
			expdate.setTime(expdate.getTime() + (1000* 24 * 60 * 60 * 1));
		}
		document.cookie = c_name+"=" + value + "; path=/; expires=" + expdate.toGMTString();
	},
	get : function(cName) {
		cName = cName + '=';
		var cookieData = document.cookie;
		var start = cookieData.indexOf(cName);
		var cValue = '';
		if(start != -1){
			 start += cName.length;
			 var end = cookieData.indexOf(';', start);
			 if(end == -1)end = cookieData.length;
			 cValue = cookieData.substring(start, end);
		}
		return unescape(cValue);
	}
};

co.ajaxErrMsg = '죄송합니다. 통신 중 오류가 발생하였습니다.\n잠시 후 다시 시도해 주십시오.';
co.ajaxErrAlert = function(){
	co.alert(co.ajaxErrMsg);
};

var fn = {};


//숫자를 금액표현법으로 변환한다.
function makeMoneyStr(value) {

	var result = '';
	var negative = false;

	// 숫자면 변경
	if (typeof(value) == 'number') {
		negative = value < 0;
		value = Math.abs(value).toString();
	} else if (typeof(value) == 'string') {
		if (isNaN(value)) return value;

		if (value.indexOf('-') == 0) {
			negative = true;
			value = value.substring(1);
		}
	} else {
		return value;
	}

	var len = value.length;
	var comma = 0;

	for (;len > 0; len--, comma++) {
		if (comma != 0 && comma % 3 == 0) result = ',' + result;
		result = value.substring(len -1, len) + result;
	}

	return negative ? '-' + result : result;
};


function convertDataFormat(secDate,gb){

		var year = secDate.substr(0,4);
		var month = secDate.substr(4,2);
		var day = secDate.substr(6,2);
		var rtdate = year + gb + month + gb + day;

		return rtdate;
}

function returnDate(datevalue){
	//date 설정
	var edDay = new Date();
	var exDay = new Date();
	exDay.setDate(exDay.getDate() - datevalue);

	alert(getFormatDate(edDay));

	alert(getFormatDate(exDay));

}

function getFormatDate(date){
	var year = date.getFullYear();                                 //yyyy
	var month = (1 + date.getMonth());                     //M
	month = month >= 10 ? month : '0' + month;     // month 두자리로 저장
	var day = date.getDate();                                        //d
	day = day >= 10 ? day : '0' + day;                            //day 두자리로 저장
	return  year + '' + month + '' + day;
}

function showSpinner() {

	var loader = '<div id="wrapPageLoading">'
		+'<span class="ingpageloading">잠시만 기다려주세요.</span>'
		+'<script>$(\'#wrapPageLoading\').on(\'touchstart touchmove touchend scroll\', function() {return false;});</script>'
		+'</div>';

	$('body').append(loader);
}
function hideSpinner() {
	$('#wrapPageLoading').remove();
}

String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
}

Date.prototype.yyyymmdd = function() {
	  var mm = this.getMonth() + 1; // getMonth() is zero-based
	  var dd = this.getDate();

	  return [this.getFullYear(), !mm[1] && '0', mm, dd[1] && '0', dd].join(''); // padding
};

//우편번호 검색
function loadrpostcode() {
	showSpinner();
	sessionStorage.setItem( 'postpopupscrollposition', $( window ).scrollTop() );
	$('#wrapZipCode').toggleClass('active');
	$.get('/mobile/load/address/search.do').done(function(html) {
		$('#postsearch').html(html);
		$('html, body').toggleClass('masking');
		hideSpinner();
	});
}

//우편번호검색 닫기
function closerpostcode() {
	var scrollPosition = sessionStorage.getItem( 'postpopupscrollposition' ) === null ? 0 : sessionStorage.getItem( 'postpopupscrollposition' );
	$('html, body').removeClass('masking');
	$( window ).scrollTop( scrollPosition );
	$('#wrapZipCode').removeClass('active');
	
	sessionStorage.removeItem( 'postpopupscrollposition' );
}

function topBnrCall(cateCd,bnrId){
	var elm = $('#' + bnrId);
	if(elm.length < 1) {
		return;
	}

	$.ajax({
        timeout: 1000,
        data		: {'cateCd' : cateCd},
        url: "/mobile/ajax/getMobileTopBnr.do",
        dataType: "Json",
        //async : false ,
        success: function(data) {
        	var bnrInfo = "";
        	var target = "";

        	if (data!=  null) {

        		elm.empty();

    			if(data.LINK_METH_CD == "02"){
    				target = 'target="_new"';
    			}

    			bnrInfo = '<a href="'+ data.LINK_URL +'" class="banner" '+ target +'><img src="'+ data.IMG_BNR +'" alt="'+ data.ALT+'"></a>';
    			elm.html(bnrInfo);

    		}else{ // 등록된 배너가 없을때는 안보이도록 처리 (앱에서 여백이 생기는 현상 있음)
    			elm.css("display","none");
			}

        },
        error: function(xhr) {

        }
	});

}

function callAppScheme(scheme,isAppVal) {
	var appActionID = "appExecuteFrame";
	var actionFrame = document.createElement("IFRAME");

	actionFrame.id = appActionID;
	actionFrame.name = actionFrame.id;
	actionFrame.width = 0;
	actionFrame.height = 0;
	actionFrame.src = scheme;

	if(isAppVal == true){
	window.location.href = scheme ;
	//document.body.appendChild(actionFrame);
	setTimeout(function() {
		//document.body.removeChild(actionFrame);
	}, 100);
	}
};