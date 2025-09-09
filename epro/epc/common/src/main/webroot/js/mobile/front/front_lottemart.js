/**
 ********************************************************
 * 최초작성 : 2011-09-16 jgSon
 *
 * 장바구니, 바로구매, 찜하기 관련된 JAVASCRIPT
 * 파라미터를 넘겨 받아 체크하여 해당 메소드를 호출한다.
 *********************************************************
 */

 /** ********************************************************
  * TForm 을 사용하기 전에 초기화 처리를 위해 호출해야함
  ******************************************************** */
  function initTForm(){
	inHtml = "<form name=\"tForm\" id=\"tForm\" method=\"POST\" action=\"\">";
	inHtml = inHtml + "<div id=\"divTemp\"></div>";
	inHtml = inHtml + "</form>";
	document.getElementById("tFormDiv").innerHTML =inHtml;
  }

  /** ********************************************************
   * 상품 옵션 선택 팝업창 띄우기
   * p_type : 01. 장바구니 : '' or null or 'basket'
   * 		  02. 바로구매 : 'order'
   ******************************************************** */
   function goPopupOption(p_prodCd, p_type){
 	  var url 				= (window._LMAppUrlM || $.utils.config('LMAppUrlM'))+"/product/popup/productOptionList.do?PROD_CD="+p_prodCd +"&TYPE="+p_type;
 	  var popProductOption 	= window.open(url, 'popProductOption', 'width=400, height=381, scrollbars=yes, resizable=yes');
 	  popProductOption.focus();
   }

 /** ********************************************************
  * 장바구니 담기 Ajax Function
  ******************************************************** */
  function fnAddBasketItem(){
		var params = $('#tForm').serialize();
	    fn$ajax((window._LMAppUrlM || $.utils.config('LMAppUrlM'))+"/basket/insertBasket.do", params, fnNmGetter().name, false);
	}

  //	간편 장바구니 정보 조회 함수 대입 변수
  var _quickEasyCart;

  /**
   *  장바구니 담기 후 실행 Function
   */
	function callBack_$fnAddBasketItem(response){
		// TForm 초기화
		initTForm();
		var jsonData = eval( "(" + response + ")" );
		var param = jsonData[0];

		if(param.ERR_NO != "0"){
			alert(param.ERR_MSG);
			return;
		}//if
		if(param.ERR_NO == "0"){
			// 선택하신 상품이 장바구니에 등록되었습니다.
			//alert(msg_basket_insert_ok);
			if(confirm("선택하신 상품이 장바구니에 등록되었습니다. \n 지금 확인하시겠습니까?")){
				window.location.href = (window._LMAppUrlM || $.utils.config('LMAppUrlM'))+"/mobile/mypage/PMWMMAR0003.do";
			}
//			var bResult = confirm("선택하신 상품이 장바구니에 등록되었습니다. \n 지금 확인하시겠습니까?");
//
//			if(bResult == true){
//
//				location.replace(_LMAppUrlM+"/mobile/mypage/PMWMMAR0003.do");
//			}else{
//				//	간편 장바구니 화면 재호출
//				if ( _quickEasyCart != undefined && typeof _quickEasyCart == 'function' )
//					_quickEasyCart('I');
//				return;
//			}//if
		}//if
	}

	 /** ********************************************************
	  * 정기배송 장바구니 담기 Ajax Function Popup.
	  ******************************************************** */
	  function fnAddPeriBasketItem(){
			var params = $('#tForm').serialize();
		    fn$ajax((window._LMAppUrlM || $.utils.config('LMAppUrlM'))+"/basket/insertBasket.do", params, fnNmGetter().name, false);
		}

	  //	간편 장바구니 정보 조회 함수 대입 변수
	  var _quickEasyCart;

	  /**
	   *  장바구니 담기 후 실행 Function
	   */
		function callBack_$fnAddPeriBasketItem(response){
			// TForm 초기화
			initTForm();
			var jsonData = eval( "(" + response + ")" );
			var param = jsonData[0];

			if(param.ERR_NO != "0"){
				alert(param.ERR_MSG);
				return;
			}//if
			if(param.ERR_NO == "0"){
				// 선택하신 상품이 장바구니에 등록되었습니다.
				//alert(msg_basket_insert_ok);
				if(confirm("선택하신 상품이 정기배송 장바구니에 등록되었습니다. \n 지금 확인하시겠습니까?")){
					window.location.href = (window._LMAppUrlM || $.utils.config('LMAppUrlM'))+"/mobile/mypage/PMWMMAR0003.do?basketType=B";
				}else{
					//location.reload();
				}
			}//if
		}

 /** ********************************************************
  * 장바구니 수정 Ajax Function
  ******************************************************** */
  function fnReviseBasketQty(){

		var params = $('#tForm').serialize();

	    fn$ajax((window._LMAppUrlM || $.utils.config('LMAppUrlM'))+"/basket/reviseBasketQty.do", params, fnNmGetter().name, false)
	}


  /**
   *  장바구니 수정 후 실행 Function
   */
	function callBack_$fnReviseBasketQty(response){

		// TForm 초기화
		initTForm();
		var jsonData = eval( "(" + response + ")" );
		var param = jsonData[0];

		if(param.ERR_NO != "0"){
			alert(param.ERR_MSG);
			location.reload();
			return;
		}//if

		if(param.ERR_NO == "0"){
			//정상적으로 수정되었습니다.
			alert('상품 수량을 변경하였습니다.');
			//location.replace(_LMAppUrl+"/basket/basketList.do");
			location.reload();
		}//if
	}
 /** ********************************************************
  * 장바구니 에서 상품 삭제 Ajax Function
  ******************************************************** */
  function fnDeleteItem(){

		var params = $('#tForm').serialize();

	    fn$ajax((window._LMAppUrlM || $.utils.config('LMAppUrlM'))+"/basket/deleteBasket.do", params, fnNmGetter().name, false);
	}


  /**
   *  장바구니 에서 상품 삭제 후 실행 Function
   */
	function callBack_$fnDeleteItem(response){

		var jsonData = eval( "(" + response + ")" );
		var param = jsonData[0];

		if(param.ERR_NO != "0"){
			alert(param.ERR_MSG);
			return;
		}//if

		if(param.ERR_NO == "0"){
			alert("삭제 되었습니다.");
			if(param.periDeliYn == "Y"){
				location.replace((window._LMAppUrlM || $.utils.config('LMAppUrlM'))+"/mobile/peribasket/basketList.do");
			}else{
				location.replace((window._LMAppUrlM || $.utils.config('LMAppUrlM'))+"/mobile/basket/PMWMMAR0003.do");
			}
		}//if
	}

