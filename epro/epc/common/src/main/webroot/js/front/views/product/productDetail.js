//"use strict";

/*
 * Icon Info
 */
var setIcon=[];
setIcon["1"]	= {title:"온라인단독(센터단독)",	iconSeq: "1",		iconOrder: 99,		iconType: "nomal",		sticker: "icon-goods-type9",		tag: "icon-band-tag9"};
setIcon["2"]	= {title:"클리어런스%",				iconSeq: "2",		iconOrder: 1,		iconType: "custom",	sticker: "icon-goods-discount",	tag: "icon-band-discount"};
setIcon["3"]	= {title:"할인율(%)",					iconSeq: "3",		iconOrder: 2,   	iconType: "custom",	sticker: "icon-goods-discount",	tag: "icon-band-discount"};
setIcon["4"]	= {title:"온라인단독프로모션",		iconSeq: "4",		iconOrder: 3,		iconType: "nomal",		sticker: "flag-s-{{:ICON_IDNT_NO}}",	tag: "flag-s-{{:ICON_IDNT_NO}}"};
setIcon["5"]	= {title:"M+N",							iconSeq: "5",		iconOrder: 4,		iconType: "custom",	sticker: "icon-goods-bundle",	tag: "icon-band-bundle"};
setIcon["6"]	= {title:"증정",							iconSeq: "6",		iconOrder: 11,		iconType: "nomal",		sticker: "icon-goods-type13",	tag: "icon-band-tag13"};
setIcon["7"]	= {title:"L.POINT 할인",					iconSeq: "7",		iconOrder: 5,		iconType: "nomal",		sticker: "icon-goods-type4",		tag: "icon-band-tag4"};
setIcon["8"]	= {title:"L.POINT 혜택",					iconSeq: "8",		iconOrder: 6,		iconType: "nomal",		sticker: "icon-goods-type3",		tag: "icon-band-tag3"};
setIcon["9"]	= {title:"카드 할인",				iconSeq: "9",		iconOrder: 7,	  	iconType: "nomal",		sticker: "icon-goods-type8",		tag: "icon-band-tag8"};
setIcon["10"]	= {title:"카드 혜택",						iconSeq: "10",	iconOrder: 8 ,	iconType: "nomal",		sticker: "icon-goods-type7",		tag: "icon-band-tag7"};
setIcon["11"]	= {title:"다둥이 할인",			iconSeq: "11",	iconOrder: 9,	iconType: "nomal",		sticker: "icon-goods-type6",		tag: "icon-band-tag6"};
setIcon["12"]	= {title:"다둥이 혜택",			iconSeq: "12",	iconOrder: 10,	iconType: "nomal",		sticker: "icon-goods-type5",		tag: "icon-band-tag5"};
setIcon["13"]	= {title:"M쿠폰",							iconSeq: "13",	iconOrder: 14,	iconType: "nomal",		sticker: "icon-goods-type16",	tag: "icon-band-tag16"};
setIcon["14"]	= {title:"증정",				iconSeq: "14",	iconOrder: 11,	iconType: "nomal",		sticker: "icon-goods-type10",	tag: "icon-band-tag10"};
setIcon["15"]	= {title:"증정",					iconSeq: "15",	iconOrder: 11,	iconType: "nomal",		sticker: "icon-goods-type11",	tag: "icon-band-tag11"};
setIcon["16"]	= {title:"살수록 더싸게",				iconSeq: "16",	iconOrder: 12,	iconType: "nomal",		sticker: "icon-goods-type1",		tag: "icon-band-tag1"};
setIcon["17"]	= {title:"같이 더싸게",					iconSeq: "17",	iconOrder: 13,	iconType: "nomal",		sticker: "icon-goods-type2",		tag: "icon-band-tag2"};
setIcon["18"]	= {title:"할인율(원)",					iconSeq: "18",	iconOrder: 2,	iconType: "nomal",		sticker: "icon-goods-won",		tag: "icon-band-won"};
setIcon["19"]	= {title:"딜상품",							iconSeq: "19",	iconOrder: 23,		iconType: "nomal",		sticker: "icon-goods-type14",	tag: "icon-band-tag14"};
setIcon["20"]	= {title:"L.POINT",							iconSeq: "20",	iconOrder: 5.1,		iconType: "nomal",		sticker: "icon-goods-type4-1",	tag: "icon-band-tag4-1"};
setIcon["21"]	= {title:"L.POINT",							iconSeq: "21",	iconOrder: 5.2,		iconType: "nomal",		sticker: "icon-goods-type4-2",	tag: "icon-band-tag4-2"};
setIcon["22"]	= {title:"다둥이",							iconSeq: "22",	iconOrder: 9.1,		iconType: "nomal",		sticker: "icon-goods-type6-1",	tag: "icon-band-tag6-1"};
setIcon["23"]	= {title:"다둥이",							iconSeq: "23",	iconOrder: 9.2,		iconType: "nomal",		sticker: "icon-goods-type6-2",	tag: "icon-band-tag6-2"};

