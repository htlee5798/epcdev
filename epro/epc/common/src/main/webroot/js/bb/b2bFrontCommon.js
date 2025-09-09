/** ********************************************************
 * 간편결제안내 페이지 띄우기
 ******************************************************** */
function goPopupEasyPayInfoForm(){
	var contextPath = getContextPath();
	var targetUrl = contextPath+"/front/ordsetl/popupEasyPayInfoForm.do";	
	Common.centerPopupWindow(targetUrl, 'popupEasyPayInfoForm', {
		width	: 830,
		height	: 600
	});
}


/**
 * B2B 공통 상품검색 엔터 체크
 * 
 */
$(document).ready(function(){
	$("#comSearchTxt").keypress(function(ev) {
		if ((ev.which && ev.which === 13) || (ev.keyCode && ev.keyCode === 13)) {
			goComSearch();
		} else {
			return;
		}
	});
});


/**
 * B2B 공통 상품검색
 * 
 */
goComSearch = function() {
	var searchGubun  = null;
	var schName = $('.sc_list span').html();
	if (schName == '상품명') {
		searchGubun = '1';
	} else if (schName == '상품코드') {
		searchGubun = '2';
	} else if (schName == '업체명') {
		searchGubun = '3';
	}
	var searchTxt = $("#comSearchTxt").val();
	var contextPath = getContextPath();
	
	location.href = contextPath + "/front/proddisp/prodList.do?searchGubun=" + searchGubun +  "&searchTxt=" + searchTxt + "&dummy=dummy";
}


/**
 * B2B FRONT include_head.js LINK
 * 
 */
getFrontLinkPath = function(linkStr) {
	var linkPath = null;
	var contextPath = getContextPath();
	switch (linkStr) {
	  case 'main'  : 
		  linkPath = "javascript:location.href='" + contextPath + "/front/maindisp/main.do'"; break;
	  case 'logOut'  : 
		  linkPath = "javascript:location.href='" + contextPath + "/front/login/logout.do'"; break;
	  case 'custCtr' : 
		  linkPath = "javascript:location.href='" + contextPath + "/front/announ/announList.do'"; break;
	  case 'event' : 
		  linkPath = "javascript:location.href='" + contextPath + "/front/proddisp/prodList.do?s_ctgr_tp=0'"; break;
	  case 'season' : 
		  linkPath = "javascript:location.href='" + contextPath + "/front/proddisp/prodList.do?s_ctgr_tp=1'"; break;
	  case 'basket' : 
		  linkPath = "javascript:location.href='" + contextPath + "/front/ordsetl/basketList.do'"; break;
	  case 'zzimBasket' : 
		  linkPath = "javascript:location.href='" + contextPath + "/front/mypage/zzimBasketList.do'"; break;
	  case 'frqOrd' : 
		  linkPath = "javascript:location.href='" + contextPath + "/front/mypage/selectFrqOrdProdListForm.do'"; break;
	  case 'aplyLteCard' : 
		  linkPath = "javascript:location.href='http://www.lottecard.co.kr/app/view/if/hub.jsp?URL=/app/IHSVCEA_V100.top'"; break;
	  case 'simplePay' : 
		  linkPath = "javascript:goPopupEasyPayInfoForm()"; break;
	  default   : linkPath = "javascript:location.href='" + contextPath + "/front/maindisp/main.do'"; break;
	}
	return linkPath;
}


/**
 * B2B FRONT include_quick.js LINK
 * 
 */
