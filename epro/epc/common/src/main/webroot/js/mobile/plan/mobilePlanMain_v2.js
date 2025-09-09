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
	var loadingFooterClass = 'footer-loading';
	var $footer = $('#footer');
	var $themeSellFilter;
	var $listThemeSell;
	var _subCategoryId;
	var $currentPage;
	var _currentPage = 1;
	var _rowPerPage = 10;
	var _totalCount = _rowPerPage * 2;
	var infiniteScroll;
	var isLoading = false;

	$.templates('templateListThemeSell',
		'{{for PLANLIST}}' +
			'<a href="/mobile/plan/planDetail.do?CategoryID={{:CATEGORY_ID}}&MkdpSeq={{:MKDP_SEQ}}&pdivnSeq={{:DIVN_SEQ}}" class="item">' +
			'<p class="catchphrase">{{:CORNER_TITLE}}<br>{{:CORNER_TITLE2}}</p>' +
			'<p class="title">{{:BENEFIT_INFO}}<br><b>{{:BENEFIT_INFO2}}</b></p>' +
			'<p class="benefit">{{:EVENT_INFO}}</p>' +
			'<img src="//simage.lottemart.com{{:MO_IMG_PATH}}" alt="{{:CORNER_TITLE}}{{:CORNER_TITLE2}}" onerror="this.src=\'//simage.lottemart.com/v3/images/layout/themesell-list-noimg.png\'">' +
			'</a>' +
		'{{/for}}'
	);

	var _init = function() {
		$themeSellFilter = $('#themeSellFilter');
		$listThemeSell = $('#listThemeSell');
		_subCategoryId = $themeSellFilter.find('.active').attr('href').split('#')[1];
		$currentPage = $('#currentPage');
		_currentPage = Number($currentPage.val());
		isLoading = false;

		infiniteScroll = new _InfiniteScroll({
			container : $('.wrapper-main'),
			callback: function () {
				if (!isLoading) getListThemeSell();
			}
		});

		$themeSellFilter.on('click', 'a', function(e) {
			e.preventDefault();
			var $obj = $(this);

			// if ($obj.hasClass('active')) return;

			$obj.addClass('active').siblings().removeClass('active');
			_subCategoryId = this.hash.split('#')[1];
			_currentPage = 1;
			_totalCount = 1;
			$currentPage.val(_currentPage);

			$listThemeSell.empty();
			isLoading = false;
			getListThemeSell();
		});
		getListThemeSell();

		$(window).on('scroll.mainScroll', function () {
			infiniteScroll.setPosition($(window).scrollTop());
		});
	};

	function getListThemeSell() {
		if (!isLoading) {
			if (window.getHistoryBack && window.getHistoryBack()) {
				window.setHistoryBack(false);
				$themeSellFilter.find('.active').trigger('click');
				$footer.removeClass(loadingFooterClass);
				return;
			}
			if ( _totalCount < 1 ) return false;
			if ( _currentPage >= (_totalCount / _rowPerPage) + 1 ) {
				$footer.removeClass(loadingFooterClass);
				return;
			}

			isLoading = true;
			$.api.get({
				url: '/mobile/plan/ajax/planList.do',
				successCallback: renderListThemeSell,
				data: {
					currentPage: _currentPage,
					rowPerPage: _rowPerPage,
					subCategoryId: _subCategoryId,
					strCd: $.utils.config('main_store_code')
				}
			});
		}
	}

	function renderListThemeSell(responseData) {
		if (responseData.isSucceed) {
			_totalCount = Number( responseData.data.LIST.COUNT );

			if ( _totalCount < 1 && $listThemeSell.find('a').length < 1 ) {
				$listThemeSell.addClass('list-empty');
				$footer.removeClass(loadingFooterClass);
				return;
			}else{
				if ( _currentPage >= (_totalCount / _rowPerPage) + 1 ) return;

				var $div = document.createElement('div');
				var $docFrag = document.createDocumentFragment();

				$listThemeSell.removeClass('list-empty');
				$div.innerHTML = $.render.templateListThemeSell(responseData.data.LIST);
				$docFrag.appendChild($div);
				$listThemeSell.append($docFrag);

				_currentPage += 1;
				$currentPage.val(_currentPage);
			}
			isLoading = false;
		}
	}

	return {
		init: function() {
			$footer.addClass(loadingFooterClass);
			_init();
		}
	};
});
