package com.emp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpJDBCDAO implements EmpDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO EMPLOYEE(EMPNO,USERNO,EMPSTATUS,EMPNAME) VALUES (EMPLOYEE_SEQ.NEXTVAL,?,?,?)";
	private static final String UPDATE = "UPDATE EMPLOYEE SET USERNO=?,EMPSTATUS=?,EMPNAME=? WHERE EMPNO=?";
	private static final String DELETE = "UPDATE EMPLOYEE SET EMPSTATUS=0 WHERE EMPNO=?";
	private static final String GET_ONE_STMT = "SELECT EMPNO,USERNO,EMPSTATUS,EMPNAME FROM EMPLOYEE WHERE EMPSTATUS>0 AND EMPNO=?";
	private static final String GET_ONE_STMT_USERNO = "SELECT EMPNO,USERNO,EMPSTATUS,EMPNAME FROM EMPLOYEE WHERE EMPSTATUS>0 AND USERNO=?";
	private static final String GET_ALL_STMT = "SELECT EMPNO,USERNO,EMPSTATUS,EMPNAME FROM EMPLOYEE WHERE EMPSTATUS>0";

	@Override
	public void insert(EmpVO empVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, empVO.getUserNo());
			pstmt.setInt(2, empVO.getEmpStatus());
			pstmt.setString(3, empVO.getEmpName());

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
	public void update(EmpVO empVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, empVO.getUserNo());
			pstmt.setInt(2, empVO.getEmpStatus());
			pstmt.setString(3, empVO.getEmpName());
			pstmt.setString(4, empVO.getEmpNo());

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
	public EmpVO findByPrimaryKey(String empNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmpVO empVO = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, empNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				empVO = new EmpVO();
				empVO.setEmpNo(rs.getString("EMPNO"));
				empVO.setUserNo(rs.getString("USERNO"));
				empVO.setEmpStatus(rs.getInt("EMPSTATUS"));
				empVO.setEmpName(rs.getString("EMPNAME"));
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

		return empVO;
	}

	@Override
	public List<EmpVO> getAll() {
		List<EmpVO> list = new ArrayList<EmpVO>();
		EmpVO empVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				empVO = new EmpVO();
				empVO.setEmpNo(rs.getString("EMPNO"));
				empVO.setUserNo(rs.getString("USERNO"));
				empVO.setEmpStatus(rs.getInt("EMPSTATUS"));
				empVO.setEmpName(rs.getString("EMPNAME"));
				list.add(empVO);
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

	@Override
	public EmpVO findByUserNo(String userNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EmpVO empVO = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT_USERNO);

			pstmt.setString(1, userNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				empVO = new EmpVO();
				empVO.setEmpNo(rs.getString("EMPNO"));
				empVO.setUserNo(rs.getString("USERNO"));
				empVO.setEmpStatus(rs.getInt("EMPSTATUS"));
				empVO.setEmpName(rs.getString("EMPNAME"));
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

		return empVO;
	}
	
	@Override
	public void delete(String empNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, empNo);
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
	

	public static void main(String[] args) {
		// insert
//		EmpJDBCDAO dao=new EmpJDBCDAO();
//		EmpVO empVO = new EmpVO();
//		empVO.setUserNo("7");
//		empVO.setEmpStatus(1);
//		empVO.setEmpName("沉沉沉");
//		dao.insert(empVO);
//		System.out.println("新增成功");

		// update
//		EmpJDBCDAO dao=new EmpJDBCDAO();
//		EmpVO empVO = new EmpVO();
//		empVO.setUserNo("6");
//		empVO.setEmpStatus(1);
//		empVO.setEmpName("沉11");
//		empVO.setEmpNo("7");
//		dao.update(empVO);
//		System.out.println("修改成功");

		// findByPrimaryKey
//		EmpJDBCDAO dao=new EmpJDBCDAO();
//		EmpVO empVO=dao.findByPrimaryKey("5");
//		System.out.println(empVO.getEmpNo());
//		System.out.println(empVO.getUserNo());
//		System.out.println(empVO.getEmpStatus());
//		System.out.println(empVO.getEmpName());

		// getAll
//		EmpJDBCDAO dao=new EmpJDBCDAO();
//		List<EmpVO> list=dao.getAll();
//		
//		for(EmpVO empVO:list) {
//			System.out.println(empVO.getEmpNo());
//			System.out.println(empVO.getUserNo());
//			System.out.println(empVO.getEmpStatus());
//			System.out.println(empVO.getEmpName());
//			System.out.println("");
//			System.out.println("-------------------------------");
//
//		}

		// findByUserNO
//		EmpJDBCDAO dao = new EmpJDBCDAO();
//		EmpVO empVO = dao.findByUserNo("U000001");
//		System.out.println(empVO.getEmpNo());
//		System.out.println(empVO.getUserNo());
//		System.out.println(empVO.getEmpStatus());
//		System.out.println(empVO.getEmpName());
		
		//delete
		EmpJDBCDAO dao = new EmpJDBCDAO();
		dao.delete("E000001");
		System.out.println("刪除完成");
		
	}

	

}
