$( document ).ready( function () {
	var mallList = function ( targetClass ) {
		var _$cbindt = $( 'div.uxBannerList' ).find( targetClass ),
			_$wrap = _$cbindt.find( 'div.listWrap' ),
			_$target = _$wrap.find( 'ul' ),
			_$prevBtn = _$cbindt.find( 'a.prev' ),
			_$nextBtn = _$cbindt.find( 'a.next' );

		_$prevBtn.bind( 'click', function () {
			_$target.animate({ 'left' : - ( _$target.width() - _$wrap.width() ) + 'px' });
		});

		_$nextBtn.bind( 'click', function () {
			_$target.animate({ 'left' : '0px' });
		});
	}

	mallList( 'div.brand' );
	mallList( 'div.partner' );
});

