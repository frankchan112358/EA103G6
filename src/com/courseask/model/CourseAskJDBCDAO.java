package com.courseask.model;

import java.sql.*;
import java.util.*;

public class CourseAskJDBCDAO implements CourseAskDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	// 新增
	private static final String INSERT_STMT = "INSERT INTO COURSEASK(COURSEASKNO,COURSENO,STUDENTNO,TITLE,QUESTION,UPDATETIME) VALUES (COURSEASK_SEQ.NEXTVAL,?,?,?,?,?)";
	// 查全部
	private static final String GET_ALL_STMT = "SELECT COURSEASKNO,COURSENO,STUDENTNO,TITLE,QUESTION,UPDATETIME FROM COURSEASK WHERE ISDELETE=0 ORDER BY COURSEASKNO";
	// 查單個
	private static final String GET_ONE_STMT = "SELECT COURSEASKNO,COURSENO,STUDENTNO,TITLE,QUESTION,UPDATETIME FROM COURSEASK  WHERE COURSEASKNO =?";
	// 刪除狀態
	private static final String DELETE = "UPDATE COURSEASK SET ISDELETE=1 WHERE COURSEASKNO=?";
	// 修改
	private static final String UPDATE = "UPDATE COURSEASK SET COURSENO=?,STUDENTNO=?,TITLE=?,QUESTION=?,UPDATETIME=?,STATUS=? WHERE COURSEASKNO=?";

	@Override
	public void insert(CourseAskVO courseAskVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, courseAskVO.getCourseNo());
			pstmt.setString(2, courseAskVO.getStudentNo());
			pstmt.setString(3, courseAskVO.getTitle());
			pstmt.setString(4, courseAskVO.getQuestion());
			pstmt.setTimestamp(5, courseAskVO.getUpdateTime());

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
	public void update(CourseAskVO courseAskVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, courseAskVO.getCourseNo());
			pstmt.setString(2, courseAskVO.getStudentNo());
			pstmt.setString(3, courseAskVO.getTitle());
			pstmt.setString(4, courseAskVO.getQuestion());
			pstmt.setTimestamp(5, courseAskVO.getUpdateTime());
			pstmt.setInt(6, courseAskVO.getStatus());
			pstmt.setString(7, courseAskVO.getCourseAskNo());

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
	public void delete(String courseAskNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, courseAskNo);

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
	public CourseAskVO findByPrimaryKey(String courseAskNo) {
		CourseAskVO courseAskVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, courseAskNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				courseAskVO = new CourseAskVO();
				courseAskVO.setCourseAskNo(rs.getString("courseAskNo"));
				courseAskVO.setCourseNo(rs.getString("courseNo"));
				courseAskVO.setStudentNo(rs.getString("studentNo"));
				courseAskVO.setTitle(rs.getString("title"));
				courseAskVO.setQuestion(rs.getString("question"));
				courseAskVO.setUpdateTime(rs.getTimestamp("updateTime"));
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
		return courseAskVO;
	}

	@Override
	public List<CourseAskVO> getAll() {
		List<CourseAskVO> list = new ArrayList<CourseAskVO>();
		CourseAskVO courseAskVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				courseAskVO = new CourseAskVO();
				courseAskVO.setCourseAskNo(rs.getString("courseAskNo"));
				courseAskVO.setCourseNo(rs.getString("courseNo"));
				courseAskVO.setStudentNo(rs.getString("studentNo"));
				courseAskVO.setTitle(rs.getString("title"));
				courseAskVO.setQuestion(rs.getString("question"));
				courseAskVO.setUpdateTime(rs.getTimestamp("updateTime"));

				list.add(courseAskVO);
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

		CourseAskJDBCDAO dao = new CourseAskJDBCDAO();

		// 新增
		CourseAskVO courseAskVO1 = new CourseAskVO();
		courseAskVO1.setCourseNo("C001");
		courseAskVO1.setStudentNo("S000002");
		courseAskVO1.setTitle("JAVA命名疑慮");
		courseAskVO1.setQuestion("類別開頭大寫，方法小寫嗎??");
		courseAskVO1.setUpdateTime(new java.sql.Timestamp((new java.util.Date()).getTime()));
		courseAskVO1.setStatus(0);

		dao.insert(courseAskVO1);

		// 查詢
		CourseAskVO courseAskVO2 = dao.findByPrimaryKey("1");
		System.out.println(courseAskVO2.getCourseAskNo() + ",");
		System.out.println(courseAskVO2.getCourseNo() + ",");
		System.out.println(courseAskVO2.getStudentNo() + ",");
		System.out.println(courseAskVO2.getTitle() + ",");
		System.out.println(courseAskVO2.getQuestion() + ",");
		System.out.println(courseAskVO2.getUpdateTime());
		System.out.println("--------------------------");

		// 查詢
		List<CourseAskVO> list = dao.getAll();
		for (CourseAskVO aCourseAskVO : list) {
			System.out.println(aCourseAskVO.getCourseAskNo() + ",");
			System.out.println(aCourseAskVO.getCourseNo() + ",");
			System.out.println(aCourseAskVO.getStudentNo() + ",");
			System.out.println(aCourseAskVO.getTitle() + ",");
			System.out.println(aCourseAskVO.getQuestion() + ",");
			System.out.println(aCourseAskVO.getUpdateTime());
			System.out.println("--------------------------");
		}

		// 刪除
//		dao.delete("1");

		// 修改
		CourseAskVO courseAskVO3 = new CourseAskVO();
		courseAskVO3.setCourseNo("C001");
		courseAskVO3.setStudentNo("S000001");
		courseAskVO3.setTitle("JAVA命名疑慮");
		courseAskVO3.setQuestion("類別開頭大寫，方法小寫嗎??");
		courseAskVO3.setUpdateTime(new java.sql.Timestamp((new java.util.Date()).getTime()));
		courseAskVO3.setStatus(1);
		courseAskVO3.setCourseAskNo("1");
		dao.update(courseAskVO3);
	}
}