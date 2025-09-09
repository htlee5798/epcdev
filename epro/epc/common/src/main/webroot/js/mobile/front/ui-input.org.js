/* description || 2011.08.29 - 오영제
- 공통사항
  form 전송시 name 선언해야 정상작동 합니다.
  disabled 선언시 비활성

- select
  class 는 select 로 선언
  width, height 값 미 선언시 select 항목 width, height 값으로 적용
  height 200px 넘어갈경우 height 선언 없을시 default 200px
  selected 선언시 로딩시 selected 선언된 option 항목 selected 활성

- radio
  name 으로 radio 가 그룹이 형성
  label 선언시 label 활성화, 없으면 ignore
  checked 선언시 로딩시 해당 radio 객체 checked 활성

- checkbox
  checked 선언시 로딩시 해당 radio 객체 checked 활성
*/

var focusSelectObject; // 현재 활성화된 select 객체를 담을 변수

function browserCheck() {
	var vs = navigator.appVersion;
	var ie6 = new RegExp("MSIE 6.0");
	var ie7 = new RegExp("MSIE 7.0");
	var ie8 = new RegExp("MSIE 8.0");
	if ( vs.match(ie6) || vs.match(ie7) || vs.match(ie8) ) {
		return true;
	} else {
		return false;
	}
}

function browserNameCheck() {
	var vs = navigator.userAgent;
	vs = vs.toLowerCase();

	var browserName = '';

	if( vs.indexOf("firefox") != -1 ) { browserName = "firefox"; }
	else if( vs.indexOf("safari") != -1 ) { browserName = vs.indexOf("chrome") != -1 ? "chrome" : "safari"; }
	else if( vs.indexOf("msie") != -1 ) { browserName = "msie"; }
	else if( vs.indexOf("opera") != -1 ) { browserName = "opera"; }

	return browserName;
}

function selectReload(obj, target) {
	target = eval(target);

	// 두번째 인자에 따른 target 창
	var thisObj;
	if( target ) {
		thisObj = jQuery('#' + obj, target.document);
	} else {
		thisObj = jQuery('#' + obj);
	}

	if( jQuery(thisObj).length ) {
		var i;
		var targetObj = jQuery(thisObj).prev();
		var zIndex = jQuery(targetObj).find('>ul:eq(0)').css('z-index'); // 현재 생성된 option 항목의 z-index

		var str = '';
		var optionCnt = jQuery(thisObj).find('option').length;
		var optionValue, optionText, optionSelected;
		var firstOpt = jQuery(thisObj).find('option:selected').text(); // select 첫번째 option text
		if( !firstOpt ) {
			// selected 선언이 없으면 첫번째 option selected
			firstOpt = jQuery(thisObj).find('option:eq(0)').text();
		}
		jQuery(targetObj).find('.sel_left').text( firstOpt );

		// select option 항목 갯수 만큼 style select 항목영역 생성
		if( optionCnt > 0 ) {
			for( i=0; i<optionCnt; i++ ) {
				optionValue = jQuery(thisObj).find('option:eq(' + i + ')').val();
				optionText = jQuery(thisObj).find('option:eq(' + i + ')').text();

				// selected 선언된 option 항목 class=selected 선언
				if( jQuery(thisObj).find('option:eq(' + i + ')').attr('selected') ) {
					optionSelected = " class='selected'";
				} else {
					optionSelected = '';
				}

				str+= '<li' + optionSelected + ' ref="' + optionValue + '">' + optionText + '</li>';
			} // for End
		}

		jQuery(targetObj).find('>ul>li').remove();
		jQuery(targetObj).find('>ul').css({'width':'', 'height':''});
		jQuery(targetObj).find('>ul').html( str );

		if( target ) {
			target.selectInit(thisObj, zIndex, 'reload');
			target.selectEffect(thisObj, 'reload');
		} else {
			selectInit(thisObj, zIndex, 'reload');
			selectEffect(thisObj, 'reload');
		}

		targetObj = null; zIndex = null; str = null;
		optionCnt = null; optionValue = null; optionText = null; optionSelected = null;
		firstOpt = null; i = null;
	}
	thisObj = null;

	return false;
}

function selectEffect(obj, act) {
	if( act != 'reload' ) {
		// select 메뉴 클릭시 하위 option 리스트 보이기
		jQuery(obj).prev().find('.select_top').click(function() {
			jQuery(this).find('.sel_left').css({
				'background-color':'#ffffff',
				'color':'#999999'
			});
			jQuery(this).selectToggle();
			return false;
		});
	}

	// 하위 option 리스트 항목 클릭시 이벤트 처리
	jQuery(obj).prev().find('.option_list li').click(function() {
		jQuery(this).selectDataInsert();
		return false;
	});

	// option 항목 mouseover 효과
	jQuery(obj).prev().find('.option_list li').mouseover(function() {
		jQuery(this).addClass('hover');
		return false;
	});

	// option 항목 mouseout 효과
	jQuery(obj).prev().find('.option_list li').mouseout(function() {
		jQuery(this).removeClass('hover');
		return false;
	});
}

