/***************************************************

         c_Softphone function
         
         
   - util.js
     : 소프트폰에서 사용하는 Utilities 정의

   * init : 2014-02-25 hawon
      update : 2014-09-14 hawon
   
***************************************************/


  function nowDateTime() {
  
    return new Date().getTime();
    
  }


  function userSleep(msec) {
  
    var start = new Date().getTime();
    
    var now = start;
    
    while(now - start < msec) {
    
      now = new Date().getTime();
      
    }
    
  }


  function popUpWin(url, name, width, height, x, y, onfocus) {
	  //test
//	  alert("popUpWin called");
    var pop_win;
    
    x = window.top.screenLeft + x;
    y = window.top.screenTop + y;
    pop_win = window.open(url, name, "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width="+width+", height="+height +", left="+x+", top="+y);
    
    
//    parent.test(); //softphone_CTI.jsp
//    parent.parent.test(); //gnb.jsp
//    parent.parent.parent.frames["contentFram"].clickTabMenu("url","menuNm");
//    window.parent.frames["contentFram"].clickTabMenu("/lottemart-cc/PCCMAGT0009/inboundMain.do?sysDivnCd=02","통합화면");
  //  pop_win.moveTo(x, y);
    //#mart
    if(onfocus == "no"){
    	
    }
    else{
    	pop_win.focus();
    }
    
    //pop_win.resizeTo(width+15, height+30);
    
    return pop_win;
	  
  }


