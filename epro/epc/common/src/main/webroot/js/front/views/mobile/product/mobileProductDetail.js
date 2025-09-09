//"use strict";

 /*
  * Icon Info
  */
var setIcon=[];
setIcon["1"]	= {title:"온라인단독(센터단독)",	iconSeq: "1",		iconOrder: 1,		iconType: "nomal",		sticker: "icon-promotion-type9",		tag: "icon-tag-benefit9"};
setIcon["2"]	= {title:"클리어런스%",				iconSeq: "2",		iconOrder: 2,		iconType: "custom",	sticker: "icon-promotion-discount",	tag: "icon-tag-benefit-discount"};
setIcon["3"]	= {title:"할인율(%)",					iconSeq: "3",		iconOrder: 3.1,	iconType: "custom",	sticker: "icon-promotion-discount",	tag: "icon-tag-benefit-discount"};
setIcon["4"]	= {title:"온라인단독프로모션",		iconSeq: "4",		iconOrder: 4,		iconType: "nomal",		sticker: "flag-s-{{:ICON_IDNT_NO}}",	tag: "flag-s-{{:ICON_IDNT_NO}}"};
setIcon["5"]	= {title:"M+N",							iconSeq: "5",		iconOrder: 5,		iconType: "custom",	sticker: "icon-promotion-bundle",	tag: "icon-tag-benefit-bundle"};
setIcon["6"]	= {title:"덤",								iconSeq: "6",		iconOrder: 6,		iconType: "nomal",		sticker: "icon-promotion-type13",	tag: "icon-tag-benefit13"};
setIcon["7"]	= {title:"L.POINT할인",					iconSeq: "7",		iconOrder: 7,		iconType: "nomal",		sticker: "icon-promotion-type4",		tag: "icon-tag-benefit4"};
setIcon["8"]	= {title:"L.POINT혜택",					iconSeq: "8",		iconOrder: 8,		iconType: "nomal",		sticker: "icon-promotion-type3",		tag: "icon-tag-benefit3"};
setIcon["9"]	= {title:"카드상품할인",				iconSeq: "9",		iconOrder: 9,		iconType: "nomal",		sticker: "icon-promotion-type8",		tag: "icon-tag-benefit8"};
setIcon["10"]	= {title:"카드혜택",						iconSeq: "10",	iconOrder: 10,	iconType: "nomal",		sticker: "icon-promotion-type7",		tag: "icon-tag-benefit7"};
setIcon["11"]	= {title:"다둥이상품할인",			iconSeq: "11",	iconOrder: 11,	iconType: "nomal",		sticker: "icon-promotion-type6",		tag: "icon-tag-benefit6"};
setIcon["12"]	= {title:"다둥이추가적립",			iconSeq: "12",	iconOrder: 12,	iconType: "nomal",		sticker: "icon-promotion-type5",		tag: "icon-tag-benefit5"};
setIcon["13"]	= {title:"M쿠폰",							iconSeq: "13",	iconOrder: 13,	iconType: "nomal",		sticker: "icon-promotion-type12",	tag: "icon-tag-benefit12"};
setIcon["14"]	= {title:"마일리지 증정",				iconSeq: "14",	iconOrder: 14,	iconType: "nomal",		sticker: "icon-promotion-type10",	tag: "icon-tag-benefit10"};
setIcon["15"]	= {title:"사은품 증정",					iconSeq: "15",	iconOrder: 15,	iconType: "nomal",		sticker: "icon-promotion-type11",	tag: "icon-tag-benefit11"};
setIcon["16"]	= {title:"살수록더싸게",				iconSeq: "16",	iconOrder: 16,	iconType: "nomal",		sticker: "icon-promotion-type1",		tag: "icon-tag-benefit1"};
setIcon["17"]	= {title:"같이더싸게",					iconSeq: "17",	iconOrder: 17,	iconType: "nomal",		sticker: "icon-promotion-type2",		tag: "icon-tag-benefit2"};
setIcon["18"]	= {title:"할인율(원)",					iconSeq: "18",	iconOrder: 3.2,	iconType: "nomal",		sticker: "icon-promotion-won",		tag: "icon-tag-benefit-won"};
setIcon["19"]	= {title:"딜상품",							iconSeq: "19",	iconOrder: 0,		iconType: "nomal",		sticker: "icon-promotion-type14",	tag: "icon-tag-benefit14"};

var setIcon_new=[];
setIcon_new["1"]	= {title:"온라인단독(센터단독)",	iconSeq: "1",		iconOrder: 1,		iconType: "nomal",		sticker: "icon-promotion-type9",		tag: "icon-tag-benefit9"};
setIcon_new["2"]	= {title:"클리어런스%",				iconSeq: "2",		iconOrder: 2,		iconType: "custom",	sticker: "icon-band-discount",	        tag: "icon-band-discount"};
setIcon_new["3"]	= {title:"할인율(%)",					iconSeq: "3",		iconOrder: 3.1,	iconType: "custom",	sticker: "icon-band-discount",	        tag: "icon-band-discount"};
setIcon_new["4"]	= {title:"온라인단독프로모션",		iconSeq: "4",		iconOrder: 4,		iconType: "nomal",		sticker: "flag-s-{{:ICON_IDNT_NO}}",	tag: "flag-s-{{:ICON_IDNT_NO}}"};
setIcon_new["5"]	= {title:"M+N",							iconSeq: "5",		iconOrder: 5,		iconType: "custom",	sticker: "icon-band-bundle",	        tag: "icon-band-bundle"};
setIcon_new["6"]	= {title:"증정",							iconSeq: "6",		iconOrder: 6,		iconType: "nomal",		sticker: "icon-band-type13",	        tag: "icon-band-tag13"};
setIcon_new["7"]	= {title:"L.POINT할인",					iconSeq: "7",		iconOrder: 7,		iconType: "nomal",		sticker: "icon-promotion-type4",		tag: "icon-tag-benefit4"};
setIcon_new["8"]	= {title:"L.POINT혜택",					iconSeq: "8",	    iconOrder: 10,	iconType: "nomal",		sticker: "icon-band-type3",		        tag: "icon-band-tag3"};
setIcon_new["9"]	= {title:"카드할인",				        iconSeq: "9",	    iconOrder: 11,	iconType: "nomal",		sticker: "icon-band-tag8",		        tag: "icon-band-tag8"};
setIcon_new["10"]	= {title:"카드혜택",					iconSeq: "10",	iconOrder: 12,	iconType: "nomal",		sticker: "icon-band-type7",		        tag: "icon-band-tag7"};
setIcon_new["11"]	= {title:"다둥이상품할인",		iconSeq: "11",	iconOrder: 13,	iconType: "nomal",		sticker: "icon-promotion-type6",		tag: "icon-tag-benefit6"};
setIcon_new["12"]	= {title:"다둥이추가적립",		iconSeq: "12",	iconOrder: 16,	iconType: "nomal",		sticker: "icon-band-type5",		        tag: "icon-band-tag5"};
setIcon_new["13"]	= {title:"M쿠폰",						iconSeq: "13",	iconOrder: 17,	iconType: "nomal",		sticker: "icon-band-tag16",	            tag: "icon-band-tag16"};
setIcon_new["14"]	= {title:"증정",			            iconSeq: "14",	iconOrder: 18,	iconType: "nomal",		sticker: "icon-band-type10",	        tag: "icon-band-tag10"};
setIcon_new["15"]	= {title:"증정",				        iconSeq: "15",	iconOrder: 19,	iconType: "nomal",		sticker: "icon-band-type11",	        tag: "icon-band-tag11"};
setIcon_new["16"]	= {title:"살수록더싸게",			iconSeq: "16",	iconOrder: 20,	iconType: "nomal",		sticker: "icon-band-type1",		        tag: "icon-band-tag1"};
setIcon_new["17"]	= {title:"같이더싸게",				iconSeq: "17",	iconOrder: 21,	iconType: "nomal",		sticker: "icon-band-type2",		        tag: "icon-band-tag2"};
setIcon_new["18"]	= {title:"할인율(원)",				iconSeq: "18",	iconOrder: 3.2,	iconType: "nomal",		sticker: "icon-band-won",		        tag: "icon-band-won"};
setIcon_new["19"]	= {title:"딜상품",						iconSeq: "19",	iconOrder: 0,		iconType: "nomal",		sticker: "icon-promotion-type14",	tag: "icon-tag-benefit14"};

