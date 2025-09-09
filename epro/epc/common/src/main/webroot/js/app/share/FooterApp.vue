<template>
    <footer id="footer" :class="{'footer-loading':!showFooter}">
        <nav class="inform-nav">
            <a v-if="!loginCheck && memberNo"
               @click.prevent="goLogout;"
               href="#"
               data-gtm="M009">
                비회원 로그아웃
            </a>
            <a v-if="!loginCheck && !memberNo"
               href="#"
               @click.prevent="new_logins"
               data-gtm="M009">
                로그인
            </a>
            <a v-if="loginCheck"
               @click.prevent="goLogout"
               data-gtm="M009">
                로그아웃
            </a>
            <a :href="this.$store.state.lmAppUrlM +'/mobile/customercenter/customerCenterMain.do'"
               data-gtm="M009">
                고객센터
            </a>
            <a :href="this.$store.state.lmAppUrlM + '/mobile/customercenter/customerShoppingGuide.do'"
               data-gtm="M009">
                이용안내
            </a>
            <a href="http://m.company.lottemart.com/bc/mobile/main/PbcmMobileMain0001.mdo"
               data-gtm="M009">
                회사소개
            </a>
        </nav>
        <div class="wrap-inner">
            <nav class="inform-nav-inner">
                <a :href="this.$store.state.lmAppUrlM +'/mobile/customercenter/viewHtmlCustomer.do?viewPath=guideUse'"
                   data-gtm="M009">
                    이용약관
                </a>
                <a :href="this.$store.state.lmAppUrlM + '/mobile/customercenter/viewHtmlCustomer.do?viewPath=guidePrivacy'"
                   class="point5"
                   data-gtm="M009">
                    개인정보처리방침
                </a>
                <a v-if="isSamsung !== 'Y' && isToysrus"
                   href="http://toysrus.lottemart.com/index.do"
                   target="_blank"
                   data-gtm="M009">
                    PC버전보기
                </a>
                <a v-if="isSamsung !== 'Y' && !isToysrus"
                   href="http://www.lottemart.com/index.do"
                   target="_blank"
                   data-gtm="M009">
                    PC버전보기
                </a>
                <a v-if="isSamsung !== 'Y' && !isLottemartApp"
                   id="footerAppDown">
                    앱 다운로드
                </a>
            </nav>
            <ul class="inform-list">
                <li>롯데쇼핑㈜ 롯데마트 사업본부</li>
                <li>대표이사 : 강희태</li>
                <li>주소 : 서울시 송파구 올림픽로 269(신천동 롯데캐슬 골드) 6,7층</li>
                <li>사업자 등록번호 : 215-85-13569</li>
                <li>통신판매업 신고 : <a href="http://www.ftc.go.kr/info/bizinfo/communicationViewPopup.jsp?wrkr_no=2158513569" target="_blank" data-gtm="M009">송파 제 3366호</a></li>
                <li>개인정보 관리 책임 및 청소년 보호 책임자 : 이준성</li>
                <li>호스팅 제공자 : 롯데쇼핑㈜ 롯데마트</li>
                <li><a href="mailto:onlinemall@lottemart.com" data-gtm="M009">onlinemall@lottemart.com</a></li>
                <li>롯데마트 매장문의 : <a href="tel:0221458000" data-gtm="M009">02-2145-8000</a> / <a :href="lmAppUrlM + '/mobile/voc/vocMain.do'" data-gtm="M009">고객의 소리</a></li>
            </ul>
        </div>
        <div v-if="isSamsung !== 'Y'"
             class="wrap-inner">
            <p class="title">롯데마트몰 고객센터</p>
            <nav class="wrap-button type2">
                <a href="#"
                   @click.prevent="callCC"
                   data-gtm="M009">
                    <i class="icon-common-phone"></i> 1577-2500
                </a>
                <a :href="this.$store.state.lmAppUrlM+'/mobile/talkCustomerservice.do'"
                   data-gtm="M009">
                    <i class="icon-common-talk"></i> 롯데마트몰 톡
                </a>
                <a :href="this.$store.state.lmAppUrlM+'/member/GroupEmpUpdate.do?STA=MO'"
                   data-gtm="M009">임직원 등록
                </a>
            </nav>
            <p class="desc">고객센터 이용시간 : 평일 09시~21시, 주말 09시~19시</p>
        </div>
        <div class="wrap-inner">
            <div class="wrap-serv-info">
                <p class="desc">고객님의 안전거래를 위해 현금으로 결제 시 <br>저희 쇼핑몰에서 가입한 소비자 피해보상 <br>서비스를 이용하실 수 있습니다.</p>
                <div class="wrap-btn">
                    <a href="https://www.usafe.co.kr/usafeShopView.asp?com_no=2158513569" class="btn-common-color3" target="_new" data-gtm="M009">서비스 가입 <br>사실 확인</a>
                </div>
            </div>
        </div>
        <div class="wrap-inner">
            <p class="copy">Copyright ⓒ LOTTEMART. All Rights Reserved.</p>
        </div>
        <div class="wrap-links">
            <a href="http://m.culture.lottemart.com/mw/mobile/main/main.mdo" data-gtm="M009">문화센터</a>
            <a href="http://m.company.lottemart.com/bc/mobile/main/PbcmMobileMain0001.mdo" data-gtm="M009">롯데마트 회사소개</a>
            <a href="http://m.company.lottemart.com/vc/main.do" data-gtm="M009">VIC Market</a>
        </div>
    </footer>
</template>
<script>
    import {mapGetters} from 'vuex';
    import {
        ROOT_IS_APP,
        ROOT_LOGIN_CHECK,
        ROOT_MEMBER_NO,
        ROOT_IS_SAMSUNG,
        ROOT_SHOW_FOOTER
    } from "../stores/types-root";

    const userAgent = navigator.userAgent;
    export default {
        name : 'FooterApp',
        methods : {
            goLogout,
            new_logins,
            callCC
        },
        computed : {
            isToysrus () {
                return userAgent.toUpperCase().indexOf('TOYSRUS.IPHONE.SHOPPING') !== -1 ||
                    userAgent.toUpperCase().indexOf('TOYSRUS.ANDROID.SHOPPING') !== -1;
            },
            ...mapGetters({
                isLottemartApp : ROOT_IS_APP,
                loginCheck : ROOT_LOGIN_CHECK,
                memberNo : ROOT_MEMBER_NO,
                isSamsung : ROOT_IS_SAMSUNG,
                showFooter : ROOT_SHOW_FOOTER
            })
        }
    }
</script>