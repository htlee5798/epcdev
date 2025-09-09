;(function( $, window, document, undefined ) {
	'use strict';
	
	var BasketItemSearcher = function($el, options) {
		this.$el = $el;
		this.messages = BasketItemSearcher.messages;
		
		this.config = BasketItemSearcher.config;
		$.extend(this.config, options);
		
		this.bindEvent();
		
		return this;
	};
	
	BasketItemSearcher.prototype = {
			
		initialize : function() {
			var searcher = this;

			searcher.render();
			searcher.$itemContainer = searcher.$el.find('[name=itemContainer]');
			
			searcher.setTotalCount();
			searcher.renderItems({
				callback : function(res, params) {
					if(res.items.length <= 0) {
						searcher.$itemContainer.html(searcher.getEmptyItemHtml());
						return;
					};
					
					searcher.config.renderPaginationData(searcher.$itemContainer, $.render[searcher.$itemContainer.data('templateId')](res), true);
					searcher.config.setPaginator(searcher.createPaginationOptions(params));
				}
			}); 
		},
		
		getEmptyItemHtml : function() {
			var $tempWrapper = $('<div/>');
			
			$tempWrapper.append($('<p/>').addClass('list-empty').text(this.messages.emptyItem));
			return $tempWrapper.html();
		},
		
		createPaginationOptions : function(params) {
			var searcher = this;
			
			return $.extend({}, {
				$searcher : searcher.$el,
				totalCount : searcher.totalCount,
				currentCount : searcher.$itemContainer.children().length,
			}, params);
		},
		
		bindEvent : function() {
			var searcher = this;
			
			// 선택상품 적용
			searcher.$el.on('click', '[name=btnAply]', function() {
				var $selectedItem = searcher.$el.find('[name=productCode]:checked');
				if(!searcher.isValid($selectedItem)) {
					alert(searcher.messages.noneSelectedItem);
					
					return;
				}
				
				if(searcher.config.callback) {
					searcher.config.callback(searcher.config.$selectedItemContainer, $selectedItem);
				}
				searcher.destroy();
			});
			
			// 상품 코드 선택
			searcher.$el.on('click', '[name=productCode]', function() {
				var $this = $(this);

				$.utils.setActiveCheckedFieldWrapper($this, $this.closest('[name=productCodeWrapper]'));
			});
			
			// 페이징
			searcher.$el.on('click', '[name=btnPagination]', function() {
				var currentPage = $(this).attr('data-current-page') | 0;
				
				searcher.renderItems({
					data : { currentPage : currentPage },
					callback : function(res, params) {
						var html = $.render[searcher.$itemContainer.data('templateId')](res);
						
						searcher.config.renderPaginationData(searcher.$itemContainer, html, false);
						searcher.config.setPaginator(searcher.createPaginationOptions(params));
					}
				});
			});
		},
		
		destroy : function() {
			if(this.config.destroy) {
				this.config.destroy();
			}
		},
		
		isValid : function($selectedItem) {
			return $selectedItem.length > 0; 
		},
		
		render : function() {
			this.$el.html($.render[this.$el.data('templateId')](this.config.params));
			this.$el.find('[name=btnPagination]').hide();			
		},
		
		renderItems : function(options) {
			var searcher = this
			  , params = $.extend({}, searcher.config.params, options.data);
			
			$.api.get({
				apiName : this.config.apiName,
				data : params,
				$container : searcher.$el,
				isShowLoadingBar : true,
				successCallback : function(res) {
					if(options.callback) {
						options.callback(res, params);
					}
				},
				errorCallback : function() {
					if(options.callback) {
						options.callback();
					}
					
					$.utils.log(arguments);
				}
			});
		},
		
		setTotalCount : function() {
			var searcher = this;
			
			$.api.get({
				apiName : this.config.countApiName,
				async : false,
				$container : searcher.$el,
				isShowLoadingBar : true,
				successCallback : function(res) {
					searcher.totalCount = res.totalCount;
				},
				errorCallback : function() {
					searcher.totalCount = 0;
				}
			});
		}
		
	};
	
	BasketItemSearcher.instance = null;
	
	BasketItemSearcher.config = {
		$selectedItemContainer : $('#selectedItemContainer'),
		apiName : 'normalBasketList',
		countApiName : 'normalBasketCount',
		params : {
			rowPerPage : 20,
			currentPage : 1
		},
		renderPaginationData : function($container, html, isFirstPage) {
			if(isFirstPage) {
				$container.empty();
			}
			
			$container.append(html);
		},
		callback : function($selectedItemContainer, $selectedItem) {
			var $itemWrapper = $selectedItem.closest('[name=itemWrapper]')
			  , $cloneItemContainer = $selectedItem.closest('[name=itemContainer]').clone().html($itemWrapper)
			  , $tempContainer = $('<ul />').addClass('complain-list');
			
			$tempContainer.append($cloneItemContainer);
			
			$selectedItemContainer.html($tempContainer.html());
		},
		setPaginator : function(options) {
			var $btnPagination = options.$searcher.find('[name=btnPagination]');
			
			if(options.totalCount > options.currentCount) {
				$btnPagination.show();
				$btnPagination.attr('data-current-page', options.currentPage + 1);
			} else {
				$btnPagination.hide();
			}
		}
	};
	
	BasketItemSearcher.instance = null;
	BasketItemSearcher.getInstance = function($el, options) {
		if(!BasketItemSearcher.instance) {
			BasketItemSearcher.instance = new BasketItemSearcher($el, options);
		}
		
		return BasketItemSearcher.instance;
	};
	
	BasketItemSearcher.messages = {
		emptyItem : '장바구니 상품이 없습니다',
		noneSelectedItem : '선택된 상품이 없습니다. 상품을 선택해주세요',
	};
	
	$.fn.loadBasketItemSearcher = function(options) {
		var instance = BasketItemSearcher.getInstance($(this), options);
		
		return instance.initialize();
	};
	
})(jQuery, window, document);