/**
 * 페이징 인덱스 HTML을 얻습니다.
 * @param totalCntStr    : 총 row 수
 * @param curPageStr     : 현제 페이지 번호
 * @param rowsPerPageStr : 페이지당 표시할 row 갯수
 * @param funcName       : 페이지 번호 클릭시 호출할 function Name
 * @param pagingDivId    : 화면에 표시할 DIV ID Name
 * @return
 */
function setLMPaging(totalCntStr, rowsPerPageStr, curPageStr, funcName, pagingDivId, gubun ) {
	var totalCnt = 0;
	var curPage = 0;
	var rowsPerPage = 0;

	try{
		totalCnt = parseInt(totalCntStr, 10);		
		rowsPerPage = parseInt(rowsPerPageStr, 10);
		curPage = parseInt(curPageStr, 10);
		document.getElementById(pagingDivId).innerHTML = render(totalCnt, curPage, rowsPerPage, funcName, gubun);
	}catch(e){
		alert(e);
	}
}

/**
 * 페이지 인덱스 설정
 * @param totalCnt
 * @param curPage
 * @param rowsPerPage
 * @param funcName
 */
function render(totalCnt, curPage, rowsPerPage, funcName, gubun){
	var lastPageNo;					// 마지막 페이지 번호
	var fromPageNo;					// 페이지 인덱스 시작 번호 
	var toPageNo;					// 페이지 인덱스 끝 번호
	var previousPage;				// 이전 페이지
	var nextPage;					// 다음 페이지
	var indexSize = (gubun == "2" ? 5 : 10);				// 페이지 인덱스 크기
	
	if(curPage < 1){
		curPage = 1 ;
	}
	
	if(totalCnt == 0){
		lastPageNo = 1;
	}else{
		lastPageNo = Math.floor(totalCnt / rowsPerPage);
		if((totalCnt % rowsPerPage) != 0){
			lastPageNo++;
		}
	}
	
    if (curPage > lastPageNo) {
        curPage = lastPageNo;
    }
    
    // fromPageNo, toPageNo 계산
	pageCount = (totalCnt > rowsPerPage) ? Math.floor(totalCnt / rowsPerPage) : 1;
	if (pageCount < totalCnt / rowsPerPage) {
		pageCount += 1;
	}

	fromPageNo = Math.floor((curPage-1) / indexSize) * indexSize + 1;
	toPageNo = Math.floor((curPage-1) / indexSize) * indexSize + indexSize;
	if (pageCount < toPageNo){
		toPageNo = pageCount;
	}
    
	// previousPage, nextPage 계산
	previousPage = fromPageNo - 1;
	if (previousPage < 1) {
		previousPage   = 1;
	}
	nextPage = toPageNo + 1;
	if (nextPage > lastPageNo) {
		nextPage = lastPageNo;
	}
    
    return getRenderHtml(totalCnt,fromPageNo, toPageNo, lastPageNo, curPage
        ,previousPage, nextPage, funcName, rowsPerPage, gubun);
}

/**
 * 페이지 인덱스 렌더링
 * @param totalCnt
 * @param fromPageNo
 * @param toPageNo
 * @param lastPageNo
 * @param curPage
 * @param previousPage
 * @param nextPage
 * @param funcName
 */


function getRenderHtml(totalCnt, fromPageNo, toPageNo, lastPageNo, curPage, previousPage, nextPage, funcName, rowsPerPage, gubun){
	var htmlString = "";
	var pagingGubun = gubun||"1";
	
	htmlString += "<table cellspacing=\"1\" cellpadding=\"2\"><tr>";
	
    // 첫 페이지
    if (curPage == 1) {
    	htmlString += "<td><table cellspacing=1 cellpadding=2><tr><td width=\"20\">&nbsp;</td></tr></table></td>";
    } else {
    	htmlString += "<td><table cellspacing=1 cellpadding=2><tr><td width=\"20\"><a href=\"javascript:" + funcName + "(1);\"><img src=\"/images/pp/common/layout/btn_first.gif\" alt=\"첫페이지\" class=\"middle\" /></a></td></tr></table></td>";
    }
    
    // 이전 페이지 출력
    if(curPage !=1){
    	htmlString += "<td><table cellspacing=1 cellpadding=2 style=\"margin-left: 5px\"><tr><td width=\"20\"><a href=\"javascript:" + funcName + "('" + previousPage + "');\"><img src=\"/images/pp/common/layout/btn_prev.gif\" alt=\"이전페이지\" class=\"middle\" /></a></td></tr></table></td>";
    }else{
    	htmlString += "<td><table cellspacing=1 cellpadding=2 style=\"margin-left: 5px\"><tr><td width=\"20\">&nbsp;</td></tr></table></td>";
    }
    

    // 페이지 인덱스
    for (var i=fromPageNo; i<=toPageNo; i++) {

    	if (i == curPage){
    		htmlString += "<td><table cellspacing=1 cellpadding=2 bgcolor=\"#4646cd\" style=\"margin-left:5px;font-size:15px;\"><tr bgcolor=\"#FFFFFF\"><td width=\"20\" style=\"text-align:center;cursor:pointer;\">"+i+"</td></tr></table></td>";
    	}else{
    		htmlString += "<td><table cellspacing=1 cellpadding=2 bgcolor=\"#666666\" style=\"margin-left:5px;font-size:15px;\"><tr bgcolor=\"#FFFFFF\"><td width=\"20\" onclick=\"javascript:" + funcName + "('" + i + "');\" style=\"text-align:center;cursor:pointer;\">"+i+"</td></tr></table></td>";
    	}
    }

    // 이후 페이지 출력
    if (curPage == nextPage) {
        htmlString += "<td><table cellspacing=1 cellpadding=2 style=\"margin-left:5px\"><tr><td width=\"20\">&nbsp;</td></tr></table></td>";
    } else {
    	htmlString += "<td><table cellspacing=1 cellpadding=2 style=\"margin-left:5px\"><tr><td width=\"20\"><a href=\"javascript:" + funcName + "('" + nextPage + "');\"><img src=\"/images/pp/common/layout/btn_next.gif\" alt=\"다음페이지\" class=\"middle\" /></a></td></tr></table></td>";
    }


    // 마지막 페이지 출력
    if (curPage == lastPageNo) {
        htmlString += "<td><table cellspacing=1 cellpadding=2 style=\"margin-left: 5px\"><tr><td width=\"20\">&nbsp;</td></tr></table></td>";
    } else {
    	//htmlString += "<span class=\"end\" onclick=\"javascript:" + funcName + "('" + lastPageNo + "');\" ></spna>";
    	htmlString += "<td><table cellspacing=1 cellpadding=2 style=\"margin-left: 5px\"><tr><td width=\"20\"><a href=\"javascript:" + funcName + "('" + lastPageNo + "');\"><img src=\"/images/pp/common/layout/btn_end.gif\" alt=\"끝페이지\" class=\"middle\" /></a></td></tr></table></td>";
    	
    }
    
    htmlString += "</tr></table>";

    return htmlString;
}