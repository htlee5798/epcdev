///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * JS 페이지 공통 환경 변수.
 * layout.jsp에서 아래 형식으로 사용해서
 * 컨텍스트패스와 이미지 서버 url을 지정해서 담습니다.
 * 필요한 변수들 정의 하시면 됩니다.
 * 
	$(document).ready(function(){
		setContextPath("${pageContext.request.contextPath }");
	});
 */
var env = {
	contextPath : "",
	serviceName : "",
	enable : ""
};

function setContextPath(paramContextPath){
	env.contextPath = paramContextPath;
}
function setServiceName(serviceName) {
	env.serviceName = serviceName;
}
function setEnable(yn) {
	env.enable = yn;
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


/**
 * ## jQuery Ajax default 설정. ##
 * - jQuery를 이용한 모든 Ajax call 의 beforeSend에 blockUI를 걸어서 메세지를 띄어줍니다.
 * - Ajax call의 모든결과(stop/error/success)에 blockUI를 해제시켜 줍니다.
 * 
 * ## jQuery POST Ajax call ## 
 * - JSon 형식의 ajax call에는 postJson을..
 * - HTML 형식의 ajax call에는 postRequest를 사용하시면 됩니다.
 * 
 * ## beforeSend에 blockUI를 걸고싶지 않은 경우 ## 
 * 	$.ajax({
		type: "GET",
		url: "URL",
		beforeSend: function(xhr) {},
		success: function(data) {
			// todo
		}
	});
 *	형식으로 beforeSend를 재정의해서 사용하시면 됩니다.
 *
 * ## TIPS ##
 * - 일반 submit 호출 전에 
 * 		$.blockUI({ message: '<h4>잠시만 기다려 주세요.</h4>' });
 *   위의 구문을 사용하시면.. 이중호출 방지 및 처리중 메세지 띄우는 효과가 있답니다~~ ㅋㅋ
 *
 *	사용에 문의 있으시면 언제든 물어보셔도 됩니다~
 */
$().ajaxStop($.unblockUI); 
$().ajaxError($.unblockUI); 
$().ajaxSuccess($.unblockUI); 

$.ajaxSetup({
	global: true,
    beforeSend: function(xhr) { 
		//$.blockUI({ message: '<h4>잠시만 기다려 주세요.</h4>' });  
	} ,
	success: function(data) {
		//$.unblockUI();
	}
});	

$.postJSON = function(url, data, callback) {
	$.post(url, data, callback, "json");
};

$.postRequest = function(url, param, callback) {
	$.post(url, param, callback, "html");
};

/**
 * String trim
 */
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
}

/**
 * String ltrim
 */
String.prototype.ltrim = function() {
	return this.replace(/^\s+/,"");
}

/**
 * String rtrim
 */
String.prototype.rtrim = function() {
	return this.replace(/\s+$/,"");
}

/**
 * String replaceAll
 */
String.prototype.replaceAll = function(str1, str2){
	var temp_str = "";
	if (this.trim() != "" && str1 != str2){
		temp_str = this.trim();
		
		while (temp_str.indexOf(str1) > -1){
			temp_str = temp_str.replace(str1, str2);
		}
	}
	return temp_str;
}

/**
 * 지정된 이름, 값으로 쿠키를 지정된 만료일까지 설정.
 * @param {String} name			쿠키 이름
 * @param {String} value		쿠키 값
 * @param {Date} expires		만료일
 */
function setCookie (name, value, expires) {
	document.cookie = name + "=" + escape (value) + "; path=/; expires=" + expires.toGMTString();
}

/**
 * 쿠키 값 가져옴.
 * @param {String} name	쿠키 이름
 */
function getCookie(Name) {
	var search = Name + "="
	if (document.cookie.length > 0) {                  // 쿠키가 설정되어 있다면
		offset = document.cookie.indexOf(search)
		if (offset != -1) {                            // 쿠키가 존재하면
			offset += search.length
			end = document.cookie.indexOf(";", offset)
			if (end == -1)	end = document.cookie.length

			return unescape(document.cookie.substring(offset, end))
		}
	}

	return "";
}