getFrontQuickLinkPath = function(linkStr) {
	var linkPath = null;
	var contextPath = getContextPath();
	switch (linkStr) {
	  case 'ordRepay' : 
		  linkPath = "javascript:location.href='" + contextPath + "/front/mypage/selectOrderRepayListForm.do'"; break;
	  case 'orderList' : 
		  linkPath = "javascript:location.href='" + contextPath + "/front/mypage/selectOrderListForm.do'"; break;
	  case 'zzimBasket' : 
		  linkPath = "javascript:location.href='" + contextPath + "/front/mypage/zzimBasketList.do'"; break;
	  case 'logOut'  : 
		  linkPath = "javascript:location.href='" + contextPath + "/front/login/logout.do'"; break;
	  default   : linkPath = "javascript:location.href='" + contextPath + "/front/maindisp/main.do'"; break;
	}
	return linkPath;
}



/**
 * B2B FRONT 고객센터 LEFT MENU 
 * 
 * 메뉴 호출시 req
 * 고객센터(공지사항)       announSbjt
 * 고객센터(FAQ)            faq
 */
getFrontCustomerLeft = function(req) {
	var contextPath = getContextPath();
	var menu1 = req == 'announSbjt' ?  "class='on'" : "";
	var menu2 = req == 'faq' ?  "class='on'" : "";
	
	var conLeftStr = '		<div id="conLeft"> ';
	conLeftStr += '				<h2><img src='+_imgUrl+'/bb/front/customer/lnb-customer.gif" alt="고객센터 롯데마트 B2B몰 고객센터입니다."></h2> ';
	conLeftStr += '				<ul> ';
	conLeftStr += '					<li ' + menu1 + '><a href="javascript:location.href=\'' + contextPath + '/front/announ/announList.do\'">공지사항</a></li> ';
	conLeftStr += '					<li ' + menu2 + '><a href="javascript:location.href=\'' + contextPath + '/front/faq/faqList.do\'">FAQ</a></li> ';
	conLeftStr += '				</ul> ';
	conLeftStr += '			</div> ';

	return conLeftStr;
}

/**
 * B2B FRONT Paging
 * 
 */
getFrontPaging = function(currentPageNo, pageSize, recordCountPerPage, totalRecordCount) {
	currentPageNo = parseInt(currentPageNo, 10);
	pageSize = parseInt(pageSize, 10);
	recordCountPerPage = parseInt(recordCountPerPage, 10);
	totalRecordCount = parseInt(totalRecordCount, 10);
	
	
	var firstPage = 1;                                                                         // 첫 페이지
	var maxPage = Math.ceil(totalRecordCount/recordCountPerPage);                              // 마지막 페이지
	var maxBlock = Math.ceil(maxPage/pageSize); // 마지막 블럭
	var curBlock = Math.ceil(currentPageNo/pageSize);                                          // 현재 블럭
	var startPagePerBlock = (curBlock - 1) * pageSize + 1;                                     // 블럭내 첫페이지
	var endPagePerBlock = maxBlock <= curBlock ? maxPage : curBlock * pageSize;                // 블럭내 마지막페이지
	var nextBlockFirstPage = curBlock != maxBlock ? curBlock *  pageSize + 1 : curBlock;       // 다음블럭 첫페이지
	var prevBlockFirstPage = curBlock != 1 ? (curBlock - 2) * pageSize + 1 : curBlock;         // 이전블럭 첫페이지
//	var prevPageNo = currentPageNo == 0 ? currentPageNo : currentPageNo - 1;                   // 이전 페이지
//	var nextPageNo = currentPageNo == maxPage ? currentPageNo : currentPageNo + 1;             // 다음페이지
	maxBlock = maxBlock == (undefined || 0) ? 1 : Math.ceil(maxPage/pageSize);
	
	
	psgeStr = '<div class="page"> ';
	psgeStr += '	<!-- 이동할 페이지가 없을 경우 : 이미지파일명 XXX-off.gif --> ';
	
	// 첫페이지 인 경우
	if (curBlock == 1) {
		psgeStr += '	<span><img src="'+_imgUrl+'/bb/front/common/btn-pageFirst-off.gif" alt="" /></span> ';
		psgeStr += '	<span><img src="'+_imgUrl+'/bb/front/common/btn-pagePrev-off.gif" alt="이전리스트" /></span> ';
	} else {
		psgeStr += '	<span><a href="javascript:fn_link_page(' + firstPage + ');"><img src="'+_imgUrl+'/bb/front/common/btn-pageFirst.gif" alt="처음페이지" /></a></span> ';
		psgeStr += '	<span><a href="javascript:fn_link_page(' + prevBlockFirstPage + ');"><img src="'+_imgUrl+'/bb/front/common/btn-pagePrev.gif" alt="이전리스트" /></a></span> ';
	}
	psgeStr += '	<span class="page_num"> ';
	for (var idx=startPagePerBlock; idx <= endPagePerBlock; idx++) {
		if (idx == currentPageNo) {
			psgeStr += '		<strong>'+ currentPageNo +'</strong> ';
		} else {
			psgeStr += '		<a href="javascript:fn_link_page('+ idx + ');">'+ idx + '</a> ';
		}
	}
	psgeStr += '	</span> ';
	// 마지막페이지 인경우
	if (curBlock == maxBlock) {
		psgeStr += '	<span><img src="'+_imgUrl+'/bb/front/common/btn-pageNext-off.gif" alt="다음리스트" /></span> ';
		psgeStr += '	<span><img src="'+_imgUrl+'/bb/front/common/btn-pageEnd-off.gif" alt="" /></span> ';
	} else {
		psgeStr += '	<span><a href="javascript:fn_link_page(' + nextBlockFirstPage + ');"><img src="'+_imgUrl+'/bb/front/common/btn-pageNext.gif" alt="다음리스트" /></a></span> ';
		psgeStr += '	<span><a href="javascript:fn_link_page(' + maxPage + ');"><img src="'+_imgUrl+'/bb/front/common/btn-pageEnd.gif" alt="마지막페이지" /></a></span> ';
	}
	psgeStr += '</div> ';
	return psgeStr;
}



