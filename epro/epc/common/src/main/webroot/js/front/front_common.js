﻿﻿function getOSInfoStr() { // 운영체제 확인
	var strOS = '';
	var ua = navigator.userAgent;

	if(ua.indexOf("NT 6.0") != -1) strOs = "Windows Vista/Server 2008";
	else if(ua.indexOf("NT 6.1") != -1) strOs = "Windows 7";
	else if(ua.indexOf("NT 5.2") != -1) strOs = "Windows Server 2003";
	else if(ua.indexOf("NT 5.1") != -1) strOs = "Windows XP";
	else if(ua.indexOf("NT 5.0") != -1) strOs = "Windows 2000";
	else if(ua.indexOf("NT") != -1) strOs = "Windows NT";
	else if(ua.indexOf("9x 4.90") != -1) strOs = "Windows Me";
	else if(ua.indexOf("98") != -1) strOs = "Windows 98";
	else if(ua.indexOf("95") != -1) strOs = "Windows 95";
	else if(ua.indexOf("Win16") != -1) strOs = "Windows 3.x";
	else if(ua.indexOf("Windows") != -1) strOs = "Windows";
	else if(ua.indexOf("Linux") != -1) strOs = "Linux";
	else if(ua.indexOf("Macintosh") != -1) strOs = "Macintosh";
	else strOs = "";

	return strOs;
}

function msCheck() { // msie 버전 check
	var vs = navigator.appVersion;
	vs = vs.toLowerCase();

	var ieVersion = '';
	if( vs.indexOf("msie 6.0") != -1 ) { ieVersion = "6"; }
	if( vs.indexOf("msie 7.0") != -1 ) { ieVersion = "7"; }
	if( vs.indexOf("msie 8.0") != -1 ) { ieVersion = "8"; }
	if( vs.indexOf("msie 9.0") != -1 ) { ieVersion = "9"; }

	return ieVersion;
}

function browserNameCheck() { // browser check
	var vs = navigator.userAgent;
	vs = vs.toLowerCase();

	var browserName = '';

	if( vs.indexOf("firefox") != -1 ) { browserName = "firefox"; }
	else if( vs.indexOf("safari") != -1 ) { browserName = vs.indexOf("chrome") != -1 ? "chrome" : "safari"; }
	else if( vs.indexOf("msie") != -1 ) { browserName = "msie"; }
	else if( vs.indexOf("opera") != -1 ) { browserName = "opera"; }

	return browserName;
}

function backgroundIframe( obj ) { // ie6 용 iframe 삽입
	jQuery("<iframe>").css({
		'width'			: jQuery( obj ).width() + 'px',
		'height'		: jQuery( obj ).height() + 'px',
		'position'		: 'absolute',
		'left'			: jQuery( obj ).css('left'),
		'top'			: jQuery( obj ).css('top'),
		'background'	: 'transparent'
	}).attr({
		'frameborder'	: '0'
	}).insertBefore( jQuery( obj ) );
}

function spread_layer(objId, iFrame) { // layer 펼치기
	var element = document.getElementById(objId);

	// ie6 에서 select box 가 레이어를 뚫고 나오는 버그 처리
	var ieVersion = msCheck();

	if( element ) {
		if( element.style.display == 'none' ) {
			if( iFrame == 'iframe' && ieVersion == '6' ) {
				if( jQuery( element ).parent().find('>iframe').length ) {
					jQuery( element ).parent().find('>iframe').remove();
				}

				backgroundIframe( jQuery( element ) );
			}

			element.style.display = 'block';
		} else {
			if( iFrame == 'iframe' && ieVersion == '6' ) {
				jQuery( element ).parent().find('>iframe').hide();
			}

			element.style.display = 'none';
		}
	}
	element = null;
	return false;
}

function spread_social() { // 소셜쇼핑 지역 메뉴 펼치기
	// init
	var nowIdx = 0;
	jQuery('#social_area').find('>ul>li').each(function(){
		jQuery(this).bind('mouseover', function() {
			jQuery('#social_area').find('>ul>li').each(function(){
				jQuery(this).removeClass('on');
			});

			if( jQuery(this).index() > 0 ) {
				jQuery(this).addClass('on');

				nowIdx = parseInt( jQuery(this).index(), 10 ) - 1;
				jQuery('.list_brc:eq(' + nowIdx + ')').show();
			} else {
				return false;
			}
		});

		jQuery(this).bind('mouseleave', function(event) {
			var nowObj = jQuery(this);

			if( jQuery(this).index() > 0 ) {
				if( jQuery( event.relatedTarget ).parent().length ) {
					nowIdx = parseInt( jQuery(this).index(), 10 ) - 1;
					var tmpObj = jQuery( event.relatedTarget ).parent();
					if( jQuery('.list_brc:eq(' + nowIdx + ')').get(0) != jQuery( tmpObj ).get(0) ) {
						jQuery(this).removeClass('on');
						jQuery('.list_brc:eq(' + nowIdx + ')').hide();
					}
					tmpObj = null;
				}

				jQuery('.list_brc:eq(' + nowIdx + ')').bind('mouseleave',function(event) {
					if( jQuery( event.relatedTarget ).parent().parent().length ) {
						tmpObj = jQuery( event.relatedTarget ).parent().parent();
						if( jQuery(nowObj).get(0) != jQuery( tmpObj ).get(0) ) {
							var liIdx = nowIdx + 1;
							jQuery('#social_area').find('>ul>li:eq(' + liIdx + ')').removeClass('on');

							jQuery(this).hide();
							liIdx = null;
						}
					}
				});
			} else {
				jQuery('.list_brc').each(function(){
					jQuery(this).hide();
				});
			}
			nowObj = null;
		});
	});
	nowIdx = null;
}

