(function(win, $){
	var helper = {};
	
	helper.renderProducts = function(data, templateParams) {
		$.api.get({
			apiName : data.apiName || 'getCategoryProductListPage',
			dataType : data.dataType || 'html',
			data : data,
			successCallback : function(productListHtml){
				var callback = templateParams.successCallback || successCallback;
				
				callback(productListHtml, templateParams);
				
				if(templateParams.afterRender){
					templateParams.afterRender(templateParams);
				};
			}
		});
		
		function successCallback(productListHtml, params){
			var productPanelHtml = $.render[templateParams.templateId]($.extend({
					productListHtml : productListHtml
				}, params));
			
			params.$listContainer.append(productPanelHtml);
			if(params.afterRender) {
				params.afterRender(params);
			};
		};
	};
	
	helper.showProductsByTab = function(data, templateParams) {
		var _templateParams = {
			afterRender : function(params) {
				helper.activeProductsWithTab(params.$tab, params.getContainer(), params.activeClass);
			}
		};
		
		$.extend(_templateParams, templateParams);
		
		if(_templateParams.getContainer().length > 0) {
			_templateParams.afterRender(templateParams);
		} else {
			helper.renderProducts(data, _templateParams);
		};
	};
	
	// TODO : 하위호환 (추후 배포 다되면 activePanelWithTab로 jsp 변경필요)
	helper.activeProductsWithTab = function($tab, $container, activeClass) {
		helper.activePanelWithTab($tab, $container, activeClass);
	};
	
	helper.activePanelWithTab = function($tab, $tabPanel, activeClass) {
		if(!$tab || !$tabPanel) {
			return;
		};
		
		var _activeClass = activeClass || 'active';
		
		$tab.addClass(_activeClass).siblings().removeClass(_activeClass);
		$tabPanel.addClass(_activeClass).siblings().removeClass(_activeClass);
	};
	
	if(!$.ui){ $.ui = {}; };
	
	$.ui.helper = helper;	
})(window, jQuery);