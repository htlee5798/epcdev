//js template register
//ex) $.templates( [templatename], [templatecode] )

$.templates( 'basketOptionForDefault' ,
'<section class="{{:~className(layerClass)}} basket-option">' +
	'<div class="container">' +
		'<h1 class="tit-h">{{:title}}</h1>' +
		'<div class="contents">'+
			'<form>' +
				'<div class="opt-wrap">' +
					'<div class="optselect hidden" id="itemField"></div>'+
					'<div class="optselect hidden" id="optionField"></div>'+
					'<div class="optselect hidden" id="subOptionField"></div>'+
					'<div class="optselect">' +
						'<dl>' +
							'<dt>수량</dt>'+
							'<dd class="select-type1">'+
								'{{include tmpl="quantity" /}}' +
							'</dd>'+
						'</dl>'+
					'</div>' +
				'</div>'+
				'<div class="set-btn">' +
					'<input type="submit" class="btn-form-type1 layerpop-complete" value="확인">' +
				'</div>'+
			'</form>' +
		'</div>'+
	'</div>' +
	'<button type="button" class="btn-ico-close" id="btnClose" title="레이어팝업 닫기"><i>옵션 레이어팝업 닫기</i></button>'+
'</section>');

$.templates( 'basketOptionForPick',
'<section class="{{:~className(layerClass)}} basket-option pick">' +
	'<div class="container">' +
		'<h1 class="tit-h">{{:title}}</h1>' +
		'<div class="contents">'+
			'<div class="optselect">' +
				'<div class="goodsadded">' +
					'<p>상품을 {{:unit}}개 단위로 선택해주세요.</p>' +
				'</div>'+
			'</div>' +
			'<form>' +
				'<div class="opt-wrap">' +
					'<div class="optselect">' +
						'{{for options}}' +
						'<dl>' +
							'<dt>[골라담기]{{:name}}</dt>'+
							'<dd class="select-type1">'+
								'{{include tmpl="quantityByPick" /}}' +
							'</dd>'+
						'</dl>'+
						'{{/for}}' +
					'</div>' +
				'</div>'+
				'<div class="set-btn">' +
					'<input type="submit" class="btn-form-type1 layerpop-complete" value="확인" >' +
				'</div>'+
			'</form>' +
		'</div>'+
	'</div>' +
	'<button type="button" class="btn-ico-close" id="btnClose"><i>팝업 닫기</i></button>'+
'</section>');

$.templates( 'mobileBasketOptionForDefault',
	'<div class="mask"></div>' +
	'<div class="{{:~className(layerClass)}}">' +
		'<form>' +
			'<div class="option-select">' +
				'<p class="title">옵션 선택</p>' +
				'<div class="select hidden" id="itemField"></div>'+
				'<div class="select hidden" id="optionField"></div>'+
				'<div class="select hidden" id="subOptionField"></div>'+
				'<div class="option-select">' +
					'<p class="title">수량 선택</p>' +
					'{{include tmpl="mobileQuantity" /}}' +
				'</div>' +
			'</div>' +
			'<div class="wrap-btn">' +
				'<button type="button" class="btn-common-color4 close">취소</button>'+
				'<input type="submit" class="btn-common-color1" value="확인" />' +
			'</div>' +
		'</form>' +
	'</div>');

$.templates( 'mobileBasketOptionForPick',
	'<div class="mask"></div>' +
	'<div class="{{:~className(layerClass)}}">' +
		'<form>' +
			'<div class="option-select">' +
				'<p class="title">골라담기</p>' +
				'<p class="desc">상품을 {{:unit}}개 단위로 선택해주세요.</p>'+
				'<p class="error hidden"></p>' +
			'</div>'+
			'<div class="option-select">' +
				'{{for options}}' +
				'<div class="goodsadded {{if #index !== 0}}subadded{{/if}} lie-table">' +
					'<div class="left">{{:name}}</div>' +
					'{{include tmpl="mobileQuantityByPick"/}}' +
				'</div>' +
				'{{/for}}' +
				'<p class="goodsadded result">' +
					'점포 재고상황에 따라 상품구성이 상이할 수 있습니다.<br/>많은 양해 부탁드립니다.'+
				'</p>' +
			'</div>' +
			'<div class="wrap-btn">' +
				'<button type="button" class="btn-common-color4 close">취소</button>'+
				'<input type="submit" class="btn-common-color1" value="확인" />' +
			'</div>' +
		'</form>' +
	'</div>');

$.templates( 'quantity', '<div class="set-spinner-type2-large wrapper-quantity">'+
		'<input type="text" name="quantity" class="quantity" title="수량" value="1" style="ime-mode:disabled" maxlength="15" data-min-quantity="1" data-max-quantity="1" disabled>'+
		'<button type="button" class="sp-minus" disabled><i class="ico-minus">수량 감소</i></button>' +
		'<button type="button" class="sp-plus" disabled><i class="ico-plus">수량 증가</i></button>' +
'</div>');

$.templates( 'quantityByPick','<div class="set-spinner-type2-large wrapper-quantity">'+
		'<input type="text" name="{{:value}}" class="quantity" title="수량" value="0" style="ime-mode:disabled" maxlength="15" data-min-quantity="0" data-max-quantity="{{:maxQuantity}}">'+
		'<button type="button" class="sp-minus"><i class="ico-minus">수량 감소</i></button>' +
		'<button type="button" class="sp-plus"><i class="ico-plus">수량 증가</i></button>' +
'</div>');

$.templates( 'mobileQuantity' , '<div class="spinner">' +
		'<button type="button" class="minus" disabled>'+
			'<i class="icon-sum-minus">감소</i>' +
		'</button>' +
		'<input type="tel" name="quantity" value="1" maxlength="15" maxlength="15" data-min-quantity="1" data-max-quantity="1" disabled>' +
		'<button type="button" class="plus" disabled>'+
			'<i class="icon-sum-plus">추가</i>' +
		'</button>' +
'</div>');

$.templates( 'mobileQuantityByPick', '<div class="spinner right">' +
		'<button type="button" class="minus">'+
			'<i class="icon-sum-minus">감소</i>' +
		'</button>' +
		'<input type="tel" name="{{:value}}" class="quantity" value="0" maxlength="15" data-min-quantity="0" data-max-quantity="{{:maxQuantity}}">' +
		'<button type="button" class="plus">'+
			'<i class="icon-sum-plus">추가</i>' +
		'</button>' +
'</div>');


$.templates( 'selectBox',
'<dl>'+
	'<dt>{{:label}}</dt>'+
	'<dd class="select-type1">'+
		'<label for="option{{:productCode}}">선택하세요</label>'+
		'<select id="option{{:productCode}}" name="itemCode">'+
			'<option value="" selected>선택하세요</option>' +
			'{{for optionData}}' +
			'<option value="{{:value}}" {{if isSoldOut }}disabled{{/if}} data-variation="{{:variationYn}}">{{:name}}{{if isSoldOut}}-품절{{/if}}</option>' +
			'{{/for}}'+
		'</select>'+
	'</dd>'+
'</dl>');

$.templates( 'selectBoxForDeal',
'<dl>'+
	'<dt>{{:label}}</dt>'+
	'<dd class="select-type1">'+
		'<label for="option{{:productCode}}">선택하세요</label>'+
		'<select id="option{{:productCode}}" name="item{{:productCode}}">'+
			'<option value="" selected>선택하세요</option>' +
			'{{for optionData}}' +
			'<option value="{{:productCode}}" {{if isSoldOut}}disabled{{/if}} data-option-yn="{{:hasOption}}" data-variation="{{:variationYn}}">{{:name}}{{if isSoldOut}}-품절{{/if}}</option>' +
			'{{/for}}'+
		'</select>'+
	'</dd>'+
'</dl>');

$.templates( 'mobileSelectBox',
'<select id="option{{:productCode}}" name="itemCode">' +
	'<option value="" selected>선택하세요</option>'+
	'{{for optionData}}' +
	'<option value="{{:value}}" {{if isSoldOut }}disabled{{/if}} data-option-yn="{{:hasOption}}" data-variation="{{:variationYn}}">{{:name}}{{if isSoldOut}}-품절{{/if}}</option>' +
	'{{/for}}'+
'</select>');

$.templates( 'benefitLayer',
	'<section class="layerpop-type2 benefit-layer {{:~className(className)}}">' +
		'<div class="container">' +
			'<h1 class="tit-h">할인 내역 </h1>' +
			'<div class="contents">' +
				'<ul class="bul-circle">' +
					'{{for data}}' +
						'<li>{{:title}} <em class="txt-red flt-right">{{:value}}</em></li>' +
					'{{/for}}'+
				'</ul>' +
			'</div>' +
		'</div>' +
		'<a href="javascript:;" class="btn-ico-close js-close" title="레이어팝업 닫기"><i>할인 내역 레이어팝업 닫기</i></a>' +
	'</section>');

$.templates( 'toastLayer',
	'<section class="{{:layerClass}}">'+
		'<div class="container">'+
			'<div class="contents">{{:contents}}</div>'+
		'</div>'+
	'</section>');

$.templates( 'popupLayer',
	'<section class="{{:layerClass}}">' +
		'<div class="container">'+
			'<h1 class="tit-h">{{:title}}</h1>'+
			'<div class="contents">{{:contents}}</div>'+
		'</div>'+
		'<button type="button" class="btn-ico-close"><i>레이어팝업 닫기</i></button>'+
	'</section>');

$.templates( 'bookmarkLayer',
	'<section class="layerpop-bookmark layerpop-target layer-bottom">' +
		'{{:contents}}'+
		'<a href="/event/enter.do?categoryId=C0070347" class="btn-type1">{{if isBookMart}}혜택 받으러 가기{{else}}바로가기 ON 하기{{/if}}</a>'+
	'</section>');

$.templates( 'loadingBarForPC',
		'<div id="wrapLoadingBar">' +
			'<img src="{{:~imageCdnPath()}}/lm2/images/layout/loadinglogo.gif" alt="롯데마트Mall">' +
		'</div>');

$.templates( 'loadingBarForMobile',
		'<div class="wrapLoadingBar" style="display:none;">' +
		'<div class="container">' +
		'	<div class="bar">' +
		'	<div class="thing"></div>' +
		'	<div class="thing"></div>' +
		'	<div class="thing"></div>' +
		'	<div class="thing"></div>' +
		'	<div class="thing"></div>' +
		'	<div class="thing"></div>' +
		'	<div class="thing"></div>' +
		'	<div class="thing"></div>' +
		'</div>' +
		'<div class="logo">' +
		'	<img src="{{:~sImageCdnPath()}}/lm2/images/layout/m-loadinglogo.png" alt="롯데마트Mall">' +
		'</div>' +
		'</div></div>');

