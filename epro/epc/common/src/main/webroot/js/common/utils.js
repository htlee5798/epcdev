//필수 체크 공통 함수(input, select 에 대해 alt 값에 체크하고자 하는 id를 입력
function checkEsen(){
	var input = document.getElementsByTagName('input');
	var select = document.getElementsByTagName('select');
	for(var i=0; i<input.length; i++){

		if(input[i].getAttribute('alt') != ''){
			
			if(!checkValue(input[i].getAttribute('alt'))){
				return false;
			}
		}
	} 

	for(var j=0; j<select.length; j++){

		if(select[j].getAttribute('alt') != ''){
			if(!checkValue(select[j].getAttribute('alt')))
				return false;
  
		}
	} 

	return true;
}

//필수 체크 공통 함수(select 에 대해 alt 값에 체크하고자 하는 id를 입력
function checkEsenSelect(){
	var select = document.getElementsByTagName('select');

	for(var j=0; j<select.length; j++){

		if(select[j].getAttribute('alt') != ''){
			if(!checkValue(select[j].getAttribute('alt'))){
				return false;
			}
		}
	} 

	return true;
}

//값 체크(값을 넣지 않았으면 alert를 띄위고 Background Color를 변경해줌)
function checkValue(objName){

	if(document.getElementById(objName) != null){
		if(document.getElementById(objName).value == ''){

			if(document.getElementById(objName).style.display=='none' || document.getElementById(objName).parentNode.style.display == 'none' 
				|| document.getElementById(objName).style.visibility=='hidden' || document.getElementById(objName).parentNode.style.visibility=='hidden')
			
				return true;
  
			document.getElementById(objName).style.background = '#FFFFCC';
			document.getElementById(objName).focus();
			alert('필수입력값입니다.');
 
			return false;
		}else 
			return true;
	}else{
		return true;
	}
}

//숫자 체크하기
function checkNumber(name, targetName, length){
	var pattern = /^[0-9]+$/;
	
	
	var checkData = document.getElementById(name);
	if(checkData.value != null && checkData.value != ""){
		if(!pattern.test(checkData.value)){
			alert("숫자만 입력할 수 있습니다.");
			checkData.value = "";
			checkData.focus();
			return false;
		}else{
			if(targetName != ""){
			 textFocusFunc(name, targetName, length);
			}
		}
	}
}

//전화번호, fax, 핸드폰번호 에서 중간번호 입력시 length보다 커지면 마지막 번호 text로 focus 이동
//dataId :  현재 text의 ID, id : 마지막 번호 text ID, length : 길이
function textFocusFunc(dataId, id, length){
	var data = document.getElementById(dataId);
	if(data.value.length >= length){
		document.getElementById(dataId).value = data.value.substring(0, length);
		document.getElementById(id).focus();
	}
}

// 파일 다운로드 팝업
function fileDownloadFunc(fileSeq, brdSct) {
	//window.open("/download.action?fileSeq=" + fileSeq + "&brdSct=" + brdSct, "download","left=100,top=100,width=0,height=0");
	document.location.href = "/download.action?fileSeq=" + fileSeq + "&brdSct=" + brdSct;
}

// 파일 다운로드2 팝업
function fileDownloadFunc2(seq) {
	//window.open("/download.action?fileSeq=" + fileSeq + "&brdSct=" + brdSct, "download","left=100,top=100,width=0,height=0");
	document.location.href = '/download2.action?Seq='+seq,'download','left=100,top=100,width=500,height=500';
}


// 팝업 띄우기
function openWindowFunc(url, winName, width, height){
	window.open(url, winName, "toolbar=0, location=0, menubar=0, scrollbars=0 resizable=0, width=" + width + ", height=" + height);
}

//trim 기능 구현 함수
String.prototype.trim = function() {
    return this.replace(/(^\s+)|(\s+$)/g, "");
};

Array.prototype.include = function () {
    for (var index in this) {
        if (this[index] == arguments[0]) {
            return true;
        }
    }
    return false;
};