/** ********************************************************
 * 바로구매하기
 ******************************************************** */
function fnDirectOrderItem(){
	var params = $('#tForm').serialize();

    fn$ajax((window._LMAppSSLUrlM || $.utils.config('LMAppSSLUrlM'))+"/basket/insertDirectBasket.do", params, fnNmGetter().name, false);
}

/**
 *  바로구매 후 실행 Function
 */
	function callBack_$fnDirectOrderItem(response){
		var jsonData = eval( "(" + response + ")" );
		var param = jsonData[0];

		if(param.ERR_NO != "0"){
			alert(param.ERR_MSG);
			return;
		}//if

		if(param.ERR_NO == "0"){
			initTForm();
			var deliTypeList = param.deliTypeCd.split("[!@!]");
			//alert(param.deliTypeCd);
			$('#divTemp').append(genDomInput("bsketNo", param.bsketNo));
			$('#divTemp').append(genDomInput("basketType", param.basketType));
			/*
			if(deliTypeList.length > 1){
				$('#divTemp').append(genDomInput("deliTypeCd", deliTypeList[0]));
			}else{
				$('#divTemp').append(genDomInput("deliTypeCd", param.deliTypeCd));
			}
			*/
			$('#divTemp').append(genDomInput("deliTypeCd", param.deliTypeCd));
			$('#divTemp').append(genDomInput("bsketDivnCd", "02"));
			$('#tForm').attr('action',(window._LMAppUrlM || $.utils.config('LMAppUrlM'))+'/mobile/basket/insertPreOrder.do').submit();
		}//if
	}

