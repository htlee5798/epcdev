/***************************************************

         Environment function
         
         
   - envConstants.js
   
     : 환경 변수 설정

   * init : 2014-03-15 hawon
      update : 2014-09-14 hawon
   
***************************************************/

/*/////////////////////////////////////////////
    
    TEST Constants
    
/////////////////////////////////////////////*/

  //상담저장 시 대기 제어 정책 여부
  var SavePolish = true;
  
  
  //오토로그인 기능이 있는 경우 사용 (해당 함수 반드시 수정할 것)
  var AutoLogin = false;

  var LogHeader = "[Usr Log]";
  
  //로그 레벨 3 : 전체, 2:이벤트/액션, 1:최소
  var LogLevel = 1;

/*/////////////////////////////////////////////
    
     CTI Constants
    
/////////////////////////////////////////////*/

  var DualCTI = true;

  //CTI 연동 상수
  var ServerActive = "192.168.100.155";
  var ServerStandby="192.168.100.156";
  
  var Port = "9709";
  
  // pbx type(0:none, 1:asai, 2:merdian, 3:csta1, 4:csta2, 5:csta3)
//  var Protocol = 5; //LG BPX
 // var Protocol = 4; //LG BPX
  var Protocol = 1; //AVAYA BPX

  var AgentID = "";
  var AgentGroup = "";
  var AgentParty = "";
  var AgentDN = "";
  
  var AgentAppID = "";
  var AgentName = "";
  var AgentAppIP = "";
  
/*/////////////////////////////////////////////
    
     SITE Constants
    
/////////////////////////////////////////////*/

  //queueDN과 calledParty 병행 사용
  var UseCalledPartyForQueueNo = true;

  //소프트폰의 저장버튼 사용 여부
  var SaveButtonUse = false;
  
  //국선 구분 번호 / 내선번호 / 백화점 호전환 내선번호 길이  
  var EXInnerTelUse = false;
  
  var OBDivNo = "9";
  var EXDivNo = "8";
  var InnerTelLength = 4;
  var ExInnerTelLength = 6;  
  
  //그룹코드 Level   (팀코드 중 첫째 자리의 경우 1, 둘째 자리까지인 경우 2....
  var GroupCodeLevel = 2;
  
  //그룹코드 길이
  var GroupCodeLength = 3;
  
  //APP URL
//  var URL = "http://210.93.166.100/scc/";

  //#mart
  var APPURL = parent.document.form1.appUrl.value;
  //로컬
//  var APPURL = "http://localhost:10001/cc";
  //개발
//  var APPURL = "http://limadmind.lottemart.com/cc";
  //운영
//  var APPURL = "http://limadmin.lottemart.com/cc";
  
  //SingleStep Transfer Groups
  var SingleStepTransferQueueList;
//  var SingleStepTransferQueueList = [["전산호전환","3803"],["고객호전환","3805"]];


  //Input VDN Code Infomation
  //var InputVDNCodeList;
  
  var InputVDNCodeList = [["3911","마트본사","3911"]
                                        ,["3912","마트점포","3912"]
                                        ,["3913","마트지점핫라인", "3913"]
                                        ,["3914","결품처리", "3914"]
                                        ,["3922","마트명절IVR","3922"]
                                        ,["3926","기타이용문의","3926"]
                                        ,["3927","전화주문","3927"]
                                        ,["3928","명절주문","3928"]
                                        ,["3951","마트IVR장애용","3951"]
                                        ,["0000","없음","0000"]];
                                        
  
  
  var BranchVDNCodeList;

  
  
  
/*/////////////////////////////////////////////

   ARS Constants

/////////////////////////////////////////////*/

  //#mart
//  var ARSMessageUseYN = "N";
  var ARSMessageUseYN = "Y";


  var ARSMessagePortNo = "5051";
  
  var ConsultationARSYN = "N";
  
  //var ARSServiceCodeList;
  /*
  var ARSServiceCodeList = [["01","1701", "비밀번호"]
                                        ,["02","1701", "주민번호"]];
    */                                  

  var ARSReceiveData = "";
  //mart 주민번호 -> 생년월일
  var ARSServiceCodeList = [["01","3961", "비밀번호"]
                                        ,["02","3962", "생년월일"]];