$.templates( 'rnbBasket',
	'<strong class="tab01 {{if activeClass === ' + "'tab01'" + '}}active{{/if}}" data-tab-id="tab01">' +
'<a href="#RNBcartTab01">장바구니<em>{{:normalBasket.itemList.length}}</em></a></strong>' +
	'<div class="tab-wrap tab-cont" id="RNBcartTab01">' +
		'{{if normalBasket.itemList.length > 0}}' +
			'<ul class="item-list">' +
				'{{for normalBasket.itemList tmpl="rnbBasketItem" /}}' +
			'</ul>' +
		'{{/if}}' +
		'<div class="no-data-result" {{if normalBasket.itemList.length != 0}}style="display: none;"{{/if}}>' +
			'<p class="txt">장바구니에 담긴 상품이 없습니다.</p>' +
		'</div>' +
	'</div>' +
	'<strong class="tab02 {{if activeClass === ' + "'tab02'" + '}}active{{/if}}" data-tab-id="tab02" {{if (highwayStrYn == ' + "'Y'" + ' || zipSeq == ' + "'2472039'" + ' || isSmartPickUp == ' + "'Y'" + ') || pickUpYn == ' + "'Y'" + '}} style="display: none;" {{/if}}>'+
	'<a href="#RNBcartTab02">정기배송<em>{{:periDeliBasket.itemList.length}}</em></a></strong>' +
	'<div class="tab-wrap tab-cont" id="RNBcartTab02" {{if (highwayStrYn == ' + "'Y'" + ' || zipSeq == ' + "'2472039'" + ' || isSmartPickUp == ' + "'Y'" + ') || pickUpYn == ' + "'Y'" + '}} style="display: none;" {{/if}}>' +
		'{{if periDeliBasket.itemList.length > 0}}' +
			'<ul class="item-list">' +
				'{{for periDeliBasket.itemList tmpl="rnbBasketItem" /}}' +
			'</ul>' +
		'{{/if}}' +
		'<div class="no-data-result" {{if periDeliBasket.itemList.length != 0}}style="display: none;"{{/if}}>' +
			'<p class="txt">장바구니에 담긴 상품이 없습니다.</p>' +
		'</div>' +
	'</div>'
);

$.templates( 'rnbBasketItem',
		'<li id="RNB_Basket_{{:BSKET_NO}}">'+
			'<a href="javascript:goProductDetail(' + "'{{:CATEGORY_ID}}','{{:PROD_CD}}','N','','','{{:MALL_DIVN_CD}}'" + ')" class="thumb">'+
				'{{if STOCK_QTY < 1}}'+
				'<span class="prod-sout">'+
					'<img class="thumb" src="{{:~getSoldOutImagePath()}}" alt="상품 준비중"/>'+
				'</span>'+
				'<img src="{{:~getLmCdnStaticItemImagePath(MD_SRCMK_CD)}}" data-PROD_CD="{{:PROD_CD}}" data-MD_SRCMK_CD="{{:MD_SRCMK_CD}}" alt="{{:PROD_NM}} 품절" onerror="showNoImage(this)">'+
				'{{else ~isAdult(ADL_YN)}}'+
				'<img src="{{:~getAdultImagePath()}}" data-PROD_CD="{{:PROD_CD}}" data-MD_SRCMK_CD="{{:MD_SRCMK_CD}}" alt="19세 이상 이미지 표시">'+
				'{{else}}'+
				'<img src="{{:~getLmCdnStaticItemImagePath(MD_SRCMK_CD)}}" data-PROD_CD="{{:PROD_CD}}" data-MD_SRCMK_CD="{{:MD_SRCMK_CD}}" alt="{{:PROD_NM}}" onerror="showNoImage(this)">'+
				'{{/if}}'+
				'<div class="wrap-item">'+
					'<p class="prod-name"><strong>{{:PROD_NM}}</strong></p>'+
					'<p class="count"><em>{{:BSKET_QTY}}</em>개</p>'+
					'<p class="price-max">구매가 <em>{{:~numberFormat(PROMO_MAX_VAL * BSKET_QTY)}}</em>원</p>'+
				'</div>'+
			'</a>'+
			'<button type="button" class="item-remove" onclick="RNB.basket.removeBasket(' + "'{{:BSKET_NO}}'" + ')">삭제</button>'+
		'</li>'
);


$.templates( 'rnbCoupon',
		'<strong class="layer-tit">보유쿠폰<em>{{:totalCount}}</em></strong>' +
		'<div class="scroll-wrap">' +
			'<ul class="coupon">' +
				'{{if ~isLogin() === false}}' +
					'<li><em class="desc">로그인 후 확인하실 수 있습니다.</em></li>' +
				'{{else}}' +
					'{{if list.length > 0 }}' +
						'{{for list}}' +
							'<li><a href="/mymart/selectMyCoupon.do" class="coupon-link"><em class="txt">{{:COUPON_NM}}</em><span class="date">{{:USE_END_DY_FMT}} 까지</span></a></li>' +
						'{{/for}}'+
					'{{else}}' +
						'<li><em class="desc">보유하고 있는 쿠폰이 없습니다.</em></li>' +
					'{{/if}}' +
				'{{/if}}' +
			'</ul>' +
		'</div>' +
		'<strong class="layer-tit">만기 예정 쿠폰<em>{{:expireCount}}</em></strong>' +
		'<div class="scroll-wrap">' +
			'<ul class="coupon">' +
				'{{if ~isLogin() === false}}' +
					'<li><em class="desc">로그인 후 확인하실 수 있습니다.</em></li>' +
				'{{else}}' +
					'{{if expireList.length > 0 }}' +
						'{{for expireList}}' +
							'<li><a href="/mymart/selectMyCoupon.do" class="coupon-link"><em class="txt">{{:COUPON_NM}}</em><span class="date expiration-date">{{:USE_END_DY_FMT}} 만료</span></a></li>' +
						'{{/for}}'+
					'{{else}}' +
						'<li><em class="desc">만료 예정인 쿠폰이 없습니다.</em></li>' +
					'{{/if}}' +
				'{{/if}}' +
			'</ul>' +
		'</div>' +
		'<a href="/event/eventMain.do?SITELOC=AE004" class="link-couponlist">더 많은 쿠폰 받기</a>' +
		'<button type="button" class="layer-close">쿠폰레이어 닫기</button>'
);

$.templates( 'rnbHistory',
		'<strong class="layer-tit">히스토리<em>{{:totalCount}}</em></strong>'+
		'<div class="scroll-wrap">'+
			'<ul class="item-list">'+
			'{{for history}}'+
				'<li>'+
					'<a href="{{:~getRNBHistoryLink(histTypeCd, categoryId, contentsNo, prodCd,mallDivnCd)}}"'+
						'class="thumb" {{if histTypeCd !=="05"}}id="RNB_History_{{:histSeq}}"{{/if}}>'+
						'{{if histTypeCd === "01"}}'+
							'{{if (prodCd).substring(0,1) != "D" && stockQty < 1}}'+
								'<span class="prod-sout">'+
									'<img class="thumb" src="{{:~getSoldOutImagePath()}}" alt="상품 준비중"/>'+
								'</span>'+
								'<img src="{{:~getLmCdnStaticItemImagePath(mdSrcmkCd)}}" data-PROD_CD="{{:prodCd}}" data-MD_SRCMK_CD="{{:mdSrcmkCd}}" alt="{{:histNm}} 품절" onerror="showNoImage(this)">'+
							'{{else ~isAdult(adlYn)}}'+
								'<img src="{{:~getAdultImagePath()}}" data-PROD_CD="{{:prodCd}}" data-MD_SRCMK_CD="{{:mdSrcmkCd}}" alt="19세 이상 이미지 표시" >'+
							'{{else}}'+
								'<img src="{{:~getLmCdnStaticItemImagePath(mdSrcmkCd)}}" data-PROD_CD="{{:prodCd}}" data-MD_SRCMK_CD="{{:mdSrcmkCd}}" alt="{{:histNm}}" onerror="showNoImage(this)">'+
							'{{/if}}'+
							'<div class="wrap-item">'+
								'<p class="prod-name"><strong>{{:histNm}}</strong></p>'+
								'{{if sellPrc > currSellPrc }}'+
									'<p class="prod-price">판매가<span>{{:~numberFormat(sellPrc)}}</span>원</p>'+
									'<p class="price-max">최저가<em>{{:~numberFormat(currSellPrc)}}</em>원</p>'+
								'{{else}}'+
									'<p class="price-max">판매가<em>{{:~numberFormat(currSellPrc)}}</em>원</p>'+
								'{{/if}}'+
							'</div>'+
						'{{else}}' +
							'<img src="{{:~getRNBHistoryImage(histTypeCd, histImg)}}" alt="{{:histNm}}" {{if histTypeCd !== "02"}}onerror="OnErrorImage(this, 80, 80, {{:~getRNBHistoryErrorImage(histTypeCd)}})"{{/if}}>'+
							'<div class="wrap-info">'+
								'<strong {{if histTypeCd !== "02"}}class="{{:~getRNBHistroyClass(histTypeCd)}}"{{/if}}>{{:~getRNBHistroyTitle(histTypeCd)}}</strong>'+
								'<p class="txt">{{:histNm}}</p>'+
							'</div>'+
						'{{/if}}' +
				'</a>'+
				'{{if histTypeCd === "01"}}' +
					'{{if optionYn == "Y"}}'+
					'<button type="button" class="btn-cart" onclick="RNB.goHistoryProdDetail(' + "'{{:categoryId}}','{{:prodCd}}'" + ')"><span class="txt">장바구니에 담기</span></button>'+
					'{{else}}'+
					'<button type="button" class="btn-cart" onclick="RNB.basket.addBasket({prodCd:' + "'{{:prodCd}}'" + ', categoryId:' + "'{{:categoryId}}'" + '})"><span class="txt">장바구니에 담기</span></button>'+
					'{{/if}}'+
					'<button type="button" class="item-remove" onclick="RNB.history.removeHistory(' + "'{{:histSeq}}'" + ')">삭제</button>'+
				'{{/if}}'+
				'{{if histTypeCd === "05"}}' +
					'<button type="button" class="item-remove" onclick="RNB.history.removeHistory(' + "'{{:histSeq}}'" + ')">삭제</button>' +
				'{{/if}}' +
				'</li>'+
			'{{/for}}'+
			'</ul>'+
			'<div class="no-data-result" {{if history.length != 0}}style="display: none;"{{/if}}>'+
				'<p class="txt">히스토리가 없습니다.</p>'+
			'</div>'+
			'<button type="button" class="layer-close">히스토리 레이어 닫기</button>'+
		'</div>'
);

