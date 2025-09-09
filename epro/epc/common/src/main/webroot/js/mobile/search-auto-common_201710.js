//브라우져 종류 체크 IE/ETC
function dqc_getBrowserType() {
	if (navigator.appName == "Microsoft Internet Explorer")
		return 1;
	else
		return 2;
}

// create XMLHTTP
function dqc_getXMLHTTP(xmlRequest) {
	if (xmlRequest && xmlRequest.readyState != 0)
		xmlRequest.abort();

	try {
		xmlRequest = new ActiveXObject("Msxml2.XMLHTTP");
	} catch (e) {
		try {
			xmlRequest = new ActiveXObject("Microsoft.XMLHTTP");
		} catch (e) {
			xmlRequest = false;
		}
	}

	if (!xmlRequest && typeof XMLHttpRequest != "undefined")
		xmlRequest = new XMLHttpRequest();

	return xmlRequest;
}

// trim, 공백제거
function dqc_trimSpace(ke) {
	ke = ke.replace(/^ +/g, "");
	ke = ke.replace(/ +$/g, " ");

	ke = ke.replace(/^ +/g, " ");
	ke = ke.replace(/ +$/g, "");

	ke = ke.replace(/ +/g, " ");

	return ke;
}

// 문장 하이라이팅
function dqc_highlight(s, d, is_suf, sTag, eTag) {
	var ret = "";

	if (is_suf == 0)
		ret = dqc_makehigh_pre(s, d, sTag, eTag);
	else if (is_suf == -1)
		ret = dqc_makehigh_suf(s, d, sTag, eTag);
	else
		ret = dqc_makehigh_mid(s, d, is_suf, sTag, eTag);

	if (ret == "")
		return s;
	else
		return ret;
}

// 앞부분 단어 하이라이팅
function dqc_makehigh_pre(s, t, sTag, eTag) {
	var d = "";
	var s1 = s.replace(/ /g, "");
	var t1 = t.replace(/ /g, "");

	t1 = t1.toLowerCase();

	if (t1 == s1.substring(0, t1.length)) {
		d = sTag;

		for ( var i = 0, j = 0; j < t1.length; i++) {
			if (s.substring(i, i + 1) != " ")
				j++;
			d += s.substring(i, i + 1);
		}

		d += eTag + s.substring(i, s.length)
	}
	return d;
}

// 뒷부분 단어 하이라이팅
function dqc_makehigh_suf(s, t, sTag, eTag) {
	var d = "";
	var s1 = s.replace(/ /g, "");
	var t1 = t.replace(/ /g, "");

	t1 = t1.toLowerCase();

	if (t1 == s1.substring(s1.length - t1.length)) {
		for ( var i = 0, j = 0; j < s1.length - t1.length; i++) {
			if (s.substring(i, i + 1) != " ")
				j++;
			d += s.substring(i, i + 1);
		}

		d += sTag;

		for ( var k = i, l = 0; l < t1.length; k++) {
			if (s.substring(k, k + 1) != " ")
				l++;
			d += s.substring(k, k + 1);
		}

		d += eTag;
	}

	return d;
}

// 중간부분 단어 하이라이팅
function dqc_makehigh_mid(s, t, pos, sTag, eTag) {
	var d = "";
	var s1 = s.replace(/ /g, "");
	var t1 = t.replace(/ /g, "");

	t1 = t1.toLowerCase();
	d = s.substring(0, pos);
	d += sTag;

	for ( var i = pos, j = 0; j < t1.length; i++) {
		if (s.substring(i, i + 1) != " ")
			j++;
		d += s.substring(i, i + 1);
	}

	d += eTag + s.substring(i, s.length);

	return d;
}

// string length
function dqc_strlen(s) {
	var i, l = 0;

	for (i = 0; i < s.length; i++) {
		if (s.charCodeAt(i) > 127)
			l += 2;
		else
			l++;
	}

	return l;
}

// string substring
function dqc_substring(s, start, len) {
	var i, l = 0;
	d = "";

	for (i = start; i < len && l < len; i++) {
		if (s.charCodeAt(i) > 127)
			l += 2;
		else
			l++;

		d += s.substr(i, 1);
	}
	return d;
}
// 숫자 콤마(,) 추가
function addComma(n) {
	if (isNaN(n)) {
		return 0;
	}
	var reg = /(^[+-]?\d+)(\d{3})/;
	n += '';
	while (reg.test(n))
		n = n.replace(reg, '$1' + ',' + '$2');
	return n;
}