setIcon_new["20"]	= {title:"L.POINT할인",				iconSeq: "20",	iconOrder: 8,		iconType: "nomal",		sticker: "icon-band-type4",		        tag: "icon-band-tag4"};
setIcon_new["21"]	= {title:"L.POINT할인",				iconSeq: "21",	iconOrder: 9,		iconType: "nomal",		sticker: "icon-band-type4-1",		    tag: "icon-band-tag4-1"};
setIcon_new["22"]	= {title:"다둥이상품할인",		iconSeq: "22",	iconOrder: 14,	iconType: "nomal",		sticker: "icon-band-type6",	            tag: "icon-band-tag6"};
setIcon_new["23"]	= {title:"다둥이상품할인",		iconSeq: "23",	iconOrder: 15,	iconType: "nomal",		sticker: "icon-band-type6-1",	        tag: "icon-band-tag6-1"};

$(function() {

	// 배송비 절약하기
	$(".board-goods-details").on('click', "#saveDeliPrice", function (e) {
		var vendorNm = prodBasicInfo.VENDOR_NM;

		if( vendorNm.indexOf("주) ") ) {
			vendorNm = vendorNm.replace("주) ", "주)")
		}

		document.location.href=_LMAppUrlM+"/mobile/search/Search.do?searchTerm="+encodeURIComponent(vendorNm)+"&viewType=G";

	});

	// 같은 브랜드 상품 더보기
	$("#goodsDetails1").on('click', "#searchBrand", function (e) {
		var brandNm = encodeURIComponent($("#brandNm").text());
		document.location.href=_LMAppUrlM+"/mobile/search/Search.do?searchTerm="+brandNm+"&viewType=G";

	});



	// 배송유형 관련
	$('#quickmenu-bar').on('click', 'input:checkbox', function(e){
		var type = $( e.target );

		if(type.is("#periDeliYn")) {
			//정기배송
			if( !$(".btnSoldOut").hidden ) {
				if( $(this).prop("checked") ) {
					$(".btnBuy").hide();
					$(".btnPeri").show();
				} else {
					$(".btnBuy").show();
					$(".btnPeri").hide();
				}
			}
		} else if(type.is("#forgnDeliYn")) {
			//해외배송
			if( !$(".btnSoldOut").hidden ) {
				if( $(this).prop("checked") ) {
					$(".btnBuy").hide();
					$(".btnForgn").show();
				} else {
					$(".btnBuy").show();
					$(".btnForgn").hide();
				}
			}
		}
	});

	//앱일경우 clipboard.js 를 통해 url 복사 하는데 지원하는 범위가 크롬 42 버전 부터임
	var chromeVersion = parseInt(getChromeVersion () ) ;

	if(chromeVersion){
		if(chromeVersion < 42){
			$("i.icon-share-url").parent().hide();
		}
	}else{
		$("i.icon-share-url").parent().hide();
	}

	function getChromeVersion () {
	    var raw = navigator.userAgent.match(/Chrom(e|ium)\/([0-9]+)\./);

	    return raw ? parseInt(raw[2], 10) : false;
	}

	if( $("#tab02").length > 0 || $("#tab03").length > 0 ) {
		// 상품평리스트 등록순, 추천순 선택
		$('[id=orderSelect]').selectmenu({
			 change: function(){
				 changeFilter('');
			 }
		});

		// 상품평리스트 등록순, 추천순 선택
		$('[id=quesOrder]').selectmenu({
			 change: function(){
				 getQuestionListAjax('', '1');
			 }
		});

		// 상품평 상품리스트 옵션 선택
		$('[id=reviewProdOptn]').selectmenu({
			 change: function(){
				 getReviewListAjax('', '1');
			 }
		});

		// 상품문의 상품리스트 옵션 선택
		$('[id=quesProdOptn]').selectmenu({
			 change: function(){
				 getQuestionListAjax('', '1');
			 }
		});

		 // 상품평 조회
		getReviewListAjax('', '1');
		 // 상품문의 조회
		getQuestionListAjax('', '1');
	}


	if($("[data-share]").length < 1){
		var $snsShareWrap = $( '#snsShareWrap' );

		$snsShareWrap.find( 'a' ).each(function() {
			$.utils.shareHandler.bind( $( this ) );
		});

		try {
			if( window.Clipboard ) {
				var clipboard =  new Clipboard( '.link-copy' ,{
					text : function( trigger ) {
						return "["+prodBasicInfo.PROD_NM+"] "+ location.href;
					}
				});

				clipboard.on( 'success', function() {
					alert( view_messages.copylink.success );
				});

				clipboard.on( 'error', function() {
					alert( view_messages.copylink.fail );
				});
			}
		} catch(e) {
		}
	}

});

	//영양정보 Link : Lotte All Safe
	function ProdHistory(prod_cd){
		window.open("http://www.lotteallsafe.com/controls/safesearch/pop/init?GOOD_CODE="+prod_cd,"popup","left=0, top=0, width=670,height=640,scrollbars=yes");
	}

	/*
	 * 상품 이미지 정보 (Mobile)
	 */
	function setProductImgMobile(itemsSrcmkCD, itemsImgQty) {
		var imgWidth="640";
	    var mobileImg='';

	    for(var i=0; i<itemsImgQty; i++) {
	    	mobileImg += '<li class="swiper-slide"><img src="'+getImgPass(itemsSrcmkCD, imgWidth, (i+1))+'" width="'+imgWidth+'" alt="" onerror="javascript:showNoImage(this)"></li>';
	    }

	    $("#mobileImg").append(mobileImg);
	}

	function getImgPass(itemsSrcmkCD,size,seq){
		var foderNm=itemsSrcmkCD.substring(0,5).trim();
		var imgpass = $("#FormProdInfo #imgpass").val();

		return imgpass+"/"+foderNm+"/"+itemsSrcmkCD+"_"+seq+"_"+size+".jpg";
	}

	/*
	 * 매장배송가능시간 안내
	 * deliSpFg_SD : 근거리배송
	 * deliSpFg_SP : 매장픽업
	 * deliSpFg_CP : 조리픽업
	 */
	function getDeliveryTimeInfo(deliSpFg_SD, deliSpFg_SP, deliSpFg_CP) {
		var params = {
				'deliSpFg_CP' : deliSpFg_CP
				,'deliSpFg_SP' : deliSpFg_SP
				,'deliSpFg_SD' : deliSpFg_SD
				};

		$.getJSON("/mobile/product/ajax/mobileDeliveryTimeInfoAjax.do", params)
		.done(function(data) {
			var delivery_notice = "";
			var delivery_text = deliSpFg_CP=="Y"? "픽업":"배송";

			if( data.status === "success" ) {
//				console.log("deliveryTimeInfo = ", data.deliveryTimeInfo);

				var deliveryTimeInfo = data.deliveryTimeInfo;
				var deliTypeCd = deliveryTimeInfo.DELI_TYPE_CD;									// 배송유형	01: 근거리, 03: 조리픽업
				var delivDate = setDateFormat(deliveryTimeInfo.DELIV_DATE, '-').trim();				// 배송일자
				var deliStartTm = deliveryTimeInfo.DELI_PRAR_START_TM.replace(":", "").trim();	// 배송시작시간
				var ordCloseTm = deliveryTimeInfo.ORD_CLOSE_TM.replace(":", "").trim();			// 주문마감시간

				var nowDt = data.nowDt.replace(":", "").trim();										// 현재시간
				var after2hDt = data.after2hDt.replace(":", "").trim();			// 현재시간 + 2 시간
				var after1dDt = setDateFormat(data.after1dDt, '-').trim();	// 다음날
				var after2dDt = setDateFormat(data.after2dDt, '-').trim();	// 모레

				if( delivDate == nowDt.substring(0, 10).trim() ) {
					if( nowDt.substring(10).trim() < ordCloseTm && deliStartTm < after2hDt.substring(10).trim() ) {
						delivery_text += deliSpFg_CP!="Y"? "출발":"";
						delivery_notice = '<i class="icon-common-truck"></i> 지금 주문하시면 <span class="point1"><em class="number-point">2</em>시간 이내</span>에 '+delivery_text+' 가능';
					} else {
						//김포 허브앤스포크 작업 - 시간 출력하는 부분 수정 - 분 부분을 0으로 나오게끔 수정
						//주석 처리 delivery_notice = '<i class="icon-common-truck"></i> 지금 주문하면 <span class="point1">오늘 <em class="number-point">'+deliStartTm.substring(0,2)+":"+deliStartTm.substring(2)+'</em></span> 부터 '+delivery_text+'가능';
						delivery_notice = '<i class="icon-common-truck"></i> 지금 주문하면 <span class="point1">오늘 <em class="number-point">'+deliStartTm.substring(0,2)+":"+deliStartTm.substring(2,3)+"0"+'</em></span> 부터 '+delivery_text+'가능';
					}
				}  else if ( delivDate == after1dDt ) {
					//김포 허브앤스포크 작업 - 시간 출력하는 부분 수정 - 분 부분을 0으로 나오게끔 수정
					//주석 처리 delivery_notice = '<i class="icon-common-truck"></i> 지금 주문하면 <span class="point1">내일 <em class="number-point">'+deliStartTm.substring(0,2)+':'+deliStartTm.substring(2)+'</em></span> 부터 '+delivery_text+'가능';
					delivery_notice = '<i class="icon-common-truck"></i> 지금 주문하면 <span class="point1">내일 <em class="number-point">'+deliStartTm.substring(0,2)+':'+deliStartTm.substring(2,3)+"0"+'</em></span> 부터 '+delivery_text+'가능';
				} else if( delivDate >= after2dDt ) {
					delivery_notice = '<i class="icon-common-truck"></i> 현재 주문량이 많아 <span class="point1">모레</span>부터 '+delivery_text+' 가능';
				}
			}

			if(delivery_notice != "") {
				$("#delivery-notice").html(delivery_notice);
			} else {
				$("#delivery-notice").parent("tr").remove();
			}
		});
	}

	//원단위 절사
	function setUsablePrc(prc){

		var returnPrc = 0;
		var strPrc = "";
		var intPrc = 0;
		if(prc <= 0){
			returnPrc = 0;
		}else{

			strPrc = prc.toString();
			strPrc = strPrc.substring(0,strPrc.length-1) + "0";
			intPrc = Number(strPrc);
			returnPrc = intPrc;
		}
		return returnPrc;
	}

	// 찜하기
	function goDetailWishList(obj, event) {
		var PROD_CD = prodBasicInfo.PROD_CD;
		var CATEGORY_ID = prodBasicInfo.CATEGORY_ID;
		var overseaYn ="N";

		if($("input:checkbox[name=forgnDeliYn]").length > 0 && $("input:checkbox[name=forgnDeliYn]").prop("checked")){
			overseaYn = "Y";
			//alert("해외배송의 경우 찜하기 기능을 사용할 수 없습니다.");
			alert( view_messages.wish.fail );
			return;
		}

		var srcmkCD = $("#mdSrcmkCd").text();
		if(srcmkCD==null || srcmkCD==""){
			return;
		}

  		var flag = global.isLogin(_LMAppUrlM+"/mobile/cate/PMWMCAT0004_New.do?ProductCD="+PROD_CD+"&CategoryID="+CATEGORY_ID);
  		if(flag) {//로그인여부
  			if(!global.isMember()){
  				//alert("비회원은 찜바구니에 상품을 담을수 없습니다.");
  				alert( view_messages.member.no_member);
  				return;
  			}

  			var actGb = "access";
  			 if($(obj).hasClass("active")) {
  				 actGb = "cancel";
  			 }

  			wishItems = ({
  				"prodCd": PROD_CD,
  				"categoryId": CATEGORY_ID,
  				"forgnDelyplYn": overseaYn
  			});

  			if(actGb == "cancel") {
  				global.deleteWish(wishItems, function(data) {
  					$('.pop-toast-'+actGb).text("위시리스트 담기가 취소되었습니다.").addClass('active');
  					$(".wish-list").removeClass("active");
  				});
  			} else {

  				$.api.get({
  					apiName : 'defaultDeliInfo',
  					successCallback : function( resData ) {
  						ret = resData.defaultMemberAddrYn;
  						if( resData.defaultMemberAddrYn === 'N' ) {
  							alert( resData.message );
  							location.href = $.utils.config( 'LMAppUrlM' ) + '/mobile/popup/selectMemberDeliveryForm.do';
  						}else{

  							global.addWish(wishItems, function(data) {
  			  					$('.pop-toast-'+actGb).text(data.message).addClass('active');
  			  					$(".wish-list").addClass("active");
  			  				});
  						}
  					}
  				});
  			}
		}
	}

	//장바구니 toast popup
	var successBasketAction = function successBasketAction(num) {
		var basketToast = $('body').append('<p class="basket-toastpopup" />'),
			timeDistance = 500;

		basketToast = $('.basket-toastpopup');
		setTimeout(function() {
			basketToast.addClass('action');
		}, timeDistance);

		basketToast.on('transitionend webkitTransitionEnd', function() {
			basketCount();
			basketToast.remove();
		});
	};

	//광고 스크립트 호출 함수
	function callAdssomJS(data){

		//광고 스크립트 함수 호출

		if(window._seedConversionAnalysis ){

			var basketNo = data.basketItem.bsketNo ? data.basketItem.bsketNo[0] : '',
				totalSellAmt = data.basketItem.totSellAmt ? data.basketItem.totSellAmt : '',
				prodNm = data.basketItem.prodNm ? data.basketItem.prodNm[0] : '',
				currSellPrc = data.basketItem.currSellPrc ? data.basketItem.currSellPrc : '',
				basketQty = data.basketItem.bsketQty ? data.basketItem.bsketQty[0] : '',
				categoryId = data.basketItem.categoryId ? data.basketItem.categoryId[0] : '';

			/* 2018.01.15 adssom 1.0 주석 처리
			_seedConversionAnalysis(598, basketNo, totalSellAmt, prodNm, currSellPrc, basketQty, categoryId);
			*/
			_seedConversionAnalysis('C03', basketNo, totalSellAmt, prodNm, currSellPrc, basketQty, categoryId);
		}
	}
	//장바구니
	function goDetailShoppingCart(event) {
		//console.log("goDetailShoppingCart");

		var PROD_CD = prodBasicInfo.PROD_CD;
		var CATEGORY_ID = prodBasicInfo.CATEGORY_ID;
		var ONLINE_PROD_TYPE_CD = prodBasicInfo.ONLINE_PROD_TYPE_CD;
		var OPTION_YN = prodBasicInfo.OPTION_YN;
		var bujinYN = clearenceInfo.bujinYN;
		var ctrTypeDivnCd = clearenceInfo.ctrTypeDivnCd;
		var limitTime = clearenceInfo.limitTime;
		var basketItems=[];

		if( ONLINE_PROD_TYPE_CD != "05" && $("#deliType").text() == "" ){
	      	alert( view_messages.fail.notDeliProd );//배송가능한 상품이 아닙니다.
	      	return;
	      }

		var srcmkCD = $("#mdSrcmkCd").text();
		if($("#mdSrcmkCd").length > 0 && (srcmkCD==null || srcmkCD=="")){
			return;
		}

		if( prodBasicInfo.LIVEFISH_YN == "Y" ) {
			alert("바로구매만 가능한 상품입니다.");
			return;
		}

		var flag = global.isLogin(_LMAppUrlM+"/mobile/cate/PMWMCAT0004_New.do?ProductCD="+PROD_CD+"&CategoryID="+CATEGORY_ID);
		if(flag) {//로그인여부
			var optionArea = $(".relation-option .option-list");
			var overseaYn ="N";		// 해외배송여부
			var periDeliYn ="N";		// 정기배송여부

			if($("input:checkbox[name=forgnDeliYn]").length > 0 && $("input:checkbox[name=forgnDeliYn]").prop("checked")){
				//해외배송여부 체크
				overseaYn = "Y";
			}

			if($("input:checkbox[name=periDeliYn]").length > 0 && $("input:checkbox[name=periDeliYn]").prop("checked")){
				//정기배송여부 체크
				periDeliYn = "Y";
			}

			if( _Member_yn == "false" && _GuestMember_yn =="true" && periDeliYn == "Y" ) {
				alert("L.POINT 통합회원 또는 롯데마트 회원만 정기배송 신청이 가능합니다. 회원 가입 후, 신청해 주세요");
				return;
			}
			// 기본 옵션선택 체크 / 딜상품 옵션선택 체크
			if( OPTION_YN == "Y"  || ONLINE_PROD_TYPE_CD == "05" ) {
				if( $("#quickmenu-bar .inner-scroll").find("div[name=virtual-basket]").length < 1 ) {
					// 가상 장바구니 상품 체크
					alert( view_messages.select.selectOption );
					fnResetOptn();
					return;
				}
			}

			 // 2016.05.11 신지원 추가 부진재고 상품 - 시작
			var rtnBasketItems;
			if( bujinYN == 'Y' && ctrTypeDivnCd == '2' ) {//(저온)부진재고 상품일때, 4시이전 주문가능, 4시이후 주문서비스마감
		        if (limitTime == 'N') { // 주문가능
			     	// 장바구니
		        	if (confirm( view_messages.bujin.orderInfo+"\n장바구니에 담으시겠습니까?")){
		        		rtnBasketItems = makeBasketItems(overseaYn, periDeliYn);
		        		if( rtnBasketItems != false && rtnBasketItems.length > 0 ) {
		        			global.addBasket(rtnBasketItems, function(data) {
		        				basketCount();
		        				schemeLoader.loadScheme({key: 'basketCountUpdate'});

		        				//광고 스크립트 함수 호출
                                callAdssomJS(data);

		        				alert("선택하신 상품이 장바구니에 등록되었습니다.");

		        				return;
		        			});
		        		}
		        	} else {
		        		return;
		        	}
		        } else { // 주문서비스 마감
					alert( view_messages.bujin.timeout );
					return;
		        }
	        } else {
		     	// 장바구니
	        	rtnBasketItems = makeBasketItems(overseaYn, periDeliYn);
	      		if( rtnBasketItems != false && rtnBasketItems.length > 0 ) {
	      			global.addBasket(rtnBasketItems, function(data) {
	      				basketCount();
                        schemeLoader.loadScheme({key: 'basketCountUpdate'});
	    				//alert("선택하신 상품이 장바구니에 등록되었습니다.");

	      				//광고 스크립트 함수 호출
                        callAdssomJS(data);

	      				successBasketAction();

	    				return;
	    			});
	      		}
	        }
	        // 2016.05.11 신지원 추가 - 끝
		}
	}

	//바로구매
	function goDetailBuyNow(obj, event) {
		var PROD_CD = prodBasicInfo.PROD_CD;
		var CATEGORY_ID = prodBasicInfo.CATEGORY_ID;
		var ONLINE_PROD_TYPE_CD = prodBasicInfo.ONLINE_PROD_TYPE_CD;
		var OPTION_YN = prodBasicInfo.OPTION_YN;
		var bujinYN = clearenceInfo.bujinYN;
		var ctrTypeDivnCd = clearenceInfo.ctrTypeDivnCd;
		var limitTime = clearenceInfo.limitTime;
		var overseaYn ="N";		// 해외배송여부
		var periDeliYn ="N";		// 정기배송여부
		var basketItems=[];

		if( ONLINE_PROD_TYPE_CD != "05" && $("#deliType").text() == "" ){
        	alert( view_messages.fail.notDeliProd );//배송가능한 상품이 아닙니다.
        	return;
        }

		var srcmkCD = $("#mdSrcmkCd").text();
		if(srcmkCD==null || srcmkCD==""){
			return;
		}

		if($("input:checkbox[name=forgnDeliYn]").length > 0 && $("input:checkbox[name=forgnDeliYn]").prop("checked")){
			// 해외배송상품 바로구매 체크
			overseaYn = "Y";
			alert( view_messages.fail.nowOrderOverSea );
			return;
		}

		if($("input:checkbox[name=periDeliYn]").length > 0 && $("input:checkbox[name=periDeliYn]").prop("checked")){
			// 정기배송상품 바로구매 체크
			periDeliYn = "Y";
			alert( view_messages.fail.nowOrderPeriDeli );
			return;
		}

		var flag = global.isLogin(_LMAppUrlM+"/mobile/cate/PMWMCAT0004_New.do?ProductCD="+PROD_CD+"&CategoryID="+CATEGORY_ID);
		if(flag) {//로그인여부

			// 기본 옵션선택 체크 / 딜상품 옵션선택 체크
			if( OPTION_YN == "Y"  || ONLINE_PROD_TYPE_CD == "05" ) {
				if( $("#quickmenu-bar .inner-scroll").find("div[name=virtual-basket]").length < 1 ) {
					// 가상 장바구니 상품 체크
					alert( view_messages.select.selectOption );
					fnResetOptn();
					return;
				}
			}

	        // 2016.05.11 신지원 추가 부진재고 상품 - 시작
			var rtnBasketItems;

			// 2017.10.19 전희찬 추가 점포코드 다를경우 장바구니로 이동 - 시작
			var basketType = ( periDeliYn == "Y" )?"B":( overseaYn == "Y" )?"C":"";
			var objItemCnt = 0;
			for(i=0; i<$("input:hidden[name=itemsStrCd]").length; i++) {
				var strCd = $("input:hidden[name=itemsStrCd]:eq(0)").val();
				if (strCd != $("input:hidden[name=itemsStrCd]:eq("+i+")").val()){
					objItemCnt++
				}
			}
			if(objItemCnt > 0) {
				// 장바구니
				rtnBasketItems = makeBasketItems(overseaYn, periDeliYn);
        		if( rtnBasketItems != false && rtnBasketItems.length > 0 ) {
        			global.addBasket(rtnBasketItems, function(data) {
        				basketCount();
        				schemeLoader.loadScheme({key: 'basketCountUpdate'});

                        if (confirm( "장바구니에 상품이 담겼습니다.\n(해당 상품은 장바구니에서만 주문이 가능합니다.)\n지금 확인하시겠습니까?")){
                        	global.goBasket();
                        }else{
                        	return;
                        }
        			});
        		}
			}else{
			// 2017.10.19 전희찬 추가 점포코드 다를경우 장바구니로 이동 -  끝
		        if( bujinYN == 'Y' && ctrTypeDivnCd == '2' ) {//(저온)부진재고 상품일때, 4시이전 주문가능, 4시이후 주문서비스마감
			        if (limitTime == 'N') { // 주문가능
		        		rtnBasketItems = makeBasketItems(overseaYn, periDeliYn);
		        		if( rtnBasketItems != false && rtnBasketItems.length > 0 ) {
				        	if (confirm( view_messages.bujin.orderInfo+"\n바로구매 하시겠습니까?")){
			        			$(obj).removeClass("purchase").prop("disabled", true);
			        			global.addDirectBasket(rtnBasketItems, function(xhr){
			        				var data = $.parseJSON(xhr.responseText);
			        				if (data && data.message) {
			        					$(obj).addClass("purchase").prop("disabled", false);		// 구매버튼 리셋
			        					alert(data.message);
			        					return;
			        				}
			        			});	//바로구매
				        	} else {
				        		return;
				        	}
		        		}
			        } else { // 주문서비스 마감
						alert( view_messages.bujin.timeout );
						return;
			        }
		        } else {
			        rtnBasketItems = makeBasketItems(overseaYn, periDeliYn);
		        	if( rtnBasketItems != false && rtnBasketItems.length > 0 ) {

			        	$(obj).removeClass("purchase").prop("disabled", true);
	        			global.addDirectBasket(rtnBasketItems, function(xhr){
	        				var data = $.parseJSON(xhr.responseText);
	        				if (data && data.message) {
	        					$(obj).addClass("purchase").prop("disabled", false);		// 구매버튼 리셋
	        					alert(data.message);
	        					return;
	        				}
	        			});	//바로구매

		        	}
		        }
		        // 2016.05.11 신지원 추가 - 끝
				// 2017.10.19 전희찬 추가 점포코드 다를경우 장바구니로 이동 -  시작
			}
				// 2017.10.19 전희찬 추가 점포코드 다를경우 장바구니로 이동 -  끝
		}
	}

	var makeBasketItems =function(overseaYn, periDeliYn) {
		var optionArea = $("#quickmenu-bar .inner-scroll");
		var PROD_CD = prodBasicInfo.PROD_CD;
		var CATEGORY_ID = prodBasicInfo.CATEGORY_ID;
		var ONLINE_PROD_TYPE_CD = prodBasicInfo.ONLINE_PROD_TYPE_CD;
		var CTPD_DEAL_YN = prodBasicInfo.CTPD_DEAL_YN;
		var irRegularVariation="", mstProdCd=null,  ctpdItemYn=null, ctpdProdCd=null, ctpdItemCd=null, ordReqMsg=null;
		var ordReqMsg=null, hopeDeliDy=null;
		var basketItems=[];

		if( ONLINE_PROD_TYPE_CD == '02' || ONLINE_PROD_TYPE_CD == '03' ) {
			hopeDeliDy=($("#HOPE_DELI_PSBT_DD").val()).replace(/\./g, '').trim();	// 희망배송일
			ordReqMsg=$("#ordReqMsg").val();	// 구매요청사항

			if( hopeDeliDy == null || hopeDeliDy == "" ) {
				alert("상품 수령이나 설치를 위한 희망배송일을 등록해 주세요");
				return false;
			}

			if( ONLINE_PROD_TYPE_CD == '02' && $("#ordReqMsg").val().trim() == "" ) {
				// 주문제작상품
				alert("구매요청사항을 입력하세요");
				return false;
			}
		}

		if( ONLINE_PROD_TYPE_CD == "04" ) {
			//골라담기
			var optnLoadQty = 0;
			var leftQty = 0;
			var setQty = Number($("#OPTN_LOAD_SET_QTY").val());

			for(i=0; i<$("input[name=optnLoadQty]").length; i++) {
				optnLoadQty += Number($("input[name=optnLoadQty]:eq("+i+")").val());
			}

			if( optnLoadQty % setQty != 0 || optnLoadQty == 0 ) {
				leftQty = setQty - (optnLoadQty % setQty);
				alert("상품 "+setComma(leftQty)+"개를 더 선택해주세요.");
				return false;
			}

			var itemOrderCount	= optnLoadQty / setQty;
			var itemsProdCd		= PROD_CD;
			var itemsCode			= "001";

			for( var i=0; i < $(optionArea).find("div.lie-table").length; i++) {
				var obj = $(optionArea).find("div.lie-table:eq("+i+")");
				var delim = ($(optionArea).find("div.lie-table").length == (i+1))?"":";";

				var optnLoadQty	= Number($(obj).find("input[name=optnLoadQty]").val());
				var optnLoadNm	= $(obj).find("input:hidden[name=optnLoadNm]").val();

				if( optnLoadQty > 0 )
					irRegularVariation += optnLoadNm+":"+optnLoadQty+delim;
			}

			basketItems.push({
				prodCd: itemsProdCd,			// 상품코드
				itemCd: itemsCode,				// 단품코드
				bsketQty: itemOrderCount,		// 주문수량
				categoryId: CATEGORY_ID,		// 카테고리ID
				nfomlVariation: irRegularVariation,	// 옵션명, 골라담기의 경우 옵션명:수량
				overseaYn: overseaYn,			// 해외배송여부
				prodCouponId: null,				// 즉시할인쿠폰ID
				oneCouponId: null,				// ONE 쿠폰ID
				cmsCouponId: null,				// CMS 쿠폰ID
				markCouponId: null,				// 마케팅제휴쿠폰ID
				periDeliYn: periDeliYn				// 정기배송여부
			});

		} else {

			if( $(optionArea).find("div[name=virtual-basket]").length > 0 ) {
				// 옵션상품
				var nfomlVariation="", delim="";
				for( var i=0; i < $(optionArea).find("div[name=virtual-basket]").length; i++) {
					var obj = $(optionArea).find("div[name=virtual-basket]:eq("+i+")");
					var nfomlVariationYN = 'N';
					var basketAddFlag = 'Y';
					nfomlVariation = "";
					fnChkOptnQty($(obj).find(".set-btn .up"));// 수량체크

					var itemOrderCount	= Number($(obj).find("input[name=OrderCount]").val());
					var itemsCurrSalePrc	= Number($(obj).find("input:hidden[name=itemsCurrSalePrc]").val());
					var itemsProdCd		= $(obj).find("input:hidden[name=itemsProdCd]").val();
					var itemsCode			= $(obj).find("input:hidden[name=itemsCode]").val();
					var categoryId			= (itemsProdCd == PROD_CD)?CATEGORY_ID:"";

					if( ONLINE_PROD_TYPE_CD == "05") {
						mstProdCd = PROD_CD;
					}

					// 비정규 옵션일 경우 옵션 추가
					if( $(obj).find("input:hidden[name=irregularVariationOptnDesc]").length > 0 ) {
						nfomlVariationYN = 'Y';
						nfomlVariation	= $(obj).find("input:hidden[name=itemsOptnDesc]").val()+":"+itemOrderCount+";";
					}

					$.each(basketItems, function (index, item) {
						if( nfomlVariationYN == 'Y' && item.prodCd == itemsProdCd && item.itemCd == itemsCode && item.itemCd == '001' ) {
							item.nfomlVariation += nfomlVariation;
							item.bsketQty += itemOrderCount;
							basketAddFlag = "N";
						}
					});

					if( basketAddFlag != "N" ) {
						basketItems.push({
							prodCd: itemsProdCd,				// 상품코드
							itemCd: itemsCode,					// 단품코드
							bsketQty: itemOrderCount,			// 주문수량
							categoryId: categoryId,				// 카테고리ID
							nfomlVariation: nfomlVariation,	// 옵션명, 골라담기의 경우 옵션명:수량
							overseaYn: overseaYn,				// 해외배송여부
							prodCouponId: null,					// 즉시할인쿠폰ID
							oneCouponId: null,					// ONE 쿠폰ID
							cmsCouponId: null,					// CMS 쿠폰ID
							markCouponId: null,					// 마케팅제휴쿠폰ID
							periDeliYn: periDeliYn,				// 정기배송여부
							mstProdCd: mstProdCd,				// 딜상품의 대표상품코드
							saveYn: null,								// 계속담기 여부
							ctpdItemYn: ctpdItemYn,				// 구성품 여부
							ctpdProdCd: ctpdProdCd,			// 구성품 원 상품코드
							ctpdItemCd: ctpdItemCd,			// 구성품 원 단품코드
							ordReqMsg: ordReqMsg,			// 구매요청사항
							hopeDeliDy: hopeDeliDy				// 희망배송일
						});
					}

					// 구성품
					if( CTPD_DEAL_YN == "Y" && $(obj).find(">div.subadded").length > 0 ) {
						for( var j=0; j < $(obj).find(">div.subadded").length; j++) {
							var objCtpd = $(obj).find(">div.subadded:eq("+j+")");

							fnChkOptnQty($(objCtpd).find(".set-btn .up"));// 수량체크

							var ctpd_itemOrderCount	= Number($(objCtpd).find("input[name=OrderCount]").val());
							var ctpd_itemsCurrSalePrc	= Number($(objCtpd).find("input:hidden[name=itemsCurrSalePrc]").val());
							var ctpd_itemsProdCd		= $(objCtpd).find("input:hidden[name=itemsProdCd]").val();
							var ctpd_itemsCode			= $(objCtpd).find("input:hidden[name=itemsCode]").val();

							basketItems.push({
								prodCd: ctpd_itemsProdCd,			// 상품코드
								itemCd: ctpd_itemsCode,				// 단품코드
								bsketQty: ctpd_itemOrderCount,		// 주문수량
								nfomlVariation: nfomlVariation,		// 옵션명, 골라담기의 경우 옵션명:수량
								overseaYn: overseaYn,					// 해외배송여부
								periDeliYn: periDeliYn,					// 정기배송여부
								ctpdItemYn: 'Y',								// 구성품 여부
								ctpdProdCd: itemsProdCd,				// 구성품 원 상품코드
								ctpdItemCd: itemsCode					// 구성품 원 단품코드
							});
						}
					}
				}	// end for
			} else {
				//console.log("단품 담기");
				var optionArea = $("#quickmenu-bar").find("div[name=virtual-basket]");

				fnChkOptnQty($(optionArea).find("input[name=OrderCount]"));// 수량체크

				var itemOrderCount	= Number($(optionArea).find("input[name=OrderCount]").val());
				var itemsCurrSalePrc	= Number($(optionArea).find("input:hidden[name=itemsCurrSalePrc]").val());
				var itemsProdCd		= $(optionArea).find("input:hidden[name=itemsProdCd]").val();
				var itemsCode			= $(optionArea).find("input:hidden[name=itemsCode]").val();

				//console.log("itemOrderCount = ", itemOrderCount);console.log("itemsCurrSalePrc = ", itemsCurrSalePrc);console.log("itemsProdCd = ", itemsProdCd);console.log("itemsCode = ", itemsCode);

				if(itemOrderCount < 1) {
					alert( view_messages.fail.orderCntFail );
					return false;
				}

				basketItems.push({
					prodCd: itemsProdCd,			// 상품코드
					itemCd: itemsCode,				// 단품코드
					bsketQty: itemOrderCount,		// 주문수량
					categoryId: CATEGORY_ID,		// 카테고리ID
					nfomlVariation: null,				// 옵션명, 골라담기의 경우 옵션명:수량
					overseaYn: overseaYn,			// 해외배송여부
					prodCouponId: null,				// 즉시할인쿠폰ID
					oneCouponId: null,				// ONE 쿠폰ID
					cmsCouponId: null,				// CMS 쿠폰ID
					markCouponId: null,				// 마케팅제휴쿠폰ID
					periDeliYn: periDeliYn,			// 정기배송여부
					ordReqMsg: ordReqMsg,		// 구매요청사항
					hopeDeliDy: hopeDeliDy			// 희망배송일
				});

			}
		}

		return basketItems;
	}

	function goPage(curPage){ // 상품평 페이지변경
		getReviewListAjax('', curPage);
	}

	function changeFilter(searchFilter) { // 상품평 조회조건 변경
		var searchFilter = searchFilter;

		if (searchFilter == "") {
			 searchFilter = $("#reviewTab .active").attr("id").replace("searchFilter", "");
		}

		getReviewListAjax(searchFilter, '1');
	}

	function goQuesPage(curPage){
		getQuestionListAjax('', curPage);
	}

	// 함께 구매/함께 본 상품 조회
	var smartListCnt = 0;
	function fnSmartOfferList(SmartOfferGB) {
		$.api.get({
			apiName : 'mobileSmartOffer',
			data : {
				"ProductCD": prodBasicInfo.PROD_CD,
				"CategoryID": prodBasicInfo.CATEGORY_ID,
				"SmartOfferGB": SmartOfferGB,
				"SmartListCnt": smartListCnt
			},
			dataType : 'html',
			successCallback : function( data ) {
				if( data == "" ) {
					$("#smartOffer").remove();
				} else {
					if( SmartOfferGB == "Order" ) $("#smartOffer_Order").append(data);
					if( SmartOfferGB == "Search" ) $("#smartOffer_Search").append(data);

					if (!!$.fn.lazyload) {
						$('.type-groupgoods .lazy-img').lazyload({effect: 'fadeIn'});
						$('.type-groupgoods').on('scroll', function() {
							$(window).trigger('scroll');
						});
					}
					smartListCnt++;
				}
			}
		});
	}

	//(체크박스 선택한) 쿠폰 다운로드
	function selectedCouponDownFn() {
		var repCouponIdArray = new Array();
		var dcCpbookCdArray = new Array();
		$('.wrap-coupon-list > div').each(function(){
			repCouponIdArray.push($(this).data("repcouponid"));
			dcCpbookCdArray.push($(this).data("dccpbookcd"));
		});

		//쿠폰 다운로드 실행 호출
		couponDownFn(repCouponIdArray, dcCpbookCdArray, 'all');
	}

	//쿠폰 다운로드 실행
	function couponDownFn(repCouponId, dcCpbookCd, task) {

		var str_repCouponId = "";
		var str_dcCpbookCd = "";

		if ($.isArray(repCouponId)) {
			//선택다운로드
			str_repCouponId = repCouponId.join(",");
			str_dcCpbookCd = dcCpbookCd.join(",");
		} else {
			//개별다운로드
			str_repCouponId = repCouponId;
			str_dcCpbookCd = dcCpbookCd;
		}

		var flag = global.isLogin(_LMAppUrlM+"/mobile/cate/PMWMCAT0004_New.do?ProductCD="+prodBasicInfo.PROD_CD+"&CategoryID="+prodBasicInfo.CATEGORY_ID);

		if(flag){
			$.ajax({
				type	 : "POST",
				url		 : "/api/coupon/multiIssued.do",
				data	 : {'repCouponIds' : str_repCouponId, 'dcCpbookCds' : str_dcCpbookCd},
				dataType : 'json',
				success  : function(data) {
					alert(data.rtnMsg);

					if (data.rtn == "success") {
						var couponIds = data.rtnData.split(",");			//data.rtnData 최종 등록된 대표쿠폰ID
						var len = couponIds.length;

						if ( task === 'all' ) {
							$('.wrap-coupon-list > div').remove();
							$('.js-close').trigger('click');
							$("#couponDownTr").remove();
						}else{
							for(var i = 0; i < len; i++) {
								//다운로드 완료시 마크업 삭제 처리
								console.log( 'str_repCouponId: ' + str_repCouponId );
								$('#couponZone_' + str_repCouponId).remove();
							}
						}

						var availableCouponCount = $('.wrap-coupon-list > div').length;
						if (availableCouponCount == 0) {
							//할인쿠폰 다운받기 영역 비노출
							$('.js-close').trigger('click');
							$("#couponDownTr").hide();
						} else {
							//다운받을 수 있는 쿠폰수 재설정
							$("#availableCouponCount").text(availableCouponCount);
						}
					}
				},
				error: function(xhr, Status, error) {
				}
			});
		}

		couponDownInit();
	}

	// ID별 한정수량 노출
	function getProdLimtQty() {
		var params = {
				ProductCD: prodBasicInfo.PROD_CD
		};

		$.ajax({
			url: "/mobile/product/ajax/prodLimtQtyAjax.do",
			type	: "POST",
			data: params
		})
		.done(function(data) {
			if( typeof(data) != "undefined" && data.ProdLimtQty != null && data.ProdLimtQty.ID_LIMT_QTY >0) {
				$("#limtQty").text(data.ProdLimtQty.ID_LIMT_QTY);
				$("#limtQtyLI").show();
			}
		});

	}

	function getCookie(cname) {
	    var name = cname + "=";
	    var ca = document.cookie.split(';');
	    for(var i = 0; i < ca.length; i++) {
	        var c = ca[i];
	        while (c.charAt(0) == ' ') {
	            c = c.substring(1);
	        }
	        if (c.indexOf(name) == 0) {
	            return c.substring(name.length, c.length);
	        }
	    }
	    return "";
	}

	//장바구니 수량 조회
	function basketCount() {
		$.getJSON("/basket/api/count.do")
		.done(function(data) {
			if (data) {
				if( data.count > 0 ) {
					document.querySelector('#basketCountData').dataset.qty = data.count;
				}
			}
		});
	}

