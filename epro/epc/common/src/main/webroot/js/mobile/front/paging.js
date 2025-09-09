/**
 * 페이징 인덱스 HTML을 얻습니다.
 * @param totalCntStr    : 총 row 수
 * @param curPageStr     : 현제 페이지 번호
 * @param rowsPerPageStr : 페이지당 표시할 row 갯수
 * @param funcName       : 페이지 번호 클릭시 호출할 function Name
 * @param pagingDivId    : 화면에 표시할 DIV ID Name
 * @return
 */

function setLMPaging(totalCntStr, rowsPerPageStr, curPageStr, funcName, pagingDivId, gb ) {
	var totalCnt = 0;
	var curPage = 0;
	var rowsPerPage = 0;
	try{
		totalCnt = parseInt(totalCntStr, 10);		
		rowsPerPage = parseInt(rowsPerPageStr, 10);
		curPage = parseInt(curPageStr, 10);
		document.getElementById(pagingDivId).innerHTML = render(totalCnt, curPage, rowsPerPage, funcName, gb);
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
function render(totalCnt, curPage, rowsPerPage, funcName, gb){
	var lastPageNo;					// 마지막 페이지 번호
	var fromPageNo;					// 페이지 인덱스 시작 번호 
	var toPageNo;					// 페이지 인덱스 끝 번호
	var previousPage;				// 이전 페이지
	var nextPage;					// 다음 페이지
	var indexSize = 10;				// 페이지 인덱스 크기
	
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
    
	var gubun = gb;
	if(typeof gubun === "undefined" ) 
		gubun = '';
	
	if(gubun != '' && gubun == 'other')
		return getRenderHtmlOther(totalCnt,fromPageNo, toPageNo, lastPageNo, curPage,previousPage, nextPage, funcName);
	else
		return getRenderHtml(totalCnt,fromPageNo, toPageNo, lastPageNo, curPage,previousPage, nextPage, funcName);
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


function getRenderHtml(totalCnt, fromPageNo, toPageNo, lastPageNo, curPage, previousPage, nextPage, funcName){
	var htmlString = "";
	htmlString += "<div class=\"footer-paging\" >";
    // 이전 페이지 출력
    if(previousPage !='1'){
    	htmlString += "<a href=\"#\" onclick=\"javascript:" + funcName + "('" + previousPage + "');\" >이전</a>";
    }else{
    	htmlString += "<a class=\"none\" href=\"#\">이전</a>";
    }
    

    // 페이지 인덱스
    htmlString += "<select class=\"wrap-select-paging\" onchange='"+funcName+ "('" + i + "');\">";
    for (var i=fromPageNo; i<=toPageNo; i++) {
    	
    	if (i == curPage){
    		htmlString += "<option checked>"+i+" / "+ toPageNo+"</option>";
    	}else{
    		htmlString += "<option>"+i+" / "+ toPageNo+"</option>";
    	}
    }
    
    // 이후 페이지 출력
    if (curPage == nextPage) {
        htmlString += "<a class=\"none\" href=\"#\">다음</span>";
    } else {
    	htmlString += "<a href=\"#\" onclick=\"javascript:" + funcName + "('" + nextPage + "');\" >다음</a></div>";
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
 * @param img
 */


function getRenderHtmlOther(totalCnt, fromPageNo, toPageNo, lastPageNo, curPage, previousPage, nextPage, funcName){
	var htmlString = "";
	
    // 이전 페이지 출력
    if(previousPage !='1'){
    	htmlString += "<span><a href='#' class='rollover' style='cursor:pointer' onclick=\"javascript:" + funcName + "('" + previousPage + "');\"><img src='//simage.lottemart.com/images/mobile/contentimg/event/C0070365_600_pagePrev.jpg' alt='이전리스트'></span>";
    }else{
    	// htmlString += "<span></span>";
    }
    htmlString += "<span class=\"page_num\">";
    // 페이지 인덱스
    for (var i=fromPageNo; i<=toPageNo; i++) {

    	if (i>fromPageNo) {
            //htmlString += "<span class=\"pageSeparate\" ></span>";
        }
    	
    	if (i == curPage){
    		htmlString += " <strong>" + i + "</strong>";
    	}else{
    		htmlString += " <a href='#' onclick=\"javascript:" + funcName + "('" + i + "');\" style='cursor:pointer' >" + i + "</a>";
    	}
    }
    htmlString += "</span>";

    // 이후 페이지 출력
    if (curPage == nextPage) {
        // htmlString += "<span></span>";
    } else {
    	htmlString += " <span><a href='#' class='rollover' style='cursor:pointer' onclick=\"javascript:" + funcName + "('" + nextPage + "');\"><img src='//simage.lottemart.com/images/mobile/contentimg/event/C0070365_600_pageNext.jpg' alt='다음리스트'></span>";
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





/*notice js**/
//페이징 구현
function pagingNotice(page,urlPage){
	if (urlPage==null) urlPage="/mobile/PMWMCAT0004.do?cPage=";
	//var total = ${(totalCount/10)+(1-((totalCount/10)%1))%1};
	
	if(page<1){
		alert("<spring:message code='mobile.page.pre'/>");
	}
	else{
		if(page>total){
			alert("<spring:message code='mobile.page.next'/>");
		}
		else{
    		$.ajax({
    			url: urlPage+page,
   				type: "POST",
   				dataType:"html",
   				context: document.body,
   				async: false,
	   			success:function(data){
 					noticeList(data);
 					clickedTextColor();
    			}
   			});
   		}
	}
}

// 페이징 구현
function noticeList(data){
	
	var temp = $(data).find('noticeList');
	
	$('noticeList').remove();
  	$(temp).appendTo('.article:first');
  	delete(temp);
  	
//  	var total = ${(totalCount/10)+(1-((totalCount/10)%1))%1};
	var cpage = $('.wrap-select-paging').val();
	
  	if(cpage==1){
  		$('#pre').addClass('none');	
  	}
  	if(cpage==total){
  		$('#next').addClass('none');	
  	}
}

//페이징 위한 페이지 선택 값 
function selectedPage(){
	var selectedPage = $('.wrap-select-paging').val();
	paging(selectedPage);
}

// 클릭된 타이틀의 색 변경
function clickedTextColor(){
	var a_table = document.getElementById('table_03');
	var a_tr = a_table.getElementsByTagName('tr');
	var a_td = a_table.getElementsByTagName('td');

	for(var i=0;i<a_tr.length;i++){
		if(a_tr[i] != a_tr[0]){
			a_tr[i].onclick = function(){
				var thumbs = this.parentNode.getElementsByTagName('tr');
				for(var k=0;k<thumbs.length;k++){
					if(this==thumbs[k]){
						for(var l=0;l<a_td.length;l++){
							if(a_td[l].className=='num') a_td[l].style.color = '#4c4c4c';
							if(a_td[l].className=='title') a_td[l].getElementsByTagName('a')[0].style.color = '#333';
							if(a_td[l].className=='date') a_td[l].style.color = '#808080';
						}
						for(var j=0;j<3;j++){
							a_tr[k+1].getElementsByTagName('td')[j].style.color = '#ee1c24';
						}
						a_tr[k+1].getElementsByTagName('a')[0].style.color = '#ee1c24';
					}
				}
			}
		}
	}
	
	
}

