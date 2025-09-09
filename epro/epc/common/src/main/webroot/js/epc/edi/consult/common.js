// 이미지 확장자 추출 공통함수
function getFileExtension(filePath)
{
  var lastIndex = -1;
  lastIndex = filePath.lastIndexOf('.');
  var extension = "";

  if ( lastIndex != -1 ) {
    extension = filePath.substring( lastIndex+1, filePath.len );
  }
  else {
    extension = "";
  }

  return extension;
}

// 이미지 체크 공통함수
function resetImage(obj)
{
  // obj.select();
  // document.selection.clear();
  // document.execCommand('Delete');
  obj.outerHTML = obj.outerHTML
}

// 파일 찾아보기 하면 바로 이미지 나오게 하는 스크립트
function uploadImageCheck(obj, maxsize, preView)
{
	
  var value = obj.value;
  var src = getFileExtension(value);
  if (src == "") {
    alert('올바른 파일을 입력하세요!');
    resetImage(obj);
    return;
  }
  else if ( !((src.toLowerCase() == "gif") || (src.toLowerCase() == "jpg") || (src.toLowerCase() == "jpeg")) ) {
    alert('gif 와 jpg 파일만 지원합니다.');
    resetImage(obj);
    return;
  }

  imageCall(obj, value, maxsize, preView);
}



function imageCall(obj, value, maxsize, preView)
{	
	if(preView == 't')
	{		
		var imgInfo = new Image();
		imgInfo.onload = imageLoad;
		imgInfo.maxsize = maxsize;
		imgInfo.obj = obj;
		imgInfo.src = value;
	}
	else
	{		
		var imgInfo = new Image();
		imgInfo.onload = imageLoad2;
		imgInfo.maxsize = maxsize;
		imgInfo.obj = obj;
		imgInfo.src = value;
	}
	
}


function imageLoad()
{
  var imgSrc, imgWidth, imgHeight, imgFileSize;
  var maxFileSize;
  imgObj = this.obj
  imgSrc = this.src;
  imgFileSize = this.fileSize;
  imgMaxSize = this.maxsize;
  imgWidth = "145";//this.width;
  imgHeight = "180";//this.height;

  if (imgMaxSize == "300K") {
    maxFileSize = 307200; // 최대 300KB까지
  }
  else if (imgMaxSize == "1M") {
    maxFileSize = 1024000; // 최대 1MB까지
  }
  else if (imgMaxSize == "2M") {
    maxFileSize = 1024*1024*2; // 최대 2MB까지
  }

  if (imgSrc == "" || imgWidth <= 0 || imgHeight <= 0) {
    alert('그림파일을 가져올 수 없습니다.');
    resetImage(imgObj);
    // onLoad 대상 아무일도 하지않는 객체로 변경하도록 한다.
    // 아래와 같이 하지 않으면 imageLoad1 객체를 계속 호출하여 작동된다.
    this.onload = tempBlank;
    return;
  }

  if (imgFileSize > maxFileSize) {
    alert('선택하신 그림 파일은 허용 최대크기인 ' + maxFileSize/1024 + ' KB 를 초과하였습니다.');
    resetImage(imgObj);
    // onLoad 대상 아무일도 하지않는 객체로 변경하도록 한다.
    // 아래와 같이 하지 않으면 imageLoad1 객체를 계속 호출하여 작동된다.
    this.onload = tempBlank;
    return;
  }

  document.MyForm.img2.src    = imgSrc;
  //document.MyForm.img1.width  = imgWidth;
  //document.MyForm.img1.height = imgHeight;
}

