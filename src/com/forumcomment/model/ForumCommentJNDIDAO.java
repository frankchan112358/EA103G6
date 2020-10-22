package com.forumcomment.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class ForumCommentJNDIDAO implements ForumCommentDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO forumcomment (forumcommentno,forumpostno,studentno, content ,updatetime,createtime) VALUES (forumcomment_seq.NEXTVAL,?, ?,?, ?, ?)";
	// 顯示出一則貼文的留言
	private static final String GET_ONE_FP_ALLFC_STMT = "SELECT forumcommentno,forumpostno,studentno, content ,updatetime,createtime FROM forumcomment where forumpostno = ?";
	// 在一篇文章下留言
	private static final String GET_ONE_FP_ADD_FC = "SELECT forumpostno,studentno FROM forumcomment WHERE forumpostno =? ";
	private static final String GET_ALL_STMT = "SELECT forumcommentno,forumpostno,studentno, content ,updatetime,createtime FROM forumcomment where isDelete = 0 order by to_number(forumcommentno)";
	// 取得一篇留言
	private static final String GET_ONE_STMT = "SELECT forumcommentno,forumpostno,studentno, content ,updatetime,createtime FROM forumcomment where forumcommentno = ?";
	// 取得一篇文章的回應數
	private static final String GET_ONE_FP_RESPONSE = "SELECT COUNT(1) FROM forumcomment WHERE forumpostno = ?";
	// 刪除文章
	private static final String DELETE = "UPDATE forumcomment set isDelete=1 where forumcommentno = ?";
	// 修改文章
	private static final String UPDATE = "UPDATE forumcomment set content=? where forumcommentno = ?";
	// 取得一個學員的留言
	private static final String GET_ONE_STUDENT_FC = "SELECT forumcommentno,forumpostno,studentno, content ,updatetime,createtime FROM forumcomment WHERE studentno = ?";

	@Override
	public void insert(ForumCommentVO forumCommentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, forumCommentVO.getForumPostNo());
			pstmt.setString(2, forumCommentVO.getStudentNo());
			pstmt.setString(3, forumCommentVO.getContent());
			pstmt.setTimestamp(4, forumCommentVO.getUpdateTime());
			pstmt.setTimestamp(5, forumCommentVO.getCreateTime());
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
	public void update(ForumCommentVO forumCommentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE);

//			pstmt.setString(1, forumCommentVO.getForumPostNo());
//			pstmt.setString(2, forumCommentVO.getStudentNo());
			pstmt.setString(1, forumCommentVO.getContent());
//			pstmt.setTimestamp(4, forumCommentVO.getUpdateTime());
//			pstmt.setTimestamp(5, forumCommentVO.getCreateTime());
			pstmt.setString(2, forumCommentVO.getForumCommentNo());

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
	public void delete(String forumCommentNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, forumCommentNo);

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
	public ForumCommentVO getOneFcByFcNo(String forumCommentNo) {
		ForumCommentVO forumCommentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, forumCommentNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				forumCommentVO = new ForumCommentVO();
				forumCommentVO.setForumCommentNo(rs.getString("forumCommentNo"));
				forumCommentVO.setForumPostNo(rs.getString("forumPostNo"));
				forumCommentVO.setStudentNo(rs.getString("studentNo"));
				forumCommentVO.setContent(rs.getString("content"));
				forumCommentVO.setUpdateTime(rs.getTimestamp("updateTime"));
				forumCommentVO.setCreateTime(rs.getTimestamp("createTime"));

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
		return forumCommentVO;

	}

	@Override
	public List<ForumCommentVO> getAll() {
		List<ForumCommentVO> list = new ArrayList<ForumCommentVO>();
		ForumCommentVO forumCommentVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				forumCommentVO = new ForumCommentVO();
				forumCommentVO.setForumCommentNo(rs.getString("forumCommentNo"));
				forumCommentVO.setForumPostNo(rs.getString("forumPostNo"));
				forumCommentVO.setStudentNo(rs.getString("studentNo"));
				forumCommentVO.setContent(rs.getString("content"));
				forumCommentVO.setUpdateTime(rs.getTimestamp("updateTime"));
				forumCommentVO.setCreateTime(rs.getTimestamp("createTime"));
				list.add(forumCommentVO);
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

	@Override
	public List<ForumCommentVO> getOneFpFc(String forumPostNo) {
		List<ForumCommentVO> list = new ArrayList<ForumCommentVO>();
		ForumCommentVO forumCommentVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ONE_FP_ALLFC_STMT);

			pstmt.setString(1, forumPostNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				forumCommentVO = new ForumCommentVO();
				forumCommentVO.setForumCommentNo(rs.getString("forumCommentNo"));
				forumCommentVO.setForumPostNo(rs.getString("forumPostNo"));
				forumCommentVO.setStudentNo(rs.getString("studentNo"));
				forumCommentVO.setContent(rs.getString("content"));
				forumCommentVO.setUpdateTime(rs.getTimestamp("updateTime"));
				forumCommentVO.setCreateTime(rs.getTimestamp("createTime"));
				list.add(forumCommentVO);
			}

			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
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
	public ForumCommentVO addFcByGetFp(String forumPostNo) {
		ForumCommentVO forumCommentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ONE_FP_ADD_FC);

			pstmt.setString(1, forumPostNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				forumCommentVO = new ForumCommentVO();
				forumCommentVO.setForumPostNo(rs.getString("forumPostNo"));
				forumCommentVO.setStudentNo(rs.getString("studentNo"));
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

		return forumCommentVO;
	}

	@Override
	public List<ForumCommentVO> getOneStudentFc(String studentNo) {
		List<ForumCommentVO> list = new ArrayList<ForumCommentVO>();
		ForumCommentVO forumCommentVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ONE_STUDENT_FC);

			pstmt.setString(1, studentNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				forumCommentVO = new ForumCommentVO();
				forumCommentVO.setForumCommentNo(rs.getString("forumCommentNo"));
				forumCommentVO.setForumPostNo(rs.getString("forumPostNo"));
				forumCommentVO.setStudentNo(rs.getString("studentNo"));
				forumCommentVO.setContent(rs.getString("content"));
				forumCommentVO.setUpdateTime(rs.getTimestamp("updateTime"));
				forumCommentVO.setCreateTime(rs.getTimestamp("createTime"));
				list.add(forumCommentVO);
			}

			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
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
	public Integer getFcResponsesByFpNo(String forumPostNo) {
		Integer responses = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ONE_FP_RESPONSE);
			pstmt.setString(1,forumPostNo);
			
			rs = pstmt.executeQuery();
			rs.next();
			responses = rs.getInt("COUNT(1)");
		
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
		}finally {
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
		
		return responses;
	}



	

}
