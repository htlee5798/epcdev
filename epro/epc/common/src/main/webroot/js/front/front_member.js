var english = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
var num = "0123456789";
//아이디에 허용할 문자
var comp = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
//비밀번호에 허용할 특수문자를 추가하자...
var pw_comp = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_"; 
//var pw_comp = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_~!@#$%^&*()+|`=\"; 
var ename = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ &.0123456789";
var pw_comp2 = "1234";
var pw_comp3 = "asdf";
var pw_comp4 = "qwer";
var pw_comp5 = "zxcv";
var pw_comp01 = "abcdefghijklmnopqrstuvwxyz";
var pw_comp02 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
var pw_comp03 = "123456789";

function chgFocus(len, inObj, nextObj) {
	if( inObj.value.length == len) {
		nextObj.focus();
	}
} 

function isInteger(st){          
	if (!isEmpty(st)){          
   		for (j=0; j<st.length; j++){          
     		if (((st.substring(j, j+1) < "0") || (st.substring(j, j+1) > "9")))          
     			return false;          
   		}          
	}else{          
   		return false ;          
	}          
	return true ;          
}

function isEmpty(toCheck){          
	var chkstr = toCheck + "";          
	var is_Space = true ;          
	
	if ( ( chkstr == "") || ( chkstr == null ) )           
		return( true );          
	for ( j = 0 ; is_Space && ( j < chkstr.length ) ; j++){          
		if( chkstr.substring( j , j+1 ) != " " )           
			is_Space = false ;          
	}          
	return ( is_Space );          
}

function isSpace(toCheck){          
	var chkstr = toCheck + "";          
	var is_Space = false ;          
	
	if ( ( chkstr == "") || ( chkstr == null ) )           
		return( true );          
	for ( j = 0 ; is_Space==false && ( j < chkstr.length ) ; j++){          
		if( chkstr.substring( j , j+1 ) == " " )           
			is_Space = true ;          
	}          
	return ( is_Space );          
}

function strLeng(strIn){          
	var strOut = 0;          
	for ( i = 0 ; i < strIn.length ; i++){          
		ch = strIn.charAt(i);          
		if ((ch == "\n") || ((ch >= "ㅏ") && (ch <= "히")) || ((ch >="ㄱ") && (ch <="ㅎ")))           
			strOut += 2;          
		else          
			strOut += 1;          
	}           
	return (strOut);          
}	              


function id_ck(value){
/*	if(!value){
		alert("id를 입력해 주십시요.");
		return false;
	}*/
	var len = value.length;
	if(!(len > 3 && len < 16)){
		alert(msg_user_error_idLengthCheck); //아이디는 4자 이상 15자 이하여야 합니다.
		return false;
	}

	for(i=0;i<len;i++){
			if(comp.indexOf(value.charAt(i)) == -1){
				alert(msg_user_error_invalidReChar); //허용된 문자가 아닙니다. 다시 입력해 주십시오.
				return false;
			}
	}
	
	var pattern1 = /(^[a-z])/;
	var pattern2 = /([^a-zA-Z0-9\-_])/;
	var pattern3 = /([0-9])/;
	var id = value;

//	if(!pattern1.test(id)){
//	    alert("아이디의 첫글자는 영문(대,소문자 구분)이어야 합니다.");
//	    return false;
//	}

	if(pattern2.test(id)){
	    alert("아이디는 영문(대,소문자 구분), 숫자, -, _ 만 사용할 수 있습니다.");
	    return false;
	} 
	
//	if(!pattern3.test(id)){
//	    alert("아이디는 숫자도 혼용 해주셔야 합니다.");
//	    return false;
//	} 

	return true;
}

