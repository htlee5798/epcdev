var english = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
var num = "0123456789";
var comp = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
//비밀번호에 허용할 특수문자를 추가하자...
var pw_comp = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_"; 
//var pw_comp = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_~!@#$%^&*()+|`=\"; 
var ename = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ &.0123456789";

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

//주어진 값이 숫자인지 판별
function checkInteger(args)
{
  var vcString = args.value;
  if(!isEmpty(vcString))
  {
    for(i = 0; i < vcString.length;i++)
    {
      if ((vcString.substring(i, i+1) >= "0") && (vcString.substring(i, i+1) <= "9"))
      {
        args.value = vcString.substring(0,i+1);
      }
      else
      {
        args.value = vcString.substring(0,i);
        break;
      }
    }
  }
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

function checkEmail(value){//null 체크는 하지 않는다. 필수값이 아니므로 null체크는 각각의 Page에서 할것
	var ind=value.indexOf('@');
	var beforeAt=value.substring(0,ind);
	var afterAt=value.substring(ind);
	if(ind==-1){
		//alert("메일주소를 정확하게 입력해 주세요.");
		return false;
	}else if(beforeAt.length==0){
		//alert("메일주소를 정확하게 입력해 주세요.");
		return false;
	}else{
		var dotind=afterAt.indexOf('.');
		if(dotind==-1){
			//alert("메일주소를 정확하게 입력해 주세요.");
			return false;
		}else{
			var beforeDot = afterAt.substring(1,dotind);
			var afterDot = afterAt.substring(dotind+1);
			if(beforeDot.length==0){
				//alert("메일주소를 정확하게 입력해 주세요.");
				return false;
			}else if(afterDot.length==0){
				//alert("메일주소를 정확하게 입력해 주세요.");
				return false;
			}
			
		}
	}
	return true;
}

function checkDate(msubject,minput1,minput2,minput3)
{
	mYear = Number(minput1);
	mMonth = Number(minput2);
	mDay = Number(minput3);
	
	if( mYear < 0){
		alert(msubject + ": 년도가 잘못입력되었습니다.");
		return false;
	}
	if( mMonth <1 || mMonth > 12){
		alert(msubject + ": 월이 잘못입력되었습니다.");
		return false;
	}
	if(mMonth == 1 || mMonth == 3 || mMonth == 5 || mMonth == 7 || mMonth == 8 || mMonth == 10 || mMonth == 12){
		if(mDay < 1 || mDay > 31){
			alert(msubject + ": 1일부터 31일까지 입력가능합니다.");
			return false;
		}
	}else if(mMonth == 2){
		if( (mYear % 4) == 0 && (mYear % 100) != 0)
		{
			if(mDay < 1 || mDay > 29)
			{
				alert(msubject + ": 1일부터 29일까지 입력가능합니다.");
				return false;
			}			
		}else if( (mYear % 400) == 0){
			if(mDay < 1 || mDay > 29)
			{
				alert(msubject + ": 1일부터 29일까지 입력가능합니다.");
				return false;
			}			
		}else{
			if(mDay < 1 || mDay > 28){
				alert(msubject + ": 1일부터 28일까지 입력가능합니다.");
				return false;
			}						
		}
	}else{
		if(mDay <1 || mDay > 30){
			alert(msubject + ": 1일부터 30일까지 입력가능합니다.");
			return false;
		}
	}
	return true;
}
