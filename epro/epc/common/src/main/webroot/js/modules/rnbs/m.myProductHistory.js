(function( factory ) {
	'use strict';

	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ) );
	} else {
		factory( jQuery );
	}
}(function( $ ) {
	'use strict';

	var myProductHistory = (function() {
		return {
			get : _get,
			basket : _basket,
			goProductDetail : _goProductDetail,
			remove : _remove
		};

		function _get() {
			$.api.get({
				apiName : 'myHistory',
				data : {
					'histTypeCd' : '01',
					'ignoreType' : 'N'
				},
				successCallback : function( data ) {
					_render( data );
				}
			});
		}

		function _render( data ) {
			var lists = $.render.mobileRnbMyHistory( data );

			if( data && data.historyList.length > 0 ) {
				$( "#histprodcnt" ).html( data.historyList.length );
				$( "#gnbhist1" ).html( lists );
			} else {
				$("#histprodcnt").html("0");
				if( $.utils.config('Login_yn') != 'Y' &&  $.utils.config('Member_yn') != 'true'){
					var msg = ' <div class="list-empty">'
				            + '관심 있는 상품은 <br>로그인 후 확인하실 수 있습니다.'
				            + '<div class="wrap-btn"><a href="javascript:new_logins();" class="btn-common-color1">로그인</a></div>'
				            + '</div>';
					$("#GNTabItem1").html(msg);
				}else{
					$("#GNTabItem1").html(lists);
				}

			}

			$( "#gnbhist1" ).on( 'click', '.action-trash', function() {
				myProductHistory.remove( $( this ).data( 'seq' ) );

				return false;
			}).on( 'click', '.action-cart', function() {
				var $this = $( this ),
					data = $this.data();

				if( data.optionYn === 'N' ) {
					myProductHistory.basket( data.categoryId, data.productCode );
				} else {
					myProductHistory.goProductDetail( data.categoryId, data.productCode );
				}
				return false;
			});
		}

		function _basket( categoryId, productCode ) {
			var basketItems=[];

			basketItems.push({
				prodCd: productCode,			 // 상품코드
				itemCd: '001',			  // 단품코드
				bsketQty: 1,		// 주문수량
				categoryId: categoryId,		 // 카테고리ID
				nfomlVariation: null,			   // 옵션명, 골라담기의 경우 옵션명:수량
				overseaYn: 'N',					 // 해외배송여부
				prodCouponId: null,			 // 즉시할인쿠폰ID
				oneCouponId: null,			  // ONE 쿠폰ID
				cmsCouponId: null,			  // CMS 쿠폰ID
				markCouponId: null,			 // 마케팅제휴쿠폰ID
				periDeliYn: "N"					 // 정기배송여부
			});

			global.addBasket( basketItems, function( data ) {
				alert('선택한 상품을 장바구니에 담았습니다.');
                schemeLoader.loadScheme({key: 'basketCountUpdate'});
			});
		}

		function _goProductDetail( categoryId, productCode ) {
			if( confirm( "상품 상세에서 옵션 선택 후 담으실 수 있습니다." ) ) {
				location.href= $.utils.config( 'LMAppUrlM' ) + '/mobile/cate/PMWMCAT0004_New.do?CategoryID='+categoryId +'&ProductCD='+ productCode ;
			}
		}

		function _remove( seq ) {
			$.api.set({
				apiName : 'myHistoryRemove',
				data : {
					'histSeq' : seq
				},
				traditaionnal : true,
				successCallback : function( data ) {
					if( data.success === true ) {
						myProductHistory.get();
					} else {
						alert( data.message );
					}
				}
			});
		}
	})();

	window.myProductHistory = myProductHistory;
}));