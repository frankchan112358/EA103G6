package com.finalscore.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FinalScoreDAO implements FinalScoreDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO FINALSCORE (FINALSCORENO, COURSENO, STUDENTNO, SCORE) VALUES(FINALSCORE_SEQ.NEXTVAL, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT FINALSCORENO, COURSENO, STUDENTNO, SCORE FROM FINALSCORE ORDER BY FINALSCORENO";
	private static final String GET_ONE_STMT = "SELECT FINALSCORENO, COURSENO, STUDENTNO, SCORE FROM FINALSCORE WHERE FINALSCORENO=?";
	private static final String DELETE = "DELETE FROM FINALSCORE WHERE FINALSCORENO=?";
	private static final String UPDATE = "UPDATE FINALSCORE SET COURSENO=?, STUDENTNO=?, SCORE=? WHERE FINALSCORENO=?";
	private static final String UPDATESCORE = "UPDATE FINALSCORE SET SCORE=? WHERE COURSENO=? AND STUDENTNO=?";

	@Override
	public void insert(FinalScoreVO finalScoreVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, finalScoreVO.getCourseNo());
			pstmt.setString(2, finalScoreVO.getStudentNo());
			pstmt.setInt(3, finalScoreVO.getScore());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, finalScoreVO.getCourseNo());
			pstmt.setString(2, finalScoreVO.getStudentNo());
			pstmt.setInt(3, finalScoreVO.getScore());
			pstmt.setString(4, finalScoreVO.getFinalScoreNo());

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

	public static void updateScore(FinalScoreVO finalScoreVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATESCORE);
			

			pstmt.setInt(1, finalScoreVO.getScore());
			pstmt.setString(2, finalScoreVO.getCourseNo());
			pstmt.setString(3, finalScoreVO.getStudentNo());

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
	public void delete(String finalScoreNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, finalScoreNo);

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
	public FinalScoreVO findByPrimaryKey(String finalScoreNo) {

		FinalScoreVO finalScoreVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
