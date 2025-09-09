$( document ).ready( function () {
	var mallList = function ( targetClass ) {
		var _$cont = $( 'div.uxBannerList' ).find( targetClass ),
			_$wrap = _$cont.find( 'div.listWrap' ),
			_$target = _$wrap.find( 'ul' ),
			_$prevBtn = _$cont.find( 'a.prev' ),
			_$nextBtn = _$cont.find( 'a.next' );

		_$prevBtn.on( 'click', function () {
			_$target.animate({ 'left' : - ( _$target.width() - _$wrap.width() ) + 'px' });
		});

		_$nextBtn.on( 'click', function () {
			_$target.animate({ 'left' : '0px' });
		});
	}

	mallList( 'div.brand' );
	mallList( 'div.partner' );
});