function imageLoad2()
{
  var imgSrc, imgWidth, imgHeight, imgFileSize;
  var maxFileSize;
  imgObj = this.obj
  imgSrc = this.src;
  imgFileSize = this.fileSize;
  imgMaxSize = this.maxsize;
  imgWidth = "145";//this.width;
  imgHeight = "180";//this.height;

  if (imgMaxSize == "300K") {
    maxFileSize = 307200; // 최대 300KB까지
  }
  else if (imgMaxSize == "1M") {
    maxFileSize = 1024000; // 최대 1MB까지
  }
  else if (imgMaxSize == "2M") {
    maxFileSize = 1024*1024*2; // 최대 2MB까지
  }

  if (imgSrc == "" || imgWidth <= 0 || imgHeight <= 0) {
    alert('그림파일을 가져올 수 없습니다.');
    resetImage(imgObj);
    // onLoad 대상 아무일도 하지않는 객체로 변경하도록 한다.
    // 아래와 같이 하지 않으면 imageLoad1 객체를 계속 호출하여 작동된다.
    this.onload = tempBlank;
    return;
  }

  if (imgFileSize > maxFileSize) {
    alert('선택하신 그림 파일은 허용 최대크기인 ' + maxFileSize/1024 + ' KB 를 초과하였습니다.');
    resetImage(imgObj);
    // onLoad 대상 아무일도 하지않는 객체로 변경하도록 한다.
    // 아래와 같이 하지 않으면 imageLoad1 객체를 계속 호출하여 작동된다.
    this.onload = tempBlank;
    return;
  }
}


function uploadImageCheck1(obj, maxsize)
{
  var value = obj.value;
  var src = getFileExtension(value);
  if (src == "")
  {
	alert('올바른 파일을 입력하세요!');
    resetImage(obj);
    return;
  }
  else  
  {	 
	if((src == "doc") || (src == "xls" || src == "ppt"))
	{
	}
	else	
	{
		alert('올바른 파일을 입력하세요!!\n첨부 문서는 아래 문서만 가능합니다.\n(MS워드, 엑셀, 파워포인트)');
		resetImage(obj);
		return;
	}
  } 
  
  
 
}



// imageLoad / imageLoad1 작동중 오류발생시 사용되는 함수
function tempBlank() { }


//팝업 막힌경우 안내페이지로
function init()
{
	if ( getCookieValue( "pop_summer" ) != "done" )
	{
		main_popWindow = window.open('/irsweb/resort/popup/summer/pop.htm','event3','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,width=700,height=250,scrollbars=no,left=50,top=0');

		if (main_popWindow == null)
		{
			if(confirm("현재 유저님의 컴퓨터에는 한화리조트의 팝업창이 차단되어있습니다.\n한화리조트의 모든 서비스를 정상적으로 받기 위해서는 한화리조트의 팝업창을 허용해 주셔야 합니다.\n팝업창 허용에 관한 안내페이지를 보시겠습니까?"))
			location.href="http://www.hanwharesort.co.kr/irsweb/resort/top/cs_faq_read.asp?num=121";
		}
		main_popWindow.opener = self;
	}
}

/*
' ------------------------------------------------------------------
' Function    : displayComma(inAmtTag, inputAmt)
' Description : 상품원가, 상품매가, 월평균매출등과 같은 숫자값이 입력될 경우
'               숫자 3자리 마다 ','를 찍어준다.
' Argument    : inAmtTag - 입력되는 input Tag
				inputAmt - 입력되는 input Tag의 값(inAmtTag.value)
' Return      : 
' ------------------------------------------------------------------
*/

function displayComma(inAmtTag, inputAmt) { 
    //넘어온 inputAmt 값에서 ','를 제거
    var strAmt = removeChar(inputAmt.toString(), ",");
    //','가 제거된 값을 int형식으로 변환, 숫자 이외에 값이 들어가면 '0'을 리턴
    var intAmt = toInt(strAmt);
    var strTemp = "";
    var intCommaPos = 0;
    var intInputAmtLen = 0;
    
    //해당 값의 자리수가 4자리가 넘는 경우(','가 필요한경우)
    if (intAmt >= 1000) {

        strAmt = new String(intAmt);
        strTemp = "";
        intCommaPos = 3;
        intInputAmtLen = strAmt.length;
        while (intInputAmtLen > 0) { 
            intInputAmtLen = intInputAmtLen - intCommaPos; 
            if(intInputAmtLen < 0) {
                intCommaPos = intInputAmtLen + intCommaPos;
                intInputAmtLen = 0;
            } 
            strTemp = "," + strAmt.substr(intInputAmtLen, intCommaPos) + strTemp             
        } 
            
        inAmtTag.value =  "" + strTemp.substr(1);
    }
    //3자리 이하의 수는 그냥 처리
    else {
        inAmtTag.value.length > 0 ? inAmtTag.value =  "" + intAmt : inAmtTag.value =  "";
    }
    

}


