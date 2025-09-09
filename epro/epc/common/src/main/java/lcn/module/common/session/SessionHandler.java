package lcn.module.common.session;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionHandler<T> {

	private static SessionHandler<?> instance = null;

    public static SessionHandler<?> getInstance() {
    	synchronized(SessionHandler.class){
    		if (instance == null) {
                instance = new SessionHandler<Object>();
            }
    	}

        return instance;
    }

    private HttpSession getSession(HttpServletRequest request) {
        return request.getSession();
    }

    public void setSessionInfo(HttpServletRequest request, String key, T value) {
        HttpSession httpSession = getSession( request );
        httpSession.setAttribute( key, value );
    }

    @SuppressWarnings("unchecked")
	public T getSessionInfo(HttpServletRequest request, String key) {
        HttpSession httpSession = getSession( request );
        return (T) httpSession.getAttribute( key );
    }

    public boolean isLoginIfo(HttpServletRequest request) {
        HttpSession httpSession = getSession( request );

        return (httpSession.getAttribute( "LOGIN_SESSION" ) != null);
    }

    public void setLoginInfo(HttpServletRequest request, T value) {
        setSessionInfo( request, "LOGIN_SESSION", value );
    }

    public T getLoginInfo(HttpServletRequest request) {
        return getSessionInfo( request, "LOGIN_SESSION" );
    }

    public void invalidateSession(HttpServletRequest request) {
        HttpSession httpSession = getSession( request );
        Enumeration<?> er = httpSession.getAttributeNames();
        while (er.hasMoreElements()) {
            httpSession.removeAttribute( (String) er.nextElement() );
        }
        httpSession.invalidate();
    }

    public void removeSessionInfo(HttpServletRequest request, String key) {
        HttpSession httpSession = getSession( request );
        httpSession.removeAttribute( key );
    }

}