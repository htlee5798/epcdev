//----- 주간 인기 찜 상품 선택 -----
function DQselectKeyword(term) {
	location.href="/search/search.do?searchTerm="+encodeURIComponent(term);
}

//----- 카테고리 펼쳐보기 -----
function DQcategory_display(id, startNum, endNum){
	var category = "";
	var cnt = 0;

	for(var i=startNum; i<endNum; i++) {
		cnt = i % 4;
		if(cnt == 0){
			category = eval(id + "_" +  i);
			if(category.style.display=="block") {
				category.style.display="none";
				document.getElementById("categoryList").src = "//simage.lottemart.com/images/front/event/btn-open-category-02_on.gif";
			} else {
				category.style.display="block";
				document.getElementById("categoryList").src = "//simage.lottemart.com/images/front/event/btn-open-category-02_off.gif";
			}
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

function DQcategory_submit() {
	document.categoryForm.submit();
}
//----- 정렬 선택시 -----
/*function DQorderBy_submit(orderBy){
	document.pageNavigator.orderBy.value = orderBy;
	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.currentPageCS.value = "1";
	document.pageNavigator.submit();
}*/
// 201603 수정
function DQorderBy_submit(){
	var orderBy = document.getElementById("orderbySelect").value;
	document.pageNavigator.orderBy.value = orderBy;
	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.currentPageCS.value = "1";
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
			document.getElementById(id).value = min;
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

//----- 인기검색어 --------//
function fn_goTrend(keyword) {
	var frm = document.pageNavigator;
	frm.searchTerm.value = keyword;
	frm.submit();
}

//------- 팝업 ---------//
function openwindow(url,searchTerm){
	window.open(url, "popup","width=420px,height=320px,scrollbars=yes,resizable=no");
	
}

//------- 배송타입 ---------//
function deliverySubmit(type){
	document.pageNavigator.deliveryType.value = type;
	document.pageNavigator.submit();
}

//----- NEW 카테고리 선택시 -----
function New_DQcategory_submit() {
	document.categoryForm.deliveryType.value ="total";
	document.categoryForm.submit();
	
}

//----- 2014.04 카테고리명으로 검색 -----
function setCategory(catNm) {
	document.pageNavigator.cateName1.value = encodeURIComponent(catNm);
	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.currentPageCS.value = "1";
	document.pageNavigator.submit();
	
}

//----- 2014.04 카테고리 창 열었을때 -----
function setOpenCategory() {
	document.pageNavigator.openCategoryDiv.value = "Y";
}

//----- 2014.04 혜택으로 검색 -----
/*function setBenefit(val) {
	if(val=="catalog"){
		if(document.getElementById("chk21").checked==true){
			document.pageNavigator.catalog.value = "Y";
		}else{
			document.pageNavigator.catalog.value = "N";
		}
	}
	else if(val=="gift"){
		if(document.getElementById("chk22").checked==true){
			document.pageNavigator.gift.value = "Y";
		}else{
			document.pageNavigator.gift.value = "N";
		}
	}
	else if(val=="enuri"){
		if(document.getElementById("chk24").checked==true){
			document.pageNavigator.enuri.value = "Y";
		}else{
			document.pageNavigator.enuri.value = "N";
		}
	}
	else if(val=="members"){
		if(document.getElementById("chk25").checked==true){
			document.pageNavigator.members.value = "Y";
		}else{
			document.pageNavigator.members.value = "N";
		}
	}
	else if(val=="noch"){
		if(document.getElementById("chk26").checked==true){
			document.pageNavigator.noch.value = "Y";
		}else{
			document.pageNavigator.noch.value = "N";
		}
	}
	else if(val=="cardDc"){
		if(document.getElementById("chk27").checked==true){
			document.pageNavigator.cardDc.value = "Y";
		}else{
			document.pageNavigator.cardDc.value = "N";
		}
	}
	else if(val=="directCoupon"){
		if(document.getElementById("chk23").checked==true){
			document.pageNavigator.directCoupon.value = "Y";
		}else{
			document.pageNavigator.directCoupon.value = "N";
		}
	}
	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.currentPageCS.value = "1";
	document.pageNavigator.submit();
	
}*/

//201603 수정
function setBenefit(val) {
	if(val=="gift"){
		if(document.getElementById("benefit-01").checked==true){
			document.pageNavigator.gift.value = "Y";
		}else{
			document.pageNavigator.gift.value = "N";
		}
	}
	else if(val=="enuri"){
		if(document.getElementById("benefit-02").checked==true){
			document.pageNavigator.enuri.value = "Y";
		}else{
			document.pageNavigator.enuri.value = "N";
		}
	}
	else if(val=="members"){
		if(document.getElementById("benefit-03").checked==true){
			document.pageNavigator.members.value = "Y";
		}else{
			document.pageNavigator.members.value = "N";
		}
	}
	else if(val=="noch"){
		if(document.getElementById("benefit-04").checked==true){
			document.pageNavigator.noch.value = "Y";
		}else{
			document.pageNavigator.noch.value = "N";
		}
	}
	else if(val=="cardDc"){
		if(document.getElementById("benefit-05").checked==true){
			document.pageNavigator.cardDc.value = "Y";
		}else{
			document.pageNavigator.cardDc.value = "N";
		}
	}

	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.currentPageCS.value = "1";
	document.pageNavigator.submit();
	
}

//------- 배송타입 ---------//
/*function setDelivery(val){
	if(val=="select") document.pageNavigator.deliveryType.value = $("#deliveryType").val();
	else document.pageNavigator.deliveryType.value = val;
	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.currentPageCS.value = "1";
	document.pageNavigator.submit();
}*/

//------- 매장배송만 보기 ---------//
function setDelivery01(){
	if(document.getElementById("setDelivery01").checked==true){
		document.pageNavigator.deliveryType.value = "01";
	}else{
		document.pageNavigator.deliveryType.value = "total";
	}
	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.currentPageCS.value = "1";
	document.pageNavigator.submit();
}
//------- 목록 리스트,갤러리 보기 ---------//
function setViewType(val){
	document.pageNavigator.viewType.value = val;
	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.currentPageCS.value = "1";
	document.pageNavigator.submit();
}
//-----2014.04 검색 내 재검색 -----
function DQ_reSearch(){
	//var searchTermExt = document.getElementById("searchTermExt").value;
	var searchTermExt = $("#searchTermExt").val();
	searchTermExt = searchTermExt.replace(/</g, '').replace(/>/g, '').replace(/&/g, '').replace(/"/g, '').replace(/'/g, '').replace(/#/g, '').replace(/^/g, '').replace(/%/g, '%25');
	//document.getElementById("searchTermExtValue").value = searchTermExt;
	$("#searchTermExtValue").val(searchTermExt);
	var cnt = searchTermExt.length;

	if(cnt < 1){
		alert(msg_common_search_termCheck);	//검색하실 내용을 입력하세요.
		//document.getElementById("searchTermExt").focus();
		$("#searchTermExt").focus();
		return;
	}else {
		//$("#reSearchForm").submit();
		document.getElementById("reSearchForm").submit();
	}
}

//-- 201603 추가
//----- 2016.03 신누리: depth 추가 -----
function setCategory(depth, catNm) {
	if(depth == 2){
		document.pageNavigator.cateName1.value = encodeURIComponent(catNm);
	}else if(depth == 3){
		document.pageNavigator.cateName2.value = encodeURIComponent(catNm);		
	}
	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.currentPageCS.value = "1";
	document.pageNavigator.submit();
	
}
//----- 2016.03 신누리: 브랜드 추가-----
function setBrand(){
			
	var obj = document.getElementsByName("brandId");
	var brandCd = "";
	
	for(var i=0; i < obj.length; i++){
		if(obj[i].checked == true){
			if(i < obj.length-1)
				brandCd += obj[i].value + ",";
			else
				brandCd += obj[i].value;
		}
	}
	document.pageNavigator.brandCd.value = brandCd;
	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.currentPageCS.value = "1";
	document.pageNavigator.submit();
}
//----- 2016.03 배송타입 추가 -----
function setDelivery(){
	var obj = document.getElementsByName("delivery");
	var deliveryType = "";
	
	for(var i=0; i < obj.length; i++){
		if(obj[i].checked == true){
			if(i < obj.length-1)
				deliveryType += obj[i].value + ",";
			else
				deliveryType += obj[i].value;
		}
	}
	document.pageNavigator.deliveryType.value = deliveryType;
	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.currentPageCS.value = "1";
	document.pageNavigator.submit();	
}

function setDeliveryCnt(val){
	document.pageNavigator.deliveryType.value = val;
	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.currentPageCS.value = "1";
	document.pageNavigator.submit();
}
//----- 2016.03 가격 추가 -----
function setPrice(){
	var minPrice = document.getElementById("minPrice").value;
	var maxPrice = document.getElementById("maxPrice").value;
	document.pageNavigator.minPrice.value = minPrice;
	document.pageNavigator.maxPrice.value = maxPrice;
	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.currentPageCS.value = "1";
	document.pageNavigator.submit();	
}
//----- 2016.03 레이어 열기/닫기 -----
$(document).ready(function(){	
	/*$('#bpLayer').click(function() {
		if($(this).attr('class') == "layer-open-btn active"){	// 레이어 열림 
			document.pageNavigator.isShow.value = 'Y';
		}else if($(this).attr('class') == "layer-open-btn"){													// 레이어 닫힘 
			$(' #isShow ').val('N');
			document.pageNavigator.isShow.value = 'N';
		}else{
			document.pageNavigator.isShow.value = 'Y';
		}
	});*/
	$('#clearLayer').click(function() {
		 document.pageNavigator.gift.value="N";
         document.pageNavigator.enuri.value="N";
         document.pageNavigator.members.value="N";
         document.pageNavigator.noch.value="N";
         document.pageNavigator.cardDc.value="N";
         document.pageNavigator.minPrice.value="";
         document.pageNavigator.maxPrice.value="";
	});
	/*$('#closeLayer').click(function() {
		document.pageNavigator.isShow.value = 'N';	
	});*/
});