/**
 * B2B FRONT MYPAGE LEFT MENU 
 * 
 * 메뉴 호출시 req
 * 찜바구니        wishList
 * 단골상품        freqProd
 * 주문내역        ordDtl
 * 예치금내역      dpamt
 * 미배송환불        deliDef
 * 상품요청        prodReq
 * 회원정보수정    memInfoChg
 * 회원탈퇴신청    memOutAppl
 */
getFrontMyPageLeft = function(req) {
	var contextPath = getContextPath();
	var menu1 = req == 'wishList' ?  "class='on'" : "";
	var menu2 = req == 'freqProd' ?  "class='on'" : "";
	var menu3 = req == 'ordDtl' ?  "class='on'" : "";
	var menu4 = req == 'dpamt' ?  "class='on'" : "";
	var menu5 = req == 'deliDef' ?  "class='on'" : "";
	var menu6 = req == 'prodReq' ?  "class='on'" : "";
	var menu7 = req == 'memInfoChg' ?  "class='on'" : "";
	var menu8 = req == 'memOutAppl' ?  "class='on'" : "";
	

	var conLeftStr = '		<div id="conLeft"> ';
	conLeftStr += '			<h2><img src="'+_imgUrl+'/bb/front/mypage/lnb-mypage.gif" alt="마이페이지 고객님의 정보를 한눈에 확인하세요."></h2> ';
	conLeftStr += '			<ul> ';
	conLeftStr += '				<li ' + menu1 + '><a href="javascript:location.href=\'' + contextPath + '/front/mypage/zzimBasketList.do\'">찜바구니</a></li> ';
	conLeftStr += '				<li ' + menu2 + '><a href="javascript:location.href=\'' + contextPath + '/front/mypage/selectFrqOrdProdListForm.do\'">단골상품</a></li> ';
	conLeftStr += '				<li ' + menu3 + '><a href="javascript:location.href=\'' + contextPath + '/front/mypage/selectOrderListForm.do\'">주문내역</a></li> ';
	conLeftStr += '				<li ' + menu4 + '><a href="javascript:location.href=\'' + contextPath + '/front/mypage/depositList.do\'">예치금내역</a></li> ';
	conLeftStr += '				<li ' + menu5 + '><a href="javascript:location.href=\'' + contextPath + '/front/mypage/selectOrderRepayListForm.do\'">미배송환불</a></li> ';
	conLeftStr += '				<li ' + menu6 + '><a href="javascript:location.href=\'' + contextPath + '/front/mypage/prodreq.do\'">상품요청</a></li> ';
	conLeftStr += '				<li ' + menu7 + '><a href="javascript:location.href=\'' + contextPath + '/front/mypage/goModifyMemberInfo.do\'">회원정보수정</a></li> ';
	conLeftStr += '				<li ' + menu8 + '><a href="javascript:location.href=\'' + contextPath + '/front/mypage/requestOutMember.do\'">회원탈퇴신청</a></li> ';
	conLeftStr += '			</ul> ';
	conLeftStr += '		</div> ';

	return conLeftStr;
}

