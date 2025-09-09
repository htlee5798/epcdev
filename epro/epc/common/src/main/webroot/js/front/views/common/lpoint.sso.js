/** Client 라이브러리 초기화 */

var sso;
if(!(typeof SsoClientLibrary === 'undefined')){
	sso = new SsoClientLibrary({
	    ccoSiteNo: $.utils.config('SsoSiteNumber')
	    , clntAkInf: $.utils.config('SsoClientInfo')
	    , urEvnmtDc: $.utils.isMobile() ? ($.utils.config('onlinemallApp') ? '2' : '1') : '0'
	    , acesTkn: $.cookie(getTokenName($.utils.config('SsoSiteNumber')))
	    , vrblNm: 'sso'
	    , srnOpt: {opMd: '0', srnHeaderExpsYn: 'Y'}
	});
}

//if( !(typeof sso === 'undefined')) // lpoint 서버 정상인 경우 실행

var lpoint = {
    defaults: {
        aftPcMd: '0'
        , rturUrl: window.location.href
        , rturUrlCaloMethd: 'GET'
        , onlCstTpC : ''
        , frnYn : ''
        , callback: function (response) {
            console.log(response);
        }
    },
    ssoLogin: function (options) {
        options = optionsInitialize(options);
        options.akUrl = '/exBiz/login/ssoLogin_01_001';
        options = $.extend({}, lpoint.defaults, options || {});

        if ($.cookie('ssoToken')) {
            options.akDta = {ssoTkn: $.cookie('ssoToken')};
        }
        if( !(typeof sso === 'undefined')){
        	sso.callSsoLogin(options);
        }
    },
    autoLogin: function (options) {
        options = optionsInitialize(options);
        options.akUrl = '/exBiz/login/autoLogin_01_001';
        options = $.extend({}, lpoint.defaults, options || {});
        
        if ($.cookie('renewalTkn') && $.cookie('autoLgnRgDtti')) {
        	options.akDta = {rnwTkn: $.cookie('renewalTkn'), autoLgnRgDtti: $.cookie('autoLgnRgDtti')};
        }
        if( !(typeof sso === 'undefined')){
        	sso.callSsoLogin(options);
        }        
    },
    togetherSsoLogin : function (options, ssoTkn) {
    	options = optionsInitialize(options);
        options.akUrl = '/exBiz/login/ssoLogin_01_001';
        options = $.extend({}, lpoint.defaults, options || {});

        if (ssoTkn) {
    		options.akDta = {ssoTkn: urlObj.searchParams.get('ssoTkn')};
        } else if ($.cookie('ssoToken')) {
            options.akDta = {ssoTkn: $.cookie('ssoToken')};
        }
        if( !(typeof sso === 'undefined')){
        	sso.callSsoLogin(options);
        }
    },
    togetherGroupSsoLogin : function (options, ssoTkn, siteGrpNo) {
    	options = optionsInitialize(options);
        options.akUrl = '/exBiz/login/ssoGrpLogin_01_001';
        options = $.extend({}, lpoint.defaults, options || {});

        if (ssoTkn) {
    		options.akDta = {ssoTkn: ssoTkn, siteGrpNo: siteGrpNo};
        } else if ($.cookie('ssoToken')) {
            options.akDta = {ssoTkn: $.cookie('ssoToken'), siteGrpNo: siteGrpNo};
        }
        if( !(typeof sso === 'undefined')){
        	sso.callSsoGroupLogin(options);
        }
    },
    ItgPrvAgreement : function (options, ssoTkn, siteGrpNo) {
    	options = optionsInitialize(options);
    	options.akUrl = '/exView/join/itgPrvAg_01_001';
    	options = $.extend({}, lpoint.defaults, options || {});
    	options.akDta = {ssoTkn: ssoTkn
						, siteGrpNo : siteGrpNo
						};
    	if( !(typeof sso === 'undefined')){
    		sso.callScreen(options);
    	}
    },
    changePassword: function (options) {
        options = optionsInitialize(options);
        options.akUrl = '/view/manage/chPassword_01_000';
        options = $.extend({}, lpoint.defaults, options || {});
        if( !(typeof sso === 'undefined')){
        	sso.callScreen(options);
        }
    },
    changeInfo: function (options) {
        options = optionsInitialize(options);
        options.akUrl = '/view/manage/mbrManage_01_000';
        options = $.extend({}, lpoint.defaults, options || {});
        if( !(typeof sso === 'undefined')){
        	sso.callScreen(options);
        }
    },
    changePasswordCampaign: function (options) {
        options = optionsInitialize(options);
        options.akUrl = '/view/login/login_04_001';
        options = $.extend({}, lpoint.defaults, options || {});
        if( !(typeof sso === 'undefined')){
        	sso.callScreen(options);
        }
    },
    withdrawal: function (options) {
        options = optionsInitialize(options);
        options.akUrl = '/view/manage/mbrSes_01_001';
        options = $.extend({}, lpoint.defaults, options || {});
        if( !(typeof sso === 'undefined')){
        	sso.callScreen(options);
        }
    },
    join: function (options) {
        options = optionsInitialize(options);
        options.akUrl = '/exView/join/mbrJoin_01_001';
        options = $.extend({}, lpoint.defaults, options || {});
        if( !(typeof sso === 'undefined')){
        	sso.callScreen(options);
        }
    },
    termsAgreement : function (options, ssoTkn, onlCstTpC, frnYn, rspDtc) {
    	options = optionsInitialize(options);
    	options.akUrl = '/exView/join/mbrJoin_04_001';
    	options = $.extend({}, lpoint.defaults, options || {});
    	options.akDta = {ssoTkn: ssoTkn
						, onlCstTpC : onlCstTpC
						,frnYn : frnYn
						,rspDtc : rspDtc};
    	if( !(typeof sso === 'undefined')){
    		sso.callScreen(options);
    	}
    },
	//옴니서비스동의
    omniPrvAgreement : function (options) {
    	options = optionsInitialize(options);
    	options.akUrl = '/view/manage/omniPrvAg_01_001';
    	options = $.extend({}, lpoint.defaults, options || {});
    	if( !(typeof sso === 'undefined')){
    		sso.callScreen(options);
    	}
    },
	//유료회원가입
    subscriberJoin : function (options) {
    	options = optionsInitialize(options);
    	options.akUrl = '/view/chgMbr/chgMbrJoin_01_001';
    	options = $.extend({}, lpoint.defaults, options || {});
    	if( !(typeof sso === 'undefined')){
    		sso.callScreen(options);
    	}
    },
	//유료회원관리
    subscriberManage : function (options) {
    	options = optionsInitialize(options);
    	options.akUrl = '/view/chgMbr/chgMbrManage_01_001';
    	options = $.extend({}, lpoint.defaults, options || {});
    	if( !(typeof sso === 'undefined')){
    		sso.callScreen(options);
    	}
    }
};

function optionsInitialize(options) {
    if (typeof (options) === 'undefined') {
        options = {};
    }

    return options;
}

function getTokenName(ssoSiteNumber) {
    var clientInfo = {
        '1066': 'martToken',
        '1142': 'martshopToken',
        '1568': 'mmartshopToken',
        '1180': 'martccToken',
        '1241': 'mmartccToken'
    };

    return clientInfo[ssoSiteNumber];
}
