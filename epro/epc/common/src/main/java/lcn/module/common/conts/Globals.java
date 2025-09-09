package lcn.module.common.conts;



/**
 *  Class Name : Globals.java
 *  Description : 시스템 구동 시 프로퍼티를 통해 사용될 전역변수를 정의한다.
 *  Modification Information
 * @author   
 * @since 
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2010.03.18  ryu.jiman          최초 생성
 *
 * </pre>
 */

public class Globals {
	
	//플랫폼 명(웹 모바일 표시용)
	public static final String PLATFORM_NAME_WINDOWMOBILE = "윈도우 모바일"; 
	public static final String PLATFORM_NAME_ANDROID = "안드로이드"; 
	public static final String PLATFORM_NAME_IPHONE = "아이폰"; 
	public static final String PLATFORM_NAME_BADA = "바다";
	public static final String PLATFORM_NAME_WINDOWS7 = "윈도우7"; 
	public static final String PLATFORM_NAME_UNKNOWN = "미등록"; 

	//플랫폼 코드
	public static final String PLATFORM_CD_WINDOWMOBILE = "2000011"; 
	public static final String PLATFORM_CD_ANDROID = "2000012"; 
	public static final String PLATFORM_CD_IPHONE = "2000013"; 
	public static final String PLATFORM_CD_BADA = "2000014";
	public static final String PLATFORM_CD_WINDOWS7 = "2000015"; 
	public static final String PLATFORM_CD_UNKNOWN = "9999999"; 

	
	//파일업로드공통 root
	public static final String fileStorePath = "/Project/upload"; 
	
	
	//모바일 어플 경로 
	//Help File 경로
	public static final String HELP_FILE_PATH = "/file/mobile/helpFile/"; 

	//App File 경로
	public static final String APP_FILE_PATH = "/file/mobile/appFile/"; 

	//App File 직접 다운로드 경로
	public static final String APP_FILE_DIRECT_PATH = "http://localhost:8080/fileDirectDownload.do?"; 

	//App 설치 File 직접 다운로드 경로
	public static final String APP_SETUP_FILE_DIRECT_PATH = "http://localhost:8080/filePathDirectDownload.do?"; 
	
	////////
	
	//OS 유형
    public static final String OS_TYPE = "UNIX";

    //메인 페이지
    public static final String MAIN_PAGE = "/main/MainMenuHome.do";
    //ShellFile 경로
    public static final String SHELL_FILE_PATH = "/Users/jimanryu/Project/props/globals.properties";
    //퍼로퍼티 파일 위치
    public static final String CONF_PATH = "/Users/jimanryu/Project/props/conf";
    //Server정보 프로퍼티 위치
    public static final String SERVER_CONF_PATH = "/Users/jimanryu/Project/props/server.properties";
    //Client정보 프로퍼티 위치
    public static final String CLIENT_CONF_PATH = "/Users/jimanryu/Project/props/client.properties";
    //파일포맷 정보 프로퍼티 위치
    public static final String FILE_FORMAT_PATH = "/Users/jimanryu/Project/props/format.properties";
   //
  
    //
    //파일 업로드 원 파일명
	public static final String ORIGIN_FILE_NM = "originalFileName";
	//파일 확장자
	public static final String FILE_EXT = "fileExtension";
	//파일크기
	public static final String FILE_SIZE = "fileSize";
	//업로드된 파일명
	public static final String UPLOAD_FILE_NM = "uploadFileName";
	//파일경로
	public static final String FILE_PATH = "filePath";
	
	//메일발송요청 XML파일경로
	public static final String MAIL_REQUEST_PATH = "/Users/jimanryu/Project/props/mail/request/";
	//메일발송응답 XML파일경로
	public static final String MAIL_RESPONSE_PATH = "/Users/jimanryu/Project/props/mail/request/";
	
	// G4C 연결용 IP (localhost)
	public static final String LOCAL_IP = "localhost";
    
}