$.templates( 'rnbQuickHistory',
		'{{if history.length < 1}}'+
			'<p class="list-none">최근 조회한<em>히스토리가</em>없습니다.</p>'+
		'{{else}}'+
			'<ul>'+
			'{{for history}}'+
				'{{if #index%2 === 0}}'+
					'<li {{if #getIndex() === 0}}class="active"{{/if}}>'+
				'{{/if}}'+
				'<a href="{{:~getRNBHistoryLink(histTypeCd, categoryId, contentsNo, prodCd,mallDivnCd)}}"'+
					'{{if histTypeCd ==="01"}} class="prod-item"{{/if}}' +
					'{{if histTypeCd !=="05"}} id="RNB_QuickHistory_{{:histSeq}}"{{/if}}>'+
					'{{if histTypeCd ==="01"}}' +
						'{{if (prodCd).substring(0,1) != "D" && stockQty < 1}}'+
						'<span class="prod-sout">'+
							'<img class="thumb" src="{{:~getSoldOutImagePath()}}" alt="상품 준비중"/>'+
						'</span>'+
							'<img src="{{:~getLmCdnStaticItemImagePath(mdSrcmkCd)}}" data-PROD_CD="{{:prodCd}}" data-MD_SRCMK_CD="{{:mdSrcmkCd}}" alt="{{:histNm}} 품절" onerror="showNoImage(this)">'+
						'{{else ~isAdult(adlYn)}}'+
							'<img src="{{:~getAdultImagePath()}}" data-PROD_CD="{{:prodCd}}" data-MD_SRCMK_CD="{{:mdSrcmkCd}}" alt="19세 이상 이미지 표시" >'+
						'{{else}}'+
							'<img src="{{:~getLmCdnStaticItemImagePath(mdSrcmkCd)}}" data-PROD_CD="{{:prodCd}}" data-MD_SRCMK_CD="{{:mdSrcmkCd}}" alt="{{:histNm}}" onerror="showNoImage(this)">'+
						'{{/if}}'+
						'<div class="wrap-item">'+
							'<p class="prod-name"><strong>{{:histNm}}</strong></p>'+
							'<p class="price-max">판매가 <em>{{:~numberFormat(currSellPrc)}}</em>원</p>'+
						'</div>'+
					'{{else}}' +
						'<div class="wrap-info">'+
							'<strong class="{{:~getRNBHistroyClass(histTypeCd)}}">{{:~getRNBHistroyTitle(histTypeCd)}}</strong>'+
							'<p class="txt">{{:histNm}}</p>'+
						'</div>'+
					'{{/if}}' +
				'</a>'+
				'{{if #index%2 === 1 }}'+
				'</li>'+
				'{{/if}}'+
			'{{/for}}'+
			'</ul>'+
			'<div class="control-btn">'+
				'<button type="button" class="prev">이전</button>'+
				'<button type="button" class="next">다음</button>'+
			'</div>'+
	'{{/if}}'
);

$.templates( 'emptyMainBest',
	'<div class="cont-wrap">' +
		'<strong class="message-txt">원하는 상품이 없다면 이렇게 찾아보세요.</strong>' +
		'<ul>' +
			'<li><a href="/plan/planDetail.do?CategoryID=C2060003&MkdpSeq=000000000002" class="link01">마일리지, 사은품 받자 사은행사</a></li>' +
			'<li><a href="/best/bestMain.do" class="link02">베스트 상품만 콕콕 베스트</a></li>' +
			'<li><a href="/plan/planMain.do?SITELOC=AC022&STR_CD={{:mainStoreCode}}&lotteEmpYn={{:lotteEmpYn}}&CategoryID=C206" class="link03">행사의 모든것 기획전</a></li>' +
		'</ul>' +
	'</div>'
);

$.templates( 'planDetailOption',
	'<select>' +
		'{{if categoryList.length === 0 }}' +
			'<option>등록된 기획전이 없습니다.</option>'+
			'{{:~onToggleBtnMovePlanDetail(false)}}' +
		'{{else}}' +
			'{{for categoryList}}' +
				'{{if ~isAppendPlanDetailOption( CATEGORY_ID )}}' +
					'<option value="{{:CATEGORY_ID}},{{:MKDP_SEQ}}" {{if #index === 0}}selected{{/if}}>' +
						'{{:MKDP_NM}}' +
					'</option>'+
				'{{/if}}' +
				'{{:~onToggleBtnMovePlanDetail(true)}}' +
			'{{/for}}'+
		'{{/if}}' +
	'</select>'
);

$.templates( 'mobileBenefitLayer',
	'<div class="layerpopup-type1 {{:~className(className)}}">' +
		'<ul class="discount-list">' +
			'{{for data}}' +
				'<li>'+
					'<p class="left">{{:title}}</p>' +
					'<p class="number-point right">{{:value}}</p>'+
				'</li>' +
			'{{/for}}' +
		'</ul>' +
	'</div>'
);

$.templates( 'mobileRnbMyHistory',
	'{{if historyList.length > 0}}' +
		'{{for historyList}}' +
			'<li>' +
				'<a href="{{:~getLMAppUrlM()}}/mobile/cate/PMWMCAT0004_New.do?CategoryID={{:categoryId}}&ProductCD={{:prodCd}}" data-gtm="M094">' +
					'<div class="thumbnail">' +
						'{{if ~isSoldProductItem(prodCd, stockQty )}}' +
							'<span class="prod-sout"><img class="thumb" src="{{:~getSoldOutImagePath()}}" alt="상품 준비중"></span>' +
						'{{/if}}'+
						'<img src="{{:~getLmCdnStaticItemImagePath(mdSrcmkCd)}}" onerror="javascript:showNoImage(this,80,80)" alt="">' +
					'</div>' +
					'<div class="conts">' +
						'<p class="title">{{:histNm}}</p>' +
						'<p class="price">' +
							'{{if sellPrc === currSellPrc}}' +
								'<strong class="number">{{:~numberFormat(sellPrc)}}<i>원</i></strong>' +
							'{{else selPrc < currSellPrc }}' +
								'<strong class="number">{{:~numberFormat(currSellPrc)}}<i>원</i></strong>' +
							'{{else}}' +
								'<del class="number">{{:~numberFormat(sellPrc)}}<i>원</i></del><strong class="number">{{:~numberFormat(currSellPrc)}}<i>원</i></strong>' +
							'{{/if}}' +
						'</p>' +
					'</div>' +
				'</a>' +
				'<button type="button" class="toggle" data-gtm="M095"><i class="icon-common-gntoggle"></i></button>' +
				'<div class="wrap-togglebox">' +
					'<button class="action-trash" data-gtm="M096" data-seq="{{:histSeq}}"><i class="icon-common-trash">삭제</i></button>' +
					'<button class="action-cart" data-gtm="M097" data-category-id="{{:categoryId}}" data-product-code="{{:prodCd}}" data-option-yn="{{:optionYn}}"><i class="icon-common-cart">장바구니</i></button>' +
				'</div>'+
			'</li>' +
		'{{/for}}' +
	'{{else}}' +
		'<div class="list-empty">최근 본 상품이 없습니다.</div>' +
	'{{/if}}'
);

$.templates( 'mobileRnbMyHistoryForMenu',
	'{{if historyList.length > 0 }}' +
		'{{for historyList}}' +
			'<li>' +
				'{{if histTypeCd === "02"}}' +
					'<a href="{{:~getLMAppUrlM()}}/mobile/cate/productList.do?CategoryID={{:categoryId}}">' +
						'<div class="thumbnail label-category">카테고리</div>' +
						'<div class="conts">' +
							'<p class="desc">{{:histNm}}</p>' +
						'</div>'+
					'</a>' +
				'{{else histTypeCd === "03"}}' +
					'<a href="{{:~getLMAppUrlM()}}/mobile/plan/planDetail.do?CategoryID={{:categoryId}}&MkdpSeq={{:contentsNo}}&viewType=L&mode=M&pdivnSeq=001">' +
						'<div class="thumbnail">' +
							'<img src="{{:~getLmCdnStaticImagePath(histImg)}}" onerror="javascript:this.src=\'{{:~lmCdnV3RootPath()}}/images/layout/noimg_plan_list_80x80.jpg\'"/>' +
						'</div>' +
						'<div class="conts">' +
							'<p class="tag"><i class="icon-tag-notic1">{{:histTypeNm }}</i></p>' +
							'<p class="desc">{{:histNm}}</p>' +
						'</div>' +
					'</a>' +
				'{{else histTypeCd === "04"}}' +
					'<a href="{{:~getLMAppUrlM()}}/mobile/evt/mEventDetail.do?categoryId={{:categoryId}}">' +
						'<div class="thumbnail">' +
							'<img src="{{:~getLmCdnStaticImagePath(histImg)}}" onerror="javascript:this.src=\'{{:~lmCdnV3RootPath()}}/images/layout/noimg_event_80x63.jpg\'"/>' +
						'</div>' +
						'<div class="conts">' +
							'<p class="tag"><i class="icon-tag-notic2">{{:histTypeNm }}</i></p>' +
							'<p class="desc">{{:histNm}}</p>' +
						'</div>' +
					'</a>' +
				'{{else histTypeCd === "05"}}' +
					'<a href="{{:~getLMAppUrlM()}}/mobile/trend/mobileTrendDetail.do?CategoryID={{:categoryId}}&trendNo={{:contentsNo}}&trendTabNo=01">' +
						'<div class="thumbnail">' +
							'<img src="{{:~getLmCdnStaticImagePath(histImg)}}" onerror="javascript:this.src=\'{{:~lmCdnV3RootPath()}}/images/layout/noimg_trend_80x63.jpg\'"/>' +
						'</div>' +
						'<div class="conts">' +
							'<p class="tag"><i class="icon-tag-notic3">{{:histTypeNm }}</i></p>' +
							'<p class="desc">{{:histNm}}</p>' +
						'</div>' +
					'</a>' +
				'{{/if}}' +
				'<button type="button" class="toggle"><i class="icon-common-gntoggle"></i></button>' +
				'<div class="wrap-togglebox">' +
					'<button class="action-trash" data-seq="{{:histSeq}}"><i class="icon-common-trash">삭제</i></button>' +
				'</div>' +
			'</li>' +
		'{{/for}}' +
	'{{else}}' +
		'<div class="list-empty">최근 본 메뉴가 없습니다.</div>' +
	'{{/if}}'
);

