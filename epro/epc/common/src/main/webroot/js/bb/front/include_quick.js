getIncQuick = function(quickArr, hndNm) {
		strHtml = '<div id="quick_area">';
			strHtml = strHtml + '<div class="q_info">';
				strHtml = strHtml + '<p class="uid"><em>' + hndNm + '</em><br>고객님 환영합니다</p>';
	
				strHtml = strHtml + '<ul class="q_menu">';
					strHtml = strHtml + '<li><a href="' + getFrontQuickLinkPath("ordRepay") + '"><img src="'+_imgUrl+'/bb/front/common/q-no-delivery.gif" alt="미배송환불"></a></li>';
					strHtml = strHtml + '<li><a href="' + getFrontQuickLinkPath("orderList") + '"><img src="'+_imgUrl+'/bb/front/common/q-mybuy-list.gif" alt="내 주문내역"></a></li>';
					strHtml = strHtml + '<li><a href="' + getFrontQuickLinkPath("zzimBasket") + '"><img src="'+_imgUrl+'/bb/front/common/q-mypage.gif" alt="마이페이지"></a></li>';
				strHtml = strHtml + '</ul>';
				strHtml = strHtml + '<p class="btn_log"><a href="' + getFrontQuickLinkPath("logOut") + '"><img src="'+_imgUrl+'/bb/front/common/btn-logout.gif" alt="로그아웃"></a></p>';
			strHtml = strHtml + '</div>';
	
			strHtml = strHtml + '<div class="prod_bx">';
				strHtml = strHtml + '<p class="txt_rece"><img src="'+_imgUrl+'/bb/front/common/q-view-product.gif" alt="최근본 상품"></p>';
				strHtml = strHtml + '<div class="q_prod_list">';
					strHtml = strHtml + '<p><img src="'+_imgUrl+'/bb/front/common/btn-q-prev-on.gif" alt="이전보기"></p>';
					strHtml = strHtml + '<div class="bx_hope" id="quickDiv">';
						strHtml = strHtml + '<ul>';
							for (var i=0; i< quickArr.length; i++ ) {
								strHtml = strHtml + quickArr[i];
							}
						strHtml = strHtml + '</ul>';
					strHtml = strHtml + '</div>';
					strHtml = strHtml + '<p><img src="'+_imgUrl+'/bb/front/common/btn-q-next-on.gif" alt="다음보기"></p>';
				strHtml = strHtml + '</div>';
			strHtml = strHtml + '</div>';
		strHtml = strHtml + '</div>';
		return strHtml;
}