function selectInit(obj, zIndex, act) {
	// width Init
		var objWidth = 0;
		var thisObj = jQuery(obj).prev(); // .select_skin DIV 객체
		var selectObj = jQuery( thisObj ).find('.select_top'); // select title 영역
		var optionObj = jQuery( thisObj ).find('.option_list'); // select 항목 영역

		// select 영역 오른쪽 화살표 이미지 width 뺀 width 계산
		var selectWidth = parseInt( jQuery( selectObj ).width(), 10 );
		var optionWidth = parseInt( jQuery( optionObj ).innerWidth(), 10 );
		var rightWidth = jQuery( selectObj ).find('.sel_right').width();

		// select 객체에 width 값이 선언되었는지 판별
		var cssRegExp = new RegExp('width');
		var styleText = jQuery(obj).attr('style');
		var cssResult = 'N';
		if( cssRegExp.test(styleText) ) {
			var cssWidth = jQuery(obj).css('width');
			cssWidth = parseInt( cssWidth.replace('px', ''), 10 );
			// 사파리 브라우저 width 버그 수정
			var ua = navigator.userAgent;
			var safariRegExp = new RegExp('Safari');
			if( safariRegExp.test(ua) ) {
				var chromeRegExp = new RegExp('Chrome');
				if( !chromeRegExp.test(ua) ) {
					cssWidth = cssWidth + 25;
				}
				chromeRegExp = null;
			}

			optionWidth = cssWidth;
			selectWidth = cssWidth;

			cssResult = 'Y';
			ua = null; safariRegExp = null; cssWidth = null;
		} else {
			selectWidth = optionWidth > selectWidth ? optionWidth : selectWidth;
			optionWidth = optionWidth > selectWidth ? selectWidth : selectWidth;
			if( act == 'reload' ) {
				selectWidth = selectWidth - 5;
				optionWidth = optionWidth - 5;
			}
		}

		selectWidth = selectWidth - rightWidth; // 오른쪽 화살표 이미지 width 제거

		// padding-left 값 제거
		var paddingLeft = jQuery( selectObj ).find('.sel_left').css('padding-left');
		if( paddingLeft ) {
			paddingLeft = parseInt( paddingLeft.replace('px', ''), 10 );
		} else {
			paddingLeft = 0;
		}

		// padding-right 값 제거
		var paddingRight = jQuery( selectObj ).find('.sel_left').css('padding-right');
		if( paddingRight ) {
			paddingRight = parseInt( paddingRight.replace('px', ''), 10 );
		} else {
			paddingRight = 0;
		}

		var paddingWidth = paddingLeft + paddingRight;

		jQuery( selectObj ).parent().parent().css('line-height', '0');
		paddingLeft = null; paddingRight = null;

		selectWidth = selectWidth - paddingWidth; // 실제 select title 영역 width

		// option 항목의 글자수가 많아서 width 값이 늘어나면
		// option 과 select 영역의 width 값을 맞춘뒤 sel_right 영역의 width 만큼 늘려준다
		if( cssResult == 'N' && act != 'reload' ) {
			selectWidth = selectWidth + parseInt( jQuery( selectObj ).find('.sel_right').width(), 10 );
			optionWidth = optionWidth + parseInt( jQuery( selectObj ).find('.sel_right').width(), 10 );
		}

		jQuery( selectObj ).find('.sel_left').css('width', selectWidth + 'px');
		jQuery( optionObj ).css({
			'z-index':zIndex,
			'width':optionWidth + 'px' // select 항목 width 값 title width 값과 동일하게 설정
		});

		// li 영역에 ul width 값 선언 ( ie6에서 빈공간 click event 발생 안되는 문제 수정 )
		optionWidth = optionWidth - paddingWidth;

	// height Init
		// select 객체에 height 값 선언 안했을시 Default 는 css에 선언한 height 값 적용
		// css 에도 선언이 없을경우 default height 100px
		jQuery(optionObj).css('height','');
		var optionHeight = 0;
		jQuery(optionObj).find('>li').each(function() {
			optionHeight = optionHeight + parseInt( jQuery(this).outerHeight(), 10 );
		});
		var overflow_y = '';

		// select 객체 height css 선언여부 판별
		cssRegExp = new RegExp('height');
		styleText = jQuery(this).attr('style');
		var defaultHeight = 100; // css 선언 없을시 Default height
		if( cssRegExp.test(styleText) ) {
			var cssHeight = jQuery(this).css('height');
			cssHeight = parseInt( cssHeight.replace('px', ''), 10 );
			overflow_y		= optionHeight <= cssHeight ? 'hidden' : 'scroll';
			optionHeight	= optionHeight < cssHeight ? optionHeight : cssHeight;
			cssHeight = null;
		} else {
			overflow_y		= optionHeight <= defaultHeight ? 'hidden' : 'scroll';
			optionHeight	= optionHeight < defaultHeight ? optionHeight : defaultHeight;
		}

		// option 항목에 css 선언
		jQuery( optionObj ).css({
			'overflow-x':'hidden',
			'overflow-y':overflow_y,
			'height':optionHeight + 'px'
		});

		// disabled 선언이면 아무런 효과 없음
		if( jQuery(this).is(':disabled') ) {
			jQuery(thisObj).find('.select_top .sel_left').addClass('readonly_s');
			return false;
		}

		// select 객체 init
		jQuery(this).find('>option').each(function(){
			if( jQuery(this).is(':selected') == true ) {
				jQuery( selectObj ).find('.sel_left').text( jQuery(this).text() );
				return false;
			}
		});
		selectObj = null; optionObj = null;
}

