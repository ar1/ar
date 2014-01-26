package com.login;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//Filter users for the access to certain pages, such as welcome, welcome_sec
/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {
	String COOKIE_NAME = "remember_me";
	int COOKIE_AGE = 120;
	//HashMap testmap = new HashMap();
    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub.
    	//testmap.put("9876543", "like");
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		User user = (User) request.getSession().getAttribute("user");
		
		System.out.println("this is filter");
		
		//when session does not exist
		if (user == null) {
			//check cookie
		    String uuid = getCookieValue(request, COOKIE_NAME);
		    
		    System.out.println("get uuid from cookie : " + uuid);
		    if (uuid != null) {
		        user = (User) RememberUser.RememberMap.get(uuid);
		        
		        
		        if (user != null) {
		        	System.out.println("username: " + user.getUsername());
		          //  request.login(username, "1234");
		            request.getSession().setAttribute("user", user); // Login.
		            addCookie(response, COOKIE_NAME, uuid, COOKIE_AGE); // Extends age.
		        } else {
		            removeCookie(response, COOKIE_NAME);
		        }
		    }
		}

		if (user == null) {
//			response.setHeader("Cache-Control","no-cache");
//			response.setHeader("Cache-Control","no-store");
//	        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
//	        response.setDateHeader("Expires", -1);
		    response.sendRedirect("index.html");
		} else {
//			response.setHeader("Cache-Control","no-cache");
//			response.setHeader("Cache-Control","no-store");
//		        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
//		        response.setDateHeader("Expires", -1);
		    chain.doFilter(req, res);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	public static String getCookieValue(HttpServletRequest request, String name) {
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (name.equals(cookie.getName())) {
	                return cookie.getValue();
	            }
	        }
	    }
	    return null;
	}

	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
	    Cookie cookie = new Cookie(name, value);
	    cookie.setPath("/");
	    cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	}

	public static void removeCookie(HttpServletResponse response, String name) {
	    addCookie(response, name, null, 0);
	}
}