// 최근본상품
var historyMouseObj;
var historyFuncResult = 'N';	// 최근 본 상품 up, down 버튼 이벤트 부여 여부
var firstHistoryNum = 0;		// 최근 본 상품 현재 순서
var maxHistoryNum = 0;			// 최근 본 상품 최대 갯수
function spread_history(obj) {
	var rootObj; // rootObj : fl_history

	if( jQuery(obj).parent().find('.click_btn_dsc').length ) {
		rootObj = jQuery(obj).parent();
	} else if( jQuery(obj).parent().parent().find('.click_btn_dsc').length ) {
		rootObj = jQuery(obj).parent().parent();
	} else if( jQuery(obj).parent().parent().parent().find('.click_btn_dsc').length ) {
		rootObj = jQuery(obj).parent().parent().parent();
	}

	var history_display = function( idx, obj ) {
		var lastIdx = idx + 3;
		var nowIdx = 0;

		jQuery(obj).each(function() {
			nowIdx = parseInt( jQuery(this).index(), 10 );
			if( nowIdx >= idx && nowIdx <= lastIdx ) {
				jQuery(this).show();
			} else {
				jQuery(this).hide();
			}
		});
		lastIdx=null; nowIdx=null;
		return false;
	};

	var btn_init = function(obj, act) {
		var imgSrc = jQuery(obj).attr('src');
		var tmpSrc = imgSrc.split('.');
		var tmpCnt = parseInt( tmpSrc.length, 10 ) - 1;
		var imgExt = tmpSrc[tmpCnt]; // 이미지 파일 확장자

		if( act == 'on' ) {
			if(imgSrc.indexOf("-off." + imgExt) != -1) {
				imgSrc = imgSrc.replace('-off.' + imgExt, '-on.' + imgExt);
			} else if(imgSrc.indexOf("-dis." + imgExt) != -1) {
				imgSrc = imgSrc.replace('-dis.' + imgExt, '-on.' + imgExt);
			}

			jQuery(obj).addClass('hand');
		} else if( act == 'off' ) {
			if(imgSrc.indexOf("-on." + imgExt) != -1) {
				imgSrc = imgSrc.replace('-on.' + imgExt, '-off.' + imgExt);
			} else if(imgSrc.indexOf("-dis." + imgExt) != -1) {
				imgSrc = imgSrc.replace('-dis.' + imgExt, '-off.' + imgExt);
			}

			jQuery(obj).addClass('hand');
		} else if( act == 'dis' ) {
			if(imgSrc.indexOf("-off." + imgExt) != -1) {
				imgSrc = imgSrc.replace('-off.' + imgExt, '-dis.' + imgExt);
			} else if(imgSrc.indexOf("-on." + imgExt) != -1) {
				imgSrc = imgSrc.replace('-on.' + imgExt, '-dis.' + imgExt);
			}

			jQuery(obj).removeClass('hand');
		}

		jQuery(obj).attr('src', imgSrc);

		imgSrc=null; tmpSrc=null; tmpCnt=null; imgExt=null;
	};

	var history_init = function(obj) {
		var liCnt = 1;

		jQuery(obj).find('ul>li').each(function() {
			if( liCnt > 4 ) {
				jQuery(this).hide();
			} else {
				jQuery(this).show();
			}
			liCnt++;
		});
		liCnt=null;

		maxHistoryNum = jQuery(obj).find('ul>li').length;

		if( jQuery(obj).find('.btn_top').length ) {
			jQuery(obj).find('.btn_top').bind('click', function() {
				var tmpHref = jQuery(this).find('>a').attr('href');
				if( tmpHref == '#' ) {
					jQuery(this).find('>a').attr('href', 'javascript:void(0);');
				}
				tmpHref=null;

				firstHistoryNum = firstHistoryNum - 1;

				if( firstHistoryNum < 0 ) {
					firstHistoryNum = 0;
				} else {
					jQuery(obj).find('.btn_btm').find('img').attr('btnstatus', '');
					btn_init( jQuery(obj).find('.btn_btm').find('img'), 'off' );

					if( firstHistoryNum == 0 ) {
						jQuery(this).find('img').attr('btnstatus', 'dis');
						btn_init( jQuery(this).find('img'), 'dis' );
					} else {
						jQuery(this).find('img').attr('btnstatus', '');
						btn_init( jQuery(this).find('img'), 'off' );
					}
					history_display(firstHistoryNum, jQuery(obj).find('ul>li'));
				}
			});

			jQuery(obj).find('.btn_top').find('img').bind('mouseover', function() {
				if( jQuery(this).attr('btnstatus') != 'dis' ) {
					btn_init( jQuery(this), 'on' );
				}
			});

			jQuery(obj).find('.btn_top').find('img').bind('mouseleave', function() {
				if( jQuery(this).attr('btnstatus') != 'dis' ) {
					btn_init( jQuery(this), 'off' );
				}
			});

			jQuery(obj).find('.btn_top').find('img').attr('btnstatus', 'dis');
			btn_init( jQuery(obj).find('.btn_top').find('img'), 'dis' );
		}

		if( jQuery(obj).find('.btn_btm').length ) {
			jQuery(obj).find('.btn_btm').bind('click', function() {
				var tmpHref = jQuery(this).find('>a').attr('href');
				if( tmpHref == '#' ) {
					jQuery(this).find('>a').attr('href', 'javascript:void(0);');
				}
				tmpHref=null;

				firstHistoryNum = firstHistoryNum + 1;

				if( firstHistoryNum > maxHistoryNum - 4 ) {
					firstHistoryNum = maxHistoryNum - 4;
				} else {
					if( firstHistoryNum > 0 ) {
						jQuery(obj).find('.btn_top').find('img').attr('btnstatus', '');
						jQuery(obj).find('.btn_top').find('img').addClass('hand');
						btn_init( jQuery(obj).find('.btn_top').find('img'), 'off' );
					}

					if( firstHistoryNum == maxHistoryNum - 4 ) {
						jQuery(obj).find('.btn_btm').find('img').attr('btnstatus', 'dis');
						jQuery(this).find('img').removeClass('hand');
						btn_init( jQuery(this).find('img'), 'dis' );
					}
					history_display(firstHistoryNum, jQuery(obj).find('ul>li'));
				}
			});

			jQuery(obj).find('.btn_btm').find('img').bind('mouseover', function() {
				if( jQuery(this).attr('btnstatus') != 'dis' ) {
					btn_init( jQuery(this), 'on' );
				}
			});

			jQuery(obj).find('.btn_btm').find('img').bind('mouseleave', function() {
				if( jQuery(this).attr('btnstatus') != 'dis' ) {
					btn_init( jQuery(this), 'off' );
				}
			});

			if( maxHistoryNum > 4 ) { // 최근본상품 갯수가 4개 이상일 경우
				jQuery(obj).find('.btn_btm').find('img').attr('btnstatus', '');
				jQuery(obj).find('.btn_btm').find('img').addClass('hand');
				btn_init( jQuery(obj).find('.btn_btm').find('img'), 'off' );
			} else {
				jQuery(obj).find('.btn_btm').find('img').attr('btnstatus', 'dis');
				jQuery(obj).find('.btn_btm').find('img').removeClass('hand');
				btn_init( jQuery(obj).find('.btn_btm').find('img'), 'dis' );
			}
		}
	};

	var mouseOutEvent = function(obj) {
		var mouseResult = jQuery(obj).attr('mouseover');
		if( mouseResult == 'N' ) {
			clearTimeout( historyMouseObj );
			jQuery( obj ).hide();
			jQuery( obj ).parent().find('.click_btn img').removeClass('on');
		} else {
		}
	};

	var divObj;
	if( jQuery( rootObj ).find('.click_btn').length ) {
		jQuery( rootObj ).find('.click_btn').bind('click', function(event) {
			event.preventDefault();

			var thisClass = jQuery(this).find('img').attr('class');
			if( thisClass == 'on' ) {
				jQuery(this).parent().find('.click_btn_dsc').attr('mouseover', 'N');
				clearTimeout( historyMouseObj );

				historyMouseObj = setTimeout(function() { mouseOutEvent( jQuery(rootObj).find('.click_btn_dsc') ); }, 3000 );
			} else {
				clearTimeout( historyMouseObj );
			}
		});
	}

	if( jQuery( rootObj ).find('.click_btn_dsc').length ) {
		jQuery( rootObj ).find('.click_btn_dsc').bind('mouseover', function(event) {
			event.preventDefault();

			jQuery(this).attr('mouseover', 'Y');
			clearTimeout( historyMouseObj );
		});

		jQuery( rootObj ).find('.click_btn_dsc').bind('mouseleave', function(event) {
			event.preventDefault();

			jQuery(this).attr('mouseover', 'N');
			clearTimeout( historyMouseObj );

			historyMouseObj = setTimeout(function() { mouseOutEvent( jQuery(rootObj).find('.click_btn_dsc') ); }, 2000 );
		});

		divObj = jQuery( rootObj ).find('.click_btn_dsc');

		if( historyFuncResult != 'Y' ) {
			history_init( jQuery(divObj) );
			historyFuncResult = 'Y';
		}

		// ie6 에서 select box 가 레이어를 뚫고 나오는 버그 처리
		var ieVersion = msCheck();

		if( jQuery(obj).attr('class') != 'on' ) {
			jQuery(obj).addClass('on');
			jQuery(divObj).show();

			if( ieVersion == '6' ) {
				if( jQuery( rootObj ).find('>iframe').length ) {
					jQuery( rootObj ).find('>iframe').show();
				} else {
					backgroundIframe( jQuery( divObj ) );
				}
			}
		} else {
			jQuery(obj).removeClass('on');
			jQuery(divObj).hide();

			if( ieVersion == '6' ) {
				if( jQuery( rootObj ).find('>iframe').length ) {
					jQuery( rootObj ).find('>iframe').hide();
				}
			}
		}
	}
}

function category_layer(obj) { // 상품리스트 카테고리 레이어
	var rootObj;
	if( jQuery(obj).parent().find('.click_btn_dsc').length ) {
		rootObj = jQuery(obj).parent();
	} else if( jQuery(obj).parent().parent().find('.click_btn_dsc').length ) {
		rootObj = jQuery(obj).parent().parent();
	} else if( jQuery(obj).parent().parent().parent().find('.click_btn_dsc').length ) {
		rootObj = jQuery(obj).parent().parent().parent();
	}

	// rootObj : cate_area

	var divObj;
	if( jQuery( rootObj ).find('.click_btn_dsc').length ) {
		divObj = jQuery( rootObj ).find('.click_btn_dsc');

		var rootHeight = parseInt( jQuery(rootObj).parent().outerHeight(), 10 );
		rootHeight = rootHeight - 3;
		jQuery( divObj ).css('top', rootHeight+'px');
		rootHeight=null;

		// ie6 에서 select box 가 레이어를 뚫고 나오는 버그 처리
		var browserName = browserNameCheck();
		var ieVersion = msCheck();
		if( browserName == 'msie' ) {
			if( ieVersion == '6' ) {
				if( !jQuery( divObj ).parent().find('iframe').length ) {
					jQuery("<iframe>").css({
						'width'			: jQuery( divObj ).outerWidth() + 'px',
						'height'		: jQuery( divObj ).innerHeight() + 'px',
						'position'		: 'absolute',
						'left'			: jQuery( divObj ).css('left'),
						'top'			: jQuery( divObj ).css('top'),
						'background'	: 'transparent'
					}).attr({
						'frameborder'	: '0'
					}).insertBefore(divObj);
				}
			}
		}

		var imgSrc = jQuery(obj).find('.click_btn img').attr('src');
		var tmpSrc = imgSrc.split('.');
		var tmpCnt = parseInt( tmpSrc.length, 10 ) - 1;
		var imgExt = tmpSrc[tmpCnt]; // 이미지 파일 확장자

		if( jQuery(divObj).css('display') == 'none' ) {
			if( browserName == 'msie' ) {
				if( ieVersion == '6' ) {
					if( jQuery( divObj ).parent().find('iframe').length ) {
						jQuery( divObj ).parent().find('iframe').show();
					}
				}
			}

			imgSrc = imgSrc.replace('-off.' + imgExt, '-on.' + imgExt);
			jQuery(obj).find('.click_btn img').attr('src', imgSrc);
			jQuery(divObj).show();
		} else {
			if( browserName == 'msie' ) {
				if( ieVersion == '6' ) {
					if( jQuery( divObj ).parent().find('iframe').length ) {
						jQuery( divObj ).parent().find('iframe').hide();
					}
				}
			}

			imgSrc = imgSrc.replace('-on.' + imgExt, '-off.' + imgExt);
			jQuery(obj).find('.click_btn img').attr('src', imgSrc);
			jQuery(divObj).hide();
		}
	}
}

