/**
 ********************************************************
- * 최초작성 : 2011-09-16 jgSon
 *
 * 공통으로 사용하는 JAVASCRIPT
 * 파라미터를 넘겨 받아 체크하여 해당 메소드를 호출한다.
 *********************************************************
 */

 /** ********************************************************
  * TForm 을 사용하기 전에 초기화 처리를 위해 호출해야함
  ******************************************************** */
  function initTForm(){
	inHtml = "<form name=\"tForm\" id=\"tForm\" method=\"POST\" action=\"\">";
	inHtml = inHtml + "<div id=\"divTemp\"></div>";
	document.getElementById("tFormDiv").innerHTML =inHtml;
  }

  /** ********************************************************
   * 상품 옵션 선택 팝업창 띄우기
   * p_type : 01. 장바구니 : '' or null or 'basket'
   * 		  02. 바로구매 : 'order'
   ******************************************************** */
   function goPopupOption(p_prodCd, p_type){
 	  var url 				= _LMAppUrl+"/product/popup/productOptionList.do?PROD_CD="+p_prodCd +"&TYPE="+p_type;
 	  var popProductOption 	= window.open(url, 'popProductOption', 'width=400, height=381, scrollbars=yes, resizable=yes');
 	  popProductOption.focus();
   }

 /** ********************************************************
  * 장바구니 담기 Ajax Function
  ******************************************************** */
  function fnAddBasketItem(){

		var params = $('#tForm').serialize();

	    fn$ajax(_LMAppSSLUrl+"/basket/insertBasket.do", params, fnNmGetter().name, false);
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
			alert(msg_basket_insert_ok);
			//	간편 장바구니 화면 재호출
			if ( _quickEasyCart != undefined && typeof _quickEasyCart == 'function' )
				_quickEasyCart('I');
			return;
			// 선택하신 상품이 장바구니에 등록되었습니다. 지금 확인하시겠습니까?
//			var bResult = confirm(msg_basket_insert_nowcheck);
//
//			if(bResult == true){
//
//				location.replace(_LMAppUrl+"/basket/basketList.do");
//			}else{
//				//	간편 장바구니 화면 재호출
//				if ( _quickEasyCart != undefined && typeof _quickEasyCart == 'function' )
//					_quickEasyCart('I');
//				return;
//			}//if
		}//if
	}

 /** ********************************************************
  * 장바구니 수정 Ajax Function
  ******************************************************** */
  function fnReviseBasketQty(){

		var params = $('#tForm').serialize();

	    fn$ajax(_LMAppSSLUrl+"/basket/reviseBasketQty.do", params, fnNmGetter().name, false);
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
			return;
		}//if

		if(param.ERR_NO == "0"){
			//정상적으로 수정되었습니다.
			alert(msg_common_success_update);
			location.replace(_LMAppUrl+"/basket/basketList.do");
		}//if
	}
 /** ********************************************************
  * 장바구니 에서 상품 삭제 Ajax Function
  ******************************************************** */
  function fnDeleteItem(){

		var params = $('#tForm').serialize();

	    fn$ajax(_LMAppSSLUrl+"/basket/deleteBasket.do", params, fnNmGetter().name, false);
	}


  /**
   *  장바구니 에서 상품 삭제 후 실행 Function
   */
	function callBack_$fnDeleteItem(response){
		// TForm 초기화
		initTForm();
		var jsonData = eval( "(" + response + ")" );
		var param = jsonData[0];

		if(param.ERR_NO != "0"){
			alert(param.ERR_MSG);
			return;
		}//if

		if(param.ERR_NO == "0"){
			//정상적으로 삭제되었습니다.
			alert(msg_common_success_delete);
			location.replace(_LMAppUrl+"/basket/basketList.do");
		}//if
	}

/** ********************************************************
 * 바로구매하기
 ******************************************************** */
function fnDirectOrderItem(){
	var params = $('#tForm').serialize();

    fn$ajax(_LMAppSSLUrl+"/basket/insertDirectBasket.do", params, fnNmGetter().name, false);
}

