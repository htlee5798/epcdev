/***************************************************

         c_Softphone function
         
         
   - evProc.js
     : 소프트폰에서 사용하는 Event 정의

   * init : 2014-02-25 hawon
      update : 2014-09-14 hawon
   
***************************************************/

	
/*/////////////////////////////////////////////
    
    Event method
    
/////////////////////////////////////////////*/		

	function evLogin() {
	  
    debugD("[Receive EV] evLogin");
	
	  //상태 NotReady
	  stateChange("NotReady");
	  
    //Main Info Sending....
		sendInfoToMain("Login");
		
	}
	
	function evLogout() {
	
    debugD("[Receive EV] evLogout");
    
	  //상태 NotReady
	  //stateChange("Logout");
	  
    //debug("NowStatus");
    
    //Main Info Sending....
		//sendInfoToMain("Logout");
		
	}
	
	function evNotReady() {
	  
    debugD("[Receive EV] evNotReady");
    
	  if (NowAction == "Vacate") {
	  
  	  //상태 NotReady
  	  stateChange("Vacate");
  	    
	  } else {
  	
  	  //상태 NotReady
  	  stateChange("NotReady");
    	    
      //Main Info Sending....
  		sendInfoToMain("NotReady");
  		
	  }
	
    //debug("NowStatus");
    
	}
	
	function evReady() {
	
    debugD("[Receive EV] evReady");
    
	  //상태 NotReady
	  stateChange("Ready");
	  
    //debug("NowStatus");
    
    //Main Info Sending....
		//sendInfoToMain("Ready");

/*		
		NowCallID = 0;
		ConsultationCallID = 0;
		
	  HeldCallID = 0;
	  HeldDeviceID = 0;		
*/		
	}
	
	function evAfterCallWork(no) {
		
    debugD("[Receive EV] evAfterCallWork");
    
    //debug("NowStatus");  
		
		stateChange("AfterCallWork");
		
    //Main Info Sending....
		sendInfoToMain("AfterCallWork");
		
		NowCallID = 0;
		ConsultationCallID = 0;
		
	  HeldCallID = 0;
	  HeldDeviceID = 0;		
		
	}
	
	function evServiceInitated() {
	  
    debugD("[Receive EV] evServiceInitated");
    
    debug("NowStatus");
    
    
		if (NowCallID == 0) {
		
  		stateChange("MakeRing");
  		
		} else {
		
  		stateChange("ConsultationRing");
  		
		}
		
	}
	
	
	function evDelivered(flag) {

    CustomTelNo = coverageTelNo(_otherParty);
    UEIData = _uei;
    SiteData = UEIData;
    CIData = getAPI("Ci");
    IncomQueueNo = getAPI("CalledParty");
    IncomQueueNo1 = getAPI("QueueDN");
	  CallRefID = _callRefId;

		debugD("FLAG Ci : " + getAPI("Ci") + " CalledParty : " + getAPI("CalledParty") + " QueueDN : " + getAPI("QueueDN") + " NowCallID : " + NowCallID + " HeldCallID : " + HeldCallID  + " flag : " + flag  );
										  
		if(UseCalledPartyForQueueNo) {
		
		  if (IncomQueueNo1 == "0" || IncomQueueNo1 == "" || IncomQueueNo1 == 0) IncomQueueNo1 = IncomQueueNo;
		
		}
		
		
	  switch (flag) {
	  
	    //flag == 2 (cubeES_Altering)
	    case "2" :
  	    
      		if (NowCallID == 0) {
      		
		        //debugD("cubeES_Altering + NowCallID==0");
										  
      			stateChange("Ring");
            
            //Infomation SET
            setSiteInfo("AnswerCall");
            
            //Main Info Sending....
        		sendInfoToMain("Delivered");
  		
      			//POPUP
      			windowPopup("AnswerCall");
        			
      		} else {
      			
		        //debugD("cubeES_Altering + NowCallID!=0");
										  
      		}
    		
	      break;
	      
	    //flag == 3 (cubeES_Connect)
	    case "3" :
          
          if (HeldCallID == 0) {
          
		        //debugD("cubeES_Connect + HeldCallID==0");
					  
					  // Avaya OB인 경우, CalledParty를 고객전화번호로 변경
            if (Protocol == 1) CustomTelNo = coverageTelNo(IncomQueueNo);
    
      		  stateChange("MakeRing");
      		  
            //Infomation SET
            setSiteInfo("MakeCall");
            
            //Main Info Sending....
        		sendInfoToMain("Delivered");
        			  
          } else {
          
		        //debugD("cubeES_Connect + HeldCallID!=0");
										  
      		  //stateChange("ConsultationRing");
          
            checkARSMessage("evDelivered");
			
          }

	      break;
	      
	    default :
	    
		        //debugD("default");
										  
	      break;
	  
	  }

    setInputBox();
		
    debug("NowStatus");
    
	}
	
	function evEstablished() {
		
    debug("NowStatus");
		
		if (NowCallID == 0) {
		
			NowCallID = _callRefId;
			
  		stateChange("OnLine");
    		
      //Main Info Sending....
  		sendInfoToMain("Established");
			
  		  //#mart
		  //saveFlagChange(false);
  			saveFlagChange(true);
			
		} else {

		       if (ConsultationARSYN == "Y") {

			} else {

			ConsultationCallID = _callRefId;
			
			stateChange("Consultation");
			
			variableSet("Consultation");

			}
		}

	}
	
	function evHeld() {
	
    debug("NowStatus");

		stateChange("Hold");
		
  	HeldCallID = _heldCallId;
  	HeldDeviceID = _heldDevice;						

	}
	
	function evRetrieved() {
	
    debug("NowStatus");
    
    if (getAPI("CallRefID") != _callRefId) {
		
			_callRefId = getAPI("CallRefID");
			debugD("FLAG != _callRefId : _callRefId <= APIEvt.CallRefID");
			
		}

		stateChange("OnLine");
		
	  HeldCallID = 0;
	  HeldDeviceID = 0;		

	}
	
	function evConferenced() {

    debug("NowStatus");
						
		if (getAPI("CallRefID") != _callRefId) {
		
			_callRefId = getAPI("CallRefID");
			debugD("FLAG != _callRefId : _callRefId <= APIEvt.CallRefID");
			
		}

		stateChange("Conference");
		
	  HeldCallID = 0;
	  HeldDeviceID = 0;
	  IsConferenced = true;
		
    checkARSMessage("evConferenced");
    
	}
	
	
	function evTransfered() {
	  
    //Main Info Sending....
		sendInfoToMain("Transfered");
			
    debug("NowStatus");

    if (afterCallWork()) {
    
      waitEvent("AfterCallWork");
    
    }
    
	}
	
	function evConnectionCleared(type, flag) {
	
    debugD("ConnectionCleared - " + type + " : Held FLAG " + flag + " NowStatus : " + NowStatus);
    
  	CCAnalysis(type, flag);

	}
		
	function evCCSEDown() {
	
	
    usrAlert(ErrMsgServerError + "\n" + InfoMsgAdminRequest);
    
    downProc();
		
	}
	
	function evFailed() {
	  
    debug("[Receive EV]evFailed");
    
    //usrAlert(ErrMsgServerError);
		
	}
	