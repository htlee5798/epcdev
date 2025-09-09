/**  SCP-PROJECT : sejisesang START
 * @FileName : mcoupon_scpcommon.js
 * @Description : 쿠폰 및 스탬프 세부정보 팝업창 관리
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   생성일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2015.03.12  박세진      신규작성
 *
 * @Copyright (C) 2000 ~ 2015 롯데정보통신(주) All right reserved.
 * </pre>
*/

// 쿠폰 팝업 열기
function openPopupCoupon(value, type, isUsed) {
	
	// 브라우저별 버전
	var browser = GetBrowser();
	if(browser <= 7){
		$("body").css({
			'position' : 'absolute',
			'top' : 0,
			'left' : 0,
			'width' : '100%',
			'height' : '100%',
			'overflow-y' : 'hidden'
		});
	}else{
		$("body").css({
			'position' : 'fixed',
			'overflow-y' : 'scroll',
			'width' : '100%'
		});
	}
	
	$(".cp-target").css("display", "none");
	
	if ( value == "sum" && type != "" ){
		$("#couponDetail").removeClass();
		$("#couponDetail").addClass("coupon-profile sum-coupon");
		$("#couponDetail").addClass("sum-coupon-" + type);
	}
	else if ( value == "goods" ){
		$(".cp-target").css("display", "block" );
		$("#couponDetail").removeClass();
		$("#couponDetail").addClass("coupon-profile goods-coupon");
	} else {
		$("#coupon_layer_popup").css("display", "none");
		console.log("쿠폰 정보를 확인할 수 없습니다.");
		return;
	}
	
	var new_height = ( $(window).height() - $("#coupon_layer_popup").height() ) / 2 + $(window).scrollTop() - 190 ; //190: 공통 top의 높이 
	
	$("#coupon_layer_popup").css( "top", new_height+"px" );
	
	modalBackgroundSet();
	
	if (isUsed == true) $(".used").css("display", "block");
	else $(".used").css("display", "none"); 
	
	$("#coupon_layer_popup").css("display", "block");
	
}


// 스탬프 팝업 열기 
function openPopupStamp(value) {
	
	// 브라우저별 배경창 제어
	var browser = GetBrowser();
	if(browser <= 7){
		$("body").css({
			'position' : 'absolute',
			'top' : 0,
			'left' : 0,
			'width' : '100%',
			'height' : '100%',
			'overflow-y' : 'hidden'
		});
	}else{
		$("body").css({
			'position' : 'fixed',
			'overflow-y' : 'scroll',
			'width' : '100%'
		});
	}
	
	if (value == null || value == "") return;
	$("#" + value + "_layer_popup").css("display", "block");
	
	var new_height = ( $(window).height() - $("#"+value+"_layer_popup").height() ) / 2 + $(window).scrollTop() - 190; //190: 공통 top의 높이
	$("#"+value+"_layer_popup").css( "top", new_height+"px" );
	
	if (value != 'gMember') modalBackgroundSet(); //스크롤바 확인 위해 주석 처리
	
	// 상세팝업 안의 스크롤 맨 위로
	$("#layerbody").scrollTop(0);
}


// 팝업 닫기 
function closePopup(value) {
	if ( value == null || value == "" ) return;
	$("#"+value+"_layer_popup").css( "display", "none" );
	
	// 브라우저 버전 확인(IE)
	var browser = GetBrowser();
	
	// value 값에 따른 팝업창 닫기 및 초기화 설정
	if(value == "gMember"){
		
		// 멤버리스트/참여신청자 -> 멤버리스트로 초기화
		$("#tab_mart1").children("ul").children("li").removeClass("on");
		$("#tab_mart1").children("ul").children("li:first-child").addClass("on");
		$("#applicantList").css("display","none");
		$("#memList").css("display","block");
		
		//멤버리스트 팝업창만 닫음
		$("#"+value+"_layer_popup").css( "display", "none" );
		
	}else{
		
		// 스탬프 보기/ 선물확인 -> 스탬프 보기로 초기화
		$("#tab_mart").children("ul").children("li").removeClass("on");
		$("#tab_mart").children("ul").children("li:first-child").addClass("on");
		$("#benefitList").css("display","none");
		$("#stampDetailList").css("display","block");
		
		// 배경창 제어	
		if(browser <= 7){
			$("body").css({
				'position' : 'relative',
				'overflow-y' : 'auto'
			});		
		}else{
			$("body").css({
				'position' : 'relative'
			});
		}
		
		$("body div.modal-bg").remove();
		$("#UxHeader").unwrap('<div class="ie7-wrap" />');
		$("#footer").unwrap('<div class="ie7-wrap2" />');
		
	}
}

// 모달팝업 배경
function modalBackgroundSet() {
	
	$("#container_v2").after('<div class="modal-bg"></div>');

	$("#UxHeader").wrap( '<div class="ie7-wrap" />');
	$("#footer").wrap('<div class="ie7-wrap2" />');
	
	var width = $('body').width();
	var height = $('body').height();
	
	$(".modal-bg").css({
		"width" : width+"px",
		"height" : height+"px",
		"top":"-190px"
	});
	
}


//스탬프 상세 탭 변경	
function changeTab(obj) {
	var tabid = $(obj).attr("id");	
	var classchk = $(obj).parent().parent().hasClass("on");	
	var ulid = $(obj).parent().parent().parent().attr("id");	

	if (!classchk) {
		
		$("#" + ulid).children("li").removeClass("on");	
		$(obj).parent().parent().addClass("on");
		
			// 상세보기의 탭과 멤버리스트 탭의 remove를 구분 
			if(tabid=="stampDetail"||tabid=="benefit"){
				$(".tab-box1").css("display", "none");
			}else{
				$(".tab-box").css("display", "none");
			}
		
		$("#" + tabid + "List").css("display", "block");
	}	
	
}

// 브라우저 판별 및 버전 확인
function GetBrowser(){
	var browser = navigator.userAgent.toLowerCase();
		if ( -1 != browser.indexOf('msie') ){
			 if (navigator.appName == 'Microsoft Internet Explorer') {        
	              var ua = navigator.userAgent;        
	              var re = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");        
	              if (re.exec(ua) != null)            
	                  rv = parseFloat(RegExp.$1);    
	             }    
	         return rv; 
		}
		return 'undefined browser';
};


/** SCP-PROJECT : sejisesang END **/