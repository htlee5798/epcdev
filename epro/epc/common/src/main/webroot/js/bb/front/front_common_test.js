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

var DS_gnb_depth1_arr, DS_gnb_depth2_arr;
function DS_spread_gnb( obj ) {
	var ulObj = jQuery( obj ).find('>ul');

	var liCnt = jQuery( ulObj ).find('>li').length;
	DS_gnb_depth1_arr = new Array(liCnt);

	var nowIndex = 0;

	jQuery( ulObj ).find('>li').bind('mouseover', function() {
		var nowObj = jQuery(this);
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
						});

						jQuery(this).find('.depth2>ul>li').bind('mouseleave', function() {
							jQuery(this).removeClass('on');
						});

						if( jQuery(this).find('.depth3').length ) {
							jQuery(this).find('.depth3').hide();
							jQuery(this).find('.depth3:eq(0)').show();
						}

						jQuery(this).find('.depth2').attr('effectResult', 'Y');
					}

					var lastIdx = jQuery(this).find('.depth2').attr('lastIdx');
					lastIdx = lastIdx ? parseInt( lastIdx, 10 ) : 0;

					jQuery(this).find('.depth2>ul>li').removeClass('on');
					jQuery(this).find('.depth2>ul>li:eq(' + lastIdx + ')').addClass('on');
				}
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
	});
}

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

var DS_pageEndObj = 'N';
var DS_pageEndObjFind = '';
var DS_pageEndObjFindCnt = 0;
var DS_firstWidth = 0; var DS_firstHeight = 0;