/**
 * 쿠키 삭제.
 * @param {String} cookieName	쿠키 이름
 */
function deleteCookie(cookieName){
	var expireDate = new Date();	
	expireDate.setDate( expireDate.getDate() - 1 );
	document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString() + "; path=/";
}

/**
 * 스트링 널 체크.
 * @param {String} str
 */
function isNull(str){
	if(str == null) return true;
	return !(str.replace(/(^\s*)|(\s*$)/g, ""));
}

/**
 * Object value 의 넘버 체크.
 * @param {Object} str
 */
function checkNumber(obj){
	var valueString = obj.value;
	var temp = '';
	for(i = 0; i<valueString.length; i++){
		code = valueString.charCodeAt(i);
		if(code > 47 && code < 58) temp += valueString.charAt(i);
	}
	obj.value = temp;
}

/**
 * IE의 버젼 번호 얻어옵니다.
 */
function getIEVer(){
	var retval = 0;
    var agent = navigator.userAgent.toLowerCase();
    var ver = agent.split(';');
    if(agent.indexOf('msie') != -1){
        retval = parseFloat(ver[1].slice(6, ver[1].length));
	}
	return retval;
}

/**
 * event 발생위치의 마우스 포지션
 * @param {event} event			이벤트 객체
 */	
function getMousePosition(event){
	var x = (getIEVer()<=0) ? event.pageX: window.event.clientX + document.documentElement.scrollLeft;
	var y = (getIEVer()<=0) ? event.pageY: window.event.clientY + document.documentElement.scrollTop;		
	return {"x":x,"y":y};
}

/**
 * Object 보임/숨김.
 * @param {boolean} flag 		true/false - true:보임
 * @param {String} objectId		object id
 * @param {event} event			이벤트 객체(이벤트 발생시는 발생한 위치, 없을시엔 고정위치)
 */	
function setVisibleObject(flag, objectId, event){
    var tempObject = $("#"+objectId);
	if(flag){
		if(event){
			var pos = getMousePosition(event);
			var tempCss = {		
				top: pos.y + 5,
				left: pos.x + 5
			};
			tempObject.css(tempCss);
		}
		tempObject.show();	
	}else{
		tempObject.hide();
	}	
}

/**
 * 팝업 윈도우 화면의 중간에 위치.
 * @param {String} targetUrl	팝업 윈도우의 내용을 구성하기 위한 호출 URL
 * @param {String} windowName	팝업 윈도우의 이름
 * @param {Object} properties	팝업 윈도우의 속성(넓이, 높이, x/y좌표)
 */	
function centerPopupWindow(targetUrl, windowName, properties) {
	var childWidth = properties.childWidth;
	var childHeight = properties.childHeight;
	var childTop = (screen.height - childHeight) / 2 - 50;    // 아래가 가리는 경향이 있어서 50을 줄임
	var childLeft = (screen.width - childWidth) / 2;
	var popupProps = "width=" + childWidth + ",height=" + childHeight + ", top=" + childTop + ", left=" + childLeft;
	if (properties.scrollBars == "YES") {
		popupProps += ", scrollbars=yes";
	}

	var popupWin = window.open(targetUrl, windowName, popupProps);
	popupWin.focus();
}

/**
 * 팝업 레이어 생성.
 * @param {String} layerId
 */
