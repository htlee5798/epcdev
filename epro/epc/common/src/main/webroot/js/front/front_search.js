//----- 주간 인기 찜 상품 선택 -----
function DQselectKeyword(term) {
	location.href="/search/search.do?searchTerm="+encodeURIComponent(term);
}
//----- 정렬 선택시 -----
function DQorderBy_submit(orderBy){
	document.pageNavigator.orderBy.value = orderBy;
	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.currentPageCS.value = "1";
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

//------- 목록 리스트,갤러리 보기 ---------//
function setViewType(val){
	document.pageNavigator.viewType.value = val;
	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.currentPageCS.value = "1";
	document.pageNavigator.submit();
}

//----- 2016.03:depth 추가 -----
function setCategory(depth, catNm) {
	var mallDivnCd = $("#mallDivnCdVal").val();
	var searchForm = document.pageNavigator;
	
	if(mallDivnCd == "00001"){
		if(depth == 2){
			document.pageNavigator.cateName1.value = encodeURIComponent(catNm);
			document.pageNavigator.cateName2.value = "";
		}else if(depth == 3){
			document.pageNavigator.cateName2.value = encodeURIComponent(catNm);		
		}else if(depth == 0){
			document.pageNavigator.cateName1.value = "";
			document.pageNavigator.cateName2.value = "";
		}
	}else if(mallDivnCd == "00002"){
		document.pageNavigator.cateName1.value = encodeURIComponent(catNm);
	}
	
	searchForm.gift.value = "N";
	searchForm.members.value = "N";
	searchForm.cardDc.value = "N";
	searchForm.children.value = "N";
	searchForm.relation.value = "N";
	searchForm.bundle.value = "N";
	searchForm.minPrice.value = "";
	searchForm.maxPrice.value = "";
	searchForm.brandCd.value = "";
	searchForm.currentPage.value = "1";
	searchForm.currentPageCS.value = "1";
	searchForm.submit();	
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

//----- 2016.03 가격 추가 (조건추가 5.19) -----
function setPrice(){
	var minPrice = document.getElementById("minPrice").value;
	var maxPrice = document.getElementById("maxPrice").value;
	if(parseInt(minPrice) > parseInt(maxPrice)){
		alert("가격 조건이 올바르지 않습니다.");
		$("#minPrice").focus();
		return;
	}else{
		document.pageNavigator.minPrice.value = minPrice;
		document.pageNavigator.maxPrice.value = maxPrice;
		document.pageNavigator.currentPage.value = "1";
		document.pageNavigator.currentPageCS.value = "1";
		document.pageNavigator.submit();
	}
}

function fnmallType(type){
	var searchForm = document.pageNavigator;
	var searchTerm = unescape(decodeURIComponent(searchForm.searchTermList.value)).split('^')[0].replace(/#/g, '');
	
	searchForm.mallDivnCd.value = type;
	searchForm.currentPage.value = "1";
	searchForm.currentPageCS.value = "1";
	searchForm.cateName1.value = "";
	searchForm.cateName2.value = "";
	searchForm.gift.value = "N";
	searchForm.members.value = "N";
	searchForm.cardDc.value = "N";
	searchForm.children.value = "N";
	searchForm.relation.value = "N";
	searchForm.bundle.value = "N";
	searchForm.minPrice.value = "";
	searchForm.maxPrice.value = "";
	searchForm.brandCd.value = "";
	searchForm.detailOnOff.value="";
	searchForm.reFlag.value = "";
	searchForm.deliveryType.value = "";	
	searchForm.searchTerm.value =searchTerm;
	searchForm.detailSearch.value = "";
	searchForm.submit();
}

//----- 2016.03 레이어 열기/닫기 -----
$(document).ready(function(){		
	// 연관검색어 없을시 클래스 추가
	var searchWord = $('.related-words'),
	wordLength = searchWord.find('dl').length;
	if(!wordLength){
		searchWord.addClass('word-off');
	}
});

//상세검색
function DetailSearch(){
	var obj = document.getElementsByName("brandId");
	var searchTermExt = $("#searchTermExt").val();
	var searchTermList = $("#searchTermList").val();
	
	var onOff ="";

	var brandCd = "";
	/*brand*/
	for(var i=0; i < obj.length; i++){
		if(obj[i].checked == true){
			if(i < obj.length-1)
				brandCd += obj[i].value + ",";
			else
				brandCd += obj[i].value;
		}
	}

	/*재검색*/
	searchTermExt = searchTermExt.replace(/</g, '').replace(/>/g, '').replace(/&/g, '').replace(/"/g, '').replace(/'/g, '').replace(/#/g, '').replace(/^/g, '').replace(/%/g, '%25');
	document.pageNavigator.searchTermExt.value= searchTermExt;
	

	if(searchTermExt == "" || searchTermList == "##"){
		document.pageNavigator.reFlag.value = "";
	}else{
		if($("#reFlagAND").is(":checked") == true){
			document.pageNavigator.reFlag.value = "AND";
		}else if($("#reFlagNOT").is(":checked") == true){
			document.pageNavigator.reFlag.value = "NOT";
		}
	}

	/*혜택 gift members enuri cardDc */
	if($("#check-benef-1").is(":checked") == true){
		document.pageNavigator.gift.value = "Y";
	}else{
		document.pageNavigator.gift.value = "N";
	}
	if($("#check-benef-2").is(":checked") == true){
		document.pageNavigator.members.value = "Y";
	}else{
		document.pageNavigator.members.value = "N";
	}
	if($("#check-benef-3").is(":checked") == true){
		document.pageNavigator.cardDc.value = "Y";
	}else{
		document.pageNavigator.cardDc.value = "N";
	}
	if($("#check-benef-4").is(":checked") == true){
		document.pageNavigator.children.value = "Y";
	}else{
		document.pageNavigator.children.value = "N";
	}
	if($("#check-benef-5").is(":checked") == true){
		document.pageNavigator.relation.value = "Y";
	}else{
		document.pageNavigator.relation.value = "N";
	}
	if($("#check-benef-6").is(":checked") == true){
			document.pageNavigator.bundle.value = "Y";
	}else{
		document.pageNavigator.bundle.value = "N";
	}
	if((($("#more_search").hasClass("more_active") == true) && document.pageNavigator.detailOnOff.value == "" ) || (($("#more_search").hasClass("more_active") == false) && document.pageNavigator.detailOnOff.value == "Y")){
		onOff = "Y";
	}

	if($("dl").hasClass("price-form add-form clear-after") == true){
		document.pageNavigator.minPrice.value=$('#minPrice').val();
		document.pageNavigator.maxPrice.value=$('#maxPrice').val();
	}else{
		document.pageNavigator.minPrice.value="";
		document.pageNavigator.maxPrice.value="";
	}
	document.pageNavigator.brandCd.value = encodeURIComponent(brandCd);
	document.pageNavigator.detailOnOff.value=onOff;
	document.pageNavigator.currentPage.value = "1";
	document.pageNavigator.currentPageCS.value = "1";
	document.pageNavigator.detailSearch.value = "Y";
	document.pageNavigator.submit();
}

function displayState(){
	if($("#moreShow").hasClass("btn-ico-more all-form") == true){
		$("#more_search").addClass("more_active");
	}
}

//상세검색 초기화
function DetailClaer(){
	var searchForm = document.pageNavigator;
	var intminPrc = $('.price-min').val();
	var intmaxPrc = $('.price-max').val();
	searchForm.cateName1.value = "";
	searchForm.cateName2.value = "";
	searchForm.gift.value = "N";
	searchForm.members.value = "N";
	searchForm.cardDc.value = "N";
	searchForm.children.value = "N";
	searchForm.relation.value = "N";
	searchForm.bundle.value = "N";
	searchForm.minPrice.value = "";
	searchForm.maxPrice.value = "";
	searchForm.brandCd.value = "";
	searchForm.currentPage.value = "1";
	searchForm.currentPageCS.value = "1";
	document.pageNavigator.detailOnOff.value="";
	/*$('.slider-range').slider({
		range: true,
		min: intminPrc,
		max: intmaxPrc,
		values: [intminPrc, intmaxPrc]
	});
	$('#minPrice').val($('.slider-range').slider('values', 0));
	$('#maxPrice').val($('.slider-range').slider('values', 1));
	$('.price-range .price-min').text($('.slider-range').slider('values', 0));
	$('.price-range .price-max').text($('.slider-range').slider('values', 1));
	$('<span><em>'+$('.slider-range').slider('values', 0)+'</em></span>').appendTo($('.slider-range').find('.ui-slider-handle:eq(0)'));
	$('<span><em>'+$('.slider-range').slider('values', 1)+'</em></span>').appendTo($('.slider-range').find('.ui-slider-handle:eq(1)'));*/
	
	searchForm.submit();
}

/** ********************************************************
 * 상품상세url  (카테고리id,상품코드,팝업유무 Y,N) => mallDivnCd = undifined
 * 상품상세url  (카테고리id,상품코드,팝업유무 Y,N, 몰구분코드)
 ******************************************************** */
 function goSearchProductDetail(cateId,prodCd,popupYn,searchTerm, collectionName,num, url, mallDivnCd){
	var url = url;
	var docId = num+'|'+prodCd+'|'+mallDivnCd;
	if(prodCd=="" || prodCd==null){
		alert( msg_product_error_noPro);
		return;
	}	
	requestFeedback(docId, searchTerm, collectionName);
	document.location.href = url+"/product/ProductDetail.do?CategoryID="+cateId+"&ProductCD="+prodCd+"&socialSeq=&koostYn=";
 }
 
 function requestFeedback(docId, searchTerm, collectionName) { // 검색사용성
		if(searchTerm != "") {
			$.post("/search/searchFeedback.do", {
					"searchTerm" : encodeURIComponent(searchTerm),
					"docId" : encodeURIComponent(docId),
					"collectionName" : encodeURIComponent(collectionName)
					},
				function(data) { 
					// var returnData = eval("(" + data + ")"); 
				}
			);
		}
	}