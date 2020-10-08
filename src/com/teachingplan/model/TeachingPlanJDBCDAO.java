package com.teachingplan.model;

import java.sql.*;
import java.util.*;

public class TeachingPlanJDBCDAO implements TeachingPlanDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO TEACHINGPLAN (TEACHINGPLANNO, COURSENO, WEEK, LESSON, PLANCONTENT) VALUES(TEACHINGPLAN_SEQ.NEXTVAL, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT TEACHINGPLANNO, COURSENO, WEEK, LESSON, PLANCONTENT FROM TEACHINGPLAN ORDER BY TEACHINGPLANNO";
	private static final String GET_ONE_STMT = "SELECT TEACHINGPLANNO, COURSENO, WEEK, LESSON, PLANCONTENT FROM TEACHINGPLAN WHERE TEACHINGPLANNO=?";
	private static final String DELETE = "DELETE FROM TEACHINGPLAN WHERE TEACHINGPLANNO=?";
	private static final String UPDATE = "UPDATE TEACHINGPLAN SET COURSENO=?, WEEK=?, LESSON=?, PLANCONTENT=? WHERE TEACHINGPLANNO=?";

	@Override
	public void insert(TeachingPlanVO teachingPlanVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, teachingPlanVO.getCourseNo());
			pstmt.setInt(2, teachingPlanVO.getWeek());
			pstmt.setInt(3, teachingPlanVO.getLesson());
			pstmt.setString(4, teachingPlanVO.getPlanContent());

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
	public void update(TeachingPlanVO teachingPlanVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, teachingPlanVO.getCourseNo());
			pstmt.setInt(2, teachingPlanVO.getWeek());
			pstmt.setInt(3, teachingPlanVO.getLesson());
			pstmt.setString(4, teachingPlanVO.getPlanContent());
			pstmt.setString(5, teachingPlanVO.getTeachingPlanNo());

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
	public void delete(String teachingPlanNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, teachingPlanNo);

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
	public TeachingPlanVO findByPrimaryKey(String teachingPlanNo) {

		TeachingPlanVO teachingPlanVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		TeachingPlanJDBCDAO dao = new TeachingPlanJDBCDAO();

		// 新增
		TeachingPlanVO teachingPlanVO1 = new TeachingPlanVO();
		teachingPlanVO1.setCourseNo("C002");
		teachingPlanVO1.setWeek(3);
		teachingPlanVO1.setLesson(4);
		teachingPlanVO1.setPlanContent("HTML介紹...etc");
		dao.insert(teachingPlanVO1);

		// 修改
		TeachingPlanVO teachingPlanVO2 = new TeachingPlanVO();
		teachingPlanVO2.setTeachingPlanNo("4");
		teachingPlanVO2.setCourseNo("C001");
		teachingPlanVO2.setWeek(4);
		teachingPlanVO2.setLesson(3);
		teachingPlanVO2.setPlanContent("AWS概論...etc");
		dao.update(teachingPlanVO2);

		// 刪除
		dao.delete("4");

		// 查詢
		TeachingPlanVO teachingPlanVO3 = dao.findByPrimaryKey("3");
		System.out.println(teachingPlanVO3.getTeachingPlanNo());
		System.out.println(teachingPlanVO3.getCourseNo());
		System.out.println(teachingPlanVO3.getWeek());
		System.out.println(teachingPlanVO3.getLesson());
		System.out.println(teachingPlanVO3.getPlanContent());

		// 查詢
		List<TeachingPlanVO> list = dao.getAll();
		for (TeachingPlanVO ateachingPlan : list) {
			System.out.println(ateachingPlan.getTeachingPlanNo());
			System.out.println(ateachingPlan.getCourseNo());
			System.out.println(ateachingPlan.getWeek());
			System.out.println(ateachingPlan.getLesson());
			System.out.println(ateachingPlan.getPlanContent());
			System.out.println("===========================");
		}
	}
}