//replaceAll 기능 구현함수
String.prototype.replaceAll = replaceAll;
function replaceAll(strValue1, strValue2){
	var strTemp = this;
	strTemp = strTemp.replace(new RegExp(strValue1, "g"), strValue2);

	return strTemp;
}

/**
 * 문자열 좌측에 지정한 문자로 채워넣기
 *
 * @param		oOrg			컨트롤 또는 문자열
 * @param		sPaddingChar	채워넣기 문자
 * @param		iNum			채워넣기한 문자열 자릿수
 * @return	String
 */
function setLPadding (oOrg, sPaddingChar, iNum) {
	if (oOrg == null || sPaddingChar == null || iNum == null) return;


 	var sReturn, sOrgStr
 	var sPaddingStr = "";


 	if (typeof(oOrg) == "object")
 		sOrgStr = oOrg.value;
 	else if (typeof(oOrg) == "string")
 		sOrgStr = oOrg;
 	else
 		return;


 	if (sOrgStr.length == 0) return;


 	for (var i=0; i < iNum; i++) {
 		sPaddingStr += sPaddingChar;
 	}


 	sReturn = (sPaddingStr + sOrgStr).substring((sPaddingStr + sOrgStr).length-iNum, (sPaddingStr + sOrgStr).length);


 	if (typeof(oOrg) == "object")
		oOrg.value = sReturn;
 	else
 		return	sReturn;
}


/**
 * 문자열 우측에 지정한 문자로 채워넣기
 *
 * @param		oOrg			컨트롤 또는 문자열
 * @param		sPaddingChar	채워넣기 문자
 * @param		iNum			채워넣기한 문자열 자릿수
 * @return	String
 */
function setRPadding (oOrg, sPaddingChar, iNum) {
	if (oOrg == null || sPaddingChar == null || iNum == null) return;


	var sReturn, sOrgStr;
	var sPaddingStr = "";


	if (typeof(oOrg) == "object")
		sOrgStr = oOrg.value;
	else if (typeof(oOrg) == "string")
		sOrgStr = oOrg;
	else
		return;


	if (sOrgStr.length == 0) return;


	for (var i=0; i < iNum; i++) {
		sPaddingStr += sPaddingChar;
	}


	sReturn = (sOrgStr + sPaddingStr).substring(0, iNum);


	if (typeof(oOrg) == "object")
		oOrg.value = sReturn;
	else
		return	sReturn;
}



function chkLength(id, length){
	if(document.getElementById(id).value.length > length)
		return false;
	else
		return true;
}

//해당 일자형식으로 포맷을 맞춰주는 함수('-'를 붙여줌)
function makeDateFormat(str, type) {
	
	if(type=="D" || type=="W") {
		str = str.substring(0,4) + "-" + str.substring(4,6) + "-" + str.substring(6,8);
	} else if(type=="M") {
		str = str.substring(0,4) + "-" + str.substring(4,6);
	} else if(type=="Y") {
		str = str.substring(0,4);
	}
	return str;		
}



/**
 * 
 * 
 * @param pInterval : "yyyy" 는 연도 가감, "m" 은 월 가감, "d"는 일 가감
 * @param pAddVal : 가감하고자 하는 값(정수형)
 * @param pYyyymmdd : 가감의 기준이 되는 날짜
 * @param pDelimiter : pYyyymmdd 값에 사용된 구분자를 설정(없으면 "" 입력)
 * @returns
 * 	yyyymmdd 또는 함수 입력시 지정된 구분자를 가지는 yyyy?mm?dd값
 * 사용 예
 * 2008-01-01 에 3일 더하기 ==> addDate("d", 3, "2008-08-01", "-");
 * 20080101 에 8개월 더하기 ==> addDate("m", 8, "20080801", "");
 */