var productDetail = (function() {
	var smartListCnt = 0;

	var wireUpEvents = function() {
		var shareElement = $("#container .detail-share"), // Social Link
			$snsShareWrap = $('#snsShareWrap');
		//프린트
		var printArea = function() {
			var sw = screen.width;
			var sh = screen.height;
			var w = 1070;	//팝업창 가로
			var h = 880;	//팝업창 세로
			var xpos = (sw - w) / 2; //화면에 띄울 위치
			var ypos = (sh - h) / 2;

			var pHeader = "<html><head>";
			pHeader += "<link rel='stylesheet' href='/v3/styles/styles.css'/><link rel='stylesheet' href='/v3/styles/contents.css'/>";
			pHeader += "<title>" + prodBasicInfo.PROD_NM + "</title></head><body>";
			pHeader += "<article id='document' class='wrapper-category'><div id='container'><article id='contents'><div class='wrap-in'><section class='prod-detail'>";
			var pgetContent = $("#container .prod-detail").html() + "<br>";
			var pFooter = "</section></div></article></div></article></body></html>";
			var pContent = pHeader + pgetContent + pFooter;

			var pWin = window.open("", "print", "width=" + w + ",height=" + h + ",top=" + ypos + ",left=" + xpos + ",status=no,scrollbars=yes"); // 인쇄할 새창 오픈
			pWin.document.open();
			pWin.document.write(pContent);
			$('.prod-detail-info .prod-decision', pWin.document).remove();	// 버튼영역 삭제

			setTimeout(function() {
				pWin.document.close();	// 클로즈
				pWin.print();					// 인쇄창 오픈
				pWin.close();					// 인쇄 또는 취소시 팝업창 닫기
			}, 500);
		};

		// 배송비 절약하기
		$(".relation-delivery").on('click', "#saveDeliPrice", function(e) {
			var vendorNm = prodBasicInfo.VENDOR_NM;

			if (vendorNm.indexOf("주) ")) {
				vendorNm = vendorNm.replace("주) ", "주)")
			}
			var popup = window.open(_LMAppUrl + "/search/search.do?searchTerm=" + encodeURIComponent(vendorNm) + "&viewType=gallery");
			if (popup) popup.focus();
		});

		// 동영상
		$('.thumb-slist-movie .play-btn').on('click', function(event) {
			var key = $(this).prop("id");
			var templateStr = $.templates("#videoPlayer");
			var result = templateStr.render({
				src: key
			});
			var requestUri = location.href.replace(location.origin, '');
			ga('send', 'event', {
				eventAction: '동영상 콘텐츠 | 동영상 보기',
				eventCategory: '#기기=P #분류=PDP #페이지명=상품상세정보 #URL=' + requestUri,
				eventLabel: '#CID=' + prodBasicInfo.CATEGORY_ID + ' #PID=' + prodBasicInfo.PROD_CD
			});

			$("#videoPlayer").text(result);
			fadeContShowYoutube(this, event);
		});

		// 배송유형 관련
		$('.relation-delivery').on('click', 'input:checkbox', function(e) {
			var type = $(e.target);

			if (type.is("#periDeliYn")) {
				//정기배송
				if (!$("#btnSoldOut").hidden) {
					if ($(this).prop("checked")) {
						$("#btnBuy").hide();
						$("#btnPeri").show();
					} else {
						$("#btnBuy").show();
						$("#btnPeri").hide();
					}
				}
			} else if (type.is("#forgnDeliYn")) {
				//해외배송
				if (!$("#btnSoldOut").hidden) {
					if ($(this).prop("checked")) {
						$("#btnBuy").hide();
						$("#btnForgn").show();
					} else {
						$("#btnBuy").show();
						$("#btnForgn").hide();
					}
				}
			}
		});

		//프린트
		$("a.print", shareElement).click(function(e) {
			eventPropagationWrapper(e, printArea);
		});

		$snsShareWrap.find('a').each(function() {
			$.utils.shareHandler.bind($(this));
		});
	};

	var setClipboard = function() {
		if (window.Clipboard) {
			var clipboard = new Clipboard('.link-copy', {
				text: function(trigger) {
					return "[" + prodBasicInfo.PROD_NM + "] " + location.href;
				}
			});

			clipboard.on('success', function() {
				alert(view_messages.copylink.success);
			});

			clipboard.on('error', function() {
				alert(view_messages.copylink.fail);
			});
		};
	};

	var setUI = function() {
		// 기획전 바로가기
		$('[id=selectPlanTheme]').selectmenu({
			change: function() {
				var categoryID = $(this).val();
				document.location.href = _LMAppUrl + "/plan/planDetail.do?CategoryID=" + categoryID;
			}
		});

		// 관리자 미리보기 점포변경
		$('[id=selectStoreAdmin]').selectmenu({
			change: function() {
				var strCd = $(this).val();
				var prodCd = $("#FormProdInfo #PROD_CD").val();
				document.location.href = _LMAppUrl + "/product/ProductDetail.do?previewYN=Y&ProductCD=" + prodCd + "&strCd=" + strCd;
			}
		});

		if ($("#tab02").length > 0 || $("#tab03").length > 0) {
			// 상품평리스트 등록순, 추천순 선택
			$('[id=orderSelect]').selectmenu({
				change: function() {
					changeFilter('');
				}
			});

			// 상품평리스트 등록순, 추천순 선택
			$('[id=quesOrder]').selectmenu({
				change: function() {
					getQuestionListAjax('', '1');
				}
			});

			// 상품평 상품리스트 옵션 선택
			$('[id=reviewProdOptn]').selectmenu({
				change: function() {
					getReviewListAjax('', '1');
				}
			});

			// 상품문의 상품리스트 옵션 선택
			$('[id=quesProdOptn]').selectmenu({
				change: function() {
					getQuestionListAjax('', '1');
				}
			});
			// 상품평 평균 조회
			getReviewCntAjax();

			// 상품평 조회
			getReviewListAjax('', '1');
			// 상품문의 조회
			getQuestionListAjax('', '1');
		}
	};

	// Best 인기 상품/추가 구매시 좋은 상품 조회
	var fnSmartOfferList = function(smartOfferGB) {
		//console.log("fnSmartOfferList()");
		var prodCd = prodBasicInfo.PROD_CD,
			categoryId = prodBasicInfo.CATEGORY_ID,
			params = {
				"ProductCD": prodCd,
				"CategoryID": categoryId,
				"SmartOfferGB": smartOfferGB,
				"SmartListCnt": smartListCnt
			},
			url = "/product/ajax/smartOfferAjax.do",

			$frag = undefined,
			anothrChoosenSlider = undefined;

		$.ajax({
			type: "POST",
			url: url,
			data: params,
			dataType: 'html',
			success: function(data) {
				var sliderSettings = {
					mode: 'fade',
					pager: true,
					speed: 0,
					onSliderLoad: function(activeIdx) {
						anothrChoosenSlider = this;
						_wac.bx.setAccessibility(anothrChoosenSlider, sliderSettings.autoHover);

						//190806 롯데마트 웹접근성 : 원은재
						var $nowFocus, $activeSlide,
							$bxViewport = anothrChoosenSlider.parent(), //뷰포트
							$bxWrap = $bxViewport.parent(), // 래퍼
							$bxControls = $bxWrap.find('.bx-controls'), //컨트롤러
							$lastPager = _wac.getFocusables($bxControls).last();
							$bxViewport.insertAfter($bxControls);

						var bxSlideLastItemKeydownHandler = function(e){
							_wac.preventDefaultAction(e);
							$bxControls.find('.bx-pager-link.active').focus();
						}
						var bxControlsKeydownHandler = function(e){
							$nowFocus = $(this)

							if(_wac.isEnterEvent(e)){
								_wac.markWacEnteredAsync($nowFocus)
							}else if(_wac.isTabEvent(e)){
								if(_wac.isWacEntered($nowFocus)){
									_wac.preventDefaultAction(e);
									_wac.removeWacEnteredAsync($nowFocus)
									$activeSlide = $bxViewport.find('[aria-hidden="false"]');
									$activeSlide = _wac.getFocusables($activeSlide)
									$activeSlide.first().focus();

									_wac.wireUpEventAsync($activeSlide.last(), 'keydown', bxSlideLastItemKeydownHandler );
								}else if($(document.activeElement).is($lastPager)){
									_wac.preventDefaultAction(e);
									_wac.visibleFocusables($('.ad-aside')).first().focus();

								}
							}
						}

						$bxControls.on({
							keydown : bxControlsKeydownHandler

						})


					},
				};

				//console.log("data = ", data);
				_wac.isEmpty(data)
					? $("#smartOffer").remove()
					: (function() {
						$.utils.deferredAction(function() {
							((smartOfferGB === 'Order') || (smartOfferGB === 'Search'))
								&& _wac.isEmpty($frag)
								&& ($frag = $(document.createDocumentFragment()));

							(smartOfferGB === 'Order')
								&& (function() {
									$frag.append(data);
									$("#smartOffer_Order").append($frag);
								})();

							(smartOfferGB === 'Search')
								&& (function() {
									$frag.append(data)
									$("#smartOffer_Search").append($frag);
								})();
						}).then(function() {
							var $slideEl = $('.anothr-choosen .wrap-prod-list');

							!_wac.isEmpty($slideEl)
								&& $slideEl.bxSlider(sliderSettings);
						});
						smartListCnt++;
					})();
			},
			error: function(xhr, Status, error) {
			}
		});
	};

	var init = function() {
		window.fnSmartOfferList = fnSmartOfferList;
		$.utils.deferredAction(setUI, wireUpEvents, setClipboard);
	};

	return {
		init: init,
	};
})();



