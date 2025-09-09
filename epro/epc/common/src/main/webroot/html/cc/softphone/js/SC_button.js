/***************************************************

         c_Softphone function
         
         
   - button.js
     : 소프트폰에서 사용하는 Button Action 정의

   * init : 2014-02-25 hawon
      update : 2014-09-14 hawon
   
***************************************************/


  
/*/////////////////////////////////////////////
    
    Button Click method
    
/////////////////////////////////////////////*/		

	function procButtonClick(btnID) {
		
		//alert(btnID);
					
		var el = document.getElementById(btnID);		
		
		//alert(el.alt);		
		
		if (!isButtonON(el)) return false;
		
		switch(btnID) {
		
			case "Login":
				
				if(el.alt == "Login_ON") {
				  
				  
				  if(AutoLogin) {

    					sendInfoToMain("Login");
					
		      } else {
		      
  				  //alert(2);
  					if(login()) {
  					
  						debug("Login");
          						
        			waitEvent("Login");
        			
  						//stateChange("NotReady");
  												
  					} else {
  					
  					  alert(ErrMsgError + " : Login()");
  					  
  					  return false;
  					  
  					}
  			  }
					
				} else if (el.alt == "Logout_ON") {
				
					if (!saveCheck()) return false;
      					
          //Logout 시 바로 버튼 비활성화
      		waitEvent("Logout");
      			
					if(logout()) {
					
						stateChange("Logout");
												
					}
				
				} else {
		  					  
				  return false;
				  
				}
				
				break;
			
			case "Ready" :
				
				if(el.alt == "Ready_ON") {
				
					if (!saveCheck()) return false;
					
					if(ready()) {
	
						//debug("Ready");

        		waitEvent("Ready");
        			
						//stateChange("Ready");
						
					} else {
					  					  
  					return false;
  					
  				}
				
				} else if (el.alt == "NotReady_ON") {
				
					if(notReady()) {
	
						//debug("NotReady");
						
        		waitEvent("NotReady");
        			
						//stateChange("NotReady");
						
					} else {
					  					  
  					  return false;
  					  
  				}					
				
				} else {
				
				}
				
				break;
				
			case "HangUp" :
			
				if (el.alt == "HangUp_ON") {
				
					if (hangup()) {
					
						//debug("Hangup");
						
        		waitEvent("AfterCallWork");
        			
						//stateChange("AfterCallWork");
						
					} else {
					  					  
  					  return false;
  					  
  				}
								
				} else {
				
				}
				
				break;
				
			case "MakeCall" :
			
				if (el.alt == "Answer_ON") {
				
					if(answer()) {
					
					  if (PopupObject != null) PopupObject.close();
					
						//debug("Answer");
						
        		waitEvent("Established");
        			
						//stateChange("OnLine");
						
					} else {
					  					  
  					  return false;
  					  
  				}
				
				} else if (el.alt = "MakeCall_ON") {
				    					
					if (!saveCheck()) return false;
					    		
          variableSet("MakeCall");
          
    			// POPUP	
    			windowPopup(btnID);
    			
/*					
					if(makeCall()) {
					
						//debug("MakeCall");
						
        		//waitEvent("MakeCall");
        			
						//stateChange("OnLine");
						
					}								
*/					

				} else {
				
				}
			
				break;
			
			case "Consultation" :
			
				if (el.alt == "Consultation_ON") {
				    					
				  //Ready Agent List Get
				  agentReadyGetEx();
				    					
    			// POPUP
    			windowPopup(btnID);
    							
/*					if(consultation()) {
					
						//debug("Consultation");
						
						stateChange("ConsultationRing");
						
					}
*/					
				} else if (el.alt == "Reconnect_ON") {
				
					if(reconnect()) {
					
						//debug("Reconnect");
						
        		waitEvent("Reconnect");
        			
						//stateChange("Reconnect");
						
					} else {
					  					  
  					  return false;
  					  
  				}
				
				}
							
			case "Transfer" :
			
				if (el.alt == "Transfer_ON") {
				
					if(transfer()) {
					
						//debug("Transfer");
						
        		waitEvent("Transfer");
        			
						//stateChange("Transfer");
						
					} else {
					  					  
  					  return false;
  					  
  				}
					
				} else {
				
				}
				
			case "Conference" :
			
				if (el.alt == "Conference_ON") {
				
					if(conference()) {
					
						//debug("Conference");
						
        		waitEvent("Conference");
        			
						//stateChange("Conference");
						
					} else {
					  					  
  					  return false;
  					  
  				}
					
				} else {
				
				}
				
				break;
			
			case "Hold" :
				
				if (el.alt == "Hold_ON") {
				
					if(hold()) {
					
						//debug("Hold");
						
        		waitEvent("Hold");
        			
						//stateChange("Hold");
						
					} else {
					  					  
  					  return false;
  					  
  				}

				} else if (el.alt == "Retrieve_ON") {
				
					if(retrieve()) {
					
						//debug("Retrieve");
						
        		waitEvent("Retrieve");
        			
						//stateChange("OnLine");
						
					} else {
					  					  
  					  return false;
  					  
  				}
				
				}
				
				break;
				
			case "Vacate":
				
				if (el.alt == "Vacate_ON") {
					
    					
					if (!saveCheck()) return false;
						
          offAllButton();
          
    			//INFO POPUP
    			windowPopup(btnID);
    			
/*					
					if(vacate()) {
					
		        waitEvent("Vacate");
		  
						debug("Vacate");
						
						//stateChange("Vacate");
						
					}
*/
					
				} else if (el.alt == "VacateOut_ON") {
				
					if(afterCallWork()) {
					
		        waitEvent("VacateOut");
		  
						//debug("VacateOut");
						
						//stateChange("AfterCallWork");
						
					} else {
					  					  
  					  return false;
  					  
  				}
					
				} else {
				
				}
				
				break;
			
			case "Save" :
			
				if (el.alt == "Save_ON") {
				
				  save();
				  
				}
			  
			  break;
			
			default:
			
				break;
		
		}		
		  					  
	  return true;  					  
	
	}


  function offAllButton() {
  
		//OFF ALL
		buttonAction("Ready", "Ready_OFF");
		buttonAction("MakeCall", "MakeCall_OFF");
		buttonAction("HangUp", "HangUp_OFF");
		buttonAction("Consultation", "Consultation_OFF");
		buttonAction("Transfer", "Transfer_OFF");
		buttonAction("Conference", "Conference_OFF");
		buttonAction("Hold", "Hold_OFF");
		buttonAction("Vacate", "Vacate_OFF");
		buttonAction("Login", "Logout_OFF");
		if (SaveButtonUse) buttonAction("Save", "Save_OFF");
	
	}

