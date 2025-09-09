/***************************************************

         Site function


   - siteUtil.js

     : 개별 함수 설정


     : 테스트를 위한 임시 차단 반드시 적용전 처리 할 것.

         1. setUser   : softphone.jsp   >> softphoneInitiate
         2. ContactEnd 처리  : urlCall >> ContactEnd


   * init : 2014-03-15 hawon
      update : 2014-09-14 hawon

***************************************************/

/*/////////////////////////////////////////////

   Constants

/////////////////////////////////////////////*/

  //ARS Input Service Code Infomation
  var InputServiceCodeList = [["00000","없음"]
                                        ,["00001","일반01"]
                                        ,["00002","일반02"]
                                        ,["00011","일반11"]
                                        ,["00012","일반12"]
                                        ,["00013","일반13"]];

  var DefaultSvcCode = "00001";
  var NothingSvcCode = "00000";
  var NothingCustCode = "0000";
  var NothingQueueNo = "0000";

/*/////////////////////////////////////////////

   Function

/////////////////////////////////////////////*/


  function defaultUserSet() {

    alert("Default User SET!!");

    //softphoneInitiate('0511001','하원','101','51056','10.151.162.100','1509');


    AgentAppID = '56052';
    AgentName = '이혜임';
    AgentGroup = '900';
    AgentParty = '902';
    AgentID = '56052';
    AgentAppIP = '192.168.106.23';
    AgentDN = '1509';

    //TextBox SET
    basicStatusSet();

  }


//백화점 사용자 정보 GET 함수
/*  function getUserInfo() {

    if(!TestMode) {

      //setUserInfo('<%=user.getUserid()%>','<%=user.getUsername()%>', "", '<%=user.getPart_id()%>','<%=user.getCti_id()%>','<%=user.getIp_addr()%>','<%=user.getInterphone()%>');

		  if (!inputDN()) return false;

      //TextBox SET
      basicStatusSet();

    } else {

      defaultUserSet();

    }

  }
*/