function DS_window_init(locX, locY) { // 윈도우 팝업 사이즈 리사이징
	if( locX != 'center' ) {
		if( locX && locY ) {
			var winLocX = locX ? locX : 0; // 가로 위치
			var winLocY = locY ? locY : 0; // 세로 위치
		}
	}

	// MS IE 에서 정확하게 document width 값을 계산하기 위해
	// 임의적으로 5px씩 리사이징후 계산
	if( jQuery.browser.msie ) {
		window.resizeBy(-5, -5);
	}

	var browserName = DS_browserNameCheck(); // 브라우저 명

	// 현재 창 width, height
	var winWidth = parseInt( jQuery(window).width(), 10 );
	var winHeight = parseInt( jQuery(window).height(), 10 );

	DS_firstWidth = winWidth; DS_firstHeight = winHeight;

	// document width, height
	var docWidth = parseInt( jQuery(document).width(), 10 );
	var docHeight = parseInt( jQuery('body').innerHeight(), 10 );

	// 모니터 해상도 width, height
	var screenWidth = parseInt( screen.availWidth, 10 );
	var screenHeight = parseInt( screen.availHeight, 10 );

	// 각 브라우져 별 widht, height 계산
	if( browserName == 'opera' ) {
		// 오페라에서 height 값이 67 을 빼줘야 부모창의 innerHeight 값이 된다
		screenWidth = parseInt( opener.window.outerWidth, 10 );
		screenHeight = parseInt( opener.window.outerHeight, 10 ) - 67;
	} else {
		// IE 에서 팝업창 width 값을 다르게 잡아야 함
		// width 값이 계속 늘어나는 버그
		if( browserName == 'msie' ) {
			docWidth = document.body.scrollWidth;
		}
		ieRegExp = null;
	}

	// 파이어폭스 에서 height 90px 더 빼줘야 screen.availHeight 값이 정확하게 나온다.
	if( browserName == 'firefox' ) {
		// 윈도우 height가 document height 보다 클경우 document height 로 맞춰준다
		// reason : screen height 를 넘어가는 경우 처리 ( firefox 에서만 발생 )
		docWidth = docWidth > screenWidth ? docWidth - ( docWidth - screenWidth ) : docWidth;
		docHeight = docHeight > screenHeight ? ( docHeight - ( docHeight - screenHeight ) ) - 90 : docHeight + 3;
	} else {
		docWidth = docWidth > screenWidth ? docWidth - ( docWidth - screenWidth ) : docWidth;
		docHeight = docHeight > screenHeight ? docHeight - ( docHeight - screenHeight ) : docHeight;
	}

	// 변화시킬 값을 계산 ( document 값 - window 값 )
	var popWidth = docWidth - winWidth;
	var popHeight = docHeight - winHeight;

	// resize 후의 팝업창 감지하여 스크롤바 width 값 제거
	if( jQuery.browser.msie ) {
		if( jQuery.browser.version == '9.0' ) {
			var fixedWidth_before = parseInt( window.outerWidth, 10 );
		} else {
			var fixedWidth_before = parseInt( document.body.scrollWidth, 10 );
		}
	} else {
		var fixedWidth_before = parseInt( window.outerWidth, 10 );
	}

	window.resizeBy(popWidth, popHeight); // 팝업창 리사이징

	if( browserName == 'msie' ) {
		if( jQuery.browser.version == '9.0' ) {
			var fixedWidth_after = parseInt( window.outerWidth, 10 );
			var diffWin = parseInt( window.outerHeight, 10 );
		} else {
			var fixedWidth_after = parseInt( document.body.scrollWidth, 10 );
			var diffWin = parseInt( document.body.scrollHeight, 10 );
		}
	} else {
		var fixedWidth_after = parseInt( window.outerWidth, 10 );
		var diffWin = parseInt( window.outerHeight, 10 );
	}

	var diffObj = parseInt( jQuery(document).height(), 10 );

	// height 값 비교하여 스크롤 유무 판단
	var nowOs = DS_getOSInfoStr();

	if( diffWin > diffObj ) {
		// 스크롤바 미 사용시 스크롤바 width 제거
		var minusWidth = fixedWidth_before - fixedWidth_after;
		var resizeAct = 'Y';

		// Window XP 에서 윈도우 팝업 widht 계산시 버그 처리
		if( nowOs == 'Windows XP' ) {
			if( minusWidth == -1 || minusWidth == -17 ) {
				resizeAct = 'N';
			}
		}

		var changeWidth = 0;
		if( resizeAct == 'Y' ) {
			if( fixedWidth_before != fixedWidth_after ) {
				// Window XP 에서는 1px 을 덜 빼줘야 가로스크롤이 생기지 않는다.
				if( nowOs == 'Windows XP' ) {
					changeWidth = -16;
				} else {
					changeWidth = -17;
				}

				if( jQuery.browser.version != '9.0' ) {
					// safari, chrome, firefox 만 새로 window, document 의 width 값을 다시 계산
					if( browserName == 'safari' || browserName == 'chrome' || browserName == 'firefox' ) {
						var s_winWidth = parseInt( window.outerWidth, 10 );
						var s_docWidth = parseInt( jQuery('div:eq(0)').outerWidth(), 10 );
						var s_reWidth = s_winWidth - s_docWidth;

						if( s_reWidth > 20 ) {
							window.resizeBy( changeWidth, 0 );
						}

						s_winWidth = null; s_docWidth = null; s_reWidth = null;
					} else {
						window.resizeBy( changeWidth, 0 );
					}
				}
			}
		}
	}

	// window, document 의 width 값을 다시 계산
	winHeight = window.innerHeight;
	docHeight = document.body.scrollHeight;

	if( winHeight < docHeight ) {
	} else {
		if( browserName == 'msie' ) {
			// ie 7,8,9, 에서 세로 스크롤 영역 width 값 버그때문에 한번더 width 계산하여 제거
			if( jQuery.browser.version != '6.0' ) {
				var t_winWidth = parseInt( window.outerWidth, 10 );
				var t_docWidth = parseInt( jQuery('div:eq(0)').outerWidth(), 10 );
				var t_reWidth = t_winWidth - t_docWidth;
				if( t_reWidth > 20 ) {
					window.resizeBy( -17, 0 );
				}
				t_winWidth = null; t_docWidth = null; t_reWidth = null;
			}
		}
	}

	if( locX != 'center' ) {
		if( locX && locY ) {
			window.moveTo( winLocX, winLocY ); // 팝업창 위치 left 0px, top 0px
		}
	} else {
		// center 정렬을 위해 현재 창 width, height 다시계산
		winWidth = parseInt( jQuery(window).width(), 10 );
		winHeight = parseInt( jQuery(window).height(), 10 );

		var centerX = ( screenWidth / 2 ) - ( winWidth / 2 );
		var centerY = ( screenHeight / 2 ) - ( winHeight / 2 );

		window.moveTo( centerX, centerY ); // 팝업창 위치 left 0px, top 0px
	}

	// 사용한 변수 init
	ua=null,operaRegExp=null,firefoxRegExp=null,winWidth=null,winHeight=null,docWidth=null,docHeight=null,screenWidth=null,screenHeight=null;
	popWidth=null,popHeight=null,fixedWidth_before=null,fixedWidth_after=null,diffWin=null,diffObj=null,nowOs=null,changeWidth=null;
	vs=null,vsText=null,centerX=null,centerY=null;

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
			pageEndObjFind = setTimeout(function () { DS_pageEndSearch(locX, locY); }, 10 );
		} else {
			clearTimeout( DS_pageEndObjFind );
		}
	}
	return false;
}