function createPopupLayer(layerId){
	var layerHtml = "";
	if(navigator.userAgent.indexOf("MSIE 6")>-1 && navigator.userAgent.indexOf("MSIE 7")<0){
		layerHtml += "<div id=\"" + layerId + "\" class=\"popup_area\" style=\"position:absolute; display:none;\">";
	} else {
		layerHtml += "<div id=\"" + layerId + "\" class=\"popup_area\" style=\"position:fixed; display:none;\">";
	}
	layerHtml += "<div class=\"popup_top\"><img src=\"" + env.contextPath + "/images/popup/box_top.gif\" alt=\"\" /></div>";
	layerHtml += "<div class=\"popup_content_bg\">";
	layerHtml += "<div id=\"" + layerId + "_body\" class=\"popup_content\"></div>";
	layerHtml += "</div>";
	layerHtml += "<div class=\"popup_bottom\">";
	layerHtml += "<img src=\"" + env.contextPath + "/images/popup/box_bottom.gif\" alt=\"close\" usemap=\"#Map\" />";
	layerHtml += "<map name=\"Map\" id=\"Map\"><area shape=\"rect\" coords=\"356,1,406,17\" href=\"#\" onclick=\"javascript:closePopupLayer('" + layerId + "'); return false;\"/></map>";
	layerHtml += "</div>";
	layerHtml += "</div>";
	
	$("#layerArea").append(layerHtml);
}

/**
 * 레이어 팝업.
 * @param {String} targetLayerId  타겟 레이어 id
 * @param {String} parentLayerId  타겟 레이어의 parent 레이어 Id
 */
function showPopupLayer(targetLayerId, parentLayerId, screenTop){
	//alert("screenTop:" + screenTop);
	var targetLayer = $("#"+targetLayerId);
	var layerTop = (screen.height - targetLayer.height()) / 2 - 150;
	var layerLeft = (screen.width - targetLayer.width()) / 2;
	
	if (navigator.userAgent.indexOf("MSIE 6")>-1 && screenTop != undefined && screenTop != "") {
		layerTop = screenTop;
	}
	//alert("layerTop:" + layerTop + ", layerLeft:" + layerLeft + ", screen.height:" + screen.height);
	
	var zindex = 10;
	if(parentLayerId){
		var parentLayer = $("#"+parentLayerId);
		if(parentLayer.css("z-index") != "auto"){
			zindex = parseInt(parentLayer.css("z-index")) + 10;
		}
	}

	targetLayer.css("top", layerTop);
	targetLayer.css("left", layerLeft);
	targetLayer.css("z-index", zindex);
	targetLayer.fadeIn("fast");	
	setDimm(targetLayerId, true);
}

/**
 * 팝업 레이어 닫기
 * @param {String} targetLayerId  타겟 레이어 id
 */
function closePopupLayer(targetLayerId){
	$("#"+targetLayerId).fadeOut("slow");	
	$("#"+targetLayerId).remove();
	if( navigator.appVersion.indexOf( "MSIE 6" ) > -1 )	{
		$("#contsType").show();
		$("#genxPlayerVer").show();
		$("#sdkVer").show();
		$("#wpVmVer").show();
		$("#wmVmVer").show();
		$("#wgEngnVer").show();
	}
	setDimm(targetLayerId, false);
}

/**
 * 팝업 레이어 닫기 (반복적으로 가능한 레이어 닫기)
 * @param {String} targetLayerId  타겟 레이어 id
 */
function closePopLayer(targetLayerId){
	$("#"+targetLayerId).fadeOut("slow");	
	if( navigator.appVersion.indexOf( "MSIE 6" ) > -1 )	{
		$("#contsType").show();
		$("#genxPlayerVer").show();
		$("#sdkVer").show();
		$("#wpVmVer").show();
		$("#wmVmVer").show();
		$("#wgEngnVer").show();
	}
	setDimm(targetLayerId, false);
}


/**
 * 타겟 레이어의 딤드처리 설정/해제.
 * @param {String} targetLayerId	타겟 레이어 id
 * @param {Boolean} falg 			true/false
 */
