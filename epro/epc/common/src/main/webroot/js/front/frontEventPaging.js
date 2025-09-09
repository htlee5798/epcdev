/**
 * 페이징 인덱스 HTML을 얻습니다.
 * @param totalCntStr    : 총 row 수
 * @param curPageStr     : 현제 페이지 번호
 * @param rowsPerPageStr : 페이지당 표시할 row 갯수
 * @param funcName       : 페이지 번호 클릭시 호출할 function Name
 * @param pagingDivId    : 화면에 표시할 DIV ID Name
 * @return
 */
function setLMPaging(totalCntStr, rowsPerPageStr, curPageStr, funcName, pagingDivId, img ) {
	var totalCnt = 0;
	var curPage = 0;
	var rowsPerPage = 0;
	try{
		totalCnt = parseInt(totalCntStr, 10);		
		rowsPerPage = parseInt(rowsPerPageStr, 10);
		curPage = parseInt(curPageStr, 10);
		document.getElementById(pagingDivId).innerHTML = render(totalCnt, curPage, rowsPerPage, funcName, img);
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
function render(totalCnt, curPage, rowsPerPage, funcName, img){
	var lastPageNo;					// 마지막 페이지 번호
	var fromPageNo;					// 페이지 인덱스 시작 번호 
	var toPageNo;					// 페이지 인덱스 끝 번호
	var previousPage;				// 이전 페이지
	var nextPage;					// 다음 페이지
	var indexSize = 5;				// 페이지 인덱스 크기
	
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
    
	return getRenderHtml(totalCnt,fromPageNo, toPageNo, lastPageNo, curPage,previousPage, nextPage, funcName, img);
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
 * @param img
 */


function getRenderHtml(totalCnt, fromPageNo, toPageNo, lastPageNo, curPage, previousPage, nextPage, funcName, img){
	var htmlString = "";
	var paramImg = img;
	var nextImgPath = "/images/front/product/btn-pageNext.gif";
	var preImgPath = "/images/front/product/btn-pagePrev.gif";

    // 첫 페이지
	/*
    if (curPage == 1) {
    	htmlString += "<span></span>";
    } else {
    	htmlString += "<span><img src='/images/front/product/btn-pagePrev.gif' alt='처음으로' style='cursor:pointer' onclick=\"javascript:" + funcName + "(1);\" ></span>";
    }
    */
    
    // 이전 페이지 출력
    if(previousPage !='1'){
    	htmlString += "<span><img src='"+"//simage.lottemart.com/images/front/product/btn-pagePrevFF.gif"+"' alt='처음으로' style='cursor:pointer' onclick=\"javascript:" + funcName + "('" + 1 + "');\" ></span>";
    	htmlString += "<span><img src='"+preImgPath+"' alt='이전리스트' style='cursor:pointer' onclick=\"javascript:" + funcName + "('" + previousPage + "');\" ></span>";
    }else{
    	// htmlString += "<span></span>";
    }
    htmlString += "<span class=\"pagelist\">";
    // 페이지 인덱스
    for (var i=fromPageNo; i<=toPageNo; i++) {

    	if (i>fromPageNo) {
            //htmlString += "<span class=\"pageSeparate\" ></span>";
        }
    	
    	if (i == curPage){    		
    		htmlString += '<strong title="현재 페이지">' + i + "</strong>";
    	}else{
    		htmlString += "<a href=\"javascript:" + funcName + "('" + i + "');\" >" + i + "</a>";
    	}
    }
    htmlString += "</span>";

    // 이후 페이지 출력
    if (curPage == nextPage) {
        // htmlString += "<span></span>";
    } else {
    	htmlString += "<span><img src='"+nextImgPath+"' alt='다음리스트' style='cursor:pointer' onclick=\"javascript:" + funcName + "('" + nextPage + "');\" ></span>";
    	htmlString += "<span><img src='"+"//simage.lottemart.com/images/front/product/btn-pageNextBB.gif"+"' alt='마지막으로' style='cursor:pointer' onclick=\"javascript:" + funcName + "('" + lastPageNo + "');\" ></span>";
    }
    // 마지막 페이지 출력
    /*
    if (curPage == lastPageNo) {
        htmlString += "<span></span>";
    } else {
    	htmlString += "<span><img src='/images/front/product/btn-pageNext.gif' alt='마지막으로' style='cursor:pointer' onclick=\"javascript:" + funcName + "('" + lastPageNo + "');\" ></spna>";
    }
    */

    
    return htmlString;
}