// 상품 확대보기
var GoodsSpotLayer = (function() {
	$.templates('GoodsDetailSpotLayer',
		'<div class="swiper-goods-spot">' +
		'	<div class="swiper-wrapper">' +
		'	{{for dataLists}}' +
		'		<div class="swiper-slide">' +
		'			<div class="swiper-zoom-container">' +
		'				<img src="{{:src}}" alt="{{:alt}}" onerror="this.src = _LMCdnV3RootUrl + \'/images/layout/noimg_prod_640x640.jpg\';">' +
		'			</div>' +
		'		</div>' +
		'	{{/for}}' +
		'	</div>' +
		'	<div class="swiper-pagination"></div>' +
		'</div>' +
		'<button type="button" class="icon-close">레이어 닫기</button>'
	);

	var $html = $('html')
		, $wrapObj = $('#mobileImg')
		, $obj = $wrapObj.find('.swiper-slide')
		, dataGoodsList = {}
		, $wrapLayer = null
		, spotSwiper = null;

	dataGoodsList.dataLists = [];

	function getData() {
		if (dataGoodsList.dataLists.length > 0) return;

		for (var i = $obj.length; i > 0; i--) {
			var $objImg = $obj.eq(i-1).find('img');

			dataGoodsList.dataLists.unshift({
				'index': i
				, 'src': $objImg.attr('src')
				, 'alt': $objImg.attr('alt') || '상품 이미지 확대보기'
			});
		}
	}

	function parseHTML(data) {
		var $docFrag = document.createDocumentFragment();
		var $layer = document.createElement('div');
		$layer.classList.add('wrap-goods-spot-layer');
		$layer.innerHTML = $.render['GoodsDetailSpotLayer'](data);
		$docFrag.appendChild($layer);
		return $docFrag;
	}

	function openLayer(idx) {
		if (!$wrapLayer) {
			getData();
			var _temp = parseHTML(dataGoodsList);
			$('body').append(_temp);
			$wrapLayer = $html.find('.wrap-goods-spot-layer');
		}

		$wrapLayer.addClass('active');
		setTimeout(function () {
			$html.addClass('layer-masking-mall');
		}, 300);

		addEvent(idx);
	}

	function addEvent(idx) {
		if (!spotSwiper) {
			var wrapLayer = '.swiper-goods-spot';

			if (typeof Swiper4 === 'undefined') {
				spotSwiper = new Swiper(wrapLayer, {
					initialSlide: idx
					, zoom: !0
					, pagination: wrapLayer + ' .swiper-pagination'
					, onInit: function(swiper) {
						swiperZoomInit();
					}
				});
			} else {
				spotSwiper = new Swiper4(wrapLayer, {
					initialSlide: idx
					, zoom: !0
					, pagination: {
						el: wrapLayer + ' .swiper-pagination'
					}
					, on: {
						init: function () {
							swiperZoomInit();
						}
					}
				});
			}
		} else {
			spotSwiper.slideTo(idx, 0);
			swiperZoomInit();
		}

		function swiperZoomInit() {
			var $tgt = $wrapLayer.find('.swiper-slide-active').find('.swiper-zoom-container')
				, $siblings = $wrapLayer.find('.swiper-slide-active').siblings();
			$tgt.find('img').css('transform', 'scale('+ $tgt.height() / $tgt.width() +')');
			$siblings.find('img').css('transform', 'scale(1)');
		}

		$wrapLayer.find('.icon-close').on('click', function () {
			$wrapLayer.removeClass('active');
			$html.removeClass('layer-masking-mall');
		})
	}

	return {
		init: getData,
		open: openLayer
	}
})();

