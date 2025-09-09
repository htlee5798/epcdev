(function( $, window, document, undefined ) {
	'use strict';

	var $Dim = $( '<i style="position:fixed;top:0;right:0;bottom:0;left:0;z-index:11;" />' );

	$.support.cors = true;
	
	$.api = {
		urls : {
			basketOptions : '/api/item/options.do',
			dealOptions : '/api/items.do',
			wishToggle : '/quickmenu/api/mywish/toggle.do',
			wishAdds : '/quickmenu/api/mywish/add.do',
			wishDelete : '/quickmenu/api/mywish/delete.do',
			recommendKeywords : '/search/recommend_keywords.do',
			mainLnb : '/common/api/lnb_contents.do',
			
			basketList : '/basket/api/list.do',
			normalBasketList : '/basket/api/01/items.do',
			normalBasketCount : '/basket/api/01/items/count.do',
			myOrderList : '/api/orders.do',
			myOrderCount : '/api/orders/count.do',
			myOrderItemList : '/api/orders/items.do',
			commonCodeList : '/api/common/codes.do',
			
			basketAdd : '/basket/api/add.do',
			basketRemove : '/basket/api/remove.do',
			basketCount : '/basket/api/count.do',
			getCategoryList : '/api/category/list.do',
			getCategoryProductListPage : '/api/category/product/listPage.do',
			getTemplateProductListPage : '/api/template/product/listPage.do',
			appBaketCount : '/app/basketCountNew.do',
			basketAddDirect : '/basket/insertDirectBasket.do',
			defaultDeliInfo : '/quickmenu/api/defaultDeliInfo.do',
			myDelivery : '/quickmenu/api/mydelivery.do',
			myCoupon : '/quickmenu/api/mycoupon.do',
			myHistory : '/quickmenu/api/myhistory.do',
			myByHistory : '/quickmenu/api/mybyhistory.do',
			myHistoryAdd : '/quickmenu/api/myhistory/add.do',
			myHistoryRemove : '/quickmenu/api/myhistory/delete.do',
			isLogin : "/member/islogin.do",
			isMember : '/member/ismember.do',
			mainTodayList : '/index/ajax/todayInfoAjax.do',
			mainBestList : '/index/ajax/bestProdAjax.do',
			mainEventList : '/index/ajax/EventAjax.do',
			mainPlanList : '/index/ajax/PlanAjax.do',
			mainClearanceList : '/index/ajax/ClearanceAjax.do',
			mainSpecStyleList : '/index/ajax/SpecStyleListAjax.do',
			mainFootList : '/index/ajax/FootListAjax.do',
			mainDownCoupon : '/event/downCouponByCPBook.do',
			planListAll : '/plan/getPlanListAllAjax.do',
			planDetail : '/plan/ajax/productDetail.do',
			trendAppendList : '/trend/ajax/trendAppendList.do',
			mobileUpdateProductReview : '/product/ajax/updateProductReview.do',
			mobileProductReviewCount : '/mobile/product/ajax/mobileProductReviewCnt.do',
			mobileProductReviewList : '/product/ajax/MobileProductReviewList.do',
			mobileProductQuestion : '/product/ajax/MobileProductQuestion.do',
			mobileChangeSelectOptionBoxAction : '/mobile/product/ajax/changeSelectOptionBoxAction.do',
			mobileGnbMenu : '/inc/mobileGnbMenu.do',
			mobileGetMain : '/mobile/ajax/corners/detail.do',
			mobileDeliveryTimeInfo : '/mobile/product/ajax/mobileDeliveryTimeInfoAjax.do',
			mobileSmartOffer : '/mobile/product/ajax/mobileSmartOfferAjax.do',
			mobileCateProductList : '/mobile/cate/ajax/cateProductListAjax.do',
			mobilePeriodShippingSmartRecommendList : '/delivery/api/smart.do',
            mobilePeriodShippingPackageList : '/delivery/api/package/list.do',
			mobileHotList : '/mobile/ajax/todayhot/mobileHotList.do',
			mobileSellingPriceList : '/mobile/main/ajax/ajaxCateProdList.do',
            mobilePlanList : '/mobile/plan/ajax/planAjaxList.do',
            saveDeviceIdAndPushYnOnServer : '/app/saveMemberDeviceInfo.do',
            saveAdIdOnServer : '/app/saveADIDInfo.do',
            getRecommProdList : '/holidays/api/getRecommProdList.do',
            getPlanProdList : '/api/plan/product/listPage.do',
            getMobileNearestBuyHistoryV2 : '/mobile/v2/getMobileNearestBuyHistory.do',
            getPersonalProducts : '/mobile/ajax/todayhot/personal/products.do',
            getMobileHottime : '/mobile/ajax/todayhot/CornerMapWithContents.do',
            getMemberValue : '/mobile/delivery/sessionNm.do',
			getMainSpecialList : '/mobile/mainSpecial/list.do',
			mobileAnalyticsApi : '/mobile/main/ajax/analyticsApiAjax.do'
		},
		get : function( options ) {
			var _this = this,
				url = _this.urls[ options.apiName ] || options.url,
				isDim = false,
				request = null,
				isShowMoreBar = options.isShowMoreBar && options.$container,
				isShowLoadingBar = options.isShowLoadingBar && options.$container;
			
			if( url === undefined ) {
				return;
			}
			
			if( options.isDim === undefined ) {
				isDim = true;
			}
			
			request = $.ajax({
				type : 'GET',
				async : options.async === undefined ? true : options.async,
				dataType : options.dataType || 'json',
				url : url,
				data : options.data || {},
				cache: options.cache === undefined ? true : options.cache,
				beforeSend : function(xhr, settings) {
					if( isDim ) {
						$( 'body' ).append( options.$dim || $Dim );
					}
					
					if(isShowMoreBar) {
						options.$container.moreBar();
					}
					
					if(isShowLoadingBar) {
						options.$container.loadingBar();
					}
				},
				complete : function(xhr, state) {
					var $el = options.$dim || $Dim;
					
					if( $el.length > 0 ) {
						$el.remove();
					}
					
					if(isShowMoreBar) {
						options.$container.moreBar(false);
					}
					
					if(isShowLoadingBar) {
						options.$container.loadingBar(false);
					}

					if(options.complete) {
						options.complete(xhr, state);
					}
				},
				success : function( resData ) {
					if( options.successCallback ) {
						options.successCallback( resData );
					}
				},
				error : function( xhr, status, error ) {
					$.utils.log(error);
					$.utils.log(status);
					$.utils.log(xhr);
					if( options.errorCallback ) {
		            	options.errorCallback( xhr, status, error );
		            }
				}
			});
			return request;
		},
		set : function( options ) {
			var _this = this,
				url = _this.urls[ options.apiName ] || options.url,
				isDim = false,
				request = null;
			if( url === undefined ) {
				return;
			}
			
			if( options.isDim === undefined ) {
				isDim = true;
			}
			
			request = $.ajax({
				type : 'POST',
				url : url,
				headers: {},
		        async: options.async === undefined ? true : options.async,
		        crossDomain: true,
		        cache: false,
		        processData: true,
		        data: options.data || {},
		        traditional : options.traditional || false,
		        xhrFields: {
		            withCredentials: true
		        },
		        beforeSend: function(xhr, settings) {
		            xhr.setRequestHeader("content-type", 'application/x-www-form-urlencoded');
		            xhr.setRequestHeader('x-requested-with', 'XMLHttpRequest');
		            
		            if( isDim ) {
						$( 'body' ).append( options.$dim || $Dim );
					}
		        },
                complete : function(xhr, state) {
                    var $el = options.$dim || $Dim;

                    if( $el.length > 0 ) {
                        $el.remove();
                    }

                    if(options.complete) {
                        options.complete(xhr, state);
                    }
                },
		        success: function ( resData ) {

		        	if( options.successCallback ) {
						options.successCallback( resData );
					}
		        },
		        error: function(xhr, status, error) {
		        	console.log( error );
		            if( options.errorCallback ) {
		            	options.errorCallback( xhr, status, error );
		            }
		        }
			});
			return request;
		}
	};
	
})( jQuery, window, document );