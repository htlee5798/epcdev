<template>
    <transition
            v-on:enter="onEnter"
            v-on:after-enter="onAfterEnter"
            v-on:leave="onLeave">
        <aside id="globalCategory"
               class="gnb-category sprint"
               v-if="isActive">
            <header class="header">
                <div class="wrap-personal">
                    <div class="member type-login"
                         v-if="!loginCheck">
                        <p class="name"
                           v-if="memberNo">비회원 {{guestMemberName}} 님</p>
                        <p class="name" v-else>
                            <login-button></login-button>
                            <join-button></join-button>
                        </p>
                    </div>
                    <div v-else
                         class="member">
                        <p class="level">
                            <template v-if="memberGradeCd !== ''">
                                <a :href="lmAppUrlM + '/mobile/evt/mEventDetail.do?categoryId=C0070337'"
                                   data-ga-action="카테고리 헤더 클릭"
                                   data-ga-category="롯데마트몰 - easy & slow | 1-1 | 회원등급별 혜택"
                                   data-ga-label="버튼 클릭">
                                    <i :class="levelIconClass"></i>
                                </a>
                            </template>
                        </p>
                        <p class="name">
                            <a :href="lmAppUrlM + '/mobile/mypage/PMWMMAR0001.do'">
                                {{memberName}}님
                                <em>{{memberGradeName}}</em>
                            </a>
                        </p>
                    </div>
                    <div v-if="isApp"
                         class="wrap-linked">
                        <a href="#"
                           @click.stop.prevent="onSetting">
                            <i class="icon-category-setting"></i>
                        </a>
                    </div>
                    <button type="button"
                            class="icon-close"
                            @click.stop.prevent="onClose"
                            data-gtm="M042">
                        닫기
                    </button>
                </div>
                <nav class="category-tabmenu"></nav>
            </header>
            <div class="wrap-inner-scroll">
                <div class="wrap-category-menu">
                    <div id="allCategoryMenu">
                        <div class="swiper-wrapper">
                            <nav class="menulist-category swiper-slide all-cate" data-text="전체 카테고리">
                                <h2 class="title"><em>FOOD</em></h2>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010011&SITELOC=OJ001" class="call_global" data-gtm="M006" data-cate-no="C0010011" data-log="OJ001"><i class="icon-catemenu-C0010011">쌀/잡곡</i> 쌀/잡곡</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010012&SITELOC=OJ002" class="call_global" data-gtm="M006" data-cate-no="C0010012" data-log="OJ002"><i class="icon-catemenu-C0010012">채소</i> 채소</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010013&SITELOC=OJ003" class="call_global" data-gtm="M006" data-cate-no="C0010013" data-log="OJ003"><i class="icon-catemenu-C0010013">과일</i> 과일</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010014&SITELOC=OJ004" class="call_global" data-gtm="M006" data-cate-no="C0010014" data-log="OJ004"><i class="icon-catemenu-C0010014">정육/계란</i> 정육/계란</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010015&SITELOC=OJ005" class="call_global" data-gtm="M006" data-cate-no="C0010015" data-log="OJ005"><i class="icon-catemenu-C0010015">생선/건해산물</i> 생선/건해산물</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010016&SITELOC=OJ006" class="call_global" data-gtm="M006" data-cate-no="C0010016" data-log="OJ006"><i class="icon-catemenu-C0010016">우유/냉장냉동/간편식</i> 우유/냉장냉동/간편식</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010017&SITELOC=OJ007" class="call_global" data-gtm="M006" data-cate-no="C0010017" data-log="OJ007"><i class="icon-catemenu-C0010017">생수/과자/라면/커피</i> 생수/과자/라면/커피</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010040&SITELOC=OJ008" class="call_global" data-gtm="M006" data-cate-no="C0010040" data-log="OJ008"><i class="icon-catemenu-C0010040">치킨/튀김/도시락</i> 치킨/튀김/도시락</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010046&SITELOC=OJ009" class="call_global" data-gtm="M006" data-cate-no="C0010046" data-log="OJ009"><i class="icon-catemenu-C0010046">견과/선식/한방</i> 견과/선식/한방</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010052&SITELOC=OJ010" class="call_global" data-gtm="M006" data-cate-no="C0010052" data-log="OJ010"><i class="icon-catemenu-C0010052">친환경식품</i> 친환경식품</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010053&SITELOC=OJ011" class="call_global" data-gtm="M006" data-cate-no="C0010053" data-log="OJ011"><i class="icon-catemenu-C0010053">건강식품</i> 건강식품</a>
                                <h2 class="title"><em>LIVING</em></h2>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010021&SITELOC=OJ017" class="call_global" data-gtm="M006" data-cate-no="C0010021" data-log="OJ017"><i class="icon-catemenu-C0010021">유아/출산용품</i> 유아/출산용품</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010023&SITELOC=OJ018" class="call_global" data-gtm="M006" data-cate-no="C0010023" data-log="OJ018"><i class="icon-catemenu-C0010023">화장지/세제/위생용품</i> 화장지/세제/위생용품</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010024&SITELOC=OJ019" class="call_global" data-gtm="M006" data-cate-no="C0010024" data-log="OJ019"><i class="icon-catemenu-C0010024">헤어/바디/화장품</i> 헤어/바디/화장품</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010025&SITELOC=OJ020" class="call_global" data-gtm="M006" data-cate-no="C0010025" data-log="OJ020"><i class="icon-catemenu-C0010025">가구/수납/조명/보수</i> 가구/수납/조명/보수</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010026&SITELOC=OJ021" class="call_global" data-gtm="M006" data-cate-no="C0010026" data-log="OJ021"><i class="icon-catemenu-C0010026">주방/청소/욕실용품</i> 주방/청소/욕실용품</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010027&SITELOC=OJ022" class="call_global" data-gtm="M006" data-cate-no="C0010027" data-log="OJ022"><i class="icon-catemenu-C0010027">문구/취미/자동차용품</i> 문구/취미/자동차용품</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010034&SITELOC=OJ023" class="call_global" data-gtm="M006" data-cate-no="C0010034" data-log="OJ023"><i class="icon-catemenu-C0010034">싱글즈홈</i> 싱글즈홈</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010115&SITELOC=OJ024" class="call_global" data-gtm="M006" data-cate-no="C0010115" data-log="OJ024"><i class="icon-catemenu-C0010115">침구/데코/원예</i> 침구/데코/원예</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010054&SITELOC=OJ025" class="call_global" data-gtm="M006" data-cate-no="C0010054" data-log="OJ025"><i class="icon-catemenu-C0010054">친환경생활</i> 친환경생활</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010055&SITELOC=OJ026" class="call_global" data-gtm="M006" data-cate-no="C0010055" data-log="OJ026"><i class="icon-catemenu-C0010055">반려동물용품</i> 반려동물용품</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010059&SITELOC=OJ027" class="call_global" data-gtm="M006" data-cate-no="C0010059" data-log="OJ027"><i class="icon-catemenu-C0010059">가전</i> 가전</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010061&SITELOC=OJ028" class="call_global" data-gtm="M006" data-cate-no="C0010061" data-log="OJ028"><i class="icon-catemenu-C0010061">하이마트</i> 하이마트</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010062&SITELOC=OJ029" class="call_global" data-gtm="M006" data-cate-no="C0010062" data-log="OJ029"><i class="icon-catemenu-C0010062">e쿠폰</i> e쿠폰</a>
                                <template v-if="isApp">
                                    <a href="http://m.toysrus.lottemart.com?SITELOC=OJ030"
                                       @click.stop.prevent="onToysrus"
                                       data-gtm="M006" data-log="OJ030"><i class="icon-catemenu-toysrus">토이저러스</i> 토이저러스</a>
                                </template>
                                <template v-else>
                                    <a href="http://m.toysrus.lottemart.com?return_url=http://m.toysrus.lottemart.com/mobile/main.do"
                                       @click.stop.prevent="onToysrus"
                                       data-gtm="M006"
                                       data-log="OJ030"><i class="icon-catemenu-toysrus">토이저러스</i> 토이저러스</a>
                                </template>
                                <h2 class="title"><em>STYLE</em></h2>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010022&SITELOC=OJ036" class="call_global" data-gtm="M006" data-cate-no="C0010022" data-log="OJ036"><i class="icon-catemenu-C0010022">유아동패션/잡화</i> 유아동패션/잡화</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010028&SITELOC=OJ037" class="call_global" data-gtm="M006" data-cate-no="C0010028" data-log="OJ037"><i class="icon-catemenu-C0010028">의류/언더웨어</i> 의류/언더웨어</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010029&SITELOC=OJ038" class="call_global" data-gtm="M006" data-cate-no="C0010029" data-log="OJ038"><i class="icon-catemenu-C0010029">신발/잡화</i> 신발/잡화</a>
                                <a href="/mobile/cate/firstCategoryProductList.do?CategoryID=C0010030&SITELOC=OJ039" class="call_global" data-gtm="M006" data-cate-no="C0010030" data-log="OJ039"><i class="icon-catemenu-C0010030">스포츠/레저</i> 스포츠/레저</a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020021&SITELOC=OJ040" data-gtm="M006" data-category-id="C2020021" data-log="OJ040"><i class="icon-catemenu-akplaza">AKPLAZA</i> AKPLAZA</a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020022&SITELOC=OJ041" data-gtm="M006" data-category-id="C2020022" data-log="OJ041"><i class="icon-catemenu-bonafit">보나핏</i> 보나핏</a>
                            </nav>
                            <nav class="menulist-category swiper-slide" data-text="롯데마트 전문관">
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020004&SITELOC=OJ054" data-gtm="M005" data-category-id="C2020024" data-log="OJ054"><i class="icon-catemenu-C2020004">Yorihada</i> 요리하다 <span class="desc">요리에 즐거움을 더하다</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020028&SITELOC=OJ055" data-gtm="M005" data-category-id="C2020028" data-log="OJ055"><i class="icon-catemenu-C2020028">Only Price</i> Only Price<span class="desc">스마트한 고객의 똑똑한 선택</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020001&SITELOC=OJ056" data-gtm="M005" data-category-id="C2020001" data-log="OJ056"><i class="icon-catemenu-C2020001">Hav＇eat</i> 해빗 <span class="desc">자연을 닮은 건강한 습관</span></a>​​
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020034&SITELOC=OJ077" data-gtm="M005" data-category-id="C2020034" data-log="OJ077" class=""><i class="icon-catemenu-C2020034">건강식품</i> 건강식품 <span class="desc">우리 가족 건강 지킴이</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020029&SITELOC=OJ074" data-gtm="M005" data-category-id="C2020029" data-log="OJ074"><i class="icon-catemenu-C2020029">뷰티전문관</i> 뷰티전문관 <span class="desc">뷰티 추천 아이템</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C026&SITELOC=OJ078"  data-gtm="M005" data-category-id="C026" data-log="OJ078"  class=""><i class="icon-catemenu-C026">Bonafit SOCKS</i>보나핏 삭스<span class="desc">스타일양말 전문관</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020010&SITELOC=OJ057" data-gtm="M005" data-category-id="C2020010" data-log="OJ057"><i class="icon-catemenu-C2020010">3LowFood</i> 쓰리로우푸드 <span class="desc">건강한 습관케어 프로그램</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020009&SITELOC=OJ058" data-gtm="M005" data-category-id="C2020009" data-log="OJ058"><i class="icon-catemenu-C2020009">팔도 산지장터</i> 팔도 산지장터 <span class="desc">생산자 농산물관</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C021&SITELOC=OJ059" data-gtm="M005" data-category-id="C021" data-log="OJ059"><i class="icon-catemenu-C021">하이마트</i> 하이마트 <span class="desc">전자제품 전문점</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020030&SITELOC=OJ076" data-gtm="M005" data-category-id="C02020030" data-log="OJ076"><i class="icon-catemenu-C2020030">smashing 9</i> smashing 9 <span class="desc">합리적인 가격의 최고의 신발 제안</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C023&SITELOC=OJ060" data-gtm="M005" data-category-id="C023" data-log="OJ060"><i class="icon-catemenu-C023">펫가든</i> 펫가든 <span class="desc">반려동물을 위한 쇼핑세상</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020023&SITELOC=OJ061" data-gtm="M005" data-category-id="C2020023" data-log="OJ061"><i class="icon-catemenu-C2020023">바보사랑</i> 바보사랑 <span class="desc">기분좋은 상상에너지</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020002&SITELOC=OJ062" data-gtm="M005" data-category-id="C2020002" data-log="OJ062"><i class="icon-catemenu-C2020002">ROOMxHOME</i> 룸바이홈 <span class="desc">공간을 편집하다</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020006&SITELOC=OJ063" data-gtm="M005" data-category-id="C2020006" data-log="OJ063"><i class="icon-catemenu-C2020006">page green</i> 페이지그린 <span class="desc">일상에서 여유를 찾는 녹색공간</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020011&SITELOC=OJ064" data-gtm="M005" data-category-id="C2020011" data-log="OJ064"><i class="icon-catemenu-C2020011">룸바이홈키친</i> 룸바이홈키친 <span class="desc">스마트한 주방공간</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020005&SITELOC=OJ065" data-gtm="M005" data-category-id="C2020005" data-log="OJ065"><i class="icon-catemenu-C2020005">Moter Max</i> 모토맥스 <span class="desc">내 차에 가치를 더하다</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020003&SITELOC=OJ067" data-gtm="M005" data-category-id="C2020003" data-log="OJ067"><i class="icon-catemenu-C2020003">It.Street</i> 잇스트리트 <span class="desc">나만의 잇 스타일</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020031&SITELOC=OJ073" data-gtm="M005" data-category-id="C2020031" data-log="OJ073"><i class="icon-catemenu-C2020031">잇액세서리</i> 잇액세서리 <span class="desc">나를 빛내는 잇 아이템</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020032&SITELOC=OJ075" data-gtm="M005" data-category-id="C2020032" data-log="OJ075"><i class="icon-catemenu-C2020032">잇트래블</i> 잇트래블 <span class="desc">여행을 위한 모든 것</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020007&SITELOC=OJ068" data-gtm="M005" data-category-id="C2020007" data-log="OJ068"><i class="icon-catemenu-C2020007">TE</i> 테 <span class="desc">Trend in everyday</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020022&SITELOC=OJ069" data-gtm="M005" data-category-id="C2020022" data-log="OJ069"><i class="icon-catemenu-C2020022">보나핏</i> 보나핏 <span class="desc">실용적인 언더웨어 매장</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020021&SITELOC=OJ070" data-gtm="M005" data-category-id="C2020021" data-log="OJ070"><i class="icon-catemenu-C2020021">AK플라자</i> AK PLAZA <span class="desc">백화점을 클릭하다</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020018&SITELOC=OJ071" data-gtm="M005" data-category-id="C2020018" data-log="OJ071"><i class="icon-catemenu-C2020018">초이스엘</i> 초이스엘 <span class="desc">우수한 품질 합리적 가격</span></a>
                                <a href="/mobile/special/mobileSpecialMain.do?CategoryID=C2020017&SITELOC=OJ072" data-gtm="M005" data-category-id="C2020017" data-log="OJ072"><i class="icon-catemenu-C2020017">해외상품관</i> 해외상품관 <span class="desc">롯데마트가 엄선한 해외상품</span></a>
                            </nav>
                        </div>
                    </div>
                </div>
                <footer class="footer sprint"
                        v-if="isShowFooter">
                    <a :href="lmAppUrlM + '/mobile/evt/eventMain.do'"
                       id="tabid3"
                       class="banner">
                        <img src="http://image.lottemart.com/lm2/images/contents/2017/12/m-category-banner-test.png" alt="롯데마트의 풍성한 혜택! 이벤트/혜택 총집합">
                    </a>
                    <template v-if="loginCheck && !memberNo">
                        <aside v-if="historyList.length > 0"
                               class="wrapper-fav-product">
                            <h2 class="title">최근 구매 상품</h2>
                            <nav class="fav-product">
                                <template v-for="item in historyList">
                                    <a :href="productDetailUrl(item)"
                                       class="item">
                                        <div class="thumbnail">
                                            <img :src="productImagePath(item)"
                                                 @error="onError"
                                                 :alt="item.prodNm">
                                        </div>
                                        <p class="desc">{{item.prodNm}}</p>
                                    </a>
                                </template>
                                <a :href="lmAppUrlM + '/mobile/mypage/PMWMMAR0029.do'" class="more">
                                    더보기
                                </a>
                            </nav>
                        </aside>
                    </template>
                    <nav class="group-relate">
                        <a :href="lmAppUrlM +'/mobile/plan/planDetail.do?CategoryID=C2060003&SITELOC=OJ047&MkdpSeq=000000000270&mode=M&pdivnSeq='"
                           data-gtm="M008"
                           data-log="OJ047">
                            <i class="icon-category-relate7">우리아이M클럽</i>
                            우리아이M클럽
                        </a>
                        <a :href="lmAppUrlM +'/mobile/delivery/main.do?SITELOC=OJ048'"
                           data-gtm="M008"
                           data-log="OJ048">
                            <i class="icon-category-relate8">정기배송</i>
                            정기배송
                        </a>
                        <a href="#"
                           @click.stop.prevent="goStoreCloseGuide"
                           data-gtm="M008"
                           data-log="OJ049">
                            <i class="icon-category-relate1">휴무매장 안내</i>
                            휴무매장 안내
                        </a>
                        <a href="http://m.company.lottemart.com/bc/mobile/PbcmMobileBranch/list.mdo?SITELOC=OJ050"
                           data-gtm="M008"
                           data-log="OJ050">
                            <i class="icon-category-relate2">매장안내</i>
                            매장안내
                        </a>
                        <a :href="lmAppUrlM +'/mobile/customercenter/customerCenterNotice.do?SITELOC=OJ051'"
                           data-gtm="M008"
                           data-log="OJ051">
                            <i class="icon-category-relate3">공지사항</i>
                            공지사항
                        </a>
                        <a :href="lmAppUrlM +'/mobile/customercenter/customerCenterMain.do?SITELOC=OJ052'"
                           data-gtm="M008"
                           data-log="OJ052">
                            <i class="icon-category-relate4">고객센터</i>
                            고객센터
                        </a>
                        <a :href="lmAppUrlM +'/mobile/customercenter/customercenterFAQ.do?SITELOC=OJ053'"
                           data-gtm="M008"
                           data-log="OJ053">
                            <i class="icon-category-relate5">FAQ</i>
                            FAQ
                        </a>
                        <template v-if="!loginCheck">
                            <logout-button v-if="memberNo">
                            </logout-button>
                            <login-button v-else
                                          type="categoryFooter">
                            </login-button>
                        </template>
                        <template v-else>
                            <logout-button></logout-button>
                        </template>
                    </nav>
                </footer>
            </div>
        </aside>
    </transition>
