/*
' ------------------------------------------------------------------
' Function    : openPopup(openUrl, popTitle)
' Description : 모달 형식의 팝업창을 띄운다
' Argument    : openUrl  - 창이 떴을때 열릴 주소
				popTitle - 창의 제목
' Return      : showModalDialog인 경우 임의의 반환값을 받을 수 있다
' ------------------------------------------------------------------
*/

function openPopup(openUrl, popTitle)
{	
	winstyle = "dialogWidth=280px; dialogHeight:203px; center:yes; status: no; scroll: no; help: no"; 
	result = window.showModalDialog(openUrl, window, winstyle);
}	


function setFocus(obj)
{
	//alert(obj);
	document.getElementsByName(obj)[0].focus();
}

function len_chk(Obj) {

	if (inputNumOnly(Obj)) {

		var a;
		a = Obj.name.length;
		if (Obj.name.substring(a-2,a) == "YY" || Obj.name.substring(7,9) == "YY" || Obj.name == "birth1") {
			if (Obj.value.length != 4 && Obj.value.length != 0) {
				alert("년도를 정확히 입력하여 주시기 바랍니다.\n(예)2005년->2005)");
				Obj.value = "";
				Obj.focus();
				}
		}
		else if (Obj.name.substring(a-2,a) == "MM" || Obj.name.substring(7,9) == "MM" || Obj.name == "birth2") {
			if (Obj.value.length != 2 && Obj.value.length != 0) {
				alert("달을 정확히 입력하여 주시기 바랍니다.\n(예)1월->01)");
				Obj.value = "";
				Obj.focus();
			}else if (Obj.value.length != 0 && (Obj.value < "01" || Obj.value > "32")) {
				alert("달을 정확히 입력하여 주시기 바랍니다.\n(예)1월->01)");
				Obj.value = "";
				Obj.focus();
			}
		}
		else if (Obj.name == "birth3")
		{
			if (Obj.value.length != 2 || (Obj.value < "01" || Obj.value > "31")) 
			{
				alert("이을 정확히 입력하여 주시기 바랍니다.\n(예)1일->01)");
				Obj.value = "";
				Obj.focus();
			}			
		}
		else if (Obj.name == "esta_yy")
		{
			if (Obj.value.length != 4 && Obj.value.length != 0) {
				alert("년도를 정확히 입력하여 주시기 바랍니다.\n(예)2005년->2005)");
				Obj.value = "";
				Obj.focus();
				}			
		}
		else if (Obj.name == "foundationYear")
		{		
			if (Obj.value.length != 4 && Obj.value.length != 0) {
				alert("년도를 정확히 입력하여 주시기 바랍니다.\n(예)2005년->2005)");
				Obj.value = "";
				Obj.focus();
				}			
		}
	
	}
}

function img_view(imgurl) {

//alert("tt")

  var w = "200";
  var h = "200";
  var width = "200";
  var height = "200";
  
  var path = "upload/" + imgurl;

  var openWidth = "250";
  var openHeight = "200";


  if (openWidth > 800)
    openWidth = 800;
  if (openHeight > 800)
    openHeight = 800;
        
  window.open('Pop_ViewImg.asp?img=' + path + '&w=' + w + '&h=' + h, 'imgwin', 'width=' + openWidth + ', height=' + openHeight + ', scrollbars=yes, resizable=yes');
}	

function openZipCode()
{
	url = "pop_Address_01.asp";
	window.open(url,"zip","toolbar=no,menubar=no,resizable=no,scrollbars=yes,width=304,height=298");
}	





