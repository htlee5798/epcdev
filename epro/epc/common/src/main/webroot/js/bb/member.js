/***********************************************************
 * 아이디&비밀번호 체크 
 *********************************************************/
checkIdAndPwd = function(id, pwd, pwdCnfm, len) {
	
	if (id.length < 6 || id.length > 12) {
		alert("아이디는 6자이상 12자 이하 입력 해주시기 바랍니다.");
		return true;
	}
	
	if (pwd.length < 6 || pwd.length > 20) {
		alert("비밀번호는  6자이상 20 이하 입력 해주시기 바랍니다.");
		return true;
	}
	
	if (/([^a-z,A-Z,0-9])/.test(id) == true) {
		alert("아이디는 알파벳과 숫자이외의 다른 문자를 사용 하실 수 없습니다.");
		return true;
	}
	
	if (/([^a-z,A-Z,0-9])/.test(pwd) == true) {
		alert("비밀번호는 알파벳과 숫자이외의 다른 문자를 사용 하실 수 없습니다.");
		return true;
	}
	
	if (pwd != pwdCnfm) {
		alert("비밀번호와 비밀번호 확인 문자가 일치하지 않습니다.");
		return true;
	}
	
	
	/*if (/(.)\1+\1+/.test(id) == true) {
		alert("아이디는 3자이상 연속 같은 문자를 사용 하실 수 없습니다.");
		return true;
	}
	
	if (/(.)\1+\1+/.test(pwd) == true) {
		alert("비밀번호는 3자이상 연속 같은 문자를 사용 하실 수 없습니다.");
		return true;
	}
	var temp = null;
	var index = null;
	for (var i=0; i < id.length-(len-1);i++) {
		temp = id.substr(i, len);
		index = pwd.indexOf(temp);
		
		if (index >= 0) {	//중복문자를 검사한다.
			alert("아이디와 비밀번호는 4자이상 같은 문자를 사용 하실 수 없습니다.");
			return true;
		}
	}*/
	
	return false;
}

/***********************************************************
 * 사업자번호 체크
 *********************************************************/
checkBizNum = function(val1, val2, val3) {
	var chkValArr = new Array(10);          
	chkValArr[0] = ( parseFloat(val1.substring(0 ,1))  * 1 ) % 10;          
	chkValArr[1] = ( parseFloat(val1.substring(1 ,2))  * 3 ) % 10;          
	chkValArr[2] = ( parseFloat(val1.substring(2 ,3))  * 7 ) % 10;          
	chkValArr[3] = ( parseFloat(val2.substring(0 ,1))  * 1 ) % 10;          
	chkValArr[4] = ( parseFloat(val2.substring(1 ,2))  * 3 ) % 10;          
	chkValArr[5] = ( parseFloat(val3.substring(0 ,1))  * 7 ) % 10;          
	chkValArr[6] = ( parseFloat(val3.substring(1 ,2))  * 1 ) % 10;          
	chkValArr[7] = ( parseFloat(val3.substring(2 ,3))  * 3 ) % 10;          
	
	chkValTemp = parseFloat(val3.substring(3,4))  * 5 + "0";          
	
	chkValArr[8] = parseFloat(chkValTemp.substring(0,1)) + parseFloat(chkValTemp.substring(1,2));          
	chkValArr[9] =  parseFloat(val3.substring(4,5));          
	chkValLastid = (10 - ( ( chkValArr[0] + chkValArr[1] + chkValArr[2] + chkValArr[3] + chkValArr[4] + chkValArr[5] + chkValArr[6] + chkValArr[7] + chkValArr[8] ) % 10 ) ) % 10;          
	
	if (chkValArr[9] != chkValLastid){          
		alert("유효한 사업자 번호가 아닙니다.");
			return false;
	} else {
		return true;
	} 
}

/***********************************************************
 * 스페이스 넣기
 *********************************************************/
getSpace = function(elem, len) {
	var elemVal = elem.val();
	for (var i=1; elemVal.length < 4 ; i++) {
		elemVal += " ";
	}
	return elemVal;
}

/***********************************************************
 * 전화번호 스페이스 넣기
 *********************************************************/