</template>
<script>
    import $ from 'jquery';
    import Swiper from 'swiper';
    import {mapGetters} from 'vuex';
    import {
        ROOT_SHOW_POPUP,
        ROOT_LOGIN_CHECK,
        ROOT_MEMBER_NO,
        ROOT_GUEST_MEMBER_NAME,
        ROOT_LM_APP_URL_M,
        ROOT_LM_CDN_STATIC_ROOT_URL,
        ROOT_LM_SERVER_TYPE,
        ROOT_ONLINE_MALL_APP,
        ROOT_HISTORY_LIST,
        ROOT_MEMBER_GRADE_CD,
        ROOT_MEMBER_GRADE_NAME,
        ROOT_MEMBER_NAME,
        ROOT_IS_APP,
        ROOT_LPOINT_SERVICE_YN
    } from "../stores/types-root";
    import LoginButton from "./LoginButton";
    import JoinButton from "./JoinButton";
    import LogoutButton from "./LogoutButton";

    let slider = null;

    export default {
        components: {
            LogoutButton,
            JoinButton,
            LoginButton
        },
        name: 'CategoryMenu',
        data () {
            return {
                isShowFooter : true,
                menuPosition : 0
            }
        },
        computed: {
            ...mapGetters({
                isActive: ROOT_SHOW_POPUP,
                loginCheck: ROOT_LOGIN_CHECK,
                memberNo: ROOT_MEMBER_NO,
                memberGradeCd: ROOT_MEMBER_GRADE_CD,
                memberGradeName: ROOT_MEMBER_GRADE_NAME,
                memberName : ROOT_MEMBER_NAME,
                guestMemberName: ROOT_GUEST_MEMBER_NAME,
                lmAppUrlM: ROOT_LM_APP_URL_M,
                lmCdnStaticRootUrl : ROOT_LM_CDN_STATIC_ROOT_URL,
                lmServerType : ROOT_LM_SERVER_TYPE,
                onlinemallApp: ROOT_ONLINE_MALL_APP,
                historyList : ROOT_HISTORY_LIST,
                isApp: ROOT_IS_APP,
                lpointServiceYn : ROOT_LPOINT_SERVICE_YN
            }),
            levelIconClass() {
                return this.memberGradeCd === '0' ? 'icon-category-level1' : 'icon-category-level' + this.memberGradeCd;
            }
        },
        methods: {
            productDetailUrl (item) {
                let lmAppUrlM = this.lmAppUrlM;
                return `${lmAppUrlM}//mobile/cate/PMWMCAT0004_New.do?CategoryID=${item.categoryId}&ProductCD=${item.prodCd}`;
            },
            productImagePath (item) {
                let lmCdnStaticRootUrl = this.lmCdnStaticRootUrl;
                let imageFolderPath = item.prodCd.substring(0, 5);
                return `${lmCdnStaticRootUrl}/images/prodimg/${imageFolderPath}/${item.prodCd}_1_500.jpg`;
            },
            onError (e) {
                showNoImage(e.target, 80, 80);
            },
            onSetting() {
                schemeLoader.loadScheme({key: 'setting'});
            },
            onClose() {
                this.$store.dispatch(ROOT_SHOW_POPUP, false);
            },
            onEnter(el, done) {
                sessionStorage.setItem('openLnbTop', $(window).scrollTop());

                let _this = this;

                slider = new Swiper(el.querySelector('#allCategoryMenu'), {
                    autoHeight: true,
                    pagination: {
                        el: el.querySelector('.category-tabmenu'),
                        clickable: true,
                        renderBullet(index, className) {
                            let name = $(el.querySelector('#allCategoryMenu'))
                                .find('nav.menulist-category.swiper-slide')
                                .eq(index)
                                .data('text');
                            return `<span class="${className}" data-gtm="M040">${name}</span>`;
                        }
                    },
                    on: {
                        transitionStart() {
                            $(el.querySelector('.wrap-inner-scroll')).scrollTop(0);
                        },
                        transitionEnd() {
                            _this.isShowFooter = slider.realIndex === 1 ? false : true;
                        }
                    }
                });

                if(this.loginCheck && !this.memberNo) {
                    $.api.get({
                        apiName: 'getMobileNearestBuyHistoryV2',
                        successCallback(data) {
                            _this.$store.dispatch(ROOT_HISTORY_LIST, data.historyList);
                        }
                    });
                }

                setTimeout(() => {
                    done();
                }, 300);
            },
            onAfterEnter(el) {
                $('html, body').addClass('mask');
                $('#page-wrapper').addClass('globalCategory');
            },
            onLeave(el, done) {
                let top = sessionStorage.getItem( 'openLnbTop' ) || 0;
                $('#page-wrapper').removeClass('globalCategory');
                setTimeout(() => {
                    $('html, body')
                        .removeClass('mask')
                        .scrollTop(top);

                    schemeLoader.loadScheme({key: 'lnbClosed'});
                    schemeLoader.loadScheme({key: 'showBar'});

                    done();
                }, 300);
            },
            onToysrus () {
                if(schemeLoader.loadScheme({key: 'openToysrus'})) {
                    location.href = 'http://m.toysrus.lottemart.com';
                }
            }
        }
    };
</script>