// -----검색 요청-----
function DQhandleEnter(kind, event, flag) {

	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which
			: event.charCode;

	if (keyCode == 13) {
		if (flag == "mainSearch") {
			DQmainSearch_submit();
		}
		return false;
	} else {
		return true;
	}
}

function DQmainSearch_submit() {
	/* facebook dynamic remarketing 이벤트중복-주석처리 2019-02-15
	if(window.fbq){
		fbq('track', 'Search');
	}
	}*/
	// alert(cateType1);
	var searchTerm = document.getElementById("searchTerm").value;
	searchTerm = searchTerm.replace(/(^\s*)|(\s*$)/gi, ""); // searchTerm 공백제거
	var saveStatus = getSearchCookie("dq_searchCookie_save");

	for ( var p = 0; p < searchTerm.length; p++) {
		searchTerm = searchTerm.replace("<", "").replace(">", "").replace("&",	"").replace("\"", "").replace("'", "");
	}
	var truncSearchTerm = dqc_substring(searchTerm, 0, 40);
	document.getElementById("searchTerm").value = truncSearchTerm;
	if(dqc_strlen(searchTerm) > 40) {
		$("#strLengthChk").val("Y");
	}
	var cnt = searchTerm.length;
	if (cnt < 1) {
		alert("검색하실 내용을 입력하세요.");
		document.getElementById("searchTerm").focus();
		return false;
	} else {
		// document.getElementById("mainSearchForm").action="/mobile/search/PMWMSER0003.do?SITELOC=MW003&keyword="+searchTerm;
			if(saveStatus != "off") {
 				var dt = new Date();
 			 	var month = dt.getMonth()+1;
 			 	var day = dt.getDate();
 			 	if(month < 10) month = "0"+month;
 			 	if(day < 10) day = "0"+day;
 			 	var toDate = month + "." + day;

 			 	if(searchTerm != ""){
 			 		setSearchCookie_m( 'dq_keyword', searchTerm+ "$|" + toDate);
 			 	}
 	 	 	}

		//document.getElementById("mainSearchForm").submit();
		//Ajax로 처리 2017.09.05
		//ajax 변환 start!!
		  	  $.ajax({
					type:"GET",
					url:"/mobile/search/ajax/Search.do?searchTerm=" + encodeURIComponent ( $('#searchTerm').val() ),
					success:function(data){

					if(data !== null && data !== ''){
						if( typeof data.redirectURL !== "undefined" ){
							// 검색프로모션 결과값이 있는 경우
					    	ga('send', 'event', {
								eventAction: '검색 키워드 전단/전문관 바로가기',
								eventCategory: '#몰구분=Mart #기기=M #분류=상품탐색PLP(검색) #페이지명=검색PLP #URL=' + data.redirectURL,
								eventLabel : 'searchTerm=' + $('#searchTerm').val()
							});

							location.href=data.redirectURL;
						}else{
							// 검색프로모션 결과값이 없는 경우
							document.getElementById("mainSearchForm").submit();
						}
					}
					},
					error: function(xhr,status,error){
						alert(error);
					}
				});
				//ajax 변환 end!!

	}
}

// ----- 정렬 선택시 -----
//function DQorderBy_submit(orderBy) {
//	document.pageNavigator.orderBy.value = orderBy;
//	document.pageNavigator.submit();
//}

// ----카테고리
//function cateSubmit(cate) {
//	var _label = cate.split('^');
//	ga('send', 'event', {
//		eventAction: '검색 | 검색 실행 | 보기 필터',
//		eventCategory: 'M | Search | LotteMartMall | ' + location.href,
//		eventLabel : _label[1]
//	});
//	document.pageNavigator.cateType1.value = cate;
//	document.pageNavigator.submit();
//}

//------- 2016.03 상세조건 배송 종류 추가 ---------//
function deliverySubmit() {

	//document.pageNavigator.deliveryType.value = type;
	document.getElementById("deliveryType");
	document.pageNavigator.submit();
}

/*// ---- 배송조건
function deliverySubmit(type) {
	document.pageNavigator.deliveryType.value = type;
	document.pageNavigator.submit();
}*/

