/***************************************************

         Softphone function
         
         
   - softphone.js
     : 소프트폰 동작 함수, 변수, 상수 정의

   * init : 2014-02-25 hawon
      update : 2014-09-14 hawon
   
***************************************************/


/*/////////////////////////////////////////////
    
    Variable / Constants
    
/////////////////////////////////////////////*/


  
  var MaxAgentCount = 10;
  
  //CTI Work mode 상수
  var usr_AUX_WORK = 1;
  var usr_AFTCAL_WK = 2;
  var usr_AUTO_IN = 3;
  var usr_MANUAL_IN = 4;


	/******************************************************************
	Variable Name: 
	Parameter  : 
	Return Value :
	Description : 
	typedef enum ctmpWorkMode_Def {
        ctmp_AUX_WORK = 1,
        ctmp_AFTCAL_WK = 2,
        ctmp_AUTO_IN = 3,
        ctmp_MANUAL_IN = 4
} ctmpWorkMode_Def;
	
	******************************************************************/

/*
[휴식 사유 코드]
(0, '일반휴식');
(1, '개인');
(2, '식사');
(3, '티타임');
(4, '교육');
(5, '회의');
(6, '상담');
(7, '다른업무');
(8, '개인업무');
(9, '수리');
*/
  
  
  //콜 관련 변수
    		  
  //StatusList
  var StatusList = [["로그인", "Login"],["로그아웃","Logout"],["대기","Ready"],["대기해제","NotReady"],["후처리","AfterCallWork"]
                        ,["신호음","Ring"],["걸기신호음","MakeRing"],["통화중","OnLine"],["협의중","Consultation"]
                        ,["협의신호음","ConsultationRing"],["호전환","Transfer"],["회의통화","Conference"],["보류","Hold"],["이석","Vacate"]
                        ,["통화종료","ConnectionCleared"],["오류","Error"],["벨","Delivered"]];
                        
  var IsSave = true;

  var ReasonCode = 1;  //휴식 사유 코드
    
  var CustomTelNo = "";
  var ConsultationTelNo = "";
  var arrConsultationTelNo = new Array ("0","0");
  var DialTelNo = "";
  var arrDialTelNo = new Array ("0","0","0","0");
  var TransferQueueNo = "";
  var UEIData = "";
  var UUIData = "";
  var CIData = "";
  var IncomQueueNo = "";
  var IncomQueueNo1 = "";
  var ConnectType = "";

	var NowStatus = "";
	var BeforStatus = "";
	var NowAction = "";
	var BeforAction = "";
	var CCEvent = 0;
	var CallRefID = 0;
	var NowCallID = 0;
	var ConsultationCallID = 0;
	var HeldCallID = 0;
	var HeldDeviceID = 0;

  var ReadyAgentList;
  var AgentCount = 0;

  var ConsultationDN = "";
  var ConsultationYN = "";
  var IsConferenced = false;
  
  //Site Data Setup
  var SiteData = "";
  var CustType = "";
  var CustTypeNM = "";
  var CustNo = "";
  var CustNoNM = "";
  
  var PopupObject = null;
  
  
  
/*/////////////////////////////////////////////
    
    Initialize
    
/////////////////////////////////////////////*/
	
	function initVar() {
		
//		NowStatus = "";
//		BeforStatus = "";
		NowAction = "";
		BeforAction = "";
		CCEvent = 0;
		CallRefID = 0;
		NowCallID = 0;
		ConsultationCallID = 0;
		
    ReasonCode = 0;    
    
    CustomTelNo = "";
    ConsultationTelNo = "";
    
    for (var i = 0; i < arrConsultationTelNo.length; i++) arrConsultationTelNo[i] = "0";
    
    DialTelNo = "";
    
    for (var i = 0; i < arrDialTelNo.length; i++) arrDialTelNo[i] = "0";
    
    TransferQueueNo = "";
    
    UEIData = "";
    SiteData = "";
    UUIData = "";
    CIData = "";		
    IncomQueueNo = "";
    IncomQueueNo1 = "";
    ConnectType = "";
    
    ReadyAgentList = null;
    
    ConsultationDN = "";
    ConsultationYN = "N";
    IsConferenced = false;
    
    CustType = "";
    CustNo = "";
    
    initInputBox();
    
	}
	
	function saveFlagChange(val) {
	
	  IsSave = val;
	
	}
	
  function initInputBox() {
  
    document.all.cubeCi.value = "";
    document.all.cubeUui.value = "";
    document.all.cubeUei.value = "";
    document.all.customTelNo.value = "";
    document.all.dialTelNo.value = "";    
    document.all.transferQueueNo.value = "";    
    document.all.cubePhoneNo.value = "";
    
  }
  
  function setInputBox() {
  
    document.all.cubeCi.value = CIData;
    document.all.cubeUui.value = UUIData;
    document.all.cubeUei.value = SiteData;
    document.all.customTelNo.value = CustomTelNo;    
    document.all.cubePhoneNo.value = ConsultationTelNo;
    document.all.dialTelNo.value = DialTelNo;    
    document.all.transferQueueNo.value = TransferQueueNo;    
      
  }
  
  function basicStatusSet() {
  
   	document.all.cubeDn.value = AgentDN;
  	document.all.cubeId.value = AgentID;
  	document.all.cubeAgentParty.value = AgentParty;
  	document.all.cubeAgentGroup.value = AgentGroup;

		buttonAction("Login", "Login_ON");
		
		NowStatus = "Logout";
  
  }
  
  function inputDN() {
  
    var dn = "";
    
    if (AgentDN == "") dn = prompt(InputMsgDN, "ex)1701");
    
    if (dn == null) {
    
      alert(ErrMsgInputDN + "\n" + InfoMsgCancelLogin);
      return false;
    
    } else {
    
      AgentDN = dn;
      document.all.cubeDn.value = dn;
      
      return true;
    
    }
    
  }

