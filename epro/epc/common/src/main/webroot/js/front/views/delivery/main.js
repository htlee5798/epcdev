"use strict";

var imageContentBase = _LMCdnDynamicUrl; //_LMCdnStaticUrl;	// TODO _LMCdnStaticUrl 로 교체

/**
 * 패키지 목록 조회
 */
var getPackageList = function(page, packageType) {
	if (typeof(page) === "undefined") {
		page = 1;
	};

	var params = {
		package_type: packageType || $("#packageTypeHeader a.active").attr("data-package-type"),
		page_no: page,
		page_size: 6
	};

	$.getJSON("/delivery/api/package/list.do", params)
		.done(function(data) {
			if (data != null) {
				var sectionEl = $("#packageContent").find("#tab" + params.package_type);
				sectionEl.empty();
				sectionEl.html("<ul class=\"delivery-listpack\"></ul><div class=\"wrap-paging-linetype\"></div>");

				var obj, liObj;
				for (var i in data.contents) {
					obj = data.contents[i];
					liObj = document.createElement("li");
					//				alert(obj.MAI_IMG1);
					$(liObj).html("<a href=\"javascript:void(0)\" onclick=\"openPackageDetail('" + obj.PERI_DELI_PKG_NO + "')\">" +
						"<img src=\"" + _LMCdnStaticUrl + obj.MAI_IMG1 + "\" alt=\"" + obj.PERI_DELI_PKG_NM + " 메인 이미지\" />" +
						"<span class=\"delivery-packlink\">" +
						"<img src=\"" + _LMCdnStaticUrl + obj.MAI_IMG2 + "\" alt=\"" + obj.PERI_DELI_PKG_NM + " 상세 이미지\" />" +
						"</span>" +
						"</a>");

					sectionEl.find("ul").append(liObj);
				};
				if (data.contents.length > 0) {
					$(".wrap-paging-linetype", sectionEl).html(paginator(page, data.totalElements, 6, "javascript:getPackageList(##)"));
				} else {
					$(".wrap-paging-linetype", sectionEl).html(paginator(1, 1, 6, "javascript:getPackageList(##)"));
				}
			}
		});
};

function gotoProdZoom(prodCd, categoryId, adlYn) {
	goProdZoom(categoryId, prodCd, '001', 'N', adlYn, '00001');
}

function prodDetailLink(obj, event) {
	var onclick = $(obj).closest('.product-article').find('.prod-name a').attr('onclick'); //onclick으로 가져올때
	var section = $(obj).find('section').length;
	// location 링크 goProductDetail 로 가져올수 있도록 해야함
	if (!$(event.target).attr('href')) {
		if (onclick != null && onclick.length > 0) {
			if (section == '0') {
				var onclickArr = onclick.split(';');
				eval(onclickArr[0]); // onclick
			}
		}
	}
}

/**
 * 정기배송 패키지 목록 페이지 이동
 */
var goPackagePage = function(page) {};

