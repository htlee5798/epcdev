$(document).ready( function() {

	//search result
	if ( front_uxsearch ) throw new Error('[ixError] "front_uxsearch"이 이미 존재하여 충돌이 발생!');

	var front_uxsearch = ( function () {

		var _searchWrap = $('#searchTabWrap'),
			_searchList = $('.searchList'),
			_searchTab = $( 'li a.tab' );

		var _crtSearchList = 0;

		resizeSearchWrap();

		_searchList.each( function ( index ) {
			if ( index != 0) $( this ).hide();
		});

		_searchTab.bind( 'click focusin focusout' , tabbtnMouseHandler );
		
		function tabbtnMouseHandler (e) {

			var idx = $( this ).parent().index();

			if ( e.type == 'click' ) {
				_searchTab.eq(_crtSearchList).removeClass( 'on');
				_searchList.eq(_crtSearchList).hide();
				_crtSearchList = idx;
				_searchList.eq(idx).show();
				$( this ).addClass( 'on');
				resizeSearchWrap();
			}
		}

		function resizeSearchWrap () {
			$('#searchTabWrap').height($('li a.on + .searchList').height() + 45);
		}

		var ua = navigator.userAgent.toLowerCase(),
		docMode = document.documentMode,
		isIE = false;

		if ( navigator.appName == 'Microsoft Internet Explorer' ) {
			var re = new RegExp( 'msie ([0-9]{1,}[\.0-9]{0,})' );
			if ( re.exec(ua) != null ) {
				if ( parseFloat( RegExp.$1 ) < 9 ) isIE = true;
			}
		}

	}());

});