package com.courseask.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CourseAskJNDIDAO implements CourseAskDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	// 新增
	private static final String INSERT_STMT = "INSERT INTO COURSEASK(COURSEASKNO,COURSENO,STUDENTNO,TITLE,QUESTION,UPDATETIME) VALUES (COURSEASK_SEQ.NEXTVAL,?,?,?,?,?)";
	// 查全部
	private static final String GET_ALL_STMT = "SELECT COURSEASKNO,COURSENO,STUDENTNO,TITLE,QUESTION,UPDATETIME ,STATUS FROM COURSEASK WHERE ISDELETE=0 ORDER BY to_number(COURSEASKNO) DESC";
	// 查單個
	private static final String GET_ONE_STMT = "SELECT COURSEASKNO,COURSENO,STUDENTNO,TITLE,QUESTION,UPDATETIME ,STATUS FROM COURSEASK  WHERE COURSEASKNO =?";
	// 刪除狀態
	private static final String DELETE = "UPDATE COURSEASK SET ISDELETE=1 WHERE COURSEASKNO=?";
	// 修改
	private static final String UPDATE = "UPDATE COURSEASK SET COURSENO=?,STUDENTNO=?,TITLE=?,QUESTION=?,UPDATETIME=?,STATUS=? WHERE COURSEASKNO=?";

	@Override
	public void insert(CourseAskVO courseAskVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, courseAskVO.getCourseNo());
			pstmt.setString(2, courseAskVO.getStudentNo());
			pstmt.setString(3, courseAskVO.getTitle());
			pstmt.setString(4, courseAskVO.getQuestion());
			pstmt.setTimestamp(5, courseAskVO.getUpdateTime());

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
	public void update(CourseAskVO courseAskVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, courseAskVO.getCourseNo());
			pstmt.setString(2, courseAskVO.getStudentNo());
			pstmt.setString(3, courseAskVO.getTitle());
			pstmt.setString(4, courseAskVO.getQuestion());
			pstmt.setTimestamp(5, courseAskVO.getUpdateTime());
			pstmt.setInt(6, courseAskVO.getStatus());
			pstmt.setString(7, courseAskVO.getCourseAskNo());

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
	public void delete(String courseAskNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, courseAskNo);

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
	public CourseAskVO findByPrimaryKey(String courseAskNo) {
		CourseAskVO courseAskVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
				courseAskVO.setStatus(rs.getInt("status"));
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
			con = ds.getConnection();
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
				courseAskVO.setStatus(rs.getInt("status"));

				list.add(courseAskVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