$.templates( 'mobileRnbMyByHistory',
		'{{if historyList.length > 0}}' +
			'{{for historyList}}' +
				'<li>' +
					'<a href="{{:~getLMAppUrlM()}}/mobile/cate/PMWMCAT0004_New.do?CategoryID={{:categoryId}}&ProductCD={{:prodCd}}">' +
						'<div class="thumbnail">' +
							'{{if ~isSoldProductItem(prodCd, stockQty )}}' +
								'<span class="prod-sout"><img class="thumb" src="{{:~getSoldOutImagePath()}}" alt="상품 준비중"></span>' +
							'{{/if}}'+
							'<img src="{{:~getLmCdnStaticItemImagePath(mdSrcmkCd)}}" onerror="javascript:showNoImage(this,80,80)" alt="">' +
						'</div>' +
						'<div class="conts">' +
							'<p class="title">{{:prodNm}}</p>' +
							'<p class="price">' +
								'{{if sellPrc === currSellPrc}}' +
									'<strong class="number">{{:~numberFormat(sellPrc)}}<i>원</i></strong>' +
								'{{else selPrc < currSellPrc }}' +
									'<strong class="number">{{:~numberFormat(currSellPrc)}}<i>원</i></strong>' +
								'{{else}}' +
									'<del class="number">{{:~numberFormat(sellPrc)}}<i>원</i></del><strong class="number">{{:~numberFormat(currSellPrc)}}<i>원</i></strong>' +
								'{{/if}}' +
							'</p>' +
						'</div>' +
					'</a>' +
					'<button type="button" class="toggle"><i class="icon-common-gntoggle"></i></button>' +
					'<div class="wrap-togglebox">' +
						'<button class="action-cart" data-category-id="{{:categoryId}}" data-product-code="{{:prodCd}}" data-option-yn="{{:optionYn}}" data-item-code="{{:itemCd}}"><i class="icon-common-cart">장바구니</i></button>' +
					'</div>'+
				'</li>' +
			'{{/for}}' +
		'{{else}}' +
			'<div class="list-empty">최근 구매 상품이 없습니다.</div>' +
		'{{/if}}'
	);
$.templates( 'mobileRnbMyByHistory',
	'{{if historyList.length > 0}}' +
		'{{for historyList}}' +
			'<li>' +
				'<a href="{{:~getLMAppUrlM()}}/mobile/cate/PMWMCAT0004_New.do?CategoryID={{:categoryId}}&ProductCD={{:prodCd}}">' +
					'<div class="thumbnail">' +
						'{{if ~isSoldProductItem(prodCd, stockQty )}}' +
							'<span class="prod-sout"><img class="thumb" src="{{:~getSoldOutImagePath()}}" alt="상품 준비중"></span>' +
						'{{/if}}'+
						'<img src="{{:~getLmCdnStaticItemImagePath(mdSrcmkCd)}}" onerror="javascript:showNoImage(this,80,80)" alt="">' +
					'</div>' +
					'<div class="conts">' +
						'<p class="title">{{:prodNm}}</p>' +
						'<p class="price">' +
							'{{if sellPrc === currSellPrc}}' +
								'<strong class="number">{{:~numberFormat(sellPrc)}}<i>원</i></strong>' +
							'{{else selPrc < currSellPrc }}' +
								'<strong class="number">{{:~numberFormat(currSellPrc)}}<i>원</i></strong>' +
							'{{else}}' +
								'<del class="number">{{:~numberFormat(sellPrc)}}<i>원</i></del><strong class="number">{{:~numberFormat(currSellPrc)}}<i>원</i></strong>' +
							'{{/if}}' +
						'</p>' +
					'</div>' +
				'</a>' +
				'<button type="button" class="toggle"><i class="icon-common-gntoggle"></i></button>' +
				'<div class="wrap-togglebox">' +
					'<button class="action-cart" data-category-id="{{:categoryId}}" data-product-code="{{:prodCd}}" data-option-yn="{{:optionYn}}" data-item-code="{{:itemCd}}"><i class="icon-common-cart">장바구니</i></button>' +
				'</div>'+
			'</li>' +
		'{{/for}}' +
	'{{else}}' +
		'<div class="list-empty">최근 구매 상품이 없습니다.</div>' +
	'{{/if}}'
);
$.templates( 'shareLayer',
	'<article class="layerpopup-share">' +
		'<header class="header">' +
			'<h3 class="title">공유하기</h3>' +
			'<button class="icon-close js-close">닫기</button>' +
		'</header>' +
		'<div class="wrap-sharelist">' +
			'<a href="javascript:;" data-service="kakaotalk"><i class="icon-share-kakao"></i>카카오톡</a>' +
			'<a href="javascript:;" data-service="facebook"><i class="icon-share-facebook"></i>페이스북</a>' +
			'<a href="javascript:;" data-service="twitter"><i class="icon-share-twiter"></i>트위터</a>' +
			'<a href="javascript:;" data-service="sms"><i class="icon-share-sms"></i>SMS</a>' +
			'<a href="javascript:;" data-service="kakaostory" class="hidden"><i class="icon-share-kakaostory"></i>카카오스토리</a>' +
			'<a href="javascript:;" data-service="band" class="hidden"><i class="icon-share-band"></i>밴드</a>' +
			'<a href="javascript:;" data-service="line"><i class="icon-share-line"></i>라인</a>' +
			'<a href="javascript:;" class="link-copy"><i class="icon-share-url"></i>URL 복사</a>' +
		'</div>' +
	'</article>'
);
$.templates( 'appInstallInductionLayer',
	'<div class="wrap-appsetlayer">'+
		'<div class="conts">'+
			'<p class="blind">앱에서 더 큰 혜택!</p>'+
			'<a href="javascript:;">' +
				'<img src="//simage.lottemart.com/lm2/images/contents/2017/07/m-appset-layer.png" alt="APP에서 혜택 받기">' +
			'</a>'+
			'<button type="button" class="js-close">괜찮아요. 지금 페이지에서 볼래요</button>'+
		'</div>'+
		'<div class="wrap-mask"></div>'+
	'</div>'
);
$.templates('appInstallInductionLayer2',
	'<div class="wrap-appsetlayer type-qr">'+
		'<div class="conts">'+
			'<p class="blind">앱에서 더 큰 혜택!</p>'+
			'<a href="{{:dlink}}">'+
				'<img src="//simage.lottemart.com/lm2/images/contents/2017/10/m-appset-layer-qr.png" alt="APP에서 혜택 받기">'+
			'</a>'+
			'<button type="button" class="js-close">괜찮아요. 지금 페이지에서 볼래요</button>'+
		'</div>'+
		'<div class="wrap-mask"></div>'+
	'</div>'
);
$.templates( 'holidaysLNBSubLayer',
	'<div class="gry-lnb-depth">'+
		'<p class="title">{{:parentCategoryName}}</p>'+
		'<ul>' +
			'{{for categories ~startSiteLocationIndex=startSiteLocationIndex ~subSiteLocation=subSiteLocation}}' +
				'<li>' +
					'{{if ~startSiteLocationIndex > 0}}' +
						'<a href="/holidays/detail.do?ppass=1&CATEGORY_ID={{:CATEGORY_ID }}&SITELOC=' + "VS{{:~lPad((#getIndex() + ~startSiteLocationIndex), 3, '0')}}" + '">' +
					'{{else}}' +
						'<a href="/holidays/detail.do?ppass=1&CATEGORY_ID={{:CATEGORY_ID }}&SITELOC={{:~subSiteLocation}}">' +
					'{{/if}}' +
						'{{:CATEGORY_NM}}' +
						'{{if ICON_PATH != null && ICON_PATH != "" }}' +
							'<img src="{{:~getLmCdnStaticImagePath(ICON_PATH)}}" alt="{{:CATEGORY_NM}}" onerror="this.remove()" />' +
						'{{/if}}' +
					'</a>' +
				'</li>' +
			'{{/for}}' +
		'</ul>' +
	'</div>'
);
$.templates( 'holidaysProductListPanel',
	'{{if categoryId != null && categoryId != ""}}' +
		'<div name="productPanel" class="{{:productListWrapperClass }}" data-category-id="{{:categoryId }}">' +
	'{{/if}}' +
		'<h3 class="blind">{{:categoryName}}</h3>' +
		'{{if productListHtml != null && productListHtml != ""}}' +
			'{{:productListHtml}}' +
			'{{if hasMoreButton === true}}' +
				'<div class="area-btn-more">' +
					'<a href="/holidays/detail.do?CATEGORY_ID={{:categoryId}}&SITELOC={{:siteLocation }}"><img src="//simage.lottemart.com/lm2/images/contents/2019/11/holiday/holi_btn_more.png" alt="상품 더 보기"></a>' +
				'</div>' +
			'{{/if}}' +
		'{{else}}' +
			'{{include tmpl="emptyProductListPanel" /}}' +
		'{{/if}}' +
	'{{if categoryId != null && categoryId != ""}}'+
		'</div>'+
	'{{/if}}'
);
$.templates( 'mobileHolidaysProductListPanel',
	'<article id="{{:categoryId}}" name="productPanel" class="{{:classId}}" data-category-id="{{:categoryId }}">' +
		'{{if productListHtml != null && productListHtml != ""}}' +
			'{{:productListHtml}}' +
			'{{if hasMoreButton === true}}' +
				'<div class="area-holi-morebtn">' +
				'<a href="{{:categoryListUrl}}?CategoryID={{:categoryId}}&SITELOC={{:siteLocation }}" class="holi-btn-list-bottom"><span>더 많은 상품 보기</span></a>' +
				'</div>' +
			'{{/if}}' +
		'{{else}}' +
			'{{include tmpl="emptyProductListPanel" /}}' +
		'{{/if}}' +
	'</article>'
);
$.templates( 'holidaysBannerProductListPanel',
	'<h2 class="holi-bullet-title"><span class="title-holi-premium">{{:categoryName}}</span></h2>' +
	'<div name="productPanel" class="{{:productListWrapperClass }}" data-category-id="{{:categoryId }}">' +
		'{{if productListHtml != null && productListHtml != ""}}' +
			'{{:productListHtml}}' +
		'{{else}}' +
			'{{include tmpl="emptyProductListPanel" /}}' +
		'{{/if}}' +
	'</div>'
);
$.templates( 'holidaysBannerProductListMd',
		'<h2 class="holi-bullet-title"><span class="holi_title_slide">MD 추천상품</span></h2>' +
		'{{if productListHtml != null && productListHtml != ""}}' +
			'{{:productListHtml}}' +
		'{{else}}' +
			'{{include tmpl="emptyProductListPanel" /}}' +
		'{{/if}}'
	);
