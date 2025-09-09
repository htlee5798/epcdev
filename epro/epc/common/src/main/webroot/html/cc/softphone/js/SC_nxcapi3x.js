/***************************************************

         nxcapi3x API Define function
 
   
   * init : 2014-02-25 hawon
      update : 2014-09-14 hawon
   
***************************************************/

	var iGateID = 0;
	var iInvokeID = 0;

	function callCubeMethod(func) 
	{
	
	    try {
    	   
    		// NXCAPI function 호출
    		switch (func)
    		{
    			case "OpenServer":
    				
    				// gate id->nxcapi 초기화 설정
    				NXCAPI3X.GateId = 1;
    				NXCAPI3X.LogTrace = 'TRUE';
    				NXCAPI3X.LogPath = "C:\\softphone";
    
    				// gate id 생성
    				iGateID = NXCAPI3X.GateId;
    
    			/***************************************************
    			//USER DEFINE-hawon////////////////////////////*/
    
    						// active server
    				NXCAPI3X.ServerName			= ServerActive;
    				
    				// standby server(이중화)
    				NXCAPI3X.ServerName1		= ServerStandby;
    		
    				// pbx type(0:none, 1:asai, 2:merdian, 3:csta1, 4:csta2, 5:csta3)
    				NXCAPI3X.LinkMode			= Protocol;
    		
    				// port
    				NXCAPI3X.OpenPort			= Port;
    			
    			/*//////////////////////////////////////////////////
    			***************************************************/				
    		
    				// DN
    				NXCAPI3X.OpenDevice			= document.all.cubeDn.value;
    				
    				ret = NXCAPI3X.nxcapiopenserver();
    				
    				break;
    
    			case "OpenServerHA":
    				
    				// gate id->nxcapi 초기화 설정
    				NXCAPI3X.GateId = 1;
    				NXCAPI3X.LogTrace = 'TRUE';
    				NXCAPI3X.LogPath = "C:\\softphone";
    				
    				// gate id 생성
    				iGateID = NXCAPI3X.GateId;
    				
    			/***************************************************
    			//USER DEFINE-hawon////////////////////////////*/
    
    						// active server
    				NXCAPI3X.ServerName			= ServerActive;
    				
    				// standby server(이중화)
    				NXCAPI3X.ServerName1		= ServerStandby;
    		
    				// pbx type(0:none, 1:asai, 2:merdian, 3:csta1, 4:csta2, 5:csta3)
    				NXCAPI3X.LinkMode			= Protocol;
    		
    				// port
    				NXCAPI3X.OpenPort			= Port;
    			
    			/*//////////////////////////////////////////////////
    			***************************************************/				
    		
    				// DN
    				NXCAPI3X.OpenDevice			= document.all.cubeDn.value;
    	
    				ret = NXCAPI3X.nxcapiOpenServerHAX();
            
    				break;
    
    			case "MonitorStart":
    
    				// gate id 생성
    				//iGateID = NXCAPI3X.GateId;
    				// nxcapimonitorstart(gate, invokeID);
    				ret = NXCAPI3X.nxcapimonitorstart(iGateID, iInvokeID);
    				iInvokeID++;				
    				
    				// timer start
    				if(ret==1)
    				{
    
    					NXCAPI3XEvt.opengate (iGateID);
    					document.all.cubeResult.innerText = func + " succeeded.\n" + document.all.cubeResult.innerText;
    //					NXCAPI3XEvt.setdelaytime(100);  //NXCAPI3X 5.1.2.6 버전용
    					NXCAPI3XEvt.starttimer();
    					func = "TimerStart";
    					
    				}
    
    				break;
    
    
    			case "MonitorStop":
    				
    				iGateID = NXCAPI3X.gateid;
    				
    				// nxcapimonitorstop(gate, invokeID);
    				ret = NXCAPI3X.nxcapimonitorstop(iGateID, iInvokeID);
    				iInvokeID++;
    				
    				// timer stop
    				if(ret==1)
    				{
    					document.all.cubeResult.innerText = func + " succeeded.\n" + document.all.cubeResult.innerText;
    					NXCAPI3XEvt.stoptimer();
    					func = "TimerStop";
    				}
    
    				break;
    			
    			case "CloseServer":
    				
    				iGateID = NXCAPI3X.gateid;			
    
    				// nxcapiCloseServer(gate, invokeID);
    				ret = NXCAPI3X.nxcapicloseserver(iGateID, iInvokeID);
    				iInvokeID++;
    
    				break;
    
    			case "CloseServerHA":
    				
    				iGateID = NXCAPI3X.gateid;			
    
    				// nxcapiCloseServerX(gate, invokeID);
    				ret = NXCAPI3X.nxcapiCloseServerX(iGateID, iInvokeID);
    				//iInvokeID++;
    
    				break;
    			
    			case "ClearConnection":
    
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapiclearconnection(gate, invoke, callRefId, cleardevice, pridata);
    				ret = NXCAPI3X.nxcapiclearconnection(iGateID, iInvokeID, _callRefId, _monitorParty, "");
    
    				iInvokeID++;
    
    				break;
    
    /*			case "MakeCall":
    
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapimakecall(gate, invoke, callingnumber, callednumber, uui, acccode, authcode, ueidata, cidata, pridata)
    				ret = NXCAPI3X.nxcapimakecall(iGateID, iInvokeID, document.all.cubeDn.value, document.all.cubePhoneNo.value, document.all.cubeUui.value, "", "", document.all.cubeUei.value, document.all.cubeCi.value, "");
    
    				iInvokeID++;
    
    				break;
    */
    			case "AnswerCall":
    				
    				iGateID = NXCAPI3X.gateid;
            
    				// nxcapianswercall(gate, invoke, callid, answerdevice, pridata);
    				ret = NXCAPI3X.nxcapianswercall(iGateID, iInvokeID, _callRefId, _monitorParty, "");
          
    				iInvokeID++;
            //alert(_callRefId);
    				break;
    
    			case "Hold":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapiholdcall(gate, invoke, callid, holddevice);
    				ret = NXCAPI3X.nxcapiholdcall(iGateID, iInvokeID, _callRefId, _monitorParty);
    
    				iInvokeID++;
    
    				break;
    
    			case "Retrieve":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapiretrievecall(gate, invoke, callid, retrievedevice);
    				ret = NXCAPI3X.nxcapiretrievecall(iGateID, iInvokeID, _heldCallId, _heldDevice);
    
    				iInvokeID++;
    
    				break;
    				
    			case "Reconnect":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapiretrievecall(gate, invoke, callid, retrievedevice);
    				ret = NXCAPI3X.nxcapireconnectcall(iGateID, iInvokeID, _heldCallId, _heldDevice, _activeCallId, _activeDevice);
    
    				iInvokeID++;
    
    				break;
    
    			case "ConsultationTrans":
    				
    				iGateID = NXCAPI3X.gateid;
    				// pbx type = meridian이면 _callclass 지정 할 것!
    				var _callclass = 0;
    
    				// nxcapiconsultationcall			 (gate,    invoke, 	  callid, 		 callingdevice, calleddevice, 					callclass,  uui, acccode, authcode, ueidata, cidata, pridata);
    				ret = NXCAPI3X.nxcapiconsultationcall(iGateID, iInvokeID, _activeCallId, _activeDevice, document.all.cubePhoneNo.value, _callclass,	document.all.cubeUui.value, "", "", document.all.cubeUei.value, document.all.cubeCi.value, "");
    
    				iInvokeID++;
    
    				break;
    
    			case "ConsultationConfe":
    				
    				iGateID = NXCAPI3X.gateid;
    				// pbx type = meridian이면 _callclass 지정 할 것!
    				var _callclass = 1;
    
    				// nxcapiconsultationcall			 (gate,    invoke, 	  callid, 		 callingdevice, calleddevice, 					callclass,  uui, acccode, authcode, ueidata, cidata, pridata);
    				ret = NXCAPI3X.nxcapiconsultationcall(iGateID, iInvokeID, _activeCallId, _activeDevice, document.all.cubePhoneNo.value, _callclass,	document.all.cubeUui.value, "", "", document.all.cubeUei.value, document.all.cubeCi.value, "");
    
    				iInvokeID++;
    
    				break;
    				
    				
       /*############################################################################################################################################################## 
       모드적용 위치 예시
       iProgress = CAPI3X1.ctmpsetfeatureagentstatus(CAPI3X1.GateId, 0, CAPI3X1.OpenDevice, 0 <- Agentmode, CAPI3X1.OpenAgentID, CAPI3X1.OpenGroup, CAPI3X1.OpenGroup, 0 <- ReasonCode, 0 <- WorkMode, "")
    		ctmpV_AgentLogin   					상담원 로그인 상태(0)
    		ctmpV_AgentLogout			 		  상담원 로그아웃 상태(1)
    		ctmpV_AgentNotReady					상담원 휴식 상태(2)
    		ctmpV_AgentReady		  			상담원 대기 상태(3)
    		ctmpV_AgentOtherWork				상담원 Busy 상태(4)
    		ctmpV_AgentAfterCallWork		상담원 후처리 상태(5)
    		
    		**주의 사항 **
    		;로그온 및 CC 후 작업모드(1:상태없음, 2:후처리상태, 3:레디상태, 4:Not Ready 상태)  /ASAI/
                                 (0:상태없음, 1:Not Ready 상태, 2:Ready, 3:후처리상태)     /CSTA/
    		1. AVAYA 교환기의 경우 로그인을 할때 WorkMode를 2로 셋팅 하도록 함 - workmode 2의 경우 로그인시 후처리로 표시
    		   - 대기호가 밀린 상황에서 상담원이 로그인을 하자 마자 호가 들어오는 것을 방지
       ###############################################################################################################################################################*/    
       
       
    			case "Ready":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapisetfeatureagentstatus(			   gate, 	invoke,    device, 					  agentmode, 		agentid, 				   agentdata, 						  agentgroup, 						 reasoncode,  workmode);
    				ret = NXCAPI3X.nxcapisetfeatureagentstatus(iGateID, iInvokeID, document.all.cubeDn.value, cubeV_AgentReady, document.all.cubeId.value, document.all.cubeAgentParty.value, document.all.cubeAgentGroup.value, 0, 		  0);

    				iInvokeID++;
    
    				break;
    
    			case "LogOn":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapisetfeatureagentstatus(			   gate, 	invoke,    device, 					  agentmode, 		agentid, 				   agentdata, 						  agentgroup, 						 reasoncode,  workmode);
    				
//      				ret = NXCAPI3X.nxcapisetfeatureagentstatus(iGateID, iInvokeID, document.all.cubeDn.value, cubeV_AgentLogin, document.all.cubeId.value, document.all.cubeAgentParty.value, document.all.cubeAgentGroup.value, 0, 		  1);
      		  
    			/***************************************************
    			//USER DEFINE-hawon////////////////////////////*/
    
    				//AVAYA PBX
    				if ( Protocol == 1 ) { 
    				
      				ret = NXCAPI3X.nxcapisetfeatureagentstatus(iGateID, iInvokeID, document.all.cubeDn.value, cubeV_AgentLogin, document.all.cubeId.value, document.all.cubeAgentParty.value, document.all.cubeAgentGroup.value, 0, 		  1);
      		  
      		  //LG PBX
            } else if ( Protocol == 4) {
            
      				ret = NXCAPI3X.nxcapisetfeatureagentstatus(iGateID, iInvokeID, document.all.cubeDn.value, cubeV_AgentLogin, document.all.cubeId.value, document.all.cubeAgentParty.value, document.all.cubeAgentGroup.value, 0, 		  0);
      				
            } else {
            
      				ret = NXCAPI3X.nxcapisetfeatureagentstatus(iGateID, iInvokeID, document.all.cubeDn.value, cubeV_AgentLogin, document.all.cubeId.value, document.all.cubeAgentParty.value, document.all.cubeAgentGroup.value, 0, 		  0);

            }
    			
    			/*//////////////////////////////////////////////////
    			***************************************************/				
    		
    				iInvokeID++;
    
    				break;
    
    			case "NotDeady":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapisetfeatureagentstatus(			   gate, 	invoke,    device, 					  agentmode, 		agentid, 				   agentdata, 						  agentgroup, 						 reasoncode,  workmode);
    				ret = NXCAPI3X.nxcapisetfeatureagentstatus(iGateID, iInvokeID, document.all.cubeDn.value, cubeV_AgentNotReady, document.all.cubeId.value, document.all.cubeAgentParty.value, document.all.cubeAgentGroup.value, 0, 		  0);
    
    				iInvokeID++;
    
    				break;
    
    			case "LogOff":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapisetfeatureagentstatus(			   gate, 	invoke,    device, 					  agentmode, 		agentid, 				   agentdata, 						  agentgroup, 						 reasoncode,  workmode);
    				ret = NXCAPI3X.nxcapisetfeatureagentstatus(iGateID, iInvokeID, document.all.cubeDn.value, cubeV_AgentLogout, document.all.cubeId.value, document.all.cubeAgentParty.value, document.all.cubeAgentGroup.value, 0, 		  0);
    
    				iInvokeID++;
    
    				break;
    
    			case "OtherWork":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapisetfeatureagentstatus(			   gate, 	invoke,    device, 					  agentmode, 		agentid, 				   agentdata, 						  agentgroup, 						 reasoncode,  workmode);
    				ret = NXCAPI3X.nxcapisetfeatureagentstatus(iGateID, iInvokeID, document.all.cubeDn.value, cubeV_AgentOtherWork, document.all.cubeId.value, document.all.cubeAgentParty.value, document.all.cubeAgentGroup.value, 0, 		  0);
    
    				iInvokeID++;
    
    				break;
    
    			case "AfterCallWork":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapisetfeatureagentstatus(			   gate, 	invoke,    device, 					  agentmode, 			agentid, 				   agentdata, 						  agentgroup, 						 reasoncode,  workmode);
    				ret = NXCAPI3X.nxcapisetfeatureagentstatus(iGateID, iInvokeID, document.all.cubeDn.value, cubeV_AgentAfterCallWork,  document.all.cubeId.value, document.all.cubeAgentParty.value, document.all.cubeAgentGroup.value, 0, 		  0);
    
    				iInvokeID++;
    
    				break;
    
    			case "DivertDeflect":
    
    				iGateID = NXCAPI3X.gateid;
    				//nxcapidivertdeflect(gate, invoke, callid, callingdevice, divertdevice, LPCTSTR uui, LPCTSTR ueidata, LPCTSTR cidata, LPCTSTR pridata);
    				ret = NXCAPI3X.nxcapidivertdeflect(iGateID, iInvokeID, _callRefId, _monitorParty, document.all.cubePhoneNo.value, "", "", "", "");
    
    				iInvokeID++;
    
    				break;
    /*
    			case "AgentReadyGet":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapiagentreadyget(gate, 	invoke ,   0, 0, 0);
    				ret = NXCAPI3X.nxcapiagentreadyget(iGateID, iInvokeID, 0, 0, 1);
    
    				iInvokeID++;
    
    				if(ret>=0&&ret<1000)
    					document.all.cubeResult.innerText = func + " succeeded. : count["+ret+"]\n" + document.all.cubeResult.innerText;
    
    				break;
    */			
    			//v2.6 2010.12.07 Lynn
    			case "AgentBusyGet":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				ret = NXCAPI3X.nxcapiagentbusyget(iGateID, iInvokeID, 0, 0, 1);
    
    				iInvokeID++;
    
//    				if(ret>=0&&ret<1000)
//    					document.all.cubeResult.innerText = func + " succeeded. : count["+ret+"]\n" + document.all.cubeResult.innerText;
          		  
    			/***************************************************
    			//USER DEFINE-hawon////////////////////////////*/
    
    				if(ret>=0&&ret<1000) {
    				
    					document.all.cubeResult.innerText = func + " succeeded. : count["+ret+"]\n" + document.all.cubeResult.innerText;
    
        			debugD("Action succeeded "+ func);
        				
        			return 1;
        			
    			  }          		      

    			/*//////////////////////////////////////////////////
    			***************************************************/				
    		
    				break;
    
    			case "SetFeatureSpeakerMute":
    				iGateID = NXCAPI3X.gateid;
    
    				iInvokeID++;
    
    				ret = NXCAPI3X.nxcapiSetFeatureSpeakerMuteX(iGateID, iInvokeID, document.all.cubeDn.value, 0);
    
    				break;
    			
    			//2010.11.29 Lynn 추가
    			case "onesteptransfer":
    				
    				iGateID = NXCAPI3X.gateid;
    				
    				// nxcapionesteptransfer(long gate, long invoke, long callid, LPCTSTR callingdevice, LPCTSTR calleddevice, LPCTSTR uui, LPCTSTR ueidata, LPCTSTR cidata) 
    				ret = NXCAPI3X.nxcapionesteptransfer(iGateID, iInvokeID, _callRefId, _monitorParty, document.all.cubePhoneNo.value, "", "", "");
    
    				iInvokeID++;
    
    				break;
    				
    			//2010.11.30 Lynn 추가
    			case "onestepconference":
    				
    				iGateID = NXCAPI3X.gateid;
    				
    				// nxcapionestepconference(long gate,long invoke,long callid,LPCTSTR callingdevice,LPCTSTR calleddevice,long callclass,LPCTSTR uui,LPCTSTR ueidata,LPCTSTR cidata) 
    				ret = NXCAPI3X.nxcapionestepconference(iGateID, iInvokeID, _callRefId, _monitorParty, document.all.cubePhoneNo.value, _callclass,"", "", "");
    
    				iInvokeID++;
    
    				break;
    /*				
    			//2010.12.24 Lynn 추가
    			case "SingleStepTransfer":
    			  iGateID = NXCAPI3X.gateid;
    			  
    				//nxcapisinglesteptransfer(long gate, long invoke, long callid, LPCTSTR callingdevice, LPCTSTR calleddevice, long callmode, LPCTSTR uui, LPCTSTR acccode, LPCTSTR authcode, LPCTSTR ueidata, LPCTSTR cidata)
    				ret = NXCAPI3X.nxcapisinglesteptransfer(iGateID, iInvokeID, _callRefId, _monitorParty, document.all.cubePhoneNo.value, 1, document.all.cubeUui.value, "", "", document.all.cubeUei.value, document.all.cubeCi.value);
    
    				iInvokeID++;
    
    			  break;
    */			  
    			//2010.12.24 Lynn 추가
    			case "GetSkillMaster":
    			  iGateID = NXCAPI3X.gateid;
    			
    			  //nxcapiGetSkillMasterX(long gate, short invoke, long tenantID) 
        	  ret = NXCAPI3X.nxcapiGetSkillMasterX(iGateID, iInvokeID, document.all.cubeTenant.value);
    			  
    			  iInvokeID++;
    			  
    			  if(ret>=0&&ret<1000)
    					document.all.cubeResult.innerText = func + " succeeded. : count["+ret+"]\n" + document.all.cubeResult.innerText;
    			  
    			  break;
    			  
    			//2011.01.13 Lynn 추가
    			case "AgentGetBySkill":
    			  iGateID = NXCAPI3X.gateid;
    			
    			  //nxcapiAgentGetBySkillX(long gate, short invoke, long tenantID, long skillID, long agentMode, short count)
        	  ret = NXCAPI3X.nxcapiAgentGetBySkillX(iGateID, iInvokeID, document.all.cubeTenant.value, document.all.cubeSkill.value, 0, document.all.cubeCount.value);
    			  
    			  iInvokeID++;
    			  
//    			  if(ret>=0&&ret<1000)
//    					document.all.cubeResult.innerText = func + " succeeded. : count["+ret+"]\n" + document.all.cubeResult.innerText;
    					
    			/***************************************************
    			//USER DEFINE-hawon////////////////////////////*/
    
    				if(ret>=0&&ret<1000) {
    				
    					document.all.cubeResult.innerText = func + " succeeded. : count["+ret+"]\n" + document.all.cubeResult.innerText;
    
        			debugD("Action succeeded "+ func);
        				
        			return 1;
        			
    			  }          		      

    			/*//////////////////////////////////////////////////
    			***************************************************/			
    				    			  
    			  break;
    /*			  
    			//2012.07.17 Kevin 추가
    			case "AgentStatusGet":
    				iGateID = NXCAPI3X.gateid;
    
    				//nxcapiagentStatusGet(long gate, long invoke, long mode, long maxcount, long group, long part) 		
    				ret = NXCAPI3X.nxcapiagentStatusGet(iGateID, iInvokeID, 0, 10, 100, 101);
    				
    				iInvokeID++;
    							  
    				if(ret>=0&&ret<1000)
    					document.all.cubeResult.innerText = func + " succeeded. : count["+ret+"]\n" + document.all.cubeResult.innerText;
    
    			  break;
    */			  
    		
    			//2012.07.26 Kevin 추가
    			case "ActiveGateId":
    				iGateID = NXCAPI3X.gateid;
    		
    				ret = NXCAPI3X.nxcapiactivegateIdrtn (iGateID);
    							  
    				document.all.cubeResult.innerText = func + " ActiveGateIdss : ["+ret+"]\n" + document.all.cubeResult.innerText;
    
    			  break;
    			
    			case "WaitCountGet":
    				iGateID = NXCAPI3X.gateid;
    				iInvokeID++;
    				ret = NXCAPI3X.nxcapiWaitCountGetX(iGateID, iInvokeID,1, "6001");
    							  
    				document.all.cubeResult.innerText = func + " count : ["+ret+"]\n" + document.all.cubeResult.innerText;
    			  break;
    			
    			case "MaxCPSQ":
    				iGateID = NXCAPI3X.gateid;
    				iInvokeID++;
    				//nxcapiGetMaxCPSQX(long gate, long invokeID, long ciodID, long cpid ) 
    				ret = NXCAPI3X.nxcapiGetMaxCPSQX (iGateID, iInvokeID, document.all.cubeciodid.value, document.all.cubecpid.value);
    				//ret = NXCAPI3XOB.nxcapiobsetcampaignlisthandleX(iGateID, iInvokeID, 1, 1, "", 1, "", 1);
    				document.all.cubeResult.innerText = func + " nxcapiGetMaxCPSQX : ["+ret+"]\n" + document.all.cubeResult.innerText;
    
    			  break;
    			
    			case "waitingcalltimeperiod":
    				iGateID = NXCAPI3X.gateid;
    				iInvokeID++;
    				_period = document.all.cubeCount.value
    				//nxcapiwaitingcalltimeperiod(long gate, long invoke, long interval, LPCTSTR queue) 
    				ret = NXCAPI3X.nxcapiwaitingcalltimeperiod(iGateID, iInvokeID, document.all.cubeCount.value, document.all.cubeQueue.value);
    				
    			  break;
    			
    			case "waitingcallListofque":
    				iGateID = NXCAPI3X.gateid;
    				iInvokeID++;
    				//nxcapiwaitingcallListofque(long gate, long invoke, LPCTSTR queue) 
    				ret = NXCAPI3X.nxcapiwaitingcallListofque(iGateID, iInvokeID, document.all.cubeQueue.value);
    				
    			  break;
    			  
    			case "NotifyRecord":
    				//E1 에서 사용
    				iGateID = NXCAPI3X.gateid;
    				iInvokeID++;
    				//nxcapiNotifyRecord(long gate, long invoke, long callId, long deviceDn, long mode, LPCTSTR bsCode)
    				ret = NXCAPI3X.nxcapiNotifyRecord(iGateID, iInvokeID, 111, 3003, 1, "bbbbbbbbbbbbbbbb");
    				
    			  break;
    			  
    			  
    			/***************************************************
    			//USER DEFINE-hawon////////////////////////////*/
    /*
          usr_AUX_WORK    : 0
          usr_AFTCAL_WK   : 1 After Call Work 상태.
          usr_AUTO_IN        : 2 Ready 상태.
          usr_MANUAL_IN	.: 3 Not Ready 상태.
    */
    
    			case "NotReady":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapisetfeatureagentstatus(			   gate, 	invoke,    device, 					  agentmode, 		agentid, 				   agentdata, 						  agentgroup, 						 reasoncode,  workmode);
    				ret = NXCAPI3X.nxcapisetfeatureagentstatus(iGateID, iInvokeID, document.all.cubeDn.value, cubeV_AgentNotReady, document.all.cubeId.value, document.all.cubeAgentParty.value, document.all.cubeAgentGroup.value, 0, 		  0);
    
    				iInvokeID++;
    
    				break;
    
    							  
    			case"AgentStatusSet":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapisetfeatureagentstatus(			   gate, 	invoke,    device, 					  agentmode, 		agentid, 				   agentdata, 						  agentgroup, 						 reasoncode,  workmode);
    				ret = NXCAPI3X.nxcapisetfeatureagentstatus(iGateID, iInvokeID, document.all.cubeDn.value, cubeV_AgentNotReady, document.all.cubeId.value, document.all.cubeAgentParty.value, document.all.cubeAgentGroup.value,ReasonCode,usr_AFTCAL_WK);
    
    				iInvokeID++;
    			  
    			  break;
    
    			case "MakeCall":
    
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapimakecall(gate, invoke, callingnumber, callednumber, uui, acccode, authcode, ueidata, cidata, pridata)
    				ret = NXCAPI3X.nxcapimakecall(iGateID, iInvokeID, document.all.cubeDn.value, getDialNo(document.all.dialTelNo.value), document.all.cubeUui.value, "", "", document.all.cubeUei.value, document.all.cubeCi.value, "");
    
    				iInvokeID++;
    
    				break;
    				
    
    			case "Consultation":
    				
    				iGateID = NXCAPI3X.gateid;
    				// pbx type = meridian이면 _callclass 지정 할 것!
    				var _callclass = 0;
    
    				// nxcapiconsultationcall			 (gate,    invoke, 	  callid, 		 callingdevice, calleddevice, 					callclass,  uui, acccode, authcode, ueidata, cidata, pridata);
    				ret = NXCAPI3X.nxcapiconsultationcall(iGateID, iInvokeID, _activeCallId, _activeDevice, getDialNo(document.all.cubePhoneNo.value), _callclass,	document.all.cubeUui.value, "", "", document.all.cubeUei.value, document.all.cubeCi.value, "");
    
    				iInvokeID++;
    
    				break;				
    
    			case "Transfer":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapitransfercall(gate, invoke, heldcallid, helddevice, activecallid, activedevice, pridata) 
    				ret = NXCAPI3X.nxcapitransfercall(iGateID, iInvokeID, _heldCallId, _heldDevice, _activeCallId, getDialNo(document.all.cubePhoneNo.value), "");
    
    				iInvokeID++;
    
    				break;
    
    			case "Conference":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapiconferencecall(gate, invoke, heldCallID, heldDevice, activeCallID, activeDevice, priDate) 
    				ret = NXCAPI3X.nxcapiconferencecall(iGateID, iInvokeID, _heldCallId, _heldDevice, _activeCallId, getDialNo(document.all.cubePhoneNo.value), "");
    
    				iInvokeID++;
    
    				break;
    
    			case "AgentStatusGet":
    				iGateID = NXCAPI3X.gateid;
    
            // mode 204 : ready, notReady
    				//nxcapiagentStatusGet(long gate, long invoke, long mode, long maxcount, long group, long part) 		
    				ret = NXCAPI3X.nxcapiagentStatusGet(iGateID, iInvokeID, 204, MaxAgentCount, AgentGroup, AgentParty);
    				
    				iInvokeID++;
    							  
//    				if(ret>=0&&ret<1000)
//    					document.all.cubeResult.innerText = func + " succeeded. : count["+ret+"]\n" + document.all.cubeResult.innerText;
    			/***************************************************
    			//USER DEFINE-hawon////////////////////////////*/
    
    				if(ret>=0&&ret<1000) {
    				
    					document.all.cubeResult.innerText = func + " succeeded. : count["+ret+"]\n" + document.all.cubeResult.innerText;
    
        			debugD("Action succeeded "+ func);
        				
        			return 1;
        			
    			  }          		      

    			/*//////////////////////////////////////////////////
    			***************************************************/				    
    			
    			  break;
    			  
    			case "AgentReadyGet":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				// nxcapiagentreadyget(gate, 	invoke ,   0, 0, 0);
    				ret = NXCAPI3X.nxcapiagentreadyget(iGateID, iInvokeID, AgentGroup, AgentParty, MaxAgentCount);
    
    				iInvokeID++;
    
//    				if(ret>=0&&ret<1000)
//    					document.all.cubeResult.innerText = func + " succeeded. : count["+ret+"]\n" + document.all.cubeResult.innerText;
        			/***************************************************
    			//USER DEFINE-hawon////////////////////////////*/
    
    				if(ret>=0&&ret<1000) {
    				
    					document.all.cubeResult.innerText = func + " succeeded. : count["+ret+"]\n" + document.all.cubeResult.innerText;
    
        			debugD("Action succeeded "+ func);
        				
        			return 1;
        			
    			  }          		      

    			/*//////////////////////////////////////////////////
    			***************************************************/
    							   
    				break;
    				
    			case "AgentReadyGetEx":
    				
    				iGateID = NXCAPI3X.gateid;
    
    				/*함수 원형
              nxcapiagentreadygetex(long gate, long invoke, long agentgroup, long agentpart, long maxcount, long centerid)
              
              호출 예제
              nxcapiagentreadygetex(gate, invoke, 100, 101, 10, 1);
              
              결과 이벤트
              <SCRIPT language="javascript" 
              FOR="NXCAPI3X" 
              EVENT="ctmpreadyagentgetexvalue(agentId, agentName, agentGroup, groupName, agentPart, partName, deviceDN, blendMode, continueTime)">
              
              로그(agentId, deviceDN, agentGroup, agentPart 등) 찍어보면 나옵니다.
            */
    
    				ret = NXCAPI3X.nxcapiagentreadygetex(iGateID, iInvokeID, AgentGroup, AgentParty, MaxAgentCount,1);
    
    				iInvokeID++;
    
//    				if(ret>=0&&ret<1000)
//    					document.all.cubeResult.innerText = func + " succeeded. : count["+ret+"]\n" + document.all.cubeResult.innerText;
    					
          /***************************************************
    			//USER DEFINE-hawon////////////////////////////*/
    
    				if(ret>=0&&ret<1000) {
    				
    					document.all.cubeResult.innerText = func + " succeeded. : count["+ret+"]\n" + document.all.cubeResult.innerText;
    
        			debugD("Action succeeded "+ func);
        				
        			return 1;
        			
    			  }          		      

    			/*//////////////////////////////////////////////////
    			***************************************************/
    			
    				break;
    								
    			case "SingleStepTransfer":
    			
    			  iGateID = NXCAPI3X.gateid;
    			  
    				//nxcapisinglesteptransfer(long gate, long invoke, long callid, LPCTSTR callingdevice, LPCTSTR calleddevice, long callmode, LPCTSTR uui, LPCTSTR acccode, LPCTSTR authcode, LPCTSTR ueidata, LPCTSTR cidata)
    				ret = NXCAPI3X.nxcapisinglesteptransfer(iGateID, iInvokeID, _callRefId, _monitorParty, getDialNo(document.all.transferQueueNo.value), 1, document.all.cubeUui.value, "", "", document.all.cubeUei.value, document.all.cubeCi.value);
    
    				iInvokeID++;
    
    			  break;							
    							
    			/*//////////////////////////////////////////////////
    			***************************************************/									  			  
    			  
    		}		
    
      } catch(e) {
      
        alert(ErrMsgErrCommnuication);
        
      }
    
    		// 결과 값 출력 -> document.all.cubeResult
    		if (ret==1)
    		{
    		  /***************************************************
    			//USER DEFINE-hawon////////////////////////////*/		
    			
    			//document.all.cubeResult.innerText = func + " succeeded.\n" + document.all.cubeResult.innerText;
    
    							
    			debugD("Action succeeded "+ func);
    				
    			return 1;
    			
    			/*//////////////////////////////////////////////////
    			***************************************************/			
    				
    		}
    		else
    		{
    		
      		/***************************************************
    			//USER DEFINE-hawon////////////////////////////*/
    			
    			//document.all.cubeResult.innerText = func + " failed["+ret+"].\n" + document.all.cubeResult.innerText;
    
    			debugD("Action failed "+ func);
    			
    			return 0;
    			
    			/*//////////////////////////////////////////////////
    			***************************************************/				
    		}

	}


	/******************************************************************
	Variable Name: 이벤트 선언
	Parameter  : 
	Return Value :
	Description : event kind
	******************************************************************/
	var cubeEK_CallCleared			= 1;
	var cubeEK_Confernced			= 2;
	var cubeEK_ConnectionCleared	= 3;
	var cubeEK_Delivered			= 4;
	var cubeEK_Diverted				= 5;
	var cubeEK_Established			= 6;
	var cubeEK_Failed				= 7;
	var cubeEK_Held					= 8;
	var cubeEK_NetworkReached		= 9;
	var cubeEK_Originated			= 10;
	var cubeEK_Queued				= 11;
	var cubeEK_Retrieved			= 12;
	var cubeEK_ServiceInitated		= 13;
	var cubeEK_Transferred			= 14;
	var cubeEK_Route				= 15;
	var cubeEK_RouteEnded			= 16;
	var cubeEK_DummyCall			= 17;
	var cubeEK_eDiverted			= 18;
	var cubeEK_CallInformation		= 101;
	var cubeEK_DoNotDisturb			= 102;
	var cubeEK_Forwarding			= 103;
	var cubeEK_MessageWaiting		= 104;
	var cubeEK_AutoAnswer			= 105;	 // CSTA2
	var cubeEK_MicrophoneMute		= 106;	 // CSTA2
	var cubeEK_SpeakerMute			= 107;	 // CSTA2
	var cubeEK_SpeakerVolume		= 108;	 // CSTA2 		
	var cubeEK_SystemEventIE		= 200;
	var cubeEK_LoggedOn				= 201;
	var cubeEK_LoggedOff			= 202;
	var cubeEK_NotReady				= 203;
	var cubeEK_Ready				= 204;
	var cubeEK_OtherWork			= 205;
	var cubeEK_AfterCallWork		= 206;
	var ctmpEK_Popup				= 254;

	/******************************************************************
	Variable Name: 
	Parameter  : 
	Return Value :
	Description : cubeLocalConnectState_Def
	******************************************************************/
	var cubeES_Null = 0;
	var cubeES_Initiate = 1;
	var cubeES_Altering = 2;
	var cubeES_Connect = 3;
	var cubeES_Hold = 4;
	var cubeES_Queued = 5;
	var cubeES_Fail = 6;

	/******************************************************************
	Variable Name: 
	Parameter  : 
	Return Value :
	Description : cubeEventCallCause_Def
	******************************************************************/
	var cubeV_AddedParty = 1;
	var cubeV_AlertingDevice = 2;
	var cubeV_AnsweringDevice = 3;
	var cubeV_CalledNumber = 4;
	var cubeV_CallingDevice = 5;
	var cubeV_ConfController = 6;
	var cubeV_DivertingDevice = 7;
	var cubeV_FailedDevice = 8;
	var cubeV_HoldingDevice = 9;
	var cubeV_LastRedirection = 10;
	var cubeV_ReleasingDevice = 11;
	var cubeV_RetrievingDevice = 12;
	var cubeV_TransferringDevice = 13;
	var cubeV_TrunkUsed = 14;

	/******************************************************************
	Variable Name: 
	Parameter  : 
	Return Value :
	Description : cubeAgentMode_Def
	******************************************************************/
  cubeV_AgentLogin = 0;
	cubeV_AgentLogout = 1;
	cubeV_AgentNotReady = 2;
	cubeV_AgentReady = 3;
	cubeV_AgentOtherWork = 4;
	cubeV_AgentAfterCallWork = 5;

	/******************************************************************
	Variable Name: 
	Parameter  : 
	Return Value :
	Description : 
	******************************************************************/
	var _callRefId				= 0;
	var _activeCallId			= 0;
	var _activeDevice			= "";
	var _heldCallId				= 0;
	var _heldDevice				= "";
	var _monitorParty			= "";
	var _otherParty				= "";
	var _uui					= "";
	var _uei					= "";
	var args					= "";
	var heldflag				= 0;
	
	var _period = 0;
	

	