function numberWithCommas(x) {
	return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function setdcList(current, PROM_MAX_VAL_LIST) {
	layerpopTriggerBtn(current, event);

	var dcListHtml = "";

	var promMaxValList = "";
	if (PROM_MAX_VAL_LIST != null) {
		promMaxValList = PROM_MAX_VAL_LIST.split("@");
	}

	if (promMaxValList.length > 0) {
		for (var x = 0; x < promMaxValList.length; x++) {
			if (promMaxValList[x] != "") {
				dcListHtml += "					<li>";
				var promMaxValListArr = promMaxValList[x].split("!");
				for (var j = 0; j < promMaxValListArr.length; j++) 	{
					if (j == 1) {
						dcListHtml += "							<em class='txt-red flt-right'>";
					}
					dcListHtml += promMaxValListArr[j];

					if (j == 1) {
						dcListHtml += "</em>";
					}
				}
				dcListHtml += "</li>";
			}
		}
		$("#dcList").html(dcListHtml);
	}
}


// 정기배송 상품 목록 정보
var ProductListInfo = {
	category: null,
	page: 1,
	pageSize: 8,
	totalRecords: 0,
	checkedVal: "pb",
	totalPages: function() {
		return Math.ceil(this.totalRecords / this.pageSize);
	},
	order: "hot" // 인기순정렬 - 기본값
};

/**
 * 카테고리에 할당 된 상품 목록을 조회
 */
var getSmartRecommList = function() {
	var params = {};
	$.getJSON("/delivery/api/smart.do", params)
		.done(function(data) {
			var sliderSettings = {
				mode: 'fade',
				pager: true,
				speed: 0,
				onSliderLoad: function(activeIdx) {
					var slider = this,
						$this = $(this),
						$prev = _wac.getNextFocusable($this.closest(".delivery-smart"), "prev"),
						$next = _wac.getNextFocusable($this.closest('.wrap-in-tab2'), 'next'),

						//190827 웹접근성 : 원은재

						$bxWrapper = $this.closest('.bx-wrapper'),
						$bxViewport = $this.closest('.bx-viewport'),
						$bxControls = $bxWrapper.find('.bx-controls'),
						$bxPager = $bxWrapper.find('.bx-pager a'),
						$nowActiveEl, $nowActiveSlide;

						$bxWrapper.append($bxViewport);
						$bxControls.find('.bx-prev').text('정기배송 씨~ 스마트 추천 이전 보기')
						$bxControls.find('.bx-next').text('정기배송 씨~ 스마트 추천 다음 보기')

						var bxPagerKeydownHandler = function(e){
							$nowActiveEl = $(this);
							if(_wac.isEnterEvent(e)){
								_wac.markWacEnteredAsync($(this));
							}else if(_wac.isTabEvent(e)){
									if(_wac.isWacEntered($nowActiveEl)){
										_wac.preventDefaultAction(e);
										$nowActiveSlide = _wac.getFocusables($bxViewport.find('.prod-list[aria-hidden="false"]'));
										$nowActiveSlide.first().focus();

										_wac.wireUpEventAsync($nowActiveSlide.last(), 'keydown', slideContsKeydownHandler);

									}else if($nowActiveEl.is($bxPager.last())){
										_wac.preventDefaultAction(e);
										_wac.getFocusables($('#packageTypeHeader')).first().focus();
									}

							}
						}

						var slideContsKeydownHandler = function(e){
							_wac.preventDefaultAction(e);
							$bxPager.filter('.active').focus();
						}

						var bxPagerFocusHandler = function(e){
							_wac.removeWacEnteredAsync($(this));
						}

						$bxPager.on({
							keydown : bxPagerKeydownHandler,
							focusout : bxPagerFocusHandler
						})

						_wac.getFocusables($('#packageTypeHeader')).first().on('keydown', function(e){
							if(_wac.isShiftTabEvent(e) && $bxPager.length > 0 ){
								_wac.preventDefaultAction(e);
								$bxPager.last().focus();
							}
						})

//					_wac.bx.setAccessibility(
//						slider,
//						sliderSettings.autoHover,
//						$prev,
//						$next,
//						function() {
//							return _wac.getFocusables($this.closest('.bx-wrapper').find(".bx-pager"));
//						}
//					);





				},
			};

			// 상품 표시
			$("#smartList").empty();
			if (data != null && data.list != undefined) {
				var productList = data.list;
				var expressDeliStatus = data.expressDeliStatus;
				var smartProductHtml = "";
				for (var i = 0; i < productList.length; i++) {
					if (i % 4 == 0) {
						if (i != 0) {
							smartProductHtml += "</div>";
						}
						smartProductHtml += "<div class='prod-list'> ";
					}
					smartProductHtml += "<div class='product-article'>";
					smartProductHtml += "<div class='wrap-thumb'>";
					smartProductHtml += "<a href='javascript:;' class='thumb-link' onclick='goProductDetailView(\"" + productList[i].CATEGORY_ID + "\",\"" + productList[i].PROD_CD + "\", \"" + productList[i].CLICK_URL + "\"); return false;' data-category-id='"+ productList[i].CATEGORY_ID +"' data-prod-cd='"+ productList[i].PROD_CD +"'>";
					if (productList[i].SOUT_YN_REAL == "Y") {
						smartProductHtml += "<span class='prod-sout'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_soldout_208x208.png' alt='품절'></span>";
					}
					smartProductHtml += "		<img src='" + imageContentBase + "/" + productList[i].MD_SRCMK_CD.substring(0, 5) + "/" + productList[i].MD_SRCMK_CD + "_1_204.jpg' alt='" + productList[i].PROD_NM + "' onerror='javascript:showNoImage(this,204,204)' >";
					smartProductHtml += "</a>";
					smartProductHtml += "<span class='wrap-tag'>";

					var promoProdIcon = "";
					if (productList[i].PROMO_PROD_ICON != null) {
						promoProdIcon = productList[i].PROMO_PROD_ICON.split("|");
					}
					if (promoProdIcon.length > 0) {
						for (var k = 0; k < promoProdIcon.length; k++) {
							if (promoProdIcon[k] != "") {
								var prodIcon = promoProdIcon[k].split(":");
								var orderNo = prodIcon[0];
								var iconType = prodIcon[1]; //prodIcon[1]
								var iconNm = prodIcon[2];
								var iconDesc = prodIcon[3];
								var iconExtra = prodIcon[4]; //prodIcon[4]
								if (k < 1) {
									if (orderNo == "04") {
										smartProductHtml += "<i class='icon-promotion-custom'><img src='" + _LMCdnStaticUrl + "'/images/front/common/flag-s-" + iconType + ".gif' alt='개별관리형'></i>"

									} else if (iconType == "won") {
										smartProductHtml += "<i class='icon-goods-" + iconType + "'><em class='money'>" + numberWithCommas(iconDesc) + "</em><span class='blind'>원 할인</span></i>";
									} else if (iconType == "discount") {
										smartProductHtml += "<i class='icon-goods-" + iconType + "'><em class='number'>" + iconDesc + "</em><span class='blind'>% 할인</span></i>";
									} else if (iconType == "bundle") {
										smartProductHtml += "<i class='icon-goods-" + iconType + "'><em class='plus'>" + iconDesc + "</em></i>";
									} else {

										if (iconExtra.indexOf("-1") > 0) {
											smartProductHtml += "<i class='icon-goods-type" + iconExtra + "'><em class='number'>" + iconDesc + "</em><span class='blind'>% Lpoint할인</span></i>";
										} else if (iconExtra.indexOf("-2") > 0) {
											smartProductHtml += "<i class='icon-goods-type" + iconExtra + "'><em class='money'>" + numberWithCommas(iconDesc) + "</em><span class='blind'>원 Lpoint할인</span></i>";
										} else {
											smartProductHtml += "<i class='icon-goods-" + iconType + "'><em>" + iconNm + "</em></i>";
										}
									}
								}
							}
						}
					}
					smartProductHtml += "</span>";
					smartProductHtml += "<span class='wrap-band'>";
					if (expressDeliStatus != null && productList[i].EXPRESS_DELI_YN != null) {
						if (expressDeliStatus.isExpressDeliveryArea && productList[i].EXPRESS_DELI_YN == 'Y') {
							smartProductHtml += "<i class='icon-band-delivery30'>바로배송</i>";
						}
					} else if (productList[i].DELI_TYPE.substring(0, 2) == '01'  && productList[i].DELI_TYPE2 != '979') {
						smartProductHtml += "<i class='icon-band-delivery1'>매장배송</i>";
					} else if (productList[i].DELI_TYPE.substring(0, 2) == '02' && productList[i].DELI_TYPE2 != '979') {
						smartProductHtml += "		<i class='icon-band-delivery2'>매장택배</i>";
					} else if (productList[i].DELI_TYPE.substring(0, 2) == '04') {
						smartProductHtml += "		<i class='icon-band-delivery4'>업체택배</i>";
					} else if (productList[i].DELI_TYPE.substring(0, 2) == '06') {
						smartProductHtml += "		<i class='tag-delivery-type5'>명절배송</i>";
					} else if (productList[i].DELI_TYPE.substring(0, 2) == '07') {
						smartProductHtml += "		<i class='icon-band-delivery3'>스마트픽</i>";
					} else if (productList[i].ICON_10 == '1') {
						smartProductHtml += "		<i class='icon-band-delivery6'>무료배송</i>";
					}

					smartProductHtml += "</span>";
					smartProductHtml += "<span class='prod-link'>";
					var wishActive = "";
					if (productList[i].WISH_CNT > 0) {
						wishActive = "active";
					}
					smartProductHtml += "	<a href='javascript:;' class='dibs " + wishActive + "' data-button-name='btnAddWish' data-category-id='" + productList[i].CATEGORY_ID + "' data-product-code='" + productList[i].PROD_CD + "' title='위시리스트 담기'><span class='blind'>위시리스트</span></a>";
					smartProductHtml += "	<a href='javascript:;' class='blank' onclick='gotoProdZoom(\"" + productList[i].PROD_CD + "\",\"" + productList[i].CATEGORY_ID + "\",\"" + productList[i].ADL_YN + "\");return false;' target='_blank' title='새 창 열림'><span class='blind'>새창 보기</span></a>";
					smartProductHtml += "</span>";
					smartProductHtml += "</div>";
					smartProductHtml += "<div class='wrap-info'>";
					smartProductHtml += "<p class='prod-name'><strong><a href='javascript:;' onclick='goProductDetailView(\"" + productList[i].CATEGORY_ID + "\",\"" + productList[i].PROD_CD + "\", \"" + productList[i].CLICK_URL + "\"); return false;'>" + productList[i].PROD_NM + "</a></strong></p>";
					if (productList[i].CURR_SELL_PRC > productList[i].PROM_MAX_VAL) {
						smartProductHtml += "<p class='prod-price'>";
						smartProductHtml += "	판매가 <strong class='price-strike-type2'><span>" + numberWithCommas(productList[i].CURR_SELL_PRC) + "<i></i></span><span class='won'>원</span></strong>";
						smartProductHtml += "</p>";
					}
					smartProductHtml += "<p class='price-max'>";
					if (productList[i].CURR_SELL_PRC > productList[i].PROM_MAX_VAL) {
						smartProductHtml += "최저가";
					} else {
						smartProductHtml += "판매가";
					}
					smartProductHtml += " <span class='num-n'><em>" + numberWithCommas(productList[i].PROM_MAX_VAL) + "</em></span>원";
					if (productList[i].CURR_SELL_PRC > productList[i].PROM_MAX_VAL) {
						smartProductHtml += "<button type='button' class='layerpop-trigger2' data-trigger='layerpop-maxbenefit' onclick='setdcList(this, \"" + productList[i].PROM_MAX_VAL_LIST + "\");' title='레이어 팝업 열림'>최저가 내역</button>";
					}
					smartProductHtml += "</p>";
					smartProductHtml += "<div class='wrap-spinner'>";

					//sold out 경우 상단
					var isManufacturingProduct = productList[i].ONLINE_PROD_TYPE_CD == '02' || productList[i].ONLINE_PROD_TYPE_CD == '03'
					  , isSoldOut = productList[i].SOUT_YN_REAL == 'Y';

					smartProductHtml += "<button type='button' class='btn-type4' data-method='basket' data-is-sold-out='" + isSoldOut + "' data-is-manufacturing-product='" + isManufacturingProduct + "' data-prod-cd='"+ productList[i].PROD_CD +"' data-option-yn='"+ productList[i].OPTION_YN +"' data-prod-type-cd='"+ productList[i].ONLINE_PROD_TYPE_CD +"' data-category-id='"+ productList[i].CATEGORY_ID +"' data-period-delivery-yn='Y' title='레이어 팝업 열림'>정기배송 담기</button>";
					smartProductHtml += "</div>";
					smartProductHtml += "</div>";
					smartProductHtml += "</div>";
				}
				$("#smartList").html(smartProductHtml);
			}
			$("#smartList").bxSlider(sliderSettings);
		});
};

/**
 * 카테고리에 할당 된 상품 목록을 조회
 */
//categoryId, order, page
var getProductListByCategory = function(categoryId, order, page) {
	if (!order) order = ProductListInfo.checkedVal || $('input:radio[name=productListOrder]:checked').val();
	if (!page) page = 1 || ProductListInfo.page;

	var params = {
		CategoryID: categoryId,
		kindView: order,
		currentPage: page,
		rowPerPage: 8
	};

	if(typeof rowPerPage != 'undefined'){
		params.rowPerPage = rowPerPage;
	};

	//페이지 뒤로가기에도 정렬조건과 페이지를 유지할 수 있도록 input:hidden 에 저장
	$('#paginationCategoryId').val(categoryId);
	$('#byCategoryOrder').val(order);
	$('#byCategoryCurrentPage').val(page);

	$.getJSON("/delivery/api/product/list.do", params)
		.done(function(data) {
			// 상품 표시
			$("#productList").empty();

			if (data != null && data.PRODUCTLIST != undefined) {
				var html = "";
				var productList = data["PRODUCTLIST"];
				var expressDeliStatus = data["expressDeliStatus"];
				var contentsInfo = data["PRODUCTLISTPAGE"];


				ProductListInfo.totalRecords = contentsInfo["TOTALCOUNT"];

				var productListHtml = "";
				var productListCnt = 0;

				for (var i = 0; i < productList.length; i++) {
					if (productList[i].PERI_DELI_YN == 'Y') {
						productListCnt++;
						productListHtml += "<div class='product-article'>";
						productListHtml += "<div class='wrap-thumb'>";
						productListHtml += "<a href='javascript:;' class='thumb-link' onclick='goProductDetailView(\"" + productList[i].CATEGORY_ID + "\",\"" + productList[i].PROD_CD + "\"); return false;' data-category-id='"+ productList[i].CATEGORY_ID +"' data-prod-cd='"+ productList[i].PROD_CD +"'>";
						if (productList[i].SOUT_YN_REAL == "Y") {
							productListHtml += "<span class='prod-sout'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_soldout_208x208.png' alt='품절'></span>";
						}
						productListHtml += "		<img src='" + imageContentBase + "/" + productList[i].MD_SRCMK_CD.substring(0, 5) + "/" + productList[i].MD_SRCMK_CD + "_1_204.jpg' alt='" + productList[i].PROD_NM + "' onerror='javascript:showNoImage(this,204,204)' alt='' class='thumb'>";
						productListHtml += "</a>";
						productListHtml += "<span class='wrap-tag'>";
						var promoProdIcon = "";
						if (productList[i].PROMO_PROD_ICON != null) {
							promoProdIcon = productList[i].PROMO_PROD_ICON.split("|");
						}

						if (promoProdIcon.length > 0) {
							for (var k = 0; k < promoProdIcon.length; k++) {
								if (promoProdIcon[k] != "") {
									var prodIcon = promoProdIcon[k].split(":");
									var orderNo = prodIcon[0];
									var iconType = prodIcon[1];
									var iconNm = prodIcon[2];
									var iconDesc = prodIcon[3];
									var iconExtra = prodIcon[4]; //prodIcon[4]
									if (k < 1) {
										if (orderNo == "04") {
											productListHtml += "<i class='icon-promotion-custom'><img src='" + _LMCdnStaticUrl + "'/images/front/common/flag-s-" + iconType + ".gif' alt='개별관리형'></i>"
										} else if (iconType == "won") {
											productListHtml += "<i class='icon-goods-" + iconType + "'><em class='money'>" + numberWithCommas(iconDesc) + "</em><span class='blind'>원 할인</span></i>";
										} else if (iconType == "discount") {
											productListHtml += "<i class='icon-goods-" + iconType + "'><em class='number'>" + iconDesc + "</em><span class='blind'>% 할인</span></i>";
										} else if (iconType == "bundle") {
											productListHtml += "<i class='icon-goods-" + iconType + "'><em class='plus'>" + iconDesc + "</em></i>";
										} else {

											if (iconExtra.indexOf("-1") > 0) {
												productListHtml += "<i class='icon-goods-type" + iconExtra + "'><em class='number'>" + iconDesc + "</em><span class='blind'>% Lpoint할인</span></i>";
											} else if (iconExtra.indexOf("-2") > 0) {
												productListHtml += "<i class='icon-goods-type" + iconExtra + "'><em class='money'>" + numberWithCommas(iconDesc) + "</em><span class='blind'>원 Lpoint할인</span></i>";
											} else {
												productListHtml += "<i class='icon-goods-" + iconType + "'><em>" + iconNm + "</em></i>";
											}
										}

									}
								}
							}
						}
						productListHtml += "</span>";
						productListHtml += "<span class='wrap-band'>";
						if (expressDeliStatus != null && productList[i].EXPRESS_DELI_YN != null) {
							if (expressDeliStatus.isExpressDeliveryArea && productList[i].EXPRESS_DELI_YN == 'Y') {
								productListHtml += "<i class='icon-band-delivery30'>바로배송</i>";
							} 
						} else if (productList[i].DELI_TYPE.substring(0, 2) == '01' && productList[i].DELI_TYPE2 != '979') {
							productListHtml += "<i class='icon-band-delivery1'>매장배송</i>";
						} else if (productList[i].DELI_TYPE.substring(0, 2) == '02' && productList[i].DELI_TYPE2 != '979') {
							productListHtml += "		<i class='icon-band-delivery2'>매장택배</i>";
						} else if (productList[i].DELI_TYPE.substring(0, 2) == '04') {
							productListHtml += "		<i class='icon-band-delivery4'>업체택배</i>";
						} else if (productList[i].DELI_TYPE.substring(0, 2) == '06') {
							productListHtml += "		<i class='tag-delivery-type5'>명절배송</i>";
						} else if (productList[i].DELI_TYPE.substring(0, 2) == '07') {
							productListHtml += "		<i class='icon-band-delivery3'>스마트픽</i>";
						} else if (productList[i].ICON_10 == '1') {
							productListHtml += "		<i class=icon-band-delivery6'>무료배송</i>";
						}

						productListHtml += "</span>";
						productListHtml += "<span class='prod-link'>";
						var wishActive = "";
						if (productList[i].WISH_CNT > 0) {
							wishActive = "active";
						}
						productListHtml += "	<a href='javascript:;' class='dibs " + wishActive + "' data-button-name='btnAddWish' data-category-id='" + productList[i].CATEGORY_ID + "' data-product-code='" + productList[i].PROD_CD + "' title='위시리스트 담기'><span class='blind'>위시리스트</span></a>";
						productListHtml += "	<a href='javascript:;' onclick='gotoProdZoom(\"" + productList[i].PROD_CD + "\",\"" + productList[i].CATEGORY_ID + "\",\"" + productList[i].ADL_YN + "\");return false;' class='blank' target='_blank' title='새 창 열림'><span class='blind'>새창 보기</span></a>";
						productListHtml += "</span>";
						productListHtml += "</div>";
						productListHtml += "<div class='wrap-info'>";
						productListHtml += "<p class='prod-name'><strong><a href='javascript:;' onclick='goProductDetailView(\"" + productList[i].CATEGORY_ID + "\",\"" + productList[i].PROD_CD + "\"); return false;'>" + productList[i].PROD_NM + "</a></strong></p>";
						if (productList[i].CURR_SELL_PRC > productList[i].PROM_MAX_VAL) {
							productListHtml += "<p class='prod-price'>";
							productListHtml += "	판매가 <strong class='price-strike-type2'><span>" + numberWithCommas(productList[i].CURR_SELL_PRC) + "<i></i></span><span class='won'>원</span></strong>";
							productListHtml += "</p>";
						}
						productListHtml += "<p class='price-max'>";
						if (productList[i].CURR_SELL_PRC > productList[i].PROM_MAX_VAL) {
							productListHtml += "최저가";
						} else {
							productListHtml += "판매가";
						}
						productListHtml += " <span class='num-n'><em>" + numberWithCommas(productList[i].PROM_MAX_VAL) + "</em></span>원";
						if (productList[i].CURR_SELL_PRC > productList[i].PROM_MAX_VAL) {
							productListHtml += "<button type='button' class='layerpop-trigger2' data-trigger='layerpop-maxbenefit' onclick='setdcList(this, \"" + productList[i].PROM_MAX_VAL_LIST + "\");' title='레이어 팝업 열림'>최저가 내역</button>";
						}

						productListHtml += "</p>";
						productListHtml += "<div class='wrap-spinner'>";

						var isManufacturingProduct = (productList[i].ONLINE_PROD_TYPE_CD == '02' || productList[i].ONLINE_PROD_TYPE_CD == '03')
						  , isSoldOut = productList[i].SOUT_YN_REAL == 'Y';

						productListHtml += "<button type='button' class='btn-type4' data-method='basket' data-is-sold-out='" + isSoldOut + "' data-is-manufacturing-product='" + isManufacturingProduct + "' data-prod-cd='"+ productList[i].PROD_CD +"' data-option-yn='"+ productList[i].OPTION_YN +"' data-prod-type-cd='"+ productList[i].ONLINE_PROD_TYPE_CD +"' data-category-id='"+ productList[i].CATEGORY_ID +"' data-period-delivery-yn='Y' title='레이어 팝업 열림'>정기배송 담기</button>";
						productListHtml += "</div>";
						productListHtml += "</div>";
						productListHtml += "</div>";
					}
				}

				$("#productList").append(productListHtml);

				// 페이징 처리
				ProductListInfo.totalRecords = data.PRODUCTLISTPAGE.TOTALCOUNT;
				$("#productPaginate").html(paginator(page, ProductListInfo.totalRecords, data.PRODUCTLISTPAGE.ROWPERPAGE, "javascript:goProductPage(##)"));
			} else {
				$("#productPaginate").html(paginator(1, 1, data.PRODUCTLISTPAGE.ROWPERPAGE, "javascript:goProductPage(##)"));
			};

			$('dd[id^=ddcat').removeClass("active");
			$('#ddcat' + categoryId).attr("class", "active");

			// 조회조건 값 설정
			ProductListInfo.category = categoryId;
			ProductListInfo.order = order;
			ProductListInfo.page = page;
		});
}

/**
 * 상품 리스트 페이지 이동
 */
var goProductPage = function(page) {
	getProductListByCategory(ProductListInfo.category, ProductListInfo.order, page);
	//페이징 선택후 포커스
	var $prodListWrap = $('.dynamic'),
		$prodFirstObj = $prodListWrap.find('.thumb-link:first');
	$prodFirstObj.focus();
};

/**
 * 정기배송 패키지 상세 열기
 */
var openPackageDetail = function(periDeliPkgNo) {
	window.open("/delivery/popup/package.do?peri_deli_pkg_no=" + periDeliPkgNo, "DeliveryPackageDetailPop", "width=1050px, height=965px, scrollbars=yes");
};

//정기배송 장바구니 담기
var addDeliveryBasket = function(categoryId, prodCd, itemCd, soutYn, optionYn, i) {
	if (isLogin("/delivery/main.do")) {

		if (soutYn == 'Y') {
			alert("이 상품은 품절입니다.");
			return;
		}

		if (optionYn == 'Y') {
			alert("해당 상품은 상품 상세에서 장바구니에 담으실 수 있습니다.");
			goProductDetailView(categoryId, prodCd);
		} else {

			if( _Member_yn == "false" && _GuestMember_yn =="true" && "Y"  == "Y" ) {
				alert("L.POINT 통합회원 또는 롯데마트 회원만 정기배송 신청이 가능합니다. 회원 가입 후, 신청해 주세요");
				return;
			}

			initTForm();

			$('#divTemp').append(genDomInput("prodCd", prodCd));
			$('#divTemp').append(genDomInput("itemCd", itemCd));
			$('#divTemp').append(genDomInput("bsketQty", 1));
			$('#divTemp').append(genDomInput("categoryId", categoryId));
			$('#divTemp').append(genDomInput("overseaYn", "N")); //해외배송유무
			$('#divTemp').append(genDomInput("periDeliYn", "Y")); //정기배송유무
			$('#divTemp').append(genDomInput("nfomlVariation", "")); //옵션
			$('#divTemp').append(genDomInput("prodCouponId", ""));
			$('#divTemp').append(genDomInput("oneCouponId", ""));
			$('#divTemp').append(genDomInput("cmsCouponId", ""));

			fnAddBasketItemPop("peri");
		}
	}
}

//레시피 상품정보로 바로가기
function goDetailRcpPop(rcpno, nno, mno) {
	var url = _LMAppUrl;
	//var newWindow = window.open("about:blank");
	if (rcpno == "") {
		alert("레시피정보가 없습니다.");
		window.open(url + "/recipe/expert.do");
	} else if (nno == "") {
		if (mno == "") {
			window.open(url + "/recipe/recipeDetail.do?NNO=1&MNO=00001&rcpNo=" + rcpno)
		} else {
			window.open(url + "/recipe/recipeDetail.do?NNO=1&MNO=" + mno + "&rcpNo=" + rcpno);
		}
	} else if (mno == "") {
		if (nno == "") {
			window.open(url + "/recipe/recipeDetail.do?NNO=1&MNO=00001&rcpNo=" + rcpno);
		} else {
			window.open(url + "/recipe/recipeDetail.do?NNO=" + nno + "&MNO=0001&rcpNo=" + rcpno);
		}
	} else {
		window.open(url + "/recipe/recipeDetail.do?NNO=" + nno + "&MNO=" + mno + "&rcpNo=" + rcpno);
	}
}

function productDetailView(cateId, prodCd, popupYn) {
	var popYn = popupYn;
	var koost_Yn = "N";
	var socialSeqVal = "";
	var url = _LMAppUrl;
	var dpCode = "";
	if (popYn == "Y") {
		//var newWindow = window.open("about:blank");
		//newWindow.location.href = url + "/product/ProductDetail.do?CategoryID=" + cateId + "&ProductCD=" + prodCd + "&socialSeq=" + socialSeqVal + "&koostYn=" + koost_Yn + (dpCode != "" ? "&dp=" + dpCode : "");
		window.open(url + "/product/ProductDetail.do?CategoryID=" + cateId + "&ProductCD=" + prodCd + "&socialSeq=" + socialSeqVal + "&koostYn=" + koost_Yn + (dpCode != "" ? "&dp=" + dpCode : ""));
	} else {
		document.location.href = url + "/product/ProductDetail.do?CategoryID=" + cateId + "&ProductCD=" + prodCd + "&socialSeq=" + socialSeqVal + "&koostYn=" + koost_Yn + (dpCode != "" ? "&dp=" + dpCode : "");
	}


}

var productDetail = function(obj) {
	var param = jParam(obj);
	goProdZoom(param.categoryId, param.prodCd, '001', 'N', param.adlYn, '00001');
}

var deliveryScheduleLink = function(periDeliId) {
	$('form#managePeri').get(0).submit();
}

function userguide_link() {
	window.open("/delivery/popup/user_guide.do", "DeliveryUserGuidePopup", "width=920px, height=770px");
}

function faq_link() {
	location.href = "/happycenter/faqMain.do";
}

$(function() {
	var shareElement = $("#container .detail-share");
	// Kakao
	$("a.kakao", shareElement).click(function(e) {
		eventPropagationWrapper(e, function() {
			socialLink.kakaoStory(location.href, "롯데마트몰 정기배송서비스를 추천합니다. \n\n'장보는 편리함과 합리적인 구매까지!!' \n롯데마트몰 정기배송 \nlottemart.com");
		});
	});

	// Facebook
	$("a.facebook", shareElement).click(function(e) {
		eventPropagationWrapper(e, function() {
			socialLink.facebook(location.href);
		});
	});

	// Twitter
	$("a.twitter", shareElement).click(function(e) {
		eventPropagationWrapper(e, function() {
			socialLink.twitter(location.href, "[롯데마트몰]장보는 편리함과 합리적인 구매까지!! \n '정기배송' \nlottemart.com");
		});
	});

	// Link Copy
	$("a.link-copy", shareElement).click(function(e) {
		eventPropagationWrapper(e, function() {
			clipboard.copy(location.href).then(
				function() {
					alert(view_messages.copylink.success);
				},
				function() {
					alert(view_messages.copylink.fail);
				}
			);
		});
	});

	// 상품목록 정렬
	/*
	$("input[name='productListOrder']").click(function(e) {
		var obj = this;
		eventPropagationWrapper(e, function() {
			if ($(obj).val() != ProductListInfo.order) {
				$(obj).parent().siblings().removeClass('active');
				$(obj).parent().addClass('active');
				getProductListByCategory(ProductListInfo.category, $(obj).val(), 1);
			}
		});
	});
	*/

	// 패키지 탭 클릭
	$("nav#packageTypeHeader > a").click(function(e) {
		var $t = $(this);

		eventPropagationWrapper(e, function() {
			getPackageList(1, $t.data('packageType'));
		});
	});

	$(document).on('click', 'a.wishBtn', function() {
		gotoWish(this);
	});

	//뒤로가기로 진입했을 경우 이전 값을 유지하도록 input:hidden 에 셋팅된 값을 우선적으로 사용한다.
	var paginationCategoryId = $('#paginationCategoryId').val() || $("#deliveryCategory > dl:eq(0) > dd:eq(0)").attr("data-category-id")
		, byCategoryCurrentPage = $('#byCategoryCurrentPage').val() || 1
		, byCategoryOrder = $('#byCategoryOrder').val() || 'pb'; //pb우선노출 적용 개선 'hot'--> 'pb' 변경 처리 by 20180816 hyena

	var orderWrapper = $('div.check-inresult')
		, currentOrder = orderWrapper.find('input:radio[name=productListOrder][value=' + byCategoryOrder + ']');

	//뒤로가기로 진입했을 경우 정렬조건 유지
	currentOrder.prop('checked', true).parent().addClass('active').siblings().removeClass('active');

	getPackageList(1);
	getSmartRecommList();
	// 첫번째 카테고리 상품 목록 조회
	getProductListByCategory(paginationCategoryId, byCategoryOrder, byCategoryCurrentPage);

});