// MD 상품 추천
$.templates( 'mobileHolidaysBannerProductListPanel',
	'{{if productListHtml != null && productListHtml != ""}}' +
		'{{:productListHtml}}' +
	'{{else}}' +
		'{{include tmpl="emptyProductListPanel" /}}' +
	'{{/if}}'
	);
$.templates( 'emptyProductListPanel',
	'<p class="nonmsg">해당 상품이 없습니다.</p>'
);
$.templates( 'mobileHolidaysBestCategoryTabs',
	'<nav class="holi-sticky-tab">' +
		'<div class="holi-sticky-wrap">' +
			'{{for bestCategories}}' +

			'{{/for}}' +
		'</div>' +
	'</nav>'
);
$.templates('mobilePriceWrap',
		'<div class="price-wrap">'+
			'<div class="price">'+
				'{{if CURR_SELL_PRC > PROM_MAX_VAL}}' +
					'<strong class="point1">{{:~saleRatio(PROM_MAX_VAL, CURR_SELL_PRC)}}%</strong>'+
					'<div class="number">' +
						'<em class="sale">{{:~numberFormat(PROM_MAX_VAL)}}</em>' +
						'<i>원{{if ~isDeal(ONLINE_PROD_TYPE_CD)}}~{{/if}}</i>' +
					'</div>' +
					'<div class="number">' +
						'<em>{{:~numberFormat(CURR_SELL_PRC)}}</em>' +
						'<i>원</i>' +
					'</div>' +
				'{{else}}'+
					'<div class="number">' +
						'<em>{{:~numberFormat(CURR_SELL_PRC)}}</em>' +
						'<i>원{{if ~isDeal(ONLINE_PROD_TYPE_CD)}}~{{/if}}</i></i>' +
					'</div>' +
				'{{/if}}'+
			'</div>'+
		'</div>'
);
$.templates('mobileProductListBadge',
	'<div class="badge">' +
		'{{if FREE_DELIVERY === "Y" && ONLINE_PROD_TYPE_CD != "08" }}' +
			'{{if DELI_AMT_NM !== null && DELI_AMT_NM !== ""}}' +
				'<div class="type01">{{:DELI_AMT_NM}}</div>' +
			'{{/if}}'+
		'{{/if}}' +
		'{{if ~isDealTypeProductItem(ONLINE_PROD_TYPE_CD, DELI_TYPE)}}' +
			'{{if DELI_TP_NM !== null && DELI_TP_NM !== ""}}' +
				'<div class="type01">{{:DELI_TP_NM}}</div>' +
				'{{if PERI_DELI_YN === "Y"}}' +
					'<div class="type01">'+
						'정기배송' +
						'{{if PERI_COUPON != null}}' +
							'[{{: PERI_COUPON}} 할인]' +
						'{{/if}}'+
					 '</div>'+
				 '{{/if}}'+
			'{{/if}}'+
		'{{/if}}' +
		'{{if PROMO_PROD_ICON !== null}}' +
			'{{:~getMobileBage(PROMO_PROD_ICON, 1)}}' +
		'{{/if}}' +
		'{{if IS_EXPRESS_DELIVERY_AREA !== null &&  EXPRESS_DELI_YN !== null}}' +
			'{{if IS_EXPRESS_DELIVERY_AREA === "Y" &&  EXPRESS_DELI_YN === "Y"}}' +
				'<div class="type03">바로배송</div>' +
			'{{/if}}'+
		'{{/if}}'+
	'</div>'
);
$.templates('mobilePeriodShippingSmartRecommendList',
	'{{for list}}' +
	'<li data-panel="product">' +
		'<div class="product-wrap">' +
			'<div class="product-img">' +
			'{{if ~isAdult(ADL_YN)}}' +
				'<img src="{{:~lmCdnV3RootPath()}}/images/layout/m-goods-adult.png" alt="19">'+
			'{{else}}' +
				'{{if ~isSoldOut(SOUT_YN_REAL)}}' +
					'<span class="prod-sout">'+
						'<img class="thumb" src="{{:~lmCdnV3RootPath()}}/images/layout/img_soldout_500x500.png"/>'+
					'</span>' +
				'{{/if}}' +
				'<img src="{{:THUMNAIL}}"'+
					'onerror="imageError(this,' + "'noimg_prod_500x500.jpg'" + ')"'+
					'alt="{{:PROD_NM}}"' +
				'/>'+
			'{{/if}}' +
			'</div>' +
			'<div>' +
				'<div class="product-info">'+
					'{{if IS_EXPERIENCE}}'+
						'<div class="product-tag">'+
							'<i class="tag-product type1">체험매장상품</i>'+
						'</div>'+
					'{{/if}}'+
					'{{include tmpl="mobileProductListBadge"/}}' +
					'<a class="name"'+
						'href="{{:~getLMAppUrlM()}}/mobile/cate/PMWMCAT0004_New.do?CategoryID={{:CATEGORY_ID}}&ProductCD={{:PROD_CD}}"'+
						'data-gtm="M020"'+
						'data-cid={{:CATEGORY_ID}}'+
						'data-pid={{:PROD_CD}}' +
						'data-title={{:PROD_NM}}' +
					'>'+
							'{{:PROD_NM}}' +
					'</a>' +
					'{{include tmpl="mobilePriceWrap"/}}'+
					'<button type="button" class="basket"'+
						'data-method="basket"' +
						'data-gtm="M019"' +
						'data-is-sold-out="{{:~isSoldOut(SOUT_YN_REAL)}}"' +
						'data-prod-cd="{{:PROD_CD}}"' +
						'data-prod-type-cd="{{:ONLINE_PROD_TYPE_CD}}"' +
						'data-min-quantity="{{if MIN_ORD_PSBT_QTY <= 0}}1{{else}}{{:MIN_ORD_PSBT_QTY}}{{/if}}"' +
						'data-max-quantity="{{:MAX_ORD_PSBT_QTY}}"'+
						'data-category-id="{{:CATEGORY_ID}}"'+
						'data-prod-title="{{:PROD_NM}}"' +
						'data-is-manufacturing-product="{{:~getManuFacturingProduct(ONLINE_PROD_TYPE_CD)}}"'+
						'data-period-delivery-yn="Y"'+
						'title="정기배송 담기">'+
						'<i class="icon-common-cartEmp"></i> 정기배송 담기' +
					'</button>'+
				'</div>'+
			'</div>'+
		'</div>' +
	'</li>' +
	'{{/for}}' +
	'<li class="ingpageloading">'+
		'<h2 class="title-basic nomore">마지막 상품입니다.</h2>'+
	'</li>'
);
$.templates('mobilePeriodShippingPackageList',
	'{{for contents}}' +
		'<p class="shipping-packbig">' +
			'<a href="{{:~getLMAppUrlM()}}/mobile/delivery/package.do?peri_deli_pkg_no={{:PERI_DELI_PKG_NO}}&SITELOC={{if #index === 2 || #index < 8}}OI00{{: #index + 2}}{{else}}OI0{{: #index + 2}}{{/if}}&returnURL=/mobile/delivery/main.do" data-gtm="M069">' +
				'<img src="{{:~getImageContentBase()}}/{{:MOBL_MAI_BNR_IMG}}" alt="{{:PERI_DELI_PKG_NM}}" onerror="imageError(this,' + "'noimg_prod_204x204.jpg'" + ')">' +
			'</a>' +
		'</p>' +
	'{{/for}}' +
	'<span class="ingpageloading">' +
		'<h2 class="title-basic">마지막 리스트입니다</h2>' +
	'</span>'
);

$.templates('mobileFilterLayer',
	'<article class="layerpopup-type1 {{:~className(layerClass)}}">' +
		'<form>'+
			'<header class="header">'+
				'{{if title}}' +
				'<h3 class="title">{{:title}}</h3>' +
				'{{/if}}'+
				'<button type="submit" class="icon-close js-close" data-type="close">닫기</button>' +
			'</header>'+
			'{{if allText}}' +
				'<button type="submit" class="btn-form-color4-fullw search-plan" data-type="all">{{:allText}}</button>' +
			'{{/if}}' +
			'<div class="wrap-cateselect {{:~className(wrapClass)}}">'+
				'<div class="sorting-check">'+
				'{{for list}}' +
				'<label>'+
					'<input type="checkbox" name="item" value="{{:MINOR_CD}}">'+
					'<em>{{:CD_NM}}</em>' +
				'</label>'+
				'{{/for}}' +
				'</div>' +
			'</div>' +
			'<div class="acenter">'+
				'<button type="submit" data-type="submit" class="{{:~className(btnSubmitStyle)}}">'+
				'{{:submitText}}' +
				'</button>' +
			'</div>'+
		'</form>'+
	'</article>'
);

$.templates('mobileSearchFilterCheckbox',
	'<li>'+
		'<label>'+
			'<input type="checkbox"' +
				'class="chk-form"' +
				'data-gtm="{{:gtmNumber}}"' +
				'data-title="{{:title}}"' +
				'data-value="{{:name}}"' +
				'name="{{:type}}"' +
				'value="{{:value}}"' +
				'<span>{{:name}}</span>' +
		'</label>'+
	'</li>'
);

