/*********************************************
* 파일명: validate.js
* 기능: 유연한 자동 폼 검사기
* 만든이: EPC
* 날짜: 2011-11-11
**********************************************/

/**
 * 주민번호 뒤에 6자리 * 처리하기 
 * 예제 - (JuminNoAstric(7809011111111)
 * @param JuminNo
 * @return
 */
function JuminNoAstric(JuminNo){
	
	if( JuminNo.length <= 0 || ( JuminNo.length != 13 && JuminNo.length != 14 ) ) return JuminNo;
		var strRet = "";
		if( JuminNo.length == 13 ){
			strRet = JuminNo.substring(0, 6)+ "-" + JuminNo.substring(6, 7) + "******";
		}else if( (JuminNo.length == 14) ){
			strRet = JuminNo.substring(0,8) + "******";
		}
	return strRet;
}

/**
 * null 체크
 * 예제 - nullChk('질문',$('#title').val(),$('#title'));
 * @param 
 * @return true, false
 */
function nullCheck(name,val,target) {
    if(val == ""){
    	alert(" "+name+"을(를) 입력하세요!");
    	$(target).focus();
    	return false;
    }
    return true;
}

/**
 * 바이트처리후 메시지 및 초과된 바이트수 삭제
 * 예제 - byteCheck('질문',$('#title').val(),4,$('#title'));
 * @param 
 * @return true, false
 */
function byteCheck(name,val,length,target) {
    
    var len = getByte(val);
   
    if (len > length) {
       alert(" "+name+"은(는)  최대 "+length+" 바이트까지 입력가능합니다.  초과된 " + (len - length) + "바이트는 자동으로 삭제됩니다.");
       var str = ASSERT_LEN(val, length);
       $(target).val(str);
       $(target).focus();
       return false;
    }
    return true;
}

/**
 * 바이트처리후 메시지 (그리드에서 사용)
 * 예제 - byteCheck2('질문',$('#title').val(),4);
 * @param 
 * @return true, false
 */
function byteCheck2(name,val,length) {
    
    var len = getByte(val);
   
    if (len > length) {
       alert(" "+name+"은(는)  최대 "+length+" 바이트까지 입력가능합니다.");
       return false;
    }
    return true;
}

/**
 * byte 계산처리 
 * 예제 - getByte('테스트')
 * @param nbytes
 * @return
 */
function getByte( msg )
{
	var nbytes = 0;

	for (var z=0; z<msg.length; z++) {
		var ch = msg.charAt(z);

		if(escape(ch).length > 4) {
			//한글 utf-8 3  euc-kr 2
			nbytes += 3;
			
		} else if (ch == '\n') {
			if (msg.charAt(z-1) != '\r') {
				nbytes += 1;
			}else{
				nbytes += 1;
			}
		} else if (ch == '<' || ch == '>') {
			nbytes += 4;
		} else {
			
			nbytes += 1;
		}
	}
	return nbytes;
}

/**
 * 초과된 바이트수 삭제처리
 * 예제 - ASSERT_LEN('테스트', 4)
 * @param msg
 * @return
 */
function ASSERT_LEN(val, maximum) { 
    var inc = 0;
    var nbytes = 0;
    var msg = "";
    var msglen = val.length;

    for (i=0; i<msglen; i++) {
        var ch = val.charAt(i);
        if (escape(ch).length > 4) {
        	//한글 utf-8 3  euc-kr 2
            inc = 3;
        } else if (ch != '\r') {
            inc = 1;
        }
        if ((nbytes + inc) > maximum) {
            break;
        }
        nbytes += inc;
        msg += ch;
    }
    return msg;
}

//금액 3자리마다 콤마
function addTxtComma(formnum){
	
    num1 = formnum.length;

    FirstNum = formnum.substr(0,1);
    FirstNum2 = formnum.substr(1,num1);

    if(FirstNum == "0"){ 
            return "0";
    return FirstNum2;
            formnum = FirstNum2;
    }
    loop = /^\$|,/g;
    formnum = formnum.replace(loop, "");	
    var fieldnum = '' + formnum;
    if(isNaN(fieldnum)){
        alert("숫자만 입력하실 수 있습니다.");
        
        return "";
    }else{
        var comma = new RegExp('([0-9])([0-9][0-9][0-9][,.])');
        var data = fieldnum.split('.');
        data[0] += '.';
        do {
            data[0] = data[0].replace(comma, '$1,$2');
        } while (comma.test(data[0]));

        if (data.length > 1){
            return data.join('');
        }else{
            return data[0].split('.')[0];
        }
    }
}
/**
 * 입력값에서 콤마를 없앤다.
 */
function removeComma(input) {
    return input.replace(/,/gi,"");
}
//라디오 
function getRadioValue(obj){
	var len = obj.length;	
	for( c = 0 ; len > c ; c ++ ){
		if ( obj[c].checked ) return obj[c].value;
	}
}
//라디오 
function setRadioValue(obj, val){
	
	if ( val == null || val == "" )	return;
	
	var len = obj.length;
	for( c = 0 ; len > c ; c ++ ){
		if ( obj[c].value == val ) obj[c].checked=true;
	}
	
}