function sperad_category(obj, act) { // 펼쳐보기 메뉴
	var rootObj;
	if( jQuery(obj).parent().find('.click_btn_dsc').length ) {
		rootObj = jQuery(obj).parent();
	} else if( jQuery(obj).parent().parent().find('.click_btn_dsc').length ) {
		rootObj = jQuery(obj).parent().parent();
	} else if( jQuery(obj).parent().parent().parent().find('.click_btn_dsc').length ) {
		rootObj = jQuery(obj).parent().parent().parent();
	}

	// rootObj : cate_area

	var divObj;
	if( jQuery( rootObj ).find('.click_btn_dsc').length ) {
		divObj = jQuery( rootObj ).find('.click_btn_dsc');

		if( jQuery( divObj ).css('display') == 'none' ) {
			if( act == 'on' ) {
				jQuery( obj ).addClass('on');
			}

			if( jQuery( rootObj ).find('.cate_area').length ) {
				var rootHeight = parseInt( jQuery(rootObj).parent().outerHeight(), 10 );
				rootHeight = rootHeight - 3;
				jQuery( divObj ).css('top', rootHeight+'px');
				rootHeight=null;
			}

			// ie6 에서 select box 가 레이어를 뚫고 나오는 버그 처리
			var ieVersion = msCheck();
			if( ieVersion == '6' ) {
				if( !jQuery( divObj ).parent().find('iframe').length ) {
					jQuery("<iframe>").css({
						'width'			: jQuery( divObj ).outerWidth() + 'px',
						'height'		: jQuery( divObj ).innerHeight() + 'px',
						'position'		: 'absolute',
						'left'			: jQuery( divObj ).css('left'),
						'top'			: jQuery( divObj ).css('top'),
						'background'	: 'transparent',
						'filter'		: 'alpha(opacity=0)'
					}).attr({
						'frameborder'	: '0'
					}).insertBefore(divObj);
				} else {
					jQuery( divObj ).parent().find('iframe').show();
				}
			}

			jQuery( divObj ).show();
		} else {
			if( act == 'on' ) {
				jQuery( obj ).removeClass('on');
			}

			if( jQuery( divObj ).parent().find('iframe').length ) {
				jQuery( divObj ).parent().find('iframe').hide();
			}
			jQuery( divObj ).hide();
		}
	}

	rootObj = null; divObj = null;
	return false;
}

function layer_open(obj, objId) { // layer 펼치기 img -on, -off 방식
	var btnResult = 'N';
	var layerResult = 'N';

	if( jQuery('#' + objId + '_btn').length ) {
		btnResult = 'Y';

		var imgSrc = jQuery('#' + objId + '_btn').attr('src');
		var tmpSrc = imgSrc.split('.');
		var tmpCnt = parseInt( tmpSrc.length, 10 ) - 1;
		var imgExt = tmpSrc[tmpCnt]; // 이미지 파일 확장자

		if(imgSrc.indexOf("-off." + imgExt) != -1) {
			jQuery(obj).attr('act', 'off');
		} else {
			jQuery(obj).attr('act', 'on');
		}

		var imgAct = jQuery(obj).attr('act');

		if( imgAct == 'on' ) {
			imgSrc = imgSrc.replace('-on.' + imgExt, '-off.' + imgExt);
			jQuery('#' + objId + '_btn').attr('src', imgSrc);

			if( jQuery('#' + objId + '_layer').length ) {
				jQuery('#' + objId + '_layer').hide();
			}
		} else if( imgAct == 'off' ) {
			imgSrc = imgSrc.replace('-off.' + imgExt, '-on.' + imgExt);
			jQuery('#' + objId + '_btn').attr('src', imgSrc);

			if( jQuery('#' + objId + '_layer').length ) {
				jQuery('#' + objId + '_layer').show();
			}
		}
		imgSrc=null; tmpSrc=null; tmpCnt=null; imgExt=null;
	} else {
		if( jQuery('#' + objId + '_layer').length ) {
			layerResult = 'Y';

			if( jQuery('#' + objId + '_layer').css('display') == 'block' ) {
				jQuery(obj).attr('act', 'on');
			} else {
				jQuery(obj).attr('act', 'off');
			}

			var imgAct = jQuery(obj).attr('act');

			if( imgAct == 'on' ) {
				jQuery('#' + objId + '_layer').hide();
			} else if( imgAct == 'off' ) {
				jQuery('#' + objId + '_layer').show();
			}
		}
	}
}

function spread_cateList(obj, act) { // 카테고리 검색결과 펼쳐보기
	if( jQuery('.cate_cont').length ) {
		if( act == 'all' ) {
			var objResult = jQuery( obj ).parent().attr('spread');
			if( objResult == 'Y' ) {
				jQuery('.cate_cont').find('>dl').each(function() {
					var limitCnt = 7;
					var nowCnt = 0;
					jQuery(this).find('>dd:eq(0)>ul>li').each(function() {
						if( nowCnt < limitCnt ) {
							jQuery(this).show();
						} else {
							jQuery(this).hide();
						}
						nowCnt++;
					});

					if( jQuery(this).find('.oc_btn').length ) {
						var imgSrc = jQuery(this).find('.oc_btn img').attr('src');
						var tmpSrc = imgSrc.split('.');
						var tmpCnt = parseInt( tmpSrc.length, 10 ) - 1;
						var imgExt = tmpSrc[tmpCnt]; // 이미지 파일 확장자

						imgSrc = imgSrc.replace('-close.' + imgExt, '-open.' + imgExt);
						jQuery(this).find('.oc_btn img').attr('src', imgSrc);
					}
				});

				jQuery( obj ).removeClass('on');
				jQuery( obj ).parent().attr('spread', 'N');
			} else {
				jQuery('.cate_cont').find('>dl').each(function() {
					jQuery(this).find('>dd:eq(0)>ul>li').each(function() {
						jQuery(this).show();
					});

					if( jQuery(this).find('.oc_btn').length ) {
						var imgSrc = jQuery(this).find('.oc_btn img').attr('src');
						var tmpSrc = imgSrc.split('.');
						var tmpCnt = parseInt( tmpSrc.length, 10 ) - 1;
						var imgExt = tmpSrc[tmpCnt]; // 이미지 파일 확장자

						imgSrc = imgSrc.replace('-open.' + imgExt, '-close.' + imgExt);
						jQuery(this).find('.oc_btn img').attr('src', imgSrc);
					}
				});

				jQuery( obj ).addClass('on');
				jQuery( obj ).parent().attr('spread', 'Y');
			}
		} else {
			jQuery('.cate_cont').find('>dl').each(function() {
				if( jQuery(this).find('.oc_btn').length ) {
					var cateCnt = jQuery(this).find('>dd:eq(0)>ul>li').length;
					if( cateCnt > 7 ) {
						var imgSrc = jQuery(this).find('.oc_btn img').attr('src');
						var tmpSrc = imgSrc.split('.');
						var tmpCnt = parseInt( tmpSrc.length, 10 ) - 1;
						var imgExt = tmpSrc[tmpCnt]; // 이미지 파일 확장자

						if( imgSrc.indexOf("close") != -1 ) {
							imgSrc = imgSrc.replace('-close.' + imgExt, '-open.' + imgExt);
							jQuery(this).find('.oc_btn img').attr('src', imgSrc);
						}

						var limitCnt = 7;
						var thisCnt = 0;
						jQuery(this).find('>dd:eq(0)>ul>li').each(function() {
							if( thisCnt < limitCnt ) {
								jQuery(this).show();
							} else {
								jQuery(this).hide();
							}
							thisCnt++;
						});

						jQuery(this).find('.oc_btn').bind('click',function() {
							var imgSrc = jQuery(this).find('img').attr('src');
							var tmpSrc = imgSrc.split('.');
							var tmpCnt = parseInt( tmpSrc.length, 10 ) - 1;
							var imgExt = tmpSrc[tmpCnt]; // 이미지 파일 확장자


							var listObj = jQuery(this).prev();
							var visibleCnt = 0;
							if( imgSrc.indexOf("open") != -1 ) {
								jQuery( listObj ).find('>ul>li').each(function() {
									jQuery(this).show();
								});

								imgSrc = imgSrc.replace('-open.' + imgExt, '-close.' + imgExt);
							} else if( imgSrc.indexOf("close") != -1 ) {
								visibleCnt = 7;
								var nowCnt = 0;
								jQuery( listObj ).find('>ul>li').each(function() {
									if( nowCnt < visibleCnt ) {
										jQuery(this).show();
									} else {
										jQuery(this).hide();
									}
									nowCnt++;
								});

								imgSrc = imgSrc.replace('-close.' + imgExt, '-open.' + imgExt);
							}
							jQuery(this).find('img').attr('src', imgSrc);
						});
					} else {
						jQuery(this).find('.oc_btn').hide();
					}
				}
			});
		}
	}
}

