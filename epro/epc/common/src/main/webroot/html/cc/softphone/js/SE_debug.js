/***************************************************

         c_Softphone function
         
         
   - debug.js
     : 소프트폰에서 사용하는 debug write 정의

   * init : 2014-02-25 hawon
      update : 2014-09-14 hawon
   
***************************************************/


/*/////////////////////////////////////////////
    
    Debug Logging
    
/////////////////////////////////////////////*/		

  function debug(str) {
    
   var outputStr = "";
    
    
		switch(str) {

			case "Login" :
				
				if(LogLevel > 0)	outputStr = str + "["+ AgentID + "/" + AgentDN + "/" + AgentGroup + "/" + AgentParty + "]";

				break;
				
			case "MakeCall" :
					
	      if(LogLevel > 0)	outputStr = str + "/ MakeCall dial Number : " + getDialNo(document.all.dialTelNo.value);

				break;
				
			case "Consultation" :
				
				if(LogLevel > 0)	outputStr = str + "/ ConsultationCall dial Number : " + getDialNo(document.all.cubePhoneNo.value);

				break;

			case "Vacate" :
				
				if(LogLevel > 1)	outputStr = str + "/ ReasonCode : " + ReasonCode;

				break;

			case "SingleStepTransfer" :
				
				if(LogLevel > 1)	outputStr = str;

				break;

			case "NowStatus" :
				
				if(LogLevel > 2)	outputStr = str + " : " + NowStatus;

				break;
/*
			case "ConnectionCleared" :
				
				outputStr = outputStr + str + " : " + NowStatus;

				break;
*/				

			default :
				
				if(LogLevel > 2)	outputStr = str;
				//outputStr = str;

				break;

		}
    
   switch (LogLevel) {
   
     case 1:
       break;
     case 2:
       outputStr = outputStr + callLogStr();
       break;
     case 3:
       outputStr = outputStr + callLogStr() + etcLogStr();
       break;  
   
   }
    
    if (outputStr != "") document.all.cubeResult.innerText = debugHeader() + outputStr + "\n" + document.all.cubeResult.innerText;
				
  }

	function debugD(str) {
    
	  if(str != "") document.all.cubeResult.innerText = debugHeader() + str + "\n" + document.all.cubeResult.innerText;
		
	}
	
  function debugHeader() {
    
		var now = new Date();
		var dateString = now.getYear() + "-" + now.getMonth() + "-" + now.getDate() + " " + now.getHours() + ":" + now.getMinutes() + ":" + now.getSeconds();
		
		return LogHeader + " [" + dateString + "] ";
		
  }
  
  function callLogStr() {
    
    var str = " /_monitorParty : " + _monitorParty + " /" + "_otherParty : " + _otherParty
        				+ " /_activeCallId : " + _activeCallId + " /_callRefId : " + _callRefId 
        				+ " /_activeDevice : " + _activeDevice 
        				+ "\n";
		

		return str;
  
  }
  
  function etcLogStr() {
  

    var str =  " / " + "_uei : " + _uei	+ " / " + "_uui : " + _uui
                + " / " + "APIEvt.Ci : " + getAPI("Ci")
  				      + "\n";
 
  	return str;
  	
  }
  