function check_submit_2(type)
{
	if(MyForm.kor_name.value.length==0)
		{			
			strMsg ="한글 이름을 입력해 주십시요";         
			popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.kor_name";
			openPopup(popUrl,'ID');   
		
			return;
		}
		if(MyForm.nationality.value.length==0)
		{
			
			strMsg ="국적을 입력해 주십시요";         
			popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.nationality";
			openPopup(popUrl,'ID');   
		
			return;
		}
		if(MyForm.cha_name.value.length==0)
		{
			
			strMsg ="한자 이름을 입력해 주십시요";         
			popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.cha_name";
			openPopup(popUrl,'ID');   
		
			return;
		}
		if((MyForm.birth1.value.length==0) || (MyForm.birth2.value.length==0) || (MyForm.birth3.value.length==0))
		{
			
			strMsg ="생년월일을 입력해 주십시요";         
			popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.birth1";
			openPopup(popUrl,'ID');   
		
			return;
		}
		if(MyForm.age.value.length==0)
		{
			
			strMsg ="연령을 입력해 주십시요";         
			popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.age";
			openPopup(popUrl,'ID');   
		
			return;
		}
		if(MyForm.addr.value.length==0)
		{
			
			strMsg ="주소를 입력해 주십시요";         
			popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.addr";
			openPopup(popUrl,'ID');   
		
			return;
		}
		if(MyForm.email.value.length==0)
		{
			
			strMsg ="E-mail을 입력해 주십시요";         
			popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.email";
			openPopup(popUrl,'ID');   
		
			return;
		}
		if((MyForm.hp1.value.length==0) || (MyForm.hp2.value.length==0) || (MyForm.hp3.value.length==0))
		{
			
			strMsg ="핸드폰 번호를 입력해 주십시요";         
			popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.hp1";
			openPopup(popUrl,'ID');   
		
			return;
		}
		
		/* 사진 필수 아님!
		if(type == "I")
		{
			if(MyForm.Mypic.value.length==0)
			{
				
				strMsg ="사진을 입력해 주십시요";         
				popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.Mypic"
				openPopup(popUrl,'ID')   
			
				return;
			}
		}
		*/
		
		if(MyForm.addr.value.length==0)
		{
			
			strMsg ="주소를 입력해 주십시요";         
			popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.addr";
			openPopup(popUrl,'ID');   
		
			return;
		}
		if(MyForm.addr.value.length==0)
		{
			
			strMsg ="주소를 입력해 주십시요";         
			popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.addr";
			openPopup(popUrl,'ID');   
		
			return;
		}
		if(MyForm.addr.value.length==0)
		{
			
			strMsg ="주소를 입력해 주십시요";         
			popUrl = "pop_message_00.asp?strMsg=" + strMsg + "&objFocus=MyForm.addr";
			openPopup(popUrl,'ID');   
		
			return;
		}
		//loading();
		//alert("1");
		ShowProgress_0();			
		document.MyForm.submit();
		
}




/*function check_submit_4()
{
	var MyForm = document.getElementsByName("MyForm")[0];

		for (var i=0;i<3;i++) {
			if (MyForm.productName[i].value.length == 0) {
				strMsg ="msg.product.name";         
				popUrl = "/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=productCost["+i+"]";
				openPopup(popUrl,'ID');   
			
				return false;
			}
		}	
		
		for ( var i=0;i<3;i++) {
			if (MyForm.productCost[i].value.length == 0) {
				strMsg ="msg.product.cost";         
				popUrl = "/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=productCost["+i+"]";
				openPopup(popUrl,'ID');   
			
				return false;
			}
		}	
		
		for (var i=0;i<3;i++) {
			if (MyForm.productSalePrice[i].value.length == 0) {
				strMsg ="msg.product.saleprice";         
				popUrl = "/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=productCost["+i+"]";
				openPopup(popUrl,'ID');   
			
				return false;
			}
		}	
		
		for (var i=0;i<3;i++) {
			if (MyForm.monthlyAverage[i].value.length == 0) {
				strMsg ="msg.month.amount";         
				popUrl = "/epc/edi/consult/messagePopup.do?strMsg=" + strMsg + "&objFocus=productCost["+i+"]";
				openPopup(popUrl,'ID');   
			
				return false;
			}
		}		
		

	
	

	
	var buy_prc_cnt = MyForm.productCost.length;
		
	for(var i=0; i<buy_prc_cnt; i++)
	{
		var productCost = MyForm.productCost[i].value;
		MyForm.productCost[i].value = productCost.replace(/,/g, '');
		//alert(document.MyForm.buy_prc[i].value);
		
		var productSalePrice = MyForm.productSalePrice[i].value;
		MyForm.productSalePrice[i].value = productSalePrice.replace(/,/g, '');
		
		var monthlyAverage = MyForm.monthlyAverage[i].value;
		MyForm.monthlyAverage[i].value = monthlyAverage.replace(/,/g, '');
	}
	
	return true;
	//ShowProgress();			
	//document.MyForm.submit();
}	//document.all.submitbtn.style.display = "none";
*/
		
	
function check_submit_5(val)
{	
	
	
	if(val=="Y"){
		alert("승인 진행중입니다.");
		return;
		
	}else{
		
		//alert(document.MyForm.update_gb.value);
		var MyForm = document.getElementsByName("MyForm")[0];
		
		
		//alert("대분류1:"+MyForm.l1_cd.value);
		if(MyForm.l1_cd.value == ""){	
			alert("상담정보변경을 누르고 상담신청에서 팀을 신청하시고 다시 상담신청하세요! ");
			return;
		}
			
		
		if(MyForm.update_gb.value != "1")
		{
			
			var cf = confirm("상담신청 하시겠습니까?");
			
			
			
			if (cf == true)
			{
				//document.MyForm.update_gb.value = "1";
				//alert(document.MyForm.update_gb.value);
				document.MyForm.submit();
			}
			else
				return;
			}
		
		else
			alert("이미 신청 하였습니다!");
	
	}
		
		
}