addTelNoSpace = function(telNm) {
	for (var i=1; i <= 3 ; i++) {
		if ($("#" + telNm + i).val().length < 5) {
			$("#" + telNm + i).val(getSpace($("#" + telNm + i)));
		}
	} 
}


/***********************************************************
 * 전화번호 체크
 *********************************************************/
chkTelNo = function (telNm) {
	var objVal = null;
	var elem = null;
	var objIdx = 0;
	var isNumOk = true;
	for (var i=1; i <= 3 ; i++) {
		elem = $("#" + telNm + i);
		objVal = $("#" + telNm + i).val();
		objIdx = 0;
		for (var idx=0; idx < objVal.length; idx++) {
			if (/[^0-9]/.test(objVal.substring(idx, idx+1))) {
				objIdx = idx;
				isNumOk = false;
				break;
			}
		}
		elem.value = objVal.substring(0, objIdx);
		
		if (i == 1) {
			if(/\d{2,4}/.test(objVal) == false) {
				return elem;
			}
			if(objVal == "000") {
				return elem;
			}
			if (isNumOk == false) {
				return elem;
			}
		} else if (i == 2) {
			if(/\d{3,4}/.test(objVal) == false) {
				return elem;
			}
			if (isNumOk == false) {
				return elem;
			}
		} else if (i == 3) {
			if(/\d{4}/.test(objVal) == false) {
				return elem;
			}
			if (isNumOk == false) {
				return elem;
			}
		}
	} 
	return null;
}

/***********************************************************
 * 이메일 체크
 *********************************************************/
checkEmail = function(emailVal) {
	var regVar = "^[a-zA-Z0-9!$&*.=^`|~#%'+\/?_{}-]+@([a-zA-Z0-9_-]+\.)+[a-zA-Z]{2,4}$";
	if(new RegExp(regVar).test(emailVal) == true) {
		return true;	
	} else {
		alert("이메일 형식이 아닙니다.");
		$("#emailId").focus();
		return false;
	}
}


/***********************************************************
 * 이메일 도메인 선택
 *********************************************************/
changeEmailDomain = function(obj) {
	if (obj.value == "direct_input") {
		frm.emailDomain.readOnly = false;
		frm.emailDomain.value = "직접입력";
	} else {
		frm.emailDomain.readOnly = true;
		frm.emailDomain.value = obj.value;
	}
}



setTelNoTrim = function(telNm) {
	for (var i=1; i <= 3 ; i++) {
		$("#" + telNm + i).val($.trim($("#" + telNm + i).val()));
	} 
}



/***********************************************************
 * 아이디&비밀번호 체크 
 **********************************************************/
 checkPwd = function(pwd, pwdCnfm) {
	
	if (pwd.length == 0 || pwd.length > 15) {
		alert("비밀번호는  6자이상 16 이하 입력 해주시기 바랍니다.");
		return false;
	}
	
	if (/([^a-z,A-Z,0-9])/.test(pwd) == true) {
		alert("비밀번호는 알파벳과 숫자이외의 다른 문자를 사용 하실 수 없습니다.");
		return false;
	}
	
	if (pwd != pwdCnfm) {
		alert("비밀번호와 비밀번호 확인 문자가 일치하지 않습니다.");
		return false;
	}
	
	if (/(.)\1+\1+/.test(pwd) == true) {
		alert("비밀번호는 3자이상 연속 같은 문자를 사용 하실 수 없습니다.");
		return false;
	}
	
	return true;
}

/***********************************************************
 * 알파벳 숫자혼용체크 
 **********************************************************/
checkAlphaAndNum = function(chkStr) {
	var ch = 0;
	var isNumExist = false;
	var isAlphaExitst = false;
	for (i=0; i < chkStr.length;  i++) { 
		ch = chkStr.charAt(i); 
		if (/[0-9]/.test(chkStr)) {
			isNumExist = true;
			break;
		}
	}
	if (isNumExist) {
		for (i=0; i < chkStr.length;  i++) { 
			ch = chkStr.charAt(i); 
			if (/[a-zA-Z]/.test(chkStr)) {
				isAlphaExitst = true;
				break;
			}
		}
		return isAlphaExitst;
	} else {
		return false;
	}
}

checkDuplStr = function(id) {
	if (/(.)\1+\1+/.test(id) == true) {
		return false;
	} else {
		return true;
	}
}

