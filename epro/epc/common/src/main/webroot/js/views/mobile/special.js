$.view = {};

(function(win, $) {
	if (!$.utils) {
		return alert('not defined dependency library');
	}

	var viewTypeStorageKey = 'mobileSpecialMain-viewType',
		isLoading = false,
		isExistsMoreProduct = true;

	var special = {
		setTopBannerUI: function() {
			var $bannerContainer = $('.wrap-pagination-slider-fullfit'),
				$banners = $bannerContainer.find('.swiper-slide');

			if ($banners.length <= 1) {
				$banners.css({ 'margin-left': 'auto', 'margin-right': 'auto' });
			} else {
				$bannerContainer.swiper({
					pagination: $bannerContainer.find('.swiper-pagination')[0],
					centeredSlides: true,
					slidesPerView: 'auto',
					loop: true,
					autoplay: 4e3,
					autoplayDisableOnInteraction: false
				});
			}
		},

		getProductApiUrl: function(productType) {
			if (productType.contains('Template')) {
				return '/mobile/special/ajax/productsByCorner.do';
			} else if (productType.contains('Category')) {
				return '/mobile/cate/ajax/cateProductListAjax.do';
			}

			return null;
		},

		// TODO : duplicate product list
		bindScrollEvent: function(options) {
			var _infiniteScroll = new _InfiniteScroll({
				container: options.$container,
				callback: options.callback
			});

			$(window).on('scroll', function() {
				if (_infiniteScroll) {
					_infiniteScroll.setPosition($(this).scrollTop());
				}
			});
		},

		// TODO : duplicate product list
		getNextViewType: function(currentViewType) {
			if (currentViewType == 'type-list') {
				return 'type-gallery';
			} else if (currentViewType == 'type-gallery') {
				return 'type-image';
			} else if (currentViewType == 'type-image') {
				return 'type-list';
			}
		},

		// TODO : duplicate product list
		setFilterFunction: function(callback) {
			var _this = this;

			window.setFilter = function() {
				$('[name=deliveryView]').val(_this.createDeliveryTypes());
				$('[name=benefitChkList]').val($.utils.concatCheckedValues($('[name=benefitChk]')));
				$('[name=setBenefitId]').val($.utils.concatCheckedId($('[name=benefitChk]')));

				$('#globalSearchLayer')
					.find('#closeBtn')
					.click();

				if (callback) {
					callback();
				}
			};
		},

		// filter layer history back 대응
		setFilterLayerInitValue: function() {
			var $layer = $('#globalSearchLayer');

			var deliveryTypes = this.createDeliveryTypes(),
				checkedBenefitValues = $.utils.concatCheckedValues($('[name=benefitChk]'));

			$layer.find('#deliveryView').val(deliveryTypes);
			$layer.find('#benefitChkList').val(checkedBenefitValues);

			$('[name=deliveryView]').val(deliveryTypes);
			$('[name=benefitChkList]').val(checkedBenefitValues);
		},

		// TODO : duplicate product list
		createDeliveryTypes: function() {
			var deliveryTypes = [];

			$('[name=deliveryType]').each(function(i, v) {
				var $v = $(v);
				if ($v.is(':checked')) {
					deliveryTypes.push($v.val());

					if ($v.val() == '01') {
						deliveryTypes.push('01[!@!]03');
					}
				}
			});

			return deliveryTypes;
		},

		// TODO : duplicate product list
		setViewTypeUI: function(options) {
			var currentValue = options.currentValue || 'type-list';

			if (this.getViewTypeSessionStorage() == null) {
				this.setViewTypeSessionStorage(currentValue);
			}

			var targetValue = options.targetValue || this.getViewTypeSessionStorage() || this.getNextViewType(currentValue);

			options.$pageWrapper
				.find('.' + currentValue)
				.removeClass(currentValue)
				.addClass(targetValue);

			options.$btnChangeViewType.removeClass(currentValue).addClass(targetValue);
		},

		// TODO : duplicate product list
		setViewTypeSessionStorage: function(value) {
			sessionStorage.setItem(viewTypeStorageKey, value);
		},

		// TODO : duplicate product list
		getViewTypeSessionStorage: function() {
			return sessionStorage.getItem(viewTypeStorageKey);
		},

		renderProducts: function(params) {
			if (!isLoading) {
				isLoading = true;

				var $currentPage = params.$panel.find('[name=currentPage]');
				if ($currentPage) {
					$currentPage.val(params.currentPage || 1);
				}

				var productType = params.$panel.data('panelId'),
					_params = $.extend(
						{
							url: this.getProductApiUrl(productType),
							isShowMoreBar: true,
							isShowLoadingBar: true,
							dataType: 'html',
							productType: productType,
							$container: $('#pageWrapper'),
							data: $.extend(
								{
									categoryId: $.utils.config('mainCategoryId'),
									CategoryID: $.utils.config('mainCategoryId'),
									specCategory: 'Y'
								},
								$.utils.serializeObject(params.$panel.find('form'))
							)
						},
						params
					);

				$.api.get(_params);
			}
		},

		renderFirstPageProducts: function(panelId) {
			var _this = this,
				$panel = $('[name=productPanel][data-panel-id=' + panelId + ']');

			_this.renderProducts({
				$panel: $panel,
				successCallback: function(productListHtml) {
					var $productContainer = $panel.find('[name=productContainer]'),
						$totalCount = $('[name=totalCount]'),
						$btnSearchFilter = $('#searchFilter');

					$productContainer.html(productListHtml);
					if ($totalCount.length > 0) {
						var $pageData = $productContainer.find('.page-data'),
							totalCount = $pageData.data('totalcount');

						_this.setTotalCountText($totalCount, totalCount);
						isExistsMoreProduct = totalCount > $pageData.data('lastnum');

						if ($btnSearchFilter.length > 0 && $btnSearchFilter.data('searchFilter')) {
							$btnSearchFilter.data('searchFilter').setOptions({
								totalCount: totalCount,
								searchObject: {
									deliveryView: $('[name=deliveryView]').val(),
									benefitChkList: $('[name=benefitChkList]').val()
								}
							});
						}
					}

					isLoading = false;
				}
			});
		},

		setTotalCountText: function($el, totalCount) {
			$el.each(function(i, v) {
				var $v = $(v),
					totalCountText = totalCount;

				if ($v.data('hasParenthesis')) {
					totalCountText = '(' + totalCount + ')';
				}

				$v.text(totalCountText);
			});
		},

		renderMoreCategoryProducts: function() {
			if (isExistsMoreProduct) {
				var $panel = $('[name=productPanel][data-panel-id=subCategory]');

				this.renderProducts({
					$panel: $panel,
					currentPage: parseInt($panel.find('[name=currentPage]').val(), 10) + 1,
					successCallback: function(productListHtml) {
						var $productContainer = $panel.find('[name=productContainer]');

						$productContainer.append(productListHtml);

						var $pageData = $productContainer.find('.page-data').last();

						isExistsMoreProduct = $pageData.data('totalcount') > $pageData.data('lastnum');
						isLoading = false;
					}
				});
			}
		}
	};

	$.view = {
		special: special
	};
})(window, jQuery);