/**
 * 퀵상품 취득
 * 
 */
getQuickMenu = function() {
	var contextPath = getContextPath();
	$.post(contextPath + "/front/maindisp/quickMenu.do", {},
		function (data) {    
             $("#quickDiv").html(data);
        }
	);
}



goFrontLogOut = function(){
	if( confirm("로그아웃 하시겠습니까?")){
		top.location.href='logout.do';
	}
}

/**
 * 카렌더 호출
 */
openIFCalendar = function(id) {
	$('#'+ id).datepicker('show');
	setTimeout("callCalendar('" + id + "')", 50); 
}

openCalendar = function(id) {
	if ($('#ui-datepicker-div').css('display') == 'block') {
		$('#' + id).datepicker('hide');
	} else {
		$('#' + id).datepicker('show');
	}
}

findForm = function(forms, findFormId) {
	var frmLen = forms.length;
	var frm = null;
	for (var i=0; i < frmLen; i++) {
		if (forms[i].id == findFormId) {
			frm = forms[i];
		}
	}
	return frm;
}

callCalendar = function(id) {
	var off = $('#ui-datepicker-div').offset();
	$("#calDiv").css( "top",  off.top).css( "left", off.left);
	$("#calDiv").height($('#ui-datepicker-div').height() + 18).width($('#ui-datepicker-div').width());
	$("#calCont").height($('#ui-datepicker-div').height() + 18).width($('#ui-datepicker-div').width() + 6);
	$('#calDiv').css('display', 'block');
}

var cellModMap = new Array(); 

/**
 * 숫자 정렬
 */
sortNumber = function(a,b) {
	return a - b;
}

getModRowMap = function() {
	return this.cellModMap;
}

setModRowMap = function(cellModMap) {
	this.cellModMap = cellModMap;
}

// 처음 값 히든 처리
processFirstHidden = function(gridObj, hidCell) {
	
//	var firstCellValHid = "";
	var firstCellVal = "";
	var firstCellHidVal = "";
	for (var i=0; i < gridObj.GetRowCount(); i++) {
//		firstCellValHid += "<input type='hidden' id='firstCellVal' name='firstCellVal' value='" + gridObj.GetCellValue(hidCell, i) + "'>";
		firstCellVal += gridObj.GetCellValue(hidCell, i) + ",";
		firstCellHidVal += gridObj.GetCellHiddenValue(hidCell, i) + ",";

	}
	firstCellVal = firstCellVal.substring(0,firstCellVal.length -1);
	firstCellHidVal = firstCellHidVal.substring(0,firstCellHidVal.length -1);
	
//	firstCellValHid += "<input type='hidden' id='firstCellVal' name='firstCellVal' value='" + firstCellVal + "'>";
	
	
	document.getElementById("firstCellValDiv").innerHTML = "<input type='hidden' id='firstCellVal' name='firstCellVal' value='" + firstCellVal + "'>";
	document.getElementById("firstCellHidValDiv").innerHTML = "<input type='hidden' id='firstCellHidVal' name='firstCellHidVal' value='" + firstCellHidVal + "'>";
}