/** ********************************************************
 * 장바구니 삭제
 ******************************************************** */
function fnDeleteBasketItem(){

	var params = $('#tForm').serialize();

    fn$ajax((window._LMAppSSLUrlM || $.utils.config('LMAppSSLUrlM'))+"/basket/ajaxDeleteBasket.do", params, fnNmGetter().name, false);
}

/**
*  장바구니 삭제 후 실행 Function
*/
function callBack_$fnDeleteBasketItem(response){

	var jsonData = eval( "(" + response + ")" );
	var param = jsonData[0];

	if(param.ERR_NO != "0"){
		alert(param.ERR_MSG);
		return;
	}//if

	if(param.ERR_NO == "0"){
		//	간편 장바구니 화면 재호출
		if ( _quickEasyCart != undefined && typeof _quickEasyCart == 'function' )
			_quickEasyCart('D');
		alert('정상적으로 삭제되었습니다.');
	}//if
}
/** ********************************************************
 * 즉석조리 배송회차 조회
 ******************************************************** */
function fnSelectDeliInstPickup(strCd){

  	var params = {};

    params["strCd"] = [];

    $.each(strCd, function(i, value){
        params["strCd"].push( value );
    });

    fn$ajax((window._LMAppSSLUrlM || $.utils.config('LMAppSSLUrlM'))+"/basket/ajaxDeliInstPickup.do", params, fnNmGetter().name, false);
}

/**
*  즉석조리 배송회차 조회 후 실행 Function
*/
function callBack_$fnSelectDeliInstPickup(response){

	var jsonData = eval( "(" + response + ")" );
	var param = jsonData[0];

	if(param.ERR_NO != "0"){
		alert(param.ERR_MSG);
		return;
	}//if

	if(param.ERR_NO == "0"){

		deliInstPickupList(jsonData);
	}//if
}
/** ********************************************************
 * 선택 재계산
 ******************************************************** */
function fnCheckRecalculation(){

	var params = $('#tForm').serialize();

    fn$ajax((window._LMAppSSLUrlM || $.utils.config('LMAppSSLUrlM'))+"/basket/checkRecalculation.do", params, fnNmGetter().name, false);
}

/**
*  선택 재계산 후 실행 Function
*/
function callBack_$fnCheckRecalculation(response){

	var jsonData = eval( "(" + response + ")" );
	var param = jsonData[0];

	if(param.ERR_NO != "0"){
		alert(param.ERR_MSG);
		return;
	}//if

	if(param.ERR_NO == "0"){
		checkRecalculation(jsonData);
	}//if
}

/** ********************************************************
* 배송 점포 찾기 팝업 str_cd = 점포 코드
* shortcut='Y' 바로갔을 경우 결과값 출력
******************************************************** */
function DirectStore(str_cd){
var popup = window.open((window._LMAppUrlM || $.utils.config('LMAppUrlM'))+"/popup/storeResult.do?str_cd="+str_cd+"&shortcut=Y","popupDirectStore","scrollbars=yes, width=520, height=600");
		if(popup !=null){
			popup.focus();
		}
}