/**
 *  바로구매 후 실행 Function
 */
	function callBack_$fnDirectOrderItem(response){
		// TForm 초기화
		initTForm();
		var jsonData = eval( "(" + response + ")" );
		var param = jsonData[0];

		if(param.ERR_NO != "0"){
			alert(param.ERR_MSG);
			return;
		}//if

		if(param.ERR_NO == "0"){
			$('#divTemp').append(genDomInput("bsketNo", param.bsketNo));
			$('#divTemp').append(genDomInput("basketType", param.basketType));
			$('#divTemp').append(genDomInput("deliTypeCd", param.deliTypeCd));
			$('#divTemp').append(genDomInput("bsketDivnCd", "02"));

			$('#tForm').attr('action',_LMAppUrl+'/basket/insertPreOrder.do').submit();
		}//if
	}

/** ********************************************************
 * 장바구니 삭제
 ******************************************************** */
function fnDeleteBasketItem(){

	var params = $('#tForm').serialize();

    fn$ajax(_LMAppSSLUrl+"/basket/ajaxDeleteBasket.do", params, fnNmGetter().name, false);
}

/**
*  장바구니 삭제 후 실행 Function
*/
function callBack_$fnDeleteBasketItem(response){
	// TForm 초기화
	initTForm();
	var jsonData = eval( "(" + response + ")" );
	var param = jsonData[0];

	if(param.ERR_NO != "0"){
		alert(param.ERR_MSG);
		return;
	}//if

	if(param.ERR_NO == "0"){
		//	간편 장바구니 화면 재호출
		if ( _quickEasyCart != undefined && typeof _quickEasyCart == 'function' ){
			_quickEasyCart('D');
		}
		// 정상적으로 삭제되었습니다.
		alert(msg_common_success_delete);
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

    fn$ajax(_LMAppSSLUrl+"/basket/ajaxDeliInstPickup.do", params, fnNmGetter().name, false);
}

/**
*  즉석조리 배송회차 조회 후 실행 Function
*/
function callBack_$fnSelectDeliInstPickup(response){
	// TForm 초기화
	initTForm();
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

    fn$ajax(_LMAppSSLUrl+"/basket/checkRecalculation.do", params, fnNmGetter().name, false);
}

/**
*  선택 재계산 후 실행 Function
*/
function callBack_$fnCheckRecalculation(response){
	// TForm 초기화
	initTForm();
	var jsonData = eval( "(" + response + ")" );
	var param = jsonData[0];

	if(param.ERR_NO != "0"){
		if(param.BASKET_TYPE == "41"){
			$('[name=selectForgnDelypl]').val("");
			$('[name=forgnDeliveryCd]').val("");
		}
		alert(param.ERR_MSG);
		return;
	}//if

	if(param.ERR_NO == "0"){
		checkRecalculation(jsonData);
	}//if
}
/** ********************************************************
 * 지점 찾기 팝업 함수만 가져다 씀
 ******************************************************** */
  function goStore(){
 	 var popup = window.open(_LMAppUrl+"/popup/storeSearch.do","popupStoreSearch","width=500, height=205 ,scrollbars=yes");
  		if(popup !=null){
  			popup.focus();
  		}
  }

  /** ********************************************************
   * 배송 점포 찾기 팝업 str_cd = 점포 코드
   * shortcut='Y' 바로갔을 경우 결과값 출력
   ******************************************************** */
  function DirectStore(str_cd){
 	var popup = window.open(_LMAppUrl+"/popup/storeResult.do?str_cd="+str_cd+"&shortcut=Y","popupDirectStore","scrollbars=yes, width=520, height=600");
  			if(popup !=null){
  				popup.focus();
  			}
  }

  /** ********************************************************
  * 상품상세url  (카테고리id,상품코드,팝업유무 Y,N)
  ******************************************************** */
  function goProductDetail(cateId,prodCd,popupYn,socialSeq){
  	var popYn=popupYn;
  	var socialSeqVal="";
  	if(popupYn==""){
  		popYn="N";
  	}
  	if(prodCd=="" || prodCd==null){
  		//alert("상품코드가 존재하지 않습니다.");
  		alert( msg_product_error_noPro);
  		if(popYn=="Y"){
  			self.close();
  		}
  		return;
  	}

if(socialSeq==undefined){
	socialSeqVal="";
}else{
	socialSeqVal=socialSeq;
}

    	if(popYn=="Y"){
    		opener.document.location.href = _LMAppUrl+"/product/ProductDetail.do?CategoryID="+cateId+"&ProductCD="+prodCd+"&socialSeq="+socialSeqVal;
    		self.close();
    	}else{
    		document.location.href = _LMAppUrl+"/product/ProductDetail.do?CategoryID="+cateId+"&ProductCD="+prodCd+"&socialSeq="+socialSeqVal;
    	}

  }

  /** ********************************************************
   * 간편보기url  (카테고리id,상품코드,단품코드,출력여부(printing))
   ******************************************************** */
  function goProductZoom(cateId,prodCd,itemCd,printYn) {
	    var itemCode="001"; //단품은 001로 고정합니다.(변경되면 삭제해 주세요.)

	    if(itemCd!="" || itemCd!=null){
	    	itemCode = itemCd;
	    }
		if(prodCd==null || prodCd==""){
			//alert("상품정보가 존재하지 않습니다.");
			alert( msg_product_error_noPro);
			return;
		}
		if(cateId==null || cateId==""){
			alert(msg_category_error_noCate);
			return;
		}
		//if(itemCd==null || itemCd==""){
		//	alert("단품코드가 존재하지 않습니다.");
		//	return;
		//}
		var popup = window.open(_LMAppUrl+"/product/popup/ProductZoom.do?ProductCD="+prodCd+"&ItemCd="+itemCode+"&PrintYn="+printYn+"&CategoryID="+cateId, "PopupProductImgZoom", "toolbar=no,location=no,directories=no,status=no,scrollbars=no,resizable=no,menubar=no,width=900,height=630,dependent=yes");
		//900*630


	}

  /** ********************************************************
   * 찜바구니 넣기  (상품코드, 해외배송여부, 카테고리아이디)
   ******************************************************** */
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

      fn$ajax(_LMAppSSLUrl+"/mymart/ajax/insertWishList.do", params, fnNmGetter().name, false);
  }

  function callBack_$fnAddWishList(response){

  	var jsonData = eval( "(" + response + ")" );
  	var param = jsonData[0]; if(param.ERR_NO != "0"){alert(param.ERR_MSG); return;}

  	if(param.ERR_NO != "0"){
  		alert(param.SHOW_MSG);
  	}

  	if(param.ERR_NO == "0"){
  		alert(param.SHOW_MSG);
  	}
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

	  fn$ajax(_LMAppSSLUrl+"/mymart/ajax/insertWishList.do", params, fnNmGetter().name, false);
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

				location.replace(_LMAppUrl+"/mymart/selectWishList.do");
			}else{
				return;
			}//if
		}//if
  }

  /** ********************************************************
   * 멀티배송 상품 재고 및 판매 유무 등 체크
   ******************************************************** */
  function fnMultiOrderItemChk(){
  	var params = $('#tForm').serialize();
      fn$ajax(_LMAppSSLUrl+"/basket/ajaxMultiDeliItemChk.do", params, fnNmGetter().name, false);
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

  		if(param.ERR_NO != "0"){
  			alert(param.ERR_MSG);
  			deliAndChooseItem += '<tr>';
  			deliAndChooseItem += '<td colspan="4" class="nodata">';
  			deliAndChooseItem += '선택하신 배송지별 선택상품이 없습니다.';
  			deliAndChooseItem += '</td>';
  			deliAndChooseItem += '<input type="hidden" name="orderList" value="" />';
  			deliAndChooseItem += '</tr>';
  			$('#deliAndChooseItemInsert').html("");
  			$('#deliAndChooseItemInsert').html(deliAndChooseItem);
  			return;
  		}//if

  		if(param.ERR_NO == "0")
  		{

  		}//if
  	}

  /** ********************************************************
   * 로그인 여부 체크
   ******************************************************** */