function checkApply_submit(val)
{	
	var Password1 = document.MyForm.Password1.value;



	
	if(val=="Y"){
		alert("승인 진행중입니다.");
		return;		
	}else{	
		
		if (Password1==""){
			alert("비밀번호를 입력하세요.");
			return;
		}
		
		//alert(document.MyForm.update_gb.value);
		var MyForm = document.getElementsByName("MyForm")[0];
				
		
		
		if(MyForm.update_gb.value != "1")
		{
			
			var cf = confirm("상담신청 하시겠습니까?");
			
			if (cf == true)
			{
				//document.MyForm.update_gb.value = "1";
				//alert(document.MyForm.update_gb.value);
				document.MyForm.submit();
			}
			else
				return;
			}		
		else
			alert("이미 신청 하였습니다!");	
	}		
}






function Nextfocus(lenth,name,a)  
{
	if (name=="corpnRsdtNo1")
	{
	
	
	}	
	else if (name=="tel1")
	{
		for( i=0 ; i<a.value.length; i++)
		{
			if(a.value.charAt(i) <"0" || a.value.charAt(i)>"9")
			{
				alert("<spring:message code='msg.common.error.noNum'/>");
				a.focus();
				a.value="";
				return;
			}
		}
		if(lenth==3)
		{
			document.MyForm.tel2.focus();
		}
	}	
	else if (name=="tel2")
	{
		
		for( i=0 ; i<a.value.length; i++)
		{
			if(a.value.charAt(i) <"0" || a.value.charAt(i)>"9")
			{
				alert("<spring:message code='msg.common.error.noNum'/>");
				a.focus();
				a.value="";
				return;
			}
		}
		if(lenth==4)
		{
			document.MyForm.tel3.focus();
		}
	}	
	else if (name=="tel3")
	{

		
		for( i=0 ; i<a.value.length; i++)
		{
			if(a.value.charAt(i) <"0" || a.value.charAt(i)>"9")
			{
				alert("<spring:message code='msg.common.error.noNum'/>");
				a.focus();
				a.value="";
				return;
			}
		}
		if(lenth==4)
		{
			//document.MyForm.fax_no1.focus();
		}
	}	
		
	
	else if (name=="cell2")
	{
		for( i=0 ; i<a.value.length; i++)
		{
			if(a.value.charAt(i) <"0" || a.value.charAt(i)>"9")
			{
				alert("<spring:message code='msg.common.error.noNum'/>");
				a.focus();
				a.value="";
				return;
			}
		}
		if(lenth==4)
		{
			document.MyForm.cell3.focus();
		}
	}
	else if (name=="cell3")
	{
		for( i=0 ; i<a.value.length; i++)
		{
			if(a.value.charAt(i) <"0" || a.value.charAt(i)>"9")
			{
				alert("<spring:message code='msg.common.error.noNum'/>");
				a.focus();
				a.value="";
				return;
			}
		}
		if(lenth==4)
		{
			//document.MyForm.email.focus();
		}
	}
	else if (name=="officetel1")
	{
		for( i=0 ; i<a.value.length; i++)
		{
			if(a.value.charAt(i) <"0" || a.value.charAt(i)>"9")
			{
				alert("<spring:message code='msg.common.error.noNum'/>");
				a.focus();
				a.value="";
				return;
			}
		}
		if(lenth==3)
		{
			document.MyForm.officetel2.focus();
		}
	}
	else if (name=="officetel2")
	{
		for( i=0 ; i<a.value.length; i++)
		{
			if(a.value.charAt(i) <"0" || a.value.charAt(i)>"9")
			{
				alert("<spring:message code='msg.common.error.noNum'/>");
				a.focus();
				a.value="";
				return;
			}
		}
		if(lenth==4)
		{
			document.MyForm.officetel3.focus();
		}
	}
	else if (name=="officetel3")
	{
		for( i=0 ; i<a.value.length; i++)
		{
			if(a.value.charAt(i) <"0" || a.value.charAt(i)>"9")
			{
				alert("<spring:message code='msg.common.error.noNum'/>");
				a.focus();
				a.value="";
				return;
			}
		}
		if(lenth==4)
		{
			//document.MyForm.home_addr.focus();
		}
	}
}