function addDate(pInterval, pAddVal, pYyyymmdd, pDelimiter) {
	var yyyy;
	var mm;
	var dd;
	var cDate;
	var coDate;
	var cYear, cMonth, cDay;
	
	if(pDelimiter != ""){
		pYyyymmdd = pYyyymmdd.replace(eval("/\\" + pDelimiter + "/g"), "");
	}
	yyyy = pYyyymmdd.substr(0, 4);
	mm = pYyyymmdd.substr(4, 2);
	dd = pYyyymmdd.substr(6, 2);
	
	if(pInterval == "yyyy"){
		yyyy = (yyyy * 1) + (pAddVal * 1);
	}else if(pInterval == "m"){
		mm = (mm * 1) + (pAddVal * 1);
	}else if(pInterval == "d"){
		dd = (dd*1) + (pAddVal * 1);
	}
	
	cDate = new Date(yyyy, mm-1, dd);
	cYear = cDate.getFullYear();
	cMonth = cDate.getMonth() + 1;
	cDay = cDate.getDate();
	
	cMonth = cMonth < 10 ? "0" + cMonth : cMonth;
	cDay = cDay < 10 ? "0" + cDay : cDay;
	
	if(pDelimiter != ""){
		return cYear + pDelimiter + cMonth + pDelimiter + cDay;
	}else{
		return  "" + cYear + "" + cMonth + "" + cDay ;
	}
}

function excelDown(){
	var frm = document.excelForm;
	var url = "./common/ExcelDownload.jsp";
	var excelArea = document.getElementById("excelArea");
	var category = document.getElementById("category");
	frm.excelData.value = excelArea.innerHTML;
	frm.category.value = category.innerHTML;
	frm.target="excelDown";
	frm.action = url;
	frm.submit();
}

// 입력 byte 카운팅
// 추가			: 정길준
// input		: 키보드 입력 값
// max 			: 제한할 최대 값
// targetName	: 입력 바이트를 표시할 name
// 이벤트는 	: onkeyup
function checkByte(input, max, targetName) {
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
	//if(targetName != ""){
	//	document.getElementById(targetName).value = inputByte;
	//}
}


// 입력 byte 제한
// 특징		: return값을 반환한다(true, false)
//			: 파라미터로 value값을 넘겨줘야 한다.
function checkByteContents(value, max) {
	var result = true;
	
	var str = value; 
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
		result = false;
		strTemp = str.substr(0, charLength);
		value = strTemp;

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
	return result;
}


//입력 byte 제한
//특징		: return값을 반환한다(true, false)
//			: 파라미터로 value값을 넘겨줘야 한다.
function checkByteContentsStr(value, max ,alertStr) {
	var result = true;
	
	var str = value; 
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
		alert(alertStr);
		result = false;
		strTemp = str.substr(0, charLength);
		value = strTemp;

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
	return result;
}

//아이디 체크
function idCheckFunc(id){
	var pwd = document.getElementById(id).value;
	var reg1 = /^[a-zA-Z0-9]{6,14}$/;
	var reg2 = /[a-zA-Z]/g;
	var reg3 = /[0-9]/g;
	var reg4 = /^[a-zA-Z0-9]{6,14}/;
	if(emptyCheckFunc(pwd)){
		if(reg4.test(pwd)){
			if(reg1.test(pwd) && reg2.test(pwd) && reg3.test(pwd)){
				return true;
			}else{
				alert("아이디는 영문과 숫자를 조합하여 구성해야 합니다.");
				document.getElementById(id).focus();
				return false;
			}
		}else{
			document.getElementById("idCheckDesc").innerText = "아이디 최소 6자 최대 14자 까지 구성해야합니다.";
		}
	}else{
		document.getElementById("idCheckDesc").innerText = "아이디에 공백이 있으면 안됩니다.";
		document.getElementById(id).focus();
		return false;
	}
}

