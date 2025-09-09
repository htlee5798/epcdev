$( document ).ready( function () {
	var mallList = function ( targetClass ) {
		var _$cont = $( 'div.ux-footer-award' ).find( targetClass ),
			_$wrap = _$cont.find( 'div.list_wrap' ),
			_$target = _$wrap.find( 'ul' ),
			_$prevBtn = _$cont.find( 'a.prev' ),
			_$nextBtn = _$cont.find( 'a.next' );

		_$nextBtn.bind( 'click', function () {
			_$target.animate({ 'left' : - ( _$target.width() - _$wrap.width() ) + 'px' });
		});

		_$prevBtn.bind( 'click', function () {
			_$target.animate({ 'left' : '0px' });
		});
	}

	mallList( 'div.award-fr-1' );
});