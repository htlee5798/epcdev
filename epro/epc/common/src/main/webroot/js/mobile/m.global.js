//검색엔진 쿠키값 정보를 가져오기 위한 셋팅 (2016.03.10)
function getSearchMIdCookie(name) {
	var dc = document.cookie;
	var prefix = name + "=";
	var begin = dc.indexOf("; " + prefix);
	if (begin == -1) {
	    begin = dc.indexOf(prefix);
	    if (begin != 0)
	        return null;
	} else {
	    begin += 2;
	}

	var end = document.cookie.indexOf(";", begin);
	if (end == -1)
	    end = dc.length;
	return unescape(dc.substring(begin + prefix.length, end));
}

function setSearchMIdCookie(name, value, expiredays){
	var today = new Date();
	today.setDate( today.getDate() + expiredays );
	document.cookie = name + "=" + escape( value ) + "; expires=" + today.toGMTString() + ";"
}

function generateSearchMIdCookie(){
	if ( getSearchMIdCookie("koost_cid") == null){
		var n = new Date();
		var p1 = n.getTime();
		var p2 = Math.random().toString(16).slice(2,10);
		setSearchMIdCookie("koost_cid",p1 +':'+ p2,3000);
	}
}
function lnbOpen(button) {
	var $lnb = $('aside#globalCategory'),
		swiper = $( '#allCategoryMenu' ).length > 0 ? $( '#allCategoryMenu' )[0].swiper : undefined;

	sessionStorage.setItem( 'openLnbTop', $( window ).scrollTop() );

	//TODO: 4번 배포 후 삭제
	if($lnb.length > 0) {
		if( swiper ) {
			swiper.slideTo( 0, false );
		}
		lnbShow();
		var isLoading = false;
		$lnb.on('webkitTransitionEnd transitionend', function() {
			var $this = $(this);
			if(isLoading) return;
			isLoading = true;

			if($this.find('.wrap-inner-scroll').length <= 0) {
				var returnUrl = getReturnUrl();
				$.get('/inc/mobileGnbMenu.do', {'ICPASS' : 'Y', 'returnUrl' : returnUrl }, function(response) {
					$this.html(response);

					swiper = $( '#allCategoryMenu' )[0].swiper;
					if( swiper ) {
						swiper.slideTo( 0, false );
					}
				});
			}

			$('html, body').addClass('masking');
		});
	} else {
		$(button).prop('disabled', true);
		var lnb = $('aside#globalCategory');
		if(lnb.length > 0) {
			if( swiper ) {
				swiper.slideTo( 0, false );
			}
			lnbShow();
			$(button).prop('disabled', false);
			return;
		}

		var returnUrl = getReturnUrl();

		$.get('/inc/mobileGnbMenu.do', {'ICPASS' : 'Y', 'returnUrl' : returnUrl, 'ab': sessionStorage.getItem('ab') }, function(response) {
			var pageWrapper = $('#page-wrapper');
			pageWrapper.append(response);

			swiper = $( '#allCategoryMenu' )[0].swiper;
			swiper.slideTo( 0, false );
			lnbShow();
			$(button).prop('disabled', false);

			$('html, body').addClass('masking');
		});
	}

}

function lnbShow() {
//	var wrapper = $('html, body'),
	var page = $('#page-wrapper'),
		tgtCls = 'globalCategory';
//성능 이슈 발생
//	setTimeout(function() {

//		page.append('<div class="global-mask"/>');
//	}, 300 );

	page.addClass(tgtCls);
    schemeLoader.loadScheme({key: 'lnbOpen'});
    schemeLoader.loadScheme({key: 'hideBar'});

    if( window.LOTTEMART ) {
    	window.LOTTEMART.hideBar();
    }
}

function getReturnUrl() {
	var returnUrl = location.origin + location.pathname + location.search;
	var returnCategoryId = $.cookie !== undefined ? $.cookie('__categoryId') : utils.cookie.get( '__categoryId' );
	if(returnCategoryId && location.search.indexOf('returnCategoryId') < 0) {
		returnUrl += ((location['search']) ? '&' : '?') + "returnCategoryId=" + returnCategoryId;
	}
	return returnUrl;
}

function lnbClose(){

	var wrapper = $('html, body'),
		page = $('#page-wrapper'),
		tgtCls = 'globalCategory',
		top = sessionStorage.getItem( 'openLnbTop' );

	page.removeClass(tgtCls);
//	$('.global-mask').remove();
	wrapper.removeClass('masking');

	$('.trigger-category-submenu').removeClass('trigger-category-submenu');
	$('li.active').removeClass('active');

	$('.wrap-inner-scroll').scrollTop(0);

	$( 'html, body' ).scrollTop( top ? top : 0 );

	setTimeout(function() {
        schemeLoader.loadScheme({key: 'lnbClosed'});
        schemeLoader.loadScheme({key: 'showBar'});
	}, 300);

}
function ban_close(){
	var d = new Date();
	d.setDate(d.getDate() + 1); //1일 뒤 이 시간
	var expires = "expires="+d.toGMTString();
	document.cookie = "viewban=Y;" + expires;
    if (document.querySelector('#settingTopBanner')) document.querySelector('#settingTopBanner').style.display = 'none';
}

