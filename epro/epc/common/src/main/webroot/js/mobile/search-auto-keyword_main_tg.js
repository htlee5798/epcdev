//설정값
var dq_searchForm = document.getElementById("mainSearchForm");
var dq_searchFormTextbox = dq_searchForm.searchTerm;
var dq_searchTextbox = document.getElementById("searchTerm");

var dq_resultDivID = document.getElementById("layerSearchResultNew") !== null ? 'layerSearchResultNew' : 'layerSearchResult'; //자동완성레이어 ID
var dq_hStartTag = "<em class=\"point1\">"; //하이라이팅 시작 테그
var dq_hEndTag = "</em>"; //하이라이팅 끝 테그
var dq_bgColor = "#03f3f3"; //선택빽그라운드색
var dq_intervalTime = 500; //자동완성 입력대기 시간

//고정값
var dq_acResult = new Object(); //결과값
var dq_acLine = 0; //자동완성 키워드 선택  위치(순번)
var dq_searchResultList_f = ""; //자동완성결과리스트
var dq_searchKeyword = ""; //검색어(한영변환안된)
var dq_ajaxReqObj_f = ""; //ajax request object

var dq_keyStatus = 1; //키상태구분값
var dq_acuse = 1; //자동완성사용여부
var dq_engFlag = 0; //자동완성한영변환체크
var dq_acDisplayFlag = 0; //자동완성 display 여부
var dq_acArrowFlag = 0; //마우스이벤트용 flag
var dq_acArrowOpenFlag = 0; //마우스이벤트용 flag
var dq_acFormFlag = 0; //마우스이벤트용 flag
var dq_acListFlag = 0; //자동완성 레이어 펼쳐진 상태 여부
var dq_browserType = dqc_getBrowserType(); //브라우져타입
var dq_keywordBak = ""; //키워드빽업
var dq_keywordOld = ""; //키워드빽업

dq_keywordBak = dq_keywordOld = dq_searchTextbox.value;

// 검색어 저장 여부 값이 없을 경우 디폴트로 on
var cookie = document.cookie;
var isSave = cookie.indexOf('dq_searchCookie_save');

if (isSave == -1) {
	setSaveCookie('dq_searchCookie_save', 'on');
	$("#saveKeyword").text = "저장끄기";
}

function dq_handleEnter(kind, event) {
	var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;

	if (keyCode == 13) {
		fn_searchMain();
		return false;
	} else {
		return true;
	}
}

function dq_keywordSearch(keyword) {
	dq_searchTextbox.value = keyword;

	//8.31 추가 setcookie
	var saveStatus = getSearchCookie("dq_searchCookie_save");
	if (saveStatus != "off") {
		var dt = new Date();
		var month = dt.getMonth() + 1;
		var day = dt.getDate();
		if (month < 10) month = "0" + month;
		if (day < 10) day = "0" + day;
		var toDate = month + "." + day;

		if (keyword != "") {
			setSearchCookie_m('dq_keyword', keyword + "$|" + toDate);
		}
	}

	//dq_searchForm.submit();
	//Ajax로 처리 2017.11.22
	//ajax 변환 start!!
	$.ajax({
		type: "GET",
		url:"/mobile/search/ajax/Search.do?searchTerm=" + encodeURIComponent ( dq_searchTextbox.value ),
		success: function (data) {
			if (data !== null && data !== '') {
				if (typeof data.redirectURL !== "undefined") {
					// 검색프로모션 결과값이 있는 경우
					ga('send', 'event', {
						eventAction: '검색 키워드 전단/전문관 바로가기',
						eventCategory: '#몰구분=Mart #기기=M #분류=상품탐색PLP(검색) #페이지명=검색PLP #URL=' + data.redirectURL,
						eventLabel: 'searchTerm=' + dq_searchTextbox.value
					});

					location.href = data.redirectURL;
				} else {
					// 검색프로모션 결과값이 없는 경우
					dq_searchForm.submit();
				}
			}
		},
		error: function (xhr, status, error) {
			alert(error);
		}
	});
	//ajax 변환 end!!
}