function tabChange() { // 통합회원가입 탭 버튼
	if( jQuery('.tit_li').length ) {
		jQuery('.tit_li').bind('click', function() {
			var thisObj = jQuery(this);
			var parentObj = jQuery(this).parent().parent();

			var onRegExp = new RegExp('on');
			var classText = jQuery(parentObj).attr('class');
			if( onRegExp.test(classText) ) {
				return false;
			} else {
				var thisIdx; var pObj;
				var openDiv = '';
				var type = 'name';
				var radioResult = 'N';

				jQuery('.tit_li').each(function() {
					thisIdx = parseInt( jQuery(this).parent().parent().index(), 10 );
					switch( thisIdx ) {
						case 0 : { openDiv = 'general'; break; }
						case 1 : { openDiv = 'foreign'; break; }
						case 2 : { openDiv = 'company'; break; }
					} // switch End

					pObj = jQuery(this).parent().parent();

					jQuery('#' + openDiv).find('input:text').each(function() {
						jQuery(this).val('');
					});

					if( jQuery(this).get(0) == jQuery(thisObj).get(0) ) {
						jQuery( pObj ).addClass('on');
						jQuery('#' + openDiv ).show();

						if( jQuery('#name_' + openDiv).length ) {
							jQuery('#name_' + openDiv).show();
						}

						if( jQuery('#ipin_' + openDiv).length ) {
							jQuery('#ipin_' + openDiv).hide();
						}

						if( jQuery('#name_' + openDiv + '_btn').length ) {
							jQuery('#name_' + openDiv + '_btn').show();
						}

						if( jQuery('#ipin_' + openDiv + '_btn').length ) {
							jQuery('#ipin_' + openDiv + '_btn').hide();
						}

						if( jQuery('#' + openDiv + '_select').length ) {
							jQuery('#' + openDiv + '_select').find('input:eq(0)').attr('checked', true);
						}
					} else {
						jQuery( pObj ).removeClass('on');
						jQuery('#' + openDiv ).hide();

						if( jQuery('#name_' + openDiv + '_btn').length ) {
							jQuery('#name_' + openDiv + '_btn').hide();
						}

						if( jQuery('#ipin_' + openDiv + '_btn').length ) {
							jQuery('#ipin_' + openDiv + '_btn').hide();
						}
					}
				});
				thisIdx = null; pObj = null; openDiv = null;
				type = null; radioResult = null;
			}
			thisObj = null; parentObj = null; onRegExp = null; classText = null;
		});

		if( jQuery('.tit_li').find('>a').length ) {
			jQuery('.tit_li').find('>a').bind('focus', function() {
				this.blur();
			});
		}
		return false;
	}
}

function layerChange(type, obj) { // 통합회원가입 레이어변경
	var targetId = '#' + type + '_' + obj;

	if( jQuery( targetId ).css('display') == 'none' ) {
		jQuery( targetId ).find('input:text').each(function() {
			jQuery(this).val('');
		});

		jQuery( targetId ).show();
		jQuery( targetId + '_btn' ).show();
	} else {
		return false;
	}

	var anotherId = '';
	if( type == 'name' ) {
		anotherId = '#ipin_' + obj;
	} else if( type == 'ipin' ) {
		anotherId = '#name_' + obj;
	}

	jQuery( anotherId ).hide();
	jQuery( anotherId + '_btn' ).hide();

	targetId = null; anotherId = null;
	return false;
}

function spread_topSearch() { // 통합검색 레이어 펼치기
	
	if( jQuery('.search').length ) {

		jQuery('.search').find('.combo1').bind('click', function() {

			var thisObj = jQuery(this);
			var rootObj = jQuery(this).parent();
			var layObj = jQuery(rootObj).find('.combo2');
			//var imgSrc =jQuery(this).find('.sc_arrow img').attr('src');

			if( jQuery(layObj).css('display') == 'none' ) {
				jQuery(layObj).show();
				jQuery(this).next().attr('class', 'click-on');
				//layObj.attr('class', 'click-on');
				
				//imgSrc = imgSrc.replace('-off.gif', '-on.gif');
				//jQuery(this).find('.sc_arrow img').attr('src', imgSrc);

				// ie6 에서 select box 가 레이어를 뚫고 나오는 버그 처리
				/*var ieVersion = msCheck();
				if( ieVersion == '6' ) {
					if( jQuery( rootObj ).find('>iframe').length ) {
						jQuery( rootObj ).find('>iframe').show();
					} else {
						backgroundIframe( jQuery( rootObj ).find('.sel_lay') );
					}
				}*/

			} else {
				jQuery(layObj).hide();
				
				jQuery(this).next().attr('class', 'click-off');
				//layObj.attr('class', 'click-off');
				
				//imgSrc = imgSrc.replace('-on.gif', '-off.gif');
				//jQuery(this).find('.sc_arrow img').attr('src', imgSrc);

				/*if( jQuery( rootObj ).find('>iframe').length ) {
					jQuery( rootObj ).find('>iframe').hide();
				}*/
			}

			if( !jQuery( layObj ).attr('effect') ) {
				
				var selectObj = jQuery(layObj).find('ul li');
				jQuery(selectObj).css('cursor', 'pointer');
				
				jQuery(selectObj).bind('click', function() {
					
					//jQuery(thisObj).find('>span:eq(0)').html( jQuery(this).html() );
					//jQuery(thisObj).find('>input[type=hidden]').val( jQuery(this).attr('ref') );

					//jQuery(layObj).hide();
					jQuery(thisObj).find('>strong:eq(0)').html( jQuery(this).text() );
					
					jQuery(this).next().attr('class', 'click-off');
					
					//jQuery(thisObj).next().attr('class', 'line_off');
				});

				jQuery(selectObj).bind('mouseover', function() {
					jQuery(this).addClass('on');
				});

				jQuery(selectObj).bind('mouseleave', function() {
					jQuery(this).removeClass('on');
				});

				jQuery( layObj ).attr('effect', 'Y');
			}
		});

		search_keyword( jQuery('.sc_area') );
	}
}

function search_keyword( obj ) { // 통합검색 입력창 onfocus 시 검색결과 레이어 펼치기
	if( jQuery( obj ).find('.sc_input #keyword').length ) {
		jQuery( obj ).find('.sc_input #keyword').focusin(function() {
			jQuery(this).val('');
			if( jQuery( obj ).find('.sc_pr_lay').length ) {
				jQuery( obj ).find('.sc_list').next().attr('class', 'line_on');

				// ie6 에서 select box 가 레이어를 뚫고 나오는 버그 처리
				var ieVersion = msCheck();
				if( ieVersion == '6' ) {
					if( jQuery( obj ).find('>iframe').length ) {
						jQuery( obj ).find('>iframe').show();
					} else {
						backgroundIframe( jQuery( obj ).find('.sc_pr_lay') );
					}
				}

				jQuery( obj ).find('.sc_pr_lay').show();
			}
		});

		jQuery( obj ).find('.sc_input #keyword').focusout(function() {
			setTimeout(function() {
				if( jQuery( obj ).find('>iframe').length ) {
					jQuery( obj ).find('>iframe').hide();
				}
				jQuery( obj ).find('.sc_txt_lay').hide();
				jQuery( obj ).find('.sc_list').next().attr('class', 'line_off');
			}, 80);
		});
	}
}

