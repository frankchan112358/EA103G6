package com.reply.model;

import java.sql.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ReplyJNDIDAO implements ReplyDAO_interface {
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
	private static final String INSERT_STMT = "INSERT INTO REPLY(REPLYNO,COURSEASKNO,TEACHERNO,STUDENTNO,REPLYCONTENT,UPDATETIME) VALUES (REPLY_SEQ.NEXTVAL,?,?,?,?,?)";
	// 查全部
	private static final String GET_ALL_STMT = "SELECT REPLYNO,COURSEASKNO,TEACHERNO,STUDENTNO,REPLYCONTENT,UPDATETIME FROM REPLY ORDER BY REPLYNO";
	// 查單個
	private static final String GET_ALL_COURSEASK = "SELECT REPLYNO,COURSEASKNO,TEACHERNO,STUDENTNO,REPLYCONTENT,UPDATETIME FROM REPLY WHERE COURSEASKNO=? ORDER BY UPDATETIME";
	// 查課程編號
	private static final String GET_ONE_STMT = "SELECT REPLYNO,COURSEASKNO,TEACHERNO,STUDENTNO,REPLYCONTENT,UPDATETIME FROM REPLY  WHERE REPLYNO =?";
	// 刪除
	private static final String DELETE = "DELETE FROM REPLY WHERE REPLYNO=?";
	// 修改
	private static final String UPDATE = "UPDATE REPLY SET COURSEASKNO=?,TEACHERNO=?,STUDENTNO=?,REPLYCONTENT=?,UPDATETIME=?  WHERE REPLYNO=?";

	@Override
	public void insert(ReplyVO replyVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, replyVO.getCourseAskNo());
			pstmt.setString(2, replyVO.getTeacherNo());
			pstmt.setString(3, replyVO.getStudentNo());
			pstmt.setString(4, replyVO.getReplyContent());
			pstmt.setTimestamp(5, replyVO.getUpdateTime());

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
	public void update(ReplyVO replyVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, replyVO.getCourseAskNo());
			pstmt.setString(2, replyVO.getTeacherNo());
			pstmt.setString(3, replyVO.getStudentNo());
			pstmt.setString(4, replyVO.getReplyContent());
			pstmt.setTimestamp(5, replyVO.getUpdateTime());
			pstmt.setString(6, replyVO.getReplyNo());

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
	public void delete(String replyNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, replyNo);

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
	public ReplyVO findByPrimaryKey(String replyNo) {
		ReplyVO replyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
	public List<ReplyVO> getAll() {
		List<ReplyVO> list = new ArrayList<ReplyVO>();
		ReplyVO replyVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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

	@Override
	public List<ReplyVO> getAllWithCouseAskNo(String courseAskNo) {
		List<ReplyVO> list = new ArrayList<ReplyVO>();
		ReplyVO replyVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
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
