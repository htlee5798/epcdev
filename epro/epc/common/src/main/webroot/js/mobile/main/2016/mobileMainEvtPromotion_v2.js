(function(root, factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD. Register as an anonymous module.
		define([], factory);
	} else if (typeof module === 'object' && module.exports) {
		// Node. Does not work with strict CommonJS, but
		// only CommonJS-like environments that support module.exports,
		// like Node.
		module.exports = factory();
	} else {
		// Browser globals (root is window)
		root.returnExports = factory();
	}
})(typeof self !== 'undefined' ? self : this, function() {
	var $wrapper,
		$ul,
		infiniteScroll,
		apiUrl,
		nowLoading,
		size,
		totalCount,
		totalPage,
		listEnd = false,
		param = {
			divnSeq: '',
			dispCd: '',
			'pagination.page': $('[name=currentPage]').val() || 1,
			'pagination.size': 10,
			strCd: $.utils.config('main_store_code'),
			zipSeq: $.utils.config('zipSeq')
		},
		$imgs;

	var init = function(url, prm) {
		$wrapper = $('#productList');
		$ul = $wrapper.find('ul');
		apiUrl = url;
		size = prm['pagination.size'];
		$imgs = $('.wrap-leaflet-contents').find('img');
		param = $.extend(
			{
				strCd: $.utils.config('main_store_code'),
				zipSeq: $.utils.config('zipSeq')
			},
			prm
		);
		infiniteScroll = new _InfiniteScroll({
			container: $ul,
			callback: function(e) {
				if (!nowLoading && !listEnd) {
					getPromotion(param);
				}
			}
		});
		showImg(prm.divnSeq);
		bindEvent();
		// $ul.empty();
		isEndPage();
		getPromotion(param);
	};

	var bindEvent = function() {
		$('.leaflet-filter').on('click', 'a', function(e) {
			e.preventDefault();
			$(this)
				.addClass('active')
				.siblings()
				.removeClass('active');
			var dataList = $(this).data();
			$wrapper.height($wrapper.height());
			$ul.empty();
			apiUrl = {
				CategoryID: dataList.categoryid,
				MkdpSeq: dataList.mkdpseq
			};
			setPage(1); //다른 서브카테고리로 넘어가면 무조건 1페이지부터 시작
			getPromotion(
				$.extend(param, {
					divnSeq: dataList.divnseq
					// 'type' : dataList.type
				})
			);
		});

		$wrapper.on('click', 'a[data-prod-cd]', function(e) {
			e.preventDefault();
			location.href = '/mobile/cate/PMWMCAT0004_New.do?ProductCD='+e.target.dataset.prodCd+'&CategoryID='+e.target.dataset.categoryId || '';
		});

		$(window).on('scroll.mainScroll', function() {
			infiniteScroll.setPosition($(window).scrollTop());
		});
	};
	var showImg = function(num) {
		if($imgs.length){
			$imgs
				.addClass('hidden')
				.filter('[data-divnSeq=' + num + ']')
				.removeClass('hidden');
		}
	};
	var getPromotion = function(param) {
		if (window.getHistoryBack && window.getHistoryBack()) {
			window.setHistoryBack(false);
			return;
		}
		nowLoading = true;
		$.api.get({
			url: '/categories/' + apiUrl.CategoryID + '/' + apiUrl.MkdpSeq + '/productList.do',
			// url: '/js/temporary/evtPromotion'+param['pagination.page']+'.json',
			data: param,
			isShowMoreBar: true,
			$container: $wrapper,
			successCallback: function(response) {
				nowLoading = false;
				if (response.error) {
					alert(response.error.message);
					return;
				}
				if (response.data) {
					totalCount = response.data.totalCount;
					$ul.append($.render['mobileProductList'](response.data));
					if (response.data.expressDeliStatus!=null) {
						if (!response.data.expressDeliStatus.isExpressDeliveryArea) {	//바로배송 가능 권역이 아닌경우 배송유형 > 바로배송 삭제
							$ul.find('[name=expressType]').remove();
						}
					}
				} else {
					totalCount = 0;
				}
				var page = parseInt(param['pagination.page']);
				var flagDestScrTop = ($('#mainGnb').height() + $('#header').height()) || ($('.ta-main-nav').height() + $('.ta-header-main').height());
				var destScrTop = $('.wrap-leaflet-contents').offset().top - $('.leaflet-filter').height() - flagDestScrTop + 3;
				if (page == 1 && $(window).scrollTop() > destScrTop) {
					$(window).scrollTop(destScrTop);
				}
				$wrapper.height('auto');
				showImg(param.divnSeq);
				setPage(page + 1);
				isEndPage();
				if (window.imageLazyLoad) {
					window.imageLazyLoad.setLazy();
					window.imageLazyLoad.load();
				}
			}
		});
	};
	var setPage = function(pageNum) {
		param['pagination.page'] = pageNum;
		$('[name=currentPage]').val(pageNum);
	};
	var isEndPage = function() {
		var $footer = $('#footer'),
			loadingFooterClass = 'footer-loading';
		var $empty = $ul.find('li.list-empty');
		if ($empty.length) {
			$footer.removeClass(loadingFooterClass);
			listEnd = true;
			return;
		} else {
			if (Number.isInteger(totalCount)) {
				totalPage = Math.ceil(totalCount / size);
				var pageNum = param['pagination.page'];
				if (pageNum > totalPage) {
					pageNum = totalPage;
					$footer.removeClass(loadingFooterClass);
					listEnd = true;
					$ul.append('<li class="list-empty active">마지막 상품입니다.</li>');
				} else {
					$footer.addClass(loadingFooterClass);
					listEnd = false;
				}
			}
		}
	};
	return {
		init: init,
		getPromotion: getPromotion
	};
});
