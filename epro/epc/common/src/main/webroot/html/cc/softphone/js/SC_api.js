/***************************************************

         c_Softphone function
         
         
   - api.js
     : 소프트폰에서 사용하는 api 부가 합수 정의

   * init : 2014-02-25 hawon
      update : 2014-09-14 hawon
   
***************************************************/


/*/////////////////////////////////////////////
    
    API get method
    
/////////////////////////////////////////////*/		

	function getAPI(str) {

/*
        CIData = CAPI3XEvt1.Ci;
        IncomQueueNo = CAPI3XEvt1.CalledParty;
        IncomQueueNo1 = CAPI3XEvt1.QueueDN; 
      _monitorParty	= NXCAPI3XEvt.MonitorParty;
      _otherParty		= NXCAPI3XEvt.OtherParty;
      _callRefId = NXCAPI3XEvt.CallRefID;
      _heldDevice = NXCAPI3XEvt.MonitorParty;
      NXCAPI3XEvt.CalledParty
*/

    var values = "";

		switch(str) {
		
			case "CallRefID" :
			
        values = NXCAPI3XEvt.CallRefID; 
        
				break;
				
			case "MonitorParty" :
			
        values = NXCAPI3XEvt.MonitorParty; 
        
				break;
				
			case "OtherParty" :
			
        values = NXCAPI3XEvt.OtherParty; 
        
				break;
				
			case "Ci":
				
        values = NXCAPI3XEvt.Ci;
        
				break;
			
			case "CalledParty" :
				
        values = NXCAPI3XEvt.CalledParty;
        
				break;
				
			case "QueueDN" :
			
        values = NXCAPI3XEvt.QueueDN; 
        
				break;
				
		  case "LocalConnState" :
		  
				values = NXCAPI3XEvt.LocalConnState;
				
				break;
				
			default:
			
				break;
		
		}
		
		return values;
	
	}


  