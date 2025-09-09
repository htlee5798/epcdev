(function(root, factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD. Register as an anonymous module.
		define([], factory);
	} else if (typeof module === 'object' && module.exports) {
		// Node. Does not work with strict CommonJS, but
		// only CommonJS-like environments that support module.exports,
		// like Node.
		module.exports = factory();
	} else {
		// Browser globals (root is window)
		root.returnExports = factory();
	}
})(typeof self !== 'undefined' ? self : this, function() {

$.views.helpers({
	isMobileHotListInnerBanner: function (index) { //핫콕 추천 영역 노출용
		return index === 8 || index === 13 || index === 3;
	},
	isMobileHotListInnerBannerNew: function (index) { //핫콕 추천영역외 5개카테고리 노출용
		return index === 3 || index === 8 ;
	},
	promotionTypes: function (type, discountContent) {
		var promotions = [{
			type: '40',
			name: 'promotions40',
			icons: 'icon-badge-type40'
		}, {
			type: '39',
			name: 'promotions39',
			icons: 'icon-badge-type39'
		}, {
			type: '38',
			name: 'promotions38',
			icons: 'icon-badge-type38'
		}, {
			type: '37',
			name: 'promotions37',
			icons: 'icon-badge-type37'
		}, {
			type: '36',
			name: 'promotions36',
			icons: 'icon-badge-type36'
		}, {
			type: '35',
			name: 'promotions35',
			icons: 'icon-badge-type35'
		}, {
			type: '34',
			name: 'promotions34',
			icons: 'icon-badge-type34'
		}, {
			type: '33',
			name: 'promotions33',
			icons: 'icon-badge-type33'
		}, {
			type: '32',
			name: 'promotions32',
			icons: 'icon-badge-type32'
		}, {
			type: '31',
			name: 'promotions31',
			icons: 'icon-badge-type31'
		}, {
			type: '30',
			name: 'promotions30',
			icons: 'icon-badge-type30'
		}, {
			type: '29',
			name: 'promotions29',
			icons: 'icon-badge-type29'
		}, {
			type: '28',
			name: 'promotions28',
			icons: 'icon-badge-type28'
		}, {
			type: '27',
			name: 'promotions27',
			icons: 'icon-badge-type27'
		}, {
			type: '26',
			name: 'promotions26',
			icons: 'icon-badge-type26'
		}, {
			type: '25',
			name: 'promotions25',
			icons: 'icon-badge-type25'
		}, {
			type: '24',
			name: 'promotions24',
			icons: 'icon-badge-type24'
		}, {
			type: '23',
			name: 'promotions23',
			icons: 'icon-badge-type23'
		}, {
			type: '22',
			name: 'promotions22',
			icons: 'icon-badge-type22'
		}, {
			type: '21',
			name: 'promotions21',
			icons: 'icon-badge-type21'
		}, {
			type: '17',
			name: 'promotions17',
			icons: 'icon-badge-type17'
		}, {
			type: '16',
			name: 'promotions16',
			icons: 'icon-badge-type16'
		}, {
			type: '15',
			name: 'promotions15',
			icons: 'icon-badge-type15'
		}, {
			type: '11',
			name: discountContent ? $.utils.comma(discountContent) : '',
			icons: discountContent ? 'icon-badge-type11' : 'icon-badge-lpoint'
		}, {
			type: '10',
			name: discountContent ? discountContent : '',
			icons: discountContent ? 'icon-badge-type10' : 'icon-badge-lpoint'
		}, {
			type: '08',
			name: discountContent,
			icons: 'icon-badge-type8'
		}, {
			type: '07',
			name: '증정',
			icons: 'icon-badge-type7'
		}];

		var promotion = promotions.filter(function (v) {
			return v.type === type;
		})[0];

		if (!promotion) {
			return '';
		}

		return '<i class="' + promotion.icons + '">' + promotion.name + '</i>';
	},
	mobileTodayHotBenefits: function (type, content) {
		var benefits = [{
			type: '01',
			name: content ? $.utils.comma(content) + '원 할인' : ''
		}, {
			type: '02',
			name: content + '% 할인'
		}, {
			type: '04',
			name: '증정'
		}, {
			type: '05',
			name: '카드할인'
			/*
		}, {
				type: '07',
				name: '증정'
	 */
			/*  요청건으로 1+1 텍스트 미노출되도록 처리 by 20190201
			}, {
					type: '08',
					name: content
			*/
		}, {
			type: '09',
			name: '살수록더싸게'
		}];

		var benefit = benefits.filter(function (v) {
			return v.type === type;
		})[0];

		if (!benefit) {
			return '';
		}

		return '<i class="benefit type2">' + benefit.name + '</i>';
	},
	recommendRatingStyle: function (point) {
		return 'width:' + (point * 10) + '%';
	},
	recommendRatingCount: function (count) {
		return (count * 10) + '점';
	},
	saleRatio: function ( num1, num2 ) {
		return 100 - Math.ceil(num1 / num2 * 100);
	}
});

$.templates('mobileTodayHotList',
'{{if lists.length === 0}}' +
	'<p class="list-empty">카테고리에 할당된 상품이 없습니다.</p>' +
'{{else}}' +
	'{{for lists}}' +
		'{{if ~isMobileHotListInnerBanner(#index) }}' +
			'<div name="insertionBannerPanelbest"></div>' +
		'{{/if}}' +
		'{{if ~isMobileHotListInnerBannerNew(#index) }}' +
			'<div name="insertionBannerPanelfood"></div>' +
			'<div name="insertionBannerPanelkitchen"></div>' +
			'<div name="insertionBannerPanelhobbies"></div>' +
			'<div name="insertionBannerPanelsports"></div>' +
			'<div name="insertionBannerPanelliving"></div>' +
		'{{/if}}' +
		'<div name="koklist" class="hotkok-item{{if isSoldOut}} soldout{{/if}}">' +
			// '<div class="info">' +
			'{{include tmpl="mobileTodayHotListProductName" /}}' +
			'{{include tmpl="mobileTodayHotSales" /}}' +
			'{{include tmpl="mobileTodayHotBasketButton" /}}' +
			'{{include tmpl="mobileTodayHotBtnVideo" /}}' +
			'{{include tmpl="mobileTodayHotRecommend" /}}' +
			// '</div>' +
			// '{{include tmpl="mobileTodayHotSpecialBanner"/}}' +
		'</div>' +
	'{{/for}}' +
	'{{if code && code.MINOR_CD != 00}}' +
		'<div class="wrap-button hotkok">' +
			'<a href="/mobile/hot/{{:code.MINOR_CD}}/detail.do" class="btn-form-type1" data-code="{{:code.MINOR_CD}}">' +
				'<i class="icon-hotkokcate-{{:code.LET_1_REF}}">{{:code.CD_NM}}</i> ' +
				'<strong>{{:code.CD_NM}}상품</strong>' +
				'<i class="desc">더보기</i>' +
				'<i class="icon-more">바로가기</i>' +
			'</a>' +
		'</div>' +
	'{{/if}}' +
'{{/if}}'
);

$.templates('mobileTodayHotListProductName',
	'<div class="thumbnail">' +
		'{{include tmpl="mobileTodayHotThumbnailImage" /}}' +
	'</div>' +
	'<div class="promotion">' +
		'{{:~promotionTypes(dispDcType, dispDcContent)}}' +
		'{{:~promotionTypes(dispDcType2, dispDcContent2)}}' +
	'</div>' +
	'<div class="wrap-benefit">' +
		'{{include tmpl="mobileTodayHotDelivery" /}}' +
		'{{include tmpl="mobileTodayHotBenefits" /}}' +
		'{{if "Y" == expressDeliYn && isExpressDeliveryArea}}'+
			'<i class = "benefit type3">바로배송</i>'+
		'{{/if}}'+
	'</div>' +
	'<a class="title" name="productName" href="{{:~getLMAppUrlM()}}/mobile/cate/PMWMCAT0004_New.do?CategoryID={{:category.id}}&ProductCD={{:code}}&SITELOC=OB015{{if #getIndex() === 0 && catDivnCd === "00"}}&ad_sect=mhk{{/if}}" data-gtm="M102" data-title="{{:name}} {{if standardName}}(' + '{{:standardName}}' + '){{/if}}" data-cid="{{:category.id}}" data-pid="{{:code}}">' +
		'{{:name}}' +
		'{{if standardName}}({{:standardName}}){{/if}}' +
	'</a>' +
	'{{include tmpl="mobileLowestPrice" /}}'
);

$.templates('mobileLowestPrice',
	'{{if currSellPrc > promoMaxVal}}' +
	'<p class="price price-benefit {{if isDealProduct}}deal-product{{/if}}">' +
		'<strong class="point1">{{:~saleRatio(promoMaxVal, currSellPrc)}}%</strong>{{:~numberFormat(promoMaxVal)}}' +
	'</p>' +
	'{{/if}}' +
	'<p class="price {{if isDealProduct}}deal-product{{/if}}">' +
		'{{:~numberFormat(currSellPrc)}}' +
	'</p>'
);

$.templates('mobileTodayHotThumbnailImage',
	'{{if isAdultProduct}}' +
		'<img src="//simage.lottemart.com/v3/images/temp/lazyload-blank.png"' +
			'data-src="{{:~lmCdnV3RootPath()}}/images/layout/m-goods-adult.png"' +
			'class="lazy"' +
			'alt="19"/>' +
	'{{else}}' +
		'<img src="//simage.lottemart.com/v3/images/temp/lazyload-blank.png"' +
			'data-src="{{:itemImagePath}}"' +
			'class="lazy"' +
			'onerror="imageError(this,' + "'noimg_prod_500x500.jpg'" + ');this.removeAttribute(' + "'data-src'" + ')"' +
			'alt="name"/>' +
	'{{/if}}'
);

$.templates('mobileTodayHotDelivery',
	'{{if freeDeliveryTypeText}}' +
		'<i class="benefit type1">' +
			'{{:freeDeliveryTypeText}}' +
		'</i>&nbsp;' +
	'{{/if}}' +
	'{{if deliveryTypeText}}' +
		'<i class="benefit type1">' +
			'{{:deliveryTypeText}}'	+
		'</i>' +
	'{{/if}}' +
	'{{if periDeliYn === ' + "'Y'" + '}}' +
		'<i class="benefit type1">' +
		'정기배송 ' + '{{if periCoupon}}' +
			'[{{:periCoupon}} 할인]' +
		'{{/if}}' +
		'</i>&nbsp;' +
	'{{/if}}'
);

$.templates('mobileTodayHotBenefits',
	'{{:~mobileTodayHotBenefits(dispDcType, dispDcContent)}}' +
	'{{:~mobileTodayHotBenefits(dispDcType2, dispDcContent2)}}'
);

$.templates('mobileTodayHotSales',
	'{{if viewQty > 0}}' +
		'<p class="sales">{{:~numberFormat(viewQty)}}</p>' +
	'{{/if}}'
);

$.templates('mobileTodayHotRecommend',
	'{{if recommPoint > 6 && recommCount > 0}}' +
		'<div class="wrap-rating">' +
			'<div class="rating">' +
				'<i class="inner"' +
					'style="{{:~recommendRatingStyle(recommPoint)}}">'+
					'{{:~recommendRatingCount(recommCount)}}' +
				'</i>' +
			'</div>' +
			'({{:~numberFormat(recommCount)}})' +
		'</div>' +
	'{{/if}}'
);

$.templates('mobileTodayHotBasketButton',
	'<button type="button" class="basket{{if isSoldOut}} disabled{{/if}}" title="장바구니 담기"' +
		'data-gtm="M103"' +
		'data-method="basket"' +
		'data-is-sold-out="{{:isSoldOut}}"' +
		'data-option-yn="{{:optionYn}}"' +
		'data-prod-cd="{{:code}}"' +
		'data-prod-type-cd="{{:onlineProdTypeCd}}"' +
		'data-min-quantity="{{if minOrdPsbtQty <= 0}}1{{else}}{{:minOrdPsbtQty}}{{/if}}"' +
		'data-max-quantity="{{:maxOrdPsbtQty}}"' +
		'data-category-id="{{:category.id}}"' +
		'data-prod-title="{{:name}}"' +
		'data-is-manufacturing-product="{{:isManufacturingProduct}}"' +
		'{{if isSoldOut}} disabled{{/if}}>' +
	'</button>'
);

$.templates('mobileTodayHotBtnVideo',
	'{{if prodUrl}}' +
		'<button type="button" class="icon-common-goodsmovie goods-movie-pop" name="btnPlayYoutube"' +
			'data-gtm="M101"' +
			'data-video="{{:prodUrl}}?rel=0"' +
			'data-cid="{{:category.id}}"' +
			'data-pid="{{:code}}"' +
			'data-title="{{:name}} {{if standardName}}({{:standardName}}){{/if}}">' +
			'동영상보기' +
		'</button>' +
	'{{/if}}'
);

$.templates('mobileTodayHotSpecialBanner',
	'{{if specialBannerUrl}}' +
		'<a href="{{:specialBannerUrl}}" class="special-banner">' +
			'<i data-category-id="{{:specialBannerCategoryId}}"></i>' +
		'</a>' +
	'{{/if}}'
);


	var hash = '',
		loadingFooterClass = 'footer-loading',
		$footer = $('#footer'),
		$pageWrapper = '',
		$btnPlayYoutube = '',
		$productListWrapper = '',
		$kokBnr5 = '',
		$mobileHottime = '',
		$filter = '',
		filterHeight = 0,
		tabIndex = -1;

	var _init = function(obj) {
		hash = obj.hash;

		$pageWrapper = $('[data-pageid=' + hash + ']');
		$btnPlayYoutube = $pageWrapper.find('[name=btnPlayYoutube]');
		$productListWrapper = $pageWrapper.find('[name=productListWrapper]');
		$kokBnr5 = $pageWrapper.find('[name=kokBnr5]');
		$mobileHottime = $pageWrapper.find('[name=mobileHottime]');
		$filter = $pageWrapper.find('.hotkok-filter');
		filterHeight = $filter.outerHeight(true);

		var $activeFilter = $filter.find('.active');
		var type = getType($activeFilter);
		var contentId = getContentId($activeFilter);
		var tabNo = getTabNo($activeFilter);
		
		if ($productListWrapper.children().length !== 0) {
			startRemainTimeCounter();
		}

		bindEvent();
		bindScrollEvent();
		renderPersonalProducts();

		getTodayHotList(type, contentId, tabNo);

		$pageWrapper.find('[name^=kokBnr]').show();
		$pageWrapper.find('[name^=mobileHottime]').show();

		if ($btnPlayYoutube.length > 0) {
			sendVideoLoadPageInfoGA();
		}
	};
	
	function getTodayHotList(type, contentId, tabNo) {
		if (window.getHistoryBack && window.getHistoryBack()) {
			window.setHistoryBack(false);
			return;
		}

		var $wrapper = document.getElementById(contentId);
		var $docFrag = document.createDocumentFragment();
		
		if (
			$($wrapper)
				.children()
				.not('.filter-title').length > 0 ||
			$($wrapper).hasClass('is-padding')
		) {
			return;
		}
		$($wrapper).addClass('is-padding');

		$.api.get({
			url: '/hot/products.do',
			data: {
				catDivnCd: tabNo,
				strCd: $.utils.config('main_store_code'),
				stdistMallYn: $.utils.config('stdistMallYn'),
				custGrade: $.utils.config('custGrade'),
				childYn: $.utils.config('childYn')
			},
			successCallback: function(response) {
				if (response.isSucceed) {
					var $div = document.createElement('div');
					var imageRootUrl = response.data.imageRootUrl;
					var catDivnCd = response.data.catDivnCd;
					var isExpressDeliveryArea = response.data.isExpressDeliveryArea;
					var lists = response.data.hotProducts.map(function(v) {
						v.itemImagePath = imageRootUrl + (v.itemWideImagePath || v.itemImagePath);
						v.catDivnCd = catDivnCd;
						v.isExpressDeliveryArea = isExpressDeliveryArea;
						return v;
					});
					
					$div.innerHTML = $.render.mobileTodayHotList({
						lists: lists
					});

					$docFrag.appendChild($div);
					$wrapper.appendChild($docFrag);

					if (type === 'best') {
						showInsertionBanner(contentId,type);
						renderHottime();
					}else{//핫콕 추천영역외 5개카테고리 노출 추가
						showInsertionBanner(contentId,type);
					}
				} else {
					$.utils.error('todayhotlist:' + response.error);
				}

				if (window.imageLazyLoad) {
					window.imageLazyLoad.setLazy();
					window.imageLazyLoad.load();
				}

				$($wrapper).removeClass('before-loading is-padding');
			}
		});
	}

	function getType($el) {
		return $el.data('type');
	}

	function getTabNo($el) {
		return $el.data('tabNo');
	}

	function getContentId($el) {
		return $el.attr('href').replace('#', '');
	}

	function throttle(fn, wait) {
		var time = Date.now();

		return function() {
			if (time + wait - Date.now() < 0) {
				fn();
				time = Date.now();
			}
		};
	}

	function getStickyHeight() {
		var _height = $('.ta-main-nav').outerHeight(true) || $('#mainGnb').outerHeight(true) || 0;
		return filterHeight + _height;
	}

	function bindScrollEvent() {
		$(window).on(
			'scroll.throttle',
			throttle(function() {
				var scrollTop = $(this).scrollTop();
				var tabs = [];

				$productListWrapper.find('.product-list').each(function() {
					var $this = $(this);
					var top = $(this).offset().top;
					var bottom = top + $(this).outerHeight(true);
					var id = $this.attr('id');
					
					if (!id) return;
					
					tabs.push({
						id: id,
						position: {
							top: top,
							bottom: bottom
						},
						callback: function() {
							
							if (!$filter.find('[href="#' + id + '"]').hasClass('active')) {
								$filter
									.find('[href="#' + id + '"]')
									.addClass('active')
									.siblings()
									.removeClass('active');
							}
							
							var data = $('#' + id).data();
							
							if (!id) return;
							getTodayHotList(data.type, id, data.tabNo);
						}
					});
				});

				var stickyTop = scrollTop + getStickyHeight();

				if (stickyTop < tabs[0].position.top) {
					activeTab(tabs, 0);
					return;
				}

				tabs.some(function(tab, index) {
					if (stickyTop >= tab.position.top && stickyTop <= tab.position.bottom) {
						activeTab(tabs, index);
					}
					return stickyTop >= tab.position.top && stickyTop <= tab.position.bottom;
				});
			}, 250)
		);
	}

	function activeTab(tabs, index) {
		if (tabIndex === index) return;
		
		if ($(document).height() === $(window).height() + $(window).scrollTop()) {
			tabIndex = tabs.length - 1;
		}
		tabIndex = index;
		tabs[tabIndex].callback();
	}

	function bindEvent() {
		$filter.on('click', 'a', function(e) {
			e.preventDefault();

			var contentId = getContentId($(this));
			var top = $productListWrapper.find('#' + contentId).offset().top + 3;

			// $(window).scrollTop(top - getStickyHeight() - 110);
			$(window).scrollTop(top - getStickyHeight());
		});

		$pageWrapper.on('click', '[name=btnPlayYoutube]', function() {
			$(this).videoLayer({
				posTop: $(this).closest('.hotkok-item').offset().top + 'px',
				videoHeight: '18rem'
			});
		});

		var $slTarget = $('.hotkok-service-list');
		$slTarget.find('.item-more').on('click', function() {
			$slTarget.toggleClass('fold');
		});
	}

	function startRemainTimeCounter() {
		setTimeout(function() {
			var $remainTimeWrapper = $('#mobileHottime').find('#hotTime');

			if ($remainTimeWrapper.length > 0) {
				var $timer = createRemainTimeCounter($remainTimeWrapper);
				$timer.start();
			}
		}, 50);
	}

	function renderPersonalProducts() {
		getPersonalProducts({
			callback: function(res) {
				$pageWrapper.find('[name=kokBnr5]').each(function() {
					$(this).html('');
				});

				if ($.isEmptyObject(res)) {
					return;
				}
				if (res.html != '') {
					var wrapClass = ('Y' === res.isMcoupon) ? 'wrapper-fav-product fav-mcoupon' : 'wrapper-fav-product';
					$pageWrapper.find('[name=kokBnr5]').attr('class', wrapClass);
					$pageWrapper.find('[name=kokBnr5]').append(res.html);
				}
			}
		});
	}

	function getPersonalProducts(options) {
		$.api.get({
			apiName: 'getPersonalProducts',
			dataType: 'json',
			$container: $kokBnr5,
			successCallback: function(res) {
				var html = '';
				var isMcoupon = '';
				var result = {};

				if (res.data.coupons != null) {
					if (Object.keys(res.data.coupons).length >= 1) {
						html = $.render.mobilePersonalMcouponsNew({
							personalMcoupons: res.data.coupons,
							memberName: res.data.memberName
						});
						isMcoupon = 'Y';
					}
				} else {
					if (res.data.recently != null) {
						if (Object.keys(res.data.recently).length >= 2) {
							html = $.render.mobileRecentlyProduct({
								recentlyPurchasedProducts: res.data.recently
							});
						}
					}
					isMcoupon = 'N';
				}

				result.isMcoupon = isMcoupon;
				result.html = html;

				if (options.callback) {
					options.callback(result);
				}
			}
		});
	}

	function renderHottime() {
		renderHtime({
			callback: function(res) {
				$('#mobileHottime').html(res);
				$('#mobileHottime')
					.find('[name^=kokBnr1]')
					.show();
				startRemainTimeCounter();
			}
		});
	}

	function renderHtime(hottime) {
		$.api.get({
			apiName: 'getMobileHottime',
			dataType: 'json',
			$container: $mobileHottime,
			successCallback: function(res) {
				var html = $.render.mobileHottime({
					CornerMapWithContent: res
				});
				if (hottime.callback) {
					hottime.callback(html);
				}
			}
		});
	}

	function createRemainTimeCounter($target) {
		var hotTimeChek =
			$('#hotTimeChek').val() == '' || $('#hotTimeChek').val() == undefined ? 24 : $('#hotTimeChek').val();
		var currentSeconds = (Math.round(getCurrentTime() / 1e3) + 9 * 60 * 60) % (24 * 60 * 60),
			remainSeconds = ($target.data('endtime') - currentSeconds) % (hotTimeChek * 60 * 60);

		return $target.setTimer({
			$wrapper: $target,
			currentSeconds: currentSeconds,
			remainSeconds: remainSeconds
		});
	}

	function getCurrentTime() {
		var currentTime = new Date().getTime();

		$.api.get({
			url: '/mobile/main/ajax/serverTime.do?datetime=' + currentTime,
			async: false,
			successCallback: function(data) {
				currentTime = data.serverTime;
			}
		});

		return currentTime;
	}

	function showInsertionBanner(contentId,type) {//핫콕 이미지 배너 노출 구분을 위해 type 값 추가함
		var $insertionBanner = $pageWrapper.find('[name=insertionBannerContainer'+type+']');
		$insertionBanner.each(function(i, v) {
			var $insertionBannerPanel = $pageWrapper
				.find('#' + contentId)
				.find('[name=insertionBannerPanel'+type+']')
				.eq(i);
			if ($insertionBannerPanel.length > 0) {
				$insertionBannerPanel.append($(v).show());
				$insertionBannerPanel.show();
			}
		});
	}

	function sendVideoLoadPageInfoGA() {
		var requestUri =
			location.href.replace(location.origin + '/mobile', '') +
			(location.search === '' ? '?returnCategoryId=' + hash : '');

		ga('send', 'event', {
			eventAction: '동영상 콘텐츠 | 동영상 페이지 로드',
			eventCategory: '#기기=M #분류=GNB #페이지명=핫콕 #URL=' + requestUri,
			eventLabel: '#CID=' + $btnPlayYoutube.data('cid') + ' #PID=' + $btnPlayYoutube.data('pid')
		});
	}

	return {
		init: function(obj) {
			$footer.removeClass(loadingFooterClass);

			$.utils.config({
				custGrade: obj.custGrade,
				childYn: obj.childYn
			});

			_init(obj);
		}
	};
});