function setDimm(targetLayerId, flag){
	if(flag){
		var htmlStr = "<div id=\"temp_"+targetLayerId+"\" class=\"dimmed\"></div>"
		$("#bodyFrame").append(htmlStr);
		
		if(document.body.clientHeight > document.documentElement.clientHeight){
			$("#temp_"+targetLayerId).css("height", document.body.clientHeight + "px");
		}
		$("#temp_"+targetLayerId).css("z-index", parseInt($("#"+targetLayerId).css("z-index"))-1);

		// 셀렉트박스 hidden 처리
		//$("select").css("visibility","hidden");
		var svn = document.getElementsByTagName("select");
		for (a=0; a<svn.length; a++) {
			if ($(svn[a]).offsetParent().attr("id") != targetLayerId) {
				svn[a].style.visibility = "hidden";
			}
		}
	}else{
		$("#temp_"+targetLayerId).remove();
		// 셀렉트박스 살리기
		//$("select").css("visibility","visible");
		var svn1 = document.getElementsByTagName("select");
		for (a=0; a<svn1.length; a++) {
			if ($(svn1[a]).offsetParent().attr("id") != targetLayerId) {
				svn1[a].style.visibility = "visible";
			}
		}
	}
}

/**
 * 영문 한글 변환.
 * @param {String} str 입력 문자열
 * @return {String} 한글로 변환된 문자열
 */
function convertEngCharToKor(str){
	var en_h = "rRseEfaqQtTdwWczxvg"; 
	var reg_h = "[" + en_h + "]"; 		
	
	var reg_b = "hk|ho|hl|nj|np|nl|ml|k|o|i|O|j|p|u|P|h|y|n|b|m|l";		
	
	var reg_f = "rt|sw|sg|fr|fa|fq|ft|fx|fv|fg|qt|r|R|s|e|f|a|q|t|T|d|w|c|z|x|v|g|";		
	var reg_exp = new RegExp("("+reg_h+")("+reg_b+")((?:"+reg_f+")(?=(?:"+reg_h+")(?:"+reg_b+"))|(?:"+reg_f+"))","g");
	return str.replace(reg_exp,_korReplace); 
}

/**
 * 주어진 문자열에서 영문 한글 변환.
 * @param {String} str 입력 문자열
 * @param {Object} 한글 초성에 해당하는 영문
 * @param {Object} 한글 중성에 해당하는 영문
 * @param {Object} 한글 종성에 해당하는 영문
 * @return {String} 한글로 변환된 문자열
 */
function _korReplace(str,h,b,f) { 
	var en_h = "rRseEfaqQtTdwWczxvg"; 
	var en_b = { 
		k:0,o:1,i:2,O:3,j:4,p:5,u:6,P:7,h:8,hk:9,ho:10,hl:11,y:12,n:13,nj:14,np:15,nl:16,b:17,m:18,ml:19,l:20 
	} 
	var en_f = { 
		"":0,r:1,R:2,rt:3,s:4,sw:5,sg:6,e:7,f:8,fr:9,fa:10,fq:11,ft:12,fx:13,fv:14,fg:15,a:16,q:17,qt:18,t:19,T:20,d:21,w:22,c:23,z:24,x:25,v:26,g:27 
	} 
	return String.fromCharCode(en_h.indexOf(h)*21*28 + en_b[b]*28 + en_f[f] + 44032); 
};

/**
 * 스트링이 한글인지 확인.
 * @param {String} str 문자열
 * @return {Boolean} 한글이면 true
 */
function isKorean(str){		
	var i;
	var ch;
	var retval = true;
	for (i=0;i<str.length;i++) {
		ch = escape(str.charAt(i)); 

		if (strCharByte(ch) != 2) {
			retval = false;
		}
	}
	return retval;
}

/**
 * 스트링 바이트 크기 반환.
 * @param {String} chStr 	문자열
 * @return {Number} 한글이면 2, 아니면 1
 */
function strCharByte(chStr) {
	if (chStr.substring(0, 2) == '%u') {
		if (chStr.substring(2,4) == '00')
			return 1;
		else
			return 2;        //한글
	} else if (chStr.substring(0,1) == '%') {
		if (parseInt(chStr.substring(1,3), 16) > 127)
			return 2;        //한글
		else
			return 1;
	} else {
			return 1;
	}
}

function popLayerAjaxCall(props, screenTop){	
	//alert(" popLayerAjaxCall - screenTop:" + screenTop);
	$.ajax({
		type: props.type,
		url: props.url,
		data: props.param,
		beforeSend: function(xhr) {},
		success: function(data) {
			createPopupLayer(props.layerId);
			$("#"+props.layerId+"_body").html(data);
			showPopupLayer(props.layerId, props.parentLayerId, screenTop);			
		}
	});
}

