
//----- 주간 인기 찜 상품 선택 -----
function DQselectKeyword(term) {
	location.href="/search/search.do?searchTerm="+encodeURIComponent(term);
}

//----- 카테고리 펼쳐보기 -----
function DQcategory_display(id, startNum, endNum){
	var category = "";
	for(var i=startNum; i<endNum; i++) {
		category = eval(id + "_" +  i);
		if(category.style.display=="block") {
			category.style.display="none";
		} else {
			category.style.display="block";
		}
	}
}

//----- 카테고리 선택시 -----
function DQcategory_submit(cateName1, cateName2, cateName3, cateCd){
	document.pageNavigator.cateName1.value = encodeURIComponent(cateName1);
	document.pageNavigator.cateName2.value = encodeURIComponent(cateName2);
	document.pageNavigator.cateName3.value = encodeURIComponent(cateName3);
	document.pageNavigator.cateCd.value = cateCd;
	document.pageNavigator.submit();
}

//----- 조건 검색찾기 -----
function DQcondition_submit() {
	document.conditionForm.submit();
}

//----- 정렬 선택시 -----
function DQorderBy_submit(orderBy){
	document.pageNavigator.orderBy.value = orderBy;
	document.pageNavigator.submit();
}

//----- 출력개수 선택시 -----
function DQlimit_submit(LIMIT){
	document.pageNavigator.LIMIT.value = LIMIT;
	document.pageNavigator.submit();
}

//----- 상품개수 변경시 -----
function DQproduct_count(id, num, min, max){
	var cnt = document.getElementById(id).value*1;
	var rcnt;
	if((cnt + (num*1)) > 0) {
		rcnt = cnt + (num*1);
		if(rcnt < min || rcnt > max) {
			alert(fnJsMsg(msg_productOrderQty, min, max));	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
			document.getElementById(id).value = max;
		} else {
			document.getElementById(id).value = cnt + (num*1);
		}
	} else {
		document.getElementById(id).value = 1;
	}
}

//-----검색필드 선택-----
function DQfooterSelectSearchField(field) {
	document.footerSearchForm.searchField.value = field;
}