function selectInitMultiple(obj, zIndex, act) {
	// width Init
	var objWidth = 0;
	var thisObj = jQuery(obj).prev(); // .select_skin DIV 객체
	var optionObj = jQuery( thisObj ).find('.option_list'); // select 항목 영역

	// select 영역 오른쪽 화살표 이미지 width 뺀 width 계산
	var optionWidth = parseInt( jQuery( optionObj ).innerWidth(), 10 );

	// select 객체에 width 값이 선언되었는지 판별
	var cssRegExp = new RegExp('width');
	var styleText = jQuery(obj).attr('style');
	var cssResult = 'N';
	if( cssRegExp.test(styleText) ) {
		var cssWidth = jQuery(obj).css('width');
		cssWidth = parseInt( cssWidth.replace('px', ''), 10 );
		// 사파리 브라우저 width 버그 수정
		var ua = navigator.userAgent;
		var safariRegExp = new RegExp('Safari');
		if( safariRegExp.test(ua) ) {
			var chromeRegExp = new RegExp('Chrome');
			if( !chromeRegExp.test(ua) ) {
				cssWidth = cssWidth + 25;
			}
			chromeRegExp = null;
		}

		optionWidth = cssWidth;

		cssResult = 'Y';
		ua = null; safariRegExp = null; cssWidth = null;
	} else {
		if( act == 'reload' ) {
			optionWidth = optionWidth - 5;
		}
	}

	// padding-left 값 제거
	var paddingLeft = jQuery( optionObj ).css('padding-left');
	if( paddingLeft ) {
		paddingLeft = parseInt( paddingLeft.replace('px', ''), 10 );
	} else {
		paddingLeft = 0;
	}

	// padding-right 값 제거
	var paddingRight = jQuery( optionObj ).css('padding-right');
	if( paddingRight ) {
		paddingRight = parseInt( paddingRight.replace('px', ''), 10 );
	} else {
		paddingRight = 0;
	}

	var paddingWidth = paddingLeft + paddingRight;

	jQuery( optionObj ).parent().css('line-height', '0');
	paddingLeft = null; paddingRight = null;

	optionWidth = optionWidth - paddingWidth; // 실제 select title 영역 width

	// option 항목의 글자수가 많아서 width 값이 늘어나면
	// option 과 select 영역의 width 값을 맞춘뒤 sel_right 영역의 width 만큼 늘려준다
	if( cssResult == 'N' && act != 'reload' ) {
		//optionWidth = optionWidth + parseInt( jQuery( selectObj ).find('.sel_right').width(), 10 );
	}

	jQuery( optionObj ).css({
		'z-index':zIndex,
		'width':optionWidth + 'px' // select 항목 width 값 title width 값과 동일하게 설정
	});

	jQuery( optionObj ).parent().css({
		'width':optionWidth + 'px'
	});

	// li 영역에 ul width 값 선언 ( ie6에서 빈공간 click event 발생 안되는 문제 수정 )
	optionWidth = optionWidth - paddingWidth;

	// height Init
	// select 객체에 height 값 선언 안했을시 Default 는 css에 선언한 height 값 적용
	// css 에도 선언이 없을경우 default height 200px
	var optionHeight = parseInt( jQuery(optionObj).height(), 10 );
	var overflow_y = '';

	// select 객체 height css 선언여부 판별
	cssRegExp = new RegExp('height');
	styleText = jQuery(obj).attr('style');
	var defaultHeight = 200; // css 선언 없을시 Default height
	if( cssRegExp.test(styleText) ) {
		var cssHeight = jQuery(obj).css('height');
		cssHeight = parseInt( cssHeight.replace('px', ''), 10 );
		overflow_y		= optionHeight < cssHeight ? 'hidden' : 'scroll';
		optionHeight	= optionHeight < cssHeight ? optionHeight : cssHeight;
		delete cssHeight;
	} else {
		overflow_y		= optionHeight <= defaultHeight ? 'hidden' : 'scroll';
		optionHeight	= optionHeight < defaultHeight ? optionHeight : defaultHeight;
	}

	if( overflow_y == 'scroll' ) {
		optionWidth = optionWidth + 20;
	}

	// option 항목에 css 선언
	jQuery( optionObj ).css({
		'overflow-x':'hidden',
		'overflow-y':overflow_y,
		'width':optionWidth + 'px',
		'height':optionHeight + 'px'
	});

	jQuery( optionObj ).parent().css({
		'height':optionHeight + 'px'
	});

	// disabled 선언이면 아무런 효과 없음
	if( jQuery(obj).is(':disabled') ) {
		jQuery(optionObj).addClass('readonly_s');
		return false;
	}

	optionObj = null;
}

function selectDiff(thisObj, selectObj, type) {
	type = eval( type );
	var thisValue	= jQuery(thisObj).attr('ref');
	var thisText	= jQuery(thisObj).text();

	jQuery( selectObj ).find('option').each(function() {
		if( jQuery(this).val() == thisValue && jQuery(this).text() == thisText ) {
			jQuery(this).attr('selected', type);
		}
	});
}