function displayComma_2(inputAmt) {     
    //해당 값의 자리수가 4자리가 넘는 경우(','가 필요한경우)
    if (intAmt >= 1000) {
        strAmt = new String(intAmt);
        strTemp = "";
        intCommaPos = 3;
        intInputAmtLen = strAmt.length;
        while (intInputAmtLen > 0) { 
            intInputAmtLen = intInputAmtLen - intCommaPos; 
            if(intInputAmtLen < 0) {
                intCommaPos = intInputAmtLen + intCommaPos;
                intInputAmtLen = 0;
            } 
            strTemp = "," + strAmt.substr(intInputAmtLen, intCommaPos) + strTemp 
        } 
            
        inAmtTag.value =  "" + strTemp.substr(1);
    }
    //3자리 이하의 수는 그냥 처리
    else {
        inputAmt.length > 0 ? inputAmt =  "" + intAmt : inputAmt =  "";
    }
    

}



/*
' ------------------------------------------------------------------
' Function    : inputNumOnly(inputTag, inputValue)
' Description : 숫자만 입력해야 되는경우, 숫자 이외의 문자를 넣지못하게한다.
' Argument    : inputTag   - 입력되는 input Tag
				inputValue - 입력되는 input Tag의 값(inputTag.value)
' Return      : 
' ------------------------------------------------------------------
*/
/*
function inputNumOnly(inputTag, inputValue) {     
   var tempStr = toInt(inputValue);  
   inputTag.value.length > 0 ? inputTag.value =  "" + tempStr : inputTag.value =  "";
}
*/
function inputNumOnly(input){
    
	
	var chars = "0123456789";
    if(!hasCharsOnly(input,chars))
    {
    	alert("숫자만 입력 가능합니다!");
    	input.select();
//    	input.value = '';
//    	input.focus();
    	return false;
    }
    else
    	return true;
}



/**
 * 	2002.05.30. 
 *	string, null -->  integer
 */
function toInt(str)
{
	var num = parseInt(str, 10);	
	if(isNaN(num))
		return 0;
	else
		return num;
}



/**
 *  문자열에 있는 특정문자패턴을 다른 문자패턴으로 바꾸는 함수.
 */
function replace(targetStr, searchStr, replaceStr)
{
	var len, i, tmpstr;

	len = targetStr.length;
	tmpstr = "";

	for ( i = 0 ; i < len ; i++ ) {
		if ( targetStr.charAt(i) != searchStr ) {
			tmpstr = tmpstr + targetStr.charAt(i);
		}
		else {
			tmpstr = tmpstr + replaceStr;
		}
	}
	return tmpstr;
}





/**
 *	입력값에 숫자만 있는지 체크
 *	(번호 입력란 체크.
 *	 금액입력란은 isNumComma를 사용해야 합니다.)
 */
function isNumber(input) {
    var chars = "0123456789";
    return hasCharsOnly(input,chars);
}

/**
 *	입력값에 숫또는 . 자만 있는지 체크
 *	(번호 입력란 체크.
 *	 금액입력란은 isNumComma를 사용해야 합니다.)
 */
function isNumberDu(input) {
    var chars = "0123456789.";
    return hasCharsOnly(input,chars);
}



/**
 * 입력값이 특정 문자(chars)만으로 되어있는지 체크
 * 특정 문자만 허용하려 할 때 사용
 * ex) if (!hasCharsOnly(form.blood,"ABO")) {
 *         alert("혈액형 필드에는 A,B,O 문자만 사용할 수 있습니다.");
 *     }
 */
