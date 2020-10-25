package com.forumtopic.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ForumTopicJNDIDAO implements ForumTopicDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA103G6");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO forumtopic (forumtopicno, banjino, forumtopicname) VALUES (forumtopic_seq.NEXTVAL,?,?)";
	private static final String GET_ALL_STMT = "SELECT forumtopicno, banjino, forumtopicname FROM forumtopic where isDelete = 0 order by to_number(forumtopicno)";
	private static final String GET_ONE_STMT = "SELECT forumtopicno, banjino, forumtopicname FROM forumtopic where forumtopicno = ?";
	private static final String DELETE = "UPDATE forumtopic set isDelete=1 where forumtopicno = ?";
	private static final String UPDATE = "UPDATE forumtopic set banjino=?, forumtopicname=?";
	private static final String GETBYBANJINO = "select * from forumtopic where banjino = ? and isdelete = 0";

	@Override
	public void insert(ForumTopicVO forumTopicVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, forumTopicVO.getBanjiNo());
			pstmt.setString(2, forumTopicVO.getForumTopicName());

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
	public void update(ForumTopicVO forumTopicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, forumTopicVO.getBanjiNo());
			pstmt.setString(2, forumTopicVO.getForumTopicName());

			pstmt.setString(6, forumTopicVO.getForumTopicNo());

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
	public void delete(String forumTopicNo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, forumTopicNo);

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
	public ForumTopicVO findByPrimaryKey(String forumTopicNo) {

		ForumTopicVO forumTopicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, forumTopicNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				forumTopicVO = new ForumTopicVO();
				forumTopicVO.setForumTopicNo(rs.getString("forumTopicNo"));
				forumTopicVO.setBanjiNo(rs.getString("banjiNo"));
				forumTopicVO.setForumTopicName(rs.getString("forumTopicName"));

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
		return forumTopicVO;

	}

	@Override
	public List<ForumTopicVO> getAll() {
		List<ForumTopicVO> list = new ArrayList<ForumTopicVO>();
		ForumTopicVO forumTopicVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				forumTopicVO = new ForumTopicVO();
				forumTopicVO.setForumTopicNo(rs.getString("forumTopicNo"));
				forumTopicVO.setBanjiNo(rs.getString("banjiNo"));
				forumTopicVO.setForumTopicName(rs.getString("forumTopicName"));

				list.add(forumTopicVO);
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
	public List<ForumTopicVO> getByBanJiNo(String banjiNo) {
		List<ForumTopicVO> list = new ArrayList<ForumTopicVO>();
		ForumTopicVO forumTopicVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GETBYBANJINO);

			pstmt.setString(1, banjiNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				forumTopicVO = new ForumTopicVO();
				forumTopicVO.setForumTopicNo(rs.getString("forumTopicNo"));
				forumTopicVO.setBanjiNo(rs.getString("banjiNo"));
				forumTopicVO.setForumTopicName(rs.getString("forumTopicName"));

				list.add(forumTopicVO);

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