function spread_gnb( obj ) { // 공통메뉴 레이어 펼치기
	var ulObj = jQuery( obj ).find('>ul');

	jQuery( ulObj ).find('>li').bind('mouseover', function() {
		var nowObj = jQuery(this);
		jQuery(ulObj).find('>li').each(function() {
			if( jQuery(this).get(0) == jQuery(nowObj).get(0) ) {
				var imgSrc = jQuery(this).find('>a>img').attr('src');
				imgSrc = imgSrc.replace('-off.gif', '-on.gif');
				jQuery(this).find('>a>img').attr('src', imgSrc);

				jQuery(this).addClass('on');

				// ie6 에서 select box 가 레이어를 뚫고 나오는 버그 처리
				var ieVersion = msCheck();
				if( ieVersion == '6' ) {
					if( jQuery(this).find('>iframe').length ) {
						jQuery(this).find('>iframe').show();
					} else {
						backgroundIframe( jQuery(this).find('>div:eq(0)') );
					}
				}

				jQuery(this).find('>div:eq(0)').show();
			}
		});
	});

	jQuery( ulObj ).find('>li').bind('mouseleave', function() {
		var imgSrc = jQuery(this).find('>a>img').attr('src');
		imgSrc = imgSrc.replace('-on.gif', '-off.gif');
		jQuery(this).find('>a>img').attr('src', imgSrc);

		jQuery(this).removeClass('on');

		if( jQuery(this).find('>iframe').length ) {
			jQuery(this).find('>iframe').hide();
		}
		jQuery(this).find('>div:eq(0)').hide();
	});
}

function spread_relation() {
	var rootObj = jQuery('.relation');
	jQuery( rootObj ).bind('mouseover', function() {
		jQuery(this).find('>div:eq(0)').attr('class', 'jq_on');
		jQuery(this).find('>div:eq(1)').css('overflow', 'hidden');
		jQuery(this).find('>div:eq(1)').show();
	});

	jQuery( rootObj ).bind('mouseleave', function() {
		jQuery(this).find('>div:eq(0)').attr('class', 'jq_off');
		jQuery(this).find('>div:eq(1)').hide();
	});
}

// 2011-10-14 검색어 랭킹
var rankMouseResult = 'N';
function rolling(options) {
	var self = this;
	this.object = document.getElementById(options.rollId);
	jQuery(this.object).parent().bind('mouseover', function(e) {
		e.preventDefault ? e.preventDefault() : e.returnValue = false;
		var thisObj = jQuery(this);
		rankMouseResult = 'Y';
		if( jQuery(thisObj).attr('class') == 'jq_rank_list' ) {
			this.control = setTimeout(function() {self.rankPop( thisObj, self );}, 200);
		}
	});

	jQuery(this.object).parent().bind('mouseleave', function(e) {
		e.preventDefault ? e.preventDefault() : e.returnValue = false;
		var thisObj = jQuery(this);
		rankMouseResult = 'N';
		if( jQuery(thisObj).attr('class') == 'jq_rank_pop' ) {
			if( jQuery(thisObj).find('#rank_last').length ) {
				jQuery(thisObj).find('#rank_last').show();
			}
			jQuery(thisObj).attr('class', 'jq_rank_list');
			jQuery(thisObj).find('#jq_rank_list').css('top', '-' + self.elPosition + 'px');

			this.control = setTimeout(function() {self.play();}, 500);
		}
	});

	this.delay = 2000; this.speed = 1; this.step = 1; this.mover = true;
	this.elChildHeight = jQuery(this.object).find('>div:eq(0)').css('height');
	this.elChildHeight = this.elChildHeight.replace('px', '');
	this.elHeight = this.object.offsetHeight;
	this.elPosition = 0;

	var cloneDiv = jQuery(this.object).find('>div:eq(0)').clone();
	jQuery(cloneDiv).attr('id', 'rank_last');
	jQuery(this.object).append( jQuery(cloneDiv) );

	this.control = setTimeout(function() {self.play()}, this.delay);
}
rolling.prototype = {
	play:function() {
		if( rankMouseResult == 'Y' ) {
			clearTimeout(this.control);
		} else {
			var self = this, time;
			this.elPosition = this.elPosition>(this.mover?this.elHeight:0) ? this.elPosition-this.elHeight : this.elPosition+1;
			this.object.style.top = (this.mover ? -this.elPosition : this.elPosition) + "px";
			this.control = setTimeout(function() {self.play()}, this.elPosition%(this.elChildHeight*this.step)==0?this.delay:this.speed);
		}
	},
	stop:function() {
		clearTimeout(this.control);
	},
	rankPop:function(obj, targetObj) {
		if( rankMouseResult == 'Y' ) {
			targetObj.stop();
			var thisObj = jQuery(obj).find('#jq_rank_list');

			if( jQuery(thisObj).find('#rank_last').length ) {
				jQuery(thisObj).find('#rank_last').hide();
			}

			jQuery(thisObj).css('top', '0px');

			jQuery(obj).attr('class', 'jq_rank_pop');
		} else {
			clearTimeout(this.control);
		}
	}
};

