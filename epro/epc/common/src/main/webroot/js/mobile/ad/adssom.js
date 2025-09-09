/*******************************************************************************************/
/*									 				 		 설치 가이드 										 		

### 공통 스크립트 삽입 방법 ###

1) 첨부해 드린 adssom.js 파일을 URL로 접근가능한 서버 어느 위치에 업로드 합니다.

2) 사이트 모든 페이지에 "공통적으로 들어가는 페이지"에 아래와 같은 형태로 삽입하시면 됩니다. 
   (일반적으로 하단 Copyright 페이지에 삽입 합니다.)

3) 가능하면 스크립트 실행이 원할이 될수 있도록 <body></body> 사이에 삽입해 주시면 감사 하겠습니다.

<!------------- 여기부터 복사 ------------------------>
<script type="text/javascript" src="/파일이업로드된서버URL/adssom.js"></script>
<!------------- 여기 까지 복사 ------------------------>
																																	    */
/******************************************************************************************/


/*************************************************/
/* 								config 										 */		
/*************************************************/
var _SEED_COOKIE_EXPIRE_DAY = "30"; 	//Day
var _SEED_SITE_ID = "384"; 
// If you fill a place, use the sub-domain.
var _SEED_SUB_DOMAIN = "lottemart.com"; 

/*************************************************/
/* 							constant 										 */		
/*************************************************/

var _SEED_SCRIPT_VERSION = "1.3";
var _SEED_SERVER = "collect.adssom.co.kr";


var _SEED_URL_DELIMETERS = [ "SEEDID", "NVSID", "DUSID", "NVADID", "DMKW","n_keyword_id" ];

var _SEED_VI_COOKIE_NAME = "_s_vi" + _SEED_SITE_ID; 
var _SEED_ISAD_COOKIE_NAME = "_s_ad" + _SEED_SITE_ID; 
var _SEED_ARRAY_COOKIE_NAME = "_s_ar" + _SEED_SITE_ID;

var _SEED_NO_AD_VISIT_SERVER_URL = "/seedNoAdVisit.do";
var _SEED_VISIT_SERVER_URL = "/seedVisit.do";
var _SEED_CONV_SERVER_URL = "/seedConv.do";

var  _SEED_VISITOR_IDX = 0 ;
var  _SEED_BEFORE_KEY_IDX = 1 ;
var  _SEED_CURRENT_KEY_IDX = 2 ;
var  _SEED_BEFORE_PARAM_TYPE_IDX = 3;
var  _SEED_CURRENT_PARAM_TYPE_IDX = 4;
var  _SEED_LAST_AD_VISIT_IDX = 5;
var  _SEED_BEFORE_KEYWORD_IDX= 6;
var  _SEED_CURRENT_KEYWORD_IDX = 7;

var _DAUM_DELIMETER = "DMKW" ;

/*************************************************/
/* 							function 										 */		
/*************************************************/

/* [ key generate ]  */                          					         
var _seedGenerateKey = function(){
	var requiredIDLength = 32;
	var uniqueID = "";
	
	var _generateKey = function(str, maxLength){		
		if( _seedValueValidationCheck(str) === false )  str = ""; 		
		while(str.length<maxLength) {
			var randomNumber = Math.floor(Math.random()*9);			
			str=""+str+randomNumber+"";
		}		
		return str;
	};
	
	try {
		uniqueID=_seedGetTimeValue();
		var siteIDCount = 0;
		for(var i=0;i<_SEED_SITE_ID.length;i++){
			var tmp = _SEED_SITE_ID.charCodeAt(i);
			siteIDCount += tmp;
		}
		
		uniqueID += siteIDCount;

		if( uniqueID.length <= requiredIDLength ){			
			uniqueID = _generateKey(uniqueID, requiredIDLength);
		}else{
			var diff = uniqueID.length - requiredIDLength;
			uniqueID = uniqueID.substring(0,uniqueID.length-diff);
		}
	}catch(e){
		uniqueID = "";
		uniqueID = _generateKey(uniqueID, requiredIDLength);		
	}	
	return uniqueID;	
};

/* [ get time value  ] */
var _seedGetTimeValue = function(){
	return (new Date().getTime()).toFixed();
};


