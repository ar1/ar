package com.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//Check the user, first through session, then through cookie
/**
 * Servlet implementation class LoginMgmt
 */
public class LoginMgmt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public HashMap rememberDao = new HashMap();
	private String COOKIE_NAME = "remember_me";
	private int COOKIE_AGE = 120;
	private String count_file ="/Users/ee06b075/Documents/countNo";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginMgmt() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void init(){
		//Get the number of visiting of the site at the starting point of tomcat
		FileReader file = null;
		try {
			file = new FileReader(count_file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(file);
		String numVal = null;
		try {
			numVal = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.getServletContext().setAttribute("visits", numVal+"");
	}
	
	public void destroy(){
		//Write the added number back to the file at the end point of tomcat
		FileWriter fw = null;
		try {
			fw = new FileWriter(count_file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedWriter bw = new BufferedWriter(fw);
		
		try {
			bw.write(this.getServletContext().getAttribute("visits")+"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		boolean remember = "true".equals(request.getParameter("remember"));
		System.out.println(remember);
		// User user = userDAO.find(username, password);

		if (email != null) {

			// user.setPassword(password);

			//Connect to DB
			DBUtil db = new DBUtil();
			Connection conn = null;

			try {
				conn = db.getDBConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Get the user by email provided by user
			UserDB userDB = new UserDB(conn);
			User user = userDB.findUser(email,password);
			if (user != null) {
				
					// request.login(username, password); 
					request.getSession().setAttribute("user", user);
					// if user wanted to stay logged in, use cookie
					if (remember) {
						String uuid = UUID.randomUUID().toString();
						System.out.println("generated uuid: " + uuid);
						RememberUser.RememberMap.put(uuid, user);
						// String uuid = "9876543";
						// rememberDao.put(uuid, username);
						// rememberDAO.save(uuid, user);
						addCookie(response, COOKIE_NAME, uuid, COOKIE_AGE);
						//response.sendRedirect("Welcome");
						response.sendRedirect("PropertyPagingProc?pageNow=1");
					} else {
						// rememberDAO.delete(user);
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
						//response.sendRedirect("Welcome");
						response.sendRedirect("PropertyPagingProc?pageNow=1");
					}
					
					//get the time of visits, add 1 when each time successful login
					int times = Integer.parseInt((String) this.getServletContext().getAttribute("visits"));
					++times;
					this.getServletContext().setAttribute("visits", times+"");
					
				
			}
			else{
				response.sendRedirect("index.html");
			}
			
			if(conn!=null){
				//System.out.println("i am here");
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
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

	public static void addCookie(HttpServletResponse response, String name,
			String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public static void removeCookie(HttpServletResponse response, String name) {
		addCookie(response, name, null, 0);
	}
}
