var CURR_PAGE = 0;
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
	//초기값 셋팅
	var totalCnt = 0;
	var curPage = 0;
	var rowsPerPage = 0;

	if (rowsPerPageStr == "0") {
		rowsPerPageStr = "50";
	}
	try {
		totalCnt = parseInt(totalCntStr, 10);		
		rowsPerPage = parseInt(rowsPerPageStr, 10);
		curPage = parseInt(curPageStr, 10);
		document.getElementById(pagingDivId).innerHTML = render(totalCnt, curPage, rowsPerPage, funcName, gubun);
	}catch(e) {
		alert(e);
	}
}

/**
 * 페이징 인덱스 없이 총개수 부분의 HTML만 얻습니다.
 * @param totalCntStr
 * @param rowsPerPageStr
 * @param curPageStr
 * @param funcName
 * @param pagingDivId
 */
function setLMNoPaging(totalCntStr, rowsPerPageStr, curPageStr, funcName, pagingDivId ) {
	var totalCnt = 0;
	
	try{
		totalCnt = parseInt(totalCntStr, 10);		
		document.getElementById(pagingDivId).innerHTML = renderOnlyTotal(totalCnt);
	}catch(e) {
		alert(e);
	}
}

// 3자리마다 컴마 찍어주기
function setComma(orgNum) {
	return orgNum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

/**
 * 페이지 인덱스 설정
 * @param totalCnt
 * @param curPage
 * @param rowsPerPage
 * @param funcName
 */
function render(totalCnt, curPage, rowsPerPage, funcName, gubun) {
	var lastPageNo;					// 마지막 페이지 번호
	var fromPageNo;					// 페이지 인덱스 시작 번호 
	var toPageNo;					// 페이지 인덱스 끝 번호
	var previousPage;				// 이전 페이지
	var nextPage;					// 다음 페이지
	var indexSize = (gubun == "2" ? 5 : 10);				// 페이지 인덱스 크기
	
	if (curPage < 1) {
		curPage = 1 ;
	}
	
	if (totalCnt == 0) {
		lastPageNo = 1;
	} else {
		lastPageNo = Math.floor(totalCnt / rowsPerPage);
		if ((totalCnt % rowsPerPage) != 0) {
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
	if (pageCount < toPageNo) {
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
    
    return getRenderHtml(totalCnt, fromPageNo, toPageNo, lastPageNo, curPage
    				   , previousPage, nextPage, funcName, rowsPerPage, gubun);
}

/**
 * 페이지 부분에 총개수만 설정
 * @param totalCnt
 * @param curPage
 * @param rowsPerPage
 * @param funcName
 */
function renderOnlyTotal(totalCnt) {
	
	var htmlString = "";
	
	htmlString += "<p class=\"total\">[총건수 <span>";
	if (totalCnt == 0) {
		htmlString += "0 </span> ]</p>";
	} else {
		htmlString += setComma(totalCnt) + "</span> ]</p>";
	}
	
	return htmlString;
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
function getRenderHtml(totalCnt, fromPageNo, toPageNo, lastPageNo, curPage, previousPage, nextPage, funcName, rowsPerPage, gubun) {
	var htmlString = "";
	var pagingGubun = gubun||"1";
	
	htmlString += "<p class=\"total\">[총건수 <span>";
	if (totalCnt == 0) {
		htmlString += "0 </span> ]</p>";
	} else {
		htmlString += setComma(totalCnt) + "</span> ]</p>";
	}
	
	htmlString += "<div class=\"paging"+pagingGubun+"\">";
	
    // 첫 페이지
    if (curPage == 1) {
    	htmlString += "<span class=\"fst\" ></span>";
    } else {
    	//htmlString += "<span class=\"fst\" onclick=\"javascript:" + funcName + "(1);\" ></span>";
    	htmlString += "<span class=\"fst\" onclick=\"javascript:" + funcName + "(1);\" ><a href=\"#\"><img src=\"/images/pp/common/layout/btn_first.gif\" alt=\"첫페이지\" class=\"middle\" /></a></span>";
    }
    
    // 이전 페이지 출력
    if (previousPage !='1') {
    	//htmlString += "<span class=\"pre\" onclick=\"javascript:" + funcName + "('" + previousPage + "');\" ></span>";
    	htmlString += "<span class=\"pre\" onclick=\"javascript:" + funcName + "('" + previousPage + "');\" ><a href=\"#\"><img src=\"/images/pp/common/layout/btn_prev.gif\" alt=\"이전페이지\" class=\"middle\" /></a></span>";
    } else {
    	htmlString += "<span class=\"pre\" ></span>";
    }
    

    // 페이지 인덱스
    for (var i=fromPageNo; i<=toPageNo; i++) {

    	if (i>fromPageNo) {
            //htmlString += "<span class=\"pageSeparate\" ></span>";
        }
    	
    	var css = "";
    	if (i > fromPageNo) {
    		css = "bar";
    	}
    	
    	if (i == curPage) {
    		htmlString += "<span class=\""+css+" bar2\" ><a href=\"#\">" + i + "</a></span>";
    	} else {
    		htmlString += "<span class=\""+css+"\" onclick=\"javascript:" + funcName + "('" + i + "');\" ><a href=\"#\">" + i + "</a></span>";
    	}
    }

    // 이후 페이지 출력
    if (curPage == nextPage) {
        htmlString += "<span class=\"nxt\" ></span>";
    } else {
    	//htmlString += "<span class=\"nxt\" onclick=\"javascript:" + funcName + "('" + nextPage + "');\" ></span>";
    	htmlString += "<span class=\"nxt\" onclick=\"javascript:" + funcName + "('" + nextPage + "');\" ><a href=\"#\"><img src=\"/images/pp/common/layout/btn_next.gif\" alt=\"다음페이지\" class=\"middle\" /></a></span>";
    }


    // 마지막 페이지 출력
    if (curPage == lastPageNo) {
        htmlString += "<span class=\"end\" ></span>";
    } else {
    	//htmlString += "<span class=\"end\" onclick=\"javascript:" + funcName + "('" + lastPageNo + "');\" ></spna>";
    	htmlString += "<span class=\"end\" onclick=\"javascript:" + funcName + "('" + lastPageNo + "');\" ><a href=\"#\"><img src=\"/images/pp/common/layout/btn_end.gif\" alt=\"끝페이지\" class=\"middle\" /></a></span>";
    }
    
    htmlString += "</div>";
    
    if (gubun == "1") {
    	htmlString += "<p class=\"listCnt\"><span>";
    	htmlString += "<select name=\"rowsPerPage\" id=\"rowsPerPage\" onChange=\"javascript:doSearch();\" class=\"select\" align=\"right\">";
    	htmlString += "	<option value=\"100\" "+(rowsPerPage == 100 ? 'selected':'')+">100건</option>";
    	htmlString += "	<option value=\"500\" "+(rowsPerPage == 500 ? 'selected':'')+">500건</option>";
    	htmlString += "	<option value=\"1000\" "+(rowsPerPage == 1000 ? 'selected':'')+">1000건</option>";
    	htmlString += "	<option value=\"5000\" "+(rowsPerPage == 5000 ? 'selected':'')+">5000건</option>";
    	htmlString += "	<option value=\"10000\" "+(rowsPerPage == 10000 ? 'selected':'')+">10000건</option>";
    	htmlString += "	<option value=\"30000\" "+(rowsPerPage == 30000 ? 'selected':'')+">30000건</option>";
    	htmlString += "	<option value=\"50000\" "+(rowsPerPage == 50000 ? 'selected':'')+">50000건</option>";
    	htmlString += "</select>";
    	htmlString += "</span></p>";
    } else if (gubun == "2") {
    	htmlString += "<p class=\"listCnt\"><span>";
    	htmlString += "<select name=\"rowsPerPage\" id=\"rowsPerPage\" onChange=\"javascript:doSearch();\" class=\"select\" align=\"right\">";
    	htmlString += "	<option value=\"50\" "+(rowsPerPage == 50 ? 'selected':'')+">50건</option>";
    	htmlString += "	<option value=\"1000\" "+(rowsPerPage == 1000 ? 'selected':'')+">1000건</option>";
    	htmlString += "	<option value=\"10000\" "+(rowsPerPage == 10000 ? 'selected':'')+">10000건</option>";
    	htmlString += "	<option value=\"65000\" "+(rowsPerPage == 65000 ? 'selected':'')+">65000건</option>";
    	htmlString += "</select>";
    	htmlString += "</span></p>";
    } else {
    	htmlString += "<p class=\"listCnt\"><span>";
    	htmlString += "<select name=\"rowsPerPage\" id=\"rowsPerPage\" onChange=\"javascript:doSearch();\" class=\"select\" align=\"right\">";
    	htmlString += "	<option value=\"10\" "+(rowsPerPage == 10 ? 'selected':'')+">10건</option>";
    	htmlString += "	<option value=\"30\" "+(rowsPerPage == 30 ? 'selected':'')+">30건</option>";
    	htmlString += "	<option value=\"50\" "+(rowsPerPage == 50 ? 'selected':'')+">50건</option>";
    	htmlString += "	<option value=\"100\" "+(rowsPerPage == 100 ? 'selected':'')+">100건</option>";
    	htmlString += "	<option value=\"500\" "+(rowsPerPage == 500 ? 'selected':'')+">500건</option>";
    	htmlString += "	<option value=\"1000\" "+(rowsPerPage == 1000 ? 'selected':'')+">1000건</option>";
    	htmlString += "</select>";
    	htmlString += "</span></p>";
    }

    return htmlString;
}


function getRenderHtml_OLD(totalCnt, fromPageNo, toPageNo, lastPageNo, curPage, previousPage, nextPage, funcName) {
	var htmlString = "";
	
	
	htmlString += "<p class=\"total\">[총건수 <span>";
	if (totalCnt == 0) {
		htmlString += "0 </span> ]</p>";
	} else {
		htmlString += totalCnt + "</span> ]</p>";
	}
	
	// 첫 페이지
	if (curPage == 1) {
		htmlString += "<span class=\"fst\" ></span>";
	} else {
		htmlString += "<span class=\"fst\" onclick=\"javascript:" + funcName + "(1);\" ></span>";
	}
	
	// 이전 페이지 출력
	if (previousPage !='1') {
		htmlString += "<span class=\"pre\" onclick=\"javascript:" + funcName + "('" + previousPage + "');\" ></span>";
	} else {
		htmlString += "<span class=\"pre\" ></span>";
	}
	
	
	// 페이지 인덱스
	for (var i=fromPageNo; i<=toPageNo; i++) {
		
		if (i>fromPageNo) {
			htmlString += "<span class=\"pageSeparate\" ></span>";
		}
		
		if (i == curPage) {
			htmlString += "<span class=\"bar2\" >" + i + "</span>";
		} else {
			htmlString += "<span class=\"bar\" onclick=\"javascript:" + funcName + "('" + i + "');\" >" + i + "</span>";
		}
	}
	
	// 이후 페이지 출력
	if (curPage == nextPage) {
		htmlString += "<span class=\"nxt\" ></span>";
	} else {
		htmlString += "<span class=\"nxt\" onclick=\"javascript:" + funcName + "('" + nextPage + "');\" ></span>";
	}
	
	
	// 마지막 페이지 출력
	if (curPage == lastPageNo) {
		htmlString += "<span class=\"end\" ></span>";
	} else {
		htmlString += "<span class=\"end\" onclick=\"javascript:" + funcName + "('" + lastPageNo + "');\" ></spna>";
	}
	
	return htmlString;
}