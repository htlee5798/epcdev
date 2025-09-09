document.onreadystatechange=function()
{
 if (document.readyState == 'complete')
 {
      if (document.all['divShowInstall'])
        document.all['divShowInstall'].style.visibility = 'hidden';
  }
};

var strScripts ="<OBJECT ID='Wec' CLASSID='CLSID:3975BFE4-FC3A-4B94-BEF6-2BBC3A315F7B' WIDTH='680' HEIGHT='400' CodeBase='/cab/NamoWec.cab#Version=7,0,1,22'>";
strScripts +="<PARAM NAME='UserLang' VALUE=kor>";
strScripts +="<PARAM NAME='InitFileURL' VALUE='/edit/As7Init.xml'>";
strScripts +="<PARAM NAME='InitFileVer' VALUE='1.0'>";
strScripts +="<PARAM NAME='InitFileWaitTime' VALUE='3000'>";
strScripts +="<PARAM NAME='InstallSourceURL' VALUE='http://help.namo.co.kr/activesquare/techlist/help/AS7_update'>";
strScripts +="</OBJECT>";

document.write(strScripts);