function popLayerAjaxCall2(props, screenTop){	
	//alert(" popLayerAjaxCall - screenTop:" + screenTop);
	$.ajax({
		type: props.type,
		url: props.url,
		data: props.param,
		beforeSend: function(xhr) {},
		success: function(data) {
			var layerHtml = "";
			if(navigator.userAgent.indexOf("MSIE 6")>-1 && navigator.userAgent.indexOf("MSIE 7")<0){
				layerHtml += "<div id=\"" + props.layerId + "\" class=\"popup_area\" style=\"position:absolute; display:none;\">";
			} else {
				layerHtml += "<div id=\"" + props.layerId + "\" class=\"popup_area\" style=\"position:fixed; display:none;\">";
			}
			layerHtml += "<div class=\"regist_pop_top\"><img src=\"" + env.contextPath + "/images/popup/regist_pop_top.gif\" alt=\"\" /></div>";
			layerHtml += "<div class=\"regist_pop_cont_area\">";

			layerHtml += "<div id=\"" + props.layerId + "_body\" class=\"popup_content\"></div>";

			layerHtml += "</div>";
			layerHtml += "<div class=\"popup_bottom\">";
			layerHtml += "<img src=\"" + env.contextPath + "/images/popup/regist_pop_bottom.gif\" alt=\"close\" usemap=\"#Map\" />";
			layerHtml += "<map name=\"Map\" id=\"Map\"><area shape=\"rect\" coords=\"533,12,585,29\" href=\"#\" onclick=\"javascript:closePopupLayer('" + props.layerId + "'); return false;\"/></map>";
			layerHtml += "</div>";
			layerHtml += "</div>";

			$("#layerArea").append(layerHtml);			
			$("#"+props.layerId+"_body").html(data);
			showPopupLayer(props.layerId, props.parentLayerId, screenTop);			
		}
	});
}

function popZipCodeList(zipcode1IdVal, zipcode2IdVal, addr1IdVal, addr2IdVal){
	var params = {
		zipcode1Id : isNull(zipcode1IdVal) ? 'zipcode1' : zipcode1IdVal,
		zipcode2Id : isNull(zipcode2IdVal) ? 'zipcode2' : zipcode2IdVal,
		addr1Id : isNull(addr1IdVal) ? 'addr1' : addr1IdVal,
		addr2Id : isNull(addr2IdVal) ? 'addr2' : addr2IdVal
	};
	var props = {
		type : "POST",
		url : env.contextPath + "/common/popZipCodeList.do",
		param : params,
		layerId : "popZipCodeList",
		parentLayerId : "container"
	};
	popLayerAjaxCall(props);
}

/**
 * 자주 쓰는 페이지 이동.
 * @return
 */
function gotoPage(page){
	var url = "";

	/*
	 * 로그인
	 */
	 if (page == "LOGIN") url = env.contextPath + "/login/login.do";
	/*
	 * 로그아웃
	 */
	else if (page == "LOGOUT") url = env.contextPath + "/login/logout.do";

	/*
	 * 회원가입
	 */
	else if (page == "REGIST") url = env.contextPath + "/member/registInfoCheck.do";
	
	/*
	 * 메인페이지
	 */
	else if (page == 'MAIN') url = env.contextPath + "/main/main.do";


	/*
	 * 아이디/패스워드 찾기
	 */
	else if (page == "FINDID") url = env.contextPath + "/login/findId.do";
	/*
	 * 회원가입 전 타채널 가입 여부 체크 - 신규가입
	 */
	else if (page == "CHECKBEFOREREGIST") url = env.contextPath + "/member/registCheckOtherChnl.do";

	/*
	 * 다운로드 
	 */
	else if (page == "DOWNLOAD") url = env.contextPath + "/download/downloadRead.do";
	/*
	 * 다운로드2
	 */
	else if (page == "DOWNLOAD2") url = env.contextPath + "/download/downloadAndroid.do?ctgrCd=android&menuId=1";

	/*
	 * PW찾기
	 */
	else if (page == "FINDPW") url = env.contextPath + "/login/findPw.do";

	/*
	 * 공지
	 */
	else if (page == "NOTICE") url = env.contextPath + "/notice/listNotice.do";
	/*
	 * 문의하기
	 */	
	else if (page == "QNA") url = env.contextPath + "/qna/newQna.do";
	/*
	 * 
	 */
	else if (page == "FAQ") url = env.contextPath + "/faq/listFaq.do";
	/*
	 * BLOG
	 */
	else if (page == "BLOG") url = env.contextPath + "/blog/viewBlog.do";
		
		
	var url_= document.URL;
	url_= url_.split("//");
	var aa;
	if (page == "FINDID"){
		aa = "https://";
	}else{
		aa = "http://";
	}
	url= aa+url_[1].substr(0,url_[1].indexOf("/"))+url;
	location.href= url;
}

