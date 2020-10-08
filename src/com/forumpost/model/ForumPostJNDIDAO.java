package com.forumpost.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ForumPostJNDIDAO implements ForumPostDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO forumpost (forumpostno,forumtopicno,studentno,title, content ,updatetime,createtime) VALUES (forumpost_seq.NEXTVAL,?, ?,?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT forumpostno,forumtopicno,studentno,title, content,updatetime,createtime FROM forumpost where isDelete = 0 order by forumpostno";
	private static final String GET_ONE_STMT = "SELECT forumpostno,forumtopicno,studentno,title, content,updatetime,createtime FROM forumpost where forumpostno = ?";
	private static final String DELETE = "UPDATE forumpost set isDelete=1 where forumpostno = ?";
	private static final String UPDATE = "UPDATE forumpost set forumtopicno=?, studentno=?, title=?, content=?, updatetime=?, createtime=? where forumpostno = ?";

	@Override
	public void insert(ForumPostVO forumPostVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, forumPostVO.getForumTopicNo());
			pstmt.setString(2, forumPostVO.getStudentNo());
			pstmt.setString(3, forumPostVO.getTitle());
			pstmt.setString(4, forumPostVO.getContent());
			pstmt.setTimestamp(5, forumPostVO.getUpdateTime());
			pstmt.setTimestamp(6, forumPostVO.getCreateTime());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void update(ForumPostVO forumPostVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, forumPostVO.getForumTopicNo());
			pstmt.setString(2, forumPostVO.getStudentNo());
			pstmt.setString(3, forumPostVO.getTitle());
			pstmt.setString(4, forumPostVO.getContent());
			pstmt.setTimestamp(5, forumPostVO.getUpdateTime());
			pstmt.setTimestamp(6, forumPostVO.getCreateTime());
			pstmt.setString(7, forumPostVO.getForumPostNo());

			pstmt.executeUpdate();

			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void delete(String forumPostNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, forumPostNo);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public ForumPostVO findByPrimaryKey(String forumPostNo) {

		ForumPostVO forumPostVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, forumPostNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				forumPostVO = new ForumPostVO();
				forumPostVO.setForumPostNo(rs.getString("forumPostNo"));
				forumPostVO.setForumTopicNo(rs.getString("forumTopicName"));
				forumPostVO.setStudentNo(rs.getString("studentName"));
				forumPostVO.setTitle(rs.getString("title"));
				forumPostVO.setContent(rs.getString("content"));
				forumPostVO.setUpdateTime(rs.getTimestamp("updateTime"));
				forumPostVO.setCreateTime(rs.getTimestamp("createTime"));
			}

			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			
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
		return forumPostVO;
	}

	@Override
	public List<ForumPostVO> getAll() {
		List<ForumPostVO> list = new ArrayList<ForumPostVO>();
		ForumPostVO forumPostVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				forumPostVO = new ForumPostVO();
				forumPostVO.setForumPostNo(rs.getString("forumPostNo"));
				forumPostVO.setForumTopicNo(rs.getString("forumTopicNo"));
				forumPostVO.setStudentNo(rs.getString("studentNo"));
				forumPostVO.setTitle(rs.getString("title"));
				forumPostVO.setContent(rs.getString("content"));
				forumPostVO.setUpdateTime(rs.getTimestamp("updateTime"));
				forumPostVO.setCreateTime(rs.getTimestamp("createTime"));
				list.add(forumPostVO);
			}

			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			
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

	