//setTextbox
function dq_setTextbox(flag, ev) {
	var _event;
	var key;
	// var schemeUrl = 'lottemartapp://';
	//		dq_stateChange();

	var o = dq_getAcResult();

	if (o && o[1][0] != "")
		dq_acResultShow(o[0], o[1]);
	else {
		dq_reqSearch();
	}

	// schemeUrl = schemeUrl + 'hideBar';
	$('#quickmenu-bar').removeClass('enable');
	$('#pagemove-to-top, #pagemove-prev').hide();
	if( location.href.indexOf( 'stopSearch.do' ) == -1 && location.href.indexOf( 'nobuyProd.do' ) == -1  && location.href.indexOf( 'nobuyProd_d.do' ) == -1) {
		// 추천검색어 영역
		$.getJSON("/search/searchList.do?LINK_TYPE_CD=13")
		.done(function(data) {
			if (data.searchList.length >= 1) {
				$('.wrap-referral-keyword').css('display','block');
				$('#mainSearchForm').addClass('srch-keyword');
				var elm = $(".search-referral-keyword .inner ul");
				$(elm).empty();
				$.each(data.searchList, function(i, obj) {
					div = "<li><a href=\"" + obj.LINK_IMG_PATH + "\">" + "#" + obj.LINK_NM + "</a></li>";	
					$(elm).append(div);
				});
			} else{
				$('.wrap-referral-keyword').remove();
			}
		});	
	}
	
	if (document.getElementById('searchTerm').value !== "") {
		//			document.getElementById('searchDelete').style.display="block";
		$('#searchDelete').removeClass('hidden');
		// $( '.wrap-app' ).addClass( 'hidden' );
		$('.ta-header-search').addClass('layer-open-type1').removeClass('layer-open-type2');
		$('.search-cancel').text("닫기"); //닫기버튼추가
		$('.ta-header-search').removeClass('no-search-cancel');
		//			$( '#quickmenu-bar' ).addClass( 'enable' );
		//			schemeUrl = schemeUrl + 'showBar';

	} else { // 검색어가 없으면
		
		$('.ta-header-search').addClass('layer-open-type2').removeClass('layer-open-type1');
		$('#searchDelete').addClass('hidden');
		// $( '.wrap-app' ).removeClass( 'hidden' );

		//			schemeUrl = schemeUrl + 'hideBar';

		if ($('.list-search-wrap').length > 0) {
			var mySwiper = $('.list-search-wrap')[0].swiper;

			if (mySwiper) {
				mySwiper.onResize();
			}
		}
	}
	if ((document.getElementById('searchTerm').value == "") && ($('.ta-header-search').find('.text-list-wrap').css('display') == 'none')) {
		//alert("201710");
		// $('.search-cancel').text("닫기");	//닫기버튼추가
		$('.ta-header-search').removeClass('no-search-cancel');
	}


	switch (dq_browserType) {
		case 1: // IE
			_event = window.event;
			key = _event.keyCode;
			break;
		case 2: // Netscape
			key = ev.which;
			break;
		default:
			key = _event.keyCode;
			break;
	}

	if (dq_keyStatus == 1 && flag && key != 13) {
		dq_keyStatus = 2;

		schemeLoader.loadScheme({
			key: 'hideBar'
		});
	}
}

function dq_stateChange() {
	dq_searchTextbox.onclick = dq_acDisplayView;
	dq_searchTextbox.onblur = dq_acDisplayCheck;
	document.body.onclick = dq_acDisplayCheck;
}

function dq_acDisplayView() {
	dq_acDisplayFlag = 1;
	dq_acFormFlag = 0;
	dq_reqAcResultShow();
}

function dq_acDisplayCheck() {

	if (dq_acDisplayFlag) {
		dq_acDisplayFlag = 0;
		return;
	}

	if (dq_acArrowFlag)
		return;


	if (dq_acFormFlag)
		return;

	dq_acDisplayHide();
}

function dq_helpDisplayShow() {
	var helpDiv = document.getElementById("search_help");
	helpDiv.style.display = "block";
	var searchH = $('.search_content .swiper-slide-active').outerHeight(true) + 64;
	$('.search_slide_wrap').css({
		'max-height': searchH
	}).addClass('open');
}

function dq_helpDisplayHide() {
	var helpDiv = document.getElementById("search_help");
	helpDiv.style.display = "none";

}

function dq_acDisplayHide() {
	var resultDiv = document.getElementById(dq_resultDivID);

	if (resultDiv.style.display == "none")
		return;

	dq_setDisplayStyle(0);
	dq_acListFlag = 0;
	dq_acLine = 0;
}

