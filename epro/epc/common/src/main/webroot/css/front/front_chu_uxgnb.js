/**
 * lotteMart - uxGnb
 * @package {uxGnb}
 * @version 20130709 (_khv)
*/

$(document).ready( function() {

	// gnb
	var _menu2 = $( 'ul#menu2' ),
		_cBtn = $( 'div#menu1' ).find( 'a.category_view' ),
		_cate = $( 'div#menu1' ).find( 'div.category_all_wrap' );
		_cuBtn = $( 'div#chuseokGnbWrap' ).find( '.gnbMenu' ),
		_cucate = $( 'div#chuseokGnbWrap' ).find( 'div.viewAllWrap' );

	var _idDep1,
		_idDep2,
		_idDep3,
		_idDep4;

	_menu2.find( 'ul.depth3, ul.depth4' ).hide();

	// select index
	$( 'ul#menu2 > li' ).each( function ( idx, list ) {
		$(this).bind( 'mouseenter focusin', function () {
			_idDep1 = idx;
			//console.log( _idDep1 )
			$(this).children( 'a' ).addClass( 'on' );

			if ( _cBtn.hasClass( 'view' ) ) {
				_cBtn.removeClass( 'view' );
				_cate.removeClass( 'open' ).animate({ 'height':'0' }, 800, 'easeInCirc' );
			}
			if ( _cuBtn.hasClass( 'tClose' ) ) {
				_cuBtn.removeClass( 'tClose' );
				_cucate.animate({ 'height' : '0' }, { duration : 400, easing : 'easeInCubic', queue : false, complete:function(){ _cucate.css("display", "none"); } });
			}
		});
		$(this).bind( 'mouseleave focusout', function () {
			$(this).children( 'a' ).removeClass( 'on' );
		});

		$(this).find( 'ul.depth2 > li' ).each( function ( idx, list ) {
			$(this).bind( 'mouseenter focusin', function () {
				_idDep2 = idx;
				//console.log( _idDep1, _idDep2 )
				$(this).children( 'a' ).addClass( 'on' );

				dep2Loc( $(this) );
			});
			$(this).bind( 'mouseleave focusout', function () {
				$(this).children( 'a' ).removeClass( 'on' );
			});

			$(this).find( 'ul.depth3 > li' ).each( function ( idx, list ) {
				$(this).bind( 'mouseenter focusin', function () {
					_idDep3 = idx;
					//console.log( _idDep1, _idDep2, _idDep3 )
					$(this).children( 'a' ).addClass( 'on' );

					dep3Loc( $(this) );
				});
				$(this).bind( 'mouseleave focusout', function () {
					$(this).children( 'a' ).removeClass( 'on' );
				});

				$(this).find( 'ul.depth4 > li' ).each( function ( idx, list ) {
					$(this).bind( 'mouseenter focusin', function () {
						_idDep4 = idx;
						//console.log( _idDep1, _idDep2, _idDep3, _idDep4 )
						$(this).children( 'a' ).addClass( 'on' );
					});
					$(this).bind( 'mouseleave focusout', function () {
						$(this).children( 'a' ).removeClass( 'on' );
					});
				});
			});
		});
	});

	// mouse event
	_menu2.find( 'li' ).bind( 'mouseenter focusin', function () {
		$(this).children( 'ul' ).show();
	});
	_menu2.find( 'li' ).bind( 'mouseleave focusout', function () {
		$(this).children( 'ul' ).hide();
	});

	// depth3 location
	function dep2Loc ( _this ) {
		var dep2H = _this.height() * _idDep2,
			dep3 = _this.find( 'ul.depth3 > li' );

		var dep3H = (dep3.height() * dep3.length) + 30;

		if ( dep2H + dep3H > 460 ) {
			dep3.parent().css( 'top', -( dep2H + dep3H - 460 + 15 ) + 'px' );
		}
	}

	function dep3Loc ( _this ) {
		var dep2H = _this.parent().parent().height() * _idDep2,
			dep3H = _this.height() * _idDep3,
			dep4 = _this.find( 'ul.depth4 li' ),
			dep4H = ( dep4.height() * dep4.length ) + 30;

		var total = dep2H + dep3H + parseInt(_this.parent().css('top')) + dep4H + 15;

		if ( total > 460 ) {
			dep4.parent().css( 'top', -( total - 460 + 15 ) + 'px' );
		}
	}

});