$.templates('mobileSearchFilter',
	'<aside id="globalSearchLayer">' +
		'<header class="header">'+
			'<h1 class="title">상세검색</h1>' +
			'<span class="search-desc">'+
				'전체 <em class="number">{{:totalCount}}</em>개'+
			'</span>' +
			'<button type="button"' +
					'data-id="btnreset"' +
					'class="btn-common-color4"'+
					'data-gtm="{{:gtmNumberForButtonReset}}">초기화</button>' +
			'<button type="button"' +
					'data-id="btnclose"'+
					'class="icon-close"'+
					'data-gtm="{{:gtmNumberForButtonClose}}">닫기</button>' +
		'</header>' +
		'<section class="wrap-inner-scroll">'+
			'<dl class="shipping-faq-list">'+
				'{{if deliveryList.length > 0}}' +
				'<dt data-id="btnaccordion">배송</dt>' +
				'<dd>'+
					'<ul class="ps-list">'+
					'{{for deliveryList}}'+
						'{{include tmpl="mobileSearchFilterCheckbox"/}}' +
					'{{/for}}' +
					'</ul>'+
				'</dd>'+
				'{{/if}}' +
				'{{if benefitList.length > 0}}' +
				'<dt data-id="btnaccordion">혜택</dt>' +
				'<dd>'+
					'<ul class="ps-list">'+
					'{{for benefitList}}' +
						'{{include tmpl="mobileSearchFilterCheckbox"/}}' +
					'{{/for}}' +
					'</ul>'+
				'</dd>'+
				'{{/if}}' +
			'</dl>'+
		'</section>' +
		'<div class="searchfilter-select-apply">'+
			'<button type="button"' +
					'class="btn-common-color6 fullw"' +
					'data-id="btnsearch"'+
					'data-gtm="{{:gtmNumberForApply}}">'+
						'선택적용' +
					'</button>' +
		'</div>' +
	'</aside>' +
	'<div class="global-mask" style="display:none"></div>'
);

$.templates('mobileProductList',
	'{{if products !== null && products.length > 0}}' +
		'{{for products}}'+
			'<li data-panel="product">' +
				'<div class="product-wrap">' +
					'{{include tmpl="mobileProductItem" /}}' +
				'</div>'+
			'</li>'+
		'{{/for}}'+
		'{{if count <= 30}}' +
			'<li class="list-empty">' +
				'{{include tmpl="emptyProductList" /}}' +
			'</li>' +
		'{{/if}}' +
	'{{else}}' +
		'<li class="list-empty">' +
			'{{include tmpl="emptyProductList" /}}' +
		'</li>' +
	'{{/if}}'
);

$.templates('emptyProductList',
	'{{if count > 0}}' +
		'마지막 상품입니다.' +
	'{{else}}' +
		'{{if benefitChkList !== "" || deliveryView !== "" }}' +
			'선택하신 조건에 맞는 상품이 없습니다.' +
		'{{else}}' +
			'상품이 없습니다.' +
		'{{/if}}' +
	'{{/if}}'
);

$.templates('mobileProductItem',
	'{{include tmpl="mobileProductImage" /}}' +
	'<div class="product-info">' +
		'{{if isExperience}}'+
			'<div class="product-tag">'+
				'<i class="tag-product type1">체험매장상품</i>'+
			'</div>'+
		'{{/if}}'+
		'{{include tmpl="mobileIconInfo" /}}' +
		'<a href="#"' +
			'class="name"' +
			'data-gtm="M033"' +
			'data-category-id="{{:category.id}}"' +
			'data-click-url="{{:CLICK_URL}}"' +
			'data-prod-cd="{{:code}}">' +
			'{{:name}}' +
		'</a>' +
		'<div class="price-wrap">' +
			'<div class="price">' +
				'{{if currSellPrc > promoMaxVal}}'+
					'<strong class="point1">{{:~saleRatio(promoMaxVal, currSellPrc)}}%</strong>'+
					'<div class="number">' +
						'<em class="sale">{{:~numberFormat(promoMaxVal)}}</em>' +
						'<i>원{{if ~isDeal(onlineProdTypeCd)}}~{{/if}}</i>' +
					'</div>' +
					'<div class="number">' +
						'<em>{{:~numberFormat(currSellPrc)}}</em>' +
						'<i>원</i>' +
					'</div>' +
				'{{else}}'+
					'<div class="number">' +
						'<em>{{:~numberFormat(currSellPrc)}}</em>' +
						'<i>원{{if ~isDeal(onlineProdTypeCd)}}~{{/if}}</i></i>' +
					'</div>' +
				'{{/if}}'+
			'</div>' +
		'</div>' +
		'<div class="review-wrap">' +
			'{{if recommPoint && (recommPoint > 6 && recommCount > 0)}}' +
				'<div class="review">' +
					'{{include tmpl="mobileStarPoint" /}}' +
					'<span class="review-cases">'+
						'(<em>{{:~numberFormat(recommCount)}}</em>)' +
					'</span>' +
				'</div>' +
			'{{else recommRecomPnt && (recommRecomPnt > 6 && recommCnt > 0)}}' +
				'<div class="review">' +
					'{{include tmpl="mobileStarPoint" /}}' +
					'<span class="review-cases">'+
						'(<em>{{:~numberFormat(recommCnt)}}</em>)' +
					'</span>' +
				'</div>' +
			'{{/if}}' +
			'<div class="cartInput">' +
				'{{include tmpl="mobileBasketButton" /}}' +
			'</div>' +
		'</div>' +
	'</div>'
);

$.templates('mobileProductImage',
	'<div class="product-img">' +
		'{{if ~isAdult(adlYn)}}' +
			'<img src="{{:~lmCdnV3RootPath()}}/images/layout/m-goods-adult.png"' +
				'onerror="showNoImage(this,500,500)"'+
				'alt="성인인증이 필요한 상품입니다."/>'+
		'{{else ~isSoldOut(soutYn,onlineProdTypeCd)}}' +
			'<span class="prod-sout">' +
				'<img src="{{:~lmCdnV3RootPath()}}/images/layout/img_soldout_500x500.png"' +
					'onerror="showNoImage(this,500,500)"'+
					'alt="상품 준비중"' +
					'class="thumb"/>' +
			'</span>' +
			'{{if "Y" == wideYn}}' +
				'<img src="{{:~getItemWideImagePath(mdSrcmkCd)}}"' +
				'onerror="this.style.display=\'none\'"'+
				'class="wide" alt="{{:name}}}"/>' +
			'{{/if}}' +
			'<img src="{{:~getItemImagePath(mdSrcmkCd,500,1)}}"' +
				'onerror="showNoImage(this,500,500)"'+
				'alt="{{:name}}}"/>' +
		'{{else}}' +
			'{{if "Y" == wideYn}}' +
				'<img src="{{:~getItemWideImagePath(mdSrcmkCd)}}"' +
				'onerror="this.style.display=\'none\'"'+
				'class="wide" alt="{{:name}}}"/>' +
			'{{/if}}' +
			'<img src="{{:~getItemImagePath(mdSrcmkCd,500,1)}}"' +
				'onerror="showNoImage(this,500,500)"'+
				'alt="{{:name}}}"/>' +
		'{{/if}}' +
	'</div>'
);
$.templates('mobileIconInfo',
	'<div class="badge">' +
		'{{if freeDelivery === "Y" && (onlineProdTypeCd && onlineProdTypeCd !== "08")}}' +
			'<div class="type01">' +
				'무료배송'+
			'</div>' +
		'{{/if}}' +
		'{{if ~isDeal(onlineProdTypeCd) === false}}' +
			'{{if ~isShowDelivery(onlineProdTypeCd,deliType)}}' +
				'<div class="type01">'+
					'{{:~deliveryIcons(onlineProdTypeCd,deliType,deliType2).name}}' +
				'</div>' +
			'{{/if}}' +
		'{{/if}}'+
		'{{if periDeliYn === "Y"}}' +
			'<div class="type01">'+
				'정기배송' +
				'{{if periCoupon != null}}' +
					'[{{: periCoupon}} 할인]' +
				'{{/if}}'+	
			'</div>' +
		'{{/if}}'+
		'{{if ~promotions(promoProdIcon).length > 0}}' +
			'{{for ~promotions(promoProdIcon)}}' +
				'<div class="type02">' +
					'{{include tmpl="mobilePromotionBadge" /}}'+
				'</div>'+
			'{{/for}}' +
		'{{/if}}' +
		'{{if isExpressDeliveryArea !== null && expressDeliYn !== null}}' +
			'{{if isExpressDeliveryArea === "Y" && expressDeliYn === "Y"}}' +
				'<div name="expressType" class="type03">바로배송</div>' +
			'{{/if}}'+
		'{{/if}}'+
	'</div>' +
	'{{if ~isDeal(onlineProdTypeCd) === false}}' +
		'{{if ~isGiftMall(onlineProdTypeCd,deliType)}}' +
			'<div class="promotion-holi">' +
				'<i class="{{:~deliveryIcons(onlineProdTypeCd,deliType).css}}">'+
					'{{:~deliveryIcons(onlineProdTypeCd,deliType).name}}'+
				'</i>'+
			'</div>'+
		'{{/if}}'+
	'{{/if}}'
);
$.templates('mobileStarPoint',
	'<span class="starpoint-layer"' +
		'id="btnReviews">' +
		'{{if recommPoint}}' +
		'<em class="starpoint-cover" style="width:{{:recommPoint * 10}}%"></em>' +
		'{{else recommRecomPnt}}' +
		'<em class="starpoint-cover" style="width:{{:recommRecomPnt * 10}}%"></em>' +
		'{{/if}}' +
	'</span>'
);
$.templates('mobileBasketButton',
	'<button type="button"' +
		'class="basket {{if ~isSoldOut(soutYn,onlineProdTypeCd)}}disabled{{/if}}"' +
		'data-method="basket"' +
		'data-is-sold-out="{{:~isSoldOut(soutYn,onlineProdTypeCd)}}"' +
		'data-option-yn="{{:optionYn}}"' +
		'data-prod-cd="{{:code}}"' +
		'data-prod-type-cd="{{:onlineProdTypeCd}}"' +
		'data-min-quantity="{{if minOrdPsbtQty <= 0}}1{{else}}{{:minOrdPsbtQty}}{{/if}}"' +
		'data-max-quantity="{{:maxOrdPsbtQty}}"' +
		'data-category-id="{{:category.id}}"' +
		'data-prod-title="{{:name}}"' +
		'data-is-manufacturing-product="{{if onlineProdTypeCd ===' + '"02"|| onlineProdTypeCd ===' + '"03"' + '}}true{{else}}false{{/if}}"' +
		'title="장바구니 담기"' +
		'{{if ~isSoldOut(soutYn,onlineProdTypeCd)}}disabled{{/if}}>' +
		'<i class="icon-common-cartEmp"></i>' +
	'</button>'
);
$.templates('mobilePromotionBadge',
	'{{if orderNo === "04"}}'+
		'{{:iconDesc}}' +
	'{{else iconType === "won"}}' +
		'<em class="won">{{:~numberFormat(iconDesc)}}</em> 할인' +
	'{{else iconType === "discount"}}' +
		'<em class="number">{{:iconDesc}}</em> 할인' +
	'{{else iconType === "bundle"}}' +
		'<em class="plus">{{:iconDesc}}</em>' +
	'{{else iconType === "type6-1"}}' +
		'<em class="number">{{:iconDesc}}</em> 다둥이' +
	'{{else iconType === "type6-2"}}' +
		'<em class="won">{{:~numberFormat(iconDesc)}}</em> 다둥이' +
	'{{else iconType === "type4-1"}}' +
		'<em class="number">{{:iconDesc}}</em> L.POINT' +
	'{{else iconType === "type4-2"}}' +
		'<em class="won">{{:~numberFormat(iconDesc)}}</em> L.POINT' +
	'{{else iconType !== "type12"}}' +
		'{{:iconNm}}' +
	'{{/if}}'
);
$.templates('mobileRecentlyProduct',
		'{{if recentlyPurchasedProducts != null}}' +
			'<h2 class="title">최근 <b>구매 상품</b></h2>' +
			'<nav class="fav-product swiper-inner-scroll">' +
				'{{for recentlyPurchasedProducts}}' +
					'<a href="{{:~getLMAppUrlM()}}/mobile/cate/PMWMCAT0004_New.do?CategoryID={{:categoryId}}&ProductCD={{:prodCd }}" data-gtm= "M022" class= "item {{if ~isDealProductItem(ONLINE_PROD_TYPE_CD_DEAL) and stockQty lt 1 }} soldout {{/if}}">' +
						'<div class="thumbnail">' +
							'<img src="{{:~getItemImagePath(mdSrcmkCd, 240, 1)}}" onerror="javascript:showNoImage(this,240,240)">' +
						'</div>' +
						'<p class="desc">{{:prodNm }}</p>' +
					'</a>' +
				'{{/for}}' +
				'<a href="javascript:;" class="more" data-gtm="M021" onclick="rnbOpen(this);return false;">더보기</a>' +
			'</nav>' +
		'{{/if}}'
);
$.templates('mobilePersonalMcoupons',
		'{{if personalMcoupons != null}}' +
			'<h2 class="title"><b>{{:memberName }}</b>님을 위한 <strong class="point1">M쿠폰</strong> 추천상품</h2>' +
			'<nav class="fav-product swiper-inner-scroll">' +
				'{{for personalMcoupons}}' +
					'<a href="{{:~getLMAppUrlM()}}/mobile/evt/mobileEventMCouponDetail.do?couponId={{:COUPON_ID}}" class= "mcouponDetailItem vertical">' +
						'<div class="wrap-mcoupon-registered">' +
							'<div class="mcoupon-registered">' +
								'<div class="cateimage">' +
									'<img src="{{:COUPON_IMG_URL}}" onerror="javascript:showNoImage(this,500,500)">' +
								'</div>' +
								'<div class="desc">' +
									'<p class="tit">' +
										'<span class="number">' +
											'{{:COUPON_DC_AMT }} ' +
											'{{if DC_UNIT_CD === "01"}} <span class="number-percent">원</span> ' +
											'{{else DC_UNIT_CD === "02"}} <span class="number-percent">%</span> ' +
											'{{else DC_UNIT_CD === "05"}} <span class="number-percent">배</span> ' +
											'{{else DC_UNIT_CD === "06"}} <span class="number-percent">P</span> ' +
											'{{/if}}' +
										'</span>' +
									'</p>' +
									'<ul class="desc-list">' +
										'<li>{{:COUPON_NM }}</li>' +
									'</ul>' +
								'</div>' +
							'</div>' +
						'</div>' +
					'</a>' +
				'{{/for}}' +
				'<a href="{{:~getLMAppUrlM()}}/mobile/evt/eventMain.do?tabId=2" class="more">더보기</a>' +
			'</nav>' +
		'{{/if}}'
	);
