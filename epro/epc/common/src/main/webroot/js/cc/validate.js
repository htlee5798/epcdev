/*********************************************
* 파일명: validate.js
* 기능: 유연한 자동 폼 검사기
* 만든이: CC 
* 날짜: 2011-10-10
* *********************************************/

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
			nbytes += 2;
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
            inc = 2;
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


/**
@ 입력창에 숫자만 입력되게하는 Function
ex) onKeyDown="NumberCheck();"
*/
function NumberCheck()
{
//KeyCode = event.keyCode;
//alert(KeyCode);
//숫자값 체크
if( (event.keyCode<48 && event.keyCode !=8 && event.keyCode != 9 && event.keyCode != 37 && event.keyCode != 39 && event.keyCode != 46) || (event.keyCode>57 && event.keyCode<96 && event.keyCode !=8  && event.keyCode != 9 && event.keyCode != 37 && event.keyCode != 39 && event.keyCode != 46) || (event.keyCode>105 && event.keyCode !=8  && event.keyCode != 9 && event.keyCode != 37 && event.keyCode != 39 && event.keyCode != 46))
		event.returnValue=false;
}