// 윈도우 팝업 사이즈 리사이징
var pageEndObj='N', pageEndObjFind='', pageEndObjFindCnt=0, firstWidth=0, firstHeight=0, resizeObj='N';
function window_init(locX, locY) {
	var browserName	= browserNameCheck(); // 브라우저 명
	var osName		= getOSInfoStr();
	var msVersion	= msCheck();

	// 현재 창 width, height
	var winWidth = parseInt( jQuery(window).width(), 10 );
	var winHeight = parseInt( jQuery(window).height(), 10 );

	// 오페라 브라우저 부모창 크기
	var operaWidth=0, operaHeight = 0;
	if( browserName == 'opera' ) {
		operaWidth = parseInt( opener.window.outerWidth, 10 )
		operaHeight = parseInt( opener.window.outerHeight, 10 ) - 50;
	}

	firstWidth = winWidth; firstHeight = winHeight;

	// body width, height
	var bodyWidth = parseInt( jQuery('html').width(), 10 );
	var bodyHeight = parseInt( jQuery('html').height(), 10 );

	// document width, height
	var docWidth = parseInt( jQuery('#pop_wrap').width(), 10 );
	var docHeight = parseInt( jQuery('#pop_wrap').height(), 10 );

	// 모니터 해상도 width, height
	var screenWidth = parseInt( screen.availWidth, 10 );
	var screenHeight = parseInt( screen.availHeight, 10 );

	// 변화시킬 값을 계산 ( document 값 - window 값 )
	var popWidth = docWidth - winWidth;
	var popHeight = 0;

	var heightAll = 'N';
	if( browserName == 'opera' ) {
		if( bodyHeight > operaHeight ) {
			heightAll = 'Y';
			popHeight = operaHeight - winHeight;
		} else {
			popHeight = docHeight - winHeight;
		}
	} else {
		if( bodyHeight > screenHeight ) {
			heightAll = 'Y';
			popHeight = screenHeight - winHeight;
			if( browserName == 'msie' ) {
				popHeight = popHeight - 38;
			} else if( browserName == 'firefox' ) {
				if( osName == 'Windows 7' ) {		popHeight = popHeight - 78; }
				else if( osName == 'Windows XP' ) {	popHeight = popHeight - 69; }
			}
		} else {
			popHeight = docHeight - winHeight;
		}
	}

	if( resizeObj == 'Y' ) {
		if( browserName == 'chrome' || browserName == 'safari' ) {
			self.window.resizeBy(popWidth, 0); // 팝업창 리사이징
		} else {
			self.window.resizeBy(popWidth, popHeight); // 팝업창 리사이징
		}

		if( heightAll == 'Y' ) {
			if( browserName != 'opera' ) {
				var moveLeft = 0;
				var moveTop = 0;

				if( !isNaN( window.screenTop ) ) {
					moveLeft = window.screenLeft;
					moveTop = window.screenTop;
				} else {
					moveLeft = window.screenX;
					moveTop = window.screenY;
				}

				if( browserName == 'msie' ) {
					moveTop = moveTop - 30;
				}

				moveLeft = moveLeft + jQuery(window).width();

				if( osName == 'Windows XP' ) {
					var limitWidth = screenWidth - 20;
					if( moveLeft > limitWidth ) {
						moveLeft = limitWidth - moveLeft;
						window.moveBy( moveLeft, '-' + moveTop );
					} else {
						window.moveBy( 0, '-' + moveTop );
					}
				} else if( osName == 'Windows 7' ) {
					var limitWidth = screenWidth - 20;
					if( moveLeft > limitWidth ) {
						moveLeft = limitWidth - moveLeft;
						window.moveBy( moveLeft, '-' + moveTop );
					} else {
						window.moveBy( 0, '-' + moveTop );
					}
				} else {
					window.moveBy( 0, '-' + moveTop );
				}

				// IE 에서는 팝업창 Y좌표를 0으로 설정시 이동한 위치만큼 아래쪽에
				// 빈공간이 생기므로 채워준다.
				if( browserName == 'msie' ) {
					self.window.resizeBy( 0, moveTop );
				}

				if( locX == 'center' ) {
					var tmpLocX = ( screenWidth / 2 ) - ( winWidth / 2 );
					window.moveTo( tmpLocX, 0 );
					tmpLocX=null;
				} else if( locX && locY ) {
					var tmpWidth = screenWidth - winWidth;
					var tmpLocX = locX > tmpWidth ? tmpWidth : locX;
					window.moveTo( tmpLocX, 0 );
					tmpWidth=null; tmpLocX=null;
				}

				moveLeft=null; moveTop=null;
			}
		} else {
			var tmpWidth=0, tmpHeight=0;
			var locX=0, locY=0;

			if( !isNaN( window.screenX ) ) {
				locX = parseInt( window.screenX, 10 );
				locY = parseInt( window.screenY, 10 );

				tmpWidth = screenWidth - window.screenX;
				tmpHeight = screenHeight - window.screenY;
			} else {
				locX = parseInt( window.screenLeft, 10 );
				locY = parseInt( window.screenTop, 10 );

				tmpWidth = screenWidth - window.screenLeft;
				tmpHeight = screenHeight - window.screenTop;
			}

			if( browserName == 'firefox' ) {
				if( locX < 0 ) {
					locX = Math.abs(locX);
					window.moveBy( locX, 0 );
				} else {
					if( locY < 0 ) {
						locY = Math.abs(locY);
						window.moveBy( 0, locY );
					} else {
						var moveX = 0, moveY = 0;

						var tmpWinWidth = parseInt( jQuery(window).width(), 10 ) + 0;
						var tmpWinHeight = parseInt( jQuery(window).height(), 10 ) + 0;

						if( tmpWinWidth > tmpWidth ) {
							moveX = tmpWinWidth - tmpWidth;
						}

						if( tmpWinHeight > tmpHeight ) {
							moveY = tmpWinHeight - tmpHeight;
						}

						if( moveX > 0 ) {
							setTimeout(function() { window_init(locX, locY); }, 300 );

							window.moveBy( '-' + moveX, 0 );
						} else if( moveY > 0 ) {
							setTimeout(function() { window_init(locX, locY); }, 300 );

							window.moveBy( 0, '-' + moveY );
						}
					}
				}
			}
		}
	} else {
		// 모니터 영역 벗어나서 팝업창 생성시 모니터 화면에 맞춰서 이동
		var tmpWidth=0, tmpHeight=0;
		var locX=0, locY=0;

		if( !isNaN( window.screenX ) ) {
			locX = parseInt( window.screenX, 10 );
			locY = parseInt( window.screenY, 10 );

			tmpWidth = screenWidth - window.screenX;
			tmpHeight = screenHeight - window.screenY;
		} else {
			locX = parseInt( window.screenLeft, 10 );
			locY = parseInt( window.screenTop, 10 );

			tmpWidth = screenWidth - window.screenLeft;
			tmpHeight = screenHeight - window.screenTop;
		}

		if( browserName == 'msie' ) {
			if( msVersion == '9' ) {
				var tmpWinWidth = parseInt( jQuery(window).width(), 10 ) + 17;
				var tmpWinHeight = parseInt( jQuery(window).height(), 10 ) + 67;
			} else if( msVersion == '8' ) {
				var tmpWinWidth = parseInt( jQuery(window).width(), 10 ) + 7;
				var tmpWinHeight = parseInt( jQuery(window).height(), 10 ) + 29;

				locY = locY - 51;
			} else if( msVersion == '7' ) {
				var tmpWinWidth = parseInt( jQuery(window).width(), 10 ) + 0;
				var tmpWinHeight = parseInt( jQuery(window).height(), 10 ) + 29;
			} else if( msVersion == '6' ) {
				var tmpWinWidth = parseInt( jQuery(window).width(), 10 ) + 19;
				var tmpWinHeight = parseInt( jQuery(window).height(), 10 ) + 23;

				locY = locY - 24;
			}

			if( locX < 0 ) {
				locX = Math.abs(locX);
				window.moveBy( locX, 0 );
			} else {
				if( locY < 0 ) {
					locY = Math.abs(locY);
					window.moveBy( 0, locY );
				} else {
					var moveX = 0, moveY = 0;

					if( tmpWinWidth > tmpWidth ) {
						moveX = tmpWinWidth - tmpWidth;
					}

					if( tmpWinHeight > tmpHeight ) {
						moveY = tmpWinHeight - tmpHeight;
					}

					if( moveX > 0 ) {
						window.moveBy( '-' + moveX, 0 );
					} else if( moveY > 0 ) {
						window.moveBy( 0, '-' + moveY );
					}
				}
			}
		} else if( browserName == 'opera' ) {
			locY = locY - 27;
			if( locX < 0 ) {
				locX = Math.abs(locX);
				window.moveBy( locX, 0 );

				if( locY < 0 ) {
					locY = Math.abs(locY);
					window.moveBy( 0, locY );
				}
			} else {
				if( locY < 0 ) {
					locY = Math.abs(locY);
					window.moveBy( 0, locY );
				} else {
					tmpWidth = operaWidth - locX;
					tmpHeight = operaHeight - locY;

					var tmpWinWidth = parseInt( jQuery(window).width(), 10 ) + 12;
					var tmpWinHeight = parseInt( jQuery(window).height(), 10 ) + 34;

					var moveX = 0, moveY = 0;

					if( tmpWinWidth > tmpWidth ) {
						moveX = tmpWinWidth - tmpWidth;
					}

					if( tmpWinHeight > tmpHeight ) {
						moveY = tmpWinHeight - tmpHeight;
					}

					if( moveX > 0 ) {
						window.moveBy( '-' + moveX, 0 );
					} else if( moveY > 0 ) {
						window.moveBy( 0, '-' + moveY );
					}
				}
			}
		} else if( browserName == 'firefox' ) {
			if( locX < 0 ) {
				locX = Math.abs(locX);
				window.moveBy( locX, 0 );
			} else {
				if( locY < 0 ) {
					locY = Math.abs(locY);
					window.moveBy( 0, locY );
				} else {
					var moveX = 0, moveY = 0;

					var tmpWinWidth = parseInt( jQuery(window).width(), 10 ) + 18;
					var tmpWinHeight = parseInt( jQuery(window).height(), 10 ) + 77;

					if( tmpWinWidth > tmpWidth ) {
						moveX = tmpWinWidth - tmpWidth;
					}

					if( tmpWinHeight > tmpHeight ) {
						moveY = tmpWinHeight - tmpHeight;
					}

					if( moveX > 0 ) {
						window.moveBy( '-' + moveX, 0 );
					} else if( moveY > 0 ) {
						window.moveBy( 0, '-' + moveY );
					}
				}
			}
		}

		self.window.resizeBy(popWidth, popHeight); // 팝업창 리사이징
	}

	if( resizeObj == 'N' ) {
		resizeObj = 'Y';
		setTimeout(function() { window_init(locX, locY); }, 300 );
	} else {
		resizeObj = 'N';
	}
}

// 로딩시 생성한 fake Div 가 있으면 팝업 리사이징
// reason : 페이지 로딩 완료 시점을 정확하게 알기 위해
// body 끝단에 생성한 div 유무를 검사함.
function pageEndSearch(locX, locY) {
	if( jQuery('#pageEnd').length ) {
		window_init(locX, locY);
		jQuery('#pageEnd').remove();
		clearTimeout(pageEndObjFind);
	} else {
		if( pageEndObjFindCnt < 10 ) { // 과부하를 막기위해 count 10 까지만 수행하고 clear
			pageEndObjFindCnt++;
			pageEndObjFind = setTimeout(function () { pageEndSearch(locX, locY); }, 10 );
		} else {
			clearTimeout( pageEndObjFind );
		}
	}
	return false;
}

function popupInit() { // 팝업창 크기 초기화
	if( Resize_ajaxResult == 'N' ) {
		firstWidth = firstWidth - 100;
		window.resizeTo( firstWidth, firstHeight );
	} else {
		Resize_ajaxResult = 'N'; // ajax 이벤트 초기화
	}
}

// ajax 처리시 beforeunload 이벤트가 일어나는 bug Fix
var Resize_ajaxResult = 'N';
jQuery(document).ajaxComplete(function(){
	Resize_ajaxResult = 'Y';
});

var anchorRemove = 'N';
function anchorUnloadRemove() {
	jQuery('a').each(function() {
		var VAR_href = jQuery(this).attr('href');

		if( VAR_href ) {
			var tmpArr = VAR_href.split(':');
			if( tmpArr[0] == 'javascript' ) {
				anchorRemove = 'Y';
			}
		}
	});
}

