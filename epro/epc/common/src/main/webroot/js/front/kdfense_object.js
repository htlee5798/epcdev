//********
var var_kLicense;
var kdfv_domain = window.location.hostname;
var request_os = window.navigator.appVersion;
//www.kings.co.kr 210012

var_kLicense = "3fbc015189e44a06358f75aa23d32b87824a4cfac7470de3f661862cbbc6b81f21";

if(kdfv_domain.search("\\.lottemart.com") != -1){
	var_kLicense = "092ea4c920ffacdde2de1adeeed18e1a8226a906f173bc0edc83512f000778c851";
}else if(kdfv_domain.search("lottemart.com") != -1){
	var_kLicense = "23a079ab6051252c02876ea7bbf2439d4b039d56c42d8c2455c12f905a6ce239a0";
}
var bTrident=navigator.userAgent.toUpperCase().indexOf('TRIDENT');
var bWindows=navigator.userAgent.toUpperCase().indexOf('WINDOWS');
var e=navigator.userAgent.toUpperCase().indexOf('EDGE');

/*
if(!window.ActiveXObject || (bTrident == -1 || bWindows == -1))
{
	alert("키보드 보안은 Internet Explorer에서 동작합니다");
}
*/

if( window.ActiveXObject || (bTrident != -1 && bWindows != -1))
{
	document.write('<object id=kdefense classid="CLSID:A4508A45-F1C4-40f3-99B4-0CA08AC77E3B"');
	if ( location.href.indexOf("https://") > -1 )
	{
		document.write('	codebase="https://www.lottemart.com/js/front/kdfense8.cab#Version=8,3,8,1"');
	}
	else
	{
		document.write('	codebase="http://www.lottemart.com/js/front/kdfense8.cab#Version=8,3,8,1"');
	}
	document.write('	height=0 width=0 align=left size=0>');

	document.write('	<PARAM name="nOption"  value=', parseInt("0x00000006",16), '>');
	document.write(' 	<PARAM name="nOptionEx2" value=', parseInt("0x00008008",16), '>');

	//document.write('	<PARAM name="nModuleVersion" value="416032301">');    																
	//document.write('	<PARAM name="szModulePath"   value="http://kings.nefficient.co.kr/kings/kdfinj6x/416032301_6224/kdfinj.dll">');  
	//document.write('	<PARAM name="szModuleHash"   value="EED7A1587A93AE6B779D80D74F613558">'); 
	
	document.write('	<PARAM name="nModuleVersion" value="418031601">');    																
	document.write('	<PARAM name="szModulePath"   value="http://kings.nefficient.co.kr/kings/kdfinj6x/418031601_6277/kdfinj.dll">');  
	document.write('	<PARAM name="szModuleHash"   value="FF8E6BE56CD19251EDDBEF200362BB5F">');
	
	document.write(' 	<PARAM name="nExModuleVersion"  value="714100101">');  
	document.write(' 	<PARAM name="szExModulePath"    value="http://kings.nefficient.co.kr/kings/kdfmod3x/714100101_1053/kdfmod.dll">');
	document.write(' 	<PARAM name="szExModuleHash"    value="94751CD42F71041CC3A357519ABB2D14">');

	document.write('	<PARAM name="szSubClassName" value=MacromediaFlashPlayerActiveX|>');

	document.write('	<PARAM name="szGKey" value="58c705643199c2ff067850db4181dff9f0d63edeecde16ab33ac57c24703d22ff0">');
	document.write('	<PARAM name="kLicense" value=', var_kLicense, '>');
	document.write('</object>');
}
else if( bWindows != -1) //멀티브라우저
{
	alert("키보드 보안은 Internet Explorer에서 동작합니다");
}

// 2010.10.14. Kings 추가 : 이 함수를 추가하시면 키보드보안이 이상이 있는경우 그에 따른 경고 메시지를 보여줍니다.
function OnKdfenseError(nError) 
{
	if(nError == '0')
	{
		//alert("정상동작");
	}
	else if(nError == '10006') //kdfmod가 값을 제대로 가져가지 못했을때, 대표값이 나올 수 있으므로 안내페이지로 이동
	{
		window.open('http://www.kings.co.kr/k/help/KError.php?ErrorCode=KDF2003','팝업','width=1000,height=520,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,left=150,top=100');
	}
	else if(nError == '30002') // Debug Tool 탐지, 창 닫기
	{
		alert("Debugging Tool이 탐지되었습니다. \n확인을 누르면 창이 닫히게 됩니다.");
		window.close();
	}
	else
	{
		alert(nError+" : 에러가 발생하였습니다");
		window.open('http://www.kings.co.kr/k/help/KError.php?ErrorCode='+nError,'팝업','width=1000,height=520,toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,resizable=no,left=150,top=100');

		//비 정상 
		// nError에 에러 코드가 들어옴

		// 1 : kdfmgr.exe가 실행되어 있지 않은 경우
		// 2 : 일반모드인 경우
		// 3 : 보안 서비스를 할 수 없는 OS임
		// 4 : kdfinj.dll(키보드보안 모듈)이 정상적으로 로드 되지 않음
		// 5 : 타사의 키보드보안과 충돌
		// 10001 : 변조된 kdfmgr.exe가 동작중임
		// 10002 : 키보드보안 프로그램이 정상적으로 설치되어 있지 않음
		// 10003 : 키보드보안 프로그램이 비정상 종료되고 있음
		// 10004 : 키보드보안 프로그램을 업데이트 할 수 없어 정상 실행되지 않음
		// 10005 : 사용자가 권한 상승에 동의하지 않고 취소하여 키보드보안 프로그램이 실행되지 않음
		// 10006 : kdfmod가 값을 제대로 가져가지 못했을때, 대표값이 나올 수 있다는 에러 메시지 
		// 10007 : ActiveX로 전달되는 Param이 정상적이지 않은 경우 

		// 10008 : 웹 보안 모듈이 정상적으로 동작 하지 않음

		// 20001 : Kdfinj.dll 무결성에 문제
		// 20002 : Kdfmod.dll 무결성에 문제
				
		// 30001 : 정상적인 IE가 아님
		// 30002 : Debug Tool 탐지
		// 30003 : 최소 Driver 버전이 낮아서 재부팅 필요
	}
}

function fnGetKDefenseObject()
{	
	if (navigator.appName != "Microsoft Internet Explorer")
	{
		lsslmimeType = navigator.mimeTypes["application/lssl-plugin"];

		if( lsslmimeType == null || lsslmimeType == "undefined")
		{
			return null;
		}
		else 
		{
			return lsslmimeType;
		}
	}
	else {

		var objKDefense = null;
		if(document.getElementById)	objKDefense = document.getElementById("kdefense");
		else objKDefense = document.kdefense;

		try
		{
			if(objKDefense.GetOCXVersion() == "") return null;
			else return objKDefense;
		}
		catch(e)
		{
			return null;
		}
	}
}