function dq_setDisplayStyle(type) {
	var resultDiv = document.getElementById(dq_resultDivID);

	if (type == 0) {
		resultDiv.style.display = "none";
		dq_switchImage(0);
	} else if (type == 1) {
		resultDiv.style.display = "block";
		dq_switchImage(1);
	} else if (type == 2) {
		resultDiv.style.display = "none";
		dq_switchImage(1);

	}
}

function dq_reqAcResultShow() {
	var resultDiv = document.getElementById(dq_resultDivID);

	if (dq_searchTextbox.value == "" || dq_acuse == 0) {
		dq_helpDisplayShow();
		return;
	}

	if (dq_acListFlag && dq_acDisplayFlag) {

		dq_acDisplayHide();
		return;
	}

	var o = dq_getAcResult();

	if (o && o[1][0] != "")
		dq_acResultShow(o[0], o[1]);
	else
		dq_reqSearch();
}

function dq_getAcResult() {
	var ke = dqc_trimSpace(dq_searchTextbox.value);

	return typeof (dq_acResult[ke]) == "undefined" ? null : dq_acResult[ke];
}

function dq_setAcResult(aq, al) {
	dq_acResult[aq] = new Array(aq, al);
}

function dq_acResultShow(aq, al) {
	dq_helpDisplayHide();

	if (aq != dqc_trimSpace(dq_searchTextbox.value))
		dq_engFlag = 1;
	else
	if (aq && aq != "" && aq != dqc_trimSpace(dq_searchTextbox.value))
		return;

	dq_searchKeyword = aq;
	dq_searchResultList = al;

	dq_printAcResult(aq);

	if (dq_searchResultList.length)
		dq_acListFlag = 1;
	else
		dq_acListFlag = 0;

	if (dq_acListFlag) {
		dq_setAcPos(0);

		if (dq_browserType == 1)
			dq_searchTextbox.onkeydown = dq_acKeywordTextViewIE;
		else if (dq_browserType == 2)
			dq_searchTextbox.onkeydown = dq_acKeywordTextViewFF;
	}
}

function dq_setAcPos(v) {
	dq_acLine = v;
	setTimeout('dq_setAcLineBgColor();', 10);
}

function dq_printAcResult(searchKeyword) {
	var resultDiv = document.getElementById(dq_resultDivID);

	$("#" + dq_resultDivID).html(dq_getHistoryList(searchKeyword));
	if (dq_searchResultList[0] == "") {
		$("#" + dq_resultDivID).append(dq_getAcNoResultList())
		//resultDiv.innerHTML = dq_getAcNoResultList();
	} else {
		$("#" + dq_resultDivID).find('ul').append(dq_getAcResultList());
	}

	dq_setDisplayStyle(1);

	setTimeout('dq_setAcLineBgColor();', 10);


	var keywordHistory = getSearchCookie('dq_keyword'),
		tmpCookieValue = "",
		text = "",
		cookieValue = keywordHistory.replace("%(?![0-9a-fA-F]{2})", "%25");
	cookieValue = keywordHistory.replace("\\+", "%2B");

	/*$('[id^=cookieKeyword_]').on('click', function() {
		var $this = $(this),
		deleteKeyword = $this.attr('data-title');
		deleteKeywordDate = $this.attr('data-date');
		keywordHistory = keywordHistory.replace(deleteKeyword + '$|'+ deleteKeywordDate +'∃', '');

		setCookieSearch('dq_keyword', unescape(encodeURIComponent(keywordHistory)));

		return false;
	});*/
}

function dq_setAcLineBgColor() {
	var o1, o2, qs_ac_len;

	if (!dq_acListFlag)
		return;

	qs_ac_len = dq_searchResultList.length;

	for (i = 0; i < qs_ac_len; i++) {
		o1 = document.getElementById('dq_ac' + (i + 1));

		if (o1 != null) {
			if ((i + 1) == dq_acLine)
				o1.style.backgroundColor = dq_bgColor;
			else
				o1.style.backgroundColor = '';
		}
	}
}

