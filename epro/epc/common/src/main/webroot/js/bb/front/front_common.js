// 운영체제 확인
function DS_getOSInfoStr() {
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

function DS_msCheck() { // msie 버전 check
	var vs = navigator.appVersion;
	vs = vs.toLowerCase();

	var ieVersion = '';
	if( vs.indexOf("msie 6.0") != -1 ) { ieVersion = "6"; }
	if( vs.indexOf("msie 7.0") != -1 ) { ieVersion = "7"; }
	if( vs.indexOf("msie 8.0") != -1 ) { ieVersion = "8"; }
	if( vs.indexOf("msie 9.0") != -1 ) { ieVersion = "9"; }

	return ieVersion;
}

function DS_browserNameCheck() { // browser check
	var vs = navigator.userAgent;
	vs = vs.toLowerCase();

	var browserName = '';

	if( vs.indexOf("firefox") != -1 ) { browserName = "firefox"; }
	else if( vs.indexOf("safari") != -1 ) { browserName = vs.indexOf("chrome") != -1 ? "chrome" : "safari"; }
	else if( vs.indexOf("msie") != -1 ) { browserName = "msie"; }
	else if( vs.indexOf("opera") != -1 ) { browserName = "opera"; }

	return browserName;
}

// 카테고리 메뉴
function DS_sperad_category(obj, act) {
	var rootObj;
	if( jQuery(obj).parent().find('.click_btn_dsc').length ) {
		rootObj = jQuery(obj).parent();
	} else if( jQuery(obj).parent().parent().find('.click_btn_dsc').length ) {
		rootObj = jQuery(obj).parent().parent();
	} else if( jQuery(obj).parent().parent().parent().find('.click_btn_dsc').length ) {
		rootObj = jQuery(obj).parent().parent().parent();
	}

	// rootObj : con

	var divObj;
	if( jQuery( rootObj ).find('.click_btn_dsc').length ) {
		divObj = jQuery( rootObj ).find('.click_btn_dsc');

		var rootHeight = parseInt( jQuery(rootObj).parent().outerHeight(), 10 );
		rootHeight = rootHeight - 3;

		if( jQuery( divObj ).css('display') == 'none' ) {
			if( act == 'on' ) {
				jQuery( obj ).addClass('on');
			}

			jQuery( divObj ).css('top', rootHeight+'px');
			rootHeight=null;

			// ie6 에서 select box 가 레이어를 뚫고 나오는 버그 처리
			var ieVersion = DS_msCheck();
			if( ieVersion == '6' ) {
				if( !jQuery( divObj ).parent().find('iframe').length ) {
					jQuery("<iframe>").css({
						'width'		: jQuery( divObj ).outerWidth() + 'px',
						'height'	: jQuery( divObj ).outerHeight() + 'px',
						'position'	: 'absolute',
						'left'		: jQuery( divObj ).css('left'),
						'top'		: jQuery( divObj ).css('top')
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

var DS_listViewObj;
function DS_listSpread() { // 리스트형 게시판용 펼치기
	// 명명규칙
	// 타이틀 영역	class = question
	// 본문 영역	class = answer
	// 상위객체 li 에 class=on 유무에 따라 본문영역 show, hide
	jQuery('.question').css('cursor', 'pointer');

	jQuery('.question').each(function() {
		var parentObj = jQuery(this).parent().parent();
		if( jQuery(parentObj).attr('class') == 'on' ) {
			DS_listViewObj = jQuery(parentObj);
			if( jQuery(this).parent().next().length ) {
				jQuery(this).parent().next().show();
			}
		}
	});

	jQuery('.question').bind('click', function() {
		var parentObj = jQuery(this).parent().parent();
		var viewObj = jQuery(this).parent().next();

		if( DS_listViewObj ) {
			if( jQuery(DS_listViewObj).get(0) != jQuery(parentObj).get(0) ) {
				jQuery(DS_listViewObj).removeClass('on');
			}
		}

		if( jQuery(viewObj).css('display') == 'none' ) {
			jQuery(parentObj).addClass('on');
		} else {
			jQuery(parentObj).removeClass('on');
		}

		DS_listViewObj = jQuery(parentObj);
	});
}

// 통합검색 레이어 펼치기
function DS_spread_topSearch() {
	if( jQuery('.sc_area').length ) {
		jQuery('.sc_area').find('.sc_list').bind('click', function() {
			var thisObj = jQuery(this);
			var rootObj = jQuery(this).parent();
			var layObj = jQuery(rootObj).find('.sel_lay');
			var imgSrc =jQuery(this).find('.sc_arrow img').attr('src');

			if( jQuery(layObj).css('display') == 'none' ) {
				jQuery(layObj).show();
				jQuery(this).next().attr('class', 'line_on');
				imgSrc = imgSrc.replace('-off.gif', '-on.gif');
				jQuery(this).find('.sc_arrow img').attr('src', imgSrc);
			} else {
				jQuery(layObj).hide();
				jQuery(this).next().attr('class', 'line_off');
				imgSrc = imgSrc.replace('-on.gif', '-off.gif');
				jQuery(this).find('.sc_arrow img').attr('src', imgSrc);
			}

			if( !jQuery( layObj ).attr('effect') ) {
				var selectObj = jQuery(layObj).find('ul li');
				jQuery(selectObj).css('cursor', 'pointer');

				jQuery(selectObj).bind('click', function() {
					jQuery(thisObj).find('>span:eq(0)').html( jQuery(this).html() );
					jQuery(thisObj).find('>input[type=hidden]').val( jQuery(this).attr('ref') );

					jQuery(layObj).hide();
					jQuery(thisObj).next().attr('class', 'line_off');
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

		DS_search_keyword( jQuery('.sc_area') );
	}
}

function DS_search_keyword( obj ) {
	if( jQuery( obj ).find('.sc_input #keyword').length ) {
		jQuery( obj ).find('.sc_input #keyword').focusin(function() {
			jQuery(this).val('');
			if( jQuery( obj ).find('.sc_txt_lay').length ) {
				jQuery( obj ).find('.sc_list').next().attr('class', 'line_on');
				jQuery( obj ).find('.sc_txt_lay').show();
			}
		});

		jQuery( obj ).find('.sc_input #keyword').focusout(function() {
			setTimeout(function() {
				jQuery( obj ).find('.sc_txt_lay').hide();
				jQuery( obj ).find('.sc_list').next().attr('class', 'line_off');
			}, 80);
		});
	}
}

function DS_backgroundIframe( obj ) { // ie6 용 iframe 삽입
	jQuery("<iframe>").css({
		'width'			: ( jQuery( obj ).width() - 3 ) + 'px',
		'height'		: ( jQuery( obj ).height() - 89 ) + 'px',
		'position'		: 'absolute',
		'left'			: jQuery( obj ).css('left'),
		'top'			: jQuery( obj ).css('top'),
		'z-index'		: jQuery( obj ).css('z-index') - 1,
		'background'	: 'transparent'
	}).attr({
		'frameborder'		: '0'
	}).hide().insertBefore( jQuery( obj ) );
}

var DS_gnb_depth1_arr, DS_gnb_depth2_arr;
function DS_spread_gnb( obj ) {
	var ulObj = jQuery( obj ).find('>ul');

	var liCnt = jQuery( ulObj ).find('>li').length;
	DS_gnb_depth1_arr = new Array(liCnt);

	var nowIndex = 0;

	var browserName	= DS_browserNameCheck();	// 브라우저 명
	var ieVersion	= DS_msCheck();				// IE 버전

	if( browserName == 'msie' ) {
		if( ieVersion == '6' ) {
			jQuery( ulObj ).find('>li').each(function() {
				if( !jQuery(this).find('iframe').length ) {
					DS_backgroundIframe( jQuery(this).find('>div:eq(0)') );
				}
			});
		}
	}

	jQuery( ulObj ).find('>li').bind('mouseover', function() {
		var nowObj = jQuery(this);

		// IE 6 에서 select 뚫고 나오는 버그 처리
		if( jQuery(this).find('iframe').length ) {
			jQuery(this).find('iframe').show();
		}

		jQuery(ulObj).find('>li').each(function() {
			if( jQuery(this).get(0) == jQuery(nowObj).get(0) ) {
				var imgSrc = jQuery(this).find('>a>img').attr('src');
				nowIndex = parseInt( jQuery(this).index(), 10 );
				if( !DS_gnb_depth1_arr[nowIndex] ) {
					DS_gnb_depth1_arr[nowIndex] = new Array(2);
				}

				if( !DS_gnb_depth1_arr[nowIndex][1] ) {
					if( imgSrc.indexOf('-off.gif') ) {
						imgSrc = imgSrc.replace('-off.gif', '-on.gif');
						DS_gnb_depth1_arr[nowIndex][1] = new Image();
						DS_gnb_depth1_arr[nowIndex][1].src = imgSrc;
					}
				}

				jQuery(this).find('>a>img').attr('src', DS_gnb_depth1_arr[nowIndex][1].src);

				jQuery(this).addClass('on');

				jQuery(this).find('>div:eq(0)').show();

				if( jQuery(this).find('.depth2').length ) {
					if( jQuery(this).find('.depth2').attr('effectResult') != 'Y' ) {
						var parentObj = jQuery(this);
						jQuery(this).find('.depth2>ul>li').bind('mouseover', function() {
							jQuery(this).addClass('on');
							jQuery(parentObj).find('.depth3').hide();
							jQuery(parentObj).find('.depth3:eq(' + jQuery(this).index() + ')').show();
							jQuery(parentObj).find('.depth2').attr('lastIdx', jQuery(this).index() );

							if( jQuery(nowObj).find('iframe').length ) {
								jQuery(nowObj).find('iframe').css({
									'width'		: ( jQuery(nowObj).find('>div:eq(0)').width() - 3 ) + 'px',
									'height'	: ( jQuery(nowObj).find('>div:eq(0)').height() - 4 ) + 'px'
								});
							}
						});

						jQuery(this).find('.depth2>ul>li').bind('mouseleave', function() {
							jQuery(this).removeClass('on');
						});

						if( jQuery(this).find('.depth3').length ) {
							jQuery(this).find('.depth3>ul>li').bind('mouseover', function() {
								jQuery(this).addClass('on');
							});

							jQuery(this).find('.depth3>ul>li').bind('mouseleave', function() {
								jQuery(this).removeClass('on');
							});

							jQuery(this).find('.depth3').hide();
							jQuery(this).find('.depth3:eq(0)').show();
						}

						jQuery(this).find('.depth2').attr('effectResult', 'Y');
					}

					var lastIdx = jQuery(this).find('.depth2').attr('lastIdx');
					lastIdx = lastIdx ? parseInt( lastIdx, 10 ) : 0;

					jQuery(this).find('.depth2>ul>li').removeClass('on');
					jQuery(this).find('.depth2>ul>li:eq(' + lastIdx + ')').addClass('on');

					lastIdx=null;
				}
				imgSrc=null;
			}
		});
	});

	jQuery( ulObj ).find('>li').bind('mouseleave', function() {
		var imgSrc = jQuery(this).find('>a>img').attr('src');
		nowIndex = parseInt( jQuery(this).index(), 10 );
		if( !DS_gnb_depth1_arr[nowIndex] ) {
			DS_gnb_depth1_arr[nowIndex] = new Array(2);
		}

		if( !DS_gnb_depth1_arr[nowIndex][0] ) {
			if( imgSrc.indexOf('-on.gif') ) {
				imgSrc = imgSrc.replace('-on.gif', '-off.gif');
				DS_gnb_depth1_arr[nowIndex][0] = new Image();
				DS_gnb_depth1_arr[nowIndex][0].src = imgSrc;
			}
		}

		jQuery(this).find('>a>img').attr('src', DS_gnb_depth1_arr[nowIndex][0].src);

		jQuery(this).removeClass('on');

		jQuery(this).find('>div:eq(0)').hide();

		// MOUSE OUT 이벤트에 해당 하위 메뉴들 초기화
		var lastIdx = parseInt( jQuery(this).find('.depth2').attr('lastIdx'), 10 );
		if( lastIdx > 0 ) {
			jQuery(this).find('.depth2').attr('lastIdx', '0');
			jQuery(this).find('.depth3').hide();
			jQuery(this).find('.depth3:eq(0)').show();

			// IE 6 에서 select 뚫고 나오는 버그 처리
			if( jQuery(this).find('iframe').length ) {
				jQuery(this).find('iframe').hide();

				jQuery(this).find('iframe').css({
					'width'		: ( jQuery(this).find('>div:eq(0)').width() - 3 ) + 'px',
					'height'	: ( jQuery(this).find('>div:eq(0)').height() - 4 ) + 'px'
				});
			}
		} else {
			if( jQuery(this).find('iframe').length ) {
				jQuery(this).find('iframe').hide();
			}
		}
		imgSrc=null; lastIdx=null;
	});
}

var DS_pageEndObj = 'N';
var DS_pageEndObjFind = '';
var DS_pageEndObjFindCnt = 0;
var DS_firstWidth = 0; var DS_firstHeight = 0;

var DS_resizeObj = 'N';

function DS_window_init(locX, locY) { // 윈도우 팝업 사이즈 리사이징
	var browserName	= DS_browserNameCheck(); // 브라우저 명
	var osName		= DS_getOSInfoStr();
	var msVersion	= DS_msCheck();

	// 현재 창 width, height
	if( window.innerWidth ) {
		var winWidth = parseInt( window.innerWidth, 10 );
	} else {
		var winWidth = parseInt( document.documentElement.clientWidth, 10 );
	}

	var operaHeight = 0;
	if( browserName == 'opera' ) {
		operaHeight = parseInt( opener.window.outerHeight, 10 ) - 50;
	}

	var winHeight = parseInt( jQuery(window).height(), 10 );

	DS_firstWidth = winWidth; DS_firstHeight = winHeight;

	// document width, height
	var docWidth = parseInt( jQuery('#pop_wrap').width(), 10 );
	var docHeight = parseInt( jQuery('#pop_wrap').height(), 10 );

	// 모니터 해상도 width, height
	var screenWidth = parseInt( screen.availWidth, 10 );
	var screenHeight = parseInt( screen.availHeight, 10 );

	// 변화시킬 값을 계산 ( document 값 - window 값 )
	var popWidth = docWidth - winWidth;
	var popHeight = docHeight - winHeight;

	var heightAll = 'N';

	// document height 가 모니터 height 보다 클경우
	// XP, 7 운영체제에서 각 브라우저 마다 작업표시줄 크기를 다르게 인식하므로 개별 계산
	if( docHeight > screenHeight ) {
		heightAll = 'Y';
		popHeight = screenHeight - winHeight;
		if( browserName == 'msie' ) {
			if( osName == 'Windows XP' ) {
				popWidth = popWidth + 1;
				popHeight = popHeight - 38;
			} else if( osName == 'Windows 7' ) {
				popWidth = popWidth + (window.outerWidth - window.innerWidth);
				popWidth = popWidth + 1;
				popHeight = popHeight - 38;
			}
		} else if( browserName == 'firefox' ) {
			if( osName == 'Windows XP' ) {
				popWidth = popWidth + 18;
				popHeight = screenHeight - window.outerHeight;
			} else if( osName == 'Windows 7' ) {
				popWidth = popWidth + (window.outerWidth - window.innerWidth);
				popWidth = popWidth - 1;
				popHeight = popHeight - 77;
			}			
		} else if( browserName == 'chrome' || browserName == 'safari' ) {
			if( osName == 'Windows XP' ) {
				popWidth = popWidth + 17;
			} else if( osName == 'Windows 7' ) {
				popWidth = popWidth + 17;
			}
		} else if( browserName == 'opera' ) {
			if( osName == 'Windows XP' ) {
				popWidth = popWidth + 17;
				popHeight = operaHeight - winHeight;
			} else if( osName == 'Windows 7' ) {
				popWidth = popWidth + 17;
				popHeight = operaHeight - winHeight;
			}
		}
	}

	// document height가 모니터 height 보다 클 경우 실행
	if( heightAll == 'Y' ) {
		var moveLeft = 0, moveTop = 0;
		if( window.screenTop ) {
			moveLeft = window.screenLeft;
			moveTop = window.screenTop;
		} else {
			moveLeft = window.screenX;
			moveTop = window.screenY;
		}

		if( moveTop > 0 ) {
			if( browserName == 'msie' ) {
				moveTop = moveTop - 30;
			}
			window.moveBy(0, '-' + moveTop);
		} else {
			window.moveBy(0, moveTop);
		}
	} else { // document 가 현재 모니터 영역에서 벗어났을 경우 실행
		var moveLeft = 0, moveTop = 0;
		if( window.screenTop ) {
			moveLeft = window.screenLeft;
			moveTop = window.screenTop;
		} else {
			moveLeft = window.screenX;
			moveTop = window.screenY;
		}

		// XP, 7 으로 구분하여 각 브라우저들을 개별적으로 계산
		// 각종 브라우저 예외 및 윈도우 창 인식 값이 다르므로 개별 처리
		if( osName == 'Windows XP' ) {
			if( browserName == 'chrome' || browserName == 'safari' ) {
				popHeight = popHeight + 19;	
			} else if( browserName == 'msie' ) {
				moveTop = moveTop - 30;
				var topValue = ( moveTop + jQuery(window).height() ) + 37;
				if( screenHeight < topValue ) {
					topValue = screenHeight - topValue;
					if( DS_resizeObj == 'Y' ) {
						window.moveBy(0, topValue);
					}
				}
			} else if( browserName == 'firefox' ) {
				var topValue = ( moveTop + jQuery(window).height() ) + 70;
				if( screenHeight < topValue ) {
					topValue = screenHeight - topValue;
					if( DS_resizeObj == 'Y' ) {
						window.moveBy(0, topValue);
					}
				}
			} else if( browserName == 'opera' ) {
				moveTop = moveTop - 250;
				var topValue = ( moveTop + jQuery(window).height() );
				if( operaHeight < topValue ) {
					topValue = operaHeight - topValue;
					if( DS_resizeObj == 'Y' ) {
						window.moveBy(0, topValue);
					}
				}
			}
		} else if( osName == 'Windows 7' ) {
			if( browserName == 'chrome' || browserName == 'safari' ) {
				popHeight = popHeight + 17;
			} else if( browserName == 'firefox' ) {
				popHeight = popHeight + 2;
				var topValue = ( moveTop + jQuery(window).height() ) + 0;
				if( screenHeight < topValue ) {
					topValue = screenHeight - topValue;
					if( DS_resizeObj == 'Y' ) {
						window.moveBy(0, topValue);
					}
				}
			} else if( browserName == 'msie' ) {
				popHeight = popHeight + 2;
				var topValue = ( moveTop + jQuery(window).height() ) + 0;
				if( screenHeight < topValue ) {
					topValue = screenHeight - topValue;
					topValue = topValue - 7;
					if( DS_resizeObj == 'Y' ) {
						window.moveBy(0, topValue);
					}
				}
			} else if( browserName == 'opera' ) {
				moveTop = moveTop - 250;
				var topValue = ( moveTop + jQuery(window).height() ) + 0;
				if( operaHeight < topValue ) {
					topValue = operaHeight - topValue;
					if( DS_resizeObj == 'Y' ) {
						window.moveBy(0, topValue);
					}
				}
			}
		}
	}
	
	window.resizeBy(popWidth, popHeight);

	// 정확한 윈도우 창 위치 및 리사이징을 위해 0.1 초 뒤 한번도 실행
	// 한번더 실행 함으로써 로딩 딜레이 및 이상현상에서 계산되지 못한 나머지 값들을 계산하여 재 리사지잉
	if( DS_resizeObj == 'N' ) {
		DS_resizeObj = 'Y';
		setTimeout(function() { DS_window_init(locX, locY); }, 100 );
	} else {
		DS_resizeObj = 'N';
	}

	return false;
}

// 로딩시 생성한 fake Div 가 있으면 팝업 리사이징
// reason : 페이지 로딩 완료 시점을 정확하게 알기 위해
// body 끝단에 생성한 div 유무를 검사함.
function DS_pageEndSearch(locX, locY) {
	if( jQuery('#pageEnd').length ) {
		DS_window_init(locX, locY);
		jQuery('#pageEnd').remove();
		clearTimeout( DS_pageEndObjFind );
	} else {
		if( DS_pageEndObjFindCnt < 10 ) { // 과부하를 막기위해 count 10 까지만 수행하고 clear
			DS_pageEndObjFindCnt++;
			DS_pageEndObjFind = setTimeout(function () { DS_pageEndSearch(locX, locY); }, 20 );
		} else {
			clearTimeout( DS_pageEndObjFind );
		}
	}
	return false;
}

function DS_popupInit() {
	if( DS_Resize_ajaxResult == 'N' ) {
		DS_firstWidth = DS_firstWidth - 100;
		DS_firstHeight = DS_firstHeight - 100;
		window.resizeTo( DS_firstWidth, DS_firstHeight );
	} else {
		DS_Resize_ajaxResult = 'N'; // ajax 이벤트 초기화
	}
}

// ajax 처리시 beforeunload 이벤트가 일어나는 bug 때문에 선언
var DS_Resize_ajaxResult = 'N';

// ajax 처리시 Y를 선언하여 beforeunload 발생하여도 
jQuery(document).ajaxComplete(function(){
	DS_Resize_ajaxResult = 'Y';
});

// 팝업 페이지 로딩시 body 끝단에 fake Div 생성
function DS_popupResize(obj, locX, locY) {
	if( jQuery(obj).find('body').length ) {
		jQuery('body').ready(function(){
			jQuery('<div id="pageEnd"></div>').css({'width':'0px','height':'0px'}).appendTo('body');
		});
	}

	// setTimeout 선언 0.1 초 간격 10회
	DS_pageEndObjFind = setTimeout(function () { DS_pageEndSearch(locX, locY); }, 10 );

	jQuery(window).bind('beforeunload', function() {
		//setTimeout(function(){ DS_popupInit(); }, 20 );
	});
}

// 2011-10-14 검색어 랭킹
var DS_noticeMouseResult = 'N';
function DS_rolling(options) {
	var self = this;
	this.object = document.getElementById(options.rollId);
	if( !this.object ) { return false; }
	jQuery(this.object).parent().bind('mouseover', function(e) {
		self.stop();
	});

	jQuery(this.object).parent().bind('mouseleave', function(e) {
		this.control = setTimeout(function() {self.play();}, 1000);
	});

	this.delay = 2000; this.speed = 1; this.step = 1; this.mover = true;
	if( jQuery(this.object).find('>div:eq(0)').css('height') ) {
		this.elChildHeight = jQuery(this.object).find('>div:eq(0)').css('height');
		this.elChildHeight = this.elChildHeight.replace('px', '');
	} else {
		this.elChildHeight = 0;
	}
	this.elHeight = this.object.offsetHeight;
	this.elPosition = 0;

	var cloneDiv = jQuery(this.object).find('>div:eq(0)').clone();
	jQuery(cloneDiv).attr('id', 'notice_last');
	jQuery(this.object).append( jQuery(cloneDiv) );

	this.control = setTimeout(function() {self.play()}, this.delay);
}
DS_rolling.prototype = {
	play:function() {
		if( DS_noticeMouseResult == 'Y' ) {
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
	}
};

var DS_prod_list_effectResult = 'N';
var DS_prod_list_nowIdx = 0;
var DS_prod_list_prodCnt = 0;
function DS_prod_list() {
	var rootObj = jQuery('.prod_bx');

	if( jQuery( rootObj ).find('.bx_hope>ul>li').length ) {
		if( DS_prod_list_effectResult == 'N' || jQuery( rootObj ).find('.bx_hope>ul>li').length > 4 ) {
			jQuery( rootObj ).find('.bx_hope>ul>li').bind('mouseover', function() {
				jQuery(this).find('.prod_img').addClass('prod_on');
			});

			jQuery( rootObj ).find('.bx_hope>ul>li').bind('mouseleave', function() {
				jQuery(this).find('.prod_img').removeClass('prod_on');
			});

			var prevBtnSrc = jQuery( rootObj ).find('.q_prod_list>p:eq(0) img').attr('src'); // 이전보기 버튼
			prevBtnSrc = prevBtnSrc.replace('prev-on.gif', 'prev-off.gif'); // 페이지 로딩시 이전보기 버튼 비활성화
			jQuery( rootObj ).find('.q_prod_list>p:eq(0) img').attr('src', prevBtnSrc);
			jQuery( rootObj ).find('.q_prod_list>p:eq(0) img').css('cursor', 'default');

			DS_prod_list_prodCnt = jQuery( rootObj ).find('.bx_hope>ul>li').length;
			if( DS_prod_list_prodCnt < 5 ) {
				var nextBtnSrc = jQuery( rootObj ).find('.q_prod_list>p:eq(1) img').attr('src'); // 다음보기 버튼
				nextBtnSrc = nextBtnSrc.replace('next-on.gif', 'next-off.gif'); // 상품 5개 미만시 다음보기 버튼 비활성화
				jQuery( rootObj ).find('.q_prod_list>p:eq(1) img').attr('src', nextBtnSrc);
				jQuery( rootObj ).find('.q_prod_list>p:eq(1) img').css('cursor', 'default');
			} else {
				jQuery( rootObj ).find('.q_prod_list>p:eq(1) img').css('cursor', 'pointer');
			}

			var prodBtnEffect = function( obj ) {
				var lastIdx = DS_prod_list_prodCnt - 4;

				var prevSrc = jQuery( obj ).find('>p:eq(0) img').attr('src');
				var prevCursor = '';
				var nextSrc = jQuery( obj ).find('>p:eq(1) img').attr('src');
				var nextCursor = '';

				if( DS_prod_list_nowIdx == 0 ) {
					prevSrc = prevSrc.replace('prev-on.gif', 'prev-off.gif');
					if( lastIdx > 0 ) {
						nextSrc = nextSrc.replace('next-off.gif', 'next-on.gif');
						nextCursor = 'pointer';
					} else {
						nextSrc = nextSrc.replace('next-on.gif', 'next-off.gif');
						nextCursor = 'default';
					}
				} else if( DS_prod_list_nowIdx == lastIdx ) {
					prevSrc = prevSrc.replace('prev-off.gif', 'prev-on.gif');
					prevCursor = 'pointer';
					nextSrc = nextSrc.replace('next-on.gif', 'next-off.gif');
					nextCursor = 'default';
				} else {
					if( DS_prod_list_nowIdx > 0 ) {
						prevSrc = prevSrc.replace('prev-off.gif', 'prev-on.gif');
						prevCursor = 'pointer';
					}

					if( DS_prod_list_prodCnt > DS_prod_list_nowIdx ) {
						nextSrc = nextSrc.replace('next-off.gif', 'next-on.gif');
						nextCursor = 'pointer';
					}
				}

				jQuery( obj ).find('>p:eq(0) img').attr('src', prevSrc);
				jQuery( obj ).find('>p:eq(0) img').css('cursor', prevCursor);

				jQuery( obj ).find('>p:eq(1) img').attr('src', nextSrc);
				jQuery( obj ).find('>p:eq(1) img').css('cursor', nextCursor);

				prevSrc = null; nextSrc = null;
			};

			var prodReload = function( obj ) {
				var startIdx = DS_prod_list_nowIdx;
				var endIdx = startIdx + 3;

				jQuery( obj ).hide();

				jQuery( obj ).each(function() {
					if( startIdx <= jQuery(this).index() && jQuery(this).index() <= endIdx ) {
						jQuery(this).show();
					}
				});
			};

			jQuery( rootObj ).find('.q_prod_list>p:eq(0)').bind('click', function() {
				if( DS_prod_list_nowIdx > 0 ) {
					DS_prod_list_nowIdx--;

					prodReload( jQuery( rootObj ).find('.bx_hope>ul>li') );
				}

				prodBtnEffect( jQuery( rootObj ).find('.q_prod_list') );
			});

			jQuery( rootObj ).find('.q_prod_list>p:eq(1)').bind('click', function() {
				var lastIdx = DS_prod_list_prodCnt - 4;
				if( lastIdx > DS_prod_list_nowIdx ) {
					DS_prod_list_nowIdx++;

					prodReload( jQuery( rootObj ).find('.bx_hope>ul>li') );
				}

				prodBtnEffect( jQuery( rootObj ).find('.q_prod_list') );
			});

			// 페이지 로딩시 4개 상품까지만 display block 나머지는 none
			var firstLimit = 0;
			jQuery( rootObj ).find('.bx_hope>ul>li').each(function() {
				if( firstLimit < 4 ) {
					jQuery(this).show();
				} else {
					jQuery(this).hide();
				}
				firstLimit++;
			});
			firstLimit = null;

			DS_prod_list_effectResult = 'Y';

			prodBtnEffect( jQuery( rootObj ).find('.q_prod_list') );
		}
	}
}

var DS_quickStartTop = 0;
var DS_quickMenuObj;

var DS_quickMenu = function( obj ) {
	var quickWidth = parseInt( jQuery( obj ).width(), 10 );

	var locType = '';
	if( jQuery( obj ).parent().attr('id') == 'con_wrap' ) {
		locType = 'in';
	} else {
		locType = 'out';
	}

	var quickInit = function( thisObj, thisType, act ) {
		var objTop = 0;
		if( !DS_quickStartTop ) {
			objTop = jQuery( obj ).css('margin-top');
			objTop = parseInt( objTop.replace('px'), 10 );
			DS_quickStartTop = objTop;
		} else {
			var scrollTop = parseInt( jQuery(window).scrollTop(), 10 );
			if( DS_quickStartTop < scrollTop ) {
				objTop = scrollTop;
			} else {
				objTop = DS_quickStartTop + scrollTop;
			}
			scrollTop=null;
		}

		var bodyWidth = 0;
		var contentWidth = parseInt( jQuery('#con_wrap').width(), 10 );
		var mainWidth = parseInt( jQuery('#con_area').width(), 10 );
		var tmpWidth = contentWidth - mainWidth;
		var quickMarginLeft = tmpWidth - quickWidth;

		if( thisType == 'in' ) {
			bodyWidth = 0;

			var objLeft = mainWidth + quickMarginLeft;
		} else if( thisType == 'out' ) {
			bodyWidth = parseInt( document.getElementById('wrap').offsetWidth, 10 );

			var objLeft = (bodyWidth - contentWidth) / 2;
			objLeft = ( mainWidth + objLeft) + quickMarginLeft;
		}

		var diffWidth = mainWidth + quickMarginLeft;
		if( act == 'resize' ) {
			if( diffWidth < objLeft ) {
				jQuery( obj ).css({
					'top'		: objTop + 'px',
					'left'		: objLeft + 'px'
				});
			}
		} else {
			if( diffWidth > objLeft ) {
				objLeft = diffWidth;
			}

			jQuery( obj ).css({
				'margin-top'	: '0px',
				'top'			: objTop + 'px',
				'left'			: objLeft + 'px',
				'position'		: 'absolute',
				'z-index'		: '3000'
			});
		}
	};

	var contentHeight = parseInt( jQuery('#wrap').height(), 10 );
	var objHeight = parseInt( jQuery( obj ).height(), 10 );
	var scrollLimit = contentHeight - objHeight;

	var quickMove = function( thisObj ) {
		if( jQuery(window).scrollTop() > DS_quickStartTop ) {
			if( scrollLimit > jQuery(window).scrollTop() ) {
				jQuery( thisObj ).stop();
				jQuery( thisObj ).animate({'top':jQuery(window).scrollTop()}, 500 );
			}
		} else {
			var nowTop = jQuery( thisObj ).css('top');
			nowTop = parseInt( nowTop.replace('px'), 10 );

			if( nowTop != DS_quickStartTop ) {
				jQuery( thisObj ).stop();
				jQuery( thisObj ).animate({'top':DS_quickStartTop}, 500 );
			}
			nowTop=null;
		}
	};

	quickInit( obj, locType );

	if( jQuery('.prod_bx').length ) {
		DS_prod_list();
	}

	jQuery(window).scroll(function() {
		quickMove( obj );
	});

	jQuery(window).resize(function() {
		quickInit( obj, locType, 'resize');
	});

	// opera 에서 페이지 로딩시 이전 스크롤 이동한 값 catch 못하는 버그
	// 강제로 scroll 이벤트 발생
	var bName = DS_browserNameCheck();
	if( bName == 'opera' ) { jQuery(window).trigger('scroll'); }
	bName=null;
}

jQuery(document).ready(function() {
	if( jQuery('.sc_area').length ) {
		DS_spread_topSearch();
	}

	if( jQuery('#gnb').length ) {
		DS_spread_gnb( jQuery('#gnb') );
	}

	if( jQuery('.top_notice').length ) {
		rank_roll = new DS_rolling({rollId: "n_list"});
	}

	if( jQuery('#quick_area').length ) {
		if( jQuery('#con_wrap').length && jQuery('#con_area').length ) {
			DS_quickMenuObj = new DS_quickMenu( document.getElementById('quick_area') );
		}
	}
});