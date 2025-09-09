var _com_ajax_modal_j$Object;        
var _com_ajax_async_mode_j$Object = true;
var _com_ajax_global_start = false;
var _com_ajax_global_complete = true;
var _pop_prj_info_forms_ ;
var _pop_prj_info_modal_ ;
var _modal_opts_;
var _BASE_JS_ = "";

/**
 * 로딩중 이미지 셋팅
 * @param {Object} "body"
 */
$(document).ready(function() {
    
	//ajax 관련 사전작업
	if($("body").has("#h_area").html() == null){
		$("body").prepend("<div id=h_area></div>");
	}

	if($("body").has("#h_loading").html() == null){
        var tmpHtml = "";
        tmpHtml  = "<div id='h_loading' style='position: fixed; top: 0; left: 0; display:none;z-index:9999'>"; 
        tmpHtml += "<table width=200 height=100 border=0> ";
        tmpHtml += "    <tr>";
//        tmpHtml += "        <td class='h_load_ind' align='center'><img id='ajax_load_type_img' src='"+ _BASE_JS_ +"/js/jquery/res/loading2.gif' width='130' height='130'/></td>";
        tmpHtml += "        <td class='h_load_ind' align='center'><img id='ajax_load_type_img' src='"+ _LMCdnStaticUrl +"/lm2/images/layout/loadinglogo.gif' alt='롯데마트Mall' /></td>";
        tmpHtml += "    </tr>";
        tmpHtml += "</table>";
        tmpHtml += "</div>";

        $(tmpHtml)
         .prependTo("#h_area");
        
        $(document)
        .ajaxStart(function() {
        	_com_ajax_global_complete = false;
        })
        .ajaxStop(fn$ajax_globalStop);
    }
});

var fn$ajax_globalStart_old = function() {
	 var $this = $("#h_loading");
     if(_com_ajax_async_mode_j$Object == false) {
    	 _com_ajax_global_start = true;
    	 $($this).find("td.h_load_ind").html("<img id='ajax_load_type_img' src='"+ _BASE_JS_ +"/js/jquery/res/loading2.gif' width='130' height='130'/>"); 
         var opts = {
                    name : '#h_loading',
                    zIndex : 9998 ,
                    center : true,
                    backgroundDisplay :true,
                    fade   : true 
               };
            
         _com_ajax_modal_j$Object = $.yakuModal(opts);
         $($this).show();
     }
};

var fn$ajax_globalStart = function(forceBlock) {
    if(forceBlock || _com_ajax_async_mode_j$Object == false) {
    	if( $.fn.loadingBar ) {
    		$( 'body' ).loadingBar();
    	}
//    	try {
//			$.blockUI({
//				message: $("#ajax_load_type_img"),
//				css: {
//					border: 'none',
////					top: ($(window).height() - 130) /2 + 'px',
////					left: ($(window).width() - 130) /2 + 'px', 
////		            width: '130px',
//					top: ($(window).height() - 94) /2 + 'px',
//					left: ($(window).width() - 196) /2 + 'px', 
//					width: '196px',
//		            backgroundColor: 'rgba(255, 255, 255, 0)'
//				},
//				overlayCSS: {
////					backgroundColor: '#000', 
//					backgroundColor: '#FFF',
//			        opacity:         .75
//				}
//			});
//    	}
//    	catch ( e ) {}
    }
};

var fn$ajax_globalStop = function() {
	_com_ajax_global_complete = true;
	
	if( _com_ajax_modal_j$Object ) {
		_com_ajax_modal_j$Object.close();
		_com_ajax_modal_j$Object = null;
	}
	_com_ajax_global_start = false;
	if( $.fn.loadingBar ) {
		$( 'body' ).loadingBar( false );
	}
	
//	try {
//		$.unblockUI();
//		try{
//			_com_ajax_modal_j$Object.close();
//			_com_ajax_modal_j$Object = null;
//			_com_ajax_global_start = false;
//		}catch(e){}
//	}
//	catch ( e ) {
//		try {
//			// 이전 방식...
//			var $this = $("#h_loading");
//			try{
//				_com_ajax_modal_j$Object.close();
//				_com_ajax_modal_j$Object = null;
//				_com_ajax_global_start = false;
//			}catch(e){}
//			$($this).find("td.h_load_ind").empty();
//		    $($this).hide();
//		}
//		catch ( e ) {}
//	}
};

/**
 * 공통 ajax 호출함수
 * callBack 함수는 콜함수의 앞자리4개를 제외하고 접두어를 통일한다.
 * @param {string} in_url
 * @param {JSON} in_send_json
 * @param {string} in_call_back
 * @param {boolean} cache_type
 * @param {boolean} async_mode
 */