//시네마 사용자 정보 GET 함수
  function getUserInfo() {

	//test
//	  alert("SS_siteUtil.js - getUserInfo called");

    if(!TestMode) {

    	//#mart
        AgentAppID 	= parent.document.form1.ctiId.value;	// TAD_CTI_USR - CTI_ID
        AgentName	= parent.document.form1.adminNm.value;	// TAD_ADMIN - ADMIN_NM
        AgentGroup 	= parent.document.form1.tskGroup.value; // TAD_CTI_USR - TSK_GROUP
        AgentParty 	= parent.document.form1.teamCd.value;	// TAD_CTI_USR - TEAM_CD
        AgentID 	= parent.document.form1.ctiId.value;	// TAD_CTI_USR - CTI_ID
        AgentAppIP 	= parent.document.form1.ipAddr.value;	// application
        AgentDN	 	= parent.document.form1.extNo.value;	// TAD_CTI - EXT_NO (IP 로 조회)

        //test
//        alert(   "AgentAppID	:"+AgentAppID	+"\n"
//      		  +"AgentName	:"+AgentName	+"\n"
//      		  +"AgentGroup	:"+AgentGroup	+"\n"
//      		  +"AgentParty	:"+AgentParty	+"\n"
//      		  +"AgentID		:"+AgentID		+"\n"
//      		  +"AgentAppIP	:"+AgentAppIP	+"\n"
//      		  +"AgentDN		:"+AgentDN		+"\n"
//        );

//    	  AgentAppID 	= '56052';
//        AgentName 	= '이혜임';
//        AgentGroup 	= '900';
//        AgentParty 	= '902';
//        AgentID 		= '56052';
//        AgentAppIP 	= '192.168.106.23';
//        AgentDN 		= '1509';

//      if (!inputDN()) return false;

      //TextBox SET
      basicStatusSet();

    } else {

      defaultUserSet();

    }

  }


  function setUEIData(str) {

    switch(str) {

      case "0" :

        UEIData = NothingSvcCode + "/" + getInputServieName(NothingSvcCode) + "/" + NothingCustCode + "/" + "없음";

        break;

      case "-" :

        UEIData = "-/-/-/-";

        break;

      default :

        UEIData = NothingSvcCode + "/" + getInputServieName(NothingSvcCode) + "/" + NothingCustCode + "/" + "없음";

        break;

    }


  }

  function doConsultationCallForBrnCd(brnCD, telNo) {


    if( !consultationCall(getBranchDivNo(brnCD) + telNo) ) return false;

    return true;

  }


  function getBranchCode(vdnNo) {

    var code = "";

    for (var i = 0; i < BranchVDNCodeList.length; i++) {

      if(BranchVDNCodeList[i][2] == vdnNo) code = BranchVDNCodeList[i][0];

    }

    return code;

  }

  function getBranchName(vdnNo) {

    var name = "";

    for (var i = 0; i < BranchVDNCodeList.length; i++) {

      if(BranchVDNCodeList[i][2] == vdnNo) name = BranchVDNCodeList[i][1];

    }

    return name;

  }


  function getBranchDivNo(brnID) {

    var divNo = "";
    var idx = 0;

    if (brnID.substring(0,2) != "01") idx = 2;

    for (var i = 0; i < BranchVDNCodeList.length; i++) {

      if(BranchVDNCodeList[i][idx] == brnID) divNo = BranchVDNCodeList[i][3];

    }

    return divNo;

  }


  function getARSServiceDN(cd) {

    var dn = "";

    for (var i = 0; i < ARSServiceCodeList.length; i++) {

      if(ARSServiceCodeList[i][0] == cd) dn = ARSServiceCodeList[i][1];

    }

    return dn;

  }


  function getARSServiceNM(cd) {

    var nm = "";

    for (var i = 0; i < ARSServiceCodeList.length; i++) {

      if(ARSServiceCodeList[i][0] == cd) nm = ARSServiceCodeList[i][2];

    }

    return nm;

  }


  function setSiteInfo(type) {

      //alert("1)"+ UEIData + "/" + SiteData);

      ConnectType = type;

      switch(type) {

      case "AnswerCall" :


        if( trim(UEIData).length == 0 ) setUEIData("0");

        //ARS서비스코드/ARS서비스명/상영관코드(고객번호)/상영관명/인입VDN명/CallRefID/인입VDN/-
        //00000/상담원연결/0000/00관/없음/268438437/0000/-

        //alert("2)" + UEIData);
        var splitData = UEIData.split("/");

        var CustNo = splitData[0]; //고객번호
        var CustCode = splitData[1]; //ARS서비스코드
        var CustCodeNM = splitData[2]; // ARS서비스명

        alert("ARS 서비스명 : "+CustCodeNM);

        /*
        CustType = splitData[0];
        CustTypeNM = splitData[1];
        CustNo = splitData[2];
        CustNoNM = splitData[3];*/

        //UEI로 들어오는 데이터 중 협의로 들어오는 데이터 처리
        //if (CustNo != NothingCustCode) CustomTelNo = CustNo;

        if (getInputVdnName(IncomQueueNo1) == "") IncomQueueNo1 = NothingQueueNo;

        SiteData = UEIData + "/"+ getInputVdnName(IncomQueueNo1) + "/" + CallRefID + "/" + IncomQueueNo1 + "/" + "-";

/* Branch Code 사용의 경우

        if (getBranchName(IncomQueueNo1) == "") IncomQueueNo1 = NothingQueueNo;

        SiteData = UEIData + "/"+ getBranchName(IncomQueueNo1) + "/" + CallRefID + "/" + IncomQueueNo1 + "/" + "-";
*/

        break;

      case "MakeCall" :

  		  //MakeCall UEIData SET
  		  setUEIData("0");
  		  SiteData = UEIData;

        break;

      default :

        setUEIData("0");
  		  SiteData = UEIData;

        break;

      }

      //alert("3)"+UEIData + "::::" + SiteData);

  }

  function getInputVdnName(vdnNo) {

    var name = "";

    for (var i = 0; i < InputVDNCodeList.length; i++) {

      if(InputVDNCodeList[i][0] == vdnNo) name = InputVDNCodeList[i][1];

    }

    return name;

  }

  function getInputVdnCode(vdnNo) {

    var code = "";

    for (var i = 0; i < InputVDNCodeList.length; i++) {

      if(InputVDNCodeList[i][0] == vdnNo) code = InputVDNCodeList[i][2];

    }

    return code;

  }


  function getInputServieName(svcCd) {

    var name = "";

    for (var i = 0; i < InputServiceCodeList.length; i++) {

      if(InputServiceCodeList[i][0] == svcCd) name = InputServiceCodeList[i][1];

    }

    return name;

  }

