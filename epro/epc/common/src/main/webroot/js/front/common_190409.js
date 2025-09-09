/**
 * Window Open <br>
 * Window속성 - 화면가운데 위치 하며 resizable=yes, status=yes, toolbar=no, menubar=no
 * @param url  window의 URL
 * @param name  Window의 명
 * @param widht window폭 (픽셀)
 * @param height window높이 (픽셀)
 * @param scroll scrollbar 여부 (yes OR no)
 * @return window object
 */
//openwindow(strURL, "PhotoFind", width=302,height=132, 'no');

function openwindow(mypage, myname, w, h, scroll) {
	var winl = (screen.width - w) / 2;
	var wint = (screen.height - h) / 2;
	winprops = 'height='+h+',width='+w+',top='+wint+',left='+winl+',scrollbars='+scroll+',resizable';
	win = window.open(mypage, myname, winprops);
	if ( parseInt(navigator.appVersion) >= 4 && win !== null ) { 
		win.window.focus(); 
	}
}

//순수 숫자 체크
function isOnlyNumber(v) {
    var reg = /^(\s|\d)+$/;
    return reg.test(v);
}

//순수 숫자 체크 INPUT박스(IE, FF)
function isOnlyNumberInput(e, decimal) {
    var key;
    var keychar;

    if (window.event) {
       // IE에서 이벤트를 확인하기 위한 설정
        key = window.event.keyCode;
    } else if (e) {
      // FireFox에서 이벤트를 확인하기 위한 설정
        key = e.which;
    } else {
        return true;
    }

    keychar = String.fromCharCode(key);
    
    if ((key == null) || (key == 0) || (key == 8) || (key == 9) || (key == 13) || (key == 27) || (key == 37) || (key == 39) || (key == 46)
    		|| (event.keyCode > 95 && event.keyCode < 106)) {
        return true;
    } else if ((("0123456789").indexOf(keychar) > -1)) {
        return true;
    } else if (decimal && (keychar == ".")) {
        return true;
    } else
        return false;
}

//모두 숫자인지 체크
function isNumber(obj) {
	var i;
	var	str	=	obj.value.trim();

	if (str.length == 0)
		return false;

	for (var i=0; i < str.length; i++) {
		if(!('0' <= str.charAt(i) && str.charAt(i) <= '9'))
			return false;
	}
	return true;
}

//한 Char라도 숫자인지 체크
function isNumber1(obj) {
	var i;
	var	str	=	obj.value.trim();

	if (str.length == 0)
		return false;

	for (var i=0; i < str.length; i++) {
		if('0' <= str.charAt(i) && str.charAt(i) <= '9')
			return true;
	}
	return false;
}

//공백체크
function IsSpace(obj) {
	var i;
	var	str	=	obj.value.trim();

	for(i = 0; i < str.length; i++) {
		if(str.charAt(i) == ' ')
			return true;
	}
	return false;
}

//주민번호 검색
function isSSN(front, back) {
	var hap = 0;
	var	temp;

	for (var i=0; i < 6; i++) {
		var temp = front.charAt(i) * (i+2);
		hap += temp;
	}

	var n1 = back.charAt(0);
	var n2 = back.charAt(1);
	var n3 = back.charAt(2);
	var n4 = back.charAt(3);
	var n5 = back.charAt(4);
	var n6 = back.charAt(5);
	var n7 = back.charAt(6);

	hap += n1*8+n2*9+n3*2+n4*3+n5*4+n6*5;
	hap %= 11;
	hap = 11 - hap;
	hap %= 10;

	if(hap != n7)
		return false;

	return true;
}

//이메일 체크
function isEmail(obj) {
	var str = obj.value.trim();

	if(str == "")
		return false;

	var i = str.indexOf("@");
	if(i < 0)
		return false;

	i = str.indexOf(".");
	if(i < 0)
		return false;

	return true;
}


//알파벳 여부 체크
function isAlphabet(obj) {
	var str = obj.value.trim();

	if(str.length == 0)
		return false;

	str = str.toUpperCase();
	for(var i=0; i < str.length; i++) {
		if(!(('A' <= str.charAt(i) && str.charAt(i) <= 'Z') || ('a' <= str.charAt(i) && str.charAt(i) <= 'z')))
			return false;
	}
	return true;
}

//한문자라도 알파벳인지 여부 체크
function isAlphabet1(obj) {
	var str = obj.value.trim();

	if(str.length == 0)
		return false;

	str = str.toUpperCase();
	for(var i=0; i < str.length; i++) {
		if(('A' <= str.charAt(i) && str.charAt(i) <= 'Z') || ('a' <= str.charAt(i) && str.charAt(i) <= 'z'))
			return true;
	}
	return false;
}


//알파벳과 space인지 여부 체크
function isAlphabet2(obj) {
	var str = obj.value.trim();

	if(str.length == 0)
		return false;

	str = str.toUpperCase();
	for(var i=0; i < str.length; i++) {
		if(('A' <= str.charAt(i) && str.charAt(i) <= 'Z') || ('a' <= str.charAt(i) && str.charAt(i) <= 'z') || (str.charAt(i) == ' '))
			return true;
	}
	return false;
}

//두값이 같은지 체크
function isSame(obj1, obj2) {
	var str1 = obj1.value;
	var str2 = obj2.value;

	if(str1.length == 0 || str2.length == 0)
		return false;

	if(str1 == str2)
		return true;
	return false;
}