function isLogin(redirectUrl)
{
	params = {
			'redirectUrl' : redirectUrl
	};

	var bLogin;
	var in_url;

	if(location.href.indexOf("https://") > -1){
    	in_url = _LMAppSSLUrl;
    }else if(location.href.indexOf("http://") > -1){
    	in_url = _LMAppUrl;
    }

	$.ajax({
        type       : "POST" ,
        url        : in_url+"/member/islogin.do" ,
        data       : params ,
        async      : false ,
        dataType   : "text" ,
        timeOut    : (9 * 1000) ,
        success    : function(response){
         	var jsonData = eval( "(" + response + ")" );
         	bLogin = ( jsonData.isLogin == "Y" );
        } ,
		cache      : true ,
        error      : callSysErr
    });

	if ( !bLogin ) {
		goLogin(_SID_NM_MARTMALL, redirectUrl);
	}

	return bLogin;
}

/** ********************************************************
 * 회원, 비회원 체크
 ******************************************************** */
function isMember()
{
	params = {
	};

	var bMember;
	var in_url;

	if(location.href.indexOf("https://") > -1){
    	in_url = _LMAppSSLUrl;
    }else if(location.href.indexOf("http://") > -1){
    	in_url = _LMAppUrl;
    }

	$.ajax({
        type       : "POST" ,
        url        : in_url+"/member/ismember.do" ,
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
// 비교하기 팝업 전역변수. 비교하기, 팝업 장바구니에서 사용
  var popupProductCompare;

/*******************************************************************************
 * 상품 비교하기 버튼 (상품코드) p_prodCDs = 상품코드|^|상품코드|^|상품코드 상품코드 2개이상 3개 이하
 ******************************************************************************/
function goProductCompare(p_prodCDs) {
	var value = p_prodCDs;
	if (value == null) {
		alert(msg_compare_fail_compareNoProd);	//비교하기 할 수 있는 상품이 없습니다.
		return false;
	} else {
		popupProductCompare = window.open(_LMAppUrl
				+ "/product/popup/productCompare.do?value=" + value, "popupProductCompare",
				"width=900, height=640");
		popupProductCompare.focus();
	}
}

/*******************************************************************************
 * 손큰 피자
 ******************************************************************************/
function goPizzaMain() {
	location.href=_LMAppUrl + "/category/pizzaMain.do";
}
/** ********************************************************
 * 상품 no image
 ******************************************************** */
function showNoImage(obj){

	if(typeof obj.src == "undefined"){
		return "";
	}

	if ( obj.src.indexOf("product-default") == -1 ) {
		$(obj).attr("src",_LMCdnStaticUrl+"/images/front/common/product-default-"+ $(obj).attr("width") +"-"+ $(obj).attr("width") +".jpg");
	}
}

/** ********************************************************
 * 상품 19 이미지
 ******************************************************** */
function show19Image(obj){
	if(typeof obj.src == "undefined"){
		return "";
	}
	
	if ( obj.src.indexOf("19_") == -1 ) {
		$(obj).attr("src",_LMCdnStaticUrl+"/images/front/common/19_"+ $(obj).attr("width") +"_"+ $(obj).attr("width") +".jpg");
	}
}

/** ********************************************************
 * 공통 카테고리 조회
 ******************************************************** */
function goCategoryList(p_categoryId){
	document.location.href = _LMAppUrl+"/category/categoryList.do?CategoryID="+p_categoryId;
}

/** ********************************************************
 * 공통 카테고리 새창
 ******************************************************** */
function popCategoryList(p_categoryId){
	window.open(_LMAppUrl+"/category/categoryList.do?CategoryID="+p_categoryId, "", "");
}


/** ********************************************************
 * 특화몰 카테고리 조회
 ******************************************************** */
function goCategorySpecList(p_categoryId){
	document.location.href = _LMAppUrl+"/category/categorySpecList.do?CategoryID="+p_categoryId;
}

/** ********************************************************
 * 특화몰 카테고리 새창
 ******************************************************** */
function popCategorySpecList(p_categoryId){
	window.open(_LMAppUrl+"/category/categorySpecList.do?CategoryID="+p_categoryId, "", "");
}


/** ********************************************************
 * 주문상세보기  (주문아이디, 새창여부)
 ******************************************************** */
function fnOrderDetail(orderId, newWinId){
	var winId = "";
	if(typeof newWinId != "undefined"){
		winId = newWinId;
	}

	if(winId == "Y" || winId == "y"){
		window.open(_LMAppUrl+"/mymart/selectMyOrderDetail.do?orderId="+orderId, "selectMyOrderDetail","");
	}else if(winId == "P"){
		opener.location.href = _LMAppUrl+"/mymart/selectMyOrderDetail.do?orderId="+orderId;
		self.close();
	}else{
		location.href = _LMAppUrl+"/mymart/selectMyOrderDetail.do?orderId="+orderId;
	}
}

/** ********************************************************
 * 배송조회  (배송회사코드, 송장번호)
 ******************************************************** */
function fnDeliverySearch(hodecoCd, hodecoInvoiceNo){
	window.open(_LMAppUrl+"/mymart/popup/selectDeliveryStatus.do?hodecoCd="+hodecoCd+"&hodecoInvoiceNo="+hodecoInvoiceNo, "deliverySearch","toolbar=no,location=no,directories=no,status=no,scrollbars=yes,resizable=yes,menubar=no,width=550,height=600,dependent=yes");
}


/** ********************************************************
 * 이벤트바로가기  (배송회사코드, 송장번호)
 ******************************************************** */
function fnEventDetail(categoryId){
	location.href = _LMAppUrl+"/event/detail.do?categoryId="+categoryId;
}

/** ********************************************************
 * 제휴몰 바로가기
 ******************************************************** */
function goAffiliate(param){
	location.href = _LMAppUrl+"/partner/viewHtml.do?viewPath=partnerMain" + (!!param ? "&" + param : "");
}

/**********************************************************
 * 소셜쇼핑 링크
 *********************************************************/
function goSocialShopping(param)
{
	location.href = _LMAppUrl+"/shop/sociallist.do" + (!!param ? "?" + param : "");
}

/**********************************************************
 * 홈으로
 *********************************************************/
function goHome(){
	location.href = _LMAppUrl;
}

function fnChangePassword()
{
	var winPop = window.open(_LMAppUrl+"/member/changePasswordForm.do", "changePassword", "scrollbar=no");
	winPop.focus();
}
/** ********************************************************
 * 팝업 장바구니 담기 Ajax Function Popup. 상품 비교
 ******************************************************** */
 function fnAddBasketItemPop(){
		var params = $('#tForm').serialize();
	    fn$ajax(_LMAppSSLUrl+"/basket/insertBasket.do", params, fnNmGetter().name, false);
	}

 //	간편 장바구니 정보 조회 함수 대입 변수
 var _quickEasyCartPop;

 /**
  *  장바구니 담기 후 실행 Function. 장바구니 확인하기 확인 -> 팝업창 close / 취소 -> 팝업창 focus
  */
	function callBack_$fnAddBasketItemPop(response){
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
			alert(msg_basket_insert_ok);
			//	간편 장바구니 화면 재호출
			popupProductCompare.focus();
			if ( _quickEasyCartPop != undefined && typeof _quickEasyCartPop == 'function' )
				_quickEasyCartPop('I');
			return;

			// 선택하신 상품이 장바구니에 등록되었습니다. 지금 확인하시겠습니까?
//			var bResult = confirm(msg_basket_insert_nowcheck);

//			if(bResult == true){
//				popupProductCompare.close();
//				location.replace(_LMAppUrl+"/basket/basketList.do");
//			}else{
//				//	간편 장바구니 화면 재호출
//				popupProductCompare.focus();
//				if ( _quickEasyCartPop != undefined && typeof _quickEasyCartPop == 'function' )
//					_quickEasyCartPop('I');
//				return;
//			}//if
		}//if
	}

/** ********************************************************
 * 공통 팝업 화면 중앙에 뜨는 스크립트: NewWindow(URL, 창이름, 팝업 가로, 팝업 세로, 스크롤 여부) bang
 ******************************************************** */
var win = null;
function NewWindow(mypage,myname,w,h,scroll){
	LeftPosition = (screen.width) ? (screen.width-w)/2 : 0;
	TopPosition = (screen.height) ? (screen.height-h)/2 : 0;
	settings = 'height='+h+',width='+w+',top='+TopPosition+',left='+LeftPosition+',scrollbars='+scroll+',resizable';
	win = window.open(mypage,myname,settings);
}

/** ********************************************************
 * 공통 팝업 위치 지정 스크립트 : NewWindow(URL, 창이름, 세팅'width=400,height=440,scrollbars=no, left=990, top=1') bang
 ******************************************************** */
function NewWindowSetting(mypage,myname,settings){
	window.open(mypage,myname,settings);
}



/** ********************************************************
 * 기본배송지
 ******************************************************** */
function fnPopMyDeliveryList(){
	window.open(_LMAppUrl+"/mymart/popup/selectMyDeliveryList.do", "PopMyMartDeliveryAddrForm", "width=620,height=525,toolbar=no,location=no,directories=no,status=no,scrollbars=yes,resizable=no,menubar=no, dependent=yes");
}


/** ********************************************************
 * 멀티배송 배송지 등록 & 수정
 ******************************************************** */
function fnMultiDelivery(){

	var params = $('#tForm').serialize();

    fn$ajax(_LMAppSSLUrl+"/basket/selectMultiDelivery.do", params, fnNmGetter().name, false);
}

/**
*  멀티배송 배송지 등록 & 수정 후 실행 Function
*/
function callBack_$fnMultiDelivery(response){
	// TForm 초기화
	initTForm();
	var jsonData = eval( "(" + response + ")" );
	var param = jsonData[0];

	if(param.ERR_NO != "0"){
		alert(param.ERR_MSG);
		return;
	}//if

	if(param.ERR_NO == "0"){
		fnCallBackMultiDelivery(jsonData);
	}//if
}
/** ********************************************************
 * 제휴사 중 렌터카 로그인
 ******************************************************** */
function goRentCar(param){
	if(!isLogin(_LMAppUrl+"/affiliate/affiliateSite.do?site_id=0011" + (!!param ? "&" + param : ""))){
		return;
	}else{
		location.href = _LMAppUrl+"/affiliate/affiliateSite.do?site_id=0011" + (!!param ? "&" + param : "");
	}
}

/** ********************************************************
 * 공통 alert 띄우기
 ******************************************************** */
function fnAlert(msg){
	alert(msg);
}

//	패밀리 회원 ID 찾기
function fnFamilySearchID(param)
{
	var url = "https://member.lottetown.com/door/user/requestId.jsp?sid=" + _SID_NM_MARTMALL + (!!param ? "&" + param : "");
	var popWin = window.open(url, "", 'width=750,height=600,scrollbars=no');
	popWin.focus();
}

//	패밀리 회원가입
function fnFamilyJoin(param)
{
	var url = "https://member.lottetown.com/door/user/login_common.jsp?sid=" + _SID_NM_MARTMALL + "&mallcode=null" + (!!param ? "&" + param : "");
	var popWin = window.open(url, "");
	popWin.focus();
}

//	패밀리 회원정보 변경
function fnFamilyChangeUser(param)
{
	var url = "https://member.lottetown.com/door/user/change_user_info.jsp?sid=" + _SID_NM_MARTMALL + (!!param ? "&" + param : "");
	var popWin = window.open(url, "");
	popWin.focus();
}

//	마미마트 링크
function goMymart(reqURI, isPopup)
{
	var url = _LMAppUrl + '/mymart/' + (reqURI || 'index.do');
	if ( isLogin(url) )
	{
		if ( isMember() )
		{
			if ( !!isPopup && !!opener )
				opener.location.href = url;
			else
				location.href = url;
		}
		else
			goLogin(_SID_NM_MARTMALL, url);
	}
}

//	로그인이 필요한 화면 url 일 경우 링크
function goRequiredLoginURL(reqURL, isPopup)
{
	if ( isLogin(reqURL) )
	{
		if ( isMember() )
		{
			if ( !!isPopup && !!opener )
				opener.location.href = reqURL;
			else
				location.href = reqURL;
		}
		else
			goLogin(_SID_NM_MARTMALL, reqURL);
	}
}

//모든로그인이 필요한 페이지를 호출한다.(woo)
function goComMyMart(goUrl, popupYn){

		var flag = isLogin(location.href);
		if(flag) {//로그인여부
			if(!isMember()){//비회원은 탈락
				//비회원은 접근할 수 없습니다.
				alert( msg_product_fail_notUrlPage);
				//return;
			}else{
				if(popupYn =='y' || popupYn =='Y'){
					var popWin = window.open(goUrl, "");
					popWin.focus();
				}else{
					document.location.href = goUrl;
				}
			}
		}
}
//레시피 상품정보로 바로가기
function goDetailRcp(rcpNo){
	//var f = document.form1;

	//f.action = "${_LMAppUrl}/recipe/recipeDetail.do";
	document.location.href = _LMAppUrl+"/recipe/recipeDetail.do?rcpNo="+rcpNo;

}

String.prototype.replaceHTML = function ()
{
	return this.replace(/"|'/g, '&quot;').replace(/>/g, '&gt;').replace(/</g, '&lt;').replace(/\r\n|\r|\n/g, '<br>');
};