/**
 * 공지사항 상세 페이지로.
 * @param noticeId
 * @return
 */
function commonViewNotice(noticeId) {
	var url = env.contextPath + "/notice/viewNotice.do?sub.noticeId=" + noticeId;
	location.href= url;
}


/**
 * 포럼 상세 보기 (Main 에서 사용)
 * @param ctgrCd
 * @param articleId
 * @return
 */
function forumDetail(ctgrCd, articleId) {
	location.href = env.contextPath + "/forum/forumBoard.do?cr.ctgrCd="+ctgrCd+"&cr.articleId="+articleId;
}

/**
 * 포럼 서브 메인 (Main 에서 사용)
 * @param ctgrCd
 * @param articleId
 * @return
 */
function forumBoardList(ctgrCd, articleId) {
	location.href = env.contextPath +  "/forum/forumBoardList.do?cr.ctgrCd="+ctgrCd;
}

/**
 * 다운로드 Category 별 이동 (Main 에서 사용)
 * @param ctgrCd
 * @return
 */
function goDownloadCategory (ctgrCd) {
	location.href = env.contextPath + "/download/downloadRead.do?sub.ctgrCd="+ctgrCd
}

/**
 * 이메일 체크.
 * @param email
 * @return
 */
function checkEmail(email) {
	if(email.value == ''){
		alert('이메일을 입력해 주세요.');
		email.focus();
		return false;
	}

	if(/^[_0-9a-zA-Z-]+(\.[_0-9a-zA-Z-]+)*@[0-9a-zA-Z-]+(\.)+([0-9a-zA-Z-]+)([\.0-9a-zA-Z-])*$/.test(email.value) == false) {
   		alert('E-mail주소 형식이 아닙니다.\n\r다시 입력해 주세요.');
   		email.focus();
   		return false;
  	}
	return true;
}

/**
 * 필수입력 필드 체크.
 * @param filed
 * @param filedName
 * @return
 */
function requiredFiled(filed, filedName) {
	if(filed.value == ''){
		alert(filedName + ' 입력해 주세요.');
		filed.focus();
		return false;
	}
	return true;
}

/**
 * 필수입력 필드 체크.
 * @param filedId
 * @param filedName
 * @return
 */
function requiredFiledById(filedId, filedName) {
	return requiredFiled(document.getElementById(filedId), filedName);
}

/**
 * 숫자만으로 이루어졌는지 체크.
 * @param obj
 * @return
 */
function isNumeric(obj) {
    if(isNaN(obj)) {  
        return false;
    } else {
        return true;
    }
}

/**
 * 전화번호 형식으로 바꾸기
 * 예) 	02282342232 -> 02-8234-2232
 * 		0312437845	-> 031-124-7845
 * 		01071050616	-> 010-7105-0616
 * @param num
 * @return
 */
function phone_format(num){
	return num.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
}

/**
 * 약관 종류에 따른 alert 처리
 * @return
 */