getContextPath = function() {
//	  var path = './';
//	  var e = document.createElement('span');
//	  e.innerHTML = '<a href="' + path + '" />';
//	  url = e.firstChild.href;
	  
//	  var url2 = document.URL;
//	  alert("url2 :" + url2);
	var url = document.URL;
	var p = url.split('/');
	var retContextPath = p[3] == 'front' ? "" : '/'+ p[3];
	  return retContextPath;
}

processHidden = function(gridObj) {
	var keyArr = new Array();
	
	var chkRowArr = new Array();
	// 체크된 로우 취득
	for(var i = 0; i < gridObj.GetRowCount(); i++) { //그리드 데이터의 로우수를 반환한다. 
		if(gridObj.GetCellValue("SELECTED", i) == "1") {//지정한 셀의 값을 가져온다.
			chkRowArr[chkRowArr.length] = String(i);
		}
	}
	
	
	var chkRowBool = false;
	// 체크된 수정된 셀 취득  key : 수정 로우
	for (var key in cellModMap) {	
		for(var i = 0; i < chkRowArr.length; i++) {
			if(chkRowArr[i] == key) {
				chkRowBool = true;
				break;
			} 
		}
		// 체크된 로우 key 취득
		if (chkRowBool == true) {
			keyArr[keyArr.length] = key;
		}
		chkRowBool = false;;
	}

//	keyArr.sort(sortNumber);
	
	var modCellRows = "";
	var modCellRowsArr = new Array();
	for (var i=0; i < keyArr.length; i++) {
		modCellRowsArr[modCellRowsArr.length] = parseInt(keyArr[i]);
	}
	modCellRowsArr.sort(sortNumber);
	
	for (var i=0; i < modCellRowsArr.length; i++) {
		modCellRows += modCellRowsArr[i] + ",";
	}
	
	var modCellRowVal = "";
	for (var i=0; i < modCellRowsArr.length; i++) {
		modCellRowVal += cellModMap[modCellRowsArr[i]] + ",";
	}
	
	modCellRows = modCellRows.substring(0, modCellRows.length -1);
	modCellRowVal = modCellRowVal.substring(0, modCellRowVal.length -1);
	
	
	document.getElementById("modCellRowDiv").innerHTML = "<input type='hidden' id='modCellRows' name='modCellRows' value='" + modCellRows + "'>";
	document.getElementById("modCellValDiv").innerHTML = "<input type='hidden' id='modCellRowVal' name='modCellRowVal' value='" + modCellRowVal + "'>";
}




/**
 * form 데이타 설정
 */
setAllParamIdVal = function(frm, gridObj, currentPage, mode) {
	gridObj.setParam('mode', mode);
	gridObj.setParam('currentPage', currentPage);
	var len = frm.elements.length;
	for(var i = 0; i < len; i++) {
		if (frm[i].id != undefined && frm[i].id != "" && frm[i].id != "WG1" && frm[i].type.toUpperCase() != 'BUTTON') {
			gridObj.setParam(frm[i].id, frm[i].value);
//			alert("id:" + frm[i].id + ", value:" +  frm[i].value);
		}
	}
}

setUnitComma = function(str) {
	return Number(String(str).replace(/\..*|[^\d]/g, "")).toLocaleString().slice(0, -3);
}

removeUnitComma = function(str) {
	return str.replace(/,/g, "");
}


