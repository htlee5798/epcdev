(function( $, window, document, undefined ) {
	'strict';
	
	$.fn.wishAdds = function( options ) {
		var defaults = {
			checkbox : 'input[name=chkProductList]',
			apiName : 'wishAdds',
			categoryIds : [],
			productCodes : [],
			overSeaYns : [],
			callback : function() {}
		},
		$this = this,
		config = $.extend( true, {}, defaults, options );
		
		var $checkedBox = $( config.checkbox + ':checked' );
		
		(function init() {
			if( !global.isLogin( location.href ) ) {
				return false;
			}
			
			if( !global.isMember() ){
  				alert( view_messages.fail.noMember || "비회원은 위시리스트에 상품을 담을수 없습니다." );
  				return false;
  			}
			
			setParams();
			
			if( validate() ) {
				set();
			}
		})();
		
		function set() {
			$.api.set({
				apiName : config.apiName,
				data : {
					prodCds : config.productCodes,
					categoryIds : config.categoryIds,
					forgnDelyplYns : config.overSeaYns
				},
				isDim : false,
				traditional : true,
				successCallback : function( resData ) {
					$checkedBox.each( function( i, v ) {
						var $v = $( v ),
							$wrapper = $v.closest( '[data-panel=product]' ),
							$btnAdd = $wrapper.find( '[data-button-name=btnAddWish]' );
						
						$btnAdd.addClass( 'active' ).text( '위시리스트' );
					});
					
					config.callback();
					
					if(typeof RNB !== 'undefined'){
						RNB.wish.load();
					}
					
					$this.toast({
						layerClass : 'layerpop-toast-btm-type1 layerpop-target',
						contents : '<span class="selected">위시리스트에 담았습니다.</span>' 
					});
				}
			});
		}
		
		function setParams() {
			if( $checkedBox.length === 0 ) {
				return;
			}
			
			$checkedBox.each( function( i, v ) {
				var $v = $( v ),
					data = $v.data();
				
				config.categoryIds.push( data.categoryId );
				config.productCodes.push( data.productCode );
				config.overSeaYns.push( 'N' );
			});
		}
		
		function validate() {
			var returnValue = true;
			
			if( isEmpty() ) {
				alert( view_messages.error.notSelected ? view_messages.error.notSelected : '선택된 항목이 없습니다.' );
				
				returnValue = false;
			}
			
			return returnValue;
		}
		
		function isEmpty() {
			return config.categoryIds.length === 0 || config.productCodes.length === 0 || config.overSeaYns.length === 0;
		}
		
		return this;
	};
	
})( jQuery, window, document );