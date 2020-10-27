package com.teachingplan.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TeachingPlanDAO implements TeachingPlanDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO TEACHINGPLAN (TEACHINGPLANNO, COURSENO, WEEK, LESSON, PLANCONTENT) VALUES(TEACHINGPLAN_SEQ.NEXTVAL, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT TEACHINGPLANNO, COURSENO, WEEK, LESSON, PLANCONTENT FROM TEACHINGPLAN ORDER BY TEACHINGPLANNO";
	private static final String GET_ONE_STMT = "SELECT TEACHINGPLANNO, COURSENO, WEEK, LESSON, PLANCONTENT FROM TEACHINGPLAN WHERE TEACHINGPLANNO=?";
	private static final String DELETE = "DELETE FROM TEACHINGPLAN WHERE TEACHINGPLANNO=?";
	private static final String UPDATE = "UPDATE TEACHINGPLAN SET COURSENO=?, WEEK=?, LESSON=?, PLANCONTENT=? WHERE TEACHINGPLANNO=?";
	private static final String GET_TEACHINGPLAN_BY_COURSENO = "SELECT TEACHINGPLANNO, COURSENO, WEEK, LESSON, PLANCONTENT FROM TEACHINGPLAN WHERE COURSENO =?";

	@Override
	public void insert(TeachingPlanVO teachingPlanVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, teachingPlanVO.getCourseNo());
			pstmt.setInt(2, teachingPlanVO.getWeek());
			pstmt.setInt(3, teachingPlanVO.getLesson());
			pstmt.setString(4, teachingPlanVO.getPlanContent());

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
	public void update(TeachingPlanVO teachingPlanVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, teachingPlanVO.getCourseNo());
			pstmt.setInt(2, teachingPlanVO.getWeek());
			pstmt.setInt(3, teachingPlanVO.getLesson());
			pstmt.setString(4, teachingPlanVO.getPlanContent());
			pstmt.setString(5, teachingPlanVO.getTeachingPlanNo());

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
	public void delete(String teachingPlanNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, teachingPlanNo);

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
	public TeachingPlanVO findByPrimaryKey(String teachingPlanNo) {

		TeachingPlanVO teachingPlanVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, teachingPlanNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				teachingPlanVO = new TeachingPlanVO();
				teachingPlanVO.setTeachingPlanNo(rs.getString("teachingPlanNo"));
				teachingPlanVO.setCourseNo(rs.getString("courseNo"));
				teachingPlanVO.setWeek(rs.getInt("week"));
				teachingPlanVO.setLesson(rs.getInt("lesson"));
				teachingPlanVO.setPlanContent(rs.getString("planContent"));
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
		return teachingPlanVO;
	}

	@Override
	public List<TeachingPlanVO> getAll() {

		List<TeachingPlanVO> list = new ArrayList<TeachingPlanVO>();
		TeachingPlanVO teachingPlanVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				teachingPlanVO = new TeachingPlanVO();
				teachingPlanVO.setTeachingPlanNo(rs.getString("teachingPlanNo"));
				teachingPlanVO.setCourseNo(rs.getString("courseNo"));
				teachingPlanVO.setWeek(rs.getInt("week"));
				teachingPlanVO.setLesson(rs.getInt("lesson"));
				teachingPlanVO.setPlanContent(rs.getString("planContent"));
				list.add(teachingPlanVO);
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

	@Override
	public List<TeachingPlanVO> getTeachingPlanByCourseNo(String courseNo) {

		List<TeachingPlanVO> list = new ArrayList<TeachingPlanVO>();
		TeachingPlanVO teachingPlanVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_TEACHINGPLAN_BY_COURSENO);
			pstmt.setString(1, courseNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				teachingPlanVO = new TeachingPlanVO();
				teachingPlanVO.setTeachingPlanNo(rs.getString("teachingPlanNo"));
				teachingPlanVO.setWeek(rs.getInt("week"));
				teachingPlanVO.setLesson(rs.getInt("lesson"));
				teachingPlanVO.setPlanContent(rs.getString("planContent"));
				teachingPlanVO.setCourseNo(rs.getString("courseNo"));
				list.add(teachingPlanVO);

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