function DS_popupInit() {
	DS_firstWidth = DS_firstWidth - 100;
	window.resizeTo( DS_firstWidth, DS_firstHeight );
	self.location.reload();
}

// 팝업 페이지 로딩시 body 끝단에 fake Div 생성
function DS_popupResize(obj, locX, locY) {
	if( jQuery(obj).find('body').length ) {
		jQuery('body').ready(function(){
			jQuery('<div id="pageEnd"></div>').css({'width':'0px','height':'0px'}).appendTo('body');
		});
	}

	// setTimeout 선언 0.1 초 간격 10회
	pageEndObjFind = setTimeout(function () { DS_pageEndSearch(locX, locY); }, 10 );

	jQuery(window).unload(function() {
		DS_popupInit();
		return false;
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
		if( DS_prod_list_effectResult == 'N' ) {
			jQuery( rootObj ).find('.bx_hope>ul>li').bind('mouseover', function() {
				jQuery(this).find('.prod_img').addClass('prod_on');
			});

			jQuery( rootObj ).find('.bx_hope>ul>li').bind('mouseleave', function() {
				jQuery(this).find('.prod_img').removeClass('prod_on');
			});

			var prevBtnSrc = jQuery( rootObj ).find('.q_prod_list>p:eq(0) img').attr('src'); // 이전보기 버튼
			prevBtnSrc = prevBtnSrc.replace('prev-on.gif', 'prev-off.gif'); // 페이지 로딩시 이전보기 버튼 비활성화
			jQuery( rootObj ).find('.q_prod_list>p:eq(0) img').attr('src', prevBtnSrc);

			DS_prod_list_prodCnt = jQuery( rootObj ).find('.bx_hope>ul>li').length;
			if( DS_prod_list_prodCnt < 5 ) {
				var nextBtnSrc = jQuery( rootObj ).find('.q_prod_list>p:eq(1) img').attr('src'); // 다음보기 버튼
				nextBtnSrc = nextBtnSrc.replace('next-on.gif', 'next-off.gif'); // 상품 5개 미만시 다음보기 버튼 비활성화
				jQuery( rootObj ).find('.q_prod_list>p:eq(1) img').attr('src', nextBtnSrc);
			}

			var prodBtnEffect = function( obj ) {
				var lastIdx = DS_prod_list_prodCnt - 4;

				var prevSrc = jQuery( obj ).find('>p:eq(0) img').attr('src');
				var nextSrc = jQuery( obj ).find('>p:eq(1) img').attr('src');

				if( DS_prod_list_nowIdx == 0 ) {
					prevSrc = prevSrc.replace('prev-on.gif', 'prev-off.gif');
					if( lastIdx > 0 ) {
						nextSrc = nextSrc.replace('next-off.gif', 'next-on.gif');
					} else {
						nextSrc = nextSrc.replace('next-on.gif', 'next-off.gif');
					}
				} else if( DS_prod_list_nowIdx == lastIdx ) {
					prevSrc = prevSrc.replace('prev-off.gif', 'prev-on.gif');
					nextSrc = nextSrc.replace('next-on.gif', 'next-off.gif');
				} else {
					if( DS_prod_list_nowIdx > 0 ) {
						prevSrc = prevSrc.replace('prev-off.gif', 'prev-on.gif');
					}

					if( DS_prod_list_prodCnt > DS_prod_list_nowIdx ) {
						nextSrc = nextSrc.replace('next-off.gif', 'next-on.gif');
					}
				}

				jQuery( obj ).find('>p:eq(0) img').attr('src', prevSrc);
				jQuery( obj ).find('>p:eq(1) img').attr('src', nextSrc);

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

				var prevBtnSrc = jQuery( rootObj ).find('.q_prod_list>p:eq(0) img').attr('src'); // 다음보기 버튼

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
		}
	}
}

var DS_quickStartTop = 0;
function DS_quickMenu( obj ) {
	if( jQuery('#con_wrap').length && jQuery('#main_contents').length ) {
		var quickWidth = parseInt( jQuery( obj ).width(), 10 );

		var locType = '';
		if( jQuery( obj ).parent().attr('id') == 'con_wrap' ) {
			locType = 'in';
		} else {
			locType = 'out';
		}

		var quickInit = function( thisObj, act ) {
			if( locType == 'in' ) {
				var contentWidth = parseInt( jQuery('#con_wrap').width(), 10 );
				var mainWidth = parseInt( jQuery('#main_contents').width(), 10 );
				var tmpWidth = contentWidth - mainWidth;
				var quickMarginLeft = tmpWidth - quickWidth;

				var objLeft = mainWidth + quickMarginLeft;
			} else if( locType == 'out' ) {
				var bodyWidth = parseInt( jQuery('html').width(), 10 );
				var mainWidth = parseInt( jQuery('#con_wrap').width(), 10 );
				var contentWidth = parseInt( jQuery('#con_area').width(), 10 );

				var objLeft = (bodyWidth - mainWidth) / 2;
				objLeft = contentWidth + objLeft;

				var tmpWidth = mainWidth - contentWidth;
				var quickMarginLeft = tmpWidth - quickWidth;
				objLeft = objLeft + quickMarginLeft;
			}

			var objTop = jQuery( obj ).css('margin-top');
			objTop = parseInt( objTop.replace('px'), 10 );
			DS_quickStartTop = objTop;


				jQuery( obj ).css({
					'margin-top'	: '0px',
					'top'			: objTop + 'px',
					'left'			: objLeft + 'px',
					'position'		: 'absolute',
					'z-index'		: '3000'
				});

			contentWidth=null; mainWidth=null; quickWidth=null; tmpWidth=null; quickMarginLeft=null;
			objLeft=null; objTop=null;
		};

		quickInit( obj );

		var contentHeight = parseInt( jQuery('#con_wrap').height(), 10 );
		var objHeight = parseInt( jQuery( obj ).height(), 10 );
		var scrollLimit = contentHeight - objHeight;

		var quickMove = function() {
			if( jQuery(window).scrollTop() > DS_quickStartTop ) {
				if( scrollLimit > jQuery(window).scrollTop() ) {
					jQuery( obj ).stop();
					jQuery( obj ).animate({'top':jQuery(window).scrollTop()}, 500 );
				}
			} else {
				var nowTop = jQuery( obj ).css('top');
				nowTop = parseInt( nowTop.replace('px'), 10 );

				if( nowTop != DS_quickStartTop ) {
					jQuery( obj ).stop();
					jQuery( obj ).animate({'top':DS_quickStartTop}, 500 );
				}
				nowTop=null;
			}
		};

		jQuery(window).scroll(function() {
			quickMove();
		});
	}
}

jQuery(document).ready(function() {
	if( jQuery('.sc_area').length ) {
		DS_spread_topSearch();
	}

	if( jQuery('#gnb').length ) {
		DS_spread_gnb( jQuery('#gnb') );
	}

	if( jQuery('.prod_bx').length ) {
		DS_prod_list();
	}

	if( jQuery('.top_notice').length ) {
		rank_roll = new DS_rolling({rollId: "n_list"});
	}

	if( jQuery('.question').length ) {
		DS_listSpread(); // 리스트형 게시판용 펼치기
	}

	if( jQuery('#quick_area').length ) {
		DS_quickMenu( jQuery('#quick_area') );
	}
});