document.onreadystatechange=function()
{
 if (document.readyState == 'complete')
 {
      if (document.all['divShowInstall2'])
        document.all['divShowInstall2'].style.visibility = 'hidden';
  }
};

var strScripts2 ="<OBJECT ID='Wec2' CLASSID='CLSID:3975BFE4-FC3A-4B94-BEF6-2BBC3A315F7B' WIDTH='100%' HEIGHT='525' CodeBase='/cab/NamoWec.cab#Version=7,0,1,22'>";
strScripts2 +="<PARAM NAME='UserLang' VALUE=kor>";
strScripts2 +="<PARAM NAME='InitFileURL' VALUE='/edit/As7Init.xml'>";
strScripts2 +="<PARAM NAME='InitFileVer' VALUE='1.0'>";
strScripts2 +="<PARAM NAME='InitFileWaitTime' VALUE='3000'>";
strScripts2 +="<PARAM NAME='InstallSourceURL' VALUE='http://help.namo.co.kr/activesquare/techlist/help/AS7_update'>";
strScripts2 +="</OBJECT>";

document.write(strScripts2);