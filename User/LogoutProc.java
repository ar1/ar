package com.login;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogoutProc
 */
public class LogoutProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String COOKIE_NAME = "remember_me";
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogoutProc() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = (User) request.getSession().getAttribute("user");
		System.out.println(RememberUser.RememberMap);
		Iterator<Map.Entry<String, User>> iter = RememberUser.RememberMap
				.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, User> entry = iter.next();
			User temp_user = entry.getValue();
			if ((temp_user.getEmail()).equals(user.getEmail())) {
				iter.remove();
			}
		}
		removeCookie(response, COOKIE_NAME);
		request.getSession().removeAttribute("user");
		request.getSession().invalidate();
		 
//		response.setHeader("Cache-Control","no-cache");   
//	      response.setHeader("Cache-Control","no-store");   
//	      response.setDateHeader("Expires", -1);   
//	      response.setHeader("Pragma","no-cache");   
	      
		response.sendRedirect("index.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}
	public static void removeCookie(HttpServletResponse response, String name) {
		addCookie(response, name, null, 0);
	}
	public static void addCookie(HttpServletResponse response, String name,
			String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
}
