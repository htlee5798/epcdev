// 배너 롤링 스크립트 ( 가로 )
function FUNC_rolling_horizon( obj, objCnt ) {
	if( !objCnt ) { objCnt = 4; }
	var i=0, objMaxCnt = 30;

	var thisSetTime;

	var slideSpeed = 500, txtSlideSpeed = 500, btnSetTime = 1000, autoRollTime = 2000;

	var divObj = '#' + obj + 'Div';
	var divCnt = 0;
	var divWidth = 0;

	var txtObj = '#' + obj + 'Txt';
	var txtCnt = 0;

	var prevObjIdx = 0;

	var contentSlide = function( idx ) {
		for( var i=1; i <= divCnt; i++ ) {
			if( i == idx ) {
				prevObjIdx = prevObjIdx ? prevObjIdx : 1;

				if( prevObjIdx != i ) {
					if( jQuery(divObj + i).css('display') == 'none' ) {
						jQuery(divObj + i).show();
					}

					jQuery(divObj + prevObjIdx).queue('fx',[]).stop();
					jQuery(divObj + i).queue('fx',[]).stop();

					var thisLeft = jQuery(divObj + prevObjIdx).css('left');
					thisLeft = parseInt( thisLeft.replace('px'), 10 );

					if( prevObjIdx < i ) {
						thisLeft = thisLeft + divWidth;

						jQuery(divObj + i).css('left', thisLeft + 'px');

						jQuery(divObj + prevObjIdx).animate({
							left : '-' + divWidth
						}, slideSpeed);

						jQuery(divObj + i).animate({
							left : 0
						}, slideSpeed);
					} else {
						thisLeft = divWidth - thisLeft;

						jQuery(divObj + i).css('left', '-' + thisLeft + 'px');

						jQuery(divObj + prevObjIdx).animate({
							left : divWidth
						}, slideSpeed);

						jQuery(divObj + i).animate({
							left : 0
						}, slideSpeed);
					}

					prevObjIdx = i;

					thisLeft=null;
				}
			}
		} // for End
	};

	var txtDivInit = function( idx ) {
		for( var i=1; i <= txtCnt; i++ ) {
			if( idx != i ) {
				jQuery(txtObj + i).removeClass('on');
			}
		} // for End
	}

	// 스테이지 영역 초기화
	for(i=1; i < objMaxCnt; i++) {
		if( jQuery(divObj + i).length ) {
			if( divWidth == 0 ) {
				divWidth = parseInt( jQuery(divObj + i).width(), 10 );
			}

			divCnt++;

			jQuery(divObj + i).bind('mouseover', function() {
				clearTimeout( thisSetTime );
			});

			jQuery(divObj + i).bind('mouseleave', function() {
				thisSetTime = setTimeout(autoRoll, autoRollTime);
			});

			jQuery(divObj + i).css({
				'position' : 'absolute',
				'top' : '0px',
				'left' : '0px'
			});
			jQuery(divObj + i).attr('index', i);

			if( objCnt < i ) {
				jQuery(divObj + i).hide();
			}
		} else {
			break;
		}
	} // for End

	var txtWidth=0, tmpWidth=0;
	var nowIdx = 1, txtIdx = 1;
	var txtObjectResult = 'N';

	// 버튼 영역 초기화
	for(i=1; i < objMaxCnt; i++) {
		if( jQuery(txtObj + i).length ) {
			// 버튼 영역의 width 구하기
			if( txtWidth == 0 ) {
				var txtMargin = jQuery(txtObj + i).css('margin-right');
				txtMargin = parseInt( txtMargin.replace('px', ''), 10 );
				txtWidth = parseInt( jQuery(txtObj + i).width(), 10 );;
				txtWidth = txtWidth + txtMargin;
				txtMargin=null;
			}

			tmpWidth = txtWidth * ( i - 1 );

			jQuery(txtObj + i).css({
				'position' : 'absolute',
				'top' :'0px',
				'left' : tmpWidth + 'px'
			});

			if( i == 1 ) {
				jQuery(txtObj + i).addClass('on');
			}

			jQuery(txtObj + i).bind('mouseover', function() {
				clearTimeout( thisSetTime );

				txtDivInit( parseInt( jQuery(txtObj + i).attr('index'), 10 ) );
				jQuery(this).addClass('on');
				contentSlide( parseInt( jQuery(this).attr('index'), 10 ) );

				nowIdx = parseInt( jQuery(this).attr('index'), 10 );
			});

			jQuery(txtObj + i).bind('mouseleave', function() {
				thisSetTime = setTimeout(autoRoll, autoRollTime);
			});

			jQuery(txtObj + i).attr('index', i);

			if( objCnt < i ) {
				jQuery(txtObj + i).hide();
			}
			txtCnt++;
		} else {
			break;
		}
	} // for End

	// LEFT 버튼
	if( jQuery('#' + obj + 'Left').length ) {
		txtObjectResult = 'Y';
		jQuery('#' + obj + 'Left').attr('clickDeny', 'N');

		jQuery('#' + obj + 'Left').bind('mouseover', function() {
			jQuery('#clearResult').html('Y');
			clearTimeout( thisSetTime );

			var imgSrc = jQuery(this).attr('src');
			var tmpSrc = imgSrc.split('.');
			var tmpCnt = parseInt( tmpSrc.length, 10 ) - 1;
			var imgExt = tmpSrc[tmpCnt]; // 이미지 파일 확장자

			imgSrc = imgSrc.replace('-off.' + imgExt, '-on.' + imgExt);
			jQuery(this).attr('src', imgSrc);
			imgSrc=null; tmpSrc=null; tmpCnt=null; imgExt=null;
		});

		jQuery('#' + obj + 'Left').bind('mouseleave', function() {
			jQuery('#clearResult').html('N');
			thisSetTime = setTimeout(autoRoll, autoRollTime);

			var imgSrc = jQuery(this).attr('src');
			var tmpSrc = imgSrc.split('.');
			var tmpCnt = parseInt( tmpSrc.length, 10 ) - 1;
			var imgExt = tmpSrc[tmpCnt]; // 이미지 파일 확장자

			imgSrc = imgSrc.replace('-on.' + imgExt, '-off.' + imgExt);
			jQuery(this).attr('src', imgSrc);
			imgSrc=null; tmpSrc=null; tmpCnt=null; imgExt=null;
		});

		jQuery('#' + obj + 'Left').bind('click', function() {
			var leftClick = jQuery(this).attr('clickDeny');
			if( leftClick != 'Y' ) {
				jQuery(this).attr('clickDeny', 'Y');

				var thisAct = '';

				txtIdx = txtIdx - 1;
				if( txtIdx < 1 ) {
					txtIdx = txtCnt - (objCnt - 1);
					thisAct = 'left';
				}

				txtSlide( txtIdx, thisAct );
				setTimeout(function() {
					jQuery('#' + obj + 'Left').attr('clickDeny', 'N');
				}, btnSetTime);

				thisAct=null;
			}
			leftClick=null;
		});
	}

	// RIGHT 버튼
	if( jQuery('#' + obj + 'Right').length ) {
		txtObjectResult = 'Y';
		jQuery('#' + obj + 'Right').attr('clickDeny', 'N');

		jQuery('#' + obj + 'Right').bind('mouseover', function() {
			jQuery('#clearResult').html('Y');
			clearTimeout( thisSetTime );

			var imgSrc = jQuery(this).attr('src');
			var tmpSrc = imgSrc.split('.');
			var tmpCnt = parseInt( tmpSrc.length, 10 ) - 1;
			var imgExt = tmpSrc[tmpCnt]; // 이미지 파일 확장자

			imgSrc = imgSrc.replace('-off.' + imgExt, '-on.' + imgExt);
			jQuery(this).attr('src', imgSrc);
			imgSrc=null; tmpSrc=null; tmpCnt=null; imgExt=null;
		});

		jQuery('#' + obj + 'Right').bind('mouseleave', function() {
			jQuery('#clearResult').html('N');
			thisSetTime = setTimeout(autoRoll, autoRollTime);

			var imgSrc = jQuery(this).attr('src');
			var tmpSrc = imgSrc.split('.');
			var tmpCnt = parseInt( tmpSrc.length, 10 ) - 1;
			var imgExt = tmpSrc[tmpCnt]; // 이미지 파일 확장자

			imgSrc = imgSrc.replace('-on.' + imgExt, '-off.' + imgExt);
			jQuery(this).attr('src', imgSrc);
			imgSrc=null; tmpSrc=null; tmpCnt=null; imgExt=null;
		});

		jQuery('#' + obj + 'Right').bind('click', function() {
			clearTimeout( thisSetTime );

			var rightClick = jQuery(this).attr('clickDeny');
			if( rightClick != 'Y' ) {
				jQuery(this).attr('clickDeny', 'Y');

				var thisAct = '';

				txtIdx = txtIdx + 1;
				if( txtIdx > ( txtCnt - (objCnt - 1) ) ) {
					txtIdx = 1;
					thisAct = 'right';
				}

				txtSlide( txtIdx, thisAct );
				setTimeout(function() {
					jQuery('#' + obj + 'Right').attr('clickDeny', 'N');
				}, btnSetTime);

				thisAct=null;
			}
			rightClick=null;
		});
	}

	var txtSlide = function(firstIdx, act) {
		var txtLeft = 0;
		for( var i=1; i<=txtCnt; i++ ) {
			jQuery(txtObj + i).clearQueue().stop();

			if( act ) {
				if( act == 'left' ) {
					firstIdx = txtCnt - 3;
					if( i < firstIdx ) {
						txtLeft = ( firstIdx - i ) * txtWidth;
						txtLeft = '-' + txtLeft;
					} else if( i == firstIdx ) {
						txtLeft = 0;
					} else if( i > firstIdx ) {
						txtLeft = ( i - firstIdx ) * txtWidth;
					}
				} else if( act == 'right' ) {
					txtLeft = txtWidth * ( i - 1 );
				}
			} else {
				if( i < firstIdx ) {
					txtLeft = ( firstIdx - i ) * txtWidth;
					txtLeft = '-' + txtLeft;
				} else if( i == firstIdx ) {
					txtLeft = 0;
				} else if( i > firstIdx ) {
					txtLeft = ( i - firstIdx ) * txtWidth;
				}
			}

			jQuery(txtObj + i).show().animate({left: txtLeft}, txtSlideSpeed);
		} // for End
	};

	var autoRoll = function() {
		clearTimeout( thisSetTime );
		var thisAct = '';
		nowIdx = nowIdx + 1;

		if( txtObjectResult == 'Y' ) { // 버튼 슬라이드 있을경우
			if( nowIdx < (objCnt + 1) ) {
				txtIdx = 1;
			} else {
				txtIdx = nowIdx - (objCnt - 1);
			}

			if( txtIdx > ( txtCnt - (objCnt - 1) ) ) {
				txtIdx = 1;
				thisAct = 'right';
			}

			if( txtObjectResult == 'Y' ) {
				txtSlide( txtIdx, thisAct );
			}

			if( nowIdx > txtCnt ) {
				nowIdx = 1;
			}
		} else { // 버튼 슬라이드 없을 경우
			if( nowIdx > objCnt ) {
				txtIdx = 1;
			} else {
				txtIdx = nowIdx;
			}

			if( nowIdx > objCnt ) {
				nowIdx = 1;
			}
		}

		

		txtDivInit( parseInt( jQuery('#' + obj + 'Txt' + nowIdx).attr('index'), 10 ) );
		jQuery('#' + obj + 'Txt' + nowIdx).addClass('on');
		contentSlide( parseInt( jQuery('#' + obj + 'Txt' + nowIdx).attr('index'), 10 ) );

		thisSetTime = setTimeout(autoRoll, autoRollTime);
		thisAct=null;
	};

	thisSetTime = setTimeout(autoRoll, autoRollTime);
}