//설치상품, 주문제작상품 일 때
function goDetail(categoryId , prodCd){
    alert("설치상품, 주문제작상품은 상품 상세에서 담으실수 있습니다." );
    goProductDetailMobile(categoryId,prodCd,'N','');
}

function openPackageDetail(periDeliPkgNo) {
	location.href="/mobile/delivery/package.do?peri_deli_pkg_no=" + periDeliPkgNo;
}

function selProductListByCategory(categoryId){
	location.href="/mobile/popup/selProductListByCategory.do?CategoryID="+categoryId;
}

function ban_checkCookie(){
	var username=ban_getCookie("viewban");
	if(username =="Y"){
		$('#settingTopBanner').hide();
	}else{
		if(username == "" || username == null){
			$('#settingTopBanner').show();

		}
	}
}

function ban_getCookie(cname){

	var name = cname + "=";
	var ca = document.cookie.split(';');
	for(var i=0; i<ca.length;i++){
	var c = ca[i];
	while(c.charAt(0)==' ') c = c.substring(1);
	if(c.indexOf(name) == 0) return c.substring(name.length,c.length);
	}
	return "";

}

function tooltipLayerPopup(obj, elem, lTop){
	areaTooltipLayerPopup(obj, elem, lTop);
}
/* 레이어 팝업 */
function areaTooltipLayerPopup(obj, elem, lTop, openArea){
	var openEl ;
	if(openEl instanceof jQuery){
		openEl = openArea;
	}else{
		if(!openArea){
			openArea = 'body';
		}
		openEl = $(openArea);
	}

    var $el = openEl.find('[data-layer='+elem+']');

    obj.addClass('active');

    openEl.addClass('layer-popup-active').append($el).append('<div class="mask"/>');
    $el.css('top', lTop);
    $el.fadeIn(200);

    openEl.find('.mask, .js-close').click(function(){
        $el.hide();
        obj.removeClass('active');
        openEl.find('.mask').remove();
        openEl.removeClass('layer-popup-active');
    });
};
function showNoEventImage(obj){

	if(typeof obj.src == "undefined"){
        return "";
	 }
	 if ( obj.src.indexOf("noimg_") == -1 ) {
        //$(obj).attr("src",_LMCdnStaticUrl+"/images/staticimg/plan/staticimg_plan_default.jpg");
        	$(obj).attr("src","${_LMCdnV3RootUrl}/images/layout/noimg_event_240x188.jpg");
	}
}
//맞춤 베스트 전역 함수
function changeCustomMode(type) {
	var gender = $("[name='bestSetSex']:checked").val();
	var age = $("[name='bestSetAge']:checked").val();

	var frm = document.frm;

	frm.custom.value = type;
	frm.gender.value = gender;
	frm.age.value = age;

	frm.submit();
}
//성능 개선 - blocking 스크립트 제거
function validateQty( that ){
	var $this = $( that ),
		currentQty = $this.val() === '' ? 0 : $this.val(),
		maxQty = $this.data( 'maxQty' ) || 999,
		minQty = $this.data( 'minQty' ) || 1;

	if( currentQty >= maxQty ) {
		alert("상품의 최대구매수량은 " + maxQty + "개 입니다.");
        $this.val( maxQty );
	} else if( currentQty < minQty ) {
		alert("상품의 최소구매수량은 " + minQty + "개 이상 최대 "+maxQty+ "개 까지만 구매가 가능합니다.");
        $this.val(minQty);
	}
}
//성능 개선 - 주문수량
function calculationOrderQty( target , prodCd, gubun, areaIdx, _minQty, _maxQty) {
	var $target = $( target ),
		$form = $target.closest( 'form' ),
		$orderQty = $form.find( '[name="orderQty"]' ),
		minQty = _minQty || parseInt( $form.find( '[name="minQty"]' ).val() , 10 ),
		maxQty = _maxQty || parseInt( $form.find( '[name="maxQty"]' ).val(), 10 ),
		tmpQty = parseInt( $orderQty.val(), 10 );

	if( gubun === "incre" ) {
		$orderQty.val( tmpQty + 1 );
		//현재수량 재셋팅
		tmpQty	= $orderQty.val();
	} else if( gubun === "decre" ) {
		$orderQty.val( tmpQty < minQty ? minQty : maxQty -1 );
		//현재수량 재셋팅
		tmpQty	= $orderQty.val();
	}

	if( tmpQty < minQty ) {
		alert( fnJsMsg( view_messages.error.productOrderQty, minQty, maxQty ) );	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
		$orderQty.val( minQty );
	} else if( tmpQty > maxQty ) {
		alert( fnJsMsg( view_messages.error.productOrderQty, minQty, maxQty ) );	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
		$orderQty.val( maxQty );
	}
}
//이미지 에러시 noimg 대체
function imageError(obj, fileName) {
	if ( fileName == undefined) {
		return;
	}
	obj.src = $.utils.config( 'LMCdnV3RootUrl' ) + "/images/layout/" + fileName;
}