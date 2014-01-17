package com.apt;

import java.io.IOException;
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
public class GetPropertyAllProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPropertyAllProc() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String CITY = request.getParameter("locality");
		String STATE = request.getParameter("administrative_area_level_1");
		String COUNTY = request.getParameter("administrative_area_level_2");
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
			propertyResult = propdb.getPropertyCityAll(CITY, STATE);
		}
		
		else if(STATE !=null && COUNTY!=null && CITY==null){
			propertyResult = propdb.getPropertyCountyAll(COUNTY, STATE);
		}
		else if(STATE !=null && CITY==null && COUNTY==null){
			propertyResult = propdb.getPropertyStateAll(STATE);
		}

		
		
		if(propertyResult!=null){
		
		request.setAttribute("page_content",propertyResult);
		
		RequestDispatcher view = request.getRequestDispatcher("/NextPage");
		view.forward(request, response);
		}
		else{
			System.out.println("No Property");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
