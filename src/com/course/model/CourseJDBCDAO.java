package com.course.model;

import java.sql.*;
import java.util.*;

public class CourseJDBCDAO implements CourseDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO COURSE (COURSENO, BANJINO, TEACHERNO, CLASSROOMNO, BASICCOURSENO, COURSENAME, COURSEOUTLINE, LESSON, STARTDATE, ENDDATE, STATUS) VALUES('C'||LPAD(to_char(COURSE_SEQ.NEXTVAL), '3', '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT COURSENO, BANJINO, TEACHERNO, CLASSROOMNO, BASICCOURSENO, COURSENAME, COURSEOUTLINE, LESSON, TO_CHAR(STARTDATE,'YYYY-MM-DD') STARTDATE, TO_CHAR(ENDDATE,'YYYY-MM-DD') ENDDATE, STATUS FROM COURSE WHERE ISDELETE=0 ORDER BY COURSENO";
	private static final String GET_ONE_STMT = "SELECT COURSENO, BANJINO, TEACHERNO, CLASSROOMNO, BASICCOURSENO, COURSENAME, COURSEOUTLINE, LESSON, TO_CHAR(STARTDATE,'YYYY-MM-DD') STARTDATE, TO_CHAR(ENDDATE,'YYYY-MM-DD') ENDDATE, STATUS FROM COURSE WHERE COURSENO =?";
	private static final String DELETE = "UPDATE COURSE SET ISDELETE=1 WHERE COURSENO=?";
	private static final String UPDATE = "UPDATE COURSE SET BANJINO=?, TEACHERNO=?, CLASSROOMNO=?, BASICCOURSENO=?, COURSENAME=?, COURSEOUTLINE=?, LESSON=?, STARTDATE=?, ENDDATE=?, STATUS=? WHERE COURSENO=? ";

	@Override
	public void insert(CourseVO courseVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, courseVO.getBanjiNo());
			pstmt.setString(2, courseVO.getTeacherNo());
			pstmt.setString(3, courseVO.getClassroomNo());
			pstmt.setString(4, courseVO.getBasicCourseNo());
			pstmt.setString(5, courseVO.getCourseName());
			pstmt.setString(6, courseVO.getCourseOutline());
			pstmt.setInt(7, courseVO.getLesson());
			pstmt.setDate(8, courseVO.getStartDate());
			pstmt.setDate(9, courseVO.getEndDate());
			pstmt.setInt(10, courseVO.getStatus());

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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(CourseVO courseVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, courseVO.getBanjiNo());
			pstmt.setString(2, courseVO.getTeacherNo());
			pstmt.setString(3, courseVO.getClassroomNo());
			pstmt.setString(4, courseVO.getBasicCourseNo());
			pstmt.setString(5, courseVO.getCourseName());
			pstmt.setString(6, courseVO.getCourseOutline());
			pstmt.setInt(7, courseVO.getLesson());
			pstmt.setDate(8, courseVO.getStartDate());
			pstmt.setDate(9, courseVO.getEndDate());
			pstmt.setInt(10, courseVO.getStatus());
			pstmt.setString(11, courseVO.getCourseNo());

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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String courseNo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, courseNo);

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
	public CourseVO findByPrimaryKey(String courseNo) {

		CourseVO courseVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, courseNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				courseVO = new CourseVO();
				courseVO.setCourseNo(rs.getString("courseNo"));
				courseVO.setBanjiNo(rs.getString("banjiNo"));
				courseVO.setTeacherNo(rs.getString("teacherNo"));
				courseVO.setClassroomNo(rs.getString("classroomNo"));
				courseVO.setBasicCourseNo(rs.getString("basicCourseNo"));
				courseVO.setCourseName(rs.getString("courseName"));
				courseVO.setCourseOutline(rs.getString("courseOutline"));
				courseVO.setLesson(rs.getInt("lesson"));
				courseVO.setStartDate(rs.getDate("startDate"));
				courseVO.setEndDate(rs.getDate("endDate"));
				courseVO.setStatus(rs.getInt("status"));
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
		return courseVO;
	}

	@Override
	public List<CourseVO> getAll() {
		
		List<CourseVO> list = new ArrayList<CourseVO>();
		CourseVO courseVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				courseVO = new CourseVO();
				courseVO.setCourseNo(rs.getString("courseNo"));
				courseVO.setBanjiNo(rs.getString("banjiNo"));
				courseVO.setTeacherNo(rs.getString("teacherNo"));
				courseVO.setClassroomNo(rs.getString("classroomNo"));
				courseVO.setBasicCourseNo(rs.getString("basicCourseNo"));
				courseVO.setCourseName(rs.getString("courseName"));
				courseVO.setCourseOutline(rs.getString("courseOutline"));
				courseVO.setLesson(rs.getInt("lesson"));
				courseVO.setStartDate(rs.getDate("startDate"));
				courseVO.setEndDate(rs.getDate("endDate"));
				courseVO.setStatus(rs.getInt("status"));
				list.add(courseVO);
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

		CourseJDBCDAO dao = new CourseJDBCDAO();

		// 新增
		CourseVO courseVO1 = new CourseVO();
		courseVO1.setBanjiNo("B001");
		courseVO1.setTeacherNo("T000001");
		courseVO1.setClassroomNo("1");
		courseVO1.setBasicCourseNo("BC001");
		courseVO1.setCourseName("HTML");
		courseVO1.setCourseOutline("超文本標記語言（英語：HyperText Markup Language，簡稱：HTML）是一種用於建立網頁的標準標記語言。");
		courseVO1.setLesson(5);
		courseVO1.setStartDate(java.sql.Date.valueOf("2020-05-01"));
		courseVO1.setEndDate(java.sql.Date.valueOf("2020-06-01"));
		courseVO1.setStatus(0);
		dao.insert(courseVO1);

		// 修改
 		CourseVO courseVO2 = new CourseVO();
		courseVO2.setCourseNo("C002");
		courseVO2.setBanjiNo("B002");
		courseVO2.setTeacherNo("T000002");
		courseVO2.setClassroomNo("2");
		courseVO2.setBasicCourseNo("BC001");
		courseVO2.setCourseName("JAVA1");
		courseVO2.setCourseOutline("Java是一種廣泛使用的電腦程式設計語言");
		courseVO2.setLesson(7);
		courseVO2.setStartDate(java.sql.Date.valueOf("2020-05-01"));
		courseVO2.setEndDate(java.sql.Date.valueOf("2020-06-01"));
		courseVO2.setStatus(0);
		dao.update(courseVO2);

		// 刪除
		dao.delete("C003");

		// 查詢
		CourseVO courseVO3 = dao.findByPrimaryKey("C004");
		System.out.println(courseVO3.getCourseNo());
		System.out.println(courseVO3.getBanjiNo());
		System.out.println(courseVO3.getTeacherNo());
		System.out.println(courseVO3.getClassroomNo());
		System.out.println(courseVO3.getBasicCourseNo());
		System.out.println(courseVO3.getCourseName());
		System.out.println(courseVO3.getCourseOutline());
		System.out.println(courseVO3.getLesson());
		System.out.println(courseVO3.getStartDate());
		System.out.println(courseVO3.getEndDate());
		System.out.println(courseVO3.getStatus());

		// 查詢
		List<CourseVO> list = dao.getAll();
		for (CourseVO acourse : list) {
			System.out.println(acourse.getCourseNo());
			System.out.println(acourse.getBanjiNo());
			System.out.println(acourse.getTeacherNo());
			System.out.println(acourse.getClassroomNo());
			System.out.println(acourse.getBasicCourseNo());
			System.out.println(acourse.getCourseName());
			System.out.println(acourse.getCourseOutline());
			System.out.println(acourse.getLesson());
			System.out.println(acourse.getStartDate());
			System.out.println(acourse.getEndDate());
			System.out.println(acourse.getStatus());
			System.out.println("===========================");
		}
	}
}