$.templates('mobilePersonalMcouponsNew',
		'{{if personalMcoupons != null}}' +
			'<h2 class="title"><b>{{:memberName }}</b>님을 위한 <strong class="point1">M쿠폰</strong> 추천상품</h2>' +
			'<nav class="fav-product swiper-inner-scroll">' +
				'{{for personalMcoupons}}' +
					'<a href="{{:~getLMAppUrlM()}}/mobile/evt/mobileEventMCouponDetail.do?couponId={{:COUPON_ID}}" class= "mcoupon-item">' +
						'<div class="thumbnail">' +
							'<img src="{{:COUPON_IMG_URL}}" onerror="javascript:showNoImage(this,500,500)">' +
						'</div>' +
						'<div class="conts">' +
							'<p class="desc">{{:COUPON_NM }}</p>' +
							'<p class="benefit">' +
								'<span class="number">{{:COUPON_DC_AMT }}' +
									'<sub>' +
										'{{if DC_UNIT_CD === "01"}} <span class="number-percent">원</span> ' +
										'{{else DC_UNIT_CD === "02"}} <span class="number-percent">%</span> ' +
										'{{else DC_UNIT_CD === "05"}} <span class="number-percent">배</span> ' +
										'{{else DC_UNIT_CD === "06"}} <span class="number-percent">P</span> ' +
										'{{/if}}' +
									'</sub>' +
								'</span>' +
								'{{if SCP_COUPON_TYPE_CD == "A" || SCP_COUPON_TYPE_CD == "B" || SCP_COUPON_TYPE_CD == "J" || SCP_COUPON_TYPE_CD == "K"}} 할인 ' +
								'{{else SCP_COUPON_TYPE_CD == "C" || SCP_COUPON_TYPE_CD == "D"}} 적립' +
								'{{/if}}' +
							'</p>' +
						'</div>' +
						'<div class="period">{{:USE_START_DY}} ~ {{:USE_END_DY}}</div>' +
					'</a>' +
				'{{/for}}' +
				'<a href="{{:~getLMAppUrlM()}}/mobile/evt/eventMain.do?tabId=2" class="more">더보기</a>' +
			'</nav>' +
		'{{/if}}'
);
$.templates('mobileMcouponDetail',
	'{{for prodInfo}}' +
		'<div class="list-applycoupon">' +
			'{{if MALL_DIVN_CD === "00002"}}' +
				'<div onclick="goProductDetailToy(\'{{:CATEGORY_ID}}\', \'{{:PROD_CD}}\');return false;">' +
			'{{else}}' +
				'<div onclick="goProductDetailMobile(\'{{:CATEGORY_ID}}\', \'{{:PROD_CD}}\',\'N\');return false;">' +
			'{{/if}}' +
				'<img src="{{:~root.imgPath}}/{{:MD_SRCMK_CD.substring(0,5)}}/{{:MD_SRCMK_CD}}_1_220.jpg" alt="{{:PROD_NM}}" >' +
			'</div>' +
			'<div class="desc">' +
				'{{if MALL_DIVN_CD === "00002"}}' +
					'<span class="title" onclick="goProductDetailToy(\'{{:CATEGORY_ID}}\', \'{{:PROD_CD}}\');return false;">{{:PROD_NM}}</span>' +
				'{{else}}' +
					'<span class="title" onclick="goProductDetailMobile(\'{{:CATEGORY_ID}}\', \'{{:PROD_CD}}\',\'N\');return false;">{{:PROD_NM}}</span>' +
				'{{/if}}' +
				'<div class="price-wrap">' +
					'<div class="price">' +
						'{{if CURR_SELL_PRC > PROM_MAX_VAL}}' +
							'<div class="number">' +
								'<span>판매가 </span>' +
								'<em>{{:CURR_SELL_PRC}}</em><i>원</i>' +
							'</div>' +
							'<div class="number">' +
								'<span class="sale">최저가 </span>' +
								'<em class="sale">{{:PROM_MAX_VAL}}</em><i class="sale">원</i>' +
								'<button type="button" title="레이어팝업 열림" class="icon-common-toggle" data-benefit="{{:PROM_MAX_VAL_LIST}}" data-benefit-type="small"/>' +
							'</div>' +
						'{{else}}' +
							'<div class="number">' +
								'<span class="sale">판매가 </span>' +
								'<em class="sale">{{:CURR_SELL_PRC}}</em><i class="sale">원</i>' +
							'</div>' +
						'{{/if}}' +
					'</div>' +
				'</div>' +
				'<span class="benefit">M쿠폰 {{:~root.discountAmount}}</span>' +
			'</div>' +
		'</div>' +
	'{{/for}}'
);
$.templates('mobileHottime',
		'{{if CornerMapWithContent["004_003"]!= null}}' +
			'{{for CornerMapWithContent["004_003"].set["003"] }}' + //핫타임
				'{{:HTML}}' +
			'{{/for}}' +
		'{{else CornerMapWithContent["004_002"]!= null}}'+
			'{{for CornerMapWithContent["004_002"].set["002"] }}' + //핫타임예고
			'{{:HTML}}' +
			'{{/for}}' +
		'{{/if}}'
);
$.templates('mobileCounselList',
	'{{if counsels != null}}' +
		'{{for counsels}}' +
			'<dl>' +
				'<dt name="statusPanel">' +
					'<p>' +
						'{{if REPLY_YN == "Y"}}' +
							'<i class="icon-tag-faq2">답변완료</i>' +
						'{{else}}' +
							'<i class="icon-tag-faq1">답변준비</i>' +
						'{{/if}}' +
						'<em class="title-faq">{{:TITLE}}</em>' +
						'{{if ATCH_FILE_ID != null}}' +
							'<i class="icon-sprite-fileattach">첨부파일</i>' +
						'{{/if}}' +
					'</p>' +
					'<p class="namecard">' +
						'<em>{{:CUST_QST_DIVN_NM}}</em> <time class="number">{{:~formatTimeStamp(REG_DATE, "yyyy.MM.dd")}}</time>' +
					'</p>' +
				'</dt>' +
				'<dd>' +
					'<p class="faq-q">{{:CONTENT}}</p>' +
					'{{if REPLY_YN == "Y"}}' +
						'<p class="faq-a">{{:~replaceCounselContentCharacter(REPLY_CONTENT)}}</p>' +
					'{{/if}}' +
				'</dd>' +
			'</dl>' +
		'{{/for}}' +
	'{{else}}' +
		'<p class="list-empty">상담/문의 내역이 없습니다.<br>롯데마트몰/토이저러스는 언제나 고객님의<br>문의에 성심껏 안내해 드리겠습니다.</p>' +
	'{{/if}}'
);
$.templates('mobileMoreButton',
	'<div id="moreButtonWrapper" class="wrap-button">' +
		'<button id="btnMore" type="button" class="btn-form-color4" data-current-page="{{:currentPage}}">' +
			'<em class="number-point">{{:rowPerPage}}</em> 개 더보기 <i class="icon-more"></i>' +
		'</button>' +
	'</div>'
);
$.templates('basketItemSearcher',
	'<div class="wrap-columns">' +
		'<div class="right">' +
			'<button name="btnAply" type="button" class="btn-form-type1">선택 상품 적용</button>' +
		'</div>' +
	'</div>' +

	'<ul name="itemContainer" data-template-id="searcherOrderItem" class="complain-list">' +
	'</ul>' +

	'{{include tmpl="moreButton" /}}'
);
$.templates('orderItemSearcher',
	'<div class="wrap-columns">' +
		'<div class="left">' +
			'<div class="wrap-radiotab">' +
				'<input type="radio" id="oneMonth" name="period" checked="checked" value="1">' +
				'<label for="oneMonth">1개월</label>' +
				'<input type="radio" id="twoMonth" name="period" value="2">' +
				'<label for="twoMonth">2개월</label>' +
				'<input type="radio" id="threeMonth" name="period" value="3">' +
				'<label for="threeMonth">3개월</label>' +
				'<input type="radio" id="sixMonth" name="period" value="6">' +
				'<label for="sixMonth">6개월</label>' +
			'</div>' +
		'</div>' +

		'<div class="right">' +
			'<button name="btnAply" type="button" class="btn-form-type1">선택 상품 적용</button>' +
		'</div>' +
	'</div>' +

	'<div name="container" data-template-id="searcherOrder">' +
	'</div>' +
	'{{include tmpl="moreButton" /}}'
);
$.templates('searcherOrder',
	'{{if orders != null}}' +
		'{{for orders}}' +
			'<div name="orderWrapper">' +
				'<div class="complain-header">' +
					'<label class="order-number"><span name="orderNumberWrapper" class="radio-data"><input id="showOrderItem{{:ORDER_ID}}" name="orderNumber" type="radio" value="{{:ORDER_ID}}" title="{{:ORDER_ID}} 선택"/></span> {{:ORDER_ID}}</label>' +
					'<p>주문일: <em class="number-point">{{:~substr(ORD_DY, 0, 4)}}.{{:~substr(ORD_DY, 4, 2)}}.{{:~substr(ORD_DY, 6, 2)}}</em></p>' +
					'<p>주문금액: <em class="number-point">{{:~numberFormat(TOT_SELL_AMT)}}</em></p>' +
					'<div class="wrap-button">' +
						'<button name="btnDelete" type="button" class="btn-form-color2" data-type="all">삭제</button>' +
					'</div>' +
				'</div>' +
				'<ul name="orderItemContainer" class="complain-list" data-template-id="searcherOrderItem">' +
				'</ul>' +
			'</div>' +
		'{{/for}}' +
	'{{/if}}'
);
$.templates('searcherOrderItem',
	'{{if items != null}}' +
		'{{for items}}' +
			'<li name="itemWrapper">' +
				'<input type="hidden" name="productCode" value="{{:PROD_CD}}" />' +
				'<span name="productCodeWrapper" class="check-data check-data-type1"><input type="checkbox" name="productCode" value="{{:PROD_CD}}" title="{{:PROD_NM}} 선택" /></span>' +
				'<div class="conts">' +
					'<div class="thumbnail mall-type{{:~substr(MALL_DIVN_CD, 4, 1)}}">' +
						'{{if ~isAdult(ADL_YN)}}' +
							'<img src="{{:~getAdultImagePath() }}" onerror="javascript:this.src=\'{{:~getNoImagePath()}}\';" alt="{{:PROD_NM}}" />' +
						'{{else}}' +
							'<img src="{{:~getItemImagePath(MD_SRCMK_CD, 120, 1)}}" onerror="javascript:this.src=\'{{:~getNoImagePath()}}\';" alt="{{:PROD_NM}}" />' +
						'{{/if}}' +
					'</div>' +
					'<p class="title">{{:PROD_NM}}</p>' +
					'{{if OPTION_YN == "Y"}}' +
						'<p class="option">옵션: {{:OPTION_NM}}</p>' +
					'{{/if}}' +
				'</div>' +
				'<p class="qty">수량: {{:QTY}}개</p>' +
				'<p class="price">가격: <strong>{{:~numberFormat(TOTAL_AMT)}}</strong>원</p>' +

				'<div class="wrap-button">' +
					'<button name="btnDelete" type="button" class="btn-form-color2">삭제</button>' +
				'</div>' +
			'</li>' +
		'{{/for}}' +
	'{{/if}}'
);
$.templates('moreButton',
	'<button name="btnPagination" type="button" class="btn-form-fullw" data-current-page="{{:currentPage}}">더보기 <i class="icon-more"></i></button>'
);
$.templates('counselAttachFileWrapper',
	'<div name="attachFileWrapper" class="wrap-file-attach">' +
		'<div class="area-input">' +
			'<input type="text" name="filePath" readonly="readonly" placeholder="첨부할 이미지파일을 선택해주세요.">' +
			'<button type="button" name="btnRemoveFile" class="delete" style="display:none;">삭제</button>' +
		'</div>' +
		'<span class="btn-form-color3">' +
			'<input type="file" name="file" />첨부' +
		'</span>' +
		'<button type="button" name="btnDelete" class="btn-form-color8">삭제</button>' +
	'</div>'
);
$.templates('mobileCounselAttachFileWrapper',
	'<div class="area-input" name="previewImageWrap">' +
		'<input type="file" name="file">' +
		'<button type="button" class="icon-prod-delete" name="btnDelete">삭제</button>' +
	'</div>'
);
$.templates('mobileCounselPreviewImageWrap',
	'<input type="hidden" name="fileSize" value="{{:fileSize }}" />' +
	'<input type="hidden" name="encodeFileString" value="{{:encodeFileString }}" />' +
	'<img src="{{:encodeFileString}}" class="image" name="filePreview" />'
);
$.templates('mobileScanProductReviews',
		'{{for reviews}}' +
			'<li class="user-reply {{if file.atchFileId != null}}has-image{{/if}} {{if !useYn}}blind-reply{{/if}}">' +
				'<header class="reply-header">' +
					'<div class="reply-header-top">' +
						'{{if type == "EXPERIENCE"}}' +
							'<i class="icon-tag-color5">체험후기</i>' +
						'{{else}}'+
							'<i class="icon-tag-color4">구매후기</i>' +
						'{{/if}}' +
						'<span class="reply-id">{{:userName}}</span>' +
						'<span class="reply-date">{{:regDtByString}}</span>' +
					'</div>' +
					'<div class="reply-header-bottom">' +
						'<span class="reply-score"><em class="reply-score-inner" style="width:{{:grade*10}}%;">평점 {{:grade}}점</em></span>' +
						'{{if strNm != null}}' +
							'<span class="reply-branch">{{:strNm}}</span>' +
						'{{/if}}' +
					'</div>' +
				'</header>' +
				'<p class="reply-content">{{:contents}}</p>' +
				'{{if file.atchFileId != null}}' +
					'<div class="wrap-reply-img">' +
						'<a href="/mobile/popup/scan/products/{{:prodCd}}/reviews/{{:id}}/image.do">' +
							'<img src="{{:filePath}}" alt="상품평 이미지" onerror="javascript:showNoImage(this,500,500)">' +
						'</a>' +
					'</div>' +
				'{{/if}}' +
				'<div class="wrap-reply-btn">' +
					'<button type="button" name="recommButton" data-id="{{:id}}" data-type="{{:type}}" class="btn-prod-like {{if isRecommended}}active{{/if}}"><i class="icon-prod-thumb"></i>좋아요<em class="point2">{{:~numberFormat(recommCnt)}}</em></button>' +
					'<button type="button" class="btn-prod-toggle">열기/닫기</button>' +
				'</div>' +
				'{{if isShowButton}}' +
					'<div class="wrap-button">' +
						'<button type="button" name="modifyButton" data-id="{{:id}}" class="btn-prod-small-color11">수정</button>' +
						'<button type="button" name="delButton" data-id="{{:id}}" class="btn-prod-small-color8">삭제</button>' +
					'</div>' +
				'{{/if}}' +
			'</li>' +
		'{{/for}}'
);
$.templates('emptyMobileScanProductReviews',
		'<p class="list-empty">' +
			'등록된 상품평이 없습니다.<br>고객님의 소중한 체험 후기를 등록해보세요.' +
		'</p>'
);