function fn$ajax(in_url, in_send_json, in_call_function, cache_type, async_mode, method){
    if( cache_type == null || typeof(cache_type) === "undefined"){
    	cache_type = true;
    }

    if( async_mode == null || typeof(async_mode) === "undefined"){
    	async_mode = true;
    }
    
    if( method == null || typeof(method) === "undefined"){
    	method = "POST";
    }
    
    _com_ajax_async_mode_j$Object = async_mode;

    var call_back_ = eval("callBack_$" + in_call_function);
    
    if(location.href.indexOf("https://") > -1 && in_url.indexOf("http://") > -1){
    	in_url = in_url.replace("http://", "https://");
    }else if(location.href.indexOf("http://") > -1 && in_url.indexOf("https://") > -1){
    	in_url = in_url.replace("https://", "http://");
    }
    
    $.ajax({
        type       : method ,
        url        : in_url ,
        data       : in_send_json,
        context	   : document.body,
        async      : async_mode ,
        dataType   : "text" ,
        timeOut    : (9 * 1000) ,
		cache      : cache_type
    })
    .done(call_back_)
    .fail(callSysErr)
    ;
}

/**
 * 공통 ajax 호출 에러표시
 * @param {Object} XMLHttpRequest
 * @param {Object} textStatus
 * @param {Object} errorThrown
 */
function callSysErr(XMLHttpRequest, textStatus, errorThrown){
	if(XMLHttpRequest.status != 200){
		
		var errMsg = "서버에서 데이터를 처리하는중 다음과 같은 오류가 발생하였습니다"+"\n"
					  +XMLHttpRequest.status + "\n" + XMLHttpRequest.statusText+"\n"
					  +"페이지를 새로고침해보시고 계속 오류가 발생하면 관리자에게 문의바랍니다";
		//alert(errMsg);
	}
}

/**
 * 호출한 스크립트의 이름을 가져온다.
 * @param {Object} caller
 */