//------- 2015.06 매장배송만 보기 ---------//
function setDelivery01(){

	if(document.getElementById("setDelivery01").checked==true){
		document.pageNavigator.deliveryType.value = "01";
	}else{
		document.pageNavigator.deliveryType.value = "total";
	}
	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.submit();
}

function clearKey() {
	document.mainSearchForm.searchTerm.value = "";
	document.mainSearchForm.searchTerm.focus();

	$( '#searchDelete' ).addClass( 'hidden' );
	//return false;
}

$(".wrap-search-header").click(function() {
    $('#searchResult').hide();
    $('#searchDelete').removeClass('small');
    $('#searchTerm').removeClass('hidden');
    focusCampo('searchTerm');	// 문자 맨끝으로 focus
})

function focusCampo(id) {
    var inputField = document.getElementById(id);
    if (inputField != null && inputField.value.length != 0) {
        if (inputField.createTextRange) {
            var FieldRange = inputField.createTextRange();
            FieldRange.moveStart('character',inputField.value.length);
            FieldRange.collapse();
            FieldRange.select();
        } else if (inputField.selectionStart || inputField.selectionStart == '0') {
            var elemLen = inputField.value.length;
            inputField.selectionStart = elemLen;
            inputField.selectionEnd = elemLen;
            inputField.focus();
        }
    } else {
        inputField.focus();
    }
}

//----- 2015.06 혜택으로 검색 -----
//function setBenefit() {
//	//----- 2016.03 상세조건 혜택 종류 추가 ---------//
//	function setBenefit(benefitType) {
//	/* 		if(document.getElementById("chk21").checked==true){
//				document.pageNavigator.catalog.value = "Y";
//			}else{
//				document.pageNavigator.catalog.value = "N";
//			}
//
//			if(document.getElementById("chk22").checked==true){
//				document.pageNavigator.noch.value = "Y";
//			}else{
//				document.pageNavigator.noch.value = "N";
//			}
//
//			if(document.getElementById("chk23").checked==true){
//				document.pageNavigator.gift.value = "Y";
//			}else{
//				document.pageNavigator.gift.value = "N";
//			}
//
//			if(document.getElementById("chk24").checked==true){
//				document.pageNavigator.cardDc.value = "Y";
//			}else{
//				document.pageNavigator.cardDc.value = "N"; */
//			}
//
//		document.pageNavigator.benefitType.value = benefitType;
//		document.pageNavigator.currentPage.value = "1";
//		document.pageNavigator.LIMIT.value = "20";
//		document.pageNavigator.submit();
//
//}
function claerBenefit() {
	if(document.getElementById("chk21").checked==true){
		document.getElementById("chk21").checked = false;
	}

	if(document.getElementById("chk22").checked==true){
		document.getElementById("chk22").checked = false;
	}

	if(document.getElementById("chk23").checked==true){
		document.getElementById("chk23").checked = false;
	}

	if(document.getElementById("chk24").checked==true){
		document.getElementById("chk24").checked = false;
	}
}

//----- 일간 검색어 --------//
function fn_goTrend(keyword) {
	 document.pageNavigator.searchTerm.value = keyword;
	 document.pageNavigator.submit();
}

// 리스트 보기
function changeType(type){
//	document.pageNavigator.viewType.value = type;
//	document.pageNavigator.submit();
//
	var _label = '';
	if(type == "gallery"){	// 리스트일 경우 클릭 -> 2단 갤러리 형
		document.pageNavigator.viewType.value = "G";
		_label = '목록형';
	}else if(type == "G"){	// 2단 갤러리 형일 경우 클릭 -> 갤러리형
		document.pageNavigator.viewType.value = "T";
		_label = '갤러리형';
	}else if(type == "T"){	// 갤러리형일 경우 클릭 -> 리스트
		document.pageNavigator.viewType.value = "gallery";
		_label = '이미지형';
	}

	ga('send', 'event', {
		eventAction: '검색 | 검색 실행 | 보기 필터',
		eventCategory: 'M | Search | LotteMartMall | ' + location.href,
		eventLabel : _label + ' 보기 선택'
	});

	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.LIMIT.value = "20";
	document.pageNavigator.submit();
}

// ---- 인기검색어, 쿠키
function searchKeyValue(keyword) {
	document.mainSearchForm.searchTerm.value = keyword;
	document.mainSearchForm.submit();
}