// 팝업 페이지 로딩시 body 끝단에 fake Div 생성
function popupResize(obj, locX, locY) {
	if( jQuery('body').length ) {
		jQuery('body').ready(function(){
			jQuery('<div id="pageEnd"></div>').css({'width':'0px','height':'0px'}).appendTo('body');
		});
	}

	// setTimeout 선언 0.1 초 간격 10회
	pageEndObjFind = setTimeout(function () { pageEndSearch(locX, locY); }, 10 );

	jQuery(window).bind('beforeunload', function(event) {
		if( anchorRemove == 'N' ) {
			setTimeout(function(){ popupInit(); }, 100 );
		}
	});
}

function FUNC_window_popup( popupSrc, popupName, popupAttr ) { // 윈도우 팝업
	// 팝업창 주소가 없을 경우 실행 안함
	if( popupSrc ) {
		// 팝업창이름, 속성 미 입력시 Default 값으로 선언
		popupName = popupName ? popupName : 'window_popup';
		popupAttr = popupAttr ? popupAttr : 'fullscreen=yes,titlebar=no,location=no,channelmode=no,menubar=no,resizable=no,scrollbars=no,status=no,toolbar=no,directories=no';

		window.open(popupSrc, popupName, popupAttr);
	} else {
		return false;
	}
}

// 메인 Content 상품이미지 마우스 오버시 장바구니 아이콘 display
function main_cartBtn( obj, objId, act ) {
	if( act == 'open' ) {
		jQuery('.btn_cart').each(function() {
			if( jQuery(this).get(0) == jQuery('#' + objId).get(0) ) {
				jQuery(this).show();
			} else {
				jQuery(this).hide();
			}
		});
		
	} else if( act == 'close' ) {
		jQuery('#' + objId).hide();
	}
}

// 메인페이지 content slide 기능
var prevContentIdx=0, mainContentCnt = 0;
function slideMainContent() {
	var rollSpeed = 500;
	if( jQuery('.lf_menu').length ) {
		var nowIdx = 0;
		var nowObj, prevObj;

		var contentWidth = 0;
		var slideResult;

		if( !mainContentCnt ) {
			mainContentCnt = parseInt( jQuery('.lf_menu').find('>ul>li').length, 10 );
		}

		jQuery('.lf_menu').find('>ul>li').bind('click', function() {
			nowObj = jQuery('.leaflet').find('>div:eq(' + jQuery(this).index() + ')');
			prevObj = jQuery('.leaflet').find('>div:eq(' + prevContentIdx + ')');

			if( prevContentIdx != jQuery(this).index() ) {
				if( jQuery( prevObj ).css('left') != '0px' ) {
					return false;
				}

				jQuery('.lf_menu').find('>ul>li').each(function() {
					jQuery(this).removeClass('on');
				});

				jQuery(this).addClass('on');

				contentWidth = jQuery( prevObj ).width();

				if( prevContentIdx < jQuery(this).index() ) {
					jQuery( prevObj ).animate({left:'-'+contentWidth, top:0}, rollSpeed);
					jQuery( nowObj ).css({'left':contentWidth + 'px','display':'block'});
				} else {
					jQuery( prevObj ).animate({left:contentWidth, top:0}, rollSpeed);
					jQuery( nowObj ).css({'left':'-'+contentWidth + 'px','display':'block'});
				}

				jQuery( nowObj ).animate({left:0, top:0}, rollSpeed);

				prevContentIdx = jQuery(this).index();
			}
		});

		nowIdx = null; nowObj = null; prevObj = null;
		contentWidth = null; slideResult = null;

		if( jQuery('.lf_menu').find('.prev').length ) {
			jQuery('.lf_menu').find('.prev').bind('click', function() {
				var tmpIdx = prevContentIdx - 1;
				if( tmpIdx < 0 ) {
					tmpIdx = mainContentCnt - 1;
				}
				jQuery('.lf_menu').find('>ul>li:eq(' + tmpIdx + ')').trigger('click');
			});
		}

		if( jQuery('.lf_menu').find('.next').length ) {
			jQuery('.lf_menu').find('.next').bind('click', function() {
				var tmpIdx = prevContentIdx + 1;
				if( tmpIdx > mainContentCnt - 1 ) {
					tmpIdx = 0;
				}
				jQuery('.lf_menu').find('>ul>li:eq(' + tmpIdx + ')').trigger('click');
			});
		}
	}
}

// 메인페이지 content btn on, off
function mainContentBtn( obj, targetId ) {
	if( jQuery( obj ).attr('effect') != 'Y' ) {
		jQuery( obj ).bind('mouseover', function(event) {
			jQuery('#' + targetId).show();
		});

		jQuery( obj ).bind('mouseleave', function(event) {
			if( event.relatedTarget.nodeName != 'IMG' ) {
				jQuery('#' + targetId).hide();
			} else {
				if( jQuery('#' + targetId).find('>img').get(0) != event.relatedTarget ) {
					jQuery('#' + targetId).hide();
				}
			}
		});

		jQuery('#' + targetId).show();
	}
}

// 제휴몰 IFRAME 높이값 조정
var iframeFisrtTrigger = 'N';
var iframeResize = function( obj ) {
	if( jQuery('#UxWrapper').length ) {
		var thisObj = jQuery('#' + obj );

		var browserName = browserNameCheck();

		jQuery(window).bind('resize', function(){
			iFrameInit(thisObj);
		});

		var iFrameInit = function( obj ) {
			var winHeight = jQuery(window).height();
			var topHeight = jQuery('#UxWrapper').height();
			var iframeHeight = winHeight - topHeight;

			jQuery(obj).css('height', iframeHeight + 'px');
		};

		if( browserName == 'msie' ) {
			jQuery(thisObj).css({
				'overflow-x'	: 'hidden',
				'overflow-y'	: 'auto'
			});

			jQuery('html').css('overflow', 'hidden');
		}

		setTimeout(function() { iFrameInit(thisObj); }, 200);
	}
};

// 상품상세 페이지 로딩후 페이지 스크롤 스크립트
function moveLink( topValue ) {
	if( topValue ) {
		var nowTop = parseInt( topValue, 10 );
	} else {
		var nowTop = 137;
	}
	jQuery('html').animate({scrollTop:nowTop}, 500);
}

// click 시 해당 tr 배경색 변경 스크립트
function transChoiseTr() {
	if( jQuery('.step_1').length ) {
		jQuery('.step_1').find('#js_choiseTable table tr').each(function() {
			if( jQuery(this).index() == 0 ) {
				jQuery(this).addClass('on');
			} else {
				jQuery(this).removeClass('on');
			}
		});

		jQuery('.step_1').find('#js_choiseTable table tr').live('click', function() {
			var thisObj = jQuery(this);
			jQuery('.step_1').find('#js_choiseTable table tr').each(function() {
				if( jQuery(this).get(0) == jQuery(thisObj).get(0) ) {
					jQuery(this).addClass('on');
				} else {
					jQuery(this).removeClass('on');
				}
			});
		});
	}
}