//비밀번호 조합 체크
function pwdCheckFunc(id){
	var pwd = document.getElementById(id).value;
	var reg1 = /^[a-zA-Z0-9]{6,14}$/;
	var reg2 = /[a-zA-Z]/g;
	var reg3 = /[0-9]/g;
	var reg4 = /^[a-zA-Z0-9]{6,14}/;
	if(emptyCheckFunc(pwd)){
		if(reg4.test(pwd)){
			if(reg1.test(pwd) && reg2.test(pwd) && reg3.test(pwd)){
				return true;
			}else{
				alert("비밀번호는 영문과 숫자를 조합하여 구성해야 합니다.");
				document.getElementById(id).focus();
				return false;
			}
		}else{
			alert("비밀번호는 최소 6자 최대 14자 까지 구성해야합니다.");
		}
	}else{
		alert("비밀번호에 공백이 있으면 안됩니다.");
		document.getElementById(id).focus();
		return false;
	}
}

//영문숫자 조합 체크
function mixCheckFunc(str){
	var reg1 = /^[a-zA-Z0-9]{6,14}/;
	var reg2 = /[a-zA-Z]/g;
	var reg3 = /[0-9]/g;
	return (reg1.test(str) && reg2.test(str) && reg3.test(str));
}

//공백 체크
function emptyCheckFunc(str) {
   if(str.indexOf(" ") == -1){
	   return true;
   }else{
	   return false;
   }
}

//trim 기능 구현 함수
String.prototype.trim = function() {
    return this.replace(/(^\s+)|(\s+$)/g, "");
}


// 파일 유효성 체크
// obj : 파일 객체, chkSize : 파일 용량 사이즈, chkExt : 확장자 체크 ,로 구분(공백없이 넘겨야함 jpg,gif)
// return 값 ( ext : 확장자 오류, size : 사이즈 오류, success : 체크 성공 )
function fileValidationCheckFunc(obj, chkSize, chkExt){
	if(chkExt != ""){ // 확장자 체크값이 있으면 확장자 체크를 한다
		var extName = "";
		var tempT = obj.value.split(".");
		for(var i = 0 ; i < tempT.length ; i++){
			extName = tempT[i];
		}
		
		var isExtCheck = false;
		
		for(var i = 0 ; i < chkExt.split(",").length ; i++){
			if(chkExt.split(",")[i].toLowerCase() == extName.toLowerCase()){
				isExtCheck = true;
				break;
			}
		}
		
		if(isExtCheck == false){
			return "ext";
		}
	}
	
	/*
	
	if(chkSize != ""){ // 용량 체크 할 경우
		var checkSize = parseInt(chkSize);
		
		var fileSize = 0;
		
		var IE = (navigator.userAgent.indexOf('MSIE') != -1) ? true : false;
		
		if(IE){
			var appVer = navigator.appVersion;
			if(appVer.indexOf("MSIE 6.") != -1){ // IE 6일 경우
				var img = new Image();
				img.dynsrc = obj.value;
				fileSize = img.fileSize;
			}else if(appVer.indexOf("MSIE 8.")){ // IE 8일 경우
								
			}
		}else{
			fileSize = obj.files.item(0).fileSize;
		}
		
		if(checkSize > fileSize){
			return "size";
		}
	}
	
	*/
	
	return "success";
}
function isMoreThan14age(socno1, checkYear){
	var u_year; var u_month;
	var u_day; var v_age;
	var v_today = new Date();
	/* alert( socno1 + ", " + checkYear); */ 
	if( checkYear > 0 == checkYear < 3)
		u_year = "19" + socno1.substring(0, 2);
	else if(checkYear > 2 == checkYear < 5)
		u_year = "20" + socno1.substring(0, 2);
	else return 'wrong'; // 2002.10.8 주민등록뒷자리 첫번째 번호 유효성 체크 (1,2,3,4) are only valid -- by muse 
		u_month = socno1.substring(2, 4); u_day = socno1.substring(4);
	/* alert( socno1.substring(0, 2) + "-" + u_year + ", " + u_month + ", " + u_day ); */
	var rVal = isValidDate(u_year, u_month, u_day );
	if( rVal == 'N' ) return 'I';
	else if( rVal == 'Y' ){ v_age = v_today.getYear()*1 - u_year*1;
		if( u_month*1 > (v_today.getMonth() *1+1) ){
			v_age -= 1;
		}else if( u_month*1 == (v_today.getMonth()*1+1) == u_day*1 > v_today.getDate()*1 ){
			v_age -= 1;
		}
	}
	if( v_age < 14 ) return 'N';
	else if( v_age >= 14 == v_age < 18 ) return 'M';
	else return 'Y';
}

