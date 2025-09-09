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
	radioStyle: function() {
		jQuery(this).hide().each(function (){
			if( jQuery(this).attr('skin') == 'Y' ) {
				return true;
			}

			jQuery(this).attr('skin', 'Y');
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
							jQuery(this).radioToggle(thisObj, labelObj); // click 이벤트
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
	radioToggle: function(inputObj, labelObj) {
		var inputName = jQuery(inputObj).attr('name');
		var prevObj = '';
		var result = '';

		if( jQuery(inputObj).attr('onclick') ) {
			jQuery(inputObj).trigger('onclick');
		}

		if( jQuery(inputObj).is(':checked') == true ) {
			return false;
		}

		var thisTagName = '';
		jQuery('input:radio[name=' + inputName + ']').each(function(){
			if( jQuery(inputObj).get(0) == jQuery(this).get(0) ) {
				// 2011-09-11 IE 6, 7 에서 input checkbox 객체에 checked 속성 적용 안되는 현상 수정
				jQuery(this).attr('checked', true).click();
				jQuery(this).prev().addClass('selected_r');

				thisTagName = jQuery(this).parent().get(0).tagName.toLowerCase();

				if( thisTagName == 'label' ) {
					if( jQuery(this).parent().get(0) == jQuery(labelObj).get(0) ) {
						jQuery(labelObj).addClass('on');
					}

				}

			} else {
				if( !browserCheck() ) {
					jQuery(this).attr('checked', false);
				}
				jQuery(this).prev().removeClass('selected_r');

				jQuery(this).parent().removeClass('on');

				if( thisTagName == 'label' ) {
					jQuery(this).parent().removeClass('on');
				}
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
			if( jQuery(this).attr('skin') == 'Y' ) {
				return true;
			}

			jQuery(this).attr('skin', 'Y');
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
		var thisTagName = jQuery(this).get(0).tagName.toLowerCase();

		if( check ) {
			if( thisTagName == 'label' ) {
				jQuery(this).removeClass('on');
			}			
			jQuery(inputObj).attr('checked', false);
			jQuery(spanObj).removeClass('selected_c');
		} else {
			if( thisTagName == 'label' ) {
				jQuery(this).addClass('on');
			}			
			jQuery(inputObj).attr('checked', true);
			jQuery(spanObj).addClass('selected_c');
		}
		
		if( jQuery(inputObj).attr('onclick') ) {
			jQuery(inputObj).trigger('onclick');
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

// 스킨 입혔던 select, checkbox, radio 객체들 초기화를 위해 remove
function skinInit() {
	jQuery('input:checkbox').remove();
	jQuery('input:radio').remove();
}

jQuery(document).ready(function(){
	// radio 버튼 이미지 처리
	jQuery('input:radio:not([class|=chk])').radioStyle();

	// checkbox 이미지 처리
	jQuery('input:checkbox:not([class|=chk])').checkStyle();
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