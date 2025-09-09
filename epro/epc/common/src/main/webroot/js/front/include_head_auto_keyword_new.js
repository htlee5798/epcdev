
	//설정값
	var dq_searchForm = document.getElementById("mainSearchForm");
	var dq_searchFormTextbox = dq_searchForm.searchTerm;
	var dq_searchTextbox = document.getElementById("searchTerm");
	var dq_dumy_searchTermTextbox = document.getElementById("dumy_searchTerm");

	var dq_resultDivID = "dqAuto";               //자동완성레이어 ID
	var dq_hStartTag = "<strong>";                    //하이라이팅 시작 테그
	var dq_hEndTag = "</strong>";                     //하이라이팅 끝 테그
	var dq_bgColor = "#EAEAEA";                  //선택빽그라운드색
	var dq_intervalTime = 500;                   //자동완성 입력대기 시간
	
	//고정값
	var dq_acResult = new Object();              //결과값
	var dq_acLine = 0;                           //자동완성 키워드 선택  위치(순번)	
	var dq_searchResultList_f = "";                //자동완성결과리스트	(FKEY)
	var dq_searchResultList_c = "";                //자동완성결과리스트	(CATE)
	var dq_searchResultList_p = "";                //자동완성결과리스트	(PLAN)
	var dq_searchResultList_b = "";
	var dq_searchKeyword = "";	                 //검색어(한영변환안된)
	var dq_ajaxReqObj_f = "";                      //ajax request object
	var dq_ajaxReqObj_b = "";                      //ajax request object

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
	var dq_cookieDel = "N";						//기록삭제여부
	var dq_iniChk = "N";
	var dq_chk = "Y";
	var del_keyword = "";
	var cookieCount = 0;
	var cnt = 0;
	var closeStatus = 0; //1닫기, 0열기
	var cookieStatus = 0;//1삭제
	
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
		var n_keyword = "";
		var cate = "";
		if(keyword.indexOf("|")>0){
			n_keyword = keyword.split("|")[0];
			cate = keyword.split("|")[1];
			location.href="/search/search.do?searchTerm="+encodeURIComponent(n_keyword)+ "&cateName2="+encodeURIComponent(cate);
			setSearchCookie( 'dq_cookie_search', n_keyword );
		}else{
			location.href="/search/search.do?searchTerm="+encodeURIComponent(keyword);
			setSearchCookie( 'dq_cookie_search', keyword );
		}
		
	}
	
	//기획전 검색어 없을시 카테고리 URL
	function dq_CategoryUrl(url){
		location.href=url;
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
	 
	 	if(o && o[1][0] != "" && o[2][0] != "" && o[3][0] != "")
	 		dq_acResultShow(o[0], o[1], o[2], o[3]);
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
 	function dq_setAcResult(aq, al_f, al_p, al_c)
 	{
 		dq_acResult[aq] = new Array(aq, al_f, al_p, al_c);
 	}
 	
 	//자동완성 결과 보기 - ac_show
 	function dq_acResultShow(aq, al_f, al_p, al_c)
 	{
		if(aq != dqc_trimSpace($("#searchTerm").val()))
			dq_engFlag = 1;
 		else
			if(aq && aq != "" && aq != dqc_trimSpace($("#searchTerm").val())) 			
				return ;

	 	dq_searchKeyword = aq;
	 	dq_searchResultList_f = al_f;
	 	dq_searchResultList_p = al_p;
	 	dq_searchResultList_c = al_c;
	 			
	 	dq_printAcResult();
					
	 	if((dq_searchResultList_f != "" && dq_searchResultList_f.length) || (dq_searchResultList_p != "" && dq_searchResultList_p.length)|| (dq_searchResultList_c != "" && dq_searchResultList_c.length))
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
			if(dq_searchResultList_f[0] == "" && dq_searchResultList_p[0] == "" && dq_searchResultList_c[0] == "") {
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
 			//alert(resultDiv.innerHTML);
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
			if(dq_searchResultList_c != ""){
				qs_ac_len = dq_searchResultList_c.length;
			}
			
			if(dq_searchResultList_f != ""){
				qs_ac_len = qs_ac_len + dq_searchResultList_f.length;
			}
			
			if(dq_searchResultList_p != ""){
				qs_ac_len = qs_ac_len + dq_searchResultList_p.length;
			}
		}else{	
			if(cnt > 0){
				qs_ac_len = cnt;
			}
			if(dq_searchResultList_p != ""){
				qs_ac_len = qs_ac_len + dq_searchResultList_p.length;
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
			if(dq_searchResultList_c != ""){
				qs_ac_len = dq_searchResultList_c.length;
			}
			
			if(dq_searchResultList_f != ""){
				qs_ac_len = qs_ac_len + dq_searchResultList_f.length;
			}
			
			if(dq_searchResultList_p != ""){
				qs_ac_len = qs_ac_len + dq_searchResultList_p.length;
			}
		}else{	
			if(cookieCount > 0){
				qs_ac_len = cnt;
			}
			if(dq_searchResultList_p != ""){
				qs_ac_len = qs_ac_len + dq_searchResultList_p.length;
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

				//if(dq_acLine>11) dq_acLine=1;

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
		var ac, acq, dumy_searchTerm;
		var resultDiv = document.getElementById(resultDiv);
		var qs_ac_len = 0;
		
		if(($("#searchTerm").val() != null && $("#searchTerm").val() != "") && dq_chk=="N"){
			if(dq_searchResultList_c != ""){
				qs_ac_len = dq_searchResultList_c.length;
			}
			
			if(dq_searchResultList_f != ""){
				qs_ac_len = qs_ac_len + dq_searchResultList_f.length;
			}
			
			if(dq_searchResultList_p != ""){
				qs_ac_len = qs_ac_len + dq_searchResultList_p.length;
			}
		}else{	
			if(cookieCount > 0){
				qs_ac_len = cnt;
			}
			if(dq_searchResultList_p != ""){
				qs_ac_len = qs_ac_len + dq_searchResultList_p.length;
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

				dq_keywordOld = (acq.value) ? acq.value : '';
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
	 
	 	/*if(ke == "")
	 	{ 
	 		dq_acDisplayHide();
			return;
		} */	

	 	if(ke == ""){
	 		sv_f = "/search/ajax/New_headGetAutoKeyword.do?p=1&d="+dq_cookieDel;
	 	}else{ 		
	 		sv_f = "/search/ajax/New_headGetAutoKeyword.do?p=1&q=" + escape(encodeURIComponent(ke));
	 	}
	 	//alert(sv_f);
	 	
	 	dq_ajaxReqObj_f = dqc_getXMLHTTP();
	 	//dq_ajaxReqObj_b = dqc_getXMLHTTP();
	 	
	 	if(dq_ajaxReqObj_f)
	 	{ 		 		
	 		dq_ajaxReqObj_f.open("GET", sv_f, true);
	 		dq_ajaxReqObj_f.onreadystatechange = dq_acShow;
	 		//dq_ajaxReqObj_b.open("GET", sv_b, true);
	 		//dq_ajaxReqObj_b.onreadystatechange = dq_acShow;
	 	} 
	 
	 	try
	 	{		 	
	 		dq_ajaxReqObj_f.send(null);
	 		//dq_ajaxReqObj_b.send(null);
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
				//eval(dq_ajaxReqObj_b.responseText);
				dq_setAcResult(dq_searchKeyword, dq_searchResultList_f, dq_searchResultList_p, dq_searchResultList_c);
				dq_acResultShow(dq_searchKeyword, dq_searchResultList_f, dq_searchResultList_p, dq_searchResultList_c);
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
 		var plan_nm = "";
 		var cnt = 0;
 		var img_url = "";

 		var pos = 0;
 		var result = "";
 		
 		var dpFrontList = dq_searchResultList_f;
 		var dpCateList = dq_searchResultList_c;
 		var dpPlanList = dq_searchResultList_p;
 		var textCheck = 0;
 		
 		if(dq_searchKeyword!="") {
	 		if((dpCateList != null && dpCateList != "" && dpCateList.length > 0) || (dpFrontList != null && dpFrontList != "" && dpFrontList.length > 0)|| (dpPlanList != null && dpPlanList != "" && dpPlanList.length > 0))
			{	 			
	 			text += "<div id=\"serachResultCookie\" class=\"serachResult open\">";
	 			
	 			//CATE
	 			if(dpCateList != ""){
				 	for(i=0;i<dpCateList.length;i++)
				 	{
				 		result = dpCateList[i];
				 		keywordOrign = result;
				 		var temp_keyword = keywordOrign.split("^")[1];
				 		keyword = keywordOrign.replace("^", ">");
				 		
						
						if(i == 0){
							text += "<ul class=\"exhibitions-v1 open\">";
	
						}
						text += "<li id='dq_ac" + (i+1) + "' onmouseover=\"dq_setAcPos('" + (i+1) + "')\" onFocus=\"dq_setAcPos('" + (i+1) + "');\" onmouseout=\"dq_setAcPos(0);\"  onBlur=\"dq_setAcPos(0);\" onclick=\"dq_setAcInput('" + keywordOrign + "');dq_keywordSearch('" + temp_keyword +"|"+ keywordOrign+ "');\" onkeypress=\"dq_setAcInput('" + keywordOrign + "');\" >";
						text += "<a href=\"#\" id=\"dq_alink" + (i+1) + "\">";
						text += keyword;
						text += "</a>";
						text += "<input type=\"hidden\" id=\"dq_acqHidden" + (i+1) + "\" value=\"" + keyword + "\"/>";
						text += "<span id='dumy_searchTerm" + (i+1) + "' style='display:none'>" + keywordOrign+"</span>";
						text += "<span id='dq_acq" + (i+1) + "' style='display:none'>" + keyword + "</span></li>";			
						if(i == dpCateList.length-1){
							text += "</ul>";
						}
				 	}
			 	textCheck = 1;
			 	cnt = dpCateList.length;
				}
			 	if(textCheck && dpFrontList != ""){
			 		textCheck = 2;
			 	}
		 			
	 			//FKEY
	 			if(dpFrontList != ""){
	 				var temt_cnt = textCheck?cnt:0;
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
							text += "<ul class=\"query open-views\">";
	
						}
						text += "<li id='dq_ac" + (i+1+temt_cnt) + "' onmouseover=\"dq_setAcPos('" + (i+1+temt_cnt) + "')\" onFocus=\"dq_setAcPos('" + (i+1+temt_cnt) + "');\" onmouseout=\"dq_setAcPos(0);\"  onBlur=\"dq_setAcPos(0);\" onclick=\"dq_setAcInput('" + keywordOrign + "');dq_keywordSearch('" + keywordOrign + "');\" onkeypress=\"dq_setAcInput('" + keywordOrign + "');\" >";
						text += "<a href=\"#\" id=\"dq_alink" + (i+1+temt_cnt) + "\"><strong>";
						text +=  keywordSumm;
						text +=  "</strong>";
						text += "<em>";
						text +=  count;
						text +=  "회</em>";
						text += "</a>";
						//text +=  "<span>삭제</span>";
						text +=  "<input type=\"hidden\" id=\"dq_acqHidden" + (i+1+temt_cnt) + "\" value=\"" + rekeyword + "\"/>";
						text += "<span id='dumy_searchTerm" + (i+1+temt_cnt) + "' style='display:none'>"+keywordOrign+"</span>";
						text += "<span id='dq_acq" + (i+1+temt_cnt) + "' style='display:none'>" + rekeyword + "</span></li>";
						if(i == dpFrontList.length-1){
							text += "</ul>";
						}
				 	}
				 	textCheck = 2;
				 	cnt += dpFrontList.length;
	 			}
	 			
			 	if(textCheck && dpPlanList != ""){
			 		textCheck = 3;
			 	}
			 	
			 	//기획전
			 	if(dpPlanList != ""){
			 		var temt_cnt = textCheck?cnt:0;
	
				 	for(i=0;i<dpPlanList.length;i++)
				 	{
				 		result = dpPlanList[i].split("^");
				 		keyword = keywordOrign = result[1];
				 		plan_nm = result[0];
	
						if(i == 0){
							text += "<h2 class=\"open\">검색어 관련기획전</h2>";
							text += "<ul class=\"exhibitions-v2 open\">";
	
						}
						text += "<li id='dq_ac" + (i+1+temt_cnt) + "' onmouseover=\"dq_setAcPos('" + (i+1+temt_cnt) + "')\" onFocus=\"dq_setAcPos('" + (i+1+temt_cnt) + "');\" onmouseout=\"dq_setAcPos(0);\"  onBlur=\"dq_setAcPos(0);\" onclick=\"dq_setAcInput('" + keywordOrign + "');goCategoryList('" + plan_nm + "');\" onkeypress=\"dq_setAcInput('" + keywordOrign + "');\">";
						text += "<a href=\"#\" id=\"dq_alink" + (i+1+temt_cnt) + "\">";
						text +=  keyword + "</a>";
						text +=  "<input type=\"hidden\" id=\"dq_acqHidden" + (i+1+temt_cnt) + "\" value=\"" + keywordOrign + "\"/>";
						text += "<span id='dumy_searchTerm" + (i+1+temt_cnt) + "' style='display:none'>"+ keywordOrign + "</span>";
						text += "<span id='dq_acq" + (i+1+temt_cnt) + "' style='display:none'>" + keywordOrign + "</span></li>";
						if(i == dpPlanList.length-1){
							text += "</ul>";
						}
				 	}
	 			}
	
			 	text += "<div class=\"btnClose open\">";
			 	text += "<a href=\"javascript:dq_acDisplayHide();\" >닫기 X</a>";
		 		text += "</div>";
			 	
			 	text += "</div>";	
		 	}
	 		dq_iniChk="N";
	 		dq_chk="N";
 		}//else if(dq_cookieDel=="N" && (dpFrontList != null && dpFrontList != "" && dpFrontList.length > 0)) {	
	 	return text;
	}
 	
 	//나의 검색어 출력
 	function dq_getAcMySearchTermList() {
 		var keyword = "";
 		var keywordOrign = "";
 		var rekeyword = "";
 		var text = "";
 		//var cnt = 0;
 		var img_url = "";
 		var allCookies = "";

 		var pos = 0;
 		var result = "";
 		
 		var dpPlanList = dq_searchResultList_p;
 		var textCheck = 0;

 		text += "<div id=\"serachResultCookie\" class=\"serachResult open\">";
		text += "<h2 class=\"open\">나만의 최근 검색어</h2>";
		text += "<ul class=\"query open-delete\">";

 		var str = getSearchCookie("dq_cookie_search");

		if ( str != "" && str != ";" ) {
			allCookies = str.split( '∃' );
			cookieCount = allCookies.length;

			if ( allCookies.length > 10 ) {
				cookieCount = 10;
			}
			

			
			for( var i=0; i<cookieCount; i++ ) {
				if( allCookies[i] == "" || allCookies[i] == ";" )	break;
				if(allCookies[i].indexOf("IFRAME") != -1){
					continue;
				}
				//alert(i + " : " + allCookies[i]);
				
				text += "<li id='dq_ac" + (i+1) + "' onmouseover=\"dq_setAcPos('" + (i+1) + "')\" onFocus=\"dq_setAcPos('" + (i+1) + "');\" onmouseout=\"dq_setAcPos(0);\"  onBlur=\"dq_setAcPos(0);\" onclick=\"dq_setAcInput('"+allCookies[i]+"');\" onkeypress=\"dq_setAcInput('"+allCookies[i]+"');\" >";
				text += "<a href=\"#\" onclick=\"dq_keywordSearch('" + allCookies[i] + "');\" id=\"dq_alink" + (i+1) + "\">";
				if(allCookies[i].length > 20){
					text += "<strong>"+allCookies[i].substring(0,20)+"..."+"</strong></a>";
				}else{
					text += "<strong>"+allCookies[i]+"</strong></a>";
				}
				text += "<span id='dumy_searchTerm" + (i+1) + "' style='display:none'></span>";
				text += "<input type=\"hidden\" id=\"dq_acqHidden" + (i+1) + "\" value=\"" +allCookies[i] + "\"/>";
				text += "<span id='dq_acq" + (i+1) + "' style='display:none'>" + allCookies[i]  + "</span>";
				text += "<a href=\"#\" onclick=\"delSearchCookie('dq_cookie_search','" +allCookies[i] + "');\"><span>삭제</span></a></li>";
			}
		} else {
			text += "<li id='dq_ac" + (i+1) + "' onmouseover=\"dq_setAcPos('" + (i+1) + "')\" onFocus=\"dq_setAcPos('" + (i+1) + "');\" onmouseout=\"dq_setAcPos(0);\"  onBlur=\"dq_setAcPos(0);\" onclick=\"dq_setAcInput('');\" onkeypress=\"dq_setAcInput('');\" >";
			text += "<a href=\"#\">";
			text += "<strong>최근검색한 내용이 없습니다.</strong></a>";
			text += "<span id='dumy_searchTerm" + (i+1) + "' style='display:none'></span>";
			text += "<input type=\"hidden\" id=\"dq_acqHidden" + (i+1) + "\" value=\"" +allCookies[i] + "\"/>";
			text += "<span id='dq_acq" + (i+1) + "' style='display:none'>" + allCookies[i]  + "</span>";
			text += "</li>";
		}  
		
		cnt = cookieCount-1;
		text += "</ul>";
		if(dpPlanList != ""){
	 		textCheck = 2;
	 	}
	 	//기획전
		if(dpPlanList != ""){
	 		var temt_cnt = textCheck?cnt:0;

		 	for(i=0;i<dpPlanList.length;i++)
		 	{
		 		result = dpPlanList[i].split("|");
		 		keyword = keywordOrign = result[1];
		 		img_url = result[0];


				if(i == 0){
					text += "<h2 class=\"open\">추천기획전</h2>";
					text += "<ul class=\"exhibitions-v2 open\">";

				}
				text += "<li id='dq_ac" + (i+1+temt_cnt) + "' onmouseover=\"dq_setAcPos('" + (i+1+temt_cnt) + "')\" onFocus=\"dq_setAcPos('" + (i+1+temt_cnt) + "');\" onmouseout=\"dq_setAcPos(0);\"  onBlur=\"dq_setAcPos(0);\" onclick=\"dq_setAcInput('" + keywordOrign + "');dq_CategoryUrl('" + img_url + "');\" onkeypress=\"dq_setAcInput('" + keywordOrign + "');\">";
				text += "<a href=\"#\" id=\"dq_alink" + (i+1+temt_cnt) + "\">";
				text +=  keyword + "</a>";
				text +=  "<input type=\"hidden\" id=\"dq_acqHidden" + (i+1+temt_cnt) + "\" value=\"" + keyword + "\"/>";
				text += "<span id='dumy_searchTerm" + (i+1+temt_cnt) + "' style='display:none'>"+ keywordOrign + "</span>";
				text += "<span id='dq_acq" + (i+1+temt_cnt) + "' style='display:none'>" + keywordOrign + "</span></li>";
				
				if(i == dpPlanList.length-1){
					text += "</ul>";
				}
		 	}
		}
	 	
		text += "<div class=\"btnClose2 open\">";
		text += "<a href=\"javascript:delSearchCookieAll('dq_cookie_search');\">전체삭제 X</a>";
		text += "</div>";
		text += "<div class=\"btnClose open\">";
	 	text += "<a href=\"javascript:dq_acDisplayHide();\" >닫기 X</a>";
 		text += "</div>";
		//alert(text);
		dq_iniChk="Y";
		dq_chk="Y";
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
			
			if(o && o[1][0] != "" && o[2][0] != "" && o[3][0] != "") { 
				dq_acResultShow(o[0], o[1], o[2], o[3]);
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