/*/////////////////////////////////////////////
    
    Change Status(Button) method
    
/////////////////////////////////////////////*/		

	function stateChange(stat) {
		
		hideLoader();
		
		offAllButton();
		  
		switch(stat) {

			case "Logout" :
			
				buttonAction("Login", "Login_ON");
				break;
			
			case "NotReady" :
			
				buttonAction("Ready", "Ready_ON");
				buttonAction("MakeCall", "MakeCall_ON");
				buttonAction("Vacate", "Vacate_ON");
				buttonAction("Login", "Logout_ON");
		    if (SaveButtonUse) buttonAction("Save", "Save_ON");
    		
				break;
			
			case "Ready" :
			
				buttonAction("Ready", "NotReady_ON");
				break;
			
			case "Ring" :
				
				buttonAction("MakeCall", "Answer_ON");
				break;
			
			case "MakeRing" :
				
				buttonAction("HangUp", "HangUp_ON");
				break;
			
			case "OnLine" :
			
				buttonAction("HangUp", "HangUp_ON");
				buttonAction("Consultation", "Consultation_ON");
				buttonAction("Hold", "Hold_ON");
		    if (SaveButtonUse) buttonAction("Save", "Save_ON");
				break;
			
			case "ConsultationRing" :
			
				buttonAction("Consultation", "Reconnect_ON");

				break;
			
			case "Consultation" :
			
				buttonAction("Consultation", "Reconnect_ON");
				buttonAction("Transfer", "Transfer_ON");
				buttonAction("Conference", "Conference_ON");
		    if (SaveButtonUse) buttonAction("Save", "Save_ON");
				break;
						
			case "Reconnect" :
			
				buttonAction("Consultation", "Reconnect_ON");
		    if (SaveButtonUse) buttonAction("Save", "Save_ON");
				break;
			
			case "Transfer" :
			
				break;
			
			case "Conference" :
			
				buttonAction("HangUp", "HangUp_ON");
				break;
			
			case "AfterCallWork" :
			
				buttonAction("Ready", "Ready_ON");
				buttonAction("MakeCall", "MakeCall_ON");
				buttonAction("Vacate", "Vacate_ON");
				buttonAction("Login", "Logout_ON");
		    if (SaveButtonUse) buttonAction("Save", "Save_ON");
				break;
			
			case "Hold" :
				
				buttonAction("Hold", "Retrieve_ON");
				break;
			
			case "Vacate" :
			
				buttonAction("Vacate", "VacateOut_ON");
				break;
			
			default :
			
				break;
		
		}
		
		BeforStatus = NowStatus;
		NowStatus = stat;

		//displayStatus(stat);
		
	  debugD("stateChange() : " + stat);
	    
	}
	
  function getStatusName(stat) {
  
    var name = "";
    
    for (var i = 0; i < StatusList.length; i++) {
    
      if (StatusList[i][1] == stat) name = StatusList[i][0];
      
    }
    
    return name;
    
  }
	  	
	function buttonAction(bName, aType) {

//		debugD("button11"+bName);
//		debugD("button22"+aType);
	
		var el = document.getElementById(bName);
		imgCh(el, aType);
		
	}
	
		
	function isButtonON(obj) {
		
		if (obj.alt.length > 0) {
		
			var len = obj.alt.length;
				
			//if(obj.alt.substring(len,len-3) == "OFF") return 0;
			if(obj.alt.substring(len,len-2) == "ON") return true;
		
		}
		
		return false;
	}

	function imgCh(obj, str) {
	
		obj.alt = str
		obj.src = "./images/"+str+".gif";
		
		if (isButtonON(obj) == 1) obj.style.cursor = "pointer";
		else obj.style.cursor = "";
	
	}
  
  function mouseEvent(btnID,str) {
    
		var el = document.getElementById(btnID);		
		var len = el.alt.length;
		var bName = el.alt.substring(0, len-3);
		
		//alert(el.alt);		
		
		if (!isButtonON(el)) return;
		
    if (str == "over") {
    
  		el.src = "./images/"+bName+"_OVER.gif";
  		
		} else if (str == "out") {
		
  		el.src = "./images/"+bName+"_ON.gif";
  		
		} else {
		
		}
		
  }
  
  
    
/*/////////////////////////////////////////////

   Windows Pop up

/////////////////////////////////////////////*/

  function windowPopup(str) {

 
    switch (str) {
    
    case "AnswerCall" :
    
    		  //PopupObject = popUpWin("./popup/AnswerPop.html", "popWin", 340,310,0,0);
    		  //PopupObject = popUpWinModal("./popup/AnswerPop.html", "popWin", 320,310,0,0);
    			 
      break;
    
    case "MakeCall" :
    
    			PopupObject = popUpWin("./popup/MakeCallPop.html", "popWin", 340,300,0,0);
    			
      break;
      
    case "Vacate" :
    
    			PopupObject = popUpWin("./popup/VacatePop.html", "popWin", 360,310,0,0);
    			
      break;
    
    case "Consultation" :
                    
    			PopupObject = popUpWin("./popup/ConsultationCallPop.html", "popWin", 350,430,0,0);
    			
      break;
      
    default :
    
      break;
      
    }
    
    hideLoader();
    
  }
  
  