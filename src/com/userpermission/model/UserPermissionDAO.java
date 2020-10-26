package com.userpermission.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserPermissionDAO implements UserPermissionDAO_interface{

	private static DataSource ds=null;
	static {
		try {
			Context ctx=new InitialContext();
			ds=(DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO USERPERMISSION(USERPERMISSIONNO,USERNO,PERMISSIONNO,READABLE,PERMISSIONEDIT) VALUES(USERPERMISSION_SEQ.NEXTVAL,?,?,?,?)";
	private static final String UPDATE = "UPDATE USERPERMISSION SET READABLE=?,PERMISSIONEDIT=? WHERE USERNO=? AND PERMISSIONNO=? ";
	private static final String GET_ONE_STMT = "SELECT USERNO,PERMISSIONNO,READABLE,PERMISSIONEDIT FROM USERPERMISSION WHERE USERNO=? AND PERMISSIONNO=?";
	private static final String GET_ALL_STMT = "SELECT USERNO,PERMISSIONNO,READABLE,PERMISSIONEDIT FROM USERPERMISSION";
	private static final String GET_ALL_STMT_BYTHEMSELVES = "SELECT USERNO,PERMISSIONNO,READABLE,PERMISSIONEDIT FROM USERPERMISSION WHERE USERNO=?";

	
	
	@Override
	public void insert(UserPermissionVO userPermissionVO) {
		Connection con=null;
		PreparedStatement pstmt =null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, userPermissionVO.getUserNo());
			pstmt.setString(2, userPermissionVO.getPermissionNo());
			pstmt.setInt(3, userPermissionVO.getReadable());
			pstmt.setInt(4, userPermissionVO.getPermissionEdit());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, userPermissionVO.getReadable());
			pstmt.setInt(2, userPermissionVO.getPermissionEdit());
			pstmt.setString(3, userPermissionVO.getUserNo());
			pstmt.setString(4, userPermissionVO.getPermissionNo());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
			con = ds.getConnection();
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
			con = ds.getConnection();
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

	@Override
	public List<UserPermissionVO> getAllByThemselves(String userNo) {
		List<UserPermissionVO> list=new ArrayList<UserPermissionVO>();
		UserPermissionVO userPermissionVO=null;
		
		Connection con=null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT_BYTHEMSELVES);
			pstmt.setString(1, userNo);
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
	


}