/* [ get parameter value  ] */
var _seedGetParameterValue = function(parameters){
	
	var _getParameterValueByRegex = function(paraName){
		paraName = paraName.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");	
		var regexS = "[\\?&]"+paraName+"=([^&#]*)";
			var regex = new RegExp( regexS );
			var results = regex.exec( window.location.href );
			if( results == null ) return "";
			else return results[1];	
	};

	var existsParam = false;
	var returnParameterValue="";
	var returnParamCode=-1;
	var result=null;
	
	for(var i=0; i<parameters.length; i++){		
		var paraName = parameters[i];
		returnParameterValue = _getParameterValueByRegex(paraName);		
		if( "" != returnParameterValue )  {			
			result = [returnParameterValue,  paraName];
			break;
		}		
	}	
	
	return result;
};


/* [ Get expired minute (unused) ] */
var _seedGetExpiredMinute = function(expiredMinute){
	var currentDateTime = new Date(); 
	currentDateTime.setTime(  currentDateTime.getTime() + (expiredMinute*60*1000) );		
	return currentDateTime;
};

/* [ Get expired date   ] */
var _seedGetExpiredDate = function(expiredDate){
	var expiredMinute = expiredDate*24*60;
	return _seedGetExpiredMinute(expiredMinute);
};

/* [ Set Cookie  ] */
var _seedSetCookie = function(_key,_value,_expiredMinute){	
	var expiresStr = ""; 
	var subDomain = "";		
	
	if ( _seedValueValidationCheck(_SEED_SUB_DOMAIN) ){		
		subDomain="; domain="+_SEED_SUB_DOMAIN;
	}
	
	if( _seedValueValidationCheck(_expiredMinute) ){						
		expiresStr="; expires=" + _expiredMinute.toGMTString();
	}		
	document.cookie = _key + "=" + escape(_value) + expiresStr + ";path=/" + subDomain;	
};

/* [ Get Cookie  ] */
var _seedGetCookie = function(_key){	
	var name = _key + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) === 0) return c.substring(name.length,c.length);
    }
    return "";
};

/* [ Encoding  ] */
var _seedEscapeStr = function(str){	
	var bEncURI = "N";		
	try{bEncURI=encodeURIComponent('O');}catch(_e){}		
	
	if( bEncURI == "O" ) str=encodeURIComponent(str);
	else {
		str = escape(str);	
		str=str.split("+").join("%2B");
		str=str.split("/").join("%2F");
		str=str.split("&").join("%26");
		str=str.split("?").join("%3F");
		str=str.split(":").join("%3A");
		str=str.split("#").join("%23");
	}	
	return str;
};

/* [ Decoding  ] */
var _seedUnescapeStr = function(str){	
	var bDeURI = "N";	
	try{bDeURI=decodeURIComponent('O');}catch(_e){ }	
	if( bDeURI == "O" ) str=decodeURIComponent(str);
	else {
		str = unescape(str);		
	}	
	return str;
};


/* [ Get array type cookie ] */
var _seedGetArrayTypeCookie = function (ordinal){	
	var cookieValue = _seedUnescapeStr(_seedGetCookie(_SEED_ARRAY_COOKIE_NAME));
	
	var items  =  cookieValue.split(",");
	
	if ( (typeof items[ordinal]) == "undefined"  ){	
		return "";
	}else{		
		return items[ordinal];
	}
};

/* [ validation ] */
var _seedValueValidationCheck = function(val){
	var isValid = true;
	if(  typeof(val) === 'undefined' || val === 'undefined' || val === '' || val === null  ){
		isValid = false;
	}	
	return isValid;	
};

/* [ NVADID Refine ] */
var _seedNvAdIdRefine = function(val){
	val = val.split("+");
	return val[0];
};

/* [ URL Reducation ] */
var _seedReducationUrl = function(val){
	try{
		return val.substring(0,100);
	}catch(e){
		return "";
	}
}

