<template>
    <a href="#"
       @click.stop.prevent="onLogOut">
        <template v-if="memberNo">
            <i class="icon-category-relate6">비회원 로그아웃</i>비회원 로그아웃
        </template>
        <template v-else>
            <i class="icon-category-relate6">로그아웃</i>로그아웃
        </template>
    </a>
</template>

<script>
    import $ from 'jquery';
    import {mapGetters} from 'vuex';
    import {
        ROOT_MEMBER_YN,
        ROOT_MEMBER_NO
    } from "../stores/types-root";

    export default {
        name: "LogoutButton",
        props : {
          type : {
              default : 'category'
          }
        },
        data () {
            return {
                gaOptions : {
                    category : {
                        quest: {
                            eventCategory: '롯데마트몰 - easy & slow | 17-2 | 비회원 로그아웃',
                            eventAction: '카테고리 푸터 클릭',
                            eventLabel: '버튼 클릭'
                        },
                        member: {
                            eventCategory: '#몰구분=Mart #기기=M #분류=카테고리 #페이지명=롯데마트몰 - easy & slow life',
                            eventAction: '카테고리 영역 클릭',
                            eventLabel: '#Depth=1 #콘텐츠=하단배너 #카테고리명=로그아웃'
                        }
                    }
                }
            }
        },
        computed : {
            ...mapGetters({
                memberYn : ROOT_MEMBER_YN,
                memberNo: ROOT_MEMBER_NO
            }),
            userType () {
                return this.memberNo ? 'guest' : 'member';
            }
        },
        methods : {
            onLogOut () {
                if(window.ga) {
                    window.ga('send', 'event', this.gaOptions[this.type][this.userType])
                }

                if(this.memberYn === 'true') {
                    if (!confirm('로그아웃 하시겠습니까?')) {
                        return;
                    }
                }

                let autoLoginCookie = $.cookie('autoLogin');
                let userAgent = navigator.userAgent;
                let outRetURL = '';
                let path = '';

                if(autoLoginCookie === 'Y') {
                    $.removeCookie("autoLoginId");
                    $.removeCookie("autoLogin");
                    $.removeCookie("atlgdate");

                    // TODO 황성운 : 자동로그인 로그 생성 원인 분석 후 삭제
                    let domain = location.hostname;
                    $.removeCookie("isAutoLogin", {domain : domain, path : '/'});
                    $.removeCookie("autoLoginMemberId", {domain : domain, path : '/'});
                }

                if(userAgent.indexOf('toysrus.iphone.shopping') !== -1
                    || userAgent.indexOf('toysrus.android.shopping') !== -1) {
                    outRetURL = 'http://m.toysrus.lottemart.com/mobile/main.do';
                }

                if(this.lpointServiceYn === 'Y') {
                    path = "http://www.lottemart.com/imember/login/ssoLogoutPop.do?sid=MMARTSHOP";
                } else {
                    let paramsForMart = autoLoginCookie === 'Y' ? 'mart=Y' : '';
                    path = `http://www.lottemart.com/imember/login/ssoLogoutPop.do?${paramsForMart}&sid=MMARTSHOP`;
                }

                location.replace(`${path}&returnurl=${outRetURL}`);
            }
        }
    }
</script>

<style scoped></style>