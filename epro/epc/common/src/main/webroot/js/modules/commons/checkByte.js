(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.checkByte = factory();
	}
}( window, function() {
	var _checkByte = function( input, max, targetName ) {
		var str = input.value; 
		var strLength = str.length;		// 전체길이
		
		//변수초기화
		var inputByte = 0;				// 한글일 경우 2, 그 외 글자는 1을 더함
		var charLength = 0;				// substring하기 위해 사용
		var oneChar = "";				// 한글자씩 검사
		var strTemp = "";				// 글자수를 초과하면 제한한 글자전까지만 보여줌.
		
		for(var i=0; i< strLength; i++) {
			oneChar = str.charAt(i);	// 한 글자 추출
			if (escape(oneChar).length > 4) { 
				inputByte +=2;			// 한글이면 2를 더한다
			} else {
				inputByte++;			// 한글이 아니면 1을 다한다
			}
		    
			if (inputByte <= max) {
				charLength = i + 1;
			}
		}

		// 글자수 초과
		if (inputByte > max) {
			alert( max + "바이트를 초과 입력할수 없습니다.");
			strTemp = str.substr(0, charLength);
			input.value = strTemp;

			// 표시될 글자수를 다시 센다.
			var tempByte = 0;
			for(var i=0; i< strTemp.length; i++) {
				var tempChar = strTemp.charAt(i);
				if (escape(tempChar).length > 4) { 
					tempByte +=2;
				} else {
					tempByte++;
				}
			}
			inputByte = tempByte;
		}
	};
	
	return _checkByte;
}));