/* [ send ] */
var _seedSendData = function (beforeAdKeyword, currentAdKeyword, visitID, visitorID, currentParamType, beforeParamType , currentAdKey, beforeAdKey, sendData, collectPage){

	var paramBeforeAdKeyword = "&preKwd=" + beforeAdKeyword;	
	var paramCurrentAdKeyword = "&curKwd=" + currentAdKeyword;	

	var paramVisitID = "&visitId=" + visitID;	
	var paramVisitorID = "&visitorId=" + visitorID;
	var paramCurrentAdKey = "&curKwdId=" + currentAdKey;
	var paramBeforeAdKey = "&preKwdId=" + beforeAdKey;
	var paramCurrentParamType = "&curKwdIdType=" + currentParamType;
	var paramBeforeParamType = "&preKwdIdType=" + beforeParamType;
	var basicInfo = paramVisitID + paramVisitorID + paramCurrentAdKey + paramBeforeAdKey + paramCurrentParamType + paramBeforeParamType + paramBeforeAdKeyword + paramCurrentAdKeyword;
	
	var siteID = "?siteId=" + _seedEscapeStr(_SEED_SITE_ID);	
	
	var scriptVer = "&scriptVer=" + _SEED_SCRIPT_VERSION;
	
	var referrerPage = self.document.referrer;
	referrerPage = _seedReducationUrl(referrerPage);	
	referrerPage = (referrerPage == "undefined" || ! referrerPage ) ? referrerPage="":"&prePageFullUrl=" +  _seedEscapeStr(referrerPage);
	
	var currentPage = self.document.location.href;
	currentPage = _seedReducationUrl(currentPage);
	currentPage = (currentPage.substr(0,4) == "file" || ! currentPage ) ? currentPage="":"&curPageFullUrl=" +  _seedEscapeStr(currentPage);
	
	var protocol = currentPage.indexOf("https") != -1?"https://":"http://";

	var returnUrl  =  protocol + _SEED_SERVER + collectPage + siteID + basicInfo  + referrerPage + currentPage + scriptVer + sendData;
	
	var imageObject = new Image();
	imageObject.src = returnUrl;	
};




/*************************************************/
/* 							Analysis 										 */		
/*************************************************/

/* [ Analysis Etc Visit  ] */
var _seedEtcVisitAnalysis = function(){			
	var visitID = _seedGetCookie(_SEED_VI_COOKIE_NAME);	
	if ( _seedValueValidationCheck(visitID) === false ){
		visitID = "noAd";
		_seedSetCookie(_SEED_VI_COOKIE_NAME,visitID, "" ); 
		_seedSetCookie(_SEED_ISAD_COOKIE_NAME,0, "" );
		_seedSendData(visitID,"" ,"" , "", "", "","" , "", "",_SEED_NO_AD_VISIT_SERVER_URL );
	}	
};