function FUNC_easy_cart() {
	var viewLimit = 5;
	// 장바구니, 최근 구매내역 버튼 이벤트
	if( jQuery('.tab_q').length ) {
		// 장바구니
		if( jQuery('.tab_q').find('>ul').attr('effect') != 'Y' ) {
			if( jQuery('.tab_q').find('>ul>li').length ) {
				jQuery('.tab_q').find('>ul>li').bind('click', function() {
					if( jQuery(this).find('a').attr('href') == '#' ) {
						jQuery(this).find('a').attr('href', 'javascript:void(0)');
					}

					var thisObj = jQuery(this);

					jQuery('.tab_q').find('>ul>li').each(function() {
						if( jQuery(this).get(0) == jQuery(thisObj).get(0) ) {
							jQuery(this).addClass('on');

							if( jQuery(this).index() == '0' ) {
								jQuery('.q_basket').show();
								jQuery('.q_buy_list').hide();
							} else if( jQuery(this).index() == '1' ) {
								jQuery('.q_basket').hide();
								jQuery('.q_buy_list').show();
							}
						} else {
							jQuery(this).removeClass('on');
						}
					});
				});
			}

			jQuery('.tab_q').find('>ul').attr('effect', 'Y');
		}
	}

	var btn_change = function( obj, act ) {
		var imgSrc = jQuery(obj).attr('src');
		if( imgSrc == undefined ) { return false; }
		var tmpSrc = imgSrc.split('.');
		var tmpCnt = parseInt( tmpSrc.length, 10 ) - 1;
		var imgExt = tmpSrc[tmpCnt]; // 이미지 파일 확장자

		if( act == 'on' ) {
			if(imgSrc.indexOf("-off." + imgExt) != -1) {
				imgSrc = imgSrc.replace('-off.' + imgExt, '-on.' + imgExt);
			} else if(imgSrc.indexOf("-dis." + imgExt) != -1) {
				imgSrc = imgSrc.replace('-dis.' + imgExt, '-on.' + imgExt);
			}

			jQuery(obj).css('cursor', 'pointer');
		} else if( act == 'off' ) {
			if(imgSrc.indexOf("-on." + imgExt) != -1) {
				imgSrc = imgSrc.replace('-on.' + imgExt, '-off.' + imgExt);
			} else if(imgSrc.indexOf("-dis." + imgExt) != -1) {
				imgSrc = imgSrc.replace('-dis.' + imgExt, '-off.' + imgExt);
			}

			jQuery(obj).css('cursor', 'pointer');
		} else if( act == 'dis' ) {
			if(imgSrc.indexOf("-off." + imgExt) != -1) {
				imgSrc = imgSrc.replace('-off.' + imgExt, '-dis.' + imgExt);
			} else if(imgSrc.indexOf("-on." + imgExt) != -1) {
				imgSrc = imgSrc.replace('-on.' + imgExt, '-dis.' + imgExt);
			}

			jQuery(obj).css('cursor', 'default');
		}
		jQuery(obj).attr('src', imgSrc);
		imgSrc=null; tmpSrc=null; tmpCnt=null; imgExt=null;
	};

	var btn_init = function(obj) {
		var liCnt = jQuery(obj).find('>ul>li').length;
		if( liCnt <= viewLimit ) {
			if( jQuery(obj).find('.move_top img').length ) {
				btn_change( jQuery(obj).find('.move_top img'), 'dis' );
			}

			if( jQuery(obj).find('.move_btm img').length ) {
				btn_change( jQuery(obj).find('.move_btm img'), 'dis' );
			}
		} else {
			if( jQuery(obj).find('.move_top').length && jQuery(obj).find('.move_top').attr('effect') != 'Y' ) {
				jQuery(obj).find('.move_top').bind('click', function() {
					var nowIdx = parseInt( jQuery(obj).attr('idx'), 10 );
					nowIdx = nowIdx - 1;
					var maxCnt = parseInt( jQuery(obj).find('>ul>li').length, 10 ) - viewLimit;

					if( nowIdx < 0 ) {
						nowIdx = 0;
					} else {
						jQuery(obj).find('.move_btm img').attr('btnstatus', '');
						btn_change( jQuery(obj).find('.move_btm').find('img'), 'off' );

						if( nowIdx == 0 ) {
							jQuery(this).find('img').attr('btnstatus', 'dis');
							btn_change( jQuery(this).find('img'), 'dis' );
						} else {
							jQuery(this).find('img').attr('btnstatus', '');
							btn_change( jQuery(this).find('img'), 'off' );
						}
						jQuery(obj).find('>ul>li').hide();
						for(var i=nowIdx; i<nowIdx + viewLimit; i++ ) {
							jQuery(obj).find('>ul>li:eq(' + i + ')').show();
						} // for End
					}
					jQuery(obj).attr('idx', nowIdx);
					jQuery(this).trigger('mouseover');
				});

				jQuery(obj).find('.move_top').bind('mouseover', function() {
					var imgObj = jQuery(this).find('img');
					if( jQuery(imgObj).attr('btnstatus') != 'dis' ) {
						btn_change( jQuery(imgObj), 'on' );
					}
					imgObj=null;
				});

				jQuery(obj).find('.move_top').bind('mouseleave', function() {
					var imgObj = jQuery(this).find('img');
					if( jQuery(imgObj).attr('btnstatus') != 'dis' ) {
						btn_change( jQuery(imgObj), 'off' );
					}
					imgObj=null;
				});

				jQuery(obj).find('.move_top').attr('effect', 'Y');
				jQuery(obj).find('.move_top img').attr('btnstatus', 'dis');
				btn_change( jQuery(obj).find('.move_top img'), 'dis' );
			}

			if( jQuery(obj).find('.move_btm').length && jQuery(obj).find('.move_btm').attr('effect') != 'Y' ) {
				jQuery(obj).find('.move_btm').bind('click', function() {
					var nowIdx = parseInt( jQuery(obj).attr('idx'), 10 );
					nowIdx = nowIdx + 1;
					var maxCnt = parseInt( jQuery(obj).find('>ul>li').length, 10 ) - viewLimit;

					if( nowIdx > maxCnt ) {
						nowIdx = maxCnt;
					} else {
						jQuery(obj).find('.move_top img').attr('btnstatus', '');
						btn_change( jQuery(obj).find('.move_top img'), 'off' );

						if( nowIdx == maxCnt ) {
							jQuery(this).find('img').attr('btnstatus', 'dis');
							btn_change( jQuery(this).find('img'), 'dis' );
						} else {
							jQuery(this).find('img').attr('btnstatus', '');
							btn_change( jQuery(this).find('img'), 'off' );
						}
						jQuery(obj).find('>ul>li').hide();
						for(var i=nowIdx; i<nowIdx + viewLimit; i++ ) {
							jQuery(obj).find('>ul>li:eq(' + i + ')').show();
						} // for End
					}
					jQuery(obj).attr('idx', nowIdx);
					jQuery(this).trigger('mouseover');
				});

				jQuery(obj).find('.move_btm').bind('mouseover', function() {
					var imgObj = jQuery(this).find('img');
					if( jQuery(imgObj).attr('btnstatus') != 'dis' ) {
						btn_change( jQuery(imgObj), 'on' );
					}
					imgObj=null;
				});

				jQuery(obj).find('.move_btm').bind('mouseleave', function() {
					var imgObj = jQuery(this).find('img');
					if( jQuery(imgObj).attr('btnstatus') != 'dis' ) {
						btn_change( jQuery(imgObj), 'off' );
					}
					imgObj=null;
				});

				jQuery(obj).find('.move_btm').attr('effect', 'Y');
				jQuery(obj).find('.move_btm img').attr('btnstatus', '');
				btn_change( jQuery(obj).find('.move_btm img'), 'off' );
			}
		}
		liCnt=null;
	};

	var layer_init = function(obj) {
		var nowCnt = 0;
		jQuery(obj).find('>ul>li').each(function() {
			// 장바구니, 최근구매내역 첫번째 li 객체에 class noline 부여
			if( jQuery(this).index() == '0' ) {
				jQuery(this).addClass('noline');
			}

			// 최대 보여질 갯수만 display:block
			if( jQuery(this).index() < viewLimit ) {
				jQuery(this).show();
			} else {
				jQuery(this).hide();
			}
		});

		jQuery(obj).attr('idx', '0');
		nowCnt=null;
	};

	if( jQuery('.q_basket').length ) {
		btn_init( jQuery('.q_basket') );
		layer_init( jQuery('.q_basket') );
	}

	if( jQuery('.q_buy_list').length ) {
		btn_init( jQuery('.q_buy_list') );
		layer_init( jQuery('.q_buy_list') );
	}
}

jQuery(document).ready(function() {
	if( jQuery('.search').length ) {
		spread_topSearch();
	}

	if( jQuery('#gnb').length ) {
		spread_gnb( jQuery('#gnb') );
	}

	if( jQuery('#h_display').length ) {
		spread_gnb( jQuery('#h_display') );
	}

	if( jQuery('.relation').length ) {
		spread_relation();
	}

	if( jQuery('#js_easyCart').length ) {
		FUNC_easy_cart();
	}

	if( jQuery('a').length ) {
		anchorUnloadRemove();
	}
});

/*
	socialArea : SNS공유기능 영역
	twtMsg : 트위터 출력문구
	storyMsg : 카카오스토리 출력문구
	socialUrl : 현재 URL
*/
function setSocialMsg(socialArea, twtMsg, storyMsg, socialUrl) {
	// Kakao
	$("a.kakao", socialArea).click(function(e) {
		eventPropagationWrapper(e, function() {
			socialLink.kakaoStory(socialUrl, storyMsg);
		});
	});
	
	// Facebook
	$("a.facebook", socialArea).click(function(e) {
		eventPropagationWrapper(e, function() {
			socialLink.facebook(socialUrl);
		});
	});
	
	// Twitter
	$("a.twitter", socialArea).click(function(e) {
		eventPropagationWrapper(e, function() {
			socialLink.twitter(socialUrl, twtMsg);
		});
	});
	
	// Link Copy
	$("a.link-copy", socialArea).off().on('click', function(e){
		eventPropagationWrapper(e, function() {
			clipboard.copy(socialUrl).then(
				function() {
					alert( view_copy_messages.copylink.success );
				},
				function() {
					alert( view_copy_messages.copylink.fail );
				}
			);
		});
	});
}