function dq_acKeywordTextViewIE() {
	var e = window.event;
	var ac, acq;
	var resultDiv = document.getElementById(dq_resultDivID);
	var qs_ac_len = dq_searchResultList.length;

	if (e.keyCode == 39)
		dq_reqAcResultShow();

	if (e.keyCode == 40 || (e.keyCode == 9 && !e.shiftKey)) {
		if (!dq_acListFlag) {
			dq_reqAcResultShow();
			return;
		}

		if (dq_acLine < qs_ac_len) {
			if (dq_acLine == 0)
				dq_keywordBak = dq_searchTextbox.value;

			dq_acLine++;

			ac = eval('dq_ac' + dq_acLine);
			acq = eval('dq_acq' + dq_acLine);
			dq_keywordOld = dq_searchTextbox.value = acq.outerText;
			dq_searchTextbox.focus();
			dq_setAcLineBgColor();
			e.returnValue = false;
		}
	}

	if (dq_acListFlag && (e.keyCode == 38 || (e.keyCode == 9 && e.shiftKey))) {
		if (!dq_acListFlag)
			return;

		if (dq_acLine <= 1) {

			dq_acDisplayHide();
			dq_keywordOld = dq_searchTextbox.value = dq_keywordBak;
		} else {
			dq_acLine--;

			ac = eval('dq_ac' + dq_acLine);
			acq = eval('dq_acq' + dq_acLine);
			dq_keywordOld = dq_searchTextbox.value = acq.outerText;
			dq_searchTextbox.focus();
			dq_setAcLineBgColor();
			e.returnValue = false;
		}
	}
}

function dq_acKeywordTextViewFF(fireFoxEvent) {
	var ac, acq;
	var resultDiv = document.getElementById(resultDiv);
	var qs_ac_len = dq_searchResultList.length;

	if (fireFoxEvent.keyCode == 39)
		dq_reqAcResultShow();

	if (fireFoxEvent.keyCode == 40 || fireFoxEvent.keyCode == 9) {
		if (!dq_acListFlag) {
			dq_reqAcResultShow();
			return;
		}

		if (dq_acLine < qs_ac_len) {
			if (dq_acLine == 0)
				dq_keywordBak = dq_searchTextbox.value;

			dq_acLine++;

			ac = document.getElementById('dq_ac' + dq_acLine);
			acq = document.getElementById('dq_acqHidden' + dq_acLine);

			dq_keywordOld = dq_searchTextbox.value = acq.value;

			dq_searchTextbox.focus();
			dq_setAcLineBgColor();
			fireFoxEvent.preventDefault();
		}
	}

	if (dq_acListFlag && (fireFoxEvent.keyCode == 38 || fireFoxEvent.keyCode == 9)) {
		if (!dq_acListFlag)
			return;

		if (dq_acLine <= 1) {

			dq_acDisplayHide();
			dq_keywordOld = dq_searchTextbox.value = dq_keywordBak;
		} else {
			dq_acLine--;

			ac = document.getElementById('dq_ac' + dq_acLine);
			acq = document.getElementById('dq_acqHidden' + dq_acLine);

			dq_keywordOld = dq_searchTextbox.value = acq.value;
			dq_searchTextbox.focus();
			dq_setAcLineBgColor();
			fireFoxEvent.preventDefault();
		}
	}
}

function dq_reqSearch() {
	var sv;
	var ke = dqc_trimSpace(dq_searchTextbox.value);

	ke = ke.replace(/ /g, "%20");

	while (ke.indexOf("\\") != -1)
		ke = ke.replace(/ /g, "%20").replace("\\", "");

	while (ke.indexOf("\'") != -1)
		ke = ke.replace(/ /g, "%20").replace("\'", "");

	if (ke == "") {

		dq_acDisplayHide();
		return;
	}
	sv = "/mobile/search/ajax/New_getAutoKeyword.do?p=1&q=" + escape(encodeURIComponent(ke));
	//alert(sv);

	dq_ajaxReqObj = dqc_getXMLHTTP();

	if (dq_ajaxReqObj) {
		dq_ajaxReqObj.open("GET", sv, true);
		dq_ajaxReqObj.onreadystatechange = dq_acShow;
	}

	try {
		dq_ajaxReqObj.send(null);
	} catch (e) {
		return 0;
	}
}

function dq_acShow() {
	if (dq_acuse == 1) {
		if (dq_ajaxReqObj.readyState == 4 && dq_ajaxReqObj.responseText && dq_ajaxReqObj.status == 200) {
			//alert(dq_ajaxReqObj.responseText);
			eval(dq_ajaxReqObj.responseText);
			dq_setAcResult(dq_searchKeyword, dq_searchResultList);
			dq_acResultShow(dq_searchKeyword, dq_searchResultList);
		}
	} else {
		dq_setDisplayStyle(2);
	}
}

