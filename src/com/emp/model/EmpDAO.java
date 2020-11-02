package com.emp.model;

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

public class EmpDAO implements EmpDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO EMPLOYEE(EMPNO,USERNO,EMPNAME) VALUES ('E'||LPAD(to_char(EMPLOYEE_SEQ.NEXTVAL), '6', '0'),?,?)";
	private static final String UPDATE = "UPDATE EMPLOYEE SET USERNO=?,EMPSTATUS=?,EMPNAME=? WHERE EMPNO=?";
	private static final String DELETE = "UPDATE EMPLOYEE SET EMPSTATUS=0 WHERE EMPNO=?";
	private static final String GET_ONE_STMT="SELECT EMPNO,USERNO,EMPSTATUS,EMPNAME FROM EMPLOYEE WHERE EMPSTATUS>0 AND EMPNO=?";
	private static final String GET_ALL_STMT="SELECT EMPNO,USERNO,EMPSTATUS,EMPNAME FROM EMPLOYEE WHERE EMPSTATUS>0 AND USERNO!='U000001'";
	private static final String GET_ONE_STMT_USERNO="SELECT EMPNO,USERNO,EMPSTATUS,EMPNAME FROM EMPLOYEE WHERE EMPSTATUS>0 AND USERNO=?";

	
	@Override
	public void insert(EmpVO empVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, empVO.getUserNo());
			pstmt.setString(2, empVO.getEmpName());

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
	public void update(EmpVO empVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
		
			pstmt.setString(1, empVO.getUserNo());
			pstmt.setInt(2, empVO.getEmpStatus());
			pstmt.setString(3, empVO.getEmpName());
			pstmt.setString(4, empVO.getEmpNo());
			
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
	public EmpVO findByPrimaryKey(String empNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmpVO empVO = null;
		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, empNo);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				empVO=new EmpVO();
				empVO.setEmpNo(rs.getString("EMPNO"));
				empVO.setUserNo(rs.getString("USERNO"));
				empVO.setEmpStatus(rs.getInt("EMPSTATUS"));
				empVO.setEmpName(rs.getString("EMPNAME"));
			}
			
		}catch (SQLException se) {
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
		
		return empVO;
	}

	@Override
	public List<EmpVO> getAll() {
		List<EmpVO> list=new ArrayList<EmpVO>();
		EmpVO empVO=null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);		
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				empVO=new EmpVO();
				empVO.setEmpNo(rs.getString("EMPNO"));
				empVO.setUserNo(rs.getString("USERNO"));
				empVO.setEmpStatus(rs.getInt("EMPSTATUS"));
				empVO.setEmpName(rs.getString("EMPNAME"));
				list.add(empVO);
			}
			
		}catch (SQLException se) {
			se.printStackTrace();
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
	public EmpVO findByUserNo(String userNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmpVO empVO = null;
		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT_USERNO);
			
			pstmt.setString(1, userNo);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				empVO=new EmpVO();
				empVO.setEmpNo(rs.getString("EMPNO"));
				empVO.setUserNo(rs.getString("USERNO"));
				empVO.setEmpStatus(rs.getInt("EMPSTATUS"));
				empVO.setEmpName(rs.getString("EMPNAME"));
			}
			
		}catch (SQLException se) {
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
		
		return empVO;
	}

	@Override
	public void delete(String empNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, empNo);
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

}