/* [ Analysis visit ] */
var _seedVisitAnalysis = function(){		

		var param = _seedGetParameterValue(_SEED_URL_DELIMETERS);		
		
		//Is there a url to the seed parameter?		
		if( _seedValueValidationCheck(param) === true ){						
			
			var currentAdKey = param[0]; //parameter escape
			var currentParamType = param[1];
			var currentAdKeyword = "";
			
			//is daum parameter
			if( currentParamType === _DAUM_DELIMETER  ) {
				currentAdKeyword = _seedEscapeStr( currentAdKey );
				currentAdKey = "";				
			}else{
				currentAdKey = _seedNvAdIdRefine(currentAdKey); 
			}
			
			//Visit ID issued
			var visitID = _seedGenerateKey();
			var visitorID = _seedGetArrayTypeCookie(_SEED_VISITOR_IDX);
			
			var currentCookieKeyId = _seedGetArrayTypeCookie(_SEED_CURRENT_KEY_IDX);
			var currentCookieParamType = _seedGetArrayTypeCookie(_SEED_CURRENT_PARAM_TYPE_IDX);			
			var currentCookieKeyword = _seedGetArrayTypeCookie(_SEED_CURRENT_KEYWORD_IDX);
			
			//Visitor ID value does exist?						
			if( _seedValueValidationCheck(visitorID) === false ) {
				//Visitor ID issued
				visitorID = visitID;				
			}
			
			var beforeAdKey = currentCookieKeyId;
			var beforeParamType = currentCookieParamType;
			var beforeAdKeyword = currentCookieKeyword;
			
			var cookieArray=[];
			cookieArray[_SEED_VISITOR_IDX] = visitorID;
			
			cookieArray[_SEED_BEFORE_KEY_IDX] = beforeAdKey;
			cookieArray[_SEED_BEFORE_KEYWORD_IDX] = beforeAdKeyword;
			cookieArray[_SEED_BEFORE_PARAM_TYPE_IDX] = beforeParamType;
			
			cookieArray[_SEED_CURRENT_KEY_IDX] = currentAdKey;
			cookieArray[_SEED_CURRENT_KEYWORD_IDX] = currentAdKeyword;			
			cookieArray[_SEED_CURRENT_PARAM_TYPE_IDX] = currentParamType;
		
			cookieArray[_SEED_LAST_AD_VISIT_IDX] = _seedGetTimeValue();
			
			_seedSetCookie(_SEED_VI_COOKIE_NAME,visitID, "" ); 
			_seedSetCookie(_SEED_ISAD_COOKIE_NAME,1, "" );
			_seedSetCookie(_SEED_ARRAY_COOKIE_NAME, cookieArray, _seedGetExpiredDate(_SEED_COOKIE_EXPIRE_DAY));

			
			var NVRANKID = ["NVADRANK"];
			var NEWNVRANKID = ["n_rank"];
			var paramClickRank = "";			
			var clickRankParam = _seedGetParameterValue(NVRANKID);
			var clickNewRankParam = _seedGetParameterValue(NEWNVRANKID);
			if( _seedValueValidationCheck(clickRankParam) === true ){
				paramClickRank = "&clickRank="+clickRankParam[0];
			}						
			if( _seedValueValidationCheck(clickNewRankParam) === true ){
				paramClickRank = "&clickRank="+clickNewRankParam[0];
			}						
			var sendData = paramClickRank;
						
			_seedSendData(beforeAdKeyword, currentAdKeyword,   visitID, visitorID, currentParamType, beforeParamType  , currentAdKey, beforeAdKey, sendData , _SEED_VISIT_SERVER_URL);
			
		}else{
			_seedEtcVisitAnalysis();
		}
};

/* [ Analysis conversion (button process) ] */
var _seedConversionAnalysisButtonEvent = function(conversionId,  uniqueNumber, amount, itemNames, itemPrices,  quantity, category, etc, itemCode ){
	if( _seedValueValidationCheck(conversionId) === true  ){
		_seedConversionAnalysis(conversionId);
	}	
};

/* [ Analysis conversion (general process) ] */
var _seedConversionAnalysisEvent = function(){	
	//Is there an seed conversion id on this page?		
	if(  _seedValueValidationCheck(_SEED_CID) === true  ){		
		var un = ""; try { un = _SEED_UN }catch(e){}; 
		var amt = ""; try { amt = _SEED_AMT }catch(e){};
		var iname = ""; try { iname = _SEED_INAME }catch(e){}; 
		var iprice = ""; try { iprice = _SEED_IPRICE }catch(e){};
		var qty = ""; try { qty = _SEED_QTY }catch(e){};
		var ct = ""; try { ct = _SEED_CT }catch(e){};
		var etc = ""; try { etc = _SEED_ETC }catch(e){};
		var icode = ""; try { icode = _SEED_ICODE }catch(e){};		
		_seedConversionAnalysis(_SEED_CID, un, amt, iname, iprice,  qty, ct, etc, icode );
	}
};

