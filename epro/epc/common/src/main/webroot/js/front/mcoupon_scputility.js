/** SCP-PROJECT : SMJANG START
 * @FileName : mcoupon_scputility.js
 * @Description : 쿠폰/스탬프 정보 처리에 사용되는 함수 모음
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   생성일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2015.03.12  장승만      신규작성
 *
 * @Copyright (C) 2000 ~ 2015 롯데정보통신(주) All right reserved.
 * </pre>
*/

// 숫자에 콤마 추가하기 (금액단위)
function setComma(n) {
    var reg = /(^[+-]?\d+)(\d{3})/;
    n += "";
    while (reg.test(n)) n = n.replace(reg, "$1" + "," + "$2");
    return n;
}

//날짜 형식 : yyyymmdd --> yyyy.mm.dd
function setDatePeriod(date) {
	date = date.trim();
	
	if (date.length < 8) return "";
	
	return date.substring(0, 4) + '.' + date.substring(4, 6) + '.' + date.substring(6, 8);
}

//sortByKey 함수는 자바스크립트에서 제공하는 sort를 이용하여 구현
function sortByKey(array, key) {
    return array.sort(
        function(a, b) {
            var x = a[key];
            var y = b[key];
            return ((x < y) ? -1 : ((x > y) ? 1 : 0));
        }
    );
}

//	JSON 데이터 정렬
function sortJSONbyField(jsonData, field, asc, fType) {
	if (jsonData == null || jsonData == "") return;

    jsonData = jsonData.sort(function(a, b) {
    	if (fType == "number") {
	        if (asc) return (a[field] > b[field]) ? 1 : ((a[field] < b[field]) ? -1 : 0);
	        else return (b[field] > a[field]) ? 1 : ((b[field] < a[field]) ? -1 : 0);
    	}
    	else if (field == "timestamps") {
    		var dt1 = a.timestamps.launched_at;
    		var dt2 = b.timestamps.launched_at;

    		if (asc) return (dt1.toLowerCase() > dt2.toLowerCase()) ? 1 : ((dt1.toLowerCase() < dt2.toLowerCase()) ? -1 : 0);
	        else return (dt2.toLowerCase() > dt1.toLowerCase()) ? 1 : ((dt2.toLowerCase() < dt1.toLowerCase()) ? -1 : 0);
    	}
    	else {
	        if (asc) return (a[field].toLowerCase() > b[field].toLowerCase()) ? 1 : ((a[field].toLowerCase() < b[field].toLowerCase()) ? -1 : 0);
	        else {
//	        	console.log("##### =====> field [%s]", field);
//	        	console.log("##### =====> a field [%s]", a[field]);
//	        	console.log("##### =====> b field [%s]", b[field]);
	        	return (b[field].toLowerCase() > a[field].toLowerCase()) ? 1 : ((b[field].toLowerCase() < a[field].toLowerCase()) ? -1 : 0);
	        }
    	}
    });

    return jsonData;
}

// 날짜 차이 계산 (date1 - date2 = ?? days)
function getDateDiff(date1, date2)
{
    var arrDate1 = date1.split(".");
    var getDate1 = new Date(parseInt(arrDate1[0]),parseInt(arrDate1[1])-1,parseInt(arrDate1[2]));
    var arrDate2 = date2.split(".");
    var getDate2 = new Date(parseInt(arrDate2[0]),parseInt(arrDate2[1])-1,parseInt(arrDate2[2]));
    
    var getDiffTime = getDate1.getTime() - getDate2.getTime();
    
    return Math.floor(getDiffTime / (1000 * 60 * 60 * 24));
}

// Parameters JSON String 만들기
function MakeStringOfJsonParameters(obj) {
	var jsonParamStr = '{';
	$.each(obj, function(index, parameter) {
		jsonParamStr += '"' + parameter.name + '":"' + parameter.value + '",';		
	});
	if (jsonParamStr.charAt(jsonParamStr.length - 1) == ',')
		jsonParamStr = jsonParamStr.substring(0, jsonParamStr.length - 1);
	jsonParamStr += '}';

	return jsonParamStr;
}

// REST 응답 메시지 체크
function CheckMessageCode(json, jqXHR) {
	var result = true, errMsg = "";

	// 조회 성공
	if (json.code != "S0000") {
		result = false;
		console.log(json.message);
	}
	
	return result;
}

/*
 * path : 전송 URL
 * params : 전송 데이터 {'q':'a','s':'b','c':'d'...}으로 묶어서 배열 입력
 * method : 전송 방식(생략가능)
 */
function post_to_url(path, params, method) {
    method = method || "post"; // Set method to post by default, if not specified.
    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    
    form.setAttribute("method", method);
    form.setAttribute("action", path);
    
    var result = $.parseJSON(params);
    $.each(result, function(key, value) {
        var hiddenField = document.createElement("input");
        
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", key);
        hiddenField.setAttribute("value", value);
        form.appendChild(hiddenField);
    });

    document.body.appendChild(form);
    form.submit();
}

// Paging 처리 (아이템 총수, 선택 페이지, 조회 함수, 출력 함수, 출력 함수 파라미터)
// 10개 페이지 단위로 나타내며 10개 단위의 내에서는 페이지를 클릭하여 이동하고
// 다음 버튼을 클릭하면 다음 10개 페이지 중 첫번째 페이지로 이동한다.
// 이전 버튼을 클릭하면 이전 10개 페이지 중 마지막 페이지로 이동한다.
function SetPageInfos(totItemCount, selPage, searchFuncName, showFuncName, redraw) {
	// 페이징 영역 지우기
	$('#pagingDiv').empty();

	// 선택 페이지의 쿠폰 리스트 보이기
	if (redraw != false)
		showFuncName(selPage);
	
	if (totItemCount < 1) return;

	var maxPage = parseInt((totItemCount - 1) / 10) + 1;

	var startPage = parseInt((selPage - 1) / 10) * 10 + 1;
	var endPage = Math.min(maxPage + 1, startPage + 10);
	
	// 페이지 설정
	var htmlString = "";

	// 이전 페이지로 이동 : 첫 페이지가 아닐 경우
	if (selPage > 10) {
		htmlString += '<span><img src="//simage.lottemart.com/images/front/product/btn-pagePrev.gif" alt="이전리스트" style="cursor:pointer" ' +
 				  	  'onclick="javascript:' + searchFuncName + '(' + (startPage - 1) + ');"></span>';		
	}
	
	// page class 설정
	htmlString += '<span class="page_num">';
 
	// 페이지 인덱스
	for (var i = startPage; i < endPage; i++) {
		if (i == selPage)
			htmlString += '<strong>' + i + '</strong>';
		else
			htmlString += '<span onclick="javascript:' + searchFuncName + '(' + i + ');" style="cursor:pointer">' + i + '</span>';
	}
	htmlString += "</span>";
	
	// 이후 페이지 출력
	if (endPage < maxPage + 1)
		htmlString += '<span><img src="//simage.lottemart.com/images/front/product/btn-pageNext.gif" alt="다음리스트" style="cursor:pointer" ' +
					  'onclick="javascript:' + searchFuncName + '(' + endPage + ');"></span>';
 
	$('#pagingDiv').html(htmlString);
}

/** SCP-PROJECT : SMJANG END **/