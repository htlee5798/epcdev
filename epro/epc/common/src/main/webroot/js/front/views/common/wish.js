;(function(){
	
	"use strict"
	
	var defaults = {
			toggleUrl : null,
			multipleUrl : null,
			callback : null
		};
		
	var Wish = function(params) {
		var _Wish = this,
			_target = null;
		
		function init() {
			$.extend(true, defaults, params || {});
		}
		
		_Wish.toggle = function(categoryId, productCode) {
			if(!global.isLogin(document.location.href)) return false;
			
			var isMultiple = false,
				target = event.target ? event.target : event.srcElement,
				params = {
					categoryId : categoryId,
					productCode : productCode
				};
			
			_target = $(target);

			execute(params, isMultiple);
		};
		
		_Wish.adds = function(params) {
			if(!global.isLogin(document.location.href)) return false;
			
			var isMultiple = true,
				target = event.target ? event.target : event.srcElement;
			
			_target = $(target);
			
			execute(params, isMultiple);
		};
		
		function execute(params, isMultiple){
			var _params = getParams(params, isMultiple);

			if(validate(_params)) {
				ajax(_params);
			}
		}
		
		function getParams(params, isMultiple) {
			var defaultParams = {
					callback : null,
					error : errorHandler
				};
		
			if(isMultiple) {
				defaultParams.categoryIds = params.categoryIds;
				defaultParams.productCodes = params.productCodes;
				defaultParams.overSeaYns = [];
				
				if(params.productCodes.length > 0) {
					$.each(params.productCodes, function(i, v){
						defaultParams.overSeaYns.push('N');
					 });
				}
			}else {
				defaultParams.categoryId = params.categoryId;
				defaultParams.productCode = params.productCode;
				defaultParams.overSeaYn = 'N';
			}
			
			defaultParams.isMultiple = isMultiple;
			
			$.extend(true, defaultParams, defaults);
			
			if(typeof params.callback === 'function') {
				defaultParams.callback = params.callback;
			}
			
			return defaultParams;
		}
		
		function validate(params){
			var validateResult = false;
		
			if(params.isMultiple) {
				var condition = function(prodCds) {
					return prodCds.length < 1;
				}
				
				validateResult = getValidateResult(params.productCodes, fnJsMsg(view_messages.error.notSelected), condition);
				
			}else {
				var condition = function(prodCd) {
					return prodCd == '';
				}
				
				validateResult = getValidateResult(params.productCode, '선택한 상품이 없어 찜하기가 취소 되었습니다.', condition);
				
				if(validateResult){
					validateResult = getValidateResult(params.categoryId, '카테고리가 확인되지 않아 찜하기가 취소 되었습니다.', condition);
				};
			}
			
			
			function getValidateResult(data, message, condition) {
				var result = {
					message : message
				};
		
				if(data == null || condition(data)) {
					errorHandler(result);
					return false;
				};
				
				return true;
			}

			return validateResult;
		}
		
		function errorHandler(e) {
			var message = e.message;
			
			if(message === undefined) {
				message = $.parseJSON(e.responseText).message;
			}
			
			alert(message);
		}
			
		function ajax(params) {
			var datas = null,
				url = params.isMultiple ? params.multipleUrl : params.toggleUrl;
			
			if(params.isMultiple){
				datas = {
					prodCds : params.productCodes,
					categoryIds : params.categoryIds,
					forgnDelyplYns : params.overSeaYns
				};
			}else {
				datas = {
					prodCds : params.productCode,
					categoryIds : params.categoryId,
					forgnDelyplYns : params.overSeaYn
				};
			}
			
			$.ajax({
				url: url,
				data: datas,
				method: 'POST',
				traditional : true,
				success : function(res) {
					params.callback(_target, res);
				},
				error : errorHandler
			});
		}
		
		init();
	}
	
	window.Wish = Wish;
})();