jQuery.fn.selectCreate = function(){ // select 객체 내용을 토대로 style select 생성 ( html 소스 return )
	var arrowImg = '/images/front/common/btn-arrow-select.gif'; // 오른쪽 화살표 이미지
	var thisObj = jQuery(this); // select 객체
	var firstOpt = jQuery(this).find('option:selected').text(); // select 첫번째 option text
	if( !firstOpt ) {
		firstOpt = jQuery(this).find('option:eq(0)').text();
	}

	// style select 객체 생성
	var selectHtml = '';
	selectHtml+= '<div class="select_top">';
	selectHtml+= '	<div class="sel_left" style="white-space:nowrap;">' + firstOpt + '</div>';
	selectHtml+= '	<div class="sel_right"><img src="/images/front/common/btn-arrow-select.gif"></div>';
	selectHtml+= '</div>';

	var optionCnt = jQuery(thisObj).find('option').length;
	var optionValue = '';
	var optionText = '';
	var optionSelected = '';

	// select option 항목 갯수 만큼 style select 항목영역 생성
	if( optionCnt > 0 ) {
		selectHtml+= '<ul class="option_list" style="white-space:nowrap;">';

		jQuery(thisObj).find('option').each(function(){
			if( jQuery(this).is(':selected') ) {
				oprionSelected = " class='selected'";
			} else {
				oprionSelected = "";
			}
			selectHtml+= '<li' + oprionSelected + ' ref="' + jQuery(this).val() + '">' + jQuery(this).text() + '</li>';
		});
		selectHtml+= '</ul>&nbsp;';
	}

	// 사용한 변수 Init
	optionCnt = null; optionValue = null; optionText = null;
	arrowImg = null; thisObj = null; thisName = null; firstOpt = null;

	return selectHtml;
};

jQuery.fn.checkboxInit = function(){ // checkbox init
	if( jQuery(this).is(':disabled') ) {
		jQuery(this).prev().addClass('readonly_c');
	}

	if( jQuery(this).is(':checked') ) {
		jQuery(this).prev().addClass('selected_c');
	}
};

jQuery.fn.radioInit = function(){
	if( jQuery(this).is(':disabled') ) {
		jQuery(this).prev().addClass('readonly_r');
	}

	if( jQuery(this).is(':checked') ) {
		jQuery(this).prev().addClass('selected_r');
	}
};