/*
window.showModalDialog(url[,argument[,features]])
  
  features='DialogWidth=폭;DialogHeight=높이';등

예)

 dialogObj = showModalDialog('Ex14Modal.htm', '', 'dialogTop=300px;dialogLeft=100px;dialogHeight=10em;dialogWidth=30em'

*/
  
  function popUpWinModal(url, name, width, height, x, y) {
  
    var modal_win;
    
    x = window.top.screenLeft + x;
    y = window.top.screenTop + y;
    
    modal_win = showModalDialog(url, name, "dialogTop="+x+";dialogLeft="+y+";dialogHeight="+height+";dialogWidth="+width);
  
    return modal_win;
  
  }


  // 구분번호 추가 후 전화번호 반환 
  // 외부발신인 경우, 확장내선인경우 
  
  function getDialNo(str) {
  
    var tel = str;
    
    //alert("TEL : " + str + "(" + telNoType(str) + ")");
    
    switch (telNoType(str)) {
    
      case 1 :
      
          tel = OBDivNo + str;
          
        break;
        
      case 2 :
      
          tel = EXDivNo + str;
          
        break;
      
    }
    
    return tel;
    
  }    
  
  // 외부 발신 전화인지 여부 
  // 외부발신인 경우 1, 내선인경우 0, 확장내선인경우 2, 전화번호가 아닌 경우 -1
  
  function telNoType(str) {
  
    if (!isNumeric(str)) return false;
    
    var tel1;
    
    if (str.length == InnerTelLength) {
    
      return 0;
      
    } else if (str.length == ExInnerTelLength) {
      
      if (EXInnerTelUse) return 2;
    
    } else if (str.length == 7) {
    
      return 1;
    
    } else if (str.length == 8) {
    
      return 1;
    
    } else if (str.length == 9) {     
    
      tel1 = str.substring(0, 2);
    
    } else if (str.length == 10) {
      
      tel1 = str.substring(0, 2);
      
      if (tel1 != "02") tel1 = str.substring(0, 3);
      
    } else if (str.length == 11) {
      
      tel1 = str.substring(0, 3);
  
    } else {
      
      return -1;
      
    }
    
    if(chkTelHeader(tel1)) return 1;
    
    return -1;
    
  }
  
  function replaceAll(str,from,to){
		return str.split(from).join(to);
	}
  
  // 전화번호 유효성 검사
  function isTelNo(str) {
	  
	 if(str != null){
		 str = replaceAll(str,"-","");
	 }
    
    if (!isNumeric(str)) return false;
    
    var tel1;
    
    if (str.length == InnerTelLength) {
    
      return true;
      
    } else if (str.length == ExInnerTelLength) {
    
      if (EXInnerTelUse) return true;
    
    } else if (str.length == 7) {
    
      return true;
    
    } else if (str.length == 8) {
    
      return true;
    
    }else if (str.length == 9) {     
    
      tel1 = str.substring(0, 2);
    
    } else if (str.length == 10) {
      
      tel1 = str.substring(0, 2);
      
      if (tel1 != "02") tel1 = str.substring(0, 3);
      
    } else if (str.length == 11) {
      
      tel1 = str.substring(0, 3);
  
    } 
    else if (str.length == 12) {
        
        tel1 = str.substring(0, 3);
    
      }
    else if (str.length == 13) {
        
        tel1 = str.substring(0, 3);
    
      }
    else {
      
      return false;
      
    }
    
    return chkTelHeader(tel1);
    
  }
  
  function chkTelHeader(str) {
  
    var list = Array("02"    //서울
                          , "051" //부산
                          , "053" //대구
                          , "032" //인천
                          , "062" //광주
                          , "042" //대전
                          , "052" //울산
                          , "044" //세종
                          , "031" //경기
                          , "033" //강원
                          , "043" //충북
                          , "041" //충남
                          , "063" //전북
                          , "061" //전남
                          , "054" //경북
                          , "055" //경남
                          , "064" //제주
                          , "010", "011", "013", "016", "017", "018", "019" //이동통신
                          , "050" //평생번호
                          , "070" //070 번호
                          , "080" //080 번호
                          );
  
                     
    for (var i=0; i<list.length; i++) {
    
      if (str == list[i]) return true;
      
    }
    
    return false;
    
  }
  
  
  function coverageTelNo(str) {
  
    var len = str.length;
    //var str2 = str;
    
    if (len < 7 ) return str;
    
    if (str.substring(0,1) == "9") str = str.substring(1,len);
    
    if (str.substring(0,1) == "1") str = "0" + str;
    
    switch (len) {
    
      case 7 :
      
        break;      
        
      case 8 :
      
        break;
        
      case 9 :
        
        if (str.substring(0,1) == "2" || str.substring(0,1) == "3" || str.substring(0,1) == "4" || str.substring(0,1) == "5" || str.substring(0,1) == "6") str = "0" + str;
        
        break;
                
      case 10 :
      
        if (str.substring(0,1) == "3" || str.substring(0,1) == "4" || str.substring(0,1) == "5" || str.substring(0,1) == "6") str = "0" + str;
        
        break;
        
      case 11 :
      
        break;
        
      case 12 :
      
        break;
        
      default :
      
        break;
    
    }
    
    return str;
    
  }
  
  // 스트링에 입력된 내용이 숫자인지 여부를 판단 
  function isNumeric(str) {
    
    str = str+"";
    
    if(str.length == 0) return false;
      
    for(var i=0; i < str.length; i++)  
      if(!('0' <= str.charAt(i) && str.charAt(i) <= '9')) return false;
        
    return true;
    
  }
  
  // 좌우측 및 중간 공백문자를 제거한다.
  function trim(str) { 
  
    str = str+"";
    
    return delChar(str.replace(/(^\s*)|(\s*$)/gi, ""), ' ');
    
  }
  
  // 좌측 공백문자를 제거한다.
  function LTrim(str) { 
  
    str = str+"";
    
  	var i = 0;
  	
  	while (str.substring(i,i+1) == ' ' || str.substring(i,i+1) == '　')  i = i + 1;
  	
  	return str.substring(i);
  	
  }
  
  // 우측 공백문자를 제거한다.
  function RTrim(str) { 
    
    str = str+"";
    
  	var i = str.length - 1;
  	
  	while (i >= 0 && (str.substring(i,i+1) == ' ' || str.substring(i,i+1) == '　'))  i = i - 1;
  		
  	return str.substring(0,i+1);
  }
  
 
  // 지정 문자를 제거한다
  function delChar(str, ch) { 
  
    str = str+"";
    
  	var len = str.length;
  	var ret = "";
  	
  	for (i=0; i<len; ++i) 
  		if (str.substring(i,i+1) != ch) ret = ret + str.substring(i,i+1);
  		
  	return ret;
  	
  }

  function setSelectBox(obj, arr) {
  
//    alert(arr.length);
    
    for (var i=0; i< arr.length; i++) addSelectBox(obj, arr[i][0], arr[i][1]);
  
  }
  
  function addSelectBox(obj, txt, val) {
  
    option = new Option();
    
    option.text = txt;
    option.value= val;
    
    obj.add(option);
  
  }
  
  
  function usrAlert(str) {
    
    // 사용자 Alert
    
    //if(PopupObject != null) PopupObject.close();

    alert(str);
  
  }
  
  function fillRepetedChar(str, chr, num) {
  
    var chrStr = "";
    
    for (var i = 0; i < num; i++) {
      chrStr = chr + chrStr;
    }
  
    return str + chrStr;
    
  }
  
  
  