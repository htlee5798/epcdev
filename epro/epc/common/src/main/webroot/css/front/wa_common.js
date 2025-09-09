$(document).ready(function() {
	
	$('.wa_layerPoint').focusin(function(){
		$('.wa_popup').css('display','block');
	});
	$('.wa_layerPoint').focusout(function(){
		$('.wa_popup').css('display','none');
	});
	
});

/**
 * 장차법 관련 자바스크립트 공통파일
 * 
 * 20130715
 */
function fwa_selectorMoveButtonInstall(selectElement, onChangeListener){
	//console.log("[fwa_selectorMoveButtonInstall] " + selectElement + ", " + onChangeListener);
	
	// exception
	if (selectElement == null) return;
	
	// init
	selectElement.data("selectedValue", selectElement.val());
	
	// button
	var btnMove = $("<a></a>").attr("href", "javascript:void(0);").attr("class", "select_btn");
	btnMove.mousedown(function(e){
		onChangeListener(e);
		
		selectElement.data("selectedValue", selectElement.val());
	});
	btnMove.keydown(function(e){
		var code = (e.keyCode ? e.keyCode : e.which);
		if (code == 13) { //Enter keycode
			 onChangeListener(e);
			 selectElement.data("selectedValue", selectElement.val());
		}
	});
	btnMove.bind("focusout", function(e){
		var target = e.relatedTarget != null ? e.relatedTarget : e.toElement;
		if (target != selectElement.get(0)) {
			if (btnMove.is(":visible")){
				btnMove.hide();
				selectElement.val( selectElement.data("selectedValue") );
			}
		}
	});
	var img = $("<img/>").attr("alt", "이동").attr("src", "//simage.lottemart.com/images/front/common/select_btn_move.gif");
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
		if (target == null || target != btnMove.get(0)) {
			if (btnMove.is(":visible")){
				btnMove.hide();
				selectElement.val( selectElement.data("selectedValue") );
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