/** ********************************************************
* 상품상세url  (카테고리id,상품코드,팝업유무 Y,N)
******************************************************** */
function goProductDetail(cateId,prodCd,popupYn,socialSeq,smartOfferClickUrl){
	var dpCode = "";
	if(typeof(smartOfferClickUrl) != "undefined" && smartOfferClickUrl != "") {
		// 스마트오퍼를 통한 상품을 클릭 시 logging 을 위해 다음을 호출하고, 결과는 받을 필요 없다. itemSetId, scnId
		try {
			var codeList = ["dpId", "itemSetId", "scnId"];
			var clickParams = smartOfferClickUrl.substring(smartOfferClickUrl.indexOf("?") + 1).split("&");
			var curParams;
			for (var i = 0 ; i < codeList.length ; i++ ) {
				curParams = $.grep(clickParams, function(obj) {
					return obj.indexOf(codeList[i] + "=") >= 0;
				});
				if (curParams != null && curParams.length > 0) {
					if (codeList[i] === "dpId") {
						dpCode += ("&" + "dp=" + curParams[0].split("=")[1]);
					}
					else {
						dpCode += ("&" + curParams[0]);
					}
				}
			}
			$.get(smartOfferClickUrl);
		}
		catch (e) {}
	}
// 상세페이지 url 변경으로 재수정함.
  document.location.href = (window._LMAppUrlM || $.utils.config('LMAppUrlM'))+"/mobile/cate/PMWMCAT0004_New.do?CategoryID="+cateId+"&ProductCD="+prodCd+"&socialSeq="+socialSeq + (dpCode != "" ? dpCode:"");
  //document.location.href = _LMAppUrlM+"/mobile/product/mobileProductDetail.do?CategoryID="+cateId+"&ProductCD="+prodCd;

}

/** ********************************************************
* 상품상세Toyurl  (카테고리id,상품코드)
******************************************************** */
function goProductDetailToy(cateId,prodCd){
	if( cateId == undefined || cateId == "" || prodCd == undefined || prodCd == "" ){
		alert('잘롯된 상품정보 입니다.');
		return false;
	}

	document.location.href = "http://m.toysrus.lottemart.com/mobile/cate/ProductDetail.do?CategoryID="+cateId+"&ProductCD="+prodCd;
}

/** ********************************************************
* 상품상세url 신규  (카테고리id,상품코드,팝업유무 Y,N)  2013.01.07 김현엽
******************************************************** */
function goProductDetailMobile(cateId,prodCd,popupYn,socialSeq,siteLoc,smartOfferClickUrl){
	var dpCode = "";
	if(typeof(smartOfferClickUrl) != "undefined" && smartOfferClickUrl != "") {
		// 스마트오퍼를 통한 상품을 클릭 시 logging 을 위해 다음을 호출하고, 결과는 받을 필요 없다. itemSetId, scnId
		try {
			var codeList = ["dpId", "itemSetId", "scnId"];
			var clickParams = smartOfferClickUrl.substring(smartOfferClickUrl.indexOf("?") + 1).split("&");
			var curParams;
			for (var i = 0 ; i < codeList.length ; i++ ) {
				curParams = $.grep(clickParams, function(obj) {
					return obj.indexOf(codeList[i] + "=") >= 0;
				});
				if (curParams != null && curParams.length > 0) {
					if (codeList[i] === "dpId") {
						dpCode += ("&" + "dp=" + curParams[0].split("=")[1]);
					}
					else {
						dpCode += ("&" + curParams[0]);
					}
				}
			}
			$.get(smartOfferClickUrl);
		}
		catch (e) {}
	}

  document.location.href = (window._LMAppUrlM || $.utils.config('LMAppUrlM'))+"/mobile/cate/PMWMCAT0004_New.do?CategoryID="+cateId+"&ProductCD="+prodCd+"&socialSeq="+socialSeq +"&SITELOC=" + siteLoc+  (dpCode != "" ? dpCode:"");


}

/** ********************************************************
* 찜바구니 넣기[장바구니 전용]  (상품코드, 해외배송여부, 카테고리아이디)
******************************************************** */
function fnProdWishListBasket(prodCd, forgnDelyplYn, categoryId){
  //상품코드
  var prodCds = [prodCd];
  //해외배송여부
  var forgnDelyplYns = [forgnDelyplYn];
  //카테고리ID
  var categoryIds = [categoryId];

  fnAddWishListBasket(prodCds, forgnDelyplYns, categoryIds);
}

