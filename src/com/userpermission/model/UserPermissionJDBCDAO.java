package com.userpermission.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserPermissionJDBCDAO implements UserPermissionDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";
	
	private static final String INSERT_STMT = "INSERT INTO USERPERMISSION(USERPERMISSIONNO,USERNO,PERMISSIONNO,READABLE,PERMISSIONEDIT) VALUES(USERPERMISSION_SEQ.NEXTVAL,?,?,?,?)";
	private static final String UPDATE = "UPDATE USERPERMISSION SET READABLE=?,PERMISSIONEDIT=? WHERE USERNO=? AND PERMISSIONNO=? ";
	private static final String GET_ONE_STMT = "SELECT USERNO,PERMISSIONNO,READABLE,PERMISSIONEDIT FROM USERPERMISSION WHERE USERNO=? AND PERMISSIONNO=?";
	private static final String GET_ALL_STMT = "SELECT USERNO,PERMISSIONNO,READABLE,PERMISSIONEDIT FROM USERPERMISSION";

	
	@Override
	public void insert(UserPermissionVO userPermissionVO) {
		Connection con=null;
		PreparedStatement pstmt =null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, userPermissionVO.getUserNo());
			pstmt.setString(2, userPermissionVO.getPermissionNo());
			pstmt.setInt(3, userPermissionVO.getReadable());
			pstmt.setInt(4, userPermissionVO.getPermissionEdit());

			pstmt.executeUpdate();

		} catch (SQLException se) {
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
	}

	@Override
	public void update(UserPermissionVO userPermissionVO) {
		Connection con=null;
		PreparedStatement pstmt =null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, userPermissionVO.getReadable());
			pstmt.setInt(2, userPermissionVO.getPermissionEdit());
			pstmt.setString(3, userPermissionVO.getUserNo());
			pstmt.setString(4, userPermissionVO.getPermissionNo());

			pstmt.executeUpdate();

		} catch (SQLException se) {
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
		
	}

	@Override
	public UserPermissionVO findByFK(String userNo, String permissionNo) {
		Connection con=null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		UserPermissionVO userPermissionVO=null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, userNo);
			pstmt.setString(2, permissionNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				userPermissionVO =new UserPermissionVO();
				userPermissionVO.setUserNo(rs.getString("USERNO"));
				userPermissionVO.setPermissionNo(rs.getString("PERMISSIONNO"));
				userPermissionVO.setReadable(rs.getInt("READABLE"));
				userPermissionVO.setPermissionEdit(rs.getInt("PERMISSIONEDIT"));
				
				
			}
			

		} catch (SQLException se) {
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
		
	
		return userPermissionVO;
	}

	@Override
	public List<UserPermissionVO> getAll() {
		List<UserPermissionVO> list=new ArrayList<UserPermissionVO>();
		UserPermissionVO userPermissionVO=null;
		
		Connection con=null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				userPermissionVO =new UserPermissionVO();
				userPermissionVO.setUserNo(rs.getString("USERNO"));
				userPermissionVO.setPermissionNo(rs.getString("PERMISSIONNO"));
				userPermissionVO.setReadable(rs.getInt("READABLE"));
				userPermissionVO.setPermissionEdit(rs.getInt("PERMISSIONEDIT"));
				list.add(userPermissionVO);
			}
			
		} catch (SQLException se) {
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
		
		UserPermissionJDBCDAO dao =new UserPermissionJDBCDAO();
		
		//insert
//		UserPermissionVO userPermissionVO =new UserPermissionVO();
//		userPermissionVO.setUserNo("U000004");
//		userPermissionVO.setPermissionNo("1");;
//		userPermissionVO.setReadable(1);
//		userPermissionVO.setPermissionEdit(0);
//		dao.insert(userPermissionVO);
//		System.out.println("新增成功");
		
		//update
//		UserPermissionVO userPermissionVO =new UserPermissionVO();
//		userPermissionVO.setReadable(0);
//		userPermissionVO.setPermissionEdit(1);
//		userPermissionVO.setUserNo("U000004");
//		userPermissionVO.setPermissionNo("1");;
//		dao.update(userPermissionVO);
//		System.out.println("修改成功");
		
		//findByFK
//		UserPermissionVO userPermissionVO=dao.findByFK("U000001", "2");
//		System.out.println(userPermissionVO.getUserNo());
//		System.out.println(userPermissionVO.getPermissionNo());
//		System.out.println(userPermissionVO.getReadable());
//		System.out.println(userPermissionVO.getPermissionEdit());
		
		
		//getAll
		List<UserPermissionVO> list=dao.getAll();
		for(UserPermissionVO userPermissionVO :list) {
			System.out.print(userPermissionVO.getUserNo()+"\t");
			System.out.print(userPermissionVO.getPermissionNo()+"\t");
			System.out.print(userPermissionVO.getReadable()+"\t");
			System.out.println(userPermissionVO.getPermissionEdit()+"\t");
			System.out.println("-------------------------------");
			
		}
		
		
	}

}