/**
 * 모든 값들이 제대로 입력된 경우 submit을 수행되고
 * 파일이 업로드 되는경우 업로드 진행창을 보여준다  
 */
 
function ShowProgress_0()
{
   strAppVersion = navigator.appVersion;
   //alert("2");
   if (document.MyForm.Mypic.value != "") 
   {
      if (strAppVersion.indexOf('MSIE')!=-1 && strAppVersion.substr(strAppVersion.indexOf('MSIE')+5,1) > 4) 
      { 

          winstyle = "dialogWidth=385px; dialogHeight:150px; center:yes; status: no"; 
          window.showModelessDialog("dext_related/show_progress.asp?nav=ie", null, winstyle); 
      } 
      else 
      { 
          winpos = "left=" + ((window.screen.width-380)/2)+",top=" + ((window.screen.height-110)/2); 
          winstyle = "width=380,height=110,status=no,toolbar=no,menubar=no," + "location=no,resizable=no,scrollbars=no,copyhistory=no," + winpos; 
          window.open("dext_related/show_progress.asp",null,winstyle); 
      } 
   }
   else
	{
	   alert("새로운 이미지를 등록하지 않으셨습니다.\n다음페이지로 넘어갑니다.");
	}
   return true;
} 

function ShowProgress()
{
   strAppVersion = navigator.appVersion;
  
   if (document.MyForm.item_imgPath.value != "") 
   {
      if (strAppVersion.indexOf('MSIE')!=-1 && strAppVersion.substr(strAppVersion.indexOf('MSIE')+5,1) > 4) 
      { 

          winstyle = "dialogWidth=385px; dialogHeight:150px; center:yes; status: no"; 
          window.showModelessDialog("dext_related/show_progress.asp?nav=ie", null, winstyle); 
      } 
      else 
      { 
          winpos = "left=" + ((window.screen.width-380)/2)+",top=" + ((window.screen.height-110)/2); 
          winstyle = "width=380,height=110,status=no,toolbar=no,menubar=no," 
				   + "location=no,resizable=no,scrollbars=no,copyhistory=no," 
				   + winpos; 
          window.open("dext_related/show_progress.asp",null,winstyle); 
      } 
   }
   else
	{
	   alert("이미 등록된 이미지를 등록합니다.\n잠시 기다려주세요");
	}
   
   return true;
} 
function loading(){
		document.all.loading.style.visibility = 'visible';
	}
