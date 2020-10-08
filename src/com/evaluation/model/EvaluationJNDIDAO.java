package com.evaluation.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EvaluationJNDIDAO implements EvaluationDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO Evaluation (evaluationNo,courseNo,studentNo,question,answer) VALUES (evaluation_seq.nextval,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT evaluationNo,courseNo,studentNo,question,answer FROM Evaluation ORDER BY to_number(evaluationNo)";
	private static final String GET_ONE_STMT = "SELECT evaluationNo,courseNo,studentNo,question,answer FROM Evaluation WHERE evaluationNo = ?";
	private static final String GET_BY_COURSE_STMT = "SELECT evaluationNo,courseNo,studentNo,question,answer FROM Evaluation WHERE courseNo = ? ORDER BY to_number(evaluationNo)";
	private static final String UPDATE = "UPDATE Evaluation SET courseNo=?,studentNo=?,question=?,answer=? WHERE evaluationNo = ?";
	private static final String DELETE = "DELETE FROM Evaluation WHERE evaluationNo = ?";

	@Override
	public void insert(EvaluationVO evaluationVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, evaluationVO.getCourseNo());
			pstmt.setString(2, evaluationVO.getStudentNo());
			pstmt.setInt(3, evaluationVO.getQuestion());
			pstmt.setInt(4, evaluationVO.getAnswer());
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
	public void update(EvaluationVO evaluationVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, evaluationVO.getCourseNo());
			pstmt.setString(2, evaluationVO.getStudentNo());
			pstmt.setInt(3, evaluationVO.getQuestion());
			pstmt.setInt(4, evaluationVO.getAnswer());
			pstmt.setString(5, evaluationVO.getEvaluationNo());
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
	public void delete(String evaluationNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, evaluationNo);
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
	public EvaluationVO findByPrimaryKey(String evaluationNo) {
		EvaluationVO evaluationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, evaluationNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				evaluationVO = new EvaluationVO();
				evaluationVO.setEvaluationNo(rs.getString("evaluationNo"));
				evaluationVO.setCourseNo(rs.getString("courseNo"));
				evaluationVO.setStudentNo(rs.getString("studentNo"));
				evaluationVO.setQuestion(rs.getInt("question"));
				evaluationVO.setAnswer(rs.getInt("answer"));
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
		return evaluationVO;
	}

	@Override
	public List<EvaluationVO> findByCourseNo(String courseNo) {
		List<EvaluationVO> list = new ArrayList<EvaluationVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_COURSE_STMT);
			pstmt.setString(1, courseNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				EvaluationVO evaluationVO = new EvaluationVO();
				evaluationVO.setEvaluationNo(rs.getString("evaluationNo"));
				evaluationVO.setCourseNo(rs.getString("courseNo"));
				evaluationVO.setStudentNo(rs.getString("studentNo"));
				evaluationVO.setQuestion(rs.getInt("question"));
				evaluationVO.setAnswer(rs.getInt("answer"));
				list.add(evaluationVO);
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
	public List<EvaluationVO> getAll() {
		List<EvaluationVO> list = new ArrayList<EvaluationVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				EvaluationVO evaluationVO = new EvaluationVO();
				evaluationVO.setEvaluationNo(rs.getString("evaluationNo"));
				evaluationVO.setCourseNo(rs.getString("courseNo"));
				evaluationVO.setStudentNo(rs.getString("studentNo"));
				evaluationVO.setQuestion(rs.getInt("question"));
				evaluationVO.setAnswer(rs.getInt("answer"));
				list.add(evaluationVO);
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
