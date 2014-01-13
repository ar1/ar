package com.apt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	//private static String url = "jdbc:mysql://localhost:3306/AptRef?";
	//private static String url = "jdbc:mysql://198.199.94.46:3306/ReferFriend?";
	private static String url = "jdbc:mysql://162.243.241.96:3306/AptRef?";
	//local
//	private static String userName = "keli";
//	private static String password = "1234";
	//Mac connect to server
	//private static String userName = "kl4660";
	//private static String password = "kEDa31F#)!xi";
	private static String userName = "frontend";
	private static String password = "aptreferral_2014";
	
	//Server_old
//	private static String userName = "root";
//	private static String password = "kl4660";
	
	//Server_new
//		private static String userName = "ee06b075";
//		private static String password = "45CwOaN";
	
	public Connection getDBConnection() throws SQLException{
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		 Connection conn = DriverManager.getConnection(url +
                 "user=" + userName + "&" + "password=" + password);
		 System.out.println("Connected to DB");
		return conn;
	}

	public void closeResource(Connection conn){
		
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Connection closed");
	}
	
	
	public static void main(String[] args) throws SQLException{
		DBUtil db = new DBUtil();
		db.getDBConnection();
	}
}