function fnAddWishListBasket(prodCds, forgnDelyplYns, categoryIds){
  var params = {};
  params["prodCds"] = [];
  params["forgnDelyplYns"] = [];
  params["categoryIds"] = [];

  $.each(prodCds, function(i, value){
	  params["prodCds"].push( value );
  });
  $.each(forgnDelyplYns, function(i, value){
	  params["forgnDelyplYns"].push( value );
  });
  $.each(categoryIds, function(i, value){
	  params["categoryIds"].push( value );
  });

  //	20111208
  //fn$ajax(_LMAppSSLUrl+"/mymart/ajax/insertWishList.do", params, fnNmGetter().name, false);
  fn$ajax((window._LMAppSSLUrlM || $.utils.config('LMAppSSLUrlM'))+"/mymart/ajax/insertWishList.do", params, fnNmGetter().name, false);
}

function callBack_$fnAddWishListBasket(response){

  var jsonData = eval( "(" + response + ")" );
  var param = jsonData[0]; if(param.ERR_NO != "0"){alert(param.ERR_MSG); return;}

	if(param.ERR_NO != "0"){
		alert(param.ERR_MSG);
		return;
	}//if

	if(param.ERR_NO == "0"){
		// 선택하신 상품이 위시리스트에 등록되었습니다. \\n 지금 확인하시겠습니까?
		var bResult = confirm(msg_mymart_confirm_insert_nowcheck);

		if(bResult == true){

			//	20111208
			//location.replace(_LMAppUrl+"/mymart/selectWishList.do");
			location.replace((window._LMAppUrlM || $.utils.config('LMAppUrlM'))+"/mobile/mypage/PMWMMAR0032.do");
		}else{
			return;
		}//if
	}//if
}


/** 찜바구니 넣기  (상품코드, 해외배송여부, 카테고리아이디) */
function fnProdWishList(prodCd, forgnDelyplYn, categoryId){
//상품코드
var prodCds = [prodCd];
//해외배송여부
var forgnDelyplYns = [forgnDelyplYn];
//카테고리ID
var categoryIds = [categoryId];

fnAddWishList(prodCds, forgnDelyplYns, categoryIds);
}

function fnAddWishList(prodCds, forgnDelyplYns, categoryIds){
var params = {};
  params["prodCds"] = [];
  params["forgnDelyplYns"] = [];
  params["categoryIds"] = [];

  $.each(prodCds, function(i, value){
	  params["prodCds"].push( value );
  });
  $.each(forgnDelyplYns, function(i, value){
	  params["forgnDelyplYns"].push( value );
  });
  $.each(categoryIds, function(i, value){
	  params["categoryIds"].push( value );
  });

  fn$ajax((window._LMAppSSLUrlM || $.utils.config('LMAppSSLUrlM'))+"/mymart/ajax/insertWishList.do", params, fnNmGetter().name, false);
}

function callBack_$fnAddWishList(response){

var jsonData = eval( "(" + response + ")" );
var param = jsonData[0]; if(param.ERR_NO != "0"){alert(param.ERR_MSG); return;}

if(param.ERR_NO == "0"){
	alert(param.SHOW_MSG);
}
}

function isLogin(redirectUrl, event)
{
params = {
		'redirectUrl' : redirectUrl
};

var bLogin;
var in_url;

if (location.href.indexOf("https://") > -1) {
    in_url = _LMAppSSLUrlM;
} else if(location.href.indexOf("http://") > -1) {
    in_url = _LMAppUrlM;
}

var url = (in_url || $.utils.config('LMAppUrlM')) +"/mobile/login/islogin.do";

$.ajax({
	type       : "POST" ,
	url        : url,
	//url        : _LMAppSSLUrl+"/member/islogin.do" ,
	data       : params ,
	async      : false ,
	dataType   : "text" ,
	timeOut    : (9 * 1000) ,
	success    : function(response){
		var jsonData = eval( "(" + response + ")" );
		bLogin = ( jsonData.isLogin == "Y" );
	} ,
	cache      : false ,
	error      : callSysErr
});

if ( !bLogin ) {
	goLogin("MMARTSHOP", redirectUrl);
	//return false;
}

return bLogin;
}