//ID 는 숫자와 영어, - , _ 만 가능 체크
function IsID(obj)
{
	obj = obj.toUpperCase();
	if(obj.length < 4) return false;

	for(var i=1; i < obj.length; i++)
	{
		if(!(('A' <= obj.charAt(i) && obj.charAt(i) <= 'Z') ||
			('0' <= obj.charAt(i) && obj.charAt(i) <= '9')))
			return false;
	}
	return true;
}

// 특수문자 체크
function isSpecial(obj) {
	var m_Sp = /[$\\@\\\#%\^\&\*\(\)\[\]\+\_\{\}\'\~\=\|]/;
	var m_val  = obj.value.trim();

	if(m_val.length == 0)
		return false;

	var strLen = m_val.length;
	var m_char = m_val.charAt((strLen) - 1);
	if(m_char.search(m_Sp) != -1)
		return true;

}

//숫자, - 만 가능 체크
function IsTel(obj) {
	var i;
	var	str	=	obj.value.trim();

	for(i = 0; i < str.length; i++) {
		if(!('0' <= str.charAt(i) && str.charAt(i) <= '9') && str.charAt(i) != '-')
		{
			return false;
		}
	}
	return true;
}

//숫자, , 만 가능 체크
function IsCurrency(obj) {
	var i;
	var	str	=	obj.value.trim();

	for(i = 0; i < str.length; i++) {
		if(!('0' <= str.charAt(i) && str.charAt(i) <= '9') && str.charAt(i) != ',')
		{
			return false;
		}
	}
	return true;
}

//숫자만 가능 (onKeyDown="onlyNumber()")
function onlyNumber()
{
	if((event.keyCode != 9 && event.keyCode != 8 && event.keyCode != 46 && event.keyCode != 37 
			&& event.keyCode != 39) && (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105))
	{
		event.returnValue=false;
	}
}

// 3자리 마다 콤마(,) 찍어주는 기능
function setComma(num)
{
	var num = num.toString();
	var rtn = "";
	var val = "";
	var j = 0;
	x = num.length;

	for(i=x; i>0; i--)
	{
		if(num.substring(i,i-1) != ",")
			val = num.substring(i, i-1) + val;
	}
	x = val.length;
	for(i=x; i>0; i--)
	{
		if(j%3 == 0 && j!=0)
			rtn = val.substring(i,i-1)+","+rtn;
		else
			rtn = val.substring(i,i-1)+rtn;

		j++;
	}

	return rtn;
}

//사업자 등록번호 체크
function isBizInteger(st,maxLength)
{
   if (st.length == maxLength)
   {
      for (j=0; j>maxLength; j++)
         if (((st.substring(j, j+1) < "0") || (st.substring(j, j+1) > "9")))
         {
            return false;
         }
   }
   else
   {
      return false;
   }
   return true;
}
//사업자 등록 번호 체크 루틴.
function BizCheck(obj1, obj2, obj3)
{
   biz_value = new Array(10);

   if (isBizInteger(obj1.value,3) == false)
   {
      obj1.focus();
      obj1.select();
      return false;
   }

   if (isBizInteger(obj2.value,2) == false)
   {
      obj2.focus();
      obj2.select();
      return false;
   }

   if (isBizInteger(obj3.value,5) == false)
   {
      obj3.focus();
      obj3.select();
      return false;
   }

   var objstring = obj1.value +"-"+ obj2.value +"-"+ obj3.value;
   var li_temp, li_lastid;

   if ( objstring.length == 12 )
   {
      biz_value[0] = ( parseFloat(objstring.substring(0 ,1)) * 1 ) % 10;
      biz_value[1] = ( parseFloat(objstring.substring(1 ,2)) * 3 ) % 10;
      biz_value[2] = ( parseFloat(objstring.substring(2 ,3)) * 7 ) % 10;
      biz_value[3] = ( parseFloat(objstring.substring(4 ,5)) * 1 ) % 10;
      biz_value[4] = ( parseFloat(objstring.substring(5 ,6)) * 3 ) % 10;
      biz_value[5] = ( parseFloat(objstring.substring(7 ,8)) * 7 ) % 10;
      biz_value[6] = ( parseFloat(objstring.substring(8 ,9)) * 1 ) % 10;
      biz_value[7] = ( parseFloat(objstring.substring(9,10)) * 3 ) % 10;
      li_temp = parseFloat(objstring.substring(10,11)) * 5 + "0";
      biz_value[8] = parseFloat(li_temp.substring(0,1)) + parseFloat(li_temp.substring(1,2));
      biz_value[9] = parseFloat(objstring.substring(11,12));
      li_lastid = (10 - ( ( biz_value[0] + biz_value[1] + biz_value[2] + biz_value[3] + biz_value[4] + biz_value[5] + biz_value[6] + biz_value[7] + biz_value[8] ) % 10 ) ) % 10;

      if (biz_value[9] != li_lastid)
      {
         obj1.focus();
         obj1.select();
         return false;
      }
      else
         return true;
   }
   else
   {
      obj1.focus();
      obj1.select();
      return false;
   }
}

//----------------------------------------------------------------------------------------------------------
/**
 * 체크박스와 라디오버튼의 체크 상태를 확인한다.
 * @param 	check_list
 * @return	선택 : true
 *          미선택 : false
 */
function IsChecked(check_list)
{
	var is_checked = false;
	var length;

	length = check_list.length;

	if (length > 0)
	{
		for (i = 0; i < length; i++)
		{
			if (check_list[i].checked)
			{
				is_checked = true;
				break;
			}
		}
	}
	else
	{
		if (check_list.checked)
		{
			is_checked = true;
		}
	}

	return is_checked;
}
//----------------------------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------------------------
/**
 * 체크박스와 라디오버튼의 체크된 값을 가져온다.
 * @param 	check_list
 * @return	선택 : value
 *          미선택 : ''
 */
function GetCheckedValue(check_list)
{
	var sRetVal = '';
	var length;

	if (!check_list) return '';

	length = check_list.length;

	if (length > 0)
	{
		for (i = 0; i < length; i++)
		{
			if (check_list[i].checked)
			{
				sRetVal = check_list[i].value;
				break;
			}
		}
	}
	else
	{
		if (check_list.checked)
		{
			sRetVal = check_list.value;
		}
	}

	return sRetVal;
}
//----------------------------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------------------------
/**
 * 체크박스와 라디오버튼의 체크를 초기화한다.
 * @param 	check_list
 * @bFlag 	TRUE/FALSE
 */
function SetCheckedValue(check_list, bFlag)
{
	var length;

	if (!check_list) {
		check_list.checked = bFlag;
		return '';
	}

	length = check_list.length;

	if (length > 0)
	{
		length = check_list.length;

		if (length > 0)
		{
			for (i = 0; i < length; i++)
			{
				check_list[i].checked = bFlag;
			}
		}
		else
		{
			check_list.checked = false;
		}
	}
}
//----------------------------------------------------------------------------------------------------------

//----------------------------------------------------------------------------------------------------------
/**
 * 체크박스와 라디오버튼의 disabled 를 초기화한다..
 * @param 	check_list
 * @bFlag 	TRUE/FALSE
 */
function SetCheckedDisabled(check_list, bFlag)
{
	var length;

	if (!check_list) {
		check_list.disabled = bFlag;
		return '';
	}

	length = check_list.length;

	if (length > 0)
	{
		length = check_list.length;

		if (length > 0)
		{
			for (i = 0; i < length; i++)
			{
				check_list[i].disabled = bFlag;
			}
		}
		else
		{
			check_list.disabled = false;
		}
	}
}
//----------------------------------------------------------------------------------------------------------

function dispTelNo (strval) {
	var nRet = true;
	var	s, m, e;

	if (strval.length != 12)
		nRet = false;

	s = strval.mid(0,4);
	m = strval.mid(4,4);
	e = strval.mid(8,4);

	if (nRet)
		document.write (s+'-'+m+'-'+e);
	else
		document.write (strval);

	return nRet;
}

function dispZipNo (strval) {
	var nRet = true;
	var	s, e;

	if (strval.length != 6)
		nRet = false;

	s = strval.mid(0,3);
	e = strval.mid(3,3);

	if (nRet)
		document.write (s+'-'+e);
	else
		document.write (strval);

	return nRet;
}

function makeTelNo (strval) {
	var telno;
	telno = strval.arrSplit('-');

	if (telno.length < 3)
		return strval;

	telno[0] = '0000' + telno[0];
	telno[1] = '0000' + telno[1];
	telno[2] = '0000' + telno[2];

	return telno[0].right(4)+telno[1].right(4)+telno[2].right(4);

}

function makeTelDDD (strval) {
	if (strval.trim() == '')
		return '';

	var telno, retval = '';
	telno = strval.arrSplit('-');

	if (telno.length > 0  && telno.length < 3)
	{
		if (telno[0].left(1) != '0') {
			retval = '02';
			for (i=0;i<telno.length;i++)
				retval = retval +'-'+ telno[i];
		}
	}

	if (retval == '')
		retval = strval;

	return retval;
}

function makeZip (strval) {
	var zipno;

	zipno = strval.arrSplit('-');
	if (zipno.length != 2)
		return strval;

	return zipno[0] + zipno[1];

}

function makeInt (strval) {
	var num, retNum = '';
	num = strval.arrSplit(',');

	for (var i=0; i < num.length ;i++) {
		retNum = retNum + num[i];
	}

	return retNum;

}

function dispLeftStr (strval, length) {
	var retval;

	var aaa = strval.arrSplit('\n');

	retval = strval.left (length) + '...';
	document.write(retval);
}

//한글 길이 체크
function GetLength(sText)
{
	var i;
	var nLength = 0;

	for (i = 0; i < sText.length; i++) 	{
		if (sText.charCodeAt(i) > 128)
			nLength	+= 2;
		else
			nLength	++;
	}

	return nLength;
}

var s1_YesNULL	= '0';
var s1_NoNULL	= '1';
var s2_YesNUM	= '0';
var s2_NoNUM	= '1';
var s2_AllNUM	= '2';
var s3_YesABC	= '0';
var s3_NoABC	= '1';
var s3_AllABC	= '2';

/**
 * CheckTextBoxEx 체크
 * @param 	javaFlag	:	javascript flag (Y or N)
 * @param 	sChkType	:	s1_NoNULL + s2_NoNUM + s3_AllABC 등이 형태로 조합된다.
 * 							s1_YesNULL		: TextBox에 Space 가 들어갈수있다.
 * 							s1_NoNULL		: TextBox에 Space 가 들어갈수없다.
 * 							s2_YesNUM		: TextBox에 숫자가 들어갈수있다.
 * 							s2_NoNUM		: TextBox에 숫자가 들어갈수없다.
 * 							s2_AllNUM		: TextBox에 숫자만 들어갈수있다.
 * 							s3_YesABC		: TextBox에 알파벳이 들어갈수있다.
 * 							s3_NoABC		: TextBox에 알파벳이 들어갈수없다.
 * 							s3_AllABC		: TextBox에 알파벳만 들어갈수있다.
 * @param 	nLength		:	text length
 * @param 	obj			:	text object
 * @param 	sMsg		:	ErrorString
 * @return 	true or false
 */

function CheckTextBoxEx (javaFlag, sChkType, nLength, obj, sMsg) {

	if (!obj)
	{
		alert ("["+ sMsg + "] Text Object Not Found...");
		return false;
	}

	if (sChkType.length != 3)
	{
		alert ("sChkType Error...!!!");
		return false;
	}

	var	bChk = true;

	if (javaFlag == 'Y') {
	// 자바스크립트 체크 플래그 TRUE

		if (obj.value.trim().length == 0) {
			alert ("["+sMsg+"]" + " 항목을 입력하세요.");
			obj.focus();
			return false;
		}
	} else {
	// 자바스크립트 체크 플래그 FALSE
		if (obj.value.length == 0) {
		// 입력받지 안았다면
			bChk = false;
		}
	}


	if (bChk) {

		if (GetLength(obj.value) > nLength)
		{
			alert ( "["+sMsg+"]" + " 항목의 길이는 " + nLength + " 보다 작아야 합니다.");
			obj.focus();
			return false;
		}

		if (sChkType.substring(0,1) == s1_NoNULL)		// 공백 체크
		{
			if (IsSpace(obj)) {
			// 빈문자가 입력되었는지 체크
				alert ("["+sMsg+"]" + " 항목에는 공백이 들어갈수 없습니다.");
				obj.focus();
				return false;
			}
		}

		if (sChkType.substring(1,2) == s2_NoNUM)		// 숫자체크
		{
			if (isNumber1(obj))
			{
			// 숫자가 입력되었는지 체크
				alert ("["+sMsg+"]"+" 항목에는 숫자를 입력할 수 없습니다.");
				obj.focus();
				return false;
			}

		}

		if (sChkType.substring(1,2) == s2_AllNUM)		// 숫자체크
		{
			if (!isNumber(obj))
			{
			// 숫자가 입력되었는지 체크
				alert ("["+sMsg+"]"+" 항목은 숫자만 입력가능합니다.");
				obj.focus();
				return false;
			}

		}

		if (sChkType.substring(2,3) == s3_NoABC)		// 알파벳체크
		{
			if (isAlphabet1(obj))
			{
			// 알파벳이 입력되었는지 체크
				alert ("["+sMsg+"]"+" 항목에는 알파벳을 입력할 수 없습니다.");
				obj.focus();
				return false;
			}
		}

		if (sChkType.substring(2,3) == s3_AllABC)		// 알파벳체크
		{
			if (!isAlphabet(obj))
			{
			// 숫자가 입력되었는지 체크
				alert ("["+sMsg+"]"+" 항목은 알파벳만 입력가능합니다.");
				obj.focus();
				return false;
			}
		}

	}

	return true;
}

/**
 * Radio, Check 체크
 * @param 	javaFlag	:	javascript flag (Y or N)
 * @param 	obj			:	radio, check object
 * @return 	true or false
 */
function CheckRadioObject (javaFlag, obj, sMsg) {

	if (!obj)
	{
		alert ("["+ sMsg + "] objRadio Object Not Found...");
		return false;
	}

	if (javaFlag == 'Y') {
		if (!IsChecked(obj))
		{
			alert ("["+sMsg+"]" + " 항목을 선택하세요.");
			return false;
		}
	}

	return true;
}

/**
 * Combo 체크
 * @param 	javaFlag		:	javascript flag (Y or N)
 * @param 	obj		:	combo object
 * @return 	true or false
 */
function CheckComboObject (javaFlag, obj, sMsg) {

	if (!obj)
	{
		alert ("["+ sMsg + "] objCombo Object Not Found...");
		return false;
	}

	if (javaFlag == 'Y') {
	// 자바스크립트 체크 플래그 TRUE
		if (obj.options[obj.selectedIndex].value.length == 0) {
			alert ("["+sMsg+"]" + " 항목을 선택하세요.");
			obj.focus();
			return false;
		}
	}

	return true;
}

 //--------TRIM() START----------

function _private_arrSplit(split) {
	var tmpStr;
	var i ;
	var iCnt;
	var iEnd;
	tmpStr = this;

	iCnt = 0;
	for( i = 0 ; i < tmpStr.length ; i++) {
		if (tmpStr.charAt(i) == split) {
			iCnt++;
		}
	}
	iCnt++;

	arr_str = new Array(iCnt);

	for (i = 0 ; i < iCnt ; i++)	{
		iEnd = tmpStr.indexOf(split);
		if (iEnd < 0)
			arr_str[i] = tmpStr;
		else{
			arr_str[i] = tmpStr.substring(0,iEnd);
			tmpStr = tmpStr.substring(iEnd+1);
		}
	}

	return arr_str;
}

function _private_trim() {
  var tmpStr, atChar;
  tmpStr = this;

  if (tmpStr.length > 0) atChar = tmpStr.charAt(0);
  while (_private_stringvb_isSpace(atChar)) {
    tmpStr = tmpStr.substring(1, tmpStr.length);
    atChar = tmpStr.charAt(0);
  }
  if (tmpStr.length > 0) atChar = tmpStr.charAt(tmpStr.length-1);
  while (_private_stringvb_isSpace(atChar)) {
    tmpStr = tmpStr.substring(0,( tmpStr.length-1));
    atChar = tmpStr.charAt(tmpStr.length-1);
  }
  return tmpStr;
}

function _private_left(inLen) {
  return this.substring(0,inLen);
}

function _private_right(inLen) {
  return this.substring((this.length-inLen),this.length);
}

function _private_mid(inStart,inLen) {
  var iEnd;
  if (!inLen)
    iEnd = this.length;
  else
    iEnd = inStart + inLen;
  return this.substring(inStart,iEnd);
}

function _private_stringvb_isSpace(inChar) {
  return (inChar == ' ' || inChar == '\t' || inChar == '\n');
}

String.prototype.trim     = _private_trim;
String.prototype.left     = _private_left;
String.prototype.right    = _private_right;
String.prototype.mid      = _private_mid;
String.prototype.arrSplit =_private_arrSplit;
//--------TRIM() END----------

/**
 * 아이디형태의 값검증
 */
function checkIdType(in_str){
	var pattern = new RegExp('[^ㄱ-ㅎ가-힣a-zA-Z0-9]', 'i');
	if (pattern.exec(in_str) != null) {
		//alert("한글, 영문, 숫자만 가능합니다.");
		return false;
	}else{
		return true;
	}
}

/**
 * 한글만 가능
 */
function checkHangulType(in_str){
	var pattern = new RegExp('[^ㄱ-ㅎ가-힣]', 'i');
	if (pattern.exec(in_str) != null) {
		//alert("한글 가능합니다.");
		return false;
	}else{
		return true;
	}
}

/**
 * 특수문자 존재여부
 */
function checkSpecialType(in_str) {
	var m_Sp = /[$\\@\\\#%\^\&\*\(\)\[\]\+\_\{\}\'\~\=\|\<\>]/;
	var m_val  = in_str;

	if(m_val.length == 0)
		return true;

	var strLen = m_val.length;
	var m_char = m_val.charAt((strLen) - 1);
	if(m_char.search(m_Sp) != -1)
		return false;

}

/**
 * 문자열의 Byte 수를 리턴하는 함수
 */
function checkMaxLength(str) {

	if (str == null || str.length == 0) {
      return 0;
    }
    var size = 0;

    for (var i = 0; i < str.length; i++) {
      size += charByteSize(str.charAt(i));
    }
    return size;
}

function charByteSize(ch) {
    if (ch == null || ch.length == 0) {
      return 0;
    }

    var charCode = ch.charCodeAt(0);

    if (charCode <= 0x00007F) {
      return 1;
    } else if (charCode <= 0x0007FF) {
      return 2;
    } else if (charCode <= 0x00FFFF) {
      return 3;
    } else {
      return 4;
    }
}

function phone_format(num){
	return num.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
}


var isNN = (navigator.appName.indexOf("Netscape")!=-1);
function autoTab(input,len, e){
	var keyCode = (isNN) ? e.which : e.keyCode;
	var filter = (isNN) ? [0,8,9] : [0,8,9,16,17,18,37,38,39,40,46];

	if(input.value.length >= len && !containsElement(filter,keyCode)) {
		input.value = input.value.slice(0, len);
		input.form[(getIndex(input)+1) % input.form.length].focus();
	}

	function containsElement(arr, ele) {
		var found = false, index = 0;
		while(!found && index < arr.length)
			if(arr[index] == ele)
				found = true;
			else
				index++;
		return found;
	}

	function getIndex(input) {
		var index = -1, i = 0, found = false;
		while (i < input.form.length && index == -1)
			if (input.form[i] == input)index = i;
			else i++;
		return index;
	}
	return true;
}

function num_only(fl){

 t = fl.value ;

	for(i=0;i<t.length;i++)
		if (t.charAt(i)<'0' || t.charAt(i)>'9') {
			alert("숫자만 입력해주세요.") ;
			 fl.value="";
			 fl.focus() ;
			 return false ;
		}
}

String.prototype.trim = function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 숫자, - 만 가능
 */
function checkPhoneNumberType(in_str){
	var pattern = new RegExp('[^0-9\-]', 'i');
	if (pattern.exec(in_str) != null) {
		return false;
	}else{
		return true;
	}
}

/**
 * input element의 hidden type object를 생성하여 반환한다.
 *
 * @param elemName element의 이름
 * @param elemValue element의 값
 * @return input element (&lt;input type="hidden" name="elemName" value="elemValue"&gt;)
 */
function genDomInput(elemName, elemValue){
	var input = document.createElement("input");
	input.setAttribute("type", "hidden");
	input.setAttribute("name", elemName);
	input.setAttribute("id", elemName);
	input.setAttribute("value", elemValue);
	return input;
}

/**
 * 인수로 받은 object가 배열인지 판단 한다.
 * - null이면 0을 리턴
 * - 배열이 아니면 1을 리턴
 * - 배열이라면 배열 길이를 리턴
 * </pre>
 * @param obj 검사할 form.element
 * @return number (0, 1, obj.length)
 */
function isArray(obj){
	if(obj == null){
		return 0;
	}else {
		//alert(obj.type);
		if(obj.type == 'select-one'){
			return 1;
		}else if(obj.type == 'select-multiple'){
			return 1;
		}else{
			if(obj.length > 1){
				return obj.length;
			}else {
				return 1;
			}
		}
	}
}

//날짜추가
function fnAddDay( targetDate  , dayPrefix ){
	var newDate = new Date();
	var processTime = targetDate.getTime() + ( parseInt(dayPrefix) * 24 * 60 * 60 * 1000 );
	newDate.setTime(processTime);
	return newDate;
}

//마이마트 공통 날짜 선택 기능 from ~ to
function fnSetDate(setGbn){
	var dt = new Date();
	var todayYYYY =  dt.getFullYear().toString();
	var todayMM =  (dt.getMonth() + 1).toString();
	var todayDD =  dt.getDate().toString();

	toYYYY = todayYYYY;
	toMM = lpad(todayMM, 2, "0");
	toDD = lpad(todayDD, 2, "0");

	if(setGbn == "today"){
		fromYYYY = todayYYYY;
		fromMM = lpad(todayMM, 2, "0");
		fromDD = lpad(todayDD, 2, "0");
	}else if(setGbn == "3day"){
		fromDt = fnAddDay(dt, -3);
		fromYYYY = fromDt.getFullYear().toString();
		fromMM = lpad((fromDt.getMonth() + 1).toString(), 2, "0");
		fromDD = lpad(fromDt.getDate().toString(), 2, "0");;
	}else if(setGbn == "7day"){
		fromDt = fnAddDay(dt, -7);
		fromYYYY = fromDt.getFullYear().toString();
		fromMM = lpad((fromDt.getMonth() + 1).toString(), 2, "0");
		fromDD = lpad(fromDt.getDate().toString(), 2, "0");
	}else if(setGbn == "15day"){
		fromDt = fnAddDay(dt, -15);
		fromYYYY = fromDt.getFullYear().toString();
		fromMM = lpad((fromDt.getMonth() + 1).toString(), 2, "0");
		fromDD = lpad(fromDt.getDate().toString(), 2, "0");
	}else if(setGbn == "1month"){
		fromDt = fnAddDay(dt, -30);
		fromYYYY = fromDt.getFullYear().toString();
		fromMM = lpad((fromDt.getMonth() + 1).toString(), 2, "0");
		fromDD = lpad(fromDt.getDate().toString(), 2, "0");
	}else if(setGbn == "3month"){
		fromDt = fnAddDay(dt, -90);
		fromYYYY = fromDt.getFullYear().toString();
		fromMM = lpad((fromDt.getMonth() + 1).toString(), 2, "0");
		fromDD = lpad(fromDt.getDate().toString(), 2, "0");
	}

	$("#selectFromYear option[value='"+ fromYYYY +"']").attr("selected", "selected");
	$("#selectFromMonth option[value='"+ fromMM +"']").attr("selected", "selected");
	fnSetDay('From');
	setTimeout("", 2000);
	$("#selectFromDay option[value='"+ fromDD +"']").attr("selected", "selected");

	$("#selectToYear option[value='"+ toYYYY +"']").attr("selected", "selected");
	$("#selectToMonth option[value='"+ toMM +"']").attr("selected", "selected");
	fnSetDay('To');
	setTimeout("", 2000);
	$("#selectToDay option[value='"+ toDD +"']").attr("selected", "selected");

	$.each($(".list_d").children(), function(i) {
		$(this).removeClass();
	});
	
	$("#"+ setGbn +"Enable").addClass("on");
	$("#setDateArrange").val(setGbn);
}

function fnSetDay(fromTo) {
	var $yyObj = $("#select"+fromTo+"Year");
	var $mmObj = $("#select"+fromTo+"Month");
	var $ddObj = $("#select"+fromTo+"Day");

	var yy = $("#select"+fromTo+"Year").val();
	var mm = $("#select"+fromTo+"Month").val();
	var dd = $("#select"+fromTo+"Day").val();
	
	var dayMax = 31;

	if( mm == "04" || mm == "06" || mm == "09" || mm == "11" ) {
	    dayMax = 30;
	}else if( mm == "02" ) {
	    if ((yy % 4) == 0) {
	        if ((yy % 100) == 0 && (yy % 400) != 0) {
	            dayMax = 28;
	        } else {
	            dayMax = 29;
	        }
        } else {
            dayMax = 28;
        }
	}

	$ddObj.empty();

	for( var i=0; i<dayMax; i++) {
		if( i < 9 ) {
			$ddObj.append("<option value='"+ "0"+(i+1) +"'>"+ "0"+(i+1) +"</option>");
		} else {
			$ddObj.append("<option value='"+ (i+1) +"'>"+ (i+1) +"</option>");
		}
	}
}


/*
 * 설명      : js에서 메세지 국제화 던질때, 파라미터 지정 가능
 * 예제      : 메세지 프로퍼티가 아래와 같을때
 * 		   msg.category.error.productOrderMax={0}상품은 {1}개 까지만 주문 가능합니다.
 * 사용법 : alert(fnJsMsg('<spring:message code="msg.category.error.productOrderMax"/>', '공룡', '100'));	//{0}상품은 {1}개 까지만 주문 가능합니다.
 * 결과      : 공룡 상품은 100개 까지만 주문 가능합니다.
*/
function fnJsMsg() {
	 var arg= arguments;
	 if(arg.length==0) return '';
	 if(arg.length==1) return arg[0];

	 var fn = function(w, g) {
	  if(isNaN(g)) return '';
	  var idx = parseInt(g)+1;
	  if(idx >= arg.length) return '';
	  return arg[parseInt(g)+1];
	 };
	 return arg[0].replace(/\{([0-9]*)\}/g, fn);
	}

/**
 * SSO 회원 로그인 팝업
 * @param sid MARTMALL
 * @param returnURL 되돌아 갈 페이지
 */
function goLogin(sid, returnURL)
{
	if( _LMLocalDomain == "true" ) {
		openurl = _LMMembersAppSSLUrl + "/imember/login/login.do?sid=" + sid + "&returnurl=" + encodeURIComponent(returnURL)+ "&mode=DEV";
	} else {
		openurl = _LMMembersAppSSLUrl + "/imember/login/login.do?sid=" + sid + "&returnurl=" + encodeURIComponent(returnURL);			
	}	
	
	location.href = openurl;
}

/**
 * 로그아웃
 */
function logout(returnURL, linkURL)
{
	var url;		
		
	if( _LMFamilyLoginYn == "Y" ) {
		if( _LMLocalDomain == "true" ) {
			url = _LMMembersAppSSLUrl + (!linkURL ? "/imember/login/ssoLogoutPop.do" : linkURL) + "?sid="+_SID_NM_MARTMALL+"&kind=f&SITELOC=AA005&returnurl=" + encodeURIComponent(returnURL)+ "&mode=DEV";
		}else {
			url = _LMMembersAppSSLUrl + (!linkURL ? "/imember/login/ssoLogoutPop.do" : linkURL) + "?sid="+_SID_NM_MARTMALL+"&kind=f&SITELOC=AA005&returnurl=" + encodeURIComponent(returnURL);
		}

	} else {
		
		if( _LMLocalDomain == "true" ) {
			url = _LMMembersAppSSLUrl + (!linkURL ? "/imember/login/ssoLogoutPop.do" : linkURL) + "?sid="+_SID_NM_MARTMALL+"&kind=m&SITELOC=AA005&returnurl=" + encodeURIComponent(returnURL)+ "&mode=DEV&mart=Y";
		} else {
			url = _LMMembersAppSSLUrl + (!linkURL ? "/imember/login/ssoLogoutPop.do" : linkURL) + "?sid="+_SID_NM_MARTMALL+"&kind=m&SITELOC=AA005&returnurl=" + encodeURIComponent(returnURL) + "&mart=Y";
		}
		
	}		

	$("<iframe>").attr({"src":url}).css({"width":"0px", "height":"0px"}).appendTo("body");

}

/**
 * B2E 로그아웃
 */
function b2eLogout(returnURL, linkURL)
{
	var url = _LMMembersAppSSLUrl + (!linkURL ? "/login/ssoLogoutPop.do" : linkURL) + "?returnURL=" + encodeURIComponent(returnURL) + "&SITELOC=AA005";
	location.href =  url;
}

/**
 * 비회원로그인
 */
function goNoMember(sid, returnURL)
{
    location.href =  _LMNoMemberAppSSLUrl+"/login/noMemLoginSeed.do?sid=" + sid + "&returnURL=" + returnURL;

}

/**
 * 비회원 주문/배송조회
 */
function goOrdSearchLogin(returnURL)
{
    location.href =  _LMNoMemberAppSSLUrl +"/login/searchOrderNoMemLogin.do?returnURL=" + returnURL;
}

/**
 * 비회원 주문/배송조회 팝업
 */
function goOrdSearchLoginPop(sid, returnURL)
{
	//var openurl =  _LMNoMemberAppSSLUrl + "/login/searchOrderNoMemLogin.do?sid=" + sid + "&returnURL=" + returnURL;
	var openurl =  _LMNoMemberAppSSLUrl + "/login/noMemLoginSeed.do?type=order&sid=" + sid + "&returnURL=" + returnURL;
	var popup = window.open(openurl, "searchOrderNoMemPop", "toolbar=no,location=no,directories=no,status=no,scrollbars=yes, resizable=yes, menubar=no,width=500,height=500,dependent=yes");
    if (popup != null) popup.focus();
}


/**
 * 롯데마트회원가입사이트 - 회원가입
 */
function goJoin(sid, returnURL)
{
	window.open(_LMMembersAppSSLUrl+"/user/registMemCheckForm.do?sid="+sid+"&returnURL="+ returnURL, "MartMember","");

}


/**
 * 롯데마트회원가입사이트 - 회원정보변경(내국인)
 */
function goChangeUserMem(sid, returnURL)
{
	window.open(_LMMembersAppSSLUrl+"/user/registMemChangeForm.do?sid="+sid+"&returnURL="+ returnURL, "MartMember","");

}


/**
 * 롯데마트회원가입사이트 - 회원정보변경(외국인)
 */
function goChangeUserFgn(sid, returnURL)
{
	window.open(_LMMembersAppSSLUrl+"/user/registFgnChangeForm.do?sid="+sid+"&returnURL="+ returnURL, "MartMember","");
}


/**
 * 롯데마트회원가입사이트 - 회원정보변경(사업자)
 */
function goChangeUserCor(sid, returnURL)
{
	window.open(_LMMembersAppSSLUrl+"/user/registCorChangeForm.do?sid="+sid+"&returnURL="+ returnURL, "MartMember","");

}


/**
 * 롯데마트회원가입사이트 - 비밀번호변경
 */
function goChangePw(sid, returnURL)
{
	window.open(_LMMembersAppSSLUrl+"/user/changePasswordForm.do?sid="+sid+"&returnURL="+ returnURL, "MartMember","");
}


/**
 * 롯데마트회원가입사이트 - 아이디비밀번호찾기
 */
function goSearchIdPw(sid, returnURL)
{
	window.open(_LMMembersAppSSLUrl+"/user/searchIdPasswordPreForm.do?sid="+sid+"&returnURL="+ returnURL, "MartMember","");
}

/**
 * 롯데마트회원가입사이트
 */
function goWithDraw(sid, returnURL)
{
	window.open(_LMMembersAppSSLUrl+"/user/registWithDrawGuide.do?sid="+sid+"&returnURL="+ returnURL, "MartMember","");
}


/**
 * 체크박스 전체선택/해제
 */
function fnCheckTogle(){
	var trgtObj = $("#all_check").attr("class");
    var checked = $("#all_check").attr("checked");
    var msgCheck;
    console.log(trgtObj + " : " + checked);
    if(checked == "checked"){
    	msgCheck = true;
    }else{
    	msgCheck = false;
    }

    $("input:checkbox[name='"+ trgtObj +"']").each(function(){
    	$(this).attr("checked", msgCheck);
    });    
}
/**
 * 체크박스 전체선택/해제
 */
function fnCheckTogleNew(){
	var trgtObj = $("#all_check").attr("class");
    var checkYn = $("#all_check").prop("checked");
    var msgCheck;
    if(checkYn){
    	msgCheck = true;
    }else{
    	msgCheck = false;
    }

    $("input:checkbox[name='"+ trgtObj +"']").each(function(){
    	$(this).attr("checked", msgCheck);
    	if(msgCheck){
    		$(this).parent().addClass('active');
    		$(this).parent().removeClass('focus');
    	}else{
    		$(this).parent().removeClass('active');
    		$(this).parent().addClass('focus');
    	}
    });    
    
}
/**
 * 통합회원 - 비밀번호변경
 */
function goChangePwIntegration(sid, returnurl) {
	window.open(_LMMembersAppSSLUrl + "/imember/member/password01.do?sid=" + sid +"&returnurl="+encodeURIComponent(returnurl), "MartMember","");
}

/**
 * 통합회원 - 회원정보변경
 */
function goChangeUserIntegration(sid, returnurl) {
	window.open(_LMMembersAppSSLUrl + "/imember/member/form.do?sid=" + sid +"&returnurl="+encodeURIComponent(returnurl), "MartMember","");
}

/**
 * 통합회원 - 탈퇴
 */
function goWithDrawIntegration(sid, returnurl) {
	window.open(_LMMembersAppSSLUrl + "/imember/member/withdrawal/request.do?sid=" + sid +"&returnurl="+encodeURIComponent(returnurl), "MartMember","");
}


/**
 * 통합회원 - 비밀번호변경
 */
function goChangePwIntegrationPop(sid, returnurl) {
	var popup;
    var openurl = _LMMembersAppSSLUrl + "/imember/member/password01.do?sid=" + sid +"&returnurl="+encodeURIComponent(returnurl);

	popup = window.open(openurl, "goChangePwIntegration", "toolbar=no,location=no,directories=no,status=no,scrollbars=yes,resizable=yes, menubar=no,width=1000,height=600,dependent=yes");
	if (popup != null) popup.focus();
}

/**
 * 통합회원 - 회원정보변경
 */
function goChangeUserIntegrationPop(sid, returnurl) {
	var popup;
    var openurl  = _LMMembersAppSSLUrl + "/imember/member/form.do?sid=" + sid +"&returnurl="+encodeURIComponent(returnurl);

	popup = window.open(openurl, "goChangeUserIntegration", "toolbar=no,location=no,directories=no,status=no,scrollbars=yes,resizable=yes, menubar=no,width=1000,height=600,dependent=yes");
	if (popup != null) popup.focus();

}

/**
 * 통합회원 - 탈퇴
 */
function goWithDrawIntegrationPop(sid, returnurl) {
	var popup;
    var openurl = _LMMembersAppSSLUrl + "/imember/member/withdrawal/request.do?sid=" + sid +"&returnurl="+encodeURIComponent(returnurl);

	popup = window.open(openurl, "goWithDrawIntegration", "toolbar=no,location=no,directories=no,status=no,scrollbars=yes,resizable=yes, menubar=no,width=1000,height=600,dependent=yes");
	if (popup != null) popup.focus();
	
}

/**
 * 	주민등록번호 체크 - jaeyulim
 * 
    주민번호 검증공식 - 마지막 자리를 제외한 앞자리수를 규칙에 맞게 곱한다.
	123456-1234567
	****** ******
	234567 892345
	곱한 각각에 자리수를 더한다.
	더한수를 11 로 나눈 후 나눈 나머지 수를 구한다.
	나눈 나머지 수를 11에 뺀다.
	11 - (각각더한수%11)
	주민번호 마지막 번호와 일치하면 검증일치 
 */
function checkIdno(juminNo1, juminNo2) {
	
	var jumin = juminNo1 +"-"+ juminNo2;
	
	if (jumin.match(/^\d{2}[0-1]\d[0-3]\d-[1-4]\d{6}$/) == null) {
		alert("유효하지 않은 주민등록번호 입니다.");
		return false;
	}

	var chk = 0;
	var i;
	var last_num = jumin.substring(13, 14);
	var chk_num = '234567-892345';

	for (i = 0; i < 13; i++) {
		if (jumin.charAt(i) != '-')
			chk += (parseInt(chk_num.charAt(i)) * parseInt(jumin.charAt(i)));
	}

	chk = (11 - (chk % 11)) % 10;

	if (chk != last_num){
		alert("유효하지 않은 주민등록번호 입니다.");
		return false;
	}

	return true;
}


//날짜 형식 : yyyymmdd --> yyyy[.]mm[.]dd
function setDateFormat(date, split) {
	if( date === undefined ) {
		return '';
	}

	if (date.length < 8 ) {
		return "";
	}
	
	date = date.trim();
	
	return date.substring(0, 4) + split + date.substring(4, 6) + split + date.substring(6, 8);
}

//입력 byte 카운팅
//추가			: 정길준
//input		: 키보드 입력 값
//max 			: 제한할 최대 값
//targetName	: 입력 바이트를 표시할 name
//이벤트는 	: onkeyup
function checkByte(input, max, targetName) {
	var str = input.value; 
	var strLength = str.length;		// 전체길이
	
	//변수초기화
	var inputByte = 0;				// 한글일 경우 3, 그 외 글자는 1을 더함
	var charLength = 0;				// substring하기 위해 사용
	var oneChar = "";				// 한글자씩 검사
	var strTemp = "";				// 글자수를 초과하면 제한한 글자전까지만 보여줌.
	
	for(var i=0; i< strLength; i++) {
		oneChar = str.charAt(i);	// 한 글자 추출
		if (escape(oneChar).length > 4) { 
			inputByte +=3;			// 한글이면 3를 더한다
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
				tempByte +=3;
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