function pw_ck(id, value){

	var len = value.length;
	if(!(len > 8 && len < 16)){
		alert(msg_user_error_pwdLengthCheck); //비밀번호는 6자 이상 15자 이하여야 합니다.
		return false;
	}

    chk1 = /[a-z]/;  //a-z와 0-9이외의 문자가 있는지 확인
    chk2 = /[A-Z]/;  //적어도 한개의 a-z 확인    
    chk3 = /\d/;  //적어도 한개의 0-9 확인
    chk4 = /[!,@,#,$,%,^,&,*,?,_,~]/;  //적어도 한개의 0-9 확인
    
    var tcnt = 0;
    
    if ( chk1.test(value) == true ) {
    	tcnt = tcnt + 1;
    }
   
    
    if ( chk2.test(value) == true ) {
    	tcnt = tcnt + 1;
    }
   	 
    if ( chk3.test(value) == true ) {
    	tcnt = tcnt + 1;
    }
    
    if ( chk4.test(value) == true ) {
    	tcnt = tcnt + 1;
    }         
    
    if ( tcnt < 2) {
    	alert(msg_user_error_pwdCheck);	// 비밀번호는 영문 대소문자,숫자,특수기호의 조합으로 6~15자리를 사용해야 합니다.
    	return false;
    }      

	if ( !!id )
	{
		for (var i=0; i < id.length-2; i++) {
			temp = id.substring(i, i+4);
			j = value.indexOf(temp);
			if ( j > -1 ) {
				alert(msg_user_error_pwdIdCheck); //비밀번호에 아이디와 동일한 문자열을 4자이상 사용하실 수 없습니다.
				return false;
			}
		}
	}

 	if(/(\w)\1\1/.test(value))
    {
        alert(msg_user_error_pwdOverlapCheck); //연속된 문자열을 3자 이상 사용 할 수 없습니다.
        return false;
    }

		
	
	var SamePass_0 = 0; //동일문자 카운트
	var SamePass_1 = 0; //연속성(+) 카운드
	var SamePass_2 = 0; //연속성(-) 카운드
	
	var SamePass_3 = 0; //연속값2 체크카운트
	var SamePass_4 = 0; //연속값3 체크카운트
	var SamePass_5 = 0; //연속값4 체크카운트
	var SamePass_6 = 0; //연속값5 체크카운트	
	
	for (var i=0; i<value.length-2; i++) {
		temp = value.substring(i, i+3);
		
		
		SamePass_0 = pw_comp01.indexOf(temp);
		SamePass_1 = pw_comp02.indexOf(temp);
		SamePass_2 = pw_comp03.indexOf(temp);
		
		if ( SamePass_0 > -1 ) {
			alert(msg_user_error_pwdOverlapCheck); //연속된 문자열을 3자 이상 사용 할 수 없습니다.
			return false;
		}		
		if ( SamePass_1 > -1 ) {
			alert(msg_user_error_pwdOverlapCheck); //연속된 문자열을 3자 이상 사용 할 수 없습니다.
			return false;
		}	
		if ( SamePass_2 > -1 ) {
			alert(msg_user_error_pwdOverlapCheck); //연속된 문자열을 3자 이상 사용 할 수 없습니다.
			return false;
		}			
		
		SamePass_4 = pw_comp3.indexOf(temp);
		SamePass_5 = pw_comp4.indexOf(temp);
		SamePass_6 = pw_comp5.indexOf(temp);
		
		if ( SamePass_4 > -1 ) {
			alert(msg_user_error_pwdKeyboardCheck);	//키보드상에서 연속한 위치의 문자열은 비밀번호로 사용하실 수 없습니다.
			return false;
		}		
		if ( SamePass_5 > -1 ) {
			alert(msg_user_error_pwdKeyboardCheck);	//키보드상에서 연속한 위치의 문자열은 비밀번호로 사용하실 수 없습니다.
			return false;
		}	
		if ( SamePass_6 > -1 ) {
			alert(msg_user_error_pwdKeyboardCheck);	//키보드상에서 연속한 위치의 문자열은 비밀번호로 사용하실 수 없습니다.
			return false;
		}			
	}	

	return true;
}

function saup_val_ck(val1,val2,val3)          
{          
	if(!(val1)||!(val2)||!(val3)){
		alert(msg_user_error_nullCor); //사업자등록번호를 입력해 주세요.
		return false;
	}
	li_value = new Array(10);          
	if ( strLeng(val1) == 3 && strLeng(val2) == 2 && strLeng(val3) == 5){          
   		if ( ( isInteger(val1)) && ( isInteger(val2)) && ( isInteger(val3))){          
          li_value[0] = ( parseFloat(val1.substring(0 ,1))  * 1 ) % 10;          
          li_value[1] = ( parseFloat(val1.substring(1 ,2))  * 3 ) % 10;          
          li_value[2] = ( parseFloat(val1.substring(2 ,3))  * 7 ) % 10;          
          li_value[3] = ( parseFloat(val2.substring(0 ,1))  * 1 ) % 10;          
          li_value[4] = ( parseFloat(val2.substring(1 ,2))  * 3 ) % 10;          
          li_value[5] = ( parseFloat(val3.substring(0 ,1))  * 7 ) % 10;          
          li_value[6] = ( parseFloat(val3.substring(1 ,2))  * 1 ) % 10;          
          li_value[7] = ( parseFloat(val3.substring(2 ,3))  * 3 ) % 10;          
          li_temp = parseFloat(val3.substring(3,4))  * 5 + "0";          
          li_value[8] = parseFloat(li_temp.substring(0,1)) + parseFloat(li_temp.substring(1,2));          
          li_value[9] =  parseFloat(val3.substring(4,5));          
          li_lastid = (10 - ( ( li_value[0] + li_value[1] + li_value[2] + li_value[3] + li_value[4] + li_value[5] + li_value[6] + li_value[7] + li_value[8] ) % 10 ) ) % 10;          
          if (li_value[9] != li_lastid){          
            alert(msg_user_error_invalidCor); //사업자등록번호가 잘못 입력되었습니다.         
            return false;          
          }else          
            return true;
        }else          
             alert(msg_user_error_corNumber); //사업자등록번호는 123-45-56789의 형태로 입력하십시오.          
             return false;          
    }else {          
    	alert(msg_user_error_corNumber); //사업자등록번호는 123-45-56789의 형태로 입력하십시오.
  		return false;          
    } 

	return true;    	             
}
  
  //jiwoon 2004.04.22 add
  /* E-Mail 입력값 확인 */
function jsMailVal(item)
{	
    var posAt;
	var fChk = 0;
    var mailAddr = item;	    

    posAt = mailAddr.indexOf("@");
	var mailID = mailAddr.substring(0, posAt);
	var mailHost = mailAddr.substring(posAt+1);
    
    if (posAt == -1) 
		fChk = 1;								/* @가 없으면 오류 */
	else 
	{
    	if ((mailID.toUpperCase() == "HTTP") ||(mailID.toUpperCase() == "WWW") ||(mailID == "/") ) 
			fChk = 1;
		else if (mailHost.indexOf("@") != -1) 
			fChk = 1;							/* @ 한번 이상 있으면 오류 */ 
		if (mailHost.indexOf(".") == -1)
			fChk = 1;							/* . 없으면 오류 */ 
		if (mailHost.indexOf(".") == mailHost.length-1) 
			fChk = 1;							/* . 뒤에 값이 없으면 오류 */ 
		if (mailAddr.indexOf("'") > -1 ) 
			fChk = 1;							/* . 뒤에 값이 없으면 오류 */ 
		if (mailHost.indexOf('"') > -1) 
			fChk = 1;							/* . 뒤에 값이 없으면 오류 */ 
		if (mailHost.indexOf('!') > -1) 
			fChk = 1;							/* . 뒤에 값이 없으면 오류 */ 
		if (mailHost.indexOf('`') > -1) 
			fChk = 1;							/* . 뒤에 값이 없으면 오류 */ 
		if (mailHost.indexOf(';') > -1) 
			fChk = 1;							/* . 뒤에 값이 없으면 오류 */ 
		if (mailHost.indexOf(':') > -1) 
			fChk = 1;							/* . 뒤에 값이 없으면 오류 */ 
		if (mailHost.indexOf('?') > -1) 
			fChk = 1;							/* . 뒤에 값이 없으면 오류 */ 
		if (mailHost.indexOf('/') > -1) 
			fChk = 1;							/* . 뒤에 값이 없으면 오류 */ 
	}

	if (fChk == 1) 				
		return false;
	if(fChk == 0)
		return true;
}


//입력받은 소문자 포커스 이동시 대문자로 바꿔주기
function changeUpper(obj) {
	obj.value = obj.value.toUpperCase();
}
	