function hasCharsOnly(input,chars) {
    for (var inx = 0; inx < input.value.length; inx++) {
       if (chars.indexOf(input.value.charAt(inx)) == -1)
           return false;
    }
    return true;
}




/**
 *	orgChar 문자열에서 rmChar문자열을 없애고 리턴한다
 *	계좌번호나 금액에서 '-'나 ','를 제거할때 사용한다
 *	(2002.06.07)
 */
function removeChar(orgChar, rmChar){
    return replace(orgChar,rmChar,"");
}

//로딩바 보이기

function loadingMaskFixPos(){
 //var childWidth = 350;
 //var childHeight = 228;
// var childTop = (document.body.clientHeight - childHeight) / 2;
// var childLeft = (document.body.clientWidth - childWidth) / 2;
 
 var childTop = "200";
 var childLeft = "300";
	 
 var loadingDiv = $('<span id="loadingLayer" style="position:absolute; left:'+childLeft+'px; top:'+childTop+'px; width:100%; height:100%; z-index:10001"><img id="ajax_load_type_img" src="/js/jquery/res/loading.gif"></span>').appendTo($("body"));
 loadingDiv.show();
 var loadingDivBg = $('<iframe id="loadingLayerBg" frameborder="0" style="filter:alpha(opacity=0);position:absolute; left:0px; top:0px; width:100%; height:100%; z-index:10000"><img id="ajax_load_type_img" src="/js/jquery/res/loading.gif"></iframe>').appendTo($("body"));
 loadingDivBg.show();
}

function cal_byte(Obj, VMax, ObjName)
{ 
 var tmpStr;
 var temp=0;
 var onechar;
 var tcount;
 tcount = 0;
 var aquery = Obj.value
 
 tmpStr = new String(aquery);
 temp = tmpStr.length;

 for (k=0;k<temp;k++)
 {
  onechar = tmpStr.charAt(k);

  if (escape(onechar).length > 4)
  {
   tcount += 2;
  }
  else tcount ++;
  
  /*
  else if (onechar!='\r')
  {
   tcount++;
  }onkeyup
  */  
 }
 
 if(tcount>VMax)
 {
  
  reserve = tcount-VMax;
  alert(ObjName+"은(는) "+VMax+"바이트 이상은 전송하실수 없습니다.\r\n 쓰신 메세지는 "+reserve+"바이트가 초과되었습니다.\r\n 초과된 부분은 자동으로 삭제됩니다."); 
  nets_check(Obj, VMax);
  return false;
 }
 return true;
}

function nets_check(Obj,lsMax)
{
 var tmpStr;
 var temp=0;
 var onechar;
 var tcount;
 tcount = 0;

 var aquery = Obj.value;

 tmpStr = new String(aquery);
 temp = tmpStr.length;

 for(k=0;k<temp;k++)
 {
  onechar = tmpStr.charAt(k);
  
  if(escape(onechar).length > 4)
  {
   tcount += 2;
  }
  else tcount++;
  /*
  else if(onechar!='\r')
  {
   tcount++;
  }
  */
  if(tcount>lsMax)
  {
   tmpStr = tmpStr.substring(0,k-1); 
   break;
  }
 }
 Obj.focus();
 Obj.value = tmpStr;
}
 
/******************** SRM ********************/

/**
 * 사업자등록번호 체크
 * @param irsNo
 * @returns {Boolean}
 */