//셀렉트박스용 카테고리 리스트 조회
function goCategoryListAjax(p_categoryId, p_depth) {
	var params = {
		'CategoryID': p_categoryId,
		'Depth': p_depth
	};

	fn$ajax(_LMAppUrl+"/product/ajax/categoryListAjax.do", params, fnNmGetter().name);
}

function callBack_$goCategoryListAjax(response) {
	$("#catNavi").html(response);
}

//영양정보 Link : Lotte All Safe
function ProdHistory(prod_cd){
	window.open("http://www.lotteallsafe.com/controls/safesearch/pop/init?GOOD_CODE="+prod_cd,"popup","left=0, top=0, width=670,height=640,scrollbars=yes");
}

/**
* 상품 이미지 정보 (PC)
*/
function setProductImg(itemsSrcmkCD, itemsImgQty) {
	var imgWidth = "640",
		smlimg = "",
		$frag = undefined,
		$wrapper = $(".prod-detail-info .wrap-thumb-slist"),
		$imgList = $wrapper.find(".thumb-slist-remote"),
		$moveList = $wrapper.find(".thumb-slist-movie"),
		imgThumbSlider = undefined,
		movieThumbSlider = undefined,
		deferredArr1 = [],
		deferredArr2 = [];

	var imgThumbSliderSettings = {
		slideWidth: 104,
		minSlides: 1,
		maxSlides: 3,
		pager: false,
		infiniteLoop: false,
		hideControlOnEnd: true,
		autoHover: true,
		onSliderLoad: function(activeIdx) {
			imgThumbSlider = this;
			$.utils.deferredAction(function() {
				_wac.bx.setAccessibility(imgThumbSlider, imgThumbSliderSettings.autoHover);
			});
		},
	};

	var movieThumbSliderSettings = {
		slideWidth: 104,
		minSlides: 1,
		maxSlides: 3,
		pager: false,
		infiniteLoop: false,
		hideControlOnEnd: true,
		autoHover: true,
		onSliderLoad: function(activeIdx) {
			movieThumbSlider = this;
			$.utils.deferredAction(function() {
				_wac.bx.setAccessibility(movieThumbSlider, movieThumbSliderSettings.autoHover);
			});
		},
	};

	var activeCont = function(ele) {
		var $active = ele;

		$active.find("li:first-child").addClass("active");
		$active.find("a").click(function(e) {
			var $this = $(this);

			e.preventDefault();
			$this
				.closest("li")
				.addClass("active")
				.siblings("li")
				.removeClass("active");
		});
	};

	if (itemsSrcmkCD.length >= 5) {
		$("#imgBigSize").prop("src", getImgPass(itemsSrcmkCD, imgWidth, "1"));
		$("#imgVideo").prop("src", getImgPass(itemsSrcmkCD, imgWidth, "1"));
	}

	$frag = $(document.createDocumentFragment());
	for (var i = 0; i < itemsImgQty; i++) {
		deferredArr1.push(
			$.utils.deferredAction(function() {
				smlimg +=
					"<li><a href=''><img  width='" +
					imgWidth +
					"' src='" +
					getImgPass(itemsSrcmkCD, imgWidth, i + 1) +
					"' alt='" +
					prodBasicInfo.VENDOR_NM +
					"' class='thumb imgStoB' onerror='javascript:showNoImage(this)' onclick='return false;'></a></li>";
			})
		);
	}

	!_wac.isEmpty(deferredArr1) &&
		$.utils.deferredArrAction(deferredArr1).then(function() {
			$frag.append(smlimg);
			$("#imgSmallSize").append($frag);
		});

	$wrapper.each(function() {
		var $this = $(this);

		deferredArr2.push(
			$.utils.deferredAction(function() {
				$this.find("li").length > 3
					&& !_wac.isEmpty($imgList)
					&& $(".prod-detail-info .thumb-slist-remote").bxSlider(imgThumbSliderSettings);

				$this.find("li").length > 3
					&& !_wac.isEmpty($moveList)
					&& $(".prod-detail-info .thumb-slist-movie").bxSlider(movieThumbSliderSettings);

				activeCont($this);
			})
		);
	});

	!_wac.isEmpty(deferredArr2) && $.utils.deferredArrAction(deferredArr2);

	$imgList.on("click", "a", function(e) {
		var $this = $(this),
			$img = $this.find("img"),
			$bigImg = $(".thumb-bigsize .thumb");

		e.preventDefault();
		$bigImg.prop("src", $img.prop("src"));
	});
}

