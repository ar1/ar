package com.apt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class GetPropertyProc
 */
public class GetPropertyProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPropertyProc() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("hi");
		String jsp = "mapListRef.jsp";
		String CITY = request.getParameter("locality");
		String STATE = request.getParameter("administrative_area_level_1");
		String COUNTY = request.getParameter("administrative_area_level_2");
		
		System.out.println(CITY+" "+STATE+" "+COUNTY);
		DBUtil db = new DBUtil();
		Connection conn = null;
		try {
			conn = db.getDBConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PropertyDB propdb = new PropertyDB(conn);
		
		ArrayList propertyResult=null;
		
		if(CITY!=null && STATE !=null){
			propertyResult = propdb.getPropertyCity(CITY, STATE);
		}
		
		else if(STATE !=null && COUNTY!=null && CITY==null){
			propertyResult = propdb.getPropertyCounty(COUNTY, STATE);
		}
		else if(STATE !=null && CITY==null && COUNTY==null){
			propertyResult = propdb.getPropertyState(STATE);
		}


		if(propertyResult==null||propertyResult.isEmpty()) {
			PrintWriter pw = response.getWriter();
			pw.write("No Result Found!");
            return;
		}	
		
		request.setAttribute("page_content",propertyResult);
		
		RequestDispatcher view = request.getRequestDispatcher(jsp);
		view.forward(request, response);
			
	}

}