function isValidDate( v_year, v_month, v_day){
	var today = new Date();
	var d_year = v_year*1;
	var d_month = v_month*1;
	var d_day = v_day*1;
	//alert("isValidDate arg1 : " + v_year + ", arg2 : " + v_month + ", arg3 : " + v_day ); 
	//alert("isValidDate" + (today.getMonth()*1+1));
	/* 과거 날짜여야 함. */
	if( v_year > today.getYear() ) return 'N';
	else if( v_year == today.getYear() == v_month*1 > (today.getMonth()*1+1))
		return 'N';
	else if( v_year == today.getYear() == v_month*1 == (today.getMonth()*1+1) == v_day > today.getDate())
		return 'N';  
	/* 달별 일 check */
	if( d_month == 1 || d_month == 3 || d_month == 5 || d_month == 7 || d_month == 8 || d_month == 10 || d_month == 12){
		if( d_day > 31 || d_day < 1)
			return 'N';
		} else if(d_month == 4 || d_month == 6 || d_month == 9 || d_month == 11 ){ if( d_day > 30 || d_day < 1 )
			return 'N';
		} else if( d_month == 2 ) { if( ((d_year%4) == 0 == (d_year%100)!= 0) || ((d_year%100) == 0 == (d_year%400) == 0) ){ if( d_day > 29 || d_day < 1 )
			return 'N';
		}
		else { if( d_day > 28 || d_day < 1 )
			return 'N';
		}
	}
	return 'Y';
}


//탭 이동		: 정길준(2010.09.12)
//obj 			: 현재 input의 obj
//dest 			: 탭이 옯겨갈 목적지의 id명
//maxLength 	: 최대값
function moveTab(obj, dest, maxLength) {
	var curLength = obj.value.length;
	if(chkNum(obj) && (curLength == maxLength)) {
		document.getElementById(dest).focus();
	}
}

// 숫자 입력 검사		: 정길준(2010.09.12)
// 숫자 입력시 return으로 true를 반환한다.
function chkNum(obj) {
	if((event.keyCode == 8)									// backspace
			|| (event.keyCode == 9)							// tab
			|| (event.keyCode == 46)						// delete
			|| (event.keyCode > 34 && event.keyCode < 41)	// end, home, 방향키
			|| (event.keyCode > 47 && event.keyCode < 58)	// 숫자키
			|| (event.keyCode > 95 && event.keyCode <106)	// 숫자키(넘버자판)
			|| (event.keyCode == 19)						// PAUSE BREAK
			|| (event.keyCode == 20)						// CAPSLOCK
			|| (event.keyCode == 144)						// NUMLOCK
			|| (event.keyCode == 145)						// SCROLLLOCK
			) {
		return true;
	} else {
		alert("숫자만 입력 가능합니다.");
		obj.value = "";
		obj.focus();
		return false;
	}
}

// GET 특수기호 전송 : 이은혜(2011.01.12)
function  specialReplace(str) {
	str = str.replace(/\%/g,"%25");
	str = str.replace(/&/g,"%26"); 
	str = str.replace(/\+/g,"%2B");

    return str;
}

//숫자 앞에 0 붙이기
function leadingZeros(n, digits) {
	var zero = '';
	n = n.toString();

	if (n.length < digits) {
		for (var i = 0; i < digits - n.length; i++) zero += '0';
	}
	return zero + n;
}