/*/////////////////////////////////////////////

   Main Windows Function Call

/////////////////////////////////////////////*/

  function sendInfoToMain(stat) {

    //alert(2);

		var sendMsg = "";

    switch (stat) {

      case "Delivered" :

        if(ConnectType == "AnswerCall") {

           urlCall(stat, "IN");

        } else {

           urlCall(stat, "OUT");

        }
  /*
        //parent.CTI_Ring(svcCd, TelNo, vdnNo);
        if(!TestMode) parent.CTI_Ring(CustType, CustTypeNM, CustomTelNo, IncomQueueNo1, CallRefID, AgentDN, CustNo, CustNoNM);
  */
        sendMsg = "TestMode (" + TestMode + ")" + ": "+ stat + "(" + CustType + ", " + CustTypeNM + ", " + CustomTelNo + ", " + IncomQueueNo1 + ", " + CallRefID + "," + AgentDN + "," + CustNo + "," + CustNoNM + ")";

        break;

      case "Established" :

        if(ConnectType == "AnswerCall") {

           urlCall(stat, "IN");

        } else {

           urlCall(stat, "OUT");
        }

  /*
        if(!TestMode) parent.CTI_Answer();
  */
        sendMsg = "TestMode (" + TestMode + ")" + ": "+ stat + "(" + stat + ")";

        break;

      case "ARSReceive" :

        urlCall(stat, ARSReceiveData);
  /*
        if(!TestMode) parent.ARS_setData(ARSReceiveData);
  */
        sendMsg = "TestMode (" + TestMode + ")" + ": "+ stat + "(" + ARSReceiveData + ")";

        break;

//      case "Ready" :
//
//        if(!TestMode) parent.CTI_Wait("true");
//
//        sendMsg = "TestMode (" + TestMode + ")" + ": parent.CTI_Wait('true')";
//
//        break;

//      case "Ready-Fail" :
//
//        if(!TestMode) parent.CTI_Wait("false");
//
//        sendMsg = "TestMode (" + TestMode + ")" + ": parent.CTI_Wait('false')";
//
//        break;

      case "ConnectionCleared" :
  /*
        if(!TestMode) parent.CTI_ClearConnection();
  */
        urlCall(stat, "");

        sendMsg = "TestMode (" + TestMode + ")" + ": "+ stat + "(" + stat + ")";

        break;

//      case "Transfered" :
//
//        if(!TestMode) parent.CTI_ClearConnection();
//
//        sendMsg = "TestMode (" + TestMode + ")" + ": parent.CTI_ClearConnection(" + stat + ")";
//
//        break;

//      case "Login" :
//
//        if(!TestMode) parent.sLineno = AgentDN;
//
//        if(!TestMode) parent.CTI_LoginOn();
//
//        sendMsg = "TestMode (" + TestMode + ")" + ": parent.CTI_LoginOn(" + stat + ")";
//
//        break;

//      case "Logout" :
//
//        if(!TestMode) parent.CTI_LoginOff();
//
//        sendMsg = "TestMode (" + TestMode + ")" + ": parent.CTI_LoginOff(" + stat + ")";
//
//        break;

//      case "NotReady" :
//
//        if(!TestMode) parent.CTI_WaitCancel();
//
//        sendMsg = "TestMode (" + TestMode + ")" + ": parent.CTI_WaitCancel(" + stat + ")";
//
//        break;

      default :

        urlCall(stat, "");

        sendMsg = "TestMode (" + TestMode + ")" + ": " + stat + "";

        break;

    }

    debugD(sendMsg);

  }


