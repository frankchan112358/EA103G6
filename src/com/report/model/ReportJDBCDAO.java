package com.report.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class ReportJDBCDAO implements ReportDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO report (reportno,forumpostno, forumcommentno, studentno, type, description , reporttime) VALUES (report_seq.NEXTVAL,?, ?,?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT reportno,forumpostno, forumcommentno, studentno, type, description , reporttime FROM report where status = 0 order by to_number(reportno)";
	private static final String GET_ONE_STMT = "SELECT reportno,forumpostno, forumcommentno, studentno, type, description , reporttime FROM report where reportno = ?";
	private static final String DELETE = "UPDATE report set status=1 where reportno = ?";
	private static final String UPDATE = "UPDATE report set forumpostno=?, forumcommentno=?, studentno=?, type=?, description=?, reporttime=? where reportno = ?";

	@Override
	public void insert(ReportVO reportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, reportVO.getForumPostNo());
			pstmt.setString(2, reportVO.getForumCommentNo());
			pstmt.setString(3, reportVO.getStudentNo());
			pstmt.setInt(4, reportVO.getType());
			pstmt.setString(5, reportVO.getDescription());
			pstmt.setTimestamp(6, reportVO.getReportTime());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
	public void update(ReportVO reportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, reportVO.getForumPostNo());
			pstmt.setString(2, reportVO.getForumCommentNo());
			pstmt.setString(3, reportVO.getStudentNo());
			pstmt.setInt(4, reportVO.getType());
			pstmt.setString(5, reportVO.getDescription());
			pstmt.setTimestamp(6, reportVO.getReportTime());
			pstmt.setString(7, reportVO.getReportNo());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
	public void delete(String reportNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, reportNo);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
	public ReportVO findByPrimaryKey(String reportNo) {
		ReportVO reportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, reportNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				reportVO = new ReportVO();
				reportVO.setReportNo(rs.getString("reportNo"));
				reportVO.setForumPostNo(rs.getString("forumPostNo"));
				reportVO.setForumCommentNo(rs.getString("forumCommentNo"));
				reportVO.setStudentNo(rs.getString("studentNo"));
				reportVO.setType(rs.getInt("type"));
				reportVO.setDescription(rs.getString("description"));
				reportVO.setReportTime(rs.getTimestamp("reportTime"));

			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
		return reportVO;

	}

	@Override
	public List<ReportVO> getAll() {
		List<ReportVO> list = new ArrayList<ReportVO>();
		ReportVO reportVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				reportVO = new ReportVO();
				reportVO.setReportNo(rs.getString("reportNo"));
				reportVO.setForumPostNo(rs.getString("forumPostNo"));
				reportVO.setForumCommentNo(rs.getString("forumCommentNo"));
				reportVO.setStudentNo(rs.getString("studentNo"));
				reportVO.setType(rs.getInt("type"));
				reportVO.setDescription(rs.getString("description"));
				reportVO.setReportTime(rs.getTimestamp("reportTime"));
				list.add(reportVO);
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
		ReportJDBCDAO dao = new ReportJDBCDAO();

		ReportVO reportVO1 = new ReportVO();
		reportVO1.setReportNo("1");
		reportVO1.setForumPostNo("1");
		reportVO1.setForumCommentNo("");
		reportVO1.setStudentNo("S000001");
		reportVO1.setType(0);
		reportVO1.setDescription("自己的貼文自己檢舉");
		reportVO1.setReportTime(new java.sql.Timestamp((new java.util.Date()).getTime()));
		dao.insert(reportVO1);

		ReportVO reportVO2 = new ReportVO();
		reportVO2.setReportNo("2");
		reportVO2.setForumPostNo("2");
		reportVO2.setForumCommentNo("");
		reportVO2.setStudentNo("S000002");
		reportVO2.setType(1);
		reportVO2.setDescription("涉及暴力等字言");
		reportVO2.setReportTime(new java.sql.Timestamp((new java.util.Date()).getTime()));
		dao.update(reportVO2);

		dao.delete("1");

		ReportVO reportVO3 = dao.findByPrimaryKey("1");
		System.out.print(reportVO3.getReportNo() + ",");
		System.out.print(reportVO3.getForumPostNo() + ",");
		System.out.print(reportVO3.getForumCommentNo() + ",");
		System.out.print(reportVO3.getStudentNo() + ",");
		System.out.print(reportVO3.getType() + ",");
		System.out.print(reportVO3.getDescription() + ",");
		System.out.print(reportVO3.getReportTime() + ",");
		System.out.println("---------------------");

		List<ReportVO> list = dao.getAll();
		for (ReportVO aReport : list) {
			System.out.print(aReport.getReportNo() + ",");
			System.out.print(aReport.getForumPostNo() + ",");
			System.out.print(aReport.getForumCommentNo() + ",");
			System.out.print(aReport.getStudentNo() + ",");
			System.out.print(aReport.getType() + ",");
			System.out.print(aReport.getDescription() + ",");
			System.out.print(aReport.getReportTime() + ",");
			System.out.println();
		}
	}

}
