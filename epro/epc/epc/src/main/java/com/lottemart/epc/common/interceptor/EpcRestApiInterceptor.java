package com.lottemart.epc.common.interceptor;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.spring.security.PasswordEncoder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.util.ConfigUtils;
import com.lottemart.epc.common.dao.AccessCommonDao;

/**
 * 
 * @Class Name : EpcRestApiInterceptor.java
 * @Description : EPC RestApi Interceptor
 * 					- /restApi/..  
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      			수정자           		수정내용
 *  -------    		--------    	---------------------------
 * 
 *               </pre>
 */
public class EpcRestApiInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(EpcRestApiInterceptor.class);
	
	private static final String prefix = "/restapi";
	
	@Autowired
	AccessCommonDao accessDao;
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//인증헤더
        String authHeader = request.getHeader("Authorization");

        //Authorization 헤더 누락
        if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Basic")) {
        	logger.error("[EPC API|ERROR] 401 - 인증정보누락");
            sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "인증정보 누락");
            return false;
        }
        
        /*
         * 1. 호출 가능한 URL인지 확인
         */
        String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = uri.substring(contextPath.length());		//context 제외한 path
		path = path.replaceFirst(prefix, "");					//restapi 공통 path prefix 제거
		
		int apiUrlChk = accessDao.selectEpcApiUrlChk(path);
		if(apiUrlChk == 0) {
			logger.error("[EPC API|ERROR] 등록되지 않은 api 주소 - "+path);
        	sendJsonError(response, HttpServletResponse.SC_BAD_REQUEST, "등록되지 않은 api 주소");
            return false;
		}
		
        /*
         * 2. 인증정보 추출
         */
        String base64Credentials;		//암호화된 인증정보
        byte[] credentialsBytes;		//인증정보 복호화 Bytes
        String credentials;				//인증정보 복호화 String - (아이디:비밀번호)
        String[] credentialsArr;		//인증정보 분리 array
        
        String accessId, accessPwd;		//인증계정
        
        try {
        	//인증정보 추출
            base64Credentials = authHeader.substring("Basic".length()).trim();
            credentialsBytes = Base64.getDecoder().decode(base64Credentials);			//인증정보복호화
            credentials = new String(credentialsBytes, StandardCharsets.UTF_8); 		//인증정보 to string
            
            //인증정보 복호화 실패
            if(null == credentials || "".equals(credentials)) {
            	logger.error("[EPC API|ERROR] 인증정보 복호화 실패");
            	sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "인증정보 복호화 실패");
            	return false;
            }
            
            //인증정보 분리
            credentialsArr = credentials.split(":");
            //인증정보가 잘못됐을 경우
            if(null == credentialsArr || credentialsArr.length != 2) {
            	sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "잘못된 인증정보");
            	return false;
            }
            
            accessId = credentialsArr[0];		//접근ID
            accessPwd = credentialsArr[1];	//접근PWD
            
            //접근아이디 정보 없음
            if(accessId == null || "".equals(accessId)) {
            	logger.error("[EPC API|ERROR] 인증정보 확인 실패 (Access ID)");
            	sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "인증정보 확인 실패 (Access ID)");
                return false;
            }
            
            //접근패스워드 정보 없음
            if(accessPwd == null || "".equals(accessPwd)) {
            	logger.error("[EPC API|ERROR] 인증정보 확인 실패 (Access Password)");
            	sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "인증정보 확인 실패 (Access Password)");
                return false;
            }
            
        }catch(Exception e) {
        	logger.error("[EPC API|ERROR] 인증정보 복호화 실패("+e.getMessage()+")");
        	sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "인증정보 복호화 실패");
            return false;
        }
        
        /*
         * 3. 인증정보 확인
         */
        //아이디, 패스워드 공백제거
        accessId = StringUtils.deleteWhitespace(accessId);
        accessPwd = StringUtils.deleteWhitespace(accessPwd);
        
        //API 호출 사용자 정보 조회
        Map<String,Object> apiUserInfo = accessDao.selectEpcApiUserInfo(accessId);
        
        //사용자정보 없을 경우,
        if(apiUserInfo == null || apiUserInfo.isEmpty()) {
        	logger.error("[EPC API|ERROR] 등록되지 않은 사용자 정보 - accessId : "+accessId);
        	sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "등록되지 않은 인증 정보");
            return false;
        }
        
        String userPw = MapUtils.getString(apiUserInfo, "USER_PWD", "");	//패스워드
        String userNm = MapUtils.getString(apiUserInfo, "USER_NM", "");		//API유저명
        
        //패스워드 일치여부 확인
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean passwordEqual = passwordEncoder.matches(accessPwd, userPw);
        
        //패스워드 불일치 시
        if(!passwordEqual) {
        	logger.error("[EPC API|ERROR] 패스워드 불일치 - accessId : "+accessId);
        	sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 인증 정보 (password 불일치)");
            return false;
        }
        
        //성공 시, 헤더에 ACCESS 정보 저장
        Map<String, Object> connection = new HashMap<>();
        connection.put("code", HttpServletResponse.SC_OK);	//200
        connection.put("message", "정상");
        
        request.setAttribute("connection", connection);
        
        //접근 사용자정보 헤더에 저장
        String apiUserSessionKey = ConfigUtils.getString("epc.api.caller.session.key");	//헤더 key
        
        Map<String, String> apiUserMap = new HashMap<>();
        apiUserMap.put("userId", accessId);
        apiUserMap.put("userNm", userNm);
        
        request.setAttribute(apiUserSessionKey, apiUserMap);


        logger.info("[EPC API] Connection Ok - accessId : "+accessId);
        return true; // 인증 성공 시 계속 진행
    }

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}
	
	/**
	 * JSON Error 전송
	 * @param response
	 * @param status
	 * @param message
	 * @throws Exception
	 */
	private void sendJsonError(HttpServletResponse response, int status, String message) throws Exception {
		response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        
        /*
         * json 반환값 셋팅
         * - client 쪽에서 inputStream 대신 errorStream으로 추출가능
         */
        Map<String, Object> statusMap = new HashMap<String, Object>();
        statusMap.put("msgCd", "F");			//응답결과
        statusMap.put("message", message);		//응답메세지
        
        JSONObject jo = new JSONObject(statusMap);
        String statusStr = jo.toString();
        
        PrintWriter out = response.getWriter();
        out.print(statusStr);
        out.flush();
	}
}