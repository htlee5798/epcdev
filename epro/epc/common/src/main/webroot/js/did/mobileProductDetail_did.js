//"use strict";

 /*
  * Icon Info
  */
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
});
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

			if( data != "" ) {
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
						delivery_notice = '<i class="icon-common-truck"></i> 지금 주문하면 <span class="point1">오늘 <em class="number-point">'+deliStartTm.substring(0,2)+":"+deliStartTm.substring(2)+'</em></span> 부터 '+delivery_text+'가능';
					}
				}  else if ( delivDate == after1dDt ) {
					delivery_notice = '<i class="icon-common-truck"></i> 지금 주문하면 <span class="point1">내일 <em class="number-point">'+deliStartTm.substring(0,2)+':'+deliStartTm.substring(2)+'</em></span> 부터 '+delivery_text+'가능';
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

					smartListCnt++;
				}
			}
		});
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
			if( typeof(data) != "undefined" && data.ProdLimtQty != null) {
				$("#limtQty").text(data.ProdLimtQty.ID_LIMT_QTY);
				$("#limtQtyLI").show();
			}
		});
	}