jQuery.fn.extend({ // select, radio, checkbox 스타일 적용 함수 모음
	selectStyle: function() {
		var zIndex = 5000; // 각 select 순서별로 z-index 부여
		var tabIndex = 1;

		jQuery(this).hide().each(function (){
			if( !jQuery(this).attr('multiple') ) {
				jQuery("<div>")
					.attr('class', 'select_skin')
					.css({
						'display':'block'
					})
					.html( jQuery(this).selectCreate() )
					.insertBefore(this);
				//alert( jQuery(this).prev().height() );

				// select 사이즈 초기화
				selectInit(this, zIndex);

				if( jQuery(this).is(':disabled') == false ) {
					selectEffect(this);
					jQuery(this).prev().find('.select_top .sel_left').attr('tabindex', tabIndex);
					tabIndex++;
				}
			} else {
				var thisObj = jQuery(this); // select 객체

				// style select 객체 생성
				var selectHtml = '';

				var optionCnt = jQuery(thisObj).find('option').length;
				var optionValue = '';
				var optionText = '';
				var optionSelected = '';

				// select option 항목 갯수 만큼 style select 항목영역 생성
				if( optionCnt > 0 ) {
					selectHtml+= '<ul class="option_list noSelect" style="display:block;white-space:nowrap;">';

					jQuery(thisObj).find('option').each(function(){
						if( jQuery(this).is(':selected') ) {
							oprionSelected = " class='selected'";
						} else {
							oprionSelected = "";
						}
						selectHtml+= '<li' + oprionSelected + ' ref="' + jQuery(this).val() + '">' + jQuery(this).text() + '</li>';
					});
					selectHtml+= '</ul>&nbsp;';
				}

				jQuery("<div>")
					.attr('class', 'select_skin')
					.css({
						'display':'block',
						'position':'relative'
					})
					.html( selectHtml )
					.insertBefore(this);

				selectInitMultiple(thisObj, zIndex);

				var nowObj;
				var selectObj = jQuery(this);
				var ulObj = jQuery(this).prev().find('.option_list');

				var multiResult = 'N';
				var mouseDown = 'N'; var ctrlDown = 'N'; var shiftDown = 'N';

				var thisValue, thisText;

				jQuery(this).prev().find('.option_list li').bind('mousedown', function() {
					mouseDown = 'Y';
					nowObj = jQuery(this);

					if( ctrlDown == 'Y' ) {
						jQuery( ulObj ).attr('ctrlIdx', jQuery(this).index() );
						var classRegExp = new RegExp('selected');
						var classText = jQuery(this).attr('class');
						if( classRegExp.test(classText) ) {
							jQuery(this).removeClass('selected');
							selectDiff( jQuery(this), jQuery( selectObj ), false );
						} else {
							jQuery(this).addClass('selected');
							selectDiff( jQuery(this), jQuery( selectObj ), true );
						}
					} else if( shiftDown == 'Y' ) {
						jQuery( ulObj ).attr('shiftIdx', jQuery(this).index() );

						var ctrlIdx = parseInt( jQuery( ulObj ).attr('ctrlIdx'), 10 );
						var shiftIdx = parseInt( jQuery( ulObj ).attr('shiftIdx'), 10 );
						var nowIdx;

						if( isNaN( shiftIdx ) ) { return false; }

						var firstIdx, lastIdx;

						if( ctrlIdx < shiftIdx ) {
							firstIdx = ctrlIdx;
							lastIdx = shiftIdx;
						} else if( ctrlIdx > shiftIdx ) {
							firstIdx = shiftIdx;
							lastIdx = ctrlIdx;
						} else {
							firstIdx = ctrlIdx;
							lastIdx = shiftIdx;
						}

						jQuery( ulObj ).find('li').each(function() {
							nowIdx = parseInt( jQuery(this).index(), 10 );

							if( firstIdx <= nowIdx && nowIdx <= lastIdx ) {
								jQuery(this).addClass('selected');
								selectDiff( jQuery(this), jQuery( selectObj ), true );
							} else {
								jQuery(this).removeClass('selected');
								selectDiff( jQuery(this), jQuery( selectObj ), false );
							}
						});
					} else {
						jQuery( ulObj ).attr('ctrlIdx', jQuery(this).index() );
						jQuery( ulObj ).find('li').each(function() {
							if( jQuery(this).val() == jQuery(nowObj).val() && jQuery(this).text() == jQuery(nowObj).text() ) {
								jQuery(this).addClass('selected');
								selectDiff( jQuery(this), jQuery( selectObj ), true );
							} else {
								jQuery(this).removeClass('selected');
								selectDiff( jQuery(this), jQuery( selectObj ), false );
							}
						});
					}
				});

				jQuery( thisObj ).prev().find('.option_list li').bind('mouseup', function() {
					mouseDown = 'N';
				});

				// option 항목 mouseover 효과
				jQuery( thisObj ).prev().find('.option_list li').bind('mouseover', function(e) {
					e.preventDefault ? e.preventDefault() : e.returnValue = false;

					if( mouseDown == 'Y' ) {
						jQuery( ulObj ).attr('shiftIdx', jQuery(this).index() );
						if( ctrlDown == 'Y' ) {
							jQuery( ulObj ).attr('ctrlIdx', jQuery(this).index() );
							var classRegExp = new RegExp('selected');
							var classText = jQuery(this).attr('class');
							if( classRegExp.test(classText) ) {
								jQuery(this).removeClass('selected');
								selectDiff( jQuery(this), jQuery( selectObj ), false );
							} else {
								jQuery(this).addClass('selected');
								selectDiff( jQuery(this), jQuery( selectObj ), true );
							}
						} else {
							var ctrlIdx = parseInt( jQuery( ulObj ).attr('ctrlIdx'), 10 );
							var shiftIdx = parseInt( jQuery( ulObj ).attr('shiftIdx'), 10 );
							var nowIdx;

							if( isNaN( shiftIdx ) ) { return false; }

							var firstIdx, lastIdx;

							if( ctrlIdx < shiftIdx ) {
								firstIdx = ctrlIdx;
								lastIdx = shiftIdx;
							} else if( ctrlIdx > shiftIdx ) {
								firstIdx = shiftIdx;
								lastIdx = ctrlIdx;
							} else {
								firstIdx = ctrlIdx;
								lastIdx = shiftIdx;
							}

							jQuery( ulObj ).find('li').each(function() {
								nowIdx = parseInt( jQuery(this).index(), 10 );

								if( firstIdx <= nowIdx && nowIdx <= lastIdx ) {
									jQuery(this).addClass('selected');
									selectDiff( jQuery(this), jQuery( selectObj ), true );
								} else {
									jQuery(this).removeClass('selected');
									selectDiff( jQuery(this), jQuery( selectObj ), false );
								}
							});
						}
					}
					return false;
				});

				// option 항목 mouseout 효과
				jQuery( thisObj ).prev().find('.option_list li').bind('mouseout', function() {
					return false;
				});

				jQuery(this).prev().find('.option_list li').bind('dragstart', function(e) {
					e.preventDefault ? e.preventDefault() : e.returnValue = false;
				});

				jQuery(document).bind('keydown', function(e) {
					if( e.keyCode == 17 ) { ctrlDown = 'Y'; }
					if( e.keyCode == 16 ) { shiftDown = 'Y'; }
				});

				jQuery(document).bind('keyup', function(e) {
					if( e.keyCode == 17 ) { ctrlDown = 'N'; }
					if( e.keyCode == 16 ) { shiftDown = 'N'; }
				});

				dragDisable( jQuery(ulObj) );

				if( jQuery(this).is(':disabled') == false ) {
					jQuery(this).prev().attr('tabindex', tabIndex);
					tabIndex++;
				}
			}
			zIndex--;
		});

		// 사용했던 변수 제거
		zIndex = null; selectHtml = null; selectStr = null; tabIndex = null;
	},
	selectToggle: function() { // select 클릭시 select 객체 위치 기준 아래쪽에 option 객체 보이기
		// 다른 select 객체 click 시 이전에 활성화된 select 는 display none 처리
		if( jQuery(this).get(0) != jQuery(focusSelectObject).get(0) ) {
			jQuery(focusSelectObject).parent().find('.option_list').hide();
			focusSelectObject = jQuery(this);
		}

		if( jQuery(this).parent().find('.option_list').length ) {
			var optionObj = jQuery(this).parent().find('.option_list');

			var selectHeight = parseInt( jQuery(this).height(), 10 ); // select 객체 height

			var optionLeft = parseInt( jQuery(this).position().left, 10 ); // select 객체 left
			var optionTop = parseInt( jQuery(this).position().top, 10 ); // select 객체 top

			var borderWidth = jQuery(this).css('border-bottom-width');
			borderWidth = parseInt( borderWidth.replace('px', ''), 10 );
			optionTop = optionTop + selectHeight + borderWidth; // top + select 객체 height + border-width

			var winHeight = parseInt( jQuery(document).height(), 10 );
			var optionHeight = parseInt( jQuery( optionObj ).height(), 10 );

			var optionTop2 = optionTop + optionHeight;
			if( winHeight < optionTop2 ) {
				optionTop = parseInt( jQuery(this).position().top, 10 ); // select 객체 top 재설정
				optionTop = optionTop - borderWidth - optionHeight - 4; // top 위치 역으로 올리기
			}

			if( jQuery(optionObj).css('display')  == 'none' ) {
				jQuery(optionObj).css({
					'left' : optionLeft + 'px',
					'top' : optionTop + 'px',
					'position' : 'absolute'
				});

				var optTop = 0;
				var optHeight, optPaddingTop, optPaddingBottom;
				var optCnt = 0;
				var browserName = browserNameCheck();

				jQuery(optionObj).find('li').each(function() {
					if( jQuery(this).attr('class') == 'selected' ) {
						return false;
					} else {
						optHeight = parseInt( jQuery(this).innerHeight(), 10 );

						optPaddingTop = jQuery(this).css('padding-top');
						optPaddingTop = parseInt( optPaddingTop.replace('px'), 10 );

						optPaddingBottom = jQuery(this).css('padding-bottom');
						optPaddingBottom = parseInt( optPaddingBottom.replace('px'), 10 );

						optTop = optTop + ( optHeight + optPaddingTop + optPaddingBottom );
						switch( browserName ) {
							case 'msie' : { optTop = optTop + 2; break; }
							case 'firefox' : { optTop = optTop + 3; break; }
							case 'chrome' : { optTop = optTop + 2; break; }
							case 'safari' : { optTop = optTop + 2; break; }
							case 'opera' : { optTop = optTop + 3; break; }
						}

						optCnt++;
					}
				});

				jQuery(optionObj).show().scrollTop(optTop);
			} else {
				jQuery(optionObj).hide();
			}

			optionObj = null; selectHeight = null; optionLeft = null; optionTop = null;
			borderWidth = null; winHeight = null; optionHeight = null; optionTop2 = null;
		}
		return false;
	},
	selectDataInsert: function() {
		var selectObj = jQuery(this).parent().parent();
		var orgObj = jQuery(selectObj).next();
		var thisText = jQuery(this).text();
		var thisValue = jQuery(this).attr('ref');

		jQuery( selectObj ).find('.select_top .sel_left').text(thisText);
		jQuery( selectObj ).find('.select_top .sel_left').attr('ref', thisValue);

		var optText = ''; var optValue = '';
		jQuery(this).parent().find('>li').each(function(){
			optText = jQuery(this).text();
			optValue = jQuery(this).attr('ref');
			if( thisText == optText && thisValue == optValue ) {
				jQuery(this).addClass('selected');
			} else {
				jQuery(this).removeClass('selected');
			}
		});

		jQuery(orgObj).find('>option').each(function(){
			if( jQuery(this).text() == thisText && jQuery(this).val() == thisValue ) {
				jQuery(this).attr('selected', true);
			} else {
				if( !browserCheck() ) {
					jQuery(this).attr('selected', false);
				}
			}
		});

		// 2011.09.05 오영제 onchange 이벤트 트리거 삽입
		if( jQuery(orgObj).attr('onchange') ) {
			jQuery(orgObj).trigger('onchange');
		}

		jQuery(this).parent().hide();

		jQuery( selectObj ).find('.select_top .sel_left').trigger('focus');

		selectObj = null; orgObj = null; thisText = null; thisValue = null;
		optText = null; optValue = null;
		return false;
	},
	selectReadonly: function(act) {
		var className = 'readonly_s';
		var readonlyClass = new RegExp(className);
		var objClass = jQuery(this).attr('class');
		var targetObj = jQuery(this).parent().find('.select_skin .select_top');
		var selectObj = jQuery(this).parent().find('.select_skin .option_list');

		// readonly 설정
		if( act == 'Y' ) {
			jQuery( targetObj ).unbind('click');
			jQuery( this ).addClass('readonly_s');
			jQuery(this).parent().find('.select_skin').addClass('readonly_s');

			// div, select init
			jQuery(this).find('>option').each(function(){
				jQuery(this).attr('selected', false);
			});

			// select 하위 항목들 class Init
			jQuery( selectObj ).find('>li').each(function(){
				jQuery(this).removeClass('selected');
			});

			var initValue = jQuery( selectObj ).find('>li:eq(0)').attr('ref');
			var initText = jQuery( selectObj ).find('>li:eq(0)').text();

			jQuery( targetObj ).find('>div:eq(0)').attr('ref', initValue);
			jQuery( targetObj ).find('>div:eq(0)').text(initText);

			initValue = null; initText = null;
		} else { // readonly 해제
			jQuery( targetObj ).click(function() {
				jQuery( this ).selectToggle();
			});
			jQuery( this ).removeClass('readonly_s');
			jQuery( this ).parent().find('.select_skin').removeClass('readonly_s');
		}

		className = null; readonlyClass = null; objClass = null; targetObj = null; selectObj = null;
		return false;
	},
	radioStyle: function() {
		jQuery(this).hide().each(function (){
			jQuery("<span>").addClass('radio').insertBefore(this);

			jQuery(this).radioInit(); // 라디오버튼 init : checked, disabled 처리

			var objId = jQuery(this).attr('id'); // radio 객체의 id

			var thisObj = jQuery(this);
			var spanObj = jQuery(this).prev(); // 라디오버튼 이미지
			var labelObj = ''; // label 객체
			if( objId ) {
				// radio 버튼 id 와 for 값이 같은 객체를 찾는다.
				jQuery(document).find('label').each(function(){
					if( jQuery(this).attr('for') == objId ) {
						labelObj = jQuery(this);
					}
				});
			}

			if( jQuery(this).attr('name') ) {
				if( jQuery(this).is(':disabled') == false ) {
					// radio 이미지객체에 효과주기
					if( jQuery(spanObj).length ) {
						jQuery(spanObj).css('cursor', 'pointer'); // 마우스커서 pointer로 변경
						jQuery(spanObj).bind('click', function(){
							jQuery(this).radioToggle(thisObj); // click 이벤트
							return false;
						});
					}

					// radio label에 효과주기
					if( jQuery(labelObj).length ) {
						jQuery(labelObj).css('cursor', 'pointer'); // 마우스커서 pointer로 변경
						jQuery(labelObj).bind('click', function(){
							jQuery(this).radioToggle(thisObj); // click 이벤트
							return false;
						});
					}
				} else {
					// disabled 선언한 객체 효과 없음
					if( jQuery(labelObj).length ) {
						jQuery(labelObj).css('cursor', 'default');
					}
				}
			}
		});
	},
	radioToggle: function(inputObj) {
		var inputName = jQuery(inputObj).attr('name');
		var prevObj = '';
		var result = '';

		if( jQuery(inputObj).attr('onclick') ) {
			jQuery(inputObj).trigger('onclick');
		}

		if( jQuery(inputObj).is(':checked') == true ) {
			return false;
		}

		jQuery('input:radio[name=' + inputName + ']').each(function(){
			if( jQuery(inputObj).get(0) == jQuery(this).get(0) ) {
				// 2011-09-11 IE 6, 7 에서 input checkbox 객체에 checked 속성 적용 안되는 현상 수정
				jQuery(this).attr('checked', true);
				jQuery(this).prev().addClass('selected_r');
			} else {
				if( !browserCheck() ) {
					jQuery(this).attr('checked', false);
				}
				jQuery(this).prev().removeClass('selected_r');
			}
		});
		return false;
	},
	radioReadonly: function(act) {
		var objName = jQuery(this).attr('name');
		var opt = new Array();
		opt.checked = '0px -20px';
		opt.unchecked = '0px 0px';

		if( act == 'Y' ) {
			jQuery(document).find('input[name=' + objName + ']').each(function(){
				jQuery(this).addClass('readonly_r');

				// 이미지 radio 버튼 click 이벤트 제거
				jQuery(this).prev().unbind('click');

				// label 영역 click 이벤트 제거
				jQuery(this).next().unbind('click');

				// radio 버튼의 checked 속성 제거
				jQuery(this).attr('checked', false);

				jQuery(this).prev().css('background-position', opt.unchecked);
			});
		} else {
			jQuery(document).find('input[name=' + objName + ']').each(function(){
				jQuery(this).removeClass('readonly_r');

				jQuery(this).prev().click(function() {
					jQuery(this).radioToggle(opt, 'span');
				});

				jQuery(this).next().click(function() {
					jQuery(this).radioToggle(opt, 'label');
				});
			});
		}
	},
	checkStyle: function() {
		jQuery(this).hide().each(function (){
			jQuery("<span>").addClass('checkbox').insertBefore(this);

			jQuery(this).checkboxInit();

			var objId = jQuery(this).attr('id'); // radio 객체의 id

			var thisObj = jQuery(this);
			var spanObj = jQuery(this).prev(); // 라디오버튼 이미지
			var labelObj = ''; // label 객체
			if( objId ) {
				// radio 버튼 id 와 for 값이 같은 객체를 찾는다.
				jQuery(document).find('label').each(function(){
					if( jQuery(this).attr('for') == objId ) {
						labelObj = jQuery(this);
					}
				});
			}

			if( jQuery(this).is(':disabled') == false ) {
				if( jQuery(spanObj).length ) {
					jQuery(spanObj).css('cursor', 'pointer');
					jQuery(spanObj).bind('click', function() {
						jQuery(this).checkboxToggle(spanObj, thisObj);
						return false;
					});
				}

				if( jQuery(labelObj).length ) {
					jQuery(labelObj).css('cursor', 'pointer');
					jQuery(labelObj).bind('click', function() {
						jQuery(labelObj).checkboxToggle(spanObj, thisObj);
						return false;
					});
				}
			} else {
				if( jQuery(labelObj).length ) {
					jQuery(labelObj).css('cursor', 'default').addClass('readonly');
				}
			}
		});
		checkHtml = null; checkStr = null;
	},
	checkboxToggle: function(spanObj, inputObj) {
		var check = jQuery(inputObj).is(':checked');
		var thisObj = jQuery(inputObj).get(0);

		if( jQuery(inputObj).attr('onclick') ) {
			jQuery(inputObj).trigger('onclick');
		}

		if( check ) {
			jQuery(inputObj).attr('checked', false);
			jQuery(spanObj).removeClass('selected_c');
		} else {
			jQuery(inputObj).attr('checked', true);
			jQuery(spanObj).addClass('selected_c');
		}

		// 체크박스 전체선택
		if( !jQuery(inputObj).attr('id') || jQuery(inputObj).attr('id') == undefined ) {
			var className = jQuery(inputObj).attr('class');
			jQuery('.' + className).each(function(){
				if( jQuery(this).is(':disabled') == false ) {
					if( check ) {
						jQuery(this).attr('checked', false);
						jQuery(this).prev().removeClass('selected_c');
					} else {
						jQuery(this).attr('checked', true);
						jQuery(this).prev().addClass('selected_c');
					}
				}
			});
		}

		check = null; thisObj = null;
		return false;
	},
	checkboxReadonly: function(act) { // checkbox readonly 기능
		var opt = new Array();
		opt.checked = '-25px -20px';
		opt.unchecked = '-25px 0px';

		if( act == 'Y' ) {
			jQuery(this).each(function(){
				if( jQuery(this).is(':disabled') ) {
					jQuery(this).prev().addClass('readonly_c');
					jQuery(this).prev().unbind('click');

					jQuery(this).next().addClass('readonly_c');
				} else {
					jQuery(this).prev().removeClass('readonly_c');
					jQuery(this).next().removeClass('readonly_c');
				}
			});
		} else {
			jQuery(this).each(function(){
				jQuery(this).prev().removeClass('readonly_c');
				jQuery(this).next().removeClass('readonly_c');

				jQuery(this).prev().click(function(){
					jQuery(this).checkboxToggle(opt, 'span');
				});
			});
		}
		opt = null;
		return false;
	}
});

