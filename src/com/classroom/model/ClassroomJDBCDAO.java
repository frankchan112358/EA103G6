package com.classroom.model;

import java.sql.*;
import java.util.*;

public class ClassroomJDBCDAO implements ClassroomDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	// 新增
	private static final String INSERT_STMT = "INSERT INTO CLASSROOM(CLASSROOMNO,CLASSROOMNAME,NUMBEROFSTUDENT)VALUES(CLASSROOM_SEQ.NEXTVAL,?,?)";
	// 查全部
	private static final String GET_ALL_STMT = "SELECT CLASSROOMNO,CLASSROOMNAME,NUMBEROFSTUDENT FROM CLASSROOM WHERE ISDELETE=0 ORDER BY CLASSROOMNO";
	// 查單個
	private static final String GET_ONE_STMT = "SELECT CLASSROOMNO,CLASSROOMNAME,NUMBEROFSTUDENT FROM CLASSROOM WHERE CLASSROOMNO =?";
	// 更新刪除狀態
	private static final String DELETE = "UPDATE CLASSROOM SET ISDELETE=1 WHERE CLASSROOMNO=?";
	// 修改
	private static final String UPDATE = "UPDATE CLASSROOM SET CLASSROOMNAME=?,NUMBEROFSTUDENT=? WHERE CLASSROOMNO=?";

	@Override
	public void insert(ClassroomVO classroomVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, classroomVO.getClassroomName());
			pstmt.setInt(2, classroomVO.getNumberOfStudent());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public void update(ClassroomVO classroomVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);

			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, classroomVO.getClassroomName());
			pstmt.setInt(2, classroomVO.getNumberOfStudent());
			pstmt.setString(3, classroomVO.getClassroomNo());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public void delete(String classroomNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, classroomNo);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public ClassroomVO findByPrimaryKey(String classroomNo) {

		ClassroomVO classroomVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, classroomNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				classroomVO = new ClassroomVO();
				classroomVO.setClassroomNo(rs.getString("classroomNo"));
				classroomVO.setClassroomName(rs.getString("classroomName"));
				classroomVO.setNumberOfStudent(rs.getInt("numberOfStudent"));
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
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
		return classroomVO;
	}

	@Override
	public List<ClassroomVO> getAll() {
		List<ClassroomVO> list = new ArrayList<ClassroomVO>();
		ClassroomVO classroomVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				classroomVO = new ClassroomVO();
				classroomVO.setClassroomNo(rs.getString("classroomNo"));
				classroomVO.setClassroomName(rs.getString("classroomName"));
				classroomVO.setNumberOfStudent(rs.getInt("numberOfStudent"));

				list.add(classroomVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
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
		ClassroomJDBCDAO dao = new ClassroomJDBCDAO();

		// 新增
		ClassroomVO classroomVO1 = new ClassroomVO();
		classroomVO1.setClassroomName("306");
		classroomVO1.setNumberOfStudent(55);
		dao.insert(classroomVO1);

		// 查詢
		ClassroomVO classroomVO2 = dao.findByPrimaryKey("2");
		System.out.print(classroomVO2.getClassroomNo() + ",");
		System.out.print(classroomVO2.getClassroomName() + ",");
		System.out.println(classroomVO2.getNumberOfStudent());
		System.out.println("---------------------");
//		
//		// 查詢
		List<ClassroomVO> list = dao.getAll();
		for (ClassroomVO aClassroomVO : list) {
			System.out.print(aClassroomVO.getClassroomNo() + ",");
			System.out.print(aClassroomVO.getClassroomName() + ",");
			System.out.println(aClassroomVO.getNumberOfStudent());
			System.out.println("---------------------");
		}

		// 刪除狀態(isdelete)
//		dao.delete("1");

		// 修改
		ClassroomVO classroomVO3 = new ClassroomVO();
		classroomVO3.setClassroomName("333");
		classroomVO3.setNumberOfStudent(22);
		classroomVO3.setClassroomNo("2");
		dao.update(classroomVO3);
	}
}
