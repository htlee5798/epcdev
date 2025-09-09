
	//설정값
	var dq_searchForm = document.getElementById("mainSearchForm");
	var dq_searchFormTextbox = dq_searchForm.searchTerm;
	var dq_searchTextbox = document.getElementById("searchTerm");
	var dq_searchTextField = document.getElementById("searchField");

	var dq_resultDivID = "dqAuto";               //자동완성레이어 ID
	var dq_hStartTag = "<em>";                    //하이라이팅 시작 테그
	var dq_hEndTag = "</em>";                     //하이라이팅 끝 테그
	var dq_bgColor = "#f3f3f3";                  //선택빽그라운드색
	var dq_intervalTime = 500;                   //자동완성 입력대기 시간
	
	//고정값
	var dq_acResult = new Object();              //결과값
	var dq_acLine = 0;                           //자동완성 키워드 선택  위치(순번)	
	var dq_searchResultList = "";                //자동완성결과리스트	
	var dq_searchKeyword = "";	                 //검색어(한영변환안된)
	var dq_ajaxReqObj = "";                      //ajax request object

	var dq_keyStatus = 1;                        //키상태구분값
	var dq_acuse = 1;                            //자동완성사용여부
	var dq_engFlag = 0;                          //자동완성한영변환체크
	var dq_acDisplayFlag = 0;                    //자동완성 display 여부
	var dq_acArrowFlag = 0;                      //마우스이벤트용 flag	
	var dq_acArrowOpenFlag = 0;                  //마우스이벤트용 flag
	var dq_acFormFlag = 0;                       //마우스이벤트용 flag
	var dq_acListFlag = 0;                       //자동완성 레이어 펼쳐진 상태 여부
	var dq_browserType = dqc_getBrowserType();	 //브라우져타입
	var dq_keywordBak = "";                      //키워드빽업
	var dq_keywordOld = "";                      //키워드빽업
	
	var dq_autoPos = "F";						//전방,후방 위치 체크
	
	dq_keywordBak = dq_keywordOld = dq_searchTextbox.value;
	
	//엔터체크
	function dq_handleEnter(event)
	{		
		var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode; 

		if (keyCode == 13)
		{
			//검색스크립트
			DQmainSearch_submit();
			return false;
		}	
		else
		{
			return true;
		}	
	}
	
	//마우스클릭시검색
	function dq_keywordSearch(keyword)
	{
		dq_searchFormTextbox.value = keyword;
		dq_searchForm.submit();
	}
	
	//입력값 체크 - setTextbox
	function dq_setTextbox(flag, ev) 
	{		
		var _event; 
		var key;
				
		//dq_stateChange();
		
		switch(dq_browserType)
		{
			case 1 : // IE
				_event = window.event;				
				key = _event.keyCode;
				break;
			case 2 : // Netscape
				key = ev.which;
				break;
			default :				
				key = _event.keyCode;
				break;
		}
							
		if(dq_keyStatus == 1 && flag && key != 13)
			dq_keyStatus = 2;	
	}
		
	//자동완성레이어 상태변경 - wd
	function dq_stateChange() 
	{			
		dq_searchTextbox.onclick = dq_acDisplayView;
		dq_searchTextbox.onblur = dq_acDisplayCheck;
		document.body.onclick = dq_acDisplayCheck;				
	}
	
	//자동완성레이어 보여 주기 - req_ipc
	function dq_acDisplayView() 
 	{ 
		dq_acDisplayFlag = 1;
		dq_acFormFlag = 0;
		dq_reqAcResultShow();
 	}

	//자동완성레이어 감추기전  체크 - dis_p
 	function dq_acDisplayCheck() 
 	{
		if(dq_acDisplayFlag) 
		{ 
			dq_acDisplayFlag=0;			
			return ;
		} 
			
		if(dq_acArrowFlag)		
			return;
				
	
		if(dq_acFormFlag)
			return;
		
		dq_acDisplayHide();
	}
 	
 	//자동완성레이어 감추기 - ac_hide
 	function dq_acDisplayHide()
 	{  		
 		var resultDiv = document.getElementById(dq_resultDivID);
 		
		if(resultDiv.style.display == "none") 
			return ;
		
		dq_setDisplayStyle(0);
		dq_acListFlag = 0;
		dq_acLine = 0;
	}
 	
 	//자동완성레이어 display style 설정 - popup_ac
 	function dq_setDisplayStyle(type)
 	{	 		
 		var resultDiv = document.getElementById(dq_resultDivID);

 		if(type==0)
		{
			resultDiv.style.display = "none";
			//dq_switchImage(0);
		}
		else if(type==1)
		{
			resultDiv.style.display = "block";
			//dq_switchImage(1);
		}
		else if(type==2)
		{
			resultDiv.style.display = "none";
			//dq_switchImage(1);
		}		
 	}
 	
 	//자동완성 결과 보기 요청 - req_ac2
 	function dq_reqAcResultShow() 
	{		
 		var resultDiv = document.getElementById(dq_resultDivID);
 		
		if(dq_searchTextbox.value == "" || dq_acuse == 0)
			return ;
			
	 	if(dq_acListFlag && dq_acDisplayFlag)
	 	{ 
	 		dq_acDisplayHide();
			return;
		} 
	
		var o = dq_getAcResult();
	 
	 	if(o && o[1][0] != "")
	 		dq_acResultShow(o[0], o[1]);
	 	else
	 		dq_reqSearch();
 	}
 	
 	//자동완성 결과 object 리턴 - get_cc
 	function dq_getAcResult()
 	{ 
		var ke = dqc_trimSpace(dq_searchTextbox.value);
		
	 	return typeof(dq_acResult[ke])=="undefined" ? null : dq_acResult[ke];
 	} 
 	
 	//자동완성 결과 object 생성 - set_cc
 	function dq_setAcResult(aq, al)
 	{
 		dq_acResult[aq] = new Array(aq, al);
 	}
 	
 	//자동완성 결과 보기 - ac_show
 	function dq_acResultShow(aq, al)
 	{
		if(aq != dqc_trimSpace(dq_searchTextbox.value))
			dq_engFlag = 1;
 		else
			if(aq && aq != "" && aq != dqc_trimSpace(dq_searchTextbox.value)) 			
				return ;

	 	dq_searchKeyword = aq;
	 	dq_searchResultList = al;
	 			
	 	dq_printAcResult();
					
	 	if((dq_searchResultList != "" && dq_searchResultList.length))
		 	dq_acListFlag = 1;
	 	else
			dq_acListFlag = 0;
		
	 	if(dq_acListFlag)
	 	{ 
	 		dq_setAcPos(0);
			
			if(dq_browserType == 1)
				dq_searchTextbox.onkeydown = dq_acKeywordTextViewIE;
			else if(dq_browserType == 2)
				dq_searchTextbox.onkeydown = dq_acKeywordTextViewFF;
		} 
	} 
 	
 	//자동완성결과 라인 위치 지정 - set_acpos
 	function dq_setAcPos(v)
 	{
 		dq_acLine = v;
		setTimeout('dq_setAcLineBgColor();', 10);
 	}
 	
 	//자동완성레이어에 결과 출력 - print_ac
 	function dq_printAcResult(pos) 
 	{ 
 		var resultDiv = document.getElementById(dq_resultDivID);
 			 				 		 		
		if(dq_searchResultList[0] == "")
			resultDiv.innerHTML = dq_getAcNoResultList();
	 	else
	 		resultDiv.innerHTML = dq_getAcResultList(pos);
	 				
		dq_setDisplayStyle(1); //자동완성창 보여줌.
	 	
	 	setTimeout('dq_setAcLineBgColor();', 10); 	
	 	
	 
 	}
 	
 	//자동완성 키워드 라인의 백그라운드색 설정 - set_ahl
 	function dq_setAcLineBgColor()
 	{
 		var o1, qs_ac_len;
 		
		if(!dq_acListFlag)
			return;
		
		if(dq_searchResultList != ""){
			qs_ac_len = dq_searchResultList.length;
		}
		
	 	for(i=0;i<qs_ac_len;i++)
	 	{
			o1 = document.getElementById('dq_alink' + (i+1));
			
			if(o1 != null)
			{			
				if((i+1) == dq_acLine) {
					o1.style.backgroundColor = dq_bgColor;
				} else {
					o1.style.backgroundColor = '';
				}
			}
		}
 	}
 	
 	//자동완성레이어의 선택된 키워드를 textbox에 넣어줌(IE) - ackhl
 	function dq_acKeywordTextViewIE()
	{
		var e = window.event;
		var ac, acq;
		var resultDiv = document.getElementById(dq_resultDivID);
		var resultListDiv = document.getElementById("list_pr");
		var qs_ac_len = 0;
		
		if(dq_searchResultList != ""){
			qs_ac_len = dq_searchResultList.length;
		}

		if(e.keyCode==39)
	 		dq_reqAcResultShow();	 	 
	 	
	 	if(e.keyCode==40 || (e.keyCode==9 && !e.shiftKey))
	 	{
		 	if(!dq_acListFlag)
		 	{
				dq_reqAcResultShow();
			 	return;
			}
			
			if(dq_acLine < qs_ac_len)
			{
				if(dq_acLine == 0)
					dq_keywordBak = dq_searchTextbox.value;

				dq_acLine++;

				if(dq_acLine>20) dq_acLine=1;
				
			 	ac = eval('dq_ac' + dq_acLine);
			 	acq = eval('dq_acqHidden' + dq_acLine);
			 	dq_keywordOld = dq_searchTextbox.value = acq.value;
			 	dq_searchTextbox.focus();
			 	dq_setAcLineBgColor();
			 	resultListDiv.scrollTop = 70 *(dq_acLine-1); 
			 	e.returnValue = false;
		 	}
	 	}
	 	
	 	if(dq_acListFlag && (e.keyCode==38 || (e.keyCode==9 && e.shiftKey)))
	 	{		 			 	
			if(!dq_acListFlag) 
				return;
		 
		 	if(dq_acLine <= 1)
		 	{ 
		 		dq_acDisplayHide();
			 	dq_keywordOld = dq_searchTextbox.value = dq_keywordBak;
		 	} 
		 	else
		 	{
				dq_acLine--;
							
			 	ac = eval('dq_ac'+ dq_acLine);
			 	acq = eval('dq_acqHidden' + dq_acLine);
			 	dq_keywordOld = dq_searchTextbox.value = acq.value;
			 	dq_searchTextbox.focus();
			 	dq_setAcLineBgColor();
			 	resultListDiv.scrollTop = resultListDiv.scrollTop - 70;
			 	e.returnValue = false;
			}
		}
	}
 	
 	//자동완성레이어의 선택된 키워드를 textbox에 넣어줌(IE외 브라우져) - ackhl_ff
 	function dq_acKeywordTextViewFF(fireFoxEvent)
	{		
		var ac, acq;
		var resultDiv = document.getElementById(resultDiv);
		var resultListDiv = document.getElementById("list_product");
		var resultListDiv = document.getElementById("list_pr");
		var qs_ac_len = 0;
		
		if(dq_searchResultList != ""){
			qs_ac_len = dq_searchResultList.length;
		}
		
	 	if(fireFoxEvent.keyCode==39)
	 		dq_reqAcResultShow();
	 		 
	 	if(fireFoxEvent.keyCode==40 || fireFoxEvent.keyCode==9)
	 	{			
		 	if(!dq_acListFlag)
		 	{
		 		dq_reqAcResultShow();
			 	return;
			}
			
			if(dq_acLine < qs_ac_len)
			{ 
				if(dq_acLine == 0) 
					dq_keywordBak = dq_searchTextbox.value;
					
				dq_acLine++;
						 
			 	ac = document.getElementById('dq_ac' + dq_acLine);
			 	acq = document.getElementById('dq_acqHidden' + dq_acLine);
			 	
			 	dq_keywordOld = dq_searchTextbox.value = acq.value;
			 	
			 	dq_searchTextbox.focus();
			 	dq_setAcLineBgColor();
			 	resultListDiv.scrollTop = 70 *(dq_acLine-1); 
			 	fireFoxEvent.preventDefault();
		 	}
	 	}
	 	
	 	if(dq_acListFlag && (fireFoxEvent.keyCode==38 || fireFoxEvent.keyCode==9))
	 	{
			if(!dq_acListFlag) 
				return;
		 
		 	if(dq_acLine <= 1)
		 	{ 
		 		dq_acDisplayHide();
			 	dq_keywordOld = dq_searchTextbox.value = dq_keywordBak;
		 	} 
		 	else
		 	{
		 		dq_acLine-- ;
			 
			 	ac = document.getElementById('dq_ac' + dq_acLine);
			 	acq = document.getElementById('dq_acqHidden' + dq_acLine);
			 	
			 	dq_keywordOld = dq_searchTextbox.value = acq.value;
			 	dq_searchTextbox.focus() ;
			 	dq_setAcLineBgColor() ;
			 	resultListDiv.scrollTop = resultListDiv.scrollTop - 70;
			 	fireFoxEvent.preventDefault();
			}
		}						
	}
 	
 	//검색요청 - reqAc
 	function dq_reqSearch() 
 	{	 				
		var sv;
		var ke = dqc_trimSpace(dq_searchTextbox.value);
		var fi = dqc_trimSpace(dq_searchTextField.value);
		
		ke = ke.replace(/ /g, "%20");
		
		while(ke.indexOf("\\") != -1)
			ke = ke.replace(/ /g, "%20").replace("\\", "");
		
		while(ke.indexOf("\'") != -1)
			ke = ke.replace(/ /g, "%20").replace("\'", "");
	 
	 	if(ke == "")
	 	{ 
	 		dq_acDisplayHide();
			return;
		} 
					
	 	//sv = "/search/ajax/headGetAutoProduct.do?p=1&f="+fi+"&q=" + escape(encodeURIComponent(ke));
	 	sv = "/search/ajax/headGetAutoProduct.do?p=1&q=" + escape(encodeURIComponent(ke));

	 	dq_ajaxReqObj = dqc_getXMLHTTP();
	 	
	 	if(dq_ajaxReqObj)
	 	{ 		 		
	 		dq_ajaxReqObj.open("GET", sv, true);
	 		dq_ajaxReqObj.onreadystatechange = dq_acShow;
	 	} 
	 
	 	try
	 	{		 	
	 		dq_ajaxReqObj.send(null);
	 	}
	 	catch(e)
	 	{		
			return 0;
		} 
	}
 
 	//자동완성 결과 보기 - showAC
 	function dq_acShow() 
 	{	 		
		if(dq_acuse == 1)
	 	{		 		
			if(dq_ajaxReqObj.readyState==4 && dq_ajaxReqObj.responseText && dq_ajaxReqObj.status==200)
			{					
				//alert(dq_ajaxReqObj.responseText);
				eval(dq_ajaxReqObj.responseText);
				dq_setAcResult(dq_searchKeyword, dq_searchResultList);
				dq_acResultShow(dq_searchKeyword, dq_searchResultList);
			}
	 	}
	 	else
	 	{
	 		dq_setDisplayStyle(2);
	 	}
 	}
 	
 	//선택키워드저장 - set_acinput
 	function dq_setAcInput(keyword) 
 	{	 		
		if(!dq_acListFlag) 
			return;				
		
	 	dq_keywordOld = dq_searchTextbox.value = keyword;		 			 	
	 	dq_searchTextbox.focus();
	 	dq_acDisplayHide();		
 	}
 	
 	//기능끄기 버튼을 눌렀을때 - ac_off
	function dq_acOff() 
	{		
		if(dq_searchTextbox.value == "") {
			dq_setDisplayStyle(0);
		} else {
			dq_acDisplayHide();
		}
	
		dq_acuse = 0;
 	}

	//화살표클릭 - show_ac
	function dq_acArrow()
	{			
		var resultDiv = document.getElementById(dq_resultDivID);
		
		if(dq_acuse == 0)
		{
			dq_keywordOld = "";
			dq_acuse = 1;		
			
			if(dq_searchTextbox.value == "")			
				resultDiv.innerHTML = dq_getAcOnNoKeyword();			
		}
		else
		{
			if(dq_searchTextbox.value == "")
				resultDiv.innerHTML = dq_getAcNoKeyword();						
		}
		
		if(dq_searchTextbox.value == "" && (resultDiv.style.display == "block")) {
			dq_setDisplayStyle(0);
			dq_setDisplayStyle(1);
		}
		
		dq_acDisplayView();
		dq_searchTextbox.focus();
		dq_wi();
		
		document.body.onclick=null;
	}
	
	//검색어입력창의 자동완성 화살표를 위, 아래로 변경한다. - switch_image	
	/*function dq_switchImage(type)
	{			
		var arrow_obj = document.getElementById("dq_autoImg").src;			
		var former_part = arrow_obj.substring(0,arrow_obj.length-6);
		
		if(type==0)
		{
			document.getElementById("dq_autoImg").src = former_part+"Dn.gif";
			document.getElementById("dq_autoImg").title = "자동완성 펼치기";
		}
		else if(type==1)
		{			
			document.getElementById("dq_autoImg").src = former_part+"Up.gif";
			document.getElementById("dq_autoImg").title = "자동완성 닫기";
		}
 	}*/
	
	//자동완성 레이어 mouse on
	function dq_setMouseon() 
 	{ 		
	 	dq_acFormFlag = 1;
 	}

	//자동완성 레이어 mouse out
 	function dq_setMouseoff()
 	{		
	 	dq_acFormFlag = 0;		
		dq_searchTextbox.focus();
 	}
 	
 	//자동완성 결과 코드 - get_aclist
 	function dq_getAcResultList(dp)
 	{ 	 	
 		var prod_cd = "";
 		var prod_nm = "";
 		var direct_cpn_nm = "";
 		var cms_coupon_yn = "";
 		var catalog_yn = "";
 		var curr_sell_prc = "";
 		var prod_standard_nm = "";
 		var deli_type = "";
 		var cat_cd = "";
 		var md_cd = "";
 		var prodImg = "";
 		var disp_prod_nm = "";

 		var text = "";
 		
 		var pos = 0;
 		var result = "";
 		
 		var dpList = dq_searchResultList;
 		var textCheck = 0;
 		
 		if(dpList != null && dpList != "" && dpList.length > 0)
		{
 			text += "<div class=\"in\" id=\"list_in\">\n";
			text += "<p class=\"txt\"><strong>'" + dq_searchTextbox.value + "'</strong> 검색결과 <span><strong>" + dpList.length + "</strong>개</span>의 추천상품</p>\n";
			text += "<div class=\"list_pr\" id=\"list_pr\">\n";
			text += "	<div class=\"list_product\" id=\"list_product\">\n";
 			
 			if(dpList != ""){
			 	for(i=0;i<dpList.length;i++)
			 	{
			 		result = dpList[i].split("|");
			 		prod_cd = result[0];
			 		prod_nm = result[1];
			 		direct_cpn_nm = result[2];
			 		cms_coupon_yn = result[3];
			 		catalog_yn = result[4];
			 		curr_sell_prc = result[5];
			 		prod_standard_nm = result[6];
			 		if(prod_standard_nm!="") prod_standard_nm = "/" + prod_standard_nm;
			 		deli_type = result[7];
			 		cat_cd = result[8];
			 		md_cd = result[9];
			 		prodImg = result[10];
			 		disp_prod_nm = result[11];
			 		if(deli_type=="01") {
			 			deli_type = "지점배송";
			 		} else if(deli_type=="02") {
			 			deli_type = "지점택배";
			 		} else if(deli_type=="03") {
			 			deli_type = "점포픽업";
			 		} else if(deli_type=="04") {
			 			deli_type = "업체배송";
			 		} else {
			 			deli_type = "기타배송";
			 		}
			 		
					if(i == 0){
						text += "<ul>";
					}
					text += "<li id='dq_ac" + (i+1) + "' onmouseover=\"dq_setAcPos('" + (i+1) + "')\" onFocus=\"dq_setAcPos('" + (i+1) + "');\" onmouseout=\"dq_setAcPos(0);\"  onBlur=\"dq_setAcPos(0);\">\n";
					text += "<div class=\"pr_wrap\" id=\"dq_alink" + (i+1) + "\">\n";
					text += "	<div class=\"photo_wrap2\">\n";
					text += "		<a href=\"javascript:goProductDetail('" + cat_cd + "', '" + prod_cd + "', 'N')\"><img src=\"" + prodImg + "\" width=\"75\" height=\"75\" class=\"photo\" alt=\"" + prod_nm + "\" onerror=\"javascript:showNoImage(this)\"></a>\n";
					text += "	</div>\n";
					text += "	<div class=\"pr_price\">\n";
					text += "		<p><em class=\"t_roman\">" + addComma(curr_sell_prc) + "</em> 원" + prod_standard_nm + "</p>";
					text += "	</div>\n";
					text += "	<p class=\"pr_name\"><a href=\"javascript:goProductDetail('" + cat_cd + "', '" + prod_cd + "', 'N')\">" + prod_nm + "</a></p>\n";
					text += "	<p class=\"pr_type\">" + deli_type + "</p>\n";
					text += "</div>\n";
					text +=  "<input type=\"hidden\" id=\"dq_acqHidden" + (i+1) + "\" value=\"" + disp_prod_nm + "\"/>\n";
					text += "<span id='dq_acq" + (i+1) + "' style='display:none'></span></li>\n";
					if(i == dpList.length-1){
						text += "</ul>\n";
					}
			 	}
			 	//textCheck = 1;
 			}
 			
	 		text += "	</div>\n";
		 	text += "</div>\n";
		 	text += "<div class=\"more\"><span class=\"t_right\"><a href=\"javascript:dq_acDisplayHide();\" >닫기</a></span></div>\n";
		 	text += "</div>\n";
		 	
	 	}
 		//document.write(text);
	 	return text;
	}
 	
 	//자동완성 결과 없는 경우 - get_ac0
 	function dq_getAcNoResultList() 
 	{ 	 		
 		var text = "";
 		var ment = "해당 단어로 시작하는 검색어가 없습니다.";
 		/*
		text += "<dl><dd>";
 		text += "<ul class=\"front\">";
 		text += "	<li></li>";
 		text += "	<li>";	
 		text += ment;
 		text += "	</li>";
 		text += "	<li></li>";
 		text += "</ul>";
 		text += "<ul class=\"foot\">";
 		text += "<li class=\"close_btn\"><img src=\"/images/item/search_close_window.gif\" onclick=\"dq_acOff()\" style=\"cursor: pointer\"></li>";
 		text += "</ul>";
 		text += "<span id=dq_acq1 style='display:none'></span>";
 		text += "</dd></dl>";
 		*/
	 	return text;
 	}
 	
 	//자동완성 키워드 없는 경우
 	function dq_getAcNoKeyword() 
 	{ 	 		
 		var text = "";
 		var ment = "현재 자동완성 기능을 사용하고 계십니다.";
 		/*
 		text += "<dl><dd>";
 		text += "<ul class=\"front\">";
 		text += "	<li></li>";
 		text += "	<li>";	
 		text += ment;
 		text += "	</li>";
 		text += "	<li></li>";
 		text += "</ul>";
 		text += "<ul class=\"foot\">";
 		text += "<li class=\"close_btn\"><img src=\"/images/item/search_close_window.gif\" onclick=\"dq_acOff()\" style=\"cursor: pointer\"></li>";
 		text += "</ul>";
 		text += "<span id=dq_acq1 style='display:none'></span>";
 		text += "</dd></dl>";
	 	*/
	 	return text;
 	}
 	
 	//자동완성 복구시 키워드 없는 경우
 	function dq_getAcOnNoKeyword() 
 	{ 	 		
 		var text = "";
 		var ment = "자동완성기능이 활성화 되었습니다.";
 		
 		text += "<dl><dd>";
 		text += "<ul class=\"front\">";
 		text += "	<li></li>";
 		text += "	<li>";	
 		text += ment;
 		text += "	</li>";
 		text += "	<li></li>";
 		text += "</ul>";
 		text += "<ul class=\"foot\">";
 		//text += "	<li class=\"header\">검색어자동완성 <span onclick=\"javascript:dq_acOff();\">기능끄기</span></li>";
 		//text += "<li class=\"more_btn\"><a href=\"#\"><img src=\"/images/item/search_firstword_more.gif\"></a></li>";
 		text += "<li class=\"close_btn\"><a href=\"#\"><img src=\"/images/item/search_close_window.gif\"></a></li>";
 		text += "</ul>";
 		text += "<span id=dq_acq1 style='display:none'></span>";
 		text += "</dd></dl>";
	 	
	 	return text;
 	}

 	//검색박스 키워드 처리 루프 - wi()
 	function dq_wi() 
 	{	 		
		if(dq_acuse==0)
			return;
		
		var keyword = dq_searchTextbox.value;
		var keywordSumm = "";

	 	if(keyword == "" && keyword != dq_keywordOld){
	 		dq_acDisplayHide();
	 	}
	 	
		if(keyword != "" && keyword != dq_keywordOld && dq_keyStatus != 1)
		{
			var o = null;
			
			o = dq_getAcResult();
			
			if(o && o[1][0] != "") 
				dq_acResultShow(o[0], o[1]);
			else
				dq_reqSearch();
		}
		
		dq_keywordOld = keyword;		
		setTimeout("dq_wi()", dq_intervalTime);
 	}
 	
	setTimeout("dq_wi()", dq_intervalTime);
