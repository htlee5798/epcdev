	//���콺 �ѿ����̹���
	var currentOn;
	
	function ImgOn(imgEl)
	{
		var name = imgEl.src;
		if( name.indexOf("on.gif") <= 0 ) {
			imgEl.src = name.replace(".gif", "on.gif");
		} else {
			currentOn = imgEl;
		}
	}
	
	function ImgOut(imgEl)
	{
		if( currentOn != imgEl ) {
			imgEl.src = imgEl.src.replace("on.gif", ".gif");
		}
	}

	/**
	 * img �ױ׿� �̹����� ���� ��� �⺻ �̹��� ǥ��
	 */
	function showNoImage(url,obj,width,gubun) {
		if(gubun==null || gubun=="undefined") gubun = "1";
		//�⺻�̹����� ���� ��� stack overflow error�߻��ϹǷ� �ݵ�� �־����.
		obj.src = url+"/prodimg/noimage_"+gubun+"_"+width+".jpg";
	}

	/**
	 * ���ڿ� �յ� �����̽� ����
	 */
	function trim(value) {
	   var temp = value;
	   var obj = /^(\s*)([\W\w]*)(\b\s*$)/;
	   if (obj.test(temp)) { temp = temp.replace(obj, '$2'); }
	   var obj = / +/g;
	   temp = temp.replace(obj, " ");
	   if (temp == " ") { temp = ""; }
	   return temp;
	}
	
	/**
	 * tab ó��
	 */
	function tabChange(name,index,length) {
		for(var i=1; i<=length; i++) {
			if(i==index) {
				if(document.all["tab"+name+i]) document.all["tab"+name+i].style.display = "";
				if(document.all["img"+name+i].src.indexOf("on.gif") <= 0) {
					document.all["img"+name+i].src = document.all["img"+name+i].src.replace(".gif", "on.gif");	
				}
			} else {
				if(document.all["tab"+name+i]) document.all["tab"+name+i].style.display = "none";
				document.all["img"+name+i].src = document.all["img"+name+i].src.replace("on.gif", ".gif");
			}
		}
	}
	
	/**
	 * ��Ű ����
	 */
	function getCookie(cookieName)
	{
		var search=cookieName + "=";
		if(document.cookie.length > 0) // ��Ű�� �����Ǿ� �ִٸ�
		{
			offset = document.cookie.indexOf(search);
			if(offset != -1) // ��Ű�� �����ϸ�
			{
				offset += search.length;
				// set index of beginning of value
				end=document.cookie.indexOf(";", offset);
				// ��Ű ���� ������ ��ġ �ε��� ��ȣ ����
				if(end == -1) {
					end=document.cookie.length;
				}
					
				return unescape(document.cookie.substring(offset, end));
			}
		}
		return "";
	}
	
	/**
	 * ��Ű �о����
	 */
	function setCookie(name, value)
	{
		var argv    = setCookie.arguments;
		var argc    = setCookie.arguments.length;
		var expires = (2 < argc) ? argv[2] : null;
		var path    = (3 < argc) ? argv[3] : null;
		var domain  = (4 < argc) ? argv[4] : null;
		var secure  = (5 < argc) ? argv[5] : false;
		document.cookie = name + "=" + escape (value) + ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + ((path == null) ? "" : ("; path=" + path)) + ((domain == null) ? "" : ("; domain=" + domain)) + ((secure == true) ? "; secure" : ""); 
	}
	
	//�����÷���

	function render_flash(url, width, height) {
		document.write('<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="' + width + '" height="' + height + '" VIEWASTEXT>');
		document.write('<param name="allowScriptAccess" value="always" />');
		document.write('<param name="movie" value="' + url + '">');
		document.write('<param name="quality" value="high">');
		document.write('<param name="wmode" value="transparent">');
		document.write('<param name="menu" value="false">');
		document.write('<embed src="' + url + '" allowScriptAccess="always" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="' + width + '" height="' + height + '" wmode="transparent"></embed>');
		document.write('</object>');
	}