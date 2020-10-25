package com.coursepost.model;

import java.sql.*;
import java.util.*;

public class CoursePostJDBCDAO implements CoursePostDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO COURSEPOST (COURSEPOSTNO, COURSENO, TITLE, POSTCONTENT, UPDATETIME) VALUES(COURSEPOST_SEQ.NEXTVAL, ?, ?, ?, CURRENT_TIMESTAMP)";
	private static final String GET_ALL_STMT = "SELECT COURSEPOSTNO, COURSENO, TITLE, POSTCONTENT, TO_CHAR(UPDATETIME, 'YYYY-MM-DD HH24:MI:SS')UPDATETIME FROM COURSEPOST ORDER BY COURSEPOSTNO DESC";
	private static final String GET_ONE_STMT = "SELECT COURSEPOSTNO, COURSENO, TITLE, POSTCONTENT, TO_CHAR(UPDATETIME, 'YYYY-MM-DD HH24:MI:SS')UPDATETIME FROM COURSEPOST WHERE COURSEPOSTNO=?";
	private static final String DELETE = "DELETE FROM COURSEPOST WHERE COURSEPOSTNO=?";
	private static final String UPDATE = "UPDATE COURSEPOST SET COURSENO=?, TITLE=?, POSTCONTENT=? WHERE COURSEPOSTNO=?";
	private static final String GET_COURSEPOST_BY_COURSENO = "SELECT COURSEPOSTNO, COURSENO, TITLE, POSTCONTENT, TO_CHAR(UPDATETIME,'YYYY-MM-DD HH24:MI:SS') UPDATETIME FROM COURSEPOST WHERE COURSENO =?";

	
	@Override
	public void insert(CoursePostVO coursePostVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, coursePostVO.getCourseNo());
			pstmt.setString(2, coursePostVO.getTitle());
			pstmt.setString(3, coursePostVO.getPostContent());

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
	public void update(CoursePostVO coursePostVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, coursePostVO.getCourseNo());
			pstmt.setString(2, coursePostVO.getTitle());
			pstmt.setString(3, coursePostVO.getPostContent());
			pstmt.setString(4, coursePostVO.getCoursePostNo());

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
	public void delete(String coursePostNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, coursePostNo);

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
	public CoursePostVO findByPrimaryKey(String coursePostNo) {

		CoursePostVO coursePostVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, coursePostNo);

			rs = pstmt.executeQuery();
 
			while (rs.next()) {
				coursePostVO = new CoursePostVO();
				coursePostVO.setCoursePostNo(rs.getString("coursePostNo"));
				coursePostVO.setCourseNo(rs.getString("courseNo"));
				coursePostVO.setTitle(rs.getString("title"));
				coursePostVO.setPostContent(rs.getString("postContent"));
				coursePostVO.setUpdateTime(rs.getTimestamp("updateTime"));
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
		return coursePostVO;
	}

	@Override
	public List<CoursePostVO> getAll() {

		List<CoursePostVO> list = new ArrayList<CoursePostVO>();
		CoursePostVO coursePostVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				coursePostVO = new CoursePostVO();
				coursePostVO.setCoursePostNo(rs.getString("coursePostNo"));
				coursePostVO.setCourseNo(rs.getString("courseNo"));
				coursePostVO.setTitle(rs.getString("title"));
				coursePostVO.setPostContent(rs.getString("postContent"));
				coursePostVO.setUpdateTime(rs.getTimestamp("updateTime"));
				list.add(coursePostVO);
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
	
	@Override
	public List<CoursePostVO> getCoursePostByCourseNo(String courseNo) {
		List<CoursePostVO> list = new ArrayList<CoursePostVO>();
		CoursePostVO coursePostVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_COURSEPOST_BY_COURSENO);
			pstmt.setString(1, courseNo);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				coursePostVO = new CoursePostVO();
				coursePostVO.setCoursePostNo(rs.getString("coursePostNo"));
				coursePostVO.setTitle(rs.getString("title"));
				coursePostVO.setPostContent(rs.getString("postContent"));
				coursePostVO.setUpdateTime(rs.getTimestamp("updateTime"));
				coursePostVO.setCourseNo("courseNo");
				list.add(coursePostVO); // Store the row in the vector
			}
	
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

		CoursePostJDBCDAO dao = new CoursePostJDBCDAO();

		// 新增
		CoursePostVO coursePostVO1 = new CoursePostVO();
		coursePostVO1.setCourseNo("C001");
		coursePostVO1.setTitle("JAVASCRIPT線上考試");
		coursePostVO1.setPostContent("範圍1~4章,close課本");
		dao.insert(coursePostVO1);

		// 修改
		CoursePostVO coursePostVO2 = new CoursePostVO();
		coursePostVO2.setCoursePostNo("2");
		coursePostVO2.setCourseNo("C001");
		coursePostVO2.setTitle("RWD線上測驗");
		coursePostVO2.setPostContent("範圍5~10章,open課本");
		dao.update(coursePostVO2);

		// 刪除
		dao.delete("3");

		// 查詢
		CoursePostVO coursePostVO3 = dao.findByPrimaryKey("4");
		System.out.println(coursePostVO3.getCoursePostNo());
		System.out.println(coursePostVO3.getCourseNo());
		System.out.println(coursePostVO3.getTitle());
		System.out.println(coursePostVO3.getPostContent());
		System.out.println(coursePostVO3.getUpdateTime());

		// 查詢
		List<CoursePostVO> list = dao.getAll();
		for (CoursePostVO acoursePost : list) {
			System.out.println(acoursePost.getCoursePostNo());
			System.out.println(acoursePost.getCourseNo());
			System.out.println(acoursePost.getTitle());
			System.out.println(acoursePost.getPostContent());
			System.out.println(acoursePost.getUpdateTime());
			System.out.println("===================================");
		}
	}
	
}
