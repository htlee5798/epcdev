/*************************************************/
/* [DataLake] 의 일환으로써, 7개사(닷컴,엘롯데,슈퍼,롭스,마트,홈쇼핑,하이마트) 사이트를 
 * 방문하는 *[고객들의 행동로그(웹로그)]를 직접 모아 통합분석, 인사이트 도출, 활용 등을 하고자 함. 
 * 자체적으로 아래의 수집스크립트를 롯데마트 온라인 몰에 삽입 후 수집,가공 및 저장하고자 추가된 파일임.
 * 
 * 아래의 스크립트로 롯데마트 온라인 몰의 고객들의 행동로그를 수집 함. :: 20181212
*/		
/*************************************************/

//uniq 난수 발생
function getRandomKey() {
	var nowDate = new Date();
	return nowDate.getTime() + "_" + Math.floor(Math.random() * 99999);
}


function sendLog(){
	
	var strURL = location.href.split('?'); 
	var nowDate = new Date(),
      data = {
			userUniqKey: getRandomKey(), //고객고유식별키(자체발행)
			chlno: getParam("AFFILIATE_ID"), // 파라미터 채널 값
			chldtlno: getParam("CHANNEL_CD"), // 파라미터 채널상세 값
			goods_no: getParam("ProductCD"), // 상품번호
			siteloc: getParam("SITELOC"), // tracking(페이지별)
			tracking_no: getParam("pdivnSeq"), // tracking
			spdp_no: getParam("MkdpSeq"), // 기획전번호 
			disp_no: getParam("CategoryID"), // 카테고리번호
	  		query: decodeURIComponent(getParam("searchTerm")), //검색어
			page: strURL[0], // 현재 페이지 경로
			datetime: // 수집된 시간
				nowDate.getFullYear() + "-" 
				+ (nowDate.getMonth() + 1) + "-" 
				+ nowDate.getDate() + ":"
				+ nowDate.getHours() + ":"
				+ nowDate.getMinutes() + ":"
				+ nowDate.getSeconds(),
			mbrno: $("input[name=memberno]").val()||"", // 회원번호
			siteno: "43206", // 사이트 번호
			gid: getCookie("_gid")||"", // GA 쿠키식별값
			members_key: getCookie("MEMBERS_KEY"), // 멤버스키암호화값
          browserType: navigator.userAgent.match(/iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson|LG|SAMSUNG/i) ? "Mobile" : "PC" // 브라우저 타입
      };

	  // User Uniq Key Check
	  if (!getCookie("lotte40UniqUserKey")) {
	      setCookie("lotte40UniqUserKey", data.userUniqKey, 365, "/", "lottemart.com");
	  } else {
	      data.userUniqKey = getCookie("lotte40UniqUserKey");
	  }
	
	  callTrafficCollection(data); // 수집 데이터 전송
}
function callTrafficCollection(sendData){
	
	var formData = 'params=' + encodeURIComponent(encodeURIComponent(JSON.stringify(sendData)));
	
	$.ajax({
		url: "//analytics.lotte.com/save2",
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		crossDomain: true,
		data:  formData,
		method: "post",
		cache: false,
		success: function(html) {
//			console.log(html)
		}
	});
}

//파라미터 값 가져오기
function getParam(name) {
	var vars = {};
	var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
		vars[key] = value;
	});

	return vars[name] ? vars[name] : "";
}

//쿠키 읽기
function getCookie(c_name) {
	var i, x, y, ARRcookies = document.cookie.split(";");

	for (i = 0; i < ARRcookies.length; i++) {
		x = ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
		y = ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
		x = x.replace(/^\s+|\s+$/g,"");

		if (x == c_name) {
			return unescape(y);
		}
	}
}

//쿠키 생성
function setCookie(name, value, expires, path, domain, secure) {
	var today = new Date();
	today.setTime( today.getTime() );

	if (expires) { // expire 일단위
		expires = expires * 1000 * 60 * 60 * 24;
	}

	var expires_date = new Date(today.getTime() + expires);

	document.cookie = name + "=" + escape(value) +
		(expires ? ";expires=" + expires_date.toGMTString() : "") +
		(path ? ";path=" + path : "") +
		(domain ? ";domain=" + domain : "lottemart.com") +
		(secure ? ";secure" : "");
}


/* 
 * 주문 완료 시 호출 위한 함수
 */
function ds_call(itemId){    
	// 수집 데이터 세팅
	var ord_goods_no = "";
	ord_goods_no = itemId;    /////주문상품 
   
	var strURL = location.href.split('?'); 
	var nowDate = new Date(),
		data = {
			userUniqKey: getRandomKey(), //고객고유식별키(자체발행)
			chlno: getParam("AFFILIATE_ID"), // 파라미터 채널 값
			chldtlno: getParam("CHANNEL_CD"), // 파라미터 채널상세 값
			ord_goods_no: ord_goods_no, // 주문상품번호
			siteloc: getParam("SITELOC"), // tracking(페이지별)
			tracking_no: getParam("pdivnSeq"), // tracking
			spdp_no: getParam("MkdpSeq"), // 기획전번호 
			disp_no: getParam("CategoryID"), // 카테고리번호
      		query: decodeURIComponent(getParam("searchTerm")), //검색어
			page: strURL[0], // 현재 페이지 경로
			datetime: // 수집된 시간
				nowDate.getFullYear() + "-" 
				+ (nowDate.getMonth() + 1) + "-" 
				+ nowDate.getDate() + ":"
				+ nowDate.getHours() + ":"
				+ nowDate.getMinutes() + ":"
				+ nowDate.getSeconds(),
			orderId: getParam("orderId"), // 주문번호
			mbrno: $("input[name=memberno]").val()||"", // 회원번호
			siteno: "43206", // 사이트 번호
			gid: getCookie("_gid"), // GA 쿠키식별값
			members_key: getCookie("MEMBERS_KEY"), // 멤버스키암호화값
			browserType: navigator.userAgent.match(/iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson|LG|SAMSUNG/i) ? "Mobile" : "PC" // 브라우저 타입
		};  

	// User Uniq Key Check
	if (!getCookie("lotte40UniqUserKey")) {
		setCookie("lotte40UniqUserKey", data.userUniqKey, 365, "/", "lottemart.com");
	} else {
		data.userUniqKey = getCookie("lotte40UniqUserKey");
	}

	callTrafficCollection(data); // 수집 데이터 전송
	
} //주문완료 시 호출되서 동작

//수집서버에 데이터 전달
function callTrafficCollection_back(data) {

	try{
		// get new XHR object
		var newXHR;
		if (window.XMLHttpRequest) { // 모질라, 사파리등 그외 브라우저, ...
			newXHR = new XMLHttpRequest();
		} else if (window.ActiveXObject) { // IE 8 이상
			newXHR = new ActiveXObject("Microsoft.XMLHTTP");
		}
		  
		newXHR.open( 'POST', '//analytics.lotte.com/save2' );
		newXHR.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
		  
		newXHR.onreadystatechange = function (oEvent) {  
//			alert(oEvent)
		};
		
		var formData = 'params=' + encodeURIComponent(encodeURIComponent(JSON.stringify(data)));
		// send it off
		newXHR.send( formData );
	}catch(e){}

}

sendLog();