function isMember()
{
params = {
};

var bMember;

$.ajax({
	type       : "POST" ,
	url        : (window._LMAppSSLUrlM || $.utils.config('LMAppSSLUrlM'))+"/member/ismember.do" ,
	data       : params ,
	async      : false ,
	dataType   : "text" ,
	timeOut    : (9 * 1000) ,
	success    : function(response){
		var jsonData = eval( "(" + response + ")" );
		bMember = ( jsonData.isMember == 'Y' );
	} ,
	cache      : true ,
	error      : callSysErr
});

return bMember;
}


/**손큰 피자*/
function goPizzaMain() {
location.href=(window._LMAppUrlM || $.utils.config('LMAppUrlM')) + "/category/pizzaMain.do";
}
/**  상품 no image */
function showNoImage(obj, width, height){

if(typeof obj.src == "undefined"){
	return "";
}

if ( obj.src.indexOf("noimg_prod") == -1 ) {
	var width = width || $(obj).attr("width");
	var height = height || width || $(obj).attr("height");
	//$(obj).attr("src",_LMAppUrlM+"/v3/images/layout/noimg_prod_"+ $(obj).attr("width") +"x"+ $(obj).attr("width") +".jpg");
	$(obj).attr("src",(window._LMCdnV3RootUrl || $.utils.config('LMCdnV3RootUrl')) +"/images/layout/noimg_prod_"+ width +"x"+ height +".jpg");

}
}

/**공통 카테고리 조회*/
function goCategoryList(p_categoryId){
document.location.href = (window._LMAppUrlM || $.utils.config('_LMAppUrlM'))+"/category/categoryList.do?CategoryID="+p_categoryId;
}


/** ********************************************************
* 상품 19 이미지
******************************************************** */
function show19Image(obj){
if(typeof obj.src == "undefined"){
	return "";
}

if ( obj.src.indexOf("19_") == -1 ) {
	$(obj).attr("src",(window._LMCdnStaticUrl || $.utils.config('LMCdnStaticUrl'))+"/images/front/common/19_"+ $(obj).attr("width") +"_"+ $(obj).attr("width") +".jpg");
}
}



/**********************************************************
* 소셜쇼핑 링크
*********************************************************/
function goSocialShopping(param)
{
location.href = (window._LMAppUrlM || $.utils.config('_LMAppUrlM'))+"/mobile/PMWMCAT0019.do" + (!!param ? "?" + param : "");;
}


/** ********************************************************
* 멀티배송 상품 재고 및 판매 유무 등 체크
******************************************************** */
function fnMultiOrderItemChk(){
var params = $('#tForm').serialize();
fn$ajax((window._LMAppUrlM || $.utils.config('_LMAppUrlM'))+"/basket/ajaxMultiDeliItemChk.do", params, fnNmGetter().name, false);
}

/**
*  멀티배송 상품 재고 및 판매 유무 등 체크 후 실행 Function
*/
function callBack_$fnMultiOrderItemChk(response){
	// TForm 초기화
	initTForm();
	var jsonData = eval( "(" + response + ")" );
	var param = jsonData[0];
	var deliAndChooseItem = "";

	if(param.ERR_NO != "0") {
		alert(param.ERR_MSG);
		location.href = (window._LMAppUrlM || $.utils.config('_LMAppUrlM'))+"/mobile/mypage/PMWMMAR0003.do";
	}
}

//	패밀리 회원가입
function fnFamilyJoin(param) {
	lpoint.join();
}

function fnMartJoin() {
	ga('send', 'event', { eventCategory: '회원가입', eventAction: '롯데마트몰 회원가입(간편가입회원)' });

	var url = (window._LMAppUrl || $.utils.config('LMAppUrl')) + '/imember/member/join/memberForm_step1.do?sid=MARTSHOP&mallcode=null&SITELOC=JA005&returnurl='+ (window._LMAppUrl || $.utils.config('LMAppUrl')) +'/index.do';

	if( /toysrus.iphone.shopping/.test(userAgent)  ) {
		appVersion = userAgent.split( 'app-version' )[1].replace( 'V.', '' );

		if( checkAppVersion( appVersion , '3.12' ) ){
			callAppScheme( 'toysrusapp://openPopup?url=' + url, true );
		} else {
			location.href = url;
		}
	} else {
		window.open(url, "")
	}
}