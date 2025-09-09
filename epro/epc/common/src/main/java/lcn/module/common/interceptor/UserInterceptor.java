package lcn.module.common.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lottemart.common.exception.AlertException;
import com.lottemart.common.login.model.LoginSession;

/**
 * @Class Name : 
 * @Description : HandlerInterceptor로서, 권한처리, 세션처리 등을 담당할 것임.
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class UserInterceptor implements HandlerInterceptor {

	protected final Log logger = LogFactory.getLog(getClass());
    
    //로그인 페이지
    private String loginUri;
    private String authErrorUri;
    private List<String> loginFreeActions;
    private List<String> accessFreeActions;
    
	public static final String parameterKey = "viewName";
    
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = uri.substring(contextPath.length());
		
		if(logger.isInfoEnabled()){
			logger.info("===[interceptor.preHandle] getRequestURL()=" + request.getRequestURL());
		}
		
		LoginSession loginSession = LoginSession.getLoginSession(request);
		
		// 체크하지 않는 URL에 속하면 통과.
		if(isIgnoreUrl(path)){		    
		    return true;
		}

		// 세션이 없으면 튕겨내기.
		if(loginSession == null){	
			if(uri.endsWith(".json")){
				throw new AlertException("세션 타임아웃이 발생했습니다. \n다시 로그인 하세요.");
			}else{
				String loginPage = resolveLoginPage(request);
				logger.error("== 로그인 정보가 없습니다. 로그인 페이지로 redirect시킵니다. (" + loginPage + ")");
				response.sendRedirect(loginPage);
				return false;				
			}
			
		}

        String paramValue = request.getParameter(parameterKey);
        
        if (paramValue != null && !paramValue.equals(""))
            uri += "?" + parameterKey + "=" + paramValue;

/*        
        // 로그인체크/권한체크 없이 접근가능 체크
        for (String action : loginFreeActions) {
        	if (action.equals(uri)) {
	        	// 로그인체크/권한체크 없이 접근가능
				allowAccess(request, response, chain);
				return;
        	}else if ((uri!=null)&&(uri.startsWith(action))) { //운영 전환시 삭제 
        		allowAccess(request, response, chain);
				return;
        	}else if ((uri!=null)&&(uri.endsWith(action))) { //운영 전환시 삭제 
        		allowAccess(request, response, chain);
				return;
        	}
        }
		
		LoginSession loginSession = LoginSession.getLoginSession((HttpServletRequest)request);
		if (loginSession == null) {
	        // 로그인 오류
	        ((HttpServletResponse)response).sendRedirect(((HttpServletRequest)request).getContextPath() + loginUri);
	        return;
		}
		else {
			// 실행권한 체크 - 로그인 경우
			// 입력된 url 경로가 showView.do 인 경우는 파라미터를 포함하여 체크하고,
			// 기타의 경우는 파라미터는 제외하고 경로만으로 체크한다.
			String scenario = servletPath;
			if (StringUtils.startsWith(uri, "/common/view/showView.do")) {
				scenario = uri;
			}

			// 권한체크 불필요한 페이지 체크(로그인은 필요함)
	        for (String action : accessFreeActions) {
	        	if (action.equals(scenario)) {
					allowAccess(request, response, chain);
					return;
	        	}
	        }
	        

			
            // 로그인체크 및 실행권한 체크 성공 성공
			allowAccess(request, response, chain);
			return;
		}		
		*/
		
				
		return true;
	}
	
	/**
	 * <pre>
	 * Desc : 세션이 없을때, 튕겨낼 페이지를 추정한다.
	 *    거래의 URL을 보고서, a로 시작하면, 마트쪽 로그인페이지로, 아니면, 콜센타 로그인페이지로 보낸다.
	 * </pre> 
	 * @param request
	 * @param path
	 * @return
	 */
	private String resolveLoginPage(HttpServletRequest request){
	    
		return request.getContextPath() + confirmStartsWithSlash( loginUri );
	}
	
	/**
	 * <pre>
	 * Desc : 세션체크를 하지 않는 목록은 설정파일(ws-servlet.xml)에 빼냈으며,
	 *    이 설정파일에 있는 url은 세션체크를 하지 않고 무시한다.
	 * </pre> 
	 * @param path
	 * @return
	 */
	private boolean isIgnoreUrl(String path){
		for(String ignoreUrl : loginFreeActions){
			if(path.endsWith(ignoreUrl)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)throws Exception {
		// TODO Auto-generated method stub
	}
    
    public static boolean isAjaxJsonRequest(HttpServletRequest request){
    	String requestUri = request.getRequestURI();
    	return requestUri.endsWith(".json");
    }
    
    
    private String confirmStartsWithSlash(String url){
        if(url!=null & !url.startsWith("/")){
            return "/" + url;
        }else{
            return url;
        }
    }

	public String getLoginUri() {
		return loginUri;
	}

	public void setLoginUri(String loginUri) {
		this.loginUri = loginUri;
	}

	public String getAuthErrorUri() {
		return authErrorUri;
	}

	public void setAuthErrorUri(String authErrorUri) {
		this.authErrorUri = authErrorUri;
	}

	public List<String> getLoginFreeActions() {
		return loginFreeActions;
	}

	public void setLoginFreeActions(List<String> loginFreeActions) {
		this.loginFreeActions = loginFreeActions;
	}

	public List<String> getAccessFreeActions() {
		return accessFreeActions;
	}

	public void setAccessFreeActions(List<String> accessFreeActions) {
		this.accessFreeActions = accessFreeActions;
	}

}
