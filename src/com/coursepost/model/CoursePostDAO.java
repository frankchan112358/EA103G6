package com.coursepost.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CoursePostDAO implements CoursePostDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO COURSEPOST (COURSEPOSTNO, COURSENO, TITLE, POSTCONTENT, UPDATETIME) VALUES(COURSEPOST_SEQ.NEXTVAL, ?, ?, ?, CURRENT_TIMESTAMP)";
	private static final String GET_ALL_STMT = "SELECT COURSEPOSTNO, COURSENO, TITLE, POSTCONTENT, TO_CHAR(UPDATETIME, 'YYYY-MM-DD HH24:MI:SS')UPDATETIME FROM COURSEPOST ORDER BY COURSEPOSTNO DESC";
	private static final String GET_ONE_STMT = "SELECT COURSEPOSTNO, COURSENO, TITLE, POSTCONTENT, TO_CHAR(UPDATETIME, 'YYYY-MM-DD HH24:MI:SS')UPDATETIME FROM COURSEPOST WHERE COURSEPOSTNO=?";
	private static final String DELETE = "DELETE FROM COURSEPOST WHERE COURSEPOSTNO=?";
	private static final String UPDATE = "UPDATE COURSEPOST SET COURSENO=?, TITLE=?, POSTCONTENT=? WHERE COURSEPOSTNO=?";

	@Override
	public void insert(CoursePostVO coursePostVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, coursePostVO.getCourseNo());
			pstmt.setString(2, coursePostVO.getTitle());
			pstmt.setString(3, coursePostVO.getPostContent());

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
	public void update(CoursePostVO coursePostVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, coursePostVO.getCourseNo());
			pstmt.setString(2, coursePostVO.getTitle());
			pstmt.setString(3, coursePostVO.getPostContent());
			pstmt.setString(4, coursePostVO.getCoursePostNo());

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
	public void delete(String coursePostNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, coursePostNo);

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
	public CoursePostVO findByPrimaryKey(String coursePostNo) {

		CoursePostVO coursePostVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