function gfnCheckBizNum(irsNo) {
	var chkValArr = new Array(10);
	chkValArr[0] = ( parseFloat(irsNo.substring(0 ,1))  * 1 ) % 10;
	chkValArr[1] = ( parseFloat(irsNo.substring(1 ,2))  * 3 ) % 10;          
	chkValArr[2] = ( parseFloat(irsNo.substring(2 ,3))  * 7 ) % 10;          
	chkValArr[3] = ( parseFloat(irsNo.substring(3 ,4))  * 1 ) % 10;          
	chkValArr[4] = ( parseFloat(irsNo.substring(4 ,5))  * 3 ) % 10;          
	chkValArr[5] = ( parseFloat(irsNo.substring(5 ,6))  * 7 ) % 10;          
	chkValArr[6] = ( parseFloat(irsNo.substring(6 ,7))  * 1 ) % 10;          
	chkValArr[7] = ( parseFloat(irsNo.substring(7 ,8))  * 3 ) % 10;          
	
	chkValTemp = parseFloat(irsNo.substring(8,9))  * 5 + "0";          
	
	chkValArr[8] = parseFloat(chkValTemp.substring(0,1)) + parseFloat(chkValTemp.substring(1,2));          
	chkValArr[9] =  parseFloat(irsNo.substring(9,10));          
	chkValLastid = (10 - ( ( chkValArr[0] + chkValArr[1] + chkValArr[2] + chkValArr[3] + chkValArr[4] + chkValArr[5] + chkValArr[6] + chkValArr[7] + chkValArr[8] ) % 10 ) ) % 10;          
	
	if (chkValArr[9] != chkValLastid) {
		//alert("유효한 사업자 번호가 아닙니다.");
		return false;
	} else {
		return true;
	}
}

/**
 * byte 체크 (한글 3byte)
 * @param 입력값
 * @param 최대byte
 * @returns {Boolean}
 */
function cal_3byte(input, VMax, permitMsg, byteMsg) {
	var tmpStr = new String(input);
	var onechar;
	var tcount = 0;
	var permitChar = /^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣\s\r\n`~!@#$%^&*()\-_=+\\|\]\["',./:;<>?}{]$/;
	var check5Byte = /^[@&$"%'()+/;<=>?|\-\\]$/;
	var check6Byte = /^[}{]$/;
	var check = /^[\n]$/;
	
	for (k = 0; k < tmpStr.length; k++) {
		onechar = tmpStr.charAt(k);
		
		// 허용되지 않은 문자가 포함될 경우
		if (RegExp(permitChar).test(onechar) == false) {
			alert(permitMsg);
			return false;
		}
		
		if (RegExp(check).test(onechar) == true) {
			tcount += 10;
			
		} else if (RegExp(check6Byte).test(onechar) == true) {
			tcount += 6;
			
		} else if (RegExp(check5Byte).test(onechar) == true) {
			tcount += 5;
			
		} else if (escape(onechar).length > 4) {
			tcount += 3;
			
		} else {
			tcount++;
		}
	}
	
	if (tcount > VMax) {
		alert(byteMsg)
		return false;
	}
	
	return true;
}

/**
 * number 타입 체크
 * @param 입력값
 * @param 최대자리수
 * @param 소수점자리수
 * @returns {Boolean}
 */
function cal_numberCheck(input, VMax, fixed) {
	var tmpStr = new String(input);
	
	var temp;
	if (tmpStr.indexOf(".") > -1) {
		temp = tmpStr.split(".");
		
		if (temp[0].length > (parseInt(VMax)-parseInt(fixed))) {
			return false;
		} else if (temp[1].length > fixed) {
			return false;
		}
		
	} else {
		if (tmpStr.length > parseInt(VMax)-parseInt(fixed)) {
			return false;
		}
	}
	return true;
}

/**
 * 파일 확장자 체크
 * @param gbn : all(전체), word(문서), image(이미지)
 * @param fileExt : 확장자명
 * @returns {Boolean} : false(업로드불가확장자), true(업로드가능확장자)
 */
function fileExtCheck(checkFileExt, fileExt){
    fileExt = fileExt.toUpperCase();
    var checkFileExtArray = checkFileExt.split("|");
    var fileCheck = false;
    for(var i=0; i<checkFileExtArray.length; i++){
        if(fileExt == checkFileExtArray[i]) {
            fileCheck = true;
            break;
        }
    }
    return fileCheck;
}
/***************************************************/