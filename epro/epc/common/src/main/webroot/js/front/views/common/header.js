$(document).ready(function() {
	// 회원가입
	$("#GNB_FamilyJoin").click(function(e) {
		eventPropagationWrapper(e, function() {
			global.familyJoin('SITELOC=JA005');
		});
	});
	
	// 회원정보수정
	$("#GNB_ChangeMyInfo").click(function(e) {
		eventPropagationWrapper(e, function() {
			if (_LMFamilyLoginYn != "Y") {
				global.integrationChangeUser('SITELOC=LA013',_ReqURL);
			}
			else {
				global.familyChangeUser('SITELOC=LA013');
			}
		});
	});
	
	function deleteCookie(cookieName){
		var expireDate = new Date();	
		expireDate.setDate( expireDate.getDate() - 1 );
		document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
	};
	
	// 로그아웃
	$("#GNB_Logout").click(function(e) {
		deleteCookie('DORMANCY_ORDER_EVENT');
		eventPropagationWrapper(e, function() {
			if (_LMB2ELoginYn === "Y") {
				global.b2eLogout(_ReqURL, "/login/b2eLogout.do");
			}
			else if (_GuestMember_yn === "true") {
				global.b2eLogout(_ReqURL);
			}
			else if (_ReqURL.indexOf("/mymart/") >= 0 ) {
				global.logout();
			}
			else {
				global.logout(_ReqURL);
			}
		});
	});
	
	// 로그인
	$("#GNB_Login, #RNB_Login, #RNB_Login_Today").click(function(e) {
		eventPropagationWrapper(e, function() {
			global.login(_SID_NM_MARTMALL, _ReqURL);
		});
	});
	
	// 장바구니
	$("#GNB_Basket").click(function(e) {
		//비회원-주문배송조회로그인 경우 진입불가
		var _isNoMemLoginType = window.isNoMemLoginType || false;
		
		if(_isNoMemLoginType == "002"){
			if(confirm("비회원 배송 조회는 주문한 내역만 확인 가능합니다.\n장바구니는 회원가입 후 이용할 수 있습니다.\n회원 가입 후 이용하시겠습니까?")){
				global.b2eLogout(_ReqURL);
				global.familyJoin('SITELOC=JA005');
				return;
			}else {
				return;
			}
		}else{
			eventPropagationWrapper(e, global.goBasket);
		}
	});
	
	// 주문/배송
	$("#GNB_MyOrder").click(function(e) {
		eventPropagationWrapper(e, global.goMyOrder);
	});
	
	// 찜바구니
	$("#GNB_WishList").click(function(e) {
		eventPropagationWrapper(e, global.goWish);
	});
	
	$("#GNB_MyCoupon").click(function(e) {
		eventPropagationWrapper(e, function() {
			goMymart('selectMyCoupon.do?SITELOC=AA010');
		});
	});
	
	$("#GNB_Milege").click(function(e) {
		eventPropagationWrapper(e, function() {
			goMymart('selectMyMileage.do?SITELOC=AA011');
		});
	});
	
	$("#GNB_LPOINT").click(function(e) {
		eventPropagationWrapper(e, function() {
			goMymart('selectMyLotteMembersPoint.do?SITELOC=AA012');
		});
	});
	
	$("#GNB_MyMart").click(function(e) {
		eventPropagationWrapper(e, function() {
			//톡상담 종료로 인해 1:1문의 페이지로 url 변경
//			goMymart('selectMyTalkCommunication.do?SITELOC=AA013');
			goMymart('selectMyCounselList.do');
		});
	});

	//GNB_OrderCount();
	GNB_BasketCount();
});

//<!--
//민기영 책임님 요청 주문 카운트 삭제
//-->
//var GNB_OrderCount = function() {
//	if (_Member_yn === "true" ) {	//	global.isLogin(null, function() {
//		$.getJSON("/mymart/api/myorder/count.do")
//		.done(function(data) {
//			if (data) {
//				var cnt = data.count;
//				if ($("#GNB_MyOrder > em").length > 0) {
//					$("#GNB_MyOrder > em").text(cnt);
//				}
//				else {
//					$("#GNB_MyOrder").append("<em>" + cnt + "</em>");
//				}
//			}
//		});
//	}	//	}, dummyFunc);
//};

// 장바구니 수량 조회
var GNB_BasketCount = function() {
	if (_Login_yn === "Y" ) {	//	global.isLogin(null, function() {
		$.getJSON("/basket/api/count.do")
		.done(function(data) {
			var cnt = 0;
			if (data) {
				cnt = data.count;
			}

			if ($("#GNB_Basket > em").length > 0) {
				$("#GNB_Basket > em").text(cnt);
			}
			else {
				$("#GNB_Basket").append("<em>" + cnt + "</em>");
			}
		});
	}	//	}, dummyFunc);
};