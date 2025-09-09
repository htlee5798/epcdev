/*******************************************************************************************/
/*									 				 		ADSSOM 컨버전 스크립트									 		
/** 
 * 전환(구매) 전체에 대한 데이터 입니다. 
 * 입력하지 않아도 되지만 많이 입력할수록 다양한 리포트 제공이 가능합니다. 
																																	    */
/******************************************************************************************/

/*************************************************/
/* 							function 										 */		
/*************************************************/

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

/* [ validation ] */
var _seedValueValidationCheck = function(val){
	var isValid = true;
	if(  typeof(val) === 'undefined' || val === 'undefined' || val === '' || val === null  ){
		isValid = false;
	}	
	return isValid;	
};

/*************************************************/
/* 							Analysis 										 */		
/*************************************************/

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
	
		if( _seedValueValidationCheck(conversionId) === true  ) {
			
			var regIsNum = /[^0-9]/gi;
			var regIsUnitNum = /[^0-9;]/gi;

			var _11h11mConv = {};

			  _11h11mConv['convTypeId'] =  ( _seedValueValidationCheck(conversionId) === true ) ? conversionId : ''  ; //컨버전 타입 ID : 관리사이트에서 발급된 고유 ID (필수입력값임. 수정하지 마세요) 
			  _11h11mConv['convKey'] =  ( _seedValueValidationCheck(uniqueNumber) === true ) ? uniqueNumber : ''  ; //컨버전 키 : 내부 시스템에서 사용하는 구매 ID 
			  _11h11mConv['convCa'] = ( _seedValueValidationCheck(amount) === true ) ? amount.replace(regIsNum,"") : ''  ; //매출 : 최종 구매 금액 (선택) 
			  
			  /** 
			  * 전환(구매) 개별대한 상세 데이터 입니다. 여러 상품을 판매했을 경우 ; 로 구분해서 입력하시면 됩니다. 
			  * 입력하지 않아도 되지만 많이 입력할수록 다양한 리포트 제공이 가능합니다. 
			  */ 
			  _11h11mConv['convDetName'] = ( _seedValueValidationCheck(itemNames) === true ) ? _seedEscapeStr(itemNames)  : ''  ; //컨버전 상세 명 : 컨버전(상품)의 개별 이름을 입력 
			  _11h11mConv['convDetCost'] = ( _seedValueValidationCheck(itemPrices) === true ) ? itemPrices.replace(regIsUnitNum,"")   : ''  ; //컨버전 상세 금액 : 컨버전(상품)의 개별 단가(1개 상품 금액)를 입력 
			  _11h11mConv['convDetCnt'] = ( _seedValueValidationCheck(quantity) === true ) ? quantity.replace(regIsUnitNum,"")   : ''  ; //컨버전 상세 수량 : 컨버전(상품)의 개별 판매 수량 
			  _11h11mConv['convDetCategory'] = ( _seedValueValidationCheck(category) === true ) ? _seedEscapeStr(category) : ''  ; //컨버전 상세 카테고리 : 컨버전(상품)의 개별 카테고리를 입력 
			  
			  try { _11h11m.log(_11h11mConv,'conv'); } catch(e) {} //로그를 전송합는 코드입니다. (수정하지 마세요)	
		}
};

/*************************************************/
/* 							Execute 										 */		
/*************************************************/
/* Execute Seed Script */
var _execute_seed_script = function(){	
	try { _seedConversionAnalysisEvent(); }catch(e){}	
}

_execute_seed_script();