function getImgPass(itemsSrcmkCD,size,seq){
	var foderNm=itemsSrcmkCD.substring(0,5).trim();
	var imgpass = $("#FormProdInfo #imgpass").val();

	return imgpass+"/"+foderNm+"/"+itemsSrcmkCD+"_"+seq+"_"+size+".jpg";
}

/**
 * 매장배송가능시간 안내
 *
 * @param {any} deliSpFg_SD 근거리배송
 * @param {any} deliSpFg_SP 매장픽업
 * @param {any} deliSpFg_CP 조리픽업
 */
function getDeliveryTimeInfo(deliSpFg_SD, deliSpFg_SP, deliSpFg_CP) {
	var params = {
			'deliSpFg_CP' : deliSpFg_CP
			,'deliSpFg_SP' : deliSpFg_SP
			,'deliSpFg_SD' : deliSpFg_SD
			};

	$.getJSON(_LMAppUrl+"/product/ajax/deliveryTimeInfoAjax.do", params)
	.done(function(data) {
		var delivery_notice = "";
		var delivery_text = deliSpFg_CP=="Y"? "픽업":"배송";

		if( data != "" && data != undefined ) {
			//console.log("deliveryTimeInfo = ", data.deliveryTimeInfo);

			var deliveryTimeInfo = data.deliveryTimeInfo;
			var deliTypeCd = deliveryTimeInfo.DELI_TYPE_CD;											// 배송유형	01: 근거리, 03: 조리픽업
			var delivDate = setDateFormat(deliveryTimeInfo.DELIV_DATE, '-').trim();				// 배송일자
			var deliStartTm = deliveryTimeInfo.DELI_PRAR_START_TM.replace(":", "").trim();	// 배송시작시간
			var ordCloseTm = deliveryTimeInfo.ORD_CLOSE_TM.replace(":", "").trim();			// 주문마감시간

			var nowDt = data.nowDt.replace(":", "").trim();					// 현재시간
			var after2hDt = data.after2hDt.replace(":", "").trim();			// 현재시간 + 2 시간
			var after1dDt = setDateFormat(data.after1dDt, '-').trim();	// 다음날
			var after2dDt = setDateFormat(data.after2dDt, '-').trim();	// 모레

			if( delivDate == nowDt.substring(0, 10).trim() ) {
				if( nowDt.substring(10).trim() < ordCloseTm && deliStartTm < after2hDt.substring(10).trim() ) {
					delivery_text += deliSpFg_CP!="Y"? "출발":"";
					delivery_notice = "지금 주문하시면 <strong>2시간 이내</strong>에 "+delivery_text+"이 가능합니다.";
				} else {
					//김포 허브앤스포크 작업 - 시간 출력하는 부분 수정 - 분 부분을 0으로 나오게끔 수정
					//주석 처리 delivery_notice = "지금 주문하면 <strong>오늘 "+deliStartTm.substring(0,2)+":"+deliStartTm.substring(2)+"</strong> 부터 "+delivery_text+"가능";
					delivery_notice = "지금 주문하면 <strong>오늘 "+deliStartTm.substring(0,2)+":"+deliStartTm.substring(2,3)+"0"+"</strong> 부터 "+delivery_text+"가능";
				}
			}  else if ( delivDate == after1dDt ) {
				//김포 허브앤스포크 작업 - 시간 출력하는 부분 수정 - 분 부분을 0으로 나오게끔 수정
				//주석 처리 delivery_notice = "지금 주문하면 <strong>내일 "+deliStartTm.substring(0,2)+":"+deliStartTm.substring(2)+"</strong> 부터 "+delivery_text+"가능";
				delivery_notice = "지금 주문하면 <strong>내일 "+deliStartTm.substring(0,2)+":"+deliStartTm.substring(2,3)+"0"+"</strong> 부터 "+delivery_text+"가능";
			} else if( delivDate >= after2dDt ) {
				delivery_notice = "현재 주문량이 많아 <strong>모레</strong>부터 "+delivery_text+"이 가능합니다.";
			}
		}

		if(delivery_notice != "") {
			$("#delivery-notice").addClass("delivery-notice");
			$("#delivery-notice").html(delivery_notice);
		} else {
			$("#delivery-notice").removeClass("delivery-notice");
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
		_seedConversionAnalysis(600, basketNo, totalSellAmt, prodNm, currSellPrc, basketQty, categoryId);
		*/
		_seedConversionAnalysis('C03', basketNo, totalSellAmt, prodNm, currSellPrc, basketQty, categoryId);
	}
}

//장바구니
function goDetailShoppingCart(obj, event, popupYn) {
	//console.log("goDetailShoppingCart");

	var PROD_CD = prodBasicInfo.PROD_CD;
	var CATEGORY_ID = prodBasicInfo.CATEGORY_ID;
	var ONLINE_PROD_TYPE_CD = prodBasicInfo.ONLINE_PROD_TYPE_CD;
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

	var flag = global.isLogin(_LMAppUrl+"/product/ProductDetail.do?ProductCD="+PROD_CD+"&CategoryID="+CATEGORY_ID);
	//var flag = global.isLogin(_ReqURL);
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
		if( $(".relation-option .option-select").find("select[name!=optnCtpd]").length > 0 || ONLINE_PROD_TYPE_CD == "05" ) {
			if( $(".relation-option .option-list").find("li").length < 1 ) {
				// 가상 장바구니 상품 없음
				alert( view_messages.select.selectMainProd );
				fnResetOptn();
				return;
			}
		}

		var rtnBasketItems;
		var basketType = ( periDeliYn == "Y" )?"B":( overseaYn == "Y" )?"C":"";

			// 2016.05.11 신지원 추가 부진재고 상품 - 시작
		if ( bujinYN == 'Y' && ctrTypeDivnCd == '2' ) {  //(저온)부진재고 상품일때, 4시이전 주문가능, 4시이후 주문서비스마감
					if (limitTime == 'N') { // 주문가능
					// 장바구니
						if (confirm( view_messages.bujin.orderInfo+"\n장바구니에 담으시겠습니까?")){
							rtnBasketItems = makeBasketItems(overseaYn, periDeliYn);
							if( rtnBasketItems != false && rtnBasketItems.length > 0 ) {
								if(!!opener) {
									opener.global.addBasket(JSON.stringify( rtnBasketItems ), function(data) { showBasketLayer(obj, event, basketType, popupYn)
										//광고 스크립트 함수 호출
										callAdssomJS(data);
									} );
								} else {
									global.addBasket(JSON.stringify( rtnBasketItems ), function(data) { showBasketLayer(obj, event, basketType, popupYn)
										//광고 스크립트 함수 호출
										callAdssomJS(data);
									} );
								}
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
						if(!!opener) {
							opener.global.addBasket(JSON.stringify( rtnBasketItems ), function(data) { showBasketLayer(obj, event, basketType, popupYn)
								//광고 스크립트 함수 호출
								callAdssomJS(data);
							} );
						} else {
							global.addBasket(JSON.stringify( rtnBasketItems ), function(data) { showBasketLayer(obj, event, basketType, popupYn)
								//광고 스크립트 함수 호출
								callAdssomJS(data);
							} );
						}
					}
				}
				// 2016.05.11 신지원 추가 - 끝
	}
}

function showBasketLayer(obj, event, basketType, popupYn) {
	var clickEvt = "";
	if( popupYn == "Y") {
		clickEvt = "opener.global.goBasket('"+basketType+"');self.close();";
	} else {
		clickEvt = "global.goBasket('"+basketType+"');";
	}

	var templateStr = $.templates("#basketLayer");
	var result = templateStr.render({
		questStr: "<strong>장바구니에 상품이 담겼습니다.</strong><br>지금 확인하시겠습니까?",
		fnClick: clickEvt,
		addClass: ""
	});

	$("#basketLayer").text(result);
			layerpopTriggerBtn(obj, event);
};

//바로구매
function goDetailBuyNow(obj, event) {
	var PROD_CD = prodBasicInfo.PROD_CD;
	var CATEGORY_ID = prodBasicInfo.CATEGORY_ID;
	var ONLINE_PROD_TYPE_CD = prodBasicInfo.ONLINE_PROD_TYPE_CD;
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

	var flag = global.isLogin(_LMAppUrl+"/product/ProductDetail.do?ProductCD="+PROD_CD+"&CategoryID="+CATEGORY_ID);
	if(flag) {//로그인여부

		// 기본 옵션선택 체크 / 딜상품 옵션선택 체크
		if( $(".relation-option .option-select").find("select[name!=optnCtpd]").length > 0 || ONLINE_PROD_TYPE_CD == "05" ) {
			if( $(".relation-option .option-list").find("li").length < 1 ) {
				// 가상 장바구니 상품 체크
				alert( view_messages.select.selectMainProd );
				fnResetOptn();
				return;
			}
		}
		// 2016.05.11 신지원 추가 부진재고 상품 - 시작
		var rtnBasketItems;

		// 2017.10.12 전희찬 추가 점포코드 다를경우 장바구니로 이동 - 시작
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
					global.addBasket(JSON.stringify( rtnBasketItems ), function(data) { showDetailBuyNowBasketLayer(obj, event, basketType)
					} );
					}
		}else{
			// 2017.10.12 전희찬 추가 점포코드 다를경우 장바구니로 이동 - 끝
					if ( bujinYN == 'Y' && ctrTypeDivnCd == '2' ) {  //(저온)부진재고 상품일때, 4시이전 주문가능, 4시이후 주문서비스마감
						if (limitTime == 'N') { // 주문가능

							if (confirm( view_messages.bujin.orderInfo+"\n바로구매 하시겠습니까?")){
								rtnBasketItems = makeBasketItems(overseaYn, periDeliYn);
								if( rtnBasketItems != false && rtnBasketItems.length > 0 ) {
									$(obj).removeClass("purchase").prop("disabled", true);		// 구매버튼처리
									global.addDirectBasket(rtnBasketItems, function(xhr){
										var data = $.parseJSON(xhr.responseText);
										if (data && data.message) {
											$(obj).addClass("purchase").prop("disabled", false);		// 구매버튼 리셋
											alert(data.message);
											return;
										}
									});	//바로구매
								}
							} else {
								return;
							}
						} else { // 주문서비스 마감
					alert( view_messages.bujin.timeout );
					return;
						}
					} else {
						rtnBasketItems = makeBasketItems(overseaYn, periDeliYn);
						if( rtnBasketItems != false && rtnBasketItems.length > 0 ) {
						$(obj).removeClass("purchase").prop("disabled", true);		// 구매버튼처리
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
		}
	}
}

function showDetailBuyNowBasketLayer(obj, event, basketType) {
	var clickEvt = "global.goBasket('"+basketType+"');";
	var templateStr = $.templates("#prestLayer");
	var questStr = "<strong>장바구니에 상품이 담겼습니다.<br>(해당 상품은 장바구니에서만 주문이 가능합니다.)</strong><br>지금 확인하시겠습니까?";
	var result = templateStr.render({
		questStr: questStr,
		fnClick: clickEvt,
		addClass: ""
	});;

	$("#prestLayer").text(result);
			layerpopTriggerBtn(obj, event);
};

var showPrestLayer = {
		basket : function(obj, event) {
			var questStr = "<strong>사은품의 재고가<br>부족하여 지급되지 않을 수 있습니다.</strong><br>장바구니에 담으시겠습니까?";
			var templateStr = $.templates("#basketLayer");
			var result = templateStr.render({
				questStr: questStr,
				fnClick: "goDetailShoppingCart(this, event);",
				addClass: "btn-close"
			});
			$("#basketLayer").text(result);
					layerpopTriggerBtn(obj, event);
		},
		purchase :  function(obj, event) {
			var questStr = "<strong>사은품의 재고가<br>부족하여 지급되지 않을 수 있습니다.</strong><br>바로구매 하시겠습니까?";
			var templateStr = $.templates("#prestLayer");
			var result = templateStr.render({
				questStr: questStr,
				fnClick: "goDetailBuyNow(this, event);",
				addClass: "btn-close"
			});
			$("#prestLayer").text(result);
					layerpopTriggerBtn(obj, event);
		}
};

var makeBasketItems =function(overseaYn, periDeliYn) {
	var optionArea = $(".relation-option .option-list");
	var PROD_CD = prodBasicInfo.PROD_CD;
	var CATEGORY_ID = prodBasicInfo.CATEGORY_ID;
	var ONLINE_PROD_TYPE_CD = prodBasicInfo.ONLINE_PROD_TYPE_CD;
	var CTPD_DEAL_YN = prodBasicInfo.CTPD_DEAL_YN;
	var irRegularVariation="", mstProdCd=null,  ctpdItemYn=null, ctpdProdCd=null, ctpdItemCd=null, ordReqMsg=null;
	var ordReqMsg=null, hopeDeliDy=null;
	var basketItems=[];

	if( ONLINE_PROD_TYPE_CD == '02' || ONLINE_PROD_TYPE_CD == '03' ) {
		hopeDeliDy=($("#hopeDeliDy").val()).replace(/\./g, '').trim();	// 희망배송일
		ordReqMsg=$("#ordReqMsg").val();	// 구매요청사항

		if( hopeDeliDy == null || hopeDeliDy == "" ) {
			alert("상품 수령이나 설치를 위한 희망배송일을 등록해 주세요");
			return false;
		}

		var minDate = $( ".calendar" ).datepicker( "option", "minDate" );	// 배송시작 가능일
		var minDateM = (((minDate.getMonth()+1)+"").length < 2)? "0"+(minDate.getMonth()+1) : ""+(minDate.getMonth()+1);
		var minDateD = ((minDate.getDate()+"").length < 2)? "0"+minDate.getDate() : ""+minDate.getDate();
		var minDateYMD = minDate.getFullYear()+minDateM+minDateD;

		if( hopeDeliDy.length < 8) {
			alert("올바른 날짜형식이 아닙니다. 다시 입력하세요.");
			return false;
		}

		if( hopeDeliDy < minDateYMD ) {
			alert("희망배송일을 배송시작 가능일 이후로 선택해주세요. ");
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

		for(i=0; i<$("input:text[name=optnLoadQty]").length; i++) {
			optnLoadQty += Number($("input:text[name=optnLoadQty]:eq("+i+")").val());
		}

		if( optnLoadQty % setQty != 0 || optnLoadQty == 0 ) {
			leftQty = setQty - (optnLoadQty % setQty);
			alert("상품 "+setComma(leftQty)+"개를 더 선택해주세요.");
			return false;
		}

		var itemOrderCount	= optnLoadQty / setQty;
		var itemsProdCd		= PROD_CD;
		var itemsCode			= "001";

		for( var i=0; i < $(optionArea).find("li").length; i++) {
			var obj = $(optionArea).find("li:eq("+i+")");
			var delim = ($(optionArea).find("li").length == (i+1))?"":";";

			var optnLoadQty	= Number($(obj).find("input:text[name=optnLoadQty]").val());
			var optnLoadNm	= $(obj).find("input:hidden[name=optnLoadNm]").val();

			if( optnLoadQty > 0 )
				irRegularVariation += optnLoadNm+":"+optnLoadQty+delim;
		}

		basketItems.push({
			prodCd: itemsProdCd,					// 상품코드
			itemCd: itemsCode,						// 단품코드
			bsketQty: itemOrderCount,				// 주문수량
			categoryId: CATEGORY_ID,				// 카테고리ID
			nfomlVariation: irRegularVariation,		// 옵션명, 골라담기의 경우 옵션명:수량
			overseaYn: overseaYn,					// 해외배송여부
			prodCouponId: null,						// 즉시할인쿠폰ID
			oneCouponId: null,						// ONE 쿠폰ID
			cmsCouponId: null,						// CMS 쿠폰ID
			markCouponId: null,						// 마케팅제휴쿠폰ID
			periDeliYn: periDeliYn					// 정기배송여부
		});

	} else {

		if( $(optionArea).find("li").length > 0 ) {
			// 옵션상품
			var nfomlVariation="", delim="";
			for( var i=0; i < $(optionArea).find("li").length; i++) {
				var obj = $(optionArea).find("li:eq("+i+")");
				var nfomlVariationYN = 'N';
				var basketAddFlag = 'Y';

				if( !fnChkOptnQty($(obj).find(".set-btn .up")) ) {
					return false;
				}// 수량체크

				var itemOrderCount	= Number($(obj).find("input:text[name=OrderCount]").val());
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
						nfomlVariation: nfomlVariation,		// 옵션명, 골라담기의 경우 옵션명:수량
						overseaYn: overseaYn,				// 해외배송여부
						prodCouponId: null,					// 즉시할인쿠폰ID
						oneCouponId: null,					// ONE 쿠폰ID
						cmsCouponId: null,					// CMS 쿠폰ID
						markCouponId: null,					// 마케팅제휴쿠폰ID
						periDeliYn: periDeliYn,				// 정기배송여부
						mstProdCd: mstProdCd,				// 딜상품의 대표상품코드
						saveYn: null,						// 계속담기 여부
						ctpdItemYn: ctpdItemYn,				// 구성품 여부
						ctpdProdCd: ctpdProdCd,				// 구성품 원 상품코드
						ctpdItemCd: ctpdItemCd,				// 구성품 원 단품코드
						ordReqMsg: ordReqMsg,				// 구매요청사항
						hopeDeliDy: hopeDeliDy				// 희망배송일
					});
				}

				// 구성품
				if( CTPD_DEAL_YN == "Y" && $(obj).find(">div.select-item-relation").length > 0 ) {
					for( var j=0; j < $(obj).find(">div.select-item-relation").length; j++) {
						var objCtpd = $(obj).find(">div.select-item-relation:eq("+j+")");

						if( !fnChkOptnQty($(objCtpd).find(".set-btn .up")) ) {
							return false;
						}// 수량체크

						var ctpd_itemOrderCount	= Number($(objCtpd).find("input:text[name=OrderCount]").val());
						var ctpd_itemsCurrSalePrc	= Number($(objCtpd).find("input:hidden[name=itemsCurrSalePrc]").val());
						var ctpd_itemsProdCd		= $(objCtpd).find("input:hidden[name=itemsProdCd]").val();
						var ctpd_itemsCode			= $(objCtpd).find("input:hidden[name=itemsCode]").val();

						basketItems.push({
							prodCd: ctpd_itemsProdCd,			// 상품코드
							itemCd: ctpd_itemsCode,				// 단품코드
							bsketQty: ctpd_itemOrderCount,		// 주문수량
							nfomlVariation: nfomlVariation,		// 옵션명, 골라담기의 경우 옵션명:수량
							overseaYn: overseaYn,				// 해외배송여부
							periDeliYn: periDeliYn,				// 정기배송여부
							ctpdItemYn: 'Y',					// 구성품 여부
							ctpdProdCd: itemsProdCd,			// 구성품 원 상품코드
							ctpdItemCd: itemsCode				// 구성품 원 단품코드
						});
					}
				}
			}	// end for
		} else {
			//console.log("단품 담기");
			var optionArea = $(".relation-option .option-select");

			if( !fnChkOptnQty($(optionArea).find("input:text[name=OrderCount]")) ) {
				return false;
			}// 수량체크

			var itemOrderCount	= Number($(optionArea).find("input:text[name=OrderCount]").val());
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
				nfomlVariation: null,			// 옵션명, 골라담기의 경우 옵션명:수량
				overseaYn: overseaYn,			// 해외배송여부
				prodCouponId: null,				// 즉시할인쿠폰ID
				oneCouponId: null,				// ONE 쿠폰ID
				cmsCouponId: null,				// CMS 쿠폰ID
				markCouponId: null,				// 마케팅제휴쿠폰ID
				periDeliYn: periDeliYn,			// 정기배송여부
				ordReqMsg: ordReqMsg,			// 구매요청사항
				hopeDeliDy: hopeDeliDy			// 희망배송일
			});

		}
	}

	return basketItems;
}

