/**
 * 패키지 상품 정보를 구성
 */
var collectProductItems = function() {
	var totalCount = 0;
	var settlePlanAmt = 0;
	var totalAmt = 0;
	var totalDcAmt = 0
	var part = null;
	var selCnt = 0;
	var packageProductList = $("#packageProductList article");
	var obj, prodCd, prodNm, orderCount, itemPrice, itemDcPrice, price, dcPrice;

	$("#productList").empty();
	for (var i = 0; i < packageProductList.length ; i++ ) {
		obj = packageProductList[i];

		var check = $(obj).find("input[name=prodCd]");
		if (!check.prop("checked")) {
			continue;
		}
		prodCd = check.val();
		prodNm = check.siblings('input[name=prodNm]').val();
		itemPrice = parseInt(check.siblings('input[name=itemPrice]').val());
		itemDcPrice = parseInt(check.siblings('input[name=itemDcPrice]').val());
		orderCount = parseInt($(obj).find("input[name=bsketQty]").val());

		price = itemPrice * orderCount;
		dcPrice = (itemPrice - itemDcPrice) * orderCount;

		totalAmt += price;
		totalDcAmt += dcPrice;

		part = document.createElement("dl");
		$(part).append("<dt title=\"" + prodNm + "\">" + prodNm + "</dt>");
		$(part).append("<dd class='num'>" + orderCount + "개</dd>");
		$(part).append("<dd>" + utils.formatNumber((price - dcPrice)) + "원</dd>");
		selCnt++;
		$("#productList").append(part);
	}

	setTimeout(function() {
		totalCount = selCnt;
		settlePlanAmt = totalAmt - totalDcAmt;

		settlePlanAmt = Math.floor(settlePlanAmt/10)*10;

		$("#totalCount").html("(" + utils.formatNumber(totalCount) + "개 상품)");
		$("#settlePlanAmt").html(utils.formatNumber(settlePlanAmt));
		$("#totalAmt").html(utils.formatNumber(totalAmt));
		$("#totalDcAmt").html(utils.formatNumber(totalDcAmt));
	}, 100);

	//totalCount = packageProductList.length;
};

/**
 * 상품 수량 변경 적용
 */
var changeItemCount = function(prodCd) {
	collectProductItems();
};

/**
 * 선택된 옵션값 설정
 */