checkNumber = function(obj) {	
	var objVal = removeUnitComma(obj.value);
	if (/[^0-9]/g.test(objVal)) {
		alert("숫자만 입력 가능합니다.");
		
		var objIdx = 0;
		for (var i=0; i < objVal.length; i++) {
			if (/[^0-9]/.test(objVal.substring(i, i+1))) {
				objIdx = i;
				break;
			}
		}
		obj.value = objVal.substring(0, objIdx);
		if (obj.value.length == 0) {
			obj.value = 0;
		}
		obj.focus();
	} 

	if (objVal.length >= 1 && objVal.indexOf('0') == 0) {
		alert("입력하실 수는 0보다 큰 정수를 입력하시기 바랍니다.");
		obj.value = "";
	}
	
	if (obj.value != "") {
		obj.value = setUnitComma(obj.value);
	}
}


checkFloat = function(pcent) {
	var pcentVal = pcent.value;
	var pcentValArr = pcentVal.split(".");

	if (/^[^0-9]/.test(pcentVal) || /[^\.0-9]/g.test(pcentVal)) {
		alert("실수만 입력가능합니다.");
		pcent.focus();
		var numIdx = 0;
		for (var i=0; i < pcentVal.length; i++) {
			if (/[^\.0-9]/g.test(pcentVal.substring(i, i+1))) {
				numIdx = i;
				break;
			}
		}
		pcent.value = pcentVal.substring(0, numIdx);
		return;
	} 
	
	if (pcentVal.split(".").length > 2) {
		alert("입력하신 값에 오류가 있습니다.");
		pcent.focus();
		var cntIdx = 0;
		var numIdx = 0;
		for (var i=0; i < pcentVal.length; i++) {
			if (pcentVal.substring(i, i+1) == '.') {
				cntIdx++;
			}
			if (cntIdx == 2) {
				numIdx = i;
				break;
			}
		}
		pcent.value = pcentVal.substring(0, numIdx);
		pcent.style.backgroundColor = 'Lime'; 
		return;
	} 

	// 소수 첫째자리에서 끊기 
	if (pcentValArr.length == 2 && pcentValArr[1].length > 1) {
		var numIdx = 0;
		for (var i=0; i < pcentVal.length; i++) {
			if (pcentVal.substring(i, i+1) == '.') {
				numIdx = i;
				break;
			}
		}
		if (pcentVal.substring(0, numIdx + 1).split(".")[1] == "") {
			pcent.value = pcentVal.substring(0, numIdx + 2);
		} 			
	}
}

getTempTable = function(height) {
	var tmpTab = '<table height=' + height + 'px width="100%" border="0" cellspacing="0" cellpadding="0">';
	tmpTab += '<tr>';
	tmpTab += '<td align="center" valign="middle"><img src="'+_imgUrl+'/bb/common/bigBlackWaiting.gif" alt="Loading. Please wait." /></td>';
	tmpTab += '</tr>';
	tmpTab += '</table>';
	
	return tmpTab;
}

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
	centerPopupWindow : function(targetUrl, windowName, properties, isQuick) {
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
		if (isQuick == true) {
			setTimeout("getQuickMenu()", 1500);
		}
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
 * 상품 팝업
 */
function goFrontProdPopup(val){
	var contextPath = getContextPath();
	var targetUrl = contextPath+'/front/proddisp/prodDetailPopup.do?prod_cd='+val;
	Common.centerPopupWindow(targetUrl, 'prodPopup', {width : 900, height : 654}, true);
}

/**
 * no 이미지 처리
 */
function noImgError(val){
	
	$('img').each(function(){
		var instance = $(this);
		var img = new Image();

		$(img).error(function(){
			if(val == '1'){
				instance.attr('src',_imgUrl+'/bb/front/common/product-default-60-60.jpg');
			}else if(val == '2'){
				instance.attr('src',_imgUrl+'/bb/front/common/product-default-88-88.jpg');
			}else if(val == '3'){
				instance.attr('src',_imgUrl+'/bb/front/common/product-default-400-400.jpg');
			}else{
				instance.attr('src',_imgUrl+'/bb/front/common/product-default-88-88.jpg');
			}
		}).attr('src', instance.attr('src'));
	});
}