function fnEventSearch(url, param,idx) {
	n_click_logging(_LMAppUrl + "/clickevent?SITELOC=PC00"+idx,location.href);
	location.href=url+"/mobile/evt/mEventDetail.do?categoryId="+param;
}

function fnMpoSearch() {
	n_click_logging(_LMAppUrl + "/clickevent?SITELOC=PD004",location.href);
	location.href="http://m.company.lottemart.com/bc/mobile/PbcmMobileBranch/list.mdo";
}

function directSearch(keyword, obj) {
	document.hiddenSearch.searchTerm.value = keyword;
	var saveStatus = getSearchCookie("dq_searchCookie_save");
	if(obj == "ingSearch1") {
		n_click_logging(_LMAppUrl + "/clickevent?SITELOC=PA002",location.href);
	} else if (obj == "ingSearch2") {
		n_click_logging(_LMAppUrl + "/clickevent?SITELOC=PA003",location.href);
	}
	if(saveStatus != "off") {
		var dt = new Date();
	 	var month = dt.getMonth()+1;
	 	var day = dt.getDate();
	 	if(month < 10) month = "0"+month;
	 	if(day < 10) day = "0"+day;
	 	var toDate = month + "." + day;

	 	if(keyword != ""){
	 		setSearchCookie_m( 'dq_keyword', keyword + "$|" + toDate);
	 	}
 	}

	//document.hiddenSearch.submit();
	//Ajax로 처리 2017.11.22
	//ajax 변환 start!!
  	  $.ajax({
			type:"GET",
			url:"/mobile/search/ajax/Search.do?searchTerm=" + encodeURIComponent ( document.hiddenSearch.searchTerm.value ),
			success:function(data){

			if(data !== null && data !== ''){
				if( typeof data.redirectURL !== "undefined" ){
					// 검색프로모션 결과값이 있는 경우
			    	ga('send', 'event', {
						eventAction: '검색 키워드 전단/전문관 바로가기',
						eventCategory: '#몰구분=Mart #기기=M #분류=상품탐색PLP(검색) #페이지명=검색PLP #URL=' + data.redirectURL,
						eventLabel : 'searchTerm=' + document.hiddenSearch.searchTerm.value
					});

					location.href=data.redirectURL;
				}else{
					// 검색프로모션 결과값이 없는 경우
					document.hiddenSearch.submit();
				}
			}
			},
			error: function(xhr,status,error){
				alert(error);
			}
		});
		//ajax 변환 end!!





}

//getCookie
function getSearchCookie(cookieName) {
	var search = cookieName + "=";
	var cookie = document.cookie;
	 //alert(decodeURIComponent(cookie));

	// 현재 쿠키가 존재할 경우
	if (cookie.length > 0) {
		// 해당 쿠키명이 존재하는지 검색한 후 존재하면 위치를 리턴.
		startIndex = cookie.indexOf(cookieName);
		// alert(cookie);

		// 만약 존재한다면
		if (startIndex != -1) {
			// 값을 얻어내기 위해 시작 인덱스 조절
			startIndex += cookieName.length;

			// 값을 얻어내기 위해 종료 인덱스 추출
			endIndex = cookie.indexOf(";", startIndex);

			// 만약 종료 인덱스를 못찾게 되면 쿠키 전체길이로 설정
			if (endIndex == -1)
				endIndex = cookie.length;
			// 쿠키값을 추출하여 리턴
			//return decodeURIComponent(unescape(cookie.substring(startIndex + 1, endIndex)));
			//return decodeURIComponent(cookie.substring(startIndex + 1, endIndex));
			return decodeURIComponent(cookie.substring(startIndex + 1, endIndex));
		} else {
			// 쿠키 내에 해당 쿠키가 존재하지 않을 경우
			return "";
		}
	} else {
		// 쿠키 자체가 없을 경우
		return "";
	}
}

