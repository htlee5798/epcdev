(function( $, window, document, undefined ) {
	'use strict';
	
	$.fn.wishToggle = function( options ) {
		var defaults = {
			apiName : 'wishToggle',
			categoryId : '',
			productCode : '',
			overSeaYN : 'N',
			callback : function() {}
		},
		$this = this,
		config = $.extend( true, {}, defaults, options );
		
		(function init() {
			if( !global.isLogin( location.href ) ) {
				return false;
			}
			
			if( !global.isMember() ){
  				alert( view_messages.fail.noMember || "비회원은 위시리스트에 상품을 담을수 없습니다." );
  				return false;
  			}
			
			if( validate() ) {
				$.api.get({
					apiName : 'defaultDeliInfo',
					successCallback : function( resData ) {
						if( resData.defaultMemberAddrYn === 'N' ) {
							alert( resData.message );
							
							if ( opener && !opener.closed && window.opener != null ) {
								window.opener.RNB.delivery.openMyDelivery();
							}else{
								RNB && RNB.delivery.openMyDelivery();
							}
						}else{
							set();
						}
					}
				});
			}
		})();
		
		return this;
		
		function set() {
			$.api.set({
				apiName : config.apiName,
				data : {
					prodCds : config.productCode,
					categoryIds : config.categoryId,
					forgnDelyplYns : config.overSeaYN
				},
				isDim : false,
				successCallback : function( resData ) {
					var boardType = $( '#aList' ).hasClass( 'active' ) ? 'list' : 'image',
						toastMessage = '';
					
					if( resData.toggle === 'add' ) {
						if( boardType === 'image' ) {
							$this.addClass( 'active' );
						} else {
							$this.text( '위시리스트' );
						}
						
						toastMessage = '위시리스트에 담았습니다.';
					} else {
						if( boardType === 'image' ) {
							$this.removeClass( 'active' );
						} else {
							$this.text( '위시리스트' );
						}
						
						toastMessage = '위시리스트 담기가 취소되었습니다.';
					}
					
					config.callback();
					
					if(typeof RNB !== 'undefined'){
						RNB.wish.load();
					}
					
					$this.toast({
						layerClass : 'layerpop-toast-btm-type1 layerpop-target',
						contents : '<span class="selected">' + toastMessage + '</span>' 
					});
				},
				errorCallback : function( errors ) {
					var errorMessage = $.parseJSON( errors.responseText );
					
					if( errorMessage && errorMessage.message ) {
						alert( errorMessage.message );
					}
				}
			});
		}
		
		function validate() {
			var returnValue = true;
			
			if( isEmpty( config.productCode ) ) {
				alert( '선택한 상품이 없어 위시리스트 담기가 취소 되었습니다.' );
				returnValue = false;
			}
			
			if( isEmpty( config.categoryId ) ) {
				alert( '카테고리가 확인되지 않아 위시리스트 담기가 취소 되었습니다.' );
				returnValue = false;
			}
			
			if( $( "input:checkbox[name=forgnDeliYn]" ).length > 0 && $( "input:checkbox[name=forgnDeliYn]" ).is( ":checked" ) ){
				alert( view_messages.wish.fail || '해외배송의 경우 위시리스트 담기 기능을 사용할 수 없습니다.' );
				returnValue = false;
			}
			
			return returnValue;
		}
		
		function isEmpty( val ) {
			return val === null || val === '';
		}
	};
})( jQuery, window, document );