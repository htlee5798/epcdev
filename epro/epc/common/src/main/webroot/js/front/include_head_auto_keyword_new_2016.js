
	//설정값
	var dq_searchForm = document.getElementById("mainSearchForm");
	var dq_searchFormTextbox = dq_searchForm.searchTerm;
	var dq_searchTextbox = document.getElementById("searchTerm");
	var dq_dumy_searchTermTextbox = document.getElementById("dumy_searchTerm");

	var dq_resultDivID = "dqAuto";               //자동완성레이어 ID
	var dq_hStartTag = "<b>";                    //하이라이팅 시작 테그
	var dq_hEndTag = "</b>";                     //하이라이팅 끝 테그
	var dq_bgColor = "#EAEAEA";                  //선택빽그라운드색
	var dq_intervalTime = 500;                   //자동완성 입력대기 시간
	
	//고정값
	var dq_acResult = new Object();              //결과값
	var dq_acLine = 0;                           //자동완성 키워드 선택  위치(순번)	
	var dq_searchResultList_f = "";                //자동완성결과리스트	(FKEY)
	var dq_searchKeyword = "";	                 //검색어(한영변환안된)
	var dq_ajaxReqObj_f = "";                      //ajax request object

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
	
	var dq_cookieDel = "N";						//기록삭제여부
	var dq_iniChk = "N";
	var dq_chk = "Y";
	var del_keyword = "";
	var cookieCount = 0;
	var cnt = 0;
	var closeStatus = 0; //1닫기, 0열기
	var cookieStatus = 0;//1삭제
	//var headChangeKey = "C";
	
	dq_keywordBak = dq_keywordOld = $("#searchTerm").val();
	
	//엔터체크
	function dq_handleEnter(event)
	{		
		var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode; 

		if (keyCode == 13)
		{
			//검색스크립트
			dq_keywordSearch(keyword);
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
		location.href="/search/search.do?searchTerm="+encodeURIComponent(keyword);
		
		var saveStatus = getSearchCookie("dq_mykeyword_save");
 		if(saveStatus.split( '∃' )[0] != "off") {
 			var dt = new Date();
 		 	var month = dt.getMonth()+1;
 		 	var day = dt.getDate();
 		 	if(month < 10) month = "0"+month;
 		 	if(day < 10) day = "0"+day;
 		 	var toDate = month + "." + day;
 		 	
 			setSearchCookie( 'dq_cookie_search', keyword + "$|" + toDate);
 		}
	}
	
	//change (최근검색어/인기검색어)
	function changeKey(key)
	{
		if(key == 'C'){
			$("#headChangeKey").val("C");
		}else{
			$("#headChangeKey").val("T");
		}
		dq_printAcResult();
	
	}

	//입력값 체크 - setTextbox
	function dq_setTextbox(flag, ev) 
	{		
		closeStatus=0;
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
 		closeStatus=1;
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

		if($("#searchTerm").val() == "" || dq_acuse == 0)
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
		var ke = dqc_trimSpace($("#searchTerm").val());
		
	 	//return typeof(dq_acResult[ke])=="undefined" ? null : dq_acResult[ke];
	 	return dq_acResult[ke];

 	} 
 	
 	//자동완성 결과 object 생성 - set_cc
 	function dq_setAcResult(aq, al_f)
 	{
 		dq_acResult[aq] = new Array(aq, al_f);
 	}
 	
 	//자동완성 결과 보기 - ac_show
 	function dq_acResultShow(aq, al_f)
 	{
		if(aq != dqc_trimSpace($("#searchTerm").val()))
			dq_engFlag = 1;
 		else
			if(aq && aq != "" && aq != dqc_trimSpace($("#searchTerm").val())) 			
				return ;

	 	dq_searchKeyword = aq;
	 	dq_searchResultList_f = al_f;
	 			
	 	dq_printAcResult();
					
	 	if(dq_searchResultList_f != "" && dq_searchResultList_f.length)
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
 		
 		if(($("#searchTerm").val() != null && $("#searchTerm").val() != "") && dq_iniChk=="N" ) {
			if(dq_searchResultList_f[0] == "") {
				//resultDiv.innerHTML = dq_getAcNoResultList();
				dq_acDisplayHide();
				closeStatus=0;
			} else {
		 		//resultDiv.innerHTML = dq_getAcResultList(pos);
		 		$("#"+dq_resultDivID).html(dq_getAcResultList(pos));
		 		//alert(resultDiv.innerHTML);
		 		dq_setDisplayStyle(1); //자동완성창 보여줌.
	 		}
 		}else{
 			$("#"+dq_resultDivID).html(dq_getAcMySearchTermList());
 			//resultDiv.innerHTML = dq_getAcMySearchTermList();
 			dq_setDisplayStyle( 1 ); //자동완성창 보여줌.	
 		}
	 				
	 	setTimeout('dq_setAcLineBgColor();', 10); 	
 	}
 	
 	//자동완성 키워드 라인의 백그라운드색 설정 - set_ahl
 	function dq_setAcLineBgColor()
 	{
 		
 		var o1, o2;
 		var qs_ac_len = 0;
 		
		if(!dq_acListFlag)
			return;
		if(($("#searchTerm").val() != null && $("#searchTerm").val() != "") && dq_chk=="N"){
			if(dq_searchResultList_f != ""){
				qs_ac_len = dq_searchResultList_f.length;
			}
			
		}else{	
			if(cnt > 0 && $("#headChangeKey").val() == "C"){
				qs_ac_len = cnt;
			}else {
				if(dq_searchResultList_f != ""){
					qs_ac_len = dq_searchResultList_f.length;
				}
			}
		}
		
	 	for(i=0;i<qs_ac_len;i++)
	 	{
	
			//o1 = document.getElementById('dq_alink' + (i+1));
	 		o1 = document.getElementById('dq_ac' + (i+1));

			if(o1 != null)
			{		
				if((i+1) == dq_acLine){
					o1.style.backgroundColor = dq_bgColor;
				}
				else{
					o1.style.backgroundColor = '';
				}
			}
			
		}
			
 	}
 	
 	//자동완성레이어의 선택된 키워드를 textbox에 넣어줌(IE) - ackhl
 	function dq_acKeywordTextViewIE()
	{
		var e = window.event;
		var ac, acq, dumy_searchTerm;
		var resultDiv = document.getElementById(dq_resultDivID);
		var qs_ac_len = 0;
		
		if(($("#searchTerm").val() != null && $("#searchTerm").val() != "") && dq_chk=="N"){
			if(dq_searchResultList_f != ""){
				qs_ac_len = dq_searchResultList_f.length;
			}

		}else{	
			if(cookieCount > 0 && $("#headChangeKey").val() == "C"){
				qs_ac_len = cnt;
			}else {
				if(dq_searchResultList_f != ""){
					qs_ac_len = dq_searchResultList_f.length;
				}
			}
		}
		
	 	/*if(e.keyCode==39){
	 		//dq_reqAcResultShow();	
	 	}*/	 
	 	
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
					dq_keywordBak = $("#searchTerm").val();

				dq_acLine++;

			 	ac = eval('dq_ac' + dq_acLine);
			 	acq = eval('dq_acq' + dq_acLine);
			 	dumy_searchTerm = eval('dumy_searchTerm' + dq_acLine);

			 	dq_keywordOld = acq.outerText;
			 	$("#searchTerm").val(acq.outerText);

			 	dq_dumy_searchTermTextbox.value = dumy_searchTerm.outerText;
			 	dq_searchTextbox.focus();
			 	dq_setAcLineBgColor();
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
			 	dq_keywordOld = dq_keywordBak;
			 	$("#searchTerm").val(dq_keywordBak);
		 	} 
		 	else
		 	{
				dq_acLine--;
							
			 	ac = eval('dq_ac'+ dq_acLine);
			 	acq = eval('dq_acq' + dq_acLine);
			 	dumy_searchTerm = eval('dumy_searchTerm' + dq_acLine);

			 	dq_keywordOld = acq.outerText;
			 	$("#searchTerm").val(acq.outerText);

			 	dq_dumy_searchTermTextbox.value = dumy_searchTerm.outerText;
			 	
			 	dq_searchTextbox.focus();
			 	dq_setAcLineBgColor();
			 	e.returnValue = false;
			}
		}
	}
 	
 	//자동완성레이어의 선택된 키워드를 textbox에 넣어줌(IE외 브라우져) - ackhl_ff
 	function dq_acKeywordTextViewFF(fireFoxEvent)
	{		
		var ac, acq, category_plan, dumy_searchTerm;
		var resultDiv = document.getElementById(resultDiv);
		var qs_ac_len = 0;
		
		if(($("#searchTerm").val() != null && $("#searchTerm").val() != "") && dq_chk=="N"){
			if(dq_searchResultList_f != ""){
				qs_ac_len = dq_searchResultList_f.length;
			}
		}else{	
			if(cookieCount > 0 && $("#headChangeKey").val() == "C"){
				qs_ac_len = cnt;
			}else {
				if(dq_searchResultList_f != ""){
					qs_ac_len = dq_searchResultList_f.length;
				}
			}
		}
		
	 //	if(fireFoxEvent.keyCode==39)
	 	//	dq_reqAcResultShow();
	 		 
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
					dq_keywordBak = $("#searchTerm").val();
					
				dq_acLine++;
						 
			 	ac = document.getElementById('dq_ac' + dq_acLine);
			 	acq = document.getElementById('dq_acqHidden' + dq_acLine);
			 	dumy_searchTerm = document.getElementById('dumy_searchTerm' + dq_acLine);

			 	dq_keywordOld = acq.value;
			 	$("#searchTerm").val(acq.value);
			 	dq_dumy_searchTermTextbox.value = dumy_searchTerm.innerText;

			 	dq_searchTextbox.focus();
			 	dq_setAcLineBgColor();
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
			 	dq_keywordOld = dq_keywordBak;
			 	$("#searchTerm").val(dq_keywordBak);
		 	} 
		 	else
		 	{
		 		dq_acLine-- ;
			 
			 	ac = document.getElementById('dq_ac' + dq_acLine);
			 	acq = document.getElementById('dq_acqHidden' + dq_acLine);
			 	dumy_searchTerm = document.getElementById('dumy_searchTerm' + dq_acLine);

			 	dq_keywordOld = acq.value;
			 	$("#searchTerm").val(acq.value);
			 	dq_dumy_searchTermTextbox.value = dumy_searchTerm.innerText;
			 	
			 	dq_searchTextbox.focus() ;
			 	dq_setAcLineBgColor() ;
			 	fireFoxEvent.preventDefault();
			}
		}						
	}
 	
 	//검색요청 - reqAc
 	function dq_reqSearch() 
 	{	 				
		var sv_f;
		var sv_b;
		var ke = dqc_trimSpace($("#searchTerm").val());
		
		ke = ke.replace(/ /g, "%20");
		
		while(ke.indexOf("\\") != -1)
			ke = ke.replace(/ /g, "%20").replace("\\", "");
		
		while(ke.indexOf("\'") != -1)
			ke = ke.replace(/ /g, "%20").replace("\'", "");

	 	/*if(ke == ""){
	 		sv_f = "/search/ajax/New_headGetAutoKeyword.do?p=1&q=";
	 	}else{ 		
	 		sv_f = "/search/ajax/New_headGetAutoKeyword.do?p=1&q=" + escape(encodeURIComponent(ke));
	 	}*/
		
		sv_f = "/search/ajax/New2016_headGetAutoKeyword.do?p=1&q=" + escape(encodeURIComponent(ke));
	
	 	//alert(sv_f);
	 	
	 	dq_ajaxReqObj_f = dqc_getXMLHTTP();
	 	
	 	if(dq_ajaxReqObj_f)
	 	{ 		 		
	 		dq_ajaxReqObj_f.open("GET", sv_f, true);
	 		dq_ajaxReqObj_f.onreadystatechange = dq_acShow;
	 	} 
	 
	 	try
	 	{		 	
	 		dq_ajaxReqObj_f.send(null);
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
			if(dq_ajaxReqObj_f.readyState==4 && dq_ajaxReqObj_f.responseText && dq_ajaxReqObj_f.status==200)
			{					
				//alert(dq_ajaxReqObj_f.responseText);
				eval(dq_ajaxReqObj_f.responseText);
				dq_setAcResult(dq_searchKeyword, dq_searchResultList_f);
				dq_acResultShow(dq_searchKeyword, dq_searchResultList_f);
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
	 	dq_keywordOld = keyword;
	 	$("#searchTerm").val(keyword);
	 	dq_searchTextbox.focus();
	 	dq_acDisplayHide();		
 	}
 	
 	//기능끄기 버튼을 눌렀을때 - ac_off
	function dq_acOff() 
	{		
		if($("#searchTerm").val() == "") {
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
			
			if($("#searchTerm").val() == "")			
				resultDiv.innerHTML = dq_getAcOnNoKeyword();	
		}
		else
		{
			if($("#searchTerm").val() == "")
				resultDiv.innerHTML = dq_getAcNoKeyword();						
		}
		
		if($("#searchTerm").val() == "" && (resultDiv.style.display == "block")) {
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
 		var keyword = "";
 		var keywordOrign = "";
 		var rekeyword = "";
 		var text = "";
 		var count = 0;
 		var pos = 0;
 		var result = "";
 		
 		var dpFrontList = "";
 		var textCheck = 0;
 		
 		if(dq_searchKeyword!="") {
 			dpFrontList = dq_searchResultList_f;
 			
	 		if(dpFrontList != null && dpFrontList != "" && dpFrontList.length > 0)
			{	 			
	 			text += "<div id=\"serachResultCookie\" class=\"search-layer-initial active\">";
	 			
	 			//FKEY
	 			if(dpFrontList != ""){
				 	for(i=0;i<dpFrontList.length;i++)
				 	{
				 		result = dpFrontList[i].split("|");
				 		keyword = keywordOrign = result[0];
				 		rekeyword = keyword.replace("HHHHHHHHHH", "");
				 		keywordSumm = rekeyword.length>22?rekeyword.substring(0,22)+"...":rekeyword;
						count = result[1];
						 
						if(dq_engFlag == 0)
							pos = keywordOrign.indexOf($("#searchTerm").val());
						else if(dq_engFlag == 1)
							pos = keywordOrign.indexOf(dq_searchKeyword);
					
						if(pos >= 0)
						{
							if(pos == 0)
							{
								if(dq_engFlag == 0)
									keywordSumm = dqc_highlight(keywordSumm, $("#searchTerm").val(), 0, dq_hStartTag, dq_hEndTag);
								else if(dq_engFlag == 1)
									keywordSumm = dqc_highlight(keywordSumm, dq_searchKeyword, 0, dq_hStartTag, dq_hEndTag);
							}
							else if(pos == keywordOrign.length - 1)
							{
								if(dq_engFlag == 0)
									keywordSumm = dqc_highlight(keywordSumm, $("#searchTerm").val(), -1, dq_hStartTag, dq_hEndTag);
								else if(dq_engFlag == 1)
									keywordSumm = dqc_highlight(keywordSumm, dq_searchKeyword, -1, dq_hStartTag, dq_hEndTag);													
							}												
							else
							{						
								if(dq_engFlag == 0)
									keywordSumm = dqc_highlight(keywordSumm, $("#searchTerm").val(), pos, dq_hStartTag, dq_hEndTag);
								else if(dq_engFlag == 1)
									keywordSumm = dqc_highlight (keywordSumm, dq_searchKeyword, pos, dq_hStartTag, dq_hEndTag);
							}
						}
						if(i == 0){
							text += "<ul>";
	
						}
						text += "<li id='dq_ac" + (i+1) + "' onmouseover=\"dq_setAcPos('" + (i+1) + "')\" onFocus=\"dq_setAcPos('" + (i+1) + "');\" onmouseout=\"dq_setAcPos(0);\"  onBlur=\"dq_setAcPos(0);\" onclick=\"dq_setAcInput('" + keywordOrign + "');dq_keywordSearch('" + keywordOrign + "');\" onkeypress=\"dq_setAcInput('" + keywordOrign + "');\" >";
						text += "<a href=\"#\" id=\"dq_alink" + (i+1) + "\">";
						text +=  keywordSumm;
						text += "<em>";
						text +=  count;
						text +=  "회</em>";
						text += "</a>";
						text +=  "<input type=\"hidden\" id=\"dq_acqHidden" + (i+1) + "\" value=\"" + rekeyword + "\"/>";
						text += "<span id='dumy_searchTerm" + (i+1) + "' style='display:none'>"+keywordOrign+"</span>";
						text += "<span id='dq_acq" + (i+1) + "' style='display:none'>" + rekeyword + "</span></li>";
						if(i == dpFrontList.length-1){
							text += "</ul>";
						}
				 	}
	 			}
			 	text += "</div>";	
		 	}
	 		dq_iniChk="N";
	 		dq_chk="N";
 		}//else if(dq_cookieDel=="N" && (dpFrontList != null && dpFrontList != "" && dpFrontList.length > 0)) {	
	 	return text;
	}
 	
 	//나의 검색어 출력 && 인기검색어
 	function dq_getAcMySearchTermList() {
 		var keyword = "";
 		var keywordOrign = "";
 		var text = "";
 		var ranking_ = "";
 		var allCookies = "";
 		var pos = 0;
 		var result = "";
 		var dqTrendList = "";
 		var dt = new Date();
 		var CookieVal = "";
 		var CookieDate = "";

 		text += "<div id=\"serachResultCookie\" class=\"search-layer-words active\">";
 		text += "<dl>";

 		if(dq_searchKeyword == ""){
 			
 		if($("#headChangeKey").val() == "C"  || $("#headChangeKey").val() == ''){
	 		text += "<dt class=\"layer-tab-recent active\"><a href=\"#\" onmouseover=\"changeKey('C');\">최근검색어</a></dt>";
	 		text += "<dd>";
	
	 		var str = getSearchCookie("dq_cookie_search");
	 		var saveStatus = getSearchCookie("dq_mykeyword_save");
	 		
				if ( str != "" && str != ";" ) {
					allCookies = str.split( '∃' );
					cookieCount = allCookies.length;
					text += "<ul>";
		
					if ( allCookies.length > 10 ) {
						cookieCount = 10;
					}
		
					for( var i=0; i<cookieCount; i++ ) {
						if( allCookies[i] == "" || allCookies[i] == ";" )	break;
						if(allCookies[i].indexOf("IFRAME") != -1){
							continue;
						}
						
						CookieVal = allCookies[i].split("$|")[0];
						CookieDate = allCookies[i].split("$|")[1];
						
						text += "<li id='dq_ac" + (i+1) + "' onmouseover=\"dq_setAcPos('" + (i+1) + "')\" onFocus=\"dq_setAcPos('" + (i+1) + "');\" onmouseout=\"dq_setAcPos(0);\"  onBlur=\"dq_setAcPos(0);\" onclick=\"dq_setAcInput('"+CookieVal+"');\" onkeypress=\"dq_setAcInput('"+CookieVal+"');\" >";
						text += "<a href=\"#\" onclick=\"dq_keywordSearch('" + CookieVal + "');\" id=\"dq_alink" + (i+1) + "\">";
						if(CookieVal.length > 20){
							text += CookieVal.substring(0,20)+"...";
						}else{
							text += CookieVal;
						}
						if(CookieDate != undefined){
							text += "<em>"+CookieDate+"</em></a>";
						}else{
							text += "</a>";
						}
						text += "<span id='dumy_searchTerm" + (i+1) + "' style='display:none'></span>";
						text += "<input type=\"hidden\" id=\"dq_acqHidden" + (i+1) + "\" value=\"" +CookieVal + "\"/>";
						text += "<span id='dq_acq" + (i+1) + "' style='display:none'>" + CookieVal  + "</span>";
						text += "<button type=\"button\" onclick=\"delSearchCookie('dq_cookie_search','" + allCookies[i] + "');\">삭제</button></li>";
						//<li><a href="#">과자<em>02.04</em><button type="button">삭제</button></a></li>

					}
					text += "</ul>";
				} else {
					text += "<p>최근 검색한 기록이 없습니다.</p>";
				}  
				text += "<div class=\"layer-bottom-area\">";
				text += "<button type=\"button\" onclick=\"javascript:delSearchCookieAll('dq_cookie_search');\">전체삭제</button>";
				if(saveStatus.split( '∃' )[0] == 'off'){
					text += "<button type=\"button\" id=\"target\" onclick=\"javascript:saveOn();\">저장켜기</button>";
				}if(saveStatus.split( '∃' )[0] == 'on'){
					text += "<button type=\"button\" id=\"target\" onclick=\"javascript:saveOff();\">저장끄기</button>";
				}
			 	text += "</dd>";
			 	text += "<dt class=\"layer-tab-popular\"><a href=\"#\" onmouseover=\"changeKey('T');\">인기검색어</a></dt>";
			 	cnt = cookieCount-1;
	 		}
 		
	 		else if($("#headChangeKey").val() == "T"){
			 	//인기검색어
	 			text += "<dt class=\"layer-tab-recent\"><a href=\"#\" onmouseover=\"changeKey('C');\">최근검색어</a></dt>";
				text += "<dt class=\"layer-tab-popular active\"><a href=\"#\" onmouseover=\"changeKey('T');\">인기검색어</a></dt>";
				text += "<dd>";
				dqTrendList = dq_searchResultList_f;
				
				var endYear = dt.getFullYear();
			 	var endMonth = dt.getMonth()+1;
			 	var endDay = dt.getDate();
			 	
			 	dt.setDate(dt.getDate() - 7); 
			 	var strYear = dt.getFullYear();
			 	var strMonth = dt.getMonth()+1;
			 	var strDay = dt.getDate();
			 	
			 	if(strMonth < 10)	strMonth = "0"+strMonth;
			 	if(strDay < 10)	strDay = "0"+strDay;
			 	if(endMonth < 10)	endMonth = "0"+endMonth;
			 	if(endDay < 10)	endDay = "0"+endDay;
			 	
			 	var startDate = strYear + ". " +  strMonth + ". " + strDay;
			 	var endDate = endYear + ". " +  endMonth + ". " + endDay;

			 	if(dqTrendList != ""){
			 		text += "<ul>";
		
				 	for(i=0;i<dqTrendList.length;i++)
				 	{
				 		result = dqTrendList[i].split("|");
				 		
				 		keyword = keywordOrign = result[0];
				 		ranking = result[1];
		
				 		text += "<li id='dq_ac" + (i+1) + "' onmouseover=\"dq_setAcPos('" + (i+1) + "')\" onFocus=\"dq_setAcPos('" + (i+1) + "');\" onmouseout=\"dq_setAcPos(0);\"  onBlur=\"dq_setAcPos(0);\" onclick=\"dq_setAcInput('" + keywordOrign + "');\" onkeypress=\"dq_setAcInput('" + keywordOrign + "');\">";
						text += "<a href=\"#\" onclick=\"dq_keywordSearch('" + keywordOrign + "');\" id=\"dq_alink" + (i+1) + "\">";
						if(i+1 < 10){
							text += "<i class=\"popular-layer-icon-0"+ (i+1+ranking_) +"\">"+ (i+1+ranking_)+". </i>";
						}else{
							text += "<i class=\"popular-layer-icon-"+ (i+1+ranking_) +"\">"+ (i+1+ranking_)+". </i>";
						}
						text +=  keyword + "</a>";
						text +=  "<input type=\"hidden\" id=\"dq_acqHidden" + (i+1) + "\" value=\"" + keyword + "\"/>";
						text += "<span id='dumy_searchTerm" + (i+1) + "' style='display:none'>"+ keywordOrign + "</span>";
						text += "<span id='dq_acq" + (i+1) + "' style='display:none'>" + keywordOrign + "</span></li>";
				 	}
				 	text += "</ul>";
				 	text += "<div class=\"layer-bottom-area\">"+ startDate +  " ~ " +  endDate +"</div>";
				}
				text += "</dd>";
	 		}
		 	text += "</dl>";
		 	text += "</div>";

			dq_iniChk="Y";
			dq_chk="Y";
 		}
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
		
		var keyword = $("#searchTerm").val();
		var keywordSumm = "";

		if(keyword != "") dq_iniChk="N";
		/*if(keyword == "" && keyword != dq_keywordOld){
	 		dq_acDisplayHide();
	 	}*/	
		//cookieStatus
		
		
		if(cookieStatus==1){
			cookieStatus=0;
			dq_reqSearch();
		}
		else if(closeStatus==1){
			//alert("닫기상태");
		}
		else if((keyword == "" || keyword != dq_keywordOld) && dq_keyStatus != 1 && dq_iniChk=="N" && dq_chk=="N")
		{
			var o = null;
			o = dq_getAcResult();
			
			if(o && o[1][0] != "") { 
				dq_acResultShow(o[0], o[1]);
			} else {
				dq_reqSearch();
			}
			
		}
		else if((keyword == "" || keyword != dq_keywordOld) && dq_keyStatus != 1 && dq_chk=="Y"){
			if(keyword == ""){
				if( $("#dqAuto").css("display") != "none"){
					//searchHeadButton
				}else{
					dq_reqSearch();
				}
				
			}else{
				dq_reqSearch();
			}
		}
			
		
		dq_keywordOld = keyword;		
		setTimeout("dq_wi()", dq_intervalTime);
 	}
 	
	setTimeout("dq_wi()", dq_intervalTime);