/*---------------------------------------------------------------------------
    Description   : 나의 검색어 쿠키 셋팅
-----------------------------------------------------------------------------*/
function setSearchCookie(searchQuery, c_name){
       var cookieValue = searchQuery ;
       var i,x,y,ARRcookies=document.cookie.split(";");

       for (i=0;i<ARRcookies.length;i++)
         {
         x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
         y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
         x=x.replace(/^\s+|\s+$/g,"");

         if (x==c_name)
         {
           var tempValue = unescape(y);
                 if(tempValue != ""){
                            var tempArr = tempValue.split("$|");
                            for(var j=0; j<tempArr.length; j++){
                                      if(tempArr[j] == searchQuery){
                                                 tempValue = tempValue.replace("$|"+searchQuery + "$|", "$|");
                                                 if(tempValue == searchQuery){
                                                            tempValue = "";
                                                 }
                                                 if(tempValue.indexOf(searchQuery + "$|") == 0){
                                                            tempValue = tempValue.substr(searchQuery.length+2, tempValue.length);
                                                 }

                                                 if( (tempValue.length - tempValue.lastIndexOf("$|"+searchQuery) ) == (searchQuery.length+2) ){
                                                            tempValue = tempValue.substr(0, searchQuery.length-2);
                                                 }
                                      }
                            }
                            cookieValue = cookieValue+ "$|" + tempValue + "^";
                 }
         }
       }
       setCookie(c_name, cookieValue, "1");
}

/*---------------------------------------------------------------------------
    Description   : 나의 검색어 쿠키 생성
-----------------------------------------------------------------------------*/
function setCookie(c_name,value,exdays)
{
       var exdate=new Date();
       exdate.setDate(exdate.getDate() + exdays);
       var c_value=encodeURIComponent(value) + "; path=/; " +((exdays==null) ? "" : "; expires="+exdate.toUTCString());
       document.cookie=c_name + "=" + c_value;
}

// setCookie
function setSearchCookie_m( cookieName, cookieValue ) {
	if(cookieValue.indexOf(">")>0){
		cookieValue = cookieValue.split(">")[1];
	}
	var new_cookieValue = cookieValue.split("$|")[0];
	var new_cookieDate = cookieValue.split("$|")[1];
	var expire = new Date();
    expire.setDate(expire.getDate() + 7);

	var ext = getSearchCookie( cookieName );
//	ext =  encodeURIComponent(unescape(ext));
//	console.log(ext);
	var index = ext.indexOf( new_cookieValue );
	var dupData = ext.split("∃");
	var re_ex = "";

	if(index != -1 ){			//중복 키워드 일 경우
		for(var i=0; i<dupData.length-1; i++){
			if(new_cookieValue == dupData[i].split("$|")[0]){
				ext = ext.replace(dupData[i]+"∃","").replace(/%2B/g, '\+').replace(/%25/g, "%");
			}
		}
	}
	// 최근검색어 10개 이상일 경우 10개까지만 저장
	if(ext.split("∃").length >= 10){
		for(var j=0; j<10; j++){
			re_ex += ext.split("∃")[j] + "∃";
		}
	}
	if(re_ex != "") ext = re_ex.replace(/%2B/g, '\+').replace(/%25/g, "%");
	document.cookie = cookieName + "=" + encodeURIComponent(unescape( cookieValue) + "∃" + ext ).replace("undefined","")+ ";expires=" + expire.toGMTString() + ";path=/" + ";domain=.lottemart.com";
	//document.cookie = cookieName + "=" + escape( cookieValue + "∃" + ext ).replace("undefined","") + ";expires=" + expire.toGMTString() + "; path=/";
	//alert("document.cookie:"+document.cookie);
}
/*---------------------------------------------------------------------------
Description   : 검색여부 켜기/끄기
-----------------------------------------------------------------------------*/
function setSaveCookie(cookieName,cookieValue)
{
	setCookie(cookieName, cookieValue, 1);
}