function fnNmGetter(caller) {
    var f = arguments.callee.caller;
    if(caller) f = f.caller;
    var pat = /^function\s+([a-zA-Z0-9_]+)\s*\(/i;
    pat.exec(f);
    var func = new Object();
    func.name = RegExp.$1;
    return func;
} 

/**
 * form에 있는 객체를 JSON으로 변환
 * @param {Object} selector
 */
function formToJSON( selector ){
    var form = {};
    $(selector).find(':input[name]:enabled').each( function() {
        var self = $(this);
        var name = self.attr('name');
        if (form[name]) {
            form[name] = form[name] + ',' + self.val();
        }else{
            form[name] = self.val();
        }
    });

  return form;
}

/**
 * iif
 */
function iif(compare, setValue, elseValue){
	if(compare){
		return setValue;
	}else{
		return elseValue;
	}
}

/**
 * 3자리마다 콤마
 * @param {Object} srcNumber
 */
function commaSplit(srcNumber) { 
	var txtNumber = '' + srcNumber; 
	var rxSplit = new RegExp('([0-9])([0-9][0-9][0-9][,.])'); 
	var arrNumber = txtNumber.split('.'); 
	
	arrNumber[0] += '.'; 
	
	do { 
	   arrNumber[0] = arrNumber[0].replace(rxSplit, '$1,$2'); 
	}while (rxSplit.test(arrNumber[0])); 
	
	if (arrNumber.length > 1) { 
	   return arrNumber.join(''); 
	}else { 
	   return arrNumber[0].split('.')[0]; 
    } 
} 

/**
 * 구간별 일수
 * @param startDate
 * @param endDate
 * @param pType
 * @return
 */
function fnDateDiff(startDate, endDate, pType){
	//param : pStartDate - 시작일
	//param : pEndDate  - 마지막일
	//param : pType       - 'D':일수, 'M':개월수
	
	if(startDate == null || endDate == null){
		return 0;
	}
	
	var pStartDate = startDate.replace(/\-/g, "");
	pStartDate = pStartDate.replace(/\./g, "");
	
	var pEndDate = endDate.replace(/\-/g, "");
	pEndDate = pEndDate.replace(/\./g, "");

	var strSDT = new Date(pStartDate.substring(0,4),pStartDate.substring(4,6)-1,pStartDate.substring(6,8));
	var strEDT = new Date(pEndDate.substring(0,4),pEndDate.substring(4,6)-1,pEndDate.substring(6,8));
	var strGapDT = 0;

	if(pType == 'D') {  //일수 차이
	    strGapDT = (strEDT.getTime()-strSDT.getTime())/(1000*60*60*24);
	} else {            //개월수 차이
	    //년도가 같으면 단순히 월을 마이너스 한다.
	    // => 20090301-20090201 의 경우 아래 else의 로직으로는 정상적인 1이 리턴되지 않는다.
	    if(pEndDate.substring(0,4) == pStartDate.substring(0,4)) {
	        strGapDT = pEndDate.substring(4,6) * 1 - pStartDate.substring(4,6) * 1;
	    } else {
	        strGapDT = Math.floor((strEDT.getTime()-strSDT.getTime())/(1000*60*60*24*365.25/12));
	    }
	}

	return strGapDT;
}


/**
 * LPAD
 * @param inStr
 * @param totalLen
 * @param strReplace
 * @return
 */
function lpad(inStr ,totalLen, strReplace){
	
	var strAdd  = "";
	var diffLen = totalLen - inStr.length;
	
	for(var i = 0; i < diffLen; ++i){
		strAdd += strReplace;
	}
	return strAdd+inStr;
}

(function($) {
	var status = {};

	$.fn.yakuModal = function(options)
	{
		var opts = options;
		return $(this).bind('click.yakuModal', function(){
			return new $modal(opts);
		});
	};

	$.yakuModal = function(options) {
		return new $modal(options);
	};

	$.yakuModal.defaults = {
		name  : '#popupLayer' ,
		closeButton : '#close' ,
		backgroundDisplay  : true ,
		backgroundOpacity  : "0.5" ,
		backgroundColor  : "#000" ,
		center : true ,
		fade   : true ,
		speed  : 'fast' ,
		zIndex : '100'  ,
		left  : '100px' ,
		top   : '200px' ,
		scroll : 'no'
	};

	//var ie6   = $.browser.msie && parseInt($.browser.version) == 6 && typeof window['XMLHttpRequest'] != "object";
	var ie6 = false;
	$.yakuModal.impl = function(options)
	{
		var s   = this;
		s.opts  = $.extend({}, $.yakuModal.defaults, options);
		s.popup = $(s.opts.name);
		s.closeButton = $(s.opts.closeButton);
		s.init();
		s.bindEvents();
	};

	var $modal;

	$modal = $.yakuModal.impl;

    $modal.fn = $modal.prototype = {
        version : '2.0'
    }

    $modal.fn.extend =  $modal.extend = $.extend;

	$modal.fn.extend({
		init : function(){
			var s =this;

			s.popup.css({
				"position": "absolute",
				"zIndex" :  this.opts.zIndex ,
				"top"    :  this.opts.top    ,
				"left"   :  this.opts.left
			});


			if(s.popup.css('display') == 'none'){
				s.status = 0;
			} else {
				s.status = 1;
			}

			if(s.opts.center) {
				$modal.moveCenter(s.popup);
			}

			if(s.opts.backgroundDisplay){
				s.makeOverlay();
				if(ie6) $modal.fixIE(this.overlay);
			}

			if(ie6) s.makeIFrame();

			this.show();

		},
		show : function(){
			var s=this;
			if(s.status == 0)  {
				if(s.opts.fade) {
					s.popup.fadeIn(s.opts.speed);
					if(s.opts.backgroundDisplay)  s.overlay.fadeIn(s.opts.speed);
				} else {
					s.popup.css('display', 'block');
					if(s.opts.backgroundDisplay)  s.overlay.css('display', 'block');
				}
				if(ie6 && s.ifrm) {
					s.ifrm.css({
						top :  s.popup.offset().top ,
						left : s.popup.offset().left
					})
				}

				if(jQuery.ui && s.popup.draggable) {
					s.popup.draggable({
						drag: function(event, ui) {
							if(ie6) {
								s.ifrm.css({
									top  : $(this).offset().top ,
									left : $(this).offset().left
								});
							}
						}
					});
				}
				s.status = 1;
			}
		},
		close : function() {
			var s=this;
			if(s.status == 1 ) {
				if(s.opts.fade) {
					s.popup.fadeOut(this.opts.speed);
					if(s.opts.backgroundDisplay)  s.overlay.fadeOut(this.opts.speed);
				} else {
					s.popup.css('display', 'none');
					if(s.opts.backgroundDisplay)  s.overlay.css('display', 'none');
				}
				if(ie6 && s.ifrm) {
					s.ifrm.hide().remove();
				}
				s.status = 0;
			}
		},
		makeOverlay: function() {
			if(!this.overlay)
			{
				this.overlay = $("<div id='h_overlay' class='overlay'></div>");
				this.overlay.css({
						"display" : "none" ,
						"position" : "fixed" ,
						"height" : "100%" ,
						"width"  : "100%" ,
						"left" : "0",
						"top"  : "0",
						"background" : this.opts.backgroundColor ,
						"zIndex" : "200" ,
						"opacity": this.opts.backgroundOpacity
				});
				
				
				
				if($("body").has("#h_overlay").html() != null){
					$("#h_overlay").remove();
					$('body').prepend(this.overlay);
					$("#h_overlay").show();
				}else{
					$('body').prepend(this.overlay);
				}
			}
		},
		makeIFrame : function() {
			var s = this;
			if(!s.ifrm) {
				s.ifrm =  $("<iframe src='about:blank' id='iframe_bg' src='about:blank' scrolling='no' frameborder='0' ></iframe>")
				.css({
					'position' : 'absolute',
					'width'    : s.popup.width() ,
					'height'   : s.popup.height(),
					'opacity'  : '0',
					'border'   : 'none'
				}).insertBefore(s.popup);
			}
		},
		bindEvents : function(){
			var s = this;

			s.closeButton.bind("click.closeButton", function(e){
				e.preventDefault();
				s.close();
			});

			$(document).bind('keydown.layerPopup', function (e) {
				//if ( s.status == 1 && e.keyCode == 27) { // ESC
				//	e.preventDefault();
				//	s.close();
				//};
			});

			$(s.overlay).bind("click.overlay",function(e){
				//if ( s.status == 1 ) {
				//	e.preventDefault();
				//	s.close();
				//};
			});

		},
		unbindEvents : function(){
			$(this.opts.closeButton).unbind('click.closeButton');
			$(document).unbind('keydown.layerPopup');
			$(this.opts.overlay).unbind('click.overlay');
		}
	});

	$modal.extend({
		moveCenter : function(modal) {
			modal.css('top',  $(window).scrollTop() + $(window).height()/2-modal.height()/2);
			modal.css('left', $(window).width()/2-modal.width()/2);
		},
		fixIE: function (objs) {
			$.each([objs], function (i, el) {
				if (el) {
					var bch = 'document.body.clientHeight', bcw = 'document.body.clientWidth',
						bsh = 'document.body.scrollHeight', bsl = 'document.body.scrollLeft',
						bst = 'document.body.scrollTop', bsw = 'document.body.scrollWidth',
						ch = 'document.documentElement.clientHeight', cw = 'document.documentElement.clientWidth',
						sl = 'document.documentElement.scrollLeft', st = 'document.documentElement.scrollTop',
						s = el[0].style;

					s.position = 'absolute';
					if (i < 2) {
						s.removeExpression('height');
						s.removeExpression('width');
						s.setExpression('height','' + bsh + ' > ' + bch + ' ? ' + bsh + ' : ' + bch + ' + "px"');
						s.setExpression('width','' + bsw + ' > ' + bcw + ' ? ' + bsw + ' : ' + bcw + ' + "px"');
					}
					else {
						var te, le;
						if (p && p.constructor == Array) {
							var top = p[0]
								? typeof p[0] == 'number' ? p[0].toString() : p[0].replace(/px/, '')
								: el.css('top').replace(/px/, '');
							te = top.indexOf('%') == -1
								? top + ' + (t = ' + st + ' ? ' + st + ' : ' + bst + ') + "px"'
								: parseInt(top.replace(/%/, '')) + ' * ((' + ch + ' || ' + bch + ') / 100) + (t = ' + st + ' ? ' + st + ' : ' + bst + ') + "px"';

							if (p[1]) {
								var left = typeof p[1] == 'number' ? p[1].toString() : p[1].replace(/px/, '');
								le = left.indexOf('%') == -1
									? left + ' + (t = ' + sl + ' ? ' + sl + ' : ' + bsl + ') + "px"'
									: parseInt(left.replace(/%/, '')) + ' * ((' + cw + ' || ' + bcw + ') / 100) + (t = ' + sl + ' ? ' + sl + ' : ' + bsl + ') + "px"';
							}
						}
						else {
							te = '(' + ch + ' || ' + bch + ') / 2 - (this.offsetHeight / 2) + (t = ' + st + ' ? ' + st + ' : ' + bst + ') + "px"';
							le = '(' + cw + ' || ' + bcw + ') / 2 - (this.offsetWidth / 2) + (t = ' + sl + ' ? ' + sl + ' : ' + bsl + ') + "px"';
						}
						s.removeExpression('top');
						s.removeExpression('left');
						s.setExpression('top', te);
						s.setExpression('left', le);
					}
				}
			});
		}

	});

})(jQuery);