// 배너 롤링 스크립트 ( 세로 )
function SUB_btnChange( obj, act ) {
	var imgSrc = jQuery( obj ).attr('src');
	if( imgSrc == undefined ) { return false; }
	var tmpSrc = imgSrc.split('.');
	var tmpCnt = parseInt( tmpSrc.length, 10 ) - 1;
	var imgExt = tmpSrc[tmpCnt]; // 이미지 파일 확장자

	if( act == 'on' ) {
		imgSrc = imgSrc.replace('-off.' + imgExt, '-on.' + imgExt);
	} else if( act == 'off' ) {
		imgSrc = imgSrc.replace('-on.' + imgExt, '-off.' + imgExt);
	}

	jQuery( obj ).attr('src', imgSrc);
	imgSrc=null; tmpSrc=null; tmpCnt=null; imgExt=null;
}

var setTimeObj = new Array();
// 탭버튼 롤링 스크립트
function FUNC_tabChange( obj, objCnt, ext ) {
	//if( !objCnt ) { objCnt = 5; }
	var setTimeCnt = setTimeObj.length;
	if( setTimeCnt > 0 ) {
		var setResult = 'N';
		for(var tmpNum=0; tmpNum < setTimeCnt; tmpNum++) {
			if( setTimeObj[tmpNum] == obj ) {
				setResult = 'Y';
				break;
			}
		} // for End

		if( setResult == 'N' ) {
			setTimeObj[setTimeObj.length] = obj;
		} else {
			return false;
		}
	} else {
		setTimeObj[setTimeObj.length] = obj;
	}

	var i=0, objMaxCnt = 30;

	var thisSetTime;

	var btnSetTime = 3000;

	var imgObj = '#' + obj + 'Img';
	var imgCnt = 0;

	var divObj = '#' + obj + 'Div';
	var divCnt = 0;

	var prevObjIdx = 1;
	var duplicateCheck = 'N';
	
	jQuery(document).ajaxStart(function(){
		prevObjIdx = 1;
		clearTimeout( thisSetTime );
		for( var tmpI=1; tmpI<=imgCnt; tmpI++) {
			if( tmpI == prevObjIdx ) {
				SUB_btnChange( jQuery(imgObj + tmpI), 'on' );
			} else {
				SUB_btnChange( jQuery(imgObj + tmpI), 'off' );
			}
		} // for End
		thisSetTime = setTimeout(function() { auto_tabChange(); }, btnSetTime);
	});

	var auto_tabChange = function() {
		jQuery(obj).clearQueue().stop();
		clearTimeout( thisSetTime );
		
		for( var tmpI=1; tmpI<=imgCnt; tmpI++) {
			SUB_btnChange( jQuery(imgObj + tmpI), 'off' );
		} // for End

		SUB_btnChange( jQuery(imgObj + prevObjIdx), 'off' );
		jQuery(divObj + prevObjIdx).hide();

		prevObjIdx = prevObjIdx + 1;
		prevObjIdx = prevObjIdx > imgCnt ? 1 : prevObjIdx;

		SUB_btnChange( jQuery(imgObj + prevObjIdx), 'on' );
		jQuery(divObj + prevObjIdx).show();

		thisSetTime = setTimeout(function() { auto_tabChange(); }, btnSetTime);
	};

	// 탭버튼 Count
	for(i=1; i < objMaxCnt; i++) {
		if( jQuery(imgObj + i).length ) {
			// 페이지 최초 로딩시 첫번째 탭 활성화
			if( i == 1 ) {
				SUB_btnChange( jQuery(imgObj + i), 'on' );
				jQuery(divObj + i).addClass('on');
			}
			imgCnt++;
		} else {
			break;
		}
	} // for End

	for(i=1; i < imgCnt + 1; i++ ) {
		jQuery(imgObj + i).bind('mouseover', function() {
			clearTimeout( thisSetTime );

			prevObjIdx = parseInt( jQuery(this).attr('index'), 10 );

			// 마우스 오버시 해당 객체 제외하고 비활성화
			for( var j=1; j < imgCnt + 1; j++ ) {
				if( jQuery(imgObj + j).length ) {
					if( jQuery(imgObj + j).get(0) == jQuery(this).get(0) ) {
						SUB_btnChange( jQuery(imgObj + j), 'on' );
						jQuery(divObj + j).show();
					} else {
						SUB_btnChange( jQuery(imgObj + j), 'off' );
						jQuery(divObj + j).hide();
					}
				}
			} // for End
		});

		jQuery(imgObj + i).bind('mouseleave', function() {
			thisSetTime = setTimeout(function() { auto_tabChange(); }, btnSetTime);
		});

		jQuery(imgObj + i).attr('index', i);
		if( objCnt ) {
			if( objCnt < i ) {
				jQuery(imgObj + i).hide();
			}
		}
	} // for End

	// 스테이지 Count
	for(i=1; i < objMaxCnt; i++) {
		if( jQuery(divObj + i).length ) {
			jQuery(divObj + i).attr('index', i);
			if( objCnt ) {
				if( objCnt < i ) {
					jQuery(divObj + i).hide();
				}
			}

			jQuery(divObj + i).bind('mouseover', function() {
				clearTimeout( thisSetTime );
			});

			jQuery(divObj + i).bind('mouseleave', function() {
				thisSetTime = setTimeout(function() { auto_tabChange(); }, btnSetTime);
			});

			divCnt++;
		} else {
			break;
		}
	} // for End

	thisSetTime = setTimeout(function() { auto_tabChange(); }, btnSetTime);
}