$.templates('mobileSpecialMainWrapper',
	'{{for mainSpecial}}' +
	'<article class="wrap-special-content"' +
				'data-category-id="{{:category}}">' +
		'{{include tmpl="mobileSpecialMainTitleWrapper" /}}' +
		'{{if relatedProductList.length > 0}}' +
			'<ul class="swiper-inner-scroll">' +
				'{{include tmpl="mobileSpecialMainRelatedProductList" ~mainLink=link ~totalLength=relatedProductList.length /}}' +
			'</ul>' +
		'{{/if}}'+
	'</article>' +
	'{{/for}}'
);

$.templates('mobileSpecialMainTitleWrapper',
	'<h3 class="main-special-title">' +
		'<a href="{{:link}}" class="wrap-special-title">'+
			'{{:title}}' +
			'<em class="main-special-desc">'+
				'{{:explain}}' +
			'</em>' +
			'<img src="{{:imageUrl}}" alt="{{:title}}">'+
		'</a>' +
	'</h3>'
);

$.templates('mobileSpecialMainRelatedProductList',
	'{{for relatedProductList}}' +
		'<li class="main-special-list">' +
			'<a href="{{:~getLMAppUrlM()}}{{:link}}" class="main-special-prod">'+
				'<img src="{{:~getSimageStaticProductImagePath(mdSrcmkCd, 200)}}"'+
						'class="main-special-img"'+
						'alt="{{:title}}"/>' +
				'<span class="special-prod-name">{{:title}}</span>' +
			'</a>' +
		'</li>' +
		'{{if #index + 1 === ~totalLength}}' +
		'<li class="main-special-more">'+
			'<a href="{{:~mainLink}}" class="main-special-more-btn">' +
				'<i class="icon-special-more"></i>' +
				'<span class="main-special-more-txt">더보기</span>'+
			'</a>'+
		'</li>' +
		'{{/if}}' +
	'{{/for}}'
);