function dq_setAcInput(keyword) {
	if (!dq_acListFlag)
		return;

	dq_keywordOld = dq_searchTextbox.value = keyword;
	dq_searchTextbox.focus();

	dq_acDisplayHide();
}

function dq_acOff() {
	if (dq_searchTextbox.value == "")
		dq_setDisplayStyle(0);
	else {

		dq_acDisplayHide();
	}


	//dq_acuse = 0;
}

function dq_acArrow() {
	var resultDiv = document.getElementById(dq_resultDivID);

	if (dq_acuse == 0) {
		dq_keywordOld = "";
		dq_acuse = 1;

		if (dq_searchTextbox.value == "")
			resultDiv.innerHTML = dq_getAcOnNoKeyword();
	} else {
		if (dq_searchTextbox.value == "")
			resultDiv.innerHTML = dq_getAcNoKeyword();
	}

	if (dq_searchTextbox.value == "" && (resultDiv.style.display == "block")) {
		dq_setDisplayStyle(0);

	} else
		dq_setDisplayStyle(1);

	dq_acDisplayView();
	dq_searchTextbox.focus();
	dq_wi();

	document.body.onclick = null;
}

function dq_switchImage(type) {
	if (type == 0) {
		//document.getElementById("imgIntro0").src = "";
		//document.getElementById("imgIntro0").title = "";
	} else if (type == 1) {
		//document.getElementById("imgIntro0").src = "";
		//document.getElementById("imgIntro0").title = "";
	}
}

function dq_setMouseon() {
	dq_acFormFlag = 1;
}

function dq_setMouseoff() {
	dq_acFormFlag = 0;
	dq_searchTextbox.focus();
}

function dq_getAcResultList(dp) {
	var keyword = "";
	var keywordOrign = "";
	var keyword = "";
	var keywordLength = 0;
	var lenValue = 40;
	var text = "";
	var count = 0;

	var pos = 0;
	var result = "";

	var dpFrontList = dq_searchResultList;

	var textCheck = 0;

	if (dq_searchKeyword != "") {
		if (dpFrontList != null && dpFrontList != "" && dpFrontList.length > 0) {
			//자동완성
			if (dpFrontList !== "") {

				// 검색 히스토리(최대5개) + 자동검색결과 = 최대 12개 20171220 홍진옥 작업
				var maxCount = 12,
					historyListCount = $('#layerSearchResultNew').find('li').length,
					availableCount = maxCount - historyListCount,
					resultCount = dpFrontList.length <= availableCount ? dpFrontList.length : availableCount;

				for (var i = 0, len = resultCount; i < len; i++) {
					result = dpFrontList[i].split("|");
					keyword = keywordOrign = result[0];
					keywordSumm = keyword.length > 22 ? keyword.substring(0, 22) + "..." : keyword;
					count = $.utils.comma(result[1]);

					keywordLength = dqc_strlen(keywordOrign);

					if (dq_engFlag == 0)
						pos = keywordOrign.indexOf(dq_searchTextbox.value);
					else if (dq_engFlag == 1)
						pos = keywordOrign.indexOf(dq_searchKeyword);

					if (pos >= 0) {
						if (pos == 0) {
							if (dq_engFlag == 0)
								keywordSumm = dqc_highlight(keywordSumm, dq_searchTextbox.value, 0, dq_hStartTag, dq_hEndTag);
							else if (dq_engFlag == 1)
								keywordSumm = dqc_highlight(keywordSumm, dq_searchKeyword, 0, dq_hStartTag, dq_hEndTag);
						} else if (pos == keywordOrign.length - 1) {
							if (dq_engFlag == 0)
								keywordSumm = dqc_highlight(keywordSumm, dq_searchTextbox.value, -1, dq_hStartTag, dq_hEndTag);
							else if (dq_engFlag == 1)
								keywordSumm = dqc_highlight(keywordSumm, dq_searchKeyword, -1, dq_hStartTag, dq_hEndTag);
						} else {
							if (dq_engFlag == 0)
								keywordSumm = dqc_highlight(keywordSumm, dq_searchTextbox.value, pos, dq_hStartTag, dq_hEndTag);
							else if (dq_engFlag == 1)
								keywordSumm = dqc_highlight(keywordSumm, dq_searchKeyword, pos, dq_hStartTag, dq_hEndTag);
						}
					}
					text += '<li id="dq_ac' + (i + 1) + '" onclick="dq_keywordSearch(' + "'" + keywordOrign + "'" + ');return false;">';
					text += "<input type='hidden' id='dq_acqHidden" + (i + 1) + "' value='" + keywordOrign + "'/>";
					text += "<span id='dq_acq" + (i + 1) + "' style='display:none'>" + keywordOrign + "</span>";
					text += "<a href='#' id='dq_alink" + (i + 1) + "'>";
					text += keywordSumm;
					text += "</a></li>";
					//횟수노출 제거 20170911
					//text += "</a><p class='desc'><em class='number'>";
					//text +=  count;
					//text +=  "</em> 회</p></li>";
				}
				textCheck = 1;
			}
		}
		dq_iniChk = "N";
		dq_chk = "N";
	}
	return text;
}