function checkWhichAgree() {
	var boxName = "";
	var boxs = $("input:checkbox");
	
	if ($("input:checkbox[name=box]").length > $("input:checkbox[name=box]:checked").length) {
		for (var i=0; i<$("input:checkbox[name=box]").length; i++) {
			if (boxs[i].checked == false) {
				boxName = boxs[i].value;
				break;
			}
		}
		alert(boxName + "약관에 동의해 주세요.");
		return true;
	}
}


/**
 * Delimeter가 포함된 현재일자및 ±num일자를 구한다 ex) alert(getDay(-1,'-'))
 * @return
 */
function getNumDay(num,delim) {
	var dayNum = new Date(new Date().valueOf() + (24*60*60*1000)*num);
	var year = dayNum.getFullYear();
	var month = dayNum.getMonth()+1;
	var day = dayNum.getDate();
	if (month < 10) month = "0" + month;
	if (day < 10) day = "0" + day;
	return year + delim + month + delim + day;
}


/**
 * Delimeter가 포함된 현재월및 ±num월을 구한다 ex) alert(getMonth(0,'-'))
 * @return
 */
function getNumMonth(num,delim) {
	var dayNum = new Date();
	var year = dayNum.getFullYear();
	var month = dayNum.getMonth()+1 +num;
	if (month < 10) month = "0" + month;
	return year + delim + month;
}

function replaceAllRemoveSpecial(str) {
	var blank = "";
	var rgst1 = "<";
	var rgst2 = ">";
	
	str = str.replace(/rgst1/ig, blank);
	str = str.replace(/rgst2/ig, blank);
	return str;
}

//main noti popup (next common method make dev.common.js)
function notiPopupOpen() {
	/* 기존 팝업
	if(getCookie("notiPopup") == ""){
		//if (checkDate3('2010021200', '2010031200')) {
			var targetUrl = env.contextPath+"/jsp/common/popup/popNoti.jsp"
			window.open(targetUrl,'POLL','top=0, left=0, scrollbars=no, width=558, height=838');
		//}
	}
	*/
	// 변경된 Tacademy 봄학기 일정
	//var targetUrl = env.contextPath+"/jsp/common/popup/tAcademySpringCamp.jsp"
	//window.open(targetUrl,'POLL','top=0, left=0, scrollbars=no, width=574, height=831');
	
	// 2010.07.08 jipark add - 티스토어 개발자 지원도구 배포 안내 팝업
	// 2010.09.28 팝업 Close - 11.01 open
	if(getCookie("devNoti20101123") == ""){
		var targetUrl = env.contextPath+"/jsp/common/popup/devNoti20101123.jsp";
		window.open(targetUrl,'devNoti20101123','top=0, left=0, scrollbars=no, width=500, height=713');
	}
}

function checkDate3( startDate, endDate ){
	
	var dt = new Date();
	var month = eval(dt.getMonth()+1);
	var day = dt.getDate();
	var hour = dt.getHours();

	if (month  < 10) month = "0" + month;
	if (day  < 10) day = "0" + day;
	if (hour  < 10) hour = "0" + hour;

	var date = dt.getFullYear()+""+month+""+day+""+hour;

	if ((startDate <= date) && (date <= endDate)) return true;
	else return false;
}

/*
 * 입력값 Byte수 반환.
 * (한글일 경우 3으로 계산)
 */
function getByteLength(str){
	 var resultSize = 0;
	 if (str == null) {
		return 0;
	}

	for ( var i = 0; i < str.length; i++) {
		var c = escape(str.charAt(i));
		if (c.length == 1) {
			resultSize++;
		} else if (c.indexOf("%u") != -1) {
			resultSize += 3;
		} else if (c.indexOf("%") != -1) {
			resultSize += c.length / 3;
		}
	}
	return resultSize;
} 

function checkLength(field, max){
	var str = field.value;
	if (getByteLength(str) > max) {
		alert(max + "Byte 까지만 입력됩니다.");
		field.value = "";
	}
}


/*
 * limit 넘으면 자르기. 
 * limit는 byte단위.
 * 한글은 3byte 처리.
 */
