(function( $, window, document ) { 
	'use strict';
	/**
	 * See (http://jquery.com/).
	 * @name jQuery
	 * @class 
	 * See the jQuery Library  (http://jquery.com/) for full details.  This just
	 * documents the function and classes that are added to jQuery by this plug-in.
	 */

	/**
	 * See (http://jquery.com/)
	 * @name fn
	 * @class 
	 * See the jQuery Library  (http://jquery.com/) for full details.  This just
	 * documents the function and classes that are added to jQuery by this plug-in.
	 * @memberOf jQuery
	 */
	
	/**
	 * <h5>basket</h3>
	 * <p>장바구니/정기배송 시 사용 공통 모듈</p>
	 * 
	 * <h5>Require Options</h5>
	 * <ul>
	 * 	<li style="list-style-type:none"><strong>1. status : </strong>
	 * 		<ul>
	 * 			<li>'soldout' - 품절 상품일 경우</li>
	 * 			<li>'detail' - 상품상세로 이동</li>
	 * 			<li>'option' - 옵션이 있는 상품일 경우</li>
	 * 			<li>'save' - 장바구니 담기일 경우</li>
	 * 		</ul>
	 * 	</li>
	 * 	<li style="list-style-type:none"><strong>2. type : </strong>
	 * 		<ul>
	 * 			<li>'basket' - [ Default ] 장바구니 담기</li>
	 * 			<li>'regularBasket' - 정기배송 담기</li>
	 * 		</ul>
	 * 	</li>
	 * </ul>
	 * @class basket
	 * @memberOf jQuery.fn
	 * @param{Object} options - 모듈 초기 설정값
	 * @example <caption>Html</caption>
	 * <form>
	 * 	<input type="hidden" name="prodCd" value="8801123725618">
	 * 	<input type="hidden" name="categoryId" value="C20200040003">
	 * 	<input type="hidden" name="srcmkCd" value="8801123725618">
	 * 	<input type="hidden" name="maxQty" value="15">
	 * 	<input type="hidden" name="minQty" value="1">
	 * 	<input type="hidden" name="prodNm" value="요리하다 밀크앤어니언 핫도그">
	 * 	<input type="hidden" name="itemCd" value="001">
	 * 	<input type="hidden" name="nfomlVariationDesc" value="">
	 * 	<input type="hidden" name="optnLoadContent" value="">
	 * 	<input type="hidden" name="gubun" value="01">
	 * 	<input type="hidden" name="variationYn" value="N">
	 * 	<input type="hidden" name="deliType" value="01[!@!]03">
	 * 	<input type="hidden" name="safeQty" value="">
	 * 	<input type="tel" class="qty" name="orderQty" placeholder="1" value="1" maxlength="3" onkeyup="calculationOrderQty(this, '8801123725618','','1');">
	 * 	<button type="button" class="basket" data-type="basket" data-status="soldout" title="장바구니 담기"><i class="icon-common-cart">장바구니</i></button>
	 * </form>
	 * @example <caption>JS - init 1</caption>
	 * <script type="text/javascript">
	 * 	$( '.basket' ).basket();
	 * </script>
	 * @example <caption>JS - init 2</caption>
	 * <script type="text/javascript">
	 * 	$( '.basket' ).basket({
	 * 		statue : 'soldout',
	 * 		type : 'basket'
	 *		...
	 * 	});
	 * </script>
	 * 
	 */
	$.fn.basket = function ( options ) {
        	var $this = this,
        		$layerProduct = $( 'body' ).find( 'div[data-layer="mProductOpt"]' ),
        		layerPosition = $this.offset().top + $this.outerHeight() + 10,
        		_defaults = {
        			status : '',
        			categoryId : '',
        			prodCd : '',
        			type : '',
					gubun : '',
					optionYn : '',
					prodTypeCd : '',
					areaIdx : '',
					overseaYn : 'N',
					periDeliYn : ''
        		},
        		_config = $.extend( true, _defaults, options || $this.data() );
        	
        	var init = _config.type === 'basket' ? initBasket : initReqularBasket;
        	
        	if( _config.status === 'soldout' ) {
    			alert( '이 상품은 품절되었습니다.' );
    			return;
    		} else if( _config.status === 'detail' ) {
    			alert( '설치상품, 주문제작상품은 상품 상세에서 담으실 수 있습니다.' );
    			goProductDetailMobile( _config.categoryId, _config.prodCd, 'N', '' );//global 함수 사용
    			return;
    		}
        	
        	init();
        	
        	return this;
        	
        	function initBasket() {
        		var gubun = _config.gubun,
         			optionYn = _config.optionYn,
        			loc = location.href,
        			__categoryId = getCookie('__categoryId');

        		if(__categoryId) {
        			loc = loc + ((loc.indexOf('?') > -1) ? '&' : '?') + 'returnCategoryId=' + __categoryId;
        		}

        		if( _config.status === 'save' ) {
        			if( global.isLogin( loc ) ){
        				if( optionYn === 'Y' ) {
        					getOption( _config );
        				} else {
        					setBasket( gubun );
        				}
        			}
        		} else if( _config.status === 'option' ) {
        			if( global.isLogin( loc ) ){
						if(  $layerProduct.length > 0 ) {
							getOption( _config );						
						} else {
							setBasket( gubun );
						}
        			}
        		}
        	}
        	
        	function initReqularBasket() {
        		var $form = $this.closest( 'form' ),
      				formData = $form.serializeArray(),
      				categoryId = _config.categoryId,
      				itemCd = _config.itemCd,
      				prodCd = _config.prodCd,
      				bsketQty = formData.filter(function( v ) { return v.name === 'prodQty' })[ 0 ].value,
      				params = {
	      				prodCd : prodCd,
	      				itemCd : itemCd,
	      				bsketQty : bsketQty === '' ? 1 : bsketQty,
	      				categoryId : categoryId,
	      				overseaYn : _config.overseaYn,
	      				periDeliYn : _config.periDeliYn,
	      				nfomlVariation : '',
	      				prodCouponId : '',
	      				oneCouponId : '',
	      				cmsCouponId : ''
	      			},
	      			apiPath = _LMAppUrlM+"/basket/insertBasket.do",
	      			loc = location.href,
	      			isHttpsLocation = isHttpUrl( loc ),
	      			isHttpsApiPath = isHttpUrl( apiPath ),
	      			__categoryId = getCookie('__categoryId');

        		if(__categoryId) {
        			loc = loc + ((loc.indexOf('?') > -1) ? '&' : '?') + 'returnCategoryId=' + __categoryId;
        		}

	      		if( global.isLogin( loc ) ) {
	      		    if( isHttpsLocation && !isHttpsApiPath ){
	      		    	apiPath = apiPath.replace("http://", "https://");
	      		    }else if( !isHttpsLocation && isHttpsApiPath ){
	      		    	apiPath = apiPath.replace("https://", "http://");
	      		    }
	      		    
	      		    if( _config.soutYnReal === 'Y' || _config.soutYn === 'Y' ) {
	      		    	alert("이 상품은 품절입니다.");
	      		  		return;		
	      		    }
	
	      		    if(_config.optionYn == 'Y'){
		  				goProdDetail( categoryId, prodCd );
		  				return;
		  			}
	      		    
	      		    $.ajax({
	      		        type : 'POST' ,
	      		        url : apiPath ,
	      		        data : params,
	      		        dataType : 'json' ,
	      		        timeOut : (9 * 1000) ,
	      				cache : true,
	      				success: function( resData ) {
	      					if( resData[ 0 ].ERR_NO !== '0' ) {
	      						alert( resData[ 0 ].ERR_MSG );
	      					} else if( resData[ 0 ].ERR_NO === '0' ){
	      						if( confirm("선택하신 상품이 장바구니에 등록되었습니다. \n 지금 확인하시겠습니까?" ) ){
	      							location.href = _LMAppUrlM+"/mobile/mypage/PMWMMAR0003.do?basketType=B";
	      						}
	      					}
	      				}
	      		    });
	      		}
        	}
        	
    		function setBasket( gubun ) {
    			var $form = $this.closest( 'form' ),
    				formData = $form.serializeArray(),
    				prodIdx = '',
    				orderQty = '',
    				minQty = '',
    				maxQty = '',
    				prodCd = '',
    				itemCd = '',
    				categoryId = '';

    			for( var i = 0, len = formData.length; i < len; i++ ) {
      				var data = formData[ i ];
      				
      				if( data.name === 'prodCd' ) { prodCd = data.value; }
      				if( data.name === 'itemCd' ) { itemCd = data.value; }
      				if( data.name === 'orderQty' ) { orderQty = parseInt( data.value, 10 ); }
      				if( data.name === 'minQty' ) { minQty = parseInt( data.value, 10 ); }
      				if( data.name === 'maxQty' ) { maxQty = parseInt( data.value, 10 ); }
      				if( data.name === 'categoryId' ) { categoryId = data.value; }
      			}

    			if( isOnlyNumber( orderQty ) === false ) {
      				alert( fnJsMsg( view_messages.error.orderQtyNumber ) );
      				return;
      			} else if( orderQty < minQty ) {
      				alert( fnJsMsg( view_messages.error.productOrderQty, minQty, maxQty ) );
      				$form.find( '[name="orderQty"]' ).val( minQty );
      				return;
      			} else if( orderQty >maxQty ) {
      				alert( fnJsMsg( view_messages.error.productOrderQty, minQty, maxQty ) );
      				$form.find( '[name="orderQty"]' ).val( maxQty );
      				return;
      			}
    			
    			var basketItems = [{
      				prodCd: prodCd,
      				itemCd: itemCd,
      				bsketQty: orderQty,
      				categoryId: categoryId,
      				nfomlVariation: null,
      				overseaYn: 'N',
      				prodCouponId: null,
      				oneCouponId: null,
      				cmsCouponId: null,
      				markCouponId: null,
      				periDeliYn: "N"
      			}];
      			
   				global.addBasket( basketItems, function( resData ) {
   					if( gubun === "B") {
   						fn.basketCnt.call();
                        schemeLoader.loadScheme({key: 'basketCountUpdate'});
   						alert("선택하신 상품이 장바구니에 등록되었습니다.");
   					} else if( gubun === "D" ) {
   						fn.basketCnt.call();
                        schemeLoader.loadScheme({key: 'basketCountUpdate'});
      					var data = $.parseJSON( resData.responseText );
      					if ( data && data.message ) {
      						alert( data.message );
      						return;
      					}
   					}
   				});
    		}
    		//call ajax
    		function getOption( obj ) {
    			
    			$.ajax({
          			type: "post",
          			url: "/mobile/ajax/cate/mProductOptionListAjax.do",
          			data: {
          				'PROD_CD' : obj.prodCd,
          				'TYPE' : obj.gubun === 'D' ? 'order' : 'basket',
          				'PRODTYPECD' : obj.prodTypeCd,
          				'CATEGORYID' : obj.categoryId,
          				'PERIDELIYN'   : obj.periDeliYn
          			},
          			dataType: "html",
          			success: function( resData) {
          				renderLayer( resData );
          			}
      			});
    		}
    		function renderLayer( htmlStr ) {
    			$layerProduct.html( htmlStr );
  				
  				$this.addClass( 'active' );
  				$( 'body' )
					//.append( $layerProduct )
					.append( '<div class="mask"></div>' );
  				
  				$layerProduct.css( 'top', layerPosition ).fadeIn( 200 );
  				
  				$( '.mask, .js-close' ).on( 'click', function() {
  					$layerProduct.hide();
  					$this.removeClass( 'active' );
  					$( '.mask' ).remove();
  					return false;
  				});
    		}
    		
    		function isHttpUrl( url ) { return url.indexOf( 'https://' ) !== -1; }
    		
    		function getCookie( sKey ) {
    			if(!sKey) {
    				return null;
    			}

    			var cookie = document.cookie.replace(new RegExp("(?:(?:^|.*;)\\s*" + encodeURIComponent(sKey).replace(/[\-\.\+\*]/g, "\\$&") + "\\s*\\=\\s*([^;]*).*$)|^.*$"), "$1") || null;

    			if(cookie) {
	    			try {
	    				cookie = decodeURIComponent(cookie);
	    			} catch(e) {
	    				cookie = decodeURIComponent(unescape(cookie)); // escape 처리된 문자열이 저장된 경우, catch.
	    			}
    			}

    			return cookie;
    		}

        };
})( jQuery, window, document );