function dq_getHistoryList(searchKeyword) {
	var keywordHistory = getSearchCookie('dq_keyword'),
		tmpCookieValue = "",
		text = "",
		cookieValue = keywordHistory.replace("%(?![0-9a-fA-F]{2})", "%25");
	cookieValue = keywordHistory.replace("\\+", "%2B");
	text += "<ul class='list'>";
	// if(cookieValue != "") {
	// 	var cookieKeyword = ""
	// 	  , cookieRegDate = ""
	// 	  , keyword = ""
	// 	  , cnt = 0;
	// 	tmpCookieValue = cookieValue.split('∃');
	// 	tempCount = tmpCookieValue.length;

	// 	for(var i = 0; i < tempCount; i++) {
	// 		cookieKeyword = tmpCookieValue[i].split("$|")[0];
	// 		cookieRegDate = tmpCookieValue[i].split("$|")[1];
	// 		if(cookieKeyword.indexOf(searchKeyword) != -1) {
	// 			keyword = cookieKeyword.replace(searchKeyword, '<em class="point1">'+ searchKeyword + '</em>');
	// 			text += '<li class="history" id="myKeyword'+i+'">';
	// 			text += 	'<a href="#" onclick="dq_keywordSearch(\'' + cookieKeyword + '\');return false;">' + keyword + '</a>'
	// 			text += 	'<p class="adddate">' + cookieRegDate + '<button type="button" class="icon-searching-delete" onclick="delSearchCookie(\'dq_keyword\',\''+tmpCookieValue[i]+'\',\''+ i +'\');return false;">삭제</button></p>'
	// 			text += '</li>';
	// 			cnt++;
	// 			if(cnt >= 5) {
	// 				break;
	// 			}
	// 		}
	// 	}
	// }
	text += "</ul>";
	return text;
}


//자동완성 결과 없는 경우 - get_ac0
function dq_getAcNoResultList() {
	var text = "";
	var ment = "해당 단어로 시작하는 검색어가 없습니다.";
	text += "<ul class='list'>";
	text += "<li class='wordnone'>";
	text += ment;
	text += "</li>";
	text += "</ul>";
	dq_iniChk = "N";
	dq_chk = "N";
	return text;
}

function dq_getAcNoKeyword() {
	var text = "";
	var ment = "현재 자동완성 기능을 사용하고 계십니다.";

	return text;
}

function dq_getAcOnNoKeyword() {
	var text = "";
	var ment = "자동완성기능이 활성화 되었습니다.";

	return text;
}

function dq_wi() {
	if (dq_acuse == 0)
		return;

	var keyword = dq_searchTextbox.value;

	if (keyword == "" && keyword != dq_keywordOld) {

		dq_acDisplayHide();
		$("#searchDelete").hide();
	}

	if (keyword != "" && keyword != dq_keywordOld && dq_keyStatus != 1) {
		$("#searchDelete").show();
		var o = null;

		o = dq_getAcResult();

		if (o && o[1][0] != "")
			dq_acResultShow(o[0], o[1]);
		else
			dq_reqSearch();
	}

	dq_keywordOld = keyword;
	setTimeout("dq_wi()", dq_intervalTime);
}

setTimeout("dq_wi()", dq_intervalTime);