//메인페이지 content slide 기능
var prevContentIdx=0, mainContentCnt = 0, mainRollObj = null;
function slideMainContent( obj, rollSpeed, setDealy ) {
	if( jQuery(obj).length ) {
		var nowIdx = 0;
		var nowObj, prevObj;

		var contentWidth = 0;
		var slideResult;

		if( !mainContentCnt ) {
			mainContentCnt = parseInt( jQuery(obj).find('>ul>li').length, 10 );
		}

		if( jQuery(obj).find('>ul>li').length ) {
			jQuery(obj).find('>ul>li').each(function() {
				if( jQuery(this).find('img').length ) {
					jQuery(this).find('img').css('cursor', 'pointer');
				}
			});
		}

		jQuery('#mainContent').find('>div').hide();
		jQuery('#mainContent').find('>div:eq(0)').show();

		var autoRoll = function() {
			//alert( idx );
			var tmpIdx = prevContentIdx + 1;
			if( tmpIdx > mainContentCnt - 1 ) {
				tmpIdx = 0;
			}
			jQuery(obj).find('>ul>li:eq(' + tmpIdx + ')').trigger('click');
			mainRollObj = setTimeout(function() { autoRoll(); }, setDealy);
		};

		jQuery(obj).find('>ul>li').bind('click', function() {
			nowObj = jQuery('#mainContent').find('>div:eq(' + jQuery(this).index() + ')');
			prevObj = jQuery('#mainContent').find('>div:eq(' + prevContentIdx + ')');

			if( !jQuery(nowObj).length ) { return false; }
			if( !jQuery(prevObj).length ) { return false; }

			if( prevContentIdx != jQuery(this).index() ) {
				if( jQuery( prevObj ).css('left') != '0px' ) {
					return false;
				}

				jQuery(obj).find('>ul>li').each(function() {
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

		if( jQuery(obj).find('.prev').length ) {
			if( jQuery(obj).find('.prev').find('img').length ) {
				jQuery(obj).find('.prev').find('img').css('cursor', 'pointer');
			}

			jQuery(obj).find('.prev').bind('click', function() {
				var tmpIdx = prevContentIdx - 1;
				if( tmpIdx < 0 ) {
					tmpIdx = mainContentCnt - 1;
				}
				jQuery(obj).find('>ul>li:eq(' + tmpIdx + ')').trigger('click');
			});
		}

		if( jQuery(obj).find('.next').length ) {
			if( jQuery(obj).find('.next').find('img').length ) {
				jQuery(obj).find('.next').find('img').css('cursor', 'pointer');
			}

			jQuery(obj).find('.next').bind('click', function() {
				var tmpIdx = prevContentIdx + 1;
				if( tmpIdx > mainContentCnt - 1 ) {
					tmpIdx = 0;
				}
				jQuery(obj).find('>ul>li:eq(' + tmpIdx + ')').trigger('click');
			});
		}

		jQuery(obj).parent().bind('mouseover', function() {
			clearTimeout( mainRollObj );
		});

		jQuery(obj).parent().bind('mouseleave', function() {
			mainRollObj = setTimeout(function() { autoRoll(); }, setDealy);
		});

		mainRollObj = setTimeout(function() { autoRoll(); }, setDealy);
	}
}

//메인페이지 content btn on, off
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
try {
	  document.execCommand("BackgroundImageCache", false, true);
	} catch(err) {}
