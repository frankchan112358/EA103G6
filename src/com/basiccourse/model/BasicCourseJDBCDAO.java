package com.basiccourse.model;

import java.sql.*;
import java.util.*;

public class BasicCourseJDBCDAO implements BasicCourseDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO BASICCOURSE (BASICCOURSENO, BASICCOURSENAME, BASICCOURSEINFO, LESSON) VALUES('BC'||LPAD(to_char(BASICCOURSE_SEQ.NEXTVAL), '3', '0'), ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT BASICCOURSENO, BASICCOURSENAME, BASICCOURSEINFO, LESSON FROM BASICCOURSE WHERE ISDELETE=0 ORDER BY BASICCOURSENO";
	private static final String GET_ONE_STMT = "SELECT BASICCOURSENO, BASICCOURSENAME, BASICCOURSEINFO, LESSON FROM BASICCOURSE WHERE BASICCOURSENO =?";
	private static final String DELETE = "UPDATE BASICCOURSE SET ISDELETE=1 WHERE BASICCOURSENO =?";
	private static final String UPDATE = "UPDATE BASICCOURSE SET BASICCOURSENAME=?, BASICCOURSEINFO=?, LESSON=? WHERE BASICCOURSENO =?";

	@Override
	public void insert(BasicCourseVO basicCourseVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, basicCourseVO.getBasicCourseName());
			pstmt.setString(2, basicCourseVO.getBasicCourseInfo());
			pstmt.setInt(3, basicCourseVO.getLesson());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
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
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(BasicCourseVO basicCourseVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, basicCourseVO.getBasicCourseName());
			pstmt.setString(2, basicCourseVO.getBasicCourseInfo());
			pstmt.setInt(3, basicCourseVO.getLesson());
			pstmt.setString(4, basicCourseVO.getBasicCourseNo());

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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String basicCourseNo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, basicCourseNo);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public BasicCourseVO findByPrimaryKey(String basicCourseNo) {

		BasicCourseVO basicCourseVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, basicCourseNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				basicCourseVO = new BasicCourseVO();
				basicCourseVO.setBasicCourseNo(rs.getString("basicCourseNo"));
				basicCourseVO.setBasicCourseName(rs.getString("basicCourseName"));
				basicCourseVO.setBasicCourseInfo(rs.getString("basicCourseInfo"));
				basicCourseVO.setLesson(rs.getInt("lesson"));

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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return basicCourseVO;
	}

	@Override
	public List<BasicCourseVO> getAll() {
		
		List<BasicCourseVO> list = new ArrayList<BasicCourseVO>();
		BasicCourseVO basicCourseVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				basicCourseVO = new BasicCourseVO();
				basicCourseVO.setBasicCourseNo(rs.getString("basicCourseNo"));
				basicCourseVO.setBasicCourseName(rs.getString("basicCourseName"));
				basicCourseVO.setBasicCourseInfo(rs.getString("basicCourseInfo"));
				basicCourseVO.setLesson(rs.getInt("lesson"));
				list.add(basicCourseVO);
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
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {

		BasicCourseJDBCDAO dao = new BasicCourseJDBCDAO();

		// 新增
		BasicCourseVO basicCourseVO1 = new BasicCourseVO();
		basicCourseVO1.setBasicCourseName("HTML");
		basicCourseVO1.setBasicCourseInfo("超文本標記語言（英語：HyperText Markup Language，簡稱：HTML）是一種用於建立網頁的標準標記語言。");
		basicCourseVO1.setLesson(10);
		dao.insert(basicCourseVO1);

		// 修改
		BasicCourseVO basicCourseVO2 = new BasicCourseVO();
		basicCourseVO2.setBasicCourseNo("BC004");
		basicCourseVO2.setBasicCourseName("RWD");
		basicCourseVO2.setBasicCourseInfo("響應式網頁設計（英語：Responsive web design，通常縮寫為RWD），或稱自適應網頁設計、回應式網頁設計、對應式網頁設計。");
		basicCourseVO2.setLesson(5);
		dao.update(basicCourseVO2);

		dao.delete("1");

		// 查詢
		BasicCourseVO basicCourseVO3 = dao.findByPrimaryKey("2");
		System.out.println(basicCourseVO3.getBasicCourseNo());
		System.out.println(basicCourseVO3.getBasicCourseName());
		System.out.println(basicCourseVO3.getBasicCourseInfo());
		System.out.println(basicCourseVO3.getLesson());

		// 查詢
		List<BasicCourseVO> list = dao.getAll();
		for(BasicCourseVO abasicCourse : list) {
			System.out.println(abasicCourse.getBasicCourseNo());
			System.out.println(abasicCourse.getBasicCourseName());
			System.out.println(abasicCourse.getBasicCourseInfo());
			System.out.println(abasicCourse.getLesson());
			System.out.println("===================================");
		}
	}
}