function goPage(curPage){ // 상품평 페이지변경
	var reviewFilter = $("#reviewTab .active").attr("id").replace("searchFilter", "");
	getReviewListAjax(reviewFilter, curPage);
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

//문자열 replace 함수
function str_replace(str){
	var str_rtn = str.toString();
	for(i=0; i<str.length; i++){
		str_rtn = str_rtn.replace(',','');
	}
	str_rtn = Number(str_rtn);
	return str_rtn;
}

function downCouponDm(masterCouponID, dcCpbookCd) {
	var PROD_CD = prodBasicInfo.PROD_CD;
	var CATEGORY_ID = prodBasicInfo.CATEGORY_ID;

	if ( !global.isLogin(_LMAppUrl+"/product/ProductDetail.do?CategoryID="+CATEGORY_ID+"&ProductCD="+PROD_CD) ) return false;

	var params = {
			'masterCouponID' : masterCouponID,
			'dcCpbookCd' : dcCpbookCd,
			'newOrderCnt' : "0",
			'productCD' : PROD_CD
			};
	fn$ajax(_LMAppUrl+"/event/downCouponByCPBook.do", params, fnNmGetter().name, false);
}

function callBack_$downCouponDm(response) {
	var jsonData = eval( "(" + response + ")" );

	if ( jsonData.rtnMsg ){
		if(jsonData.rtnError == "1") {
			alert(jsonData.rtnMsg);
			return;
		} else {
					try {
						if (typeof(RNB) != "undefined" && typeof(RNB.coupon) != "undefined") {
							RNB.coupon.load();
						}
					}
					catch ( e ) {}
			alert(jsonData.rtnMsg);
		}
	}
}

// ID별 한정수량 노출
function getProdLimtQty() {
	var params = {
			ProductCD: prodBasicInfo.PROD_CD
	};

	$.getJSON("/product/ajax/prodLimtQtyAjax.do", params)
	.done(function(data) {
		if( typeof(data) != "undefined" && data.ProdLimtQty != null) {
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

//(체크박스 선택한) 쿠폰 다운로드
function selectedCouponDownFn() {

	var totalCouponCount = $('input:checkbox[name=each_coupon_check]').length;						//총 쿠폰수
	var availableCouponCount = $('input:checkbox[name=each_coupon_check]:not(:disabled)').length;	//이미 받은 쿠폰

	if (availableCouponCount == 0) {
			alert('모두 발급 받았습니다. 마이롯데 > 쿠폰에서 확인하세요.');
			return false;
	}

	if (totalCouponCount > 0 && $('input:checkbox[name=each_coupon_check]:not(:disabled):checked').length == 0) {
			alert("쿠폰을 선택하세요.");
			return false;
	}

	//체크한 항목의 쿠폰아이디 추출
	var repCouponIdArray = new Array();
	var dcCpbookCdArray = new Array();
	$('input:checkbox[name=each_coupon_check]:not(:disabled)').each(function(){
		if($(this).is(":checked")) {
			repCouponIdArray.push($(this).data("repcouponid"));
			dcCpbookCdArray.push($(this).data("dccpbookcd"));
		}
	});

	//쿠폰 다운로드 실행 호출
	couponDownFn(repCouponIdArray, dcCpbookCdArray);
}

//쿠폰 다운로드 실행
function couponDownFn(repCouponId, dcCpbookCd) {

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

	var flag = global.isLogin(_LMAppUrl+"/product/ProductDetail.do?ProductCD="+prodBasicInfo.PROD_CD+"&CategoryID="+prodBasicInfo.CATEGORY_ID);

	if(flag){
		$.ajax({
			type	 : "POST",
			url		 : "/api/coupon/multiIssued.do",
			data	 : {'repCouponIds' : str_repCouponId, 'dcCpbookCds' : str_dcCpbookCd},
			dataType : 'json',
			success  : function(data) {
				alert(data.rtnMsg);

				if (data.rtn == "success") {
					var couponIds = data.rtnData.split(",");		//data.rtnData 최종 등록된 대표쿠폰ID
					var len = couponIds.length;
					for(var i = 0; i < len; i++) {
						//다운로드 표시랑, 체크박스 disable 처리
						$("#each_coupon_check_" + couponIds[i]).closest('span').removeClass("active");
						$("#each_coupon_check_" + couponIds[i]).closest('span').addClass("disabled");
						$("#each_coupon_check_" + couponIds[i]).prop("disabled",true);
						$("#downloadTd_" + couponIds[i]).html("<span>다운완료</span>");
					}

					//다운받을 수 있는 쿠폰수 재설정
					var availableCouponCount = $('input:checkbox[name=each_coupon_check]:not(:disabled)').length;
					$("#availableCouponCount").text(availableCouponCount);

					if (availableCouponCount == 0) {
						$("#all_coupon_check").closest('span').addClass("disabled");
						$('#all_coupon_check').prop("disabled","disabled");
					}
				}
			},
			error: function(xhr, Status, error) {
			}
		});
	}
}

$(function() {
	productDetail.init();
});
