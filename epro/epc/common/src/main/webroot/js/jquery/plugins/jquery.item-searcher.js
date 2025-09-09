;(function( $, window, document, undefined ) {
	'use strict';
	
	if(!$.ui || !$.ui.helper){
		$.utils.log('undefined $.ui.helper');
		
		return;
	};
	
	var $helper = $.ui.helper;

	var ItemSearcher = function($el, options) {
		this.config = { 
			tabSelector : '[name=btnTab]'
		};
		
		$.extend(this.config, options);
		this.$el = $el;
		
		this.bindEvent();
		
		return this;
	};
	
	ItemSearcher.prototype = {
		
		initialize : function() {
			this.show();
			this.showItemSearcherByItemType(this.$el.find(this.config.tabSelector + '.active'));
		},
		
		show : function() {
			if(this.config.show) {
				this.config.show();
			}
		},
		
		bindEvent : function() {
			var searcher = this;
			
			// 탭클릭
			searcher.$el.on('click', searcher.config.tabSelector + ':not(.active)', function() {
				searcher.showItemSearcherByItemType($(this));
			});
			
			// 닫기
			searcher.$el.on('click', '[name=btnClose]', function() {
				searcher.destroy();
			});		
			
			// 위로이동
			searcher.$el.on('click', '[name=btnMoveToTop]', function() {
				searcher.$el.find('#pageWrapper').animate( { scrollTop : 0 }, 400 );
			});
			
		},
		
		destroy : function() {
			if(this.config.destroy) {
				this.config.destroy();
			}
		},
		
		showItemSearcherByItemType : function($activeTab) {
			var itemType = $activeTab.data('itemType');
			
			var $activeTargetPanel = this.$el.find('[name=tabPanel][data-item-type=' + itemType + ']');
			
			$helper.activePanelWithTab($activeTab, $activeTargetPanel);
			this.setSubItemSearcher($activeTargetPanel, itemType);
		},
		
		setSubItemSearcher : function($wrapper, itemType) {
			var loadFunctionName = this.getSubSearcherLoadFunctionName(itemType);
			
			$wrapper[loadFunctionName](this.config);
		},
		
		getSubSearcherLoadFunctionName : function(itemType) {
			return 'load' + itemType + 'ItemSearcher';
		}
		
	};
	
	ItemSearcher.instance = null;
	ItemSearcher.getInstance = function($el, options) {
		if(!ItemSearcher.instance) {
			ItemSearcher.instance = new ItemSearcher($el, options);
		}
		
		return ItemSearcher.instance;
	};
	
	$.fn.showItemSearcher = function(options) {
		var instance = ItemSearcher.getInstance($(this), options);
		
		return instance.initialize();
	};
	
})(jQuery, window, document);