/*/////////////////////////////////////////////
    
    Extra method
    
/////////////////////////////////////////////*/	

  function doActionFunc(type) {
  
    switch (type) {
    
      case "Consultation" :
            
          if (consultation()) {
          
            debug("Consultation");
            //waitEvent("ServiceInitated");
            
      			stateChange("ConsultationRing");
      			
          } else {
          
      			return false;
      			
          }
          
        break;
        
      case "SingleStepTransfer" :
            
        if (singleStepTransfer()) {
        
          debug("SingleStepTransfer");
          
          waitEvent("Transfer");
          
        } else {
        
          return false;
          
        }
      
        break;
        
      case "MakeCall" :
            
        if (makeCall()) {
        
          debug("MakeCall");
          
          waitEvent("MakeCall");
    			
    			//stateChange("MakeCall");
          
        } else {
        
          return false;
          
        }
       
        break;
        
      case "Vacate" :
                
    		if(vacate()) {
    		  
    			debug("Vacate");
    			
    		  waitEvent("Vacate");
    		  
    			//stateChange("Vacate");
    			
    		} else {
    		
          return false;
          
    		}
    		
        break;
        
      case "Answer" :
            
      		if(answer()) {
      		
      			debug("Answer");
      
        		waitEvent("Established");
        			
      			//stateChange("OnLine");
      			
      		}  else {
      		
      		  return false;
      		  
      		}
      		
        		
        break;
    
      case "NotReady" :
        
    		if(notReady()) {
  			
          debug("NotReady");
          
          waitEvent("NotReady");
          
    			//stateChange("NotReady");
    			
    		} else {
    		
    		  return false;
    		  
    		}
    		
        break;
        
    }

		return true;
		
  }




