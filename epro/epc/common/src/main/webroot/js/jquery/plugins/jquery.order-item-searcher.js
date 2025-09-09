;(function( $, window, document, undefined ) {
	'use strict';
	
	var OrderItemSearcher = function($el, options) {
		this.$el = $el;
		this.messages = OrderItemSearcher.messages;
		
		this.config = OrderItemSearcher.config;
		$.extend(this.config, options);
		
		this.bindEvent();
		
		return this;
	};

	OrderItemSearcher.prototype = {
		
		initialize : function() {
			var searcher = this;
			
			searcher.render();
			
			var params = { searchMonth : searcher.$el.find('[name=period]:checked').val() };

			searcher.setTotalCount(params);
			searcher.renderOrders(params);
		},
		
		bindEvent : function() {
			var searcher = this;
			
			// 기간 검색
			searcher.$el.on('click', '[name=period]', function() {
				var $this = $(this)
				  , params = { searchMonth : $this.val() };

				searcher.setTotalCount(params);
				searcher.renderOrders(params);
			});
			
			// 주문 선택			
			searcher.$el.on('change', '[name=orderNumber]', function() {
				var $this = $(this)
				  , $wrapper = $this.closest('[name=orderWrapper]')
				  , $itemContainer = $wrapper.find('[name=orderItemContainer]');

				if($itemContainer.children().length <= 0) {
					searcher.renderOrderItems($itemContainer, $this.val());
				}
				
				searcher.resetSelectedItems();
				searcher.showOrderItemContainer($wrapper);
				
				searcher.$el.find('[name=orderNumber]').each(function(i, v) {
					var $v = $(v);
					
					$.utils.setActiveCheckedFieldWrapper($v, $v.closest('[name=orderNumberWrapper]'));
				});
			});
			
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
				
				searcher.renderOrders({ 
					searchMonth : searcher.$el.find('[name=period]:checked').val(), 
					currentPage : currentPage 
				});
			});
			
		},
		
		destroy : function() {
			if(this.config.destroy) {
				this.config.destroy();
			}
		},
		
		render : function() {
			this.$el.html($.render[this.$el.data('templateId')](this.config.order.params));
			this.$el.find('[name=btnPagination]').hide();
		},
		
		renderOrders : function(data) {
			var searcher = this;
			
			searcher.renderData(searcher.config.order, {
				data : data,
				callback : function(res, params) {
					var $orderContainer = searcher.$el.find('[name=container]');
					
					if(res.orders.length <= 0) {
						$orderContainer.html(searcher.getEmptyHtml());
						return;
					}
					
					var isFirstPage = params.currentPage <= 1;
					
					searcher.config.renderPaginationData($orderContainer, $.render[$orderContainer.data('templateId')](res), isFirstPage);
					
					searcher.config.setPaginator({
						$searcher : searcher.$el,
						totalCount : searcher.totalCount,
						currentCount : $orderContainer.children().length,
						currentPage : params.currentPage
					});
				}
			});
		},
		
		getEmptyHtml : function(html) {
			var $tempWrapper = $('<div/>');
			
			$tempWrapper.append($('<p/>').addClass('list-empty').text(this.messages.emptyItem));
			return $tempWrapper.html();
		},
		
		showOrderItemContainer : function($wrapper) {
			this.$el.find('[name=orderItemContainer].active').removeClass('active');
			$wrapper.find('[name=orderItemContainer]').addClass('active');
		},
		
		resetSelectedItems : function() {
			var $selectedItem = this.$el.find('[name=productCode]:checked');
			
			$selectedItem.removeAttr('checked');
			$selectedItem.each(function(i, v) {
				$.utils.setActiveCheckedFieldWrapper($(v), $(v).closest('[name$=Wrapper]'));
			});
		},
		
		renderOrderItems : function($itemContainer, orderId) {
			var searcher = this;

			searcher.renderData(searcher.config.orderItem, {
				data : { orderId : orderId },
				callback : function(res) {
					if(res) {
						var templateId = $itemContainer.data('templateId');
						
						$itemContainer.append($.render[templateId](res));	
					}
				}
			});
		},
		
		renderData : function(config, options) {
			var searcher = this
			  , params = $.extend({}, config.params, options.data);
			
			$.api.get({
				apiName : config.apiName,
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
		
		setTotalCount : function(params) {
			var searcher = this;
			
			$.api.get({
				apiName : searcher.config.order.countApiName,
				async : false,
				data : params,
				$container : searcher.$el,
				isShowLoadingBar : true,
				successCallback : function(res) {
					searcher.totalCount = res.totalCount;
				},
				errorCallback : function() {
					searcher.totalCount = 0;
				}
			});
		},
		
		isValid : function($selectedItem) {
			return $selectedItem.length > 0; 
		}
		
	};
	
	OrderItemSearcher.config = {
		$selectedItemContainer : $('#selectedItemContainer'), 
		order : {
			apiName : 'myOrderList',
			countApiName : 'myOrderCount',
			params : {
				rowPerPage : 20,
				currentPage : 1
			}
		},
		orderItem : {
			apiName : 'myOrderItemList'
		},
		
		renderPaginationData : function($container, html, isFirstPage) {
			if(isFirstPage) {
				$container.empty();
			}
			
			$container.append(html);
		},
		
		callback : function($selectedItemContainer, $selectedItem) {
			var $cloneOrderWrapper = $selectedItem.closest('[name=orderWrapper]').clone();
			
			$selectedItemContainer.empty();
			$cloneOrderWrapper.find('[name=productCode]:checkbox').each(function(i, v) {
				var $v = $(v);
				
				if(!$v.is(':checked')) {
					$v.closest('[name=itemWrapper]').remove();
				}
			});
			
			$selectedItemContainer.append($cloneOrderWrapper.html());
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
	
	OrderItemSearcher.instance = null;
	OrderItemSearcher.getInstance = function($el, options) {
		if(!OrderItemSearcher.instance) {
			OrderItemSearcher.instance = new OrderItemSearcher($el, options);
		}
		
		return OrderItemSearcher.instance;
	};
	
	OrderItemSearcher.messages = {
		emptyItem : '주문내역이 없습니다.',
		noneSelectedItem : '선택된 상품이 없습니다. 주문 또는 상품을 선택해주세요'	
	};
	
	$.fn.loadOrderItemSearcher = function(options) {
		var instance = OrderItemSearcher.getInstance($(this), options);
		
		return instance.initialize();
	};
	
})(jQuery, window, document);