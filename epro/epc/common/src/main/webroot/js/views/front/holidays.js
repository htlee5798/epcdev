$.view = {};

(function(win, $){
	if(!$.ui || !$.ui.helper){
		$.utils.log('undefined $.ui.helper');

		return;
	};

	var $helper = $.ui.helper;

	var holidays = {

		showotherPrice : function($tab) {				//상품 할당 영역 노출
			var data = {
					apiName : 'getCategoryProductListPage',
					imageSize : 500,
					CATEGORY_ID : getTabCategoryId($tab),
					ROW_SIZE : $tab.data('rowSize'),
					siteLocation : $tab.data('siteLocation'),
					returnPagePath : $tab.data('pagePath'),
					sortType : 'DISPLAY_SEQ_ASC'
				},
				templateParams = {
					$tab : $tab,
					hasMoreButton : false,
					categoryId : getTabCategoryId($tab),
					$listContainer : $('#'+$tab.data('listContainer')),
					productListWrapperClass : $tab.data('listClass'),
					templateId : 'holidaysBannerProductListPanel',
					categoryName : $tab.data('title'),
					afterRender : $helper.active
				};
				templateParams.getContainer = function() {
					var $container = {};

					if(templateParams.$listContainer) {
						$container = templateParams.$listContainer.find('[name=productPanel][data-category-id=' + templateParams.categoryId  + ']');;
					};
					return $container;
				};

			$helper.showProductsByTab(data, templateParams);
		},

		showProductsByMd : function($tab) {				// MD 추천 상품 할당 영역 노출
			var data = {
					apiName : 'getCategoryProductListPage',
					imageSize : 500,
					CATEGORY_ID : getTabCategoryId($tab),
					ROW_SIZE : $tab.data('rowSize'),
					siteLocation : $tab.data('siteLocation'),
					returnPagePath : $tab.data('pagePath'),
					sortType : 'DISPLAY_SEQ_ASC'
				},
				templateParams = {
					$tab : $tab,
					hasMoreButton : false,
					categoryId : getTabCategoryId($tab),
					$listContainer : $('#'+$tab.data('listContainer')),
					productListWrapperClass : $tab.data('listClass'),
					templateId : 'holidaysBannerProductListMd',
					categoryName : $tab.data('title'),
					afterRender : $helper.active
				};
				templateParams.getContainer = function() {
					var $container = {};

					if(templateParams.$listContainer) {
						$container = templateParams.$listContainer.find('[name=productPanel][data-category-id=' + templateParams.categoryId  + ']');;
					};
					return $container;
				};

			$helper.showProductsByTab(data, templateParams);
		},

		showProductsByPrice : function($tab) {				//가격대별 선물세트
			var data = {
					ROW_SIZE : $tab.data('rowSize'),
					siteLocation : $tab.data('siteLocation'),
					sortType : 'DISPLAY_SEQ_ASC'
				},
				templateParams = {
					$listContainer : $('#pricePanelContainer'),
					productListWrapperClass : 'wrap-prod-linetype'
				};

			$helper.showProductsByTab(extendData(data, $tab), extendTemplateParams(templateParams, $tab));
		},

		showBuyByLowPrice : function($tab) {			//설 특별혜택선물세트&대량구매추천세트
			var data = {
					CORN_SEQ : $tab.data('cornSeq'),
					SET_SEQ : $tab.data('setSeq'),
					ROW_SIZE : $tab.data('rowSize'),
					siteLocation : $tab.data('siteLocation'),
					sortType : 'DISPLAY_SEQ_ASC',
				},
				templateParams = {
					$listContainer : $('#lowpricePanelContainer'),
					productListWrapperClass : 'wrap-prod-linetype'
				};

			$helper.showProductsByTab(extendData(data, $tab), extendTemplateParams(templateParams, $tab));
		},

		showCategoryProducts : function($tab) {			//카테고리별 선물세트
			var data = {
					CORN_SEQ : $tab.data('cornSeq'),
					SET_SEQ : $tab.data('setSeq'),
					ROW_SIZE : 8,
					imageSize : 500,
					siteLocation : $tab.data('siteLocation'),
					sortType : 'DISPLAY_SEQ_ASC'
				},
				templateParams = {
					$listContainer : $('#catePanelContainer'),
					productListWrapperClass : 'wrap-prod-linetype'
				};

			$helper.showProductsByTab(extendData(data, $tab), extendTemplateParams(templateParams, $tab));
		},

		showCategoryDetailProducts : function($tab) {			//카테고리별 선물세트
			var data = {
					CORN_SEQ : $tab.data('cornSeq'),
					SET_SEQ : $tab.data('setSeq'),
					ROW_SIZE : $tab.data('rowSize'),
					siteLocation : $tab.data('prodFilter'),
					sortType : 'DISPLAY_SEQ_ASC'
				},


				templateParams = {
					$listContainer : $('#catePanelContainer'),
					productListWrapperClass : 'wrap-prod-linetype'
				};

			$helper.showProductsByTab(extendData(data, $tab), extendTemplateParams(templateParams, $tab));
		},

		renderProducts : function(data, templateParams) {
			$helper.showProductsByTab(extendData(data), extendTemplateParams(templateParams));
		},

		showInquiryPopup : function(productName) {
			var inquiryUrl = _LMAppUrl + '/board/holiday/popup/view.do';

			if( productName && productName != '' ) {
				inquiryUrl += '?ProductNM=' + encodeURIComponent(productName);
			};

			window.open(inquiryUrl, 'inquiryPurchasePopup', 'top=50, left=200, width=650, height=800, resizable=no, location=no');
		},

		showRecommendProducts : function($tab) {			//개인별 명절 추천상품

			var data = {
					apiName : 'getRecommProdList',
					siteLocation : $tab.val()
				},
			templateParams = {
				$listContainer : $('.wrap-personal-cont'),
				hasMoreButton : false,
				productListWrapperClass : 'wrap-prod-linetype'
			};

			 $helper.renderProducts(extendData(data, $tab), extendTemplateParams(templateParams, $tab));

		},

		renderSubLNBLayer : function(params){
			$.api.get({
				apiName : 'getCategoryList',
				data : params.data,
				successCallback : function(categories){
					if(categories.length > 0){
						var html = $.render.holidaysLNBSubLayer({
							parentCategoryName : params.categoryName,
							subSiteLocation : params.subSiteLocation,
							startSiteLocationIndex : params.startSiteLocationIndex,
							categories : categories
						});

						params.$category.append(html);
						//renderRepresentationProduct(params.$category);
					}
				}
			});

			function renderRepresentationProduct($category) {
				var params = {
						apiName : 'getTemplateProductListPage',
						returnPagePath : 'holidays/includes/productList',
						CATEGORY_ID : $category.data('categoryId'),
						CORN_SEQ : '002',
						SET_SEQ : '001'
					},
					productSiteLocation = $category.data('productSiteLocation');

				if(productSiteLocation && productSiteLocation !== ''){
					$.extend(params, { siteLocation : productSiteLocation });
				}

				$helper.renderProducts(params, {
					successCallback : function(productListHtml) {
						if(productListHtml) {
							$(productListHtml).children().appendTo($category.siblings());
							$('.lnb-holi').find('.product-article').addClass('prod-list');
						};
					}
				});
			}
		}
	};

	function extendData(data, $tab){
		var _data = {
			ROW_SIZE : 3,
			CATEGORY_ID : getTabCategoryId($tab),
			returnPagePath : 'holidays/includes/productList'
		};

		return $.extend(_data, data);
	};

	function extendTemplateParams(templateParams, $tab){
		var _templateParams = {
			$tab : $tab,
			hasMoreButton : true,
			categoryId : getTabCategoryId($tab),
			productListWrapperClass : 'wrap-prod-linetype',
			templateId : 'holidaysProductListPanel',
			afterRender : $helper.active
		};

		$.extend(_templateParams, templateParams);
		if($tab) {
			_templateParams.categoryName = $tab.text();
			_templateParams.siteLocation = $tab.data('siteLocation');
		};

		_templateParams.getContainer = function() {
			var $container = {};

			if(_templateParams.$listContainer) {
				$container = _templateParams.$listContainer.find('[name=productPanel][data-category-id=' + _templateParams.categoryId  + ']');;
			};

			return $container;
		};

		return _templateParams;
	};

	function getTabCategoryId($tab){
		if($tab) {
			return $tab.data('categoryId');
		};

		return '';
	};


	$.view = {
		holidays : holidays
	};
})(window, jQuery);