// D코드 상품 상세 옵션 레이어
var DcodeProdDetails = (function () {
	$.views.helpers({
		prodDetailsImageGroup: function (qty, code) {
			var res = [];
			var imageCode = code.substring(0, 5) + '/' + code.substring(5, 10) + '/' + code;
			var d = new Date();
			var h = d.getHours() < 10 ? "0" + d.getHours() : d.getHours();
			for (; qty > 0; qty--) {
				res.unshift({
					'src': $.utils.config('LMCdnStaticRootUrl') + '/images/snapshot/product/' + imageCode + '_' + qty + '.jpg'+'?V='+d.yyyymmdd()+h
				});
			}
			return res;
		},
		getItemImgPath: function (prodCode) {
			if (!prodCode) return '';

			var dirName = prodCode.substring(0, 5).trim();

			return $.utils.config('LMCdnStaticRootUrl') + '/images/prodimg/' + dirName + '/' + prodCode + '_1_208.jpg';
		}
	});

	$.templates('dcodeProdLayer',
		'<h2 class="title">선택 상품 상세</h2>' +
		'<div class="wrap-thumbnails"></div>' +
		'<div class="wrap-views" id="wrap-views">' +
		'	<div class="swiper-wrapper" id="prodSelectDetails">' +
		'	{{for dataLists}}' +
		'		<div class="swiper-slide">' +
		'			<div class="wrap-select-detail">' +
		'				{{include tmpl="dcodeProdDetails" /}}' +
		'				{{include tmpl="dcodeProdBadge" /}}' +
		'				{{include tmpl="dcodeProdImg" /}}' +
		'			</div>' +
		'		</div>' +
		'	{{/for}}' +
		'	</div>' +
		'</div>' +
		'<div class="swiper-button-next"></div>' +
		'<div class="swiper-button-prev"></div>' +
		'<button type="button" class="icon-close">레이어 닫기</button>'
	);

	$.templates('dcodeProdDetails',
		'<div class="wrap-gdi-optional-list">' +
		'	<div class="thumbnail">' +
		'		<img src="{{:~getItemImgPath(prodCode)}}" alt="{{:title}}">' +
		'	</div>' +
		'	<div class="wrap-conts">' +
		'		<p class="title-option">선택{{:index}}</p>' +
		'		<div class="wrap-contents">' +
		'			<p class="title">{{:title}}</p>' +
		'			<p class="price">' +
		'			{{if beforePrice}}' +
		'				<del class="before">판매가 <i class="number">{{:beforePrice}}</i>원</del>' +
		'				<span class="sellqty">{{:beforePriceDesc}}</span>' +
		'				<strong class="after">최저가 <i class="number-point">{{:price}}</i>원</strong>' +
		'				<span class="sellqty">{{:priceDesc}}</span>' +
		'			{{else}}' +
		'				<strong class="after">판매가 <i class="number-point">{{:price}}</i>원</strong>' +
		'				<span class="sellqty">{{:priceDesc}}</span>' +
		'			{{/if}}' +
		'			</p>' +
		'		</div>' +
		'	</div>' +
		'</div>'
	);

	$.templates('dcodeProdBadge',
		'<div class="wrap-select-badge">' +
		'{{for delivery}}' +
		'	<i class="prod-icon type1">{{:item}}</i>' +
		'{{/for}}' +
		'{{for benefit}}' +
		'	<i class="prod-icon type2">{{:item}}</i>' +
		'{{/for}}' +
		'</div>'
	);

	$.templates('dcodeProdImg',
		'<div class="wrap-option-images{{if soldoutchk}} soldout{{/if}}">' +
		'{{for ~prodDetailsImageGroup(imglength, prodDetailCode)}}' +
		'	<img src="//simage.lottemart.com/v3/images/temp/blank.png" data-src="{{:src}}" alt="{{:title}}" class="swiper-lazy">' +
		'{{/for}}' +
		'</div>'
	);

	var $html = $('html');
	var $wrapLayer = null;
	var $wrapZoom = null;
	var galleryTop = null;
	var _scrollTop = 0;

	function getDataGoodsOption() {
		var dataDcodeProdList = {};
		var returnData = [];
		var $listItems = $('.wrap-gdi-optional-list');
		var $viewItems = $('.wrap-gdi-optional-view');

		for (var i = $listItems.length; i > 0; i--) {
			var _idx = i - 1;
			var _listItem = $listItems.eq(_idx);
			var _viewItem = $viewItems.eq(_idx);
			var _dataProdCode = _listItem.find('.lazy-img').data('original').split('_1_208.jpg')[0];
			var _dataProdDetailCode = _viewItem.find('.wrap-option-images').find('.lazy-img:eq(0)').data('original').split('_1.jpg')[0];
			var _listItemPrice = _listItem.find('.price');
			var _listItemPriceBefore = _listItemPrice.find('.before');
			var _listItemPriceAfter = _listItemPrice.find('.after');
			var _iconDelivery = [];
			var _tempDelivery = _viewItem.find('#deliType_detail_' + _idx + ' em');
			var _iconBenefit = [];
			var _tempBenefit = _viewItem.find('#benefitTag_detail_' + _idx + ' i');

			for (var j = _tempDelivery.length; j > 0; j--) _iconDelivery.unshift({
				'item': _tempDelivery.eq(j - 1).text()
			});
			for (var j = _tempBenefit.length; j > 0; j--) _iconBenefit.unshift({
				'item': _tempBenefit.eq(j - 1).text()
			});

			returnData.unshift({
				'index': i,
				'prodCode': _dataProdCode.substr(_dataProdCode.length - 13),
				'prodDetailCode': _dataProdDetailCode.substr(_dataProdDetailCode.length - 13),
				'title': _listItem.find('.title').text(),
				'beforePrice': _listItemPriceBefore.find('.number').text(),
				'price': _listItemPriceAfter.find('.number-point').text(),
				'beforePriceDesc': _listItemPriceBefore.next('.sellqty').text(),
				'priceDesc': _listItemPriceAfter.next('.sellqty').text(),
				'benefit': _iconBenefit,
				'delivery': _iconDelivery,
				'imglength': _viewItem.find('.wrap-option-images img').length,
				'soldoutchk': (_listItem.closest('.wrap-item').hasClass('soldout')),
			});
		}
		dataDcodeProdList.dataLists = returnData;

		return dataDcodeProdList;
	}

	function parseHTML(data) {
		var $docFrag = document.createDocumentFragment();
		var $layer = document.createElement('div');
		$layer.classList.add('wrap-goods-layer');
		$layer.innerHTML = $.render['dcodeProdLayer'](data);
		$docFrag.appendChild($layer);
		return $docFrag;
	}

	function open(idx) {
		_scrollTop = $(window).scrollTop();
		if (!$wrapLayer) {
			var _temp = parseHTML(getDataGoodsOption());
			$('body').append(_temp);
			$wrapLayer = $html.find('.wrap-goods-layer');
			$wrapZoom = $wrapLayer.find('#wrap-views');
		}

		$wrapLayer.addClass('active');
		setTimeout(function () {
			$html.addClass('layer-masking-mall');
		}, 300);

		addEvent(idx);
		initLayer();
	}

	function initLayer() {
		var $wrapScroll = $wrapLayer.find('.wrap-thumbnails');
		var $target = $wrapScroll.find('.swiper-pagination-bullet-active');
		activePos = ($target.length > 0) ? ($target.offset().left - $wrapScroll.offset().left - $wrapScroll.outerWidth() / 2 + $target.outerWidth() / 2) + $wrapScroll.scrollLeft() : 0;
		$wrapZoom.scrollTop(0);
		$wrapScroll.scrollLeft(activePos);
	}

	function addEvent(idx) {
		var $layerClose = $wrapLayer.find('.icon-close');

		var setPinchZoom = (function () {
			var $wrapDetails = $('.swiper-slide-active').find('.wrap-select-detail');

			$wrapDetails.attr('id', 'wrapPinch');
			function removeToast() {
				$wrapDetails.addClass('pinch-ready');
			}
			$wrapDetails.on('touchstart', function () {
				removeToast();
			})
			setTimeout(function () {
				removeToast();
			}, 3000)
		})

		var _enableZoom = (function () {
			var scroller = undefined;

			return function (swiper) {
				if (scroller !== undefined) {
					scroller.destroy();
				}
				var wrapper = document.querySelector('#wrap-views');

				scroller = new iScroll((wrapper.querySelectorAll('.swiper-slide')[swiper.activeIndex]), {
					zoom: true,
					onZoomEnd: function (e) {
						var slide = $(this.wrapper);
						if (parseInt(this.scale) == 1) {
							slide.removeClass('swiper-no-swiping');
						} else {
							slide.addClass('swiper-no-swiping');
						}
					},
					onZoomStart: function (e) {
						if (e.type === 'touchstart') {
							this.originX = Math.abs(e.touches[0].pageX + e.touches[1].pageX) / 2 - this.x;
						} else if (e.type === 'touchend') {
							this.wrapperOffsetLeft = 0;
						}
					},
				})
			};
		})();

		if (!galleryTop) {
			var wrapLayer = '.wrap-goods-layer';
			galleryTop = new Swiper('#wrap-views', {
				initialSlide: idx,
				lazyLoading: true,
				pagination: wrapLayer + ' .wrap-thumbnails',
				paginationClickable: true,
				nextButton: wrapLayer + ' .swiper-button-next',
				prevButton: wrapLayer + ' .swiper-button-prev',
				paginationBulletRender: function (swiper, index, className) {
					var $swiperSlide = $wrapZoom.find('.swiper-slide').eq(index);
					var _backgroundImage = $swiperSlide.find('.thumbnail img').attr('src');
					var flagSoldout = ($swiperSlide.find('.wrap-option-images').hasClass('soldout')) ? ' soldout' : '';
					return '<div class="' + className + '"><div class="thumbnail' + flagSoldout + '"  style="background-image: url(' + _backgroundImage + ')"></div>선택' + (index + 1) + '</div>';
				},
				onTransitionStart: initLayer,
				onSlideChangeEnd: _enableZoom,
				onInit: function(swiper) {
					_enableZoom(swiper);
					setPinchZoom();
				},
				touchAngle: 25,
				threshold: 30
			});
		} else {
			galleryTop.slideTo(idx, 0);
		}

		$layerClose.on('click', function () {
			$wrapLayer.removeClass('active');
			$html.removeClass('layer-masking-mall');
			if (_scrollTop > 0) $(window).scrollTop(_scrollTop);
		})
	}

	return {
		init: getDataGoodsOption,
		open: open
	}
})();

$(function () {
	$('.btn-option-detail').on('click', function () {
		var idx = $(this).closest('.wrap-item').index();
		DcodeProdDetails.open(idx);
	});

	GoodsSpotLayer.init();
	$('#mobileImg').find('img').on('click', function(e) {
		e.preventDefault();
		e.stopPropagation();
		var idx = $(this).closest('.swiper-slide').index();

		GoodsSpotLayer.open(idx);
	});
});