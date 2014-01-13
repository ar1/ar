package com.apt;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PropertyDB {

	private Connection conn;
	private String table_name;
	private PreparedStatement pstmt;
	private Statement stmt;

	public PropertyDB(Connection conn) {
		table_name = "Property";
		this.conn = conn;

	}

	public ArrayList<Property> getPropertyCity(String city, 
			String state) {
		ResultSet rs = null;
		Statement stmt = null;
		ArrayList<Property> propResult = new ArrayList<Property>();
		String PROP_NAME;
		String LAT_CENTROID;
		String LONG_CENTROID;
		String Ref_Nbr;
		Property property = null;
		try {
			// Get needed-page content
			String query = "select * from " + table_name
					+ " where Upper(CITY)=Upper('" + city
					+ "') and Upper(state)=Upper('" + state
					+ "')";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				PROP_NAME = rs.getString(2);
				LAT_CENTROID = rs.getString(10);
				LONG_CENTROID = rs.getString(11);
				Ref_Nbr = rs.getString(19);
//				System.out.println(PROP_NAME);
//				System.out.println(LAT_CENTROID);
//				System.out.println(LONG_CENTROID);
//				System.out.println(Ref_Nbr);
				
				property = new Property();
				property.setPROP_NAME(PROP_NAME);
				property.setLAT_CENTROID(LAT_CENTROID);
				property.setLONG_CENTROID(LONG_CENTROID);
				property.setRef_Nbr(Ref_Nbr);

				propResult.add(property);
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
		return propResult;
	}

	public ArrayList<Property> getPropertyCounty(String county, 
			String state) {
		ResultSet rs = null;
		Statement stmt = null;
		ArrayList<Property> propResult = new ArrayList<Property>();
		String PROP_NAME;
		String LAT_CENTROID;
		String LONG_CENTROID;
		String Ref_Nbr;
		Property property = null;
		try {
			// Get needed-page content
			String query = "select * from " + table_name
					+ " where Upper(COUNTY)=Upper('" + county
					+ "') and Upper(state)=Upper('" + state
					+ "')";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				PROP_NAME = rs.getString(2);
				LAT_CENTROID = rs.getString(10);
				LONG_CENTROID = rs.getString(11);
				Ref_Nbr = rs.getString(19);
//				System.out.println(PROP_NAME);
//				System.out.println(LAT_CENTROID);
//				System.out.println(LONG_CENTROID);
//				System.out.println(Ref_Nbr);
				
				property = new Property();
				property.setPROP_NAME(PROP_NAME);
				property.setLAT_CENTROID(LAT_CENTROID);
				property.setLONG_CENTROID(LONG_CENTROID);
				property.setRef_Nbr(Ref_Nbr);

				propResult.add(property);
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
		return propResult;
	}
	
	public ArrayList<Property> getPropertyState(String state) {
		ResultSet rs = null;
		Statement stmt = null;
		ArrayList<Property> propResult = new ArrayList<Property>();
		String PROP_NAME;
		String LAT_CENTROID;
		String LONG_CENTROID;
		String Ref_Nbr;
		Property property = null;
		try {
			// Get needed-page content
			String query = "select * from " + table_name
					+ " where Upper(state)=Upper('" + state
					+ "')";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				PROP_NAME = rs.getString(2);
				LAT_CENTROID = rs.getString(10);
				LONG_CENTROID = rs.getString(11);
				Ref_Nbr = rs.getString(19);
//				System.out.println(PROP_NAME);
//				System.out.println(LAT_CENTROID);
//				System.out.println(LONG_CENTROID);
//				System.out.println(Ref_Nbr);
				
				property = new Property();
				property.setPROP_NAME(PROP_NAME);
				property.setLAT_CENTROID(LAT_CENTROID);
				property.setLONG_CENTROID(LONG_CENTROID);
				property.setRef_Nbr(Ref_Nbr);

				propResult.add(property);
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
		return propResult;
	}
	// public boolean checkPropInfo(Property property) {
	//
	// boolean check_result = false;
	// ResultSet rs=null;
	// int count = 0;
	// try {
	// String query = "select * from " + table_name
	// + " where Upper(ZIP)=UPPER('" + property.getZipcode()
	// + "') and Upper(P_NAME)=('" + property.getProperty_name()
	// + "')";
	// stmt = conn.createStatement();
	// rs = stmt.executeQuery(query);
	// while (rs.next()) {
	// ++count;
	// }
	// if (count == 0) {
	// check_result = false;
	// } else {
	// check_result = true;
	// }
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	//
	// }finally{
	// if(rs!=null){
	// try {
	// rs.close();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// if(stmt!=null){
	// try {
	// stmt.close();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	// return check_result;
	//
	// }

	// public boolean loadPropInfo(Property property) {
	// boolean load_result = false;
	// try {
	// conn.setAutoCommit(false);
	// String sql = "INSERT INTO "
	// + table_name
	// + "(ZIP,P_NAME, INSERT_DATE) VALUES(?,?,str_to_date(?,'%Y%m%d%H%i'))";
	// pstmt = conn.prepareStatement(sql);
	// pstmt.setString(1, property.getZipcode());
	// pstmt.setString(2, property.getProperty_name());
	// pstmt.setString(3, property.getInsert_date());
	// pstmt.execute();
	// conn.commit();
	// load_result = true;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }finally{
	// if(pstmt!=null){
	// try {
	// pstmt.close();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	// // try {
	// // conn.close();
	// // } catch (SQLException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // }
	//
	// return load_result;
	//
	// }
	

}