/*/////////////////////////////////////////////
    
    Basic method
    
/////////////////////////////////////////////*/	


  //로그인 정보를 확인, 정보가 없는 경우 설정하는 함수
  function loginInfoCheck() {
    		
	  //test
//	  alert("SC_softphone.js - loginInfoCheck called");

	  if (AgentID.length == 0 || AgentDN.length == 0 || AgentGroup.length == 0 || AgentParty.length == 0) {
	    
	    getUserInfo();
	  }
	        		
	    //if (AgentID.length == 0 || AgentDN.length == 0 || AgentGroup.length == 0 || AgentParty.length == 0) {	
  	    //defaultUserSet();
	    //}     
  
  }
  

  //메인화면에서 로그인 정보를 설정한 후 호출하는 함수
	function autoLogin() {
    
    //alert("autoLogin");
    
		if (!inputDN()) return false;
		
		if(login()) {
		
			debug("Login");
			
			waitEvent("Login");
			
			//stateChange("NotReady");
			
		} else {
		
		  alert(ErrMsgError + " : Login()");
		  
		  return false;
		  
		}
		
		return true;
  }
  
  function waitEvent(stat) {
  
    switch (stat) {
/*    
      case "Login" :
        
        offAllButton();
        
        break;
      
      case "Logout" :
        
        offAllButton();
        
        break;
      
      case "Ready" :
        
        offAllButton();
        
        break;
      
      case "NotReady" :
        
        offAllButton();
        
        break;
      
      case "AfterCallWork" :
        
        offAllButton();
        
        break;
      
      case "MakeCall" :
        
        offAllButton();
        
        break;
      
      case "Established" :
        
        offAllButton();
        
        break;
      
      case "Reconnect" :
        
        offAllButton();
        
        break;
      
      case "Conference" :
        
        offAllButton();
        
        break;
      
      case "Transfer" :
        
        offAllButton();
        
        break;
      
      case "Hold" :
        
        offAllButton();
        
        break;
      
      case "Retrieve" :
        
        offAllButton();
        
        break;
*/      
      case "Vacate" :
        
        NowAction = stat;
        
        //offAllButton();
        
        break;
      
      case "VacateOut" :
        
        NowAction = "";
        
        offAllButton();
        
        break;
      
      default :
      
        offAllButton();
        
        break;
    
    }
    
    debugD("Waiting... (" + stat + ") Event");
        
  }
  
	function login() {
	  //alert(4);
	  
	//test
//	alert("SC_softphone.js - login called");
	  loginInfoCheck();
	  
	  if(DualCTI) {
	  
  		if(callCubeMethod("OpenServerHA") == 1) {
  			

  			if(callCubeMethod("MonitorStart") == 1) {
  			

  				if(callCubeMethod("LogOn") == 1 ) {
  										
  					//alert("SoftPhone Start");
  					
  					return true;
  				}
  				
  			}
  			
  		}
	  
	  } else {
	  
  	  if(callCubeMethod("OpenServer") == 1) {
  			
  			if(callCubeMethod("MonitorStart") == 1) {
  			
  				if(callCubeMethod("LogOn") == 1 ) {
  										
  					//alert("SoftPhone Start");
  					
  					return true;
  				}
  				
  			}
  			
  		}
  		
	  }
	  		
		return false;
		
	}
	
	function downProc() {
	
	  callCubeMethod("LogOff");
	  callCubeMethod("MonitorStop");
	  
	  if(DualCTI) {
	  
  	  callCubeMethod("CloseServerHA");
	  
	  } else {
	  
  	  callCubeMethod("CloseServer");
	  }
	  
	  //initVar();
	  basicStatusSet();
	  
	}
	
	function logout() {
    
		if(callCubeMethod("LogOff") == 1) {
			
			if(callCubeMethod("MonitorStop") == 1) {
			
	      if(DualCTI) {
	  
  				if(callCubeMethod("CloseServerHA") == 1 ) {
  										
  					debugD("SoftPhone Logout END");
  					
  					sendInfoToMain("Logout");
  					
  					return true;
				  }
				  
				} else {
					  
  				if(callCubeMethod("CloseServer") == 1 ) {
  										
  					//alert("SoftPhone END");
  					
  					sendInfoToMain("Logout");
  					
  					return true;
				  }
				  
				}
				
			}
			
		}
		
		return false;
	}
	
	
	function ready() {
    
    //alert(6);
		if(callCubeMethod("Ready") == 1) {
  						
      variableSet("Ready");
      
      //alert(1);
      sendInfoToMain("Ready");
      
			//alert("Ready");
			return true;
			
		} 
		
		
		debugD("**********[READY FAIL]");
		
    //alert(1);
    sendInfoToMain("Ready-Fail");
      
		return false;
	
	}
		
	function notReady() {
		
		if(callCubeMethod("NotReady") == 1) {

			//alert("NotReady");
			return true;		
		}
		
		return false;
	}
		
	function afterCallWork() {
	
		if(callCubeMethod("AfterCallWork") == 1) {
		  
			return true;
		}
		
		return false;
	}
	
	
	function makeCall() {

	  if(NowStatus == "OnLine") return true;
	  
		if(callCubeMethod("MakeCall") == 1) {
		
			return true;
		}
		
		return false;
	}
	
	
	function answer() {
	  
	  if(NowStatus == "OnLine") return true;
	  
		if(callCubeMethod("AnswerCall") == 1) {
		
			return true;
		}
		
		return false;
	}
	
	function hangup() {
	
		if(callCubeMethod("ClearConnection") == 1) {
		
/*			if(callCubeMethod("AfterCallWork") == 1) {
	
				//alert("AfterCallWork");
				return true;		
			}*/
			
			return true;
		}
		
		return false;
	
	}
	
	function consultation() {

		if (callCubeMethod("Consultation") == 1) {
		
	    variableSet("ConsultationRing");
	  
			return true;
			
		}
		
		return false;
	}
	
	function transfer() {
	
		if (callCubeMethod("Transfer") == 1) {
		
			return true;
			
		}
		
		return false;
		
	}
	
  function singleStepTransfer() {
	
		if (callCubeMethod("SingleStepTransfer") == 1) {
		
			return true;
			
		}
		
		return false;
		
  }
	
	function conference() {
	
		if (callCubeMethod("Conference") == 1) {
		
			return true;
			
		}
		
		return false;
		
	}

	function reconnect() {
	
		if (callCubeMethod("ClearConnection") == 1) {
		
			return true;
			
		}
		
		return false;
		
	}
	
	function hold() {
	
		if (callCubeMethod("Hold") == 1) {
		
			return true;
		}
		
		return false;
	}
	
	function retrieve() {
	
		if (callCubeMethod("Retrieve") == 1) {
		
			return true;
		}
		
		return false;
	}

	function vacate() {
	
//	  ReasonCode = 1;

		if (callCubeMethod("AgentStatusSet") == 1) {
		
			return true;
		}
		
		return false;
	}
	
	function agentReadyGet() {
	
	  ReadyAgentList = null;	  
	  ReadyAgentList = new Array();
	  
	  agentCount = callCubeMethod("AgentReadyGet");
	  
	}
	
	function agentReadyGetEx() {
	
	  ReadyAgentList = null;	  
	  ReadyAgentList = new Array();
	  
	  agentCount = callCubeMethod("AgentReadyGetEx");
	  
	}
	
	function save() {
	
	  saveHistory();
	  
	}
	
		
	function saveCheck() {
	
			
	  if (SavePolish == true && IsSave == false) {
	  
	    usrAlert(InfoMsgNoSave);
	    
	    return false;
	    
	  } 
	  
	  return true;
	}
	
	
	function getAgentList() {
	  
	  ReadyAgentList.sort();
	  
	  document.all.cubeResult.innerText = ReadyAgentList.toString()
					                                              + "\n" + document.all.cubeResult.innerText;
					                                              
	  for (var i=0; i < ReadyAgentList.length; i++) {
	  
	    
      var splitData = ReadyAgentList[i].split("/");
    
	    document.all.cubeResult.innerText = splitData[0]
					                                              + "\n" + document.all.cubeResult.innerText;	  
	  
	  }
					                                              
	
	}

  function CCAnalysis(type, flag) {
  
    debugD("CC-ANALYSIS NOWSTATUS : " + NowStatus + " type : " + type + " flag : " + flag );
    
    
    //상담원 전화가 끊어졌을때에
    if (type == "cubeES_Null") {
      
      if (flag == 0) {
        
        //  회의통화중 처리 필요
        //  201409134 hawon 회의통화 처리 추가
        //
        if (IsConferenced) {
          
          stateChange("OnLine");
          
          IsConferenced = false;
          
        } else {
        
          //Main Info Sending....
      		sendInfoToMain("ConnectionCleared");
          
          if (afterCallWork()) {
          
            waitEvent("AfterCallWork");
          
          }
          
        }
        
      } else {

    	  /**************************************************/
    		//  (IF AVAYA == 1, LG 6.0 == 4)
  		  if (Protocol == 1) {
  		    
  		    if (!retrieve()) {
      		    
            if (afterCallWork()) {
            
              waitEvent("AfterCallWork");
            
            }
        
  		    } else {
  		    
            waitEvent("Retrieve");
          
            //stateChange("OnLine");
    		  
  		    }
  		    
  		  } else {
  		  
          stateChange("OnLine");
  		  
  		  }
        
      }    

    //상대방 전화가 끊어졌을때에
    } else {
    
    //// 고객전화가 끊어졌을 경우, CallRefID 체크 필요
    
      
      if (flag == 0) {
      
        //회의통화중 처리 필요
        if (IsConferenced) {
          
//          alert("Here 14 !!");
          if(checkARSMessage("evConnectionCleared")){

            stateChange("OnLine");
            
          } else {
            
            stateChange("OnLine");
            
          }
        

          IsConferenced = false;
          
        }
        
      } else {
        
        //회의통화중 처리 필요
        if (IsConferenced) {
          
//          alert("Here 15 !!");
          if(checkARSMessage("evConnectionCleared")) {
          
          } else {
            
            stateChange("OnLine");
            
          }
        
          IsConferenced = false;
          
        }
        
      }
      
    }
    
    debugD("CC-ANALYSIS NOWSTATUS : " + NowStatus);
    
  }
	
  function variableSet(stat) {
  
    switch(stat) {
    
			
			case "ConsultationRing" :
  			
        ConsultationDN = ConsultationTelNo;
        ConsultationYN = "N";
  			
				break;
			
			case "Consultation" :
			
        ConsultationDN = ConsultationTelNo;
        ConsultationYN = "Y";
  			
				break;
				
			case "AfterCallWork" :
			
			  break;
			  
			    
      case "Ready" :
      
    		initVar();
		
        break;
        
      case "MakeCall" :
        
    		initVar();
		
    		break;
		
      default :
      
        break;
        
    }
  
  }