// cookie 하나 삭제
function delSearchCookie(cookieName, cookieValue, cookieId) {
/*	var returnCookie = "";
	var ex = getSearchCookie(cookieName);
	//alert(ex);
	var newcookieValue = encodeURIComponent(returnCookie).replace(/%25/g, "%");
	var cookieArray = ex.split("$|");
	for(var i=0;i<cookieArray.length;i++){
		if(cookieArray[i]!=cookieValue&&cookieArray[i]!=""){
			if(returnCookie=="") returnCookie += cookieArray[i];
			else returnCookie += "$|" + cookieArray[i];
		}
	}
	document.cookie = cookieName + "=" + encodeURIComponent(returnCookie).replace(/%25/g, "%") + ";  path=/";*/

	ga('send', 'event', {
		eventAction: '검색 | 시도 | 최근검색어',
		eventCategory: 'M | Search | 검색레이어 | ' + cookieValue,
		eventLabel : '최근 검색어 삭제'
	});

	var ex = getSearchCookie( cookieName );
	ex = ex.replace(cookieValue+"∃","");
	document.cookie = cookieName + "=" + encodeURIComponent( ex ).replace( "undefined", "" ) + ";path=/" + ";domain=.lottemart.com";

	var GlobalCookieCnt = 0;
	GlobalCookieCnt = document.getElementById("GlobalCookieCnt").value;
	document.getElementById("GlobalCookieCnt").value = GlobalCookieCnt-1;

	if(document.getElementById("myKeyword"+cookieId)) document.getElementById("myKeyword"+cookieId).style.display = "none";
	if(GlobalCookieCnt<2){
		document.getElementById("delAllButton").style.display = "none";
		document.getElementById("myKeywordNo").style.display = "block";

		$( '#recentlyList' ).find( 'ul' ).hide();
		$( '#recentlyList' ).find( '.btm-desc' ).hide();
	}

	setRecentlySearchList();
	//document.mainSearchForm.submit();
	//window.location.reload();
}

//cookie 하나 삭제
function delSearchCookie2(cookieName, cookieValue, cookieId) {
	var ex = getSearchCookie(cookieName);
	//var newcookieValue = escape(encodeURIComponent(cookieValue)).replace(/%25/g, "%");
	var newcookieValue = encodeURIComponent(cookieValue).replace(/%25/g, "%");

	ex = escape(ex).replace(newcookieValue, "");
	document.cookie = cookieName + "=" + ex.replace("undefined", "") + ";  path=/";

	var GlobalCookieCnt = 0;
	GlobalCookieCnt = document.getElementById("GlobalCookieCnt").value;
	document.getElementById("GlobalCookieCnt").value = GlobalCookieCnt-1;
	if(document.getElementById("myKeyword"+cookieId)) document.getElementById("myKeyword"+cookieId).style.display = "none";
	if(GlobalCookieCnt<2) document.getElementById("myKeywordNo").style.display = "block";
	//document.mainSearchForm.submit();
	//window.location.reload();
}

// cookie 모두 삭제
function delSearchCookieAll(cookieName, cookieCnt) {
	ga('send', 'event', {
		eventAction: '검색 | 시도 | 최근검색어',
		eventCategory: 'M | Search | 검색레이어',
		eventLabel : '최근 검색어 전체 삭제'
	});

	document.cookie = cookieName + "=" + "; path=/" + ";domain=.lottemart.com";
	for ( var i = 0; i < cookieCnt; i++) {
		if(document.getElementById("myKeyword"+i)) document.getElementById("myKeyword"+i).style.display = "none";
	}
	document.getElementById("delAllButton").style.display = "none";
	document.getElementById("myKeywordNo").style.display = "block";
	var searchH = $('.search_content .swiper-slide-active').outerHeight(true) + 64;
	$('.search_slide_wrap').css({'max-height':searchH}).addClass('open');

	$( '#recentlyList' ).find( 'ul' ).hide();
	$( '#recentlyList' ).find( '.btm-desc' ).hide();

	$("#GlobalCookieCnt").val(0);
	setRecentlySearchList();
}

function isSaveCookie(){
	if($("#saveKeyword").text() == '저장끄기'){
		$("#saveKeyword").text('저장켜기');
		offSearchCookie();
	}else {
		$("#saveKeyword").text('저장끄기');
		onSearchCookie();
	}
}

// 검색 끄기
function offSearchCookie(){
	setSaveCookie('dq_searchCookie_save', 'off');
}

// 검색 켜기
function onSearchCookie(){
	setSaveCookie('dq_searchCookie_save', 'on');
}

function goMobileSearchHome(){
	location.href="/mobile/search/Search.do?SITELOC=NM103";
 	//var popup = window.open(_LMAppUrlM+"/mobile/search/Search.do?SITELOC=NM103","popupSearch","scrollbars=yes");
	//if(popup !=null){
	//	popup.focus();
	//}
}

// 최근 검색어 0개 css empty 적용
function setRecentlySearchList() {
	var globalCookieCnt = $('#GlobalCookieCnt').val();
	if(globalCookieCnt == 0) {
		$('#recentlyList').addClass('empty');
	}
}