var setOptionCode = function(masterProdCd, obj){
	var itemCd = $('option:selected', obj).val();
	var itemNm = $('option:selected', obj).text();
	var mstInfo = $('#masterProd_'+masterProdCd);

	$('input[name=itemCd]', mstInfo).val(itemCd);
	$('.itemText', '#optionBtn_'+masterProdCd).html(itemNm);
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

var getRelProdList = function(current, categoryId, prodCd) {
	var re_prodCd = prodCd;

	if (!$("#relBtn" + prodCd).hasClass("on")) {
		$("#relBtn" + prodCd).addClass("on").text("상품 닫힘");
		//console.log($("#relProdList"+prodCd).html());
		_wac.isEmpty($("#relchk" + prodCd).val())
			&& $.utils.deferredAction(function() {
				var params = {categoryId: categoryId};
					
			
				$.getJSON("/mobile/delivery/api/relprod.do", params)
					.done(function(data) {
						var relProListHtml = "",
							k = 0,
							$frag = $(document.createDocumentFragment());

						if (data != null && data.list != undefined) {
							var productList = data.list;

							productList.forEach(function(v, i, a) {
								//if(productList[i].PROD_CD != prodCd){
								if (k % 2 == 0) {
									if (k != 0) {
										relProListHtml += "</div>";
									}
									relProListHtml += "<div class='wrap'>";
								}
								relProListHtml += "<article class='prod-replace-cont'>";
								relProListHtml += "		<div class='wrap-thumb'>";
								if (productList[i].SOUT_YN_REAL == 'Y') {
									relProListHtml += "			<span class='prod-sout'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_soldout_208x208.png' alt='품절'></span>";
								}
								if ('${_FrontSessionEntity.adultPccvYn}' != 'Y' && productList[i].ADL_YN == 'Y') {
									relProListHtml += "			<img src='" + _LMCdnV3RootUrl + "/images/layout/img_age19_80x80.jpg' alt='" + productList[i].PROD_NM + " 이미지' class='thumb'>";
								}
								relProListHtml += "			<img src='" + _LMCdnDynamicUrl + "/" + productList[i].MD_SRCMK_CD.substring(0, 5) + "/" + productList[i].MD_SRCMK_CD + "_1_80.jpg' alt='" + productList[i].PROD_NM + " 이미지' width='80' height='80' class='thumb' onerror='showNoImage(this,80,80);'>";
								relProListHtml += "		</div>";
								relProListHtml += "		<div class='wrap-info'>";
								relProListHtml += "			<p class='prod-name'><strong>" + productList[i].PROD_NM + "</strong></p>";
								relProListHtml += "			<p class='price-max'>";
								if (productList[i].CURR_SELL_PRC == productList[i].PROM_MAX_VAL) {
									relProListHtml += "판매가";
								} else {
									relProListHtml += "최저가";
								}
								relProListHtml += "			 : <span class='num-n'><em>" + numberWithCommas(productList[i].PROM_MAX_VAL) + "</em></span> 원</p>";
								if (productList[i].SOUT_YN_REAL == 'N') {
									//relProListHtml += "			<button type='button' class='btn-tbl btn-replace' onclick='javascript:setProduct(\""+prodCd+"\", \""+productList[i].PROD_NM+"\",\""+productList[i].PROD_CD+"\", \""+productList[i].ITEM_CD+"\",\""+productList[i].CATEGORY_ID+"\", \""+productList[i].MD_SRCMK_CD+"\", \""+productList[i].CURR_SELL_PRC+"\", \""+productList[i].PROM_MAX_VAL+"\", \""+productList[i].MAX_ORD_PSBT_QTY+"\", \""+productList[i].MIN_ORD_PSBT_QTY+"\", \""+productList[i].OPTION_YN+"\", \""+productList[i].OPTION_NM+"\", \""+productList[i].DELI_TYPE+"\", \""+productList[i].PROMO_PROD_ICON +"\", \"" + productList[i].PROM_MAX_VAL_LIST  +"\")'>대체담기</button>";
									var promoIconStr = productList[i].PROMO_PROD_ICON;
									var promoContents = productList[i].PROM_MAX_VAL_LIST;
									var promoMaxVal = productList[i].PROM_MAX_VAL;
									if (productList[i].OPTION_YN == "Y") {
										relProListHtml += "<button class='btn-tbl btn-replace' id='managePeri_replace_" + i + "_" + productList[i].PROD_CD + "' onclick='managePeriCart.add(this);' type='button' data-category-id='" + productList[i].CATEGORY_ID + "' data-prod-cd='" + productList[i].PROD_CD + "'  data-prod-type-cd='" + productList[i].ONLINE_PROD_TYPE_CD + "'  ";
										relProListHtml += "data-option-yn='" + productList[i].OPTION_YN + "' data-gubun='" + productList[i].GUBUN + "' data-item-cd='" + productList[i].ITEM_CD + "'  data-str-cd='" + productList[i].STR_CD + "' data-zip-seq='" + productList[i].RECP_ZIP_SEQ + "'  data-peri-deli-prod-seq='" + productList[i].PERI_DELI_PROD_SEQ + "' ";
										relProListHtml += "data-min-qty='" + productList[i].MIN_ORD_PSBT_QTY + "' data-max-qty='" + productList[i].MAX_ORD_PSBT_QTY + "' data-btn-obj-id='managePeri_replace_" + i + "_" + productList[i].PROD_CD + "' data-call-back-func='addPeriReplaceCallback' ";
										relProListHtml += "data-master-prod-cd='" + prodCd + "' data-prod-nm='" + productList[i].PROD_NM + "' data-md-srcmk-cd='" + productList[i].MD_SRCMK_CD + "' data-item-price='" + productList[i].CURR_SELL_PRC + "' ";
										relProListHtml += "data-item-dc-price='" + promoMaxVal + "' data-deli-type='" + productList[i].DELI_TYPE + "' data-promo-prod-icon='" + promoIconStr + "' data-promo-max-val-list='" + promoContents + "' data-reprod-cd='" + re_prodCd + "'>대체담기</button>";
									} else {
										relProListHtml += "<button type='button' class='btn-tbl btn-replace' onclick='javascript:setProduct(\"" + prodCd + "\", \"" + productList[i].PROD_NM + "\",\"" + productList[i].PROD_CD + "\", \"" + productList[i].ITEM_CD + "\",\"" + productList[i].CATEGORY_ID + "\", \"" + productList[i].MD_SRCMK_CD + "\", \"" + productList[i].CURR_SELL_PRC + "\", \"" + promoMaxVal + "\", \"" + productList[i].MAX_ORD_PSBT_QTY + "\", \"" + productList[i].MIN_ORD_PSBT_QTY + "\", \"" + productList[i].OPTION_YN + "\", \"" + productList[i].OPTION_NM + "\", \"" + productList[i].DELI_TYPE + "\", \"" + promoIconStr + "\", \"" + promoContents + "\", \"" + re_prodCd + "\")'>대체담기</button>";
									}
								}
								relProListHtml += "		</div>";
								relProListHtml += "	</article>";
								k++;
								//}
							});

							if (relProListHtml == "") {
								relProListHtml += "<div class='wrap-prod-replace not-prod-wrap'>";
								relProListHtml += "	<div class='prod-replace-list'>";
								relProListHtml += "		<p class='not-prod'>현재 대체할수 있는 정기배송 상품이 없습니다.</p>";
								relProListHtml += "	</div>";
								relProListHtml += "</div>";
							}
							relProListHtml += "		<input type='hidden' id='relchk" + prodCd + "' name='relchk' value='Y'/>";
							$frag.append(relProListHtml);
							$("#relProdList" + prodCd).append($frag);

							(k !== 0)
								&& $.utils.deferredAction(function() {
									var $obj = $("#relProdList" + prodCd),
										sliderSettings = {
											mode: 'fade',
											pagerType: 'short',
											speed: 0,
											infiniteLoop: false,
											hideControlOnEnd: true,
											onSliderLoad: function(activeIndex) {
												var slider = this;
												$.utils.deferredAction(function() {
													setTimeout(function() {
														activeChange($obj, activeIndex);
													}, 0);
													_wac.bx.setAccessibility(slider, sliderSettings.autoHover);
												});
											},
											onSlideBefore: function(activeEle, oldIndex, activeIndex) {
												setTimeout(function() {
													activeChange($obj, activeIndex);
												}, 0);
											}
										};
									var activeChange = function(ele, activeIndex) {
										var $ele = ele,
											$numWrap = $ele.closest('.bx-wrapper').find('.bx-pager'),
											activeNum = activeIndex + 1,
											totalNum = $ele.find('.wrap').length;
										$numWrap.html('<em class="num" title="현재 선택">' + activeNum + '</em> / ' + totalNum + '');
									};

									$obj.bxSlider(sliderSettings);
							});

							$("#relprod" + prodCd).removeClass("hidden");
						}
					});

				$('.wrap-prod-selectchange .prod-replace-list .btn-replace').on('click', function() {
					prodReplaceBtnEvent(this);
				});
			});

		!_wac.isEmpty($("#relchk" + prodCd).val()) &&
			$.utils.deferredAction(function() {
				$("#relBtn" + prodCd).addClass("on");
				$("#relprod" + prodCd).removeClass("hidden");
			});
	} else {
		$("#relBtn" + prodCd).removeClass("on").text("상품 펼침");
		$("#relprod" + prodCd).addClass("hidden");
	}
};

/**
 * 본상품/대체상품 설정
 */
var setProduct = function(masterProdCd, prodNm, prodCd, itemCd, categoryId, mdSrcmkCd, itemPrice, itemDcPrice, maxOrdPsbtQty, minOrdPsbtQty, optionYn, optionNm, deliType, promoProdIcon, promoMaxValList,re_prodCd ) {
	var mstInfo = $('#masterProd_'+masterProdCd);
	var popYn = "Y";
	$('input[name=prodNm]', mstInfo).val(prodNm);
	$('input[name=prodCd]', mstInfo).val(prodCd);
	$('input[name=prodCd]', mstInfo).attr("disabled", false);

	if($('input[name=prodCd]', mstInfo).attr("checked") == true){
		$('input[name=prodCd]', mstInfo).attr("checked", true);
		$('input[name=prodCd]', mstInfo).click();
	}

	$('input[name=itemCd]', mstInfo).val(itemCd);
	$('input[name=categoryId]', mstInfo).val(categoryId);
	$('input[name=itemPrice]', mstInfo).val(itemPrice);
	$('input[name=itemDcPrice]', mstInfo).val(itemDcPrice);

	//re_prodCd : 원래 있던 상품코드----대체상품 담기해도 기존에 보이던 품절이 계속 보이는 부분 수정
	$('#sold_img_'+re_prodCd).css("display", "none");
	//$('#masterProd_'+masterProdCd).removeClass().addClass("check-data check-data-type1");

	mdSrcmkCd = mdSrcmkCd || '';

	$('#img_'+masterProdCd).html("<img src='"+_LMCdnDynamicUrl+"/"+ mdSrcmkCd.toString().substring(0,5)+"/"+mdSrcmkCd+"_1_80.jpg' alt='"+prodNm+" 이미지' width='80' height='80' class='thumb' onclick='goProductPackageDetail(\""+categoryId+"\", \""+prodCd+"\", \""+popYn+"\");' onerror='showNoImage(this,80,80)'>");

	$('#prodNm_'+masterProdCd).html("<a href='javascript:goProductPackageDetail(\""+categoryId+"\", \""+prodCd+"\", \""+popYn+"\");'><strong>"+prodNm+"</strong></a>");
	if(itemPrice == itemDcPrice){
		$('#sellPrc_'+masterProdCd).html("<em>" + numberWithCommas(itemPrice) + "</em>");
		$('#sellPrcN_'+masterProdCd).show();
		$('#currSellPrcN_'+masterProdCd).hide();
	}else{
		$('#currSellPrc_'+masterProdCd).html("<em>" + numberWithCommas(itemDcPrice) + "</em>");
		$('#sellPrcN_'+masterProdCd).hide();

		if(promoMaxValList != 'null') {
			var promoList =  promoMaxValList.split('@');
			var promoListHtml = '';

			for(var i=0; i < promoList.length; i++) {

				if(promoList[i] != '') {

					var promoSubList = promoList[i].split('!');

					promoListHtml = promoListHtml + '<li>';

					for(var j=0; j < promoSubList.length; j++) {
						if(j==1) promoListHtml = promoListHtml + '<em class="txt-red flt-right">';
						promoListHtml = promoListHtml + promoSubList[j];
						if(j==1) promoListHtml = promoListHtml + '</em>';
					}

					promoListHtml = promoListHtml + '</li>';
				}
			}

			$('#dcContents_'+masterProdCd).empty().append(promoListHtml);
		}

		$('#currSellPrcN_'+masterProdCd).show();
	}
	$("#itemCount_"+masterProdCd).val("1");
	$('input[name=bsketQty]', mstInfo).val("1");
	if(optionYn == 'Y' && optionNm != 'null'){
		$('#optionBtn_'+masterProdCd).show();
	}else{
		$('#optionBtn_'+masterProdCd).hide();
		$('input[name=nfomlVariation]', mstInfo).val("");
	}

	setOptionLayer(masterProdCd, true);



	$("#itemCount_"+masterProdCd).attr("data-max-qty",maxOrdPsbtQty);
	$("#itemCount_"+masterProdCd).attr("data-min-qty",minOrdPsbtQty);
	$("#itemCount_"+masterProdCd).attr("data-option-yn",optionYn);
	$("#itemCount_"+masterProdCd).data().optYn = "N";
	$("#itemUp_"+masterProdCd).attr("MAX_ORD_PSBT_QTY",maxOrdPsbtQty);
	$("#itemDown_"+masterProdCd).attr("MIN_ORD_PSBT_QTY",minOrdPsbtQty);

	var deliveryHtml = "";
	if(deliType.substr(0,2) == "01"){
		deliveryHtml = "<i class='icon-band-delivery1'>매장배송</i>";
	}else if(deliType.substr(0,2) == "02"){
		deliveryHtml = "<i class='icon-band-delivery2'>매장택배</i>";
	}else if(deliType.substr(0,2) == "04"){
		deliveryHtml = "<i class='icon-band-delivery4'>업체택배</i>";
	}else if(deliType.substr(0,2) == "06"){
		deliveryHtml = "<i class='icon-band-delivery5'>명절배송</i>";
	}else if(deliType.substr(0,2) == "07"){
		deliveryHtml = "<i class='icon-band-delivery3'>스마트픽</i>";
	}

	var promoIconHtml = '';

	if(promoProdIcon!='null') {
		var prodIcons =  promoProdIcon.split('|');

		for(var i=0; i < prodIcons.length; i++) {

			if(prodIcons[i] != '') {
				var prodIcon = prodIcons[i].split(':');

				var orderNo = prodIcon[0];
				var iconType = prodIcon[1];
				var iconNm = prodIcon[2];
				var iconDesc = prodIcon[3];
				var iconExtra = prodIcon[4];

				if(typeof(iconExtra) == 'undefined') iconExtra = '';

				if(orderNo == '04') {
					promoIconHtml = promoIconHtml + '<i class="icon-tag-benefit-custom"><img src="' + _LMCdnV3RootUrl + '/images/front/common/flag-s-' + iconType + '_mbg.gif"></i>';
				} else {
					var className = '';
					if(iconExtra=='') {
						if(iconType.indexOf('type') > -1) {
							className = 'icon-band-' + iconType.replace('type','tag');
						} else {
							className = 'icon-band-' + iconType;
						}
					} else {
						className = 'icon-band-tag' + iconExtra;
					}

					promoIconHtml = promoIconHtml + '<i class="' + className + '">';

					if(iconType == 'won') {
						promoIconHtml = promoIconHtml + numberWithCommas(iconDesc) + '원 할인';
					}
					else if(iconType == 'discount') {
						promoIconHtml = promoIconHtml + iconDesc + '% 할인';
					}
					else if(iconType == 'bundle') {
						promoIconHtml = promoIconHtml + iconDesc
					}
					else {
						if(typeof(iconExtra) != "undefined" && iconExtra.indexOf('-1') > -1) {
							if(iconType == 'type6') promoIconHtml = promoIconHtml + '다둥이 ';
							promoIconHtml = promoIconHtml + iconDesc + '% 할인';
						}
						else if(typeof(iconExtra) != "undefined" && iconExtra.indexOf('-2') > -1) {
							if(iconType == 'type6') promoIconHtml = promoIconHtml + '다둥이 ';
							promoIconHtml = promoIconHtml + numberWithCommas(iconDesc) + '원 할인';
						} else {
							promoIconHtml = promoIconHtml + iconNm;
						}
					}

					promoIconHtml = promoIconHtml + '</i>';
				}
			}
		}
	}

	deliveryHtml = deliveryHtml + promoIconHtml;

	$('#prodBene_'+masterProdCd).html(deliveryHtml);

	//2017.01.20 이승남
//	if($('input[name=prodCd]', mstInfo).attr("checked") == true){
//		$('#masterProd_'+masterProdCd).addClass("active");
//	}

	collectProductItems();
};

function showDiscountDetail(obj, event, masterProdCd) {
	layerpopTriggerBtn(obj, event);
	$('#currDcContents_'+masterProdCd).empty().append($('#dcContents_'+masterProdCd).html());
}

// 상품상세 페이지 이동 함수
function goProductPackageDetail(cateId,prodCd,popupYn){
	var popYn=popupYn;
	var koost_Yn="N";
	var socialSeqVal="";
	var url = _LMAppUrl;
	if(popupYn==""){
		popYn="N";
	}


	//온라인몰 = 00001

		url = _LMAppUrl;

	if(prodCd=="" || prodCd==null){
		//alert("상품코드가 존재하지 않습니다.");
		alert( msg_product_error_noPro);
		if(popYn=="Y"){
			//self.close();
		}
		return;
	}

		socialSeqVal="";

	var dpCode = "";


	if(popYn=="Y"){
		opener.productDetailView(cateId,prodCd,popupYn);
		//opener.document.location.href = url+"/product/ProductDetail.do?CategoryID="+cateId+"&ProductCD="+prodCd+"&socialSeq="+socialSeqVal+"&koostYn="+koost_Yn + (dpCode != ""?"&dp=" + dpCode:"");
		//self.close();
	}else{
		document.location.href = url+"/product/ProductDetail.do?CategoryID="+cateId+"&ProductCD="+prodCd+"&socialSeq="+socialSeqVal+"&koostYn="+koost_Yn + (dpCode != ""?"&dp=" + dpCode:"");
	}
  }

var confirmOption = function(obj) {
	var params = jParam(obj);
	$(obj).parents('.dynamic').find('input[name=itemCd]').val(params.itemCd);
	$(obj).parents('.dynamic').find('input[name=bsketQty]').val(params.ordQty);
	$(obj).parents('.dynamic').find('input.ORD_QTY').val(params.ordQty);

	// 옵션변경에서 수량변경시 주문금액 자동설정 수정
	collectProductItems();
};

var ordQty = 0;
function qtyValChk(current, prodCd, val){

	var itemObj = $("#itemCount_" + prodCd);
	var maxQty = parseInt($(itemObj).attr("data-max-qty") || "999");
	var minQty = parseInt($(itemObj).attr("data-min-qty") || "1");
	ordQty = val;
	if(val >= maxQty){
		alert("상품의 최대구매수량은 " + maxQty + "개 입니다.");
		$("#itemCount_"+prodCd).val(maxQty);
		ordQty = maxQty;
		return;
	}

	if(val < minQty){
		alert("상품의 최소구매수량은 " + minQty + "개 이상 최대 "+maxQty+ "개 까지만 구매가 가능합니다.");
		$("#itemCount_"+prodCd).val(minQty);
		ordQty = minQty;
		return;
	}

	$(current).parents('.dynamic').find('input[name=bsketQty]').val(ordQty);
	$(current).parents('.dynamic').find('param.ORD_QTY').val(ordQty);

	collectProductItems();
}

//레시피 상품정보로 바로가기
function goDetailRcpPop(rcpno,nno,mno){
	opener.goDetailRcpPop(rcpno,nno,mno);
}

var package = (function() {
	var wireUpPopRecommendRecipeShiftTabHandler = function($popRecommendRecipe) {
		var popRecommendRecipeShiftTabHandler = function(e) {
			var $this = $(this),
				$target = $(e.target);
			
			var closeLayer = function() {
				$this.closest('.layerpop-recommend-recipe.layerpop-target.layer-bottom')
					.find('.btn-ico-close').click();
			};
			
			_wac.isShiftTabEvent(e)
				&& (parseInt(_wac.getFocusables($this).index($target)) === 0)
				&& $.utils.deferredAction(closeLayer).then(function() {
					_wac.onNextAsync(e, _wac.getFocusables($this).first());
				});
		};

		_wac.wireUpEventAsync($popRecommendRecipe, 'keydown', popRecommendRecipeShiftTabHandler, '.wrap');
	};

	var wireUpEvents = function() {
		var soutItem = "",
			$popRecommendRecipe = $('.pop-recommend-recipe');
		
		//옵션 버튼 클릭 - 공통과 구조가 달라 따로 설정
		$('.dynamic').on('click', 'button.optionView', function(){
			layerpopTriggerBtn( this, event );
			setOptionLayer(this);
		});

		
		$('.dynamic').on("click", 'button.up', function(){
			var basketQty = $(this).parent().siblings('input.ORD_QTY');

			if($(basketQty).data().optYn == "N"){

				var ordQty = parseInt(basketQty.val());
				var limit = parseInt($(this).attr('MAX_ORD_PSBT_QTY'));

				if(ordQty < limit) ordQty++;
				basketQty.val(ordQty);
				$(this).parents('.dynamic').find('input[name=bsketQty]').val(ordQty);
				$(this).parents('.dynamic').find('param.ORD_QTY').val(ordQty);

				// 수량변경시 주문금액 자동설정 수정
				collectProductItems();
				}else{
					periPackageOption($(basketQty).data().prodCd)
				}


		});

		$('.dynamic').on("click", 'button.down', function(){
			var basketQty = $(this).parent().siblings('input.ORD_QTY');

			if($(basketQty).data().optYn == "N"){
				var ordQty = parseInt(basketQty.val());
				var limit = parseInt($(this).attr('MIN_ORD_PSBT_QTY'));

				if(ordQty > limit) ordQty--;
				basketQty.val(ordQty);
				$(this).parents('.dynamic').find('input[name=bsketQty]').val(ordQty);
				$(this).parents('.dynamic').find('param.ORD_QTY').val(ordQty);

				// 수량변경시 주문금액 자동설정 수정
				collectProductItems();
			}else{
					periPackageOption($(basketQty).data().prodCd)
			}
		});

		$('.dynamic').on('change', 'select', function(){
			$(this).siblings('label').html($('option:selected', this).text());
		});

		// event: 전체선택 click
		$("#selectAllBt").click(function(e) {
			eventPropagationWrapper(e, function() {
				$(".packdetail-left").find("input[name='prodCd']").prop("checked", true).parent().addClass("active");
				collectProductItems();
			});
		});

		// event: 선택해제 click
		$("#unselectBt").click(function(e) {
			eventPropagationWrapper(e, function() {
				$(".packdetail-left").find("input[name='prodCd']").prop("checked", false).parent().removeClass("active");
				$(".packdetail-left").find("input[name='prodCd']").attr("checked", false);
				collectProductItems();
			});
		});

		// 개별 체크
		$("input[name='prodCd']").on('click', function(){
			if(this.checked) $(this).parent().addClass("active");
			else $(this).parent().removeClass("active");
			collectProductItems();
		});

		// event: 추천레시피 click
		$("#recommandRecipeBt").click(function(e) {
			layerpopTriggerBtn( this, event );
		});

			//장바구니 담기 버튼 click
		$("#addBasket").click(function(e) {
			soutItem = "";
			if(isLogin(location.href)){
				initTForm();

				if( _Member_yn == "false" && _GuestMember_yn =="true" && "Y" == "Y" ) {
						alert("L.POINT 통합회원 또는 롯데마트 회원만 정기배송 신청이 가능합니다. 회원 가입 후, 신청해 주세요");
						return;
				}

				// 2017.01.23 이승남
				var listLength = $('input[name=prodCd]').length;
				var checkLength = $('input[name=prodCd]:checked').length;
				var disLength = $('input[name=prodCd]:disabled').length;
				if(checkLength < 1){
					alert("장바구니에 등록할 상품이 없습니다.");
					return false;
				}

				var blnOption = false;

				$('input[name=prodCd]').each(function(){
					if(this.checked ){

						var optTxt = $(this).parent().parent().parent().find(".wrap-info").find("dl").find("dd").find(".opt-txt").text();
						var prodNm = $(this).parent().find("input[name=prodNm]").val();

						if($(this).parent().find("input[name=optionYn]").val() == "Y" && optTxt == ""){
							alert("         옵션을 선택해 주십시요.  \n  상품 : " + prodNm + " ");
							blnOption = true;
							return false;
						}

						$('#divTemp').append(
								$(this).parent().html()	//장바구니에 체크된 물품 추가
						);
					}

					// 2	017.02.17 이승남 _품절 상품 안내 창 추가
					if(parseInt(checkLength) == (parseInt(listLength) - parseInt(disLength))){
						if($(this).attr("disabled")){
							var soutItemNm = $(this).parent().find('input[name=prodNm]').val();
							if(!soutItem == ""){
								soutItem += " ," + soutItemNm;
							}else{
								soutItem += soutItemNm;
							}
						}
					}
				});

				if(blnOption) return false;

				if(soutItem != ""){
					alert(soutItem +" 상품은 품절로 장바구니에 담기지 않았습니다.");
				}
				// 2017.02.17 이승남 _품절 상품 안내 창 추가 끝
				fnAddPeriBasketItemPop();
			}
		});

		$('.btn-recommend-recipe.layerpop-trigger2')
			.on('click', function() {
				var activeChange = function(ele, activeIndex, activeTitle) {
					var $ele = ele,
						$numWrap = $ele.closest('.bx-wrapper').find('.bx-pager'),
						$pop = $('.layerpop-recommend-recipe'),
						activeNum = activeIndex + 1,
						totalNum = $ele.find('.wrap').length,
						tit = $pop.find('.wrap:eq(' + activeTitle + ')').attr('data-tit');
					
					$numWrap.html('<em class="num" title="현재 선택">'+activeNum+'</em> / '+totalNum+'');
					$pop.find('h1').text(tit);
				};

				var sliderSettings = {
					mode: 'fade',
					pagerType: 'short',
					speed: 0,
					infiniteLoop: false,
					hideControlOnEnd: true,
					onSliderLoad: function(activeIndex) {
						var slider = this;

						
						$.utils.deferredAction(function() {
							setTimeout(function() {
								activeChange($popRecommendRecipe, activeIndex, 0);
							}, 0);
							_wac.bx.setAccessibility(slider, sliderSettings.autoHover);
							!_wac.isEmpty($popRecommendRecipe)
								&& wireUpPopRecommendRecipeShiftTabHandler($popRecommendRecipe);
						});
					},
					onSlideBefore: function(activeEle, oldIndex, activeIndex) {
						setTimeout(function() {
							activeChange($popRecommendRecipe, activeIndex, activeIndex);
						}, 0);
					}
				};
				
				$popRecommendRecipe = $('.pop-recommend-recipe');
				$popRecommendRecipe.bxSlider(sliderSettings);
			});
		
	};

	

	var initAsync = function() {
		return $.utils.deferredAction(function() {
			window.resizeTo(1010, 965);
			collectProductItems();
			wireUpEvents();
		});
	};

	return {
		initAsync: initAsync,
	};
})();

$(function() {
	package.initAsync();	
});