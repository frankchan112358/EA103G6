package com.forumpost.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.io.IOException;
import java.sql.*;

public  class ForumPostJNDIDAO implements ForumPostDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO forumpost (forumpostno,forumtopicno,studentno,title, content , updatetime,createtime) VALUES (forumpost_seq.NEXTVAL,?, ?,?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT forumpostno,forumtopicno,studentno,title, content,forumpostviews,updatetime,createtime FROM forumpost where isDelete = 0 order by to_number(forumpostno)";
	private static final String GET_ALL_STMT_HOT = "SELECT * FROM forumpost where isDelete = 0 order by forumPostViews DESC";
	private static final String GET_ONE_STMT = "SELECT forumpostno,forumtopicno,studentno,title, content,forumpostviews,updatetime,createtime FROM forumpost where forumpostno = ?";
	private static final String GET_ONE_STUDENT_STMT = "SELECT forumpostno,forumtopicno,studentno,title, content,forumpostviews,updatetime,createtime FROM forumpost where studentno = ?";

	private static final String DELETE = "UPDATE forumpost set isDelete=1 where forumpostno = ?";
	private static final String UPDATE = "UPDATE forumpost set forumtopicno=?, studentno=?, title=?, content=?, updatetime=? where forumpostno = ?";
	private static final String UPDATE_STMT = "UPDATE forumpost SET title = ? , content = ? WHERE forumpostno=?";
	private static final String ADD_VIEWS = "UPDATE forumpost SET forumpostviews = ? WHERE forumpostno=?";
	private static final String RESEARCH_STMT = "SELECT * FROM forumpost WHERE isDelete = 0";
	private static final String GETBYTOPIC = "SELECT * FROM forumpost WHERE forumtopicno = ?";




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
//			pstmt.setInt(5, forumPostVO.getForumPostViews());
			pstmt.setTimestamp(5, forumPostVO.getUpdateTime());
			pstmt.setTimestamp(6, forumPostVO.getCreateTime());
			

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
//			pstmt.setTimestamp(6, forumPostVO.getCreateTime());
			pstmt.setString(6, forumPostVO.getForumPostNo());

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
	public void delete(String forumPostNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, forumPostNo);

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
	public ForumPostVO findOneFpByFpNo(String forumPostNo) {

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
				forumPostVO.setForumTopicNo(rs.getString("forumTopicNo"));
				forumPostVO.setStudentNo(rs.getString("studentNo"));
				forumPostVO.setTitle(rs.getString("title"));
				forumPostVO.setContent(rs.getString("content"));
				forumPostVO.setForumPostViews(rs.getInt("forumPostViews"));
				forumPostVO.setUpdateTime(rs.getTimestamp("updateTime"));
				forumPostVO.setCreateTime(rs.getTimestamp("createTime"));
				

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
//				forumPostVO.setForumPostViews(rs.getInt("forumPostViews"));
				forumPostVO.setUpdateTime(rs.getTimestamp("updateTime"));
				forumPostVO.setCreateTime(rs.getTimestamp("createTime"));
				list.add(forumPostVO);
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
	public List<ForumPostVO> findOneSTUDENT(String studentNo) {
		List<ForumPostVO> list = new ArrayList<ForumPostVO>();
		ForumPostVO forumPostVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ONE_STUDENT_STMT);
			pstmt.setString(1, studentNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				if (rs.getInt("isDelete") == 0) {
					forumPostVO = new ForumPostVO();
					forumPostVO.setForumPostNo(rs.getString("forumPostNo"));
					forumPostVO.setForumTopicNo(rs.getString("forumTopicNo"));
					forumPostVO.setStudentNo(rs.getString("studentNo"));
					forumPostVO.setTitle(rs.getString("title"));
					forumPostVO.setContent(rs.getString("content"));
//					forumPostVO.setForumPostViews(rs.getInt("forumPostViews"));
					forumPostVO.setUpdateTime(rs.getTimestamp("updateTime"));
					forumPostVO.setCreateTime(rs.getTimestamp("createTime"));
					list.add(forumPostVO);
				}
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
	public List<ForumPostVO> getAllHot() {
		List<ForumPostVO> list = new ArrayList<ForumPostVO>();
		ForumPostVO forumPostVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ALL_STMT_HOT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				if (rs.getInt("isDelete") == 0) {
					forumPostVO = new ForumPostVO();
					forumPostVO.setForumPostNo(rs.getString("forumPostNo"));
					forumPostVO.setForumTopicNo(rs.getString("forumTopicNo"));
					forumPostVO.setStudentNo(rs.getString("studentNo"));
					forumPostVO.setTitle(rs.getString("title"));
					forumPostVO.setContent(rs.getString("content"));
					forumPostVO.setForumPostViews(rs.getInt("forumPostViews"));
					forumPostVO.setUpdateTime(rs.getTimestamp("updateTime"));
					forumPostVO.setCreateTime(rs.getTimestamp("createTime"));
					list.add(forumPostVO);
				}
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
	public void addForumPostViews(ForumPostVO forumPostVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(ADD_VIEWS);

			pstmt.setInt(1, forumPostVO.getForumPostViews());
			pstmt.setString(2, forumPostVO.getForumPostNo());

			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources ( 關閉資源 )
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
	public List<ForumPostVO> search(String title) {
		List<ForumPostVO> list = new ArrayList<ForumPostVO>();
		ForumPostVO forumPostVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			con = ds.getConnection();

			
			pstmt = con.prepareStatement(RESEARCH_STMT);
			
			pstmt.setString(1,"%"+title+"%");
			
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
	public List<ForumPostVO> getByTopicNo(String forumTopicNo) {
		List<ForumPostVO> list = new ArrayList<ForumPostVO>();
		ForumPostVO forumPostVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			con = ds.getConnection();

			
			pstmt = con.prepareStatement(GETBYTOPIC);
			
			pstmt.setString(1, forumTopicNo);
			
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
}
