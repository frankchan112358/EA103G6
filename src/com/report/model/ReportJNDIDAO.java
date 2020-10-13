package com.report.model;

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



public class ReportJNDIDAO implements ReportDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO report (reportno,forumpostno, forumcommentno, studentno, type, description , reporttime,status) VALUES (report_seq.NEXTVAL,?, ?,?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT reportno,forumpostno, forumcommentno, studentno, type, description , reporttime ,status FROM report order by to_number(reportno)";
	private static final String GET_ONE_STMT = "SELECT reportno,forumpostno, forumcommentno, studentno, type, description , reporttime ,status FROM report where reportno = ?";
	private static final String DELETE = "DELETE FROM report where reportno = ?";
	private static final String UPDATE = "UPDATE report set forumpostno=?, forumcommentno=?, studentno=?, type=?, description=?, reporttime=? ,status=? where reportno = ?";


	@Override
	public void insert(ReportVO reportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, reportVO.getForumPostNo());
			pstmt.setString(2, reportVO.getForumCommentNo());
			pstmt.setString(3, reportVO.getStudentNo());
			pstmt.setInt(4, reportVO.getType());
			pstmt.setString(5, reportVO.getDescription());
			pstmt.setTimestamp(6, reportVO.getReportTime());
			pstmt.setInt(7, reportVO.getStatus());


			pstmt.executeUpdate();

			
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, reportVO.getForumPostNo());
			pstmt.setString(2, reportVO.getForumCommentNo());
			pstmt.setString(3, reportVO.getStudentNo());
			pstmt.setInt(4, reportVO.getType());
			pstmt.setString(5, reportVO.getDescription());
			pstmt.setTimestamp(6, reportVO.getReportTime());
			pstmt.setInt(7, reportVO.getStatus());
			pstmt.setString(8, reportVO.getReportNo());

			pstmt.executeUpdate();

			
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, reportNo);

			pstmt.executeUpdate();

			
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

			con = ds.getConnection();
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
				reportVO.setStatus(rs.getInt("status"));


			}

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

			con = ds.getConnection();
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
				reportVO.setStatus(rs.getInt("status"));

				list.add(reportVO);
			}

			
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
}
	