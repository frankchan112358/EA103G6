package com.evaluation.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EvaluationJDBCDAO implements EvaluationDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO Evaluation (evaluationNo,courseNo,studentNo,question,answer) VALUES (evaluation_seq.nextval,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT evaluationNo,courseNo,studentNo,question,answer FROM Evaluation ORDER BY to_number(evaluationNo)";
	private static final String GET_ONE_STMT = "SELECT evaluationNo,courseNo,studentNo,question,answer FROM Evaluation WHERE evaluationNo = ?";
	private static final String GET_BY_COURSE_STMT = "SELECT evaluationNo,courseNo,studentNo,question,answer FROM Evaluation WHERE courseNo = ? ORDER BY to_number(evaluationNo)";
	private static final String UPDATE = "UPDATE Evaluation SET courseNo=?,studentNo=?,question=?,answer=? WHERE evaluationNo = ?";
	private static final String DELETE = "DELETE FROM Evaluation WHERE evaluationNo = ?";
	private static final String GetEvaluationWithCourseStudent = "SELECT evaluationNo,courseNo,studentNo,question,answer FROM Evaluation WHERE courseNo = ? AND studentNo = ? ORDER BY question";
	private static final String DeleteWithCourseStudent = "DELETE FROM Evaluation WHERE courseNo = ? AND studentNo = ?";
	private static final String GetStudentAddedCourseEvaluation = "SELECT DISTINCT courseNo FROM Evaluation WHERE studentNo = ? ORDER BY courseNo";

	@Override
	public void insert(EvaluationVO evaluationVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, evaluationVO.getCourseNo());
			pstmt.setString(2, evaluationVO.getStudentNo());
			pstmt.setInt(3, evaluationVO.getQuestion());
			pstmt.setInt(4, evaluationVO.getAnswer());

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
	public void update(EvaluationVO evaluationVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, evaluationVO.getCourseNo());
			pstmt.setString(2, evaluationVO.getStudentNo());
			pstmt.setInt(3, evaluationVO.getQuestion());
			pstmt.setInt(4, evaluationVO.getAnswer());
			pstmt.setString(5, evaluationVO.getEvaluationNo());
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
	public void delete(String evaluationNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, evaluationNo);
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
	public EvaluationVO findByPrimaryKey(String evaluationNo) {
		EvaluationVO evaluationVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Clouldn't load database driver. " + e.getMessage());
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	public List<EvaluationVO> getAll() {
		List<EvaluationVO> list = new ArrayList<EvaluationVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Coulen't load database driver. " + e.getMessage());
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

	public List<EvaluationVO> getEvaluationWithCourseStudent(String courseNo, String studentNo) {
		List<EvaluationVO> list = new ArrayList<EvaluationVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GetEvaluationWithCourseStudent);
			pstmt.setString(1, courseNo);
			pstmt.setString(2, studentNo);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Coulen't load database driver. " + e.getMessage());
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

	public void deleteWithCourseStudent(String courseNo, String studentNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DeleteWithCourseStudent);
			pstmt.setString(1, courseNo);
			pstmt.setString(2, studentNo);
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

	public List<String> getStudentAddedCourseEvaluation(String studentNo) {
		List<String> list = new ArrayList<String>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GetStudentAddedCourseEvaluation);
			pstmt.setString(1, studentNo);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("courseNo"));
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
		EvaluationJDBCDAO dao = new EvaluationJDBCDAO();

//		EvaluationVO evaluationVO1 = new EvaluationVO();
//		evaluationVO1.setCourseNo("C002");
//		evaluationVO1.setStudentNo("S000001");
//		evaluationVO1.setQuestion(1);
//		evaluationVO1.setAnswer(7);
//		dao.insert(evaluationVO1);
//
//		EvaluationVO evaluationVO2 = new EvaluationVO();
//		evaluationVO2.setEvaluationNo("2");
//		evaluationVO2.setCourseNo("C001");
//		evaluationVO2.setStudentNo("S000002");
//		evaluationVO2.setQuestion(2);
//		evaluationVO2.setAnswer(2);
//		dao.update(evaluationVO2);
//
//		dao.delete("3");
//
//		EvaluationVO evaluationVO3 = dao.findByPrimaryKey("4");
//		if (evaluationVO3 != null) {
//			System.out.printf("%s,%s,%s,%s,%s%n", evaluationVO3.getEvaluationNo(), evaluationVO3.getCourseNo(),
//					evaluationVO3.getStudentNo(), evaluationVO3.getQuestion(), evaluationVO3.getAnswer());
//			System.out.println("---------------------");
//		}
//
//		List<EvaluationVO> list_cours = dao.findByCourseNo("C001");
//		for (EvaluationVO evaluationVO : list_cours) {
//			System.out.printf("%s,%s,%s,%s,%s%n", evaluationVO.getEvaluationNo(), evaluationVO.getCourseNo(),
//					evaluationVO.getStudentNo(), evaluationVO.getQuestion(), evaluationVO.getAnswer());
//		}
//		System.out.println("---------------------");
//
//		List<EvaluationVO> list = dao.getAll();
//		for (EvaluationVO evaluationVO : list) {
//			System.out.printf("%s,%s,%s,%s,%s%n", evaluationVO.getEvaluationNo(), evaluationVO.getCourseNo(),
//					evaluationVO.getStudentNo(), evaluationVO.getQuestion(), evaluationVO.getAnswer());
//		}
//		System.out.println("---------------------");
//
//		List<EvaluationVO> list2 = dao.getEvaluationWithCourseStudent("C001", "S000001");
//		for (EvaluationVO evaluationVO : list2) {
//			System.out.printf("%s,%s,%s,%s,%s%n", evaluationVO.getEvaluationNo(), evaluationVO.getCourseNo(),
//					evaluationVO.getStudentNo(), evaluationVO.getQuestion(), evaluationVO.getAnswer());
//		}
		System.out.println("---------------------");

		dao.deleteWithCourseStudent("C001", "S000001");
	}
}
