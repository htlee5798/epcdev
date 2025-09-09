/**
 * @prjectName  : 롯데정보통신 프로젝트   
 * @since       : 2011. 4. 6. 오후 4:29:17
 * @author      : jmryu 
 * @Copyright(c) 2000 ~ 2010 롯데정보통신(주)
 *  All rights reserved.
 */
package lcn.module.common.session;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Class Name :
 * @Description :
 * @Modification Information
 * 
 *               <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 4. 6. 오후 4:29:17 jmryu
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */

public class SessionGenHandler<T> {

	private static SessionGenHandler<?> instance = null;

	public static SessionGenHandler<?> getInstance() {
    	synchronized(SessionGenHandler.class){
    		if (instance == null) {
                instance = new SessionGenHandler<Object>();
            }
    	}

        return instance;
    }

	private HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}

	public void setSessionInfo(HttpServletRequest request, String key, T value) {
		HttpSession httpSession = getSession(request);
		httpSession.setAttribute(key, value);
	}

	@SuppressWarnings("unchecked")
	public T getSessionInfo(HttpServletRequest request, String key) {
		HttpSession httpSession = getSession(request);
		return (T) httpSession.getAttribute(key);
	}

	public boolean isLoginIfo(HttpServletRequest request) {
		HttpSession httpSession = getSession(request);

		return (httpSession.getAttribute("LOGIN_SESSION") != null);
	}

	public void setLoginInfo(HttpServletRequest request, T value) {
		setSessionInfo(request, "LOGIN_SESSION", value);
	}

	public T getLoginInfo(HttpServletRequest request) {
		return getSessionInfo(request, "LOGIN_SESSION");
	}

	public void invalidateSession(HttpServletRequest request) {
		HttpSession httpSession = getSession(request);
		Enumeration<?> er = httpSession.getAttributeNames();
		while (er.hasMoreElements()) {
			httpSession.removeAttribute((String) er.nextElement());
		}
		httpSession.invalidate();
	}

	public void removeSessionInfo(HttpServletRequest request, String key) {
		HttpSession httpSession = getSession(request);
		httpSession.removeAttribute(key);
	}
}
