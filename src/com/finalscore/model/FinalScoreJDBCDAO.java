package com.finalscore.model;

import java.sql.*;
import java.util.*;

public class FinalScoreJDBCDAO implements FinalScoreDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO FINALSCORE (FINALSCORENO, COURSENO, STUDENTNO, SCORE) VALUES(FINALSCORE_SEQ.NEXTVAL, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT FINALSCORENO, COURSENO, STUDENTNO, SCORE FROM FINALSCORE ORDER BY FINALSCORENO";
	private static final String GET_ONE_STMT = "SELECT FINALSCORENO, COURSENO, STUDENTNO, SCORE FROM FINALSCORE WHERE FINALSCORENO=?";
	private static final String DELETE = "DELETE FROM FINALSCORE WHERE FINALSCORENO=?";
	private static final String UPDATE = "UPDATE FINALSCORE SET COURSENO=?, STUDENTNO=?, SCORE=? WHERE FINALSCORENO=?";

	@Override
	public void insert(FinalScoreVO finalScoreVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, finalScoreVO.getCourseNo());
			pstmt.setString(2, finalScoreVO.getStudentNo());
			pstmt.setInt(3, finalScoreVO.getScore());

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
	public void update(FinalScoreVO finalScoreVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, finalScoreVO.getCourseNo());
			pstmt.setString(2, finalScoreVO.getStudentNo());
			pstmt.setInt(3, finalScoreVO.getScore());
			pstmt.setString(4, finalScoreVO.getFinalScoreNo());

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
	public void delete(String finalScoreNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, finalScoreNo);

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
	public FinalScoreVO findByPrimaryKey(String finalScoreNo) {

		FinalScoreVO finalScoreVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, finalScoreNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				finalScoreVO = new FinalScoreVO();
				finalScoreVO.setFinalScoreNo(rs.getString("finalScoreNo"));
				finalScoreVO.setCourseNo(rs.getString("courseNo"));
				finalScoreVO.setStudentNo(rs.getString("studentNo"));
				finalScoreVO.setScore(rs.getInt("score"));

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
		return finalScoreVO;
	}

	@Override
	public List<FinalScoreVO> getAll() {

		List<FinalScoreVO> list = new ArrayList<FinalScoreVO>();
		FinalScoreVO finalScoreVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				finalScoreVO = new FinalScoreVO();
				finalScoreVO.setFinalScoreNo(rs.getString("finalScoreNo"));
				finalScoreVO.setCourseNo(rs.getString("courseNo"));
				finalScoreVO.setStudentNo(rs.getString("studentNo"));
				finalScoreVO.setScore(rs.getInt("score"));
				list.add(finalScoreVO);
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

		FinalScoreJDBCDAO dao = new FinalScoreJDBCDAO();

		// 新增
		FinalScoreVO finalScoreVO1 = new FinalScoreVO();
		finalScoreVO1.setCourseNo("C001");
		finalScoreVO1.setStudentNo("S000001");
		finalScoreVO1.setScore(98);
		dao.insert(finalScoreVO1);

		// 修改
		FinalScoreVO finalScoreVO2 = new FinalScoreVO();
		finalScoreVO2.setFinalScoreNo("2");
		finalScoreVO2.setCourseNo("C002");
		finalScoreVO2.setStudentNo("S000002");
		finalScoreVO2.setScore(95);
		dao.update(finalScoreVO2);

		// 刪除
		dao.delete("4");

		// 查詢
		FinalScoreVO finalScoreVO3 = dao.findByPrimaryKey("3");
		System.out.println(finalScoreVO3.getFinalScoreNo());
		System.out.println(finalScoreVO3.getCourseNo());
		System.out.println(finalScoreVO3.getStudentNo());
		System.out.println(finalScoreVO3.getScore());

		// 查詢
		List<FinalScoreVO> list = dao.getAll();
		for (FinalScoreVO afinalScore : list) {
			System.out.println(afinalScore.getFinalScoreNo());
			System.out.println(afinalScore.getCourseNo());
			System.out.println(afinalScore.getStudentNo());
			System.out.println(afinalScore.getScore());
			System.out.println("===========================");
		}
	}
}