/*/////////////////////////////////////////////

   외부 URL Call

/////////////////////////////////////////////*/

  function urlCall(stat, str) {

		  var sendMsg = "";
		  var onfocus = "yes";

		  var paramStr = "";
		//#mart
		  var url = "/cc/common/softPhoneCTIParameter.do?";
		  //var url = APPURL + "/common/softPhoneCTIParameter.do?";
//	      var url = '<c:url value="/common/softPhoneCTIParameter.do"/>?';

      var startDateTime = "";

      //test
//      alert("function urlCall, stat:"+stat+" str:"+str);
      //AgentDN
      //CallRefID
      //CustomTelNo
      //ARSReceiveData

			/////////////////////////////////////////////*/

      // 마트 정보 전달
			//  http://limadmin.lottemart.com/cc/common/softPhoneCTIParameter.do?callRefId=&telNO=&callStartTime=&status=&agentDN=&password=
			//  URI : http://limadmin.lottemart.com/cc
			//  /common/softPhoneCTIParameter.do?callRefId=&telNO=&callStartTime=&status=&agentDN=&password=

			// 패스워드 전달시 status=password, password=값
			// 고객정보 전달시 status=콜인입

      //CallRefID
      //CustomTelNo
      //AgentDN

      //ActionShowStatusInfo status="Login", "Logout", "Ready","NotReady","AfterCallWork","CallCleared","Call_Delivered(IN)","Call_ESTABLISHED(IN)","CONNECTIONCLEARED"
      //ActionShowStatusInfoOut status= "통화연결(OUT)", "OB_ESTABLISHED"

      //ActionShowCustomerInfo

      //ARS Message : ActionShowPasswordInfo &status=password&agentDN=&password=password


    switch (stat) {

      //Delivered
      case "Delivered" :

        if (str == "IN") {

          paramStr = "callRefId="+CallRefID+"&telNO="+CustomTelNo+"&callStartTime=&status=" + "Call_Delivered(IN)" + "&agentDN=" + AgentDN + "&password=";

        } else {

          paramStr = "callRefId="+CallRefID+"&telNO="+CustomTelNo+"&callStartTime=&status=" + "통화연결(OUT)" + "&agentDN=" + AgentDN + "&password=";

        }

        break;

      //Established
      case "Established" :

        startDateTime = nowDateTime();

        if (str == "IN") {

        	//test
//        	CustomTelNo = "01048553768";
          paramStr = "callRefId="+CallRefID+"&telNO="+CustomTelNo+"&callStartTime=" + startDateTime + "&status=" + "Call_ESTABLISHED(IN)" + "&agentDN=" + AgentDN + "&password=";

        } else {

          paramStr = "callRefId="+CallRefID+"&telNO="+CustomTelNo+"&callStartTime=" + startDateTime + "&status=" + "OB_ESTABLISHED" + "&agentDN=" + AgentDN + "&password=";

        }

        break;

      case "ARSReceive" :

    	  //test
//    	  alert("SS_siteUtil.js - urlCall() called stat:" + stat + " str:"+str +"\n URL:" + url);
//    	  debugD("SS_siteUtil.js - urlCall() called stat:" + stat + " str:"+str +"\n URL:" + url);
    	  //#mart
    	  //serviceCode : 01 = ARS 비번 VDN 3961
    	  //serviceCode : 02 = ARS 생년월일 VDN 3962

    	  //test
//    	  str = "11";
    	  if(document.all.cubeUei.value == "01"){
    		  paramStr = "callRefId="+CallRefID+"&telNO="+CustomTelNo+"&callStartTime=&status=" + "password" + "&agentDN=" + AgentDN + "&password=" + str;
    		  onfocus = "no";
    	  }
    	  else if(document.all.cubeUei.value == "02"){
    		  paramStr = "callRefId="+CallRefID+"&telNO="+CustomTelNo+"&callStartTime=&status=" + "idno" + "&agentDN=" + AgentDN + "&idno=" + str;
    		  onfocus = "no";
    	  }
    	  else{
    	  }

        break;

      case "ConnectionCleared" :

    	  paramStr = "callRefId="+CallRefID+"&telNO="+CustomTelNo+"&callStartTime=&status=" + "CONNECTIONCLEARED" + "&agentDN=" + AgentDN + "&password=";
    	  onfocus = "no";

    	  break;

      case "Error" :

        break;

      default :

        //paramStr = "callRefId="+CallRefID+"&telNO="+CustomTelNo+"&callStartTime=&status=" + stat + "&agentDN=" + AgentDN + "&password=";
    	  paramStr = "";

        break;

    }

    url = url + paramStr;

    //test
//    alert("SS_siteUtil.js - urlCall() URL:"+url);
//    alert("TestMode:"+TestMode);

  	if(!TestMode){
  		if(paramStr != ""){
  			popUpWin(url, "proc", 0,0,0,0, onfocus);
  		}
  	}

    debugD(url);

  }


	function displayStatus(stat) {

    //alert(stat+"/"+getStatusName(stat));
	  document.all.StatusDisplay.value = getStatusName(stat);

////////////////////////////////////////////////////

	  if(!TestMode) parent.LogDsp(getStatusName(stat));

////////////////////////////////////////////////////

	}



  function saveHistory() {

    debugD("Contact History Save....");


    //SITE SAVE 소스 코딩 추가


  }




