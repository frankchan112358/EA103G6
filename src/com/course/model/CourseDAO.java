package com.course.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CourseDAO implements CourseDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO COURSE (COURSENO, BANJINO, TEACHERNO, CLASSROOMNO, BASICCOURSENO, COURSENAME, COURSEOUTLINE, LESSON, STARTDATE, ENDDATE, STATUS) VALUES('C'||LPAD(to_char(COURSE_SEQ.NEXTVAL), '3', '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT COURSENO, BANJINO, TEACHERNO, CLASSROOMNO, BASICCOURSENO, COURSENAME, COURSEOUTLINE, LESSON, TO_CHAR(STARTDATE,'YYYY-MM-DD') STARTDATE, TO_CHAR(ENDDATE,'YYYY-MM-DD') ENDDATE, STATUS FROM COURSE WHERE ISDELETE=0 ORDER BY COURSENO";
	private static final String GET_ONE_STMT = "SELECT COURSENO, BANJINO, TEACHERNO, CLASSROOMNO, BASICCOURSENO, COURSENAME, COURSEOUTLINE, LESSON, TO_CHAR(STARTDATE,'YYYY-MM-DD') STARTDATE, TO_CHAR(ENDDATE,'YYYY-MM-DD') ENDDATE, STATUS FROM COURSE WHERE COURSENO =?";
	private static final String DELETE = "UPDATE COURSE SET ISDELETE=1 WHERE COURSENO=?";
	private static final String UPDATE = "UPDATE COURSE SET BANJINO=?, TEACHERNO=?, CLASSROOMNO=?, BASICCOURSENO=?, COURSENAME=?, COURSEOUTLINE=?, LESSON=?, STARTDATE=?, ENDDATE=?, STATUS=? WHERE COURSENO=?";

	@Override
	public void insert(CourseVO courseVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, courseNo);

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
	public CourseVO findByPrimaryKey(String courseNo) {

		CourseVO courseVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
}