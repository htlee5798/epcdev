/**
 * 장차법 관련 자바스크립트 공통파일
 * 
 * 20130715
 */
function fwa_selectorMoveButtonInstall(selectElement, onChangeListener){
	//console.log("[fwa_selectorMoveButtonInstall] " + selectElement + ", " + onChangeListener);
	
	// exception
	if (selectElement == null) return;
	
	// button
	var btnMove = $("<a></a>").attr("href", "javascript:void(0);").attr("class", "select_btn");
	btnMove.click(function(e){
		onChangeListener(e);
	});
	btnMove.keydown(function(e){
		var code = (e.keyCode ? e.keyCode : e.which);
		if (code == 13) { //Enter keycode
			 onChangeListener(e);
		}
	});
	btnMove.bind("focusout", function(e){
		var target = e.relatedTarget != null ? e.relatedTarget : e.toElement;
		if (target != selectElement.get(0)) {
			if (btnMove.is(":visible")){
				btnMove.hide();
			}
		}
	});
	var img = $("<img/>").attr("alt", "이동").attr("src", "data:image/gif;base64,R0lGODlhNgAeAMQfAJiYmOHh4fb29uPj48/Pz+Li4s3NzZqamvn5+d/f35+fn6WlpcLCwvf396ioqMHBwdnZ2ZeXl5mZmdXV1dDQ0PDw8Pr6+o+Pj8nJyfz8/P39/f7+/vv7+2ZmZv///////yH/C1hNUCBEYXRhWE1QPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNS4wLWMwNjAgNjEuMTM0Nzc3LCAyMDEwLzAyLzEyLTE3OjMyOjAwICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOnhtcE1NPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0vIiB4bWxuczpzdFJlZj0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL3NUeXBlL1Jlc291cmNlUmVmIyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ1M1IFdpbmRvd3MiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6MzMwOUM3REZFRTk4MTFFMjk0NDZFNjBCRUI2QTdGMjkiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6MzMwOUM3RTBFRTk4MTFFMjk0NDZFNjBCRUI2QTdGMjkiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDozMzA5QzdEREVFOTgxMUUyOTQ0NkU2MEJFQjZBN0YyOSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDozMzA5QzdERUVFOTgxMUUyOTQ0NkU2MEJFQjZBN0YyOSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PgH//v38+/r5+Pf29fTz8vHw7+7t7Ovq6ejn5uXk4+Lh4N/e3dzb2tnY19bV1NPS0dDPzs3My8rJyMfGxcTDwsHAv769vLu6ubi3trW0s7KxsK+urayrqqmop6alpKOioaCfnp2cm5qZmJeWlZSTkpGQj46NjIuKiYiHhoWEg4KBgH9+fXx7enl4d3Z1dHNycXBvbm1sa2ppaGdmZWRjYmFgX15dXFtaWVhXVlVUU1JRUE9OTUxLSklIR0ZFRENCQUA/Pj08Ozo5ODc2NTQzMjEwLy4tLCsqKSgnJiUkIyIhIB8eHRwbGhkYFxYVFBMSERAPDg0MCwoJCAcGBQQDAgEAACH5BAEAAB8ALAAAAAA2AB4AAAX/4DcQWGmeaKqurEkMXxEgXm3feK7vvI0EEAOnRywabxwH5shs6g5Lp7QJiE6vxCp2y9NyvzcvGCwec8vTzq3DbrOpVmN7Td+ie+pa3rPnn+NEe4JrbnNMdzyDejt9hxgbkJGSkx2RlZCXG4WFk52SVZ6hmaOhpaYboKeWq5iUnKqfj7CYbJKZmp23qlUavb6/wMG9Hb/EwMbCwrzJzMfHhc3KGNHU1da9VRna29zd3t/g4d/Z4uXm591VHOvs7e7v8PHy8Orz9vf47vX5/P3tDREMCPBHkF8ACQUmVLDAsKHDhxAjSpzIUECCBQw+UFBwoaPHjyBDihxJ0uOBBx9CAQAAOw==");
	img.css("margin-left", "5px");
	btnMove.append(img);
	selectElement.after(btnMove);
	btnMove.hide();
	
	// event
	selectElement.removeAttr("onchange");
	selectElement.bind("focusin", function(e){
		if (btnMove.is(":hidden")){
			btnMove.show();
		}
	});
	selectElement.bind("focusout", function(e){
		var target = e.relatedTarget != null ? e.relatedTarget : e.toElement;
		if (target != null && target != btnMove.get(0)) {
			if (btnMove.is(":visible")){
				btnMove.hide();
			}
		}
	});
}

// tab 이용시 간편보기 아이콘 보이기
function fwa_tabFocusIn(e) {
	var target = e.target != null ? e.target : e.srcElement;
	$(target).parent().children(".flag_qv").show();
};

// tab 이용시 간편보기 아이콘 숨기기 
function fwa_tabFocusOut(){
	$('.flag_qv').hide();
};

// tab 이용시 간편보기 아이콘 숨기기 
function fwa_tabFocusOut1(e){
	var target = e.relatedTarget != null ? e.relatedTarget : e.toElement;
	if(!$(target).hasClass("flag_qv")){
		$('.flag_qv').hide();
	}
};

// tab 뒤로돌아갈때
function fwa_focusMove(e){
	var target = e.target != null ? e.target : e.srcElement;
	setTimeout(function(){
		$(target).parent().next().find('.option_cart select').eq(0).focus();
	}, 50);
}

// tab 이용시 간편보기 팝업 열기 추가	
function fwa_reviewFocusIn(cateId,prodCd,itemCd,printYn){
	window.open(_LMAppUrl+"/product/popup/ProductZoom.do?ProductCD="+prodCd+"&ItemCd="+itemCd+"&PrintYn="+printYn+"&CategoryID="+cateId, "PopupProductImgZoom2", "toolbar=no,location=no,directories=no,status=no,scrollbars=no,resizable=no,menubar=no,width=900,height=630,dependent=yes");
};

// 고객상품평 tab으로 접근시 이벤트
function fwa_detailSub(){
	if( jQuery('.tbl_review').length ) {
		
		var commentObj = jQuery('.tbl_review');
		var detailObj, tableObj;

		jQuery(commentObj).find('.subject').each(function(){
			// 상품평 제목 객체 class='on' 으로 통일
			detailObj = jQuery(this).parent().next();

			if( jQuery(this).parent().attr('class') == 'on' ) {
				prdCmtObj = jQuery(this);
			}

			tableObj = jQuery(commentObj);
			jQuery(this).bind('keypress', function(e) {
				if(e.keyCode == 13){
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
				}
			});
		});

		commentObj = null; detailObj = null; 
		return false;
	}
}