package com.permission.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissionJDBCDAO implements PermissionDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";
	
	private static final String GET_ALL_STMT="SELECT *FROM PERMISSION";

	@Override
	public List<PermissionVO> getAll() {
		List<PermissionVO> list=new ArrayList<PermissionVO>();
		PermissionVO permissionVO=null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);		
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				permissionVO=new PermissionVO();
				permissionVO.setPermissionNo(rs.getString(1));
				permissionVO.setPermissionName(rs.getString(2));
				list.add(permissionVO);
			}
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
		return list;
	}
	
	public static void main(String[] args) {
		PermissionJDBCDAO dao =new PermissionJDBCDAO();
		List<PermissionVO> list=dao.getAll();
		
		for(PermissionVO permissionVO:list) {
			System.out.print(permissionVO.getPermissionNo());
			System.out.print("\t");
			System.out.println(permissionVO.getPermissionName());
			System.out.println("----------------------");
		}
	}

}
