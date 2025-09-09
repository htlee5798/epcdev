/*
// LOTTEMART MOBILE SEARCH
// 2013 10 28 _khv
*/


var mobileSearch = ( function () {

	var _$result = $( 'div.result_list' ),
		_$btn = _$result.find( 'button' ),
		_$wrap = _$result.find( 'ul' ),
		_$list = _$wrap.find( 'li' );

	var _open = false,
		_openNum = 2;			// 처음에 보여지는 개수

	/////////////////////////////////////////////////////////////////////////
	
	_$btn.bind( 'click', btnHandler );
	_$btn.eq(1).hide();

	_$wrap.css( 'height', (_$list.outerHeight() * _openNum - 1) + 'px' );

	/////////////////////////////////////////////////////////////////////////

	function btnHandler () {
		if ( _open ) {
			_open = false;

			_$btn.show().eq(1).hide();
			_$wrap.transition( 'height:' + (_$list.outerHeight() * _openNum - 1) + 'px', 'height 0.5s ease' );
		} else {
			_open = true;
				
			_$btn.show().eq(0).hide();
			_$wrap.transition( 'height:' + (_$list.outerHeight() * _$list.length - 1) + 'px', 'height 0.5s ease' );
		}
	}

})();