function checkSelectAll(className, act) { // checkbox 전체선택, 선택해제
	jQuery('.' + className).each(function(){
		if( jQuery(this).is(':disabled') == false ) {
			if( act == 'checked' ) {
				jQuery(this).attr('checked', true);
				jQuery(this).prev().addClass('selected_c');
			} else {
				jQuery(this).attr('checked', false);
				jQuery(this).prev().removeClass('selected_c');
			}
		}
	});
}

function readonlySelect(obj, act) {
	jQuery('#' + obj).selectReadonly(act);
}

function readonlyRadio(obj, act) {
	jQuery('input[name=' + obj + ']').radioReadonly(act);
}

function readonlyCheck(act) {
	jQuery('input:checkbox').checkboxReadonly(act);
}

function dragDisable(obj) {
	obj = eval(obj);
	return jQuery( obj ).each(function(){
		if(jQuery.browser.mozilla){//Firefox
			jQuery(this).css('MozUserSelect','none');
		}else if(jQuery.browser.msie){//IE
			jQuery(this).bind('selectstart',function(){return false;});
		}else{//Opera, etc.
			jQuery(this).mousedown(function(){return false;});
		}
	});
}

jQuery('body').bind('click', function(){ // document 클릭시 펼쳐놓은 option Layer 감추기
	if( focusSelectObject == undefined ) {
	} else {
		jQuery(focusSelectObject).next().hide();
	}
});

// 스킨 입혔던 select, checkbox, radio 객체들 초기화를 위해 remove
function skinInit() {
	jQuery('select').remove();
	jQuery('input:checkbox').remove();
	jQuery('input:radio').remove();
}

jQuery(document).ready(function(){
	// select box 이미지 처리
	jQuery('select').selectStyle();

	// radio 버튼 이미지 처리
	jQuery('input:radio').radioStyle();

	// checkbox 이미지 처리
	jQuery('input:checkbox').checkStyle();
});

// form submit시 select, checkbox, radio 객체 초기화 Deny
// form submit시 뒤로가기로 했을경우 selected 값 보존을 위해
var submitResult = 'N';
jQuery('form').submit(function() {
	submitResult = 'Y';
});

jQuery(window).bind('beforeunload', function() {
	// 새로고침시 select, checkbox, radio 객체 초기화

	if( submitResult == 'N' ) {
		skinInit();
	}
});