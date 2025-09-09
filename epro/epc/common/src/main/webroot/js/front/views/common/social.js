"use strict";
  
/*var script=document.createElement("script");
script.src = "/js/front/views/mobile/facebookApi.js";//포함시킬 js 파일 (경로가 존재하면 경로까지 작성)
document.getElementsByTagName("head")[0].appendChild(script);*/
window.fbAsyncInit = function() {
   FB.init({
     appId      : '739454589522567',
     status     : true,          // 로그인 상태를 확인
     cookie     : true,          // 쿠키허용
     xfbml      : true,
     version    : 'v2.8'
   });
};	

(function(d, s, id){
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/ko_KR/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));

/**
 * 소셜서비스를 통한 URL 공유 관련 API
 * @required developers.kakao.com/sdk/js/kakao.min.js
 */
try {
	Kakao.init(_KAKAO_APP_KEY_);
}
catch ( e ) {}


var socialLink = {
	// Facebook
	facebookLogin: function(url, img_src, title){
		window.location = "https://www.facebook.com/dialog/feed?app_id=739454589522567&display=popup&picture="+ encodeURIComponent(img_src) +"&title="+ title +"&description=롯데마트몰&link="+ encodeURIComponent(url) +"&redirect_uri="+ encodeURIComponent(url);
	},
	//ios에서 FB.getLoginStatus가 먹히지 않는 현상으로 인하여 사용하지 않는 상태
	facebook: function(url, img_src, title) {
		FB.ui({
			 method: 'share', 
			 mobile_iframe: true,
			 href: url,
			 picture: img_src,
			 title: title,
			 description: '롯데마트몰'
		},
		function(response) {
			console.log(response);
		});
	},
	
	// Twitter
	twitter: function(url, text) {
		/*var socialUrl = "https://twitter.com/share?";
		var params = {
			url: encodeURIComponent(url),
			text: encodeURIComponent(text)
		};
		window.open(socialUrl + $.param(params), "_balnk");*/
		window.open("https://twitter.com/intent/tweet?url=" + encodeURIComponent(url) + "&text=" + encodeURIComponent(text)).focus();
	},
	
	// 카카오스토리
	kakaoStory: function(url, text) {
		// 카카오 모듈이 없을 때
		if ( typeof(Kakao) === "undefined" ) {
			// TODO 카카오 모듈 load
			return;
		}
		
		Kakao.Story.share({
			url: url,
			text: text
		});
	},
	
	kakaoTalk: function(labels, img_src, url) {
		
		try {
			Kakao.init(_KAKAO_APP_KEY_);
		}
		catch ( e ) {}
		
		// 카카오 모듈이 없을 때
		if ( typeof(Kakao) === "undefined" ) {
			// TODO 카카오 모듈 load
			return;
		}

		Kakao.Link.sendTalkLink({
		  label: labels,
		  image: {
			    src: img_src,
			    width: '640',
			    height: '460'
			  },
		  webLink: {  
			text:'웹으로 연결',
			url:url
		  },
		  appButton: {
		    text: '앱으로 연결',
		    execParams:{iphone:{UrlLink:url},android:{UrlLink:url}}
		  }
		});
	}
	
};