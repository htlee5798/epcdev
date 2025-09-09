// 운영체제 확인
function getOSInfoStr() {
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

// 별점주기 생성
var starVote = function(objId) {
	var starCnt = 5;

	// background-position 값 찾기
	var backPosLoc = function(starObj, act) {
		if( jQuery(starObj).css('background-position') ) {
			var backPos = jQuery(starObj).css('background-position');
			if( act == 'off' ) {
				if( backPos == '0px -16px' ) {
					jQuery(starObj).css('background-position', '0px 0px');
				}
			} else {
				if( backPos == '0px 0px' ) {
					jQuery(starObj).css('background-position', '0px -16px');
				}
			}
			backPos = null;
		} else {
			var backPosX = jQuery(starObj).css('background-position-x');
			var backPosY = jQuery(starObj).css('background-position-y');
			if( act == 'off' ) {
				if( backPosX == '0px' && backPosY == '-16px' ) {
					jQuery(starObj).css({
						'background-position-x':'0px',
						'background-position-y':'0px'
					});
				}
			} else {
				if( backPosX == '0px' && backPosY == '0px' ) {
					jQuery(starObj).css({
						'background-position-x':'0px',
						'background-position-y':'-16px'
					});
				}
			}
			backPosX = null; backPosY = null;
		}
		return false;
	};

	// 별점주기 클릭시 함수
	var pointSubmit = function(starPoint) {
		// 이곳에 추가 기능 개발하여 사용하세요
		//alert( '별점 ' + starPoint + ' 점 주었습니다!' );
		return false;
	};

	// mouse over, out 시 별 아이콘 변경
	var starChange = function(starObj, act, idx){
		var i; var selectCnt = 0;
		if( act == 'leave' ) {
			selectCnt = parseInt( jQuery(starObj).find('.starResult').html(), 10 );
		} else {
			selectCnt = parseInt( idx, 10 );
		}

		for( i=0; i < selectCnt; i++ ) {
			backPosLoc( jQuery(starObj).find('.star:eq(' + i + ')') );
		} // for End

		for( i=selectCnt; i < starCnt; i++ ) {
			backPosLoc( jQuery(starObj).find('.star:eq(' + i + ')'), 'off' );
		} // for End
		i = null; selectCnt = null;
		return false;
	};

	// mouse over, out, click 기능 정의
	var starEffect = function(starObj) {
		jQuery(starObj).find('.star').bind('mouseover', function(){
			var idx = jQuery(this).attr('index');
			starChange(starObj, 'over', idx);
			idx = null;
		});

		jQuery(starObj).bind('mouseleave', function(){
			var starPoint = jQuery(starObj).find('.starResult').html();
			starChange(starObj, 'leave');
			starPoint = null;
		});

		jQuery(starObj).find('.star').bind('click', function(){
			var starPoint = jQuery(this).attr('index');
			jQuery(starObj).find('.starResult').html( starPoint );
			pointSubmit(starPoint);
			starPoint = null;
		});
		return false;
	};

	this.init = function() {
		var element = jQuery('#' + objId);
		var str = '';
		if( jQuery(element).length ) {
			// 별모양 아이콘 5개 생성
			for( var i=1; i <= starCnt; i++ ) {
				str+= "<span class='star' index='" + i + "'></span>";
			} // for End
			str+= "<span class='starResult' id='starResult' name='starResult' style='display:none;'>0</span>";
			jQuery(element).html(str);
			jQuery(element).css({
				'height': jQuery(element).find('.star').height() + 'px'
			});
			starEffect(element);
		}
		element = null; str = null;
		return false;
	};
};

function starFunc() { // 별점주기 DIV 생성
	if( jQuery('.star_wrap').length ) {
		jQuery('.star_wrap').each(function() {
			if( jQuery(this).attr('jsEffect') != 'Y' ) {
				jQuery(this).attr('jsEffect', 'Y');
				var thisStar = new starVote( jQuery(this).attr('id') );
				thisStar.init();
				thisStar = null;
			}
		});
	}
}

jQuery.fn.extend({
	// 상품리스트 확대보기 아이콘
	photoPreviewInit: function(){
		var className = jQuery(this).attr('class');
		if( className ) {
			var topObj = ''; var previewObj = '';
			var effectResult = '';

			// 상품리스트 이미지 mouse over, out 기능
			jQuery('.' + className).each(function(){
				if( jQuery(this).attr('jsEffect') != 'Y' ) {
					jQuery(this).attr('jsEffect', 'Y');

					topObj = jQuery(this).parent().parent();
					previewObj = jQuery(topObj).find('.flag_qv');

					effectResult = jQuery(previewObj).attr('effectResult');

					if( !effectResult || effectResult == 'N' ) {
						jQuery(previewObj).attr('effectResult', 'Y');

						jQuery(topObj).bind('mouseover', function() {
							jQuery(this).find('.flag_qv').show();
						});

						jQuery(topObj).bind('mouseout', function() {
							jQuery(this).find('.flag_qv').hide();
						});

						// 확대보기 아이콘 click 시 기능
						jQuery(previewObj).bind('click', function() {
							//간편보기url(카테고리id,상품코드,단품코드,출력여부(printing)Y,N)
							if(typeof goProductZoom != "undefined") {
								goProductZoom(
									jQuery(this).attr('cateId'),
									jQuery(this).attr('prodCd'),
									jQuery(this).attr('itemCd'),
									jQuery(this).attr('printYn')
								);
							}
						});
					}
				}
			});
		}
		return false;
	},
	imgChange: function(){ // 상품상세 이미지 mouse click 시 이미지 변경
		var className = jQuery(this).attr('class');
		if( className ) {
			var thisObj = ''; var smallObj = ''; var thisSrc = '';
			jQuery('.' + className).each(function() {
				if( jQuery(this).attr('jsEffect') != 'Y' ) {
					jQuery(this).attr('jsEffect', 'Y');
					thisObj = jQuery(this);
					smallObj = jQuery(this).find('.prod_small_pic');
					thisSrc = '';
					if( jQuery(smallObj).length ) {
						jQuery(smallObj).find('>li>a>img').css('cursor','pointer');
						jQuery(smallObj).find('>li>a>img').bind('click', function(){
							var imgObj = jQuery(this);
							jQuery(smallObj).find('>li').each(function(){

								if( jQuery(this).find('>img').get(0) == jQuery(imgObj).get(0) ) {
									jQuery(this).find('>img').css('border','1px solid #D40059');
								} else {
									jQuery(this).find('>img').css('border','0px');
								}
							});

							thisSrc = jQuery(this).attr('src');
							var tmpExt = thisSrc.split('.');
							tmpExt = tmpExt[tmpExt.length - 1];

							var localExp = new RegExp("75x75");
							var noImgExp = new RegExp("75-75");

							if ( thisSrc.match(localExp) ) {
								thisSrc = thisSrc.replace('75x75', '400x400');
							} else if( thisSrc.match(noImgExp) ) {
								thisSrc = thisSrc.replace('75-75', '400-400');
							} else {
								thisSrc = thisSrc.replace('_75', '_500');
							}
							jQuery(thisObj).find('.prod_big_pic img').attr('src', thisSrc);
						});
					}
				}
			});
		}
		return false;
	}
});

function descLayer() { // 펼침 메뉴
	if( jQuery('.shopping_chance').length ) {
		var div1 = '';
		var div2 = '';
		if( jQuery('.shopping_chance .btn_chance img').length ) {
			jQuery('.shopping_chance .btn_chance img').bind('click', function(event){
				event.stopPropagation();
				div1 = jQuery(this).parent().prev();
				div2 = jQuery(this).parent().prev().find('>div:eq(0)');

				if( jQuery(div2).css('display') == 'none' ) {
					jQuery(div1).addClass('on');
					jQuery(div2).show();
				} else {
					jQuery(div1).removeClass('on');
					jQuery(div2).hide();
				}
			});
		}

		if( jQuery('.shopping_chance .tit').length ) {
			jQuery('.shopping_chance .tit').css('cursor', 'pointer');
			jQuery('.shopping_chance .tit').bind('click', function(event){
				event.stopPropagation();
				div1 = jQuery(this).parent();
				div2 = jQuery(this).parent().find('>div:eq(0)');

				if( jQuery(div2).css('display') == 'none' ) {
					jQuery(div1).addClass('on');
					jQuery(div2).show();
				} else {
					jQuery(div1).removeClass('on');
					jQuery(div2).hide();
				}
			});
		}

		div1 = null; div2 = null;
	}
	return false;
}

var prdCmtObj;
function productComment() { // 상품평 펼치기
	if( jQuery('.tbl_review').length ) {
		// 상품평 리스트에서 제목 click시 본문 펼쳐짐, 재 click시 담힘
		var commentObj = jQuery('.tbl_review');
		var detailObj, effectObj, prevObj, tableObj;
		var border_width = '', border_color = '', border_style = '';

		jQuery(commentObj).find('.subject').each(function(){
			// 상품평 제목 객체 class='on' 으로 통일
			detailObj = jQuery(this).parent().next();

			if( jQuery(this).parent().attr('class') == 'on' ) {
				prdCmtObj = jQuery(this);
			}

			tableObj = jQuery(commentObj);
			jQuery(this).bind('click', function() {
				if( jQuery(this).get(0) != jQuery(prdCmtObj).get(0) ) {
					var thisObj = jQuery(this);
					var objName = '';

					var browserName = browserNameCheck();
					var msVersion = msCheck();

					var tdObj = '';
					jQuery(tableObj).find('.subject').each(function() {
						objName = jQuery(this).parent().get(0).tagName;
						objName = objName.toLowerCase();

						if( jQuery( thisObj ).get(0) == jQuery(this).get(0) ) {
							if( objName == 'tr' ) {
								jQuery(this).parent().addClass('on');
								jQuery(this).parent().next().show();
							} else {
								jQuery(this).parent().parent().addClass('on');
								jQuery(this).parent().parent().next().show();
							}
						} else {
							if( objName == 'tr' ) {
								jQuery(this).parent().removeClass('on');
								jQuery(this).parent().next().hide();
							} else {
								jQuery(this).parent().parent().removeClass('on');
								jQuery(this).parent().parent().next().hide();
							}
						}
					});
					prdCmtObj = jQuery(this);
				} else {
					return false;
				}
			});
		});

		commentObj = null; detailObj = null; effectObj = null;
		return false;
	}
}

var listViewObj;
function listSpread() { // 리스트형 게시판용 펼치기
	// 명명규칙
	// 타이틀 영역	class = question
	// 본문 영역	class = view
	// 상위객체 li 에 class=on 유무에 따라 본문영역 show, hide
	if( jQuery('.question').length ) {
		jQuery('.question').css('cursor', 'pointer');

		jQuery('.question').each(function() {
			var parentObj = jQuery(this).parent().parent();
			if( jQuery(parentObj).attr('class') == 'on' ) {
				listViewObj = jQuery(parentObj);
			}
		});

		jQuery('.question').bind('click', function() {
			var parentObj = jQuery(this).parent().parent();
			var viewObj = jQuery(this).parent().next();

			if( listViewObj ) {
				if( jQuery(listViewObj).get(0) != jQuery(parentObj).get(0) ) {
					jQuery(listViewObj).removeClass('on');
				}
			}

			if( jQuery(viewObj).css('display') == 'none' ) {
				jQuery(parentObj).addClass('on');
			} else {
				jQuery(parentObj).removeClass('on');
			}

			listViewObj = jQuery(parentObj);
		});
	}
}

function product_upDown() { // 상품 구매 갯수 변경
	if( jQuery('.prod_count_wrap').length ) {
		var plusNumber = 0; var minusNumber = 0; var thisNumber = 0;
		var inputObj;

		jQuery('.prod_count_wrap').each(function() {
			jQuery(this).find('.btn_plus>img').bind('click', function(){
				plusNumber = parseInt( jQuery(this).attr('alt'), 10 );
				inputObj = jQuery(this).parent().prev().find('input');
				if( jQuery(inputObj).length ) {
					thisNumber = parseInt( jQuery(inputObj).val(), 10 );
					jQuery(inputObj).val( thisNumber + plusNumber );
				}
			});

			jQuery(this).find('.btn_minus>img').bind('click', function(){
				minusNumber = parseInt( jQuery(this).attr('alt'), 10 );
				inputObj = jQuery(this).parent().prev().prev().find('input');
				if( jQuery(inputObj).length ) {
					thisNumber = parseInt( jQuery(inputObj).val(), 10 );
					if( thisNumber > 1 ) {
						jQuery(inputObj).val( thisNumber + minusNumber );
					}
				}
			});
		});
	}
}

var osHeight = 0;
function option_search() {
	if( jQuery('.option_search').length ) {
		jQuery('.option_search').find('.btn').bind('click', function() {
			var classRegExp = new RegExp('close');
			var classText = jQuery(this).attr('class');
			var btnStatus = 'open';
			var ulParent;

			var ulObj = jQuery(this).parent().next().find('>ul');
			ulParent = jQuery(ulObj).parent();
			if( osHeight < 1 ) {
				osHeight = jQuery(ulParent).height();
			}

			var imgSrc = jQuery(this).find('>a>img').attr('src');

			if( classRegExp.test(classText) ) {
				jQuery(ulParent).css('height', osHeight);
				jQuery(this).removeClass('close');

				imgSrc = imgSrc.replace('close', 'open');
				jQuery(this).find('>a>img').attr('src', imgSrc);
			} else {
				var orgHeight = parseInt( jQuery(ulObj).height(), 10 );
				if( !jQuery(this).attr('ref') ) {
					jQuery(this).attr('ref', orgHeight);
				}

				var liObj = jQuery(ulObj).find('>li:eq(0)');
				var liHeight		= parseInt( jQuery(liObj).height(), 10 );
				var paddingTop		= parseInt( jQuery(liObj).css('padding-top'), 10 );
				var paddingBottom	= parseInt( jQuery(liObj).css('padding-bottom'), 10 );

				var firstHeight = liHeight + paddingTop + paddingBottom;

				jQuery( ulParent ).css('height', firstHeight + 'px');
				jQuery(this).addClass('close');

				imgSrc = imgSrc.replace('open', 'close');
				jQuery(this).find('>a>img').attr('src', imgSrc);
			}
		});
	}
}

function convertToiframe() { // 상품상세, 레시피 태그 iframe 으로 삽입
	if( jQuery('.convertIframe').length ) {
		var productHtml=null, iframeObj=null, iframeDoc=null, iframeHeight=0;

		jQuery('.convertIframe').each(function() {
			productHtml = jQuery(this).val();
			jQuery(this).hide();
			jQuery('<iframe>').attr({
				"name"			: "prod_iframe",
				"src"			: "about:blank",
				"scrolling"		: "no",
				"frameborder"	: "0"
			}).css({'width':'100%','height':'0px'}).insertBefore( this );

			iframeObj = jQuery(this).prev();
			iframeDoc = jQuery( iframeObj ).get(0).contentWindow;
			iframeDoc.document.write( productHtml );
			iframeDoc.document.close();

			if( jQuery( iframeObj ).contents().find('a').length ) {
				jQuery( iframeObj ).contents().find('a').each(function() {
					jQuery(this).attr('target', '_parent');
				});
			}

			if (iframeDoc.contentDocument && iframeDoc.contentDocument.body.offsetHeight) {
				// W3C DOM (and Mozilla) syntax
				iframeHeight = iframeDoc.contentDocument.body.offsetHeight;
			} else if (iframeDoc.document && iframeDoc.document.body.scrollHeight) {
				// IE DOM syntax
				iframeHeight = iframeDoc.document.body.scrollHeight;
			}
			jQuery( iframeObj ).css('height', iframeHeight+'px');
			jQuery(this).remove();
		});

		productHtml=null, iframeObj=null, iframeDoc=null, iframeHeight=null;
	}
}

jQuery(document).ready(function(){
	descLayer(); // 펼침 메뉴

	// 상품이미지 mouseover 시 확대보기 아이콘
	if( jQuery('.photo').length ) {
		jQuery('.photo').photoPreviewInit();
	}
});