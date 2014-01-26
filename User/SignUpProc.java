package com.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignUpProc
 */
public class SignUpProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpProc() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String passwd = request.getParameter("passwd");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		Calendar cal = Calendar.getInstance();
		String insert_date = dateFormat.format(cal.getTime());
		
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
		boolean isMember = userDB.checkUser(email);
		if(isMember){
			response.sendRedirect("Error");
		}
		else{
			User user = new User();
			user.setEmail(email);
			user.setPassword(passwd);
			user.setUsername(username);
			user.setInsert_date(insert_date);
			
			boolean load_result = userDB.loadUser(user);
			if(load_result){
			response.sendRedirect("Ok");
			}
			else{
				System.out.println("wrong wrong");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

}
