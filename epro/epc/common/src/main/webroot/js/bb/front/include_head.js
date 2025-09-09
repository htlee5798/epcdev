	getIncHead = function(announArr, cateArr) {
		strHtml = '<div id="head">';
			strHtml = strHtml + '<h1><a href="' + getFrontLinkPath("main") + '"><img src="'+_imgUrl+'/bb/front/common/h1-logo-lottemart.gif" alt="LOTTE MART B2B"></a></h1>';
			strHtml = strHtml + '<div id="utill_menu">';
				strHtml = strHtml + '<ul class="utm">';
				strHtml = strHtml + '<!-- 111025 수정 -->';
				strHtml = strHtml + '<li><a href="' + getFrontLinkPath("logOut") + '"><img src="'+_imgUrl+'/bb/front/common/topmenu-logout.gif" alt="로그아웃"></a></li>';
			strHtml = strHtml + '<!-- //111025 수정 -->';
				strHtml = strHtml + '<li class="alt"><a href="' + getFrontLinkPath("custCtr") + '"><img src="'+_imgUrl+'/bb/common/topmenu-customer.gif" alt="고객센터"></a></li>';
			
					strHtml = strHtml + '<li class="alt"><a href="' + getFrontLinkPath("aplyLteCard") + '"  target="_blank"><img src="'+_imgUrl+'/bb/front/common/topmenu-card-apply.gif" alt="롯데카드신청"></a></li>';
					strHtml = strHtml + '<li class="alt"><a href="' + getFrontLinkPath("simplePay") + '"><img src="'+_imgUrl+'/bb/front/common/topmenu-pay-apply.gif" alt="간편결제신청"></a></li>';
				strHtml = strHtml + '</ul>';
			strHtml = strHtml + '</div>';
	
			strHtml = strHtml + '<!-- 검색 -->';
			strHtml = strHtml + '<div id="top_search">';
				strHtml = strHtml + '<div class="sc_area">';
					strHtml = strHtml + '<div class="sc_list">';
						strHtml = strHtml + '<span>상품명</span>';
						strHtml = strHtml + '<div class="sc_arrow"><img src="'+_imgUrl+'/bb/front/common/ico-search-bottom-off.gif" alt=""></div>';
						strHtml = strHtml + '<input type="hidden" id="sType" name="sType" value="t_product">';
					strHtml = strHtml + '</div>';
					strHtml = strHtml + '<div class="line_off"></div>';
					strHtml = strHtml + '<div class="sc_input"><input type="text" id="comSearchTxt" name="comSearchTxt" value=""></div>';
					strHtml = strHtml + '<div class="sc_btn"><a href="javascript:goComSearch();"><img src="'+_imgUrl+'/bb/front/common/btn-gnb-search.gif" alt="검색"></a></div>';
					strHtml = strHtml + '<div class="clr"></div>';
					strHtml = strHtml + '<!-- 통합검색 레이어 -->';
					strHtml = strHtml + '<div class="sel_lay"><div class="in" id="comSchDiv">';
						strHtml = strHtml + '<ul>';
							strHtml = strHtml + '<li ref="t_product" >상품명</li>';
							strHtml = strHtml + '<li ref="t_product_code" >상품코드</li>';
							strHtml = strHtml + '<li ref="t_company" >업체명</li>';
						strHtml = strHtml + '</ul>';
					strHtml = strHtml + '</div></div>';
					strHtml = strHtml + '<!-- // 통합검색 레이어 -->';
				strHtml = strHtml + '</div>';
			strHtml = strHtml + '</div>';
	
			strHtml = strHtml + '<!-- 주문 가능 시간 -->';
			strHtml = strHtml + '<div class="top_order_time">';
				strHtml = strHtml + '<img src="'+_imgUrl+'/bb/front/common/img-order-time.gif" alt="">';
			strHtml = strHtml + '</div>';
	
			strHtml = strHtml + '<!-- gnb -->';
			strHtml = strHtml + '<div id="gnb">';
				strHtml = strHtml + '<ul>';
						for (var i=0; i< cateArr.length; i++ ) {
							strHtml = strHtml + cateArr[i];
						}
				strHtml = strHtml + '</ul>';
			strHtml = strHtml + '</div>';
	
			strHtml = strHtml + '<!-- 나의 메뉴 -->';
			strHtml = strHtml + '<div id="mymenu">';
				strHtml = strHtml + '<ul class="mym">';
					strHtml = strHtml + '<li><a href="' + getFrontLinkPath("basket") + '"><img src="'+_imgUrl+'/bb/front/common/topmenu-basket.gif" alt="장바구니"></a></li>';
					strHtml = strHtml + '<li><a href="' + getFrontLinkPath("zzimBasket") + '"><img src="'+_imgUrl+'/bb/front/common/topmenu-wish-list.gif" alt="찜바구니"></a></li>';
					strHtml = strHtml + '<li><a href="' + getFrontLinkPath("frqOrd") + '"><img src="'+_imgUrl+'/bb/front/common/topmenu-regular-product.gif" alt="단골상품"></a></li>';
				strHtml = strHtml + '</ul>';
			strHtml = strHtml + '</div>';
			strHtml = strHtml + '<div class="top_product">';
				strHtml = strHtml + '<ul>';
					strHtml = strHtml + '<li><a href="' + getFrontLinkPath("event") + '"><img src="'+_imgUrl+'/bb/front/common/menu-event-product.gif" alt="행사상품"></a></li>';
					strHtml = strHtml + '<!-- 행사상품 페이지일 경우 이미지명 menu-event-product-on.gif 으로 변경 -->';
					strHtml = strHtml + '<li><a href="' + getFrontLinkPath("season") + '"><img src="'+_imgUrl+'/bb/front/common/menu-time-product.gif" alt="시즌상품"></a></li>';
					strHtml = strHtml + '<!-- 시즌상품 페이지일 경우 이미지명 menu-time-product-on.gif 으로 변경 -->';
				strHtml = strHtml + '</ul>';
			strHtml = strHtml + '</div>';
	
			strHtml = strHtml + '<!-- 공지사항 -->';
			strHtml = strHtml + '<div class="top_notice">';
				strHtml = strHtml + '<p class="tit">공지사항</p>';
				strHtml = strHtml + '<div id="n_list" class="n_list">';
				for (var i=0; i< announArr.length; i++ ) {
					strHtml = strHtml + announArr[i];
				}
				strHtml = strHtml + '</div>';
			strHtml = strHtml + '</div>';
		strHtml = strHtml + '</div>';
		
		return strHtml;
	}