function cuttingAsByte(obj, limitMax, isMsgOpen) {
	
	var temp = 0;
	var onechar;
	var tcount = 0;
	var aquery = obj.value; 
	var tmpStr2 = "";

	tmpStr = new String(aquery);
	temp = tmpStr.length;
	
	for (k=0;k<temp;k++){
		onechar = tmpStr.charAt(k);
		if (escape(onechar).length > 4) {
			tcount += 3;
		}else{
			tcount++;
		}
		if(tcount>limitMax) {
			if(escape(tmpStr.charAt(tcount-2)).length == 3){
				k=k-1;
			}
			tmpStr2 = tmpStr.substring(0,k);
			break;
		}else{
			tmpStr2 += onechar; 
		}
	}	

	if(tcount > Number(limitMax)) {
		reserve = tcount-Number(limitMax);
		obj.value = tmpStr2;
		if (isMsgOpen = true) alert(limitMax + "Byte까지 입력됩니다.");
	return;
	}
}


/**
 * 함수명: getByteLength()
 * 설  명: 문자열의 Byte 길이 값을 반환
 * 인  자: null
 * 리  턴: 문자열의 Byte 길이
 * 사용법: "한글12".getByteLength() -> 6
 */ 
String.prototype.getByteLength = function() 
{
    var len = 0;
    if(this == null) return 0;
    for(var i = 0; i < this.length; i++) {
        var c = escape(this.charAt(i));
        if(c.length == 1) len++;
        else if(c.indexOf("%u") != -1) len += 2;
        else if(c.indexOf("%") != -1) len += c.length / 3;
    }
    
    return len;
}

	/**
	 * 포스트 방식 팝업 윈도우 화면의 중간에 위치.
	 * @param {String} targetUrl	팝업 윈도우의 내용을 구성하기 위한 호출 URL
	 * @param {String} windowName	팝업 윈도우의 이름
	 * @param {Object} properties	팝업 윈도우의 속성(넓이, 높이, x/y좌표)
	 * @param {Object} formJson Json 방식의 Form 값
	 */	
	function centerPopupWindowPost(targetUrl, windowName, properties, formJson, voName) {
		var childWidth = properties.childWidth;
		var childHeight = properties.childHeight;
		var childTop = (screen.height - childHeight) / 2 - 50;    // 아래가 가리는 경향이 있어서 50을 줄임
		var childLeft = (screen.width - childWidth) / 2;
		var popupProps = "width=" + childWidth + ",height=" + childHeight + ", top=" + childTop + ", left=" + childLeft;
		if (properties.scrollBars == "YES") {
			popupProps += ", scrollbars=yes";
		}
		
		//Json 방식 Form 처리
	    $("#postPopUpForm").remove(); 
	    
	    var newForm = "<form id='postPopUpForm' action='"+ targetUrl + "' method=post target='"+ windowName + "'>";
	          
	    if(formJson != null) {
	        $.each(formJson, function(i, j) { 
	            if (voName != "") i = voName + "." + i; //voName 있을경우 name에 추가해줌
	        	newForm += "\n<input type=\"hidden\" name=\"" + i + "\" value=\"" + j + "\"/>";
	        }); 
	    } 
	    newForm += "\n</form>";
	    
	    $("body").append(newForm);     
	
		var popupWin = window.open("", windowName, popupProps);
	    
		$("#postPopUpForm").submit();
	    
	    popupWin.focus();
	}

	//NBC Center Open Script
	function popNbcOpen( targetUrl )	{
		var left	= ( screen.availWidth - 800 ) / 2;
		var top	= ( screen.availHeight - 600 ) / 2;
		window.open( targetUrl, 'popNbcWindow', 'top='+top+', left='+left+', width=800, height=600' );
	}
	 
	function balloonHelp(divObj) {
		var obj = document.getElementById(divObj);
		var view = document.getElementById("view");
		var close = document.getElementById("close");
		if (obj.style.display == "none") {
			obj.style.display = "block";
		} else {
			obj.style.display = "none";
		}
	}
