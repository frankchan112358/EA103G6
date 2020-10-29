package com.reply.model;

import java.sql.*;
import java.util.*;

public class ReplyJDBCDAO implements ReplyDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA103G6";
	String passwd = "123456";

	// 新增
	private static final String INSERT_STMT = "INSERT INTO REPLY(REPLYNO,COURSEASKNO,TEACHERNO,STUDENTNO,REPLYCONTENT,UPDATETIME) VALUES (REPLY_SEQ.NEXTVAL,?,?,?,?,?)";
	// 查全部
	private static final String GET_ALL_STMT = "SELECT REPLYNO,COURSEASKNO,TEACHERNO,STUDENTNO,REPLYCONTENT,UPDATETIME FROM REPLY ORDER BY REPLYNO";
	// 查單個
	private static final String GET_ONE_STMT = "SELECT REPLYNO,COURSEASKNO,TEACHERNO,STUDENTNO,REPLYCONTENT,UPDATETIME FROM REPLY  WHERE REPLYNO =?";
	// 查編號
	private static final String GET_ALL_COURSEASK = "SELECT REPLYNO,COURSEASKNO,TEACHERNO,STUDENTNO,REPLYCONTENT,UPDATETIME FROM REPLY WHERE COURSEASKNO=? ORDER BY UPDATETIME";
	// 刪除
	private static final String DELETE = "DELETE FROM REPLY WHERE REPLYNO=?";
	// 修改
	private static final String UPDATE = "UPDATE REPLY SET COURSEASKNO=?,TEACHERNO=?,STUDENTNO=?,REPLYCONTENT=?,UPDATETIME=?  WHERE REPLYNO=?";

	@Override
	public void insert(ReplyVO replyVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, replyVO.getCourseAskNo());
			pstmt.setString(2, replyVO.getTeacherNo());
			pstmt.setString(3, replyVO.getStudentNo());
			pstmt.setString(4, replyVO.getReplyContent());
			pstmt.setTimestamp(5, replyVO.getUpdateTime());

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
	public void update(ReplyVO replyVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, replyVO.getCourseAskNo());
			pstmt.setString(2, replyVO.getTeacherNo());
			pstmt.setString(3, replyVO.getStudentNo());
			pstmt.setString(4, replyVO.getReplyContent());
			pstmt.setTimestamp(5, replyVO.getUpdateTime());
			pstmt.setString(6, replyVO.getReplyNo());

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
	public void delete(String replyNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, replyNo);

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
	public ReplyVO findByPrimaryKey(String replyNo) {
		ReplyVO replyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, replyNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				replyVO = new ReplyVO();
				replyVO.setReplyNo(rs.getString("replyNo"));
				replyVO.setCourseAskNo(rs.getString("courseAskNo"));
				replyVO.setTeacherNo(rs.getString("teacherNo"));
				replyVO.setStudentNo(rs.getString("studentNo"));
				replyVO.setReplyContent(rs.getString("replyContent"));
				replyVO.setUpdateTime(rs.getTimestamp("updateTime"));
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
		return replyVO;
	}

	@Override
	public List<ReplyVO> getAllWithCouseAskNo(String courseAskNo) {
		List<ReplyVO> list = new ArrayList<ReplyVO>();
		ReplyVO replyVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_COURSEASK);
			pstmt.setString(1, courseAskNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				replyVO = new ReplyVO();
				replyVO.setReplyNo(rs.getString("replyNo"));
				replyVO.setCourseAskNo(rs.getString("courseAskNo"));
				replyVO.setTeacherNo(rs.getString("teacherNo"));
				replyVO.setStudentNo(rs.getString("studentNo"));
				replyVO.setReplyContent(rs.getString("replyContent"));
				replyVO.setUpdateTime(rs.getTimestamp("updateTime"));

				list.add(replyVO);
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
	public List<ReplyVO> getAll() {
		List<ReplyVO> list = new ArrayList<ReplyVO>();
		ReplyVO replyVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				replyVO = new ReplyVO();
				replyVO.setReplyNo(rs.getString("replyNo"));
				replyVO.setCourseAskNo(rs.getString("courseAskNo"));
				replyVO.setTeacherNo(rs.getString("teacherNo"));
				replyVO.setStudentNo(rs.getString("studentNo"));
				replyVO.setReplyContent(rs.getString("replyContent"));
				replyVO.setUpdateTime(rs.getTimestamp("updateTime"));

				list.add(replyVO);
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
		ReplyJDBCDAO dao = new ReplyJDBCDAO();

		// 新增
//		ReplyVO replyVO1 = new ReplyVO();
//		replyVO1.setCourseAskNo("1");
//		replyVO1.setTeacherNo("T000003");
//		replyVO1.setStudentNo("S000002");
//		replyVO1.setReplyContent("是的");
//		replyVO1.setUpdateTime(new java.sql.Timestamp((new java.util.Date()).getTime()));
//
//		dao.insert(replyVO1);

		// 查詢
//		ReplyVO replyVO2 = dao.findByPrimaryKey("1");
//		System.out.print(replyVO2.getReplyNo() + ",");
//		System.out.print(replyVO2.getCourseAskNo() + ",");
//		System.out.print(replyVO2.getTeacherNo() + ",");
//		System.out.print(replyVO2.getStudentNo() + ",");
//		System.out.println(replyVO2.getReplyContent() + ",");
//		System.out.println(replyVO2.getUpdateTime());
//		System.out.println("------------------------------");

		// 查詢
//		List<ReplyVO> list = dao.getAll();
//		for (ReplyVO aReplyVO : list) {
//			System.out.print(aReplyVO.getReplyNo() + ",");
//			System.out.print(aReplyVO.getCourseAskNo() + ",");
//			System.out.print(aReplyVO.getTeacherNo() + ",");
//			System.out.print(aReplyVO.getStudentNo() + ",");
//			System.out.println(aReplyVO.getReplyContent() + ",");
//			System.out.println(aReplyVO.getUpdateTime());
//			System.out.println("------------------------------");
//		}
		// 刪除
//		dao.delete("5");

		// 修改
		ReplyVO replyVO3 = new ReplyVO();
		replyVO3.setCourseAskNo("2");
		replyVO3.setTeacherNo("T000002");
		replyVO3.setStudentNo("S000001");
		replyVO3.setReplyContent("對的");
		replyVO3.setUpdateTime(new java.sql.Timestamp((new java.util.Date()).getTime()));
		replyVO3.setReplyNo("1");
		dao.update(replyVO3);
	}

}