/* [ Analysis conversion (common process) ] */
var _seedConversionAnalysis = function(conversionId,  uniqueNumber, amount, itemNames, itemPrices,  quantity, category, etc, itemCode ){	
	
		var visitorID = _seedGetArrayTypeCookie(_SEED_VISITOR_IDX); 
		
		if( _seedValueValidationCheck(conversionId) === true && _seedValueValidationCheck(visitorID) === true ) {
			
			var visitID = _seedGetCookie(_SEED_VI_COOKIE_NAME);
			visitID = ( "noAd" === visitID?"":visitID   ) ;
					
			var currentCookieKeyId = _seedGetArrayTypeCookie(_SEED_CURRENT_KEY_IDX);
			var beforeCookieKeyId = _seedGetArrayTypeCookie(_SEED_BEFORE_KEY_IDX);
			
			var currentCookieParamType = _seedGetArrayTypeCookie(_SEED_CURRENT_PARAM_TYPE_IDX);
			var beforeCookieParamType = _seedGetArrayTypeCookie(_SEED_BEFORE_PARAM_TYPE_IDX);
			
			var currentCookieKeyword = _seedGetArrayTypeCookie(_SEED_CURRENT_KEYWORD_IDX);
			var beforeCookieKeyword = _seedGetArrayTypeCookie(_SEED_BEFORE_KEYWORD_IDX);
			
			var currentAdKeyId = '';
			var beforeAdKeyId = '';
			
			var currentAdKeyword = '';
			var beforeAdKeyword = '';
			
			var currentAdKeyType = '';
			var beforeAdKeyType = '';
			
			if( _seedValueValidationCheck(visitID) === true ){
				//Direct conversion
				currentAdKeyId = currentCookieKeyId;
				currentAdKeyword = currentCookieKeyword;
				currentAdKeyType = currentCookieParamType;
				
				beforeAdKeyId = beforeCookieKeyId;
				beforeAdKeyword = beforeCookieKeyword;
				beforeAdKeyType = beforeCookieParamType;
			}else{
				//Indirect conversion
				visitID = _seedGenerateKey();
				beforeAdKeyId = currentCookieKeyId;				
				beforeAdKeyword = currentCookieKeyword;
				beforeAdKeyType = currentCookieParamType;
			}
			
			var lastAdVisitDiff = 0;
			var lavdTmp = _seedGetArrayTypeCookie( _SEED_LAST_AD_VISIT_IDX );
			if(_seedValueValidationCheck(lavdTmp) === true ){
				lastAdVisitDiff = Math.round((  _seedGetTimeValue()  - lavdTmp)/1000);
			}
			
			var regIsNum = /[^0-9]/gi;
			var regIsUnitNum = /[^0-9;]/gi;
			
			var paramConversionID = ( _seedValueValidationCheck(conversionId) === true ) ? "&convMngId=" +  conversionId : ''  ;
			var paramUniqueNumber =  ( _seedValueValidationCheck(uniqueNumber) === true ) ? "&conversionUniqueNumber=" +  uniqueNumber : ''  ;
			var paramAmount = ( _seedValueValidationCheck(amount) === true ) ?  "&amount=" + amount.replace(regIsNum,"") : ''  ;
			var paramItemNames = ( _seedValueValidationCheck(itemNames) === true ) ?  "&itemName=" + _seedEscapeStr(itemNames)  : ''  ;			
			var paramItemPrices = ( _seedValueValidationCheck(itemPrices) === true ) ?  "&itemPrice=" + itemPrices.replace(regIsUnitNum,"")   : ''  ;			
			var paramQuantity = ( _seedValueValidationCheck(quantity) === true ) ?  "&itemQuantity=" + quantity.replace(regIsUnitNum,"")   : ''  ;
			var paramCategory = ( _seedValueValidationCheck(category) === true ) ?  "&itemCategory=" + _seedEscapeStr(category) : ''  ;
			var paramItemCode = ( _seedValueValidationCheck(itemCode) === true ) ?  "&itemCode=" + _seedEscapeStr(itemCode) : ''  ;
			
			var paramEtc = ( _seedValueValidationCheck(etc) === true ) ?  "&etc=" + _seedEscapeStr(etc) : ''  ;
			var paramLastAdVisitDiff  = "&lastAdVisitDiff=" + lastAdVisitDiff;
			
			var sendData = paramConversionID + paramUniqueNumber + paramAmount + paramItemNames + paramItemPrices + paramQuantity + paramCategory + paramEtc+paramLastAdVisitDiff;
			
			_seedSendData(beforeAdKeyword, currentAdKeyword,  visitID, visitorID, currentAdKeyType, beforeAdKeyType , currentAdKeyId, beforeAdKeyId, sendData, _SEED_CONV_SERVER_URL );			
		}
};

/*************************************************/
/* 							Execute 										 */		
/*************************************************/
/* Execute Seed Script */
var _execute_seed_script = function(){	
	try { _seedVisitAnalysis(); }catch(e){} 		
	try { _seedConversionAnalysisEvent(); }catch(e){}	
}

_execute_seed_script();
