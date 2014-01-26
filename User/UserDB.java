package com.login;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UserDB {
	private Connection conn;
	private String table_name;
	
	public UserDB(Connection conn) {
		table_name = "User";
		this.conn = conn;

	}
	
	public boolean loadUser(User user){
		boolean load_result=false;
		String securePasswd=null;
		try {
			securePasswd = new SecurePWD().generateStrongPasswordHash(user.getPassword());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PreparedStatement pstmt = null;
		try {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO "
					+ table_name
					+ "(EMAIL,USERNAME,PASSWD, INSERT_DATE) VALUES(?,?,?,str_to_date(?,'%Y%m%d%H%i'))";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getUsername());
			pstmt.setString(3, securePasswd);
			pstmt.setString(4, user.getInsert_date());
			pstmt.execute();
			conn.commit();
			load_result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return load_result;
	}
	
	public User findUser(String email, String password) {
		ResultSet rs = null;
		Statement stmt = null;
		String username;
		String hashedpassword;
		boolean user_is_exists=false;
		User user=null; 
		try {
			String query = "select USERNAME, PASSWD from " + table_name
					+ " where Upper(EMAIL) = UPPER('" + email
					+ "') limit 1";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				username = rs.getString(1);
				hashedpassword = rs.getString(2);
				// Check if hashedpassword is matched with the password input by
				// user
				try {
					user_is_exists = new SecurePWD().validatePassword(password,
							hashedpassword);
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(user_is_exists){
				user = new User();
				user.setEmail(email);
				user.setPassword(password);
				user.setUsername(username);
				}
			}
			
		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return user;

	}
	
	public boolean checkUser(String email){
		boolean isMember=false;
		ResultSet rs = null;
		Statement stmt = null;
		String email_result=null;
		try {
			String query = "select EMAIL from " + table_name
					+ " where Upper(EMAIL) = UPPER('" + email
					+ "') limit 1";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				email_result = rs.getString(1);
				}
			
			if(email_result!=null){
				isMember=true;
			}
			
		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return isMember;
	}
//	public static void main(String[] args) throws SQLException{
//		DBUtil db = new DBUtil();
//		Connection conn = db.getDBConnection();
//		UserDB dba = new UserDB(conn);
//		System.out.println(dba.findUser("69@gmail.com").getUsername());
//	}
}
