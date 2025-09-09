	
	//브라우져 종류 체크 IE/ETC
	function dqc_getBrowserType()
	{
		if(navigator.appName == "Microsoft Internet Explorer")
			return 1;  
		else
			return 2;
	}			 	 	  
 	
	//create XMLHTTP 
 	function dqc_getXMLHTTP(xmlRequest) 
	{
		if(xmlRequest && xmlRequest.readyState!=0) 
			xmlRequest.abort() ;
	 
	 	try
	 	{
	 		xmlRequest = new ActiveXObject("Msxml2.XMLHTTP");
	 	}
		catch(e)
		{ 
			try
			{
				xmlRequest = new ActiveXObject("Microsoft.XMLHTTP");
			}
		 	catch(e)
		 	{
		 		xmlRequest = false;
		 	}
		}
	
		if(!xmlRequest && typeof XMLHttpRequest!= "undefined") 
			xmlRequest = new XMLHttpRequest();										
				
		return xmlRequest;
 	} 	 	

 	//trim, 공백제거
 	function dqc_trimSpace(ke)
	{ 
		ke = ke.replace(/^ +/g, "");
		ke = ke.replace(/ +$/g, " ");
		
		ke = ke.replace(/^ +/g, " ");
		ke = ke.replace(/ +$/g, "");
		 
		ke = ke.replace(/ +/g, " ");
		
		return ke ;
 	} 

 	//문장 하이라이팅
 	function dqc_highlight(s, d, is_suf, sTag, eTag)
 	{
		var ret = "";
		
		if(is_suf == 0)
			ret=dqc_makehigh_pre(s, d, sTag, eTag);
	 	else if(is_suf == -1)
			ret=dqc_makehigh_suf(s, d, sTag, eTag);
	 	else
			ret=dqc_makehigh_mid(s, d, is_suf, sTag, eTag);

		if(ret=="")
			return s;
	 	else
		 	return ret;
 	}
 	
 	//앞부분 단어 하이라이팅
 	function dqc_makehigh_pre(s, t, sTag, eTag)
 	{ 
		var d="";
		var s1 = s.replace(/ /g, "");
		var t1 = t.replace(/ /g, "");
		
		t1 = t1.toLowerCase();
		
		if(t1==s1.substring(0, t1.length))
		{
			d = sTag;
		
			for(var i=0,j=0; j<t1.length; i++) 
			{
				if(s.substring(i, i+1) != " ") 
					j++;
				d += s.substring(i, i+1);
		 	}
		 	
		 	d += eTag + s.substring(i, s.length)
	 	} 
		return d;
 	} 
 
 	//뒷부분 단어 하이라이팅
	function dqc_makehigh_suf(s, t, sTag, eTag) 
 	{ 
		var d = "";
		var s1 = s.replace(/ /g, "");
	 	var t1 = t.replace(/ /g, "");
	 	
	 	t1 = t1.toLowerCase();
	 	
	 	if(t1==s1.substring(s1.length-t1.length))
	 	{ 
			for(var i=0,j=0;j<s1.length-t1.length;i++)
			{ 
				if(s.substring(i, i+1) != " ") 
					j++;
				d += s.substring(i, i+1);
		 	} 
		
			d += sTag;
		 
		 	for(var k=i,l=0;l<t1.length;k++)
		 	{
				if(s.substring(k, k+1) != " ")
					l++;
			 	d += s.substring(k, k+1);
		 	}
		 	
		 	d += eTag;
	 	}
	 	
	 	return d;
 	} 

 	//중간부분 단어 하이라이팅
	function dqc_makehigh_mid(s, t, pos, sTag, eTag)
 	{ 
		var d = "";
		var s1 = s.replace(/ /g, "");
		var t1 = t.replace(/ /g, "");
		
		t1 = t1.toLowerCase();
		d = s.substring(0, pos);
		d += sTag;
		
		for(var i=pos,j=0;j<t1.length; i++) 
		{
			if(s.substring(i, i+1) != " ")
				j++;
			d += s.substring(i, i+1);
		}
		
		d += eTag + s.substring(i, s.length);
		
		return d;
	}	 		
 	
 	//string length
	function dqc_strlen(s)
 	{ 
		var i,l=0;
		
	 	for(i=0; i<s.length; i++) 
	 	{
			if(s.charCodeAt(i) > 127)
				l+=2;
		 	else
		 		l++;
		}
	 	
	 	return l;
 	}

	//string substring
 	function dqc_substring(s, start, len)
 	{ 
		var i,l=0;d="";
		
		for(i=start;i<s.length && l<len;i++)
		{
			if(s.charCodeAt(i) > 127) 
				l+=2;
			else
				l++;
		
			d += s.substr(i,1);
	 	}
	 		 
	 	return d ;
 	}
 	//숫자  콤마(,) 추가
 	function addComma(n) {
 		 if(isNaN(n)){return 0;}
 		  var reg = /(^[+-]?\d+)(\d{3})/;   
 		  n += '';
 		  while (reg.test(n))
 		    n = n.replace(reg, '$1' + ',' + '$2');
 		  return n;
 		}

 	//-----검색필드 선택-----
 	function DQselectSearchField(field) {
 		document.getElementById("searchField").value = field;
 		//document.mainSearchForm.searchField.value = field;
 	}
 	
 	//-----검색 요청-----
 	function DQhandleEnter (kind, event, flag) {
 	     var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;

 	     if (keyCode == 13) {
 	    	 if(flag=="mainSearch") {
 	    		 DQmainSearch_submit();
 	    	 } else if(flag=="reSearch") {
 	    		 DQreSearch_submit();
 	    	 } else if(flag=="footerSearch") {
 	    		DQfooterSearch_submit();
 	    	 }
 	    	 return false;
 	     } else {
 	    	 return true;
 	     }
 	  }
 	
 	//-----검색 요청 헤더 -----
 	function DQmainSearch_submit(){
 		var searchField = document.getElementById("searchField").value;
 		var searchTerm = document.getElementById("searchTerm").value;
 		var search_link_nm = document.getElementById("search_link_nm").value;
 		var search_link_url = document.getElementById("search_link_url").value;
 		var category_plan  = document.getElementById("category_plan").value;
 		var dumy_searchTerm = document.getElementById("dumy_searchTerm").value; 
 		var searchpViewType= ""; 
 		if(document.getElementById("searchpViewType")) searchpViewType= document.getElementById("searchpViewType").value; 
 		var new_searchTerm = "";

 		setSearchCookie( 'dq_cookie_search', searchTerm );
 		
 		
 		if(searchTerm.indexOf(">")){
 			new_searchTerm = searchTerm.replace(">", "^");
 		}
		
 		if(searchTerm == search_link_nm && search_link_url != ""){
 			location.href=search_link_url;
 		} else {
 			//searchTerm = searchTerm.replace(/</g, '').replace(/>/g, '').replace(/&/g, '').replace(/"/g, '').replace(/'/g, '').replace(/#/g, '');
	 		document.getElementById("searchTerm").value = searchTerm;
	 		var cnt = searchTerm.length;

	 		if(cnt < 1){
	 			alert(msg_common_search_termCheck);	//검색하실 내용을 입력하세요.
	 			document.getElementById("searchTerm").focus();
	 			return;
	 		}else {
	 			if(new_searchTerm.indexOf("^")>0){
	 				var n_searchTerm = new_searchTerm.split("^")[1];
	 				location.href="/search/search.do?searchTerm="+encodeURIComponent(n_searchTerm)+ "&cateName2="+encodeURIComponent(new_searchTerm)+ "&viewType="+searchpViewType;
	 			}else if(category_plan != "temp" && category_plan != "" && category_plan != null && category_plan.indexOf("lottemart")<0){
	 				goCategoryList(category_plan);
	 			}else if(category_plan.indexOf("lottemart")>0){
	 				location.href=category_plan;	
	 			}else if(dumy_searchTerm.substring(0, 10) == "HHHHHHHHHH"){
	 				location.href="/search/search.do?searchField="+searchField+"&searchTerm="+encodeURIComponent(dumy_searchTerm.replace(/%/g, '%25'))+ "&viewType="+searchpViewType;
	 			}else{
	 				location.href="/search/search.do?searchField="+searchField+"&searchTerm="+encodeURIComponent(searchTerm.replace(/%/g, '%25'))+ "&viewType="+searchpViewType;
	 			}
	 		}
 		}
 	  }

 	//-----검색 요청 재검색 -----
 	function DQreSearch_submit(){
 		var searchTermExt = $("#searchTermExt").val();
		searchTermExt = searchTermExt.replace(/</g, '').replace(/>/g, '').replace(/&/g, '').replace(/"/g, '').replace(/'/g, '').replace(/#/g, '').replace(/^/g, '').replace(/%/g, '%25');
 		document.getElementById("searchTermExtValue").value = searchTermExt;
 		var cnt = searchTermExt.length;
 		setSearchCookie( 'dq_cookie_search', searchTermExt );

 		if(cnt < 1){
 			alert(msg_common_search_termCheck);	//검색하실 내용을 입력하세요.
 			document.getElementById("searchTermExt").focus();
 			return;
 		}else {
 			if(document.reSearchForm.reFlag[0].checked || document.reSearchForm.reFlag[1].checked) {
 				document.getElementById("reSearchForm").action="/search/search.do";
 				document.getElementById("reSearchForm").submit();
 			} else {
 				DQselectKeyword(searchTermExt);
 			}
 		}
 	  }
 	
 	//-----검색 요청 풋터 -----
 	function DQfooterSearch_submit(){
 		var FooterSearchTerm = document.getElementById("FooterSearchTerm").value;
 		var search_link_nm = document.getElementById("search_link_nm").value;
 		var search_link_url = document.getElementById("search_link_url").value;
 		if(FooterSearchTerm == search_link_nm && search_link_url != ""){
 			location.href=search_link_url;
 		} else {
 			FooterSearchTerm = FooterSearchTerm.replace(/</g, '').replace(/>/g, '').replace(/&/g, '').replace(/"/g, '').replace(/'/g, '').replace(/#/g, '').replace(/^/g, '').replace(/%/g, '%25');
	 		document.getElementById("FooterSearchTermValue").value = FooterSearchTerm;
	 		var cnt = FooterSearchTerm.length;
	
	 		if(cnt < 1){
	 			alert(msg_common_search_termCheck);	//검색하실 내용을 입력하세요.
	 			document.getElementById("FooterSearchTerm").focus();
	 			return;
	 		}else {
	 			document.getElementById("footerSearchForm").action="/search/search.do";
	 			document.getElementById("footerSearchForm").submit();
	 		}
 		}
 	  }
 	
 // setCookie
	function setSearchCookie( cookieName, cookieValue ) {
		if(cookieValue.indexOf(">")>0){
			cookieValue = cookieValue.split(">")[1];
		}
		var expire = new Date();
        expire.setDate(expire.getDate() + 7); 
		var ex = getSearchCookie( cookieName );
		var index = ex.indexOf( cookieValue );
		if( index == -1 ) {
			document.cookie = cookieName + "=" + escape( cookieValue + "∃" + ex ).replace("undefined","") + ";expires=" + expire.toGMTString() + "; path=/";
			
		}
		
	}

	// cookie 하나 삭제
	function delSearchCookie( cookieName, cookieValue ) {
		cookieStatus=1;
		var ex = getSearchCookie( cookieName );
		ex = ex.replace(cookieValue+"∃","");
		document.cookie = cookieName + "=" + escape( ex ).replace( "undefined", "" ) + ";  path=/";
	}

	// cookie 모두 삭제
	function delSearchCookieAll( cookieName ) {		
		cookieStatus=1;
		document.cookie = cookieName + "=" + "; path=/";
		
	}

	// getCookie
	function getSearchCookie( cookieName ) {
		var search = cookieName + "=";
		var cookie = document.cookie;
		//alert(cookie);
		
		// 현재 쿠키가 존재할 경우
		if( cookie.length > 0 ) {
		// 해당 쿠키명이 존재하는지 검색한 후 존재하면 위치를 리턴.
			startIndex = cookie.indexOf( cookieName );
			//alert(cookie);

			// 만약 존재한다면
			if( startIndex != -1 ) {
				// 값을 얻어내기 위해 시작 인덱스 조절
				startIndex += cookieName.length;

				// 값을 얻어내기 위해 종료 인덱스 추출
				endIndex = cookie.indexOf( ";", startIndex );

			// 만약 종료 인덱스를 못찾게 되면 쿠키 전체길이로 설정
			if( endIndex == -1 ) endIndex = cookie.length;
				// 쿠키값을 추출하여 리턴
				return unescape( cookie.substring( startIndex + 1, endIndex ) );
			} else {
				// 쿠키 내에 해당 쿠키가 존재하지 않을 경우
				return "";
			}
		} else {
			// 쿠키 자체가 없을 경우
			return "";
		}
	}
 